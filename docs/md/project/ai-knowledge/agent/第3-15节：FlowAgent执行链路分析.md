---
title: 【更】第3-15节：AgentFlow执行链路分析
pay: https://t.zsxq.com/Ht0o1
---

# 《Ai Agent》第3-15节：AgentFlow执行链路分析

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/u9tjH](https://t.zsxq.com/u9tjH)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

为了打开 Agent 的实现思路，本章我们再增加一种新的 Auto Agent 设计，这种设计方式以通过用户的提问和当前 Agent 配置的 MCP 工具集合，进行执行步骤的规划设计。之后在通过执行步骤按照拆分的步骤顺序号，依次进行执行。有点类似于 [manus](https://manus.im/)  的过程。

## 二、流程设计

如图，多种 Ai Agent 执行设计流程图；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-15-01.png" width="600px">
</div>

Ai Agent 的处理过程也是分为几类的，用于适应不同的场景使用；

1. 固定N个步骤，这类的一般是配置工作流的，提高任务执行的准确性。如，一些检索资料、发送帖子、处理通知等。
2. 顺序循环调用，配置 Agent 要执行的多个 Client 端，以此顺序执行。适合一些简单的任务关系，并已经分配好的动作，类似于1的方式。
3. 智能动态决策，这类是目前市面提供给大家使用的 Agent 比较常见的实现方式，它会动态的规划执行动作，完成行动步骤，观察执行结果，判断完成状态和步骤。并最终给出结果。
4. 【新增】规划分析决策，根据用户输入的信息诉求，以及配置的 MCP 的能力，进行步骤规划。之后把步骤拆分出 1、2、3 具体要做什么，在依次执行这些步骤。