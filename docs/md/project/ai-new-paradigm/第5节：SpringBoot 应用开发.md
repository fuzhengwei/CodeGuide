---
title: 第5节：SpringBoot 应用开发
pay: https://t.zsxq.com/ic9dm
---

# 《AI 新范式》第5节：SpringBoot 应用开发

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/ic9dm](https://t.zsxq.com/ic9dm)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

本地开发连接云服务器 MySQL + Redis，从零写一个可运行的业务应用。

## 一、本章诉求

使用 xfg-ddd-skills 提示词驱动，自动生成 DDD 分层项目，连接云服务器 MySQL 和 Redis，开发一个表单服务应用。

## 二、项目初始化与依赖配置

### 1. 快速初始化

start.spring.io 或 IDEA 新建项目，选择 Spring Web、MyBatis、MySQL、Redis。JDK 17+、Maven。

### 2. 关键依赖

MyBatis-Plus、Lombok、commons-pool2（Redis 连接池）。

### 3. 云服务器连接

配置安全组开放 3306/6379，使用公网 IP 连接。

```xml
-- pom.xml 核心依赖
<dependency>
  <groupId>com.baomidou</groupId>
  <artifactId>mybatis-plus-boot-starter</artifactId>
  <version>3.5.5</version>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

## 三、application.yml 配置

### MySQL 连接配置

```yaml
spring:
  datasource:
    url: jdbc:mysql://47.98.x.x:3306/walicode
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### Redis 连接配置

```yaml
spring:
  data:
    redis:
      host: 47.98.x.x
      port: 6379
      password: your_password
      database: 0
      lettuce:
        pool:
          max-active: 8
```

> 💡 注意安全组开放 3306 和 6379，生产环境建议配置白名单而非 0.0.0.0/0

## 四、简化提示词 - 表单服务开发

使用 xfg-ddd-skills 创建项目，以下是完整提示词（直接复制使用）：

```
使用 xfg-ddd-skills 创建一个表单服务项目：

## 功能
1. 运营创建表单、生成分享链接
2. 用户访问链接填写表单
3. 运营后台查看填写数据

## 数据库设计（连接云服务器）
- form（表单表）
- form_field（字段表）
- form_data（数据表）

## 缓存设计（连接云服务器）
- Redis 缓存表单详情 30分钟
- 访问频次限流

## 技术栈
- SpringBoot 3.x
- MyBatis-Plus
- MySQL（云服务器 IP:3306）
- Redis（云服务器 IP:6379）
- xfg-ddd-skills 分层架构
```

## 五、数据库表设计与缓存策略

### 数据库表设计

- **form** 表单表（标题/分享链接）
- **form_field** 字段表（Label/类型/必填）
- **form_data** 数据表（提交用户/JSON）

### Redis 缓存设计

```bash
-- Key 设计
form:{id}          -- 表单详情
form:rank         -- 访问排行 ZSet
form:rate:{ip}    -- 频次限流
```

> 💡 使用 xfg-ddd-skills 自动生成分层架构：app/trigger/domain/infrastructure

## 六、读者作业

- 简单作业：使用 xfg-ddd-skills 提示词创建表单服务项目，配置 application.yml 连接云服务器 MySQL 和 Redis，本地启动验证。
- 复杂作业：思考 DDD 分层架构中，domain 层为什么不直接依赖 MyBatis 的 DAO 对象？这样做的好处是什么？
