---
title: SpringCloud Stream
lock: need
---

# SpringCloud Stream

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

在我们日常开发中，有很多的同类共性功能组件，如；MQ 的有 Kafka、RabbitMQ，RPC 的有 GRpc、Dubbo。那如果我们想让服务可以平滑的从一套组件切换到另外一套，应该如何处理呢？🤔

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-springcloud-feign-01.gif" width="200px">
</div>

**这样的东西我也做过**

在我工作的公司，近10年的发展中，Redis 的缓存服务组件陆续的变换了3、4款，目前有2套最终稳定共用的。那么我为此开发了一款缓存中间件，可以做到动态切换、读写控制、监控管理，可以非常方便的迁移和升级。

那么，在我们使用 MQ 的时候，如果在不改变系统工程代码的情况下，该怎样优雅的从一套MQ迁移到另外一套呢？今天小傅哥就带着大家来办这样一个事。

## 一、组件介绍

官网：[https://spring.io/projects/spring-cloud-stream](https://spring.io/projects/spring-cloud-stream)

Spring Cloud Stream 是一个用于构建与共享消息系统连接的高度可扩展的事件驱动微服务的框架。

该框架提供了一个灵活的编程模型，该模型建立在已建立且熟悉的 Spring 习语和最佳实践之上，包括对持久发布/订阅语义、消费者组和有状态分区的支持。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-springcloud-stream-01.png" width="450px">
</div>

Spring Cloud Stream 支持对接的 MQ 包括：RabbitMQ、Kafka、RocketMQ、Azure Service Bus 等。

## 二、测试工程

小傅哥这里搭建了一套测试 MQ 案例的六边形架构；

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-springcloud-stream-02.png" width="650px">
</div>

**工程**：[https://github.com/fuzhengwei/xfg-dev-tech-springcloud-stream](https://github.com/fuzhengwei/xfg-dev-tech-springcloud-stream)

- docs 提供了使用 docker 安装 kafka、rabbitmq 的环境脚本。docker 安装和使用教程：[https://bugstack.cn/md/road-map/docker.html](https://bugstack.cn/md/road-map/docker.html)
- trigger 是六边形架构的触发器层，用于接收 MQ 消息。接收后就可以通过调用 domain 领域服务，完成功能的串联。另外 trigger 作为触发器，不只是可以接收 MQ 消息，还可以提供HTTP接口、RPC接口，Job任务调度。
- domain 领域层是具体的业务逻辑实现层，当业务逻辑中有需要发MQ消息的时候，则可以通过 infrastructure 基础设施层通过依赖倒置实现 domain 领域层 adapter 适配器中的接口，完成消息的发送。

## 三、环境安装

本节的案例工程会需要用到 Kafka、RabbitMQ，所以需要安装这两套环境。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-springcloud-stream-03.png" width="650px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-rabbitmq-02.png" width="650px">
</div>

- Mac 电脑会比较好安装一些，直接在 IntelliJ IDEA 点击小绿色按钮即可完成安装。安装完成后进入 [http://localhost:9000/#!/2/docker/containers](http://localhost:9000/#!/2/docker/containers)- 可看到 Kafka、RabbitMQ 运行。
- Windows 需要开启 wsl2 在安装 Docker 之后就可以安装 docker 使用了。
- 如果本机电脑配合低或者比较旧不好安装，推荐使用云服务器进行操作。云服务器就相当于你的一个远程电脑了，非常适合部署这些环境，同时怎么这套都不会影响你的本地环境。[https://618.gaga.plus](https://618.gaga.plus) - 推荐2c4g云服务。

## 四、功能验证

在做项目的案例前，我们可以先做下 SpringCloud Stream 对接 Kafka、RabbitMQ 的案例，有了这个基础在做整个工程的案例就更容易了。

### 1. pom 配置

```java
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-dependencies</artifactId>
    <version>Finchley.SR2</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
<!-- kafka -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream-binder-kafka</artifactId>
</dependency>
<!-- rabbit -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream-binder-rabbit</artifactId>
</dependency>
```

- 首先在 [spring-cloud-stream](https://spring.io/projects/spring-cloud-stream) 组件官网中是有一套对应的 SpringBoot、SpringCloud 版本匹配的关系的。如果你需要升级版本可以参考官网中的关系。
- 这里的 kafka、rabbitmq，使用的时候要分别使用，把另外一套不使用的注释掉就可以。
- 这里我们注释掉 rabbit 的引入，只测试 kafka 部分。

### 2. yml 配置

```java
spring:
#  rabbitmq:
#    addresses: 192.168.1.108
#    port: 5672
#    username: admin
#    password: admin
#    listener:
#      simple:
#        prefetch: 10 # 每次投递n个消息，消费完在投递n个
  kafka:
    bootstrap-servers: 192.168.1.105:9092
    producer:
      # 发生错误后，消息重发的次数。
      retries: 1
      #当有多个消息需要被发送到同一个分区时，生产者会把它们放在同一个批次里。该参数指定了一个批次可以使用的内存大小，按照字节数计算。
      batch-size: 16384
      # 设置生产者内存缓冲区的大小。
      buffer-memory: 33554432
      acks: 1
    consumer:
      # 自动提交的时间间隔 在spring boot 2.X 版本中这里采用的是值的类型为Duration 需要符合特定的格式，如1S,1M,2H,5D
      auto-commit-interval: 1S
      # 该属性指定了消费者在读取一个没有偏移量的分区或者偏移量无效的情况下该作何处理：
      # latest（默认值）在偏移量无效的情况下，消费者将从最新的记录开始读取数据（在消费者启动之后生成的记录）
      # earliest ：在偏移量无效的情况下，消费者将从起始位置读取分区的记录
      auto-offset-reset: earliest
      # 是否自动提交偏移量，默认值是true,为了避免出现重复数据和数据丢失，可以把它设置为false,然后手动提交偏移量
      enable-auto-commit: false
  cloud:
    stream:
      bindings:
        output:
          destination: ${mq.topic.user}
        input:
          destination: ${mq.topic.user}
        myoutput:
          destination: ${mq.topic.user02}
        myinput:
          destination: ${mq.topic.user02}

mq:
  topic:
    user: xfg-topic
    user02: xfg-topic-02
```

- SpringCloud Stream，支持你以统一一套的方式配置不同的 MQ 渠道。需要使用 kafka、rabbitmq，就可以分别配置。
- 之后你可以看到，cloud.stream.bindings 可以指定 input、output，这个既可以使用本身 SpringCloud Stream 提供的，也可以自定义。通过这样的一个方式，让所有的  MQ 都以这样的方式进行输入、输出对接。
- 这里我们注释掉 rabbitmq 只测试 kafka

### 3. 案例代码

#### 3.1 自定义输入输出key

```java
public interface MyProcessor {

    String INPUT = "myinput";
    String OUTPUT = "myoutput";

    @Input(INPUT)
    SubscribableChannel input();

    @Output(OUTPUT)
    MessageChannel output();
}
```

#### 3.2 单测类

##### 3.2.1 默认方式

```java
Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamTest01 {

    @Autowired
    private MessageProducer producer;

    @Test
    public void test_publish() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            producer.send("mq 消息，哈喽哇！");
        }

        new CountDownLatch(1).await();
    }

    @Component
    @EnableBinding(Source.class)
    static class MessageProducer {

        @Autowired
        private Source source;

        public void send(String message) {
            source.output().send(MessageBuilder.withPayload(message).build());
        }

    }

    @Component
    @EnableBinding({Sink.class})
    static class MessageConsumer {

        @StreamListener(Sink.INPUT)
        public void onMessage(String message) {
            System.out.println("@测试 -> " + message);
        }

    }

}
```

```java
@测试 -> mq 消息，哈喽哇！
@测试 -> mq 消息，哈喽哇！
@测试 -> mq 消息，哈喽哇！
@测试 -> mq 消息，哈喽哇！
```

- 使用组件中提供的 Source、Sink 带有的 input、output 方式处理消息。

##### 3.2.2 自定义方式

```java
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamTest02 {

    @Autowired
    private MessageProducer producer;

    @Test
    public void test_publish() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            producer.send("mq 消息，哈喽哇！");
        }

        new CountDownLatch(1).await();
    }

    @Component
    @EnableBinding(MyProcessor.class)
    static class MessageProducer {

        @Autowired
        private MyProcessor source;

        public void send(String message) {
            source.output().send(MessageBuilder.withPayload(message).build());
        }

    }

    @Component
    @EnableBinding({MyProcessor.class})
    static class MessageConsumer {
        @StreamListener(MyProcessor.INPUT)
        public void onMessage(String message) {
            System.out.println("@测试 -> " + message);
        }

    }

}
```

```java
@测试 -> mq 消息，哈喽哇！
@测试 -> mq 消息，哈喽哇！
@测试 -> mq 消息，哈喽哇！
@测试 -> mq 消息，哈喽哇！
```

- 自定义了 MyProcessor 可以自定设定 input、output 的名称。我这里设定的是 myinput、myoutput

## 五、工程案例 - 六边形架构

### 1. 消息事件定义

```java
public class UserMessageEvent extends BaseEvent<UserMessageEvent.UserMessage> {

    @Value("${mq.topic.user}")
    private String topic;

    @Override
    public EventMessage<UserMessage> buildEventMessage(UserMessage data) {
        return EventMessage.<UserMessage>builder()
                .id(RandomStringUtils.randomNumeric(11))
                .timestamp(new Date())
                .data(data)
                .build();
    }

    @Override
    public String topic() {
        return topic;
    }

    /**
     * 要推送的事件消息，聚合到当前类下。
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserMessage {
        private String userId;
        private String userName;
        private String userType;
    }

}
```

- 在 domain 领域层，adapter 适配器中定义消息事件。这也就是一个规范，当有基础层需要发送消息的时候，则需要通过定义的消息结构来处理。也就是我们在领域层定义标准，之后由基础设施层完成处理。

### 2. 基础层添加发送工具

```java
@Slf4j
@Component
@EnableBinding(Source.class)
public class EventPublisher {

    @Autowired
    @Qualifier(Source.OUTPUT)
    private MessageChannel messageChannel;

    @Autowired
    private Source source;

    public void publish(String topic, BaseEvent.EventMessage<?> eventMessage) {
        try {
            String messageJson = JSON.toJSONString(eventMessage);
            source.output().send(MessageBuilder.withPayload(messageJson).build());
            log.info("发送MQ消息 topic:{} message:{}", topic, messageJson);
        } catch (Exception e) {
            log.error("发送MQ消息失败 topic:{} message:{}", topic, JSON.toJSONString(eventMessage), e);
            throw e;
        }
    }

}
```

- 工程中所有的消息发送方式都是统一一套的，所以我们在基础层定义出发送消息的方法。各个模块需要使用的时候，直接调用就可以了。

### 3. 发送事件消息

```java
@Service
public class UserRepository extends UserMessageEvent implements IUserRepository {

    @Resource
    private EventPublisher publisher;

    @Override
    public void doSaveUser(UserEntity userEntity) {
        // 推送消息
        publisher.publish(this.topic(), this.buildEventMessage(UserMessageEvent.UserMessage.builder()
                .userId(userEntity.getUserId())
                .userName(userEntity.getUserName())
                .userType(userEntity.getUserTypeVO().getDesc())
                .build()));
    }

}
```

- 在基础设施层实现了领域层定义的方法后，就可以在完成业务领域服务功能后，开始推送消息了。

### 4. 监听消费

```java
@Slf4j
@Component
@EnableBinding({Sink.class})
public class MessageListener {

    @StreamListener(Sink.INPUT)
    public void onMessage(String message) {
        log.info("接收消息:{}", message);
    }

}
```

- 监听消息，配置一个 INPUT，这样就可以接收到消息了。

### 5. 测试验证

```java
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Resource
    private IUserService userService;

    @Test
    public void test_register() throws InterruptedException {
        while (true) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId("10001");
            userEntity.setUserName("小傅哥");
            userEntity.setUserTypeVO(UserTypeVO.T8);

            userService.register(userEntity);
            Thread.sleep(1500);
        }

    }

}
```

```java
24-12-01.13:33:53.003 [main            ] INFO  AppInfoParser          - Kafka version : 1.0.2
24-12-01.13:33:53.003 [main            ] INFO  AppInfoParser          - Kafka commitId : 2a121f7b1d402825
24-12-01.13:33:53.043 [main            ] INFO  EventPublisher         - 发送MQ消息 topic:xfg-topic message:{"data":{"userId":"10001","userName":"小傅哥","userType":"架构师"},"id":"89743057693","timestamp":1733031232650}
24-12-01.13:33:54.549 [main            ] INFO  EventPublisher         - 发送MQ消息 topic:xfg-topic message:{"data":{"userId":"10001","userName":"小傅哥","userType":"架构师"},"id":"80224746522","timestamp":1733031234546}
```

- 模拟测试持续发送消息。发送后就可以在监听消息的 MessageListener 收到具体的消息数据。

