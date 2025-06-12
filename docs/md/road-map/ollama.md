---
title: Nas + Ollama + DeepSeek
lock: need
---

# 【教程】在Nas上部署Ollama，搭建DeepSeek、配置PageAssist AI、提供API调用

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

在 OpenAI 刚兴起的时候，一个非算法的外行Java，想在个人电脑上部署个 GPT2 都费老鼻子👃🏻劲了。现在 DeepSeek 开源以后，拿这 Ollama 直接就能部署，兼职比程序员👨🏻‍💻安装 Java JDK 都容易。😂Java 二狗，也能过上算法的日子。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ollama-00.gif" width="150px"/>
</div>

**自己部署的 DeepSeek 功能还挺多！**

自己基于 Ollama 部署的一套 DeepSeek，可以提供独属于你自己一套的 AI，并且可以做图片识别、联网、知识库。而且如果你是一个需要使用 DeepSeek 接口做开发的码农，还可以直接使用自己提供的这套 API 做开发。

接下来，小傅哥就带着你使用 Docker 完成 Ollama 的安装和 DeepSeek 模型的部署。Docker 可以在任何环境执行，小傅哥自己是放到 Nas 环境里部署。部署完成后，安装谷歌浏览器插件 Page Assist 使用 Ollama 部署的 DeepSeek 模型。

## 一、部署脚本

小傅哥这里为你提供了执行安装的脚本，以及测试API的方法；

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ollama-01.png" width="850px"/>
</div>

- 代码：[https://github.com/fuzhengwei/xfg-dev-tech-ollama](https://github.com/fuzhengwei/xfg-dev-tech-ollama)
- dev-ops，提供了 docker-compose.yml 部署 ollama 脚本。这个文件你可以放到任何安装了 Docker 的环境里执行。
- src 代码，提供的是测试这套 Ollama 下的大模型。除了你本节部署的 DeepSeek，以后部署其他的也可以这样使用。

## 二、部署安装

### 1. 执行脚本

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ollama-02.png" width="850px"/>
</div>

```java
# docker compose -f docker-compose.yml up -d
version: '3.8'
services:
  ollama:
#    image: ollama/ollama:0.5.10
    image: registry.cn-hangzhou.aliyuncs.com/xfg-studio/ollama:0.5.10
    container_name: ollama
    restart: unless-stopped
    ports:
      - "11434:11434"
```

- 原始镜像 `image: ollama/ollama:0.5.10` 代理镜像 `registry.cn-hangzhou.aliyuncs.com/xfg-studio/ollama:0.5.10` 
- Nas 可以通过界面操作执行启动，如果你是 Linux 服务器，安装了 Docker，可以使用命令执行 `docker compose -f docker-compose.yml up -d`

### 2. 模型说明 - DeepSeek

| 模型             | 内存 | 存储   | 特点                                                 |
| ---------------- | ---- | ------ | ---------------------------------------------------- |
| deepseek-r1:1.5b | 8G   | 12GB   | 轻量级模型，运行速度快，性能有限。                   |
| deepseek-r1:7b   | 16G  | 80GB   | 平衡型模型，性能较好，硬件需求适中。                 |
| deepseek-r1:14b  | 32G  | 200GB  | 高性能模型，擅长复杂任务（数学推理，代码生成）       |
| deepseek-r1:32b  | 64G  | 320GB  | 专业级模型，性能强大，适合高精度任务                 |
| deepseek-r1:70b  | 128G | 500GB+ | 顶级模型，性能最强，适合大规模计算和高复杂任务执行。 |

- 这里小傅哥选择的是 1.5b 模型，你可以按照自己的机器配置进行选择。

### 3. 模型安装 - DeepSeek

你需要进入到 Ollama 管理后台执行安装模型脚本；

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ollama-03.png" width="850px"/>
</div>

```java
# 拉取模型
ollama pull deepseek-r1:1.5b

# 运行模型
ollama run deepseek-r1:1.5b

# 联网模型
ollama pull nomic-embed-text
```

- 命令：`docker exec -it ollama /bin/bash` 也可以进入控制台
- 首先，安装完成后，可以执行运行之后在后台进行对话。对话完成需要关闭的话，运行 Ctrl + D 关闭。
- 之后，安装联网模型。这个过程要持续一会。另外不要一下就选很大的模型，怕你扛不住。

## 三、配置插件

官网：[https://github.com/n4ze3m/page-assist](https://github.com/n4ze3m/page-assist)

### 1. 搜索安装

为了更方便的使用 DeepSeek 模型，这里可以在谷歌浏览器安装一个 Page Assist 插件。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ollama-04.png" width="850px"/>
</div>

- 点击安装 Page Assist 插件

### 2. 链接地址

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ollama-07.png" width="850px"/>
</div>

### 3. 设置中文

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ollama-05.png" width="850px"/>
</div>

### 4. 配置知识库 - RAG

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ollama-06.png" width="850px"/>
</div>

### 5. 添加知识库 - PDF/MD

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ollama-08.png" width="850px"/>
</div>

## 四、对话使用

### 1. ai对话

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ollama-09.png" width="850px"/>
</div>

- 你可以选择模型、联网、图片识别和自己设定的知识库。

### 2. 页面对话

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ollama-10.png" width="850px"/>
</div>

- 你可以在插件上右键，之后就可以与你需要的另外的网页进行对话。理解网页内容做解答。

## 五、API 对接

### 1. curl 接口

```java
curl http://192.168.1.109:11434/api/generate \
  -H "Content-Type: application/json" \
  -d '{
        "model": "deepseek-r1:1.5b",
        "prompt": "1+1",
        "stream": false
      }'

```

- 这是请求 Ollama DeepSeek 模型的 curl 操作。

### 2. 代码请求

#### 2.1 配置接口

```java
@Configuration
public class OllamaConfig {

    @Bean
    public OllamaApi ollamaApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.109:11434")
                .addConverterFactory(JacksonConverterFactory.create()).build();

        return retrofit.create(OllamaApi.class);
    }

    public interface OllamaApi {
        @POST("/api/generate")
        Call<OllamaResponse> generate(@Body OllamaRequest request);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OllamaRequest {
        private String model;
        private String prompt;
        private boolean stream;
    }
    
    // ... 省略部分代码
}    
```

- 这里我们使用 retrofit2 框架封装对模型API的访问。

#### 2.2 访问接口

```java
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OllamaTest {

    @Resource
    private OllamaConfig.OllamaApi api;

    @Test
    public void test_chat() throws IOException {
        OllamaConfig.OllamaRequest request = new OllamaConfig.OllamaRequest(
                "deepseek-r1:1.5b",
                "1+1",
                false
        );

        Call<OllamaConfig.OllamaResponse> generate = api.generate(request);
        Response<OllamaConfig.OllamaResponse> execute = generate.execute();
        OllamaConfig.OllamaResponse response = execute.body();

        log.info("测试结果:{}", JSON.toJSONString(response.getResponse()));
    }

}
```

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ollama-11.png" width="950px"/>
</div>

- 如图，运行结果可以看到调用API没问题啦。
- 另外，SpringAI 也提供了访问 ollama 的 Jar，也可以使用。后续会提供这块的内容。