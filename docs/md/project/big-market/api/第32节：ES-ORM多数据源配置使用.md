---
title: 第32节：ES-ORM多数据源配置使用
pay: https://t.zsxq.com/l032Y
---

# 《大营销平台系统设计实现》 - 营销服务 第32节：ES-ORM多数据源配置使用

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★★★☆☆
- **本章重点**：为工程提供多数据源配置，可以同时支持操作 MySQL 和 ElasticSearch
- **课程视频**：[https://t.zsxq.com/q1RFi](https://t.zsxq.com/q1RFi)

**版权说明**：©本项目与星球签约合作，受[《中华人民共和国著作权法实施条例》](http://www.gov.cn/zhengce/2020-12/26/content_5573623.htm) 版权法保护，禁止任何理由和任何方式公开(public)源码、资料、视频等内容到Github、Gitee等，违反可追究进一步的法律行动。

## 一、本章诉求

首先是目前已经在 MySQL 分库分表使用的基础上，加入了 canal 组件通过 binlog 日志把分库分表的数据同步 ElasticSearch 文件系统，那么接下来我们就需要让应用程序可以从 ElasticSearch 查询数据。也就是如何处理一个应用中多数据源的使用，同时要简化使用。另外因为工程的基础设施层越来越多内容了，我们要一点小的结构调整。

## 二、业务流程

让一套应用支持多套数据源使用；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-45-01.png" width="550px">
</div>

- 分库分表是对C端用户的，所有的C端行为一定是有用户ID的。
- canal 同步 ElasticSearch 是为了给运营端做数据聚合查询的，一般这类的查询是不做核心业务的，因为同步是有时效性的。
