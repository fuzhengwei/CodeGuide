---
title: 【更】第3-17节：增加调度器策略执行Agent链路
pay: https://t.zsxq.com/ayQWR
---

# 《Ai Agent》第3-17节：增加调度器策略执行Agent链路

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/44qnU](https://t.zsxq.com/44qnU)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

从ui页面开始，增加 ai agent 分类选择使用。因为这里有不同类型的 ai agent，所以要对调用的过程增加一个策略调度器，按照不同类型的 ai agent 选择不同的执行策略。

## 二、流程设计

如图，Ai Agent 策略调度器执行过程；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-17-01.png" width="800px">
</div>

- 首先，我们实现的 ai agent 有多种类型，所以在选择场景选择的时候，要根据不同的场景获取到走那种类型的 ai agent 执行策略。
- 之后，这里我们就把 AutoAgent、FlowAgent 都放到调度器里执行，另外要在数据库表 ai_agent 中增加一个 strategy 执行策略配置，这样用户提问时候传入的 agent id 就可以获取到对应的策略了。
- 最后，本节还有一点关于页面 UI 的完善，每次对话，会把对话消息存储到历史对话中。这部分前端的东西使用 ai 开发工具处理的。