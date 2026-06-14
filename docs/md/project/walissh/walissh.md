---
title: WaLiSSH - AI Shell 智能终端
lock: no
---

# WaLiSSH - AI Shell 智能终端

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>项目：[https://t.zsxq.com/s83De](https://t.zsxq.com/s83De)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?isOutside=true&aid=116564019905827&bvid=BV1hS5G6tE7m&cid=38291374725&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

大家好，我是技术UP主小傅哥！

耗时`1个月`，迭代了`13个`版本，积累了近≈`1万个`，用户安装使用。WaLiCode「[https://walicode.xiaofuge.cn](https://walicode.xiaofuge.cn/) 」 一款小傅哥基于自研的 AI IDE 本地运行 AI 编程助手！**那么，现在我要为你做一个这样的课程版新项目！** 死鬼，开心吗🐴！

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-0-00.png" width="150px"/>
</div>

**呐，新项目是什么？🤔**

新项目是小傅哥自研的 `WaLiSSH`，一款本地运行的 AI SSH 运维助手。

**该项目**，以 WaLiCode 的架构设计为内核标准，使用 Spring AI + Google ADK + Tauri（+Typescript），结合运行时智能体设计，涵盖；`Agent WorkFlow`、`上下文管理（对话追踪、搜索、提示词）`、`意图识别（+LLM）`、`混合窗口裁剪`、`动态提示词（ Prompt 组装器、里程碑追踪）`等，核心设计，构建一整套 AI Agent 智能体服务工具。

**用途呢**，除了基本的 SSH +Sftp 能力外，你可以为 WaLiSSH 项目工程，扩展添加 mcp、skills 技能等，为你的服务器做智能化运维服务管理。如；`日志分析`、`系统健康度巡检`、`内存泄漏排查`、`复杂项目链路耗时处理`、`项目压测优化建议等等场景`。这个工具，就是你穿插在云服务器上的智能机器人。现在它已经可以替换我电脑上的其他 SSH 工具了，嘎嘎好用！

**说白了**，AI Agent 要解决的就是一整条研发交付路径服务，包括；研发设计（spec规约）、工程编码（WaLiCode）、项目上线/运维（WaLiSSH）等，而本次的项目，其实完全可以在学习后，扩展出Coding能力。

如果你感受过 [WaLiCode](https://walicode.xiaofuge.cn/) 有多强悍，那么你就知道会学习多少东西多硬了！（**技术高、简历硬、面试能力又高又硬**）好啦，接下来，我要放一些项目演示效果啦！🌶

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walissh/product-walissh-user-guide-00.png" width="200px"/>
</div>

--- 演示视频

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walissh/product-walissh-user-guide-10.png" width="240px"/>
</div>

🧧扫码加入「星球：码农会锁」，学习 walissh 实战项目。此外还可以获得已完成的20个项目（含6个AI项目 + 1个即将开启的）。地址：[https://t.zsxq.com/s83De](https://t.zsxq.com/s83De)

>下文有项目运行演示和详细介绍。关注`小傅哥の码场`「B站」，最近也会发布介绍视频。此次项目，会在本周末发出当前全部源码 + 运行说明，让心急的兄弟，先把热豆腐吃了。随后课程章节内容，会逐步更新。💐

## 一、软件启动

### 1. 软件信息

- 服务端（walissh-server）；Java jdk 17、maven 3.8.x、spring ai 1.1.5、google adk 1.2.0、mysql 8.x（存储ssh连接、上下文数据用于意图识别增强），（小傅哥已为此框架做了脚手架项目，可以快速搭建服务端）。注意，在项目里配置 LLM baseUrl、apikey 才可以使用。
- 客户端（walissh-client）；nodejs 20+、Tauri、React、Typescript（tauri 可以构建 Windows、mac、linux、安卓、ios，安装包）[https://v2.tauri.org.cn/](https://v2.tauri.org.cn/)

你可以获取源码后，可以通过 IntelliJ IDEA 启动服务端。之后使用 WebStorm 打开客户端（会自动启动构建 run npm install），之后在客户端命令终端里执行 docs/dev-ops 下（`start-dev.sh`/`start-dev.bat`）脚本。即可启动软件。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walissh/product-walissh-user-guide-01.png" width="650px"/>
</div>

> 后续课程会提供项目学习方式、项目教程、部署视频等，这些内容都会手把手教你学习下来。

### 2. 安装完成

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walissh/product-walissh-user-guide-02.png" width="950px"/>
</div>

- 对话；产品功能如图所示，首先需要通过 SSH 添加你的服务器。之后你可以在对话输入框，询问；`检查服务器状态`、`安装docker了吗`、`docker里都安装了什么软件，占用了多少资源`、`查看下系统配置的elk日志`、`帮我安装jdk17、nodejs`等等，你都可以操作起来了，再也不用一堆堆的写命令了。当你执行询问后，命令终端，会自动执行分析和执行你的命令，最终给出分析结果。
- 文件；你还可以点击文件、查看系统的运行日志、配置文件、添加 nginx 脚本等内容，这些内容也可以选择后加入到对话框。让具体的分析系统运行时候的报错，或者配置的 nginx 为啥访问不了等场景问题。
- sftp；该软件还有 sftp 这样的基础能力，但更加优化，你可以打开本地多个文件夹，选择不同的文件夹，上传文件。来回切换更加方便。

> 此外，还有很多小细节的细腻的功能，如命令辅助，这里列出了常用命令，可以让小白伙伴更加清楚且便捷的使用。还有历史对话，收藏对话（命令）等。

## 二、软件使用

### 1. 基础设置

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walissh/product-walissh-user-guide-03.png" width="850px"/>
</div>

- 首先，在设置里有个通用设置，这里的服务端地址，就是 walissh-server 部署的地址。如果你部署在云服务器，那么这个默认地址也可以配置成云服务器。
- 之后，如果你想改变外观主题或者终端字体，可以分别调整设置。
- 最后，是关于，这里写了相关的技术栈，整个项目也是基于此语言进行构建的。

### 2. 辅助命令

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walissh/product-walissh-user-guide-04.png" width="950px"/>
</div>


- 连接服务器，在终端页有相关的辅助命令，你可以直接使用。
- 右侧是对话输入框，以及对话展示栏。提供了一些案例，也可以点击后，测试验证。

### 3. 智能分析

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walissh/product-walissh-user-guide-05.png" width="950px"/>
</div>

- 你可以在对话框输入框，发送各类运维相关的信息。之后 ssh ai agent 会自动化的分析，并执行脚本命令（你可以看到命令的执行，风险命令已被限制不会执行）。
- 命令逐个执行完毕后，你会得到一个分析结果，以及会提示你是否做后续的其他操作。

>这个东西是我一直想要的，但市面上是真没有！我太需要一个智能 ssh 服务了，可以帮我这类的请运维工程师，做很多工作。甚至你像做个 redis 集群，也可以帮你配置完成。通过多个 ssh 终端对话，让 ai agent 智能体，依次的配置并验证检查。

### 4. 文件目录

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walissh/product-walissh-user-guide-06.png" width="950px"/>
</div>

- 这是一个非常有用的功能，我们把文件作为项目理解，你可以打开项目，并查看每个文件。包括像是你部署的项目，运行的日志，也可以查看、编辑，还能选中后对话，让智能体分析这是什么报错。
- 在此页面里，还分区展示了终端和文件，简直太舒服了！完全是人性化设计！（我已经在nas上部署了一套 walissh-server，日常的服务器运维，使用这套东西，简直太爽了 😄）

### 5. sftp（上传/下载）

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walissh/product-walissh-user-guide-07.png" width="950px"/>
</div>

- 这是一个 sftp 的基础能力，但小傅哥这里也做了扩展，可以打开做个文件夹，之后切换的进行使用。美滋滋！
- 我们日常在做上传文件的时候，往往文件都是在各类文件夹里，每次打开后再切换都比较麻烦，所以做了这样的能力。

## 三、课程信息

**重要信息**；本周末（10.16 ~ 10.17）先上传课程完整的全部代码（+启动部署教程）。之后逐步按照教学方式，在拆分讲解项目的`需求`、`设计`、`实现`、`部署`等系列内容。该项目会使用星球「码农会锁」的 [AI Agent 脚手架 ](https://t.zsxq.com/a8AJj)项目进行快速搭建，这几天时间可以快速学习补充。

### 1. 项目工程

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walissh/product-walissh-user-guide-08.png" width="650px"/>
</div>

- 项目分为服务端 walissh-server、客户端 walissh-client，所有的核心智能体能力，都在服务端里。客户端主要负责基础软件功能和api接口服务对接。
- 服务端 walissh-server 采用 DDD 架构设计（非常方便的管理智能体），domain 层负责智能体装配、运行时设计，之后 case 层负责ReAct 执行编排，以及接口结果的异步反馈。

> 项目工程地址会在本课程目录中更新：[https://t.zsxq.com/s83De](https://t.zsxq.com/s83De)

### 2. 核心领域（运行时 Agent）

```java
walissh-server-domain/src/main/java/cn/bugstack/ai/domain/agent/
├── service/
│   ├── IChatContextService.java              上下文管理领域服务接口
│   ├── IIntentService.java                   意图识别领域服务接口
│   ├── IIntentEnhancerService.java           意图增强领域服务接口
│   ├── IPromptService.java                   提示词构建领域服务接口
│   ├── armory/                               智能体装配（google adk）
│   ├── context/                              上下文记忆服务实现包
│   │   ├── ChatContextService.java           领域服务实现
│   │   ├── provider/
│   │   │   ├── ContextProvider.java          Provider 接口
│   │   │   ├── TerminalStateProvider.java    终端状态（OS、用户、目录）
│   │   │   ├── TaskProvider.java             当前任务
│   │   │   ├── MilestoneProvider.java        里程碑事件
│   │   │   └── ToolResultProvider.java       工具结果摘要
│   │   └── reducer/
│   │       ├── MessageReducer.java           Reducer 接口
│   │       ├── PriorityReducer.java          优先级裁剪
│   │       ├── SlidingWindowReducer.java     滑动窗口裁剪
│   │       └── HybridReducer.java            混合裁剪（默认）
│   ├── intent/                               意图识别服务实现包
│   │   ├── IntentService.java                领域服务实现
│   │   ├── ContextTracker.java               对话上下文追踪器 (内部组件)
│   │   └── classifier/
│   │       ├── IntentClassifier.java         分类器接口
│   │       ├── RuleIntentClassifier.java     第1层：规则分类
│   │       └── LLMIntentClassifier.java      第2层：LLM 分类
│   ├── enhance/                              意图增强服务实现包
│   │   ├── IntentEnhancerService.java        领域服务实现
│   │   └── processor/
│   │       ├── SignalExtractor.java          信号提取 (内部组件)
│   │       └── ContextSearch.java            服务器上下文搜索 (内部组件)
│   ├── prompt/                               提示词构建服务实现包
│   │   ├── PromptService.java                领域服务实现
│   │   └── dynamic/
│   │       ├── DynamicPromptBuilder.java     动态 Prompt 组装器 (内部组件)
│   │       └── MilestoneTracker.java         里程碑追踪器 (内部组件)
├── model/
│   ├── valobj/
│   │   ├── prompt/
│   │   │   ├── PromptContextVO.java          Prompt 上下文值对象
│   │   │   └── MilestoneVO.java              里程碑
│   │   ├── IntentResult.java                 意图识别结果
│   │   ├── ExtractedSignals.java             提取的信号
│   │   ├── SearchContext.java                搜索上下文
│   │   └── ConversationContext.java          对话上下文
│   └── entity/
│       └── ChatMessageEntity.java            对话消息实体
└── adaper/
    └── IChatHistoryRepository.java           对话历史持久化网关接口
```

- 这些核心设计，就是 walicode 里使用的，也是小傅哥不断深入折腾做的相关内容。通过这些设计，可以让智能体更加准确的识别用户意图，并做出正确的执行动作。
- 包括；上下文管理领域服务、意图识别领域服务、意图增强领域服务、提示词构建领域服务等。这些东西都有源码，你可以都陆续的学习到。**这些内容的学习，可以让你远超互联网公司里即使正在做智能体项目的大部分工程师！**

### 3. 库表设计

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walissh/product-walissh-user-guide-09.png" width="950px"/>
</div>

- 对话消息、里程碑、回话Session、ssh连接信息，都有相关的库表设计。这不是一个小儿科，而是一个完整的 ai ssh 工具服务设计。
- 当你进行细化的学习后，你也可以继续扩展这些内容，做你需要的功能的迭代。（当你的项目被其他伙伴开始使用的时候，你会有很多功能迭代，只要你动起来，你就会有非常多的学习积累）

> 现在，我们可以一起启程了，一起成为 AI Agent 相关技术实践工程师，积累 AI Agent 项目开发能力。—— 注意，小傅哥为你提供的从不是一个项目，而是一整套多个项目，从多方面为你提高技术体系化成长。

