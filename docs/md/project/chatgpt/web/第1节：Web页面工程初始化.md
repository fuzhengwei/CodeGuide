---
title: 第1节：Web页面工程初始化
pay: https://t.zsxq.com/0eD8hVmSU
---

# 《ChatGPT 微服务应用体系构建》 - chatgpt-web 第1节：Web页面工程初始化

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★☆☆☆☆
- **本章重点**：通过 WebStorm 开发工具，创建 Next.js TypeScript Web 工程。简单介绍工程框架和代码模块职责，以及做简单的框体内容代码开发。
- **课程视频**：[https://t.zsxq.com/0eceIlPgs](https://t.zsxq.com/0eceIlPgs)

## 一、本章诉求

对于后端开发人员来说，在进入职场以后，因为工作职责的划分，其实就很少做前端的开发内容了。而这些年前端的编程语言也发生了特别多的变化，早已经不是 JQuery 那个年代的代码风格，而是都以面向对象的方式进行开发，再通过编译构建的生成 HTML 代码的方式进行实现。

而本章作为 ChatGPT 课程的一部分，是会涉及到前端的页面开发。这对很多后端开发伙伴来说，可能会有一些压力。所以如果你是为了增加这方面技能，或者是为了以后自己做一些东西的时候可以写下页面，那么可以跟着小傅哥一起学习。当然小傅哥在写前端的东西上也只是个`二把刀`，但好在我能写的东西，也基本都够用。另外如果你只是为了工作需求，其实不学习这部分内容也没关系。你可以直接使用一些开源的页面框架，并指导在哪些地方添加、修改、补充即可。

## 二、环境诉求

- Node.js v16.14.2 - [https://nodejs.org/zh-cn](https://nodejs.org/zh-cn) - 下载安装即可
- WebStorm 2023.1 - `因为自带了开发前端的工具，所以非常好用。`
- 源地址设置；`npm config set registry https://registry.npm.taobao.org` - 安装好环境后，设置下源地址。否则在后面构建项目，下载文件时候会卡出xiang
- TypeScript：[https://www.runoob.com/typescript/ts-tutorial.html](https://www.runoob.com/typescript/ts-tutorial.html) - 课程资料，简单的菜鸟入门教程。学习之后也能看懂 TypeScript 代码。所有的面向对象语言代码，基本是通用的。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-web-01-01.png?raw=true" width="650px">
</div>

**注意**：如果你自己没有太多的 HTML、JS、Div+CSS 的编写经验，也可以直接使用小傅哥提供的非常简单的页面，只要你简单改动即可对后端接口调用，完成的 ChatGPT 的响应。源码：[https://gitcode.net/KnowledgePlanet/chatgpt/chatgpt-web/-/tree/230527-xfg-init/docs/html](https://gitcode.net/KnowledgePlanet/chatgpt/chatgpt-web/-/tree/230527-xfg-init/docs/html)

## 三、工程结构

整个工程由 WebStorm 的 Next.js 模板配置创建，创建后会在控制台回车选择执行安装即可。- `注意选择的是含有 src 文件夹的工程结构`

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-web-01-02.png?raw=true" width="650px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-web-01-03.png?raw=true" width="650px">
</div>

- 创建完成后工程会自动的构建，构建完成除了上图中绿色新增部分外，都是工程默认提供好的。
- package.json 是一个配置类，可以用于启动工程测试，以及这里会把工程所需的模块加载进来。
- page.tsx、layout.tsx 是这个工程的入口页面，指定了首页和要加载的页面。
- home.tsx 是小傅哥新增加的一个窗体页面，其他几个绿色文件是为了配合 home.tsx 而存在的 css 文件。