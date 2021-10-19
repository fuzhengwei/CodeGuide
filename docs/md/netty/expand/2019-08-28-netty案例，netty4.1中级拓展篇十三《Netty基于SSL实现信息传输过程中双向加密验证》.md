---
layout: post
category: itstack-demo-netty-2
title: netty案例，netty4.1中级拓展篇十三《Netty基于SSL实现信息传输过程中双向加密验证》
tagline: by 付政委
tag: [netty,itstack-demo-netty-2]
lock: need
---

# netty案例，netty4.1中级拓展篇十三《Netty基于SSL实现信息传输过程中双向加密验证》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
>SSL(Secure Sockets Layer 安全套接层),及其继任者传输层安全（Transport Layer Security，TLS）是为网络通信提供安全及数据完整性的一种安全协议。TLS与SSL在传输层对网络连接进行加密。

在实际通信过程中，如果不使用SSL那么信息就是明文传输，从而给非法分子一些可乘之机；
- 窃听风险[eavesdropping]：第三方可以获知通信内容。
- 篡改风险[tampering]：第三方可以修改通信内容。
- 冒充风险[pretending]：第三方可以冒充他人身份参与通信。

SSL/TLS协议就是为了解决这三大风险而设计的；
- 保密：在握手协议中定义了会话密钥后，所有的消息都被加密。
- 鉴别：可选的客户端认证，和强制的服务器端认证。
- 完整性：传送的消息包括消息完整性检查（使用MAC）。

那么本章节我们通过在netty的channHandler中添加SSL安全模块{sslContext.newHandler(channel.alloc())}，来实现加密传输的效果。

**测试注释掉客户端SSL安全模块：**
```java
io.netty.handler.ssl.NotSslRecordException: not an SSL/TLS record: cea2d0c5b9abd6dabac5a3ba627567737461636bb3e6b6b4d5bb207c20cda8d6aab7fecef1b6cbc1b4bdd3bda8c1a2b3c9b9a6204d6f6e205365702032332031333a35303a3535204353542032303139203132372e302e302e310d0a
```

**测试篡改服务端时间：**
```java
javax.net.ssl.SSLHandshakeException: General SSLEngine problem
```

## 开发环境
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
3. OpenSSL-Win64 可以按照自己的需要进行下载；[http://slproweb.com/products/Win32OpenSSL.html](http://slproweb.com/products/Win32OpenSSL.html)

## 生成证书 | 过程较长，耐心完成

![微信公众号：bustack虫洞栈 | 生成证书](https://bugstack.cn/assets/images/pic-content/2019/09/netty-2-13-2.png)

>1、安装OpenSSL

安装完成后D:\Program Files\OpenSSL-Win64\bin目录下，cnf文件复制到bin目录里，否则在操作工程中如果未指定路径，会报错；
[https://stackoverflow.com/questions/22906927/openssl-windows-error-in-req/27918971](https://stackoverflow.com/questions/22906927/openssl-windows-error-in-req/27918971)
![OpenSSL Windows: error in req](https://bugstack.cn/assets/images/pic-content/2019/09/netty-2-13-1.png)

>2、生成服务端和客户端私钥 | 命令中需要输入密码测试可以都输入123456

```java
openssl genrsa -des3 -out server.key 1024
openssl genrsa -des3 -out client.key 1024
```

>3、根据key生成csr文件 | -config openssl.cnf 默认在cnf文件夹，如果未复制出来，需要指定路径“D://..cnf//openssl.cnf”

```java
openssl req -new -key server.key -out server.csr -config openssl.cnf
openssl req -new -key client.key -out client.csr -config openssl.cnf
```

>4、根据ca证书server.csr、client.csr生成**x509**证书

```java
openssl x509 -req -days 3650 -in server.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out server.crt
openssl x509 -req -days 3650 -in client.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out client.crt
```

>5、将key文件进行**PKCS#8**编码

```java
openssl pkcs8 -topk8 -in server.key -out pkcs8_server.key -nocrypt
openssl pkcs8 -topk8 -in client.key -out pkcs8_client.key -nocrypt
```

>6、最终将bin文件夹下，如下文件复制出来；

```java
server端：ca.crt、server.crt、pkcs8_server.key
client端：ca.crt、client.crt、pkcs8_client.key
```

## 代码示例
```java
itstack-demo-netty-2-13
└── src
    ├── main
    │   └── java
    │       └── org.itstack.demo.netty
    │           ├── client
    │           │	├── MyChannelInitializer.java
    │           │	├── MyClientHandler.java
    │           │	└── NettyClient.java
    │           ├── server
    │           │	├── MyChannelInitializer.java
    │           │	├── MyServerHandler.java	
    │           │	└── NettyServer.java
    │           └── ssl
    │           	├── client
    │           	│	├── ca.crt
    │           	│	├── client.crt	
    │           	│	└── pkcs8_client.key	
    │           	└── server
    │           		├── ca.crt
    │           		├── pkcs8_server.key
    │           		└── server.crt
    │
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```

**以下重点代码块讲解，完整代码，关注公众号：bugstack虫洞栈 | 回复Netty源码获取**

### 客户端：

>client/NettyClient.java | 引入SSL认证

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class NettyClient {

    public static void main(String[] args) throws SSLException {
        new NettyClient().connect("127.0.0.1", 7398);
    }

    private void connect(String inetHost, int inetPort) throws SSLException {

        //引入SSL安全验证
        File certChainFile = new File("E:\\itstack\\GIT\\itstack.org\\itstack-demo-netty\\itstack-demo-netty-2-13\\src\\main\\java\\org\\itstack\\demo\\netty\\ssl\\client\\client.crt");
        File keyFile = new File("E:\\itstack\\GIT\\itstack.org\\itstack-demo-netty\\itstack-demo-netty-2-13\\src\\main\\java\\org\\itstack\\demo\\netty\\ssl\\client\\pkcs8_client.key");
        File rootFile = new File("E:\\itstack\\GIT\\itstack.org\\itstack-demo-netty\\itstack-demo-netty-2-13\\src\\main\\java\\org\\itstack\\demo\\netty\\ssl\\client\\ca.crt");
        SslContext sslCtx = SslContextBuilder.forClient().keyManager(certChainFile, keyFile).trustManager(rootFile).build();

        //配置客户端NIO线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new MyChannelInitializer(sslCtx));
            ChannelFuture f = b.connect(inetHost, inetPort).sync();
            System.out.println("itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈 | 获取专题源码}");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
```

>client/NettyClient.java | 添加SSL认证模块，测试过程中可以尝试注释掉

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    private SslContext sslContext;

    public MyChannelInitializer(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        // 添加SSL安全验证
        channel.pipeline().addLast(sslContext.newHandler(channel.alloc()));
        // 基于换行符号
        channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        channel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        channel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyClientHandler());
    }

}
```

### 服务端：

>server/NettyServer.java | 引入SSL安全验证

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class NettyServer {

    public static void main(String[] args) throws SSLException {
        new NettyServer().bing(7398);
    }

    private void bing(int port) throws SSLException {

        //引入SSL安全验证
        File certChainFile = new File("E:\\itstack\\GIT\\itstack.org\\itstack-demo-netty\\itstack-demo-netty-2-13\\src\\main\\java\\org\\itstack\\demo\\netty\\ssl\\server\\server.crt");
        File keyFile = new File("E:\\itstack\\GIT\\itstack.org\\itstack-demo-netty\\itstack-demo-netty-2-13\\src\\main\\java\\org\\itstack\\demo\\netty\\ssl\\server\\pkcs8_server.key");
        File rootFile = new File("E:\\itstack\\GIT\\itstack.org\\itstack-demo-netty\\itstack-demo-netty-2-13\\src\\main\\java\\org\\itstack\\demo\\netty\\ssl\\server\\ca.crt");
        SslContext sslCtx = SslContextBuilder.forServer(certChainFile, keyFile).trustManager(rootFile).clientAuth(ClientAuth.REQUIRE).build();

        //配置服务端NIO线程组
        EventLoopGroup parentGroup = new NioEventLoopGroup(1); //NioEventLoopGroup extends MultithreadEventLoopGroup Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)    //非阻塞模式
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new MyChannelInitializer(sslCtx));
            ChannelFuture f = b.bind(port).sync();
            System.out.println("itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈 | 获取专题源码}");
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

>server/NettyServer.java | 添加SSL认证模块

```java
**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    private SslContext sslContext;

    public MyChannelInitializer(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(SocketChannel channel) {
        // 添加SSL安装验证
        channel.pipeline().addLast(sslContext.newHandler(channel.alloc()));
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

## 测试结果

>启动服务端NettyServer

```java
itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈 | 获取专题源码}
链接报告开始
链接报告信息：有一客户端链接到本服务端
链接报告IP:127.0.0.1
链接报告Port:7399
链接报告完毕
2019-09-21 15:11:50 接收到消息：微信公众号：bugstack虫洞栈 | 通知服务端链接建立成功 Mon Sep 23 15:11:50 CST 2019 127.0.0.1
2019-09-21 15:11:51 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]客户端发送，服务端你在吗？
2019-09-21 15:11:51 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]客户端发送，服务端你在吗？
2019-09-21 15:11:51 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]客户端发送，服务端你在吗？
2019-09-21 15:11:51 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]客户端发送，服务端你在吗？
2019-09-21 15:11:51 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]客户端发送，服务端你在吗？
2019-09-21 15:11:51 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]客户端发送，服务端你在吗？
2019-09-21 15:11:51 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]客户端发送，服务端你在吗？
2019-09-21 15:11:51 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]客户端发送，服务端你在吗？
2019-09-21 15:11:51 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]客户端发送，服务端你在吗？

异常信息：
远程主机强迫关闭了一个现有的连接。
客户端断开链接/127.0.0.1:7399

Process finished with exit code -1
```

>启动客户端NettyClient

```java
链接报告开始
链接报告信息：本客户端链接到服务端。channelId：3ad375e9
链接报告IP:127.0.0.1
链接报告Port:51656
链接报告完毕
itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈 | 获取专题源码}
2019-09-21 15:11:50 接收到消息：微信公众号：bugstack虫洞栈 | 通知客户端链接建立成功 Mon Sep 23 15:11:50 CST 2019 127.0.0.1
2019-09-21 15:11:50 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]服务端发送，客户端我在。
2019-09-21 15:11:50 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]服务端发送，客户端我在。
2019-09-21 15:11:50 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]服务端发送，客户端我在。
2019-09-21 15:11:50 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]服务端发送，客户端我在。
2019-09-21 15:11:50 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]服务端发送，客户端我在。
2019-09-21 15:11:50 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]服务端发送，客户端我在。
2019-09-21 15:11:50 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]服务端发送，客户端我在。
2019-09-21 15:11:50 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]服务端发送，客户端我在。
2019-09-21 15:11:50 接收到消息：微信公众号：bugstack虫洞栈 | [SSL]服务端发送，客户端我在。

Process finished with exit code -1
```

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！

