---
title: 端到端，构建企业级知识库。我把它做成项目给你！
lock: no
---

# 端到端，构建企业级知识库。我把它做成项目给你！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

`从前车马慢，一个需求聊一天。现在端到端，今天提，明天上线！`

**怀念**，带着零食、拎着饮料，拉着研发，一聊就是一天。晚上，产品把 PRD 写好了，研发一行代码还没写，但肚子先填饱了。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-rag-01.png" width="450px"/>
</div>

现在是坏了，`转全栈`，`端到端`，今天的需求，明天上线。但这事怎么干，其实老板也没想清楚。就是，`我要`、`我要`、`我要`！

但干这么长时间，我先想明白了。`端到端` 最先需要的是一个企业知识库，而这个知识库还得需要`产研测`共同维护使用。而且是一边 AI 对话，一边就动态的维护知识库。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-rag-02.png" width="950px"/>
</div>

产研测协作链路，以知识库数据为核心，依托 MCP 协议实现多款差异化 AI Agent 工具互联互通，串联产品、研发、测试三类核心岗位，形成`需求撰写` — `代码开发` — `测试用例`编写的完整工作闭环。

产品，qclaw/workbuddy 编写 PRD 文档。研发，codex/walicode 编码开发。测试，trae.ai 编写用例。所有的角色都以统一的知识库为主。知识库可以分为核心主知识库，项目知识库和本次需求知识库。经过这样的多次迭代配和，录制整个流程行为，后续就可以设计成最终态，对轻量化需求，自主完成端到端交付。

**但这个事并不容易！**

想构建出一个企业级的实用知识库会涉及到；`数据建模`、`文档解析`、`tree-sitter AST 符号感知分块`、`Embedding 渠道复用`、`HNSW 向量索引构建`、`FTS5 全文索引`、`向量+关键词混合检索`、`RAG 问答（多轮对话 + Token 逐级回退 + Deep Research 多轮迭代）`、`MCP Server（Streamable HTTP + SSE 双传输 + 13 个工具 + JSON-RPC 2.0）等手段，才能做出一个好用的产品。

不过，冻哇瑞 🐸！我已经为你做了一套 `WaLiAPI` - `Local LLM Gateway` + 服务（知识库）+ MCP 对接。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-rag-03.png" width="950px"/>
</div>

这一套软件，可以轻松的帮助你完成知识库的维护和使用。还可以以 MCP 的方式对接，与 AI 聊着天，随时随地，就可以把一些知识，让 AI 动态的维护到知识库里。`请分析项目xxx核心接口流程，维护到知识库`、`基于以上编码实现，维护到知识库`、`请对我已经验证过的测试用例维护到知识库`。只有这种设计，不是固定死的知识库，才是有价值的知识库。

> 好啦，接下来小傅哥就告诉你，这套东西怎么使用，以及把实现教程分章节的给大家。即使你想自己做一套知识库也可以完成。

## 一、软件下载

WaLiAPI 是一个本地运行的 LLM API 网关服务软件，统一维护管理 LLM 渠道（负载） + 日志审计（脱敏），以及本次新开发的 RAG 企业级知识库能力。

- 官网：[https://walicode.xiaofuge.cn/](https://walicode.xiaofuge.cn/) - WaLiCode 官网，包括了配套软件 WaLiAPI 服务。
- 下载：[https://drive.weixin.qq.com/s?k=ACMA4AfQABU4S23jg8](https://drive.weixin.qq.com/s?k=ACMA4AfQABU4S23jg8)
- 代码：[https://github.com/fuzhengwei/WaLiAPI](https://github.com/fuzhengwei/WaLiAPI)

>注意，下载后要配置 LLM 模型渠道，确保渠道可用，有可使用的向量模型，推荐；`text-embedding-3-small`

## 二、功能使用

### 1. 新建知识库

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-rag-04.png" width="550px"/>
</div>

点击，【新建知识库】填写名称和描述，向量模型默认使用 `text-embedding-3-small` 注意不同的向量模型维度不一样，最好使用的时候保持统一。

### 2. 配置模型

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-rag-05.png" width="550px"/>
</div>

- 【新建知识库】后，在设置里，可以选择向量模型和绑定渠道，之后会使用这个渠道进行数据的处理。
- 其他的配置，也可以按需调整，如分块和过滤、排除目录、排除文件等。

### 3. 上传知识库

#### 3.1 文档上传

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-rag-06.png" width="550px"/>
</div>

可以单个上传 md、tst、json、yaml 等文件。也可以按照后续大家的反馈，来扩展文件类型。（代码是开源的可以提交 PR）

#### 3.2 来源上传

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-rag-07.png" width="550px"/>
</div>

来源上传，支持 Git 仓库、URL、本地目录，这样可以非常方便的把代码库转换为知识库。

### 4. 检索

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-rag-08.png" width="650px"/>
</div>


在【检索】下，可以测试上传的知识库，这里有一些快速检索的标签，方便用户测试验证。之后 `#1`会展示命中相似度，看到我们上传的文件知识库的可靠性。

### 5. 问答

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-rag-09.png" width="650px"/>
</div>


可以和知识库进行对话，还可以加 Deep Research 对话完成后，你会看到命中的文档。通过这样的测试，可以清楚地知道文档对话信息。

### 6. 索引

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-rag-10.png" width="650px"/>
</div>

创建的知识库，会通过 hnsw 构建索引。如果，你新建知识库，也可点击重新构建索引。

> HNSW，全称 **Hierarchical Navigable Small World**（分层可导航小世界图），是一种**近似最近邻搜索（ANN）**算法，专门用来在海量向量中快速找到最相似的几个。说白了，**HNSW 是让向量搜索从"挨个比对"变成"按图导航"的技术。**

## 三、服务对接

### 1. mcp 服务

WaLiAPI 服务（MCP）提供知识库外部对接能力，这样可以极大的方便我们动态的维护知识库。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-rag-11.png" width="850px"/>
</div>

MCP 端点`http://127.0.0.1:8777/mcp/sse` 通过配置，可以在你的任意 AI IDE/Cli 工具配置 MCP 服务。这些可用工具，就都可以动态调用了。此外，你还可以把 MCP 封装为 Skills 技能，什么场景使用什么知识库，可以减少 prompt 提示词。

### 2. 对接使用

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-rag-12.png" width="650px"/>
</div>

大部分 AI 工具，你都可以直接通过对话的方式，告诉它把 `http://127.0.0.1:8777/mcp/sse` 服务配置上。并理解下工具的能力。此外你还可以告诉它，编写一个 skills 技能，更好的使用 mcp 服务。

> 配置完，就可以直接撸起来了，非常好用。如果是公司里，那么可以把 WaLiAPI 配置一个共用服务器上。

## 四、学习教程

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-rag-13.png" width="950px"/>
</div>

- 地址：[https://ai-agent-guide.xiaofuge.cn/](https://ai-agent-guide.xiaofuge.cn/)
- 说明：tree-sitter 符号感知分块、HNSW 索引、Token 预算管理、零外部依赖 RAG、MCP + RAG、Deep Research等内容，已经为大家做好了八股。

## 五、实战项目

为了让小伙伴，可以痛快的把 WaLiAPI 渠道管理、日志审计、以及知识库开发 + MCP 服务，都能学习到。小傅哥这里，做好实战项目，可以跟着学习。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/waliapi/waliapi-rag-14.png" width="350px"/>
</div>

- 课程：[https://t.zsxq.com/jftYn](https://t.zsxq.com/jftYn)
- 介绍：统一模型配置和负载 | 本地运行的 LLM API 网关桌面软件，转换各供应商 API 为 OpenAI 兼容协议。可配合 WaLiCode、Codex、Claude Code、QClaw 等 AI IDE，让你知道 AI 对话，到底在说啥。还能结合知识库完整使用。

