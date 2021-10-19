---
layout: post
category: itstack-demo-netty-1
title: netty案例，netty4.1基础入门篇十一《netty udp通信方式案例Demo》
tagline: by 付政委
tag: [netty,itstack-demo-netty-1] 
lock: need
---

# netty案例，netty4.1基础入门篇十一《netty udp通信方式案例Demo》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
在Netty通信中UDP的实现方式也非常简单，只要注意部分代码区别于TCP即可。本章节需要注意的知识点 ；NioDatagramChannel、ChannelOption.SO_BROADCAST

>Internet 协议集支持一个无连接的传输协议，该协议称为用户数据报协议（UDP，User Datagram Protocol）。UDP 为应用程序提供了一种无需建立连接就可以发送封装的 IP 数据报的方法。RFC 768 [1]  描述了 UDP。
Internet 的传输层有两个主要协议，互为补充。无连接的是 UDP，它除了给应用程序发送数据包功能并允许它们在所需的层次上架构自己的协议之外，几乎没有做什么特别的的事情。面向连接的是 TCP，该协议几乎做了所有的事情。

## 环境准备
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
3. NetAssist 网络调试助手，可以从网上下载也可以联系我，微信公众号：bugstack虫洞栈 | 关注回复你的邮箱

## 代码示例

```java
itstack-demo-netty-1-11
└── src
    ├── main
    │   └── java
    │       └── org.itstack.demo.netty
    │           ├── client
    │           │   ├── MyChannelInitializer.java
    │           │   ├── MyClientHandler.java
    │           │   └── NettyClient.java
    │           └── server
    │               ├── MyChannelInitializer.java
    │               ├── MyServerHandler.java
    │               └── NettyServer.java
    └── test
         └── java
             └── org.itstack.demo.netty.test
                 └── ApiTest.java
```

>client/MyChannelInitializer.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on @2019
 */
public class MyChannelInitializer extends ChannelInitializer<NioDatagramChannel> {

    @Override
    protected void initChannel(NioDatagramChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        //pipeline.addLast("stringDecoder", new StringDecoder(Charset.forName("GBK")));
        pipeline.addLast(new MyClientHandler());
    }

}
```

>client/MyClientHandler.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on @2019
 */
public class MyClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    //接受服务端发送的内容
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        String msg = packet.content().toString(Charset.forName("GBK"));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " UDP客户端接收到消息：" + msg);
    }

}
```

>client/NettyClient.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on @2019
 */
public class NettyClient {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .handler(new MyChannelInitializer());
            Channel ch = b.bind(7398).sync().channel();
            //向目标端口发送信息
            ch.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer("你好端口7397的bugstack虫洞栈，我是客户端小爱，你在吗！", Charset.forName("GBK")),
                    new InetSocketAddress("127.0.0.1", 7397))).sync();
            ch.closeFuture().await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

}
```

>server/MyChannelInitializer.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on @2019
 */
public class MyChannelInitializer extends ChannelInitializer<NioDatagramChannel> {

    private EventLoopGroup group = new NioEventLoopGroup();

    @Override
    protected void initChannel(NioDatagramChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        //pipeline.addLast("stringDecoder", new StringDecoder(Charset.forName("GBK")));
        pipeline.addLast(group, new MyServerHandler());
    }

}
```

>server/MyServerHandler.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on @2019
 */
public class MyServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        String msg = packet.content().toString(Charset.forName("GBK"));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " UDP服务端接收到消息：" + msg);

        //向客户端发送消息
        String json = "微信公众号：bugstack虫洞栈，通知：我已经收到你的消息\r\n";
        // 由于数据报的数据是以字符数组传的形式存储的，所以传转数据
        byte[] bytes = json.getBytes(Charset.forName("GBK"));
        DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(bytes), packet.sender());
        ctx.writeAndFlush(data);//向客户端发送消息
    }

}
```

>server/NettyServer.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on @2019
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)    //广播
                    .option(ChannelOption.SO_RCVBUF, 2048 * 1024)// 设置UDP读缓冲区为2M
                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024)// 设置UDP写缓冲区为1M
                    .handler(new MyChannelInitializer());

            ChannelFuture f = b.bind(7397).sync();
            System.out.println("itstack-demo-netty udp server start done. {关注公众号：bugstack虫洞栈，获取源码}");
            f.channel().closeFuture().sync();
        } finally {
            //优雅的关闭释放内存
            group.shutdownGracefully();
        }

    }

}
```

## 测试结果

>启动NettyServer

```java
itstack-demo-netty udp server start done. {关注公众号：bugstack虫洞栈，获取源码}
2019-09-01 16:58:34 UDP服务端接收到消息：你好端口7397的bugstack虫洞栈，我是客户端小爱，你在吗！
2019-09-01 16:59:15 UDP服务端接收到消息：你好，有人在关注bugstack公众号，关注可以获得源码！
2019-09-01 16:59:15 UDP服务端接收到消息：你好，有人在关注bugstack公众号，关注可以获得源码！
2019-09-01 16:59:16 UDP服务端接收到消息：你好，有人在关注bugstack公众号，关注可以获得源码！
2019-09-01 16:59:17 UDP服务端接收到消息：你好，有人在关注bugstack公众号，关注可以获得源码！
2019-09-01 16:59:17 UDP服务端接收到消息：你好，有人在关注bugstack公众号，关注可以获得源码！
2019-09-01 16:59:18 UDP服务端接收到消息：你好，有人在关注bugstack公众号，关注可以获得源码！
2019-09-01 16:59:18 UDP服务端接收到消息：你好，有人在关注bugstack公众号，关注可以获得源码！
2019-09-01 16:59:19 UDP服务端接收到消息：你好，有人在关注bugstack公众号，关注可以获得源码！
2019-09-01 16:59:19 UDP服务端接收到消息：你好，有人在关注bugstack公众号，关注可以获得源码！

Process finished with exit code -1

```

>启动NettyClient

```java
2019-09-01 16:58:34 UDP客户端接收到消息：微信公众号：bugstack虫洞栈，通知：我已经收到你的消息


Process finished with exit code -1
```

>在启动一个网络调试助手，NetAssist | 这样方便我们验证

![](https://bugstack.cn/assets/images/pic-content/2019/09/netty-1-11-1.png)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！
