---
title: Aiyun Workbeanch
lock: need
---

# Aiyun Workbeanch 通过流水线方式构建应用镜像（Docker）

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

对于搞编程开发的伙伴来说，我一直都推荐使用 Mac 电脑，因为 Windows 电脑除了相对省点钱，其他的什么也不省！为啥？那我 Windows 电脑推沟里？

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-actions-workflows-01.png" width="150px">
</div>

**大厂更多的都是给程序员👨🏻‍💻配Mac！**

其实到也不是 Windows 不好，只是在编程开发中的环境配置，要有太多的额外的兼容问题。因为 Windows 是一个系统，这个系统被各个厂商使用，安装到自己家的电脑上。因此一个软件安装后，不同的机器，都可能在一些细节功能上，有自己的差异化处理方案。就像 Docker 安装，Mac 电脑直接安装即可，Windows 不是安装虚拟机，就是搞个 wsl2，不少小白基本就在这崩溃了。

相对说，Mac 电脑就好的多，因为人家的系统只给自己家的电脑使用。所以各类软件的安装和使用也不会有那么多的适配问题了。另外 Mac 电脑也等同于是在 Linux 上做开发，你的所有编程中使用的命令，几乎就是无差别的可以在云环境 Linux 执行。因此，Mac 电脑除了不省钱，其他的都省；省时、省心、省力。

不过，Windows 电脑也不能推沟里呀！所以，我们要想点办法，让一些如 Docker 的环境安装和镜像构建，让外部系统解决。不就可以了噻！

>接下来，小傅哥就教大家搞一下这个事情。—— 学到手的全是技术！

## 一、DevOps 流水线

DevOps 流水线，是一种可持续集成交付的手段。用户可以使用流水线自定义编排项目发布过程中所涉及的代码打包、单元测试、自动部署等各项阶段。 通过一系列自动化任务的组合解决日常开发工作中繁琐而重复的任务。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-aliyun-workbench-01.png" width="750px">
</div>

官网（JD）：[https://docs.jdcloud.com/cn/devops/application-scenarios](https://docs.jdcloud.com/cn/devops/application-scenarios)

---

市面上也有很多的免费的可持续交付工具，方便我们自己部署项目使用。如 [Github Actions](https://bugstack.cn/md/road-map/github-actions-workflows.html)、[buddy.works](https://bugstack.cn/md/road-map/buddy.html)、[Jenkins](https://bugstack.cn/md/road-map/jenkins.html)，这些已经在 [bugstack.cn](https://bugstack.cn/) 编程路书（发布部署）中分享。今天小傅哥再分享一个阿里云的 DevOps 流水线操作，实际使用体验效果还不错。

## 二、云效工作台介绍

本次案例会涉及到使用阿里云效平台的 `代码管理`、`流水线配置`、`容器镜像`

### 1. 代码&流水线

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-aliyun-workbench-02.png" width="850px">
</div>

- 地址：[https://devops.aliyun.com/workbench](https://devops.aliyun.com/workbench)
- 说明：用于管理工程代码和配置流水线。你可以提交本地代码，也可以把其他仓库的代码导入到`代码管理`中，如；Github、Gitee、Gitlab，其他的也可以通过 url 导入。

### 2. 容器镜像服务

#### 2.1 容器申请

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-aliyun-workbench-03.png" width="850px">
</div>

- 地址：[https://cr.console.aliyun.com/cn-hangzhou/instance/dashboard](https://cr.console.aliyun.com/cn-hangzhou/instance/dashboard)
- 说明：容器镜像，相当于代理的 [Docker Hub](https://hub.docker.com/) 可以把我们通过流水线构建的项目（SpringBoot/React/...）构建的镜像，放到容器镜像服务中。之后就可以在其他云环境或者本地环境拉取使用了。

#### 2.2 新建服务

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-aliyun-workbench-09.png" width="550px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-aliyun-workbench-10.png" width="550px">
</div>

- 这里要新建一个镜像名称，`xfg-dev-tech-aliyun-workbench` 的镜像。这个镜像为后续的流水线构建使用。
- 创建的时候，要选择对应的仓库。可以是 Codeup 的阿里云代码库，也可以是 Github 关联地址的仓库。

> 再往下开始平台，你需要注册一个云校平台和容器镜像服务。

## 三、上传代码库

这部分会涉及一些 Git 的操作，这里小傅哥准备好了教程可以使用 [https://bugstack.cn/md/road-map/git.html](https://bugstack.cn/md/road-map/git.html)

### 1. 案例工程

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-aliyun-workbench-04.png" width="350px">
</div>

```java
# 基础镜像 openjdk:8-jre-slim 代理地址；registry.cn-hangzhou.aliyuncs.com/xfg-studio/openjdk:8-jre-slim
FROM registry.cn-hangzhou.aliyuncs.com/xfg-studio/openjdk:8-jre-slim

# 作者
MAINTAINER xiaofuge

# 配置
ENV PARAMS=""

# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 在容器内创建目录/home/project 用于存放应用程序和相关文件
RUN mkdir -p /home/project

# 设置工作目录为/home/project，即后续的命令都在这个工作目录下执行。
WORKDIR /home/project

# 添加应用
ADD xfg-dev-tech-app/target/xfg-dev-tech-app.jar /home/project/xfg-dev-tech-app.jar

## 在镜像运行为容器后执行的命令
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /home/project/xfg-dev-tech-app.jar $PARAMS"]
```

- 地址：[https://github.com/fuzhengwei/xfg-dev-tech-aliyun-workbench](https://github.com/fuzhengwei/xfg-dev-tech-aliyun-workbench)
- 说明：这里小傅哥给你准备好了一个测试工程，你可以使用验证阿里云效流水线操作。
- 脚本：注意配置的脚本信息，如果你是新的其他的工程，可以参考配置。

### 2. 上传项目

把案例代码上传到阿里云代码库的方式有很多，可以从 Github 导入，也可以先把代码拉取到本地，在从本地上传到阿里云代码库。但两个方式都建议把代码拉取到本地代码一份，方便进行修改。

#### 2.1 导入代码

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-aliyun-workbench-05.png" width="950px">
</div>

- 首先，进入案例工程地址（Github），点击 Fork 克隆到自己的仓库中。
- 之后，在阿里云效点击导入仓库，这里可以选择从 Github 导入。导入的时候，需要填写 Access Token。创建地址：[https://github.com/settings/tokens](https://github.com/settings/tokens)
- 最后，在确认后会展示你的 Github 仓库列表，选择要导入的工程即可。

#### 2.2 上传代码

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-aliyun-workbench-06.png" width="950px">
</div>

- 首先，开始操作之前，你要把提供的案例代码拉取到本地。
- 之后，在云效代码库，新建一个代码库。新建后可以获得一个 https 提交代码库的地址。另外，要注意你还要在云效仓库个人设置里，创建一个，个人访问令牌（地址：[https://account-devops.aliyun.com/settings/profile](https://account-devops.aliyun.com/settings/profile)）。
- 最后，在自己 Intellij IDEA 菜单栏上，点击 Git 选择 Manage Remotes 添加阿里云效仓库地址，之后就可以把代码提交到阿里云效了。

### 3. 添加流水线

#### 3.1 构建操作

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-aliyun-workbench-11.png" width="950px">
</div>

- 如图，配置构建信息。注意，☑️ 制品中包含打包路径的目录。

#### 3.2 添加步骤；Docker镜像构建

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-aliyun-workbench-12.png" width="750px">
</div>

- 添加新的步骤，选择构建并推送至ACR（个人版）注意配置路径正确。

#### 3.3 添加步骤；邮件通知

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-aliyun-workbench-13.png" width="750px">
</div>

- 添加一个邮件通知的插件，构建后会接收到邮件。

### 4. 构建镜像

#### 4.1 执行构建

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-aliyun-workbench-14.png" width="950px">
</div>

- 点击运行，之后就可以构建镜像了。如果哪个节点失败了，可以点日志查看失败原因。

#### 4.2 构建结果

##### 4.2.1 邮件通知

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-aliyun-workbench-17.png" width="450px">
</div>

- 你会收到一个来自云效构建的通知邮件。代表着构建的结果。

##### 4.2.2 查看镜像

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-aliyun-workbench-15.png" width="950px">
</div>

- 构建完成镜像后，可以进入`容器镜像服务`中找到镜像。地址：[https://cr.console.aliyun.com/cn-hangzhou/instance/repositories](https://cr.console.aliyun.com/cn-hangzhou/instance/repositories)
- 之后还可以通过公网地址拉取镜像，命令：`docker pull registry.cn-hangzhou.aliyuncs.com/fuzhengwei/xfg-dev-tech-aliyun-workbench:1.0.0`

##### 4.2.3 拉取镜像

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-aliyun-workbench-16.png" width="950px">
</div>

```java
[root@hcss-ecs-decd ~]# docker pull registry.cn-hangzhou.aliyuncs.com/fuzhengwei/xfg-dev-tech-aliyun-workbench:1.0.0
1.0.0: Pulling from fuzhengwei/xfg-dev-tech-aliyun-workbench
1efc276f4ff9: Already exists 
a2f2f93da482: Already exists 
1a2de4cc9431: Already exists 
d2421c7a4bbf: Already exists 
64e0330ea35f: Pull complete 
4da07e374896: Pull complete 
4f4fb700ef54: Pull complete 
eb9d0e194679: Pull complete 
Digest: sha256:a7af08fff4ea0b76184a2fad7e7ca639ab4a9ad16573480b526eda672ed8a483
Status: Downloaded newer image for registry.cn-hangzhou.aliyuncs.com/fuzhengwei/xfg-dev-tech-aliyun-workbench:1.0.0
registry.cn-hangzhou.aliyuncs.com/fuzhengwei/xfg-dev-tech-aliyun-workbench:1.0.0
[root@hcss-ecs-decd ~]# docker tag registry.cn-hangzhou.aliyuncs.com/fuzhengwei/xfg-dev-tech-aliyun-workbench:1.0.0 fuzhengwei/xfg-dev-tech-aliyun-workbench:1.0.0
[root@hcss-ecs-decd ~]# docker images
REPOSITORY                                                                   TAG       IMAGE ID       CREATED          SIZE
fuzhengwei/xfg-dev-tech-aliyun-workbench                                     1.0.0     df740ba425bb   24 minutes ago   221MB
```

- 镜像构建完成后，就可以在云服务器上拉取镜像了。
- 好啦，到这你就可以愉快的完善了，前端构建镜像也是一样的操作。
