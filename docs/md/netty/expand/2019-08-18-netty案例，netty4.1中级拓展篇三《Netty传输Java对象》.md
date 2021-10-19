---
layout: post
category: itstack-demo-netty-2
title: netty案例，netty4.1中级拓展篇三《Netty传输Java对象》
tagline: by 付政委
tag: [netty,itstack-demo-netty-2] 
lock: need
---

## 前言介绍
Netty在实际应用级开发中，有时候某些特定场景下会需要使用Java对象类型进行传输，但是如果使用Java本身序列化进行传输，那么对性能的损耗比较大。为此我们需要借助protostuff-core的工具包将对象以二进制形式传输并做编码解码处理。与直接使用protobuf二进制传输方式不同，这里不需要定义proto文件，而是需要实现对象类型编码解码器，用以传输自定义Java对象。

>protostuff 基于Google protobuf，但是提供了更多的功能和更简易的用法。其中，protostuff-runtime 实现了无需预编译对java bean进行protobuf序列化/反序列化的能力。protostuff-runtime的局限是序列化前需预先传入schema，反序列化不负责对象的创建只负责复制，因而必须提供默认构造函数。此外，protostuff 还可以按照protobuf的配置序列化成json/yaml/xml等格式。在性能上，protostuff不输原生的protobuf，甚至有反超之势。
1. 支持protostuff-compiler产生的消息
2. 支持现有的POJO对象
3. 支持现有的protoc产生的Java消息
4. 与各种移动平台的互操作能力（Android、Kindle、j2me）
5. 支持转码

## 开发环境
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】

## 代码示例
```java
itstack-demo-netty-2-03
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
    │           │	└── MsgInfo.java
    │           ├── server
    │           │	├── MyChannelInitializer.java
    │           │	├── MyServerHandler.java
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

>client/MyChannelInitializer.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //对象传输处理
        channel.pipeline().addLast(new ObjDecoder(MsgInfo.class));
        channel.pipeline().addLast(new ObjEncoder(MsgInfo.class));
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyClientHandler());
    }

}
```
>client/MyClientHandler.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
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
        //通知客户端链接建立成功
        String str = "通知服务端链接建立成功" + " " + new Date() + " " + channel.localAddress().getHostString();
        ctx.writeAndFlush(MsgUtil.buildMsg(channel.id().toString(), str));
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("断开链接" + ctx.channel().localAddress().toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息类型：" + msg.getClass());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息内容：" + JSON.toJSONString(msg));
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
>client/NettyClient.java

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

            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"));

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}

```
>codec/ObjDecoder.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class ObjDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;

    public ObjDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

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
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        out.add(SerializationUtil.deserialize(data, genericClass));
    }

}
```
>codec/ObjEncoder.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class ObjEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public ObjEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object in, ByteBuf out)  {
        if (genericClass.isInstance(in)) {
            byte[] data = SerializationUtil.serialize(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }

}
```
>domain/MsgInfo.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class MsgInfo {

    private String channelId;
    private String msgContent;

    public MsgInfo() {
    }

    public MsgInfo(String channelId, String msgContent) {
        this.channelId = channelId;
        this.msgContent = msgContent;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}

```
>server/MyChannelInitializer.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        //对象传输处理
        channel.pipeline().addLast(new ObjDecoder(MsgInfo.class));
        channel.pipeline().addLast(new ObjEncoder(MsgInfo.class));
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyServerHandler());
    }

}
```
>server/MyServerHandler.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("链接报告开始");
        System.out.println("链接报告信息：有一客户端链接到本服务端。channelId：" + channel.id());
        System.out.println("链接报告IP:" + channel.localAddress().getHostString());
        System.out.println("链接报告Port:" + channel.localAddress().getPort());
        System.out.println("链接报告完毕");
        //通知客户端链接建立成功
        String str = "通知客户端链接建立成功" + " " + new Date() + " " + channel.localAddress().getHostString() + "\r\n";
        ctx.writeAndFlush(MsgUtil.buildMsg(channel.id().toString(), str));
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
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息类型：" + msg.getClass());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息内容：" + JSON.toJSONString(msg));
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
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
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
>util/MsgUtil.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class MsgUtil {

    public static MsgInfo buildMsg(String channelId, String msgContent) {
        return new MsgInfo(channelId,msgContent);
    }

}
```
>util/SerializationUtil.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class SerializationUtil {

    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    private static Objenesis objenesis = new ObjenesisStd();

    private SerializationUtil() {

    }

    /**
     * 序列化(对象 -> 字节数组)
     *
     * @param obj 对象
     * @return 字节数组
     */
    public static <T> byte[] serialize(T obj) {
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(cls);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    /**
     * 反序列化(字节数组 -> 对象)
     *
     * @param data
     * @param cls
     * @param <T>
     */
    public static <T> T deserialize(byte[] data, Class<T> cls) {
        try {
            T message = objenesis.newInstance(cls);
            Schema<T> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(data, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private static <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            cachedSchema.put(cls, schema);
        }
        return schema;
    }

}
```
## 测试结果
>启动NettyServer

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-2-03-1.png)

>启动NettyClient

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-2-03-2.png)

>服务端执行结果

```java
itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}
链接报告开始
链接报告信息：有一客户端链接到本服务端。channelId：eaa23c73
链接报告IP:127.0.0.1
链接报告Port:7397
链接报告完毕
2019-08-04 16:25:48 接收到消息类型：class org.itstack.demo.netty.domain.MsgInfo
2019-08-04 16:25:48 接收到消息内容：{"channelId":"e0a8c2f0","msgContent":"通知服务端链接建立成功 Sun Aug 04 16:25:48 CST 2019 127.0.0.1"}
2019-08-04 16:25:48 接收到消息类型：class org.itstack.demo.netty.domain.MsgInfo
2019-08-04 16:25:48 接收到消息内容：{"channelId":"e0a8c2f0","msgContent":"你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"}
2019-08-04 16:25:48 接收到消息类型：class org.itstack.demo.netty.domain.MsgInfo
2019-08-04 16:25:48 接收到消息内容：{"channelId":"e0a8c2f0","msgContent":"你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"}
2019-08-04 16:25:48 接收到消息类型：class org.itstack.demo.netty.domain.MsgInfo
2019-08-04 16:25:48 接收到消息内容：{"channelId":"e0a8c2f0","msgContent":"你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"}
2019-08-04 16:25:48 接收到消息类型：class org.itstack.demo.netty.domain.MsgInfo
2019-08-04 16:25:48 接收到消息内容：{"channelId":"e0a8c2f0","msgContent":"你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"}
2019-08-04 16:25:48 接收到消息类型：class org.itstack.demo.netty.domain.MsgInfo
2019-08-04 16:25:48 接收到消息内容：{"channelId":"e0a8c2f0","msgContent":"你好，使用protobuf通信格式的服务端，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，关注我获取案例源码。"}
异常信息：
远程主机强迫关闭了一个现有的连接。
客户端断开链接/127.0.0.1:7397

Process finished with exit code -1
```

>客户端执行结果

```java
链接报告开始
itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}
链接报告信息：本客户端链接到服务端。channelId：e0a8c2f0
链接报告IP:127.0.0.1
链接报告Port:60886
链接报告完毕
2019-08-04 16:25:48 接收到消息类型：class org.itstack.demo.netty.domain.MsgInfo
2019-08-04 16:25:48 接收到消息内容：{"channelId":"eaa23c73","msgContent":"通知客户端链接建立成功 Sun Aug 04 16:25:48 CST 2019 127.0.0.1\r\n"}

Process finished with exit code -1

```

------------

上一篇：[netty案例，netty4.1中级拓展篇二《Netty使用Protobuf传输数据》](/itstack-demo-netty-2/2019/08/17/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E4%BA%8C-Netty%E4%BD%BF%E7%94%A8Protobuf%E4%BC%A0%E8%BE%93%E6%95%B0%E6%8D%AE.html)

下一篇：[netty案例，netty4.1中级拓展篇四《Netty传输文件、分片发送、断点续传》](/itstack-demo-netty-2/2019/08/19/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%9B%9B-Netty%E4%BC%A0%E8%BE%93%E6%96%87%E4%BB%B6-%E5%88%86%E7%89%87%E5%8F%91%E9%80%81-%E6%96%AD%E7%82%B9%E7%BB%AD%E4%BC%A0.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！
