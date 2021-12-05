---
title: 第02节：部署环境 Redis
pay: https://t.zsxq.com/fAYNJy3
---

# 第02节：部署环境 Redis

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、运维日志

- 从[官网](http://www.redis.cn/download.html)下载 Redis 安装包，主要为了获取 [redis.conf](https://codechina.csdn.net/KnowledgePlanet/Lottery/-/blob/master/doc/assets/redis/redis.conf) 配置文件。*因为需要按照模板配置文件，修改一些必备的参数，才能让启动后的 Redis 被远程链接*
- 下载 SFTP 软件 Transmit，链接远程服务器创建 redis 配置文件夹，并上传 [redis.conf](https://codechina.csdn.net/KnowledgePlanet/Lottery/-/blob/master/doc/assets/redis/redis.conf)
- 在 Docker 容器中，通过命令安装 Redis，因为这样可以把 Redis 配置一并加入并启动，否则默认情况下为不加载配置，这样即使安装完成也不能被本地访问到。*注意：redis.conf 配置了 Redis 的链接密码 1234 你可以进行更改*
- 安装并启动 Redis 后，可以通过 Portainer 中的容器查看 Redis 安装运行情况
- 下载 RDM Redis 客户端软件，进行链接 Redis 测试。

## 二、选择 Redis 镜像

![](/images/article/project/lottery/Part-5/2-01.png)

- 网址：[https://hub.docker.com/_/redis?tab=tags](https://hub.docker.com/_/redis?tab=tags)
- 选择最新的镜像文件：`docker pull redis:latest`

## 三、下载Redis的配置文件

![](/images/article/project/lottery/Part-5/2-02.png)

- 下载后解压，你会看到一个 redis.conf 的文件，接下来需要对文件进行修改；*如果你自己配置的有问题，也可以下载我配置好的：[redis.conf](https://codechina.csdn.net/KnowledgePlanet/Lottery/-/blob/master/doc/assets/redis/redis.conf)*
     - 【必须】`bind 127.0.0.1` 把`#`号注释掉这部分，使redis可以外部访问
     - 【必须】`daemonize` 修改为`no` 用守护线程的方式启动
     - 【必须】`requirepass` 设置你的Redis链接密码
     - 【必须】`appendonly` 修改为`yes`，redis持久化　　默认是no
     - 【可选】`tcp-keepalive` 300 防止出现远程主机强迫关闭了一个现有的连接的错误 默认是300