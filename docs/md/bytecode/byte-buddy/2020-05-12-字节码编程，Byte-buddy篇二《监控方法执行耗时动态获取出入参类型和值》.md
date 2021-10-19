---
layout: post
category: itstack-demo-agent
title: 二、监控方法执行耗时动态获取出入参类型和值
tagline: by 付政委
tag: [itstack-demo-bytecode]
excerpt: 通过对Byte-buddy高级API的委托方式的使用，再加上注解@Origin、@SuperCall等获取方法在执行过程中的入参信息方法的出参结果，最终学习委托处理的方式对方法进行监控。
lock: need
---

# 字节码编程，Byte-buddy篇二《监控方法执行耗时动态获取出入参类型和值》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、前言

**案例**是剥去外衣包装展示出核心功能的最佳学习方式！

就像是我们研究字节码编程最终是需要应用到实际场景中，例如：实现一款非入侵的全链路最终监控系统，那么这里就会包括一些基本的核心功能点；`方法执行耗时`、`出入参获取`、`异常捕获`、`添加链路ID`等等。而这些一个个的功能点，最快的掌握方式就是去实现他最基本的功能验证，这个阶段基本也是技术选型的阶段，验证各项技术点是否可以满足你后续开发的需求。否则在后续开发中，如果已经走了很远的时候再发现不适合，那么到时候就很麻烦了。

在前面的`ASM`、`Javassist` 章节中也有陆续实现过获取方法的出入参信息，但实现的方式还是偏向于*字节码控制*，尤其`ASM`，更是需要使用到字节码指令将入参信息压栈操作保存到局部变量用于输出，在这个过程中需要深入了解`Java虚拟机规范`，否则很不好完成这一项的开发。但！`ASM`也是性能最牛的。其他的字节码编程框架都是基于它所开发的。**关于这部分系列文章可以访问链接进行专题系列的学习**：[https://bugstack.cn/itstack/itstack-demo-bytecode.html](https://bugstack.cn/itstack/itstack-demo-bytecode.html)

**那么**，本章节我们会使用 `Byte-buddy` 来实现这一功能，在接下来的操作中你会感受到这个字节码框架的魅力，它的*API*更加高级也更符合普遍易接受的操作方式进行处理。

## 二、开发环境

1. JDK 1.8.0
2. byte-buddy 1.10.9
3. byte-buddy-agent 1.10.9
4. 本章涉及源码在：`itstack-demo-bytecode-2-02`，可以关注**公众号**：[`bugstack虫洞栈`](https://bugstack.cn/assets/images/qrcode.png)，回复源码下载获取。`你会获得一个下载链接列表，打开后里面的第17个「因为我有好多开源代码」`，记得给个`Star`！

## 三、案例目标

在这里我们定义一个类并创建出等待被监控的方法，当方法执行时监控方法的各项信息；`执行耗时`、`出入参信息`等。

```java 
public class BizMethod {

    public String queryUserInfo(String uid, String token) throws InterruptedException {
        Thread.sleep(new Random().nextInt(500));
        return "德莱联盟，王牌工程师。小傅哥(公众号：bugstack虫洞栈)，申请出栈！";
    }

}
```

- 我们这里模拟监控并没有使用 `Javaagent` 去做字节码加载时的增强，主要为了将**最核心**的内容体现出来。后续的章节会陆续讲解各个核心功能的组合使用，做出一套监控系统。

## 四、技术实现

在技术实现的过程中，我会陆续的将需要监控的内容一步步完善。这样将一个总体的内容进行拆解后，方便学习和理解。

### 1. 创建监控主体类

```java
@Test
public void test_byteBuddy() throws Exception {
    DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
            .subclass(BizMethod.class)
            .method(ElementMatchers.named("queryUserInfo"))
            .intercept(MethodDelegation.to(MonitorDemo.class))
            .make();

    // 加载类
    Class<?> clazz = dynamicType.load(ApiTest.class.getClassLoader())
            .getLoaded();  

    // 反射调用
    clazz.getMethod("queryUserInfo", String.class, String.class).invoke(clazz.newInstance(), "10001", "Adhl9dkl");
}
```

- 这一部分是 `Byte Buddy` 的模版代码，定义需要被加载的类和方法；*BizMethod.class*、*ElementMatchers.named("queryUserInfo")*，这一步也就是让程序可以定位到你的被监控内容。
- 接下来就是最重要的一部分**委托**；`MethodDelegation.to(MonitorDemo.class)`，最终所有的监控操作都会被 `MonitorDemo.class` 类中的方法进行处理。
- 最后就是类的加载和反射调用，这部分主要用于每次的测试验证。*查找方法，传递对象和入参信息*

### 2. 监控方法耗时

如上一步所述这里主要需要使用到，委托类进行控制监控信息。

```java 
public class MonitorDemo {

    @RuntimeType
    public static Object intercept(@SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        try {
            return callable.call();
        } finally {
            System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
        }
    }

}
```

- 这里面包括几个核心的知识点；`@RuntimeType`：定义运行时的目标方法。`@SuperCall`：用于调用父类版本的方法。
- 定义好方法后，下面有一个 `callable.call();` 这个方法是调用原方法的内容，返回结果。而前后包装的。
- 最后在`finally`中，打印方法的执行耗时。`System.currentTimeMillis() - start`

**测试结果：**

```java 
方法耗时：419ms

Process finished with exit code 0
```

### 3. 获取方法信息

获取方法信息的过程其实就是在获取方法的描述内容，也就是你编写的方法拆解为各个内容进行输出。那么为了实现这样的功能我们需要使用到新的注解 `@Origin Method method`

```java
@RuntimeType
public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable) throws Exception {
    long start = System.currentTimeMillis();
    Object resObj = null;
    try {
        resObj = callable.call();
        return resObj;
    } finally {
        System.out.println("方法名称：" + method.getName());
        System.out.println("入参个数：" + method.getParameterCount());
        System.out.println("入参类型：" + method.getParameterTypes()[0].getTypeName() + "、" + method.getParameterTypes()[1].getTypeName());
        System.out.println("出参类型：" + method.getReturnType().getName());
        System.out.println("出参结果：" + resObj);
        System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
    }
}
```

- `@Origin`，用于拦截原有方法，这样就可以获取到方法中的相关信息。
- 这一部分的信息相对来说比较全，尤其也获取到了参数的个数和类型，这样就可以在后续的处理参数时进行循环输出。

**测试结果：**

```java 
方法名称：queryUserInfo
入参个数：2
入参类型：java.lang.String、java.lang.String
出参类型：java.lang.String
出参结果：德莱联盟，王牌工程师。小傅哥(公众号：bugstack虫洞栈)，申请出栈！
方法耗时：490ms

Process finished with exit code 0
```

### 4. 获取入参内容

当我们能获取入参的基本描述以后，再者就是获取入参的内容。在一段方法执行的过程中，如果可以在必要的时候拿到当时入参的信息，那么就可以非常方便的进行排查异常快速定位问题。在这里我们会用到新的注解；`@AllArguments` 、`@Argument(0)`，一个用于获取全部参数，一个获取指定的参数。

```java 
@RuntimeType
public static Object intercept(@Origin Method method, @AllArguments Object[] args, @Argument(0) Object arg0, @SuperCall Callable<?> callable) throws Exception {
    long start = System.currentTimeMillis();
    Object resObj = null;
    try {
        resObj = callable.call();
        return resObj;
    } finally {
        System.out.println("方法名称：" + method.getName());
        System.out.println("入参个数：" + method.getParameterCount());
        System.out.println("入参类型：" + method.getParameterTypes()[0].getTypeName() + "、" + method.getParameterTypes()[1].getTypeName());
        System.out.println("入参内容：" + arg0 + "、" + args[1]);
        System.out.println("出参类型：" + method.getReturnType().getName());
        System.out.println("出参结果：" + resObj);
        System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
    }
}
```

- 与上面的代码块相比，多了参数的获取和打印。主要知道这个方法就可以很方便的获取入参的内容。

**测试结果：**

```java 
方法名称：queryUserInfo
入参个数：2
入参类型：java.lang.String、java.lang.String
入参内容：10001、Adhl9dkl
出参类型：java.lang.String
出参结果：德莱联盟，王牌工程师。小傅哥(公众号：bugstack虫洞栈)，申请出栈！
方法耗时：405ms

Process finished with exit code 0
```

### 5. 其他注解汇总

除了以上为了获取方法的执行信息使用到的注解外，`Byte Buddy` 还提供了很多其他的注解。如下；

| 注解          | 说明                                                         |
| ------------- | ------------------------------------------------------------ |
| @Argument     | 绑定单个参数                                                 |
| @AllArguments | 绑定所有参数的数组                                           |
| @This         | 当前被拦截的、动态生成的那个对象                             |
| @Super        | 当前被拦截的、动态生成的那个对象的父类对象                   |
| @Origin       | 可以绑定到以下类型的参数：Method 被调用的原始方法 Constructor 被调用的原始构造器 Class 当前动态创建的类 MethodHandle MethodType String  动态类的toString()的返回值 int  动态方法的修饰符 |
| @DefaultCall  | 调用默认方法而非super的方法                                  |
| @SuperCall    | 用于调用父类版本的方法                                       |
| @Super        | 注入父类型对象，可以是接口，从而调用它的任何方法             |
| @RuntimeType  | 可以用在返回值、参数上，提示ByteBuddy禁用严格的类型检查      |
| @Empty        | 注入参数的类型的默认值                                       |
| @StubValue    | 注入一个存根值。对于返回引用、void的方法，注入null；对于返回原始类型的方法，注入0 |
| @FieldValue   | 注入被拦截对象的一个字段的值                                 |
| @Morph        | 类似于@SuperCall，但是允许指定调用参数                       |

### 6. 常用核心API

1. `ByteBuddy`
    - 流式API方式的入口类
    - 提供Subclassing/Redefining/Rebasing方式改写字节码
    - 所有的操作依赖DynamicType.Builder进行,创建不可变的对象
    
2. `ElementMatchers(ElementMatcher)`
    - 提供一系列的元素匹配的工具类(named/any/nameEndsWith等等)
    - ElementMatcher(提供对类型、方法、字段、注解进行matches的方式,类似于Predicate)
    - Junction对多个ElementMatcher进行了and/or操作

3. `DynamicType`(动态类型,所有字节码操作的开始,非常值得关注)
    - Unloaded(动态创建的字节码还未加载进入到虚拟机,需要类加载器进行加载)
    - Loaded(已加载到jvm中后,解析出Class表示)
    - Default(DynamicType的默认实现,完成相关实际操作)

4. `Implementation`(用于提供动态方法的实现)
   - FixedValue(方法调用返回固定值)
   - MethodDelegation(方法调用委托,支持两种方式: Class的static方法调用、object的instance method方法调用)

5. `Builder`(用于创建DynamicType,相关接口以及实现后续待详解)
   - MethodDefinition
   - FieldDefinition
   - AbstractBase 
   
## 五、总结

- 在这一章节的实现过程来看，只要知道相关API就可以很方便的解决我们的监控方法信息的诉求，他所处理的方式非常易于使用。而在本章节中也要学会几个关键知识点；委托、方法注解、返回值注解以及入参注解。
- 当我们学会了监控的核心功能，在后续与`Javaagent`结合使用时就可以很容易扩展进去，而不是看到了陌生的代码。对于这一部分非入侵的入侵链路监控，也是目前比较热门的话题和需要探索的解决方案，就像最近阿里云也举办了类似的编程挑战赛。[首届云原生编程挑战赛1：实现一个分布式统计和过滤的链路追踪](https://tianchi.aliyun.com/competition/entrance/231790/introduction)    
- 关于字节码编程专栏已经完成了大部分文章的编写，包括如下文章；(**学习链接**：[`https://bugstack.cn/itstack/itstack-demo-bytecode.html`](https://bugstack.cn/itstack/itstack-demo-bytecode.html))
  
    - [`字节码编程，Byte-buddy篇一《基于Byte Buddy语法创建的第一个HelloWorld》`](https://bugstack.cn/itstack/itstack-demo-bytecode.html)
    - [`字节码编程，Javassist篇五《使用Bytecode指令码生成含有自定义注解的类和方法》`](https://bugstack.cn/itstack/itstack-demo-bytecode.html)
    - [`字节码编程，Javassist篇四《通过字节码插桩监控方法采集运行时入参出参和异常信息》`](https://bugstack.cn/itstack/itstack-demo-bytecode.html)
    - [`字节码编程，Javassist篇三《使用Javassist在运行时重新加载类「替换原方法输出不一样的结果」》`](https://bugstack.cn/itstack/itstack-demo-bytecode.html)
    - [`字节码编程，Javassist篇二《定义属性以及创建方法时多种入参和出参类型的使用》`](https://bugstack.cn/itstack/itstack-demo-bytecode.html)
    - [`字节码编程，Javassist篇一《基于javassist的第一个案例helloworld》`](https://bugstack.cn/itstack/itstack-demo-bytecode.html)
    - [`ASM字节码编程 | 用字节码增强技术给所有方法加上TryCatch捕获异常并输出`](https://bugstack.cn/itstack/itstack-demo-bytecode.html)
    - [`ASM字节码编程 | JavaAgent+ASM字节码插桩采集方法名称以及入参和出参结果并记录方法耗时`](https://bugstack.cn/itstack/itstack-demo-bytecode.html)
    - [`ASM字节码编程 | 如果你只写CRUD，那这种技术你永远碰不到`](https://bugstack.cn/itstack/itstack-demo-bytecode.html)

- **最佳的学习体验和方式**是，在学习和探索的过程中不断的对知识进行深度的学习，通过一个个实践的方式让知识成结构化和体系的建设。    