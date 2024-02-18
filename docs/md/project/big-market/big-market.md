---
title: 大营销平台系统
lock: no
---

# 《大营销平台系统》—— 小傅哥第8个项目，前后端 + Dev-Ops 的全栈式综合编程实战DDD项目！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>课程：[https://t.zsxq.com/14gswKIeX](https://t.zsxq.com/14gswKIeX)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=453794718&bvid=BV1s5411e7Z2&cid=1406321377&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

大家好，我是技术UP主小傅哥。

💐又到了启动新项目的时候，死鬼开心嘛。小傅哥的**星球：码农会锁**，第8个应用级实战项目开启啦！—— 在这之前小傅哥已经完结了7个实战项目，可进入 [https://gaga.plus](https://gaga.plus) 嘎嘎强平台，体验项目。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-230905-01.png?raw=true" width="150px">
</div>

呐，接下来，小傅哥要带着大家做一个什么项目呢？🤔

这个新项目，结合小傅哥已经带着大家完成的 OpenAi 大模型应用业务场景，做上层的营销活动。这就像互联网公司中有了电商、外卖、出行等场景一样，在场景之上做营销活动。所以我们的新项目是 **《大营销平台系统》**！因为小傅哥的星球之前做过了一个抽奖，那么这个项目会用新的DDD架构，对抽奖系统进行重构，并扩展出`营销账户`、`用户返利`、`积分兑换`等服务，完成一整套的营销平台功能。💥

小傅哥把互联网中真实的场景、架构、实现，拿出来让你成体系化的学习；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-01-02.png" width="750px">
</div>

这里抽奖模块通过RPC接口，对接到大营销平台。这里不只是抽奖，还要串联账户、奖品、返利等各项内容。接下来，小傅哥就着重介绍下这套信息项目的重点，让大家可以知道学习到哪些知识，掌握哪些技术。

>文末有加入学习方式，还有优惠券可以使用。先到先得！

## 一、能学到啥

在各大互联网公司中，营销平台都是那个流量最大，场景最复杂的系统，也是需求迭代最多还最快系统。在这个部门的研发伙伴，谁身上都是背着“几个事故”锻炼出来的技术。所以，跟着小傅哥学习这样一套系统，是可以学习到非常多的技术。包括；—— `以往的学习，你可能有很多技术栈使用的缺失，甚至也没接触过有高质量的架构和设计模式编码。那么在折腾学习旅程中，这些内容你都将学到。`

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-try-it-out-02.png" width="850px">
</div>

- 【前端】熟练使用 React、Typescript 在前端工程中开发营销活动页。
- 【前端】熟练掌握，跨域接口请求，以及通过浏览器指纹技术实现防刷。
- 【前端】熟练使用，Ant Design Pro 开发后台运营管理系统。
- 【后端】熟练搭建 DDD 工程项目、以及 DDD 脚手架搭建项目。并对 DDD 设计方法有清楚的认知。
- 【后端】熟练掌握 Spring、SpringBoot、MyBatis 等开发框架技术，并对框架源码所提供的扩展接口具备运用能力。
- 【后端】熟练运用分布式技术栈，包括：Dubbo、RocketMQ、Redis、XXL-JOB、Sharding-JDBC、Nacos等。
- 【后端】熟练使用多种设计模式、设计原则，对各类场景的方案设计和落地能力，深度提高自身编码思维和开发技术能力。
- 【后端】深度学习复杂场景的架构设计、编程思维，如果处理系统功能的边界和上下文的维护。—— 这些东西一定是从实践中才能学习到的。
- 【后端】熟练使用 Mock 单测工具、JMeter 压测工具，增强代码交付质量。
- 【后端】熟练掌握异常、枚举、错误码的定义和使用，并学习到如何合理打印服务日志，便于问题排查。
- 【运维】熟练使用 Docker 在本地和服务端的配置和部署应用，以及在本地构建前后端镜像。
- 【运维】熟练掌握 Git、GitCode，对工程代码的管理，推送、拉取、切换分支、合并代码等操作。
- 【运维】熟练使用 Nginx 配置转发服务，并能申请ssl配置https服务。
- 【运维】熟练使用 Grafana 监控系统，对系统的 JVM、磁盘、Tomcat、应用(QPS、响应时间、调用量)完整监控。

此外，小傅哥会把系统开发过程中的思考、设计、编码，录制⏺成完整的视频，让大家可以学习到的更多、更细、更深！

>像这样的营销复杂场景项目，势必会引入相关分布式技术栈的使用。并且营销会根据业务流程拆解出对应的微服务系统，包括；券、活动、拉新、抽奖、积分、兑换、灌券、返利等各个平台，这些微服务间通过 RPC 进行通信。又使用 MQ 解耦、任务补偿，以确保微服务内事务一致性，微服务外最终一致性。

## 二、项目介绍

本次项目是一个包括 `前后端 + Dev-Ops` 的全栈式综合编程实战项目，基于 React + SpringBoot + 分布式技术栈 + Nginx + Grafana + Docker 云服务，开发、部署、上线、监控的《大营销平台系统》项目。

大营销平台项目属于营销组最为核心的项目，承接流量最大、系统设计最复杂、需求迭代最多，也是最容易出事故的组。🤨**在这个组1年的技术成长 = 其他组3年！**

营销组的项目是最早触达用户的，打开页面、优惠选券、组合支付、积分活动、分享收益、拉新返现等，都是先进入营销系统，完成相关的动作才是商品的选择和支付。你几乎能在购物、出行、金融、音视频娱乐中，都有对应的营销玩法活动。所以这块系统`流量最大`、`需求最多`、`功能最复杂`！—— **每个公司都有营销组在加班！💐**

抖音、京东、滴滴、拼多多，都有这样的项目；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-try-it-out-01.png" width="400px">
</div>

虽然需求杂，需求多，但这样部门组里的项目是非常锻炼人的。所以小傅哥这次也开启了一个 **《大营销平台项目》** 带着你一起学习复杂的场景架构设计和高级编码落地经验。完全从0到1，手把手的带着你思考、设计、编码，完成项目！


### 1. 业务核心流程

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-01-03.png" width="750px">
</div>


### 2. 模型设计高质量

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-try-it-out-03.png" width="850px">
</div>

### 3. 系统工程规范化

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-05-03.png?raw=true" width="850px">
</div>

### 4. 单一职责抽象化

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-06-01.png?raw=true" width="850px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-06-03.png?raw=true" width="850px">
</div>

### 5. 设计模式场景化

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-08-02.png?raw=true" width="850px">
</div>

### 6. 项目工程列表

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-01-05.png" width="750px">
</div>

### 7. 业务监控示例

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-01-04.png" width="750px">
</div>

- 目前是 OpenAi 业务系统监控，大营销平台系统上线后，会把这部分监控一起添加上。
- 项目，只有上线 。你才会注意到很多的细节，就像 Tomcat 的最大连接数，如果不开发超时熔断，在接口异常超时等待的情况，就有可能把连接数打满。

## 三、项目大纲

不同于网上的小Demo项目，这个项目的场景来自于互联网真实业务需求，一个个章节、一步步流程的带着大家从0到1，需求分析、工程设计和代码实现。是一个纯手把手教大家学习实战技术的项目！

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-try-it-out-05.png" width="850px">
</div>

课程包括；视频、小册、1 对 1 答疑解惑、专属VIP项目交流群，并且提供简历编写模板结构的一条龙🐲服务。让你学习后，直接拉开与还在玩具项目其他人的差距，面试脱颖而出提高竞争力！！！

## 四、加入学习

**注意**📢，本项目也只是【星球：码农会锁】众多项目中的1个，其他的项目还包括：OpenAi大模型应用项目、API网关、Lottery抽奖、IM通信、SpringBoot Starter 组件开发、IDEA Plugin 插件开发等，并还有开源项目学习。

如果大家希望通过做有价值的编程项目，提高自己的编程思维和编码能力，可以加入小傅哥的【星球：码农会锁】。加入后解锁🔓所有往期项目，还可以学习后续新开发的项目。

>这样一套项目，放在一些平台售卖，一个至少都是几百上千。但小傅哥的星球，只需要100多，就可以获得全部的学习项目 [https://gaga.plus](https://gaga.plus)！

[🧧加入学习](https://bugstack.cn/md/zsxq/other/join.html) **加入星球**：下载`星球APP`，从星球【课程入口】进入。里面有完整的学习指引，包括；使用说明、代码仓库、专属项目群、学习路线、往期项目。

