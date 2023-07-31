---
title: RocketMQ
lock: need
---

# RocketMQ 使用教程和模型结构

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=956583991&bvid=BV1ap4y1G764&cid=1214781475&page=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

本文的宗旨在于通过简单干净实践的方式教会读者，使用 Docker 配置 RocketMQ 并在基于 DDD 分层结构的 SpringBoot 工程中使用 RocketMQ 技术。因为大部分 MQ 的发送都是基于特定业务场景的，所以本章节也是基于 [《MyBatis 使用教程和插件开发》](https://bugstack.cn/md/road-map/mybatis.html) 章节的扩展。

本章也会包括关于 MQ 消息的发送和接收应该处于 DDD 的哪一层的实践讲解和使用。

本文涉及的工程：

- xfg-dev-tech-rocketmq：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-rocketmq](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-rocketmq)
- RocketMQ Docker 安装：[rocketmq-docker-compose-mac-amd-arm.yml](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-rocketmq/-/blob/master/docs/rocketmq/rocketmq-docker-compose-mac-amd-arm.yml)
- 导入测试库表 [road-map.sql](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-rocketmq/-/blob/master/docs/mysql/road-map.sql)

## 一、案例背景

首先我们要知道，MQ 消息的作用是用于；`解耦过长的业务流程`和`应对流量冲击的消峰`。如；用户下单支付完成后，拿到支付消息推动后续的发货流程。也可以是我们基于 [《MyBatis 使用教程和插件开发》](https://bugstack.cn/md/road-map/mybatis.html) 中的案例场景，给雇员提升级别和薪资的时候，也发送一条MQ消息，用于发送邮件通知给用户。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-rocketmq-01.png" width="650px">
</div>

- 从薪资调整到邮件发送，这里是2个业务流程，通过 MQ 消息的方式进行连接。
- 其实MQ消息的使用场景特别多，原来你可能使用多线程的一些操作，现在就扩展为多实例的操作了。发送 MQ 消息出来，让应用的各个实例接收并进行消费。

## 二、领域事件

因为我们本章所讲解的内容是把 RocketMQ 放入 DDD 架构中进行使用，那么也就引申出领域事件定义。所以我们先来了解下，什么是领域事件。

领域事件，可以说是解耦微服务设计的关键。领域事件也是领域模型中非常重要的一部分内容，用于标示当前领域模型中发生的事件行为。一个领域事件会推进业务流程的进一步操作，在实现业务解耦的同时，也推动了整个业务的闭环。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-rocketmq-02.png" width="850px">
</div>

- 首先，我们需要在领域模型层，添加一块 event 区域。它的存在是为了定义出于当前领域下所需的事件消息信息。信息的类型可以是model 下的实体对象、聚合对象。
- 之后，消息的发送是放在基础设置层。本身基础设置层就是依赖倒置于模型层，所以在模型层所定义的 event 对象，可以很方便的在基础设置层使用。而且大部分开发的时候，MQ消息的发送与数据库操作都是关联的，采用的方式是，做完数据落库后，推送MQ消息。所以定义在仓储中实现，会更加得心应手、水到渠成。
- 最后，就是 MQ 的消息，MQ 的消费可以是自身服务所发出的消息，也可以是外部其他微服务的消息。就在小傅哥所整体讲述的这套简明教程中 DDD 部分的触发器层。

## 三、环境安装

本案例涉及了数据库和RocketMQ的使用，都已经在工程中提供了安装脚本，可以按需执行。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-rocketmq-03.png" width="450px">
</div>

这里主要介绍 RocketMQ 的安装；

### 1. 执行 compose yml

**文件**：[docs/rocketmq/rocketmq-docker-compose-mac-amd-arm.yml](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-rocketmq/-/blob/master/docs/rocketmq/rocketmq-docker-compose-mac-amd-arm.yml) - 关于安装小傅哥提供了不同的镜像，包括Mac、Mac M1、Windows 可以按需选择使用。

```yml
version: '3'
services:
  # https://hub.docker.com/r/xuchengen/rocketmq
  # 注意修改项；
  # 01：data/rocketmq/conf/broker.conf 添加 brokerIP1=127.0.0.1
  # 02：data/console/config/application.properties server.port=9009 - 如果8080端口被占用，可以修改或者添加映射端口
  rocketmq:
    image: livinphp/rocketmq:5.1.0
    container_name: rocketmq
    ports:
      - 9009:9009
      - 9876:9876
      - 10909:10909
      - 10911:10911
      - 10912:10912
    volumes:
      - ./data:/home/app/data
    environment:
      TZ: "Asia/Shanghai"
      NAMESRV_ADDR: "rocketmq:9876"
```

- 在 IDEA 中打开 rocketmq-docker-compose-mac-amd-arm.yml 你会看到一个绿色的按钮在左侧侧边栏，点击即可安装。或者你也可以使用命令安装：`# /usr/local/bin/docker-compose -f /docs/dev-ops/environment/environment-docker-compose.yml up -d` - 比较适合在云服务器上执行。
- 首次安装可能使用不了，一个原因是 brokerIP1 未配置IP，另外一个是默认的 8080 端口占用。可以按照如下小傅哥说的方式修改。

### 2. 修改默认配合

1. 打开 `data/rocketmq/conf/broker.conf` 添加一条 `brokerIP1=127.0.0.1` 在结尾

```java
# 集群名称
brokerClusterName = DefaultCluster
# BROKER 名称
brokerName = broker-a
# 0 表示 Master, > 0 表示 Slave
brokerId = 0
# 删除文件时间点，默认凌晨 4 点
deleteWhen = 04
# 文件保留时间，默认 48 小时
fileReservedTime = 48
# BROKER 角色 ASYNC_MASTER为异步主节点，SYNC_MASTER为同步主节点，SLAVE为从节点
brokerRole = ASYNC_MASTER
# 刷新数据到磁盘的方式，ASYNC_FLUSH 刷新
flushDiskType = ASYNC_FLUSH
# 存储路径
storePathRootDir = /home/app/data/rocketmq/store
# IP地址
brokerIP1 = 127.0.0.1
```

2. 打开 ``data/console/config/application.properties` 修改 `server.port=9009` 端口。

```java
server.address=0.0.0.0
server.port=9009
```

- 修改配置后，重启服务。

### 3. RockMQ登录与配置

#### 3.1 登录

RocketMQ 此镜像，会在安装后在控制台打印登录账号信息，你可以查看使用。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-rocketmq-04.png" width="850px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-rocketmq-05.png" width="850px">
</div>

登录：[http://localhost:9009/](http://localhost:9009/)

#### 3.2 创建Topic

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-rocketmq-06.png" width="850px">
</div>

- 也可以使用命令创建：`docker exec -it rocketmq sh /home/app/rocketmq/bin/mqadmin updateTopic -n localhost:9876 -c DefaultCluster -t xfg-mq`

#### 3.3 创建消费者组

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-rocketmq-07.png" width="850px">
</div>

- 也可以使用命令创建：`docker exec -it rocketmq sh /home/app/rocketmq/bin/mqadmin updateSubGroup -n localhost:9876 -c DefaultCluster -g xfg-group`

## 四、工程实现

### 1. 工程结构

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-rocketmq-08.png" width="850px">
</div>

- MQ 的使用无论是 RocketMQ 还是 Kafka 等，都很简单。但在使用之前，要考虑好怎么在架构中合理的使用。如果最初没有定义好这些，那么胡乱的任何地方都能发送和接收MQ，最后的工程将非常难以维护。
- 所以这里整个MQ的生产和消费，是按照整个 DDD 领域事件结构进行设计。分为在 domain 使用基础层生产消息，再有 trigger 层接收消息。

### 2. 配置文件

**引入POM**

```xml
<!-- https://mvnrepository.com/artifact/org.apache.rocketmq/rocketmq-client-java -->
<dependency>
    <groupId>org.apache.rocketmq</groupId>
    <artifactId>rocketmq-client-java</artifactId>
    <version>5.0.4</version>
</dependency>
<dependency>
    <groupId>org.apache.rocketmq</groupId>
    <artifactId>rocketmq-spring-boot-starter</artifactId>
    <version>2.2.0</version>
</dependency>
```

**添加配置**

```yml
# RocketMQ 配置
rocketmq:
  name-server: 127.0.0.1:9876
  consumer:
    group: xfg-group
    # 一次拉取消息最大值，注意是拉取消息的最大值而非消费最大值
    pull-batch-size: 10
  producer:
    # 发送同一类消息的设置为同一个group，保证唯一
    group: xfg-group
    # 发送消息超时时间，默认3000
    sendMessageTimeout: 10000
    # 发送消息失败重试次数，默认2
    retryTimesWhenSendFailed: 2
    # 异步消息重试此处，默认2
    retryTimesWhenSendAsyncFailed: 2
    # 消息最大长度，默认1024 * 1024 * 4(默认4M)
    maxMessageSize: 4096
    # 压缩消息阈值，默认4k(1024 * 4)
    compressMessageBodyThreshold: 4096
    # 是否在内部发送失败时重试另一个broker，默认false
    retryNextServer: false
```

### 3. 定义领域事件

**源码**：`cn.bugstack.xfg.dev.tech.domain.salary.event.SalaryAdjustEvent`

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-rocketmq-09.png" width="450px">
</div>

```java
@EqualsAndHashCode(callSuper = true)
@Data
public class SalaryAdjustEvent extends BaseEvent<AdjustSalaryApplyOrderAggregate> {

    public static String TOPIC = "xfg-mq";

    public static SalaryAdjustEvent create(AdjustSalaryApplyOrderAggregate adjustSalaryApplyOrderAggregate) {
        SalaryAdjustEvent event = new SalaryAdjustEvent();
        event.setId(RandomStringUtils.randomNumeric(11));
        event.setTimestamp(new Date());
        event.setData(adjustSalaryApplyOrderAggregate);
        return event;
    }

}
```

- 每个领域的消息，都有领域自己定义。发送的时候再交给基础设施层来发送。

### 4. 消息发送

**源码**：`cn.bugstack.xfg.dev.tech.infrastructure.event.EventPublisher`

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-rocketmq-10.png" width="450px">
</div>

```java
@Component
@Slf4j
public class EventPublisher {

    @Setter(onMethod_ = @Autowired)
    private RocketMQTemplate rocketmqTemplate;

    /**
     * 普通消息
     *
     * @param topic   主题
     * @param message 消息
     */
    public void publish(String topic, BaseEvent<?> message) {
        try {
            String mqMessage = JSON.toJSONString(message);
            log.info("发送MQ消息 topic:{} message:{}", topic, mqMessage);
            rocketmqTemplate.convertAndSend(topic, mqMessage);
        } catch (Exception e) {
            log.error("发送MQ消息失败 topic:{} message:{}", topic, JSON.toJSONString(message), e);
            // 大部分MQ发送失败后，会需要任务补偿
        }
    }

    /**
     * 延迟消息
     *
     * @param topic          主题
     * @param message        消息
     * @param delayTimeLevel 延迟时长
     */
    public void publishDelivery(String topic, BaseEvent<?> message, int delayTimeLevel) {
        try {
            String mqMessage = JSON.toJSONString(message);
            log.info("发送MQ延迟消息 topic:{} message:{}", topic, mqMessage);
            rocketmqTemplate.syncSend(topic, MessageBuilder.withPayload(message).build(), 1000, delayTimeLevel);
        } catch (Exception e) {
            log.error("发送MQ延迟消息失败 topic:{} message:{}", topic, JSON.toJSONString(message), e);
            // 大部分MQ发送失败后，会需要任务补偿
        }
    }

}
```

- 在基础设施层提供 event 事件的处理，也就是 MQ 消息的发送。


**源码**：`cn.bugstack.xfg.dev.tech.infrastructure.repository.SalaryAdjustRepository`

```java
@Resource
private EventPublisher eventPublisher;
    
@Override
@Transactional(rollbackFor = Exception.class, timeout = 350, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public String adjustSalary(AdjustSalaryApplyOrderAggregate adjustSalaryApplyOrderAggregate) {
		 
		// ... 省略部分代码 

    eventPublisher.publish(SalaryAdjustEvent.TOPIC, SalaryAdjustEvent.create(adjustSalaryApplyOrderAggregate));
    return orderId;
}
```

在 SalaryAdjustRepository 仓储的实现中，做完业务流程开始发送 MQ 消息。这里有2点要注意；
1. 消息发送，不要写在数据库事务中。因为事务一直占用数据库连接，需要快速释放。
2. 对于一些强MQ要求的场景，需要在发送MQ前，写入一条数据库 Task 记录，发送消息后更新 Task 状态为成功。如果长时间未更新数据库状态或者为失败的，则需要由任务补偿进行处理。

### 5. 消费消息

**源码**：`cn.bugstack.xfg.dev.tech.trigger.mq.SalaryAdjustMQListener`

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-rocketmq-11.png" width="450px">
</div>

```java
@Component
@Slf4j
@RocketMQMessageListener(topic = "xfg-mq", consumerGroup = "xfg-group")
public class SalaryAdjustMQListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        log.info("接收到MQ消息 {}", s);
    }

}
```

- 消费消息，配置消费者组合消费的主题，之后就可以接收到消息了。接收以后你可以做自己的业务，如果抛出异常，消息会进行重新接收处理。

## 六、测试验证

### 1. 单独发送消息测试

```java
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RocketMQTest {

    @Setter(onMethod_ = @Autowired)
    private RocketMQTemplate rocketmqTemplate;

    @Test
    public void test() throws InterruptedException {
        while (true) {
            rocketmqTemplate.convertAndSend("xfg-mq", "我是测试消息");
            Thread.sleep(3000);
        }
    }

}
```

- 这里方便你来发送消息，验证流程。

### 2. 业务流程消息验证

```java
@Test
public void test_execSalaryAdjust() throws InterruptedException {
    AdjustSalaryApplyOrderAggregate adjustSalaryApplyOrderAggregate = AdjustSalaryApplyOrderAggregate.builder()
            .employeeNumber("10000001")
            .orderId("100908977676003")
            .employeeEntity(EmployeeEntity.builder().employeeLevel(EmployeePostVO.T3).employeeTitle(EmployeePostVO.T3).build())
            .employeeSalaryAdjustEntity(EmployeeSalaryAdjustEntity.builder()
                    .adjustTotalAmount(new BigDecimal(100))
                    .adjustBaseAmount(new BigDecimal(80))
                    .adjustMeritAmount(new BigDecimal(20)).build())
            .build();
    String orderId = salaryAdjustApplyService.execSalaryAdjust(adjustSalaryApplyOrderAggregate);
    log.info("调薪测试 req: {} res: {}", JSON.toJSONString(adjustSalaryApplyOrderAggregate), orderId);
    Thread.sleep(Integer.MAX_VALUE);
}
```

```java
23-07-29.15:40:52.307 [main            ] INFO  HikariDataSource       - HikariPool-1 - Start completed.
23-07-29.15:40:52.445 [main            ] INFO  EventPublisher         - 发送MQ消息 topic:xfg-mq message:{"data":{"employeeEntity":{"employeeLevel":"T3","employeeTitle":"T3"},"employeeNumber":"10000001","employeeSalaryAdjustEntity":{"adjustBaseAmount":80,"adjustMeritAmount":20,"adjustTotalAmount":100},"orderId":"100908977676004"},"id":"98117654515","timestamp":"2023-07-29 15:40:52.425"}
23-07-29.15:40:52.517 [main            ] INFO  ISalaryAdjustApplyServiceTest - 调薪测试 req: {"employeeEntity":{"employeeLevel":"T3","employeeTitle":"T3"},"employeeNumber":"10000001","employeeSalaryAdjustEntity":{"adjustBaseAmount":80,"adjustMeritAmount":20,"adjustTotalAmount":100},"orderId":"100908977676004"} res: 100908977676004
23-07-29.15:40:52.520 [ConsumeMessageThread_1] INFO  SalaryAdjustMQListener - 接收到MQ消息 {"data":{"employeeEntity":{"employeeLevel":"T3","employeeTitle":"T3"},"employeeNumber":"10000001","employeeSalaryAdjustEntity":{"adjustBaseAmount":80,"adjustMeritAmount":20,"adjustTotalAmount":100},"orderId":"100908977676004"},"id":"98117654515","timestamp":"2023-07-29 15:40:52.425"}
```

- 当执行一次加薪调整后，就会接收到MQ消息了。