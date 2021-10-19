---
layout: post
category: itstack-demo-netty-1
title: netty案例，netty4.1基础入门篇一《嗨！NettyServer》
tagline: by 付政委
tag: [netty,itstack-demo-netty-1] 
lock: need
---

# netty案例，netty4.1基础入门篇一《嗨！NettyServer》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
凡是新知识都需要有个入门的案例，一个简单的输入输出就能解除你当前遇到的所有疑惑。不要总想着先学理论后学实战。【X东方还135学理论，246学实战，800个床位不锈钢】，本案例专题主要介绍netty4.1的使用。

## 开发环境
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
3. telnet 测试【可以现在你的win7机器上测试这个命令，用于链接到服务端的测试命令】

## 代码示例
```java
itstack-demo-netty-1-01
└── src
    ├── main
    │   └── java
    │       └── org.itstack.demo.netty.server
    │           ├── MyChannelInitializer.java
    │           └── NettyServer.java
    └── test
         └── java
             └── org.itstack.demo.netty.test
                 └── ApiTest.java
```

>NettyServer.java

```java
package org.itstack.demo.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 博  客：http://itstack.org
 * 论  坛：http://bugstack.cn
 * Create by fuzhengwei on 2019/7/20
 * 一个简单的Netty服务端示例
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
## 运行演示
1、启动server服务端
2、连接server服务端
![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-01-2.jpg)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！