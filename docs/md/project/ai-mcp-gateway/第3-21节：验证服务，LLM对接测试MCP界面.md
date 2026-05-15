---
title: 【更】第3-21节：验证服务，LLM对接测试MCP界面
pay: https://t.zsxq.com/uFxzn
---

# 《AI MCP Gateway 网关服务系统》第3-21节：验证服务，LLM对接测试MCP界面

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/xlwYy](https://t.zsxq.com/xlwYy)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

## 一、本章诉求

将上一章节开发实现的 LLM 测试网关服务接口，在管理后台对接测试 MCP 服务调用页面，以便于通过页面选择网关的方式进行测试验证使用。

只要你开发好了对应的服务接口，AI IDE 工具做这类事情很方便，也完全可以做一套自己的新的页面 UI 效果出来。（建议听劝，做一套自己的，可以面试演示的！）

## 二、功能设计

如图，LLM 对接 MCP 服务，从接口到服务接口的关系；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-21-01.png" width="850px"/>
</div>

- 首先，这一部分主要添加的内容是从管理后台的UI，增加一个测试网关服务的界面。通过选择网关，选择认证（key），是否重新加载，之后发起对话。
- 之后，因为要网关列表，并根据网关列表有配置认证的情况，查询认证（key），所以要适当调整接口。满足查询诉求。