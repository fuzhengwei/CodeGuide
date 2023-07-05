---
title: Maven
lock: no
---

# Maven 使用说明和配置

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

[Apache Maven](https://maven.apache.org/) 是一个软件项目管理、构建和依赖工具。基于项目对象模型 (POM) 的概念，Maven 可以通过中央信息来管理项目的构建、报告和文档。

简单来说，Apache Maven 最大的核心功能是帮你管理 Jar 包。不知道你是否在最开始学习 Java 开发时，如果有需要依赖其他 Jar 包，则需要把那个 Jar 复制到工程中，并且还需要在 Eclipse 里配置加载 Jar 包才能使用。而现在有 Maven 这样的工具后，一切都变得简单了。工程所需的 Jar 包，只需要配置 POM 就可以直接使用拉取和使用。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230704-01.png?raw=true" width="550px">
</div>

- **官网**：[https://maven.apache.org/](https://maven.apache.org/)
- **仓库**：[https://mvnrepository.com/](https://mvnrepository.com/)
- **镜像**：[https://developer.aliyun.com/mvn/guide](https://developer.aliyun.com/mvn/guide) - 阿里云提供的公共代理仓库，方便研发伙伴使用，速度更快更稳定。

## 一、软件下载

Maven 3.8+ 需要 JDK 1.7 或更高版本才能执行。 —— 较为常用的版本

**下载**：[https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230704-02.png?raw=true" width="750px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230704-03.png?raw=true" width="750px">
</div>

- 下载后直接解压就可以，它就是文件，不需要做安装操作。
- 提醒；如果你是在 Linux 等服务器上有使用需求，而不是配置到 IntelliJ IDEA 则需要配置对应的环境变量。这样才能使用 mvn 命令。

---

编辑 **/etc/profile** 文件 **sudo vim /etc/profile**，在文件末尾添加如下代码：

```
export MAVEN_HOME=/usr/local/apache-maven-3.8.8
export PATH=${PATH}:${MAVEN_HOME}/bin
```

保存文件，并运行如下命令使环境变量生效：

\# source /etc/profile

在控制台输入如下命令，如果能看到 Maven 相关版本信息，则说明 Maven 已经安装成功：

```
# mvn -v
```

## 二、环境配置

Maven /conf/settings.xml 最常用的配置一个是仓库Jar存放的位置，另外一个为了提高拉取Jar包的速度，需要配置阿里云镜像地址。—— 注意配置前，先复制一个 `settings.xml.bak` 做个备份。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230704-04.png?raw=true" width="950px">
</div>

### 1. 存储地址

```java
<localRepository>/Users/fuzhengwei/dev-ops/apache-maven-3.8.8/repository</localRepository>
```

### 2. 镜像仓库

```java
<mirrors>
  <mirror>
    <id>aliyunmaven</id>
    <mirrorOf>*</mirrorOf>
    <name>阿里云公共仓库</name>
    <url>https://maven.aliyun.com/repository/public</url>
  </mirror>
</mirrors>
```

- 配置上阿里云的仓库以后，Maven 的下载速度就嗖嗖的了！

## 三、使用配置

打开你的 IntelliJ IDEA 如图配置 Maven；

**首次打开时：**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230704-06.png?raw=true" width="850px">
</div>

**打开工程时：**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230704-05.png?raw=true" width="850px">
</div>

- 配置好你的 Maven 地址就可以使用了。

## 四、命令操作

IntelliJ IDEA Maven 面板提供了操作控制；

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230704-07.png?raw=true" width="550px">
</div>

- clean：清理，执行该命令会删除项目路径下的target文件，但是不会删除本地的maven仓库已经生成的jar文件。
- validate：验证，验证工程正确性，所需信息完整否。
- compile：编译，大伙都知道java的识别文件是.class，编译生成class文件,编译命令，只编译选定的目标，不管之前是否已经编译过，会在你的项目路径下生成一个target目录，在该目录中包含一个classes文件夹，里面全是生成的class文件及字节码文件。与build区别：只编译选定的目标，不管之前是否已经编译过。
- test：测试，单元测试。
- package：打包，将工程文件打包为指定的格式，例如JAR，WAR等（看你项目的pom文件，里面的packaging标签就是来指定打包类型的）。这个命令会在你的项目路径下一个target目录，并且拥有compile命令的功能进行编译，同时会在target目录下生成项目的jar/war文件。如果a项目依赖于b项目，打包b项目时，只会打包到b项目下target下，编译a项目时就会报错，因为找不到所依赖的b项目，说明a项目在本地仓库是没有找到它所依赖的b项目，这时就用到install命令。
- verify：核实，主要是对package检查是否有效、符合标准。
- install：安装，将包安装至本地仓库，以让其它项目依赖。该命令包含了package命令功能，不但会在项目路径下生成class文件和jar包，同时会在你的本地maven仓库生成jar文件，供其他项目使用（如果没有设置过maven本地仓库，一般在用户/.m2目录下。如果a项目依赖于b项目，那么install b项目时，会在本地仓库同时生成pom文件和jar文件，解决了上面打包package出错的问题）。
- build：建造，功能类似compile，区别是对整个项目进行编译。与compile区别及特点：是对整个工程进行彻底的重新编译，而不管是否已经编译过。Build过程往往会生成发布包，这个具体要看对IDE的配置了，Build在实际中应用很少，因为开发时候基本上不用，发布生产时候一般都用ANT等工具来发布。Build因为要全部编译，还要执行打包等额外工 作，因此时间较长。
- site：站点，生成项目的站点文档。
- deploy：配置部署，复制到远程仓库。前提需要在工程 POM 和 Maven 里配置上相关的信息以及账号。

## 五、构建配置

```java
<build>
    <finalName>项目名称</finalName>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.0</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
                <encoding>UTF-8</encoding>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-resources-plugin</artifactId>
            <version>2.5</version>
            <configuration>
                <encoding>UTF-8</encoding>
            </configuration>
        </plugin>
        <!-- 统一设定POM版本信息插件 -->
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>versions-maven-plugin</artifactId>
            <version>2.7</version>
        </plugin>
    </plugins>
</build>
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230704-08.png?raw=true" width="950px">
</div>

这是一个基本的构建配置；
- maven-compiler-plugin 指定构建版本
- maven-resources-plugin 构建资源
- versions-maven-plugin 用于统一设定工程的版本工具【如图】

## 六、实战技巧

公司中会有很多的内部工程，开发时是怎么管理Jar的呢？
1. 如果是自己本地验证，那么你需要把你的N个工程，只要是需要被M工程引用的，就需要在N个工程里都操作 Install 操作，让Jar包进入到本地仓库。这样你的其他工程就可以引入了。
2. 如果你的本地工程已经定义好接口，需要给其他人使用。那么你可以点击deploy部署。这个部署是把你的Jar发布到内部的私有仓库，只公司可见。这个操作之前需要在 POM 里配置发布部署的信息以及账号。

