---
layout: post
category: itstack-demo-netty-1
title: netty案例，netty4.1基础入门篇十二《简单实现一个基于Netty搭建的Http服务》
tagline: by 付政委
tag: [netty,itstack-demo-netty-1]
lock: need
---

# netty案例，netty4.1基础入门篇十二《简单实现一个基于Netty搭建的Http服务》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
Netty不仅可以搭建Socket服务，也可以搭建Http、Https服务。本章节我们通过一个简单的入门案例，来了解Netty搭建的Http服务，在我们后续的Netty网关服务中会使用到这样的功能点。

超文本传输协议（HTTP，HyperText Transfer Protocol)是互联网上应用最为广泛的一种网络协议。
>在后端开发中接触HTTP协议的比较多，目前大部分都是基于Servlet容器实现的Http服务，往往有一些核心子系统对性能的要求非常高，这个时候我们可以考虑采用NIO的网络模型来实现HTTP服务，以此提高性能和吞吐量，Netty除了开发网络应用非常方便，还内置了HTTP相关的编解码器，让用户可以很方便的开发出高性能的HTTP协议的服务，Spring Webflux默认是使用的Netty。

## 环境准备
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
3. Postman接口调试器，可以从网上下载也可以联系我，微信公众号：bugstack虫洞栈 | 关注回复你的邮箱

## 代码示例

```java
itstack-demo-netty-1-12
└── src
    ├── main
    │   └── java
    │       └── org.itstack.demo.netty
    │           └── server    	
    │               ├── MyChannelInitializer.java
    │               ├── MyClientHandler.java
    │               └── NettyServer.java
    └── test
         └── java
             └── org.itstack.demo.netty.test
                 └── ApiTest.java
```

>server/MyChannelInitializer.java | 添加了Http的处理协议

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        // 数据解码操作
        channel.pipeline().addLast(new HttpResponseEncoder());
        // 数据编码操作
        channel.pipeline().addLast(new HttpRequestDecoder());
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyServerHandler());
    }

}
```

>server/MyClientHandler.java | 模版代码基本没变，和其他的代码类似

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof HttpRequest) {
            DefaultHttpRequest request = (DefaultHttpRequest) msg;
            System.out.println("URI:" + request.getUri());
            System.err.println(msg);
        }

        if (msg instanceof HttpContent) {
            LastHttpContent httpContent = (LastHttpContent) msg;
            ByteBuf byteData = httpContent.content();
            if (!(byteData instanceof EmptyByteBuf)) {
                //接收msg消息
                byte[] msgByte = new byte[byteData.readableBytes()];
                byteData.readBytes(msgByte);
                System.out.println(new String(msgByte, Charset.forName("UTF-8")));
            }
        }

        String sendMsg = "微信公众号：bugstack虫洞栈，欢迎您的关注&获取源码！不平凡的岁月终究来自你每日不停歇的刻苦拼搏，每一次真正成长都因看清脚下路而抉择出的生活。愿你我；承遇朝霞，年少正恰，整装戎马，刻印风华。\n" +
                "博客栈：https://bugstack.cn\n" +
                "内容介绍：本公众号会每天定时推送科技资料；专题、源码、书籍、视频、咨询、面试、环境等方面内容。尤其在技术专题方面会提供更多的原创内容，让更多的程序员可以从最基础开始了解到技术全貌，目前已经对外提供的有；《手写RPC框架》、《用Java实现JVM》、《基于JavaAgent的全链路监控》、《Netty案例》等专题。\n" +
                "获取源码：\n" +
                "关注｛bugstack虫洞栈｝公众号获取源码，回复<用Java实现jvm源码>\n" +
                "关注｛bugstack虫洞栈｝公众号获取源码，回复<netty源码>\n" +
                "关注｛bugstack虫洞栈｝公众号获取源码，回复<rpc源码>\n" +
                "关注｛bugstack虫洞栈｝公众号获取源码，回复<基于JavaAgent的全链路监控>";

        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(sendMsg.getBytes(Charset.forName("UTF-8"))));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        ctx.write(response);
        ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }

}
```

>server/NettyServer.java | 模版代码基本没变，和其他的代码类似

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class NettyServer {

    public static void main(String[] args) {
        new NettyServer().bing(7397);
    }

    private void bing(int port) {
        //配置服务端NIO线程组
        EventLoopGroup parentGroup = new NioEventLoopGroup(); //NioEventLoopGroup extends MultithreadEventLoopGroup Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)    //非阻塞模式
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new MyChannelInitializer());
            ChannelFuture f = b.bind(port).sync();
            System.out.println("itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            childGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
        }

    }

}
```

## 测试结果

>启动NettyServer

```java
itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}
URI:/
DefaultHttpRequest(decodeResult: success, version: HTTP/1.1)
GET / HTTP/1.1
cache-control: no-cache
Postman-Token: 28f37dfb-bb5a-4cb2-ae7a-87cf6cda900c
User-Agent: PostmanRuntime/7.6.0
Accept: */*
Host: localhost:7397
accept-encoding: gzip, deflate
content-type: multipart/form-data; boundary=--------------------------359056973355668947377349
content-length: 226
Connection: keep-alive
----------------------------359056973355668947377349
Content-Disposition: form-data; name="msg"

微信公众号：bugstack虫洞栈 | 欢迎关注并获取源码！
----------------------------359056973355668947377349--


Process finished with exit code -1
```

>Postman访问；http://localhost:7397 并设置参数

![](https://bugstack.cn/assets/images/pic-content/2019/09/netty-1-12-1.png)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！