---
layout: post
category: itstack-demo-agent
title: 五、使用Bytecode指令码生成含有自定义注解的类和方法
tagline: by 付政委
tag: [itstack-demo-bytecode]
excerpt: 到本章为止已经写了四篇关于字节码编程的内容，涉及了大部分的API方法。整体来说对 Javassist 已经有一个基本的使用认知。那么在 Javassist 中不仅提供了高级 API 用于创建和修改类、方法，还提供了低级 API 控制字节码指令的方式进行操作类、方法。
lock: need
---

# 字节码编程，Javassist篇五《使用Bytecode指令码生成含有自定义注解的类和方法》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、前言

到本章为止已经写了四篇关于字节码编程的内容，涉及了大部分的API方法。整体来说对 `Javassist` 已经有一个基本的使用认知。那么在 `Javassist` 中不仅提供了高级 `API` 用于创建和修改类、方法，还提供了低级 `API` 控制*字节码指令*的方式进行操作类、方法。

有了这样的 [`javassist API`](http://www.javassist.org/html/index.html) 在一些特殊场景下就可以使用字节码指令控制方法。

接下来我们通过字节码指令模拟一段含有自定义注解的方法修改和生成。在修改的过程中会将原有方法计算`息费`的返回值替换成 `0`，最后我们使用这样的技术去生成一段计算息费的方法。通过这样的练习学会字节码操作。

## 二、开发环境

1. JDK 1.8.0
2. javassist 3.12.1.GA
3. 本章涉及源码在：`itstack-demo-bytecode-1-04`，可以关注**公众号**：[`bugstack虫洞栈`](https://bugstack.cn/assets/images/qrcode.png)，回复*源码下载*获取。`你会获得一个下载链接列表，打开后里面的第17个「因为我有好多开源代码」`，记得给个`Star`！

## 三、案例目标

1. 使用指令码修改原有方法返回值
2. 使用指令码生成一样的方法

**测试方法**

```java 
@RpcGatewayClazz(clazzDesc = "用户信息查询服务", alias = "api", timeOut = 500)
public class ApiTest {

    @RpcGatewayMethod(methodDesc = "查询息费", methodName = "interestFee")
    public double queryInterestFee(String uId){
        return BigDecimal.TEN.doubleValue();  // 模拟息费计算返回
    }

}
```  

- 这里使用的注解是测试中自定义的，模拟一个相当于网关接口的暴漏。

## 四、技术实现

### 1. 读取类自定义注解

```java 
ClassPool pool = ClassPool.getDefault();
// 类、注解
CtClass ctClass = pool.get(ApiTest.class.getName());
// 通过集合获取自定义注解
Object[] clazzAnnotations = ctClass.getAnnotations();
RpcGatewayClazz rpcGatewayClazz = (RpcGatewayClazz) clazzAnnotations[0];
System.out.println("RpcGatewayClazz.clazzDesc：" + rpcGatewayClazz.clazzDesc());
System.out.println("RpcGatewayClazz.alias：" + rpcGatewayClazz.alias());
System.out.println("RpcGatewayClazz.timeOut：" + rpcGatewayClazz.timeOut());
```      

- `ctClass.getAnnotations()`，可以获取所有的注解，进行操作

**输出结果：**

```java 
RpcGatewayClazz.clazzDesc：用户信息查询服务
RpcGatewayClazz.alias：api
RpcGatewayClazz.timeOut：500
```   

### 2. 读取方法的自定义注解

```java 
CtMethod ctMethod = ctClass.getDeclaredMethod("queryInterestFee");
RpcGatewayMethod rpcGatewayMethod = (RpcGatewayMethod) ctMethod.getAnnotation(RpcGatewayMethod.class);
System.out.println("RpcGatewayMethod.methodName：" + rpcGatewayMethod.methodName());
System.out.println("RpcGatewayMethod.methodDesc：" + rpcGatewayMethod.methodDesc());
```          

- 在读取方法自定义注解时，通过的是注解的 `class` 获取的，这样按照名称可以只获取最需要的注解名称。

**输出结果：**

```java
RpcGatewayMethod.methodName：interestFee
RpcGatewayMethod.methodDesc：查询息费
```

### 3. 读取方法指令码

```java 
MethodInfo methodInfo = ctMethod.getMethodInfo();
CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
CodeIterator iterator = codeAttribute.iterator();
while (iterator.hasNext()) {
    int idx = iterator.next();
    int code = iterator.byteAt(idx);
    System.out.println("指令码：" + idx + " > " + Mnemonic.OPCODE[code]);
}
```    

- 这里的指令码就是一个方法编译后在 `JVM` 执行的操作流程。

**输出结果：**

```java
指令码：0 > getstatic
指令码：3 > invokevirtual
指令码：6 > dreturn
```

### 4. 通过指令修改方法

```java
ConstPool cp = methodInfo.getConstPool();
Bytecode bytecode = new Bytecode(cp);
bytecode.addDconst(0);
bytecode.addReturn(CtClass.doubleType);
methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
```

- `addDconst`，将 double 型0推送至栈顶
- `addReturn`，返回 double 类型的结果

此时的方法的返回值已经被修改，下面的是新的 `class` 类；

```java
@RpcGatewayClazz(
    clazzDesc = "用户信息查询服务",
    alias = "api",
    timeOut = 500L
)
public class ApiTest {
    public ApiTest() {
    }

    @RpcGatewayMethod(
        methodDesc = "查询息费",
        methodName = "interestFee"
    )
    public double queryInterestFee(String var1) {
        return 0.0D;
    }
}
```        

- 可以看到查询息费的返回结果已经是 `0.0D`。如果你的程序被这样操作，那么还是很危险的。所以有时候会进行一些混淆编译，降低破解风险。

### 5. 使用指令码生成方法

#### 5.1 创建基础方法信息

```java 
ClassPool pool = ClassPool.getDefault();
// 创建类信息
CtClass ctClass = pool.makeClass("org.itstack.demo.javassist.HelloWorld");
// 添加方法
CtMethod mainMethod = new CtMethod(CtClass.doubleType, "queryInterestFee", new CtClass[]{pool.get(String.class.getName())}, ctClass);
mainMethod.setModifiers(Modifier.PUBLIC);
MethodInfo methodInfo = mainMethod.getMethodInfo();
ConstPool cp = methodInfo.getConstPool();
```

- 创建类和方法的信息在我们几个章节中也经常使用，主要是创建方法的时候需要传递；返回类型、方法名称、入参类型，以及最终标记方法的可访问量。

#### 5.2 创建类使用注解

```java 
// 类添加注解
AnnotationsAttribute clazzAnnotationsAttribute = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
Annotation clazzAnnotation = new Annotation("org/itstack/demo/javassist/RpcGatewayClazz", cp);
clazzAnnotation.addMemberValue("clazzDesc", new StringMemberValue("用户信息查询服务", cp));
clazzAnnotation.addMemberValue("alias", new StringMemberValue("api", cp));
clazzAnnotation.addMemberValue("timeOut", new LongMemberValue(500L, cp));
clazzAnnotationsAttribute.setAnnotation(clazzAnnotation);
ctClass.getClassFile().addAttribute(clazzAnnotationsAttribute);
```

- `AnnotationsAttribute`，创建自定义注解标签
- `Annotation`，创建实际需要的自定义注解，这里需要传递自定义注解的类路径
- `addMemberValue`，用于添加自定义注解中的值。需要注意不同类型的值 `XxxMemberValue` 前缀不一样；*StringMemberValue*、*LongMemberValue*
- `setAnnotation`，最终设置自定义注解。如果不设置，是不能生效的。

#### 5.3 创建方法注解

```java
// 方法添加注解
AnnotationsAttribute methodAnnotationsAttribute = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
Annotation methodAnnotation = new Annotation("org/itstack/demo/javassist/RpcGatewayMethod", cp);
methodAnnotation.addMemberValue("methodName", new StringMemberValue("查询息费", cp));
methodAnnotation.addMemberValue("methodDesc", new StringMemberValue("interestFee", cp));
methodAnnotationsAttribute.setAnnotation(methodAnnotation);
methodInfo.addAttribute(methodAnnotationsAttribute);
```   

- 设置类的注解与设置方法的注解，前面的内容都是一样的。唯独需要注意的是方法的注解，需要设置到方法的；`addAttribute` 上。

#### 5.4 字节码编写方法快

```java 
// 指令控制
Bytecode bytecode = new Bytecode(cp);
bytecode.addGetstatic("java/math/BigDecimal", "TEN", "Ljava/math/BigDecimal;");
bytecode.addInvokevirtual("java/math/BigDecimal", "doubleValue", "()D");
bytecode.addReturn(CtClass.doubleType);
methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
```

- `Javassist` 中的指令码通过，*Bytecode* 的方式进行添加。基本所有的指令你都可以在这里使用，它有非常强大的 `API`。
- `addGetstatic`，获取指定类的静态域, 并将其压入栈顶
- `addInvokevirtual`，调用实例方法
- `addReturn`，从当前方法返回double
- 最终讲字节码添加到方法中，也就是会变成方法体。

#### 5.5 添加方法信息并输出

```java
// 添加方法
ctClass.addMethod(mainMethod);
 
// 输出类信息到文件夹下
ctClass.writeFile();
```                  
- 这部分内容就比较简单了，也是我们做 `Javassist` 字节码开发常用的内容。添加方法和输出字节码编程后的类信息。

#### 5.6 最终创建的类方法

```java 
@RpcGatewayClazz(
    clazzDesc = "用户信息查询服务",
    alias = "api",
    timeOut = 500L
)
public class HelloWorld {
    @RpcGatewayMethod(
        methodName = "查询息费",
        methodDesc = "interestFee"
    )
    public double queryInterestFee(String var1) {
        return BigDecimal.TEN.doubleValue();
    }

    public HelloWorld() {
    }
}
```      

![字节码生成含有注解的类和方法](https://bugstack.cn/assets/images/2020/itstack-demo-bytecode-1-05-1.png)

## 五、总结

- 本章节我们看到字节码编程不只可以像以前使用强大的api去直接编写代码，还可以向方法中添加指令，控制方法。这样就可以非常方便的处理一些特殊场景。例如 `TryCatch` 中的开始位置。
- 关于 `javassist` 字节码编程本身常用的方法基本已经覆盖完成，后续会集合 `JavaAgent` 做一些案例汇总，将知识点与实际场景进行串联。
- 学习终究还是要成体系的系统化深入学习，只言片语有的内容不能很好的形成一个技术栈的闭环，也不利于在项目中实战。 