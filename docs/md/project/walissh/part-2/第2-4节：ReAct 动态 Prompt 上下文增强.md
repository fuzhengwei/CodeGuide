---
title: 第2-4节：ReAct 动态 Prompt 上下文增强
pay: https://t.zsxq.com/BCuOl
---

# 《WaLiSSH - AI Shell 智能终端》第2-4节：ReAct 动态 Prompt 上下文增强

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/HV9Z7](https://t.zsxq.com/HV9Z7)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

上一节我们把 ReAct 的执行过程沉淀成了 Case 层的五节点链路：`RootNode → AiCallNode → ToolCallNode → LoopDecisionNode → UserFeedbackNode`，整条链路已经“跑得起来、停得下来、扩得出去”。但如果你真的拿它去操作服务器，很快会发现一个隐蔽但致命的问题——**AI 是“失忆”的，也是“盲人”**。

啥意思？

你让它“帮我安装 docker”，它不知道当前服务器是 Ubuntu 还是 CentOS，只能瞎猜一条命令发下去；你上一轮刚在 `/opt/app` 目录下操作，这一轮它根本不知道自己在哪个目录；你刚说“不对，换个方式”，它下一轮就把你的纠偏忘了，继续按老思路干。更要命的是，如果模型哪天抽风给你生成一条 `rm -rf /`，后端也没有任何拦截，直接就往服务器上打了。

这些问题的根源，不在于模型不够聪明，而在于**我们给模型的上下文太少了**。上一节的 `AiCallNode` 只是把用户的原始消息原封不动地丢给模型，模型看到的永远是“一句孤立的话”，没有环境、没有历史、没有教训。

所以这一节，我们要给 ReAct 引擎装上“眼睛”和“记性”——做**动态 Prompt 上下文增强**。你可以在每轮对话前，自动把服务器环境信息（OS、用户、工作目录）、最近执行的命令、关键事件（用户纠偏、任务切换、工具报错）注入到用户消息里，让模型每次决策时都“心里有数”。同时，再给工具层加一道危险命令拦截，把 `rm -rf /` 这类自杀式命令挡在门外。

这一节做完，WaLiSSH 的 AI 将从“一个只会埋头执行的工具人”，变成“一个知道自己在哪、干过啥、被纠正过啥的运维助手”。

## 一、本章诉求

本章的目标，是在 `walissh-server` 工程里，为 ReAct Case 层增加一套**动态 Prompt 构建体系**，核心包括：

- **定义 Prompt 上下文值对象**：通过 `PromptContextVO` 承载环境信息、最近命令、里程碑事件三类动态上下文。
- **实现动态 Prompt 构建器**：`DynamicPromptBuilder` 负责把上下文组装成结构化的消息前缀（`[系统环境]`、`[最近执行的命令]`、`[关键事件]`）。
- **实现里程碑追踪器**：`MilestoneTracker` 基于正则规则自动识别用户纠偏、任务完成、任务切换、工具报错等关键事件，并按会话维度缓存。
- **收口 Prompt 领域服务**：通过 `IPromptService` 统一对外提供“环境采集 + 里程碑记录 + 消息富化”能力，Case 层只依赖接口，不碰内部组件。
- **改造 AiCallNode 注入逻辑**：在每轮调用模型前，自动构建富化消息（原始消息 + 动态前缀），并在工具执行后记录命令与里程碑。
- **危险命令拦截**：在 `SshExecuteAdkTool` 中通过正则识别 `rm -rf /`、`dd if=`、`mkfs.` 等高危命令，直接拒绝执行。
- **扩展 DynamicContext**：增加 `recentCommands` 最近命令缓存，支撑跨轮次的命令记忆。

如果说上一节是“让 AI 跑得有章法”，这一节就是“让 AI 跑得有脑子、有分寸”。

## 二、流程设计

这一节的核心变化，发生在 `AiCallNode` 调用模型之前和工具执行之后。上一节的链路是“用户消息 → 直接丢给模型”，这一节变成了“用户消息 → 采集环境 + 查询里程碑 → 拼装动态前缀 → 富化消息 → 再丢给模型”。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walissh/walissh-2-4-01.png" width="950px">
</div>

完整链路如下：

1. `AiCallNode` 拿到用户消息后，先调用 `promptService.detectAndRecordMilestone()`，让 `MilestoneTracker` 判断这句话是不是一次纠偏/切换/完成信号。
2. 接着调用 `promptService.buildEnrichedMessage()`，由 `PromptService` 完成三件事：
   - 通过 `ISshTerminalService` 在终端里执行 `uname -srm`、`whoami`、`pwd`，采集环境信息；
   - 从 `MilestoneTracker` 取出该会话最近 10 条里程碑；
   - 从 `DynamicContext` 取出最近执行的命令列表。
3. `DynamicPromptBuilder` 把这三类数据拼装成消息前缀，拼在原始用户消息前面，形成富化消息。
4. 富化消息交给 ADK Runner 推理，模型这下就能“看到”自己在哪台机器、哪个目录、刚干过啥、用户纠过啥。
5. 工具执行完成后，`AiCallNode` 把执行的命令记录到 `DynamicContext.recentCommands`，并把工具结果交给 `MilestoneTracker` 检测是否出现报错里程碑。
6. 下一轮循环时，这些新产生的上下文又会被注入，形成“越聊越聪明”的正循环。
