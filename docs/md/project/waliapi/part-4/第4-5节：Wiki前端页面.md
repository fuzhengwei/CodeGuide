---
title: 第4-5节：Wiki 前端页面
pay: https://t.zsxq.com/jftYn
---

# 《WaLiAPI - 本地 LLM API 网关》第4-5节：Wiki 前端页面

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

前四节完成了 Wiki 的后端全链路（存储 → 摄入 → 页面管理 → MCP）。这一节做收尾——**前端 Wiki 页面**，让用户在桌面上可视化地管理项目、浏览页面、搜索问答、看标签与知识图谱。

## 一、本章诉求

1. 在 KnowledgeBasePage 中实现 WikiSection 组件（项目列表 + 项目详情）
2. 实现 6 类视图切换（overview/pages/sources/search/graph/settings）
3. 实现 WikiMarkdown 渲染器（frontmatter 剥离 + 语法高亮 + wikilinks）
4. 实现 CreateWikiProjectModal 创建项目弹窗
5. 在 api.ts 封装 Wiki 接口（17 个方法对接 Tauri 命令）
6. Sidebar 增加 Wiki 导航，Dashboard 增加 Wiki 统计

## 二、前端架构总览

Wiki 前端嵌入在 KnowledgeBasePage 中，与知识库（RAG）共享同一页面，通过 Tab 切换：

```
KnowledgeBasePage
  ├── Tab: knowledge  → KnowledgeBaseSection (RAG 知识库)
  ├── Tab: wiki       → WikiSection (Wiki 知识图谱)  ← 本节重点
  ├── Tab: mcp        → McpSection
  └── Tab: skills     → SkillsSection
```

WikiSection 内部组件树：

```
WikiSection
  ├── 项目列表视图
  │   ├── 项目卡片（名称/统计/状态/MCP开关）
  │   └── CreateWikiProjectModal
  │
  └── 项目详情视图 (WikiProjectDetail)
      ├── Tab: overview   → 统计仪表盘
      ├── Tab: pages      → 页面列表 + WikiMarkdown 渲染
      ├── Tab: sources    → 源资料列表 + 摄入操作
      ├── Tab: search     → 搜索 + 问答
      ├── Tab: graph      → 知识图谱可视化
      └── Tab: settings   → 项目配置
```
