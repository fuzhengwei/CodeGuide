---
title: 【更】第9节：扩展OpenAI模型对接，以及完整AI对接
pay: https://t.zsxq.com/YJxRy
---

# 《DeepSeek RAG 知识库》第9节：扩展OpenAI模型对接，以及完整AI对接

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>博客：[https://t.zsxq.com/1JLUq](https://t.zsxq.com/1JLUq)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

基于 Spring AI 扩展 OpenAI 模型对接，这样我们就可以使用一些代理的 ChatGPT 接口完成对话了。最终在完成全部接口与页面的对接。

## 二、技术方案

Spring AI 框架的好处，就是可以以统一的方式直接配置使用各类大模型。像是一些 Spring AI 没有直接对接的大模型，可以基于 one-api 配置转发，用统一 OpenAI 方式进行对接。

## 三、功能实现

### 1. 工程结构

### 2. 引入组件

```java
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
</dependency>
```