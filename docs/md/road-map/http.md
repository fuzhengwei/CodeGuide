---
title: http
lock: need
---

# HTTP 框架使用和场景实战 - 结合ChatGLM自动回帖！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=577179347&bvid=BV1GB4y1f7U4&cid=1299792581&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

本文的宗旨在于通过简单干净实践的方式教会读者，HTTP的常用框架使用，HTTP接口快速对接方式。以及在编码实战中练习 HTTP 对数据的采集、ChatGLM对接、问题回答。这样的场景学习，非常适合以后大家在做一些智能化问答进行参考使用。

本文涉及的工程：
- xfg-dev-tech-http：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-http](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-http)
- chatglm-sdk-java：[https://bugstack.cn/md/project/chatgpt/sdk/chatglm-sdk-java.html](https://bugstack.cn/md/project/chatgpt/sdk/chatglm-sdk-java.html)

## 一、案例背景

虽然在分布式架构的微服务内部是RPC调用，但在对外的Web/H5等场景下，则需要使用HTTP协议进行调用。因而我们在对接公司以外的其他服务时，通常都是HTTP协议，包括你对接微信支付、ChatGLM等。当然也有少部分使用 websocket 协议。

但众所周知HTTP的调用，会涉及较多的信息配置。包括；请求头、入参、出参，而这些内容都是非对象化的设计。很多人在对接特别多的 HTTP 请求以后，自己的工程代码就会变得非常混乱。所以我们需要用一些 HTTP 框架，来解决这个场景问题，让 HTTP 的调用更加优雅。

那么本章节小傅哥会结合知识星球的接口进行案例场景学习；

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-http-01.png?raw=true" width="650px">
</div>

1. **星球免费加入**：[https://wx.zsxq.com/dweb2/index/group/28885518425541](https://wx.zsxq.com/dweb2/index/group/28885518425541)
2. 本章节所提供的课程源码，即可复现截图中的展示内容。自动化Ai回复问答。

## 二、接口提取

接下来，小傅哥会告诉如何非常快速的使用Java代码对接上HTTP调用。—— 把🐘大象装冰箱统共分3步；

### 1. 第一步

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-http-02.png?raw=true" width="650px">
</div>

- 首先，在PC端谷歌浏览器，打开知识星球首页：[https://wx.zsxq.com/dweb2/index/group/28885518425541](https://wx.zsxq.com/dweb2/index/group/28885518425541)
- 之后，摁F12打开控制台，选择网络模块。
- 最后，找到 topics 接口查询。在接口上鼠标右键。**以cURL格式复制**

### 2. 第二步

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-http-03.png?raw=true" width="650px">
</div>

- 首先，打开 [ApiPost](https://www.apipost.cn/) 工具中，如果没有可以下载一个。
- 之后，点击左侧的导入接口，这个里面可以直接把 cURL 格式接口导入进去。
- 最后，点击**立即导入** - 导入后你就可以点击**发送**按钮测试验证了。

### 3. 第三步

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-http-04.png?raw=true" width="650px">
</div>

- 首先，点击生成代码，会弹出一个各类语言对接代码案例。
- 之后，在你需要的类型代码上复制生成的代码。
- 最后，把代码粘贴到Java工程中测试。这部分代码可以参考案例工程 `cn.bugstack.xfg.dev.tech.test#HttpClientTest|OKHttpTest|Retrofit2Test`

---

除此之外，你还可以使用Ai工具，来生成对接的测试代码。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-http-05.png?raw=true" width="850px">
</div>

- 这样的方式也是非常好用的，而且如果有运行问题，你还可以继续提问。
- 用编程经验的人用 OpenAi 会更舒服，没有编程经验的人完全依赖 OpenAi 也会遇到各种错误。

## 三、玩个场景

### 1. 需求说明

接下来，我结合所学技术锻炼下。结合星球完成一个自动Ai回贴的功能，通过定时任务扫描星球接口帖子，并对未回答且圈【@小傅哥】的帖子进行采集回答。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-http-06.png?raw=true" width="650px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-http-07.png?raw=true" width="650px">
</div>

- 这里的接口，需要用到2个：一个扫描帖子的HTTP接口，一个回复帖子的HTTP接口。
- ChatGLM部分，小傅哥已经做了 SDK 非常好对接。[https://github.com/fuzhengwei/chatglm-sdk-java](https://github.com/fuzhengwei/chatglm-sdk-java)

### 2. 工程结构

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-http-08.png?raw=true" width="850px">
</div>

- 首先，你可以下载工程代码，按照这个结构来看代码。
- 之后，工程中对接了 ChatGLM SDK 并做了相关的配置在YML中。此外 YML中的 ChatGLM SDK 需要的 ApiSecretKey 可以通过官网申请。之后星球的 cookie 可以通过 F12 请求头中复制 Cookie 信息。
- 最后，是 ZSXQJob 的开发使用。

### 3. 代码实现

#### 4.1 ChatGLM SDK 对接

**源码**：`cn.bugstack.xfg.dev.tech.config.ChatGLMSDKConfig`

```java
@Bean
@ConditionalOnProperty(value = "chatglm.sdk.config.enabled", havingValue = "true", matchIfMissing = false)
public OpenAiSession openAiSession(ChatGLMSDKConfigProperties properties) {
    // 1. 配置文件
    cn.bugstack.chatglm.session.Configuration configuration = new cn.bugstack.chatglm.session.Configuration();
    configuration.setApiHost(properties.getApiHost());
    configuration.setApiSecretKey(properties.getApiSecretKey());
    // 2. 会话工厂
    OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);
    // 3. 开启会话
    return factory.openSession();
}
```

- 因为我们需要一个自动的智能Ai回帖，所以需要使用到 [ChatGLM](https://github.com/fuzhengwei/chatglm-sdk-java)。当然你也可以对接其他大厂开发的 OpenAi SDK 使用。

#### 4.2 流程串联

**源码**：`cn.bugstack.xfg.dev.tech.job.ZSXQJob`

```java
@Scheduled(cron = "0/10 * * * * ?")
public void exec() throws Exception {
    Response response = getResponse(cookie);
    RespData respData = response.getRespData();
    List<TopicsItem> topics = respData.getTopics();
    for (TopicsItem topicsItem : topics) {
        // 是否回答过判断
        if (!isCommentDone(topicsItem)) continue;
        // 找到圈我我帖子
        long topicId = topicsItem.getTopicId();
        Talk talk = topicsItem.getTalk();
        // "<e type="mention" uid="241858242255511" title="%40%E5%B0%8F%E5%82%85%E5%93%A5" /> 提问 java 冒泡排序"
        String text = talk.getText();
        // 正在匹配处理
        String regex = "<e type=\"mention\" uid=\"(\\d+)\" title=\"(.*?)\" /> (.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String uid = matcher.group(1);
            String remainingText = matcher.group(3);
            if ("241858242255511".equals(uid)) {
                if (null == openAiSession) {
                    log.info("你没有开启 ChatGLM 参考yml配置文件来开启");
                    // 你可以使用 ChatGLM SDK 进行回答，回复问题；
                    comment(cookie, topicId, "【测试，只回答圈我的帖子】对接 ChatGLM SDK https://bugstack.cn/md/project/chatgpt/sdk/chatglm-sdk-java.html 回答：" + remainingText);
                } else {
                    log.info("ChatGLM 进入回答 {} {}", topicId, remainingText);
                    if (topicIds.contains(topicId)){
                        continue;
                    } else {
                        topicIds.add(topicId);
                    }
                    new Thread(() -> {
                    
                        // 入参；模型、请求信息
                        ChatCompletionRequest request = new ChatCompletionRequest();
                        request.setModel(Model.CHATGLM_LITE); // chatGLM_6b_SSE、chatglm_lite、chatglm_lite_32k、chatglm_std、chatglm_pro
                        request.setPrompt(new ArrayList<ChatCompletionRequest.Prompt>() {
                            private static final long serialVersionUID = -7988151926241837899L;
                            {
                                add(ChatCompletionRequest.Prompt.builder()
                                        .role(Role.user.getCode())
                                        .content(remainingText)
                                        .build());
                            }
                        });
                        
                        // 请求
                        try {
                            StringBuilder content = new StringBuilder();
                            openAiSession.completions(request, new EventSourceListener() {
                                @Override
                                public void onEvent(EventSource eventSource, @Nullable String id, @Nullable String type, String data) {
                                    ChatCompletionResponse chatCompletionResponse = com.alibaba.fastjson.JSON.parseObject(data, ChatCompletionResponse.class);
                                    log.info("测试结果 onEvent：{}", chatCompletionResponse.getData());
                                    // type 消息类型，add 增量，finish 结束，error 错误，interrupted 中断
                                    if (EventType.finish.getCode().equals(type)) {
                                        ChatCompletionResponse.Meta meta = com.alibaba.fastjson.JSON.parseObject(chatCompletionResponse.getMeta(), ChatCompletionResponse.Meta.class);
                                        log.info("[输出结束] Tokens {}", com.alibaba.fastjson.JSON.toJSONString(meta));
                                    }
                                    content.append(chatCompletionResponse.getData());
                                }
                                @Override
                                public void onClosed(EventSource eventSource) {
                                    log.info("对话完成");
                                    // 你可以使用 ChatGLM SDK 进行回答，回复问题；
                                    comment(cookie, topicId, "ChatGLM 回答：" + content);
                                    topicIds.remove(topicId);
                                }
                            });
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                }
            }
        }
    }
}    
```

- 以上代码就是自动智能Ai回贴的流程代码，因为是做示例，所以没有按照职责边界做拆分。
- 这段代码中会扫码帖子，并对符合流程需要我【@小傅哥】回答的帖子，进行采集和回答。
- 回答帖子会创建一个线程，调用 ChatGLM 并对返回的流式数据最封装。最后在完成时候，进行回答操作。
- 注意，这里因为限定的判断了 uId = 241858242255511 所以只有@小傅哥，才会回答。你也可以通过回复帖子，查看自己的 uId 替换。
- 此外，更多的细节代码，可以参考工程。

## 四、测试验证

### 1. 环境配置

```pom
chatglm:
  sdk:
    config:
      # 状态；true = 开启、false 关闭
      enabled: true
      # 官网地址
      api-host: https://open.bigmodel.cn/
      # 官网申请 https://open.bigmodel.cn/usercenter/apikeys
      api-secret-key: 4e087e4135306ef4a676f0cce*****.sgP2D*****
      # 知识星球 Cookie 你需要获取你的 cookie 登录
      cookie: zsxq_access_token=86EB23*****
```

- 首先你需要在 application-dev.yml 配置相关的信息，这些信息可以从下面的说明中获取。
- 如果你暂时又申请不到 ChatGLM 还想测试，可以把 enabled 配置为 false

#### 1.1 获取 ChatGLM ApiSecretKey

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-http-09.png?raw=true" width="850px">
</div>

- 地址：[https://open.bigmodel.cn/usercenter/apikeys](https://open.bigmodel.cn/usercenter/apikeys)
- 备注：申请开通即可，很快就可以使用。

#### 1.2 获取星球 Cookie

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-http-10.png?raw=true" width="850px">
</div>

- 星球：[https://wx.zsxq.com/dweb2/index/group/28885518425541](https://wx.zsxq.com/dweb2/index/group/28885518425541)
- 获取：你可以在进入后，点击任意一个调用的接口，找到 Cookie 信息。
- 注意：如果你对接了模拟登录接口，还可以自动的获取 Cookie 信息。

### 2. 启动程序和回贴

#### 2.1 发个帖子

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-http-11.png?raw=true" width="650px">
</div>

- 注意要圈**小傅哥**发帖子，如果你程序中修改了uId 为自己，那么可以圈你提问。

#### 2.2 回复帖子

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-http-12.png?raw=true" width="850px">
</div>
- 这个是你就会看到后台会快速运行检测帖子和回答。
- 好啦，运行到这就全部完成了。你也可以去做做测试了。
