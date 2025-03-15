---
title: 【更】第2-16节：引入RabbitMQ分布式多端消费
pay: https://t.zsxq.com/fhRso
---

# 《拼团交易平台系统》第2-16节：引入RabbitMQ分布式多端消费

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/mipvF](https://t.zsxq.com/mipvF)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

## 一、本章诉求

引入 RabbitMQ 分布式技术框架，实现分布式消息消费和多服务消费的能力。

消息，是一种解耦服务间直接（http/rpc）调用的手段，以发送消息和接收消息的模式，完成业务流程的异步化处理。

**RabbitMQ 基础教程**：[https://bugstack.cn/md/road-map/rabbitmq.html](https://bugstack.cn/md/road-map/rabbitmq.html)

## 二、业务流程

在互联网公司中，往往一个微服务发送出来的 MQ，除了自己接收消费处理自己的业务流程，也会有很多其他微服务进行消费。那么这里就会有一个 Topic，被多个应用消费的配置。如图；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/group-buy-market/group-buy-market-2-16-01.png" width="550px">
</div>

- 以拼团发送结算完成消息举例，拼团是负载均衡部署了2套服务，发送的MQ消息，由小型支付对接。
- 那么，负载均衡的拼团服务，发送MQ后，自己的2套微服务，会分别接收到消息。另外一套小型支付，假设只部署了一套，那么这里会消费5个MQ消息。
- 注意，以 RabbitMQ举例，这里会需要使用到同一套交换机，同一个路由Key，但队列要分别每个服务配置不同的。同时消息支持持久化，也就是拼团发送的MQ消息，即使小型支付服务暂时没有启动，也可以在启动后消费队列里的MQ消息。
