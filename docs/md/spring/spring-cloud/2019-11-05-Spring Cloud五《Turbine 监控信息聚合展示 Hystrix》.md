---
layout: post
category: itstack-demo-springcloud
title: 第5章：Turbine 监控信息聚合展示 Hystrix
tagline: by 付政委
tag: [spring,itstack-demo-springcloud]
excerpt: Hystrix Dashboard 可以定时收集接口调用信息；时长、次数、性能、熔断等各项指标来进行监控展示，但是我们每次监控都需要输入一个Hystrix 的链接例如：http://localhost:9001/actuator/hystrix.stream，但是这样并不利于我们去做整体服务的监控，并且在实际使用的过程中如果是几十到几百个接口那么这样的监控几乎达不到监控效果，就累死在监控路上了。因此我们需要使用到 Turbine 来进行监控信息聚合，可以按业务组定义配置方便监控。
lock: need
---

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
Hystrix Dashboard 可以定时收集接口调用信息；时长、次数、性能、熔断等各项指标来进行监控展示，但是我们每次监控都需要输入一个Hystrix 的链接例如：http://localhost:9001/actuator/hystrix.stream，但是这样并不利于我们去做整体服务的监控，并且在实际使用的过程中如果是几十到几百个接口那么这样的监控几乎达不到监控效果，就累死在监控路上了。因此我们需要使用到 Turbine 来进行监控信息聚合，可以按业务组定义配置方便监控。

## 案例说明
案例通过添加itstack-demo-springcloud-turbine工程模块，将单体监控汇总在统一页面进行管理，此时的监控模型，如图；
![微信公众号：bugstack虫洞栈 & Turbine监控模型](https://bugstack.cn/assets/images/pic-content/2019/11/springcloud-5-1.png)

## 环境准备
1. jdk 1.8、idea2018、Maven3
2. Spring Boot 2.0.6.RELEASE
3. Spring Cloud Finchley.SR2

## 代码示例

```java
itstack-demo-springcloud-05
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
├── itstack-demo-springcloud-hystrix-dashboard
│   └── src
│       └── main
│           ├── java
│           │   └── org.itstack.demo
│           │        └── DashboardApplication.java
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
├── itstack-demo-springcloud-hystrix-ribbon
│   └── src
│       └── main
│           ├── java
│           │   └── org.itstack.demo
│           │        ├── service
│           │        │   └── RibbonService.java
│           │        ├── web
│           │        │   └── RibbonController.java      
│           │        └── RibbonApplication.java
│           └── resources   
│               └── application.yml
└── itstack-demo-springcloud-turbine
    └── src
        └── main
            ├── java
            │   └── org.itstack.demo   
            │        └── TurbineApplication.java
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

### itstack-demo-springcloud-hystrix-dashboard | 服务监控像仪表盘一样透视系统健康度
Spring Cloud Hystrix Dashboard只是spring cloud基于Hystrix Dashboard，将实时监控数据通过页面呈现出来。Spring Cloud Hystrix Dashboard的底层原理是间隔一定时间去“Ping”目标服务，返回的结果是最新的监控数据，最后将数据显示出来。

```java
Cluster via Turbine (default cluster): http://turbine-hostname:port/turbine.stream 
Cluster via Turbine (custom cluster): http://turbine-hostname:port/turbine.stream?cluster=[clusterName]
Single Hystrix App: http://hystrix-app:port/actuator/hystrix.stream 
```

>DashboardApplication.java | 配置@EnableHystrixDashboard启动服务

```java
/**
 * 微信公众号：bugstack虫洞栈 | 沉淀、分享、成长，专注于原创专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@EnableHystrixDashboard
@SpringBootApplication
public class DashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashboardApplication.class, args);
    }

}
```

>application.yml | 通用配置信息

```java
server:
  port: 8989

spring:
  application:
    name: itstack-demo-springcloud-hystrix-dashboard
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
@EnableHystrix
public class FeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }

}
```

>application.yml | eureka服务配置，从注册中心获取可用服务。开启hystrix=true，并设置hystrix.stream

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

management:
  endpoints:
    web:
      exposure:
        include: hystrix.stream
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

### itstack-demo-springcloud-turbine | 监控信息聚合服务

通过配置汇总，将应用itstack-demo-springcloud-feign,itstack-demo-springcloud-ribbon，汇总监控。

>TurbineApplication.java | 通过注解@EnableTurbine配置启动Ribbon

```java
/**
 * 微信公众号：bugstack虫洞栈 | 沉淀、分享、成长，专注于原创专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@EnableTurbine
@SpringBootApplication
public class TurbineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TurbineApplication.class, args);
    }

}
```

>application.yml | eureka服务配置，从注册中心获取可用服务

```java
spring:
  application:
    name: itstack-demo-springcloud-turbine

server:
  port: 8080

management:
  port: 8081

## 服务注册中心
eureka:
  client:
    service-url:
      defaultZone: http://localhost:7397/eureka

## 监控汇总配置，这里配置2个应用逗号隔开
turbine:
  app-config: itstack-demo-springcloud-feign,itstack-demo-springcloud-ribbon
  cluster-name-expression: new String("default")
  combine-host-port: true
```

## 测试验证
1. 启动itstack-demo-springcloud-hystrix-dashboard，访问；http://localhost:8989/hystrix
![微信公众号：bugstack虫洞栈 & hystrix-dashboard](https://bugstack.cn/assets/images/pic-content/2019/11/springcloud-4-3.png)

2. 分别启动如下系统模拟；
	1. itstack-demo-springcloud-eureka-server  服务注册发现中心
	2. itstack-demo-springcloud-eureka-client  测试接口提供方
	3. itstack-demo-springcloud-hystrix-feign  接口调用方Feign
	4. itstack-demo-springcloud-hystrix-ribbon 接口调用方Ribbon
	5. itstack-demo-springcloud-turbine 	   监控信息汇总
	
3. 测试监控
   1. 在hystrix-dashboard监控页面｛http://localhost:8989/hystrix｝，输入；http://localhost:8080/turbine.stream
   2. 刷新访问两个调用方接口；http://localhost:9001/api/queryUserInfo?userId=111、http://localhost:9002/api/queryUserInfo?userId=111
   3. 回看刚才的监控页面；http://localhost:8989/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A8080%2Fturbine.stream，已经可以看到监控信息汇总，如图；
      ![微信公众号：bugstack虫洞栈 & 监控信息汇总](https://bugstack.cn/assets/images/pic-content/2019/11/springcloud-5-2.png)

## 综上总结
1. 通过Turbine服务我们可以将监控信息汇总到一起进行查看，这样更加方便实际应用。
2. SpringCloud 到现在的案例已经使用到了很多服务模块，它确实是一套有序集合框架，将各家优秀功能服务集成，方便使用。
3. SpringBoot 、 SpringCloud，在开发一些中小型独立服务非常边界，对于一些超大型以外的公司非常合适。当然并不是一线互联网就不使用，因为这里面还牵扯到很多对应的替代产品，比如Dubbo、网关、全链路监控、路由等等，所以需要根据不同业务进行技术选型，不要被技术限制。

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**SpringCloud专题**」获取本文源码&更多原创专题案例！
