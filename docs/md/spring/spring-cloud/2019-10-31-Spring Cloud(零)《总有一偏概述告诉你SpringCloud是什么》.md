---
layout: post
category: itstack-demo-springcloud
title: Spring Cloud(零)《总有一偏概述告诉你SpringCloud是什么》
tagline: by 付政委
tag: [spring,itstack-demo-springcloud]
excerpt: 为了更好的实现领域驱动设计的落地，不仅要在设计思路上做到领域职责清晰、系统边界明确，还需要使用到Spring Boot、Spring Cloud框架服务体系来更好的构建微服务。后续部分章节将针对Spring Cloud的使用以及有益于构建微服务的知识技能做系列案例整理，以最终完成架构设计专题案例。网上不免有很多优秀的文章，但为了系统的学习更需要事必躬亲，亲力亲为。
lock: need
---

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
为了更好的实现领域驱动设计的落地，不仅要在设计思路上做到领域职责清晰、系统边界明确，还需要使用到Spring Boot、Spring Cloud框架服务体系来更好的构建微服务。后续部分章节将针对Spring Cloud的使用以及有益于构建微服务的知识技能做系列案例整理，以最终完成架构设计专题案例。网上不免有很多优秀的文章，但为了系统的学习更需要事必躬亲，亲力亲为。

## 内容概述
![https://spring.io](https://bugstack.cn/assets/images/pic-content/2019/10/springboot-1-01.png)

>Built directly on Spring Boot's innovative approach to enterprise Java, Spring Cloud simplifies distributed, microservice-style architecture by implementing proven patterns to bring resilience, reliability, and coordination to your microservices.

Spring Cloud 是一系列框架的有序集合。它利用 Spring Boot 的开发便利性巧妙地简化了分布式系统基础设施的开发，如服务发现注册、配置中心、消息总线、负载均衡、断路器、数据监控等，都可以用 Spring Boot 的开发风格做到一键启动和部署。Spring 并没有重复制造轮子，它只是将目前各家公司开发的比较成熟、经得起实际考验的服务框架组合起来，通过 Spring Boot 风格进行再封装屏蔽掉了复杂的配置和实现原理，最终给开发者留出了一套简单易懂、易部署和易维护的分布式系统开发工具包。

## 微服务与SpringCloud

微服务的概念源于Martin Fowler于2014年3月25日写的一篇文章https://martinfowler.com/articles/microservices.html

微服务架构模式（Microservices Architecture Pattern）的目的是将大型的、复杂的、长期运行的应用程序构建为一组相互配合的服务，每个服务都可以很容易得局部改良。 Micro 这个词意味着每个服务都应该足够小，但是，这里的小不能用代码量来比较，而应该是从业务逻辑上比较 —— 符合 SRP(单一职责) 原则的才叫微服务。

>设计模式六大原则
- 单一职责原则
- 里氏替换原则
- 依赖倒置原则
- 接口隔离原则
- 迪米特原则
- 开闭原则

关于微服务的一个形象表达：
![微服务框架](https://bugstack.cn/assets/images/pic-content/2019/10/springboot-1-02.png)

X轴：运行多个负载均衡器之后的运行实例
Y轴：将应用进一步分解为微服务（分库）
Z轴：大数据量时，将服务分区（分表）

而Spring Cloud是Spring为微服务架构思想做的一个一站式实现。从某种程度是可以简单的理解为，微服务是一个概念、一个项目开发的架构思想。Spring Cloud 是微服务架构的一种 Java 实现。

简单来说，服务化的核心就是将传统的一站式应用根据业务拆分成一个一个的服务，而微服务在这个基础上要更彻底地去耦合（不再共享 DB、KV，去掉重量级 ESB），并且强调 DevOps 和快速演化。这就要求我们必须采用与一站式时代、泛 SOA 时代不同的技术栈，而 Spring Cloud 就是其中的佼佼者。

>DevOps 是英文 Development 和 Operations 的合体，他要求开发、测试、运维进行一体化的合作，进行更小、更频繁、更自动化的应用发布，以及围绕应用架构来构建基础设施的架构。这就要求应用充分的内聚，也方便运维和管理。这个理念与微服务理念不谋而合。

## 使用微服务架构的好处
复杂度可控：在将应用分解的同时，规避了原本复杂度无止境的积累。每一个微服务专注于单一功能，并通过定义良好的接口清晰表述服务边界。由于体积小、复杂度低，每个微服务可由一个小规模开发团队完全掌控，易于保持高可维护性和开发效率。

独立部署：由于微服务具备独立的运行进程，所以每个微服务也可以独立部署。当某个微服务发生变更时无需编译、部署整个应用。由微服务组成的应用相当于具备一系列可并行的发布流程，使得发布更加高效，同时降低对生产环境所造成的风险，最终缩短应用交付周期。

技术选型灵活：微服务架构下，技术选型是去中心化的。每个团队可以根据自身服务的需求和行业发展的现状，自由选择最适合的技术栈。由于每个微服务相对简单，故需要对技术栈进行升级时所面临的风险就较低，甚至完全重构一个微服务也是可行的。

容错高：当某一组建发生故障时，在单一进程的传统架构下，故障很有可能在进程内扩散，形成应用全局性的不可用。在微服务架构下，故障会被隔离在单个服务中。若设计良好，其他服务可通过重试、平稳退化等机制实现应用层面的容错。

可扩展：每个服务可以各自进行 x 扩展和 z 扩展，而且，每个服务可以根据自己的需要部署到合适的硬件服务器上。当应用的不同组件在扩展需求上存在差异时，微服务架构便体现出其灵活性，因为每个服务可以根据实际需求独立进行扩展。

## Spring Cloud 技术总览

![](https://bugstack.cn/assets/images/pic-content/2019/10/springboot-1-03.png)

- 服务治理：这是 Spring Cloud 的核心。目前 Spring Cloud 主要通过整合 Netflix 的相关产品来实现这方面的功能（Spring Cloud Netflix），包括用于服务注册和发现的 Eureka，调用断路器 Hystrix，调用端负载均衡 Ribbon，Rest 客户端 Feign，智能服务路由 Zuul，用于监控数据收集和展示的 Spectator、Servo、Atlas，用于配置读取的 Archaius 和提供 Controller 层 Reactive 封装的 RxJava。除此之外，针对于服务的注册和发现，除了 Eureka，Spring Cloud 也整合了 Consul 和 Zookeeper 作为备选，但是因为这两个方案在 CAP 理论上都遵循 CP 而不是 AP（下一篇会详细介绍这点），所以官方并没有推荐使用。

- 分布式链路监控：Spring Cloud Sleuth 提供了全自动、可配置的数据埋点，以收集微服务调用链路上的性能数据，并发送给 Zipkin 进行存储、统计和展示。

- 消息组件：Spring Cloud Stream 对于分布式消息的各种需求进行了抽象，包括发布订阅、分组消费、消息分片等功能，实现了微服务之间的异步通信。Spring Cloud Stream 也集成了第三方的 RabbitMQ 和 Apache Kafka 作为消息队列的实现。而 Spring Cloud Bus 基于 Spring Cloud Stream，主要提供了服务间的事件通信（比如刷新配置）。

- 配置中心：基于 Spring Cloud Netflix 和 Spring Cloud Bus，Spring 又提供了 Spring Cloud Config，实现了配置集中管理、动态刷新的配置中心概念。配置通过 Git 或者简单文件来存储，支持加解密。

- 安全控制：Spring Cloud Security 基于 OAuth2 这个开放网络的安全标准，提供了微服务环境下的单点登录、资源授权、令牌管理等功能。

- 命令行工具：Spring Cloud Cli 提供了以命令行和脚本的方式来管理微服务及 Spring Cloud 组件的方式。

- 集群工具：Spring Cloud Cluster 提供了集群选主、分布式锁（暂未实现）、一次性令牌（暂未实现）等分布式集群需要的技术组件。

**Eureka**
Eureka 是 Netflix 开源的一款提供服务注册和发现的产品，它提供了完整的 Service Registry 和 Service Discovery 实现。也是 Spring Cloud 体系中最重要最核心的组件之一。它将所有的可以提供的服务都注册到它这里来管理，其它各调用者需要的时候去注册中心获取，然后再进行调用，避免了服务之间的直接调用，方便后续的水平扩展、故障转移等。

**Hystrix**
在微服务架构中通常会有多个服务层调用，基础服务的故障可能会导致级联故障，进而造成整个系统不可用的情况，这种现象被称为服务雪崩效应。服务雪崩效应是一种因 “服务提供者” 的不可用导致 “服务消费者” 的不可用，并将不可用逐渐放大的过程。

**Hystrix Dashboard 和 Turbine**
当熔断发生的时候需要迅速的响应来解决问题，避免故障进一步扩散，那么对熔断的监控就变得非常重要。熔断的监控现在有两款工具：Hystrix-dashboard 和 Turbine

Hystrix-dashboard 是一款针对 Hystrix 进行实时监控的工具，通过 Hystrix Dashboard 我们可以直观地看到各 Hystrix Command 的请求响应时间，请求成功率等数据。但是只使用 Hystrix Dashboard 的话，你只能看到单个应用内的服务信息，这明显不够。我们需要一个工具能让我们汇总系统内多个服务的数据并显示到 Hystrix Dashboard 上，这个工具就是 Turbine.
![Turbine](https://bugstack.cn/assets/images/pic-content/2019/10/springboot-1-05.png)

**Spring Cloud Config**
随着微服务不断的增多，每个微服务都有自己对应的配置文件。在研发过程中有测试环境、UAT 环境、生产环境，因此每个微服务又对应至少三个不同环境的配置文件。这么多的配置文件，如果需要修改某个公共服务的配置信息，如：缓存、数据库等，难免会产生混乱，这个时候就需要引入 Spring Cloud 另外一个组件：Spring Cloud Config。

Spring Cloud Config 是一个解决分布式系统的配置管理方案。它包含了 Client 和 Server 两个部分，Server 提供配置文件的存储、以接口的形式将配置文件的内容提供出去，Client 通过接口获取数据、并依据此数据初始化自己的应用。

其实就是 Server 端将所有的配置文件服务化，需要配置文件的服务实例去 Config Server 获取对应的数据。将所有的配置文件统一整理，避免了配置文件碎片化。配置中心 git 实例参考：配置中心 git 示例；

如果服务运行期间改变配置文件，服务是不会得到最新的配置信息，需要解决这个问题就需要引入 Refresh。可以在服务的运行期间重新加载配置文件，具体可以参考这篇文章：配置中心 svn 示例和 refresh

当所有的配置文件都存储在配置中心的时候，配置中心就成为了一个非常重要的组件。如果配置中心出现问题将会导致灾难性的后果，因此在生产中建议对配置中心做集群，来支持配置中心高可用性。

**Spring Cloud Bus**
上面的 Refresh 方案虽然可以解决单个微服务运行期间重载配置信息的问题，但是在真正的实践生产中，可能会有 N 多的服务需要更新配置，如果每次依靠手动 Refresh 将是一个巨大的工作量，这时候 Spring Cloud 提出了另外一个解决方案：Spring Cloud Bus

Spring Cloud Bus 通过轻量消息代理连接各个分布的节点。这会用在广播状态的变化（例如配置变化）或者其它的消息指令中。Spring Cloud Bus 的一个核心思想是通过分布式的启动器对 Spring Boot 应用进行扩展，也可以用来建立一个或多个应用之间的通信频道。目前唯一实现的方式是用 AMQP 消息代理作为通道。

Spring Cloud Bus 是轻量级的通讯组件，也可以用在其它类似的场景中。有了 Spring Cloud Bus 之后，当我们改变配置文件提交到版本库中时，会自动的触发对应实例的 Refresh，

**Spring Cloud Zuul**
在微服务架构模式下，后端服务的实例数一般是动态的，对于客户端而言很难发现动态改变的服务实例的访问地址信息。因此在基于微服务的项目中为了简化前端的调用逻辑，通常会引入 API Gateway 作为轻量级网关，同时 API Gateway 中也会实现相关的认证逻辑从而简化内部服务之间相互调用的复杂度。

Spring Cloud 体系中支持 API Gateway 落地的技术就是 Zuul。Spring Cloud Zuul 路由是微服务架构中不可或缺的一部分，提供动态路由，监控，弹性，安全等的边缘服务。Zuul 是 Netflix 出品的一个基于 JVM 路由和服务端的负载均衡器。

它的具体作用就是服务转发，接收并转发所有内外部的客户端调用。使用 Zuul 可以作为资源的统一访问入口，同时也可以在网关做一些权限校验等类似的功能。

**链路跟踪**
随着服务的越来越多，对调用链的分析会越来越复杂，如服务之间的调用关系、某个请求对应的调用链、调用之间消费的时间等，对这些信息进行监控就成为一个问题。在实际的使用中我们需要监控服务和服务之间通讯的各项指标，这些数据将是我们改进系统架构的主要依据。因此分布式的链路跟踪就变的非常重要，Spring Cloud 也给出了具体的解决方案：Spring Cloud Sleuth 和 Zipkin
![链路跟踪，来自谷歌文档](https://bugstack.cn/assets/images/pic-content/2019/08/17387004-c9295b1ffd21eb27.png)

Spring Cloud Sleuth 为服务之间调用提供链路追踪。通过 Sleuth 可以很清楚的了解到一个服务请求经过了哪些服务，每个服务处理花费了多长时间。从而让我们可以很方便的理清各微服务间的调用关系。

Zipkin 是 Twitter 的一个开源项目，允许开发者收集 Twitter 各个服务上的监控数据，并提供查询接口

分布式链路跟踪需要 Sleuth+Zipkin 结合来实现

## 综上总结
Spring Cloud 各个组件如何来配套使用：
![](https://bugstack.cn/assets/images/pic-content/2019/10/springboot-1-07.png)

从上图可以看出 Spring Cloud 各个组件相互配合，合作支持了一套完整的微服务架构。

- 其中 Eureka 负责服务的注册与发现，很好将各服务连接起来
- Hystrix 负责监控服务之间的调用情况，连续多次失败进行熔断保护
- Hystrix dashboard，Turbine 负责监控 Hystrix 的熔断情况，并给予图形化的展示
- Spring Cloud Config 提供了统一的配置中心服务
- 当配置文件发生变化的时候，Spring Cloud Bus 负责通知各服务去获取最新的配置信息
- 所有对外的请求和服务，我们都通过 Zuul 来进行转发，起到 API 网关的作用
- 最后我们使用 Sleuth+Zipkin 将所有的请求数据记录下来，方便我们进行后续分析

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**SpringCloud专题**」获取本文源码&更多原创专题案例！


