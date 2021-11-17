---
title: 第3节：开发工具栏和Tab页，展示股票行情和K线
lock: need
---

# 《IntelliJ IDEA 插件开发》第3节：开发工具栏和Tab页，展示股票行情和K线

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`没招了，不写点刺激的，你总是不好好看！`

以前，我不懂。写的技术就是技术内容，写的场景就是场景分析，但从读者的阅读我发现，大家更喜欢的是技术与场景结合，尤其是用技术结合那些羞羞答答的场景，虽然嘴上都不说。

本章节主要是想给大家介绍关于在 IDEA 插件开发中如何使用工具栏和Tab页，来填充在 IDEA 中底部的窗体，就像 IDEA 中的控制台一样。但就这么写好像是够呛能有人看，因为大家只从技术点来看，很难发现这里面有什么应用场景，即使看了好像也不知道这技术能干啥。

**那咋办**，整点`刺激`的吧。大家不是喜欢`赔钱炒股`吗，那就把`股票行情`和`K线展示`结合到IDEA插件开发中。可以让学习插件的伙伴，都能在写代码疲惫的时候还能看一眼股票，也许在关键的时候还能提醒你赶紧抛出去！

## 二、需求目的

安全起见，需要在 IDEA 中以一个比较隐秘的角落，开发股票插件，让炒股的同学可以在紧张编码CRUD之余，不要忘记自己关注的股票购买和抛售。

那么为了解决这个问题，我们需要几个技术点，包括：股票数据接口、查询对象封装、IDEA 底部窗体和工具栏开发、定时任务扫描、Swing UI、股票代码配置和保存、窗体按钮事件监听等。

接下来我们就结合这些技术点，来解决实际的场景问题，看看如何在 IDEA 中开发一个股票插件。

## 三、案例开发

### 1. 工程结构

```java
guide-idea-plugin-tab
├── .gradle
└── src
    ├── main
    │   └── java
    │   	└── cn.bugstack.guide.idea.plugin 
    │       	├── domain
    │       	│	├── model
    │       	│	│	├── aggregates
    │       	│	│	│	└── StockResult.java    
    │       	│	│	└── vo  
    │       	│	│	 	├── Data.java 
    │       	│	│	 	├── GoPicture.java     
    │       	│	│	 	└── Stock.java       
    │       	│	└── service   
    │       	│	 	├── impl     
    │       	│	 	│	└── StockImpl.java    
    │       	│	 	└── IStock      
    │       	├── factory
    │       	│	└── TabFactory.java  
    │       	├── infrastructure
    │       	│	├── DataSetting.java       
    │       	│	└── DataState.java     
    │       	├── module
    │       	│	├── RefreshBar.java   
    │       	│	├── SettingBar.java    
    │       	│	└── ViewBars.java         
    │       	└── ui
    │       	│	├── ConsoleUI.java 
    │       	│	├── ConsoleUI.form
    │       	│	├── GidConfig.java  
    │       	│	└── GidConfig.form
    │       	└── Config    
    ├── resources
    │   └── META-INF
    │       └── plugin.xml 
    ├── build.gradle  
    └── gradle.properties
```

**源码获取**：#公众号：`bugstack虫洞栈` 回复：`idea` 即可下载全部 IDEA 插件开发源码

在此 IDEA 插件工程中，主要分为5块区域：
- domain：领域层，提供查询股票接口的数据服务，如果你是做的一些其他工具型功能，也可以把业务类的内容放到 domain 中实现。
- factory：工厂层，这里主要提供的是一个工具窗体生成的入口对象，来创建出我们自己添加的窗体内容。
- infrastructure：基础层，提供了数据存放对象，这个数据对象是一个可以落盘的操作，创建好的类配置到 plugin.xml  中即可。*这样我们配置好股票代码后，关机重启 IDEA 也可以把配置读取出来*
- module：模块，提供用于 UI 窗体使用的一些工具页操作。比如这里的 ViewBars 会在 TabFactory 中实例化，用于展示出你添加的窗体。
- ui：这一部分使用的是 IDEA 中自动拖拽生成的窗体，免去了手写的复杂性，一些简单的页面直接拖拽就可以。*这也是一种低代码哦！*

**接下来**，我们就分别看下每个核心功能点的实现过程，这个过程中你可以提前把代码下载下来，对照着学习会更加容易理解。

![](https://bugstack.cn/images/article/assembly/assembly-211118-3-01.png)

1. 实现 ToolWindowFactory 开发一个底部的窗体，用于承载所需的内容
2. 左侧是侧边工具栏，配置自选股、刷新股票指数
3. 右侧是2个 tab 页，分别用于展示股票数据和K线图，这里的数据则需要通过股票接口来提供

### 2. tab 页窗体

首先这里我们先使用 IDEA 插件开发中，Swing UI 功能，拖拽出2个简单的窗体。有了这样的一个基本结构大家的脑子里应该就可以`有画面`了。

#### 2.1 自选股配置窗体

![](https://bugstack.cn/images/article/assembly/assembly-211118-3-04.png)

```java
public class GidConfig implements Configurable {

    private JPanel mainPanel;
    private JPanel settingPanel;
    private JLabel gidLabel;
    private JTextField gidTextField;

    private ConsoleUI consoleUI;

    public GidConfig(ConsoleUI consoleUI){
        this.consoleUI = consoleUI;
    }

    public JTextField getGidTextField() {
        return gidTextField;
    }

    @Override
    public void apply() throws ConfigurationException {
        List<String> gidList = DataSetting.getInstance().getGids();
        gidList.clear();
        String[] gids = gidTextField.getText().trim().split(",");
        for (String gid : gids) {
            gidList.add(gid.trim());
        }
        // 刷新数据
        consoleUI.addRows(gidList);
    }

}
```

- 在 GidConfig 对应的 java 类中，可以对一些窗体中出现的属性进行获取。当用户点击这个窗体的确认按钮后，我们可以在 apply 中拿到用户配置的股票代码，并对其进行读取和设置股票数据。

#### 2.2 股票展示窗体

![](https://bugstack.cn/images/article/assembly/assembly-211118-3-05.png)

```java
public class ConsoleUI {

    private JTabbedPane tabbedPane1;
    private JPanel one;
    private JPanel two;
    private JLabel picMin;
    private JTable table;
    private JLabel picDay;

    // 查询数据服务
    private IStock stock = new StockImpl();

    private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][]{}, new String[]{"股票", "代码", "最新", "涨跌", "涨幅"});

    public ConsoleUI() {
        // 初始数据
        table.setModel(defaultTableModel);
        addRows(DataSetting.getInstance().getGids());

        // 添加事件
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                Object value = table.getValueAt(row, 1);
                GoPicture goPicture = stock.queryGidGoPicture(value.toString());
                try {
                    // 分钟K线
                    picMin.setSize(545, 300);
                    picMin.setIcon(new ImageIcon(new URL(goPicture.getMinurl())));

                    // 当日K线
                    picDay.setSize(545, 300);
                    picDay.setIcon(new ImageIcon(new URL(goPicture.getDayurl())));
                } catch (MalformedURLException m) {
                    m.printStackTrace();
                }
            }
        });
    }

    public JTabbedPane getPanel() {
        return tabbedPane1;
    }

    public void addRows(List<String> gids) {
        // 查询
        List<Data> dataList = stock.queryPresetStockData(gids);

        // 清空
        int rowCount = defaultTableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            defaultTableModel.removeRow(0);
        }

        // 添加
        for (Data data : dataList) {
            defaultTableModel.addRow(new String[]{data.getName(), data.getGid(), data.getNowPri(), data.getIncrease(), data.getIncrePer()});
            table.setModel(defaultTableModel);
        }
    }

}
```

- 展示股票的窗体对应的 ConsoleUI 类，主要负责数据的渲染、更新和对每条数据的事件操作，当用户点击某一条数据以后，就可以到 `K线` 页中看到对应的股票指数了。

### 3. 股票框体设置

在开发完 UI 窗体后，我们还需要使用一个 SimpleToolWindowPanel 的继承实现类，承载工具栏和页面的设置。

#### 3.1 设置-工具栏

**cn.bugstack.guide.idea.plugin.module.SettingBar**

```java
public class SettingBar extends DumbAwareAction {

    private ViewBars panel;

    public SettingBar(ViewBars panel) {
        super("配置股票", "Click to setting", IconLoader.getIcon("/icons/config.svg"));
        this.panel = panel;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ShowSettingsUtil.getInstance().editConfigurable(panel.getProject(), new GidConfig(panel.getConsoleUI()));
    }

}
```

- 设置工具栏位于自定义插件面板中最左侧的位置，用于设置自选股票代码。
- 通过在方法 `actionPerformed` 中使用 `ShowSettingsUtil` 工具类启动 UI 窗体。

#### 3.2 刷新-工具栏

**cn.bugstack.guide.idea.plugin.module.RefreshBar**

```java
public class RefreshBar extends DumbAwareAction {

    private ViewBars panel;

    public RefreshBar(ViewBars panel) {
        super("刷新指数", "Click to refresh", IconLoader.getIcon("/icons/refresh.svg"));
        this.panel = panel;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        panel.getConsoleUI().addRows(DataSetting.getInstance().getGids());
    }

}
```

- 在刷新工具栏中主要是用于手动触发刷新股票最新结果，之所以使用手动刷新主要是这个接口有查询次数限制，如果是定时任务一直跑，一会100次的查询限制就用完了。*不过我们这里也是为了体现专栏内对技术的使用，增加多个设置按钮，就更容易知道如何添加了*

#### 3.3 窗体填充面板

**cn.bugstack.guide.idea.plugin.module.ViewBars**

```java
public class ViewBars extends SimpleToolWindowPanel {

    private Project project;
    private ConsoleUI consoleUI;

    public ViewBars(Project project) {
        super(false, true);
        this.project = project;
        consoleUI = new ConsoleUI();

        // 设置窗体侧边栏按钮
        DefaultActionGroup group = new DefaultActionGroup();
        group.add(new SettingBar(this));
        group.add(new RefreshBar(this));

        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar("bar", group, false);
        toolbar.setTargetComponent(this);
        setToolbar(toolbar.getComponent());

        // 添加
        JBSplitter splitter = new JBSplitter(false);
        splitter.setSplitterProportionKey("main.splitter.key");
        splitter.setFirstComponent(consoleUI.getPanel());
        splitter.setProportion(0.3f);
        setContent(splitter);
    }

    public Project getProject() {
        return project;
    }

    public ConsoleUI getConsoleUI() {
        return consoleUI;
    }
}
```

- 在填充面板中主要是在我们自定义的插件中，在左侧添加工具栏，其余位置添加股票展示面板。
- `DefaultActionGroup` 中可以以此添加设置和刷新按钮，并最终填充到 `ActionToolbar` 里去，这样就设置完成了。
- `JBSplitter` 是一个分割线，右侧填充上我们的股票指数展示面板 `splitter.setFirstComponent(consoleUI.getPanel())`

### 4. 填充主面板到IDEA工具栏

#### 4.1 窗体工厂

**cn.bugstack.guide.idea.plugin.factory.TabFactory**

```java
public class TabFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 窗体
        ViewBars viewPanel = new ViewBars(project);
        // 获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        // 获取 ToolWindow 显示的内容
        Content content = contentFactory.createContent(viewPanel, "股票", false);
        // 设置 ToolWindow 显示的内容
        toolWindow.getContentManager().addContent(content, 0);

        // 定时任务，自动刷新股票
        /* 因每日查询次数限制，这里就不开定时任务了，用户可以自行申请 https://dashboard.juhe.cn/home
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                viewPanel.getConsoleUI().addRows(DataSetting.getInstance().getGids());
            }
        }, 3000, 2000);*/
    }

}
```

- 在 TabFactory 中主要包括两部分，一个是把 ViewBars 填充到整个 toolWindow 中，另外一个是我们目前已经注释掉的刷新股票数据的定时任务。
- 这里由于股票接口查询次数限制，所以就把定时任务注释掉了，否则一会就把可用次数跑没了。

#### 4.2 配置窗体

**plugin.xml**

```xml
<idea-plugin>
    
    <extensions defaultExtensionNs="com.intellij">
        <!-- Add your extensions here -->
        <toolWindow id="XUtil"
                    canCloseContents="true"
                    anchor="bottom"
                    factoryClass="cn.bugstack.guide.idea.plugin.factory.TabFactory"
                    icon="/icons/stock.png"
        />

    </extensions>

</idea-plugin>
```

- 这里我们把窗体配置到整个 IDEA 界面的最下方 `anchor="bottom"` *这个位置既方便又最安全*

### 5. 数据持久化配置

- 当我们使用 IDEA 进行配置一些基本参数后，例如：Maven、Gradle、Git、签名信息等，在日常的关闭和重启 IDEA 时，这些配置信息是会保存下来的，而不会说关闭就没了。
- 那么我们开发的这款插件需要做的一些自选股票代码配置，也要进行保存，否则不能每次都在 IDEA 启动时重新设置。所以这里我们需要用到 `plugin.xml` 中 `applicationService` 配置上实现了 `PersistentStateComponent` 的数据设置存放类。

#### 5.1 对象数据

**cn.bugstack.guide.idea.plugin.infrastructure.DataState**

```java
public class DataState {

    private List<String> gids = new ArrayList<>();

    public List<String> getGids() {
        return gids;
    }

    public void setGids(List<String> gids) {
        this.gids = gids;
    }

}
```

- 这个是数据对象类，你可以在这里设置你需要的属性存放，就像 gids 一样，用于存放用户配置的股票代码集合。

#### 5.2 持久数据

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

     public List<String> getGids(){
        return state.getGids();
     }

}
```

- DataSetting 类需要使用到 IDEA 插件开发的提供的注解 @State 配置持久对象
- 此外还需要提供一个 getInstance 方法来获取数据对象实例，那么在我们实际使用的时候就可以拿到我们配置的对象了并进行设置和读取数据。

#### 5.3 plugin.xml 配置

```xml
<extensions defaultExtensionNs="com.intellij">
    <!-- Add your extensions here -->
    <applicationService serviceImplementation="cn.bugstack.guide.idea.plugin.infrastructure.DataSetting"/>
</extensions>
```

- 在创建好数据设置类以后，则需要使用 applicationService 标签把你的类配置到 plugin.xml 中 extensions 里面。

### 6. 股票接口

#### 6.1 技术调研

- 无论什么功能开发，在开始之前都需要把这些`零碎的事情`处理完，才能进行代码开发，这个过程也叫做技术调研到设计和评审。就像现在我们需要进行股票信息的查询，那么就需要找到一个可以提供数据查询的接口，看看这个接口如何申请使用，以及返回的对象都有哪些字段，是否符合我们的预期。
- 这里小傅哥找到了一个聚合数据的接口，不过只能免费`100次/天`调用，如果你有更好的可以更换下。
  
  ![](https://bugstack.cn/images/article/assembly/assembly-211118-3-02.png)

- 接口：[http://web.juhe.cn:8080/finance/stock/hs?gid=sz000651&key=自己申请](http://web.juhe.cn:8080/finance/stock/hs?gid=sz000651&key=自己申请) - 这里的 key 需要自己申请
- 数据：

  ![](https://bugstack.cn/images/article/assembly/assembly-211118-3-03.png)	

#### 6.2 服务封装

有了股票的查询接口，接下来就可以对数据做一个查询和对象转换了。

**cn.bugstack.guide.idea.plugin.domain.service.impl.StockImpl**

```java
public class StockImpl implements IStock {

    // 自行申请，股票API，替换key即可【一天可免费调用100次】：https://dashboard.juhe.cn/home/
    private final String key = "4bc57728***********f0595";

    @Override
    public List<Data> queryPresetStockData(List<String> gids) {
        List<Data> dataList = new ArrayList<>();
        for (String gid : gids) {
            StockResult stockResult = JSON.parseObject(HttpUtil.get("http://web.juhe.cn:8080/finance/stock/hs?gid=" + gid + "&key=" + key), StockResult.class);
            Stock[] stocks = stockResult.getResult();
            for (Stock stock : stocks) {
                dataList.add(stock.getData());
            }
        }
        return dataList;
    }

}
```

- 这里我们在 domain 领域层中定义数据 vo 对象，以及提供股票查询服务的封装。这样调用方就可以直接使用这份数据了，如果你是其他厂商提供的股票查询接口，也可以进行封装和更换，做一个接口适配层。

## 四、测试验证

![](https://bugstack.cn/images/article/assembly/assembly-211118-3-06.png)

- 如果你下载工程后没有 Plugin 和一个绿色箭头，那么可以按照图自己配置 `:runIde` 这样就可以运行了。

**运行效果** - 激动人心的时刻到了，再也不用担心写代码影响看股票了哦

### 1. 配置股票

![](https://bugstack.cn/images/article/assembly/assembly-211118-3-07.png)

- 首先你需要在这里配置你关注的股票代码，我配置了3个我看好的。

### 2. 自选股指数

![](https://bugstack.cn/images/article/assembly/assembly-211118-3-08.png)

- 配置好以后你就可以看到自己的自选股指数了，选中一条以后，在点击K线。
- 当你需要看最新数据的时候，可以点左侧的刷新按钮。

### 3. K线

![](https://bugstack.cn/images/article/assembly/assembly-211118-3-09.png)

- 现在这个就是对应的 K线，是不是还挺香的。目前是加了最近K线和日K线，你还可以扩展其他维度的图。

## 五、总结

- 本章节我们使用到了在 IDEA 窗体中添加稍微复杂一些的页面结构，有侧边栏、有Tab页，并在需要在这些页面体中进行交互和通信。此外还是用到了数据的存储设置，这个在很多时候开发IDEA插件里都会用到。
- 像是这样的技术实践不只是可以用于展示股票数据，你还可以结合自己所需扩展属于你实际场景中需要的内容，比如开发一个数据集中查询插件，可以查询数据库、ES、Redis等，也可以是所有的工具类集合页，这些内容会更有技术价值。
- 当你自己开始主动的向你学习到的一些源码、框架、组件、项目等中添加自己想要的功能时，就是你真的开始学习了，否则一个内容看过没多久也就忘记了。