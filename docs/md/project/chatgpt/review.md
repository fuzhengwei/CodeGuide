---
title: 复盘：OpenAi 项目复盘总结
lock: no
---

# 复盘：OpenAi 项目复盘总结 —— 小傅哥又一个Java项目力作！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>课程：[https://t.zsxq.com/133o5FKvc](https://t.zsxq.com/133o5FKvc)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=277778555&bvid=BV1tc411f7Am&cid=1316295147&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

大家好，我是技术UP主小傅哥。星球【[码农会锁](https://wx.zsxq.com/dweb2/index/group/48411118851818)】 OpenAi 可真实上线运行的项目，3个阶段全部完结交付💐🌶

前端；埋点监控 pv、uv、ip、跳出率、热力图。后端；普罗米修斯、Grafana 监控 QPS、调用量、响应时长、系统负载。系统；DDD 架构 + 微信支付 + 账户充值 +多渠道路由(ChatGPT、ChatGLM) + React 前端UI。—— 嘎嘎强、嘎嘎强！

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/openai-01.jpg?raw=true" width="200px">
</div>

**能做一个上线运行的项目，就能多学到一半的知识量**！

不上线，就不知道系统需要多大的服务器。不上线，就不能拿到系统的监控数据。不上线，就不清楚系统都有哪些异常。不上线，就不记得要给数据库表做索引优化。说的直白了，没有上线的项目，还只能算是开发阶段的`“技术玩具”`。甚至可能很多系统开发中的流程都是不健全的，缺少细节的处理和流程的补偿。所以，从这个项目开始，小傅哥在星球【码农会锁】带着大家做的项目，都将以上线为最终结果。

当我们把系统做完，配置上监控。看到系统负载和调用量那一刻，这个系统才是一个完整的系统。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/openai-02.png" width="850px">
</div>

那么，接下来小傅哥就对整个系统，做一次从需求到技术落地的复盘总结。让大家可以学习到的更多。

>文末有加入学习方式。此外在本项目的业务场景底座上，后续将开启新的上线项目，你猜猜会是什么项目！👍🐂

## 一、先说你能学到的

首先呢，此项目是一个真实运行在线上有用户使用的系统，所以所有的需求设计和落地实现，都以真实情况进行考量。那么也就是说，所有学习此项目的伙伴，都将学习到真实场景的实战技能。这些技能会通过课程的3个阶段来完成，如下；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/openai-03.png" width="550px">
</div>

- 第1阶段【基础】：01节~23节，学习工程的前后端基础框架搭建开发、实现 OpenAi 异步响应式对话、微信公众号鉴权登录。
- 第2阶段【深度】：24节~29节，运用模板、策略、工厂，添加多规则过滤引擎，处理白名单、敏感词、账户额度、状态、模型的验证，以及完成核心商品下单、微信支付、账户充值流程。这部分的 DDD 领域模型设计的非常漂亮。
- 第3阶段【广度】：30节~32节，多渠道策略模型OpenAi对接，系统监控(Prometheus + Grafana)、分布式技术栈扩展，让系统具备分布式部署能力。

此项目拆成3个阶段，让大家学习不会有太大压力，如果你着急完成并希望面试，那么完成第1阶段即可编写简历中使用。`整体项目预计在3周~4周可学习完成，如果不编写前端预计在2~3周学习完成。`

## 二、需求是怎么来的

OpenAi 项目，最早来自于小傅哥分别部署过的两个前端开源项目，给大家提供生成式服务。从最开始的部署完就能使用，到后来对接公众号登录引流粉丝关注，再到设计加密ApiKey降低资源投入成本，这样一步步形成了整个需求诉求。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/openai-04.png" width="750px">
</div>

1. **开源项目**，先后部署了2个版本的WEB-UI，也在各个版本中添加了微信公众号登录和加密Key独立使用的功能。一个是为了吸粉，另外一个是为了让常用用户有自己的Key，这样可以适当降低成本。
   - 优势；开源项目可以快速验证市场，完成早期的应用上线。并且开源项目有很多人维护，可以快速迭代。
   - 劣势；这个劣势到不是开源项目的问题，而是自身我们要结合自身需求迭代时所呈现的问题。

2. **自研项目**，于4.1日开始启动，10.1日全部切换到新版。对于我们学习来说，OpenAi 生成式服务只是个场景，结合这个场景可以锻炼其他各项模块的学习使用。如；公众号对接、登录、鉴权、规则过滤、商品下单、微信支付、商品发放等各项功能的开发。那么换换成其他场景一样可以做这些内容。
   -  优势；自研项目，容易管理，方便扩展。对各项功能的迭代都会非常方便。
   -  劣势；前期投入陈本大，需要较长的研发周期。

其实我很早就想自研一个这样的项目，但也因为个人可支配时间有限，既有日常的技术推文编写，也有星球内的课程开发。所以就想着那么不如把这样一个项目做成星球课程项目，既满足我能上线给大家使用，也能让星球伙伴学习到真实场景的项目，岂不是一举两得！

> 所以，对于各位来说；你相当于拿到了一个“公司”对外上线的项目，通过渐进式逐个章节+视频的方式，手把手教你学习开发实践应用技能！嘎嘎强、嘎嘎强！

## 三、系统是怎么做的

因为这套系统的第一要点是上线，那可不能胡乱开发，也不能随便的炫技个技术栈就完事了。并且所有的流程被抽象后都应该具备良好的扩展性和迭代性，这样的诉求下完成的交付，才是非常有价值的。

那么，我们以一个用户旅程的视角来看下，系统的流程脉络。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/openai-05.png" width="650px">
</div>

以用户的旅程视角，来看整个系统的模块串联。

- 首先，从用户登录 OpenAi-Web-UI 开始，引导关注公众号，获取验证码，并完成登录。这个时候系统会进行验证码与账户的绑定，并通过加锁的方式保证唯一关联关系。
- 之后，用户开始对话。那么系统会根据对话用户的类型，选择不同的渠道。如系统默认还是用户自身，并根据不同的类型进行校验。如果用户使用完默认的体验次数后，则引导进行账户充值处理。这块会进入整个后台系统商品域的处理，采用DDD架构模型进行落地。
- 最后，用户充值后使用则进行账户相关的校验，以及校验后根据结果返回给用户对应的信息。此外系统还提供了多模型的支持，对话选择阶段，进行多渠道路由的处理。这块在系统开发中，用到了策略 + 工厂的使用。

## 四、项目交付展示下

本次项目，是一个具有前后端 + Dev-Ops的综合项目实践，也是最新 DDD 架构模型的落地开发，在这个项目中你会学习到非常干货的技术运用、场景方案，也会学习到系统的部署、运维、监控等知识内容。

### 1. 应用部署 - 环境

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/openai-06.png" width="850px">
</div>

### 2. 项目演示 - 支付

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/openai-07.png" width="850px">
</div>

### 3. 前端监控 - 热力图

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-230905-09.png?raw=true" width="850px">
</div>

### 4. 后端监控 - Grafana

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/openai-08.png" width="850px">
</div>

## 五、项目大纲

**此项目，是按照互联网公司开发项目模式进行落地，逐个分支，逐步迭代完成。每个章节都会对应一个分支，并配文档和视频，讲解需求、讲解架构、讲解代码。** 大家可以先看看课程的大纲，就知道可以学习到哪些东西了。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/openai-09.png" width="550px">
</div>

---

课程包括；视频、小册、1 对 1 答疑解惑、专属VIP项目交流群，并且提供简历编写模板结构的一条龙🐲服务。让你学习后，直接拉开与还在玩具项目其他人的差距，面试脱颖而出提高竞争力！！！

## 六、加入学习

OpenAi 项目，是小傅哥【星球：[码农会锁](https://wx.zsxq.com/dweb2/index/group/48411118851818)】的第7个完结项目，其他的还包括：Api网关、Lottery 抽奖、IM通信、SpringBoot Starter、IDEA Plugin 等。—— 死鬼，你见过这么多的项目社区吗！

不过，这还不是最💥炸裂的。最炸裂的是，我们将在 OpenAi 项目的业务底座上，扩展更多项目开发。因为已经有了这个上线对外的项目，有用户、有流量、有行为，接下来才是后续的开发项目，将是全部以上线为目标，挑战技术运用！

**这是我心中的山河⛰** —— 你猜，小傅哥接下来会做哪个项目？🤔

<div align="center">
    <img src="https://bugstack.cn/images/article/project/lottery/Part-1/1-00.png" width="750px">
</div>

在有了 OpenAi 项目以后，这篇山河图中的项目，都将可以落地。哪怕我想做个拼多多的砍一刀，都可以！那么，在 OpenAi 项目完全收尾后，你觉得小傅哥会启动哪个项目？可以留言评论区哦！

>[🧧加入学习](https://bugstack.cn/md/zsxq/other/join.html) 能做到这样的技术项目实战社群，真的不多！你只是投入一顿大麻辣烫💰，就🉐获得超级大的回报！