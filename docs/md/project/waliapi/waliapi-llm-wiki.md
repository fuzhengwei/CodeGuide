---
title: WaLiAPI - 端到端，文档（LLM-Wiki）
lock: no
---

# WaLiAPI - 端到端，文档（LLM-Wiki）

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

`有人演员退役，做程序员啦？🤔`

AI 来啦！抹平了`0-3`年的差距。但我以为我的对手，只是`前端`、`测试`、`运维`、`产品`，都能写后端代码啦。

但没想到的是，现在是外行抢饭碗😂呀！国企老板能写程序、作家歌手能整APP，更可怕的还有`退役`演员，也要安装 claude code 开始做 Web 开发了！

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-llm-wiki-01.png" width="350px"/>
</div>

>诺亚，曾用名：夏树光、花吹紫苑。目前从事程序员工作。接受使用 Claude 进行自动化和 Web 开发。

**死鬼**，不知道你是否看过她的 AI 项目作品。但我知道，如果你在不好好把自己的各项技术储备上来的话，那么接下来，你将面对的是，一大波0经验的，直接具备3年开发能力。

近半年，各个互联网大厂开始导向；`测试转前端`，`前端做后端`，`后端整全栈`，`产品端到端`，大家轮轴转。所以，为了避免我，以及我的粉丝伙伴，被这趟列车甩下去。我做了一系列事情；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-llm-wiki-02.gif" width="150px"/>
</div>

- 今年年初，开发了一套 AI Agent 脚手架，涵盖 spring ai/langchain、google adk，之后开发 ai  + draw.io、ai + mobile 这样的基础场景案例。
- 随后三月份，结合 claude code 泄露的源码和过往对 AI 的积累，开始设计实现 WaLiCode [https://walicode.xiaofuge.cn](https://walicode.xiaofuge.cn) 并对外发布，现已有几万次安装使用。这个过程积累了大量的用户反馈和实战经验。
- 之后五月份启动教学版 walissh（AI 运维）、walicode（AI 编码） 系列项目，结合 AI Agent 脚手架，做深度复杂场景智能体的设计实现。`这个过程已有伙伴基于 walissh 项目拿到 Offer`
- 到了7月，开始设计 WaLiAPI 并直接对外开源，这是一款本地 LLM Local Gateway 项目，用于解决`统一模型转换协议`的网关能力，以及`日志审计`、`知识库`、`Wiki`，并能一键处理 codex、claude code 的配置使用。
- 随着整个过程的积累，还提供了一份 AI Agent 通识教程，方便大家随学，随补，随练八股文。[https://ai-agent-guide.xiaofuge.cn/](https://ai-agent-guide.xiaofuge.cn/) - 不少刷题后面试的伙伴都反馈说，很不错！

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-llm-wiki-03.png" width="350px"/>
</div>

为了避免兄弟们，乱抓瞎的学，**综上，我提供了一套完整的路线**；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-agent-scaffold/part-1/1-1/images/ai-agent-scaffold-1-1-10.png" width="650px"/>
</div>

**学习建议**

- 学习路线A-完整进阶：0 - 认知和实践、「1阶段」`0、1、2、3、9` 「2阶段」`5、4、7、6、8、10`
- 学习路线B-着急面试：`0 + (2)` |` (3)` |` (5+4) `|` (5+4+6)` | `(5+6+7)` |` (5+4+8) `| ...

**阶段成长**

- 第1阶段：OpenAI代码自动评审+(AI Agent 脚手架+场景应用/AIAgent智能体)2选1
- 第2阶段：OpenAI应用项目(公众号扫码登录、微信支付)+AI MCP网关（+WaLiAPI），时间充足可结合 API Gateway 业务网关
- 第3阶段：AI MCP Gateway + API网关结合、OpenAI应用+AI Agent +拼团/大营销结合
- 第4阶段：进阶到 WaLiSSH + WaLiCode 深入到 AI Agent 运行时设计实现 + WaLiAPI（知识库/Wiki）

> 接下来，小傅哥介绍下最近，最近更新的实战项目。小伙伴可以了解到，都可以掌握哪些东西。其他更多的内容，可以到小傅哥的博客了解 [bugstack.cn](https://bugstack.cn/)

## 一、AI Agent 通识教程 - 涨知识、刷八股、模拟面试

`Agent 架构与传统 LLM 链式调用有什么区别？` `什么是 ReAct 模式，底层工作原理是什么？` `多轮 Agent 对话怎么解决提示词和上下文采集和裁剪？` `Agent 工具调用的工具类型都有哪些，在长对话中，怎么保证 Agent 的工具调用的可靠性。` 死鬼，想转 Agent 应用开发工程师吗，这些问题你准备好了吗。别怕，本套通识教程，都为你把这些准备好了。在做项目前，可以先把 AI Agent Guide 好好刷下。

> 所有的这些内容，都来自于小傅哥在编写智能体实战项目时做的各类知识总结。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-llm-wiki-04.png" width="950px"/>
</div>

- 教程地址：[https://ai-agent-guide.xiaofuge.cn](https://ai-agent-guide.xiaofuge.cn/)
- 教程说明：这是一套从零到面试通关的 AI Agent Guide 通识教程，用可视化动画拆解复杂概念，循序渐进的由浅入深的帮助大家理解和掌握 Agent 智能体。并且每节内容，都覆盖了核心内容的讲解，八股文章的总结和课后面试问题的考察。除此之外，还附带了 AI 工具（免费的）帮助大家理解和学习整套课程。可以说这套教程，是当下最系统的中文 AI Agent 学习资源之一。

## 二、WaLiAPI - 本地网关 + 知识库（RAG&Wiki）

WaLiAPI 非常有意思，它是一个本地的 LLM 大模型网关。之所以做它是因为我想知道这些牛逼的智能体，在对话过程中都发了什么消息。通过这些消息再反向的推导出对智能体的设计，在这个过程，我学习到了 OpenClaw、Claude Code、Codex，等智能体的设计实现技巧。

这部分内容完事后，陆续完善 OpenAI、Anthropic、Ollama 协议转换，之后开始做安全日志审计（脱敏对话）、RAG 知识库、Wiki 文档（图谱），以及增加 MCP、Skills 对接。

💐 这一套项目，小傅哥做了3个语言版本，`Rust + Typescript`、`Java`、`Python` 都包括了。无论做桌面程序还是做服务端的，都可以。项目入口；[https://t.zsxq.com/jftYn](https://t.zsxq.com/jftYn)

>开源仓库：[https://github.com/fuzhengwei/WaLiAPI](https://github.com/fuzhengwei/WaLiAPI) - 可以在 Releases 中安装最新版体验，后续支持自动更新。该项目，还做成了课程教程，教大家完成这样一套 AI Infra 基础项目（还有一套 AI MCP Gateway 也是属于这类基础项目）

### 1. 支持语言

得益于 AI 加持，快速拆解出 WaLiAPI 的 Java、Python 版本，Java 版本遵循 DDD 架构设计实现。这样你可以在任何场景使用网关、知识库、LLM-Wiki 能力。

#### 1.1 Typescript

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-llm-wiki-14.png" width="350px"/>
</div>


#### 1.2 Java

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-llm-wiki-12.png" width="550px"/>
</div>


#### 1.3 Python

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-llm-wiki-13.png" width="350px"/>
</div>

### 2. 模型对接

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-llm-wiki-08.png" width="950px"/>
</div>

- 网关 LLM 使用，可以通过 API 接口，让其他应用对接。也可以直接在 `Codex`、`Claude Code`，软件应用下直接配置进去。
- WaLiAPI 配置的各类渠道模型，都可以转换为另外的模型，这样也省去了部分模型不支持其他协议，没法配置使用的情况。

### 3. 审计日志

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-llm-wiki-05.png" width="950px"/>
</div>
- 只要把网关 LLM 的接口配置到其他应用上，那么对话的时候，就可以把请求和响应的信息，都摘出出来，并标记处对话构成，对于这些内容的记录，是非常有利于分析智能体的执行的。
- 此外，还可以发现对话风险，以及在设置中做配置安全审计和脱敏。

### 4. RAG知识库

RAG（Retrieval-Augmented Generation，检索增强生成）是一种将**外部信息检索**与**大语言模型生成**相结合的技术方案。核心思路是"先查资料，再回答"——在生成回答前，先从外部知识库中检索相关文档片段，再将检索结果作为上下文输入给 LLM，由模型生成最终回答。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-llm-wiki-06.png" width="950px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-llm-wiki-07.png" width="950px"/>
</div>

1. **文档索引**：将原始文档分块 → 通过 Embedding 模型转为向量 → 存入向量数据库
2. **检索**：用户查询 → 向量化 → 在向量数据库中做语义相似度匹配 → 返回最相关的文档片段
3. **增强**：将检索结果与用户查询拼接为增强上下文
4. **生成**：LLM 基于增强上下文生成最终回答

### 5. LLM-Wiki

LLM-Wiki 是由 Andrej Karpathy 于 2026 年 4 月提出的一种**新型知识管理范式**。核心思路是：让大语言模型充当"知识编译器"，将零散的原始资料**预先编译**为结构化、可互联、可持续迭代的 Markdown 维基知识库，而非在查询时临时检索拼接。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-llm-wiki-09.png" width="950px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-llm-wiki-10.png" width="950px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-llm-wiki-11.png" width="950px"/>
</div>

1. **知识编译**：LLM 自动阅读原始资料，提炼核心观点，生成结构化 Markdown 页面，而非简单存储原文。
2. **自动互联**：LLM 自动识别概念间的关联，建立类似维基百科的交叉引用链接网络，支持跨主题探索。
3. **增量更新**：新文档加入时，只处理新内容，自动与现有知识整合，保持知识库一致性。
4. **矛盾标记**：发现信息不一致时，LLM 自动标注冲突观点，保持知识体系严谨。
5. **持久积累**：知识一旦被编译就永久保存，形成可积累、可演进的知识资产，无"每次重来"的浪费。
6. **架构极简**：纯文本文件实现知识存储与流转，无需向量数据库、Embedding 模型、重排序等复杂组件。

### 6. 技能使用

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-llm-wiki-16.png" width="850px"/>
</div>
WaLiAPI 提供了一套匹配的 Skills 技能，可以在 AI 工具里配置。这样就可以通过对话的方式使用，以端到端的方式使用知识库、Wiki 文档，这个设计还是非常不错的，可以极其方便的进行更新和读取使用。多人协议，也非常方便了。

> 在 Skills 里，拿到地址，配置到你的 AI 工具里就可以使用。

## 三、WaLiSSH/WaLiCode - AI Coding/DevOps 编码&运维

WaLiSSH 来自于 AI Agent 脚手架的使用，之后做深度扩展。WaLiCode 来自于 WaLiSSH 做桌面应用的扩展迭代，完成工具的扩充和UI的增强对接。达到可以辅助编码的能力。

WaLiSSH 已经更新完 UI、SSH 对接、最小 MVP 版本对接、ReAct 拆分、Prompt 提示词、Context 上下文的采集和裁剪，后续还有意图识别和 SFTP 的处理，就完结了。之后开始扩展工具迭代 WaLiCode 就把这部分项目做完了。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-llm-wiki-15.png" width="650px"/>
</div>

接下来，给大家分享一些项目进行中的演示和设计图；

### 1. 对接UI

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walissh/walissh-2-2-03.png" width="950px"/>
</div>

### 2. 智能体流程

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walissh/walissh-2-1-01.png" width="950px"/>
</div>

### 3. 提示词 + 上下文

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walissh/walissh-2-5-01.png" width="950px"/>
</div>

> 该项目有一个对外发布的产品，[https://walicode.xiaofuge.cn/](https://walicode.xiaofuge.cn/) 可以深度体验下。之后就知道课程版本可以学习到的内容了。
