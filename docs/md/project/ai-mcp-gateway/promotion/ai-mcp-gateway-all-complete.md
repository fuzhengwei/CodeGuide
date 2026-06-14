---
title: 撒花，完结！AI 场景的，硬核技术项目！
lock: no
---

# 撒花，完结！AI 场景的，硬核技术项目！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>课程：[https://t.zsxq.com/SNsgH](https://t.zsxq.com/SNsgH)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥！

经历8个多月，开局一把 IntelliJ IDEA，`前后端 + DevOps`，全栈式编程。这是我在 [星球：码农会锁](#) 完成的第21个实战项目。全程文档、视频，手把手&渐进式，逐个章节的做`系统设计`、`协议分析`（mcp、json-rpc2），直至一行行编码完成。它，**《AI MCP Gateway》** 一套 AI MCP 分布式网关系统完结！

<div align="center">
	<img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-230905-01.png" width="150px"/>
</div>

**讲实话，这种项目做起来到交付，真的非常耗费心血！**

做个demo不好吗，搞个玩具不能吗，直接让AI窜个课程不行吗？**不，都不可以**。那些东西从本质讲根本没法给大家带来非常全面的，非常成体系的成长，即使有时候包装的很好看，但内核也是空虚的。所以，作为一个深耕技术多年（13年+）的架构师，真心更愿意做有价值的事，做长久的事。

为啥做**《AI MCP Gateway》** ，因为虽然不少伙伴都在做 AI 项目，但很多做的都比较虚，以至于面试的时候总是讲了几个技术关键字之后是车轱辘话。所以希望在 AI 这块给你加入一些有核心技术底层的，又与智能体项目关联度极强的AI MCP 网关项目。—— 阿里也有一套 Higress-AI 的网关项目，这是我们的对标物。

说到 AI，LLM、Agent WorkFlow（ReAct）、A2A、MCP、Skills、CLI...，以及一些技术框架。这些技术项，LLM 到 Agent ReAct 有框架包装，Skills 更多是提示词的定向聚合。扎到底层是 LLM 训练，跑到顶层是 Agent 智能体构建。我们本身做业务智能体的，又不是去搞 LLM 大模型底层，所以 AI MCP Gateway 就是一个很好的技术切入点。而这也恰恰是公司里都要做的事项，要把公司里各个服务接口，都可以平滑的过渡到 MCP 协议上，只各类场景的智能体对接，所以这就是它的核心价值！

那么，接下来小傅哥就细致的介绍下，本次完结的新项目，可以让大家学习到哪些知识，掌握哪些技术。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-agent-scaffold/part-1/1-1/images/ai-agent-scaffold-1-1-10.png" width="550px"/>
</div>

>文末提供了全套的 AI 实战项目，一次加入全部都可以学习💐。

## 一、能学到啥

该项目是 AI 应用场景下的通用技术服务组件类项目，以解决接口 MCP 协议转换而设计实现。在整个项目中，你可以积累到关于 MCP 协议的深度分析，学习分析协议的技巧和方案，并积累关于设计一个组件解决通用场景问题的能力。

- 【前端】基于 html、js、div、css，设计 MCP 协议分析页面。
- 【前端】基于 html、js、div、css，构建一套服务端管理系统，便于 MCP 协议的录入和使用。
- 【后端】MCP 协议的分析、理解、运用。掌握 MCP 开发和使用的能力。
- 【后端】基于 MCP 协议的分析和网关设计诉求，构建网关服务库表。
- 【后端】运用 DDD 分层架构，设计 MCP 网关服务系统。
- 【后端】构建AI代理服务，断点调试分析 MCP 协议。
- 【后端】设计 MCP 分析协议网页服务，链接 MCP 服务，观察 MCP 协议。
- 【后端】基于 Flux 响应式接口，设计实现 MCP 协议的 sse 连接、initialize 初始化响应、tools 工具的 list 反馈和 call 调用等。
- 【后端】提供 MCP 协议的动态录入和加载能力，以及提供录入接口组件，便于其他系统可快速录入。
- 【后端】设计 MCP 网关协议鉴权服务，确保 MCP 服务使用的安全性。
- 【后端】熟练使用 okhttp3、retrofit2 框架，动态对接 HTTP 服务接口，用于 MCP 协议 toos/call 工具调用。
- 【后端】扩展学习 rpc 泛化调用，给 MCP 协议提供使用。其实有了这套东西，还可以对接如硬件设备 rs232 串口通信，让 MCP 服务，管理你的硬件设备。
- 【后端】基于 Redis 发布订阅，设计分布式能力，自动化识别会话、服务通知、网关负载。
- 【运维】熟练使用 Docker 在本地和服务端的配置和部署应用，以及在本地构建前后端镜像。
- 【运维】熟练掌握 Git、GitCode，对工程代码的管理，推送、拉取、切换分支、合并代码等操作。
- 【运维】结合 AI 新范式（有一套独立项目），AI Shell 能力，自动化的压测（AB） + Arthas 链路性能分析。

此外，小傅哥对于每个章节还讲解了章节的诉求、流程的设计，之后再到方案实现和功能验证。并在每个章节留有作业让大家练习。当然这还没有完，你知道小傅哥这个架构师画图还是非常牛逼的，所以你还能看到各种画图的技巧，耳濡目染的把这些东西学习成自己的本事！~

## 二、项目介绍

本项目 AI MCP Gateway，以解决各类业务接口便捷转换为 MCP 协议而设计实现。通过这样的配置，可以大大的简化从普通http、rpc接口到 MCP 协议的转换操作。这样的项目，也是每个互联网公司在做 AI Agent 智能体时，必备的基础设施项目。

### 1. 项目部署

#### 1.1 云服务部署

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-4-1-10.png" width="950px"/>
</div>

- 分布式部署进行负载，这是一整套可靠的技术架构。

#### 1.2 分布式介绍

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-4-1-11.png" width="950px"/>
</div>

- AI MCP Gateway 分布式架构设计，ai-mcp-gateway 网关服务支撑横向扩展部署。通过 nginx 进行负载轮训。

#### 1.3 分布式架构

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-26-01.png" width="950px"/>
</div>

- 这是关于分布式架构设计的内核，有完整的会话服务通知体系，可以让各个加入、退出的实例节点，都具有会话服务。确保 MCP 会话不间断。

> 除此之外还有很多核心内容，包括 MCP 结合 Spring AI 源码的调试分析，还有 SSE 实现、Streamable 实现，双个协议的实现。这些东西都是你可以随着课程学习到的。

### 2. 管理后台

#### 2.1 网关列表

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-4-1-09.png" width="950px"/>
</div>

#### 2.2 协议配置

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-4-1-06.png" width="950px"/>
</div>

- 这里网关测试的是小傅哥上传并解析的 Swagger OpenAPI 协议，导入后，可以被识别为 MCP 网关协议，之后进行通信。

#### 2.3 网关测试

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-4-1-07.png" width="950px"/>
</div>

- 网关加入了一套 LLM 轻量的智能体，用于测试配置的网关服务是否可用。
- 这个完整的东西，拿出去给面试官演示，那不妥妥的让面试官给你发 Offer！

#### 2.4 测试日志

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-4-1-12.png" width="950px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-4-1-08.png" width="950px"/>
</div>

- 首先，当有新的会话发生的时候，可以查看 Redis 的分布式数据会话数据的存储。
- 之后，也可以在应用后台看到日志信息，网关服务请求的数据。

> 网关项目的制作，设计，编写，录制，都非常细腻，即使是小白伙伴也能跟着学习完成。课程中还有非常多的图文结合视频，来深度的讲解协议如何分析和设计，绝对抱你眼福！

## 三、课程大纲

**不同于网上的小demo项目，也不是那种 AI 快速窜出来的项目。** 这个项目是一步步，一个个章节的带着大家从0到1的方式，进行分析、设计和开发。是一个纯手把手教大家学习实战技术的项目！大家可以先看看课程的大纲，就知道可以学习到哪些东西了。

- 第1部分：系统设计
  - 1：《AI MCP Gateway》第1-1节：网关需求分析
  - 5：《AI MCP Gateway》第1-2节：系统建模设计
  - 11：《AI MCP Gateway》第1-3节：网关协议表
  - 16：《AI MCP Gateway》第1-4节：升级网关库表

- 第2部分：协议分析
  - 2：《AI MCP Gateway》第2-1节：MCP服务实现（用于后续协议分析）
  - 3：《AI MCP Gateway》第2-2节：MCP代理调用
  - 4：《AI MCP Gateway》第2-3节：MCP通信协议(json-rpc2) - 调试 Spri...
  - 29：《AI MCP Gateway》第2-4节：streamable协议应用案例 - streamable 协议开始
  - 30：《AI MCP Gateway》第2-5节：streamable协议应用分析

- 第3部分：网关实现（完成到26节可以写简历啦，由完整后台UI）

  - 6：《AI MCP Gateway》第3-1节：工程初始化创建
  - 7：《AI MCP Gateway》第3-2节：会话管理服务实现
  - 8：《AI MCP Gateway》第3-3节：会话接口编排
  - 9：《AI MCP Gateway》第3-4节：会话消息结构设计
  - 10：《AI MCP Gateway》第3-5节：消息协议处理案例
  - 12：《AI MCP Gateway》第3-6节：基础层数据处理(Dao)
  - 13：《AI MCP Gateway》第3-7节：协议消息处理-Initialize
  - 14：《AI MCP Gateway》第3-8节：协议消息处理-ToolsList
  - 15：《AI MCP Gateway》第3-9节：协议消息处理-ToolsCall
  - 17：《AI MCP Gateway》第3-10节：评审库表升级代码
  - 18：《AI MCP Gateway》第3-11节：会话内容编排处理
  - 19：《AI MCP Gateway》第3-12节：鉴权功能领域服务
  - 20：《AI MCP Gateway》第3-13节：鉴权功能编排处理
  - 21：《AI MCP Gateway》第3-14节：解析Swagger标准OpenAPI协议
  - 22：《AI MCP Gateway》第3-15节：协议域-协议解析处理
  - 23：《AI MCP Gateway》第3-16节：协议域-协议存储处理
  - 24：《AI MCP Gateway》第3-17节：网关配置域-配置数据存储(CRUD)
  - 25：《AI MCP Gateway》第3-18节：管理端-API功能编排串联
  - 26：《AI MCP Gateway》第3-19节：管理端-API与UI对接
  - 27：《AI MCP Gateway》第3-20节：验证服务，LLM对接测试MCP接口
  - 28：《AI MCP Gateway》第3-21节：验证服务，LLM对接测试MCP界面 - sse 协议完结
  - 31：《AI MCP Gateway》第3-22节：streamable-http-api，测试验证案例
  - 32：《AI MCP Gateway》第3-23节：调整case层结构设计，处理不同方式的mcp实现
  - 33：《AI-MCP-Gateway》第3-24节：通过case和domain，串联出Streamable...
  - 34：《AI MCP Gateway》第3-25节：验证服务，LLM对接测试Streamable接口
  - 35：《AI MCP Gateway》第3-26节：分布式服务设计(redis)

- 第3部分：DevOps

	- 36：《AI MCP Gateway》第4-1节：把网关部署到云服务器

---

课程包括；视频、小册、1 对 1 答疑解惑、专属VIP项目交流群，并且提供简历编写模板结构的一条龙🐲服务。让你学习后，直接拉开与还在玩具项目其他人的差距，面试脱颖而出提高竞争力！！！

## 四、加入学习

**注意**📢，本项目也只是【星球：码农会锁】众多项目中的1个，其他的项目还包括：AI Agent 智能体（脚手架、拖拉拽、ai+draw.io、ai + ppt、walissh...）、大营销、拼团、API网关、Lottery抽奖、IM通信、SpringBoot Starter 组件开发、IDEA Plugin 插件开发等，并还有开源项目学习。

如果大家希望通过做有价值的编程项目，提高自己的编程思维和编码能力，可以加入小傅哥的【星球：码农会锁】。加入后解锁🔓所有往期项目，还可以学习后续新开发的项目。
