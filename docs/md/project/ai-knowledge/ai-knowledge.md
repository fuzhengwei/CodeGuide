---
title: DeepSeep RAG&MCP&Agent 智能体 - 解析文档&Git仓库代码&AI工作流
lock: no
---

# 《DeepSeep RAG&MCP&Agent 智能体》 - 解析文档&Git仓库代码&AI工作流

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

这套项目，小傅哥为它干了`1.87万`行代码（前后端），`14张`数据库表，全程动态化构建模块 Bean 对象，预热装配 Agent 服务。达到随用随配，自主组建出想要的各类的 Agent 功能服务。就以现在的丰富的 MCP 市场来可以说是，广阔天地，大有可为！在这套项目架构下，可以扩展出非常多的东西。

### 课程目录

#### 第1期 RAG Spring AI 0.8.1 - 完结

1. 【更】AI RAG 知识库，项目介绍&需求分析&环境说明
2. 【更】初始化知识库工程&部署模型&提交代码
3. 【更】Ollama DeepSeek 流式应答接口实现
4. 【更】Ollama DeepSeek 流式应答页面对接
5. 【更】Ollama RAG 知识库上传、解析和验证
6. 【更】Ollama RAG 知识库接口服务实现
7. 【更】基于AI工具，设计前端UI和接口对接
8. 【更】Git仓库代码库解析到知识库并完善UI对接
9. 【更】扩展OpenAI模型对接，以及完整AI对接
10. 【更】云服务器部署知识库（Docker、Nginx）

#### 第2期 MCP Spring AI 1.0.0 - 开冲

11. 【新】AI MCP 项目介绍
12. 【新】吃上细糠，Spring AI 框架升级 + GPU 部署 AI
13. 【新】吃上细糠，官网 DeepSeek + open-api 对接
14. 【新】MCP 服务的应用类演示和使用
15. 【新】MCP Spring  AI 客户端npx调用，以及资源讲解
16. 【新】MCP Spring AI 服务端webflux实现
17. 【新】MCP Spring AI 服务端 + 客户端对接使用
18. 【新】服务接口实现，增强 RAG 知识库 + MCP 服务使用
19. 【新】应用服务接口与前端页面对接
20. ... 随课程开发提供，包括后续的云服务部署。

#### 第3期 Agent Spring AI 1.0.0 - 进行中，本周末开更

0. 第3-0节，AiAgent项目介绍和系统演示
1. 第3-1节，Ai Agent 业务流程、系统架构、库表设计说明
2. 第3-2节，初始化工程和库表dao等，提交代码，讲解代码库使用
3. 第3-3节，硬编码方式讲解 Ai Agent 构建，为后续拆分做准备
4. 第3-4节，引入扳手工程，规则模型，整体设计Agent预热装配
5. 第3-5节，规则节点，RootNode 异步加载数据
6. 第3-6节，规则节点，AiClientToolMcpNode 工具MCP服务构建
7. 第3-7节，规则节点，AiClientAdvisorNode 顾问角色服务构建
6. 第3-8节，规则节点，AiClientModelNode 模型构建 bean 对象
7. 第3-9节，规则节点，AiClientNode 客户端构建
8. 第3-10节，Agent 服务预热和对话接口封装，使用验证
9. 第3-11节，知识库接口封装和使用
10. 第3-12节，智能体动态任务构建
11. 第3-13节，Admin 管理端 API 接口讲解（CRUD）
12. 第3-14节，Ai Agent 对话与 UI 页面对接
13. 第3-15节，构建镜像，上线云服务器
14. 第3-16节，Agent 场景玩法分享
14. ... 更多内容，随着课程开始逐步更新。

### 课程计划

课程已全部录制完成，计划在3月3日开更，3月16日之前全部剪辑更新完成。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-0-01.png" width="850px"/>
</div>

- 全课程包括文档 + 小册，全程视频手把手带着做。
- 课程地址：[https://t.zsxq.com/GwNZp](https://t.zsxq.com/GwNZp)

---

课程包括；视频、小册、1 对 1 答疑解惑、专属VIP项目交流群，并且提供简历编写模板结构的一条龙🐲服务。让你学习后，直接拉开与还在玩具项目其他人的差距，面试脱颖而出提高竞争力！！！

