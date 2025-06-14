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
- 2c8g 1年，328￥，适合部署星球大部分项目，可以完成多个微服务项目部署。

注意📢：购买选择系统时，推荐系统镜像，**centos 7.9**

>如果自己账号不是新人身份，可以自己注册个新账号，用家里人JD扫码认证一下即可。

🎁 礼物赠送，购买2c4g 3年的，赠送Joy公仔，邮寄到家！购买后，联系小傅哥（微信：fustack）

## 二、一键部署脚本

小傅哥，这里为你准备一键安装 Docker 环境的脚本文件，你可以非常省心的完成 Docker 部署。使用方式如下。

### 1. 脚本文件

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-install-02.png" width="650px">
</div>

- **地址**：<https://github.com/fuzhengwei/xfg-dev-tech-docker-install>
- **地址**：<https://gitcode.com/Yao__Shun__Yu/xfg-dev-tech-docker-install>


### 2. 脚本说明

#### 2.1 install_docker.sh

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

#### 2.2 run_install_docker_local

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

# 定义本地脚本文件名
LOCAL_SCRIPT_NAME="install_docker.sh"

info "使用本地Docker安装脚本: $LOCAL_SCRIPT_NAME"

# 检查本地脚本是否存在
if [ ! -f "$LOCAL_SCRIPT_NAME" ]; then
    error "本地脚本文件 $LOCAL_SCRIPT_NAME 不存在"
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

- run_install_docker、run_install_docker_local，推荐使用 run_install_docker_local
- 这是最终执行安装的脚本，安装完成 docker 后，还会提示是否安装 docker 管理工具 portainer。

#### 2.3 run_install_software

```java
#!/bin/bash

# 设置颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
BLUE='\033[0;34m'
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

header() {
    echo -e "${BLUE}[HEADER]${NC} $1"
}

# 检查是否以root用户运行
if [ "$(id -u)" -ne 0 ]; then
    warning "此脚本需要root权限运行，将尝试使用sudo"
    # 如果不是root用户，则使用sudo重新运行此脚本
    exec sudo "$0" "$@"
    exit $?
fi

# 检查Docker是否已安装
if ! command -v docker &> /dev/null; then
    error "Docker未安装，请先运行install_docker.sh安装Docker"
fi

# 检查docker-compose是否已安装
if ! command -v docker-compose &> /dev/null; then
    info "正在安装docker-compose..."
    curl -L "https://gitee.com/fustack/docker-compose/releases/download/v2.24.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    chmod +x /usr/local/bin/docker-compose
    if ! command -v docker-compose &> /dev/null; then
        error "docker-compose安装失败，请手动安装"
    else
        info "docker-compose安装成功"
    fi
fi

# 检查software目录是否存在
if [ ! -d "$(pwd)/software" ]; then
    error "software目录不存在，请从 https://github.com/fuzhengwei/xfg-dev-tech-docker-install 下载项目，并上传到云服务器 / 根目录下"
fi

# 检查docker-compose-software.yml文件是否存在
if [ ! -f "$(pwd)/software/docker-compose-software.yml" ]; then
    error "docker-compose-software.yml文件不存在，请检查software目录是否完整"
fi

# 检查docker-compose-software-aliyun.yml文件是否存在
if [ ! -f "$(pwd)/software/docker-compose-software-aliyun.yml" ]; then
    error "docker-compose-software-aliyun.yml文件不存在，请检查software目录是否完整"
fi

# 获取当前磁盘空间信息
disk_info=$(df -h / | tail -1)
disk_total=$(echo $disk_info | awk '{print $2}')
disk_used=$(echo $disk_info | awk '{print $3}')
disk_avail=$(echo $disk_info | awk '{print $4}')
disk_used_percent=$(echo $disk_info | awk '{print $5}')

info "当前磁盘空间信息：总空间 ${disk_total}，已使用 ${disk_used}，可用 ${disk_avail}，使用率 ${disk_used_percent}"

# 定义软件列表及其大小估计（单位：MB）
declare -A software_sizes=(
    ["nacos"]=500
    ["mysql"]=600
    ["phpmyadmin"]=100
    ["redis"]=50
    ["redis-admin"]=50
    ["rabbitmq"]=300
    ["elasticsearch"]=500
    ["logstash"]=300
    ["kibana"]=200
    ["xxl-job-admin"]=150
    ["prometheus"]=100
    ["grafana"]=100
)

# 定义软件的账号密码信息
declare -A software_credentials=(
    ["nacos"]="账号：nacos 密码：nacos 访问地址：http://服务器IP:8848/nacos"
    ["mysql"]="账号：root 密码：123456 端口：13306"
    ["phpmyadmin"]="访问地址：http://服务器IP:8899 (连接到MySQL)"
    ["redis"]="端口：16379"
    ["redis-admin"]="账号：admin 密码：admin 访问地址：http://服务器IP:8081"
    ["rabbitmq"]="账号：admin 密码：admin 访问地址：http://服务器IP:15672"
    ["elasticsearch"]="访问地址：http://服务器IP:9200"
    ["logstash"]="端口：4560,50000,9600"
    ["kibana"]="访问地址：http://服务器IP:5601"
    ["xxl-job-admin"]="账号：admin 密码：123456 访问地址：http://服务器IP:9090/xxl-job-admin"
    ["prometheus"]="访问地址：http://服务器IP:9090"
    ["grafana"]="访问地址：http://服务器IP:4000"
)

# 检查已安装的软件
check_installed() {
    local software=$1
    if docker ps -a --format '{{.Names}}' | grep -q "^${software}$"; then
        return 0 # 已安装
    else
        return 1 # 未安装
    fi
}

# 选择使用哪个配置文件
echo "-----------------------------------------------------------"
header "选择配置文件："
echo "-----------------------------------------------------------"
echo "1. 使用原始配置文件 (推荐，但可能需要从Docker Hub拉取镜像)"
echo "2. 使用阿里云镜像配置文件 (国内网络环境推荐)"
echo "-----------------------------------------------------------"
read -p "请选择配置文件 [1/2] (默认: 1): " config_choice
config_choice=${config_choice:-1}

if [ "$config_choice" = "1" ]; then
    compose_file="$(pwd)/software/docker-compose-software.yml"
    info "已选择使用原始配置文件"
else
    compose_file="$(pwd)/software/docker-compose-software-aliyun.yml"
    info "已选择使用阿里云镜像配置文件"
fi

# 列出可安装的软件
echo "-----------------------------------------------------------"
header "可安装的软件列表："
echo "-----------------------------------------------------------"

# 创建软件选择数组
software_list=("nacos" "mysql" "phpmyadmin" "redis" "redis-admin" "rabbitmq" "elasticsearch" "logstash" "kibana")

# 如果选择了原始配置文件，添加只在原始配置中存在的软件
if [ "$config_choice" = "1" ]; then
    software_list+=("xxl-job-admin" "prometheus" "grafana")
fi
declare -A software_selected

# 显示软件列表及其状态
for ((i=0; i<${#software_list[@]}; i++)); do
    software=${software_list[$i]}
    size=${software_sizes[$software]}
    
    if check_installed "$software"; then
        echo "$((i+1)). $software [已安装] (预计占用空间: ${size}MB)"
    else
        echo "$((i+1)). $software (预计占用空间: ${size}MB)"
    fi
done

echo "-----------------------------------------------------------"
echo "请选择要安装的软件（多选，用空格分隔，如：1 3 5）："
read -a selections

# 处理用户选择
total_size=0
for selection in "${selections[@]}"; do
    # 检查输入是否为数字
    if ! [[ "$selection" =~ ^[0-9]+$ ]]; then
        warning "无效的选择: $selection，已跳过"
        continue
    fi
    
    # 检查选择是否在范围内
    if [ "$selection" -lt 1 ] || [ "$selection" -gt "${#software_list[@]}" ]; then
        warning "选择超出范围: $selection，已跳过"
        continue
    fi
    
    index=$((selection-1))
    software=${software_list[$index]}
    software_selected[$software]=1
    size=${software_sizes[$software]}
    total_size=$((total_size + size))
done

if [ ${#software_selected[@]} -eq 0 ]; then
    error "未选择任何软件，安装已取消"
fi

# 显示选择的软件及总空间
echo "-----------------------------------------------------------"
header "您选择了以下软件："
for software in "${!software_selected[@]}"; do
    echo "- $software (预计占用空间: ${software_sizes[$software]}MB)"
done
echo "总计预计占用空间: ${total_size}MB"
echo "-----------------------------------------------------------"

# MySQL初始化提示
if [[ -n "${software_selected[mysql]}" ]]; then
    echo "-----------------------------------------------------------"
    header "MySQL初始化提示："
    echo "-----------------------------------------------------------"
    info "您选择了安装MySQL，安装完成后可以使用phpmyadmin进行管理"
    info "如果您希望在初始化时创建数据库和表，可以将SQL脚本放在以下目录："
    echo "  $(pwd)/software/mysql/sql/"
    info "目前该目录已包含以下SQL文件："
    ls -1 "$(pwd)/software/mysql/sql/" | grep ".sql" | while read -r sql_file; do
        echo "  - $sql_file"
    done
    info "您可以添加自己的SQL文件到该目录，它们将在MySQL初始化时自动执行"
    echo "-----------------------------------------------------------"
fi

# Prometheus配置提示
if [[ -n "${software_selected[prometheus]}" ]]; then
    echo "-----------------------------------------------------------"
    header "Prometheus配置提示："
    echo "-----------------------------------------------------------"
    info "您选择了安装Prometheus，请确保："
    info "1. 在安装前配置您的应用监控设置："
    echo "  $(pwd)/software/prometheus/prometheus.yml"
    info "2. 确保被监控的应用端口已在防火墙中开放"
    info "3. 当前配置文件中的目标应用为：'system-app:8091'"
    info "4. 如需监控其他应用，请修改配置文件中的targets部分"
    echo "-----------------------------------------------------------"
fi

# 确认安装
read -p "确认安装以上软件？(y/n): " confirm
if [[ ! "$confirm" =~ ^[Yy]$ ]]; then
    info "安装已取消"
    exit 0
fi

# 创建临时的docker-compose文件
temp_compose_file="$(pwd)/software/docker-compose-temp.yml"
cp "$compose_file" "$temp_compose_file"

# 处理已安装的软件
for software in "${!software_selected[@]}"; do
    if check_installed "$software"; then
        read -p "$software 已安装，是否重新安装？(y/n): " reinstall
        if [[ "$reinstall" =~ ^[Yy]$ ]]; then
            info "将重新安装 $software"
            docker rm -f "$software" &> /dev/null
        else
            info "跳过安装 $software"
            unset software_selected[$software]
        fi
    fi
done

# 如果没有软件需要安装，则退出
if [ ${#software_selected[@]} -eq 0 ]; then
    info "没有需要安装的软件，安装已取消"
    rm -f "$temp_compose_file"
    exit 0
fi

# 修改临时docker-compose文件，只保留选中的服务
sed -i '/^services:/,$d' "$temp_compose_file"
echo "services:" >> "$temp_compose_file"

# 从原始文件中提取选中的服务配置
original_file="$compose_file"
for software in "${!software_selected[@]}"; do
    # 提取服务配置块
    awk -v service="$software:" 'BEGIN {flag=0; found=0;}
    $0 ~ "^  "service {flag=1; found=1;}
    flag && /^  [a-zA-Z]/ && $0 !~ "^  "service {flag=0;}
    flag {print;}
    END {exit !found;}' "$original_file" >> "$temp_compose_file"
    
    # 如果是依赖于其他服务的，确保依赖的服务也被安装
    if grep -q "depends_on:" <<< "$(awk -v service="$software:" 'BEGIN {flag=0;}
    $0 ~ "^  "service {flag=1;}
    flag && /^  [a-zA-Z]/ && $0 !~ "^  "service {flag=0;}
    flag {print;}' "$original_file")"; then
        # 提取依赖服务
        depends=$(awk -v service="$software:" 'BEGIN {flag=0;}
        $0 ~ "^  "service {flag=1;}
        flag && /depends_on:/ {flag=2;}
        flag==2 && /^      [a-zA-Z]/ {print $1;}
        flag && /^  [a-zA-Z]/ && $0 !~ "^  "service {flag=0;}' "$original_file")
        
        for dep in $depends; do
            if [ -z "${software_selected[$dep]}" ]; then
                warning "$software 依赖于 $dep，但 $dep 未被选中安装"
                read -p "是否同时安装 $dep？(y/n): " install_dep
                if [[ "$install_dep" =~ ^[Yy]$ ]]; then
                    info "将同时安装 $dep"
                    software_selected[$dep]=1
                    # 提取依赖服务配置块
                    awk -v service="$dep:" 'BEGIN {flag=0; found=0;}
                    $0 ~ "^  "service {flag=1; found=1;}
                    flag && /^  [a-zA-Z]/ && $0 !~ "^  "service {flag=0;}
                    flag {print;}
                    END {exit !found;}' "$original_file" >> "$temp_compose_file"
                else
                    warning "$software 可能无法正常工作，因为缺少依赖 $dep"
                fi
            fi
        done
    fi
done

# 添加网络配置
echo "" >> "$temp_compose_file"
awk '/^networks:/,0' "$original_file" >> "$temp_compose_file"

# 执行docker-compose
info "开始安装选中的软件..."
cd "$(pwd)/software"
docker-compose -f docker-compose-temp.yml up -d

# 检查安装结果
if [ $? -eq 0 ]; then
    info "软件安装完成！"
    echo "-----------------------------------------------------------"
    header "已安装的软件及访问信息："
    for software in "${!software_selected[@]}"; do
        if check_installed "$software"; then
            echo "- $software: ${software_credentials[$software]}"
            
            # MySQL安装后的提示
            if [ "$software" = "mysql" ]; then
                info "MySQL已安装成功，您可以使用phpmyadmin进行管理"
                info "初始化SQL脚本已自动执行，包括："
                ls -1 "$(pwd)/mysql/sql/" | grep ".sql" | while read -r sql_file; do
                    echo "  - $sql_file"
                done
            fi
            
            # Prometheus安装后的提示
            if [ "$software" = "prometheus" ]; then
                info "Prometheus已安装成功，请确保："
                info "1. 被监控的应用已正确配置并开放端口"
                info "2. 如需修改监控配置，请编辑：$(pwd)/prometheus/prometheus.yml"
                info "3. 修改配置后需要重启Prometheus：docker restart prometheus"
            fi
        else
            warning "$software 安装失败"
            if [ "$config_choice" = "1" ]; then
                warning "可能是因为网络问题无法拉取镜像，建议尝试使用阿里云镜像配置文件重新安装"
                warning "重新运行脚本并选择选项2使用阿里云镜像配置文件"
            fi
        fi
    done
    echo "-----------------------------------------------------------"
    info "如需修改账号密码，请编辑 $compose_file 文件"
    info "修改后，重新运行此脚本即可更新配置"
    
    # 清理临时文件
    rm -f "$temp_compose_file"
else
    error "软件安装失败，请查看上面的错误信息"
    if [ "$config_choice" = "1" ]; then
        warning "可能是因为网络问题无法拉取镜像，建议尝试使用阿里云镜像配置文件重新安装"
        warning "重新运行脚本并选择选项2使用阿里云镜像配置文件"
    fi
fi

```

- 软件安装脚本

### 3. 上传脚本

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-install-03.png" width="650px">
</div>

- ssh 工具，推荐 termius。[https://bugstack.cn/md/road-map/tool.html](https://bugstack.cn/md/road-map/tool.html) 免费的就够用，带有 sftp 工具。
- 左侧是云服务器空间 root 下，右侧是本地环境。

### 4. 执行安装

#### 4.1 docker 安装

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

#### 4.2 软件安装

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

