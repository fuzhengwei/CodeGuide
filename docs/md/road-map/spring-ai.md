---
title: Spring AI
lock: need
---

# Spring AI

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。今天来带大家体验一下 Spring AI 的 Easy 开发！

现在的 OpenAI 对接开发真的是越来越容易了，直接引入 Spring AI 这个组件包，配置上可调用的 ApiHost、ApiKey 就可以完成对 OpenAI（ChatGPT）接口的调用。在此之前也有那么多各类的接口服务，还没有像 OpenAI 这样，被 Spring 如此重视，直接提供专属的 SDK 包，封装所有大模型（`暂无国内的`）。这也说明 OpenAI 的趋势力量多么庞大！

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/springcloud-gateway-01.gif" width="150px"/>
</div>

**OpenAI 结合业务才更有价值**

虽然 OpenAI 的接口对接是越来越简单了，但我们要知道，一个实际的项目就不只是对接 OpenAI 接口，还需要完成一系列的业务流程封装。就比如目前我们看到国内的各类套壳或自研类的 OpenAI 服务，都会包括；`用户`、`鉴权`、`账户`、`支付`、`场景`等，一套东西来支撑整个流程跑通，同时还有系统工程背后的`舆情监控`、`敏感词过滤`、`数据存储`、`行为分析`等各类操作。这些东西也就是常说的，不能只会一个技术点，而是要结合场景，用技术支撑业务落地。

接下来，小傅哥会分享 Spring AI + Gpt-4o 的对接使用，以及介绍如何开发应用级的 OpenAI 项目。

## 一、简单介绍

Spring AI 项目，是为开发 AI 应用程序提供了 Spring 友好的 API 和抽象。所有的大模型对接（OpenAI、Ollama、Azure OpenAI、Amazon Bedrock、HuggingFace、Google VertexAI、Mistral AI）都以一种统一标准的形式进行。这样就减少了大家再额外开发对接的成本了，也不用维护和兼容各个 OpenAI 的接口迭代。

- 官网：[https://docs.spring.io/spring-ai/reference/index.html](https://docs.spring.io/spring-ai/reference/index.html)
- 文档：[https://docs.spring.io/spring-ai/reference/1.0-SNAPSHOT/index.html](https://docs.spring.io/spring-ai/reference/1.0-SNAPSHOT/index.html)
- 源码：[https://github.com/spring-projects/spring-ai](https://github.com/spring-projects/spring-ai)

## 二、工程对接

### 1. 工程说明

- 环境：JDK 17、SpringBoot 3.2.3、spring-ai 0.8.0
- 源码：[xfg-dev-tech-spring-ai](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-spring-ai)

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-spring-ai-01.png" width="350px"/>
</div>

在此工程中完成了 SpringAI 模块的引入，以及使用提供的 API 接口，完成同步应答、流式应答、图片绘制功能。

### 2. 引入模块

```pom
 <dependency>
     <groupId>org.springframework.ai</groupId>
     <artifactId>spring-ai-bom</artifactId>
     <version>0.8.0</version>
     <type>pom</type>
     <scope>import</scope>
 </dependency>
 
 <dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
</dependency>
```

- spring-ai 也在不断的发展，2024年5月22日 发布了 1.0.0 M1 并对代码做了一些调整。在生产级别使用需要注意📢版本的迭代。

### 2. 接口对接

测试之前需要在 application-dev.yml 配置对接的信息。

```yaml
spring:
  ai:
    openai:
      base-url: https://api.aws-*****/
      api-key: sk-oLkakcax33mJl628D3A533Fd67A24602Ac37D6*****
```

注意 spring-ai 提供了各类大模型的对接，都可以按需配置到这里。*在它的源码 autoConfig 类里会有要配置参数的名称，可以参考。*

#### 2.1 功能测试

```java
@Resource
private OpenAiChatClient chatClient;
@Resource
private OpenAiImageClient openaiImageClient;

@Test
public void test_generate() {
    ChatResponse chatResponse = chatClient.call(
            new Prompt(
                    "1+1",
                    OpenAiChatOptions.builder()
                            .withModel("gpt-4o")
                            .build()
            ));
    log.info("测试结果：{}", JSON.toJSONString(chatResponse));
}

@Test
public void test_generate_stream() throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(1);
    Flux<ChatResponse> stream = chatClient.stream(new Prompt("1+1"));
    stream.subscribe(
            chatResponse -> {
                AssistantMessage output = chatResponse.getResult().getOutput();
                log.info("测试结果: {}", JSON.toJSONString(output));
            },
            Throwable::printStackTrace,
            () -> {
                countDownLatch.countDown();
                System.out.println("Stream completed");
            }
    );
    countDownLatch.await();
}

@Test
public void test_generate_image() {
    ImageResponse imageResponse = openaiImageClient.call(
            new ImagePrompt("画个小狗",
                    OpenAiImageOptions.builder()
                            .withModel("dall-e-3")
                            .withQuality("hd")
                            .withN(1)
                            .withHeight(1024)
                            .withWidth(1024)
                            .build()
            )
    );
    log.info("测试结果: {}", JSON.toJSONString(imageResponse));
}
```

**测试结果**

```java
24-05-26.16:46:46.287 [ForkJoinPool.commonPool-worker-1] INFO  OpenAITest             - 测试结果: {"content":"","messageType":"ASSISTANT","properties":{"role":"ASSISTANT"}}
24-05-26.16:46:46.288 [ForkJoinPool.commonPool-worker-1] INFO  OpenAITest             - 测试结果: {"content":"","messageType":"ASSISTANT","properties":{"role":"ASSISTANT"}}
24-05-26.16:46:46.289 [ForkJoinPool.commonPool-worker-1] INFO  OpenAITest             - 测试结果: {"content":"1","messageType":"ASSISTANT","properties":{"role":"ASSISTANT"}}
24-05-26.16:46:46.289 [ForkJoinPool.commonPool-worker-1] INFO  OpenAITest             - 测试结果: {"content":"+","messageType":"ASSISTANT","properties":{"role":"ASSISTANT"}}
24-05-26.16:46:46.289 [ForkJoinPool.commonPool-worker-1] INFO  OpenAITest             - 测试结果: {"content":"1","messageType":"ASSISTANT","properties":{"role":"ASSISTANT"}}
24-05-26.16:46:46.289 [ForkJoinPool.commonPool-worker-1] INFO  OpenAITest             - 测试结果: {"content":" equals","messageType":"ASSISTANT","properties":{"role":"ASSISTANT"}}
24-05-26.16:46:46.289 [ForkJoinPool.commonPool-worker-1] INFO  OpenAITest             - 测试结果: {"content":" ","messageType":"ASSISTANT","properties":{"role":"ASSISTANT"}}
24-05-26.16:46:46.290 [ForkJoinPool.commonPool-worker-1] INFO  OpenAITest             - 测试结果: {"content":"2","messageType":"ASSISTANT","properties":{"role":"ASSISTANT"}}
24-05-26.16:46:46.290 [ForkJoinPool.commonPool-worker-1] INFO  OpenAITest             - 测试结果: {"content":"","messageType":"ASSISTANT","properties":{"role":"ASSISTANT"}}
Stream completed
```

- 测试结果为 test_generate_stream 流式返回 `1+1` 的应答结果。 

#### 2.2 接口服务

```java
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/openai/")
public class OpenAiController {

    @Resource
    private OpenAiChatClient chatClient;

    /**
     * curl http://localhost:8090/api/v1/openai/generate?message=1+1
     */
    @RequestMapping(value = "generate", method = RequestMethod.GET)
    public ChatResponse generate(@RequestParam String message) {
        return chatClient.call(
                new Prompt(
                        message,
                        OpenAiChatOptions.builder()
                                .withModel("gpt-4o")
                                .build()
                ));
    }

    /**
     * curl http://localhost:8090/api/v1/openai/generate_stream?message=1+1
     */
    @RequestMapping(value = "generate_stream", method = RequestMethod.GET)
    public Flux<ChatResponse> generateStream(@RequestParam String message) {
        return chatClient.stream(new Prompt(message));
    }

}
```

在 OpenAiController 中提供了对接两个接口的方法，以及同步和流式返回。*流式返回还可以使用 ResponseBodyEmitter 进行流式返回*

Spring AI 提供的 OpenAI 大模型对接是 Easy 的很，减去了自己开发代码对接大模型。但仅仅是这样一个简单还不够，我们还需要学习积累的更多！如；微信鉴权登录怎么做、支付怎么对接、账户额度怎么扣减、限流熔断怎么操作等等，实际场景问题才是有价值的核心内容，所以，咱们要上个大菜！☞ [OpenAi 大模型应用服务体系构建 - API-SDK、鉴权、公众号、企业微信、支付服务](https://bugstack.cn/md/project/chatgpt/chatgpt.html)