---
layout: post
category: itstack-demo-ddd
title: DDD专题案例三《领域驱动设计架构基于SpringCloud搭建微服务》
tagline: by 付政委
tag: [ddd,itstack-demo-ddd]
excerpt: 微服务不是泥球小单体，而是具备更加清晰职责边界的完整一体的业务功能服务。领域驱动设计的思想通过Domain的功能域设计，可以把核心功能与支撑功能很好的区分开。而在MVC的设计模式尝尝是把所有的；数据服务、定义的属性类、提供的功能都在一条线上，这样是非常快速的开发方式但在做微服务部署时候确很麻烦。
lock: need
---

# DDD专题案例三《领域驱动设计架构基于SpringCloud搭建微服务》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>DDD项目：[https://t.zsxq.com/jAi2nUf](https://t.zsxq.com/jAi2nUf)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
微服务不是泥球小单体，而是具备更加清晰职责边界的完整一体的业务功能服务。领域驱动设计的思想通过Domain的功能域设计，可以把核心功能与支撑功能很好的区分开。而在MVC的设计模式尝尝是把所有的；数据服务、定义的属性类、提供的功能都在一条线上，这样是非常快速的开发方式但在做微服务部署时候确很麻烦。

按照不同的业务场景可能设计出软件在数据库使用上会有单库单表或者分库分表，如果是一个体量足够需要分库分表设计的系统，在扩容时候它是否能满足你的需求包括；
1. 核心计算不涉及库扩容，但是系统功能都在一起怎么办，已扩容都扩容了很浪费
2. 所有的扩容都涉及到数据库连接数增加，但并不是每个行为都直达到所有库表
3. 持续发展的业务会带来数据激增，将来怎么进行扩展，重新洗数据并不是很好的选择

那么实际开发大泥球架构时，不只是会遇到上面的问题，还可能会遇到工期很赶加个人也不提升效率，反复交接代码扶不过三代等等，因此我们将服务拆分为独立单体具备此核心域完整功能的系统是非常必要的。

如图，是微服务数据库使用的一种思想，我们希望路由层从最开始就被执行，用户分群动态扩展
![微信公众号：bugstack虫洞栈 & 微服务数据库路由](https://bugstack.cn/assets/images/pic-content/2019/10/ddd-03-1.png)

- 本节案例代码：[https://github.com/fuzhengwei/itstack-demo-ddd](https://github.com/fuzhengwei/itstack-demo-ddd)
- 新版DDD讲解：[架构的本质之 DDD 架构](https://bugstack.cn/md/road-map/ddd.html)

## 案例目标
本案例通过使用SpringCloud将我们的服务架构扩展为通过路由调用的微服务
1. 首先通过Eureka作为服务注册与发现中心
2. 然后使用Feign模式作为调用API接口
3. 最后依赖于zuul设置路由转发功能

为了方便测试，本案例会在itstack-demo-ddd-03中建4个工程；
- itstack-demo-ddd-case｛基于DDD的微服务｝
- itstack-demo-ddd-eureka-server｛服务注册与发现｝
- itstack-demo-ddd-feign｛调用方，通过API接口调用｝
- itstack-demo-ddd-zuul｛网关路由组件｝

## 开发环境
1、jdk1.8
2、springboot 2.0.6.RELEASE 以及SpringCloud相关服务
3、idea + maven

## 代码示例

### itstack-demo-ddd-case | 基于DDD的微服务 {本段代码在上一章节已经演示}

```java
itstack-demo-ddd-case
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.demo
    │   │       ├── application
    │   │       │	├── MallRuleService.java	
    │   │       │	└── MallTreeService.java	
    │   │       ├── domain
    │   │       │	├── rule
    │   │       │	│   ├── model
    │   │       │	│   │   ├── aggregates
    │   │       │	│   │   │   └── UserRichInfo.java	
    │   │       │	│   │   └── vo
    │   │       │	│   │       ├── DecisionMatter.java
    │   │       │	│   │       ├── EngineResult.java
    │   │       │	│   │       ├── TreeNodeInfo.java
    │   │       │	│   │       ├── TreeNodeLineInfo.java	
    │   │       │	│   │       └── UserSchool.java	
    │   │       │	│   ├── repository
    │   │       │	│   │   └── IRuleRepository.java	
    │   │       │	│   └── service
    │   │       │	│       ├── engine
    │   │       │	│       │   ├── impl	
    │   │       │	│       │   └── EngineFilter.java	
    │   │       │	│       ├── logic
    │   │       │	│       │   ├── impl	
    │   │       │	│       │   └── LogicFilter.java	
    │   │       │	│       └── MallRuleServiceImpl.java	
    │   │       │	└── tree
    │   │       │	    ├── model
    │   │       │	    │   ├── aggregates
    │   │       │	    │   │   └── TreeCollect.java	
    │   │       │	    │   └── vo
    │   │       │	    │       ├── TreeInfo.java	
    │   │       │	    │       └── TreeRulePoint.java	
    │   │       │	    ├── repository
    │   │       │	    │   └── ITreeRepository.java	
    │   │       │	    └── service
    │   │       │	        └── MallTreeServiceImpl.java	
    │   │       ├── infrastructure
    │   │       │	├── common
    │   │       │	│   └── Constants.java
    │   │       │	├── dao
    │   │       │	│   ├── RuleTreeDao.java
    │   │       │	│   ├── RuleTreeNodeDao.java	
    │   │       │	│   └── RuleTreeNodeLineDao.java	
    │   │       │	├── po
    │   │       │	│   ├── RuleTree.java
    │   │       │	│   ├── RuleTreeConfig.java
    │   │       │	│   ├── RuleTreeNode.java	
    │   │       │	│   └── RuleTreeNodeLine.java		
    │   │       │	├── repository
    │   │       │	│   ├── cache
    │   │       │	│   │   └── RuleCacheRepository.java
    │   │       │	│   ├── mysql
    │   │       │	│   │   ├── RuleMysqlRepository.java	
    │   │       │	│   │   └── TreeMysqlRepository.java
    │   │       │	│   ├── RuleRepository.java	
    │   │       │	│   └── TreeRepository.java	
    │   │       │	└── util
    │   │       │	    └── CacheUtil.java
    │   │       ├── interfaces
    │   │       │	├── dto
    │   │       │	│	├── DecisionMatterDTO.java
    │   │       │	│	└── TreeDTO.java	
    │   │       │	└── DDDController.java
    │   │       └── DDDApplication.java
    │   └── resources	
    │       ├── mybatis
    │       └── application.yml
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```

### itstack-demo-ddd-eureka-server | 服务注册与发现

```java
itstack-demo-ddd-eureka-server
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.demo
    │   │       └── EurekaServerApplication.java
    │   └── resources	
    │       └── application.yml
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```

>EurekaServerApplication.java | 启动服务

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run( EurekaServerApplication.class, args );
    }

}
```

>application.yml | 服务配置

```java
server:
  port: 8989

eureka:
  instance:
    hostname: localhost
  client:
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/

spring:
  application:
    name: itstack-demo-ddd-eureka-server
```

### itstack-demo-ddd-feign | 调用方，通过API接口调用

```java
itstack-demo-ddd-feign
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.demo
    │   │       ├── domain
    │   │       │	└── TreeDTO.java
    │   │       ├── service
    │   │       │	└── MallService.java
    │   │       ├── web
    │   │       │	└── FeignController.java
    │   │       └── FeignApplication.java
    │   └── resources	
    │       └── application.yml
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```

>MallService.java | 通过注册方式调用API

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@FeignClient(value = "itstack-demo-ddd-case")
public interface MallService {

    @RequestMapping(value = "/api/tree/queryTreeSummaryInfo", method = RequestMethod.POST)
    Object queryTreeSummaryInfo(@RequestBody TreeDTO request);

}
```

>FeignApplication.java | 启动服务

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class FeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }

}
```

>application.yml | 服务配置

```java
server:
  port: 9090

spring:
  application:
    name: itstack-demo-ddd-feign

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8989/eureka/
```

### itstack-demo-ddd-zuul| 网关路由组件

```java
itstack-demo-ddd-zuul
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.demo
    │   │       └── ZuulApplication.java
    │   └── resources	
    │       └── application.yml
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```

>ZuulApplication.java | 启动服务

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

}
```

>application.yml | 服务配置{本案例是静态路由，按需可以开发为动态路由}

```java
server:
  port: 9191

spring:
  application:
    name: itstack-demo-ddd-zuul

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8989/eureka/
zuul:
  routes:
    api-a:
      path: /route-a/**
      serviceId: itstack-demo-ddd-feign
```

## 测试验证

按照顺序启动；itstack-demo-ddd-eureka-server、itstack-demo-ddd-case｛可以模拟启动多个｝、itstack-demo-ddd-feign、itstack-demo-ddd-zuul

>访问；http://localhost:8989/ | 服务中心
![微信公众号：bugstack虫洞栈 & 服务中心](https://bugstack.cn/assets/images/pic-content/2019/10/ddd-03-2.png)

>访问：http://localhost:9191/route-a/api/queryTreeSummaryInfo?treeId=10001 | 通过网关路由调用DDD服务接口

![微信公众号：bugstack虫洞栈 & 调用网关接口测试](https://bugstack.cn/assets/images/pic-content/2019/10/ddd-03-3.png)


## 综上总结
1. DDD的设计模式加上SpringBoot与SpringCloud非常适合开发微服务
2. 以上案例可以进行扩展，使不同的用户群体在网关接口调用时就打到不同的服务上
3. 另外目前没有使用dubbo类型的rpc框架，也就是没有对外提供定义接口jar包，后续会进行延展

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**DDD落地案例**」获取本文源码&更多原创专题案例！此外推荐你一个可以上手实践的DDD项目，看看如何从流程中提炼领域设计代码实现，在应用层、领域层以及基础层的仓储实现是如何完成开发和调用的，项目地址：[https://t.zsxq.com/jAi2nUf](https://t.zsxq.com/jAi2nUf)
