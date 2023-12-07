---
title: guava
lock: need
---

# Guava 使用

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

本文的宗旨在于通过简单干净实践的方式教会读者，使用 Guava 的一些常用操作方法。这些方法也是日常使用 Guava 时最为常用的方法，如果你在使用中还有一些案例和特性，或者踩坑经验也可以在本文提交PR

本文涉及的工程：

- xfg-dev-tech-guava：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-guava](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-guava)
- Github Guava：[https://github.com/google/guava/wiki](https://github.com/google/guava/wiki)

## 一、组件介绍

Guava 是 Google 的一组核心 Java 库，其中包括新的集合类型（例如 multimap 和 multiset）、不可变集合、图形库以及用于并发、I/O、哈希、原语、字符串等的实用程序！它被广泛用于 Google 内部的大多数 Java 项目，也被许多其他公司广泛使用。—— 来自于 Guava 的介绍。

在小傅哥的小项目中使用 Guava，最常用也是非常喜欢用的就是它可以替代 Redis、MQ，在小项目中使用，这样我就不用一个项目引入过多的技术栈，包括；
1. 它有一个有时效性的缓存，可以替代 Redis 使用。
2. 他有一个 EventBus 消息总线，可以替代 MQ 使用。

此外，他还有布隆过滤器、简化的Java反射，以及一些集合和并发的操作。也是可以使用的，非常方便。

## 二、常用功能

### 1. 本地缓存

```java
@Test
public void test_cache() {
    Cache<String, String> cache = CacheBuilder.newBuilder()
            // 最大存储条数，缓存将尝试逐出最近或不经常使用的条目
            .maximumSize(10000)
            // 可以设定删除时候的权重判断
            .weigher((Weigher<String, String>) (x, y) -> x.length() - y.length())
            // 有效时间
            .expireAfterWrite(3, TimeUnit.SECONDS)
            // 记录次数
            .recordStats()
            .build();
    cache.put("xfg", "bugstack.cn");
    log.info("测试结果：{}", cache.getIfPresent("xfg"));
    cache.invalidate("xfg"); // cache.invalidateAll(); 也可以全部删除
    log.info("测试结果：{}", cache.getIfPresent("xfg"));
    log.info("测试结果：{}", cache.stats());
}
```

- 你可以自己设定有时效性的缓存对象，还可以记录次数、权重和最大条数。
- 如果你的项目不大，也不想自己实现有过期时间的缓存，那么 Guava 非常适合使用。

### 2. 事件总线

```java
@Test
public void test_eventbus() {
    EventBus eventBus = new EventBus();
    eventBus.register(new Listener());
    // 可以由其他服务推送消息，之后就可以在监听中收到了
    eventBus.post("消息总线，订单号：100001");
}
static class Listener {
    @Subscribe
    public void handleEvent(String orderId) {
        log.info("测试结果：{}", orderId);
    }
}
```

- 事件总线和MQ消息实现的效果是一致的，都是为了解耦功能流程。但 Guava 的这个组件，非常适合在自己的小项目中使用，接入成本非常低。

### 3. 并发回调

```java
@Test
public void test_ListenableFuture() throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(1);
    ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
    ListenableFuture<String> explosion = executorService.submit(() -> "finished");
    ExecutorService callBackService = Executors.newFixedThreadPool(1);
    Futures.addCallback(explosion, new FutureCallback<String>() {
        public void onSuccess(String explosion) {
            System.out.println("onSuccess");
            countDownLatch.countDown();
        }
        public void onFailure(Throwable thrown) {
            System.out.println("onFailure");
            countDownLatch.countDown();
        }
    }, callBackService);
    countDownLatch.await();
}
```

### 4. 布隆过滤器

```java
@Test
public void test_BloomFilter() {
    BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()),
            1000,
            0.01);
    // 向布隆过滤器中添加元素
    bloomFilter.put("apple");
    bloomFilter.put("banana");
    bloomFilter.put("orange");
    // 检查元素是否存在于布隆过滤器中
    System.out.println(bloomFilter.mightContain("apple"));   // true
    System.out.println(bloomFilter.mightContain("banana"));  // true
    System.out.println(bloomFilter.mightContain("orange"));  // true
    System.out.println(bloomFilter.mightContain("grape"));   // false
    // 输出布隆过滤器的统计信息
    System.out.println("Expected FPP: " + bloomFilter.expectedFpp());
    System.out.println("Number of Inserted Elements: " + bloomFilter.approximateElementCount());
}
```

### 5. 反射工具包

```java
@Test
public void test_Invokable() throws NoSuchMethodException {
    Method method = UserEntity.class.getMethod("getUserName");
    Invokable<?, ?> invokable = Invokable.from(method);
    log.info("测试结果 - 方法名称：{}", invokable.getName());
    log.info("测试结果 - 参数类型：{}", JSON.toJSONString(invokable.getTypeParameters()));
    log.info("测试结果 - 静态判断：{}", invokable.isStatic());
    // !(Modifier.isFinal(method.getModifiers()) || Modifiers.isPrivate(method.getModifiers()) || Modifiers.isStatic(method.getModifiers()) || Modifiers.isFinal(method.getDeclaringClass().getModifiers()))
    log.info("测试结果 - isOverridable：{}", invokable.isOverridable());
}
```

- 如果你开发一些组件，有不少的操作都是需要判断方法的权限范围、包的权限范围等，使用 Guava 的插件也会非常方便。

--- 

其他更多的操作可以参考仓库代码和官网文档学习，基本都是非常完整的案例。
