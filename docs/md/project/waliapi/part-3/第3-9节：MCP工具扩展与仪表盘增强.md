---
title: 第3-9节：MCP工具扩展与仪表盘增强
pay: https://t.zsxq.com/jftYn
---

# 《WaLiAPI - 本地 LLM API 网关》第3-9节：MCP工具扩展与仪表盘增强

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

3-8 节我们把检索能力做成了"可调参、可视化"。这一节做两件收尾的事：一是把这套检索参数**开放给 MCP 工具**，让外部 AI Agent（Claude Desktop、Cursor）也能享受混合检索调优；二是**增强仪表盘和前端体验**，并解决一个真实的版本升级坑——数据库迁移 checksum 不兼容。

## 一、本章诉求

1. 扩展 MCP `search_knowledge_base` 工具——支持 search_mode / vector_weight / keyword_weight
2. 扩展 MCP `ask_knowledge_base` 工具——支持检索配置 + 输出 Retrieval Details
3. 增强 DashboardPage——6 卡片扩展为 8 卡片，新增知识库统计
4. 增强 KnowledgeBasePage——本地目录浏览按钮、MCP 路由直达
5. 修复迁移 checksum 兼容问题——v0.1.1 用户升级 v0.1.3 不报 VersionMismatch
6. 版本打包——Cargo.toml / tauri.conf.json / package.json 升级到 0.1.4

## 二、MCP search_knowledge_base 工具扩展

### 2.1 工具描述与入参 Schema

MCP 工具通过 `get_tools()` 返回 JSON Schema 定义。v0.1.4 给 `search_knowledge_base` 增加三个检索参数，并更新工具描述（让 Agent 知道支持混合检索）：

```rust
serde_json::json!({
    "name": "search_knowledge_base",
    "description": "Search across a local knowledge base using hybrid (vector + keyword), vector-only, or keyword-only retrieval. Returns matching text chunks with similarity scores and per-component score breakdowns.",
    "inputSchema": {
        "type": "object",
        "properties": {
            "query": { "type": "string", "description": "Natural language search query." },
            "kb_id": { "type": "string", "description": "Specific knowledge base ID..." },
            "top_k": { "type": "integer", "default": 5 },
            "search_mode": {
                "type": "string",
                "enum": ["hybrid", "vector", "keyword"],
                "description": "Retrieval mode: hybrid (default), vector (semantic only), keyword (FTS5 only). CJK bigram tokenization is used for Chinese queries.",
                "default": "hybrid"
            },
            "vector_weight": {
                "type": "number",
                "description": "Weight for vector similarity score in hybrid mode (0.0-1.0, default: 0.7). Only effective when search_mode=hybrid.",
                "default": 0.7
            },
            "keyword_weight": {
                "type": "number",
                "description": "Weight for keyword (FTS5) score in hybrid mode (0.0-1.0, default: 0.3). Only effective when search_mode=hybrid.",
                "default": 0.3
            }
        },
        "required": ["query"]
    }
})
```

> **要点**：`description` 是写给 Agent 看的"使用说明"。明确告知"CJK bigram 用于中文查询""权重仅在 hybrid 生效"，Agent 才能正确构造参数。MCP 工具的 description 本质上是一种**提示工程**。

