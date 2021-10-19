---
layout: post
category: itstack-demo-netty-1
title: netty案例，netty4.1基础入门篇十《关于ChannelOutboundHandlerAdapter简单使用》
tagline: by 付政委
tag: [netty,itstack-demo-netty-1]
lock: need
---

## 前言介绍
ChannelOutboundHandlerAdapter与ChannelInboundHandlerAdapter都是继承于ChannelHandler，并实现自己的ChannelXxxHandler。用于在消息管道中不同时机下处理处理消息。

>ChannelInboundHandler拦截和处理入站事件，ChannelOutboundHandler拦截和处理出站事件。ChannelHandler和ChannelHandlerContext通过组合或继承的方式关联到一起成对使用。事件通过ChannelHandlerContext主动调用如read(msg)、write(msg)和fireXXX()等方法，将事件传播到下一个处理器。注意：入站事件在ChannelPipeline双向链表中由头到尾正向传播，出站事件则方向相反。
当客户端连接到服务器时，Netty新建一个ChannelPipeline处理其中的事件，而一个ChannelPipeline中含有若干ChannelHandler。如果每个客户端连接都新建一个ChannelHandler实例，当有大量客户端时，服务器将保存大量的ChannelHandler实例。为此，Netty提供了Sharable注解，如果一个ChannelHandler状态无关，那么可将其标注为Sharable，如此，服务器只需保存一个实例就能处理所有客户端的事件。

------------


#### ** ChannelHandler类图 **
![ChannelHandler类图](https://bugstack.cn/assets/images/pic-content/2019/08/ChannelHandler类图.png)

## 环境准备
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】

## 代码示例
```java
itstack-demo-netty-1-10
└── src
    ├── main
    │   └── java
    │       └── org.itstack.demo.netty
    │			├── client
    │           │ 	├── MyChannelInitializer.java
    │           │ 	├── MyInMsgHandler.java
    │           │ 	├── MyOutMsgHandler.java
    │           │ 	└── NettyClient.java
    │			└── server
    │           	├── MyChannelInitializer.java
    │           	├── MyServerHandler.java
    │           	└── NettyServer.java
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```

>client/MyChannelInitializer.java | 添加Handler执行器

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        // 基于换行符号
        channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        channel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        channel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyOutMsgHandler());//消息出站处理器，在Client发送消息时候会触发此处理器
        channel.pipeline().addLast(new MyInMsgHandler()); //消息入站处理器
    }

}

```

>client/MyInMsgHandler.java | 继承于ChannelInboundHandlerAdapter

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyInMsgHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("链接报告开始");
        System.out.println("链接报告信息：本客户端链接到服务端。channelId：" + channel.id());
        System.out.println("链接报告IP:" + channel.localAddress().getHostString());
        System.out.println("链接报告Port:" + channel.localAddress().getPort());
        System.out.println("链接报告完毕");
        //通知客户端链接建立成功
        String str = "通知服务端链接建立成功" + " " + new Date() + " " + channel.localAddress().getHostString() + "\r\n";
        ctx.writeAndFlush(str);
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("断开链接" + ctx.channel().localAddress().toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 客户端接收到消息：" + msg);
        //通知客户端链消息发送成功
        String str = "随机数：" + Math.random() * 10 + "\r\n";
        ctx.writeAndFlush(str);
    }

    /**
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }

}
```

>client/MyOutMsgHandler.java | 处理出站信息，read与write方法，其他方法可以自行添加验证

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyOutMsgHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("ChannelOutboundHandlerAdapter.read 发来一条消息\r\n");
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.writeAndFlush("ChannelOutboundHandlerAdapter.write 发来一条消息\r\n");
        super.write(ctx, msg, promise);
    }

}
```

>server/MyServerHandler.java 

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("链接报告开始");
        System.out.println("链接报告信息：有一客户端链接到本服务端");
        System.out.println("链接报告IP:" + channel.localAddress().getHostString());
        System.out.println("链接报告Port:" + channel.localAddress().getPort());
        System.out.println("链接报告完毕");
        //通知客户端链接建立成功
        String str = "通知客户端链接建立成功" + " " + new Date() + " " + channel.localAddress().getHostString() + "\r\n";
        //ctx.writeAndFlush(str);
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开链接" + ctx.channel().localAddress().toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 服务端接收到消息：" + msg);
        //通知客户端链消息发送成功
        String str = "随机数：" + Math.random() * 10 + "\r\n";
        //ctx.writeAndFlush(str);
    }

    /**
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }

}
```

## 测试结果

>启动NettyServer

```java
itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}
链接报告开始
链接报告信息：有一客户端链接到本服务端
链接报告IP:127.0.0.1
链接报告Port:7397
链接报告完毕
2019-08-25 13:38:34 服务端接收到消息：ChannelOutboundHandlerAdapter.write 发来一条消息
2019-08-25 13:38:34 服务端接收到消息：通知服务端链接建立成功 Tue Aug 27 13:38:34 CST 2019 127.0.0.1
2019-08-25 13:38:34 服务端接收到消息：ChannelOutboundHandlerAdapter.read 发来一条消息

Process finished with exit code -1
```

>启动NettyClient

```java
链接报告开始
itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}
链接报告信息：本客户端链接到服务端。channelId：47e1b185
链接报告IP:127.0.0.1
链接报告Port:63884
链接报告完毕
异常信息：
远程主机强迫关闭了一个现有的连接。
断开链接/127.0.0.1:63884

Process finished with exit code -1

```

------------

上一篇：[netty案例，netty4.1基础入门篇九《自定义编码解码器，处理半包、粘包数据》](/itstack-demo-netty-1/2019/08/12/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E4%B9%9D-%E8%87%AA%E5%AE%9A%E4%B9%89%E7%BC%96%E7%A0%81%E8%A7%A3%E7%A0%81%E5%99%A8-%E5%A4%84%E7%90%86%E5%8D%8A%E5%8C%85-%E7%B2%98%E5%8C%85%E6%95%B0%E6%8D%AE.html)

下一篇：[netty案例，netty4.1基础入门篇十一《netty udp通信方式案例Demo》](/itstack-demo-netty-1/2019/08/14/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E5%8D%81%E4%B8%80-netty-udp%E9%80%9A%E4%BF%A1%E6%96%B9%E5%BC%8F%E6%A1%88%E4%BE%8BDemo.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！
