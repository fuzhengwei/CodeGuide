---
title: 给 MCP 服务加上安全认证！
lock: no
---

# 给 MCP 服务加上安全认证！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

刚刚过去两个月，市面的 MCP 服务，如雨后春笋一般不断涌现出来，包括；`百度`、`高德`、`网盘`、`支付宝`。这些 MCP 服务，可以让我们基于 Spring AI 框架构建的 Agent 具备非常丰富的使用功能。同时这也说明，程序员👨🏻‍💻，应该具备开发 MCP 服务的能力，Spring AI 让 Java 再次牛逼！

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-mcp-knowledge-250330-02.gif" width="150px"/>
</div>

>关于 RAG、MCP、Agent 是什么，这里小傅哥已经编写过了全套的教程，可以进入学习；[https://bugstack.cn/md/project/ai-knowledge/ai-knowledge.html](https://bugstack.cn/md/project/ai-knowledge/ai-knowledge.html)

本节小傅哥主要给大家分享，关于市面上这些标准的带有验证权限的 MCP 服务，怎么使用 Spring AI 进行对接。同时我们自己开发的 MCP 服务，怎么加上权限校验。

## 一、举例，对接高德地图 MCP

高德地图 MCP Server；

```java
{
  "mcpServers": {
    "amap-amap-sse": {
      "url": "https://mcp.amap.com/sse?key=您在高德官网上申请的key"
    }
  }
}
```

- 官网：[https://lbs.amap.com/api/mcp-server/gettingstarted](https://lbs.amap.com/api/mcp-server/gettingstarted)  - `官网提供了创建对接 Key`

### 1. 代码使用示例

```java
@Configuration
public class McpConfig {

    @Bean
    public List<NamedClientMcpTransport> mcpClientTransport() {
        McpClientTransport transport = HttpClientSseClientTransport
                .builder("https://mcp.amap.com")
                .sseEndpoint("/sse?key=<your_key>")
                .objectMapper(new ObjectMapper())
                .build();

        return Collections.singletonList(new NamedClientMcpTransport("amap", transport));
    }
    
}
```

- 对接时，需要设定 sseEndpoint 如果不设定个，Spring AI 默认是对 builder 的 baseUrl 值添加 `/sse` 的。
- 所以，如果你要对接外部带有验证权限的 MCP 服务，需要手动设置下 sseEndpoint 值。

### 2. 项目中的配置

小傅哥，带着大家做的 Ai Agent，也支持了外部的这些带有权限校验的 MCP 服务。你可以，以多种方式进行配置。如；

```java
{
	"baseUri":"https://mcp.amap.com",
        "sseEndpoint":"/sse?key=801aabf79ed0ff78603cfe85****"
}
```

```java
{
	"baseUri":"https://mcp.amap.com",
        "sseEndpoint":"/sse?key=801aabf79ed0ff78603cfe85****"
}
```

- 以上两种配置方式，在 ai-agent-station 都做了兼容处理。以下是兼容代码，学习这部分项目的伙伴，可以直接阅读课程代码。

```java
@Slf4j
@Component
public class AiClientToolMcpNode extends AbstractArmorySupport {

	  // ... 省略部分代码

    protected McpSyncClient createMcpSyncClient(AiClientToolMcpVO aiClientToolMcpVO) {
        String transportType = aiClientToolMcpVO.getTransportType();

        switch (transportType) {
            case "sse" -> {
                AiClientToolMcpVO.TransportConfigSse transportConfigSse = aiClientToolMcpVO.getTransportConfigSse();
                // http://127.0.0.1:9999/sse?apikey=DElk89iu8Ehhnbu
                String originalBaseUri = transportConfigSse.getBaseUri();
                String baseUri;
                String sseEndpoint;

                int queryParamStartIndex = originalBaseUri.indexOf("sse");
                if (queryParamStartIndex != -1) {
                    baseUri = originalBaseUri.substring(0, queryParamStartIndex - 1);
                    sseEndpoint = originalBaseUri.substring(queryParamStartIndex - 1);
                } else {
                    baseUri = originalBaseUri;
                    sseEndpoint = transportConfigSse.getSseEndpoint();
                }

                sseEndpoint = StringUtils.isBlank(sseEndpoint) ? "/sse" : sseEndpoint;

                HttpClientSseClientTransport sseClientTransport = HttpClientSseClientTransport
                        .builder(baseUri) // 使用截取后的 baseUri
                        .sseEndpoint(sseEndpoint) // 使用截取或默认的 sseEndpoint
                        .build();

                McpSyncClient mcpSyncClient = McpClient.sync(sseClientTransport).requestTimeout(Duration.ofMinutes(aiClientToolMcpVO.getRequestTimeout())).build();
                var init_sse = mcpSyncClient.initialize();
                log.info("Tool SSE MCP Initialized {}", init_sse);
                return mcpSyncClient;
            }
            case "stdio" -> {
                AiClientToolMcpVO.TransportConfigStdio transportConfigStdio = aiClientToolMcpVO.getTransportConfigStdio();
                Map<String, AiClientToolMcpVO.TransportConfigStdio.Stdio> stdioMap = transportConfigStdio.getStdio();
                AiClientToolMcpVO.TransportConfigStdio.Stdio stdio = stdioMap.get(aiClientToolMcpVO.getMcpName());

                // https://github.com/modelcontextprotocol/servers/tree/main/src/filesystem
                var stdioParams = ServerParameters.builder(stdio.getCommand())
                        .args(stdio.getArgs())
                        .build();
                var mcpClient = McpClient.sync(new StdioClientTransport(stdioParams))
                        .requestTimeout(Duration.ofSeconds(aiClientToolMcpVO.getRequestTimeout())).build();
                var init_stdio = mcpClient.initialize();
                log.info("Tool Stdio MCP Initialized {}", init_stdio);
                return mcpClient;
            }
        }

        throw new RuntimeException("err! transportType " + transportType + " not exist!");
    }

}
```

- 以上代码，是为了自动化构建 MCP 服务的，其中 case sse 的部分，会对 url 进行拆分，如果本身 url 配置了校验权限，则不会从另外一个参数获取，否则从另外参数拼接。这样就可以很好的扩展用户配置时的多样性问题了。

>以上是关于带有权限校验的 MCP 服务配置的问题，接下来，我们要说下怎么自己开发一个带有权限校验

## 二、实现，带有权限校验的 MCP 服务

首先，Spring AI 是有意提供基于自家的 OAuth2 框架，完成 MCP 服务的多样性权限校验的。不过目前提供的方案能用，但不算成熟。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-250524-01.png" width="850px"/>
</div>

官网：[https://spring.io/blog/2025/05/19/spring-ai-mcp-client-oauth2](https://spring.io/blog/2025/05/19/spring-ai-mcp-client-oauth2)

### 1. 基于 OAuth2 认证

#### 1.1 工程结构

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-250524-02.png" width="750px"/>
</div>

- 工程：[https://gitcode.net/KnowledgePlanet/mcp-server-auth](https://gitcode.net/KnowledgePlanet/mcp-server-auth) - `面向于学习 ai-agent-station 的伙伴`
- 使用 OAuth2 基于 Spring MVC 的方式到也简单，知道添加配置即可。

#### 1.2 所需的 POM 文件

```pom
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-authorization-server</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-mcp-server-webmvc</artifactId>
</dependency>
```

#### 1.3 测试验证

```java
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server.shutdown=immediate")
public class ApiTest {

    @LocalServerPort
    private int port;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test_access_token() throws IOException, InterruptedException {
        String token = obtainAccessToken();
        log.info("token:{}", token);
        // eyJraWQiOiJiMWQ0MGIxNi1hOTYzLTQ2NmYtYTVkOC02NGRjMzg0ODljYWEiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJtY3AtY2xpZW50IiwiYXVkIjoibWNwLWNsaWVudCIsIm5iZiI6MTc0ODA1MTc1NiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo1ODA5OCIsImV4cCI6MTc0ODA1MjA1NiwiaWF0IjoxNzQ4MDUxNzU2LCJqdGkiOiI5NjY4ZmZkMi0wNjQ2LTRiNmItODQ4Ni1jYzk3ZjMxNTdmOTEifQ.CG4GYai_NYkmfcqmNi-_HYG06Kan04uNSsC2ivn_eC9Ra6xMKYTs9KIT7k5lKxSFRUOPI7K0zJNVvNXrrIe0iFl-csrG2vGukNTGTPMxtUi2hheBMRbnvjvuojW4DeOEE8UOpdA6uow67ucwcymTlDXE-k7OjRZeyp7UdVz2WyoDFQhLB6ihLbDSj5puAZxfNocirRzo36gmW243aW9f1gugPUcpND-oobc2q8xyBG2cX2AlGXUSS-v9PLjHr2W2smFTKHHGwu7FpMMBnJLUT5gZD0llIg6yqro91nFaAFOpGHXjRZYgVjkRlzxx08Zuquva9PbStxbUl2j8hI43_Q

        var client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/sse"))
                .header("Accept", "text/event-stream")
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

        var responseCode = new AtomicInteger(-1);
        var sseRequest = client.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream()).thenApply(response -> {
            responseCode.set(response.statusCode());
            if (response.statusCode() == 200) {
                log.info("response:{}", JSON.toJSONString(response));
                return response;
            }
            else {
                throw new RuntimeException("Failed to connect to SSE endpoint: " + response.statusCode());
            }
        });

        await().atMost(Duration.ofSeconds(1)).until(sseRequest::isDone);
        assertThat(sseRequest).isCompleted();
        assertThat(responseCode).hasValue(200);
    }

    private String obtainAccessToken() throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();

        var clearTextCredentials = "mcp-client:secret".getBytes(StandardCharsets.UTF_8);
        var credentials = new String(Base64.getUrlEncoder().encode(clearTextCredentials));
        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/oauth2/token"))
                .header("Authorization", "Basic " + credentials)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(ofString("grant_type=client_credentials"))
                .build();

        var rawResponse = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

        Map<String, String> response = objectMapper.readValue(rawResponse, Map.class);
        return response.get("access_token");
    }
    
}
```

- 加上 OAuth2 以后，就需要获取并设置 accessToken 才能访问 sse 服务了。

### 2. 基于网关实现

其实我们到不非得依赖于 Spring OAuth2 往 MCP 服务里在添加一些其他的东西。倒不如直接走网关，让网关来管理权限，MCP 服务只做服务的事情就好。

这里我们基于 Nginx 来配置验证功能，当然你可以在学习本节的案例后，配置任何其他的网关来管理你的 MCP 服务。

注意，这里的前置条件为你已经跟着小傅哥，至少完成了一个 MCP 服务。课程；[https://t.zsxq.com/GwNZp](https://t.zsxq.com/GwNZp)

当我们有了一套基于 sse 形式访问的 mcp 后，我们是可以给这套 mcp 基于 nginx 转发的形式进行访问后面真实的 mcp 服务的。在转发的过程中，拿到用户在地址 `http://127.0.0.1:9999/sse?apikey=DElk89iu8Ehhnbu` mcp 服务后面拼接的 apikey，并对 apikey 进行验证。

#### 2.1 配置工程

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-250524-03.png" width="850px"/>
</div>

- 在 ai-agent-station 项目下，提供了 dev-ops-v2 配置 mcp 服务转发和验证能力。
- 注意，部署的时候，要把 mcp.localhost.conf 转发的 mcp 服务的地址，更换为你的地址。
- 另外，每一个 mcp.localhost.conf 还可以配置域名，这样就达到了高德地图 mcp 访问的效果。举例；`https://fatie.mcp.bugstack.cn/sse/apikey=*******`

#### 2.2 服务转发&校验

```java
# 可以负载服务
upstream backend_servers {
    server 192.168.1.108:8101;
}

server {
    listen 80;

    server_name 192.168.1.104;  # 修改为你的实际服务器 IP 或域名【域名需要备案】

    location /sse {
        # 验证apikey参数，这个apikey也可以对接服务端接口来处理。
        if ($arg_apikey != "DElk89iu8Ehhnbu") {
            return 403; # 如果apikey不正确，返回403禁止访问
        }

        # 重写URL，去掉apikey参数
        rewrite ^(/sse/)\?apikey=.* $1 break;

        proxy_pass http://backend_servers;  # 将请求代理到上游服务器组
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;

        chunked_transfer_encoding off;
        proxy_buffering off;
        proxy_cache off;
        proxy_set_header X-Forwarded-For $remote_addr;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    location /mcp/message {
        proxy_pass http://backend_servers;  # 将请求代理到上游服务器组
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

}
```

- 特别注意，mcp 服务是有2个步骤的，一个是 sse 访问，还有一个 mcp/message 的处理。我们只需要对 sse 的请求进行验证即可。
- `/sse` 请求路径，需要会提取 apikey 与 nginx 配置的值进行对比，如果不正确则会返回一个 403 禁止访问，通过则放行。
- 之后重写 url 地址，让转发到本身 mcp 的地址是干净的。从 `http://127.0.0.1:9999/sse?apikey=DElk89iu8Ehhnbu` 验证转发后为 `http://192.168.1.108:8101/sse`

#### 2.3 功能验证

首先，要确保你的 mcp 服务是可以使用的。如，访问；`http://192.168.1.108:8101/sse` 可以获得到结果。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-agent-station-250524-04.png" width="850px"/>
</div>
- 如图，验证成功。我们可以通过转发的方式进行验证和使用。
- 另外，有了转发和验证，你原本的服务，sse 8101 就不用对外了。只有你的网关（nginx）可以访问即可。这样就可以控制权限了。

#### 2.4 动态验证

那么，目前我们配置的nginx 转发这不是一个固定的权限账号吗，怎么让不同的接入方都申请一个秘钥key来使用呢？这里我们需要使用到 nginx 的 auth 认证模块。

```java
# 可以负载服务
upstream backend_servers {
    server 192.168.1.108:8101;
}

server {
    listen 80;

    server_name 192.168.1.104;  # 修改为你的实际服务器 IP 或域名【域名需要备案】

    location /sse {
        auth_request /auth;

        # 重写URL，去掉apikey参数
        rewrite ^(/sse/)\?apikey=.* $1 break;

        proxy_pass http://backend_servers;  # 将请求代理到上游服务器组
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;

        chunked_transfer_encoding off;
        proxy_buffering off;
        proxy_cache off;
        proxy_set_header X-Forwarded-For $remote_addr;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    location /mcp/message {
        proxy_pass http://backend_servers;  # 将请求代理到上游服务器组
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
  
     location = /auth {
        # 发送子请求到HTTP服务，验证客户端的凭据，返回响应码
        internal;
        # 设置参数
        set $query '';
        if ($request_uri ~* "[^\?]+\?(.*)$") {
            set $query $1;
        }
        # 验证成功，返回200 OK
        proxy_pass http://207.246.123.*:8090/auth/token?$query;
        # 发送原始请求
        proxy_pass_request_body off;
        # 清空 Content-Type
        proxy_set_header Content-Type "";
     }  

}

```

- 在访问 `/sse` 的时候，增加 auth 认证，auth 来访问本地一个 http 接口。你可以是 SpringBoot 实现的接口。这个接口负责验证你的秘钥是否正确。同时你的 SpringBoot 服务还可以提供出一个创建秘钥的平台，让接入方使用。
- 其实类似这样的场景，使用功能更加丰富的 api 网关都是自带的，或者 github 一些专门为 mcp 做网关服务的也都有。

## 三、增强，学习 rag、mcp、agent

小傅哥，已经为你准备好了一套 AI RAG、MCP、Agent 实践编程课程，使用 Java + Spring AI 框架，增强自己的 AI 应用开发能力，迅速囤积编程技能，满足各个公司招聘时对AI应用类开发的要求！如下，课程目录，全程文档小册 + 视频带着你从0到1学习。

#### **第1期 RAG Spring AI 0.8.1 - 完结**

1. 【更】AI RAG 知识库，项目介绍&需求分析&环境说明
2. 【更】初始化知识库工程&部署模型&提交代码
3. 【更】Ollama DeepSeek 流式应答接口实现
4. 【更】Ollama DeepSeek 流式应答页面对接
5. 【更】Ollama RAG 知识库上传、解析和验证
6. 【更】Ollama RAG 知识库接口服务实现
7. 【更】基于AI工具，设计前端UI和接口对接
8. 【更】Git仓库代码库解析到知识库并完善UI对接
9. 【更】扩展OpenAI模型对接，以及完整AI对接
10. 【更】云服务器部署知识库（Docker、Nginx）

#### **第2期 MCP Spring AI 1.0.0 - 完结**

1. 【更】吃上细糠，升级SpringAI框架
2. 【更】康庄大道，上手 AI MCP 工作流
3. 【更】道山学海，实现MCP自动发帖服务（stdio）
4. 【更】海纳百川，上线MCP自动发帖服务
5. 【更】川流不息，实现MCP微信公众号消息通知服务
6. 【更】息息相通，MCP 服务部署上线（sse 模式）

#### 第3期 Agent Spring AI 1.0.0 - 进行中【全套源码和部署已提供】

1. 第3-0节，AiAgent项目介绍和系统演示
2. 第3-1节，Ai Agent 业务流程、系统架构、库表设计说明
3. 第3-2节，初始化工程和库表dao等，提交代码，讲解代码库使用
4. 第3-3节，硬编码方式讲解 Ai Agent 构建，为后续拆分做准备
5. 第3-4节，引入扳手工程，规则模型，整体设计Agent预热装配
6. 第3-5节，规则节点，RootNode 异步加载数据
7. 第3-6节，规则节点，AiClientToolMcpNode 工具MCP服务构建
8. 第3-7节，规则节点，AiClientAdvisorNode 顾问角色服务构建
9. 第3-8节，规则节点，AiClientModelNode 模型构建 bean 对象
10. 第3-9节，规则节点，AiClientNode 客户端构建
11. 第3-10节，Agent 服务预热和对话接口封装，使用验证
12. 第3-11节，知识库接口封装和使用
13. 第3-12节，智能体动态任务构建
14. 第3-13节，Admin 管理端 API 接口讲解（CRUD）
15. 第3-14节，Ai Agent 对话与 UI 页面对接
16. 第3-15节，构建镜像，上线云服务器
17. 第3-16节，Agent 场景玩法分享
18. ... 更多内容，随着课程开始逐步更新。

课程详细介绍：[https://mp.weixin.qq.com/s/j_G32TDfM_l-S76Wo0zPXw](https://mp.weixin.qq.com/s/j_G32TDfM_l-S76Wo0zPXw)

