---
title: 第2-2节：LLM客户端与流式SSE实现
pay: https://t.zsxq.com/Gkz23
---

# 《WaLiOffice - AI Agent 智能办公平台》第2-2节：LLM客户端与流式SSE实现

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

上一节我们把 WaLiOffice 的工程架子搭好了——后端能启动，前端能访问，日志正常打印。但现在服务里什么功能都没有，**就是个空壳**。所以从这一节开始，我们逐步往里填功能。

第一个要填的，就是 **LLM 客户端**。整个 WaLiOffice 的"大脑"就是 LLM——用户说一句话，Agent 调用 LLM 理解意图、决定工具、生成内容。没有 LLM 客户端，后面的 ReAct 循环、工具调用、产物生成全都是空中楼阁。

**但你想过没有**：LLM 调用封装为什么这么复杂？不就是调个 API 吗？——没那么简单。实际工程中有这些问题需要解决：

1. **多 Key 轮询**：一个 API Key 限速了怎么办？要能自动换 Key
2. **流式 SSE**：用户输入要"实时看到 AI 在打字"，不能等 AI 全部生成完再返回
3. **模型选择**：不同用户、不同任务，可能需要不同的模型
4. **容错重试**：LLM 服务不稳定（401/403/429/5xx），要自动重试
5. **视觉输入**：用户上传图片，要支持多模态（图文混合消息）

这些问题，这节全部解决。

## 一、本章诉求

1. 理解 `LlmClient` 非流式调用封装（多 Key 轮询 + 模型选择 + 重试策略）
2. 掌握 `LlmStreamClient` 流式调用封装（SSE 事件解析 + channel 传递）
3. 学会 `StreamEvent` 枚举的设计（Delta / ToolCallDelta / Done）
4. 理解 `extract_json` 容错解析（去 markdown fence + 截取花括号）
5. 理解 `is_chat_compatible_model` 模型兼容性判断

## 二、流程设计

### 2.1 LLM 客户端分层架构

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walioffice/walioffice-2-2-01.png" width="950px">
</div>

WaLiOffice 的 LLM 调用封装分为**两个客户端**：

| 客户端 | 用途 | 特点 |
|--------|------|------|
| `LlmClient` | 非流式调用（ReAct 循环中调用工具后的总结） | 支持多 Key 轮询、模型选择、工具调用 |
| `LlmStreamClient` | 流式调用（前端 SSE 推送） | 返回 `mpsc::Receiver<StreamEvent>` channel |

两者共享同样的 HTTP 请求逻辑（Key 轮询、重试），区别在于：
- `LlmClient`：等待完整响应，返回 `ChatCompletionResponse`
- `LlmStreamClient`：实时解析 SSE 事件，通过 channel 逐个推送 `StreamEvent`
