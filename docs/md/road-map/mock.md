---
title: Mock
lock: need
---

# Mock 单元测试&插件生成测试代码

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=492503304&bvid=BV1mN411x7rJ&cid=1311297417&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

本文的宗旨在于通过简单干净实践的方式教会读者，如何使用 [Mock](https://zh.wikipedia.org/wiki/%E6%A8%A1%E6%8B%9F%E5%AF%B9%E8%B1%A1) 进行工程的单元测试，以便于验证系统中的独立模块功能的健壮性。

从整个工程所处不同阶段的测试手段包括；单元测试、集成测试、系统测试、验收测试、性能测试、安全测试、回归测试，以及兼容、可靠、可用性测试。

而单元测试的重点在于，对工程开发中的代码，进行流程中的单元化测试。如一整个下单流程中，需要调用各项外部的接口(风控、账户、营销、试算、支付)，才能完成整个下单流程。但在本地开发过程中，不太能将所有的外部接口都调试为开发环境可用状态，所有这个时候要做单元化测试，对于一些不能随时提供服务的接口进行 [Mock](https://zh.wikipedia.org/wiki/%E6%A8%A1%E6%8B%9F%E5%AF%B9%E8%B1%A1) 处理。

本文涉及的工程：

- xfg-dev-tech-mock：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-mock](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-mock)
- chatglm-sdk-java：[https://gitcode.net/KnowledgePlanet/road-map/chatglm-sdk-java](https://gitcode.net/KnowledgePlanet/road-map/chatglm-sdk-java)

## 一、案例背景

因为 Mock 单元测试的重点，主要体现在；功能流程较长、调用外部接口稳定性较差、测试过程中希望可以不启动 SpringBoot 应用就能对单个功能模块进行测试验证。

所以本章节带着这样一个案例背景的情况，小傅哥带着大家把 [《HTTP 框架使用和场景实战 - 结合ChatGLM自动回帖！》](https://bugstack.cn/md/road-map/http.html) 做一个小重构。来对 Mock 框架进行验证使用。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-mock-01.png" width="650px">
</div>

- 首先，这里使用 DDD 工程模型结构，搭建出测试工程。—— DDD 是一种软件设计方法，而软件的设计方法涵盖了；范式、模型、框架、方法论。所以通常下 MVC 与 DDD 的对比先从模型、框架在到思想设计和方法论。
- 之后，我们在这样的一个模型结构下，实现出自动回帖的领域功能。而这个模型的实现恰好需要调用外部的接口和 ChatGLM SDK，这与我们要做的 Mock 测试正好符合，因为在大部分开发场景下，远程的 HTTP 调用可能不不会一直可用，所以可以用 Mock 方式进行模拟。

## 二、功能实现

### 1. 工程结构

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-mock-02.png" width="450px">
</div>

- 在 domain 中实现一个zsxq的自动回帖领域，而它所需的要调用的接口则由基础设施层提供。
- 另外在 app 中还有 ChatGLM SDK 的配置启动，也会被注入到 AiReply 实现类中。

### 2. Ai模块启动

```yml
# ChatGLM SDK Config
chatglm:
  sdk:
    config:
      # 状态；true = 开启、false 关闭
      enabled: false
      # 官网地址
      api-host: https://open.bigmodel.cn/
      # 官网申请 https://open.bigmodel.cn/usercenter/apikeys
      api-secret-key: d570f7c5d289cdac2abdfdc562e39f3f.trqz1dH8ZK6ED7Pg
```

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

- 所有的这些配置类的服务，都可以放到 app下的 config 模块中。
- ChatGLM 可以直接在官网申请，默认会赠送18元的额度，对于它所提供的模型，还是非常够测试使用的。

### 3. 基础设置 - 接口调用

#### 3.1 接口 - 防腐对接

**源码**：`cn.bugstack.xfg.dev.tech.infrastructure.gateway.api.IZSXQApi`

```java
public interface IZSXQApi {

    /**
     * 查询知识星球帖子内容
     *
     * @return 帖子数据
     * @throws IOException 异常
     */
    ResponseDTO topics() throws IOException;

    /**
     * 回复帖子
     *
     * @param topicId 帖子ID
     * @param content 回复内容
     */
    void comment(long topicId, String content);

}
```

- 在基础设置层中定义 gateway 网关接口调用，对于外部的接口使用，中间要做一层防腐，不要直接把外部的接口暴露出去使用。

#### 3.2 使用 - 依赖倒置

**源码**：`cn.bugstack.xfg.dev.tech.infrastructure.gateway.adapter.ZSXQAdapter`

```java
public class ZSXQAdapter implements IZSXQAdapter {

    @Resource
    private IZSXQApi zsxqApi;

    @Override
    public List<TopicsItemVO> queryTopics() {
        try {
            ResponseDTO responseDTO = zsxqApi.topics();
            RespData respData = responseDTO.getRespData();
            List<TopicsItem> topics = respData.getTopics();
            List<TopicsItemVO> topicsItemVOList = new ArrayList<>();

            for (TopicsItem topicsItem : topics) {
                TopicsItemVO topicsItemVO = TopicsItemVO.builder()
                        .topicId(topicsItem.getTopicId())
                        .talk(topicsItem.getTalk().getText())
                        .showCommentsItems(topicsItem.getShowComments() != null ? topicsItem.getShowComments().stream()
                                .map(showCommentsItem -> {
                                    TopicsItemVO.ShowCommentsItem item = new TopicsItemVO.ShowCommentsItem();
                                    item.setUserId(showCommentsItem.getOwner().getUserId());
                                    return item;
                                })
                                .collect(Collectors.toList()) : new ArrayList<>())
                        .build();

                topicsItemVOList.add(topicsItemVO);
            }

            return topicsItemVOList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean comment(long topicId, String content) {
        zsxqApi.comment(topicId, content);
        return true;
    }

}
```

- 注意，TopicsItemVO 对象来自于 domain 下领域中模型下的 VO 对象。因为是依赖倒置的，所以 infrastructure 引用的是 domain 并对其接口做实现处理。
- 并且，TopicsItemVO 只是需要获取自己需要的对象，还可以做简单的封装处理。这样可以衔接外部接口和内部逻辑中间的桥梁，不做强关联。

### 4. 任务调度

**源码**：`cn.bugstack.xfg.dev.tech.job.ReplyJob`

```java
public class ReplyJob {

    @Resource
    private IAiReply aiReply;

    @Scheduled(cron = "0/10 * * * * ?")
    public void exec() throws Exception {
        log.info("自动回帖任务开始执行...");
        aiReply.doAiReply();
    }

}
```

- 现在在 trigger 触发器层中的 job 下，就可以调用我们已经实现好的 AiReply 自动回帖功能了。
- 此外，注意 Application 中 `@EnableScheduling` 注解是开启的，否则任务不能执行。

## 三、系统测试

### 1. 集成测试

```java
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    private IAiReply aiReply;

    @Test
    public void test_IAiReply() {
        aiReply.doAiReply();
    }

}
```

- 通常情况下这种测试是最多的，写多少功能，就直接测试调用。如功能中所用到的；HTTP接口、RPC接口、数据库、Redis等资源，都会需要使用到。有时候也因为这样，所以不好测试。那么单元测试就出现了。

### 2. 单元测试

```java
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MockTest {

    @Resource
    private IAiReply aiReply;

    @MockBean
    private IZSXQAdapter izsxqAdapter;

    @Test
    public void test_doAiReply() throws InterruptedException, JsonProcessingException {
        Mockito.when(izsxqAdapter.queryTopics()).thenReturn(new ArrayList<TopicsItemVO>() {{
            TopicsItemVO topicsItemVO = new TopicsItemVO();
            topicsItemVO.setTopicId(10001L);
            topicsItemVO.setTalk("<e type=\"mention\" uid=\"241858242255511\" title=\"%40%E5%B0%8F%E5%82%85%E5%93%A5\" /> 提问 java 冒泡排序");
            add(topicsItemVO);
        }});

        Mockito.when(izsxqAdapter.comment(Mockito.anyLong(), Mockito.anyString())).thenReturn(true);

        aiReply.doAiReply();

        // 等待；ChatGLM 异步回复
        new CountDownLatch(1).await();
    }

}
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-mock-03.png" width="550px">
</div>

- 在基于使用 SpringBoot 的启动，以及一部分功能需要走真实调用的情况下，另外一部分功能的接口可能没法调用时。可以使用这样的一种 MockBean 的方式进行处理，并对整条链路上调用到的接口方法进行 Mock 处理。`Mockito.when(调用到的接口).thenReturn(返回的结果);
- 那么现在在测试方法中，做了2个Mock操作，把查询帖子和回复帖子，都给处理掉。也就是有了 Mock 以后，程序调用到这里，就直接走 Mock 里设置的结果信息了。

### 3. 功能测试

```java
@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class ZSXQAdapterTest {

    @Mock
    private IZSXQApi mockZsxqApi;

    @InjectMocks
    private ZSXQAdapter zsxqAdapterUnderTest;

    @Test
    public void testQueryTopics() throws Exception {
        // Setup
        final List<TopicsItemVO> expectedResult = Arrays.asList(TopicsItemVO.builder()
                .topicId(0L)
                .talk("talk")
                .showCommentsItems(Arrays.asList(TopicsItemVO.ShowCommentsItem.builder()
                        .userId(0L)
                        .build()))
                .build());

        // Configure IZSXQApi.topics(...).
        final ResponseDTO responseDTO = new ResponseDTO();
        final RespData respData = new RespData();
        final TopicsItem topicsItem = new TopicsItem();
        final ShowCommentsItem showCommentsItem = new ShowCommentsItem();
        final Owner owner = new Owner();
        owner.setUserId(0L);
        showCommentsItem.setOwner(owner);
        topicsItem.setShowComments(Arrays.asList(showCommentsItem));
        final Talk talk = new Talk();
        talk.setText("talk");
        topicsItem.setTalk(talk);
        topicsItem.setTopicId(0L);
        respData.setTopics(Arrays.asList(topicsItem));
        responseDTO.setRespData(respData);
        when(mockZsxqApi.topics()).thenReturn(responseDTO);

        // Run the test
        final List<TopicsItemVO> result = zsxqAdapterUnderTest.queryTopics();

        // Verify the results
        assertEquals(expectedResult, result);

        log.info("测试结果：{}", JSON.toJSONString(result));
    }
    
}
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-mock-04.png" width="550px">
</div>

- 除了前面两种测试，我们在开发功能的时候，还有场景测试；不启动 SpringBoot 但希望对实现的功能进行测试。
- 那么这里所体现的就是这样的测试，主要使用；`@RunWith(MockitoJUnitRunner.class)`、`@Mock`、`@InjectMocks` 相当于模拟了一个启动的过程，只不过都是 Mock 的信息。但你可以根据这些信息来调试你的接口。
- 提示：你可以安装 `IDEA Plugin Squaretest` 它能自动的帮你生成Mock单元测试。`这个插件是收费的，但还好不贵。`
