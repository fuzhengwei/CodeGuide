---
layout: post
category: interview
title: 面经手册 · 第34篇《MyBatis 工作原理是什么？从 SqlSessionFactory 到 SqlSession 全链路解析》
tagline: by 小傅哥
tag: [java,interview]
excerpt: MyBatis 从配置加载到 SQL 执行，中间经历了什么？SqlSessionFactory 怎么创建？SqlSession 为什么不是线程安全的？MyBatis 用了哪些设计模式？搞懂这条链路，MyBatis 面试题就通了三分之一。本文从架构分层到源码追踪，把执行流程彻底讲透。
lock: need
---

# 面经手册 · 第34篇《MyBatis 工作原理是什么？从 SqlSessionFactory 到 SqlSession 全链路解析》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

MyBatis 面试中，除了 `#{}` 和 `${}` 之外，"工作原理"几乎是第二高频考点。

大多数人能说出个大概流程，但面试官追问一句"SqlSession 为什么不是线程安全的"，或者"MyBatis 用了哪些设计模式"，就答不上来了。

本文从 MyBatis 的架构分层出发，把配置加载 → SqlSession 创建 → SQL 执行 → 结果返回这条完整链路拆解清楚，同时把六大设计模式一起讲透。

## 二、面试题

`谢飞机，小记！`，面试继续。

**面试官**：说说 MyBatis 的工作原理？

**谢飞机**：读取配置文件，创建 SqlSession，执行 SQL……

**面试官**：能说详细点吗？每一步具体做了什么？

**谢飞机**：就是……SqlSessionFactoryBuilder 创建 SqlSessionFactory，然后……创建 SqlSession？

**面试官**：SqlSessionFactory 创建过程中，Configuration 对象做了什么？

**谢飞机**：配置信息？

**面试官**：SqlSession 是线程安全的吗？

**谢飞机**：不是……

**面试官**：为什么不是？怎么解决的？

**谢飞机**：和 Spring 整合之后……SqlSessionTemplate？

**面试官**：SqlSessionTemplate 原理是什么？MyBatis 用到了哪些设计模式？

**谢飞机**：这个我真的不知道了……再见！ヾ(￣▽￣)

## 三、功能架构分层

### 1. 三层架构

```
┌─────────────────────────────────────────────────────┐
│                   API 接口层                         │
│  SqlSession、Mapper 接口                             │
│  对外提供增删改查能力                                 │
├─────────────────────────────────────────────────────┤
│                   数据处理层                         │
│  参数映射、SQL 解析、SQL 执行、结果映射                │
│  StatementHandler、ParameterHandler、                │
│  ResultSetHandler、Executor                          │
├─────────────────────────────────────────────────────┤
│                   基础支撑层                         │
│  配置管理、事务管理、连接管理、缓存、插件              │
│  Configuration、Environment、                        │
│  TransactionFactory、DataSource                      │
└─────────────────────────────────────────────────────┘
```

### 2. 核心组件

| 组件 | 职责 |
|------|------|
| Configuration | 全局配置信息，MyBatis 的"大脑" |
| SqlSessionFactory | 创建 SqlSession 的工厂，全局唯一 |
| SqlSession | 面向开发者的 API 入口，执行 SQL |
| Executor | SQL 执行器，真正执行 SQL 的核心 |
| StatementHandler | 语句处理器，管理 Statement |
| ParameterHandler | 参数处理器，设参数 |
| ResultSetHandler | 结果集处理器，映射结果 |
| MappedStatement | 一条 SQL 的完整映射信息 |

## 四、完整执行流程

### 1. 全链路流程图

```
1. 加载配置
   mybatis-config.xml + XxxMapper.xml
       ↓
   XMLConfigBuilder 解析全局配置
       ↓
   XMLMapperBuilder 解析 Mapper XML
       ↓
   Configuration 对象（保存所有配置）

2. 创建 SqlSessionFactory
   SqlSessionFactoryBuilder.build(inputStream)
       ↓
   XMLConfigBuilder.parse()
       ↓
   new DefaultSqlSessionFactory(configuration)

3. 创建 SqlSession
   sqlSessionFactory.openSession()
       ↓
   Environment → TransactionFactory → Transaction
       ↓
   Executor（SimpleExecutor/ReuseExecutor/BatchExecutor）
       ↓
   new DefaultSqlSession(configuration, executor)

4. 执行 SQL
   sqlSession.selectList("com.xxx.UserMapper.findById", 1)
       ↓
   Configuration.getMappedStatement(id)
       ↓
   Executor.query(ms, parameter, rowBounds, resultHandler)
       ↓
   StatementHandler.prepare() → parameterize()
       ↓
   Statement.execute() → ResultSetHandler.handleResultSets()

5. 返回结果
   ResultSetHandler → DefaultResultSetHandler
       ↓
   结果集遍历 → 反射创建对象 → 属性映射
       ↓
   返回 List<Object>
```

### 2. 编程式使用步骤（完整代码）

```java
// 第一步：加载配置文件
String resource = "mybatis-config.xml";
InputStream inputStream = Resources.getResourceAsStream(resource);

// 第二步：创建 SqlSessionFactory
SqlSessionFactory sqlSessionFactory = 
    new SqlSessionFactoryBuilder().build(inputStream);

// 第三步：获取 SqlSession
SqlSession sqlSession = sqlSessionFactory.openSession();

try {
    // 第四步：执行 SQL
    User user = sqlSession.selectOne(
        "com.example.mapper.UserMapper.findById", 1);
    
    // 第五步：使用结果
    System.out.println(user.getName());
} finally {
    // 第六步：关闭 SqlSession
    sqlSession.close();
}
```

## 五、SqlSessionFactory 创建过程

### 1. 源码追踪

```java
// 1. SqlSessionFactoryBuilder
public SqlSessionFactory build(InputStream inputStream) {
    return build(new Reader(inputStream));
}

public SqlSessionFactory build(Reader reader) {
    XMLConfigBuilder parser = new XMLConfigBuilder(reader);
    return build(parser.parse());  // ← 核心：解析配置
}

public SqlSessionFactory build(Configuration config) {
    return new DefaultSqlSessionFactory(config);  // ← 创建工厂
}
```

```java
// 2. XMLConfigBuilder.parse()
public Configuration parse() {
    if (parsed) throw new Exception("已解析");
    parsed = true;
    parseConfiguration(parser.evalNode("/configuration"));
    return configuration;
}

private void parseConfiguration(XNode root) {
    // 解析各节点
    propertiesElement(root.evalNode("properties"));
    settingsElement(root.evalNode("settings"));
    typeAliasesElement(root.evalNode("typeAliases"));
    pluginElement(root.evalNode("plugins"));
    objectFactoryElement(root.evalNode("objectFactory"));
    environmentsElement(root.evalNode("environments"));
    typeHandlerElement(root.evalNode("typeHandlers"));
    mapperElement(root.evalNode("mappers"));  // ← 解析 Mapper
}
```

```java
// 3. mapperElement() 解析 Mapper XML
private void mapperElement(XNode parent) {
    for (XNode child : parent.getChildren()) {
        String resource = child.getStringAttribute("resource");
        InputStream mapperStream = Resources.getResourceAsStream(resource);
        XMLMapperBuilder mapperParser = new XMLMapperBuilder(
            mapperStream, configuration, resource, 
            configuration.getSqlFragments());
        mapperParser.parse();  // ← 解析每个 Mapper 文件
    }
}
```

### 2. Configuration 对象

Configuration 是 MyBatis 的核心配置对象，相当于 MyBatis 的"大脑"，保存了所有运行时信息：

```java
public class Configuration {
    // 环境
    protected Environment environment;
    
    // 映射注册（核心）
    protected final MapperRegistry mapperRegistry = new MapperRegistry(this);
    protected final Map<String, MappedStatement> mappedStatements = new StrictMap<>();
    
    // 缓存
    protected final Map<String, Cache> caches = new StrictMap<>();
    
    // 拦截器
    protected final InterceptorChain interceptorChain = new InterceptorChain();
    
    // 类型处理器
    protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
    
    // 全局设置
    protected boolean cacheEnabled = true;
    protected boolean lazyLoadingEnabled = false;
    protected boolean mapUnderscoreToCamelCase = false;
    // ...
}
```

## 六、SqlSession 线程安全性

### 1. 为什么不是线程安全的？

SqlSession 内部持有以下可变状态：

```java
public class DefaultSqlSession implements SqlSession {
    private final Configuration configuration;
    private final Executor executor;           // ← 持有执行器
    private final boolean autoCommit;
    private boolean dirty;                     // ← 脏标记
    private List<BatchResult> batchResults;    // ← 批量结果
}
```

```java
public abstract class BaseExecutor implements Executor {
    // 一级缓存，SqlSession 级别
    protected PerpetualCache localCache;        // ← 线程不安全！
    protected Transaction transaction;         // ← 事务连接
    protected LinkedList<DeferredLoad> deferredLoads;
}
```

**关键原因**：

1. **localCache 是 SqlSession 级别**：多个线程共享会导致缓存数据混乱
2. **transaction 持有数据库连接**：多线程共用一个 Connection 会导致状态混乱
3. **dirty 标记**：多线程同时修改会导致提交/回滚判断错误

### 2. 实际项目中的解决方案

```
方案一：每次请求创建新的 SqlSession
  ✅ 每个方法调用 openSession() → close()
  ❌ 每次创建销毁，性能差

方案二：Spring 整合（推荐）
  ✅ SqlSessionTemplate，每次操作创建新 SqlSession
  ✅ 由 Spring 事务管理器控制生命周期

方案三：ThreadLocal 绑定
  ✅ SqlSessionManager 使用 ThreadLocal
  ⚠️ 需要手动管理
```

## 七、MyBatis 六大设计模式

### 1. Builder 模式 — SqlSessionFactoryBuilder

```java
// 复杂对象分步构建
SqlSessionFactory factory = new SqlSessionFactoryBuilder()
    .build(inputStream);
// 内部：XMLConfigBuilder.parse() → Configuration → DefaultSqlSessionFactory
```

**应用场景**：Configuration 对象属性繁多，通过 Builder 模式分步构建。

### 2. 工厂模式 — SqlSessionFactory

```java
// 工厂创建产品
SqlSession session = sqlSessionFactory.openSession();
```

**应用场景**：统一创建 SqlSession，隐藏 Executor 创建细节。

### 3. 代理模式 — MapperProxy

```java
// JDK 动态代理，Mapper 接口无需实现类
UserMapper mapper = sqlSession.getMapper(UserMapper.class);
mapper.findById(1);  // 实际由 MapperProxy.invoke() 执行
```

**应用场景**：Mapper 接口的代理创建和方法调用拦截（第35篇详解）。

### 4. 模板方法模式 — BaseExecutor

```java
public abstract class BaseExecutor implements Executor {
    @Override
    public int update(MappedStatement ms, Object parameter) {
        ErrorContext.instance().resource(ms.getResource())
            .activity("executing an update");
        if (closed) throw new ExecutorException("Cannot execute, closed");
        clearLocalCache();
        return doUpdate(ms, parameter);  // ← 模板方法，子类实现
    }
    
    protected abstract int doUpdate(MappedStatement ms, Object parameter);
}
```

**子类**：
- SimpleExecutor：每次创建新的 Statement
- ReuseExecutor：复用 Statement（预编译缓存）
- BatchExecutor：批量执行

### 5. 装饰器模式 — CachingExecutor

```java
public class CachingExecutor implements Executor {
    private final Executor delegate;       // ← 被装饰者
    private final TransactionalCacheManager tcm;
    
    @Override
    public <E> List<E> query(...) {
        Cache cache = ms.getCache();
        if (cache != null) {
            // 二级缓存命中直接返回
            Object key = createCacheKey(ms, ...);
            return tcm.getObject(cache, key);
        }
        return delegate.query(...);  // ← 交给被装饰者执行
    }
}
```

装饰链：SimpleExecutor → CachingExecutor → 插件代理

### 6. 组合模式 — SqlNode

```java
// 动态 SQL 的组合模式
public interface SqlNode {
    boolean apply(DynamicContext context);
}

// 叶子节点
public class IfSqlNode implements SqlNode { ... }
public class ForeachSqlNode implements SqlNode { ... }

// 组合节点
public class MixedSqlNode implements SqlNode {
    private final List<SqlNode> contents;  // ← 包含多个子节点
    public boolean apply(DynamicContext context) {
        for (SqlNode sqlNode : contents) {
            sqlNode.apply(context);  // ← 递归调用
        }
        return true;
    }
}
```

**设计模式总结**：

| 设计模式 | 应用 | 核心类 |
|---------|------|--------|
| Builder | 配置构建 | SqlSessionFactoryBuilder |
| Factory | SqlSession 创建 | SqlSessionFactory |
| Proxy | Mapper 代理 | MapperProxy |
| Template Method | 执行器骨架 | BaseExecutor |
| Decorator | 缓存装饰 | CachingExecutor |
| Composite | 动态 SQL | MixedSqlNode |

## 八、常见面试追问

### Q1：Configuration 对象是什么时候创建的？

在 SqlSessionFactoryBuilder.build() 阶段，XMLConfigBuilder.parse() 创建并填充 Configuration 对象，然后传给 DefaultSqlSessionFactory。Configuration 是全局唯一的，MyBatis 启动时一次性创建。

### Q2：为什么 SqlSession 不是线程安全的？Spring 怎么解决的？

SqlSession 内部持有数据库连接（Connection）和一级缓存（PerpetualCache），这些都不是线程安全的。Spring 通过 SqlSessionTemplate 解决：每次数据库操作都创建新的 SqlSession，操作完成后自动关闭，同时通过 Spring 事务管理器将 SqlSession 绑定到当前线程的事务中。

### Q3：Executor 有几种？有什么区别？

| 类型 | 特点 | 场景 |
|------|------|------|
| SimpleExecutor | 每次创建新 Statement | 默认，大多数场景 |
| ReuseExecutor | 复用预编译 Statement | 重复执行相同 SQL |
| BatchExecutor | 批量执行，不立即提交 | 批量插入/更新 |

### Q4：MappedStatement 的 ID 是什么？

`namespace + 方法名`，例如 `com.example.UserMapper.findById`。这也是 Mapper 接口方法不能重载的原因——ID 必须唯一。

## 九、总结

```
记住三个核心要点：

1. MyBatis 执行流程
   加载配置 → 创建 Configuration → 创建 SqlSessionFactory 
   → 获取 SqlSession → 执行 SQL → 返回结果
   Configuration 是全局唯一的核心配置对象

2. SqlSession 非线程安全
   原因：持有 Connection + 一级缓存 + dirty 标记
   解决：Spring 整合后由 SqlSessionTemplate 管理

3. 六大设计模式
   Builder(构建)、Factory(创建)、Proxy(Mapper代理)
   Template(执行器骨架)、Decorator(缓存装饰)、Composite(动态SQL)
   理解这些模式就能理解 MyBatis 的大部分设计
```

**面试回答模板**：

> MyBatis 的工作流程分五步：加载配置文件，通过 XMLConfigBuilder 解析全局配置和 Mapper XML，将所有信息存入 Configuration 对象；然后通过 Configuration 创建 SqlSessionFactory；调用 openSession() 获取 SqlSession，SqlSession 内部创建 Executor 执行器；通过 Executor 执行 SQL，经过 StatementHandler 准备语句、ParameterHandler 设置参数、执行并获取结果集；最后由 ResultSetHandler 将结果集映射为 Java 对象。
>
> SqlSession 不是线程安全的，因为它内部持有数据库连接和一级缓存。在 Spring 整合中，SqlSessionTemplate 每次操作创建新的 SqlSession，并通过事务管理器绑定到当前线程。
>
> MyBatis 用到了六大设计模式：Builder 模式构建配置、工厂模式创建 SqlSession、动态代理实现 Mapper 接口、模板方法定义执行器骨架、装饰器模式实现二级缓存、组合模式处理动态 SQL。
