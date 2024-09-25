---
title: 设计：架构图、拓扑图、用例图、流程图、建模图
lock: no
---

# 《大营销平台系统》系统设计图：架构图、拓扑图、用例图、流程图、建模图

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>课程：[https://t.zsxq.com/17gswKIeX](https://t.zsxq.com/17gswKIeX)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?isOutside=true&aid=113189501017893&bvid=BV1Nes9e2E5K&cid=25977226974&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

大家好，我是技术UP主小傅哥。

😂 现在的面试是不是为难小朋友，怎么刚一毕业的面试，就让现场手画简历项目的系统架构图。面试官说，你不是计算机专业毕业🎓的吗，这不是你的基础吗？你作为一个合格的毕业生，不就应该学习到这些吗？

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-v7-01.gif" width="200px">
</div>

**为什么毕业生，画不出来这些图？**

其实不只毕业生，很多工作1-3年的也画不出这些系统的架构图。主要是因为，在校的时候没有学过，市面的一些简单学习项目也都是 CRUD 的 demo 级别项目，工作了以后如果又是一些小厂，大部分就是完成功能需求而已。所以很多人压根就没有画过这些图。

但作为程序员👨🏻‍💻这个行业的伙伴，如果还想多职业生涯走的更长，也多赚点米，那么就需要好好的在一个大型项目中锻炼这些画图的技能。因为一图胜前言，有时候图好，前途就好！

那一个系统都会做出哪些个设计图呢？这里会包括；`系统架构图`、`业务拓扑图`、`用户用例图`、`业务流程图`、`系统建模图`，之后就是细分实现功能的 UML 流程图。这里小傅哥会以此的给大家做绘制和讲解，这趟车🚗你可以学习到很多！

>文末提供了本次分享系统设计图对应的系统工程全套代码、文档、视频和面试官以及简历资料。

## 一、系统的场景

这是互联网公司C端场景，峰值流量最高的一套**营销类业务系统**，业务涵盖；`抽奖`、`活动`、`积分`、`返利`、`兑换`，这些核心的业务流程。像是在`抖音商城`、`京东金融`、`滴滴出行`、`拼多多`，都有这样业务场景。如图；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-system-design-diagram-01.png" width="750px">
</div>

整个系统是一个包括 `前后端 + Dev-Ops` 的全栈式综合编程实战项目，基于 React + SpringBoot + 分布式技术栈 + Nginx + Grafana + Docker 云服务，开发、部署、上线、监控，全体系技术运用构建。

> 接下来，小傅哥就给大家分别的展示出各项设计图，包括；`系统架构图`、`业务拓扑图`、`用户用例图`、`业务流程图`、`系统建模图`。

## 二、系统架构图

**软件架构**是有关软件整体结构与组件的抽象描述，用于指导大型软件系统各个方面的设计。软件架构会包括软件组件、组件之间的关系，组件特性以及组件间关系的特性。软件架构可以和建筑物的架构相比拟。软件架构是构建计算机软件，开发系统以及计划进行的基础，可以列出开发团队需要完成的任务。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-system-design-diagram-02.png" width="950px">
</div>

- 这是一套大营销系统的分布式架构设计图，从前端到负载，从服务治理到后端分布式技术栈体现，从应用到部署和监控的全体系展示。在这样一套系统架构中，你可以清楚的知道从前到后的流程、各项分布式技术栈的用途、整个系统的脉络关系。所以这样的一个图可以清晰的指导我们做系统的搭建。
- `佛瑞德·布鲁克斯`在写作《人月神话》一书时提及：软件系统的架构是有关软件系统该作什么以及不该作什么的实体观点。这些观点应和软件的实现分开。架构师的角色是“观点的看守者”，确认系统中增加的部分是符合此架构，因此可以保有概念完整性
- 另外程序员`马尔文·康威`在1967年论文发表了康威定律，其中提到一个组织开发的软件，其架构会反映其组织架构。佛瑞德·布鲁克斯在写作《人月神话》一书时，就在书上时提到此例子，命名为“康威定律”。

## 三、用户用例图

用例图（英语：use case diagram）是用户与系统交互的最简表示形式，展现了用户和与他相关的用例之间的关系。通过用例图，人们可以获知系统不同种类的用户和用例。用例图也经常和其他图表配合使用。

- 用例图，也可以等同于是用户故事（英语：User story）（软件开发和项目管理中的常用术语），主旨是以日常语言或商务用语撰写句子，是一段简单的功能表述。以客户或使用者的观点撰写下有价值的功能、引导、框架来与使用者进行互动，进而推动工作进程。可以被认为是一种规格文件，但更精确而言，它代表客户的需求与方向。以该用户故事来反应对象在组织内的其工作职责、范围、需要进行的任务等。用户故事在敏捷开发方法中用来定义系统需要提供的功能和实现需求管理。
- 尽管用例本身会涉及大量细节和各种可能性，用例图却能提纲挈领地让人了解系统概况。它为“系统做什么”提供了简化了的图形表示，因此被誉为“搭建系统的蓝图”。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-system-design-diagram-06.png" width="650px">
</div>
- 用例图是研发进入设计阶段第一个图，通过用例图了解整个系统中发生的流程行为。这个图虽然很粗，但非常适合做后续的设计指导。


## 四、业务拓扑图

系统业务拓扑图通常是指系统中各个组件和业务流程的可视化表示。这类图示有助于理解和分析系统如何运作、各组件之间的关系、数据流动的路径以及可能的瓶颈或失败节点。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-system-design-diagram-03.png" width="950px">
</div>

- 业务拓扑图，展示出了业务实现中的关系模型，包括整个系统会从哪里展示，包括了哪些核心业务功能和执行的链路。以及数据的走向。
- 有了系统业务拓扑图，开发者、系统架构师和运维人员可以更直观地理解系统的结构和业务逻辑，从而更有效地进行设计、部署、监控和故障排除。

## 五、四色建模图

如果系统是 DDD 开发的，还需要做四色建模。在使用 DDD 的标准对系统建模前，一堆人要先了解 DDD 的操作手段，这样才能让产品、研发、测试、运营等了解业务的伙伴，都能在同一个语言下完成系统建模。

- 蓝色 - 决策命令，是用户发起的行为动作，如；开始签到、开始抽奖、查看额度等。
- 黄色 - 领域事件，过去时态描述。如；签到完成、抽奖完成、奖品发放完成。它所阐述的都是这个领域要完成的终态。
- 粉色 - 外部系统，如你的系统需要调用外部的接口完成流程。
- 红色 - 业务流程，用于串联决策命令到领域事件，所实现的业务流程。一些简单的场景则直接有决策命令到领域事件就可以了。
- 绿色 - 只读模型，做一些读取数据的动作，没有写库的操作。
- 棕色 - 领域对象，每个决策命令的发起，都是含有一个对应的领域对象。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-system-design-diagram-07.png" width="950px">
</div>

- 系统建模后可以细分出系统开发中要实现的领域，包括；返利、活动、策略、奖品，兑换可以是单独的领域也可以合并到返利实现。
- 具体的建模过程可以阅读 [《架构：DDD 领域驱动设计，战略、战术、战役，落地指引规范》](https://bugstack.cn/md/project/big-market/ddd.html)

## 六、业务流程图

系统业务功能流程图（Business Functional Flow Diagram，BFD）是一种用于描述和展示系统内业务功能及其之间关系的图形化工具。它通常用于系统分析和设计阶段，以帮助理解和沟通业务流程、功能要求以及系统的各个组件如何协同工作。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-system-design-diagram-04.png" width="950px">
</div>

- 在该流程中，详细的展示出了系统的业务流转关系，从1~6的用户行为，全面的展示出了系统中的流程动作。怎么抽奖、怎么返利、怎么兑换积分。
- 画这个图到时候，首先，需要识别系统中的主要业务功能。这些业务功能通常可以通过业务需求文档、需求分析会谈以及与业务用户的交流中得出。之后，识别各业务功能之间的关系和依赖性。这包括理解哪些业务功能依赖于哪些其他功能，以及它们如何相互作用。最后，绘制流程图，以图形方式展示业务功能和它们之间的关系。

## 七、工程分层图

工程的分层有 MVC 三层架构、DDD 四层架构，其实 DDD 不能只是说它是架构，因为用于承载 DDD 的架构是；整洁架构、洋葱架构、六边形架构、菱形架构这些。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-system-design-diagram-08.png" width="750px">
</div>
- 六边形架构，会把本身提供到外部的放到trigger，让接口调用、消息监听、任务调度，都可以统一一个入口处理。而对于需要调用外部同类的能力统一放到 infrastructure 基础设施层，包括；数据库、缓存、配置、调用其他方的接口。
- querys 模块是为了提供查询设计的模块，这样一些基本简单的查询就不需要再走到 domain 领域层了。
- 更多关于 DDD 的工程模型，可以在 [bugstack.cn](https://bugstack.cn) 进入《路书》中阅读9篇系统架构部分。

## 八、扩展类知识

这是小傅哥为大家提供的一套综合锻炼编程能力的实战项目大课。全程视频手把手，一个个章节、一步步流程的带着大家从0到1，需求分析、工程设计和代码实现。

课程完成后，有可参考简历编写模板、简历面试问题汇总、项目延展性思考和分析，以及项目中的架构、设计模式在场景方案中的运用。

- 课程目录：[https://bugstack.cn/md/project/big-market/big-market.html](https://bugstack.cn/md/project/big-market/big-market.html)
- 系统建模：[https://bugstack.cn/md/project/big-market/ddd.html](https://bugstack.cn/md/project/big-market/ddd.html)
- 简历参考：[https://bugstack.cn/md/project/big-market/notes.html](https://bugstack.cn/md/project/big-market/notes.html)
- 面试问题：[https://bugstack.cn/md/project/big-market/notes.html](https://bugstack.cn/md/project/big-market/notes.html)
