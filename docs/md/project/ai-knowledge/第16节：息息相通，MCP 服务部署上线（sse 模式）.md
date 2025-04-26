---
title: 【更】第16节：息息相通，MCP 服务部署上线（sse 模式）
pay: https://t.zsxq.com/Qexmh
---

# 《DeepSeek RAG&MCP 增强检索知识库系统》第16节：息息相通，MCP 服务部署上线（sse 模式）

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/J6kpc](https://t.zsxq.com/J6kpc)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

调整 mcp-server-csdn、mcp-server-weixin，两个 MCP 部署方式为 SSE 以及增加 Dockerfile 部署脚本。让服务支持以 sse 方式，被 ai-mcp-knowledge 调用。

## 二、功能流程

如图，以 sse 方式，构建服务打包上线；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-16-01.png" width="850px">
</div>

- `SSE` (Server-Sent Events) ，是一种基于 HTTP 的**服务器向客户端单向实时推送数据**的通信技术，常用于实现实时更新功能。
- 在 Spring AI 框架中，SSE 的实现方式包括 spring-ai-starter-mcp-server-webmvc、spring-ai-starter-mcp-server-webflux 两种框架实现。课程以 webflux 进行使用。
- SSE 的部署方式，要把每个 mcp 服务，通过 docker 进行部署，提供出可用的接口。之后 ai-mcp-knowledge 工程则配置 sse 方式进行使用。

> 接下来我们介绍，如何配置 sse 方式进行对接。这部分主要是配置文件的变化，以及 1.0.0-M6 版本号关于 sse bug 的处理。


