---
title: 【更】第13节，道山学海，实现MCP自动发帖服务
pay: https://t.zsxq.com/WSM3k
---

# 《DeepSeek RAG&MCP 增强检索知识库系统》第13节，道山学海，实现MCP自动发帖服务

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/xTjYL](https://t.zsxq.com/xTjYL)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

分析 CSDN 文章发表接口，以 MCP 服务搭建的方式，实现一款 stdio 模式的 CSDN 发帖 MCP 服务。（后续开发 sse 模式）

**注意**：不限于CSDN，你可以结合本节的学习，在任何一个平台使用它的接口，完成自动发帖服务。如，这里还有一个知识星球的发帖，也可以学习下之后对接实现一个 MCP 服务。地址：[https://bugstack.cn/md/road-map/http.html](https://bugstack.cn/md/road-map/http.html)

## 二、功能流程

如图，实现 CSDN 发帖 MCP 服务流程；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-13-01.png" width="850px">
</div>

- 首先，无论你是对接任何的平台，都是需要先获得他的接口服务。这种接口一种是平台提供了专门的对接接口，另外就是没有这样的接口，我们是通过浏览器访问网站，获得的接口。哪这些接口通过代码方式完成请求。
- 之后，基于得到的接口，封装成可以调用的服务 service，这样 MCP 的入口工具，设定好入参信息，就可以调用底层的接口服务了。
- 最后，当用户提问时，如果你实现了不止一个 CSDN 发帖的 MCP，也包括如星球发帖。那么你的 AI 工作流，是可以顺序的向这些平台自动发帖。