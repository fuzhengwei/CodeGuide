---
title: 第 9 章 结合 SpringBoot 开发 ORM Starter
pay: https://t.zsxq.com/0c7qkNTdA
---

# 第 9 章 结合 SpringBoot 开发 ORM Starter

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、前言

`开发一个中间件你会遇到什么样的技术障碍？`

有些时候，有些知识，不是因为难不会，而是因为不知道而不会。这类的技术点并不会多难，因为这些知识都不是对复杂数学逻辑的具体实现过程学习，只是一种约定、规范、标准而已。

就像如果你没尝试过把业务系统中的非业务逻辑开发成组件，那么可能就不会想到要这么做。当后来你了解了有中间件这样一种解决方案时，可能你会遇到不知道中间件开发的框架是什么样，该怎么读取一些定义的配置或者是注解，以及如何与 SpringBoot 

这些问题在你实际开发过程中都会一点点遇到，其实最好的学习方式的是有类似的成长土壤或者系统的学习过，但这方面资料其实并不多，它不像业务开发有很多的资料可以查阅。所以如果以上都没有，又遇到的是新问题，那么阅读同类的源码是最好的方式。

## 二、需求背景

我们已经在前两章实现了 ORM 框架，也把自己实现的 ORM 框架与 Spring 结合，可以让我们像使用 MyBatis-Spring 一样使用我们的实现的组件。

那么接下来我们需要做的就是，把这个自己实现的 mybatis 和 mybatis-spring，与 SpringBoot 结合，开发出一个类似 mybatis-spring-boot-starter 的 ORM 组件。

*mybatis-spring-boot-starter 的官方源码在这里 https://github.com/mybatis/spring-boot-starter，感兴趣的小伙伴可以阅读一下。* 