---
title: dump eclipse mat
lock: need
---

# Eclipse MAT 分析 Java heap space dump 日志

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

写了这么久Java代码，操作了那么多发布上线，那你看到过`java.lang.OutOfMemoryError: Java heap space`吗？如果有幸看到了，你是怎么解决的呢？是束手无策，还是有排查工具。如果这样的问题是被面试问的，没做过就很难回答了。那么怎么学习一下呢？

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/student-learn-01.gif" width="150px">
</div>

**什么场景才会有 OutOfMemoryError**

能写出 OutOfMemoryError 的不是编码不精，就是故意埋坑。其实很多时候我们很难在正常编码写写出一个 OutOfMemoryError，因为这个过程你需要大量的往内存加数据，逐步把 JVM 的内存耗尽。而只是1G内存容量（-Xmx1G），仅订单数据就要300多万条记录，谁又能写个 MyBatis SQL 操作，要一次直接把`300万`数据查询到程序内存里呢。

不过，还真可能有！但这个不是程序员故意编码查询300万，而是在做数据导出的时候，处理分页的加法计算有问题，导致每次都是 limit 0,n，n 不断的加大。正确的应该是 limit m,n 这样的查询。这样的 `OutOfMemoryError`，在过往工作中就遇到过，最终经过排查到一次要从数据库获取几百万条数据，导致服务宕机。

那么，为了更好的让大家学习到这样的场景以及使用工具排查，小傅哥这里专门做了案例。可以一起学习下。

## 一、环境准备

为了方便大家进行学习验证，小傅哥这里准备好了一个测试工程和相关的环境安装。你可以在安装完工程后，执行 `ApiTest#test_insert` 向数据库写入250万数据，便于测试。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-dump-01.png" width="450px">
</div>

- 工程：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-dump](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-dump)
- 说明：`dev-ops` 下提供了 docker 安装 mysql 以及初始化表。如果你没有 docker 也可以直接本地数据库导入库表。

## 二、软件安装

### 1. 分析软件

对于 OutOfMemoryError 的错误排查，需要让工程导出 dump 日志文件，之后通过软件工具分析。分析 dump 的软件有2个常见的；免费的 Eclipse MAT、付费的 JProfiler(可短期体验)

- Eclipse MAT：[https://eclipse.dev/mat/downloads.php](https://eclipse.dev/mat/downloads.php)
- JProfiler：[https://www.ej-technologies.com/download/jprofiler/files](https://www.ej-technologies.com/download/jprofiler/files) - `学会 MAT 这个可以自己体验`

### 2. Eclipse MAT 安装配置

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-dump-02.png" width="450px">
</div>

#### 2.1 加大内存

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-dump-04.png" width="550px">
</div>

```java
-startup
../Eclipse/plugins/org.eclipse.equinox.launcher_1.6.600.v20231106-1826.jar
--launcher.library
../Eclipse/plugins/org.eclipse.equinox.launcher.cocoa.macosx.aarch64_1.2.800.v20231003-1442
-vmargs
--add-exports=java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED
-Xms4096m
-Xmx4096m
-Dorg.eclipse.swt.internal.carbon.smallFonts
-XstartOnFirstThread
```

- 需要配置 `-Xms4096m`、`-Xmx4096m` 否则过大的 dump 日志，就不能加载进去分析了。大一点配置分析的更快。

#### 2.2 `does not contain the JNI_CreateJavaVM symbol.` 报错处理

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-dump-03.png" width="450px">
</div>

```java
<array>
            
  <string>-vm</string>
  <string>/Library/Java/JavaVirtualMachines/jdk1.8.0_341.jdk/Contents/Home/bin</string>

  <string>-keyring</string>
  <string>~/.eclipse_keyring</string>
        
</array>
```

- 如果遇到 `The JVM shared library "/Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home/bin/../lib/server/libjvm.dylib" does not contain the JNI_CreateJavaVM symbol.` 可以打开 Info.plist 添加 -vm 和 jdk 路径。

## 三、产生 dump 案例

首先你需要为你要运行的方法添加 VM Options 当你运行一个方法后，添加 JVM 配置，这样才能到处 dump

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-dump-05.png" width="950px">
</div>

- `-Xms128M -Xmx128M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../xfg-dev-tech-dump/docs/dump`
- HeapDumpPath 为你的工程完成路径，到出到 dump 文件夹下。
- 运行方法后，就可以在 docs/dump 就可以看到产生的日志了。

### 1. 数据库查询

```java
<select id="queryPage" resultMap="dataMap">
        SELECT * FROM user_order
        limit 0, 2500000
</select>

@Test
public void test_java_heap_space_sql() throws InterruptedException {
    while (true){
        userOrderDao.queryPage();
    }
}
```

### 2. 线程池过大

```java
@Test
public void test_thread_pool_java_heap_space() {
    // 创建一个固定大小的线程池
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    try {
        // 不断提交占用大量内存的任务
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            executorService.submit(new MemoryHogTask());
        }
    } catch (OutOfMemoryError e) {
        System.out.println("Caught OutOfMemoryError: " + e.getMessage());
    } finally {
        // 关闭线程池
        executorService.shutdown();
    }
}

static class MemoryHogTask implements Runnable {
    @Override
    public void run() {
        try {
            // 分配一个大数组来占用内存
            int[] memoryHog = new int[1000000]; // 大约占用 4MB 内存
            // 模拟一些计算以避免 JIT 优化掉内存分配
            for (int i = 0; i < memoryHog.length; i++) {
                memoryHog[i] = i;
            }
            // 保持任务在一定时间内占用内存
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

### 3. list 添加过多数据

```java
@Test
public void test_java_heap_space_list() throws InterruptedException {
    List<byte[]> list = new ArrayList<>();
    while (true) {
        byte[] bytes = new byte[1024 * 1024 * 1024];
        list.add(bytes);
        TimeUnit.SECONDS.sleep(1);
    }
}
```

- 这类场景是比较多的，比如我们做营销活动，要把很多的活动数据预热到缓存，如果 JVM 配置的比较低，是可能会出现 `Java heap space` 的。

## 四、Eclipse MAT 分析

### 1. 导入 dump 文件

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-dump-06.png" width="650px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-dump-07.png" width="650px">
</div>

### 2. 查看统计树

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-dump-08.png" width="450px">
</div>

### 3. 排序对象

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-dump-09.png" width="850px">
</div>

### 4. 引用关系

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-dump-10.png" width="850px">
</div>

- 点击查看，被谁引用了。

### 5. 逐层分析 - 进入对象详情

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-dump-11.png" width="850px">
</div>

- 看看这个 Object 值装的是什么。

### 6. 发现问题

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-dump-12.png" width="850px">
</div>

- 看到了在检索数据库数据。其实前面就已经定义到哪里的方法导致，这里可以具体看到细节。

### 7. 其他分析

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-dump-13.png" width="650px">
</div>

- 我们本案例采用的是 MySql 8.x 如果你使用其他线程池工具，还可能会返回具体的 SQL 语句一起打印出来。方便分析。

好啦，有了这样一个分析过程，你也可以尝试熟悉下工具，分析分析其他的 Java heap space 场景。几次玩下来也就熟悉这个工具了。