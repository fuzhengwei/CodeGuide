---
title: 【更】第3-26节：分布式服务设计(redis)
pay: https://t.zsxq.com/n8tO1
---

# 《AI MCP Gateway 网关服务系统》第3-26节：分布式服务设计(redis)

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/5qCwp](https://t.zsxq.com/5qCwp)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

大家好，我是技术UP主小傅哥。

今天是我们《AI MCP Gateway》项目学习的第3-26节课程。前面我们已经完成了 SSE 和 Streamable HTTP 两种协议的完整开发和验证，接下来要为网关引入分布式 Redis 组件。这一步不是直接做业务功能，而是在打地基——但这个地基不是孤立的，它会从 Infrastructure 层一直贯穿到 Trigger 层，形成一条完整的分布式会话同步链路。

## 一、本章诉求

面试可能会问，为什么现在要引入 Redis，或问，为什么不引入Redis？另外注意，分布式的组件也很多，也可以使用其他的，如 nacos 这样的。

那么，我们之前的会话管理不是用内存 Map 就能搞定吗？没错，在单实例部署下，内存 Map 确实够用。但网关服务天生就是需要多实例部署的——当你面对高并发、高可用的场景时，只靠一个实例扛不住，也不应该只靠一个实例扛。

多实例意味着什么？

- **会话数据共享**：用户在实例A上创建的会话，在实例B上也得能找到。
- **会话变更同步**：实例A新建或删除了会话，实例B需要实时感知。
- **服务重启恢复**：某个实例挂了重启之后，能从 Redis 恢复出之前所有活跃的会话。

这些能力，都不是内存 Map 能给你的。所以，这一节我们把 Redis 组件引入进来，用 Redisson 作为客户端，封装好统一的 Redis 服务接口。同时，沿着 DDD 六边形架构的分层规范，从 Infrastructure → Domain → Case → Trigger，一层层串联出分布式会话同步的完整调用链路。

👩🏻‍🏫敲黑板：基础设施先行，业务功能后置。先把"路"修好，再让"车"跑起来。但修路的时候，要让大家看到这条路通向哪里。

## 二、功能设计

如图，分布式 Redis 组件在工程中的完整调用链路；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-26-01.png" width="950px"/>
</div>

- 首先，在 `ai-mcp-gateway-app` 的 config 包下，新增 `RedisClientConfig` 和 `RedisClientConfigProperties`，负责创建 Redisson 客户端 Bean 和读取配置属性。
- 之后，在 `ai-mcp-gateway-infrastructure` 的 redis 包下，新增 `IRedisService` 接口和 `RedissonService` 实现类。接口提供了键值操作、队列、集合、Map、锁、信号量、布隆过滤器、发布订阅等几乎涵盖 Redis 全部数据结构的操作方法。
- 然后，在 Infrastructure 层的 `adapter/port/SessionPort` 中，通过 `IRedisService` 完成会话元数据的 Redis Map 存储和 Redis Topic 事件发布/订阅。
- 接着，在 Domain 层的 `SessionDistributedService` 中，编排分布式会话的构建、重建、保存、删除、加载等核心领域逻辑。
- 再然后，在 Case 层的 `DistributedRedisService` 中，做事件幂等判断和流程转发——本地已有就不重复创建，本地没有就不重复删除。
- 最后，在 Trigger 层的 `SessionRedisListener` 中，应用启动时订阅 Redis Topic，接收到事件后转发到 Case 层处理。

这就是从底层基础设施到上层触发器的完整链路。每一层各司其职，互不越权。