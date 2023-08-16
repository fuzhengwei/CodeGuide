---
title: MySQL
lock: need
---

# MySQL 开发规范和使用约束

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=744741676&bvid=BV1Br4y1Z7Du&cid=1232274988&page=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

本文的宗旨在于通过简单干净实践的方式教会读者，如何更好地使用 MySQL 数据库。这包括；库表创建规范、字段的创建规范、索引的创建规范以及SQL使用的相关规范，通过这些内容的讲解，让读者更好使用 MySQL 数据库，创建出符合规范的表和字段以及建出合适的索引。

如果你还想学习更深入的 MySQL 知识，建议可以阅读下官网的参考手册，这比任何一个资料都要有权威性。

- [MySQL 5.7 参考手册](https://dev.mysql.com/doc/refman/5.7/en/create-index.html)
- [MySQL 8.0 参考手册](https://dev.mysql.com/doc/refman/8.0/en/commit.html)

本文涉及的工程【导表语句】：

- [road_map_5.6.sql](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-mysql/docs) 
- [road_map_8.0.sql](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-mysql/docs) 

## 一、案例背景

此案例背景定位于使用使用一个简单的订单表，来讲解 MySQL 使用的相关规范。包括；表的引擎、命名约束、字段长度、金额类型、更新时间、索引字段、组合索引等内容，方便大家学习以后，可以基于这些字段的规范演示讲解，在自己创建库表的时候有个参考对照，尽可能创建出性能更佳的库表和索引。

## 二、库表规范

为了能让读者更加清晰地看到这些相关规范都是如何体现的，小傅哥这里准备了个大图，把库表字段和规范全部整合在一起，方便学习使用。如下；

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-mysql-01.png?raw=true" width="950px">
</div>

如上所列规范包括：建表相关规范、字段相关规范、索引相关规范、使用相关规范。

### 1. 建表相关规范

1. 库名、表名、字段名，使用小写和下划线 _ 分割
2. 库名、表名、字段名，不超过12个字符。默认支持64个字符。
3. 库名、表名、字段名，见名知意，建议使用名词而不是动词。
4. 使用 InnoDB 存储引擎。支持；事务、锁、高并发 性能好。
5. 推荐使用 utf8mb4 可以存emoji
6. 单表字段数，建议不超过40个

### 2. 字段相关规范

1. 整型定义中不显示设置长度，如使用 INT，而不是INT(4)
2. 存储精度浮点数，使用 DECIMAL 替代 FLOAT、DOUBLE
3. 所有字段，都要有 Comment 描述
4. 所有字段应定义为 NOT NULL
5. 超过2038年，用DATETIME存储
6. 短数据类型 0~80 选用 TINYINT 存储
7. UUID 有全局唯一统一字段属性，适合做同步ES使用。
8. IPV4，用无符号 INT 存储
9. IPV6，用VARBINARY存储
10. JSON MySql 8.x 新增特性
11. update_time 设置 on update 更新属性

### 3. 索引相关规范

1. 要求有自增ID作为主键，不要使用随机性较强的 order_id 作为主键，会导致innodb内部page分裂和大量随机I/O，性能下降。
2. 单表索引建议控制在5个以内，单索引字段数不超过5个。注意：已有idx(a, b)索引，又有idx(a)索引，可以把idx(a)删了，浪费空间，降低更新、写入性能。* 单个索引中，每个索引记录的长度不能超过64KB
3. 利用覆盖索引来进行查询操作，避免回表。另外建组合索引的时候，区分度最高的在最左边。
4. `select(count(distinct(字段)))/count(id) = 1` 的区分度，更适合建索引。在一些低区分度的字段，例如type、status上建立独立索引几乎没意义，降低更新、写入性能。
5. 防止因字段不同造成的隐式转换，导致索引失效。
6. 更新频繁的字段，不要建索引。

### 4. 使用相关规范

1. 单表数据量不超过500万行，ibc 文件大小不超过 2G
2. 水平分表用取模，日志、报表类，可以用日期
3. 单实例表数目小于 500
4. alter表之前，先判断表数据量，对于超过100W行记录的表进行alter table，必须在业务低峰期执行。因为alter table会产生表锁，期间阻塞对于该表的所有写入
5. SELECT语句必须指定具体字段名称，禁止写成 `“*”select *` 会将不需要读的数据也从MySQL里读出来，造成网卡压力，数据表字段一旦更新，但model层没有来得及更新的话，系统会报错
6. insert语句指定具体字段名称，不要写成 `insert into t1 values(…)`
7. `insert into…values(XX),(XX),(XX)..` 这里XX的值不要超过5000个，值过多会引起主从同步延迟变大。
8. `union all` 和 `union`，不要超过5个子句，如果没有去重的需求，使用union all性能更好。
9. in 值列表限制在500以内，例如 `select… where userid in(….500个以内…)`，可以减少底层扫描，减轻数据库压力。
10. 除静态表或小表（100行以内），DML语句必须有where条件，且尽量使用索引查找
11. 生产环境禁止使用 hint，如 sql_no_cache，force index，ignore key，straight join等。
要相信MySQL优化器。hint是用来强制SQL按照某个执行计划来执行，但随着数据量变化我们无法保证自己当初的预判是正确的。
12. where条件里，等号左右字段类型必须一致，否则会造成隐式的类型转化，可能导致无法使用索引
13. 生产数据库中强烈不推荐在大表执行全表扫描，查询数据量不要超过表行数的25%，否则可能导致无法使用索引
14. where子句中禁止只使用全模糊的LIKE条件进行查找，如like ‘%abc%’，必须有其他等值或范围查询条件，否则可能导致无法使用索引
15. 索引列不要使用函数或表达式，如 `where length(name)=10` 或 `where user_id+2=1002`，否则可能导致无法使用索引
16. 减少使用or语句 or有可能被 mysq l优化为支持索引，但也要损耗 mysql 的 cpu 性能。可将or语句优化为union，然后在各个where条件上建立索引。如 `where a=1 or b=2` 优化为 `where a=1… union …where b=2, key(a),key(b)` 某些场景下，也可优化为 `in`
17. 分页查询，当limit起点较高时，可先用过滤条件进行过滤。如 `select a,b,c from t1 limit 10000,20`; 优化为 `select a,b,c from t1 where id>10000 limit 20`;
18. 同表的字段增删、索引增删等，合并成一条DDL语句执行，提高执行效率，减少与数据库的交互。
19. `replace into` 和 `insert on duplicate key update` 在并发环境下执行都可能产生死锁（后者在5.6版本可能不报错，但数据有可能产生问题），需要catch异常，做事务回滚，具体的锁冲突可以关注`next key lock`和`insert intention lock`
20. TRUNCATE TABLE 比  DELETE 速度快，且使用的系统和事务日志资源少，但 TRUNCATE 无事务且不触发 trigger ，有可能造成事故，故不建议在开发代码中使用此语句。说明： TRUNCATE TABLE 在功能上与不带  WHERE 子句的  DELETE 语句相同。

## 三、建表语句

**环境说明**；

- MySQL 8.0.32 - 可使用 Docker 安装，脚本放到本案例仓库了。
- [Sequel Ace](https://www.sequelpro.com/)

```sql
# ************************************************************
# Sequel Ace SQL dump
# 版本号： 20050
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# 主机: localhost (MySQL 8.0.32)
# 数据库: road_map
# 生成时间: 2023-08-12 07:19:03 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# 转储表 user_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_order`;

CREATE TABLE `user_order` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID；【必须保留自增ID，不要将一些有随机特性的字段值设计为主键，例如order_id，会导致innodb内部page分裂和大量随机I/O，性能下降】int 大约21亿左右，超过会报错。bigint 大约9千亿左右。',
  `user_name` varchar(64) NOT NULL COMMENT '用户姓名；',
  `user_id` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户编号；',
  `user_mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户电话；使用varchar(20)存储手机号，不要使用整型。手机号不会做数学计算、涉及到区号或者国家代号，可能出现+-()、支持模糊查询，例如：like“135%”',
  `sku` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品编号',
  `sku_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `order_id` varchar(64) NOT NULL COMMENT '订单ID',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '商品数量；整形定义中不显示规定显示长度，比如使用 INT，而不使用 INT(4)',
  `unit_price` decimal(10,2) NOT NULL COMMENT '商品价格；小数类型为 decimal，禁止使用 float、double',
  `discount_amount` decimal(10,2) NOT NULL COMMENT '折扣金额；',
  `tax` decimal(4,2) NOT NULL COMMENT '费率金额；',
  `total_amount` decimal(10,2) NOT NULL COMMENT '支付金额；(商品的总金额 - 折扣) * (1 - 费率)',
  `order_date` datetime NOT NULL COMMENT '订单日期；timestamp的时间范围在1970-01-01 00:00:01到2038-01-01 00:00:00之间',
  `order_status` tinyint(1) NOT NULL COMMENT '订单状态；0 创建、1完成、2掉单、3关单 【不要使用 enum 要使用 tinyint 替代。0-80 范围，都可以使用 tinyint】',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删单；0未删除，1已删除 【表达是否概念的字段必须使用is_】',
  `uuid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '唯一索引；分布式下全局唯一，用于binlog 同步 ES 方便使用',
  `ipv4` int unsigned NOT NULL DEFAULT '2130706433' COMMENT '设备地址；存储IPV4地址，通过MySQL 函数转换，inet_ntoa、inet_aton 示例；SELECT INET_ATON(‘209.207.224.40′); 3520061480 SELECT INET_NTOA(3520061480); 209.207.224.40所有字段定义为NOT NULL，并设置默认值，因为null值的字段会导致每一行都占用额外存储空间\\n数据迁移容易出错，在聚合函数计算结果偏差（如count结果不准）并且null的列使索引/索引统计/值比较都更加复杂，MySQL内部需要进行特殊处理，表中有较多空字段的时候，数据库性能下降严重。开发中null只能采用is null或is not null检索，而不能采用=、in、<、<>、!=、not in这些操作符号。如：where name!=’abc’，如果存在name为null值的记录，查询结果就不会包含name为null值的记录',
  `ipv6` varbinary(16) NOT NULL COMMENT '设备地址；存储IPV6地址，VARBINARY(16)  插入：INET6_ATON(''2001:0db8:85a3:0000:0000:8a2e:0370:7334'') 查询：SELECT INET6_NTOA(ip_address) ',
  `ext_data` json NOT NULL COMMENT '扩展数据；记录下单时用户的设备环境等信息(核心业务字段，要单独拆表)。【select user_name, ext_data, ext_data->>''$.device'', ext_data->>''$.device.machine'' from `user_order`;】',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_orderid` (`order_id`),
  UNIQUE KEY `uq_uuid` (`uuid`),
  KEY `idx_order_date` (`order_date`),
  KEY `idx_sku_unit_price_total_amount` (`sku`,`unit_price`,`total_amount`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `user_order` WRITE;
/*!40000 ALTER TABLE `user_order` DISABLE KEYS */;

INSERT INTO `user_order` (`id`, `user_name`, `user_id`, `user_mobile`, `sku`, `sku_name`, `order_id`, `quantity`, `unit_price`, `discount_amount`, `tax`, `total_amount`, `order_date`, `order_status`, `is_delete`, `uuid`, `ipv4`, `ipv6`, `ext_data`, `update_time`, `create_time`)
VALUES
	(1,'小傅哥','U001','13512345678','SKU001','Mac Pro M2 贴膜','ORD001',2,10.99,2.00,0.50,19.48,'2023-08-12 10:00:00',0,0,'uuid001',2130706433,X'20010DB885A3000000008A2E03707334','{\"device\": {\"machine\": \"IPhone 14 Pro\", \"location\": \"shanghai\"}}','2023-08-12 10:00:00','2023-08-12 10:00:00'),
	(2,'福禄娃','U002','13698765432','SKU002','IPad mini4 外套','ORD002',1,25.99,0.00,1.50,24.49,'2023-08-12 11:30:00',1,0,'uuid002',2130706433,X'20010DB885A3000000008A2E03707334','{\"device\": {\"machine\": \"PC Windows\", \"location\": \"BeiJing\"}}','2023-08-12 11:30:00','2023-08-12 11:30:00'),
	(3,'拎瓢冲','U003','13755555555','SKU003','数据线','ORD003',3,9.99,1.50,0.00,26.97,'2023-08-12 13:45:00',0,0,'uuid003',2130706433,X'20010DB885A3000000008A2E03707334','{\"device\": {\"machine\": \"PC Windows\", \"location\": \"BeiJing\"}}','2023-08-12 13:45:00','2023-08-12 13:45:00'),
	(4,'熏5null','U004','13812345678','SKU004','U盘','ORD004',1,15.99,0.00,0.75,15.24,'2023-08-12 14:20:00',1,0,'uuid004',2130706433,X'20010DB885A3000000008A2E03707334','{\"device\": {\"machine\": \"PC Windows\", \"location\": \"BeiJing\"}}','2023-08-12 14:20:00','2023-08-12 14:20:00'),
	(5,'温柔一刀','U005','13999999999','SKU005','坐垫','ORD005',2,12.50,1.25,0.25,23.75,'2023-08-12 15:55:00',0,0,'uuid005',2130706433,X'20010DB885A3000000008A2E03707334','{\"device\": {\"machine\": \"PC Windows\", \"location\": \"BeiJing\"}}','2023-08-12 15:55:00','2023-08-12 15:55:00');

/*!40000 ALTER TABLE `user_order` ENABLE KEYS */;
UNLOCK TABLES;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
```

## 三、语句操作

### 1. 插入操作

```sql
INSERT INTO `user_order` (`id`, `user_name`, `user_id`, `user_mobile`, `sku`, `sku_name`, `order_id`, `quantity`, `unit_price`, `discount_amount`, `tax`, `total_amount`, `order_date`, `order_status`, `is_delete`, `uuid`, `ipv4`, `ipv6`, `ext_data`, `update_time`, `create_time`)
VALUES
	(9,'小傅哥','U001','13512345678','SKU001','Mac Pro M2 贴膜','ORD0101',2,10.99,2.00,0.50,19.48,'2023-08-12 10:00:00',0,0,'uuid010',INET_ATON('127.0.0.1'),INET6_ATON('2001:0db8:85a3:0000:0000:8a2e:0370:7334'),'{\"device\": {\"machine\": \"IPhone 14 Pro\", \"location\": \"shanghai\"}}','2023-08-12 10:00:00','2023-08-12 10:00:00');
```

- 其实列了这个 SQL 主要让大家注意到 IPV4、IPV6 的存储需要用到转换函数。也就是 MySQL 自己提供的 `INET_ATON`、`INET6_ATON` 转换和对应的 `INET_NTON`、`INET6_NTON` 解析。
- 此外你还可以单独测试这个函数；`select INET6_NTOA(INET6_ATON('2001:0db8:85a3:0000:0000:8a2e:0370:7334'))`

### 2. 查询操作

#### 2.1 IP 查询

```sql
select user_name, sku, INET_NTOA(ipv4), INET6_NTOA(ipv6) from `user_order`;

小傅哥	SKU001	127.0.0.1	2001:db8:85a3::8a2e:370:7334
福禄娃	SKU002	127.0.0.1	2001:db8:85a3::8a2e:370:7334
拎瓢冲	SKU003	127.0.0.1	2001:db8:85a3::8a2e:370:7334
熏5null	SKU004	127.0.0.1	2001:db8:85a3::8a2e:370:7334
温柔一刀	SKU005	127.0.0.1	2001:db8:85a3::8a2e:370:7334
```

#### 2.2 JSON 查询

```sql
select user_name, ext_data, ext_data->>'$.device', ext_data->>'$.device.machine' from `user_order`;

小傅哥	{"device": {"machine": "IPhone 14 Pro", "location": "shanghai"}}	{"machine": "IPhone 14 Pro", "location": "shanghai"}	IPhone 14 Pro
福禄娃	{"device": {"machine": "PC Windows", "location": "BeiJing"}}	{"machine": "PC Windows", "location": "BeiJing"}	PC Windows
拎瓢冲	{"device": {"machine": "PC Windows", "location": "BeiJing"}}	{"machine": "PC Windows", "location": "BeiJing"}	PC Windows
熏5null	{"device": {"machine": "PC Windows", "location": "BeiJing"}}	{"machine": "PC Windows", "location": "BeiJing"}	PC Windows
温柔一刀	{"device": {"machine": "PC Windows", "location": "BeiJing"}}	{"machine": "PC Windows", "location": "BeiJing"}	PC Windows
```

- MySQL 8.0 提供了 JSON 这样的专属存放方式，你可以通过 JSON 字段的内容来读取对应的信息。

#### 2.3 索引使用

```sql
# 使用 order_id 唯一索引
EXPLAIN select user_name, sku, INET_NTOA(ipv4), INET6_NTOA(ipv6) from `user_order` where order_id = 'ORD002';
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-mysql-02.png?raw=true" width="950px">
</div>

```sql
# 使用组合索引
EXPLAIN select sku,total_amount,order_date from `user_order` where total_amount > 10 and order_date between '2023-08-09 00:00:00' and '2023-08-09 23:59:59';
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-mysql-03.png?raw=true" width="950px">
</div>

#### 2.4 数量统计

```sql
select count(*) from `user_order`
```

- 不要使用 count(列名) 或 count(常量) 来替代 `count(*)` ，`count(*)` 是 SQL 92 定义的标准统计行数的语法，跟数据库无关，跟 NULL 和非 NULL 无关。

#### 2.5 for update

```sql
START TRANSACTION;

SELECT user_name, sku, total_amount, order_date, order_status FROM `user_order` WHERE order_id = 'ORD002' FOR UPDATE;

-- 在这里执行其他操作，其他会话无法修改 order_id 为 ORD002 的订单信息

COMMIT;
```

#### 2.6 行级锁

```sql
UPDATE `user_order` SET order_status = 0 WHERE order_id = 'ORD002' AND order_status = 3 FOR UPDATE;
```

- order_id 是唯一索引，如果没有索引，将会执行全表扫描。在这种情况下，MySQL会对整个user_order 表进行锁定，而不仅仅是符合条件的行。
- 即使你没有显式地添加 `FOR UPDATE` 语句，更新语句仍会锁定符合条件的行。这是因为MySQL 默认会使用行级锁来保证并发事务的一致性。

#### 2.7 表锁

- ALTER TABLE语句：当执行ALTER TABLE语句修改表结构时，MySQL会自动获取一个排它锁（X锁），这会阻塞其他会话对该表的读写操作，直到ALTER TABLE操作完成。

- LOCK TABLES语句：当使用LOCK TABLES语句手动锁定表时，会对被锁定的表使用表级别的锁，阻塞其他会话对该表的读写操作。

- TRUNCATE TABLE语句：TRUNCATE TABLE语句会获取一个排它锁（X锁），阻塞其他会话对该表的读写操作，直到TRUNCATE TABLE操作完成。

## 四、其他配置

### 1. 监控活动和性能：

在MySQL中，你可以使用以下命令来监控MySQL服务器的活动和性能：

- SHOW PROCESSLIST;：该命令用于显示当前正在运行的所有MySQL连接和查询。它将显示每个连接的ID、用户、主机、数据库、执行时间和当前执行的查询。
- SHOW STATUS;：该命令用于显示MySQL服务器的各种状态信息，例如连接数、线程状态、查询缓存命中率等。
- SHOW ENGINE INNODB STATUS;：该命令用于显示InnoDB存储引擎的详细状态信息，包括死锁信息、事务信息和缓冲池状态等。
- EXPLAIN：在查询语句前加上EXPLAIN关键字，可以获取查询执行计划的详细信息。这将显示查询的表访问顺序、使用的索引和可能的性能问题。
- mysqladmin extended-status：该命令用于显示MySQL服务器的扩展状态信息，包括各种计数器和性能指标。

### 2. 连接数查询和配置

查看MySQL服务器的可用连接数和设置连接数，可以使用以下方法：

1. 查看当前可用连接数：
   - 使用命令行客户端登录到MySQL服务器。
   - 执行以下SQL查询语句：`SHOW VARIABLES LIKE 'max_connections';`
   - 这将显示MySQL服务器当前配置的最大连接数。
2. 设置连接数：
   - 编辑MySQL服务器的配置文件（通常是`my.cnf`或`my.ini`）。
   - 找到`[mysqld]`部分。
   - 添加或修改以下行：`max_connections = <desired_value>`
   - 将`<desired_value>`替换为你希望设置的连接数。
   - 保存并关闭配置文件。
   - 重启MySQL服务器，以使更改生效。

设置连接数需要权衡服务器的可用资源和性能。如果设置的连接数过高，可能会导致服务器负载过重，影响性能。建议根据服务器的硬件规格和预期的负载量来调整连接数。另外，某些MySQL版本或发行版可能对最大连接数有特定的限制，请确保你的设置在允许范围内。

**注意**：1核1G可配置300个连接、2核4G可配置1000个连接、4核16G可配置4000个连接、8核32G可配置8000个连接。

---

👏🏻 欢迎小伙伴点击文章下面的`在 GitHub 上编辑此页`，提交更多的MySQL实践应用技巧。