---
layout: post
category: itstack-demo-netty-1
title: netty案例，netty4.1基础入门篇零《初入JavaIO之门BIO、NIO、AIO实战练习》
tagline: by 付政委
tag: [netty,itstack-demo-netty-1]
lock: need
---

## 前言介绍
在Java中，提供了一些关于使用IO的API，可以供开发者来读写外部数据和文件，我们称这些API为Java IO。IO是Java中比较重要知识点，且比较难学习的知识点。并且随着Java的发展为提供更好的数据传输性能，目前有三种IO共存；分别是BIO、NIO和AIO。
![微信公众号：bugstack虫洞栈 & bio、nio、aio](https://bugstack.cn/assets/images/pic-content/2019/10/netty-1-01-1-ios.png)

>Java BIO[Blocking I/O] | 同步阻塞I/O模式

BIO 全称Block-IO 是一种同步且阻塞的通信模式。是一个比较传统的通信方式，模式简单，使用方便。但并发处理能力低，通信耗时，依赖网速。

>Java NIO[New I/O] | 同步非阻塞模式

- Java NIO，全程 Non-Block IO ，是Java SE 1.4版以后，针对网络传输效能优化的新功能。是一种非阻塞同步的通信模式。
- NIO 与原来的 I/O 有同样的作用和目的, 他们之间最重要的区别是数据打包和传输的方式。原来的 I/O 以流的方式处理数据，而 NIO 以块的方式处理数据。
- 面向流的 I/O 系统一次一个字节地处理数据。一个输入流产生一个字节的数据，一个输出流消费一个字节的数据。
- 面向块的 I/O 系统以块的形式处理数据。每一个操作都在一步中产生或者消费一个数据块。按块处理数据比按(流式的)字节处理数据要快得多。但是面向块的 I/O  - 缺少一些面向流的 I/O 所具有的优雅性和简单性。

>Java AIO[Asynchronous I/O] | 异步非阻塞I/O模型

Java AIO，全程 Asynchronous IO，是异步非阻塞的IO。是一种非阻塞异步的通信模式。在NIO的基础上引入了新的异步通道的概念，并提供了异步文件通道和异步套接字通道的实现。

如下我们将分别对三种IO进行案例演示，通过对三种的IO的认知来方便学习后续的Netty知识。

## 开发环境
1. jdk1.8【jdk1.7及以上，AIO需要1.7】
2. NetAssist 网络调试助手[获取：关注公众号：bugstack虫洞栈 | 回复；NetAssist+邮箱]

## 代码示例
```java
itstack-demo-netty-1-00
└── src
    ├── main
    │   └── java
    │       └── org.itstack.demo.netty
    │           ├── aio
    │           │   ├── client
    │           │   │   ├── AioClient.java
    │           │   │   └── AioClientHandler.java	
    │           │   ├── server
    │           │   │   ├── AioServer.java
    │           │   │   ├── AioServerChannelInitializer.java
    │           │   │   └── AioServerHandler.java
    │           │   ├── ChannelAdapter.java
    │           │   ├── ChannelHandler.java
    │           │   └── ChannelInitializer.java	
    │           ├── bio
    │           │   ├── client
    │           │   │   ├── BioClient.java
    │           │   │   └── BioClientHandler.java	
    │           │   ├── server
    │           │   │   ├── BioServer.java
    │           │   │   └── BioServerHandler.java
    │           │   ├── ChannelAdapter.java
    │           │   └── ChannelHandler.java	
    │           └── nio
    │               ├── client
    │               │   ├── NioClient.java
    │               │   └── NioClientHandler.java	
    │               ├── server
    │               │   ├── NioServer.java
    │               │   └── NioServerHandler.java
    │               ├── ChannelAdapter.java
    │               └── ChannelHandler.java	
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```

**重点代码块讲解，完整代码，关注公众号：bugstack虫洞栈 | 回复Netty源码获取**

### AIO案例代码

>aio/client/AioClient.java | 客户端

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例，以最易学习编程的方式分享知识，让萌新、小白、大牛都能有所收获。目前已完成的专题有；Netty4.x从入门到实战、用Java实现JVM、基于JavaAgent的全链路监控等，其他更多专题还在排兵布阵中。
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class AioClient {

    public static void main(String[] args) throws Exception {
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
        Future<Void> future = socketChannel.connect(new InetSocketAddress("192.168.1.116", 7397));
        System.out.println("itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈 | 欢迎关注&获取源码}");
        future.get();
        socketChannel.read(ByteBuffer.allocate(1024), null, new AioClientHandler(socketChannel, Charset.forName("GBK")));
        Thread.sleep(100000);
    }

}
```

>aio/client/AioClientHandler.java | 客户端消息处理器

```java
**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例，以最易学习编程的方式分享知识，让萌新、小白、大牛都能有所收获。目前已完成的专题有；Netty4.x从入门到实战、用Java实现JVM、基于JavaAgent的全链路监控等，其他更多专题还在排兵布阵中。
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class AioClientHandler extends ChannelAdapter {

    public AioClientHandler(AsynchronousSocketChannel channel, Charset charset) {
        super(channel, charset);
    }

    @Override
    public void channelActive(ChannelHandler ctx) {
        try {
            System.out.println("微信公众号：bugstack虫洞栈 | 链接报告信息:" + ctx.channel().getRemoteAddress());
            //通知客户端链接建立成功
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelInactive(ChannelHandler ctx) {
    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        System.out.println("微信公众号：bugstack虫洞栈 | 服务端收到：" + new Date() + " " + msg + "\r\n");
        ctx.writeAndFlush("客户端信息处理Success！\r\n");
    }

}
```

>aio/server/AioServer.java | 服务端

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例，以最易学习编程的方式分享知识，让萌新、小白、大牛都能有所收获。目前已完成的专题有；Netty4.x从入门到实战、用Java实现JVM、基于JavaAgent的全链路监控等，其他更多专题还在排兵布阵中。
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class AioServer extends Thread {

    private AsynchronousServerSocketChannel serverSocketChannel;

    @Override
    public void run() {
        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open(AsynchronousChannelGroup.withCachedThreadPool(Executors.newCachedThreadPool(), 10));
            serverSocketChannel.bind(new InetSocketAddress(7397));
            System.out.println("itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈 | 欢迎关注&获取源码}");
            // 等待
            CountDownLatch latch = new CountDownLatch(1);
            serverSocketChannel.accept(this, new AioServerChannelInitializer());
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AsynchronousServerSocketChannel serverSocketChannel() {
        return serverSocketChannel;
    }

    public static void main(String[] args) {
        new AioServer().start();
    }

}
```

>aio/server/AioServerChannelInitializer.java | 初始化

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例，以最易学习编程的方式分享知识，让萌新、小白、大牛都能有所收获。目前已完成的专题有；Netty4.x从入门到实战、用Java实现JVM、基于JavaAgent的全链路监控等，其他更多专题还在排兵布阵中。
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class AioServerChannelInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(AsynchronousSocketChannel channel) throws Exception {
        channel.read(ByteBuffer.allocate(1024), 10, TimeUnit.SECONDS, null, new AioServerHandler(channel, Charset.forName("GBK")));
    }

}
```

>aio/server/AioServerHandler.java | 处理消息

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例，以最易学习编程的方式分享知识，让萌新、小白、大牛都能有所收获。目前已完成的专题有；Netty4.x从入门到实战、用Java实现JVM、基于JavaAgent的全链路监控等，其他更多专题还在排兵布阵中。
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class AioServerHandler extends ChannelAdapter {

    public AioServerHandler(AsynchronousSocketChannel channel, Charset charset) {
        super(channel, charset);
    }

    @Override
    public void channelActive(ChannelHandler ctx) {
        try {
            System.out.println("微信公众号：bugstack虫洞栈 | 链接报告信息:" + ctx.channel().getRemoteAddress());
            //通知客户端链接建立成功
            ctx.writeAndFlush("微信公众号：bugstack虫洞栈 | 通知服务端链接建立成功" + " " + new Date() + " " + ctx.channel().getRemoteAddress() + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelInactive(ChannelHandler ctx) {
    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        System.out.println("微信公众号：bugstack虫洞栈 | 服务端收到：" + new Date() + " " + msg + "\r\n");
        ctx.writeAndFlush("服务端信息处理Success！\r\n");
    }

}
```

>aio/ChannelAdapter.java | Channle适配器模仿Netty

```java
/**
 * 消息处理器
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例，以最易学习编程的方式分享知识，让萌新、小白、大牛都能有所收获。目前已完成的专题有；Netty4.x从入门到实战、用Java实现JVM、基于JavaAgent的全链路监控等，其他更多专题还在排兵布阵中。
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public abstract class ChannelAdapter implements CompletionHandler<Integer, Object> {

    private AsynchronousSocketChannel channel;
    private Charset charset;

    public ChannelAdapter(AsynchronousSocketChannel channel, Charset charset) {
        this.channel = channel;
        this.charset = charset;
        if (channel.isOpen()) {
            channelActive(new ChannelHandler(channel, charset));
        }
    }

    @Override
    public void completed(Integer result, Object attachment) {
        try {
            final ByteBuffer buffer = ByteBuffer.allocate(1024);
            final long timeout = 60 * 60L;
            channel.read(buffer, timeout, TimeUnit.SECONDS, null, new CompletionHandler<Integer, Object>() {
                @Override
                public void completed(Integer result, Object attachment) {
                    if (result == -1) {
                        try {
                            channelInactive(new ChannelHandler(channel, charset));
                            channel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    buffer.flip();
                    channelRead(new ChannelHandler(channel, charset), charset.decode(buffer));
                    buffer.clear();
                    channel.read(buffer, timeout, TimeUnit.SECONDS, null, this);
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    exc.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void failed(Throwable exc, Object attachment) {
        exc.getStackTrace();
    }

    public abstract void channelActive(ChannelHandler ctx);

    public abstract void channelInactive(ChannelHandler ctx);

    // 读取消息抽象类
    public abstract void channelRead(ChannelHandler ctx, Object msg);

}
```

### AIO案例演示 | 服务端测试

**启动AioServer**

```java
itstack-demo-netty aio server start done. {关注公众号：bugstack虫洞栈 | 欢迎关注&获取源码}
微信公众号：bugstack虫洞栈 | 链接报告信息:/192.168.1.116:57208
微信公众号：bugstack虫洞栈 | 服务端收到：Sun Oct 06 18:19:50 CST 2019 hi aio server! helloworld

微信公众号：bugstack虫洞栈 | 服务端收到：Sun Oct 06 18:19:51 CST 2019 hi aio server! helloworld

微信公众号：bugstack虫洞栈 | 服务端收到：Sun Oct 06 18:19:51 CST 2019 hi aio server! helloworld

微信公众号：bugstack虫洞栈 | 服务端收到：Sun Oct 06 18:19:52 CST 2019 hi aio server! helloworld


Process finished with exit code -1
```

![微信公众号：bugstack虫洞栈 & AIO案例测试](https://bugstack.cn/assets/images/pic-content/2019/10/netty-1-00-2.png)

---------

### BIO案例代码

>bio/client/BioClient.java | 客户端

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例，以最易学习编程的方式分享知识，让萌新、小白、大牛都能有所收获。目前已完成的专题有；Netty4.x从入门到实战、用Java实现JVM、基于JavaAgent的全链路监控等，其他更多专题还在排兵布阵中。
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class BioClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.1.116", 7397);
            System.out.println("itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈 | 欢迎关注&获取源码}");
            BioClientHandler bioClientHandler = new BioClientHandler(socket, Charset.forName("utf-8"));
            bioClientHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
```

>bio/client/BioClientHandler.java | 消息处理器

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例，以最易学习编程的方式分享知识，让萌新、小白、大牛都能有所收获。目前已完成的专题有；Netty4.x从入门到实战、用Java实现JVM、基于JavaAgent的全链路监控等，其他更多专题还在排兵布阵中。
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class BioClientHandler extends ChannelAdapter {

    public BioClientHandler(Socket socket, Charset charset) {
        super(socket, charset);
    }

    @Override
    public void channelActive(ChannelHandler ctx) {
        System.out.println("链接报告LocalAddress:" + ctx.socket().getLocalAddress());
        ctx.writeAndFlush("hi! 我是bugstack虫洞栈 BioClient to msg for you \r\n");
    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        ctx.writeAndFlush("hi 我已经收到你的消息Success！\r\n");
    }

}
```

>bio/server/BioServer.java | 服务端

```java
/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019/9/30
 */
public class BioServer extends Thread {

    private ServerSocket serverSocket = null;

    public static void main(String[] args) {
        BioServer bioServer = new BioServer();
        bioServer.start();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(7397));
            System.out.println("itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈 | 欢迎关注&获取源码}");
            while (true) {
                Socket socket = serverSocket.accept();
                BioServerHandler handler = new BioServerHandler(socket, Charset.forName("utf-8"));
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

>bio/server/BioServerHandler.java | 消息处理器

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例，以最易学习编程的方式分享知识，让萌新、小白、大牛都能有所收获。目前已完成的专题有；Netty4.x从入门到实战、用Java实现JVM、基于JavaAgent的全链路监控等，其他更多专题还在排兵布阵中。
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class BioServerHandler extends ChannelAdapter {

    public BioServerHandler(Socket socket, Charset charset) {
        super(socket, charset);
    }

    @Override
    public void channelActive(ChannelHandler ctx) {
        System.out.println("链接报告LocalAddress:" + ctx.socket().getLocalAddress());
        ctx.writeAndFlush("hi! 我是bugstack虫洞栈 BioServer to msg for you \r\n");
    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        ctx.writeAndFlush("hi 我已经收到你的消息Success！\r\n");
    }

}
```

>bio/ChannelAdapter.java | 适配器

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例，以最易学习编程的方式分享知识，让萌新、小白、大牛都能有所收获。目前已完成的专题有；Netty4.x从入门到实战、用Java实现JVM、基于JavaAgent的全链路监控等，其他更多专题还在排兵布阵中。
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public abstract class ChannelAdapter extends Thread {

    private Socket socket;
    private ChannelHandler channelHandler;
    private Charset charset;

    public ChannelAdapter(Socket socket, Charset charset) {
        this.socket = socket;
        while (!socket.isConnected()) {
            break;
        }
        channelHandler = new ChannelHandler(this.socket, charset);
        channelActive(channelHandler);
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String str = null;
            while ((str = input.readLine()) != null) {
                channelRead(channelHandler, str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 链接通知抽象类
    public abstract void channelActive(ChannelHandler ctx);

    // 读取消息抽象类
    public abstract void channelRead(ChannelHandler ctx, Object msg);

}
```

### BIO案例测试

**启动BioServer**

```java
itstack-demo-netty bio server start done. {关注公众号：bugstack虫洞栈 | 欢迎关注&获取源码}
链接报告LocalAddress:/192.168.1.116
2019-10-06 18:28:17 接收到消息：hi bio server! helloworld {结尾是换行符}
2019-10-06 18:28:17 接收到消息：hi bio server! helloworld {结尾是换行符}
2019-10-06 18:28:18 接收到消息：hi bio server! helloworld {结尾是换行符}
2019-10-06 18:28:18 接收到消息：hi bio server! helloworld {结尾是换行符}
2019-10-06 18:28:19 接收到消息：hi bio server! helloworld {结尾是换行符}
2019-10-06 18:28:19 接收到消息：hi bio server! helloworld {结尾是换行符}

Process finished with exit code -1
```

![微信公众号：bugstack虫洞栈 & BIO案例测试](https://bugstack.cn/assets/images/pic-content/2019/10/netty-1-00-3.png)


### NIO案例代码

>nio/client/NioClient.java | 客户端

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例，以最易学习编程的方式分享知识，让萌新、小白、大牛都能有所收获。目前已完成的专题有；Netty4.x从入门到实战、用Java实现JVM、基于JavaAgent的全链路监控等，其他更多专题还在排兵布阵中。
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        boolean isConnect = socketChannel.connect(new InetSocketAddress("192.168.1.116", 7397));
        if (isConnect) {
            socketChannel.register(selector, SelectionKey.OP_READ);
        } else {
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
        System.out.println("itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈 | 欢迎关注&获取源码}");
        new NioClientHandler(selector, Charset.forName("GBK")).start();
    }

}
```

>nio/client/NioClientHandler.java | 消息处理器

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例，以最易学习编程的方式分享知识，让萌新、小白、大牛都能有所收获。目前已完成的专题有；Netty4.x从入门到实战、用Java实现JVM、基于JavaAgent的全链路监控等，其他更多专题还在排兵布阵中。
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class NioClientHandler extends ChannelAdapter {

    public NioClientHandler(Selector selector, Charset charset) {
        super(selector, charset);
    }

    @Override
    public void channelActive(ChannelHandler ctx) {
        try {
            System.out.println("链接报告LocalAddress:" + ctx.channel().getLocalAddress());
            ctx.writeAndFlush("hi! 我是bugstack虫洞栈 BioClient to msg for you \r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        ctx.writeAndFlush("hi 我已经收到你的消息Success！\r\n");
    }

}
```

>nio/server/NioServer.java | 服务端

```java
**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例，以最易学习编程的方式分享知识，让萌新、小白、大牛都能有所收获。目前已完成的专题有；Netty4.x从入门到实战、用Java实现JVM、基于JavaAgent的全链路监控等，其他更多专题还在排兵布阵中。
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class NioServer {

    private Selector selector;
    private ServerSocketChannel socketChannel;

    public static void main(String[] args) throws IOException {
        new NioServer().bind(7397);
    }

    public void bind(int port) {
        try {
            selector = Selector.open();
            socketChannel = ServerSocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.socket().bind(new InetSocketAddress(port), 1024);
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈 | 欢迎关注&获取源码}");
            new NioServerHandler(selector, Charset.forName("utf-8")).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
```

>nio/server/NioServerHandler.java | 消息处理器

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例，以最易学习编程的方式分享知识，让萌新、小白、大牛都能有所收获。目前已完成的专题有；Netty4.x从入门到实战、用Java实现JVM、基于JavaAgent的全链路监控等，其他更多专题还在排兵布阵中。
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class NioServerHandler extends ChannelAdapter {

    public NioServerHandler(Selector selector, Charset charset) {
        super(selector, charset);
    }

    @Override
    public void channelActive(ChannelHandler ctx) {
        try {
            System.out.println("链接报告LocalAddress:" + ctx.channel().getLocalAddress());
            ctx.writeAndFlush("hi! 我是bugstack虫洞栈 BioServer to msg for you \r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        ctx.writeAndFlush("hi 我已经收到你的消息Success！\r\n");
    }

}
```

>nio/ChannelAdapter.java | 适配器

```java
/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例，以最易学习编程的方式分享知识，让萌新、小白、大牛都能有所收获。目前已完成的专题有；Netty4.x从入门到实战、用Java实现JVM、基于JavaAgent的全链路监控等，其他更多专题还在排兵布阵中。
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public abstract class ChannelAdapter extends Thread {

    private Selector selector;

    private ChannelHandler channelHandler;
    private Charset charset;

    public ChannelAdapter(Selector selector, Charset charset) {
        this.selector = selector;
        this.charset = charset;
    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select(1000);  //Selects a set of keys whose corresponding channels are ready for I/O
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    handleInput(key);
                }
            } catch (Exception ignore) {
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (!key.isValid()) return;

        // 客户端SocketChannel
        Class<?> superclass = key.channel().getClass().getSuperclass();
        if (superclass == SocketChannel.class){
            SocketChannel socketChannel = (SocketChannel) key.channel();
            if (key.isConnectable()) {
                if (socketChannel.finishConnect()) {
                    channelHandler = new ChannelHandler(socketChannel, charset);
                    channelActive(channelHandler);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else {
                    System.exit(1);
                }
            }
        }

        // 服务端ServerSocketChannel
        if (superclass == ServerSocketChannel.class){
            if (key.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);

                channelHandler = new ChannelHandler(socketChannel, charset);
                channelActive(channelHandler);
            }
        }

        if (key.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            int readBytes = socketChannel.read(readBuffer);
            if (readBytes > 0) {
                readBuffer.flip();
                byte[] bytes = new byte[readBuffer.remaining()];
                readBuffer.get(bytes);
                channelRead(channelHandler, new String(bytes, charset));
            } else if (readBytes < 0) {
                key.cancel();
                socketChannel.close();
            }
        }
    }

    // 链接通知抽象类
    public abstract void channelActive(ChannelHandler ctx);

    // 读取消息抽象类
    public abstract void channelRead(ChannelHandler ctx, Object msg);

}
```

### NIO案例测试

**启动NioServer**

```java
itstack-demo-netty nio server start done. {关注公众号：bugstack虫洞栈 | 欢迎关注&获取源码}
链接报告LocalAddress:/192.168.1.116:7397
2019-10-06 18:33:32 接收到消息：hi bio server! helloworld {结尾无换行符}
2019-10-06 18:33:32 接收到消息：hi bio server! helloworld {结尾无换行符}
2019-10-06 18:33:32 接收到消息：hi bio server! helloworld {结尾无换行符}
2019-10-06 18:33:33 接收到消息：hi bio server! helloworld {结尾无换行符}
2019-10-06 18:33:33 接收到消息：hi bio server! helloworld {结尾无换行符}

Process finished with exit code -1
```

![微信公众号：bugstack虫洞栈 & NIO案例测试](https://bugstack.cn/assets/images/pic-content/2019/10/netty-1-00-4.png)

------------

下一篇：[netty案例，netty4.1基础入门篇一《嗨！NettyServer》](/itstack-demo-netty-1/2019/08/01/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E4%B8%80-%E5%97%A8-NettyServer.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！