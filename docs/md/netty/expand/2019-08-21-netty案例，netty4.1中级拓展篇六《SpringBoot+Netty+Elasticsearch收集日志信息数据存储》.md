---
layout: post
category: itstack-demo-netty-2
title: netty案例，netty4.1中级拓展篇六《SpringBoot+Netty+Elasticsearch收集日志信息数据存储》
tagline: by 付政委
tag: [netty,itstack-demo-netty-2] 
lock: need
---

# netty案例，netty4.1中级拓展篇六《SpringBoot+Netty+Elasticsearch收集日志信息数据存储》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
在实际的开发场景中，我们希望将大量的业务以及用户行为数据存储起来用于分析处理，但是由于数据量较大且需要具备可分析功能所以将数据存储到文件系统更为合理。尤其是一些互联网高并发级应用，往往数据库都采用分库分表设计，那么将这些分散的数据通过binlog汇总到一个统一的文件系统就显得非常有必要。

## 开发环境
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
3. elasticsearch6.2.2
   1. [windows环境下安装elasticsearch6.2.2](https://bugstack.cn/md/devops/2019-08-12-windows%E7%8E%AF%E5%A2%83%E4%B8%8B%E5%AE%89%E8%A3%85elasticsearch6.2.2.html )
   2. [elasticsearch-head插件安装](https://bugstack.cn/md/devops/2019-08-13-elasticsearch-head%E6%8F%92%E4%BB%B6%E5%AE%89%E8%A3%85.html)

## 代码示例
```java
itstack-demo-netty-2-06
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.demo.netty
    │   │       ├── codec
    │   │       │	├── ObjDecoder.java
    │   │       │	└── ObjEncoder.java	
    │   │       ├── domain
    │   │       │	├── TransportProtocol.java
    │   │       │	└── User.java
    │   │       ├── server
    │   │       │	├── MyChannelInitializer.java
    │   │       │	├── MyServerHandler.java
    │   │       │	└── NettyServer.java
    │   │       ├── service
    │   │       │	├── impl
    │   │       │	│	└── UserServiceImpl.java	
    │   │       │	├── UserRepository.java
    │   │       │	└── UserService.java	
    │   │       ├── util
    │   │       │	└── SerializationUtil.java
    │   │       ├── web
    │   │       │	└── NettyController.java	
    │   │       └──	Application.java	
    │   └── resources	
    │      └── application.yml
    │
    └── test
         └── java
             └── org.itstack.demo.test
				 └── ApiTest.java
```

**演示部分重点代码块，完整代码下载关注公众号；bugstack虫洞栈**

>domain/User.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
@Document(indexName = "stack", type = "group_user")
public class User {

    @Id
    private String id;
    private String name;   //姓名
    private Integer age;   //年龄
    private String level;  //级别
    private Date entryDate;//时间
    private String mobile; //电话
    private String email;  //邮箱
    private String address;//地址


    public User(String id, String name, Integer age, String level, Date entryDate, String mobile, String email, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.level = level;
        this.entryDate = entryDate;
        this.mobile = mobile;
        this.email = email;
        this.address = address;

    }

    ... get/set
}
```

>server/MyServerHandler.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
@Service("myServerHandler")
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(MyServerHandler.class);

    @Autowired
    private UserService userService;

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
        logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 服务端接收到消息：" + JSON.toJSONString(msg));
		//接收数据写入到Elasticsearch
        TransportProtocol transportProtocol = (TransportProtocol) msg;
        userService.save((User) transportProtocol.getObj());
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

>service/UserService.java *提供CRUD方法

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public interface UserService {

    void save(User user);

    void deleteById(String id);

    User queryUserById(String id);

    Iterable<User> queryAll();

    Page<User> findByName(String name, PageRequest request);

}
```

>service/UserRepository.java *可以扩展需要的方法，User是表、String是ID

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public interface UserRepository extends ElasticsearchRepository<User, String> {

    Page<User> findByName(String name, Pageable pageable);

}
```

>service/impl/UserServiceImpl.java *CRUD实现类

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private UserRepository dataRepository;

    @Autowired
    public void setDataRepository(UserRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public void save(User user) {
        dataRepository.save(user);
    }

    @Override
    public void deleteById(String id) {
        dataRepository.deleteById(id);
    }

    @Override
    public User queryUserById(String id) {
        Optional<User> optionalUser = dataRepository.findById(id);
        return optionalUser.get();
    }

    @Override
    public Iterable<User> queryAll() {
        return dataRepository.findAll();
    }

    @Override
    public Page<User> findByName(String name, PageRequest request) {
        return dataRepository.findByName(name, request);
    }

}
```

>Application.java *springboot启动时会同时启动Netty服务

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(Application.class);

    @Value("${netty.host}")
    private String host;
    @Value("${netty.port}")
    private int port;
    @Resource
    private NettyServer nettyServer;

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(Application.class, args);
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

>application.properties *配置文件｛服务端口、netty、Elasticsearch｝

```java
## 服务端口
server.port = 8080

## Netty服务端配置
netty.host = 127.0.0.1
netty.port = 7397

## Elasticsearch配置｛更换为自己的cluster-name、cluster-nodes｝
spring.data.elasticsearch.cluster-name=es-itstack
spring.data.elasticsearch.cluster-nodes=127.0.0.1:9300
spring.data.elasticsearch.repositories.enabled=true
```

>ApiTest.java *Netty客户端，用于向服务端发送数据

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
                    //对象传输处理
                    channel.pipeline().addLast(new ObjDecoder(TransportProtocol.class));
                    channel.pipeline().addLast(new ObjEncoder(TransportProtocol.class));
                    // 在管道中添加我们自己的接收数据实现方法
                    channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

                        }
                    });
                }
            });
            ChannelFuture f = b.connect("127.0.0.1", 7397).sync();
            System.out.println("itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}");

            TransportProtocol tp1 = new TransportProtocol(1, new User(UUID.randomUUID().toString(), "李小明", 1, "T0-1", new Date(), "13566668888", "184172133@qq.com", "北京"));
            TransportProtocol tp2 = new TransportProtocol(1, new User(UUID.randomUUID().toString(), "张大明", 2, "T0-2", new Date(), "13566660001", "huahua@qq.com", "南京"));
            TransportProtocol tp3 = new TransportProtocol(1, new User(UUID.randomUUID().toString(), "李书鹏", 2, "T1-1", new Date(), "13566660002", "xiaobai@qq.com", "榆树"));
            TransportProtocol tp4 = new TransportProtocol(1, new User(UUID.randomUUID().toString(), "韩小雪", 2, "T2-1", new Date(), "13566660002", "xiaobai@qq.com", "榆树"));
            TransportProtocol tp5 = new TransportProtocol(1, new User(UUID.randomUUID().toString(), "董叔飞", 2, "T4-1", new Date(), "13566660002", "xiaobai@qq.com", "河北"));
            TransportProtocol tp6 = new TransportProtocol(1, new User(UUID.randomUUID().toString(), "候明相", 2, "T5-1", new Date(), "13566660002", "xiaobai@qq.com", "下花园"));
            TransportProtocol tp7 = new TransportProtocol(1, new User(UUID.randomUUID().toString(), "田明明", 2, "T3-1", new Date(), "13566660002", "xiaobai@qq.com", "东平"));
            TransportProtocol tp8 = new TransportProtocol(1, new User(UUID.randomUUID().toString(), "王大伟", 2, "T4-1", new Date(), "13566660002", "xiaobai@qq.com", "西湖"));
            TransportProtocol tp9 = new TransportProtocol(1, new User(UUID.randomUUID().toString(), "李雪明", 2, "T1-1", new Date(), "13566660002", "xiaobai@qq.com", "南昌"));
            TransportProtocol tp10 = new TransportProtocol(1, new User(UUID.randomUUID().toString(), "朱小飞", 2, "T2-1", new Date(), "13566660002", "xiaobai@qq.com", "吉林"));
            TransportProtocol tp11 = new TransportProtocol(1, new User(UUID.randomUUID().toString(), "牛大明", 2, "T1-1", new Date(), "13566660002", "xiaobai@qq.com", "长春"));
            TransportProtocol tp12 = new TransportProtocol(1, new User(UUID.randomUUID().toString(), "关雪儿", 2, "T2-1", new Date(), "13566660002", "xiaobai@qq.com", "深圳"));

            //向服务端发送信息
            f.channel().writeAndFlush(tp1);
            f.channel().writeAndFlush(tp2);
            f.channel().writeAndFlush(tp3);
            f.channel().writeAndFlush(tp4);
            f.channel().writeAndFlush(tp5);
            f.channel().writeAndFlush(tp6);
            f.channel().writeAndFlush(tp7);
            f.channel().writeAndFlush(tp8);
            f.channel().writeAndFlush(tp9);
            f.channel().writeAndFlush(tp10);
            f.channel().writeAndFlush(tp11);
            f.channel().writeAndFlush(tp12);

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

>启动Elasticsearch *也可以直接双击..elasticsearch-6.2.2/bin/elasticsearch.bat

```java
D:\Program Files\elasticsearch\node01\elasticsearch-6.2.2\bin>elasticsearch.bat
[2019-08-10T14:09:26,562][INFO ][o.e.n.Node               ] [node01] initializin
g ...
[2019-08-10T14:09:26,770][INFO ][o.e.e.NodeEnvironment    ] [node01] using [1] d
ata paths, mounts [[杞欢 (D:)]], net usable_space [301.3gb], net total_space [
407.1gb], types [NTFS]
[2019-08-10T14:09:26,771][INFO ][o.e.e.NodeEnvironment    ] [node01] heap size [
990.7mb], compressed ordinary object pointers [true]
[2019-08-10T14:09:26,843][INFO ][o.e.n.Node               ] [node01] node name [
node01], node ID [R5wRCDr0SSKsVsgkZwB-Hg]
[2019-08-10T14:09:26,843][INFO ][o.e.n.Node               ] [node01] version[6.2
.2], pid[22264], build[10b1edd/2018-02-16T19:01:30.685723Z], OS[Windows 7/6.1/am
d64], JVM[Oracle Corporation/Java HotSpot(TM) 64-Bit Server VM/1.8.0_45/25.45-b0
2]
[2019-08-10T14:09:26,843][INFO ][o.e.n.Node               ] [node01] JVM argumen
ts [-Xms1g, -Xmx1g, -XX:+UseConcMarkSweepGC, -XX:CMSInitiatingOccupancyFraction=
75, -XX:+UseCMSInitiatingOccupancyOnly, -XX:+AlwaysPreTouch, -Xss1m, -Djava.awt.
headless=true, -Dfile.encoding=UTF-8, -Djna.nosys=true, -XX:-OmitStackTraceInFas
tThrow, -Dio.netty.noUnsafe=true, -Dio.netty.noKeySetOptimization=true, -Dio.net
```

>启动Elasticsearch-head

```java
D:\Program Files\elasticsearch\head>npm run start

> elasticsearch-head@0.0.0 start D:\Program Files\elasticsearch\head
> grunt server

Running "connect:server" (connect) task
Waiting forever...
Started connect web server on http://localhost:9100
```

![Elasticsearch-head](https://bugstack.cn/assets/images/pic-content/2019/08/netty-2-06-1.png)

>启动StringBoot *Netty服务会随着启动{Application.main}

```java
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.0.1.RELEASE)

2019-08-10 14:14:49.619  INFO 5976 --- [           main] org.itstack.demo.netty.Application       : Starting Application on JRA1W11T0247 with PID 5976 (E:\itstack\GIT\itstack.org\itstack-demo-netty\itstack-demo-netty-2-06\target\classes started by fuzhengwei1 in E:\itstack\GIT\itstack.org\itstack-demo-netty)
2019-08-10 14:14:49.622  INFO 5976 --- [           main] org.itstack.demo.netty.Application       : No active profile set, falling back to default profiles: default
2019-08-10 14:14:49.704  INFO 5976 --- [           main] ConfigServletWebServerApplicationContext : Refreshing org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@7f010382: startup date [Sat Aug 10 14:14:49 CST 2019]; root of context hierarchy
2019-08-10 14:14:51.341  INFO 5976 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2019-08-10 14:14:51.367  INFO 5976 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2019-08-10 14:14:51.367  INFO 5976 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet Engine: Apache Tomcat/8.5.29
2019-08-10 14:14:51.377  INFO 5976 --- [ost-startStop-1] o.a.catalina.core.AprLifecycleListener   : The APR based Apache Tomcat Native library which allows optimal performance in production environments was not found on the java.library.path: [C:\Program Files\Java\jdk1.8.0_45\bin;C:\windows\Sun\Java\bin;C:\windows\system32;C:\windows;C:\ProgramData\Oracle\Java\javapath;C:\Program Files (x86)\Common Files\NetSarang;C:\Program Files (x86)\Intel\iCLS Client\;C:\Program Files\Intel\iCLS Client\;C:\windows\system32;C:\windows;C:\windows\System32\Wbem;C:\windows\System32\WindowsPowerShell\v1.0\;C:\Program Files\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files\Intel\Intel(R) Management Engine Components\IPT;C:\Program Files (x86)\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files (x86)\Intel\Intel(R) Management Engine Components\IPT;C:\Program Files\Java\jdk1.8.0_45/bin;C:\Program Files\Java\jdk1.8.0_45/jre/bin;D:\Program Files\SlikSvn\bin;D:\Program Files\TortoiseSVN\bin;D:\Program Files (x86)\apache-maven-2.2.1\bin;D:\Program Files\TortoiseGit\bin;D:\Program Files\nodejs\;D:\Program Files (x86)\SSH Communications Security\SSH Secure Shell;C:\Users\fuzhengwei1\AppData\Roaming\npm;;.]
2019-08-10 14:14:51.523  INFO 5976 --- [ost-startStop-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2019-08-10 14:14:51.523  INFO 5976 --- [ost-startStop-1] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1819 ms
2019-08-10 14:14:51.659  INFO 5976 --- [ost-startStop-1] o.s.b.w.servlet.ServletRegistrationBean  : Servlet dispatcherServlet mapped to [/]
2019-08-10 14:14:51.663  INFO 5976 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'characterEncodingFilter' to: [/*]
2019-08-10 14:14:51.664  INFO 5976 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'hiddenHttpMethodFilter' to: [/*]
2019-08-10 14:14:51.664  INFO 5976 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'httpPutFormContentFilter' to: [/*]
2019-08-10 14:14:51.664  INFO 5976 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'requestContextFilter' to: [/*]
2019-08-10 14:14:52.090  INFO 5976 --- [           main] o.elasticsearch.plugins.PluginsService   : no modules loaded
2019-08-10 14:14:52.092  INFO 5976 --- [           main] o.elasticsearch.plugins.PluginsService   : loaded plugin [org.elasticsearch.index.reindex.ReindexPlugin]
2019-08-10 14:14:52.092  INFO 5976 --- [           main] o.elasticsearch.plugins.PluginsService   : loaded plugin [org.elasticsearch.join.ParentJoinPlugin]
2019-08-10 14:14:52.092  INFO 5976 --- [           main] o.elasticsearch.plugins.PluginsService   : loaded plugin [org.elasticsearch.percolator.PercolatorPlugin]
2019-08-10 14:14:52.092  INFO 5976 --- [           main] o.elasticsearch.plugins.PluginsService   : loaded plugin [org.elasticsearch.script.mustache.MustachePlugin]
2019-08-10 14:14:52.092  INFO 5976 --- [           main] o.elasticsearch.plugins.PluginsService   : loaded plugin [org.elasticsearch.transport.Netty3Plugin]
2019-08-10 14:14:52.092  INFO 5976 --- [           main] o.elasticsearch.plugins.PluginsService   : loaded plugin [org.elasticsearch.transport.Netty4Plugin]
2019-08-10 14:14:52.973  INFO 5976 --- [           main] o.s.d.e.c.TransportClientFactoryBean     : adding transport node : 127.0.0.1:9300
2019-08-10 14:14:54.486  INFO 5976 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**/favicon.ico] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2019-08-10 14:14:54.730  INFO 5976 --- [           main] s.w.s.m.m.a.RequestMappingHandlerAdapter : Looking for @ControllerAdvice: org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@7f010382: startup date [Sat Aug 10 14:14:49 CST 2019]; root of context hierarchy
2019-08-10 14:14:54.800  INFO 5976 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/localAddress]}" onto public java.lang.String org.itstack.demo.netty.web.NettyController.localAddress()
2019-08-10 14:14:54.803  INFO 5976 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error]}" onto public org.springframework.http.ResponseEntity<java.util.Map<java.lang.String, java.lang.Object>> org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.error(javax.servlet.http.HttpServletRequest)
2019-08-10 14:14:54.804  INFO 5976 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error],produces=[text/html]}" onto public org.springframework.web.servlet.ModelAndView org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.errorHtml(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)
2019-08-10 14:14:54.822  INFO 5976 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/webjars/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2019-08-10 14:14:54.822  INFO 5976 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2019-08-10 14:14:54.985  INFO 5976 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2019-08-10 14:14:55.013  INFO 5976 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-08-10 14:14:55.016  INFO 5976 --- [           main] org.itstack.demo.netty.Application       : Started Application in 5.982 seconds (JVM running for 6.516)
2019-08-10 14:14:55.043  INFO 5976 --- [           main] o.itstack.demo.netty.server.NettyServer  : itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}
```

![启动StringBoot](https://bugstack.cn/assets/images/pic-content/2019/08/netty-2-06-2.png)

>启动Netty客户端发送数据 ApiTest.main

```java
itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}
14:16:33.543 [main] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.maxCapacityPerThread: 4096
14:16:33.543 [main] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.maxSharedCapacityFactor: 2
14:16:33.543 [main] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.linkCapacity: 16
14:16:33.543 [main] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.ratio: 8
14:16:33.555 [nioEventLoopGroup-2-1] DEBUG io.netty.buffer.AbstractByteBuf - -Dio.netty.buffer.checkAccessible: true
14:16:33.555 [nioEventLoopGroup-2-1] DEBUG io.netty.buffer.AbstractByteBuf - -Dio.netty.buffer.checkBounds: true
14:16:33.556 [nioEventLoopGroup-2-1] DEBUG io.netty.util.ResourceLeakDetectorFactory - Loaded default ResourceLeakDetector: io.netty.util.ResourceLeakDetector@529fc511
```

>执行结果 *数据已经写入到Elasticsearch

![启动StringBoot](https://bugstack.cn/assets/images/pic-content/2019/08/netty-2-06-3.png)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！