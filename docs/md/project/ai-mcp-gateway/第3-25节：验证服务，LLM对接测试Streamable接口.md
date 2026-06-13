---
title: 【更】第3-25节：验证服务，LLM对接测试Streamable接口
pay: https://t.zsxq.com/zhIa4
---

# 《AI MCP Gateway 网关服务系统》第3-25节：验证服务，LLM对接测试Streamable接口

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/e0vQE](https://t.zsxq.com/e0vQE)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

前面我们已经把 Streamable HTTP 协议的基础能力开发出来了，这一节要做的事情就是把这套能力真正验证起来。

这一部分主要做三件事：

1. 验证 Streamable HTTP 服务接口是否可用，包括 `GET / POST / DELETE` 三个入口。
2. 验证会话管理语义是否符合 Streamable 协议，重点确认不会再像 SSE 一样发送 endpoint 事件。
3. 验证 LLM 侧是否可以通过 Streamable HTTP 方式，对接网关并完成工具调用。

只要这三件事跑通了，就说明我们前面做的协议适配不是“写出来了”，而是“真的能用起来了”。

## 二、功能设计

如图，Streamable HTTP 协议对接 LLM 服务的整体关系；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-25-01.png" width="850px"/>
</div>

- 首先，这一部分的重点是把 Streamable HTTP 协议真正接入到网关服务验证链路中。
- 之后，需要调整会话管理逻辑，让 Streamable HTTP 走它自己的协议语义，而不是继续沿用 SSE 的 endpoint 下发方式。
- 最后，在 LLM 侧增加 Streamable 工具回调策略，让模型也可以通过这个协议去加载 MCP 服务。

这里要特别注意一点：**Streamable HTTP 和 SSE 是两套不同的协议语义**。它们虽然都和 MCP 会话相关，但不能直接混用。
