---
layout: post
category: itstack-demo-netty-4
title: netty案例，netty4.1源码分析篇二《ServerBootstrap配置与绑定启动》
tagline: by 付政委
tag: [netty,itstack-demo-netty-4] 
lock: need
---

结合上一章节介绍NioEventLoopGroup，本章节继续介绍ServerBootstrap相关代码。

>启动NettyServer的模版代码

```java
private void bing(int port) {
    EventLoopGroup parentGroup = new NioEventLoopGroup(); 
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

## ServerBootstrap与Bootstrap
- 它们都是继承于AbstractBootstrap，分别负责服务端与客户端；
- ServerBootstrap，服务端用于接收客户端的连接并为接收连接的用户创建Channel通道
- BootStrap，客户端不接收连接，并且是在父通道完成系列操作。

** 类继承结构图：**

![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-4-2-2.png)

## ServerBootstrap启动流程源码分析

1、 处理说明
- 新建NioEventLoopGroup类型的bossGroup和group。bossGroup主要处理服务端接收客户端连接处理，group主要处理读写等I/O事件及任务等；
- 创建ServerBootstrap，其主要对一些处理进行代理，如bind()等操作，其是其他类的一个简单门面；
- channel()方法设置服务端的ServerSocketChannel实现类，本处实现类为NioServerSocketChannel。
- option()方法设置Channel的相关选项，具体查看ChannelOption中的定义；
- localAddress()设置服务端绑定的本地地址及端口；
- handler()设置服务端的对应Channel的Handler;
- childHandler()设置子连接的Channel的Handler；
- bind()及sync()绑定本地地址并同步返回绑定结果；

2、 bing()调用流程
![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-4-2-1.png)

- 调用ServerBootstrap.bind()：应用调用ServerBootstrap的bind()操作；
- 调用AbstractBootstrap.bind()：调用doBind()对进行bind操作；
- 调用AbstractBootstrap.initAndRegister()：利用ChannelFactory.newChannel()实例化NioServerSocketChannel；
- 调用ServerBootstrap.init()：对NioServerSocketChannel进行初始化，主要操作如设置Channel相关的选项及属性、设置ChannelHandler为ServerBootstrapAcceptor等，ServerBootstrapAcceptor为inbound类型的ChannelHandler，其为ServerBootstrap的内部类，其主要实现ChannelRead()操作，将客户端的连接注册到EventLoopGroup的EventLoop中。
- 调用NioEventLoop.register()：将NioServerSocketChannel注册到bossGroup中。
- 调用AbstractBootstrap.doBind0：将实际的bind操作以任务的形式添加到bossGroup的EventLoop中。
- 调用NioServerSocketChannel.bind()：在EventLoop中以任务的形式调用此方法进行实际的bind()操作。

## 源码方法分析

### 1、doBind()源码分析

>AbstractBootstrap.java | AbstractBootstrap.doBind() 

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

** 主要流程处理 **

- 调用initAndRegister()初始化Channel并将其注册到bossGroup中的NioEventLoop中；
- 若注册成功，则调用doBind0()进行实际的bind操作；
- 若还未注册，则创建注册结果的监听器及doBind0()的异步结果，若Channel注册成功，则在结果监听器中进行doBind0()操作，并将bind()异步结果这种为成功；否则将在监听器中设置异步结果为失败；

### 2、 initAndRegister()源码分析

>AbstractBootstrap.java | AbstractBootstrap.initAndRegister() 

```java
final ChannelFuture initAndRegister() {
	Channel channel = null;
	try {
		channel = channelFactory.newChannel();
		init(channel);
	} catch (Throwable t) {
		if (channel != null) {
			// channel can be null if newChannel crashed (eg SocketException("too many open files"))
			channel.unsafe().closeForcibly();
			// as the Channel is not registered yet we need to force the usage of the GlobalEventExecutor
			return new DefaultChannelPromise(channel, GlobalEventExecutor.INSTANCE).setFailure(t);
		}
		// as the Channel is not registered yet we need to force the usage of the GlobalEventExecutor
		return new DefaultChannelPromise(new FailedChannel(), GlobalEventExecutor.INSTANCE).setFailure(t);
	}

	ChannelFuture regFuture = config().group().register(channel);
	if (regFuture.cause() != null) {
		if (channel.isRegistered()) {
			channel.close();
		} else {
			channel.unsafe().closeForcibly();
		}
	}

	// If we are here and the promise is not failed, it's one of the following cases:
	// 1) If we attempted registration from the event loop, the registration has been completed at this point.
	//    i.e. It's safe to attempt bind() or connect() now because the channel has been registered.
	// 2) If we attempted registration from the other thread, the registration request has been successfully
	//    added to the event loop's task queue for later execution.
	//    i.e. It's safe to attempt bind() or connect() now:
	//         because bind() or connect() will be executed *after* the scheduled registration task is executed
	//         because register(), bind(), and connect() are all bound to the same thread.

	return regFuture;
}
```

** 主要处理流程 **

- 通过ChannelFactory新创建一个Channel；
- 调用ServerBootstrap的init()方法对Channel进行初始化；

### 3、init()源码分析

>AbstractBootstrap.java | AbstractBootstrap.init()

```java
@Override
void init(Channel channel) throws Exception {
	final Map<ChannelOption<?>, Object> options = options0();
	synchronized (options) {
		setChannelOptions(channel, options, logger);
	}

	final Map<AttributeKey<?>, Object> attrs = attrs0();
	synchronized (attrs) {
		for (Entry<AttributeKey<?>, Object> e: attrs.entrySet()) {
			@SuppressWarnings("unchecked")
			AttributeKey<Object> key = (AttributeKey<Object>) e.getKey();
			channel.attr(key).set(e.getValue());
		}
	}

	ChannelPipeline p = channel.pipeline();

	final EventLoopGroup currentChildGroup = childGroup;
	final ChannelHandler currentChildHandler = childHandler;
	final Entry<ChannelOption<?>, Object>[] currentChildOptions;
	final Entry<AttributeKey<?>, Object>[] currentChildAttrs;
	synchronized (childOptions) {
		currentChildOptions = childOptions.entrySet().toArray(newOptionArray(0));
	}
	synchronized (childAttrs) {
		currentChildAttrs = childAttrs.entrySet().toArray(newAttrArray(0));
	}

	p.addLast(new ChannelInitializer<Channel>() {
		@Override
		public void initChannel(final Channel ch) throws Exception {
			final ChannelPipeline pipeline = ch.pipeline();
			ChannelHandler handler = config.handler();
			if (handler != null) {
				pipeline.addLast(handler);
			}

			ch.eventLoop().execute(new Runnable() {
				@Override
				public void run() {
					pipeline.addLast(new ServerBootstrapAcceptor(
							ch, currentChildGroup, currentChildHandler, currentChildOptions, currentChildAttrs));
				}
			});
		}
	});
}
```

** 主要处理流程 **

- 如果设置了Channel选项，则调用setChannelOptions()对Channel进行选项设置；
- 如果设置了属性，则将对应属性设置为Channel的属性；
- 设置子Channel的选项及属性；
- 初始化NioServerSocketChannel的ChannelHandler为ServerBootstrapAcceptor，ServerBootstrapAcceptor为inbound类型的ChannelHandler，其主要功能是将已经接受连接的子Channel注册到workerGroup的NioEventLoop中；

### 4、 doBind0()源码分析

>AbstractBootstrap.java | AbstractBootstrap.doBind0()

```java
private static void doBind0(
            final ChannelFuture regFuture, final Channel channel,
            final SocketAddress localAddress, final ChannelPromise promise) {

        // This method is invoked before channelRegistered() is triggered.  Give user handlers a chance to set up
        // the pipeline in its channelRegistered() implementation.
        channel.eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                if (regFuture.isSuccess()) {
                    channel.bind(localAddress, promise).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                } else {
                    promise.setFailure(regFuture.cause());
                }
            }
        });
    }
```

** 主要处理流程 **

- 将NioServerSocketChannel.bind()操作封装为任务，并将任务提交给其对应的EventLoop进行处理；

### 5、 ServerBootstrapAcceptor源码分析
ServerBootstrapAcceptor为NioServerSocketChannel的 - ChannelHandler，其类型为Inbound类型；

>ServerBootstrapAcceptor.java

```java
private static class ServerBootstrapAcceptor extends ChannelInboundHandlerAdapter {

	private final EventLoopGroup childGroup;
	private final ChannelHandler childHandler;
	private final Entry<ChannelOption<?>, Object>[] childOptions;
	private final Entry<AttributeKey<?>, Object>[] childAttrs;
	private final Runnable enableAutoReadTask;

	ServerBootstrapAcceptor(
			final Channel channel, EventLoopGroup childGroup, ChannelHandler childHandler,
			Entry<ChannelOption<?>, Object>[] childOptions, Entry<AttributeKey<?>, Object>[] childAttrs) {
		this.childGroup = childGroup;
		this.childHandler = childHandler;
		this.childOptions = childOptions;
		this.childAttrs = childAttrs;

		// Task which is scheduled to re-enable auto-read.
		// It's important to create this Runnable before we try to submit it as otherwise the URLClassLoader may
		// not be able to load the class because of the file limit it already reached.
		//
		// See https://github.com/netty/netty/issues/1328
		enableAutoReadTask = new Runnable() {
			@Override
			public void run() {
				channel.config().setAutoRead(true);
			}
		};
	}

	@Override
	@SuppressWarnings("unchecked")
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		final Channel child = (Channel) msg;

		child.pipeline().addLast(childHandler);

		setChannelOptions(child, childOptions, logger);

		for (Entry<AttributeKey<?>, Object> e: childAttrs) {
			child.attr((AttributeKey<Object>) e.getKey()).set(e.getValue());
		}

		try {
			childGroup.register(child).addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (!future.isSuccess()) {
						forceClose(child, future.cause());
					}
				}
			});
		} catch (Throwable t) {
			forceClose(child, t);
		}
	}

	private static void forceClose(Channel child, Throwable t) {
		child.unsafe().closeForcibly();
		logger.warn("Failed to register an accepted channel: {}", child, t);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		final ChannelConfig config = ctx.channel().config();
		if (config.isAutoRead()) {
			// stop accept new connections for 1 second to allow the channel to recover
			// See https://github.com/netty/netty/issues/1328
			config.setAutoRead(false);
			ctx.channel().eventLoop().schedule(enableAutoReadTask, 1, TimeUnit.SECONDS);
		}
		// still let the exceptionCaught event flow through the pipeline to give the user
		// a chance to do something with it
		ctx.fireExceptionCaught(cause);
	}
}
```

** ServerBootstrapAcceptor主要实现了以下方法： **
- channelRead()：设置子连接的ChannelHandler、设置子连接的Channel选项，设置子连接的Channel属性，将子连接注册的child对应的EventLoop中（即workerGroup的EventLoop中）；
- exceptionCaught()：若ServerSocketChannel在accept子连接时抛出异常，若ServerSocketChannel的autoRead为true，则设置其为false，即不允许自动接收客户端连接，并延迟1s后再设置其为true，使其允许自动接收客户端连接；

------------

上一篇：[netty案例，netty4.1源码分析篇一《NioEventLoopGroup源码分析》](/itstack-demo-netty-4/2019/09/10/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E4%B8%80-NioEventLoopGroup%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.html)

下一篇：[netty案例，netty4.1源码分析篇三《Netty服务端初始化过程以及反射工厂的作用》](/itstack-demo-netty-4/2019/09/12/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E4%B8%89-Netty%E6%9C%8D%E5%8A%A1%E7%AB%AF%E5%88%9D%E5%A7%8B%E5%8C%96%E8%BF%87%E7%A8%8B%E4%BB%A5%E5%8F%8A%E5%8F%8D%E5%B0%84%E5%B7%A5%E5%8E%82%E7%9A%84%E4%BD%9C%E7%94%A8.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**rpc案例源码**」获取本文源码&更多原创专题案例！
