---
layout: post
category: framework
title: 不重复造轮子都是骗小孩的，教你手撸 SpringBoot 脚手架！
tagline: by 小傅哥
tag: [java,framework]
excerpt: 为什么我们要去造轮子？造轮子的核心目的，是为了解决通用共性问题的凝练和复用。虽然市面上已经有了大量成熟稳定用于支撑系统建设的轮子，也就是服务、框架、组件、工具等，但对于一些较大型的公司来说，这些轮子可能并不一定能很好的支撑起系统需要承载的服务体量，这个时候就需要自建一些轮子。
lock: need
---

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>原文：[https://mp.weixin.qq.com/s/8nZnAPetPwiMtqMRj-2STw](https://mp.weixin.qq.com/s/8nZnAPetPwiMtqMRj-2STw)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`为什么我们要去造轮子？`

造轮子的核心目的，是为了解决通用共性问题的凝练和复用。

虽然市面上已经有了大量成熟稳定用于支撑系统建设的轮子，也就是服务、框架、组件、工具等，但对于一些较大型的公司来说，这些轮子可能并不一定能很好的支撑起系统需要承载的服务体量，这个时候就需要自建一些轮子。

而提倡的不重复造轮子，新造轮子不一定能保证稳定性。一般用在以官网推出的核心轮子上是适合的，比如 SpringBoot、Netty、HBase 等。但对于一些特殊场景的解决方案工具型组件，通常是没有完全符合的轮子的，就像 SpringBoot 脚手架。

其实每个较大型的公司都会有很多同类技术服务的组件，例如 RPC、数据库路由、注册中心、分布式任务、MQ队列消息等，而这时候脚手架的开发就需要适配这些组件，搭建出符合自己公司技术栈实现需要的系统架构。*这不同于一些较小的互联网公司，可以完全使用 SpringBoot 提供的一整套解决方案*

**另外**，造轮子是个人技术沉淀、也是薪资待遇的积累！*别说造不了飞机，只是你没有提供场地！*

`有什么场景还能造轮子？`

![](https://bugstack.cn/assets/images/2020/all-28-1.png)

用于架构基建下的所有模块都可以成为轮子，通常我们都是在这些场景下：`负载均衡`、`服务网关`、`服务治理`、`框架语言`、`服务组件`、`数据承载`、`框架结构`、`部署方式`、`工具插件`，建设需要的轮子。

其实一个较成熟的互联网公司，大部分场景下的轮子，已基本建造完了。剩下的一般是用于解决业务场景下非业务逻辑的通用性组件，例如，高并发下的缓存热Key、Redis 层路由、活动邀请的不唯一短码生成，等等类似这样的场景。但此类场景的轮子建设也是非常有价值的，在公司层面使用稳定后，还可以推广到市场获得一定的认可，以及更好的会被收入到 Apache 项目。

## 二、什么是脚手架呢？

**What is scaffolding? Is it a term for a particular platform?**

>Scaffolding is a meta-programming method of building database-backed software applications. It is a technique supported by some model-view-controller frameworks, in which the programmer may write a specification that describes how the application database may be used. The compiler uses this specification to generate code that the application can use to create, read, update and delete database entries, effectively treating the template as a "scaffold" on which to build a more powerful application.

- [https://stackoverflow.com/questions/235018/what-is-scaffolding-is-it-a-term-for-a-particular-platform](https://stackoverflow.com/questions/235018/what-is-scaffolding-is-it-a-term-for-a-particular-platform)

![脚手架](https://bugstack.cn/assets/images/2020/all-28-2.png)

结合 stackoverflow 上的回答，脚手架是一种元编程方法，用于构建基于数据的应用。创建系统架构的程序员编写一份规格说明书，用于描述怎么去使用数据库。而脚手架可以根据这份规则说明书生成相应的框架代码。我们把这种模式成为脚手架，在脚手架上更高效的构建出 `powerful` 的应用！

说白了就是简化具有共性重复操作的简单工作，不再需要程序员还得一点点粘贴复制，克隆出一个已经存在的架构。只需要在界面或者公用接口上，传入必要的参数，就可以创建出一个应用开发框架。

## 三、谁提供了脚手架？

### 1、Spring 官网脚手架

![spring initializr](https://bugstack.cn/assets/images/2020/all-28-3.png)

- 推荐：⭐⭐⭐⭐
- 链接：[https://start.spring.io](https://start.spring.io/)
- 源码：[https://github.com/spring-io/start.spring.io](https://github.com/spring-io/start.spring.io)
- 描述：Spring Initializr 本质上也是一个 Web 应用，它可以通过 Web 界面、Spring Tool Suite、IntelliJ IDEA 等方式，构建出一个基本的 Spring Boot 项目结构。*同时可以使用它的源码进行本地部署*

### 2、阿里云脚手架

![Aliyun Java Initializr](https://bugstack.cn/assets/images/2020/all-28-4.png)

- 推荐：⭐⭐⭐⭐
- 链接：[https://start.aliyun.com](https://start.aliyun.com/)
- 描述：Aliyun Java Initializr 和 Spring Initializr 是同类的 Web 服务，是代码框架生成器，一键生成你的代码框架，有完善的工具链，免费的IDEA插件，方便直接在IDE中生成，同时也非常适合国内用户的网络环境。

---

**其实**，这两个脚手架都能很好的生成项目结构，让程序员可以在统一的标准下快速的进入开发环境。只是依赖于自身选择的支撑服务，选择不同的框架就可以了。

## 四、手撸一个脚手架！

`都有脚手架了，那为什么要自己撸一个呢？`

脚手架的目的是为了在统一的标准下快速建设系统框架，把系统开发过程中需要的配置、组件、服务、测试，一并通过配置引入到系统开发中。

但有些时候在互联网公司通用的脚手架是不太合适使用的，因为它没有把公司内的一些自研性质的组件引入进去，也不能很好的融合。如果已经用脚手架生成后还得需要研发人员自己大量复制进去一些特定的组件，就破坏了脚手架本身能力，也是破坏了准则和规范。

**所以**，需要结合脚手架的开发能力，包装各类特定组件、服务、配置，实现符合公司领域的统一脚手架。

**那么**，本章节就带着大家看看一个脚手架，该如何开发实现。其实并没有太复杂，我们可以使用 freemarker 的能力，构建系统框架。

### 1. 工程框架

```java
EasyRiggerInitializr
└── src
    ├── main
    │   ├── java
    │   │   └── cn.bugstack.initializr.rigger
    │   │       ├── application
    │   │       │   └── IProjectGenerator.java
    │   │       ├── domain
    │   │       │	├── model
    │   │       │	│   └── ApplicationInfo.java	    
    │   │       │	│   └── ProjectInfo.java	
    │   │       │	└── service
    │   │       │       ├── module
    │   │       │       │	├── impl
    │   │       │       │  	│   ├── GenerationApplication.java
    │   │       │       │  	│   ├── GenerationIgnore.java
    │   │       │       │  	│   ├── GenerationPackageInfo.java
    │   │       │       │  	│   ├── GenerationPom.java
    │   │       │       │  	│   ├── GenerationTest.java    
    │   │       │       │  	│   └── GenerationYml.java     
    │   │       │       │   └── BaseModule.java    
    │   │       │       └── ProjectGeneratorImpl.java
    │   │       └── RiggerApplication.java
    │   └── resources	
    │       ├── generator
    │       │  	├── application.ftl
    │       │  	├── ignore.ftl 
    │       │  	├── package-info.ftl 
    │       │  	├── pom.ftl 
    │       │  	├── test.ftl     
    │       │  	└── yml.ftl    
    │       └── application.yml
    └── test
         └── java
             └── cn.bugstack.initializr.rigger.test
                 └── ApiTest.java
```

![脚手架生成工程](https://bugstack.cn/assets/images/2020/all-28-5.png)

整个用于创建脚手架的工程并不复杂，主要就是通过 freemarker 对各类定义的 ftl 模板文件，生成对应的系统框架结构。这里包括：工程主体、框架结构、启动类、配置文件、测试类等，也可以结合自身需求把对应 ORM 的类和映射关系生成出来。

整个工程结构偏 DDD 层次结构，domain 领域中建设了所有的生成方式，resources/generator 定义生成模板，其他地方就没有太大的差异了。

接下来简单介绍下这个工程的代码，让大家可以理解这样的工程是如何开发的，也可以通过这样工程继续完善成自己需要的结构。

### 2. 应用层定义生成类接口

**cn.bugstack.initializr.rigger.application.IProjectGenerator.java**

```java
public interface IProjectGenerator {

    void generator(ProjectInfo projectInfo) throws Exception;

}
```

- DDD 的分层结构，通常都会在 application 这个比较薄的层定义接口，再有 domain 领域层做相应的实现。
- 这个接口的定义主要是为了，让外部调用方可以通过此接口创建工程框架。

### 3. FTL 模板定义

**什么是 FreeMarker?**

![什么是 FreeMarker?](https://bugstack.cn/assets/images/2020/all-28-6.png)

FreeMarker 是一款 模板引擎： 即一种基于模板和要改变的数据， 并用来生成输出文本(HTML网页，电子邮件，配置文件，源代码等)的通用工具。 它不是面向最终用户的，而是一个Java类库，是一款程序员可以嵌入他们所开发产品的组件。

模板编写为FreeMarker Template Language (FTL)。它是简单的，专用的语言， 不是 像PHP那样成熟的编程语言。 那就意味着要准备数据在真实编程语言中来显示，比如数据库查询和业务运算， 之后模板显示已经准备好的数据。在模板中，你可以专注于如何展现数据， 而在模板之外可以专注于要展示什么数据。

- FreeMarker 在线手册：[http://freemarker.foofun.cn](http://freemarker.foofun.cn/)

#### 3.1 application.ftl

```java
package ${packageName};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ${className} {

    public static void main(String[] args) {
        SpringApplication.run(${className}.class, args);
    }

}
```

#### 3.2 pom.ftl

```java
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.6.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
    <version>${version}</version>
    <name>${name}</name>
    <description>${description}</description>
    
</project>
```

#### 3.3 yml.ftl

```java
server:
  port: 8081
```

**以上**，只是用于生成框架文件的基础 ftl 文件，有需要一些特殊判断和逻辑的，可以参考[FreeMarker 在线手册](http://freemarker.foofun.cn/)，编写自己需要的 ftl 文件。

### 4. FTL 生成文件

**cn.bugstack.initializr.rigger.domain.service.module.impl.GenerationApplication.java**

```java
@Service
public class GenerationApplication extends BaseModule {

    private Logger logger = LoggerFactory.getLogger(GenerationApplication.class);

    public void doGeneration(ProjectInfo projectInfo, String projectsRoot, String lastPackageName, StringBuffer applicationJavaName) throws Exception {

        ApplicationInfo applicationInfo = new ApplicationInfo(
                projectInfo.getGroupId() + "." + lastPackageName,
                applicationJavaName.toString()
        );

        String packagePath = applicationInfo.getPackageName().replace(".", "/") + "/";

        File file = new File(projectsRoot + projectInfo.getArtifactId() + "/src/main/java/" + packagePath,
                applicationInfo.getClassName() + ".java");

        // 写入文件
        super.writeFile(file, "application.ftl", applicationInfo);

        logger.info("创建主入口类 Application.java {}", file.getPath());
    }

}
```

- 关于 ftl 文件的使用，无论在用于生成那一层的文件，基本都是通用。这里只展示一下关于 Application.java 的创建。
- 主要包括了，定义入参 `ApplicationInfo`、定义文件位置 `/src/main/java/`、以及写入到文件 `super.writeFile`，这三方面。

### 5. 创建框架入口

**cn.bugstack.initializr.rigger.domain.service.ProjectGeneratorImpl.java**

```java
@Service
public class ProjectGeneratorImpl implements IProjectGenerator {

    private Logger logger = LoggerFactory.getLogger(ProjectGeneratorImpl.class);

    @Resource
    private GenerationApplication generationApplication;
    @Resource
    private GenerationYml generationYml;
    @Resource
    private GenerationPom generationPom;
    @Resource
    private GenerationTest generationTest;
    @Resource
    private GenerationIgnore generationIgnore;
    @Resource
    private GenerationPackageInfo generationPackageInfo;

    @Override
    public void generator(ProjectInfo projectInfo) throws Exception {

        URL resource = this.getClass().getResource("/");
        String projectsRoot = resource.getFile() + "/projects/";

        String lastPackageName = projectInfo.getArtifactId().replaceAll("-", "").toLowerCase();
        //启动类名称
        String[] split = projectInfo.getArtifactId().split("-");
        StringBuffer applicationJavaName = new StringBuffer();
        Arrays.asList(split).forEach(s -> {
            applicationJavaName.append(s.substring(0, 1).toUpperCase() + s.substring(1));
        });
        applicationJavaName.append("Application");

        // 1. 创建  Application.java
        generationApplication.doGeneration(projectInfo, projectsRoot, lastPackageName, applicationJavaName);

        // 2. 生成 application.yml
        generationYml.doGeneration(projectInfo, projectsRoot);

        // 3. 生成 pom.xml
        generationPom.doGeneration(projectInfo, projectsRoot);

        // 4. 创建测试类 ApiTest.java
        generationTest.doGeneration(projectInfo, projectsRoot, lastPackageName, applicationJavaName);

        // 5. 生成 .gitignore
        generationIgnore.doGeneration(projectInfo, projectsRoot);

        // 6. DDD 四层描述文件
        generationPackageInfo.doGeneration(projectInfo, projectsRoot, lastPackageName, applicationJavaName);

    }

}
```

`ProjectGeneratorImpl` 类，就是应用层接口 `IProjectGenerator` 在领域层的具体实现。这里包括了如下内容：
1. 创建  Application.java
2. 生成 application.yml
3. 生成 pom.xml
4. 创建测试类 ApiTest.java
5. 生成 .gitignore
6. DDD 四层描述文件

**综上**，就是整个脚手架生成的简要介绍，其实并没有多复杂，主要就是 ftl 文件的定义和使用，这种创建脚手架的方式还是很方便的。

### 6. 测试验证

**单元测试**

```java
@Test
public void test_IProjectGenerator() throws Exception {
    ProjectInfo projectInfo = new ProjectInfo(
            "cn.bugstack.demo",
            "web-test",
            "1.0.0-SNAPSHOT",
            "web-test",
            "Demo project for Spring Boot"
    );
    iProjectGenerator.generator(projectInfo);
}
```

**测试结果**

![脚手架创建工程结构](https://bugstack.cn/assets/images/2020/all-28-7.png)

- 脚手架把创建出来的工程生成到 test-classes 下，这个路径也可以配置到其他路径里。
- 有了新生成的工程就可以通过 IDEA 打开了，与我们手动创建的工程是一样的。

## 五、源码下载

![](https://bugstack.cn/assets/images/2020/all-28-8.png)

- 源码下载：[关注公众号：bugstack虫洞栈](https://bugstack.cn/assets/images/qrcode.png)，回复：`脚手架`
- 项目介绍：SpringBoot 脚手架，简化项目构建。目前的项目工程还比较简单，非常适合新人学习使用。后续我们会在这个版本的基础上陆续完善一些功能，把RPC、MQ、注册中心、网关、等各类组件融合进来，方便选择性的构建和扩展。

## 六、总结

- 站在公司角度不重复造轮子是为了各部门职责和资源的成本，但对个人来说，不能因为一句不重复造轮子，就放弃了对知识栈深入学习的机会。
- 没有这些根基的学习，也压根不会理解技术的迁移、服务的提取、组件的凝练。反反复复的总是做一些 API 的应用包壳，对个人技术上也就没有什么成长。
- 最后说回来，哪怕公司不需要你造轮子，没关系，你可以造给自己，可以分享到 Github 社区。一方面是自己的学习汇总，另一方面也是对技术的沉淀和贡献。
