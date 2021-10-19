---
layout: post
category: itstack-demo-agent
title: 三、用字节码增强技术给所有方法加上TryCatch捕获异常并输出
tagline: by 付政委
tag: [itstack-demo-bytecode]
excerpt: 为了不让系统裸奔，把每一个方法都加上监控，你有什么技术手段吗？硬编码、AOP？还有吗！比如非入侵式的探针技术进行字节码增强，你是否有了解。
lock: need
---

# ASM字节码编程 | 用字节码增强技术给所有方法加上TryCatch捕获异常并输出

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、前言

![深夜Diss，一级爱慕](https://bugstack.cn/assets/images/2020/itstack-demo-asm-03-01.png)

你开发的系统是裸奔的吗？深夜被老板 **Diss**

一套系统是否稳定运行，取决于它的运行健康度，而这包括；调用量、可用率、影响时长以及服务器性能等各项指标的一个综合值。并且在系统出现异常问题时，可以抓取整个业务方法执行链路并输出；当时的入参、出参、异常信息等等。当然还包括一些JVM、Redis、Mysql的各项性能指标，以用于快速定位并解决问题。
             
那么要做到这样的事情有什么监控方案呢，这里面的做法比较多。比如；
1. 最简单粗暴的可能就是硬编码在方法中，收取执行耗时以及出入参和异常信息。但这样的成本实在太大，而且有一些不可预估的风险。
2. 可以选择切面方式做一套统一监控的组件，相对来说还是好一些的。但也需要硬编码，同时维护成本不低。
3. 市面上对于这样的监控其实是有整套的非入侵监控方案的，比如；Google Dapper、Zipkin等都可以实现，他们都是基于探针技术非入侵的采用字节码增强的方式进行监控。

*好*，那么这样非入侵的探针方式是怎么实现的呢？如何去做方法的`字节码增强`？

在字节码增强方面有三个框架；ASM、Javassist、ByteCode，各有优缺点按需选择。这在我们之前的字节码编程文章里也有所提到。

本文主要讲解关于 `ASM` 方式的字节码增强，接下来的案例会逐步讲解一个给方法添加 `TryCatch` 块，用于*采集异常信息以及正常的出参结果*的流程。

一步步向你展示通过指令码来改写你的方法！

## 二、系统环境

1. jdk1.8.0
2. asm-commons 6.2.1

## 三、技术目标

通过 `ASM` 字节码增强技术，使用指令码将方法修改为我们想要的效果。这部分原本需要使用 `JavaAgent` 技术，在工程启动加载时候进行修改字节码。这里为了将关于字节码核心内容展示出来，通过加载类名称获取字节码进行修改。

>这是修改之前的方法

```java 
public Integer strToNumber(String str) {
    return Integer.parseInt(str);
}
```    

>这是修改之后的方法

```java
public Integer strToNumber(String str) {
    try {
        Integer var2 = Integer.parseInt(str);
        MethodTest.point("org.itstack.test.MethodTest$Test.strToNumber", var2);
        return var2;
    } catch (Exception var3) {
        MethodTest.point("org.itstack.test.MethodTest$Test.strToNumber", var3);
        throw var3;
    }
}
```   

从修改前到修改后，可以看到。有如下几点修改；
1. 返回值赋值给新的参数，并做了输出
2. 把方法包裹在一个 `TryCatch` 中，并将异常也做了输出

好！如果你有很敏锐的嗅觉，或者很多小问号。那么你是否会想到如果使用到你自己的业务中，是不是就可以做一套非入侵的监控系统了？ ~~之后升职加薪~~

## 四、实现过程

字节码增强的过程乍一看还是比较麻烦的，如果你没有阅读过JVM虚拟机规范等相关书籍，确实很不好理解。但是也就是这部分不那么容易理解的知识，才是你后续价值的体现。

接下来我会一步步的带着你通过字节码增强的方式，来实现我们的监控需求。最终的完整的代码，可以通过关注**公众号**：`bugstack虫洞栈` 回复`源码`获取。

### 1. 搭建字节码框架

```java
/**
 * 字节码增强获取新的字节码
 */
private byte[] getBytes(String className) throws IOException {

    ClassReader cr = new ClassReader(className);
    ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
    cr.accept(new ClassVisitor(ASM5, cw) {

        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {

            // 方法过滤
            if (!"strToNumber".equals(name))
                return super.visitMethod(access, name, descriptor, signature, exceptions);

            MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

            return new AdviceAdapter(ASM5, mv, access, name, descriptor) {
                
                // 方法进入时修改字节码                                          
                protected void onMethodEnter() {}
                 
                // 访问局部变量和操作数栈
                public void visitMaxs(int maxStack, int maxLocals) {}
                
                // 方法退出时修改字节码  
                protected void onMethodExit(int opcode) {}

            };
        }
    }, ClassReader.EXPAND_FRAMES);

    return cw.toByteArray();
}
```

以上这段代码就是 `ASM` 用于处理字节码增强的模版代码块。首先他会分别创建 `ClassReader`、`ClassWriter`，用于对类的加载和写入，这里的加载方式在构造方法中也提供的比较丰富。可以通过类名、字节码或者流的方式进行处理。

接下来是对方法的访问 `MethodVisitor` ，基本所有使用 `ASM` 技术的监控系统，都会在这里来实现字节码的注入。这里面目前用到了三个方法的，如下；
1. `onMethodEnter` 方法进入时设置一些基本内容，比如当前纳秒用于后续监控方法的执行耗时。还有就是一些 `Try` 块的开始。
2. `visitMaxs` 这个是在方法结束前，用于添加 `Catch` 块。到这也就可以将整个方法进行包裹起来了。
3. `onMethodExit` 最后是这个方法退出时，用于 `RETURN` 之前，可以注入结尾的字节码加强，比如调用外部方法输出监控信息。

基本上所有的 `ASM` 字节码增强操作，都离不开这三个方法。下面我就一步步来用指令将方法改造。

### 2. 获取方法返回值

**这是一个被测试的方法；**

```java
public Integer strToNumber(String str) {
    return Integer.parseInt(str);
}
```

**编写指令**

这个 `onMethodExit` 方法就是我们上面提到的字节码编写框架中的内容，在里面添加具体的字节码指令。

```java 
@Override
protected void onMethodExit(int opcode) {
    if ((IRETURN <= opcode && opcode <= RETURN) || opcode == ATHROW) {
        int nextLocal = this.nextLocal;
        mv.visitVarInsn(ASTORE, nextLocal); // 将栈顶引用类型值保存到局部变量indexbyte中。
        mv.visitVarInsn(ALOAD, nextLocal);  // 从局部变量indexbyte中装载引用类型值入栈。
    }
}
```

1. **this.nextLocal**，获取局部变量的索引值。这个值就让局部变量最后的值，也就是存放 `ARETURN` 的值(*ARETURN*，是返回对象类型，如果是返回 `int` 则需要使用 *IRETURN*)。
2. **ASTORE**，将栈顶引用类型值保存到局部变量indexbyte中。这里就是把返回的结果，保存到局部变量。*在你头脑中可以想象这有两块区域，一个是局部变量、一个是操作数栈。他们不断的进行压栈和操作*。
3. **ALOAD**，从局部变量indexbyte中装载引用类型值入栈。现在再将这个值放到操作数栈用，用于一会输出使用。

**被初次增强后的方法；**

```java
public Integer strToNumber(String str) {
    Integer var2 = Integer.parseInt(str);
    return var2;
}
```

- 首先可以看到，原本的返回值被赋值到一个参数上，之后再由 `return` 将参数返回。这样也就可以让我们拿到了方法出参 `var2` 进行输出操作。

### 3. 输出方法返回值

在上面我们已经将返回内容赋值给参数，那么在 `return` 之前，我们就可以在添加一个方法来输出方法信息和出参了。

**定义输出结果方法；**

```java
public static void point(String methodName, Object response) {
    System.out.println("系统监控 :: [方法名称：" + methodName + " 输出信息：" + JSON.toJSONString(response) + "]\r\n");
}
```      

接下来我们使用字节码增强的方式来调用这个静态方法。

```java 
@Override
protected void onMethodExit(int opcode) {
    if ((IRETURN <= opcode && opcode <= RETURN) || opcode == ATHROW) {
        ...
        
        mv.visitLdcInsn(className + "." + name);  // 类名.方法名
        mv.visitVarInsn(ALOAD, nextLocal);
        mv.visitMethodInsn(INVOKESTATIC, Type.getInternalName(MethodTest.class), "point", "(Ljava/lang/String;Ljava/lang/Object;)V", false);
    }
}
```   

1. **mv.visitLdcInsn(className + "." + name);**，常量池中的常量值（int, float, string reference, object reference）入栈。也就是我们把类名和方法名，写到常量池中。
2. **mv.visitVarInsn(ALOAD, nextLocal);**，将上面我们提到的返回值加载到操作数栈。
3. **mv.visitMethodInsn(INVOKESTATIC, Type.getInternalName(MethodTest.class), "point", "(Ljava/lang/String;Ljava/lang/Object;)V", false);**，调用静态方法。`INVOKESTATIC` 是调用指令，后面是方法的地址、方法名、方法描述。
4. `(Ljava/lang/String;Ljava/lang/Object;)V`，表示 `String` 和 `Object` 类型的入参，`V` 是返回空。整体看也就是我们的方法；`void point(String methodName, Object response)`

**再次被增强后的方法；**

```java
public Integer strToNumber(String str) {
 Integer var2 = Integer.parseInt(str);
 point("org.itstack.test.MethodTest.strToNumber", var2);
 return var2;
}
```   

- 在字节码增强后的方法，每次调用这个方法都会输出方法的名称和出参结果。可能还有一个问题就是，*如果抛异常了，那么就监控不到了！*

### 4. 给方法加上TryCatch

如果需要抓住方法的异常信息并输出，那么就需要给原有的方法包上一层 `TryCatch` 捕获异常。接下来我们开始完成这样的指令码操作。

**添加 `TryCatch` 开始**

```java 
private Label from = new Label(),
        to = new Label(),
        target = new Label();

@Override
protected void onMethodEnter() {
    //标志：try块开始位置
    visitLabel(from);
    visitTryCatchBlock(from,
            to,
            target,
            "java/lang/Exception");
}
``` 

- 在 `onMethodEnter()` 中，加入 `TryCatch` 开始块，在部分在 `ASM` 中固定的模式，按照需求添加即可。

**添加 `TryCatch` 结尾**

```java 
@Override
public void visitMaxs(int maxStack, int maxLocals) {
    //标志：try块结束
    mv.visitLabel(to);

    //标志：catch块开始位置
    mv.visitLabel(target);
    mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Exception"});  

    // 异常信息保存到局部变量
    int local = newLocal(Type.LONG_TYPE);
    mv.visitVarInsn(ASTORE, local);
 
    // 抛出异常
    mv.visitVarInsn(ALOAD, local);
    mv.visitInsn(ATHROW);
    super.visitMaxs(maxStack, maxLocals);
}
```  

- 在 `visitMaxs` 方法中完成 `TryCatch` 的结尾，包住异常请抛出。
- `mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Exception"});`，在指定方法操作数栈中将 `TryCatch` 处理完成。这里面的几个参数也可以动态拼装；局部变量、参数、栈、异常。
- `ASTORE`，将异常信息保存到局部变量，并使用指定 `ALOAD` 放到操作数栈，用于抛出。
- `ATHROW`，最后是抛出异常的指令，也就是 `throw var`;

**这次增强后的方法；**

```java 
public Integer strToNumber(String str) {
    try {
        Integer var2 = Integer.parseInt(str);
        point("org.itstack.test.MethodTest.strToNumber", var2);
        return var2;
    } catch (Exception var3) {
        throw var3;
    }
}
```   

- 这时离我们要的内容越来越近了，整个方法被包装到一个 `TryCatch` 中，并按照需要输出我们的信息。接下来就需要将异常信息，打印出来。

### 5. 输出异常信息

在我们使用 `ASM` 字节码增强后，已经可以将方法拓展的非常的适合于监控了。接下来我们定义一个静态方法，用于输出异常信息；

**定义输出异常方法；**

```java
public static void point(String methodName, Throwable throwable) {
    System.out.println("系统监控 :: [方法名称：" + methodName + " 异常信息：" + throwable.getMessage() + "]\r\n");
}
```     

接下来的事情就很简单了，只需要在抛出异常的指令中，把调用外部方法的内容集成进去就可以了。

```java
@Override
public void visitMaxs(int maxStack, int maxLocals) {
    ...
    // 输出信息
    mv.visitLdcInsn(className + "." + name);  // 类名.方法名
    mv.visitVarInsn(ALOAD, local);
    mv.visitMethodInsn(INVOKESTATIC, Type.getInternalName(MethodTest.class), "point", "(Ljava/lang/String;Ljava/lang/Throwable;)V", false);
    
    ...
}
```    

- 这一部分主要体现将异常信息进行输出，通过字节码指令来实现调用外部方法。
- `mv.visitLdcInsn`，加载常量。也就是类名和方法名。
- `ALOAD`，将异常信息加载到操作数栈用，用于输出。
- `INVOKESTATIC`，调用静态方法。调用方法除了这个指令外还有；`invokespecial`、`invokevirtual`、`invokeinterface`。

**现在再看字节码增强后的方法；**

```java
public Integer strToNumber(String str) {
    try {
        Integer var2 = Integer.parseInt(str);
        point("org.itstack.test.MethodTest.strToNumber", (Object)var2);
        return var2;
    } catch (Exception var3) {
        point("org.itstack.test.MethodTest.strToNumber", (Throwable)var3);
        throw var3;
    }
}
```   

好！到这我们已经将这个方法彻底的通过字节码改造完成，可以非常方便的监控异常信息。对用外部输出的方法，后续可以通过 `MQ` 等机制推送出去，用于图表展示监控信息。

## 五、测试验证

这是一个字符串转换成数字类型的方法，我们通过调用传输不同的参数进行验证。比如；数字类型字符串和非数字类型字符串。

另外这里是我们通过字节码增强的方式进行改造方法，改造后这个方法反馈给我们的仍然是字节码，所以需要使用到 `ClassLoader` 进行加载到执行。

**测试方法；**

```java
public static void main(String[] args) throws Exception {
    // 方法字节码增强
    byte[] bytes = new MethodTest().getBytes(MethodTest.class.getName());
    // 输出方法新字节码
    outputClazz(bytes, MethodTest.class.getSimpleName());    

    // 测试方法
    Class<?> clazz = new MethodTest().defineClass("org.itstack.test.MethodTest", bytes, 0, bytes.length);
    Method queryUserInfo = clazz.getMethod("strToNumber", String.class);            

    // 正确入参；测试验证结果输出
    Object obj01 = queryUserInfo.invoke(clazz.newInstance(), "123");
    System.out.println("01 测试结果：" + obj01);   

    // 异常入参；测试验证打印异常信息
    Object obj02 = queryUserInfo.invoke(clazz.newInstance(), "abc");
    System.out.println("02 测试结果：" + obj02);
}
```     

**输出结果；**

```java 
ASM字节码增强后类输出路径：/User/itstack/git/github.com/WormholePistachio/SQM/target/test-classes/MethodTestSQM.class

系统监控 :: [方法名称：org.itstack.test.MethodTest.strToNumber 输出信息：123]

01 测试结果：123
系统监控 :: [方法名称：org.itstack.test.MethodTest.strToNumber 异常信息：For input string: "abc"]         
    
Process finished with exit code 1
```                

![ASM字节码增强，演示效果](https://bugstack.cn/assets/images/2020/itstack-demo-asm-03-02.gif)

## 六、总结

- 通过字节码指令控制代码的编写注入，是不是很酷？完成功能的同时，逐步也解了 `JVM虚拟机` 。至少不向以前那样只是去硬背一些理论，而是彻底的实践了。不要感觉这很难，嗯！
- 在逐步的了解字节码编程后，你会在很多的场景领域中建设出高级的玩法。甚至去翻看源码也能更加容易阅读理解，并把这技巧复用给自己其他系统。
- 比如我们常用的非入侵的监控系统，全链路监控，以及一些反射框架中，其实都用到了 `ASM`，只是还没有注意到而已。最终多学习一些延申拓展的知识，关于这些技巧可以阅读 `JVM虚拟机规范`，也可以阅读ASM文档；[asm.itstack.org](http://asm.itstack.org/#/) 

## 七、彩蛋

最近将个人原创代码库资源整理出一份 `wiki` 文档，同时逐步将各类案例汇总集中，方便获取。

本代码库是作者小傅哥多年从事一线互联网Java开发的学习历程技术汇总，旨在为大家提供一个清晰详细的学习教程，侧重点更倾向编写Java核心内容。如果本仓库能为您提供帮助，请给予支持(关注、点赞、分享，给个Star ✨)！

**链接**：[https://github.com/fuzhengwei/CodeGuide/wiki](https://github.com/fuzhengwei/CodeGuide/wiki)

![CodeGuide Wiki，程序员编码指南](https://bugstack.cn/assets/images/2020/itstack-demo-asm-03-03.png)

