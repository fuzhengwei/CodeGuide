---
title: 架构：DDD 领域驱动设计
lock: no
---

# 架构：DDD 领域驱动设计，战略、战术、战役，落地指引规范。

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>课程：[https://t.zsxq.com/14gswKIeX](https://t.zsxq.com/14gswKIeX)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?isOutside=true&aid=1154849743&bvid=BV1yZ421s7En&cid=1550075616&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

大家好，我是技术UP主小傅哥。

👨🏻‍💻 经过5.1假期的一顿框框输出，终于完成了[《大营销项目》](https://bugstack.cn/md/project/big-market/big-market.html)第二阶段的开发和上线，体验地址：[https://gaga.plus](https://gaga.plus) 有了这个项目的落地，也终于可以给大家完整的梳理出一套 DDD 落地指引规范。包括；战略、战术、战役，各个阶段都要做什么，`怎么做风暴模型和四色建模`。有了这套东西参考，小白也能目标明确的做 DDD 项目开发啦！

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/roadmap-ddd-stc-01.png" width="550px">
</div>

**我一直讲，要先实践，再理论！**

编程，偏理科的东西要先上手实践，再做理论理解。因为所有的理论提出，也都是建立有了实践结果后，抽象出来的理论。但你上来就要用理论去反推结果，并不是一件容易的事情。就像不少的 DDD 文章，往往会用一个理论，去讲另外一个理论，这也导致很多没有实践过的小白伙伴，压根不知道讲的是什么。最终觉得 DDD 太难！

所以在近2年的时间里，小傅哥分享了非常多的 DDD 实践内容，包括；DDD 工程结构&脚手架、各项分布式技术栈在 DDD 结构下的使用、DDD 实战项目&小场景训练。这些内容都可以在 [bugstack.cn](https://bugstack.cn) 学习。

接下来，小傅哥会带着你走一遍`研发设计评审`，讲解 DDD 落地项目的全部过程。

>文末有对应本项目的 DDD 工程代码地址，理论 + 代码 + 视频，学的嘎嘎透彻！

## 一、战略、战术、战役

首先 DDD 是一种软件设计方法，[Domain-driven design (DDD) is a major software design approach.](https://en.wikipedia.org/wiki/Domain-driven_design) 来自维基百科。软件设计方法涵盖了；范式、模型、框架、方法论，主要活动包括建模、测试、工程、开发、部署、维护。来自维基百科的[软件设计](https://en.wikipedia.org/wiki/Software_design)涵盖信息介绍。

在 DDD 领域驱动设计中，常提到`战略`、`战术`，和一少部分会讲到`战役`。这3个词主要讲的是不同的开发阶段所需要完成的事项；

- 战略 - 建模；领域划分、界限上下文、核心领域
- 战术 - 架构；工程结构、领域对象、领域服务、领域事件
- 战役 - 编码；设计原则、设计模式

DDD 的战略、战术和战役设计相辅相成，战略提供系统的建模作为宏观指导，战术下面有N个战役，两者则关注具体的实现和编码落地。

在维基百科中有不少 DDD 非常好的资料，其中一个是关于事件风暴的，讲解了执行战略设计中风暴模型的步骤。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/roadmap-ddd-stc-03.png" width="650px">
</div>

>有了这基础认知，接下来我们通过《大营销项目》从需求到设计，一步步了解系统的领域驱动设计。

## 二、产品需求

### 1. 产品诉求

如图，是一个复杂的营销抽奖场景玩法需求，涵盖了；`活动配置`、`签到&奖励`、`活动账户`、`抽奖策略「责任链+规则树」`、`库存扣减`、`抽奖满N次后阶梯抽奖`等。面对这样的复杂系统，非常适合使用 DDD 落地。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/roadmap-ddd-stc-02.png" width="550px">
</div>

分析需求；

1. 整体概率相加，总和为1或者分值计算，概率范围千分位
2. 抽奖为免费抽奖次数 + 用户消耗个人积分抽奖
3. 抽奖活动可给用户分配可抽奖次数，通过点击签到发放
4. 活动延伸配置用户库存消耗管理，单独提供表配置各类库存
   用户可用总库存、用户可用日库存
5. 部分抽奖规则，需要抽奖n次后解锁，才能有机会抽取
6. 抽奖完成增加（运气值/积分值/抽奖次数）记录，让用户获得奖品。
7. 奖品对接，自身的积分、内部系统的奖品
8. 随机积分，发给你积分。
9. 黑名单用户抽奖，则给予固定的奖品。

### 2. 业务流程

依照于产品需求，在产品的 PRD 文档中还会绘制出业务流程图。产品的流程图会比较粗一些，研发后期需要根据产品的 PRD 文档做具体的设计。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/roadmap-ddd-stc-04.png" width="650px">
</div>

- 产品经理会详细的介绍整个系统的功能流程和需要对接接口文档。
- 以上就是以用户旅程为维度，从点击签到获得活动账户额度，再到一些列抽奖、抽奖策略、中奖结果和奖品发放的流程。

## 三、系统架构

如果首次承接的是一个新的系统，还需要对系统进行架构设计，是单体架构还是分布式架构，以及所要用到的技术栈。最好在提供好相关的落地案例和DDD脚手架。—— 没有这些东西，就想说点理论，就让团队用DDD写代码，那就是天方夜谭！*你都没写出DDD代码，兄弟👬🏻哪里去复制！*

**资料**：—— 详细介绍了 DDD 落地的案例和通用的脚手架。
- DDD 架构：[https://bugstack.cn/md/road-map/ddd.html](https://bugstack.cn/md/road-map/ddd.html)
- MVC2DDD：[https://bugstack.cn/md/road-map/mvc2ddd.html](https://bugstack.cn/md/road-map/mvc2ddd.html)
- DDD 脚手架：[https://bugstack.cn/md/road-map/ddd-archetype-maven.html](https://bugstack.cn/md/road-map/ddd-archetype-maven.html)

### 1. 分布式架构

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/roadmap-ddd-stc-05.png" width="750px">
</div>

### 2. 分布式技术

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/roadmap-ddd-stc-06.png" width="750px">
</div>

## 四、战略设计

不少伙伴，都讲过不知道怎么开始 DDD，主要是拿到一个需求，不知道从哪下手，也不知道那些领域的模型是怎么弄出来的。好，这次小傅哥就给你整个完整的案例，告诉你如何开始。

### 1. 用例图

根据业务需求画系统用例图；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/roadmap-ddd-stc-07.png" width="650px">
</div>

- 用例图（英语：use case diagram）是用户与系统交互的最简表示形式，展现了用户和与他相关的用例之间的关系。通过用例图，人们可以获知系统不同种类的用户和用例。用例图也经常和其他图表配合使用。
- 用例图，也可以等同于是用户故事（英语：User story）（软件开发和项目管理中的常用术语），主旨是以日常语言或商务用语撰写句子，是一段简单的功能表述。以客户或使用者的观点撰写下有价值的功能、引导、框架来与使用者进行互动，进而推动工作进程。可以被认为是一种规格文件，但更精确而言，它代表客户的需求与方向。以该用户故事来反应对象在组织内的其工作职责、范围、需要进行的任务等。用户故事在敏捷开发方法中用来定义系统需要提供的功能和实现需求管理。
- 尽管用例本身会涉及大量细节和各种可能性，用例图却能提纲挈领地让人了解系统概况。它为“系统做什么”提供了简化了的图形表示，因此被誉为“搭建系统的蓝图”。

### 2. 事件风暴定义

在使用 DDD 的标准对系统建模前，一堆人要先了解 DDD 的操作手段，这样才能让产品、研发、测试、运营等了解业务的伙伴，都能在同一个语言下完成系统建模。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/roadmap-ddd-stc-08.png" width="950px">
</div>

- 蓝色 - 决策命令，是用户发起的行为动作，如；开始签到、开始抽奖、查看额度等。
- 黄色 - 领域事件，过去时态描述。如；签到完成、抽奖完成、奖品发放完成。它所阐述的都是这个领域要完成的终态。
- 粉色 - 外部系统，如你的系统需要调用外部的接口完成流程。
- 红色 - 业务流程，用于串联决策命令到领域事件，所实现的业务流程。一些简单的场景则直接有决策命令到领域事件就可以了。
- 绿色 - 只读模型，做一些读取数据的动作，没有写库的操作。
- 棕色 - 领域对象，每个决策命令的发起，都是含有一个对应的领域对象。

**👩🏻‍🏫敲黑板** 综上，左下角的示意图。就是一个用户，通过一个策略命令，使用领域对象，通过业务流程，完成2个领域事件，调用1次外部接口个过程。我们在整个 DDD 建模过程中，就是在寻找这些节点。

### 3. 寻找领域事件

接下来，大量的时间，都是在挖掘领域事件。这个过程就是一堆人头脑风暴的过程，避免错失流程节点。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/roadmap-ddd-stc-09.png" width="950px">
</div>

- 根据产品 PRD 文档，一起开会梳理有哪些领域事件。其实大多数领域事件一个人都可以想到，只是有些部分小的场景和将来可能产生的事件不一定覆盖全。所以要通过产品、测试、以及团队的架构师，一起讨论。
- 像是整个大营销的抽奖会包括如图所列举的事件。在列举这个阶段，你用在乎格式。也可以是每个人准备好黄色便签纸，想到一个就贴到黑板上一个，只是穷举完成。—— 实际做DDD中，也是这样用便签纸贴黑板，所以用不同的颜色做区分。

### 4. 识别领域角色和对象

在确定了领域事件以后，接下来要做的就是通过决策命令串联领域事件，并填充上所需要的领域对象。这块的操作，新手可以分开处理，如先给领域事件添加决策命令、执行用户和领域对象，最后在串联流程。就像 `事件风暴定义` 中的示意一样。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/roadmap-ddd-stc-10.png" width="950px">
</div>

- 首先，通过用户的行为动作，也就是决策命令，串联到对应的领域事件上。并对复杂的流程提供出红色的业务流程。
- 之后，为决策命令添加领域对象，每一个领域在整个流程中都起到了至关重要的作用。

### 5. 划分领域边界

有了识别出来的领域角色的流程，就可以非常容易的划分出领域边界了。先在事件风暴图上圈出领域边界，之后在单独提供领域划分。

#### 5.1 圈出领域

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/roadmap-ddd-stc-11.png" width="950px">
</div>

#### 5.2 领域边界

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/roadmap-ddd-stc-12.png" width="500px">
</div>

- 到这步咱们就可以获得整个项目中 DDD 的领域边界划分了。之后再往下就是具体的每个领域对象的详细设计和流程设计。

### 6. 研发详细设计

#### 6.1 实体对象

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/roadmap-ddd-stc-13.png" width="550px">
</div>

- 你需要对每一个领域对象进行字段的详细设计。并划分出它们的上下文关系。一般在公司中，这部分设计完成，其他人也能对照你的设计进行代码开发。

#### 6.2 流程设计

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/roadmap-ddd-stc-14.png" width="900px">
</div>

- 流程设计，就是更详细的设计了。每一步要调用到哪个系统，哪个接口，要执行什么动作就全部都有了。

## 五、工程实现

DDD 的战略设计做完，划分出领域边界以后。接下来就是要执行战术和战役了。也就是在工程中做编码实现。但一定要懂得设计原则和设计模式，否则写不出好的代码的。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/roadmap-ddd-stc-15.png" width="500px">
</div>

- 工程实现，就是在确定的框架结构中编码。可以是洋葱架构、整洁架构、菱形架构等等。这部分内容的可以通过实战项目来锻炼，获得编码技巧。

## 六、实战项目

注意📢，加入小傅哥的【星球：码农会锁】即可获得大营销项目学习，此外还包括：OpenAI 应用、API网关、Lottery抽奖、IM通信、SpringBoot Starter 组件开发、IDEA Plugin 插件开发等，并还有开源项目学习。

提示🔓，加入星球即可解锁全部项目，后续的新项目也可以学习。这些内容非常适合提高编程思维和编码能力。与小demo项目不同，这些内容的积累都是面试中的利器！

> [🧧加入](https://t.zsxq.com/14gswKIeX) 这样成体系的全量项目学习，放在一些平台售卖，至少都要上千块。但小傅哥的星球，只需要100多，就可以获得大厂架构师对你手把手教学！[https://gaga.plus](https://gaga.plus) - 项目演示地址。
