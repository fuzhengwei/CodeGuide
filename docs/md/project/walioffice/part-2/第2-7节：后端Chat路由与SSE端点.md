---
title: 第2-7节：后端Chat路由与SSE端点
pay: https://t.zsxq.com/qCOE8
---

# 《WaLiOffice - AI Agent 智能办公平台》第2-7节：后端Chat路由与SSE端点

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

前两节我们搞定了 ReAct 循环和意图识别——Agent 能思考、能选工具、能识别用户意图。但这些都还是"零件"，用户发一条消息后，**谁来把意图识别结果喂给 Agent 循环？谁来把 Agent 产生的事件推给前端？谁来管会话历史和产物存储？**

答案就是——**Chat 路由（`/api/chat/stream`）**。

这是整个后端的"总调度室"：一次 HTTP 请求进来，它要完成 **意图分析 → 会话管理 → 历史加载 → Agent 启动 → SSE 推流 → 产物落盘 → 消息持久化** 这一整条链路。上一节我们写的 `chat_stream` 还只是个返回"功能开发中"的 stub，本节把它变成真正的**流式对话端点**。

**但你想过没有**：为什么不直接用普通的 JSON 响应（`return Json(data)`），而要用 SSE（Server-Sent Events）？

因为 Agent 的执行是**异步多阶段**的——思考 → 调工具 → 出结果 → 再思考 → 再调工具……每一步都需要实时推给前端。如果用普通 HTTP，用户只能等到所有步骤完成才能看到结果，中间干等着；用 SSE，用户能实时看到"Agent 正在思考""正在调用 PPT 工具""产物已生成"。

## 一、本章诉求

1. 理解 `chat_stream` 处理函数的完整流程（从请求到 SSE 响应）
2. 掌握 IntentAnalyzer 接入 Agent 循环的集成方式
3. 学会 AgentEvent → SSE Event 的转换协议设计
4. 理解会话管理（创建/获取/历史加载/消息持久化）
5. 掌握产物落盘策略（docx/xlsx/pptx/drawio/图片/视频自动保存到文件系统）

## 二、流程设计

### 2.1 Chat 路由整体流程

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walioffice/walioffice-2-7-01.png" width="950px">
</div>

整个 `chat_stream` 函数从接收 HTTP 请求到 SSE 流结束，执行流程如下：

```
POST /api/chat/stream (AuthUser + ChatRequest)
        ↓
① 意图识别 — IntentAnalyzer.analyze(message, session_id, has_image)
        ↓
② 创建 LLM 客户端 — LlmClient::for_user(user_id, model)
        ↓
③ 会话管理 — 有 session_id 就加载，没有就创建新会话
        ↓
④ 加载历史消息 + 已有产物 — session_repo.get_messages / get_artifacts
        ↓
⑤ 附件处理 — save_chat_attachments_to_files（图片/文本自动存储）
        ↓
⑥ 保存用户消息 — session_repo.add_message
        ↓
⑦ 构建意图注入 — build_intent_context_addition → AgentConfig.system_prompt
        ↓
⑧ 启动 Agent 循环 — run_agent_loop(history, message, config)
        ↓
⑨ SSE 事件转发 — tokio::spawn 异步任务消费 AgentEvent → SSE Event
        ↓
⑩ 产物落盘 + 持久化 — save_generated_artifact_to_files + session_repo
        ↓
Sse<ReceiverStream> 返回给客户端
```

