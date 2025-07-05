---
title: 【更】第3-7节：动态实例化客户端API
pay: https://t.zsxq.com/RdjkP
---

# 《Ai Agent》第3-7节：动态实例化客户端API

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/4r5r4](https://t.zsxq.com/4r5r4)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

完善数据加载操作，动态实例化`客户端API`（ai_client_api）并注册到 Spring 容器。

这是整个 armory 动态装配 Ai Agent 节点的第一步，涉及到了数据的获取，对象的创建和 Spring 容器的 Bean 对象注册。能看懂本节的操作，基本后续一直到整个 Ai Agent 构建也就都可以看懂了。

## 二、功能流程

如图，客户端API实例化过程设计；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-7-01.png" width="650px">
</div>

- 首先，整个 AI Agent 的实例化过程，就是各项组件的创建和组装的过程。为了让整体的实现代码更易于维护，我们把这样的创建过程，通过 规则树的方式 进行串联实现。这种设计模式的优势在于：模块化设计、易于扩展、代码复用度高。
- 之后，从开始节点看，依次执行，数据构建节点、API构建节点。在 API 构建的过程中，会检查上下文中是否存在已经从数据库获取的数据，之后依次循环构建并注册到 Spring 容器。
