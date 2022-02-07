---
title: 第08节：部署环境 canal 同步数据到 ES
pay: https://t.zsxq.com/RjqfEmA
---

# 第07节：部署环境 Elasticsearch、Kibana

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、运维日志

- canal 是一款 阿里巴巴 MySQL binlog 增量订阅&消费组件，模拟 MySQL slave 的交互协议，伪装自己为 MySQL slave ，向 MySQL master 发送dump 协议。MySQL master 收到 dump 请求，开始推送 binary log 给 slave (即 canal )，canal 解析 binary log 对象(原始为 byte 流)，之后把数据转存到其他需要保存的服务中。文档：[https://github.com/alibaba/canal/wiki](https://github.com/alibaba/canal/wiki)
- 本章节我们会在 docker 环境，把 Mysql 数据库中抽奖系统的分库分表数据使用 canal 同步到 ES 文件系统中

## 二、环境要求

![](/images/article/project/lottery/Part-5/8-01.png)

canal [kə'næl]，译意为水道/管道/沟渠，主要用途是基于 MySQL 数据库增量日志解析，提供增量数据订阅和消费

| 应用           | 端口  | 版本   |
| -------------- | ----- | ------ |
| Mysql          | 3306  | 5.7    |
| ElasticSearch  | 9200  | 7.6.2  |
| Kibana         | 5601  | 7.6.2  |
| canal.deployer | 11111 | 1.1.15 |
| canal.adapter  | 8081  | 1.1.15 |
| canal.admin    | 8089  | 1.1.15 |

- Mysql、ElasticSearch、Kibana，在 Docker 环境已经安装完成，如果尚未安装或者版本不符合可以按照前面章节内容重新安装(PS：版本不匹可能没法同步数据)。
- canal 组件包括：canal.deployer、canal.adapter、canal.admin，本地地址(已做相应的排坑处理)：[https://gitcode.net/KnowledgePlanet/canal](https://gitcode.net/KnowledgePlanet/canal)、官网地址(如果有一些个性需求可以下载官网组件)：[https://github.com/alibaba/canal/releases/tag/canal-1.1.5](https://github.com/alibaba/canal/releases/tag/canal-1.1.5)
  - canal.deployer：可以直接监听MySQL的binlog，把自己伪装成MySQL的从库，只负责接收数据，并不做处理。
  - canal.adapter：相当于canal的客户端，会从canal-server中获取数据，然后对数据进行同步，可以同步到MySQL、Elasticsearch和HBase等存储中去。
  - canal.admin：为canal提供整体配置管理、节点运维等面向运维的功能，提供相对友好的WebUI操作界面，方便更多用户快速和安全的操作。

## 三、Mysql 配置

**配置**：对 Mysql 数据库开启 binlog 写入功能，并设置 `binlog-format = ROW` 同时需要创建一个只读权限的账号，用于订阅 binlog

### 1. 查看 binlog 是否启用

**注意**：你可以在 Docker 中使用 `docker exec -it [容器ID/容器名称(mysql)] /bin/bash` 进入容器后，使用 `mysql -u root -p` 进入 mysql 后执行命令查看，也可以通过 mysql 可视化工具直接链接后输入查询命令的方式进行查看

**命令**：`SHOW VARIABLES LIKE '%log_bin%';`

|   已开启 binlog   |  未开启 binlog    |
| :----: | :----: |
| ![](/images/article/project/lottery/Part-5/8-02.png) |  ![](/images/article/project/lottery/Part-5/8-03.png)  |

- 如果你的 Mysql 是未开启 binlog 的，那么需要执行下面的步骤进行开启，否则不能使用 canal 订阅 binlog