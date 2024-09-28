---
title: 第2节：querys模块提供查询接口
pay: https://t.zsxq.com/YFckh
---

# 《大营销平台系统设计实现》 - 运营后台 第2节：querys模块提供查询接口

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★★☆☆☆
- **本章重点**：给 big-market 新增加 querys 模块，提供数据查询操作，并提供接口给前端使用。
- **课程视频**：[https://t.zsxq.com/ByrMx](https://t.zsxq.com/ByrMx)

**版权说明**：©本项目与星球签约合作，受[《中华人民共和国著作权法实施条例》](http://www.gov.cn/zhengce/2020-12/26/content_5573623.htm) 版权法保护，禁止任何理由和任何方式公开(public)源码、资料、视频等内容到Github、Gitee等，违反可追究进一步的法律行动。

## 一、本章诉求

在使用 DDD 做工程开发的时候，我们可以通过 domain 包，划分出一个个功能领域进行实现。这样强业务属性的逻辑，可以很好的拆分对应的边界，不至于像是 MVC 中让一堆的 Service 混乱。

但当我们遇到一些非强业务逻辑，只是对数据、缓存、ElasticSearch等做查询，给 ERP 运营系统提供数据时，就不时候全部都走一遍 domain 领域了，那样就显得非常重。所以这里我们要新增加一个 querys 模块，只做这类数据的查询操作。

在 big-market 提供好 querys 模块和相应的接口后，配置到前端页面使用。

## 二、后端实现

### 1. 添加 querys 模块

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market//big-market-49-01.png" width="850px">
</div>

- 在工程上右键，新增加一个 querys 模块，并对 pom 进行配置。这个 pom 的配置与 domain 中的配置类似。

### 2. 实现 querys 接口

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market//big-market-49-02.png" width="450px">
</div>

- 在 querys 中定义需要查询的的仓储接口，并由 domain 领域层实现接口。
- 这个实现的过程也是依赖倒置的方式实现。
- 另外注意，咱们是把 ElasticSerach 的查询，使用的是 x-pack-jdbc 方式，ElasticSerach 还有还有 SpringBoot 程序通过 `spring-boot-starter-data-elasticsearch` 提供查询的方式，也可以尝试试试。`面试或者做方案中，也有可能会问，你为什么选择 x-pack-jdbc 还有什么其他方式`