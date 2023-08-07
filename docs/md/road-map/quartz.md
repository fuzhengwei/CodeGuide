---
title: Quartz & XXL-Job
lock: need
---

# Quartz、Spring-Schedule、XXL-Job 使用教程和扩展开发

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

本文的宗旨在于通过简单干净实践的方式教会读者，多种类型的任务执行组件使用案例，包括；Quartz 使用、扩展 Spring-Schedule 自动增加任务、XXL-Job 分布式任务调度。其中像 Spring-Schedule 小傅哥还添加了一些 Spring 组件开发的能力可自动扩展任务、对 XXL-Job 的配置引入了 Docker Compose 自动化安装和自动初始化 MySQL 数据库 xxl-job.sql 库表数据。这些都是为了让你在不同的场景选择合适的框架，同时也能更简单的使用这些框架。

本章节的任务调度组件会放到 DDD 的 Trigger 模块中，也就是触发器层。我们认为所有的调用行为，HTTP、RPC、MQ、任务，都是一个触发的入口，所以对于任务调度也放到这一层使用。

本文涉及的工程：

- xfg-dev-tech-quartz：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-quartz](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-quartz)
- xxl-job-docker.compose.yml 安装：[xxl-job-docker.compose.yml](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-quartz/-/blob/master/docs/xxl-job/xxl-job-docker-compose.yml) - `xxl-job 已提供了最简化安装自动导入库表操作`

## 一、案例背景

任务调度是一个非常重要的功能组件，常作用于；定时清理数据 - 冷数据迁移、活动状态扫描 - 过期活动关闭、消息发送补偿 - MQ失败重发、支付掉单补偿 - 支付幂等重试，等各类场景都会用到任务调度组件。它可以帮我们执行确定规则的业务或功能流程。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-quartz-01.png" width="650px">
</div>

- 以整个 DDD 分层架构中，以触发器层为入口编写任务调度方法。任务的实现方式有多种，如果你的场景较为简单，则使用 Spring 或者 Quartz 提供的任务实现方式即可。如果你的场景较为复杂，需要分布式任务管理，那么最好配置一套如 XXL-Job 这样的分布式任务调度组件来使用。
- 所有的触发器中的任务，都只是固定时间频次下的执行入口，最终需要调用领域层所提供的方法完成具体的业务逻辑。如果你使用 DDD 分层有 case/application 防腐处理，则会调用这一编排层，而不是 domain 领域层。

## 二、任务模型

当你的微服务应用是一组较小的模型结构时，其实任务与服务结合在一起即可，让它与自己的领域绑定。但如果微服务的体量很大，那么这组微服务所对应的任务也会较多，同时需要一些分布式的能力，让调度的算力可以更快更好的运用起来。

所以一般这个时候就需要引入把任务单独拆分出一个微服务系统，一般可以叫做 xxx-worker 系统，他们就是专门处理任务的一个个执行器。把这些执行器注册到任务调度中心，由任务调度中心统一管理各项任务的执行。这样如果有一个任务在一个算力执行器上失败或者说执行器宕机了，那么可以把任务迁移到其他算力执行器上执行。这就是分布式的好处。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-quartz-02.png" width="550px">
</div>

- 如图，就是分布式架构下。执行器系统被任务调度中心管理，调用微服务提供的接口，完成对微服务接口的调用。
- 一般分布式引用的微服务接口，也都是 RPC 接口，这样就已经具备了负载能力。
- 任务调度与 MQ 消息是一组非常常用的技术栈组合，MQ 失败的消息，经常是由任务扫描补偿，继续发送MQ消息，驱动流程的执行。

## 三、环境安装

本案例所需安装的环境主要是 XXL-Job 的一套 MySQL 库和 XXL-Job 应用以及对应的库表初始化。为了让大家使用起来更加简单，小傅哥这里提供了一套 compose.yml 支持 AMD 和 ARM 架构使用。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-quartz-03.png" width="450px">
</div>

- 在此位置找到执行文件，如果你本机已经安装过 [Docker](https://bugstack.cn/md/road-map/docker.html) 那么在 IntelliJ IDEA 中直接执行即可。

### 1. 执行 compose.yml

文件：[docs/xxl-job/xxl-job-docker-compose.yml](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-quartz/-/blob/master/docs/xxl-job/xxl-job-docker-compose.yml)

```yml
# 命令执行 docker-compose up -d
version: '3.9'
services:
  # http://127.0.0.1:9090/xxl-job-admin admin/123456 - 安装后稍等会访问即可
  # 官网镜像为 xuxueli/xxl-job-admin 但不支持ARM架构【需要自己打包】，所以找了一个 kuschzzp/xxl-job-aarch64:2.4.0 镜像支持 AMD/ARM
  xxl-job-admin:
    image: kuschzzp/xxl-job-aarch64:2.4.0
    container_name: xxl-job-admin
    restart: always
    depends_on:
      - mysql
    ports:
      - "9090:9090"
    links:
      - mysql
    volumes:
      - ./data/logs:/data/applogs
      - ./data/xxl-job/:/xxl-job
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/xxl_job?serverTimezone=UTC&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Shanghai
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=123456
      - SERVER_PORT=9090

  # MySQL 8.0.32 支持 AMD/ARM
  mysql:
    image: mysql:8.0.32
    container_name: mysql
    command: --default-authentication-plugin=mysql_native_password
    restart: always
    environment:
      TZ: Asia/Shanghai
      # MYSQL_ALLOW_EMPTY_PASSWORD: 'yes' 可配置无密码，注意配置 SPRING_DATASOURCE_PASSWORD=
      MYSQL_ROOT_PASSWORD: 123456
      MYSQL_USER: xfg
      MYSQL_PASSWORD: 123456
    depends_on:
      - mysql-job-dbdata
    ports:
      - "13306:3306" # 如果你无端口占用，可以直接使用 3306
    volumes:
      - ./sql:/docker-entrypoint-initdb.d
    volumes_from:
      - mysql-job-dbdata

  # 自动加载数据
  mysql-job-dbdata:
    image: alpine:3.18.2
    container_name: mysql-job-dbdata
    volumes:
      - /var/lib/mysql
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-quartz-04.png" width="850px">
</div>

- 在 IDEA 中打开 rocketmq-docker-compose-mac-amd-arm.yml 你会看到一个绿色的按钮在左侧侧边栏，点击即可安装。或者你也可以使用命令安装：`# /usr/local/bin/docker-compose -f /docs/xxl-job/xxl-job-docker-compose.yml up -d` - 比较适合在云服务器上执行。
- 在 compose 中提供了 xxl-job 所需要的库的依赖安装，以及自动加载文件下的初始化库表数据。这个库表数据来自于 xxl-job sql：[https://gitee.com/xuxueli0323/xxl-job/blob/master/doc/db/tables_xxl_job.sql](https://gitee.com/xuxueli0323/xxl-job/blob/master/doc/db/tables_xxl_job.sql) - `这里小傅哥把 SQL 文件下载到了本地，用于初始化安装使用`
- 标签；`depends_on` - 依赖于谁先安装、`MYSQL_ALLOW_EMPTY_PASSWORD: 'yes'` - 可以设置MySQL无密码安装、`mysql-job-dbdata` - 一个启动安装数据库初始化脚本的镜像。并且需要在 MySQL 安装时使用 `volumes_from` 标签引入。

### 2. 访问 xxl-job

**地址**：[http://127.0.0.1:9090/xxl-job-admin](http://127.0.0.1:9090/xxl-job-admin)  - admin/123456 - 安装后稍等启动完成，就可以访问啦。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-quartz-05.png" width="850px">
</div>

- 默认的账号 admin 密码 123456

### 3. 执行器管理

执行器的作用，就是让 xxl-job-admin 这个任务调度系统，调用注册上来的执行器完成任务的执行。客户端需要配置好这里的执行器名称才能注册上来。你可以根据自己的需要新增新的执行器，也可以在测试的时候使用默认的这个执行器名称。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-quartz-06.png" width="850px">
</div>

- 本地服务启动后，会注册进来一个执行器的地址，OnLine 机器地址会显示。

### 4. 任务配置

任务的作用，就是执行器下具体的执行方法，按照配置的时间下发到任务中执行。

```java
@Slf4j
@Component
public class XXLJob {

    @XxlJob("demoJobHandler")
    public void doJob() {
        // 可以在任务中，调用一些业务方法逻辑的实现，如定时扫描超时未支付订单为关单处理，恢复库存
        log.info("执行任务 - XXL-Job - 01");
    }

}
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-quartz-07.png" width="950px">
</div>

- 一个执行器下管理的任务一般会有很多，所以你在测试的时候也可以尝试新增一些任务来测试。

## 四、工程实现

### 1. 工程结构

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-quartz-08.png" width="950px">
</div>

- 首先，trigger 触发器模块下有3类任务，分别是 Quartz、Schedule 和 XXL-Job 分布式任务。XXL-Job 所需的配置会多一些，需要 application-dev.yml 配置 xxl-job 参数，之后配置 Config 启动任务。最后是 XXL-Job 使用任务。
- QuartzJob 就是一个直接使用的案例，但像 XXL-Job 也是基于 Quartz 扩展的，小傅哥也做了一个分布式任务调度的中间件，如果感兴趣也可以学习。[https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%2015%20%E7%AB%A0%20%E5%88%86%E5%B8%83%E5%BC%8F%E4%BB%BB%E5%8A%A1%E8%B0%83%E5%BA%A6.html](https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%2015%20%E7%AB%A0%20%E5%88%86%E5%B8%83%E5%BC%8F%E4%BB%BB%E5%8A%A1%E8%B0%83%E5%BA%A6.html)
- ScheduleJob 是基于 Spring 提供的 Schedule 进行的扩展，可以根据自定义注解添加任务和自动启动。这个方式也是非常好用的。

### 2. 配置文件

**引入POM**

```pom
<!-- Quartz -->
<dependency>
    <groupId>org.quartz-scheduler</groupId>
    <artifactId>quartz</artifactId>
    <version>2.3.2</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-quartz -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-quartz</artifactId>
    <version>3.1.2</version>
</dependency>

<!-- https://mvnrepository.com/artifact/com.xuxueli/xxl-job-core -->
<dependency>
    <groupId>com.xuxueli</groupId>
    <artifactId>xxl-job-core</artifactId>
    <version>2.4.0</version>
</dependency>
```

- 分别包括；Quartz、XXL-Job 两个组件

**添加配置**

```yml
# xxl-job https://www.xuxueli.com/xxl-job/#%E6%AD%A5%E9%AA%A4%E4%B8%80%EF%BC%9A%E8%B0%83%E5%BA%A6%E4%B8%AD%E5%BF%83%E9%85%8D%E7%BD%AE%EF%BC%9A
xxl:
  job:
    # 验证信息 官网Bug https://github.com/xuxueli/xxl-job/issues/1951
    accessToken: default_token
    # 注册地址
    admin:
      addresses: http://localhost:9090/xxl-job-admin
    # 注册执行器
    executor:
      #  执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。
      address:
      appname: xxl-job-executor-sample
      # 执行器IP 配置为本机IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯实用；地址信息用于 "执行器注册" 和 "调度中心请求并触发任务"；
      ip:
      # 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；
      port: 9999
      # 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；
      logpath: ./data/applogs/xxl-job/jobhandler
      # 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；
      logretentiondays: 30
```

xxl-job 有一些必要的配置信息
- accessToken，默认是一个 default_token - 它的官网也有 issue 提到这个bug，如果未配置，应该为空。[https://github.com/xuxueli/xxl-job/issues/1951](https://github.com/xuxueli/xxl-job/issues/1951)
- addresses，是一个注册地址，也就是我们访问 xxl-job 的地址
- appname，我们这里使用了官网默认提供的执行器的名称 xxl-job-executor-sample 你可以新增或者修改。
- ip、port 意思是你把本地的执行器注册到调度中心，如果你的 XXL-Job 部署到云服务器，而本地启动服务的时候，你是可以注册到服务端的，但调度中心没法调用到你本地的服务，因为你本地没有公网IP。这个时候你可以使用 natapp 做映射内网穿透进行测试。

### 3. 任务配置

#### 3.1 Quartz 任务

**源码**：`cn.bugstack.xfg.dev.tech.job.QuartzJob`

```java
@Slf4j
@Component()
public class QuartzJob {

    @Scheduled(cron = "0/3 * * * * ?")
    public void execute01() {
        // 可以在任务中，调用一些业务方法逻辑的实现，如定时扫描超时未支付订单为关单处理，恢复库存
        log.info("执行任务 - Quartz - 01");
    }

    @Scheduled(cron = "0/3 * * * * ?")
    public void execute02() {
        // 可以在任务中，调用一些业务方法逻辑的实现，如定时扫描超时未支付订单为关单处理，恢复库存
        log.info("执行任务 - Quartz - 02");
    }

}
```

- Quartz 支持在一个类中，配置多个任务，每个任务方法都可以配置自己执行策略。
- 此类方式非常适合一些不需要统一任务调度的简单场景使用。

#### 3.2 Spring-Schedule 扩展任务

**配置任务注册器** - 在 app config 下

```java
@Slf4j
@Configuration
@EnableScheduling
public class JobRegistrarAutoConfig implements SchedulingConfigurer {

    private final ApplicationContext applicationContext;

    public JobRegistrarAutoConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        Map<String, ExtScheduleJob> jobBeanMap = applicationContext.getBeansOfType(ExtScheduleJob.class);
        Collection<ExtScheduleJob> jobBeans = jobBeanMap.values();
        for (ExtScheduleJob job : jobBeans) {
            ExtScheduleJobConfig extScheduleJobConfig = AnnotationUtils.findAnnotation(job.getClass(), ExtScheduleJobConfig.class);
            if (extScheduleJobConfig == null || !extScheduleJobConfig.state()) continue;

            log.info("启动任务 {} {}", extScheduleJobConfig.jobName(), extScheduleJobConfig.cronExpression());
            taskRegistrar.addCronTask(job, extScheduleJobConfig.cronExpression());
        }
    }

}
```

- JobRegistrarAutoConfig 实现了 SchedulingConfigurer 的类，可以自己自动化的根据所有实现了 ExtScheduleJob 类进行任务扩展添加。
- 这是一种扩展方式，有了这样的扩展方式，如果你是做同类的任务需求，只是配置不同的话，那么还可以基于 yml 配置，来创建出不同的代理任务。

### 3.3 XXL-Job

**源码**：`cn.bugstack.xfg.dev.tech.job.XXLJob`

```java
@Slf4j
@Component
public class XXLJob {

    @XxlJob("demoJobHandler")
    public void doJob() {
        // 可以在任务中，调用一些业务方法逻辑的实现，如定时扫描超时未支付订单为关单处理，恢复库存
        log.info("执行任务 - XXL-Job - 01");
    }

}
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-quartz-09.png" width="950px">
</div>

- 注意到这里的 demoJobHandler 就是在 [http://127.0.0.1:9090/xxl-job-admin/jobinfo](http://127.0.0.1:9090/xxl-job-admin/jobinfo) 配置的执行方法名称。
- 你用配置了什么注解的名称，就新增对应的名称即可。

## 五、工程测试

- QuartzJob - 启动工程即可运行
- ScheduleJob - 注解中，state = false 是不运行，否则直接运行。
- XXLJob - 需要由 [http://127.0.0.1:9090/xxl-job-admin/jobinfo](http://127.0.0.1:9090/xxl-job-admin/jobinfo) 进行测试或者启动任务。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-quartz-10.png" width="950px">
</div>

```java
23-08-05.14:19:42.003 [pool-2-thread-1 ] INFO  QuartzJob              - 执行任务 - Quartz - 01
23-08-05.14:19:42.003 [pool-2-thread-1 ] INFO  ScheduleJob            - 执行任务 - Schedule - 01
23-08-05.14:19:42.060 [xxl-job, JobThread-1-1691216327906] INFO  XXLJob                 - 执行任务 - XXL-Job - 01
23-08-05.14:19:45.003 [pool-2-thread-1 ] INFO  QuartzJob              - 执行任务 - Quartz - 02
23-08-05.14:19:45.003 [pool-2-thread-1 ] INFO  QuartzJob              - 执行任务 - Quartz - 01
23-08-05.14:19:45.004 [pool-2-thread-1 ] INFO  ScheduleJob            - 执行任务 - Schedule - 01
23-08-05.14:19:45.041 [xxl-job, JobThread-1-1691216327906] INFO  XXLJob                 - 执行任务 - XXL-Job - 01
```

- 注意编辑任务的执行时间，`0/3 * * * * ?` 这样才能当下执行。另外如果你要测试的话，可以点**执行一次**。
- 现在是启动了多个测试任务，所以测试中可以看到各类任务的打印。读者在做测试的时候，可以适当关闭，方便学习。

## 六、扩展学习 JobRunr

官网：[jobrunr](https://github.com/jobrunr/jobrunr) - `一种在 Java 中执行后台处理的巧妙简单的方法。由持久存储支持。开放并免费用于商业用途。`

### 1. 安装部署

```yml
version: '3'
services:
  jobrunr:
    image: jobrunr/server:latest
    ports:
      - 8000:8000
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/jobrunrdb
      - SPRING_DATASOURCE_USERNAME=jobrunr
      - SPRING_DATASOURCE_PASSWORD=jobrunr
    depends_on:
      - postgres
    networks:
      - jobrunr-network

  postgres:
    image: postgres:latest
    environment:
      - POSTGRES_USER=jobrunr
      - POSTGRES_PASSWORD=jobrunr
      - POSTGRES_DB=jobrunrdb
    volumes:
      - ./pgdata:/var/lib/postgresql/data
    networks:
      - jobrunr-network

networks:
  jobrunr-network:
```

## 2. 使用案例

```java
// 即发即忘任务
BackgroundJob.enqueue(() -> System.out.println("Simple!"));

// 延迟的任务
BackgroundJob.schedule(Instant.now().plusHours(5), () -> System.out.println("Reliable!"));

// 重复的任务
BackgroundJob.scheduleRecurrently("my-recurring-job", Cron.daily(), () -> service.doWork());

// 配置的任务
@Component
public class MyJobService {

    private final JobScheduler jobScheduler;

    @Autowired
    public MyJobService(JobScheduler jobScheduler) {
        this.jobScheduler = jobScheduler;
    }

    public void scheduleJob() {
        jobScheduler.<MyJob>enqueue(job -> job
                .setJobDetails(MyJob.class)
                .withName("My Job")
                .withArgument("arg1", "value1")
                .withArgument("arg2", "value2")
        );
    }

    @Job(name = "My Job")
    public void processJob(String arg1, String arg2) {
        // 处理作业的逻辑
        System.out.println("Processing job with arguments: " + arg1 + ", " + arg2);
    }
}
```
