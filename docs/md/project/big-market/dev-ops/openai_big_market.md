---
title: 第4节：课程完结，上线部署教程
lock: no
---

# 《大营销平台系统设计实现》 - 开发运维 第4节：课程完结，上线部署教程

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

不少小伙伴在面试中总会被提问，你的微服务是哪两套项目对接，你是分布式体现在哪。好像这么一问，平时做的项目既不是微服务，也不是分布式，都不知道怎么回答啦！咋办！？🤔

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-docker-idea-00.png" width="200px">
</div>

**还是别做玩具项目了！**

要说这种级别的东西，要不是在公司里真的实践过，要不是跟着小傅哥完完全全的做过。否则还真没有这类经验，很多市面的CRUD项目都是一个个孤岛单体，根本没有微服务对接，也谈不上分布式架构。所有的这类东西，都是真的在职场多年实践，才能一步步带着你学习掌握到！

这次，我把两套项目完成了微服务对接，分布式架构部署。你可以完全全全的学习到这些东西到底是怎么玩的！

>上车，这次项目做的很硬核！文末可以获取全部项目课程。

## 1. 效果展示

这是一次非常庞大的系统开发实践，学习这样一套东西，可以全面的提高个人综合技术实战能力。了解业务、懂得架构、提到思维、锻炼编码。一个个场景的解决方案，一个个设计模式的实践运用。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-dev-ops-5-06.png" width="600px">
</div>

- 地址：[https://openai.gaga.plus/](https://openai.gaga.plus/) - 侧边栏，点击兑换、购物、营销。
- 说明：你可以体验 OpenAI 兑换、抽奖、兑换、签到等各个功能。这些也都各个互联网公司C端场景的产品设计。

>接下来是一整套的项目的部署执行过程，公司中大家上线，对于中大型项目和需求，也会列出明确的上线执行步骤。

## 2. 部署结构

### 2.1 微服务&分布式

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-dev-ops-5-07.png" width="750px">
</div>

- 微服务：一套 OpenAi chatgpt-data 后端应用，一套 big-market 后端应用，两套微服务。
- 分布式：Nginx 负载 http，大营销负载 rpc，支持多实例部署，mq 消息解耦流程、xxl-job 任务驱动、redis 缓存预热数据。分库分表、canal 完成数据的分离和聚合。

### 2.2 分层部署关系

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-dev-ops-5-08.png" width="750px">
</div>

- 从底层往上，依次是[服务器（4c16g）](http://618.gaga.plus/)、部署环境、应用环境、上线配置、应用构建、参数配置，之后就是执行应用启动啦。
- 接下来我们的部署，也会做这些操作。

## 3. 环境诉求

>为了方便大家完成项目的部署，这里采用了 Linux 服务器直接搭建构建环境的方式进行处理。在以下操作中你可以把 Linux 当做一台本地的电脑进行使用。只不过都是命令方式操作。

本节需要在一台服务器上，部署分布式技术栈（nacos、mysql、redis、xxl-job、rabbitmq、Zookeeper等），2套 big-market 大营销后台（rpc 负载）、1套 chatgpt-data 后端、1套 chatgpt-web 前端。

1. 推荐 `4c16g` 服务器 3个月：https://618.gaga.plus - `158元(续费同价)`，这个价格比较适合临时做测试验证。【占用在12g+】
2. docker、portainer - `在 bugstack.cn 路书中有安装教程`
3. jdk 1.8、maven 3.8.8、git，课程提供了安装部署视频
4. Git使用教程：[https://bugstack.cn/md/road-map/git.html](https://bugstack.cn/md/road-map/git.html)
5. 申请微信公众号测试平台：[https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index](https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index) - 需要申请。
6. 智谱AI，申请Key：[https://bigmodel.cn/usercenter/apikeys](https://bigmodel.cn/usercenter/apikeys) - OpenAI 项目会使用到

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-dev-ops-5-01.png" width="600px">
</div>

- 脚本：[https://gitcode.net/KnowledgePlanet/big-market/big-market-dev-ops](https://gitcode.net/KnowledgePlanet/big-market/big-market-dev-ops)
- 说明：这里小傅哥已经分各个阶段给大家提供好了部署脚本，你可以参照使用。在 mysql 包下，已经提供了本次项目部署所需的 sql 初始化文件。

**docker 镜像推荐**

```java
# 清空 /etc/docker/daemon.json 文件，准备写入新内容
echo >/etc/docker/daemon.json

# 使用 cat 命令将以下内容写入 /etc/docker/daemon.json 文件
# 配置 Docker 镜像加速器，使用多个镜像源来提高镜像下载速度
cat >/etc/docker/daemon.json <<EOF
{
  "registry-mirrors": [
    "https://dc.j8.work",
    "https://proxy.1panel.live",
    "https://dockerproxy.cn",
    "https://hub1.nat.tf",
    "https://dockerproxy.1panel.live",
    "https://docker.awsl9527.cn",
    "https://docker.vpszj.top"
  ]
}
EOF

# 重启 Docker 服务以使配置生效
systemctl restart docker
```

- 地址：[https://status.1panel.top/status/docker](https://status.1panel.top/status/docker) - 这里由1panel提供了可用的镜像地址。
- 说明：如果你希望从官网拉取镜像，那么可以做以上的镜像配置。

## 4. 环境配置

注意：执行脚本前，确保你已经完成了 linux 服务器的购买，docker、portainer 环境的安装。这样你才能执行 docker-compose-environment.yml 文件安装环境。

### 1. 上传脚本

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-dev-ops-5-02.png" width="600px">
</div>

- 推荐使用 [termius](https://termius.com/) 连接云服务器，在根目录创建 `/dev-ops` 文件夹。再通过 SFTP 工具，把本地的部署脚本上传到文件夹中。

### 2. 执行脚本

```shell
cd /dev-ops
# 方式1
docker-compose -f docker-compose-environment.yml up -d

# 方式2        
docker-compose -f docker-compose-environment-xfg-studio.yml up -d
```

- 方式1；需要配置可用的 docker 镜像，才能拉取。
- 方式2；是小傅哥准备好的镜像地址，速度更快一些。如果方式2不可用了，可以继续用方式1

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-dev-ops-5-03.png" width="950px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-dev-ops-5-05.png" width="750px">
</div>

```java
- nacos：http://117.72.90.238:8848/nacos - 「账密：nacos/nacos」
- rabbitmq：http://117.72.90.238:15672/ - 「账密：admin/12qw!@QW」
- redis-admin：http://117.72.90.238:8081/ - 「账密：admin/12qw!@QW」
- xxl-job-admin：http://117.72.90.238:9090/xxl-job-admin - 「账密：root/12qw!@QW」
- phpmyadmin：http://117.72.90.238:8899/ - 「账密：root/12qw!@QW」
```

- 部署完成后，可以依次访问 phpmyadmin、rabbitmq、redis-admin、xxl-job-admin、nacos 这些管理后台。检查数据库表是否已经全部初始化完成。
- 账号、密码、IP，你可以按照自己的进行修改，密码是配置在 docker-compose-environment/environment-xfg-studio 脚本中。

## 5. 应用构建

在 /dev-ops/ 目录下创建创建 gitcode 文件夹，里面分别拉取代码；

### 1. big-market 大营销后端

#### 1.1 参数配置

1. application.yml 修改 `active: prod` 为上线参数
2. application-prod.yml 为上线部署配置，其中一些IP的地方被名称替代，如 mysql、zookeeper等，是因为这些IP可以在 docker-compose 部署中，在一个文件夹下，走同一个网络，进行内网通信。
3. docker-compose-app.yml big-market 注意配置 rabbitmq 地址为你的服务器公网ip地址，并开放端口 5672
4. docker-compose-app.yml 配置了2套 big-market 应用实例（不同端口）进行负载。
5. gitcode 检出代码，可参考文章开头提到的 Git 使用教程。

#### 1.2 构建脚本

```shell
cd /dev-ops/gitcode/
git clone -b docker-images-v5.0 https://gitcode.net/KnowledgePlanet/big-market/big-market.git

cd big-market/
mvn clean install

cd big-market-app
chmod +x build.sh
./build.sh
```

### 2. chatgpt-data OpenAI 后端

#### 2.1 参数配置

1. application.yml 修改 `active: prod` 为上线参数
2. application-prod.yml 为上线部署配置，其中一些IP的地方被名称替代，如 mysql、nacos，是因为这些IP可以在 docker-compose 部署中，在一个文件夹下，走同一个网络，进行内网通信。
3. chatgpt.sdk.config.enabled、wxpay.config.enabled，可以都配置false，如果你有 chatgpt 和 微信支付那么可以配置。星球中还有关于支付宝沙箱、蓝兔支付，也可以更换对接。

#### 2.2 构建脚本

```shell
cd /dev-ops/gitcode/
git clone -b docker-images-v5.0 https://gitcode.net/KnowledgePlanet/chatgpt/chatgpt-data.git

cd chatgpt-data/
mvn clean install

cd chatgpt-data-app
chmod +x build.sh
./build.sh
```

### 3. chatgpt-web OpenAI 前端

#### 3.1 配置

**index.tsx**

```java
const openAIApiHostUrl = "http://117.72.90.238:8091";
const bigMarketApiHostUrl = "http://117.72.90.238:8092";
```

- 修改为你的IP:PORT地址，如果你有域名也可以统一配置 https 的域名地址，但要全部都是统一的。避免跨域问题。

#### 3.2 脚本

```shell
cd /dev-ops/gitcode/
git clone -b docker-images-v5.0 https://gitcode.net/KnowledgePlanet/chatgpt/chatgpt-web.git

chmod +x build.sh
./build.sh
```

## 6. 应用部署

### 1. 脚本启动

以下为应用部署 docker compose 脚本，部署的时候进入linux此脚本所在文件夹，执行 `docker-compose -f docker-compose-app.yml up -d` 即可。

```java
version: '3.8'
# 命令执行 docker-compose -f docker-compose-app.yml up -d
services:
  # 大营销后端
  big-market-app-01:
    image: fuzhengwei/big-market-app:5.1
    container_name: big-market-app-01
    restart: always
    ports:
      - "8092:8092"
    environment:
      - TZ=PRC
      - SERVER_PORT=8092
      - APP_CONFIG_API_VERSION=v1
      - APP_CONFIG_CROSS_ORIGIN=*
      - ZOOKEEPER_SDK_CONFIG_ENABLE=false
      - SPRING_RABBITMQ_ADDRESSES=117.72.90.238
    volumes:
      - ./log:/data/log
    networks:
      - my-network
    logging:
      driver: "json-file"
      options:
        max-size: "10m"
        max-file: "3"

  # 大营销后端
  big-market-app-02:
    image: fuzhengwei/big-market-app:5.1
    container_name: big-market-app-02
    restart: always
    ports:
      - "8093:8093"
    environment:
      - TZ=PRC
      - SERVER_PORT=8093
      - APP_CONFIG_API_VERSION=v1
      - APP_CONFIG_CROSS_ORIGIN=*
      - ZOOKEEPER_SDK_CONFIG_ENABLE=false
      - SPRING_RABBITMQ_ADDRESSES=117.72.90.238
    volumes:
      - ./log:/data/log
    networks:
      - my-network
    logging:
      driver: "json-file"
      options:
        max-size: "10m"
        max-file: "3"

  # OpenAI 应用后端
  chatgpt-data-app:
    image: fuzhengwei/chatgpt-data-app:5.1
    container_name: chatgpt-data-app
    ports:
      - "8091:8091"
    environment:
      - TZ=PRC
      - SERVER_PORT=8091
      - APP_CONFIG_API_VERSION=v1
      - APP_CONFIG_CROSS_ORIGIN=*
      - APP_CONFIG_LIMIT_COUNT=3
      - APP_CONFIG_WHITE_LIST=ojbZUv18lbmriaTjcCWBYkOrSbHA
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=12qw!@QW
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/openai?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC&useSSL=true
      - WX_CONFIG_ORIGINALID=gh_e067c267e056
      - WX_CONFIG_APPID=wx5a228ff69e28a91f
      - WX_CONFIG_TOKEN=b8b6
      - CHATGPT_SDK_CONFIG_ENABLED=false
      - CHATGPT_SDK_CONFIG_API_HOST=https://service-d6wuqy4n-1320869466.cd.apigw.tencentcs.com/
      - CHATGPT_SDK_CONFIG_API_KEY=sk-cVvbudeJq3yQUvcyCeBeB97253C146BaAaC94d70D2890b8e
      - CHATGLM_SDK_CONFIG_ENABLED=true
      - CHATGLM_SDK_CONFIG_API_HOST=https://open.bigmodel.cn/
      - CHATGLM_SDK_CONFIG_API_KEY=96b1fb042a0b51f334ecfdb28ef95837.6pgWXziv5CdbJdGP
    volumes:
      - ./log:/data/log
    networks:
      - my-network
    logging:
      driver: "json-file"
      options:
        max-size: "10m"
        max-file: "3"
    restart: always

  # OpenAI 应用前端
  chatgpt-web-app:
    container_name: chatgpt-web-app
    image: fuzhengwei/chatgpt-web-app:5.1
    ports:
      - "3002:3002"
    networks:
      - my-network
    restart: always

networks:
  my-network:
    driver: bridge
```

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-dev-ops-5-04.png" width="950px">
</div>

- 注意：`SPRING_RABBITMQ_ADDRESSES=117.72.90.238`，需要配置云服务器IP地址，并开放 RabbitMQ 端口。
- 执行预热活动：`http://117.72.90.238:8092/api/v1/raffle/activity/armory?activityId=100401`
- 本身大营销还有一个后台，但服务器可能不够，如果不部署后台，不做活动上架，那么可以通过执行预热活动接口完成活动预热。
- 注意微信配置

```java
- WX_CONFIG_ORIGINALID=gh_e067c267e056
- WX_CONFIG_APPID=wx5a228ff69e28a91f
- WX_CONFIG_TOKEN=b8b6
```

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-dev-ops-5-11.png" width="950px">
</div>
- 全部更换为你的配置信息。注意，接口配置信息，需要在服务启动后配置。


### 2. natapp 启动 - 无自己域名

#### 2.1 购买隧道

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-dev-ops-5-09.png" width="950px">
</div>

```java
[default]
authtoken=bcffa08ea0a7dde4      #对应一条隧道的authtoken，你需要更换为你的。否则不能正常启动。
clienttoken=                    #对应客户端的clienttoken,将会忽略authtoken,若无请留空,
log=none                        #log 日志文件,可指定本地文件, none=不做记录,stdout=直接屏幕输出 ,默认为none
loglevel=ERROR                  #日志等级 DEBUG, INFO, WARNING, ERROR 默认为 DEBUG
http_proxy=                     #代理设置 如 http://10.123.10.10:3128 非代理上网用户请务必留空
```

- 打开 natapp/config.ini 文件，复制 authtoken 替换为你的。

#### 2.2 执行启动

```java
[root@lavm-snw6pd3z62 chatgpt-web]# cd /dev-ops/
[root@lavm-snw6pd3z62 dev-ops]# ls
canal          docker-compose-app.yml                     gitcode  kibana    maven   nginx       redis
canal-adapter  docker-compose-environment-xfg-studio.yml  grafana  log       mysql   prometheus
curl           docker-compose-environment.yml             java     logstash  natapp  rabbitmq
[root@lavm-snw6pd3z62 dev-ops]# cd natapp/
[root@lavm-snw6pd3z62 natapp]# ./natapp 
```

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-dev-ops-5-10.png" width="550px">
</div>

- 启动 Natapp 穿透程序，主要是因为微信公众号配置，需要域名。如果你没有域名就按照这个方式，如果有域名，可以走自己的域名。
- 在微信公众平台配置 URL 验签地址：[https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index](https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index) 配置地址：`http://xfg-studio.natapp1.cc/api/v1/wx/portal/wxad979c0307864a66` - 更换为你的 natapp 内网穿透地址。

### 3. Nginx 配置 - 有自己域名

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-dev-ops-5-12.png" width="550px">
</div>

- 域名：需要购买，各个云服务器厂商，都可以购买域名。
- ssl：在 bugstack.cn 路书中有讲解如何配置免费 ssl。
- 在微信公众平台配置 URL 验签地址：[https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index](https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index) 配置地址：`http://api-test.gaga.plus/api/v1/wx/portal/wxad979c0307864a66` - 更换为你的域名地址。

## 7. 应用访问

地址：[http://117.72.90.238:3002/](http://117.72.90.238:3002/) - `更换为你的IP地址`

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-dev-ops-5-13.png" width="550px">
</div>

> 登录之后，你就可以愉快的玩耍啦！
