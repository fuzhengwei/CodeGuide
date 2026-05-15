---
title: 【更】第3-17节：网关配置域-配置数据存储(CRUD)
pay: https://t.zsxq.com/BGsjf
---

# 《AI MCP Gateway 网关服务系统》第3-17节：网关配置域-配置数据存储(CRUD)

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/wGt6C](https://t.zsxq.com/wGt6C)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

## 一、本章诉求

本节我们要新增加一个网关配置域，对网关、工具提供基本的配置操作，也就是 CRUD 的代码从领域层操作数据库的过程。这部分的内容实现，为的就是在后续提供的 AI MCP 网关运营服务上，可以在后台配置出整个网关服务。

## 二、流程设计

如图，网关配置域功能流程设计；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-17-01.png" width="750px"/>
</div>

- 首先，我们知道的，一整套的 AI MCP 网关配置，包括了，协议的解析存储和使用，之后是鉴权，但最开始是有一个网关的配置的，也就是写入基本的网关配置信息。
- 那么，为了能完整的串联全部流程，我们这里需要增加下网关配置域，处理网关配置、网关工具配置的内容，一个网关可以挂多个工具，一个工具映射了一个协议（http、rpc等）
- 注意，当前我们先实现了最基本的保存和更新，后续随着运营后台的开发，在陆续补充需要的接口。