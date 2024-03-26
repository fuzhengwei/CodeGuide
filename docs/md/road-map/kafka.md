---
title: Kafka
lock: need
---

# 在 DDD 中优雅的发送 Kafka 消息

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

本文的宗旨在于通过简单干净实践的方式教会读者，使用 Docker 部署 Kafka 以及 Kafka 的管理后台，同时基于 DDD 工程使用 Kafka 消息。这里有一个非常重要的点，就是怎么优雅的在 DDD 工程结构下使用 MQ 消息。

在整个《Java简明教程》已经讲解过 [RocketMQ](https://bugstack.cn/md/road-map/rocketmq.html)、[RabbitMQ](https://bugstack.cn/md/road-map/rabbitmq.html) 的使用，本文是对 MQ 系列的一个补充，基本大家在选择使用 MQ 组件时，也就这三类。

本文涉及的工程：
- xfg-dev-tech-kafka：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-kafka](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-kafka)
- Kafka Docker 安装：[docs/dev-ops/docker-compose.yml](#)
- SwitchHost： [https://switchhosts.vercel.app/zh](https://switchhosts.vercel.app/zh) - 下载安装

## 一、环境安装

### 1. host 映射

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-kafka-02.png" width="750px">
</div>

下载 SwitchHost 配置一个映射地址。点击 `+` 添加一个本地环境，之后配置你的 IP kafka 这样就能找这个地址了。IP 为你本地的IP，如果是云服务器就是公网IP地址。

### 2. 安装脚本

本案例涉及了 Kafka 的使用，环境的安装脚本已经放到工程下，可以直接点击安装即可。—— 需要前置条件已安装 [Docker](https://bugstack.cn/md/road-map/docker.html) 环境。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-kafka-01.png" width="750px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-kafka-04.png" width="750px">
</div>

- Mac 电脑会比较好安装一些，直接在 IntelliJ IDEA 点击小绿色按钮即可完成安装。安装完成后进入 [http://localhost:9000/#!/2/docker/containers](http://localhost:9000/#!/2/docker/containers) 可看到 zookeeper、kafka、kafka-eagle 运行啦。
- Windows 电脑安装 Docker 需要折腾下
- Linux 服务器，需要上传整个 dev-ops 后在云服务器执行脚本安装；`docker-compose -f docker-compose.yml up -d`
- 如图29行，有一个 kafka:9092 这个 kafka 是个 host 地址，就是 SwitchHost 打开后配置本地的 ip地址映射 kafka 

### 3. 访问地址

- 地址：[http://127.0.0.1:8048/](http://127.0.0.1:8048/)
- 账密：admin/123456

#### 3.1 首页

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-kafka-05.png" width="750px">
</div>

#### 3.2 大屏

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-kafka-07.png" width="750px">
</div>

#### 3.3 主题

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-kafka-06.png" width="750px">
</div>

- 你可以通过 Create 创建主题消息，填写后点击 Submit 保存。

## 二、消息流程

本节的重点内容在于如何优雅的发送 MQ 消息，让消息聚合到领域层中，并在发送的时候可以不需要让使用方关注过多的细节。【如图】

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-kafka-03.png" width="950px">
</div>

- 在领域层中提供一个 event 包，定义事件消息。也就是一个领域中要发什么消息，就定义什么消息。这个消息只归属于当前领域中。
- 定义的消息则由仓储继承实现【一个领域如果拆分的合理，一般只会有一
- 个事件驱动，也就有一个事件消息】，如果是有多个消息一种是拆分领域，另外一种是提供多个仓储，还有一种是由仓储层注入实现。
- 这里我们先有个影响，之后在到代码部分再看下就会更加清楚是怎么实现的了。

## 三、代码实现

### 1. 工程结构

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-kafka-08.png" width="450px">
</div>

- domain 是领域层，提供一个个领域服务。如果一个工程有多个领域，则有不同的 a、b、c 领域包，每个包下有一套【event、model、repository、service】。
- 在领域层定义的 event 事件，里面涵盖了事件消息。而这个事件消息可以让 UserRepository 继承实现。最终完成消息发送。
- 最后是 trigger 触发器层，所有的 http、rpc、job、mq 都是一种触发行为。通过触发器的 listener 监听，来接收 mq 消息。

### 2. 环境配置

application-dev.yml

```yml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      # 发生错误后，消息重发的次数。
      retries: 1
      #当有多个消息需要被发送到同一个分区时，生产者会把它们放在同一个批次里。该参数指定了一个批次可以使用的内存大小，按照字节数计算。
      batch-size: 16384
      # 设置生产者内存缓冲区的大小。
      buffer-memory: 33554432
      # 键的序列化方式
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      # 值的序列化方式
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
      # acks=0 ： 生产者在成功写入消息之前不会等待任何来自服务器的响应。
      # acks=1 ： 只要集群的首领节点收到消息，生产者就会收到一个来自服务器成功响应。
      # acks=all ：只有当所有参与复制的节点全部收到消息时，生产者才会收到一个来自服务器的成功响应。
      acks: 1
      
...

# 配置主题
kafka:
  topic:
    group: xfg-group
    user: xfg-topic
```

- 完整配置可参考源码。
- 需要注意的配置，`bootstrap-servers: localhost:9092` 
- user: xfg-topic 是发送消息的主题，可以在 kafka 后台创建。
- group: xfg-group 任意即可。

### 2. 代码实现

#### 2.1 配置发送事件

**源码**：`cn.bugstack.xfg.dev.tech.infrastructure.event.EventPublisher`

```java
@Slf4j
@Component
public class EventPublisher {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publish(String topic, BaseEvent.EventMessage<?> eventMessage) {
        try {
            String messageJson = JSON.toJSONString(eventMessage);
            kafkaTemplate.send(topic, messageJson);
            log.info("发送MQ消息 topic:{} message:{}", topic, messageJson);
        } catch (Exception e) {
            log.error("发送MQ消息失败 topic:{} message:{}", topic, JSON.toJSONString(eventMessage), e);
            throw e;
        }
    }

}
```

- 这个是一个启动 kafka 消息的模板。我们把它放到基础层中。

#### 2.2 事件消息定义

**源码**：`cn.bugstack.xfg.dev.tech.domain.event.UserMessageEvent`

```java
public class UserMessageEvent extends BaseEvent<UserMessageEvent.UserMessage> {

    @Value("${kafka.topic.user}")
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

- 首先，BaseEvent 是一个基类，定义了消息中必须的 id、时间、泛型数据。每一个要发送的消息都按照这个结构来发。
- 关于消息的发送，这是一个非常重要的设计手段，事件消息的发送，消息体的定义，聚合到一个类中来实现。可以让代码更加整洁。

#### 2.3 事件消息发送

**源码**：`cn.bugstack.xfg.dev.tech.infrastructure.repository.UserRepository`

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

- 用仓储实现类继承事件消息，在完成数据的操作后，推送消息。

#### 2.4 事件消息监听

**源码**：`cn.bugstack.xfg.dev.tech.trigger.listener.KafkaMessageListener`

```java
@Slf4j
@Component
public class KafkaMessageListener {

    @KafkaListener(topics = "${kafka.topic.user}", groupId = "${kafka.topic.group}", concurrency = "1")
    public void topic_test(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            try {
                // 逻辑处理

                // 确认消息消费完成，如果抛异常消息会进入重试
                ack.acknowledge();
                log.info("Kafka消费成功! Topic:" + topic + ",Message:" + msg);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Kafka消费失败！Topic:" + topic + ",Message:" + msg, e);
            }
        }
    }

}
```

- 在触发器层监听消息，来完成解耦的业务流程。

## 三、测试验证

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
24-03-17.12:28:58.308 [main            ] INFO  EventPublisher         - 发送MQ消息 topic:xfg-topic message:{"data":{"userId":"10001","userName":"小傅哥","userType":"架构师"},"id":"45672247343","timestamp":1710649737803}
24-03-17.12:28:59.811 [main            ] INFO  EventPublisher         - 发送MQ消息 topic:xfg-topic message:{"data":{"userId":"10001","userName":"小傅哥","userType":"架构师"},"id":"18572935390","timestamp":1710649739809}
24-03-17.12:29:01.294 [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO  ConsumerCoordinator    - [Consumer clientId=consumer-xfg-group-1, groupId=xfg-group] Successfully joined group with generation Generation{generationId=1, memberId='consumer-xfg-group-1-f1c1ab73-b72d-4296-809b-d951f88a49dd', protocol='range'}
24-03-17.12:29:01.297 [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO  ConsumerCoordinator    - [Consumer clientId=consumer-xfg-group-1, groupId=xfg-group] Finished assignment for group at generation 1: {consumer-xfg-group-1-f1c1ab73-b72d-4296-809b-d951f88a49dd=Assignment(partitions=[xfg-topic-0])}
24-03-17.12:29:01.314 [main            ] INFO  EventPublisher         - 发送MQ消息 topic:xfg-topic message:{"data":{"userId":"10001","userName":"小傅哥","userType":"架构师"},"id":"15051699480","timestamp":1710649741313}
24-03-17.12:29:01.334 [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO  ConsumerCoordinator    - [Consumer clientId=consumer-xfg-group-1, groupId=xfg-group] Successfully synced group in generation Generation{generationId=1, memberId='consumer-xfg-group-1-f1c1ab73-b72d-4296-809b-d951f88a49dd', protocol='range'}
24-03-17.12:29:01.334 [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO  ConsumerCoordinator    - [Consumer clientId=consumer-xfg-group-1, groupId=xfg-group] Notifying assignor about the new Assignment(partitions=[xfg-topic-0])
24-03-17.12:29:01.341 [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO  ConsumerCoordinator    - [Consumer clientId=consumer-xfg-group-1, groupId=xfg-group] Adding newly assigned partitions: xfg-topic-0
24-03-17.12:29:01.354 [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO  ConsumerCoordinator    - [Consumer clientId=consumer-xfg-group-1, groupId=xfg-group] Found no committed offset for partition xfg-topic-0
24-03-17.12:29:01.380 [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO  SubscriptionState      - [Consumer clientId=consumer-xfg-group-1, groupId=xfg-group] Resetting offset for partition xfg-topic-0 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[kafka:9092 (id: 1 rack: null)], epoch=0}}.
24-03-17.12:29:01.381 [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO  KafkaMessageListenerContainer - xfg-group: partitions assigned: [xfg-topic-0]
24-03-17.12:29:01.631 [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO  KafkaMessageListener   - Kafka消费成功! Topic:xfg-topic,Message:{"data":{"userId":"10001","userName":"小傅哥","userType":"架构师"},"id":"45672247343","timestamp":1710649737803}
24-03-17.12:29:01.642 [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO  KafkaMessageListener   - Kafka消费成功! Topic:xfg-topic,Message:{"data":{"userId":"10001","userName":"小傅哥","userType":"架构师"},"id":"18572935390","timestamp":1710649739809}
24-03-17.12:29:01.647 [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO  KafkaMessageListener   - Kafka消费成功! Topic:xfg-topic,Message:{"data":{"userId":"10001","userName":"小傅哥","userType":"架构师"},"id":"15051699480","timestamp":1710649741313}
```

- 运行测试，可以看到消息的推送和接收。