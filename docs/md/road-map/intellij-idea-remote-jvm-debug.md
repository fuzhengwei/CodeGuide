---
title: IntelliJ IDEA Remote JVM Debug
lock: need
---

# IntelliJ IDEA Remote JVM Debug - 本地远程调试服务器运行代码

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

我遇到过一种bug场景，本地没什么问题，部署到服务器上就不行了。往往遇到这样的问题，要花费大量的时间检查程序逻辑，一个个方法 mock 测试验证。那有人会问了，你怎么不在本地 debug 调试下呢？

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/intellij-idea-remote-jvm-debug-01.gif" width="150px"/>
</div>

**你的代码本地没法启动！**

其实并不是所有的工程代码，都能本地启动运行的。尤其复杂的工程，与外部对接非常多，甚至还有一些是风险控制的问题，本地是不能启动直接调用的。也就是控制研发人员，不允许本地程序调用其他程序的接口。那么对于这样的工程，研发的自测就要通过编写 [mock](https://bugstack.cn/md/road-map/mock.html) 方式进行单元化测试。

不过这里会有一个问题，单元化的测试，mock 的数据是不会随着外部所有程序的调整动态的变更的，而是随着研发编写需求，一次写好后，后续如果这个功能没有被调整，那么 mock 测试也不会在调整了。同时还因为 mock 覆盖的场景不全，不知道引入的外部那么多接口都有哪些新增的逻辑。因而，你可能本地运行没问题，但部署到测试环境，就会有一些不缺性的报错。

对于这里的报错，当你没有 debug 手段的时候，就要把前后的报错数据，都要复制到本地，通过 mock 的方式验证程序逻辑，一点点排查。不过，这个过程也要花费好长时间，尤其是一些复杂的逻辑与外部交互又非常多的时候，调试起来很耗费时间。

所以，程序员👨🏻‍💻对于实在难以调试的代码，还有一种方式就是远程调试。把代码部署到服务器，通过请求服务器的接口，本地的 IntelliJ IDEA 打开的工程，就可以调试对应的运行数据。可以非常高效的解决 bug！

接下来，小傅哥就带着大家做一个这样的案例。如果你是一个新手小白，就更有必要学习一下这样的手段了。

> 🧧 文末还提供了17个实战项目，全套的文档、视频、代码，都可以获取。还有非常清晰的学习路线！嘎嘎冲！

## 一、测试工程

这里小傅哥为你准备好了一个测试工程，你可以直接下载验证。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/intellij-idea-remote-jvm-debug-02.png" width="950px"/>
</div>

- 地址：[https://github.com/fuzhengwei/xfg-dev-tech-remote-jvm-debug](https://github.com/fuzhengwei/xfg-dev-tech-remote-jvm-debug)
- 环境：JDK 1.8、SpringBoot 2.7
- 如图，工程提供了简单的 TestApiController 一个 http 测试入口。之后通过 Dockerfie 方式构建镜像，以及提供了 docker-compose-app.yml 启动工程。重要的是 JAVA_REMOTE_DEBUG 的配置。这是一种 JavaAgent 技术，如果感兴趣可以进入小傅哥的博客，[https://bugstack.cn/](https://bugstack.cn/) 字节码编程中学习。

### 1. 测试接口

```java
@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/test/")
public class TestApiController {

    /**
     * curl --request POST \
     *   --url 'http://127.0.01:8091/xfg/api/v1/test/group_buy_notify?requestDTO=1111'
     *
     * 注意，yml 里配置了应用根目录；server.servlet.context-path: /xfg
     */
    @RequestMapping(value = "group_buy_notify", method = RequestMethod.POST)
    public String groupBuyNotify(@RequestParam String requestDTO) {
        log.info("请求参数 {}", JSON.toJSONString(requestDTO));

        return "success";
    }

}
```

- 一个简单的测试接口，访问接口地址为；`http://127.0.01:8091/xfg/api/v1/test/group_buy_notify?requestDTO=1111`
- 你可以复制 curl 部分，导入到 apipost/apifox 等工具里使用。

### 2. Dockerfile

```java
# 基础镜像
FROM openjdk:8-jre-slim

# 作者
MAINTAINER xiaofuge

# 配置
ENV PARAMS=""

# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 添加应用
ADD target/xfg-dev-tech-remote-jvm-debug-app.jar /xfg-dev-tech-remote-jvm-debug-app.jar
EXPOSE 8091 5005

ENTRYPOINT ["sh","-c","java $JAVA_REMOTE_DEBUG -jar $JAVA_OPTS /xfg-dev-tech-remote-jvm-debug-app.jar $PARAMS"]
```

- ENTRYPOINT，添加了一个 $JAVA_REMOTE_DEBUG 的动态入参，其实这个位置要填入的就是 `-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005` 但并不是上线的所有程序都要做这样的事情，一般只有测试环境才需要。
- EXPOSE 8091 5005 对外暴漏应用程序所需的 8091 5005 端口。

### 3. DockerCompose 部署脚本

```java
# /usr/local/bin/docker-compose -f /docs/dev-ops/environment/environment-docker-compose-2.4.yml up -d
version: '3.8'
# docker-compose -f docker-compose-app.yml up -d
services:
  xfg-dev-tech-remote-jvm-debug-app:
    image: fuzhengwei/xfg-dev-tech-remote-jvm-debug-app:1.0
    container_name: xfg-dev-tech-remote-jvm-debug-app
    restart: on-failure
    ports:
      - "5005:5005"
      - "8091:8091"
    environment:
      - TZ=PRC
      - SERVER_PORT=8091
      # 2c4g 配置，4c8g 翻倍，-Xms4096m -Xmx4096m | -Xmx –Xms：指定java堆最大值（默认值是物理内存的1/4(<1GB)）和初始java堆最小值（默认值是物理内存的1/64(<1GB))
      - JAVA_OPTS=-Xms2048m -Xmx2048m
      - JAVA_REMOTE_DEBUG=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005
    volumes:
      - ./log:/data/log
    logging:
      driver: "json-file"
      options:
        max-size: "10m"
        max-file: "3"
    networks:
      - my-network

networks:
  my-network:
    driver: bridge
```

- 部署服务注意，要对外提供 5005、8091 两个映射端口。对于这种端口的暴漏说明，我已经提供好了基础教程，可以在这里扩展学习；[https://bugstack.cn/md/road-map/docker-what.html](https://bugstack.cn/md/road-map/docker-what.html)
- 另外在 environment 环境中，配置 `- JAVA_REMOTE_DEBUG=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005` 这样就可以把远侧调试的服务给启动起来了。相当于你在自己的电脑的 IntelliJ IDEA 与服务器上的应用，通过 5005 端口进行通信完成测试debug验证。

## 二、部署程序

确保你自己本地已经安装好了 [Docker](https://www.docker.com/) Mac、Windows 电脑都可以安装。

### 1. 打包程序

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/intellij-idea-remote-jvm-debug-03.png" width="650px"/>
</div>

- 通过 IntelliJ Install 打包程序。其实就是 Maven 命令，mvn clean install 的操作。


### 2. 构建镜像

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/intellij-idea-remote-jvm-debug-04.png" width="650px"/>
</div>

1. mac 电脑可以点绿色箭头。
2. windows 电脑，可以通过 powershell 执行 ./build.sh

### 3. 发布项目

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/intellij-idea-remote-jvm-debug-05.png" width="850px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/intellij-idea-remote-jvm-debug-06.png" width="850px"/>
</div>

- mac 点击绿色箭头启动程序即可。
- windows 打开 powershell 执行脚本 `docker-compose -f docker-compose-app.yml up -d`

## 三、调试程序

### 1. IntelliJ IDEA 配置

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/intellij-idea-remote-jvm-debug-07.png" width="950px"/>
</div>

- 首先，在运行调试按钮那，点击下拉框。增加一个新的调试配置。
- 之后，点击+号，添加 `Remote JVM Debug`
- 最后，填写 IP 地址和端口，点击 Apply OK 即可。

### 2. 导入接口

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/intellij-idea-remote-jvm-debug-08.png" width="950px"/>
</div>

- 导入接口到 ApiPost 中调用。其他的工具也可以，这样接口可以保存起来，方便以后调试。

### 3. 远程调试

#### 3.1 启动程序

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/intellij-idea-remote-jvm-debug-09.png" width="450px"/>
</div>

- 以新建的远程 debug 方式，启动程序。

#### 3.2 添加断点

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/intellij-idea-remote-jvm-debug-10.png" width="750px"/>
</div>

- 添加断点。注意，这会的程序部署的要一致，不能动代码。否则和远程部署的不一致，是不能调试的。

#### 3.3 调用接口

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/intellij-idea-remote-jvm-debug-11.png" width="650px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/intellij-idea-remote-jvm-debug-12.png" width="650px"/>
</div>

- 首先，在 ApiPost 中点击【发送】按钮。
- 这会，你可以在打了断点的程序中，查看到请求的调用会进来。最终执行完，远程的部署的程序也会执行完。

🌶 好啦，到这你就已经学会了远程调试了。以后自己的简历中，对于项目的描述，此类问题，你也可以讲自己是如何做的远程调试。
