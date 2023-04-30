---
title: 第1节：push工程到仓库
pay: https://t.zsxq.com/0d7K7hJ0i
---

# 《ChatGPT 微服务应用体系构建》 - dev-ops 第1节：push工程到仓库

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★☆☆☆☆
- **本章重点**：通过本章节内容的学习，教会读者如何把一个在本地创建的工程，PUSH到远程仓库【gitcode.net】。在 github\gitee\gitcode 各类仓库中操作是通用的。
- **课程视频**：[https://t.zsxq.com/0dDDBIssX](https://t.zsxq.com/0dDDBIssX)

## 一、前言

在前面星球中的课程讲解中发现，很多球友伙伴还没有正式进入课程前，就被环境配置、代码提交、项目构建等基础操作问题卡住了。但这些问题本身只要看过一遍就会了，所有小傅哥准备基于这样一个课程，把类似这样的基础操作带着大家完成下。好让哪怕是在校学习的新人伙伴也能顺利进入学习。

类似这样的基本操作会包括；云服务器购买、SSH连接、安装容器环境、Nginx配置等。逐个讲解和视频操作每个内容，方便大家随时查阅自己需要的内容。

## 二、代码仓库

像一些小型互联网公司中，往往并不会自己建设一套 GitLab 仓库，大部分时候都是使用；Github、Gitee、Gitcode 这样的公开仓库来维护代码。但无论是否自建还是使用这些市面的产品，所有的基本操作都是一样的，所以会用一套，也就可以用其它的了。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-dev-ops-01-01.png?raw=true" width="850px">
</div>

- 所有的仓库，再右上角都可以找到创建项目的路径。