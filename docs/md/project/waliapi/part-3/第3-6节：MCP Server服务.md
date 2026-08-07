---
title: 第3-6节：MCP Server服务
pay: https://t.zsxq.com/cEXT6
---

# 《WaLiAPI - 本地 LLM API 网关》第3-6节：MCP Server服务

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

前面5节完成了知识库的"内部能力"——数据模型、解析分块、向量化索引、混合检索、RAG问答。这一节把这些能力**通过 MCP 协议对外暴露**，让任意 AI Agent（Claude Desktop、Cursor、Copilot 等）能直接调用知识库工具。

## 一、本章诉求

1. 理解 MCP（Model Context Protocol）的设计目标和传输机制
2. 实现 Streamable HTTP + SSE 双传输通道
3. 实现 13 个 MCP 工具定义（搜索/问答/CRUD/导入/索引管理）
4. 实现 instructions 注入（Agent 首次连接时的系统提示）
5. 实现 McpService 注册到 ServiceRegistry
6. 理解前端 KnowledgeBasePage 与 MCP 的交互方式

## 二、MCP 协议概述

### 2.1 MCP 是什么？

MCP（Model Context Protocol）是 Anthropic 在 2024 年推出的开放协议，让 AI 模型通过标准化接口与外部工具交互。核心概念：

```
┌─────────────────────────────────────────────────────┐
│              MCP Architecture                         │
│                                                      │
│  MCP Host (Claude Desktop / Cursor / AI Agent)       │
│       │                                              │
│       │  JSON-RPC 2.0 over HTTP                      │
│       │                                              │
│  MCP Client (内置在 Host 中)                          │
│       │                                              │
│       │  SSE / Streamable HTTP                       │
│       │                                              │
│  MCP Server (WaLiAPI 知识库服务)                      │
│       │                                              │
│       │  13 tools:                                   │
│       │  search / ask / list_kb / create_kb / ...    │
│       │                                              │
│  本地资源 (SQLite + HNSW 索引)                        │
└─────────────────────────────────────────────────────┘
```

### 2.2 MCP 传输方式

MCP 支持两种传输方式：

| 传输方式 | 连接方向 | 适用场景 |
|----------|---------|---------|
| **Streamable HTTP** | Client → Server (POST JSON-RPC) | 所有请求/响应 |
| **SSE** | Server → Client (GET 事件流) | 长连接、推送通知 |

**WaLiAPI 的实现**：同时支持两种传输，通过同一组路由暴露：

```
POST /mcp          → JSON-RPC 请求/响应（Streamable HTTP）
GET  /mcp/sse      → SSE 事件流（Server → Client 推送）
POST /mcp?session_id=xxx → SSE 模式下的 JSON-RPC 请求
```

