---
title: 第04节：部署环境 Mysql
pay: https://t.zsxq.com/jUbmeE2
---

# 第04节：部署环境 Mysql

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、运维日志

- 拉取 Mysql 镜像部署 Docker，使用本地工具链接 Mysql 创建库表并导入建表语句

## 二、查看可用的 MySQL 版本

访问 MySQL 镜像库地址：[https://hub.docker.com/_/mysql?tab=tags](https://hub.docker.com/_/mysql?tab=tags)

![](/images/article/project/lottery/Part-5/4-01.png)

**此外，我们还可以用 `docker search mysql` 命令来查看可用版本：**

![](/images/article/project/lottery/Part-5/4-02.png)

## 二、拉取 MySQL 镜像

```java
docker pull mysql:latest
```

![](/images/article/project/lottery/Part-5/4-03.png)