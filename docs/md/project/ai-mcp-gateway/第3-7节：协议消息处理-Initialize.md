---
title: 【更】第3-7节：协议消息处理-Initialize
pay: https://t.zsxq.com/Re6ZW
---

# 《AI MCP Gateway 网关服务系统》第3-7节：协议消息处理-Initialize

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/yIOEM](https://t.zsxq.com/yIOEM)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

从本章开始，我们将进入消息协议的处理过程，把原本的硬编码的案例操作，通过网关ID（gatewayId）与数据库配置数据进行关联。

今天我们来处理第一个协议消息的处理场景，Initialize 初始化部分。这部分学习时，会附带调试 Spring AI 框架中 modelcontextprotocol 关于协议处理部分的源码，让大家有更多的积累。

- [https://github.com/modelcontextprotocol/java-sdk](https://github.com/modelcontextprotocol/java-sdk)
- [https://modelcontextprotocol.io/docs/getting-started/intro](https://modelcontextprotocol.io/docs/getting-started/intro)

## 二、功能设计

如图，Initialize 初始化协议处理；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-7-01.png" width="900px"/>
</div>

- 首先，在 session 会话层的 adapter 下，创建调用基础设施层仓储服务的接口，并由基础设施层做功能实现。再通过依赖倒置的方式用于领域层使用。（这部分对于初次接触 DDD，又没学习过一些源码或者设计知识的伙伴，可能感觉有点绕，不过没关系，在看到课程源码以后，会逐步清晰）
- 然后，从数据库获取的数据，要进行协议转换。也就是把之前固定编码的部分，用数据库获取的数据来动态填充。这部分内容只是初次拿到数据并使用的小试牛刀，并不复杂。不过，我们还会增加对 Spring AI 源码的调试，扩展知识学习。
- 之后，在我们陆续完成这些 handler 消息的处理后，则会在 case 层陆续做编排，以及验证网关ID和token等。
