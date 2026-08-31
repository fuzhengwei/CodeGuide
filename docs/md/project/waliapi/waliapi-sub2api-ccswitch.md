---
title: sub2api + cc swtich = waliapi？
lock: no
---

# sub2api + cc swtich = waliapi？

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

`200￥`来块月套餐，一个问题干掉 `16.7%` 的额度。🤨 完啦，没有一家 Token/Coding Plan 是省心的了，不是消耗嗖嗖的，就是卡着不动的。咋整？！

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walicode/walicode-introduce-01.gif" width="150px"/>
</div>

**sub2api + cc swtich = waliapi ？** 

啥？🤔 啥，意思。

其实不少即使是做程序👨🏻‍💻 的码农，也不知道中转站的 API 是怎么来的。所以，`哭哭`花钱买套餐。还总被一会 0.8倍消耗引上车，一会又提高 2.0倍消耗，把你充值的“马内”干没了。那这东西不能自己整吗？不对外，就自己用。

当然能！

`sub2api` 可以登录 openai（chatgpt）账号，转换为 API 协议，消耗账号内额度。之后 `cc switch` 配置 API 在设置到 codex 进行使用。不过两个一起弄，挺麻烦呀？咋整。我给你来个，两招合并成一整招！

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-sub2api-01.png" width="150px"/>
</div>

所以，WaLiAPI 来了。我需要一款，可以负载方式统一管理 LLM API + Auth（OpenAI/Kimi）渠道，动态映射 auto -> N（gpt、glm、deepseek），以及具备日志审计（知道工具软件发了啥）、端到端 RAG、LLM-WiKi 等核心服务的统一通用 AI 网关服务。—— 注意，当你换模型的时候，不需要重启 codex！模型故障了，也可以做到动态切换。**先斩后奏，应用特许，这就是 WaLiAPi！**

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-sub2api-02.png" width="850px"/>
</div>

接下来，小傅哥就带着大家走一遍流程。嘎嘎简单，跟着操作完，你就可以爽用 chatgpt 模型了。也可以配置到 codex、walicode、claude code 等各类 AI 软件中。

## 一、前置准备

### 步骤1；WaLiAPI 软件安装

- 官网：[https://walicode.xiaofuge.cn](https://walicode.xiaofuge.cn/) WaLiCode 官网，包括了配套软件 WaLiAPI 服务。
- 下载：[https://drive.weixin.qq.com/s?k=ACMA4AfQABU4S23jg8](https://drive.weixin.qq.com/s?k=ACMA4AfQABU4S23jg8)
- 仓库（github）：[https://github.com/fuzhengwei/WaLiAPI](https://github.com/fuzhengwei/WaLiAPI)
- 仓库（gitcode）：[https://gitcode.com/fuzhengwei/WaLiAPI](https://gitcode.com/fuzhengwei/WaLiAPI)

### 步骤2；OpenAI 账号

- 方式1，可以是自己注册的 N 多个账号。
- 方式2，在一些平台购买账号，或者直接购买 auth/sub2api/cpa 登录的 token 信息。🤔 不过这个渠道也总换，可以自己搜索。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-sub2api-03.png" width="550px"/>
</div>

> 账号商家有时候也换，可以谷歌搜索 `Plus-个人自用超级合适--发货格式账号---密码---2FA`，就看到了这类的售卖账号的了。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-sub2api-06.png" width="750px"/>
</div>
一般这类卖账号的比较多，卖 Token 的比较少（稳定性问题）。购买后，分3段，账号、密码、2fa，接下来我们使用下。


## 二、授权账号

你可以参考视频操作，也可以跟着下面的文档流程来。如果你获取的是可以直接导入的 Codex Token，也可以直接导入。



### 1. 登录说明

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-sub2api-04.png" width="650px"/>
</div>

- 首先，进入 WaLiAPI 的【渠道】管理里（渠道的意思就是这些 LLM 大模型通信 API 提供者和 Auth 方），选择 Auth 账号的 Codex 登录。
- 之后，点击登录账号，这个时候会提示一个登录过程。【开始登录】跳转浏览器，进入浏览器会需要你的 openai 账号。填写后开始登录，登录成功回到 WaLiAPI 就可以使用了。
- 注意，一般购买的账号，登录流程为；账号密码登录、2fa（一个链接里输入码）需要获取登陆一次性验证码、发码平台选择一个手机号码进行绑定。这样就能登录成功。

### 2. 开始登录

#### 2.1 跳转登录

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-sub2api-05.png" width="450px"/>
</div>

- 开始登录后，会跳转登录地址，这里可以填写你的账号信息。

#### 2.2 输入账号

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-sub2api-07.png" width="450px"/>
</div>

- 把你自己的或者购买的账号，输入进去使用。
- 如果是你自己注册的账号（2.3、2.4，这2步骤就都不需要了）

#### 2.3 验证码（2fa）

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-sub2api-08.png" width="750px"/>
</div>

- 用第三段账号，放到 2fa 地址中：[https://2fa.fun/index.html](https://2fa.fun/index.html)
- 拿到获取的验证码，进行登录。

#### 2.4 手机号

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-sub2api-09.png" width="750px"/>
</div>

- 到这步骤，需要发码平台，虚拟手机号，接个验证码。
- 获取验证码后，填写后登录。

### 3. 登录完成

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-sub2api-10.png" width="550px"/>
</div>

登录完成后，你就可以看到可用的模型了，以及周和小时的限额。

### 4. 测试使用

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-sub2api-11.png" width="650px"/>
</div>

在【使用】里，你可以选择 MODEL 模型 gpt-5.4 进行测试验证。

## 三、使用验证

### 1. 映射模型

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-sub2api-12.png" width="450px"/>
</div>

- 这里你可以给渠道的模型添加一个映射 auto -> gpt-5.4 可以是 1:N 方便我们映射，后面更换了也不用重启 codex
- auto 也可以是其他名称，这样配置后就具备了负载能力。

### 2. 写入配置（codex）

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-sub2api-13.png" width="550px"/>
</div>
- 首先，你可以安装一个 Codex 之后配置使用。如果不方便使用 Codex，可以下载这个（WaLiCode）[https://walicode.xiaofuge.cn/](https://walicode.xiaofuge.cn/)

- 在【使用】Codex 下，可以选择 auto 模型，写入网关配置。注意，本地会让你创建一个 apikey 这部分没创建也会提示你创建后使用。

### 3. 对话验证

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-sub2api-14.png" width="550px"/>
</div>

之后你就可以非常方便的使用 Codex 进行编码操作了。爽用起来！

## 四、学习项目

> 给大家分享下，小傅哥最近一年，正在做的 AI 实战类项目。有；WaLiAPI（网关）、WaLiOffice（办公）、WaLiSSH、WaLiCode（AI 编码于运维助手）、AI MCP Gateway（http->mcp 仿阿里）、AI Agent 脚手架（Harness 同类概念）。这些内容你可以在博客的 [https://bugstack.cn/](https://bugstack.cn/) 实战项目里进入。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-sub2api-15.png" width="550px"/>
</div>

- 【WaLiAPI - 本地 LLM API 网关（+RAG、LLM-Wiki） 统一模型配置和负载】本地运行的 LLM API 网关桌面软件，转换各供应商 API 为 OpenAI 兼容协议和 RAG 能力（数据建模、文档解析、tree-sitter AST 符号感知分块、Embedding 渠道复用、HNSW 向量索引构建、FTS5 全文索引、向量 + 关键词混合检索、RAG 问答（多轮对话 + Token 逐级回退 + Deep Research 多轮迭代）+ MCP 服务）。可配合 WaLiCode、Codex、Claude Code、QClaw 等 AI IDE，增强 AI 对话能力，让你知道 AI 对话，到底在说啥。 -
- 【WaLiOffice - 基于 Deepseek Harness 复刻 “豆包” 能力】 WaLiOffice 是一个 Web 端 AI Agent 智能办公平台（对接了 docx、ppt、excel、draw.io、echart、画图、做视频），用户通过自然语言对话即可自动生成 PPT、Word、Excel、Draw.io 图表、图片和视频等多种文档。后端用 Rust（axum + rusqlite），前端用 React 18 + TypeScript，数据存储为 SQLite/MySQL（可配置 2 选 1），LLM 对接 OpenAI 兼容端点并支持 SSE 流式输出。包含 DSH 插件 
- 【AI Coding/DevOps 可持续交付助手 - ai coding、ai 运维、ai 办公（字节 trae.ai）】 该项目，以 WaLiCode 的架构设计为内核标准，分拆 2 套工程（walissh、walicode）讲解。项目使用 Spring AI + Google ADK + Tauri（+Typescript），结合运行时智能体设计，涵盖；`Agent WorkFlow`、`上下文管理（对话追踪、搜索、提示词）`、`意图识别（+LLM）`、`混合窗口裁剪`、`动态提示词（ Prompt 组装器、里程碑追踪）`等，核心设计，构建一整套 AI Agent 智能体服务工具。 用途呢，用于辅助编码，用于辅助运维。除了基本的 AI Coding、SSH +Sftp 能力外，你可以为项目工程，扩展添加 mcp、skills 技能等，为你的服务器做智能化运维服务管理。如；`日志分析`、`系统健康度巡检`、`内存泄漏排查`、`复杂项目链路耗时处理`、`项目压测优化建议等等场景`。这个工具，就是你穿插在云服务器上的智能机器人。现在它已经可以替换我电脑上的其他 SSH 工具了，嘎嘎好用！
- 【AI MCP Gateway 网关服务系统】 字节内统一把业务接口转换 MCP 服务网关 本项目是 AI Agent 智能体，关于 MCP 协议对接的通用网关服务项目，以解决各类业务接口便捷转换为 MCP 协议而设计实现。通过这样的配置，可以大大的简化从普通 http、rpc 接口到 MCP 协议的转换操作。这样的项目，也是每个互联网公司在做 AI Agent 智能体时，必备的基础设施项目。
- 【AI Agent 脚手架 + 场景应用】 本套 AI Agent 综合智能体脚手架 项目，主要为降低智能体开发门槛、提升业务系统集成效率而构建。项目涵盖了：工程脚手架自动生成、智能工作流编排、多模态交互（文本 / 绘图 / 动作） ，以及手机自动化网关控制等核心功能和场景方案。
