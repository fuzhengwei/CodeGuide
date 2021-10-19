---
layout: post
category: itstack-demo-netty-1
title: netty案例，netty4.1基础入门篇二《NettyServer接收数据》
tagline: by 付政委
tag: [netty,itstack-demo-netty-1]
lock: need
---

## 前言介绍
繁事都需要一个简单的入门的点，尤其学习程序员行业的知识最快的方式是先运行期helloworld，往往这样一个简单能运行的例子，就能解除你当前遇到的所有疑惑。切记，对于一个初学者，不建议上来就研究理论，实操往往更重要。本章节介绍使用netty端写一个能接收数据的socketServer服务端，通过实现通道适配器ChannelInboundHandlerAdapter.channelRead获取并并解析接收数据。

## 开发环境
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
3. telnet 测试【可以现在你的win7机器上测试这个命令，用于链接到服务端的测试命令】

## 代码示例
```java
itstack-demo-netty-1-02
└── src
    ├── main
    │   └── java
    │       └── org.itstack.demo.netty.server
    │           ├── MyChannelInitializer.java
    │           ├── MyServerHandler.java
    │           └── NettyServer.java
    └── test
         └── java
             └── org.itstack.demo.netty.test
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

        System.out.println("链接报告开始");
        System.out.println("链接报告信息：有一客户端链接到本服务端");
        System.out.println("链接报告IP:" + channel.localAddress().getHostString());
        System.out.println("链接报告Port:" + channel.localAddress().getPort());
        System.out.println("链接报告完毕");

        //在管道中添加我们自己的接收数据实现方法
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

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收msg消息
        ByteBuf buf = (ByteBuf) msg;
        byte[] msgByte = new byte[buf.readableBytes()];
        buf.readBytes(msgByte);
        System.out.print(new Date() + "接收到消息：");
        System.out.println(new String(msgByte, Charset.forName("GBK")));
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

![](https://bugstack.cn/assets/images/pic-content/2019/08/nettyserver02.png)

>启动模拟器NetAssist

![](https://bugstack.cn/assets/images/pic-content/2019/08/1.png)

>执行结果

```java
itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}
链接报告开始
链接报告信息：有一客户端链接到本服务端
链接报告IP:127.0.0.1
链接报告Port:7397
链接报告完毕
Mon Aug 05 14:15:32 CST 2019接收到消息：你好，服务端。我是<bugstack虫洞栈>公众号，关注我获取源码。“点发送数据，不需要回车换行”
Mon Aug 05 14:15:34 CST 2019接收到消息：你好，服务端。我是<bugstack虫洞栈>公众号，关注我获取源码。“点发送数据，不需要回车换行”
Mon Aug 05 14:15:34 CST 2019接收到消息：你好，服务端。我是<bugstack虫洞栈>公众号，关注我获取源码。“点发送数据，不需要回车换行”
Mon Aug 05 14:15:35 CST 2019接收到消息：你好，服务端。我是<bugstack虫洞栈>公众号，关注我获取源码。“点发送数据，不需要回车换行”

Process finished with exit code -1
```

------------

上一篇：[netty案例，netty4.1基础入门篇一《嗨！NettyServer》](/itstack-demo-netty-1/2019/07/30/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E9%9B%B6-%E5%88%9D%E5%85%A5JavaIO%E4%B9%8B%E9%97%A8BIO-NIO-AIO%E5%AE%9E%E6%88%98%E7%BB%83%E4%B9%A0.html)

下一篇：[netty案例，netty4.1基础入门篇三《NettyServer字符串解码器》](/itstack-demo-netty-1/2019/08/06/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E4%B8%89-NettyServer%E5%AD%97%E7%AC%A6%E4%B8%B2%E8%A7%A3%E7%A0%81%E5%99%A8.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！

