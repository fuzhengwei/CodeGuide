---
title: 【更】第2-2节：MCP代理调用
pay: https://t.zsxq.com/OkMLy
---

# 《AI MCP Gateway 网关服务系统》第2-2节：MCP代理调用

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/UAbZd](https://t.zsxq.com/UAbZd)

>大家好，我是技术UP主小傅哥。

## 一、本章诉求

实现一个 MCP 客户端，用于对接 MCP 服务端，并通过代理 AI 接口的方式完成调用。该方案旨在调试 AI 调用 MCP 过程中的通信的请求接口协议，便于查看和分析相关数据。

## 二、流程设计

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-2-2-01.png" width="550px"/>
</div>

- 如图，是整个 Ai Client 以 Tools 工具，对接 MCP 的流程结构图。也是 Ai Agent 智能体最基础配置。如果感兴趣 [Ai Agent 项目](https://t.zsxq.com/GwNZp)，也可以在星球里学习。
- 之后，我们要在整个实现过程中，为 Ai 接口，通过 SpringBoot HTTP 方式做一层代理。这样在调用 MCP 的过程中，我们就可以清楚的知道这个过程的协议数据结构了。`代理的方式可以用在很多场景，还有一种是浏览器代理的主动安全扫描技术，甚至你服务器的应用暴漏了数据库密码都可以被扫描出来。` [扩展知识：安全漏洞扫描，他怎么拿到了我的数据库密码？](https://bugstack.cn/md/road-map/13scan-jdumpspider.html)
