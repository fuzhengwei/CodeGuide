---
title: 【更】第3-14节：解析Swagger标准OpenAPI协议
pay: https://t.zsxq.com/wvb2k
---

# 《AI MCP Gateway 网关服务系统》第3-14节：解析Swagger标准OpenAPI协议

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/hTWme](https://t.zsxq.com/hTWme)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

在前面章节，我们实现了关于 AI MCP 网关，解决 HTTP 协议向 MCP 协议的转换处理。相关的协议（HTTP）数据存储在数据库表中。那么现在的另外一个问题就是，数据库表里的协议映射数据应该怎么录入进去。

这个录入方案也比较多，比如，提供一个页面，让用户自己手动输入相关的协议映射信息。也可以提供一个 SDK 组件，让 HTTP 接口服务端引入，之后自动上报。之后这里还有一种方案，是通过 Swagger 导出标准 OpenAPI 协议接口 json 文件，以文件数据的方式，录入到数据库表中。

本节，我们先来处理关于 Swagger 的使用到提供一个工具包把 OpenAPI 的 JSON 转换为我们的设计的库表对应对象的关系。`本节操作转换的工具类包，不非得手动编码，能理解和使用即可`。

## 二、工具介绍（Swagger）

官网：[https://swagger.io/](https://swagger.io/)

Swagger 是一个开源的API 设计和文档工具，它可以帮助开发人员更快、更简单地设计、构建、文档化和测试 RESTful API。以及可以导出 [OpenAPI](https://openapi.apifox.cn/) 标准的协议接口 JSON 文件。

它的接入方式也非常简单，只要在项目工程中引入 Swagger 相关的 POM 文件，在工程启动后就可以访问 Swagger 页面，查看 HTTP 接口服务。如果希望接口描述信息更为准确，也可以在接口和出入参对象（属性）上添加上相关的注解描述。这个描述信息对我们 MCP 协议更为重要。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-14-01.png" width="750px"/>
</div>

如图，我们需要的就是这份标准的 OpenAPI 接口的 JSON 文件，使用它解析并转换为目前 ai mcp gateway 库表结构中设计的 http 协议和字段映射关系。你可以把这部分的解析，当做一个工具包使用。