---
title: 第 5 章：IDEA工程右键菜单，自动生成ORM代码
lock: need
---

# 《IntelliJ IDEA 插件开发》第 5 章：IDEA工程右键菜单，自动生成ORM代码

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>原文：[https://mp.weixin.qq.com/s/4DFCF83bySTYyBZgouX6-w](https://mp.weixin.qq.com/s/4DFCF83bySTYyBZgouX6-w)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`都能用，都能凑活用！`

一个东西好几套，为了晋升都来搞。拿了成绩就要跑，后面兄弟再重造！

几年前，大家并不是这样，那时候还有很多东西可以创新，乱世出英雄总能在一个方向深耕并做出一款款好用的产品功能、框架服务、技术组件等。但后来好像这样的情况开始减少了，取而代之的是重复、复刻、照搬，换个新的皮肤、换个新的样式、换个新的名字，就是取巧的新东西了。

有时候公司或者组织也像家，但家里的东西一般是破了补补、坏了修修，实在不行就换个，但没有谁的家里卫生间一个马桶、厨房一个马桶、客厅一个马桶、卧室一个马桶的，虽然你的新马桶可以自动喷水。

所以，在建设一个好的产品功能时，尽可能要学学那些已经非常的优秀的产品，IDEA、GitHub、Mysql等等，在IDEA提供了满足用户扩展功能的插件开发，而不是你说一个东西我没有，你就自己造。*共建会让这个东西变得更加优秀！*

## 二、需求目的

在上一章节中我们通过扩展创建工程向导，添加我们需要创建DDD工程脚手架的步骤，最终提供一个DDD开发框架。那么在这个DDD工程开发框架中，还缺少一部分基于数据库表信息自动生成对应PO、DAO、Mapper文件的功能。

![](https://bugstack.cn/images/article/assembly/assembly-211207-5-01.png)

- 那么本章节我们就来在工程中扩展这部分内容，实际操作的效果就是我们可以在工程上通过鼠标右键的方式，唤出添加ORM代码块的窗体，通过选择库表的方式，使用 freemarker 自动生成代码。
- 在生成的代码块中需要完成对所需要包的引入，同时会使用到 lombok 注解的方式替代PO对象中的get、set方法，以减少代码量逻辑的创建。

## 三、案例开发

### 1. 工程结构

```java
guide-idea-plugin-orm
├── .gradle
└── src
    ├── main
    │   └── java
    │   	└── cn.bugstack.guide.idea.plugin 
    │       	├── action
    │       	│	└── CodeGenerateAction.java      
    │       	├── domain
    │       	│	├── model.vo 
    │       	│	│	├── CodeGenContextVO.java       
    │       	│	│	└── ORMConfigVO.java       
    │       	│	└── service   
    │       	│	 	├── impl     
    │       	│	 	│	└── ProjectGeneratorImpl.java  
    │       	│	 	├── AbstractProjectGenerator.java     
    │       	│	 	├── GeneratorConfig.java      
    │       	│	 	└── IProjectGenerator.java      
    │       	├── infrastructure
    │       	│	├── data    
    │       	│	│	├── DataSetting.java       
    │       	│	│	└── DataState.java      
    │       	│	├── po    
    │       	│	│	├── Base.java    
    │       	│	│	├── Column.java 
    │       	│	│	├── Dao.java 
    │       	│	│	├── Field.java 
    │       	│	│	├── Model.java     
    │       	│	│	└── Table.java       
    │       	│	└── utils 
    │       	│		├── DBHelper.java     
    │       	│		└── JavaType.java      
    │       	├── module  
    │       	│	└── FileChooserComponent.java         
    │       	└── ui
    │       	 	├── ORMSettingsUI.java  
    │       	 	└── ORMSettingsUI.form
    ├── resources
    │   ├── META-INF
    │   │   └── plugin.xml 
    │   └── template
    │       ├── dao.ftl
    │       ├── mapper.ftl
    │       └── model.ftl
    ├── build.gradle  
    └── gradle.properties
```

**源码获取**：#公众号：`bugstack虫洞栈` 回复：`idea` 即可下载全部 IDEA 插件开发源码

在此 IDEA 插件工程中，主要分为5块区域：

- action：用于提供菜单栏，这个菜单的位置在 plugin.xml 中配置，我们把它配置到工程鼠标右键出现的列表上。*这样可以更加方便的让我们选取工程，以及在这个工程下添加生成的代码片段*
- domain：领域服务层，其实你直接写一个Service包也是可以的，只不过最近作者小傅哥更喜欢使用DDD的思想和结构来创建代码实现功能逻辑。
- infrastructure：基础层，提供数据在工程下的存放，每一个工程右键都有自己的配置存储默认信息，方便下次打开的时候可以读取到这部分内容。同时这一层还提供了用于处理数据库操作的类，因为我们需要从数据库中读取出表的信息、字段、注释，用于创建PO、DAO、Mapper使用。
- module：模块层，这里提供了一个用于选择文件路径的组件，可以让我们在工程上鼠标右键后出来的窗体中，点击模块选择对应的要生成代码的位置路径。
- ui：提供配置面板，也就是我们在代码工程上鼠标右键弹出来的面板，这个面板配置后用于生成ORM代码。

### 2. 拖拽Swing面板

**ORMSettingsUI**：咱们先把用于创建代码配置的面板创建出来，有了画面，就好进入了。

![](https://bugstack.cn/images/article/assembly/assembly-211207-5-02.png)

- 面板包括生成 PO、DAO、XML 的代码路径，以及配置数据库和选择表的内容。
- 操作过程就是在你配置好了这些基本信息后，就可以选择查询表名，并选择好你要给哪几个表生成对应的ORM代码了。

### 3. 配置鼠标右键弹窗

首先我们需要创建一个 Action 实现类，通过 `New -> Plugin DevKit -> Action`

![](https://bugstack.cn/images/article/assembly/assembly-211207-5-03.png)

**cn.bugstack.guide.idea.plugin.action.CodeGenerateAction**

```java
/**
 * @author: 小傅哥，微信：fustack
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class CodeGenerateAction extends AnAction {

    private IProjectGenerator projectGenerator = new ProjectGeneratorImpl();

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        ShowSettingsUtil.getInstance().editConfigurable(project, new ORMSettingsUI(project, projectGenerator));
    }

}
```

- 这是一个右键菜单的入口，通过这个入口才能去打开我们自己的UI窗体，这个UI窗体就是我们上面拖拽出来的配置面板，ORMSettingsUI
- 接下来我们还需要把这个 Action 配置到 plugin.xml 文件中，才能被右键菜单创建出来。*开发代码的时候也是这样一个流程，你总要从一个点开始，有了抓手才好抓下去*

**plugin.xml 配置**

```xml
<actions>
    <!-- Add your actions here -->
    <action id="CodeGenerateAction" class="cn.bugstack.guide.idea.plugin.action.CodeGenerateAction"
            text="ORMCodeGenerate - 小傅哥" description="Code Generate ORM" icon="/icons/logo.png">
        <add-to-group group-id="ProjectViewPopupMenu" anchor="last"/>
    </action>
</actions>
ea-plugin>
```

- 把我们的 Action 实现类配置到 xml 中，同时你还要配置它应该出现的位置，比如你需要把这个菜单添加到工程创建中 `ProjectViewPopupMenu` 以及位置信息 `anchor="last"`
- 另外为了让插件看上去更加高大上还美观适合吹牛，那么还需要配置 icon，这个位置配置一个`16*16`的图片，图片可以从 [iconfont](https://www.iconfont.cn/) 进行下载。

### 4. 给窗体添加功能

这一步其实干的就是注入灵魂的事情，让窗体活起来。给输入框添加内容、给按钮添加事件、给确认按钮增加上生成创建ORM代码块的上下文。*文章的描述尽可能会偏向于核心代码的讲解，详情可以参考源码*

接下来这部分内容会在 ORMSettingsUI 类中**反复摩擦**，直到补全所有功能。

#### 4.1 选择框事件

![](https://bugstack.cn/images/article/assembly/assembly-211207-5-04.png)

```java
// 选择PO生成目录
this.poButton.addActionListener(e -> {
    FileChooserComponent component = FileChooserComponent.getInstance(project);
    VirtualFile baseDir = project.getBaseDir();
    VirtualFile virtualFile = component.showFolderSelectionDialog("选择PO生成目录", baseDir, baseDir);
    if (null != virtualFile) {
        ORMSettingsUI.this.poPath.setText(virtualFile.getPath());
    }
});
```

- 还记得我们提到的`拖拽Swing面板`吗，那么这个添加事件的步骤就是给你的 PO 目录添加一个事件，允许我们可以自己选择出要把对应PO的代码生成到哪个目录结构下。
- 关于dao、xml都是类似操作，这里就不在演示了。

#### 4.2 数据表事件

![](https://bugstack.cn/images/article/assembly/assembly-211207-5-05.png)

```java
this.selectButton.addActionListener(e -> {
    try {
        DBHelper dbHelper = new DBHelper(this.host.getText(), Integer.parseInt(this.port.getText()), this.user.getText(), this.password.getText(), this.database.getText());
        List<String> tableList = dbHelper.getAllTableName(this.database.getText());
        String[] title = {"", "表名"};
        Object[][] data = new Object[tableList.size()][2];
        for (int i = 0; i < tableList.size(); i++) {
            data[i][1] = tableList.get(i);
        }
        table1.setModel(new DefaultTableModel(data, title));
        TableColumn tc = table1.getColumnModel().getColumn(0);
        tc.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        tc.setCellEditor(table1.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table1.getDefaultRenderer(Boolean.class));
        tc.setMaxWidth(100);
    } catch (Exception exception) {
        Messages.showWarningDialog(project, "数据库连接错误,请检查配置.", "Warning");
    }
});
```

- 这一步操作核心流程就在于把你需要生成ORM的代码的表给拉出来，只要把表选择上，才能根据表生成PO、DAO、Mapper，其实你用的其他一些自动生成代码框架也是这么干的。
- 另外你的建表最好规范，比如有表注释、有字段注释、字段的设计遵守下划线和小写字母，这样会更加容易创建出好看的代码。

#### 4.3 组装生成代码上下文

当我们点击配置窗体的 **OK** 按钮时候，要干啥，对喽，我们要创建出代码片段了，那么这个时候需要在重写的 `apply` 中完成此项操作。

```java
public void apply() {
    // 链接DB
    DBHelper dbHelper = new DBHelper(config.getHost(), Integer.parseInt(config.getPort()), config.getUser(), config.getPassword(), config.getDatabase());
    
    // 组装代码生产上下文
    CodeGenContextVO codeGenContext = new CodeGenContextVO();
    codeGenContext.setModelPackage(config.getPoPath() + "/po/");
    codeGenContext.setDaoPackage(config.getDaoPath() + "/dao/");
    codeGenContext.setMapperDir(config.getXmlPath() + "/mapper/");
    List<Table> tables = new ArrayList<>();
    Set<String> tableNames = config.getTableNames();
    for (String tableName : tableNames) {
        tables.add(dbHelper.getTable(tableName));
    }
    codeGenContext.setTables(tables);
    
    // 生成代码
    projectGenerator.generation(project, codeGenContext);
}
```

- 在 apply 中的核心代码主要就是使用 DBHelper 数据操作工具获取到对应的库下链接信息，同时把选择的号的表创建出用于生成代码类的参数，比如；类的名称、字段名称、注释名称等。
- 最后就是调用生成代码的服务了，`projectGenerator.generation(project, codeGenContext);` 这一部分就是在我们领域服务 domain 中实现的。

### 5. 代码生成领域服务

![](https://bugstack.cn/images/article/assembly/assembly-211207-5-06.png)

- 用于创建PO、DAO、Mapper的代码块的代码主要是这里实现的，核心在于提供了一个抽象类以及对应的实现类，因为处理代码生成需要使用到 freemarker 所以就在抽象类里包装了下，这样可以免去实现类中还需要关心这部分逻辑。

**ProjectGeneratorImpl 生成代码**

```java
@Override
protected void generateORM(Project project, CodeGenContextVO codeGenContext) {
    List<Table> tables = codeGenContext.getTables();
    for (Table table : tables) {
        List<Column> columns = table.getColumns();
        List<Field> fields = new ArrayList<>();
        for (Column column : columns) {
            Field field = new Field(column.getComment(), JavaType.convertType(column.getType()), column.getName());
            field.setId(column.isId());
            fields.add(field);
        }
        // 生成PO
        Model model = new Model(table.getComment(), codeGenContext.getModelPackage() + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getName()), table.getName(), fields);
        writeFile(project, codeGenContext.getModelPackage(), model.getSimpleName() + ".java", "domain/orm/model.ftl", model);
        
        // 生成DAO
        Dao dao = new Dao(table.getComment(), codeGenContext.getDaoPackage() + "I" + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getName()) + "Dao", model);
        writeFile(project, codeGenContext.getDaoPackage(), dao.getSimpleName() + ".java", "domain/orm/dao.ftl", dao);
        
        // 生成Mapper
        writeFile(project, codeGenContext.getMapperDir(), dao.getModel().getSimpleName() + "Mapper.xml", "domain/orm/mapper.ftl", dao);
    }
}
```

- 创建代码的过程就比较简单了，通过循环提取出来的表信息，映射成对应的类和属性以及注释，再使用 resources 下的 ftl 文件创建出对应的类和xml配置文件就可以了。
- 如果你还需要生成起来代码片段或者创建调用一些常用的组件，也是可以通过这样的方式进行实现的。

## 四、测试验证

- 点击 `Plugin` 启动 IDEA 插件，之后在工程右键如下：

**1. 鼠标右键，选择菜单**

![](https://bugstack.cn/images/article/assembly/assembly-211207-5-07.png)

**2. 配置页面，配置信息**

![](https://bugstack.cn/images/article/assembly/assembly-211207-5-08.png)

**3. 自动创建，生成代码**

![](https://bugstack.cn/images/article/assembly/assembly-211207-5-09.png)

- 好了，选择代码块就这么嗖的创建了出来，是不是非常方便，而且可以满足你在任何时候的把新的库表代码补充进来，减少了手敲CRUD操作。

## 五、总结

- 本章节小傅哥带着你又在 IDEA DDD 插件生成工程的结构下，又完善了一步生成ORM代码，当然你也可以在创建工程向导中添加生成ORM代码的步骤。而在工程下创建ORM的方式可以当做是对脚手架工程的补充，满足不同场景下的需求。
- 此外在 IDEA 插件开发的系列内容中我们是不断的尝试使用新的方式完善不同的功能点，如果你需要开发一个完整的插件那么可以结合这些功能一起来开发你的需求。
- 插件开发中还是有很多的内容需要了解和学习的，同时也要注意一些代码实现细节，例如我们本章节中的数据保存是在一个什么维度，是IDEA开发工具维度，还是在IDEA中的工程维度，这些是有区别。*比如你的不同工程，是不需要保存同一份配置的*