---
layout: post
category: itstack-demo-netty-1
title: netty案例，netty4.1基础入门篇六《NettyServer群发消息》
tagline: by 付政委
tag: [netty,itstack-demo-netty-1]
lock: need
---

# netty案例，netty4.1基础入门篇六《NettyServer群发消息》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
在微信或者QQ的聊天中我们经常会用到一些群聊，把你的信息发送给所有用户。那么为了实现群发消息，在netty中我们可以使用ChannelGroup方式进行群发消息。如果为了扩展验证比如你实际聊天有不同的群，那么可以定义ConcurrentHashMap结构来存放ChannelGroup。ChannelGroup中提供了一些基础的方法；添加、异常、查找、清空、发放消息、关闭等。
## 开发环境
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
3. telnet 测试【可以现在你的win7机器上测试这个命令，用于链接到服务端的测试命令】
## 代码示例
```java
itstack-demo-netty-1-06
└── src
    ├── main
    │   └── java
    │       └── org.itstack.demo.netty.server
    │           ├── ChannelHandler.java
    │           ├── MyChannelInitializer.java
    │           ├── MyServerHandler.java
    │           └── NettyServer.java
    └── test
         └── java
             └── org.itstack.demo.netty.test
                 └── ApiTest.java
```
>ChannelHandler.java

```java

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class ChannelHandler {

    //用于存放用户Channel信息，也可以建立map结构模拟不同的消息群
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

}
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

    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //当有客户端链接后，添加到channelGroup通信组
        ChannelHandler.channelGroup.add(ctx.channel());
        //日志信息
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("链接报告开始");
        System.out.println("链接报告信息：有一客户端链接到本服务端");
        System.out.println("链接报告IP:" + channel.localAddress().getHostString());
        System.out.println("链接报告Port:" + channel.localAddress().getPort());
        System.out.println("链接报告完毕");
        //通知客户端链接建立成功
        String str = "通知客户端链接建立成功" + " " + new Date() + " " + channel.localAddress().getHostString() + "\r\n";
        ctx.writeAndFlush(str);
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开链接" + ctx.channel().localAddress().toString());
        // 当有客户端退出后，从channelGroup中移除。
        ChannelHandler.channelGroup.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        //收到消息后，群发给客户端
        String str = "服务端收到：" + new Date() + " " + msg + "\r\n";
        ChannelHandler.channelGroup.writeAndFlush(str);
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
>NettyServer.java

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
## 测试结果
>启动服务端NettyServer

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-1-06-1.png)

>启动**2**模拟器NetAssist 互相发送数据测试

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-1-06.png)

>执行结果

```java
itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}
链接报告开始
链接报告信息：有一客户端链接到本服务端
链接报告IP:10.13.28.13
链接报告Port:7397
链接报告完毕
链接报告开始
链接报告信息：有一客户端链接到本服务端
链接报告IP:10.13.28.13
链接报告Port:7397
链接报告完毕
2019-08-06 09:20:33 接收到消息：你好，服务端；我是小A。我是<bugstack虫洞栈>公众号，关注我获取源码。“我的结尾是一个换行符，用于传输半包粘包处理”
2019-08-06 09:20:35 接收到消息：你好，服务端；我是小A。我是<bugstack虫洞栈>公众号，关注我获取源码。“我的结尾是一个换行符，用于传输半包粘包处理”
2019-08-06 09:20:37 接收到消息：你好，服务端；我是小B。我是<bugstack虫洞栈>公众号，关注我获取源码。"我的结尾是一个换行符，用于传输半包粘包处理"
2019-08-06 09:20:38 接收到消息：你好，服务端；我是小B。我是<bugstack虫洞栈>公众号，关注我获取源码。"我的结尾是一个换行符，用于传输半包粘包处理"
客户端断开链接/10.13.28.13:7397
客户端断开链接/10.13.28.13:7397
```

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！
