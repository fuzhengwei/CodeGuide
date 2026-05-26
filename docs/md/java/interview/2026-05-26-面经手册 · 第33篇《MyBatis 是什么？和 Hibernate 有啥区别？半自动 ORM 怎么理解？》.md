---
layout: post
category: interview
title: 面经手册 · 第33篇《MyBatis 是什么？和 Hibernate 有啥区别？半自动 ORM 怎么理解？》
tagline: by 小傅哥
tag: [java,interview]
excerpt: MyBatis 面试第一关，基础概念怎么答才不落俗套？ORM 是什么？MyBatis 和 Hibernate 到底哪个好？为什么说 MyBatis 是半自动 ORM？大多数候选人只会背定义，面试官一追问就露馅。本文从概念到对比，把基础题答出深度。
lock: need
---

# 面经手册 · 第33篇《MyBatis 是什么？和 Hibernate 有啥区别？半自动 ORM 怎么理解？》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

面试官喜欢问基础概念，不是因为概念有多难，而是想看你对技术的理解有多深。

"MyBatis 是什么？"——大多数人背一遍定义就完事了。"和 Hibernate 有啥区别？"——背两条优缺点。"半自动 ORM 怎么理解？"——嗯……

但如果你能从 ORM 的本质出发，讲清楚全自动和半自动的设计取舍，面试官就知道你不只是背了八股文，而是真正用过、想过。

本文把 MyBatis 基础概念相关的面试题一次性讲透。

## 二、面试题

`谢飞机，小记！`，背了几天 MyBatis 概念，来面试了。

**面试官**：谢飞机，MyBatis 是什么？

**谢飞机**：MyBatis 是一个优秀的持久层框架，支持自定义 SQL、存储过程和高级映射。

**面试官**：ORM 是什么？MyBatis 是 ORM 框架吗？

**谢飞机**：ORM 是对象关系映射……MyBatis 是半自动 ORM。

**面试官**：半自动体现在哪？

**谢飞机**：需要手写 SQL……

**面试官**：那 MyBatis 和 Hibernate 的区别是什么？

**谢飞机**：MyBatis 灵活，Hibernate 自动化程度高……

**面试官**：什么时候选 MyBatis，什么时候选 Hibernate？

**谢飞机**：这个……项目大多用 MyBatis？

**面试官**：好吧。MyBatis 的优缺点能具体说说吗？

**谢飞机**：优点是 SQL 灵活……缺点是……要写很多 XML？

**面试官**：你再回去想想。下一个！

## 三、MyBatis 是什么

### 1. 官方定义

MyBatis 是一款优秀的持久层框架，它支持自定义 SQL、存储过程以及高级映射。MyBatis 免除了几乎所有的 JDBC 代码和参数手工设置以及结果集的检索工作。MyBatis 可以使用简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO 为数据库中的记录。

### 2. MyBatis 能做什么

拆解来看，MyBatis 核心能力有四个：

```
┌──────────────────────────────────────────────┐
│              MyBatis 核心能力                  │
├──────────────────────────────────────────────┤
│  1. SQL 映射：XML/注解定义 SQL，自动执行       │
│  2. 参数映射：Java 对象 → SQL 参数             │
│  3. 结果映射：SQL 结果集 → Java 对象           │
│  4. 缓存机制：一级缓存 + 二级缓存              │
└──────────────────────────────────────────────┘
```

**一句话总结**：MyBatis 帮你干了 JDBC 的脏活累活（获取连接、设参数、遍历结果集），但 SQL 还是你自己写。

### 3. MyBatis 的前世今生

```
iBATIS → MyBatis
  2002年：Clinton Begin 创建 iBATIS
  2010年：iBATIS 3.x 更名为 MyBatis（Apache → Google Code → GitHub）
  至今：MyBatis 3.5.x，MyBatis-Plus 扩展
```

iBATIS 到 MyBatis 的核心变化：简化了配置、引入了注解支持、优化了动态 SQL。

## 四、ORM 概念与分类

### 1. 什么是 ORM

ORM（Object-Relational Mapping，对象关系映射）：一种将 Java 对象与数据库表进行映射的技术，让开发者用面向对象的方式操作数据库，而不是手写 SQL。

```
Java 世界          ORM 框架          数据库世界
┌──────┐                        ┌──────┐
│ User │  ←── 自动映射 ──→      │ user │
│ 对象  │                        │ 表   │
└──────┘                        └──────┘

属性 name  ←── 列映射 ──→  字段 user_name
属性 age   ←── 列映射 ──→  字段 age
```

### 2. 全自动 ORM vs 半自动 ORM

| 对比项 | 全自动 ORM | 半自动 ORM |
|-------|-----------|-----------|
| 代表框架 | Hibernate、JPA | MyBatis |
| SQL 编写 | 框架自动生成 | 开发者手写 |
| 映射方式 | 对象→表自动映射 | 需要配置映射关系 |
| 学习成本 | 高（HQL、缓存、延迟加载等） | 低（会 SQL 就能上手） |
| SQL 优化 | 困难（SQL 对开发者透明） | 容易（SQL 完全可控） |
| 灵活度 | 低（复杂查询难实现） | 高（SQL 随心所欲） |

### 3. 半自动 ORM 的"半"体现在哪

**"半" = 框架只做了一半的事**：

```
全自动 ORM（Hibernate）：
  Java 对象 → 框架生成 SQL → 执行 → 框架映射结果 → Java 对象
  ✅ 全程由框架完成，开发者只操作对象

半自动 ORM（MyBatis）：
  Java 对象 → 开发者写 SQL → 框架执行 + 映射结果 → Java 对象
  ⚠️ SQL 需要开发者自己写，映射结果由框架完成
```

**具体体现**：

1. **SQL 需要手写**：不会像 Hibernate 那样根据对象状态自动生成 INSERT/UPDATE/DELETE
2. **映射需要配置**：resultMap、`#{}` 参数绑定，需要开发者明确指定
3. **缓存需要理解**：一级缓存默认开，二级缓存需要手动开启并理解使用场景
4. **关联需要设计**：一对一、一对多的关联查询需要显式配置，不像 Hibernate 自动级联

## 五、MyBatis vs Hibernate 深度对比

### 1. 核心差异

| 维度 | MyBatis | Hibernate |
|------|---------|-----------|
| 设计理念 | SQL 为中心 | 对象为中心 |
| SQL 控制 | 完全可控 | 框架生成，可控性弱 |
| 学习曲线 | 低（懂 SQL 即可） | 高（HQL、Session、缓存体系） |
| 动态查询 | 动态 SQL 标签，灵活 | Criteria API，繁琐 |
| 缓存 | 简单（一级 + 二级） | 复杂（一级 + 二级 + 查询缓存） |
| 移植性 | SQL 方言依赖，移植性差 | HQL 方言适配，移植性好 |
| 性能调优 | 直接优化 SQL | 需理解框架机制，调优难度大 |
| 适用场景 | 复杂 SQL、高并发、报表 | 快速开发、标准 CRUD |

### 2. 关键对比详解

**SQL 透明度**：

```java
// MyBatis：SQL 完全可见
@Select("SELECT * FROM user WHERE age > #{age}")
List<User> findByAge(@Param("age") int age);

// Hibernate：SQL 对开发者透明
session.createQuery("FROM User WHERE age > :age", User.class)
       .setParameter("age", 18)
       .list();
// 生成什么 SQL？需要开日志才能看到
```

**N+1 查询问题**：

```
MyBatis：
  - 手动写 JOIN SQL，一次查完
  - 或者用延迟加载（需配置）
  - SQL 可控，问题好排查

Hibernate：
  - 默认可能触发 N+1 查询
  - 需要理解 fetch 策略
  - 排查困难，SQL 是框架生成的
```

**批量操作**：

```
MyBatis：
  - foreach 拼接批量 SQL
  - Batch Executor
  - 灵活但需要自己处理

Hibernate：
  - Session 批量 flush/clear
  - 需要理解一级缓存的影响
  - 配置不好容易 OOM
```

### 3. 国内为什么偏爱 MyBatis

1. **互联网业务 SQL 复杂**：多表关联、子查询、聚合统计，Hibernate 生成 SQL 性能差
2. **性能敏感**：高并发场景下 SQL 需要精确优化，MyBatis 可控
3. **团队技术栈**：DBA 友好，SQL 审查方便
4. **MyBatis-Plus 加持**：弥补了 CRUD 样板代码的问题

## 六、MyBatis 优缺点

### 1. 优点

| 优点 | 说明 |
|------|------|
| SQL 灵活可控 | 手写 SQL，复杂查询、性能优化无障碍 |
| 学习成本低 | 会 SQL + 简单配置即可上手 |
| 代码与 SQL 分离 | XML 管理 SQL，代码和 SQL 不耦合 |
| 支持动态 SQL | if/where/foreach 等标签，条件查询很方便 |
| 映射灵活 | resultMap 支持复杂映射、关联映射 |
| 社区活跃 | MyBatis-Plus 扩展，国内生态完善 |

### 2. 缺点

| 缺点 | 说明 |
|------|------|
| SQL 工作量大 | 单表 CRUD 也要写 SQL（MP 可弥补） |
| 数据库移植性差 | SQL 方言不同，换数据库需改 SQL |
| 字段映射繁琐 | 表字段多时 resultMap 配置量大 |
| 缓存机制简单 | 二级缓存作用有限，分布式需 Redis |
| 侵入性 | SQL 写在 XML 中，不跨框架使用 |

## 七、选择建议

```
选择 MyBatis 的场景：
  ✅ 互联网项目，SQL 复杂度高
  ✅ 性能要求高，需要精确优化 SQL
  ✅ 团队 DBA 参与 SQL 审查
  ✅ 已有 MyBatis-Plus 生态

选择 Hibernate 的场景：
  ✅ 快速开发，CRUD 为主
  ✅ 需要数据库移植性
  ✅ 领域模型驱动设计（DDD）
  ✅ 团队熟悉 JPA 规范
```

## 八、常见面试追问

### Q1：MyBatis 和 iBATIS 的关系？

iBATIS 是 MyBatis 的前身。2010 年，项目从 Apache 迁移到 Google Code，并更名为 MyBatis。核心变化：简化 XML 配置、新增注解支持、增强动态 SQL。

### Q2：MyBatis 能做 DTO 映射吗？

能。通过 resultMap 可以将查询结果映射到任意 Java 对象（不限于实体类），包括 DTO、VO 等。只需配置列名到属性的映射关系。

### Q3：为什么国内项目大多用 MyBatis 而不是 Hibernate？

核心原因：国内互联网业务 SQL 复杂度高、性能要求严格，需要 DBA 参与优化 SQL。MyBatis 的 SQL 可控性天然适合这种场景。加上 MyBatis-Plus 补齐了 CRUD 效率的短板，形成完整生态。

### Q4：JPA 和 MyBatis 可以共存吗？

可以。Spring Data JPA 和 MyBatis 可以在同一个项目中使用，共享同一个 DataSource。简单 CRUD 用 JPA，复杂查询用 MyBatis。但一般不建议混用，增加维护成本。

### Q5：MyBatis-Plus 和 MyBatis 的关系？

MyBatis-Plus 是 MyBatis 的增强工具，只做增强不做改变。核心增强：通用 CRUD、条件构造器、分页插件、代码生成器等。底层完全兼容 MyBatis。

## 九、总结

```
记住三个核心要点：

1. MyBatis 是半自动 ORM
   框架负责参数映射和结果映射，SQL 需要开发者自己写
   "半" = 不生成 SQL，只做映射

2. MyBatis vs Hibernate 的核心差异
   MyBatis：SQL 为中心，灵活可控，适合复杂业务
   Hibernate：对象为中心，自动化高，适合标准 CRUD

3. 选型的判断标准
   SQL 复杂 + 性能要求高 → MyBatis
   快速开发 + 数据库可移植 → Hibernate
   国内主流选 MyBatis，MyBatis-Plus 补齐效率短板
```

**面试回答模板**：

> MyBatis 是一款半自动 ORM 持久层框架，它帮开发者处理了 JDBC 的参数设置和结果映射，但 SQL 需要开发者自己编写。和 Hibernate 的核心区别在于：MyBatis 以 SQL 为中心，SQL 完全可控，适合复杂查询和性能优化；Hibernate 以对象为中心，自动生成 SQL，开发效率高但调优困难。半自动的"半"体现在框架只做了参数映射和结果映射，SQL 编写和优化仍需要开发者负责。在国内互联网项目中，因为业务 SQL 复杂、性能要求高，MyBatis 是主流选择，配合 MyBatis-Plus 可以补齐 CRUD 效率的短板。
