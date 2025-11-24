---
title: 本地任务消息组件
lock: no
---

# 《本地任务消息组件》- 为事务和消息推送（HTTP、MQ），提供最终一致性解决方案

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>课程：[https://t.zsxq.com/Pfekb](https://t.zsxq.com/Pfekb)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

**你说气人不**，每每公司`晋升提报`和`加薪`的时候，总是那些手里有俏活的👬🏻兄弟。业务项目虽然是根基，但大家都做也就拉不开差距，而技术类组件、通用服务、功能平台，倒不是所有人都能搞的，这类结合业务场景的提取共性问题，凝练成通用解决方案的项目，可以开发一个就解决了全大部门的问题，所以做这类项目很亮眼！

<div align="center">
	<img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-230905-01.png?raw=true" width="150px"/>
</div>

**这类项目也有门槛！**

看着好像做个技术组件也没啥，但实际真想做的时候，你会发现你不知道什么服务可以被抽取为通用组件，不具备这样的经验和眼界。同时即使知道做啥了，也不知道如何结合像 Spring、MyBatis 源码能力，开发出一个通用的组件来，能让其他业务项目引入使用。

有人讲这东西不就是重复造轮子吗？🤔 还真不是，重复造轮子，指的市面上有的，或者公司里有个基础平台组有的。但这类的轮子往往不是深度结合业务的，而是那种无业务属性的功能逻辑的轮子，比如；rpc、xxl-job、mq 等。但一个业务组，他所需要的是解决通用业务场景问题的轮子，但这类东西又不属于基础平台研发组，所以往往都是业务组自己来解决这类场景问题。

所以，让自己具备开发组件的能力，是非常非常重要的，这即是抽象业务也是驾驭源码的能力体现。

小傅哥已经为大家提供了非常多的通用组件项目，如；`扳手工程（DCC动态配置中心、设计模式、动态限流、任务调度）`、`BCP 透视业务监控`、`动态线程池`、`支付SDK组件`、`SpringBoot Starter （16个合集）`、`IntelliJ IDEA Plugin 插件开发能力（具备这个的，开发了不少AI类组件）`。

这次小傅哥给大家再加一个新的组件《本地任务消息组件》，该组件解决业务场景远程调用HTTP或推送MQ消息，最终一致的问题。

## 一、能学到啥

【架构】掌握 DDD 分层与端口-适配器模式，清晰划分 domain/infrastructure/trigger/config 模块，提升可维护性与扩展性。
【后端】学习注解+AOP 方式受理任务消息，结合事务边界进行统一处理，理解 `@LocalTaskMessage` 与切面配合的落地实践。
【后端】掌握本地消息表设计与分片扫描策略（按门牌号 houseNumber 分片），实现高效拉取与顺序处理，提升系统可靠性。
【后端】熟悉 Spring Event 事件驱动与异步消费，使用 `ApplicationEvent`+`@EventListener`+`@Async` 实现解耦通知链路。
【后端】实践策略模式实现可插拔通知能力，支持 HTTP 与 RabbitMQ 两种通知通道，并在成功/失败时更新任务状态。
【后端】熟练使用 OkHttp3 与 Retrofit2 统一封装 HTTP 网关，掌握动态 URL、Header、Body 的组合与异常处理。
【后端】了解 RabbitMQ 事件发布的可选依赖注入方式，避免未配置 MQ 时的强依赖导致应用启动失败。
【配置】掌握 `@ConfigurationProperties` 驱动的多任务组动态调度配置，支持 cron 与 fixedDelay 两种触发方式，并可配置批次大小 limit。
【运维】学习 `ThreadPoolTaskScheduler` 的线程池化调度管理，合理设置线程名与池大小，提升任务调度的可观测性与稳定性。
【数据】掌握原生 JDBC 访问与 DAO 封装，完成插入、状态更新、分片条件查询、最小游标查询等落地实现。
【测试】通过示例命令对象 `TaskMessageEntityCommand` 的构建与调用，理解入参约定、枚举策略与配置对象的协作。
【实践】提升异常、日志与枚举的综合使用能力，建立稳定的错误处理。

## 二、项目介绍

**《本地消息任务组件》** 项目，是以自定义注解 AOP 切入或编程的方式，动态化完成数据库表事务和消息推送（HTTP、MQ），达到最终一致性的目的。

>在没有这样的组件的时候，为了完成业务流程的同时，在发送一个MQ消息或则远程调用 HTTP 操作，都需要自己写一个本地消息表，之后还要维护消息表的扫描补偿。

操作方式如图；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/local-task-message/local-task-message-1-03.png" width="950px"/>
</div>

- 用户可以选择通过注解或者直接调用组件服务的方式进行使用。也就不用业务项目工程再维护关于本地消息表的写入和 MQ 或者 HTTP 的处理和补偿了。
- 注解方式，会自动获取入参，入参需要为 TaskMessageEntityCommand 对象，它可以是某个入参的对象。之后配置 req.command 也可以获取。

## 三、产品方案

### 1. 产品概述

本地任务消息组件基于 Spring 框架能力，设计并实现了通用功能内核，便于集成到各类业务系统中。在业务系统中，组件支持在事务内完成数据写库的同时，写入一条本地消息记录（需在业务系统数据库中创建符合组件规范的本地消息表）。写入完成后，组件同步推送 Spring 事件，触发事务外的异步处理，如 MQ 消息发送或 HTTP 回调。即使异步处理失败，组件内置的本地消息表定时任务（支持自定义配置“门牌号”多任务并行扫描，提升扫描吞吐量）会持续检测并重试通知，确保消息最终一致性和业务流程的可靠执行。

### 2. 技术架构

<div align="center">
	<img src="https://bugstack.cn/images/article/project/local-task-message/local-task-message-1-01.png" width="950px"/>
</div>

- 首先，Local Task Message 任务消息组件，是以解决通用业务场景中的，本地数据库事务和外部MQ/HTTP调用一致性问题而设计实现的。让上游业务系统，不需要在每个流程中，都要做大量的重复编码。而是通过注解或者直接调用组件内核服务即可完成消息的通知操作。
- 之后，Local Task Message 任务消息组件，并不是一个单纯的工具性功能，而是剩余一个领域服务内核，它具备完整的领域功能，具备操作数据库表的能力，以及接收 Spring Event 事件，对接 MQ、HTTP 完成和外部的交互处理。
- 然后，上游系统在使用这套服务时，只需要配置好对应的本地消息表（一个事务下，连的同一个库），以及引入组件和完成yml配置，即可直接使用。

### 3. 功能流程

<div align="center">
	<img src="https://bugstack.cn/images/article/project/local-task-message/local-task-message-1-02.png" width="850px"/>
</div>

- 引入本地消息组件后，以用户开发dao入库操作为开始，可以通过注解或者调用组件服务 `ILocalTaskMessageHandleService` 驱动同一个事务下，进行消息推送。
- 切面的方式，会更为优雅简洁，不需要用户自己在维护调用关系。
- 整个操作会由组件自行处理写库操作，基于 Spring Event 的监听和通知，触发消息推送。完成 http、mq 的调用逻辑。同时还有基于门牌号扫描的逻辑，增强吞吐量。

## 四、课程目录

现课程已全部录制完成，接下来会日更📅项目💐；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/local-task-message/local-task-message-01.png" width="850px"/>
</div>

- 6节课程，全程视频手把手，带着你分析需求，编写代码。快速完成一个组件项目。
- 课程代码，以互联网公司方式逐步拉分支开发，你可以学习到正规的编码操作。
