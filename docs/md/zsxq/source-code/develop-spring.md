---
title: 手写Spring：渐进式源码实践
lock: no
---

# 手写Spring：渐进式源码实践

<div align="center">
    <img src="https://bugstack.cn/images/article/spring/spring-1-00.png?raw=true">
    <div style="font-size: 12px;"><a href="https://t.zsxq.com/Ja27ujq">星球介绍：码农会锁 - 实战项目、专属小册、问题解答、简历指导、架构图稿、视频课程</a></div>
</div>

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>原文：[https://mp.weixin.qq.com/s/g7YdIe_FSrk-WE8nQRO3TA](https://mp.weixin.qq.com/s/g7YdIe_FSrk-WE8nQRO3TA)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`不正经！写写面经，去撸Spring源码啦🌶？`

是的，在写了4篇关于Spring核心源码的面经内容后，我决定要去手撸一个Spring了。为啥这么干呢？因为所有我想写的内容，都希望它是以理科思维理解为目的的学会，而不是靠着硬背记住。而目前面经中涉及到的每一篇Spring源码内容分析，在即使去掉部分非主流逻辑后，依然会显得非常庞大。*对有经验的老司机尚可阅读几遍接受，但就新人来讲只能放入收藏夹吃灰啦！*

[![](https://bugstack.cn/assets/images/spring/spring-1-01.png)](https://download.csdn.net/download/Yao__Shun__Yu/14932325)

可能已经阅读过 **2.5k下载量**的 [《Java面经手册》](https://download.csdn.net/download/Yao__Shun__Yu/14932325)的小伙伴会知晓，这是一本以面试题为入口讲解 Java 核心内容的技术书籍，书中内容极力的向你证实代码是对数学逻辑的具体实现。当你仔细阅读书籍时，会发现Java中有大量的数学知识，包括：扰动函数、负载因子、拉链寻址、开放寻址、斐波那契（Fibonacci）散列法还有黄金分割点的使用等等。

所以在编写面经手册关于 Spring 系列时，我也希望它是一项有益于程序员真正成长的技术资料和价值汇总，而不仅仅是对一些列繁杂内容的罗列。那么从借鉴 [tiny-spring]([https://github.com/code4craft/tiny-spring](https://github.com/code4craft/tiny-spring))、[mini-spring](https://github.com/fuzhengwei/small-spring) 以及对我对Spring的学习和常折腾开发中间件的经验上，来编写一款适合自己沉淀也满足于大家学习的Spring资料。

*傅哥的面经都是“假”的，一上来就学数学、撸源码、挖核心！* 好！既然你这么说，接下来我们定义`目标`、`计划`，开始撸`源码`！

## 二、目标

![](https://bugstack.cn/assets/images/spring/spring-1-02.png)

本仓库以 Spring 源码学习为目的，通过带着读者一点点手写简化版 Spring 框架，了解 Spring 核心原理，为后续再深入学习 Spring 打下基础。

在手写的过程中会剔除 Spring 源码中繁杂的内容，摘取整体框架中的核心逻辑，简化代码实现过程，保留核心功能，例如：IOC、AOP、Bean生命周期、上下文、作用域、资源处理等内容实现。

所有的内容实现都会由简开始，一步步带着大家实现，最终所有的内容完成后，在提供一个相对完整的 small-spring，在这个过程中只要你能跟着走下来，那么最后你一定可以较容易的阅读 Spring 源码了。

## 三、目录

### 容器篇：IOC

   - [第1章：开篇介绍，手写Spring能给你带来什么？](https://bugstack.cn/md/spring/develop-spring/2021-05-16-%E7%AC%AC1%E7%AB%A0%EF%BC%9A%E5%BC%80%E7%AF%87%E4%BB%8B%E7%BB%8D%EF%BC%8C%E6%89%8B%E5%86%99Spring%E8%83%BD%E7%BB%99%E4%BD%A0%E5%B8%A6%E6%9D%A5%E4%BB%80%E4%B9%88%EF%BC%9F.html)
   - [第2章：小试牛刀，实现一个简单的Bean容器](https://bugstack.cn/md/spring/develop-spring/2021-05-20-%E7%AC%AC2%E7%AB%A0%EF%BC%9A%E5%B0%8F%E8%AF%95%E7%89%9B%E5%88%80%EF%BC%8C%E5%AE%9E%E7%8E%B0%E4%B8%80%E4%B8%AA%E7%AE%80%E5%8D%95%E7%9A%84Bean%E5%AE%B9%E5%99%A8.html)
   - [第3章：初显身手，运用设计模式，实现 Bean 的定义、注册、获取](https://bugstack.cn/md/spring/develop-spring/2021-05-23-%E7%AC%AC3%E7%AB%A0%EF%BC%9A%E5%88%9D%E6%98%BE%E8%BA%AB%E6%89%8B%EF%BC%8C%E8%BF%90%E7%94%A8%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%EF%BC%8C%E5%AE%9E%E7%8E%B0%20Bean%20%E7%9A%84%E5%AE%9A%E4%B9%89%E3%80%81%E6%B3%A8%E5%86%8C%E3%80%81%E8%8E%B7%E5%8F%96.html)
   - [第4章：崭露头角，基于Cglib实现含构造函数的类实例化策略](https://bugstack.cn/md/spring/develop-spring/2021-05-30-%E7%AC%AC4%E7%AB%A0%EF%BC%9A%E5%B4%AD%E9%9C%B2%E5%A4%B4%E8%A7%92%EF%BC%8C%E5%9F%BA%E4%BA%8ECglib%E5%AE%9E%E7%8E%B0%E5%90%AB%E6%9E%84%E9%80%A0%E5%87%BD%E6%95%B0%E7%9A%84%E7%B1%BB%E5%AE%9E%E4%BE%8B%E5%8C%96%E7%AD%96%E7%95%A5.html)
   - [第5章：一鸣惊人，为Bean对象注入属性和依赖Bean的功能实现](https://bugstack.cn/md/spring/develop-spring/2021-06-02-%E7%AC%AC5%E7%AB%A0%EF%BC%9A%E4%B8%80%E9%B8%A3%E6%83%8A%E4%BA%BA%EF%BC%8C%E4%B8%BABean%E5%AF%B9%E8%B1%A1%E6%B3%A8%E5%85%A5%E5%B1%9E%E6%80%A7%E5%92%8C%E4%BE%9D%E8%B5%96Bean%E7%9A%84%E5%8A%9F%E8%83%BD%E5%AE%9E%E7%8E%B0.html)
   - [第6章：气吞山河，设计与实现资源加载器，从Spring.xml解析和注册Bean对象](https://bugstack.cn/md/spring/develop-spring/2021-06-09-%E7%AC%AC6%E7%AB%A0%EF%BC%9A%E6%B0%94%E5%90%9E%E5%B1%B1%E6%B2%B3%EF%BC%8C%E8%AE%BE%E8%AE%A1%E4%B8%8E%E5%AE%9E%E7%8E%B0%E8%B5%84%E6%BA%90%E5%8A%A0%E8%BD%BD%E5%99%A8%EF%BC%8C%E4%BB%8ESpring.xml%E8%A7%A3%E6%9E%90%E5%92%8C%E6%B3%A8%E5%86%8CBean%E5%AF%B9%E8%B1%A1.html)
   - [第7章：所向披靡，实现应用上下文，自动识别、资源加载、扩展机制](https://bugstack.cn/md/spring/develop-spring/2021-06-17-%E7%AC%AC7%E7%AB%A0%EF%BC%9A%E6%89%80%E5%90%91%E6%8A%AB%E9%9D%A1%EF%BC%8C%E5%AE%9E%E7%8E%B0%E5%BA%94%E7%94%A8%E4%B8%8A%E4%B8%8B%E6%96%87%EF%BC%8C%E8%87%AA%E5%8A%A8%E8%AF%86%E5%88%AB%E3%80%81%E8%B5%84%E6%BA%90%E5%8A%A0%E8%BD%BD%E3%80%81%E6%89%A9%E5%B1%95%E6%9C%BA%E5%88%B6.html)
   - [第8章：龙行有风，向虚拟机注册钩子，实现Bean对象的初始化和销毁方法](https://bugstack.cn/md/spring/develop-spring/2021-06-23-%E7%AC%AC8%E7%AB%A0%EF%BC%9A%E9%BE%99%E8%A1%8C%E6%9C%89%E9%A3%8E%EF%BC%8C%E5%90%91%E8%99%9A%E6%8B%9F%E6%9C%BA%E6%B3%A8%E5%86%8C%E9%92%A9%E5%AD%90%EF%BC%8C%E5%AE%9E%E7%8E%B0Bean%E5%AF%B9%E8%B1%A1%E7%9A%84%E5%88%9D%E5%A7%8B%E5%8C%96%E5%92%8C%E9%94%80%E6%AF%81%E6%96%B9%E6%B3%95.html)
   - [第9章：虎行有雨，定义标记类型Aware接口，实现感知容器对象](https://bugstack.cn/md/spring/develop-spring/2021-06-28-%E7%AC%AC9%E7%AB%A0%EF%BC%9A%E8%99%8E%E8%A1%8C%E6%9C%89%E9%9B%A8%EF%BC%8C%E5%AE%9A%E4%B9%89%E6%A0%87%E8%AE%B0%E7%B1%BB%E5%9E%8BAware%E6%8E%A5%E5%8F%A3%EF%BC%8C%E5%AE%9E%E7%8E%B0%E6%84%9F%E7%9F%A5%E5%AE%B9%E5%99%A8%E5%AF%B9%E8%B1%A1.html)
   - [第10章：横刀跃马，关于Bean对象作用域以及FactoryBean的实现和使用](https://bugstack.cn/md/spring/develop-spring/2021-06-30-%E7%AC%AC10%E7%AB%A0%EF%BC%9A%E6%A8%AA%E5%88%80%E8%B7%83%E9%A9%AC%EF%BC%8C%E5%85%B3%E4%BA%8EBean%E5%AF%B9%E8%B1%A1%E4%BD%9C%E7%94%A8%E5%9F%9F%E4%BB%A5%E5%8F%8AFactoryBean%E7%9A%84%E5%AE%9E%E7%8E%B0%E5%92%8C%E4%BD%BF%E7%94%A8.html)
   - [第11章：更上层楼，基于观察者实现，容器事件和事件监听器](https://bugstack.cn/md/spring/develop-spring/2021-07-07-%E7%AC%AC11%E7%AB%A0%EF%BC%9A%E6%9B%B4%E4%B8%8A%E5%B1%82%E6%A5%BC%EF%BC%8C%E5%9F%BA%E4%BA%8E%E8%A7%82%E5%AF%9F%E8%80%85%E5%AE%9E%E7%8E%B0%EF%BC%8C%E5%AE%B9%E5%99%A8%E4%BA%8B%E4%BB%B6%E5%92%8C%E4%BA%8B%E4%BB%B6%E7%9B%91%E5%90%AC%E5%99%A8.html)

### 代理篇：AOP

   - [第12章：炉火纯青，基于JDK和Cglib动态代理，实现AOP核心功能](https://bugstack.cn/md/spring/develop-spring/2021-07-13-%E7%AC%AC12%E7%AB%A0%EF%BC%9A%E7%82%89%E7%81%AB%E7%BA%AF%E9%9D%92%EF%BC%8C%E5%9F%BA%E4%BA%8EJDK%E5%92%8CCglib%E5%8A%A8%E6%80%81%E4%BB%A3%E7%90%86%EF%BC%8C%E5%AE%9E%E7%8E%B0AOP%E6%A0%B8%E5%BF%83%E5%8A%9F%E8%83%BD.html)
   - [第13章：行云流水，把AOP动态代理，融入到Bean的生命周期](https://bugstack.cn/md/spring/develop-spring/2021-07-22-%E7%AC%AC13%E7%AB%A0%EF%BC%9A%E8%A1%8C%E4%BA%91%E6%B5%81%E6%B0%B4%EF%BC%8C%E6%8A%8AAOP%E5%8A%A8%E6%80%81%E4%BB%A3%E7%90%86%EF%BC%8C%E8%9E%8D%E5%85%A5%E5%88%B0Bean%E7%9A%84%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F.html)
   - [第14章：笑傲江湖，通过注解配置和包自动扫描的方式完成Bean对象的注册](https://bugstack.cn/md/spring/develop-spring/2021-07-27-%E7%AC%AC14%E7%AB%A0%EF%BC%9A%E7%AC%91%E5%82%B2%E6%B1%9F%E6%B9%96%EF%BC%8C%E9%80%9A%E8%BF%87%E6%B3%A8%E8%A7%A3%E9%85%8D%E7%BD%AE%E5%92%8C%E5%8C%85%E8%87%AA%E5%8A%A8%E6%89%AB%E6%8F%8F%E7%9A%84%E6%96%B9%E5%BC%8F%E5%AE%8C%E6%88%90Bean%E5%AF%B9%E8%B1%A1%E7%9A%84%E6%B3%A8%E5%86%8C.html)
   - [第15章：万人之敌，通过注解给属性注入配置和Bean对象](https://bugstack.cn/md/spring/develop-spring/2021-08-03-%E7%AC%AC15%E7%AB%A0%EF%BC%9A%E4%B8%87%E4%BA%BA%E4%B9%8B%E6%95%8C%EF%BC%8C%E9%80%9A%E8%BF%87%E6%B3%A8%E8%A7%A3%E7%BB%99%E5%B1%9E%E6%80%A7%E6%B3%A8%E5%85%A5%E9%85%8D%E7%BD%AE%E5%92%8CBean%E5%AF%B9%E8%B1%A1.html)
   - [第16章：战无不胜，给代理对象的属性设置值](https://wx.zsxq.com/dweb2/index/topic_detail/212854215518421) —— 星球专属

### 高级篇：Design

   - [第17章：攻无不克，通过三级缓存解决循环依赖](https://wx.zsxq.com/dweb2/index/topic_detail/212854215518421) —— 星球专属
   - [第18章：挂印封刀，数据类型转换工厂设计实现](https://wx.zsxq.com/dweb2/index/topic_detail/212854215518421) —— 星球专属


## 五、总结

- 当你阅读 Spring 源码时你会看到各种的嵌套、递归、代理，以及可能连想调试时都不清楚断点要打在哪里，运行起来的程序跳来跳去。最终导致自己也就看不下去这份源码了！这是因为 Spring 发展的太久了，它为了满足不同的场景，已经做了太多的补充和优化，所以我们要做的是剥丝抽茧，体现核心，把最直接相干的内容体现出来进行学习，才更容易理解。
- 在源码学习的过程中，小傅哥会和你一起从最简单、最简单的Bean容器开始，可能有些时候某些章节内容并不会太多，不过我会帮你建立一些知识关联，尽可能让你在这个学习过程中，收获更多。
- 那么本章节关于 `Spring 手撸`专栏的开篇介绍就到这了，接下来你可以阅读到文章、获取到源码，直至我们把所有的内容全部完成，到时候就可以开发出一个相对完整的 Spring 框架了。希望在这个过程中你能和我一直坚持学习打卡！
