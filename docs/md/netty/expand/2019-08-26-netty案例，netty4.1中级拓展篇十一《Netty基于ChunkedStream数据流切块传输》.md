---
layout: post
category: itstack-demo-netty-2
title: netty案例，netty4.1中级拓展篇十一《Netty基于ChunkedStream数据流切块传输》
tagline: by 付政委
tag: [netty,itstack-demo-netty-2]
lock: need
---

## 前言介绍
在Netty这种异步NIO框架的结构下，服务端与客户端通信过程中，高效、频繁、大量的写入大块数据时，因网络传输饱和的可能性就会造成数据处理拥堵、GC频繁、用户掉线的可能性。那么由于写操作是非阻塞的，所以即使没有写出所有的数据，写操作也会在完成时返回并通知ChannelFuture。当这种情况发生时，如果仍然不停地写入，就有内存耗尽的风险。所以在写大块数据时，需要对大块数据进行切割发送处理。

>https://netty.io/4.0/api/io/netty/handler/stream/ChunkedStream.html
>ChunkedInput 的实现
ChunkedFile 从文件中逐块获取数据，当你的平台不支持零拷贝或者你需要转换数据时使用
ChunkedNioFile 和ChunkedFile 类似，只是它使用了FileChannel
ChunkedStream 从InputStream 中逐块传输内容
ChunkedNioStream 从ReadableByteChannel 中逐块传输内容

## 开发环境
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
3. NetAssist 网络调试助手[获取：关注公众号：bugstack虫洞栈 | 回复；NetAssist+邮箱]

## 代码示例
```java
itstack-demo-netty-2-11
└── src
    ├── main
    │   └── java
    │       └── org.itstack.demo.netty.server
    │           ├── MyChannelInitializer.java
    │           ├── MyServerChunkHandler.java
    │           ├── MyServerHandler.java
    │           └── NettyServer.java
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```

** 重点代码块讲解，完整代码，关注公众号：bugstack虫洞栈 | 回复Netty源码获取 **

>MyChannelInitializer.java | 添加流量分块功能

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
        // 流量分块
        channel.pipeline().addLast(new ChunkedWriteHandler());
        channel.pipeline().addLast(new MyServerChunkHandler());
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        channel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        channel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyServerHandler());
    }

}
```

>MyServerChunkHandler.java | 流量分块实现ChunkedStream(in, 10)

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈 | 欢迎关注并获取专题&源码
 * Create by fuzhengwei on 2019
 */
public class MyServerChunkHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        //内容验证
        if (!(msg instanceof ByteBuf)) {
            super.write(ctx, msg, promise);
            return;
        }
        //获取Byte
        ByteBuf buf = (ByteBuf) msg;
        byte[] data = this.getData(buf);
        //写入流中
        ByteInputStream in = new ByteInputStream();
        in.setBuf(data);
        //消息分块；10个字节，测试过程中可以调整
        ChunkedStream stream = new ChunkedStream(in, 10);
        //管道消息传输承诺
        ChannelProgressivePromise progressivePromise = ctx.channel().newProgressivePromise();
        progressivePromise.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
            }
            @Override
            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("消息发送成功 success");
                    promise.setSuccess();
                } else {
                    System.out.println("消息发送失败 failure：" + future.cause());
                    promise.setFailure(future.cause());
                }
            }
        });
        ReferenceCountUtil.release(msg);
        ctx.write(stream, progressivePromise);
    }

    //获取Byte
    private byte[] getData(ByteBuf buf) {
        if (buf.hasArray()) {
            return buf.array().clone();
        }
        byte[] data = new byte[buf.readableBytes() - 1];
        buf.readBytes(data);
        return data;
    }

}
```

## 测试结果

>启动服务端NettyServer

```java
itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}
链接报告开始
链接报告信息：有一客户端链接到本服务端
链接报告IP:127.0.0.1
链接报告Port:7397
链接报告完毕
消息发送成功 success
2019-09-15 16:36:04 接收到消息：hi 微信公众号：bugstack虫洞栈 | 欢迎关注并获取专题文章和源码
消息发送成功 success
2019-09-15 16:36:04 接收到消息：
消息发送成功 success

Process finished with exit code -1

```

>启动NetAssist网络调试助手 | 发送测试消息[结尾加换行]

```java
hi 微信公众号：bugstack虫洞栈 | 欢迎关注并获取专题文章和源码
```

![微信公众号：bugstack虫洞栈](https://bugstack.cn/assets/images/pic-content/2019/09/netty-2-11-1.png)


------------

上一篇：[netty案例，netty4.1中级拓展篇十《Netty接收发送多种协议消息类型的通信处理方案》](/itstack-demo-netty-2/2019/08/25/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%8D%81-Netty%E6%8E%A5%E6%94%B6%E5%8F%91%E9%80%81%E5%A4%9A%E7%A7%8D%E5%8D%8F%E8%AE%AE%E6%B6%88%E6%81%AF%E7%B1%BB%E5%9E%8B%E7%9A%84%E9%80%9A%E4%BF%A1%E5%A4%84%E7%90%86%E6%96%B9%E6%A1%88.html)

下一篇：[netty案例，netty4.1中级拓展篇十二《Netty流量整形数据流速率控制分析与实战》](/itstack-demo-netty-2/2019/08/27/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%8D%81%E4%BA%8C-Netty%E6%B5%81%E9%87%8F%E6%95%B4%E5%BD%A2%E6%95%B0%E6%8D%AE%E6%B5%81%E9%80%9F%E7%8E%87%E6%8E%A7%E5%88%B6%E5%88%86%E6%9E%90%E4%B8%8E%E5%AE%9E%E6%88%98.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！



