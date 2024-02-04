---
title: OpenAI + TLDraw 设计图转前端代码
lock: need
---

# 【小场景训练营】OpenAI + TLDraw 设计图转前端代码

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=238232794&bvid=BV1we411D776&cid=1391721608&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

哈喽，大家好我是技术UP主小傅哥。

大部分前端程序员是不会写后端代码的，但大部分后端程序员都能写点前端代码。不过虽然能写，但也是照葫芦画瓢，修修改改的二把手刀选手。😂 小傅哥就是这样的二把刀前端，每次写前端都感觉像是屎上雕花，一点点扣哧 DIV、CSS 调整样式和数据。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ddd-scene-solution/openai-tldraw-01.png?raw=true" width="250px">
</div>

但自从有了 OpenAI 以后，我一直惦记着，能不在 Draw.io 这样的工具出设计图后，让 OpenAI 识别自动转换为 HTML 代码。之后把这样的东西做成一整套工具来使用，大大的方便`二把刀前端们`快速完成从UI到HTML的初稿。

为此小傅哥基于[开源项目](https://github.com/SawyerHood/draw-a-ui)，使用 [React](https://zh-hans.react.dev/learn) + [TLDraw](https://github.com/tldraw/tldraw) + [OpenAI](https://platform.openai.com/docs/api-reference/chat/create)（多模态 gpt-4-vision） 做了一款这样的工具案例；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ddd-scene-solution/openai-tldraw-02.png?raw=true" width="650px">
</div>

对于研发工程师来说，只是使用其实没多大意义，重要的是知道这东西是怎么实现的。那么接下来小傅哥就来详细给大家介绍下具体的设计实现过程。

>文末提供了该案例的课程地址（含源码）和项目的体验地址。体验地址：https://openai.itedus.cn/#/draw

## 一、场景说明

本节小傅哥会带着大家先以最简单的方式完成 OpenAI 多模态接口与 TLDraw 的对接，再讲解项目中如何使用策略模式拆解不同类型的 OpenAI 服务接口（`文生文`、`文生图`、`多模态`）。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ddd-scene-solution/openai-tldraw-03.png?raw=true" width="650px">
</div>

- 首先，我们知道 OpenAI 的多模态模型，是需要像接口中传一个 BASE64 图片信息，并给予对应的描述性 Prompt 脚本。这样 OpenAI 就会知道我们要基于对这个图片做什么。
- 那么，我们就需要一款在线画图的工具，比如 [TLDraw](https://github.com/tldraw/tldraw) 或者 [Draw.io](https://github.com/jgraph/drawio) 把这样的绘图能力引入到 React 工程中。通过绘图后生成截图，再把图片转换为 BASE64 就可以使用。
- 最后，把 OpenAI 传递回来的接口数据，用 React 进行展示和效果渲染。也就是操作完成后，你可以看到 HTML 对应的展示效果。

## 二、功能实现

### 1. 多模态接口

```java
curl -X POST "https://api.openai.com/v1/chat/completions" \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer sk-kgUPx1vKDywFbsun7c05Ed5eA4C24d4aA7B06aE9F76e6eFe" \
     -d '{
           "model": "gpt-4-vision-preview",
           "max_tokens": 4096,
           "messages": [
             {
               "role": "system",
               "content": "Your-System-Prompt-Here"
             },
             {
               "role": "user",
               "content": [
                 {
                   "type": "image_url",
                   "image_url": {
                     "url": "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Gfp-wisconsin-madison-the-nature-boardwalk.jpg/2560px-Gfp-wisconsin-madison-the-nature-boardwalk.jpg"
                   }
                 },
                 {
                   "text": "Turn this into a single html file using tailwind.",
                   "type": "text"
                 }
               ]
             }
           ]
         }'

```

- 首先，我们做一个东西的时候，要先知道最根本调用是如何处理的。
- 之后，多模态接口与以往的 gpt-3.5、gpt-4.0 都是用的同一个接口，`v1/chat/completions` 只是用了 `gpt-4-vision-preview` 模型。同时 content 既可以是单独的描述字符串，也可以是对象类型含有 `type`、`text`、`image_url` 的方式进行使用。

## 二、前端实现

本案例基于 React 构建的前端页面，如果你还是个前端小白可以学习此份教程；[https://zh-hans.react.dev/learn](https://zh-hans.react.dev/learn) —— 全中文官网资料非常适合入门。

### 1. 工程介绍

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ddd-scene-solution/openai-tldraw-04.png?raw=true" width="350px">
</div>

工程主要分为三块，OpenAI 接口、存储和工具类、TLDraw UI绘制页面 + 调用 OpenAI 接口的实现。

### 2. tldraw 组件

在开发前端代码的时候，需要按照 `npm install @tldraw/tldraw@2.0.0-alpha.17` 组件。

```react
// 引入组件
const Tldraw = dynamic(async () => (await import('@tldraw/tldraw')).Tldraw, {
    ssr: false,
})

// 使用组件
<Tldraw persistenceKey="tldraw"/>
```

- 引入组件后就可以在 page.tsx 中使用这个组件了，目前你启动 react 会看到整个运行出来的 UI 设计页面。

### 3. 生成图片

```react
// tldraw 可以把当前页面转换为 svg 图片
const svg = await editor.getSvg(Array.from(editor.currentPageShapeIds))
// 基于工具把 svg 图片转换为 png图片
const png = await svg2image(svg, {
  type: 'png',
  quality: 1,
  scale: 1,
})
// 再把png图片在前端直接转换为 base64
const dataUrl = (await blobToBase64(png!)) as string
```

### 4. 调用接口

```react
try {
	let json = await getResponseFromAPI(dataUrl, prompt)
} catch (error: any) {
	console.log(error)
	alert(`Error from open ai: ${JSON.stringify(error.message)}`)
	return
}
```

- 之后就可以同步调用接口数据等待返回结果，回显到页面即可。

## 三、使用体验

### 1. 初始动作

```react
echo "BASE_API_URL=url-your-proxy like https://xxxxx.proxy.com/v1/chat/completions \r\nOPENAI_API_KEY=sk-your-key" > .env.local
rm -rf node_modules
npm install
npm run dev
```

- 推荐使用 WebStorm 打开 openai-tldraw 工程，它会自动的提示你执行 `npm install` 构建项目。
- 当你进入 openai-tldraw 工程中，需要先执行 echo 教程，创建出 `.env.local` 文件。这个配置里写入你 BASE_API_URL 和 OPENAI_API_KEY
- 最后在工程中 package.json 点击 dev 左侧的绿色按钮或者执行 `npm run dev` 都会启动工程。
- 启动后访问地址：[http://localhost:3000/](http://localhost:3000/)

### 2. 生成效果

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ddd-scene-solution/openai-tldraw-06.png?raw=true" width="650px">
</div>

### 3. 生成代码

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ddd-scene-solution/openai-tldraw-07.png?raw=true" width="650px">
</div>

## 五、项目运用

以上还是简单的案例，当我们把这样的接口功能放到项目中开发，就需要考虑到接口的策略调用来让结构更加易于维护。

### 1. 流程设计

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ddd-scene-solution/openai-tldraw-08.png?raw=true" width="650px">
</div>

此流程为小傅哥星球「码农会锁」OpenAI 项目的的核心流程，最下面三个地方支持；`文生文`、`文生图`、`多模态（图文理解）`，而图文理解本文的案例是同步请求，但前面的`文生文`、`文生图`，是异步响应。那么这样的代码结构怎么设计呢？🤔 

>欢迎👏🏻加入小傅哥星球「码农会锁」，一起学习这样的有架构、有设计，有高质量编码的项目。演示地址：[https://gaga.plus](https://gaga.plus)

### 2. 代码举例

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ddd-scene-solution/openai-tldraw-09.png?raw=true" width="650px">
</div>

在小傅哥的星球「码农会锁」中，有大量的此类设计，来解决实际场景的问题。如果能参与一个这样的项目学习，那么对编程的架构和设计的理解会提升的非常多。

## 六、加入学习

**注意📢**，本项目也只是【星球：码农会锁】众多项目中的1个，其他的项目还包括：正在进行的大营销平台、API网关、Lottery抽奖、IM通信、SpringBoot Starter 组件开发、IDEA Plugin 插件开发等，还有开源项目学习。

**课程💐**，加入星球「码农会锁」，即可获得本项目源码，以及当前92个课程代码仓库。

如果大家希望通过做有价值的编程项目，提高自己的编程思维和编码能力，可以加入小傅哥的【星球：码农会锁】。加入后解锁🔓所有往期项目，还可以学习后续新开发的项目。

>[🧧加入学习](https://bugstack.cn/md/zsxq/other/join.html)

**加入星球**：下载`星球APP`，从星球【课程入口】进入。里面有完整的学习指引，包括；使用说明、代码仓库、专属项目群、学习路线、往期项目。

