---
layout: post
category: interview
title: 面经手册 · 第41篇《MyBatis 插件怎么拦截 SQL？四大核心对象与 Interceptor 原理分析》
tagline: by 小傅哥
tag: [java,interview]
excerpt: MyBatis 插件机制是扩展框架能力的关键。PageHelper 分页、SQL 监控、数据权限，都是通过插件实现的。但你知道插件是怎么拦截 SQL 的吗？四大核心对象是什么？Interceptor 原理是什么？多个插件的执行顺序怎么控制？本文从标签用法到源码追踪，把插件机制彻底讲透。
lock: need
---

# 面经手册 · 第41篇《MyBatis 插件怎么拦截 SQL？四大核心对象与 Interceptor 原理分析》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

MyBatis 的强大之处，不仅在于 SQL 映射和缓存机制，更在于它开放的插件扩展点。

PageHelper 分页插件、SQL 执行时间监控、数据权限过滤、自动填充字段……这些常见的扩展能力，全部基于 MyBatis 的 Interceptor 插件机制实现。

但大多数候选人只知道"插件就是拦截器"，面试官一追问底层原理就露馅。

## 二、面试题

`谢飞机，小记！`，面试继续。

**面试官**：MyBatis 插件的原理是什么？

**谢飞机**：通过拦截器拦截 SQL 执行。

**面试官**：能拦截哪些对象？

**谢飞机**：Executor？

**面试官**：四大核心对象是什么？

**谢飞机**：Executor、StatementHandler……还有？

**面试官**：ParameterHandler 和 ResultSetHandler。插件是怎么拦截的？底层用的什么技术？

**谢飞机**：动态代理？

**面试官**：对，JDK 动态代理。那 Plugin.wrap() 做了什么？

**谢飞机**：……

**面试官**：多个插件的执行顺序是怎样的？

**谢飞机**：按配置顺序？

**面试官**：你再想想。下一个！

## 三、四大核心对象

### 1. 概览

MyBatis SQL 执行过程中，有四大核心对象，每个对象都有可拦截的方法：

| 核心对象 | 职责 | 可拦截方法 |
|---------|------|-----------|
| Executor | SQL 执行器，总调度 | update/query/commit/rollback |
| StatementHandler | SQL 语句处理器 | prepare/parameterize/batch/update/query |
| ParameterHandler | 参数处理器 | setParameters/getParameterObject |
| ResultSetHandler | 结果集处理器 | handleResultSets/handleOutputParameters |

### 2. 创建时机

```java
// org.apache.ibatis.session.Configuration
public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
    Executor executor = new SimpleExecutor(this, transaction);
    if (cacheEnabled) executor = new CachingExecutor(executor);
    executor = (Executor) interceptorChain.pluginAll(executor);  // ← 插件拦截
    return executor;
}

public StatementHandler newStatementHandler(Executor executor, MappedStatement ms, ...) {
    StatementHandler handler = new RoutingStatementHandler(executor, ms, parameter, ...);
    handler = (StatementHandler) interceptorChain.pluginAll(handler);  // ← 插件拦截
    return handler;
}

public ParameterHandler newParameterHandler(MappedStatement ms, Object parameter, ...) {
    ParameterHandler handler = new DefaultParameterHandler(ms, parameter, boundSql);
    handler = (ParameterHandler) interceptorChain.pluginAll(handler);  // ← 插件拦截
    return handler;
}

public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement ms, ...) {
    ResultSetHandler handler = new DefaultResultSetHandler(executor, ms, ...);
    handler = (ResultSetHandler) interceptorChain.pluginAll(handler);  // ← 插件拦截
    return handler;
}
```

**每个核心对象创建后，都会经过 `interceptorChain.pluginAll()` 进行插件包装。**

### 3. 执行链路

```
SqlSession
  ↓
Executor（被插件代理）
  ↓
StatementHandler（被插件代理）
  ↓  prepare() → parameterize()
ParameterHandler（被插件代理）
  ↓  setParameters()
Statement.execute()
  ↓
ResultSetHandler（被插件代理）
  ↓  handleResultSets()
结果返回
```

## 四、插件原理 — JDK 动态代理

### 1. 核心接口

```java
public interface Interceptor {
    // 拦截逻辑
    Object intercept(Invocation invocation) throws Throwable;
    
    // 创建代理对象
    default Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
    
    // 设置属性
    default void setProperties(Properties properties) {}
}
```

### 2. 拦截器链

```java
// org.apache.ibatis.plugin.InterceptorChain
public class InterceptorChain {
    private final List<Interceptor> interceptors = new ArrayList<>();
    
    public Object pluginAll(Object target) {
        for (Interceptor interceptor : interceptors) {
            target = interceptor.plugin(target);  // 逐层代理
        }
        return target;
    }
}
```

### 3. Plugin.wrap() 源码

```java
// org.apache.ibatis.plugin.Plugin
public class Plugin implements InvocationHandler {
    private final Object target;
    private final Interceptor interceptor;
    private final Map<Class<?>, Set<Method>> signatureMap;
    
    public static Object wrap(Object target, Interceptor interceptor) {
        // 1. 获取插件要拦截的方法签名
        Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
        Class<?> type = target.getClass();
        
        // 2. 检查目标对象是否在拦截范围内
        Map<Class<?>, Set<Method>> targetMap = new HashMap<>();
        for (Class<?> superClass : type.getInterfaces()) {
            if (signatureMap.containsKey(superClass)) {
                targetMap.put(superClass, signatureMap.get(superClass));
            }
        }
        
        // 3. 如果有需要拦截的方法，创建代理
        if (!targetMap.isEmpty()) {
            return Proxy.newProxyInstance(
                type.getClassLoader(),
                type.getInterfaces(),
                new Plugin(target, interceptor, targetMap)  // ← JDK 动态代理
            );
        }
        
        // 4. 不需要拦截，返回原对象
        return target;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 检查当前方法是否需要拦截
        Set<Method> methods = signatureMap.get(method.getDeclaringClass());
        if (methods != null && methods.contains(method)) {
            // 拦截！调用插件的 intercept 方法
            return interceptor.intercept(new Invocation(target, method, args));
        }
        // 不拦截，调用原始方法
        return method.invoke(target, args);
    }
}
```

### 4. 原理图解

```
目标对象：StatementHandler
    ↓ Plugin.wrap()
代理对象：$Proxy0 (implements StatementHandler)
    ↓ 调用 prepare()
Plugin.invoke()
    ↓ 检查是否在 signatureMap 中
    ↓ 是 → interceptor.intercept(new Invocation(target, method, args))
    ↓ 否 → method.invoke(target, args)
    
Invocation 包含：
    - target：原始对象
    - method：被拦截的方法
    - args：方法参数
```

## 五、自定义插件编写

### 1. SQL 执行时间监控插件

```java
@Intercepts({
    @Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
    )
})
public class SqlTimeInterceptor implements Interceptor {
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 前置：记录开始时间
        long startTime = System.currentTimeMillis();
        
        try {
            // 执行原始方法
            return invocation.proceed();
        } finally {
            // 后置：计算耗时
            long cost = System.currentTimeMillis() - startTime;
            StatementHandler handler = (StatementHandler) invocation.getTarget();
            BoundSql boundSql = handler.getBoundSql();
            String sql = boundSql.getSql();
            
            if (cost > 1000) {
                log.warn("慢SQL [{}ms]: {}", cost, sql);
            } else {
                log.info("SQL [{}ms]: {}", cost, sql);
            }
        }
    }
    
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
```

### 2. 注册插件

**方式一：mybatis-config.xml**

```xml
<plugins>
    <plugin interceptor="com.example.plugin.SqlTimeInterceptor">
        <property name="slowThreshold" value="1000"/>
    </plugin>
</plugins>
```

**方式二：Spring Boot 配置**

```java
@Configuration
public class MyBatisConfig {
    
    @Bean
    public SqlTimeInterceptor sqlTimeInterceptor() {
        return new SqlTimeInterceptor();
    }
}
```

### 3. 分页插件示例

```java
@Intercepts({
    @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
    )
})
public class PageInterceptor implements Interceptor {
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        RowBounds rowBounds = (RowBounds) args[2];
        
        if (rowBounds != RowBounds.DEFAULT) {
            // 有分页参数，改写 SQL
            MappedStatement ms = (MappedStatement) args[0];
            BoundSql boundSql = ms.getBoundSql(args[1]);
            String originalSql = boundSql.getSql();
            
            // 拼接分页 SQL
            String pageSql = originalSql + " LIMIT " 
                + rowBounds.getOffset() + ", " + rowBounds.getLimit();
            
            // 反射修改 SQL
            Field field = boundSql.getClass().getDeclaredField("sql");
            field.setAccessible(true);
            field.set(boundSql, pageSql);
            
            // 清除 RowBounds（避免 MyBatis 内存分页）
            args[2] = RowBounds.DEFAULT;
        }
        
        return invocation.proceed();
    }
}
```

## 六、多插件执行顺序

### 1. 代理嵌套结构

```
假设配置了3个插件：A、B、C

创建代理过程（按配置顺序）：
  target → A.plugin(target) → proxyA
  proxyA → B.plugin(proxyA) → proxyB
  proxyB → C.plugin(proxyB) → proxyC

最终执行时（洋葱模型）：
  C.intercept() 
    → B.intercept() 
      → A.intercept() 
        → target.method()
      ← A
    ← B
  ← C
```

**结论**：配置在前面的插件，代理在最内层，拦截时最晚执行。配置在后面的插件，代理在最外层，拦截时最先执行。

### 2. 示例验证

```
配置顺序：PluginA → PluginB

代理结构：target → A代理 → B代理（最外层）

调用流程：
  1. B.intercept() 先执行
  2. B.proceed() → A.intercept() 
  3. A.proceed() → target.method()
  
执行顺序：B → A → target → A返回 → B返回
```

### 3. 注意事项

- 插件执行顺序与配置顺序相反（最外层先执行）
- 修改 SQL 的插件应放在最内层（最先配置）
- 监控类插件应放在最外层（最后配置）
- 多个插件修改同一方法时，注意互相影响

## 七、常见面试追问

### Q1：MyBatis 插件能拦截哪些方法？

四大核心对象的所有公开方法都可以拦截，常用的有：
- Executor：update、query、commit、rollback
- StatementHandler：prepare、parameterize、batch、update、query
- ParameterHandler：setParameters、getParameterObject
- ResultSetHandler：handleResultSets、handleOutputParameters

### Q2：插件和 Spring AOP 有什么区别？

| 对比项 | MyBatis 插件 | Spring AOP |
|-------|-------------|------------|
| 实现方式 | JDK 动态代理 | JDK/CGLIB 代理 |
| 作用范围 | 仅四大核心对象 | Spring 容器中所有 Bean |
| 配置方式 | @Intercepts + @Signature | @Aspect + 切点表达式 |
| 拦截粒度 | 精确到方法签名 | 切点表达式匹配 |
| 适用场景 | SQL 拦截改写 | 通用切面逻辑 |

### Q3：如何控制插件的执行顺序？

MyBatis 原生不提供顺序控制，按配置顺序层层代理。可以通过自定义 InterceptorChain 或在 Spring 中使用 @Order 注解控制 Bean 注册顺序。

## 八、总结

```
记住三个核心要点：

1. 四大核心对象
   Executor（执行器）、StatementHandler（语句处理器）
   ParameterHandler（参数处理器）、ResultSetHandler（结果集处理器）
   每个对象创建后都经过 interceptorChain.pluginAll() 代理

2. 插件原理
   JDK 动态代理 → Plugin.wrap() → Proxy.newProxyInstance()
   invoke() 检查 signatureMap → 命中则调用 interceptor.intercept()
   Invocation 封装 target + method + args

3. 多插件执行顺序
   配置顺序 A→B→C，代理结构 C→B→A（洋葱模型）
   最外层先执行，最内层最后执行
   修改SQL的插件先配置，监控类插件后配置
```

**面试回答模板**：

> MyBatis 插件基于 JDK 动态代理实现，拦截四大核心对象：Executor、StatementHandler、ParameterHandler、ResultSetHandler。每个对象创建后经过 InterceptorChain.pluginAll() 逐层代理包装。
>
> 核心原理是 Plugin.wrap() 通过 @Intercepts 和 @Signature 注解获取要拦截的方法签名，用 Proxy.newProxyInstance() 创建代理对象。调用被拦截方法时，Plugin.invoke() 检查 signatureMap，命中则调用 Interceptor.intercept()，传入 Invocation 对象（包含目标对象、方法、参数），通过 invocation.proceed() 执行原始逻辑。
>
> 多个插件按配置顺序层层代理，形成洋葱模型：后配置的插件在最外层，先拦截；先配置的插件在最内层，后拦截。修改 SQL 的插件应先配置，监控类插件应后配置。
