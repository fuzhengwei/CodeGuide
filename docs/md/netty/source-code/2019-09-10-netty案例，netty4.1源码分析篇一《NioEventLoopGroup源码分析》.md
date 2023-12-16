---
layout: post
category: itstack-demo-netty-4
title: netty案例，netty4.1源码分析篇一《NioEventLoopGroup源码分析》
tagline: by 付政委
tag: [netty,itstack-demo-netty-4]
lock: need
---

# netty案例，netty4.1源码分析篇一《NioEventLoopGroup源码分析》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

本章节我们从一个基础构建的基础NettyServer来分析NioEventLoopGroup源码，其中包括了；EventLoopGroup事件循环组、NioEventLoopGroup异步事件循环组、MultithreadEventLoopGroup多线程事件循环组等。

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

## 类结构树

NioEventLoopGroup 通过实现Java的并发编程包的方法，来实现自己的相关功能。

## EventExecutorGroup

EventExecutorGroup 使用next()方法负责提供EventExecutor。除此之外，它还负责处理生命周期，并且可以以一种全局的方式进行关闭。

>EventExecutorGroup.java

```java
/**
 * The {@link EventExecutorGroup} is responsible for providing the {@link EventExecutor}'s to use
 * via its {@link #next()} method. Besides this, it is also responsible for handling their
 * life-cycle and allows shutting them down in a global fashion.
 */
public interface EventExecutorGroup extends ScheduledExecutorService, Iterable<EventExecutor> {

	...

    /**
     * Returns one of the {@link EventExecutor}s managed by this {@link EventExecutorGroup}.
     */
    EventExecutor next();

	...
}
```

- 方法介绍
	- EventExecutorGroup.next() 返回一个由EventExecutorGroup管理的事件执行器。组里包含了若干个EventExecutor。


## EventLoopGroup
EventLoopGroup继承EventExecutorGroup的接口

EventLoopGroup 本身是特殊的EventExecutorGroup，它的作用是会在事件循环（处理链接、输入输出消息等）的过程当中，进行selection操作当中允许注册一个一个的channel链接。

>EventLoopGroup.java

```java
/**
 * Special {@link EventExecutorGroup} which allows registering {@link Channel}s that get
 * processed for later selection during the event loop.
 *
 */
public interface EventLoopGroup extends EventExecutorGroup {
    /**
     * Return the next {@link EventLoop} to use
     */
    @Override
    EventLoop next();

    /**
     * Register a {@link Channel} with this {@link EventLoop}. The returned {@link ChannelFuture}
     * will get notified once the registration was complete.
     */
    ChannelFuture register(Channel channel);

    /**
     * Register a {@link Channel} with this {@link EventLoop} using a {@link ChannelFuture}. The passed
     * {@link ChannelFuture} will get notified once the registration was complete and also will get returned.
     */
    ChannelFuture register(ChannelPromise promise);

    /**
     * Register a {@link Channel} with this {@link EventLoop}. The passed {@link ChannelFuture}
     * will get notified once the registration was complete and also will get returned.
     *
     * @deprecated Use {@link #register(ChannelPromise)} instead.
     */
    @Deprecated
    ChannelFuture register(Channel channel, ChannelPromise promise);
}
```

- 方法介绍

	- EventLoopGroup.next() 返回下一个事件循环

	- EventLoopGroup.register(Channel channel) 将一个通道注册到事件循环当中，所返回的ChannelFuture在注册完成之后就会收到一个通知。（ChannelFuture是一个异步方法，ChannelFuture是继承自jdk1.5里面的Future方法。

	- EventLoopGroup.register(ChannelPromise promise) 与上面的方法构成一个重载，ChannelPromise里面继承了ChannelFuture，里面包含了channel。在注册完成之后ChannelFuture会收到一个通知并且也会返回。

	- EventLoopGroup.register(Channel channel, ChannelPromise promise) 因为ChannelPromise已经包含了Channel，方法重复了所以被注释掉了。


## NioEventLoopGroup

>NioEventLoopGroup.java

MultithreadEventLoopGroup是NioEventLoopGroup的一个父类，NioEventLoopGroup基于NIO选择器的Selector的一个实现。并提供多种不同入参的构造方法，在不同的构造方法内提供一些默认的初始化方法，以便于创建Netty服务配置信息。

```java
/**
 * {@link MultithreadEventLoopGroup} implementations which is used for NIO {@link Selector} based {@link Channel}s.
 */
public class NioEventLoopGroup extends MultithreadEventLoopGroup {

    /**
     * Create a new instance using the default number of threads, the default {@link ThreadFactory} and
     * the {@link SelectorProvider} which is returned by {@link SelectorProvider#provider()}.
     */
    public NioEventLoopGroup() {
        this(0);
    }

    /**
     * Create a new instance using the specified number of threads, {@link ThreadFactory} and the
     * {@link SelectorProvider} which is returned by {@link SelectorProvider#provider()}.
     */
    public NioEventLoopGroup(int nThreads) {
        this(nThreads, (Executor) null);
    }

    /**
     * Create a new instance using the specified number of threads, the given {@link ThreadFactory} and the
     * {@link SelectorProvider} which is returned by {@link SelectorProvider#provider()}.
     */
    public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
        this(nThreads, threadFactory, SelectorProvider.provider());
    }

    public NioEventLoopGroup(int nThreads, Executor executor) {
        this(nThreads, executor, SelectorProvider.provider());
    }

    /**
     * Create a new instance using the specified number of threads, the given {@link ThreadFactory} and the given
     * {@link SelectorProvider}.
     */
    public NioEventLoopGroup(
            int nThreads, ThreadFactory threadFactory, final SelectorProvider selectorProvider) {
        this(nThreads, threadFactory, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
    }

    public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory,
        final SelectorProvider selectorProvider, final SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, threadFactory, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public NioEventLoopGroup(
            int nThreads, Executor executor, final SelectorProvider selectorProvider) {
        this(nThreads, executor, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
    }

    public NioEventLoopGroup(int nThreads, Executor executor, final SelectorProvider selectorProvider,
                             final SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, executor, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public NioEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory,
                             final SelectorProvider selectorProvider,
                             final SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, executor, chooserFactory, selectorProvider, selectStrategyFactory,
                RejectedExecutionHandlers.reject());
    }

    public NioEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory,
                             final SelectorProvider selectorProvider,
                             final SelectStrategyFactory selectStrategyFactory,
                             final RejectedExecutionHandler rejectedExecutionHandler) {
        super(nThreads, executor, chooserFactory, selectorProvider, selectStrategyFactory, rejectedExecutionHandler);
    }
	
	...
}
```
- 方法介绍
	- NioEventLoopGroup()

		- 在创建Netty服务端的时候，代码中实例化了两个EventLoopGroup分别是parentGroup、childGroup，parentGroup 主要用于接收请求链接，链接成功后交给childGroup处理收发数据等事件。

		- NioEventLoopGroup可以在构造方法中传入需要启动的线程数，默认的情况下他会在采用计算机核心数*2的方式去启动线程数量。另外目前很多计算机采用了超线程技术，那么4核心的机器，超线程后就是8核心，Netty在启动的时候随时会启动8*2=16个线程。

		>超线程（HT, Hyper-Threading）是英特尔研发的一种技术，于2002年发布。超线程技术原先只应用于Xeon 处理器中，当时称为“Super-Threading”。之后陆续应用在Pentium 4 HT中。早期代号为Jackson。 [1] 
通过此技术，英特尔实现在一个实体CPU中，提供两个逻辑线程。之后的Pentium D纵使不支持超线程技术，但就集成了两个实体核心，所以仍会见到两个线程。超线程的未来发展，是提升处理器的逻辑线程。英特尔于2016年发布的Core i7-6950X便是将10核心的处理器，加上超线程技术，使之成为20个逻辑线程的产品。

		- new NioEventLoopGroup()，空构造函数情况下会使用一个系统默认的线程数，这个默认线程数是Netty通过使用计算机核心数*2计算的，代码如下；

		>MultithreadEventLoopGroup.java | 源码中NettyRuntime.availableProcessors() * 2

		```java
		public abstract class MultithreadEventLoopGroup extends MultithreadEventExecutorGroup implements EventLoopGroup {
	
		private static final InternalLogger logger = InternalLoggerFactory.getInstance(MultithreadEventLoopGroup.class);
	
		private static final int DEFAULT_EVENT_LOOP_THREADS;
	
		static {
			DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt(
					"io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
	
			if (logger.isDebugEnabled()) {
				logger.debug("-Dio.netty.eventLoopThreads: {}", DEFAULT_EVENT_LOOP_THREADS);
			}
		}
	
		...
		}
		```

		>可以按照实际需要调整线程数；

		```java
		EventLoopGroup parentGroup = new NioEventLoopGroup(1);  //单线程

		EventLoopGroup parentGroup = new NioEventLoopGroup(4);  //多线程
		```

	- NioEventLoopGroup(int nThreads); 	
		
		在此构造函数Executor的参数为NULL，最终在MultithreadEventExecutorGroup.MultithreadEventExecutorGroup中会进行创建线程任务执行器
		```java
		if (executor == null) {
			executor = new ThreadPerTaskExecutor(newDefaultThreadFactory());
		}
		```
		
	- NioEventLoopGroup(int nThreads, ThreadFactory threadFactory)
	
		在此构造函数中提供了SelectorProvider.provider()用于通过静态方法来获取NIO实例

		```java
		/**
		* Create a new instance using the specified number of threads, the given {@link ThreadFactory} and the
		* {@link SelectorProvider} which is returned by {@link SelectorProvider#provider()}.
		*/
		public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
			this(nThreads, threadFactory, SelectorProvider.provider());
		}
		```
	
		```java
		public static SelectorProvider provider() {
			synchronized (lock) {
				if (provider != null)
					return provider;
				return AccessController.doPrivileged(
					new PrivilegedAction<SelectorProvider>() {
						public SelectorProvider run() {
								if (loadProviderFromProperty())
									return provider;
								if (loadProviderAsService())
									return provider;
								provider = sun.nio.ch.DefaultSelectorProvider.create();
								return provider;
							}
						});
			}
		}
		```

	- NioEventLoopGroup(int nThreads, ThreadFactory threadFactory, final SelectorProvider selectorProvider)
		在此构造函数中提供了DefaultSelectStrategyFactory.INSTANCE来创建默认选择策略工厂。
		```java
		final class DefaultSelectStrategy implements SelectStrategy {
			static final SelectStrategy INSTANCE = new DefaultSelectStrategy();
		
			private DefaultSelectStrategy() { }
		
			@Override
			public int calculateStrategy(IntSupplier selectSupplier, boolean hasTasks) throws Exception {
				return hasTasks ? selectSupplier.get() : SelectStrategy.SELECT;
			}
		}
		```

	- NioEventLoopGroup(int nThreads, Executor executor, final SelectorProvider selectorProvider,final SelectStrategyFactory selectStrategyFactory)
		公开辅助方法，用于创建不同的拒绝执行处理器。
		
		>RejectedExecutionHandlers.java 
		
		``` java
		private static final RejectedExecutionHandler REJECT = new RejectedExecutionHandler() {
			@Override
			public void rejected(Runnable task, SingleThreadEventExecutor executor) {
				throw new RejectedExecutionException();
			}
		};
		```


## MultithreadEventExecutorGroup

使用多个线程同时处理其任务的实现的抽象基类，其中的MultithreadEventExecutorGroup方法最终创建执行线程

>MultithreadEventExecutorGroup.java

```java
/**
 * Abstract base class for {@link EventExecutorGroup} implementations that handles their tasks with multiple threads at
 * the same time.
 */
public abstract class MultithreadEventExecutorGroup extends AbstractEventExecutorGroup {

    ...

    /**
     * Create a new instance.
     *
     * @param nThreads          the number of threads that will be used by this instance.
     * @param executor          the Executor to use, or {@code null} if the default should be used.
     * @param chooserFactory    the {@link EventExecutorChooserFactory} to use.
     * @param args              arguments which will passed to each {@link #newChild(Executor, Object...)} call
     */
    protected MultithreadEventExecutorGroup(int nThreads, Executor executor,
                                            EventExecutorChooserFactory chooserFactory, Object... args) {
        if (nThreads <= 0) {
            throw new IllegalArgumentException(String.format("nThreads: %d (expected: > 0)", nThreads));
        }

        if (executor == null) {
            executor = new ThreadPerTaskExecutor(newDefaultThreadFactory());
        }

        children = new EventExecutor[nThreads];

        for (int i = 0; i < nThreads; i ++) {
            boolean success = false;
            try {
                children[i] = newChild(executor, args);
                success = true;
            } catch (Exception e) {
                // TODO: Think about if this is a good exception type
                throw new IllegalStateException("failed to create a child event loop", e);
            } finally {
                if (!success) {
                    for (int j = 0; j < i; j ++) {
                        children[j].shutdownGracefully();
                    }

                    for (int j = 0; j < i; j ++) {
                        EventExecutor e = children[j];
                        try {
                            while (!e.isTerminated()) {
                                e.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
                            }
                        } catch (InterruptedException interrupted) {
                            // Let the caller handle the interruption.
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                }
            }
        }

        chooser = chooserFactory.newChooser(children);

        final FutureListener<Object> terminationListener = new FutureListener<Object>() {
            @Override
            public void operationComplete(Future<Object> future) throws Exception {
                if (terminatedChildren.incrementAndGet() == children.length) {
                    terminationFuture.setSuccess(null);
                }
            }
        };

        for (EventExecutor e: children) {
            e.terminationFuture().addListener(terminationListener);
        }

        Set<EventExecutor> childrenSet = new LinkedHashSet<EventExecutor>(children.length);
        Collections.addAll(childrenSet, children);
        readonlyChildren = Collections.unmodifiableSet(childrenSet);
    }

    ...
}
```

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！

