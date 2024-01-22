---
title: Portainer
lock: need
---

# Portainer 环境配置

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=924100393&bvid=BV1ET4y1b7Ez&cid=1413251533&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

- 官网：[https://www.portainer.io/](https://www.portainer.io/)
- 介绍：在任何数据中心、云、网络边缘或 IIOT 设备的 Kubernetes、Docker、Swarm 和 Nomad 上，在几分钟内部署、配置、故障排除和保护容器。 

## 一、基础安装

### 1. 拉取最新的 Portainer

```java
[root@CodeGuide portainer]# docker pull portainer/portainer
Using default tag: latest
latest: Pulling from portainer/portainer
94cfa856b2b1: Pull complete 
49d59ee0881a: Pull complete 
a2300fd28637: Pull complete 
Digest: sha256:fb45b43738646048a0a0cc74fcee2865b69efde857e710126084ee5de9be0f3f
Status: Downloaded newer image for portainer/portainer:latest
docker.io/portainer/portainer:latest
```

- docker pull portainer/portainer
- 拉取 portainer

### 2. 安装和启动

```java
[root@CodeGuide]# docker run -d --restart=always --name portainer -p 9000:9000 -v /var/run/docker.sock:/var/run/docker.sock portainer/portainer
```

### 3. 访问 Portainer

- 地址：[http://39.96.*.*:9000/](#)
- 操作：登录后设置你的用户名和密码，并设置本地Docker即可，设置完成后，如下

<div align="center">
	<img src="https://bugstack.cn/images/article/devops/dev-ops-portainer-230418-01.png?raw=true" width="950px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/article/devops/dev-ops-portainer-230418-02.png?raw=true" width="950px"/>
</div>

## 二、链接服务

地址：[http://180.76.119.142:9000/#!/wizard/endpoints/create?envType=dockerStandalone](http://180.76.119.142:9000/#!/wizard/endpoints/create?envType=dockerStandalone)

<div align="center">
	<img src="https://bugstack.cn/images/article/devops/dev-ops-portainer-230418-01.png?raw=true" width="950px"/>
</div>

```shell script
docker run -d \
  -p 9001:9001 \
  --name portainer_agent \
  --restart=always \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v /var/lib/docker/volumes:/var/lib/docker/volumes \
  portainer/agent:2.16.2
```

