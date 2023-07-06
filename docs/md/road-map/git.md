---
title: Git
lock: no
---

# Git 使用说明和配置

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

# Git 使用教程

讲到 Git 就不得不提一下 Linux，因为如果没有 Linux 也就没有 Git 的诞生，这里是有一段 **10天** 写出 Git 的故事！

众所周知，Linus 于 1991年 创建了开源的 Linux，从此 Linux 系统不断发展壮大。但 Linux 的壮大是靠全世界热心的开发者参与的，不过这么多人在世界各地为 Linux 编写代码，那代码是如何管理的呢？事实是，2002 之前，合并代码的操作都是 Linux 老爷子自己手动合并的！

但 2000 年的时候，不是已经 [SVN](https://zh.wikipedia.org/wiki/Subversion) 可以使用了吗？但 Linus 坚决反对 CVS、SVN 这些集中式的管理工具，不仅速度差还得联网操作。虽然有商用版的功能好一些，但这与 Linux 开源精神不符。所以 Linus 就不用，看人家这开源精神！

不过到了 2002年，Linux 都发展10年了，在这么手动的合并代码，让社区的兄弟也苦不堪言，强烈反对。为此，林纳斯·托瓦兹（Linus Torvalds）决定使用 BitKeeper 作为 Linux 内核主要的版本控制系统用以维护代码。BitKeeper 的东家 BitMover 公司，看 Linux 开源的不容易，授权 Linux 社区免费使用这个版本控制系统。

哈哈哈，但好景不长！2005年，安德鲁·垂鸠（Andrew Tridgell）写了一个简单程序，可以连接 BitKeeper 的仓库，BitKeeper 著作权拥有者拉里·麦沃伊认为安德鲁·垂鸠对 BitKeeper 内部使用的协议进行逆向工程，决定收回无偿使用 BitKeeper 的许可。Linux 内核开发团队与 BitMover 公司进行磋商，但无法解决他们之间的歧见。林纳斯·托瓦兹决定自行开发版本控制系统替代 BitKeeper ，以10天的时间编写出 git 第一个版本！—— 牛皮！

有了 Git 以后，GitHub 平台也于2007年10月1日开始开发。网站于2008年2月以beta版本开始上线，4月份正式上线。GitHub 里面的项目可以通过标准的 Git 命令进行访问和操作。—— 这就是 Linux、Git、Github 的故事。

## 一、软件安装

地址：[https://git-scm.com/downloads](https://git-scm.com/downloads) - `选择需要的版本下载`

### 1. Mac

```java
# 如果你没有 brew 命令，则需要先安装下；https://brew.sh/index_zh-cn
brew install git
```

### 2. Windows

- [32-bit Git for Windows Setup](https://github.com/git-for-windows/git/releases/download/v2.41.0.windows.1/Git-2.41.0-32-bit.exe)
- [64-bit Git for Windows Setup](https://github.com/git-for-windows/git/releases/download/v2.41.0.windows.1/Git-2.41.0-64-bit.exe)

### 3. Linux

### Debian/Ubuntu

获取适用于您的 Debian/Ubuntu 版本的最新稳定版本

```
# apt-get install git
```

对于 Ubuntu，此 PPA 提供最新的稳定上游 Git 版本

```
# add-apt-repository ppa:git-core/ppa
# apt update; apt install git
```

### Red Hat Enterprise Linux, Oracle Linux, CentOS, Scientific Linux, et al.

RHEL 及其衍生版本通常提供旧版本的 git。您可以[下载 tarball](https://www.kernel.org/pub/software/scm/git/)并从源代码构建，或者使用第 3 方存储库（例如[IUS 社区项目）](https://ius.io/)来获取更新版本的 git。


## 二、配置账户

```java
# 安装完成后，配置账户，在命令行输入：
$ git config --global user.name "Your Name"
$ git config --global user.email "email@example.com"
```

## 三、操作界面

Git 附带了用于提交 ( [git-gui](https://git-scm.com/docs/git-gui) ) 和浏览 ( [gitk](https://git-scm.com/docs/gitk) ) 的内置 GUI 工具，但也有一些第三方工具可供用户寻求特定于平台的体验。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-230705-01.png?raw=true" width="750px">
</div>

- Git 提供了操作的客户端界面，你可以按需下载使用。

## 四、操作手册

- 文档：[https://git-scm.com/book/zh/v2](https://git-scm.com/book/zh/v2) - `可以直接对照着操作，练习命令`
- PDF：[https://github.com/progit/progit2-zh/releases/download/2.1.62/progit.pdf](https://github.com/progit/progit2-zh/releases/download/2.1.62/progit.pdf)

### 1. [起步](https://git-scm.com/book/zh/v2/起步-关于版本控制)

- 1.1 [关于版本控制](https://git-scm.com/book/zh/v2/起步-关于版本控制)
- 1.2 [Git 简史](https://git-scm.com/book/zh/v2/起步-Git-简史)
- 1.3 [Git 是什么？](https://git-scm.com/book/zh/v2/起步-Git-是什么？)
- 1.4 [命令行](https://git-scm.com/book/zh/v2/起步-命令行)
- 1.5 [安装 Git](https://git-scm.com/book/zh/v2/起步-安装-Git)
- 1.6 [初次运行 Git 前的配置](https://git-scm.com/book/zh/v2/起步-初次运行-Git-前的配置)
- 1.7 [获取帮助](https://git-scm.com/book/zh/v2/起步-获取帮助)
- 1.8 [总结](https://git-scm.com/book/zh/v2/起步-总结)

### 2. [Git 基础](https://git-scm.com/book/zh/v2/Git-基础-获取-Git-仓库)

- 2.1 [获取 Git 仓库](https://git-scm.com/book/zh/v2/Git-基础-获取-Git-仓库)
- 2.2 [记录每次更新到仓库](https://git-scm.com/book/zh/v2/Git-基础-记录每次更新到仓库)
- 2.3 [查看提交历史](https://git-scm.com/book/zh/v2/Git-基础-查看提交历史)
- 2.4 [撤消操作](https://git-scm.com/book/zh/v2/Git-基础-撤消操作)
- 2.5 [远程仓库的使用](https://git-scm.com/book/zh/v2/Git-基础-远程仓库的使用)
- 2.6 [打标签](https://git-scm.com/book/zh/v2/Git-基础-打标签)
- 2.7 [Git 别名](https://git-scm.com/book/zh/v2/Git-基础-Git-别名)
- 2.8 [总结](https://git-scm.com/book/zh/v2/Git-基础-总结)

### 3. [Git 分支](https://git-scm.com/book/zh/v2/Git-分支-分支简介)

- 3.1 [分支简介](https://git-scm.com/book/zh/v2/Git-分支-分支简介)
- 3.2 [分支的新建与合并](https://git-scm.com/book/zh/v2/Git-分支-分支的新建与合并)
- 3.3 [分支管理](https://git-scm.com/book/zh/v2/Git-分支-分支管理)
- 3.4 [分支开发工作流](https://git-scm.com/book/zh/v2/Git-分支-分支开发工作流)
- 3.5 [远程分支](https://git-scm.com/book/zh/v2/Git-分支-远程分支)
- 3.6 [变基](https://git-scm.com/book/zh/v2/Git-分支-变基)
- 3.7 [总结](https://git-scm.com/book/zh/v2/Git-分支-总结)

### 4. [服务器上的 Git](https://git-scm.com/book/zh/v2/服务器上的-Git-协议)

- 4.1 [协议](https://git-scm.com/book/zh/v2/服务器上的-Git-协议)
- 4.2 [在服务器上搭建 Git](https://git-scm.com/book/zh/v2/服务器上的-Git-在服务器上搭建-Git)
- 4.3 [生成 SSH 公钥](https://git-scm.com/book/zh/v2/服务器上的-Git-生成-SSH-公钥)
- 4.4 [配置服务器](https://git-scm.com/book/zh/v2/服务器上的-Git-配置服务器)
- 4.5 [Git 守护进程](https://git-scm.com/book/zh/v2/服务器上的-Git-Git-守护进程)
- 4.6 [Smart HTTP](https://git-scm.com/book/zh/v2/服务器上的-Git-Smart-HTTP)
- 4.7 [GitWeb](https://git-scm.com/book/zh/v2/服务器上的-Git-GitWeb)
- 4.8 [GitLab](https://git-scm.com/book/zh/v2/服务器上的-Git-GitLab)
- 4.9 [第三方托管的选择](https://git-scm.com/book/zh/v2/服务器上的-Git-第三方托管的选择)
- 4.10 [总结](https://git-scm.com/book/zh/v2/服务器上的-Git-总结)

### 5. [分布式 Git](https://git-scm.com/book/zh/v2/分布式-Git-分布式工作流程)

- 5.1 [分布式工作流程](https://git-scm.com/book/zh/v2/分布式-Git-分布式工作流程)
- 5.2 [向一个项目贡献](https://git-scm.com/book/zh/v2/分布式-Git-向一个项目贡献)
- 5.3 [维护项目](https://git-scm.com/book/zh/v2/分布式-Git-维护项目)
- 5.4 [总结](https://git-scm.com/book/zh/v2/分布式-Git-总结)

### 6. [GitHub](https://git-scm.com/book/zh/v2/GitHub-账户的创建和配置)

- 6.1 [账户的创建和配置](https://git-scm.com/book/zh/v2/GitHub-账户的创建和配置)
- 6.2 [对项目做出贡献](https://git-scm.com/book/zh/v2/GitHub-对项目做出贡献)
- 6.3 [维护项目](https://git-scm.com/book/zh/v2/GitHub-维护项目)
- 6.4 [管理组织](https://git-scm.com/book/zh/v2/GitHub-管理组织)
- 6.5 [脚本 GitHub](https://git-scm.com/book/zh/v2/GitHub-脚本-GitHub)
- 6.6 [总结](https://git-scm.com/book/zh/v2/GitHub-总结)

### 7. [Git 工具](https://git-scm.com/book/zh/v2/Git-工具-选择修订版本)

- 7.1 [选择修订版本](https://git-scm.com/book/zh/v2/Git-工具-选择修订版本)
- 7.2 [交互式暂存](https://git-scm.com/book/zh/v2/Git-工具-交互式暂存)
- 7.3 [贮藏与清理](https://git-scm.com/book/zh/v2/Git-工具-贮藏与清理)
- 7.4 [签署工作](https://git-scm.com/book/zh/v2/Git-工具-签署工作)
- 7.5 [搜索](https://git-scm.com/book/zh/v2/Git-工具-搜索)
- 7.6 [重写历史](https://git-scm.com/book/zh/v2/Git-工具-重写历史)
- 7.7 [重置揭密](https://git-scm.com/book/zh/v2/Git-工具-重置揭密)
- 7.8 [高级合并](https://git-scm.com/book/zh/v2/Git-工具-高级合并)
- 7.9 [Rerere](https://git-scm.com/book/zh/v2/Git-工具-Rerere)
- 7.10 [使用 Git 调试](https://git-scm.com/book/zh/v2/Git-工具-使用-Git-调试)
- 7.11 [子模块](https://git-scm.com/book/zh/v2/Git-工具-子模块)
- 7.12 [打包](https://git-scm.com/book/zh/v2/Git-工具-打包)
- 7.13 [替换](https://git-scm.com/book/zh/v2/Git-工具-替换)
- 7.14 [凭证存储](https://git-scm.com/book/zh/v2/Git-工具-凭证存储)
- 7.15 [总结](https://git-scm.com/book/zh/v2/Git-工具-总结)

### 8. [自定义 Git](https://git-scm.com/book/zh/v2/自定义-Git-配置-Git)

- 8.1 [配置 Git](https://git-scm.com/book/zh/v2/自定义-Git-配置-Git)
- 8.2 [Git 属性](https://git-scm.com/book/zh/v2/自定义-Git-Git-属性)
- 8.3 [Git 钩子](https://git-scm.com/book/zh/v2/自定义-Git-Git-钩子)
- 8.4 [使用强制策略的一个例子](https://git-scm.com/book/zh/v2/自定义-Git-使用强制策略的一个例子)
- 8.5 [总结](https://git-scm.com/book/zh/v2/自定义-Git-总结)

### 9. [Git 与其他系统](https://git-scm.com/book/zh/v2/Git-与其他系统-作为客户端的-Git)

- 9.1 [作为客户端的 Git](https://git-scm.com/book/zh/v2/Git-与其他系统-作为客户端的-Git)
- 9.2 [迁移到 Git](https://git-scm.com/book/zh/v2/Git-与其他系统-迁移到-Git)
- 9.3 [总结](https://git-scm.com/book/zh/v2/Git-与其他系统-总结)

### 10. [Git 内部原理](https://git-scm.com/book/zh/v2/Git-内部原理-底层命令与上层命令)

- 10.1 [底层命令与上层命令](https://git-scm.com/book/zh/v2/Git-内部原理-底层命令与上层命令)
- 10.2 [Git 对象](https://git-scm.com/book/zh/v2/Git-内部原理-Git-对象)
- 10.3 [Git 引用](https://git-scm.com/book/zh/v2/Git-内部原理-Git-引用)
- 10.4 [包文件](https://git-scm.com/book/zh/v2/Git-内部原理-包文件)
- 10.5 [引用规范](https://git-scm.com/book/zh/v2/Git-内部原理-引用规范)
- 10.6 [传输协议](https://git-scm.com/book/zh/v2/Git-内部原理-传输协议)
- 10.7 [维护与数据恢复](https://git-scm.com/book/zh/v2/Git-内部原理-维护与数据恢复)
- 10.8 [环境变量](https://git-scm.com/book/zh/v2/Git-内部原理-环境变量)
- 10.9 [总结](https://git-scm.com/book/zh/v2/Git-内部原理-总结)

### A1. [附录 A: 在其它环境中使用 Git](https://git-scm.com/book/zh/v2/附录-A%3A-在其它环境中使用-Git-图形界面)

- A1.1 [图形界面](https://git-scm.com/book/zh/v2/附录-A%3A-在其它环境中使用-Git-图形界面)
- A1.2 [Visual Studio 中的 Git](https://git-scm.com/book/zh/v2/附录-A%3A-在其它环境中使用-Git-Visual-Studio-中的-Git)
- A1.3 [Visual Studio Code 中的 Git](https://git-scm.com/book/zh/v2/附录-A%3A-在其它环境中使用-Git-Visual-Studio-Code-中的-Git)
- A1.4 [Eclipse 中的 Git](https://git-scm.com/book/zh/v2/附录-A%3A-在其它环境中使用-Git-Eclipse-中的-Git)
- A1.5 [IntelliJ / PyCharm / WebStorm / PhpStorm / RubyMine 中的 Git](https://git-scm.com/book/zh/v2/附录-A%3A-在其它环境中使用-Git-IntelliJ-%2F-PyCharm-%2F-WebStorm-%2F-PhpStorm-%2F-RubyMine-中的-Git)
- A1.6 [Sublime Text 中的 Git](https://git-scm.com/book/zh/v2/附录-A%3A-在其它环境中使用-Git-Sublime-Text-中的-Git)
- A1.7 [Bash 中的 Git](https://git-scm.com/book/zh/v2/附录-A%3A-在其它环境中使用-Git-Bash-中的-Git)
- A1.8 [Zsh 中的 Git](https://git-scm.com/book/zh/v2/附录-A%3A-在其它环境中使用-Git-Zsh-中的-Git)
- A1.9 [Git 在 PowerShell 中使用 Git](https://git-scm.com/book/zh/v2/附录-A%3A-在其它环境中使用-Git-Git-在-PowerShell-中使用-Git)
- A1.10 [总结](https://git-scm.com/book/zh/v2/附录-A%3A-在其它环境中使用-Git-总结)

### A2. [附录 B: 在你的应用中嵌入 Git](https://git-scm.com/book/zh/v2/附录-B%3A-在你的应用中嵌入-Git-命令行-Git-方式)

- A2.1 [命令行 Git 方式](https://git-scm.com/book/zh/v2/附录-B%3A-在你的应用中嵌入-Git-命令行-Git-方式)
- A2.2 [Libgit2](https://git-scm.com/book/zh/v2/附录-B%3A-在你的应用中嵌入-Git-Libgit2)
- A2.3 [JGit](https://git-scm.com/book/zh/v2/附录-B%3A-在你的应用中嵌入-Git-JGit)
- A2.4 [go-git](https://git-scm.com/book/zh/v2/附录-B%3A-在你的应用中嵌入-Git-go-git)
- A2.5 [Dulwich](https://git-scm.com/book/zh/v2/附录-B%3A-在你的应用中嵌入-Git-Dulwich)

### A3. [附录 C: Git 命令](https://git-scm.com/book/zh/v2/附录-C%3A-Git-命令-设置与配置)

- A3.1 [设置与配置](https://git-scm.com/book/zh/v2/附录-C%3A-Git-命令-设置与配置)
- A3.2 [获取与创建项目](https://git-scm.com/book/zh/v2/附录-C%3A-Git-命令-获取与创建项目)
- A3.3 [快照基础](https://git-scm.com/book/zh/v2/附录-C%3A-Git-命令-快照基础)
- A3.4 [分支与合并](https://git-scm.com/book/zh/v2/附录-C%3A-Git-命令-分支与合并)
- A3.5 [项目分享与更新](https://git-scm.com/book/zh/v2/附录-C%3A-Git-命令-项目分享与更新)
- A3.6 [检查与比较](https://git-scm.com/book/zh/v2/附录-C%3A-Git-命令-检查与比较)
- A3.7 [调试](https://git-scm.com/book/zh/v2/附录-C%3A-Git-命令-调试)
- A3.8 [补丁](https://git-scm.com/book/zh/v2/附录-C%3A-Git-命令-补丁)
- A3.9 [邮件](https://git-scm.com/book/zh/v2/附录-C%3A-Git-命令-邮件)
- A3.10 [外部系统](https://git-scm.com/book/zh/v2/附录-C%3A-Git-命令-外部系统)
- A3.11 [管理](https://git-scm.com/book/zh/v2/附录-C%3A-Git-命令-管理)
- A3.12 [底层命令](https://git-scm.com/book/zh/v2/附录-C%3A-Git-命令-底层命令)

