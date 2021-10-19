---
layout: post
category: itstack-demo-any
title: windows环境下安装elasticsearch6.2.2
tagline: by 付政委
tag: [ddd,itstack-demo-any]
excerpt: 在windows环境下安装Elasticsearch 6.2.2
lock: need
---

## 前言介绍
在windows环境下安装Elasticsearch 6.2.2

>ElasticSearch是一个基于Lucene的搜索服务器。它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java语言开发的，并作为Apache许可条款下的开放源码发布，是一种流行的企业级搜索引擎。ElasticSearch用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。官方客户端在Java、.NET（C#）、PHP、Python、Apache Groovy、Ruby和许多其他语言中都是可用的。根据DB-Engines的排名显示，Elasticsearch是最受欢迎的企业搜索引擎，其次是Apache Solr，也是基于Lucene。

## 安装环境
**1、Elasticsearch 6.2.2 下载** [https://www.elastic.co/cn/downloads/past-releases/elasticsearch-6-2-2](https://www.elastic.co/cn/downloads/past-releases/elasticsearch-6-2-2)
![](https://bugstack.cn/assets/images/pic-content/2019/08/elasticsearch6.6.2.png)

**2、JDK 1.8 （jdk1.7及以下不能支持Elasticsearch）下载** [https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
![](https://bugstack.cn/assets/images/pic-content/2019/08/es-3.png)

**3、安装cmd进入elasticsearch-6.2.2\bin，执行elasticsearch.bat**
![](https://bugstack.cn/assets/images/pic-content/2019/08/es-1.png)

**4、启动完成后打开http://localhost:9200 ｛默认端口9200｝**
![](https://bugstack.cn/assets/images/pic-content/2019/08/es-2.png)

------------