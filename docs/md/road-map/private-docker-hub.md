---
title: Private DockerHub
lock: need
---

# 构建 DockerHub 私有镜像仓库，通过 GitHub Actions 推送镜像

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

这不稳，那不行。这鲜族人过年，要了狗命了！但凡真正进入开发快车道的伙伴，就几乎离不开 DockerHub 的使用，包括拉取 mysql、redis、kafka、nacos 等环境镜像，也包括自己的应用构建镜像。不少伙伴都哭喊，这没法用，写代码都不香啦！

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-private-dockerhub-01.gif" width="150px">
</div>

**有没有那种，风雨无阻都很稳的办法呢！**

6月份的时候小傅哥写了一篇基于 GitHub Actions 推送镜像到阿里云私有镜像仓库的教程，但最近看到阿里云要对自身的私有仓库做出调整，以后也不在为所有环境全量的提供镜像加速了。不过目前还能用，但不知道还能用多久。所以，未雨绸缪啊！万一又用不了了，怪麻烦的。

嘿嘿，不过也不用太担心。因为我们还有方案！那就是自建私有镜像仓库，替代阿里云。在这套方案中，虽然我们的云服务器不能直接拉取 Docker Hub 仓库中镜像，但是 GitHub Actions 可以呀，不仅可以还能让 GitHub Actions 执行脚本把拉取下来的镜像推送到我们在自己云服务器上搭建的私有镜像仓库。这不美滋滋了吗！任何时候你想用就用，而且你可以只给自己用。

>接下来，小傅哥就教你怎么做这个事情。—— 学到手的全是技术！

## 一、私有镜像仓库

如果你是一个小公司，或者是一个小组织，那么 Docker Hub 私有镜像仓库是非常适合你使用的，它可以避免你的应用镜像对外，也可以固定范围的拉取可靠镜像。并且私有镜像仓库的搭建也是非常简单的，就一行代码的事。

### 1. 安装脚本

```java
# 命令执行 docker-compose -f docker-compose.yml up -d
version: '3.8'
services:
  # docker run -dit --restart=always --name=docker-registry -p 5000:5000 -v /docker/var/lib/registry:/var/lib/registry library/registry:latest
  registry:
    image: library/registry:latest
    container_name: docker-registry
    restart: always
    ports:
      - "5000:5000"
    volumes:
      - /docker/var/lib/registry:/var/lib/registry
```

**安装方式** - 这个过程还是需要拉取一次镜像的，可以找一些镜像仓库或者让其他伙伴提供下它的私有镜像仓库地址。

- 方式1；`docker-compose -f docker-compose.yml up -d`
- 方式2；`docker run -dit --restart=always --name=docker-registry -p 5000:5000 -v /docker/var/lib/registry:/var/lib/registry library/registry:latest`

### 2. 镜像配置

```java
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
    "registry-mirrors": [
        "https://dc.j8.work"
    ],
    "insecure-registries":["116.198.201.187:5000"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```

- IP 为你的云服务器 IP 地址，5000 端口为默认端口，记得云服务器要防火墙开放。
- 如上，`116.198.201.187:5000` 替换为你的地址。

### 3. 常用命令

#### 3.1 查看私有镜像仓库中存在的镜像文件

```java
[root@lavm-cnqkgk85q4 ~]# curl 116.198.201.187:5000/v2/_catalog
{"repositories":["kafka","kafka-eagle","mysql","phpmyadmin","redis","registry"]}
```

#### 3.2 查看指定的镜像版本

```java
[root@lavm-cnqkgk85q4 ~]# curl 116.198.201.187:5000/v2/redis/tags/list
{"name":"redis","tags":["6.2","latest"]}
```

#### 3.3 拉取镜像

```java
docker pull 116.198.201.187:5000/redis
docker pull 116.198.201.187:5000/redis:6.2
docker pull 116.198.201.187:5000/redis:latest
```

- 从私有的镜像仓库拉取镜像文件。

#### 3.4 推送镜像

```java
docker push 116.198.201.187:5000/mysql:latest
```

- 推送镜像，这个命令很有用，后面在 GitHub Actions 中会使用到。

## 二、GitHub Actions 脚本

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-private-dockerhub-02.png" width="450px">
</div>

- 地址：[https://github.com/fuzhengwei/docker-image-pusher](https://github.com/fuzhengwei/docker-image-pusher) - 你可以 fork 使用。
- 注意：需要配置 Actions，下文中会说明。

### 1. 简单示意

```java
name: Pull and Push MySQL Docker Image

on:
  push:
    branches:
      - main

jobs:
  deploy:
    runs-on: ubuntu-latest

    steps:
    - name: Checkout repository
      uses: actions/checkout@v2

    - name: Log in to Docker Hub
      run: echo "${{ secrets.DOCKER_HUB_PASSWORD }}" | docker login -u "${{ secrets.DOCKER_HUB_USERNAME }}" --password-stdin

    - name: Configure Docker to use HTTP for private registry
      run: |
        echo '{"insecure-registries":["116.198.201.187:5000"]}' | sudo tee /etc/docker/daemon.json
        sudo systemctl restart docker

    - name: Pull MySQL image from Docker Hub
      run: docker pull mysql:latest

    - name: Tag MySQL image for private registry
      run: docker tag mysql:latest 116.198.201.187:5000/mysql:latest

    - name: Push MySQL image to private registry
      run: docker push 116.198.201.187:5000/mysql:latest
```

- 这是一段 GitHub Actions 脚本，用于配置到 GitHub 仓库使用。下文会有让你 fork 工程和配置使用的方式。
- 此脚本的作用在于从GitHub 仓库拉取镜像推送到我们自建的私有镜像仓库中。—— 受带宽和网络影响，推送过程会稍微慢一些。
- 那么，有了这么一个可以配置镜像 `docker pull mysql:latest` 拉取和推送的操作，我们就也可以配置一个 images.txt 文件放到工程下，只要修改这个里的文件，就自动完成推送。岂不是美滋滋！

### 2. 动态脚本

在之前小傅哥看到一个 `@技术爬爬虾` 的大佬分享了个 GitHub Actions 推送镜像到阿里云私有仓库，小傅哥修改为推送到自己的仓库了。一些相关配置，也可以从 `@技术爬爬虾` 这里学习下。[https://github.com/tech-shrimp/docker_image_pusher](https://github.com/tech-shrimp/docker_image_pusher)

```java
name: Docker

on:
  workflow_dispatch:
  push:
    branches: [ main ]

env:
  PRIVATE_REGISTRY: "${{ secrets.PRIVATE_REGISTRY }}"

jobs:

  build:
    name: Pull
    runs-on: ubuntu-latest
    steps:
      - name: Before freeing up disk space
        run: |
          echo "Before freeing up disk space"
          echo "=============================================================================="
          df -hT
          echo "=============================================================================="

      # 增加可用磁盘空间
      - name: Maximize build space
        uses: easimon/maximize-build-space@master
        with:
          root-reserve-mb: 2048
          swap-size-mb: 128
          remove-dotnet: 'true'
          remove-haskell: 'true'
          # 如果空间还是不够用，可以把以下开启，清理出更多空间
          # remove-android: 'true'
          # remove-codeql: 'true'
          build-mount-path: '/var/lib/docker/'

      - name: Restart docker
        run: sudo service docker restart

      - name: Free up disk space complete
        run: |
          echo "Free up disk space complete"
          echo "=============================================================================="
          df -hT
          echo "=============================================================================="

      - name: Checkout Code
        uses: actions/checkout@v4

      - name: Docker Setup Buildx
        uses: docker/setup-buildx-action@v3

      - name: Configure Docker to use HTTP for private registry
        run: |
          echo "{\"insecure-registries\":[\"$PRIVATE_REGISTRY\"]}" | sudo tee /etc/docker/daemon.json
          sudo systemctl restart docker

      - name: Build and push image to private registry
        run: |
          # 数据预处理,判断镜像是否重名
          declare -A duplicate_images
          declare -A temp_map
          while IFS= read -r line || [ -n "$line" ]; do
              # 忽略空行与注释
              [[ -z "$line" ]] && continue
              if echo "$line" | grep -q '^\s*#'; then
                  continue
              fi
          
              # 获取镜像的完整名称，例如kasmweb/nginx:1.25.3（命名空间/镜像名:版本号）
              image=$(echo "$line" | awk '{print $NF}')
              # 将@sha256:等字符删除
              image="${image%%@*}"
              echo "image $image"
              # 获取镜像名:版本号  例如nginx:1.25.3
              image_name_tag=$(echo "$image" | awk -F'/' '{print $NF}')
              echo "image_name_tag $image_name_tag"
              # 获取命名空间 例如kasmweb,  这里有种特殊情况 docker.io/nginx，把docker.io当成命名空间，也OK
              name_space=$(echo "$image" | awk -F'/' '{if (NF==3) print $2; else if (NF==2) print $1; else print ""}')
              echo "name_space: $name_space"
              # 这里不要是空值影响判断
              name_space="${name_space}_"
              # 获取镜像名例如nginx
              image_name=$(echo "$image_name_tag" | awk -F':' '{print $1}')
              echo "image_name: $image_name"
          
              # 如果镜像存在于数组中，则添加temp_map
              if [[ -n "${temp_map[$image_name]}" ]]; then
                   # 如果temp_map已经存在镜像名，判断是不是同一命名空间
                   if [[ "${temp_map[$image_name]}" != $name_space  ]]; then
                      echo "duplicate image name: $image_name"
                      duplicate_images[$image_name]="true"
                   fi
              else
                  # 存镜像的命名空间
                  temp_map[$image_name]=$name_space
              fi       
          done < images.txt
          
          while IFS= read -r line || [ -n "$line" ]; do
          
              # 忽略空行与注释
              [[ -z "$line" ]] && continue
              if echo "$line" | grep -q '^\s*#'; then
                  continue
              fi
            
              echo "docker pull $line"
              docker pull $line
              platform=$(echo "$line" | awk -F'--platform[ =]' '{if (NF>1) print $2}' | awk '{print $1}')
              echo "platform is $platform"
              # 如果存在架构信息 将架构信息拼到镜像名称前面
              if [ -z "$platform" ]; then
                  platform_prefix=""
              else
                  platform_prefix="${platform//\//_}_"
              fi
              echo "platform_prefix is $platform_prefix"
              # 获取镜像的完整名称，例如kasmweb/nginx:1.25.3（命名空间/镜像名:版本号）
              image=$(echo "$line" | awk '{print $NF}')
  
              # 获取 镜像名:版本号  例如nginx:1.25.3
              image_name_tag=$(echo "$image" | awk -F'/' '{print $NF}')
              # 获取命名空间 例如kasmweb  这里有种特殊情况 docker.io/nginx，把docker.io当成命名空间，也OK
              name_space=$(echo "$image" | awk -F'/' '{if (NF==3) print $2; else if (NF==2) print $1; else print ""}')
              # 获取镜像名例  例如nginx
              image_name=$(echo "$image_name_tag" | awk -F':' '{print $1}')
            
              name_space_prefix=""
              # 如果镜像名重名
              if [[ -n "${duplicate_images[$image_name]}" ]]; then
                 #如果命名空间非空，将命名空间加到前缀
                 if [[ -n "${name_space}" ]]; then
                    name_space_prefix="${name_space}_"
                 fi
              fi
            
              # 将@sha256:等字符删除
              image_name_tag="${image_name_tag%%@*}"
              new_image="$PRIVATE_REGISTRY/$platform_prefix$name_space_prefix$image_name_tag"
              latest_image="$PRIVATE_REGISTRY/$platform_prefix$name_space_prefix$image_name:latest"
            
              echo "docker tag $image $new_image"
              docker tag $image $new_image
              echo "docker push $new_image"
              docker push $new_image
  
              echo "docker tag $image $latest_image"
              docker tag $image $latest_image
              echo "docker push $latest_image"
              docker push $latest_image
            
              echo "开始清理磁盘空间"
              echo "=============================================================================="
            
              df -hT
              echo "=============================================================================="
              docker rmi $image
              docker rmi $new_image
              echo "磁盘空间清理完毕"
              echo "=============================================================================="
              df -hT
              echo "=============================================================================="     
          
          done < images.txt
```

- 此脚本的用途在于把从 Docker Hub 拉取的镜像推送到自己的私有仓库中。
- 这里有一个 PRIVATE_REGISTRY 就是你的私有仓库地址 `116.198.201.187:5000`，你需要配置到 GitHub Actions 中。

## 三、安装使用

### 1. 工程 Fork

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-private-dockerhub-04.png" width="950px">
</div>

### 2. 配置 Actions secret

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-private-dockerhub-03.png" width="750px">
</div>

- key：PRIVATE_REGISTRY
- value：你的镜像仓库地址

### 3. images.txt

在 images.txt 提交你需要 push 的镜像，如；

```java
phpmyadmin:5.2.1
redis:6.2
```

### 4. 观察执行

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-private-dockerhub-05.png" width="750px">
</div>

- 推送成功后，你可以在 Actions 中查看镜像推送过程。

### 5. 镜像使用

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-private-dockerhub-06.png" width="750px">
</div>

```java
docker pull 116.198.201.187:5000/redis
docker tag 116.198.201.187:5000/redis my-redis
```

- 在上文中，有使用私有镜像仓库的脚本，现在你可以使用了。
- 拉取的镜像会带有前缀，116.198.201.187:5000 这个时候你可以重新 tag 下，这样就和你的 docker compose 符合了。
