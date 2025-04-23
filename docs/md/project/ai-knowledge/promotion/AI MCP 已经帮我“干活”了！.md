---
title: AI MCP 已经一天24小时，给我“打工”了！
lock: no
---

# AI MCP 已经一天24小时，给我“打工”了！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>课程：[https://t.zsxq.com/GwNZp](https://t.zsxq.com/GwNZp)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

一天24篇推文，500+收藏，2.6万+阅读量，我已经让 AI 帮我干活了！这是小傅哥最新实现的一套 `mcp-server-csdn`，完全不需要我上手，就可以定时的执行文章编写和发表。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-mcp-knowledge-250330-01.png" width="500px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-mcp-knowledge-250330-03.png" width="500px"/>
</div>

只要你配置好对应的话题，AI MCP 就会自动创作文章和发表。虽然单篇流量不一定都高，但架不住我根本没出手呀，走的就是一个量大取胜！**这不是自动发帖，这是自动创业呀😂！简直美滋滋！**

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-mcp-knowledge-250330-02.gif" width="100px"/>
</div>

MCP 模型上下文协议，核心的作用就在于通过标准的协议设计，让 AI 可以以通用的方式调用各类服务的接口。所以，我们可以通过 MCP 的实现，让 AI 自动化的完成内容的创作和发表。

在这样的一个 CSDN 发文章的模型跑通后，我们就可以大批量的复制，完成不同类内容的创作和发表，也可以以这样的方式对接其他各类内容社区平台。甚至你还可以想到，这东西能辅助我们完成很多工作。

以前总有人说，AI 来了，可能会让很多程序员失业。但恰恰相反，不仅不会失业，反而 AI 会让程序员如虎添翼，推进改变其他行业的工作模式。毕竟，没有哪个行业不仅能用 AI，还能懂 AI，还能开发 AI 了！

接下来，小傅哥就给大家，介绍下这套 MCP 的设计和工作模式。

> 🧧 文末提供了全套 AI、RAG、MCP 的开发、使用教程以及工程源码。此外还有非常多的互联网大厂项目，都可以一并获取学习。

## 一、MCP 的工作模式

MCP 服务，可以以工具 Tools 的形式配置到 AI MCP 客户端。当我们向 AI 发送执行指令后，AI 会携带工具 Tools 信息，一起发送给 AI。之后进行语义分析以及调用 AI MCP 执行业务诉求。

你可以把 AI MCP 当成你雇来的员工，你有什么想法就告诉他，让他充当那个跑腿和执行的人。而你只负责下达指令和验收结果。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-mcp-knowledge-250330-04.png" width="650px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-mcp-knowledge-250330-05.png" width="650px"/>
</div>

如图，我们可以给程序设定一套话术描述，让它去做指定类型的文章创作和发表。在 AI 完成内容创作后，会调用 MCP 服务，传递接口入参值，再由 MCP 服务，完成文章的发表。

如果，我们希望发布的文章具备当前热点。可以增加一个 MCP 服务，这个服务来检索牛客网最新的求职面试信息，再加上各个大厂招聘要求描述信息等。基于这些信息先获取到面试热点话题，在结合话题与设定的面试场景话术一起生成对应的文章。**MCP 就像是你的员工，你可以交代A员工做什么后，交接给B员工继续处理。**

## 二、MCP 怎么开发的

首先，MCP 是一套标准的模型上下文协议，它不限制非得使用那种语言实现。如；NodeJS、Python、Java，都可以实现 MCP 服务。以 Spring AI 框架举例，Java 工程师可以，以非常简单的使用 Java 代码开发普通的业务逻辑，之后配置上 AI MCP 工具类注解和完成 Bean 对象的实例化即可。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-mcp-knowledge-250330-06.png" width="850px"/>
</div>

- 如图，正常的搭建 SpringBoot 应用程序，编写对接 CSDN 发帖接口。之后给服务方法配置上 Tool 工具注解。那么它就会被 Spring AI MCP 注册和使用了。
- 同时，一个工程里可以有很多的这样的服务和工具。比如你要开发一个各大平台集成的发帖/文章的服务，那么也可以增加其他的对接类在同一个工程里。最终配置上 AI MCP 的注册即可。

## 三、MCP 怎么对接的

把 Java 开发实现的 MCP 服务端，打包成一个 Jar，把这个 Jar 配置到 MCP 客户端工程里。即可完成 MCP 服务的调用。并且一个 MCP 客户端，也可以对接多套 MCP 服务，这些服务可以以 AI 工作流的形式完成自己的工作。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-mcp-knowledge-250330-07.png" width="850px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-mcp-knowledge-250330-08.png" width="850px"/>
</div>

- 如图，在 SpringBoot 工程中，增加MCP 服务的配置对接。这个时候 AI 对话就那可以拿到 MCP 工具进行对话。并将要执行的信息，通过 MCP 服务工具完成处理。
- 并且，你可以把 MCP 服务，打包构建镜像部署到（服务器/Nas），让它一天24小时的干活。用不了多久，你就成为某个领域内容的专家了！

## 四、MCP 怎么学习下？

小傅哥，已经为你准备好了一套 AI RAG、MCP、Function Call 实践编程课程，使用 Java + Spring AI 框架，增强自己的 AI 应用开发能力，迅速囤积编程技能！如下，课程目录，全程文档小册 + 视频带着你从0到1学习。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-0-01.png" width="850px"/>
</div>

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

1. 【新】AI MCP 项目介绍
2. 【新】吃上细糠，Spring AI 框架升级 + GPU 部署 AI
3. 【新】康庄大道，上手 AI MCP 工作流.md
4. 【新】道山学海，实现MCP自动发帖服务
5. 【新】海纳百川，MCP 其他服务实现
6. 【新】川流不息，官网 DeepSeek + open-api 对接
7. 【新】息兵罢战，服务接口实现，增强 RAG 知识库 + MCP 服务使用
8. 【新】应用服务接口与前端页面对接
9. ... 随课程开发提供，包括后续的云服务部署。

> AI RAG&MCP 只是小傅哥社群里众多项目的一个，这里还有非常多的牛皮项目，一次加入都可以学习到。并且，不断的更新迭代新项目，社群嘎嘎活跃！