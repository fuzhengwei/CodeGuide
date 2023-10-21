---
title: 第9节：OpenAi多渠道策略模式
pay: https://t.zsxq.com/13sKrl3oY
---

# 《ChatGPT 微服务应用体系构建》 - chatgpt-api 第9节：OpenAi多渠道策略模式

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★★★★☆
- **本章重点**：在DDD架构下的OpenAi领域模块中，通过策略模式扩展OpenAi多渠道对接使用。
- **课程视频**：[https://t.zsxq.com/13cy0TKmq](https://t.zsxq.com/13cy0TKmq)

**版权说明**：©本项目与星球签约合作，受[《中华人民共和国著作权法实施条例》](http://www.gov.cn/zhengce/2020-12/26/content_5573623.htm) 版权法保护，禁止任何理由和任何方式公开(public)源码、资料、视频等内容到Github、Gitee等，违反可追究进一步的法律行动。

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

OpenAi 大模型不只是有 ChatGPT，还有如；ChatGLM、讯飞、腾讯、百度、京东各家提供的生成式大模型。并且主要的是在一个模型渠道出现问题的时候，可以让用户方便的切换到另外的模型进行使用。那么本章节的核心目的就是优雅的对接和切换使用除了 ChatGPT 以外的 ChatGLM 生成式大模型，代码的设计实现上会用到策略模式。

对接ChatGLM前，可以阅读[ChatGLM 大模型 SDK](https://bugstack.cn/md/project/chatgpt/sdk/chatglm-sdk-java.html)设计实现。

## 二、流程设计

现有的工程实现中，已经对接了 ChatGPT 大模型通信渠道，那么本节将使用策略模式扩展新的渠道。**设计模式的运用不是对代码的增加，而是对代码的边界切割，合理摆放再进行衔接调用。**

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-api-09-01.png?raw=true" width="450px">
</div>

- 如图中，左侧的一列是标准的流程，也是程序的主脉络。这部分通过模板模式来定义调用过程标准。
- 如图中，右侧的部分，是通过设计模式封装的流程。而本节重点是对原本单条的 OpenAi 渠道对接，扩展为策略模式下的多渠道 OpenAi 对接。
