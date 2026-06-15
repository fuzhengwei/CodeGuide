---
title: 第3节：MySQL 配置和使用
pay: https://t.zsxq.com/jrwOq
---

# 《AI 新范式》第3节：MySQL 配置和使用

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/jrwOq](https://t.zsxq.com/jrwOq)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

Docker 部署数据库 · 远程访问配置，从建库到实战操作一站式搞定。

## 一、本章诉求

在云服务器上通过 Docker 部署 MySQL，配置远程访问，完成建库建表和数据操作实战。

## 二、MySQL 配置和使用

### 1. Docker 部署 MySQL

容器化安装 MySQL，数据持久化存储，配置即代码，一键启停。

### 2. 安全配置

远程访问控制、用户权限管理、连接加密，保障数据安全无忧。

### 3. 实战操作

建库建表、数据导入导出、慢查询分析等常用操作演示。

## 三、MySQL Docker 部署

```bash
docker run -d \
  --name mysql \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=your_password \
  -v mysql_data:/var/lib/mysql \
  mysql:8.0
```

## 四、远程连接配置

- 开放 3306 端口（安全组 + 防火墙）
- 创建远程访问用户并授权
- 修改 `bind-address` 允许外部连接
- 使用 Navicat / DBeaver 连接验证

## 五、读者作业

- 简单作业：Docker 部署 MySQL 后，使用本地 Navicat 或 DBeaver 连接远程数据库，建一张用户表并插入一条数据。
- 复杂作业：思考 MySQL 8.0 的 `mysql_native_password` 和 `caching_sha2_password` 认证方式有什么区别？为什么 Docker 部署时需要指定 `--default-authentication-plugin`？
