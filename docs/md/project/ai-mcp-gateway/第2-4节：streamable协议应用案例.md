---
title: 【更】第2-4节：streamable协议应用案例
pay: https://t.zsxq.com/K9HyJ
---

# 《AI MCP Gateway 网关服务系统》第2-4节：streamable协议应用案例

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/88gpJ](https://t.zsxq.com/88gpJ)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

## 一、本章诉求

前面章节我们学习了 sse 协议的分析，设计，实现和使用，从这一节开始，我们进入 streamable 协议的扩展，这也是目前最新的 mcp 协议使用方式。我们通过网关的统一管理，可以让一套服务，有不同的协议调用。

这一节先升级 Spring AI 框架（1.1.4），增加 mcp 服务 streamable 协议方式的对接案例，对比 sse 与 streamable 的差异。

## 二、协议对比

如图，mcp 通信，sse、streamable 对比；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-2-4-01.png" width="850px"/>
</div>

- Streamable HTTP 是一种基于普通 HTTP 请求、服务器可按需升级为 SSE 流式响应、无需强制长连接、统一通过 /message 协议层通信并支持 stateless 模式的传输机制。
- Streamable HTTP 支持无状态服务器与纯 HTTP 实现，无需强制长连接和 SSE 依赖，与现有基础设施良好兼容，是对 HTTP + SSE 的渐进式改进，并可灵活选择是否使用 SSE 流式响应。

> 这两种实现都是在协议的出入方式有变化，但底层的通信仍然是json-rpc2。后续章节会从源码视角来看 sse、streamable 的实现差异，之后在去做设计和编码实现。
