---
title: AI MCP 再继续这么用，可就要”创业“变现了呀！
lock: no
---

# AI MCP 再继续这么用，可就要”创业“变现了呀！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>课程：[https://t.zsxq.com/GwNZp](https://t.zsxq.com/GwNZp)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

在 [AI MCP 24小时为我”打工“](https://mp.weixin.qq.com/s/sB4EaP6HMtj3VxK-VMD8nQ)，两周时间。已产生 `251篇文章`，带来`10万+阅读量`，`涨粉近1000人`！在这么搞下去，不得变现了哇！

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-knowledge-250413-01.png" width="150px"/>
</div>

嘿嘿，不过这点点成绩根本挡不住我前进的脚步👣！

AI MCP 的能力远不止于次，或者更准确的说是 AI Agent 的能力，还有非常多的场景可以运用，开玩笑讲，他会让 Java 再次伟大！这也是为什么 Spring AI 那么快速的迭代自己的框架设计，甚至不惜每次迭代还要推翻之前的结构模型，也要更好的支持未来 AI 工程的开发实现。

在有了 AI Agent 后，所有的 tob/toc 服务项目，都应该会变得更加智能，这会包括我们现在使用的各项软件，如；电商、出行、外卖等，也包括研发人员使用的各类技术软件。如果大公司还是牛马般的靠体力卷工时，甚至可能会被小公司的不断创新所掀翻。

好啦，那么这篇文章小傅哥会给大家介绍下 AI Agent 以及可运用的场景，帮助大家打开思路！

>文末提供了 AI RAG&MCP 实战编程项目，可以快速掌握 Spring AI 应用开发能力！

## 一、什么是 AI Agent

AI Agent 是整合多种技术手段的智能实体，其实现依赖于 Tools、MCP、Memory、RAG（Retrieval 增强检索生成） 等技术组件。但不是非得依赖全部组件才叫 AI Agent。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-knowledge-250413-02.png" width="550px"/>
</div>

AI Agents 是**系统** ，它通过赋予 **大型语言模型(LLMs)** **访问工具** 和**知识** 来扩展其能力，从而使 **LLMs** 能够**执行操作** 。

- **系统** ：将 Agents 视为一个由许多组件组成的系统，而不仅仅是单个组件，这一点很重要。在基本层面上，AI Agent 的组件包括：
  - **环境** - AI Agent 运行的定义空间。例如，如果我们有一个旅行预订 AI Agent，则环境可以是 AI Agent 用来完成任务的旅行预订系统。
  - **传感器** - 环境具有信息并提供反馈。AI Agents 使用传感器来收集和解释有关环境当前状态的信息。在旅行预订 Agent 示例中，旅行预订系统可以提供诸如酒店可用性或航班价格之类的信息。
  - **执行器** - 一旦 AI Agent 接收到环境的当前状态，对于当前任务，Agent 会确定要执行的操作以更改环境。对于旅行预订 Agent，它可能是为用户预订可用房间。
- **大型语言模型** - Agents 的概念在 LLMs 创建之前就已存在。使用 LLMs 构建 AI Agents 的优势在于它们能够解释人类语言和数据。这种能力使 LLMs 能够解释环境信息并制定改变环境的计划。
- **执行操作** - 在 AI Agent 系统之外，LLMs 仅限于根据用户提示生成内容或信息的情况。在 AI Agent 系统内部，LLMs 可以通过解释用户请求并使用其环境中可用的工具来完成任务。
- **访问工具** - LLM 可以访问哪些工具由 1) 它运行的环境和 2) AI Agent 的开发者定义。对于我们的旅行 Agent 示例，Agent 的工具受预订系统中可用操作的限制，开发者可以将 Agent 的工具访问权限限制为航班。
- **知识** - 除了环境提供的信息外，AI Agents 还可以从其他系统、服务、工具甚至其他 Agents 中检索知识。在旅行 Agent 示例中，此知识可以是位于客户数据库中的用户旅行偏好信息。

我们可以以一个人，作为视角来理解 AI Agent，Memory 记忆是人的大脑，Tools 是人的身体和四肢，Rag 知识库是过往的经验储备，MCP 是我们与外部的连接调用。而整个人就是这个智能体 AI Agent。

## 二、AI 智能体的工作原理

每个智能体都定义了角色、个性和沟通风格，包括具体指令和可用工具的说明。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-knowledge-250413-03.png" width="850px"/>
</div>

- **角色**：角色是智能体的核心特征，定义了其性格和行为方式。一个定义良好的角色可以帮助智能体在与环境和用户互动时保持一致性。随着时间的推移，智能体会通过经验积累和环境互动不断发展和完善其角色。
- **记忆**：智能体的记忆系统由短期记忆、长期记忆、共识记忆和情景记忆组成。短期记忆用于处理即时互动，长期记忆存储历史数据和对话，情景记忆记录过去的互动细节，而共识记忆则用于在智能体之间共享信息。通过这些记忆系统，智能体能够回忆过去的互动，适应新情况，保持上下文一致性，并从经验中学习以提高性能。
- **工具**：工具是智能体用来与环境互动并增强其功能的资源。它们可以是函数或外部资源，帮助智能体访问信息、处理数据或控制外部系统以执行复杂任务。工具根据其界面类型进行分类，包括物理界面、图形界面和基于程序的界面。通过工具学习，智能体能够理解工具的功能及其应用场景，从而有效地使用这些工具。
- **模型**：大语言模型 (LLM) 是构建 AI 智能体的基础，赋予智能体理解、推理和行动的能力。LLM 作为智能体的“大脑”，使其能够处理和生成语言，而其他组件则支持智能体的推理和行动能力。

> https://github.com/google/A2A 多个 Agent 间，可以使用 A2A 协议，完成 Agent 和 Agent 对接。

## 三、AI Agent 场景应用

### 1. BCP 智能巡检

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-knowledge-250413-04.png" width="800px"/>
</div>

- 场景：在日常的互联网toc场景的系统中，每天都会有不同程度的客诉，这些客诉问题需要进行大量的系统排查。
- 方案：将企业内的各项系统服务，开发出 MCP 服务接口（APIs），并为每个核心业务流程提供排查链路计划。在发生系统报警时，以报警为触达手段，排查系统日志、账户、Redis、Dev-Ops 服务等，给给出综合的解决方案。
- 其他：不同配置的 Agent 是可以连接通信的，一个 Agent 就是一个配置出来的 ChatClient 对话体。

### 2. 工具AI化设计

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-knowledge-250413-05.png" width="800px"/>
</div>

- 场景：其实现在除了编程方面，其他很多软件并没有那么快速的接入 AI Agent。如 trae.ai\cursor 都是编程的利器，可以更快速的迭代开发代码。那么同样的 SSH 链接云服务器的工具，也可以通过 AI Agent 进行扩展，提高我们的操作服务器部署软件的效率。
- 方案：设计 Linux SSH MCP Server 服务，同时提供 SFTP 以及云服务器的对接。再加上 Linux RAG 知识库。这样我们就可以以对话和编码的方式操作服务器。如，部署 JDK，安装 NodeJS，执行系统镜像的构建、推送、拉取、部署等操作。

### 3. toc场景AI化

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-knowledge-250413-06.png" width="800px"/>
</div>

- 场景：目前的大部分购物操作，都是人工的方式自己在平台检索，之后对于不懂的在进行客服提问，最后去下单。那么这里也可以加入 AI 操作，自主的完成购物、下单，以及将来退货的操作。
- 方案：为产品提供购物话术 RAG 增强检索能力，以及提供货物、下单、结算、物流的 MCP 服务。用户和 AI 对话过程中，完成货品的组装选择和下单。聊聊天就把东西买好了，还能给提供产品的使用和维护。

### 4. AI Agent 编排

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-knowledge-250413-07.png" width="750px"/>
</div>

- 当我们有大量的 MCP 服务实现、RAG 知识库沉淀后，会催生出 AI Agent 的编排能力实现。你可以按需组装一套自己的对话 AI Agent。
- 类似的场景实现：[https://github.com/n8n-io/n8n](https://github.com/n8n-io/n8n) - 付费的。不过很多企业会借助之前的 BPMN 实现的低代码，扩展出 AI Agent 编排实现。

```java
docker volume create n8n_data
docker run -it --rm --name n8n -p 5678:5678 -v n8n_data:/home/node/.n8n registry.cn-hangzhou.aliyuncs.com/xfg-studio/n8n:1.88.0
```

