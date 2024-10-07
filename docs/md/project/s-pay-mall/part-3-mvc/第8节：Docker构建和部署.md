---
title: 第3-8节：Docker构建和部署
pay: https://t.zsxq.com/3X9GA
---

# 第3-8节：Docker构建和部署

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

通过 Docker 给工程配置 Dockerfile、build.sh 文件对项目进行构建和部署，前端应用采用 Nginx 进行部署。

## 二、Docker 是什么？

Docker 你可以把它当做一个小型的轻量的虚拟机（虚拟的电脑），它可以帮你屏蔽各类软件环境安装时候的复杂性，你只需要一段脚本，即可完成 mysql、redis、nginx 等等各类你能想到的软件的部署和卸载。—— 不知道多少伙伴自己电脑装完 MySQL 就不会卸载了！

<div align="center">
    <img src="https://bugstack.cn/images/article/project/s-pay-mall/s-pay-mall-mvc-3-8-01.png" width="850px">
</div>

- 官网：[https://www.docker.com/](https://www.docker.com/) - 本地电脑直接安装即可。Windows 电脑因为不是 linux 所以需要开启 wsl2（百度/星球搜索有配置教程）
- 云教程：[https://bugstack.cn/md/road-map/docker.html](https://bugstack.cn/md/road-map/docker.html) - 也可以选择云服务器安装。