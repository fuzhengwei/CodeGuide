---
layout: post
category: itstack-demo-netty-2 
title: netty案例，netty4.1中级拓展篇一《Netty与SpringBoot整合》
tagline: by 付政委
tag: [netty,itstack-demo-netty-2]
lock: need
---

# netty案例，netty4.1中级拓展篇一《Netty与SpringBoot整合》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
在实际的开发中，我们需要对netty服务进行更多的操作，包括；获取它的状态信息、启动/停止、对客户端用户强制下线等等，为此我们需要把netty服务加入到web系统中，那么本章节介绍如何将Netty与SpringBoot整合。
>Spring Boot是由Pivotal团队提供的全新框架，其设计目的是用来简化新Spring应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。通过这种方式，Spring Boot致力于在蓬勃发展的快速应用开发领域(rapid application development)成为领导者。

## 开发环境
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】

## 代码示例
```java
itstack-demo-netty-2-01
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.demo.netty
    │   │       ├── server
    │   │       │	├── MyChannelInitializer.java
    │   │       │	├── MyServerHandler.java
    │   │       │	└── NettyServer.java
    │   │       └── web
    │   │       	├── NettyApplication.java
    │   │       	└── NettyController.java
    │   └── resource
    │      	└── application.yml
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```
>MyChannelInitializer.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        // 基于换行符号
        channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        channel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        channel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyServerHandler());
    }

}
```
>MyServerHandler.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(MyServerHandler.class);

    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        logger.info("链接报告开始");
        logger.info("链接报告信息：有一客户端链接到本服务端");
        logger.info("链接报告IP:{}", channel.localAddress().getHostString());
        logger.info("链接报告Port:{}", channel.localAddress().getPort());
        logger.info("链接报告完毕");
        //通知客户端链接建立成功
        String str = "通知客户端链接建立成功" + " " + new Date() + " " + channel.localAddress().getHostString() + "\r\n";
        ctx.writeAndFlush(str);
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端断开链接{}", ctx.channel().localAddress().toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
        logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 服务端接收到消息：" + msg);
        //通知客户端链消息发送成功
        String str = "服务端收到：" + new Date() + " " + msg + "\r\n";
        ctx.writeAndFlush(str);
    }

    /**
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        logger.info("异常信息：\r\n" + cause.getMessage());
    }

}
```
>NettyServer.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
@Component("nettyServer")
public class NettyServer {

    private Logger logger = LoggerFactory.getLogger(NettyServer.class);

    //配置服务端NIO线程组
    private final EventLoopGroup parentGroup = new NioEventLoopGroup(); //NioEventLoopGroup extends MultithreadEventLoopGroup Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
    private final EventLoopGroup childGroup = new NioEventLoopGroup();
    private Channel channel;

    public ChannelFuture bing(InetSocketAddress address) {
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)    //非阻塞模式
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new MyChannelInitializer());

            channelFuture = b.bind(address).syncUninterruptibly();
            channel = channelFuture.channel();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (null != channelFuture && channelFuture.isSuccess()) {
                logger.info("itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}");
            } else {
                logger.error("itstack-demo-netty server start error. {关注公众号：bugstack虫洞栈，获取源码}");
            }
        }
        return channelFuture;
    }

    public void destroy() {
        if (null == channel) return;
        channel.close();
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }

    public Channel getChannel() {
        return channel;
    }

}
```
>NettyApplication.java *SpringBoot入口

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
@SpringBootApplication
@ComponentScan("org.itstack.demo.netty")
public class NettyApplication implements CommandLineRunner {

    @Value("${netty.host}")
    private String host;
    @Value("${netty.port}")
    private int port;
    @Autowired
    private NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        InetSocketAddress address = new InetSocketAddress(host, port);
        ChannelFuture channelFuture = nettyServer.bing(address);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> nettyServer.destroy()));
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }

}
```
>NettyController.java *WEB方法

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
@RestController
@RequestMapping(value = "/nettyserver", method = RequestMethod.GET)
public class NettyController {

    @Resource
    private NettyServer nettyServer;

    @RequestMapping("/localAddress")
    public String localAddress() {
        return "nettyServer localAddress " + nettyServer.getChannel().localAddress();
    }

    @RequestMapping("/isOpen")
    public String isOpen() {
        return "nettyServer isOpen " + nettyServer.getChannel().isOpen();
    }

}
```
>application.yml *NettyServer服务端配置

```xml
server:
  port:8080

netty:
  host: 127.0.0.1
  port: 7397
```

>ApiTest.java *NettyClient客户端，用于测试

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class ApiTest {

    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    // 基于换行符号
                    channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    // 解码转String，注意调整自己的编码格式GBK、UTF-8
                    channel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
                    // 解码转String，注意调整自己的编码格式GBK、UTF-8
                    channel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                    // 在管道中添加我们自己的接收数据实现方法
                    channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            //接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
                            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 客户端接收到消息：" + msg);
                        }
                    });
                }
            });
            ChannelFuture f = b.connect("127.0.0.1", 7397).sync();
            System.out.println("itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}");

            //向服务端发送信息
            f.channel().writeAndFlush("你好，SpringBoot启动的netty服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取全套源码。“我的结尾是一个换行符，用于传输半包粘包处理”\r\n");
            f.channel().writeAndFlush("你好，SpringBoot启动的netty服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取全套源码。“我的结尾是一个换行符，用于传输半包粘包处理”\r\n");
            f.channel().writeAndFlush("你好，SpringBoot启动的netty服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取全套源码。“我的结尾是一个换行符，用于传输半包粘包处理”\r\n");
            f.channel().writeAndFlush("你好，SpringBoot启动的netty服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取全套源码。“我的结尾是一个换行符，用于传输半包粘包处理”\r\n");
            f.channel().writeAndFlush("你好，SpringBoot启动的netty服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取全套源码。“我的结尾是一个换行符，用于传输半包粘包处理”\r\n");

            f.channel().closeFuture().syncUninterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
```
## 测试结果
>启动SpringBoot *NettyApplication.main >run

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-02-1-1.png)

>启动ApiTest

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-02-1-2.png)

>Web访问 *http://localhost:8080/nettyserver/localAddress

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-02-1-3.png)

>Web访问 *http://localhost:8080/nettyserver/isOpen

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-02-1-4.png)

>服务端执行结果

```java
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.0.4.RELEASE)

2019-08-04 16:39:57.702  INFO 9160 --- [           main] o.i.demo.netty.web.NettyApplication      : Starting NettyApplication on JRA1W11T0247 with PID 9160 (E:\itstack\GIT\itstack.org\itstack-demo-netty\itstack-demo-netty-2-01\target\classes started by fuzhengwei1 in E:\itstack\GIT\itstack.org\itstack-demo-netty)
2019-08-04 16:39:57.705  INFO 9160 --- [           main] o.i.demo.netty.web.NettyApplication      : No active profile set, falling back to default profiles: default
2019-08-04 16:39:57.784  INFO 9160 --- [           main] ConfigServletWebServerApplicationContext : Refreshing org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@3e2e18f2: startup date [Sun Aug 04 16:39:57 CST 2019]; root of context hierarchy
2019-08-04 16:39:59.065  INFO 9160 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2019-08-04 16:39:59.090  INFO 9160 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2019-08-04 16:39:59.090  INFO 9160 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet Engine: Apache Tomcat/8.5.32
2019-08-04 16:39:59.099  INFO 9160 --- [ost-startStop-1] o.a.catalina.core.AprLifecycleListener   : The APR based Apache Tomcat Native library which allows optimal performance in production environments was not found on the java.library.path: [C:\Program Files\Java\jdk1.8.0_45\bin;C:\windows\Sun\Java\bin;C:\windows\system32;C:\windows;C:\ProgramData\Oracle\Java\javapath;C:\Program Files (x86)\Common Files\NetSarang;C:\Program Files (x86)\Intel\iCLS Client\;C:\Program Files\Intel\iCLS Client\;C:\windows\system32;C:\windows;C:\windows\System32\Wbem;C:\windows\System32\WindowsPowerShell\v1.0\;C:\Program Files\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files\Intel\Intel(R) Management Engine Components\IPT;C:\Program Files (x86)\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files (x86)\Intel\Intel(R) Management Engine Components\IPT;C:\Program Files\Java\jdk1.8.0_45/bin;C:\Program Files\Java\jdk1.8.0_45/jre/bin;D:\Program Files\SlikSvn\bin;D:\Program Files\TortoiseSVN\bin;D:\Program Files (x86)\apache-maven-2.2.1\bin;D:\Program Files\TortoiseGit\bin;D:\Program Files (x86)\SSH Communications Security\SSH Secure Shell;.]
2019-08-04 16:39:59.200  INFO 9160 --- [ost-startStop-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2019-08-04 16:39:59.200  INFO 9160 --- [ost-startStop-1] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1419 ms
2019-08-04 16:39:59.253  INFO 9160 --- [ost-startStop-1] o.s.b.w.servlet.ServletRegistrationBean  : Servlet dispatcherServlet mapped to [/]
2019-08-04 16:39:59.256  INFO 9160 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'characterEncodingFilter' to: [/*]
2019-08-04 16:39:59.257  INFO 9160 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'hiddenHttpMethodFilter' to: [/*]
2019-08-04 16:39:59.257  INFO 9160 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'httpPutFormContentFilter' to: [/*]
2019-08-04 16:39:59.257  INFO 9160 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'requestContextFilter' to: [/*]
2019-08-04 16:39:59.433  INFO 9160 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**/favicon.ico] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2019-08-04 16:39:59.571  INFO 9160 --- [           main] s.w.s.m.m.a.RequestMappingHandlerAdapter : Looking for @ControllerAdvice: org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@3e2e18f2: startup date [Sun Aug 04 16:39:57 CST 2019]; root of context hierarchy
2019-08-04 16:39:59.628  INFO 9160 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/nettyserver/isOpen],methods=[GET]}" onto public java.lang.String org.itstack.demo.netty.web.NettyController.isOpen()
2019-08-04 16:39:59.629  INFO 9160 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/nettyserver/localAddress],methods=[GET]}" onto public java.lang.String org.itstack.demo.netty.web.NettyController.localAddress()
2019-08-04 16:39:59.631  INFO 9160 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error]}" onto public org.springframework.http.ResponseEntity<java.util.Map<java.lang.String, java.lang.Object>> org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.error(javax.servlet.http.HttpServletRequest)
2019-08-04 16:39:59.632  INFO 9160 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error],produces=[text/html]}" onto public org.springframework.web.servlet.ModelAndView org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.errorHtml(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)
2019-08-04 16:39:59.735  INFO 9160 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/webjars/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2019-08-04 16:39:59.736  INFO 9160 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2019-08-04 16:39:59.847  INFO 9160 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2019-08-04 16:39:59.873  INFO 9160 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-08-04 16:39:59.876  INFO 9160 --- [           main] o.i.demo.netty.web.NettyApplication      : Started NettyApplication in 2.616 seconds (JVM running for 3.052)
2019-08-04 16:40:00.039  INFO 9160 --- [           main] o.itstack.demo.netty.server.NettyServer  : itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}
2019-08-04 16:40:11.922  INFO 9160 --- [ntLoopGroup-3-1] o.i.demo.netty.server.MyServerHandler    : 链接报告开始
2019-08-04 16:40:11.922  INFO 9160 --- [ntLoopGroup-3-1] o.i.demo.netty.server.MyServerHandler    : 链接报告信息：有一客户端链接到本服务端
2019-08-04 16:40:11.922  INFO 9160 --- [ntLoopGroup-3-1] o.i.demo.netty.server.MyServerHandler    : 链接报告IP:127.0.0.1
2019-08-04 16:40:11.922  INFO 9160 --- [ntLoopGroup-3-1] o.i.demo.netty.server.MyServerHandler    : 链接报告Port:7397
2019-08-04 16:40:11.922  INFO 9160 --- [ntLoopGroup-3-1] o.i.demo.netty.server.MyServerHandler    : 链接报告完毕
2019-08-04 16:40:11.952  INFO 9160 --- [ntLoopGroup-3-1] o.i.demo.netty.server.MyServerHandler    : 2019-08-04 16:40:11 服务端接收到消息：你好，SpringBoot启动的netty服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取全套源码。“我的结尾是一个换行符，用于传输半包粘包处理”
2019-08-04 16:40:11.953  INFO 9160 --- [ntLoopGroup-3-1] o.i.demo.netty.server.MyServerHandler    : 2019-08-04 16:40:11 服务端接收到消息：你好，SpringBoot启动的netty服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取全套源码。“我的结尾是一个换行符，用于传输半包粘包处理”
2019-08-04 16:40:11.954  INFO 9160 --- [ntLoopGroup-3-1] o.i.demo.netty.server.MyServerHandler    : 2019-08-04 16:40:11 服务端接收到消息：你好，SpringBoot启动的netty服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取全套源码。“我的结尾是一个换行符，用于传输半包粘包处理”
2019-08-04 16:40:11.955  INFO 9160 --- [ntLoopGroup-3-1] o.i.demo.netty.server.MyServerHandler    : 2019-08-04 16:40:11 服务端接收到消息：你好，SpringBoot启动的netty服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取全套源码。“我的结尾是一个换行符，用于传输半包粘包处理”
2019-08-04 16:40:11.956  INFO 9160 --- [ntLoopGroup-3-1] o.i.demo.netty.server.MyServerHandler    : 2019-08-04 16:40:11 服务端接收到消息：你好，SpringBoot启动的netty服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取全套源码。“我的结尾是一个换行符，用于传输半包粘包处理”
2019-08-04 16:42:30.991  INFO 9160 --- [ntLoopGroup-3-1] o.i.demo.netty.server.MyServerHandler    : 异常信息：
远程主机强迫关闭了一个现有的连接。
2019-08-04 16:42:30.997  INFO 9160 --- [ntLoopGroup-3-1] o.i.demo.netty.server.MyServerHandler    : 客户端断开链接/127.0.0.1:7397

Process finished with exit code -1
```

>客户端执行结果

```java
16:40:11.856 [main] DEBUG io.netty.buffer.ByteBufUtil - -Dio.netty.maxThreadLocalCharBufferSize: 16384
itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}
16:40:11.897 [main] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.maxCapacityPerThread: 4096
16:40:11.897 [main] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.maxSharedCapacityFactor: 2
16:40:11.898 [main] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.linkCapacity: 16
16:40:11.898 [main] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.ratio: 8
16:40:11.910 [nioEventLoopGroup-2-1] DEBUG io.netty.buffer.AbstractByteBuf - -Dio.netty.buffer.checkAccessible: true
16:40:11.910 [nioEventLoopGroup-2-1] DEBUG io.netty.buffer.AbstractByteBuf - -Dio.netty.buffer.checkBounds: true
16:40:11.912 [nioEventLoopGroup-2-1] DEBUG io.netty.util.ResourceLeakDetectorFactory - Loaded default ResourceLeakDetector: io.netty.util.ResourceLeakDetector@2a2a41bc
2019-08-04 16:40:11 客户端接收到消息：通知客户端链接建立成功 Sun Aug 04 16:40:11 CST 2019 127.0.0.1
2019-08-04 16:40:11 客户端接收到消息：服务端收到：Sun Aug 04 16:40:11 CST 2019 你好，SpringBoot启动的netty服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取全套源码。“我的结尾是一个换行符，用于传输半包粘包处理”
2019-08-04 16:40:11 客户端接收到消息：服务端收到：Sun Aug 04 16:40:11 CST 2019 你好，SpringBoot启动的netty服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取全套源码。“我的结尾是一个换行符，用于传输半包粘包处理”
2019-08-04 16:40:11 客户端接收到消息：服务端收到：Sun Aug 04 16:40:11 CST 2019 你好，SpringBoot启动的netty服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取全套源码。“我的结尾是一个换行符，用于传输半包粘包处理”
2019-08-04 16:40:11 客户端接收到消息：服务端收到：Sun Aug 04 16:40:11 CST 2019 你好，SpringBoot启动的netty服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取全套源码。“我的结尾是一个换行符，用于传输半包粘包处理”
2019-08-04 16:40:11 客户端接收到消息：服务端收到：Sun Aug 04 16:40:11 CST 2019 你好，SpringBoot启动的netty服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取全套源码。“我的结尾是一个换行符，用于传输半包粘包处理”

Process finished with exit code -1
```

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！