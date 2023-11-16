---
title: 小册介绍
lock: need
---

# 小傅哥 DDD 编程开发小册，帮助大家更好的落地实践

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

如果在面试的时候，面试官问你DDD是什么，你怎么解释？是不是感觉DDD的资料也看了不少，但好像还没有一个定义给DDD，所以炸一听这有的问题的时候，还真有点慌。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-introduce-01.jpg?raw=true" width="200px">
</div>

那DDD是什么呢？🤔

**关于DDD是什么，在维基百科有一个明确的定义。"Domain-driven design (DDD) is a major software design approach." 也就是说DDD是一种主要的软件设计方法。而软件设计涵盖了；范式、模型、框架、方法论。**

- 范式（paradigm）指的是一种编程思想。
- 模型（model）指的是对现实世界或者问题的抽象描述。
- 框架（framework）指的是提供了一系列通用功能和结构的软件工具。
- 方法论（methodology）指的是一种系统的、有组织的解决问题的方法。

所以，DDD不只是只有指导思想，伴随的DDD的还包括框架结构分层。但说到底，这些仍然是理论讨论。在没有一个DDD落地项目物参考下，其实大部分码农是没法完成DDD开发的。所以小傅哥今年花费了5个月假期/周末的时间，完成的《DDD简明开发教程》，帮助大家落地DDD编码。

>文末可以获取到基于DDD架构开发的实战项目。

## 一、能学到啥

因为DDD本身有很多的方法论，指导着框架结构的设计，从而控制着工程模块间的平衡。这种平衡的约束，起到了很好的防腐作用，天然的控制了职责的分离。不过也因为有了这些约束，很多初次上手DDD的研发伙伴不知道怎么下手了。所以以下这些内容，则是帮你进入DDD开发；

1. 为你，提供整个DDD简单版、DDD标准版，脚手架使用，并配合提供 docker compose 一键开发环境安装。这样你再使用的时候就可以更加容易进入了。
2. 为你，深度讲解DDD分层结构、DDD全模块调用链路、MVC2DDD的升级关闭和对比、DDD领域模型(账户/支付)的设计和编码。
3. 为你，拆分独立案例，讲解应用启动、配置加载、各类技术框架和分布式技术栈，在DDD分层结构下具体到每个模块中的使用手段。如；MyBatis、Dubbo、RocketMQ、Redis、Zookeeper、Sharding-JDBC、XXL-Job等。—— 这些案例都来自于业务场景中的常用使用方式，非常具有代表性。
4. 为你，讲解工程开发中常用类库和工程的测试压测处理方案。如；Guava、OKHTTP、Mock、JMeter、AB、Siege等。这些东西是为了更好的完成代码交付。
5. 为你，工程开发最后的阶段，提供；Docker、Portainer 云服务操作，以及包括对工程的全链路监控学习。此外基础的 IntelliJ IDEA、Maven、Git、Github/Gitcode/Gitee，的使用教程。

以上，这些内容的学习，可以让你既掌握DDD架构的开发使用，又能快速的学会各项基础技术栈和分布式技术栈的运用，还能学习到监控、压测、云服务操作。可以说是一举多得！嘎嘎强！

## 二、小册演示

可以这么说，这套DDD的小册课程是非常基础入门，又覆盖得极其全面。哪怕是小白研发，也能完全跟着走下来。既有文章又有视频，每一个小知识点都是一个独立的案例讲解。以下就是DDD小册内的一些截图，可以参考看下。

### 1. 工程结构模型

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-04.png?raw=true" width="650px">
</div>

### 2. 架构对比参照

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ddd-02.png?raw=true" width="650px">
</div>

### 3. 模块调用链路

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ddd-05.png?raw=true" width="950px">
</div>

### 4. 领域模型设计

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-mybatis-02.png?raw=true" width="650px">
</div>

### 5. 系统压测讲解

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jmeter-05.png?raw=true" width="650px">
</div>

### 6. 应用监控部署

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230617-01.png?raw=true" width="850px">
</div>

>对此DDD小册感兴趣的伙伴，也可以进入小傅哥的B站进行视频学习。

## 三、小册大纲

小傅哥在初学阶段也看过网上的案例，可以说是鱼龙混杂，如果刚好自己是初学还不太懂，那么几乎要花费整个1天的时间，一遍遍百度搜索出各种有毛病的案例。才能完成自己的学习。所以基于这样的经历，小傅哥提供了完整的、前面的、成体系化的一整套学习案例。

- 工程脚手架(1)
   - DDD 脚手架

- 系统架构(5)
   - [MVC 架构](#)
   - [DDD 架构](#)
   - [MVC2DDD - 架构重构](#)
   - [DDD 架构 - 账户域](#)
   - [DDD 架构 - 交易域](#)

- 开发环境(6)
   - [IntelliJ IDEA](#)
   - [Maven](#)
   - [Git](#)
   - [Github](#)
   - [Gitcode](#)
   - [Gitee](#)

- 开发技术(11)
   - [MyBatis](#)
   - [Dubbo](#)
   - [RocketMQ](#)
   - [Quartz & XXL-Job](#)
   - [MySQL](#)
   - [db-router](#)
   - [sharding-jdbc](#)
   - [ConnectionPool](#)
   - [Zookeeper](#)
   - [Redis](#)
   - [Ignite](#)

- 常用类库(3)
   - [fastjson](#)
   - [guava](#)
   - [http](#)

- 工程测试(2)
   - [Mock](#)
   - [JMeter](#)

- 质量监控(1)
   - [skywalking 全链路监控](#)

- 发布部署(2)
   - [Docker](#)
   - [Portainer](#)

---

全小册目前发布了31节内容，分为脚手架、架构、环境、技术、类库、测试、监控、部署，8部分内容。这样整个一条龙🐲的学习，可以让大家对于技术的积累更为扎实。以后还会结合实际场景所需，扩展小册内容。如果有小伙伴需要用到的内容，不在这里，也可以提出建议或者贡献。💐

## 四、编程经验

1. 并不一定非得需要所有人一起做领域设计。一个研发有时候就够。`因为铁打的研发，流水的产品`。最熟悉系统和业务的，往往是最核心的研发。尤其是大公司变动较多的情况下，每每产品更替，都需要与研发请教整体流程。
2. DDD难落地，不是DDD有多难，是没经验的人做DDD，不清楚范式、模型、框架、方法论，只能抱着理论给团队伙伴讲，并且在很多细节使用上没法把控，导致越来越难维护。`上DDD==上一坨屎`。
3. 不是非得多复杂的系统才能用DDD，简单系统使用DDD，也会更好的维护。因为很多没彻底做过DDD的开发的，甚至并不知道domain的核心是什么，眼里只有聚合、聚合、聚合。
4. 能在MVC结构下写代码不错的人，到DDD可以写的更好。DDD只是软件设计方法的改变，但不决定原本代码垃圾的人，换个结构代码就牛逼了。
5. 不具备DDD结构设计和调整的研发，会生搬硬套DDD。所以没法根据自身业务需求调整分层的结构，如在domain领域编排复用极低的场景，可以去掉application/case层，这样会减少大量的对象转换。
6. 千万别说设计模式没用，好好看看一些高质量的框架源码，对于；分治、抽象、知识(设计原则、设计模式)的运行，是有多好。没用设计模式的DDD，就像没有家具的四居室，卧室里可以安装马桶。厨房里可以放个床。

## 五、加入学习

注意📢，DDD开发小册为小傅哥提供的免费学习资料，提供了基础内容的讲解。此外加入小傅哥【星球：码农会锁】还有DDD应用级实战项目课程，通过这些课程的实操，对实际业务场景进行架构、设计、编码、上线，更加扎实的掌握DDD领域驱动设计的运用。项目体验地址：[https://gaga.plus](https://gaga.plus) - 小傅哥为星球项目部署的演示平台。

- 【免费】- DDD开发小册；[https://bugstack.cn/md/road-map/road-map.html](https://bugstack.cn/md/road-map/road-map.html)
- 【付费】- DDD实战项目；[https://t.zsxq.com/14NGs0bGU](https://t.zsxq.com/14NGs0bGU) - `OpenAi 大模型应用`、`Lottery 分布式抽奖`、`Api网关`、`IM通信`等

>这样一套全体系的实战项目，放在一些平台售卖，至少都是几百块。但小傅哥的星球，只需要100来块钱，就可以获得几千元的学习项目！

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-youhuiquan.png?raw=true" width="350px">
</div>

小傅哥的【星球：码农会锁】，是一条龙🐲成体系的学习，因为这些内容都是小傅哥原创编写，所以学习起来的衔接性会非常强，有紧有收、循序渐进！欢迎加入，可别错过！

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-introduce-02.png?raw=true" width="750px">
</div>
