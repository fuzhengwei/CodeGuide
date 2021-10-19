---
layout: post
category: itstack-demo-springcloud
title: Spring Cloud(三)《应用服务快速失败熔断降级保护 Hystrix》
tagline: by 付政委
tag: [spring,itstack-demo-springcloud]
excerpt: 在互联网开发中经常会听到雪崩效应，比如某明星发一些状态某猿就要回去加班了！那么为了应对雪崩我们经常会进行服务扩容、添加缓存、优化流程但往往突发的事件依然有击穿缓存、应用负载、数据库IO、网络异常等等带来的风险，所以一些常见的做法有服务降级、限流、熔断，在逐步恢复系统可用率来保护系统。
lock: need
---

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
在互联网开发中经常会听到雪崩效应，比如某明星发一些状态某猿就要回去加班了！那么为了应对雪崩我们经常会进行服务扩容、添加缓存、优化流程但往往突发的事件依然有击穿缓存、应用负载、数据库IO、网络异常等等带来的风险，所以一些常见的做法有服务降级、限流、熔断，在逐步恢复系统可用率来保护系统。

**Hystrix** 是一种熔断降级的中间件，由 Spring Cloud 集成整合后在Ribbon与Fegin中提供使用。
>Hystrix is a latency and fault tolerance library designed to isolate points of access to remote systems, services and 3rd party libraries, stop cascading failure and enable resilience in complex distributed systems where failure is inevitable.

![微信公众号：bugstack虫洞栈 & Hystrix工作原理(官网)](https://bugstack.cn/assets/images/pic-content/2019/11/SpringCloud-3-1.png)

## 案例说明
本案例在itstack-demo-springcloud-02的基础上添加Hystrix服务，当我们的itstack-demo-springcloud-eureka-client尚未启动或主动停止后，我们在调用接口服务时候会进行熔断保护。

## 环境准备
1. jdk 1.8
2. Spring Boot 2.0.6.RELEASE
3. Spring Cloud Finchley.SR2

## 代码示例
```java
itstack-demo-springcloud-03
├── itstack-demo-springcloud-eureka-client
│   └── src
│       └── main
│           ├── java
│           │   └── org.itstack.demo
│           │        ├── web
│           │        │   └── EurekaClientController.java
│           │        └── EurekaClientApplication.java
│           └── resources   
│               └── application.yml
├── itstack-demo-springcloud-eureka-server
│   └── src
│       └── main
│           ├── java
│           │   └── org.itstack.demo
│           │        └── EurekaServerApplication.java
│           └── resources   
│               └── application.yml
├── itstack-demo-springcloud-hystrix-feign
│   └── src
│       └── main
│           ├── java
│           │   └── org.itstack.demo
│           │        ├── service
│           │        │   ├── hystrix
│           │        │   │   └── FeignServiceHystrix.java
│           │        │   └── FeignService.java
│           │        ├── web
│           │        │   └── FeignController.java
│           │        └── FeignApplication.java
│           └── resources   
│               └── application.yml
└── itstack-demo-springcloud-hystrix-ribbon
    └── src
        └── main
            ├── java
            │   └── org.itstack.demo
            │        ├── service
            │        │   └── RibbonService.java
            │        ├── web
            │        │   └── RibbonController.java		
            │        └── RibbonApplication.java
            └── resources   
                └── application.yml
```

**完整代码欢迎关注公众号：bugstack虫洞栈 | 回复“SpringCloud专题”进行下载**

### itstack-demo-springcloud-eureka-client | 服务提供方

提供一个查询用户信息的简单方法，在配置文件中通过修改端口启动2次，模拟双实例应用，为调用方负载做准备。

>web/EurekaClientController.java | 注意@EnableEurekaClient用于向注册中心提供服务

```java
/**
 * 微信公众号：bugstack虫洞栈 | 沉淀、分享、成长，专注于原创专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@EnableEurekaClient
@RestController
public class EurekaClientController {

    @Value("${server.port}")
    private int port;

    @RequestMapping(path = "/api/queryUserInfo", method = RequestMethod.GET)
    public String queryUserInfo(@RequestParam String userId) {
        return "Hi 微信公众号：bugstack虫洞栈 | " + userId + " >: from eureka client port: " + port;
    }

}
```

>EurekaClientApplication.java | 服务启动类

```java
/**
 * 微信公众号：bugstack虫洞栈 | 沉淀、分享、成长，专注于原创专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@SpringBootApplication
public class EurekaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }

}
```

>application.yml | 配置文件链接服务注册中心,8001\8002分别配置启动

```java
server:
  port: 8001 / 8002

spring:
  application:
    name: itstack-demo-springcloud-eureka-client

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:7397/eureka/
```

### itstack-demo-springcloud-eureka-server | 单个服务注册中心

服务注册中心用于承载接口提供方向上注册，同时正在调用方链接后可以获取指定应用的服务实例。

>EurekaServerApplication.java | 通过注解@EnableEurekaServer启动服务注册与发现中心

```java

/**
 * 微信公众号：bugstack虫洞栈 | 沉淀、分享、成长，专注于原创专题案例
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

>application.yml | 服务注册中心配置文件，端口7397和我们之前写netty的服务的端口一致

```java
server:
  port: 7397

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
    name: itstack-demo-springcloud-eureka-server
```

### itstack-demo-springcloud-feign | Feign服务调用方，添加熔断Hystrix

Feign 是一个声明式的 Web Service 客户端，它的目的就是让 Web Service 调用更加简单。它整合了 Ribbon 和 Hystrix，从而让我们不再需要显式地使用这两个组件。Feign 还提供了 HTTP 请求的模板，通过编写简单的接口和插入注解，我们就可以定义好 HTTP 请求的参数、格式、地址等信息。接下来，Feign 会完全代理 HTTP 的请求，我们只需要像调用方法一样调用它就可以完成服务请求。

Feign 具有如下特性：

可插拔的注解支持，包括 Feign 注解和 JAX-RS 注解
支持可插拔的 HTTP 编码器和解码器
支持 Hystrix 和它的 Fallback
支持 Ribbon 的负载均衡
支持 HTTP 请求和响应的压缩

>service/FeignService.java | 注解方式调用，方便易用。@FeignClient会在调用时进行解析服务到具体的http://ip:port/

```java
/**
 * 微信公众号：bugstack虫洞栈 | 沉淀、分享、成长，专注于原创专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@FeignClient(value = "itstack-demo-springcloud-eureka-client", fallback = FeignServiceHystrix.class)
public interface FeignService {

    @RequestMapping(value = "/api/queryUserInfo", method = RequestMethod.GET)
    String queryUserInfo(@RequestParam String userId);

}
```

>service/hystrix/FeignServiceHystrix.java | 提供熔断服务，当发生异常时主动返回预定结果

```java
/**
 * 微信公众号：bugstack虫洞栈 | 沉淀、分享、成长，专注于原创专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Component
public class FeignServiceHystrix implements FeignService {

    @Override
    public String queryUserInfo(String userId) {
        return "queryUserInfo by userId：" + userId + " err！from feign hystrix";
    }
    
}
```

>web/FeignController.java | 使用接口提供服务 From Feign

```java
/**
 * 微信公众号：bugstack虫洞栈 | 沉淀、分享、成长，专注于原创专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@RestController
public class FeignController {

    @Resource
    private FeignService ribbonService;

    @RequestMapping(path = "/api/queryUserInfo", method = RequestMethod.GET)
    public String queryUserInfo(@RequestParam String userId) {
        return ribbonService.queryUserInfo(userId) + " From Feign";
    }

}
```

>FeignApplication.java | 注解@EnableEurekaClient、@EnableFeignClients、@EnableDiscoveryClient获取调用注册中心服务

```java
/**
 * 微信公众号：bugstack虫洞栈 | 沉淀、分享、成长，专注于原创专题案例
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

>application.yml | eureka服务配置，从注册中心获取可用服务。开启hystrix=true

```java
server:
  port: 9001

spring:
  application:
    name: itstack-demo-springcloud-feign

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:7397/eureka/

feign.hystrix.enabled: true
```

### itstack-demo-springcloud-ribbon | Ribbon服务调用方

Ribbon是一个基于 HTTP 和 TCP 的客户端负载均衡器。它可以通过在客户端中配置 ribbonServerList 来设置服务端列表去轮询访问以达到均衡负载的作用。

当 Ribbon 与 Eureka 联合使用时，ribbonServerList 会被 DiscoveryEnabledNIWSServerList 重写，扩展成从 Eureka 注册中心中获取服务实例列表。同时它也会用 NIWSDiscoveryPing 来取代 IPing，它将职责委托给 Eureka 来确定服务端是否已经启动。

>service/RibbonService.java | 接口式硬编码调用不太易于维护，因此也是比较少用的方式。hystrix实际通过getFallback()返回熔断结果

```java
/**
 * 微信公众号：bugstack虫洞栈 | 沉淀、分享、成长，专注于原创专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Service
public class RibbonService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "queryUserInfoFallback")
    public String queryUserInfo(String userId) {
        return restTemplate.getForObject("http://ITSTACK-DEMO-SPRINGCLOUD-EUREKA-CLIENT/api/queryUserInfo?userId=" + userId, String.class);
    }

    /**
     * Specifies a method to process fallback logic.
     * A fallback method should be defined in the same class where is HystrixCommand.
     * Also a fallback method should have same signature to a method which was invoked as hystrix command.
     * for example:
     * <code>
     *      @HystrixCommand(fallbackMethod = "getByIdFallback")
     *      public String getById(String id) {...}
     *
     *      private String getByIdFallback(String id) {...}
     * </code>
     * Also a fallback method can be annotated with {@link HystrixCommand}
     * <p/>
     * default => see {@link com.netflix.hystrix.contrib.javanica.command.GenericCommand#getFallback()}
     *
     * @return method name
     *
     * getFallback()
     * 
     * @Override
     * protected Object getFallback() {
     *     final CommandAction commandAction = getFallbackAction();
     *     if (commandAction != null) {
     *         try {
     *             return process(new Action() {
     *                 @Override
     *                 Object execute() {
     *                     MetaHolder metaHolder = commandAction.getMetaHolder();
     *                     Object[] args = createArgsForFallback(metaHolder, getExecutionException());
     *                     return commandAction.executeWithArgs(metaHolder.getFallbackExecutionType(), args);
     *                 }
     *             });
     *         } catch (Throwable e) {
     *             LOGGER.error(FallbackErrorMessageBuilder.create()
     *                     .append(commandAction, e).build());
     *             throw new FallbackInvocationException(unwrapCause(e));
     *         }
     *     } else {
     *         return super.getFallback();
     *     }
     * }
     */
    public String queryUserInfoFallback(String userId) {
        return "queryUserInfo by userId：" + userId + " err！from ribbon hystrix";
    }

}
```

>web/RibbonController.java |  使用接口提供服务 From Ribbon

```java
/**
 * 微信公众号：bugstack虫洞栈 | 沉淀、分享、成长，专注于原创专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@RestController
public class RibbonController {

    @Resource
    private RibbonService ribbonService;

    @RequestMapping(path = "/api/queryUserInfo", method = RequestMethod.GET)
    public String queryUserInfo(@RequestParam String userId) {
        return ribbonService.queryUserInfo(userId) + " From Ribbon";
    }
    
}
```

>RibbonApplication.java | 通过注解@LoadBalanced注册rest模版，用于Ribbon接口调用。并启动@EnableHystrix

```java
/**
 * 微信公众号：bugstack虫洞栈 | 沉淀、分享、成长，专注于原创专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableHystrix
public class RibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(RibbonApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
```

>application.yml | eureka服务配置，从注册中心获取可用服务

```java
server:
  port: 9002

spring:
  application:
    name: itstack-demo-springcloud-ribbon

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:7397/eureka/
```

## 测试验证
1. 启动服务注册中心itstack-demo-springcloud-eureka-server
2. 本地测试不启动eureka-client，以达到服务不可以用的效果
3. 启动itstack-demo-springcloud-feign 
4. 启动itstack-demo-springcloud-ribbon
5. 访问Feign服务调用方，在熔断的保护下会返回预定熔断结果：http://localhost:9001/api/queryUserInfo?userId=1024

```java
queryUserInfo by userId：1024 err！from feign hystrix From Feign
queryUserInfo by userId：1024 err！from feign hystrix From Feign
```
6. 访问Ribbon服务调用方，在熔断的保护下会返回预定熔断结果：http://localhost:9002/api/queryUserInfo?userId=1024

```java
queryUserInfo by userId：1024 err！from ribbon hystrix From Ribbon
queryUserInfo by userId：1024 err！from ribbon hystrix From Ribbon
```

## 综上总结
1. Spring Cloud 将Hystrix整合后提供非常简单的使用方式，并且提供了丰富的配置可以满足实际应用开发
2. Hystrix Git开源代码；https://github.com/Netflix/Hystrix
3. 在熔断降级就像是电闸的保险丝，可以在非常重要的时刻快速失败保护系统

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**SpringCloud专题**」获取本文源码&更多原创专题案例！

