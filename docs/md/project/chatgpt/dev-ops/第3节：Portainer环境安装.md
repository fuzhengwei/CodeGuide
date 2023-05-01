---
title: 第3节：Portainer环境配置
pay: https://t.zsxq.com/0dYyw6VNQ
---

# 《ChatGPT 微服务应用体系构建》 - dev-ops 第3节：Portainer环境配置

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★☆☆☆☆
- **本章重点**：通过本章节内容的学习，教会读者如何本地和云服务器安装Portainer环境。
- **课程视频**：[https://t.zsxq.com/0dmmGxI39](https://t.zsxq.com/0dmmGxI39)

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