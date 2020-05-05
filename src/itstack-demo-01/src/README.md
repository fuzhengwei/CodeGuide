# 汉字不能写代码？别闹了，只是看着有点豪横！容易被开除！

## 一、前言

`在编程的路上你是否想过，用汉字写一写代码？`

最近有初学编程的小伙伴问`小傅哥`，汉字可以写代码吗。自己英文不好，要是汉字可以写代码就好了。难道你要的是**易语言**？其实并不是，小伙伴也是学习 `Java` 的初学者，刚刚学习到 `Spring` 看着一片没有注释的代码实在不好理解，要是都是汉字写的，那不和读作文一样了吗！

说道注释，我想到大部分程序员讨厌的**两件事**；
1. 不喜欢写注释
2. 不喜欢别人不写注释

其实对于学习编程来说，初学时写写案例，完成简单的功能，反复练习夯实基础。数学和英文都还并不是你的绊脚石，因为你不需要做复杂的逻辑处理，比如算法。也不需要查阅大量的资料，比如原版的英文资料以及国内没有翻译的技术书籍等。所以这个时候对你来说，只是需要不断的学，不断的写。

回到我们的说的，既然你问汉字可以写代码吗，其实在 `java` 里，原则上你可以写汉字的`类`、`属性`、`方法`，`JVM虚拟机`也是可以通过编译执行的。只是这样的代码并不能很好的维护，甚至说乱码了也很麻烦。再者，说要写方言怎么办！

好！那么我们接下来就使用汉字的方式来编写一段关于 `SpringAop` 的案例！

## 二、开发环境

1. JDK 1.8.0
2. Spring 4.3.24.RELEASE
3. 本篇涉及的源码下载，可以关注**公众号**：[`bugstack虫洞栈`](https://bugstack.cn/assets/images/qrcode.png) 获取，并且还可以获取更多原创案例。

## 三、技术实现

为了这个案例更加真实，我们模拟`电影清朝韦小宝时期`，太监入宫的过程。说白了也就是 `Spring` 的 `AOP` 面向切面，`切`的编程。

在做案例之前，我们先了解一下 `AOP` 的基本概念；

1. `@Aspect`，定义切面的注解
2. `@Pointcut`，切入点，一般会在方法上设定通配符表达式
3. `@Around`，环绕，也就是你原本的方法会在这里处理
4. `@Before`，前置处理
5. `@After`，后置处理

### 1. 定义切面

>紫禁城.内务府.敬事房.膑.太监膑.净身监管.java

```java
@Aspect
@Component
public class 净身监管 {

    @Pointcut("execution(public * 紫禁城.内务府.敬事房.利器库..*.军刺切(..))")
    public void 监管员(){

    }

    @Before("监管员()")
    public void 敬事前(){
        System.out.println("敬事前:---------准备下刀... ...");
    }

    @After("监管员()")
    public void 敬事后(){
        System.out.println("敬事后:---------切面完成... ...");
    }

    @Around("监管员()")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {

        System.out.println("待切身份："+pjp.getArgs()[0]);
        System.out.println("执行工具："+pjp.getSignature().getName());

        //获得传递对象，并做处理
        太监膑 太监 = (太监膑) pjp.getArgs()[0];
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
        太监.set敬事日期(timeFormat.format(new Date()));

        //此处可以传递更改后的参数
        Object obj = pjp.proceed(new Object[]{太监});

        return obj;

    }

}
```

- `@Aspect`，定义切面类，用于处理程序中的切面编程操作。
- `@Pointcut("execution(public * 紫禁城.内务府.敬事房.利器库..*.军刺切(..))")`，定义切点处，对那些方法进行执行切面操作。除了这样的操作外，还可以定义成自定义注解。那么后续只要把某个你需要的方法上面添加这样的自定义注解，就可以被 `AOP` 拦截。
- `@Before("监管员()")`、`@After("监管员()")`，记录切面执行前后的记录。
- `@Around("监管员()")`，用于环绕方法增强，可以这里去处理方法中的一些属性信息，比如添加给某个字段添加时间。*太监.set敬事日期(timeFormat.format(new Date()));*

### 2. 设置切面可执行方法

>紫禁城.内务府.敬事房.利器库.切除器具.java

```java
@Component("切除")
public class 切除器具 {

    public 太监膑 军刺切(太监膑 太监){
        太监.set性别(宦官.太监.name());
        System.out.println("... 啊 ... ...老子被切面了！"+太监.get姓名());
        return 太监;
    }

}
```

- 这里类的方法就是上面定义的切点，`Pointcut`，也就是会被切面处理的方法。

### 3. 执行切面操作类

>紫禁城.内务府.敬事房.执刀人.张三丰执刀.java

```java
public class 张三丰执刀 {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("皇太极.xml");

        太监刑 太监行刑 = ctx.getBean("太监刑",太监刑.class);

        太监膑 太监 = new 太监膑();
        太监.set姓名("小德张");
        太监.set年龄("9");
        太监.set性别(宦官.男.name());

        太监 = 太监行刑.执行切除(太监);

        System.out.println("\r\n切除状态："+太监);
    }

}
```

- 首先这里定义了获取 `Spring` 注解的 `Application`，用于我们获取 `Bean` 。
- 接下来定义一个对象类，主要传递具体参数信息交给执行切面的方法，进行操作。
- 最后输出结果信息，也就是我们可以看到具体被切面操作的方法。

## 四、测试结果

### 1. 执行内容

启动方法；`紫禁城.内务府.敬事房.执刀人.张三丰执刀.java`

```java
待切身份：太监膑 [姓名=小德张, 年龄=9, 性别=男, 敬事日期=]
执行工具：军刺切
敬事前:---------准备下刀... ...
... 啊 ... ...老子被切面了！小德张
敬事后:---------切面完成... ...

切除状态：太监膑 [姓名=小德张, 年龄=9, 性别=太监, 敬事日期=2020-05-05]

Process finished with exit code 0
```

### 2. 效果图

![切面运行效果图](https://bugstack.cn/assets/images/2020/CodeGuide-01-1.png)

## 五、总结

- 汉字编程，好奇可以试试，但别真的用到项目里。本文也只是通过这样的例子，向你展示学习过程的乐趣，建立一些学习过程的好感。
- 最近加了很多刚入门学习编程的小伙伴，有很多小问号。比如；

  ![切面运行效果图](https://bugstack.cn/assets/images/2020/CodeGuide-01-2.png)
  
  *我是非常建议先跑起来多写，慢慢的等深入后，再去探究原理*
  
- 最近听到一首诗，不错；`廿四桥边廿四风，凭栏犹忆旧江东。夕阳返照桃花渡，柳絮飞来片片红。`，白色的柳絮在夕阳桃花的映衬下就是成了`片片红`。只要你敢学识渊博，就会拥有翻江倒海之力。