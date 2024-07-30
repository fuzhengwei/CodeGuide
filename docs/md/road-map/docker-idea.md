---
title: Docker IntelliJ IDEA
lock: need
---

# Docker WEB IntelliJ IDEA —— 随时随地打开浏览器就能写代码！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

我遇到过不少粉丝伙伴因为自己是老 Windows 电脑，部署项目费劲的问题。因为本身 Java 开发完成以后就是部署到 Linux 服务器的，而 Windows 压根不是 Linux 系统。所以不是装虚拟机，就是装 Docker 来模拟这些环境，但很多时候都差强人意，尤其是配置低的 Windows 又跑不动，这咋办！

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-idea-00.png" width="150px"/>
</div>

**死鬼，我就知道你有很多办法！**

嘿嘿，是的，小傅哥又给你带来一个新方法：”在 Linux 安装一个 IntelliJ IDEA！“ 并且可以在线执行代码、构建项目、打包程序。这不美滋滋，尤其是对有学习项目诉求的伙伴，不就可以随时随地学习自己的项目了吗！

>好啦，接下来小傅哥就带着你安装下。文末提供了本次安装的相关脚本。

## 一、前置说明

- 一台云服务器，最低 2c4g [https://618.gaga.plus](https://618.gaga.plus ) —— 价格比较实惠。
- 安装 Docker、Docker Compose 教程：[https://bugstack.cn/md/road-map/docker.html](https://bugstack.cn/md/road-map/docker.html)

```java
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://dc.j8.work", "https://docker.1panel.live"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```

- 如果拉取镜像失败，可以配置下以上地址。

## 二、安装脚本

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-idea-01.png" width="450px"/>
</div>

- 地址：[https://gitcode.net/KnowledgePlanet/xfg-dev-tech-docker-idea](https://gitcode.net/KnowledgePlanet/xfg-dev-tech-docker-idea)
- 说明：小傅哥这里为你提供了搭建 Docker IDEA 的操作脚本，并配有 JDK、Maven，这样可以更加方便我们构建项目。

## 三、执行安装

### 1. 上传脚本

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-idea-02.png" width="650px"/>
</div>

- 下载项目后，把本地的配置文件传到云服务器端。注意是 root 目录下。也就是 `~` 这里。

### 2. 解压文件

#### 2.1 jdk

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-idea-04.png" width="650px"/>
</div>

```java
[root@lavm-aqhgp9nber java]# tar -zxvf jdk-8u202-linux-x64.tar.gz 
```

- 这个解压后是为了映射到 Docker IntelliJ IDEA 下的。

#### 2.2 maven

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-idea-05.png" width="650px"/>
</div>

```java
[root@lavm-aqhgp9nber maven]# unzip apache-maven-3.8.8.zip 
```

- 可选不非得加压，因为我们可以直接把 `maven .m2` 下的 `settings.xml` 映射到  Docker IntelliJ IDEA 下。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-idea-06.png" width="650px"/>
</div>

```java
[root@lavm-aqhgp9nber maven]# ls -a
.  ..  apache-maven-3.8.8.zip  install-maven.sh  .m2  remove-maven.sh
```

### 3. 授权文件

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-idea-03.png" width="850px"/>
</div>

```java
chmod -R 777 projector-user/
```

- 给整个文件夹包括文件夹下的其他文件一起授权。
- 授权后检查当前文件夹 `projector-user` 和文件夹以下的 java、maven 文件夹。

### 4. 执行安装 

```java
# 命令执行 docker-compose -f docker-compose.yml up -d
# 以下这些都可以做 WEB IDEA 安装。在下面替换就可以。
# docker pull registry.jetbrains.team/p/prj/containers/projector-clion
# docker pull registry.jetbrains.team/p/prj/containers/projector-datagrip
# docker pull registry.jetbrains.team/p/prj/containers/projector-goland
# docker pull registry.jetbrains.team/p/prj/containers/projector-idea-c
# docker pull registry.jetbrains.team/p/prj/containers/projector-idea-u
# docker pull registry.jetbrains.team/p/prj/containers/projector-phpstorm
# docker pull registry.jetbrains.team/p/prj/containers/projector-pycharm-c
# docker pull registry.jetbrains.team/p/prj/containers/projector-pycharm-p

version: '3.9'
services:
  intellij-idea:
    image: registry.jetbrains.team/p/prj/containers/projector-idea-c
    container_name: intellij-idea
    ports:
      - "8887:8887"
    volumes:
      - ~/projector-user:/home/projector-user
      - ~/projector-user/maven/.m2/settings.xml:/home/projector-user/.m2/settings.xml
    tty: true
    stdin_open: true
    restart: unless-stopped  # 这将确保容器在失败时自动重启
```

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-idea-08.png" width="950px"/>
</div>

- 执行脚本 `docker-compose -f docker-compose.yml up -d`
- 安装完成后记得在云服务器开放端口 `8887`

### 5. 测试项目

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-idea-07.png" width="550px"/>
</div>

- 通过 `git clone` 检出项目地址 github/gitee/gitcode ，这样在projector-user 下就可以看到你的项目了。
- 这个是用于 WEB IDEA 打开的项目，当然也可以在 WEB IDEA 创建信息项目。

## 四、访问测试

地址：[http://117.72.37.243:8887/](http://117.72.37.243:8887/) 

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-idea-09.png" width="950px"/>
</div>

- 接下来你就可以在网页端，操作自己的项目了，想怎么玩就怎么玩，和本地效果是一样的。
- 这对于新人伙伴学习编程太有用了，直接在云服务器都能打包部署了。

