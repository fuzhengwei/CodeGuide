---
title: 【更】第3-0节：Ai Agent 项目介绍和系统演示
lock: no
---

# 《DeepSeek RAG&MCP&Agent 智能体系统》第3-0节：Ai Agent 项目介绍和系统演示

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

**25年3月初**，小傅哥，带着大家开启了 DeepSeek RAG、MCP 项目，随后兴起 MCP 服务提供的热潮，包括；支付宝、百度、高德，等等大厂开启了 MCP 服务计划。直至现在，市面上已经有了场景非常丰富的 MCP 服务。**那些第一波跟着小傅哥学习的伙伴，早早的让简历上多了一笔 MCP 服务开发和使用的经验！** 

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/openai-01.jpg" width="150px">
</div>

**接下来，小傅哥将带着小伙伴，再往前多走一步！**

RAG 教了，MCP 搞了，那么现在是时候，实现一套自动化的 Ai Agent 服务了。

如图，以通过数据库表动态配置的手段，完成相关物料的加载，包括；`模型（gpt-4.1/deepseek）`、`客户端`、`对话预设`、`执行规划（Planning）`、`顾问（记忆、RAG、日志）`、`工具（MCP`）等，在把单个 Client 串联，完成整个 Agent 调用链。这样一个 Agent 调用链可以以对话形式使用或通过 Agent 动态任务自动执行。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-0-01.png" width="800px">
</div>

这套项目，小傅哥为它干了`1.87万`行代码（前后端），`14张`数据库表，全程动态化构建模块 Bean 对象，预热装配 Agent 服务。达到随用随配，自主组建出想要的各类的 Agent 功能服务。就以现在的丰富的 MCP 市场来可以说是，广阔天地，大有可为！在这套项目架构下，可以扩展出非常多的东西。

接下来，小傅哥就给大家，细致的介绍下这套项目，以及截图演示运行效果。

> 🧧 文末提供了全套 AI、RAG、MCP、Agent 项目、开发教程以及工程源码。此外还有非常多的互联网大厂项目（17个），都可以一并获取学习。

## 一、项目演示

这套项目的功能非常强大，全部都以 Agent 方式进行通信。所有的 Agent 都可以动态化配置，解耦的非常强👍🏻。接下来，小傅哥给大家演示下使用效果。

### 1. 前端页面

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-0-02.png" width="950px">
</div>

- 首先，我为智能对话体（MCP）配置了联网、CSDN自动发帖、文件操作服务。
- 之后，我们可以通过预设的提示词模板，来调用对应的 MCP 服务，也可以多个 MCP 一起调用。如联网检索文章、生成解答，发布到论坛，在把文章名称记录到本地文件。这一系列操作都是可以的。
- MCP 服务平台；

  - https://mcpfound.cc/
  - https://mcp.so/
  - https://sai.baidu.com/mcp

> 结合知识库、MCP、提示词规划、上下文记忆，可以有非常多的场景可以玩。后续小傅哥还会继续分享可玩场景。

### 2. 后台页面

#### 2.1 配置智能体（动态预热）

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-0-03.png" width="950px">
</div>

#### 2.2 动态任务

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-0-04.png" width="550px">
</div>

- 系统会自动的把任务加载到系统内执行，完成智能体的调用。
- 有了这个操作，你配置好的智能体，他就可以连续24小时的工作了。除了自动发文章，你可以配置出各种东西。比如特朗普推特、黄金、股票价格，每天早上出一个文件，邮件方式推送给自己。兼职美滋滋。

#### 2.3 MCP管理

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-0-05.png" width="950px">
</div>

- 市面上有非常的多的 MCP 服务，我们可以选择的这些服务来组装出我们的智能体。
- 系统支持配置 stdio、sse，两种方式。无论是自己开发的 MCP 还是市面的都可以使用。（课程中有教大家，基于 Spring AI 怎么开发 MCP 服务）

## 二、系统设计

### 1. 功能流程

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-0-06.png" width="600px">
</div>

- 如图，从上往下，以任务或会话方式，调用 agent 为目标，串联各个 client。形成内部处理 a2a 流程。
- 之后，对于 client 则由系统都动态的方式创建 bean 对象。运营在 ai agent 后台配置相关数据即可。

### 2. 库表设计

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-0-07.png" width="800px">
</div>

如图，为整个系统对应的数据库表信息；

- ai_agent_task_schedule，智能体任务调度配置表
- ai_agent，AI智能体配置表
- ai_agent_client，智能体-客户端关联表
- ai_client，AI客户端配置表
- 模型配置组；ai_client_model、ai_client_model_config、ai_client_model_tool_config
- 工具配置组；ai_client_tool_config、ai_client_tool_mcp
- 顾问配置组；ai_client_advisor、ai_client_advisor_config
- 提示词配置；ai_client_system_prompt、ai_client_system_prompt_config
- 知识库配置；ai_rag_order

### 3. 系统工程

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-0-08.png" width="400px">
</div>

- 如图，为整个系统的工程结构，分为 api、app、domain、infrastructure、trigger、types，六边形架构。（现在各个互联网都在落地 DDD，因为 DDD 比最早出来的几年，已经有了非常明确的规范）相关资料；[https://bugstack.cn/md/road-map/ddd-guide-01.html](https://bugstack.cn/md/road-map/ddd-guide-01.html)
- Domain 核心领域层，处理 Agent 的预热、对话、知识库、任务的操作。后续 Agent 相关都维护到这个领域包下。
- Trigger 触发器层，负责对外提供接口，让外部来调用。当有一些纯 crud 操作的流程时，这个架构下，会在 trigger 层直接调用基础设施层提供数据，而不需要在经过 domain 领域层，重复封装对象。

**注意** ai-agent-station 全套代码，可以直接获取后学习（持续更新最新方案）。之后课程会单独起一个 ai-agent-station-study 工程，带着大家从0到1学习。

