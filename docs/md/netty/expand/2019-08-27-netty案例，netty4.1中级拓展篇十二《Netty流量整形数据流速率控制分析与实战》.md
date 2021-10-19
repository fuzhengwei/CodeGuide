---
layout: post
category: itstack-demo-netty-2
title: netty案例，netty4.1中级拓展篇十二《Netty流量整形数据流速率控制分析与实战》
tagline: by 付政委
tag: [netty,itstack-demo-netty-2] 
lock: need
---

# netty案例，netty4.1中级拓展篇十二《Netty流量整形数据流速率控制分析与实战》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
你是否使用过某盘，在前几年我们使用的时候速度飞快，上传下载嗖嗖嗖。但是近年来只要不办会员，速度慢的像蜗牛，当然人家也得赚钱我们能理解。那么这样的限速是怎么实现的呢，我们这个案例使用Netty的流量整形进行限速传输，测试过程中当你把流量整形功能去掉后你就是年费VIP。

流量整形（Traffic Shaping）是一种主动调整流量输出速率的措施。一个典型应用是基于下游网络结点的TP指标来控制本地流量的输出。流量整形与流量监管的主要区别在于，流量整形对流量监管中需要丢弃的报文进行缓存——通常是将它们放入缓冲区或队列内，也称流量整形（Traffic Shaping，简称TS）。当令牌桶有足够的令牌时，再均匀的向外发送这些被缓存的报文。流量整形与流量监管的另一区别是，整形可能会增加延迟，而监管几乎不引入额外的延迟。
![微信公众号：bugstack虫洞栈](https://bugstack.cn/assets/images/pic-content/2019/09/netty-2-12-1.png)

Netty中通过实现抽象类AbstractTrafficShapingHandler，提供了三个流量整形的类；GlobalTrafficShapingHandler、ChannelTrafficShapingHandler、GlobalChannelTrafficShapingHandler；

>AbstractTrafficShapingHandler.java | 功能介绍

```java
/**
 * <p>AbstractTrafficShapingHandler allows to limit the global bandwidth
 * (see {@link GlobalTrafficShapingHandler}) or per session
 * bandwidth (see {@link ChannelTrafficShapingHandler}), as traffic shaping.
 * It allows you to implement an almost real time monitoring of the bandwidth using
 * the monitors from {@link TrafficCounter} that will call back every checkInterval
 * the method doAccounting of this handler.</p>
 *
 * <p>If you want for any particular reasons to stop the monitoring (accounting) or to change
 * the read/write limit or the check interval, several methods allow that for you:</p>
 * <ul>
 * <li><tt>configure</tt> allows you to change read or write limits, or the checkInterval</li>
 * <li><tt>getTrafficCounter</tt> allows you to have access to the TrafficCounter and so to stop
 * or start the monitoring, to change the checkInterval directly, or to have access to its values.</li>
 * </ul>
 */
public abstract class AbstractTrafficShapingHandler extends ChannelDuplexHandler{...}
```

AbstractTrafficShapingHandler允许限制全局的带宽(见GlobalTrafficShapingHandler)或者每个session的带宽(见ChannelTrafficShapingHandler)作为流量整形。
它允许你使用TrafficCounter来实现几乎实时的带宽监控，TrafficCounter会在每个检测间期（checkInterval）调用这个处理器的doAccounting方法。

如果你有任何特别的原因想要停止监控（计数）或者改变读写的限制或者改变检测间期（checkInterval），可以使用如下方法：
1. configure：允许你改变读或写的限制，或者检测间期（checkInterval）；
2. getTrafficCounter：允许你获得TrafficCounter，并可以停止或启动监控，直接改变检测间期（checkInterval），或去访问它的值。

**TrafficCounter**：对读和写的字节进行计数以用于限制流量。
它会根据给定的检测间期周期性的计算统计入站和出站的流量，并会回调AbstractTrafficShapingHandler的doAccounting方法。
如果检测间期（checkInterval）是0，将不会进行计数并且统计只会在每次读或写操作时进行计算。

>GlobalTrafficShapingHandler.java | 全局限制

```java
/**
 * <p>This implementation of the {@link AbstractTrafficShapingHandler} is for global
 * traffic shaping, that is to say a global limitation of the bandwidth, whatever
 * the number of opened channels.</p>
 * <p>Note the index used in {@code OutboundBuffer.setUserDefinedWritability(index, boolean)} is <b>2</b>.</p>
 *
 * <p>The general use should be as follow:</p>
 * <ul>
 * <li><p>Create your unique GlobalTrafficShapingHandler like:</p>
 * <p><tt>GlobalTrafficShapingHandler myHandler = new GlobalTrafficShapingHandler(executor);</tt></p>
 * <p>The executor could be the underlying IO worker pool</p>
 * <p><tt>pipeline.addLast(myHandler);</tt></p>
 *
 * <p><b>Note that this handler has a Pipeline Coverage of "all" which means only one such handler must be created
 * and shared among all channels as the counter must be shared among all channels.</b></p>
 *
 * <p>Other arguments can be passed like write or read limitation (in bytes/s where 0 means no limitation)
 * or the check interval (in millisecond) that represents the delay between two computations of the
 * bandwidth and so the call back of the doAccounting method (0 means no accounting at all).</p>
 *
 * <p>A value of 0 means no accounting for checkInterval. If you need traffic shaping but no such accounting,
 * it is recommended to set a positive value, even if it is high since the precision of the
 * Traffic Shaping depends on the period where the traffic is computed. The highest the interval,
 * the less precise the traffic shaping will be. It is suggested as higher value something close
 * to 5 or 10 minutes.</p>
 *
 * <p>maxTimeToWait, by default set to 15s, allows to specify an upper bound of time shaping.</p>
 * </li>
 * <li>In your handler, you should consider to use the {@code channel.isWritable()} and
 * {@code channelWritabilityChanged(ctx)} to handle writability, or through
 * {@code future.addListener(new GenericFutureListener())} on the future returned by
 * {@code ctx.write()}.</li>
 * <li><p>You shall also consider to have object size in read or write operations relatively adapted to
 * the bandwidth you required: for instance having 10 MB objects for 10KB/s will lead to burst effect,
 * while having 100 KB objects for 1 MB/s should be smoothly handle by this TrafficShaping handler.</p></li>
 * <li><p>Some configuration methods will be taken as best effort, meaning
 * that all already scheduled traffics will not be
 * changed, but only applied to new traffics.</p>
 * So the expected usage of those methods are to be used not too often,
 * accordingly to the traffic shaping configuration.</li>
 * </ul>
 *
 * Be sure to call {@link #release()} once this handler is not needed anymore to release all internal resources.
 * This will not shutdown the {@link EventExecutor} as it may be shared, so you need to do this by your own.
 */
@Sharable
public class GlobalTrafficShapingHandler extends AbstractTrafficShapingHandler {}
```

这实现了AbstractTrafficShapingHandler的全局流量整形，也就是说它限制了全局的带宽，无论开启了几个channel。
注意`『 OutboundBuffer.setUserDefinedWritability(index, boolean)』`中索引使用’2’。

一般用途如下：
创建一个唯一的GlobalTrafficShapingHandler

```java
GlobalTrafficShapingHandler myHandler = new GlobalTrafficShapingHandler(executor);
pipeline.addLast(myHandler);
```
executor可以是底层的IO工作池

注意，这个处理器是覆盖所有管道的，这意味着只有一个处理器对象会被创建并且作为所有channel间共享的计数器，它必须于所有的channel共享。
所有你可以见到，该类的定义上面有个@Sharable注解。

在你的处理器中，你需要考虑使用『channel.isWritable()』和『channelWritabilityChanged(ctx)』来处理可写性，或通过在ctx.write()返回的future上注册listener来实现。

你还需要考虑读或写操作对象的大小需要和你要求的带宽相对应：比如，你将一个10M大小的对象用于10KB/s的带宽将会导致爆发效果，若你将100KB大小的对象用于在1M/s带宽那么将会被流量整形处理器平滑处理。

一旦不在需要这个处理器时请确保调用『release()』以释放所有内部的资源。这不会关闭EventExecutor，因为它可能是共享的，所以这需要你自己做。

GlobalTrafficShapingHandler中持有一个Channel的哈希表，用于存储当前应用所有的Channel：

```java
private final ConcurrentMap<Integer, PerChannel> channelQueues = PlatformDependent.newConcurrentHashMap();
```
key为Channel的hashCode；value是一个PerChannel对象。
PerChannel对象中维护有该Channel的待发送数据的消息队列`ArrayDeque<ToSend> messagesQueue`。

>ChannelTrafficShapingHandler.java | 功能介绍

```java
/**
 * <p>This implementation of the {@link AbstractTrafficShapingHandler} is for channel
 * traffic shaping, that is to say a per channel limitation of the bandwidth.</p>
 * <p>Note the index used in {@code OutboundBuffer.setUserDefinedWritability(index, boolean)} is <b>1</b>.</p>
 *
 * <p>The general use should be as follow:</p>
 * <ul>
 * <li><p>Add in your pipeline a new ChannelTrafficShapingHandler.</p>
 * <p><tt>ChannelTrafficShapingHandler myHandler = new ChannelTrafficShapingHandler();</tt></p>
 * <p><tt>pipeline.addLast(myHandler);</tt></p>
 *
 * <p><b>Note that this handler has a Pipeline Coverage of "one" which means a new handler must be created
 * for each new channel as the counter cannot be shared among all channels.</b>.</p>
 *
 * <p>Other arguments can be passed like write or read limitation (in bytes/s where 0 means no limitation)
 * or the check interval (in millisecond) that represents the delay between two computations of the
 * bandwidth and so the call back of the doAccounting method (0 means no accounting at all).</p>
 *
 * <p>A value of 0 means no accounting for checkInterval. If you need traffic shaping but no such accounting,
 * it is recommended to set a positive value, even if it is high since the precision of the
 * Traffic Shaping depends on the period where the traffic is computed. The highest the interval,
 * the less precise the traffic shaping will be. It is suggested as higher value something close
 * to 5 or 10 minutes.</p>
 *
 * <p>maxTimeToWait, by default set to 15s, allows to specify an upper bound of time shaping.</p>
 * </li>
 * <li>In your handler, you should consider to use the {@code channel.isWritable()} and
 * {@code channelWritabilityChanged(ctx)} to handle writability, or through
 * {@code future.addListener(new GenericFutureListener())} on the future returned by
 * {@code ctx.write()}.</li>
 * <li><p>You shall also consider to have object size in read or write operations relatively adapted to
 * the bandwidth you required: for instance having 10 MB objects for 10KB/s will lead to burst effect,
 * while having 100 KB objects for 1 MB/s should be smoothly handle by this TrafficShaping handler.</p></li>
 * <li><p>Some configuration methods will be taken as best effort, meaning
 * that all already scheduled traffics will not be
 * changed, but only applied to new traffics.</p>
 * <p>So the expected usage of those methods are to be used not too often,
 * accordingly to the traffic shaping configuration.</p></li>
 * </ul>
 */
public class ChannelTrafficShapingHandler extends AbstractTrafficShapingHandler {...}
```
ChannelTrafficShapingHandler是针对单个Channel的流量整形，和GlobalTrafficShapingHandler的思想是一样的。只是实现中没有对全局概念的检测，仅检测了当前这个Channel的数据。
这里就不再赘述了。


>GlobalChannelTrafficShapingHandler.java | 功能介绍

```java
/**
 * This implementation of the {@link AbstractTrafficShapingHandler} is for global
 * and per channel traffic shaping, that is to say a global limitation of the bandwidth, whatever
 * the number of opened channels and a per channel limitation of the bandwidth.<br><br>
 * This version shall not be in the same pipeline than other TrafficShapingHandler.<br><br>
 *
 * The general use should be as follow:<br>
 * <ul>
 * <li>Create your unique GlobalChannelTrafficShapingHandler like:<br><br>
 * <tt>GlobalChannelTrafficShapingHandler myHandler = new GlobalChannelTrafficShapingHandler(executor);</tt><br><br>
 * The executor could be the underlying IO worker pool<br>
 * <tt>pipeline.addLast(myHandler);</tt><br><br>
 *
 * <b>Note that this handler has a Pipeline Coverage of "all" which means only one such handler must be created
 * and shared among all channels as the counter must be shared among all channels.</b><br><br>
 *
 * Other arguments can be passed like write or read limitation (in bytes/s where 0 means no limitation)
 * or the check interval (in millisecond) that represents the delay between two computations of the
 * bandwidth and so the call back of the doAccounting method (0 means no accounting at all).<br>
 * Note that as this is a fusion of both Global and Channel Traffic Shaping, limits are in 2 sets,
 * respectively Global and Channel.<br><br>
 *
 * A value of 0 means no accounting for checkInterval. If you need traffic shaping but no such accounting,
 * it is recommended to set a positive value, even if it is high since the precision of the
 * Traffic Shaping depends on the period where the traffic is computed. The highest the interval,
 * the less precise the traffic shaping will be. It is suggested as higher value something close
 * to 5 or 10 minutes.<br><br>
 *
 * maxTimeToWait, by default set to 15s, allows to specify an upper bound of time shaping.<br><br>
 * </li>
 * <li>In your handler, you should consider to use the {@code channel.isWritable()} and
 * {@code channelWritabilityChanged(ctx)} to handle writability, or through
 * {@code future.addListener(new GenericFutureListener())} on the future returned by
 * {@code ctx.write()}.</li>
 * <li>You shall also consider to have object size in read or write operations relatively adapted to
 * the bandwidth you required: for instance having 10 MB objects for 10KB/s will lead to burst effect,
 * while having 100 KB objects for 1 MB/s should be smoothly handle by this TrafficShaping handler.<br><br></li>
 * <li>Some configuration methods will be taken as best effort, meaning
 * that all already scheduled traffics will not be
 * changed, but only applied to new traffics.<br>
 * So the expected usage of those methods are to be used not too often,
 * accordingly to the traffic shaping configuration.</li>
 * </ul><br>
 *
 * Be sure to call {@link #release()} once this handler is not needed anymore to release all internal resources.
 * This will not shutdown the {@link EventExecutor} as it may be shared, so you need to do this by your own.
 */
@Sharable
public class GlobalChannelTrafficShapingHandler extends AbstractTrafficShapingHandler {...}
```

相比于GlobalTrafficShapingHandler增加了一个误差概念，以平衡各个Channel间的读/写操作。也就是说，使得各个Channel间的读/写操作尽量均衡。比如，尽量避免不同Channel的大数据包都延迟近乎一样的是时间再操作，以及如果小数据包在一个大数据包后才发送，则减少该小数据包的延迟发送时间等。。

## 开发环境
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】

## 代码示例
```java
itstack-demo-netty-2-12
└── src
    ├── main
    │   └── java
    │       └── org.itstack.demo.netty
    │           ├── client
    │           │	├── MyChannelInitializer.java
    │           │	├── MyClientHandler.java
    │           │	└── NettyClient.java
    │           └── server
    │           	├── common
    │           	│	└── MyServerCommonHandler.java	
    │           	├── MyChannelInitializer.java
    │           	├── MyServerHandler.java	
    │           	└── NettyServer.java
    │
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```

**部分重点代码块讲解，获取全部代码，关注公众号：bugstack虫洞栈 | 回复netty源码**

>client/MyChannelInitializer.java | 增加Channel流量整形配置，速率设置为10bytes/s

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //流量整形
        channel.pipeline().addLast(new ChannelTrafficShapingHandler(10, 10));
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

>server/common/MyServerCommonHandler.java | 提供抽象类，监控发送速率以及获取发送状态

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈 | 欢迎关注并获取专题&源码
 * Create by fuzhengwei on 2019
 */
public abstract class MyServerCommonHandler extends SimpleChannelInboundHandler<String> {

    protected boolean sentFlag;
    private Runnable counterTask;
    private AtomicLong consumeMsgLength = new AtomicLong();
    private long priorProgress;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        counterTask = () -> {
            while (true) {
                try {
                    Thread.sleep(500);
                    long length = consumeMsgLength.getAndSet(0);
                    if (0 == length) continue;
                    System.out.println("数据发送速率(KB/S)：" + length);
                } catch (InterruptedException ignored) {
                }
            }
        };
        super.handlerAdded(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        sendData(ctx);
        //启动监控线程
        new Thread(counterTask).start();
    }

    protected abstract void sendData(ChannelHandlerContext ctx);

    protected ChannelProgressivePromise getChannelProgressivePromise(ChannelHandlerContext ctx, Consumer<ChannelProgressiveFuture> completedAction) {
        ChannelProgressivePromise channelProgressivePromise = ctx.newProgressivePromise();
        channelProgressivePromise.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                consumeMsgLength.addAndGet(progress - priorProgress);
                priorProgress = progress;
            }

            @Override
            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                sentFlag = false;
                if (future.isSuccess()) {
                    System.out.println("微信公众号：bugstack虫洞栈 | 提醒，消息发送成功！");
                    priorProgress -= 10;
                    Optional.ofNullable(completedAction).ifPresent(action -> action.accept(future));
                } else {
                    System.out.println("微信公众号：bugstack虫洞栈 | 提醒，消息发送失败！");
                    future.cause().printStackTrace();
                }
            }
        });
        return channelProgressivePromise;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("微信公众号：bugstack虫洞栈 | NettyServer接收到消息：" + msg);
    }

}
```

>server/MyChannelInitializer.java | 增加全局流量整形配置，速率设置为10bytes/s

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈 | 欢迎关注并获取专题&源码
 * Create by fuzhengwei on 2019
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {

        // 基于换行符号
        channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 流量整形；writeLimit/readLimit{0 or a limit in bytes/s}
        channel.pipeline().addLast(new GlobalTrafficShapingHandler(channel.eventLoop().parent(), 10, 10));
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        channel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        channel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyServerHandler());

    }

}

```

>server/MyServerHandler.java | 处理消息验证是否可以发送ctx.channel().isWritable()

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈 | 欢迎关注并获取专题&源码
 * Create by fuzhengwei on 2019
 */
public class MyServerHandler extends MyServerCommonHandler {

    @Override
    protected void sendData(ChannelHandlerContext ctx) {
        sentFlag = true;
        ctx.writeAndFlush( "111111111122222222223333333333\r\n", getChannelProgressivePromise(ctx, new Consumer<ChannelProgressiveFuture>() {
            @Override
            public void accept(ChannelProgressiveFuture channelProgressiveFuture) {
                if (ctx.channel().isWritable() && !sentFlag) {
                    sendData(ctx);
                }
            }
        }));
    }

}
```

## 测试结果

>启动服务端NettyServer | 可以看到速率已经被限制

```java
itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈 | 获取专题案例源码}
微信公众号：bugstack虫洞栈 | 提醒，消息发送成功！
数据发送速率(KB/S)：32
微信公众号：bugstack虫洞栈 | 提醒，消息发送成功！
数据发送速率(KB/S)：10
微信公众号：bugstack虫洞栈 | NettyServer接收到消息：876d251b-aba8-481a-81d0-e123a4c42214
微信公众号：bugstack虫洞栈 | 提醒，消息发送成功！
数据发送速率(KB/S)：10
微信公众号：bugstack虫洞栈 | NettyServer接收到消息：250d53fb-acc3-4390-b5c5-a660577fff6f
微信公众号：bugstack虫洞栈 | 提醒，消息发送成功！
数据发送速率(KB/S)：10
微信公众号：bugstack虫洞栈 | NettyServer接收到消息：89cad8a0-8e5b-44ef-812b-39c4b2d2e0fb
微信公众号：bugstack虫洞栈 | 提醒，消息发送成功！
数据发送速率(KB/S)：10
微信公众号：bugstack虫洞栈 | NettyServer接收到消息：e951ca01-a583-4c20-b884-5c272b1cc7a4
微信公众号：bugstack虫洞栈 | 提醒，消息发送成功！
数据发送速率(KB/S)：10
微信公众号：bugstack虫洞栈 | NettyServer接收到消息：4b13d77c-188f-4613-9cd9-94a2a7751932
微信公众号：bugstack虫洞栈 | 提醒，消息发送成功！
数据发送速率(KB/S)：10
微信公众号：bugstack虫洞栈 | 提醒，消息发送成功！
微信公众号：bugstack虫洞栈 | NettyServer接收到消息：fdc5378c-a594-4be8-885d-4caa7ecccd82
数据发送速率(KB/S)：10

Process finished with exit code -1
```


>启动客户端NettyClient | 可以看到速率已经被限制

```java
itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈 | 获取专题案例源码}
微信公众号：bugstack虫洞栈 | NettyClient接收到消息：111111111122222222223333333333 length：30
微信公众号：bugstack虫洞栈 | NettyClient接收到消息：111111111122222222223333333333 length：30
微信公众号：bugstack虫洞栈 | NettyClient接收到消息：111111111122222222223333333333 length：30
微信公众号：bugstack虫洞栈 | NettyClient接收到消息：111111111122222222223333333333 length：30
微信公众号：bugstack虫洞栈 | NettyClient接收到消息：111111111122222222223333333333 length：30
微信公众号：bugstack虫洞栈 | NettyClient接收到消息：111111111122222222223333333333 length：30
微信公众号：bugstack虫洞栈 | NettyClient接收到消息：111111111122222222223333333333 length：30
微信公众号：bugstack虫洞栈 | NettyClient接收到消息：111111111122222222223333333333 length：30

Process finished with exit code -1
```

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！