---
title: 第1-2节 DDD 建模方法
pay: https://t.zsxq.com/teMSw
---

# 《小型支付商城系统》第1-2节 DDD 建模方法

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

四色建模（风暴事件）是整个 DDD 这套软件设计方法中用于工程拆分界限上下文的非常重要的实践手段。通过建模过程快速识别业务领域中的关键事件和核心流程，也是在这个过程中设计出领域对象的，为后面详细设计和代码开发做指导。

你可以把整个过程理解为，为工程开发提供面向对象设计，涵盖；领域拆分、界限串联、功能聚合。所以相比`Service + 数据模型`的贫血开发方式，**DDD 前期需要付出更多的设计成本，但对于软件的长周期迭代，这样的好处是非常大的。**

## 1. 建模目的

工程的建模的目的是为了我们做工程开发时提供指导方案，就像一栋大楼的设计蓝图一样，也像一个超市中会有不同品类的货架，需要提前规划好。所以你需要在工程开发时所需的各类核心内容，都会在建模中体现，如；分几个包、有哪些核心对象、要串联什么流程、有哪些核心业务要实现、过程中与外部服务的交互。

那么为了达成一个讨论的共识，而不是每个人都有一套的标准和词汇。所以会使用 DDD 提供专门的建模方法和名词进行统一的设计，此外因为 DDD 的统一建模语言，不涉及技术编码，也具有通用性，所以可以在建模过程让产品、研发、测试、架构师等人员一起参与讨论。如；领域、领域模型（实体、聚合、值对象）、领域服务、端口适配器、仓储、界限上下文、领域编排等名词。*这在上一节已经做了相关的解释。*

## 2. 怎么建模

DDD 的建模过程，是以一个用户为起点，通过行为命令，发起行为动作，串联整个业务。而这个用户的起点最初来自于用例图的分析。用例图是用户与系统交互的最简表示形式，展现了用户和与他相关的用例之间的关系。通过用例图，我们可以分析出所有的行为动作。

在 DDD 中用于完成用户的行为命令和动作分析的过程，是一个四色建模的过程，也称作风暴模型。在使用 DDD 的标准对系统建模前，一堆人要先了解 DDD 的操作手段，这样才能让产品、研发、测试、运营等了解业务的伙伴，都能在同一个语言下完成系统建模。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/roadmap-ddd-stc-08.png" width="750px">
</div>

此图是整个四色建模的指导图，通过寻找领域事件，发起事件命令，完成领域事件的过程，完成 DDD 工程建模。

- 蓝色 - 决策命令，是用户发起的行为动作，如；开始签到、开始抽奖、查看额度等。
- 黄色 - 领域事件，过去时态描述。如；签到完成、抽奖完成、奖品发放完成。它所阐述的都是这个领域要完成的终态。
- 粉色 - 外部系统，如你的系统需要调用外部的接口完成流程。
- 红色 - 业务流程，用于串联决策命令到领域事件，所实现的业务流程。一些简单的场景则直接有决策命令到领域事件就可以了。
- 绿色 - 只读模型，做一些读取数据的动作，没有写库的操作。
- 棕色 - 领域对象，每个决策命令的发起，都是含有一个对应的领域对象。