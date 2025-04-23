---
title: 【更】第15节，川流不息，实现MCP微信公众号消息通知服务
pay: https://t.zsxq.com/84IV7
---

# 《DeepSeek RAG&MCP 增强检索知识库系统》第15节，川流不息，实现MCP微信公众号消息通知服务

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/Qlqkr](https://t.zsxq.com/Qlqkr)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

AI MCP 是可以让 AI 以工作流方式进行调用的，为了更好的体现这一点，同时也为了增强整体的自动发帖服务链路。本节我们实现一个微信公众号推送消息的 MCP 服务。

这一节暂时会先以 stdio 方式开发，之后下一节部署的时候，会把 CSDN、WeiXin 两个 MCP 服务都以 SSE 方式进行部署。让大家学习到不同的开发方式和部署方式。

## 二、功能流程

如图，自动发帖后，进行微信公众号，消息推送；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-15-01.png" width="850px">
</div>

- 首先，CSDN 自动发帖是上一节实现的内容，本节要实现一个微信公众号推送模板消息的实现。
- 之后，AI 调用两套 MCP，可以一次会话，也可以使用 ChatMemory 进行记忆完成2次对话处理 MCP 流程。
- 最终，实现自动发帖后，完成消息通知给我们自己。点击通知信息可进入具体文章。

