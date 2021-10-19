---
layout: post
category: itstack-demo-netty-2
title: netty案例，netty4.1中级拓展篇五《基于Netty搭建WebSocket，模仿微信聊天页面》
tagline: by 付政委
tag: [netty,itstack-demo-netty-2]
lock: need
---

## 前言介绍
本章节我们模仿微信聊天页面，开发一个基于Netty搭建WebSocket通信案例。Netty的应用方面非常广；聊天、MQ、RPC、数据等等，在5G到来的时候更加需要大量数据传输，Netty的应用也会更加广阔。
1. 这个案例使用SpringBoot+Netty+WebSocket搭建功能。
2. 使用Netty提供的HttpServerCodec、HttpObjectAggregator、ChunkedWriteHandler进行编码解码处理。
3. 通信逻辑尽可能简化到只了解根本，便于后续个人应用及开发的拓展。
   1. 客户端链接成功后，向服务端发送请求获取个人信息，也可以拓展为登录请求。
   2. 获取个人信息后，就可以知道差异化展示消息到页面。
  
## 开发环境
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
3. jquery.min.js、jquery.serialize-object.min.js

## 代码示例
```java
itstack-demo-netty-2-05
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.demo.netty
    │   │       ├── domain
    │   │       │	├── ClientMsgProtocol.java
    │   │       │	└── ServerMsgProtocol.java
    │   │       ├── server
    │   │       │	├── MyChannelInitializer.java
    │   │       │	├── MyServerHandler.java
    │   │       │	└── NettyServer.java
    │   │       ├── util
    │   │       │	├── ChannelHandler.java
    │   │       │	└── MsgUtil.java
    │   │       └── web
    │   │       	├── NettyApplication.java
    │   │       	└── NettyController.java	
    │   └── resources	
    │   │   └── application.yml
    │   └── webapp	
    │       ├── res		
    │       └── WEB-INF
    │        	└── index.jsp	
    │
    └── test
         └── java
             └── org.itstack.demo.test
				 ├── ApiTest.java
				 ├── NettyClientTest.java
                 └── NettyServerTest.java
```

**演示部分重点代码块，完整代码下载关注公众号；bugstack虫洞栈**

>server/MyChannelInitializer.java *websocket处理协议 

```java
/**
 * 消息传输协议
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on @2019
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        channel.pipeline().addLast("http-codec", new HttpServerCodec());
        channel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
        channel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyServerHandler());
    }

}
```
>server/MyServerHandler.java *处理websocket消息信息 

```java
/**
 * 消息传输协议
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on @2019
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(MyServerHandler.class);

    private WebSocketServerHandshaker handshaker;

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
        ChannelHandler.channelGroup.add(ctx.channel());
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端断开链接{}", ctx.channel().localAddress().toString());
        ChannelHandler.channelGroup.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //http
        if (msg instanceof FullHttpRequest) {

            FullHttpRequest httpRequest = (FullHttpRequest) msg;

            if (!httpRequest.decoderResult().isSuccess()) {

                DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);

                // 返回应答给客户端
                if (httpResponse.status().code() != 200) {
                    ByteBuf buf = Unpooled.copiedBuffer(httpResponse.status().toString(), CharsetUtil.UTF_8);
                    httpResponse.content().writeBytes(buf);
                    buf.release();
                }

                // 如果是非Keep-Alive，关闭连接
                ChannelFuture f = ctx.channel().writeAndFlush(httpResponse);
                if (httpResponse.status().code() != 200) {
                    f.addListener(ChannelFutureListener.CLOSE);
                }

                return;
            }

            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws:/" + ctx.channel() + "/websocket", null, false);
            handshaker = wsFactory.newHandshaker(httpRequest);

            if (null == handshaker) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                handshaker.handshake(ctx.channel(), httpRequest);
            }

            return;
        }

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

            ClientMsgProtocol clientMsgProtocol = JSON.parseObject(request, ClientMsgProtocol.class);
            //1请求个人信息
            if (1 == clientMsgProtocol.getType()) {
                ctx.channel().writeAndFlush(MsgUtil.buildMsgOwner(ctx.channel().id().toString()));
                return;
            }
            //群发消息
            if (2 == clientMsgProtocol.getType()) {
                TextWebSocketFrame textWebSocketFrame = MsgUtil.buildMsgAll(ctx.channel().id().toString(), clientMsgProtocol.getMsgInfo());
                ChannelHandler.channelGroup.writeAndFlush(textWebSocketFrame);
            }

        }

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
>util/MsgUtil.java *消息构建工具类 

```java
/**
 * 消息传输协议
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on @2019
 */
public class MsgUtil {

    public static TextWebSocketFrame buildMsgAll(String channelId, String msgInfo) {
        //模拟头像
        int i = Math.abs(channelId.hashCode()) % 10;

        ServerMsgProtocol msg = new ServerMsgProtocol();
        msg.setType(2); //链接信息;1自发信息、2群发消息
        msg.setChannelId(channelId);
        msg.setUserHeadImg("head" + i + ".jpg");
        msg.setMsgInfo(msgInfo);

        return new TextWebSocketFrame(JSON.toJSONString(msg));
    }

    public static TextWebSocketFrame buildMsgOwner(String channelId) {
        ServerMsgProtocol msg = new ServerMsgProtocol();
        msg.setType(1); //链接信息;1链接信息、2消息信息
        msg.setChannelId(channelId);
        return new TextWebSocketFrame(JSON.toJSONString(msg));
    }

}
```
>resources/application.yml *基础配置信息，包括了；应用端口、netty服务端端口等

```xml
server:
  port: 8080

netty:
  host: 127.0.0.1
  port: 7397

spring:
  mvc:
    view:
      prefix: /WEB-INF/
      suffix: .jsp
```

>WEB-INF/index.jsp *模仿微信聊天界面

```html
<!-- left -->
	<div style="width:60px; height:667px; background-color:#2D2B2A; float:left;">
		<!-- 头像 -->
		<div style="width:35px; height:35px; margin:0 auto; margin-top:19px; margin-left:12px; float:left; border:1px solid #666666;border-radius:3px;-moz-border-radius:3px;">
			<img src="res/img/bugstack.png" width="35px" height="35px"/>
		</div>
		
		<!-- 聊天 -->
		<div style="width:28px; height:28px; margin:0 auto; margin-top:25px; margin-left:16px; float:left;">
			<img src="res/img/chat.png" width="28px" height="28px"/>
		</div>
		
		<!-- 好友 -->
		<div style="width:28px; height:28px; margin:0 auto; margin-top:20px; margin-left:16px; float:left;">
			<img src="res/img/friend.png" width="28px" height="28px"/>
		</div>
		
		<!-- 收藏 -->
		<div style="width:28px; height:28px; margin:0 auto; margin-top:20px; margin-left:16px; float:left;">
			<img src="res/img/collection.png" width="28px" height="28px"/>
		</div>
		
		<!-- 设置 -->
		<div style="width:20px; height:20px; margin:0 auto; margin-left:20px; float:left; position:absolute;bottom:0; margin-bottom:12px;">
			<img src="res/img/set.png" width="20px" height="20px"/>
		</div>
		
	</div>
	
	<!-- center -->
	<div style="width:250px; height:667px; background-color:#EBE9E8; float:left;">
		<div style=" background-image:url(res/img/search.png); background-repeat:no-repeat;margin:0 auto; margin-top:25px; padding-top:5px; padding-bottom:5px; width:210px; background-color:#DBD9D8;border-radius:3px;-moz-border-radius:3px; float:left; margin-left:20px; font-size:12px; color:#333333;text-indent:27px;">		
			http://bugstack.cn
		</div>
		
		<!-- friendList -->
		<div id="friendList" style="float:left; margin-top:5px;">
			<div style="width:250px; height:65px;">
				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head1.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">哪咤宝</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">付政委：[图片]</td>
					</tr>
				</table>
				
				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#C9C7C6;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head2.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">bugstack虫洞栈</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">北京程序猿-小白：netty开发..</td>
					</tr>
				</table>
				
				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head3.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">咸鱼江湖</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">背包冲：情人节没礼物，不存..</td>
					</tr>
				</table>				
				
				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head4.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">整条街最靓</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">公司-老板：[文件]下个Q的KPI</td>
					</tr>
				</table>	

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head5.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">Sniper</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">Sniper：雨后天晴写下，年华..</td>
					</tr>
				</table>	
				
				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head7.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">星星点灯照亮我的家门</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">王老板：不吹牛的说我家77套..</td>
					</tr>
				</table>	

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head8.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">詹姆斯·高斯林</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">詹姆斯·高斯林：我所说的都关..</td>
					</tr>
				</table>	

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head9.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">叮裤猫</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">叮裤猫：那还第一次见</td>
					</tr>
				</table>	
				
				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head10.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">背锅冲</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">背锅冲：大树说的，不让去。</td>
					</tr>
				</table>																
			</div>
		</div>
		
	</div>
		
		
    ... ...
```

## 测试结果

>启动SpringBoot *Netty会随着启动

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-2-05-0.png)


>打开网页websocket客户端；http://localhost:8080/index

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-2-05-1.png)

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-2-05-6.png)

>服务端执行结果

```java
 .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.0.4.RELEASE)

2019-08-04 19:44:59.132  INFO 9208 --- [           main] o.i.demo.netty.web.NettyApplication      : Starting NettyApplication on JRA1W11T0247 with PID 9208 (E:\itstack\GIT\itstack.org\itstack-demo-netty\itstack-demo-netty-2-05\target\classes started by fuzhengwei1 in E:\itstack\GIT\itstack.org\itstack-demo-netty\itstack-demo-netty-2-05)
2019-08-04 19:44:59.138  INFO 9208 --- [           main] o.i.demo.netty.web.NettyApplication      : No active profile set, falling back to default profiles: default
2019-08-04 19:44:59.437  INFO 9208 --- [           main] ConfigServletWebServerApplicationContext : Refreshing org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@32cc499f: startup date [Sun Aug 04 19:44:59 CST 2019]; root of context hierarchy
2019-08-04 19:45:00.702  INFO 9208 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2019-08-04 19:45:00.738  INFO 9208 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2019-08-04 19:45:00.738  INFO 9208 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet Engine: Apache Tomcat/8.5.32
2019-08-04 19:45:00.748  INFO 9208 --- [ost-startStop-1] o.a.catalina.core.AprLifecycleListener   : The APR based Apache Tomcat Native library which allows optimal performance in production environments was not found on the java.library.path: [C:\Program Files\Java\jdk1.8.0_45\bin;C:\windows\Sun\Java\bin;C:\windows\system32;C:\windows;C:\ProgramData\Oracle\Java\javapath;C:\Program Files (x86)\Common Files\NetSarang;C:\Program Files (x86)\Intel\iCLS Client\;C:\Program Files\Intel\iCLS Client\;C:\windows\system32;C:\windows;C:\windows\System32\Wbem;C:\windows\System32\WindowsPowerShell\v1.0\;C:\Program Files\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files\Intel\Intel(R) Management Engine Components\IPT;C:\Program Files (x86)\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files (x86)\Intel\Intel(R) Management Engine Components\IPT;C:\Program Files\Java\jdk1.8.0_45/bin;C:\Program Files\Java\jdk1.8.0_45/jre/bin;D:\Program Files\SlikSvn\bin;D:\Program Files\TortoiseSVN\bin;D:\Program Files (x86)\apache-maven-2.2.1\bin;D:\Program Files\TortoiseGit\bin;D:\Program Files\nodejs\;D:\Program Files (x86)\SSH Communications Security\SSH Secure Shell;C:\Users\fuzhengwei1\AppData\Roaming\npm;;.]
2019-08-04 19:45:00.985  INFO 9208 --- [ost-startStop-1] org.apache.jasper.servlet.TldScanner     : At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
2019-08-04 19:45:00.994  INFO 9208 --- [ost-startStop-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2019-08-04 19:45:00.997  INFO 9208 --- [ost-startStop-1] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1560 ms
2019-08-04 19:45:01.082  INFO 9208 --- [ost-startStop-1] o.s.b.w.servlet.ServletRegistrationBean  : Servlet dispatcherServlet mapped to [/]
2019-08-04 19:45:01.086  INFO 9208 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'characterEncodingFilter' to: [/*]
2019-08-04 19:45:01.086  INFO 9208 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'hiddenHttpMethodFilter' to: [/*]
2019-08-04 19:45:01.086  INFO 9208 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'httpPutFormContentFilter' to: [/*]
2019-08-04 19:45:01.086  INFO 9208 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'requestContextFilter' to: [/*]
2019-08-04 19:45:01.334  INFO 9208 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**/favicon.ico] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2019-08-04 19:45:01.497  INFO 9208 --- [           main] s.w.s.m.m.a.RequestMappingHandlerAdapter : Looking for @ControllerAdvice: org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@32cc499f: startup date [Sun Aug 04 19:44:59 CST 2019]; root of context hierarchy
2019-08-04 19:45:01.578  INFO 9208 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/index]}" onto public java.lang.String org.itstack.demo.netty.web.NettyController.index(org.springframework.ui.Model)
2019-08-04 19:45:01.581  INFO 9208 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error]}" onto public org.springframework.http.ResponseEntity<java.util.Map<java.lang.String, java.lang.Object>> org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.error(javax.servlet.http.HttpServletRequest)
2019-08-04 19:45:01.582  INFO 9208 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error],produces=[text/html]}" onto public org.springframework.web.servlet.ModelAndView org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.errorHtml(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)
2019-08-04 19:45:01.606  INFO 9208 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/webjars/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2019-08-04 19:45:01.606  INFO 9208 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2019-08-04 19:45:01.673  INFO 9208 --- [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page template: index
2019-08-04 19:45:01.771  INFO 9208 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2019-08-04 19:45:01.832  INFO 9208 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-08-04 19:45:01.836  INFO 9208 --- [           main] o.i.demo.netty.web.NettyApplication      : Started NettyApplication in 3.205 seconds (JVM running for 6.314)
2019-08-04 19:45:02.002  INFO 9208 --- [           main] o.itstack.demo.netty.server.NettyServer  : itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}
2019-08-04 19:45:04.850  INFO 9208 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring FrameworkServlet 'dispatcherServlet'
2019-08-04 19:45:04.850  INFO 9208 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : FrameworkServlet 'dispatcherServlet': initialization started
2019-08-04 19:45:04.867  INFO 9208 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : FrameworkServlet 'dispatcherServlet': initialization completed in 17 ms
2019-08-04 19:45:06.137  INFO 9208 --- [ntLoopGroup-3-1] o.i.demo.netty.server.MyServerHandler    : 链接报告开始
2019-08-04 19:45:06.137  INFO 9208 --- [ntLoopGroup-3-1] o.i.demo.netty.server.MyServerHandler    : 链接报告信息：有一客户端链接到本服务端
2019-08-04 19:45:06.137  INFO 9208 --- [ntLoopGroup-3-1] o.i.demo.netty.server.MyServerHandler    : 链接报告IP:127.0.0.1
2019-08-04 19:45:06.137  INFO 9208 --- [ntLoopGroup-3-1] o.i.demo.netty.server.MyServerHandler    : 链接报告Port:7397
2019-08-04 19:45:06.137  INFO 9208 --- [ntLoopGroup-3-1] o.i.demo.netty.server.MyServerHandler    : 链接报告完毕
服务端收到：{"type":1,"msgInfo":"请求个人信息"}
2019-08-04 19:45:10.590  INFO 9208 --- [ntLoopGroup-3-2] o.i.demo.netty.server.MyServerHandler    : 链接报告开始
2019-08-04 19:45:10.590  INFO 9208 --- [ntLoopGroup-3-2] o.i.demo.netty.server.MyServerHandler    : 链接报告信息：有一客户端链接到本服务端
2019-08-04 19:45:10.591  INFO 9208 --- [ntLoopGroup-3-2] o.i.demo.netty.server.MyServerHandler    : 链接报告IP:127.0.0.1
2019-08-04 19:45:10.591  INFO 9208 --- [ntLoopGroup-3-2] o.i.demo.netty.server.MyServerHandler    : 链接报告Port:7397
2019-08-04 19:45:10.591  INFO 9208 --- [ntLoopGroup-3-2] o.i.demo.netty.server.MyServerHandler    : 链接报告完毕
服务端收到：{"type":1,"msgInfo":"请求个人信息"}
2019-08-04 19:45:12.374  INFO 9208 --- [ntLoopGroup-3-3] o.i.demo.netty.server.MyServerHandler    : 链接报告开始
2019-08-04 19:45:12.374  INFO 9208 --- [ntLoopGroup-3-3] o.i.demo.netty.server.MyServerHandler    : 链接报告信息：有一客户端链接到本服务端
2019-08-04 19:45:12.374  INFO 9208 --- [ntLoopGroup-3-3] o.i.demo.netty.server.MyServerHandler    : 链接报告IP:127.0.0.1
2019-08-04 19:45:12.374  INFO 9208 --- [ntLoopGroup-3-3] o.i.demo.netty.server.MyServerHandler    : 链接报告Port:7397
2019-08-04 19:45:12.374  INFO 9208 --- [ntLoopGroup-3-3] o.i.demo.netty.server.MyServerHandler    : 链接报告完毕
服务端收到：{"type":1,"msgInfo":"请求个人信息"}
服务端收到：{"type":2,"msgInfo":"你好在吗，我是bugstack虫洞栈的作者，付政委。"}
服务端收到：{"type":2,"msgInfo":"我在的，我已经关注了这个公众号；bugstack虫洞栈，里面的很多知识都是干货，真的能帮助到我的学习，他还有博客网站；https://bugstack.cn 感谢作者！让我学习到这么多知识。"}
服务端收到：{"type":2,"msgInfo":"呀和！原来这么多人在群里。哈哈哈，大家一起学习真好。我的头像是随机的哦，你们的也是。像公告的信息一样；不平凡的岁月终究来自你每日不停歇的刻苦拼搏，犹如；承遇朝霞，年少正恰，整装戎马，刻印风华。"}

Process finished with exit code -1
```

------------

上一篇：[netty案例，netty4.1中级拓展篇四《Netty传输文件、分片发送、断点续传》](/itstack-demo-netty-2/2019/08/19/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%9B%9B-Netty%E4%BC%A0%E8%BE%93%E6%96%87%E4%BB%B6-%E5%88%86%E7%89%87%E5%8F%91%E9%80%81-%E6%96%AD%E7%82%B9%E7%BB%AD%E4%BC%A0.html)

下一篇：[netty案例，netty4.1中级拓展篇六《SpringBoot+Netty+Elasticsearch收集日志信息数据存储》](/itstack-demo-netty-2/2019/08/21/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%85%AD-SpringBoot+Netty+Elasticsearch%E6%94%B6%E9%9B%86%E6%97%A5%E5%BF%97%E4%BF%A1%E6%81%AF%E6%95%B0%E6%8D%AE%E5%AD%98%E5%82%A8.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！
