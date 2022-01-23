---
title: 第10节：在插件中引入探针，基于字节码插桩获取执行SQL
lock: need
---

# 第10节：在插件中引入探针，基于字节码插桩获取执行SQL

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>原文：[https://mp.weixin.qq.com/s/Agy6c1kx9LIhs_4PqJlNNw](https://mp.weixin.qq.com/s/Agy6c1kx9LIhs_4PqJlNNw)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`片面了！`

一月三舟，托尔斯泰说：“多么伟大的作家，也不过就是在书写自己的片面而已”。何况是我，何况是我们！

虽然我们不书写文章，但我们写需求、写代码、写注释，当我们遇到了需要被讨论的问题点时，往往变成了争论点。这个好、那个差、你用的都是啥啥啥！

当你把路走窄了，你所能接受到的新的思路、新的想法、新的视野，以及非常重要的收入，也都会随之减少。只有横向对比、参考借鉴、查漏补缺，才能让你的头脑中会有更多的思路，无论是在写代码上、还是在理财上、还是在生活上。

## 二、需求目的

你是否有在使用 IntelliJ IDEA 做开发的过程，需要拿到执行 SQL 语句，复制出来做验证的时候，总是这样的语句：`SELECT * FROM USER WHERE id = ? AND name = ?` 又需要自己把 `? 号` 替换成入参值呢？

*当然这个需求其实并不大，甚至你还可以使用其他方式解决。那么在本章节会给你提供一个新的思路，可能你几乎是没过的方式进行处理。*

那么在这个章节的案例中我们用到基于 IDEA Plugin 开发能力，把字节码插桩探针，基于 Javaagent 的能力，注入到代码中。再通过增强后的字节码，获取到 `com.mysql.jdbc.PreparedStatement` -> `executeInternal` 执行时的对象，从而拿到可以直接测试的 SQL 语句。

## 三、案例开发

### 1. 工程结构

```java
guide-idea-plugin-probe
├── .gradle
├── probe-agent
│   ├── src
│   │   └── main
│   │       └── java
│   │       	└── cn.bugstack.guide.idea.plugin
│   │       		├── MonitorMethod.java
│   │       		└── PreAgent.java
│   └── build.gradle
└── probe-plugin
│   └── src
│   │   └── main
│   │       ├── java
│   │       │	└── cn.bugstack.guide.idea.plugin
│   │       │		└── utils
│   │       │		│	└── PluginUtil.java
│   │       │		└── PerRun.java
│   │       └── resources
│   │       	└── META-INF
│   │        		└── plugin.xml
│	└── build.gradle
├── build.gradle    
└── gradle.properties
```

**源码获取**：#公众号：`bugstack虫洞栈` 回复：`idea` 即可下载全部 IDEA 插件开发源码

在此 IDEA 插件工程中，工程结构分为2块：

- probe-agent：探针模块，用于编译打包提供字节码增强服务，给 probe-plugin 模块使用
- probe-plugin：插件模块，通过 `java.programPatcher` 加载字节码增强包，获取并打印执行数据库操作的 SQL 语句。

### 2. 字节码增强获取 SQL

此处的字节码增强方式，采用的 Byte-Buddy 字节码框架，它的使用方式更加简单，在使用的过程中有些像使用 AOP 的拦截方式一样，获取到你需要的信息。

此外在 gradle 打包构建的时候，需要添加 `shadowJar` 模块，把 `Premain-Class` 打包进去。*这部分代码中可以查看*

#### 2.1 探针入口

**cn.bugstack.guide.idea.plugin.PreAgent**

```java
//JVM 首先尝试在代理类上调用以下方法
public static void premain(String agentArgs, Instrumentation inst) {
    AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
        return builder
                .method(ElementMatchers.named("executeInternal")) // 拦截任意方法
                .intercept(MethodDelegation.to(MonitorMethod.class)); // 委托
    };
    new AgentBuilder
            .Default()
            .type(ElementMatchers.nameStartsWith("com.mysql.jdbc.PreparedStatement"))
            .transform(transformer)
            .installOn(inst);
}
```

- 通过 Byte-buddy 配置，拦截匹配的类和方法，因为这个类和方法下，可以获取到完整的执行 SQL 语句。

#### 2.2 拦截 SQL

**cn.bugstack.guide.idea.plugin.MonitorMethod**

```java
@RuntimeType
public static Object intercept(@This Object obj, @Origin Method method, @SuperCall Callable<?> callable, @AllArguments Object... args) throws Exception {
    try {
        return callable.call();
    } finally {
        String originalSql = (String) BeanUtil.getFieldValue(obj, "originalSql");
        String replaceSql = ReflectUtil.invoke(obj, "asSql");
        System.out.println("数据库名称：Mysql");
        System.out.println("线程ID：" + Thread.currentThread().getId());
        System.out.println("时间：" + new Date());
        System.out.println("原始SQL：\r\n" + originalSql);
        System.out.println("替换SQL：\r\n" + replaceSql);
    }
}
```

- 拦截方法入参是一种可配置操作，比如 `@This Object obj` 是为了获取当前类的执行对象，`@Origin Method method` 是为了获取执行方法。
- 在 finally 块中，我们可以通过反射拿到当前类的属性信息，以及反射拿到执行的 SQL，并做打印输出。

#### 2.3 编译打包

在测试和开发 IDEA Plugin 插件之前，我们需要先进行一个打包操作，这个打包就是把字节码增强的代码打包整一个 Jar 包。在 build.gradle -> shadowJar

![](https://bugstack.cn/images/article/assembly/assembly-220117-8-01.png)

- 打包编译后，就可以在 build -> libs 下看到 Jar：`probe-agent-1.0-SNAPSHOT-all.jar` 这个 Jar 就是用来做字节码增强处理的。

#### 2.4 测试验证

这里在把写好的字节码增强组件给插件使用之前，可以做一个测试验证，避免每次都需要启动插件才能做测试。

**单元测试**

```java
public class ApiTest {

    public static void main(String[] args) throws Exception {

        String URL = "jdbc:mysql://127.0.0.1:3306/itstack?characterEncoding=utf-8";
        String USER = "root";
        String PASSWORD = "123456";
        Class.forName("com.mysql.jdbc.Driver");

        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        String sql="SELECT * FROM USER WHERE id = ? AND name = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setLong(1,1L);
        statement.setString(2,"谢飞机");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString("name") + " " + rs.getString("address"));
        }

    }

}
```

- VM options：`-javaagent:你的路径\libs\probe-agent-1.0-SNAPSHOT-all.jar`
- 注意在测试运行的时候，你要给 ApiTest 配置 VM options 才能打印拦截 SQL 信息

**测试结果**

```java
原始SQL：
SELECT * FROM USER WHERE id = ? AND name = ?
替换SQL：
SELECT * FROM USER WHERE id = 1 AND name = '谢飞机'
谢飞机 北京.大兴区.通明湖公园
```

- 好啦，这样我们就可以拦截可以复制执行的 SQL 语句了，接下来我们再做下 IDEA Plugin 的处理。

### 3. 通过插件开发引入探针 Jar

接下来我们要把开发好的字节码增强 Jar 包，复制到 IDEA Plugin 插件开发模块中的 libs(可自己创建) 下，之后在 plugin.xml 配置加载 `implementation fileTree(dir: 'libs', includes: ['*jar'])` 这样就可以程序中，找到这个 jar 包并配置到程序中。

#### 3.1 复制 jar 到 libs 下

![](https://bugstack.cn/images/article/assembly/assembly-220117-8-02.png)

#### 3.2 build.gradle 配置加载

```xml
dependencies {
    implementation fileTree(dir: 'libs', includes: ['*jar'])
}
```

- 通过 `implementation fileTree` 引入加载文件树的方式，把我们配置好的 Jar 加载到程序运行中。

#### 3.3 程序中引入 javaagent

**cn.bugstack.guide.idea.plugin.PerRun**

```java
public class PerRun extends JavaProgramPatcher {

    @Override
    public void patchJavaParameters(Executor executor, RunProfile configuration, JavaParameters javaParameters) {

        RunConfiguration runConfiguration = (RunConfiguration) configuration;
        ParametersList vmParametersList = javaParameters.getVMParametersList();
        vmParametersList.addParametersString("-javaagent:" + agentCoreJarPath);
        vmParametersList.addNotEmptyProperty("guide-idea-plugin-probe.projectId", runConfiguration.getProject().getLocationHash());

    }

}
```

- 通过继承 `JavaProgramPatcher` 类，实现 `patchJavaParameters` 方法，通过 configuration 属性来配置我们自己需要被加载的 `-javaagent` 包。
- 这样在通过 IDEA 已经安装此插件，运行代码的时候，就会执行到这个拦截和打印 SQL 的功能。

#### 3.4 plugin.xml 添加配置

```xml
<extensions defaultExtensionNs="com.intellij">
    <!-- Add your extensions here -->
    <java.programPatcher implementation="cn.bugstack.guide.idea.plugin.PerRun"/>
</extensions>
```

- 之后你还需要把开发好的加载类，配置到 `java.programPatcher` 这样就可以程序运行的时候，被加载到了。

## 四、测试验证

- 准备好一个有数据库操作的工程，需要的是 JDBC，*如果是其他的，你需要自己扩展*
- 启动插件后，打开你的工程，运行单元测试，查看打印区

**启动插件**

![](https://bugstack.cn/images/article/assembly/assembly-220117-8-03.png)

- 如果你是新下载代码，那么可以在 probe-plugin -> Tasks -> intellij -> runIde 中进行运行启动。

**单元测试**

```java
@Test
public void test_update(){
    User user = new User();
    user.setId(1L);
    user.setName("谢飞机");
    user.setAge(18);
    user.setAddress("北京.大兴区.亦庄经济开发区");
    userDao.update(user);
}
```

**测试结果**

```java
22:30:55.593 [main] DEBUG cn.bugstack.test.demo.infrastructure.dao.UserDao.update[143] - ==>  Preparing: UPDATE user SET name=?,age=?,address=? WHERE id=? 
22:30:55.625 [main] DEBUG cn.bugstack.test.demo.infrastructure.dao.UserDao.update[143] - ==> Parameters: 谢飞机(String), 18(Integer), 北京.大兴区.亦庄经济开发区(String), 1(Long)
数据库名称：Mysql
线程ID：1
原始SQL：
UPDATE user SET name=?,age=?,address=?
        WHERE id=?
替换SQL：
UPDATE user SET name='谢飞机',age=18,address='北京.大兴区.亦庄经济开发区'
        WHERE id=1
```

- 通过测试结果可以看到，我们可以获取到直接拿去测试验证的 SQL 语句了，就不用在复制带问号的 SQL 还得修改后测试了。

## 五、总结

- 首先我们是在本章节初步尝试使用多模块的方式来创建工程，这样的方式可以更加好维护各类一个工程下所需要的代码模块。*你也可以尝试使用 gradle 创建多模块工程*
- 对于字节码插桩增强的使用方式，本篇只是一个介绍，这项技术还可以运用到更多的场景，开发出各种提升研发效率的工具。
- 了解额外的 Jar 包是怎么加载到工程的，以及如何通过配置的方式让 `javaagent` 引入自己开发好的探针组件。