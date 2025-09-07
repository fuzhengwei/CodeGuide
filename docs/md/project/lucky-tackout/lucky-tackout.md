---
title: 吉祥外卖系统 PLUS 版
lock: no
---

# 吉祥外卖系统 PLUS 版

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>项目：[https://t.zsxq.com/g57ja](https://t.zsxq.com/g57ja)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

**是的，我干了一套吉祥外卖系统 PLUS 版本！**

临近秋招，小傅哥又开始为非常多的小伙伴提供简历评审服务。在这个过程中，发现不少伙伴的简历都有一款**外卖系统**，但在项目描述上，大家把简历写的更像是八股文，缺少了很多核心流程描述。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/lucky-tackout/lucky-tackout-introduction-01.png" width="850px"/>
</div>

这样写简历，其实就等同于没做项目，投递出去肯定是非常吃亏的。即使靠着学历进入到面试阶段，在后续的2、3轮面试中，也非常有风险被其他同届伙伴刷下去。而好的项目，除了有这样的`热点场景`，也要有非常细腻的核心流程。让面试官一看简历，就有非常大的兴趣对你进行提问。

不过，都这个时候了，也马上要进入面试了。对于一些非常忙的伙伴，来不及换项目时候。所以，小傅哥这段时间，赶出了一套”**吉祥外卖**“，提供相关的；设计图稿、系统设计、课程资料、章节课程、简历模板、工程代码（可运行展示）等，方便小伙伴结合已经有的外卖项目基础，扩展学习刷课程即可。

接下来，小傅哥就介绍下这套项目，方便小伙伴们了解下，运行效果是什么样，都包括哪些东西。

> 💐 文末提供了课程的全套源码和资料，以及还有额外的17个实战项目，全程视频手把手做，增强小伙伴们的综合能力。

## 一、运行效果

该项目是一个基于真实业务场景的**快餐外卖生态系统** ，以互联网ToC场景的核心业务流程为背景，采用分布式架构设计 + 领域驱动建模，完成项目场景功能实现。项目涵盖用户端、商家端、配送端、运营端四大业务场景，构建完整的外卖生态闭环。

### 1. 用户端

#### 1.1 登录进首页

<div align="center">
	<img src="https://bugstack.cn/images/article/project/lucky-tackout/lucky-tackout-introduction-02.png" width="650px"/>
</div>

#### 1.2 下单到结算

<div align="center">
	<img src="https://bugstack.cn/images/article/project/lucky-tackout/lucky-tackout-introduction-03.png" width="650px"/>
</div>

#### 1.3 支付和我的

<div align="center">
	<img src="https://bugstack.cn/images/article/project/lucky-tackout/lucky-tackout-introduction-04.png" width="650px"/>
</div>

### 2. 配送端

<div align="center">
	<img src="https://bugstack.cn/images/article/project/lucky-tackout/lucky-tackout-introduction-11.png" width="325px"/>
</div>

### 3. 商家端

<div align="center">
	<img src="https://bugstack.cn/images/article/project/lucky-tackout/lucky-tackout-introduction-12.png" width="850px"/>
</div>

### 4. 运营端

<div align="center">
	<img src="https://bugstack.cn/images/article/project/lucky-tackout/lucky-tackout-introduction-13.png" width="850px"/>
</div>

以上UI，用户端完整对接了后端接口。配送端、商家端、运营端，后端接口已提供，后续可对接。

综上，是一个完整的从`登录`，`选品`、`下单`、`结算`、`支付`的全流程，非常全面的展示出系统功能。所有的操作都有后台的接口调用和数据库表输入的录入，在这个过程你可以完整的理解到关于外卖系统的全核心流程。那么有这样一套东西，你就可以在学习后，完善自己的简历了。

**程序员**，还是要看到可演示运行的东西 + 项目的代码，才好编写自己的简历。

## 二、项目信息

接下来，小傅哥带着大家在看看关于这套项目所能给大家提供的工程代码和资料。

### 1. 工程结构

<div align="center">
	<img src="https://bugstack.cn/images/article/project/lucky-tackout/lucky-tackout-introduction-05.png" width="650px"/>
</div>

- 工程：[https://gitcode.net/KnowledgePlanet/lucky-tackout](https://gitcode.net/KnowledgePlanet/lucky-tackout) - `文末加入小傅哥社群，即可获得全部代码，以及其他17个实战项目`
- 说明：工程提供了前后端代码，设计资料，文档资料等。主要适合于速刷系统，补充简历的伙伴使用。如果需要手把手教程类的项目，可以做小傅哥社群里其他17个实战项目。
- 另外：本套项目主要为了方便伙伴快速刷来对应面试的，小傅哥也是快速出的，暂时没有相关的视频类资料。后续会录制一些视频讲解。

### 2. 功能领域

<div align="center">
	<img src="https://bugstack.cn/images/article/project/lucky-tackout/lucky-tackout-introduction-06.png" width="850px"/>
</div>

- 如图，功能领域涵盖；用户端、商家端、配送端、平台运营，之后这些模块使用核心服务层提供的能力。
- 注意，关于页面，小傅哥提供了用户端的UI，后续在补充其他端的页面UI。

### 3. 领域模型

<div align="center">
	<img src="https://bugstack.cn/images/article/project/lucky-tackout/lucky-tackout-introduction-07.png" width="850px"/>
</div>

- 基于我们所需要的功能场景，进行了领域建模。分为；用户领域、订单领域、商家领域、配送领域、支付领域、产品领域。
- 这些内容可以体现到简历上，也就有非常细腻的流程可以讲出来了。

### 4. 库表设计

<div align="center">
	<img src="https://bugstack.cn/images/article/project/lucky-tackout/lucky-tackout-introduction-08.png" width="850px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/article/project/lucky-tackout/lucky-tackout-introduction-09.png" width="850px"/>
</div>

- 本套项目设计了非常全面的库表，你可以理解到整体外卖的全部核心体系。

## 三、启动说明

### 1. 环境说明

- JDK 1.8
- Maven 3.8.x
- MySql 8.x
- SpringBoot 2.7.12

### 2. 导入库表

在工程路径 `docs/dev-ops/mysql/sql/fast_food_delivery.sql` 提供了库表脚本，要导入到本地数据库中。

### 3. 配置链接

```java
spring:
  datasource:
    username: root
    password: 12345678
    url: jdbc:mysql://127.0.0.1:3306/fast_food_delivery?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC&useSSL=true
    driver-class-name: com.mysql.cj.jdbc.Driver
```

- 在工程的 yml 文件，配置你的数据库表信息。

### 4. 启动项目

<div align="center">
	<img src="https://bugstack.cn/images/article/project/lucky-tackout/lucky-tackout-introduction-10.png" width="850px"/>
</div>

- 配置好相关信息后，启动项目，之后进入到 `nginx/html/h5-food-delivery` 打开 html 页面即可。

