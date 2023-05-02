---
title: 第1节：API工程搭建和简单访问认证
pay: https://t.zsxq.com/0dcdSvckQ
---

# 《ChatGPT 微服务应用体系构建》 - chagpt-api 第1节：API工程搭建和简单访问认证

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★★☆☆☆
- **本章重点**：搭建ChatGPT-API工程，作为统一的后端访问入口，提供API能力。这些能力后随着章节的推进，不断地叠加，如；访问验证、OpenAI会话、公众号、企业微信等。
- **课程视频**：[https://t.zsxq.com/0dzm349us](https://t.zsxq.com/0dzm349us)

## 一、本章诉求

一般我们研发人员在需要完成一个完整需求前，最好是先梳理需求，并把用于完成需求目标的各个功能节点进行单个验证，以确保方案的可行性。有了这些基本功能模块的验证后，就可以逐步再把各个模块像乐高积木一样搭建起来，搭建的过程就是架构和设计模式的运用。

那么我们本章的目标就是在 Nginx 访问接口时，做一些权限校验，只有校验通过才能访问接口，否则就直接返回失败。有了这样的控制，你是不是能想象到，你在一些网站购买的一个月有效期的服务，过期就不能使用的场景。

那么在本章节小傅哥会带着大家先搭建一个简单的 SpringBoot 工程，并在工程中提供用于与 Nginx 的 auth_request 模块做验证处理的访问接口。在本章节会涉及到 Maven 工程的创建、代码提交、启动发布、Nginx  auth 配置等内容。

## 二、流程设计

整个流程为；以用户视角访问API开始，进入 Nginx 的 auth 认证模块，调用 SpringBoot 提供的认证服务。根据认证结果调用重定向到对应的 API 接口或者 404 页面。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-api-01-01.png?raw=true" width="750px">
</div>

由于 OpenAI 或者本身自己训练的一套服务，都会有服务器成本。所以基于这样一个模型结构，后续可以通过用户购买 Token 的时效性进行成本回收。这也是其中一种商业变现的思路。