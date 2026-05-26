---
layout: post
category: interview
title: 面经手册 · 第37篇《resultMap 和 resultType 有啥区别？字段名不一致怎么映射？》
tagline: by 小傅哥
tag: [java,interview]
excerpt: MyBatis 结果映射，是连接 SQL 世界和 Java 世界的桥梁。resultType 自动映射，resultMap 手动映射，选哪个？字段名不一致怎么办？结果集是怎么变成 Java 对象的？本文从映射方式到底层源码，把结果映射彻底讲透。
lock: need
---

# 面经手册 · 第37篇《resultMap 和 resultType 有啥区别？字段名不一致怎么映射？》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

SQL 执行完，返回的是 ResultSet——一行行数据。怎么变成 Java 对象？这就是结果映射要干的事。

resultType 和 resultMap 是 MyBatis 结果映射的两种方式，面试常问区别。但大多数候选人只停留在"一个自动一个手动"，被追问结果封装的底层原理就懵了。

本文把结果映射从用法到源码一网打尽。

## 二、面试题

`谢飞机，小记！`，面试继续。

**面试官**：resultMap 和 resultType 的区别是什么？

**谢飞机**：resultType 自动映射，resultMap 手动映射。

**面试官**：字段名和属性名不一致怎么办？

**谢飞机**：用 resultMap 配映射关系。

**面试官**：还有别的方式吗？

**谢飞机**：SQL 里用别名？

**面试官**：嗯，还有呢？MyBatis 的结果集是怎么变成 Java 对象的？

**谢飞机**：反射？

**面试官**：具体点呢？哪个类？哪个方法？

**谢飞机**：ResultSetHandler？

**面试官**：resultMap 中 id 元素的作用是什么？

**谢飞机**：标识主键？

**面试官**：标识主键有什么用？对缓存和嵌套查询有什么影响？

**谢飞机**：……再见！ヾ(￣▽￣)

## 三、resultType 自动映射

### 1. 基本用法

resultType 是最简单的映射方式：MyBatis 按**同名规则**自动将查询结果的列名映射到 Java 对象的属性名。

```xml
<!-- 实体类属性名和表字段名一致时，直接用 resultType -->
<select id="findById" resultType="com.example.entity.User">
    SELECT id, name, age, email FROM user WHERE id = #{id}
</select>
```

```java
// User 类
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    // getter/setter...
}
```

### 2. 映射规则

```
查询结果列名       Java 属性名       是否映射
──────────────────────────────────────────
id              id              ✅ 一致
name            name            ✅ 一致
age             age             ✅ 一致
email           email           ✅ 一致
create_time     createTime      ❌ 不一致（下划线 vs 驼峰）
```

### 3. 自动映射级别

```xml
<!-- mybatis-config.xml -->
<settings>
    <!-- NONE：关闭自动映射 -->
    <!-- PARTIAL：只映射无嵌套的结果（默认） -->
    <!-- FULL：映射所有结果（含嵌套） -->
    <setting name="autoMappingBehavior" value="PARTIAL"/>
</settings>
```

## 四、resultMap 手动映射

### 1. 基本用法

```xml
<!-- 显式指定列名到属性名的映射 -->
<resultMap id="userResultMap" type="com.example.entity.User">
    <id column="id" property="id"/>
    <result column="user_name" property="name"/>
    <result column="age" property="age"/>
    <result column="email" property="email"/>
</resultMap>

<select id="findById" resultMap="userResultMap">
    SELECT id, user_name, age, email FROM user WHERE id = #{id}
</select>
```

### 2. resultMap 子元素

| 元素 | 作用 |
|------|------|
| id | 标识主键字段，影响缓存和嵌套查询结果合并 |
| result | 普通字段映射 |
| constructor | 通过构造方法注入（避免无参构造+setter） |
| association | 一对一关联映射 |
| collection | 一对多关联映射 |
| discriminator | 鉴别器，根据列值决定用哪个 resultMap |

### 3. constructor 元素

```xml
<resultMap id="userResultMap" type="com.example.entity.User">
    <constructor>
        <idArg column="id" javaType="long"/>
        <arg column="user_name" javaType="string"/>
        <arg column="age" javaType="int"/>
    </constructor>
    <result column="email" property="email"/>
</resultMap>
```

```java
// 对应的构造方法
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    
    public User(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
```

### 4. discriminator 鉴别器

```xml
<resultMap id="vehicleResultMap" type="com.example.entity.Vehicle">
    <id column="id" property="id"/>
    <result column="name" property="name"/>
    <discriminator javaType="int" column="vehicle_type">
        <case value="1" resultMap="carResultMap"/>
        <case value="2" resultMap="truckResultMap"/>
    </discriminator>
</resultMap>
```

根据 `vehicle_type` 列的值决定使用哪个 resultMap，实现多态映射。

## 五、字段名不一致的三种解决方式

### 方式一：SQL 别名

```xml
<select id="findById" resultType="com.example.entity.User">
    SELECT id, user_name AS name, age, email 
    FROM user WHERE id = #{id}
</select>
```

**优点**：不改配置，直接在 SQL 中解决
**缺点**：每个 SQL 都要写别名，维护成本高

### 方式二：mapUnderscoreToCamelCase

```xml
<!-- mybatis-config.xml -->
<settings>
    <setting name="mapUnderscoreToCamelCase" value="true"/>
</settings>
```

**效果**：自动将下划线命名转为驼峰命名

```
数据库列名        Java 属性名       是否映射
──────────────────────────────────────────
user_name       userName         ✅ 自动转换
create_time     createTime       ✅ 自动转换
is_deleted      isDeleted        ✅ 自动转换
```

**优点**：全局配置一次，所有 SQL 生效
**缺点**：只能处理下划线→驼峰，其他不一致情况不适用

### 方式三：resultMap 手动映射

```xml
<resultMap id="userResultMap" type="com.example.entity.User">
    <id column="id" property="id"/>
    <result column="user_name" property="name"/>
</resultMap>
```

**优点**：最灵活，任何命名规则都能处理
**缺点**：配置量大，字段多时繁琐

### 选择建议

```
字段名一致 → resultType（简单）
下划线 vs 驼峰 → mapUnderscoreToCamelCase（推荐）
特殊命名规则 → resultMap（兜底）
复杂映射（关联、鉴别器） → resultMap（必须）
```

## 六、结果封装源码解析

### 1. 核心类

```java
// 结果集处理器接口
public interface ResultSetHandler {
    <E> List<E> handleResultSets(Statement stmt);
    void handleOutputParameters(CallableStatement cs);
}

// 默认实现
public class DefaultResultSetHandler implements ResultSetHandler {
    // ...
}
```

### 2. 封装流程

```
Statement.executeQuery()
    ↓
ResultSet（原始结果集）
    ↓
DefaultResultSetHandler.handleResultSets()
    ↓
遍历 ResultSet 的每一行
    ↓
handleRowValues() → handleRowValuesForSimpleResultMap()
    ↓
createResultObject() → 反射创建 Java 对象
    ↓
applyPropertyMappings() → 设置属性值
    ↓
返回 List<Object>
```

### 3. 关键源码

**创建结果对象**：

```java
private Object createResultObject(ResultSetWrapper rsw, ResultMap resultMap, ...) {
    // 1. 有构造器映射 → 用构造方法创建
    if (resultMap.getConstructorResultMappings().size() > 0) {
        return createResultObjectUsingConstructor(rsw, resultMap, ...);
    }
    
    // 2. 有类型处理器 → 用 TypeHandler 创建
    if (typeHandler != null) {
        return typeHandler.getResult(rsw.getResultSet(), columnName);
    }
    
    // 3. 默认 → 反射创建无参实例
    Object resultObject = resultHandler.createResultObject(...);
    return resultObject;
}
```

**属性映射**：

```java
private boolean applyPropertyMappings(ResultSetWrapper rsw, 
        ResultMap resultMap, MetaObject metaObject, ...) {
    List<ResultMapping> propertyMappings = resultMap.getPropertyResultMappings();
    for (ResultMapping propertyMapping : propertyMappings) {
        String propertyName = propertyMapping.getProperty();
        String column = propertyMapping.getColumn();
        // 获取列值
        Object value = getPropertyMappingValue(rsw.getResultSet(), metaObject, ...);
        // 设置属性值（通过反射）
        metaObject.setValue(propertyName, value);
    }
}
```

### 4. id 元素的作用

id 元素不只是标识主键，它还有两个关键作用：

**作用一：嵌套查询结果合并**

```java
// DefaultResultSetHandler.handleRowValuesForNestedResultMap()
// 通过 id 值判断两行数据是否属于同一个对象
CacheKey combinedKey = combineKey(rowValues, resultMap);
Object partialObject = nestedResultObjects.get(combinedKey);
if (partialObject != null) {
    // 已存在，合并关联属性（而非创建新对象）
    applyNestedResultMappings(rsw, resultMap, partialObject, ...);
} else {
    // 新对象
    partialObject = createResultObject(...);
    nestedResultObjects.put(combinedKey, partialObject);
}
```

**作用二：影响一级缓存**

一级缓存的 CacheKey 基于 MappedStatement ID + SQL + 参数值。id 元素标识的主键字段决定了结果对象的唯一性判断。

## 七、resultType 可以映射 Map

```xml
<select id="findById" resultType="java.util.HashMap">
    SELECT id, name, age FROM user WHERE id = #{id}
</select>
```

返回 `Map<String, Object>`，key 是列名，value 是列值。适用于动态列、不确定结构的查询。

## 八、常见面试追问

### Q1：resultType 可以映射 Map 吗？

可以。指定 `resultType="java.util.HashMap"` 或 `resultType="map"`，返回 `List<Map<String, Object>>`。key 是列名，value 是列值。

### Q2：resultMap 能继承吗？

能。使用 `extends` 属性：

```xml
<resultMap id="baseResultMap" type="com.example.entity.User">
    <id column="id" property="id"/>
    <result column="name" property="name"/>
</resultMap>

<resultMap id="detailResultMap" type="com.example.entity.User" extends="baseResultMap">
    <result column="email" property="email"/>
    <association property="department" .../>
</resultMap>
```

### Q3：多个 resultMap 有相同 id 会怎样？

MyBatis 启动时会抛异常，因为 resultMap 的 ID 在 Configuration 中必须全局唯一。

### Q4：autoMappingBehavior 设为 FULL 有什么风险？

FULL 级别会在嵌套映射（association/collection）中也启用自动映射，可能导致意外的属性覆盖。比如关联对象的属性名和主对象重名时会映射错误。一般保持默认 PARTIAL 即可。

## 九、总结

```
记住三个核心要点：

1. resultType vs resultMap
   resultType：同名自动映射，适合简单查询
   resultMap：手动指定映射，适合复杂映射（关联、鉴别器、构造器）

2. 字段名不一致三种解决方式
   SQL 别名 → 简单快捷
   mapUnderscoreToCamelCase → 全局下划线转驼峰（推荐）
   resultMap 手动映射 → 灵活但配置量大

3. 结果封装核心流程
   handleResultSets() → handleRowValues() → createResultObject()
   → applyPropertyMappings()
   反射创建对象 + 设置属性值，id 元素影响嵌套结果合并
```

**面试回答模板**：

> resultType 是自动映射，按列名和属性名同名规则映射；resultMap 是手动映射，显式指定列名到属性名的对应关系，支持复杂映射如关联查询和鉴别器。字段名不一致有三种解决方式：SQL 中用别名、全局开启 mapUnderscoreToCamelCase 配置、用 resultMap 手动映射。
>
> 底层结果封装通过 DefaultResultSetHandler 实现：先遍历 ResultSet，通过反射创建 Java 对象，再根据映射关系设置属性值。resultMap 中的 id 元素不仅标识主键，还影响嵌套查询的结果合并——通过 id 值判断多行数据是否属于同一个对象。
