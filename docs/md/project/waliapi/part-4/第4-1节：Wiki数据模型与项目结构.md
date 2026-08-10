---
title: 第4-1节：Wiki 数据模型与项目结构
pay: https://t.zsxq.com/jftYn
---

# 《WaLiAPI - 本地 LLM API 网关》第4-1节：Wiki 数据模型与项目结构

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

进入第 4 部分，我们开始构建 **Wiki** 能力。如果说 part-3 的知识库（RAG）解决的是"找原始片段"——把文档切块向量化后做语义检索，那么 Wiki 解决的是"看结构化知识"——文档摄入后自动生成带 frontmatter、标签、wikilinks 的**结构化页面**，并能编织成知识图谱。

两者互补：RAG 适合跨文档的语义问答，Wiki 适合结构化浏览、精确页面检索与标签导航。这一节先打地基：数据模型与项目结构。

## 一、本章诉求

1. 设计 Wiki 的数据库表结构（migration 017，七张表 + migration 018 标签列）
2. 实现 Rust 数据模型（models.rs — 项目/页面/源/任务/会话/搜索/问答/图谱/标签）
3. 实现项目目录管理（project.rs — 磁盘文件系统布局）
4. 实现数据库 CRUD（repository.rs — 全表读写封装）
5. 注册 WikiService 服务骨架（mod.rs），接入 part-3-2 的服务注册框架

## 二、Wiki 与 RAG 的定位差异

| 能力域 | 说明 | 数据形态 | 适合场景 |
|--------|------|---------|---------|
| Knowledge Base (RAG) | 文档分块 → 向量化 → 语义检索 | chunk + 向量 | 原始片段搜索、跨文档问答 |
| Wiki | 文档摄入 → 结构化页面 → 知识图谱 | page + frontmatter + wikilinks | 结构化浏览、精确检索、标签导航 |

RAG 把文档拆成"碎片"做向量搜索，Wiki 把文档"提炼"成结构化页面做知识管理。一个是找片段，一个是看整理好的知识。
