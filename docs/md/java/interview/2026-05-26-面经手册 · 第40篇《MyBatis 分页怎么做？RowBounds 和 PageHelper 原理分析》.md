---
layout: post
category: interview
title: 面经手册 · 第40篇《MyBatis 分页怎么做？RowBounds 和 PageHelper 原理分析》
tagline: by 小傅哥
tag: [java,interview]
excerpt: MyBatis 分页是日常开发必备功能，但逻辑分页和物理分页的区别、RowBounds 的内存截取原理、PageHelper 的拦截器改写 SQL 机制——这些你真的搞透了吗？本文从源码级别拆解 RowBounds 的 handleRowValues → skipRows 全链路，以及 PageHelper 的 PageInterceptor → ThreadLocal → Dialect 改写 SQL 的完整流程，面试不再只停留在"用过 PageHelper"。
lock: need
---

# 面经手册 · 第40篇《MyBatis 分页怎么做？RowBounds 和 PageHelper 原理分析》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

分页，是日常开发中几乎每个列表接口都绕不开的功能。前端传 pageNum 和 pageSize，后端返回对应页的数据和总条数——这套流程写多了，觉得分页不过如此。

但面试官不这么想。一道" MyBatis 分页怎么做"，就能区分出候选人到底是"会用"还是"真懂"：你知道 RowBounds 是逻辑分页吗？逻辑分页和物理分页的区别是什么？RowBounds 在大数据量下有什么问题？PageHelper 的分页原理是什么？它怎么做到不改业务代码就自动加 LIMIT 的？ThreadLocal 在里面起了什么作用？

本文从逻辑分页的 RowBounds 源码出发，到物理分页的 PageHelper 拦截器全链路追踪，把 MyBatis 分页的底层原理彻底讲透。

## 二、面试题

`谢飞机，小记！`，最近项目里一直在用 PageHelper 分页，觉得分页这块稳了，信心满满来面试。

**面试官**：谢飞机，你们项目分页怎么做的？

**谢飞机**：用的 PageHelper，startPage 然后查一下就好了。

**面试官**：那你知道 PageHelper 的分页原理是什么吗？

**谢飞机**：嗯... 它会自动加上 LIMIT？

**面试官**：怎么加的？它为什么能自动改写你的 SQL？

**谢飞机**：拦截器？...

**面试官**：对，MyBatis 的 Interceptor 机制。那 RowBounds 你了解吗？

**谢飞机**：RowBounds... 好像是 MyBatis 自带的分页？

**面试官**：RowBounds 是逻辑分页还是物理分页？

**谢飞机**：... 物理分页？

**面试官**：不对。RowBounds 是逻辑分页——SQL 查出全部数据，再在内存里截取。你想想，数据量大的时候会怎样？

**谢飞机**：...OOM？

**面试官**：对。那你清楚 PageHelper 是怎么用 ThreadLocal 传递分页参数的吗？为什么强调 startPage 要紧跟查询方法？

**谢飞机**：这个... 我只知道要紧跟，不太清楚为什么。

**面试官**：好吧。那分页查询总数怎么优化？深分页性能问题怎么解决？

**谢飞机**：我... 再见！ヾ(￣▽￣)

## 三、逻辑分页与物理分页

### 1. 两种分页的本质区别

```
逻辑分页（RowBounds）：
  SQL: SELECT * FROM user          ← 查出全部数据
  Java 内存: 跳过 offset 行，取 limit 行  ← 内存截取
  数据库不知道你在分页，它以为你要所有数据

物理分页（LIMIT）：
  SQL: SELECT * FROM user LIMIT 10 OFFSET 20  ← 只查需要的数据
  数据库: 只返回第 21~30 行
  数据库层面就完成了分页
```

### 2. 核心对比

| 对比项 | 逻辑分页（RowBounds） | 物理分页（LIMIT） |
|-------|----------------------|-------------------|
| SQL 语句 | 不改写，查出全部 | 加 LIMIT/OFFSET |
| 分页位置 | Java 内存 | 数据库 |
| 数据库负担 | 重（全量查询） | 轻（按需查询） |
| 内存占用 | 高（全量结果集） | 低（只存当页数据） |
| 大数据量 | ❌ OOM 风险 | ✅ 无问题 |
| 数据实时性 | 查询后内存中分页，后续变更不可见 | 每次重新查询，实时 |
| 适用场景 | 数据量小的场景 | 生产环境推荐 |

**一句话总结**：逻辑分页是"先全查再截"，物理分页是"只查需要的"。生产环境应该用物理分页。

## 四、RowBounds 逻辑分页源码分析

### 1. RowBounds 的使用方式

```java
// RowBounds 构造：offset=20, limit=10 → 取第 21~30 条
List<User> users = sqlSession.selectList("com.example.mapper.UserMapper.findAll",
    null, new RowBounds(20, 10));
```

```java
// RowBounds 定义
public class RowBounds {
    public static final int NO_ROW_OFFSET = 0;
    public static final int NO_ROW_LIMIT = Integer.MAX_VALUE;
    public static final RowBounds DEFAULT = new RowBounds();

    private final int offset;
    private final int limit;

    public RowBounds() {
        this.offset = NO_ROW_OFFSET;
        this.limit = NO_ROW_LIMIT;
    }

    public RowBounds(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }
    // getter 省略
}
```

可以看到，RowBounds 默认 limit 是 `Integer.MAX_VALUE`，也就是不分页——查出全部数据。

### 2. 执行链路追踪

```
SqlSession.selectList()
  → Executor.query()
    → ResultSetHandler.handleResultSet()  ← 结果集处理
      → DefaultResultSetHandler.handleRowValues()
        → skipRows()    ← 跳过 offset 行
        → 逐行读取 limit 行
```

### 3. 核心源码：DefaultResultSetHandler

**步骤一：handleRowValues 入口**

```java
// org.apache.ibatis.executor.resultset.DefaultResultSetHandler
@Override
public void handleRowValues(ResultSetWrapper rsw, ResultMap resultMap,
        ResultHandler<?> resultHandler, RowBounds rowBounds, ResultMapping parentMapping) throws SQLException {
    if (resultMap.hasNestedResultMaps()) {
        ensureNoRowBounds();
        checkResultHandler();
        handleRowValuesForNestedResultMap(rsw, resultMap, resultHandler, rowBounds, parentMapping);
    } else {
        handleRowValuesForSimpleResultMap(rsw, resultMap, resultHandler, rowBounds, parentMapping);
    }
}
```

**步骤二：简单结果集处理**

```java
private void handleRowValuesForSimpleResultMap(ResultSetWrapper rsw, ResultMap resultMap,
        ResultHandler<?> resultHandler, RowBounds rowBounds, ResultMapping parentMapping) throws SQLException {
    DefaultResultContext<Object> resultContext = new DefaultResultContext<>();
    ResultSet resultSet = rsw.getResultSet();

    // ① 跳过 offset 行
    skipRows(resultSet, rowBounds);

    // ② 逐行读取，直到 limit 行或结果集结束
    while (shouldProcessMoreRows(resultContext, rowBounds) && !resultSet.isClosed()) {
        ResultMap discriminatedResultMap = resolveDiscriminatedResultMap(resultSet, resultMap, null);
        Object rowValue = getRowValue(rsw, discriminatedResultMap, null);
        storeObject(resultHandler, resultContext, rowValue, parentMapping, resultSet);
    }
}
```

**步骤三：skipRows — 内存跳行**

```java
private void skipRows(ResultSet rs, RowBounds rowBounds) throws SQLException {
    if (rowBounds.getOffset() != RowBounds.NO_ROW_OFFSET) {
        // 根据数据库类型决定跳行方式
        if (rs.getType() != ResultSet.TYPE_FORWARD_ONLY) {
            // 可滚动的结果集，直接 absolute 定位
            rs.absolute(rowBounds.getOffset());
        } else {
            // 只进结果集，一行一行跳
            for (int i = 0; i < rowBounds.getOffset(); i++) {
                if (!rs.next()) {
                    return;
                }
            }
        }
    }
}
```

**步骤四：shouldProcessMoreRows — 限制行数**

```java
private boolean shouldProcessMoreRows(ResultContext<?> context, RowBounds rowBounds) {
    // 判断是否已经取够了 limit 行
    return !context.isStopped() && context.getResultCount() < rowBounds.getLimit();
}
```

### 4. RowBounds 的问题

```
假设数据库有 100 万条用户数据：

RowBounds(999990, 10)  → 取最后 10 条

执行过程：
1. SQL: SELECT * FROM user           ← 查出 100 万条数据！
2. skipRows: 跳过前 999990 行        ← 消耗大量时间
3. 读取 10 行                        ← 只需要这 10 条
4. 剩余数据丢弃                       ← 浪费 99.99% 的数据

问题：
- 数据库返回 100 万条数据的网络传输开销
- JDBC ResultSet 存放 100 万条数据的内存开销
- 跳行 999990 次的 CPU 开销
- 数据量更大时直接 OOM
```

**结论**：RowBounds 逻辑分页只适合小数据量场景（如配置表、字典表），生产环境应使用物理分页。

## 五、物理分页的基本原理

物理分页的核心是**在 SQL 层面加 LIMIT 子句**，让数据库只返回需要的数据：

```sql
-- MySQL
SELECT * FROM user LIMIT 10 OFFSET 20;      -- 第 21~30 条
SELECT * FROM user LIMIT 20, 10;             -- 同上，offset, size

-- Oracle
SELECT * FROM (
    SELECT t.*, ROWNUM rn FROM user t WHERE ROWNUM <= 30
) WHERE rn > 20;

-- PostgreSQL
SELECT * FROM user LIMIT 10 OFFSET 20;

-- SQL Server
SELECT * FROM user ORDER BY id
OFFSET 20 ROWS FETCH NEXT 10 ROWS ONLY;
```

不同数据库的分页语法不同，这是 PageHelper 需要"方言"（Dialect）的原因。

## 六、PageHelper 分页插件原理分析

### 1. PageHelper 的基本使用

```java
// 第一步：设置分页参数
PageHelper.startPage(1, 10);  // 第 1 页，每页 10 条

// 第二步：紧跟查询方法（中间不能有其他查询！）
List<User> users = userMapper.findAll();

// 第三步：获取分页信息
PageInfo<User> pageInfo = new PageInfo<>(users);
System.out.println("总条数: " + pageInfo.getTotal());
System.out.println("总页数: " + pageInfo.getPages());
System.out.println("当前页数据: " + pageInfo.getList());
```

### 2. 整体架构

```
PageHelper 分页流程：

1. PageHelper.startPage(pageNum, pageSize)
   → 创建 Page 对象 → 存入 ThreadLocal

2. MyBatis 执行 SQL
   → Executor.query() 被 PageInterceptor 拦截

3. PageInterceptor.intercept()
   → 从 ThreadLocal 取出 Page 对象
   → 判断是否需要分页（当前方法是否需要分页）

4. 改写 SQL
   → 获取原 SQL: SELECT * FROM user
   → 通过 Dialect 生成 COUNT SQL: SELECT COUNT(*) FROM user
   → 通过 Dialect 生成分页 SQL: SELECT * FROM user LIMIT 10 OFFSET 0

5. 执行改写后的 SQL
   → 先执行 COUNT SQL 获取总条数
   → 再执行分页 SQL 获取当页数据

6. 封装结果
   → 将总条数 + 当页数据封装到 Page 对象
   → 清除 ThreadLocal 中的分页参数
```

### 3. 核心源码追踪

**步骤一：startPage — 设置分页参数**

```java
// com.github.pagehelper.PageHelper
public static <E> Page<E> startPage(int pageNum, int pageSize) {
    return startPage(pageNum, pageSize, DEFAULT_COUNT);
}

public static <E> Page<E> startPage(int pageNum, int pageSize, boolean count) {
    return startPage(pageNum, pageSize, count, null, null);
}

protected static <E> Page<E> startPage(int pageNum, int pageSize, boolean count,
        String orderBy, Boolean reasonable) {
    Page<E> page = new Page<>(pageNum, pageSize, count);
    page.setOrderBy(orderBy);
    page.setReasonable(reasonable);
    // 存入 ThreadLocal！
    setLocalPage(page);
    return page;
}

public static void setLocalPage(Page page) {
    LOCAL_PAGE.set(page);  // LOCAL_PAGE 是 ThreadLocal<Page>
}
```

**关键**：PageHelper 用 `ThreadLocal` 存储分页参数，保证了线程安全——每个线程的分页参数互不干扰。

**步骤二：PageInterceptor — 拦截 Executor.query()**

```java
// com.github.pagehelper.PageInterceptor
@Intercepts({
    @Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class PageInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取拦截方法的参数
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];

        // 从 ThreadLocal 获取分页参数
        Page page = PageHelper.getLocalPage();

        if (page != null) {
            // 需要分页
            // ① 获取原 SQL
            BoundSql boundSql = ms.getBoundSql(parameter);
            String originalSql = boundSql.getSql();

            // ② 执行 COUNT 查询（获取总条数）
            long count = executeCount(ms, parameter, boundSql, page);

            // ③ 改写 SQL，添加 LIMIT
            String pageSql = getPageSql(page, originalSql);

            // ④ 执行分页 SQL
            List result = executePageQuery(ms, parameter, pageSql, boundSql, page);

            // ⑤ 封装结果到 Page 对象
            page.setTotal(count);
            page.addAll(result);

            // ⑥ 清除 ThreadLocal
            PageHelper.clearPage();

            return page;
        }

        // 不分页，直接执行原方法
        return invocation.proceed();
    }
}
```

**步骤三：Dialect — SQL 改写**

```java
// com.github.pagehelper.dialect.AbstractHelperDialect
@Override
public String getPageSql(String sql, Page page, RowBounds rowBounds) {
    // 委托给具体数据库方言实现
    return getPageSql(sql, page);
}

// com.github.pagehelper.dialect.helper.MySqlDialect
@Override
public String getPageSql(String sql, Page page) {
    StringBuilder sqlBuilder = new StringBuilder(sql.length() + 14);
    sqlBuilder.append(sql);
    if (page.getStartRow() == 0) {
        // LIMIT pageSize
        sqlBuilder.append(" LIMIT ");
        sqlBuilder.append(page.getPageSize());
    } else {
        // LIMIT offset, pageSize
        sqlBuilder.append(" LIMIT ");
        sqlBuilder.append(page.getStartRow());
        sqlBuilder.append(",");
        sqlBuilder.append(page.getPageSize());
    }
    return sqlBuilder.toString();
}
```

**实际效果**：

```sql
-- 原 SQL
SELECT * FROM user WHERE status = 1 ORDER BY create_time DESC

-- 改写后的分页 SQL
SELECT * FROM user WHERE status = 1 ORDER BY create_time DESC LIMIT 0,10

-- 同时执行的 COUNT SQL
SELECT COUNT(*) FROM user WHERE status = 1
```

### 4. PageHelper 的 Interceptor 注册

PageHelper 通过 MyBatis 的插件机制注册拦截器，在 `mybatis-config.xml` 或 Spring 配置中：

```xml
<!-- mybatis-config.xml -->
<plugins>
    <plugin interceptor="com.github.pagehelper.PageInterceptor">
        <!-- 数据库方言 -->
        <property name="helperDialect" value="mysql"/>
        <!-- 合理化分页参数 -->
        <property name="reasonable" value="true"/>
    </plugin>
</plugins>
```

```xml
<!-- Spring Boot 配置 -->
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="plugins">
        <array>
            <bean class="com.github.pagehelper.PageInterceptor">
                <property name="properties">
                    <props>
                        <prop key="helperDialect">mysql</prop>
                        <prop key="reasonable">true</prop>
                    </props>
                </property>
            </bean>
        </array>
    </property>
</bean>
```

## 七、PageHelper 使用注意事项

### 1. startPage 必须紧跟查询方法

```java
// ✅ 正确：startPage 紧跟查询
PageHelper.startPage(1, 10);
List<User> users = userMapper.findAll();

// ❌ 错误：中间插入了其他查询
PageHelper.startPage(1, 10);
List<Role> roles = roleMapper.findAll();  // ← 这个查询被分页了！
List<User> users = userMapper.findAll();  // ← 这个反而没被分页
```

**原因**：PageHelper 在 ThreadLocal 中存入分页参数后，下一个查询方法执行时就会被拦截并分页，分页完成后自动清除 ThreadLocal。所以 startPage 只对紧跟着的第一个查询生效。

### 2. 手动清除 ThreadLocal

```java
// 某些异常场景下 ThreadLocal 没被清除，会导致后续查询意外分页
try {
    PageHelper.startPage(1, 10);
    List<User> users = userMapper.findAll();
} catch (Exception e) {
    // 异常时 PageHelper 可能没来得及清除 ThreadLocal
    PageHelper.clearPage();  // ← 手动清除
    throw e;
}
```

### 3. 不支持 for 循环中分页

```java
// ❌ 错误：循环中 startPage
for (int i = 1; i <= 10; i++) {
    PageHelper.startPage(i, 10);
    List<User> users = userMapper.findAll();
    // 处理逻辑...
}

// ✅ 正确：每次循环独立分页
for (int i = 1; i <= 10; i++) {
    PageHelper.startPage(i, 10);
    List<User> users = userMapper.findAll();
    PageInfo<User> pageInfo = new PageInfo<>(users);
    // 处理逻辑... ThreadLocal 已被自动清除
}
```

### 4. 自定义物理分页实现

如果你不想用 PageHelper，也可以自己实现物理分页，核心思路相同——用 MyBatis Interceptor 拦截 SQL 并改写：

```java
@Intercepts({
    @Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MyPageInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];

        // 判断是否需要分页（自定义注解或参数标记）
        PageParam pageParam = extractPageParam(parameter);
        if (pageParam == null) {
            return invocation.proceed();
        }

        // 获取原 SQL
        BoundSql boundSql = ms.getBoundSql(parameter);
        String originalSql = boundSql.getSql();

        // 改写 SQL
        String pageSql = originalSql + " LIMIT " + pageParam.getOffset()
            + ", " + pageParam.getPageSize();

        // 通过反射修改 BoundSql 中的 SQL
        reflectSetSql(boundSql, pageSql);

        // 执行 COUNT 查询 + 分页查询
        // ... 封装分页结果

        return invocation.proceed();
    }

    private void reflectSetSql(BoundSql boundSql, String sql) {
        try {
            Field field = boundSql.getClass().getDeclaredField("sql");
            field.setAccessible(true);
            field.set(boundSql, sql);
        } catch (Exception e) {
            throw new RuntimeException("修改 SQL 失败", e);
        }
    }
}
```

**核心步骤**：
1. 实现 `Interceptor` 接口，用 `@Intercepts` 注解标记拦截 `Executor.query()`
2. 从方法参数中获取原 SQL
3. 拼接 LIMIT 子句改写 SQL
4. 通过反射修改 `BoundSql` 中的 SQL 字符串
5. 继续执行改写后的 SQL

## 八、常见面试追问

### Q1：RowBounds 在大数据量下有什么问题？

RowBounds 是逻辑分页，SQL 查出全部数据后，在内存中跳行截取。大数据量下存在三个严重问题：

1. **内存溢出**：JDBC 需要将全部结果集加载到内存，数据量过大直接 OOM
2. **网络传输**：数据库向应用传输全量数据，带宽和时间浪费严重
3. **跳行开销**：skipRows 逐行跳过 offset 行，offset 越大越慢

生产环境应使用物理分页，避免使用 RowBounds。

### Q2：PageHelper 支持哪些数据库方言？

PageHelper 内置了丰富的数据库方言支持：

| 方言类 | 数据库 |
|-------|--------|
| MySqlDialect | MySQL / MariaDB |
| OracleDialect | Oracle |
| PostgreSqlDialect | PostgreSQL |
| SqlServerDialect | SQL Server |
| Db2Dialect | DB2 |
| H2Dialect | H2 |
| HsqldbDialect | HSQLDB |
| SqliteDialect | SQLite |
| InformixDialect | Informix |

也可以通过 `autoRuntimeDialect` 配置让 PageHelper 自动识别数据库类型。

### Q3：分页查询总数怎么优化？

深分页场景下，COUNT 查询和 LIMIT 查询都可能成为性能瓶颈：

**COUNT 优化**：

```sql
-- ① 避免 COUNT(*) 扫描全表
-- 如果有索引覆盖，MySQL 可以走索引扫描
SELECT COUNT(id) FROM user WHERE status = 1;

-- ② 业务允许时用近似值
SHOW TABLE STATUS LIKE 'user';  -- Rows 列是近似值

-- ③ 缓存总数
-- 首次查询 COUNT 后缓存，列表数据变更时刷新
```

**深分页 LIMIT 优化**：

```sql
-- 问题：LIMIT 100000, 10 需要扫描 100010 行再丢弃前 100000 行

-- 优化一：游标分页（推荐）
-- 用上一页最后一条的 ID 作为起始点
SELECT * FROM user WHERE id > 100000 ORDER BY id LIMIT 10;

-- 优化二：子查询延迟关联
SELECT * FROM user
INNER JOIN (SELECT id FROM user ORDER BY create_time LIMIT 100000, 10) t
ON user.id = t.id;

-- 优化三：业务限制
-- 限制最大翻页数（如只允许查看前 100 页）
```

### Q4：PageHelper 的 reasonable 参数有什么用？

`reasonable=true` 开启分页参数合理化：

- pageNum < 1 时，自动设为第 1 页
- pageNum > 总页数时，自动设为最后一页

`reasonable=false`（默认）时不做修正，pageNum 不合法时返回空结果。

### Q5：PageHelper 和 RowBounds 可以一起用吗？

可以，但不推荐。PageHelper 拦截 `Executor.query()` 时，如果检测到 ThreadLocal 中有分页参数，会用物理分页替代 RowBounds 的逻辑分页。PageHelper 内部会创建一个新的 `RowBounds.DEFAULT` 来覆盖原始的 RowBounds 参数，避免双重分页。

## 九、总结

```
记住三个核心要点：

1. 逻辑分页 vs 物理分页
   RowBounds 是逻辑分页：SQL 查出全部数据，内存中 skipRows + 限行截取
   物理分页是 SQL 层面加 LIMIT：数据库只返回需要的数据

2. PageHelper 的核心原理 = MyBatis Interceptor + ThreadLocal + Dialect
   startPage → ThreadLocal 存分页参数
   PageInterceptor → 拦截 Executor.query() → 从 ThreadLocal 取参数
   Dialect → 改写 SQL 加 LIMIT → 执行改写后的 SQL → 封装 Page 结果

3. PageHelper 使用注意
   startPage 必须紧跟查询方法（ThreadLocal 只生效一次）
   异常时手动 clearPage 防止 ThreadLocal 泄漏
   深分页用游标分页或子查询延迟关联优化
```

**面试回答模板**：

> MyBatis 分页主要有两种方式：逻辑分页和物理分页。
>
> 逻辑分页用 RowBounds，原理是 SQL 查出全部数据后，在 DefaultResultSetHandler 的 handleRowValues 方法中，通过 skipRows 跳过 offset 行，再用 shouldProcessMoreRows 限制只取 limit 行。这种方式大数据量下会 OOM，不适合生产环境。
>
> 物理分页是在 SQL 层面加 LIMIT 子句，数据库只返回需要的数据。PageHelper 是最常用的物理分页插件，核心原理是 MyBatis 的 Interceptor 机制：PageInterceptor 拦截 Executor.query() 方法，从 ThreadLocal 中取出 startPage 设置的分页参数，通过 Dialect 方言改写 SQL 加 LIMIT，同时生成 COUNT 查询获取总条数，最终封装为 Page 对象返回。
>
> 使用时注意 startPage 要紧跟查询方法，因为 PageHelper 在 ThreadLocal 中存入参数后，下一个查询就会被拦截分页，完成后自动清除。异常场景下要手动调用 clearPage 防止 ThreadLocal 泄漏。深分页场景可以用游标分页或子查询延迟关联优化性能。
