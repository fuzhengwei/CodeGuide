---
title: 拼团交易平台逆向流程启动
lock: no
---

# 拼团交易平台逆向流程启动

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

经过小傅哥这么多年的努力，👨🏻‍💻认认真真的做编程项目之下，一点点把来自于互联网真实技术教给社群伙伴。现在也是行了起来。在大学里`优秀毕业生分享上`，也能被伙伴认可，拿出来写到黑板上啦！

<div align="center">
    <img src="https://bugstack.cn/images/article/project/group-buy-market/group-buy-market-promotion-250629-01.png" width="450px">
</div>

**他们是这么评价小傅哥的！**

小傅哥在大型互联网，`toc业务场景，工作这么多年`，也确实积累了丰富的业务+技术经验。也深知，对于一个从事此行业的编程人员，应该学习哪些项目才能快速成长。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/group-buy-market/group-buy-market-promotion-250629-02.png" width="750px">
</div>

为此，小傅哥做了一整套项目，`由浅入深，循序渐进`。以不同难度级别的项目，带着小伙伴们稳扎稳打的成长。有了这样的真实场景学习，`既可以`提高自己的编程思维和编码能力，`又能`在面试中讲解出各种场景方案，`还能`为以后入职进入到公司快速融入团队。可以说是，一举多得。

> 🧧 文末提供了全套的实战项目，含有；业务项目、组件项目、创新AI项目，各项目对应的文档、视频、代码，全部提供给大家学习使用。

## 一、项目介绍

**于24年末，小傅哥以线上真实toc业务场景**，设计了一套 **《拼团交易平台系统》**，帮助大家深入学习，`前后端技术`、`分布式架构`、`微服务对接`、`Dev-Ops 发布上线`、`AI MCP + ELK + 普罗米修斯监控`、通用组件扳手工程设计等。可以说学习这样一套项目，就能完整的积累各项互联网toc场景常见方案和通用技术。

像是这样的项目，基本大家在使用各个互联网app的时候，都能体会到拼团的应用。如；`美团拼单`、`滴滴拼券`、`京东/拼多多购物`、`腾讯`等，都可以看到。所以这也是面试中面试官非常喜欢聊的业务和实现方案。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/group-buy-market/group-buy-market-promotion-v5-01.png" width="750px">
</div>

现在这套项目已经讲解了38节课程，所有的拼团正向流程 + 分布式方案 + Dev-Ops 部署和监控，都已完成。接下来将开启逆向退单流程。也就是7月5日，周末开启。

## 二、业务流程

如图，以用户旅途视角来看整个拼团流程。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/group-buy-market/group-buy-market-2-25-01.png" width="700px">
</div>

- 如图，以上为用户旅途视角的全流程。从运营配置拼团活动，到用户从「小型支付商城（对接的一个场景）」，开始查看带有拼团优惠的上，进行试算，过滤规则。再到参与拼团，完成下单和一系列的流程处理。之后是拼团对于支付收单的入账计算，达成拼团目标后，回调（HTTP/MQ）商城服务，完成整个交易过程。
- 之后，我们要考虑的是如何处理逆向流程，也就是退单的过程。退单分为当前过程中，拼团是否完成，未完成则根据是否支付了，取消锁单量和完成量。如果拼团已完成，则取消锁单量和完成量，拼团优惠释放后，则回调商城（refundGroupBuySuccess）,完成退单退货服务。
- 一般，对于已经完成拼团的，有用户退单是不会对其他用户已经完成交易的进行退单的，会造成很差的体验。这部分成本往往由商家和平台分摊，毕竟平台的目的是为了卖货。

## 三、详细设计

### 1. 服务对接

<div align="center">
    <img src="https://bugstack.cn/images/article/project/group-buy-market/group-buy-market-3-4-01.png" width="700px">
</div>

### 2. 正向流程

<div align="center">
    <img src="https://bugstack.cn/images/article/project/group-buy-market/group-buy-market-2-25-04.png" width="700px">
</div>

### 3. 逆向流程

<div align="center">
    <img src="https://bugstack.cn/images/article/project/group-buy-market/group-buy-market-2-25-03.png" width="700px">
</div>

以上，是整个`拼团系统` + `小型支付`的对接和正逆向流程设计。正向流程，已经全部完成。后面我们就要开启逆向流程啦，带着大家完完整整的把项目全部做完。

以上的流程分析，在课程中会有专门的视频讲解，一步步带着大家分析所有过程，以及各项细节。之后在技术社群的伙伴，完成所有的编码。



