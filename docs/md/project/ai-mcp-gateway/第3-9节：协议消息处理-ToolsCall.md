---
title: 【更】第3-9节：协议消息处理-ToolsCall
pay: https://t.zsxq.com/U4Ouo
---

# 《AI MCP Gateway 网关服务系统》第3-9节：协议消息处理-ToolsCall

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/Vodcp](https://t.zsxq.com/Vodcp)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

从 AI 客户端，向 AI MCP 发起请求的过程中，小傅哥已经带着大家处理了；InitializeHandler - 初始化、ToolsListHandler - 获取工具列表，接下来再要处理的就是 ToolsCallHandler 的操作了，接收来自 AI 客户端，用户的请求，转换为对应的参数，发起接口请求（这部分我们会调用 http 接口）。

## 二、流程设计

如图，Tool/Call 工具接口调用协议处理；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-9-01.png" width="500px"/>
</div>

- 首先，从 ToolsCallHandler 入口开始，会接收到 AI 请求接口过程中，发过来的格式化参数信息（根据我们提供的工具列表说明提供过来的）。
- 之后，根据获取的请求信息，解析请求参数（像是 http 接口分为 post、get，如果以后扩展 rpc 接口，就直接泛化调用，这部分就可以根据你想对接哪些东西来看了）并做协议调用。
- 注意，当前章节还是gateway -> tool 是 `1:1` 的，这部分后续在做细化处理。我们先用一个简单的结构，把整个流程跑通。有了基础后，在深入的理解拆分会更好理解。
