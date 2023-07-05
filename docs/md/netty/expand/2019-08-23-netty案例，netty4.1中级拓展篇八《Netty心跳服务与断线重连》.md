---
layout: post
category: itstack-demo-netty-2
title: netty案例，netty4.1中级拓展篇八《Netty心跳服务与断线重连》
tagline: by 付政委
tag: [netty,itstack-demo-netty-2]
lock: need
---

# netty案例，netty4.1中级拓展篇八《Netty心跳服务与断线重连》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
在我们使用netty中，需要监测服务是否稳定以及在网络异常链接断开时候可以自动重连。需要实现监听；f.addListener(new MyChannelFutureListener())

## 环境准备
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】

## 代码示例
```java
itstack-demo-rpc-2-08
└── src
    └── main
    │    └── java
    │        └── org.itstack.demo.netty
    │             ├── client
    │             │   ├── MyChannelFutureListener.java
    │             │   ├── MyChannelInitializer.java
    │             │   ├── MyClientHandler.java
    │             │   └── NettyClient.java
    │             └── server
    │             	  ├── MyChannelInitializer.java
    │             	  ├── MyServerHandler.java
    │             	  └── NettyServer.java
    └── test
         └── java
             └── org.itstack.demo.test
                 ├── StartClient.java
                 └── StartServer.java
```

** 展示部分重要代码块，完整代码可以关注公众号获取；bugstack虫洞栈 **

>client/MyChannelFutureListener.java 

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyChannelFutureListener implements ChannelFutureListener {
    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (channelFuture.isSuccess()) {
            System.out.println("itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}");
            return;
        }
        final EventLoop loop = channelFuture.channel().eventLoop();
        loop.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    new NettyClient().connect("127.0.0.1", 7397);
                    System.out.println("itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}");
                    Thread.sleep(500);
                } catch (Exception e){
                    System.out.println("itstack-demo-netty client start error go reconnect ... {关注公众号：bugstack虫洞栈，获取源码}");
                }
            }
        }, 1L, TimeUnit.SECONDS);
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
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("断开链接重连" + ctx.channel().localAddress().toString());
        //使用过程中断线重连；实际场景需要关闭线程池，并且通过ping的方式进行检测
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new NettyClient().connect("127.0.0.1", 7397);
                    System.out.println("itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}");
                    Thread.sleep(500);
                } catch (Exception e){
                    System.out.println("itstack-demo-netty client start error go reconnect ... {关注公众号：bugstack虫洞栈，获取源码}");
                }
            }
        }).start();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
    }

    /**
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常信息，断开重连：\r\n" + cause.getMessage());
        ctx.close();
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

    public void connect(String inetHost, int inetPort) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new MyChannelInitializer());
            ChannelFuture f = b.connect(inetHost, inetPort).sync();
            f.addListener(new MyChannelFutureListener()); //添加监听，处理重连
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
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

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                System.out.println("bugstack虫洞栈提醒=> Reader Idle");
                ctx.writeAndFlush("读取等待：公众号bugstack虫洞栈，客户端你在吗[ctx.close()]{我结尾是一个换行符用于处理半包粘包}... ...\r\n");
                ctx.close();
            } else if (e.state() == IdleState.WRITER_IDLE) {
                System.out.println("bugstack虫洞栈提醒=> Write Idle");
                ctx.writeAndFlush("写入等待：公众号bugstack虫洞栈，客户端你在吗{我结尾是一个换行符用于处理半包粘包}... ...\r\n");
            } else if (e.state() == IdleState.ALL_IDLE) {
                System.out.println("bugstack虫洞栈提醒=> All_IDLE");
                ctx.writeAndFlush("全部时间：公众号bugstack虫洞栈，客户端你在吗{我结尾是一个换行符用于处理半包粘包}... ...\r\n");
            }
        }
        ctx.flush();
    }

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
        ctx.writeAndFlush(str);
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
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
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


## 测试结果

>启动NettyServer *在心跳中设置ctx.close();模拟断开链接，等待重连

```java
itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}
链接报告开始
链接报告信息：有一客户端链接到本服务端
链接报告IP:127.0.0.1
链接报告Port:7397
链接报告完毕
bugstack虫洞栈提醒=> Reader Idle
客户端断开链接/127.0.0.1:7397
链接报告开始
链接报告信息：有一客户端链接到本服务端
链接报告IP:127.0.0.1
链接报告Port:7397
链接报告完毕
bugstack虫洞栈提醒=> Reader Idle
客户端断开链接/127.0.0.1:7397
链接报告开始
链接报告信息：有一客户端链接到本服务端
链接报告IP:127.0.0.1
链接报告Port:7397
链接报告完毕
bugstack虫洞栈提醒=> Reader Idle
客户端断开链接/127.0.0.1:7397
链接报告开始
链接报告信息：有一客户端链接到本服务端
链接报告IP:127.0.0.1
链接报告Port:7397
链接报告完毕
异常信息：
远程主机强迫关闭了一个现有的连接。
客户端断开链接/127.0.0.1:7397

Process finished with exit code -1
```

>启动NettyClient

```java
链接报告开始
链接报告信息：本客户端链接到服务端。channelId：d9f3f045
链接报告IP:127.0.0.1
链接报告Port:53009
链接报告完毕
itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}
2019-08-18 16:49:28 接收到消息：通知客户端链接建立成功 Sun Aug 18 16:49:28 CST 2019 127.0.0.1
2019-08-18 16:49:30 接收到消息：读取等待：公众号bugstack虫洞栈，客户端你在吗[ctx.close()]{我结尾是一个换行符用于处理半包粘包}... ...
断开链接重连/127.0.0.1:53009
链接报告开始
链接报告信息：本客户端链接到服务端。channelId：23dc9235
链接报告IP:127.0.0.1
链接报告Port:53035
链接报告完毕
itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}
2019-08-18 16:49:30 接收到消息：通知客户端链接建立成功 Sun Aug 18 16:49:30 CST 2019 127.0.0.1
2019-08-18 16:49:32 接收到消息：读取等待：公众号bugstack虫洞栈，客户端你在吗[ctx.close()]{我结尾是一个换行符用于处理半包粘包}... ...
断开链接重连/127.0.0.1:53035
itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}
链接报告开始
链接报告信息：本客户端链接到服务端。channelId：9c186f92
链接报告IP:127.0.0.1
链接报告Port:53052
链接报告完毕
itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}
2019-08-18 16:49:32 接收到消息：通知客户端链接建立成功 Sun Aug 18 16:49:32 CST 2019 127.0.0.1
2019-08-18 16:49:34 接收到消息：读取等待：公众号bugstack虫洞栈，客户端你在吗[ctx.close()]{我结尾是一个换行符用于处理半包粘包}... ...
断开链接重连/127.0.0.1:53052
itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}
链接报告开始
链接报告信息：本客户端链接到服务端。channelId：46b1d56a
链接报告IP:127.0.0.1
链接报告Port:53069
链接报告完毕
itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}
2019-08-18 16:49:34 接收到消息：通知客户端链接建立成功 Sun Aug 18 16:49:34 CST 2019 127.0.0.1

Process finished with exit code -1
```

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！