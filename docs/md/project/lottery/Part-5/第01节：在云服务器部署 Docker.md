---
title: 第01节：在云服务器部署 Docker
pay: https://t.zsxq.com/08c55XltC
---

# 第01节：在云服务器部署 Docker

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、运维日志

- 在云服务器上部署 Docker，并安装 Portainer 运维面板以及汉化。汉化包已放到 [Lotter/doc/asserts/Portainer-CN](https://gitcode.net/KnowledgePlanet/Lottery/-/tree/master/doc/assets/Portainer-CN)
- 服务器系统 CentOS 8.x、Docker 20.10.11
- 如果你的云服务器已经安装其他系统，可以停机后更换系统即可，其实这个时候你还可以选择 Docker 镜像，也就是默认帮你安装好了 Docker 

## 二、手动安装 Docker

Docker 是一个开源的应用容器引擎，让开发者可以打包他们的应用以及依赖包到一个可移植的镜像中，然后发布到任何流行的 Linux或Windows操作系统的机器上，也可以实现虚拟化。容器是完全使用沙箱机制，相互之间不会有任何接口。

- 官网：[https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop)

### 1. 查看系统的内核版本

```java
[root@CodeGuide ~]# uname -r
4.18.0-80.11.2.el8_0.x86_64
```

- `uname -r`
- x86 64位系统，如果是32位是不能安装 docker 的