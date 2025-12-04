---
title: draw.io + ai agent
lock: need
---

# draw.io + ai Agent，确实打开新思路

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

<iframe id="B-Video" src="//player.bilibili.com/player.html?isOutside=true&aid=115652731865787&bvid=BV13ZSaBqE9G&cid=34460795162&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

大家好，我是技术UP主小傅哥。

又到年底了，又要必不可少的开始画图做PPT述职啦 😂，述职的好坏和年终奖都有可能挂钩！这对天天写代码大部分的程序员👨🏻‍💻来说是一件非常头疼的事，但好在这次可以使用个**神器**！

<div align="center">
	<img src="https://bugstack.cn/images/article/project/chatgpt/openai-01.jpg" width="150px"/>
</div>

**Ai Agent 会结合到各个场景！**

可以想象，将来我们使用的各种工具，都会很方便的以标准的协议方式结合到 AI Agent 智能体。这就像小傅哥，25年，年初开始做 Ai Agent 时，对一个智能体的执行过程，是需要自己进行分析，设计，实现的（`那时候我就在讲，Agent 也会出标准框架`）。到了年中的时候 Google ADK 除了 0.1.0 最初的版本，年尾发布了 0.4.0，这个框架提供了构建智能体的最基本框架，可以整合 Spring AI、Langchain4j 快速构建智能体。

>Spring AI、Langchain4j 提供的是 AI 封装能力，Google ADK 提供的是 Agent 执行能力（并行、循环、串行）的组装和使用。

未来（甚至不会太久），各个软件与 Ai Agent 的对接会变得非常容易，你手里用到的各项软件，都可以很方便的添加上智能体的能力。就像，我们现在用到的 `trae.ai`、`Cursor`，在以后 Ai Agent 基础能力成熟后，在结合 `Visual Studio Code` 给出软件对接标准协议，那么任何一个人都可以快速的搭建出一个智能体编码工具。除此之外，其他的软件也都会逐步提供出这样的能力。

扩展的思路分析完毕，小傅哥这次先带着大家了解下，draw.io 是怎么与 Ai 结合使用的。

>🧧 文末提供了小傅哥所有编程实战项目获取方式，一次加入即可获得19个已完结的和本次新开展的。

## 一、关于 draw.io

draw.io 是一个用于绘制通用图表的 JavaScript 客户端编辑器。

官网：[https://www.drawio.com/](https://www.drawio.com/)
代码：[https://github.com/jgraph/drawio](https://github.com/jgraph/drawio)

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/draw.io-01.png" width="950px"/>
</div>

我所有的这类的绘图，都来自于使用 draw.io 完成（[bugstack.cn](https://bugstack.cn) 还有很多绘图）。它可以下载到本地使用，也可以在网页使用。如果你是开发人员，还可以使用它的开发组件引入到自己的程序中进行使用。也正因如此，有了 Ai Agent 能力的结合，一种是通过 MCP，另外一种是直接通过扩展功能，直接在 draw.io 二开上完成。接下来小傅哥会分别介绍这两种方式以及使用。

## 二、MCP 方式对接

MCP（Model Context Protocol）是用于解决 AI 与服务接口通信的标准模型上下文协议，该协议通过标准化通信方式，使AI应用程序能够访问和使用实时数据、执行操作，从而克服了模型训练数据的局限性。 你可以将MCP想象成AI应用程序的“USB-C接口”，它提供了一个标准化的方法，让AI模型能够连接各种外部资源。

**Draw.io Model Context Protocol (MCP) Server**，就是基于此协议实现的对接 Draw.io 的 MCP 服务。我们可以使用这样一套服务，把 AI 与 Draw.io 建立起使用链接。

源码：[https://github.com/lgazo/drawio-mcp-server](https://github.com/lgazo/drawio-mcp-server)

### 1. 插件安装

- 谷歌浏览器：[https://chromewebstore.google.com/detail/drawio-mcp-extension/okdbbjbbccdhhfaefmcmekalmmdjjide](https://chromewebstore.google.com/detail/drawio-mcp-extension/okdbbjbbccdhhfaefmcmekalmmdjjide)
- 火狐浏览器：[https://addons.mozilla.org/en-US/firefox/addon/drawio-mcp-extension/](https://addons.mozilla.org/en-US/firefox/addon/drawio-mcp-extension/)

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/draw.io-02.png" width="650px"/>
</div>

安装完成后，可以看到这样一个小插件（右侧插件文件里可以点击让常驻浏览器），之后后面可以等着使用（继续下面的步骤）。

### 2. 配置服务

```java
{
	"mcpServers": {
		"drawio": {
			"command": "npx",
			"args": [
			  "-y",
        "drawio-mcp-server"
			]
		}
	}
}
```

#### 2.1 trae.ai - 也可以其他的

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/draw.io-03.png" width="650px"/>
</div>

#### 2.2 spring ai

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/draw.io-04.png" width="650px"/>
</div>

### 3. 使用服务

首先打开：[https://app.diagrams.net/#](https://app.diagrams.net/)

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/draw.io-05.png" width="950px"/>
</div>

- 对接上之后，你就可以在右侧提问了。之后他就可以实时的出设计图。`受限于MCP服务对接，它不是在 Draw.io 上扩展，会缺少一些交互和判断，画图有时候会有重叠。`
- 不过，这个方式的好处是，你可以结合自己的代码，拖进去，让它直接出架构图、流程图、模型图等。之后在自己做一些调整。

## 三、软件方式使用

现在 Github 上有很多基于 Draw.io 的二次开发，结合了 Ai 的能力，这里小傅哥找到一个做这块功能比较早的，next-ai-draw-io 来演示给大家。感兴趣的伙伴，还可以结合代码做二次开发。

这是一个基于 Next.js 的 Web 应用程序，它将 AI 功能与 draw.io 图表集成在一起。该应用程序允许您通过自然语言命令和 AI 辅助可视化来创建、修改和增强图表。

### 1. 代码下载

代码：[https://github.com/DayuanJiang/next-ai-draw-io](https://github.com/DayuanJiang/next-ai-draw-io)

```java
git clone https://github.com/DayuanJiang/next-ai-draw-io.git
```

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/draw.io-06.png" width="950px"/>
</div>

下载代码后，注意修改配置文件。

### 2. 启动工程

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/draw.io-07.png" width="650px"/>
</div>

- 进入 package.json 点击绿色箭头执行启动。

### 3. 访问项目

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/draw.io-08.png" width="950px"/>
</div>

- 访问 http://localhost:6002 这样你就可以提问来让它绘制图了。这个效果还不错。