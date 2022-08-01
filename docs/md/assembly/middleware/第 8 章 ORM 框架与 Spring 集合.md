---
title: 第 8 章 ORM 框架与 Spring 集合
pay: https://juejin.cn/book/6940996508632219689/section/6941278434337226765
---

# 第 8 章 ORM 框架与 Spring 集合

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、前言

`技术迁移在实现中间件中的运用`

作为一个 Java 程序员，通常不只是学习 Java 的基础内容，还会随着工作年限开始不断的学习一些框架源码。尤其是 MyBatis、Spring，但可能很大一部分研发人员并不知道学习这些源码最终会用到什么地方，只是觉得学习了可以更好的了解这些技术。

但其实学习这些源码技术更有用的方式是做一些技术迁移工作，将框架源码中的技术设计、思想模式运用到我们的业务中间件开发体系中，例如 Spring 中的 Bean 注入和管理，那么我们也可以尝试将自己的一些抽离于业务形态的逻辑对象，注入到 Spring 中，再供给业务系统注入使用。

## 二、需求背景

ORM 框架与 Spring 结合的需求背景就是，**什么是 MyBatis-Spring？**

MyBatis-Spring 会帮助你将 MyBatis 代码无缝地整合到 Spring 中。它将允许 MyBatis 参与到 Spring 的事务管理之中，创建映射器 mapper 和 SqlSession 并注入到 bean 中，以及将 Mybatis 的异常转换为 Spring 的 DataAccessException。 最终，可以做到应用代码不依赖于 MyBatis，Spring 或 MyBatis-Spring。

那么，我们要实现的目标就是把自己实现的 ORM 框架与 Spring 结合，交给 Spring 管理。当然我们会使用最直接和简化的方式把核心代码实现出来，让大家可以更清楚的看到这部分功能实现的逻辑。