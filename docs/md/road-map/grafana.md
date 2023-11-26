---
title: Grafana 可视化监控面板
lock: need
---

# Prometheus + Grafana 监控，验证 Hystrix 超时熔断

作者：小傅哥
博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

本文的宗旨在于通过简单干净实践的方式教会读者，使用 Prometheus + Grafana 部署监控，同时结合监控了解到为什么需要使用 Hystrix 对接口进行超时熔断处理。

很多伙伴在面试的时候，都可能会被问到；你的应用接口响应时间多少，Tomcat 配置了多少连接数，如果接口超时了怎么办，会不会把服务拖垮。那会不会呢，其实会的，对于一些接口不稳定容易超时但又不熔断的接口，在用户大量请求的情况下，是很容易把Tomcat连接数打满，直至拖垮整个服务，让服务的任何接口都没有响应。所以本节小傅哥会带着大家，来模拟这样的场景，让大家学习下。

本文涉及的工程：
- xfg-dev-tech-grafana：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-grafana](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-grafana) - `测试工程、监控Docker安装脚本、Grafana监控面板配置JSON`

## 一、你是怎么挂的？

Tomcat 可分配的连接数就像厕所的坑位，一堆用户来上大号。本来4个坑位也够用了，因为用户来了也可以快速释放请求，不会长时间占用。但突然有这么一天，用户都拉肚子，一个进去就1个小时候，其余人都排队。最后给压垮了！

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-grafana-01.png?raw=true" width="550px">
</div>

那是不是，增加了连接数(WC 更多的坑位就好了呢)，其实也好不哪去，你总不能给所有的用户都建一个坑位，而且坑位越多，距离也越长了，这就会涉及到线程的切换，也是不小的资源消耗。

所以，为了保护我方Tomcat(WC 坑位)，则需要快速熔断，而不是让它一直占用着链接不释放。

## 二、工程环境配置

这里小傅哥做一个 SpringBoot 工程测试案例，并配合添加 Hystrix 熔断组件，以及使用 Grafana 监控来观察简单压测时连接数的消耗和接口性能的反馈。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-grafana-02.png?raw=true" width="450px">
</div>

- 环境；jdk 1.8、Maven 3.6.x、Docker 环境
- 代码；在 xfg-dev-tech-grafana 测试工程中提供了测试代码和环境安装

### 1. 配置修改

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-grafana-07.png?raw=true" width="650px">
</div>

- 首先，你需要打开 prometheus.yml 修改监控采集应用的IP地址。这个配置还可以监控例如 MySQL 和其他应用。

### 2. 安装监控

文件：`docker-compose.yml`

```java
version: '3'
# 执行脚本；docker-compose -f docker-compose.yml up -d
# 拷贝配置；docker container cp grafana:/etc/grafana/ ./docs/dev-ops/
services:
  # 数据采集
  prometheus:
    image: bitnami/prometheus:2.47.2
    container_name: prometheus
    restart: always
    ports:
      - 9090:9090
    volumes:
      - ./etc/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml
  # 监控界面
  grafana:
    image: grafana/grafana:10.2.0
    container_name: grafana
    restart: always
    ports:
      - 4000:4000
    depends_on:
      - prometheus
    volumes:
      - ./etc/grafana:/etc/grafana
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-grafana-03.png?raw=true" width="950px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-grafana-04.png?raw=true" width="950px">
</div>

- 这是 docker compose 执行脚本，如果你本地已经安装了 Docker 可以直接执行 安装即可。
- 注意；如果你是生产使用，则需要修改 `etc/grafana/grafana.ini` 中 datasources 为 mysql 这样在后面迁移的时候也会非常容易。

### 3. 监控配置

- 安装完监控环境以后，可以先打开 Grafana 地址：[http://127.0.0.1:4000](http://127.0.0.1:4000) - `admin/admin`

- 地址：[http://127.0.0.1:4000/dashboards](http://127.0.0.1:4000/dashboards) - 打开仪表盘，导入监控面板配置。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-grafana-05.png?raw=true" width="950px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-grafana-06.png?raw=true" width="950px">
</div>

- 打开导入面板后，把案例工程中的 JSON 复制到导入面板的 JSON 里，点击 Load 这样就配置进去了。
- 不过这会你还看不见数据，因为工程还没有启动，没有往里写入数据。

## 三、测试应用说明

### 1. 熔断配置

```pom
<dependency>
    <groupId>com.netflix.hystrix</groupId>
    <artifactId>hystrix-javanica</artifactId>
    <version>1.5.18</version>
</dependency>
```

- 这个熔断的组件，不需要引入 SpringCloud 一堆的东西，使用起来更加容易。

```java
@Configuration
public class HystrixConfig {

    @Bean
    public HystrixCommandAspect hystrixCommandAspect() {
        return new HystrixCommandAspect();
    }

}
```

- 引入 POM 后，添加 HystrixConfig 熔断配置。

### 2. 接口配置

这里小傅哥提供了2个接口，一个普通的查询数据接口，一个是 OpenAi 中服务的给前端异步响应结果的接口。尤其是 OpenAi 异步接口，我们在实际使用的时候，也总会有超时熔断，所以这里给大家添加上。

#### 2.1 普通接口

```java
/**
 * curl http://localhost:8091/api/hystrix/query_order_info
 */
@HystrixCommand(commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "50")
}, fallbackMethod = "queryOrderInfo_error"
)
@RequestMapping(value = "query_order_info", method = RequestMethod.GET)
public String queryOrderInfo() throws InterruptedException {
    new CountDownLatch(1).await();
    return "您的订单信息查询完毕";
}

private String queryOrderInfo_error() throws InterruptedException {
    return "Fallback Hello";
}
```

- 测试过程中，超时熔断时间，可以设置的较大一些，也可以先不添加超时熔断的注解。

#### 2.2 异步接口

```java
/**
 * curl http://localhost:8091/api/hystrix/stream
 */
@HystrixCommand(commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "150")
}, fallbackMethod = "stream_error"
)
@RequestMapping(value = "/stream", method = RequestMethod.GET)
public ResponseBodyEmitter stream(HttpServletResponse response) throws Exception {
    response.setContentType("text/event-stream");
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Cache-Control", "no-cache");
    ResponseBodyEmitter emitter = new ResponseBodyEmitter();
    emitter.send("异步响应");
    new Thread(() -> {
        for (int i = 0; i < 200; i++) {
            try {
                emitter.send("hi xfg-dev-tech-grafana\r\n" + i);
                Thread.sleep(250);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        emitter.complete();
    }).start();
    return emitter;
}

public ResponseBodyEmitter stream_error(HttpServletResponse response) throws IOException {
    response.setContentType("text/event-stream");
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Cache-Control", "no-cache");
    ResponseBodyEmitter emitter = new ResponseBodyEmitter();
    emitter.send("Err");
    emitter.complete();
    return emitter;
}
```

- 另外一个是一步响应接口，如果响应时间较长，则进入超时熔断方法中。

## 四、服务压测验证

### 1. 配置信息

**application.yml**

```yml
server:
  port: 8091
  # 1核2G内存，可默认配置 200；4核8G内存【accept-count=1000、max-threads=800、max-connections=10000】，线程数经验值800。线程池过大，cpu调度会消耗大量时间
  tomcat:
    mbeanregistry:
      enabled: true
    max-connections: 20 # 最大连接数  
    threads:
      max: 20         # 设定处理客户请求的线程的最大数目，决定了服务器可以同时响应客户请求的数,默认200
      min-spare: 10   # 初始化线程数,最小空闲线程数,默认是10
    accept-count: 10  # 等待队列长度
```

- 我们在 YML 文件中指定了 tomcat 的连接数配置为 20 个，处理线程最多也是20个。
- 同时还添加了监控空你暴露为true，这样 Grafana 才能监控到。

### 2. 压测工具

压测工具有很多，包括；[JMeter、ApacheBench、Siege](https://bugstack.cn/md/road-map/jmeter.html) 小傅哥已经在前面写过文章，也可以去参考使用。

不过这里为了让大家更加简单的压测下，小傅哥使用了 ApiPost 工具自带的压测，这样不需要自己再安装其他工具就可以简单压测下了。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-grafana-08.png?raw=true" width="750px">
</div>

- 你只要把2个接口配置到 ApiPost 就可以用一键压测工具进行压测。

### 3. 压测观察

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-grafana-09.png?raw=true" width="850px">
</div>

- 这里小傅哥压测了下接口 `http://localhost:8091/api/hystrix/stream` 接口。因为这个接口设置了超时，可以看见，快速的就把连接数给占满了。
- 所以如果你的应用配置的 Tomcat 连接数不合理，之后接口又容易超时，超时后又没有熔断，那么很容易就会把你的服务拖垮。很多新人在做一些对外的应用时，如果没有注意到这些，那么也是很容易宕机的。
