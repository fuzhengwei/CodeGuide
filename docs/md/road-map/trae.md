---
title: Trae.ai
lock: need
---

# Trae.ai - 真好用，码农必备工具！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

有人说程序员这个行业👨🏻‍💻总是在革自己的命，一个个编码 AI 工具的出现，是不已经不需要那么多初级 CRUD 程序员了🤔。但恰恰相反，AI 工具的出现，是把初级程序员，迅速培养到了高级工程师！

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-nas-01.gif" width="200px">
</div>

`其实 AI 让程序员更牛！`

以前初学编程看不懂的代码，现在使用AI能动态做解释和提供案例，以前跨语言（java->vue/react）不能做的事情，现在可以搞了。以前不能写的插件（IntelliJ IDEA、谷歌浏览器），现在能实现了。以前写不出的高级感代码，现在随手就拿到全球最优秀的编码方案。

今天介绍一款 AI 开发工具 [Trae.ai](https://www.trae.ai/) ，让刚入门编程的找到学习方法，让初级工程也能写出优秀的代码，让高级研发嘎嘎提高效率！

>文末提供了 AI 应用开发实战项目，想提高  AI 应用编程能力的伙伴，可以获取学习项目。

## 一、软件下载

官网地址：[https://www.trae.ai/](https://www.trae.ai/) 

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-trae-01.png" width="650px">
</div>

- Trae 是一个自适应的 AI IDE，它可以改变您的工作方式，与您协作以更快地运行。
- 在使用的时候，你可以一个工程同时在 IntelliJ IDEA 打开，也使用 Trae 打开。😂 `因为习惯 IntelliJ IDEA，各类调试还是很舒服的。`

> 同类软件 [Cursor](https://www.cursor.com/cn)，不过这个想使用好的模型得付费，Trae 是不需要付费的！

## 二、使用体验

### 1. 提问代码

**拖拽代码/文件夹/工程**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-trae-02.png" width="750px">
</div>

**添加代码到对话**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-trae-03.png" width="750px">
</div>

**通过#号，选择要对话的内容**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-trae-04.png" width="750px">
</div>

> 之所以分享这个软件，也是因为很多小白初学编程，拿到一个项目不知道从哪里看。现在你有了这个软件，就可以非常简单的对工程的代码进行提问了。也不需要复制到任何 AI 对话工具了，并且这套软件是免费的。

### 2. 生成代码 - 后端

这里我们举例，复制掘金发文接口，之后让 Trae.ai 帮我包装；

#### 2.1 复制接口

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-trae-05.png" width="750px">
</div>

#### 2.2 研发设计 - 话术

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-trae-06.png" width="750px">
</div>

**生成话术，类似于研发设计**

```java
在 gateway 文件夹下，编写 IJueJinService 类，以 retrofit2 方式包装 curl 请求接口。接口入参仅为必要参数，如；cookie，发文的 Request 对象。

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

#### 2.3 编码效果

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-trae-07.png" width="850px">
</div>

- 生成后，代码会直接写入到你说明的文件夹下，创建好相关的类信息。之后你可以点击全部接受，他会审查代码，如果代码有问题还会检修编写。

### 3. 生成代码 - 前端

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-trae-08.png" width="950px">
</div>

- 这是小傅哥实现的一套 AI RAG&MCP 知识库项目，设计的一套页面。这套页面使用了 AI 做了大量的编码。`以前能写前端，但总感觉是屎上雕花，不出活！`这回有 AI 工具，省心多了。
- AI 对于这类既定的，没有复杂的逻辑和各类框架综合使用的，真的描述一句优化UI，它就`苦吃苦吃`的干活了！

> 你发现没，你只要写一些描述，它就能很好的干活了。这是因为 AI Agent 智能体会包括；规划、记忆、召回、工具（MCP），来把需求转换为可以看到的代码。

## 三、实战项目

这是小傅哥最近带着大家做的一套 AI 应用实战项目，《DeepSeek RAG&MCP 增强检索知识库系统》 - 解析文档&Git仓库代码&AI工作流，全程视频手把手教学。

RAG：实现了除普通文档知识解析外，增加了 Git 代码库的拉取和解析，并提供操作接口。为工程师做项目开发时，`需求分析`、`研发设计`、`辅助编码`、`代码评审`、`风险评估`、`上线检测`等，做工程交付提效。

MCP：用不了多久，各大互联网企业都将大量的推进落地，自有 MCP 服务的实现，用于增强企业 AI 应用的提效能力。因为 MCP 的加入，可以让你；一条命令`帮研发`，调用应用系统日志、排查系统CPU负载、自主选择是否调度数据库信息。也可以一条命令`帮运营`，搞定复杂的SQL执行、导出报表、分析数据、完成促活营销券的自动化配置上架。这就是 MCP的魅力！👍🏻

**RAG + MCP = 智能AI工作流，如智能客服，智能编码助手，智能运维工程师等。**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-trae-09.png" width="650px">
</div>

```java
@Test
public void test_weixinNotice_chatMemory() {
    System.out.println("\n>>> ASSISTANT: " + chatClient
            .prompt("""
                    我需要你帮我生成一篇文章，要求如下；
                            
            1. 场景为互联网大厂java求职者面试
            2. 面试管提问 Java 核心知识、JUC、JVM、多线程、线程池、HashMap、ArrayList、Spring、SpringBoot、MyBatis、Dubbo、RabbitMQ、xxl-job、Redis、MySQL、Linux、Docker、设计模式、DDD等不限于此的各项技术问题。
            3. 按照故事场景，以严肃的面试官和搞笑的水货程序员谢飞机进行提问，谢飞机对简单问题可以回答，回答好了面试官还会夸赞。复杂问题胡乱回答，回答的不清晰。
            4. 每次进行3轮提问，每轮可以有3-5个问题。这些问题要有技术业务场景上的衔接性，循序渐进引导提问。最后是面试官让程序员回家等通知类似的话术。
            5. 提问后把问题的答案，写到文章最后，最后的答案要详细讲述出技术点，让小白可以学习下来。
                            
            根据以上内容，不要阐述其他信息，请直接提供；文章标题、文章内容、文章标签（多个用英文逗号隔开）、文章简述（100字）
                            
            将以上内容发布文章到CSDN。
                    """)
            .advisors(advisor -> advisor
                    .param(CHAT_MEMORY_CONVERSATION_ID_KEY, "1001")
                    .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
            .call()
            .content());
                       
                       
    System.out.println("\n>>> ASSISTANT: " + chatClient
            .prompt("""
                     之后进行，微信公众号消息通知，平台：CSDN、主题：为文章标题、描述：为文章简述、跳转地址：从发布文章到CSDN获取 url
                    """)
            .advisors(advisor -> advisor
                    .param(CHAT_MEMORY_CONVERSATION_ID_KEY, "1001")
                    .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
            .call()
            .content());
}
```

- 两轮对话，发文到CSDN，之后推送消息到公众号平台。这两轮对话是配置有记忆功能的，所以可以衔接上下文。
- 这样的技术，还是非常有必要积累的，因为很多中大厂也都开了 AI 开发工程师的岗位。
