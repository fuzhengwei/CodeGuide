---
title: 阶段性总结，Ai Agent 的重要性
lock: no
---

# 阶段性总结，Ai Agent 的重要性

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

还好，还好早早的就开始了 AI 应用技术的储备，让我吃了互联网职场的第一碗创新类应用的技术饭。

其实从最早 OpenAI 发布 ChatGPT 开始，我就开始使用其接口陆续开发了应用，2022年年尾-开发智能问答助手、2023年年初-OpenAI应用项目（可支付买额度对话）、2023年终-OpenAI 代码自动评审。

可以说，在 AI 应用类开发这条路上，我一直走的很快也很靠前。甚至，我也思考怎么让 AI 识别接口，具备调用能力，而不是就只是做一些对话类操作。直至2024年他来啦！

2024年11月份，Anthropic 公司推出了 MCP（Model Context Protocol，模型上下文协议）开放标准协议（JSON-RPC 2.0），其核心目标是通过提供一个标准化的接口，使AI模型能够无缝地访问本地和远程资源。

如果没有 MCP 可以说就没有 AI Agent 智能体，也不会有现在那么多的结合于 AI 来为工作提效的场景。

所以，当我看到 AI MCP 那一刻，我是很兴奋。也在 AI MCP 协议出了不久，立马筹备新的 AI Agent 项目，让大家都能学习到这一技术。而且我也早早的告诉过大家 **用不了多久，各大互联网企业都将大量的推进落地，自有 MCP 服务的实现，用于增强企业 AI 应用的提效能力。** 随后，我们看到了阿里支付宝 MCP、高德地图 MCP、百度搜索 MCP，等等各类 MCP 服务如雨后春笋一般。再往后各个公司推出了各种的 Ai Agent 能力，因为 AI 可以调用 MCP 服务，真正的帮我们做一些事情。

现在，公司里随便一个功能服务接口（RPC、HTTP），都可以通过简单配置走 MCP 网关转换为 MCP 协议接口能力，让 AI Agent 通过配置即可完成调用。也就是说，通过配置接口 + Prompt 提示词，既可以承接绝对部分日常工作，如；客诉排查、日志分析、监控巡检、文档评审、单测开发等等。

老板总说，我需要的不只是 AI 开发能力的人才，而且他还要懂得业务，具备应用项目落地能力。这样才能为我们企业的场景提效，而不是天马行空。所以，想学 AI 的伙伴来说，不要只是一头扎入到 AI 里，也要多积累业务场景经验。

## 一、市面的开源资料

**86.5k Star！** 关注 Ai Agent 实现的人超级多，也都知道这是一个热门方向。项目：[https://github.com/x1xhlol/system-prompts-and-models-of-ai-tools](https://github.com/x1xhlol/system-prompts-and-models-of-ai-tools)

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-ext-250921-01.png" width="850px">
</div>

这是一款开源系统提示词项目，包含了市面的热门产品 Trae、Cursor、Claude、v0 等20多款 Ai Agent 项目的提示词 + MCP Tool。非常适合需要做 Ai Agent 项目的伙伴借鉴使用。

小傅哥还复制下来 Trae.ai 提示词（不过可能不是最新版）的内容进行本地验证，配置百度搜索 MCP 服务 + 本地文件处理。经过提问后，可以在本地创建出对应的文件夹内容，如果在结合进去 Shell 脚本能力，会更好。如果想要更好的效果，可以完全配置出一套 Trae 所需的工具服务组，那么效果会更好。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-ext-250921-02.png" width="850px">
</div>

目前 Ai Agent 的开发实现，主要为；`分析`、`规划`、`执行`、`验证`，以及在这个过程中循环检测执行，对结果进行确认校验继续分析。可能将来也会由 OpenAI 主导，设计一套 Ai Agent 框架结构组件，那么 Ai Agent 的开发门口将更低也会更通用（目前就有不少这样的 Auto Agent 框架）。

## 二、也快接近尾声啦

2025年3月3日，小傅哥带着大家从 RAG 开始，之后进入 MCP，后来又做到 Ai Agent，持续了半年多。这块也快收尾了，核心功能都已经完成，陆续的开始做一些收尾的章节。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-ext-250921-03.png" width="850px">
</div>

- RAG 10节、MCP 6节、Ai Agent 18节（还有待更新的），目前整体34节，后续整体都做完预计到40节，10.1假期后差不多。

## 三、总结下项目内容

### 1. 页面效果

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-17-04.png" width="950px">
</div>

- 如图，用户可以自己选择一个对话场景的智能体，之后后端会根据用户的请求进行动态化策略调度。
- 目前，已经添加的场景有；CSDN发帖 + 通知（同类小红书也可以做）、智能对话分析、ELK日志检索分析、智能监控分析服务。只要你学习了这套系统，就可以设计出你所需要的 Ai Agent 使用场景。

### 2. 执行流程

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-15-01.png" width="650px">
</div>

在整个 Ai Agent 的实现中，小傅哥带着大家分析设计了4种方案，包括；固定执行的、循环执行的、智能分析决策的还有一个按照步骤规划的。这些流程都有适合于自己业务场景使用。在代码中也都有不同方案的实现，之后通过用户选择后进行动态化的策略调度。

### 3. 核心动作

#### 3.1 动态调度

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-17-01.png" width="750px">
</div>
这里会根据用户的请求，进行策略路由，找到所需的 Ai Agent 执行策略进行处理。这里小傅哥也有意加入不同的策略，让大家可以看到很多的 Ai Agent 设计思路。

#### 3.2 执行策略

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-16-01.png" width="750px">
</div>

- 这是其中的一种 Ai Agent 执行策略方式，通过用户的提问进行分析、规划、列出执行步骤，之后依次执行。
- 所有的这些实现都有相应的代码，带着大家使用规则树框架清晰的实现出来。

好啦，欢迎感兴趣的伙伴一起加入学习，小傅哥的社群，有非常多的实战项目，涵盖；业务、组件、框架、源码、开源、创新等，让你加入后，就等同于加入一个互联网大厂的核心项目组，各项信息都能全部接触到，甚至包括产品 PRD 文档。**因为小傅哥就是大厂架构师，所以也是按照一个我所在的核心的组的方式来给大家建设与之匹配的资料内容。**

