---
title: 第3-1节：MVC 工程框架搭建 + 基础配置 + Git 使用
pay: https://t.zsxq.com/K4xTs
---

# 《小型支付商城系统》第3-1节：MVC 工程框架搭建 + 基础配置 + Git 使用

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

手把手搭建，单 model 和多 model 的 mvc 分层架构，并配置 pom、yml 等，以及讲解分层结构的功能职责。最后将工程使用 Git 提交到 gitcode 仓库中。

mvc 的优势在于快，这个快是来自于不对系统进行建模、不考虑防腐（多层调用间的对象，不做隔离处理，可互相调用），也没有为分布式的各项连接资源做分层考虑，所以它的实现方式更快。

在最早我们实现单体类应用中，一个工程基本就使用2个东西，一个是 mysql 数据库，另外一个是 redis 缓存。这样的 mvc 工程复杂度是很低的，所以也比较好维护。

但随着分布式微服务的引入，在一个工程中除了有，数据库、缓存，还有 rpc、mq、任务调度、配置中心、一致性组件、分库分表、ElasticSearch 等等各项技术栈，这样在 mvc 中在实现这些就变得复杂了。所以这也是为什么大家开始逐步往 DDD 迁移，因为一方面是 DDD 的建模思维，另外一方面是 DDD 与新的架构模型，洋葱、整洁、六边形更加契合。新的架构模型，也能更好的承载分布式微服务架构设计实现。

不过，所有东西的学习最后学的就是思想。工程框架结构的设计，也就是用合理的分层结构，有效的填充各项资源，提高资源的调配效率。这句你要细细的琢磨下。如果你面试能讲出这么一句，那么对工程的框架理解的非常深入的。

## 二、环境配置

- Jdk 1.8
- Maven 3.x - [Maven 教程](https://bugstack.cn/md/road-map/maven.html)
- IntellJ IDEA 社区版(免费) [IntelliJ  IDEA 教程](https://bugstack.cn/md/road-map/intellij-idea.html)
- Git - 安装后会配置到  IntellJ IDEA 这样才能向服务端推送或者拉取代码。[Git 教程 ](https://t.zsxq.com/19Rnk98M0) 学习后可以知道怎么拉取、提交和比对代码。

---

```xml
<mirrors>
    <mirror>
      <id>alimavenrepository</id>
      <name>aliyun maven repository</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
</mirrors>
```

- 如果你的 Maven 拉取 Jar 包速度很慢，可以在 Maven 的 conf 下 settings.xml 添加阿里云镜像。
- [https://t.zsxq.com/19Rnk98M0](https://t.zsxq.com/19Rnk98M0) 已提供配置好了 阿里云 Maven 镜像的 Maven 压缩包。