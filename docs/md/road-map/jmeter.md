---
title: JMeter
lock: need
---

# JMeter 压测工具的配置和使用

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

本文的宗旨在于通过简单干净实践的方式教会读者，如何使用[JMeter](https://jmeter.apache.org)进行工程的压测测试。也同时会介绍到；ApacheBench、Siege 两个更简单压测工具的使用。

压测是开发完成正式上线对外使用前非常重要的一环，尤其是各个互联网大厂，上线的核心应用和在618、双11以及各种大促时间节点下的应用，都需要进行压测摸底，知道一个系统的最大承载量，并基于这样的一个量的安全范围值内设置熔断、限流和降级的指标。—— 而且这也是面试过程中评估你是否真的做过上线系统的能力考察项；`你的系统负载量多大、部署多少台服务器、响应时间怎么样、峰值是多少`

本文涉及的工程：

- xfg-dev-tech-jmeter：[xfg-dev-tech-jmeter](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-jmeter)

## 一、工具安装

这个安装分为本地安装和云服务安装，一般正规的玩法是本地安装后来写测试计划看效果。之后把jmx文件放到云服务器来执行压测。这样才能不受GUI和本地的限制，压测的比较大。**本文提供了Docker部署和执行 JMX 压测脚本**

### 1. 本地安装

- 环境：JDK 1.8+ - `因为这是一个纯Java开发的软件，所以需要JDK环境`
- 官网：[https://jmeter.apache.org/download_jmeter.cgi](https://jmeter.apache.org/download_jmeter.cgi) - `Mac 下载 apache-jmeter-5.6.2.tgz`、`Windows 下载 apache-jmeter-5.6.2.zip` 注意官网还有对应的文档，很清晰的介绍了使用方法。
- 镜像：[https://mirrors.aliyun.com/apache/jmeter/binaries/](https://mirrors.aliyun.com/apache/jmeter/binaries/) - `如果官网下载比较慢，也可以通过阿里云镜像下载`

### 2. 云服务安装

**脚本**：`xfg-dev-tech-jmeter/docs/dev-ops/docker-compose.yml`

```java
version: '3'
services:
  # JMeter是一个功能强大的性能测试工具，可以模拟多种类型的负载，并提供详细的测试报告
  # 官网：https://jmeter.apache.org/
  # 脚本：jmeter -n -t one.jmx -l one.jtl
  Jmeter:
    image: justb4/jmeter:5.5
    container_name: jmeter
    restart: always
    environment:
      - DISPLAY=:0
      - TZ=Europe/Paris
    volumes:
      - https://bugstack.cn/images/roadmap/tutorial/jmx/:/opt/apache-jmeter-5.5/jmx/
```

- 如果你本地已经安装 Docker 那么直接执行 docker-compose.yml 即可完成安装。
- 注意，`xfg-dev-tech-jmeter/docs/dev-ops/jmx` 下是 JMeter 所保存的压测脚本。

### 1. 解压启动

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jmeter-01.png?raw=true" width="550px">
</div>

进入到 apache-jmeter-5.5/bin 目录下；

- Mac 电脑，在 jmeter 上右键，选择终端启动。
- Windows 电脑，直接点 jmeter.bat 启动。

### 2. 配置语言

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jmeter-02.png?raw=true" width="550px">
</div>

修改语言有2个方式；

- 进入 apache-jmeter-5.5/bin/jmeter.properties 设置 language=zh_CN
- 如图，进入页面，手动选择。

## 二、配置说明

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jmeter-03.png?raw=true" width="550px">
</div>

这是 JMeter 压测配置中非常简单的一个**测试计划**，在这个测试计划下需要包括`线程组 - 负责运行`、`取样器(压测的接口) - 负责调接口`和`至少一个监听器 - 负责看结果`。这样才能完成压测并获得结果。

### 1. 线程组

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jmeter-04.png?raw=true" width="550px">
</div>

通过线程组开启对HTTP接口的请求循环操作方式。它可以模拟配置出流量的负载均值请求、峰值请求、逐步加量等场景。

#### 1.1 函数线程组

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jmeter-05.png?raw=true" width="750px">
</div>

- Open Mode Thread Group 支持配置简单的配置和 Groovy 脚本 如；`${__groovy((1..5).collect { "rate(" + it*10 + "/sec) random_arrivals(10 sec) pause(1 sec)" }.join(" "))}` - 请求10次，每次都递进。
- 你还可以配置这些参数；`rate(0/min) random_arrivals(10 min) rate(100/min)`、`rate(0/min) random_arrivals(5 min) rate(100/min)random_arrivals(100 min)rate(100/min) random_arrivals(5 min) rate(0/min)` - 通过这样的规律，就可以找到如何配置了。
- 此外还支持 JMeter 函数：`pause(2 min) rate(${__Random(10,50,)}/min)random_arrivals(${__Random(10,100,)} min) rate(${__Random(10,1000,)}/min)` - 也可以多行配置。
- 负载举例；总时长为1分10秒。前10秒内，速率达到10/s，然后，在1分钟内吞吐量将保持在10/s。最大吞吐量为600个/分钟。配置：`rate(0/s) random_arrivals(20 s) rate(10/s) random_arrivals(1 m) rate(10/s)`

#### 1.2 简单线程组

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jmeter-06.png?raw=true" width="750px">
</div>

简单线程组配置起来更简单，也适合一些循环压测的场景。
- 线程数：一个用户相当于一个线程。
- Ramp-Up：预期线程组的所有线程从启动-运行-释放的总时间。ramp up=0时，表示瞬时加压，启动线程的时间无限趋近于0。在负载测试的时候，尽量把ramp up设置大一些，让性能曲线平缓，容易找到瓶颈点。
- 循环次数：线程组的循环次数，如果不设置，则表示在调度时间范围内一直循环(jmeter不停的发请求)。
- 调度器：执行的时间设置。

---

此外，JMeter 还可以安装插件，设置更多的线程组模型来压测。

### 2. 取样器

JMeter 把对压测的内容，抽象为取样器。包括HTTP接口、FTP服务等。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jmeter-07.png?raw=true" width="750px">
</div>

如图你可以通过这样的方式，在创建好的线程组下，创建一个取样器(HTTP压测接口)。不过这里小傅哥更建议你使用 cURL 方式导入使用。

#### 2.1 复制 cURL

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jmeter-08.png?raw=true" width="650px">
</div>

#### 2.2 导入 cURL

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jmeter-09.png?raw=true" width="650px">
</div>

- 导入以后，可以把HTTP请求拖到线程组下面。

### 3. 监听器

线程组是各类方式的模拟压测调用，取样器HTTP是压测的接口。那么监听器就是看线程组对取样器HTTP的压测结果。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jmeter-10.png?raw=true" width="650px">
</div>

## 三、工程准备

为了让大家更加方便的测试，不用自己在折腾，可以直接使用测试工程。测试工程内提供了测试的接口，以及对应的 jmx 脚本。启动后就可以执行测试。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jmeter-11.png?raw=true" width="750px">
</div>

- docker-compose.yml 是部署 JMeter 到 Docker 的脚本。并在脚本中映射了本地的压测脚本。
- one.jmx 是在使用 JMeter 时导出的脚本，你可以直接复制 JMeter 脚本，也可以让 JMeter 保存脚本的时候选择到这个路径下。
- 注意 one.jmx 有压测对应接口的 IP，测试的时候需要修改为你的服务器/本机IP才可以。`<stringProp name="HTTPSampler.domain">127.0.0.1</stringProp>`

## 四、压测验证

### 1. 本地压测

- 开启服务：xfg-dev-tech-jmeter
- 启动压测：JMeter

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jmeter-12.png?raw=true" width="450px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jmeter-13.png?raw=true" width="750px">
</div>

### 2. 脚本压测

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jmeter-14.png?raw=true" width="750px">
</div>

压测命令：`jmeter -n -t one.jmx -l one.jtl`

## 五、其他工具

- [ApacheBench](https://httpd.apache.org/docs/2.4/programs/ab.html)：一个轻量级的HTTP性能测试工具，可以模拟多种类型的负载，并提供详细的测试报告。脚本：`ab -n 10 -c 2 http://localhost:8091/api/jmeter/query_order_info?orderId=100001`
- [Siege](#)：是常用的HTTP性能测试工具，可以模拟多个并发用户发送请求。脚本：`siege -c10 -r1 -p http://localhost:8091/api/jmeter/query_order_info?orderId=100001`
- [ApiPost](https://www.apipost.cn/)：自带接口简单压测模拟，安装更加简单，适合初步压测验证。