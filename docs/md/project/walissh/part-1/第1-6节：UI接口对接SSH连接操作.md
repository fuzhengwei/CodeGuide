---
title: 第1-6节：UI接口对接SSH连接操作
pay: https://t.zsxq.com/VkZi2
---

# 《WaLiSSH - AI Shell 智能终端》第1-6节：UI接口对接SSH连接操作

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/WO3ll](https://t.zsxq.com/WO3ll)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

## 一、本章诉求

建立服务端 SSH 服务连接 API 接口，与前端对接。让前端可以通过界面操作的方式，通过服务端的 API 与云服务器建立连接。

## 二、流程设计

如图，从前端到后端的接口对接设计；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walissh/walissh-1-6-01.png" width="950px">
</div>

- 首先，底层后端，要提供  SshConnectionController 接口，封装领域服务，把操作 SSH 服务器的能力通过接口形式对外暴漏出去。这里的 HTTP 接口，包括了全面的 SSH 连接管理，创建连接、更新连接、删除连接、获取连接、连接列表等。注意，右侧的 AI Agent 服务暂时没有对接，后续逐步处理。
- 之后，由前端的 api/request.ts 与服务器的 HHTP 接口进行对接。往往前端的代码开发，会把一些列的 api 操作，都会汇总到一个类文件或者包文件下。这类的 ts 文件代码，也是面向对象的结构，与 java 的代码很类似。所以后端伙伴看这部分代码不会太吃力。