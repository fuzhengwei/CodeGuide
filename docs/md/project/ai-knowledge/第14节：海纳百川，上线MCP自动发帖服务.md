---
title: 【更】第14节，海纳百川，上线MCP自动发帖服务
pay: https://t.zsxq.com/G5mog
---

# 《DeepSeek RAG&MCP 增强检索知识库系统》第14节，海纳百川，上线MCP自动发帖服务

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/W0u4s](https://t.zsxq.com/W0u4s)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

以 Jar 包的形式，打包 MCP 自动发帖服务，并以 stdio 方式引入到项目工程。再通过定时任务触达定时自动发帖。

## 二、功能流程

如图，以 stdio 方式，构建服务打包上线；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-14-01.png" width="750px">
</div>

- 首先，将 mcp-server-csdn 以 maven 命令方式打一个 jar。IntelliJ IDEA 也可以直接通过界面操作打包 Jar（视频里会演示）
- 之后，将 ai-mcp-knowledge 以 maven 命令方式打一个 jar，并执行 Dockerfile 构建出可部署的镜像。注意，这里额外增加一个阿里云 Docker 镜像仓库，为的是让他提供搭理，方便我们云服务器部署的时候，可以快速拉取下来镜像。此外，如果说你以云服务器当做本机一样使用，在云服务器配置好 maven、git、java jdk 17，那么就可以在云服务器直接构建镜像，也就不需要额外拉取了。（`这部分内容在课程入口-编程环境-云服务器操作中有讲解`）
-  最后，通过 docker-compose 脚本配置上线部署。
