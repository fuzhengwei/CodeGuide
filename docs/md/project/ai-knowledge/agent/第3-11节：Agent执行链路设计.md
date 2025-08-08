---
title: 【更】第3-11节：Agent执行链路设计
pay: https://t.zsxq.com/eSLoH
---

# 《Ai Agent》第3-11节：Agent执行链路设计

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/hNFqE](https://t.zsxq.com/hNFqE)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

将上一节对 Ai Agent 执行链路的分析，以及对应的 AutoAgentTest 测试代码，使用规则树设计可执行链路节点。

本节是其中的一个 Ai Agent Auto 自动执行策略，后续还要把其他的 Ai Agent 执行策略也加入进来实现。

## 二、流程设计

如图，Auto Ai Agent 动态多轮会话执行流程图；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-11-01.png" width="800px">
</div>

- 首先，给入口保留一个多策略选择，以适应我们不同场景的多类型 Agent 选择使用，后续会在 agent 配置表增加策略选择属性来区分调用。本节我们先处理一个 AutoAgent 的实现。
- 之后，进入到关键地方，在上一节 AutoAgentTest 章节，设计了一套自动化 Agent 执行方法，通过 for 循环处理。这里我们通过规则树，分多个多个节点步骤执行，节点间可循环调用，增强整体的灵活性。
- 最后，以用户提问到所有的步骤执行完成后，进入到结束环节，产生结果。如果你上一节已经高透彻，那么到这里其实会更加容易理解对于节点的拆分。