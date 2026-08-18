---
title: WaLiOffice - 在线办公系统（docx、ppt、draw.io、excel、图像、视频）
lock: no
---

# WaLiOffice - 在线办公系统（docx、ppt、draw.io、excel、图像、视频）

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

`~. 死鬼`，你以为我的瓦力全家桶是闹笑话吗？🤨

编码的天是 `Claude Code`、`Codex`，我做了个 [**WaLiCode**](https://walicode.xiaofuge.cn/)。办公的天是 `元宝`、`豆包`，那我要做 [**WaLiOffice**](https://walioffice.xiaofuge.cn/)！—— 瓦力全家桶，小傅哥 AI 时代的个人作品库！

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walioffice/walioffice-00.png" width="150px"/>
</div>

**豆包强吗？拿 Deepseek Harness，两天我给你复刻出来一个！**

豆包，字节旗下的核心 AI 产品。定位于一款全能型 AI 办公助手，兼顾学习、工作、创意、生活场景，把字节自研大模型能力封装成普通人开箱即用的工具。

字节，是聪明的，产品定位也准确。因为 `docx`、`ppt`、`excel`、`设计图（draw.io）`、`图像（生图）`、`视频`，的用户基数比做编程开发的要多的多，而且是每个企业里都要使用的一套东西。

嘿嘿，我也机智！字节干啥，我也得干点啥，还把它给开源喽。一个是提高我的技术储备，也让大家学习到这样的东西。另一个是让这样的产品，可以被个人和企业做本地化部署使用。这样整，它安全呀，还可靠，想扩展结合自己的业务场景也容易。

**所以，玩个大的！**

那，拿什么玩？有人觉得 LangChain 太重，有人说 AutoGPT 太野。那看看 Deepseek Harness（DSH），DeepSeek 官方开源的 Agent 引擎。不是第三方套壳，是 DeepSeek 自己造来跑自家模型的框架。

DSH 给了咱什么？🤔

**cordis 插件系统。** 每个能力独立插件，`name`/`inject`/`apply` 三行导出，`ctx.plugin()` 加载。加新工具 5 分钟跑通，没有继承链，没有 override 地狱。

**BlockAssembler 流式协议。** 不是一坨字符串往前端推，是结构化事件流：`block-start → reasoning-delta / text-delta / tool-call-delta → block-end`。用户实时看到模型在想什么、在说什么、在调什么工具。**豆包给你看思考过程了吗？我给你看。**

**Agent 在 Node，渲染在 Rust。** DSH 的 10 个 Office 工具在 Node 注册，`execute` 时 HTTP 回调 Rust 后端渲染文档。Node 跑 Agent 编排（异步 IO 强项），Rust 跑文档生成（内存安全强项），各取所长。想换 Agent 框架？Rust 端一行不用改。

**原生 reasoning 支持。** DeepSeek 模型的 `reasoning_content` 不是被忽略的副产物，是独立 block 平行流式输出。思考过程是一等公民，不是装饰。

**并行工具调用。** `maxParallelToolCalls: 10`，搜资料、做 PPT、画图表同时跑，不用排队等。

等等一些列技术，DSH 又给了我们更多的，新的设计思路（架构 + 师，永不过时）。

> 好啦，现在是时候，让大家体验一把 WaLiOffice 了，这个只开发了2天，基于 Deepseek Harness 实现的在线办公系统。源码已开放。

## 一、项目介绍

WaLiOffice 是一款 Web 端 AI Agent 智能办公助手，一句话生成 PPT、Word、Excel、Draw.io 图表、图片、视频，覆盖企业日常办公全场景。

**官网地址**：[https://walioffice.xiaofuge.cn/](https://walioffice.xiaofuge.cn/) - `体验地址，暂时还没有部署那么多服务器，用时候。轻点！死鬼！`

**源码地址**：[https://github.com/fuzhengwei/WaLiOffice](https://github.com/fuzhengwei/WaLiOffice)

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walioffice/walioffice-01.png" width="950px"/>
</div>

**“帮我做一份 2026 年 AI 行业报告 PPT”**、**“画一个微服务架构图”** 、**“生成一张科技感的产品封面图”**、**“检索下天津和平区重点小学和学区房以及升学率做成 excel 表”**、**“做一个视频，高考考生从上学到高考一路走来”** 

一句话，WaLiOffice 就可以帮你完成各类办公化的工作，`word`、`ppt`、`excel`、`draw.io`、`图像`、`视频`，都可以帮你完成。还会把数据帮你存储到【我的文件】，还可以创建项目空间，归类对话，也可以把过往的对话拖进去，一起维护。

- 左侧，可以创建对话、查看文件（生成产物），以及项目空间和独立对话。你可以拖动一个对话，到项目空间，方便维护。
- 中间，选择对话类型，（综合）也会根据对话分析帮你生成各类文件。在`未选择项目`里，可以选择已有项目或者新建项目空间。附件里，也可以传图片和文件。
- 右侧，产出结果，各类文件，图、视频，还有百度的图表插件（柱状图、饼图），也可以在对话中按需创建。

> 当然，WaLiOffice 还是第一个版本，后续还有更多的优化。也欢迎伙伴们一起迭代维护，提交 issue 或则 pr。

## 二、场景举例

### 1. Word

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walioffice/walioffice-03.png" width="950px"/>
</div>

- 描述：`帮我写一份新员工入职指南，包含公司简介、组织架构、办公环境介绍、常用系统账号开通流程、考勤制度、福利待遇、新人 30 天成长计划，语气亲切友好，让新人看了不慌。`
- 说明：Word 文档生成采用场景驱动的结构化输出实现：用户输入需求后，doc_generate 工具先通过关键词识别 7 类文档场景（产品 PRD/运营分析/销售汇报/技术设计/培训手册/项目交付/通用商务），结合用户指定的文档类型（report/plan/summary/article/prd）生成对应的格式引导，再调用 LLM 输出结构化 JSON——包含标题、多级章节（heading 1-3 层次）、段落正文、要点列表和表格数据，至少 5 个章节、每节 2-3 段内容，要求至少 2 个章节包含表格。生成结果同时转换为 Markdown 供前端预览，再由 docx_render 模块使用 docx-rs crate 渲染为标准 .docx 文件——标题 56 半磅粗体、章节标题按级别 36/30/26 半磅、正文 22 半磅，表格带粗体表头行，段落自动清除 Markdown 标记。当 LLM 输出解析失败时还有降级草稿兜底，确保始终有产物返回。整个过程通过 SSE 推送生成状态，用户实时看到文档从无到有的全链路。

### 2. PPT

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walioffice/walioffice-02.png" width="950px"/>
</div>

- 描述：`综合于 22年底到今年，AI的发展，各项技术，以及产品，做一个演讲 ppt。 ppt 样式要漂亮，ui要美观。`
- 说明：标准的 PPT（可下载由本地工具维护） 生成采用两阶段 AI 驱动实现：先由 ppt_plan 工具根据用户需求自动识别场景（产品方案/运营复盘/技术架构等 7 类），调用 LLM 生成结构化大纲（标题、页面布局、视觉建议、上屏要点），再由 ppt_generate 工具将大纲转换为带有真实视觉设计的幻灯片——内置 5 套主题配色（business/tech/warm/minimal/default）多组色板轮换，每页由背景、卡片、装饰条、标题、要点列表等元素按 EMU 精确坐标布局，最终由纯 Rust 手写 OOXML 渲染成 .pptx 文件，不依赖任何第三方 PPT 库。整个过程通过 SSE 逐页推送生成进度，用户实时看到 AI 从规划到成片的全链路。

### 3. Excel

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walioffice/walioffice-04.png" width="950px"/>
</div>

- 描述：`请帮我检索天津和平区学区房价格和重点小学升学率数据，按学校排名整理成表格，包含学校名称、学区房均价、初中升学率、重点高中录取率等指标，并生成一份可汇报的 Excel 文档。`
- 说明：Excel 表格生成采用多表结构化数据驱动实现：用户输入需求后，sheet_generate 工具通过关键词识别 7 类业务场景（产品管理/运营分析/销售管理/技术项目/培训管理/项目交付/通用业务），调用 LLM 生成结构化 JSON——包含标题和多个 table 数组，每个 table 自带表头、数据行和说明，要求至少 4 列 6 行，数据必须具体真实（不能用占位符），表头要专业可执行（负责人、阶段、金额、转化率、风险等级等），需求复杂时自动拆成多表（如"明细表 + 汇总表"“计划表 + 风险表”）。生成结果由 xlsx_render 模块使用 rust-xlsxwriter crate 渲染为 .xlsx 文件——每个 table 对应一个 Sheet，表头行红底白字粗体格式，数据行自动识别数字类型写入 write_number，列宽 autofit 自适应，Sheet 名做合法性清洗（≤31 字符、过滤特殊字符），整体支持单文件多 Sheet 输出。

### 4. draw.io 

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walioffice/walioffice-05.png" width="950px"/>
</div>

- 描述：`帮我画一个员工请假审批流程图：员工提交请假 → 直属领导审批 → 3天以上需部门总监审批 → HR 备案 → 通知本人结果，包含驳回和退回修改的分支，节点清晰，适合放进员工手册。`
- 说明：Draw.io 图表生成采用LLM 直出 XML实现：用户输入需求后，drawio_generate 工具通过关键词识别 7 类场景（产品方案/运营流程/销售流程/技术架构/培训流程/项目实施/通用汇报），结合用户指定的图表类型（flowchart/architecture/swimlane/topology/er/mindmap），调用 LLM 直接生成 draw.io 原生 XML（mxGraphModel 格式）——不是生成中间格式再转换，是让模型直接写 mxCell 节点、mxGeometry 坐标、style 样式和箭头连接，要求布局坐标合理不重叠、用不同颜色区分节点类型、中文标签、适合在 draw.io 中继续编辑。生成后自动清理 Markdown fence 包裹，校验是否包含 <mxGraphModel 或 <mxfile 根标签，确保产物可直接导入 draw.io 或前端内嵌渲染。整个流程没有额外渲染层，LLM 输出即最终产物，轻量高效。

### 5. 图像

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walioffice/walioffice-06.png" width="950px"/>
</div>

- 描述：`做一个一群动物，秋季运动会照片，蹬自行车。非常有意思，有很多动物参与。非常热闹`
- 介绍：**图像生成**采用"LLM 规划提示词 + LLm API 出图"两阶段实现：用户输入需求后，`image_prompt` 工具先通过关键词识别 4 类视觉场景（商业视觉/科技视觉/活动传播/通用商用），结合用户上传的参考图自动判断文生图还是图生图模式，再调用 LLM 生成 3 套差异化风格的英文提示词——每条严格遵循 `[主体] + [场景/环境] + [风格] + [光照] + [构图] + [质量要求]` 结构，图生图模式额外约束 `preserving the original composition / main subject identity`。同时根据关键词推断输出尺寸（海报竖版 2:3 / 方图 1:1 / 横版 3:2），调用 LLM Image 2.1 Flash API 逐张生成，支持 URL 和 base64 两种返回格式，失败自动重试 2 次，全部失败后用简化提示词兜底，最终返回可直接预览的图片链接。

### 6. 视频

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walioffice/walioffice-07.png" width="950px"/>
</div>

- 描述：`做一个一群动物，秋季运动会视频，蹬自行车。非常有意思，有很多动物参与。非常热闹`
- 说明：**视频生成**采用"LLM 规划镜头脚本 + Agnes Video API 异步出片 + 本地 FFmpeg 兜底"三阶段实现：用户输入需求后，`video_generate` 工具调用 LLM 生成结构化视频方案（标题、英文提示词、负向提示词、宽高比、时长档位），提示词遵循 `[主体] + [动作] + [场景] + [镜头运动] + [光线] + [风格]`，支持文生视频、图生视频、关键帧过渡三种模式。方案确定后根据宽高比推断分辨率（16:9 → 1152×768 / 9:16 → 768×1152 等）、根据时长档位推断帧数（short 81 帧 / standard 121 帧 / long 241 帧 / max 441 帧），24fps，然后提交 Agnes Video V2.0 创建任务，拿到 task_id 后轮询状态（每 5 秒一次，超时 480 秒），实时推送进度百分比。**最硬核的是兜底机制**：如果 Agnes 凭证不可用、任务创建失败、轮询超时或生成失败，自动降级到本地 `local_video` 模块用 FFmpeg 合成可播放 MP4，确保用户始终拿到视频产物而不是报错。

### 7. 我的文件

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walioffice/walioffice-08.png" width="750px"/>
</div>

所有对话生成的数据，都会在【我的文件】里，可以查看，下载，或者删除。这样可以非常方便大家使用。如果后续在迭代维护，可以对接一些网盘或者如飞书这样的软件。

## 三、项目设计

后续，小傅哥会把这个课程拆分出来章节分支，让小伙伴可以从头理解并学习。

### 1. 我能学到什么

这是一套从0到1，涵盖前后端 + DevOps 的 Deepseek Harness + 办公场景的，综合实战项目。带着大家进行需求分析、架构设计、Agent 核心、工具链开发、部署上线的全流程实践。所以，你可以非常完整地学习到 AI Agent 智能办公平台的核心开发能力，具备 Rust + React 全栈工程项目实战经验。

- 【后端】熟练 Rust 语言特性，掌握 axum + tokio 异步 Web 服务开发，理解 Rust 所有权机制在实际项目中的应用。
- 【后端】深入 ReAct Agent 架构，从零实现"推理→工具调用→结果入上下文→继续"的完整循环，掌握 max_turns 控制与上下文压缩策略。
- 【后端】设计 Agent 工具注册表体系，定义统一的 Tool trait + ToolContext + ToolResult，注册 10 个业务工具（PPT/Word/Excel/Markdown/Draw.io/图表/图片/视频/搜索），理解开闭原则在工具扩展中的实践。
- 【后端】使用纯 Rust 实现文档渲染引擎，基于 docx-rs + rust_xlsxwriter 完成 DOCX/XLSX 导出，不依赖外部服务，安全性高。
- 【后端】掌握 SSE 流式通信机制，通过 mpsc channel 推送 7 种事件（Thinking/ToolCall/ToolResult/Artifact/Message/TurnEnd/Done），实现前端实时反馈。
- 【后端】深入 OpenAI 兼容 LLM Client 封装，支持流式/非流式/附件三种调用模式，掌握多 LLM 端点分离配置（文本/图片/视频独立 API）。
- 【后端】运用 rusqlite + r2d2 连接池，设计 10 张表 + 18 个索引的 SQLite 数据库，掌握 Repository 模式在数据访问层的实践。
- 【后端】实现 JWT 认证中间件（jsonwebtoken + bcrypt），含 AuthUser 提取器，理解 Rust 中间件链的构建方式。
- 【后端】集成 DSH Agent Engine（Node.js），实现 Rust + Node.js 混合架构，通过 HTTP/SSE 跨进程通信，掌握 cordis 函数式插件体系和 BlockAssembler chunk 格式。
- 【后端】设计 Agent System Prompt（~80 行），包含意图识别规则、多产物交付策略、图片理解/生成区分、视频生成策略，掌握 Prompt Engineering 在工程中的落地。
- 【后端】积累 ffmpeg 视频兜底机制设计经验，远程 API 不可用时本地合成可预览 MP4，理解降级策略的重要性。
- 【前端】使用 React 18 + TypeScript + Vite + TailwindCSS 构建 SPA 工程，掌握 Zustand 轻量状态管理在中等复杂度应用中的实践。
- 【前端】实现 SSE 流式解析与渲染，通过 fetch + ReadableStream 处理 6 种事件（message/artifact_update/state_update/tool_result/done/project_update），掌握前端流式交互的完整方案。
- 【前端】设计三栏式 Studio 工作台（会话列表/对话区/产物预览），集成 PPT 幻灯片预览、Word 文档预览、ECharts 图表、Draw.io 图表、图片/视频播放等多种产物渲染器。
- 【前端】实现产物自动导出机制，根据 artifact 类型触发 DOCX/XLSX/MD 下载并同步保存到用户文件列表，理解前后端文件流转的完整链路。
- 【前端】封装图片压缩上传（最大 1600px / 1.8MB 目标），Office 文档内容提取（调用后端 extract API），掌握前端附件处理的工程实践。
- 【前端】使用 Radix UI 无头组件库 + lucide-react 图标体系，构建高质量 UI 交互体验，理解无头组件的设计思想。
- 【运维】使用 Docker 多阶段构建（node:20 → rust:1.80 → debian:bookworm-slim），优化镜像分层缓存，掌握 Rust + React 项目的容器化部署。
- 【运维】基于 rust-embed 将前端 dist 编译时嵌入后端二进制，实现单一二进制部署，运维成本极低。
- 【其他】积累 Rust + React 全栈架构设计经验，在整个工程中体现高内聚低耦合的模块划分——后端按 agent/auth/db/llm/render/routes 清晰分层，前端按 pages/components/stores/api/lib/types 组织代码，每个模块单一职责，具备良好的可维护性和可扩展性。

### 2. 系统架构

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walioffice/walioffice-09.png" width="950px"/>
</div>

WaLiOffice 架构图采用自上而下的三层分层结构，右侧独立列出外部服务，整体呈现"用户 → 前端 → 后端核心 → 数据"的清晰流向。

- 前端：React 18 单页应用，核心是 Studio 三栏工作台——左侧流式对话，右侧产物预览（PPT/Word/Excel/图片/视频），通过 SSE 实时接收后端推送。
- 后端核心层：Rust (axum) 构建，是整张图的重点。正中央高亮区域是 Agent ReAct 循环引擎：组装 Prompt → 调用 LLM → 判断是否需要工具 → 执行工具后回到调用步骤，最多循环 8 轮。循环过程中通过事件流实时推送思考、工具调用、产物生成等 7 种事件到前端。周围分布着纯 Rust 文档渲染引擎、文件提取、上下文压缩等支撑模块，右下角黄色区域整齐排列着 10 个工具`（PPT/Word/Markdown/Excel/图表/Draw.io/图片/视频/搜索）`，覆盖主流办公场景。
- 数据层（红色）：SQLite 嵌入式数据库，10 张表 + 18 个索引，通过 r2d2 连接池访问，零运维。
- 右侧外部服务列（紫色）：LLM API、图片生成、视频生成、搜索服务，以及底部标注的两种部署方式——单一二进制和 Docker。

>一句话概括：用户对话 → Agent ReAct 循环调度工具 → 生成文档/表格/PPT/图片/视频 → 实时推送到前端预览，全部打包进一个 Rust 二进制。

### 3. 章节计划

- 第1部分：需求与架构
  - 第1-1节：WaLiOffice需求分析
  - 第1-2节：技术栈选型与系统架构设计

- 第2部分：智能体对话基础
  - 第2-1节：工程初始化与项目结构
  - 第2-2节：LLM客户端与流式SSE实现
  - 第2-3节：数据库设计与会话持久化
  - 第2-4节：工具Trait定义与注册表机制
  - 第2-5节：ReAct循环核心实现
  - 第2-6节：System Prompt工程与意图识别
  - 第2-7节：后端Chat路由与SSE端点
  - 第2-8节：前端对话界面与流式渲染

- 第3部分：工具集成
  - 第3-1节：Markdown工具——全链路模板打通
  - 第3-2节：Word工具与纯Rust DOCX渲染
  - 第3-3节：PPT双工具协作（plan+generate）
  - 第3-4节：Excel工具与XLSX渲染
  - 第3-5节：ECharts图表工具与前端渲染
  - 第3-6节：react-drawio嵌入式编辑器
  - 第3-7节：联网搜索工具web_search
  - 第3-8节：图像生成——API调用与多Key轮询
  - 第3-9节：图像生成——附件理解与图生图
  - 第3-10节：视频生成——Agnes Video API
  - 第3-11节：视频生成——本地ffmpeg兜底机制

- 第4部分：增强设计
  - 第4-1节：配置管理与多组LLM端点
  - 第4-2节：附件处理与状态管理
  - 第4-3节：用户认证与JWT

- 第5部分：部署与DevOps
  - 第5-1节：Docker多阶段构建
  - 第5-2节：云服务器部署配置

- 第6部分：DSH Agent引擎集成（预留）

> 鉴于 Deepseek Harness 暂未发布稳定版，本部分暂放后面，待稳定版发布后补充。

  - 第6-1节：DSH架构设计与混合引擎方案
  - 第6-2节：DSH核心插件开发
  - 第6-3节：walioffice-tools统一工具插件
  - 第6-4节：cordis插件系统与BlockAssembler
  - 第6-5节：Rust代理路由与工具回调
  - 第6-6节：前端适配与DSH全链路验证

> WaLiOffice 是小傅哥提供的24个项目中的一个，还有成套的 WaLiSSH、WaLiCode、WaLiAPI 全套的瓦力组合，以及各类业务项目，网关项目，组件项目，源码项目。可以说，小傅哥提供的就是一个互联网公司项目组，各类项目非常全面。

