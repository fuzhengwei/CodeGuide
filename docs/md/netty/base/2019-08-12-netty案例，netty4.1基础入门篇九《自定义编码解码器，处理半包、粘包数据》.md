---
layout: post
category: itstack-demo-netty-1
title: netty案例，netty4.1基础入门篇九《自定义编码解码器，处理半包、粘包数据》
tagline: by 付政委
tag: [netty,itstack-demo-netty-1] 
lock: need
---

# netty案例，netty4.1基础入门篇九《自定义编码解码器，处理半包、粘包数据》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
在实际应用场景里，只要是支持sokcet通信的都可以和Netty交互，比如中继器、下位机、PLC等。这些场景下就非常需要自定义编码解码器，来处理字节码传输，并控制半包、粘包以及安全问题。那么本章节我们通过实现ByteToMessageDecoder、MessageToByteEncoder来实现我们的需求。

>数据传输过程中有各种情况；整包数据、半包数据、粘包数据，比如我们设定开始符号02、结束符号03；
整包数据；02 89 78 54 03
半包数据；02 89 78
粘包数据；02 89 78 54 03 02 89


## 环境准备
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
3. telnet 测试【可以现在你的win7机器上测试这个命令，用于链接到服务端的测试命令】

## 代码示例
```java
itstack-demo-netty-1-09
└── src
    ├── main
    │   └── java
    │       └── org.itstack.demo.netty
    │			├── codec
    │           │ 	├── MyDecoder.java
    │           │ 	└── MyEncoder.java
    │			└── server
    │           	├── MyChannelInitializer.java
    │           	├── MyServerHandler.java
    │           	└── NettyServer.java
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```

>MyDecoder.java *用于处理解码，02开始 03结束

```java
/**
 * 自定义解码器
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class MyDecoder extends ByteToMessageDecoder {

    //数据包基础长度
    private final int BASE_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {

        //基础长度不足，我们设定基础长度为4
        if (in.readableBytes() < BASE_LENGTH) {
            return;
        }

        int beginIdx; //记录包头位置

        while (true) {
            // 获取包头开始的index
            beginIdx = in.readerIndex();
            // 标记包头开始的index
            in.markReaderIndex();
            // 读到了协议的开始标志，结束while循环
            if (in.readByte() == 0x02) {
                break;
            }
            // 未读到包头，略过一个字节
            // 每次略过，一个字节，去读取，包头信息的开始标记
            in.resetReaderIndex();
            in.readByte();
            // 当略过，一个字节之后，
            // 数据包的长度，又变得不满足
            // 此时，应该结束。等待后面的数据到达
            if (in.readableBytes() < BASE_LENGTH) {
                return;
            }

        }

        //剩余长度不足可读取数量[没有内容长度位]
        int readableCount = in.readableBytes();
        if (readableCount <= 1) {
            in.readerIndex(beginIdx);
            return;
        }

        //长度域占4字节，读取int
        ByteBuf byteBuf = in.readBytes(1);
        String msgLengthStr = byteBuf.toString(Charset.forName("GBK"));
        int msgLength = Integer.parseInt(msgLengthStr);

        //剩余长度不足可读取数量[没有结尾标识]
        readableCount = in.readableBytes();
        if (readableCount < msgLength + 1) {
            in.readerIndex(beginIdx);
            return;
        }

        ByteBuf msgContent = in.readBytes(msgLength);

        //如果没有结尾标识，还原指针位置[其他标识结尾]
        byte end = in.readByte();
        if (end != 0x03) {
            in.readerIndex(beginIdx);
            return;
        }

        out.add(msgContent.toString(Charset.forName("GBK")));
    }
}
```

>MyEncoder.java	*用于处理编码，在byte开始和结束加上02 03

```java
/**
 * 自定义编码器
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class MyEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object in, ByteBuf out) throws Exception {

        String msg = in.toString();
        byte[] bytes = msg.getBytes();

        byte[] send = new byte[bytes.length + 2];
        System.arraycopy(bytes, 0, send, 1, bytes.length);
        send[0] = 0x02;
        send[send.length - 1] = 0x03;
        
        out.writeInt(send.length);
        out.writeBytes(send);

    }

}
```

>MyChannelInitializer.java 

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        //自定义解码器
        channel.pipeline().addLast(new MyDecoder());
        //自定义编码器
        channel.pipeline().addLast(new MyEncoder());
        //在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyServerHandler());
    }

}
```

>MyServerHandler.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
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

        ctx.writeAndFlush("hi I'm ok");
    }

}
```

## 测试结果

>启动NettyServer

```java
itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}
链接报告开始 {公众号：bugstack虫洞栈 >获取学习源码}
链接报告信息：有一客户端链接到本服务端
链接报告IP:127.0.0.1
链接报告Port:7397
链接报告完毕
2019-08-28 14:40:01 接收到消息：hihi  -整包测试
2019-08-28 14:40:16 接收到消息：hihi  -半包测试
2019-08-28 14:40:23 接收到消息：hihi  -粘包测试
2019-08-28 14:40:27 接收到消息：hihi  -粘包测试

Process finished with exit code -1
```

>启动模拟器NetAssist，用TcpClient链接服务端

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-1-09-2-1.png)

>分别发送三组数据；

```java
02；开始位
03；结束位
34；变量，内容长度位

第一组；整包测试数据：
02 34 68 69 68 69 03

第二组；半包测试数据
02 34 68 69 68 69
03

第三组：粘包测试数据
02 34 68 69 68 69 03 02 34
68 69 68 69 03
```

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！