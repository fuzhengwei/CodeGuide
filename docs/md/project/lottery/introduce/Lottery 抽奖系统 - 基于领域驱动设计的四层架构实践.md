---
title: Lottery 抽奖系统 - 基于领域驱动设计的四层架构实践
---

# Lottery 抽奖系统 - 基于领域驱动设计的四层架构实践

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、咋，撸个项目？

**总有粉丝伙伴问傅哥**，有没有能`上手练习技术的项目`，现在学了这么多技术知识、看了这么多设计模式、搜了这么多架构设计，但这些内容都是怎么结合在一起使用的呢？互联网中的项目架构设计是什么样的呢？我该怎么开始学到什么样才能进大厂呢？

|  项目学习意见（收集结果）    |
| ---- |
|   ![图 1-1](https://gitcode.net/KnowledgePlanet/Lottery/-/raw/master/doc/assets/img/Part-2/1-01.png)   |

- [项目学习意见（收集结果）.xlsx](https://gitcode.net/KnowledgePlanet/Lottery/-/blob/master/doc/assets/excel/%E9%A1%B9%E7%9B%AE%E5%AD%A6%E4%B9%A0%E6%84%8F%E8%A7%81%EF%BC%88%E6%94%B6%E9%9B%86%E7%BB%93%E6%9E%9C%EF%BC%89.xlsx)

`咋neng呢，撸个项目吧！` 在撸项目开始之前，做了一次项目学习意见调研，问了问大家：“想做个什么项目，如；积分商城、抽奖系统、活动系统、监控系统、技术组件，并且这些项目中用到了哪些技术栈。” 最后在大家的意见反馈中，先以开发互联网中C端类项目 **抽奖系统** 开始，这样一个项目可以让大家在系统的`架构搭建`、`功能配置`、`服务开发`中学习到关于一些关于解决`高并发`、`高性能`、`高可用`场景时的技术实践运用。*放心，其他类的互联网项目，我们也会陆续的折腾起来！*

So！基于DDD领域驱动设计的四层架构**抽奖系统**，开始啦！有座，这趟车的你跟上！

## 二、呀，能学东西！

![图 1-2](https://gitcode.net/KnowledgePlanet/Lottery/-/raw/master/doc/assets/img/Part-2/1-02.png)

一个以真实场景`实践技术栈整合`开发实际需求的项目，势必会因为要完成需求而引入各项技术栈的使用，也会由于要解决互联网中C端场景中的三高问题，而使用相应的技术实现不同类别解决与方案，我们也可以把此类解决方案理解为DDD中的业务领域模型开发。在这个设计和开发的过程中会涉及到`架构设计`、`技术应用`、`场景实现`，每一块内容都会有非常多的实践知识，可以让读者学到东西。

### 技术栈

- JDK 1.8
- SpringBoot 2.6.0
- Dubbo 2.7.10
- DB-ROUTER `自研分库分表路由组件，带着你一起写个SpringBoot Starter`
- vue `开发H5大转盘抽奖`
- 微信公众号 `对接提供API，回复抽奖`
- Docker `本地和云服务都可以`

**其他所需环境如下(mysql\kafka\zk\redis\xxl-job)：**

![](/images/article/project/lottery/mkt/portainer.png)

- 不只是 DDD 分布式项目开发，你还可以学习到关于 Docker 的实践使用

### 工程信息

![](/images/article/project/lottery/mkt/system-list.png)

📢 **注意**：db-router-spring-boot-starter 为自研数据库路由组件，你需要下载代码到本地，自行构建到本地仓库进行使用。*章节中也会介绍这个路由组件的设计和开发*


## 三、呐，怎么开始？   

为了能让读者伙伴快速🔜进入项目学习，可以按照下面的步骤开始：

1. 【入口】[Lottery](https://gitcode.net/KnowledgePlanet/Lottery) 项目主入口中有一个 README.md 有关于项目的学习说明、开发规范、章节目录和问题交流提交issue说明以及群内交流，在学习的过程中可以参考使用。
2. 【文章】每一个章节内容中都会包括；需求、实现、验证、细节，四块内容的介绍，以及当前章节中对应的代码分支可以切换学习。
3. 【代码】在代码学习的过程中可以克隆工程进行开发练习，也可以给主工程小傅哥工程代码提交PR、ISSUE，我会去审核和合并以及不断的完善代码。

### 📝 学习说明

在此项目中你会学习到互联网公司关于C端项目开发时候用到的一些，技术、架构、规范等内容。由于项目为实战类编程项目，在学习的过程中需要上手操作，小傅哥会把系统的搭建拉不同的分支列为每一个章节进行设计和实现并记录到开发日记中，读者在学习的过程中可以结合这部分内容边看文章边写代码实践。

- 技术：SpringBoot、Mybatis、Dubbo、MQ、Redis、Mysql、ELK、分库分表、Otter
- 架构：DDD 领域驱动设计、充血模型、设计模式
- 规范：分支提交规范、代码编写规范

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

<img src="https://gitcode.net/KnowledgePlanet/Lottery/-/raw/master/doc/_media/job.jpg" width="414" height="891"/>

- 也可以提交 issue：[https://gitcode.net/KnowledgePlanet/Lottery/-/issues](https://gitcode.net/KnowledgePlanet/Lottery/-/issues)

## 四、来，上我的车！

🚌 `来吧，上车，还有座！`

动手、动手、动手，一个实践类型的项目最需要的就是你动起手来，只有这样你才能发现各种问题细节的处理。更何况哪怕在别人电脑💻上运行的再顺畅的代码，在你那也可能*拉跨*，不过没关系因为所有拉跨的过程都将是你抓住学习的点！

我一直坚持很多事情要慢下来，希望你也不要过于的着急快，火急火燎不是学习的长久过程，而迟迟以恒才能让你的收获更加丰满。趁着时间还多正当年恰，坚持做好自己想做的事情吧！