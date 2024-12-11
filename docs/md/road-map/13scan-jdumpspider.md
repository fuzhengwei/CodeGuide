---
title: w13scan-jdumpspider 安全漏洞扫描
lock: need
---

# 安全漏洞扫描，他怎么拿到了我的数据库密码？

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

你有没有想过，你部署到线上的 SpringBoot 应用，在做互联网`安全人员`的手里，已经成了"小肉鸡"？可怕的攻击不一定是 DDOS 玩命的访问你的网站，消耗你的 CDN 流量。而是拿到你的应用系统的一系列配置，如；数据库账号密码和连接地址、微信公众平台核心配置、OpenAI APIKey。可以说这些东西一暴漏，那你得遭老罪喽。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-idea-00.png" width="150px"/>
</div>

**那这是怎么被发现暴漏的呢？🤔**

做安全的有一套应用漏洞检查扫描的软件，它通过启动被动流量扫描服务，通过代理的方式，挂载到自身的浏览器。日常的访问一些网站时，所有的流量节点都会进入到被动扫描代理服务中，之后消耗这些流量节点，依次检测每个地址下可能发生的；XSS、jsonp信息泄露、sql注入、http smuggling 走私攻击，以及可以根据各类组件提供的漏洞问题做扫描补偿。这样就可以拿到你的网站都有哪些安全漏洞问题了。

这里有一个最典型的就是 SpringBoot Actuator 安全点暴漏，这东西是可以拿到你的 heapdump，之后再通过 JDumpSpider 扫描，也就可以拿到你应用中的数据源、配置文件、Redis配置、ShiroKey等信息。此时如果你的数据库恰好没有配置IP访问限制，那么直接远程连接登录进去，一顿操作了。

那么整个这样一个流程怎么玩一下呢？这东西只有亲自上下手，才会感受到它的恐怖。接下来，小傅哥就教你做被动安全扫描操作和 heapdump 下载分析提取数据库密码。

## 一、流程介绍

关于被动扫描系统安全漏洞的服务系统有很多，包括 w13scan 免费开源的，也有 xray 付费的，还有的是公司或者个人独立开发的。本节我们主要使用 w13scan 作为安全漏洞扫描的案例。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-w13scan-jdumpspider-01.png" width="500px"/>
</div>

- 首先， w13scan 的流程是这样，你需要先启动一个这样的被动扫描服务，之后再你的浏览器中配置网页请求代理地址。这个代理地址就是 w13scan 服务。
- 那么，这个时候你所有在浏览器发起的请求，都会经过 w13scan 走一圈。这样，就可以把请求的网址进行一系列安全扫描检查，查看是什么样的漏洞。
- 另外，w13scan 不只是可以被动扫描还可以主动配置路径扫描一个固定的网站地址检测。
- 最后，w13scan 会把扫描到的安全漏洞以html方式展示出来，我们可以在这个网页上查看漏洞检测结果。也就可以在进一步对存在的漏洞进行挖局，如拿到 heapdump 在解析它的信息了。

## 二、案例工程

这里小傅哥为你提供了完整的案例测试工程，你可以直接完整体验。`你需要有 docker 环境或者 Python 3.6+`

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-w13scan-jdumpspider-02.png" width="750px"/>
</div>

地址：[https://github.com/fuzhengwei/xfg-dev-tech-w13scan-jdumpspider](https://github.com/fuzhengwei/xfg-dev-tech-w13scan-jdumpspider)

- 首先，你需要下载这个工程到本地电脑，便于安装环境和测试。
- 如图，已经为你描述了案例的功能。

## 三、被动扫描

**源码**：[https://github.com/fuzhengwei/w13scan](https://github.com/fuzhengwei/w13scan) - 小傅哥 fork 的 w13scan 并做了镜像打包。源码文档里提供了安装和使用说明。

### 1. 组件安装 - docker 方式

```java
# docker-compose -f docker-compose.yml up -d
version: '3.8'
services:
  w13scan:
    image: fuzhengwei/w13scan:1.0
    container_name: w13scan
    ports:
      - "7778:7778"
    volumes:
      - ./output:/w13scan/W13SCAN/output
    entrypoint: ["python3", "w13scan.py", "-s", "0.0.0.0:7778", "--html"]
    tty: true
```

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-w13scan-jdumpspider-03.png" width="750px"/>
</div>

- 你可以通过脚本 `docker-compose -f docker-compose.yml up -d` 脚本执行 w13scan 的服务安装。
- 另外，如果你不能执行 docker-compose 还可以通过提供的 `docker run -d --name w13scan -p 7778:7778 -v ./output:/w13scan/w13scan/output fuzhengwei/w13scan:1.0 python3 w13scan.py -s 127.0.0.1:7778 --html`

### 2. 组件安装 - Python 方式

这个方式就把代码拉到自己本地，进行构建和启动。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-w13scan-jdumpspider-04.png" width="750px"/>
</div>

```java
git clone https://github.com/fuzhengwei/w13scan.git
cd w13scan # 进入git目录
pip3 install -r requirements.txt
cd W13SCAN # 进入源码目录
python3 w13scan.py -h
```

```java
python3 w13scan.py -s 127.0.0.1:7778 --html # 端口可省略，默认为7778,开启--html即实时生成html报告
```

- 安装完成后启动，也可以看到一个 ` HTTPServer is running at address('0.0.0.0','7778')......` 日志。

### 3. 代理配置

无论使用那种方式部署，启动完成后，都需要给浏览器配置代理。其实也就是你的电脑的网络哪里配置下代理。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-w13scan-jdumpspider-05.png" width="750px"/>
</div>

- 给你的网络配置代理服务，我这里是 mac 电脑点网络wifi那里进入的配置。你可以找到你的位置进行添加。

### 4. 下载证书

下载：[http://w13scan.ca/](http://w13scan.ca/)

一定是你服务启动成功，代理配置成功。那么你在点击下载后，才能下载一个 `download.crt` 文件。之后双击点开信任即可。

### 5. 开始使用

一切配置成功后，你可以在浏览器访问你想检查漏洞的网址，一段时间后就可以看到被动扫描组件检测的数据信息了。之后进入到 `w13scan/W13SCAN/output` 查看检测文件了。这些 html 文件直接用浏览器打开和刷新即可。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-w13scan-jdumpspider-06.png" width="750px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-w13scan-jdumpspider-09.png" width="750px"/>
</div>

- 现在你就看到一些展示的系统安全漏洞了，再根据这些漏洞进行**友好的访问**

## 四、heapdump 下载

测试前你可以先启动 xfg-dev-tech-w13scan-jdumpspider 应用。这样就可以做后面的流程以及获取 heapdump 日志了。

### 1. 问题背景

测试工程中的 yml 配置。这个配置为了配合普罗米修斯做监控使用的，但要注意如果只是这样配置，是很有风险的。

```java
# 监控
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
  metrics:
    export:
      prometheus:
        enabled: true
  prometheus:
    enabled: true
```

- 如果你的 SpringBoot 系统配置了这样的监控，并且没有配置相关的安全校验，Spring Security 那么现在你就暴漏了自己的端点。

### 2. 端点访问

地址：[http://127.0.0.1:8091/actuator/env](http://127.0.0.1:8091/actuator/env)

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-w13scan-jdumpspider-07.png" width="550px"/>
</div>

- 这个地址，就可以由被动扫描检测出来。不过不同的被动扫描组件检测点不同，有些是没有的。

### 3. 下载 dump

地址：[http://127.0.0.1:8091/actuator/heapdump](http://127.0.0.1:8091/actuator/heapdump)

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-w13scan-jdumpspider-08.png" width="250px"/>
</div>

- 把工程的 heapdump 文件下载下来。下载后可以放到案例工程 docs 里 JDumpSpider-1.1-SNAPSHOT-full.jar 的同层路径下。

## 五、heapdump 解析

以前小傅哥带着大家做一篇[《Eclipse MAT 分析 Java heap space dump 日志》](https://bugstack.cn/md/road-map/dump-mat.html) 这个也能分析 dump 日志查看系统中的核心数据。不过这里我们选择个更针对性的 JDumpSpider 做日志分析，直接提取核心配置。

源码：[https://github.com/whwlsfb/JDumpSpider](https://github.com/whwlsfb/JDumpSpider) - 有相关的使用说明。

命令：`java -jar JDumpSpider-1.1-SNAPSHOT-full.jar heapdump`

```java
/bin/zsh /xiaofuge/develop/github/xfg-dev-tech-w13scan-jdumpspider/docs/jdumpspider.sh
(base) xiaofuge@ZBMac-GV47H1GXD docs % /bin/zsh /Users/xiaofuge/Documents/develop/github/xfg-dev-tech-w13scan-jdumpspider/docs/jdumpspider.sh
===========================================
SpringDataSourceProperties
-------------
password = 123456
driverClassName = com.mysql.cj.jdbc.Driver
url = jdbc:mysql://127.0.0.1:13306/road_map?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC&useSSL=true
username = root

===========================================
WeblogicDataSourceConnectionPoolConfig
-------------
not found!

===========================================
MongoClient
-------------
not found!

===========================================
AliDruidDataSourceWrapper
-------------
not found!

===========================================
HikariDataSource
-------------
not found!
```

- 命令执行后可以看到你的工程中连接数据库的配置，密码也会直接暴漏出来。其他的配置如果你有添加，也会一并给你拿出来。**底裤给你扒掉**

## 六、安全建议

- 对于自己上线的应用，尤其独立开发者，一定多进行安全扫描。
- 不要无密码暴漏自己的应用，包括任何监控、数据采集、以及第三方组件。
- 数据库、缓存、文件等连接，在云服务器要配置上可访问IP限制。这样就算底裤拔掉了，也还有一个玻璃罩。只能看，不能连。