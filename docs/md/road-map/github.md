---
title: Github
lock: need
---

# Github 使用

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

没用过 Github 不能算是一个正经码农！

Github 本身是的核心职责是一个在线软件源代码托管服务平台，也就是说你的本地开发的代码可以提交到 Github 让多人参与维护和迭代。但除了这个核心的功能外，还提供了不少的社交功能，如；点赞、关注、评论、复刻等，而且 Github 男性用户居多，为此也让Github 被称呼为 Gayhub 社群。

在使用Github时，你需要注册一个账号，之后创建一个用于存放代码的仓库，之后就可以把你的代码提交到仓库了。而其他参与此项目的伙伴，也可以通过你的仓库地址检出项目。当然你也可以检出其他人的项目。

- 仓库配置 SSH 参考：[https://bugstack.cn/md/road-map/git.html](https://bugstack.cn/md/road-map/git.html)

## 一、账号注册

- 官网：[https://github.com](https://github.com/)
- 注册：[https://github.com/signup?source=login](https://github.com/signup?source=login)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-230706-01.png?raw=true" width="750px">
</div>

- 你可以按照Github的注册提示，一步一步的填写信息就可以完成账号注册了。
- 注册后你就会有一个自己的 Github，如：[https://github.com/fuzhengwei](https://github.com/fuzhengwei)

## 二、仓库创建

### 1. 创建类型

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-230706-02.png?raw=true" width="550px">
</div>

- New repository 创建仓库
- Import repository 导入仓库
- New organization 创建组织，一个组织下可以创建多个项目。

### 2. 创建操作

#### 2.1 创建项目

**创建选项**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-230706-03.png?raw=true" width="550px">
</div>

- 项目名称：不重复即可，一般是小写的项目名称和中划线。
- 项目介绍：介绍写核心信息。
- 项目类型：公开的和私有的，私有的别人不能被搜索到，也看不到。
- 详细介绍：README 是用于写详细介绍的，你可以把它当成是项目本身的简历。
- .gitignore：是一个模板，哪里不被 Java 项目提交到仓库。
- License：协议，比如 Apache License 协议。

**创建完成**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-230706-04.png?raw=true" width="550px">
</div>

- 示例：[https://github.com/fuzhengwei/xfg-frame-ddd](https://github.com/fuzhengwei/xfg-frame-ddd)

#### 2.2 创建组织

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-230706-05.png?raw=true" width="550px">
</div>

- 组织相当于是一个额外的空间，而你是这个组织的管理者。它必须全局唯一。
- 之后你可以在创建项目的时候，选择到组织空间，这样项目就会创建到组织里了。

## 三、代码提交

代码提交到仓库，核心在于把本地代码和远程仓库关联起来。如果你是直接从远程仓库地址，检出到本地的，那么就不需要再次关联了。本场景适用于，你本地有代码库，需要首次提交到远程仓库中。

### 1. 创建本地仓库

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-230706-06.png?raw=true" width="750px">
</div>

- 首先你需要打开 IntelliJ IDEA 工程，选择 VCS 创建一个本地仓库的Git仓库。Create Git Repository
- 这里还有一个分享到仓库，Share Project On GitHub 你也可以选择使用，直接把本地代码分享创建到远程仓库。

### 2. 关联远程仓库

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-230706-07.png?raw=true" width="750px">
</div>

- 打开你的Github 仓库对应的项目，复制项目 HTTPS 地址，粘贴到 IntelliJ IDEA 项目的远程仓库配置中，点击 OK 即可。

### 3. 项目代码提交

#### 3.1 添加

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-230706-08.png?raw=true" width="750px">
</div>

- 在工程上右键，把你需要提交的文件，选择 +Add 注意不要把一些不需要提交的文件，也提交上去。
- 你可以先创建一个 `Add to .gitignore` 它可以屏蔽掉一些不要的文件。—— 在 Github 创建工程的时候也有此文件。你也可以先更新 pull 拉取下来。

#### 3.2 提交

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-230706-09.png?raw=true" width="750px">
</div>

- 提交代码快捷键：`Ctrl + K` - 提交到本地仓库

#### 3.3 推送

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-230706-10.png?raw=true" width="750px">
</div>

- 推送代码快捷辑：`Ctrl + Shift + K` —— 提交到远程仓库

## 四、提交规范

**分支命名**：`日期_姓名首字母缩写_功能单词`，如：`230708_xfg_buildFramework`

**提交规范**：`作者，type: desc` 如：`小傅哥，fix：修复查询用户信息逻辑问题` *参考Commit message 规范*

```
# 主要type
feat:     增加新功能
fix:      修复bug

# 特殊type
docs:     只改动了文档相关的内容
style:    不影响代码含义的改动，例如去掉空格、改变缩进、增删分号
build:    构造工具的或者外部依赖的改动，例如webpack，npm
refactor: 代码重构时使用
revert:   执行git revert打印的message

# 暂不使用type
test:     添加测试或者修改现有测试
perf:     提高性能的改动
ci:       与CI（持续集成服务）有关的改动
chore:    不修改src或者test的其余修改，例如构建过程或辅助工具的变动
```

---

**此外**，Github 是一个大宝藏，你可以通过检索，找到特别多的优质项目、代码、设计。如：[https://github.com/fuzhengwei](https://github.com/fuzhengwei)
