---
layout: post
category: itstack-demo-agent
title: 四、通过字节码插桩监控方法采集运行时入参出参和异常信息
tagline: by 付政委
tag: [itstack-demo-bytecode]
excerpt: 字节码编程插桩这种技术常与 Javaagent 技术结合用在系统的非入侵监控中，这样就可以替代在方法中进行硬编码操作。比如，你需要监控一个方法，包括；方法信息、执行耗时、出入参数、执行链路以及异常等。那么就非常适合使用这样的技术手段进行处理。
lock: need
---

# 字节码编程，Javassist篇四《通过字节码插桩监控方法采集运行时入参出参和异常信息》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、前言

字节码编程插桩这种技术常与 `Javaagent` 技术结合用在系统的非入侵监控中，这样就可以替代在方法中进行硬编码操作。比如，你需要监控一个方法，包括；方法信息、执行耗时、出入参数、执行链路以及异常等。那么就非常适合使用这样的技术手段进行处理。

为了能让这部分最核心的内容体现出来，本文会只使用 `Javassist` 技术对一段方法字节码进行插桩操作，最终输出这段方法的执行信息，如下；

**方法** - 测试方法用于后续进行字节码增强操作

```java 
public Integer strToInt(String str01, String str02) {
    return Integer.parseInt(str01);
}
```

**监控** - 对一段方法进行字节码增强后，输出监控信息

```java 
监控 - Begin
方法：org.itstack.demo.javassist.ApiTest.strToInt
入参：["str01","str02"] 入参[类型]：["java.lang.String","java.lang.String"] 入数[值]：["1","2"]
出参：java.lang.Integer 出参[值]：1
耗时：59(s)
监控 - End
```        

有了这样的监控方案，基本我们可以输出方法执行过程中的全部信息。再通过后期的完善将监控信息展示到界面，实时报警。既提升了系统的监控质量，也方便了研发排查并定位问题。

好！那么接下来我们开始一步步使用 `javassist` 进行字节码插桩，已达到我们的监控效果。

## 二、开发环境

1. JDK 1.8.0
2. javassist 3.12.1.GA
3. 本章涉及源码在：`itstack-demo-bytecode-1-04`，可以关注**公众号**：[`bugstack虫洞栈`](https://bugstack.cn/assets/images/qrcode.png)，回复*源码下载*获取。`你会获得一个下载链接列表，打开后里面的第17个「因为我有好多开源代码」`，记得给个`Star`！

## 三、技术实现

### 1. 获取方法基础信息

#### 1.1 获取类

```java
ClassPool pool = ClassPool.getDefault();
// 获取类
CtClass ctClass = pool.get(org.itstack.demo.javassist.ApiTest.class.getName());
ctClass.replaceClassName("ApiTest", "ApiTest02");
String clazzName = ctClass.getName();
```     

*通过类名获取类的信息*，同时这里可以把类名进行替换。它也包括类里面一些其他获取属性的操作，比如；`ctClass.getSimpleName()`、`ctClass.getAnnotations()` 等。

#### 1.2 获取方法

```java 
CtMethod ctMethod = ctClass.getDeclaredMethod("strToInt");
String methodName = ctMethod.getName();
``` 

通过 *getDeclaredMethod* 获取方法的 `CtMethod` 的内容。之后就可以获取方法的名称等信息。

#### 1.3 方法信息

```java 
MethodInfo methodInfo = ctMethod.getMethodInfo();
```    

*MethodInfo* 中包括了方法的信息；名称、类型等内容。

#### 1.4 方法类型

```java 
boolean isStatic = (methodInfo.getAccessFlags() & AccessFlag.STATIC) != 0;
```

通过 `methodInfo.getAccessFlags()` 获取方法的标识，之后通过 *与运算*，`AccessFlag.STATIC`，判断方法是否为静态方法。因为静态方法会*影响*后续的参数名称获取，静态方法第一个参数是 `this` ，需要排除。

#### 1.5 方法：入参信息{名称和类型}

```java 
CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
CtClass[] parameterTypes = ctMethod.getParameterTypes();
```

- LocalVariableAttribute，获取方法的入参的名称。
- parameterTypes，获取方法入参的类型。

#### 1.6 方法；出参信息

```java 
CtClass returnType = ctMethod.getReturnType();
String returnTypeName = returnType.getName();
```

*对于方法的出参信息，只需要获取出参类型。*

#### 1.7 输出所有获取的信息

```java 
System.out.println("类名：" + clazzName);
System.out.println("方法：" + methodName);
System.out.println("类型：" + (isStatic ? "静态方法" : "非静态方法"));
System.out.println("描述：" + methodInfo.getDescriptor());
System.out.println("入参[名称]：" + attr.variableName(1) + "，" + attr.variableName(2));
System.out.println("入参[类型]：" + parameterTypes[0].getName() + "，" + parameterTypes[1].getName());
System.out.println("出参[类型]：" + returnTypeName);
```    

**输出结果**

```java
类名：org.itstack.demo.javassist.ApiTest
方法：strToInt
类型：非静态方法
描述：(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Integer;
入参[名称]：str01，str02
入参[类型]：java.lang.String，java.lang.String
出参[类型]：java.lang.Integer
```       

**以上**，所输出信息，都在为监控方法在做准备。从上面可以记录方法的基本描述以及入参个数等。尤其是入参个数，因为在后续还需要使用 `$1`，来获取没有给入参的值。

### 2. 方法字节码插桩

一段需会被字节码插桩改变的原始方法；

```java 
public class ApiTest {

    public Integer strToInt(String str01, String str02) {
        return Integer.parseInt(str01);
    }

}
```

#### 2.1 先给基础属性打标

**在监控的适合**，不可能每一次调用都把所有方法信息汇总输出出来。这样做不只是性能问题，而是这些都是固定不变的信息，没有必要让每一次方法执行都输出。

好！那么在方法编译时候，给每一个方法都生成一个唯一`ID`，用`ID`关联上方法的固定信息。也就可以把监控数据通过`ID`传递到外面。

```java 
// 方法：生成方法唯一标识ID
int idx = Monitor.generateMethodId(clazzName, methodName, parameterNameList, parameterTypeList, returnTypeName);
```

**生成ID的过程**

```java 
public static final int MAX_NUM = 1024 * 32;
private final static AtomicInteger index = new AtomicInteger(0);
private final static AtomicReferenceArray<MethodDescription> methodTagArr = new AtomicReferenceArray<>(MAX_NUM);   

public static int generateMethodId(String clazzName, String methodName, List<String> parameterNameList, List<String> parameterTypeList, String returnType) {
    MethodDescription methodDescription = new MethodDescription();
    methodDescription.setClazzName(clazzName);
    methodDescription.setMethodName(methodName);
    methodDescription.setParameterNameList(parameterNameList);
    methodDescription.setParameterTypeList(parameterTypeList);
    methodDescription.setReturnType(returnType); 

    int methodId = index.getAndIncrement();
    if (methodId > MAX_NUM) return -1;
    methodTagArr.set(methodId, methodDescription);
    return methodId;
}
```       

#### 2.2 字节码插桩添加进入方法时间

```java
// 定义属性
ctMethod.addLocalVariable("startNanos", CtClass.longType);
// 方法前加强
ctMethod.insertBefore("{ startNanos = System.nanoTime(); }");
```       

- 定义一个 `long` 类型的属性，`startNanos`。并通过 `insertBefore` 插入到方法内容的开始处。

**最终 `class` 类方法**

```java 
public class ApiTest {     

    public Integer strToInt(String str01, String str02) {
        long startNanos = System.nanoTime();
        return Integer.parseInt(str01);
    }
}
```   

- 此时已经有了一个方法的开始时间，有了开始时间在加上后续的结尾时间。就可以很方便的统计一个方法的执行耗时。

#### 2.3 字节码插桩添加入参输出

```java 
// 定义属性
ctMethod.addLocalVariable("parameterValues", pool.get(Object[].class.getName()));
// 方法前加强
ctMethod.insertBefore("{ parameterValues = new Object[]{" + parameters.toString() + "}; }");
```

- 这里定义一个数组类型的属性，`Object[]`，用于记录入参信息。

**最终 `class` 类方法**

```java 
public Integer strToInt(String str01, String str02) {
    Object[] var10000 = new Object[]{str01, str02};
    long startNanos = System.nanoTime();
    return Integer.parseInt(str01);
}
```   

- 两个参数可以通过一条 `insertBefore` 进行插入，这里是为了更加清晰的向你展示字节码插桩的过程。现在我们就有了进入方法的时间和参数集合，方便后续输出。

#### 2.4 定义监控方法

**因为我们需要将监控信息**，输出给外部。那么我们这里会定义一个静态方法，让字节码增强后的方法去调用，输出监控信息。

```java 
public static void point(final int methodId, final long startNanos, Object[] parameterValues, Object returnValues) {
    MethodDescription method = methodTagArr.get(methodId);
    System.out.println("监控 - Begin");
    System.out.println("方法：" + method.getClazzName() + "." + method.getMethodName());
    System.out.println("入参：" + JSON.toJSONString(method.getParameterNameList()) + " 入参[类型]：" + JSON.toJSONString(method.getParameterTypeList()) + " 入数[值]：" + JSON.toJSONString(parameterValues));
    System.out.println("出参：" + method.getReturnType() + " 出参[值]：" + JSON.toJSONString(returnValues));
    System.out.println("耗时：" + (System.nanoTime() - startNanos) / 1000000 + "(s)");
    System.out.println("监控 - End\r\n");
}     

public static void point(final int methodId, Throwable throwable) {
    MethodDescription method = methodTagArr.get(methodId);
    System.out.println("监控 - Begin");
    System.out.println("方法：" + method.getClazzName() + "." + method.getMethodName());
    System.out.println("异常：" + throwable.getMessage());
    System.out.println("监控 - End\r\n");
}
```    

- 这里一共有两个方法，一个用于记录正常情况下的监控信息。另外一个用于记录异常时候的信息。如果是实际的业务场景中，就可以通过这样的方法使用 `MQ` 将监控信息发送给服务端记录起来并做展示。

#### 2.5 字节码插桩调用监控方法

```java 
// 方法后加强
ctMethod.insertAfter("{ org.itstack.demo.javassist.Monitor.point(" + idx + ", startNanos, parameterValues, $_);}", false); // 如果返回类型非对象类型，$_ 需要进行类型转换
```

- 这里通过静态方法将监控参数传递给外部；`idx`、`startNanos`、`parameterValues`、`$_`*出参值*

**最终 `class` 类方法**

```java 
public Integer strToInt(String str01, String str02) {
    Object[] parameterValues = new Object[]{str01, str02};
    long startNanos = System.nanoTime();
    Integer var7 = Integer.parseInt(str01);
    Monitor.point(0, startNanos, parameterValues, var7);
    return var7;
}
```

- 现在已经可以将基本的监控信息传递给外部。对于一个普通的监控，如果不需要追踪链路，基本已经可以满足需求了。

#### 2.6 字节码插桩给方法添加TryCatch

以上插桩内容，如果只是正常调用还是没问题的。但是如果方法抛出异常，那么这个时候就不能做到收集监控信息了。所以还需要给方法添加上 `TryCatch`。

```java 
// 方法；添加TryCatch
ctMethod.addCatch("{ org.itstack.demo.javassist.Monitor.point(" + idx + ", $e); throw $e; }", ClassPool.getDefault().get("java.lang.Exception"));   // 添加异常捕获
```

- 这里通过 `addCatch` 将方法包装在 `TryCatch` 里面。
- 再通过在 `catch` 中调用外部方法，将异常信息输出。
- 同时有一个点需要注意，`$e`，用于获取抛出异常的内容。

**最终 `class` 类方法**

```java 
public Integer strToInt(String str01, String str02) {
    try {
        Object[] parameterValues = new Object[]{str01, str02};
        long startNanos = System.nanoTime();
        Integer var7 = Integer.parseInt(str01);
        Monitor.point(0, startNanos, parameterValues, var7);
        return var7;
    } catch (Exception var9) {
        Monitor.point(0, var9);
        throw var9;
    }
}
```          

- 那么现在就可以非常完整的`收录方法执行的信息`，包括它的正常执行以及*异常*情况。

## 四、测试结果

接下来就是执行我们的调用测试被修改后的方法字节码。通过不同的入参，来验证监控结果；

```java 
// 测试调用
byte[] bytes = ctClass.toBytecode();
Class<?> clazzNew = new GenerateClazzMethod().defineClass("org.itstack.demo.javassist.ApiTest", bytes, 0, bytes.length);          

// 反射获取 main 方法
Method method = clazzNew.getMethod("strToInt", String.class, String.class);
Object obj_01 = method.invoke(clazzNew.newInstance(), "1", "2");
System.out.println("正确入参：" + obj_01);             

Object obj_02 = method.invoke(clazzNew.newInstance(), "a", "b");
System.out.println("异常入参：" + obj_02);
```

- 这里首先会使用 `ClassLoader` 加载字节码，之后生成新的类。
- 接下来通过获取方法并传入正确和错误的入参。

**测试结果**

```java 
监控 - Begin
方法：org.itstack.demo.javassist.ApiTest.strToInt
入参：["str01","str02"] 入参[类型]：["java.lang.String","java.lang.String"] 入数[值]：["1","2"]
出参：java.lang.Integer 出参[值]：1
耗时：63(s)
监控 - End

正确入参：1   

监控 - Begin
方法：org.itstack.demo.javassist.ApiTest.strToInt
异常：For input string: "a"
监控 - End
```    

- 截至到这我们已经将监控中最核心之一展示出来了，也就是监控方法的全部信息。后续就是需要将这样的监控信息填充到统一监控中心，进行做展示相关的计算操作。

## 五、总结

- 基于 `Javassist` 字节码操作框架可以非常方便的去进行字节码增强，也不需要考虑纯字节码编程下的指令码控制。但如果考虑性能以及更加细致的改变，还是需要使用到 `ASM` 。
- 这里包括一些字节码操作的知识点，如下；
    
    - `methodInfo.getDescriptor()`，可以输出方法描述信息。`(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Integer;`，其实就是方法的出入参和返回值。
    - `$1 $2 ...` 用于获取不同位置的参数。`$$` 可以获取全部入参，但是不太适合用在数值传递中。
    - 获取方法的入参需要判断方法的类型，静态类型的方法还包含了 `this` 参数。*AccessFlag.STATIC*。
    - `addCatch` 最开始执行就包裹原有方法内的内容，最后执行就包括所有内容。它依赖于顺序操作，其他的方法也是这样；`insertBefore`、`insertAfter`。







