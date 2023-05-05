---
title: 第1节：ChatGPT-SDK组件工程简单功能实现
pay: https://t.zsxq.com/0dLgCYNZY
---

# 《ChatGPT 微服务应用体系构建》 - chatgpt-sdk 第1节：ChatGPT-SDK组件工程简单功能实现

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★★★☆☆
- **本章重点**：创建 ChatGPT-SDK 组件工程，并使用 okhttp3 封装对 OpenAI 的请求处理。本章节暂时只实现简单的对话模型，后续逐步添加其他功能。
- **课程视频**：[https://t.zsxq.com/0d0baxnt3](https://t.zsxq.com/0d0baxnt3)

## 一、本章诉求

搭建一个 ChatGPT-SDK 组件工程，专门用于封装对 OpenAI 接口的使用。由于 OpenAI 接口本身较多，并有各类配置的设置，所以开发一个共用的 SDK 组件，更合适我们在各类工程中扩展使用。所以我们这个章节以 OpenAI 抽象为会话模型，建立工程结构设计。**其实这也是架构设计的一部分**。并在本章的 ChatGPT-SDK 组件工程中，开发简单的对话功能模块实现。

## 二、流程设计

整个流程为；以会话模型为出口，驱动整个服务的调用链路。并对外提供会话工厂的创建和使用。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-sdk-01-01.png?raw=true" width="750px">
</div>

-  如果有小伙伴学习过[《手写MyBatis》](https://t.zsxq.com/0dGck0sdO)，那么你一定会 MyBatis 源码中学习到关于会话模型的设计和实现。而类似这样的场景其实在业务流程中非常多，像是本章要实现的封装 OpenAI 其实也是会话模型结构。所以我们可以把 MyBatis 的设计思想融合到 ChatGPT-SDK 实现中。—— 星球中的[《API网关》](https://t.zsxq.com/0diYdgP5u)核心通信模块也是这样的思想。
-  在本章中，我们通过工厂模型，开启一个使用 okhttp3 封装的 OpenAI 会话服务，进行流程的调用。同时这里还包括请求拦截的处理，因为我们需要对http请求设置一些必要的参数信息，如；ApiKey、Token 等。
-  这里还需要用到 Retrofit2 组件，Retrofit2 可以将 HTTP API 转化为 Java 接口，并通过注解的方式描述请求参数和响应结果等信息，从而方便地发送网络请求。具体可以的代码对 IOpenAiApi 的赋值实现。