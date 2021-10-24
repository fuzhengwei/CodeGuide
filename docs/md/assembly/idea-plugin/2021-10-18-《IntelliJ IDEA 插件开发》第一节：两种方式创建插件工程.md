---
layout: post
category: itstack-ark-middleware
title: 第一节：两种方式创建插件工程
tagline: by 小傅哥
tag: [java,itstack-ark-middleware]
excerpt: 对于码农这一行业的编程学习生涯来说，会遇到很多的不会，不会搭建IDEA工程、不会写老师的案例、不会完成书中的效果、不会做项目的需求、不会实现复杂的逻辑、不会抽象工程的结构等等。但这些不会当中并不是所有的不会，都因为太复杂学不会，而是很大一部分内容因为找不到好的资料、没有清晰的文档、缺少完整的案例，导致不知道所以不会。
lock: need
---

# 《IntelliJ IDEA 插件开发》第一节：两种方式创建插件工程

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>原文：[]()

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`并不是所有的不会，都是真不会！`

对于码农这一行业的编程学习生涯来说，会遇到很多的**不会**，不会搭建IDEA工程、不会写老师的案例、不会完成书中的效果、不会做项目的需求、不会实现复杂的逻辑、不会抽象工程的结构等等。但这些不会当中并不是所有的不会，都因为太复杂学不会，而是很大一部分内容因为找不到好的资料、没有清晰的文档、缺少完整的案例，导致不知道所以不会。

正好最近在折腾IDEA插件开发的时候，市面的资料确实不多，也没有成体系完整的开发指导手册，所以就遇到了很多不知道就不会的事情，需要一点点查询搜索源码、验证API接口，最终把各项功能实现，当然在这个过程中也确实踩了不少坑！

好！沉淀下来，接下来在这个专栏会把一些关于 IDEA 插件开发用到的各项知识做成案例输出出来，一方面可以让自己缕清所有的知识项，另一方面也可以帮助到更多的有需要的研发人员使用。

## 二、需求目的

可能你会想什么场景会需要用到插件开发，其实插件开发算是一种通用的解决方案，由服务平台定义标准让各自使用方进行自需的扩展。

这就像我们非常常用的 P3C 代码检查插件、代码审计插件、脚手架工程创建插件、自动化API提取插件、单元测试统计插件等等，这些都是在 IDEA 代码开发平台扩展出来的各项功能插件。

插件也可以说是一种解决方案，其实与你在代码编程时使用人家已经定义好的标准结构和功能下，扩展出自己的功能时是一样的。而这种方式也可以非常好的解决一些属于代码开发期间不易于放到代码提测后问题场景，并能及时提醒研发人员作出响应的修改处理。

## 三、环境说明

- IntelliJ Platform Plugin JDK *不是自己安装的JDK1.8等，只有插件JDK才能开发插件*
- IntelliJ IDEA 2019.3.1 x64 *如果你是其他版本，会涉及到 插件工程创建后版本修改*
- gradle-5.2.1 *与 2019 IDEA 版本下的插件开发匹配，如果遇到一些环境问题可以参考我们开篇介绍*

在官方文档 [https://plugins.jetbrains.com/docs/intellij/disposers.html](https://plugins.jetbrains.com/docs/intellij/disposers.html) 介绍开发 IDEA 插件的工程方式有两种，分别是模板方式和 Gradle 工程方式。这里我们分别演示下不同方式下工程的创建和所涉及到知识点内容的介绍，虽然两种方式都能创建 IDEA 插件工程，但更推荐使用 Gradle 方式。

## 四、模板方式创建

### 1. 创建引导

**New -> Project -> IntelliJ Platform Plugin**

![](https://bugstack.cn/assets/images/middleware/guide-idea-plugin-1-01.png)

### 2. 工程结构

```java
guide-idea-plugin-create-project-by-platform
├── resources
│   └── META-INF
│       └── plugin.xml 
└── src
    └── cn.bugstack.guide.idea.plugin
        └── MyAction.java  
```

**源码**：[https://github.com/fuzhengwei/guide-idea-plugin-create-project-by-platform](https://github.com/fuzhengwei/guide-idea-plugin-create-project-by-platform) 

- plugin.xml 插件配置：开发描述、版本信息、Action事件入口、扩展信息(数据存放等)
- src 具体的事件、UI窗体、工程逻辑代码开发
- 另外类似 MyAction 的创建并不是直接创建普通类，而是通过 **New -> Plugin DevKit -> Action** 的方式进行创建，因为这样的创建方式可以再 plugin.xml 中自动添加 action 配置。*当然如果你要是自己手动创建普通类那样创建 Action 类，则需要自己手动处理配置信息。*

### 3. plugin.xml 配置

```xml
<idea-plugin>
  <id>cn.bugstack.guide.idea.plugin</id>
  <name>CreateProjectByPlatform</name>
  <version>1.0</version>
  <vendor email="184172133@qq.com" url="https://bugstack.cn">小傅哥</vendor>

  <description><![CDATA[
      基于IDEA插件模板方式创建测试工程<br>
      <em>1. 学习IDEA插件工程搭建</em>
      <em>2. 验证插件基础功能实现</em>
    ]]></description>

  <change-notes><![CDATA[
      插件开发学习功能点<br>
      <em>1. 工程搭建</em>
      <em>2. 菜单读取</em>
      <em>3. 获取配置</em>
      <em>4. 回显页面</em>
    ]]>
  </change-notes>

  <!-- please see http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/build_number_ranges.html for description -->
  <idea-version since-build="173.0"/>

  <!-- please see http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/plugin_compatibility.html
       on how to target different products -->
  <depends>com.intellij.modules.platform</depends>

  <extensions defaultExtensionNs="com.intellij">
    <!-- Add your extensions here -->
  </extensions>

  <actions>
    <!-- Add your actions here -->
    <action id="MyAction" class="cn.bugstack.guide.idea.plugin.MyAction" text="MyAction" description="MyAction">
      <add-to-group group-id="FileMenu" anchor="first"/>
    </action>

  </actions>

</idea-plugin>
```

- 这里重点看 actions 其他上面的工程信息、版本描述、个人资料都按照自己的信息填写就行，不会影响插件运行。
- actions 下是关于所有事件入口的配置，也就是你希望让你的 IDEA 插件在 IDEA 窗体中什么地方展示，以及配置快捷键等。这里的配置说明是在 FileMenu 下的第一个入口即为你的插件。

### 4. MyAction 事件入口

![](https://bugstack.cn/assets/images/middleware/guide-idea-plugin-1-04.png)

```java
public class MyAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        String classPath = psiFile.getVirtualFile().getPath();

        Messages.showMessageDialog(project, "guide-idea-plugin-create-project-by-platform: " + classPath, "Hi IDEA Plugin", Messages.getInformationIcon());
    }

}
```

- 在 MyAction 事件入口中获取 Project 工程信息、PsiFile 文件信息，以及对应的类路径。
- 最后在 Messages.showMessageDialog 下打印，这样把鼠标放到工程类下，在点这个按钮的时候就可以看到类的路径弹窗了。

### 5. 运行测试

**运行过程**

- 点击 Plugin 绿色箭头，和正常启动程序一样
- 这个时候它会打开一个新的 IDEA 工程，并在这个工程中默认安装你开发好的插件
- 在新打开的 IDEA 插件工程中，选中工程类后，点击 File -> MyAction

**运行结果**

![](https://bugstack.cn/assets/images/middleware/guide-idea-plugin-1-04.png)

- 通过测试运行效果可以看到，已经可以打出工程下类的路径信息了。*你也可以尝试把Action的入口放到其他按钮下进行测试*

## 五、Gradle 方式创建

### 1. 创建引导

**New -> Project -> Gradle 选中 Java & IntelliJ Platform Plugin**

![](https://bugstack.cn/assets/images/middleware/guide-idea-plugin-1-03.png)

### 2. 工程结构

```java
guide-idea-plugin-create-project-by-gradle
├── .gradle
└── src
    ├── main
    │   └── java
    │   	└── cn.bugstack.guide.idea.plugin 
    │       	└── MyAction.java    
    ├── resources
    │   └── META-INF
    │       └── plugin.xml 
    ├── build.gradle  
    └── gradle.properties
```

**源码**：[https://github.com/fuzhengwei/guide-idea-plugin-create-project-by-gradle](https://github.com/fuzhengwei/guide-idea-plugin-create-project-by-gradle) 

- 与模板方式创建 Gradle 主要差异在  build.gradle、gradle.properties 内容的配置，这两个文件主要是处理 Gradle 相关信息的，其中 gradle.properties 用于配置 JVM Xmx 参数的，避免下载耗费资源较大崩溃。
- plugin.xml 配置插件入口等内容，MyAction 是事件入口。

### 3. build.gradle 配置

```java
plugins {
    id 'java'
    id 'org.jetbrains.intellij' version '0.6.3'
}

group 'cn.bugstack.guide.idea.plugin'
version '1.0-SNAPSHOT'

sourceCompatibility = 1.8

repositories {
    mavenCentral()
}

dependencies {
    testCompile group: 'junit', name: 'junit', version: '4.12'
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version '2019.3.1'
}
patchPluginXml {
    changeNotes """
      <![CDATA[
      插件开发学习功能点<br>
      <em>1. 工程搭建</em>
      <em>2. 菜单读取</em>
      <em>3. 获取配置</em>
      <em>4. 回显页面</em>
    ]]>"""
}
```

- gradle 与 maven 的使用配置上，还是可以相通的找到一些类似的地方的，如果没有使用过 gradle 也是可以使用的。
- 这里需要注意 plugins 中 `id 'org.jetbrains.intellij' version '0.6.3'` 默认创建工程的版本有点高，与 gradle 5.x 不匹配。*不过你可以尝试调试合适版本进行使用*

### 4. MyAction 事件入口

```java
public class MyAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        String classPath = psiFile.getVirtualFile().getPath();

        Messages.showMessageDialog(project, "guide-idea-plugin-create-project-by-gradle: " + classPath, "Hi IDEA Plugin", Messages.getInformationIcon());
    }

}
```

- 这里与模板方式创建的案例是一样的，为了区别两个插件测试，我们这里打印了工程的名称。当然你也可以使用 project.getName() 获取工程名称。

### 5. 运行测试

- Gradle 测试运行相当于是运行 `:runIde` ,也是和普通的代码调试一样。

**运行结果**

![](https://bugstack.cn/assets/images/middleware/guide-idea-plugin-1-05.png)

- 通过测试运行效果可以看到，已经可以打出工程下类的路径信息了。

## 六、总结

- 整篇内容的学习还是蛮简单的，哪怕你之前没开发过 IDEA 插件，按照这样的套路往下折腾也是可以完成插件开发的。类似这样的知识内容只是平时常做业务开发所以接触的也不多，乍一听上去还是很陌生的，不过有这样的资料就可以上手了。
- 本章节初步介绍 IDEA 插件的方式和一个非常简单的基本功能，后续我们在 Gradle 创建插件的基础上，继续开发其他案例功能，逐步学习 IDEA 插件开发用到的各项技巧用于完成所需要解决的问题。
- 在学习的过程中可以自行尝试扩展一些其他组件入口，打印不同的工程信息。就像你使用的一些的插件一样，帮助你生成get、set，或者提取采集接口信息，也包括你写了多少行代码，思考它们是如何实现的。