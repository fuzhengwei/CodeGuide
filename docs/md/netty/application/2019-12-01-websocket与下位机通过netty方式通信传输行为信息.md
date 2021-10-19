---
layout: post
category: itstack-demo-netty-3
title: websocket与下位机通过netty方式通信传输行为信息
tagline: by 付政委
tag: [netty,itstack-demo-netty-3] 
excerpt: 在物联网开发中，常常需要通过网页端来控制设备，包括；获取信息、执行操作、启动停止等，就像我们在手机上会控制家里的小米盒子、路由器、电饭煲或者在线养狗等一些设备一样。在这里所有的下层设备都可以通过socket通信链接到服务端，而用户一端在通过http链接或者websocket链接到服务端，通过发送和接收数据来做出相应的行为操作。
lock: need
---

>微信公众号：bugstack虫洞栈 沉淀、分享、成长，专注于原创专题案例，以最易学习编程的方式分享知识，让自己和他人都能有所收获。目前已完成的专题有；Netty4.x实战专题案例、用Java实现JVM、基于JavaAgent的全链路监控、手写RPC框架、架构设计专题案例[Ing]等。

## 前言介绍
在物联网开发中，常常需要通过网页端来控制设备，包括；获取信息、执行操作、启动停止等，就像我们在手机上会控制家里的小米盒子、路由器、电饭煲或者在线养狗等一些设备一样。在这里所有的下层设备都可以通过socket通信链接到服务端，而用户一端在通过http链接或者websocket链接到服务端，通过发送和接收数据来做出相应的行为操作。如下图；
![微信公众号：bugstack虫洞栈 & 执行流程](https://bugstack.cn/assets/images/pic-content/2019/09/netty-3-01.png)

## 案例目标
1. 本章节整合Springboot+Netty，通过部署nettySocket与webSocket两套服务端，来接收转发行为消息。
2. 客户端采用js链接websocket，用于接收服务端反馈与发送指令，用于获取下位机信息。
3. 在test中启动一个模拟下位机，用于反馈信息数据。在真实开发中下位机与服务端通信通常是定义好的字节码，需要自己编写解码器。

## 环境准备
1. jdk 1.8.0
2. IntelliJ IDEA Community Edition 2018.3.1 x64
3. Netty 4.1.36.Final

## 代码示例
```java
itstack-demo-netty-3-01
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.demo.ark
    │   │       ├── domain
    │   │       │	├── msgobj
    │   │       │	│   ├── Feedback.java
    │   │       │	│   ├── QueryInfoReq.java
    │   │       │	│   └── Text.java
    │   │       │	├── Device.java
    │   │       │	├── EasyResult.java	
    │   │       │	├── InfoProtocol.java
    │   │       │	└── ServerInfo.java	
    │   │       ├── server
    │   │       │	├── socket
    │   │       │	│   ├── MyChannelInitializer.java
    │   │       │	│   ├── MyServerHandler.java
    │   │       │	│   └── NettyServer.java	
    │   │       │	└── websocket
    │   │       │	    ├── MyWsChannelInitializer.java
    │   │       │	    ├── MyWsServerHandler.java
    │   │       │	    └── WsNettyServer.java
    │   │       ├── util
    │   │       │	├── CacheUtil.java
    │   │       │	├── MsgUtil.java
    │   │       │	└── MsgUtil.java
    │   │       ├── web
    │   │       │	└── NettyController.java	
    │   │       └── Application.java
    │   └── resources	
    │   │   └── application.yml
    │   └── webapp
    │       ├── arkWs
    │       │	├── js
    │       │	│   └── ws.js	
    │       │   └── arkWsControlCenter.html	
    │       ├── res		
    │       └── WEB-INF
    │        	└── index.jsp	
    │
    └── test
        └── java
            └── org.itstack.demo.test
                └── ApiTest.java
```

**演示部分重点代码块，完整代码下载关注公众号；bugstack虫洞栈，回复Netty案例**

>server/socket/MyServerHandler.java & socket数据处理

- 当有下位机链接服务端时，构建下位机信息，实际使用可以通过注册方式进行链接验证。
- 当收到下位机信息后转发到websocket端，使网页端收到下位机信息反馈

```java
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(MyServerHandler.class);

    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        String channelId = channel.id().toString();
        System.out.println("链接报告开始");
        System.out.println("链接报告信息：有一客户端链接到本服务端。channelId：" + channelId);
        System.out.println("链接报告IP:" + channel.localAddress().getHostString());
        System.out.println("链接报告Port:" + channel.localAddress().getPort());
        System.out.println("链接报告完毕");

        //构建设备信息｛下位机、中继器、IO板卡｝
        Device device = new Device();
        device.setChannelId(channelId);
        device.setNumber(UUID.randomUUID().toString());
        device.setIp(channel.remoteAddress().getHostString());
        device.setPort(channel.remoteAddress().getPort());
        device.setConnectTime(new Date());
        //添加设备信息
        deviceGroup.put(channelId, device);
        CacheUtil.cacheClientChannel.put(channelId, channel);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object objMsgJsonStr) throws Exception {
        //接收设备发来信息
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息内容：" + objMsgJsonStr);

        //转发消息到Ws
        CacheUtil.wsChannelGroup.writeAndFlush(new TextWebSocketFrame(objMsgJsonStr.toString()));
    }

}
```

>server/websocket/MyWsServerHandler.java & websocket数据处理

- websocket数据需要转换后使用，可以支持文本消息，本案例中使用json字符串，方便对象传输
- channelRead转发数据，当收到数据后发送给下位机，主要通过内容中channel控制

```java
public class MyWsServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

       ...

        //ws
        if (msg instanceof WebSocketFrame) {
            WebSocketFrame webSocketFrame = (WebSocketFrame) msg;
            //关闭请求
            if (webSocketFrame instanceof CloseWebSocketFrame) {
                handshaker.close(ctx.channel(), (CloseWebSocketFrame) webSocketFrame.retain());
                return;
            }
            //ping请求
            if (webSocketFrame instanceof PingWebSocketFrame) {
                ctx.channel().write(new PongWebSocketFrame(webSocketFrame.content().retain()));
                return;
            }
            //只支持文本格式，不支持二进制消息
            if (!(webSocketFrame instanceof TextWebSocketFrame)) {
                throw new Exception("仅支持文本格式");
            }

            String request = ((TextWebSocketFrame) webSocketFrame).text();
            System.out.println("服务端收到：" + request);
            InfoProtocol infoProtocol = JSON.parseObject(request, InfoProtocol.class);
            //socket转发消息
            String channelId = infoProtocol.getChannelId();
            Channel channel = CacheUtil.cacheClientChannel.get(channelId);
            if (null == channel) return;
            channel.writeAndFlush(MsgUtil.buildMsg(infoProtocol));

            //websocket消息反馈发送成功
            ctx.writeAndFlush(MsgUtil.buildWsMsgText(ctx.channel().id().toString(), "向下位机传达消息success！"));
        }

    }

}
```

>web/NettyController.java & 控制层方便获取服务端信息

- 控制层提供了查询服务列表、链接设备信息、以及主动触发信息发送
- 另外如果需要将服务端的启动关闭进行手动控制，可以在这里面提供方法供调用

```java
@Controller
public class NettyController {

    private Logger logger = LoggerFactory.getLogger(NettyController.class);

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/queryNettyServerList")
    @ResponseBody
    public Collection<ServerInfo> queryNettyServerList() {
        try {
            Collection<ServerInfo> serverInfos = CacheUtil.serverInfoMap.values();
            logger.info("查询服务端列表。{}", JSON.toJSONString(serverInfos));
            return serverInfos;
        } catch (Exception e) {
            logger.info("查询服务端列表失败。", e);
            return null;
        }
    }

    @RequestMapping("/queryDeviceList")
    @ResponseBody
    public Collection<Device> queryDeviceList() {
        try {
            Collection<Device> deviceInfos = CacheUtil.deviceGroup.values();
            logger.info("查询设备链接列表。{}", JSON.toJSONString(deviceInfos));
            return deviceInfos;
        } catch (Exception e) {
            logger.info("查询设备链接列表失败。", e);
            return null;
        }
    }

    @RequestMapping("/doSendMsg")
    @ResponseBody
    public EasyResult doSendMsg(String reqStr) {
        try {
            logger.info("向设备发送信息[可以通过另外一个Web服务调用本接口发送信息]：{}", reqStr);
            InfoProtocol infoProtocol = MsgUtil.getMsg(reqStr);
            String channelId = infoProtocol.getChannelId();
            Channel channel = CacheUtil.cacheClientChannel.get(channelId);
            channel.writeAndFlush(MsgUtil.buildMsg(infoProtocol));
            return EasyResult.buildSuccessResult();
        } catch (Exception e) {
            logger.error("向设备发送信息失败：{}", reqStr, e);
            return EasyResult.buildErrResult(e);
        }
    }

}
```

>Application.java & 启动层

- 通过继承CommandLineRunner方法，在服务就绪后启动socket服务
- 启动后需要循环验证是否启动完成

```java
@SpringBootApplication
@ComponentScan("org.itstack.demo.ark")
public class Application implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(Application.class);

    @Value("${netty.socket.port}")
    private int nettyServerPort;
    @Value("${netty.websocket.port}")
    private int nettyWsServerPort;
    //默认线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //启动NettyServer-socket
        logger.info("启动NettyServer服务，启动端口：{}", nettyServerPort);
        NettyServer nettyServer = new NettyServer(new InetSocketAddress(nettyServerPort));
        Future<Channel> future = executorService.submit(nettyServer);
        Channel channel = future.get();
        if (null == channel) {
            throw new RuntimeException("netty server-s open error channel is null");
        }
        while (!channel.isActive()) {
            logger.info("启动NettyServer服务，循环等待启动...");
            Thread.sleep(500);
        }
        logger.info("启动NettyServer服务，完成：{}", channel.localAddress());
        CacheUtil.serverInfoMap.put(nettyServerPort, new ServerInfo("NettySocket", NetUtil.getHost(), nettyServerPort, new Date()));

        //启动NettyServer-websocket
        logger.info("启动NettyWsServer服务，启动端口：{}", nettyWsServerPort);
        WsNettyServer wsNettyServer = new WsNettyServer(new InetSocketAddress(nettyWsServerPort));
        Future<Channel> wsFuture = executorService.submit(wsNettyServer);
        Channel wsChannel = wsFuture.get();
        if (null == wsChannel) {
            throw new RuntimeException("netty server-ws open error channel is null");
        }
        while (!wsChannel.isActive()) {
            logger.info("启动NettyWsServer服务，循环等待启动...");
            Thread.sleep(500);
        }
        logger.info("启动NettyWsServer服务，完成：{}", wsChannel.localAddress());
        CacheUtil.serverInfoMap.put(nettyServerPort, new ServerInfo("NettyWsSocket", NetUtil.getHost(), nettyServerPort, new Date()));
    }

}
```

>webapp/arkWs/js/ws.js & 链接websocket服务端

```java
socket = new WebSocket("ws://localhost:7398/websocket");

	socket.onmessage = function(event){

		var msg = JSON.parse(event.data);
		console.info(msg);
		
		$("#msgBox").val($("#msgBox").val() + event.data + "\r\n");

	};
```

## 案例测试
1. 分别启动如下内容；
   1. Application.java，Plugins/spring-boot/spring-boot:run
   2. ApiTest.java，右键启动模拟下位机
2. 打开服务端链接；http://localhost:8080/ http://localhost:8080/arkWs/arkWsControlCenter.html
   ![微信公众号：bugstack虫洞栈 & 服务端与监控](https://bugstack.cn/assets/images/pic-content/2019/09/netty-3-02.png)
3. 发送模拟信息，观察执行结果；
   ```java
    2019-12-01 15:11:49.965  INFO 7620 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring FrameworkServlet 'dispatcherServlet'
	2019-12-01 15:11:49.965  INFO 7620 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : FrameworkServlet 'dispatcherServlet': initialization started
	2019-12-01 15:11:49.980  INFO 7620 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : FrameworkServlet 'dispatcherServlet': initialization completed in 15 ms
	2019-12-01 15:11:51.157  INFO 7620 --- [nio-8080-exec-3] o.itstack.demo.ark.web.NettyController   : 查询设备链接列表。[{"channelId":"281d1279","connectTime":1575184302964,"ip":"127.0.0.1","number":"74de0967-c0b4-4426-a9d1-183feaff2acf","port":3972}]
	2019-12-01 15:11:51.162  INFO 7620 --- [io-8080-exec-10] o.itstack.demo.ark.web.NettyController   : 查询服务端列表。[{"ip":"10.13.70.50","openDate":1575184290501,"port":7397,"typeInfo":"NettyWsSocket"}]
	2019-12-01 15:11:58.476  INFO 7620 --- [ntLoopGroup-7-1] o.i.d.a.s.websocket.MyWsServerHandler    : 链接报告开始
	2019-12-01 15:11:58.476  INFO 7620 --- [ntLoopGroup-7-1] o.i.d.a.s.websocket.MyWsServerHandler    : 链接报告信息：有一客户端链接到本服务端
	2019-12-01 15:11:58.476  INFO 7620 --- [ntLoopGroup-7-1] o.i.d.a.s.websocket.MyWsServerHandler    : 链接报告IP:0:0:0:0:0:0:0:1
	2019-12-01 15:11:58.476  INFO 7620 --- [ntLoopGroup-7-1] o.i.d.a.s.websocket.MyWsServerHandler    : 链接报告Port:7398
	2019-12-01 15:11:58.476  INFO 7620 --- [ntLoopGroup-7-1] o.i.d.a.s.websocket.MyWsServerHandler    : 链接报告完毕
	服务端收到：{"channelId":"281d1279","msgType":2,"msgObj":{"stateType":"1"}}
	2019-12-01 15:12:05 接收到消息内容：{"msgObj":{"stateMsg":"温度信息：91.31334894176383°C","stateType":1,"channelId":"93c1120a"},"msgType":3,"channelId":"93c1120a"}
	服务端收到：{"channelId":"281d1279","msgType":2,"msgObj":{"stateType":"1"}}
	2019-12-01 15:12:05 接收到消息内容：{"msgObj":{"stateMsg":"温度信息：44.83794772946285°C","stateType":1,"channelId":"93c1120a"},"msgType":3,"channelId":"93c1120a"}
   ```

## 综上总结
1. 在使用springboot与netty结合，开发一个简便的服务端还是很方便的，而且在集合一些springcloud的服务，可以使项目工程更加完善。
2. 可以尝试做一些设备控制服务，在我们不在家的时候也可以通过一个h5链接控制家里的设备，比如快到家将热水器打开。
3. 本案例还偏向于模拟，在实际开发过程中还是会出现很多业务问题需要解决，尤其是服务端与下位机的通信，需要编写编码器与解码器。

------------

上一篇：[手写RPC框架第三章《RPC中间件》](/itstack-demo-netty-3/2019/09/03/%E6%89%8B%E5%86%99RPC%E6%A1%86%E6%9E%B6%E7%AC%AC%E4%B8%89%E7%AB%A0-RPC%E4%B8%AD%E9%97%B4%E4%BB%B6.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**netty案例**」获取本文源码&更多原创专题案例！