---
title: 有方案了，让自研 Ai Agent 可视化编排！
lock: no
---

# 有方案了，让自研 Ai Agent 可视化编排！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家伙，我是技术UP主小傅哥。

每当一项新技术问世，市场上总会涌现出一批基于该技术的热门项目。以 AI 技术为例，紧随其后便出现了 `n8n`、`dify`、`coze` 等各类相关应用产品（当然，他们很优秀）。

然而，随着技术逐渐成熟和稳定，这些通用的解决方案往往会逐渐淡出市场。为什么呢？这是因为，各个企业下场后，都开始基于自身的业务，在细分领域，做自己的 Ai Agent 服务啦！所以，什么才是最重要的呢？

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-springcloud-feign-01.gif" width="150px">
</div>

**什么才是最重要的呢？**

从研发的角度来看，最关键的是；学习业务场景经验、积累技术架构方案、落地应用项目能力。相比市场上层出不穷、功能纷繁复杂的各类产品，真正有价值的是对技术本质的持续储备。只有具备扎实的技术功底，形成闭环的技术体系，才能自身核心价值能力。

有了这样的能力积累，你可以在任何一个公司，任何一个场景，架构出一套最为符合的业务系统。所以，你的能力也等同于你的级别和薪资。

所以，对于最火的 Ai Agent 不要只是会用就行了，拿个项目过来，部署上就觉得可以了。你要做的，是把全套的业务弄透彻，实现的方案搞下来，怎么编码整明白。好啦，开冲，今天给这套 Ai Agent 加上可视化编排方案。

> 🧧 文末提供了全套 AI、RAG、MCP、Agent 项目、开发教程以及工程源码。此外还有非常多的互联网大厂项目（17个），都可以一并获取学习。

## 一、拖拉拽效果

鉴于整个 Ai Agent 的配置，需要一大堆东西，如；执行频次任务、客户端串联、模型选择、顾问角色知识库、MCP 工具、提示词等，有一套可视化拖拉拽配置的前端页面，就显得非常有必要了。

因此小傅哥调研了不少具备图形化编排能力的前端组件，发现一套 [flowgram.ai（官网有文档，可直接阅读）](https://flowgram.ai/) 可以很好的满足当前 Ai Agent 编排能力。😄 并且上手不困难，效果还不错。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-250517-01.png" width="950px">
</div>

- 首先，我们要基于 [flowgram.ai](https://flowgram.ai/) 框架，开发自己需要的 Node 节点（后面会有代码说明，方便伙伴扩展）。这里增加了；task、agent、client、too-mcp、model。
- 之后，在页面点击添加节点，并选择好每个节点，应该配置的属性信息，以及连接节点关系。
- 最后，点击保存，他会给你一个json 对象，按照对象的结构，创建服务端接口即可接收和保存。（相关保存操作，会在后续课程中添加，如果有诉求可以先把前端代码下载下去，对照json开发接口即可）

> 本套全段代码 [ai-agent-station-front](#) 已经添加到课程中，可以进入获取。地址：[https://t.zsxq.com/GwNZp](https://t.zsxq.com/GwNZp)

## 二、怎么添加节点

### 1. 下载前端工程

首先，你要下载 [ai-agent-station-front](https://gitcode.net/KnowledgePlanet/ai-agent-station-front) 到本地使用 trae.ai 打开。如果没有提示你，要自己手动执行 npm install 初始化工程。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-250517-02.png" width="550px">
</div>

- 首先，[flowgram.ai](https://flowgram.ai/) 官网提供了各种案例，可以下载一个进行扩展。这里小傅哥就是下载好的一个，之后添加我们需要的节点。
- 之后，docs 下的 ai-agent-station.sql 为的是让 ai 可以使用，自动创建 node 节点的（下文有演示）。
- 另外，这里的代码，对于稍微有些 react 编码基础的伙伴，是可以非常方便看懂的。后端工程师，如果不懂也没关系，让 ai 来解答以及操作。

### 2. Ai 编码，添加节点

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-250517-03.png" width="950px">
</div>

- 首先，下载一份国际版 trae.ai，这里有使用文档。[https://bugstack.cn/md/road-map/trae.html](https://bugstack.cn/md/road-map/trae.html) 使用 cursor 也可以，其实这东西，重要的就是好用的模型。
- 之后，nodes 下是各种节点，我们可以拖拽一份让 ai 编码参考。并告诉ai，以哪个库表信息来编写新的节点。（有时候可能有问题，如果有问题，可以手动修改下）

## 三、点击保存，查看json

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-250517-04.png" width="950px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-250517-05.png" width="350px">
</div>

- 点击保存，就可以拿到节点和链接的关系数据了。这份数据是可以和数据库对应上。
- 后续课程从0到1的实现过程中，会和后端接口联动，存储数据。现在你也可以先下载前端代码，尝试编码。

