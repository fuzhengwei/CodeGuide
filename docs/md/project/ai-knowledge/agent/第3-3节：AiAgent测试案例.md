---
title: 【更】第3-3节：Ai Agent 测试案例
pay: https://t.zsxq.com/JFnzV
---

# 《Ai Agent》第3-3节：Ai Agent 测试案例

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/jI0BD](https://t.zsxq.com/jI0BD)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

在项目中引入 Spring Ai 1.0.0 框架，通过编写测试案例的方式，了解 Ai Agent 的工作模式。

**那为什么要这么做呢？**

通常对于软件设计的解决方案，我们都有一个这样共识，那就是目标结果驱动，最先搭建可运行的最小执行单元。因为软件设计原则，[康威定律](https://zh.wikipedia.org/wiki/%E5%BA%B7%E5%A8%81%E5%AE%9A%E5%BE%8B)，也提到，`大的系统组织总是比小系统更倾向于分解`。当场景问题被拆解的越小以后，也就越容易被理解和处理。所以，我们要优先通过案例的方式，验证 Ai Agent 的工作模型和可执行方案。再通过这些案例，设计详细的流程和库表细节。

## 二、功能流程

如图，为整个 Ai Agent 的工作模型；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-0-01.png" width="750px">
</div>

- 概念：**Ai Agent 是整合多种技术手段的智能实体** ，其实现依赖于 Tools、MCP、Memory、RAG（Retrieval-Augmented Generation，检索增强生成） 等技术组件构建的智能体。并且每一个 Agent Client 又可以被连接通信，增强其 Agent 智能体能力。
- 方案：这里我们基于 Spring AI 框架，通过编码的方式把模型、关键词、顾问角色、工具，放入到 LLM 客户端，构建 LLM 对话智能体。