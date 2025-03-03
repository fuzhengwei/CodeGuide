---
title: 【更】第6节：Ollama RAG 知识库接口服务实现
pay: https://t.zsxq.com/GwNZp
---

# 《DeepSeek RAG 知识库》第6节：Ollama RAG 知识库接口服务实现

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

以上一节知识库的测试案例，将这部分功能以接口方式提供。包括；知识库的上传、选择和使用。

## 二、技术方案

知识库的上传和使用是明确的，但选择哪个知识库是需要把对应的知识库记录起来。这里我们选择 Redis 列表进行记录。如果是公司里大型的知识库，还需要使用 MySQL 数据库进行存储。

## 三、功能实现

### 1. 工程结构

### 2. 引入组件

```java
<dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson-spring-boot-starter</artifactId>
    <version>3.44.0</version>
</dependency>
```