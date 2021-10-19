---
layout: post
category: itstack-demo-agent
title: 三、使用Javassist在运行时重新加载类「替换原方法输出不一样的结果」
tagline: by 付政委
tag: [itstack-demo-bytecode]
excerpt: HotSwapper动态热加载替换方法字节码，安定谢飞机家庭和睦。德莱联盟，王牌工程师，申请出栈！
lock: need
---

# 字节码编程，Javassist篇三《使用Javassist在运行时重新加载类「替换原方法输出不一样的结果」》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！


## 一、前言

通过前面两篇 `javassist` 的基本内容，大体介绍了；类池(*ClassPool*)、类(*CtClass*)、属性(*CtField*)、方法(*CtMethod*)，的使用方式，并通过创建不同类型的入参出参方法，基本可以掌握如何使用这样的代码结构进行字节码编程。

**那么**，今天我们尝试使用 `javassist` 去修改一个正在执行中的类里面的方法内容。*也就是在运行时重新加载类信息*

可能在你平时的 *CRUD* 开发中并没有想到过这样的 *烧操作*，但它却有很多的应用场景在使用，例如；
1. 热部署常用在生产环境中，主要由于这样的系统不能频繁启停且启动耗时较长的应用。
2. 另外一些组件化风控模型包，给外部使用。当模型包进行升级时并不需要外部重新部署，甚至不需要让你知道升级了。
3. 再者会用于开发、调试中，可以非常有效的提升编码效率，解放码农的**右手**和*左手*。

**人的大脑**很难创造未知的事物，*所以需要学习。请多看小傅哥的码文，少搞CRUD*

关于字节编程中所有涉及的代码，都可以通过关注`公众号`：**bugstack虫洞栈**，回复：*源码*，进行获取。

## 二、开发环境

1. JDK 1.8.0
2. jdk1.8.0_161\lib\tools.jar - 需要使用到 `jdi` 包
3. javassist 3.12.1.GA

## 三、案例目标

为了让案例目标更具`色彩`，我们模拟一个**谢飞机老婆**，通过系统查询自己男朋友`前女友数量`的 **危机** 方法，需要紧急处理的过程。

为了保障家庭的和谐化解危机，我们通过动态重新加载类，将谢飞机前女友数量修改为`0`并返回。依次安定家庭和谐。~~最终谢飞机会给我钱，当做报酬~~

![德莱联盟，王牌工程师，申请出栈](https://bugstack.cn/assets/images/2020/itstack-demo-bytecode-1-03-1.png)

**让谢飞机很慌的方法**

```java
public class ApiTest {

    public String queryGirlfriendCount(String boyfriendName) {
        return boyfriendName + "的前女友数量：" + (new Random().nextInt(10) + 1) + " 个";
    }

}
```

**可预见的结果**；

```java
你到底几个前女友！！！
谢飞机的前女友数量：3 个
谢飞机的前女友数量：5 个
谢飞机的前女友数量：8 个
```

## 四、技术实现

### 1. HotSwapper 操作类热加载

**德莱联盟，王牌工程师，申请出`栈`**

![德莱联盟王牌工程师](https://bugstack.cn/assets/images/2020/itstack-demo-bytecode-1-03-2.jpg)

```java
/**
 * 公众号：bugstack虫洞栈
 * 博客栈：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 * 本专栏是小傅哥多年从事一线互联网Java开发的学习历程技术汇总，旨在为大家提供一个清晰详细的学习教程，侧重点更倾向编写Java核心内容。如果能为您提供帮助，请给予支持(关注、点赞、分享)！
 */
public class GenerateClazzMethod {

    public static void main(String[] args) throws Exception {

        ApiTest apiTest = new ApiTest();
        System.out.println("你到底几个前女友！！！");

		      // 模拟谢飞机老婆一顿查询
        new Thread(() -> {
            while (true){
                System.out.println(apiTest.queryGirlfriendCount("谢飞机"));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        
        // 监听 8000 端口,在启动参数里设置
        // java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000
        HotSwapper hs = new HotSwapper(8000);

        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get(ApiTest.class.getName());

        // 获取方法
        CtMethod ctMethod = ctClass.getDeclaredMethod("queryGirlfriendCount");
        // 重写方法
        ctMethod.setBody("{ return $1 + \"的前女友数量：\" + (0L) + \" 个\"; }");

        // 加载新的类
        System.out.println(":: 执行HotSwapper热插拔，修改谢飞机前女友数量为0个！");
        hs.reload(ApiTest.class.getName(), ctClass.toBytecode());

    }

}
```

### 2. 知识点讲解

1. 多线程模拟循环调用，这个方法会一直执行查询。在后续修改类之后输出的结果信息会有不同。
2. `javassist.tools.HotSwapper`，是 `javassist` 的包中提供的热加载替换类操作。在执行时需要启用 JPDA（Java平台调试器体系结构）。
3. `ctMethod.setBody`，重写方法的内容在上面两个章节已经很清楚的描述了。*$1* 是获取方法中的第一个入参，大括号`{}`里是具体执行替换的方法体。
4. 最后使用 `hs.reload` 执行热加载替换操作，这里的 `ctClass.toBytecode()` 获取的是处理后类的字节码。


## 五、测试结果

### 1. 引入tools.jar

![Open Module Settings，引入tools.jar](https://bugstack.cn/assets/images/2020/itstack-demo-bytecode-1-03-3.png)

### 2. 配置-agentlib

```java
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000
```

![VM options，配置-agentlib](https://bugstack.cn/assets/images/2020/itstack-demo-bytecode-1-03-4.png)

### 3. 执行测试

```java
Listening for transport dt_socket at address: 8000
你到底几个前女友！！！
谢飞机的前女友数量：3 个
谢飞机的前女友数量：5 个
谢飞机的前女友数量：8 个
:: 执行HotSwapper热插拔，修改谢飞机前女友数量为0个！
谢飞机的前女友数量：4 个
谢飞机的前女友数量：5 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
谢飞机的前女友数量：0 个
...

Process finished with exit code -1
```

**当**看到前女友数量为 *0* 时，谢飞机露出了羞涩的微笑，并兑现了承诺，将*4毛钱*给了王牌工程师`小傅哥`。

![来自谢飞机的`4毛钱`收入](https://bugstack.cn/assets/images/2020/itstack-demo-bytecode-1-03-5.png)

### 4. 效果演示

![热加载救火，成功拿到4毛钱](https://bugstack.cn/assets/images/2020/itstack-demo-bytecode-1-03-6.gif)

## 六、总结

1. 没得办法，即使再好的技术不加点段子也没人看。只能坑我兄弟飞机了！**德莱联盟，王牌工程师，申请出`栈`**
2. 关于热加载修改类的操作，在实际场景中还是蛮多的，但一般都是比较苛刻的场景诉求。在平时开发中还是比较少遇到的，并且CRUD开发不会遇到。
3. `Javassist` 对 `ASM` 这样的字节码操作封装起来提供的`API`确实很好操作，在一些场景下也不需要考虑 `JVM` 中局部变量和操作数栈。但如果需要更高的性能，可以考虑使用 `ASM`。
