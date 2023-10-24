---
title: IntelliJ IDEA
lock: need
---

# IntelliJ IDEA 开发工具使用

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

工欲善其事必先利其器，Java 技术再牛皮，也不能用 TXT 写代码，那效率太低。而开发 Java 的工具也不少，比如；Eclipse、MyEclipse、NetBeans、JDeveloper、BlueJ、JCreator 但在众多工具中，目前高校和企业较为常用的主要是 [Intellij IDEA](https://www.jetbrains.com.cn/idea/) 系列的开发工具。

所以小傅哥本文会优先介绍 Intellij IDEA 这款 Java 开发工具，方便伙伴更好的使用开发工具编写项目。

## 一、工具安装

### 1. 下载

**下载**：[https://www.jetbrains.com.cn/idea/download/?section=mac](https://www.jetbrains.com.cn/idea/download/?section=mac) - `Windows`/`macOS(Intel/Apple Silicon)`/`Linux`

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230701-01.png?raw=true" width="850px"/>
</div>

- IntelliJ IDEA Ultimate 旗舰版 - 付费的，功能更多。如果需要使用可以购买或者XX。
- IntelliJ IDEA Community 社区版 - 免费的，对于大部人来说，免费的社区版就够用了。

### 2. 安装

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230701-02.png?raw=true" width="550px"/>
</div>

1. 无论是 Windows 还是 Mac 都是双击下载的应用程序进行安装。
2. Mac 电脑需要在弹出框后，把应用程序拖入到 Applications 中，这样就可以自动执行安装了。非常傻白甜的操作。

### 3. 主题

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230701-03.png?raw=true" width="550px"/>
</div>

安装完成后，打开工具会提示你有一个新的UI主题，这里你可以选择 Enable New UI 点击后软件会重新启动。新的 UI 非常简洁。

### 4. 效果

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230701-04.png?raw=true" width="950px"/>
</div>

这就是 IntelliJ IDEA 打开项目后的效果，分为左中右布局；四边栏为工具栏操作。
- 左；展示项目
- 中；编写代码，可以通过拖拉的方式，同时打开多个文件。这样在写代码的时候可以做个对比参考。
- 右；Maven/Gradle 数据库等操作区域，你可以托拉拽一些其他窗口到右侧。

这些功能随着你的点击使用也就逐步了解了。

## 二、常用插件

IntelliJ IDEA 除了自身的功能强大以外，还可以安装各类提效的插件。这些插件可以辅助的帮助你完成代码开发。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230701-05.png?raw=true" width="950px"/>
</div>

- Jump to Line：调试代码，一步定位📌。
- Maven Helper：Maven POM 依赖，查看、分析和排除相互冲突的依赖项。简单高效。
- Statistis：代码统计插件，可以看到整个工程中你写了多少代码量。
- Sequence Diagram：用于查看你的 工程中，代码执行流程的 UML 图，可以非常方便的熟悉一些默认代码。
- FastRequest：类似于 Postman 的 IDEA 插件。它是一个强大的 restful api 工具包插件
- Coverage：单元测试覆盖率统计插件
- Squaretest：自动生成Mock测试插件
- [CodeGeeX 智能AI编程助手](https://codegeex.cn/zh-CN/downloadGuide#vscode)

此外，你也可以自己开发一些自己的 IDEA 插件，这里小傅哥写了教程[《IDEA Plugin 插件开发》](https://bugstack.cn/md/assembly/idea-plugin/2021-08-27-%E6%8A%80%E6%9C%AF%E8%B0%83%E7%A0%94%EF%BC%8CIDEA%20%E6%8F%92%E4%BB%B6%E6%80%8E%E4%B9%88%E5%BC%80%E5%8F%91%EF%BC%9F.html) vo2dto 这款用于对象转换的插件，就是小傅哥开发的。如果你有非常好用的插件，也可以在本文下修改提交。

## 三、快捷命令

- Shift 双击，搜索
- Command/Ctrl + Shift + F 文件搜搜
- Option + Command + L 格式化代码 —— 你可以格式化；整个工程、默认当前打开的文件、选中的代码区域
- Control + Option + O 排掉当前类中，不需要的引入包
- Shift + Option + 上/下 可以把当前行的代码，上移或者下移

**更多指令** Mac\Windows 有一些差别，但基本一样。

### Ctrl

| 快捷键                 | 介绍                                                         |
| :--------------------- | :----------------------------------------------------------- |
| Ctrl + F               | 在当前文件进行文本查找 `（必备）`                            |
| Ctrl + R         | 在当前文件进行文本替换 `（必备）`                            |
| Ctrl + Z         | 撤销 `（必备）`                                              |
| Ctrl + Y         | 删除光标所在行 或 删除选中的行 `（必备）`                    |
| Ctrl + X         | 剪切光标所在行 或 剪切选择内容                               |
| Ctrl + C         | 复制光标所在行 或 复制选择内容                               |
| Ctrl + D         | 复制光标所在行 或 复制选择内容，并把复制内容插入光标位置下面 `（必备）` |
| Ctrl + W         | 递进式选择代码块。可选中光标所在的单词或段落，连续按会在原有选中的基础上再扩展选中范围 `（必备）` |
| Ctrl + E         | 显示最近打开的文件记录列表 `（必备）`                        |
| Ctrl + N         | 根据输入的 **类名** 查找类文件 `（必备）`                    |
| Ctrl + G         | 在当前文件跳转到指定行处                                     |
| Ctrl + J         | 插入自定义动态代码模板 `（必备）`                            |
| Ctrl + P         | 方法参数提示显示 `（必备）`                                  |
| Ctrl + Q         | 光标所在的变量 / 类名 / 方法名等上面（也可以在提示补充的时候按），显示文档内容 |
| Ctrl + U         | 前往当前光标所在的方法的父类的方法 / 接口定义 `（必备）`     |
| Ctrl + B         | 进入光标所在的方法/变量的接口或是定义处，等效于 `Ctrl + 左键单击` `（必备）` |
| Ctrl + K         | 版本控制提交项目，需要此项目有加入到版本控制才可用           |
| Ctrl + T         | 版本控制更新项目，需要此项目有加入到版本控制才可用           |
| Ctrl + H         | 显示当前类的层次结构                                         |
| Ctrl + O         | 选择可重写的方法                                             |
| Ctrl + I         | 选择可继承的方法                                             |
| Ctrl + +         | 展开代码                                                     |
| Ctrl + -         | 折叠代码                                                     |
| Ctrl + /         | 注释光标所在行代码，会根据当前不同文件类型使用不同的注释符号 `（必备）` |
| Ctrl + [         | 移动光标到当前所在代码的花括号开始位置                       |
| Ctrl + ]         | 移动光标到当前所在代码的花括号结束位置                       |
| Ctrl + F1        | 在光标所在的错误代码处显示错误信息 `（必备）`                |
| Ctrl + F3        | 调转到所选中的词的下一个引用位置 `（必备）`                  |
| Ctrl + F4        | 关闭当前编辑文件                                             |
| Ctrl + F8        | 在 Debug 模式下，设置光标当前行为断点，如果当前已经是断点则去掉断点 |
| Ctrl + F9        | 执行 Make Project 操作                                       |
| Ctrl + F11       | 选中文件 / 文件夹，使用助记符设定 / 取消书签 `（必备）`      |
| Ctrl + F12       | 弹出当前文件结构层，可以在弹出的层上直接输入，进行筛选       |
| Ctrl + Tab       | 编辑窗口切换，如果在切换的过程又加按上delete，则是关闭对应选中的窗口 |
| Ctrl + End       | 跳到文件尾                                                   |
| Ctrl + Home      | 跳到文件头                                                   |
| Ctrl + Space     | 基础代码补全，默认在 Windows 系统上被输入法占用，需要进行修改，建议修改为 `Ctrl + 逗号` `（必备）` |
| Ctrl + Delete    | 删除光标后面的单词或是中文句 `（必备）`                      |
| Ctrl + BackSpace | 删除光标前面的单词或是中文句 `（必备）`                      |
| Ctrl + 1,2,3...9 | 定位到对应数值的书签位置 `（必备）`                          |
| Ctrl + 左键单击  | 在打开的文件标题上，弹出该文件路径 `（必备）`                |
| Ctrl + 光标定位  | 按 Ctrl 不要松开，会显示光标所在的类信息摘要                 |
| Ctrl + 左方向键  | 光标跳转到当前单词 / 中文句的左侧开头位置 `（必备）`         |
| Ctrl + 右方向键  | 光标跳转到当前单词 / 中文句的右侧开头位置 `（必备）`         |
| Ctrl + 前方向键  | 等效于鼠标滚轮向前效果 `（必备）`                            |
| Ctrl + 后方向键  | 等效于鼠标滚轮向后效果 `（必备）`                            |

### Alt

| 快捷键          | 介绍                                                         |
| :-------------- | :----------------------------------------------------------- |
| Alt + `         | 显示版本控制常用操作菜单弹出层 `（必备）`                    |
| Alt + Q         | 弹出一个提示，显示当前类的声明 / 上下文信息                  |
| Alt + F1        | 显示当前文件选择目标弹出层，弹出层中有很多目标可以进行选择 `（必备）` |
| Alt + F2        | 对于前面页面，显示各类浏览器打开目标选择弹出层               |
| Alt + F3        | 选中文本，逐个往下查找相同文本，并高亮显示                   |
| Alt + F7        | 查找光标所在的方法 / 变量 / 类被调用的地方                   |
| Alt + F8        | 在 Debug 的状态下，选中对象，弹出可输入计算表达式调试框，查看该输入内容的调试结果 |
| Alt + Home      | 定位 / 显示到当前文件的 `Navigation Bar`                     |
| Alt + Enter     | IntelliJ IDEA 根据光标所在问题，提供快速修复选择，光标放在的位置不同提示的结果也不同 `（必备）` |
| Alt + Insert    | 代码自动生成，如生成对象的 set / get 方法，构造函数，toString() 等 `（必备）` |
| Alt + 左方向键  | 切换当前已打开的窗口中的子视图，比如Debug窗口中有Output、Debugger等子视图，用此快捷键就可以在子视图中切换 `（必备）` |
| Alt + 右方向键  | 按切换当前已打开的窗口中的子视图，比如Debug窗口中有Output、Debugger等子视图，用此快捷键就可以在子视图中切换 `（必备）` |
| Alt + 前方向键  | 当前光标跳转到当前文件的前一个方法名位置 `（必备）`          |
| Alt + 后方向键  | 当前光标跳转到当前文件的后一个方法名位置 `（必备）`          |
| Alt + 1,2,3...9 | 显示对应数值的选项卡，其中 1 是 Project 用得最多 `（必备）`  |

### Shift

| 快捷键               | 介绍                                                         |
| :------------------- | :----------------------------------------------------------- |
| Shift + F1           | 如果有外部文档可以连接外部文档                               |
| Shift + F2           | 跳转到上一个高亮错误 或 警告位置                             |
| Shift + F3           | 在查找模式下，查找匹配上一个                                 |
| Shift + F4           | 对当前打开的文件，使用新Windows窗口打开，旧窗口保留          |
| Shift + F6           | 对文件 / 文件夹 重命名                                       |
| Shift + F7           | 在 Debug 模式下，智能步入。断点所在行上有多个方法调用，会弹出进入哪个方法 |
| Shift + F8           | 在 Debug 模式下，跳出，表现出来的效果跟 `F9` 一样            |
| Shift + F9           | 等效于点击工具栏的 `Debug` 按钮                              |
| Shift + F10          | 等效于点击工具栏的 `Run` 按钮                                |
| Shift + F11          | 弹出书签显示层 `（必备）`                                    |
| Shift + Tab          | 取消缩进 `（必备）`                                          |
| Shift + ESC          | 隐藏当前 或 最后一个激活的工具窗口                           |
| Shift + End          | 选中光标到当前行尾位置                                       |
| Shift + Home         | 选中光标到当前行头位置                                       |
| Shift + Enter        | 开始新一行。光标所在行下空出一行，光标定位到新行位置 `（必备）` |
| Shift + 左键单击     | 在打开的文件名上按此快捷键，可以关闭当前打开文件 `（必备）`  |
| Shift + 滚轮前后滚动 | 当前文件的横向滚动轴滚动 `（必备）`                          |

### Ctrl + Alt

| 快捷键                | 介绍                                                         |
| :-------------------- | :----------------------------------------------------------- |
| Ctrl + Alt + L        | 格式化代码，可以对当前文件和整个包目录使用 `（必备）`        |
| Ctrl + Alt + O        | 优化导入的类，可以对当前文件和整个包目录使用 `（必备）`      |
| Ctrl + Alt + I        | 光标所在行 或 选中部分进行自动代码缩进，有点类似格式化       |
| Ctrl + Alt + T        | 对选中的代码弹出环绕选项弹出层 `（必备）`                    |
| Ctrl + Alt + J        | 弹出模板选择窗口，将选定的代码加入动态模板中                 |
| Ctrl + Alt + H        | 调用层次                                                     |
| Ctrl + Alt + B        | 在某个调用的方法名上使用会跳到具体的实现处，可以跳过接口     |
| Ctrl + Alt + V        | 快速引进变量                                                 |
| Ctrl + Alt + Y        | 同步、刷新                                                   |
| Ctrl + Alt + S        | 打开 IntelliJ IDEA 系统设置 `（必备）`                       |
| Ctrl + Alt + F7       | 显示使用的地方。寻找被该类或是变量被调用的地方，用弹出框的方式找出来 |
| Ctrl + Alt + F11      | 切换全屏模式                                                 |
| Ctrl + Alt + Enter    | 光标所在行上空出一行，光标定位到新行 `（必备）`              |
| Ctrl + Alt + Home     | 弹出跟当前文件有关联的文件弹出层                             |
| Ctrl + Alt + Space    | 类名自动完成                                                 |
| Ctrl + Alt + 左方向键 | 退回到上一个操作的地方 `（必备）`                            |
| Ctrl + Alt + 右方向键 | 前进到上一个操作的地方 `（必备）`                            |
| Ctrl + Alt + 前方向键 | 在查找模式下，跳到上个查找的文件                             |
| Ctrl + Alt + 后方向键 | 在查找模式下，跳到下个查找的文件                             |

### Ctrl + Shift

| 快捷键                   | 介绍                                                         |
| :----------------------- | :----------------------------------------------------------- |
| Ctrl + Shift + F         | 根据输入内容查找整个项目 或 指定目录内文件 `（必备）`        |
| Ctrl + Shift + R         | 根据输入内容替换对应内容，范围为整个项目 或 指定目录内文件 `（必备）` |
| Ctrl + Shift + J         | 自动将下一行合并到当前行末尾 `（必备）`                      |
| Ctrl + Shift + Z         | 取消撤销 `（必备）`                                          |
| Ctrl + Shift + W         | 递进式取消选择代码块。可选中光标所在的单词或段落，连续按会在原有选中的基础上再扩展取消选中范围 `（必备）` |
| Ctrl + Shift + N         | 通过文件名定位 / 打开文件 / 目录，打开目录需要在输入的内容后面多加一个正斜杠 `（必备）` |
| Ctrl + Shift + U         | 对选中的代码进行大 / 小写轮流转换 `（必备）`                 |
| Ctrl + Shift + T         | 对当前类生成单元测试类，如果已经存在的单元测试类则可以进行选择 `（必备）` |
| Ctrl + Shift + C         | 复制当前文件磁盘路径到剪贴板 `（必备）`                      |
| Ctrl + Shift + V         | 弹出缓存的最近拷贝的内容管理器弹出层                         |
| Ctrl + Shift + E         | 显示最近修改的文件列表的弹出层                               |
| Ctrl + Shift + H         | 显示方法层次结构                                             |
| Ctrl + Shift + B         | 跳转到类型声明处 `（必备）`                                  |
| Ctrl + Shift + I         | 快速查看光标所在的方法 或 类的定义                           |
| Ctrl + Shift + A         | 查找动作 / 设置                                              |
| Ctrl + Shift + /         | 代码块注释 `（必备）`                                        |
| Ctrl + Shift + [         | 选中从光标所在位置到它的顶部中括号位置 `（必备）`            |
| Ctrl + Shift + ]         | 选中从光标所在位置到它的底部中括号位置 `（必备）`            |
| Ctrl + Shift + +         | 展开所有代码 `（必备）`                                      |
| Ctrl + Shift + -         | 折叠所有代码 `（必备）`                                      |
| Ctrl + Shift + F7        | 高亮显示所有该选中文本，按Esc高亮消失 `（必备）`             |
| Ctrl + Shift + F8        | 在 Debug 模式下，指定断点进入条件                            |
| Ctrl + Shift + F9        | 编译选中的文件 / 包 / Module                                 |
| Ctrl + Shift + F12       | 编辑器最大化 `（必备）`                                      |
| Ctrl + Shift + Space     | 智能代码提示                                                 |
| Ctrl + Shift + Enter     | 自动结束代码，行末自动添加分号 `（必备）`                    |
| Ctrl + Shift + Backspace | 退回到上次修改的地方 `（必备）`                              |
| Ctrl + Shift + 1,2,3...9 | 快速添加指定数值的书签 `（必备）`                            |
| Ctrl + Shift + 左键单击  | 把光标放在某个类变量上，按此快捷键可以直接定位到该类中 `（必备）` |
| Ctrl + Shift + 左方向键  | 在代码文件上，光标跳转到当前单词 / 中文句的左侧开头位置，同时选中该单词 / 中文句 `（必备）` |
| Ctrl + Shift + 右方向键  | 在代码文件上，光标跳转到当前单词 / 中文句的右侧开头位置，同时选中该单词 / 中文句 `（必备）` |
| Ctrl + Shift + 前方向键  | 光标放在方法名上，将方法移动到上一个方法前面，调整方法排序 `（必备）` |
| Ctrl + Shift + 后方向键  | 光标放在方法名上，将方法移动到下一个方法前面，调整方法排序 `（必备）` |

### Alt + Shift

| 快捷键                 | 介绍                                                         |
| :--------------------- | :----------------------------------------------------------- |
| Alt + Shift + N        | 选择 / 添加 task `（必备）`                                  |
| Alt + Shift + F        | 显示添加到收藏夹弹出层 / 添加到收藏夹                        |
| Alt + Shift + C        | 查看最近操作项目的变化情况列表                               |
| Alt + Shift + I        | 查看项目当前文件                                             |
| Alt + Shift + F7       | 在 Debug 模式下，下一步，进入当前方法体内，如果方法体还有方法，则会进入该内嵌的方法中，依此循环进入 |
| Alt + Shift + F9       | 弹出 `Debug` 的可选择菜单                                    |
| Alt + Shift + F10      | 弹出 `Run` 的可选择菜单                                      |
| Alt + Shift + 左键双击 | 选择被双击的单词 / 中文句，按住不放，可以同时选择其他单词 / 中文句 `（必备）` |
| Alt + Shift + 前方向键 | 移动光标所在行向上移动 `（必备）`                            |
| Alt + Shift + 后方向键 | 移动光标所在行向下移动 `（必备）`                            |

### Ctrl + Shift + Alt

| 快捷键                 | 介绍                        |
| :--------------------- | :-------------------------- |
| Ctrl + Shift + Alt + V | 无格式黏贴 `（必备）`       |
| Ctrl + Shift + Alt + N | 前往指定的变量 / 方法       |
| Ctrl + Shift + Alt + S | 打开当前项目设置 `（必备）` |
| Ctrl + Shift + Alt + C | 复制参考信息                |

### 其他

| 快捷键        | 介绍                                                         |
| :------------ | :----------------------------------------------------------- |
| F2            | 跳转到下一个高亮错误 或 警告位置 `（必备）`                  |
| F3            | 在查找模式下，定位到下一个匹配处                             |
| F4            | 编辑源 `（必备）`                                            |
| F7            | 在 Debug 模式下，进入下一步，如果当前行断点是一个方法，则进入当前方法体内，如果该方法体还有方法，则不会进入该内嵌的方法中 |
| F8            | 在 Debug 模式下，进入下一步，如果当前行断点是一个方法，则不进入当前方法体内 |
| F9            | 在 Debug 模式下，恢复程序运行，但是如果该断点下面代码还有断点则停在下一个断点上 |
| F11           | 添加书签 `（必备）`                                          |
| F12           | 回到前一个工具窗口 `（必备）`                                |
| Tab           | 缩进 `（必备）`                                              |
| ESC           | 从工具窗口进入代码文件窗口 `（必备）`                        |
| 连按两次Shift | 弹出 `Search Everywhere` 弹出层                              |

官网：[https://blog.jetbrains.com/zh-hans/idea/2022/11/intellij-idea-3/](https://blog.jetbrains.com/zh-hans/idea/2022/11/intellij-idea-3/)
其他：[https://yuzhigang.gitbooks.io/intellij-idea-tutorial/content/introduce.html](https://yuzhigang.gitbooks.io/intellij-idea-tutorial/content/introduce.html)

## 四、更多官方学习信息

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230701-06.png?raw=true" width="750px"/>
</div>

- IntelliJ IDEA 主要特性介绍 1：[https://www.jetbrains.com/idea/features/](https://www.jetbrains.com/idea/features/)
- IntelliJ IDEA 主要特性介绍 2：[https://www.jetbrains.com/idea/features/editions_comparison_matrix.html](https://www.jetbrains.com/idea/features/editions_comparison_matrix.html)
- 官方快速入门：[http://confluence.jetbrains.com/display/IntelliJIDEA/Quick+Start](http://confluence.jetbrains.com/display/IntelliJIDEA/Quick+Start)
- 官方在线帮助文档：[http://www.jetbrains.com/idea/webhelp/getting-help.html](http://www.jetbrains.com/idea/webhelp/getting-help.html)
- 官方 wiki：[http://wiki.jetbrains.net/intellij](http://wiki.jetbrains.net/intellij)
