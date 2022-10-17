---
title: Bug-Code：异常模拟复现
lock: no
---

# Bug-Code —— 收集实际开发中所遇到的异常进行模拟复现

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>星球：[https://t.zsxq.com/05zj2niYR](https://t.zsxq.com/05zj2niYR)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=816712453&bvid=BV1RG4y1n7Ep&cid=863241955&page=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

## 一、前言

看到星球VIP群，读者伙伴分享的面试真实场景问题和讨论，发现很多没经过太多编码的研发伙伴，可能并没有看到过这样的异常，所以理解起来也有些困难。那么为了让大家更好的吸收这些实战经验，小傅哥特此创建这样一个 Bug-Code 仓库，用于给星球读者积累一些工作中常见的异常复现，积累编程经验。

- **工程仓库**：[https://gitcode.net/KnowledgePlanet/CodeTutorial/Bug-Code](https://gitcode.net/KnowledgePlanet/CodeTutorial/Bug-Code)
- **加入项目**：[https://t.zsxq.com/jAi2nUf](https://t.zsxq.com/jAi2nUf) - 加入后在星球置顶消息可以申请加入项目组，`公众号：bugstack虫洞栈 回复：星球 可以获得加入优惠券`

## 二、异常问题

### 1. Transaction rolled back because it has been marked as rollback-only

- 问题：rollback-only
- 异常：线程执行某个定时任务，在事务提交时抛出了异常。看到rollback-only字样，这个是什么原因引起的。写代码要注意什么能避免产生这一种情况。
- 测试：用数据库表防重做插入测试，触发异常；
    - 1. 两个方法都加了事务注解，两个方法都会受到到事务管理的拦截器增强，并且事务传播的方式都是 REQUIRED，当已经存在事务的时候就加入事务，没有就创建事务。这里A和B都受事务控制，并且是处于同一个事务的。
    - 2. A调用B，A中抓了B的异常，当B发生异常的时候，B的操作应该回滚，但是A吃了异常，A方法中没有产生异常，所以A的操作又应该提交，二者是相互矛盾的。
    - 3. Spring的事务关联拦截器在抓到B的异常后就会标记rollback-only为true，当A执行完准备提交后，发现rollback-only为true，也会回滚，并抛出异常告诉调用者。

- 复现：[https://gitcode.net/KnowledgePlanet/CodeTutorial/Bug-Code/-/blob/master/src/test/java/cn/bugstack/guide/test/RollbackOnlyTest.java](https://gitcode.net/KnowledgePlanet/CodeTutorial/Bug-Code/-/blob/master/src/test/java/cn/bugstack/guide/test/RollbackOnlyTest.java)

### 2. Transaction rolled back because it has been marked as rollback-only

- 问题：死锁
- 异常：Deadlock found when trying to get lock; try restarting transaction
- 测试：多线程模拟并发下，一个事务未提交完成，又来一个事务。
- 复现：[https://gitcode.net/KnowledgePlanet/CodeTutorial/Bug-Code/-/blob/master/src/test/java/cn/bugstack/guide/test/DeadlockTest.java](https://gitcode.net/KnowledgePlanet/CodeTutorial/Bug-Code/-/blob/master/src/test/java/cn/bugstack/guide/test/DeadlockTest.java)

### 3. Spring 线程安全处理

- 问题：串号
- 异常：在一个单例的 OrderService 中，使用多线程调用，对一个属性设置值后再获取，那么会出现 OrderNo 串号的问题。
- 测试：分别使用普通的单例方式和加入 ThreadLocal 根据线程获取单号
- 复现：[https://gitcode.net/KnowledgePlanet/CodeTutorial/Bug-Code/-/blob/master/src/test/java/cn/bugstack/guide/test/ThreadLocalTest.java](https://gitcode.net/KnowledgePlanet/CodeTutorial/Bug-Code/-/blob/master/src/test/java/cn/bugstack/guide/test/ThreadLocalTest.java)