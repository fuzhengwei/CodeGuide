---
title: 第02节：【作业】vue H5 大转盘抽奖
pay: https://t.zsxq.com/EuN76UV
---

# 第02节：【作业】vue H5 大转盘抽奖

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

- VUE：[lottery-front](https://gitcode.net/KnowledgePlanet/lottery-front)
- 描述：本章节是一个作业章节，我会带着你基于vue搭建初始工程，加入抽奖模块以及一个案例接口。之后需要你在这个基础上完善整个抽奖流程的调用和展示，这样你就可以把整个抽奖过程串联起来了。

## 一、开发日志

- 基于 IDEA 创建 vue 工程，并加入新的抽检模块
- 通过 Lottery-API 开发接口，给抽奖 vue 页面调用，这里需要配置跨域 `@CrossOrigin("*")`

## 二、vue 工程

我们把 vue 工程写到 [lottery-front](https://gitcode.net/KnowledgePlanet/lottery-front) 你在学习的过程中除了参考这个工程以外，也可以自己创建，创建过程如下图：

![](https://gitcode.net/KnowledgePlanet/Lottery/-/raw/master/doc/assets/img/Part-4/2-01.png)

- 点击下一步即可，它会帮你初始化完成工程。
- 一般开发 vue 的习惯使用 vscode 你也可以尝试下

**1. 安装插件**

```java
# npm 安装
npm install @lucky-canvas/vue@latest

# 或者 yarn 安装
yarn add @lucky-canvas/vue@latest
```

**2. 然后找到 main.js 引入插件并 use**

```java
// 完整加载
import VueLuckyCanvas from '@lucky-canvas/vue'
Vue.use(VueLuckyCanvas)
```

详细使用文档：[https://100px.net/usage/vue.html](https://100px.net/usage/vue.html)

![](https://gitcode.net/KnowledgePlanet/Lottery/-/raw/master/doc/assets/img/Part-4/2-02.png)