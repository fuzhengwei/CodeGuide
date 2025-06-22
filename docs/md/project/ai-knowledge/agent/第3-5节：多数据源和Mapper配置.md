---
title: 【更】第3-5节：多数据源和Mapper配置
pay: https://t.zsxq.com/099am
---

# 《Ai Agent》第3-5节：多数据源和Mapper配置

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/eYn4R](https://t.zsxq.com/eYn4R)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

为应用程序配置pgvector（向量库）、mysql（业务库）两套数据源，同时基于库表，编写基础设施层 Mapper 操作。

对于数据库表的 Mapper 编写，是一种固定的结构化代码，可以通过 MyBatis 工具生成，也可以使用 AI 编码工具处理。不过对于新人学习来说，更建议在这个阶段，通过手动的方式进行配置编写，这样可以更熟悉库表的设计和字段的理解。尤其是报错后，还可以基于报错排查错误增加编程经验。

## 二、功能流程

如图，两个数据源的配置和使用；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-5-01.png" width="950px">
</div>

- 首先，为了让应用程序具备多数据源链接，则需要增加一个扩展的 DataSourceConfig 配置类，来自己实现数据源的加载。这部分会替代原本配置到 yml 文件中，由 Spring 加载数据源的过程。
- 之后，根据不同类型的数据源，注入到 AI 向量库使用场景和 MyBatis 业务使用场景中。这个过程类似于星球中 DB-Router 路由组件的课程。可以参考：[https://bugstack.cn/md/road-map/db-router.html](https://bugstack.cn/md/road-map/db-router.html)

