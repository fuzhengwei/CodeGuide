---
title: 第4-2节：Wiki 文档摄入管道
pay: https://t.zsxq.com/0HVMp
---

# 《WaLiAPI - 本地 LLM API 网关》第4-2节：Wiki 文档摄入管道

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

上一节搭好了 Wiki 的存储骨架，这一节实现最核心的**摄入管道（ingest）**——把一份原始文档自动转化成带 frontmatter、标签、wikilinks 的结构化 Wiki 页面。这是 Wiki "结构化知识" 能力的来源，也是 Wiki 区别于 RAG 的关键：RAG 把文档"拆碎"做向量搜索，Wiki 把文档"提炼"成结构化页面做知识管理。

## 一、本章诉求

1. 实现摄入流水线主函数 `ingest_source`（9 步：加载→任务→读取→解析→LLM 生成→写盘→图谱→状态→日志）
2. 实现多格式文档解析 `parse_content`（Markdown / 纯文本 / JSON）
3. 实现 LLM 结构化页面生成 `generate_wiki_pages`（system prompt + proxy 调用）
4. 实现页面解析 `parse_generated_pages`（`---PAGE---` 分隔符协议）
5. 实现 wikilinks / tags 提取与知识图谱构建
6. 实现摄入进度事件推送到前端

## 二、摄入流水线全貌

摄入是一个 **读取 → 解析 → LLM 结构化 → 提取 → 写盘 → 建图** 的流水线：

```
wiki_sources (原始文档)
   │
   ① 加载源文件记录
   ② 创建摄入任务（status=pending）
   ③ 读取文件内容（磁盘 / raw/sources/）
   ④ 解析文档（parse_content → sections）
   ⑤ LLM 结构化生成（generate_wiki_pages → pages）
   ⑥ 写入页面到磁盘 + DB（upsert_page）
   ⑦ 提取 wikilinks → 构建知识图谱边
   ⑧ 更新源状态（ingested）+ 项目计数
   ⑨ 更新任务状态（done）+ 推送进度事件
   ▼
wiki_pages (结构化页面) + wiki_graph_edges (知识图谱)
```
