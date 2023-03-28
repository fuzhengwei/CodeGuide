---
title: 简历：项目描述
lock: no
---

# 简历：项目描述

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=951732965&bvid=BV14s4y1J7GK&cid=1071306571&page=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

## 个人信息

- 姓名
- 电话
- 邮箱
- 毕业时间
- 工作时间
- Github：https://github.com/fuzhengwei
- 开源项目：[vo2dto](https://bugstack.cn/md/product/idea-plugin/vo2dto.html) - IDEA Plugin 自动转换对象插件，5.4k 下载使用

## 毕业院校

- 时间、院校、专业、学位
- 荣誉、成绩

## 专业技能

- 熟练掌握 Java 核心知识、JUC、HashMap、斐波那契散列等，具备良好的面向对象编程思想。
- 熟练掌握 Java 设计模式，如工厂、代理、组合、策略等设计模式，并善用设计原则构建可复用代码。
- 熟练使用 IDEA、Eclipse、Visual Studio Code、Navicat、PostMan、Git、Maven、SVN 等开发工具。
- 深入理解 JVM 底层原理，熟悉 JVM 各类垃圾收集器的使用及核心参数的调优，具备一定的 JVM 调优能力。
- `深入学习 Spring 核心流程模块，如IOC、AOP、依赖倒置等，掌握Spring解决复杂场景所需的分治、抽象和知识（设计模式、设计原则），能从核心原理上解决Spring场景问题。同时，具备基于 Spring 开发 SpringBoot Starter 的技能，减少研发成本，为复杂项目提供通用技术组件。`
- `深入学习 MyBatis 核心流程模块，包括会话、反射、代理、事务和插件，熟练掌握 ORM 框架的设计思想、实现方式和应用价值。并根据需求结合 MyBatis 插件机制，开发企业所需的功能，如数据分页、数据库表路由、监控日志和数据安全等。`
- 深入理解 Spring Boot，Spring Cloud 等微服务框架的设计原理及底层架构，熟悉各种微服务架构设计比如服务注册与发现，服务降级，限流，熔断，服务网关路由设计，服务安全认证架构。
- 熟悉 Dubbo、Zookeeper 等分布式服务协调与治理等技术。
- 熟练掌握 MySql，掌握 MySQL 主从同步，读写分离技术以及集群的搭建，具备一定的 SQL 调优能力。
- 深入理解 Redis 线程模型，熟练掌握 Redis 的核心数据结构的使用场景，熟悉各种缓存高并发的使用场景，比如缓存雪崩，缓存穿透等。
- 熟练掌握分布式场景中的常见的技术问题及解决，比如分布式锁，分布式事务，分布式 session，分布式任务调度。
- 熟悉 RabbitMQ、Kafka 等常用的消息中间件进行消息的异步数据处理。
- 了解分布式搜索引擎 ElasticSearch，并能基于 ELK+Kafka 搭建分布式日志收集系统，以及 x-pack-jdbc 的扩展使用。
- 熟悉 docker 常用命令，能够实现基于 docker+Jenkins 实现自动化部署
- 掌握 Linux 常用命令，了解 Nginx 服务器的反向代理、负载均衡、动静分离等。
- 熟练运用 DDD 四层架构领域驱动设计，构建出易于迭代和维护的工程架构，遵守整洁代码、洋葱架构设计思想。

## 工作经历【在校生无】

- 公司
- 岗位
- 时间
- 职责【可选】
- 成绩【可选】

## 项目经验

### 1. 业务项目

- **项目地址**：[https://bugstack.cn/md/project/lottery/introduce/Lottery抽奖系统.html](https://bugstack.cn/md/project/lottery/introduce/Lottery抽奖系统.html)
- **项目名称**：营销活动平台 - Lottery 微服务抽奖系统
- **系统架构**：以 DDD 领域驱动设计开发，微服务拆分的分布式系统架构
- **核心技术**：SpringBoot、MyBatis、Dubbo、MQ、MySQL、XDB-Router、ES、ZK
- **项目描述**：抽奖系统是营销平台的重要微服务之一，可以满足 C 端人群的需求，例如拉新、促活、留存等。该系统运用抽象、分治和 DDD 知识，拆解服务边界，凝练领域服务功能。围绕抽奖服务建设领域服务，包括规则引擎、抽奖策略、活动玩法、奖品发放等。这可以满足业务产品快速迭代上线的需求，同时减少研发成本，提高交付效率。
- **核心职责**：
  - 构建以 DDD 分层结构的处理方式，搭建整个抽奖系统架构。运用设计原则和工厂、代理、模板、组合、策略等设计模式的综合使用，搭建易于维护和迭代的系统工程。
  - 鉴于系统内有较多的规则策略过滤，包括准入、人群、风控、A/BTest等需求，为适应系统规模可快速开发和使用的方式，搭建了去中心化的量化人群规则引擎组件。通过业务需求对逻辑的扩展和内置引擎执行器的使用，完成自由组合的人群过滤服务。这降低了共性功能重复开发所带来的成本问题，并提高了研发效率。
  - 根据实际秒杀峰值场景 `TPS 5000 ~ 8000` 的需求，开发了统一路由组件。该组件不仅可以满足差异化不同字段的分库分表组合，还支持 Redis 库存分片和秒杀滑动库存分块。而且，开发了统一路由 XDB-Router 的 SpringBoot Starter 技术组件。该套组件已经经历了多次大促活动场景的考验，支持横向扩展，可以满足业务规模的快速增长。

### 2. 技术项目

- **项目地址**：[https://bugstack.cn/md/assembly/api-gateway/api-gateway.html](https://bugstack.cn/md/assembly/api-gateway/api-gateway.html)
- **项目名称**：API 网关
- **系统架构**：微服务架构设计、SpringBoot Starter 组件设计、DDD 领域驱动设计
- **核心技术**：SpringBoot、SpringBoot Starter、Netty、NGINX、SHIRO、JWT、Redis、负载均衡、RateLimiter
- **项目描述**：API网关系统用于统一管理RPC（Dubbo）通信接口，通过协议解析和泛化调用统一对外提供HTTP服务的系统。这套系统是微服务架构设计，分为核心通信、启动引擎、注册中心、管理平台以及上报接口服务。这套API网关也是随着对公司传统庞大的单体应用（All in one）拆分为众多的微服务（Microservice）以后，所引入的统一通信管理系统。用于运行在外部HTTP请求与内部RPC服务之间的一个流量入口，实现对外部请求的协议转换、参数校验、鉴权、切量、熔断、限流、监控、风控等各类共性的通用服务。
- **核心职责**：
  - 构建 API 网关整体核心架构分层设计，拆分出核心通信、服务助手、启动引擎、注册中心、上报服务、管理后台，这样6个工程模块。便于后续的高效迭代和维护工作。
  - 分治处理会话流程，将复杂的会话流程划分为多个阶段，以提高处理效率；将连接(RPC\HTTP\其他)抽象为数据源，为数据的读取和写入提供支持；实现HTTP请求参数解析，确保请求参数的正确处理；引入执行器封装服务调用，提供对各种服务的调用支持；集成权限认证组件(Shiro+Jwt)，确保请求的合法性和安全性；实现网关会话鉴权处理，为会话的安全管理提供支持；实现网络通信配置提取，将网络通信的配置信息抽象为可配置的模块，提高配置的灵活性。
  - 设计并实现服务发现组件搭建和注册网关连接、服务配置拉取和组件使用验证、核心通信组件管理和处理服务映射、容器关闭监听和异常管理、订阅服务注册消息驱动网关映射、网关Nginx负载模型配置、动态刷新网关Nginx负载均衡配置和实现网关算力节点动态负载功能。

### 3. 组件项目01

- **项目地址**：[https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%201%20%E7%AB%A0%20%E4%BB%80%E4%B9%88%E6%98%AF%E4%B8%AD%E9%97%B4%E4%BB%B6.html](https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%201%20%E7%AB%A0%20%E4%BB%80%E4%B9%88%E6%98%AF%E4%B8%AD%E9%97%B4%E4%BB%B6.html)
- **项目名称**：自动化API提取和交付质量分析服务
- **系统架构**：基于 IDEA Plugin 插件开发与 ASM 字节码增强技术，采集工程运行信息
- **核心技术**：IDEA Plugin SDK、ASM、Swing、MySQL、SpringBoot、TTL
- **项目描述**：这是一款用于帮助研发与测试，建立起标准可调试的动态自动化 API 服务，提升交付质量的系统。插件以 IntelliJ IDEA 为底座，通过 SDK 的方式结合字节码增强技术，采集 IntelliJ IDEA 单元测试运行时的接口信息，包括：工程名称、开发分支、开发人员、接口名称、出参、入参、异常、耗时等，并把这些通过 Socket 回传到数据中心，处理为整个工程接口的全地图，让整个工程从开发、调试、提测到交付都成透明化，提高整体的交付质量。
- **核心职责**：
  - 以架构师的职责调研如何降低每次开发到提测阶段，所需要人工编写接口文档的成本。以及通过什么方式无侵入式的处理接口文档的生产和维护。
  - 通过对 ASM、Javassist、Byte-Buddy 等字节码框架的调研和测试，找到符合当前场景所需要的字节码增强框架，其中 Byte-Buddy 上手难度更小，更易于后续其他研发共同开发。
  - 运用 IDEA Plugin SDK 插件的开发技术，拦截运行动作，插入字节码增强组件包，将采集信息回传到 API 数据中心进行分析、处理和提供最终的 API 文档。

### 4. 组件项目02

- **项目地址**：[https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%201%20%E7%AB%A0%20%E4%BB%80%E4%B9%88%E6%98%AF%E4%B8%AD%E9%97%B4%E4%BB%B6.html](https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%201%20%E7%AB%A0%20%E4%BB%80%E4%B9%88%E6%98%AF%E4%B8%AD%E9%97%B4%E4%BB%B6.html)
- **项目名称**：ES-ORM 框架开发
- **系统架构**：技术迁移 ORM 开发到 ES 使用上，建设 SpringBoot Starter
- **核心技术**：SpringBoot、ES、MyBatis、dom4j、x-pack-jdbc
- **项目描述**：开发 ES-ORM 框架，降低研发使用 ES 数据查询硬编码方式的维护和迭代成本。通过 ORM 框架对 XML 和注解配置的方式，解析和映射语句处理器，代理 IESDAO 接口为具体的处理对象，并把对象通过扫描符合的路径和注解运用 BeanDefinitionRegistryPostProcessor 把代理对象注册到 Spring Bean 容器中进行统一管理和使用，最终完成 ORM 框架的数据查询和封装操作。
- **核心职责**：
  - 负责组内的脚手架和提效工具的建设，对系统中通用共性的功能进行摘取凝练成统一的组件进行使用。这其中不只包括ES-ORM框架，还包括缓存组件、秒杀组件、服务治理、全链路监控等。
  - 对 ES-ORM 框架功能模块进行拆解和实现，分为；解析入口、数据源、代理层、绑定层、执行模块、异常处理、缓存服务等。
  - 分阶段功能验证和使用，逐步本地化，将内部的服务通过 SPI 的机制进行包装整合，允许组内其他场景诉求的扩展。

### 5. 组件项目03

- **项目地址**：[https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%203%20%E7%AB%A0%20%E6%9C%8D%E5%8A%A1%E6%B2%BB%E7%90%86%EF%BC%8C%E7%BB%9F%E4%B8%80%E7%99%BD%E5%90%8D%E5%8D%95%E6%8E%A7%E5%88%B6.html](https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%203%20%E7%AB%A0%20%E6%9C%8D%E5%8A%A1%E6%B2%BB%E7%90%86%EF%BC%8C%E7%BB%9F%E4%B8%80%E7%99%BD%E5%90%8D%E5%8D%95%E6%8E%A7%E5%88%B6.html)
- **项目名称**：服务治理 SpringBoot 中间件 - 凝练通用共性功能，降低开发成本，提高交付效率
- **系统架构**：SpringBoot Starter 组件开发
- **核心技术**：熔断、降级、限流、切量、白名单、人群控制
- **项目描述**：该SpringBoot Starter中间件实现了熔断、降级、限流、切量、白名单等服务治理功能，减少了开发工作量和出错风险。利用SpringBoot的自动化配置机制简化了集成和使用，并提供了可扩展接口，以满足不同场景的需求。
- **核心职责**：
  - 鉴于组内同类需求的重复开发，设计并实现服务治理 SpringBoot Starter 中间件，提高开发效率和降低重复开发成本。 该中间件的核心功能包括服务治理中的熔断、降级、限流、切量和白名单等。
  - 通过利用SpringBoot的自动化配置机制，该中间件可以简化集成和使用，同时提供足够的配置选项以满足不同场景的需求。
  - 此外，该中间件还提供了可扩展的接口，方便用户根据自身需求扩展功能，从而更好地满足不同的业务需求。

## 自我评价

- 把自己描述成有技术追求的、有团队精神、有奋斗品质的优秀好青年。