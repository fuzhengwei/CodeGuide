---
title: 第1节：ChatGLM SDK - 智谱Ai v1
lock: no
---

# 《ChatGPT 微服务应用体系构建》 - chatglm-sdk 第1节：ChatGLM SDK - 智谱Ai

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

清华大学计算机系的超大规模训练模型 ChatGLM-130B 使用效果非常牛，所以我也想把这样的Ai能力接入到自己的应用中或者做一些 IntelliJ IDEA Plugin 使用。但经过了一晚上的折腾，我决定给它写个对接的SDK开源出来！—— 🤔 智谱Ai不是已经有了一个SDK吗？为啥还要写呢？那你写多少了？

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-231011-01.png?raw=true" width="200px">
</div>

在很早之前就关注了智谱Ai(ChatGLM)，也看到官网有一个Java对接的[SDK](https://github.com/zhipuai/zhipuai-sdk-java/)方式。但从前几天开始正式对接发现，这SDK是8月份提交的，10个commit，而且已经2个月没有更新了。所以真的是不少Bug呀，呀，呀！如果不去修改它的SDK代码，就没法对接。如；`ConfigV3类中，拆分ApiKey的操作；String[] arrStr = apiSecretKey.split(".");` 但这里的`.`是正则的关键字，所以根本没法拆分。一起动就报错 `invalid apiSecretKey` 这对于初次对接并且没有看源码的伙伴来说，是不小的炸雷。

不过，虽然 SDK 有点赶工，不好用。但不影响`智谱Ai(ChatGLM)`是个好东西。他的官网中有API HTTP 接口对接描述。所以，小傅哥决定跟着按照它的文档写一个能简单对接，代码有干净整洁的 SDK 让大家使用。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-231011-06.png?raw=true" width="650px">
</div>

那么，接下来小傅哥就介绍下，如何基于`智谱Ai(ChatGLM)`的开发者文档，开发一个通用的SDK组件。也让后续有想法PR贡献源码的伙伴，一起参与进来。—— 别看东西不大，写到简历上，也是非常精彩的一笔！

>本文不止有智谱Ai-SDK开发，还有如何在项目中运用SDK开发一个自己的OpenAi服务。文末有SDK链接和OpenAi应用工程。

## 一、对接鉴权

- 文档：[https://open.bigmodel.cn/dev/api](https://open.bigmodel.cn/dev/api)
- ApiKey：[https://open.bigmodel.cn/usercenter/apikeys](https://open.bigmodel.cn/usercenter/apikeys) - `申请个人授权，创建ApiKey即可`

智谱Ai的Api文档，与ChatGPT对接有一些差异。如果大家对接过ChatGPT开发，直接获取一个ApiKey就可以使用了。但在对接智谱Ai的Api时，需要把获取的ApiKey按照`.`号分割，并需要进行JWT-Token的创建。而这个Token才是实际传给接口的内容。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-231011-02.png?raw=true" width="650px">
</div>

- 因为生成Token会比较耗时，所以这里会使用Guava框架进行本地缓存29分钟，有效期30分钟的Token，确保可以有效的刷新。
- 在工程中提供了 BearerTokenUtils Token 生成工具类，测试的时候可以使用。

## 二、接口处理

**文档**：[https://open.bigmodel.cn/dev/api#chatglm_lite](https://open.bigmodel.cn/dev/api#chatglm_lite) - 以Api文档的chatglm_lite模型举例对接 

| 传输方式     | https                                                        |
| ------------ | ------------------------------------------------------------ |
| 请求地址     | https://open.bigmodel.cn/api/paas/v3/model-api/chatglm_lite/sse-invoke |
| 调用方式     | SSE                                                          |
| 字符编码     | UTF-8                                                        |
| 接口请求头   | accept: text/event-stream                                    |
| 接口请求格式 | JSON                                                         |
| 响应格式     | 标准 Event Stream                                            |
| 接口请求类型 | POST                                                         |
| 开发语言     | 任意可发起 HTTP 请求的开发语言                               |

在正式开发代码，要把接口的使用先简单测试运行出来。之后再去编写代码。为此这里小傅哥先根据官网的文档和鉴权使用方式，编写了 curl http 请求；

```java
curl -X POST \
        -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsInNpZ25fdHlwZSI6IlNJR04ifQ.eyJhcGlfa2V5IjoiNGUwODdlNDEzNTMwNmVmNGE2NzZmMGNjZTNjZWU1NjAiLCJleHAiOjE2OTY5OTM5ODIzMTQsInRpbWVzdGFtcCI6MTY5Njk5MjE4MjMxNH0.9nxhRXTJcP4Q_YTQ8w5y0CZOBOu0epP1J56oDaYewQ8" \
        -H "Content-Type: application/json" \
        -H "User-Agent: Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)" \
        -H "Accept: text/event-stream" \
        -d '{
        "top_p": 0.7,
        "sseFormat": "data",
        "temperature": 0.9,
        "incremental": true,
        "request_id": "xfg-1696992276607",
        "prompt": [
        {
        "role": "user",
        "content": "写个java冒泡排序"
        }
        ]
        }' \
  http://open.bigmodel.cn/api/paas/v3/model-api/chatglm_lite/sse-invoke
```

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-231011-03.png?raw=true" width="850px">
</div>

- **注意**：Authorization: Bearer 后面传的是 JWT Token 不是一个直接从官网复制的 ApiKey - `你可以使用工程中的 BearerTokenUtils 创建。`
- 之后可以直接运行这段脚本(也可以导入到ApiPost工具中)，执行后就能获得到运行效果了。—— 速度非常快！

## 三、组件开发

在🤔考虑到抽象和设计原则下，小傅哥这里采用了会话模型结构进行工程框架设计。把程序的调用抽象为一次会话，而会话的创建则交给工厂🏭。通过工厂屏蔽使用细节，在使用上简化调用，尽可能让外部最少知道原则。这样的设计实现方式，既可以满足调用方开心的使用，也可以让SDK贡献者见代码如见文档，容易理解和上手。

### 1. 工程结构

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-231011-04.png?raw=true" width="450px">
</div>

- 工程非常注重会话的设计和使用，因为框架的根基搭建好以后，扩展各项功能就会有迹可循。`大部分代码就是因为早期没有考虑好框架，最后功能来了被填充的很乱。`

### 2. 会话流程

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-231011-05.png?raw=true" width="550px">
</div>

- 会话流程以工厂创建 Session 为入口点进行使用，其他的操作都在组件内自己处理好。

### 3. 代码举例

```java
@Override
public OpenAiSession openSession() {
    // 1. 日志配置
    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    httpLoggingInterceptor.setLevel(configuration.getLevel());
    
    // 2. 开启 Http 客户端
    OkHttpClient okHttpClient = new OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(new OpenAiHTTPInterceptor(configuration))
            .connectTimeout(configuration.getConnectTimeout(), TimeUnit.SECONDS)
            .writeTimeout(configuration.getWriteTimeout(), TimeUnit.SECONDS)
            .readTimeout(configuration.getReadTimeout(), TimeUnit.SECONDS)
            .build();
    configuration.setOkHttpClient(okHttpClient);
    
    // 3. 创建 API 服务
    IOpenAiApi openAiApi = new Retrofit.Builder()
            .baseUrl(configuration.getApiHost())
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .build().create(IOpenAiApi.class);
    configuration.setOpenAiApi(openAiApi);
    return new DefaultOpenAiSession(configuration);
}
```

- 这是一段 DefaultOpenAiSessionFactory 创建工厂开启会话的服务对象。使用方只需要在自己的工程中，创建出一个工厂对象就可以对接使用了。**下文有代码示例**
- 其他更多的代码，直接看小傅哥开发好的 chatglm-sdk-java 

## 四、组件使用

### 1. 组件配置

- 申请ApiKey：[https://open.bigmodel.cn/usercenter/apikeys](https://open.bigmodel.cn/usercenter/apikeys) - 注册申请开通，即可获得 ApiKey
- 运行环境：JDK 1.8+
- maven pom - `暂时测试阶段，未推送到Maven中央仓库，需要下载代码本地 install 后使用`

```pom
<dependency>
    <groupId>cn.bugstack</groupId>
    <artifactId>chatglm-sdk-java</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

- 源码(Github)：[https://github.com/fuzhengwei/chatglm-sdk-java](https://github.com/fuzhengwei/chatglm-sdk-java)
- 源码(Gitee)：[https://gitee.com/fustack/chatglm-sdk-java](https://gitee.com/fustack/chatglm-sdk-java)
- 源码(Gitcode)：[https://gitcode.net/KnowledgePlanet/road-map/chatglm-sdk-java](https://gitcode.net/KnowledgePlanet/road-map/chatglm-sdk-java)

### 2. 单元测试

```java
@Slf4j
public class ApiTest {

    private OpenAiSession openAiSession;

    @Before
    public void test_OpenAiSessionFactory() {
        // 1. 配置文件
        Configuration configuration = new Configuration();
        configuration.setApiHost("https://open.bigmodel.cn/");
        configuration.setApiSecretKey("4e087e4135306ef4a676f0cce3cee560.sgP2*****");
        // 2. 会话工厂
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);
        // 3. 开启会话
        this.openAiSession = factory.openSession();
    }

    /**
     * 流式对话
     */
    @Test
    public void test_completions() throws JsonProcessingException, InterruptedException {
        // 入参；模型、请求信息
        ChatCompletionRequest request = new ChatCompletionRequest();
        request.setModel(Model.CHATGLM_LITE); // chatGLM_6b_SSE、chatglm_lite、chatglm_lite_32k、chatglm_std、chatglm_pro
        request.setPrompt(new ArrayList<ChatCompletionRequest.Prompt>() {
            private static final long serialVersionUID = -7988151926241837899L;

            {
                add(ChatCompletionRequest.Prompt.builder()
                        .role(Role.user.getCode())
                        .content("写个java冒泡排序")
                        .build());
            }
        });

        // 请求
        openAiSession.completions(request, new EventSourceListener() {
            @Override
            public void onEvent(EventSource eventSource, @Nullable String id, @Nullable String type, String data) {
                ChatCompletionResponse response = JSON.parseObject(data, ChatCompletionResponse.class);
                log.info("测试结果 onEvent：{}", response.getData());
                // type 消息类型，add 增量，finish 结束，error 错误，interrupted 中断
                if (EventType.finish.getCode().equals(type)) {
                    ChatCompletionResponse.Meta meta = JSON.parseObject(response.getMeta(), ChatCompletionResponse.Meta.class);
                    log.info("[输出结束] Tokens {}", JSON.toJSONString(meta));
                }
            }

            @Override
            public void onClosed(EventSource eventSource) {
                log.info("对话完成");
            }
        });

        // 等待
        new CountDownLatch(1).await();
    }

}
```

- 这是一个单元测试类，也是最常使用的流式对话模式。

## 五、应用接入

### 1. SpringBoot 配置类

```java
@Configuration
@EnableConfigurationProperties(ChatGLMSDKConfigProperties.class)
public class ChatGLMSDKConfig {

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

}

@Data
@ConfigurationProperties(prefix = "chatglm.sdk.config", ignoreInvalidFields = true)
public class ChatGLMSDKConfigProperties {

    /** 状态；open = 开启、close 关闭 */
    private boolean enable;
    /** 转发地址 */
    private String apiHost;
    /** 可以申请 sk-*** */
    private String apiSecretKey;

}
```

```java
@Autowired(required = false)
private OpenAiSession openAiSession;
```

- 注意：如果你在服务中配置了关闭启动 ChatGLM SDK 那么注入 openAiSession 为 null

### 2. yml 配置

```pom
# ChatGLM SDK Config
chatglm:
  sdk:
    config:
      # 状态；true = 开启、false 关闭
      enabled: false
      # 官网地址 
      api-host: https://open.bigmodel.cn/
      # 官网申请 https://open.bigmodel.cn/usercenter/apikeys
      api-key: 4e087e4135306ef4a676f0cce3cee560.sgP2DUs*****
```

- 你可以在配置文件中，通过 enabled 参数，启动和关闭 ChatGLM SDK


