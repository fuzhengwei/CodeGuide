---
title: 【更】第3-20节：验证服务，LLM对接测试MCP接口
pay: https://t.zsxq.com/0TXtB
---

# 《AI MCP Gateway 网关服务系统》第3-20节：验证服务，LLM对接测试MCP接口

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/VV1Bt](https://t.zsxq.com/VV1Bt)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

为了在配置完 AI MCP 网关以后，方便的验证网关服务SSE接口是否正常，我们需要在服务端增加一个简单的 LLM 大模型能力，通过 AI MCP 网关，来调用 MCP 服务。这样就可以非常方便的验证网关能力了。

本节我们先来做一下 LLM 的服务接口，把这部分能力在领域层设计实现出来，并包装一个接口服务。后续在结合这个接口服务，在管理后台配套的做一个页面出来。专门验证 MCP 服务能力。

>温馨提示，学习学到这了，就要多思考一些扩展。不只是完成课程的从0到1，也要思考从1-100还能做什么。

## 二、功能设计

如图，LLM 对接 MCP 服务，提供接口能力设计图；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-20-01.png" width="950px"/>
</div>

- 首先，整个操作的核心就是 LLM 服务，这部分内容，咱们在 ai-mcp-gateway-demo-mcp-server-test 工程里，有做过测试验证。现在相当于要把这部分能力迁移过来，放到 domain 领域层，实现 LLM 服务。
- 之后，这个 LLM 服务的目的，就是可以动态化的构建含有 MCP 服务的对话模型。MCP 的加载，来自于接口层传入的 gatewayId 网关 ID 做动态的加载处理。
- 注意，因为网关的配置也是不断的动态调整的，所以流程设计上会有一个是否重新加载的操作。重新加载的目的就是把 MCP 动态的刷新配置。