---
layout: post
category: itstack-demo-agent
title: 二、定义属性以及创建方法时多种入参和出参类型的使用
tagline: by 付政委
tag: [itstack-demo-bytecode]
excerpt: 使用字节码编程的方式创建出类中的属性和不同类型的方法入参、出参，同时需要注意在Javassist中是不会进行自动装箱和拆箱操作的。
lock: need
---

# 字节码编程，Javassist篇二《定义属性以及创建方法时多种入参和出参类型的使用》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、前言

在上一篇 **Helloworld** 中，我们初步尝试使用了 `Javassist`字节编程的方式，来创建我们的方法体并通过反射调用运行了结果。大致了解到创建在使用字节码编程的时候基本离不开三个核心类；`ClassPool`、`CtClass`、`CtMethod`，它们分别管理着对象容器、类和方法。但是我们还少用一样就是字段；`CtFields`，在这一章节中我们不止会使用字段，还会创建多个不同入参类型和返回值的学习。

在学习之前先重点列一下相关的知识点，如下；
1. `CtClass.doubleType`、`intType`、`floatType`等 **8** 个基本类型和一个`voidType`，也就是空的返回类型。
2. 传递和返回的是对象类型时，那么需要时用；`pool.get(Double.class.getName()`，进行设置。
3. 当需要设置多个入参时，需要在数组中以此设置入参类型；`new CtClass[]{CtClass.doubleType, CtClass.doubleType}`。
4. 在方法体中需要取得入参并计算时，需要使用 `$1`、`$2` ...，数字表示入参的位置。`$0` 是 *this*。
5. `CtField` 设置属性字段，并赋值。
6. `Javassist` 中的装箱/拆箱。

**好**！那么我们就开始对这些知识点进行应用，创建出类和对应的方法。*「所有代码都可以关注公众号：`bugstack虫洞栈`，回复码下载获取」*

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

为了练习属性字段和方法的不同的入参、出参，我们使用 `javassist` 创建如下这样的方法。当然你也可以尝试去扩展其他类型的方法。

```java
public class ApiTest {

    private double π = 3.14D;

    //S = πr²
    public double calculateCircularArea(int r) {
        return π * r * r;
    }

    //S = a + b
    public double sumOfTwoNumbers(double a, double b) {
        return a + b;
    }

}
```

## 四、技术实现

>GenerateClazzMethod.java & 生成类和方法

```java
/**
 * 公众号：bugstack虫洞栈
 * 博客栈：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 * 本专栏是小傅哥多年从事一线互联网Java开发的学习历程技术汇总，旨在为大家提供一个清晰详细的学习教程，侧重点更倾向编写Java核心内容。如果能为您提供帮助，请给予支持(关注、点赞、分享)！
 */
public class GenerateClazzMethod {

    public static void main(String[] args) throws CannotCompileException, NotFoundException, IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        ClassPool pool = ClassPool.getDefault();

        CtClass ctClass = pool.makeClass("org.itstack.demo.javassist.MathUtil");

        // 属性字段
        CtField ctField = new CtField(CtClass.doubleType, "π", ctClass);
        ctField.setModifiers(Modifier.PRIVATE + Modifier.STATIC + Modifier.FINAL);
        ctClass.addField(ctField, "3.14");

        // 方法：求圆面积
        CtMethod calculateCircularArea = new CtMethod(CtClass.doubleType, "calculateCircularArea", new CtClass[]{CtClass.doubleType}, ctClass);
        calculateCircularArea.setModifiers(Modifier.PUBLIC);
        calculateCircularArea.setBody("{return π * $1 * $1;}");
        ctClass.addMethod(calculateCircularArea);

        // 方法；两数之和
        CtMethod sumOfTwoNumbers = new CtMethod(pool.get(Double.class.getName()), "sumOfTwoNumbers", new CtClass[]{CtClass.doubleType, CtClass.doubleType}, ctClass);
        sumOfTwoNumbers.setModifiers(Modifier.PUBLIC);
        sumOfTwoNumbers.setBody("{return Double.valueOf($1 + $2);}");
        ctClass.addMethod(sumOfTwoNumbers);
        // 输出类的内容
        ctClass.writeFile();

    }

}

```

**这里面有几个核心点，讲解如下；**
1. `CtField`，属性字段的创建。这就像我们正常写代码一样，需要设定属性的；名称、类型以及是 `public` 的还是 `private` 的以及 `static` 和 `final` 等。都可以通过 `Modifier.PRIVATE` + `Modifier.STATIC` + `Modifier.FINAL`，通过组合来控制。同样这也适用于对方法类型的设置。同时需要在添加属性的地方，设置初始值。
2. 接下来是我们设置了一个求圆面积的方法，如果说在方法体中需要使用到入参类型。那么需要通过符号 *$*+数字，来获取入参。这个数字就是当前入参的位置。比如取第一个入参：`$1`，以此类推。
3. 之后是我们的多种入参类型，在这开始我们也提到了。如果是基本类型入参都可以使用 `CtClass.doubleType`，对象类型入参使用 `pool.get(类.class.getName)` 获取。
4. 最终同样我们会把使用字节码编译的 *class* 输出到工程目录下 `ctClass.writeFile()`。
5. 在Javassist中并不会给类型做拆箱和装箱操作，需要显式的处理。例如上面案例中，需要将 `double` 使用 `Double.valueOf` 进行转换。

下面这张基本描述了一个类方法在创建时候不同参数的含义，可以参考。

![Javassist 创建类方法入参描述](https://bugstack.cn/assets/images/2020/itstack-demo-bytecode-1-02-1.png)

## 五、测试结果

### 1. 反射调用字节码类方法

**在测试之前，我们需要写一点反射代码来调用类的方法**

```java
// 测试调用
Class clazz = ctClass.toClass();
Object obj = clazz.newInstance();

Method method_calculateCircularArea = clazz.getDeclaredMethod("calculateCircularArea", double.class);
Object obj_01 = method_calculateCircularArea.invoke(obj, 1.23);
System.out.println("圆面积：" + obj_01);

Method method_sumOfTwoNumbers = clazz.getDeclaredMethod("sumOfTwoNumbers", double.class, double.class);
Object obj_02 = method_sumOfTwoNumbers.invoke(obj, 1, 2);
System.out.println("两数和：" + obj_02);
```

**测试结果：**

```java
圆面积：4.750506
两数和：3.0

Process finished with exit code 0
```

### 2. 查看使用Javassist生成的类

![Javassist 生成的类内容](https://bugstack.cn/assets/images/2020/itstack-demo-bytecode-1-02-2.png)

## 六、总结

1. 本篇案例中重点强调了属性字段创建，同时需要给属性字段赋值。
2. 在 `Javassist` 是不会进行类型的自动装箱和拆箱的，需要我们进行手动处理，否则生成类在执行会报类型错误。
3. 当需要使用入参的时候，可以使用 `$1` 来获取。这也是后续做一些监控获取入参的方法。