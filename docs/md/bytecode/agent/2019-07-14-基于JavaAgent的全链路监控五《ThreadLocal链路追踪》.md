---
layout: post
category: itstack-demo-agent
title: 五、ThreadLocal链路追踪
tagline: by 付政委
tag: [javaagent,itstack-demo-agent]
excerpt: Google开源的Dapper链路追踪组件，并在2010年发表了论文《Dapper, a Large-Scale Distributed Systems Tracing Infrastructure》，这篇文章是业内实现链路追踪的标杆和理论基础，具有非常大的参考价值。目前，链路追踪组件有Google的Dapper，Twitter 的Zipkin，以及阿里的Eagleeye （鹰眼）等，它们都是非常优秀的链路追踪开源组件。本文主要讲述如何在Spring Cloud Sleuth中集成Zipkin。在Spring Cloud Sleuth中集成Zipkin非常的简单，只需要引入相应的依赖和做相关的配置即可。
lock: need
---

# 基于JavaAgent的全链路监控五《ThreadLocal链路追踪》

## 案例简述
>Google开源的Dapper链路追踪组件，并在2010年发表了论文《Dapper, a Large-Scale Distributed Systems Tracing Infrastructure》，这篇文章是业内实现链路追踪的标杆和理论基础，具有非常大的参考价值。目前，链路追踪组件有Google的Dapper，Twitter 的Zipkin，以及阿里的Eagleeye （鹰眼）等，它们都是非常优秀的链路追踪开源组件。本文主要讲述如何在Spring Cloud Sleuth中集成Zipkin。在Spring Cloud Sleuth中集成Zipkin非常的简单，只需要引入相应的依赖和做相关的配置即可。

![链路追踪Dapper](https://fuzhengwei.github.io/assets/images/pic-content/2019/08/17387004-c9295b1ffd21eb27.png)
当业务程序代码在线上运行时，实例A、实例B、实例C，他们直接可能从上到下依次调用，为了能很好的监控程序的调用链路，我们需要对调用链路进行追踪监控。实例的外部可能是通过RPC、HTTP、SOCKET、WEBSERVICE等方式进行调用，内部是方法逻辑依次执行。外部例如http可以通过在头部写入追踪ID进行监控，内部使用threadlocal进行保存上下文关系。｛ThreadLocal变量特殊的地方在于：对变量值的任何操作实际都是对这个变量在线程中的一份copy进行操作，不会影响另外一个线程中同一个ThreadLocal变量的值。｝

## 环境准备
1. IntelliJ IDEA Community Edition
2. jdk1.8.0_45 64位

## 配置信息（路径相关修改为自己的）
1. 配置位置：Run/Debug Configurations -> VM options
2. 配置内容：-javaagent:E:\itstack\GIT\itstack.org\itstack-demo-agent\itstack-demo-agent-05\target\itstack-demo-agent-05-1.0.0-SNAPSHOT.jar=testargs

## 代码示例
```java
itstack-demo-agent-05
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.demo.agent
    │   │         ├──track
    │   │ 	    │    ├── TrackContext.java	
    │   │ 	    │    └── TrackManager.java	
    │   │         ├── MyAdvice.java
    │   │	        └── MyAgent.java
    │	 └── resources
    │       └── META-INF
    │           └── MANIFEST.MF 	
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```
>TrackContext.java

```java
/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class TrackContext {

    private static final ThreadLocal<String> trackLocal = new ThreadLocal<String>();

    public static void clear(){
        trackLocal.remove();
    }

    public static String getLinkId(){
        return trackLocal.get();
    }

    public static void setLinkId(String linkId){
        trackLocal.set(linkId);
    }

}
```
>TrackManager.java

```java
/**
 * 追踪管控
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class TrackManager {

    private static final ThreadLocal<Stack<String>> track = new ThreadLocal<Stack<String>>();

    private static String createSpan() {
        Stack<String> stack = track.get();
        if (stack == null) {
            stack = new Stack<>();
            track.set(stack);
        }
        String linkId;
        if (stack.isEmpty()) {
            linkId = TrackContext.getLinkId();
            if (linkId == null) {
                linkId = "nvl";
                TrackContext.setLinkId(linkId);
            }
        } else {
            linkId = stack.peek();
            TrackContext.setLinkId(linkId);
        }
        return linkId;
    }

    public static String createEntrySpan() {
        String span = createSpan();
        Stack<String> stack = track.get();
        stack.push(span);
        return span;
    }


    public static String getExitSpan() {
        Stack<String> stack = track.get();
        if (stack == null || stack.isEmpty()) {
            TrackContext.clear();
            return null;
        }
        return stack.pop();
    }

    public static String getCurrentSpan() {
        Stack<String> stack = track.get();
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }


}
```
>MyAdvice.java

```java
/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class MyAdvice {

    @Advice.OnMethodEnter()
    public static void enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        String linkId = TrackManager.getCurrentSpan();
        if (null == linkId) {
            linkId = UUID.randomUUID().toString();
            TrackContext.setLinkId(linkId);
        }
        String entrySpan = TrackManager.createEntrySpan();
        System.out.println("链路追踪：" + entrySpan + " " + className + "." + methodName);

    }

    @Advice.OnMethodExit()
    public static void exit(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        TrackManager.getExitSpan();
    }

}
```
>MyAgent.java

```java
/**
 * javaagent
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class MyAgent {

    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("基于javaagent链路追踪");

        AgentBuilder agentBuilder = new AgentBuilder.Default();


        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
            builder = builder.visit(
                    Advice.to(MyAdvice.class)
                            .on(ElementMatchers.isMethod()
                                    .and(ElementMatchers.any()).and(ElementMatchers.not(ElementMatchers.nameStartsWith("main")))));
            return builder;
        };

        agentBuilder = agentBuilder.type(ElementMatchers.nameStartsWith("org.itstack.demo.test")).transform(transformer).asDecorator();

        //监听
        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {
                System.out.println("onTransformation：" + typeDescription);
            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {

            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

        };

        agentBuilder.with(listener).installOn(inst);

    }

    //如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
    public static void premain(String agentArgs) {
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
 * 线程内方法追踪
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 * VM options：
 * -javaagent:E:\itstack\GIT\itstack.org\itstack-demo-agent\itstack-demo-agent-05\target\itstack-demo-agent-05-1.0.0-SNAPSHOT.jar=testargs
 */
public class ApiTest {

    public static void main(String[] args) {

        //线程一
        new Thread(() -> new ApiTest().http_lt1()).start();

        //线程二
        new Thread(() -> {
            new ApiTest().http_lt1();
        }).start();
    }


    public void http_lt1() {
        System.out.println("测试结果：hi1");
        http_lt2();
    }

    public void http_lt2() {
        System.out.println("测试结果：hi2");
        http_lt3();
    }

    public void http_lt3() {
        System.out.println("测试结果：hi3");
    }


}
```
## 测试结果
```java
基于javaagent链路追踪
onTransformation：class org.itstack.demo.test.ApiTest
链路追踪：7dfd98e8-c474-461c-87b9-1da3bf6072c2 org.itstack.demo.test.ApiTest.http_lt1
测试结果：hi1
链路追踪：7dfd98e8-c474-461c-87b9-1da3bf6072c2 org.itstack.demo.test.ApiTest.http_lt2
测试结果：hi2
链路追踪：7dfd98e8-c474-461c-87b9-1da3bf6072c2 org.itstack.demo.test.ApiTest.http_lt3
测试结果：hi3
链路追踪：69cdf9d3-1f42-4cf6-8d80-5362dd7ea140 org.itstack.demo.test.ApiTest.http_lt1
测试结果：hi1
链路追踪：69cdf9d3-1f42-4cf6-8d80-5362dd7ea140 org.itstack.demo.test.ApiTest.http_lt2
测试结果：hi2
链路追踪：69cdf9d3-1f42-4cf6-8d80-5362dd7ea140 org.itstack.demo.test.ApiTest.http_lt3
测试结果：hi3

Process finished with exit code 0
```
------------

上一篇：[基于JavaAgent的全链路监控四《JVM内存与GC信息》](/itstack-demo-agent/2019/07/13/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E5%9B%9B-JVM%E5%86%85%E5%AD%98%E4%B8%8EGC%E4%BF%A1%E6%81%AF.html)

下一篇：[基于JavaAgent的全链路监控六《开发应用级监控》](/itstack-demo-agent/2019/07/15/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E5%85%AD-%E5%BC%80%E5%8F%91%E5%BA%94%E7%94%A8%E7%BA%A7%E7%9B%91%E6%8E%A7.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**基于JavaAgent的全链路监控**」获取本文源码&更多原创专题案例！
