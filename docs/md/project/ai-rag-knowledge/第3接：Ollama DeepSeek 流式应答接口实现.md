---
title: 【更】第3节：Ollama DeepSeek 流式应答接口实现
pay: https://t.zsxq.com/GwNZp
---

# 《DeepSeek RAG 知识库》第3节：Ollama DeepSeek 流式应答接口实现

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

引入 Spring AI 框架组件，对接 Ollama DeepSeek 提供服务接口。包括；普通应答接口和流式接口。

## 二、技术方案

对接 AI 的方式有很多，比如；`AI 官网提供的 SDK`、`自研 SDK 组件`、`one-api 服务类统一包装接口`，其中自研类 SDK 已经在星球 openai 项目对接 chatglm、chatgpt 的时候进行设计，为了差异化学习到不同技术，本项目会采用 Spring AI 框架进行对接。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-01.png" width="750px">
</div>

官网：[https://spring.io/projects/spring-ai](https://spring.io/projects/spring-ai)

Spring AI 支持；OpenAI，Microsoft，Amazon，Google和Ollama，大模型的对接。其他不属于这个范围的，可以通过 [one-api](https://github.com/songquanpeng/one-api) 配置，统一转换为 OpenAI 接口服务格式进行使用。


