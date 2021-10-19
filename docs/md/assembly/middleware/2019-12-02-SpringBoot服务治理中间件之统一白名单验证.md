---
layout: post
category: itstack-ark-middleware
title: Spring Boot 中间件开发(一)《服务治理中间件之统一白名单验证》
tagline: by 付政委
tag: [java,itstack-ark-middleware]
excerpt: Spring Boot + 领域驱动设计使得微服务越来越火热，而随着微服务越来越多，服务的治理就显得尤为重要。在我们的业务领域开发中，经常会有一些通用性功能搭建，比如；白名单、黑名单、限流、熔断等，为了更好的开发业务功能，我们需要将非业务功能的通用逻辑提取出来开发出通用组件，以便于业务系统使用。而不至于Copy来Copy去，让代码乱的得加薪才能修改的地步！
lock: need
---

# Spring Boot 中间件开发(一)《服务治理中间件之统一白名单验证》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
Spring Boot + 领域驱动设计使得微服务越来越火热，而随着微服务越来越多，服务的治理就显得尤为重要。

在我们的业务领域开发中，经常会有一些通用性功能搭建，比如；白名单、黑名单、限流、熔断等，为了更好的开发业务功能，我们需要将非业务功能的通用逻辑提取出来开发出通用组件，以便于业务系统使用。而不至于Copy来Copy去，让代码乱的得加薪才能修改的地步！

通常一个中间件开发会需要用到；自定义xml配置、自定义Annotation注解、动态代理、反射调用、字节码编程(javaassist、ASM等)，以及一些动态注册服务中心和功能逻辑开发等。本案例会使用Spring Boot开发方式定义自己的starter。

## 原理简述
通过我们使用一个公用的starter的时候，只需要将相应的依赖添加的Maven的配置文件当中即可，免去了自己需要引用很多依赖类，并且SpringBoot会自动进行类的自动配置。而我们自己开发一个starter也需要做相应的处理；

1. SpringBoot 在启动时会去依赖的starter包中寻找 resources/META-INF/spring.factories 文件，然后根据文件中配置的Jar包去扫描项目所依赖的Jar包，这类似于 Java 的 SPI 机制。
   >SPI 全称 Service Provider Interface，是Java提供的一套用来被第三方实现或者扩展的API，它可以用来启用框架扩展和替换组件。

2. 根据 spring.factories配置加载AutoConfigure类。
3. 根据 @Conditional注解的条件，进行自动配置并将Bean注入Spring Context 上下文当中。也可以使用@ImportAutoConfiguration({MyServiceAutoConfiguration.class}) 指定自动配置哪些类。
4. 日常使用的Spring官方的Starter一般采取spring-boot-starter-{name} 的命名方式，如 spring-boot-starter-web 。而非官方的Starter，官方建议 artifactId 命名应遵循{name}-spring-boot-starter 的格式。 例如：door-spring-boot-starter 。

## 环境准备
1. jdk 1.8.0
2. Maven 3.x
3. IntelliJ IDEA Community Edition 2018.3.1 x64

## 工程示例

>中间件工程：door-spring-boot-starter

```java
door-spring-boot-starter
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.door
    │   │       ├── annotation
    │   │       │	└── DoDoor.java	
    │   │       ├── config
    │   │       │	├── StarterAutoConfigure.java	
    │   │       │	├── StarterService.java	
    │   │       │	└── StarterServiceProperties.java	
    │   │       └── DoJoinPoint.java
    │   └── resources	
    │       └── META_INF
    │           └── spring.factories	
    └── test
        └── java
            └── org.itstack.demo.test
                └── ApiTest.java
```

**演示部分重点代码块，完整代码下载关注公众号；bugstack虫洞栈，回复：中间件开发**

>door/annotation/DoDoor.java & 自定义注解

- 自定义注解，用于AOP切面
- key；获取入参类属性中某个值
- returnJson；拦截返回Json内容

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DoDoor {

    String key() default "";

    String returnJson() default "";

}
```

>config/StarterAutoConfigure.java & 配置信息装配

- 通过注解；@Configuration、@ConditionalOnClass、@EnableConfigurationProperties，来实现自定义配置获取值
- prefix = "itstack.door"，用于在yml中的配置

```java
@Configuration
@ConditionalOnClass(StarterService.class)
@EnableConfigurationProperties(StarterServiceProperties.class)
public class StarterAutoConfigure {

    @Autowired
    private StarterServiceProperties properties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "itstack.door", value = "enabled", havingValue = "true")
    StarterService starterService() {
        return new StarterService(properties.getUserStr());
    }

}
```

>config/StarterServiceProperties.java & 属性配置

- @ConfigurationProperties("itstack.door")，注解获取配置
- userStr白名单用户

```java
@ConfigurationProperties("itstack.door")
public class StarterServiceProperties {

    private String userStr;

    public String getUserStr() {
        return userStr;
    }

    public void setUserStr(String userStr) {
        this.userStr = userStr;
    }

}
```

>DoJoinPoint.java & 自定义切面

- 自定义切面获取方法和属性值
- 通过属性值判断此用户ID是否属于白名单范围
- 属于白名单则放行通过jp.proceed();
- 对于拦截的用于需要通过returnJson反序列为对象返回

```java
@Aspect
@Component
public class DoJoinPoint {

    private Logger logger = LoggerFactory.getLogger(DoJoinPoint.class);

    @Autowired
    private StarterService starterService;

    @Pointcut("@annotation(org.itstack.door.annotation.DoDoor)")
    public void aopPoint() {
    }

    @Around("aopPoint()")
    public Object doRouter(ProceedingJoinPoint jp) throws Throwable {
        //获取内容
        Method method = getMethod(jp);
        DoDoor door = method.getAnnotation(DoDoor.class);
        //获取字段值
        String keyValue = getFiledValue(door.key(), jp.getArgs());
        logger.info("itstack door handler method：{} value：{}", method.getName(), keyValue);
        if (null == keyValue || "".equals(keyValue)) return jp.proceed();
        //配置内容
        String[] split = starterService.split(",");
        //白名单过滤
        for (String str : split) {
            if (keyValue.equals(str)) {
                return jp.proceed();
            }
        }
        //拦截
        return returnObject(door, method);
    }

    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return getClass(jp).getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

    private Class<? extends Object> getClass(JoinPoint jp) throws NoSuchMethodException {
        return jp.getTarget().getClass();
    }

    //返回对象
    private Object returnObject(DoDoor doGate, Method method) throws IllegalAccessException, InstantiationException {
        Class<?> returnType = method.getReturnType();
        String returnJson = doGate.returnJson();
        if ("".equals(returnJson)) {
            return returnType.newInstance();
        }
        return JSON.parseObject(returnJson, returnType);
    }

    //获取属性值
    private String getFiledValue(String filed, Object[] args) {
        String filedValue = null;
        for (Object arg : args) {
            try {
                if (null == filedValue || "".equals(filedValue)) {
                    filedValue = BeanUtils.getProperty(arg, filed);
                } else {
                    break;
                }
            } catch (Exception e) {
                if (args.length == 1) {
                    return args[0].toString();
                }
            }
        }
        return filedValue;
    }

}
```

>pom.xml & 部分配置内容

- 中间件开发用到了切面，因此需要引入spring-boot-starter-aop
- 为了使调用端不用关心中间件都引入那些包，可以将额外的包一起打包给中间件

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-aop</artifactId>
</dependency>
		
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-jar-plugin</artifactId>
	<version>2.3.2</version>
	<configuration>
		<archive>
			<addMavenDescriptor>false</addMavenDescriptor>
			<index>true</index>
			<manifest>
				<addDefaultSpecificationEntries>true</addDefaultSpecificationEntries>
				<addDefaultImplementationEntries>true</addDefaultImplementationEntries>
			</manifest>
			<manifestEntries>
				<Implementation-Build>${maven.build.timestamp}</Implementation-Build>
			</manifestEntries>
		</archive>
	</configuration>
</plugin>			
```

>spring.factories & spring入口配置

- 将自己的XxxConfigue配置到这里，用于spring启动时候扫描到

```java
org.springframework.boot.autoconfigure.EnableAutoConfiguration=org.itstack.door.config.StarterAutoConfigure
```

>测试工程：itstack-demo-springboot-helloworld

```java
itstack-demo-springboot-helloworld
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.demo
    │   │       ├── domain
    │   │       │	└── UserInfo.java	
    │   │       ├── web	
    │   │       │	└── HelloWorldController.java	
    │   │       └── HelloWorldApplication.java
    │   └── resources	
    │       └── application.yml	
    └── test
        └── java
            └── org.itstack.demo.test
                └── ApiTest.java
```

**演示部分重点代码块，完整代码下载关注公众号；bugstack虫洞栈，回复：中间件开发**

>pom.xml & 引入中间件配置

```java
<dependency>
    <groupId>org.itatack.demo</groupId>
    <artifactId>door-spring-boot-starter</artifactId>
    <version>1.0.1-SNAPSHOT</version>
</dependency>
```

>web/HelloWorldController.java & 配置白名单拦截服务

- 在需要拦截的方法上添加@DoDoor注解；@DoDoor(key = "userId", returnJson = "{\"code\":\"1111\",\"info\":\"非白名单可访问用户拦截！\"}")
- key；需要从入参取值的属性字段，如果是对象则从对象中取值，如果是单个值则直接使用
- returnJson；预设拦截时返回值，是返回对象的Json

```java
@RestController
public class HelloWorldController {

    @DoDoor(key = "userId", returnJson = "{\"code\":\"1111\",\"info\":\"非白名单可访问用户拦截！\"}")
    @RequestMapping(path = "/api/queryUserInfo", method = RequestMethod.GET)
    public UserInfo queryUserInfo(@RequestParam String userId) {
        return new UserInfo("虫虫:" + userId, 19, "天津市南开区旮旯胡同100号");
    }

}
```

>application.yml & Yml配置

- 添加白名单配置，英文逗号隔开

```java
server:
  port: 8080

spring:
  application:
    name: itstack-demo-springboot-helloworld

# 自定义中间件配置
itstack:
  door:
    enabled: true
    userStr: 1001,aaaa,ccc #白名单用户ID，多个逗号隔开
```

## 测试验证

1. 启动工程(可以Debug调试)；itstack-demo-springboot-helloworld
2. 访问连接；
   1. 白名单用户：http://localhost:8080/api/queryUserInfo?userId=1001
       
	  ```java
      {"code":"0000","info":"success","name":"虫虫:1001","age":19,"address":"天津市南开区旮旯胡同100号"}
	  ```
	   
   2. 非名单用户：http://localhost:8080/api/queryUserInfo?userId=小团团
       
	  ```java
      {"code":"1111","info":"非白名单可访问用户拦截！","name":null,"age":null,"address":null}
	  ```
3. 服务度日志；

```java
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.2.RELEASE)

2019-12-03 23:25:40.128  INFO 177110 --- [           main] org.itstack.demo.HelloWorldApplication   : Starting HelloWorldApplication on FUZHENGWEI with PID 177110 (E:\itstack\github.com\itstack-demo-springboot-helloworld\target\classes started by fuzhengwei in E:\itstack\github.com\itstack-demo-springboot-helloworld)
2019-12-03 23:25:40.133  INFO 177110 --- [           main] org.itstack.demo.HelloWorldApplication   : No active profile set, falling back to default profiles: default
2019-12-03 23:25:42.446  INFO 177110 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2019-12-03 23:25:42.471  INFO 177110 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2019-12-03 23:25:42.471  INFO 177110 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.14]
2019-12-03 23:25:42.483  INFO 177110 --- [           main] o.a.catalina.core.AprLifecycleListener   : The APR based Apache Tomcat Native library which allows optimal performance in
2019-12-03 23:25:42.611  INFO 177110 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2019-12-03 23:25:42.612  INFO 177110 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 2421 ms
2019-12-03 23:25:43.063  INFO 177110 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2019-12-03 23:25:43.317  INFO 177110 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-12-03 23:25:43.320  INFO 177110 --- [           main] org.itstack.demo.HelloWorldApplication   : Started HelloWorldApplication in 3.719 seconds (JVM running for 4.294)
2019-12-03 23:26:56.107  INFO 177110 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2019-12-03 23:26:56.107  INFO 177110 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2019-12-03 23:26:56.113  INFO 177110 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 6 ms
2019-12-03 23:26:56.171  INFO 177110 --- [nio-8080-exec-1] org.itstack.door.DoJoinPoint             : itstack door handler method：queryUserInfo value：1001
2019-12-03 23:27:04.090  INFO 177110 --- [nio-8080-exec-3] org.itstack.door.DoJoinPoint             : itstack door handler method：queryUserInfo value：小团团
```

## 综上总结

- 此版本中间件还只是一个功能非常简单的雏形，后续还需继续拓展。比如；白名单用户自动更新、黑名单、熔断、降级、限流等。
- 中间件开发可以将很多重复性工作抽象后进行功能整合，以提升我们使用工具的效率。
- 鉴于Spring Boot是比较的趋势，我会不断的深挖以及开发一些服务组件。锻炼自己也帮助他人，逐渐构建服务生态，也治理服务。

------------

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**中间件开发**」获取本文源码&更多原创专题案例！
