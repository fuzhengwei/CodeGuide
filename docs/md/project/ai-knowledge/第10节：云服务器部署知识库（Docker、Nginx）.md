---
title: 【更】第10节：云服务器部署知识库（Docker、Nginx）
pay: https://t.zsxq.com/YUG46
---

# 《DeepSeek RAG 知识库》第10节：云服务器部署知识库（Docker、Nginx）

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/cWfL6](https://t.zsxq.com/cWfL6)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

部署项目到公网云服务器，让小伙伴们学习如何打包部署上线和Linux、Docker、Nginx的操作使用。

## 二、部署过程

如图，为本次的部署过程；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-10-01.png" width="800px">
</div>

- 首先，购买云服务器，之后搭建云环境。[http://618.gaga.plus/](http://618.gaga.plus/) 需要2c4g部署。
- 之后，我们在本地构建软件镜像，之后推送到 Docker Hub，完成后再把 dev-ops 的脚本通过 sftp 上传到云服务器，执行安装。
- 最后，记得部署完成后检查各个软件运行日志，以及开通下相关的服务端口。
