---
title: 面试：技能、简历、问题汇总
lock: no
---

# 《WaLiSSH - AI Shell 智能终端》，关于面试中的技能、简历、问题汇总

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>项目：[https://t.zsxq.com/s83De](https://t.zsxq.com/s83De)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

`来啦，来啦，他来啦！` WaLiSSH（AI SSH 运维助手） 简历模板 + 面试八股，一条龙整齐划一！🐲 —— 耗时4个月，基于 Google ADK 运行时智能体构建的 AI Agent 脚手架（DDD 架构），实现了一款 AI SSH DevOps 智能体运维系统。让不少伙伴都从传统开发，上车到了 AI Agent 开发工程师。**今儿，我把相关的简历编写方式和对应的面试八股整理发给大家！**

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walioffice/walioffice-00.png" width="150px">
</div>

面试官，WaLiSSH 它是一款 AI SSH `服务运维`/`故障巡检`/`应用压测`/`日志分析` ... 的，综合智能体 DevOps 运维助手系统。全程流式可见且危险命令有拦截兜底。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walissh/product-walissh-user-guide-02.png" width="850px">
</div>

>项目地址：[https://bugstack.cn/md/project/walissh/walissh.html](https://bugstack.cn/md/project/walissh/walissh.html) - 含有完整课程目录以及课程预览，可以看看。

架构上，它由三部分组成；walissh-client 桌面客户端，由 Tauri 2 + React 19 框架实现。walissh-server 服务端，Spring Boot 3 + Spring AI + Google ADK 1.2，DDD 分层的 Agent 大脑，核心是一条 ReAct 节点链 AiCallNode → LoopDecisionNode → UserFeedbackNode 处理。之后是用于操作服务器的 SSH 连接和工具命令处理。

实现上， `意图识别级联`、`混合窗口上下文裁剪 + 动态提示词组装`，`支撑长会话不失控`、`四种类型长期记忆提取 + 基于 DAG 的子 Agent 编排`，`把复杂任务拆给专职子 Agent`、`前后端 SSE 全链路流式`，`AI 的每一步思考`、`命令执行`、`结果反馈实时呈现`等。这些内容全部实现完成的，你可以以一个产品视角来深度学习。

>接下来，小傅哥带着大家看看这套项目的简历是怎么写的，八股问题有哪些。文末提供了小傅哥所有 AI 实战课程的项目源码。

## 一、简历编写

简历模板是给大家提供的案例，不用非得完整的 CV 复制。同一套项目，不同的主观视角和场景诉求，是完全可以写出不同的版本效果的。而且你所有编写的内容，都是以为你面试时所想陈述的视角来构造的。

所以，你可以把下面这些模板内容，以及结合工程代码作为你的简历编写素材，按照你的诉求进行提炼使用。注意，你所讲的话就是事实，也是面试时候的提问入口点（聪明的死鬼，还在会这里埋入一些钩子）。

>描述的时候，可以适当的引入一些技术、术语、框架，来给内容做一些包装来讲。

### 1. AI SSH 智能运维助手 - 📮 投后端/Agent 开发通用

**项目名称**：WaLiSSH——AI SSH 智能运维助手（服务端 + 桌面客户端）
**简要介绍**：用户以自然语言下达运维指令，Agent 自主规划并在 SSH 终端安全执行，流式输出推理过程与执行结果。
**核心技术**：Spring Boot 3 ｜ Spring AI ｜ Google ADK 1.2 ｜ MyBatis/MySQL ｜ SSE ｜ Tauri 2 + React 19 + xterm.js

- **架构设计**：采用 DDD 六边形架构（api/case/domain/infrastructure/trigger 五层业务模块 + types 独立类型模块）实现依赖反转，意图识别、上下文管理、动态提示词、长期记忆均建模为独立领域服务，支撑单测与组件替换。
- **ReAct 执行链路**：构建 AiCallNode→ToolCallNode→LoopDecisionNode→UserFeedbackNode 节点链实现推理-行动-观察循环；SSE 流式推送由独立异步线程驱动（15s 心跳保活），tool_call/tool_result 事件实时推送工具调用轨迹，前端可观测 Agent 执行过程。
- **意图识别级联**：规则(<1ms，置信度≥0.8 采信)+LLM(100~500ms，≥0.5 采信)三级级联，高频指令由规则层直接命中，LLM 调用降为疑难才调；配套失败反馈重分类回路与 LRU 缓存（200 条/5 分钟）。
- **上下文工程**：实现混合裁剪器（重要性保留+时效窗口补充+完整消息组保底），保证 user/tool 消息配对完整；动态提示词以"用户消息后缀"注入环境快照，保护 system 前缀 KV-Cache 命中率。
- **多 Agent 编排**：把 Agent 作为 Tool 使用，基于 DAG 的动态编排器，每轮调度就绪任务并发执行、Semaphore 限流；单任务失败不中断整体，下游依赖级联 SKIPPED，支持 failFast 与死锁检测。
- **安全兜底**：危险命令正则拦截（rm -rf、dd、mkfs 等），命中即拒绝执行并返回人工操作提示；SSH 凭据加密落库（AES-GCM），凭据永不进入模型上下文。
- **桌面客户端**：Tauri 2 + React 19 构建跨平台客户端，xterm.js WebGL 真终端与 AI 对话面板联动，Agent 可直接操作用户当前终端会话。

### 2. 线上智能巡检系统 - 📮投运维平台/SRE/稳定性方向

**项目名称**：WaLiSSH——线上智能巡检 Agent 平台
**简要介绍**：把"每天人工登 20 台机器看一圈"变成一句话：Agent 按维度拆解巡检计划，多 Agent 并行执行检查项，输出结构化巡检报告并沉淀环境基线。
**核心技术**：Spring Boot 3 ｜ Google ADK 1.2 ｜ DAG 任务编排 ｜ SSE ｜ 长期记忆（MySQL/MyBatis）

- **巡检任务编排**：基于 DAG 的任务编排器将巡检计划（CPU/内存/磁盘/服务存活/端口探活等检查项）按依赖关系调度，互不依赖的检查项经 Semaphore 限流并发执行；单检查项失败不中断巡检，结果按任务状态机（PENDING/COMPLETED/FAILED/SKIPPED）完整留痕。
- **子 Agent 并行巡检**：子 Agent 派发服务以独立 Runner 隔离上下文，每个子 Agent 专注单一检查维度；指数退避重试（2^n 封顶 30s）应对瞬时网络抖动，巡检全程经 ThreadLocal 透传 SSH 终端会话，保证操作环境一致。
- **环境基线记忆**：长期记忆服务自动从巡检输出提取环境事实与软件版本（OS 发行版、Redis/NGINX 版本等四类结构化记忆，sha1 key 幂等去重），作为后续巡检的对比基线——"这次磁盘 85%，上次巡检还是 60%"的环比结论由记忆召回支撑。
- **里程碑追踪与断点续跑**：巡检进度以里程碑落库（ChatMilestone，内存+DB 双写），巡检中断后恢复时模型无需重读全部历史即知"查到哪了"，支持继续执行而非从头重跑。
- **报告汇总与流式输出**：巡检结果经 SSE 流式推送（异步线程驱动，tool_call/tool_result 事件实时展示当前检查项），全部完成后由汇总 Agent 输出结构化巡检报告与风险项。
- **执行安全**：巡检以只读命令为主，工具层危险命令正则拦截 + 高危操作人工确认双保险，杜绝巡检误操作。

### 3. 日志分析 - 📮投可观测/排障/DevOps 方向

**项目名称**：WaLiSSH——智能日志分析与根因定位 Agent
**简要介绍**：把报错日志、配置文件拖进对话框，Agent 自动上服务器查关联上下文、跨日志源交叉检索、给出根因假设并验证。
**核心技术**：Spring Boot 3 ｜ Spring AI ｜ SFTP/SSH 双通道 ｜ 上下文裁剪 ｜ 两阶段记忆召回 ｜ SSE

- **多源信息接入**：用户可把报错日志、配置文件内容直接粘进对话框，Agent 亦可自主 tail/grep 服务器日志与配置，交叉验证故障线索。
- **大文件上下文治理**：针对日志文件动辄数百 MB 的场景，工具层输出截断/摘要后再入上下文，配合混合裁剪器（重要性保留+时效窗口补充）控制 token 预算——关键错误堆栈不丢、无关 INFO 刷屏不进。
- **故障案例记忆**：长期记忆自动沉淀 TROUBLESHOOTING_CASE（工具失败/分析结论触发提取，偏好类记忆置信度 0.85，sha1 key 幂等去重），同类故障复发时粗筛（拉最近 120 条）+精排（关键词重叠打分）两阶段召回历史案例，实现"上次 502 是 upstream 超时，这次先查 upstream"的经验复用。
- **意图路由**：故障类请求由规则层直接命中（<1ms）走排障链路，模糊请求由 LLM 层分类；工具执行失败触发反馈重分类（最多一次），排障路径可自纠偏。
- **根因验证闭环**：ReAct 循环中 Agent 提出根因假设→执行验证命令→观察结果→修正假设，LoopDecisionNode 控制验证轮次上限防死循环；结论回写里程碑供人工复核。
- **安全边界**：只读分析命令与变更命令分级，变更类操作（重启服务/改配置）强制人工确认后执行。

### 4. 智能压测 - 📮 投性能工程/中间件方向

**项目名称**：WaLiSSH——智能压测与性能诊断 Agent
**简要介绍**：一句话发起压测：Agent 拆解为"压测执行→指标采集→瓶颈分析→优化建议"四阶段计划，多 Agent 并行执行并汇总诊断报告。
**核心技术**：Spring Boot 3 ｜ Google ADK 1.2 ｜ Plan-and-Execute ｜ 多 Agent 编排（DAG+Semaphore） ｜ SSE

- **Plan-and-Execute 编排**：规划阶段将压测任务拆为带依赖关系的 DAG（压测执行与指标采集并行、瓶颈分析依赖两者完成），编排器逐轮调度就绪任务、Semaphore 限流并发，failFast 开关控制失败时是否继续。
- **多 Agent 分工**：压测执行、指标采集、瓶颈分析等子 Agent 各司其职（驱动 wrk/ab/jmeter 等既有压测工具与 top/vmstat/jstat 等观测命令），派发经子 Agent 名称白名单校验，缩小模型选择空间降低误调用。
- **执行环境一致**：父子 Agent 经 InheritableThreadLocal 透传同一 SSH 终端会话，压测机与被观测机环境绑定不漂移。
- **结果可信**：汇总 Agent 对各子 Agent 返回做结构化汇聚（状态+结果而非全文转发），避免多 Agent 文本往返的 token 膨胀与信息失真；诊断结论附执行轨迹（tool_call/tool_result 事件全程留痕）可回溯。
- **优化建议沉淀**：诊断结论写入长期记忆故障案例库，下次同服务压测时自动召回历史基线对比。

### 5. 故障应急排查版 - 📮投稳定性/应急响应方向

**项目名称**：WaLiSSH——线上故障应急排查 Agent
**简要介绍**：告警触发后一句话描述现象，Agent 按预案思路逐项排查（进程→端口→日志→依赖），期间危险操作全部拦截，给出结论与恢复建议。
**核心技术**：Spring Boot 3 ｜ 意图识别级联（规则+LLM） ｜ ReAct ｜ SSE ｜ 长期记忆（故障案例库）

- **应急意图路由**：意图级联（规则 <1ms 置信度≥0.8 采信 + LLM 100~500ms 置信度≥0.5 采信）将"redis 是不是挂了"这类口语化告警描述路由到排障链路；低置信度不硬选，携带候选意图交主模型参考，UNKNOWN 全交主模型兜底——分错不致命，漏查才致命。
- **排查链路自纠偏**：工具执行失败且结果像意图走偏时触发重分类（最多一次防循环）；连续失败≥2 次跳过规则层直接 LLM 兜底（门槛放宽至 0.3），保证应急场景不因分类卡死。
- **执行安全兜底**：排查中命中重启/回滚等高危命令时，工具层直接拦截拒绝执行并提示人工操作，避免 Agent 自主做出不可逆变更；派发子 Agent 非幂等写任务保持重试次数为 0，由主 Agent 拿到失败原因后自行决策，防止并发重试触发双重操作。
- **现场保全**：排查全程工具调用轨迹经 SSE tool_call/tool_result 事件实时留痕，事后可完整复盘 Agent 执行过哪些命令、看过哪些日志，满足故障复盘的取证需求。
- **案例沉淀**：故障结论自动写入 TROUBLESHOOTING_CASE 记忆（sha1 key 去重），形成团队级应急预案知识库。

### 6. 多机集群运维版 - 📮 投基础设施/云平台方向

**项目名称**：WaLiSSH——多机集群批量运维 Agent
**简要介绍**：面对一组服务器（Redis 集群、K8s 节点池），Agent 规划"逐台执行+逐台验证"的批量任务，并行下发、汇总差异、失败机器单独标记。
**核心技术**：Spring Boot 3 ｜ DAG 批量编排 ｜ ThreadLocal 会话绑定 ｜ 指数退避重试 ｜ 凭据加密

- **批量任务编排**：以 DAG 描述批量变更（如集群扩容：逐节点安装→配置→启动→健康检查），编排器按依赖逐轮调度、并行执行互不依赖的节点任务；单节点失败级联跳过其下游步骤并保留其他节点继续，failFast 可选。
- **会话环境绑定**：每个节点任务经 InheritableThreadLocal 绑定对应 SSH 终端会话，多节点并行操作时环境上下文不串——这是多机并发正确性的关键设计。
- **执行隔离与重试**：子 Agent 独立 Runner 上下文隔离，节点级指数退避重试（封顶 30s）；死锁检测保证依赖环异常时提前退出并报告。
- **差异汇总**：汇总阶段输出各节点执行状态与结果差异（成功/失败/跳过清单），支持失败节点定点重跑。
- **安全管控**：集群级变更属高危操作，危险命令正则拦截 + 逐批人工确认；凭据加密存储，模型上下文只含连接引用。

> 如果你不局限于 WaLiSSH 课程版，又做了很多的二开扩展。如结合 Arthas 组件、ELK 组件、桥接、跳板机或者延伸到 MySQL、Redis、ES 等，那么你的内容还会更加丰富。

## 二、面试问题

> 使用说明：每道题先背「回答要点」骨架，再落到「结合项目」——最后这节是你区别于背书选手的杀招。全部内容锚定 walissh-server 真实源码（类名均可直接在工程中找到），面试被追问任何一条都有代码兜底。

### 2.1 基础概念

**Q1：什么是 AI Agent？和 ChatBot / Workflow 有什么区别？**

- ChatBot 一问一答无状态；Workflow 是人写死的控制流，LLM 只在节点内被调用；Agent 是以 LLM 为决策核心的循环系统，模型自己决定调哪个工具、要不要继续、何时结束（感知/规划/记忆/工具四要素）。
- 经典定义（Anthropic）：**Workflow 是代码编排 LLM，Agent 是 LLM 编排代码**。

> 结合项目：WaLiSSH 涵盖两个设计思维 —— ReAct 主链路（`AiCallNode → LoopDecisionNode → UserFeedbackNode`）是 Agent，模型自主决策；Armory 装配阶段的 `SequentialAgentNode/ParallelAgentNode/LoopAgentNode` 是 Workflow，装配期结构固定、运行期决策交给模型。一句话：「Agent 管运行时智能决策，Workflow 管编译期结构保障」。

**Q2：什么是 Google ADK？为什么用它？**

- ADK 是 Google 开源的 Agent 开发框架：`BaseAgent` + `Runner`（驱动执行产出事件流）+ `FunctionTool`（`@Schema` 注解自动生成 Function Calling 协议）+ 三大多 Agent 编排原语（Sequential/Parallel/Loop）。
- 分工而非二选一：Spring AI 负责 OpenAI 兼容协议对接与 MCP 客户端；ADK 负责多 Agent 运行时（`CustomRunnerFactory` 创建 Runner、`FunctionTool.create` 包装 SSH 工具、`SubAgentDispatchService` 派发子 Agent）。
- **ADK 的优势**：①事件驱动——Runner 产出的事件流天然就是 SSE 推流的原料，ReAct 每步推理/行动/观察直接透传到前端，不用自己造流式协议；②工具协议零手工——`@Schema` 声明即 Function Calling，SSH 工具加字段不用改提示词模板；③编排是框架原语而非自研状态机——Sequential/Parallel/Loop 开箱即用，DAG 调度只需在其上做装配层翻译；④Google 官方出品，与 Gemini 系模型协议演进同步，不担心 Protocol 更新脱节。
- **为什么这个场景适合 ADK 而不是别的框架**：WaLiSSH 的核心诉求是「用户在界面上自助装配 Agent + 多 Agent 编排 + 服务端流式输出」，本质在不只是运行在桌面端，而是可以远程服务器端配置允许多租户使用。那么 Java 语言，以及选择 Java 语言下的 Agent 框架更为合适。在这个选择下，Googlg ADK 是满足「官方 Java 版 + 多 Agent 编排原语 + 事件流架构 + 可编程嵌入（而非托管平台）」的选项——它是框架不是平台，能以库的形态长进 Spring Boot 应用里，非常方便Java 类应用使用。

**Q3：介绍项目架构，为什么用 DDD？**

- 七个 Maven 模块：api（接口定义+DTO）/ app（启动配置）/ case（用例编排：ReAct 节点链）/ domain（领域层，最重）/ infrastructure（DAO/仓储实现/PasswordEncryptor）/ trigger（HTTP 入口）/ types（通用类型）。依赖方向指向领域层，基础设施经 adapter/repository 接口反转注入。
- 特色：意图识别、上下文管理、动态提示词、长期记忆都建模为**独立领域服务**而非工具类——它们是有状态、有策略、可独立演化的领域逻辑；高内聚，低耦合，模型分层。这正是 DDD 适配 Agent 项目的理由。

**Q4：用户发一句话到收到回答，链路怎么走？**

1. 系统里，`AgentServiceController` 接收 `ChatRequestDTO`（agentId/userId/sessionId/terminalSessionId）；
2. case 层 `AIAgentReActServiceCase.chatStream` 创建 SSE emitter（3 分钟超时），节点链在独立守护线程异步执行，不阻塞 HTTP；
3. `AiCallNode`：意图识别（规则→LLM 级联）标签注入 Prompt → `HybridReducer` 裁剪历史 → `DynamicPromptBuilder` 把环境快照/最近命令/里程碑拼进用户消息后缀；
4. 模型产出回复或工具调用 → `ToolCallNode` 执行 SSH 工具（危险命令拦截、终端会话绑定）→ `LoopDecisionNode` 终止判定 → `UserFeedbackNode` 收口（组装结果、清理资源）；
5. 旁路：执行失败触发意图反馈重分类；工具输出进入长期记忆提取。
> 加分点：「流式由异步线程驱动，tool_call/tool_result 事件实时推送工具轨迹给前端」。

### 2.2 ReAct 与工具调用

**Q1：ReAct 和 Function Calling 什么关系？**

- Function Calling 是模型层的结构化输出能力（按 schema 生成 JSON）；ReAct 是推理-行动-观察的循环范式。**FC 是 ReAct 中 "Act" 这步的现代化实现**。单步任务不需要循环，强确定性流程用 Workflow。

> 结合项目：节点链即循环——`AiCallNode`（推理）→ `ToolCallNode`（行动）→ `LoopDecisionNode`（观察+终止判定）；工具侧 `SshExecuteAdkTool` 用 ADK `@Schema` 声明参数，框架自动完成协议转换（这部分节点主要用于上下文、富化消息、提示词等处理，最终会由 ADK 管理 ReAct）。

**Q2：工具调用怎么实现的？**

- 工具三要素：name（动词+宾语）、description（何时必须调用/何时不要/返回什么——质量直接决定调用准确性）、parameters（JSON Schema）。封装清单：参数校验、超时、异常分类、结果截断、脱敏。

> 结合项目：**环境上下文由系统注入而非模型决定**——`InheritableThreadLocal` 绑定 terminalSessionId，工具由此知道操作哪台服务器。追问「为什么不用参数传」：参数由模型生成，不能让模型决定连哪台机器，这既是安全也是正确性。工具输出统一 `truncateToolResponse(4000)` 截断后再入上下文。

**Q3：工具调用失败怎么处理？**

- 分类：参数错误→报错回喂让模型自修；超时→降级返回结构化错误；幻觉调用→白名单拒绝。核心理念：**工具报错也是给模型看的上下文**。

> 结合项目三层：①`SubAgentDispatchService` 指数退避重试（2^n 秒封顶 30s）；②`DynamicAgentOrchestrator` 单任务失败只置 FAILED 不中断整体，下游级联 SKIPPED，failFast 可选；③`IntentService.reportFeedback`——工具失败且置信度<0.7 且结果像意图走偏时触发重分类（最多一次），把失败升级为「修正上游决策」的信号。

**Q4：死循环/无限调用工具怎么办？**

- 硬性轮次上限 + 失败计数熔断 + 把剩余轮次注入提示词。结合项目：`LoopDecisionNode` 四类终止判定（max_steps / max_tool_calls / finish 指令 / error），意图重分类最多一次防循环，连续失败 ≥2 次跳过规则层直接 LLM 兜底。

**Q5：危险命令怎么拦截？用户诱导删库怎么办？**（SSH 场景必答题）

结合项目：`SshExecuteAdkTool.DANGEROUS_PATTERN` 正则拦截 `rm -rf /`、`dd if=`、`mkfs.`、fork 炸弹 `:(){ }`、写裸设备 `> /dev/sd`、`chmod -R 777 /`（大小写不敏感），命中**直接拒绝执行**并返回提示引导人工操作；终端会话由 InheritableThreadLocal 系统注入，模型无法被诱导切换目标机器。可主动讲演进方向：拒绝是确定性底线，更完善方案是高危命令「挂起等确认后放行」，需额外解决状态持久化与幂等。

> 注意这部分可以设计多层拦截 + 用户授权操作。也可以控制可操作写、更、删，的执行文件夹范围。

**Q6：意图识别分错了怎么办？**

结合项目（`IntentService` 级联）：规则层（<1ms，置信度≥0.8 采信）→ LLM 层（100~500ms，≥0.5 采信否则回退规则）→ 反馈回路（失败触发重分类最多一次）；低置信度不硬选、带候选意图交主模型；UNKNOWN = 放弃分类全交主模型；LRU 缓存（200 条/5 分钟）+ 连续失败≥2 跳过规则层（门槛放宽 0.3）。回答「能否挽回」：**能**——分类结果只是提示词里的先验标签，主模型每轮仍看完整上下文，误分类是可修正状态而非终态。

### 2.3 上下文与记忆

**Q1：上下文太长怎么办？**

滑动窗口裁剪 → 重要性裁剪 → 摘要压缩 → 结构化提取 → RAG 外挂。

结合项目（招牌设计）：`HybridReducer` 组合策略——`PriorityReducer` 主保留集合（关键错误/路径/工具链）∪ `SlidingWindowReducer` 近期补充 ∪ **最近 2 个完整消息组保底**。三个设计动机：重要性、时效性、**消息配对完整性**（user/tool 消息必须成组裁剪，否则协议不合法）。追问「几百 MB 日志怎么办」：工具层截断/摘要后再入上下文 + 裁剪器兜底，不塞全文。

**Q2：动态提示词是什么？**

结合项目（`DynamicPromptBuilder`）：环境快照、最近命令、里程碑事件动态注入。关键选型——用 `buildMessageSuffix`（**用户消息后缀**）而非改 system instruction，理由是 system 前缀稳定可命中 **KV Cache 前缀缓存**，改 system 每轮缓存全失效。里程碑（`MilestoneTracker` + `ChatMilestonePO` 落库）让模型跨轮不重读历史即知「做到哪了」。

**Q3：长期记忆怎么设计？**（8 大高频之一）

结合项目（`LongTermMemoryService`，四类结构化记忆自动提取）：

| 类型 | 触发 | key 策略 | 置信度 |
|---|---|---|---|
| USER_PREFERENCE | 用户消息含「以后/默认/记住」 | sha1("pref:"+内容) | 0.85 |
| ENVIRONMENT_FACT | 工具输出匹配 OS 关键词 | "os:ubuntu" | 0.70 |
| SOFTWARE_FACT | 工具输出匹配版本模式 | "redis:version" | 0.78~0.82 |
| TROUBLESHOOTING_CASE | 工具失败/助手含「结论、原因」 | sha1 | 0.68~0.72 |

- 召回：**粗筛（DB 拉最近 120 条）+ 精排（关键词重叠×2，偏好类加权 +1）**两阶段；去重靠 key 策略天然幂等。
- 杀招答点：为什么不用向量库？运维关键词（nginx/redis/502）**天然高区分度**，纯 MySQL + 关键词打分够用——零外部依赖零成本。「该用时用，不该用时不炫技」比「我用了向量库」更好。

> 对于长期记忆，视频课程里提到，可以设计的方式蛮多的。数据库、redis、本地文件、rag、llm-wiki 都是可以的。像是数据库，就是会更准，要啥拿啥。rag、llm-wiki 属于匹配算法，覆盖有关联度的。

**Q4：RAG 了解吗？你的项目为什么没做？**

- 标准链路两阶段：离线（解析→切块→Embedding→索引）+ 在线（query 改写→召回→Rerank→拼 Prompt→引用溯源）。朴素 RAG 失败模式：召回不准→混合检索、语义≠相似→BM25 兜底、上下文割裂→结构感知切片、噪音大→Rerank 只送 Top-5~8。
- 结合项目：WaLiSSH 的「知识」是结构化环境事实与故障案例，记忆服务两阶段召回就是**粗筛扩量+精排收口**思想的同构落地；扩展路径已预留——`ILongTermMemoryRepository` 仓储抽象使向量方案替换不侵入领域层。

> 如果你还做了 WaLiAPI 项目，可以把这部分基础的东西，放到里面去讲。llm 网关、rag、llm-wiki，+ mcp 结合 skills 让各个 Agent 智能体可以使用。

### 2.4 多 Agent 与编排

**Q1：多 Agent 怎么设计？什么时候不该拆？**

- 三种编排模式：中心化 router / 流水线 Sequential / 并行 Parallel。**不该拆的信号**：单 Agent + 工具列表能解决就别拆——多 Agent 带来上下文同步、错误传播、调试复杂度的代价。

> 结合项目两条路径：①装配期 `SequentialAgentNode/ParallelAgentNode/LoopAgentNode` 把配置编译成 ADK 编排原语；②运行期 `DynamicAgentOrchestrator` 按 **DAG 依赖**调度——每轮筛「依赖均 COMPLETED」的 PENDING 任务并发提交，`Semaphore` 限流；失败级联 SKIPPED；failFast 可选；一轮无状态变化即死锁提前退出（`PlanValidator` 三色 DFS 环检测前置兜底）。追问「调度器单点瓶颈」：编排器是无状态纯调度逻辑，瓶颈在下游模型并发，用 Semaphore+线程池参数控制。

**Q2：为什么用多 Agent？**

- ①上下文隔离（子 Agent 不背父会话全部历史）；②工具分组缩小模型选择空间；③并行执行互不依赖任务。代价：结果汇总要截断/摘要、调试链路变长。**简单请求走单 Agent ReAct，复杂任务才升级动态编排——按需升降级是成熟设计的标志。**

**Q3：Agent 装配（Armory）是什么？** - 如果你在介绍项目的时候提到了装配（埋钩子），是有可能会问的。

- Armory = 把用户界面配置的 Agent（选模型/写提示词/挑工具/连 MCP）**编译成可运行的 ADK 实例**：一条装配节点链，每类节点负责一种原料，产物由 `AgentCatalog` 统一编目。类比：**Agent 装配之于 Agent，如 ORM 元数据映射之于数据库访问**——配置变运行时对象，产品侧自助调整无需发版。

### 2.5 工程落地与安全

**Q1：执行流程怎么暂停/恢复？**

- 关键认知：HTTP 请求生命周期 ≠ Agent 执行生命周期，状态必须外置持久化。结合项目：对话状态落 `ChatSessionPO/ChatMessagePO`，任务进度落 `ChatMilestonePO`（`MilestoneTracker` 检测关键事件经 `saveMilestone` 持久化），恢复时模型靠里程碑即知进度。当前工程对高危命令是**直接拦截拒绝**；确认类操作的完整方案（确认状态落库+幂等键防重）可作为演进方向讲。

**Q2：MCP 是什么？项目里怎么用？**

- MCP 是 Anthropic 开放的工具接入标准协议（stdio/SSE/Streamable HTTP），让工具提供方与 Agent 宿主解耦、热插拔无需发版。结合项目：`DefaultMcpClientFactory` 在装配期创建 MCP 客户端，把外部 MCP Server 的工具**翻译成 ADK 工具**挂载——对模型来说 MCP 工具与内建工具无差别，都是 Function Calling。能力两层：内建 SSH 工具（`@Schema`）+ 外挂 MCP 工具（协议发现）。

> AI MCP Gateway 对于这部分还有专门的深度讲解，也可以后续补充学习。这样对于此类问题会回答的更加深入。

**Q3：SSE 流式怎么实现？断线了怎么办？**

- 结合项目：`ResponseBodyEmitter` + 节点链独立守护线程异步执行，HTTP 线程立即返回；trigger 层 MVP 链路 10 分钟超时 + **15s 心跳注释行**防代理断连；事件模型 `text/tool_call/tool_result/done/error`（`ReActEventDTO` 承载）。工程坑可作经验谈：流式被强断后 Agent 可能滞留执行态（需超时+终态校验）、事件可能重复推送（展示层去重）。断线恢复靠会话/里程碑持久化，任务状态不因连接断开丢失。

**Q4：Agent 效果怎么评估？**

- 两条线：结果评估（完成率/人工抽检/LLM-as-judge，主动讲 judge 偏差）+ 轨迹评估（工具调用是否多余、路径是否最短）。结合项目四个天然锚点：①意图分类命中率（规则命中 vs 回落 LLM 比例）；②工具执行成功率与重试分布；③每任务平均 ReAct 轮数；④运维金标准——**命令执行后系统状态是否符合预期**，比文本评估客观。

> 有一套评测技能，可以安装后对 Agent 进行评测。[https://t.zsxq.com/MEntg](https://t.zsxq.com/MEntg)

**Q5：凭据这类敏感信息怎么处理？**

- 结合项目：infrastructure 层独立 `PasswordEncryptor`（AES-GCM）加密落库，不落明文；设计原则——**凭据永不进入模型上下文**，工具只持有连接 ID/会话 ID 引用，真实凭据只在服务端执行时解密使用。这是「工具最小暴露面」原则的实例。

### 2.6 高频补充

**Q1：Agent 有哪几种设计模式？**

- 五大模式：ReAct / Plan-and-Execute / 单智能体+工具 / Multi-Agent / RAG。结合项目落地了前四种且**按请求复杂度自动升降级**：简单问题单 Agent 一轮搞定，复杂任务升级为 `DynamicAgentOrchestrator` 的 DAG 编排（先规划后执行的 Plan-and-Execute）。

**Q2：Tool / MCP / Skill / Workflow 边界？**

- **Tool 管执行**（原子能力）、**MCP 管接入**（标准协议）、**Skill 管知道怎么做**（知识+流程打包，prompt 层渐进式加载）、**Workflow 管流程**（人写死控制流）。

**Q3：多 Agent 为什么 Token 开销大？怎么优化？**

- 根因：Agent 间传文本，反复「状态→文本→状态」转换费 token 又失真。结合项目：`SubAgentDispatchService` 结果以结构化 Map（status/result）回写 `DynamicTask`，编排器汇总**任务状态而非全文对话**；子 Agent 独立 Runner 天然上下文隔离，只透传 InheritableThreadLocal 终端会话这个必要执行环境。

**Q4：多 Agent 重复执行/死循环/冲突怎么办？**

- 任务状态机（PENDING→COMPLETED/FAILED/SKIPPED）+ 依赖检查，已终态任务不再调度；DAG 每轮必须有状态变化否则死锁退出；单任务 maxRetries+指数退避封顶 30s；多 Agent 共享同一 SSH 终端会话（InheritableThreadLocal），命令串行不并发写同一状态。

### 2.7 LLM 基础与 RAG

**Q1：对 LLM 的本质认知？**

- 四个钉死认知：①概率生成器不是数据库（生成「最像样的」而非「最真实的」）；②知识冻结（训练日即冻结）；③幻觉根因（优化流畅度而非真实性）；④无记忆（多轮全靠应用层塞 messages）。

> 杀招句：「大模型在 Java 世界里就是另一个第三方 REST 服务——特别慢、特别贵、还偶尔胡说八道。所以 WaLiSSH 像治理不可靠依赖一样治理它：意图级联降调用、LRU 缓存、SSE 超时控制、失败反馈兜底。」分工原则：**事实查库/工具，流程走接口，表达才给 LLM**——服务器状态永远来自 SSH 真实执行结果，不让模型背状态。

**Q2：KV Cache 是什么？为什么不缓存 Q？**

- 先说 Q/K/V 是什么（自注意力机制里，每个 token 的向量会投影成三份）：**Q（Query，查询向量）=「我在找什么」；K（Key，键向量）=「我能提供什么特征」；V（Value，值向量）=「我实际携带的内容」**。Attention 的计算就是：当前 token 拿自己的 Q，与前面所有 token 的 K 做点积算相关性权重，再按权重加权求和对方的 V。
- 为什么只缓存 K/V：生成第 n 个 token 时，需要它自己这份 Q 去和「前 n-1 个 token 的 K、V」做注意力——前 n-1 个 token 的 K/V 会被后面每一步反复使用，所以必须缓存避免重算；而**前面 token 的 Q 只在自己出生那一步用过一次，之后永远不会再参与计算**，缓存了也是死数据，纯浪费显存。结合项目：动态提示词放**用户消息后缀**保 system 前缀 KV Cache 命中——一个提示词位置选型落到硬件原理上，是八股连项目的示范答法。

**Q3：向量/Embedding/余弦相似度？**

- 关键词匹配字、向量匹配意（`like '%car%'` 匹配不到 automobile）；余弦只看方向不看模长所以文本默认余弦；三个必背关系：归一化后余弦==内积、余弦越大⇔L2 越小（排序等价）、**排序等价≠阈值通用**。工程认知：入库查询必须同模型（换模型=全量重建）；相似≠相关（要混合检索+Rerank 补）。

**Q4：RAG 完整链路与关键决策？**

- 离线：解析→切块（500~1000 token、10~20% 重叠、结构感知、父子块）→Embedding→索引；在线：改写→召回→RRF 融合（`score=Σ1/(k+rank)`，不归一化两量纲）→Rerank（Cross-Encoder 准但慢，只精排 Top-50 取 5~8）→生成约束（仅依据资料回答+强制引用）。分水岭认知：**RAG 瓶颈在检索不在生成**。

**Q5：上下文窗口都 1M 了还要 RAG 吗？**

- 三个理由：成本延迟、Lost in the Middle 注意力衰减、实时性与权限过滤。正确姿势：**RAG 负责从海量里精准找，长上下文负责精读推理，互补不替代**。结合项目：会话内叫裁剪（HybridReducer），会话间叫记忆，企业级叫 RAG——同一套思想。

**Q6：为什么不微调？**

- LoRA 一句话：冻结原模型，插低秩矩阵 A(d×r)、B(r×d)（r≪d）只训小矩阵——微调权重变化量天然低秩。结合项目三个理由：运维域标注数据少、提示词可即时迭代、OpenAI 兼容协议下云端/本地开源模型（vLLM/Ollama）随时切换。「能力不够先调提示词和上下文工程，最后才微调」——应用工程师的正确优先级。

> 其实发展到现在已经不咋说微调了，好的模型，不需要你微调啥，主要就是 skills、mcp、提示词这些内容的使用，以及 plugin 插件的开发。

### 2.8 框架选型与串讲

**Q1：Spring AI vs LangChain4j？为什么选 Spring AI？**

- LangChain4j 偏 AI 应用框架（抽象重、全家桶）；Spring AI 偏 Spring 生态的 AI 基建（starter 零装配、内置 MCP 支持）。选型三段论：①Spring Boot 3 项目 starter 接入零成本；②MCP 客户端直接用 Spring AI 能力；③**Spring AI 不做多 Agent 运行时所以引入 ADK 补位**——Spring AI 管模型接入与协议，ADK 管多 Agent 编排，各取所长。

**Q2：对比过其他框架吗？为什么选 ADK？**

- 框架光谱：裸写 → LangChain4j AiServices/Spring AI ChatClient（单 Agent）→ LangGraph（Python 状态图）→ Google ADK（Java 多 Agent 运行时）→ Coze/Dify（低代码）。选 ADK 四理由：官方 Java 版、`@Schema` 声明式工具、Runner 事件流天然对接 SSE、三编排原语直接映射装配节点。

**Q3：Coze/Dify 低代码和自研的边界？**

- 平台快但深度定制受限；WaLiSSH 的安全模型（危险命令拦截、终端会话绑定）必须植入执行流每一环，平台做不到侵入式控制。标准知识库问答场景则可评估 Dify——自研与平台不对立，按场景选。

**Q4：把 LLM/RAG/Agent/MCP 串起来讲**

- 一句话隐喻：LLM 是发动机，Embedding 是坐标系，向量库是索引卡，RAG 是外挂资料室，Tool 是手脚，MCP 是 USB-C，Skill 是岗位手册，Agent 是会自己拆任务、干完还会复盘的数字员工。
- 五层架构：模型层（买的）→上下文层→能力层→编排层→**工程治理层（自研护城河——招 Java 工程师而不是算法的原因）**。
- 用 WaLiSSH 逐层指认：Spring AI OpenAiApi → HybridReducer+LongTermMemoryService → SshExecuteAdkTool+DefaultMcpClientFactory → ReAct 节点链+DAG 调度 → 意图级联降本/危险命令拦截/凭据加密/轨迹留痕。

## 三、面试材料

为了让小伙伴们更加方便的背题面试，小傅哥这里又给大家准备好了对应的`八股地图`（结合工程源码，核心流程，以及面试的内容）和一套简历 PDF 模板，方便大家拿去使用。—— `这东西真的花费太多精力，玩命肝！`

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walissh/walissh-notes-03.png" width="350px">
</div>

> 在 walissh-server 工程的 master 主分支，可以更新后使用。


### 1. 八股地图

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walissh/walissh-notes-01.png" width="950px">
</div>

- 位置：我把八股地图放到了工程下 `walissh-server/docs/notes/八股地图` 下面，一个 html 文件，你可以点击打开。
- 说明：左侧是工程，中间是流程图/时序图，右侧是对应的八股问题。这对需要速刷的伙伴太有用了，可以快速把一个项目背下来。

### 2. 简历模板

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walissh/walissh-notes-02.png" width="950px">
</div>

- 位置：我把八股地图放到了工程下 `walissh-server/docs/notes/简历模板` 下面，一个 html 文件，你可以点击打开。
- 说明：基于项目信息，小傅哥这里提供了一些 pdf 简历模板，可以参考使用。