---
title: 【更】第3-8节：协议消息处理-ToolsList
pay: https://t.zsxq.com/eOpMV
---

# 《AI MCP Gateway 网关服务系统》第3-8节：协议消息处理-ToolsList

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/U6Shg](https://t.zsxq.com/U6Shg)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

从 HTTP 接口到 MCP 协议的映射，这里要考虑的是怎么把一个完整请求 http 的接口描述录入到数据库，之后在通过数据库的配置，转换为 MCP 协议结构告诉 AI 客户端 tool/list，也就是这套 HTTP 接口到 MCP 以后所提供工具能力。

所以，小傅哥在带着大家实现的过程中，先做了基于 HTTP 接口所需存储的信息，做了库表的设计。之后到这一节，我们要把库表的数据转换为 MCP 协议结构数据。

拓展，像是 HTTP 可以做，那么 RPC、MQ、数据库等各类资源，你也可以转换为 MCP 服务协议进行使用。

## 二、流程设计

如图，Tool/List 工具列表协议处理；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-8-01.png" width="650px"/>
</div>

- 首先，我们要根据网关ID（gatewayId）从数据库中，获取网关配置和http工具字段配置列表，这部分数据相当于是把 HTTP 请求结构体，拆解喽放到数据库表中，之后再查询出来按照 MCP 协议结构组装使用。
- 然后，是对 buildTools 工具细节的处理，这部分是对元素的拆分和组装。这部分还有一些细节在下面。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-8-02.png" width="650px"/>
</div>

- 之后，映射数据库表 `mcp_protocol_mapping` 拆解字段的父子关系，一个字段以下的另外一个字段，如；`xxxRequest01 -> xxxRequest01.city` 的映射。所以在 buildProperty 的处理过程中要，要做递归循环，一层一层的找到这些内容，并拆解组装使用。