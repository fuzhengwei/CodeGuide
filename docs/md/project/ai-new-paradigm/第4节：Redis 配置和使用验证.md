---
title: 第4节：Redis 配置和使用验证
pay: https://t.zsxq.com/F1anH
---

# 《AI 新范式》第4节：Redis 配置和使用验证

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/F1anH](https://t.zsxq.com/F1anH)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

高性能缓存 · 数据持久化 · 连接验证，让应用飞起来的内存数据库。

## 一、本章诉求

在云服务器上通过 Docker 部署 Redis，配置持久化和安全加固，完成连接验证和常用数据操作。

## 二、Redis 部署与配置

### 1. 极速部署

Docker 一键启动 Redis，AI Shell 自动完成端口映射和密码配置，30 秒搞定。

### 2. 持久化存储

RDB + AOF 双持久化策略，数据不丢失，容器重启后自动恢复。

### 3. 安全加固

设置访问密码，限制外部连接，关闭危险命令，生产环境必备。

## 三、Redis Docker 部署

```bash
docker run -d \
  --name redis \
  -p 6379:6379 \
  -v redis_data:/data \
  redis:7.0 redis-server \
  --requirepass your_password \
  --appendonly yes
```

## 四、redis.conf 关键配置

```bash
# 设置访问密码
requirepass your_password

# 开启 AOF 持久化
appendonly yes

# 绑定地址（生产环境限制）
bind 127.0.0.1

# 最大内存限制
maxmemory 256mb
```

## 五、Redis 使用验证

### 1. 连接验证命令

```bash
# 进入 Redis 容器
docker exec -it redis redis-cli

# 认证密码
AUTH your_password

# 测试连接
PING
PONG

# 查看服务器信息
INFO server
```

### 2. 常用数据操作

```bash
# String 类型
SET name "walicode"
GET name

# Hash 类型（用户信息）
HSET user:1 name "小傅哥"
HGET user:1 name

# 检查 Key 过期
EXPIRE name 60
TTL name
```

> 💡 验证三步走：PING → SET/GET → INFO，全部通过说明 Redis 服务正常运行！

## 六、读者作业

- 简单作业：Docker 部署 Redis 后，执行 PING、SET/GET、INFO 三步验证，截图提交。
- 复杂作业：思考 RDB 和 AOF 两种持久化方式的优缺点？生产环境应该选择哪种，还是两者都用？
