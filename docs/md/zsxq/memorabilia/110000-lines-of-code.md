---
title: 从码农小白到P6+，我为你编写了11万行项目代码！
lock: no
---

# 从码农小白到P6+，我为你编写了11万行项目代码！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主，小傅哥。

截止到今天3.18日，我已为加入星球「码农会锁」的伙伴，提供了**总计11万行**代码数量的实战项目，涵盖了；`业务项目`、`组件项目`、`插件项目`、`源码学习`、`基础教程`，这5类内容。更重要的是这11万行代码，没有CRUD，只统计 Java 核心编码数量。🤨👍🏻

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-240317-01.png" width="250px">
</div>

可以说，这11万行的代码，耗尽了我所有的周末和假期！

众所周知小傅哥在技术圈子里是一股清流，写技术这么多年，依然想个生产队等卷王一样，源源不断的给粉丝👬🏻兄弟们创作出高质量的技术资料。业务项目5个、组件项目3个、源码学习2套、基础教程3套。再加上 [bugstack.cn](https://bugstack.cn) 开源的技术文章，除了11万行的源码，技术博客的文字创作也有300多万字。💥

**不用藏着掖着了**，是时候给兄弟们看一下了！

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-240317-02.png" width="350px">
</div>

这11万行的代码，也就等于；5个业务项目、3个组件项目、2套源码学习、1套基础教程（分布式技术栈），当你有这样的一个积累后，你的个人技术积累将有非常大的提升。程序员的0-1年找工作，2-3年跳槽/晋升、3-5年晋升高级，都需要有夯实的基本功，才能在职场中得到更高的职级和更多的薪水。

那这些项目的学习路线是什么样的呢？小傅哥接下来就给大家介绍下，让大家的学习可以事半功倍。

> 以上这些内容的门票价🎫，只需要100多元！文末有🧧优惠加入方式。

## 一、项目视图

这11万行的 Java 代码项目，都能学到啥呢，包括；场景、架构、技术？我们先来个全局视角俯瞰下，做到心中有数。

### 1. 大营销平台系统

前后端+Dev-Ops，全栈技术实践。项目将先以最新DDD架构和设计模式进行重构Lottery项目，扩展用户、账户、积分、兑换、分享模块，以及和 OpenAI 进行微服务整合，dubbo 通信完成项目综合使用。

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-240317-03.png" width="750px">
</div>

### 2. OpenAI 微服务应用体系构建

前后端+Dev-Ops，全栈技术实践。以OpenAi场景，运用DDD架构，**实现从微信公众号扫码登录，到下单支付对个人账户充值，完成OpenAi对话消费的全流程**。

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-240317-04.png" width="750px">
</div>

### 3. Lottery 分布式抽奖系统

抽奖系统是互联网C端运营场景核心微服务之一。该系统从用户进入抽奖开始，进行**差异化的人群决策，筛选可参与的活动ID，并领取活动处罚抽奖以及异步发货**。其中重点设计了，滑块锁🔐降低独占性，**增强秒杀并发度**。

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-240317-05.png" width="750px">
</div>

### 4. IM(Netty + JavaFx)：仿桌面版微信聊天

这是一个通过JavaFx技术开发桌面版UI，并在Java通信客户端集成使用的前后端分离设计。再通过服务端进行**Netty多协议通信交互**，完成通信处理。

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-240317-06.png" width="750px">
</div>

### 5. ChatGPT Ai 问答助手 - 小型，自动回帖机器人

通过搭建简单DDD工程，自动采集星球问题和帖子，再通过数据分析整理，**调用ChatGLM等开放OpenAi接口**自动回复。

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-240317-07.png" width="750px">
</div>

### 6. API网关：中间件设计和实践

以承载Nginx负载为入口，接收并处理HTTP协议进入会话流程模型，**通过“源码级”设计实现；协议的转换、关系的绑定、动作的执行、泛化的调用再到结果的返回等一系列动作**。并支持扩展各类其他服务，如MQ、Redis、JOB、MySQL等资源为HTTP服务。这套系统设计非常强劲。

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-240317-08.png" width="750px">
</div>

### 7. SpringBoot Starter 中间件设计和开发

全小册包括16个中间件对应30个代码库提供给读者学习使用。场景涵盖：**技术框架、数据服务、数据组件、分布式技术、服务治理、字节码、IDEA插件**七个方面，贯穿整个互联网系统架构中常用的核心内容。非常值得了解、学习、实践到掌握。

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-240317-09.png" width="750px">
</div>

### 8. IDEA Plugin 插件开发

全手册，分为4章12节循序渐进的通过实践案例开发的方式，**串联 IDEA Plugin 开发的各项常用技术点**，为读者讲解如何开发一个 IDEA 插件。

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-240317-10.png" width="750px">
</div>

初次之外还有手写Spring、手写MyBatis，和一套基础教程。基础教程可以让小白伙伴扎扎实实的学习 SpringBoot 应用的分布式技术栈使用。这套基础教程完全是小傅哥从0到1编写，紧密配合星球实战项目。把各项所需的东西都拆解为一个个独立的小案例，让大家学习起来更加容易。

## 二、学习路线

星球的项目比较多，我来帮你明确一个学习路线，可以作为参考。这个路线分为3个阶段，分别对应到实习、校招、社招不同的能力要求。一般卷王的视角是，实习卷校招能力，校招卷社招能力，社招卷全部能力！ 

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-240317-11.png" width="550px">
</div>

- 就实习来说，基本要求是了解一部分分布式技术栈，完整运用常用技术栈做过项目开发。如果能交付上线会更好。
- 从实习完成，在到校招难度会加大一些。简历最好有2个项目。可以是2个业务项目，或者2个业务+1个技术的组合。技术可以是组件或者手写源码类。
- 最后是社招，社招可以摘取1、2阶段的实战项目，综合在学习下源码应用的项目，这样简历会更有的拼。尤其是那种本身自己业务实在没啥讲的，那么就可以做个API网关，这类项目没有啥业务属性，学习后也更容易写到简历。【不少校招也写了 API网关，冲到阿阿里、美团、字节，还是很好用的】

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-240317-12.png" width="550px">
</div>

> 小傅哥星球的这些项目，是站在一个架构师的视角，培养新人的综合技术能力而设计的。所以建议，可以有计划的完成学习积累。每一个项目都有自己的架构特色和设计的技巧，学到手对自己的帮助都非常大。让你不只是一个CRUD仔，面试/述职，都更有东西可以讲出来。

## 三、加入学习

小傅哥是一个大厂的架构师，经常会带着伙伴们，卷这些实际场景中非常有必要的技术。也会带着伙伴实战项目，这些项目也都是来自于互联网大厂中真实的业务场景，所有学习这样的项目无论是实习、校招、社招，都是有非常强的竞争力。别人还在玩玩具，而你已经涨能力！

>这样的项目学习在小傅哥星球「码农会锁」有8个，每个都是从0到1开发并提供简历模板和面试题，并且还在继续开发，后续还将有更多！价格嘎嘎实惠，早点加入，早点提升自己。项目地址：[https://gaga.plus](https://gaga.plus)