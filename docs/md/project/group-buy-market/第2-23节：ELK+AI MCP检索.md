---
title: 【更】第2-23节：ELK + AI MCP 检索
pay: https://t.zsxq.com/AV4Jp
---

# 《拼团交易平台系统》第2-23节：ELK + AI MCP 检索

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/6BHXt](https://t.zsxq.com/6BHXt)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

为拼团项目增加 ELK 分布式日志采集和查询系统，以及通过 AI MCP 的方式，检索系统运行日志和数据分析。

系统的运行日志，是排查系统问题的重要手段，尤其是分布式复杂系统，一次行为请求会执行多个接口查询。这些接口查询会被分布式系统承接，到不同的服务节点上执行。因此分布式日志就非常有用，可以串联出在哪个系统上执行的，并拿到全部日志进行分析。

## 二、框架介绍

Elastic Stack 技术栈，别是 `Elasticsearch`、`Logstash`、`Kibana` 组成，简称 ELK 是一套针对日志数据做解决方案的框架。它使您能够聚合来自所有系统和应用程序的日志，分析这些日志，并创建可视化来进行应用程序和基础设施监控、更快的故障排除、安全分析等。

- E = Elasticsearch：Elasticsearch 是在 Apache Lucene 上构建的分布式搜索和分析引擎。对各种语言、高性能和无架构 JSON 文档的支持使 Elasticsearch 成为各种日志分析和搜索使用案例的理想选择。
- L = Logstash：Logstash 是一个开源数据摄取工具，允许您从各种来源收集数据，转换数据，并将数据发送到您希望的目标。通过预构建的筛选器和对 200 多种插件的支持，Logstash 使用户能够轻松摄取数据，无论数据源或类型如何。
- K = Kibana：Kibana 是一种数据可视化和挖掘工具，可以用于日志和时间序列分析、应用程序监控和运营智能使用案例。它提供了强大且易用的功能，例如直方图、线形图、饼图、热图和内置的地理空间支持。此外，付费的 Kibana 还有 x-pack-jdbc 可以使用，让你就像使用 MyBatis 操作 MySQL 数据库一样操作 Elasticsearch 数据。

综上，3个组件的组合使用。由 Logstash 将摄取、转换数据并将其发送到 Elasticsearch 为摄取的数据编制索引，并且分析和搜索这些数据。最终 Kibana 会将分析结果可视化。也就是你可以在 Kibana 上实时看到系统的运行日志。
