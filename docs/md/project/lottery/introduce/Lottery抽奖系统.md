---
title: Lottery 抽奖系统 - 基于领域驱动设计的四层架构实践
---

# Lottery 抽奖系统 - 基于领域驱动设计的四层架构实践

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=210028247&bvid=BV1Ta411677m&cid=468283408&page=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

**加入项目**：[https://t.zsxq.com/jAi2nUf](https://t.zsxq.com/jAi2nUf) - 加入后在星球置顶消息可以申请加入项目组，`公众号：bugstack虫洞栈 回复：星球 可以获得加入优惠券`

写CRUD、堆API、改屎山⛰，熬多少个996也只是成为重复的螺丝钉。如果你希望捅破现有工作的瓶颈，拉高一下对技术的认知，那么就非常需要一个大项目来贯穿常用技术栈的知识体系。*碎片化断层的背八股文，是解决不了这个事情*

那怎么办？当然是要肝项目了，肝一个`有互联网技术体系`、`有分布式架构运用`、`有DDD思想和设计模式实践`的真实场景项目，才能让一个尚未接触此类项目或是长期陷入CURD的熟练工，打开视野，快速成长起来。

**整整半年**，小傅哥都在做这件事情，直到今天才完成整个系统的第一期设计实现和落地。在这个[《分布式抽奖系统》](#)项目中，我会带着大家以DDD架构和设计模式落地实战的方式，进行`代码开发`、`视频介绍`、`文档说明`的方式讲解和实现分布式抽奖系统，那么这里会涉及到很多DDD的设计思路和设计模式应用，以及互联网大厂开发中所应用到的技术，包括：SpringBoot、Mybatis、Dubbo、MQ、Redis、Mysql、ELK、分库分表、Otter 等。

在加入项目之前，你可以仔细阅读如下的介绍信息，方便你能更加快速的进入学习。*一点点投资，为你3月-4月，拿一个更高更靠谱的Offer*

## 一、咋，撸个项目？

**在这之前一直有粉丝伙伴问傅哥**，有没有能`上手练习技术的项目`，现在学了这么多技术知识、看了这么多设计模式、搜了这么多架构设计，但这些内容都是怎么结合在一起使用的呢？互联网中的项目架构设计是什么样的呢？我该怎么开始学到什么样才能进大厂呢？

|  项目学习意见（收集结果）    |
| ---- |
|   ![图 1-1](/images/article/project/lottery/Part-2/1-01.png)   |

- [项目学习意见（收集结果）.xlsx](https://gitcode.net/KnowledgePlanet/Lottery/-/blob/master/doc/assets/excel/%E9%A1%B9%E7%9B%AE%E5%AD%A6%E4%B9%A0%E6%84%8F%E8%A7%81%EF%BC%88%E6%94%B6%E9%9B%86%E7%BB%93%E6%9E%9C%EF%BC%89.xlsx)

`咋neng呢，撸个项目吧！` 在撸项目开始之前，做了一次项目学习意见调研，问了问大家：“想做个什么项目，如；积分商城、抽奖系统、活动系统、监控系统、技术组件，并且这些项目中用到了哪些技术栈。” 最后在大家的意见反馈中，先以开发互联网中C端类项目 **抽奖系统** 开始，这样一个项目可以让大家在系统的`架构搭建`、`功能配置`、`服务开发`中学习到关于一些关于解决`高并发`、`高性能`、`高可用`场景时的技术实践运用。*放心，其他类的互联网项目，我们也会陆续的折腾起来！*

So！基于DDD领域驱动设计的四层架构**抽奖系统**，开始啦！有座，这趟车的你跟上！

## 二、呀，能学东西！

![图 1-2](/images/article/project/lottery/Part-2/1-02.png)

一个以真实场景`实践技术栈整合`开发实际需求的项目，势必会因为要完成需求而引入各项技术栈的使用，也会由于要解决互联网中C端场景中的三高问题，而使用相应的技术实现不同类别解决与方案，我们也可以把此类解决方案理解为DDD中的业务领域模型开发。在这个设计和开发的过程中会涉及到`架构设计`、`技术应用`、`场景实现`，每一块内容都会有非常多的实践知识，可以让读者学到东西。

### 1. 涉及技术

在此项目中你会学习到互联网公司关于C端项目开发时候用到的一些，技术、架构、规范等内容。由于项目为实战类编程项目，在学习的过程中需要上手操作，小傅哥会把系统的搭建拉不同的分支列为每一个章节进行设计和实现并记录到开发日记中，读者在学习的过程中可以结合这部分内容边看文章边写代码实践。

- 技术：JDK 1.8、SpringBoot、Mybatis、Dubbo、MQ、Redis、Mysql、ELK、分库分表、Otter、vue、微信公众号、Docker
- 架构：DDD 领域驱动设计、充血模型、设计模式
- 规范：分支提交规范、代码编写规范

**其他所需环境如下(mysql\kafka\zk\redis\xxl-job)：**

![](/images/article/project/lottery/introduce/portainer.png)

- 不只是 DDD 分布式项目开发，你还可以学习到关于 Docker 的实践使用

### 2. 系统架构

![](https://bugstack.cn/images/article/project/lottery/introduce/introduce-220101-01.png)

- 此系统架构为 DDD 领域驱动设计的四层架构实现方式，以重视代码实现落地的方式向读者介绍和展示如何开发这样的代码。
- 在 Domain 领域层逐步通过拆解系统流程设计，按照职责边界的领域模块进行设计和开发，最终在应用层进行逻辑功能编排。
- 这个系统中会体现出很多的设计模式思想和最终的实现，只有把 DDD 和设计模式结合起来，才能开发出更加易于扩展和维护的代码结构。

### 3. 分布式设计

![](https://bugstack.cn/images/article/project/lottery/introduce/introduce-220101-02.png)

整体系统架构设计包含了6个工程：

1. Lottery：分布式部署的抽奖服务系统，提供抽奖业务领域功能，以分布式部署的方式提供 RPC 服务。
2. Lottery-API：网关API服务，提供；H5 页面抽奖、公众号开发回复消息抽奖。
3. Lottery-Front：C端用户系统，vue H5 lucky-canvas 大转盘抽奖界面，讲解 vue 工程创建、引入模块、开发接口、跨域访问和功能实现
4. Lottery-ERP：B端运营系统，满足运营人员对于活动的查询、配置、修改、审核等操作。
5. DB-Router：分库分表路由组件，开发一个基于 HashMap 核心设计原理，使用哈希散列+扰动函数的方式，把数据散列到多个库表中的组件，并验证使用。
6. Lottery-Test：测试验证系统，用于测试验证RPC服务、系统功能调用的测试系统。

📢 **注意**：db-router-spring-boot-starter 为自研数据库路由组件，你需要下载代码到本地，自行构建到本地仓库进行使用。*章节中也会介绍这个路由组件的设计和开发*

### 4. 凝练流程领域

![](https://bugstack.cn/images/article/project/lottery/introduce/introduce-220101-03.png)

- 拆解功能流程，提炼领域服务，一步步教会你把一个业务功能流程如何拆解为各个职责边界下的领域模块，在通过把开发好的领域服务在应用层进行串联，提供整个服务链路。
- 通过这样的设计和落地思想，以及在把流程化的功能按照面向对象的思路使用设计模式进行设计，让每一步代码都变得清晰易懂，这样实现出来的代码也就更加易于维护和扩展了。
- 所以，你在这个过程中学会的不只是代码开发，还有更多的落地思想实践在这里面体现出来。也能为你以后开发这样的一个项目或者在面试过程中，一些实际复杂场景问题的设计思路，打下不错的基础。

## 三、呐，怎么开始？   

### 快速加入

为了能让读者伙伴快速🔜进入项目学习，可以按照下面的步骤开始：

1. 加入知识星球：[`码农会锁`](https://t.zsxq.com/jAi2nUf) 通过在公众号：`bugstack虫洞栈` 回复：`星球` 获取优惠券加入，也可以点击链接加入：[https://t.zsxq.com/jAi2nUf](https://t.zsxq.com/jAi2nUf)
2. 【入口】[Lottery](https://gitcode.net/KnowledgePlanet/Lottery) 项目主入口中有一个 README.md 有关于项目的学习说明、开发规范、章节目录和问题交流提交issue说明以及群内交流，在学习的过程中可以参考使用。
3. 【文章】每一个章节内容中都会包括；需求、实现、验证、细节，四块内容的介绍，以及当前章节中对应的代码分支可以切换学习。
4. 【代码】在代码学习的过程中可以克隆工程进行开发练习，也可以给主工程小傅哥工程代码提交PR、ISSUE，我会去审核和合并以及不断的完善代码。

### 📝 学习说明(小册、源码、视频)

课程包括：`小册`、`视频`、`代码`、`作业`，四方面结合的方式进行学习，所以也能让即使缺少编码经验的在校学生、应届生或者是CRUD熟练工，都能快速加入项目进行学习。

![](https://bugstack.cn/images/article/project/lottery/introduce/introduce-220101-04.png)

- 课程分为：`大厂规范`、`领域开发`、`运营后台`、`应用场景`、`系统运维`，共5章34节来讲解DDD分布式系统的架构设计和实践落地。
- 只要你能认真跟着敲下来，22年的 Offer 不会便宜！*真的是研发能力有差异吗，其实不是，你差的只是一个有人带着你肝的大型系统而已！*

**Lottery 抽奖系统** - 基于领域驱动设计的四层架构实践 `只有加入知识星球：码农会锁 申请加入项目组以下链接才能访问`

1. 代码：[https://gitcode.net/KnowledgePlanet/Lottery](https://gitcode.net/KnowledgePlanet/Lottery)
2. 小册：[https://gitcode.net/KnowledgePlanet/Lottery/-/wikis/home](https://gitcode.net/KnowledgePlanet/Lottery/-/wikis/home)
3. 提问：[https://gitcode.net/KnowledgePlanet/Lottery/-/issues](https://gitcode.net/KnowledgePlanet/Lottery/-/issues)
4. 介绍：[https://articles.zsxq.com/id_tz44w3oqjftv.html](https://articles.zsxq.com/id_tz44w3oqjftv.html)

注意：在项目学习的过程中，如果遇到问题可以先查看 issue，一般大家的共性问题都会在这里体现。如果你没有搜到与你相同的问题，也可以提一个新的 issue，可以包括：学习疑惑、Bug提醒、优化建议、技术分享等。

### 🎨 环境配置

- **技术栈项**：JDK1.8、Maven3.6.3、Mysql5.7(可升级配置)，SpringBoot、Mybatis、Dubbo 随POM版本
- **建表语句**：[doc/asserts/sql](https://gitcode.net/KnowledgePlanet/Lottery/-/blob/master/doc/assets/sql/lottery.sql) - `建议随非分支内sql版本走，因为需求不断迭代升级优化，直接使用最新的会遇到在各个分支下的代码运行问题`
- **代码仓库**：`2种使用方式`
   - 密码方式：登录的用户ID为 CSDN 个人中心的用户ID，[https://i.csdn.net/#/user-center/profile](https://i.csdn.net/#/user-center/profile) 密码为 CSDN 登录密码。如果没有密码或者忘记，可以在 CSDN 登录页找回密码。
   - SSH 秘钥免登录方式，设置：[https://gitcode.net/-/profile/keys](https://gitcode.net/-/profile/keys) 文档：[生成 SSH 密钥](https://gitcode.net/codechina/help-docs/-/wikis/docs/ssh#%E7%94%9F%E6%88%90-ssh-%E5%AF%86%E9%92%A5)
- **学习使用**：下载代码库后，切换本地分支到wiki中章节对应的分支，这样代码与章节内容是对应的，否则你在master看到的是全量代码。   
- **下载依赖**：[db-router-spring-boot-starter](https://gitcode.net/KnowledgePlanet/db-router-spring-boot-starter) 本项目依赖自研分库分表组件，需要下载后构建

### 📐 开发规范

**分支命名**：日期_姓名首字母缩写_功能单词，如：`210804_xfg_buildFramework`

**提交规范**：`作者，type: desc` 如：`小傅哥，fix：修复查询用户信息逻辑问题` *参考Commit message 规范*

```java
# 主要type
feat:     增加新功能
fix:      修复bug

# 特殊type
docs:     只改动了文档相关的内容
style:    不影响代码含义的改动，例如去掉空格、改变缩进、增删分号
build:    构造工具的或者外部依赖的改动，例如webpack，npm
refactor: 代码重构时使用
revert:   执行git revert打印的message

# 暂不使用type
test:     添加测试或者修改现有测试
perf:     提高性能的改动
ci:       与CI（持续集成服务）有关的改动
chore:    不修改src或者test的其余修改，例如构建过程或辅助工具的变动
```

### 🐾 学习作业

- 你可以在星球APP中提交`作业题目` | 网页提交：[https://t.zsxq.com/MvFYJe2](https://t.zsxq.com/MvFYJe2)

1. 今天的你学到了哪个章节？
2. 遇到什么问题？
3. 怎么解决的？
4. 掌握到了什么知识？

基于大家的学习反馈，小傅哥会在后续的直播中统一解决相关学习问题。加油，这趟车人人有收获！

注意️：按照作业提交频次和质量，小傅哥会组织一波奖品。**按照作业提交数量、质量、点赞、留言，综合评分，送技术图书等奖品。**

<img src="https://bugstack.cn/images/article/project/lottery/introduce/job.jpg" width="414" height="891"/>

- 也可以提交 issue：[https://gitcode.net/KnowledgePlanet/Lottery/-/issues](https://gitcode.net/KnowledgePlanet/Lottery/-/issues)

**课代表作业**

`讲真，我遇到了一个懂我的人！`

在星球实战项目学习中，有一个女课代表，每一次交作业提交都可以用精致来形容，她的学习速度超级快，也可以非常清晰的分析出每一块的流程并配上流程图，如下：

![](https://bugstack.cn/images/article/project/lottery/introduce/introduce-220101-05.png)

- 我也希望有更多的优秀的你，与我一起学习、成长、赚钱，22年，我们加油！

## 四、来，上我的车！

### 1. 你会学到什么

- 分布式系统架构如何开发(`RPC`、`MQ`、`分布式任务`、`分库分表`)
- DDD 四层架构设计与设计模式的结合落地
- 互联网大厂的代码开发规范、需求评审、运维监控
- Docker 运维实践，环境、部署、配置、监控、日志等

### 2. 适宜人群

- 具备 Java 编程基础的研发人员，想提升自己的技术能力
- 希望提升编码思维，剔除到代码中的坏味道
- 有意愿成为架构师，但还处在一定瓶颈期
- 想加入大厂做码农，但总感觉找不到门路

---

🚌 `来吧，上车，还有座！`

动手、动手、动手，一个实践类型的项目最需要的就是你动起手来，只有这样你才能发现各种问题细节的处理。更何况哪怕在别人电脑💻上运行的再顺畅的代码，在你那也可能*拉跨*，不过没关系因为所有拉跨的过程都将是你抓住学习的点！

我一直坚持很多事情要慢下来，希望你也不要过于的着急快，火急火燎不是学习的长久过程，而迟迟以恒才能让你的收获更加丰满。趁着时间还多正当年恰，坚持做好自己想做的事情吧！