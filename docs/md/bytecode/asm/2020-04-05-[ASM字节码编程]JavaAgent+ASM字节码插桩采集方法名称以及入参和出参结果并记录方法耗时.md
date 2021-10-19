---
layout: post
category: itstack-demo-agent
title: 二、JavaAgent+ASM字节码插桩采集方法名称以及入参和出参结果并记录方法耗时
tagline: by 付政委
tag: [itstack-demo-bytecode]
excerpt: 在我们实际的业务开发到上线的过程中，中间都会经过测试。那么怎么来保证测试质量呢？比如；提交了多少代码、提交了多少方法、有单元测试吗、影响了那些流程链路、有没有夹带上线。
lock: need
---

# ASM字节码编程 | JavaAgent+ASM字节码插桩采集方法名称以及入参和出参结果并记录方法耗时

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、前言

在我们实际的业务开发到上线的过程中，中间都会经过测试。那么怎么来保证测试质量呢？比如；提交了多少代码、提交了多少方法、有单元测试吗、影响了那些流程链路、有没有夹带上线。

大部分时候这些问题的汇总都是人为的方式进行提供，以依赖相信研发为主。剩下的就需要依赖有经验的测试进行白盒验证。所以即使是这样测试也会在上线后发生很多未知的问题，毕竟流程太长，影响面太广。很难用一个人去照顾到所有流程。

**所以**，我很希望使用技术手段来解决这一问题，通过服务质量监控来在研发提测后，自动报告相关数据，例如；研发代码涉及流程链路展示、每个链路测试次数、通过次数、失败次数、当时的出入参信息以及对应的代码块在当前提测分支修改记录等各项信息。最终测试在执行验证时候，分配验证渠道扫描到所有分支节点，可以清晰的看到全链路的影响。那么，这样的测试才是可以保证系统的整体质量的。

好！接下来到后续一段时间，我会不断的去完善和开发这些功能。也欢迎你的加入！

## 二、技术目标

**`技术行为`都是为目标服务的，也就是实现务`产品功能`。**

而我们这个文章的目标是需要使用固定的技术栈 ```JavaAgent``` + ```ASM```，来抓取方法执行时候的信息，包括：类名称、方法名称、入参信息和入参值、出参信息和出参值以及当前方法的耗时。

**JavaAgent**，是一种探针技术可以通过 `premain` 方法，在类加载的过程中给指定的方法进行字节码增强。其实你的每一个类最终都是字节码指令的执行，而这种增强后的方法就可以输出我们想要的信息。这就相当于你硬编码时候输出了一些方法的耗时，日志等信息。

**ASM**，是一个 Java 字节码操控框架。它能被用来动态生成类或者增强既有类的功能。ASM 可以直接产生二进制 class 文件，也可以在类被加载入 Java 虚拟机之前动态改变类行为。Java class 被存储在严格格式定义的 .class 文件里，这些类文件拥有足够的元数据来解析类中的所有元素：类名称、方法、属性以及 Java 字节码（指令）。ASM 从类文件中读入信息后，能够改变类行为，分析类信息，甚至能够根据用户要求生成新类。说白了asm是直接通过字节码来修改class文件。另外除了 asm 可以操作字节码，还有javassist和Byte-code等，他们比 asm 要简单，但是执行效率还是 asm 高。因为 asm 是直接使用指令来控制字节码。

## 三、实现方案

![字节码增强实现方案](https://bugstack.cn/assets/images/2020/itstack-demo-asm-02-1.png)

按照图中我们使用 `javaAgent` 的 `primain` 方法，使用 `asm` 进行字节码增强，以便于输出我们的监控信息。最终在我们把字节码增强后，程序所执行的就是我们的新的方法字节码，从而也就可以获取到我们需要的信息。那么，接下来我们开始一步步上线这些功能。

**关于实现方案中的所有源码，可以通过关注公众号：`bugstack虫洞栈`，回复源码下载进行获取**

### 1. 定义测试方法

```java
public class ApiTest {

    public static void main(String[] args) throws InterruptedException {
        ApiTest apiTest = new ApiTest();
        String res01 = apiTest.queryUserInfo(111, 17);
        System.out.println("测试结果：" + res01 + "\r\n");;
    }

    public String queryUserInfo(int uId, int age) throws InterruptedException {
        return "你好，bugstack虫洞栈 | 精神小伙！";
    }

}
```

- 这里我们定义了一个查询用户信息的测试方法，后续不断将这个方法进行字节码增强。

### 2. 监控类入口

>PreMain.java & 入口方法

```java
public class PreMain {

    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ProfilingTransformer());
    }

    //如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
    public static void premain(String agentArgs) {
    }

}
```

>MANIFEST.MF & 配置

```java
Manifest-Version: 1.0
Premain-Class: org.itstack.sqm.asm.PreMain
Can-Redefine-Classes: true
```

-  以上是固定的基础模板代码，所有的 `JavaAgent` 程序都需要从这里开始。

### 3. 字节码方法处理

```java

public class ProfilingTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {

        	// 排除一些不需要处理的方法
            if (ProfilingFilter.isNotNeedInject(className)) {
                return classfileBuffer;
            }

            return getBytes(loader, className, classfileBuffer);;
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        return classfileBuffer;
    }

    ...

}

```

- 这里主要通过传入进行的类加载器、类名、字节码等，负责字节码的增强操作。而这里会使用 `ASM` 方式进行处理，如下；

	```java
	private byte[] getBytes(ClassLoader loader, String className, byte[] classfileBuffer) {
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ProfilingClassAdapter(cw, className);
        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        return cw.toByteArray();
    }
	```

    - 关于 `ASM` 的使用可以通过文档学习；[asm.itstack.org](http://asm.itstack.org)


### 4. 字节码方法解析

![字节码方法解析](https://bugstack.cn/assets/images/2020/itstack-demo-asm-02-2.png)

- 当程序启动加载的时候，每个类的每一个方法都会被监控到。类的名称、方法的名称、方法入参出参的描述等，都可以在这里获取。
- 为了可以在后续监控处理不至于每一次都去传参（方法信息）浪费消耗性能，一般这里都会给每个方法生产一个全局防重的 `id` ，通过这个 `id` 就可以查询到对应的方法。
- 另外从这里可以看到的方法的入参和出参被描述成一段指定的码，```(II)Ljava/lang/String;``` ，为了我们后续对参数进行解析，那么需要将这段字符串进行拆解。

#### 4.1 解析方法入参和出参

在 `asm` 文档中说明过关于字节码结构和方法的信息，`I；int、Ljava/lang/String；String`，所以我们可以分析出这个方法的是两个 `int` 类型的入参和一个 `String` 类型的出参。也就是；`String queryUserInfo(int uId, int age)`

那么这个方法的入参除了这么简单的，还会很复杂的，比如：`(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;IJ[I[[Ljava/lang/Object;Lorg/itstack/test/Req;)Ljava/lang/String;` 对于这样的字符串内容需要使用到正则表达式进行解析。

>正则解析方法描述

```java
@Test
public void test_desc() {
    String desc = "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;IJ[I[[Ljava/lang/Object;Lorg/itstack/test/Req;)Ljava/lang/String;";

    Matcher m = Pattern.compile("(L.*?;|\\[{0,2}L.*?;|[ZCBSIFJD]|\\[{0,2}[ZCBSIFJD]{1})").matcher(desc.substring(0, desc.lastIndexOf(')') + 1));

    while (m.find()) {
        String block = m.group(1);
        System.out.println(block);
    }

}
```

**测试结果**

```java
Ljava/lang/String;
Ljava/lang/Object;
Ljava/lang/String;
I
J
[I
[[Ljava/lang/Object;
Lorg/itstack/test/Req;

Process finished with exit code 0
```

- 可以看到我们将所有的参数类型已经解析出来，因为只有通过这样的解析我们才能去处理方法中入参。这主要是8个基本类型需要进行类型转换为对象，填充到数组中，方便我们输出结果。


#### 4.2 提取类和方法生产标识ID
 
接下来我们将解析的方法信息包括入参、出参结果生产方法的标识ID，这个ID是一个全局唯一的，每一个方法都有一个固定的标识。如下；

```java
methodId = ProfilingAspect.generateMethodId(new MethodTag(fullClassName, simpleClassName, methodName, desc, parameterTypeList, desc.substring(desc.lastIndexOf(')') + 1)));

public static int generateMethodId(MethodTag tag) {
    int methodId = index.getAndIncrement();
    if (methodId > MAX_NUM) return -1;
    methodTagArr.set(methodId, tag);
    return methodId;
}
```

- 这是一个原子性用户自增的ID，`AtomicInteger`，同时也提供了一个对应的集合；`AtomicReferenceArray<MethodTag>`
- 当我们每添加一个方法就会使用这个工具生产一个对应的ID，同时存放到集合中，并返回。这个生成的过程是一次性的，所以也不会影响执行时候的耗时。 

### 5. 字节码增强「方法进入」

在 ```ProfilingMethodVisitor extends AdviceAdapter``` 中，可以重写方法 `onMethodEnter` 。也就是当方法进入时候设置开始时间和收集入参到数组中。而收集入参的过程相对会复杂一些，需要使用字节码指令创建数据，之后把每一个入参在使用字节码加载到数组中。这个过程有点像我们写代码，定义数组设置参数。

#### 5.1 在方法里设置开始时间


**这段代码我们需要使用字节码指令插桩到方法的开始处**

```java
long var3 = System.nanoTime();
```

**字节码插桩处理**

```java
mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
startTimeIdentifier = newLocal(Type.LONG_TYPE);
mv.visitVarInsn(LSTORE, startTimeIdentifier);	
```

| 字节码 | 描述 |
|:---|:---|
| INVOKESTATIC | 调用静态方法 |
| LSTORE | 将栈顶long类型值保存到局部变量indexbyte中 |

#### 5.2 初始化入参装填数组

**使用字节码的方式去初始化一个参数数量的数组**

```java
Object[] var6 = new Object[](x);
```

**通过字节码的方式进行创建数组**

```java
if (parameterCount >= 4) {
    mv.visitVarInsn(BIPUSH, parameterCount);//初始化数组长度
} else {
    switch (parameterCount) {
        case 1:
            mv.visitInsn(ICONST_1);
            break;
        case 2:
            mv.visitInsn(ICONST_2);
            break;
        case 3:
            mv.visitInsn(ICONST_3);
            break;
        default:
            mv.visitInsn(ICONST_0);
    }
}
mv.visitTypeInsn(ANEWARRAY, Type.getDescriptor(Object.class));
```

| 字节码 | 描述 |
|:---|:---|
| BIPUSH | valuebyte值带符号扩展成int值入栈 |
| ANEWARRAY | 创建引用类型的数组 |

**这里有一个数组大小的判断**，如果小于4会使用 `ICONST` 初始化长度。

#### 5.3 给数组赋值

**给数组赋值相当于如下效果，只不过需要经过一些字节码的方式进行处理**

```java
Object[] var6 = new Object[]{var1, var2};
```

**通过字节码的方式进行初始化**

```java
 // 给数组赋参数值
for (int i = 0; i < parameterCount; i++) {
    mv.visitInsn(DUP);
    mv.visitVarInsn(BIPUSH, i);
    String type = parameterTypeList.get(i);
	if ("Z".equals(type)) {
	    mv.visitVarInsn(ILOAD, ++cursor);  //获取对应的参数
	    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
	} else if ("C".equals(type)) {
	    mv.visitVarInsn(ILOAD, ++cursor);  //获取对应的参数
	    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
	} else if ("B".equals(type)) {
	    mv.visitVarInsn(ILOAD, ++cursor);  //获取对应的参数
	    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
	} else if ("S".equals(type)) {
	    mv.visitVarInsn(ILOAD, ++cursor);  //获取对应的参数
	    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
	} else if ("I".equals(type)) {
	    mv.visitVarInsn(ILOAD, ++cursor);  //获取对应的参数
	    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
	} else if ("F".equals(type)) {
	    mv.visitVarInsn(FLOAD, ++cursor);  //获取对应的参数
	    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
	} else if ("J".equals(type)) {
	    mv.visitVarInsn(LLOAD, ++cursor);  //获取对应的参数
	    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
	} else if ("D".equals(type)) {
	    cursor += 2;
	    mv.visitVarInsn(DLOAD, cursor);  //获取对应的参数
	    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
	} else {
	    ++cursor;
	    mv.visitVarInsn(ALOAD, cursor);  //获取对应的参数
	}
	mv.visitInsn(AASTORE);

	mv.visitVarInsn(ASTORE, parameterIdentifier);
}
```

*这里在赋值的过程中，包括了对基本类型的转换，否则是不能放入到的 Object 数组中的。因为它们 `int` `long` ... 都不是对象类型*

| 字节码 | 描述|
|:---|:---|
| ILOAD | 从局部变量indexbyte中装载int类型值入栈 |
| INVOKESTATIC | 调用静态方法 |
| AASTORE | 将栈顶引用类型值保存到指定引用类型数组的指定项 |

**到这为止**，我们就已经将参数初始化到数组中了，后面就可以将参数通过方法传递出去。

### 6. 字节码增强「方法退出」

在方法结束后这里还提供给我们一个退出的方法 `onMethodExit` ，我们可以通过这个方法的重写，使用字节码获取出参并一起输出到外部。

#### 6.1 获取 `return` 出参值

**通过字节码的方式，实现下面出参赋值给一个属性，并最终把值给 `return`**

```java
Object var7 = "你好，bugstack虫洞栈 | 精神小伙！";
ProfilingAspect.point(var3, 0, var6, var7);
return uId;
```

**通过字节码方式进行处理**

```java
switch (opcode) {
    case RETURN:
        break;
    case ARETURN:
        mv.visitVarInsn(ASTORE, ++localCount); // 6
        mv.visitVarInsn(ALOAD, localCount);    // 6
        break;
}
```

#### 6.2 最终将方法信息输出给外部

```java
mv.visitVarInsn(LLOAD, startTimeIdentifier);
mv.visitLdcInsn(methodId);
if (parameterTypeList.isEmpty()) {
    mv.visitMethodInsn(INVOKESTATIC, Type.getInternalName(ProfilingAspect.class), "point", "(JI)V", false);
} else {
    mv.visitVarInsn(ALOAD, parameterIdentifier);  // 5
    mv.visitVarInsn(ALOAD, localCount);           // 6
    mv.visitMethodInsn(INVOKESTATIC, Type.getInternalName(ProfilingAspect.class), "point", "(JI[Ljava/lang/Object;Ljava/lang/Object;)V", false);
}
```

- `LLOAD` ，从局部变量indexbyte中装载long类型值入栈。这里加载的就是方法的启动时间。
- `LDC` ， 常量池中的常量值（int, float, string reference, object reference）入栈。这里是加载方法ID；```methodId``` 。
- `ALOAD` ，parameterIdentifier ，从局部变量indexbyte中装载引用类型值入栈。此时加载参数数组信息。
- `ALOAD` ，localCount ，加载的是返回值信息，也就是 `return` 的结果。
- `INVOKESTATIC` ，最后就是调用静态方法输出结果信息，这个静态方法是我们已经预设好的，如下；
	
	```java
	public static void point(final long startNanos, final int methodId, Object[] requests, Object response) {
        MethodTag method = methodTagArr.get(methodId);
        System.out.println("监控 - Begin");
        System.out.println("类名：" + method.getFullClassName());
        System.out.println("方法：" + method.getMethodName());
        System.out.println("入参类型：" + JSON.toJSONString(method.getParameterTypeList()));
        System.out.println("入数[值]：" + JSON.toJSONString(requests));
        System.out.println("出参类型：" + method.getReturnParameterType());
        System.out.println("出参[值]：" + JSON.toJSONString(response));
        System.out.println("耗时：" + (System.nanoTime() - startNanos) / 1000000 + "(s)");
        System.out.println("监控 - End\r\n");
    }
	```

## 四、测试验证

### 1. 需要测试的方法

```java
public class ApiTest {

    public static void main(String[] args) throws InterruptedException {
        ApiTest apiTest = new ApiTest();
        String res01 = apiTest.queryUserInfo(111, 17);
        System.out.println("测试结果：" + res01 + "\r\n");;
    }

    public String queryUserInfo(int uId, int age) throws InterruptedException {
        return "你好，bugstack虫洞栈 | 精神小伙！";
    }

}
```
### 2. 配置javaagent

```java
-javaagent:/Users/xiaofuge/itstack/git/github.com/SQM/target/SQM-1.0-SNAPSHOT.jar
```

- IDEA 运行时候配置到 `VM options` 中，jar包地址按照自己的路径进行配置。

### 3. 被字节码增强后的方法

```java
public String queryUserInfo(int var1, int var2) throws InterruptedException {
    long var3 = System.nanoTime();
    Object[] var6 = new Object[]{var1, var2};
    Object var7 = "你好，bugstack虫洞栈 | 精神小伙！";
    ProfilingAspect.point(var3, 0, var6, var7);
    return var7;
}
```

- 通过编译后的方法可以看到，方法的执行信息全部通过静态方法输出到外部。这样就可以很方便的监控一个方法的执行信息。

### 4. 输出结果

```java
ASM类输出路径：/Users/xiaofuge/itstack/git/github.com/SQM/target/test-classes/org/itstack/test/ApiTest$1SQM.class
监控 - Begin
类名：org.itstack.test.ApiTest
方法：queryUserInfo
入参类型：["I","I"]
入数[值]：[111,17]
出参类型：Ljava/lang/String;
出参[值]："你好，bugstack虫洞栈 | 精神小伙！"
耗时：95(s)
监控 - End

测试结果：你好，bugstack虫洞栈 | 精神小伙！
```

## 五、总结

- 综上使用了 `JavaAgent` 结合 `ASM` 对监控方法做了字节码增强，可以在方法执行的时候输出我们需要的信息。而这些信息的价值就是可以很好的让我们做一些程序的全链路监控以及工程质量验证。
- 目前还是处于案例工程阶段，后续会不断突破一些技术难点，并完善服务质量监控工程，`SQM`。也欢迎有此爱好的小伙伴加入开源建设。也许这能让你除了平时的 `CRUD` 技术外，扩展一项更加高级的领域。
- 如果你对字节码插桩感兴趣，并还没有入门，可以通过我的博客；[bugstack.cn](https://bugstack.cn) 中，架构师专题->调用链路监控，学习。




