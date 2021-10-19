---
layout: post
category: interview
title: 面经手册 · 第18篇《AQS 共享锁，Semaphore、CountDownLatch，听说数据库连接池可以用到！》
tagline: by 小傅哥
tag: [java,interview]
excerpt: 强人锁难，独占锁、共享锁、公平锁、非公平锁、读锁、写锁，不会！？怎能为锁欲为！并发工具包的类可能有些平常用的不多，但如果你需要开发框架、中间件就会需要到。
lock: need
---

# 面经手册 · 第18篇《AQS 共享锁，Semaphore、CountDownLatch，听说数据库连接池可以用到！》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`学Java怎么能，突飞猛进的成长？`

是不是你看见过的突飞猛进都是别人，但自己却很难！

其实并没有一天的突飞猛进，也没有一口吃出来的胖子。有得更多的时候日积月累、不断沉淀，最后才能爆发、破局！

举个简单的例子，如果你大学毕业时候已经写了40万行代码，还找不到工作吗？但40万行平均到每天并不会很多，重要的是持之以恒的坚持。

## 二、面试题

`谢飞机，小记！` 东风吹、战鼓擂，不加班、谁怕谁！哈哈哈，找我大哥去。

**谢飞机**：喂，大哥。我女友面试卡住了，强人`锁`难，锁我也不会！

**面试官**：你不应该不会呀，问你一个，基于 AQS 实现的锁都有哪些？

**谢飞机**：嗯，有 ReentrantLock...

**面试官**：还有呢？

**谢飞机**：好像想不起来了，sync也不是！

**面试官**：哎，学点漏点，不思考、不总结、不记录。你这样人家面试你就没法聊了，最起码你要有点深度。

**谢飞机**：嘿嘿，记住了。来我家吃火锅吧，细聊。

## 三、共享锁 和 AQS

### 1. 基于 AQS 实现的锁有哪些？

![图 18-1 基于 AQS 实现的锁](https://bugstack.cn/assets/images/2020/interview/interview-18-1.png)

AQS（AbstractQueuedSynchronizer），是 Java 并发包中非常重要的一个类，大部分锁的实现也是基于 AQS 实现的，包括：
- `ReentrantLock`，可重入锁。这个是我们最开始介绍的锁，也是最常用的锁。通常会与 synchronized 做比较使用。
- `ReentrantReadWriteLock`，读写锁。读锁是共享锁、写锁是独占锁。
- `Semaphore`，信号量锁。主要用于控制流量，比如：数据库连接池给你分配10个链接，那么让你来一个连一个，连到10个还没有人释放，那你就等等。
- `CountDownLatch`，闭锁。Latch 门闩的意思，比如：说四个人一个漂流艇，坐满了就推下水。

这一章节我们主要来介绍 Semaphore ，信号量锁的实现，其实也就是介绍一个关于共享锁的使用和源码分析。

### 2. Semaphore 共享锁使用

```java
Semaphore semaphore = new Semaphore(2, false); // 构造函数入参，permits：信号量、fair：公平锁/非公平锁
for (int i = 0; i < 8; i++) {
    new Thread(() -> {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + "蹲坑");
            Thread.sleep(1000L);
        } catch (InterruptedException ignore) {
        } finally {
            semaphore.release();
        }
    }, "蹲坑编号：" + i).start();
}
```

这里我们模拟了一个在高速服务区，厕所排队蹲坑的场景。由于坑位有限，为了避免造成拥挤和踩踏，保安人员在门口拦着，感觉差不多，一次释放两个进去，一直到都释放。*你也可以想成早上坐地铁上班，或者旺季去公园，都是一批一批的放行*

**测试结果**

```java
蹲坑编号：0蹲坑
蹲坑编号：1蹲坑

蹲坑编号：2蹲坑
蹲坑编号：3蹲坑

蹲坑编号：4蹲坑
蹲坑编号：5蹲坑

蹲坑编号：6蹲坑
蹲坑编号：7蹲坑

Process finished with exit code 0
```

- Semaphore 的构造函数可以传递是公平锁还是非公平锁，最终的测试结果也不同，可以自行尝试。
- 测试运行时，会先输出`0坑、1坑`，`之后2坑、3坑`...，每次都是这样两个，两个的释放。这就是 Semaphore 信号量锁的作用。

### 3. Semaphore 源码分析

#### 3.1 构造函数

```java
public Semaphore(int permits) {
    sync = new NonfairSync(permits);
}

public Semaphore(int permits, boolean fair) {
    sync = fair ? new FairSync(permits) : new NonfairSync(permits);
}
```

*permits：n. 许可证，特许证(尤指限期的)*

默认情况下只需要传入 permits 许可证数量即可，也就是一次允许放行几个线程。构造函数会创建非公平锁。如果你需要使用 Semaphore 共享锁中的公平锁，那么可以传入第二个构造函数的参数 fair = false/true。true：FairSync，公平锁。*在我们前面的章节已经介绍了公平锁相关内容和实现，以及CLH、MCS* [《公平锁介绍》](https://bugstack.cn/interview/2020/11/04/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC16%E7%AF%87-%E7%A0%81%E5%86%9C%E4%BC%9A%E9%94%81-ReentrantLock%E4%B9%8B%E5%85%AC%E5%B9%B3%E9%94%81%E8%AE%B2%E8%A7%A3%E5%92%8C%E5%AE%9E%E7%8E%B0.html)

**初始`许可证`数量**

```java
FairSync/NonfairSync(int permits) {
    super(permits);
}

Sync(int permits) {
    setState(permits);
}

protected final void setState(int newState) {
    state = newState;
}
```

在构造函数初始化的时候，无论是公平锁还是非公平锁，都会设置 AQS 中 state 数量值。这个值也就是为了下文中可以获取的信号量扣减和增加的值。

#### 3.2 acquire 获取信号量

| 方法                                  | 描述                                       |
| ------------------------------------- | ------------------------------------------ |
| `semaphore.acquire()`                 | 一次获取一个信号量，响应中断               |
| `semaphore.acquire(2)`                | 一次获取n个信号量，响应中断（一次占2个坑） |
| `semaphore.acquireUninterruptibly()`  | 一次获取一个信号量，不响应中断             |
| `semaphore.acquireUninterruptibly(2)` | 一次获取n个信号量，不响应中断              |

- 其实获取信号量的这四个方法，主要就是，一次获取几个和是否响应中断的组合。
- `semaphore.acquire()`，源码中实际调用的方法是，` sync.acquireSharedInterruptibly(1)`。也就是相应中断，一次只占一个坑。
- `semaphore.acquire(2)`，同理这个就是一次要占两个名额，也就是许可证。*生活中的场景就是我给我朋友排的对，她来了，进来吧。*

#### 3.3 acquire 释放信号量

| 方法                   | 描述               |
| ---------------------- | ------------------ |
| `semaphore.release()`  | 一次释放一个信号量 |
| `semaphore.release(2)` | 一次获取n个信号量  |

有获取就得有释放，获取了几个信号量就要释放几个信号量。*当然你可以尝试一下，获取信号量 semaphore.acquire(2) 两个，释放信号量 semaphore.release(1)，看看运行效果*

#### 3.4 公平锁实现

**信号量获取过程**，一直到公平锁实现。`semaphore.acquire` -> `sync.acquireSharedInterruptibly(permits)` -> `tryAcquireShared(arg)`

```java
semaphore.acquire(1);

public void acquire(int permits) throws InterruptedException {
    if (permits < 0) throw new IllegalArgumentException();
    sync.acquireSharedInterruptibly(permits);
}

public final void acquireSharedInterruptibly(int arg)
        throws InterruptedException {
    if (Thread.interrupted())
        throw new InterruptedException();
    if (tryAcquireShared(arg) < 0)
        doAcquireSharedInterruptibly(arg);
}
```

**FairSync.tryAcquireShared**

```java
protected int tryAcquireShared(int acquires) {
    for (;;) {
        if (hasQueuedPredecessors())
            return -1;
        int available = getState();
        int remaining = available - acquires;
        if (remaining < 0 ||
            compareAndSetState(available, remaining))
            return remaining;
    }
}
```

- `hasQueuedPredecessors`，公平锁的主要实现逻辑都在于这个方法的使用。它的目的就是判断有线程排在自己前面没，以及把线程添加到队列中的逻辑实现。*在前面我们介绍过CLH等实现，可以往前一章节阅读*
- `for (;;)`，是一个自旋的过程，通过 CAS 来设置 state 偏移量对应值。这样就可以避免多线程下竞争获取信号量冲突。
- `getState()`，在构造函数中已经初始化 state 值，在这里获取信号量时就是使用 CAS 不断的扣减。
- 另外需要注意，共享锁和独占锁在这里是有区别的，独占锁直接返回true/false，共享锁返回的是int值。
	- 如果该值小于0，则当前线程获取共享锁失败。
	- 如果该值大于0，则当前线程获取共享锁成功，并且接下来其他线程尝试获取共享锁的行为很可能成功。
	- 如果该值等于0，则当前线程获取共享锁成功，但是接下来其他线程尝试获取共享锁的行为会失败。

#### 3.5 非公平锁实现

**NonfairSync.nonfairTryAcquireShared**

```java
protected int tryAcquireShared(int acquires) {
    return nonfairTryAcquireShared(acquires);
}

final int nonfairTryAcquireShared(int acquires) {
    for (;;) {
        int available = getState();
        int remaining = available - acquires;
        if (remaining < 0 ||
            compareAndSetState(available, remaining))
            return remaining;
    }
}
```

- 有了公平锁的实现，非公平锁的理解就比较简单了，只是拿去了 `if (hasQueuedPredecessors())` 的判断操作。
- 其他的逻辑实现都和公平锁一致。

#### 3.6 获取信号量失败，加入同步等待队列

在公平锁和非公平锁的实现中，我们已经看到正常获取信号量的逻辑。那么如果此时不能正常获取信号量呢？其实这部分线程就需要加入到同步队列。

**doAcquireSharedInterruptibly**

```java
public final void acquireSharedInterruptibly(int arg)
        throws InterruptedException {
    if (Thread.interrupted())
        throw new InterruptedException();
    if (tryAcquireShared(arg) < 0)
        doAcquireSharedInterruptibly(arg);
}

private void doAcquireSharedInterruptibly(int arg)
    throws InterruptedException {
    final Node node = addWaiter(Node.SHARED);
    boolean failed = true;
    try {
        for (;;) {
            final Node p = node.predecessor();
            if (p == head) {
                int r = tryAcquireShared(arg);
                if (r >= 0) {
                    setHeadAndPropagate(node, r);
                    p.next = null; // help GC
                    failed = false;
                    return;
                }
            }
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt())
                throw new InterruptedException();
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```

- 首先 `doAcquireSharedInterruptibly` 方法来自 AQS 的内部方法，与我们在学习竞争锁时有部分知识点相同，但也有一些差异。比如：`addWaiter(Node.SHARED)`，`tryAcquireShared`，我们主要介绍下这内容。
- `Node.SHARED`，其实没有特殊含义，它只是一个标记作用，用于判断是否共享。`final boolean isShared() {
    return nextWaiter == SHARED;
}`
- `tryAcquireShared`，主要是来自 `Semaphore` 共享锁中公平锁和非公平锁的实现。用来获取同步状态。
- `setHeadAndPropagate(node, r)`，如果r > 0，同步成功后则将当前线程结点设置为头结点，同时 helpGC，p.next = null，断链操作。
- `shouldParkAfterFailedAcquire(p, node)`，调整同步队列中 node 结点的状态，并判断是否应该被挂起。这在我们之前关于锁的文章中已经介绍。
-  `parkAndCheckInterrupt()`，判断是否需要被中断，如果中断直接抛出异常，当前结点请求也就结束。
-  `cancelAcquire(node)`，取消该节点的线程请求。

### 4. CountDownLatch 共享锁使用

CountDownLatch 也是共享锁的一种类型，之所以在这里体现下，是因为它和 Semaphore 共享锁，既相似有不同。

CountDownLatch 更多体现的组团一波的思想，同样是控制人数，但是需要够一窝。比如：我们说过的4个人一起上皮划艇、两个人一起上跷跷板、*2个人一起蹲坑我没见过*，这样的方式就是门闩 CountDownLatch 锁的思想。

```java
public static void main(String[] args) throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(10);
    ExecutorService exec = Executors.newFixedThreadPool(10);
    for (int i = 0; i < 10; i++) {
        exec.execute(() -> {
            try {
                int millis = new Random().nextInt(10000);
                System.out.println("等待游客上船，耗时：" + millis + "(millis)");
                Thread.sleep(millis);
            } catch (Exception ignore) {
            } finally {
                latch.countDown(); // 完事一个扣减一个名额
            }
        });
    }
    // 等待游客
    latch.await();
    System.out.println("船长急躁了，开船!");
    // 关闭线程池
    exec.shutdown();
}
```

- 这一个公园游船的场景案例，等待10个乘客上传，他们比较墨迹。
- 上一个扣减一个 `latch.countDown()`
- 等待游客都上船 `latch.await()`
- 最后船长开船！！`急躁了`

**测试结果**

```java
等待游客上船，耗时：6689(millis)
等待游客上船，耗时：2303(millis)
等待游客上船，耗时：8208(millis)
等待游客上船，耗时：435(millis)
等待游客上船，耗时：9489(millis)
等待游客上船，耗时：4937(millis)
等待游客上船，耗时：2771(millis)
等待游客上船，耗时：4823(millis)
等待游客上船，耗时：1989(millis)
等待游客上船，耗时：8506(millis)
船长急躁了，开船!

Process finished with exit code 0
```

- 在你实际的测试中会发现，`船长急躁了，开船!`，会需要等待一段时间。
- 这里体现的就是门闩的思想，组队、一波带走。
- CountDownLatch 的实现与 Semaphore  基本相同、细节略有差异，就不再做源码分析了。

## 四、总结

- 在有了 AQS、CLH、MCS，等相关锁的知识了解后，在学习其他知识点也相对容易。基本以上和前几章节关于锁的介绍，也是面试中容易问到的点。*可能由于目前分布式开发较多，单机的多线程性能压榨一般较少，但是对这部分知识的了解非常重要*
- 得益于Lee老爷子的操刀，并发包锁的设计真的非常优秀。每一处的实现都可以说是精益求精，所以在学习的时候可以把小傅哥的文章当作抛砖，之后继续深挖设计精髓，不断深入。
- 共享锁的使用可能平时并不多，但如果你需要设计一款类似数据库线程池的设计，那么这样的信号量锁的思想就非常重要了。所以在学习的时候也需要有技术迁移的能，不断把这些知识复用到实际的业务开发中。
