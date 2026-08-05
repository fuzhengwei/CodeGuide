---
title: 第3-4节：向量化与HNSW索引
pay: https://t.zsxq.com/wBHou
---

# 《WaLiAPI - 本地 LLM API 网关》第3-4节：向量化与HNSW索引

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

上一节完成了数据模型和文档解析——文档被切成 chunk 存进了数据库。这一节让 chunk "活起来"：**向量化 + 索引构建 + 全文检索**，为下一节的混合检索和 RAG 问答打下基础。

## 一、本章诉求

1. 实现 embedder 模块——复用 WaLiAPI 的渠道调度能力调用 Embeddings API
2. 实现轻量级 HNSW 索引——构建/搜索/持久化/增量更新
3. 实现 processor 流水线——文档处理的完整生命周期
4. 实现 importer 多源导入——Git/URL/本地目录
5. 实现 FTS5 全文索引——为混合检索提供关键词搜索能力
6. 理解向量索引 + FTS5 混合检索的设计动机

## 二、向量化（embedder.rs）

### 2.1 为什么复用渠道调度？

知识库需要调用 Embeddings API 将文本转为向量。但 WaLiAPI 本身就是一个 API 网关，已有完善的渠道调度（Dispatcher）、重试机制、多渠道 fallback。**为什么不直接用这些能力？**

```
┌─────────────────────────────────────────────────────┐
│            embedder 调用流程                          │
│                                                      │
│  texts → embed() → get_enabled_channels()            │
│         → Dispatcher::select_channels(model)          │
│         → try_embed_with_channel() → channel 1       │
│         → 失败？ → try next channel → channel 2      │
│         → 成功 → Vec<Vec<f32>>                        │
│                                                      │
│  不需要额外配置，复用用户已有的渠道设置                  │
└─────────────────────────────────────────────────────┘
```

### 2.2 embed 函数

```rust
pub async fn embed(
    texts: &[String],
    model: &str,
    repo: &Repository,
) -> Result<Vec<Vec<f32>>, String> {
    // 1. 获取启用的渠道
    let channels = repo.get_enabled_channels().await...;
    // 2. 选择支持该模型的渠道
    let selected = Dispatcher::select_channels(&channels, model);
    let candidates = if selected.is_empty() { channels.clone() } else { selected };
    // 3. 逐个尝试
    for channel in &candidates {
        match try_embed_with_channel(texts, model, channel).await {
            Ok(embeddings) => return Ok(embeddings),
            Err(e) => continue, // fallback to next channel
        }
    }
    Err("All channels failed for embedding model".to_string())
}
```

