---
title: 第1-1节：契约定义(types)，消息与工具
pay: https://t.zsxq.com/4307i
---

# 《DeepSeek Harness Java》第1-1节：契约定义(types)，消息与工具

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/WakNX](https://t.zsxq.com/WakNX)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

首先，DeepSeek Harness Java 项目，是一整个从 0 到 1 东西，没有引入什么第三方的框架。所以包括；LLM 大模型的对接、流式传输的处理、多模态的消息类型、工具的操作、SSE 的传输，以及到后续的 ReAct 智能体部分，都要逐步实现。

所以，这就涉及到了很多基础的东西要在最开始就定义好，就像星球「码农会锁」早期做[《OpenAi 大模型应用服务体系构建》](https://t.zsxq.com/kEhGG) 项目一样，前面也是有一部分做 OpenAI SDK 的实现的，来对接 LLM 大模型。

> 为了更好的适配 DSH Java 场景，也为了很多对此基础不足的伙伴，有一个完整学习。这里就不直接引入已经实现的这套 SDK 做扩展了。而是从头都做一次。

这一部分契约的定义，我计划分为3节来讲。包括；`用于 LLM 大模型对话的消息结构和工具协议`、`用于插件对接的 SPI 对象`、`之后还有一部分就是 trigger 层的 API 对象`。这样的需求实现过程，其实就是公司里做业务项目的标准。把统一的协议以及结构定义好，其他伙伴才能根据你的标准进行同步开发。

那接下来我们先开始第一部分，关于 LLM 大模型对话的消息和工具对象定义。这部分内容，我们放到 types 来编写。像是公司里有多个项目都共用此类内容的时候，会单独开发一个 SpringBoot Starter 包，来统一管理。就像星球里的 OpenAI SDK 一样。这里我们就不继续拆分了，但要知道这样的设计手段。

## 一、本章诉求

以围绕 openai 协议标准和智能体实现的诉求，定义所需消息协议（+tool）工具对象。对于 OpenAI 的协议标准，有官网和镜像站。这部分的内容实现，它的源头就来自于这里；

- 官网：<https://platform.openai.com/docs/api-reference/chat>
- 镜像：<https://openai.apifox.cn/>
- 其他：<https://openrouter.ai/docs/quickstart#using-the-openrouter-api>

举例，最基本的 curl 调用；

```java
curl http://127.0.0.1:8777/v1/chat/completions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer sk-waliapi-4f8895c43a6c4bc1ae6173fd0915c0e9" \
  -d '{
    "model": "gpt-5.5",
    "messages": [{"role": "user", "content": "Hello!"}]
  }'
```

> 建议安装 waliapi 本地 LLM Gateway，可以更加方便的配置模型、查看日志（上下文），辅助学习智能体开发。下载；<https://drive.weixin.qq.com/s?k=ACMA4AfQABU4S23jg8>
