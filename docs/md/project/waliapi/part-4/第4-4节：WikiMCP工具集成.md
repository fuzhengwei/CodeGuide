---
title: 第4-4节：Wiki MCP 工具集成
pay: https://t.zsxq.com/DwMTV
---

# 《WaLiAPI - 本地 LLM API 网关》第4-4节：Wiki MCP 工具集成

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

part-3-6 我们已经搭建了 MCP Server（Streamable HTTP + SSE，JSON-RPC 分发）。这一节把 Wiki 的全部能力封装成 **MCP 工具**，让 AI Agent（如 Claude、脚本、其他应用）能通过 MCP 协议直接读写 Wiki。

MCP 工具是 Wiki 的第三种入口——HTTP 路由给 Web 客户端用，Tauri 命令给前端用，MCP 工具给 AI Agent 用。三种入口共享同一套业务逻辑。

## 一、本章诉求

1. 在 MCP Server 的 `tools/list` 中定义 16 个 Wiki 工具（名称 + description + inputSchema）
2. 实现 `tools/call` 工具分发：根据工具名路由到对应 Wiki handler
3. 复用已有业务逻辑（handlers.rs），MCP 工具只做薄封装（参数提取 → 调用 → 格式化响应）
4. 掌握工具参数校验与错误处理（MCP 响应格式：content 数组 + isError 标志）

## 二、MCP 工具总览

### 2.1 工具分类

| 分类 | 工具数 | 工具列表 |
|------|--------|---------|
| 项目管理 | 4 | `list_wiki_projects`、`get_wiki_project`、`create_wiki_project`、`delete_wiki_project` |
| 页面操作 | 4 | `list_wiki_pages`、`get_wiki_page`、`save_wiki_page`、`delete_wiki_page` |
| 搜索问答 | 2 | `search_wiki`、`ask_wiki` |
| 标签图谱 | 2 | `get_wiki_tags`、`get_wiki_graph` |
| 源资料管理 | 4 | `list_wiki_sources`、`ingest_wiki_source`、`add_wiki_source`、`delete_wiki_source` |

### 2.2 查询类 vs 管理类

| 类型 | 工具 | 说明 |
|------|------|------|
| **查询类（只读）** | list_wiki_projects、get_wiki_project、list_wiki_pages、get_wiki_page、search_wiki、ask_wiki、get_wiki_tags、get_wiki_graph、list_wiki_sources | 不修改数据，AI 可安全调用 |
| **管理类（写入）** | save_wiki_page、delete_wiki_page、create_wiki_project、delete_wiki_project、ingest_wiki_source、add_wiki_source、delete_wiki_source | 修改数据，需要谨慎 |
