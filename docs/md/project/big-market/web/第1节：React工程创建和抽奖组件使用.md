---
title: 第1节：React工程创建和抽奖组件使用
pay: https://t.zsxq.com/17Jfuonzc
---

# 《大营销平台系统设计实现》 - 前端页面 第1节：React工程创建和抽奖组件使用

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★★☆☆☆
- **本章重点**：通过 React 搭建前端抽奖页面工程，涵盖大转盘、九宫格两种样式。
- **课程视频**：[https://t.zsxq.com/177fNsBdm](https://t.zsxq.com/177fNsBdm)

**版权说明**：©本项目与星球签约合作，受[《中华人民共和国著作权法实施条例》](http://www.gov.cn/zhengce/2020-12/26/content_5573623.htm) 版权法保护，禁止任何理由和任何方式公开(public)源码、资料、视频等内容到Github、Gitee等，违反可追究进一步的法律行动。

## 一、本章诉求

大营销平台是一个后端Java系统，核心的重要业务逻辑实现都会在后端代码实现上。但为了保证项目整体的完整性，让读者伙伴知道这套服务端的逻辑是怎么体现给用户的，也会提供出对应使用的前端页面。

小傅哥这里会采用 React 技术开发前端页面，对于已经具备一些 Java 编程经验的小伙伴来说，学 React 会比 vue 更加容易，因为 React 更像 Java 那种面向对象的编程方式，也不会额外的定义出非标准的语法结构。你只需要花费48小时阅读官网中文文档，即可理解 React 代码的含义。文档地址：[https://zh-hans.react.dev/learn](https://zh-hans.react.dev/learn)

## 二、开发环境

- `Node.js v18+` - [https://nodejs.org/en/download/](https://nodejs.org/en/download/)
- WebStorm 2023.1 - 对于熟悉 IntelliJ IDEA 开发的伙伴，使用这款工具会非常容易。
- 镜像源设置【提高下载速度】；官网镜像源为 `https://registry.npmjs.org` 需要切换为镜像源 `npm config set registry https://registry.npmmirror.com/` 【查看源 `npm config get registry`】
- 课程资料：—— 这样两份资料基本就够入门了😄
  - 官网中文资料 [https://zh-hans.react.dev/learn](https://zh-hans.react.dev/learn)
  - 菜鸟教程 [https://www.runoob.com/typescript/ts-tutorial.html](https://www.runoob.com/typescript/ts-tutorial.html)