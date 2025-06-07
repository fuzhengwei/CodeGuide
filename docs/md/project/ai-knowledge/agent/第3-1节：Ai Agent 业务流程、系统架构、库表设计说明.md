---
title: 【更】第3-1节，Ai Agent 业务流程、系统架构、库表设计说明
pay: https://t.zsxq.com/qUYx0
---

# 《Ai Agent》第3-1节，Ai Agent 业务流程、系统架构、库表设计说明

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/DcL2p](https://t.zsxq.com/DcL2p)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

了解 Ai Agent 构建元素，以 Spring AI 硬编码创建 Agent 过程，分析各个模块用途，并以此设计拆分元素和对应的库表设计。

经过本节我们可以了解到，怎么把这些硬编码的过程，以配置数据库表的方式，动态化构建。有了动态化的构建，也就可以按需配置出各种 Agent 服务来满足我们的业务诉求。尤其是现在 MCP 如火如荼的发展，有一套自动化的 Agent 是非常重要的。

> 第3-0节，介绍和演示中，涉及了本节的部分内容。本节主要站在开发视角，来讲解如何架构和开发系统。

## 二、Agent 介绍

AI 智能体是使用 AI 来实现目标并代表用户完成任务的软件系统。其表现出了推理、规划和记忆能力，并且具有一定的自主性，能够自主学习、适应和做出决定。

这些功能在很大程度上得益于生成式 AI 和 AI 基础模型的多模态功能。AI 智能体可以同时处理文本、语音、视频、音频、代码等多模态信息；可以进行对话、推理、学习和决策。它们可以随着时间的推移不断学习，并简化事务和业务流程。智能体可以与其他智能体协作，来协调和执行更复杂的工作流。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-1-01.png" width="500px">
</div>

>Spring AI 框架，支持大语言模型构建 AI Agent 实现。AI Agent是整合多种技术手段的智能实体 ，其实现依赖于 Tools、MCP、Memory、RAG（Retrieval 增强检索生成） 等技术组件，但不是非得依赖全部组件才叫 AI Agent。
