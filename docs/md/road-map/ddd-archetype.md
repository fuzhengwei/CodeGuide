---
title: DDD 脚手架
lock: need
---

# DDD 工程脚手架 + 一键安装分布式技术栈环境！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=278113187&bvid=BV1Gw411u7Dz&cid=1322192636&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

大家好，我是技术UP主小傅哥。

写了那么多案例工程，开发了那么多技术项目。那小傅哥做的这些案例和项目是每一个都要手动创建一遍吗？🤔 如果不是一个个都手动创建，那么有什么提效工具吗？

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-01.gif?raw=true" width="150px">
</div>

不用，根本不用。因为小傅哥有一套**神器**！

对于 DDD 项目的多模块化工程搭建，其实真的是一个挺耗时的工作，尤其是分布式工程的搭建更是耗时。不过工程模块的创建还不算太耗时，主要耗时在各个分布式组件的整合使用上，包括；MySQL、Redis、RocketMQ、Dubbo、shardingjdbc、XXL-JOB 等一堆技术框架。如果你做过这样的事情，一定会被他们的使用和各类版本的配合，折腾过服服帖帖。

一次工程和环境搭建，没有个6~8小时都搞不完！但今天我想你动动手指就搞定这些！

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-02.png?raw=true" width="600px">
</div>

那么，接下来小傅哥就介绍下这套带有配套环境安装的工程脚手架，让小伙伴可以熟悉使用，快速搭建自己的学习工程。

>文末有相关工程脚手架的获取地址，还有对应工程脚手架的学习项目。

## 一、效果展示

这是一套完整的工程级框架搭建标准，提供工程的脚手架搭建以及对应的完整环境初始化。可以让开发项目的伙伴，快速🔜完成基本工作，减少6~8小时的折腾。先来展示下整体教授的内容。

### 1. 工程框架

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-11.png?raw=true" width="750px">
</div>

### 2. 环境展示

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-12.png?raw=true" width="750px">
</div>

### 3. Redis 管理

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-13.png?raw=true" width="750px">
</div>

### 4. MySql 管理

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-14.png?raw=true" width="750px">
</div>

### 5. MQ 管理

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-15.png?raw=true" width="750px">
</div>

### 6. JOB 管理

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-16.png?raw=true" width="750px">
</div>

## 二、框架介绍

小傅哥这里提供了2套工程脚手架，一套轻量版的无任何分布式技术栈的使用，另外一套是标准版的全系使用分布式技术栈。因为很多小型项目并不需要依赖太多的分布式技术栈，而且轻量化的设计开发更能有效的提高开发效率。所以，读者可以按需选择你需要的脚手架进行学习、验证和生产。

### 1. 轻量版

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-03.png?raw=true" width="750px">
</div>

轻量版 DDD 框架，主要以提供 HTTP 服务为主。开发效率高，适合中小业务场景。
- 缓存，使用 Guava。如果有特定场景的情况下，可以补充 Redis 使用。
- 任务，使用 Quartz。
- 消息，使用 Spring/Guava 替代。

### 2. 标准版

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-04.png?raw=true" width="750px">
</div>

标准版 DDD 架构，以解决中大型场景业务开发为目的，综合使用分布式技术栈进行项目构建。
- Dubbo 提供 RPC 接口，Nacos 作为注册中心使用。
- Redis 提供缓存、加锁、数据处理服务。
- SharedingJdbc 提供分库分表服务。
- XXL-JOB 提供分布式任务调度服务。
- RocketMQ 提供异步消息服务。

## 三、使用说明

这套脚手架使用了 `maven-archetype-plugin` 使用命令(archetype:create-from-project)进行工程的脚手架创建，创建后在进行一些内容的修改，最终完成脚手架的模板。

读者在使用这套脚手架的时候，可以在本地下载工程代码后，进入工程下的 `scaffold-lite/std` 模块执行 README.md 中 `mvn clean install` 脚本进行安装。安装后即可在使用 IntelliJ IDEA 创建工程时候，选择 Maven 创建，添加本地仓库地址使用。—— 下面👇🏻会介绍具体操作步骤。

### 1. 工程结构

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-05.png?raw=true" width="750px">
</div>

如图，就是两套 DDD 脚手架工程，每一套工程下，都有一个 scaffold 模块。这部分是对当下对应工程的脚手架模块。另外 docs 下有 dev-ops 文件夹，是环境安装包，直接执行 docker-compose.yml 即可一次安装完全部环境。

读者，在使用的时候，也可以先不看 scaffold 模块。因为除了这个模块外，其他的就是整个 DDD 工程，作为你最开始熟悉使用。熟悉后在进入”黑圈“ README.md 中执行 mvc clean install 这样就能在本地 maven 仓库安装上脚手架了。

### 2. 框架安装

#### 2.1 安装

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-06.png?raw=true" width="750px">
</div>

- 进入工程脚手架模块下的 README.md 点击执行 mvn clean install

#### 2.2 配置

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-07.png?raw=true" width="750px">
</div>

- 首先是点击创建工厂，之后进入 Maven Archetype 下。
- 之后你需要自己配置好 maven 工程脚手架地址，有可能默认的路径地址，不生效。

#### 2.3 使用

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-08.png?raw=true" width="750px">
</div>

- 进入 Maven Archetype 如图方式创建工程。

#### 2.4 效果

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-09.png?raw=true" width="750px">
</div>

- 自动生成工程，之后你就可以通过 docs 文件件下的 docker-compose.yml 安装环境并使用了。**注意本机已安装了 Docker**

### 3. 环境安装

文件：`docs/dev-ops/docker-compose.yml`

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-10.png?raw=true" width="950px">
</div>

- Mac 电脑安装 Docker 后，可以直接点击绿色的小按钮，一步安装所有配置好的环境。
- 云服务器，可以通过 `docker-compose -f docker-compose.yml up -d`进行安装。
- 资源访问；

  - 配置中心nacos：http://127.0.0.1:8848/nacos - 【账号：nacos 密码：nacos】
  - 消息rocketmq：http://127.0.0.1:8080/#/ - 【账号：admin 密码：admin】
  - 任务调度xxl-job-admin：http://127.0.0.1:9090/xxl-job-admin/ - 【账号：admin 密码：123456】
  - 缓存redis：http://127.0.0.1:8081/
  - 数据库MySQL：http://127.0.0.1:8899/ - 登录数据库信息，在线直接管理数据库

## 四、项目学习

有了工程脚手架，最好再有一套对应的实战项目学习。这样加起来锻炼，看看各个项目的模块都是如何调度的，才能更好的学习这套工程。而小傅哥的 **星球：码农会锁** 就有这样的项目锻炼，包括：OpenAi 应用项目、Lottery 抽奖项目、Api网关项目、IM通信项目、SpringBoot Starter 组件项目等。

>这样一套项目，放在一些平台售卖，至少都是几百块。但小傅哥的星球，只需要100多，就可以获得几千元的学习项目！

- 脚手架(轻量版)：[https://gitcode.net/KnowledgePlanet/road-map/xfg-frame-archetype-lite](https://gitcode.net/KnowledgePlanet/road-map/xfg-frame-archetype-lite)
- 脚手架(标准版)：[https://gitcode.net/KnowledgePlanet/road-map/xfg-frame-archetype-std](https://gitcode.net/KnowledgePlanet/road-map/xfg-frame-archetype-std)

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-youhuiquan.png?raw=true" width="300px">
</div>

在星球的实战项目中，都是以这样的企业级标准进行架构设计和落地，学习这样的项目就是学习公司的项目。—— 不做 CRUD 小项目，要做真的深度积累！

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-frame-archetype-17.png?raw=true" width="950px">
</div>
