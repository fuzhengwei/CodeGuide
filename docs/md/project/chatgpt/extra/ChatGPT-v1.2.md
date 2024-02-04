---
title: 又完结一个新项目，小而美、小而精！
lock: no
---

# 又完结一个新项目，小而美、小而精！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

经历了4个多月`前后端 + Dev-Ops`，全栈式编程。我在[星球：码农会锁](https://t.zsxq.com/09hMHNMEh)的第7个，**手把手&渐进式，逐个章节录制视频 + 编写小册**开发的 —— OpenAI 项目，第1阶段完结啦💐！而且已经上线运行3周！

<div align="center">
	<img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-230905-01.png?raw=true" width="150px"/>
</div>

讲道理，这个项目做起来，花费我好多精力！

因为众所周知，与开发一个玩具项目相比，要做一个能要上线运行，并稳定对外提供服务的项目，其实并不容易。它不像开发个玩具项目，可以肆无忌惮的引入各种技术栈，炫技于一些新特性的使用。而是当你做任何一块功能的实现时，都要考虑你TM的要给我稳定运行，有错误要抛出准确的异常便于快速排查，给用户的提示要温馨且温暖。同时还要考虑，各个功能的实现要尽可能降低运维成本；如果不引入 MQ，是否可以使用 Redis 的发布订阅。如果不能使用 Redis 又是否可以用 Guava 替代。如果不想自己单独搞登录，又怎么结合微信公众号做鉴权登录。这一些列的真实场景问题，都随着这个项目手把手的渐进式开发，为小伙伴一步步解开技术解决场景问题的面纱。💥

**传道受业，虽说很累。** 但每每看到大家的学习到东西，我也感觉很爽；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-230905-02.png?raw=true" width="550px"/>
</div>

那么，接下来小傅哥就细致的介绍下，本次完结的新项目，可以让大家学习到哪些知识，掌握哪些技术。

>文末有加入学习方式，还有送福利 4.0 50万 Tokens 活动！🉐

## 一、能学到啥

该项目是当下最火的 OpenAI 场景应用项目，也是各个互联网大厂都开始深入折腾，并在自身业务中逐步应用的技术。你也会渐渐的看到，在招聘要求中有一条是会 ChatGPT 开发。因为本项目是`前后端+Dev-Ops`的全栈式编程，所以这一些列内容你都可以学习到，包括；

- 【前端】熟练使用 Next.js 构建的 React、Typescript 语言，构建的前端工程。
- 【前端】熟练使用 React Route 路由子页面的开发技术，以及相应的信息传递。
- 【前端】熟练掌握，跨域接口的对接使用，以及本地 json 数据加载。
- 【前端】熟练使用本地浏览器内存，存储 Token、配置、对话等信息。
- 【前端】熟练掌握前端页面数据的存放、使用以及和后端接口的交互方式。
- 【后端】熟练构建 DDD 工程架构，分层模块，职责体系。并掌握 DDD 架构的开发模式以及微服务设计思想。
- 【后端】熟练掌握 Spring、SpringBoot、MyBatis 等开发框架技术，并对其使用源码所提供的接口、类、SPI标准开发各类组件，有一定的设计思路和落地能力。—— 因为这里包含了 SDK 的设计、开发和使用。
- 【后端】熟练使用多种设计模式、设计原则，对各类场景的方案设计和落地能力，深度提高自身编码思维和开发技术能力。—— 同时包括非常重要的会话模型SDK的架构设计。
- 【后端】熟练对接微信公众号 SDK，属性验签流程和对话流程。以及完成 JWT Token 的生成和校验。
- 【后端】熟练掌握 Nginx  Auth 验证模块的开发和使用，以用于对接口的校验和拦截。
- 【后端】熟练使用流式异步响应式框架开发应答接口，完成前端动态展示应答数据。
- 【后端】熟练使用 okhttp3、retrofit2 框架，对接 ChatGPT 完成通用 SDK 的开发。有了这项技能，以后你可以方便的对接任何一个 HTTP 请求服务。
- 【后端】熟练掌握异常、枚举、错误码的定义和使用，并学习如何合理打印服务日志，便于问题排查。
- 【运维】熟练使用 Docker 在本地和服务端的配置和部署应用，以及在本地构建前后端镜像。
- 【运维】熟练掌握 Git、GitCode，对工程代码的管理，推送、拉取、切换分支、合并代码等操作。
- 【运维】熟练申请和使用 SSL 配置 Nginx 域名 HTTPS 服务。

此外，小傅哥对于每个章节还讲解了章节的诉求、流程的设计，之后再到方案实现和功能验证。并在每个章节留有作业让大家练习。当然这还没有完，你知道小傅哥这个架构师画图还是非常牛逼的，所以你还能看到各种画图的技巧，耳濡目染的把这些东西学习成自己的本事！~

## 二、项目介绍

本次项目是一个包括`前后端 + Dev-Ops`，全栈式编程，的硬核项目！基于 React + SpringBoot + Nginx + Docker 云服务部署的 OpenAI 应用项目。并且是能上线对外提供服务使用的项目！`不同于一些开源项目，本项目具备完整的前后端开发和实施部署方案。`

如果你使用过任何一款 OpenAI 产品，那么就会对这样一个业务场景非常熟悉，但对于产品之下的技术实现可能并不清楚。它是怎么做的异步应答，它是怎么对接的服务接口，它是怎么做的关联上下文数据处理。而这些都是小傅哥要在这次项目里给大家讲解的内容。

### 1. 应用部署

<div align="center">
	<img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-230905-06.png?raw=true" width="850px"/>
</div>

### 2. 项目演示

<div align="center">
	<img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-230905-03.png?raw=true" width="850px"/>
</div>

### 3. 数据监控（百度统计）

<div align="center">
	<img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-230905-04.png?raw=true" width="850px"/>
</div>

### 4. 热力展示（百度统计）

<div align="center">
	<img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-230905-09.png?raw=true" width="850px"/>
</div>

### 5. 项目流程（核心链路）

<div align="center">
	<img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-230905-05.png?raw=true" width="850px"/>
</div>

### 6. 细节设计（流程举例）

<div align="center">
	<img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-230905-07.png?raw=true" width="850px"/>
</div>

>对项目感兴趣的伙伴，也可以先免费看看其中的课程视频 https://b23.tv/OjYftBl

## 三、项目大纲

**不同于网上项目，这个项目是一步步，一个个章节的带着大家从0到1的方式，进行分析、设计和开发。是一个纯手把手教大家学习实战技术的项目！** 大家可以先看看课程的大纲，就知道可以学习到哪些东西了。

- 介绍
  - [课程：ChatGPT 微服务应用体系构建](https://bugstack.cn/md/project/chatgpt/chatgpt.html)
  - [引言：开篇介绍](https://bugstack.cn/md/project/chatgpt/引言.html)
- Dev-Ops
  - [第1节：push工程到仓库](https://bugstack.cn/md/project/chatgpt/dev-ops/第1节：push工程到仓库.html)
  - [第2节：Docker环境配置](https://bugstack.cn/md/project/chatgpt/dev-ops/第2节：Docker环境安装.html)
  - [第3节：Portainer环境配置](https://bugstack.cn/md/project/chatgpt/dev-ops/第3节：Portainer环境安装.html)
  - [第4节：Nginx环境配置](https://bugstack.cn/md/project/chatgpt/dev-ops/第4节：Nginx环境配置.html)
  - [第5节：服务镜像构建和容器部署](https://bugstack.cn/md/project/chatgpt/dev-ops/第5节：服务镜像构建和容器部署.html)
  - [第6节：前后端构建镜像部署](https://bugstack.cn/md/project/chatgpt/dev-ops/第6节：前后端构建镜像部署.html)
- ChatGPT-API
  - [第1节：API工程搭建和简单访问认证](https://bugstack.cn/md/project/chatgpt/api/第1节：API工程搭建和简单访问认证.html)
  - [第2节：Shiro登录授权发放访问token](https://bugstack.cn/md/project/chatgpt/api/第2节：Shiro登录授权发放访问token.html)
  - [第3节：微信公众号验签和初步对接OpenAI](https://bugstack.cn/md/project/chatgpt/api/第3节：微信公众号验签和初步对接OpenAI.html)
  - [第4节：工程重构和流式异步响应接口实现](https://bugstack.cn/md/project/chatgpt/api/第4节：工程重构和流式异步响应接口实现.html)
  - [第5节：公众号发送验证码鉴权登录](https://bugstack.cn/md/project/chatgpt/api/第5节：公众号发送验证码鉴权登录.html)
- ChatGPT-SDK
  - [第1节：ChatGPT-SDK组件工程简单功能实现](https://bugstack.cn/md/project/chatgpt/sdk/第1节：ChatGPT-SDK组件工程简单功能实现.html)
  - [第2节：流式应答会话设计实现](https://bugstack.cn/md/project/chatgpt/sdk/第2节：流式应答会话设计实现.html)
  - [第3节：完善实现各类常用接口](https://bugstack.cn/md/project/chatgpt/sdk/第3节：完善实现各类常用接口.html)
- ChatGPT-WEB
  - [第1节：Web页面工程初始化](https://bugstack.cn/md/project/chatgpt/web/第1节：Web页面工程初始化.html)
  - [第2节：工具栏面板](https://bugstack.cn/md/project/chatgpt/web/第2节：工具栏面板.html)
  - [第3节：按钮定义与事件实现](https://bugstack.cn/md/project/chatgpt/web/第3节：按钮定义与事件实现.html)
  - [第4节：对话框列表](https://bugstack.cn/md/project/chatgpt/web/第4节：对话框列表.html)
  - [第5节：对话框消息](https://bugstack.cn/md/project/chatgpt/web/第5节：对话框消息.html)
  - [第6节：完善对话处理](https://bugstack.cn/md/project/chatgpt/web/第6节：完善对话处理.html)
  - [第7节：对话角色设定](https://bugstack.cn/md/project/chatgpt/web/第7节：对话角色设定.html)
  - [第8节：流式接口对接](https://bugstack.cn/md/project/chatgpt/web/第8节：流式接口对接.html)
  - [第9节：公众号扫码登录](https://bugstack.cn/md/project/chatgpt/web/第9节：公众号扫码登录.html)
- 番外 - 课程阶段产物
  - [ChatGPT + 仿微信界面，效果好还TM贼漂亮！](https://bugstack.cn/md/project/chatgpt/extra/ChatGPT-v1.0.html)
  - [把一个ChatGPT项目上线，要折腾多少细节！](https://bugstack.cn/md/project/chatgpt/extra/ChatGPT-v1.1.html)

---

课程包括；视频、小册、1 对 1 答疑解惑、专属VIP项目交流群，并且提供简历编写模板结构的一条龙🐲服务。让你学习后，直接拉开与还在玩具项目其他人的差距，面试脱颖而出提高竞争力！！！

## 四、加入学习

**注意**📢，本项目也只是【星球：码农会锁】众多项目中的1个，其他的项目还包括：API网关、Lottery抽奖、IM通信、SpringBoot Starter 组件开发、IDEA Plugin 插件开发等，并还有开源项目学习。

如果大家希望通过做有价值的编程项目，提高自己的编程思维和编码能力，可以加入小傅哥的【星球：码农会锁】。加入后解锁🔓所有往期项目，还可以学习后续新开发的项目。

>这样一套项目，放在一些平台售卖，至少都是几百块。但小傅哥的星球，只需要100多，就可以获得几千元的学习项目！

- 福利1：所有今天加入的伙伴，前50名都送 4.0 的 `50万Tokens`，打开链接 [openai.itedus.cn](https://openai.itedus.cn/) - 在左下角设置里绑定即可使用。当天加入后，在星球置顶消息添加小傅哥微信，备注你的星球编号。
- 福利2：对此文章进行留言的伙伴，点赞最高前5名用户，每人送 4.0 的 `50万Tokens` 统计时间：9月6日 7:55 ~ 9月8日 7:55 

>[🧧加入学习](https://bugstack.cn/md/zsxq/other/join.html)

已经有很多伙伴开始学起来了，还有大家交的作业笔记。有了的项目驱动学习，清晰的目标感，大家冲起来也有了更明确的方向！干干干！！！

<div align="center">
	<img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-230905-08.png?raw=true" width="650px"/>
</div>
