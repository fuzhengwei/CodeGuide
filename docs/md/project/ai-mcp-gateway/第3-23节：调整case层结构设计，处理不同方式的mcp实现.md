---
title: 【更】第3-23节：调整case层结构设计，处理不同方式的mcp实现
pay: https://t.zsxq.com/4Zgxq
---

# 《AI MCP Gateway 网关服务系统》第3-23节：调整case层结构设计，处理不同方式的mcp实现

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/cHjni](https://t.zsxq.com/cHjni)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

调整 case 层的结构，使其可以扩展 streamable、sse 多种不同协议方式处理 mcp 服务。

无论是”古法编程“、还是使用 AI 写代码，如果不做结构边界的限制，那么人和 AI 都可能有随机发挥的问题。这些限制的动作就是架构的设计和编码规范，在 AI 里被定义为 Spec 规约，在”古法编程“定义为架构设计和代码评审。

所以，这一节调整 case 的结构，以适应不同协议的处理方式也是非常重要的。如果什么都不做，就只是内部逻辑做 if···else 判断，那么代码就会趋向于增熵，而做设计和规约，就是减增熵的动作。

> 温馨提示，结构化设计思维很重要，就像买房子，你要先选那些不错的格局的，这样以后生活起来，东西放的才会放的更整洁舒适。

## 二、功能设计

如图，case层、sse、streamable，两种设计；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-23-01.png" width="950px"/>
</div>

- 首先，在 mcp 服务的 case 编排下，有2个接口类；`IMcpSessionService`、`IMcpMessageService`，原本只有一套对应的实现，处理 sse 服务。在结构分层的包下，没有额外再做什么区分。
- 之后，这一节的操作，主要是调整 case 下的包的分层结构，增加 sse、streamable，两套文件夹结构，这样会更好的区分出是做什么协议处理。另外一种设计方式是在原有的 sse 实现下，增加 node 的处理节点，通过分发节点来处理。这有点，你和你媳妇的衣柜，是一个衣柜里分下格子用，还是一人一个衣柜的感觉。两个方式也都能实现，也都能说得通。一人一个，可能会有重复的地方。都放在一块，又怕时间久了混乱了。搜易看你具体的取舍。
