---
title: API 网关 - 媲美美团这套Shepherd网关架构！
lock: no
---

# API 网关 - 媲美美团这套Shepherd网关架构！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

我说："很多互联网大厂，很少基于 SpringMVC 模块对外提供 WEB 服务的 HTTP 接口！" **一下炸窝了，你说，哪个厂不用，你说。还，还不用 SpringMVC 我天天用！** 哈哈哈，好在我最近阅读到了美团的这篇技术文章[《百亿规模API网关服务Shepherd的设计与实现》](https://tech.meituan.com/2021/05/20/shepherd-api-gateway.html) 

>他说：在没有Shepherd API网关之前，美团业务研发人员如果要将内部服务输出为对外的HTTP API接口。通常要搭建一个Web应用，用于完成基础的鉴权、限流、监控日志、参数校验、协议转换等工作，同时需要维护代码逻辑、基础组件的升级，研发效率相对比较低。此外，每个Web应用都需要维护机器、配置、数据库等，资源利用率也非常差。

>他说：美团内部一些业务线苦于没有现成的解决方案，根据自身业务特点，研发了业务相关的API网关。放眼业界，亚马逊、阿里巴巴、腾讯等公司也都有成熟的API网关解决方案。

而`他说的`和`我说的`，是同一个事情。**并且我们所设计的API网关架构模型也都是类似的！** 类似的架构设计，并不会让我多惊讶。因为API网关所实现的目标一致，在同一目标下如果研发思考高度一致，那么就不需要太多技术认知对其。—— 所以，少和臭棋篓子下棋！

接下来，小傅哥就给大家分享下。两套API网关的架构设计，以及你该怎么学习才能掌握这些技术技能和提高这些技术认知。

## 一、技术设计与实现

就`API网关`来说，我们可以先抽象出一个最简单的模型来理解。它的核心目标是统一提供 HTTP 请求服务，也就是说你可以在不需要额外开发 WEB 应用的前提下，对外把自身的服务通过 HTTP 请求协议暴漏出去。因为在互联网大厂中，各个微服务系统的交互主要以 RPC 为主，同时为了提供带有基础功能(鉴权、监控、限流等) HTTP 服务，所以有了`API网关`服务。

<div align="center">
    <img src="https://bugstack.cn/images/article/assembly/api-gateway/api-gateway-230610-01.png?raw=true" width="750px">
</div>

这就是一套最基础的`API网关`设计模型结构，从左到右，从 HTTP 请求到协议转换处理，调用到具体的 RPC 服务。而 RPC 服务的接口变化由 SDK 上报到注册中心，注册中心再通知给协议转换服务。有了这样一个基础认知以后，我们在来讲解几个重要的核心模块设计和实现，包括；整体架构、注册中心、服务发现、协议转换。

### 1. 整体架构

这里有2张API网关架构图，一张是美团技术团队的，一张是小傅哥设计的。

#### 1.1 API网关架构图-美团

<div align="center">
    <img src="https://bugstack.cn/images/article/assembly/api-gateway/api-gateway-230610-02.png?raw=true" width="650px">
</div>

Shepherd API 网关的数据面也就是 Shepherd 服务端。一次完整的API请求，可能是从移动应用、Web应用，合作伙伴或内部系统发起，经过Nginx负载均衡系统后，到达服务端。服务端集成了一系列的基础功能组件和业务自定义组件，通过泛化调用请求后端RPC服务、HTTP服务、函数服务或服务编排服务，最后返回响应结果。

**注意**：美团的这张技术架构图图应该是简化的，整体架构并不会比小傅哥设计的简单。

#### 1.2 API网关架构图-小傅哥

<div align="center">
    <img src="https://bugstack.cn/images/article/assembly/api-gateway/api-gateway-0-04.png?raw=true" width="750px">
</div>

这是一整套API网关的核心通信模型结构图，以API网关算力的多套服务注册到网关中心开始，拉取RPC应用接口并完成映射HTTP调用操作。最终允许用户通过 Nginx 访问和路径重写的负载均衡管理，调用到具体的网关算力中执行协议解析和RPC接口的泛化调用并最终返回结果数据。

### 2. 注册中心

`API 网关为什么要有一个注册中心呢？`

其实这个注册中心，最核心管理就是 RPC 接口映射成一个 HTTP 请求地址，并把这个信息下发给对应的协议转发服务上进行使用。

<div align="center">
    <img src="https://bugstack.cn/images/article/assembly/api-gateway/api-gateway-230610-03.png?raw=true" width="650px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/assembly/api-gateway/api-gateway-9-01.png?raw=true" width="650px">
</div>

- 如图所示，api-gateway-core 是最核心的通信层。那么它还需要把注册的网关接口在通信核心服务中启动起来。那么怎么启动呢？
- 这个启动过程首先来自于 api-gateway-sdk 向 api-gateway-center 推送注册接口，之后在通过网关引擎 api-gateway-engin 拉取接口并在本地服务完成注册。最后再调用到网关接口时，则是通过 api-gateway-core 调用到对应的 RPC 应用中。
- 那么 api-gateway-sdk 并不是主要工程，没有它的是可以通过 api-gateway-admin 配置。所以 在整个流程中 api-gateway-center、api-gateway-core 是两个核心工程，能更好的串联流程。

### 3. 服务发现

`什么叫服务发现呢？发现谁呢？`

服务发现，发现的是用于注册到API网关注册中心的 RPC 服务，通过 SDK 配置的方式，采集到 RPC 服务中的接口信息。因为这些接口的定义如果都是手动配置到API网关注册中心，那么就会非常麻烦。所以通过 SDK 采集的方式进行自动注册，当有接口变更的时候也会及时的变更接口信息。

<div align="center">
    <img src="https://bugstack.cn/images/article/assembly/api-gateway/api-gateway-22-01.png?raw=true" width="650px">
</div>

- 在 api-gateway-center 工程中添加 Redis 发布消息模块，并提供应用服务注册后的事件通知操作。这个通知只会通知给对应的网关算力服务，不会全局通知。
- 在 api-gateway-assist 工程中开发 Redis 订阅消息模块，当收到注册中心的消息推送时，则根据系统的标识信息进行拉取服务。注意这里你可以进行细化，只把变更的信息一条条推送给网关注册，减少接口的拉取
- 在 api-gateway-sdk 工程中添加对网关注册中心接口的调用，当所有的服务注册完成后，调用接口进行通知。

### 4. 协议转换

`这是最核心的服务！`

所有的 HTTP 请求协议转发，到最终的 RPC 泛化调用，这些操作都在这一个服务中完成。而整个这一块服务的实现，其实就是一套会话模型的架构分层设计。

<div align="center">
    <img src="https://bugstack.cn/images/article/assembly/api-gateway/api-gateway-8-01.png?raw=true" width="650px">
</div>

一次网络请求经过 Netty 处理可以分为三段；消息接收、请求鉴权、消息处理。这样就由原来我们只是在接收消息后直接把消息协议转换后请求到 RPC 服务，转换为多添加二层来处理简单的消息接收和请求鉴权。这里的请求鉴权就是基于引入的 Shrio + JWT 完成。

## 二、内容结构和目录

`当你需要学习编程知识，提高编程思维和编码能力的阶段时候，你需要看到什么资料？`🤔 不知道大家是否有想过这样一个问题。

每当我看到那些非常牛皮的架构或者框架的时候，我就希望把他们吃透，并拿捏成自己的知识体系。但往往这些框架源码有太多的繁枝末节，也因为随着不断的需求迭代，让一些旁路细节流程的大量代码掩盖了核心流程。**所以**当你想学习时候，往往也是有心无力，根本不知道从哪开始。

**现在我来为你铺路！**

为了解决这样的学习问题，小傅哥把一个API网关项目，以不断接需求迭代的视角，一点点渐进式的完成整套代码开发。那么这样就可以让大家有清晰的学习编码路线，把一整套这样的东西学习完成。—— 跟着小傅哥学习，你可以`不浪费时间`、`少走弯路`、目标明确的把技术学习到手。

<div align="center">
    <img src="https://bugstack.cn/images/article/assembly/api-gateway/api-gateway-230610-05.png?raw=true" width="850px">
</div>

## 三、设计模式与编码

每每在公司经历一个大项目时，其实不只是看重这块业务场景，还看重对应这套项目的架构和编码，跟着各路大牛提升经验。可能就这样一个项目的学习，就能把一个人的编码思维提升到一个新的台阶。

那么小傅哥再做这套架构和编码时，特别注重整体的架构设计和编码实现。接下来我给大家举例看看这套代码中的代码实现。

### 1. 会话模型

**源码**：`cn.bugstack.gateway.core.session.defaults.DefaultGatewaySessionFactory`

<div align="center">
    <img src="https://bugstack.cn/images/article/assembly/api-gateway/api-gateway-230610-06.png?raw=true" width="450px">
</div>

```java
public class DefaultGatewaySessionFactory implements GatewaySessionFactory {

    private final Configuration configuration;

    public DefaultGatewaySessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public GatewaySession openSession(String uri) {
        // 获取数据源连接信息：这里把 Dubbo、HTTP 抽象为一种连接资源
        DataSourceFactory dataSourceFactory = new UnpooledDataSourceFactory();
        dataSourceFactory.setProperties(configuration, uri);
        DataSource dataSource = dataSourceFactory.getDataSource();
        // 创建执行器
        Executor executor = configuration.newExecutor(dataSource.getConnection());
        // 创建会话：DefaultGatewaySession
        return new DefaultGatewaySession(configuration, uri, executor);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
```

- 会话模型是网关算力中非常重要的一环，所有的 HTTP 请求都可以被抽象为会话模型。通过会话模型封装出 HTTP 到 RPC 的处理，中间再通过执行器和RPC抽象的数据源进行衔接。

### 2. 抽象模板

**源码**：`cn.bugstack.gateway.core.socket.BaseHandler`

<div align="center">
    <img src="https://bugstack.cn/images/article/assembly/api-gateway/api-gateway-230610-07.png?raw=true" width="450px">
</div>

```java
public abstract class BaseHandler<T> extends SimpleChannelInboundHandler<T> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
        session(ctx, ctx.channel(), msg);
    }

    protected abstract void session(ChannelHandlerContext ctx, final Channel channel, T request);

}
```

- 网关会话中还需要协议的处理，而协议的接收、解析、转换，就需要通过 Netty 实现的 Socket 服务来封装。通过为了更好的体现出会话的结构，这里小傅哥通过一个抽象类模板，定义出 session 方法。—— 好的代码，就是好的文档。有了这样的约定，也就不需要太多的口口相传。

### 3. 映射代理

**源码**：`cn.bugstack.gateway.core.bind.MapperProxyFactory`

<div align="center">
    <img src="https://bugstack.cn/images/article/assembly/api-gateway/api-gateway-230610-08.png?raw=true" width="450px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/assembly/api-gateway/api-gateway-230610-09.png?raw=true" width="750px">
</div>

```java
public class MapperProxyFactory {

    private String uri;

    public MapperProxyFactory(String uri) {
        this.uri = uri;
    }

    private final Map<String, IGenericReference> genericReferenceCache = new ConcurrentHashMap<>();

    public IGenericReference newInstance(GatewaySession gatewaySession) {
        return genericReferenceCache.computeIfAbsent(uri, k -> {
            HttpStatement httpStatement = gatewaySession.getConfiguration().getHttpStatement(uri);
            // 泛化调用
            MapperProxy genericReferenceProxy = new MapperProxy(gatewaySession, uri);
            // 创建接口
            InterfaceMaker interfaceMaker = new InterfaceMaker();
            interfaceMaker.add(new Signature(httpStatement.getMethodName(), Type.getType(String.class), new Type[]{Type.getType(String.class)}), null);
            Class<?> interfaceClass = interfaceMaker.create();
            // 代理对象
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Object.class);
            // IGenericReference 统一泛化调用接口
            // interfaceClass    根据泛化调用注册信息创建的接口，建立 http -> rpc 关联
            enhancer.setInterfaces(new Class[]{IGenericReference.class, interfaceClass});
            enhancer.setCallback(genericReferenceProxy);
            return (IGenericReference) enhancer.create();
        });
    }

}
```

- 为了更好的衔接 HTTP 请求的地址【`/wg/activity/sayHi`】与 RPC 服务的映射关系，这里我们像 ORM 框架一样做了 bind 绑定关系。有了这样一层绑定关系的抽象设计，就会变得非常好维护代码实现关系。—— 代码就是一块砖头🧱，怎么搭建摆放，那是设计师的能力体现。

### 4. 领域驱动

不只是代码，小傅哥也希望各个实现的工程结构也是干净整洁的。永远不是使用设计模式耽误时间，是不具备这样的经验的人员耽误时间。`不是现在耽误开发时间，就是耽误以后的迭代时间。`

<div align="center">
    <img src="https://bugstack.cn/images/article/assembly/api-gateway/api-gateway-230610-10.png?raw=true" width="750px">
</div>

- 举例：如何设计出领域驱动的四层架构，会用 DDD 其实 DDD 也就没那么难。驾驭不了才难。
- 同时到处都能看到设计模式的身影，用设计模式的思想解决各类场景实现问题。

## 四、技术项目与生态

其实小傅哥所构建的是一整套项目生态，以`API网关`所提供的HTTP服务为枢纽，衔接星球中的各类项目进行组合构建。目前星球中包括；`4个业务项目`和`3个组件项目`，它们可以被如下关系结构展示；

<div align="center">
    <img src="https://bugstack.cn/images/article/assembly/api-gateway/api-gateway-230610-04.png?raw=true" width="850px">
</div>

>[🧧加入学习](https://bugstack.cn/md/zsxq/other/join.html)

**所以**，这么多成体系的项目，你加入小傅哥的知识星球，**相当于加入了一个小型的技术公司**。你需要的各类项目，这里都可以学习到的。`技术的`、`业务的`、`组件的`，`还包括运维实施的`，而每一个项目都是以企业级标准进行设计和实现，学习这样的项目才是真正的奔着能工作学习的！
