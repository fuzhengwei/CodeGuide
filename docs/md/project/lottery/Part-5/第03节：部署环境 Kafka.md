---
title: 第03节：部署环境 Kafka
pay: https://t.zsxq.com/jUbmeE2
---

# 第03节：部署环境 Kafka

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、运维日志

- 在 Docker 容器中安装和配置 Kafka 环境。关于 Kafka 镜像可以在 Docker 官网镜像中搜索 [https://hub.docker.com/r/wurstmeister/zookeeper](https://hub.docker.com/r/wurstmeister/zookeeper)
- 在 Kafka 后台添加抽奖系统需要的 Topic 主题，并在本地程序中进行测试

## 二、下载镜像

![](/images/article/project/lottery/Part-5/3-01.png)

```java
docker pull wurstmeister/kafka
docker pull wurstmeister/zookeeper
```

## 二、启动 Zookeeper

```java
docker run -d --name zookeeper -p 2181:2181 -t wurstmeister/zookeeper
```