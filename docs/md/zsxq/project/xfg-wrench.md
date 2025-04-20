---
title: 通用技术组件 - 🔧扳手工程
lock: no
---

# 通用技术组件 - 🔧扳手工程，凝练共性功能，实现通用组件。为各个业务系统赋能！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>课程：[https://t.zsxq.com/o7IBm](https://t.zsxq.com/o7IBm)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

本节开始我们要推进 **《通用组件扳手工程的落地》**，扳手工程的核心目的，是为了给星球内各个业务系统实现时，所需的一些共用模块，包括；通用的组件功能、共性的模式设计、标准的异常定义等。

当你学习大营销、拼团、OpenAI 应用等业务系统时，会遇到很多同类的功能诉求，如；规则树的设计模式、DCC 动态配置中心、接口限流配置等。这些东西其实都可以被抽象凝练成一个通用的技术组件，引入后直接配置使用即可，而不需要在每个业务系统中都开发一遍。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/xfg-wrench/xfg-wrench-0-03.png" width="950px"/>
</div>

所以，这就是我们这套系统组件实现的目的。其实在互联网公司中也是这样的，一个业务项目团队，除了业务项目，还有组内的架构师把很多通用的逻辑，提炼出来开发成一个个通用的技术组件，让各个业务系统工程引入使用。

> 学习本组件项目，不需要其他项目做为前置课程。学习后，可以在其他项目进行使用。

## 一、工程计划

以星球内业务项目开发诉求，会不断的提炼出通用的组件，短期计划包括；

- [x] DCC 动态配置中心
- [x] 限流服务组件
- [x] 通用设计模式模型框架
- [ ] 更多 ...

## 二、架构设计

设计通用的统一规范的扳手工程；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/xfg-wrench/xfg-wrench-0-01.png" width="450px"/>
</div>

- 首先，我们会搭建一套标准的扳手框架 Spring Starter 工程。在工程内，以模块化方式陆续实现，动态配置、限流服务、通用设计模块框架。
- 之后，以 Redis 作为简单注册中心使用，管理动态配置、限流服务等，再以 admin 管理端，下达配置命令，来动态操作工程配置。

## 三、工程结构

<div align="center">
	<img src="https://bugstack.cn/images/article/project/xfg-wrench/xfg-wrench-0-02.png" width="450px"/>
</div>

- 地址：[https://gitcode.net/KnowledgePlanet/xfg-wrench](https://gitcode.net/KnowledgePlanet/xfg-wrench)
- 说明：
  - 定义 xfg-wrench 扳手工程，以 xfg-wrench-starter 为前缀，命名各项服务组件。如；`xfg-wrench-starter-dynamic-config-center`
  - xfg-wrench-test 为测试工程，用于验证各个模块的功能实现。
  - xfg-wrench-admin 为管理后台，后续陆续创建完成。以及其他模块组件陆续迭代开发。

---

这将是一个新的技术旅程，在这趟车上🚗，你会了解到互联网公司大厂🏭是如何对这类场景的方案落地的。
