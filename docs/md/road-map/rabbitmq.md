---
title: RabbitMQ
lock: need
---

# RabbitMQ 使用教程

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

本文的宗旨在于通过简单干净实践的方式教会读者，使用 Docker 配置 RabbitMQ 在 DDD 结构下验证使用。关于 MQ 的场景内容已经在 [RocketMQ](https://bugstack.cn/md/road-map/rocketmq.html) 一节中做了讲解，本文只要为大家扩展另外一种 MQ 的使用。方便有需要的伙伴可以做技术栈替换。

[RabbitMQ](https://www.rabbitmq.com/) 是一个由 Erlang 开发的 AMQP (Advanced Message Queuing Protocol) 的开源实现。非常轻量，用于部署，有自己提供好的管理后台，非常容易上手使用。在功能上支持订阅、广播、路由和通配符，可以适合各类场景诉求。

本文涉及的工程：
- xfg-dev-tech-rabbitmq：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-rabbitmq](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-rabbitmq)
- RabbitMQ Docker 安装：[docs/dev-ops/docker-compose.yml](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-rabbitmq/-/blob/master/docs/dev-ops/docker-compose.yml)

## 一、环境安装

因为本文是在 [RocketMQ](https://bugstack.cn/md/road-map/rocketmq.html) 一节的扩展，所以只讲解下技术使用即可。

本案例涉及了 RabbitMQ 的使用，都已经在工程中提供了安装脚本，可以按需执行。—— 前置条件已安装 [Docker](https://bugstack.cn/md/road-map/docker.html) 环境。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-rabbitmq-01.png?raw=true" width="750px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-rabbitmq-02.png?raw=true" width="750px">
</div>

- Mac 电脑会比较好安装一些，直接在 IntelliJ IDEA 点击小绿色按钮即可完成安装。安装完成后进入 [http://localhost:9000/#!/2/docker/containers](http://localhost:9000/#!/2/docker/containers) - 可看到 RabbitMQ 运行。
- Windows 电脑安装 Docker 需要折腾下
- Linux 服务器，需要上传整个 dev-ops 后在云服务器执行脚本安装；`docker-compose -f docker-compose.yml up -d`

## 二、配置主题

登录 RabbitMQ 管理后台：[http://127.0.0.1:15672/#/](http://127.0.0.1:15672/#/) - `账密：admin/admin` 

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-rabbitmq-03.png?raw=true" width="750px">
</div>

进入到后台以后，先如图配置个主题消息，后面会使用到这个主题发送和监听消息信息。

## 三、测试案例

### 1. yml 配置

**文件**：`application-dev.yml`

```yml
spring:
  # RabbitMQ 配置
  rabbitmq:
    addresses: 127.0.0.1
    port: 5672
    username: admin
    password: admin
    listener:
      simple:
        prefetch: 1 # 每次投递n个消息，消费完在投递n个
```

- 测试前，需要在工程中添加 RabbitMQ 连接配置信息。
- prefetch 是消息投递的数量，实际场景可以适当配置的大一些。

### 2. 消费配置

进入到 `xfg-dev-tech-trigger` 是监听 MQ 消息的地方。

#### 2.1 普通消息

```java
@Slf4j
@Component
public class Customer {

    /**
     * queuesToDeclare：支持多个队列，将队列绑定到默认交换机上，routeKey为队列名称。
     *
     * @param msg 接收到的消息
     */
    @RabbitListener(queuesToDeclare = @Queue(value = "testQueue"))
    public void listener(String msg) {
        log.info("接收消息：{}", msg);
        // 通过抛异常，验证消息重试
//        throw new RuntimeException("Err");
    }

}
```

- 异常可以随着你的测试开启，开启后会接收到重试的消息。

#### 2.2 广播消息

```java
@Slf4j
@Component
public class FanoutCustomer {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "fanoutCustomer"),
                    exchange = @Exchange(
                            value = "fanoutExchange",
                            type = ExchangeTypes.FANOUT
                    )
            )
    )
    public void listener(String msg) {
        log.info("接收消息【广播模式】：{}", msg);
    }

}
```

- 广播模式，所有的消费放都监听到消息。

#### 2.3 路由消息

```java
@Slf4j
@Component
public class RouteCustomer {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "routeQueue1"),
                    exchange = @Exchange(value = "routeExchange", type = ExchangeTypes.DIRECT),
                    key = "routeKey1"
            )
    )
    public void listener01(String msg) {
        log.info("接收消息【路由模式】：{}", msg);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "routeQueue2"),
                    exchange = @Exchange(value = "routeExchange", type = ExchangeTypes.DIRECT),
                    key = "routeKey2"
            )
    )
    public void listener02(String msg) {
        log.info("接收消息【路由模式】：{}", msg);
    }

}
```

- 路由模式，会根据实际发送消息时候路由选择配置，让指定的消费方接收消息。比如实际场景中有监听订单的消息，但订单有很多种，比如自营、三方以及不同支付渠道，那么可以让不同的监听者只收取自己的消息信息。

#### 2.3 通配符消息

```java
@Slf4j
@Component
public class TopicCustomer {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "topicQueue1"),
                    exchange = @Exchange(value = "topicExchange", type = ExchangeTypes.TOPIC),
                    key = "topic.*" // `*`：匹配一个单词，就只有一个单词
            )
    )
    public void listener01(String msg) {
        log.info("接收消息【通配符模式】listener01：{}", msg);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "topicQueue2"),
                    exchange = @Exchange(value = "topicExchange", type = ExchangeTypes.TOPIC),
                    key = "topic.#" // `#`：匹配一个或多个词
            )
    )
    public void listener02(String msg) {
        log.info("接收消息【通配符模式】listener02：{}", msg);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "topicQueue3"),
                    exchange = @Exchange(value = "topicExchange", type = ExchangeTypes.TOPIC),
                    key = "topic.y.#" // `#`：匹配一个或多个词
            )
    )
    public void listener03(String msg) {
        log.info("接收消息【通配符模式】listener03：{}", msg);
    }

}
```

- 通配符可以起到过滤的作用，比如在实际场景中，你需要根据过往mq的类型，做部分的监听。那么可以根据通配符配置来搞定。

## 四、测试验证

```java
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test_product() throws InterruptedException {
        // 发送消息
        rabbitTemplate.convertAndSend("testQueue", "基本消息");
        // 等待
        new CountDownLatch(1).await();
    }

    @Test
    public void test_product_fanout() throws InterruptedException {
        rabbitTemplate.convertAndSend("fanoutExchange", "", "广播消息");
        // 等待
        new CountDownLatch(1).await();
    }

    @Test
    public void test_product_route() throws InterruptedException {
        rabbitTemplate.convertAndSend("routeExchange", "routeKey1", "路由模式，消息1");
        rabbitTemplate.convertAndSend("routeExchange", "routeKey2", "路由模式，消息2");
        // 等待
        new CountDownLatch(1).await();
    }

    @Test
    public void test_product_topic() throws InterruptedException {
        rabbitTemplate.convertAndSend("topicExchange", "topic.x", "通配符模式，消息1");
        rabbitTemplate.convertAndSend("topicExchange", "topic.y.z", "通配符模式，消息2");
        // 等待
        new CountDownLatch(1).await();
    }

}
```

```java
22:29:46.792 [org.springframework.amqp.rabbit.RabbitListenerEndpointContainer#0-1] INFO  Customer               - 接收消息：基本消息
22:30:40.525 [org.springframework.amqp.rabbit.RabbitListenerEndpointContainer#1-1] INFO  FanoutCustomer         - 接收消息【广播模式】：广播消息
22:31:27.117 [org.springframework.amqp.rabbit.RabbitListenerEndpointContainer#3-1] INFO  RouteCustomer          - 接收消息【路由模式】：路由模式，消息2
22:31:27.117 [org.springframework.amqp.rabbit.RabbitListenerEndpointContainer#2-1] INFO  RouteCustomer          - 接收消息【路由模式】：路由模式，消息1
10:32:08.359 [org.springframework.amqp.rabbit.RabbitListenerEndpointContainer#6-1] INFO  TopicCustomer          - 接收消息【通配符模式】listener03：通配符模式，消息2
10:32:08.359 [org.springframework.amqp.rabbit.RabbitListenerEndpointContainer#4-1] INFO  TopicCustomer          - 接收消息【通配符模式】listener01：通配符模式，消息1
10:32:08.359 [org.springframework.amqp.rabbit.RabbitListenerEndpointContainer#5-1] INFO  TopicCustomer          - 接收消息【通配符模式】listener02：通配符模式，消息1
10:32:08.372 [org.springframework.amqp.rabbit.RabbitListenerEndpointContainer#5-1] INFO  TopicCustomer          - 接收消息【通配符模式】listener02：通配符模式，消息2
```

- 以上案例，分别测试；基本消息、广播消息、路由消息、通配符消息。

