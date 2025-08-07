---
title: 【更】第3-10节：Agent执行链路分析
pay: https://t.zsxq.com/bxh8h
---

# 《Ai Agent》第3-10节：Agent执行链路分析

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/ty1Yy](https://t.zsxq.com/ty1Yy)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

通过现有实现的动态化构建 Ai API、Model、Client、Tool（MCP）、Advisor（记忆、RAG）、Prompt，完成 Ai Agent 服务处理。

最早 OpenAi 出来时，我们只是对 Ai 单向询问（含上下文记忆）和提供问题结果。后来开始有了 RAG 知识库，可以让我们每次的提问结合知识库获取更全面的内容。再到后来开始有了 MCP 服务协议，让 AI 具备了调用外部服务的能力。

那么，到这再往后开始有了 Ai Agent 的概念，也就是让 Ai 具备环境感知能力、自主决策并执行行动，直至完成最终的结果。

这也就是我们目前在使用一些 Ai Agent 的时候，进行一些问题提问的时候，他会根据环境（询问）状态制定行动计划，调用各种工具和API执行具体任务，并在多轮交互中维持上下文状态，输出最终的结果。这也是我们要做的事情。

鉴于，整个 Ai Agent 的复杂性，我们不能一上来就直接去编码，这样很多伙伴会比较晕。所以我们先来完成 Agent 单元测试，在结合我们动态实例化的各项服务，处理 Agent 循环制定行动计划和执行多轮会话。

## 二、流程设计

如图，不同方案实现的 Agent 流程；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-10-01.png" width="800px">
</div>

Ai Agent 的处理过程也是分为几类的，用于适应不同的场景使用；

1. 固定N个步骤，这类的一般是配置工作流的，提高任务执行的准确性。如，一些检索资料、发送帖子、处理通知等。
2. 顺序循环调用，配置 Agent 要执行的多个 Client 端，以此顺序执行。适合一些简单的任务关系，并已经分配好的动作，类似于1的方式。
3. 智能动态决策，这类是目前市面提供给大家使用的 Agent 比较常见的实现方式，它会动态的规划执行动作，完成行动步骤，观察执行结果，判断完成状态和步骤。并最终给出结果。