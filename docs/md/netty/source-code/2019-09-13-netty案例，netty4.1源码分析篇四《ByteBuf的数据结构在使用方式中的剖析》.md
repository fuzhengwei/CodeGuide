---
layout: post
category: itstack-demo-netty-4
title: netty案例，netty4.1源码分析篇四《ByteBuf的数据结构在使用方式中的剖析》
tagline: by 付政委
tag: [netty,itstack-demo-netty-4]
lock: need
---

## 前言介绍
在Netty中ByteBuf是一个非常重要的类，它可以以高效易用的数据结构方式来满足网络通信过程中处理数据包内字节码序列的移动。

## 数据结构

```java
 +-------------------+------------------+------------------+
 | discardable bytes |  readable bytes  |  writable bytes  |
 |                   |     (CONTENT)    |                  |
 +-------------------+------------------+------------------+
 |                   |                  |                  |
 0      <=      readerIndex   <=   writerIndex    <=    capacity
```
那么这种数据结构之所以能高效的处理数据传输处理并解决半包粘包，主要得益于ByteBuf中有两个不同的索引；读索引(readIndex)、写索引(writerIndex)。
读索引：当我们从ByteBuf读取数据时，readerIndex指针位置也会指向到读取字节位置
写索引：当我们向ByteBuf写入数据时，writerIndex指针位置也会指向到写入字节位置

discardable bytes：当从ByteBuf读取一部分数据后，这部分数据就属于discardable，他们是可以被废弃的。
readable bytes：剩余可以继续读取的内容区域，就像一个长条的鸡蛋卡槽，一边放一边拿。从已经拿完到剩余的鸡蛋位置属于可拿区域。
writable bytes：同上一样，这一部分就是可以继续放置鸡蛋的位置。

## 功能案例

```java
// 62 75 67 73 74 61 63 6B B3 E6 B6 B4 D5 BB
public static void main(String[] args) throws UnsupportedEncodingException {
	// 1.创建一个非池化的ByteBuf，大小为14个字节
	ByteBuf buffer = Unpooled.buffer(14);
	System.out.println("1.创建一个非池化的ByteBuf，大小为14个字节");
	System.out.println("ByteBuf空间大小：" + buffer.capacity());
	// 2.写入3个字节
	buffer.writeByte(62);
	buffer.writeByte(75);
	buffer.writeByte(67);
	System.out.println("\r\n2.写入3个字节");
	System.out.println("readerIndex位置：" + buffer.readerIndex());
	System.out.println("writerIndex位置：" + buffer.writerIndex());
	// 3.写入一段字节
	byte[] bytes = {73, 74, 61, 63, 0x6B};
	buffer.writeBytes(bytes);
	System.out.println("\r\n3.写入一段字节");
	System.out.println("readerIndex位置：" + buffer.readerIndex());
	System.out.println("writerIndex位置：" + buffer.writerIndex());
	// 4.读取全部内容
	byte[] allBytes = new byte[buffer.readableBytes()];
	buffer.readBytes(allBytes);
	System.out.println("\r\n4.读取全部内容");
	System.out.println("readerIndex位置：" + buffer.readerIndex());
	System.out.println("writerIndex位置：" + buffer.writerIndex());
	System.out.println("读取全部内容：" + Arrays.toString(allBytes));
	// 5.重置指针位置
	buffer.resetReaderIndex();
	System.out.println("\r\n5.重置指针位置");
	System.out.println("readerIndex位置：" + buffer.readerIndex());
	System.out.println("writerIndex位置：" + buffer.writerIndex());
	// 6.读取3个字节
	byte b0 = buffer.readByte();
	byte b1 = buffer.readByte();
	byte b2 = buffer.readByte();
	System.out.println("\r\n6.读取3个字节");
	System.out.println("readerIndex位置：" + buffer.readerIndex());
	System.out.println("writerIndex位置：" + buffer.writerIndex());
	System.out.println("读取3个字节：" + Arrays.toString(new byte[]{b0, b1, b2}));
	// 7.读取一段字节
	ByteBuf byteBuf = buffer.readBytes(5);
	byte[] dst = new byte[5];
	byteBuf.readBytes(dst);
	System.out.println("\r\n7.读取一段字节");
	System.out.println("readerIndex位置：" + buffer.readerIndex());
	System.out.println("writerIndex位置：" + buffer.writerIndex());
	System.out.println("读取一段字节：" + Arrays.toString(dst));
	// 8.丢弃已读内容
	buffer.discardReadBytes();
	System.out.println("\r\n8.丢弃已读内容");
	System.out.println("readerIndex位置：" + buffer.readerIndex());
	System.out.println("writerIndex位置：" + buffer.writerIndex());
	// 9.清空指针位置
	buffer.clear();
	System.out.println("\r\n9.清空指针位置");
	System.out.println("readerIndex位置：" + buffer.readerIndex());
	System.out.println("writerIndex位置：" + buffer.writerIndex());
	// 10.ByteBuf中还有很多其他方法；拷贝、标记、跳过字节，多用于自定义解码器进行半包粘包处理
}
```

```java
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
```

## 内存模型

** 1、堆内内存（JVM堆空间内） **
最常用的ByteBuf模式是将数据存储在JVM的堆空间中。它能在没有使用池化的情况下提供快速的分配和释放。

** 2、堆外内存（本机直接内存）**
JDK允许JVM实现通过本地调用来分配内存。主要是为了避免每次调用本地I/O操作之前（或者之后）将缓冲区的内容复制到一个中间缓冲区（或者从中间缓冲区把内容复制到缓冲区）。

** 3、复合缓冲区（以上2种缓冲区多个混合）**
常用类：CompositeByteBuf，它为多个ByteBuf提供一个聚合视图，将多个缓冲区表示为单个合并缓冲区的虚拟表示。
比如：HTTP协议：头部和主体这两部分由应用程序的不同模块产生。这个时候把这两部分合并的话，选择CompositeByteBuf是比较好的。

## 源码解读

>ByteBuf实现了ReferenceCounted与Comparable两个接口

```java
public abstract class ByteBuf implements ReferenceCounted, Comparable<ByteBuf>
```

>创建一个指定容量大小堆缓冲区，并按需扩充容量{与list集和很像}，指针位置都是从0开始

```java
Unpooled.buffer(14);

/**
 * Creates a new big-endian Java heap buffer with the specified {@code capacity}, which
 * expands its capacity boundlessly on demand.  The new buffer's {@code readerIndex} and
 * {@code writerIndex} are {@code 0}.
 */
public static ByteBuf buffer(int initialCapacity) {
	return ALLOC.heapBuffer(initialCapacity);
}
```

>跟进代码的创建过程会发现，UnpooledHeapByteBuf.UnpooledHeapByteBuf用来创建非池化堆Buf

```java
**
 * Creates a new heap buffer with an existing byte array.
 *
 * @param initialArray the initial underlying byte array
 * @param maxCapacity the max capacity of the underlying byte array
 */
protected UnpooledHeapByteBuf(ByteBufAllocator alloc, byte[] initialArray, int maxCapacity) {
	super(maxCapacity);

	checkNotNull(alloc, "alloc");
	checkNotNull(initialArray, "initialArray");

	if (initialArray.length > maxCapacity) {
		throw new IllegalArgumentException(String.format(
				"initialCapacity(%d) > maxCapacity(%d)", initialArray.length, maxCapacity));
	}

	this.alloc = alloc;
	setArray(initialArray);
	setIndex(0, initialArray.length);
}
```

## 内容总结
- ByteBuf提供了两个指针；读、写，分别用来标记“可读”、“可写”、“可丢弃”的字节
- 通过调用write*方法写入数据后，写指针将会向后移动
- 通过调用read*方法读取数据后，读指针将会向后移动
- 写入数据或读取数据时会检查是否有足够多的空间可以写入和是否有数据可以读取
- 写入数据之前会进行容量检查，当剩余可写的容量小于需要写入的容量时，需要执行扩容操作
- clear等修改读写指针的方法，只会更改读写指针的值，并不会影响ByteBuf中已有的内容

------------

上一篇：[netty案例，netty4.1源码分析篇三《Netty服务端初始化过程以及反射工厂的作用》](/itstack-demo-netty-4/2019/09/12/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E4%B8%89-Netty%E6%9C%8D%E5%8A%A1%E7%AB%AF%E5%88%9D%E5%A7%8B%E5%8C%96%E8%BF%87%E7%A8%8B%E4%BB%A5%E5%8F%8A%E5%8F%8D%E5%B0%84%E5%B7%A5%E5%8E%82%E7%9A%84%E4%BD%9C%E7%94%A8.html)

下一篇：[netty案例，netty4.1源码分析篇五《一行简单的writeAndFlush都做了哪些事》](/itstack-demo-netty-4/2019/09/14/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E4%BA%94-%E4%B8%80%E8%A1%8C%E7%AE%80%E5%8D%95%E7%9A%84writeAndFlush%E9%83%BD%E5%81%9A%E4%BA%86%E5%93%AA%E4%BA%9B%E4%BA%8B.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**rpc案例源码**」获取本文源码&更多原创专题案例！

