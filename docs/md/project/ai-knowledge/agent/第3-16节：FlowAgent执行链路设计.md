---
title: 【更】第3-16节：FlowAgent执行链路设计
pay: https://t.zsxq.com/Htptt
---

# 《Ai Agent》第3-16节：FlowAgent执行链路设计

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/J68sk](https://t.zsxq.com/J68sk)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

在 3-11 节的时候，我们做过一个这样的事情。针对于测试阶段的 Agent Test 代码，使用设计模式拆分出各个执行步骤，便于理解和维护。这一节我们同样需要把上一节分析的 FlowAgent 测试代码，按照模块化的流程进行拆分。

## 二、流程设计

如图，Flow Ai Agent 动态步骤分析执行流程图；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-16-01.png" width="800px">
</div>

- 首先，新增加一个 Agent 执行策略，流程步骤拆分执行。这个过程其实比上一 AutoAgent 要简单一些。
- 之后，分贝设计出 Step1 工具分析、Step2 动作规划、Step3 拆分步骤、Step4 执行节点（循环执行），这四个步骤就是 FlowAgentTest.test_agent 里的步骤。
- 最后，响应结果。后续章节会使用 sse 将结果响应到前端，这里我们暂时增加了判null操作，先不需要发送 sse 数据。
