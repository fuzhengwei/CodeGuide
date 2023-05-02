---
title: 第2节：Docker环境配置
pay: https://t.zsxq.com/0dgNDOINd
---

# 《ChatGPT 微服务应用体系构建》 - dev-ops 第2节：Docker环境配置

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★☆☆☆☆
- **本章重点**：通过本章节内容的学习，教会读者如何本地和云服务器安装Docker环境。
- **课程视频**：[https://t.zsxq.com/0dmmGxI39](https://t.zsxq.com/0dmmGxI39)

## 一、前言

整个课程中所涉及的应用部署都会放到 Docker 环境中启动，所以这里先把 Docker 的配置给球友读者分享下。其实像 Docker 的安装、配置、使用，在网上也可以检索到资料，但鉴于有些伙伴找的资料参差不齐，还容易耽误时间，所以小傅哥这里也统一给大家分享。

Docker 是一个开源的应用容器引擎，让开发者可以打包他们的应用以及依赖包到一个可移植的镜像中，然后发布到任何流行的 Mac、Linux或Windows操作系统的机器上，也可以实现虚拟化。容器是完全使用沙箱机制，相互之间不会有任何接口。总之它加快构建、共享和运行现代应用程序的速度。

- 官网：[https://www.docker.com](https://www.docker.com/) - Mac、Windows、Linux

## 二、本地安装

优先建议大家本地安装一个 Docker 环境，方便测试验证。毕竟开发阶段我们暂时也不需要云服务器。

Docker 的安装非常简单，你只需要选择适合你的机器版本。Mac、Windows、Linux，直接一步步安装即可。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-dev-ops-02-01.png?raw=true" width="850px">
</div>

安装完成后，按照自己的机器配置一个适合的空间占用。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-dev-ops-02-02.png?raw=true" width="850px">
</div>

- 安装后，可以在 Docker 的操作界面拉取镜像、推送镜像、部署程序等，后面会在课程的使用中进行体现。
- 安装后，还可以在黑窗口终端、IntelliJ IDEA Terminal 中使用 Docker 命令，操作 Docker，如服务部署。