---
title: SpringCloud Bus
lock: need
---

# SpringCloud Bus 消息总线

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

在互联网公司中开发的项目经常有一种场景，是在不重启应用的情况下，变更应用中某个属性信息的值。比如，我们为系统新增加允许外部调用接入的SC渠道值，测试阶段验证名单PIN、持续发布上线后的切量。这些东西都是不重启应用的情况下，动态做配置变更，那这样的东西在 SpringCloud 有什么现成的组件可以使用呢？

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-springcloud-feign-01.gif" width="200px">
</div>

**在大厂这个组件叫什么？**

这个东西它不同于 Redis，而是把配置写到本地类对应的属性上。而不是像 Redis 那样从一个统一的地方每次去取使用。在大厂中我们管这个组件叫统一配置中，专门应对分布式工程中类对应属性的值的控制。

在小傅哥的大营销项目中，也带着大家实现过这样一款组件，叫[DCC](https://bugstack.cn/md/project/big-market/api/%E7%AC%AC29%E8%8A%82%EF%BC%9A%E5%88%86%E5%B8%83%E5%BC%8F%E5%8A%A8%E6%80%81%E9%85%8D%E7%BD%AE%E6%B4%BB%E5%8A%A8%E9%99%8D%E7%BA%A7.html)，基于 Zookeeper + AOP 切面实现。

那么我们本节来看看 SpringCloud 是如何来处理这样一个场景的。

## 一、组件介绍

Spring Cloud Bus 将分布式系统的节点与轻量级消息代理相链接。然后可以使用它来广播状态更改（例如配置更改）或其他管理指令。该项目包含 AMQP 和 Kafka 代理实现。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-springcloud-bus-01.png" width="750px">
</div>

在微服务架构中，通常使用轻量级的消息代理来创建一个共享的消息主题，让所有微服务实例都可以连接到这个主题上。因为这个主题中的消息会被所有实例监听和消费，因此通常称之为“消息总线”。连接到总线的每个实例都可以轻松地广播消息，以便所有其他连接该主题的实例能够接收到这些信息。

## 二、测试工程

小傅哥这里搭建了一套用于测试验证 SpringCloud Bus 消息总线的服务。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-springcloud-bus-02.png" width="750px">
</div>
**工程**：[https://github.com/fuzhengwei/xfg-dev-tech-springcloud-bus](https://github.com/fuzhengwei/xfg-dev-tech-springcloud-bus) - `你可以把工程fork到你的github仓库，之后做后面的操作。`

- 环境要求；jdk 1.8、maven 3.8.x、kafka - 提供了 docker 安装脚本在 docs 下。之后还有一个 [natapp](https://natapp.cn/) 做内网穿透。
- 模块职责；config-bus 配置了整套消息总线所需的服务模块，一个是 eureka 的 registry 注册中心，一个是 SpringCloud Bus 消息总线的服务 server。kafka 是通用的模块，便于统一配置。xfg-dev-tech-app 是测试工程模块。

## 三、环境安装

本节的案例工程会需要用到 Kafka、RabbitMQ，所以需要安装这两套环境。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-springcloud-bus-03.png" width="750px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-rabbitmq-02.png" width="750px">
</div>

- Mac 电脑会比较好安装一些，直接在 IntelliJ IDEA 点击小绿色按钮即可完成安装。安装完成后进入 [http://localhost:9000/#!/2/docker/containers](http://localhost:9000/#!/2/docker/containers)- 可看到 Kafka、RabbitMQ 运行。
- Windows 需要开启 wsl2 在安装 Docker 之后就可以安装 docker 使用了。
- 如果本机电脑配合低或者比较旧不好安装，推荐使用云服务器进行操作。云服务器就相当于你的一个远程电脑了，非常适合部署这些环境，同时怎么这套都不会影响你的本地环境。[https://618.gaga.plus - 推荐2c4g云服务。

## 四、功能实现

### 1. config-bus-kafka

```java
@Configuration
@PropertySource("classpath:system.properties")
public class KafkaConfig {
}
```

```java
spring.kafka.bootstrap-servers=127.0.0.1:9092

spring.kafka.producer.retries=0
spring.kafka.producer.batch-size=16384
spring.kafka.producer.buffer-memory=33554432
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer

spring.kafka.consumer.group-id=springcloud-config-bus-group
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.enable-auto-commit=false
spring.kafka.consumer.auto-commit-interval=100
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
```

- 做一个统一的  kafka 配置 model，让其他模块引入。

### 2. config-bus-registry

```java
package cn.bugstack.xfg.dev.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ConfigBusRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run( ConfigBusRegistryApplication.class, args );
    }

}
```

```java
server:
  port: 7397

spring:
  application:
    name: eureka-server

eureka:
  instance:
    # 使用 ip 代替实例名
    prefer-ip-address: true
    # 实例的主机名
    hostname: ${spring.cloud.client.ip-address}
    # 实例的 ID 规则
    instance-id: ${spring.cloud.client.ip-address}:${spring.application.name}:${server.port}
  client:
    # 是否向注册中心注册自己
    registerWithEureka: false
    # 是否向注册中心获取注册信息
    fetchRegistry: false
    serviceUrl:
      # 注册中心地址
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

- 这部分是一个 eureka 的服务端，让注册中心和客户端，都被 eureka 管理。

### 3. config-bus-server

```java
package cn.bugstack.xfg.dev.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class ConfigBusServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigBusServerApplication.class, args);
    }

}
```

```java
# 端口
server:
  port: 8000

spring:
  application:
    name: config-bus-server
  cloud:
    config:
      server:
        git:
          # 仓库地址
          uri: https://github.com/fuzhengwei/xfg-dev-tech-springcloud-bus
          # 对应 {label} 部分，即 Git 的分支
          label: master
          # 仓库文件夹名称，多个以逗号分隔
          search-paths: config-bus/config-repo
          # git 仓库用户名（公开库可以不用填写）
          username:
          # git 仓库密码（公开库可以不用填写）
          password:
    bus:
      # 开启消息跟踪
      enabled: true
      trace:
        enabled: true
  kafka:
    consumer:
      group-id: config-bus-server-group

eureka:
  instance:
    prefer-ip-address: true
    hostname: ${spring.cloud.client.ip-address}
    instance-id: ${spring.cloud.client.ip-address}:${spring.application.name}:${server.port}
  client:
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:7397/eureka/

management:
  endpoints:
    web:
      exposure:
        # 开启刷新端点
        include: bus-refresh
```

- git 部分的配置，如注释说明。之后你要修改为自己的 Github 地址，这样你在修改配置时候，才能做 webhook 调用变更。
- kafka 是默认的消费id，不需要修改。
- management 需要开启 bus-refresh 刷新断点。

### 4. config-repo

system-dev.properties

```java
hello=I'm xfg dev config 09
hi=I'm xfg dev config 08
```

- 这一层是配置文件，后面在你提交代码修改的时候，工程里也会一起修改。

### 5. xfg-dev-tech-app

#### 5.1 动态配置

```java
@RestController
@RefreshScope
public class ConfigClientController {

    @Value("${hello}")
    private String hi;

    @RequestMapping("/hi")
    public String hi() {
        return this.hi;
    }

}
```

验证时访问地址；[http://127.0.0.1:9000/hi](http://127.0.0.1:9000/hi)

#### 5.2 刷新配置

```java
package cn.bugstack.xfg.dev.tech.trigger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@RestController
public class GitHubWebhookController {

    @PostMapping("/webhook")
    public String handleGitWebhook(@RequestBody String payload) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode content = mapper.readTree(payload);

            log.info("收到 webhook {} 更新配置通知", content.get("pusher"));

            // 创建URL对象
            URL url = new URL("http://127.0.0.1:8000/actuator/bus-refresh");

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为POST
            connection.setRequestMethod("POST");

            // 开启输入输出流
            connection.setDoOutput(true);

            // 设置请求头，如果需要，可以设置Content-Type等
            connection.setRequestProperty("Content-Type", "application/json");

            // 获取输出流
            try (OutputStream os = connection.getOutputStream()) {
                // 如果有请求体数据，也可以在这里写入
                // String jsonInputString = "{\"key\": \"value\"}";
                // os.write(jsonInputString.getBytes("utf-8"));
                os.flush();
            }

            // 发送请求并获取响应码
            int responseCode = connection.getResponseCode();

            log.info("调用 actuator/bus-refresh 更新全局配置完成 code:{}", responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "done";
    }

}
```

- 做一个 webhook 接口，github 回调后，调用 `http://127.0.0.1:8000/actuator/bus-refresh` 刷新配置。
- 也可以手动访问 `http://127.0.0.1:8000/actuator/bus-refresh` 自己刷新配置验证。

## 五、功能验证

### 1. 前置配置

#### 1.1 内网穿透

获取 natapp 免费隧道 authtoken，[https://natapp.cn/tunnel/lists](https://natapp.cn/tunnel/lists) 配置到工程中。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-springcloud-bus-04.png" width="750px">
</div>

- 注意：免费隧道配置端口为9000，因为是要把本地这个 9000 端口的服务，映射出去。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-springcloud-bus-05.png" width="550px">
</div>

- 启动 natapp 后，会得到一个公网域名地址。这个地址免费的会不断地变化，测试的时候注意。

#### 1.2 webhook 配置

进入 GitHub 工程中，Settings -> Webhooks 页面。地址：[https://github.com/fuzhengwei/xfg-dev-tech-springcloud-bus/settings/hooks/517530722](https://github.com/fuzhengwei/xfg-dev-tech-springcloud-bus/settings/hooks/517530722) - `你的和我的不同`

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-springcloud-bus-06.png" width="750px">
</div>

- 拿到公网地址后，配置 webhook。如图配置完点击下面完成。更新的时候点击 update webhook。

### 2. 启动服务

陆续的启动；config-bus-registry、config-bus-server、xfg-dev-tech-app。

### 3. 服务测试

#### 3.1 第1次，访问配置接口

地址：[http://127.0.0.1:9000/hi](http://127.0.0.1:9000/hi)

```java
I'm xfg dev config 09
```

#### 3.2 更新线上配置

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-springcloud-bus-07.png" width="750px">
</div>

- 你可以在线更新配置，也可以本地更新配置后提交代码到 github。
- 变更后点击 commit changes

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-springcloud-bus-08.png" width="850px">
</div>

- 查看到 webhook 推送的记录。是成功的。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-springcloud-bus-09.png" width="850px">
</div>

- 查看日志变更记录。`webhook {"name":"fuzhengwei","email":"fuzhengwei@users.noreply.github.com"} 更新配置通知`

#### 3.2 第2次，访问配置接口

地址：[http://127.0.0.1:9000/hi](http://127.0.0.1:9000/hi)

```java
I'm xfg dev config 10
```

- 配置已经从09变更为10，代表测试成功了。
- 另外你还可以访问 [http://127.0.0.1:8000/system/dev](http://127.0.0.1:8000/system/dev) 查看整体的配置信息。

```java
{
    "name": "system",
    "profiles": [
        "dev"
    ],
    "label": null,
    "version": "fccaf3233af6d0ae16571d2c907ff87eaf1c8946",
    "state": null,
    "propertySources": [
        {
            "name": "https://github.com/fuzhengwei/xfg-dev-tech-springcloud-bus/config-bus/config-repo/system-dev.properties",
            "source": {
                "hello": "I'm xfg dev config 10",
                "hi": "I'm xfg dev config 08"
            }
        }
    ]
}
```





