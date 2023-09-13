---
title: Redis
lock: need
---

# Redis 缓存、加锁(独占/分段)、发布/订阅，常用特性的使用和高级编码操作

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=575827141&bvid=BV17z4y1j7m5&cid=1262749182&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

本文的宗旨在于通过简单干净实践的方式，向读者介绍 Redis 的安装部署，以及使用 Redisson 框架完成 Redis 常用核心功能的场景使用。

这些场景包括；Redis 的基本缓存使用、Redis 加锁（Redisson 提供了很多锁的方式，这里我们会展示独占锁和无锁化的性能测试）。之后还有一个非常重要的场景是关于 Redis 的发布和订阅。

本节内容会涉及到结合 Spring 框架进行自定义 Bean 对象的注入容器操作，以满足尽可能减少配置的情况下，完成对象的实例化和注入使用。这样的操作非常具有高级编码的实战性，值得大家折腾一下，也能顺便补充 Spring 源码的运用。

本文涉及的工程：

- xfg-dev-tech-redis：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-redis](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-redis)- `docs/dev-ops 提供了 mysql、redis 安装脚本，和数据初始化操作`
- 官网：[https://redis.io/](https://redis.io/) - `开源内存数据存储，被数百万开发人员用作数据库、缓存、流引擎和消息代理。`
- 工具：[RedisDesktopManager](https://github.com/qishibo/AnotherRedisDesktopManager/releases)、[insight](https://redis.io/docs/ui/insight/)

## 一、案例背景

在互联网应用开发中，Redis 缓存的使用，大部分都是为了保护数据库的。让应用对于非必要的情况下，尽可能减少对数据库的调用。比如一份固定的数据可以放到 Redis 缓存中提供查询，或者需要数据库唯一索引仿重拦截 insert 操作先进行 Redis 布隆过滤器校验，也或者是分布式场景下的加锁处理。这样可以减少了对数据库资源的占用，也提供了接口的响应性能。

同时也还有一些专门针对 Redis 做的技术方案，来提高系统的响应吞吐量和响应性能。如；基于 Redis 内存存储实现的规则引擎、基于 Redis 队列实现的低延迟任务调度、基于 Redis 发布和订阅实现的流程解耦操作等等，都是互联网需求场景中非常常用的技术方案。那么本节小傅哥会模拟出一个订单下单场景，来使用 Redis 缓存、加锁、发布/订阅等功能，为大家展示 Redis 的使用。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-redis-01.png?raw=true" width="700px"/>
</div>

- 整个案例在DDD分层架构下，通过领域层调用仓储，完成订单的写库操作。在写库的时候，添加了不同类型锁的处理，以验证性能的差异。之后写入缓存和发布 Redis 消息。让监听端可以收取到发布的信息。
- 通过这样一个非常常见的订单创建和查询的场景，来学习 Redis 的使用。在使用中，我们用到了 Redisson 框架，由它来处理 Redis 的调用。

## 二、环境安装

在安装执行 docker-compose.yml 脚本之前，你需要先在本地安装 [docker](https://bugstack.cn/md/road-map/docker.html) 之后 IntelliJ IDEA 打开 docker-compose.yml 文件，如图操作即可安装。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-redis-02.png?raw=true" width="700px"/>
</div>

- 在 docker-compose.yml 中会先安装 MySQL 并执行 sql 文件夹里的 SQL 语句初始化数据库表。之后会安装 Redis 环境，Redis 的配置内容放在了 redis.conf 中，里面有 Redis 的连接密码。

## 三、功能实现

接下来小傅哥会带着大家在模拟的订单场景中，把 Redis 的缓存、加锁、发布/订阅的相关功能依次实现下。

### 1. 工程结构

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-redis-03.png?raw=true" width="600px"/>
</div>

- 这是一套 DDD 工程模型，也可以说整个教程其实都是 DDD 工程模型的拆解讲解，将各个模块嵌入到 DDD 分层架构中，看看他们是如何使用的。
- 工程分为，app、domain、infrastructure、trigger 这样的四层，其实还有一个 types 通用层。

  - app；用于配置 Redis 的相关启动操作，鉴于 SpringBoot 以及 Redis 版本问题，这里我们自己来创建客户端，更好兼容版本的差异。同时也可以扩展一些额外的功能。
  - domain；是领域服务层，order 可以看做是一个订单域，包括订单的创建、支付、查询，都可以在这个领域实现。*这个订单领域涉及的表就是前面章节，所压测的表 [【压测】MySQL 连接池 c3p0、dbcp、druid、hikari](https://bugstack.cn/md/road-map/connection-pool.html)*
  - infrastructure；基础层是对 domain 依赖倒置的实现，具体到库的操作、缓存的操作，都是用这一层来实现。所以我们操作 Redis 的加锁、缓存，也会放到这里来处理。
  - trigger；触发器层，一般也有叫接口层。一般 http、rpc、job、mq、listener 都是在这一层进来使用。所以我们订阅 Redis 的消息也是放到这一层中处理。
  - types；工程中还有一个通用类型层，定义一些非专属 domain 领域内的公共资源。如配置一个自定义注解，来处理一些类的动态加载和组件开发。本章中我们就定义了一个这样的注解，来动态注入实例化的 Bean 对象。**这块非常值得学习一下，因为它是解决此类场景的高级编码**
  
### 2. 配置缓存

在 app 模块下的 config 中，创建 RedisClientConfigProperties 配置类和 RedisClientConfig 客户端启动类。用于通过 Redisson 创建 Redis 的连接客户端。

```yml
redis:
  sdk:
    config:
      host: localhost
      port: 6379
      password: 123456
      pool-size: 10
      min-idle-size: 5
      idle-timeout: 30000
      connect-timeout: 5000
      retry-attempts: 3
      retry-interval: 1000
      ping-interval: 60000
      keep-alive: true
```

- 本身 Spring 也提供了 Redis 的配置，但鉴于兼容问题和后续的功能拓展，还是比较建议自己添加配置。
- 关于代码的实现部分，可以参考 RedisClientConfigProperties、RedisClientConfig 

### 3. 数据缓存

Redis 的大部分操作其实都是缓存数据，提高系统的 QPS，在插入、更新、删除(逻辑删)、查询的时候，依赖于 Redis 进行提速操作。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-redis-04.png?raw=true" width="600px"/>
</div>

```java
// 设置到缓存，在创建订单完成后写入缓存
redissonService.setValue(orderId, orderEntity);

@Override
public OrderEntity queryOrder(String orderId) {
    OrderEntity orderEntity = redissonService.getValue(orderId);
    if (null == orderEntity) {
        UserOrderPO userOrderPO = userOrderDao.selectByOrderId(orderId);
        orderEntity = new OrderEntity();
        orderEntity.setUserName(userOrderPO.getUserName());
        orderEntity.setUserId(userOrderPO.getUserId());
        // 设置到缓存
        redissonService.setValue(orderId, orderEntity);
    }
    return orderEntity;
}
```

- 在插入数据的时候，可以一并切入缓存。如果有更新操作，可以考虑删除缓存，在查询更新。因为更新操作，很多时候都是部分字段更新，这个时候直接更新缓存容易不准。
- 最后就是查询时，用缓存拦截，避免所有的查询都打到库上。这样可以提高系统的 QPS
- 另外关于缓存击穿，说的就是你本来要在缓存存放大量数据的，但存放偏差或者漏了，那么这个时候大量请求都打到库上，导致把数据库拖垮。尤其是那种需要做事务加锁有资源竞争的，会更严重。

### 4. 加锁处理

使用 Redis 加分布式锁，也是分布式架构设计中非常常用的手段。常用于的场景包括；流程较长，耗时较多的个人开户、下单行为。也包括；一些资源竞争时加分布式锁，排队处理请求。但对于资源竞争的这类库存占用，如果加分布式锁是非常影响系统的吞吐量的，因为所有的用户都在等待上一个用户做完流程后释放锁的处理，相当于你即使系统是分布式的了，但这里的分布式锁依然会把性能拖慢。所以如图，我们要考虑两种场景不同的加锁方式。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-redis-05.png?raw=true" width="600px"/>
</div>

```java
/** 独占锁 */
@Override
public String createOrderByLock(OrderAggregate orderAggregate) {
    RLock lock = redissonService.getLock("create_order_lock_".concat(orderAggregate.getSkuEntity().getSku()));
    try {
        lock.lock();
        long decrCount = redissonService.decr(orderAggregate.getSkuEntity().getSku());
        if (decrCount < 0) return "已无库存[初始化的库存和使用库存，保持一致。orderService.initSkuCount(\"13811216\", 10000);]";
        return createOrder(orderAggregate);
    } finally {
        lock.unlock();
    }
}

/** 分段锁，也可以称为无锁化 */
@Override
public String createOrderByNoLock(OrderAggregate orderAggregate) {
    UserEntity userEntity = orderAggregate.getUserEntity();
    SKUEntity skuEntity = orderAggregate.getSkuEntity();
    // 模拟锁商品库存
    long decrCount = redissonService.decr(skuEntity.getSku());
    if (decrCount < 0) return "已无库存[初始化的库存和使用库存，保持一致。orderService.initSkuCount(\"13811216\", 10000);]";
    String lockKey = userEntity.getUserId().concat("_").concat(String.valueOf(decrCount));
    RLock lock = redissonService.getLock(lockKey);
    try {
        lock.lock();
        return createOrder(orderAggregate);
    } finally {
        lock.unlock();
    }
}
```

- 对于第1类的场景，主要是为了避免用户在一次操作后，又反复申请。系统上避免重复受理，所以添加分布式锁的方式进行拦截。如果不加分布式锁，就会进入到库表中通过唯一的索引拦截，这样对数据库的压力就比较大。
- 对于第2类的场景，是采用了分段或者自增滑块的锁方式进行处理，减少对同一个锁的等待，而是生成一堆的锁，让用户去使用。**也就是最开始案例背景的图中，一个个⭕️圆圈的分段锁**

### 5. 发布/订阅

此场景的案例会涉及到如何向 Spring 动态注入已经实例化后的 Bean 对象。为什么会出现这个场景呢？

首先 Redis 的发布订阅，简单案例代码如下；

```java
// 创建Redisson客户端
RedissonClient redisson = Redisson.create();

// 获取RTopic对象
RTopic<String> topic = redisson.getTopic("myTopic");

// 发布消息
topic.publish("Hello, Redisson!");

// 添加监听器
topic.addListener(String.class, (channel, msg) -> {
    System.out.println("Received message: " + msg);
});

// 关闭Redisson客户端
redisson.shutdown();
```

- 发布和订阅，是我们需要对同一个 Topic 进行发布和监听操作。但这个操作的代码是一种手动编码，但在我们实际使用中，如果所有的都是手动编码，**一个是非常麻烦，再有一个是非常累人**。
- 所以这里小傅哥决定给你来个高级编码，通过自定义注解，来完成动态监听和将对象动态注入到 Spring 容器中，让需要注入的属性，可以被动态注入。

#### 5.1 自定义注解

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface RedisTopic {

    String topic() default "";

}
```

- 起到了一种标识作用。`快捷键；你可以在 IDEA 工程中，摁2下 Shift 搜索这个类。`

#### 5.2 注解使用

**源码**：`cn.bugstack.xfg.dev.tech.infrastructure.trigger.mq#RedisTopicListener02`

```java
@Slf4j
@Service
@RedisTopic(topic = "testRedisTopic02")
public class RedisTopicListener02 implements MessageListener<String> {

    @Override
    public void onMessage(CharSequence channel, String msg) {
        log.info("02-监听消息(Redis 发布/订阅): {}", msg);
    }

}
```

- 对需要监听 RedisTopic 的类，进行注解配置。之后在下面的代码中使用。

#### 5.3 动态注入

**源码**：`cn.bugstack.xfg.dev.tech.config.RedisClientConfig#redissonClient`

```java
// 添加监听
String[] beanNamesForType = applicationContext.getBeanNamesForType(MessageListener.class);
for (String beanName : beanNamesForType) {
    MessageListener bean = applicationContext.getBean(beanName, MessageListener.class);
    Class<?> beanClass = bean.getClass();
    if (beanClass.isAnnotationPresent(RedisTopic.class)) {
        RedisTopic redisTopic = beanClass.getAnnotation(RedisTopic.class);
        
        RTopic topic = redissonClient.getTopic(redisTopic.topic());
        topic.addListener(String.class, bean);
        
        // 动态创建 bean 对象，注入到 spring 容器，bean 的名称为 redisTopic，对象为 RTopic
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        beanFactory.registerSingleton(redisTopic.topic(), topic);
    }
}
```

- 通过 applicationContext 获取所有实现了 MessageListener 接口的类，并对这个类的注解进行识别。
- 所有使用了咱们定义注解的类，都进行获取获取 Topic 和添加监听操作。获取的 bean 对象，就是要监听的类。
- 最后一步，再把这个类，通过 Spring 的 BeanFactory 工厂，进行注册。这样你再其他类中，就可以自动注入 topic 对象了，并进行 push 消息操作。

#### 5.4 使用对象

```java

@Slf4j
@Repository
public class OrderRepository implements IOrderRepository {

    @Resource
    private IRedisService redissonService;
    @Resource
    private IUserOrderDao userOrderDao;
    
    @Resource
    private RTopic testRedisTopic;

    @Resource(name = "testRedisTopic02")
    private RTopic testRedisTopic02;

    @Resource(name = "testRedisTopic03")
    private RTopic testRedisTopic03;


    @Override
    public String createOrder(OrderAggregate orderAggregate) {
    
        // 省略...
      
        testRedisTopic02.publish(JSON.toJSONString(orderEntity));
        testRedisTopic03.publish(JSON.toJSONString(orderEntity));

        return orderId;
    }    
}    
```

- testRedisTopic 是我们硬编码创建的 Bean 对象，testRedisTopic02、testRedisTopic03 是我们通过自定义注解动态创建的 Bean 对象。
- 之后就可以在需要 push 消息的方法中，使用 publish 发布你的消息内容了，并可以在监听中获取到消息。

## 四、功能测试

### 1. 分布式锁压测

**源码**：`cn.bugstack.xfg.dev.tech.test.domain.OrderServiceTest`

```java
@Test
public void test_createOrder() throws InterruptedException {
    String sku = RandomStringUtils.randomNumeric(9);
    int count = 10000;
    orderService.initSkuCount(sku, count);
  
    for (int i = 0; i < count; i++) {
        threadPoolExecutor.execute(() -> {
            UserEntity userEntity = UserEntity.builder()
                    .userId("小傅哥")
                    .userName("xfg".concat(RandomStringUtils.randomNumeric(3)))
                    .userMobile("+86 13521408***")
                    .build();
            SKUEntity skuEntity = SKUEntity.builder()
                    .sku(sku)
                    .skuName("《手写MyBatis：渐进式源码实践》")
                    .quantity(1)
                    .unitPrice(BigDecimal.valueOf(128))
                    .discountAmount(BigDecimal.valueOf(50))
                    .tax(BigDecimal.ZERO)
                    .totalAmount(BigDecimal.valueOf(78))
                    .build();
            DeviceVO deviceVO = DeviceVO.builder()
                    .ipv4("127.0.0.1")
                    .ipv6("2001:0db8:85a3:0000:0000:8a2e:0370:7334".getBytes())
                    .machine("IPhone 14 Pro")
                    .location("shanghai")
                    .build();
            long threadBeginTime = System.currentTimeMillis(); // 记录线程开始时间
            // 耗时:4毫秒
               String orderId = orderService.createOrder(new OrderAggregate(userEntity, skuEntity, deviceVO));
            // 耗时:106毫秒
              String orderId = orderService.createOrderByLock(new OrderAggregate(userEntity, skuEntity, deviceVO));
            // 耗时:4毫秒
            String orderId = orderService.createOrderByNoLock(new OrderAggregate(userEntity, skuEntity, deviceVO));
            long took = System.currentTimeMillis() - threadBeginTime;
            totalExecutionTime.addAndGet(took); // 累加线程耗时
            log.info("写入完成 {} 耗时 {} (ms)", orderId, took / 1000);
        });
    }
    new Thread(() -> {
        while (true) {
            if (threadPoolExecutor.getActiveCount() == 0) {
                log.info("执行完毕，总耗时：{} (ms)", (totalExecutionTime.get() / 1000));
                  log.info("执行完毕，总耗时:{}", "\r\033[31m" + (totalExecutionTime.get() / 1000) + "\033[0m");
                break;
            }
            try {
                Thread.sleep(350);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }).start();
    // 等待
    new CountDownLatch(1).await();
}
```

- 测试前，记得修改代码 count 值，代表这要初始化内存多少个容量。另外是环境记得先执行安装。
- 接下来，我们进入了压测环节。createOrder 不使用锁、createOrderByLock 使用独占锁、createOrderByNoLock 是分段锁，也可以当做无锁处理。
- 测试结果为，createOrderByLock 会占用较长的耗时。createOrderByNoLock 分段锁无锁接近于直接操作库。
- 测试的过程中，还会看到监听订阅的消息，在控制台打印。

### 2. 其他测试

除了以上这结合业务的功能测试以外，本章还提供了；读写锁、异步锁、信号量、队列、延迟队列的相关测试。

```java
/**
 * 延迟队列场景应用；https://mp.weixin.qq.com/s/jJ0vxdeKXHiYZLrwDEBOcQ
 */
@Test
public void test_getDelayedQueue() throws InterruptedException {
    RBlockingQueue<Object> blockingQueue = redissonService.getBlockingQueue("xfg-dev-tech-task");
    RDelayedQueue<Object> delayedQueue = redissonService.getDelayedQueue(blockingQueue);
    new Thread(() -> {
        try {
            while (true){
                Object take = blockingQueue.take();
                log.info("测试结果 {}", take);
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }).start();
    int i = 0;
    while (true){
        delayedQueue.offerAsync("测试" + ++i, 100L, TimeUnit.MILLISECONDS);
        Thread.sleep(1000L);
    }
}
```

- 详见源码：`cn.bugstack.xfg.dev.tech.test.infrastructure.redis.RedisTest`

---

Redis 的使用还有很多有意思、有价值的场景，如果读者还有好的案例，也可以在源码中提交PR。
