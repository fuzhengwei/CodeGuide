---
layout: post
category: itstack-demo-netty-2
title: netty案例，netty4.1中级拓展篇二《Netty使用Protobuf传输数据》
tagline: by 付政委
tag: [netty,itstack-demo-netty-2] 
lock: need
---

## 前言介绍
在netty数据传输过程中可以有很多选择，比如；字符串、json、xml、java对象，但为了保证传输的数据具备；良好的通用性、方便的操作性和传输的高性能，我们可以选择protobuf作为我们的数据传输格式。目前protobuf可以支持；C++、C#、Dart、Go、Java、Python等，也可以在JS里使用。知识点；ProtobufDecoder、ProtobufEncoder、ProtobufVarint32FrameDecoder、ProtobufVarint32LengthFieldPrepender。
>What are protocol buffers?
Protocol buffers are Google's language-neutral, platform-neutral, extensible mechanism for serializing structured data – think XML, but smaller, faster, and simpler. You define how you want your data to be structured once, then you can use special generated source code to easily write and read your structured data to and from a variety of data streams and using a variety of languages. [https://developers.google.cn/protocol-buffers](https://developers.google.cn/protocol-buffers)

## 开发环境
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
3. protoc-3.5.0-win32 【用于编译proto文件(protoc -I=源地址 --java_out=目标地址  源地址/xxx.proto)，源码中已经提供，如果是其他开发环境可以自行下载】

## 代码示例
```java
itstack-demo-netty-2-02
└── src
    ├── main
    │   └── java
    │       └── org.itstack.demo.netty
    │           ├── client
    │           │	├── MyChannelInitializer.java
    │           │	├── MyClientHandler.java
    │           │	└── NettyClient.java
    │           ├── domain
    │           │	├── MsgBody.java
    │           │	├── MsgBodyOrBuilder.java
    │           │	└── MsgInfo.java
    │           ├── proto
    │           │	└── MsgInfo.proto
    │           ├── server
    │           │	├── MyChannelInitializer.java
    │           │	├── MyServerHandler.java
    │           │	└── NettyServer.java
    │           └── util
    │           	└── MsgUtil.java
    │
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```

>client/MyChannelInitializer.java

```java
**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //protobuf 处理
        channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
        channel.pipeline().addLast(new ProtobufDecoder(MsgBody.getDefaultInstance()));
        channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
        channel.pipeline().addLast(new ProtobufEncoder());
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyClientHandler());
    }

}
```
>client/MyClientHandler.java 

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyClientHandler extends ChannelInboundHandlerAdapter {

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
        String str = "通知服务端链接建立成功" + " " + new Date() + " " + channel.localAddress().getHostString();
        ctx.writeAndFlush(MsgUtil.buildMsg(channel.id().toString(), str));
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
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息类型：" + msg.getClass());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息内容：" + JsonFormat.printToString((MsgBody) msg));
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
>client/NettyClient.java 

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class NettyClient {

    public static void main(String[] args) {
        new NettyClient().connect("127.0.0.1", 7397);
    }

    private void connect(String inetHost, int inetPort) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new MyChannelInitializer());
            ChannelFuture f = b.connect(inetHost, inetPort).sync();
            System.out.println("itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}");

            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"));

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
```
>proto/MsgInfo.proto 

```java
syntax = "proto3";

package org.itstack.demo.netty.domain;

option java_package = "org.itstack.demo.netty.domain";
option java_multiple_files = true;
option java_outer_classname = "MsgInfo";

message MsgBody {

    string channelId = 1;
    string msgInfo = 2;

}
```
>server/MyChannelInitializer.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        //protobuf 处理
        channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
        channel.pipeline().addLast(new ProtobufDecoder(MsgBody.getDefaultInstance()));
        channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
        channel.pipeline().addLast(new ProtobufEncoder());
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyServerHandler());
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
        System.out.println("链接报告信息：有一客户端链接到本服务端。channelId：" + channel.id());
        System.out.println("链接报告IP:" + channel.localAddress().getHostString());
        System.out.println("链接报告Port:" + channel.localAddress().getPort());
        System.out.println("链接报告完毕");
        //通知客户端链接建立成功
        String str = "通知客户端链接建立成功" + " " + new Date() + " " + channel.localAddress().getHostString() + "\r\n";
        ctx.writeAndFlush(MsgUtil.buildMsg(channel.id().toString(), str));
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
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息类型：" + msg.getClass());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息内容：" + JsonFormat.printToString((MsgBody) msg));
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
>server/NettyServer.java

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
>util/MsgUtil.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MsgUtil {

    /**
     * 构建protobuf消息体
     */
    public static MsgBody buildMsg(String channelId, String msgInfo) {
        MsgBody.Builder msg = MsgBody.newBuilder();
        msg.setChannelId(channelId);
        msg.setMsgInfo(msgInfo);
        return msg.build();
    }

}
```
>ApiTest.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class ApiTest {

    public static void main(String[] args) throws JsonFormat.ParseException {
        MsgBody.Builder msg = MsgBody.newBuilder();
        msg.setChannelId("abD01223");
        msg.setMsgInfo("hi helloworld");
        MsgBody msgBody = msg.build();

        //protobuf转Json 需要引入protobuf-java-format
        String msgBodyStr = JsonFormat.printToString(msgBody);
        System.out.println(msgBodyStr);

        //json转protobuf 需要引入protobuf-java-format
        JsonFormat.merge("{\"channelId\": \"HBdhi993\",\"msgInfo\": \"hi bugstack虫洞栈\"}", msg);
        msgBody = msg.build();
        System.out.println(msgBody.getChannelId());
        System.out.println(msgBody.getMsgInfo());

    }

}
```

## 测试结果
>编译proto *idea的Terminal下执行编译命令

操作：idea选中E:\itstack\GIT\itstack.org\itstack-demo-netty\itstack-demo-netty-2-02\protoc-3.5.0-win32\bin
命令：protoc -I=源地址 --java_out=目标地址  源地址/xxx.proto
```java
E:\itstack\GIT\itstack.org\itstack-demo-netty\itstack-demo-netty-2-02\protoc-3.5.0-win32\bin>protoc.exe -I=E:\itstack\GIT\itstack.org\itstack-demo-netty\itstack-demo-netty-2-02\src\main\java\org\itstack\demo\netty\proto --java_out=E:\itstack\GIT\itstack.org\itstack-demo-netty\itstack-demo-netty-2-02\src\main
\java MsgInfo.proto
```
>启动NettyServer

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-2-02-1.png)

>启动NettyClient

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-2-02-2.png)

>服务端执行结果

```java
itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}
链接报告开始
链接报告信息：有一客户端链接到本服务端。channelId：807679da
链接报告IP:127.0.0.1
链接报告Port:7397
链接报告完毕
2019-08-04 14:06:01 接收到消息类型：class org.itstack.demo.netty.domain.MsgBody
2019-08-04 14:06:01 接收到消息内容：{"channelId": "abc14b89","msgInfo": "通知服务端链接建立成功 Sun Aug 04 14:06:01 CST 2019 127.0.0.1"}
2019-08-04 14:06:01 接收到消息类型：class org.itstack.demo.netty.domain.MsgBody
2019-08-04 14:06:01 接收到消息内容：{"channelId": "abc14b89","msgInfo": "你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"}
2019-08-04 14:06:01 接收到消息类型：class org.itstack.demo.netty.domain.MsgBody
2019-08-04 14:06:01 接收到消息内容：{"channelId": "abc14b89","msgInfo": "你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"}
2019-08-04 14:06:01 接收到消息类型：class org.itstack.demo.netty.domain.MsgBody
2019-08-04 14:06:01 接收到消息内容：{"channelId": "abc14b89","msgInfo": "你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"}
2019-08-04 14:06:01 接收到消息类型：class org.itstack.demo.netty.domain.MsgBody
2019-08-04 14:06:01 接收到消息内容：{"channelId": "abc14b89","msgInfo": "你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"}
2019-08-04 14:06:01 接收到消息类型：class org.itstack.demo.netty.domain.MsgBody
2019-08-04 14:06:01 接收到消息内容：{"channelId": "abc14b89","msgInfo": "你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"}
异常信息：
远程主机强迫关闭了一个现有的连接。
客户端断开链接/127.0.0.1:7397

Process finished with exit code -1

```

>客户端执行结果

```java
itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}
链接报告开始
链接报告信息：本客户端链接到服务端。channelId：abc14b89
链接报告IP:127.0.0.1
链接报告Port:51218
链接报告完毕
2019-08-04 14:06:01 接收到消息类型：class org.itstack.demo.netty.domain.MsgBody
2019-08-04 14:06:01 接收到消息内容：{"channelId": "807679da","msgInfo": "通知客户端链接建立成功 Sun Aug 04 14:06:01 CST 2019 127.0.0.1\r\n"}

Process finished with exit code -1
```

------------

上一篇：[netty案例，netty4.1中级拓展篇一《Netty与SpringBoot整合》](/itstack-demo-netty-2/2019/08/16/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E4%B8%80-Netty%E4%B8%8ESpringBoot%E6%95%B4%E5%90%88.html)

下一篇：[netty案例，netty4.1中级拓展篇三《Netty传输Java对象》](/itstack-demo-netty-2/2019/08/18/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E4%B8%89-Netty%E4%BC%A0%E8%BE%93Java%E5%AF%B9%E8%B1%A1.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！
