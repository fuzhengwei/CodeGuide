---
title: ToyHarness
lock: need
---

# 根据 Anthropic/Codex 官网资料，开发一套 ToyHarness 工程！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术up主小傅哥。

2026年，是 AI Agent 开发爆发的一年，也是 AI Agent 开发岗位突增的一年。那你上车了吗？🚌 面试久了，其实几句话就能聊出来；你是否会做 AI Agent！

<div align="center">
    <img src="https://bugstack.cn/images/article/ai/toyharness-01.gif" width="150px">
</div>

我下手面试，其实几句话就能知道，你真的干没干过 Agent 开发；`裁剪的目的是为了节省对话 Token 吗？`、`上下文是边际收益递减的有限资源吗？`、`你了解，每塞进去一个低信号 Token，都会分摊注意力成本，导致Transformer 的 O(n²) 被摊薄的零和竞争吗？`、`思考、派生子代理、向用户提问、记忆、计划、找工具，你是设计为有效的内部机制还是简单的化的设计为工具。答案是工具！`。

> "They are typically just **LLMs using tools based on environmental feedback in a loop**."
> 

Anthropic 的 [《Building effective agents》](#)，跟几十个团队合作后的开头第一段就写着：表现最好的 Agent 实现，**没有一个用复杂框架或专用库**。他们用的都是简单、可组合的模式。

说白了，Agent 的开发，等于 = 模型在循环里根据环境反馈调用工具。**几行代码而已。**🤨

我多次迭代 Agent  Harness 开发时最深的体会也是这个：所谓 26 个内置工具、子代理、提问、记忆、做计划……扒开看全是**一张不断变长的工具清单 + 一个循环**。连"思考"本身，Anthropic 都把它做成了 `think` 工具——对模型而言，"决定"的唯一出口就是调工具。

再说白一点：**LLM 是奔着 AGI 发展的，Agent 开发要做的，只是给它提供最基本的单元元素**——工具、上下文、环境反馈。你不需要串联，你不需要写逻辑，一切交给 LLM 动态决策和执行就好。都抢棍棒、斧钺钩叉、包子油条、盒子麻花，工程师不再需要 Dify、Coze 硬编码流程，而是只提供基础的，一切由 LLM 自行决定。

**所以**，鉴于很多伙伴对 Agent 开发还尚不熟悉，小傅哥为了准备了一套 ToyHarness 实战工程项目（文档 + 工程）；`1000行` LLM对接、`2000行` Harness 骨架 + Tool 策略（你可以持续迭代加工具）+ 前端 UI 对接，交付出一套 ToyHarness 马具工程。

> 这些内容，来自于小傅哥，阅读了大量的 Codex、Anthropic 关于 Agent 的设计实现方案，以及经过多轮、多类的 Agent 开发后。为你总结和实现的最容易上车学会的 Agent 项目。文末提供了项目的获取地址 💐。

## 一、项目介绍

ToyHarness 是一套**把 Agent 拆开给你看**的实战教学工程：`1000 行` LLM 对接 + `2000 行` Harness 骨架 + Tool 策略 + 前端 UI，没有任何黑盒。你在这里学到的不是"怎么调框架"，而是 Claude Code、Codex 这些顶级 Agent 产品最底层的运行原理——**一张不断变长的工具清单 + 一个循环**。代码即课程，每一行都可以被你读懂、改造、持续迭代加工具。

<div align="center">
    <img src="images/toyharness-02.png" width="900px">
</div>

**这套🐴 Harness 工程亮点：**

- 🔁 **最小 Agent Loop**：以第一梯队的 Anthropic 官网设计资料为准；模型在循环里根据环境反馈调用工具，几行代码看透 Agent 本质。
- 🛠️ **26 个内置工具策略**：策略模式构建，便于快速迭代工具；run_command、fs_read 等工具全量开源，可持续迭代加自己的工具。
- 📊 **执行全程可视**：前端执行可视化的时序图；每轮调用的时序图（SEQUENCE）实时渲染，Agent 的每一步"思考"和"动手"都看得见。
- 🧠 **上下文工程**：压缩、阈值触发、窗口回合一目了然，亲眼看 Token 如何被省下来（截图中一次压缩省下 4.3K tokens）。
- 💬 **多会话管理**：像 ChatGPT/Claude Code  一样管理多个对话，会话记忆持久化。
- ⚙️ **模型可插拔**：推理强度、模型配置随时切换，对接任意 OpenAI 协议的 LLM。

> 上面这张图就是 ToyHarness 的完整运行界面：左侧会话列表、中间 Agent 执行对话、右侧执行时序与上下文管理面板——**执行全程可见，这样是非常适合伙伴们上手学习的。

## 二、快速体验

### 1. 启动应用

ToyHarness 是一个**零依赖**的 Maven 工程（运行时不是 Spring Boot，用的是 JDK 自带 HttpServer），所以上手成本极低：装好 JDK 和 IDEA，克隆下来就能跑。整个流程就 5 步：**装环境 → 克隆工程 → IDEA 打开 → 配置模型 → 启动访问**。

#### 1.1 环境要求

| 环境 | 版本要求 | 说明 |
|------|---------|------|
| JDK | 17+ | 工程按 `maven.compiler.release=17` 编译，LTS 版本即可 |
| IntelliJ IDEA | 2023+ | 社区版（Community）就够用，不需要 Ultimate |
| Maven | 3.8+ | IDEA 内置的 Maven 也可以，无需单独安装 |
| Git | 任意版本 | 用于克隆工程 |
| 模型服务 | 可选 | 任意 OpenAI 协议兼容的 LLM（GLM、DeepSeek、通义、本地 Ollama 等都可以） |

> 💡 **没有模型 Key 也能跑**：ToyHarness 内置了 Mock 模型，检测到未配置 api-key 时会自动降级到 Mock 模式，开箱即跑——先把 Agent Loop、时序图、工具调用的可视化跑起来看个明白，再接真实模型。

#### 1.2 下载项目

打开终端（Terminal），执行：

```bash
git clone git@gitcode.net:KnowledgePlanet/toyharness.git
```

克隆完成后，工程目录结构如下（你只需要关心 `src` 和 `pom.xml`）：

```
toyharness
├── pom.xml                                        # Maven 配置（零依赖，只有日志 starter）
├── skills/                                        # 技能包目录（SKILL.md，编辑后重启生效）
└── src/main
    ├── java/cn/xiaofuge/ai
    │   ├── Application.java                       # 启动入口（main 方法在这）
    │   ├── core/                                  # Harness 骨架 + 26 个工具策略
    │   ├── llm/                                   # LLM 对接（OpenAI 协议）
    │   └── mcp/                                   # MCP 客户端
    └── resources
        ├── application.yml                        # 服务端口 + 模型配置
        └── web/index.html                         # 前端页面（随服务启动托管）
```

> 📌 如果你没有配置过 GitCode 的 SSH 公钥，可以改用 HTTPS 地址：`git clone https://gitcode.net/KnowledgePlanet/toyharness.git`

#### 1.3 使用 IntelliJ IDEA 打开

1. 打开 IDEA，选择 `File → Open...`；
2. 选中克隆下来的 `toyharness` 工程根目录（就是有 `pom.xml` 的那一层），点 `OK`；
3. 首次打开 IDEA 会自动识别 Maven 工程并下载依赖——因为**几乎零依赖**，几秒就完成；
4. 确认 JDK：`File → Project Structure → Project`，把 SDK 设置为 JDK 17+。

#### 1.4 配置模型

模型配置在 `src/main/resources/application.yml`，就 4 个字段：

```yaml
model:
  base-url: https://open.bigmodel.cn/api/paas/v4   # OpenAI 兼容接口根地址
  endpoint: /chat/completions                      # 端点路径，默认不变
  api-key: sk-xxxx                                 # 你的模型 API Key
  model-name: glm-4.7                              # 模型名称
```

字段说明：

- **base-url**：OpenAI 协议兼容接口的根地址，`/v1` 这类版本前缀写在结尾（工程会自动拼接端点路径）。比如智谱 `https://open.bigmodel.cn/api/paas/v4`、DeepSeek `https://api.deepseek.com/v1`；
- **endpoint**：拼接在 base-url 后的端点路径，默认 `/chat/completions` 不用改；
- **api-key**：模型服务提供的密钥。**留空则自动使用 Mock 模型**（免鉴权网关也支持留空）；
- **model-name**：要调用的模型名称。

> ⚙️ 除了改配置文件，工程启动后也可以在页面右上角的**模型配置菜单**里动态切换模型、推理强度、多配置档案，页面修改会自动回写到 `application.yml`，下次启动直接生效。

#### 1.5 启动项目

方式一（推荐）：在 IDEA 中找到启动类 `cn.xiaofuge.ai.Application`，右键 `Run 'Application'`，看到控制台输出：

```java
已启动: http://127.0.0.1:8090
模型: OpenAI(base-url=..., model=glm-4.7)
页面资源: classpath:web（随 jar 启动）· 技能包: /path/to/skills
数据目录: ~/.toyharness/mac/data（会话与日志都持久化在这里）
```

方式二（命令行）：在工程根目录执行 `mvn compile exec:java` 直接启动。

方式三（打包运行）：`mvn clean package` 打出可执行 fat-jar，然后任意目录 `java -jar target/toy-harness.jar` 启动。

#### 1.6 访问地址

浏览器打开：**http://127.0.0.1:8090**

你会看到 ToyHarness 的完整运行界面：

- **左侧**：会话列表，像 ChatGPT 一样新建/切换/删除多个对话，会话记忆持久化，重启不丢；
- **中间**：Agent 执行对话区，打字发送即可开启 Agent Loop——模型思考、调工具、给结果全程流式可见；
- **右侧**：执行时序图（SEQUENCE）与上下文管理面板，每轮调用了什么工具、Token 如何被压缩节省，一目了然。

> 🚀 **上手试试**：在对话框输入"帮我看看当前目录有哪些文件"或"现在几点了，再算一下 365 * 24 * 3600"，你就能在右侧时序图里亲眼看到模型如何决定调用 `fs_grep`、`get_time`、`calculator` 这些工具——这就是 Agent 最本质的样子：**一张不断变长的工具清单 + 一个循环**。

### 2. 体验功能

启动后浏览器访问 `http://127.0.0.1:8090`，你会看到一个三栏界面：**左侧**多会话列表、**中间** Agent 执行对话区、**右侧**执行观测面板。下面用两组真实运行的截图，带你把 Agent 的核心体验走一遍（本次演示对接的模型是 `glm-5.3-flash`，你用任意 OpenAI 协议模型都可以跑出同样的效果）。

#### 2.1 简单测试

在中间对话框输入一句话：**"现在几点了？顺便帮我算一下 365 * 24 * 3600 等于多少"**，回车发送。

你会看到模型并没有直接硬算，而是自主决定先调用 `get_time` 工具查当前时间、再调用 `calculator` 工具算表达式——**两轮循环**之后给出最终回答。中间对话区用卡片展示了每一轮的工具调用（`get_time`、`calculator`，执行完标记 `done`），底部还有本次回答的 Token 统计、耗时和所用模型。

<div align="center">
    <img src="https://bugstack.cn/images/article/ai/toyharness-03.png" width="900px">
</div>

与此同时，右侧的执行时序图（SEQUENCE）正在**实时画动线**：`用户 → Harness → 模型 → 工具` 四条生命线上，实线是调用、虚线是回传/观察，`365*24*3600` 的参数和回填结果都标注在动线上，右下角 `loop:2` 说明这次 Agent Loop 一共循环了 2 轮——这就是 Agent 最本质的样子：**模型在循环里根据环境反馈调用工具**。

#### 2.2 右侧功能

ToyHarness 最大的特点是**执行全程可见**。右侧面板从上到下有三张核心卡片：

- **执行时序 · SEQUENCE**：四条生命线的实时动线图，Agent 的每一步"思考"（调模型）和"动手"（调工具）都看得见，守卫拦截会画红色动线；
- **上下文构成 · CONTEXT（窗口拆账）**：本回合真正发给模型的上下文窗口构成——截图里一次调用共 2.6K tokens，其中工具定义占了 2.3K（89%）。这就是为什么上下文是"边际收益递减的有限资源"，也是后面上下文裁剪课程的价值所在；
- **上下文压缩 · COMPACTION（执行算法）**：压缩阈值（16 条）、保留最近回合（6 条）、摘要算法（一次 LLM 调用）一目了然，历史水位进度条让你亲眼看到什么时候会触发压缩、17 条历史怎么被压成 7 条。

<div align="center">
    <img src="https://bugstack.cn/images/article/ai/toyharness-04.png" width="380px">
</div>

#### 2.3 真实案例

来一个日常编程场景：**让 ToyHarness 调研你电脑上的另一个项目**。这里拿小傅哥的另一个实战工程 `ai-mcp-gateway`（桌面上的 MCP 网关项目）做案例，围绕它展开了一轮 9 个回合的长对话——Agent 开发的三个核心场景（**多工具协作、人工审批 human-in-the-loop、上下文压缩**）在这轮对话里全部真实触发。

##### 2.3.1 第0-1回合

**第一回合：多工具探索，人工审批登场。** 提问 *"帮我看看我电脑桌面上的 ai-mcp-gateway 项目，整体是做什么的？"*——模型自主组合 `run_command` 扫目录、`fs_read` 读 README，调研后给出结构化回答。当它要执行白名单外的命令（`sed` 读文件片段）时，Agent Loop 会**暂停并升级给人决策**：页面上弹出审批卡片，写明命令内容、拦截原因和 120 秒超时，点"批准执行"才继续跑。这就是 Claude Code 同款的 human-in-the-loop：

<div align="center">
    <img src="https://bugstack.cn/images/article/ai/toyharness-05.png" width="900px">
</div>

##### 2.3.1 第2-8回合

**第 2-8 回合：越聊越深，工具按需出场。** 继续追问"用了哪些技术栈、模块怎么划分的"、"项目里 Redis 是怎么用的"、"总结一下它和 ToyHarness 的区别"、"统计一下有多少个 Java 文件"……每一回合模型都按需挑选工具（`fs_grep`、`run_command`、`fs_read`），其中统计代码那回合还能看到**环境反馈循环**：第一次 `find` 路径没找到，模型自己改成绝对路径重试成功，最终统计出 `212 个 Java 文件、11,752 行代码`。没有任何一处是硬编码流程，全是模型在循环里看反馈、做决策。

##### 2.3.1 第8-9回合

**第 9 回合：上下文压缩自动触发。** 长对话聊到 16 条消息阈值时，压缩算法真的跑了起来——Harness 把 18 条旧历史交给模型压缩成一条摘要、最近 6 条原样保留，对话区直接打出 `HARNESS 12 条旧历史 + 摘要，保留最近 6 条（上下文压缩）` 的横幅卡片；右侧 COMPACTION 面板记录了这次真实执行的数字（18 条压缩前历史 → 12 条旧历史做 LLM 摘要 + 6 条原样保留 → 7 条压缩后历史）：

<div align="center">
    <img src="https://bugstack.cn/images/article/ai/toyharness-06.png" width="900px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/ai/toyharness-07.png" width="500px">
</div>

**收尾：一句话纪要。** 最后让模型把全部调研内容整理成"对话纪要 · 5 条重点"——9 轮长对话下来，每一回合消耗多少 Token、什么时候压缩、省下了什么，全部看得见。**你不需要串联任何流程，只提供工具、上下文和环境反馈，剩下的交给模型。**

---

跑通上面两个例子后，这个工程里还有一整套值得你逐个体验的能力：

- 💬 **多会话管理**：左侧"新会话"建多个对话，历史落盘到 `~/.toyharness/`，重启不丢；
- 🧮 **13+ 内置工具**：fs 五件套（read/write/edit/glob/grep）、`run_command`、`web_fetch`、`subagent`、`memory`、`todo`……全部策略模式实现，照着一个类就能加自己的工具；
- 🛡️ **护栏与人工审批（human-in-the-loop）**：白名单外的命令会升级给用户决策，页面上会弹出审批卡片；
- 🤖 **子代理**：让主 Agent 派生一个独立上下文的迷你 Agent 干活，防止主上下文被污染；
- 🔌 **MCP 接入**：连接任意 stdio MCP 服务器，远端工具自动桥接为 `mcp__<name>` 加入工具清单；
- 📦 **技能包（Skills）**：编辑 `skills/` 目录下的 SKILL.md，重启即生效，渐进式披露按需注入；
- ⚙️ **模型可插拔**：底部悬浮岛随时切换模型、推理强度、多配置档案，改完立即热生效。

> 🎯 体验完这些，你已经把 Agent 运行时的全貌摸了一遍。接下来就进入正题——从 `1000 行` LLM 对接到 `2000 行` Harness 骨架，我们把这匹马具一点一点拆开、装回去。

## 三、项目工程

看懂一个 Agent 的最好方式，就是要有一个抽丝剥茧，拿到最核心本质的工程，这样你才能更好的上车。

ToyHarness 全部代码只有 `32 个 Java 类`，零三方依赖（唯一的依赖是日志门面），却完整覆盖了 Agent 运行时的每一个主题。我用接下来的内容工程的骨架和几处核心代码讲清楚，再给你一张**架构全景图**（来自于小傅哥编写的 xfg-coding-map-skills 实现）——对着图读代码，学习效率翻倍。

> 配套视频；[https://www.bilibili.com/video/BV1CNHL66Ekk](https://www.bilibili.com/video/BV1CNHL66Ekk) - ToyHarness 的工程实现来自于 ToyAgent 课程，对应提供了一套视频，可以学习（`可以让你非常快速的学习到如何开发 Agent`）。

### 1. 工程代码

如图，工程代码结构；左侧是给你的文档、代码地图，右侧是工程代码。

<div align="center">
    <img src="https://bugstack.cn/images/article/ai/toyharness-08.png" width="500px">
</div>


#### 1.1 工程结构

| 包 | 职责 | 关键类 |
|---|------|-------|
| `core` | **Harness 骨架**：主循环、工具清单、护栏、多会话、技能、子代理 | `ToyHarness`（全书主角）、`Toolbox`、`Guardrails`、`SessionManager`、`Skills`、`SubAgent` |
| `core.tool` | **13 个内置工具**，策略模式实现，一个类一个工具 | `ToolStrategy` 接口 + impl 下 `FsRead`/`FsWrite`/`FsEdit`/`FsGlob`/`FsGrep`/`RunCommand`/`WebFetch`/`Calculator`/`GetTime`/`GetWeather`… |
| `llm` | **模型协议层**：对接任意 OpenAI 协议的 LLM | `ChatModel` 接口、`OpenAiChatModel`、`MockChatModel`（免 Key 开箱跑）、`Models`（配置热切换） |
| `mcp` | **外部工具桥接**：零依赖 stdio MCP 客户端 | `McpClient`（JSON-RPC 2.0） |
| `common` + 根 | 数据目录与 **HTTP 入口** | `Workspace`（`~/.toyharness` 落盘）、`Application`（JDK 自带 HttpServer 起服务） |

#### 1.2 循环内核

`ToyHarness.chat()` 是整套工程的"书名页"，每次对话固定走五个阶段：**输入守卫 → 组装上下文 → Agent Loop → 输出守卫与跨轮记忆 → 历史压缩**。而其中真正构成 Agent 的，是 `runLoop()` 这个几十行的循环：

```java
private String runLoop(List<Message> messages, Consumer<String> onDelta) {
    for (int step = 1; step <= MAX_STEPS; step++) {
        // 调模型：流式返回，文本增量实时回调（SSE 转发给页面）
        ChatResponse response = model.chatStream(messages, tools.schemas(), ...);

        // 收敛条件：模型不再发起工具调用 = 最终回答，Loop 结束
        if (!response.hasToolCalls()) {
            return response.content();
        }

        // 关键不变量：assistant(tool_calls) 原样入窗口，不能改写成普通文本
        messages.add(new Message("assistant", response.content(), response.toolCalls(), null));

        for (ToolCall call : response.toolCalls()) {
            String verdict = guards.checkTool(call.name(), args);      // 工具守卫：执行权在宿主
            String observation = invokeWithApproval(call.name(), args, step); // 人工审批 + 执行
            messages.add(Message.tool(call.id(), observation));        // 观察按 tool_call_id 回填
        }
    }
    return "已达最大步数 MAX_STEPS，循环保险丝熔断。"; // 防失控：MAX_STEPS 熔断
}
```

就这么多：**模型说调工具就执行工具、把观察回填、再问模型**——收敛条件、不变量、保险丝，一个 Agent 运行时的全部骨架都在这。

#### 1.3 工具清单

13 个内置工具全部是策略模式：接口只有一个方法，每个策略类负责一族工具的定义（名称/描述/Schema）与执行体：

```java
@FunctionalInterface
public interface ToolStrategy {
    /** 将本策略负责的工具注册进工具箱（一个策略可贡献多个工具）。 */
    void register(Toolbox tools);
}
```

**新增一个工具 = 新增一个策略类 + 在 `AgentTools` 的策略清单里加一行**，工具就自动出现在系统提示词的工具清单里被模型看见——这正是"一张不断变长的工具清单"的工程化表达，也是你二开加工具的最佳切入点。

#### 1.4 模型协议

模型层没有引入任何 SDK，纯 JDK 的 `java.net.http` 直连：拼接 `base-url + endpoint` 发起流式请求，逐行解析 SSE 数据块——文本增量实时回调给页面，`tool_calls` 的增量参数分片拼装成完整调用，`usage` 回传累计成本。同一接口下还有个 `MockChatModel` 剧本模型，没配 Key 也能把整个 Loop 跑起来。**"对接 LLM"这件事，1,000 行出头就讲透了。**

> 📖 以上只是三处最核心的切面，`Guardrails` 护栏、`SessionManager` 多会话、`Skills` 渐进式披露、`SubAgent` 子代理、`McpClient` 桥接等每一处都有完整的中文注释设计说明——**核心代码请直接看工程**：[gitcode.net/KnowledgePlanet/toyharness](https://gitcode.net/KnowledgePlanet/toyharness)，对照 `md/` 目录下 01-13 章设计文档逐篇食用，效果最佳。

### 2. 全景地图

为了方便伙伴们更好的学习这套东西，小傅哥给大家配套的提供了「代码工程地图」`docs/code-map/toyharness-arch/` **双击 `toyharness-arch.html` 用浏览器打开**（纯静态页面，不需要起任何服务），就能得到这张可交互的 ToyHarness 架构全景图——上面三个数字先给你个心理预期：`32 个 Java 类`、`13 个内置工具，全部与真实工程同源。

#### 2.1 学习导航

打开 `toyharness-arch.html`，第一眼是这张**导览欢迎框**——它就是你的"开车导航"：点 **「开始导览」**，10 站会沿着一次请求的完整生命周期自动推进（入口装配 → 五阶段对话 → 守卫审批 → 工具执行 → 压缩收尾），每一站自动跳到对应视图、高亮相关节点、展开讲解卡；不想被打断也可以选「自由浏览」，随时点左上角重新进入导览：

<div align="center">
    <img src="https://bugstack.cn/images/article/ai/toyharness-09.png" width="700px">
</div>

10 站的完整路线（一条主线串起全书）：

**1. 全景鸟瞰**（就是上面那张整体图）→ **2. 启动与装配**（Application 做了哪三件事）→ **3. 一次对话的五阶段** → **4. 模型调用与流式回传** → **5. 工具守卫与人工审批** → **6. 工具执行与错误自愈** → **7. Loop 收敛与保险丝** → **8. 历史压缩**（16 条阈值、保留最近 6 条）→ **9. 能力扩展三件套**（MCP/Skills/SubAgent）→ **10. 把它跑起来**

如果你是第一次接触 Agent 工程，跟着导览走一遍，就相当于把第二章"快速体验"里看到的所有现象（时序图、审批卡、压缩横幅）在代码层面逐一验明正身。

#### 2.2 整体结构

全景图页面是三栏结构：**左侧**是真实工程树，**中间**是架构图，**右侧**是每个类的讲解卡。它最爽的用法是**点击联动**——比如在左栏点一下 `Application.java 入口门面`，中间架构图会高亮对应节点并用虚线指过去，右栏同时自动滚动定位到它的讲解卡（启动做了哪三件事、`/api/*` 的 16 个端点分工、审批闸门怎么接）：

<div align="center">
    <img src="../code-map/toyharness-arch/toyharness-arch-联动预览.png" width="900px">
</div>

**想看懂哪个类，就点哪个类**——树、图、卡三边永远保持同步，不用自己在几十个文件之间来回跳。

#### 2.3 时序流程

导览的第 3-8 站都会切到**时序图**视图——这是整张地图里最值得放大细看的一张。先看整体：一次对话从 `浏览器页面` 发起，经过 `Application`（HTTP 入口）、`ToyHarness`（chat() 五阶段）、`Guardrails`（三层护栏）、`Skills`（技能壳），再到 `OpenAiChatModel` 和工具层，**每一步调用、每一次回传都是一条带文字标注的动线**：

<div align="center">
    <img src="https://bugstack.cn/images/article/ai/toyharness-10.png" width="900px">
</div>

时序图支持缩放（右上角 ＋/－ 按钮），**放大之后才能看到真正的细节**。下面是放大的前半段——`POST /api/chat/stream` 进来之后发生的事，每条箭头都对应一章的内容：

- `checkInput(message)`：**输入守卫**，被拦截的文案直接回给页面——未调用模型、不花 token（第 11 章）；
- `组装上下文`：system + 工具清单 + skills 命中 → history + 新输入；`matchInput` 命中触发词时 `L2 全文注入`（第 03/05/09 章）；
- `loop`：`chatStream(messages, tools.schemas)` 进入 Agent Loop，`delta 文本增量 → SSE` 流式回传页面（第 01/02 章）；
- `checkTool(name, args)`：**工具守卫**，拒绝时文案作为观察回填（第 11 章）；
- `invokeWithApproval`：白名单外命令升级人工审批——`SSE event approval 审批卡片` 弹到页面，用户决策经 `POST /api/approve（allow / trust）` 回填，`null = 批准`、`文本 = 拒绝`（第 11 章，正是你在快速体验里亲手点过的那张审批卡）。

<div align="center">
    <img src="https://bugstack.cn/images/article/ai/toyharness-11.png" width="900px">
</div>

**看图的方式**：从上往下就是时间顺序，实线是调用、虚线是返回，`alt`/`loop` 框是条件与循环——把这张图和 `ToyHarness.chat()` 的源码并排放着对照读，一行代码对应一条箭头，迷路是不可能的。除此之外还有 **流程图** 视图，专门拆 `runLoop`/`compactIfNeeded` 等关键方法内部的分支逻辑，读到第 07 章上下文压缩时记得切过去看看。

> 学习项目时，第 1 步 · 跑通、第 2 步 · 跟导览，之后对照阅读核心代码以及后续就可以扩展二开了。