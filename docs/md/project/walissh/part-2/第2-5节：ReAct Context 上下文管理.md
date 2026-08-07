---
title: 第2-5节：ReAct Context 上下文管理
pay: 
---

# 《WaLiSSH - AI Shell 智能终端》第2-5节：ReAct Context 上下文管理

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[]()

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

上一节我们给 ReAct 引擎装上了“眼睛”和“记性”——每轮对话前自动采集服务器环境（OS/用户/目录）、查询里程碑事件，拼成动态前缀注入用户消息。AI 从“失忆的工具人”变成了“知道自己在哪、干过啥的运维助手”。

但这套方案跑上几轮真实任务，新的问题就暴露出来了——**它开始“消化不良”了**。

第一个问题：**上下文越喂越多，模型开始“吃撑”**。你让它排查一个线上问题，它连续执行了 20 条命令，每条命令的输出都往 `messageHistory` 里塞。到第 21 轮，光历史消息就好几万 token，模型要么直接报 context length exceeded，要么被海量信息淹没，开始“答非所问”——你问 nginx，它跟你聊半小时前的磁盘。

第二个问题：**PromptService 开始“又当爹又当妈”**。上一节的 `PromptService` 里，它既要调 SSH 终端采集环境，又要管里程碑，又要拼前缀。今天你要加“当前 git 分支”，明天要加“docker 容器状态”，后天要加“上一轮命令的失败率”，每加一个上下文来源，就得改一遍 `PromptService`。这个类正在变成一个谁都得动的“上帝类”。

第三个问题：**工具结果没有被“消化”**。工具执行完，结果是塞进历史了，但模型每轮看到的是原始的、冗长的输出全文。其实模型需要的不是“每条命令的完整输出”，而是“我执行过哪些命令、大概什么结果”这样一个**摘要**。全文该裁剪裁剪，摘要该提炼提炼。

这三个问题的根源是一样的：**上下文的生产、加工、消费，全揉在一起，没有分层，也没有治理**。

所以这一节，我们要把上一节“能跑”的动态 Prompt，升级成一套**可扩展、可治理的 Context 上下文管理体系**。核心做两件事：

1. **上下文采集插件化**：把环境采集、任务提取、里程碑、工具结果，拆成一个个独立的 `ContextProvider`，用 Spring 自动收集、按序聚合。以后想加新的上下文来源，新增一个类就行，`PromptService` 一行不用改。
2. **消息历史治理**：加一套 `MessageReducer` 裁剪器，在 token 预算内，用“优先级 + 滑动窗口”的混合策略，把不重要的历史裁掉、重要的留下，防止上下文撑爆。

这一节做完，WaLiSSH 的上下文能力就从“硬编码的小作坊”，变成了“有架构、可扩展的流水线”。

## 一、本章诉求

本章的目标，是在 `walissh-server` 工程里，为 ReAct 增加一套 **Context 上下文管理体系**，核心包括：

- **定义上下文采集器，提供者接口**：`ContextProvider` 抽象出“采集一类上下文”的能力，约定 `getName/getOrder/enabled/provide` 四个契约。
- **实现四个内置 Provider**：`TerminalStateProvider`（终端环境）、`TaskProvider`（当前任务）、`MilestoneProvider`（里程碑）、`ToolResultProvider`（工具结果摘要）。
- **定义消息裁剪器接口**：`MessageReducer` 抽象出“在 token 预算内裁剪历史”的能力。
- **实现三个 Reducer**：`SlidingWindowReducer`（滑动窗口）、`PriorityReducer`（优先级）、`HybridReducer`（混合交集）。
- **收口上下文领域服务**：`IChatContextService` / `ChatContextService` 作为聚合中枢，对上提供“聚合上下文、裁剪历史、记录工具结果”三个能力。
- **扩展 PromptContextVO**：新增 `toolResultSummary`（工具执行摘要）、`taskDescription`（当前任务）两个字段。
- **PromptService 瘦身**：把环境采集逻辑下沉到 `TerminalStateProvider`，`PromptService` 只做组装编排。
- **AiCallNode / ToolCallNode 接入**：调用前裁剪历史、富化消息，工具执行后推送结果生成摘要。

如果说上一节是“给 AI 装眼睛和记性”，这一节就是“给这套感知系统搭上可扩展的骨架”。

## 二、流程设计

这一节的架构变化，本质是把上一节“`AiCallNode → PromptService` 一条直线”的调用，变成“`AiCallNode → ChatContextService（聚合中枢）→ N 个 Provider / Reducer`”的星型结构。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walissh/walissh-2-5-01.png" width="950px">
</div>

一次 ReAct 循环中，上下文的完整流转如下：

1. `AiCallNode` 在调用模型前，先调 `chatContextService.trimHistory(history, 8000)`，由 `HybridReducer` 用“优先级 ∩ 滑动窗口”策略裁剪历史，防止上下文撑爆。
2. 然后调 `promptService.buildEnrichedMessage()`，`PromptService` 不再自己采集环境，而是委托 `chatContextService.buildPromptContext()`。
3. `ChatContextService` 按 `order` 依次调用四个 Provider：终端环境（10）→ 当前任务（20）→ 里程碑（30）→ 工具结果摘要（40），把各自的输出合并成一个 `PromptContextVO`。
4. `DynamicPromptBuilder` 把 VO 渲染成消息前缀（新增 `[工具执行摘要]`、`[当前任务]` 两段），拼在原始消息前面，回流给 `AiCallNode`。
5. 富化消息发给 LLM。工具执行完成后，`AiCallNode` / `ToolCallNode` 调 `chatContextService.pushToolResult()`，把结果写入 `ToolResultProvider` 的会话缓存，并使摘要缓存失效。
6. 下一轮循环时，`ToolResultProvider` 重新生成工具摘要，注入到新一轮的 Prompt 里——形成“执行 → 记录 → 摘要 → 再注入”的闭环。

整套设计最关键的两个思想：

**一是开闭原则**。上下文来源未来一定会不断增加（git 分支、容器状态、负载情况……）。把它们抽象成 `ContextProvider`，用 Spring 的 `List<ContextProvider>` 自动收集全部实现，新增来源只需加一个 `@Component` 类，`ChatContextService` 和 `PromptService` 完全不用动。这就是“对扩展开放、对修改关闭”。

**二是策略模式 + 组合**。消息裁剪没有“银弹”——滑动窗口保证时效性（新的不丢），优先级保证重要性（错误不丢），但单靠哪一个都有短板。所以本节用 `HybridReducer` 把两者组合，取保留结果的**交集**，再强制保底最近 2 条，让最终上下文同时满足两种约束。

>AI 辅助编码时代，架构，思维，设计思想变得更加重要，如果不能站在顶层视角俯瞰和把控，很容易造成工程的随机性混乱。
