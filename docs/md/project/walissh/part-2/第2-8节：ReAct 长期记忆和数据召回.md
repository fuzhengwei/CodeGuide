---
title: 第2-8节：ReAct 长期记忆和数据召回
pay: https://t.zsxq.com/aR2e9
---

# 《WaLiSSH - AI Shell 智能终端》第2-8节：ReAct 长期记忆和数据召回

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/yPWni](https://t.zsxq.com/yPWni)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

你发现没，其实整个的开发过程。是搭建了骨架，填充了脉络，之后逐步迭代完善。这个也是软件开发的演进过程，不可能一下就全部写完，而是从能用到好用。行，继续了死鬼！

上一节，我们给 ReAct 引擎装上了"意图识别大脑"——规则 + LLM 级联分类 + 反馈回路纠偏。Agent 不仅能调工具了，还能先理解你要干什么，再决定调什么工具。

但你如果真的拿这套系统跑一段时间，会发现一个更根本的问题——**Agent 没有记忆**什么意思？

你跟 Agent 聊了二十轮，排查了一个 nginx 502 的问题，中间执行了十多条命令、发现了 redis 连接池打满的根因。然后服务重启了。再打开页面，发现对话全没了——从零开始。你之前排查的上下文、执行的命令、发现的结论，全部丢失。

更要命的是跨会话的问题。你上次跟 Agent 说"以后执行命令都帮我加 sudo"，Agent 在那个会话里确实记住了。但下次开新会话，它又忘了——你又得重新说一遍。

还有一个不太明显但很关键的问题。2-7 节的意图识别系统有 `ContextTracker`，维护会话级意图历史和任务态。但 `ContextTracker` 是内存的 `ConcurrentHashMap`，重启即丢。一旦服务重启，所有进行中的多步任务全部断档，"继续"指令找不到续接点。

这三个问题的根源是一样的：**ReAct擎的上下文体系是"内存态"的，没有持久化。**

所以这一节，我们要给 WaLiSSH 装上"数据库记忆"——把对话历史、里程碑事件、长期记忆全部落库，让 Agent 拥有跨重启、跨会话的记忆能力。

这一节做完，WaLiSSH 就从"金鱼脑记性的助手"——它记得你说过什么、做过什么，而且能把这些记忆用在未来的对话里。

## 一、本章诉求

本章的目标，是在 `walissh-server` 工程里，为 ReAct 引擎增加一套 **数据库持久化记忆** 能力，核心包括：

- **新建 5 张数据表**：`chat_session`（会话元数据）、`chat_message`（对话消息）、`chat_milestone`（里程碑事件）、`long_term_memory`（长期记忆）、`core_memory`（核心记忆预留）。
- **定义会话历史仓储接口**：`IChatHistoryRepository` 抽象出"保存会话、消息、查询历史、保存里程碑、查询里程碑"等能力。
- **定义长期记忆仓储接口**：`ILongTermMemoryRepository` 抽象出"或更新记忆、按用户查询近期记忆"两个能力。
- **实现长期记忆服务**：`LongTermMemoryService` 自动从对话流中提取 4 种结构化记忆——用户偏好、环境事实、软件版本、故障排查案例，并支持关键词召回。
- **新增长期记忆上下文提供者**：`LongTermMemoryProvider`（order=25）在 ReAct 循环中自动召回相关记忆，注入 Prompt 前缀 `[长期记忆]` 段落。
- **对话历史全链路库**：`RootNode` 冷启动从 DB 加载历史、`AiCallNode` 每轮保存 user/assistant 消息、`ToolCallNode` 保存工具执行结果。
- **里程碑持久化**：`MilestoneTracker` 检测到关键事件后，同时写入内存缓存和数据库。
- **会话列表与消息查询 API**：`IChatService` 新增 `querySessionList` / `queryMessageList`，为前端历史记录功能提供后端支撑。

回顾一下，别忘喽。2-5 是"给 AI 搭上下文骨架"，2-6 是"给框架装净化器"，2-7 是"给 AI 装理解大脑"，那么 2-8 就是"给 AI 装长期记忆"。

## 二、流程设计

这一节的架构变化，是在 ReAct 主链路的消息流转过程中，插入"持久化旁路"——每条消息在流经 ReAct 节点时，顺便写入数据库；同时新增 `LongTermMemoryProvider` 在构建 Prompt 时召回相关长期记忆。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walissh/walissh-2-8-01.png" width="950px">
</div>

一次完整的 ReAct 循环中，持久化记忆的流转如下：

1. **冷启动恢复**：`RootNode` 初始化时，从 `IChatHistoryRepository.getRecentMessages(sessionId, 50)` 加载最近 50 条历史消息，写入 `DynamicContext.messageHistory`。这样即使服务重启，对话上下2. **用户消息落库**：`AiCallNode` 在 `step == 0`（首轮）时，将用户原始消息写入 `chat_message` 表。
3. **长期记忆提取（用户侧）**：`AiCallNode` 调用 `longTermMemoryService.recordUserMessage()` 检测用户消息中是否包含偏好信号（"以后""默认""记住"等），如有则提取为 `USER_PREFERENCE` 记忆。
4. **Prompt 构建**：`ChatContextService.buildPromptContext()` 依次调用各 `ContextProvider`，其中 `LongTermMemoryProvider`（order=25）根据当前对话内容召回相关长期记忆，输出 `longTermMemorySummary`。
5. **长期记忆注入**：`longTermMemorySummary` 经 `PromptContextVO` → `DynamicPromptBuilder` 渲染为 `[长期记忆]` 段落，拼到用户消息前面。
6. **助手回复落库**：`AiCallNode` 在事件流处理完毕后，将累计的助手回复文本写入 `chat_message` 表，同时调用 `longTermMemoryService.recordAssistantConclusion()` 提取排查结论。
7. **工具结果落库**：`ToolCallNode` 在处理工具执行结果时，将结果写入 `chat_message` 表（role=tool），同时调用 `longTermMemoryService.recordToolObservation()` 从工具输出中提取环境信息、软件版本等事实性记忆。
8. **里程碑落库**：`MilestoneTracker.detectAndRecord()` 检测到关键事件时，同时写入内存缓存和 `chat_milestone` 表。

整套设计最关键的三个思想：

**一是"旁路写入，不阻塞主流程"**。所有持久化操作都是 try-catch 包裹的旁路操作——写库失败不影响 ReAct 循环。这是因为记忆持久化是"锦上添花"而非"生死攸关"的能力，不能因为 MySQL 挂了导致整个 Agent 不可用**二是"自动提取，无需人工标注"**。长期记忆不需要用户显式说"记住这个"——系统自动从对话流中提取结构化记忆。用户说"以后执行命令都加 sudo"，系统自动识别为 `USER_PREFERENCE`；工具执行 `redis-server --version` 返回 `Redis 7.0.11`，系统自动提取为 `SOFTWARE_FACT`。

**三是"关键词召回，不依赖向量"**。长期记忆召回用的是关键词重叠打分，不是向量相似度。这样设计的好处是零外部依赖（不需要 Embedding API、不需要向量数据库），纯 MySQL + 关键词匹配就能实现。对于 SSH 运维场景，关键词召回的精度已经够用——运维对话的关键词（nginx、redis、502、timeout）天然具有高区分度。

> AI Agent 的记忆不是"把所有东西都记下来"，而是"在对话流中自动提取有价值的结构化信息，并在需要时精准召回"。
