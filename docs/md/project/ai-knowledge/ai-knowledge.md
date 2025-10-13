---
title: DeepSeek RAG&MCP&Agent 智能体 - 解析文档&Git仓库代码&AI工作流
lock: no
---

# 《DeepSeek RAG&MCP&Agent 智能体》 - 解析文档&Git仓库代码&AI工作流

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>课程：[https://t.zsxq.com/GwNZp](https://t.zsxq.com/GwNZp)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?isOutside=true&aid=114106895958308&bvid=BV1ty9sYtEkc&cid=28695397252&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

大家好，我是技术UP主小傅哥。

刚上周，老板说：`”把咱们招聘里也加一条，具备AI应用开发能力的优先！“`。是呀，现在越来越多的企业都在用AI开发能力提效了，如；聊天软件增加一键唯独信息归档提取、工作文档资料携AI对话分析、工程SQL语句脚本辅运营自动完成数据处理、代码编写用AI完成自动评审等等。这些都是在AI的基础上在构建应用，以后也会越来越多！所以，具备AI应用开发能力，也是每个工程师最应该具备的基础能力了。

并且用不了多久，各大互联网企业都将大量的推进落地，自有 [MCP](https://github.com/modelcontextprotocol) 服务的实现，用于增强企业 AI 应用的提效能力。因为 [MCP](https://github.com/modelcontextprotocol) 的加入，可以让你；一条命令`帮研发`，调用应用系统日志、排查系统CPU负载、自主选择是否调度数据库信息。也可以一条命令`帮运营`，搞定复杂的SQL执行、导出报表、分析数据、完成促活营销券的自动化配置上架。这就是 [MCP](https://github.com/modelcontextprotocol) 的魅力！👍🏻

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-0-00.png" width="250px"/>
</div>

**那么牛，MCP 是什么？**

专业的术语 `MCP = Model Context Protocol` 模型上下文协议，可实现应用与外部数据源和工具之间的无缝集成。无论您是构建 AI 驱动的 IDE、增强聊天界面还是创建自定义 AI 工作流，MCP 都提供了一种标准化的方式来连接他们所需的上下文。

`来吧，上图！让你看看它是啥！`

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-11-01.png" width="850px"/>
</div>

- 首先，站在用户的使用视角，研发或者运营，可以通过话术描述，完成系列的 AI 工作流，并拿到最终的结果。这就是 MCP 最终为你提供的服务。
- 那么，你可以想象，在日常的工作中，运营、研发、产品、测试等，都有非常多的重复非创作性的工作，占用了大量的时间成本。尤其是研发，写写代码，就有运营过来，帮我查个问题吧，小嘚嘚。但如果有这样的借助于 MCP 实现的 AI 工作流，就可以完成80%以上的工作量。
- 之后，站在技术的实现视角，MCP 是一个标准结构框架，你可以按照它（Spring AI）提供的 SDK 开发方式，完成本地化 API 的接入开发。让 AI 有明确的方式调用各类 API 服务接口。如果没有 MCP 这会是一件很麻烦的事情。

**跟着小傅哥学习，从不走偏！😄**

- 2022年底，ChatGPT 开始火爆。
- 2023年2月，小傅哥，开启了第一个基于AI的项目，ChatGPT AI 问答助手项目。让所有伙伴，都能学习到 AI 如何开发应用。
- 2023年4月，启动OpenAI(ChatGPT/ChatGLM)微服务应用体系构建大型项目，让大家可以用微信登录、微信支付/蓝兔支付，构建自己的可对外付费提供服务的 OpenAI。这一年上车学习的伙伴，很多做了自己的 AI 产品，除了提高编程技能，又小赚了一辆宝马。
- 2024年7月，结合企业诉求，开启 OpenAI + Github Acitons，实现代码自动化评审。这一年，不少伙伴在自己的公司中都有落地，个人也得到了述职晋升。
- 2025年3月，咱们再起启航，基于 Ollama 部署 DeepSeek，开发 RAG 知识库，解析文档和Git仓库代码。这个东西，将是企业中构建自己知识库的又一项非常重要的事情。有了知识库，AI 代码的自动评审，会更加精准，也可以辅助分析需求等。

那么，接下来小傅哥就细致的介绍下，本次开启的新项目，可以让大家学习到哪些知识，掌握哪些技术。

> Spring AI MCP 与 24年末发布，学习此 AI 应用开发项目，你将是第一批具备 Java AI 应用实战开发能力的人。竞争力，嘎嘎滴！

## 一、能学到啥

这是一套综合`前后端 + Dev-Ops`，基于 Spring Ai 框架实现，Ai Agent 智能体。耗时7个多月，38节课程（`视频`+`文档`），从 RAG 到 MCP，再实现出互联网企业级，可编排的 Ai Agent 智能体，现已全部开发完成 + 部署上线。💐

该项目是结合当下最火的 Ollama、DeepSeek、SpringAI 等技术构建的 RAG 知识库实现。从前端到后端到 dev-ops 的全栈式功能手把手实现。

- 前端，基于 AI 工具，设计前端对话页面，完成 HTML、JS、TailwindCSS 的编码工作。
- 前端，配置跨域服务接口，前后端分离实现 UI + 服务端接口对接。
- 后端，构建双层架构，直接面向需求编码。让学习伙伴更轻松完成 RAG 知识库核心知识的学习。
- 后端，基于 Spring AI 完成 DeepSeek、OpenAI 双模型的策略对接，处理文本向量的解析和存储。
- 后端，使用 postgresql 存储切割文本向量数据，完成知识库的解析和存储。
- 后端，处理多样文本`（.md、.sql、.txt、.word...）`的解析储存以及Git克隆代码库遍历切割存储。
- 后端，使用 Redis 存储知识库标签，用于检索展示使用。
- 后端，基于 Flux 编写流式会话接口，以及增加知识库检索功能。
- 运维，基于 Docker 部署 Ollama 环境，完成 DeepSeek 大模型配置。
- 运维，使用 Linux、Docker、Nginx 完成项目的打包、构建、上线！

虽然，知识库都有很多现成的工具。但研发的能力不是在于功能应用，而是具备这样的开发技能储备，在有需要的时候，可以举手🙋🏻‍♀️”我会，我来做！“

> 此项目，全程视频手把手操作 + 全部的小册文档，你可以轻松上手学会这样一个项目！

## 二、项目介绍

这是一套基于 Ollama DeepSeek 大模型构建的增强 RAG 知识库检索项目，在这套项目上，实现了除普通文档知识解析外，增加了 Git 代码库的拉取和解析，并提供操作接口。为工程师做项目开发时，`需求分析`、`研发设计`、`辅助编码`、`代码评审`、`风险评估`、`上线检测`等，做工程交付提效。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-1-02.png" width="950px"/>
</div>

### 第1期，RAG 我们做了什么

在 《DeepSeek RAG 增强知识库》第1阶段，基于 Spring AI 0.8.1 开发了一套可以上传文件和Git仓库进行解析、切割、存储，到使用向量库完成 AI 的知识库问答系统。并最终通过 Docker 部署上线。

#### 1. 对话页面

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-1-03.png" width="950px"/>
</div>

- 这是全程视频手把手，带着大家通过AI工具，完成的UI设计实现课程会演示这个操作），实现的一款非常简单漂亮的UI效果。
- 我们可以结合知识库，进行更加有效的提问。像是公司中，会把知识库提供出一个标准接口，给其他各个AI应用平台提供能力。

#### 2. 上传知识

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-1-04.png" width="650px"/>
</div>

- 上传知识，可以解析不同类型的知识库。
- 除了课程提供的文档库、代码库，你可以增加其他的知识库，如；网页的解析，与网页内容对话。让我们的UI，增加一个侧边栏，读取当前网页内容，分析对话。这样在公司中的一些工程的日志，错误分析时，可以更快的处理。

#### 3. 解析知识 - 后台日志

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-1-05.png" width="850px"/>
</div>

- 上传知识后，可以看到日志信息。
- 一套工程作为知识库是非常具有开发价值的，在我们做提问的时候就不需要，人工的去分析工程，而是直接使用了。

### 第2期，MCP 我们要做什么

与第2期相比，第1期可以称之为小试牛刀，让小伙伴们以最快、最快的往事，积累，运用 Spring AI 框架，开发自己的 RAG 知识库。~~也是方便有些死鬼，早点写到简历上~~

到了第2期，你就开始吃上细糠了，小傅哥会带着你升级 Spring AI 框架为 1.0.0-M6 最新版本，多模型配置和操作 PG 向量库，使用 GPU 搭建响应速度更好的 Ollama DeepSeek 大模型（秒级处理），以及对接官网 DeepSeek 的大模型和统一 one-api 对接方式。

但这还只是开始，随着基础框架的升级完成，我们将进入 MCP 服务的开发实现。通过 AI 指令，完成 AI 工作流，调度各项 MCP 处理我们的任务作业。如图，举例操作；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-11-03.png" width="950px"/>
</div>

- 基于 MCP 服务的开发和对接，通过 AI 工作流指令，完成数据的采集和存放动作。💡 聪明的小伙伴以及开始联想，基于这样的 AI 开发，可以替代很多的日常工作啦。**没想到吧，也把自己替代了** 但仍然，蠢蠢欲动（我不做，别人也做呀）！~~实现后，晋升又有的讲啦！简历也有东西写啦！~~
- 有了 MCP 后，相当于把我们需要；在一个网页操作数据库查询数据、打开另外一个网页看天气预报，再手动的创建个文件把以上的信息获取后，复制粘贴到文件里。这一些列操作，都让 AI 通过 MCP 模型上下文协议进行处理。也就是 AI 可以调用后台接口啦！

### 第3期，Agent 我们要做什么

RAG 教了，MCP 搞了，那么现在是时候，实现一套自动化的 Ai Agent 服务了。

如图，以通过数据库表动态配置的手段，完成相关物料的加载，包括；`模型（gpt-4.1/deepseek）`、`客户端`、`对话预设`、`执行规划（Planning）`、`顾问（记忆、RAG、日志）`、`工具（MCP`）等，在把单个 Client 串联，完成整个 Agent 调用链。这样一个 Agent 调用链可以以对话形式使用或通过 Agent 动态任务自动执行。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-0-01.png" width="800px">
</div>

本项目分为，用户端、管理端和服务端，服务端统一提供接口能力，管理端维护 AI Agent 智能体配置、用户端提供使用服务。

#### 1. 登录界面

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-20-01.png" width="950px">
</div>

- 这一部分在数据库表增加了 admin_user 表，有配置登录账号和密码，可以简单做校验。

#### 2. 管理界面

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-20-02.png" width="950px">
</div>

- 管理后台目前提供了，代理管理（拖拉拽编排方式配置智能体），资源管理（model、client、mcp、advisor、prompt）
- 数据分析、系统设置，是样例，你可以继续扩展你所需要的内容。

#### 3. 代理管理

##### 3.1 代理列表

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-20-03.png" width="950px">
</div>

- 这里的代理列表，就是通过拖拉拽配置的智能体。可以点击【查看】看到明细，也可以【新建】，还可以删除。
- 点击【加载】则是调用服务端，把数据加载到 Spring 容器，之后就可以使用了。

##### 3.2 代理配置

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-20-04.png" width="950px">
</div>

- 当你点击一个代理配置，则会展示出拖拉拽的数据到页面上。这部分会从数据库读取，之后展示出来，全部可视化。
- 如果你点击了Save则会做出一份新的，之后对于旧的，你可以自己手动删除。

#### 4. 资源管理

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-20-05.png" width="950px">
</div>

- 资源管理下，是配置一个智能体所需的各项资源信息，你可以在这里进行维护。如，MCP 工具管理。

#### 5. 页面使用

##### 5.1 对话交流

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-20-06.png" width="950px">
</div>

##### 5.2 场景解析

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-12-01.png" width="950px">
</div>

##### 5.3 监控分析

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-14-07.png" width="950px">
</div>

- 配置后的智能体，可以在智能体选择里进行获取使用。之后进行提问。
- 效果还不错，这里小傅哥验证了配置的智能体进行提问。

## 三、关于系统设计

本套系统设计，也是花费了非常大的心思。

### 1. 执行流程

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-15-01.png" width="650px">
</div>

在整个 Ai Agent 的实现中，小傅哥带着大家分析设计了4种方案，包括；固定执行的、循环执行的、智能分析决策的还有一个按照步骤规划的。这些流程都有适合于自己业务场景使用。在代码中也都有不同方案的实现，之后通过用户选择后进行动态化的策略调度。

### 2. 核心动作

#### 2.1 数据装配

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-9-01.png" width="750px">
</div>

- 首先，以构建 AiClientNode 的对话客户端为目的，已经完成了相关的元素实例化步骤。本节这里要处理的是，顾问角色的构建，以及构建 AiClientNode 节点。
- 之后，AiClientNode 的构建，是关联了其他各项元素的，所以在构建时，需要在 AiClientNode 节点，从 Spring 容器通过 getBean 的方式，检索到对应的各项元素。

#### 2.2 动态调度

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-17-01.png" width="750px">
</div>

- 这里会根据用户的请求，进行策略路由，找到所需的 Ai Agent 执行策略进行处理。这里小傅哥也有意加入不同的策略，让大家可以看到很多的 Ai Agent 设计思路。

#### 2.3 执行策略（01）

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-ext-250810-07.png" width="750px">
</div>

- 以程序启动为开始，进行自动化装配。这个过程我们先把一些想预先启动的数据库中的 agent 配置所需的 client 客户端进行服务初始化。之后写入到 Spring 容器，方便在执行 Agent 时进行使用。`前面有伙伴问，为什么把实例化的对象写入到 Spring 容器，这里就是原因`
- 客户端（UI），进行 POST 接口请求，这个过程需要封装一个 SSE 流式响应的接口，让 Step 1~4 各个执行步骤，把过程信息写入到流式接口。这里要注意，需要给接口返回的**对象**添加上对应的类型（什么步骤、什么节点、什么过程），以便于反馈给用户 Agent 在做什么。

#### 2.4 执行策略（02）

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-16-01.png" width="750px">
</div>

- 这是其中的一种 Ai Agent 执行策略方式，通过用户的提问进行分析、规划、列出执行步骤，之后依次执行。
- 所有的这些实现都有相应的代码，带着大家使用规则树框架清晰的实现出来。

## 四、课程目录

### 1. 课程目录

**第1阶段** spring-ai v0.8.1 - RAG 静态知识库（本阶段，需要配置附件的 setting.xml）

- 第1节：关于 AI RAG 知识库项目介绍
- 第2节：初始化知识库工程&提交代码
- 第3节：Ollama DeepSeek 流式应答接口...
- 第4节：Ollama DeepSeek 流式应答页面...
- 第5节：Ollama RAG 知识库上传、解析和验证
- 第6节：Ollama RAG 知识库接口服务实现
- 第7节：基于AI工具，设计知识库UI和接口对接
- 第8节：Git仓库代码库解析到知识库
- 第9节：扩展OpenAI模型对接，以及完整AI对接
- 第10节：云服务器部署知识库（Docker、Ngin...

**第2阶段** spring-ai v1.0.0 - MCP 动态知识库

- 第11节：吃上细糠，升级SpringAI框架
- 第12节：康庄大道，上手 AI MCP 工作...
- 第13节，道山学海，实现MCP自动发帖服务（...
- 第14节，海纳百川，上线MCP自动发帖服务
- 第15节，川流不息，实现MCP微信公众号消息通知服务
- 第16节：息息相通，MCP 服务部署上线（sse 模式）

**第3阶段** spring-ai v1.0.0 - Ai Agent 进行中「如果着急面试，可以直接做3阶段，完成到13节很够面试啦」

- 第3-0节：Ai Agent 项目介绍和系统演示【最初版本，含完整代码】
- 第3-1节，Ai Agent 业务流程、系统架构、库表设计说明
- 第3-2节：初始化项目工程
- 第3-3节：Ai Agent 测试案例
- 第3-4节：根据 Ai Agent 案例，设计库表
- 第3-5节：多数据源和Mapper配置
- 第3-6节：数据加载模型设计
- 第3-7节：动态实例化客户端API
- 第3-8节：动态实例化对话模型
- 第3-9节：实例化对话客户端
- 第3-10节：Agent执行链路分析
- 第3-11节：Agent执行链路设计
- 第3-12节：Agent服务接口和UI对接（第一版AutoAgent效果）
- 第3-13节，Agent-ELK日志分析场景
- 第3-14节，Agent-Prometheus监控分析场景
- 第3-15节：AgentFlow执行链路分析（扩展思路）
- 第3-16节：FlowAgent执行链路设计（扩展思路）
- 第3-17节：增加调度器策略执行Agent链路
- 第3-18节：动态执行智能体任务
- 第3-19节：拖拉拽编排数据存储
- 第3-20节：Agent管理后台实现
- 第3-21节：在云服务器部署上线

### 2. 编程环境

- JDK 17 ~ 21
- Postgresql
- SpringBoot 3.2.3 - Spring AI 0.8.1 ~ 1.0.0+
- Redis
- Docker
- Ollama + DeepSeek + GPU -
- RAG、MCP、Function Call

课程包括文档 + 小册，全程视频带着做。课程地址：[https://t.zsxq.com/GwNZp](https://t.zsxq.com/GwNZp)

> 综上，你可以看到此套项目的完整的介绍，这些也都是企业里非常实用的技能积累。有希望提高自己的编程能力和面试材料的，可以马上加入学习。

---

课程包括；视频、小册、1 对 1 答疑解惑、专属VIP项目交流群，并且提供简历编写模板结构的一条龙🐲服务。让你学习后，直接拉开与还在玩具项目其他人的差距，面试脱颖而出提高竞争力！！！

