---
layout: post
category: itstack-demo-springcloud
title: 第2章：服务提供与负载均衡调用 Eureka
tagline: by 付政委
tag: [spring,itstack-demo-springcloud]
excerpt: 本章节提供一个基于Eurka的服务注册中心，两个服务提供者之后分别使用Ribbon、Fegin方式进行调用，测试负载均衡。
lock: need
---

# 第2章：服务提供与负载均衡调用 Eureka

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
本章节提供一个基于Eurka的服务注册中心，两个服务提供者之后分别使用Ribbon、Fegin方式进行调用，测试负载均衡。
![微信公众号：bugstack虫洞栈 & 服务注册与调用](https://bugstack.cn/assets/images/pic-content/2019/11/springcloud-2-1.png)

服务提供者Service Provider 本质上是一个 Eureka Client，它在服务启动时，会调用服务注册方法，向 Eureka Server注册接口服务信息，包括地址、端口、服务名、入参、返回值等。当Eureka Server收到注册信息后，会维护在自己的注册列表，如下；

```java
private final ConcurrentHashMap<String, Map<String, Lease<InstanceInfo>>> registry
        = new ConcurrentHashMap<String, Map<String, Lease<InstanceInfo>>>();
```

服务消费者Service Consumer 本质也是一个 Eureka Client，它在服务启动时，也会向 Eureka Server 注册服务信息。同时在启动后会从Eureka Server 上获取所有实例的注册信息，包括 IP 地址、端口等，并缓存到本地。这个获取有一定的延时，因此我们在实际开发过程中如果服务方尚未启动完成，调用方不要着急启动避免造成调用失败。

## 案例说明
本案例在itstack-demo-springcloud-02工程中提供单个服务注册、服务提供方、Ribbon调用、Fegin调用，通过修改端口启动2个提供方来模拟测试负载均衡。

## 环境准备
1. jdk 1.8
2. Spring Boot 2.0.6.RELEASE
3. Spring Cloud Finchley.SR2

## 代码示例
```java
itstack-demo-springcloud-02
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
├── itstack-demo-springcloud-feign
│   └── src
│       └── main
│           ├── java
│           │   └── org.itstack.demo
│           │        ├── service
│           │        │   └── FeignService.java
│           │        ├── web
│           │        │   └── FeignController.java
│           │        └── FeignApplication.java
│           └── resources   
│               └── application.yml
└── itstack-demo-springcloud-ribbon
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

### itstack-demo-springcloud-feign | Feign服务调用方

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
@FeignClient(value = "itstack-demo-springcloud-eureka-client")
public interface FeignService {

    @RequestMapping(value = "/api/queryUserInfo", method = RequestMethod.GET)
    String queryUserInfo(@RequestParam String userId);

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

>application.yml | eureka服务配置，从注册中心获取可用服务

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
```

### itstack-demo-springcloud-ribbon | Ribbon服务调用方

Ribbon是一个基于 HTTP 和 TCP 的客户端负载均衡器。它可以通过在客户端中配置 ribbonServerList 来设置服务端列表去轮询访问以达到均衡负载的作用。

当 Ribbon 与 Eureka 联合使用时，ribbonServerList 会被 DiscoveryEnabledNIWSServerList 重写，扩展成从 Eureka 注册中心中获取服务实例列表。同时它也会用 NIWSDiscoveryPing 来取代 IPing，它将职责委托给 Eureka 来确定服务端是否已经启动。

>service/RibbonService.java | 接口式硬编码调用不太易于维护，因此也是比较少用的方式

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

    public String queryUserInfo(String userId) {
        return restTemplate.getForObject("http://ITSTACK-DEMO-SPRINGCLOUD-EUREKA-CLIENT/api/queryUserInfo?userId=" + userId, String.class);
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

>RibbonApplication.java | 通过注解@LoadBalanced注册rest模版，用于Ribbon接口调用

```java
/**
 * 微信公众号：bugstack虫洞栈 | 沉淀、分享、成长，专注于原创专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
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
2. 分别启动itstack-demo-springcloud-eureka-client，修改端口8001、8002启动两次提供两个服务
3. 启动itstack-demo-springcloud-feign
4. 启动itstack-demo-springcloud-ribbon
5. 访问服务注册中心http://localhost:7397/
![微信公众号：bugstack虫洞栈 & 服务注册中心](https://bugstack.cn/assets/images/pic-content/2019/11/springcloud-2-2.png)
6. 访问服务提供方；http://localhost:8001/api/queryUserInfo?userId=111 | 说明服务正常

```java
Hi 微信公众号：bugstack虫洞栈 | 111 >: from eureka client port: 8001
```

7. 访问Feign服务调用放，每次刷新会看到负载均衡调用到不同端口服务：http://localhost:9001/api/queryUserInfo?userId=111

```java
Hi 微信公众号：bugstack虫洞栈 | 111 >: from eureka client port: 8002 From Feign

Hi 微信公众号：bugstack虫洞栈 | 111 >: from eureka client port: 8001 From Feign
```

8. 访问Ribbon服务调用放，每次刷新会看到负载均衡调用到不同端口服务：http://localhost:9002/api/queryUserInfo?userId=111

```java
Hi 微信公众号：bugstack虫洞栈 | 111 >: from eureka client port: 8001 From Ribbon

Hi 微信公众号：bugstack虫洞栈 | 111 >: from eureka client port: 8002 From Ribbon
```

## 综上总结
1. 在使用SpringCloud时我们可以很轻松的使用到注册中心与很简单的方式去做服务调用
2. 以上负载均衡，都是以轮询访问的方式实现的，实际开发过程中还会有一些依赖于机器性能、GC、调用量、响应时间等计算的权重值来做负载IRule
3. 服务注册中心，负责维护注册的服务列表，同其他服务注册中心一样，支持高可用配置
4. 服务提供方，作为一个 Eureka Client，向 Eureka Server 做服务注册、续约和下线等操作，注册的主要数据包括服务名、机器 ip、端口号、域名等
5. 服务消费方，作为一个 Eureka Client，向 Eureka Server 获取 Service Provider 的注册信息，并通过远程调用与 Service Provider 进行通信

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**SpringCloud专题**」获取本文源码&更多原创专题案例！
