---
title: 为什么 insert 配置 "SELECT LAST_INSERT_ID()" 返回个0呢？
lock: need
---

# 为什么 insert 配置 "SELECT LAST_INSERT_ID()" 返回个0呢？

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言：一个Bug

`没想到一个Bug，竟然搞我两次！`

我大抵是卷上瘾了，横竖都睡不着，坐起来身来打开Mac和外接显示器，这Bug没有由来，默然看着打印异常的屏幕，一个是我的，另外一个也是我的。

---

最近可能是卷源码，卷上瘾了。先是[《手写Spring》](https://bugstack.cn/md/spring/develop-spring/2021-05-16-%E7%AC%AC1%E7%AB%A0%EF%BC%9A%E5%BC%80%E7%AF%87%E4%BB%8B%E7%BB%8D%EF%BC%8C%E6%89%8B%E5%86%99Spring%E8%83%BD%E7%BB%99%E4%BD%A0%E5%B8%A6%E6%9D%A5%E4%BB%80%E4%B9%88%EF%BC%9F.html)，再是[《手写Mybatis》](https://bugstack.cn/md/spring/develop-mybatis/2022-03-20-%E7%AC%AC1%E7%AB%A0%EF%BC%9A%E5%BC%80%E7%AF%87%E4%BB%8B%E7%BB%8D%EF%BC%8C%E6%89%8B%E5%86%99Mybatis%E8%83%BD%E7%BB%99%E4%BD%A0%E5%B8%A6%E6%9D%A5%E4%BB%80%E4%B9%88%EF%BC%9F.html)，但没想到一个小问题竟然搞了我2次！

今天这个问题主要体现在大家平常用的Mybatis，在插入数据的时候，我们可以把库表索引的返回值通过入参对象返回回来。但是通过我自己手写的Mybatis，每次返回来的都是0，而不是最后插入库表的索引值。*因为是手写的，不是直接使用Mybatis，所以我会从文件的解析、对象的映射、SQL的查询、结果的封装等一直排查下去，但竟然问题都不在这？！*

![](https://bugstack.cn/images/article/spring/source-code-220624-01.png)

- 就是这个 selectKey 的配置，在执行插入SQL后，开始执行获取最后的索引值。
- 通常只要配置的没问题，返回对象中也有对应的 id 字段，那么就可以正确的拿到返回值了。PS：问题就出现在这里，小傅哥手写的 Mybatis 竟然只难道返回一个0！

## 二、分析：诊断异常

### 1. 源码分析

可能大部分研发伙伴没有阅读过 Mybatis 源码，所以可能不太清楚这里发生了什么，小傅哥这里给大家画张图，告诉你发生了什么才让返回的结果为0的。

![](https://bugstack.cn/images/article/spring/source-code-220624-02.png)

- Mybatis 的处理过程可以分为两个大部分来看，一部分是解析，另外一部分是使用。解析的时候把 Mapper XML 中的 insert 标签语句解析出来，同时解析 selectKey 标签。最终解析完成后，把解析的语句信息使用 MappedStatement 映射语句类存放起来。便于后续在 DefaultSqlSession 执行操作的时候，可以从 Configuration 配置项中获取出来使用。
- 那么这里有一个非常重要的点，就是执行 insert 插入的时候，里面还包含了一句查询的操作。那也就是说，我们会在一次 Insert 中，包含两条执行语句。**重点**：bug就发生在这里，为什么呢？因为最开始这两条语句执行的时候，在获取链接的时候，每一条都是获取一个新的链接，那么也就是说，insert xxx、select LAST_INSERT_ID() 在两个 connection 连接执行时，其实是不对的，没法获取到插入后的索引 ID，只有在一个链接或者一个事务下(一次 commit)才能有事务的特性，获取插入数据后的自增ID。
- 而因为这部分最开始手写 JdbcTransaction 实现 Transaction 接口获取连接的时候，每一次都是新的链接，代码块如下；
  ![](https://bugstack.cn/images/article/spring/source-code-220624-03.png)
  
	- 这里的链接获取，最开始没有 if null 的判断，每次都是直接获取链接，所以这种非一个链接下的两条 SQL 操作，所以必然不会获得到正确的结果，相当于只是单独执行 `SELECT LAST_INSERT_ID()` 所以最终的查询结果为 0 了就！*你可以测试把这条语句复制到 SQL查询工具中执行*

### 2. 借助插件

**如果说找不到可能发生异常的地方怎么办？**

其实很多时候我们排查的不一定是我们的代码，不清楚这个过程都调用了哪些模块，也不太清楚都包括了哪些调用流程。其实这个时候可以结合 IDEA Plugin：`sequence Diagram` 它可以帮助你创建出一个 UML 流程图帮助你分析代码执行流程。

但这里我们不能直接使用最外层获取 Mapper 调用 insert 看流程，因为这样的流程生成出来是有缺失的。如下：

**测试代码：普通调用**

```java
@Test
public void test_insert() {
    // 1. 获取映射器对象
    IActivityDao dao = sqlSession.getMapper(IActivityDao.class);
    Activity activity = new Activity();
    activity.setActivityId(10004L);
    activity.setActivityName("测试活动");
    activity.setActivityDesc("测试数据插入");
    activity.setCreator("xiaofuge");
    // 2. 测试验证
    Integer res = dao.insert(activity);
    sqlSession.commit();
    logger.info("测试结果：count：{} idx：{}", res, JSON.toJSONString(activity.getId()));
}
```

![](https://bugstack.cn/images/article/spring/source-code-220624-05.png)

- 这样的测试案例直接使用插件 `sequence Diagram` 生成出来的流程图是有些粗的，只能作为参考，但不能看到内部流程。因为很多流程都被 Mybatis 给封装了，所以这样不能看到细节。

**测试代码：细节调用**

```java
@Test
public void test_insert_select() throws IOException {
    // 解析 XML
    Reader reader = Resources.getResourceAsReader("mybatis-config-datasource.xml");
    XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader);
    Configuration configuration = xmlConfigBuilder.parse();
    // 获取 DefaultSqlSession
    final Environment environment = configuration.getEnvironment();
    TransactionFactory transactionFactory = environment.getTransactionFactory();
    Transaction tx = transactionFactory.newTransaction(configuration.getEnvironment().getDataSource(), TransactionIsolationLevel.READ_COMMITTED, false);
    // 创建执行器
    final Executor executor = configuration.newExecutor(tx);
    SqlSession sqlSession = new DefaultSqlSession(configuration, executor);
    // 执行查询：默认是一个集合参数
    Activity activity = new Activity();
    activity.setActivityId(10004L);
    activity.setActivityName("测试活动");
    activity.setActivityDesc("测试数据插入");
    activity.setCreator("xiaofuge");
    int res = sqlSession.insert("cn.bugstack.mybatis.test.dao.IActivityDao.insert", activity);
    Object obj = sqlSession.selectOne("cn.bugstack.mybatis.test.dao.IActivityDao.insert!selectKey");
    logger.info("测试结果：count：{} idx：{}", res, JSON.toJSONString(obj));
    sqlSession.commit();
}
```

![](https://bugstack.cn/images/article/spring/source-code-220624-06.png)

- 当小傅哥把整个 Mybatis 的调用，自己用代码分块调用后，就能看到每一步的细节了。PS：这里是一个小技巧
- 所以在你测试这部分代码的时候，把这段单元测试添加后，在使用插件 `sequence Diagram` 生成出来的流程图，就能看清楚的看到每一步的处理过程和步骤。PS：因为相当于我们把 SQL 的执行拆解出来了，所以在不被封装的情况下就可以展示全部调用过程。
- 那么这里就能看到 JdbcTransaction 对数据源的获取操作，我们点击每一个 UML 创建出的节点，都可以直接定位到具体的代码中，也就借助这个工具，更容易分析出问题所在了。**当然，这里面也有很多技巧和编程经验的积累，不是一把西瓜刀，就谁都能问人家保熟不。**

## 三、震惊：同一个坑

😂 但其实就这么一个链接的问题，在小傅哥手写Spring中也同样遇到过。

![](https://bugstack.cn/images/article/spring/source-code-220624-04.png)

在 Spring 中有一部分是关于事务的处理，其实这些事务的操作也是对 JDBC 的包装操作，依赖于数据源获得的链接来管理事务。而我们通常使用 Spring 也是结合着 Mybatis 配置上数据源的方式进行使用，那么在一个事务下操作多个 SQL 语句的时候，是怎么获得同一个链接的呢。*因为从上面👆🏻的案例中，我们得知保证事务的特性，需要在同一个链接下，即使是操作多条SQL*

由于多个SQL的操作，已经是相当于每次都获取一个新的 Session 有一个新的链接从连接池中获得，但为了能达到事务的特性，所以在需要有事务操作下的多个 SQL 前需要开启事务操作，无论是手动还是注解。

而这个事务的开启动作处理做一些事务传播行为和隔离级别的限制，其实更重要的是让多个 SQL 的执行获取的链接，需要是同一个。所以这里就引入了 ThreadLocal 基于它在同一个线程操作下保存信息的同步特性，其实这里的从事务下获取的链接，其实就是保存到 TransactionSynchronizationManager#resources 属性中的。

虽然就这么一小块内容，但在小傅哥最开始手写Spring的时候，也是给漏下了。直到到测试的时候，才发现链接发现事务总是不成功，最初还以为是整个切面逻辑没有切进去或者是我的操作方式有误。直到逐步排查调试代码，发现原来多个SQL的执行竟然不是获得的同一个链接，所以也就没法让事务生效。

## 四、常见：事务失效

可能就是这么一个小小的链接问题，有时候就会引起一堆的异常，如果说我们没有学习过源码，那么可能也不知道这样的问题到底是如何发生的。所以往往深入的研究和探索，才能让你解释一个问题的时候，更加简单直接。

那么你说，事务失效的原因还有哪些？- 分享一些常见，如果你还有遇到其他的，可以发到评论区一起看看。

1. 数据库引擎不支持事务：这里以 MySQL 为例，其 MyISAM 引擎是不支持事务操作的，InnoDB 才是支持事务的引擎，一般要支持事务都会使用 InnoDB。[https://dev.mysql.com/doc/refman/8.0/en/storage-en...](https://dev.mysql.com/doc/refman/8.0/en/storage-engine-setting.html) 从 MySQL 5.5.5 开始的默认存储引擎是：InnoDB，之前默认的都是：MyISAM，所以这点要值得注意，底层引擎不支持事务再怎么搞都是白搭。
2. 方法不是 public 的：来自 Spring 官方文档【When using proxies, you should apply the @Transactional annotation only to methods with public visibility. If you do annotate protected, private or package-visible methods with the @Transactional annotation, no error is raised, but the annotated method does not exhibit the configured transactional settings. Consider the use of AspectJ (see below) if you need to annotate non-public methods.】@Transactional 只能用于 public 的方法上，否则事务会失效，如果要用在非 public 方法上，可以开启 AspectJ 代理模式。 
3. 没有被 Spring 管理：```// @Service - 这里被注释掉了 public class OrderServiceImpl implements OrderService {     @Transactional    public void placeOrder(Order order) {    	// ...    } }```
4. 数据源没有配置事务管理器：一般来自于自研的数据库路由组件 ```@Bean public PlatformTransactionManager transactionManager(DataSource dataSource) {    return new DataSourceTransactionManager(dataSource); }``` 
5. 异常被吞了。catch 后直接吃了，事务异常无法回滚。同时要配置上对应的异常 ```@Transactional(rollbackFor = Exception.class)```

## 五、总结：学习经验

很多类似这样的技术问题，都是来自于小傅哥对源码的学习，最开始是遇到问题的时候去翻看源码，虽然很多时候也很难把整个逻辑捋顺，但一点点的积累确实会让研发人员对技术有更加夯实的认知。

那么在现在我之所以去手写Spring、手写Mybatis，也是希望通过把这样的知识全部整理处理，从中学习复杂逻辑的设计方案、设计原则和如何运用设计模式解决复杂场景的问题。PS：通常我们的业务代码复杂度很难到这个程度，所以在见过”天“后，以后所承接的业务就很容易做设计了。

另外就是对各类技术细节的把控，以及积累于这样的经验把相关技术设计运用到一些类似 SpringBoot Starter 等的开发，只有类似这样的广度、高度、深度，才能真的把个人的研发能力提升起来。PS：也是为了在技术的路上走的更远，无论是高级开发、架构师、CTO！

**欢迎一起学习手写源码和实战项目！** 适合：有需要校招、面试、晋升，想提高自己的技术深度，为自己的职业生涯续期，可以长稳发展，完善自己的技术体系，奔着高级开发和架构师路线的研发伙伴。

![校招、面试、晋升，加入小傅哥的私有技术朋友圈！](https://bugstack.cn/images/article/about/about-220605-06.png?raw=true)