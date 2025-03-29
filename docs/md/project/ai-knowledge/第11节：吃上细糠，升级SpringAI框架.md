---
title: 【更】第11节：吃上细糠，升级SpringAI框架
pay: https://t.zsxq.com/zT6bl
---

# 《DeepSeek RAG&MCP 增强检索知识库系统》第11节：吃上细糠，升级SpringAI框架

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/q5rnP](https://t.zsxq.com/q5rnP)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

升级 Spring AI 框架到 1.0.0-M6 版本，以适应于二阶段 MCP（Model Context Protocol  模型上下文协议）服务开发。

## 二、本章诉求

本节升级包括；
- 提供，快速的 Ollama DeepSeek 部署，支持 1.5b、7b、30b 模型。服务器小时价格很低，速度非常快。
- 提供，pgAdmin管理工具部署（Docker+本地软件），用于操作 PG 向量库。
- 提供，向量库自主创建和多模型配置，满足不同模型的向量库使用。

## 三、升级框架

### 1. POM 配置

```pom
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-bom</artifactId>
    <version>1.0.0-M6</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

- Spring AI 目前最新版为 1.0.0-M6，[https://spring.io/projects/spring-ai#learn](https://spring.io/projects/spring-ai#learn)
