---
layout: post
category: itstack-demo-agent
title: 六、开发应用级监控
tagline: by 付政委
tag: [javaagent,itstack-demo-agent]
excerpt: 在我们的监控程序中，需要对各种模块进行监控；servlet、rpc、http、jdbc、redis、logic等，那么我们在设计监控程序时就需要对监控的程序进行模块化开发，可以在需要的时候进行组装配置即可，以方便我们监控程序的扩展和可控制性。这一章节我们把监控模块剥离，采用工厂模式进行调用｛目前是静态工厂在我们实际使用中可以把工厂做成动态配置化｝。
lock: need
---

# 基于JavaAgent的全链路监控六《开发应用级监控》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 案例简述

在我们的监控程序中，需要对各种模块进行监控；servlet、rpc、http、jdbc、redis、logic等，那么我们在设计监控程序时就需要对监控的程序进行模块化开发，可以在需要的时候进行组装配置即可，以方便我们监控程序的扩展和可控制性。这一章节我们把监控模块剥离，采用工厂模式进行调用｛目前是静态工厂在我们实际使用中可以把工厂做成动态配置化｝。

## 环境准备
1. IntelliJ IDEA Community Edition
2. jdk1.8.0_45 64位

## 配置信息（路径相关修改为自己的）
1. 配置位置：Run/Debug Configurations -> VM options
2. 配置内容：-javaagent:E:\itstack\GIT\itstack.org\itstack-demo-agent\itstack-demo-agent-06\target\itstack-demo-agent-06-1.0.0-SNAPSHOT.jar=testargs

## 代码示例
```java
itstack-demo-agent-06
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.demo.agent
    │   │       ├──	plugin
    │   │ 	    │   ├──	impl
    │   │ 	    │   │   ├── jvm
    │   │ 	    │   │   │   ├──	JvmAdvice.java
    │   │ 	    │   │   │   ├──	JvmPlugin.java
    │   │ 	    │   │   │   └──	JvmStack.java	
    │   │ 	    │   │   └──	link 	
    │   │ 	    │   │       ├── LinkAdvice.java
    │   │ 	    │   │       └──	LinkPlugin.java	
    │   │ 	    │   ├──	InterceptPoint.java
    │   │ 	    │   ├──	IPlugin.java
    │   │ 	    │   └──	PluginFactory.java
    │   │ 	    │ 	
    │   │       ├──	track
    │   │ 	    │   ├── Span.java	
    │   │ 	    │   ├── TrackContext.java	
    │   │ 	    │   └── TrackManager.java	
    │   │	    └── MyAgent.java
    │	└── resources
    │       └── META-INF
    │           └── MANIFEST.MF 	
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```
>JvmAdvice.java

```java
/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class JvmAdvice {

    @Advice.OnMethodExit()
    public static void exit() {
        JvmStack.printMemoryInfo();
        JvmStack.printGCInfo();
    }

}
```
>JvmPlugin.java

```java
/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class JvmPlugin implements IPlugin {

    @Override
    public String name() {
        return "jvm";
    }

    @Override
    public InterceptPoint[] buildInterceptPoint() {
        return new InterceptPoint[]{
                new InterceptPoint() {
                    @Override
                    public ElementMatcher<TypeDescription> buildTypesMatcher() {
                        return ElementMatchers.nameStartsWith("org.itstack.demo.test");
                    }

                    @Override
                    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
                        return ElementMatchers.isMethod()
                                .and(ElementMatchers.any())
                                .and(ElementMatchers.not(ElementMatchers.nameStartsWith("main")));
                    }
                }
        };
    }

    @Override
    public Class adviceClass() {
        return JvmAdvice.class;
    }
    
}
```
>LinkAdvice.java

```java
/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class LinkAdvice {

    @Advice.OnMethodEnter()
    public static void enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        Span currentSpan = TrackManager.getCurrentSpan();
        if (null == currentSpan) {
            String linkId = UUID.randomUUID().toString();
            TrackContext.setLinkId(linkId);
        }
        TrackManager.createEntrySpan();
    }

    @Advice.OnMethodExit()
    public static void exit(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        Span exitSpan = TrackManager.getExitSpan();
        if (null == exitSpan) return;
        System.out.println("链路追踪(MQ)：" + exitSpan.getLinkId() + " " + className + "." + methodName + " 耗时：" + (System.currentTimeMillis() - exitSpan.getEnterTime().getTime()) + "ms");
    }

}
```
>LinkPlugin.java

```java
/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class LinkPlugin implements IPlugin {

    @Override
    public String name() {
        return "link";
    }

    @Override
    public InterceptPoint[] buildInterceptPoint() {
        return new InterceptPoint[]{
                new InterceptPoint() {
                    @Override
                    public ElementMatcher<TypeDescription> buildTypesMatcher() {
                        return ElementMatchers.nameStartsWith("org.itstack.demo.test");
                    }

                    @Override
                    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
                        return ElementMatchers.isMethod()
                                .and(ElementMatchers.any())
                                .and(ElementMatchers.not(ElementMatchers.nameStartsWith("main")));
                    }
                }
        };
    }

    @Override
    public Class adviceClass() {
        return LinkAdvice.class;
    }

}
```
>InterceptPoint.java

```java
/**
 * 拦截点
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public interface InterceptPoint {

    //类匹配规则
    ElementMatcher<TypeDescription> buildTypesMatcher();

    //方法匹配规则
    ElementMatcher<MethodDescription> buildMethodsMatcher();

}
```
>IPlugin.java

```java
/**
 * 监控组件
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public interface IPlugin {

    //名称
    String name();

    //监控点
    InterceptPoint[] buildInterceptPoint();

    //拦截器类
    Class adviceClass();

}
```
>PluginFactory.java

```java
/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class PluginFactory {

    public static List<IPlugin> pluginGroup = new ArrayList<>();

    static {
        //链路监控
        pluginGroup.add(new LinkPlugin());
        //Jvm监控
        pluginGroup.add(new JvmPlugin());
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

    private static final ThreadLocal<Stack<Span>> track = new ThreadLocal<>();

    private static Span createSpan() {
        Stack<Span> stack = track.get();
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
            Span span = stack.peek();
            linkId = span.getLinkId();
            TrackContext.setLinkId(linkId);
        }
        return new Span(linkId);
    }

    public static Span createEntrySpan() {
        Span span = createSpan();
        Stack<Span> stack = track.get();
        stack.push(span);
        return span;
    }


    public static Span getExitSpan() {
        Stack<Span> stack = track.get();
        if (stack == null || stack.isEmpty()) {
            TrackContext.clear();
            return null;
        }
        return stack.pop();
    }

    public static Span getCurrentSpan() {
        Stack<Span> stack = track.get();
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        return stack.peek();
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

    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {

        System.out.println("基于javaagent链路追踪｛源码微信公众号：bugstack虫洞栈｝");
        System.out.println("==========================================================\r\n");
        AgentBuilder agentBuilder = new AgentBuilder.Default();

        List<IPlugin> pluginGroup = PluginFactory.pluginGroup;
        for (IPlugin plugin : pluginGroup) {
            InterceptPoint[] interceptPoints = plugin.buildInterceptPoint();
            for (InterceptPoint point : interceptPoints) {

                AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
                    builder = builder.visit(Advice.to(plugin.adviceClass()).on(point.buildMethodsMatcher()));
                    return builder;
                };
                agentBuilder = agentBuilder.type(point.buildTypesMatcher()).transform(transformer).asDecorator();
            }
        }

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
**
 * 链路追踪
 * VM options：
 * -javaagent:E:\itstack\GIT\itstack.org\itstack-demo-agent\itstack-demo-agent-06\target\itstack-demo-agent-06-1.0.0-SNAPSHOT.jar=testargs
 *
 * 按需打开需要测试的模块
 * 链路监控
 * pluginGroup.add(new LinkPlugin());
 * Jvm监控
 * pluginGroup.add(new JvmPlugin());
 *
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 *
 */
public class ApiTest {

    public static void main(String[] args) {

        //线程一
        new Thread(() -> new ApiTest().http_lt1("哪咤")).start();

        //线程二
        new Thread(() -> {
            new ApiTest().http_lt2("悟空");
        }).start();

    }

    public void http_lt1(String name) {
        try {
            Thread.sleep((long) (Math.random() * 500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("测试结果：hi1 " + name);
        http_lt2(name);
    }

    public void http_lt2(String name) {
        try {
            Thread.sleep((long) (Math.random() * 500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("测试结果：hi2 " + name);
        http_lt3(name);
    }

    public void http_lt3(String name) {
        try {
            Thread.sleep((long) (Math.random() * 500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("测试结果：hi3 " + name);
    }

}
```
## 测试结果
```其他语言
基于javaagent链路追踪｛源码微信公众号：bugstack虫洞栈｝
==========================================================

onTransformation：class org.itstack.demo.test.ApiTest
测试结果：hi2 悟空
测试结果：hi1 哪咤
测试结果：hi3 悟空
链路追踪(MQ)：608a1cbf-ef1f-4195-bdc7-c3729a114f8d org.itstack.demo.test.ApiTest.http_lt3 耗时：111ms
测试结果：hi2 哪咤

init: 192MB	 max: 2708MB	 used: 43MB	 committed: 184MB	 use rate: 23%
init: 2MB	 max: 0MB	 used: 13MB	 committed: 14MB	 use rate: 95%

name: PS Scavenge	 count:0	 took:0	 pool name:[PS Eden Space, PS Survivor Space]
name: PS MarkSweep	 count:0	 took:0	 pool name:[PS Eden Space, PS Survivor Space, PS Old Gen]
-------------------------------------------------------------------------------------------------
链路追踪(MQ)：608a1cbf-ef1f-4195-bdc7-c3729a114f8d org.itstack.demo.test.ApiTest.http_lt2 耗时：338ms

init: 192MB	 max: 2708MB	 used: 43MB	 committed: 184MB	 use rate: 23%
init: 2MB	 max: 0MB	 used: 13MB	 committed: 14MB	 use rate: 95%

name: PS Scavenge	 count:0	 took:0	 pool name:[PS Eden Space, PS Survivor Space]
name: PS MarkSweep	 count:0	 took:0	 pool name:[PS Eden Space, PS Survivor Space, PS Old Gen]
-------------------------------------------------------------------------------------------------
测试结果：hi3 哪咤
链路追踪(MQ)：2f28ed75-650a-4f0f-bd69-fe0709a8985e org.itstack.demo.test.ApiTest.http_lt3 耗时：221ms

init: 192MB	 max: 2708MB	 used: 43MB	 committed: 184MB	 use rate: 23%
init: 2MB	 max: 0MB	 used: 13MB	 committed: 14MB	 use rate: 95%

name: PS Scavenge	 count:0	 took:0	 pool name:[PS Eden Space, PS Survivor Space]
name: PS MarkSweep	 count:0	 took:0	 pool name:[PS Eden Space, PS Survivor Space, PS Old Gen]
-------------------------------------------------------------------------------------------------
链路追踪(MQ)：2f28ed75-650a-4f0f-bd69-fe0709a8985e org.itstack.demo.test.ApiTest.http_lt2 耗时：316ms

init: 192MB	 max: 2708MB	 used: 43MB	 committed: 184MB	 use rate: 23%
init: 2MB	 max: 0MB	 used: 13MB	 committed: 14MB	 use rate: 95%

name: PS Scavenge	 count:0	 took:0	 pool name:[PS Eden Space, PS Survivor Space]
name: PS MarkSweep	 count:0	 took:0	 pool name:[PS Eden Space, PS Survivor Space, PS Old Gen]
-------------------------------------------------------------------------------------------------
链路追踪(MQ)：2f28ed75-650a-4f0f-bd69-fe0709a8985e org.itstack.demo.test.ApiTest.http_lt1 耗时：547ms

init: 192MB	 max: 2708MB	 used: 43MB	 committed: 184MB	 use rate: 23%
init: 2MB	 max: 0MB	 used: 13MB	 committed: 14MB	 use rate: 95%

name: PS Scavenge	 count:0	 took:0	 pool name:[PS Eden Space, PS Survivor Space]
name: PS MarkSweep	 count:0	 took:0	 pool name:[PS Eden Space, PS Survivor Space, PS Old Gen]
-------------------------------------------------------------------------------------------------

Process finished with exit code 0

```
------------

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**基于JavaAgent的全链路监控**」获取本文源码&更多原创专题案例！
