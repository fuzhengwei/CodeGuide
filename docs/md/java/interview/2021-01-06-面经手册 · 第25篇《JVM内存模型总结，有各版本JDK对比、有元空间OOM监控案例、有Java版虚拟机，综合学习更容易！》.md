---
layout: post
category: interview
title: 面经手册 · 第25篇《JVM内存模型总结，有各版本JDK对比、有元空间OOM监控案例、有Java版虚拟机，综合学习更容易！》
tagline: by 小傅哥
tag: [java,interview]
excerpt: 手撸JVM运行时数据区、亲测元空间OOM溢出，从实践入手了解各个版本 JDK 关于内存模型结构的演变过程，是如何驾驭他们的，包括：程序计数器、Java 虚拟机栈、本地方法栈、堆和元空间等。
lock: need
---

# 面经手册 · 第25篇《JVM内存模型总结，有各版本JDK对比、有元空间OOM监控案例、有Java版虚拟机，综合学习更容易！》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`看了一篇文章30岁有多难！`

每篇文章的开篇总喜欢写一些，从个人视角看这个世界的感悟。

最近看到一篇文章，`30岁有多难`。文中的一些主人公好像在学业、工作、生活、爱情等方面都过的都不如意。要不是错过这，要不是走错那。总结来看，就像是很倒霉的一群倒霉蛋儿在跟生活对干！

但其实每个人可能都遇到过生活中最难的时候，或早或晚。就像我刚毕业不久时一连串遇到；`冬天里丢过第一部手机`、`修一个进了水的电脑`、`租的房子第一次被骗`，一连串下来头一次要赶在工资没发的时候，选择少吃早饭还是午饭，看看能扛过去那顿。

哈哈哈哈哈，现在想想还挺有意思的，不过这些乱遭的事很多是自己的意识和能力不足时做出的错误选择而导致的。

人那，想开车就要考驾照，想走远就要有能力。多提升认知，多拓宽眼界！`生活的意义就是不断的更新自己！`

## 二、面试题

`谢飞机，小记！`，冬风吹、战鼓擂。被窝里，谁怕谁。

**谢飞机**：歪？大哥，你在吗？

**面试官**：咋了，大周末的，这么早打电话！？

**谢飞机**：我梦见，我去谷歌写JVM了，给你们公司用，之后蹦了，让我起来改bug！

**面试官**：啊！？啊，那我问你，JDK 1.8 与 JDK 1.7 在运行时数据区的设计上，你都怎么做的优化策略的？

**谢飞机**：我没写这，我不知道！

**面试官**：擦。。。

## 三、 JDK1.6、JDK1.7、JDK1.8 内存模型演变

![图 25-1  JDK1.6、JDK1.7、JDK1.8，内存模型演变](https://bugstack.cn/assets/images/2020/interview/interview-25-1.png)

如图 25-1 是 JDK 1.6、1.7、1.8 的内存模型演变过程，其实这个内存模型就是 JVM 运行时数据区依照JVM虚拟机规范的具体实现过程。

在图 25-1 中各个版本的迭代都是为了更好的适应CPU性能提升，最大限度提升的JVM运行效率。这些版本的JVM内存模型主要有以下差异：
- JDK 1.6：有永久代，静态变量存放在永久代上。
- JDK 1.7：有永久代，但已经把字符串常量池、静态变量，存放在堆上。逐渐的减少永久代的使用。
- JDK 1.8：无永久代，运行时常量池、类常量池，都保存在元数据区，也就是常说的`元空间`。但字符串常量池仍然存放在堆上。

## 四、内存模型各区域介绍

### 1. 程序计数器

- 较小的内存空间、线程私有，记录当前线程所执行的字节码行号。
- 如果执行 Java 方法，计数器记录虚拟机字节码当前指令的地址，本地方法则为空。
- 这一块区域没有任何 OutOfMemoryError 定义。

**以上**，就是关于程序计数器的定义，如果这样看没有感觉，我们举一个例子。

定义一段 Java 方法的代码，这段代码是计算圆形的周长。

```java
public static float circumference(float r){
        float pi = 3.14f;
        float area = 2 * pi * r;
        return area;
}
```

接下来，如图 25-2 是这段代码的在虚拟机中的执行过程，左侧是它的程序计数器对应的行号。

![图 25-2 程序计数器](https://bugstack.cn/assets/images/2020/interview/interview-25-2.png)

- 这些行号每一个都会对应一条需要执行的字节码指令，是压栈还是弹出或是执行计算。
- 之所以说是线程私有的，因为如果不是私有的，那么整个计算过程最终的结果也将错误。

### 2. Java虚拟机栈

- 每一个方法在执行的同时，都会创建出一个栈帧，用于存放局部变量表、操作数栈、动态链接、方法出口、线程等信息。
- 方法从调用到执行完成，都对应着栈帧从虚拟机中入栈和出栈的过程。
- 最终，栈帧会随着方法的创建到结束而销毁。

可能这么只从定义看上去仍然没有什么感觉，我们再找一个例子。

这是一个关于`斐波那契数列（Fibonacci sequence）`求值的例子，我们通过斐波那契数列在虚拟机中的执行过程，来体会Java虚拟机栈的用途。

>斐波那契数列（Fibonacci sequence），又称黄金分割数列、因数学家列昂纳多·斐波那契（Leonardoda Fibonacci）以兔子繁殖为例子而引入，故又称为“兔子数列”，指的是这样一个数列：1、1、2、3、5、8、13、21、34、……在数学上，斐波纳契数列以如下被以递推的方法定义：F(1)=1，F(2)=1, F(n)=F(n-1)+F(n-2)（n>=3，n∈N*）在现代物理、准晶体结构、化学等领域，斐波纳契数列都有直接的应用，为此，美国数学会从1963年起出版了以《斐波纳契数列季刊》为名的一份数学杂志，用于专门刊载这方面的研究成果。

![图 25-3 斐波那契数列在虚拟机栈中的执行过程](https://bugstack.cn/assets/images/2020/interview/interview-25-3.png)

- 整个这段流程，就是方法的调用和返回。在调用过程申请了操作数栈的深度和局部变量的大小。
- 以及相应的信息从各个区域获取并操作，其实也就是入栈和出栈的过程。

### 3. 本地方法栈

- 本地方法栈与Java虚拟机栈作用类似，唯一不同的就是本地方法栈执行的是Native方法，而虚拟机栈是为JVM执行Java方法服务的。
- 另外，与 Java 虚拟机栈一样，本地方法栈也会抛出 StackOverflowError 和 OutOfMemoryError 异常。
- JDK1.8 HotSpot虚拟机直接就把本地方法栈和虚拟机栈合二为一。

*关于本地方法栈在以上的例子已经涉及了这部分内容，这里就不在赘述了。*

### 4. 堆和元空间

![图 25-4 Java 堆区域划分](https://bugstack.cn/assets/images/2020/interview/interview-25-4.png)

- JDK 1.8 JVM 的内存结构主要由三大块组成：堆内存、元空间和栈，Java 堆是内存空间占据最大的一块区域。
- Java 堆，由年轻代和年老代组成，分别占据1/3和2/3。
- 而年轻代又分为三部分，**Eden**、**From Survivor**、**To Survivor**，占据比例为8:1:1，可调。
- 另外这里我们特意画出了元空间，也就是直接内存区域。在 JDK 1.8 之后就不在堆上分配方法区了。
- **元空间**从虚拟机Java堆中转移到本地内存，默认情况下，元空间的大小仅受本地内存的限制，说白了也就是以后不会因为永久代空间不够而抛出OOM异常出现了。*jdk1.8以前版本的 class和JAR包数据存储在 PermGen下面 ，PermGen 大小是固定的，而且项目之间无法共用，公有的 class，所以比较容易出现OOM异常。*
- 升级 JDK 1.8后，元空间配置参数，`-XX:MetaspaceSize=512M XX:MaxMetaspaceSize=1024M`。教你个小技巧通过jps、jinfo查看元空间，如下：
  - ![通过命令查看元空间](https://bugstack.cn/assets/images/2020/interview/interview-25-4-1.png)
  - 通过jinfo查看默认MetaspaceSize大小（约20M）,MaxMetaspaceSize比较大。
	

**其他：关于 JDK1.8 元空间的介绍：** Move part of the contents of the permanent generation in Hotspot to the Java heap and the remainder to native memory. [http://openjdk.java.net/jeps/122](http://openjdk.java.net/jeps/122)

### 5. 常量池

- 从 JDK 1.7开始把常量池从永久代中剥离，直到 JDK1.8 去掉了永久代。而字符串常量池一直放在堆空间，用于存储字符串对象，或是字符串对象的引用。

## 五、手撸虚拟机(内存模型)

其实以上的内容，已经完整的介绍了JVM虚拟机的内存模型，也就是运行时数据区的结构。但是这东西看完可能就忘记了，因为缺少一个可亲手操作的代码。

**所以**，这里我给大家用Java代码写一段关于数据槽、栈帧、局部变量、虚拟机栈以及堆的代码结构，让大家更好的加深对虚拟机内存模型的印象。

### 1. 工程结构

```java
运行时数据区
├── heap
│   ├── constantpool
│   ├── methodarea
│   │   ├── Class.java
│   │   ├── ClassMember.java
│   │   ├── Field.java
│   │   ├── Method.java
│   │   ├── MethodDescriptor.java
│   │   ├── MethodDescriptorParser.java
│   │   ├── MethodLookup.java
│   │   ├── Object.java
│   │   ├── Slots.java
│   │   └── StringPool.java
│   └── ClassLoader.java
├── Frame.java
├── JvmStack.java
├── LocalVars.java
├── OperandStack.java
├── Slot.java
└── Thread.java
```

以上这部分就是使用Java实现的部分JVM虚拟机功能，这部分主要包括如下内容：
- Frame，栈帧
- JvmStack，虚拟机栈
- LocalVars，局部变量
- OperandStack，操作数栈
- Slot，数据槽
- Thread，线程
- heap，堆，里面包括常量池和方法区

### 2. 重点代码

**操作数栈 OperandStack**

```java
public class OperandStack {

    private int size = 0;
    private Slot[] slots;

    public OperandStack(int maxStack) {
        if (maxStack > 0) {
            slots = new Slot[maxStack];
            for (int i = 0; i < maxStack; i++) {
                slots[i] = new Slot();
            }
        }
    }
    //...
}
```

**虚拟机栈 OperandStack**

```java
public class JvmStack {

    private int maxSize;
    private int size;
    private Frame _top;
    
    //...
}
```

**栈帧 Frame**

```java
public class Frame {

    //stack is implemented as linked list
    Frame lower;

    //局部变量表
    private LocalVars localVars;

    //操作数栈
    private OperandStack operandStack;

    private Thread thread;

    private Method method;

    private int nextPC;
 
    //...
}
```

- 关于代码结构看到这有点感觉了吗？
- Slot数据槽，就是一个数组结构，用于存放数据的。
- 操作数栈、局部变量表，都是使用数据槽进行入栈入栈操作。
- 在栈帧里，可以看到连接、局部变量表、操作数栈、方法、线程等，那么文中说到的当有一个新的`每一个方法在执行的同时，都会创建出一个栈帧`，是不就对了上，可以真的理解了。
- 如果你对JVM的实现感兴趣，可以阅读`用Java实现JVM源码`：[https://github.com/fuzhengwei/itstack-demo-jvm](https://github.com/fuzhengwei/itstack-demo-jvm)

## 六、jconsole监测元空间溢出

不是说 JDK 1.8 的内存模型把永久代下掉，换上`元空间`了吗？但不测试下，就感受不到呀，没有证据！

所有关于代码逻辑的学习，都需要有数据基础和证明过程，这样才能有深刻的印象。走着，带你把元空间干满，让它OOM！

### 1. 找段持续创建大对象的代码

```java
public static void main(String[] args) throws InterruptedException {
    
    Thread.sleep(5000);
    
    ClassLoadingMXBean loadingBean = ManagementFactory.getClassLoadingMXBean();
    while (true) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(MetaSpaceOomMock.class);
        enhancer.setCallbackTypes(new Class[]{Dispatcher.class, MethodInterceptor.class});
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                return 1;
            }
            @Override
            public boolean equals(Object obj) {
                return super.equals(obj);
            }
        });
        System.out.println(enhancer.createClass().getName() + loadingBean.getTotalLoadedClassCount() + loadingBean.getLoadedClassCount() + loadingBean.getUnloadedClassCount());
    }
}
```

- 网上找了一段基于CGLIB的，你可以写一些其他的。
- `Thread.sleep(5000);`，睡一会，方便我们点检测，要不程序太快就异常了。

### 2. 调整元空间大小

默认情况下元空间太大了，不方便测试出结果，所以我们把它调的小一点。

```java
-XX:MetaspaceSize=8m
-XX:MaxMetaspaceSize=80m
```

### 3. 设置监控参数

基于 jconsole 监控，我们需要设置下参数。

```java
-Djava.rmi.server.hostname=127.0.0.1
-Dcom.sun.management.jmxremote
-Dcom.sun.management.jmxremote.port=7397
-Dcom.sun.management.jmxremote.ssl=false
-Dcom.sun.management.jmxremote.authenticate=false
```

### 4. 测试运行

#### 4.1 配置参数

**以上的测试参数**，配置到IDEA中运行程序里就可以，如下：

![图 25-5 设置程序运行参数，监控OOM](https://bugstack.cn/assets/images/2020/interview/interview-25-5.png)

另外，jconsole 可以通过 IDEA 提供的 Terminal 启动，直接输入 `jconsole`，回车即可。

#### 4.2 测试结果

```java
org.itstack.interview.MetaSpaceOomMock$$EnhancerByCGLIB$$bd2bb16e999099900
org.itstack.interview.MetaSpaceOomMock$$EnhancerByCGLIB$$9c774e64999199910
org.itstack.interview.MetaSpaceOomMock$$EnhancerByCGLIB$$cac97732999299920
org.itstack.interview.MetaSpaceOomMock$$EnhancerByCGLIB$$91c6a15a999399930
Exception in thread "main" java.lang.IllegalStateException: Unable to load cache item
	at net.sf.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:79)
	at net.sf.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
	at net.sf.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:119)
	at net.sf.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:294)
	at net.sf.cglib.proxy.Enhancer.createHelper(Enhancer.java:480)
	at net.sf.cglib.proxy.Enhancer.createClass(Enhancer.java:337)
	at org.itstack.interview.MetaSpaceOomMock.main(MetaSpaceOomMock.java:34)
Caused by: java.lang.OutOfMemoryError: Metaspace
	at java.lang.Class.forName0(Native Method)
	at java.lang.Class.forName(Class.java:348)
	at net.sf.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:467)
	at net.sf.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:339)
	at net.sf.cglib.proxy.Enhancer.generate(Enhancer.java:492)
	at net.sf.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:96)
	at net.sf.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:94)
	at net.sf.cglib.core.internal.LoadingCache$2.call(LoadingCache.java:54)
	at java.util.concurrent.FutureTask.run$$$capture(FutureTask.java:266)
	at java.util.concurrent.FutureTask.run(FutureTask.java)
	at net.sf.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:61)
	... 6 more
```

- 要的就是这句，java.lang.OutOfMemoryError: Metaspace，元空间OOM，证明 JDK1.8 已经去掉永久代，换位元空间。

#### 4.3 监控截图

![图 25-6 jconsole监测元空间溢出](https://bugstack.cn/assets/images/2020/interview/interview-25-6.png)

- 图 25-6，就是监测程序OOM时的元空间表现。这回对这个元空间就有感觉了吧！

## 七、总结

- 本文从 JDK 各个版本关于内存模型结构的演变，来了解各个区域，包括：程序计数器、Java 虚拟机栈、本地方法栈、堆和元空间。并了解从 JDK 1.8 开始去掉方法区引入元空间的核心目的和作用。
- 在通过手撸JVM代码的方式让大家对运行时数据区有一个整体的认知，也通过这样的方式让大家对学习这部分知识有一个抓手。
- 最后我们通过 jconsole 检测元空间溢出的整个过程，来学以致用，看看元空间到底在解决什么问题以及怎么测试。
