---
layout: post
category: itstack-demo-agent
title: 三、ByteBuddy操作监控方法字节码
tagline: by 付政委
tag: [javaagent,itstack-demo-agent]
excerpt: 在第二章中我们已经可以监控方法执行耗时，虽然它能完成我们一些基本需要，但是为了增强代码的扩展性，我们需要使用字节码操作工具ByteBuddy来帮助我们实现更完善的监控程序。
lock: need
---

# 基于JavaAgent的全链路监控三《ByteBuddy操作监控方法字节码》

## 案例简述
在第二章中我们已经可以监控方法执行耗时，虽然它能完成我们一些基本需要，但是为了增强代码的扩展性，我们需要使用字节码操作工具ByteBuddy来帮助我们实现更完善的监控程序。
>[Byte Buddy](http://bytebuddy.net/#/) is a code generation and manipulation library for creating and modifying Java classes during the runtime of a Java application and without the help of a compiler. Other than the code generation utilities that [ship with the Java Class Library](http://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Proxy.html), Byte Buddy allows the creation of arbitrary classes and is not limited to implementing interfaces for the creation of runtime proxies. Furthermore, Byte Buddy offers a convenient API for changing classes either manually, using a Java agent or during a build.

## 环境准备
1. IntelliJ IDEA Community Edition
2. jdk1.8.0_45 64位

## 配置信息（路径相关修改为自己的）
1. 配置位置：Run/Debug Configurations -> VM options
2. 配置内容：-javaagent:E:\itstack\GIT\itstack.org\itstack-demo-agent\itstack-demo-agent-03\target\itstack-demo-agent-03-1.0.0-SNAPSHOT.jar=testargs

## 代码示例
```java
itstack-demo-agent-03
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.demo.agent
    │   │          ├── MethodCostTime.java
    │   │	         └── MyAgent.java
    │	 └── resources
    │         └── META-INF
    │                └── MANIFEST.MF 	
    └── test
         └── java
               └── org.itstack.demo.test
                     └── ApiTest.java
```
>pom.xml (引入ByteBuddy并打入到Agent包中)

```xml
<properties>
    <!-- Build args -->
	<argline>-Xms512m -Xmx512m</argline>
	<skip_maven_deploy>false</skip_maven_deploy>
	<updateReleaseInfo>true</updateReleaseInfo>
	<project.build.sourceEncoding>utf-8</project.build.sourceEncoding>
	<maven.test.skip>true</maven.test.skip>
	<!-- 自定义MANIFEST.MF -->
	<maven.configuration.manifestFile>src/main/resources/META-INF/MANIFEST.MF</maven.configuration.manifestFile>
</properties>

<dependencies>
	<dependency>
		<groupId>javassist</groupId>
		<artifactId>javassist</artifactId>
		<version>3.12.1.GA</version>
		<type>jar</type>
	</dependency>
	<dependency>
		<groupId>net.bytebuddy</groupId>
		<artifactId>byte-buddy</artifactId>
		<version>1.8.20</version>
	</dependency>
	<dependency>
		<groupId>net.bytebuddy</groupId>
		<artifactId>byte-buddy-agent</artifactId>
		<version>1.8.20</version>
	</dependency>
</dependencies>

<!-- 将javassist包打包到Agent中 -->
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-shade-plugin</artifactId>
	<executions>
		<execution>
			<phase>package</phase>
			<goals>
				<goal>shade</goal>
			</goals>
		</execution>
	</executions>
	<configuration>
		<artifactSet>
			<includes>
				<include>javassist:javassist:jar:</include>
				<include>net.bytebuddy:byte-buddy:jar:</include>
                <include>net.bytebuddy:byte-buddy-agent:jar:</include>
			</includes>
		</artifactSet>
	</configuration>
</plugin>      
```
>MethodCostTime.java

```java
/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class MethodCostTime {

    @RuntimeType
    public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        try {
            // 原有函数执行
            return callable.call();
        } finally {
            System.out.println(method + " 方法耗时： " + (System.currentTimeMillis() - start) + "ms");
        }
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
        System.out.println("this is my agent：" + agentArgs);
        
        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
            return builder
                    .method(ElementMatchers.any()) // 拦截任意方法
                    .intercept(MethodDelegation.to(MethodCostTime.class)); // 委托
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {

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

        new AgentBuilder
                .Default()
                .type(ElementMatchers.nameStartsWith("org.itstack.demo.test")) // 指定需要拦截的类
                .transform(transformer)
                .with(listener)
                .installOn(inst);
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
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 *
 * VM options：
 * -javaagent:E:\itstack\GIT\itstack.org\itstack-demo-agent\itstack-demo-agent-03\target\itstack-demo-agent-03-1.0.0-SNAPSHOT.jar=testargs
 */
public class ApiTest {

    public static void main(String[] args) throws InterruptedException {
        ApiTest apiTest = new ApiTest();
        apiTest.echoHi();
    }

    private void echoHi() throws InterruptedException {
        System.out.println("hi agent");
        Thread.sleep((long) (Math.random() * 500));
    }

}
```
## 测试结果

```java
this is my agent：testargs
hi agent
private void org.itstack.demo.test.ApiTest.echoHi() throws java.lang.InterruptedException 方法耗时： 329ms
public static void org.itstack.demo.test.ApiTest.main(java.lang.String[]) throws java.lang.InterruptedException 方法耗时： 329ms

Process finished with exit code 0
```
------------

上一篇：[基于JavaAgent的全链路监控二《通过字节码增加监控执行耗时》](/itstack-demo-agent/2019/07/11/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%BA%8C-%E9%80%9A%E8%BF%87%E5%AD%97%E8%8A%82%E7%A0%81%E5%A2%9E%E5%8A%A0%E7%9B%91%E6%8E%A7%E6%89%A7%E8%A1%8C%E8%80%97%E6%97%B6.html)

下一篇：[基于JavaAgent的全链路监控四《JVM内存与GC信息》](/itstack-demo-agent/2019/07/13/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E5%9B%9B-JVM%E5%86%85%E5%AD%98%E4%B8%8EGC%E4%BF%A1%E6%81%AF.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**基于JavaAgent的全链路监控**」获取本文源码&更多原创专题案例！


