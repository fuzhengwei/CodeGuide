---
title: Maven Central
lock: no
---

# Maven Central 微服务包发布管理

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

诶，`你八股背的挺好，怎么实操这么差？`哎，`你算法刷的挺多，怎么编码这么烂？`啊，`你不是做过微服务，怎么不知道咋引包？`这是越来越多的面试官在招聘完的真实反馈，很多伙伴的经验不是来自于实践而是背诵！

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-01.gif" width="150px">
</div>

**有些东西没做过就是不知道！**

所有那些背的东西，都是对结果的单一描述，而不是用大量的实践验证得出的结果，那么就会缺少各种可能的回答。以及对结果的各种论点/观点。

如，很多伙伴都提到做的是微服务项目，但对于基础的 Maven 在微服务中的使用，几乎是不知道的。那么就反向说明，你没有真的做过这些。所以你才不知道。

所以，如果你想真的学习下来这些经验，就要对这些知识点一个个的实践、验证、积累。在小傅哥的编程路书中以及积累了大量的知识内容，可以学习；[https://bugstack.cn/md/road-map/road-map.html](https://bugstack.cn/md/road-map/road-map.html)

案例工程；
- [https://github.com/fuzhengwei/xfg-dev-tech-micro-service-a](https://github.com/fuzhengwei/xfg-dev-tech-micro-service-a)
- [https://github.com/fuzhengwei/xfg-dev-tech-micro-service-b](https://github.com/fuzhengwei/xfg-dev-tech-micro-service-b)

> 接下来，我们再分享下关于微服务 Maven 发包的实践知识。

## 一、我为什么报错？

“傅哥，这怎么报错了，我怎么引不到这个包？”

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-02.png" width="550px">
</div>

对于这样的报错，我估计不少伙伴碰到了都有点慌。怎么我刚下载下来的工程，什么都做没做就报错了。这个包在仓库里也没有呀？之所以你会感觉很慌，是因为没有微服务项目的开发经验。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-04.png" width="550px">
</div>
只要你碰到这样一个报错，那么在“作者/开发者/公司”的仓库中，一定会有这样一个其他的微服务，只要下载到自己本地，用 IntelliJ IDEA 打开，点击右侧的 maven install 就可以初始化包到你的本地 Maven 仓库了，这样你就能引入了。

那关于微服务的包的构建和使用有什么管理和规范吗？🤔 有的！

## 二、微服务包管理

无论是我们个人开发还是在公司开发，都是有一套微服务包的开发和推送以及使用的规范。大家默认统一遵守，所以开发中才会非常协调的进行。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-03.png" width="550px">
</div>

首先，公司中有很多业务，很多需求，这些东西不能都在一个工程里开发。那么势必就会拆分为多个微服务系统以及通用的技术解决方案组件。那么这些微服务和组件之间就会存在引入调用关系。尤其是一些大的互联网公司，更是会采用 RPC 的通信解决内部微服务之间的高效调用，而 RPC 通信不同于 HTTP 是需要引入接口定义包的，所以必然存在一个微服务中引入另外一个微服务的 Jar 也就是 POM 配置。

那么，如果是你个人验证阶段，是先在自己本地开发验证。这个时候不会发包到 maven 公司统一仓库或者中心仓库，那么就需要你自己本地在 A 服务中进行 install，把包发到个人的 maven 本地仓库文件夹中，之后你的 B 服务就可以引入使用了。

之后，站在整个公司的视角。你开发完的组件，不只是你使用，还有其他很多人使用。但跨部门，跨组的别人又不一定都有你的代码库权限，这个时候就需要把本地微服务的 Jar 发包到公司的 Maven 镜像库中。一般稍微大一点的公司都会有自己的 Maven 镜像库，如果公司比较小则会使用阿里云这类云厂商提供的的制品库，如；[https://packages.aliyun.com/](https://packages.aliyun.com/) 让公司的所有人 Maven setting 配置文件都配置这套制品库，这样就解决了一个人发包，其他人也能拉取到使用。

另外，如果你想你发的包，不只是你自己或公司里的人使用，那么就要把包发到 Maven 统一的中心仓库。[https://central.sonatype.com/publishing](https://central.sonatype.com/publishing) 这个过程稍微有点漫长，会持续 `4-12` 个小时才能完全同步好。

> 下文会提到如何发包到阿里云制品库和 Maven 中心仓库。

## 三、微服发包规范

工程发包会分为开发阶段的 snapshot 包和正式上线使用的 release 包，snapshot 包同一个版本发包后可以被替代，release 包，只能每次更换版本号。

```pom
<dependency>
    <groupId>cn.bugstack</groupId>
    <artifactId>xfg-dev-tech-api</artifactId>
    <version>0.0.0-SNAPSHOT</version>
</dependency>

<dependency>
    <groupId>cn.bugstack</groupId>
    <artifactId>xfg-dev-tech-api</artifactId>
    <version>1.0</version>
</dependency>
```

- 0.0.0-SNAPSHOT 以 SNAPSHOT 结尾是开发阶段包，都是 0.0.0-SNAPSHOT 命名。
- release 包，只需要命名版本号即可。

另外这样的微服务包的版本号，在一个工程中每个模块都需要在发包的时候一起操作修改。如果是手动的修改就容易遗漏导致问题，所以这里我们要使用 Maven 提供的插件管理包。如；

```
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>versions-maven-plugin</artifactId>
    <version>2.7</version>
</plugin>
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-05.png" width="650px">
</div>

- 配置好 `versions-maven-plugin` 点击 `version:set` 就可以统一管理工程的版本号了。

## 四、阿里云效发包 - 研发组内用

### 1. 阿里云制品库

地址：[https://packages.aliyun.com/](https://packages.aliyun.com/) - 填写个人信息申请即可。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-06.png" width="750px">
</div>

申请后，你会看到制品仓库配置，包括生产库和非生产库，他们主要负责给你提供 release、snapshot 发包和拉取。

### 2. 发包配置

分别进入 release、snapshot，都可以获得一份 Maven settings.xml 配置文件，把两份文件夹下载后可以找到 release、snapshot 的差异，合并成一份文件。这样你就可以在本地发 release、snapshot 包了。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-07.png" width="750px">
</div>

**配置文件**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-08.png" width="750px">
</div>

**样例文件**

```pom
<?xml version="1.0" encoding="UTF-8"?>

<settings xmlns="http://maven.apache.org/SETTINGS/1.2.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.2.0 https://maven.apache.org/xsd/settings-1.2.0.xsd">

  <localRepository>/Users/fuzhengwei/Applications/apache-maven-3.8.4/repository</localRepository>

  <mirrors>
    <mirror>
      <id>mirror</id>
      <mirrorOf>central,jcenter,!2452122-snapshot-XqjwfN</mirrorOf>
      <name>mirror</name>
      <url>https://maven.aliyun.com/nexus/content/groups/public</url>
    </mirror>
  </mirrors>
  <servers>
    <server>
      <id>2452122-snapshot-XqjwfN</id>
      <username>65b081c2242105ca211dd310</username>
      <password>每个人会有自己的密码</password>
    </server>
    <server>
      <id>2452122-release-dbuebF</id>
      <username>65b081c2242105ca211dd310</username>
      <password>每个人会有自己的密码</password>
    </server>
  </servers>
  <profiles>
    <profile>
      <id>rdc</id>
      <properties>


        <altSnapshotDeploymentRepository>
          2452122-snapshot-XqjwfN::default::https://packages.aliyun.com/65b081d4076e069afe3d2f50/maven/2452122-snapshot-xqjwfn
        </altSnapshotDeploymentRepository>

        <altReleaseDeploymentRepository>
          2452122-release-dbuebF::default::https://packages.aliyun.com/65b081d4076e069afe3d2f50/maven/2452122-release-dbuebf
        </altReleaseDeploymentRepository>

      </properties>
      <repositories>
        <repository>
          <id>central</id>
          <url>https://maven.aliyun.com/nexus/content/groups/public</url>
          <releases>
            <enabled>true</enabled>
          </releases>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
        </repository>
        <repository>
          <id>snapshots</id>
          <url>https://maven.aliyun.com/nexus/content/groups/public</url>
          <releases>
            <enabled>false</enabled>
          </releases>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </repository>
        <repository>
          <id>2452122-snapshot-XqjwfN</id>
          <url>https://packages.aliyun.com/65b081d4076e069afe3d2f50/maven/2452122-snapshot-xqjwfn</url>
          <releases>
            <enabled>false</enabled>
          </releases>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </repository>
        <repository>
          <id>2452122-release-dbuebF</id>
          <url>https://packages.aliyun.com/65b081d4076e069afe3d2f50/maven/2452122-release-dbuebf</url>
          <releases>
            <enabled>true</enabled>
          </releases>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
        </repository>
      </repositories>
      <pluginRepositories>
        <pluginRepository>
          <id>central</id>
          <url>https://maven.aliyun.com/nexus/content/groups/public</url>
          <releases>
            <enabled>true</enabled>
          </releases>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
        </pluginRepository>
        <pluginRepository>
          <id>snapshots</id>
          <url>https://maven.aliyun.com/nexus/content/groups/public</url>
          <releases>
            <enabled>false</enabled>
          </releases>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </pluginRepository>
        <pluginRepository>
          <id>2452122-snapshot-XqjwfN</id>
          <url>https://packages.aliyun.com/65b081d4076e069afe3d2f50/maven/2452122-snapshot-xqjwfn</url>
          <releases>
            <enabled>false</enabled>
          </releases>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </pluginRepository>
        <pluginRepository>
          <id>2452122-release-dbuebF</id>
          <url>https://packages.aliyun.com/65b081d4076e069afe3d2f50/maven/2452122-release-dbuebf</url>
          <releases>
            <enabled>true</enabled>
          </releases>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
        </pluginRepository>
      </pluginRepositories>
    </profile>
  </profiles>
  <activeProfiles>
    <activeProfile>rdc</activeProfile>
  </activeProfiles>

</settings>
```

### 3. 本地发包

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-09.png" width="750px">
</div>

### 4. 查看仓库

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-10.png" width="750px">
</div>

- 发包后，可以到你的仓库查看是否已经将本地包发到了仓库中。
- 到这里，你的整个研发组，就可以统一使用这套包拉取到自己本来进行使用了。

## 五、中心仓库发包 - 全球研发用

官网：[https://central.sonatype.com/publishing](https://central.sonatype.com/publishing)
文档：[https://central.sonatype.org/publish/publish-portal-upload/](https://central.sonatype.org/publish/publish-portal-upload/)

### 1. 命名空间配置

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-11.png" width="750px">
</div>

Maven 中心仓库发包需要验证你的域名，确保全世界的唯一性。如果选择 github 登录，你会有一个默认配置的 NameSpace（io.github.fuzhengwei），这个东西的作用就是要和本地工程名 groupId 保持一致的。如工程是 cn.bugstack、plus.gaga、com.liergou，那么你在的 NameSpace 就需要配置一个这样的调过来的域名。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-08.png" width="750px">
</div>

- 如图配置完添加验证即可，最后验证成功就可以使用了。

### 2. 上传要求

文档：[https://central.sonatype.org/publish/publish-portal-upload/](https://central.sonatype.org/publish/publish-portal-upload/)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-archetype-10.png" width="750px">
</div>

- 如文档上传要求，你需要把jar、pom、doc、sources 全部打包到 zip 包，同时每个文件的 asc、md5、sha1 也需要打包进来。
- 这些文件也都是在旧版上传 maven 中央仓库的时候，所需提供的内容。

### 3. 项目配置

**源码**：[https://gitcode.net/KnowledgePlanet/road-map/xfg-frame-archetype-lite/-/tree/master/scaffold-lite](https://gitcode.net/KnowledgePlanet/road-map/xfg-frame-archetype-lite/-/tree/master/scaffold-lite)

```pom
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

<!--    <parent>-->
<!--        <groupId>cn.bugstack</groupId>-->
<!--        <artifactId>xfg-dev-tech-micro-service-a</artifactId>-->
<!--        <version>0.0.1-SNAPSHOT</version>-->
<!--    </parent>-->

    <groupId>cn.bugstack</groupId>
    <artifactId>xfg-dev-tech-api</artifactId>
    <version>0.0.0-SNAPSHOT</version>

    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <retrofit2.version>2.9.0</retrofit2.version>
        <slf4j.version>2.0.6</slf4j.version>
        <maven-javadoc-plugin.version>3.2.0</maven-javadoc-plugin.version>
        <maven-source-plugin.version>3.2.1</maven-source-plugin.version>
        <maven-gpg-plugin.version>1.6</maven-gpg-plugin.version>
        <maven-checksum-plugin.version>1.10</maven-checksum-plugin.version>
    </properties>

    <name>xfg-dev-tech-api</name>
    <description>ce API . Copyright © 2023 bugstack虫洞栈 All rights reserved. 版权所有（C）小傅哥 https://github.com/fuzhengwei</description>
    <url>https://github.com/fuzhengwei/xfg-dev-tech-micro-service-a</url>

    <licenses>
        <license>
            <name>Apache License</name>
            <url>https://opensource.org/license/apache-2-0/</url>
            <distribution>repo</distribution>
        </license>
    </licenses>

    <developers>
        <developer>
            <id>Xiaofuge</id>
            <name>Xiaofuge</name>
            <email>184172133@qq.com</email>
            <url>https://github.com/fuzhengwei</url>
            <organization>xfg-dev-tech-micro-service-a</organization>
            <organizationUrl>https://github.com/fuzhengwei/xfg-dev-tech-micro-service-a</organizationUrl>
            <roles>
                <role>architect</role>
                <role>developer</role>
            </roles>
            <timezone>Asia/Shanghai</timezone>
        </developer>
    </developers>

    <scm>
        <connection>scm:git:https://github.com/fuzhengwei/xfg-dev-tech-micro-service-a.git</connection>
        <developerConnection>scm:git:https://github.com/fuzhengwei/xfg-dev-tech-micro-service-a.git</developerConnection>
        <tag>HEAD</tag>
        <url>https://github.com/fuzhengwei/xfg-dev-tech-micro-service-a</url>
    </scm>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.0</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                    <encoding>${project.build.sourceEncoding}</encoding>
                </configuration>
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
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.12.4</version>
                <configuration>
                    <skipTests>true</skipTests>
                </configuration>
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
                                /Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/bin/javadoc
                            </javadocExecutable>
                        </configuration>
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
                        <version>2.9.1</version>
                        <executions>
                            <execution>
                                <id>attach-javadocs</id>
                                <goals>
                                    <goal>jar</goal>
                                </goals>
                                <configuration>
                                    <additionalparam>-Xdoclint:none</additionalparam>
                                    <javadocExecutable>
                                        /Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/bin/javadoc
                                    </javadocExecutable>
                                </configuration>
                            </execution>
                        </executions>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>

</project>
```

- 注意，`<groupId>cn.bugstack</groupId>` 这里不能是继承。
- maven-javadoc-plugin：生成 doc 文档。这里要注意，因为我们脚手架不是代码文件，没有doc的，所以要在工程中加一个任意类名文件。工程中小傅哥加了个 Api 类。
- maven-source-plugin：生成 source 文件。
- maven-gpg-plugin：是签名的加密文件，需要本地安装过 gpg 包。注意；`checksum-maven-plugin` 需要在 `maven-gpg-plugin` 执行，这样就不用做下面的手动 md5 逻辑了。
- checksum-maven-plugin：生成 md5、sha1 文件，但这里不会对 pom 生成此文件，还需要单独命令处理。
- `/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/bin/javadoc` 配置你自己的 jdk 地址。

```shell
md5 ddd-scaffold-lite-1.0.pom > ddd-scaffold-lite-1.0.pom.md5
shasum ddd-scaffold-lite-1.0.pom > ddd-scaffold-lite-1.0.pom.sha1
```

- 检查生成后的文件，去掉不需要的内容。

### 4. 构建项目

#### 4.1 首次构建

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-13.png" width="750px">
</div>

- 需要构建 Release 包。

#### 4.2 执行命令

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-14.png" width="750px">
</div>

```java
md5 xfg-dev-tech-api-1.0.pom > xfg-dev-tech-api-1.0.pom.md5
shasum xfg-dev-tech-api-1.0.pom > xfg-dev-tech-api-1.0.pom.sha1
```

- 构建后，执行命令。增加新的校验文件。

#### 4.3 打包文件

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-15.png" width="750px">
</div>

- 创建一个和构建一样路径的文件夹，mac/linux 操作命名；`mkdir -p cn/bugstack/xfg-dev-tech-api/1.0/`
- 之后把文件复制到文件夹中打一个zip包。

### 5. 发包

#### 5.1 提交

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-16.png" width="650px">
</div>

- 提交你的压缩包。

#### 5.2 等待通过&发包

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-17.png" width="650px">
</div>

- 稍微等待，验证通过后可以点击 Publish 发布。
- 预计4-12小时后会同步到 maven 中心仓库以及阿里云的仓库。

#### 5.3 发布效果

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-maven-18.png" width="650px">
</div>

- 地址：[https://mvnrepository.com/search?q=cn.bugstack](https://mvnrepository.com/search?q=cn.bugstack)

