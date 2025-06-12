---
title: Docker 环境配置（一键安装）
lock: need
---

# Docker 环境配置（一键安装）

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

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

购买地址：[https://618.gaga.plus](https://618.gaga.plus)
购买地址：[https://618.gaga.plus](https://618.gaga.plus)
购买地址：[https://618.gaga.plus](https://618.gaga.plus)

**我适合买哪个服务器？**

- 2c2g 1年，28￥，可部署一套 docker、mysql、redis、SpringBoot 单体项目，用于替代本地电脑的环境部署。
- 2c4g 1年（非常推荐3年），109￥，可部署一套 docker、mysql、redis、rabbitmq、xxl-job、SpringBoot 分布式微服务项目。

注意：购买选择系统时，推荐系统镜像，centos 7.9

>如果自己账号不是新人身份，可以自己注册个新账号，用家里人JD扫码认证一下即可。

🎁 礼物赠送，购买2c4g 3年的，赠送Joy公仔，邮寄到家！购买后，联系小傅哥（微信：fustack）

## 二、一键部署脚本

小傅哥，这里为你准备一键安装 Docker 环境的脚本文件，你可以非常省心的完成 Docker 部署。使用方式如下。

### 1. 脚本文件

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-install-02.png" width="650px">
</div>

地址：[https://github.com/fuzhengwei/xfg-dev-tech-docker-install](https://github.com/fuzhengwei/xfg-dev-tech-docker-install)

### 2. 脚本说明

#### 2.1 install_docker_v1.2.sh

```java
#!/bin/bash

# 安装Docker的Shell脚本
# 作者：xiaofuge
# 版本：1.0
# 创建日期：$(date +"%Y-%m-%d")

# 设置颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
NC='\033[0m' # No Color

# 输出带颜色的信息函数
info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

error() {
    echo -e "${RED}[ERROR]${NC} $1"
    exit 1
}

# 检查是否以root用户运行
if [ "$(id -u)" -ne 0 ]; then
    warning "此脚本需要root权限运行，将尝试使用sudo"
    # 如果不是root用户，则使用sudo重新运行此脚本
    exec sudo "$0" "$@"
    exit $?
fi

info "docker 环境安装脚本 By xiaofuge，建议使用 https://618.gaga.plus 优惠购买服务器，安装 centos 7.9 系统。"

# 显示系统信息
info "开始安装 Docker 环境..."
info "检查系统信息..."
echo "内核版本: $(uname -r)"
echo "操作系统: $(cat /etc/os-release | grep PRETTY_NAME | cut -d '"' -f 2)"

# 检查是否已安装Docker
if command -v docker &> /dev/null; then
    INSTALLED_DOCKER_VERSION=$(docker --version | cut -d ' ' -f3 | cut -d ',' -f1)
    warning "检测到系统已安装Docker，版本为: $INSTALLED_DOCKER_VERSION"
    
    # 询问用户是否卸载已安装的Docker
    read -p "是否卸载已安装的Docker并安装新版本？(y/n): " UNINSTALL_DOCKER
    
    if [[ "$UNINSTALL_DOCKER" =~ ^[Yy]$ ]]; then
        info "开始卸载已安装的Docker..."
        systemctl stop docker &> /dev/null
        yum remove -y docker-ce docker-ce-cli containerd.io docker docker-client docker-client-latest docker-common docker-latest docker-latest-logrotate docker-logrotate docker-engine &> /dev/null
        rm -rf /var/lib/docker
        info "Docker卸载完成"
    else
        info "用户选择保留已安装的Docker，退出安装程序"
        exit 0
    fi
fi

# 更新系统包
info "更新系统包..."
yum update -y || error "系统更新失败"

# 安装依赖包
info "安装Docker依赖包..."
yum install -y yum-utils device-mapper-persistent-data lvm2 || error "依赖包安装失败"

# 添加Docker仓库
info "添加Docker仓库..."
yum-config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo || error "添加Docker仓库失败"

# 安装Docker
info "安装Docker CE 25.0.5..."
yum install -y docker-ce-25.0.5 docker-ce-cli-25.0.5 containerd.io || error "Docker安装失败"

# 安装Docker Compose
info "安装Docker Compose v2.24.1..."
curl -L https://gitee.com/fustack/docker-compose/releases/download/v2.24.1/docker-compose-linux-x86_64 -o /usr/local/bin/docker-compose || error "Docker Compose下载失败"
chmod +x /usr/local/bin/docker-compose || error "无法设置Docker Compose可执行权限"

# 启动Docker服务
info "启动Docker服务..."
systemctl start docker || error "Docker服务启动失败"

# 设置Docker开机自启
info "设置Docker开机自启..."
systemctl enable docker || error "设置Docker开机自启失败"

# 重启Docker服务
info "重启Docker服务..."
systemctl restart docker || error "Docker服务重启失败"

# 配置Docker镜像加速
info "配置Docker镜像加速..."
mkdir -p /etc/docker
cat > /etc/docker/daemon.json << EOF
{
  "registry-mirrors": [
    "https://docker.1ms.run",
    "https://docker.1panel.live",
    "https://docker.ketches.cn"
  ]
}
EOF

# 再次重启Docker服务以应用镜像加速配置
info "重启Docker服务以应用镜像加速配置..."
systemctl restart docker || error "应用镜像加速配置后Docker重启失败"

# 验证Docker安装
info "验证Docker安装..."
DOCKER_VERSION=$(docker --version)
echo "Docker版本: $DOCKER_VERSION"
DOCKER_COMPOSE_VERSION=$(docker-compose --version)
echo "Docker Compose版本: $DOCKER_COMPOSE_VERSION"

info "Docker环境安装完成！"
info "镜像加速已配置为："
echo "  - https://docker.1ms.run"
echo "  - https://docker.1panel.live"
echo "  - https://docker.ketches.cn"

info "您的Docker已经安装完毕，版本为：$DOCKER_VERSION"

info "提示，如果镜像不可用，可以进入链接，按照说明，重新设置镜像；https://status.1panel.top/status/docker"
```

- 本脚本，就是这套脚本里的脚本：[https://bugstack.cn/md/road-map/docker.html](https://bugstack.cn/md/road-map/docker.html) 文档里有详细的介绍。
- 通过教程里的执行步骤，编写了自动执行脚本 shell 文件。这份脚本我放到了网络上，会被 run_install_docker 下载执行。后续有更新也更加方便。

#### 2.2 run_install_docker

```java
#!/bin/bash

# 设置颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
NC='\033[0m' # No Color

# 输出带颜色的信息函数
info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

error() {
    echo -e "${RED}[ERROR]${NC} $1"
    exit 1
}

# 定义下载URL和本地文件名
DOCKER_SCRIPT_URL="https://gitee.com/fustack/docker-install/releases/download/v1.2/install_docker_v1.2.sh"
LOCAL_SCRIPT_NAME="install_docker_v1.2.sh"

info "开始下载Docker安装脚本..."

# 下载Docker安装脚本
if curl -fsSL "$DOCKER_SCRIPT_URL" -o "$LOCAL_SCRIPT_NAME"; then
    info "下载完成: $LOCAL_SCRIPT_NAME"
else
    error "下载失败，请检查网络连接或URL是否有效"
fi

# 设置可执行权限
info "设置可执行权限..."
chmod +x "$LOCAL_SCRIPT_NAME"

# 执行安装脚本
info "开始执行Docker安装脚本..."
info "注意：安装过程可能需要root权限，如果需要会自动请求"
echo "-----------------------------------------------------------"
./$LOCAL_SCRIPT_NAME

# 检查安装脚本的退出状态
if [ $? -eq 0 ]; then
    info "Docker安装脚本执行完成"
    
    # 询问用户是否安装Portainer
    read -p "是否安装Portainer容器管理界面？(y/n): " INSTALL_PORTAINER
    
    if [[ "$INSTALL_PORTAINER" =~ ^[Yy]$ ]]; then
        info "开始安装Portainer..."
        docker run -d --restart=always --name portainer -p 9000:9000 -v /var/run/docker.sock:/var/run/docker.sock portainer/portainer
        
        if [ $? -eq 0 ]; then
            info "Portainer安装成功！"
            warning "重要提示：请确保您的云服务器已开放9000端口！"
            echo "-----------------------------------------------------------"
            echo "Portainer访问方式："
            echo "1. 通过公网访问：http://您的服务器公网IP:9000"
            echo "2. 首次访问需要设置管理员账号和密码"
            echo "3. 登录后即可通过Web界面管理Docker容器"
            echo "-----------------------------------------------------------"
            info "您可以使用Portainer来方便地管理Docker容器、镜像、网络和卷等资源"
        else
            warning "Portainer安装失败，请手动安装或检查Docker状态"
        fi
    else
        info "用户选择不安装Portainer"
    fi
else
    error "Docker安装脚本执行失败，请查看上面的错误信息"
fi
```

- 这是最终执行安装的脚本，安装完成 docker 后，还会提示是否安装 docker 管理工具 portainer。

### 3. 上传脚本

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-install-03.png" width="650px">
</div>

- ssh 工具，推荐 termius。[https://bugstack.cn/md/road-map/tool.html](https://bugstack.cn/md/road-map/tool.html) 免费的就够用，带有 sftp 工具。
- 左侧是云服务器空间 root 下，右侧是本地环境。

### 4. 执行安装

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-install-04.png" width="650px">
</div>

```java
[root@xiaofuge ~]# ls
run_install_docker.sh
[root@xiaofuge ~]# chmod +x run_install_docker.sh 
[root@xiaofuge ~]# ./run_install_docker.sh 
```

- 执行完 `./run_install_docker.sh ` 全程会自动化安装，如果已经过 docker 会提示是否卸载。使用起来非常方便。

