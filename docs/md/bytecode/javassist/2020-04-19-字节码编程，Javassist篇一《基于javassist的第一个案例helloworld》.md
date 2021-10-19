---
layout: post
category: itstack-demo-agent
title: 一、基于javassist的第一个案例helloworld
tagline: by 付政委
tag: [itstack-demo-bytecode]
excerpt: 字节码编程专栏，使用Javassist动态生成类和方法，输出Helloworld。虽然这部分技术内容在 CRUD 开发中并不常用，但随着自动化测试、非入侵监控的大量使用，还是蛮多人需要这样的技能学习的。
lock: need
---

# 字节码编程，Javassist篇一《基于javassist的第一个案例helloworld》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、前言

在字节码编程方面有三个比较常见的框架；`ASM`、`byte-buddy`、`Javassist`，他们都可以对这字节码进行操作，只是操作方式和控制粒度不同。

其中 **ASM** 更偏向于底层，需要了解 **JVM** 虚拟机中指定规范以及对局部变量以及操作数栈的知识。虽然在编写起来比较麻烦，但是它也是性能最好功能最强的字节码操作框架。常见的会用在 **CGLIB** 动态代理类中，以及一些非入侵的探针监控场景中。

另外两个框架都是有强大的 API，操作使用上更加容易控制。虽然对对比上会比 **ASM** 性能差一些，但不是说性能完全不好。同样在一些监控场景中也用的非常多。如果你细心可以在你的工程 **jar** 包搜索一下。

在这之前我已经编写了 `Javaagent全链路监控` 和 `ASM` 的部分文章，虽然这部分技术内容在 **CRUD** 开发中并不常用，但随着自动化测试、非入侵监控的大量使用，还是蛮多人需要这样的技能学习的。同时我也是这样一个技能的学习者，为此后面会陆续编写和完善关于 **字节码编程** 这个专栏。也希望这个专栏在提升自己技术栈的同时也帮助他人成长。

**那么**，小傅哥计划从 `Javassist` 到 `ASM` 陆续完成整套专栏学习的文章编写。从简单入门到应用操作，一步步来完成成体系的技术知识栈学习。

**好！**，现在开始第一个Helloworld案例。*相关源码可以通过关注 `公众号：bugstack虫洞栈` 获取*

## 二、开发环境

1. JDK 1.8.0
2. javassist 3.12.1.GA

    ```xml
    <dependency>
        <groupId>javassist</groupId>
        <artifactId>javassist</artifactId>
        <version>3.12.1.GA</version>
        <type>jar</type>
    </dependency>
    ```

## 三、案例目标

不看实现过程的话，我们的案例目标其实很简单，就是使用 `javassist` 输出一行 Helloworld 。~~这话像不像产品说的~~

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("javassist hi helloworld by 小傅哥(bugstack.cn)");
    }

    public HelloWorld() {
    }
}
```

以上的这段代码就是我们接下来需要使用字节码编程技术来实现的内容。

## 四、技术实现

其实输出一个 `Helloworld` 还是蛮简单的，主要是从这里面去学习一下 `Javassist` 的基本语法结构，也能为后续的学习有一个基础的概念。

>javassist Helloworld

```java
/**
 * 公众号：bugstack虫洞栈
 * 博客栈：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 * 本专栏是小傅哥多年从事一线互联网Java开发的学习历程技术汇总，旨在为大家提供一个清晰详细的学习教程。如果能为您提供帮助，请给予支持(关注、点赞、分享)！
 */
public class GenerateClazzMethod {


    public static void main(String[] args) throws IOException, CannotCompileException, NotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        ClassPool pool = ClassPool.getDefault();

        // 创建类 classname：创建类路径和名称
        CtClass ctClass = pool.makeClass("org.itstack.demo.javassist.HelloWorld");

        // 添加方法
        CtMethod mainMethod = new CtMethod(CtClass.voidType, "main", new CtClass[]{pool.get(String[].class.getName())}, ctClass);
        mainMethod.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
        mainMethod.setBody("{System.out.println(\"javassist hi helloworld by 小傅哥(bugstack.cn)\");}");
        ctClass.addMethod(mainMethod);

        // 创建无参数构造方法
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, ctClass);
        ctConstructor.setBody("{}");
        ctClass.addConstructor(ctConstructor);

        // 输出类内容
        ctClass.writeFile();

        // 测试调用
        Class clazz = ctClass.toClass();
        Object obj = clazz.newInstance();

        Method main = clazz.getDeclaredMethod("main", String[].class);
        main.invoke(obj, (Object)new String[1]);

    }

}
```

这段代码分为几块内容来实现功能，分别包括；
1. 创建 ClassPool，它是一个基于HashMap实现的 CtClass 对象容器。
2. 使用 CtClass，创建我们的类信息，也就是类的路径和名称。
3. 接下来就是给类添加方法。包括；方法的属性、类型、名称、入参、出参和方法体的内容。
4. 在方法创建好后还需要创建一个空的构造函数，每一个类都会在编译后生成这样一个构造函数。
5. 当方法创建完成后，我们使用 `ctClass.writeFile()` 进行输出方法的内容信息。也就可以看到通过我们使用 `Javassist` 生成类的样子。
6. 最后就是我们的反射调用 `main` 方法，测试输出结果。

## 五、测试结果

当我们执行测试的时候会输出类信息到工程文件夹下，同时会输出我们的测试结果；

### 1. 使用Javassist生成的类

![使用Javassist生成的类，在工程文件夹下](https://bugstack.cn/assets/images/2020/itstack-demo-bytecode-1-01-1.png)

### 2. 输出的测试结果

```java
javassist hi helloworld by 小傅哥(bugstack.cn)

Process finished with exit code 0
```



## 六、总结

- 关于 `Javassist` 的使用在完整的且强大的 `API` 下，确实还是蛮容易使用的。并且代码的使用上并不是很难理解。
- 后续会陆续推出字节码编程的案例文章，逐步完善这部分技术知识栈的内容。最终尝试使用这样的技术知识完成一个案例级别的质量检测系统。也欢迎喜欢此类内容的小伙伴跟进学习。
- 后续的文章可能在专栏类的文章里，文章内容上会短一点。尽可能在一篇文章中描述清楚一个详尽的知识点，也方便后续整理成 PDF 书籍，方便学习使用。