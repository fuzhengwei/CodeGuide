# 以前白花好几百！时隔4年，自己把愿望实现啦！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

**4年前（2022年）**，我买了一个 IPad Pro M1 11寸平板（`6.1k`），并配了妙控键盘（`2.1k`），美滋滋的想着再安装个开发工具，这不就是出门在外的生产力吗！好家伙，买软件花了小 `￥300` 愣是没法写代码！😂 

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-ipad-01.png" width="550px"/>
</div>

当时还做个视频 [https://www.bilibili.com/video/BV1Dv4y1M7Sx](https://www.bilibili.com/video/BV1Dv4y1M7Sx) 有5万次观看，其实不少伙伴都有在平板上写代码的诉求，奈何就没有任何一款软件，可以好好的把这个支持起来。

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-ipad-01.gif" width="150px"/>
</div>
**既然，市面上没有个像样的软件，能支棱起来，那我来！** 4年后的今天，小傅哥自研的 [`WaLiCode`](https://walicode.xiaofuge.cn/) 来了，这是一款 AI IDE Coding/DevOps 智能辅助编码运维工具，可以支持在 `Mac（Apple/Intel）`、`Windows`、`Linux` 多端使用，也可以在`安卓`、`IOS`设备下载安装。这是因为小傅哥在产品设计之初，选择了 [Tarui](https://v2.tauri.app/) 作为前端框架，可以支持多端打包。

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/product-walissh-user-guide-16.jpg" width="650px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-ios-02.png" width="650px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-ios-01.png" width="650px"/>
</div>

如果你也有一个安卓平板或者 IPad 平板，那么可以从官网 [https://walicode.xiaofuge.cn/](https://walicode.xiaofuge.cn/) 指引进行安装。整体体验效果还不错，虽然 `安卓`、`IOS`，没法支持那么多命令执行，但临时写个代码，运维个服务器，还是没问题的。

**嘿嘿**，如果只是让大家在平板上安装上 WaLiCode 还不够给劲，我要给你不只是 WaLiCode 软件，还有一款教学版的 WaLiCode 整套源码。这是你在当前市面上，能找到的，真正有客户使用，真能帮用户编码的一套 AI Agent 智能体实践课程了。接下来小傅哥分别介绍下最新版 WaLiCode 所具备的能力（它具备的，你学习的教学版，就都可以实现），以及教学版 WaLiCode 本周最新更新内容（重点，支持了 CLI 终端能力）。

## 一、对外版 WaLiCode - 更新

官网：[https://walicode.xiaofuge.cn/](https://walicode.xiaofuge.cn/)

我把 WaLiCode 对标 `Cursor`、`Trae`、`QCoder`、`Claude Code` 等主流 AI IDE/CLI 工具，产品能力建设以行业头部标准为基线，打造同层级端到端 AI 编程解决方案。

现已有`1.5+`万次安装，几千人使用，跟随主流智能体设计，持续迭代和维护。我也用这样一份真实的实战经历，不断的打磨产品，并把这样的经验积累，再转换为对应的实践课程，分享给读者伙伴。所以，跟着这套东西的伙伴，你拿到的就是最有价值的实战版本。

>`注意甄别市面 Demo Agent`，很多没有实际对外的发布产品的，没有实际用户使用的，很多都是 Demo 案例。

### 1. 模型配置

#### 1.1 walicode 平台

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-v0.3.0-01.png" width="450px"/>
</div>

- 首先，WaLiCode 平台，默认提供了 claude-opus-4-6、gpt-5.4、gpt-5.1、gpt 4.1 模型，速度很快，很方便使用。
- 之后，也支持多厂家模型灵活配置；自定义 OpenAI 格式的各类大模型接口，无论是智谱、火山引擎还是本地部署的模型，均可一键接入，满足不同场景的开发需求。也支持你自己做号池（CPAMC）。

#### 1.2 其他供应商

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-v0.3.0-02.png" width="650px"/>
</div>

- 智谱：`https://open.bigmodel.cn/api/paas/v4`
- Anthropic：`https://ark.cn-beijing.volces.com/api/coding/v1`
- CPAMC：`https://cpa.taian.liujunjiang.cn:1111/v1`
- deepseek：`https://api.deepseek.com`
- 豆包：`https://ark.cn-beijing.volces.com/api/coding/v1`
- xiaomimimo：`https://token-plan-cn.xiaomimimo.com/v1`
- Ollama：`http://127.0.0.1:11434`

### 2. 编码绘图

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/product-walicode-user-guide-10.png" width="950px"/>
</div>

- 你可以辅助编码、辅助绘图（让绘制 draw.io 格式），这些内容你就按照市面用过的 AI IDE 软件就可以直接用。
- 功能；拆解、追踪、收藏、任务、历史、图片、文件，这些能力都是支持的。

### 3. Git-AI 归因

Git-AI 归因，是记录每个人使用 AI 编码的记录，包括；总量、模型、次数（日、周、月）。之后 Git 能力也是非常好用的，就像 IntelliJ IDEA 里使用 Git 一样。在小傅哥体验过不少的 AI IDE 工具，Git 能力都是很弱，也不好用的。

#### 3.1 本地记录

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/product-walissh-user-guide-11.png" width="950px"/>
</div>

#### 3.2 云端记录

##### 3.2.1 记录上报

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/product-walissh-user-guide-12.png" width="550px"/>
</div>

- 在 WaLiCode 设置里，有一个 Git-AI 归因的能力，可以记录你的使用数据。这里小傅哥设计了开放协议，可以把你使用 AI 的数据，上报记录。如果是公司里，大家都使用这个软件，就可以统计出所有人的使用量。
- 归因上报程序；[https://github.com/fuzhengwei/WaLiCode-Git-AI-Statistics](https://github.com/fuzhengwei/WaLiCode-Git-AI-Statistics) - `你可以基于程序文档说明，做二次开发`

##### 3.2.2 云端查看

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/product-walissh-user-guide-13.png" width="850px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/product-walissh-user-guide-14.png" width="850px"/>
</div>

WaLiCode-Git-AI-Statistics 做了统计案例，这些数据都来自于上报的信息。有了这样的数据，企业里也更好统计大家的贡献。

> 好啦，其他更多内容，这里不一一列举了。可以在官网 [https://walicode.xiaofuge.cn/](https://walicode.xiaofuge.cn/) 查看，也可以直接下载软件体验。

## 二、课程版 WaLiCode - 更新

课程版 WaLiCode 使用 Spring AI + Google ADK + Rust + Typescript + Truri 核心技术栈构建的智能编码和 AI Shell 运维能力的软件服务。可以全场景支持开发和运维。

该项目，之所以选择 Java 编码作为智能体能力的服务端，一个是因为做统一的服务端，可以快速复刻到任何场景完成智能体项目，另外一个是因为大家学习 Java 面向对象（+DDD）架构会更加清晰好理解（纯后端程序员，直接看前端 TS 写的 Agent 压力不低）。

此外，因为 Agent 的设计都是通用的，如 Google ADK 框架同时提供 Java、Python、Go、TypeScript 等多语言实现，只要你一套做下来，其他的都可以快速搭建出来。所以，Agent 开发，也不用特别在乎语言，AI 时代更多是架构、设计、方案、技巧，以及一套语言的熟练，即可。

WaLiCode 课程版本，不只是深度迭代 AI IDE 能力，也扩展 CLI 使用场景。因为他们都是复用 `服务端智能体` 能力。所以，这也是大家学习这样一套智能体，就可以在多个场景服用起来的价值。

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-introduce-v0.3.0-04.png" width="650px"/>
</div>

这个架构设计，全面的覆盖了现代智能体（Agent）设计的核心能力，有这套基础，想做其他的场景都会变得非常容易。


### 1. AI Coding

#### 1.1 项目分析

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-introduce-v0.3.0-01.png" width="850px"/>
</div>

#### 1.2 代码开发

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-introduce-v0.3.0-02.png" width="850px"/>
</div>

#### 1.3 智能运维

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-introduce-v0.3.0-03.png" width="850px"/>
</div>

### 2. AI CLI

#### 2.1 设置命令

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-introduce-v0.3.0-05.png" width="450px"/>
</div>

#### 2.2 进入终端

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-introduce-v0.3.0-06.png" width="850px"/>
</div>

- 在终端输入命令 `walicode-cli` 即可在任何项目下或则各类终端里进入到 WaLiCode CLI 

#### 2.3 对话能力

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-introduce-v0.3.0-07.png" width="850px"/>
</div>

可以在终端里输入信息进行对话，这里小傅哥简单的验证了 `1 + 1`，也可以询问项目，让修改代码等功能。就像和 AI IDE 图形化界面对话似的。`我还是比较喜欢有图形化界面的，直接拖拉代码文件到对话输入框，操作起来更快。`

## 三、课程代码

如果感兴趣 AI Agent 项目，想从“古法编程”编程行业，转换到智能体，吃一波 AI Agent 开发工程师红利（`好几十K的薪资待遇`）。那我非常推荐你学习下这套具备真实市场监测过产品设计 WaLiCode 项目。

### 1. 薪资待遇

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walicode/walicode-introduce-02.png" width="550px"/>
</div>

现在各个互联网公司都有智能体开发场景，而且薪资待遇非常不错。这个类型岗位，早点储备转换过来，绝对可以赚一波！

### 2. 整套路线

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-agent-scaffold/part-1/1-1/images/ai-agent-scaffold-1-1-10.png" width="550px"/>
</div>

小傅哥的社群「码农会碎」，不只是一个项目，而是一整套的项目，带着你学习、沉淀、成长，让你足够可以匹配到目前的 Agent 开发工程师岗位。

### 3. 课程说明

《**AI Coding/DevOps 可持续交付助手**》 - WaLiCode + WaLiSSH 让 AI 既可以辅助编码，又可以运维云服务器！

该项目，已经在更新中，先讲解一个小场景 walissh（课程更新中），完事后就是拓展 walicode 项目。重点！walicode 项目代码，已经全量给了大家，如果学习过星球的「AI Agent 脚手架项目」，那么可以很快的上手 walicode 的学习。

课程地址：[https://t.zsxq.com/s83De](https://t.zsxq.com/s83De)

> 这套内容学习后，你也可以打包成 Windows、Mac、Linux、安卓、IOS 版本使用。😄

图片占位

