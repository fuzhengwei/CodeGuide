---
title: Docker 镜像构建 - Github Action
lock: need
---

# Docker 镜像构建 - Github Action

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

最近看到不少研发伙伴，因为 DockerHub 拉取镜像失败，不能愉快的「👨🏻‍💻凿代码」了而头疼。但这种也就只能拦住`小卡拉米`程序员，高阶的码农有太多种方式让自己愉快的敲代码。就像 Maven 很慢，我可以自建 Maven 仓库。GitHub 很慢，我们可以自建 Gitlab。同样 Docker 很慢，我们还是可以自建 Docker 仓库哇🤩。—— 一套免费自建方案，满足个人开发诉求。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-actions-workflows-01.png" width="150px">
</div>

**小白基础很差，每次收获都很大！**

很多我们自己不能直接访问的服务，在很多互联网公司中都是可以直接访问的，包括；GitHub、谷歌，因为这些公司都是有专门备案的可信的专线网络。这也就是为什么很多机智的伙伴，有 github 地址，但访问很慢的时候，会直接在 gitee 仓库，通过导入 github 地址的方式进行访问。因为 gitee 是有对应的网络专线的，他们可以来拉取代码（并做安全审核），让我们可以放心使用。

嘿嘿，那么就有办法了。同样的访问不了 DockerHub，拉取不了进项。但阿里云有镜像仓库呀。阿里云可以访问 GitHub，GitHub 也可以访问阿里云。那么简单了，我们可以基于 GitHub 的 Actions 服务，执行构建和推送镜像的脚本，把需要的镜像推送到阿里云 Docker 镜像仓库，这不就可以满足自己使用了吗！

>接下来，小傅哥就教大家搞一下这个事情。—— 学到手的全是技术！

## 一、GitHub Actions 是什么

我把 GitHub Actions 当成一种免费的云服务器，我估计不少程序员也都这么理解！因为它可以执行出非常多的骚操作！

**正经的！** GitHub Actions 是 GitHub 提供的一项持续集成和持续交付（CI/CD）服务。它允许开发者在代码库中自动化各种任务，例如构建、测试和部署代码。通过定义工作流（workflow），开发者可以在特定事件（如代码推送、拉取请求等）发生时自动触发这些任务。

嘿嘿，你看，其实还是一台可以执行任意脚本的云服务器！除了说的 CI/CD 持续交付，也包括它可以定时的执行的任意脚本，比如；定时执行 Java 的 Main 函数，调用下 openai 接口，给自己的公众号每天推送一个最新的技术信息。或者是各类签到就送东西的平台，定时+随机时间签到获取金币/京豆/积分。

## 二、GitHub Actions  的使用

小傅哥不只是一个后端Java工程师，同时也能开发点前端，再搞点实施运维的事情。这就让我对很多问题，都有了很多的解决方案。

所以在小傅哥星球「码农会锁」带着大家实战的大营销平台项目，就提供了多种的 Docker 部署方式。如；本地构建再推送镜像到 DockerHub，鉴于不少伙d伴本地安装 Docker 费劲，又提供了直接把一台云服务器配置成开发机器，在云服务器直接 Maven 编译和构建镜像，这样就满足了大部分伙伴的开发部署诉求。

当为了让伙伴得到更全面的学习，小傅哥最近又开始提供了 GitHub Actions CI/CD 持续交付的部署方式。只要你提交代码，即可自动进行构建和完成镜像的推送，并且是同时把镜像推送到 DockerHub 和 阿里云的 Docker 镜像中。接下来小傅哥就告诉大家是怎么操作的。

### 1. 阿里云镜像仓库配置

#### 1.1 创建个人实例

- 地址：[https://cr.console.aliyun.com/cn-hangzhou/instances](https://cr.console.aliyun.com/cn-hangzhou/instances)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-actions-workflows-02.png" width="750px">
</div>

#### 1.2 查看连接凭证

- 地址：[https://cr.console.aliyun.com/cn-hangzhou/instance/credentials](https://cr.console.aliyun.com/cn-hangzhou/instance/credentials)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-actions-workflows-03.png" width="750px">
</div>

#### 1.3 镜像使用说明 - 推送后即可使用

- 地址：[https://cr.console.aliyun.com/repository/cn-hangzhou/fuzhengwei/big-market-app/details](https://cr.console.aliyun.com/repository/cn-hangzhou/fuzhengwei/big-market-app/details) - `修改为你的地址进行登录`
- 使用：`docker pull registry.cn-hangzhou.aliyuncs.com/xfg-studio/big-market-app:3.0`

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-actions-workflows-04.png" width="750px">
</div>

### 2. GitHub 仓库 Actions CI/CD 配置

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-actions-workflows-05.png" width="750px">
</div>

- 首先，点开你的 GitHub 中需要使用 Actions 的项目，点击 Settings 设置。
- 之后，添加阿里云镜像仓库配置，包括用户名和密码。[https://cr.console.aliyun.com/cn-hangzhou/instance/credentials](https://cr.console.aliyun.com/cn-hangzhou/instance/credentials)
- 最后，配置 DockerHub 用户名和 `New Access Token`。[https://hub.docker.com/settings/security](https://hub.docker.com/settings/security)

### 3. 工程脚本配置

#### 3.1 工程目录

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-actions-workflows-06.png" width="450px">
</div>

- 新增加了一个 ActionDockerfile 方便管理路径。

#### 3.2 Dockerfile

```java
# 基础镜像
FROM openjdk:8-jre-slim

# 作者
MAINTAINER xiaofuge

# 配置
ENV PARAMS=""

# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 添加应用
ADD ./big-market-app/target/big-market-app.jar /big-market-app.jar

ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /big-market-app.jar $PARAMS"]
```

#### 3.3 workflows

```java
name: Maven Build and Docker Image CI

on:
  push:
    branches: [ "master" ]
  pull_request:
    branches: [ "master" ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    - name: Checkout code
      uses: actions/checkout@v4

    - name: Set up JDK 8
      uses: actions/setup-java@v3
      with:
        distribution: 'adopt'
        java-version: '8'

    - name: Dependies Cache
      uses: actions/cache@v2
      with:
        path: ~/.m2/repository
        key: ${{ runner.os }}-maven-${{ hashFiles('**/pom.xml') }}
        restore-keys: |
          ${{ runner.os }}-maven-

    - name: Build with Maven
      run: |
          mvn clean package -Dmaven.test.skip=true

    - name: Login to DockerHub
      uses: docker/login-action@v1 
      with:
        username: ${{ secrets.DOCKERHUB_USERNAME }}
        password: ${{ secrets.DOCKERHUB_TOKEN }}

    - name: Login to DockerAlibaba
      run: |
        docker login --username=${{ secrets.ALIYUN_REGISTRY_USER }} --password=${{ secrets.ALIYUN_REGISTRY_PASSWORD }} registry.cn-hangzhou.aliyuncs.com

    - name: Build and push
      uses: docker/build-push-action@v2
      with:
        context: .
        file: ./big-market-app/ActionDockerfile
        # 所需要的体系结构，可以在 Available platforms 步骤中获取所有的可用架构 https://docs.docker.com/build/building/multi-platform/
        platforms: linux/amd64
        # 镜像推送时间
        push: ${{ github.event_name != 'pull_request' }}
        # 给清单打上多个标签
        tags: |
            fuzhengwei/big-market-app:3.0
            fuzhengwei/big-market-app:latest    

    - name: Tag image for Alibaba Cloud
      run: |
        docker tag fuzhengwei/big-market-app:3.0 registry.cn-hangzhou.aliyuncs.com/fuzhengwei/big-market-app:3.0
        docker tag fuzhengwei/big-market-app:latest registry.cn-hangzhou.aliyuncs.com/fuzhengwei/big-market-app:latest

    - name: Push to Alibaba Cloud Container Registry
      run: |
        docker push registry.cn-hangzhou.aliyuncs.com/fuzhengwei/big-market-app:3.0
        docker push registry.cn-hangzhou.aliyuncs.com/fuzhengwei/big-market-app:latest
```

- 这里就是 GitHub 的 Actions 执行脚本，包括；监听 master 分支、拉取代码、设置环境、Maven 打包、构建镜像、推送镜像分别到 DockerHub 和 阿里云镜像仓库。
- 这样我们每次需要新的打包镜像时，可以直接在这里修改下版本号，提交即可。

### 4. 启动运行

GitHub 的 Actions 是自动执行的，只要监听到 Master 分支有变化，即可自动执行并推送镜像到仓库中。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-actions-workflows-07.png" width="650px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-actions-workflows-08.png" width="650px">
</div>

> 运行到这里，你就可以在 DockerHub、阿里云仓库同时看到自己的镜像文件了。想咋用就咋用。

## 三、GitHub Actions 其他玩法

### 1. 同步镜像文件

- 地址：[https://github.com/fuzhengwei/docker-image-pusher](https://github.com/fuzhengwei/docker-image-pusher)

```
mysql:8.0.32
phpmyadmin:5.2.1
redis:6.2
spryker/redis-commander:0.8.0
rabbitmq:3.12.9
```

1. 在 `images.txt` 添加你需要的镜像（PR方式提交），你可以从 https://hub.docker.com/ 搜索需要的镜像后添加。
2. 新添加镜像，需要等待1分钟同步。之后通过命令 `docker pull registry.cn-hangzhou.aliyuncs.com/xfg-studio/mysql` 拉取你需要的镜像，如果有版本号，可以添加。如；`mysql:8.0.32`

### 2. 自动部署博客

- 地址：[https://github.com/fuzhengwei/xfg-dev-tech-blog](https://github.com/fuzhengwei/xfg-dev-tech-blog)
- 视频：[https://www.bilibili.com/video/BV1ri421a7Aj/](https://www.bilibili.com/video/BV1ri421a7Aj/)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-github-actions-workflows-09.png" width="650px">
</div>
