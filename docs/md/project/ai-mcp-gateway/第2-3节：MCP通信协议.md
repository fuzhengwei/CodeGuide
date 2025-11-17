---
title: 【更】第2-3节：MCP通信协议(json-rpc2)
pay: https://t.zsxq.com/9PlXx
---

# 《AI MCP Gateway 网关服务系统》第2-3节：MCP通信协议(json-rpc2) - debug 调试 Spring AI 源码

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/wwiTt](https://t.zsxq.com/wwiTt)

>大家好，我是技术UP主小傅哥。

## 一、本章诉求

学习了解 JSON-RPC 2.0 消息协议定义，并通过工程实践调试（debug）验证的方式，分析 MCP 通信协议过程。

## 二、通信协议

MCP 等同于为 AI 安装上了手和脚，使其这个 AI 大脑具备了行为能力的执行。所以，25年以来，AI Agent 智能体才得以落地。

MCP 定义了一种标准化的通信协议，使客户端和服务器能够以一致且可预测的方式交换消息。这种标准化的定义对整个AI和服务的交互性至关重要。

而通信就要有数据的交互格式，MCP 采用的是 JSON-RPC 2.0 协议，作为客户端和服务端之间素有的通信消息格式。JSON-RPC 是一种轻量级的远程过程调用协议，采用 JSON 编码，易于阅读和调试、与编程语言无关，支持在任何编程环境中实现（Java、Python、Go、JS...），且成熟完善，规范明确，适合广泛使用。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-2-3-01.png" width="450px"/>
</div>

> 在本节代码工程下，docs/pdf -> JSON-RPC 2.0 Specification.pdf 详细介绍了 json-rpc 2.0 通信协议，可以查阅。也可以阅读它的官网，但打开会卡一些。[https://www.jsonrpc.org/specification](https://www.jsonrpc.org/specification)

###  1. 调用协议

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-2-3-02.png" width="650px"/>
</div>

- 首先，很多通信协议，也包括业务工程的流程处理，往往第一步是建立一个验证关系，拿到整个后续链路请求的会话ID，之后以会话ID作为全流程的串联关系进行通信。这和图中的 MCP 协议调用过程是一样的。
- 之后，MCP 客户端和服务端的交互，分为4个步骤；初始化（连接）、发现工具列表（能力）、执行工具调用、断开连接。也就是说 AI 要拿到你配置的工具的 MCP 的能力，这样才能根据你的请求决定调用哪个 MCP 服务，以及处理 MCP 服务返回的结果。
