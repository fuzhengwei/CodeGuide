---
title: Dubbo
lock: need
---

# Dubbo 使用教程和原理分析

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=828140981&bvid=BV1Sg4y1c7m4&cid=1190616538&page=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

本文的宗旨在于通过简单干净实践的方式教会读者，为什么要使用Dubbo、怎么使用Dubbo、Dubbo通信的原理是什么。在学习本文后，你可以避开很多关于 Dubbo 使用时的坑，也能更清楚自己的编码是在做什么。

本文涉及的工程：
- xfg-dev-tech-dubbo：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-dubbo](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-dubbo)
- xfg-dev-tech-dubbo-test：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-dubbo-test](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-dubbo-test)

## 一、为什么使用

随着互联网场景中所要面对的用户规模和体量的增加，系统也需要做相应的拆分设计和实现。随之而来的，以前的一套系统，现在成了多个微服务。如：电商系统，以前就在一个工程中写就可以了，现在需要拆分出，用户、支付、商品、配送、活动、风控等各个模块。那么这些模块拆分后，如何高效的通信呢？

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-dubbo-01.png?raw=true" width="650px">
</div>

- 关于通信，就引入了 RPC 框架，而 Dubbo 就是其中的一个实现方式。
- 那为啥用 Dubbo 呢？其实核心问题就一个，为了提高通信效率。因为 Dubbo 的底层通信是 Socket 而不是 HTTP 所以通信的性能会更好。同时 Dubbo 又有分布式的高可用设计，在一组部署了交易服务的实例宕机后，会被从注册中心摘除，之后流量会打到其他服务上。

## 二、要怎么使用

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-dubbo-02.png?raw=true" width="650px">
</div>

Dubbo 的使用分为2方，一个是接口的提供方，另外一个是接口的调用方。接口的提供方需要提供出被调用方使用接口的描述性信息。这个信息包括：接口名称、接口入参、接口出参，只有让调用方拿到这些信息以后，它才能依托于这样的接口信息做一个代理操作，并在代理类中使用 Socket 完成双方的信息交互。

所以你看上去调用 RPC 接口好像和使用 HTTP 也没啥区别，无非就是引入了 POM 配置，之后再配置了注解就可以使用了。但其实，它是把你的 Jar 当做代理的必要参数使用了。**本文也会介绍，具体是怎么代理的**

## 三、使用的案例

对于编程的学习来说，其实最开始的那一下，不是搞明白所有原理，而是先让自己可以看到运行出来的效果。哎，之后就去分析原理，这样会舒服的多。

所以小傅哥这里提供了一套简单的 Dubbo 使用案例，只要你满足最基本的配置条件，就可以运行出效果；
1. JDK 1.8
1. Maven 3.x - jdk1.8支持的就可以
2. Dubbo 3.1.4 - POM 中已经配置，与2.x最大的使用上的区别就是一些注解的使用
3. Zookeeper 3.4.x - 如果你只是按照本文中的直连模式测试，那么不安装 Zookeeper 也可以

### 1. 接口提供方

工程案例创建结构，采用的是 DDD 结构。但和 DDD 一点关系没有。如果你对工程创建有疑惑，可以参考 [《Java 简明教程》之 DDD 架构](https://bugstack.cn/md/road-map/ddd.html)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-dubbo-03.png?raw=true" width="750px">
</div>

#### 1.1 接口定义

**源码**：`cn.bugstack.dev.tech.dubbo.api.IUserService`

```java
public interface IUserService {

    Response<UserResDTO> queryUserInfo(UserReqDTO reqDTO);

}
```

- 接口定义平平无奇，但第1个坑暗藏玄机！
- 也就是，所有的 Dubbo 接口，出入参，默认都需要继承 Serializable 接口。也就是 UserReqDTO、UserResDTO、Response 这3个类，都得继承 Serializable 序列化接口。

#### 1.2 接口实现

**源码**：`cn.bugstack.dev.tech.dubbo.trigger.rpc.UserService`

```java
@Slf4j
@DubboService(version = "1.0.0")
public class UserService implements IUserService {

    @Override
    public Response<UserResDTO> queryUserInfo(UserReqDTO reqDTO) {
        log.info("查询用户信息 userId: {} reqStr: {}", reqDTO.getUserId(), JSON.toJSONString(reqDTO));
        try {
            // 1. 模拟查询【你可以从数据库或者Redis缓存获取数据】
            UserResDTO resDTO = UserResDTO.builder()
                    .userId(reqDTO.getUserId())
                    .userName("小傅哥")
                    .userAge(20)
                    .build();

            // 2. 返回结果
            return Response.<UserResDTO>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(resDTO).build();
        } catch (Exception e) {
            log.error("查询用户信息失败 userId: {} reqStr: {}", reqDTO.getUserId(), JSON.toJSONString(reqDTO), e);
            return Response.<UserResDTO>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

}
```

- 接口实现平平无奇，但第2个坑暗藏玄机！
- Dubbo 的实现接口，需要被 Dubbo 自己管理。所以 Dubbo 提供了 @DubboService 注解。有些小卡拉米，使用的是不是 Spring 的 @Service 呀？尤其是以前的 Dubbo 版本 `2.7.*` 它的注解也是 @Service 也不留神就用成了 Spring 的 @Service。一个小bug，又调了一上午。

#### 1.3 工程配置

**application.yml**

```java
dubbo:
  application:
    name: xfg-dev-tech-dubbo
    version: 1.0.0
  registry:
    address: zookeeper://127.0.0.1:2181 # N/A - 无zookeeper可配置 N/A 走直连模式测试
  protocol:
    name: dubbo
    port: 20881
  scan:
    base-packages: cn.bugstack.dev.tech.dubbo.api
```

- 配置信息平平无奇，但第3个坑暗藏玄机！
- base-packages 扫描的是哪里配置了 Dubbo 的 API 入口，给它入口就行，它会自己找到实现类。但！你要知道 Java 的 Spring 应用能扫描到，能被 Spring 管理，那么 pom 要**直接或者间接**的引导到定义了 Dubbo 的模块。
- 再有一个问题，Spring 应用开发，讲究约定大于配置。你 Application 应用的包名，应该是可以覆盖到其他包名的。比如 Application 都配置到 `cn.bugstack.dev.tech.dubbo.a.b.c.d.*` 去了，它默认就扫不到 `cn.bugstack.dev.tech.dubbo.api` 了。一个小bug，一下午又过去了。 
- 注意：address：如果配置的是 N/A 就是不走任何注册中心，就是个直连，主要用于本地验证的。如果你配置了 zookeeper://127.0.0.1:2181 就需要先安装一个 zookeeper 另外，即使你配置了注册中心的方式，也可以直连测试。

#### 1.4 应用构建

以上信息都准备了，一群小卡拉米开始掉到第4个坑里了！

你有2个应用，一个Dubbo接口提供方、一个Dubbo接口使用方。那么你在给你另外一个应用使用接口的时候，你在 InelliJ IDEA 的 Maven 中执行 Install 了吗？

Install 是干啥的？它是为了让你使用了同一个本地 Maven 配置的应用，可以引入到对方提供的 Jar 包。你 Install 以后，这个 Jar 包就会进入到本地 Maven 仓库了。如果是公司里开发，会有专门的自己家部署的，私有Maven中心仓库，就可以通过 deploy 把本地 Jar 发布上去，那么公司里的伙伴，也就都可以引用了。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-dubbo-04.png?raw=true" width="450px">
</div>

- 你要先点击 root 下的 install 操作，这样就会自动构建了。
- 如果你电脑配置有点低，也会出现一些`气人怪相`，比如就刷不进去，install 了也引用不了。记得要 clean 清空下，也可以直接到 maven 文件夹去清空。

### 2. 接口使用方

有些小卡拉米觉得前面的坑都扫干净了，就完事了。没有接下来还有坑，让你一搞搞一天，半夜也睡不好。

#### 2.1 POM 引入

```xml
<dependency>
    <groupId>cn.bugstack</groupId>
    <artifactId>xfg-dev-tech-dubbo-api</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

- POM 的配置，就是把 Jar 包给引用进来。因为 Dubbo 需要根据这个接口，做一个代理操作。**不引入，你代码就爆红啦！爆红啦！🌶**

#### 2.2 消费配置

**源码**：`application.yml`

```java
dubbo:
  application:
    name: xfg-dev-tech-dubbo
    version: 1.0.0
  registry:
     address: zookeeper://127.0.0.1:2181
#    address: N/A
  protocol:
    name: dubbo
    port: 20881
```

- 配置了 zookeeper 你就用第一个，代码中对应 `@DubboReference(interfaceClass = IUserService.class, version = "1.0.0")`
- 配置了 N/A 你就用第二个，代码中必须指定直连。`@DubboReference(interfaceClass = IUserService.class, url = "dubbo://127.0.0.1:20881", version = "1.0.0")`

#### 2.3 代码配置

**源码**：`cn.bugstack.dev.tech.dubbo.consumer.test.ApiTest`

```java
// 直连模式；@DubboReference(interfaceClass = IUserService.class, url = "dubbo://127.0.0.1:20881", version = "1.0.0")
@DubboReference(interfaceClass = IUserService.class, version = "1.0.0")
private IUserService userService;

@Test
public void test_userService() {
    UserReqDTO reqDTO = UserReqDTO.builder().userId("10001").build();
    Response<UserResDTO> resDTO = userService.queryUserInfo(reqDTO);
    log.info("测试结果 req: {} res: {}", JSON.toJSONString(reqDTO), JSON.toJSONString(resDTO));
}
```

**测试结果**

```java
2023-07-08 15:37:22.291  INFO 62481 --- [           main] c.b.d.tech.dubbo.consumer.test.ApiTest   : 测试结果 req: {"userId":"10001"} res: {"code":"0000","data":{"userAge":20,"userId":"10001","userName":"小傅哥"},"info":"成功"}
2023-07-08 15:37:22.324  INFO 62481 --- [tor-Framework-0] o.a.c.f.imps.CuratorFrameworkImpl        : backgroundOperationsLoop exiting
```

- 如果不出啥意外，到这你就可以直接启动运行了。并看到测试结果。
- 但别忘记了，你启动的时候，需要先启动 xfg-dev-tech-dubbo 让接口提供方跑起来。

## 四、原理的分析

都说 Jar 是提供可描述性信息的，对方才能代理调用。那么这个过程是怎么干的呢，总不能一问这个，就让小卡拉米们去手写 Dubbo 呀！所以小傅哥会通过最简单模型结构，让你了解这个 Dubbo 通信的原理，方便小卡拉米们上手。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-dubbo-06.png?raw=true" width="850px">
</div>

- 如果所示，接口使用方，对接口进行代理。什么是代理呢，代理就是用一个包装的结构，代替原有的操作。在这个包装的结构里，你可以自己扩展出任意的方法。
- 那么，这里的代理。就是根据接口的信息，创建出一个代理对象，在代理对象中，提供 Socket 请求。当调用这个接口的时候，就可以对接口提供方的，发起 Socket 请求了。
- 而 Socket 接收方，也就是接口提供方。他收到信息以后，根据接口的描述性内容，进行一个反射调用。这下就把信息给请求出来，之后再通过 Socket 返回回去就可以了。

好，核心的原理就这么点。接下来，我们从代码中看看。

### 1. 接口反射 - 提供方

**源码**：`cn.bugstack.dev.tech.dubbo.trigger.socket.RpcServerSocket`

```java
@Slf4j
@Service
public class RpcServerSocket implements Runnable {

    private ApplicationContext applicationContext;

    public RpcServerSocket(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        new Thread(this).start();
    }

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) {
                            channel.pipeline().addLast(new ObjectEncoder());
                            channel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            channel.pipeline().addLast(new SimpleChannelInboundHandler<Map<String, Object>>() {

                                @Override
                                protected void channelRead0(ChannelHandlerContext channelHandlerContext, Map<String, Object> request) throws Exception {
                                    // 解析参数
                                    Class<?> clazz = (Class<?>) request.get("clazz");
                                    String methodName = (String) request.get("methodName");
                                    Class<?>[] paramTypes = (Class<?>[]) request.get("paramTypes");
                                    Object[] args = (Object[]) request.get("args");

                                    // 反射调用
                                    Method method = clazz.getMethod(methodName, paramTypes);
                                    Object invoke = method.invoke(applicationContext.getBean(clazz), args);

                                    // 封装结果
                                    Map<String, Object> response = new HashMap<>();
                                    response.put("data", invoke);

                                    log.info("RPC 请求调用 clazz:{} methodName:{}, response:{}", clazz.getName(), methodName, JSON.toJSON(response));
                                    // 回写数据
                                    channelHandlerContext.channel().writeAndFlush(response);
                                }
                            });
                        }
                    });

            ChannelFuture f = b.bind(22881).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
```

这段代码主要提供的功能包括：
1. Netty Socket 启动一个服务端。
2. 注入 ApplicationContext applicationContext 用于在接收到请求接口信息后，获取对应的 Bean 对象。
3. 根据请求来的 Bean 对象，以及参数的必要信息。进行接口的反射调用。
4. 最后一步，就是把接口反射请求的信息，再通过 Socket 返回回去。

### 2. 接口代理 - 调用方

打开工程：[xfg-dev-tech-dubbo-test](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-dubbo-test)

**源码**：`cn.bugstack.dev.tech.dubbo.consumer.config.RPCProxyBeanFactory`

```java
@Slf4j
@Component("rpcProxyBeanFactory")
public class RPCProxyBeanFactory implements FactoryBean<IUserService>, Runnable {

    private Channel channel;

    // 缓存数据，实际RPC会对每次的调用生成一个ID来标记获取
    private Object responseCache;

    public RPCProxyBeanFactory() throws InterruptedException {
        new Thread(this).start();
        while (null == channel) {
            Thread.sleep(150);
            log.info("Rpc Socket 链接等待...");
        }
    }

    @Override
    public IUserService getObject() throws Exception {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?>[] classes = {IUserService.class};
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                if (Object.class.equals(method.getDeclaringClass())) {
                    return method.invoke(this, args);
                }

                Map<String, Object> request = new HashMap<>();
                request.put("clazz", IUserService.class);
                request.put("methodName", method.getName());
                request.put("paramTypes", method.getParameterTypes());
                request.put("args", args);

                channel.writeAndFlush(request);

                // 模拟超时等待，一般RPC接口请求，都有一个超时等待时长。
                Thread.sleep(350);

                return responseCache;
            }
        };

        return (IUserService) Proxy.newProxyInstance(classLoader, classes, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserService.class;
    }

    @Override
    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.AUTO_READ, true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new ObjectEncoder());
                            channel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            channel.pipeline().addLast(new SimpleChannelInboundHandler<Map<String, Object>>() {

                                @Override
                                protected void channelRead0(ChannelHandlerContext channelHandlerContext, Map<String, Object> data) throws Exception {
                                    responseCache = data.get("data");
                                }
                            });
                        }
                    });
            ChannelFuture channelFuture = b.connect("127.0.0.1", 22881).syncUninterruptibly();
            this.channel = channelFuture.channel();
            channelFuture.channel().closeFuture().syncUninterruptibly();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
```

这段代码主要提供的功能包括：
1. 实现  `FactoryBean<IUserService>` 为的是把这样一个代理对象，交给 Spring 容器管理。
2. 实现 Runnable 接口，并在接口中，创建 Netty 的 Socket 客户端。客户端中接收来自服务端的消息，并临时存放到缓存中。**注意 Dubbo 中这块的处理会复杂一些，以及请求同步响应通信，这样才能把各个接口的调动记录下来**
3. `getObject()` 对象中，提供代理操作。代理里，就可以自己想咋搞咋搞了。而 Dubbo 也是在代理里，提供了如此的操作，对接口提供方发送请求消息，并在超时时间内返回接口信息。因为反射调用，需要你`提供类`、`方法`、`入参类型`、`入参内容`，所以我们要把这些信息传递给接口提供方。

### 3. 服务测试 - 消费验证

- 启动 xfg-dev-tech-dubbo
- 测试 xfg-dev-tech-dubbo-test

```java
@Resource(name = "rpcProxyBeanFactory")
private IUserService proxyUserService;

@Test
public void test_proxyUserService(){
    UserReqDTO reqDTO = UserReqDTO.builder().userId("10001").build();
    Response<UserResDTO> resDTO = proxyUserService.queryUserInfo(reqDTO);
    log.info("测试结果 req: {} res: {}", JSON.toJSONString(reqDTO), JSON.toJSONString(resDTO));
}
```

**测试结果**

```java
2023-07-08 16:14:51.322  INFO 74498 --- [           main] c.b.d.tech.dubbo.consumer.test.ApiTest   : 测试结果 req: {"userId":"10001"} res: {"code":"0000","data":{"userAge":20,"userId":"10001","userName":"小傅哥"},"info":"成功"}
```

- 这里我们给 IUserService 注入一个自己代理好的对象，之后就可以调用验证了。
- 好啦，到这我们就把关于 Dubbo 的事交代明白了，以上内容较多。小卡拉米需要细细的品味吸收！
