---
title: 第2-3节：ReAct Case 架构设计与实现
pay: https://t.zsxq.com/BCuOl
---

# 《WaLiSSH - AI Shell 智能终端》第2-3节：ReAct Case 架构设计与实现

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/HV9Z7](https://t.zsxq.com/HV9Z7)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

上一节我们已经把最小 MVP 版跑通了：前端右侧发一句话，后端通过 SSE 把 AI 的回复、工具执行结果流式推给 UI，左侧 SSH 终端还能实时回显命令执行过程。这个版本已经“能用了”，但还不够“好扩展”。

为什么这么说？因为上一节的 `AgentServiceController.chatStream` 里承担了太多逻辑：既要处理 HTTP 入参出参，又要管 SSE 事件协议，还要识别 ADK 事件流、拼装 tool_call / tool_result、管理 ThreadLocal 清理……这类代码一旦继续叠加，很快就会变成一坨“能跑但不敢动”的业务胶水。

所以这一节，我们不再只盯着“功能跑通”，而是开始做**架构升级**——把 ReAct 执行过程从 trigger 层抽出来，沉淀为一个独立的 **Case 层执行模型**。你可以把它理解成：上一节是在修一条“能通车的临时路”，这一节则是在修“正式高架桥”，以后加更多工具、更多终止条件、更多节点能力，都可以沿着这套结构继续扩展。

这一节做完以后，WaLiSSH 的 AI 对话后端将从“MVP 事件转发器”，升级为一个真正意义上的 **ReAct Case 执行引擎**。

## 一、本章诉求

本章的目标，是在 `student/walissh-server` 工程里，把上一节 trigger 层里偏重的 ReAct 执行逻辑，沉淀为一套独立的 **Case 层架构**。核心包括：

- **定义 ReAct Case 对外接口**：抽象出 `IAIAgentReActServiceCase`，统一提供 `chat()` 与 `chatStream()` 两种对话模式。
- **设计 ReAct 动态上下文**：通过 `DefaultReActFactory.DynamicContext` 管理会话信息、消息历史、工具调用列表、循环状态与 SSE 发射器。
- **拆分执行链路为 5 个节点**：`RootNode → AiCallNode → ToolCallNode → LoopDecisionNode → UserFeedbackNode`，每个节点只负责一件事。
- **复用结构化 SSE 事件协议**：把上一节已验证可用的 `text / tool_call / tool_result / round_end / done / error` 事件发送能力，下沉到抽象基类统一复用。
- **实现 ReAct 终止条件控制**：支持 `finish / max_steps / max_tool_calls / error / completed` 等多种 stopReason，保证循环既能持续，又能正确停止。
- **通过真实 SSH 测试验证链路**：编写 `SshAgentReActTest`，直接连接真实服务器，验证同步对话、流式对话、工具调用、最终收口全部打通。

如果说上一节是“让 AI 跑起来”，这一节就是“让 AI 跑得有章法”。

## 二、流程设计

本节完成后，后端的 ReAct 执行链路不再直接堆在 Controller，而是由一个独立的 Case 层承接。

说白了，上一节是“先跑起来”，这一节是“把跑起来的东西整理成架构”。这个动作在真实项目里特别常见：MVP 阶段先验证价值，确认链路是通的；一旦确认方向对了，马上就要做结构收敛，不然后面每加一个功能都会越来越痛苦。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walissh/walissh-2-3-01.png" width="950px">
</div>

完整链路如下：

1. 用户提交 `ChatRequestDTO`，其中包含 `agentId / userId / sessionId / message / terminalSessionId`。
2. `AIAgentReActServiceCase` 创建 `DynamicContext`，准备 `ResponseBodyEmitter`，并从 `reactRootNode` 启动链路。
3. `RootNode` 负责初始化上下文、绑定终端会话、把用户消息追加到历史中。
4. `AiCallNode` 调用 ADK Runner 执行推理，处理文本流，并从 `stateDelta` 中提取工具执行结果。
5. 如果本轮有工具调用，则进入 `ToolCallNode`；如果无工具调用，则进入 `LoopDecisionNode`。
6. `LoopDecisionNode` 根据最大步数、最大工具调用次数、finish 指令、错误状态等条件判断是否继续循环。
7. 一旦满足终止条件，则进入 `UserFeedbackNode`，构建 `ReActResultDTO`，发送 `done` 事件并关闭 SSE。

整套设计的核心点，不是“多写了几个类”，而是把 **流程推进**、**状态管理**、**结果输出** 三件事彻底拆开了：

- 节点负责推进流程
- `DynamicContext` 负责保存状态
- 抽象基类负责发送统一 SSE 事件
