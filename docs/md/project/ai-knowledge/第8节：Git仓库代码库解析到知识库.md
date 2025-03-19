---
title: 【更】第8节：Git仓库代码库解析到知识库
pay: https://t.zsxq.com/ByovV
---

# 《DeepSeek RAG 知识库》第8节：Git仓库代码库解析到知识库

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/EwqZH](https://t.zsxq.com/EwqZH)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

对知识库的解析进行扩展，增加Git仓库解析。用户填写Git仓库地址和账密，即可拉取代码并上传到知识库，之后就可以基于这套代码进行使用啦。

## 二、技术方案

引入 JGit 操作库到工程中，用于执行 Git 命令拉取代码仓库。之后对代码库文件进行遍历，依次解析分割上传到向量库中。

## 三、功能实现

### 1. 工程结构

### 2. 引入组件

```java
<dependency>
    <groupId>org.eclipse.jgit</groupId>
    <artifactId>org.eclipse.jgit</artifactId>
    <version>5.13.0.202109080827-r</version>
</dependency>
```
