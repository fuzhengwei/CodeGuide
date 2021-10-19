---
layout: post
category: itstack-demo-netty-1
title: netty案例，netty4.1基础入门篇七《嗨！NettyClient》
tagline: by 付政委
tag: [netty,itstack-demo-netty-1]
lock: need
---

# netty案例，netty4.1基础入门篇七《嗨！NettyClient》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
在前六章的案例中使用socket模拟器链接我们的NettyServer，进行通信测试。本章节我们写一个helloworld版的NettyClient客户端，与我们的socket模拟器进行通信。在netty中客户端与服务端的写法基本类似，注意一些细节即可，这也是netty的强大之处，它把nio流与sokcet封装的相当简单易用。
## 开发环境
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
3. telnet 测试【可以现在你的win7机器上测试这个命令，用于链接到服务端的测试命令】
## 代码示例
```java
itstack-demo-netty-1-07
└── src
    ├── main
    │   └── java
    │       └── org.itstack.demo.netty.client
    │           ├── MyChannelInitializer.java
    │           └── NettyClient.java
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
    protected void initChannel(SocketChannel channel) throws Exception {
        System.out.println("链接报告开始");
        System.out.println("链接报告信息：本客户端链接到服务端。channelId：" + channel.id());
        System.out.println("链接报告完毕");
    }

}
```
>NettyClient.java

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
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
```
## 测试结果
>启动模拟器NetAssist 设置TCP Server

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-1-07-1.png)

>启动客户端NettyClient

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-1-07-2.png)

>执行结果

```java
链接报告开始
链接报告信息：本客户端链接到服务端。channelId：ea1df0b3
链接报告完毕
itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}

Process finished with exit code -1
```

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！

