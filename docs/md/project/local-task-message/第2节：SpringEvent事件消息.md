---
title: 第2节：SpringEvent事件消息
pay: https://t.zsxq.com/Pfekb
---

# 《本地任务消息组件》- 第2节：SpringEvent事件消息

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>课程：[https://t.zsxq.com/Pfekb](https://t.zsxq.com/Pfekb)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

构建本地任务消息组件系统工程框架和对应的测试工程服务，并使用 Spring Event 事件消息，进行行为触达的通知和监听，用于后续处理外部 HTTP、MQ 的调用操作。

## 二、功能流程

如图，是本节关于 Spring Event 事件消息部分的流程。本节以这样一个简单功能诉求，引导工程创建。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/local-task-message/local-task-message-2-01.png" width="750px">
</div>

- 首先，搭建一套基础的消息组件工程框架，提供受理任务消息的请求，进行 Spring Event 事件的发布和监听。让大家初步了解组件类框架的实现和简单案例的验证。
- 之后，把本地消息组件构建成一个 jar，让测试工程通过 pom 的方式进行引入使用。
- 注意，本组件是一个核心内核服务，会陆续的完善全部功能，使其具备；切面拦截、数据库操作、Spring Event 监听，Job 任务扫描等。所以类似的场景，以DDD工程架构，构建内核更为合适。内核有点类似于，一个业务项目中，从上到下一整套流程，被单独提取出来做成一个独立的小项目，之后引入到上游使用方的系统，就可以运行。扩展资料：[DDD 工程模型](https://bugstack.cn/md/road-map/ddd-guide-03.html)