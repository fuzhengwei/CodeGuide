---
title: DDD 工程模型
lock: need
---

# DDD 工程模型 - 在分布式微服务架构下，MVC比DDD，要乱的多！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?isOutside=true&aid=1106396955&bvid=BV1Hw4m1k76m&cid=1626093529&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

大家好，我是技术UP主小傅哥。

凡是做到架构师岗位的，都是具有一定技术思维敏感性的，不会主观评价好和坏，但能推演出业务与技术的迭代发展被动熵增与减熵增的意识。就像拿MVC与DDD对比，也能确切的感受到，在架构方面对比于单体应用的分布式架构，是要额外引入非常多的技术栈使用。但这些模块在 MVC 下并没有设计好如何"安置"他们，就像一个老城区里突然引入了很多外来人口，不知道怎么安置一样。**不过 DDD 就像雄安新区做了整体规划！**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/ddd-easy-guide-03-00.gif" width="150px">
</div>

**什么是系统的工程结构，工程框架的作用是什么？**

其实，工程结构的存在作用目的，是为了承载工程系统开发的模型划分，定义工程服务开发过程中实施标准。说白了，就是你在工程实现时，在哪个层访问数据库、哪个层使用缓存、哪个层调用外部接口、哪个层做功能实现，这就是工程框架结构定义的目的。

但在 `Service + 贫血模型` 的三层结构开发指导下，是没有细分出面向对象工程结构设计的趋于划分的。所以在通常意义的 MVC 下，开发过程所有需要的内容，都会堆砌到 Service 实现类中。这也是为什么 DDD 领域驱动设计的落地工程结构，会出现；洋葱架构、整洁架构、菱形架构、六边形架构等这些架构模型。因为我们需要更细致的划分，来承载 DDD 设计概念中映射的领域、仓储、适配、编排、触发，并重视面向对象过程。—— 其实你一上学，学Java就开始学面向对象了，只不过一点点在忘记它。

>本节是DDD概率系列的第3节，讲解 DDD 架构设计，在 [bugstack.cn](https://bugstack.cn) 路书中有完整系列内容，可以查阅。

## 一、为啥需要架构

说到开发代码为啥需要架构，就想买了个房子，为啥要隔出厨房、客厅、卧室、卫生间一样，核心目的就是让不同的职责分配到不同的区域内。虽然在代码中是没有马桶要放卫生间、沙发要放客厅、床要放卧室。但他有一些列的科目信息要引入到工程。

**在工程开发时会涉及到的核心科目；**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/ddd-easy-guide-03-01.png" width="550px">
</div>

如；统一的异常、数据库的连接、日志的打印、外部服务的调用、消息的监听、任务的轮训以及服务的实现等一些列的东西要处理，分配到不同的工程包下承载。在 DDD 之前，我们一直用 MVC 的分层结构承接这些内容；

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/ddd-easy-guide-03-02.png" width="550px">
</div>

通用的、配置的、组件的、持久化的、内部的、外部的，在以往的单体应用时代开发下，其实是没有这么多东西的，那时候的工程结构都偏向于 Service + 贫血模型实现。

但随着微服务的演进，越来越多的内容被填充到工程中，这个时候你细心的查看架构，就会发现原本的 MVC 结构其实已经变的非常混乱了。一个 Service 中为了实现自己的功能，要引入一堆的东西，这些原子的功能与 Service 自身的服务耦合在一块。也导致了工程的维护成本越来越大。 

>这样的三层工程结构分配方式，对于要承载庞大的分布式技术栈体系显然是有点小马拉大车，三缸机带不动SUV一样。

## 二、工程结构设计

2004年，Eric Evans 在发表了一部名为《Domain Driven Design》的著作。2005年 Alistair Cockburn 提出的“六边形关系图”理论，2008年 Jeffrey Palermo 提出了洋葱架构。虽然这些架构并不是专门为 DDD 而出，但巧的是这些架构都在 DDD 一书发表之后陆续推出新的架构模型。同时这些架构的分层设计方式也都与 DDD 非常契合，在这些架构下也可以很好的落地 DDD 设计方法。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/ddd-easy-guide-03-03.png" width="750px">
</div>
无论是六边形架构，还是洋葱架构，或是 张毅老师 - http://zhangyi.xyz/ 提到的南向网关/北向网关的菱形架构，他们的目标都是以领域服务为核心，隔离内部实现与外部资源的耦合。

在 DDD 分层架构下，以支撑 domain 核心领域实现拆分出基础设施（infrastructure），来承接对外部资源的调用。触发器（trigger）向外部提供服务。之后 app 为应用启动、api 为接口定义、types 为通用信息、case 为编排。

在这样一套结构下，用于开发工程的各项科目也可以被优雅的分配到各个分层结构了。相对于 Service + 数据模型的贫血模型结构，现在就主要以 domain 为核心开发业务功能，不会在 domain 工程模块下，引入其他各类外部组件了，这样就可以更加关心业务功能开发。

之后是这样的思想映射到工程中，常见的分层结构会有两套，一套是整洁分层，另外一套是六边形分层。

### 1. 整洁架构 - 工程结构

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/ddd-easy-guide-03-04.png" width="750px">
</div>

- 整洁架构的分包形式，会将所有的外部依赖使用和工程内要对外的，统一定义到适配器层。这里可以理解为对适配和对内适配。
- 阿里的 cola 偏整洁架构 [阿里架构 cola 的相关内容](https://www.aliyun.com/sswb/610119.html)

### 2. 六边形架构 - 工程结构

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/ddd-easy-guide-03-05.png" width="750px">
</div>

六边形架构，会把本身提供到外部的放到trigger，让接口调用、消息监听、任务调度，都可以统一一个入口处理。而对于需要调用外部同类的能力统一放到 infrastructure 基础设施层，包括；数据库、缓存、配置、调用其他方的接口。

## 三、领域模型设计

虽然大家用的都是 DDD，也都有对应的模块和分包，但在细节之处还是会有一些差异。就像家里的家庭成员，衣服、裤子、鞋子，是所有人的衣服都放一起，还是每个人有独立的衣柜只放自己的。这块是有差异的。另外这东西没有绝对的好和坏，就像厨房里的碗筷是是放一起的，卫生间的马桶也是共用的，这说明分包也是需要按照最符合自己所需来设定。

### 1. 分包方式

如下，是两种分包方式；

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/ddd-easy-guide-03-06.png" width="750px">
</div>

- 方式1；DDD 领域科目类型分包，类型之下写每个业务逻辑。
- 方式2；业务领域分包，每个业务领域之下有自己所需的 DDD 领域科目。

比如，你现在一个工程下有用户、积分、抽奖、支付，（紧凑的聚合类微服务有时候更易于维护），那么这些包一种是分为独立的业务包方式2这种，另外一种就是大家都在一个坛子里吃饭，要啥去各个地方找。所以你更倾向于那种呢？

### 2. 领域模型

DDD 领域驱动设计的中心，主要在于领域模型的设计，以领域所需驱动功能实现和数据建模。一个领域服务下面会有多个领域模型，每个领域模型都是一个充血结构。**一个领域模型 = 一个充血结构**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ddd-01.png" width="750px">
</div>

- model 模型对象；
  - aggreate：聚合对象，实体对象、值对象的协同组织，就是聚合对象。
  - entity：实体对象，大多数情况下，实体对象(Entity)与数据库持久化对象(PO)是1v1的关系，但也有为了封装一些属性信息，会出现1vn的关系。
  - valobj：值对象，通过对象属性值来识别的对象 By 《实现领域驱动设计》
- repository 仓储服务；从数据库等数据源中获取数据，传递的对象可以是聚合对象、实体对象，返回的结果可以是；实体对象、值对象。因为仓储服务是由基础层(infrastructure) 引用领域层(domain)，是一种依赖倒置的结构，但它可以天然的隔离PO数据库持久化对象被引用。
- service 服务设计；这里要注意，不要以为定义了聚合对象，就把超越1个对象以外的逻辑，都封装到聚合中，这会让你的代码后期越来越难维护。聚合更应该注重的是和本对象相关的单一简单封装场景，而把一些重核心业务方到 service 里实现。**此外；如果你的设计模式应用不佳，那么无论是领域驱动设计、测试驱动设计还是换了三层和四层架构，你的工程质量依然会非常差。**
- 对象解释
  - DTO 数据传输对象 (data transfer object)，DAO与业务对象或数据访问对象的区别是：DTO的数据的变异子与访问子（mutator和accessor）、语法分析（parser）、序列化（serializer）时不会有任何存储、获取、序列化和反序列化的异常。即DTO是简单对象，不含任何业务逻辑，但可包含序列化和反序列化以用于传输数据。

## 四、分层调用链路

接下来我们把 DDD 的分层架构平铺展开，看看从一个接口的实现到各个模块分层中的调用链路关系是什么样的。这样在做自己的代码开发中也可以参考到应该把什么的功能分配到哪个模块中处理。

![](https://bugstack.cn/images/roadmap/tutorial/road-map-ddd-05.png)

从APP层、触发器层、应用层，这三块主要对领域层的上下文逻辑封装、触发式(MQ、HTTP、JOB)使用，并最终在应用层中打包发布上线。这一部分的都是使用的处理，所以也不会有太复杂的操作。

当进入领域层开始，也是智力集中体现的开始了。所有你对工程的抽象能力，都在这一块区域体现。

**关于对象定义**；vo、po、dto、entity、aggregate、req、res、response

以下描述使用场景为，在 DDD 领域驱动架构下（六边形、整洁、COLA）

domain 领域层；
- entity，实体对象，如雇员用户的基本信息、订单信息、配送信息
- vo（value object），值对象，用于描述实体对象信息。如一个人，这个雇员用户的基本 level 枚举值对象、这个人居住地址四级信息对象。这些对象不具有唯一性，也就是不具有生命特征。就像你，之后你的衣服，你的胡子。
- aggregate，聚合对象，当我们要写一笔订单入口的时候，需要做事务，事务如果需要一组对象；订单记录、账户记录、库存记录等，这些实体对象+值对象，写入到聚合对象内，一起提交过去。

* 以前的 MVC 下的 XXXVo、XxxReq、XxxRes，现在被领域细分成各个模块下的，实体、聚合、值对象了。*

infrastructure 基础设施层；
- po 数据库持久化对象，用于映射数据库表字段的。这个对象不要做业务流程，只提供数据库数据
- dto 数据传输对象，这个对象也会在基础设施层出现，用于与外部的接口对接。比如 rpc 接口、http 接口，出入参的对象，都叫做数据传输对象。命名为 XxxRequestDTO、XxxResponseDTO 支付包的sdk包里就是这样命名的。

api 层：
- dto 对象，接口的出入参，数据传输对象。命名为 XxxRequestDTO、XxxResponseDTO
- response<?> 对象，包装结果对象，提供 code、info、data，准确描述错误码以及data数据，data数据是泛型，用于包装 XxxResponseDTO 结果。当你f12浏览器，一些互联网的web服务，观察他的接口，就会看到这样的结构。

case 编排层：
- 这一层承接 web 的接口编排动作，串联 domain 领域流程。通常2个方案，一个是引入 api 层，定义的对象。另外一个就是多一层转换，在一层定义 api 层对应的 XxxRequestDTO -> XxxRequest、XxxResponseDTO -> XxxResponse

## 五、工程架构案例

### 1. 环境

- JDK 1.8
- Maven 3.8.6
- SpringBoot 2.7.2
- MySQL 5.7 - 如果你使用 8.0 记得更改 pom.xml 中的 mysql 引用
- Dubbo - [https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/reference-manual/registry/multicast/](https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/reference-manual/registry/multicast/) 文档&广播模式地址说明

### 2. 架构

- **源码**：[`https://gitcode.net/KnowledgePlanet/road-map/xfg-frame-ddd`](https://gitcode.net/KnowledgePlanet/road-map/xfg-frame-ddd)
- **树形**：`安装 brew install tree` `IntelliJ IDEA Terminal 使用 tree`

```java
.
├── README.md
├── docs
│   ├── dev-ops
│   │   ├── environment
│   │   │   └── environment-docker-compose.yml
│   │   ├── siege.sh
│   │   └── skywalking
│   │       └── skywalking-docker-compose.yml
│   ├── doc.md
│   ├── sql
│   │   └── road-map.sql
│   └── xfg-frame-ddd.drawio
├── pom.xml
├── xfg-frame-api
│   ├── pom.xml
│   ├── src
│   │   └── main
│   │       └── java
│   │           └── cn
│   │               └── bugstack
│   │                   └── xfg
│   │                       └── frame
│   │                           └── api
│   │                               ├── IAccountService.java
│   │                               ├── IRuleService.java
│   │                               ├── model
│   │                               │   ├── request
│   │                               │   │   └── DecisionMatterRequest.java
│   │                               │   └── response
│   │                               │       └── DecisionMatterResponse.java
│   │                               └── package-info.java
│   └── xfg-frame-api.iml
├── xfg-frame-app
│   ├── Dockerfile
│   ├── build.sh
│   ├── pom.xml
│   ├── src
│   │   ├── main
│   │   │   ├── bin
│   │   │   │   ├── start.sh
│   │   │   │   └── stop.sh
│   │   │   ├── java
│   │   │   │   └── cn
│   │   │   │       └── bugstack
│   │   │   │           └── xfg
│   │   │   │               └── frame
│   │   │   │                   ├── Application.java
│   │   │   │                   ├── aop
│   │   │   │                   │   ├── RateLimiterAop.java
│   │   │   │                   │   └── package-info.java
│   │   │   │                   └── config
│   │   │   │                       ├── RateLimiterAopConfig.java
│   │   │   │                       ├── RateLimiterAopConfigProperties.java
│   │   │   │                       ├── ThreadPoolConfig.java
│   │   │   │                       ├── ThreadPoolConfigProperties.java
│   │   │   │                       └── package-info.java
│   │   │   └── resources
│   │   │       ├── application-dev.yml
│   │   │       ├── application-prod.yml
│   │   │       ├── application-test.yml
│   │   │       ├── application.yml
│   │   │       ├── logback-spring.xml
│   │   │       └── mybatis
│   │   │           ├── config
│   │   │           │   └── mybatis-config.xml
│   │   │           └── mapper
│   │   │               ├── RuleTreeNodeLine_Mapper.xml
│   │   │               ├── RuleTreeNode_Mapper.xml
│   │   │               └── RuleTree_Mapper.xml
│   │   └── test
│   │       └── java
│   │           └── cn
│   │               └── bugstack
│   │                   └── xfg
│   │                       └── frame
│   │                           └── test
│   │                               └── ApiTest.java
│   └── xfg-frame-app.iml
├── xfg-frame-ddd.iml
├── xfg-frame-domain
│   ├── pom.xml
│   ├── src
│   │   └── main
│   │       └── java
│   │           └── cn
│   │               └── bugstack
│   │                   └── xfg
│   │                       └── frame
│   │                           └── domain
│   │                               ├── order
│   │                               │   ├── model
│   │                               │   │   ├── aggregates
│   │                               │   │   │   └── OrderAggregate.java
│   │                               │   │   ├── entity
│   │                               │   │   │   ├── OrderItemEntity.java
│   │                               │   │   │   └── ProductEntity.java
│   │                               │   │   ├── package-info.java
│   │                               │   │   └── valobj
│   │                               │   │       ├── OrderIdVO.java
│   │                               │   │       ├── ProductDescriptionVO.java
│   │                               │   │       └── ProductNameVO.java
│   │                               │   ├── repository
│   │                               │   │   ├── IOrderRepository.java
│   │                               │   │   └── package-info.java
│   │                               │   └── service
│   │                               │       ├── OrderService.java
│   │                               │       └── package-info.java
│   │                               ├── rule
│   │                               │   ├── model
│   │                               │   │   ├── aggregates
│   │                               │   │   │   └── TreeRuleAggregate.java
│   │                               │   │   ├── entity
│   │                               │   │   │   ├── DecisionMatterEntity.java
│   │                               │   │   │   └── EngineResultEntity.java
│   │                               │   │   ├── package-info.java
│   │                               │   │   └── valobj
│   │                               │   │       ├── TreeNodeLineVO.java
│   │                               │   │       ├── TreeNodeVO.java
│   │                               │   │       └── TreeRootVO.java
│   │                               │   ├── repository
│   │                               │   │   ├── IRuleRepository.java
│   │                               │   │   └── package-info.java
│   │                               │   └── service
│   │                               │       ├── engine
│   │                               │       │   ├── EngineBase.java
│   │                               │       │   ├── EngineConfig.java
│   │                               │       │   ├── EngineFilter.java
│   │                               │       │   └── impl
│   │                               │       │       └── RuleEngineHandle.java
│   │                               │       ├── logic
│   │                               │       │   ├── BaseLogic.java
│   │                               │       │   ├── LogicFilter.java
│   │                               │       │   └── impl
│   │                               │       │       ├── UserAgeFilter.java
│   │                               │       │       └── UserGenderFilter.java
│   │                               │       └── package-info.java
│   │                               └── user
│   │                                   ├── model
│   │                                   │   └── valobj
│   │                                   │       └── UserVO.java
│   │                                   ├── repository
│   │                                   │   └── IUserRepository.java
│   │                                   └── service
│   │                                       ├── UserService.java
│   │                                       └── impl
│   │                                           └── UserServiceImpl.java
│   └── xfg-frame-domain.iml
├── xfg-frame-infrastructure
│   ├── pom.xml
│   ├── src
│   │   └── main
│   │       └── java
│   │           └── cn
│   │               └── bugstack
│   │                   └── xfg
│   │                       └── frame
│   │                           └── infrastructure
│   │                               ├── dao
│   │                               │   ├── IUserDao.java
│   │                               │   ├── RuleTreeDao.java
│   │                               │   ├── RuleTreeNodeDao.java
│   │                               │   └── RuleTreeNodeLineDao.java
│   │                               ├── package-info.java
│   │                               ├── po
│   │                               │   ├── RuleTreeNodeLinePO.java
│   │                               │   ├── RuleTreeNodePO.java
│   │                               │   ├── RuleTreePO.java
│   │                               │   └── UserPO.java
│   │                               └── repository
│   │                                   ├── RuleRepository.java
│   │                                   └── UserRepository.java
│   └── xfg-frame-infrastructure.iml
├── xfg-frame-trigger
│   ├── pom.xml
│   ├── src
│   │   └── main
│   │       └── java
│   │           └── cn
│   │               └── bugstack
│   │                   └── xfg
│   │                       └── frame
│   │                           └── trigger
│   │                               ├── http
│   │                               │   ├── Controller.java
│   │                               │   └── package-info.java
│   │                               ├── mq
│   │                               │   └── package-info.java
│   │                               ├── rpc
│   │                               │   ├── AccountService.java
│   │                               │   ├── RuleService.java
│   │                               │   └── package-info.java
│   │                               └── task
│   │                                   └── package-info.java
│   └── xfg-frame-trigger.iml
└── xfg-frame-types
    ├── pom.xml
    ├── src
    │   └── main
    │       └── java
    │           └── cn
    │               └── bugstack
    │                   └── xfg
    │                       └── frame
    │                           └── types
    │                               ├── Constants.java
    │                               ├── Response.java
    │                               └── package-info.java
    └── xfg-frame-types.iml
```

以上是整个🏭工程架构的 tree 树形图。整个工程由  xfg-frame-app 模的 SpringBoot 驱动。这里小傅哥在 domain 领域模型下提供了 order、rule、user 三个领域模块。并在每个模块下提供了对应的测试内容。这块是整个模型的重点，其他模块都可以通过测试看到这里的调用过程。

### 3. 领域

一个领域模型中包含3个部分；model、repository、service 三部分；
- model 对象的定义 【含有；valobj = VO、entity、Aggregate】
- repository 仓储的定义【含有PO】
- service 服务实现

以上3个模块，一般也是大家在使用 DDD 时候最不容易理解的分层。比如 model 里还分为；valobj - 值对象、entity 实体对象、aggregates 聚合对象；
- **值对象**：表示没有唯一标识的业务实体，例如商品的名称、描述、价格等。
- **实体对象**：表示具有唯一标识的业务实体，例如订单、商品、用户等；
- **聚合对象**：是一组相关的实体对象的根，用于保证实体对象之间的一致性和完整性；

关于model中各个对象的拆分，尤其是聚合的定义，会牵引着整个模型的设计。当然你可以在初期使用 DDD 的时候不用过分在意领域模型的设计，可以把整个 domain 下的一个个包当做充血模型结构，这样编写出来的代码也是非常适合维护的。

### 4. 环境(开发/测试/上线)

**源码**：`xfg-frame-ddd/pom.xml`

```pom
<profile>
    <id>dev</id>
    <activation>
        <activeByDefault>true</activeByDefault>
    </activation>
    <properties>
        <profileActive>dev</profileActive>
    </properties>
</profile>
<profile>
    <id>test</id>
    <properties>
        <profileActive>test</profileActive>
    </properties>
</profile>
<profile>
    <id>prod</id>
    <properties>
        <profileActive>prod</profileActive>
    </properties>
</profile>
```

- 定义环境；开发、测试、上线。

**源码**：`xfg-frame-app/application.yml`

```java
spring:
  config:
    name: xfg-frame
  profiles:
    active: dev # dev、test、prod
```

- 除了 pom 的配置，还需要在 application.yml 中指定环境。这样就可以对应的加载到；`application-dev.yml`、`application-prod.yml`、`application-test.yml` 这样就可以很方便的加载对应的配置信息了。尤其是各个场景中切换会更加方便。

### 5. 切面

一个工程开发中，有时候可能会有很多的统一切面和启动配置的处理，这些内容都可以在 xfg-frame-app 完成。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230624-04.png?raw=true" width="450px"/>
</div>

**源码**：`cn.bugstack.xfg.frame.aop.RateLimiterAop`

```java
@Slf4j
@Aspect
public class RateLimiterAop {

    private final long timeout;
    private final double permitsPerSecond;
    private final RateLimiter limiter;

    public RateLimiterAop(double permitsPerSecond, long timeout) {
        this.permitsPerSecond = permitsPerSecond;
        this.timeout = timeout;
        this.limiter = RateLimiter.create(permitsPerSecond);
    }

    @Pointcut("execution(* cn.bugstack.xfg.frame.trigger..*.*(..))")
    public void pointCut() {
    }

    @Around(value = "pointCut()", argNames = "jp")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        boolean tryAcquire = limiter.tryAcquire(timeout, TimeUnit.MILLISECONDS);
        if (!tryAcquire) {
            Method method = getMethod(jp);
            log.warn("方法 {}.{} 请求已被限流，超过限流配置[{}/秒]", method.getDeclaringClass().getCanonicalName(), method.getName(), permitsPerSecond);
            return Response.<Object>builder()
                    .code(Constants.ResponseCode.RATE_LIMITER.getCode())
                    .info(Constants.ResponseCode.RATE_LIMITER.getInfo())
                    .build();
        }
        return jp.proceed();
    }

    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

}
```

**使用**

```java
# 限流配置
rate-limiter:
  permits-per-second: 1
  timeout: 5
```

- 这样你所有的通用配置，又和业务没有太大的关系的，就可以直接写到这里了。—— 具体可以参考代码。

## 六、工程测试验证

- 首先；整个工程由 SpringBoot 驱动，提供了 road-map.sql 测试 SQL 库表语句。你可以在自己的本地mysql上进行执行。它会创建库表。
- 之后；在 application.yml 配置数据库链接信息。
- 之后就可以打开 ApiTest 进行测试了。你可以点击 Application 类的绿色箭头启动工程，使用触发器里的接口调用测试，或者单元测试RPC接口，小傅哥也提供了泛化调用的方式。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230624-05.png?raw=true" width="950px"/>
</div>

- 如果你正常获取了这样的结果信息，那么说明你已经启动成功。接下来就可以对照着DDD的结构进行学习，以及使用这样的工程结构开发自己的项目。
