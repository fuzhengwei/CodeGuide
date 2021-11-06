---
title: 第2节：配置窗体和侧边栏窗体的使用
lock: need
---

# 《IntelliJ IDEA 插件开发》第2节：配置窗体和侧边栏窗体的使用

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>原文：[https://mp.weixin.qq.com/s/R8qvoSNyedVM95Ty8sbhgg](https://mp.weixin.qq.com/s/R8qvoSNyedVM95Ty8sbhgg)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、说明

`方向不对，努力白费！`

总有人拿到产品的需求，就着急开干，反正也懒的想开发中会发生啥，上线后多少人使用，管它三七二十一先堆起来代码看一看，反正能跑就行，无论代码还是你！

其实很多时候在编写代码前，所需要做的技术调研、架构设计、模块分层、数据结构、详细分析、方案评审等，与`三七二十一那家伙`对比起来，好像都会显得有点慢。但这个看上去慢的过程，却能解决以后很多常见和麻烦的问题，比如产品需求迭代、业务流程变更、代码逻辑更改、线上异常排查。虽然看着慢，但这个积基树本的过程就像打地基一样，总得有一个稳定的根基，才能盖好整栋大楼。**万丈高楼平地起，勿在浮沙筑高台**

## 二、需求目的

如果你需要开发一个自定义功能的插件，无论是处理代码、辅助ORM生成、日志信息记录等，都会需要进行一个插件的功能配置进行初始化操作以及把对应功能展示到整个 IDEA 窗体中的右边栏或者下边栏中，这样才能满足一个插件的基本需求。

那么这样就需要在 IDEA 窗体 `File -> Settings` 中扩展自己的配置窗体，以及开发自己需要的 ToolWindow 嵌入到 IDEA 中(左侧、右侧、下侧)，这里窗体的开发需要用到 Swing 但目前在 IDEA 中开发这样的功能只需要拖拽窗体就可以，还是蛮容易的。

那么接下来我们以一个在 IDEA 中摸鱼看书的场景为案例，学习配置窗体和阅读窗体的功能实现。

## 三、案例开发

### 1. 工程结构

```java
guide-idea-plugin-tool-window
├── .gradle
└── src
    ├── main
    │   └── java
    │   	└── cn.bugstack.guide.idea.plugin 
    │       	└── factory
    │       	│	├── ReadFactory.java 
    │       	│	└── SettingFactory.java
    │       	└── ui
    │       	│	├── ReadUI.java 
    │       	│	├── ReadUI.form
    │       	│	├── SettingUI.java  
    │       	│	└── SettingUI.form
    │       	└── Config    
    ├── resources
    │   └── META-INF
    │       └── plugin.xml 
    ├── build.gradle  
    └── gradle.properties
```

- **源码获取**：#公众号：`bugstack虫洞栈` 回复：`idea` 即可下载全部 IDEA 插件开发源码

此工程主要涉及两部分，在factory中一个是配置窗体、一个是阅读窗体，与之对应的两组UI的实现。最后 factory 类的实现都会配置到 plugin.xml 中进行使用，同时也是在 plugin.xml  中控制窗体位置和图标。

### 2. 创建 UI 窗体

#### 2.1 创建方式

**New -> Swing UI Designer -> GUI Form**

![](https://bugstack.cn/images/article/assembly/assembly-211103-01.png)

- 在 Java 中创建窗体的方式主要有 AWT、Swing、JavaFx，由于 IDEA 使用 Swing 开发，所以这里创建 Swing 窗体的兼容性会更好。
- 那么这里 Swing 窗体的创建可以是自己手写窗体结构，也可以使用可视化拖拽的 GUI Form 如果你的窗体不复杂，其实拖拽的方式就可以满足使用。

#### 2.2 配置页窗体

```java
public class SettingUI {

    private JPanel mainPanel;
    private JPanel settingPanel;
    private JLabel urlLabel;
    private JTextField urlTextField;
    private JButton urlBtn;

    public SettingUI() {
        // 给按钮添加一个选择文件的事件
        urlBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.showOpenDialog(settingPanel);
            File file = fileChooser.getSelectedFile();
            urlTextField.setText(file.getPath());
        });
    }

    public JComponent getComponent() {
        return mainPanel;
    }

    public JTextField getUrlTextField() {
        return urlTextField;
    }
}
```

![](https://bugstack.cn/images/article/assembly/assembly-211103-02.png)

- 配置页窗体主要提供文章路径的选择，这里需要用到的标签包括：JLabel、JTextField、JButton
- 在使用  GUI Form 创建完窗体后，就会出现这样一个可视化的页面，右侧可以把各类标签拖到中间的面板中，左侧进行设置展示名称和属性名称。
- 最终这里的代码标签代码会展示到 `SettingUI.java` 中，而渲染内容会被隐藏，这样的方式也比较方便控制一些自定义内容的添加，例如事件和新窗体等
- 另外在 `SettingUI.java` 中，还需要在构造函数添加一个按钮事件，用于打开文件选择器，把我们需要打开的文件，设置到 `urlTextField` 中。

#### 2.3 阅读页窗体

```java
public class ReadUI {

    private JPanel mainPanel;
    private JTextPane textContent;

    public JComponent getComponent() {
        return mainPanel;
    }

    public JTextPane getTextContent() {
        return textContent;
    }

}
```

![](https://bugstack.cn/images/article/assembly/assembly-211103-03.png)

- 在窗体创建和配置页窗体是一样的，也是通过拖拽到面板中，用于展示路径文件内容。
- 你可以适当的添加一些其他按钮进去，比如翻页阅读、滚动条、字数展示等。

### 3. ToolWindow 工具框

为了把我们自己实现的`阅读窗体`放到整个 IDEA 右侧侧边栏中，我们需要创建一个实现了 `ToolWindowFactory` 的接口，并把实现类配置到 plugin.xml 中

```java
public class ReadFactory implements ToolWindowFactory {

    private ReadUI readUI = new ReadUI();

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        // 获取 ToolWindow 显示的内容
        Content content = contentFactory.createContent(readUI.getComponent(), "", false);
        // 设置 ToolWindow 显示的内容
        toolWindow.getContentManager().addContent(content);
        // 全局使用
        Config.readUI = readUI;
    }

}
```

- 接口方法 `ToolWindowFactory#createToolWindowContent` 是需要自己工具框类实现的方法，在这个 `createToolWindowContent` 方法中把自己的窗体 `ReadUI` 实例化后填充进去即可。
- 添加窗体的补助主要依赖于 `ContentFactory.SERVICE.getInstance()` 创建出 ContentFactory 并最终使用 toolWindow 添加窗体显示 UI 即可。
- 这里我们额外的还添加了一个全局属性 `Config.readUI` 这是为了后续可以在配置窗体中使用这个 UI 进行设置文件内容。

### 4. Configurable 配置框

```java
public class SettingFactory implements SearchableConfigurable {

    private SettingUI settingUI = new SettingUI();

    @Override
    public @NotNull String getId() {
        return "test.id";
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return "test-config";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return settingUI.getComponent();
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        String url = settingUI.getUrlTextField().getText();
        // 设置文本信息
        try {
            File file = new File(url);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek(0);

            byte[] bytes = new byte[1024 * 1024];
            int readSize = randomAccessFile.read(bytes);

            byte[] copy = new byte[readSize];
            System.arraycopy(bytes, 0, copy, 0, readSize);

            String str = new String(copy, StandardCharsets.UTF_8);

            // 设置内容
            Config.readUI.getTextContent().setText(str);

        } catch (Exception ignore) {
        }
    }

}
```

- 实现自 SearchableConfigurable 接口的方法比较多，包括：getId、getDisplayName、`createComponent`、isModified、`apply` 这些里面用于写逻辑实现的主要是 `createComponent` 和 `apply`  
- createComponent 方法主要是把我们自己创建的 UI 面板提供给 JComponent
- apply 是一个事件，当我们点击完成配置的 OK、完成，时候就会触发到这个方法。在这个方法中我们拿到文件的 URL 地址使用 `RandomAccessFile` 进行读取解析文件，并最终把文件内容展示到阅读窗体中 `Config.readUI.getTextContent().setText(str);`

### 5. 配置 plugin.xml

```java
<extensions defaultExtensionNs="com.intellij">
    <!-- Add your extensions here -->
    <!-- 配置 File -> Settings -> Tools -->
    <projectConfigurable groupId="tools" displayName="My Test Config" id="test.id"
                         instance="cn.bugstack.guide.idea.plugin.factory.SettingFactory"/>
                         
    <!-- 窗体 (IDEA 界面右侧) -->
    <toolWindow id="Read-Book" secondary="false" anchor="right" icon="/icons/logo.png"
                factoryClass="cn.bugstack.guide.idea.plugin.factory.ReadFactory"/>
</extensions>
```

- 本次在 plugin.xml 中的主要配置内容就是 projectConfigurable 和 toolWindow，另外在 toolWindow 中还添加了一个 icon 的 logo，配置完成后就可以在 IDEA 页面展示出我们的自己添加的窗体了。

## 四、插件测试

- 通过 Plugin 启动插件，这个时候会打开一个新的 IDEA 窗体，在这个新窗体中就可以看到我们添加的功能了。

**配置文件路径**

![](https://bugstack.cn/images/article/assembly/assembly-211103-04.png)

- 点击选择按钮，选择你的文件位置，选择后点击 OK 

**查看展示文件**

![](https://bugstack.cn/images/article/assembly/assembly-211103c-05.png)

- 确认好文件路径后，就可以再右侧栏看到自己的文件展示内容了。*是不是在扩展些，就适合你摸鱼了！？*

## 五、总结

- 学习自定义开发UI，把UI填充到需要放置的 IDEA 窗体位置，并在窗体中添加功能的流程步骤，其实主要包括三方面：Swing UI、Factory 实现类、plugin 配置。
- 在 plugin 配置中，主要包括如窗体ID、位置、icon图标、对应的实现类，如果不添加这些是不能正常展示窗体信息的。
- 另外可以以这个案例为基础，添加自己想完成的功能，比如让这个摸鱼看书的功能更加完善，可以支持不同类型的文件，甚至可以是 PDF 的阅读，以及你想看的书籍。