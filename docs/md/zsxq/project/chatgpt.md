---
title: OpenAi 大模型应用服务体系构建
lock: no
---

# OpenAi 大模型应用服务体系构建 - API-SDK、鉴权、公众号、微信支付

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>星球：[https://t.zsxq.com/0d3o5FKvc](https://t.zsxq.com/0d3o5FKvc) - 课程入口

>沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=996017673&bvid=BV1xs4y1Q7C4&cid=1128307317&page=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

## 一、产品形态

这趟车🚌，本身的核心是关于**微服务应用体系的构建**，通过讲解配置`Docker`、`Nginx`、`SSL`等环境以及开发出`鉴权`、`认证`、`微信公众号`、`企业微信`、`支付宝交易`等模块的方式，完善体系的物料服务。而 ChatGPT 只是其中的一种产品形态而已，这种产品形态通过 API 的方式与具体的物料服务模块解耦。这样做的方式是因为基础的物料`【物料指SDK和服务】`并不会频繁变化，而离业务最近的 API 会随业务变动发生较多的改动。所以这样的应用架构方式，在互联网大厂中也是非常常见和常用的。

这些东西的价值在于架构思维，而我也希望授人以渔，教会大家一些根本的东西，而不是永远的在CV+CRUD。有了这样的学习，学习的就不只是这样一个项目，而是可以把这个项目中所涉及的组件开发，都能进行任意物料模块与需要对接的服务进行关联打通使用。方便`写到简历`、`用到项目`、`实战锻炼`、`积累经验`。

## 二、项目架构

- **目标**：此项目以围绕类似 ChatGPT 生成式服务，构建微服务应用架构体系组件。包括；用户鉴权、公众号、多方支付、企业微信等对接方式，满足不同诉求的使用。并以模块化设计，积木式构建应用，让不同的场景诉求都可以配置化对接。
- **功能**：更直白一些就是通过这套微服务体系，可以构建出；`网页版ChatGPT对接`、`用户鉴权校验接口`、`关注公众号解锁`、`支付付费购买`、`公众号自动回复`、`企业微信聊天对接`、`知识星球对接`等。

那么这套系统是以`视频`和`小册`的教程为导向，教会大家开发这些各个模块的技术组件和技术服务。同时这里的组件和服务，都是微服务实现，可以被替换成其他任何一个你所需的内容。比如不是对接 ChatGPT 而是你想对接一个其他的服务也是可以的。

**整个系统架构如下**：

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-230422-01.png?raw=true" width="750px">
</div>

如图；以用户请求为入口，通过 `Nginx SSL 443` 校验转发到对应的服务，并做相关的鉴权和服务控制，并完成最终的 token 授权使用。整套微服务包括系统；`chatgpt-api-sdk`、`chatgpt-auth`、`chatgpt-wx`、`chatgpt-pay`、`chatgpt-zsxq`、`chatgpt-admin`、`chatgpt-web` 服务。

## 三、拓扑结构

接下来我们再以工程拓扑的视角看下这套需要开发的系统；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-00-01.png?raw=true" width="850px">
</div>

如拓扑结构，系统从上到下以不同的产品形态，统一调用封装的服务API进行功能的流转。API系统中所处理的核心动作，会以各个物料模块进行实现。所以这里会拆分出标准的 ChatGPT-API 业务系统，之后再由各个模块系统支撑。到具体的模块中再进行详细的系统设计。

## 四、技术使用

此项目会使用到 `SpringBoot`、`MyBatis`、`MySQL`、`Redis` 等技术栈，但因本项目主要以小成本，轻量维护的实际使用为主，所以不会过多引入分布式技术栈。所以在设计实现上，主要以小而美、小而精，且能匹配到真实场景的使用为主。—— 分布式技术栈是为了更大规模的体量使用，但也会为此付出运维和应用服务器成本。所以一些中小厂的项目或者创业类型的项目，都会优先更轻量级技术栈使用，以此减少这部分成本。

除技术栈的使用外，涉及到开发工具包括；`IntelliJ IDEA`、`WebStorm`、`Docker`、`Protainter`、`Nginx`、`Git`、`Maven`、`Navicat`、`SSH工具`等，以满足开发代码中的使用。

关于此项目可能还会涉及少部分 Next.js、Typescript 等前端知识，方便做 Web UI 的开发。

## 五、课程计划

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-00-03.png?raw=true" width="850px">
</div>

此课程会包括，基础设置、模块开发、API服务等几块内容，而基础设施主要为一些基本操作，小傅哥会把一些这样的内容都放到这块，方便很多新人伙伴学习使用。这里会分为3个大的步骤；

1. 在基础设置的基本必备服务搭建后，会进入接口鉴权的简单开发，这个模块开发后，大家就可以简单的使用了小傅哥提供的 OpenAI 了接口了。—— 当然你如果自己有 OpenAI 接口，也可以直接使用。**像 [https://huggingface.co/](https://huggingface.co/) 也提供了一些可以免费使用的简单 Open-API**
2. 有了这部分内容的使用，后续会进入 API-SDK 的开发，以及网页的简单开发。通过这样的开发构成一套基本的模块服务。ChatGPT-WEB-UI -> API-SDK -> 鉴权 -> OpenAI 的使用。
3. ChatGPT-WEB-UI 流程🏃🏻跑通后，就可以逐步扩展其他服务模块。让业务与场景结合，如关注公众号、公众号回复、企业微信机器人、交易支付购买授权Token。这个过程可以让 ChatGPT-WEB-UI 与各个模块结合使用。

## 六、如何开始

这套课程会以`视频`、`小册`、`代码`、`作业`的方式进行推进，视频主要以演示操作、讲解核心的方式进行，并在小册和对应的代码中细化细节展示。鉴于星球有些在校的编程新手，所以关于`工程的创建`、`代码的提交`、`镜像的打包`、`容器的部署`等这些基础操作，也都会在视频中进行演示，方便大家更容易的上车。

### 1. 开通权限

星球课程涉及的代码部分会通过 [gitcode.net](https://gitcode.net/KnowledgePlanet) 提供，你只需要申请一次就可以授权到星球所有课程的代码仓库。授权申请地址：[https://t.zsxq.com/0dS1kW2r9](https://t.zsxq.com/0dS1kW2r9) - 审核后即可访问星球的项目仓库了。

### 2. 加群交流

小傅哥为星球伙伴创建了专属的VIP技术交流群，你可以通过扫码添加小傅哥的微信，备注上`你的星球编号`。我会给你拉到专属的微信群。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-00-02.png?raw=true" width="850px">
</div>

### 3. 推荐工具

推荐工具；- 随着课程讲解会引导大家安装这些工具。
- [Termisu](https://termius.com/)：云服务器链接工具，自带一套 SFTP 工具，很好用。
- IDEA 插件：`Sequence Diagram` - 用于方法上右键查看代码流程的，主要帮助大家理解代码。
- [Docker](https://www.docker.com/) - 可本地安装，不需要非得购买云服务器，也可以满足学习诉求。
- [natapp](https://natapp.cn/) - 内网穿透工具，相当于你开启后，可以分配给你一个域名，让外网访问到你的服务。

---

好啦🌶，我们的课程列车🚄😆🙈💃🙆🏃🏻‍♀️即将启动，你准备好了吗🐴！