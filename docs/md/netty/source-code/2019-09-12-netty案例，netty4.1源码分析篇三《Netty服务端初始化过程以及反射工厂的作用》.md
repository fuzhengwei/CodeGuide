---
layout: post
category: itstack-demo-netty-4
title: netty案例，netty4.1源码分析篇三《Netty服务端初始化过程以及反射工厂的作用》
tagline: by 付政委
tag: [netty,itstack-demo-netty-4]
lock: need
---

# netty案例，netty4.1源码分析篇三《Netty服务端初始化过程以及反射工厂的作用》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

本章节主要分析Netty在启动过程中的配置内容以及最终调用bind方法是如何启动Netty服务端的。

>Netty服务启动模板代码

```java
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
```

** ServerBootstrap **

- 设定相关属性的服务类
- 实现了AbstractBootstrap方法，里面的泛型ServerChannel是一个标记接口，不做实际方法
- ServerSocketChannel提供了三个方法；配置、本地地址、远程地址，用于接收处理TCP/IP连接

```java
public interface ServerSocketChannel extends ServerChannel {
    @Override
    ServerSocketChannelConfig config();
    @Override
    InetSocketAddress localAddress();
    @Override
    InetSocketAddress remoteAddress();
}
```

在Netty启动过程中，我们分别通过调用.group、.channel、.option、.childHandler来配置服务端信息，最后调用.bind()来启动服务。

>.group | 用于处理事件循环组的方法

```java
public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup) {
	super.group(parentGroup);
	if (childGroup == null) {
		throw new NullPointerException("childGroup");
	}
	if (this.childGroup != null) {
		throw new IllegalStateException("childGroup set already");
	}
	this.childGroup = childGroup;
	return this;
}
```

group方法通过为parentGroup、childGroup设置事件循环组（EventLoopGroup），用于处理事件内容与IO请求。也就是我们用于等待接收客户端连接与信息内容交互。

>.channel | 通过反射方式创建通信通道的方法

```java
public B channel(Class<? extends C> channelClass) {
    if (channelClass == null) {
        throw new NullPointerException("channelClass");
    }
    return channelFactory(new ReflectiveChannelFactory<C>(channelClass));
}
```

在这个方法中ReflectiveChannelFactory反射工厂类通过构造函数，传递channelClass这个参数，来实例化反射工厂。这个channelClass类，就是我们在配置中传递的异步NIO流的服务端Socket管道，NioServerSocketChannel。最后将工厂信息传递到channel中，用于后续实例化无参的构造函数，并在后续提供调用NioServerSocketChannel方法的能力。

ReflectiveChannelFactory类中仅是提供了一个非常简单的方法，用于获取实例化；
```java
public T newChannel() {
    try {
        return constructor.newInstance();
    } catch (Throwable t) {
        throw new ChannelException("Unable to create Channel from class " + constructor.getDeclaringClass(), t);
    }
}
```

>.option | 是一个选项配置类，可以增加一些配置参数

```java
public <T> B option(ChannelOption<T> option, T value) {
    if (option == null) {
        throw new NullPointerException("option");
    }
    if (value == null) {
        synchronized (options) {
            options.remove(option);
        }
    } else {
        synchronized (options) {
            options.put(option, value);
        }
    }
    return self();
}
```

option是Netty为我们提供的配置选项，它包含但不限于；ChannelOption.SO_BACKLOG、ChannelOption.SO_TIMEOUT、ChannelOption.TCP_NODELAY等，option并不是非的配置，如果不配置也是可以正常启动的。

------------


1、ChannelOption.SO_BACKLOG
ChannelOption.SO_BACKLOG对应的是tcp/ip协议listen函数中的backlog参数，函数listen(int socketfd,int backlog)用来初始化服务端可连接队列，服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小

2、ChannelOption.SO_REUSEADDR
ChanneOption.SO_REUSEADDR对应于套接字选项中的SO_REUSEADDR，这个参数表示允许重复使用本地地址和端口，
比如，某个服务器进程占用了TCP的80端口进行监听，此时再次监听该端口就会返回错误，使用该参数就可以解决问题，该参数允许共用该端口，这个在服务器程序中比较常使用，
比如某个进程非正常退出，该程序占用的端口可能要被占用一段时间才能允许其他进程使用，而且程序死掉以后，内核一需要一定的时间才能够释放此端口，不设置SO_REUSEADDR就无法正常使用该端口。

3、ChannelOption.SO_KEEPALIVE
Channeloption.SO_KEEPALIVE参数对应于套接字选项中的SO_KEEPALIVE，该参数用于设置TCP连接，当设置该选项以后，连接会测试链接的状态，这个选项用于可能长时间没有数据交流的连接。当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文。

4、ChannelOption.SO_SNDBUF和ChannelOption.SO_RCVBUF
ChannelOption.SO_SNDBUF参数对应于套接字选项中的SO_SNDBUF，ChannelOption.SO_RCVBUF参数对应于套接字选项中的SO_RCVBUF这两个参数用于操作接收缓冲区和发送缓冲区的大小，接收缓冲区用于保存网络协议站内收到的数据，直到应用程序读取成功，发送缓冲区用于保存发送数据，直到发送成功。

5、ChannelOption.SO_LINGER
ChannelOption.SO_LINGER参数对应于套接字选项中的SO_LINGER,Linux内核默认的处理方式是当用户调用close（）方法的时候，函数返回，在可能的情况下，尽量发送数据，不一定保证会发生剩余的数据，造成了数据的不确定性，使用SO_LINGER可以阻塞close()的调用时间，直到数据完全发送

6、ChannelOption.TCP_NODELAY
ChannelOption.TCP_NODELAY参数对应于套接字选项中的TCP_NODELAY,该参数的使用与Nagle算法有关,Nagle算法是将小的数据包组装为更大的帧然后进行发送，而不是输入一次发送一次,因此在数据包不足的时候会等待其他数据的到了，组装成大的数据包进行发送，虽然该方式有效提高网络的有效负载，但是却造成了延时，而该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输，于TCP_NODELAY相对应的是TCP_CORK，该选项是需要等到发送的数据量最大的时候，一次性发送数据，适用于文件传输。

7、IP_TOS
IP参数，设置IP头部的Type-of-Service字段，用于描述IP包的优先级和QoS选项。

8、ALLOW_HALF_CLOSURE
Netty参数，一个连接的远端关闭时本地端是否关闭，默认值为False。值为False时，连接自动关闭；为True时，触发ChannelInboundHandler的userEventTriggered()方法，事件为ChannelInputShutdownEvent。

------------


>.childHandler | 设置自己的管道服务，接收信息处理 （另外还有一个handler方法是被parentGroup所使用）

```java
public ServerBootstrap childHandler(ChannelHandler childHandler) {
    if (childHandler == null) {
        throw new NullPointerException("childHandler");
    }
    this.childHandler = childHandler;
    return this;
}
```

** 以上信息配置完成后，我们的服务端通过调用bind()方法来启动服务端 **

```java
b.bind(port).sync();
```

```java
private ChannelFuture doBind(final SocketAddress localAddress) {
	final ChannelFuture regFuture = initAndRegister();
	final Channel channel = regFuture.channel();
	if (regFuture.cause() != null) {
		return regFuture;
	}

	if (regFuture.isDone()) {
		// At this point we know that the registration was complete and successful.
		ChannelPromise promise = channel.newPromise();
		doBind0(regFuture, channel, localAddress, promise);
		return promise;
	} else {
		// Registration future is almost always fulfilled already, but just in case it's not.
		final PendingRegistrationPromise promise = new PendingRegistrationPromise(channel);
		regFuture.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				Throwable cause = future.cause();
				if (cause != null) {
					// Registration on the EventLoop failed so fail the ChannelPromise directly to not cause an
					// IllegalStateException once we try to access the EventLoop of the Channel.
					promise.setFailure(cause);
				} else {
					// Registration was successful, so set the correct executor to use.
					// See https://github.com/netty/netty/issues/2586
					promise.registered();

					doBind0(regFuture, channel, localAddress, promise);
				}
			}
		});
		return promise;
	}
}
```

这里面的第一行代码initAndRegister，里面通过反射工厂使用了我们的配置的NioServerSocketChannel.class，来实例化NioServerSocketChannel。实例化后NioServerSocketChannel会随之启动Netty服务；

```java
private static ServerSocketChannel newSocket(SelectorProvider provider) {
	try {
		/**
		 *  Use the {@link SelectorProvider} to open {@link SocketChannel} and so remove condition in
		 *  {@link SelectorProvider#provider()} which is called by each ServerSocketChannel.open() otherwise.
		 *
		 *  See <a href="https://github.com/netty/netty/issues/2308">#2308</a>.
		 */
		return provider.openServerSocketChannel();
	} catch (IOException e) {
		throw new ChannelException(
				"Failed to open a server socket.", e);
	}
}
```

```java
ServerSocketChannelImpl(SelectorProvider sp) throws IOException {
	super(sp);
	this.fd =  Net.serverSocket(true);
	this.fdVal = IOUtil.fdVal(fd);
	this.state = ST_INUSE;
}
```

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**rpc案例源码**」获取本文源码&更多原创专题案例！

