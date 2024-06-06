---
title: apisix
lock: need
---

# Apisix 云原生架构的开源 API 网关

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

`Higress`、`SpringCloud Gateway`，再到今天这套 `Apisix` 小傅哥就把市面上非常常用的3套 API 网关服务就全部都展示给大家了。其实所有的 API 网关都有一个共同的目的，就是做统一的 API 管理，包括；协议转换、负载均衡、动态路由、灰度发布、服务熔断、统一认证等。

像是这些组件化的 API 网关，使用起来其实都挺容易的，就有点像是使用了一个可视化的 Nginx 一样。但这些 API 看着简单，但有时候没有一个不错的资料对照搭建、配置和使用，也是挺难下手的。所以小傅哥把这些内容，成体系的展示给你，让你可以对照学习使用。

- 官网：[https://apisix.apache.org/zh/](https://apisix.apache.org/zh/)
- 部署：[https://github.com/apache/apisix-docker](https://github.com/apache/apisix-docker) - 官网提供了 Docker 脚本，但也有一些注意事项才能使用
- 脚本：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-apisix](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-apisix) - 可执行使用的部署脚本

## 一、APISIX 介绍

这款老6API，是阿帕奇下的开源项目。最初由 [api7.ai](https://api7.ai/apisix) 创建，于 2019 年开源并捐献给 Apache 软件基金会，使服务全球一半 API 请求的愿景成为可能。自那时起，API7.ai一直投入最优秀的人才和资源来支持 Apache APISIX 及其社区，该社区由来自世界各地的数千名贡献者和用户加入。

Apache APISIX 是一个动态、实时、高性能的云原生 API 网关，提供了负载均衡、动态上游、灰度发布、服务熔断、身份认证、可观测性等丰富的流量管理功能。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-01.png" width="750px">
</div>

为什么选择 Apache APISIX?

- 简单易用的 Dashboard：Apache APISIX Dashboard 可以让用户尽可能直观、便捷地通过可视化界面操作 Apache APISIX。作为 Apache 软件基金会中持续迭代的开源项目，欢迎你随时提出新的想法。
- 友好的用户体验：Apache APISIX Dashboard 极大地满足了用户需求，不仅提供了清晰的组织架构以适配二次开发，而且可以借助插件编排能力释放想象力！
- 可视化配置：拒绝重复造轮子。借助 Apache APISIX 内置插件，可以在极短时间内创建灵活、可靠、高性能的网关。无需编写代码，只需在编辑器中拖拽插件、配置条件，便可通过可视化的方式打造专属的 API 管理系统。
- 极致的性能体验：Apache APISIX 基于 Radixtree Route 和 etcd 提供路由极速匹配与配置快速同步的能力。从路由到插件，所有的设计和实现都是为了极速性能和超低延迟。
- 阻拦恶意程序：Apache APISIX 提供了多款身份认证与接口验证的插件，我们将稳定、安全放在首位。

## 二、环境部署

- 云服务器：2c2g 最低，我是用的 2c8g 体验的。[https://yun.xfg.plus](https://yun.xfg.plus/) - 价格实惠。
- 基础环境：Docker、Portainer、Git 【在小傅哥的 bugstack.cn 路书中都有讲解安装和使用】

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-02.png" width="950px">
</div>

### 1. 脚本配置

官网中提供了 docker 的安装脚本，但不能直接使用，需要如图做下复制操作。小傅哥已经把这部分复制的内容，到`脚本`工程里了。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-03.png" width="450px">
</div>

- 你可以知道有这么一个地方，将来如果版本更新了，也可以做下配置操作。

### 2. 可用脚本 - 准备好的

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-04.png" width="650px">
</div>

- 这个是小傅哥帮你准备好的脚本，可以直接安装使用。
- 这里涉及了 docker、portainer 的使用，在《[bugstack.cn](https://bugstack.cn) 路书》中，已经完整配置提供，可以查看。

## 三、网关配置

- 网关的登录密码是在 dev-ops/apisix-dashboard/conf.yaml 中配置的。默认的是 `admin/admin`
- 另外注意 conf.yml 中 etcd:2379 的配置，这里从 127.0.0.1 调整为 etch 应用名称。这样能保证在同一个 docker 配置的网络下，可以直接连接使用。如果是分布式部署，则要改为具体的IP。
- 这套按照学习脚本中提供了 web1、web2 两个 nginx 的配置，可以测试使用。

### 1. 访问网关

地址：[http://127.0.0.1:9093/](http://127.0.0.1:9093/)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-05.png" width="450px">
</div>

- 访问后就可以看到左侧的目录，可以配置；服务、上游、路由，以及插件等功能。
- 路由是入口，从路由可以访问到配置的服务，服务是每个应用的访问地址，可以配置权重轮训和一致性哈希。插件主要是提供的是；身份验证、安全防护、流量控制、无服务架构、可观测性功能。

### 2. 服务配置

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-06.png" width="850px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-07.png" width="850px">
</div>

- 配置后，一直点击就可以了。

### 3. 配置路由

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-08.png" width="750px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-09.png" width="750px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-10.png" width="750px">
</div>

### 4. 插件配置

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-11.png" width="750px">
</div>

- 你可以按需测试各种插件，还可以通过编排让各个要执行的插件串联起使用。

## 四、服务验证

地址：[http://192.168.1.105:9080/api](http://192.168.1.105:9080/api)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-12.png" width="750px">
</div>
- 刷新网关接口，不停的刷新，会看到接口访问值的变化。

> 有了这个大厂网关的体验，大家就了解了一套网关是如何使用的，作用是什么啦。接下来，如果感兴趣技术的积累，想扩展下自己，也可以学习一套网关代码的实现。

## 五、网关学习

除了业务开发，小傅哥自己也是非常感兴趣于这样的网关技术组件的实现，所以在日常的工作中也积累了很多网关的设计。后来在22年做了一套轻量的网关系统，把核心的内核逻辑实现出来让大家学习。帮助了很多伙伴学习项目后找到了不错的工作。

### 1. 项目架构

![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/zTfAIs5rNXhH7lTu0RhZp5yKIeHCn8DzJNoqsbV1pdIpu0ksEGZ8E4K28RVeia93w2aaoA9GibmZzxrias9Xem7Ew/640?wx_fmt=png&from=appmsg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

整个API网关设计核心内容分为这么五块；

- `第一块`：是关于通信的协议处理，也是网关最本质的处理内容。这里需要借助 NIO 框架 Netty 处理 HTTP 请求，并进行协议转换泛化调用到 RPC 服务返回数据信息。
- `第二块`：是关于注册中心，这里需要把网关通信系统当做一个算力，每部署一个网关服务，都需要向注册中心注册一个算力。而注册中心还需要接收 RPC 接口的注册，这部分可以是基于 SDK 自动扫描注册也可以是人工介入管理。当 RPC 注册完成后，会被注册中心经过AHP权重计算分配到一组网关算力上进行使用。
- `第三块`：是关于路由服务，每一个注册上来的Netty通信服务，都会与他对应提供的分组网关相关联，例如：wg/(a/b/c)/user/... a/b/c 需要匹配到 Nginx 路由配置上，以确保不同的接口调用请求到对应的 Netty 服务上。PS：如果对应错误或者为启动，可能会发生类似B站事故。
- `第四块`：责任链下插件模块的调用，鉴权、授信、熔断、降级、限流、切量等，这些服务虽然不算是网关的定义下的内容，但作为共性通用的服务，它们通常也是被放到网关层统一设计实现和使用的。
- `第五块`：管理后台，作为一个网关项目少不了一个与之对应的管理后台，用户接口的注册维护、mock测试、日志查询、流量整形、网关管理等服务。

### 2. 项目流程

API网关除了基础的功能模块以外，还需要重点考虑负载均衡的设计，只有这样才能被横向扩展支撑高并发的吞吐量。所在负载设计这块，小傅哥也是花了不少的时间来构建，让负载可以被动态的管理。

![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/zTfAIs5rNXhH7lTu0RhZp5yKIeHCn8DzHhJic5vYjqhDyahibcduMOVEZK5n6hf9FdB1hV4Wf5Q0PicqvscsjE6yA/640?wx_fmt=png&from=appmsg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

这是一整套API网关的核心通信模型结构图，以API网关算力的多套服务注册到网关中心开始，拉取RPC应用接口并完成映射HTTP调用操作。最终允许用户通过 Nginx 访问和路径重写的负载均衡管理，调用到具体的网关算力中执行协议解析和RPC接口的泛化调用并最终返回结果数据。—— 就这套架构设计学习完，就够你晋升到P7岗了！

### 3. 项目目录

第 1 部分 - 通信组件

- 第1章：HTTP请求会话协议处理
- 第2章：代理RPC泛化调用
- 第3章：分治处理会话流程
- 第4章：将连接(RPC\HTTP\其他)抽象为数据源
- 第5章：HTTP请求参数解析
- 第6章：引入执行器封装服务调用
- 第7章：权限认证组件(Shiro+Jwt)
- 第8章：网关会话鉴权处理
- 第16章：网络通信配置提取

第 2 部分 - 注册中心

- 第9章：网关注册中心服务初始创建
- 第10章：网关注册中心库表结构设计
- 第11章：网关注册算力节点领域服务实现
- 第12章：网关注册服务接口领域服务实现
- 第14章：网关映射聚合信息查询实现

第 3 部分 - 服务发现

- 第13章：服务发现组件搭建和注册网关连接
- 第15章：服务配置拉取和组件使用验证
- 第17章：核心通信组件管理和处理服务映射
- 第18章：容器关闭监听和异常管理
- 第22章：订阅服务注册消息驱动网关映射
- 第25章：网关Nginx负载模型配置
- 第26章：动态刷新网关Nginx负载均衡配置
- 第27章：实现网关算力节点动态负载功能

第 4 部分 - 镜像文件

- 第19章：网关引擎打包镜像部署

第 5 部分 - 服务注册

- 第20章：服务注册组件搭建采集接口信息
- 第21章：应用服务接口注册到注册中心

第 6 部分 - 运营后台

- 第23章：网关运营管理后台框架搭建
- 第24章：前后端分离应用的跨域接口调用

第 7 部分 - 扩展功能

- 第28章：网关组件工程模块合并
- 第29章：功能完善(算力关联、接口上报、调用反馈)

> 走编程开发这条路，就不能只是做业务，也要学习一些核心组件的实现方案。公司是公司，个人是个人。多学一些，让自己的全体系技术栈积累更加完善，也更有竞争力。

## 六、加入学习

注意📢，本项目也只是【星球：码农会锁】众多项目中的1个，其他的项目还包括：大营销、OpenAI 应用、API网关、Lottery抽奖、IM通信、SpringBoot Starter 组件开发、IDEA Plugin 插件开发等，并还有开源项目学习。`🌶加入后，从星球课程入口进入，组件项目 - 支付SDK设计和开发`

如果大家希望通过做有价值的编程项目，提高自己的编程思维和编码能力，可以加入小傅哥的【星球：码农会锁】。加入后解锁🔓所有往期项目，还可以学习后续新开发的项目。

> 这样成体系的全量项目学习，放在一些平台售卖，至少都要上千块。但小傅哥的星球，只需要100多，就可以获得大厂架构师对你手把手教学！

![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/zTfAIs5rNXhH7lTu0RhZp5yKIeHCn8DzQKCw3sWCC26XzqymklVB6O9ca40jx6HnbbIdQ8wstNTYBL64SW61Og/640?wx_fmt=png&from=appmsg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)
