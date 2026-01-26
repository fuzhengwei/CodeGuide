---
title: Higress-Ai
lock: need
---

# AI(+MCP)网关，快速集成（LLM API/HTTP/RPC/Nacos）

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

**傻，是不傻？** 有些伙伴问，Dify、Coze，市面都有了，怎么公司里还要基于 Spring AI 框架，开发自己的智能体，主要原因是什么呢？🤔 

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-higress-ai-01.gif" width="150px">
</div>

**何止 Dify，Java JDK 都有公司自己干一套的！为啥呢？**

如果你还纠结这个事，说明你还是个小白，小卡拉米。公司干一套新的，对于企业来说，这东西叫技术资产，人才储备，所有开发的出来东西，既可以申请著作权，又可以申请技术专利。转而，就把这些专利作为企业的注册资金当做投资款抵扣了，也可以用于企业所得税的抵扣。

从公司大目标再往下看，随着 LLM 大模型的发展，以及各类框架的完善，AI 应用场景的智能体开发，会越来越标准化，且越来也有容易被实现。不再需要像最早的一些 Dify、Coze 方案那样笨重。

再者，企业需要沉淀 AI 技术能力（+人才），为公司适应各类 AI 应用场景开发做储备。而直接使用市面的产品，很多时候都没法做到快速迭代新的需求，甚至要在公司发展后期遇到极端问题，甚至还要考虑重新做技术选型以及重建。这个成本就非常大了。

所以，你能理解为啥公司都要做自己的服务平台了吧。而且，公司也非常需要一个像你一样，具备 AI 方面开发知识的研发人员。这也是为什么小傅哥，要带着你做这么多 AI 类的项目。今天分享的这套 AI MCP 网关服务，小傅哥也正在带着大家做。

>接下来，小傅哥就带着大家实践下阿里旗下 Higress AI 网关能力的使用。

## 一、网关介绍

官网：[https://higress.io/](https://higress.io/)
源码：[https://github.com/alibaba/higress](https://github.com/alibaba/higress)
文档：[https://higress.cn/ai/quick-start](https://higress.cn/ai/quick-start)

Higress AI 网关快速集成 LLM API，通过 AI 网关、API 网关及Himarket，解决模型、工具及Agent统一代理问题，并助力企业构建AI中台，加速AI落地。

这套网关服务，可以统一管理你的 LLM API 服务接口，如你访问的 OpenAI、智谱、千问、文心，都可以在Higress AI 网关做一个统一管理。另外，就是 MCP 网关能力，可以把注册的 HTTP 服务、Nacos 服务等，都可以转换为 MCP 接口服务能力，让 AI 客户端使用。

恰好，这个事小傅哥也在带着大家做一套 [《AI MCP Gateway 网关服务系统》](https://bugstack.cn/md/project/ai-mcp-gateway/ai-mcp-gateway.html) 如果想在 AI 开发方面做一些储备积累，那么一定要学习这套项目。

> 本案例会使用 docker 进行部署，如果你不了解 docker 是什么，可以阅读系列教程 [https://bugstack.cn/md/road-map/docker-what.html](https://bugstack.cn/md/road-map/docker-what.html)

## 二、网关使用

### 1. 案例工程

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-higress-ai-02.png" width="750px">
</div>

- 地址：[https://github.com/fuzhengwei/xfg-dev-tech-higress-ai](https://github.com/fuzhengwei/xfg-dev-tech-higress-ai)
- 说明：案例工程，提供了部署 Higress AI 网关脚本（+redis环境），以及包括工程提供了一个测试 http 接口，并配置了 swagger api 文档。网关可以支持从 swagger api json 转换为 MCP 协议结构。
- 环境：如果你是使用云服务器部署 Higress AI 网关，那么可以使用提供好的一键部署脚本安装环境 [https://gitcode.com/Yao__Shun__Yu/xfg-dev-tech-docker-install](https://gitcode.com/Yao__Shun__Yu/xfg-dev-tech-docker-install)

### 2. 接口说明

#### 2.1 swagger api

```java
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency>

<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-api</artifactId>
    <version>2.5.0</version>
</dependency>
```

- 官网：[https://swagger.io/](https://swagger.io/)
- 添加 swagger api pom，项目启动后可以访问；[http://localhost:8091/swagger-ui/index.html](http://localhost:8091/swagger-ui/index.html) - 就可以看到项目的 http 接口信息了。

#### 2.2 接口开发

```java
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/")
@Tag(name = "测试服务", description = "示例接口")
public class TestServiceController {

    private static final Logger logger = LoggerFactory.getLogger(TestServiceController.class);

    /**
     * <a href="http://localhost:8091/swagger-ui/index.html">API文档；swagger</a><br/>
     * <a href="http://127.0.0.1:8091/api/v1/to_upper_case?word=xiaofuge">API测试；http://127.0.0.1:8091/api/v1/to_upper_case?word=xiaofuge</a>
     */
    @GetMapping("to_upper_case")
    @Operation(summary = "字符串转大写", description = "将传入 word 转换为大写")
    public String toUpperCase(@RequestParam String word) {
        logger.info("接收信息:{}", word);
        return word.toUpperCase();
    }

}
```

- 在接口上增加描述信息，这些信息可以用作于 mcp 协议结构中方法和字段上的介绍。如下就是 mcp 接口的 @Tool 工具配置。

```java
@Tool(description = "获取公司雇员信息")
public XxxResponse getCompanyEmployee(XxxRequest01 xxxRequest01, XxxRequest02 xxxRequest02) {
    log.info("根据公司和雇员，查询工资和工作工时。{} {}",xxxRequest01.getCompany(), xxxRequest02.getEmployeeName());
    // 这部分可以实际调用你需要的接口，比如调用http接口获取个数据或者做一些操作等。
    XxxResponse xxxResponse = new XxxResponse();
    xxxResponse.setSalary(new Random().longs(10000).toString());
    xxxResponse.setDayManHour(String.valueOf(new Random().nextInt(24)));
    return xxxResponse;
}
```

- 所以，即使是我们自己开发 AI MCP 网关，也是在找这样的衔接点。怎么把 http 接口，借住什么标准的桥梁，转换为 mcp 协议，让用户使用起来更加简单。而 swagger api 就是中间的标准 open api 桥梁。

### 3. 网关部署

#### 3.1 脚本说明

```java
# 命令执行 docker-compose -f docker-compose-environment-aliyun.yml up -d
# docker 代理和使用文档；https://bugstack.cn/md/road-map/docker.html
version: '3.9'
services:
  # https://github.com/alibaba/higress
  # docker run -d --rm --name higress-ai -v ${PWD}:/data \
  #        -p 8001:8001 -p 8080:8080 -p 8443:8443  \
  #        higress-registry.cn-hangzhou.cr.aliyuncs.com/higress/all-in-one:latest
  higress-ai:
    image: higress-registry.cn-hangzhou.cr.aliyuncs.com/higress/all-in-one:latest
    container_name: higress-ai
    hostname: higress-ai
    ports:
      - 8001:8001
      - 8080:8080
      - 8443:8443
    volumes:
      - ./higress-ai/data:/data
    networks:
      - my-network

  # Redis
  redis:
    image: redis:6.2
    container_name: redis
    restart: always
    hostname: redis
    privileged: true
    ports:
      - 16379:6379
    volumes:
      - ./redis/redis.conf:/usr/local/etc/redis/redis.conf
    command: redis-server /usr/local/etc/redis/redis.conf
    networks:
      - my-network
    healthcheck:
      test: [ "CMD", "redis-cli", "ping" ]
      interval: 10s
      timeout: 5s
      retries: 3

networks:
  my-network:
    driver: bridge
```

- 这里把 higress-ai 通过 docker-compose 脚本进行部署，因为部署后还需要配置 Redis 环境，基于 Redis 的 pub/sub 发布订阅能力，让网关具备分布式部署能力，所以在 docker-compose 脚本里，提供了 higress-ai、redis 的部署。
- 部署；`docker-compose -f docker-compose-environment-aliyun.yml up -d`

#### 3.2 部署网关

##### 3.2.1 部署脚本

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-higress-ai-03.png" width="750px">
</div>

- 如图说明，进行部署。Mac 电脑本地测试即可。如果 Windows 电脑有兼容问题，可以考虑 Linux 云服务器。[https:618.gaga.plus](https:618.gaga.plus)

##### 3.2.2 部署结果

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-higress-ai-04.png" width="950px">
</div>

- 部署后，访问 portainer（这个是 docker 的管理后台）

##### 3.2.3 访问网关

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-higress-ai-05.png" width="950px">
</div>

- 地址：[http://127.0.0.1:8001/](http://127.0.0.1:8001/) 
- 说明：首次登录需要设置下登录账号和密码，设置后登录即可。

### 4. 获取接口

#### 4.1 启动服务（springboot）

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-higress-ai-07.png" width="650px">
</div>

- 测试：[http://127.0.0.1:8091/api/v1/to_upper_case?word=xiaofuge](http://127.0.0.1:8091/api/v1/to_upper_case?word=xiaofuge) - `把小写的xiaofuge转换为大写`
- 接口：[http://localhost:8091/swagger-ui/index.html#/%E6%B5%8B%E8%AF%95%E6%9C%8D%E5%8A%A1/toUpperCase](http://localhost:8091/swagger-ui/index.html#/%E6%B5%8B%E8%AF%95%E6%9C%8D%E5%8A%A1/toUpperCase)

#### 4.2 获取接口

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-higress-ai-08.png" width="650px">
</div>

- 地址：[http://localhost:8091/swagger-ui/index.html](http://localhost:8091/swagger-ui/index.html)
- 说明：启动服务后，访问 swagger 地址，你就可以拿到接口文档了。这里主要需要的就是这份json结构。

### 5. 网关配置

#### 5.1 开启网关（mcp）

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-higress-ai-06.png" width="650px">
</div>

- 注意，在系统设置里，需要开启 mcp 网关服务。enable = true
- 另外，redis 地址，需要配置上。默认本文给的案例，是支持直接使用 redis 别名链接的，如果 redis 不是本案例安装的，则可以用 ip:port 连接。如果有密码，记得填写密码。

#### 5.2 服务来源

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-higress-ai-09.png" width="750px">
</div>

- 可以在网关页，配置服务来源。这里的服务来源支持多种类型，Nacos、Eureka、Zookeeper，以及我们图中这种固定地址的案例，都是可以的。
- 注意下面配置的服务协议，这里选择的是 HTTP，也支持 HTTPS，GRPC 的格式。

#### 5.3 创建服务（MCP）

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-higress-ai-10.png" width="750px">
</div>

在 AI网关管理下，MCP 管理中，添加一个 MCP 服务。这里的用途是建一个 MCP 网关和后端服务建立起连接。

#### 5.4 添加工具（协议转换）

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-higress-ai-11.png" width="750px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-higress-ai-12.png" width="750px">
</div>

- 接下来，还需要添加下工具。也就是 http 接口的具体能力。这里可以把 Swagger 的 json 文件复制过来，添加进去即可。

#### 5.5 验证接口（sse）

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-higress-ai-13.png" width="450px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-higress-ai-14.png" width="450px">
</div>

在添加接口的地方，你会看到一个网关服务的地址。也就是 SSE 接入点，用于配置到 AI 客户端里。访问地址；http://127.0.0.1:8080/mcp-servers/toUpperCase/sse

**好了，到这你的 AI MCP 网关就配置成功了，你可以把各类服务端的接口转换为 MCP 协议结构进行使用。**

### 6. 接口转发（AI）

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-higress-ai-15.png" width="850px">
</div>

AI 网关管理里，还提供了关于 AI 接口的配置。它可以进行统一管理，适合项目的接入使用。做 AI 类的项目，可以把 AI 请求，统一用这样一套东西管理起来。

## 三、调用测试

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-higress-ai-16.png" width="850px">
</div>

```java
13:55:19.581 [main] INFO cn.bugstack.xfg.dev.tech.test.ApiTest -- 测试结果:字符串 "xiaofuge" 转换为大写后是 "XIAOFUGE"。
```

- 调用后可以看到把小写的字符串 `xiaofuge` 转换为大写的 `XIAOFUGE`
- 你也可以进入到 TestServiceController 看到调用的日志。

## 四、深入学习

目前，小傅哥也正在带着粉丝伙伴学习 AI 项目，[《AI MCP 网关》](https://origin.bugstack.cn/md/project/ai-mcp-gateway/ai-mcp-gateway.html)，就是目前在持续更新的一个实战类项目。

该项目是 AI Agent 智能体，关于 MCP 协议对接的通用网关服务项目，以解决各类业务接口便捷转换为 MCP 协议而设计实现。通过这样的配置，可以大大的简化从普通http、rpc接口到 MCP 协议的转换操作。这样的项目，也是每个互联网公司在做 AI Agent 智能体时，必备的基础设施项目。

### 1. 项目架构

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-1-1-03.png" width="650px">
</div>

### 2. 工程模块

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-6-01.png" width="650px">
</div>

### 3. 协议转换

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-5-01.png" width="650px">
</div>

在此项目中，小傅哥一遍带着你分析调试 Spring AI MCP 上下文源码，一边带着你写 AI MCP 网关代码。让你全面的厘清系统设计，又能亲手实现出一套 AI MCP 网关。

