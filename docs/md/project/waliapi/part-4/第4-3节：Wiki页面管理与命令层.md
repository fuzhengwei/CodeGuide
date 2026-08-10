---
title: 第4-3节：Wiki 页面管理与命令层
pay: https://t.zsxq.com/jftYn
---

# 《WaLiAPI - 本地 LLM API 网关》第4-3节：Wiki 页面管理与命令层

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

摄入管道能把文档变成结构化页面，但要让前端真正用起来，还需要一套**页面管理与命令层**：HTTP 路由 + handlers 业务处理 + Tauri 命令。这一节把 Wiki 的读写能力完整暴露出来，形成"三种入口共享一份业务逻辑"的架构。

## 一、本章诉求

1. 实现 handlers.rs：项目 CRUD、源管理、页面 CRUD、搜索、问答、图谱、会话、队列
2. 实现 routes.rs：Wiki HTTP 路由表（20+ 路由）
3. 实现 update_page_inner / ask_inner：可复用的核心业务逻辑
4. 实现 commands/wiki.rs：Tauri 命令层（前端 invoke 调用）

## 二、三层入口架构

Wiki 的业务逻辑被设计为**三种入口共享一份实现**：

```
┌──────────────────────────────────────────────────┐
│              三种调用入口                          │
│                                                   │
│  HTTP 路由        Tauri 命令        MCP 工具      │
│  (routes.rs)     (commands/wiki.rs) (mcp/handlers)│
│      │                │                │          │
│      ▼                ▼                ▼          │
│  handlers.rs ──▶ update_page_inner / ask_inner   │
│                   （共享业务逻辑层）                │
│      │                │                │          │
│      ▼                ▼                ▼          │
│  repository.rs ──▶ project.rs ──▶ ingest.rs       │
│                   （存储 + 文件 + 摄入）            │
└──────────────────────────────────────────────────┘
```

HTTP handler、Tauri 命令、MCP 工具（4-4 节）都调用同一套 `update_page_inner` / `ask_inner` 核心逻辑，避免重复实现。
