---
title: WaLiCode
lock: need
---

# 平替 trae.ai/codex，又一个嘎嘎好用 AI IDE ！重点 - 支持各类渠道配置！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?isOutside=true&aid=116439918839670&bvid=BV1Zkd2BvExi&cid=37678287665&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

大家好，我是技术UP主小傅哥！

我自己做了个 **WaLiCode，平替市面 AI IDE（trae.ai/codex）** [https://walicode.xiaofuge.cn](https://walicode.xiaofuge.cn) 为什么不用他们的了？🤔

首先我个人是 Trae.ai 的付费用户，还是2个账户开年卡的付费用户，但也不够用！`因为他变了！`先是下掉了 Claude Code 模型（不准了以后要多次提问），后是调整了每个月给600次对话，为 $20 + 赠送额度的 Token 消耗，基本1-2天就把一个月的额度干没了（😂也是我用的猛？）。所以，不够用，根本不够用！

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/product-walicode-user-guide-01.png" width="650px"/>
</div>

**不够用咋办，买额度？**

虽然市面有一些 `coding/token plan`，但基本这些套餐都没有 GPT、Google、Claude 模型，总感觉用起来，劲不够大。想用 CPAMC、CC-Switch，或者自己 API ，这些 AI IDE 又都不允许配置。**真实条条大路，条条卡的死死的。**

所以我的诉求就是，自研一款 AI IDE 工具（`瓦力Code`）；允许各类 API 配置（支持 curl 标准协议，直接导入使用），并可添加 MCP、Skills 服务，同时深度设计一款上下文服务（避免对话多了成傻子了）。最终让这款 AI IDE 工具，成为我手边最为值得使用的 AI 提效工具。

接下来，小傅哥就为你介绍下，如何爽用这款软件，如何使用 AI IDE 提效。总结为，它可以帮助你；分析项目、编程开发、绘制图稿、提问代码（辅助学习）、面试总结（八股）等等，所有你脑洞打开的东西，都可以帮你处理。

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/product-walicode-00.png" width="300px"/>
</div>

>产品优势；支持配置任何渠道商（不限制配置）、有图形界面可以与代码对话方式完成功能开发（节省Token）。也可以扫码加入群关注产品动态和大家使用效果（已有300人+）。

## 一、软件安装

### 1. 软件信息

- 官网：[https://walicode.xiaofuge.cn/](https://walicode.xiaofuge.cn/)
- 下载：[https://drive.weixin.qq.com/s?k=ACMA4AfQABUV1xP9Uo#/](https://drive.weixin.qq.com/s?k=ACMA4AfQABUV1xP9Uo#/)
- 仓库：[https://github.com/fuzhengwei/WaLiCode](https://github.com/fuzhengwei/WaLiCode) - `方便提交 issue，下载 Release 包。`

你可以通过多个渠道，包括网盘、仓库（Release），获取软件安装包。首次安装完成后，后续会通过自动检查版本的方式，来提醒你可以安装最新版本。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/product-walicode-user-guide-02.png" width="550px"/>
</div>

### 2. 安装完成

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-v0.3.0-00.png" width="950px"/>
</div>

- 产品功能如图所示，你可以参考后使用。基本这些内容，和市面的各类 AI IDE 的设计也都是类似的，没有什么迁移使用成本。
- 下载安装后，就可以按照下面的环境配置进行操作了。最主要的就是配置一个 LLM，WaLiCode 支持各类渠道配置，不限制！

> 产品迭代速度很快，陆续的完善各项功能。也因为是咱们自己的东西，大家提到的优化点以及新的功能诉求，都可以很快的支持。

## 三、环境配置

### 1. 模型配置

#### 1.1 walicode 平台

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-v0.3.0-01.png" width="500px"/>
</div>

- 首先，WaLiCode 平台，默认提供了 claude-opus-4-6、gpt-5.4、gpt-5.1、gpt 4.1 模型，速度很快，很方便使用。
- 之后，也支持多厂家模型灵活配置；自定义 OpenAI 格式的各类大模型接口，无论是智谱、火山引擎还是本地部署的模型，均可一键接入，满足不同场景的开发需求。也支持你自己做号池（CPAMC）。

#### 1.2 其他供应商

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-v0.3.0-02.png" width="950px"/>
</div>

- 智谱：`https://open.bigmodel.cn/api/paas/v4`
- Anthropic：`https://ark.cn-beijing.volces.com/api/coding/v1`
- CPAMC：`https://cpa.taian.liujunjiang.cn:1111/v1`
- deepseek：`https://api.deepseek.com`
- 豆包：`https://ark.cn-beijing.volces.com/api/coding/v1`
- xiaomimimo：`https://token-plan-cn.xiaomimimo.com/v1`
- Ollama：`http://127.0.0.1:11434`

### 2. skill 技能

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/product-walicode-user-guide-04.png" width="650px"/>
</div>

- 允许添加各类 skills 技能，包括；Git 远程加载共享技能包、本地直接上传 zip 压缩包。添加后的技能，可以点击更新、修改和删除操作。
- 技能增加清单默认、AI 自动和关闭操作，以便于用户自主选择。

> skills 非常有用，你可以让 AI IDE 基于 skills 规范，把工程“蒸馏”，之后配置到 skills。这样他会更加理解你的工程。

### 3. mcp 配置

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/product-walicode-user-guide-05.png" width="650px"/>
</div>

- MCP 配置与生态集成，通过 Model Context Protocol 接入丰富的外部工具生态，一键配置数据库、文件系统、API 服务等扩展能力。

>像是公司里的很多业务系统，如果需要做系统日志分析，监控故障排查，那么都可以通过 MCP 方式进行接入使用。此外对于你已经对特定问题可以有固定的流程（SOP）标准排查，那么又可以提炼为 skills 技能。

## 四、场景技巧

WaLiCode 这种 AI IDE 相对于 CLI（OpenCode、Claude Code）的优势在于，我们可以讲对话的工程、模块、类、代码片段，以及外部的文件（产品PRD）、URL（在线文档），都可以填入对话框。让上下文对话更加精准（节省Token）。

### 1. 辅助开发

#### 1.1 工程分析（+流程转代码）

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/product-walicode-user-guide-10.png" width="950px"/>
</div>

> 我是一个编程的初学者，请根据项目内容，深度分析后，帮我绘制下架构图，draw.io 文件格式。帮助我理解项目。尤其是核心重点流程。

只是一个非常重要的功能，可以非常有效的帮助用户分析系统工程。将代码逻辑转换为可以便于理解的图稿形式，对于你所有不了解的流程，可以让它分析后产生流程图。

此外，不用局限于流程图，还可以出 UML 图、时序图等等。按照你的诉求，帮你直接做出来。

之后 **思路打开**，这个过程还可以反过来。你可以先让 AI 根据产品功能诉求，设计功能流程的 UML 时序图。之后把时序图添加到对话，让 AI 根据时序图流程，完成功能代码开发（~~以后让产品经理自己写代码吧！~~）。

#### 1.2 页面对接

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/product-walicode-user-guide-07.png" width="850px"/>
</div>

你可以把要`修改/新增`的逻辑设计的，`模块`，`文件`，`类`，都加入到对话框中，这样的描述会让 AI 识别的更加准确。与 CLI 命令相比，在复杂的业务流程中，代码的开发迭代，你可以更加清楚明了的指出。最终开发的代码也会让你更加有信息。

#### 1.3 接口对接

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/product-walicode-user-guide-09.png" width="850px"/>
</div>

如果是需要做一些接口开发，我们可以复制 curl 格式，告诉 AI IDE 要在哪个文件夹开发什么接口。它可以非常方便的帮助我们实现这些内容。

#### 1.4 补全注释

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/product-walicode-user-guide-11.png" width="850px"/>
</div>
程序员开发代码，既希望别人写注释，自己又不爱写注释。那么这个工作就可以让 AI IDE（walicode）来完成，它可以引入 swagger 按照非常标准的格式结构协议，帮你写注释。同时还能给你提供出一套 Swagger OpenAPI 协议结构，以后导出接口文档，也可以让别人看懂了（包括 AI 也可以看的更明白了）。

> 打开思路，你可以在各个场景使用 WaLiCode 帮你提效。添加 Swagger，对接工程接口，补全注释信息。

#### 1.5 AI Shell

##### 1.5.1 基础功能（shell、目录、sftp）

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-v0.3.0-03.png" width="950px"/>
</div>

- 你可以切换到左侧目录运维下的 SSH 服务，之后【新增】连接配置信息。连接后就可以使用基础的 SSH 能力了，shell、目录、sftp 都是完整具备的。
- 此外，还有命令辅助能力，这会极大的方便小白伙伴一边学习，一边使用脚本。

##### 1.5.2 智能操作

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-v0.3.0-04.png" width="950px"/>
</div>

- 你可以通过对话，直接询问 AI；`分析 ai-draw-io-front 运行日志`、`检查 docker 里安装的软件`，都是可以直接帮你操作的。使用起来非常方便，小白伙伴用几次，都成运维大佬了！
- 提示，项目工程编码、SSH 服务，是可以共用的，比如选择了工程代码，在通过 @的方式选择的服务器，告诉上传脚本，也是可以直接上传进去的。很丝滑！

##### 1.5.3 联动操作（工程+shell+sftp）

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-v0.3.0-05.png" width="950px"/>
</div>

- 通过 ai 对话框，选择要上传的文件夹、文件，到指定的云服务器，那么他就可以直接上传进去了。
- 执行中，会有一些命令提示，你可以选择加入到白名单，加入的命令可以在设置里维护。

##### 1.5.4 自动部署+ssl申请

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-v0.3.0-08.png" width="950px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-v0.3.0-09.png" width="950px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-v0.3.0-10.png" width="950px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-v0.3.0-11.png" width="950px"/>
</div>

准备内容；

1. [http://618.gaga.plus](http://618.gaga.plus) - 2c4g 服务器
2. LLM 大模型套餐，gpt-5.4（本案例测试使用）

话术过程；

1. 服务器有安装docker吗，如果没有请帮我安装。
2. 安装 portainer
3. 帮我部署个 nginx 转发到 9000 这样是不更安全
4. 你可以获取到 免费的 ssl 吗，来做 https
5. 已配置域名 portainer.gaga.plus （帮我分析了域名还配置了其他ip）
6. 你来访问验证试试 https://portainer.gaga.plus/ 验证下（帮我分析了443没开放）

过程中，发了2次；继续、配置好了，继续。

>全程帮你完成服务器的部署、nginx转发、ssl申请。非常方便。

#### 1.6 活动追踪

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-v0.3.0-06.png" width="950px"/>
</div>

该功能的主要目的在于，我们在使用 AI 执行一些列动作，完成用户诉求的过程中，希望可以看到 AI 都在做什么。从分析代码文件，到修改和编译文件，你可以到每一个步骤对文件的操作过程。这样可以让你更有信息应对复杂项目的处理。

#### 1.7 任务队列

<div align="center">
	<img src="https://bugstack.cn/images/article/product/software/walicode-v0.3.0-07.png" width="500px"/>
</div>

该功能的主要目的在于，提前预设添加好一些待执行任务，之后可以选择执行间隔、定时执行开始时间，这样你在出门后，也可以把要干的活提前分配好，让 AI 不间断工作。甚至是夜晚你已经睡觉了，它还可以继续哇哇的干活！

### 2. 面试帮助

#### 2.1 八股整理

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/product-walicode-user-guide-12.png" width="850px"/>
</div>

>基于该项目的架构设计，核心流程，技术选型，功能方案等，给我出一份面试八股问题。面试官，最容易考察的问题点。我需要题目以及解答参考。整理好md放到工程下。

你可以基于项目工程，让 AI IDE 帮你出一份面试八股问题。但一定要用好一些模型，否则给出的八股质量不高。

#### 2.2 综合面试（简历、招聘、项目）

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/product-walicode-user-guide-08.png" width="850px"/>
</div>

- 首先，当你做完一个项目以后，你可以把自己的简历（最好md格式），以及你要面试的公司（岗位职责信息）+ 其他汇总资料，都放到工程下。
- 之后，你可以把这些资料加入到对话，核心的目的在于；让 AI IDE 基于你的资料，以及打开的当前简历，在配和当前项目，对你进行面试总结整理。（注意好的模型，会给出更有用的答案）

> 好啦，接下来你可以打开思路，让 AI IDE 在各个场景帮你提效。而且这类工具也必须是熟练使用的，各个公司里也会要求研发人员使用 AI IDE 的编码要有一定比例的，面试的时候甚至还会说你直接用 AI IDE 完成一个功能开发。