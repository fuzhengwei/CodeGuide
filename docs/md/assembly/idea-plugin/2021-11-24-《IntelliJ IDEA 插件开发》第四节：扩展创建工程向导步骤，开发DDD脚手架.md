---
title: 第6节：扩展创建工程向导步骤，开发DDD脚手架
lock: need
---

# 第6节：扩展创建工程向导步骤，开发DDD脚手架

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>原文：[https://mp.weixin.qq.com/s/4DFCF83bySTYyBZgouX6-w](https://mp.weixin.qq.com/s/4DFCF83bySTYyBZgouX6-w)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`研发，要避免自嗨！`

你做这个东西的价值是什么？有竞品调研吗？能赋能业务吗？那不已经有同类的了，你为什么还自己造轮子？

你是不是也会被问到这样的问题，甚至可能还有些头疼。但做的时候挺嗨，研究技术嘛，还落地了，多刺激。不过要说价值，好像一时半会还体现不出来，能不能赋能业务就不更不一定了。

可谁又能保证以后不能呢，技术的点是一个个攻克尝试的才有机会再深度学习后把这些内容连成一片，就像单说水、单说沙子、单说泥巴，好像并没有啥用，但把它们凑到一块再给把火，就烧成了砖，砖就码成了墙，墙就盖成房。

## 二、需求目的

我们这一章节把 freemarker 能力与 IDEA Plugin 插件能力结合，开发一个`DDD 脚手架 IDEA 插件`，可能你会想为什么要把脚手架开发到插件里呢？还有不是已经有了成型的脚手架可以用吗？

首先我们目前看到的脚手架基本都是网页版的，也就是一次性创建工程使用，不过在我们实际使用的时候，还希望在工程创建过程中把数据库、ES、Redis等生成对应的 ORM 代码，减少开发工作量。并且在使用的工程骨架的过程中，还希望可以随着开发需要再次补充新的功能进去，这个时候网页版的脚手架都不能很好的支持了。此外一些大厂都会自己的技术体系，完全是使用市面的脚手架基本很难满足自身的需求，所以就需要有一个符合自己场景的脚手架了。

那么，我们本章节就把脚手架的开发放到 IDEA 插件开发中，一方面学习脚手架的建设，另外一方面学习如何改变工程向导，创建出自己需要的DDD结构脚手架。

## 三、案例开发

### 1. 工程结构

```java
guide-idea-plugin-scaffolding
├── .gradle
└── src
    ├── main
    │   └── java
    │   	└── cn.bugstack.guide.idea.plugin 
    │       	├── domain
    │       	│	├── model   
    │       	│	│	└── ProjectConfigVO.java       
    │       	│	└── service   
    │       	│	 	├── impl     
    │       	│	 	│	└── ProjectGeneratorImpl.java  
    │       	│	 	├── AbstractProjectGenerator.java     
    │       	│	 	├── FreemarkerConfiguration.java      
    │       	│	 	└── IProjectGenerator.java      
    │       	├── factory
    │       	│	└── TemplateFactory.java  
    │       	├── infrastructure
    │       	│	├── DataSetting.java       
    │       	│	├── DataState.java  
    │       	│	├── ICONS.java      
    │       	│	└── MsgBundle.java     
    │       	├── module  
    │       	│	├── DDDModuleBuilder.java    
    │       	│	└── DDDModuleConfigStep.java         
    │       	└── ui
    │       	 	├── ProjectConfigUI.java  
    │       	 	└── ProjectConfigUI.form
    ├── resources
    │   ├── META-INF
    │   │   └── plugin.xml 
    │   └── template
    │       ├── pom.ftl
    │       └── yml.ftl 
    ├── build.gradle  
    └── gradle.properties
```

**源码获取**：#公众号：`bugstack虫洞栈` 回复：`idea` 即可下载全部 IDEA 插件开发源码

在此 IDEA 插件工程中，主要分为5块区域：
- domain：领域层，提供创建 DDD 模板工程的服务，其实这部分主要使用的就是 freemarker
- factory：工厂层，提供工程创建模板，这一层的作用就是我们在 IDEA 中创建新工程的时候，可以添加上我们自己的内容，也就是创建出我们定义好的 DDD 工程结构。
- infrastructure：基础层，提供数据存放、图片加载、信息映射这些功能。
- module：模块层，提供 DDD 模板工程的创建具体操作和步骤，也就是说我们创建工程的时候是一步步选择的，你可以按需添加自己的步骤页面，允许用户选择和添加自己需要的内容。*比如你需要连库、选择表、添加工程所需要的技术栈等*
- ui：界面层，提供Swing 开发的 UI 界面，用于用户图形化选择和创建。

### 2. UI 工程配置窗体

![](https://bugstack.cn/images/article/assembly/assembly-211123-4-01.png)

```java
public class ProjectConfigUI {

    private JPanel mainPanel;
    private JTextField groupIdField;
    private JTextField artifactIdField;
    private JTextField versionField;
    private JTextField packageField;

}
```

- 使用 Swing UI Designer 创建一个配置工厂信息的 UI 窗体，通过这样的方式创建可以直接拖拽。
- 在这个 UI 窗体中我们主要需要；`roupId`、`artifactId`、`version`、`package`

### 3. 配置工程步骤创建

#### 3.1 数据存放

**cn.bugstack.guide.idea.plugin.infrastructure.DataSetting**

```java
@State(name = "DataSetting",storages = @Storage("plugin.xml"))
public class DataSetting implements PersistentStateComponent<DataState> {

    private DataState state = new DataState();

    public static DataSetting getInstance() {
        return ServiceManager.getService(DataSetting.class);
    }

    @Nullable
    @Override
    public DataState getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull DataState state) {
        this.state = state;
    }

     public ProjectConfigVO getProjectConfig(){
        return state.getProjectConfigVO();
     }

}
```

- 在基础层提供数据存放的服务，把创建工程的配置信息存放到服务中，这样比较方便设置和获取。

#### 3.2 扩展步骤

**cn.bugstack.guide.idea.plugin.module.DDDModuleConfigStep**

```java
public class DDDModuleConfigStep extends ModuleWizardStep {

    private ProjectConfigUI projectConfigUI;

    public DDDModuleConfigStep(ProjectConfigUI projectConfigUI) {
        this.projectConfigUI = projectConfigUI;
    }

    @Override
    public JComponent getComponent() {
        return projectConfigUI.getComponent();
    }

    @Override
    public boolean validate() throws ConfigurationException {
        // 获取配置信息，写入到 DataSetting
        ProjectConfigVO projectConfig = DataSetting.getInstance().getProjectConfig();
        projectConfig.set_groupId(projectConfigUI.getGroupIdField().getText());
        projectConfig.set_artifactId(projectConfigUI.getArtifactIdField().getText());
        projectConfig.set_version(projectConfigUI.getVersionField().getText());
        projectConfig.set_package(projectConfigUI.getPackageField().getText());

        return super.validate();
    }

}
```

- 继承 `ModuleWizardStep` 开发一个自己需要的步骤，这个步骤就会出现到我们创建新的工程中。
- 同时在重写的 validate 方法中，把从工程配置 UI 窗体中获取到信息，写入到数据配置文件中。

#### 3.3 配置步骤

**cn.bugstack.guide.idea.plugin.module.DDDModuleBuilder**

```java
public class DDDModuleBuilder extends ModuleBuilder {

    private IProjectGenerator projectGenerator = new ProjectGeneratorImpl();

    @Override
    public Icon getNodeIcon() {
        return ICONS.SPRING_BOOT;
    }
    
    /**
     * 重写 builderId 挂载自定义模板
     */
    @Nullable
    @Override
    public String getBuilderId() {
        return getClass().getName();
    }
    
    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {

        // 添加工程配置步骤，可以自己定义需要的步骤，如果有多个可以依次添加
        DDDModuleConfigStep moduleConfigStep = new DDDModuleConfigStep(new ProjectConfigUI());

        return new ModuleWizardStep[]{moduleConfigStep};
    }
}
```

- 在 createWizardSteps 方法中，把我们已经创建好的 `DDDModuleConfigStep` 添加工程配置步骤，可以自己定义需要的步骤，如果有多个可以依次添加。
- 同时需要注意，只有重写了 getBuilderId() 方法后，你新增加的向导步骤才能生效。

### 4. 开发脚手架服务

**cn.bugstack.guide.idea.plugin.domain.service.AbstractProjectGenerator**

![](https://bugstack.cn/assets/images/2020/all-28-5.png)

```java
public abstract class AbstractProjectGenerator extends FreemarkerConfiguration implements IProjectGenerator {

    @Override
    public void doGenerator(Project project, String entryPath, ProjectConfigVO projectConfig) {

        // 1.创建工程主POM文件
        generateProjectPOM(project, entryPath, projectConfig);

        // 2.创建四层架构
        generateProjectDDD(project, entryPath, projectConfig);

        // 3.创建 Application
        generateApplication(project, entryPath, projectConfig);

        // 4. 创建 Yml
        generateYml(project, entryPath, projectConfig);

        // 5. 创建 Common
        generateCommon(project, entryPath, projectConfig);
    }

}
```

- 在 domain 领域层添加用于创建脚手架框架的 FreeMarker 服务，它是一款 模板引擎： 即一种基于模板和要改变的数据， 并用来生成输出文本(HTML网页，电子邮件，配置文件，源代码等)的通用工具。FreeMarker 在线手册：[http://freemarker.foofun.cn](http://freemarker.foofun.cn/)
- 按照 DDD 工程结构，分层包括：application、domain、infrastructure、interfaces，那么我们把这些创建过程抽象到模板方法中，具体交给子类来创建。

### 5. 调用脚手架服务

**cn.bugstack.guide.idea.plugin.module.DDDModuleBuilder**

```java
public class DDDModuleBuilder extends ModuleBuilder {

    private IProjectGenerator projectGenerator = new ProjectGeneratorImpl();

    @Override
    public Icon getNodeIcon() {
        return ICONS.SPRING_BOOT;
    }

    @Override
    public void setupRootModel(@NotNull ModifiableRootModel rootModel) throws ConfigurationException {

        // 设置 JDK
        if (null != this.myJdk) {
            rootModel.setSdk(this.myJdk);
        } else {
            rootModel.inheritSdk();
        }

        // 生成工程路径
        String path = FileUtil.toSystemIndependentName(Objects.requireNonNull(getContentEntryPath()));
        new File(path).mkdirs();
        VirtualFile virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByPath(path);
        rootModel.addContentEntry(virtualFile);

        Project project = rootModel.getProject();

        // 创建工程结构
        Runnable r = () -> new WriteCommandAction<VirtualFile>(project) {
            @Override
            protected void run(@NotNull Result<VirtualFile> result) throws Throwable {
                projectGenerator.doGenerator(project, getContentEntryPath(), DataSetting.getInstance().getProjectConfig());
            }
        }.execute();

    }

}
```

- 在 `DDDModuleBuilder#setupRootModel` 中，添加创建 `DDD工程框架`的服务，`projectGenerator.doGenerator(project, getContentEntryPath(), DataSetting.getInstance().getProjectConfig());`
- 另外这里需要用到 IDEA 提供的线程调用方法，`new WriteCommandAction` 才能正常创建。

### 6. 配置模板工程

#### 6.1 模板工厂

**cn.bugstack.guide.idea.plugin.factory.TemplateFactory**

```java
public class TemplateFactory extends ProjectTemplatesFactory {

    @NotNull
    @Override
    public String[] getGroups() {
        return new String[]{"DDD脚手架"};
    }

    @Override
    public Icon getGroupIcon(String group) {
        return ICONS.DDD;
    }

    @NotNull
    @Override
    public ProjectTemplate[] createTemplates(@Nullable String group, WizardContext context) {
        return new ProjectTemplate[]{new BuilderBasedTemplate(new DDDModuleBuilder())};
    }

}
```

- 模板工厂的核心在于把我们用于创建 `DDD 的步骤`添加 `createTemplates` 方法中，这样算把整个创建自定义脚手架工程的链路就串联完成了。

#### 6.2 文件配置

**plugin.xml**

```xml
<idea-plugin>
    <id>cn.bugstack.guide.idea.plugin.guide-idea-plugin-scaffolding</id>
    <name>Scaffolding</name>
    <vendor email="184172133@qq.com" url="https://bugstack.cn">小傅哥</vendor>

    <!-- please see http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/plugin_compatibility.html
         on how to target different products -->
    <depends>com.intellij.modules.platform</depends>

    <extensions defaultExtensionNs="com.intellij">
        <projectTemplatesFactory implementation="cn.bugstack.guide.idea.plugin.factory.TemplateFactory"/>
        <applicationService serviceImplementation="cn.bugstack.guide.idea.plugin.infrastructure.DataSetting"/>
    </extensions>

</idea-plugin>
```

- 接下来还需要把我们创建的工程模板以及数据服务配置到 `plugin.xml` 中，这样在插件启动的时候就可以把我们自己插件启动起来了。

## 四、测试验证

- 点击 `Plugin` 启动 IDEA 插件，之后创建工程如下：

![](https://bugstack.cn/images/article/assembly/assembly-211123-4-02.png)

- 快拿去试试吧，启动插件，点击创建工程，傻瓜式点击，就可以创建出一个 DDD 工程结构了。

## 五、总结

- 学习使用 IDEA Plugin 开发技术，改变创建工程向导，添加自己需要的工程创建模板，这样就可以创建出一个 DDD 脚手架工程骨架了，接下来你还可以结合自己实际的业务场景添加自己需要的一些技术栈到脚手架中。
- 如果你愿意尝试可以在工程创建中链接到数据库，把数据库中对应的表生成Java代码，这样一些简单的配置、查询、映射，就不用自己动手写了。
- 在开发 DDD 脚手架的源码中还有一些细节过程，包括图标的展示、文案的信息、Freemarker的使用细节，这些你都可以在源码中学习并调试验证。

## 六、系列推荐

- [使用 Freemarker，创建 SpringBoot 脚手架](https://bugstack.cn/md/develop/framework/scheme/2021-03-14-%E4%B8%8D%E9%87%8D%E5%A4%8D%E9%80%A0%E8%BD%AE%E5%AD%90%E5%8F%AA%E6%98%AF%E9%AA%97%E5%B0%8F%E5%AD%A9%E5%AD%90%E7%9A%84%EF%BC%8C%E6%95%99%E4%BD%A0%E6%89%8B%E6%92%B8%20SpringBoot%20%E8%84%9A%E6%89%8B%E6%9E%B6%EF%BC%81.html)
- [发布Jar包到Maven中央仓库(为开发开源中间件做准备)](https://bugstack.cn/md/assembly/middleware/2019-12-07-%E5%8F%91%E5%B8%83Jar%E5%8C%85%E5%88%B0Maven%E4%B8%AD%E5%A4%AE%E4%BB%93%E5%BA%93%EF%BC%8C%E4%B8%BA%E5%BC%80%E5%8F%91%E5%BC%80%E6%BA%90%E4%B8%AD%E9%97%B4%E4%BB%B6%E5%81%9A%E5%87%86%E5%A4%87.html)
- [DDD 领域层决策规则树服务设计](https://bugstack.cn/md/develop/framework/ddd/2019-10-16-DDD%E4%B8%93%E9%A2%98%E6%A1%88%E4%BE%8B%E4%BA%8C%E3%80%8A%E9%A2%86%E5%9F%9F%E5%B1%82%E5%86%B3%E7%AD%96%E8%A7%84%E5%88%99%E6%A0%91%E6%9C%8D%E5%8A%A1%E8%AE%BE%E8%AE%A1%E3%80%8B.html)
- [工作两三年了，整不明白架构图都画啥？](https://bugstack.cn/md/develop/framework/scheme/2021-02-28-%E5%B7%A5%E4%BD%9C%E4%B8%A4%E4%B8%89%E5%B9%B4%EF%BC%8C%E6%95%B4%E4%B8%8D%E6%98%8E%E7%99%BD%E6%9E%B6%E6%9E%84%E5%9B%BE%E9%83%BD%E7%94%BB%E5%95%A5%EF%BC%9F.html)
- [CodeGuide Github 仓库开源啦！](https://bugstack.cn/md/about/me/2021-11-14-CodeGuide%E5%BC%80%E6%BA%90%E4%BB%93%E5%BA%93.html)