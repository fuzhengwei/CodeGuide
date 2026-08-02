# 能爽用 Codex 啦，各类 LLM 都可以！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

今天给小伙伴们分享下，如何不受 LLM 模型限制的，任意 `Coding Plan` 的，**爽用 Codex**，让它可以整宿整宿的跑。我就喜欢这种`四无🐔🥚`的感觉！😁

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-0-00.png" width="150px">
</div>

**那怎么才能爽用呢？**

首先，这里有一个小障碍；Codex 使用的是 OpenAI 定义的最新通信协议 `v1/responses`，但市面上大部分提供的 LLM 套餐，都只是支持 `v1/chat/completions` 协议。所以这样的 LLM 模型即使通过工具，配置到 Codex 也没法使用。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-02.png" width="550px">
</div>

所以，小傅哥基于 OpenAI 官网 `v1/responses` 全套文档，设计实现了一套 WaLiAPI 软件的`协议转换`部分，可以自动化的把各类其他协议，都统一转换为 `v1/responses` 协议。这样 Codex 配置上就可以直接使用。—— `我已经爽用了很久，协议完全匹配。各类 tool 工具调用，也已经完成支持。`

之后，要想便宜爽用。其实最好搞一个 coding plan 论次数消耗的，这比任何 API 消耗次数的都划算。或者薅羊毛找免费的模型（platform.agnes-ai.cn），这类免费的也是一段时间就出来一个。一起都搞定了，就可以使用 Codex + WaLiAPI + LLM （套餐）爽玩起来了。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-01.png" width="950px">
</div>

**以前不舍得用，现在是站起来蹬！🚴🏻** 接下来，小傅哥就教你如何配置使用。这个过程一点也不复杂，跟着做就行。

## 一、软件安装

- Codex 官网：[https://openai.com/zh-Hans-CN/codex/](https://openai.com/zh-Hans-CN/codex/) 如果下载慢，可以用我的地址下载 [https://drive.weixin.qq.com/s?k=ACMA4AfQABUxw8Y0Sh](https://drive.weixin.qq.com/s?k=ACMA4AfQABUxw8Y0Sh) - 注意，Codex 安装后也会让跳浏览器登录。
- WaLiAPI 下载：[https://drive.weixin.qq.com/s?k=ACMA4AfQABU4S23jg8](https://drive.weixin.qq.com/s?k=ACMA4AfQABU4S23jg8) - `Windows 下载 WaLiAPI_0.1.4_x64-setup.exe，Mac M系列下载 WaLiAPI_0.1.4_aarch64.dmg`

> 2个软件下载后，直接点击安装即可。WaLiAPI 是开源的项目，可以访问；[https://github.com/fuzhengwei/WaLiAPI](https://github.com/fuzhengwei/WaLiAPI)

## 二、使用教程

### 1. WaLiAPI

#### 1.1 软件介绍

WaLiAPI 是一个本地运行的 LLM API 网关桌面软件。把 OpenAI、Claude、Gemini、DeepSeek、通义、智谱、Moonshot、豆包、Ollama 等不同供应商的 API 统一转换成 OpenAI/Anthropic 兼容协议，让你只需一个`本地地址`、`一个密钥`，就能在所有客户端里无缝切换和管理多家模型。此外，还内置知识库，以 Skills 方式使用，满足产研测协同，端到端开发。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-03.png" width="750px">
</div>

- 首先，【渠道】是配置 LLM 模型对接的，【密钥】是创建对接使用的 Key。这两项完成后，就可以在【使用】里，获得统一 API 接口，以及直接配置到 Codex、Claude Code 的一键操作
- 之后，【服务】是一套 RAG 非常强的知识库能力，可以通过 MCP、Skills 方式对接，让你的 AI 工具，可以自动化的完成文档、项目、代码等内容的总结，上传和使用。`非常适合团队共用`
- 然后，【日志】这是一个具备风险审计的能力，我们可以通过日志能力知道，大模型对话中到底发了什么，还能对敏感信息进行脱敏转发。像是小傅哥自己也开发各类 AI Agent 就非常需要知道像是 Codex 这样的一流 AI 产品是如何运行的，所以这个日志能力非常重要。
- 最后，其他的功能，如【设置】可以自己点点看。这套代码也是开源的，你可以直接拿到本地运行，也可以一起维护提交 PR 代码。

#### 1.2 渠道配置

渠道，是 LLM 大模型统一管理能力，各类的 LLM 都可以通过，`新建`或者`导入`的方式进行配置。配置后，本地的这套 WaLiAPI 网关，就可以统一负载所配置的渠道模型了。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-04.png" width="750px">
</div>

- 首先，创建渠道，需要填写 `名称`、`Base URL`、`APIKey`、`模型列表`，你的渠道支持什么模型，就配置什么模型。还可以增加映射，比如 auto -> 你的模型。这样做的目的是，一个 auto 模型，就可以自动的负载到其他模型。其实软件配置 WaLiAPI 的 auto 模型，就不用关心具体的模型是啥了，后面就由 WaLiAPI 来调配。`不非得叫 auto 也可以其他名称`
- 之后，WaLiAPI 还提供了导入能力，方便大家把之前的渠道导入进来，极大的方便了 LLM 大模型的维护使用。如果后续小伙伴还有其他渠道，也需要支持导入，可以在 WaLiAPI 开源仓库提交 issue 或者贡献 PR 代码合并。

> 如果自己还没有免费LLM，可以用这个 [https://agnes-ai.com](https://agnes-ai.com) 如果想弄个 Coding Plan 猛蹬的(glm-5.2)，可以用这个 [https://taotoken.net/coding-plan?u=inv_q0gpzkk9099e94lz&utm_source=tt_invite](https://taotoken.net/coding-plan?u=inv_q0gpzkk9099e94lz&utm_source=tt_invite) - 没那么快，就是抗用。

#### 1.3 使用配置（Codex）

##### 1.3.1 API 使用

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-05.png" width="850px">
</div>

LLM 使用，第一种是 API 方式，无论你配置什么模型，都可以转换为如图三种协议方式（`chat/completions`、`responses`、`messages`）。你的各类软件，都可以通过 WaLiAPI 统一渠道进行管理。

##### 1.3.2 Codex 配置

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-06.png" width="850px">
</div>

- 首先，在你已经安装了 `Codex`、`Claude Code` 以后，打开 WaLiAPI 会识别到配置文件，选择 APIKey、Model 就可以，之后就可以点击【写入网关配置】。
- 注意，每次写入后可以重启下 `Codex` 软件，这样重启后，可以刷新最新配置进来。之后就可以使用 Codex 软件了。

##### 1.3.3 Codex 使用

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-07.png" width="850px">
</div>

- 配置后，就可以在 Codex 进行对话了。非常方便，想干啥直接问就行，用着用着就学会了。

##### 1.3.4 日志查看

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-08.png" width="850px">
</div>

- 在 WaLiAPI 可以看到你所有请求的对话，`耗时`、`消耗`、`请求`、`应答`，等信息。这样有什么失败了，超时了，都可以看到。
- 如果你是 AI Agent 开发工程师，那么对于`请求`、`响应`，就可以各类日志信息，分析后对自己的智能体设计是非常有帮助的。

> 到这，就够你先爽快的使用 Codex 了。

#### 1.4 知识库

WaLiAPI 提供了一套端到端知识库，它是干啥的呢？🤔

产、研、测，也可以就只是我们自己一个人，分饰多个角色。在我们日常使用 AI 完成功能开发的时候，总是会需要把一些，设计信息、实现的架构、核心的代码、测试的用例，以及让我们涉及的软件参考某个开源项目代码库等，把他们放到一个共用的地方，让所有的 AI 软件在各类对话中，都能拿到信息。

那么，就这里需要一套统一的知识库，我们可以在各类 AI 对话中 + Skills（MCP + RAG），自动化的完成知识库的维护和使用。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-09.png" width="850px">
</div>

该套知识库，提供了企业级（文本向量化、HNSW 索引、**AST（抽象语法树）**等）RAG 能力，并附带的做了 MCP 服务和 Skills 技能，让任意 AI 软件，都可以通过 Skills 技能的配置完成对话的操作。

##### 1.4.1 上传

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-11.png" width="850px">
</div>
- 文档，支持 md、txt、pdf 等各类文件，转换为知识库使用。
- 来源，可以上传 Git 仓库地址，URL 地址等。非常方便使用。

##### 1.4.2 索引

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-10.png" width="850px">
</div>
- 检索，可以测试关键词的匹配度，命中的知识库信息。
- 问题，这个下是 AI 直接对话，可以把识别的对话信息进行 AI 操作。是一个测试行为。

##### 1.4.3 技能（Skills）

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-12.png" width="850px">
</div>

- 本身知识库提供了 MCP 服务，又在这个基础上，提供了 Skills 技能。这样在 AI 里直接对话的方式配置使用就可以了。

##### 1.4.4 配置（AI 自动）

**安装技能**

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-13.png" width="650px">
</div>


- 首先，你可以把 `https://github.com/fuzhengwei/waliapi-rag-skills` 技能下载到本地，也可以直接让 AI 把 github 地址的 skills 技能配置到软件。但如果网速不好，最好是下载到本地。
- 之后，AI 软件会自动识别，并把只能安装到本地，之后你就可以做一系列提问了。

**上传知识**

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-15.png" width="650px">
</div>

- 你可以把 AI 对代码的分析，或则开发的功能，上传到知识库。这样后续的新对话，也可以让根据知识库信息来处理。

**查看知识**

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-14.png" width="650px">
</div>

- 接下来，你就可以问 ai 让检查知识库了。这样你就可以看到有哪些知识库。

### 2. Codex

这里小傅哥给大家分享下基本的入门使用。方便还没怎么用过 Codex 的伙伴，也可以快速上手。

#### 2.1 首页功能

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-16.png" width="950px">
</div>

- 项目，你可以选择本地工程或者文件夹，这样对话的时候，默认就以你打开的项目为基本信息。
- 宠物，可以动态的提醒一些执行信息。比如 Codex 缩小了，不在当前页了，也可以知道执行信息。
- 右侧，可以打开，终端、浏览器、文件、审阅、侧边任务等各类信息，可以辅助来操作。

#### 2.2 对话插件

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-17.png" width="950px">
</div>

- 对话中，还有很多插件，你也可以按需选择使用。如，读取文档、PDF，如果是浏览器插件，需要能访问 Google 插件市场。
- 此外，在对话的时候，你可以继续补充发消息，可以引用使用。

## 三、其他软件

如果你折腾 Codex 不方便，这里小傅哥在推荐你个 WaLiCode [https://walicode.xiaofuge.cn/](https://walicode.xiaofuge.cn/)

<div align="center">
    <img src="https://bugstack.cn/images/article/project/waliapi/waliapi-codex-18.png" width="950px">
</div>
- WaLiCode 是一款 AI IDE 工具，可以对话，编码，绘图，还可以连接服务器，非常方便使用。
- 而且支持你配置各类模型，非常方便的配置使用。可以说这是一款简单的，好用的，也不用折腾的工具。

## 四、学习成长

如果你想在 AI 上有成长，那么可以看下如下这套教程；

这里，小傅哥为伙伴们推荐一套 AI 从通识、应用、项目，全套流程路线。你可以刷到 AI 八股，也可以学会 AI VibeCoding 编程，还能实践各类 AI 项目。如，市面的 AI IDE（walissh、walicode）教你做一套市面上的 trae.ai/qcoder 一样的编程工具。WaLiAPI（LLM 负载、日志审计、RAG 知识库）、AI MCP Gateway 教你如何构建 AI Infra 基础设施。

这里的所有内容，所有的项目，都从 [bugstack.cn](https://bugstack.cn/)实战项目进入学习；

![img](https://bugstack.cn/images/article/project/ai-agent-scaffold/part-1/1-1/images/ai-agent-scaffold-1-1-10.png)

- 首先，小白推荐先进入 AI Agent Guide 基础认知教程：[ai-agent-guide.xiaofuge.cn](https://ai-agent-guide.xiaofuge.cn/)- 涵盖基础概念、八股、面试内容。
- 之后，如果没有使用过 AI IDE 工具，可以做下 AI 新范式，通过 AI 实践来锻炼。
- 最后，可以通过项目驱动学习，结果导向的项目实战，可以更好的锻炼 AI 技能，也是为转岗到 AI Agent 应用开发工程师做准备。

**学习建议**

- 学习路线A-完整进阶：0 - 认知和实践、「1阶段」0、1、2、3、9 「2阶段」5、4、7、6、7、8、10
- 学习路线B-着急面试：0 + (2) | (3) | (5+4) | (5+4+6) | (5+6+7) | (5+4+8) | ...

**阶段成长**

- 第1阶段：OpenAI代码自动评审+(AI Agent 脚手架+场景应用/AIAgent智能体)2选1
- 第2阶段：OpenAI应用项目(公众号扫码登录、微信支付)+AI MCP网关（+WaLiAPI），时间充足可结合 API Gateway 业务网关
- 第3阶段：AI MCP Gateway+API网关结合、OpenAI应用+AI Agent +拼团/大营销结合
- 第4阶段：进阶到 WaLiSSH + WaLiCode 深入到 AI Agent 运行时设计实现 + WaLiAPI（知识库）

> 以上，所有内容，加入星球「[码农会锁](https://wx.zsxq.com/group/48411118851818)」都可以学习到，此外还有其他非常多的内容，都可以获取。
