---
title: 第2-7节：ReAct 意图识别与反馈回路
pay: https://t.zsxq.com/389Xp
---

# 《WaLiSSH - AI Shell 智能终端》第2-7节：ReAct 意图识别与反馈回路

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/ZuQw4](https://t.zsxq.com/ZuQw4)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

上一节，我们把上下文治理从业务层延伸到了 ADK 框架层——自定义 `SessionService` 负责净化和裁剪，`MemoryService` 空实现守住边界。至此，WaLiSSH 的上下文体系已经形成了"业务层 + 框架层"的双层治理架构。

但如果你真的拿这套系统去跑真实运维任务，很快会发现一个新的问题——**模型不知道用户想干什么**。

举个例子。用户说"看下 nginx 502 是不是因为我刚改了 redis 配置导致连接池打满"。这句话里同时包含了诊断、监控配置三个意图。模型收到这条消息后，要么一个模糊指令去猜，要么在 ReAct 循环里反复试错——先查日志（MONITOR），再查配置（CONFIGURE），再排查原因（DIAGNOSE）。每一步工具调用都要花 token、花时间，而如果模型一开始就知道"这是一个复合意图，涉及诊断和配置"，它完全可以更有策略地编排工具调用顺序。

再举个例子。用户说"继续"。如果没有意图识别系统，模型只能把""当成一个新指令，重新理解上下文、重新规划。但如果系统知道当前有一个进行中的多步任务（比如排查 nginx 502 已经执行到第三步），那"继续"就应该精确续接到第三步，而不是从头开始。

还有一个更隐蔽的问题。用户说"帮我改下 redis 的 maxmemory 配置"，模型识别为 CONFIGURE，去执行 `cat redis.conf`，结果返回 `No such file or directory`。这说明意图可能走偏了——用户说的"redis 配置"可能不是默认路径的 `redis.conf`，而是 Docker 容器里的配置。如果没有反馈回路，模型只能按原意图继续猜路径；如果有反馈回路，系统可以自动重模型重新判断。

这三个问题的根源是一样的：**ReAct 引擎只有"工具调用"能力，没有"理解用户意图"能力。**

所以这一节，我们要给 WaLiSSH 装上"意图识别大脑"——一套规则 + LLM 级联的意图识别子系统，带反馈回路，能在工具执行失败时自动纠偏。

这一节做完，WaLiSSH 的 ReAct 引擎就从"只会调工具"变成了"先理解你要干什么，再决定调什么工具"。

## 一、本章诉求

本章的目标，是在 `walissh-server` 工程里，为 ReAct 引擎增加一套 **意图识别与反馈回路** 能力，核心包括：

- **定义意图识别服务接口**：`IIntentService` 抽象出"分类、反馈、任务态、配置注入"四个能力。
- **实现意图识别服务**：`IntentService` 采用三级级联策略——规则分类→LLM 兜底→反馈回路重分类，带 LRU 缓存。
- **定义意图分类器接口**：`IIntentClassifier` 统一抽象分类能力，规则层和 LLM 层各一个实现。
- **实现规则分类器**：`RuleIntentClassifier` 通过关键词匹配 + 正则模式 + 上下文加权，< 1ms 完成高置信度分类。
- **实现 LLM 分类器**：`LLMIntentClassifier` 复用 Agent 自己的 `OpenAiApi`，构建独立 `ChatModel`（temperature=0.1），做 LLM 兜底分类。
- **实现窗口 10 条）、失败计数、任务态，带惰性过期清理。
- **定义意图值对象**：`IntentTypeEnumVO`（13 种意图，这类的要结合具体场景来提炼）、`IntentResultVO`（主意图 + 候选 + 实体 + 重分类标记）、`ConversationContextVO`、`TaskStateVO`、`IntentRuleVO`、`IntentHistoryEntryVO`。
- **AiAgentRegisterVO 扩展**：新增 `openAiApi 和 `chatModelName`，把 Agent 装配链路中的 API 配置透传给意图识别系统。
- **PromptContextVO 扩展**：新增 `intentLabel` 字段，意图标签经 `DynamicPromptBuilder` 渲染为 `[用户意图]` 前缀。
- **AiCallNode 接入**：调用前识别意图、注入标签，工具执行后触发反馈回路。

如果说 2-5 是"给 AI 搭上下文骨架"，2-6 是"给框架装净化器"，那么 2-7 就是"给 AI 装理解大脑的神经"。

## 二、流程设计

这一节的架构变化，是在 ReAct 主链路的 `AiCallNode` 调用模型前，插入一个"意图识别前置环节"，并在工具执行后增加一个"反馈回路"。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walissh/walissh-2-7-01.png" width="950px">
</div>

一次 ReAct 循环中，意图识别的完整流转如下：

1. `AiCallNode` 在调用模型前，先调 `intentService.configure(openAiApi, modelName)` 注入 Agent 的 API 配置（复用智能体自己的模型，不单独配置）。
2. 然后调 `intentService.classify(sessionId, userId, message)` 识别意图。
3. `IntentService` 先查 LRU 缓存（5 分钟过期），命中则直接返回；未命中进入级联分类。
4. **第一层：规则分类**（< 1ms）——`RuleIntentClassifier` 通过关键词 + 正则 + 上下文加权打分，置信度 ≥ 0.8 直接采信。
5. **第二层：LLM 分类**（100~500ms）——置信度不足时下沉到 `LLMIntentClassifier`，用独立 `ChatModel`（temperature=0.1、无工具回调）做 LLM 分类，置信度 ≥ 0.5 采用。
6. 分类结果写入 `ContextTracker`（意图历史 + 任务态）和 LRU 缓存。
7. 意图标签（如 `DIAGNOSE`）经 `PromptContextVO.intentLabel` → `DynamicPromptBuilder` 渲染为 `[用户意图] 诊断问题` 前缀，拼到用户消息前面。
8. **COMPOUND / UNKNOWN / 低置信度（< 0.5）不硬路由**，全交主模型自行决策。
9. 工具执行完成后，`AiCallNode` 调 `intentService.reportFeedback(sessionId, lastIntent, success, toolResult)`。
10. **第三层：反馈回路**——工具执行失败且原意图置信度不高，用候选意图递补（最多一次，避免循环）。

整套设计最关键的三个思想：

**一是级联策略**。规则层快但覆盖面窄（< 1ms 处理高置信度场景），LLM 层慢但泛化强（100~500ms 处理模糊场景）。先快后慢，让 80% 的高置信在规则层就被拦截，不浪费 LLM 调用。

**二是"提示不硬路由"**。意图识别的结果只作为 `[用户意图]` 标签注入 Prompt，让主模型感知当前意图，但不强制路由。即便识别为 `DIAGNOSE`，主模型仍可自主决定调用哪些工具。这样设计的好处是：意图识别即使出错，也不会阻断主流程——最坏情况是标签不准，模型忽略它。

**三是反馈回路**。工具执行失败时，系统自动判断是否需要重分类。比如意图是 `CONFIGURE` 但工具返回 `No such file`，系统用候选意图递补重分类。但重分类最多一次，避免循环递补。
