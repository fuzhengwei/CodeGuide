---
title: 【更】第5节：Ollama RAG 知识库上传、解析和验证
pay: https://t.zsxq.com/fTK4R
---

# 《DeepSeek RAG 知识库》第5节：Ollama RAG 知识库上传、解析和验证

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/PM15B](https://t.zsxq.com/PM15B)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

以大模型向量存储的方式，提交本地文件到知识库。并在 AI 对话中增强检索知识库符合 AI 对话内容的资料，合并提交问题。

## 二、技术方案

以 Spring AI 提供的向量模型处理框架，将上传文件以 TikaDocumentReader 方式进行解析，再通过 TokenTextSplitter 拆分文件。完成这些操作后，在遍历文档添加标记。标记的作用是为了可以区分不同的知识库内容。完成这些动作后，把这些拆解并打标的文件存储到 postgresql 向量库中。

本技术方案旨在利用 Spring AI 提供的向量模型处理框架，对上传的文件进行解析、拆分、标记，并将处理后的数据存储到 PostgreSQL 向量库中。通过这一流程，可以实现对文件内容的高效管理和检索，特别是在需要区分不同知识库内容的场景下。

### 1. 技术组件

- **Spring AI**: 提供向量模型处理框架，支持文件的解析、拆分和向量化操作。
- **TikaDocumentReader**: 用于解析上传的文件，支持多种文件格式（如 MD、TXT、SQL 等）。
- **TokenTextSplitter**: 用于将解析后的文本内容拆分为更小的片段，便于后续处理和存储。
- **PostgreSQL 向量库**: 用于存储处理后的文本向量数据，支持高效的相似性搜索和检索。

### 2. 方案流程

#### 2.1 文件上传与解析

1. **文件上传**: 用户通过前端界面或 API 上传文件，文件可以是多种格式（如 MD、TXT、SQL 等）。
2. **文件解析**: 使用 `TikaDocumentReader` 对上传的文件进行解析，提取出文本内容。`TikaDocumentReader` 能够处理多种文件格式，并提取出结构化的文本数据。