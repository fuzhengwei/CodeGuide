---
title: Ai Agent VS 字节扣子？
lock: no
---

# Ai Agent VS 字节扣子？

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

**媲美Dify，堪比扣子？🤔** 哈哈哈，经过哐哐的一顿卷，小傅哥这套 Ai Agent 项目，已经做出一版智能对话体（对接UI），在单一方面的功能体验上，确实可以和市面的 Ai Agent 产品握握手啦 🤝！效果很是不错，本文提供了视频和截图。

<div align="center">
    <img src="https://bugstack.cn/images/system/zsxq/xingqiu-231018-00.png" width="150px">
</div>

**什么是 Ai Agent？**

做的越多理解的也越透彻，Ai Agent 更像是对人的行为的理解。一个人再有力气，也没法一天完全一年全部的工作，而是需要我们把这些工作，做好规划、定好目标、完成执行、结果检查，循序渐进的执行再到最终交付。

那么，Ai Agent 也是一样的，Ai Token 提升的在高（等于人的力气），也没法一次做完所有事情。所以在结合了 `Prompt（提示词）`、`Advisor（记忆、RAG）`、`Tool（MCP）`，也可以把一整个大任务，细分为独立的小块，每一块做好规划、执行、审查和交付。所以 Ai Agent 再趋向一个人的行为。

**跟着小傅哥，吃上一碗细糠！** 可能你也做过一些学习的项目，但往往这些项目都是一些功能逻辑的叠加和 CRUD 的拼装 + 好看一些的脸面（UI）。但是跟着小傅哥学习，给你的就不只是一个项目，还会带着你做架构，搞设计，用优雅的方式进行编码，深度积累编程思维和编码能力。

>🧧 文末已提供整套 Ai Agent 实战项目的全套代码 + 各个分支章节的文档（含视频），学习起来非常爽！

## 一、先上效果 - 堪比扣子！

😂 做的多了，也做的久了。我发现，Ai Agent 所有模块架构好后，剩下的就是 Prompt 提示词质量的对比。**Prompt = Ai Agent 大脑！** 目前小傅哥演示的这套自研的 Ai Agent 就是反复优化 Prompt 的结果。

### 1. 对话场景 - 通用场景

#### 1.1 截图效果

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-ext-250810-01.png" width="950px">
</div>

左侧是AI思考执行过程，右侧是最终执行结果。通过我们的提问，AI Agent 进行`分析`、`规划`、`执行`、`监督`，再到最终的结果产生。有了这样的步骤，最终的总结阶段数据就会更加准确。

#### 1.2 视频效果（对比扣子）

占位符！！

### 2. 日志分析 - ELK 辅助提效（公司里非常需要这样的场景）

#### 2.1 采集分析

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-ext-250810-06.png" width="650px">
</div>

如图，是一个系统日志 ELK + Ai Agent 的运行简图，通过 MCP 服务的对接，让 Ai Agent 具备检索日志的能力，再结合分析话术，以此来完成日志的自动化分析。

#### 2.2 数据案例

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-ext-250810-02.png" width="650px">
</div>

在互联网公司中，都会有一套类似 ELK 的分布式日志系统，之后各个应用会上报数据。研发在接收到系统报警和运营反馈线上问题的时候，研发就需要进入到 ELK 查看系统日志的情况，以此分析线上问题。

#### 2.3 智能分析

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-ext-250810-03.png" width="650px">
</div>

通过 Ai Agent 分析 ELK 应用系统分布式日志，自动排除出限流用户的相关信息，来辅助研发日常工作提效。

## 二、系统设计 - 这是一个正经项目！

Ai Agent 会的模型架构会趋向稳定并形成标准，之后便是 Prompt + MCP + Client 多链路动态执行迭代和优化。所以，把架构定义好，具备强扩展性是非常有必要的，也是程序员👨🏻‍💻工作价值的体现。—— 堆功能只等于 demo 案例，驾驭架构解决复杂问题才是核心价值体现！

### 1. 库表设计

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-ext-250810-04.png" width="650px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-ext-250810-05.png" width="650px">
</div>

- 这里小傅哥设计了一套非常灵活的 Ai Agent 库表结构，满足动态配置各项资源，再由程序动态化的随时加载和使用。
- 有了这样的库表，我们就可以按需配置出多种使用类型的 Ai Agent，之后对话或者 Job 任务方式执行使用。

### 2. 数据加载

如图，整体 ChatClient 客户端实例化过程；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-9-01.png" width="800px">
</div>

- 首先，以构建 AiClientNode 的对话客户端为目的，已经完成了相关的元素实例化步骤。本节这里要处理的是，顾问角色的构建，以及构建 AiClientNode 节点。
- 之后，AiClientNode 的构建，是关联了其他各项元素的，所以在构建时，需要在 AiClientNode 节点，从 Spring 容器通过 getBean 的方式，检索到对应的各项元素。
- 注意，ai_client_system_prompt 系统提示词，需要修改为 Map 结构数据。这样更方便我们从数据里获取，哪些是属于当前 AiClientNode 构建时所需的元素。

### 3. 执行分析

如图，不同方案实现的 Agent 流程；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-10-01.png" width="800px">
</div>

Ai Agent 的处理过程也是分为几类的，用于适应不同的场景使用；

1. 固定N个步骤，这类的一般是配置工作流的，提高任务执行的准确性。如，一些检索资料、发送帖子、处理通知等。
2. 顺序循环调用，配置 Agent 要执行的多个 Client 端，以此顺序执行。适合一些简单的任务关系，并已经分配好的动作，类似于1的方式。
3. 智能动态决策，这类是目前市面提供给大家使用的 Agent 比较常见的实现方式，它会动态的规划执行动作，完成行动步骤，观察执行结果，判断完成状态和步骤。并最终给出结果。

### 4. 功能架构

如图，从Agent服务的装配到接口调用和响应的关系图；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-ext-250810-07.png" width="950px">
</div>

- 以程序启动为开始，进行自动化装配。这个过程我们先把一些想预先启动的数据库中的 agent 配置所需的 client 客户端进行服务初始化。之后写入到 Spring 容器，方便在执行 Agent 时进行使用。`前面有伙伴问，为什么把实例化的对象写入到 Spring 容器，这里就是原因`
- 客户端（UI），进行 POST 接口请求，这个过程需要封装一个 SSE 流式响应的接口，让 Step 1~4 各个执行步骤，把过程信息写入到流式接口。这里要注意，需要给接口返回的**对象**添加上对应的类型（什么步骤、什么节点、什么过程），以便于反馈给用户 Agent 在做什么。

## 三、课程目录

整个课程分3个阶段讲解，包括；RAG、MCP，之后进入 Agent 阶段的学习。前面打好基础，后面进入应用。哪怕是小白，也可以跟着一起实战起来，而且每个阶段都有部署运行效果，越学越爽。

项目地址：[https://t.zsxq.com/GwNZp](https://t.zsxq.com/GwNZp)

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-ext-250810-08.png" width="650px">
</div>