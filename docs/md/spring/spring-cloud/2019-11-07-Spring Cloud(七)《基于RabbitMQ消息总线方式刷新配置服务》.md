---
layout: post
category: itstack-demo-springcloud
title: Spring Cloud(七)《基于RabbitMQ消息总线方式刷新配置服务》
tagline: by 付政委
tag: [spring,itstack-demo-springcloud]
excerpt: 在微服务架构中，为了更方便的向微服务实例广播消息，我们通常会构建一个消息中心，让所有的服务实例都连接上来，而该消息中心所发布的消息都会被微服务实例监听和消费，我们把这种机制叫做消息总线(SpringCloud Bus)
lock: need
---

## 前言介绍
>在微服务架构中，为了更方便的向微服务实例广播消息，我们通常会构建一个消息中心，让所有的服务实例都连接上来，而该消息中心所发布的消息都会被微服务实例监听和消费，我们把这种机制叫做消息总线(SpringCloud Bus)

当我们的微服务达到是几个到百个以上，在更新配置时，不太可能一个个刷新或者重启，这样既不能保证效率也容易导致遗漏造成事故。因此我们需要SpringCloud Bus 提供总线服务，在我们push代码到Git的时候，通过Webhooks（http://localhost:port/actuator/bus-refresh/）执行刷新，消息总线会通知各个实例更新配置，以达到自动更新全服务配置。

![微信公众号：bugstack虫洞栈 & 消息总线配置更新](https://bugstack.cn/assets/images/pic-content/2019/11/springcloud-7-1.png)

## 环境准备
1. jdk 1.8、idea2018、Maven3
2. Spring Boot 2.0.6.RELEASE
3. Spring Cloud Finchley.SR2
4. 需要有一个Git帐号，用来创建配置中心以及开启Webhooks服务，添加回调
5. RabbitMQ服务端环境安装
	1. 下载Erlang；http://www.erlang.org/downloads  ｛安装后配置环境变量：D:\Program Files\erl10.5｝
	2. 下载rabbitMQ；http://www.rabbitmq.com/download.html ｛安装后CMD依次执行｝
		- cd D:\Program Files\RabbitMQ Server\rabbitmq_server-3.8.1\sbin
		- rabbitmq-plugins.bat enable rabbitmq_management
		- rabbitmq-service.bat stop
		- rabbitmq-service.bat start
		- 浏览器访问；http://127.0.0.1:15672
		- 服务端口5672

## 代码示例

```java
itstack-demo-springcloud-07
├── itstack-demo-springcloud-config-client
│   └── src
│       └── main
│           ├── java
│           │   └── org.itstack.demo
│           │        ├── web
│           │        │   └── ConfigClientController.java      
│           │        └── ConfigClientApplication.java
│           └── resources   
│               ├── application.yml
│               └── bootstrap.yml
├── itstack-demo-springcloud-config-server
│   └── src
│       └── main
│           ├── java
│           │   └── org.itstack.demo   
│           │        └── ConfigServerApplication.java
│           └── resources   
│               └── application.yml
└── itstack-demo-springcloud-eureka-server
     └── src
        └── main
            ├── java
            │   └── org.itstack.demo   
            │        └── EurekaServerApplication.java
            └── resources   
                └── application.yml
```

**完整代码欢迎关注公众号：bugstack虫洞栈 回复“SpringCloud专题”进行下载**

### itstack-demo-springcloud-config-client | 配置获取客户端方，提供自动刷新Http

>web/ConfigClientController.java & 添加注解@RefreshScope自动刷新配置

```java
/**
 * 微信公众号：bugstack虫洞栈 | 沉淀、分享、成长，专注于原创专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@RestController
@RefreshScope
public class ConfigClientController {

    @Value("${info.profile:error}")
    private String profile;

    @GetMapping("/config")
    public Mono<String> config() {
        return Mono.justOrEmpty(profile);
    }

}
```	

>ConfigClientApplication.java & 普通配置即可

```java
/**
 * 微信公众号：bugstack虫洞栈 | 沉淀、分享、成长，专注于原创专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@SpringBootApplication
public class ConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }

}
```

>application.yml & 需要配置endpoints，这样才可以暴漏刷新服务

```java
spring:
  application:
    name: itstack-demo-springcloud-config-client
  cloud:
    bus:
      trace:
        enabled: true
      enabled: true
server:
  port: 9001

# 如果不使用消息总线，则开启如下配置 /actuator/refresh 这个 Endpoint 暴露出来
#management:
#  endpoints:
#    web:
#      exposure:
#        include: refresh
```

>bootstrap.yml & 配置中心服务配置，http://localhost:7397 添加配置服务

```java
spring:
  cloud:
    config:
      name: config-client         # 对应 {application} 部分，例如；config-client-dev = 只取最后一个符号'-'之前的
      profile: dev                # 对应 {profile} 部分
      label: master               # 对应 {label} 部分，即 Git 的分支。如果配置中心使用的是本地存储，则该参数无用
      discovery:
        enabled: true             # 开启 config 服务发现支持
        service-id: itstack-demo-springcloud-config-server        # 配置服务name

#配置文件会被转换成 Web，访问规则如下；
#/{application}/{profile}[/{label}]
#/{application}-{profile}.yml
#/{label}/{application}-{profile}.yml
#/{application}-{profile}.properties
#/{label}/{application}-{profile}.properties

eureka:
  client:
    service-url:
      defaultZone: http://localhost:7397/eureka/
```

### itstack-demo-springcloud-config-server | 配置提供服务端方，链接Git配置工程地址

>ConfigServerApplication.java & 添加注解@EnableConfigServer设置成配置服务中心

```java
/**
 * 微信公众号：bugstack虫洞栈 | 沉淀、分享、成长，专注于原创专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

}
```

>application.yml & 配置信息，消息总线刷新

```java
server:
  port: 8080

spring:
  application:
    name: itstack-demo-springcloud-config-server
  cloud:
    config:
      server:
        git:
          uri: https://github.com/fuzhengwei/itstack-demo-config  # 换成自己的配置Git仓库的地址，如果没有可以新建工程地址，也可以克隆我的；https://github.com/fuzhengwei/itstack-demo-config
          search-paths: config-repo                               # Git仓库地址下的底层配置文件名称，如果配置多个用逗号','分割。

# 如果配置中心需要访问权限，则开启配置
# spring.cloud.config.server.git.username：Github账户
# spring.cloud.config.server.git.password：Github密码

eureka:
  client:
    service-url:
      defaultZone: http://localhost:7397/eureka/
management:
  endpoints:
    web:
      exposure:
        include: bus-refresh
```

### itstack-demo-springcloud-eureka-server | 服务注册发现

>EurekaServerApplication.java & 添加注解@EnableEurekaServer启动服务发现

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

>application.yml & 配置信息

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

## 测试验证
1. 准备好自己Github的配置仓库，也可以克隆我的Git；https://github.com/fuzhengwei/itstack-demo-config ｛有一组配置配置文件｝
2. 配置Webhooks，在https://github.com/换你自己的fuzhengwei/换你自己的itstack-demo-netty/settings/hooks/new
3. 分别启动服务
	1. 启动RabbitMQ服务；http://127.0.0.1:15672/#/
	2. itstack-demo-springcloud-eureka-server 服务注册发现
	3. itstack-demo-springcloud-config-server 配置Server
	4. itstack-demo-springcloud-config-client 配置Client
4. 访问配置服务，端口7397；http://localhost:8080/config-client/dev
	1. 访问结果
	
	```hava
	{
		"name": "config-client",
		"profiles": [
			"dev"
		],
		"label": null,
		"version": "ea0b1a1017595d542aa01b8b2bda68f9620dd81a",
		"state": null,
		"propertySources": [
			{
				"name": "https://github.com/fuzhengwei/itstack-demo-config/config-repo/config-client-dev.yml",
				"source": {
					"info.profile": "dev bus"
				}
			}
		]
	}
	```
	
	2. 访问规则｛配置文件会被转换成 Web 接口，规则如下｝
	- /{application}/{profile}[/{label}]
	- /{application}-{profile}.yml
	- /{label}/{application}-{profile}.yml
	- /{application}-{profile}.properties
	- /{label}/{application}-{profile}.properties
	
	3. 访问配置文件；http://localhost:8080/config-client-dev.yml ｛可以直接访问查看配置信息｝
	
	```java
	info:
		profile: dev bus
	```
	
5. 访问使用配置的客户端
   1. 访问端口9001；http://localhost:9001/config
	  ```java
	  dev bus
	  ```
   2. 更改配置，POST请求刷新配置总线；http://localhost:8080/actuator/bus-refresh/  ｛如果配置Git的Webhooks则更新代码自动刷新｝
   3. 访问端口9001；http://localhost:9001/config
	  ```java
	  dev
	  ```
	  
## 综上总结
1. Spring Cloud Bus 可以更加方便的控制全局信息，用于统一刷新并通过MQ方式通过客户端
2. 如果你的内网想进行Git的Webhooks配置，可以使用http://natapp.cn进行内网穿透映射，他会给你提供免费外网调用服务
3. 消息总线方式不只是应用于配置刷新，在一起同步信息请求中都可以使用，以及自己的项目架设上

------------

上一篇：[Spring Cloud(六)《基于github webhook动态刷新服务配置》](/itstack-demo-springcloud/2019/11/06/Spring-Cloud(%E5%85%AD)-%E5%9F%BA%E4%BA%8EGithub-Webhook%E5%8A%A8%E6%80%81%E5%88%B7%E6%96%B0%E6%9C%8D%E5%8A%A1%E9%85%8D%E7%BD%AE.html)

下一篇：[Spring Cloud(八)《服务网关路由 Zuul1》](/itstack-demo-springcloud/2019/11/08/Spring-Cloud(%E5%85%AB)-%E6%9C%8D%E5%8A%A1%E7%BD%91%E5%85%B3%E8%B7%AF%E7%94%B1-Zuul1.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**SpringCloud专题**」获取本文源码&更多原创专题案例！