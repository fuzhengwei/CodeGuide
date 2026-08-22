---
title: 第2-6节：ReAct ADK Session 上下文治理与历史净化
pay: https://t.zsxq.com/jQiN0
---

# 《WaLiSSH - AI Shell 智能终端》第2-6节：ReAct ADK Session 上下文治理与历史净化

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/L35SG](https://t.zsxq.com/L35SG)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

上一节，我们把 WaLiSSH 的 ReAct 上下文能力从“手工拼 Prompt”升级成了一套可治理的体系：`ContextProvider` 负责可插拔采集，`MessageReducer` 负责历史裁剪，`ChatContextService` 负责聚合收口。这样一来，AI 在每轮调用前可以看到服务器环境、当前任务、关键里程碑、工具结果摘要，不再像早期那样“失忆”。

但工程做到这里，还不能说上下文治理彻底完成。

为啥？因为我们治理的是**业务层上下文**，而 ReAct 的模型调用最终还要经过 Google ADK 的 `Runner`。ADK 自己也有一套 `SessionService`，它会把每次运行产生的 `Event` 写进 `Session.events`。于是，一个新的问题出现了：你在业务层辛辛苦苦净化好的上下文，到了 ADK 框架层，又被原样存了一份（沈腾说的，这不完犊子了吗！）。

举个例子。上一节 `AiCallNode` 调用模型前，会把用户消息富化成这样：

```text
[系统环境]
系统: Linux 5.15.0-91-generic x86_64
用户: ubuntu
目录: /opt/app

[最近执行的命令]
- tail -100 /var/log/nginx/error.log

[关键事件]
- [ERROR] connect() failed (111: Connection refused)

[当前任务]
帮我排查 nginx 502

帮我继续看 nginx 日志
```

这条消息非常适合发给模型，因为模型确实需要这些上下文辅助推理。可它不适合被 ADK Session 原封不动地保存。否则下一轮再取历史时，所谓的“用户消息”就不是“帮我继续看 nginx 日志”，而是一整坨动态前缀 + 原始问题。再下一轮继续注入，又会形成重复回灌：第一轮前缀 200 字，第二轮 400 字，第三轮 800 字……越聊越脏。

更麻烦的是 SSH 场景的工具输出通常很长：`docker logs`、`tail -500 error.log`、`cat application.yml`、`kubectl describe pod`，随便一条都可能几千字。如果这些内容被 ADK Session 无限制保存，业务层的 `MessageReducer` 再聪明，也管不到 ADK 内部的 `Session.events`。

所以这一节，我们要解决的是比 2-5 更深一层的问题：**上下文进入 ADK 框架后，怎么不被污染、不膨胀、不失控。**

本节做完以后，WaLiSSH 会形成一套“双层上下文治理”架构：

- **业务层**：决定“每轮给模型看什么”，由 `ContextProvider + MessageReducer + ChatContextService + PromptService` 管理。
- **框架层**：决定“ADK 自己记什么”，由自定义 `SessionService + MemoryService + RunnerFactory` 管理。

这就是从“会用框架”到“能驾驭框架”的分水岭。

## 一、本章诉求

本章的目标，是在 `walissh-server` 工程里，围绕上一节完成的 Context 体系，补齐 **ADK Session 会话治理与历史净化** 能力，核心包括：

- **自定义 Runner 装配**：通过 `CustomRunnerFactory` 显式注入自定义 `SessionService` 和 `MemoryService`，接管 ADK 框架层会话行为。
- **自定义 ADK Session 服务**：`CustomAdkSessionService` 实现 `BaseSessionService`，在 `appendEvent()` 入口完成事件净化、长文本截断、按轮次裁剪。
- **动态前缀净化**：对 `user` 事件剥离 `[系统环境]`、`[关键事件]`、`[当前任务]` 等动态前缀，只把原始问题写入 Session。
- **按角色差异化截断**：`tool` 文本最多保留 1000 字符，`assistant/model` 文本最多保留 2000 字符，防止日志和大段回答撑爆框架层历史。
- **Session events 受控增长**：最多保留最近 4 轮用户对话、最多 20 条 Event，让 ADK Session 永远保持轻量。
- **Memory 边界控制**：`CustomAdkMemoryService` 故意做空实现，把长期记忆职责留在业务层，避免两套记忆机制互相打架。
- **消息组级 Reducer 升级**：`SlidingWindowReducer`、`PriorityReducer`、`HybridReducer` 从单条消息裁剪升级为消息组裁剪，保证 `assistant(tool_calls)` 与 `tool result` 不被拆散。
- **Provider 治理增强**：`TaskProvider` 增加前缀清洗，`ToolResultProvider` 增加单会话缓存上限和 `clear()` 方法。
- **ReAct 节点链闭环**：`RootNode` 记录原始任务，`AiCallNode/ToolCallNode` 沉淀工具结果，`UserFeedbackNode` 统一清理缓存。

如果说 2-4 是“给 AI 装眼睛”，2-5 是“给上下文搭骨架”，那么 2-6 就是“给上下文装净化器和限流器”。

## 二、流程设计

这一节的核心变化，不是再新增一种上下文，而是把上下文治理从业务层延伸到 ADK 框架层。完整链路可以理解为两条并行但边界清晰的流水线。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walissh/walissh-2-6-01.png" width="950px">
</div>

### 1. 业务层：决定“给模型看什么”

业务层仍然沿用上一节的主线：

1. `RootNode` 初始化 `DynamicContext`，绑定 `sessionId/userId/terminalSessionId`，并记录原始用户任务。
2. `AiCallNode` 调用模型前，先通过 `chatContextService.trimHistory(history, 8000)` 裁剪业务侧历史。
3. `PromptService.buildEnrichedMessage()` 委托 `ChatContextService` 聚合 Provider 输出，构建 `PromptContextVO`。
4. `DynamicPromptBuilder` 把环境、任务、里程碑、工具摘要渲染成动态前缀，拼到原始用户消息前面。
5. 工具执行完成后，`AiCallNode` 或 `ToolCallNode` 将工具结果推送给 `ChatContextService.pushToolResult()`，供下一轮摘要注入。

业务层关心的是：**模型这一轮推理需要看到什么信息。**