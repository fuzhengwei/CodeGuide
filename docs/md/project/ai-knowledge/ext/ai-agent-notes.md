---
title: Ai Agent 新项目，你要的简历模板来啦！
lock: no
---

# Ai Agent 新项目，你要的简历模板来啦！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

**简历怎么写？简历怎么写？简历怎么写？** 这群死鬼，一直催小傅哥，想让小傅哥把饭🍚喂嘴里。没办法，兄弟们着急秋招写简历了，让自己写总是感觉慌，不知道从哪下手。好啦，它来了，它来了，行了吧！

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-ext-250817-01.png" width="250px">
</div>

**你就说吧，各个大厂都在做，它能不重要？**

Dify、Coze、Claude，京东还开源了 JoyAgent，市面上互联网大厂做的 Ai Agent 产品可以说是如雨后春笋一般，越来越多了。Ai Agent 也成了互联网标配的业务场景，为各个应用业务提效，所以结于业务场景来看，Ai Agent 是有战略意义的。

所以，你想面试互联网类公司，在简历上多一笔关于 Ai 类的场景实现，总是能匹配到公司里更多的部门岗位。尤其是具备一些 Ai Agent 开发能力的，在现在看来，可以说是非常亮眼的存在。就趁现在吧，该让自己的简历增强下啦！🌶

>接下来，我会站在一个求职者的角度进行项目介绍和简历内容展示。可以作为你的参考。

## 一、运行效果

先看一眼系统架构和运行效果，之后我们对项目进行介绍和简历编写。—— 面试时，可能也会有面试官看你的项目运行效果，你可以把项目上线，也可以写一个技术博客文章，来介绍你做的项目。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-ext-250817-02.png" width="150px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-ext-250810-07.png" width="950px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-ext-250810-01.png" width="950px">
</div>

- 更多可查看，[关于 Ai Agent 项目介绍](https://mp.weixin.qq.com/s/S3UJY0aWbulQ2OZH9XVYmw)

## 二、项目介绍

面试官您好，本套 Ai Agent 综合智能体项目，主要为业务应用系统提效而构建，包括；需求文档分析、代码评审（可结合 openai 代码自动评审）、文档资料编写（+消息通知）、ELK 日志检索 + 普罗米修斯监控的智能 Ai Agent 分析等功能。

整套项目，抽象设计拆分了 Ai Agent 执行过程所需的各项组件（Advisor、Prompt、MCP）能力到数据库表中，使其具备自由配置编排组装的特性。以此方式可结合应用中实际场景诉求，编排类似 Diff 和智能分析 Coze 能力，达到需要什么场景就配置什么场景的 Ai Auto Agent。

该项目，在架构设计上使用了 DDD 分层架构进行设计，运用了组合模式的规则引擎构建执行链路，并结合工厂、策略、责任链等方式来处理具体流程细节（多种组合方式的Ai Agent执行过程），以此解耦系统功能的实现。这样就可以更加灵活方便的迭代各类扩展性诉求。

## 三、简历模板

**注意**：🙅🏻‍♀️不要直接复制粘贴简历模板内容，以此结构和描述方式，可以用你的个人第1学习视角来描述。包括；学习过程中的积累、检索的同类资料，以及对课程的扩展等多方面内容来编写简历。以下简历涵盖了课程 1~3 阶段内容；

- 项目名称：`Ai Agent 综合应用提效智能体`/`Ai Agent 智能巡检系统`/`Ai Agent 可编排服务系统` - 基于你的实际场景/目的，修改项目名称
- 项目架构：微服务架构、DDD 领域驱动四色模型、前后端分离设计、Agent 设计模式
- 核心技术：Spring AI（RAG、MCP、Advisor）、SpringBoot、MyBatis、MySQL、PGVector、Redis、React、flowgram.ai、Nginx、Docker
- 项目描述：本项目是一套面向业务应用系统提效的综合智能体（Ai Agent）解决方案，支持将执行过程中的各项能力（如Advisor、Prompt、MCP）抽象并存储于数据库，实现自由配置和灵活编排。用户可根据实际业务场景，动态组合和调整智能分析、代码评审、日志检索等功能模块，打造定制化的Ai Auto Agent，从而显著提升开发设计、编码、运维效率。
- 核心职责：
  - 以产品（PRD）服务诉求和多方面调研评审，设计出具有可编排能力的 Ai Agent 服务架构。并以 DDD 领域驱动建模，构建系统架构。
  - 拆解 Ai Agent 执行过程所需的能力组件，包括；Advisor 顾问角色记忆上下文和访问RAG知识库、Tool（Function Call、MCP）调用服务端（推文、通知、ELK、普罗米修斯监控等）、Prompt（提示词）、Model（对话模型）、Api（使用 one-api/自研sdk组件，统一转换其他各个模型为 openai 格式）
  - 设计通用对话分析模型，完成 Ai Agent 执行过程中所需的，问题分析、自主规划、精准执行、内容判罚（循环执行），直至输出最终结果。—— Ai Agent 可对不同步骤配置不同的 Model + MCP + Prompt 能力。并对执行过程中，通过 Advisor 顾问角色访问知识库和存储上下文数据。
  - 实现 MCP 服务能力，以 stdio/sse 方式，开发，公众号通知 MCP、推文 MCP（可以是内部的文档化服务）、ELK-MCP、普罗米修斯-MCP等。以及使用 MCP 服务平台，检索公用能力 https://sai.baidu.com/zh/（本地文件、Github、搜索引擎），统一配置使用。—— 数据库设计了多类型 MCP 服务的配置操作。
  - 设计通用 MCP Nginx Token 校验能力（也可以设计 MCP-GateWay），以配置化方式进行鉴权使用。增强 MCP 调用过程中，数据传输安全性。
  - 基于 Spring TaskScheduler 扩展实现，智能体任务调度服务，可自动化完成日常系统巡检（客诉、报警）产生 html 格式报告文档。也可以基于报警监听消息，触发巡检动作（公司内，报警信息有 MQ 消息）。
  - 提供 RAG 知识库能力，可自主上传文件 + 解析工程代码库，并对知识库设有标签为 Ai Agent Advisor 访问 RAG 提供数据使用能力，增强准确性。—— 解析的代码库，可以为 openai 代码自动评审，增强评审能力。
  - 设计一键 Ai Agent 预热能力，动态化注入 Spring 容器。支持运营配置服务，随时调整、变更、上线，方便运营配置和使用。
  - 基于 Racet + flowgram.ai 框架，为 Ai Agent 服务提供拖拉拽编排能力，增强运营使用体验。

> 此套 Ai Agent 以为企业/平台/系统，上线3个月以来，主动巡检解决数十次系统隐患问题和运营配置错误情况，以及撰写了上万篇有效文档 + 提炼技术关键信息对新人辅导。后续还会继续配置更多方面的 Ai Agent 服务能力，为企业提效。

## 四、课程目录

整个课程分3个阶段讲解，包括；RAG、MCP，之后进入 Agent 阶段的学习。前面打好基础，后面进入应用。哪怕是小白，也可以跟着一起实战起来，而且每个阶段都有部署运行效果，越学越爽。

项目地址：[https://t.zsxq.com/GwNZp](https://t.zsxq.com/GwNZp)

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-ext-250810-08.png" width="650px">
</div>