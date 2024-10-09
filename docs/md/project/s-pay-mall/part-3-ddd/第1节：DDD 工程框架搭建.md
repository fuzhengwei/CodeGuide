---
title: 第3-1节：DDD 工程框架搭建 + 基础配置 + Git 使用
pay: https://t.zsxq.com/bjFkO
---

# 《小型支付商城系统》第3-1节：DDD 工程框架搭建 + 基础配置 + Git 使用

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

手把手的，分别通过手动创建和脚手架创建 DDD 工程结构。以及完成基础配置和 Git 使用。

从本节进入 DDD 部分的学习开始，会对照 MVC 进行差异化的对比讲解。可能有时候文字没法直接关的表达两个工程的使用差异，可以先看视频在看文档和代码，这样会更好的理解 MVC  到 DDD 的工程设计实现。

首先 DDD 是一种软件设计方法，它的规范标准和思想指导着软件设计实现，而为了更好的承接这样一套思想，有了新的架构结构。也就是我们常提到的；整洁架构、洋葱架构、六边形架构、菱形架构，这些架构的设计，合理的划分出了不同的分层结构，用于承接各项组件、服务、功能领域。在我们做这部分 DDD 实现时，会不断的体现出这些内容。

## 二、环境配置

- Jdk 1.8
- Maven 3.x - [Maven 教程](https://bugstack.cn/md/road-map/maven.html)
- IntellJ IDEA 社区版(免费) [IntelliJ  IDEA 教程](https://bugstack.cn/md/road-map/intellij-idea.html) 推荐`2023`及以上版本，使用起来更方便。
- Git - 安装后会配置到  IntellJ IDEA 这样才能向服务端推送或者拉取代码。[Git 教程 ](https://t.zsxq.com/19Rnk98M0) 学习后可以知道怎么拉取、提交和比对代码。
- 在线版脚手架：[https://bugstack.cn/md/road-map/ddd-archetype-maven.html](https://bugstack.cn/md/road-map/ddd-archetype-maven.html) - 直接配置使用即可。

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