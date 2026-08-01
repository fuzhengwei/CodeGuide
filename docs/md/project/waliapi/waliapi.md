---
title: WaLiAPI - AI LLM LocalGateway
lock: no
---

# WaLiAPI - AI LLM LocalGateway 本地网关系统（渠道分发、日志留存、日志审计、知识库）

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?isOutside=true&aid=116966438209886&bvid=BV1gngU6JECc&cid=40208893567&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

大家好，我是技术UP主小傅哥。

你想方便的使用各家 LLM 大模型吗，你想稳定的负载均衡的调用吗，你更想透明的知道 Agent 工具调用 LLM 都发了什么信息吗？今天我就帮你解决这个事情。👍🏻

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-0-00.png" width="250px">
</div>

**WaLiAPI -  统一模型配置和负载，本地的安全 LLM 网关系统。**

我为啥要做这样一套东西？

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-01.png" width="250px">
</div>

1. 现在的 LLM 厂商太多了，Agent 工具也是冒样了。两下一组合，笛卡尔积的配置，手都麻木了。
2. 我不知道 Agent 调用 LLM 大模型都发了什么东西，到底消耗了多少 Token，耗时是多少。
3. 作为学习，我还想知道我使用的 AI IDE 在和 LLM 通信的时候，提供的提示词，使用的工具都是什么。
4. 像是 Claude Code 依托无痕 Unicode 隐写术，嵌入用户属地、代理、机构特征，零流量痕迹静默上报建档、伺机封禁账号。是不可以拦截下。
5. 安全审计，公司用 Agent 工具 + LLM 做代码开发，文件处理，得有日志保留。

> 接下来，小傅哥就带着大家体验下 WaLiAPI 如何安装使用。

## 一、软件介绍

WaLiAPI 是一个本地运行的 LLM API 网关桌面软件。把 OpenAI、Claude、Gemini、DeepSeek、通义、智谱、Moonshot、豆包、Ollama 等不同供应商的 API 统一转换成 OpenAI 兼容协议，让你只需一个`本地地址`、`一个密钥`，就能在所有客户端里无缝切换和管理多家模型。

- 密钥与配额管理，为每个下游用户或应用生成独立的本地访问密钥，可设置调用配额，不必把真实上游 Key 分发出去。
- 负载均衡与故障切换，支持按优先级 + 权重选择渠道，某个渠道失败时自动重试其他渠道，不影响调用方。
- 请求日志与审计，每次调用的状态码、`Token` 消耗、上游路由、工具调用、请求参数全部入库，可搜索、可筛选、可展开查看。
- 安全审计中心，内置风险检测引擎，自动扫描请求中的凭证泄露（`API Key`、`Cookie`、私钥）、敏感路径访问（`~/.ssh`、`.env`）、工具命令外联（`curl`、`wget + 管道`）、`Unicode` 隐写字符、追踪像素、公网 IP 探测等风险，在日志页以风险等级标签和详情面板展示。支持审计、警告、脱敏、阻断四种模式，内置规则可启停，可自定义黑白名单。

>这可以让你在其他 AI IDE/CLI 工具里，只需要配置一个 WaLiAPI 就可以方便的管理所有 LLM API 了。

## 二、下载安装

- 官网：[https://walicode.xiaofuge.cn/](https://walicode.xiaofuge.cn/) - WaLiCode 官网，包括了配套软件 WaLiAPI 服务。
- 下载：[https://drive.weixin.qq.com/s?k=ACMA4AfQABU4S23jg8](https://drive.weixin.qq.com/s?k=ACMA4AfQABU4S23jg8)
- 仓库：[https://github.com/fuzhengwei/WaLiAPI](https://github.com/fuzhengwei/WaLiAPI)

## 三、功能演示

### 1. 首页介绍

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-02.png" width="850px">
</div>

- 这是 `WaLiAPI` 的首页，你可以看到仪表盘、使用（curl）、渠道、秘钥、日志、设置，以及一些核心的调用数据、服务状态。你也可以点击进入 GitHub 开源仓库，查看此项目的源码，感兴趣也可以做一些贡献。
- 注意，在欢迎使用 WaLiAPI 右侧的`？问号`，点击可以看到使用指引，是一个快速上手指南。

### 2. 渠道配置

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-03.png" width="450px">
</div>

- **这是使用 WaLiAPI 第一步**，你可以把自己手里的 LLM 都通过`新建渠道`的方式添加寄哪里。按照不同协议类型进行选择添加，基本就是 OpenAI、Anthropic、Ollama 三组协议。注意，这里有个便捷操作，可以把 WaLiCode 里的模型，或者 AI 扫描，把其他 AI 工具的模型，加入到渠道管理。
- 依次添加，BaseUrl、APIKey，以及 + 模型，你使用的 LLM 提供了什么模型，添加进来就可以了。
- 模型映射，因为市面上的 LLM 太多了，如果你希望这些模型，都使用一个模型名称访问，那么可以添加一个映射，如 `auto` -> `agnes-2.0-flash` 访问 `auto` 就可以访问到 `agnes-2.0-flash` 模型了。
- 优先级和权重，字面意思，按需配置即可。

### 3. 秘钥（ApiKey）

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-04.png" width="700px">
</div>

- `+` 新建秘钥，LLM 大模型通信，安全保证，是需要建一个秘钥的。
- 你可以给自己不用的软件，分别创建一些使用的秘钥，这样也可以方便管理。如果 WaLiAPI 是部署到一个共用服务器上的，那么可以给大家分别创建不同的 ApiKey 

### 4. 使用验证

#### 4.1 配置说明

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-05.png" width="700px">
</div>

- 使用，可以选择 API Key、Model，之后发送测试验证。注意，这里就有映射的模型。
- 示例，你可以复制 curl 验证，或者 js、ts、java 代码示例，辅助你做开发。
- 配置，如果是在 WaLiCode、QClaw 等软件里，可以复制 BaseUrl、API Key、Model 来使用。

#### 4.2 配置应用（WaLiCode）

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-06.png" width="500px">
</div>

- 软件：WaLiCode
- 安装：[https://walicode.xiaofuge.cn/](https://walicode.xiaofuge.cn/)

#### 4.3 配置应用（QClaw）

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-07.png" width="500px">
</div>

- 软件：QClaw
- 安装：[https://www.qclawd.com/](https://www.qclawd.com/)

### 5. 请求日志

**日志，是 LLM 网关最关键的能力！**

当你让 Agent 或客户端工具帮你干活时，它到底把什么上下文发给了大模型、附带了哪些文件内容、调用了哪些工具、执行了什么命令 —— 这些信息你通常看不到，全凭工具自觉。WaLiAPI 把每一次调用的完整请求体、模型参数、工具调用名称与参数、上游路由、Token 消耗、响应状态全部记录留存，你可以随时回溯某次调用到底发了什么、做了什么。

但这只是基础。真正的价值在于安全审计——WaLiAPI 会在每条日志上自动标注风险等级，识别请求里有没有夹带 API Key、Cookie、私钥等凭证，有没有尝试读取 `~/.ssh`、`.env` 等敏感文件，工具调用里有没有 `curl` 外联、管道上传、追踪像素等可疑行为，甚至连 Unicode 隐写字符也会被检测出来。

这意味着你不仅知道 Agent 发了什么，还知道它有没有在你不注意的时候做不该做的事——比如把你的环境变量悄悄发给一个陌生域名，或者让模型读取本不该碰的凭证文件。对于任何认真使用 AI Agent 的人来说，这不是可选的可观测性，而是必须的安全底线。

>接下来，我们看看日志都记录了什么。

#### 5.1 日志列表

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-08.png" width="950px">
</div>

- 你可以看到每一条请求的信息，使用的模型（含映射），消耗的 Token，以及占用的耗时。
- 上面还有一个搜索能力，可以让你根据具体的 apikey、模型、时间来查询，方便做统计。
- **严重100**，是做的安全审计，配置的监控。如果像 Claude Code 依托无痕 Unicode 隐写术嵌入上报信息就可以被审计日志采集掉，还可以做脱敏。

#### 5.2 日志明细（工具、审计、提示词）

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-09.png" width="950px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-11.png" width="950px">
</div>

- 当你点开日志的时候，可以看到具体的明细信息，包括；安全审计、设计工具、对话构成、消息列表（系统的、用户的、AI 的等）
- 如果你正在学习一个黑盒的 AI 工具，如 Claude Code、Codex，那么通过 WaLiAPI 就可以知道，这些软件到底在干啥了。查看原始 JSON 就能拿到他们的数据。

>注意，并不是所有的都是不安全的，具体要看设置里的安全审计配置。

### 6. 功能设置

设置页是 WaLiAPI 的控制中枢，按功能分为五个标签页，清晰隔离不同配置领域。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-10.png" width="950px">
</div>

- "安全审计"是核心亮点，支持四种模式：只审计、警告、脱敏、阻断，可独立开关 Unicode 隐写检测、工具命令风险检测、外联追踪检测，内置 25 条风险规则并可自定义黑白名单，帮你精准掌控每一次调用的安全边界。
- "服务配置"管理网关的监听地址与端口，支持一键重启。默认；`http://127.0.0.1:8777/v1` - `8777` 可以在服务配置里修改端口。
- "通用设置"托盘行为与开机自启。
- "界面设置"切换深色浅色主题与中英文语言。
- "重试策略"控制失败自动重试次数，提升调用稳定性。所有配置一键保存，即时生效。

## 四、学习推荐

作为程序员，非常有必要在 AI 时代，快速积累 AI 技术，以及做一个 AI 作品。这样才能综合的提高自己的竞争力。下面有一套完整的学习路线，有 AI 新范式 VibeCoding 编码、AI Agent Guide 通识教程，还有非常多的实战项目，如图；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-agent-scaffold/part-1/1-1/images/ai-agent-scaffold-1-1-10.png" width="950px">
</div>

- 首先，小白推荐先进入 AI Agent Guide 基础认知教程：[ai-agent-guide.xiaofuge.cn](https://ai-agent-guide.xiaofuge.cn/) - 涵盖基础概念、八股、面试内容。
- 之后，如果没有使用过 AI IDE 工具，可以做下 AI 新范式，通过 AI 实践来锻炼。
- 最后，可以通过项目驱动学习，结果导向的项目实战，可以更好的锻炼 AI 技能，也是为转岗到 AI Agent 应用开发工程师做准备。