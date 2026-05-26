---
layout: post
category: interview
title: 面经手册 · 第39篇《MyBatis 缓存机制全解析：一级缓存、二级缓存、清理策略和 Redis 整合》
tagline: by 小傅哥
tag: [java,interview]
excerpt: MyBatis 缓存机制是面试超高频考点。一级缓存 SqlSession 级别默认开启，二级缓存 Mapper 级别需要手动开启。但二级缓存为什么默认关闭？缓存什么时候会被清理？和 Redis 整合怎么做？本文从缓存原理到源码，把缓存机制彻底讲透。
lock: need
---

# 面经手册 · 第39篇《MyBatis 缓存机制全解析：一级缓存、二级缓存、清理策略和 Redis 整合》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

缓存，是 MyBatis 面试中几乎必问的考点。一级缓存、二级缓存、缓存清理、缓存策略……知识点多且细。

很多候选人能说出"一级缓存默认开、二级缓存默认关"，但被追问"一级缓存什么时候失效"、"二级缓存为什么默认关闭"、"多表关联查询缓存数据不一致怎么办"，就答不上来了。

本文从缓存原理到源码，把 MyBatis 缓存机制彻底讲透。

## 二、面试题

`谢飞机，小记！`，面试继续。

**面试官**：MyBatis 有一级缓存和二级缓存，能说说区别吗？

**谢飞机**：一级缓存 SqlSession 级别默认开启，二级缓存 Mapper 级别需要手动开启。

**面试官**：一级缓存什么时候会失效？

**谢飞机**：SqlSession 关闭？

**面试官**：还有呢？在同一个 SqlSession 中，什么操作会清空一级缓存？

**谢飞机**：增删改？

**面试官**：对。那二级缓存为什么默认关闭？

**谢飞机**：因为……数据一致性问题？

**面试官**：具体说说什么问题？

**谢飞机**：多表关联查询时缓存数据可能不一致？

**面试官**：那和 Redis 整合怎么做？和 MyBatis 自带的二级缓存有什么区别？

**谢飞机**：这个我真没做过……

**面试官**：好吧，你回去研究下。下一个！

## 三、一级缓存（Local Cache）

### 1. 基本概念

一级缓存是 SqlSession 级别的缓存，默认开启，不可关闭（可以设为 STATEMENT 级别）。

```
SqlSession1            SqlSession2
┌──────────┐          ┌──────────┐
│ 一级缓存  │          │ 一级缓存  │
│ (独立)    │          │ (独立)    │
└──────────┘          └──────────┘
   互不影响               互不影响
```

### 2. 缓存验证

```java
SqlSession sqlSession = sqlSessionFactory.openSession();
UserMapper mapper = sqlSession.getMapper(UserMapper.class);

// 第一次查询：查数据库
User user1 = mapper.findById(1);

// 第二次查询：命中一级缓存，不查数据库
User user2 = mapper.findById(1);

System.out.println(user1 == user2);  // true，同一个对象！
```

### 3. 缓存 Key 的构成

```java
// org.apache.ibatis.executor.BaseExecutor.createCacheKey()
CacheKey cacheKey = new CacheKey();
cacheKey.update(ms.getId());                    // MappedStatement ID
cacheKey.update(rowBounds.getOffset());          // 分页偏移
cacheKey.update(rowBounds.getLimit());           // 分页限制
cacheKey.update(boundSql.getSql());              // SQL 语句
// 遍历参数值
for (ParameterMapping pm : boundSql.getParameterMappings()) {
    cacheKey.update(value);
}
cacheKey.update(environment.getId());            // 环境 ID
```

**CacheKey 由以下因素决定**：Statement ID + 分页参数 + SQL + 参数值 + 环境 ID。任何一个不同，CacheKey 就不同。

### 4. 一级缓存失效场景

| 场景 | 是否失效 | 说明 |
|------|---------|------|
| SqlSession 关闭/新建 | ✅ 失效 | 缓存随 SqlSession 生死 |
| 执行 INSERT/UPDATE/DELETE | ✅ 失效 | 清空该 SqlSession 的所有缓存 |
| 手动 clearCache() | ✅ 失效 | sqlSession.clearCache() |
| 同一查询但参数不同 | ✅ 不命中 | CacheKey 不同 |
| 同一查询但 RowBounds 不同 | ✅ 不命中 | CacheKey 不同 |
| flushCache=true | ✅ 失效 | Statement 级别配置 |

### 5. 一级缓存作用范围

```xml
<!-- mybatis-config.xml -->
<settings>
    <!-- SESSION：缓存作用整个 SqlSession（默认） -->
    <!-- STATEMENT：缓存仅作用当前 Statement，执行完即清 -->
    <setting name="localCacheScope" value="SESSION"/>
</settings>
```

### 6. 源码追踪

```java
// org.apache.ibatis.executor.BaseExecutor
public abstract class BaseExecutor implements Executor {
    protected PerpetualCache localCache;  // ← 一级缓存
    
    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, ...) {
        BoundSql boundSql = ms.getBoundSql(parameter);
        CacheKey key = createCacheKey(ms, parameter, rowBounds, boundSql);
        return query(ms, parameter, rowBounds, resultHandler, key, boundSql);
    }
    
    @Override
    public <E> List<E> query(..., CacheKey key, BoundSql boundSql) {
        // 1. 先查一级缓存
        List<E> list = localCache.getObject(key);
        if (list != null) {
            // 缓存命中
            return list;
        }
        // 2. 缓存未命中，查数据库
        list = queryFromDatabase(ms, parameter, rowBounds, resultHandler, key, boundSql);
        return list;
    }
    
    private <E> List<E> queryFromDatabase(...) {
        localCache.putObject(key, EXECUTION_PLACEHOLDER);  // 占位
        try {
            list = doQuery(ms, parameter, rowBounds, resultHandler, boundSql);
        } finally {
            localCache.removeObject(key);
        }
        localCache.putObject(key, list);  // 写入缓存
        return list;
    }
    
    @Override
    public int update(MappedStatement ms, Object parameter) {
        clearLocalCache();  // ← 增删改操作清空一级缓存
        return doUpdate(ms, parameter);
    }
}
```

## 四、二级缓存（Second Level Cache）

### 1. 基本概念

二级缓存是 Mapper（namespace）级别的缓存，多个 SqlSession 共享，需要手动开启。

```
SqlSession1 ──┐
              ├──→ 二级缓存（共享）──→ 数据库
SqlSession2 ──┘
```

### 2. 开启步骤

**第一步：全局开关**（默认 true，一般不需要改）

```xml
<settings>
    <setting name="cacheEnabled" value="true"/>
</settings>
```

**第二步：Mapper XML 中声明**（必须）

```xml
<!-- UserMapper.xml -->
<mapper namespace="com.example.mapper.UserMapper">
    <!-- 简单声明 -->
    <cache/>
    
    <!-- 或带参数 -->
    <cache eviction="LRU" flushInterval="60000" size="1024" readOnly="true"/>
</mapper>
```

**第三步：实体类实现 Serializable**

```java
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    // ...
}
```

### 3. 缓存参数

| 参数 | 说明 | 默认值 |
|------|------|--------|
| eviction | 清理策略 | LRU |
| flushInterval | 刷新间隔（毫秒） | 无（不刷新） |
| size | 最大缓存对象数 | 1024 |
| readOnly | 只读 | false |

### 4. 二级缓存执行流程

```
查询请求
  ↓
1. 先查二级缓存（CachingExecutor）
  ↓ 命中 → 直接返回
2. 再查一级缓存（BaseExecutor）
  ↓ 命中 → 返回 + 写入二级缓存
3. 查数据库
  ↓
4. 结果写入一级缓存
5. SqlSession close/commit 时 → 一级缓存数据写入二级缓存
```

**关键**：二级缓存的数据是在 SqlSession 关闭或提交时才写入的，不是查询时立即写入。

### 5. 源码追踪

```java
// org.apache.ibatis.executor.CachingExecutor
public class CachingExecutor implements Executor {
    private final Executor delegate;
    private final TransactionalCacheManager tcm;
    
    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, ...) {
        Cache cache = ms.getCache();  // ← 二级缓存
        if (cache != null) {
            flushCacheIfRequired(ms);
            if (ms.isUseCache() && resultHandler == null) {
                CacheKey key = createCacheKey(ms, parameter, rowBounds, boundSql);
                // 查二级缓存
                List<E> list = tcm.getObject(cache, key);
                if (list == null) {
                    // 未命中，交给 delegate（BaseExecutor）查一级缓存 + 数据库
                    list = delegate.query(ms, parameter, rowBounds, resultHandler, key, boundSql);
                    // 放入待提交缓存
                    tcm.putObject(cache, key, list);
                }
                return list;
            }
        }
        return delegate.query(ms, parameter, rowBounds, resultHandler, key, boundSql);
    }
}
```

### 6. TransactionalCache 事务缓存

```java
// 二级缓存使用事务缓存，commit 时才真正写入
public class TransactionalCache implements Cache {
    private final Cache delegate;                    // 真正的缓存
    private final Map<Object, Object> entriesToAddOnCommit;  // 待提交
    
    @Override
    public void putObject(Object key, Object value) {
        entriesToAddOnCommit.put(key, value);  // 先放待提交区
    }
    
    public void commit() {
        // commit 时批量写入真正的缓存
        for (Map.Entry<Object, Object> entry : entriesToAddOnCommit.entrySet()) {
            delegate.putObject(entry.getKey(), entry.getValue());
        }
        entriesToAddOnCommit.clear();
    }
    
    public void rollback() {
        entriesToAddOnCommit.clear();  // 回滚时清空待提交区
    }
}
```

## 五、缓存清理策略

### 1. 四种清理策略

| 策略 | 说明 | 适用场景 |
|------|------|---------|
| LRU（默认） | 最近最少使用 | 通用 |
| FIFO | 先进先出 | 按时间顺序 |
| SOFT | 软引用，内存不足时回收 | 内存敏感 |
| WEAK | 弱引用，GC 时回收 | 缓存时间短 |

### 2. 缓存清理时机

```
一级缓存清理：
  - 执行 INSERT/UPDATE/DELETE → clearLocalCache()
  - 手动 sqlSession.clearCache()
  - SqlSession 关闭
  - localCacheScope=STATEMENT 时每次执行完清理

二级缓存清理：
  - 执行 INSERT/UPDATE/DELETE → flushCacheIfRequired(ms)
  - flushInterval 到期自动清理
  - size 达到上限按 eviction 策略清理
  - namespace 整体清空
```

### 3. Statement 级别的 flushCache

```xml
<!-- 每次 select 执行前清空缓存 -->
<select id="findById" flushCache="true" ...>

<!-- 增删改默认 flushCache=true -->
<insert id="insert" ...>
<update id="update" ...>
<delete id="delete" ...>
```

## 六、二级缓存为什么默认关闭

### 1. 数据一致性风险

```
场景：user 表和 order 表关联

SqlSession1: 查询 user(1) → 缓存到 UserMapper 二级缓存
SqlSession2: 修改 user(1) 的 name → UserMapper 二级缓存已清空
SqlSession3: 查询 order 关联 user(1) → OrderMapper 二级缓存有旧数据
  ❌ user 数据不一致！
```

**核心问题**：二级缓存是 namespace 级别，跨 namespace 的关联查询无法保证一致性。

### 2. 序列化问题

二级缓存存储的数据需要序列化，实体类必须实现 Serializable。缓存的不是对象引用，而是对象的序列化副本——读取时是**不同的对象实例**。

### 3. 细粒度控制不足

无法对某个查询条件精细控制缓存失效，只能整个 namespace 一起清。

## 七、与 Redis 整合

### 1. 为什么用 Redis

```
MyBatis 自带二级缓存的问题：
  ❌ 单机内存，分布式环境不共享
  ❌ 重启丢失
  ❌ namespace 粒度粗

Redis 解决：
  ✅ 分布式共享
  ✅ 持久化
  ✅ 更灵活的过期策略
```

### 2. 整合方式

**第一步：引入依赖**

```xml
<dependency>
    <groupId>org.mybatis.caches</groupId>
    <artifactId>mybatis-redis</artifactId>
    <version>1.0.0-beta2</version>
</dependency>
```

**第二步：Mapper XML 配置**

```xml
<mapper namespace="com.example.mapper.UserMapper">
    <cache type="org.mybatis.caches.redis.RedisCache"/>
</mapper>
```

**第三步：redis.properties 配置**

```properties
redis.host=localhost
redis.port=6379
redis.password=
redis.database=0
redis.timeout=3000
```

### 3. 自定义 Redis Cache

```java
public class CustomRedisCache implements Cache {
    private final String id;
    private RedisTemplate<String, Object> redisTemplate;
    
    public CustomRedisCache(String id) {
        this.id = id;
        this.redisTemplate = SpringContextHolder.getBean("redisTemplate");
    }
    
    @Override
    public String getId() { return id; }
    
    @Override
    public void putObject(Object key, Object value) {
        String cacheKey = id + ":" + key.toString();
        redisTemplate.opsForValue().set(cacheKey, value, 30, TimeUnit.MINUTES);
    }
    
    @Override
    public Object getObject(Object key) {
        String cacheKey = id + ":" + key.toString();
        return redisTemplate.opsForValue().get(cacheKey);
    }
    
    @Override
    public Object removeObject(Object key) {
        String cacheKey = id + ":" + key.toString();
        redisTemplate.delete(cacheKey);
        return null;
    }
    
    @Override
    public void clear() {
        Set<String> keys = redisTemplate.keys(id + ":*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
    
    @Override
    public int getSize() {
        Set<String> keys = redisTemplate.keys(id + ":*");
        return keys == null ? 0 : keys.size();
    }
}
```

## 八、常见面试追问

### Q1：一级缓存能关闭吗？

不能完全关闭，但可以设置 `localCacheScope=STATEMENT`，让缓存只在当前 Statement 有效，执行完即清。效果上等同于关闭。

### Q2：一级缓存返回的对象是同一个吗？

是的。一级缓存存储的是对象引用，同一个 SqlSession 内多次查询返回的是**同一个对象实例**（== 为 true）。所以修改返回对象会影响缓存中的数据。

### Q3：二级缓存返回的对象是同一个吗？

不是。readOnly=false（默认）时，二级缓存通过序列化/反序列化存储，返回的是**不同的对象实例**。readOnly=true 时则返回同一引用，但修改会影响缓存。

### Q4：多表关联查询怎么处理二级缓存？

最佳实践：关联查询涉及的 Mapper 使用同一个 namespace（refid），或者只在读多写少且变更可控的场景使用二级缓存。分布式环境推荐直接用 Redis。

## 九、总结

```
记住三个核心要点：

1. 一级缓存（SqlSession 级别）
   默认开启，不可关闭（可设 STATEMENT 范围）
   增删改操作清空，SqlSession 关闭失效
   存储对象引用，返回同一个实例

2. 二级缓存（Mapper namespace 级别）
   手动开启，commit 时才写入
   跨 SqlSession 共享，但跨 namespace 不保证一致性
   默认关闭原因：数据一致性风险 + 序列化问题

3. 缓存选择建议
   单机 + 读多写少 → 一级缓存 + 二级缓存
   分布式环境 → 一级缓存 + Redis
   强一致性要求 → 只用一级缓存或不用缓存
```

**面试回答模板**：

> MyBatis 有一级缓存和二级缓存。一级缓存是 SqlSession 级别，默认开启，增删改操作会清空，SqlSession 关闭后失效，存储对象引用。二级缓存是 Mapper namespace 级别，需要手动开启，SqlSession commit 时才写入，通过序列化存储。
>
> 二级缓存默认关闭，核心原因是跨 namespace 的关联查询无法保证缓存一致性。比如用户表和订单表分属不同 namespace，用户数据更新后订单关联缓存可能还是旧数据。
>
> 分布式环境下，建议用 Redis 替代 MyBatis 自带二级缓存，实现自定义 Cache 接口，通过 RedisTemplate 管理缓存数据的读写和过期。
