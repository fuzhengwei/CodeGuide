---
title: Q&A：常见开发问题错误解答
lock: no
---

# Q&A：常见开发问题错误解答

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>课程：[https://t.zsxq.com/17gswKIeX](https://t.zsxq.com/17gswKIeX)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、断点调试

- [排查10个Bug](https://www.bilibili.com/video/BV1F6421w71e)
- [打断点查空指针](https://www.bilibili.com/video/BV1q1421Q7Uv)

## 二、开发问题

### 1. org.apache.ibatis.binding.BindingException: Invalid bound statement (not found): top.duain.infrastructure.persistent.dao.IAwardDao.queryAwardList

- 案例：[https://t.zsxq.com/ENr02](https://t.zsxq.com/ENr02)
- 解答：
  - application.dev mybatis 配置，默认是被注释掉的，需要打开。
  - 自建工程结构，Application 启动层的文件夹路径，不是整个包结构的根路径，扫描不到
  - MyBatis Mapper 配置文件填写错误，没有配置对应的路径
  - app 层的 pom 文件，没有引入 infrastructure，所以不能被 Spring 容器管理导致。

### 2. Could not create to database server. Attempted reconnect 3 times. Giving up

- 案例：[https://t.zsxq.com/7i31T](https://t.zsxq.com/7i31T)
- 解答：
  - 课程中 docker compose 安装 MySQL，提供的是 13306 端口以及初始的密码（避免和你本地 MySQL 3306 冲突），所以你的 application.dev 数据库连接也要使用 13306 端口。
  - 安装到云服务器的，安全组 13306 端口没有打开。

### 3. java.lang.NullPointerException queryStrategyEntityByStrategyId(StrategyRepository.java:71)

- 案例：
  - [https://t.zsxq.com/x3Uz0](https://t.zsxq.com/x3Uz0)
  - [https://t.zsxq.com/9QGLX](https://t.zsxq.com/9QGLX)
  - [https://t.zsxq.com/BUtmX](https://t.zsxq.com/BUtmX)
  - [https://t.zsxq.com/wstu6](https://t.zsxq.com/wstu6)
  - [https://t.zsxq.com/P81oT](https://t.zsxq.com/P81oT)
- 解答：
  - MyBatis Mapper resultMap 配置成了 resultType，导致数据库中的字段没法和 Java 对象的字段映射。如 `strategy_id -> strategyId`
  - 注解忘记配置

### 4. java.lang.ArithmeticException: / by zero

- 案例：
  - [https://t.zsxq.com/4oSIt](https://t.zsxq.com/4oSIt)
  - [https://t.zsxq.com/JxXhY](https://t.zsxq.com/JxXhY)
- 解答：粗心犯错，test_queryStrategyAwardListByStrategyId 入参为 100002L 不是 10002L。或者 redis 没有数据导致。

### 5. java.lang.ClassCastException: class java.lang.Integer cannot be cast to class java.lang.String

- 案例：[https://t.zsxq.com/Uu8XA](https://t.zsxq.com/Uu8XA)
- 解答：由于 `getFromMap` 方法里是返回的 `String` 类型，而你设置的结果值为 `Integer` 类型，那么直接返回的值会报错类型转换异常。可以这样；`redisService.getMap("stratrgy_id_100001").get("1")`

### 6. TooManyResultsExcepton: Expected one result (or null) to be returned by selectOne(), but found: 3

- 案例：
  - [https://t.zsxq.com/7NUzA](https://t.zsxq.com/7NUzA)
  - [https://t.zsxq.com/Di0oC](https://t.zsxq.com/Di0oC)
  - [https://t.zsxq.com/gKu1H](https://t.zsxq.com/gKu1H)
- 解答：查询 sql 的条件入参写错，传了空值。用自己new的对象，获取里面的值，写错代码了。参考案例地址中的图片。

### 7. Redis 管理工具看错是乱码

- 案例：[https://t.zsxq.com/RpgCw](https://t.zsxq.com/RpgCw)
- 解答：RedisClientConfig 类，修改序列化方式 `config.setCodec(JsonJacksonCodec.INSTANCE);`

### 8. map value can't be null

- 案例：[https://t.zsxq.com/A4LaY](https://t.zsxq.com/A4LaY)
- 解答：修改对象类信息后，Redis 数据未清空重新测试。

### 9. 数据库查询无结果

- 案例：[https://t.zsxq.com/2DdEh](https://t.zsxq.com/2DdEh)
- 解答：入参格式化时间写错，导致格式的时间多了个空格。如案例图。

### 10. 决策树引擎，nextNode 计算失败，未找到可执行节点

- 案例：[https://t.zsxq.com/zNKob](https://t.zsxq.com/zNKob)
- 解答：规则树过滤章节后更细调整。可以跟着课程继续。

### 11. Cannot invoke String.split(String) because this.RuleModels is null

- 案例：[https://t.zsxq.com/nENwa](https://t.zsxq.com/nENwa)
- 解答：可以看案例排查图

### 12. Unable to connect to Redis server: localhost/127.0.0.1:6379

- 案例：[https://t.zsxq.com/mrpne](https://t.zsxq.com/mrpne)
- 解答：缺少配置 Redis 启动类。

### 13. Mock 就走不到黑名单了

- 案例：[https://t.zsxq.com/iO5kL](https://t.zsxq.com/iO5kL)
- 解答：Mock 值未调整，参考案例解答图。

### 14. 找不到分库下的分表

- 解答：Dao 类缺少 `@DBRouterStrategy(splitTable = true)`

## 三、DevOps

- Docker 拉取不下来镜像，超时；配置镜像地址；[https://t.zsxq.com/EVDc9](https://t.zsxq.com/EVDc9)
- jdk1.8、maven（含镜像）下载：[https://t.zsxq.com/EVDc9](https://t.zsxq.com/EVDc9)