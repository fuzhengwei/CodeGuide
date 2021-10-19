---
layout: post
category: itstack-demo-netty-2
title: netty案例，netty4.1中级拓展篇十《Netty接收发送多种协议消息类型的通信处理方案》
tagline: by 付政委
tag: [netty,itstack-demo-netty-2] 
lock: need
---

# netty案例，netty4.1中级拓展篇十《Netty接收发送多种协议消息类型的通信处理方案》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
在我们实际做应用级开发的过程中，客户端与服务端需要发送多种消息类型，比如一个聊天室场景包括的消息类型；登录验证、组建群聊、发送消息、退出登录等等，但如果我们都是用统一对象加if判断来分别转换，那么对后期的维护成本就会非常大，这样的代码方式也不是一个面向对象开发的思维。面向对象的开发思路，经常会把很多if、switch等逻辑抽象成对应的接口和抽象类，以及加入工厂方式对服务进行动态编排。

那么我们在这里也同样需要定义一个抽象类，抽象类里包含了一个必须实现的标识性属性，用来编码解码时提取标识，找到对应的处理类进行操作。这样我们就可以不断的去扩展我们需要的不同维度的消息处理的Handler，在这个案例里我们模拟了；demo01、demo02、demo03三组消息处理handler，他们都统一继承抽象类Packet，并实现里面的getCommand方法。另外可以在这个抽象类中加入一些其他属性，包括；版本、校验、加密等，可以更加方便的用于处理各类通用非业务属性逻辑行为。

## 开发环境
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】

## 代码示例
```java
itstack-demo-netty-2-10
└── src
    ├── main
    │   └── java
    │       └── org.itstack.demo.netty
    │           ├── client
    │           │	├── MyChannelInitializer.java
    │           │	├── MyClientHandler.java
    │           │	└── NettyClient.java
    │           ├── codec
    │           │	├── ObjDecoder.java	
    │           │	└── ObjEncoder.java
    │           ├── domain
    │           │	├── protocol
    │           │	│	├── Command.java
    │           │	│	├── Packet.java
    │           │	│	└── PacketClazzMap.java
    │           │	├── MsgDemo01.java
    │           │	├── MsgDemo02.java
    │           │	└── MsgDemo03.java
    │           ├── server
    │           │	├── handler
    │           │	│	├── MsgDemo01Handler.java
    │           │	│	├── MsgDemo02Handler.java
    │           │	│	└── MsgDemo03Handler.java	
    │           │	├── MyChannelInitializer.java
    │           │	└── NettyServer.java
    │           └── util
    │           	├── MsgUtil.java
    │           	└── SerializationUtil.java
    │
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```

** 只展示讲解重点代码块，全部代码可以关注公众号：bugstack虫洞栈，回复：netty源码，获取！ **

>client/NettyClient.java | 增加了模拟发送不同类型的消息

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
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

            //测试消息，分别发放demo01、demo02、demo03
            f.channel().writeAndFlush(MsgUtil.buildMsgDemo01(f.channel().id().toString(),"你好，消息体MsgDemo01，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，欢迎关注我获取案例源码。"));
            f.channel().writeAndFlush(MsgUtil.buildMsgDemo02(f.channel().id().toString(),"你好，消息体MsgDemo02，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，欢迎关注我获取案例源码。"));
            f.channel().writeAndFlush(MsgUtil.buildMsgDemo03(f.channel().id().toString(),"你好，消息体MsgDemo03，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，欢迎关注我获取案例源码。"));

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
```

>codec/ObjDecoder.java | 改造解码器，通过读取指令取的对应的类来解码

```java
/**
 * 解码器
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class ObjDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte command = in.readByte();  //读取指令
        byte[] data = new byte[dataLength - 1]; //指令占了一位，剔除掉
        in.readBytes(data);
        out.add(SerializationUtil.deserialize(data, PacketClazzMap.packetTypeMap.get(command)));
    }

}
```

>codec/ObjEncoder.java | 改造编码器，在将对象序列化byte[]后，添加指令

```java
/**
 * 编码器
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class ObjEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet in, ByteBuf out) {
        byte[] data = SerializationUtil.serialize(in);
        out.writeInt(data.length + 1);
        out.writeByte(in.getCommand()); //添加指令
        out.writeBytes(data);
    }

}

```

>domain/protocol/Command.java | 定义指令组

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public interface Command {

    Byte Demo01 = 1;   //测试01
    Byte Demo02 = 2;   //测试02
    Byte Demo03 = 3;   //测试03

}
```

>domain/protocol/Packet.java | 定义协议包头，所以的通信消息都继承这个类

```java
/**
 * 协议包
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public abstract class Packet {

    /**
     * 获取协议指令
     * @return 返回指令值
     */
    public abstract Byte getCommand();

}
```

>domain/protocol/PacketClazzMap.java | 方便获取对应类标识的Map结构

```java
import org.itstack.demo.netty.domain.MsgDemo01;
import org.itstack.demo.netty.domain.MsgDemo02;
import org.itstack.demo.netty.domain.MsgDemo03;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class PacketClazzMap {

    public final static Map<Byte, Class<? extends Packet>> packetTypeMap = new ConcurrentHashMap<>();

    static {
        packetTypeMap.put(Command.Demo01, MsgDemo01.class);
        packetTypeMap.put(Command.Demo02, MsgDemo02.class);
        packetTypeMap.put(Command.Demo03, MsgDemo03.class);
    }

}

```

>domain/MsgDemo01.java | 通信消息定义，三个类似展示其中一个

```java
/**
 * 消息协议
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class MsgDemo01 extends Packet {

    private String channelId;
    private String demo01;

    public MsgDemo01(String channelId, String demo01) {
        this.channelId = channelId;
        this.demo01 = demo01;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getDemo01() {
        return demo01;
    }

    public void setDemo01(String demo01) {
        this.demo01 = demo01;
    }

    @Override
    public Byte getCommand() {
        return Command.Demo01;
    }

}
```

>server/handler/MsgDemo01Handler.java | 用于处理消息的handler，三个类似展示其中一个

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MsgDemo01Handler extends SimpleChannelInboundHandler<MsgDemo01> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgDemo01 msg) throws Exception {
        System.out.println("\r\n> msg handler ing ...");
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收消息的处理器：" + this.getClass().getName());
        System.out.println("channelId：" + msg.getChannelId());
        System.out.println("消息内容：" + msg.getDemo01());
    }

}

```

>server/MyChannelInitializer.java | 在这里与以往不同，里面包含了三组消息处理handler，如果有更多可以依次添加

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        //对象传输处理[解码]
        channel.pipeline().addLast(new ObjDecoder());
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MsgDemo01Handler());
        channel.pipeline().addLast(new MsgDemo02Handler());
        channel.pipeline().addLast(new MsgDemo03Handler());
        //对象传输处理[编码]
        channel.pipeline().addLast(new ObjEncoder());
    }

}
```

## 测试结果
>启动NettyServer

```java
itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}

> msg handler ing ...
2019-09-08 11:16:00 接收消息的处理器：org.itstack.demo.netty.server.handler.MsgDemo01Handler
channelId：a21401f4
消息内容：你好，消息体MsgDemo01，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，欢迎关注我获取案例源码。

> msg handler ing ...
2019-09-08 11:16:00 接收消息的处理器：org.itstack.demo.netty.server.handler.MsgDemo02Handler
channelId：a21401f4
消息内容：你好，消息体MsgDemo02，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，欢迎关注我获取案例源码。

> msg handler ing ...
2019-09-08 11:16:00 接收消息的处理器：org.itstack.demo.netty.server.handler.MsgDemo03Handler
channelId：a21401f4
消息内容：你好，消息体MsgDemo03，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，欢迎关注我获取案例源码。

Process finished with exit code -1
```

>启动NettyClient

```java
链接报告开始
itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}
链接报告信息：本客户端链接到服务端。channelId：a21401f4
链接报告IP:127.0.0.1
链接报告Port:51714
链接报告完毕
异常信息：
远程主机强迫关闭了一个现有的连接。
断开链接/127.0.0.1:51714

Process finished with exit code -1
```

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！
