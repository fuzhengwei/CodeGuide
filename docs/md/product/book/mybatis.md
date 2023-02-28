---
title: 2023年：《手写MyBatis：渐进式源码实践》
lock: no
---

# 《手写MyBatis：渐进式源码实践》—— 小傅哥新书上市！教你把MyBatis当成项目从零开发。

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>图书：[https://item.jd.com/13811216.html](https://union-click.jd.com/jdc?e=618%7Cpc%7C&p=JF8BANIJK1olXwUEXVhVAE4UC18IGVIVXQUCU24ZVxNJXF9RXh5UHw0cSgYYXBcIWDoXSQVJQwYAXV5dC0sQHDZNRwYlNlscAS4ZCQByfStdbxN3PkNgUR5ZaEcbM244G1oUXQEKU1hfCHsnA2g4STXN67Da8e9B3OGY1uefK1olXQEEUltZDUkQBW4KHmsSXQ8yIgoCXAhHXjhMK2slXjYFVFdJDjlWUXsOaWslXTYBZF5UAUsXC2oNHEcVXAYLUUJdD00RBmsNGVwQVQUBZFxcCU8eM18)

>沉淀、分享、成长，让自己和他人都能有所收获！

**讲屁话没有用**，想学好编程突破阶段瓶颈，为自己的职业生涯续期。就要把时间放在有价值的硬核项目上。因为`硬核项目` + `时间投入` = `价值回报`！

所以一股技术清流的小傅哥，为你编写了一本新书 **《手写MyBatis：渐进式源码实践》**。全书21章320页耗时2年出版。此书以实现MyBatis项目为目标，进行逐个功能模块渐进式拆解实现。就像手把手带着你敲项目一样，完成MyBatis的源码学习。通过这样的学习方式带你**领略源码级设计思维**，**突破满脑子都是MVC架构的技术瓶颈**。

所以千万别觉得开发项目只有 `MVC` 和 `DDD` 架构，否则为什么像 MyBatis 这样的源码不用 MVC架构写呢？—— `相信我，跟着小傅哥学习，会让你的编程思维提升到更高的水平。`

## 一、为什么学源码？

`代理`、`反射`、`池化`、`缓存`，MyBatis 给我们的不只是一个 ORM 框架，还包括了它经过深思熟虑所做的分层设计以及对应产生的，行之有效的解决方案。MyBatis 的存在不需要让你再刀耕火种般创建 JDBC，也不需要像使用 Hibernate 那么厚重到还需要增加学习 HQL 语句。同时 MyBatis 还支持通过插件机制扩展；监控、加密、路由等功能。因而如此简单且高效的 MyBatis ORM 框架，备受互联网大厂青睐，也是每一个 Java 程序员必须掌握的技术。

除了运用以外，MyBatis 框架也是众多码农，最能最先接触到的一个优质的**源码级别复杂项目**。此源码为了实现如此长周期软件迭代和维护，运用了分治和抽象进行模块设计，使用了**数10种**设计模型进行代码开发。这哪仅仅是一个 ORM 框架，**这简直是学习设计模式的最佳源码级实践资料**。

但就是这样已经很牛逼的学习资料，天天使用的技术框架。而且明知道学习它能有巨大的收获，但却是无从下手。因为很大一部分研发伙伴，在没有经历过中间件的设计和开发，满脑子都是 MVC 架构，也没用过几个设计模式的情况下，很难读懂源码级框架的设计。

`为此已经在 MyBatis 源码学习中得到受益的小傅哥`，希望把应对这样的硬核项目学习的方式方法，分享给从事编程开发的技术同好。通过我对 MyBatis 框架的理解和多年中间件的开发的经验，把 MyBatis 框架拆解为一个以`需求驱动`、`分支开发`、`渐进实现`的方式，展示给读者。**让即使是编程小白，也能沿着这条路走到终点获得巨大收获。**

<div align="center">
    <img src="https://bugstack.cn/images/article/product/book/mybatis-04.png?raw=true" width="450px">
</div>

## 二、学源码的必要！

从此你的简历就是可以写一段**《手写MyBatis》**项目学习：”我就是掌握了复杂源码的架构设计能力、我就是吸收了复杂场景分治和抽象的思想、我就是学会了复杂结构中设计模式的运用“。聊 MyBatis 聊的就是你手写的代码，有什么不会的，来你问吧。**以后路，你可以横着走！** 编写到简历上，给简历加分；

1. 体现在专业技能上，例如；深入学习 MyBaits 核心流程模块，包括；会话、反射、代理、事务、插件等流程，熟练掌握 ORM 框架的设计思想、实现方式和应用价值。并能按需结合 MyBatis 的插件机制，开发属于企业自己所需的功能，包括；数据分页、数据库表路由、监控日志、数据安全等方面。
2. 体现在项目经验上，例如；`对校招和实习比较有用` 把 MyBatis 当一个学习项目来描述，这是你在离校前，最可能接触到的一个完整的、成型的、知名的，有企业使用的框架。你就按照自己学习并开发了这样一个框架为目标来写项目，并描述出这个项目，你用了什么技术栈，解决了什么问题，学习到了哪些知识。
3. 体现在项目应用上，例如； 关于MyBatis 的项目，一般都是插件类开发，比如各类的MyBatis 插件，都是基于框架的深入整合类技术解决方案，体现在简历上，非常抓眼球。一看你就是有深度和自研能力的研发人员。—— 一般不让你造轮子，但需要你有造轮子的能力，这样企业中一些软件可以被你进行优化和修改。
4. 体现在解决问题是上，例如； 在你的自己的业务项目中，深入一些关于解决了原项目使用 MyBatis 时所遇到的问题，因为你学习过源码，所以非常清晰这样的流程，因此解决了一个问题。包括；事务、查询次数、批查询、插件能监听到的四个类（ParameterHandler、ResultSetHandler、StatementHandler、Executor ）你给了更好的选择。

## 三、下手这本新书！

<div align="center">
    <img src="https://bugstack.cn/images/article/product/book/mybatis-03.png?raw=true" width="550px">
</div>

本书共 22 章：

- 第 01 ~ 04 章：拆解和实现 ORM 框架的基本功能，构建会话的基本调用流程，初解析 XML 文件，以及串联 DefaultSqlSession 结合解析配置项获取展示信息。
- 第 05 ~ 08 章：创建和使用数据源，池化技术的实现，完成执行 SQL 语句的操作，同时引入反射工具包，实现对属性信息的获取和设置。
- 第 09 ~ 12 章：以实现 ORM 框架的基本功能为目的，完善静态 SQL 的标准化解析、参数设置和结果封装，使整个 ORM 框架可以处理基本的增、删、改、查操作。
- 第 13 ~ 19 章：以完善 ORM 框架的核心功能逻辑为目的，实现注解 SQL 解析、 ResultMap 参数、事务处理自增索引、动态 SQL 解析、插件、一级缓存和二级缓 存等功能。
- 第 20 ~ 22 章：利用 ORM 框架整合 Spring 和 SpringBoot，并介绍整个核心流程， 同时总结 ORM 框架开发中涉及的 10 种设计模式。

本书通过渐进式的开发方式来实现整个 MyBatis 核心源码的开发。每章开头会先列出难度和重点，再介绍要处理的问题、具体设计和实现代码，最后给出测试验证和总结。—— **我希望教会你的不只是MyBatis源码，还有手撕源码的本事！**

|                            书籍样章截图                            |
| :--------------------------------------------------------: |
|<div align="center"><img src="https://bugstack.cn/images/article/product/book/mybatis-01.png?raw=true" width="550px"></div> |
|<div align="center"><img src="https://bugstack.cn/images/article/product/book/mybatis-02.png?raw=true" width="550px"></div>                                                            |

## 四、源码全貌地图！

这是小傅哥在编写**《手写MyBatis：渐进式源码实践》**图书时，绘制的源码全貌地图。并结合地图的脉络，带着大家逐步实现这里面的功能模块，分章节细化各个模块的实现流程，最终让读者实现出一个丰富、全面、细致的 ORM 框架。

![](https://bugstack.cn/images/article/product/book/mybatis-06.png)

---

**感谢图书编辑**：`宋亚东`、`杨中兴`

**感谢大佬推荐**：`思否CTO-祁宁(@Joyqi)`、`中国科学院大学研究生导师-刘俊明`、`Apipost 创始人-穆红伟`、`京东垂直业务负责人-孙浩`、`京东授信认证业务技术负责人-郭泽渊`、`GitHub开源项目JavaGuide作者-G哥`、`《深入理解高并发编程:核心原理与案例实战》图书作者-冰河`

