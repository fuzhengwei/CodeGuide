---
layout: post
category: itstack-demo-agent
title: 四、JVM内存与GC信息
tagline: by 付政委
tag: [javaagent,itstack-demo-agent]
excerpt: 除了监控java方法的执行耗时，我们还需要获取应用实例的jvm内存与gc信息，以实时把控我们的服务器性能是否在安全范围。监控jvm内存与gc信息是非常重要的，尤其是在大促以及微博火热爆点的时候，我们需要根据监控信息进行扩容，以保证系统稳定。
lock: need
---

# 基于JavaAgent的全链路监控四《JVM内存与GC信息》

## 案例简述
除了监控java方法的执行耗时，我们还需要获取应用实例的jvm内存与gc信息，以实时把控我们的服务器性能是否在安全范围。监控jvm内存与gc信息是非常重要的，尤其是在大促以及微博火热爆点的时候，我们需要根据监控信息进行扩容，以保证系统稳定。

## 环境准备
1. IntelliJ IDEA Community Edition
2. jdk1.8.0_45 64位

## 配置信息（路径相关修改为自己的）
1. 配置位置：Run/Debug Configurations -> VM options
2. 配置内容：-javaagent:E:\itstack\GIT\itstack.org\itstack-demo-agent\itstack-demo-agent-04\target\itstack-demo-agent-04-1.0.0-SNAPSHOT.jar=testargs

## 代码示例
```java
itstack-demo-agent-04
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.demo.agent
    │   │       ├── JvmStack.java
    │   │	    └── MyAgent.java
    │	└── resources
    │       └── META-INF
    │           └── MANIFEST.MF 	
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```
>JvmStack.java

```java
/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
class JvmStack {

    private static final long MB = 1048576L;

    static void printMemoryInfo() {
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        MemoryUsage headMemory = memory.getHeapMemoryUsage();

        String info = String.format("\ninit: %s\t max: %s\t used: %s\t committed: %s\t use rate: %s\n",
                headMemory.getInit() / MB + "MB",
                headMemory.getMax() / MB + "MB", headMemory.getUsed() / MB + "MB",
                headMemory.getCommitted() / MB + "MB",
                headMemory.getUsed() * 100 / headMemory.getCommitted() + "%"

        );

        System.out.print(info);

        MemoryUsage nonheadMemory = memory.getNonHeapMemoryUsage();

        info = String.format("init: %s\t max: %s\t used: %s\t committed: %s\t use rate: %s\n",
                nonheadMemory.getInit() / MB + "MB",
                nonheadMemory.getMax() / MB + "MB", nonheadMemory.getUsed() / MB + "MB",
                nonheadMemory.getCommitted() / MB + "MB",
                nonheadMemory.getUsed() * 100 / nonheadMemory.getCommitted() + "%"

        );
        System.out.println(info);

    }

    static void printGCInfo() {
        List<GarbageCollectorMXBean> garbages = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean garbage : garbages) {
            String info = String.format("name: %s\t count:%s\t took:%s\t pool name:%s",
                    garbage.getName(),
                    garbage.getCollectionCount(),
                    garbage.getCollectionTime(),
                    Arrays.deepToString(garbage.getMemoryPoolNames()));
            System.out.println(info);
        }
    }

}
```
>MyAgent.java

```java
/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class MyAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("this is my agent：" + agentArgs);

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            public void run() {
                JvmStack.printMemoryInfo();
                JvmStack.printGCInfo();
                System.out.println("===================================================================================================");
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);
    }

}
```
>MANIFEST.MF

```其他语言
Manifest-Version: 1.0
Premain-Class: org.itstack.demo.agent.MyAgent
Can-Redefine-Classes: true

```
>ApiTest.java

```java
/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 * -javaagent:E:\itstack\GIT\itstack.org\itstack-demo-agent\itstack-demo-agent-04\target\itstack-demo-agent-04-1.0.0-SNAPSHOT.jar=testargs
 */
public class ApiTest {

    public static void main(String[] args) {
        while (true) {
            List<Object> list = new LinkedList<>();
            list.add("嗨！JavaAgent");
            list.add("嗨！JavaAgent");
            list.add("嗨！JavaAgent");
        }
    }

}
```

## 测试结果

```其他语言
this is my agent：testargs

init: 192MB	 max: 2708MB	 used: 5MB	 committed: 184MB	 use rate: 3%
init: 2MB	 max: 0MB	 used: 5MB	 committed: 7MB	 use rate: 75%

name: PS Scavenge	 count:1	 took:2	 pool name:[PS Eden Space, PS Survivor Space]
name: PS MarkSweep	 count:0	 took:0	 pool name:[PS Eden Space, PS Survivor Space, PS Old Gen]
===================================================================================================

init: 192MB	 max: 2708MB	 used: 249MB	 committed: 624MB	 use rate: 39%
init: 2MB	 max: 0MB	 used: 6MB	 committed: 7MB	 use rate: 78%

name: PS Scavenge	 count:32	 took:98	 pool name:[PS Eden Space, PS Survivor Space]
name: PS MarkSweep	 count:0	 took:0	 pool name:[PS Eden Space, PS Survivor Space, PS Old Gen]
===================================================================================================

Process finished with exit code -1
```
------------

上一篇：[基于JavaAgent的全链路监控三《ByteBuddy操作监控方法字节码》](/itstack-demo-agent/2019/07/12/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%B8%89-ByteBuddy%E6%93%8D%E4%BD%9C%E7%9B%91%E6%8E%A7%E6%96%B9%E6%B3%95%E5%AD%97%E8%8A%82%E7%A0%81.html)

下一篇：[基于JavaAgent的全链路监控五《ThreadLocal链路追踪》](/itstack-demo-agent/2019/07/14/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%BA%94-ThreadLocal%E9%93%BE%E8%B7%AF%E8%BF%BD%E8%B8%AA.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**基于JavaAgent的全链路监控**」获取本文源码&更多原创专题案例！


