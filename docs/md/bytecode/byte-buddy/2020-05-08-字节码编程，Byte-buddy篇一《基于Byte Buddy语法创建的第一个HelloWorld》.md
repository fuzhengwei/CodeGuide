---
layout: post
category: itstack-demo-agent
title: 一、基于Byte Buddy语法创建的第一个HelloWorld
tagline: by 付政委
tag: [itstack-demo-bytecode]
excerpt: 通过基础案例了解Byte-buddy字节码编程技术，从案例入门逐步深入。她无需理解字节码指令，即可使用简单的 API 就能很容易操作字节码，控制类和方法。
lock: need
---

# 字节码编程，Byte-buddy篇一《基于Byte Buddy语法创建的第一个HelloWorld》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、前言

相对于[`小傅哥`](https://bugstack.cn)之前编写的字节码编程； `ASM`、`Javassist` 系列，`Byte Buddy` 玩法上更加高级，你可以完全不需要了解一个类和方法块是如何通过 `指令码` *LDC、LOAD、STORE、IRETURN...* 生成出来的。就像它的官网介绍； 

`Byte Buddy` 是一个代码生成和操作库，用于在 `Java` 应用程序运行时创建和修改 `Java` 类，而无需编译器的帮助。除了 `Java` 类库附带的代码生成实用程序外，`Byte Buddy` 还允许创建任意类，并且不限于实现用于创建运行时代理的接口。此外，`Byte Buddy` 提供了一种方便的 API，可以使用 `Java` 代理或在构建过程中手动更改类。

- 无需理解字节码指令，即可使用简单的 API 就能很容易操作字节码，控制类和方法。
- 已支持Java 11，库轻量，仅取决于Java字节代码解析器库ASM的访问者API，它本身不需要任何其他依赖项。
- 比起JDK动态代理、cglib、Javassist，Byte Buddy在性能上具有一定的优势。

>2015年10月，Byte Buddy被 Oracle 授予了 Duke's Choice大奖。该奖项对Byte Buddy的“ Java技术方面的巨大创新 ”表示赞赏。我们为获得此奖项感到非常荣幸，并感谢所有帮助Byte Buddy取得成功的用户以及其他所有人。我们真的很感激！ 

除了这些简单的介绍外，还可以通过官网：[`https://bytebuddy.net`](https://bytebuddy.net/#/)，去了解更多关于 `Byte Buddy` 的内容。

**好！**那么接下来，我们开始从 `HelloWorld` 开始。深入了解一个技能前，先多多运行，这样总归能让找到学习的快乐。

## 二、开发环境

1. JDK 1.8.0
2. byte-buddy 1.10.9
3. byte-buddy-agent 1.10.9
4. 本章涉及源码在：`itstack-demo-bytecode-2-01`，可以关注**公众号**：[`bugstack虫洞栈`](https://bugstack.cn/assets/images/qrcode.png)，回复源码下载获取。`你会获得一个下载链接列表，打开后里面的第17个「因为我有好多开源代码」`，记得给个`Star`！

## 三、案例目标

每一个程序员，都运行过 `N` 多个` HelloWorld`，就像很熟悉的 `Java`；

```java 
public class Hi {

    public static void main(String[] args) {
        System.out.println("Byte-buddy Hi HelloWorld By 小傅哥(bugstack.cn)");
    }

}
```     

那么我们接下来就通过使用动态字节码生成的方式，来创建出可以输出 `HelloWorld` 的程序。

*新知识点的学习不要慌，最主要是找到一个可以入手的点，通过这样的一个点去慢慢解开整个程序的面纱。*

## 四、技术实现

### 1. 官网经典例子

在我们看官网文档中，从它的介绍了就已经提供了一个非常简单的例子，用于输出 `HelloWorld`，我们在这展示并讲解下。

**案例代码：**

```java 
String helloWorld = new ByteBuddy()
            .subclass(Object.class)
            .method(named("toString"))
            .intercept(FixedValue.value("Hello World!"))
            .make()
            .load(getClass().getClassLoader())
            .getLoaded()
            .newInstance()
            .toString();    

System.out.println(helloWorld);  // Hello World!
```    

他的运行结果就是一行，`Hello World!`，整个代码块核心功能就是通过 `method(named("toString"))`，找到 *toString* 方法，再通过拦截 `intercept`，设定此方法的返回值。`FixedValue.value("Hello World!")`。到这里其实一个基本的方法就通过 `Byte-buddy` ，改造完成。

接下来的这一段主要是用于加载生成后的 `Class` 和执行，以及调用方法 `toString()`。也就是最终我们输出了想要的结果。那么，如果你不能看到这样一段方法块，把我们的代码改造后的样子，心里还是有点虚。那么，我们通过字节码输出到文件，看下具体被改造后的样子，如下；

**编译后的Class文件**，`ByteBuddyHelloWorld.class`

```java 
public class HelloWorld {
    public String toString() {
        return "Hello World!";
    }

    public HelloWorld() {
    }
}
```        

在官网来看，这是一个非常简单并且能体现 `Byte buddy` 的例子。但是与我们平时想创建出来的 `main` 方法相比，还是有些差异。那么接下来，我们尝试使用字节码编程技术创建出这样一个方法。

### 2. 字节码创建类和方法

接下来的例子会通过一点点的增加代码梳理，不断的把一个方法完整的创建出来。

#### 2.1 定义输出字节码方法

为了可以更加清晰的看到每一步对字节码编程后，所创建出来的方法样子(clazz)，我们需要输出字节码生成 `clazz`。在Byte buddy中默认提供了一个 `dynamicType.saveIn()` 方法，我们暂时先不使用，而是通过字节码进行保存。

```java 
private static void outputClazz(byte[] bytes) {
    FileOutputStream out = null;
    try {
        String pathName = ApiTest.class.getResource("/").getPath() + "ByteBuddyHelloWorld.class";
        out = new FileOutputStream(new File(pathName));
        System.out.println("类输出路径：" + pathName);
        out.write(bytes);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (null != out) try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```       

- 这个方法我们在之前也用到过，主要就是一个 `Java` 基础的内容，输出字节码到文件中。

#### 2.2 创建类信息

```java 
DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
        .subclass(Object.class)
        .name("org.itstack.demo.bytebuddy.HelloWorld")
        .make();

// 输出类字节码
outputClazz(dynamicType.getBytes());
```

- 创建类和定义类名，如果不写类名会自动生成要给类名。

**此时class文件：**

```java
public class HelloWorld {
    public HelloWorld() {
    }
}
```      

#### 2.3 创建main方法

```java 
DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
        .subclass(Object.class)
        .name("org.itstack.demo.bytebuddy.HelloWorld")
        .defineMethod("main", void.class, Modifier.PUBLIC + Modifier.STATIC)
        .withParameter(String[].class, "args")
        .intercept(FixedValue.value("Hello World!"))
        .make();
```  

与上面相比新增的代码片段；

- `defineMethod("main", void.class, Modifier.PUBLIC + Modifier.STATIC)`，定义方法；名称、返回类型、属性*public static*
- `withParameter(String[].class, "args")`，定义参数；参数类型、参数名称
- `intercept(FixedValue.value("Hello World!"))`，拦截设置返回值，但此时还能满足我们的要求。

这里有一个知识点，`Modifier.PUBLIC + Modifier.STATIC`，这是一个是二进制相加，每一个类型都在二进制中占有一位。例如 `1 2 4 8 ...` 对应的二进制占位 `1111`。所以可以执行相加运算，并又能保留原有单元的属性。

**此时class文件：**

```java
public class HelloWorld {
    public static void main(String[] args) {
        String var10000 = "Hello World!";
    }

    public HelloWorld() {
    }
}
```      

此时基本已经可以看到我们平常编写的 `Hello World` 影子了，但还能输出结果。

#### 2.4 委托函数使用

为了能让我们使用字节码编程创建的方法去输出一段 `Hello World` ，那么这里需要使用到`委托`。

```java 
DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
        .subclass(Object.class)
        .name("org.itstack.demo.bytebuddy.HelloWorld")
        .defineMethod("main", void.class, Modifier.PUBLIC + Modifier.STATIC)
        .withParameter(String[].class, "args")
        .intercept(MethodDelegation.to(Hi.class))
        .make();
```

- 整体来看变化并不大，只有 `intercept(MethodDelegation.to(Hi.class))`，使用了一段委托函数，真正去执行输出的是另外的函数方法。

  - MethodDelegation，需要是 `public` 类
  - 被委托的方法与需要与原方法有着一样的入参、出参、方法名，否则不能映射上
  
**此时class文件：**

```java
public class HelloWorld {
    public static void main(String[] args) {
        Hi.main(var0);
    }

    public HelloWorld() {
    }
}
```   

- 那么此时就可以输出我们需要的内容了，`Hi.main` 是定义出来的委托函数。也就是一个 `HelloWorld`

## 五、测试结果

为了可以让整个方法运行起来，我们需要添加字节码加载和反射调用的代码块，如下；

```java 
// 加载类
Class<?> clazz = dynamicType.load(GenerateClazzMethod.class.getClassLoader())
        .getLoaded();

// 反射调用
clazz.getMethod("main", String[].class).invoke(clazz.newInstance(), (Object) new String[1]);
```   

**运行结果**

```java 
类输出路径：/User/xiaofuge/itstack/git/github.com/itstack-demo-bytecode/itstack-demo-bytecode-2-01/target/test-classes/ByteBuddyHelloWorld.class
helloWorld

Process finished with exit code 0
```     

**效果图**

![Byte buddy HelloWorld 效果图](https://bugstack.cn/assets/images/2020/itstack-demo-bytecode-2-01-1.png)

## 六、总结

- 在本章节 `Byte buddy` 中，需要掌握几个关键信息；创建方法、定义属性、拦截委托、输出字节码，以及最终的运行。这样的一个简单过程，可以很快的了解到如何使用 `Byte buddy`。
- 本系列文章后续会继续更新，把常用的 `Byte buddy` 方法通过实际的案例去模拟建设，在这个过程中加强学习使用。一些基础知识也可以通过官方文档进行学习；[https://bytebuddy.net](https://bytebuddy.net)。
- 在学习整理的过程中发现，关于字节码编程方面的资料并不是很全，主要源于大家平时的开发中基本是用不到的，谁也不可能总去修改字节码。但对于补全这样的成体系完善技术栈资料，却可以帮助很多需要的人。因此我也会持续输出类似这样空白的技术文章。


