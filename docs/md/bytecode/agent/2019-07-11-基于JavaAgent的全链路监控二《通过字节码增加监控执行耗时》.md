---
layout: post
category: itstack-demo-agent
title: 二、通过字节码增加监控执行耗时
tagline: by 付政委
tag: [javaagent,itstack-demo-agent]
excerpt: 通过上一章节的介绍《嗨！JavaAgent》，我们已经知道通过配置-javaagent:文件.jar后，在java程序启动时候会执行premain方法。接下来我们使用javassist字节码增强的方式，来监控方法程序的执行耗时。
lock: need
---

# 基于JavaAgent的全链路监控二《通过字节码增加监控执行耗时》

## 案例简述
通过上一章节的介绍《嗨！JavaAgent》，我们已经知道通过配置-javaagent:文件.jar后，在java程序启动时候会执行premain方法。接下来我们使用javassist字节码增强的方式，来监控方法程序的执行耗时。

>Javassist是一个开源的分析、编辑和创建Java字节码的类库。是由东京工业大学的数学和计算机科学系的 Shigeru Chiba （千叶 滋）所创建的。它已加入了开放源代码JBoss应用服务器项目，通过使用Javassist对字节码操作为JBoss实现动态"AOP"框架。

>关于java字节码的处理，目前有很多工具，如bcel，asm。不过这些都需要直接跟虚拟机指令打交道。如果你不想了解虚拟机指令，可以采用javassist。javassist是jboss的一个子项目，其主要的优点，在于简单，而且快速。直接使用java编码的形式，而不需要了解虚拟机指令，就能动态改变类的结构，或者动态生成类。

## 环境准备
1. IntelliJ IDEA Community Edition
2. jdk1.8.0_45 64位

## 配置信息（路径相关修改为自己的）
1. 配置位置：Run/Debug Configurations -> VM options
2. 配置内容：-javaagent:E:\itstack\GIT\itstack.org\itstack-demo-agent\itstack-demo-agent-02\target\itstack-demo-agent-02-1.0.0-SNAPSHOT.jar=testargs

## 代码示例
```java
itstack-demo-agent-02
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.demo.agent
    │   │       ├── MyAgent.java
    │   │	    └── MyMonitorTransformer.java
    │	└── resources
    │       └── META-INF
    │           └── MANIFEST.MF 	
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```

>pom.xml (引入javassist并打入到Agent包中)

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
			</includes>
		</artifactSet>
	</configuration>
</plugin>            
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
        System.out.println("this is my agent：" + agentArgs);
        MyMonitorTransformer monitor = new MyMonitorTransformer();
        inst.addTransformer(monitor);
    }

    //如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
    public static void premain(String agentArgs) {
    }

}
```

>MyMonitorTransformer.java

```java
/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class MyMonitorTransformer implements ClassFileTransformer {

    private static final Set<String> classNameSet = new HashSet<>();

    static {
        classNameSet.add("org.itstack.demo.test.ApiTest");
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        try {
            String currentClassName = className.replaceAll("/", ".");
            if (!classNameSet.contains(currentClassName)) { // 提升classNameSet中含有的类
                return null;
            }
            System.out.println("transform: [" + currentClassName + "]");

            CtClass ctClass = ClassPool.getDefault().get(currentClassName);
            CtBehavior[] methods = ctClass.getDeclaredBehaviors();
            for (CtBehavior method : methods) {
                enhanceMethod(method);
            }
            return ctClass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;

    }


    private void enhanceMethod(CtBehavior method) throws Exception {
        if (method.isEmpty()) {
            return;
        }
        String methodName = method.getName();
        if ("main".equalsIgnoreCase(methodName)) {
            return;
        }

        final StringBuilder source = new StringBuilder();
        // 前置增强: 打入时间戳
        // 保留原有的代码处理逻辑
        source.append("{")
                .append("long start = System.nanoTime();\n") //前置增强: 打入时间戳
                .append("$_ = $proceed($$);\n")              //调用原有代码，类似于method();($$)表示所有的参数
                .append("System.out.print(\"method:[")
                .append(methodName).append("]\");").append("\n")
                .append("System.out.println(\" cost:[\" +(System.nanoTime() - start)+ \"ns]\");") // 后置增强，计算输出方法执行耗时
                .append("}");

        ExprEditor editor = new ExprEditor() {
            @Override
            public void edit(MethodCall methodCall) throws CannotCompileException {
                methodCall.replace(source.toString());
            }
        };
        method.instrument(editor);
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
 * -javaagent:E:\itstack\GIT\itstack.org\itstack-demo-agent\itstack-demo-agent-02\target\itstack-demo-agent-02-1.0.0-SNAPSHOT.jar=testargs
 *
 */
public class ApiTest {

    public static void main(String[] args) {
        ApiTest apiTest = new ApiTest();
        apiTest.echoHi();
    }

    private void echoHi(){
        System.out.println("hi agent");
    }

}
```
## 测试结果
```java
this is my agent：testargs
transform: [org.itstack.demo.test.ApiTest]
hi agent
method:[echoHi] cost:[294845ns]
```
------------

上一篇：[基于JavaAgent的全链路监控一《嗨！JavaAgent》](/itstack-demo-agent/2019/07/10/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%B8%80-%E5%97%A8-JavaAgent.html)

下一篇：[基于JavaAgent的全链路监控三《ByteBuddy操作监控方法字节码》](/itstack-demo-agent/2019/07/12/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%B8%89-ByteBuddy%E6%93%8D%E4%BD%9C%E7%9B%91%E6%8E%A7%E6%96%B9%E6%B3%95%E5%AD%97%E8%8A%82%E7%A0%81.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**基于JavaAgent的全链路监控**」获取本文源码&更多原创专题案例！