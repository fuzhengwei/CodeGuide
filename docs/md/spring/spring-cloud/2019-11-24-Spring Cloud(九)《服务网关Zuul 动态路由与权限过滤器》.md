---
layout: post
category: itstack-demo-springcloud
title: Spring Cloud(九)《服务网关Zuul 动态路由与权限过滤器》
tagline: by 付政委
tag: [spring,itstack-demo-springcloud]
excerpt: 在实际的业务开发中不只是将路由配置放到文件中，而是需要进行动态管理并且可以在变化时不用重启系统就可以更新。与此同时还需要在接口访问的时候，可以增加一些权限验证以防止恶意访问。
lock: need
---

>微信公众号：bugstack虫洞栈
沉淀、分享、成长，专注于原创专题案例，以最易学习编程的方式分享知识，让自己和他人都能有所收获。目前已完成的专题有；Netty4.x实战专题案例、用Java实现JVM、基于JavaAgent的全链路监控、手写RPC框架、架构设计专题案例[Ing]等。

## 前言介绍
在实际的业务开发中不只是将路由配置放到文件中，而是需要进行动态管理并且可以在变化时不用重启系统就可以更新。与此同时还需要在接口访问的时候，可以增加一些权限验证以防止恶意访问。

1. Filter过滤器，通过继承实现对应方法可以进行控制过滤；
- PRE：这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
- ROUTING：这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用 Apache HttpClient 或 Netfilx Ribbon 请求微服务。
- POST：这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的 HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
- ERROR：在其他阶段发生错误时执行该过滤器。 除了默认的过滤器类型，Zuul 还允许我们创建自定义的过滤器类型。例如，我们可以定制一种 STATIC 类型的过滤器，直接在 Zuul 中生成响应，而不将请求转发到后端的微服务。

2. 自定义路由，同构实现SimpleRouteLocator和RefreshableRouteLocator自动刷新
- protected Map<String, ZuulRoute> locateRoutes()：此方法是加载路由配置的，父类中是获取properties中的路由配置，可以通过扩展此方法，达到动态获取配置的目的
- public Route getMatchingRoute(String path)：此方法是根据访问路径，获取匹配的路由配置，父类中已经匹配到路由，可以通过路由id查找自定义配置的路由规则，以达到根据自定义规则动态分流的效果

## 环境准备
- jdk 1.8、idea2018、Maven3
- Spring Boot 2.0.6.RELEASE
- Spring Cloud Finchley.SR2

## 代码示例

```java
itstack-demo-springcloud-08
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
└── itstack-demo-springcloud-zuul
    └── src
        └── main
            ├── java
            │   └── org.itstack.demo   
            │        ├── config
            │        │   └── ZuulConfig.java
            │        ├── filter
            │        │   └── TokenFilter.java
            │        ├── router
            │        │   └── RouteLocator.java
            │        ├── service
            │        │   └── RefreshRouteService.java
            │        └── ZuulApplication.java
            └── resources   
                └── application.yml
```

**完整代码欢迎关注公众号：bugstack虫洞栈 回复“SpringCloud专题”进行下载**

>itstack-demo-springcloud-zuul & 动态路由与权限过滤

1. 通过RouteLocator实现自己的动态路由配置，其实就是把配置文件内容转移到这里用代码类实现，并且可以根据需要修改为从数据库里获取。

2. TokenFilter提供了权限验证功能，当用户访问时候会带上token否则拦截

3. 此外还提供了自动刷新的接口，用于外部调用刷新配置

4. 最后我们需要修改application配置，zuul中还需要排除不做路由的接口[刷新权限接口]

>config/ZuulConfig.java & 路由配置类

```java
/**
 * 路由配置
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Configuration
public class ZuulConfig {

    @Autowired
    private ZuulProperties zuulProperties;
    @Autowired
    private ServerProperties server;

    @Bean
    public RouteLocator routeLocator() {
        return new RouteLocator(this.server.getServlet().getPath(), this.zuulProperties);
    }

}
```

>filter/TokenFilter.java & 权限校验类

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class TokenFilter extends ZuulFilter {

    /**
     * 过滤器的类型，它决定过滤器在请求的哪个生命周期中执行。
     * FilterConstants.PRE_TYPE：代表会在请求被路由之前执行。
     * PRE、ROUTING、POST、ERROR
     */
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * filter执行顺序，通过数字指定。[数字越大，优先级越低]
     */
    public int filterOrder() {
        return 0;
    }

    /**
     * 判断该过滤器是否需要被执行。这里我们直接返回了true，因此该过滤器对所有请求都会生效。
     * 实际运用中我们可以利用该函数来指定过滤器的有效范围。
     */
    public boolean shouldFilter() {
        return true;
    }

    /*
     * 具体执行逻辑
     */
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getParameter("token");
        if (token == null || token.isEmpty()) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("refuse! token is empty");
        }
        return null;
    }

}
```

>router/RouteLocator.java & 路由类

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class RouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    private ZuulProperties properties;

    public RouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<String, ZuulRoute>();
        //从application.properties中加载路由信息
        routesMap.putAll(super.locateRoutes());
        //从db中加载路由信息
        routesMap.putAll(routesConfigGroup());
        //优化一下配置
        LinkedHashMap<String, ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            // Prepend with slash if not already present.
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }

    /**
     * 路由配置组，可以从数据库中读取
     * 基本配置与写在文件中配置类似，如下；
     * #  routes:
     * #    api-a:
     * #      path: /route-a/**
     * #      serviceId: itstack-demo-springcloud-feign
     * #    api-b:
     * #      path: /route-b/**
     * #      serviceId: itstack-demo-springcloud-ribbon
     * @return 配置组内容
     */
    private Map<String, ZuulRoute> routesConfigGroup() {
        Map<String, ZuulRoute> routes = new LinkedHashMap<>();

        ZuulRoute zuulRoute = new ZuulRoute();
        zuulRoute.setId("route-a");
        zuulRoute.setPath("/route-a/**");
        zuulRoute.setServiceId("itstack-demo-springcloud-feign");
        // 如果使用了注册中心，那么可以根据serviceId进行访问。
        // zuulRoute.setUrl("http://localhost:9001");
        zuulRoute.setRetryable(false);
        zuulRoute.setStripPrefix(true);
        zuulRoute.setSensitiveHeaders(new HashSet<>());

        routes.put(zuulRoute.getPath(), zuulRoute);

        return routes;
    }

}
```

>service/RefreshRouteService.java & 路由刷新服务

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Service
public class RefreshRouteService {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private RouteLocator routeLocator;

    public void refreshRoute() {
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
        publisher.publishEvent(routesRefreshedEvent);
    }

}
```

>ZuulApplication.java & 启动服务注意注解，另外提供了服务接口

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
@RestController
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

    @Bean
    public TokenFilter tokenFilter() {
        return new TokenFilter();
    }

    @Autowired
    private RefreshRouteService refreshRouteService;
    @Autowired
    private ZuulHandlerMapping zuulHandlerMapping;

    @RequestMapping("api/refresh")
    public String refresh(){
        refreshRouteService.refreshRoute();
        return "success";
    }

    @RequestMapping("api/queryRouteInfo")
    @ResponseBody
    public Map<String, Object> queryRouteInfo(){
        return zuulHandlerMapping.getHandlerMap();
    }

}

```

>application.yml & 配置文件修改，路由过滤

```java
server:
  port: 10001

spring:
  application:
    name: itstack-demo-ddd-zuul

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:7397/eureka/

# 动态路由，以下配置注释；
# http://localhost:10001/route-a/api/queryUserInfo?userId=111
# http://localhost:10001/route-b/api/queryUserInfo?userId=111
zuul:
   ignoredPatterns: /api/**
#  routes:
#    api-a:
#      path: /route-a/**
#      serviceId: itstack-demo-springcloud-feign
#    api-b:
#      path: /route-b/**
#      serviceId: itstack-demo-springcloud-ribbon
```

## 测试验证
1. 分别启动如下服务；
   1. itstack-demo-springcloud-eureka-server 服务注册与发现
   2. itstack-demo-springcloud-eureka-client 接口提供方
   3. itstack-demo-springcloud-hystrix-feign 调用端
   4. itstack-demo-springcloud-hystrix-ribbon 调用端
   5. itstack-demo-springcloud-zuul 路由服务
2. 可测试接口列表；
   1. 路由服务：http://localhost:10001/route-a/api/queryUserInfo?userId=111&token=111
	  ```java
      Hi 微信公众号：bugstack虫洞栈 | 111 >: from eureka client port: 8001 From Feign
      ```
   2. 刷新配置：http://localhost:10001/api/refresh
   3. 内容配置：http://localhost:10001/api/queryRouteInfo

## 综上总结
1. 路由服务可以方便的帮我们控制业务类型的区分访问，同时自动刷新可以更加方便的使用网关路由
2. 权限验证是几乎不可少的在实际开发过程中会经常用到，所有的接口必须是安全可靠的，保证数据不泄露
3. 另外还可以考虑从入参的用户身份进行路由，这样可以把数据库路由提前，让不同用户组直接访问到不同的数据库组

## 文章汇总

1. [Spring Cloud(零)《总有一偏概述告诉你SpringCloud是什么》](https://bugstack.cn/itstack-demo-springcloud/2019/10/31/Spring-Cloud(%E9%9B%B6)-%E6%80%BB%E6%9C%89%E4%B8%80%E5%81%8F%E6%A6%82%E8%BF%B0%E5%91%8A%E8%AF%89%E4%BD%A0SpringCloud%E6%98%AF%E4%BB%80%E4%B9%88.html)
2. [Spring Cloud(一)《服务集群注册与发现 Eureka》](https://bugstack.cn/itstack-demo-springcloud/2019/11/01/Spring-Cloud(%E4%B8%80)-%E6%9C%8D%E5%8A%A1%E9%9B%86%E7%BE%A4%E6%B3%A8%E5%86%8C%E4%B8%8E%E5%8F%91%E7%8E%B0-Eureka.html)
3. [Spring Cloud(二)《服务提供与负载均衡调用 Eureka》](https://bugstack.cn/itstack-demo-springcloud/2019/11/02/Spring-Cloud(%E4%BA%8C)-%E6%9C%8D%E5%8A%A1%E6%8F%90%E4%BE%9B%E4%B8%8E%E8%B4%9F%E8%BD%BD%E5%9D%87%E8%A1%A1%E8%B0%83%E7%94%A8-Eureka.html)
4. [Spring Cloud(三)《应用服务快速失败熔断降级保护 Hystrix》](https://bugstack.cn/itstack-demo-springcloud/2019/11/03/Spring-Cloud(%E4%B8%89)-%E5%BA%94%E7%94%A8%E6%9C%8D%E5%8A%A1%E5%BF%AB%E9%80%9F%E5%A4%B1%E8%B4%A5%E7%86%94%E6%96%AD%E9%99%8D%E7%BA%A7%E4%BF%9D%E6%8A%A4-Hystrix.html)
5. [Spring Cloud(四)《服务响应性能成功率监控 Hystrix》](https://bugstack.cn/itstack-demo-springcloud/2019/11/04/Spring-Cloud(%E5%9B%9B)-%E6%9C%8D%E5%8A%A1%E5%93%8D%E5%BA%94%E6%80%A7%E8%83%BD%E6%88%90%E5%8A%9F%E7%8E%87%E7%9B%91%E6%8E%A7-Hystrix.html)
6. [Spring Cloud(五)《Turbine 监控信息聚合展示 Hystrix》](https://bugstack.cn/itstack-demo-springcloud/2019/11/05/Spring-Cloud(%E4%BA%94)-Turbine-%E7%9B%91%E6%8E%A7%E4%BF%A1%E6%81%AF%E8%81%9A%E5%90%88%E5%B1%95%E7%A4%BA-Hystrix.html)
7. [Spring Cloud(六)《基于github webhook动态刷新服务配置》](https://bugstack.cn/itstack-demo-springcloud/2019/11/06/Spring-Cloud(%E5%85%AD)-%E5%9F%BA%E4%BA%8EGithub-Webhook%E5%8A%A8%E6%80%81%E5%88%B7%E6%96%B0%E6%9C%8D%E5%8A%A1%E9%85%8D%E7%BD%AE.html)
8. [Spring Cloud(七)《基于RabbitMQ消息总线方式刷新配置服务》](https://bugstack.cn/itstack-demo-springcloud/2019/11/07/Spring-Cloud(%E4%B8%83)-%E5%9F%BA%E4%BA%8ERabbitMQ%E6%B6%88%E6%81%AF%E6%80%BB%E7%BA%BF%E6%96%B9%E5%BC%8F%E5%88%B7%E6%96%B0%E9%85%8D%E7%BD%AE%E6%9C%8D%E5%8A%A1.html)
9. [Spring Cloud(八)《服务网关路由 Zuul1》](https://bugstack.cn/itstack-demo-springcloud/2019/11/08/Spring-Cloud(%E5%85%AB)-%E6%9C%8D%E5%8A%A1%E7%BD%91%E5%85%B3%E8%B7%AF%E7%94%B1-Zuul1.html)

------------

上一篇：[Spring Cloud(八)《服务网关路由 Zuul1》](/itstack-demo-springcloud/2019/11/08/Spring-Cloud(%E5%85%AB)-%E6%9C%8D%E5%8A%A1%E7%BD%91%E5%85%B3%E8%B7%AF%E7%94%B1-Zuul1.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**SpringCloud专题**」获取本文源码&更多原创专题案例！

