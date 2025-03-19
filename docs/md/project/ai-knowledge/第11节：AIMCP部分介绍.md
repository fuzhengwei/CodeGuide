---
title: DeepSeep MCP 动态知识库 - AI工作流&智能体
lock: no
---

# 《DeepSeep MCP 动态知识库》 - AI工作流&智能体

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>课程：[https://t.zsxq.com/GwNZp](https://t.zsxq.com/GwNZp)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

用不了多久，各大互联网企业都将大量的推进落地，自有 [MCP](https://github.com/modelcontextprotocol) 服务的实现，用于增强企业 AI 应用的提效能力。因为 [MCP](https://github.com/modelcontextprotocol) 的加入，可以让你；一条命令`帮研发`，调用应用系统日志、排查系统CPU负载、自主选择是否调度数据库信息。也可以一条命令`帮运营`，搞定复杂的SQL执行、导出报表、分析数据、完成促活营销券的自动化配置上架。这就是 [MCP](https://github.com/modelcontextprotocol) 的魅力！👍🏻

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

好啦📢，这就是小傅哥即将为你开启的**《DeepSeek RAG 增强知识库》**第2阶段，`MCP 服务开发和对接`。让你具备 AI 开发能力，遥遥领先于还在扣 CRUD 项目的小伙伴，做面试最牛的仔，最职场最靓的人！

> Spring AI MCP 与 24年末发布，学习此 AI 应用开发项目，你将是第一批具备 Java AI 应用实战开发能力的人。竞争力，嘎嘎滴！

## 第1期，RAG 我们做了什么

在 《DeepSeek RAG 增强知识库》第1阶段，基于 Spring AI 0.8.1 开发了一套可以上传文件和Git仓库进行解析、切割、存储，到使用向量库完成 AI 的知识库问答系统。并最终通过 Docker 部署上线。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-11-02.png" width="950px"/>
</div>

- 基于，RAG 这样的一套知识库，你可以完成文本和代码库的解析。以及自行扩展提供出 RAG 知识库的接口，对接到 OpenAI 代码自动评审（小傅哥社群的另外一个项目），增强代码评审效果。
- 在这套项目中，可以学习到 Ollama DeepSeek 本地化&云服务部署，以及流式接口的开发、基于 AI 完成页面与知识库对话接口的处理，Linux、Docker 的部署等。小项目不大，但非常锻炼人！

## 第2期，MCP 我们要做什么

与第2期相比，第1期可以称之为小试牛刀，让小伙伴们以最快、最快的往事，积累，运用 Spring AI 框架，开发自己的 RAG 知识库。~~也是方便有些死鬼，早点写到简历上~~

到了第2期，你就开始吃上细糠了，小傅哥会带着你升级 Spring AI 框架为 1.0.0-M6 最新版本，多模型配置和操作 PG 向量库，使用 GPU 搭建响应速度更好的 Ollama DeepSeek 大模型（秒级处理），以及对接官网 DeepSeek 的大模型和统一 one-api 对接方式。

但这还只是开始，随着基础框架的升级完成，我们将进入 MCP 服务的开发实现。通过 AI 指令，完成 AI 工作流，调度各项 MCP 处理我们的任务作业。如图，举例操作；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-11-03.png" width="950px"/>
</div>

- 基于 MCP 服务的开发和对接，通过 AI 工作流指令，完成数据的采集和存放动作。💡 聪明的小伙伴以及开始联想，基于这样的 AI 开发，可以替代很多的日常工作啦。**没想到吧，也把自己替代了** 但仍然，蠢蠢欲动（我不做，别人也做呀）！~~实现后，晋升又有的讲啦！简历也有东西写啦！~~
- 有了 MCP 后，相当于把我们需要；在一个网页操作数据库查询数据、打开另外一个网页看天气预报，再手动的创建个文件把以上的信息获取后，复制粘贴到文件里。这一些列操作，都让 AI 通过 MCP 模型上下文协议进行处理。也就是 AI 可以调用后台接口啦！

## 课程目录计划 💐

此课程会扩展很多基于 Java 的 AI 开发能力学习，帮助大家积累相关的场景解决方案。在这个过程中你可以最快的掌握最新的技术，早早的提前别人一步。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-11-00-1.png" width="150px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-11-04.png" width="550px"/>
</div>

- 全课程包括文档 + 小册，全程视频手把手带着做。
- 课程地址：[https://t.zsxq.com/GwNZp](https://t.zsxq.com/GwNZp) - 含全套项目代码和视频。

### 第1期 RAG Spring AI 0.8.1 - 完结

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

### 第2期 MCP Spring AI 1.0.0 - 开冲

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

课程包括；视频、小册、1 对 1 答疑解惑、专属VIP项目交流群，并且提供简历编写模板结构的一条龙🐲服务。让你学习后，直接拉开与还在玩具项目其他人的差距，面试脱颖而出提高竞争力！！！

>课程已经开始，早早加入，早早学习。时间、时机，比任何东西都珍贵！晚点学，只能是工具人。但早点学，就是开发工具的人！加入小傅哥的社群，如图的全套实战项目，都可以学习；