---
title: Github Models
lock: need
---

# Github Models（AI接口），申请使用教程

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

现在做 AI 应用开发的小伙伴越来越多了，随之而来的是对 LLM 大模型接口的使用依赖。一般的模型，可能会让程序出现幻觉。而高质量的模型，大部分是需要付费，比如 `openai/gpt-5`、`azureml-xai/grok3`、`azureml/Phi-4` 等。**那怎么办！？🤔**

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-springcloud-feign-01.gif" width="150px"/>
</div>

这些免费的不错的大模型（LLM），也都是有对应的频次和最大出入/输出token的限制。虽然不会让咱们玩命的调用测试。但在程序开发初期，对于单个功能的验证还是非常方便的。接下来，小傅哥就给大家演示下，关于 Github 免费提供的大模型如何配置使用。

## 一、模型说明

### 1. 官网地址

官网：[https://github.com/marketplace?type=models](https://github.com/marketplace?type=models)

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-modes-01.png" width="750px"/>
</div>

在 Github marketplace 下提供了非常多的免费的 LLM 使用，gpt-5、gpt-4.1 都是有的，具备完整的测试功能。有时候还会新增加其他模型。

### 2. 频次限制

地址：[https://docs.github.com/en/github-models/use-github-models/prototyping-with-ai-models#rate-limits](https://docs.github.com/en/github-models/use-github-models/prototyping-with-ai-models#rate-limits)

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-modes-02.png" width="750px"/>
</div>

- 每日150次、每分钟15次，每次token，8000进、4000出。对于单个模型的验证是没问题的，不过对于复杂的 ai agent 智能体是跑不动的。所以，这块测试的话，要分开验证。之后对于全流程的，在购买 api 进行测试即可。
- 如果小伙伴还觉得，每分钟15次，有点不够干的，那你可以弄10个账号，通过 nginx 负载下，这样基本每分钟150次，一天1500次还是很够用的。

## 二、模型使用

### 1. 测试工程

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-modes-06.png" width="650px"/>
</div>

- 地址：[https://github.com/fuzhengwei/xfg-dev-tech-github-models](https://github.com/fuzhengwei/xfg-dev-tech-github-models)
- 说明：这里小傅哥准备了一套基于 Spring AI 的测试工程，来验证 Github LLM 的使用。你可以下载后，替换 token/apikey 即可调用验证。
- 环境：jdk 17、maven 3.8.x、spring ai 1.1.0

### 2. 对接申请

#### 2.1 申请token

地址：https://github.com/settings/tokens/new

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-modes-03.png" width="750px"/>
</div>

- 在 Tokens 下，添加 Note 描述和过期时间，完事后不用加任何其他权限，拉到最后设置点击 `Generate token` 生成令牌即可。
- 生成后复制你的 Token，复制后关闭就看不见了。只有删除和创建新的。

#### 2.2 查看curl（api）

地址：[https://github.com/marketplace/models/azure-openai/gpt-4-1/playground](https://github.com/marketplace/models/azure-openai/gpt-4-1/playground)

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-modes-04.png" width="750px"/>
</div>

在左侧你可以选择需要使用的模型，之后进行对话验证。之后，在右侧有一个 `Use this model` 这里可以获得模型 API 请求信息。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-modes-05.png" width="750px"/>
</div>

你现在单独验证 api，在整个程序测试前，要确保你的 api 是可用的。

### 3. 测试验证

```java
public class ApiTest {

    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Resource resource = new ClassPathResource("dog.png", classLoader);

        OpenAiApi openAiApi = OpenAiApi.builder()
                .baseUrl("https://models.github.ai/inference")
                // apikey 要替换为你的，在 https://github.com/settings/tokens 创建即可。
                .apiKey("ghp_FfiwBkVunYDQyhIwaQSccVw******")
                .completionsPath("/chat/completions")
                .embeddingsPath("/embeddings")
                .build();

        ChatModel chatModel = OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("openai/gpt-4.1")
                        .build())
                .build();

        // 模型测试，没问题可以识别图片
        ChatResponse response = chatModel.call(new Prompt(
                UserMessage.builder()
                        .text("请描述这张图片的主要内容，并说明图中物品的可能用途。")
                        .media(Media.builder()
                                .mimeType(MimeType.valueOf(MimeTypeUtils.IMAGE_PNG_VALUE))
                                .data(resource)
                                .build())
                        .build()));

        System.out.println("测试结果" + JSON.toJSONString(response));

    }

}
```

```java
测试结果{"metadata":{"empty":false,"id":"chatcmpl-CzHhVYaXPv3HjJ3oKE4YKPamPQRQB","model":"gpt-4.1-2025-04-14","rateLimit":{"requestsLimit":1000,"requestsRemaining":996,"tokensLimit":1000000,"tokensRemaining":980423},"usage":{"completionTokens":0,"nativeUsage":{},"promptTokens":0,"totalTokens":0}},"result":{"metadata":{"contentFilters":[],"empty":true,"finishReason":"STOP"},"output":{"media":[],"messageType":"ASSISTANT","metadata":{"role":"ASSISTANT","messageType":"ASSISTANT","finishReason":"STOP","refusal":"","index":0,"annotations":[{"$ref":"$.metadata.rateLimit.usage.nativeUsage"}],"id":"chatcmpl-CzHhVYaXPv3HjJ3oKE4YKPamPQRQB"},"text":"这张图片的主要内容是一只卡通风格的小狗。小狗是棕色的，脸部较大，眼睛圆而突出，表情看起来有些疑惑或惊讶，嘴巴微微张开，尾巴翘起。整个形象简洁可爱，线条和颜色运用都比较简单。\n\n**物品的可能用途：**\n1. **图标或表情包**：这种卡通形象常用于聊天软件中的表情包或头像，传达惊讶、困惑等情感。\n2. **儿童绘本或动画角色**：可以作为儿童图书或动画中的形象角色，吸引小朋友的注意力。\n3. **宠物相关宣传设计**：可用于宠物店、宠物产品的宣传海报、包装或logo，增加亲和力。\n4. **教育材料**：在教学课件或教育类APP中，用于引导、示范或者增加趣味性。\n\n整体而言，这是一种很常见且可爱的卡通动物形象，主要用于与宠物、儿童或者情感表达相关的领域。","toolCalls":[]}},"results":[{"$ref":"$.metadata.rateLimit.usage.nativeUsage.result"}]}
```

- 这是一段基于 Spring AI 配置 github llm api的使用方式，尤其注意，baseUrl、apiKey、completionsPath、embeddingsPath、openai/gpt-4.1，这些配置信息和你直接使用管的 gpt 配置是有差异的。
- 本案例，是识别图片的用途，你也可以验证其他功能。这样的项目在小傅哥的 [《AI Agent 智能体》](https://bugstack.cn/md/project/ai-knowledge/ai-knowledge.html) 都有讲解。

## 三、使用拓展

### 1. 部署脚本

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-modes-08.png" width="750px"/>
</div>

- 这是一套 nginx 转发脚本。你需要有自己的域名（各个云服务器厂商都可以购买）以及申请 ssl 后才可以配置使用。
- ssl 可以从 [httpok](https://bugstack.cn/md/road-map/ssl-httpsok.html) 申请使用。地址：[https://bugstack.cn/md/road-map/ssl-httpsok.html](https://bugstack.cn/md/road-map/ssl-httpsok.html)

### 2. 转发说明（nginx）

如果你希望在使用的过程中，让模型是以你之前使用 openai 一样的方式，那么可以配置一套 Nginx 转发；

```java
server {
    listen       80;
    listen  [::]:80;
    server_name  需要配置域名（api.xxx.cn）;

    rewrite ^(.*) https://$server_name$1 permanent;

}

server {
    listen       443 ssl;
    server_name  需要配置域名（api.xxx.cn）;

    ssl_certificate      /etc/nginx/ssl/_.itedus.cn.pem;
    ssl_certificate_key  /etc/nginx/ssl/_.itedus.cn.key;

    ssl_session_cache    shared:SSL:1m;
    ssl_session_timeout  5m;

    ssl_ciphers  HIGH:!aNULL:!MD5;
    ssl_prefer_server_ciphers  on;

    location / {
        proxy_set_header   X-Real-IP         $remote_addr;
        proxy_set_header   Host              $http_host;
        proxy_set_header   X-Forwarded-For   $proxy_add_x_forwarded_for;
        root   /usr/share/nginx/html;
        index  index.html index.htm;
    }

    location /v1/ {
        # 去掉路径中的/v1前缀
        rewrite ^/v1/(.*) /$1 break;
        proxy_pass https://models.inference.ai.azure.com;
        proxy_http_version 1.1;
        chunked_transfer_encoding off;
        proxy_buffering off;
        proxy_cache off;
        proxy_set_header X-Forwarded-For $remote_addr;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }
}
```

- nginx 的方式是，当访问有 v1/ 路径的时候，在进行路径重写 `rewrite ^/v1/(.*) /$1 break;` 之后转发到 `https://models.inference.ai.azure.com` - `这个也是 gpt 里的一种模型对接地址`
- 转发后，你就可以直接使用模型服务了。

### 3. curl 验证

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-modes-07.png" width="750px"/>
</div>

- 配置转发后，就可以使用 curl_nginx.sh 脚本进行验证了。

好啦，本文到这，你就可以初步使用免费的 LLM 进行一些一些初始功能验证。2025年，ai agent 开始崛起，2026年，必然是 ai agent 爆发的时候。一定要多学习这类实战项目，企业里 spring 是大盘，所以结合 spring ai 做的项目也必然会非常多。

