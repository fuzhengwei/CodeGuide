---
title: 【更】第2-5节：streamable协议应用分析
pay: https://t.zsxq.com/gRv2b
---

# 《AI MCP Gateway 网关服务系统》第2-5节：streamable协议应用分析

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/z3FaC](https://t.zsxq.com/z3FaC)

>大家好，我是技术UP主小傅哥。

## 一、本章诉求

通过分析（+ debug 调试） spring ai mcp streamable 协议源码，深入理解实现原理。

在前面章节 mcp sse 的分析和实现的基础上，本章节往后对 mcp streamable http 协议的理解和实现会轻松不少，因为底层 mcp 模型上下文协议，这部分内容是不变的，更多是调整了协议的执行流程。小伙伴们也可以结合本节内容，多一些源码的调试验证，以及各类资料的检索补充自己的知识积累。

>每个伙伴的学习储备不同，可能有些伙伴理解起来容易，有些伙伴会感觉稍微吃力。这时候可以使用 [walicode.xiaofuge.cn](https://walicode.xiaofuge.cn/) 这样的 AI IDE 工具打开这部分代码，让继续分析（把代码加入对话框，之后告诉他你是编程小白，对哪些内容不理解，需要帮忙分析）。

## 二、通信协议（streamable http）

如图，streamable http 通信协议过程；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-2-5-01.png" width="950px"/>
</div>

- 首先，乍一看 mcp streamable http 流程交互过程蛮多，不好理解。但其实抓住本质，一下就好理解了。streamable http 的协议定义采用的是单端点通信，同一个接口方法（如 `/mcp`），分为 GET、POST 两个方法类型，各自承担不同职责。
- 之后，这种通信方式在微信公众号的对接中有类似方式，通过 post 验证签名，通过后再通过 get 方法通信，也是一个接口名称，两个通信方法。之后，streamable http 也是这样的通信结构。
    - **步骤1（POST - 初始化）**；通过 post 请求，传递 method 标识为 `initialize` 初始化标识，创建 `sessionId`（`Mcp-Session-Id`）写入到 header 头信息中。`sessionId` 的用途是用于做通信标记的，你日常在互联网中做一些如流程比较长业务的，`信贷审批`、`协议签约`，都会有一个 sessionId 记录的你的生命周期行为。
    - **步骤2（GET - 建立监听）**；建立在步骤1的基础上，拿了 sessionId 以同一个接口的不同请求方式（post -> get），用 get 请求建立 SSE 连接。建立这个东西的目的在于数据传输。就像，post 你是拿到了"批文"，get 帮你建了一条通信监听管道。后续服务端有任何需要主动推送的消息（如通知、采样请求），都可以通过这条 SSE 管道推送给客户端。
    - **步骤3（POST - 业务请求）**；管道建立完毕后，接下来要操作；做初始化完成、资源、工具列表查询和工具调用，这一部分的操作都会通过 POST 请求发送到服务端，但响应结果会通过步骤2建立的 SSE 连接，使用 `sessionTransport.sendMessage(message)` 把数据发送给客户端。