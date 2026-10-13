---
title: ToyAgent
lock: need
---

# 一套 ToyAgent，教小白上手 AI Agent 开发！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

刷到不少小红书🍠伙伴吐槽；**“白付费给xxx了，整了个 Toy 🎮 项目。🤨 ”** 。评论区也有人建议，直接去 Github 看开源项目，`Claude Code`、`OpenCode` 不是一堆。但实际整下来，Toy 的不够用，源码的看不懂，根本看不懂。`鲜族人过年,要了狗命了。`

讲良心，一个小白，刚接触 Agent。上来就整 `Claude Code` 真相当于小本毕业🎓撸`钱老`的《工程控制论》！—— 字/码，都摆在那，但就不往脑子里进呀！

<div align="center">      
	<img src="https://bugstack.cn/images/article/ai/toy-agent-01.gif" width="150px"/>      
</div>

所以，就学习来说，你还真得需要一个 Toy 🎮  项目。但 Toy 的，得 Toy 的彻底点。`1，不能搞的太大，你没那多时间呀`、`2，上手得快`、`3，就是必须保证学完不能白学`

<div align="center">      
	<img src="https://bugstack.cn/images/article/ai/toy-agent-02.png" width="150px"/>      
</div>

然后，[ToyAgent](https://ai-agent-guide.xiaofuge.cn/toy-agent) 这套玩具项目来啦！一个接口类，就是一个最小 MVP 智能体。用 26个场景，全体量覆盖智能体开发。包括；`提示词工程`、`ReAct 循环`、`工具调用`、`记忆系统`、`子代理`等。

<div align="center">      
	<img src="https://bugstack.cn/images/article/ai/toy-agent-03.png" width="950px"/>      
</div>

- 官网：<https://ai-agent-guide.xiaofuge.cn/toy-agent/>
- 源码：<https://github.com/fuzhengwei/ToyAgent>

左侧是目录，中间是对话（每一节最小 MVP 版本智能体），右侧是对应章节的文章讲解。学习的时候，也可以点击文章对应的 `Github` 仓库进入到这一节的代码学习。以上这些案例，来自于小傅哥在开发 [`walicode`](https://t.zsxq.com/GQ4ae)、[`dsh java`](https://t.zsxq.com/kYcVt) 等智能体项目，积累的开发经验。所编写的每一节最小运行版本，这样你可以循序渐进的理解到一个 Agent 开发都需要什么。—— 每一个章节都是一个小玩具，但所有玩具拼装起来，就是一个最完整智能体解决方案。

> 接下来，小傅哥就来介绍下这套内容，方便小伙伴学习。此外，文末也提供了小傅哥开发的全部 AI Agent 项目源码。

## 一、🎮 玩具介绍

其实说白了，智能体的灵魂只有一个方法；

```java
public interface Agent {
    String chat(String input);   // 输入一句话，输出一句话
}
```

围绕这个方法，增加了各类技术手段，让 Agent 具备处理用户诉求的能力。但教学来说，为了让整个过程不被复杂化，且容易被理解和掌握。小傅哥这里让一个接口类，负责体现一个智能体知识点。所以，整个这套内容，你刷起来会特别轻松。

### 1. 对话使用

<div align="center">      
	<img src="https://bugstack.cn/images/article/ai/toy-agent-04.png" width="950px"/>      
</div>

- 首先，左上角有一个设置按钮，点击后可以配置对话模型。（不配置会走官网的或者 Mock 数据）
- 之后，你可以选择任意一节进行对话，每一个章节都是一个对应知识点体现。同时，对话中会把 Agent 执行过程展示出来，这能非常方便的你来理解 Agent 操作。
- 此外，右侧还有`原理文章`，是对应当前这一节的内容讲解。这可以辅助你来学习。上面还有 Github 对应这一节的源码可以点击查看。

### 2. 章节预览

| #  | 场景            | 接口类                 | 新增能力                                               | 对应教程章节       |
| -- | ------------- | ------------------- | -------------------------------------------------- | ------------ |
| 01 | 对话智能体         | `ChatAgent`         | 最小 MVP：接通模型                                        | ch01-03      |
| 02 | 提示词工程         | `PromptAgent`       | 系统提示词三层结构                                          | ch04         |
| 03 | ReAct 循环      | `ReActAgent`        | 思考-行动-观察                                           | ch04b/05     |
| 04 | 工具调用          | `ToolCallAgent`     | Function Calling                                   | ch03/10      |
| 05 | 记忆系统          | `MemoryAgent`       | 多轮记忆 + 上下文压缩                                       | ch06         |
| 06 | 意图路由          | `RouterAgent`       | 意图识别 + 决策中枢                                        | ch06b/07     |
| 09 | MCP 协议        | `McpAgent`          | 工具标准化接口                                            | ch07b/11     |
| 10 | 技能编排          | `SkillAgent`        | Skills L0/L1/L2                                    | ch08/12      |
| 07 | RAG           | `RagAgent`          | 检索增强生成                                             | ch16/20      |
| 12 | 多智能体          | `MultiAgent`        | 规划-执行-审查协作                                         | ch10/14      |
| 15 | Loop 运行时      | `LoopAgent`         | 守卫 + 保险丝 + 兜底                                      | ch08b/18/21  |
| 16 | 工作流状态机        | `WorkflowAgent`     | LangGraph 思想编排                                     | ch11b/15     |
| 18 | 全流程智能体        | `FullAgent`         | 守卫+记忆+ReAct+工具+保险丝 综合链路                            | 综合 Step01-17 |
| 08 | LLM-Wiki 知识编译 | `WikiAgent`         | 知识编译一次、持久维护、增量沉淀                                   | ch13/26      |
| 11 | 工具注册表         | `RegistryAgent`     | ToolDefinition 协议：注册/发现/热注销（disposer）              | dsh-java     |
| 17 | ReAct 运行时     | `RuntimeAgent`      | turn/step 两级循环 + 上下文裁剪 + TurnEndReason             | dsh-java     |
| 19 | 人工介入          | `AskAgent`          | ask_user_question：提问挂起 → 人工答复续跑                    | dsh-java     |
| 20 | 审批门禁          | `ApprovalAgent`     | 风险分级 + 审批挂起 + 会话免审 + 超时默认 DENY                     | dsh-java     |
| 21 | 沙箱纵深防御        | `SandboxAgent`      | 策略/黑名单/边界/规范化 四层拦截                                 | dsh-java     |
| 22 | 事件溯源          | `EventSourcedAgent` | JSONL 事件流 append-only + 回放投影重建状态                   | dsh-java     |
| 23 | 插件机制          | `PluginAgent`       | AgentPlugin 契约 + 隔离 ClassLoader 加载 + 工具桥接批量注册/注销   | dsh-java     |
| 25 | CLI 智能体       | `CliAgent`          | 终端 REPL + -p 单命令模式 + 沙箱 shell 执行器（复用 Step21 四层防御）  | 教程 ch18      |
| 13 | 子代理           | `SubagentAgent`     | dispatch_subagent：spawn 全新上下文 / fork 继承父会话，主代理动态派遣 | dsh-java     |
| 24 | Hooks 钩子      | `HooksAgent`        | before 拦截/改写参数 · after 脱敏/审计，挂钩即生效摘钩即消失            | dsh-java     |
| 14 | A2A 协作        | `A2AAgent`          | Agent Card 名片发现 + task_id 信封 + 异步回执，跨代理标准化协作       | dsh-java     |
| 26 | 定时工具          | `ScheduleAgent`     | 一次性/循环调度，触发结果 append 到事件日志可回看                      | dsh-java     |

每个场景一个文件，位于 `src/main/java/cn/xiaofuge/ai/agent/stepXX/`，接口注释即教程，嵌套实现类即全部逻辑。

### 3. 项目工程

如图，26个实战场景案例；

<div align="center">      
	<img src="https://bugstack.cn/images/article/ai/toy-agent-05.png" width="850px"/>      
</div>

- 工程地址：<https://github.com/fuzhengwei/ToyAgent>
- 项目说明：每一节源码都很轻量，直击核心。可以启动后在 web 访问，也可以编写单测在本地验证。

<div align="center">      
    <img src="https://bugstack.cn/images/article/ai/ai-agent-guide-wx-04.png" width="650px">      
</div>

> [https://ai-agent-guide.xiaofuge.cn](https://ai-agent-guide.xiaofuge.cn/)  是小傅哥做的一整套 **AI Agent Guide - 通识教程**，而这套案例，就是配和同时教程所提供的。这套内容，除了 web 版本，也提供了一套小程序版本，你可以扫码使用。

## 二、🚀 实战项目

玩具🎮，玩的热血沸腾了，接下来就是干点正式的，有点难度的，AI Agent 智能体项目了。这是一整套完整的学习路线，循序渐进，有紧有收的。

### 1. 学习路线

小傅哥为伙伴们推荐一套 AI 从通识、应用、项目，全套流程路线。你可以刷到 AI 八股，也可以学会 AI VibeCoding 编程，还能实践各类 AI 项目。如，市面的 AI IDE（walissh、walicode、walibuddy「dsh java desktop」）教你做一套市面上的 trae.ai/qcoder/workbuddy 一样的编程工具。WaLiAPI（LLM 负载、日志审计、RAG 知识库）、AI MCP Gateway 教你如何构建 AI Infra 基础设施。

这里的所有内容，所有的项目，都从 [bugstack.cn ](https://bugstack.cn/) 实战项目进入学习；

<div align="center">      
    <img src="https://bugstack.cn/images/article/project/ai-agent-scaffold/part-1/1-1/images/ai-agent-scaffold-1-1-10.png" width="850px">      
</div>

- 首先，小白推荐先进入 AI Agent Guide 基础认知教程：[ai-agent-guide.xiaofuge.cn](https://ai-agent-guide.xiaofuge.cn/)- 涵盖基础概念、八股、面试内容。
- 之后，如果没有使用过 AI IDE 工具，可以做下 AI 新范式，通过 AI 实践来锻炼。
- 最后，可以通过项目驱动学习，结果导向的项目实战，可以更好的锻炼 AI 技能，也是为转岗到 AI Agent 应用开发工程师做准备。

**学习建议**

- 学习路线A-完整进阶：0 - 认知和实践、「1阶段」0、1、2、3、9 「2阶段」5、4、7、6、7、8、10
- 学习路线B-着急面试：0 + (2) | (3) | (5+4) | (5+4+6) | (5+6+7) | (5+4+8) |（12 + 15 + 4）等。

**阶段成长**

- 第1阶段：OpenAI代码自动评审+(AI Agent 脚手架+场景应用/AIAgent智能体)2选1
- 第2阶段：OpenAI应用项目(公众号扫码登录、微信支付)+AI MCP网关（+WaLiAPI），时间充足可结合 API Gateway 业务网关
- 第3阶段：AI MCP Gateway+API网关结合、OpenAI应用+AI Agent +拼团/大营销结合
- 第4阶段：进阶到 WaLiSSH + WaLiCode 深入到 AI Agent 运行时设计实现 + WaLiAPI（知识库）
- 第5阶段：DSH Java 深度学习，Web版、桌面版、插件运用。

### 2. 展示项目

#### 2.1 DSH Java Desktop

《DeepSeek Harness Java》—— 基于 Deepseek Harness 源码设计，使用 Java + DDD，进行 1:1 复刻（Java 版本。领域驱动设计，更容易看懂）。

<div align="center">      
    <img src="https://bugstack.cn/images/article/project/deepseek-harness-java/dsh-java-desktop-02.png" width="850px">      
</div>

- 项目：<https://t.zsxq.com/kYcVt>
- 介绍：

Deepseek Harness 插件化的设计思维，类似于 AI Agent 脚手架。同类思想的目标在于快速的在一些业务场景完成智能体的设计实现。在 AI 时代，这类设计思想很重要。所以考虑设计 Deepseek Harness Java 版本，通过 ClassLoader 加载 Jar 以及在内部直接继承 type 层来实现插件扩展。当前正在拆分领域模块；

所以，该项目的核心价值在于企业通用解决方案（也就是 deepseek harness 的能力），编写简历可以着重体现这方面内容。之所以做 java 版本，一方面是企业里有此类场景诉求，另外一方面是个人具备这样的技术储备，可以在各类场景，使用各类语言，进行快速迭代智能体应用。

#### 2.2 AI Coding/DevOps

《AI Coding/DevOps 可持续交付助手》 - WaLiCode + WaLiSSH 让 AI 既可以辅助编码，又可以运维云服务器！

<div align="center">      
    <img src="https://bugstack.cn/images/article/project/walissh/product-walissh-user-guide-04.png" width="850px">      
</div>

<div align="center">      
    <img src="https://bugstack.cn/images/article/project/walicode/walicode-0-1-2.png" width="850px">      
</div>

- 项目：<https://t.zsxq.com/GQ4ae>
- 介绍：

该项目，以 WaLiCode 的架构设计为内核标准，分拆2套工程（walissh、walicode）讲解。项目使用 Spring AI + Google ADK + Tauri（+Typescript），结合运行时智能体设计，涵盖；`Agent WorkFlow`、`上下文管理（对话追踪、搜索、提示词）`、`意图识别（+LLM）`、`混合窗口裁剪`、`动态提示词（ Prompt 组装器、里程碑追踪）`等，核心设计，构建一整套 AI Agent 智能体服务工具。

用途呢，用于辅助编码，用于辅助运维。除了基本的 AI Coding、SSH +Sftp 能力外，你可以为项目工程，扩展添加 mcp、skills 技能等，为你的服务器做智能化运维服务管理。如；`日志分析`、`系统健康度巡检`、`内存泄漏排查`、`复杂项目链路耗时处理`、`项目压测优化建议等等场景`。这个工具，就是你穿插在云服务器上的智能机器人。现在它已经可以替换我电脑上的其他 SSH 工具了，嘎嘎好用！

说白了，AI Agent 要解决的就是一整条研发交付路径服务，包括；研发设计（spec规约）、工程编码（WaLiCode）、项目上线/运维（WaLiSSH）等，而本次的项目，其实完全可以在学习后，扩展出Coding能力。

#### 2.4 OpenAI 应用

《OpenAi(ChatGPT/ChatGLM) 微服务应用体系构建》- API-SDK、鉴权、公众号、支付交易。

<div align="center">      
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-230905-03.png" width="850px">      
</div>

- 项目：<https://t.zsxq.com/kEhGG>
- 介绍：此项目不只有ChatGPT还有ChatGLM，主要以围绕类似 OpenAi 生成式服务，构建微服务应用架构体系组件。包括；用户鉴权、公众号、微信支付等，满足不同诉求的使用。并以模块化设计，积木式构建应用，让不同的场景诉求都可以配置化对接。让大家既能锻炼技术，又能有场景验证。

#### 2.5 AI MCP Gateway

《AI MCP Gateway 网关服务系统》 - 以解决各类业务接口(http/rpc)便捷转换为MCP协议接口而设计实现

<div align="center">      
    <img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-4-1-06.png" width="850px">      
</div>

- 项目：<https://t.zsxq.com/NDWBI>
- 介绍：本项目是 AI Agent 智能体，关于 MCP 协议对接的通用网关服务项目，以解决各类业务接口便捷转换为 MCP 协议而设计实现。通过这样的配置，可以大大的简化从普通http、rpc接口到 MCP 协议的转换操作。这样的项目，也是每个互联网公司在做 AI Agent 智能体时，必备的基础设施项目。

#### 2.6 Auto Mobile

《AI Agent 脚手架 + 场景应用》- 综合 Spring AI、LangChain4j + Google ADK（a2a、mcp、skills），打造全新智能体架构方案。

<div align="center">      
    <img src="https://bugstack.cn/images/article/project/ai-agent-scaffold/part-5/5-9/images/ai-agent-scaffold-5-9-03.png" width="850px">      
</div>

- 项目：<https://t.zsxq.com/UIlv5>
- 介绍：本套 AI Agent 综合智能体脚手架 项目，主要为降低智能体开发门槛、提升业务系统集成效率而构建。项目涵盖了：工程脚手架自动生成、智能工作流编排、多模态交互（文本/绘图/动作） ，以及手机自动化网关控制等核心功能和场景方案。
