---
title: 第4节：通知策略处理(HTTP&MQ)
pay: https://t.zsxq.com/Pfekb
---

# 《本地任务消息组件》- 第4节：通知策略处理(HTTP&MQ)

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>课程：[https://t.zsxq.com/Pfekb](https://t.zsxq.com/Pfekb)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

增加 HTTP、MQ（RabbitMQ）服务，在接收 Spring Event 监听后，以通知行为策略的方式，完成 HTTP 远程调用和 MQ 消息推送。

## 二、功能流程

如图，是本节关于事件消息回调通知的处理流程（暂时先不处理关于数据库的更新流程）；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/local-task-message/local-task-message-4-01.png" width="750px">
</div>

- 首先，事件消息的监听在 trigger 层的监听，之后调用领域层的通知服务方法。这里需要新增加一个领域方法。
- 之后，领域服务方法，要以 notifyType 不同类型进行通知操作。如；http、mq，这部分你将来想扩展其他的，就在这里添加。
- 最后，http 和 mq，都会进入基础设施层完成服务的调用处理。http 使用的是 retrofit2 框架进行封装。mq（RabbitMQ）直接使用 RabbitTemplate 模板 push 消息即可。

