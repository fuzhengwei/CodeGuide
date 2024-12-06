---
title: Docker What？
lock: need
---

# Docker 是什么？

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

**好用，真的好用！** 自从使用 Docker 后，我可以非常简单的`安装`、`使用`、`卸载`各类所需的软件，如；MySql、Redis、RabbitMQ、XXL-Job、FRP，等一些列开发环境和我自己开发的好的项目进行上线发布。以前一个MySql安装到卸载，可能半天时间都没了。但有 Docker 后，我自己既可以是开发工程师也是可以是软件实施工程师。因为他可以一行脚本即可完成所有的操作，脚本在，环境就在，服务就在。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-00.gif" width="200px">
</div>

**那 Docker 是什么呢？**

Docker 是什么，其实来自于 Docker 的图标已经说明。Docker 的图标是一条鲸鱼 🐳一样的大船，上面摆放了很多集装箱。你可以把这些集装箱当做是一个个不同的应用程序，虽然不同但可以统一用一套集装箱（脚本命令）承载，并承放在统一一条大船上（环境上）。而且这些集装箱都有自己的编号ID（有自己的IP）互相隔离，不受影响。

Docker 是一个用于开发、发布和运行应用程序的开放平台。Docker 可让您将应用程序与基础架构分离，以便快速交付软件。借助 Docker，您可以像管理应用程序一样管理基础架构。通过利用 Docker 的发布、测试和部署代码方法，您可以显著减少编写代码和在生产中运行代码之间的延迟。

官网：[https://www.docker.com/](https://www.docker.com/)
文档：[https://docs.docker.com/get-started/docker-overview/](https://docs.docker.com/get-started/docker-overview/)

## 一、Docker 安装在哪

Docker 可以安装在 Windows + wsl2、Mac、Linux，支持 ARM、AMD 架构。它可以通过软件下载安装和执行脚本命令安装，也可以通过云服务器提供的镜像直接购买云服务器时选择使用。我们可以看下 Docker 和虚拟机安装在操作系统上的关系来了解 Docker 的安装位置。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-02.png" width="550px">
</div>

与虚拟机的安装使用相比，Docker 会把应用所需的依赖、函数库、甚至其他的软件应用可以一起打包成一个镜像，这样在应用程序运行时，就可以直接调用本地函数库，然后和 Linux 内核进行通信。有了这样的设计，你也就不需要关心每一个应用所需的环境都是啥了，也不用为每一个应用安装各类环境到 Linux 或者虚拟机了。也就做到了跨系统的运行。这有点类似于 Java 的 JVM 虚拟机。

- 本地安装：[https://www.docker.com/](https://www.docker.com/) - 下载 Mac、Windows 你需要的版本进行安装。
- 云服务器：[https://bugstack.cn/md/road-map/docker.html](https://bugstack.cn/md/road-map/docker.html) - centos 7.9 其他的也都类似操作即可。

>安装完成后都可以通过命令操作，安装、部署、卸载软件。文末提供了教程链接。

## 二、Docker 怎么工作

Docker 为了屏蔽软件使用差异，会统一对这些软件进行镜像打包，把一个软件所需的各类环境都打包到镜像中。我们在使用的时候，就是使用各类平台提供好的软件镜像，进行服务部署。同样的我们也可以作为镜像提供方，把我们的应用程序 SpringBoot、React、VUE 等，打包成镜像，让我们在其他地方，如云服务器进行部署。或者提供给全网的人员，进行部署使用。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-03.png" width="750px">
</div>

如图，这是一整套的，本地拉取镜像、部署环境、开发代码、发布镜像，再到云服务器拉取镜像、部署项目和环境的过程。在这个过程中，我们也可以借助于如 Github Action 完成镜像的构建和发布，还可以在云服务器上直接构建镜像，减少了拉取拉取的过程。

> 你可以理解为，Docker 就是一个中心和一个客户端，中心管理镜像，客户端拉取使用或者构建发布镜像。

## 三、Docker 命令说明

Docker 的操作是通过脚本命令配置和执行完成使用，不过不用害怕命令😱，命令是最简单、直接、可靠的方式。当你习惯命令以后，你会很喜欢它，并且知道只要执行这个命令就一定会有结果，而不像被软件包装后，不确定是软件的问题还是命令的问题。

Docker 安装软件分为直接使用和通过 Docker Compose 脚本。直接使用如；`docker run -d --restart=always --name portainer -p 9000:9000 -v /var/run/docker.sock:/var/run/docker.sock portainer/portainer` 这条命令会自动拉取和执行脚本。不过对于更大的项目部署，Docker Compose 脚本更好用。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-04.png" width="750px">
</div>

- 如图，是整个 Docker Compose 在配置一套执行脚本后，各个脚本的核心用途。当然 Docker Compose 还有不少的命令，比如容量的限制、端口的处理、默认的命令等，都是可以操作的，凡是你想的合理的，它都可以支持。
- 特别注意，安装到云服务器的docker部署的应用，外部访问需要走公网IP:Port端口，之后这个端口要在安全组打开。这样就类似于你再本地自己的电脑发，访问另外一台电脑上的服务了。
- 如果在使用中遇到其他命令不理解的，可以让 [openai](https://openai.itedus.cn) 解释。

## 四、Docker 实操教程

此外，为了大家更好的使用 Docker 搭建各类环境，小傅哥为大家准备好了文档和视频。可以放心食用。

- 文档：[https://bugstack.cn/md/road-map/docker.html](https://bugstack.cn/md/road-map/docker.html) - 这是一个系列，你可以从左侧目录选择阅读。
- 视频：[https://www.bilibili.com/video/BV1xw411W7xf](https://www.bilibili.com/video/BV1xw411W7xf) - 对应一整套的视频，手把手操作。