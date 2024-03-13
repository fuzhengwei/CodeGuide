---
title: DDD 脚手架【Maven 仓库版】
lock: need
---

# 我把DDD脚手架，发布到了Maven仓库，大家都能用了！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=1151660636&bvid=BV1yZ42187zo&cid=1468150515&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

大家好，我是技术UP主，小傅哥。

这篇文章将帮助粉丝伙伴们更高效地利用小傅哥构建的`DDD（领域驱动设计）脚手架`，搭建工程项目，增强使用的便捷性。让👬🏻兄弟们直接在 IntelliJ IDEA 配置个在线的链接，就能直接用上这款脚手架！——  **你就说猛不猛！🤨**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-01.png" width="850px">
</div>

**那小傅哥搞的这个在线版脚手架是怎么做到的呢？** 🤔 想学吗，想学我教你呀？

在23年的时候小傅哥发布了 DDD 两款脚手架，一个轻量版 lite ，一个标准版 std。通过这两款脚手架，小伙伴们在学习小傅哥写的技术教程和实战项目的时候，可以把脚手架代码下载到本地，通过 `maven install` 在本地构建出脚手架再配置到上图中的地址后使用。

我的理想情况是大家都能很顺利的构建、配置，美滋滋的使用，但实际情况却是，编码的路上，错误是层出不穷！

所以，我动手了。我要为你们提供更Easy的方式！但也差点难住我。接下来我就给伙伴们分享下，这个东西是如何搞的。

>文末有8个实战项目，可以加入学习。🉐 项目：[https://gaga.plus](https://gaga.plus)

## 一、思考的开始 🤔

我发布过自己的 IntelliJ IDEA vo2dto 到插件市场，也推送过 openai sdk、chatglm sdk、db-router 等组件到 Maven 中央仓库。

那我就心思了，这脚手架也是个 Jar 包，应该也能发布到 Maven 中央仓库呀。要不Maven Central 自己那个脚手架是怎么发上去的？在编程开发这个事上，我一直秉承着，只要我能看见的，就都应该能复刻出来。

但你知道这里有一点，发布到 Maven 仓库的是 Jar 包，那我配置脚手架的地址哪里来呢（地址里是脚手架的定义）？我应该也没办法把脚手架的定义推送到人家 maven.apache.org 下面去。毕竟那是人家的老巢。如果能推送，那我现在打开的 Maven Central 应该有一堆脚手架，而不只是 Maven Central 所发布自己的。

所以，我灵机一动😁，打开了 [https://repo.maven.apache.org/maven2](https://repo.maven.apache.org/maven2)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-02.png" width="550px">
</div>

这个 `archetype-catalog.xml` 就是在构建 Maven 项目的脚手架时所产生的脚手架定义文件，配置到 IntelliJ IDEA 中，才会展示出脚手架列表，并可以选择使用。

那我知道了，虽然我不能推送到 maven 的老巢里去。但我可以自己提供个地址呀，把 `archetype-catalog.xml` 推送进去。经过最开始的验证，确实可以，完全没问题！Easy！

但接下来的问题变得麻烦了，Jar 怎么推送到 Maven 仓库呢。**傅哥，你不是推过吗，你咋不行了？** 死鬼！tnnd，推送 Jar 到 Maven 仓库从24年2月改版了！！！全编程界的鸡鸭鹅狗🦆，都没有人发过教程！

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-03.png" width="550px">
</div>

因为这个推送 Jar 在最开始的两天时间里，我一度怀疑是自己超限额了，不能创建了。也曾想过要不就只推送到 阿里云的私有仓库吧，大家配置个 阿里云 Maven 镜像地址，也能用。但这样的情况始终觉得不爽🙅🙅🏻‍，所以我走上了研究新版推送 Jar 的方式，直至最终成功啦💐！！！舒服！

>接下来我就给小伙伴们分享下，这东西是怎么推送上去的。

## 二、觉得我也行 🤨

### 1. 卡卡两脚

做一个新的技术东西之前呢，先要检索下资料，看看有啥坑不。这个阶段也叫技术调研。卡卡一定能搜 `发布Jar到maven仓库`，全是以前的旧版本方案，没有一个能用的！

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-04.png" width="550px">
</div>

还有一个我写的，卡卡上去给两脚！不行了，确实没啥关于新版上传 Jar 到 Maven 仓库的资料，自己去趟坑吧。

### 2. 冒烟测试

完成目标最快的方式是什么？兄弟们！当然是结果驱动，先干一脚，看它嚎不嚎。遇到问题再解决问题。所以我准备先无脑上传一波，看看都给我什么信息。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-05.png" width="550px">
</div>

- 地址：[https://central.sonatype.com/publishing](https://central.sonatype.com/publishing)
- 看着这个上传组件的小图，还挺简单的。不过在这里它是一句有用的话都不写。那我就写个名字和上传个Jar进去。*心里笑嘻嘻，难度，它会根据我的 Jar 自动分析 POM？*

但发现我想多了，第一次上传全是报错！

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-06.png" width="650px">
</div>

- 什么 pom 文件没传。所以我又机智的把 Jar 和 POM 打包了 zip 上传。
- 紧接着报错 `Invalid 'md5' checksum for file: scaffold-lite-1.0-sources.jar`，缺少 md5、sha1 验证。好在我以前发布过 maven 仓库，知道这些配置。
- 基本上知道要怎么传了，接下来，细看文档。遇到什么类错误，优先看什么内容。

### 3. 操作步骤

在 [https://central.sonatype.com/publishing](https://central.sonatype.com/publishing) 首页有一个 Help 帮助文档，[https://central.sonatype.org/register/central-portal/#producers](https://central.sonatype.org/register/central-portal/#producers) 这里有非常详细的操作说明。接下来我讲一些核心的步骤，如果操作有失败，可以参考官网资料。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-07.png" width="650px">
</div>

开始前，登录注册 [https://central.sonatype.com](https://central.sonatype.com) - 可以选择 github 登录。

#### 3.1 配置 NameSpace

如果选择 github 登录，你会有一个默认配置的 NameSpace（io.github.fuzhengwei），这个东西的作用就是要和本地工程名 groupId 保持一致的。如工程是 cn.bugstack、plus.gaga、com.liergou，那么你在的 NameSpace 就需要配置一个这样的调过来的域名。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-08.png" width="650px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-09.png" width="650px">
</div>

- 如图配置完添加验证即可，最后验证成功就可以使用了。

#### 3.2 上传要求

文档：[https://central.sonatype.org/publish/publish-portal-upload/](https://central.sonatype.org/publish/publish-portal-upload/)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-10.png" width="750px">
</div>

- 如文档上传要求，你需要把jar、pom、doc、sources 全部打包到 zip 包，同时每个文件的 asc、md5、sha1 也需要打包进来。
- 这些文件也都是在旧版上传 maven 中央仓库的时候，所需提供的内容。

#### 3.3 项目配置

**源码**：[https://gitcode.net/KnowledgePlanet/road-map/xfg-frame-archetype-lite/-/tree/master/scaffold-lite](https://gitcode.net/KnowledgePlanet/road-map/xfg-frame-archetype-lite/-/tree/master/scaffold-lite)

```pom
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>io.github.fuzhengwei</groupId>
    <artifactId>ddd-scaffold-lite</artifactId>
    <version>1.0</version>
    <packaging>maven-archetype</packaging>

    <name>ddd-scaffold-lite</name>

    <properties>
        <java.version>1.8</java.version>
        <maven-javadoc-plugin.version>3.2.0</maven-javadoc-plugin.version>
        <maven-source-plugin.version>3.2.1</maven-source-plugin.version>
        <maven-gpg-plugin.version>1.6</maven-gpg-plugin.version>
        <maven-checksum-plugin.version>1.10</maven-checksum-plugin.version>
    </properties>

    <build>
        <extensions>
            <extension>
                <groupId>org.apache.maven.archetype</groupId>
                <artifactId>archetype-packaging</artifactId>
                <version>3.2.0</version>
            </extension>
        </extensions>

        <plugins>
            <plugin>
                <groupId>net.nicoulaj.maven.plugins</groupId>
                <artifactId>checksum-maven-plugin</artifactId>
                <version>${maven-checksum-plugin.version}</version>
                <executions>
                    <execution>
                        <id>create-checksums</id>
                        <goals>
                            <goal>artifacts</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-source-plugin</artifactId>
                <version>2.2.1</version>
                <executions>
                    <execution>
                        <id>attach-sources</id>
                        <goals>
                            <goal>jar-no-fork</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-javadoc-plugin</artifactId>
                <version>2.9.1</version>
                <configuration>
                    <encoding>UTF-8</encoding>
                    <aggregate>true</aggregate>
                    <charset>UTF-8</charset>
                    <docencoding>UTF-8</docencoding>
                </configuration>
                <executions>
                    <execution>
                        <id>attach-javadocs</id>
                        <goals>
                            <goal>jar</goal>
                        </goals>
                        <configuration>
                            <additionalparam>-Xdoclint:none</additionalparam>
                            <javadocExecutable>
                                /Library/Java/JavaVirtualMachines/jdk1.8.0_341.jdk/Contents/Home/bin/javadoc
                            </javadocExecutable>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-gpg-plugin</artifactId>
                <version>1.5</version>
                <executions>
                    <execution>
                        <id>sign-artifacts</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>sign</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-release-plugin</artifactId>
                <version>2.5.3</version>
                <configuration>
                    <autoVersionSubmodules>true</autoVersionSubmodules>
                    <useReleaseProfile>false</useReleaseProfile>
                    <releaseProfiles>release</releaseProfiles>
                    <goals>deploy</goals>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <profiles>
        <profile>
            <id>release</id>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-javadoc-plugin</artifactId>
                        <version>3.3.1</version> <!-- 使用最新版本 -->
                        <executions>
                            <execution>
                                <id>attach-javadocs</id>
                                <goals>
                                    <goal>jar</goal> <!-- 绑定到 jar 目标 -->
                                </goals>
                            </execution>
                        </executions>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>

    <description>ddd scaffold lite by xiaofuge</description>

    <url>https://spring.io/projects/spring-boot/xfg-frame-archetype</url>

    <developers>
        <developer>
            <name>fuzhengwei</name>
            <email>184172133@qq.com</email>
            <organization>fuzhengwei</organization>
            <organizationUrl>https://github.com/fuzhengwei</organizationUrl>
        </developer>
    </developers>

</project>
```

- 注意 groupId、artifactId 名字，如果你有发布诉求，需要和你自己的一直。
- maven-javadoc-plugin：生成 doc 文档。这里要注意，因为我们脚手架不是代码文件，没有doc的，所以要在工程中加一个任意类名文件。工程中小傅哥加了个 Api 类。
- maven-source-plugin：生成 source 文件。
- maven-gpg-plugin：是签名的加密文件，需要本地安装过 gpg 包。
- checksum-maven-plugin：生成 md5、sha1 文件，但这里不会对 pom 生成此文件，还需要单独命令处理。

```shell
md5 ddd-scaffold-lite-1.0.pom > ddd-scaffold-lite-1.0.pom.md5
shasum ddd-scaffold-lite-1.0.pom > ddd-scaffold-lite-1.0.pom.sha1
```

- 检查生成后的文件，去掉不需要的内容。

#### 3.4 构建项目

**第1次构建**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-11.png" width="750px">
</div>

**第2次构建**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-12.png" width="750px">
</div>

**执行脚本**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-13.png" width="750px">
</div>

#### 3.5 上传 archetype-catalog.xml

把 archetype-catalog.xml 文件，上传到域名可访问云服务器的根目录中。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-14.png" width="350px">
</div>

#### 3.6 上传打包文件到 maven 仓库

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-15.png" width="950px">
</div>

- 你需要按照你的工程结构也是 namespace 创建出文件夹结构，并把工程 target 打包的文件全部复制进来。
- 最后把 io 这个文件夹，打包一个 zip 包。就可以了。

#### 3.7 上传 maven 仓库

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-16.png" width="650px">
</div>

#### 3.8 成功啦！💐

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-17.png" width="650px">
</div>

好啦，这就是整个脚手架的操作过程！现在你可以体验使用了。

## 三、开始卷项目 🚴🏻

小傅哥是一个大厂的架构师，经常会带着伙伴们，卷这些实际场景中非常有必要的技术。也会带着伙伴实战项目，这些项目也都是来自于互联网大厂中真实的业务场景，所有学习这样的项目无论是实习、校招、社招，都是有非常强的竞争力。别人还在玩玩具，而你已经涨能力！

>🧧这样的项目学习在小傅哥星球「码农会锁」有8个，每个都是从0到1开发并提供简历模板和面试题，并且还在继续开发，后续还将有更多！价格嘎嘎实惠，早点加入，早点提升自己。项目地址：[https://gaga.plus](https://gaga.plus)

在星球学习中，你可以把项目组合使用。用业务的+技术的+组件的，会非常有竞争力。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-18.png" width="950px">
</div>