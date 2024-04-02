---
title: Linux
lock: need
---

# 我把云服务器，搭建成开发环境使用！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=1602599690&bvid=BV1fm421E7oT&cid=1490208913&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

在几年前刚入手 Mac 电脑的时候，有不少伙伴问过我，这电脑有什么优势吗？又不能打游戏！是呀，能想到的就是没有广告、APP安装简单、UI风格细腻。但这些和 Windows 电脑好像也没有太大的差别，各有所好罢了。而且同等配置 Mac 还要贵不少。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-linux-01.gif" width="200px">
</div>

但直到深入编程开发以后，一会搞后端、一些写前端、一会安装Docker环境。Mac 电脑是真的好用，你已经写了一天的代码了，Windows 伙伴还在适配环境。我觉得直到这会它的优势才体现出来，因为 Mac == 云服务器（Linux）！

那么本文，就是为了 Windows 伙伴所写，提供一套非常容易的，在云服务器就能搭建出来的开发环境和构建前后端项目的方案。让大家可以少一些折腾就能快速启动开发，完成项目的构建和部署操作。—— 其实你早晚也需要使用Linux环境！

本文涉及的工程；

- xfg-dev-tech-linux：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-linux](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-linux)
- 云服务器配置要求：2c2g [https://yun.xfg.plus](https://yun.xfg.plus)
- 工程提供了git、 java、maven、redis、mysql 的环境安装，可以让本地连接云服务器的配置，也可以在云服务器拉取项目进行构建操作。

>文末有加入学习方式，提供了8个Java实战项目，非常有东西可以学！

## 一、工程说明

在折腾工程中提供了开发必备的环境安装，可以通过执行脚本的方式安装和卸载 Java、Maven 以及通过 Docker Compose 安装 Redis、MySQL。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-linux-02.png" width="450px">
</div>

- 首先，确保你的 Linux 已安装了 Docker + Compose、Portainer —— 这部分内容在小傅哥的Java简明教程中以及提供。https://bugstack.cn/md/road-map/road-map.html
- java、maven 执行脚本即可，执行前可以先通过 chmod +x install-java.sh 附上执行权限（其他sh文件类似）
- docker-compose-environment.yml 是负责安装 mysql、redis 的，云服务器安装后要配置端口开放，这样本地才能访问。mysql 的密码注意要设置的复杂一些。
- 你可以把自己的项目的sql创建库表语句，放到 mysql/sql 的文件夹下。

## 二、环境安装

推荐使用 [termius](https://termius.com/) 工具连接你的云服务，这个工具自带 SFPT 可以方便的上传文件。

把 dev-ops 文件夹上传到云服务器上。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-linux-03.png" width="750px">
</div>

在你把 xfg-dev-tech-linux 克隆到本地以后，把 dev-ops 整个文件夹上传到云服务器的根目录即可。之后就可以执行下面的环境安装操作了。

常用命令；
- `rm -rf ...` 删掉文件夹。安装的文件会进入 `/user/local/java`、`/user/local/maven`，可以进入后删掉旧版的文件。
- `cat /etc/profile` 查看配置文件
- `vim /etc/profile` 进入后输入 i 后开始编辑，编辑后点击 esc 之后输入 :wq 退出。如果你有安装多次，但这里 Java、Maven 配置有多份，可以删掉。 
- `git clone https://xxx.git` 检出项目
- `git branch` 列出项目分支
- `git checkout master` 检出 master 分支

### 1. 安装 Git

```java
sudo yum install git
```

安装后通过 `git --version` 查看版本

### 2. 安装 Java JDK

```java
cd /dev-ops/java
chmod +x install-java.sh
./install-java.sh
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-linux-05.png" width="650px">
</div>

- 验证：`java -version` 此时会输出 Java 的版本。
- 提示：如果未输出 java，可以手动执行 `source /etc/profile`
- 卸载：如果不需要 Java 环境了，可以同样方式执行 `./remove-java.sh`

### 3. 安装 Maven

```java
cd /dev-ops/maven
chmod +x install-maven.sh
./install-maven.sh
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-linux-06.png" width="650px">
</div>

- 验证：`mvn -version` 此时会输出 maven 的版本。
- 提示：如果未输出 Maven，可以手动执行 `source /etc/profile`
- 卸载：如果不需要 Maven 环境了，可以同样方式执行 `./remove-maven.sh`

### 4. 安装 MySQL、Redis

```java
cd /dev-ops
docker-compose -f docker-compose-environment.yml up -d
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-linux-04.png" width="950px">
</div>

- 访问：[http://117.72.37.243:9000/#!/2/docker/containers](http://117.72.37.243:9000/#!/2/docker/containers) - 访问你的 Portainer，就可以看见安装内容了。
- 13306、16379、8081、8899，这几个端口，需要在云服务器打开。
- 实际自己使用的时候，MySQL 数据库的密码可以配置的更强一些。

## 三、项目构建

以上这些环境的安装就可以支撑我们部署项目了，这里以小傅哥最近带着大家做的大营销平台项目（前后端）进行打包构建举例。

开始执行之前进入到 /dev-ops 通过命令创建 github 文件夹 `mkdir github`，后续把项目检出到这个文件夹。

### 1. 后端构建

#### 1.1 克隆项目

```java
[root@lavm-aqhgp9nber dev-ops]# cd github/
[root@lavm-aqhgp9nber github]# ls
[root@lavm-aqhgp9nber github]# git clone https://gitcode.net/KnowledgePlanet/big-market/big-market.git
Cloning into 'big-market'...
Username for 'https://gitcode.net': Yao__Shun__Yu
Password for 'https://Yao__Shun__Yu@gitcode.net': 
remote: Enumerating objects: 424, done.
remote: Counting objects: 100% (424/424), done.
remote: Compressing objects: 100% (222/222), done.
remote: Total 1494 (delta 113), reused 283 (delta 71), pack-reused 1070
Receiving objects: 100% (1494/1494), 229.88 KiB | 0 bytes/s, done.
Resolving deltas: 100% (418/418), done.
```

- git clone 项目，需要输入你的账号密码。也可以配置 ssh 秘钥方式，就不用输入账号密码了。

#### 1.2 构建项目

```java
[root@lavm-aqhgp9nber big-market]# cd /dev-ops/
[root@lavm-aqhgp9nber dev-ops]# ls
docker-compose-app.yml  docker-compose-environment.yml  github  java  maven  mysql  redis
[root@lavm-aqhgp9nber dev-ops]# cd github/
[root@lavm-aqhgp9nber github]# ls
big-market
[root@lavm-aqhgp9nber github]# cd big-market/
[root@lavm-aqhgp9nber big-market]# ls
big-market-api  big-market-domain          big-market-trigger  docs     README.md
big-market-app  big-market-infrastructure  big-market-types    pom.xml
[root@lavm-aqhgp9nber big-market]# mvn clean install
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] big-market                                                         [pom]
[INFO] big-market-types                                                   [jar]
[INFO] big-market-domain                                                  [jar]
[INFO] big-market-api                                                     [jar]
[INFO] big-market-trigger                                                 [jar]
[INFO] big-market-infrastructure                                          [jar]
[INFO] big-market-app                                                     [jar]
[INFO] 
[INFO] -----------------------< cn.bugstack:big-market >-----------------------
[INFO] Building big-market 1.0-SNAPSHOT                                   [1/7]
[INFO] Reactor Summary for big-market 1.0-SNAPSHOT:
[INFO] 
[INFO] big-market ......................................... SUCCESS [  0.594 s]
[INFO] big-market-types ................................... SUCCESS [  4.069 s]
[INFO] big-market-domain .................................. SUCCESS [  2.759 s]
[INFO] big-market-api ..................................... SUCCESS [  0.775 s]
[INFO] big-market-trigger ................................. SUCCESS [  3.834 s]
[INFO] big-market-infrastructure .......................... SUCCESS [  2.423 s]
[INFO] big-market-app ..................................... SUCCESS [  3.730 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  18.696 s
[INFO] Finished at: 2024-03-31T13:22:04+08:00
[INFO] ------------------------------------------------------------------------
```

- 通过 `mvn clean install` 命令先构建项目。

#### 1.3 打包镜像

```java
[root@lavm-aqhgp9nber big-market]# cd big-market-app/
[root@lavm-aqhgp9nber big-market-app]# ls
build.sh  Dockerfile  pom.xml  src  target
[root@lavm-aqhgp9nber big-market-app]# chmod +x build.sh 
[root@lavm-aqhgp9nber big-market-app]# ./build.sh 
[+] Building 2.1s (8/8) FINISHED                                               docker:default
 => [internal] load build definition from Dockerfile                                     0.0s
 => => transferring dockerfile: 372B                                                     0.0s
 => [internal] load metadata for docker.io/library/openjdk:8-jre-slim                    0.0s
 => [internal] load .dockerignore                                                        0.0s
 => => transferring context: 2B                                                          0.0s
 => [1/3] FROM docker.io/library/openjdk:8-jre-slim                                      0.0s
 => [internal] load build context                                                        0.5s
 => => transferring context: 64.71MB                                                     0.5s
 => CACHED [2/3] RUN ln -snf /usr/share/zoneinfo/PRC /etc/localtime && echo PRC > /etc/  0.0s
 => [3/3] ADD target/big-market-app.jar /big-market-app.jar                              1.2s
 => exporting to image                                                                   0.3s
 => => exporting layers                                                                  0.3s
 => => writing image sha256:aee0181546cccf3e6677aa8de6fae13d87c4cf812f0871a994c2ba1eaca  0.0s
 => => naming to docker.io/fuzhengwei/big-market-app:1.0                                 0.0s
```

- 现在可以看到 `fuzhengwei/big-market-app:1.0`  的镜像已经打包出来来了。

### 2. 前端构建

#### 2.1 克隆项目

```java
[root@lavm-aqhgp9nber big-market-app]# cd /dev-ops/github/
[root@lavm-aqhgp9nber github]# ls
big-market
[root@lavm-aqhgp9nber github]# git clone https://gitcode.net/KnowledgePlanet/big-market/big-market-front.git
Cloning into 'big-market-front'...
Username for 'https://gitcode.net': Yao__Shun__Yu
Password for 'https://Yao__Shun__Yu@gitcode.net': 
remote: Enumerating objects: 93, done.
remote: Counting objects: 100% (93/93), done.
remote: Compressing objects: 100% (49/49), done.
remote: Total 93 (delta 33), reused 87 (delta 31), pack-reused 0
Unpacking objects: 100% (93/93), done.
```

- git clone 项目，需要输入你的账号密码。也可以配置 ssh 秘钥方式，就不用输入账号密码了。

#### 2.2 打包镜像

```java
[root@lavm-aqhgp9nber github]# cd big-market-front/
[root@lavm-aqhgp9nber big-market-front]# ls
next.config.js  package-lock.json  public     src                 tsconfig.json
package.json    postcss.config.js  README.md  tailwind.config.ts
[root@lavm-aqhgp9nber big-market-front]# ll
total 328
-rw-r--r-- 1 root root     92 Mar 31 13:26 next.config.js
-rw-r--r-- 1 root root    587 Mar 31 13:26 package.json
-rw-r--r-- 1 root root 299024 Mar 31 13:26 package-lock.json
-rw-r--r-- 1 root root     82 Mar 31 13:26 postcss.config.js
drwxr-xr-x 2 root root   4096 Mar 31 13:26 public
-rw-r--r-- 1 root root   1383 Mar 31 13:26 README.md
drwxr-xr-x 3 root root   4096 Mar 31 13:26 src
-rw-r--r-- 1 root root    507 Mar 31 13:26 tailwind.config.ts
-rw-r--r-- 1 root root    599 Mar 31 13:26 tsconfig.json
[root@lavm-aqhgp9nber big-market-front]# git branch
* 240211-xfg-init-project
[root@lavm-aqhgp9nber big-market-front]# git checkout main
Branch main set up to track remote branch main from origin.
Switched to a new branch 'main'
[root@lavm-aqhgp9nber big-market-front]# ls
build.sh    next.config.js  package-lock.json  public     src                 tsconfig.json
Dockerfile  package.json    postcss.config.js  README.md  tailwind.config.ts
[root@lavm-aqhgp9nber big-market-front]# chmod +x build.sh 
[root@lavm-aqhgp9nber big-market-front]# ls
build.sh    next.config.js  package-lock.json  public     src                 tsconfig.json
Dockerfile  package.json    postcss.config.js  README.md  tailwind.config.ts
[root@lavm-aqhgp9nber big-market-front]# ./build.sh 
 => [internal] load build definition from Dockerfile                                     0.0s
 => => transferring dockerfile: 697B                                                     0.0s
 => [internal] load metadata for docker.io/library/node:18-alpine                       37.0s
 => [internal] load .dockerignore                                                        0.0s
 => => transferring context: 2B                                                          0.0s
 => [internal] load build context                                                        0.1s
 => => transferring context: 599.89kB                                                    0.1s
 => [base 1/1] FROM docker.io/library/node:18-alpine@sha256:c698ffe060d198dcc6647be78e  63.1s
 => => resolve docker.io/library/node:18-alpine@sha256:c698ffe060d198dcc6647be78ea16833  0.0s
 => => sha256:2694e4502e2414f1f0ecb8d3216bd3dd8fdd19fc9edeef31ac653b 39.81MB / 39.81MB  60.7s
 => => sha256:f8ecf2fb4bd9a228128a2638a8bd59e1a2b1348019ff7d4ea6de2431 2.34MB / 2.34MB  16.8s
 => => sha256:d3da4a73e4df700cd719baa5e2d175a2cda03f72b893c73a4c7063ae89685 450B / 450B  0.3s
 => => sha256:c698ffe060d198dcc6647be78ea1683363f12d5d507dc5ec9855f1c55 1.43kB / 1.43kB  0.0s
 => => sha256:62ce0df0c57930a42a9f6025b33f165c73217159dcf7681148ecee10c 1.16kB / 1.16kB  0.0s
 => => sha256:ed7d7e5a958009e493ebfa4f476c77dcb03013caa7730a0fef7e6aa3a 7.14kB / 7.14kB  0.0s
 => => extracting sha256:2694e4502e2414f1f0ecb8d3216bd3dd8fdd19fc9edeef31ac653b250fe11e  2.0s
 => => extracting sha256:f8ecf2fb4bd9a228128a2638a8bd59e1a2b1348019ff7d4ea6de2431a76179  0.1s
 => => extracting sha256:d3da4a73e4df700cd719baa5e2d175a2cda03f72b893c73a4c7063ae896855  0.0s
 => [deps 1/5] RUN apk add --no-cache libc6-compat                                     230.3s
 => [builder 1/4] WORKDIR /app                                                           0.7s
 => [deps 2/5] WORKDIR /app                                                              0.0s 
 => [deps 3/5] COPY package.json yarn.lock* package-lock.json* pnpm-lock.yaml* ./        0.1s 
 => [deps 4/5] RUN yarn config set registry 'https://registry.npmmirror.com/'            0.7s 
 => [deps 5/5] RUN yarn install                                                        151.6s 
 => [builder 2/4] COPY --from=deps /app/node_modules ./node_modules                      9.9s 
 => [builder 3/4] COPY . .                                                               0.2s 
 => [builder 4/4] RUN yarn build                                                        47.4s 
 => [runner 2/5] COPY --from=builder /app/public ./public                                0.1s 
 => [runner 3/5] COPY --from=builder /app/.next/standalone ./                            0.5s 
 => [runner 4/5] COPY --from=builder /app/.next/static ./.next/static                    0.1s 
 => [runner 5/5] COPY --from=builder /app/.next/server ./.next/server                    0.1s 
 => exporting to image                                                                   0.4s 
 => => exporting layers                                                                  0.4s 
 => => writing image sha256:cdaae841aad5a14c8e8befbdab54827a4005a42bbe7904b9b0291e46342  0.0s
 => => naming to docker.io/fuzhengwei/big-market-front-app:1.4                           0.0s
```

- 检出项目后，没有看到 Dockerfile 文件，所以执行了 git branch 检查当前分支。之后执行 git checkout main 切换到 main 分支上。
- 接下来给 build.sh 加上权限，后执行构建脚本。

```java
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": [
    "https://h1log1d5.mirror.aliyuncs.com",
    "http://docker.mirrors.ustc.edu.cn",
    "http://hub-mirror.c.163.com"
   ]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```

- 如果构建失败，提示 `https://registry-1.docker.io/v2/: net/http: request canceled` 可以安装下镜像。

## 四、项目部署

完成第3步以后，你的镜像文件就已经在云服务上了。按照你自己的项目进行docker compose 编写，部署就可以了。如果你学习了小傅哥星球「码农会锁」的大营销实战项目，可以按照以下文件进行部署。

```java
version: '3.8'
# 命令执行 docker-compose -f docker-compose-app.yml up -d
services:
  big-market-app:
    image: fuzhengwei/big-market-app:1.0
    container_name: big-market-app
    restart: always
    ports:
      - "8091:8091"
    environment:
      - TZ=PRC
      - SERVER_PORT=8091
      - APP_CONFIG_API_VERSION=v1
      - APP_CONFIG_CROSS_ORIGIN=*
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=123456
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/big_market?serverTimezone=UTC&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Shanghai
      - SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
      - SPRING_HIKARI_POOL_NAME=Retail_HikariCP
      - REDIS_SDK_CONFIG_HOST=redis
      - REDIS_SDK_CONFIG_PORT=6379
    volumes:
      - ./log:/data/log
    networks:
      - my-network
    logging:
      driver: "json-file"
      options:
        max-size: "10m"
        max-file: "3"
  big-market-front-app:
    container_name: big-market-front-app
    image: fuzhengwei/big-market-front-app:1.4
    restart: always
    networks:
      - my-network
    ports:
      - 3000:3000
    environment:
      - PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
    healthcheck:
      test: [ "CMD", "wget", "--spider", "-q", "http://0.0.0.0:3000/" ]
      interval: 1m
      timeout: 10s
      retries: 3

networks:
  my-network:
    driver: bridge
```

执行；`docker-compose -f docker-compose-app.yml up -d` 脚本即可完成部署。

## 五、项目学习

小傅哥是把星球「**码农会锁**」当成互联网公司中一个事业群，所需要开发的技术项目来进行构建。所以在小傅哥的星球既可以学习业务项目，还可以掌握技术组件项目。同时为了大家更好的补充项目学习中欠缺的技术点。小傅哥还把各项技术栈拆成独立的案例分享给大家。

> 🧧星球「码农会锁」的知识体量是非常成体系的，也非常全面。加入这样一个星球，你的技术就可以稳步提升了！项目预览地址：https://gaga.plus

