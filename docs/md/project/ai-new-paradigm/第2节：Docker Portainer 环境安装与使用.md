---
title: 第2节：Docker Portainer 环境安装与使用
pay: https://t.zsxq.com/bhKAz
---

# 《AI 新范式》第2节：Docker Portainer 环境安装与使用

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/bhKAz](https://t.zsxq.com/bhKAz)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

容器化部署 · 可视化管理，让运维像操作手机 App 一样简单。

## 一、本章诉求

在云服务器上安装 Docker 引擎和 Portainer 可视化管理面板，实现容器化部署和 Web 界面管理。

Docker 是云原生时代的标配技能，配合 Portainer 让容器管理像操作手机 App 一样简单！

## 二、Docker 环境安装

### 1. Docker 一键部署

AI Shell 自动检测系统环境，安装 Docker 引擎并完成基础配置。自动安装，环境检测。

### 2. Portainer 可视化

Web 界面管理容器、镜像、网络和卷，运维效率翻倍。Web 管理，可视化。

### 3. 环境即代码

容器化部署，一次构建到处运行，告别"在我机器上能跑"。容器化，可移植。

## 三、Docker + Portainer 实战流程

### 1. Docker 安装命令

```bash
# AI Shell 一键安装 Docker
帮我在服务器上安装 Docker

# 安装完成后验证
docker --version
docker compose version
```

> 💡 AI Shell 自动适配 CentOS / Ubuntu 不同系统的安装方式。

### 2. Portainer 部署

```bash
# 部署 Portainer 容器
docker run -d \
  --name portainer \
  -p 9000:9000 \
  -v /var/run/docker.sock:/var/run/docker.sock \
  portainer/portainer-ce
```

🔑 访问 `http://服务器IP:9000` 进入管理面板，首次使用设置管理员密码。

## 四、读者作业

- 简单作业：在云服务器上安装 Docker 和 Portainer，登录 Portainer 管理面板截图提交。
- 复杂作业：思考 Docker 容器与虚拟机的区别是什么？为什么容器化部署更适合微服务架构？
