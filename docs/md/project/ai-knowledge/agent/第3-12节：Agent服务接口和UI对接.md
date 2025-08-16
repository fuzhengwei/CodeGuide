---
title: 【更】第3-12节：Agent服务接口和UI对接
pay: https://t.zsxq.com/T0H6n
---

# 《Ai Agent》第3-12节：Agent服务接口和UI对接

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/eaqbx](https://t.zsxq.com/eaqbx)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

以实现 Ai Auto Agent Server-Sent Events (SSE) 流式响应接口为目的，设计 SSE 异步响应结果对象，对 Step 1~4 步骤的过程数据进行异步流式响应返回。开发好接口后，与Ai实现的前端UI界面进行对接。

像是 Ai（Cursor、trae.ai） 对于前端这样没有太多复杂的流程代码，可以很好的实现出来。这对于后端工程师想做一些前端UI产品化的东西，就变得容易的很了！

## 二、对接效果

如图，流式响应（SSE）接口对接UI效果；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-12-01.png" width="950px">
</div>

- 首先，小傅哥对 **Auto Agent - 自动智能对话体** 进行了数据库表（初始数据）和服务启动时自动装配 Ai Agent 所需的各项配置（客户端、模型、API、MCP等）。以便于可以在接口请求服务时，调用 Auto Agent 智能体。
- 之后，用于就可以对智能体进行提问，所有的提问信息，会进入到服务端的 Step 1~4 步骤，并进行循环分析、执行、检测，以及最终输出结果。

> 接下来，小傅哥带着大家看看 Auto Agent 服务接口和对接是如何处理的。