---
title: 给兄弟们，搞了个“小电商”项目，对接微信扫码支付！
lock: no
---

# 给兄弟们，搞了个“小电商”项目，对接微信扫码支付！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

经过10.1国庆假期💐，小傅哥在 [星球：码农会锁](#) 发布的 OpenAI 课程项目中，新嵌入了一个小型电商系统。支持用户商品下单，微信扫码支付，异步额度充值的全流程实现。**而且这套支付已经上线运行，卷的就是真实！**

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-231007-01.png?raw=true" width="200px">
</div>

此项目对接支付开发小电商，会采用DDD领域分层实现，非常具有学习价值。

此前，在面试中我看到过众多伙伴写的电商或外卖项目中，写到了支付。也因此拉高了对求职者面试的兴趣。但实际面试下来，大部分校招简历的支付都是模拟的支付，或者说根本没有对接过支付。从而导致面试中，问到；`支付流程`、`掉单处理`、`发货补偿`等真实场景，都没法回答。所以也就高开低走了！

那么鉴于这样的情况，小傅哥就想在一个项目中，缩小电商场景后，重点突出支付流程的设计实现。并且在整个支付的设计中，采用领域驱动设计的思想进行设计和实现。让你既有丰富的业务流程知识、也有厚实的方案实现。

**当我拿出提交记录，阁下又该如何应对！🤨** 

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-231007-02.png?raw=true" width="650px">
</div>

此课程内容非常丰富，包括能学习到的技术栈内容、简历编写案例、上线运行和监控都有。[☞可点击查看详细介绍](https://bugstack.cn/md/project/chatgpt/extra/ChatGPT-v1.2.html)

那么，接下来小傅哥就重点的介绍下，在领域驱动设计下，商品下单支付如何实现。

>文末有加入项目学习方式，加入后可以学习工程代码。此外加入还有其他6个项目学习、源码学习、简历优化等服务！

## 一、商品支付效果演示

此项目具有 `前后端 + Dev-Ops` 全栈开发实践，采用 DDD 架构设计落地，运用设计模式编写整洁的代码。并结合 OpenAi 技术、微信支付渠道，做产品化的设计和实现。非常具有学习价值！

### 1. 商品页

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-231007-07.png?raw=true" width="750px">
</div>

### 2. 支付页

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-231007-08.png?raw=true" width="750px">
</div>

### 3. 对话页

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-231007-09.png?raw=true" width="750px">
</div>

接下来，小傅哥重点介绍下整体的领域模型和商品支付相关的详细设计。也能让伙伴和一些其他项目做个对比，这样小而美、小而精的项目，设计的到底有多赞👍🏻

## 一、项目包含的领域

星球中的 OpenAI 项目，服务功能已经越来越完善了。既能`满足校招当面试项目`，也能`学习架构当深度扩展`。而且这套 DDD 架构是互联网公司中经过实践，非常好落地的架构设计选择。**支付体验地址：https://openai.itedus.cn/#/mall**

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-api-08-01.png?raw=true?raw=true" width="750px">
</div>

- 左侧，是整个项目所涉及的领域服务，包括；生成式服务、权限校验、微信支付、用户账户、订单交易、商品。构成整个服务功能。
- 右侧，以用户视角下，了解各个领域的上下文关系。从微信对接、权限校验、再到生成式服务和账户域使用。以及本节要实现的订单交易域。

## 二、下单支付，怎么设计？

做业务功能开发，我们可以先只思考核心的主流程。那么本节要做的功能，最核心的就是用户选择商品下单，之后生成一个支付URL，用户扫码支付。再接收到支付成功回调后，把用户购买的订单发货【额度充值】。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-api-08-02.png?raw=true" width="750px">
</div>

这是一个非常核心的主流程。有了主流程，我们在思考下可能出现的异常流程。如；

1. 用户订单创建成功，但创建支付单 HTTP 超时失败。
2. 支付回调时，系统宕机或者本身服务出问题。
3. 支付成功后发送MQ消息，消息丢失，用户支付掉单。
4. 长时间未支付，超时订单。

那么，这些就都是可能出现的异常流程。虽然概率很低，但随着使用规模的增加，很低概率的问题，也会产生较大规模的客诉问题。所以要针对这些流程做补偿处理。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-api-08-03.png?raw=true" width="750px">
</div>

- 针对1~4提到异常流程，一条支付链路就会被扩展为现在的样子，在各个流程中需要穿插进入异常补偿流程。
- 用户下单，但可能存在之前下的**残单**，那么就要对应给予补充的流程后，再返回回去。
- 支付回调，仍然可能有异常。所以要有掉单补偿和发货补偿。两条任务处理。

## 三、DDD工程结构设计

说了业务流程，又提到了 DDD 领域驱动设计，那么接下来咱们看看系统的工程分层结构。有了这个东西，才能感受到 DDD 的设计。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-231007-03.png?raw=true" width="550px">
</div>

- 一个小型充值类电商，至少需要3个表；用户账户、商品表、订单表。那么流程则为，用户选择商品下单，支付成功后对个人账户进行充值使用。
- 基于这样的流程在工程的 domain 领域中，加入 order 领域表。
- 就说，学习过的项目，看过的工程结构。有如此清晰整洁的吗？

## 四、代码实现设计和细节体现

业务流程说了、工程结构看了。接下来要看看代码在DDD模型结构是怎么体现的。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-231007-04.png?raw=true" width="750px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-231007-05.png?raw=true" width="750px">
</div>

- 如图，是整个流程支付下单场景在领域中的体现，包括；模型对象、仓储、服务的各层体现。
- 模型，定义整个订单领域中所需的；聚合、实体和值对象。实体对象为商品实体、订单实体、购物车实体等，之后在聚合中包装商品和订单。
- 仓储，是对数据库的调用接口，在 domain 层定义接口，在基础层做数据的封装和返回。像是聚合对象基本就是一个事务的体现，交给仓储中做事务处理。
- 服务，这里才是最终的充血。以前MVC贫血结构中，对象和服务，是远远的被分离的。现在把一组服务所需的对象、数据库操作、事件处理都封装到一个包下。那么实现出来的逻辑才是充血模型。此外，可以重点看下，定义订单接口。是以购物车实体对象为入参，返回支付单为结果的场景订单方法。看似简单，但如果写的很乱的代码，是不会注重这些细节的。但不注意这些细节，等着被持续的时间和迭代的需求逐步放大以后，就会成为严重的代码腐化问题。

## 五、加入学习

**注意**📢，本项目也只是【星球：码农会锁】众多项目中的1个，其他的项目还包括：API网关、Lottery抽奖、IM通信、SpringBoot Starter 组件开发、IDEA Plugin 插件开发等，并还有开源项目学习。

如果大家希望通过做有价值的编程项目，提高自己的编程思维和编码能力，可以加入小傅哥的【星球：码农会锁】。加入后解锁🔓所有往期项目，还可以学习后续新开发的项目。

>[🧧加入学习](https://bugstack.cn/md/zsxq/other/join.html)

已经有很多伙伴开始学起来了，还有大家交的作业笔记。有了的项目驱动学习，清晰的目标感，大家冲起来也有了更明确的方向！干干干！！！

---

此课程，目前已经编写了28节课程，每节课程都有对应的视频。手把手带着大家完成一个从0到1的实战项目。需求明确、架构牛皮、代码整洁、还有视频、又能上线的好项目！赶紧学习起来！

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-231007-06.png?raw=true" width="750px">
</div>
