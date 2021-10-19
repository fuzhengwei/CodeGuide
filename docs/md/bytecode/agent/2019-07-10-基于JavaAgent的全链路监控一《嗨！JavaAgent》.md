---
layout: post
category: itstack-demo-agent
title: 一、嗨！JavaAgent
tagline: by 付政委
tag: [javaagent,itstack-demo-agent]
excerpt: 全链路监控又名分布式监控系统全链路追踪，目前市面的全链路监控系统基本都是参考Google的[Dapper](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724660&idx=1&sn=0f33d3386c7652bf536cb071e9f79921&chksm=8f6138d6b816b1c0d92fb75257da4fc8ddefb7ec53dfcad98dffec87740df455cc75aa7b4a5c&token=144816615&lang=zh_CN#rd)（大规模分布式系统的跟踪系统）来做的。例如；蚂蚁金服分布式链路跟踪组件SOFATracer、Gokit微服务-服务链路追踪 、Pinpoint、Prometheus(普罗米修斯)等等。
lock: need
---

# 基于JavaAgent的全链路监控一《嗨！JavaAgent》

## 前言介绍
>全链路监控又名分布式监控系统全链路追踪，目前市面的全链路监控系统基本都是参考Google的[Dapper](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724660&idx=1&sn=0f33d3386c7652bf536cb071e9f79921&chksm=8f6138d6b816b1c0d92fb75257da4fc8ddefb7ec53dfcad98dffec87740df455cc75aa7b4a5c&token=144816615&lang=zh_CN#rd)（大规模分布式系统的跟踪系统）来做的。例如；蚂蚁金服分布式链路跟踪组件SOFATracer、Gokit微服务-服务链路追踪 、Pinpoint、Prometheus(普罗米修斯)等等。

## 案例简述
JavaAgent是在JDK5之后提供的新特性，也可以叫java代理。开发者通过这种机制(Instrumentation)可以在加载class文件之前修改方法的字节码(此时字节码尚未加入JVM)，动态更改类方法实现AOP，提供监控服务如；方法调用时长、可用率、内存等。本章节初步怎么让java代码执行时可以进入我们的agent方法。

## 环境准备
1. IntelliJ IDEA Community Edition
2. jdk1.8.0_45 64位

## 配置信息 （路径相关修改为自己的）
1. 配置位置：Run/Debug Configurations -> VM options
2. 配置内容：-javaagent:E:\itstack\GIT\itstack.org\itstack-demo-agent\itstack-demo-agent-01\target\itstack-demo-agent-01-1.0.0-SNAPSHOT.jar=testargs

## 代码示例
```java
itstack-demo-agent-01
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.demo.agent
    │   │       └── MyAgent.java
    │	└── resources
    │       └── META-INF
    │           └── MANIFEST.MF 	
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```
>pom.xml

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
```

>MyAgent.java

```java
/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 */
public class MyAgent {

    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("嗨！JavaAgent " + agentArgs);
    }

    //如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
    public static void premain(String agentArgs) {
    }

}
```
>MANIFEST.MF

```
Manifest-Version: 1.0
Premain-Class: org.itstack.demo.agent.MyAgent
Can-Redefine-Classes: true

```
>ApiTest.java

```java
/**
 *
 * http://bigbully.github.io/Dapper-translation/
 *
 * 配置监控
 * VM options：
 * -javaagent:E:\itstack\GIT\itstack.org\itstack-demo-agent\itstack-demo-agent-01\target\itstack-demo-agent-01-1.0.0-SNAPSHOT.jar=testargs
 *
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class ApiTest {

    public static void main(String[] args) {
        System.out.println("hi itstack-demo-agent-01");
    }

}
```

## 测试结果

```java
this is my agent：testargs
嗨！JavaAgent

Process finished with exit code 0
```

------------

下一篇：[基于JavaAgent的全链路监控二《通过字节码增加监控执行耗时》](/itstack-demo-agent/2019/07/11/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%BA%8C-%E9%80%9A%E8%BF%87%E5%AD%97%E8%8A%82%E7%A0%81%E5%A2%9E%E5%8A%A0%E7%9B%91%E6%8E%A7%E6%89%A7%E8%A1%8C%E8%80%97%E6%97%B6.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**基于JavaAgent的全链路监控**」获取本文源码&更多原创专题案例！
