---
title: 第15节：搭建MQ消息组件Kafka服务环境
pay: https://t.zsxq.com/Y72naAU
---

# 第15节：搭建MQ消息组件Kafka服务环境

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

- 分支：[211023_xfg_mq_kafka](https://gitcode.net/KnowledgePlanet/Lottery/-/tree/211023_xfg_mq_kafka) 
- 描述：搭建MQ消息组件Kafka服务环境，并整合到SpringBoot中，完成消息的生产和消费处理

## 一、开发日志

- 搭建 Kafka 环境，配置消息主题 *注意：MQ 消息的使用不非得局限于 Kafka，也可以使用 RocketMq*
- SpringBoot 整合 Kafka，验证消息的生产和消费

## 二、Kafka 安装和配置

Apache Kafka是一个分布式发布 - 订阅消息系统和一个强大的队列，可以处理大量的数据，并使您能够将消息从一个端点传递到另一个端点。 Kafka适合离线和在线消息消费。 Kafka消息保留在磁盘上，并在群集内复制以防止数据丢失。 Kafka构建在ZooKeeper同步服务之上。 它与Apache Storm和Spark非常好地集成，用于实时流式数据分析。

以下是Kafka的几个好处：

- **可靠性** - Kafka是分布式，分区，复制和容错的。
- **可扩展性** - Kafka消息传递系统轻松缩放，无需停机。
- **耐用性** - Kafka使用分布式提交日志，这意味着消息会尽可能快地保留在磁盘上，因此它是持久的。
- **性能** - Kafka对于发布和订阅消息都具有高吞吐量。 即使存储了许多TB的消息，它也保持稳定的性能。

Kafka非常快，并保证零停机和零数据丢失。