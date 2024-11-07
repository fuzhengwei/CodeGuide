---
title: Nas
lock: need
---

# Nas - 这是我犹豫了很久，才买的设备！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

在这之前我犹豫🦑了好久，一台不错的 Nas 四五千块，买这个东西能帮助我啥呢 🤔？但后来发现，这台 2c8g 双盘位 4TB Nas，等同于个人拥有了一台永久可用的，软件丰富的，具备远程访问能力的，终身 Linux 服务器。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-nas-01.gif" width="200px">
</div>

**谁更适合买一台 Nas？**

虽然 Nas 确实很不错，但也不是所有人都有诉求，也不用都买很高配置很贵的。那谁有 Nas 诉求呢？家里有娃的，有大量的照片、视频、文件，需要家人一起存储使用。另外是喜欢看4k蓝光电影，可以用 Nas 搭建一套家庭影院。很多 Nas 也是主打这些功能。

但其实我买 Nas，有上面的功能诉求，但还有作为程序员👨🏻‍💻的编程诉求。我希望能在这台 Nas 上安装 Docker 部署环境，并能通过 SSH 客户端，以使用 Linux 的方式，连接 Nas 完成项目的构建、镜像打包和部署。

>嘿嘿，使用起来，它还真可以！不过，别买错 Nas，有些并不支持！

## 一、Nas 是啥？

NAS（Network Attached Storage，网络附加存储）是一种通过标准的网络拓扑结构（例如以太网）连接到一群计算机上的存储设备。它是一种具有很大存储容量的电脑外敷设备，通过集线器或交换机直接连在网络上，提供跨平台文件共享功能。

更简单的来讲，你就把它当做一台部署在家里的 Linux 服务器就好，只不过它的体积不大，但有很大的硬盘容量。所有开通了权限的用户，都可以使用这台共享服务器。并且它提供了很多的软件资源和网络访问服务。

## 二、买的哪家的 Nas？

市面的 Nas 有很多，如；群晖、威联通、绿联、极空间、华为。它们到没有绝对的谁家好，只不过有会一些目标人群的不同。有一些网上的对比，可以参考；

| 品牌     | 优势                                                                 | 劣势                                                                 |
|----------|----------------------------------------------------------------------|----------------------------------------------------------------------|
| 群晖     | - 软件生态丰富，DSM系统用户友好                                      | - 价格较高                                                           |
|          | - 稳定性和可靠性强                                                  | - 硬件规格在同价位上可能不如竞争对手                                 |
|          | - 拥有庞大的用户社区和丰富的在线资源                                |                                                                      |
|          | - 提供多种数据保护和备份解决方案                                    |                                                                      |
| 威联通   | - 硬件性能强，提供更高的硬件规格                                    | - 软件复杂性较高，新手用户可能较难上手                               |
|          | - 支持虚拟化、容器化应用和多媒体功能                                | - 某些情况下软件更新可能导致系统不稳定                               |
|          | - 提供丰富的扩展选项                                                |                                                                      |
| 绿联     | - 价格实惠，适合预算有限的用户                                      | - 功能较为基础，适合简单存储需求                                     |
|          | - 简单易用，适合家庭用户和小型办公室                                | - 在NAS市场的知名度和用户基础不如群晖和威联通                       |
| 极空间   | - 性价比高，适合中小企业和个人用户                                  | - 软件生态和用户界面不如群晖和威联通成熟                             |
|          | - 提供较为合理的硬件配置                                            | - 相对较小的用户社区和技术支持资源                                   |


我买的是一台 `群晖 DS723+` 并配了`8G内存` + `2*2TB`硬盘互备，因为咱们要做 Docker 安装各类软件，内存大一些更好。选择群晖主要就是想着这东西资源丰富，可以像使用 Linux 服务器一样使用 Nas 满足开发和存储需求。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-nas-02.png" width="400px">
</div>

> 用了群晖 Nas 有5个月了，越用越爽。电脑里的东西基本都搬到群晖了，电脑只成为一个工具了，不在搞那么多存储。

## 三、Nas 的编程用途

群晖 Nas 提供了 群晖管家 APP，只要开启 QuickConnect，无论在家内网还是外网都可以访问和管理 Nas。另外群晖还提供了专门做照片和视频同步管理的 Photos Mobile APP 你可以直接下载使用。

它所有的这些东西，只要对照提供的说明书都可以下载使用。难度极低，很好上手。这里小傅哥主要给大家分享下编程的用途。

这里首先，对于使用 Windows 的小伙伴，如果你有台 Nas，就不需要本地做那么多让 Windows 兼容 Linux 的事情了。你可以直接在这个上面安装提供的 Docker 套件，并完成对应用程序的打包、构建以及 PUSH 镜像到 DockerHub。

另外就是很多小伙伴的电脑配置不高，容量也不大，可以考虑购买 Nas 分摊一部分本身电脑的压力。就像我，如果开启多个 IntelliJ IDEA，在开启 Docker 提供分布式软件环境、在搞一堆网页，以及开启视频录制。这个时候就会非常卡顿了。所以我搞了台 Nas 来解决这个事情。

### 1. 有很多编程软件

群晖 Nas 提供了非常多的配套软件，适合于不同场景诉求的伙伴使用。所以很多买群晖的伙伴都说，是买软件送设备。嘿嘿，不过我更喜欢这部分编程相关的，尤其是这个 Docker、Git Server，嗖的就安装好。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-nas-03.png" width="850px">
</div>

- Nas 还支持配置定时开机关机、休眠，这样会比较省电，也不用一直在那打开着。
- 另外可以自建frp，让自己的nas服务可以直接被公网访问。一年￥48 2c2g 服务器即可。[https://618.gaga.plus](https://618.gaga.plus) 专属地址。[frp](https://bugstack.cn/md/road-map/frp.html) 教程在 bugstack.cn - 路书中。

### 2. Docker 使用

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-nas-04.png" width="850px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-nas-05.png" width="850px">
</div>

- 你可以在提供的操作界面配置 Docker Compose，完成项目文件的配置和启动。之后的体验就和正常 Linux 安装 Docker 部署软件一样了。
- 不过我通常把这里都只是作为配置后的操作界面，配置的操作，我会在用SSH工具直接链接到Nas上。

### 3. SSH 连接 Nas

因为本身 Nas 也是一台 Linux 服务器，所以可以直接用 SSH 连接使用。推荐使用 [https://termius.com/](https://termius.com/) 工具链接，很好用。

- 账号：你的 Nas 登录账号
- 密码：你的 Nas 登录密码

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-nas-06.png" width="850px">
</div>

登录以后，你就可以像使用 Linux 一样操作了，不过大部分执行类命令，要加上 sudo，比如 `sudo docker images`

### 4. 构建项目

有了 Nas 有个很大的好处就是，你可以本地直接当 Nas 的硬盘是本地的一个文件夹，直接打开就操作。里面的文件直接复制粘贴进去或者拿出来就可以。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-nas-07.png" width="650px">
</div>

通过 IntelliJ IDEA 直接打开 Nas 中的项目即可，之后你可以用它上面的 Docker 对项目进行构建了。如下；

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-nas-08.png" width="650px">
</div>

- build.sh 中执行的就是 docker 命令；`docker build -t system/s-pay-mall-mvc-app:1.0 -f ./Dockerfile .`
- 构建完，就可以部署项目了。Linux 上怎么用，这里你就怎么用。

> 当然 Nas 还有很多其他的用途，喜欢折腾的还可以搭建一些你需要的。
