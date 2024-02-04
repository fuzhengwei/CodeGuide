---
title: 搭建博客，展示过往经历。让面试官更了解我，面试更稳了！
lock: need
---

# 搭建博客，展示过往经历。让面试官更了解我，面试更稳了！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

不少小伙伴都想放在简历上放一个动态博客链接，展示自己的过往技术经历和可运行的项目展示。通过这样的方式提高自己在面试中的竞争力。但因为没做过，又不知道从哪开始，找的一些案例教程又都很复杂。所以，小傅哥来帮你一下，让你SoEasy的搞定这东西。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-extra-230905-01.png?raw=true" width="200px">
</div>

当这个男人👨🏻准备下手的时候，一切技术将变得简单、容易，好理解！

我把本次的分内容定义为简历博客，并已经做好了博客的框架（[vuepress](https://theme-hope.vuejs.press/zh/)）和内容案例，以及部署需要的Nginx配置。如果你需要部署到线上，那么需要准备一台云服务器（[2c2g 50元/1年](https://gaga.plus/yun.html)）。另外你还可以注册一个域名（ICP备案2周），这样就可以通过域名访问你的专属博客了。【要我说，每个程序员👨🏻‍💻都应该有一个自己的域名，有了它你才有了做一些事情的可能】。**接下来我会带着你搭建一个如图所示的个人简历博客**

<div align="center">
    <img src="https://bugstack.cn/images/article/devops/xfg-dev-tech-blog-01.png?raw=true" width="700px">
</div>

那么，接下来小傅哥就带着你，完成下简历博客的搭建。

>文末提供了8个Java实战项目，嘎嘎提升简历竞争力。💐

## 一、搭建准备

- 简历博客项目：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-blog](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-blog)
- 一台云服务器，2c2g 推荐 50元/1年 [https://gaga.plus/yun.html](https://gaga.plus/yun.html)
- 云服务器搭建 Docker、安装 Portainer 教程地址（包括学习视频）：[https://bugstack.cn/md/road-map/docker.html](https://bugstack.cn/md/road-map/docker.html)
- 域名和域名备案（ICP备案2周），未购买也可以完成本节课程的操作。
- SSL 申请，如果有域名了，可以申请免费的 SSL 让域名地址支持 HTTPS。申请地址：[https://yundun.console.aliyun.com/?p=cas#/certExtend/free/cn-hangzhou](https://yundun.console.aliyun.com/?p=cas#/certExtend/free/cn-hangzhou)
- Node.js 安装，地址：[https://nodejs.org/en/download](https://nodejs.org/en/download) - `选择自己机器适配的版本安装即可`

## 二、项目说明

这里小傅哥已经把博客框架、内容案例、部署博客的环境，都准备好了。你只需要下载到本地就可以使用。

<div align="center">
    <img src="https://bugstack.cn/images/article/devops/xfg-dev-tech-blog-02.png?raw=true" width="400px">
</div>

本博客采用 [VuePress Theme Hope](https://theme-hope.vuejs.press/zh/) 模板进行搭建。当你能完成部署本博客后，还可以参考模板做一些其他的扩展以及各类喜欢插件的安装。

- src 目录下，为博客的框架模板和已经写好的案例。在 `package.json`中有一个 `docs:build` 用于构建，还有一个 `docs:dev`用于本地启动测试。
- dev-ops 提供的是博客的部署，这里的 docker-compose.yml 是 Docker 执行脚本安装 Nginx 环境。
- 小傅哥在 `src/.vuepress-> config.ts`文件中下配置了把项目 build 到 nginx/html 文件夹。这样可以更加方便我们部署。

## 三、部署项目

当你把 xfg-dev-tech-blog 项目检出到本地，使用 IntelliJ IDEA 或者 WebStrom 工具打开后。会提示你执行 npm install 安装环境。如果没有提示你也可以自己在项目 Terminal 下执行。执行完安装就可以做博客的部署了。

<div align="center">
    <img src="https://bugstack.cn/images/article/devops/xfg-dev-tech-blog-03.png?raw=true" width="550px">
</div>

### 1. 构建项目

<div align="center">
    <img src="https://bugstack.cn/images/article/devops/xfg-dev-tech-blog-04.png?raw=true" width="550px">
</div>

- 打开项目的 `package.json`里面有`构建`和`运行`命名。
- 构建的操作会把工程打包为 HTML 文件写入到 dev-ops/nginx/html 文件夹下。
- 运行的操作会直接本地启动服务，启动后你可以在本地预览。这样你在修改一些内容的时候也可以随时看到效果。

### 2. 构建结果

<div align="center">
    <img src="https://bugstack.cn/images/article/devops/xfg-dev-tech-blog-05.png?raw=true" width="750px">
</div>

- 如步骤操作，完成构建。当你看到 `dev-ops/nginx/html` 下有对应的一堆文件了，说明构建成功了。

### 3. Nginx 配置

对于 Nginx 的配置提供了3种方式，分别包括；IP访问、HTTP域名访问、HTTPS域名访问。

<div align="center">
    <img src="https://bugstack.cn/images/article/devops/xfg-dev-tech-blog-06.png?raw=true" width="350px">
</div>

#### 1. localhost.conf

这个配置方式为了方便，没有域名的伙伴们测试使用。也就是你部署后可以直接通过IP进行访问。

```shell
server {
    listen       80;
    listen  [::]:80;

    location / {
       root /usr/share/nginx/html;
       index index.html;
    }

    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root /usr/share/nginx/html;
    }

}
```

- 直接把 80 请求转发到本地的根目录地址的 html 文件上即可

#### 2. http.blog.conf

##### 2.1 配置域名解析

<div align="center">
    <img src="https://bugstack.cn/images/article/devops/xfg-dev-tech-blog-07.png?raw=true" width="550px">
</div>

- 如果你有云服务器和域名，那么可以先如图，配置下域名解析地址。

##### 2.2 nginx 脚本

```shell
server {
    listen       80;
    listen  [::]:80;
    server_name  blog.gaga.plus;

    location / {
       root /usr/share/nginx/html;
       index index.html;
    }

    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root /usr/share/nginx/html;
    }

}
```

#### 3. https.blog.conf

如果你有域名那么还可以配置免费的ssl验证，配置完成后把 ssl key、pem 文件放到 dev-ops/nginx/ssl 文件夹下并上传到云服务器上。详细介绍：[https://bugstack.cn/md/devops/2023-04-18-nginx.html#_4-1-%E5%88%9B%E5%BB%BA%E8%AF%81%E4%B9%A6](https://bugstack.cn/md/devops/2023-04-18-nginx.html#_4-1-%E5%88%9B%E5%BB%BA%E8%AF%81%E4%B9%A6)

```shell
server {
    listen       80;
    listen  [::]:80;
    server_name  blogs.gaga.plus;

    rewrite ^(.*) https://$server_name$1 permanent;
}

server {
    listen       443 ssl;
    server_name  api.gaga.plus;

    ssl_certificate      /etc/nginx/ssl/blogs.gaga.plus.pem;
    ssl_certificate_key  /etc/nginx/ssl/blogs.gaga.plus.key;

    ssl_session_cache    shared:SSL:1m;
    ssl_session_timeout  5m;

    ssl_ciphers  HIGH:!aNULL:!MD5;
    ssl_prefer_server_ciphers  on;

    location / {
         root /usr/share/nginx/html;
         index index.html;
    }

    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }
}
```

- 这个方式就是配置 ssl 的方式。

### 4. 上传文件

<div align="center">
    <img src="https://bugstack.cn/images/article/devops/xfg-dev-tech-blog-08.png?raw=true" width="750px">
</div>

- 小傅哥这里使用的是 [https://termius.com/](https://termius.com/) 连接云服务器的 SSH 工具，自带 SFTP 。`你也可以使用其他 SSH/FTP 工具`
- 通过这个工具，连接云服务器，并把我们在工程下创建的 `dev-ops` 上传到云服务器端。

### 5. 服务启动

接下来，你只需要在云服务器进入到 dev-ops 文件夹下，执行脚本 `docker-compose -f docker-compose.yml up -d` 即可完成部署。

<div align="center">
    <img src="https://bugstack.cn/images/article/devops/xfg-dev-tech-blog-09.png?raw=true" width="750px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/devops/xfg-dev-tech-blog-10.png?raw=true" width="750px">
</div>

- 在你初次部署的时候，会拉取 Nginx 镜像。拉取完成后则会自动部署。
- 部署完成后可以进入到 portainer 中查看 nginx 运行，以及进入到日志中查看运行情况。

### 6. 访问博客

- IP地址：[http://117.72.37.243/](http://117.72.37.243/)
- HTTP地址：[http://blog.gaga.plus/](http://blog.gaga.plus/)
- HTTPS地址：[https://blogs.gaga.plus/](https://blogs.gaga.plus/)

<div align="center">
    <img src="https://bugstack.cn/images/article/devops/xfg-dev-tech-blog-11.png?raw=true" width="950px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/devops/xfg-dev-tech-blog-12.png?raw=true" width="950px">
</div>

**全部完成撒花 💐** 现在你就可以在简历上挂一个自己的博客了，还可以在博客中秀出自己的技术肌肉💪🏻，赢得面试官的青睐！

>当然，有了简历博客，最好配有一套对应的高质量实战项目，撑起简历的技术度。那些CRUD/XXX管理系统，现在是越来越没竞争力了。早点学一些，每天积累一点，就能让自己有更多的选择。

## 四、实战项目

小傅哥工作10年+，深刻感受到在不同阶段(毕业~3年，3年~5年、5年+)都要有对应的能力，才能踏上换乘的列车。为了让自己的职业生涯走的更加稳妥，就不要只把自己当成满足工作需要的工具。早些积累、早些积累自己的技术栈体系闭环。这是非常重要的！！！

>小傅哥把工作中的技术经验，陆续的编写出对应的博客和实战项目，整整写了3年多。这些内容可以让一个研发小白成长为技术大牛！

<div align="center">
    <img src="https://bugstack.cn/images/article/devops/xfg-dev-tech-blog-13.png?raw=true" width="950px">
</div>

**加入学习 https://gaga.plus**

>[🧧加入学习](https://bugstack.cn/md/zsxq/other/join.html)

**注意📢**，加入星球后你可以申请加入星球VIP群，获得高质量技术交流。此外星球的所有内容，都从星球的**课程入口**进入，里面做了非常细致的归档。