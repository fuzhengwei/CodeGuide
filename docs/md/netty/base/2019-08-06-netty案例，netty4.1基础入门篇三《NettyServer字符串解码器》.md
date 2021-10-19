---
layout: post
category: itstack-demo-netty-1
title: netty案例，netty4.1基础入门篇三《NettyServer字符串解码器》
tagline: by 付政委
tag: [netty,itstack-demo-netty-1]
lock: need
---

## 前言介绍
在实际开发中，server端接收数据后我们希望他是一个字符串或者是一个对象类型，而不是字节码，那么；
1. 在netty中是否可以自动的把接收的Bytebuf数据转String，不需要我手动处理？
答；有，可以在管道中添加一个StringDecoder。
2. 在网络传输过程中有半包粘包的问题，netty能解决吗？
答：能，netty提供了很丰富的解码器，在正确合理的使用下就能解决半包粘包问题。
3. 常用的String字符串下有什么样的解码器呢？
答：不仅在String下有处理半包粘包的解码器在处理其他的数据格式也有，其中谷歌的protobuf数据格式就是其中一个。对于String的有以下常用的三种：
    1. LineBasedFrameDecoder            基于换行
    2. DelimiterBasedFrameDecoder       基于指定字符串
    3. FixedLengthFrameDecoder          基于字符串长度
## 开发环境
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
3. telnet 测试【可以现在你的win7机器上测试这个命令，用于链接到服务端的测试命令】
## 代码示例
```java
itstack-demo-netty-1-03
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
        /* 解码器 */
        // 基于换行符号
        channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 基于指定字符串【换行符，这样功能等同于LineBasedFrameDecoder】
        // e.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, false, Delimiters.lineDelimiter()));
        // 基于最大长度
        // e.pipeline().addLast(new FixedLengthFrameDecoder(4));
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        channel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
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
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("链接报告开始 {公众号：bugstack虫洞栈 >获取学习源码}");
        System.out.println("链接报告信息：有一客户端链接到本服务端");
        System.out.println("链接报告IP:" + channel.localAddress().getHostString());
        System.out.println("链接报告Port:" + channel.localAddress().getPort());
        System.out.println("链接报告完毕");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
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

![](https://bugstack.cn/assets/images/pic-content/2019/08/nettyserver03.png)

>执行结果

```java
itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}
链接报告开始 {公众号：bugstack虫洞栈 >获取学习源码}
链接报告信息：有一客户端链接到本服务端
链接报告IP: 127.0.0.1
链接报告Port:7397
链接报告完毕
2019-08-05 15:23:13 接收到消息：你好，服务端。我是<bugstack虫洞栈>公众号，关注我获取源码。“我的结尾是一个换行符，用于传输半包粘包处理”
2019-08-05 15:23:15 接收到消息：你好，服务端。我是<bugstack虫洞栈>公众号，关注我获取源码。“我的结尾是一个换行符，用于传输半包粘包处理”
2019-08-05 15:23:15 接收到消息：你好，服务端。我是<bugstack虫洞栈>公众号，关注我获取源码。“我的结尾是一个换行符，用于传输半包粘包处理”
2019-08-05 15:23:16 接收到消息：你好，服务端。我是<bugstack虫洞栈>公众号，关注我获取源码。“我的结尾是一个换行符，用于传输半包粘包处理”
2019-08-05 15:23:16 接收到消息：你好，服务端。我是<bugstack虫洞栈>公众号，关注我获取源码。“我的结尾是一个换行符，用于传输半包粘包处理”
2019-08-05 15:23:17 接收到消息：你好，服务端。我是<bugstack虫洞栈>公众号，关注我获取源码。“我的结尾是一个换行符，用于传输半包粘包处理”

Process finished with exit code -1
```
------------

上一篇：[netty案例，netty4.1基础入门篇二《NettyServer接收数据》](/itstack-demo-netty-1/2019/08/05/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E4%BA%8C-NettyServer%E6%8E%A5%E6%94%B6%E6%95%B0%E6%8D%AE.html)

下一篇：[netty案例，netty4.1基础入门篇四《NettyServer收发数据》](/itstack-demo-netty-1/2019/08/07/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E5%9B%9B-NettyServer%E6%94%B6%E5%8F%91%E6%95%B0%E6%8D%AE.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！