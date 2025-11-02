---
title: 【更】第2-1节：MCP服务实现
pay: https://t.zsxq.com/QdTsG
---

# 《AI MCP Gateway 网关服务系统》第2-1节：MCP服务实现

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/mUHg8](https://t.zsxq.com/mUHg8)

>大家好，我是技术UP主小傅哥。

进入到第二部分开始，小傅哥会带着大家通过各种手段对 MCP 协议进行细致的实践验证的方式进行分析。这部分包括了实现一个 MCP 服务，完成 MCP 服务的对接，通过代理的方式调试对接中 MCP 接口协议的数据，以及通过开发网页测试工具对接协议等。最后在进行 json-rpc2 定义的协议标准讲解。通过这样一套内容串联，你会对 MCP 有非常强的理解，也能为后面做 MCP 网关实现的学习打下良好的基础。

## 一、本章诉求

基于 Spring AI 框架，实现一个简单的 MCP 服务，为后续做协议的分析和验证进行使用。

因为协议分析，主要包括了通信的格式结构，如通信的入参，所以实现这样的一个 MCP 服务，会多增加一些入参类型，便于以后做网关设计时使用。

通常来讲，这部分的操作，也可以理解为是技术调研验证阶段。当我们要实现一个大的功能服务时，就要先想办法把复杂的逻辑拆分为独立的细小单元。也就是软件第一设计原则康威定律提到的，问题越小，越容易被理解和处理。所以，当你想在此项目拓展功能，或则自己在公司承接需求的时候，也可以使用这样的方式进行辅助完成系统的详细设计。

## 二、协议说明（MCP）

文档：[https://docs.spring.io/spring-ai/reference/api/mcp/mcp-server-boot-starter-docs.html](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-server-boot-starter-docs.html) - 可以阅读官网文档，这里就包含了如何实现 MCP 服务，并提供了小案例。

```java
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-mcp-server</artifactId>
</dependency>
```

Spring AI MCP（模型上下文协议）服务启动器为在 Spring Boot 应用程序中设置 MCP 服务器提供了自动配置功能。它实现了 MCP 服务器功能与 Spring Boot 自动配置系统的无缝集成。

MCP 服务器启动器提供：
- MCP 服务器组件的自动配置
- 支持同步和异步操作模式
- 多种传输层选项
- 灵活的工具、资源和提示规范
- 更改通知功能

Spring AI MCP 框架是对 MCP 协议的实现，可以把我们实现的服务功能，以 MCP 格式进行转换处理。与我们要实现的 AI MCP Gateway 不同的是，Spring AI MCP 固定的框架，每一个 MCP 都要独立完成开发，而 AI MCP Gateway 是一个通用协议转换的服务，只需要配置就可以完成从接口（http/rpc）到 MCP 协议的转换。

但为了更好的理解 MCP 协议，我们可以先基于 Spring AI MCP 框架，来实现一个简单的 MCP 并陆续完成对接使用，再到协议分析和设计 AI MCP Gateway 网关服务。