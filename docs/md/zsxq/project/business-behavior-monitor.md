---
title: 透视业务流程 - 监控系统
lock: no
---

# 透视业务流程 - 监控系统

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>课程：[https://t.zsxq.com/CVzpL](https://t.zsxq.com/CVzpL)

>沉淀、分享、成长，让自己和他人都能有所收获！

大家好，我是技术UP主小傅哥。

可以这么说，作为一个想卷到好一些公司，拿到高薪资Offer的程序员，就没有一个只是做CRUD业务流程开发的。他们的简历上，基本都是业务项目不错，又具备开发轮子的手段。轮子的意思就是一个个独立于业务，对于同类功能凝练而成的通用技术类组件，解决同类的共性问题。因此减少业务系统重复功能建设，提高开发效率。`所以一般简历上业务 + 技术，往往是很亮眼的。`

<div align="center">
    <img src="https://bugstack.cn/images/article/project/business-behavior-monitor/business-behavior-monitor-01.png" width="150px">
</div>

**根据投票，我们先卷这业务透视监控系统**

开始的早，经历的久，在这趟职场生涯中，小傅哥的肚子里积累了非常多的实战经验的。所以小傅哥也计划着陆续的带着大家做出这些应用级别的实战项目。为了看看小伙伴们想先学哪个，小傅哥发起了投票。[投票地址](https://mp.weixin.qq.com/s/TULYFHzxQkgnXnU_Oq99hQ)

<div align="center">
    <img src="https://bugstack.cn/images/article/project/business-behavior-monitor/business-behavior-monitor-02.png" width="550px">
</div>

按照投票所展示，排序为；业务透视监控(440票)，优惠券系统(403票)、阶梯拉新(239票)、自动Mock组件(195票)、工程链路地图(132票)、礼账系统(62票)、问卷系统(62票)。

按照投票结果，小傅哥带着大家，启动第一个组件项目，《业务透视监控》教给大家如何采集`系统数据`、`链路分析`、`数据解析`、`可视化渲染等`。

>🧧 文末有优惠加入学习二维码，可以获得除了本文《业务透视监控》外，还有5个业务项目 + 5个组件项目。

## 一、能学到啥

业务项目与组件项目是两种不同的思考模式，不只是工程结构不一样，在编码逻辑实现上，组件项目也会更多的思考`兼容性`、`可靠性`、`共用性`、`扩展性`。而这些内容也是业务项目没法学习到的，所以我们需要不同的实战类内容填充自己的知识积累。

- IntelliJ IDEA 使用以及快捷键技巧。创建组件工程、代码库使用、代码提交、拉取、合并等操作
- logback 自定义日志采集组件实现，统一过滤和格式化日志数据，进行数据上报推送。
- ognl 表达式解析复杂对象数据，这样的组件在MyBatis源码中也有用到。
- 设计数据处理中心，承接自定义日志组件推送的数据。
- 业务动态链路节点可配置化设计，解析监控日志数据并做缓存计算和数据库存储。
- 节点关联关系设计和渲染，动态可视化展示业务实时监控数据信息。
- 前端gojs渲染技术，展示和修改监控链路图，以及监控日志查询。

## 二、项目介绍

本次项目会采用基于扩展 logback 日志上报数据进行 ognl 配置节点公式的方式进行采集、计算和可视化渲染。在这套项`小而美，小而精`的组件项目中，你可以学习到非常多的实战技能。

这套`透视业务流程的监控系统`，与 `Prometheus + Grafana`、`Skywalking` 有较大的差异。这两款监控都是系统健康度监控，而小傅哥带着大家做的是业务流程监控。*很多中大厂，也都有同类的业务系统*

业务流程监控，以展示用户行为维度的业务流程为核心，透视系统工程中业务的流转。1:1 还原产品 PRD 流程图为可视化动态效果，实时展示系统调用执行数据信息。`可参考下面的图`

### 1. 监控透视图 - 以星球大营销系统为例

<div align="center">
    <img src="https://bugstack.cn/images/article/project/business-behavior-monitor/business-behavior-monitor-03.png" width="850px">
</div>

### 2. 监控的日志

<div align="center">
    <img src="https://bugstack.cn/images/article/project/business-behavior-monitor/business-behavior-monitor-04.png" width="850px">
</div>

### 3. 库表的设计

<div align="center">
    <img src="https://bugstack.cn/images/article/project/business-behavior-monitor/business-behavior-monitor-05.png" width="850px">
</div>

### 4. 组件化工程

<div align="center">
    <img src="https://bugstack.cn/images/article/project/business-behavior-monitor/business-behavior-monitor-06.png" width="350px">
</div>

>有了这一套组件的学习，你可以搭配到任何一个你做的业务项目中使用。无论是在公司中，还是在面试写简历里。都能让你展示出非常不错的技术体现度。

## 三、课程大纲

**不同于网上项目，这个项目是一步步，一个个章节的带着大家从0到1的全程视频的方式，进行分析、设计和开发。是一个纯手把手教大家学习实战技术的项目！** 大家可以先看看课程的大纲，就知道可以学习到哪些东西了。项目地址：[https://t.zsxq.com/CVzpL](https://t.zsxq.com/CVzpL)

<div align="center">
    <img src="https://bugstack.cn/images/article/project/business-behavior-monitor/business-behavior-monitor-07.png" width="550px">
</div>

- 第1节：监控业务，介绍、设计、演示
- 第2节：组件工程初始化和代码提交
- 第3节：监控采集日志组件SDK设计实现
- 第4节：前端渲染gojs组件案例讲解「基于渲染可视化所需信息设计后续库表」
- 第5节：流程可视化监控，库表设计
- 第6节：监控中心工程ORM等基础配置
- 第7节：监控中心接收测试工程上报数据解析
- 第8节：监控列表接口实现和配置使用
- 第9节：监控渲染链路实现和配置使用
- 第10节：监控日志数据查询和配置使用
- 第11节：监控链路动态更新接口和配置使用
- 第12节：大营销业务系统链路配置「可以是其他任何业务系统」
- 第13节：大营销工程引入监控组件「可以是其他任何业务系统」
- 第14节：课程总结（对课程中伙伴提到的问题进行总结以及可扩展点讲解）

## 四、加入学习

**注意**📢，本项目也只是【星球：码农会锁】众多项目中的1个，其他的项目还包括：大营销、OpenAI 应用、API网关、Lottery抽奖、IM通信、SpringBoot Starter 组件开发、IDEA Plugin 插件开发、支付SDK、动态线程组件等，并还有开源项目学习。

如果大家希望通过做有价值的编程项目，提高自己的编程思维和编码能力，可以加入小傅哥的【星球：码农会锁】。加入后解锁🔓所有往期项目，还可以学习后续新开发的项目。

> [🧧加入](https://bugstack.cn/md/zsxq/other/join.html) 这样成体系的全量项目学习，放在一些平台售卖，至少都要上千块。但小傅哥的星球，只需要100多，就可以获得大厂架构师对你手把手教学！

**在今年的面试中，星球帮助众多伙伴拿到**`微信支付`、`京东科技`、`度小满`、`蚂蚁金服`、`Lazada（电商优惠营销）`、`快手`、`美团到店`等Offer，还有的校招生薪资最高年包到45w！
