---
layout: post
category: itstack-demo-agent
title: 三、使用委托实现抽象类方法并注入自定义注解信息
tagline: by 付政委
tag: [itstack-demo-bytecode]
excerpt: 通过委托(MethodDelegation)方式实现抽象类方法并加入自定义注解信息到类和方法上。这部分学习中需要注意几个知识点的使用，包括；`委托方法使用`、`复杂类型的泛型创建`、`类和方法自定义注解的添加`以及`写入字节码信息到文件中`。
lock: need
---

# 字节码编程，Byte-buddy篇三《使用委托实现抽象类方法并注入自定义注解信息》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、前言

截至到本章节关于字节码框架 `Byte-buddy` 的大部分常用 API 的使用已经通过案例介绍比较全面了，接下来介绍关于如何去实现一个抽象类以及创建出相应注解(包括类的注解和方法的注解)的知识点。而注解的这部分内容在一些监控或者拦截处理的场景下还是比较常用的，所以在这章节我们会通过一个例子来创建出含有自定义注解的类和方法。

如果你已经阅读了之前的系列文章，这部分学习的内容并不会有太多的陌生，主要是关于`委托(MethodDelegation)`方法的使用以及补充自定义注解。

**那么**，接下来我们就使用委托和注解方式来创建这样的案例进行学习。

## 二、开发环境

1. JDK 1.8.0
2. byte-buddy 1.10.9
3. byte-buddy-agent 1.10.9
4. 本章涉及源码在：`itstack-demo-bytecode-2-03`，可以关注**公众号**：[`bugstack虫洞栈`](https://bugstack.cn/assets/images/qrcode.png)，回复源码下载获取。`你会获得一个下载链接列表，打开后里面的第17个「因为我有好多开源代码」`，记得给个`Star`！

## 三、案例目标

在这里我们定义了一个抽象并且含有泛型的接口类，如下；

```java 
public abstract class Repository<T> {

    public abstract T queryData(int id);

}
```

那么接下来的案例会使用到委托的方式进行实现抽象类方法并加入自定义注解，也就相当于我们使用代码进行编程实现的效果。

```java 
@RpcGatewayClazz( clazzDesc = "查询数据信息", alias = "dataApi", timeOut = 350L )
public class UserRepository extends Repository<String> {      

    @RpcGatewayMethod( methodName = "queryData", methodDesc = "查询数据" )
    public String queryData(int var1) {
        // ...
    }

}
```

- 这里就是最终效果，我们模拟是一种网关接口的实现和定义注解暴漏接口信息（如果你是在互联网中做开发，类似这样的需求还是蛮多的，接口统一走网关服务）。

## 四、技术实现

在技术实现的过程中会逐步的去实现我们需要的功能，将需要的用到知识点信息拆开讲解，以达到最终的案例目标。

### 1. 创建自定义注解

**模拟网关类注解**

```java 
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RpcGatewayClazz {

    String clazzDesc() default "";
    String alias() default "";
    long timeOut() default 350;

}
```

**模拟网关方法注解**

```java 
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RpcGatewayMethod {

    String methodName() default "";
    String methodDesc() default "";
    
}
```

- 这部分你可以创建任何类型的注解，主要是用于模拟类和方法上分别添加注解并获取最终属性值的效果。

### 2. 创建委托函数

```java
public class UserRepositoryInterceptor {

    public static String intercept(@Origin Method method, @AllArguments Object[] arguments) {
        return "小傅哥博客，查询文章数据：https://bugstack.cn/?id=" + arguments[0];
    }

}
```

- 最终我们的字节码操作会通过委托的方式来实现抽象类的功能。
- 在委托函数中的用到注解已经在上一章节中完整的介绍了，可以回顾参考。
- `@Origin` 可以绑定到以下类型的参数：Method 被调用的原始方法 Constructor 被调用的原始构造器 Class 当前动态创建的类 MethodHandle MethodType String 动态类的toString()的返回值 int 动态方法的修饰符.
- `@AllArguments` 绑定所有参数的数组。

### 3. 创建方法主体信息

```java 
// 生成含有注解的泛型实现字类
DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
        .subclass(TypeDescription.Generic.Builder.parameterizedType(Repository.class, String.class).build()) // 创建复杂类型的泛型注解
        .name(Repository.class.getPackage().getName().concat(".").concat("UserRepository"))                  // 添加类信息包括地址
        .method(ElementMatchers.named("queryData"))                                                          // 匹配处理的方法
        .intercept(MethodDelegation.to(UserRepositoryInterceptor.class))                                     // 交给委托函数
        .annotateMethod(AnnotationDescription.Builder.ofType(RpcGatewayMethod.class).define("methodName", "queryData").define("methodDesc", "查询数据").build())
        .annotateType(AnnotationDescription.Builder.ofType(RpcGatewayClazz.class).define("alias", "dataApi").define("clazzDesc", "查询数据信息").define("timeOut", 350L).build())
        .make();
```

- 这部分基本是`Byte-buddy`的模板方法，通过核心API；`subclass`、`name`、`method`、`intercept`、`annotateMethod`、`annotateType` 的使用构建方法。
- 首先是定义复杂类型的自定义注解，设定为本方法的父类，这部分内容也就是抽象类。`Repository<T>`，通过 `TypeDescription.Generic.Builder.parameterizedType(Repository.class, String.class).build()` 来构建。
- 设定类名称在我们之前就已经使用过，这里多加类的路径信息。`concat` 函数是字符串的连接符，替换 `+` 号。
- `method`，设定匹配处理方法名称。
- `MethodDelegation.to(UserRepositoryInterceptor.class)`，最终的核心是关于委托函数的使用。这里的使用也就可以调用到我们上面定义的委托函数，等最终我们通过字节码生成的 `class` 类进行查看。
- `annotateMethod`、`annotateType`，定义类和方法的注解，通过 `define` 设定值（可以多次使用）。

### 4. 将创建的类写入目录

```java
// 输出类信息到目标文件夹下
dynamicType.saveIn(new File(ApiTest.class.getResource("/").getPath()));
```

- 这部分内容是 `Byte-buddy` 提供的 API 方法；`saveIn`，把字节码信息写成 `class` 到执行的文件夹下。这样就可以非常方便的验证通过字节码框架创建的方法内容。

**字节码方法内容**

```java
package org.itstack.demo.bytebuddy;

@RpcGatewayClazz(
    clazzDesc = "查询数据信息",
    alias = "dataApi",
    timeOut = 350L
)
public class UserRepository extends Repository<String> {
    @RpcGatewayMethod(
        methodName = "queryData",
        methodDesc = "查询数据"
    )
    public String queryData(int var1) {
        return FindOneInterceptor.intercept(cachedValue$aGmAjHXh$iha1qv0, new Object[]{var1});
    }

    public UserRepository() {
    }

    static {
        cachedValue$aGmAjHXh$iha1qv0 = Repository.class.getMethod("queryData", Integer.TYPE);
    }
}
```

- 从上可以看出来我们的自定义类已经实现了抽象类，同时也添加了类和方法的注解信息。
- 而在实现的类中有一步是使用委托函数进行处理方法的内容。

### 5. 输出自定义注解信息

```java 
// 从目标文件夹下加载类信息
Class<Repository<String>> repositoryClass = (Class<Repository<String>>) Class.forName("org.itstack.demo.bytebuddy.UserRepository");

// 获取类注解
RpcGatewayClazz rpcGatewayClazz = repositoryClass.getAnnotation(RpcGatewayClazz.class);
System.out.println("RpcGatewayClazz.clazzDesc：" + rpcGatewayClazz.clazzDesc());
System.out.println("RpcGatewayClazz.alias：" + rpcGatewayClazz.alias());
System.out.println("RpcGatewayClazz.timeOut：" + rpcGatewayClazz.timeOut()); 

// 获取方法注解
RpcGatewayMethod rpcGatewayMethod = repositoryClass.getMethod("queryData", int.class).getAnnotation(RpcGatewayMethod.class);
System.out.println("RpcGatewayMethod.methodName：" + rpcGatewayMethod.methodName());
System.out.println("RpcGatewayMethod.methodDesc：" + rpcGatewayMethod.methodDesc());
```

 - 在这里我们使用的是 `Class.forName`，进行加载类信息。也可以像以前的章节一样使用；`unloadedType.load(XXX.class.getClassLoader())` 的方式进行直接处理字节码。
 - 最后是读取自定义注解的信息内容，包括类和方法。

### 6. 测试验证运行

```java 
// 实例化对象
Repository<String> repository = repositoryClass.newInstance();
// 测试输出
System.out.println(repository.queryData(10001));
```

- 通过 `Class.forName` 的方式就可以直接调用方法，如果加载字节码的方式就需要通过反射进行处理（*以往章节有案例可以对照学习*）。

**测试结果**

```java 
RpcGatewayClazz.clazzDesc：查询数据信息
RpcGatewayClazz.alias：dataApi
RpcGatewayClazz.timeOut：350
RpcGatewayMethod.methodName：queryData
RpcGatewayMethod.methodDesc：查询数据
小傅哥博客，查询文章数据：https://bugstack.cn/?id=10001

Process finished with exit code 0
```

- 不出意外你会看到以上的结果信息，通过我们使用字节码创建的方法已经可以按照我们的需求进行内容输出。

## 五、总结

- 在本章节的学习中需要注意几个知识点的使用，包括；`委托方法使用`、`复杂类型的泛型创建`、`类和方法自定义注解的添加`以及`写入字节码信息到文件中`。
- 截至到目前基本我们已经对常用的字节码框架自我学习和分享的基本完成了，另外一些其他的API的使用可以参考官方文档；[https://bytebuddy.net](https://bytebuddy.net/)
- 每一段知识都是只有进行系统化的学习才能有完整的收获，只言片语带来的碎片化体验总是不能对一个技术进行全方面的了解。在技术的这条路上，多加油！ 