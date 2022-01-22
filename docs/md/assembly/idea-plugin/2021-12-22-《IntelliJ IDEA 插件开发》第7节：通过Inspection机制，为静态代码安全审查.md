---
title: 第7节：通过Inspection机制，对静态代码安全审查
lock: need
---

# 《IntelliJ IDEA 插件开发》第7节：通过Inspection机制，对静态代码安全审查

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`真能闹，怕喇喇蛄，还不种稻子了？`

喇喇蛄，是东北的一种害虫，经常在种水稻的季节，在池埂子上盗洞，导致稻田里的水悄悄的流没了，影响稻苗发育。

后来发现原来写代码，也能碰见“蝲蝲蛄”，无论你写的是什么功能、哪种技术、作何目的，蝲蝲蛄总能给盗几个洞出来。“你这已经有其他的某某了你怎么还造轮子”、“你这方案不行程序员不要浪费时间”、“也没看出来你这有啥优势和价值呀怎么给业务赋能”，这种话听上去“贼”有道理，吹的叮当的，但让他去做又能搞的稀的囊的。

所以，远离蝲蝲蛄，做你想做的、搞你想搞的、学你想学的，知识是不断沉淀的积累、方案是积累后的创造。

## 二、需求目的

怎么办，都有标准的研发规范，但还是没法控制住到具体的每个研发下，给写出什么代码了。

有时候标准只是文档，看和执行的这个过程中就会一定的转行失效性，你可能会想加手段；评审、扣钱、罚绩效、检讨等等，但这样可能还只是增加过程成本，最终效果也不会太好。不太可能一个写代码还得配一个保姆，所以就像 p3c、pmd-idea，这样的插件出来了，帮助程序员把代码写好，治理掉一些不合标准的问题代码。

那么，你好奇这个事是怎么干的吗，怎么你就在 IDEA 写代码，它就能给你检测出来，告诉你有问题，并提醒你修改以及有些还可以一键帮助你修改呢？那如果你想再增加点你们公司个性的要求的时候，怎么扩展呢？**本章节我们就使用 IDEA 插件开发能力，把这个事办喽**

## 三、案例开发

### 1. 工程结构

```java
guide-idea-plugin-pmd
├── .gradle
└── src
    ├── main
    │   └── java
    │   	└── cn.bugstack.guide.idea.plugin 
    │       	├── rule
    │       	│	├── FastJsonAutoType.java
    │       	│	├── HardcodedIp.java
    │       	│	└── ReplacePseudorandomGenerator.java
    │       	└── utils
    │       	 	└── InspectionBundle.java
    ├── resources
    │   ├── inspectionDescriptions
    │   │   ├── FastJsonAutoType.html
    │   │   ├── HardcodedIp.html  
    │   │   └── ReplacePseudorandomGenerator.html
    │   └── META-INF
    │       └── plugin.xml 
    ├── build.gradle  
    └── gradle.properties
```

**源码获取**：#公众号：`bugstack虫洞栈` 回复：`idea` 即可下载全部 IDEA 插件开发源码

在此 IDEA 插件工程中，主要分为3块区域：

- rule：规则配置区域，以继承 IDEA 原生 Inspection 检查类，扩展自身需要扫描的代码片段，进行警告、注释、修复。
- inspectionDescriptions：是对应的警告注释，编写到 html 中，最终展示到 IDEA 下对应的问题代码片段上。
- plugin.xml：中需要配置 localInspection 也就是配置你自定义的代码检测实现类。

### 2. 伪随机数检测

**目的**：把代码中的 `new Random` 不安全伪随机数警告并提供修复，处理为 `new SecureRandom`

**RandomRule**

![](https://bugstack.cn/images/article/assembly/assembly-211222-7-01.png)

```java
PsiElementFactory factory = JavaPsiFacade.getElementFactory(project);
typeElement.replace(factory.createTypeElementFromText("SecureRandom", null));
PsiNewExpression secureNewExp = (PsiNewExpression) factory.createExpressionFromText("new SecureRandom()", null);
newExp.replace(secureNewExp);
```

- 通过继承 `AbstractBaseJavaLocalInspectionTool` Override `buildVisitor` 方法，扩展检测代码。*当你写了这段方法后，IDEA 会把一行行的代码都通过这个方法传进来*
- 在 `visitNewExpression` 方法中扩展自身的检测处理，遇到了哪种代码片段，要提供什么样的提醒以及提醒的级别，最后是提供一个 Fix 修复能力，这个修复能力就在替换这段代码片段，通过还可以操作引入新包的动作 `import xxx`

### 3. FastJson检测

**目的**：`com.alibaba:fastjson` 在开启 AutoTypeSupport 时，存在反序列化风险。如果程序中有 `ParserConfig.getGlobalInstance().setAutoTypeSupport(true);` 代码直接提醒删除处理。

```java
public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
    return new JavaElementVisitor() {
        @Override
        public void visitMethodCallExpression(PsiMethodCallExpression expression) {
            if (hasFullQualifiedName(expression, "com.alibaba.fastjson.parser.ParserConfig", "setAutoTypeSupport")) {
                PsiExpression[] args = expression.getArgumentList().getExpressions();
                if (args.length == 1 &&
                        args[0] instanceof PsiLiteralExpression &&
                        Boolean.TRUE.equals(((PsiLiteralExpression) args[0]).getValue())
                ) {
                    holder.registerProblem(
                            expression,
                            "FastJson unserialization risk",
                            ProblemHighlightType.GENERIC_ERROR_OR_WARNING,
                            new DeleteElementQuickFix(expression, "!Fix: remove setAutoTypeSupport")
                    );
                }
            }
        }
    };
}
```

- 整个对代码检测的操作基本都是类似的，这个无非也是检测出代码库，并进行删除的提醒处理 `DeleteElementQuickFix`

### 4. 提醒模板

```html
<html>
<body>
<b>小傅哥-提醒:</b> 不安全的伪随机数生成器 <br>
<br>
<p>java.util.Random 依赖一个可被预测的伪随机数生成器。</p>
<br>
<p style="font-size: 10px;color: #629460;">最佳实践:</p>
<p style="font-size: 10px;">使用java.security.SecureRandom</p>
</body>
</html>
```

- 提醒模板需要编写 html 格式的内容，这个内容会被展示到错误代码的详情里。*后面我们做测试的可以查看*

### 5. 检测配置

```xml
<extensions defaultExtensionNs="com.intellij">
    <localInspection
            language="JAVA"       groupPath="Java"
            groupName="X-PMD"   enabledByDefault="true"   level="ERROR"
            bundle="InspectionBundle"     key="replace.pseudorandom.generator.name"
            implementationClass="cn.bugstack.guide.idea.plugin.rule.RandomRule"
    />
    
    <localInspection
            language="JAVA"       groupPath="Java"
            groupName="X-PMD"   enabledByDefault="true"   level="ERROR"
            bundle="InspectionBundle"     key="fastjson.auto.type.name"
            implementationClass="cn.bugstack.guide.idea.plugin.rule.FastJsonRule"
    />
    
    <localInspection
            language="JAVA"      groupPath="Java"
            groupName="X-PMD"  enabledByDefault="true"     level="WARNING"
            bundle="InspectionBundle"     key="hardcoded.ip.name"
            implementationClass="cn.bugstack.guide.idea.plugin.rule.IPRule"
    />
</extensions>
```

- 在 plugin.xml 中配置我们自己开发好的代码静态检测对象，这样你的检测类就生效了。

## 四、测试验证

**启动插件**

![](https://bugstack.cn/images/article/assembly/assembly-211222-7-02.png)

- 如果你下载代码后，没有 Plugin 可以自己配置一下，在 Tasks 中配置 `:runIde`

**错误提醒**

![](https://bugstack.cn/images/article/assembly/assembly-211222-7-03.png)

**错误详情**

![](https://bugstack.cn/images/article/assembly/assembly-211222-7-04.png)

- 当你点击 Fix，那么接下来就可以进行自动替换代码并修复了，就是把 `Random random = new Random()` 替换为 `SecureRandom random = new SecureRandom();`
- 其他2个也可以在获取代码后进行测试验证，一个是IP，另外一个是使用 `ParserConfig.getGlobalInstance().setAutoTypeSupport(true);` 的错误提醒。

## 五、总结

- 本章节我们学习了如何使用 IDEA 原生 Inspection 检查机制，扩展我们自己需要添加的代码检测逻辑，以及使用 LocalQuickFix 的实现类，做代码的替换和引入响应包的操作。
- 另外对于代码检测，还有一个更加标准的工具叫 PMD 它是一款采用 BSD 协议的代码检查工具，你可以扩展实现为自己的标准和规范以及完善个性的提醒和修复操作。
- 像 p3c 就是一款静态代码检测工具，用的人也非常多，不过它的插件开发不是基于 Java 实现的，代码开发上也并没有一些注释。所以非常建议阅读 pmd-idea，这款代码写的非常好，抽象充足、结构清晰、内容完整：[https://github.com/ybroeker/pmd-idea](https://github.com/ybroeker/pmd-idea)