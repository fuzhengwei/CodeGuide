---
title: 第3-5节：知识库检索与RAG问答
pay: https://t.zsxq.com/3ch0g
---

# 《WaLiAPI - 本地 LLM API 网关》第3-5节：知识库检索与RAG问答

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

上一节完成了向量化、HNSW索引和FTS5全文检索的基础设施。这一节把它们组合起来——**检索引擎 + RAG问答**。这是知识库的"输出端"，用户提问后，系统如何检索相关内容并生成答案。

## 一、本章诉求

1. 实现 retriever 模块——HNSW向量搜索 + FTS5关键词搜索 + 混合融合 + 符号过滤
2. 实现 rag 模块——多轮对话 + Token 限制降级 + 来源引用
3. 实现 handlers + routes——HTTP API 端点
4. 实现 knowledge service 注册——Service trait 实现
5. 实现 commands 层——Tauri 命令接口
6. 理解检索 → 生成 的完整链路设计

## 二、Retriever 模块

### 2.1 检索能力层次

```
┌─────────────────────────────────────────────────────────┐
│                  Retrieval Capabilities                  │
│                                                          │
│  ┌──────────────────┐  ┌──────────────────┐             │
│  │  Vector Search    │  │  FTS5 Search     │             │
│  │  (语义相似度)     │  │  (关键词匹配)     │             │
│  │  cosine distance  │  │  rank scoring    │             │
│  └──────────────────┘  └──────────────────┘             │
│           │                     │                         │
│           └──────┬──────────────┘                         │
│                  │                                        │
│          ┌───────▼───────┐                                │
│          │  Hybrid Search │                                │
│          │  加权融合       │                                │
│          │  0.7 * vector  │                                │
│          │  + 0.3 * fts5  │                                │
│          └────────────────┘                                │
│                  │                                        │
│          ┌───────▼───────┐                                │
│          │  Symbol Filter │                                │
│          │  (代码检索增强) │                                │
│          │  symbol_kind   │                                │
│          │  symbol_name   │                                │
│          └────────────────┘                                │
│                                                          │
│  输出: SearchResult[] (chunk_id, content, score, meta)   │
└─────────────────────────────────────────────────────────┘
```

### 2.2 search — 单知识库搜索

```rust
pub async fn search(
    pool: &SqlitePool,
    kb_id: &str,
    query_embedding: &[f32],
    top_k: usize,
) -> Result<Vec<SearchResult>, String> {
    let repo = KbRepository::new(pool.clone());

    // 1. 优先使用 HNSW 索引
    if let Some(index) = load_index(kb_id) {
        if index.dim == query_embedding.len() {
            let hnsw_results = index.search(query_embedding, top_k);
            // 将 HNSW 的 position id 映射回 chunk_id
            let chunks = repo.get_chunks_by_kb(kb_id).await?;
            let mapped = hnsw_results.iter().map(|r| {
                // position → chunk index → chunk data → SearchResult
            }).collect();
            return Ok(mapped);
        }
    }

    // 2. Fallback: 线性扫描（无索引或维度不匹配时）
    let chunks = repo.get_chunks_by_kb(kb_id).await?;
    let mut results: Vec<SearchResult> = chunks.iter()
        .filter_map(|chunk| {
            // 从 BLOB 解码 embedding
            let embedding = decode_embedding(&chunk.embedding, chunk.embedding_dim)?;
            let score = cosine_similarity(query_embedding, &embedding);
            Some(SearchResult { chunk_id: chunk.id, ... score, ... })
        })
        .collect();
    results.sort_by(|a, b| b.score.partial_cmp(&a.score).unwrap_or(Ordering::Equal));
    Ok(results.into_iter().take(top_k).collect())
}
```

