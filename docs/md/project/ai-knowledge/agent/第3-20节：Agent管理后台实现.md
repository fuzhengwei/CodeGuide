---
title: 【更】第3-20节：Agent管理后台实现
pay: https://t.zsxq.com/5Sdfr
---

# 《Ai Agent》第3-20节：Agent管理后台实现

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/z0jRV](https://t.zsxq.com/z0jRV)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

设计实现一套 Ai Agent 管理后台页面，允许用户通过管理后台实现 Ai Agent 的资源配置和拖拉拽方式维护 Ai Agent 智能体。
第
本章节没有复杂的逻辑实现，都是 CRUD 操作，把原本在数据库中手动处理的过程，以更符合运营使用方式提供用户使用。所以，本节你只需要了解、看懂，可以跟着继续迭代你想要的内容即可。

## 二、实现效果

这是一套完整的 Ai Agent 智能体管理后台，包括了所有资源的配置（model、client、mcp、advisor、prompt），以及拖拉拽编排的方式完成 Ai Agent 智能体的构建。

以下的截图内容和使用，会在本节课程的视频里演示。也包括如何运行使用。

### 1. 登录界面

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-20-01.png" width="950px">
</div>

- 这一部分在数据库表增加了 admin_user 表，有配置登录账号和密码，可以简单做校验。

### 2. 管理界面

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-20-02.png" width="950px">
</div>

- 管理后台目前提供了，代理管理（拖拉拽编排方式配置智能体），资源管理（model、client、mcp、advisor、prompt）
- 数据分析、系统设置，是样例，你可以继续扩展你所需要的内容。

### 3. 代理管理

#### 3.1 代理列表

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-20-03.png" width="950px">
</div>

- 这里的代理列表，就是通过拖拉拽配置的智能体。可以点击【查看】看到明细，也可以【新建】，还可以删除。
- 点击【加载】则是调用服务端，把数据加载到 Spring 容器，之后就可以使用了。

#### 3.2 代理配置

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-20-04.png" width="950px">
</div>

- 当你点击一个代理配置，则会展示出拖拉拽的数据到页面上。这部分会从数据库读取，之后展示出来，全部可视化。
- 如果你点击了Save则会做出一份新的，之后对于旧的，你可以自己手动删除。

### 4. 资源管理

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-20-05.png" width="950px">
</div>

- 资源管理下，是配置一个智能体所需的各项资源信息，你可以在这里进行维护。如，MCP 工具管理。

### 5. 页面使用

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-20-06.png" width="950px">
</div>

- 配置后的智能体，可以在智能体选择里进行获取使用。之后进行提问。
- 效果还不错，这里小傅哥验证了配置的智能体进行提问。