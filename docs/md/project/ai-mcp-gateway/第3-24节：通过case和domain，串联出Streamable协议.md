---
title: 【更】第3-24节：通过case和domain，串联出Streamable协议
pay: https://t.zsxq.com/M8I22
---

# 《AI MCP Gateway 网关服务系统》第3-24节：通过case和domain，串联出Streamable协议

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/lCMKZ](https://t.zsxq.com/lCMKZ)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

今天是我们《AI-MCP-Gateway》项目学习的第3-24节课程。在上一节中，我们调整了case层结构设计，处理不同方式的MCP实现。这一节，我们将通过case和domain层的串联，完成Streamable协议的实现。

## 一、本章诉求

在前面的章节中，我们已经实现了SSE协议的完整功能，但是在实际使用中，我们还需要支持Streamable HTTP协议，这是MCP协议的另一种传输方式。Streamable协议与SSE协议有一些不同之处，主要体现在：

1. **会话创建方式**：Streamable协议通过POST initialize请求创建会话，而不是通过GET请求
2. **会话传递方式**：Streamable协议通过响应头`Mcp-Session-Id`返回会话ID
3. **会话监听方式**：Streamable协议的GET请求只负责监听已有会话，不创建新会话
4. **端点事件**：Streamable协议不发送endpoint事件，避免破坏协议语义

## 二、协议说明

如图，case 层编排 sse、streamable 两种协议方式的处理；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-24-01.png" width="950px"/>
</div>

- 首先，在上一节拆分的基础上，把 sse 的实现迁移过来，之后做兼容设计。包括；IMcpMessageService 需要添加泛型，因为 sse、streamable 返回的类型不一样（可以看代码），之后是执行步骤问题，sse、streamable 创建和使用 sessionId 的地方不一样，streamable 的会话只是获取 sessionId、消息处理中要单独拿出来 InitializeNode 分支来完成会话的创建和初消息的处理。
- 之后，对于领域层的会话管理服务 SessionManagementService，还有一小部分的兼容动作。createSession 方法，之前在 sse 下，会直接创建 messageEndpoint，现在需要通过新增加枚举 SessionTransportTypeEnumVO 来区分类型，之后在创建。
- 设计，现在你可以感受到 DDD 架构设计的魅力没。对于一个新逻辑的引入，不会废弃到 domain 核心领域（也就是你的积木），之后在 case 进行新的逻辑编排（搭积木）。最后提供对应的接口服务即可。
