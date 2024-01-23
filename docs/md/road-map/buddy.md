---
title: Buddy
lock: need
---

# 一款在线使用的，更轻量、更好用的 CI&CD 工具 buddy.works

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

2c2g 云服务器，你占用了83%的内存空间！**傅哥！Jenkins 用不起呀！** 我好不容易找对象要50块买的一年服务器，要学你的项目。现在都被 Jenkins 吃了！

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-buddy-03.png?raw=true" width="200px">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-buddy-01.gif?raw=true" width="200px">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-buddy-02.png?raw=true" width="200px">
</div>

哈哈哈，最近一段时间跟 `CI&CD` 工具杠上了，一路调研和尝试体验了 Jenkins、GitLab、Drone、CircleCI、TeamCity、Bamboo、Travis CI、Codeship、GoCD、Wercker、Semaphore、Nevercode、Spinnaker、Buildbot... 等十多款风格迥异的构建部署工具。可以说 Jenkins 依旧是当家老大。所以小傅哥的第一篇 `CI&CD` 文章则是[《用上了 Jenkins，个人部署项目是真方便！》](https://mp.weixin.qq.com/s/tWuse0ejDOTQho2182iYWw) 但这货也确实是嘎嘎占内存！

为此，小傅哥今天分享另外一款，轻量、简单、好用，还好看的在线 `CI&CD` 工具。直接点点点，配置下就能使用了。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-buddy-04.png?raw=true" width="800px">
</div>

咋样，这款工具看着不错吧。它叫 [buddy.works](https://buddy.red/) 是一款付费软件，但提供了免费白皮袄的额度。足够我们个人用户使用。

>本文会向小伙伴介绍这块工具的使用操作，方便小白们快速上手。在文末还提供了Java项目学习，让小白从实战中积累变成经验。

## 一、工具相关

- 官网(中文)：[https://buddy.red](https://buddy.red)
- 文档(中文)：[https://docs.buddy.red](https://docs.buddy.red)
- 官网(英文)：[https://buddy.works](https://buddy.works)

这款工具号称 **「最易用的CI/CD没有之一」**，体验过后我表示，他说的对！

## 二、注册账户

地址：[https://buddy.works/sign-up?locale=cn&utm_campaign=buddy.red](https://buddy.works/sign-up?locale=cn&utm_campaign=buddy.red)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-buddy-05.png?raw=true" width="450px">
</div>

## 三、添加项目

地址：[https://app.buddy.works/](https://app.buddy.works/)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-buddy-06.png?raw=true" width="750px">
</div>

- 首先，需要在首页选择【添加项目】，并根据步骤选择你的仓库。
- 之后，这里小傅哥选择了专用服务器，填写我的Gitcode仓库地址。「Gitcode的账密就是CSDN的账密」
- 提示：你可以Fork代码到自己的仓库进行使用 [https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-jenkins](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-jenkins) 

## 四、添加流水线

流水线，就跟它的名字一样，用于构建项目所需的一条链路流程。如；Maven 构建、镜像打包&发布、SSH 基本驱动容器启动。也可以是 Maven 构建后直接通过 SFTP 把Jar传递到 Linux 服务器，在通过 SSH 链接执行 Shell 脚本完成 Jar 的镜像打包和部署。

所以，接下来我们先来完成一个流水线的最基础 Maven 构建，之后再分别添加不同类型的流水线操作。「Buddy提供了各种插件，你可以分别组合他们进行使用，完成项目的部署。」

注意📢：流水线上的节点，你可以在右侧的绿色按钮进行关闭，只执行你需要的节点。比如现在你已经知道 Maven 构建成功了，只需要推送镜像。那么可以去掉上一个节点，这样速度更快。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-buddy-07.png?raw=true" width="750px">
</div>

### 1. Docker 流水线

- 说明：这条流水线的步骤为；Maven 构建、构建Docker镜像、推送Docker镜像、SSH连接服务器拉取镜像和部署。
- 步骤：你可以点击**小加号+**，选择添加不同的流水线节点。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-buddy-08.png?raw=true" width="750px">
</div>

#### 1.1 构建Docker镜像

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-buddy-09.png?raw=true" width="750px">
</div>

#### 1.2 推送Docker镜像

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-buddy-10.png?raw=true" width="750px">
</div>

- 注意📢：你已经在 [https://hub.docker.com/](https://hub.docker.com/) 创建了自己的镜像，比如我的是；[fuzhengwei/xfg-dev-tech-jenkins](fuzhengwei/xfg-dev-tech-jenkins) 这样你的镜像才能PUSH进去。

#### 1.3 SSH连接服务器拉取镜像和部署

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-buddy-11.png?raw=true" width="750px">
</div>

```shell
# 先删除之前的容器和镜像文件
if [ "$(docker ps -a | grep xfg-dev-tech-jenkins)" ]; then
docker stop xfg-dev-tech-jenkins
docker rm xfg-dev-tech-jenkins
fi
if [ "$(docker images -q xfg-dev-tech-jenkins)" ]; then
docker rmi xfg-dev-tech-jenkins
fi

docker pull fuzhengwei/xfg-dev-tech-jenkins:2.0
docker run -itd -p 8091:8091 --name xfg-dev-tech-jenkins fuzhengwei/xfg-dev-tech-jenkins:2.0
```

- 注意：拉取镜像的速度取决于你的服务器所在的地区，有时候会拉取失败。如果你的云服务器拉取镜像总是失败❌，可以选择第二个方式 SSH 流水线。它不需要走到DockerHub，能减少网络操作。

### 2. SSH 流水线

说明：这条流水线的步骤为；Maven构建、通过 SFTP 上传构建的Jar到云服务器。之后通过 SSH 执行 Shell 脚本，在云服务器创建出 Dockerfile 「注意路径」，这样 Docker镜像就直接在云服务器的 Docker上了，不需要在通过 DockerHub 拉取镜像。最后通过 SSH 执行 Shell 脚本启动服务即可。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-buddy-12.png?raw=true" width="750px">
</div>

#### 2.1 SFTP 上传Jar

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-buddy-13.png?raw=true" width="750px">
</div>

- 配置好传输地址、目标地址，通过 SSH 连接云服务器进行传输。
- `/dev-ops`这个是云服务器的地址，你可以自己定义任何的地址。如果文件夹不存在，也会自动创建。

#### 2.2 创建Dockerfile

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-buddy-14.png?raw=true" width="750px">
</div>

```shell
#!/bin/bash

cd /dev-ops

# 定义Dockerfile的路径
DOCKERFILE_PATH="https://bugstack.cn/images/roadmap/tutorial/Dockerfile"
    
# 创建Dockerfile并写入内容
cat > $DOCKERFILE_PATH << 'EOF'
# 基础镜像 openjdk:8-jre-slim
FROM openjdk:8-jre-slim

# 作者
MAINTAINER xiaofuge

# 配置
ENV PARAMS=""

# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 添加应用
ADD target/xfg-dev-tech-app.jar /xfg-dev-tech-app.jar

## 在镜像运行为容器后执行的命令
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /xfg-dev-tech-app.jar $PARAMS"]
EOF

echo "Dockerfile has been created."
```

- 因为我们把 Maven 构建的 Jar 传到了云服务器。所以需要在 Jar 对应的目录下，创建出 Dockerfile 这样就可以通过执行 Dockerfile 完成镜像的构建了。
- 此外如果你的代码库配置有 Dockerfile，也可以通过 SFTP 的方式把 Dockerfile 上传到云服务器对应 Jar 的位置。「我只是帮你选择了一个更稳的方式」

#### 2.3 运行服务

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-buddy-15.png?raw=true" width="750px">
</div>

```shell
# 先删除之前的容器和镜像文件
if [ "$(docker ps -a | grep xfg-dev-tech-jenkins)" ]; then
docker stop xfg-dev-tech-jenkins
docker rm xfg-dev-tech-jenkins
fi
if [ "$(docker images -q xfg-dev-tech-jenkins)" ]; then
docker rmi xfg-dev-tech-jenkins
fi

cd /dev-ops
docker build -t xiaofuge/xfg-dev-tech-jenkins:1.3 .
docker run -itd -p 8091:8091 --name xfg-dev-tech-jenkins xiaofuge/xfg-dev-tech-jenkins:1.3
```

- `cd /dev-ops`进入到文件路径。之后构建镜像和部署。

## 五、运行流水线

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-buddy-16.png?raw=true" width="750px">
</div>

- 点击**运行**，就可以顺序的执行你的配置的流水线了。如果某个执行失败也可以重试。
- 如果你执行完成3个节点，最后一个失败。那么是可以把前面的流程关闭，只去验证最后一个流程。这样会更快。

>怎么样，是不是很方便。但要注意，https://app.buddy.works - 账单中，会记录你的免费额度。


