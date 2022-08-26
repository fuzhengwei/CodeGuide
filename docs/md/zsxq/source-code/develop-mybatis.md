---
title: 手写Mybatis：渐进式源码实践
lock: no
---

# 手写Mybatis：渐进式源码实践

<div align="center">
    <img src="https://bugstack.cn/images/article/spring/mybatis-220708-00.png?raw=true">
    <div style="font-size: 12px;"><a href="https://t.zsxq.com/Ja27ujq">星球介绍：码农会锁 - 实战项目、专属小册、问题解答、简历指导、架构图稿、视频课程</a></div>
</div>

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>星球：[https://t.zsxq.com/Ja27ujq](https://t.zsxq.com/Ja27ujq) - 加入星球解锁小册全部文章和源码，以及获得[更多的学习项目](https://bugstack.cn/md/project/group/zsxq.html)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=639432610&bvid=BV1nY4y1B7eT&cid=731967511&page=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

## 1. 为甚，撸Mybatis

`我就知道，你会忍不住对它下手！🤨`

21年带着粉丝伙伴撸了一遍 Spring 源码，通过提取整个框架中的核心逻辑，简化代码实现过程，渐进式开发逐步实现 IOC、AOP 中的核心功能。让读者可以由浅入深的学习 Spring 的设计思路和落地代码，也能让更多的读者可以读懂 Spring 源码，当然这也包括让一些跟着动手实践的读者在面试中脱颖而出！

吃过`小傅哥`代码的读者，都知道**TMD真香**，网盘1个T的学习视频都删了，用来存小傅哥的技术资料。所以小傅哥日常也被读者催更，`傅哥写Mybatis吧`、`傅哥写Mybatis吧`、`傅哥写Mybatis吧`，既然你们都喊了这么久了，我也该把这事办了。

## 2. 好奇，驱动学习

从小傅哥最开始对 Mybatis 感兴趣，主要来自于好奇：**“为什么在使用 Mybatis 的时候，只需定义一个接口，不用写实现类就能使用 XML 中或者注解上配置好的 SQL 语句，就能完成对数据库 CRUD 的操作呢？”** 原来这里最核心的是用到了接口代理类，把每一个数据库操作的 DAO 接口都用操作数据库的代理类实现，并注册到 Spring 容器让用户去使用。

![Mybatis 代理实现调用封装返回结果](https://bugstack.cn/images/article/spring/mybatis-220320-01.png)

其实很多时候对技术的`深度学习`就是来自于这样一点点的`好奇`和一晚晚的`折腾`，虽然会遇到很多磕磕绊绊的 bug，但经过自己的思考、整理、验证、汇总，你终会提炼出最有技术价值的核心流程和脉络信息，就像我们上面这张图一样，虽然它不是全部但这却是最重要的一部分。

## 3. 目标，肝成啥样

![](https://bugstack.cn/images/article/spring/mybatis-220320-02.png)

像我们之前完成[手写 Spring](https://mp.weixin.qq.com/s/g7YdIe_FSrk-WE8nQRO3TA)一样，拆解功能、简化流程、渐进实现，让读者能够更容易的学习到最有价值的知识。

在手写的过程中学习 Mybatis 从解析、绑定、反射、缓存，到会话和事务操作，以及如何与 Spring 进行关联注册 Bean 对象，完成整合部分功能逻辑。通过这些内容的拆解实现，读者伙伴就可以非常清楚的知道这些核心功能都是如何实现的了，以后再阅读 Mybatis 源码也就知道从哪开始到哪结束了。

## 4. 视角，全貌地图

![](https://bugstack.cn/images/article/spring/mybatis-220320-00.png)

这是整个《手撸 Mybatis》的全貌地图，小傅哥会带着大家逐步实现这里面的功能模块，分章节细化各个模块的实现流程，最终让读者实现出一个丰富、全面、细致的 ORM 框架。在学习的过程中，大家也可以参考这张图来对照手写的代码以及 Mybatis 的源码，这样更加有利于对 Mybatis 框架的理解。

## 5. 计划，执行落地

![](https://bugstack.cn/images/article/spring/mybatis-220320-03.png)

- 小傅哥之前也写过简版的 Mybatis、Mybatis-Spring，但当时主要是把重点放在和 Spring 的结合上，对于 ORM 的框架实现部分只是一个非常最基本的实现。
- 所以计划这次按照 Mybatis 的框架结构，把重点放在 ORM 功能实现为主，通过源码分析和功能实现的方式完成这次框架功能的开发。在每次开发功能时候都会基于上次的章节进行内容迭代，慢慢的把这些功能类累加出来，这样的方式也更好让读者理解每一个功能都是怎么被设计进来并开发实现的。

## 6. 这里，课程目录

- 代码：https://gitcode.net/KnowledgePlanet/TearSourceCode/small-mybatis

- [【难度☆☆☆☆☆】第01章：开篇介绍，我要带你撸 Mybatis 啦！](https://bugstack.cn/md/spring/develop-mybatis/2022-03-20-%E7%AC%AC1%E7%AB%A0%EF%BC%9A%E5%BC%80%E7%AF%87%E4%BB%8B%E7%BB%8D%EF%BC%8C%E6%89%8B%E5%86%99Mybatis%E8%83%BD%E7%BB%99%E4%BD%A0%E5%B8%A6%E6%9D%A5%E4%BB%80%E4%B9%88%EF%BC%9F.html)

### 第 1 部分 - 基础框架

- [【难度★★☆☆☆】第02章：创建简单的映射器代理工厂](https://bugstack.cn/md/spring/develop-mybatis/2022-03-27-%E7%AC%AC2%E7%AB%A0%EF%BC%9A%E5%88%9B%E5%BB%BA%E7%AE%80%E5%8D%95%E7%9A%84%E6%98%A0%E5%B0%84%E5%99%A8%E4%BB%A3%E7%90%86%E5%B7%A5%E5%8E%82.html)
- [【难度★★☆☆☆】第03章：实现映射器的注册和使用](https://bugstack.cn/md/spring/develop-mybatis/2022-04-04-%E7%AC%AC3%E7%AB%A0%EF%BC%9A%E5%AE%9E%E7%8E%B0%E6%98%A0%E5%B0%84%E5%99%A8%E7%9A%84%E6%B3%A8%E5%86%8C%E5%92%8C%E4%BD%BF%E7%94%A8.html)
- [【难度★★★☆☆】第04章：Mapper XML的解析和注册使用](https://bugstack.cn/md/spring/develop-mybatis/2022-04-09-%E7%AC%AC4%E7%AB%A0%EF%BC%9AXML%E7%9A%84%E8%A7%A3%E6%9E%90%E5%92%8C%E6%B3%A8%E5%86%8C%E4%BD%BF%E7%94%A8.html)

### 第 2 部分 -  模块服务

- [【难度★★★☆☆】第05章：数据源的解析、创建和使用](https://bugstack.cn/md/spring/develop-mybatis/2022-04-17-%E7%AC%AC5%E7%AB%A0%EF%BC%9A%E6%95%B0%E6%8D%AE%E6%BA%90%E7%9A%84%E8%A7%A3%E6%9E%90%E3%80%81%E5%88%9B%E5%BB%BA%E5%92%8C%E4%BD%BF%E7%94%A8.html)
- [【难度★★★★☆】第06章：数据源池化技术实现](https://bugstack.cn/md/spring/develop-mybatis/2022-04-23-%E7%AC%AC6%E7%AB%A0%EF%BC%9A%E6%95%B0%E6%8D%AE%E6%BA%90%E6%B1%A0%E5%8C%96%E6%8A%80%E6%9C%AF%E5%AE%9E%E7%8E%B0.html)
- [【难度★★★☆☆】第07章：SQL执行器的定义和实现](https://bugstack.cn/md/spring/develop-mybatis/2022-04-28-%E7%AC%AC7%E7%AB%A0%EF%BC%9ASQL%E6%89%A7%E8%A1%8C%E5%99%A8%E7%9A%84%E5%AE%9A%E4%B9%89%E5%92%8C%E5%AE%9E%E7%8E%B0.html)
- [【难度★★★★★】第08章：把反射用到出神入化](https://bugstack.cn/md/spring/develop-mybatis/2022-05-03-%E7%AC%AC8%E7%AB%A0%EF%BC%9A%E6%8A%8A%E5%8F%8D%E5%B0%84%E7%94%A8%E5%88%B0%E5%87%BA%E7%A5%9E%E5%85%A5%E5%8C%96.html)

### 第 3 部分 -  串联流程

- [【难度★★★★☆】第09章：细化XML语句构建器，完善静态SQL解析](https://bugstack.cn/md/spring/develop-mybatis/2022-05-18-%E7%AC%AC9%E7%AB%A0%EF%BC%9A%E7%BB%86%E5%8C%96XML%E8%AF%AD%E5%8F%A5%E6%9E%84%E5%BB%BA%E5%99%A8%EF%BC%8C%E5%AE%8C%E5%96%84%E9%9D%99%E6%80%81SQL%E8%A7%A3%E6%9E%90.html)
- [【难度★★★★☆】第10章：使用策略模式，调用参数处理器](https://bugstack.cn/md/spring/develop-mybatis/2022-05-26-%E7%AC%AC10%E7%AB%A0%EF%BC%9A%E4%BD%BF%E7%94%A8%E7%AD%96%E7%95%A5%E6%A8%A1%E5%BC%8F%EF%BC%8C%E8%B0%83%E7%94%A8%E5%8F%82%E6%95%B0%E5%A4%84%E7%90%86%E5%99%A8.html)
- [【难度★★★☆☆】第11章：流程解耦，封装结果集处理器](https://bugstack.cn/md/spring/develop-mybatis/2022-06-02-%E7%AC%AC11%E7%AB%A0%EF%BC%9A%E6%B5%81%E7%A8%8B%E8%A7%A3%E8%80%A6%EF%BC%8C%E5%B0%81%E8%A3%85%E7%BB%93%E6%9E%9C%E9%9B%86%E5%A4%84%E7%90%86%E5%99%A8.html)
- [【难度★★★☆☆】第12章：完善ORM框架，增删改查操作](https://bugstack.cn/md/spring/develop-mybatis/2022-06-10-%E7%AC%AC12%E7%AB%A0%EF%BC%9A%E5%AE%8C%E5%96%84ORM%E6%A1%86%E6%9E%B6%EF%BC%8C%E5%A2%9E%E5%88%A0%E6%94%B9%E6%9F%A5%E6%93%8D%E4%BD%9C.html)

### 第 4 部分 -  扩展功能 - 星球用户专属

- [【难度★★★☆☆】第13章：通过注解配置执行SQL语句](https://articles.zsxq.com/id_cushlx5xx5wj.html)
- [【难度★★★☆☆】第14章：解析和使用ResultMap映射参数配置](https://articles.zsxq.com/id_lszxhhridkrl.html)
- [【难度★★★☆☆】第15章：返回Insert操作自增索引值](https://articles.zsxq.com/id_gwfa0x8nciwn.html)
- [【难度★★★☆☆】第16章：解析含标签的动态SQL语句](https://articles.zsxq.com/id_1rzspttev8ls.html)
- [【难度★★★★☆】第17章：Plugin 插件扩展](https://articles.zsxq.com/id_k7havmcaefdk.html)
- [【难度★★☆☆☆】第18章：一级缓存](https://articles.zsxq.com/id_8d0o471we1ig.html)
- [【难度★★★★☆】第19章：二级缓存](https://articles.zsxq.com/id_gviwjzv5h4wp.html)
- [【难度★★★★☆】第20章：【作业】整合Spring](https://articles.zsxq.com/id_2pwtkmjixi73.html)

## 7. 作业：优秀学员

- [超清晰绘图拆解流程 By 课代表 阿羲 ⭐](https://t.zsxq.com/05vZ76aYr)
- [池化连接池执行过程 By JabesYang](https://t.zsxq.com/05AiEAMfu)

## 8. 总结，一点建议

可能很多小伙伴的网盘都有几百G的资料的，但其实并没有时间去看，一方面是这些资料都是七七八八拼凑的，很难有一条完整的脉络，甚至有些资料已经过时了，只不过放在网盘里感觉心安一样。

小傅哥之前也收藏资料，把2T的网盘都塞满了，但过去这么多年了，也没看过。所以千万记住，别被无穷的资料，占用了有限的时间，只有成体系的学习才能收获最多。只追求快，那是最大的学习障碍！