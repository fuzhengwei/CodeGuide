---
title: JoyCode
lock: need
---

# JoyCode - 代码洪流 一念即成！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

**🧨25年以来**，先是 RAG 知识库，再到发布 MCP 协议。到这，各个互联网公司哇哇进入了 AI Agent 智能体开发，在各个应用场景探索提效，做出了很多的智能体产品，如；`Dify`、`Coze`、`Claude`，以及 `AI IDEA` 产品辅助编码（`Trae.ai`、`Cursor`等）。

曾经还以为 AI 出了这么多产品，是不程序员的工作岗位会变少🤔？**好家伙，一点没少不说，还变多了**。因为互联网的思维是，**你干的越快，你活tm越多**。甚至不少公司已经要求，程序员的 AI 编码量要在50%以上。**说白了就是，你得快点整 😂**。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-joycode-01.png" width="400px">
</div>

**但，凡事不是坏事！**

其实我们公司也要求了 AI 编码量，他所编写的代码提交是有 Git 记录和 AI IDEA 记录的，以此进行分析使用情况。公司里这么做也能理解，大家都用才能整体提高编码效率。而且这事多老程序员是有好处的，老程序员的`架构思维厚实`、`编码经验充足`，`邪修技巧多样`，把这些思维转换为提示词，可以节省大量的编码量（毕竟老码农体力不如新生代）。

此外，像是这些 AI IDEA 除了企业的编码外，对程序员的学习是非常有好处的。比如对代码的理解，对模块的分析、对bug的检查处理，以及通过提问的方式拓展式的增强理解工程代码都是有好处的。**新人一定使用！** 因为不只是学习，而是进入企业后还要使用。

那么有哪些不错的 AI IDEA 吗？有的，Cursor、Trae.ai 都不错的，之后，还有一个可以免费使用的 Joycode 京东出的，使用起来也不错，支持很多模型。地址：[https://joycode.jd.com/docs/start/intro/](https://joycode.jd.com/docs/start/intro/)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-joycode-02.png" width="500px">
</div>

> 接下来，小傅哥就分享下关于 JoyCode 的使用。让小伙伴们更好的使用 AI 辅助编码和学习代码。

## 一、软件下载

官网地址：[https://joycode.jd.com/](https://joycode.jd.com/)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-joycode-03.png" width="700px">
</div>

- 代码洪流 一念即成。JoyCode，专为应对企业级复杂任务而设计的智能编码工具。
- 在使用的时候，你可以一个工程同时在 IntelliJ IDEA 打开，也使用 Trae 打开。😂 因为习惯 IntelliJ IDEA，各类调试还是很舒服的。

> 同类软件 [Cursor](https://www.cursor.com/cn)，不过这个想使用好的模型得付费，Joycode 暂时是不需要付费的！其实我们需要百家争鸣，各家都用一些才好。

## 二、使用场景

### 1. 提问代码

拖拽代码/文件夹/工程

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-joycode-04.png" width="700px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-joycode-05.png" width="700px">
</div>

- 对于需要的代码进行选择式的提问，选中后的代码，在对话框中会标注出来。如果你在对话中还要告诉AI用其他部分的代码，则把对应位置的代码，写到对话框里就可以，如；`cn.bugstack.domain.auth.service.WeixinLoginService`
- 所有我们编程过程中，可以做的操作，都可以让 AI 来做，但尽量描述准确。这也就是老码农，为啥能更准确的使用 AI IDEA 工具，因为可以知道描述的内容最终可以产出的结果。

### 2. 完善注释

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-joycode-07.png" width="700px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-joycode-06.png" width="700px">
</div>

对于类、方法、字段属性，都可以让 AI 来补全注释说明，辅助我们学习使用。如果你对某一快逻辑厘不清的时候，也可以使用 AI 这样理解代码。

### 3. 开发功能

#### 3.1 curl 接口

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-trae-05.png" width="700px">
</div>

#### 3.2 开发接口

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-trae-06.png" width="700px">
</div>

**描述的话术，尽量准确**

```java
在 s-pay-mall-ddd-infrastructure 模块下， cn.bugstack.infrastructure.gateway 文件包下，编写 IJueJinService 类，以 retrofit2 方式包装 curl 请求接口。接口入参仅为必要参数，如；cookie，发文的 Request 对象。

curl 如下；

curl 'https://api.juejin.cn/content_api/v1/article_draft/create?aid=2608&uuid=7058897578062890496' \
  -H 'accept: */*' \
  -H 'accept-language: zh-CN,zh;q=0.9,en;q=0.8' \
  -H 'content-type: application/json' \
  -b 'store-region=cn-bj; store-region-src=uid; __tea_cookie_tokens_2608=%******a51b13e5; uid_tt_ss=7aff2f2394310b3b71f07006a51b13e5; sid_tt=3813572f48984e4b5b3cc7f90733f2e9; sessionid=3813572f48984e4b5b3cc7f90733f2e9; sessionid_ss=3813572f48984e4b5b3cc7f90733f2e9; sid_ucp_v1=1.0.0-KDU4N2NlM2Q2NjFiODhjOGNhZGE5YzVlOTM4ZWZkY2U3ZTc3MzVjMjAKFwjemIC67ozUAxDT_ci_BhiwFDgCQPEHGgJsZiIgMzgxMzU3MmY0ODk4NGU0YjViM2NjN2Y5MDczM2YyZTk; ssid_ucp_v1=1.0.0-KDU4N2NlM2Q2NjFiODhjOGNhZGE5YzVlOTM4ZWZkY2U3ZTc3MzVjMjAKFwjemIC67ozUAxDT_ci_BhiwFDgCQPEHGgJsZiIgMzgxMzU3MmY0ODk4NGU0YjViM2NjN2Y5MDczM2YyZTk' \
  -H 'dnt: 1' \
  -H 'origin: https://juejin.cn' \
  -H 'priority: u=1, i' \
  -H 'referer: https://juejin.cn/' \
  -H 'sec-ch-ua: "Not(A:Brand";v="99", "Google Chrome";v="133", "Chromium";v="133"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "macOS"' \
  -H 'sec-fetch-dest: empty' \
  -H 'sec-fetch-mode: cors' \
  -H 'sec-fetch-site: same-site' \
  -H 'user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36' \
  -H 'x-secsdk-csrf-token: 00010000000152d838f2c09c65e4feef033c275a8cff940c12228719074fe36f9ce2d18b4d251833ae62ea768117' \
  --data-raw '{"category_id":"0","tag_ids":[],"link_url":"","cover_image":"","title":"测试文章","brief_content":"","edit_type":10,"html_content":"deprecated","mark_content":"","theme_ids":[],"pics":[]}'

接口返回结果；

{
    "err_no": 0,
    "err_msg": "success",
    "data": {
        "id": "7489358088379531291",
        "article_id": "0",
        "user_id": "2058727733595230",
        "category_id": "0",
        "tag_ids": [],
        "link_url": "",
        "cover_image": "",
        "is_gfw": 0,
        "title": "测试文章",
        "brief_content": "",
        "is_english": 0,
        "is_original": 1,
        "edit_type": 10,
        "html_content": "deprecated",
        "mark_content": "",
        "ctime": "1743929226",
        "mtime": "1743929226",
        "status": 0,
        "original_type": 0,
        "theme_ids": []
    }
}

根据接口信息封装 DTO 对象，放到 gateway dto 下。
```

### 4. 单元测试

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-joycode-10.png" width="700px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-joycode-11.png" width="700px">
</div>

- 编写单测试一个很好用的场景，因为单测即使写错了也不会影响到线上的业务流程。

### 5. 系统分析

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-joycode-12.png" width="950px">
</div>

- 让 ai idea 工具分析系统工程，它分析的还是很不错的。这非常有利于伙伴对项目的学习、理解、重构，是非常提效的。

## 三、AI 来了吗？

刷”知乎“的时候，有伙伴问，AI 真的来了吗？这何止是来了，我已经感受到它来的太快了，21年 AI 可以对话、22 年各种代理的服务、23 年大家结合AI 做各种服务，24年推出 MCP 协议，25年大家进入了 AI Agent 智能体，谷歌发布 A2A 协议。照这样发展，明年、后年，将会有更加成熟的 AI 场景使用，企业里也会开启大量的 AI 应用开发岗位，为各个场景提效。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-joycode-13.png" width="300px">
</div>

你会在各个公司的招聘里看到 AI 应用开发岗，我以后也是会回天津的，顺手搜了下。附近就有联想公司的这样的岗位。早点储备能力！

关于 AI 小傅哥已完成项目包括；OpenAI 代码自动评审、OpenAI 应用（含支付）、AI Agent 智能体，以及正在开展的 AI MCP Gateway 网关服务项目。感兴趣的伙伴，可以一起加入学习。

### 1. AI Agent 智能体

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-20-04.png" width="950px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-12-01.png" width="950px">
</div>

### 2. OpenAI 应用（含支付）

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-230905-03.png?raw=true" width="950px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/openai-07.png" width="950px">
</div>

### 3. AI MCP GateWay

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-1-1-03.png" width="750px"/>
</div>

> 很多能力就是这样，早早的储备起来，以备不时之需！程序员也是一个一直学习的行业！春江水暖鸭先知🦆，码农需要学知识！
