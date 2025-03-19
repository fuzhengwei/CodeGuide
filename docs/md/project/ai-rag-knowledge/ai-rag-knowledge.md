---
title: DeepSeep RAG 静态知识库 - 解析文档&Git仓库代码
lock: no
---

# 《DeepSeep RAG 知识库》 - 解析文档&Git仓库代码

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>课程：[https://t.zsxq.com/GwNZp](https://t.zsxq.com/GwNZp)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?isOutside=true&aid=114106895958308&bvid=BV1ty9sYtEkc&cid=28695397252&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

大家好，我是技术UP主小傅哥。

刚上周，老板说：`”把咱们招聘里也加一条，具备AI应用开发能力的优先！“`。是呀，现在越来越多的企业都在用AI开发能力提效了，如；聊天软件增加一键唯独信息归档提取、工作文档资料携AI对话分析、工程SQL语句脚本辅运营自动完成数据处理、代码编写用AI完成自动评审等等。这些都是在AI的基础上在构建应用，以后也会越来越多！所以，具备AI应用开发能力，也是每个工程师最应该具备的基础能力了。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-0-00.png" width="250px"/>
</div>

**跟着小傅哥学习，从不走偏！😄**

- 2022年底，ChatGPT 开始火爆。
- 2023年2月，小傅哥，开启了第一个基于AI的项目，ChatGPT AI 问答助手项目。让所有伙伴，都能学习到 AI 如何开发应用。
- 2023年4月，启动OpenAI(ChatGPT/ChatGLM)微服务应用体系构建大型项目，让大家可以用微信登录、微信支付/蓝兔支付，构建自己的可对外付费提供服务的 OpenAI。这一年上车学习的伙伴，很多做了自己的 AI 产品，除了提高编程技能，又小赚了一辆宝马。
- 2024年7月，结合企业诉求，开启 OpenAI + Github Acitons，实现代码自动化评审。这一年，不少伙伴在自己的公司中都有落地，个人也得到了述职晋升。
- 2025年3月，咱们再起启航，基于 Ollama 部署 DeepSeek，开发 RAG 知识库，解析文档和Git仓库代码。这个东西，将是企业中构建自己知识库的又一项非常重要的事情。有了知识库，AI 代码的自动评审，会更加精准，也可以辅助分析需求等。

那么，接下来小傅哥就细致的介绍下，本次开启的新项目，可以让大家学习到哪些知识，掌握哪些技术。

>🧧文末提供了小傅哥所有实战项目的获取方式，一次加入全部可学！建议早早学习，让自己的年龄和能力相匹配。

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

### 1. 对话页面

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-1-03.png" width="950px"/>
</div>


- 这是全程视频手把手，带着大家通过AI工具，完成的UI设计实现课程会演示这个操作），实现的一款非常简单漂亮的UI效果。
- 我们可以结合知识库，进行更加有效的提问。像是公司中，会把知识库提供出一个标准接口，给其他各个AI应用平台提供能力。

### 2. 上传知识

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-1-04.png" width="650px"/>
</div>

- 上传知识，可以解析不同类型的知识库。
- 除了课程提供的文档库、代码库，你可以增加其他的知识库，如；网页的解析，与网页内容对话。让我们的UI，增加一个侧边栏，读取当前网页内容，分析对话。这样在公司中的一些工程的日志，错误分析时，可以更快的处理。

### 3. 解析知识 - 后台日志

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-1-05.png" width="850px"/>
</div>

- 上传知识后，可以看到日志信息。
- 一套工程作为知识库是非常具有开发价值的，在我们做提问的时候就不需要，人工的去分析工程，而是直接使用了。

### 4. 课程目录

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


### 5. 课程计划

课程已全部录制完成，计划在3月3日开更，3月16日之前全部剪辑更新完成。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-0-01.png" width="850px"/>
</div>

- 全课程包括文档 + 小册，全程视频手把手带着做。
- 此项目，不耽误星球其他项目进度。**拼团**继续推进，马上开启三阶段分布式高并发场景设计实现。
- 课程地址：[https://t.zsxq.com/GwNZp](https://t.zsxq.com/GwNZp)

---

课程包括；视频、小册、1 对 1 答疑解惑、专属VIP项目交流群，并且提供简历编写模板结构的一条龙🐲服务。让你学习后，直接拉开与还在玩具项目其他人的差距，面试脱颖而出提高竞争力！！！

