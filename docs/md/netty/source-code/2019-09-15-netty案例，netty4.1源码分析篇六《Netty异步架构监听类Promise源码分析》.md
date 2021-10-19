---
layout: post
category: itstack-demo-netty-4
title: netty案例，netty4.1源码分析篇六《Netty异步架构监听类Promise源码分析》
tagline: by 付政委
tag: [netty,itstack-demo-netty-4]
lock: need
---

## 前言介绍
分析Promise之前我们先来看两个单词；Promise、Future
>Promise v. 许诺;承诺;答应;保证;使很可能;预示
Future n. 将来;未来;未来的事;将来发生的事;前景;前途;前程

他们的含义都是对未来即将要发生的事情做相应的处理，这也是在异步编程中非常常见的类名。

Netty是一个异步网络处理框架，在实现中大量使用了Future机制，并在Java自带Future的基础上，增加了Promise机制。这两个实现类的目的都是为了使异步编程更加方便使用。

## 源码分析

### 1、了解Java并发包中的Future
java的并发包中提供java.util.concurrent.Future类，用于处理异步操作。在Java中Future是一个未来完成的异步操作，可以获得未来返回的值。如下案例，调用一个获取用户信息的方法，该方法会立刻返回Future对象，调用Future.get()可以同步等待耗时方法的返回，也可以通过调用future的cancel()取消Future任务。

```java
class TestFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TestFuture testFuture = new TestFuture();
        Future<String> future = testFuture.queryUserInfo("10001"); //返回future
        String userInfo = future.get();
        System.out.println("查询用户信息：" + userInfo);
    }

    private Future<String> queryUserInfo(String userId) {
        FutureTask<String> future = new FutureTask<>(() -> {
            try {
                Thread.sleep(1000);
                return "微信公众号：bugstack虫洞栈 | 用户ID：" + userId;
            } catch (InterruptedException ignored) {}
            return "error";
        });
        new Thread(future).start();
        return future;
    }

}
```

### 2、Netty实现了自己的Future
Netty通过继承java并发包的Future来定义自己的Future接口，为Future加入的功能主要有添加、删除监听事件接口，最后由Promise实现。

>io.netty.util.concurrent.Future.java中定义了一些列的异步编程方法 | 经常会使用的>b.bind(port).sync();

```java
// 只有IO操作完成时才返回true
boolean isSuccess();
// 只有当cancel(boolean)成功取消时才返回true
boolean isCancellable();
// IO操作发生异常时，返回导致IO操作以此的原因，如果没有异常，返回null
Throwable cause();
// 向Future添加事件，future完成时，会执行这些事件，如果add时future已经完成，会立即执行监听事件
Future<V> addListener(GenericFutureListener<? extends Future<? super V>> listener);
Future<V> addListeners(GenericFutureListener<? extends Future<? super V>>... listeners);
// 移除监听事件，future完成时，不会触发
Future<V> removeListener(GenericFutureListener<? extends Future<? super V>> listener);
Future<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... listeners);
// 等待future done
Future<V> sync() throws InterruptedException;
// 等待future done，不可打断
Future<V> syncUninterruptibly();
// 等待future完成
Future<V> await() throws InterruptedException;
// 等待future 完成，不可打断
Future<V> awaitUninterruptibly();
boolean await(long timeout, TimeUnit unit) throws InterruptedException;
boolean await(long timeoutMillis) throws InterruptedException;
boolean awaitUninterruptibly(long timeout, TimeUnit unit);
boolean awaitUninterruptibly(long timeoutMillis);
// 立刻获得结果，如果没有完成，返回null
V getNow();
// 如果成功取消，future会失败，导致CancellationException
@Override
boolean cancel(boolean mayInterruptIfRunning);
```

### 3、Promise机制
Netty的Future与Java的Future虽然类名相同，但功能上略有不同，Netty中引入了Promise机制。在Java的Future中，业务逻辑为一个Callable或Runnable实现类，该类的call()或run()执行完毕意味着业务逻辑的完结；而在Promise机制中，可以在业务逻辑中人工设置业务逻辑的成功与失败，这样更加方便的监控自己的业务逻辑。

>io.netty.util.concurrent.Promise.java | 

```java
public interface Promise<V> extends Future<V> {

	// 设置future执行结果为成功
    Promise<V> setSuccess(V result);
	
	// 尝试设置future执行结果为成功,返回是否设置成功
    boolean trySuccess(V result);

	// 设置失败
    Promise<V> setFailure(Throwable cause);

	// 尝试设置future执行结果为失败,返回是否设置成功 
    boolean tryFailure(Throwable cause);

    // 设置为不能取消
    boolean setUncancellable();
	
	// 源码中，以下为覆盖了Future的方法，例如；
	
	Future<V> addListener(GenericFutureListener<? extends Future<? super V>> listener);
	
	@Override
    Promise<V> addListener(GenericFutureListener<? extends Future<? super V>> listener);

}
```

>TestPromise.java | 一个查询用户信息的Promise列子，加入监听再operationComplete完成后，获取查询信息

```java
class TestPromise {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TestPromise testPromise = new TestPromise();
        Promise<String> promise = testPromise.queryUserInfo("10001");
        promise.addListener(new GenericFutureListener<Future<? super String>>() {
            @Override
            public void operationComplete(Future<? super String> future) throws Exception {
                System.out.println("addListener.operationComplete > 查询用户信息完成： " + future.get());
            }
        });
    }

    private Promise<String> queryUserInfo(String userId) {
        NioEventLoopGroup loop = new NioEventLoopGroup();
        // 创建一个DefaultPromise并返回，将业务逻辑放入线程池中执行
        DefaultPromise<String> promise = new DefaultPromise<String>(loop.next());
        loop.schedule(() -> {
            try {
                Thread.sleep(1000);
                promise.setSuccess("微信公众号：bugstack虫洞栈 | 用户ID：" + userId);
                return promise;
            } catch (InterruptedException ignored) {
            }
            return promise;
        }, 0, TimeUnit.SECONDS);
        return promise;
    }

}
```

通过这个例子可以看到，Promise能够在业务逻辑线程中通知Future成功或失败，由于Promise继承了Netty的Future，因此可以加入监听事件。而Future和Promise的好处在于，获取到Promise对象后可以为其设置异步调用完成后的操作，然后立即继续去做其他任务。

### 4、Promise类组织结构&常用方法

>DefaultChannelPromise类组织结构图 | 承接Java并发包Future并增强实现

![微信公众号：bugstack虫洞栈 | DefaultChannelPromise类组织结构图](https://bugstack.cn/assets/images/pic-content/2019/09/netty-code-6-1.png)

Netty中DefalutPromise是一个非常常用的类，这是Promise实现的基础。DefaultChannelPromise是DefalutPromise的子类，加入了channel这个属性。

**DefaultPromise | 使用**
在Netty中使用到Promise的地方会非常多，例如在前面一节《一行简单的writeAndFlush都做了哪些事》分析HeadContext.write中unsafe.write(msg, promise);结合这一章节可以继续深入了解Netty的异步框架原理。另外，服务器/客户端启动时的注册任务，最终会调用unsafe的register，调用过程中会传入一个promise，unsafe进行事件的注册时调用promise可以设置成功/失败。

>SingleThreadEventLoop.java | 注册服务事件循环组

```java
@Override
public ChannelFuture register(Channel channel) {
	return register(new DefaultChannelPromise(channel, this));
}

@Override
public ChannelFuture register(final ChannelPromise promise) {
	ObjectUtil.checkNotNull(promise, "promise");
	promise.channel().unsafe().register(this, promise);
	return promise;
}
```

**DefaultPromise | 实现**
DefaultChannelPromise提供的功能可以分为两个部分；
- 为调用者提供get()和addListener()用于获取Future任务执行结果和添加监听事件。
- 为业务处理任务提供setSuccess()等方法设置任务的成功或失败。

>AbstractFuture.java | get()方法

```java
public abstract class AbstractFuture<V> implements Future<V> {

    @Override
    public V get() throws InterruptedException, ExecutionException {
        await();

        Throwable cause = cause();
        if (cause == null) {
            return getNow();
        }
        if (cause instanceof CancellationException) {
            throw (CancellationException) cause;
        }
        throw new ExecutionException(cause);
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (await(timeout, unit)) {
            Throwable cause = cause();
            if (cause == null) {
                return getNow();
            }
            if (cause instanceof CancellationException) {
                throw (CancellationException) cause;
            }
            throw new ExecutionException(cause);
        }
        throw new TimeoutException();
    }
}

```

DefaultPromise父类AbstractFuture提供了两个get方法；1、无参数的get会阻塞等待；2、有参数的get会等待指定事件，若未结束抛出超时异常。

----

>DefaultPromise.java | DefaultPromise.await()方法

```java
@Override
public Promise<V> await() throws Interrupt
	// 判断Future任务是否结束，内部根据result是否为null判断，setSuccess或setFailure时会通过CAS修改result
    if (isDone()) {
        return this;
    }
	// 线程是否被中断
    if (Thread.interrupted()) {
        throw new InterruptedException(toS
    }
	// 检查当前线程是否与线程池运行的线程是一个
    checkDeadLock();
    synchronized (this) {
        while (!isDone()) {
		   /* waiters计数加1
			* private void incWaiters() {
			*   if (waiters == Short.MAX_VALUE) {
			*       throw new IllegalStateException("too many waiters: " + this);
			*   }
			*   ++waiters;
			* }
			*/
            incWaiters();
            try {
				// Object的方法，让出CPU，加入等待队列
                wait();
            } finally {
				// waiters计数减1
                decWaiters();
            }
        }
    }
    return this;
}
```
await(long timeout, TimeUnit unit)与awite类似，只是调用了Object对象的wait(long timeout, int nanos)方法awaitUninterruptibly()方法在内部catch住了等待线程的中断异常，因此不会抛出中断异常。

----

>DefaultPromise.java | DefaultPromise.addListener0() / DefaultPromise.removeListener0()

```java
private void addListener0(GenericFutureListener<? extends Future<? super V>> listener) {
    if (listeners == null) {
        listeners = listener;
    } else if (listeners instanceof DefaultFutureListeners) {
        ((DefaultFutureListeners) listeners).add(listener);
    } else {
        listeners = new DefaultFutureListeners((GenericFutureListener<?>) listeners, listener);
    }
}
private void removeListener0(GenericFutureListener<? extends Future<? super V>> listener) {
    if (listeners instanceof DefaultFutureListeners) {
        ((DefaultFutureListeners) listeners).remove(listener);
    } else if (listeners == listener) {
        listeners = null;
    }
}
```
- addListener0方法被调用时，将传入的回调类传入到listeners对象中，如果监听多于1个，会创建DefaultFutureListeners对象将回调方法保存在一个数组中。

- removeListener0会将listeners设置为null(只有一个时)或从数组中移除(多个回调时)。

----

>DefaultPromise.java | DefaultPromise.notifyListener0() 通知侦听器

```java
@SuppressWarnings({ "unchecked", "rawtypes" })
private static void notifyListener0(Future future, GenericFutureListener l) {
    try {
        l.operationComplete(future);
    } catch (Throwable t) {
        if (logger.isWarnEnabled()) {
            logger.warn("An exception was thrown by " + l.getClass().getName() + ".operationComplete()", t);
        }
    }
}
```

- 在添加监听器时，如果任务刚好执行完毕,则会立即触发监听事件，触发监听通过notifyListeners()实现。
- addListener和setSuccess都会调用notifyListeners()和Promise内的线程池当前执行的线程是同一个线程，则放在线程池中执行，否则提交到线程池去执行；例如，main线程中调用addListener时任务完成，notifyListeners()执行回调，会提交到线程池中执行；而如果是执行Future任务的线程池中setSuccess()时调用notifyListeners()，会放在当前线程中执行。

- 内部维护了notifyingListeners用来记录是否已经触发过监听事件，只有未触发过且监听列表不为空，才会依次便利并调用operationComplete

----

>DefaultPromise.java | DefaultPromise.setSuccess0()、setFailure0() 唤起等待线程通知成功/失败

```java
// 设置成功后唤醒等待线程
private boolean setSuccess0(V result) {
    return setValue0(result == null ? SUCCESS : result);
}

// 设置成功后唤醒等待线程
private boolean setFailure0(Throwable cause) {
    return setValue0(new CauseHolder(checkNotNull(cause, "cause")));
}

// 通知成功时将结果保存在变量result，通知失败时，使用CauseHolder包装Throwable赋值给result
// RESULT_UPDATER 是一个使用CAS更新内部属性result的类，
// 如果result为null或UNCANCELLABLE，更新为成功/失败结果；UNCANCELLABLE是不可取消状态
private boolean setValue0(Object objResult) {
    if (RESULT_UPDATER.compareAndSet(this, null, objResult) ||
        RESULT_UPDATER.compareAndSet(this, UNCANCELLABLE, objResult)) {
		// 检查是否有服务，如果有，通知他们。
        if (checkNotifyWaiters()) {
            notifyListeners();  // 通知
        }
        return true;
    }
    return false;
}
```

Future任务在执行完成后调用setSuccess()或setFailure()通知Future执行结果；主要逻辑是：修改result的值，若有等待线程则唤醒，通知监听事件。

----

**DefaultChannelPromise实现**



```java
/**
 * The default {@link ChannelPromise} implementation.  It is recommended to use {@link Channel#newPromise()} to create
 * a new {@link ChannelPromise} rather than calling the constructor explicitly.
 */
public class DefaultChannelPromise extends DefaultPromise<Void> implements ChannelPromise, FlushCheckpoint {

    private final Channel channel;
    private long checkpoint;
	
	...
}
```

- 从继承关系可以看到DefaultChannelPromise是DefaultPromise的实现类，内部维护了一个通道变量Channel。
- 另外还实现了FlushCheckpoint接口，给ChannelFlushPromiseNotifier使用，我们可以将ChannelFuture注册到ChannelFlushPromiseNotifier类，当有数据写入或到达checkpoint时使用。

```java
interface FlushCheckpoint {
    long flushCheckpoint();
    void flushCheckpoint(long checkpoint)
    ChannelPromise promise();
}
```

------------

上一篇：[netty案例，netty4.1源码分析篇五《一行简单的writeAndFlush都做了哪些事》](/itstack-demo-netty-4/2019/09/14/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E4%BA%94-%E4%B8%80%E8%A1%8C%E7%AE%80%E5%8D%95%E7%9A%84writeAndFlush%E9%83%BD%E5%81%9A%E4%BA%86%E5%93%AA%E4%BA%9B%E4%BA%8B.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**rpc案例源码**」获取本文源码&更多原创专题案例！
