---
title: AREX - 流量录制&回放
lock: need
---

# AREX - 流量录制&回放

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

在互联网大厂应用系统开发中，有一项非常重要的技术手段，保证新迭代功能的可靠性。虽然，这像技术也不是“银弹（啥都能搞定）”，但缺少这项技术，对于极其复杂的业务场景新上线功能，总感觉心里没底！—— 它就是`流量录制`和`流量回放`！

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/student-learn-01.gif" width="150px">
</div>

**如何保证系统的可靠性？**

互联网大厂toc场景，非常重视交付的可靠性，甚至大于交付的效率性。所以，在整个研发声明周期过程中，要有 PRD 评审、研发设计代码、代码评审、代码分支合并评审，以及在这个过程中还有测试人员进行全流程的验证。

但即使这样，面对经历了数十年迭代的系统，单工程甚至几十万行代码，极其复杂的业务系统流程，测试也是可能会出现疏漏。这种疏漏如，虽然非本次需求迭代的场景，但是其他业务场景因当前系统的一个枚举，一个参数的变动，都会影响到数十条业务。哪怕不是代码变动，仅仅是重构了小部分代码块，调整了前后顺序，当前业务无影响，但确影响了某次其他任务的跑批结果！

一次事故，可能是扣绩效和年终奖，也可能是影响晋升提名，还可能是直接毕业🎓啦！所以，在互联网大厂班组🧱搬砖，会学到很多高级技巧，来保证系统的可靠性。

>关注小傅哥 [bugstack.cn](https://bugstack.c) 博客，编程路书，可以学习到非常多的互联网企业使用技能，补全自己的短板！如；系统架构、开发环境、开发技术、授权框架、常用类库、工程测试、质量监控、发布部署等。

## 一、说说概念

其实简单来讲，流量录制，核心的本质就是把线上运行的应用，在有流量请求到服务接口后，把请求接口的入参和执行的结果，都保存下来。之后流量回放，则是把这些保存下来的入参信息，在开发环境/测试环境/预发环境，进行请求，之后验证请求后的结果是否与线上录制的流量结果一致。整个的这个过程，就是流量的录制和回放。

但实际的操作场景要比这个过程复杂，如；录制流量的操作，要无侵入代码的，要把整个调用链路，全部录制下来，而不是单个接口。同时录制的不只是对接口的操作，还有缓存、rpc、数据库（MyBatis）、es，等各类组件兼容，都能确保可正确录制到完整数据，以便在流量回放时，可以mock掉数据接口，以此只验证功能逻辑。

说到，逻辑和数据，这里想到了DDD架构的领域层和基础设施层，领域层（domain）专门业务功能逻辑的，而基础设置层（infrastructure）则是提供数据的。这样有了业务功能和数据获取的分离，不让核心业务逻辑又掺杂外部接口的调用，其实维护和迭代起来，是会更加容易的。

## 二、讲讲原理

为了可以无侵入式的完成系统的流量录制和流量回放，这里引入了一个 Java Agent 字节码增强技术，通过在程序启动阶段，对工程代码的方法进行埋点。这个埋点，就等同于动态的通过 class 文件写入新的代码，新的代码可以采集到方法的出入参信息，并把这些信息发送到流量录制存储方，或者执行时进行mock接口请求结果处理。

这部分技术方案，小傅哥于19-20年，编写了先关的技术资料。其中包括了用于处理字节码增强技术的，asm、javassist、byte-buddy 三种框架。

<div align="center">
    <img src="https://bugstack.cn/assets/images/2020/itstack-demo-asm-02-1.png" width="750px">
</div>

文档：[https://bugstack.cn/md/bytecode/asm/2020-04-05-%5BASM%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B%5DJavaAgent+ASM%E5%AD%97%E8%8A%82%E7%A0%81%E6%8F%92%E6%A1%A9%E9%87%87%E9%9B%86%E6%96%B9%E6%B3%95%E5%90%8D%E7%A7%B0%E4%BB%A5%E5%8F%8A%E5%85%A5%E5%8F%82%E5%92%8C%E5%87%BA%E5%8F%82%E7%BB%93%E6%9E%9C%E5%B9%B6%E8%AE%B0%E5%BD%95%E6%96%B9%E6%B3%95%E8%80%97%E6%97%B6.html](https://bugstack.cn/md/bytecode/asm/2020-04-05-%5BASM%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B%5DJavaAgent+ASM%E5%AD%97%E8%8A%82%E7%A0%81%E6%8F%92%E6%A1%A9%E9%87%87%E9%9B%86%E6%96%B9%E6%B3%95%E5%90%8D%E7%A7%B0%E4%BB%A5%E5%8F%8A%E5%85%A5%E5%8F%82%E5%92%8C%E5%87%BA%E5%8F%82%E7%BB%93%E6%9E%9C%E5%B9%B6%E8%AE%B0%E5%BD%95%E6%96%B9%E6%B3%95%E8%80%97%E6%97%B6.html)

### 1. 增强代码

```java
mv.visitVarInsn(LLOAD, startTimeIdentifier);
mv.visitLdcInsn(methodId);
if (parameterTypeList.isEmpty()) {
    mv.visitMethodInsn(INVOKESTATIC, Type.getInternalName(ProfilingAspect.class), "point", "(JI)V", false);
} else {
    mv.visitVarInsn(ALOAD, parameterIdentifier);  // 5
    mv.visitVarInsn(ALOAD, localCount);           // 6
    mv.visitMethodInsn(INVOKESTATIC, Type.getInternalName(ProfilingAspect.class), "point", "(JI[Ljava/lang/Object;Ljava/lang/Object;)V", false);
}
```

### 2. 拦截信息

```java
ASM类输出路径：/Users/xiaofuge/itstack/git/github.com/SQM/target/test-classes/org/itstack/test/ApiTest$1SQM.class
监控 - Begin
类名：org.itstack.test.ApiTest
方法：queryUserInfo
入参类型：["I","I"]
入数[值]：[111,17]
出参类型：Ljava/lang/String;
出参[值]："你好，bugstack虫洞栈 | 精神小伙！"
耗时：95(s)
监控 - End

测试结果：你好，bugstack虫洞栈 | 精神小伙！
```

- 以上为简要的案例代码，通过ASM字节码增强框架来对方法修改，增加日志打印信息，在执行方法时，获取出入参。
- 字节码增强技术应用的方面非常多，如；MapStruct、非入侵的全链路监控、Lombok，甚至你用过的 IntelliJ IDEA 破解也是字节码增强技术。

## 三、工具介绍

关于流量录制和流量回放的工具是蛮多的，其中包括；[jvm-sandbox-repeater](https://github.com/alibaba/jvm-sandbox-repeater)、[MoonBox](https://github.com/vivo/MoonBox)、[arextest](https://arextest.com/) 等。

- jvm-sandbox-repeater，是一个基于 JVM-Sandbox 采用Java来实现的流量录制回放工具，或者可以理解为它是一个基于Java虚拟机的插件，可以直接运行中JVM中，无需对目标应用程序进行任何修改。
- Moonbox（月光宝盒），是一个无侵入的线上流量录制 和流量回放平台，沿用了jvm-sandbox-repeater的SPI设计，并提供了大量的常用插件，同时也提供数据统计和存储能力。通过Moonbox可以实现自动化测试、线上问题追踪、和业务监控等能力
- arextest，AREX 通过将真实的在线流量复制到测试环境进行自动化 API 测试来解决自动化测试的挑战。**作为本文分享场景工具使用。**

**名词解释**

- **录制**：把一次请求的入参、出参、**下游RPC、DB、缓存**等序列化并存储的过程
- **回放**：把录制数据还原，重新发起一次或N次请求，对特定的下游节点进行MOCK的过程
- **入口调用**：入口调用一般是应用的**流量来源**，比如http/dubbo，在调用过程中录制调用入参，返回值。回放时作为流量发起和执行结果对比依据
- **子调用**：区别于入口调用，子调用是调用执行过程中某次方法调用。子调用在录制时会记录该方法的入参、返回值；回放时用该返回值进行MOCK
- **MOCK**：在回放时，被拦截的子调用**不会发生真实调用**，利用字节码动态干预能力，将录制时的返回值直接返回
- **降噪**：在回放时，部分回放子调用入参或者回放流量响应结果和原始流量对比不一致字段，对这些非必要字段进行排除对比过程

## 四、工具使用

### 1. 案例工程

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-traffic-agent-01.png" width="800px">
</div>

- 地址：[https://github.com/fuzhengwei/xfg-dev-tech-traffic-agent](https://github.com/fuzhengwei/xfg-dev-tech-traffic-agent)
- 如图：1~6点，说明了各个模块的用途。
- 提示：建议使用云服务器部署流量录制&回放服务，非常方便。

### 2. 安装插件

地址：[https://chromewebstore.google.com/detail/arex-chrome-extension/jmmficadjneeekafmnheppeoehlgjdjj](https://chromewebstore.google.com/detail/arex-chrome-extension/jmmficadjneeekafmnheppeoehlgjdjj)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-traffic-agent-04.png" width="800px">
</div>

- 安装插件是为了有权限访问接口。

### 3. 服务部署（arex）

**前置条件**，2c4g云服务器，完成初始化Docker环境。这里有一键安装脚本，[https://bugstack.cn/md/road-map/docker-install.html](https://bugstack.cn/md/road-map/docker-install.html)

```java
[root@iv-jdyun ~]# cd /
[root@iv-jdyun ~]# mkdir dev-ops
[root@iv-jdyun ~]# cd dev-ops
[root@iv-jdyun ~]# sudo yum install git
[root@iv-jdyun ~]# git clone --depth 1 https://github.com/arextest/deployments.git
[root@iv-jdyun ~]# cd deployments
[root@iv-jdyun ~]# docker-compose up -d
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-traffic-agent-02.png" width="800px">
</div>

- 执行脚本安装完成后，在云服务器安全组开放端口号（出/入规则）。
- 之后访问：http://ip:8088 首次访问会慢一些，进入后会让设置你的登录账号。

### 4. 启动项目 - 录制流量

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-traffic-agent-03.png" width="900px">
</div>

- 首先，需要给项目工程加入一个 agent，来录制和回放。arex 的 agent jar，下载后方到工程下，lib 中。目前工程中已经下载了，如果将来有更新，可以重新下载最新的。地址：[https://github.com/arextest/arex-agent-java/releases](https://github.com/arextest/arex-agent-java/releases)
- 之后，本地项目测试验证的时候，可以先 **install**，成功之后使用 `run_application_2_agent.sh` 启动项目，这个操作会把 agent 加载到程序启动中。

#### 4.1 本地测试

```java
#!/bin/bash

# AREX Agent 启动脚本
# 用于在不同环境下启动应用并加载 AREX Agent

# 设置 AREX Agent 相关参数
AREX_AGENT_PATH="./lib/arex-agent.jar"
AREX_SERVICE_NAME="xfg-dev-tech-app"
AREX_STORAGE_HOST="115.190.107.206:8093"

# 应用 JAR 包路径（相对于项目根目录）
APP_JAR="../../xfg-dev-tech-app/target/xfg-dev-tech-app.jar"

# JVM 参数
JVM_OPTS="-Xms512m -Xmx1024m"

# 检查 AREX Agent 文件是否存在
if [ ! -f "$AREX_AGENT_PATH" ]; then
    echo "错误: AREX Agent 文件不存在: $AREX_AGENT_PATH"
    echo "请确保 arex-agent.jar 文件已放置在正确位置"
    exit 1
fi

# 检查应用 JAR 包是否存在
if [ ! -f "$APP_JAR" ]; then
    echo "错误: 应用 JAR 包不存在: $APP_JAR"
    echo "请先执行 mvn clean package 构建应用"
    exit 1
fi

echo "正在启动应用..."
echo "AREX Agent: $AREX_AGENT_PATH"
echo "Service Name: $AREX_SERVICE_NAME"
echo "Storage Host: $AREX_STORAGE_HOST"
echo "Application: $APP_JAR"
echo ""

echo "执行配置："$JVM_OPTS \
          -javaagent:"$AREX_AGENT_PATH" \
          -Darex.service.name="$AREX_SERVICE_NAME" \
          -Darex.storage.service.host="$AREX_STORAGE_HOST" \
          -jar "$APP_JAR"

# 启动应用
java $JVM_OPTS \
     -javaagent:"$AREX_AGENT_PATH" \
     -Darex.service.name="$AREX_SERVICE_NAME" \
     -Darex.storage.service.host="$AREX_STORAGE_HOST" \
     -jar "$APP_JAR"
```

#### 4.2 docker 镜像

##### 4.2.1 dockerfile

```java
# 基础镜像
FROM openjdk:8-jre-slim

# 作者
MAINTAINER xiaofuge

# 配置
ENV PARAMS=""

# AREX Agent 配置
ENV AREX_SERVICE_NAME="xfg-dev-tech-app"
ENV AREX_STORAGE_HOST="115.190.107.206:8093"

# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 添加 AREX Agent
ARG AREX_AGENT_PATH=docs/dev-ops/lib/arex-agent.jar
ADD ${AREX_AGENT_PATH} /arex-agent.jar

# 添加应用
ADD target/xfg-dev-tech-app.jar /xfg-dev-tech-app.jar

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -javaagent:/arex-agent.jar -Darex.service.name=$AREX_SERVICE_NAME -Darex.storage.service.host=$AREX_STORAGE_HOST -jar /xfg-dev-tech-app.jar $PARAMS"]
```

##### 4.2.2 docker-compose

```java
version: '3.8'

services:
  xfg-dev-tech-app:
    image: fuzhengwei/xfg-dev-tech-app:traffic-agent-v1
    container_name: xfg-dev-tech-app
    ports:
      - "8091:8091"
    environment:
      # AREX Agent 配置
      - AREX_SERVICE_NAME=xfg-dev-tech-app
      - AREX_STORAGE_HOST=115.190.107.206:8093
      # JVM 配置
      - JAVA_OPTS=-Xms512m -Xmx1024m
    volumes:
      # 日志目录挂载（可选）
      - ./data/logs:/var/log/app
    networks:
      - app-network
    restart: unless-stopped
    
networks:
  app-network:
    driver: bridge

volumes:
  app-logs:
    driver: local
```

- 此方式适用于项目部署上线。

### 5. 启动程序

```java
./bin/bash /xfg-dev-tech-traffic-agent/docs/dev-ops/run_application_2_agent.sh
```

```java
(base) fuzhengwei1@ZBMac-GV47H1GXD dev-ops % /bin/bash /Users/fuzhengwei1/Documents/develop/github/xfg-dev-tech-traffic-agent/docs/dev-ops/run_application_2_agent.sh
正在启动应用...
AREX Agent: ./lib/arex-agent.jar
Service Name: xfg-dev-tech-app
Storage Host: 115.190.107.206:8093
Application: ../../xfg-dev-tech-app/target/xfg-dev-tech-app.jar

执行配置：-Xms512m -Xmx1024m -javaagent:./lib/arex-agent.jar -Darex.service.name=xfg-dev-tech-app -Darex.storage.service.host=115.190.107.206:8093 -jar ../../xfg-dev-tech-app/target/xfg-dev-tech-app.jar
2025-06-21 17:57:25:792 [AREX] Agent-v0.4.8 starts initialization...
Java HotSpot(TM) 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::               (v2.7.12)

25-06-21.17:57:27.856 [main            ] INFO  Application            - Starting Application v1.0-SNAPSHOT using Java 17.0.7 on ZBMac-GV47H1GXD with PID 96181 (/Users/fuzhengwei1/Documents/develop/github/xfg-dev-tech-traffic-agent/xfg-dev-tech-app/target/xfg-dev-tech-app.jar started by fuzhengwei1 in /Users/fuzhengwei1/Documents/develop/github/xfg-dev-tech-traffic-agent/docs/dev-ops)
25-06-21.17:57:27.858 [main            ] INFO  Application            - The following 1 profile is active: "dev"
25-06-21.17:57:28.635 [main            ] INFO  TomcatWebServer        - Tomcat initialized with port(s): 8091 (http)
25-06-21.17:57:28.641 [main            ] INFO  Http11NioProtocol      - Initializing ProtocolHandler ["http-nio-8091"]
25-06-21.17:57:28.641 [main            ] INFO  StandardService        - Starting service [Tomcat]
25-06-21.17:57:28.642 [main            ] INFO  StandardEngine         - Starting Servlet engine: [Apache Tomcat/9.0.75]
25-06-21.17:57:28.683 [main            ] INFO  [/]                    - Initializing Spring embedded WebApplicationContext
25-06-21.17:57:28.683 [main            ] INFO  ServletWebServerApplicationContext - Root WebApplicationContext: initialization completed in 781 ms
25-06-21.17:57:28.913 [main            ] INFO  Http11NioProtocol      - Starting ProtocolHandler ["http-nio-8091"]
25-06-21.17:57:28.928 [main            ] INFO  TomcatWebServer        - Tomcat started on port(s): 8091 (http) with context path ''
25-06-21.17:57:28.935 [main            ] INFO  Application            - Started Application in 1.575 seconds (JVM running for 3.285)
```

- 启动程序，会看到 arex-agent.jar 的加载，这代表是正确✅的，可以上报服务信息。

### 6. 流量录制

```java
@Slf4j
@RestController
@RequestMapping("/api/traffic")
public class TrafficController {

    @GetMapping("/query_user_age")
    public Integer queryUserAge(@RequestParam("userId") String userId) {
        log.info("请求参数:{}", userId);
//        return getCache();
        return getDB();
    }

    // 模拟从缓存获取数据
    public Integer getCache() {
        return 20;
    }

    // 模拟从数据库获取数据
    public Integer getDB() {
        return 21;
    }

}
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-traffic-agent-05.png" width="600px">
</div>

- 你可以使用工程下，test-api-curl.sh 脚本访问接口，也可以其他方式请求接口。
- 当你请求完成接口后，就可以去arex控制台检查上报的信息和流量录制的数据了。

### 7. 流量回放

#### 7.1 内网穿透

流量回放是从云服务器调用本地，但本地没有对外的公网IP，所以需要内网穿透，让云服务器可以调用。这里的内网穿透工具很多，可以选择；[natapp](https://natapp.cn/)、[cpolar.com](https://www.cpolar.com/) 等。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-traffic-agent-06.png" width="800px">
</div>

```java
Powered By NATAPP       Please visit https://natapp.cn                                                                                                                                     (Ctrl+C to Quit)
                                                                                                                                                                                                           
Tunnel Status           Online                                                                                                                                                                             
  
Version                 2.3.9 (New Version 2.4.0)     
  
Forwarding              http://xfg-studio.natapp1.cc -> 127.0.0.1:8091                                                                                                                                     

Web Interface           Disabled                                                                                                                                                                           
                                                                                     
Total Connections       0                                                                                                                                        
```

- 启动后，会把本地的服务:端口，暴漏出去，通过 http 域名地址访问。
- 注意，在natapp配置你的应用端口，如 8091，如果是其他就修改下。

#### 7.2 回放配置 - 正确回放

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-traffic-agent-07.png" width="800px">
</div>

- 如图，填写回放配置信息。
- 这次我们是正确回放，也就是本地程序没有任何变更。配置后点OK。
- 点击后，可以在我们本地服务看到回调的日志，但还要稍微等待一会 arex 平台，他要比对回放结果。完成后，Passed 表示回放通过。

#### 7.2 回放配置 - 异常回放

首先，我们要修改下代码。

```java
@RestController
@RequestMapping("/api/traffic")
public class TrafficController {

    @GetMapping("/query_user_age")
    public Integer queryUserAge(@RequestParam("userId") String userId) {
        log.info("请求参数:{}", userId);
//        return getCache();
        return getDB();
    }

    // 模拟从缓存获取数据
    public Integer getCache() {
        return 20;
    }

    // 模拟从数据库获取数据
    public Integer getDB() {
        return 21;
    }

}
```

- 这个接口方法，如果录制流量是用的 getCache()，那么这会就修改为 getDB()，让两次结果不一样。
- 之后，要重新 install 并重新执行 `run_application_2_agent.sh` 启动项目。关闭执行 Ctrl/Command + C

配置，流量回放操作。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-traffic-agent-08.png" width="600px">
</div>

- 之后，再来一遍回放。这样就是错误的结果了。

### 8. 接口请求（Debug）

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-traffic-agent-09.png" width="800px">
</div>

- arex 工具，还支持线上请求本地接口操作。
- 也就是说，你从线上录制的流量，可以直接拿流量，调试本地接口。有了这样一个操作，哪些线上请求参数非常复杂的结构，就可以直接一键对本地进行验证了。
