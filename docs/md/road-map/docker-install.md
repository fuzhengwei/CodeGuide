---
title: Docker 环境配置（一键安装）
lock: need
---

# Docker 环境配置（一键安装）

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?isOutside=true&aid=114695709132495&bvid=BV1oaNazEEf5&cid=30537353073&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

大家好，我是技术UP主小傅哥。

说实话，做项目不上线，等于吃面不配蒜🧄，效果少一半！面试官也说：“所有做Java编程项目，没有上线云服务器的，一律当玩具看！” 是呀，做完项目不上线，是不你做的项目没法运行，是个小卡拉米练手的？🤔 那怎么办？

其实，上线云服务器非常非常简单，而且云服务器价格也非常非常便宜！趁着618活动月，**28块钱**，都能买一年的云服务器☁️，干嘛不上车！

<div align="center">
    <img src="https://bugstack.cn/images/system/zsxq/xingqiu-231018-00.png" width="200px">
</div>

**啥是云服务器？**

云服务器，就等同于自己的另外一个电脑💻，在另外一台电脑部署 redis、mysql、mq等，本地电脑连接过去使用。尤其是 Windows 电脑用户，真心建议搞个云服务器，否则你会浪费非常多的时间这套 Windows 适配问题。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-install-06.png" width="650px">
</div>

这样有了云服务器，就可以不用嚯嚯本地电脑了，安装了卸，卸了安装，把自己本机电脑环境弄的乱码起糟，全是费时费力的事。有这精力，不如用一台云服务器部署环境，开发完成项目后，再上线云服务器。既节省本地电脑资源，又锻炼了云服务器操作，起步一举两得！

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-idea-00.png" width="150px">
</div>

不过，放心！别担心你不会用云服务器，因为小傅哥已经给你准备了一件安装云服务器环境的脚本，和各类部署环境和构建项目的视频。**即使是小卡拉米，也能跟着学习下来。**

> 🧧小傅哥还提供了非常多的编程实战项目，包括；业务的、组件的、AI的、源码的、轮子的，可以关注公众号「bugstack虫洞栈」回复「星球」加入。

## 一、优惠云服务器地址

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-install-01.png" width="400px">
</div>

- 购买地址：[https://618.gaga.plus](https://618.gaga.plus)
- 购买地址：[https://618.gaga.plus](https://618.gaga.plus)
- 购买地址：[https://618.gaga.plus](https://618.gaga.plus)

**我适合买哪个服务器？**

- 2c2g 1年，28￥，可部署一套 docker、mysql、redis、SpringBoot 单体项目，用于替代本地电脑的环境部署。
- 2c4g 1年（非常推荐3年），109￥，可部署一套 docker、mysql、redis、rabbitmq、xxl-job、SpringBoot 分布式微服务项目。 
- 2c8g 1年，328￥，适合部署小傅哥星球社群[大部分项目](https://bugstack.cn/md/zsxq/material/student-learn-advanced.html)，可以完成多个微服务项目部署。

注意📢：购买选择系统时，推荐系统镜像，**centos 7.9**

>如果自己账号不是新人身份，可以自己注册个新账号，用家里人JD扫码认证一下即可。

🎁 礼物赠送，购买2c4g 3年的，赠送Joy公仔，邮寄到家！购买后，联系小傅哥（微信：fustack）

## 二、一键部署脚本

小傅哥，这里为你准备一键安装 Docker 环境的脚本文件，你可以非常省心的完成 Docker 部署。使用方式如下。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-install-02.png" width="650px">
</div>

- **地址**：<https://github.com/fuzhengwei/xfg-dev-tech-docker-install>
- **地址**：<https://gitcode.com/Yao__Shun__Yu/xfg-dev-tech-docker-install>

本文档介绍如何执行项目中的各个脚本，包括权限设置和执行步骤。

### 1. 上传脚本&设置权限

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-install-03.png" width="650px">
</div>

- ssh 工具，推荐 termius。[https://bugstack.cn/md/road-map/tool.html](https://bugstack.cn/md/road-map/tool.html) 免费的就够用，带有 sftp 工具。
- 左侧是云服务器空间 root 下，右侧是本地环境。可以把整个文件夹全部拖到云服务器。
- 另外，也可以在云服务器执行 `sudo yum install git` 安装 Git，通过 git clone 拉取脚本。

在执行任何脚本之前，需要先为脚本文件添加可执行权限：

```
# 为所有脚本添加可执行权限
chmod +x environment/jdk/install-java.sh
chmod +x environment/jdk/remove-java.sh
chmod +x run_install_docker_local.sh
chmod +x run_install_software.sh
chmod +x install-maven.sh
chmod +x remove-maven.sh

```
或者一次性为所有脚本添加权限：

```
find . -name "*.sh" -type f -exec chmod +x {} \;
```

### 2. JDK 安装脚本

#### 2.1 安装 JDK

脚本位置： environment/jdk/install-java.sh

功能： 支持安装 JDK 8 和 JDK 17

执行方式：

```
# 交互式安装（推荐）
sudo ./environment/jdk/install-java.sh

# 指定版本安装
sudo ./environment/jdk/install-java.sh -v 8    # 安装 JDK 8
sudo ./environment/jdk/install-java.sh -v 17   # 安装 JDK 17

# 强制安装（覆盖已有安装）
sudo ./environment/jdk/install-java.sh -f -v 8

# 静默安装
sudo ./environment/jdk/install-java.sh -q -v 8

# 自定义安装目录
sudo ./environment/jdk/install-java.sh -d /opt/java -v 8
```
注意事项：

- 需要 root 权限执行
- 脚本会提示手动下载 JDK 包到 /dev-ops/java 目录
- 支持的版本：JDK 8 (1.8.0_202) 和 JDK 17 (17.0.14)
- 安装完成后环境变量会自动配置

#### 2.2 卸载 JDK

脚本位置： environment/jdk/remove-java.sh

功能： 彻底清理 JDK 安装和环境配置

执行方式：

```
# 交互式删除（推荐）
sudo ./environment/jdk/remove-java.sh

# 强制删除
sudo ./environment/jdk/remove-java.sh -f

# 静默删除
sudo ./environment/jdk/remove-java.sh -f -q

# 指定安装目录删除
sudo ./environment/jdk/remove-java.sh -d /opt/java

# 删除时不备份配置文件
sudo ./environment/jdk/remove-java.sh --no-backup
```
注意事项：

- 需要 root 权限执行
- 会自动备份配置文件（除非使用 --no-backup）
- 清理系统和用户级环境变量配置

### 2.3 Maven 安装脚本

#### 2.3.1 安装 Maven

脚本位置：`environment/maven/install-maven.sh`

功能：自动安装 Apache Maven 3.8.8

执行方式：

```bash
# 交互式安装（推荐）
sudo ./environment/maven/install-maven.sh

# 自定义安装目录
sudo ./environment/maven/install-maven.sh -d /opt/maven

# 使用本地Maven包
sudo ./environment/maven/install-maven.sh -p /path/to/apache-maven-3.8.8.zip

# 强制安装（覆盖已有安装）
sudo ./environment/maven/install-maven.sh -f

# 静默安装
sudo ./environment/maven/install-maven.sh -q

# 强制静默安装
sudo ./environment/maven/install-maven.sh -f -q
```

### 3. Docker 安装脚本

脚本位置： run_install_docker_local.sh

功能： 使用本地的 install_docker.sh 脚本安装 Docker

执行方式：

```
# 执行 Docker 安装
./run_install_docker_local.sh
```
注意事项：

- 脚本会自动检查 install_docker.sh 文件是否存在
- 如果需要 root 权限会自动请求
- 安装完成后会询问是否安装 Portainer 容器管理界面
- Portainer 访问地址： http://服务器IP:9000

### 4. 软件安装脚本

脚本位置： run_install_software.sh

功能： 使用 Docker Compose 安装各种开发软件

执行方式：

```
# 执行软件安装
sudo ./run_install_software.sh
```

支持的软件：

- nacos - 服务注册与发现
- mysql - 数据库
- phpmyadmin - MySQL 管理界面
- redis - 缓存数据库
- redis-admin - Redis 管理界面
- rabbitmq - 消息队列
- elasticsearch - 搜索引擎
- logstash - 日志处理
- kibana - 日志分析界面
- xxl-job-admin - 任务调度
- prometheus - 监控系统
- grafana - 监控面板
- ollama - AI 模型服务
- pgvector - 向量数据库
- pgvector-admin - 向量数据库管理界面
  注意事项：

- 需要 root 权限执行
- 需要先安装 Docker 和 docker-compose
- 脚本会检查磁盘空间并显示预计占用
- 支持选择原始配置或阿里云镜像配置
- 可以多选软件进行批量安装

### 5. 常见问题

#### 5.1 权限问题

如果遇到权限拒绝错误：

```
# 确保脚本有执行权限
ls -la *.sh
# 如果没有 x 权限，重新添加
chmod +x script_name.sh
```

#### 5.2 环境变量生效

JDK 安装后，环境变量在当前会话中已生效，新开终端需要：

```
# 重新加载配置
source /etc/profile
# 或者重新登录
```

#### 5.3 Docker 相关

确保 Docker 服务正在运行：

```
# 检查 Docker 状态
sudo systemctl status docker
# 启动 Docker 服务
sudo systemctl start docker
```

### 6. 执行顺序建议

1. 首先安装 JDK （如果需要）：

   ```
   sudo ./environment/jdk/install-java.sh -v 8
   ```

2. 然后安装 Docker ：

   ```
   ./run_install_docker_local.sh
   ```

3. 然后安装 Docker ：

   ```
   ./install-maven.sh
   ```

4. 最后安装开发软件 ：

   ```
   sudo ./run_install_software.sh
   ```
   按照以上步骤，您就可以成功执行所有脚本并搭建完整的开发环境。

### 7. 安装演示

#### 7.1 docker 安装

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-install-04.png" width="650px">
</div>

```java
[root@xiaofuge ~]# ls
run_install_docker_local.sh
[root@xiaofuge ~]# chmod +x run_install_docker_local.sh 
[root@xiaofuge ~]# ./run_install_docker_local.sh 
```

- 执行完 `./run_install_docker_local.sh ` 全程会自动化安装，如果已经过 docker 会提示是否卸载。使用起来非常方便。

#### 7.2 软件安装

```java
[root@xiaofuge dev-ops]# ls
install_docker.sh  README.md  run_install_docker_local.sh  run_install_docker.sh  run_install_software.sh  software
[root@xiaofuge dev-ops]# chmod +x run_install_software.sh 
[root@xiaofuge dev-ops]# ls
install_docker.sh  README.md  run_install_docker_local.sh  run_install_docker.sh  run_install_software.sh  software
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-install-07.png" width="550px">
</div>

- 安装时候可以选择需要安装的软件，重复安装也会提示卸载。

