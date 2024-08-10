---
title: 第33节：xxl-job分布式任务调度
pay: https://t.zsxq.com/nOA6K
---

# 《大营销平台系统设计实现》 - 营销服务 第33节：xxl-job分布式任务调度

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★★★☆☆
- **本章重点**：为工程提供多数据源配置，可以同时支持操作 MySQL 和 ElasticSearch
- **课程视频**：[https://t.zsxq.com/N2eo4](https://t.zsxq.com/N2eo4)

**版权说明**：©本项目与星球签约合作，受[《中华人民共和国著作权法实施条例》](http://www.gov.cn/zhengce/2020-12/26/content_5573623.htm) 版权法保护，禁止任何理由和任何方式公开(public)源码、资料、视频等内容到Github、Gitee等，违反可追究进一步的法律行动。

## 零、优化调整

- fix-240807-xfg-clear-queue-by-sku 分支增加了指定sku的使用，而不是使用一个队列。
- fix-240608-xfg-decr-zero 优化扣减中0的对比。
- feat-240809-xfg-zookeeper-enable 增加可对 Zookeeper 关闭的处理，在 yml 中配置（zookeeper.sdk.config.enable）。便于大家在测试时快速启动。
- dubbo yml 配置，修改为 N/A 屏蔽掉 nacos 注册，便于本地快速测试。

## 一、本章诉求

增加 xxl-job 分布式任务调度服务，处理大营销中；`发送MQ消息任务队列`、`更新活动sku库存任务`、`更新奖品库存任务`定时任务。同时因为整个大营销是分布式部署，一套 big-market 会被多个应用实例一起部署，那么就会有多个实例上相同的一个任务要执行，这个时候需要增加抢占式锁，避免造成重复执行。重复执行可能导致无效的扫库或者重复发送MQ消息。

## 二、业务流程

通过xxl-job管理分布式应用任务；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-46-01.png" width="650px">
</div>

XXL-JOB 是一个轻量级分布式任务调度平台，其核心设计目标是开发迅速、学习简单、轻量级、易扩展。现已开放源代码并接入多家公司线上产品线，开箱即用。

- big-market 01、big-market 02、big-market 03，举例分布式部署，也可以是更多的部署实例。
- 每个应用实例都是相同的任务，这些任务增加了 redis 分布式锁抢占，避免所有任务都被同时执行。加了锁以后，同一时间只能一个实例上执行任务。
- 但不能为了只有一个执行而部署一套，部署多套的用途是为了互备，如果一个挂了还有其他的任务可以执行。这个就是分布式架构高可用的设计思路。

>**对 xxl-job 学习**：[https://bugstack.cn/md/road-map/quartz.html](https://bugstack.cn/md/road-map/quartz.html) - 小白伙伴可以看下这套内容补充学习任务调度，包括；环境安装、执行器管理、任务配置等。

