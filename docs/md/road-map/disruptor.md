---
title: Disruptor
lock: need
---

# Disruptor 高性能环形消息队列应用，Log4j 2 也用到了这套技术。

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

说到底，无论是晋升述职还是面试考察，编程技能的展现总是在那些技术的横向对比和深度的了解运用。知其一，也知其二。一个场景的问题，往往也会对应着多种的解决方案，从没有绝对的好和不好，都是是否适合而已。所以，往往技术越好的，也越低调，不那么咋咋呼呼的。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-disruptor-01.gif" width="200px"/>
</div>

**什么是柔性事务？**

在分布式软件系统架构设计中，所有的并发资源的竞争，都会往`无锁化`、`非独占竞争`，以及`柔性事务`设计。柔性事务用于替代传统事务管理中（如ACID属性：原子性、一致性、隔离性、持久性），在分布式架构系统中的使用场景。通过消息、补偿，协调不同服务间的一致性。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-disruptor-02.png" width="550px"/>
</div>

那么在消息的使用中，除了有 MQ 消息，使用于微服务之间。还有本地消息，可以作用在各个领域间驱动流程。关于本地消息可以用，Spring 的监听、Redis 发布订阅、Guava EventBus 事件总线，这些内容在小傅哥博客 [bugstack.cn](https://bugstack.cn) 《路书》中有相关的案例。之后本节咱们介绍一个新的高性能组件 Disruptor 的使用。

## 一、关于 Disruptor

Disruptor 是一种高性能的并发框架，最初由 LMAX 开发，用于解决高吞吐量、低延迟的消息处理问题。它提供了一种无锁的、有序的事件处理模型，非常适合处理需要高性能的场景。Disruptor 本身并不是用于实现事务的框架，而是一个事件处理器。因此，要在 Disruptor 上实现柔性事务，需要结合其事件处理能力与柔性事务的模式。

- 源码：[https://github.com/LMAX-Exchange/disruptor](https://github.com/LMAX-Exchange/disruptor)
- 文档：[https://lmax-exchange.github.io/disruptor/](https://lmax-exchange.github.io/disruptor/) - 谷歌浏览器右键点翻译为中文。

## 二、实战案例

### 1. 工程结构

小傅哥准备好了一份基于 Disruptor 事件消息的使用案例工程，你可以直接上手体现。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-disruptor-03.png" width="400px"/>
</div>

- app 是使用的启动层、trigger 是提供接口、监听消息、处理任务的触发器层。
- 在这里我们通过 trigger 下的 event 包，监听事件消息。之后把这个 XxxEventHandler 让 app 层下的 Disruptor 进行实例化。

### 2. 引入POM

```pom
<!-- https://mvnrepository.com/artifact/com.lmax/disruptor -->
<dependency>
    <groupId>com.lmax</groupId>
    <artifactId>disruptor</artifactId>
    <version>3.4.4</version>
</dependency>
```

- 引入 disruptor pom 包。

### 3. 监听消息

```java
@Slf4j
public class XxxEventHandler implements EventHandler<XxxEventHandler.Message> {

    @Override
    public void onEvent(Message longEvent, long l, boolean b) throws Exception {
        log.info("接收消息：{}", longEvent.getValue());
    }

    @Data
    public static class Message {
        private String value;
    }

}
```

- 在 trigger 下 event 包内，加一个实现了 disruptor EventHandler 的监听实现类，消息体类型我们定义到 XxxEventHandler 中，也就是 Message。具体生产使用的时候，按需调整。
- 这个接收消息的过程和使用 MQ 的方式是一样的。

### 4. 实例化监听

```java
@Configuration
public class DisruptorConfig {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Bean("xxxEventDisruptor")
    public Disruptor<XxxEventHandler.Message> disruptor() {
        // 环形队列的大小，注意要是2的幂
        int bufferSize = 1024;

        // 创建Disruptor
        Disruptor<XxxEventHandler.Message> disruptor = new Disruptor<>(XxxEventHandler.Message::new, bufferSize, executor);

        // 连接事件处理器
        disruptor.handleEventsWith(new XxxEventHandler());

        // 开始Disruptor
        disruptor.start();

        return disruptor;
    }

}
```

- 在 App 模块下，有一个 config 专门的配置类，在这里配置下消息监听。这个过程和我们之前使用的 Redis 发布订阅是一样的。

### 5. 推送消息(Test)

```java
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DisruptorTest {

    @Resource
    private Disruptor<XxxEventHandler.Message> xxxEventDisruptor;

    @Test
    public void test_publishEvent() throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            xxxEventDisruptor.publishEvent((event, sequence) -> event.setValue("你好，我是 Disruptor Message"));
        }

        // 暂停 - 测试完手动关闭程序
        new CountDownLatch(1).await();
    }

}
```

```java
24-10-26.11:55:55.827 [main            ] INFO  DisruptorTest          - Starting DisruptorTest using Java 1.8.0_311 on MacBook-Pro.local with PID 92827 (started by fuzhengwei in /Users/fuzhengwei/1024/KnowledgePlanet/road-map/xfg-dev-tech-disruptor/xfg-dev-tech-app)
24-10-26.11:55:55.829 [main            ] INFO  DisruptorTest          - The following 1 profile is active: "dev"
24-10-26.11:55:57.749 [main            ] INFO  DisruptorTest          - Started DisruptorTest in 2.526 seconds (JVM running for 3.741)
24-10-26.11:55:58.125 [pool-2-thread-1 ] INFO  XxxEventHandler        - 接收消息：你好，我是 Disruptor Message
24-10-26.11:55:58.128 [pool-2-thread-1 ] INFO  XxxEventHandler        - 接收消息：你好，我是 Disruptor Message
24-10-26.11:55:58.128 [pool-2-thread-1 ] INFO  XxxEventHandler        - 接收消息：你好，我是 Disruptor Message
24-10-26.11:55:58.128 [pool-2-thread-1 ] INFO  XxxEventHandler        - 接收消息：你好，我是 Disruptor Message
24-10-26.11:55:58.128 [pool-2-thread-1 ] INFO  XxxEventHandler        - 接收消息：你好，我是 Disruptor Message
24-10-26.11:55:58.128 [pool-2-thread-1 ] INFO  XxxEventHandler        - 接收消息：你好，我是 Disruptor Message
24-10-26.11:55:58.128 [pool-2-thread-1 ] INFO  XxxEventHandler        - 接收消息：你好，我是 Disruptor Message
24-10-26.11:55:58.128 [pool-2-thread-1 ] INFO  XxxEventHandler        - 接收消息：你好，我是 Disruptor Message
24-10-26.11:55:58.128 [pool-2-thread-1 ] INFO  XxxEventHandler        - 接收消息：你好，我是 Disruptor Message
24-10-26.11:55:58.128 [pool-2-thread-1 ] INFO  XxxEventHandler        - 接收消息：你好，我是 Disruptor Message
```

- 提供一个单测来测试消息推送，这样你就可以监听到消息了。

## 三、总结

在美团、京东、阿里，等各个大厂中都有很多这样的组件使用，在美团发布过的文章中[《高性能队列——Disruptor》](https://tech.meituan.com/2016/11/18/disruptor.html) 还有一个对应的压测数据。CPU:Intel Core i7-2720QM，JVM:Java 1.6.0_25 64-bit，OS:Ubuntu 11.04

| -                  | ABQ       | Disruptor  |
| :----------------- | :-------- | :--------- |
| Unicast: 1P – 1C   | 4,057,453 | 22,381,378 |
| Pipeline: 1P – 3C  | 2,006,903 | 15,857,913 |
| Sequencer: 3P – 1C | 2,056,118 | 14,540,519 |
| Multicast: 1P – 3C | 260,733   | 10,860,121 |
| Diamond: 1P – 3C   | 2,082,725 | 15,295,197 |

- 依据并发竞争的激烈程度的不同，Disruptor比ArrayBlockingQueue吞吐量快4~7倍。

---

另外，Log4j 2 采用了 Disruptor（一种无锁的线程间通信库），提高吞吐量降低延迟。在生产使用中，大并发的系统注意 Log4j 版本。官网说明：[https://logging.apache.org/log4j/2.12.x/manual/async.html](https://logging.apache.org/log4j/2.12.x/manual/async.html)

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-disruptor-04.png" width="850px"/>
</div>

- **异步 Logger**是 Log4j 2 中的新增功能。其目的是尽快从对 Logger.log 的调用返回到应用程序。您可以选择使所有 Logger 异步，或使用同步和异步 Logger 的混合。使所有 Logger 异步将提供最佳性能，而混合使用则可为您提供更大的灵活性。
- **LMAX Disruptor 技术**。异步记录器内部使用 [Disruptor（](https://logging.apache.org/log4j/2.12.x/manual/async.html#UnderTheHood)一种无锁的线程间通信库）而不是队列，从而实现更高的吞吐量和更低的延迟。
- 作为异步日志记录器工作的一部分，**异步附加器**已得到增强，可以在批处理结束时（当队列为空时）刷新到磁盘。这会产生与配置“immediateFlush=true”相同的结果，即所有收到的日志事件始终在磁盘上可用，但效率更高，因为它不需要在每个日志事件上都接触磁盘。（异步附加器在内部使用 ArrayBlockingQueue，不需要类路径上的 Disruptor jar。）
