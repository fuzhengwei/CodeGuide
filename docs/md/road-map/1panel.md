---
title: 1Panel 运维面板
lock: need
---

# 1Panel 云服务器运维面板 | 操作云服务器，这套东西还适合小白的！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

在编程的这条路上，大家都经历过小白阶段。在小白阶段，即使是简简单单的环境安装，也都会遇到一堆堆的报错。所以最开始学习编程的时候，我们都希望找到那些简单、方便、好用的工具，让我们线上手✋🏻。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/springcloud-gateway-01.gif" width="200px"/>
</div>

**那云服务器操作有Easy工具吗？🤔**

小傅哥，在最早使用云服务器的时候，为了快速简单的搭建博客对外使用，选择过宝塔面板。这个东西虽然不大，但对于最开始云服务器操作能力和对编程的理解都比较偏弱的伙伴还是非常好用的。

但当大家开始使用 Linux + Docker，对一些列分布式环境继续安装和项目构建部署时，有些小白就会遇到一些各类小问题了。只有写好了 docker compose 和对应的文档，才能让小白快速完成安装。但有一些小白用到的新的服务安装时，自己不会写 docker compose 就会遇到新问题了。所以，今天我们来体验下一个新的云服务器运维面板，非常好用。

## 一、面板介绍

1Panel 是新一代的 Linux 服务器运维管理面板，它可以帮助你非常方便管理云服务器上的软件操作。`也不需要再配置 docker 代理了。`

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-1panel-01.png" width="950px"/>
</div>

- 用户可以通过 Web 图形界面轻松管理 Linux 服务器，实现主机监控、文件管理、数据库管理、容器管理等功能。
- 深度集成开源建站软件 WordPress 和 Halo，域名绑定、SSL 证书配置等一键搞定。
- 精选上架各类高质量的开源工具和应用软件，协助用户轻松安装并升级。
- 基于容器管理并部署应用，实现最小的漏洞暴露面，同时提供病毒防护、防火墙和日志审计等功能。
- 支持一键备份和恢复，用户可以将数据备份到各类云端存储介质，永不丢失。

> 好啦，介绍的还挺🐂皮的，咱们来体验下！

## 二、安装软件

### 1. 安装脚本

```java
curl -sSL https://resource.fit2cloud.com/1panel/package/quick_start.sh -o quick_start.sh && sh quick_start.sh
```

- Centos 一键安装脚本，复制到云服务器直接执行即可。
- 其他的云服务器系统安装脚本：https://1panel.cn/docs/installation/online_installation/
- 2c2g 云服务器，活动价，一年26元 [https://618.gaga.plus](https://618.gaga.plus)

### 2. 安装效果

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-1panel-02.png" width="950px"/>
</div>

- 安装过程中你可以顺序点回车即可，注意是否配置镜像加速，因为我配置过，所以没有配置。你可以选 Y 统一配置镜像加速。
- 安装完成后，会得到一个外网地址，端口是默认的 19166，同时给会你用户和密码。注意，需要在云服务器开放端口（安全组/防火墙）19166 另外账号密码可以在登录后修改。

## 三、软件功能

### 1. 查看容器

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-1panel-03.png" width="950px"/>
</div>

- 你的 Linux 安装 Docker 会直接被 1Panel 接管。在 1Panel 会看到你安装过的软件。
- 同时可以看到每个软件占用的 CPU、内存，这个还是非常好用的。

### 2. 安装软件

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-1panel-04.png" width="950px"/>
</div>

- 在 1Panel 的应用商店里，可以按照你需要的各类软件（它的覆盖度还是非常全的），包括常用的：Nginx、MySQL、MQ、Nacos、PHPAdmin等等，都是有的。
- 如图，是个 OneAPI 它是用于访问兼容访问各类其他 OpenAI 的软件的。比如，文心一言、通义千问、讯飞星火、智谱 ChatGLM、腾讯混元，都可以按照 ChatGPT 的格式进行使用啦。

>好啦，你可以美滋滋的去体验下啦！🌶

