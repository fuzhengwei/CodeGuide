---
title: 第6节：选定对象批量织入“x.set(y.get)”代码，自动生成vo2dto
lock: need
---

# 《IntelliJ IDEA 插件开发》第6节：选定对象批量织入“x.set(y.get)”代码，自动生成vo2dto

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`给你机会，你也不中用啊`

这些年从事编程开发以来，我好像发现了大部分研发那些不愿意干的事，都成就了别人。**就像部署服务麻烦，有了Docker**、**简单CRUD不想开发，有了低代码**、**给方法代码加监控繁琐、有了非入侵的全链路监控**。

而这些原本你也在干的事情，因为没有想法、没有创新、没有思考，也可能是没有能力，所以一直都是在搬砖、码砖、砌砖，反反复复、来来回回。键盘敲的是越来越快了，代码搞的是越来越烂了。薪资没搞上去，头发是越来越少了。

对于想走技术路线的码农，千万不要只是停留在业务功能的逻辑开发上，只有当你有了共性凝练的逻辑思维，才会逐步思考怎么把一件重复的事做成一个通用的服务或者组件，而这些东西的落地不仅需要你会写代码，还要会思考更要会去索引一些你需要的技术，并用自学的方式来补充这部分技能。

## 二、需求目的

你想写对象间的`get、set`吗？烦，烦死了，尤其是在DDD四层架构下，有了多层防污处理，一会一个vo2dto、一会一个vo2do、一会一个do2po，虽然有很多工具的操作，但还是得写呀。

怎么办？不要慌，这是机会呀，我们做个插件搞定它，让它可以自动的给我生成`get、set`代码，在IDEA Plugin的处理下，选择好需要生成对象代码的锚点，复制下转换对象，自动织入代码，1s钟搞定！

## 三、案例开发

### 1. 工程结构

```java
guide-idea-plugin-vo2dto
├── .gradle
└── src
    ├── main
    │   └── java
    │   	└── cn.bugstack.guide.idea.plugin 
    │       	├── action
    │       	│	└── Vo2DtoGenerateAction.java     
    │       	├── application
    │       	│	└── IGenerateVo2Dto.java      
    │       	├── domain
    │       	│	├── model
    │       	│	│	├── GenerateContext.java     
    │       	│	│	├── GetObjConfigDO.java      
    │       	│	│	└── SetObjConfigDO.java       
    │       	│	└── service   
    │       	│	 	├── impl     
    │       	│	 	│	└── GenerateVo2DtoImpl.java    
    │       	│	 	└── AbstractGenerateVo2Dto.java      
    │       	└── infrastructure   
    │       	 	└── Utils.java    
    ├── resources
    │   └── META-INF
    │       └── plugin.xml 
    ├── build.gradle  
    └── gradle.properties
```

**源码获取**：#公众号：`bugstack虫洞栈` 回复：`idea` 即可下载全部 IDEA 插件开发源码

在此 IDEA 插件工程中，主要分为4块区域：

- action：提供菜单栏窗体，在插件中我们把这个菜单栏配置到 `Generate` 下，也就是通常你生成 get、set、constructor 方法的地方。
- application：应用层定义接口，这里定义了一个用于生成代码并织入到锚点的方法接口。
- domian：领域层专门处理代码的生成和织入动作，这一层把代码的中锚点位置获取、剪切板信息复制、应用上下文、类中get、set的解析，以及最终把生成代码织入到锚点后的操作。
- infrastructure：在基础层提供了工具类，用于获取剪切板信息和锚点位置判断等操作。

### 2. 织入代码接口

**cn.bugstack.guide.idea.plugin.application.IGenerateVo2Dto**

```java
public interface IGenerateVo2Dto {

    void doGenerate(Project project, DataContext dataContext);

}
```

- 定义接口其实非常重要的一步，因为这样一步就把生成的标准定义下来了，所有的生成动作都要从这个接口发起。*学习源码也一样，你要找到一个核心的入口点，才能更好的开始学习*

### 3. 定义模板方法

因为生成代码并织入锚点位置的操作，整个来看其实也是一套流程操作，因为在这个过程需要；获取上下文信息(也就是工程对象)、给当前锚点位置的类提取 set 方法集合、之后在给`Ctrl+C`剪切板上的信息读取出来提取 get 方法集合，第四步把set、get进行组合并织入代码到锚点位置。整体过程如下：

![](https://bugstack.cn/images/article/assembly/assembly-211214-6-01.png)

- 那么在使用模板方法后，就可以非常容易的把写在一个类里的成片的代码按照职责进行拆分。
- 同时因为有了模板的定义，也就定义出了整个一套标准流程，在流程规范下执行代码，后续再补充逻辑迭代功能也会更加容易。

### 4. 代码织入锚点

关于代码织入锚点前，我们在模板类中定义的方法，需要实现接口进行处理，重点包括：
1. 通过 `CommonDataKeys.EDITOR.getData(dataContext)`、`CommonDataKeys.PSI_ELEMENT.getData(dataContext)` 封装 GenerateContext 对象上下文信息，也就是一些类、锚点位置、文档编辑的对象。
2. 通过 PsiClass 获取光标位置对应的 Class 类信息，在通过 `psiClass.getMethods()` 读取对象方法，把 set 方法过滤出来，封装到集合中。
3. 通过 `Toolkit.getDefaultToolkit().getSystemClipboard()` 获取剪切板信息，也就是你在锚点位置给对象生成 `x.set(y.get)` 时，复制的 Y y 对象，并开始提取 get 方法，同样封装到集合中。
4. 那么最后就是代码的组装和织入动作了，这部分我们的代码如下；

**cn.bugstack.guide.idea.plugin.domain.service.impl.GenerateVo2DtoImpl**

```java
@Override
protected void weavingSetGetCode(GenerateContext generateContext, SetObjConfigDO setObjConfigDO, GetObjConfigDO getObjConfigDO) {
    Application application = ApplicationManager.getApplication();
    // 获取空格位置长度
    int distance = Utils.getWordStartOffset(generateContext.getEditorText(), generateContext.getOffset()) - generateContext.getStartOffset();
    application.runWriteAction(() -> {
        StringBuilder blankSpace = new StringBuilder();
        for (int i = 0; i < distance; i++) {
            blankSpace.append(" ");
        }
        int lineNumberCurrent = generateContext.getDocument().getLineNumber(generateContext.getOffset()) + 1;
        List<String> setMtdList = setObjConfigDO.getParamList();
        for (String param : setMtdList) {
            int lineStartOffset = generateContext.getDocument().getLineStartOffset(lineNumberCurrent++);
            new WriteCommandAction(generateContext.getProject()) {
                @Override
                protected void run(@NotNull Result result) throws Throwable {
                    generateContext.getDocument().insertString(lineStartOffset, blankSpace + setObjConfigDO.getClazzParamName() + "." + setObjConfigDO.getParamMtdMap().get(param) + "(" + (null == getObjConfigDO.getParamMtdMap().get(param) ? "" : getObjConfigDO.getClazzParam() + "." + getObjConfigDO.getParamMtdMap().get(param) + "()") + ");\n");
                    generateContext.getEditor().getCaretModel().moveToOffset(lineStartOffset + 2);
                    generateContext.getEditor().getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
                }
            }.execute();
        }
    });
}
```

- 织入代码的流程动作，主要是对set方法集合进行遍历，把对应的`x.set(y.get)`通过 `document.insertString` 到具体的位置和代码。
- 最终所有生成的代码方法织入完成，即完成了整个 `x.set(y.get)` 的过程。

### 5. 配置菜单入口

**plugin.xml**

```java
<actions>
    <!-- Add your actions here -->
    <action id="Vo2DtoGenerateAction" class="cn.bugstack.guide.idea.plugin.action.Vo2DtoGenerateAction"
            text="Vo2Dto - 小傅哥" description="Vo2Dto generate util" icon="/icons/logo.png">
        <add-to-group group-id="GenerateGroup" anchor="last"/>
        <keyboard-shortcut keymap="$default" first-keystroke="ctrl shift K"/>
    </action>
</actions>
```

- 这次我们给生成 `x.set(y.get)` 代码的操作加个快捷键，可以让我们更加方便的进行操作。

## 四、测试验证

点击 `Plugin` 启动 IDEA 插件，之后有`2步`操作；
1. 复制你需要被转换的对象，因为复制以后就可以被插件获取到剪切板信息了，也就能提取到get方法集合。
2. 把鼠标定义到需要转换设置值的对象，之后鼠标右键，选择 `Generate` -> `Vo2Dto - 小傅哥`

### 1. 复制对象

![](https://bugstack.cn/images/article/assembly/assembly-211214-6-02.png)

### 2. 生成对象

![](https://bugstack.cn/images/article/assembly/assembly-211214-6-03.png)

### 3. 最终效果

![](https://bugstack.cn/images/article/assembly/assembly-211214-6-04.png)

- 最终你就可以看到已经把你全部的对象转换，自动生成出来代码了，是不是很香。
- 如果你直接使用快捷键 `Ctrl + Shift + K` 也是可以自动生成的。

## 五、扩展接口

|                                                              |                                                              |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| 获取当前编辑的文件, 通过PsiFile可获得PsiClass, PsiField等    | `PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);`      |
| 获取当前的project对象                                        | `Project project = e.getProject();`                        |
| 获取数据上下文                                               | `DataContext dataContext = e.getDataContext();`            |
| 获取到数据上下文后，通过CommonDataKeys对象可以获得该File的所有信息 | `Editor editor = CommonDataKeys.EDITOR.getData(dataContext);<br />PsiFile psiFile = CommonDataKeys.PSI_FILE.getData(dataContext);<br />VirtualFile virtualFile = CommonDataKeys.VIRTUAL_FILE.getData(dataContext);` |
| GlobalSearchScope中有Project域，Moudule域，File域等等        | `PsiFile[] psiFiles = FilenameIndex.getFilesByName(project, name, GlobalSearchScope);` |
| 类似于IDE中的Find Usages操作                                 | `Query<PsiReference> search = ReferencesSearch.search(PsiElement);` |
| 重命名                                                       | `RenameRefactoring newName = RefactoringFactory.getInstance(Project).createRename(PsiElement, "newName");` |
| 搜索一个类的所有子类，重载方法较多，具体不再一一列出         | `Query<PsiClass> search = ClassInheritorsSearch.search(PsiClass);` |
| 根据类的全限定名查询PsiClass，下面这个方法是查询Project域    | `PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(classQualifiedName, GlobalSearchScope.projectScope(project));` |
| 获取Java类所在的Package                                      | `PsiPackage psiPackage = JavaPsiFacade.getInstance(Project).findPackage(classQualifiedName);` |
| 查找被特定方法重写的方法                                     | `Query<PsiMethod> search = OverridingMethodsSearch.search(PsiMethod);` |

## 六、总结

- 本章节中我们涉及了不少对工程对象的类和方法进行操作的处理，这些内容的实践也非常适合你在其他场景使用，比如给工程的接口生成一些自动化API的操作。
- 在给对象生成 `x.set(y.get)` 的时候，我也在思考该怎么更合理的把转换对象代入到插件的代码逻辑中，可能会想到是通过弹窗配置或者代码扫描到上一行，但这样的方式终究是不舒服的，考虑到实际自己编码的习惯操作，其实我们做这步的时候，复制是第一步动作，为了更好的体验，所以这里选择了用复制来处理这块的连接性问题。
- 本系列的 IDEA Plugin 开发都以遵循 DDD 工程结构思想为设计和实现，虽然整体内容看上去也不复杂，但希望这些框架的沉淀可以为 DDD 落地铺路，让更多的工程研发人员适应 DDD 结构。