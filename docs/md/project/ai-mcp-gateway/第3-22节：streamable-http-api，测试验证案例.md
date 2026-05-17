---
title: 【更】第3-22节：streamable-http-api，测试验证案例
pay: https://t.zsxq.com/Xg0c4
---

# 《AI MCP Gateway 网关服务系统》第3-22节：streamable-http-api，测试验证案例

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/o3k1M](https://t.zsxq.com/o3k1M)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

为了让大家学习的时候，更好的理解 streamable http 通信方式，这一节我们先来做一个固定的请求和应答结果的案例。之后后续的章节，在陆续完成 streamable http 接口与 case 编排层、domain 领域层的对接。

>在前面章节已经用 sse 方式串联了整个流程，那么目前章节核心的实现，其实就是把 streamable http 引入进来，之后逐步与底层的 mcp 协议对接即可。像是上传 openapi 协议解析存储，工具调用，结果映射等，这些都是固定的。

## 二、流程设计

如图，streamable http 调用流程（写一个固定编码的案例）；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-22-01.png" width="950px"/>
</div>

- 首先，此图也就是上一节我们分析 streamable http 协议和调试 spring ai 框架中 streamable http 部分的代码，提炼的流程图。post 简历会话 id，get 创建 sse。之后回到 post 流程，处理接收 mcp 请求，发来的（tools/list、tools/call）请求。
- 之后，就是 get、post 请求的执行流程，对于 post 请求的 method 方法处理，本节案例写了一个固定代码，暂时先不和 mcp 逻辑代码对接。
