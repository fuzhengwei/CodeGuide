---
layout: post
category: itstack-demo-netty-4
title: netty案例，netty4.1源码分析篇五《一行简单的writeAndFlush都做了哪些事》
tagline: by 付政委
tag: [netty,itstack-demo-netty-4]
lock: need
---

## 前言介绍
对于使用netty的小伙伴来说，ctx.writeAndFlush()再熟悉不过了，它可以将我们的消息发送出去。那么它都执行了那些行为呢，是怎么将消息发送出去的呢。

```java
                                                I/O Request
                                           via {@link Channel} or
                                       {@link ChannelHandlerContext}
                                                     |
 +---------------------------------------------------+---------------+
 |                           ChannelPipeline         |               |
 |                                                  \|/              |
 |    +---------------------+            +-----------+----------+    |
 |    | Inbound Handler  N  |            | Outbound Handler  1  |    |
 |    +----------+----------+            +-----------+----------+    |
 |              /|\                                  |               |
 |               |                                  \|/              |
 |    +----------+----------+            +-----------+----------+    |
 |    | Inbound Handler N-1 |            | Outbound Handler  2  |    |
 |    +----------+----------+            +-----------+----------+    |
 |              /|\                                  .               |
 |               .                                   .               |
 | ChannelHandlerContext.fireIN_EVT() ChannelHandlerContext.OUT_EVT()|
 |        [ method call]                       [method call]         |
 |               .                                   .               |
 |               .                                  \|/              |
 |    +----------+----------+            +-----------+----------+    |
 |    | Inbound Handler  2  |            | Outbound Handler M-1 |    |
 |    +----------+----------+            +-----------+----------+    |
 |              /|\                                  |               |
 |               |                                  \|/              |
 |    +----------+----------+            +-----------+----------+    |
 |    | Inbound Handler  1  |            | Outbound Handler  M  |    |
 |    +----------+----------+            +-----------+----------+    |
 |              /|\                                  |               |
 +---------------+-----------------------------------+---------------+
                 |                                  \|/
 +---------------+-----------------------------------+---------------+
 |               |                                   |               |
 |       [ Socket.read() ]                    [ Socket.write() ]     |
 |                                                                   |
 |  Netty Internal I/O Threads (Transport Implementation)            |
 +-------------------------------------------------------------------+
```

## 源码分析

>1、由一行简单发送消息开始

发送消息的代码非常简单，也是我们非常常用的发送消息的方式ctx.writeAndFlush

```java
@Override
public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	//接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
	System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
	//通知客户端链消息发送成功
	String str = "客户端收到[微信公众号：bugstack虫洞栈]：" + new Date() + " " + msg + "\r\n";
	ctx.writeAndFlush(str);
}
```

>2、跟进writeAndFlush | ChannelHandlerContext.writeAndFlush

**AbstractChannelHandlerContext.java**

```java
@Override
public ChannelFuture writeAndFlush(Object msg) {
	return writeAndFlush(msg, newPromise());
}

@Override
public ChannelPromise newPromise() {
	return new DefaultChannelPromise(channel(), executor());
}
```

在这段代码中我们可以看到，writeAndFlush方法里提供了一个默认的newPromise()作为参数传递。｛promise：v. 许诺;承诺;答应;保证;使很可能;预示｝在Netty中发送消息是一个异步操作，那么可以通过往hannelPromise中注册回调监听listener来得到该操作是否成功。

**在发送消息时添加监听**

```java
ctx.writeAndFlush("hi 微信公众号：bugstack虫洞栈 | 欢迎关注&获取专题源码", ctx.newProgressivePromise().addListener(new ChannelFutureListener() {
    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        future.isSuccess();
    }
}));
```

>3、继续向下一层跟进代码 | AbstractChannelHandlerContext.invokeWriteAndFlush

```java
private void invokeWriteAndFlush(Object msg, ChannelPromise promise) {
    if (invokeHandler()) {
        invokeWrite0(msg, promise);
        invokeFlush0();
    } else {
        writeAndFlush(msg, promise);
    }
}
```

3.1、首先通过invokeHandler()判断通道处理器已添加到管道

```java
Makes best possible effort to detect if {@link ChannelHandler#handlerAdded(ChannelHandlerContext)} was called
yet. If not return {@code false} and if called or could not detect return {@code true}.
If this method returns {@code false} we will not invoke the {@link ChannelHandler} but just forward the event.
This is needed as {@link DefaultChannelPipeline} may already put the {@link ChannelHandler} in the linked-list
but not called {@link ChannelHandler#handlerAdded(ChannelHandlerContext)}.
```

3.2、执行消息处理
invokeWrite0；首先将消息内容放入输出缓冲区中[ChannelOutboundBuffer]
invokeFlush0；然后将输出缓冲区中的数据通过socket发送到网络中

>4、分析invokeWrite0执行内容 | AbstractChannelHandlerContext.invokeWrite0

```java
private void invokeWrite0(Object msg, ChannelPromise promise) {
    try {
        ((ChannelOutboundHandler) handler()).write(this, msg, promise);
    } catch (Throwable t) {
        notifyOutboundHandlerException(t, promise);
    }
}
```

((ChannelOutboundHandler) handler()).write是一个出站事件[ChannelOutboundHandler]，会由ChannelOutboundHandlerAdapter处理；

```java
/**
 * Calls {@link ChannelHandlerContext#write(Object, ChannelPromise)} to forward
 * to the next {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
 *
 * Sub-classes may override this method to change behavior.
 */
@Skip
@Override
public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    ctx.write(msg, promise);
}
```

接下来会走到ChannelPipeline中，来执行网络数据发送；| DefaultChannelPipeline > HeadContext.write

```java
@Override
public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
    unsafe.write(msg, promise);
}
```

>5、unsafe.write执行分析

unsafe是我们构建NioServerSocketChannel或NioSocketChannel对象时，一并构建一个成员属性，它会完成底层真正的网络操作等。NioServerSocketChannel中持有的unsafe成员变量是NioMessageUnsafe对象，而NioSocketChannel中持有的unsafe成员变量是NioSocketChannelUnsafe对象。这里我们要看的是NioSocketChannel的write流程

```java
@Override
public final void write(Object msg, ChannelPromise promise) {
    assertEventLoop();
    ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
    if (outboundBuffer == null) {
        // If the outboundBuffer is null we know the channel was closed and so
        // need to fail the future right away. If it is not null the handling of th
        // will be done in flush0()
        // See https://github.com/netty/netty/issues/2362
        safeSetFailure(promise, newWriteException(initialCloseCause));
        // release message now to prevent resource-leak
        ReferenceCountUtil.release(msg);
        return;
    }
    int size;
    try {
        msg = filterOutboundMessage(msg);
        size = pipeline.estimatorHandle().size(msg);
        if (size < 0) {
            size = 0;
        }
    } catch (Throwable t) {
        safeSetFailure(promise, t);
        ReferenceCountUtil.release(msg);
        return;
    }
    outboundBuffer.addMessage(msg, size, promise);
}
```

**https://github.com/netty/netty/issues/2362**

![](https://bugstack.cn/assets/images/pic-content/2019/09/netty4-1.png)

- 获取该NioSocketChannel的ChannelOutboundBuffer成员属性。（确切地来说ChannelOutboundBuffer是NioSocketChannelUnsafe对象中的成员属性，而NioSocketChannelUnsafe才是NioSocketChannel的成员属性。）每一个NioSocketChannel会维护一个它们自己的ChannelOutboundBuffer，用于存储待出站写请求。
判断该outboundBuffer是否为null，如果为null则说明该NioSocketChannel已经关闭了，那么就会标志该异步写操作为失败完成，并释放写消息后返回。

**AbstractNioByteChannel.java** | filterOutboundMessage过滤待发送的消息：

```java
@Override
protected final Object filterOutboundMessage(Object msg) {
    if (msg instanceof ByteBuf) {
        ByteBuf buf = (ByteBuf) msg;
        if (buf.isDirect()) {
            return msg;
        }
        return newDirectBuffer(buf);
    }
    if (msg instanceof FileRegion) {
        return msg;
    }
    throw new UnsupportedOperationException(
            "unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
}
```

过滤待发送的消息，只有ByteBuf（堆 or 非堆）以及 FileRegion可以进行最终的Socket网络传输，其他类型的数据是不支持的，会抛UnsupportedOperationException异常。并且会把堆ByteBuf转换为一个非堆的ByteBuf返回。也就说，最后会通过socket传输的对象时非堆的ByteBuf和FileRegion。
[size = pipeline.estimatorHandle().size(msg);]估计待发送数据的大小：

**DefaultMessageSizeEstimator.java** | 通过ByteBuf.readableBytes()判断消息内容大小，估计待发送消息数据的大小，如果是FileRegion的话直接饭0，否则返回ByteBuf中可读取字节数。

```java
private static final class HandleImpl implements Handle {
    private final int unknownSize;
    private HandleImpl(int unknownSize) {
        this.unknownSize = unknownSize;
    }
    @Override
    public int size(Object msg) {
        if (msg instanceof ByteBuf) {
            return ((ByteBuf) msg).readableBytes();
        }
        if (msg instanceof ByteBufHolder) {
            return ((ByteBufHolder) msg).content().readableBytes()
        }
        if (msg instanceof FileRegion) {
            return 0;
        }
        return unknownSize;
    }
}
```

**ChannelOutboundBuffer.java** | ChannelOutboundBuffer.addMessage将消息加入outboundBuffer中等待发送

```java
/**
 * Add given message to this {@link ChannelOutboundBuffer}. The given {@link ChannelPromise} will be notified once
 * the message was written.
 */
public void addMessage(Object msg, int size, ChannelPromise promise) {
    Entry entry = Entry.newInstance(msg, size, total(msg), promise);
    if (tailEntry == null) {
        flushedEntry = null;
    } else {
        Entry tail = tailEntry;
        tail.next = entry;
    }
    tailEntry = entry;
    if (unflushedEntry == null) {
        unflushedEntry = entry;
    }
    // increment pending bytes after adding message to the unflushed arrays.
    // See https://github.com/netty/netty/issues/1619
    incrementPendingOutboundBytes(entry.pendingSize, false);
}
```

>6、ChannelOutboundBuffer出栈

一个内部的数据结构，被AbstractChannel用于存储它的待出站写请求。
ChannelOutboundBuffer中有两个属性private Entry unflushedEntry、private Entry flushedEntry。它们都是用Entry对象通过next指针来维护的一个单向链表。以及一个private Entry tailEntry;对象表示始终指向最后一个Entry对象（即，最后加入到该ChannelOutboundBuffer中的写请求的数据消息）
unflushedEntry表示还未刷新的ByteBuf的链表头；flushedEntry表示调用flush()操作时将会进行刷新的ByteBuf的链表头。

>7、Entry对象

```java
static final class Entry {
	private static final Recycler<Entry> RECYCLER = new Recycler<Entry>() {
		@Override
		protected Entry newObject(Handle<Entry> handle) {
			return new Entry(handle);
		}
	};

	private final Handle<Entry> handle;
	Entry next;
	Object msg;
	ByteBuffer[] bufs;
	ByteBuffer buf;
	ChannelPromise promise;
	long progress;
	long total;
	int pendingSize;
	int count = -1;
	boolean cancelled;

	private Entry(Handle<Entry> handle) {
		this.handle = handle;
	}

	static Entry newInstance(Object msg, int size, long total, ChannelPromise promise) {
		Entry entry = RECYCLER.get();
		entry.msg = msg;
		entry.pendingSize = size + CHANNEL_OUTBOUND_BUFFER_ENTRY_OVERHEAD;
		entry.total = total;
		entry.promise = promise;
		return entry;
	}

	int cancel() {
		if (!cancelled) {
			cancelled = true;
			int pSize = pendingSize;

			// release message and replace with an empty buffer
			ReferenceCountUtil.safeRelease(msg);
			msg = Unpooled.EMPTY_BUFFER;

			pendingSize = 0;
			total = 0;
			progress = 0;
			bufs = null;
			buf = null;
			return pSize;
		}
		return 0;
	}

	void recycle() {
		next = null;
		bufs = null;
		buf = null;
		msg = null;
		promise = null;
		progress = 0;
		total = 0;
		pendingSize = 0;
		count = -1;
		cancelled = false;
		handle.recycle(this);
	}

	Entry recycleAndGetNext() {
		Entry next = this.next;
		recycle();
		return next;
	}
}
```
Entry是ChannelOutboundBunffer的一个内部类，它是对真实的写消息数据以及其相关信息的一个封装。大致封装了如下信息：
a) pendingSize：记录有该ByteBuf or ByteBufs 中待发送数据大小 和 对象本身内存大小 的累加和;
b) promise：该异步写操作的ChannelPromise（用于在完成真是的网络层write后去标识异步操作的完成以及回调已经注册到该promise上的listeners）;
c) total：待发送数据包的总大小（该属性与pendingSize的区别在于，如果是待发送的是FileRegion数据对象，则pengdingSize中只有对象内存的大小，即真实的数据大小被记录为0；但total属性则是会记录FileRegion中数据大小，并且total属性是不包含对象内存大小，仅仅是对数据本身大小的记录）;
e) msg：原始消息对象的引用;
f) count：写消息数据个数的记录（如果写消息数据是个数组的话，该值会大于1）
这里说明下，pendingSize属性记录的不单单是写请求数据的大小，记录的是这个写请求对象的大小。这是什么意思了？这里做个简单的介绍：
一个对象占用的内存大小除了实例数据（instance data），还包括对象头（header）以及对齐填充（padding）。所以一个对象所占的内存大小为『对象头 + 实例数据 + 对齐填充』，即

**CHANNEL_OUTBOUND_BUFFER_ENTRY_OVERHEAD** 

```java
// Assuming a 64-bit JVM:
//  - 16 bytes object header
//  - 8 reference fields
//  - 2 long fields
//  - 2 int fields
//  - 1 boolean field
//  - padding
static final int CHANNEL_OUTBOUND_BUFFER_ENTRY_OVERHEAD =
        SystemPropertyUtil.getInt("io.netty.transport.outboundBufferEntrySizeOverhead", 96);
```

假设的是64位操作系统下，且没有使用各种压缩选项的情况。对象头的长度占16字节；引用属性占8字节；long类型占8字节；int类型占4字节；boolean类型占1字节。同时，由于HotSpot VM的自动内存管理系统要求对象起始地址必须是8字节的整数倍，也就是说对象的大小必须是8字节的整数倍，如果最终字节数不为8的倍数，则padding会补足至8的倍数。

addMessage方法主要就是将请求写出的数据封装为Entry对象，然后加入到tailEntry和unflushedEntry中。
然后调用『incrementPendingOutboundBytes(entry.pendingSize, false);』对totalPendingSize属性以及unwritable字段做调整。
totalPendingSize字段记录了该ChannelOutboundBuffer中所有带发送Entry对象的占的总内存大小和所有带发送数据的大小。unwritable用来标示当前该Channel要发送的数据是否已经超过了设定 or 默认的WriteBufferWaterMark的high值。如果当前操作导致了待写出的数据（包括Entry对象大小以及真实需要传输数据的大小）超过了设置写缓冲区的高水位，那么将会触发fireChannelWritabilityChanged事件。

------------

上一篇：[netty案例，netty4.1源码分析篇四《ByteBuf的数据结构在使用方式中的剖析》](/itstack-demo-netty-4/2019/09/13/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E5%9B%9B-ByteBuf%E7%9A%84%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E5%9C%A8%E4%BD%BF%E7%94%A8%E6%96%B9%E5%BC%8F%E4%B8%AD%E7%9A%84%E5%89%96%E6%9E%90.html)

下一篇：[netty案例，netty4.1源码分析篇六《Netty异步架构监听类Promise源码分析》](/itstack-demo-netty-4/2019/09/15/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E5%85%AD-Netty%E5%BC%82%E6%AD%A5%E6%9E%B6%E6%9E%84%E7%9B%91%E5%90%AC%E7%B1%BBPromise%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**rpc案例源码**」获取本文源码&更多原创专题案例！

