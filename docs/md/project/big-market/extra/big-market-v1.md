---
title: 第一阶段交付，可部署上线抽奖模块
lock: no
---

# 《大营销平台系统》第一阶段交付，可部署上线抽奖模块 —— 开局一把IDEA，全程视频手把手。

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主，小傅哥。

经过这个假期的嘎嘎卷🧨，大营销平台项目第一阶段开发完成并以上线【 在线体验地址：[https://gaga.plus](https://gaga.plus) 】。项目课程，全程视频手把手从0到1开发，**第1阶段仅需要2周学习，即可写到简历冲春招「已提供简历编写模板」**。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/openai-01.jpg?raw=true?raw=true" width="150px">
</div>

**开局一把IDEA，全程视频手把手！**

鉴于不少小白伙伴的技术积累偏弱，对照文档学习压力较大，所以这次小傅哥选择了`👨🏻‍💻全程视频手把手敲`的方式进行教学，让小白伙伴跟着课程从0到1走下来，即可完成项目开发+上线。并且小傅哥选择的项目，都是来自于公司中真实场景的业务项目，有着细腻的业务流程和对应的编程实现技巧，这样的项目非常具有竞争力，也是面试官和你有的聊的项目【全是CRUD或者和业务无关的项目，面试官也不知道问啥，只能聊点八股文】。

**做项目，就要做的完整！** 完整的经历过，需求 + 开发 + 上线，才能让伙伴们在面试中把东西讲清楚。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-v1-01.png" width="950px">
</div>

工作了十多年，参与多场的大型校招，面试了几百人，深知考察求职者的标准。接下来小傅哥就站在面试官角度来介绍下这个项目，你应该关注到业务流程和实现技术。

>文末有加入学习方式，开年活动送福利 4.0 100万 Tokens 助力学习！🉐

## 一、面试介绍

**面试介绍举例**，作为学习类项目，你可以参考以下介绍；

面试官您好，大营销平台的 Raffle 抽奖模块，是我独立负责实现的一个（学习/工作）项目，此项目模块在架构设计上运用了 DDD 分层架构和模板模式、责任链模式、组合模式、工厂模式等，这样的设计模式对业务流程进行解耦和实现。

Raffle 抽奖模块的完整开发，让我对 SpringBoot 框架技术，分布式技术栈的运用更加熟练，也把设计模式在实际场景的使用了起来，积累了丰富的设计实现经验。这写技术学习的内容，也可以更好的应对以后的开发工作。非常感谢您给我这次面试机会。

## 二、简历编写

- 项目名称：大营销平台 - Raffle 抽奖服务
- 项目架构：微服务架构、DDD 领域驱动模型、前后端分离设计
- 核心技术：SpringBoot、MyBatis、MySQL、Redis、SpringCloud/Dubbo【按需添加，只是对外的接口形式】、React、TypeScript
- 项目描述：Raffle 抽奖模块是整个大营销平台系统中非常重要的一个模块，也是本次项目中我来负责的设计和实现的模块。此模块主要以支撑各类差异化抽奖流程，如；通用抽奖、黑名单、人群、N消耗积分指定抽奖范围、抽奖N次解锁奖品等各类玩法的支持。在此系统模块的设计中运用到了模板模式、责任链模式、组合模式、工厂模式，解决代码的可扩展性，并对抽奖的计算和秒杀做了设计的优化，可以支撑单机 2c4g 服务器 1500 ~ 2000 TPS 的吞吐量。「不同服务器，带宽，以及是否还配置有环境相关，会有不同的数据效果」
- 核心职责：
    - 以PRD文档诉求和对功能的评审，设计出抽奖的领域模型功能，以及在抽奖的流程抽象上，分为；抽奖前、抽奖中、抽奖后，的节点上扩展各项行为动作。如抽奖前的人群判断、抽奖中库存扣减、抽奖后兜底奖励等。
    - 依赖于领域模型的定义，设计出抽奖库表。抽象抽奖过程为抽奖策略表、策略明细表、规则配置表、规则树动作表，这样会让抽奖更好扩展。
    - 设计模板模式定义抽奖流程标准，再在模板模式中，调用责任链完成抽奖，对于抽奖中和后的动作使用组合模式的规则树进行动态处理【支持库表配置】。
    - 在项目架构中定义统一标准的 api 由触发器层实现，在触发器层定义监听、任务、http、rpc模块，所有的行为动作，都理解为触发行为。
    - 抽奖也是一种峰值流量高的业务场景，因此在设计奖品库存扣减上，采用了 Redis decr 分段消费和加锁兜底的设计，同时对于消费成功的库存，异步队列方式 + 定时任务更新库存。这样可以不超卖的同时，又减少数据库的压力。
    - 在项目开发中熟练运用了 IntelliJ IDEA、WEbStorm、Docker、MySQL、云服务器、SSH工具，并已将项目完整部署到线上【在校伙伴可以提供线上案例版】。

## 三、项目介绍

本次项目是一个 `前后端 + Dev-Ops` 实践开发真实营销业务场景的，全栈式综合编程项目。此项目会分阶段的交付，目前开发完成的是第一阶段，相当于完整项目的第一个大模块开发完成。这样可以满足小伙伴们快速学习（14天），就可以面试使用。

### 1. 涉及技术

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-try-it-out-02.png" width="850px">
</div>

- 在项目的分阶段开发和交付中，会逐步的运用到这些技术栈。
- 同时对于系统架构和设计模式，也都使用功能的非常丰富。正是因为有这些东西，面试才有的讲。

### 2. 项目部署

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-v1-02.png" width="850px">
</div>

- 小傅哥在每个阶段开发完成后，都会带着你手把手的做服务的上线。在本次阶段完成，所需的服务器配置是2c2g 占用 61% 的内存空间。—— 这些东西只有自己做了，才知道！
- 在项目部署的讲解中也会给大家扩展各种CI&CD工具和压测，让你可以从我过往工作10+年的工作积累中，吸收经验。

### 3. 展示效果

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-v1-03.png" width="850px">
</div>

- 项目展示地址：[https://gaga.plus](https://gaga.plus) - `嘎嘎强，嘎嘎哒学` 专门展示星球「码农会锁」的实战项目效果【后续还会提供更多的不同UI的展示效果】。
- 欢迎点击尝试，也可以模拟压测下。这套买了3年的云服务器，就是给星球伙伴部署项目玩的。

### 4. 系统模块

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-15-01.png" width="800px">
</div>
- big-market-api 定义API标准，big-market-trigger 实现出3个接口；装配策略接口（调用后将抽奖策略装配到缓存）、查询奖品列表、随机抽奖接口。
- big-market-domain 体现了接口单一职责，抽象类的使用，子类的实现操作。【学过小傅哥的代码，你就知道什么是嘎嘎强！！！】

### 5. 核心流程

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-v1-04.png" width="850px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-12-01.png" width="850px">
</div>

- 复杂的业务场景，都会涉及模型的拆解、库表的涉及。拆解的部分就是功能的流程的衔接点，做好解耦动作。
- 从上面的库表中，就能看出，这一套抽奖系统，非常好扩展功能。库表设计也是非常好的经验，在面试中也经常被提起你的库表是如何设计的，新增加的功能怎么做进去。

## 四、项目大纲

**不同于网上项目，这个项目是一步步，一个个章节的带着大家从0到1的方式，进行分析、设计和开发。是一个纯手把手教大家学习实战技术的项目！** 大家可以先看看课程的大纲，就知道可以学习到哪些东西了。

- 介绍
  - [大营销平台系统介绍](https://bugstack.cn/md/project/big-market/big-market.html)
  - [面试：技能、简历、问题汇总](https://bugstack.cn/md/project/big-market/extra/big-market-try-it-out.html)
- 第1部分：需求文档
  - [第1节：营销场景的需求设计](https://bugstack.cn/md/project/big-market/prd/第1节：营销场景的需求设计.html)
- 第2部分：开发运维
  - [第1节：使用脚手架创建工程&PUSH代码](https://bugstack.cn/md/project/big-market/dev-ops/第1节：使用脚手架创建工程.html)
  - [第2节：第一阶段完成抽奖部署](https://bugstack.cn/md/project/big-market/none.html) 【第1阶段，预计14天可以学习完成】
- 第3部分：营销服务
  - [第1节：抽奖策略领域和库表设计](https://bugstack.cn/md/project/big-market/api/第1节：抽奖策略领域和库表设计.html)
  - [第2节：基础层持久化数据](https://bugstack.cn/md/project/big-market/api/第2节：基础层持久化数据.html)
  - [第3节：策略概率装配处理](https://bugstack.cn/md/project/big-market/api/第3节：策略概率装配处理.html)
  - [第4节：策略权重概率装配](https://bugstack.cn/md/project/big-market/api/第4节：策略权重概率装配.html)
  - [第5节：抽奖前置规则过滤](https://bugstack.cn/md/project/big-market/api/第5节：抽奖前置规则过滤.html)
  - [第6节：抽奖中置规则过滤](https://bugstack.cn/md/project/big-market/api/第6节：抽奖后置规则过滤.html)
  - [第7节：责任链模式处理抽奖规则](https://bugstack.cn/md/project/big-market/api/第7节：责任链模式处理抽奖规则.html)
  - [第8节：抽奖规则树模型结构设计](https://bugstack.cn/md/project/big-market/api/第8节：抽奖规则树模型结构设计.html)
  - [第9节：模板模式串联抽奖规则](https://bugstack.cn/md/project/big-market/api/第9节：模板模式串联抽奖规则.html)
  - [第10节：不超卖库存规则实现](https://bugstack.cn/md/project/big-market/api/第10节：不超卖库存规则实现.html)
  - [第11节：抽奖API接口实现](https://bugstack.cn/md/project/big-market/api/第11节：抽奖API接口实现.html)
- 第4部分：前端页面
  - [第1节：React工程创建和抽奖组件使用](https://bugstack.cn/md/project/big-market/web/第1节：React工程创建和抽奖组件使用.html)
  - [第2节：Mock接口对接抽奖页面](https://bugstack.cn/md/project/big-market/web/第2节：Mock接口对接抽奖页面.html)
  - [第3节：应用接口对接抽奖页面](https://bugstack.cn/md/project/big-market/web/第3节：应用接口对接抽奖页面.html)

- 扩展部分：面试问题

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-v1-05.png" width="850px">
    <div align="center">专门为大营销项目整你的常问面试问题，这些问题也是在小册的章节中讲解到的。小傅哥也会在后续继续编写和归档常见的面试题，助大家一臂之力！</div>
</div>

---

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-try-it-out-05.png" width="650px">
</div>

课程包括；视频、小册、1 对 1 答疑解惑、专属VIP项目交流群，并且提供简历编写模板结构的一条龙🐲服务。**让你学习后，直接拉开与还在玩具项目，其他求职者的差距。提高竞争力，面试脱颖而出！！！**

## 五、加入学习

**注意**，加入星球「码农会锁」即可学习小傅哥所有实战项目，星球类似于私有技术朋友圈，小傅哥是技术群主，为大家提供1v1的技术学习答疑。项目地址：[https://gaga.plus](https://gaga.plus) - 你可以进入后，查看星球项目。

[🧧加入学习](https://bugstack.cn/md/zsxq/other/join.html) **加入星球**：下载`星球APP`，从星球【课程入口】进入。里面有完整的学习指引，包括；使用说明、代码仓库、专属项目群、学习路线、往期项目。