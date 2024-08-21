---
title: ssl - httpsok
lock: need
---

# 又一款！免费的SSL，还能自动续期，支持CDN/OSS！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

从今年开始，越来越多的云服务厂商开始限制了 ssl 1年期的申请，只提供3个月有效期并且限制数量申请。而购买一个 ssl 证书，一年都至少几千块。那没有 ssl 不行吗？🤔 如果你不害怕自己的小网站被嵌入流量污染变成`小yellow广告网站`，还是建议你加下 ssl！

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-01.png" width="450px">
</div>

当你手里有超过1个以上的域名，如 `a.com`、`b.com`，之后你还对域名使用了多级域名部署不同的服务，如；`api.a.com`、`openai.b.com`、`blog.b.com` 那么这个时候一种是对每个域名都申请 ssl 证书，另外一种就是购买更贵的泛域名，支持 `*.a.com`。

除了对域名证书的申请还包括了这些域名证书的管理工作，你得知道他们什么时候到期，什么时候要更新。我见过有些互联网公司也有忘记更新域名的情况，从 https 跳转到了访问微信提示的页面。所以我需要一款 `自动续期`、`支持泛域名`、`可视化所有证书时效性`、`可配置CDN`的一款工具！好在，我找到了！

## 一、产品介绍

[https://httpsok.com/?p=4kMR](https://httpsok.com/?p=4kMR)  - 让证书续期更简单，一行命令，轻松搞定SSL证书自动续期！这一款工具的老板，竟然认识小傅哥！😂

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-02.png" width="550px">
</div>

已亲自体验，确实挺好用！像傻瓜相机一样，贼简单。

## 二、ssl证书

### 步骤1；点击申请

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-04.png" width="750px">
</div>

### 步骤2；填写域名

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-03.png" width="850px">
</div>

### 步骤3；域名验证

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-05.png" width="850px">
</div>

### 步骤4；验证成功

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-06.png" width="850px">
</div>

### 步骤5；等待下发

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-07.png" width="850px">
</div>

### 步骤6；下载证书

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-08.png" width="850px">
</div>

- 这里我们下载 Nginx 的，因为我们的服务是部署 Nginx 的。

## 三、域名配置

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-11.png" width="850px">
</div>

- 配置域名解析，把你要使用的域名和云服务器配置上A记录。这样请求到域名的时候就会解析到服务器的IP了，以及对应的 Nginx 转发。

## 四、Nginx配置

### 1. 脚本工程

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-09.png" width="850px">
</div>

- 工程：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-ssl](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-ssl)
- 说明：工程中提供了Nginx配置的初始信息，你只需要参考修改为自己的域名后，执行 `docker-compose.yml` 即可安装 Nginx

#### 1.1 修改域名

```java
server {
    listen       80;
    listen  [::]:80;
    server_name  ssl01.xiaofuge.tech;

    rewrite ^(.*) https://$server_name$1 permanent;

}

server {
    listen       443 ssl;
    server_name  ssl01.xiaofuge.tech;

    ssl_certificate      /etc/nginx/ssl/_.xiaofuge.tech.pem;
    ssl_certificate_key  /etc/nginx/ssl/_.xiaofuge.tech.key;

    ssl_session_cache    shared:SSL:1m;
    ssl_session_timeout  5m;

    ssl_ciphers  HIGH:!aNULL:!MD5;
    ssl_prefer_server_ciphers  on;

    location / {
        proxy_set_header   X-Real-IP         $remote_addr;
        proxy_set_header   Host              $http_host;
        proxy_set_header   X-Forwarded-For   $proxy_add_x_forwarded_for;
        root   /usr/share/nginx/html;
        index  index.html index.htm;
    }

    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }
}
```

- 首先，修改 `ssl01.xiaofuge.tech` 为你的域名。
- 之后，修改 ssl 地址信息。这个地址是 docker-compose 映射的地址。如果你是 linux 直接安装，则可以直接使用 acme.sh 安装的时候生成的地址。
- 注意，更多 Nginx 配置 [https://bugstack.cn/md/road-map/nginx.html](https://bugstack.cn/md/road-map/nginx.html)

#### 1.2 修改证书

```java
version: '3'
# docker-compose -f docker-compose.yml up -d
services:
  nginx:
    image: nginx:1.25.3
    container_name: nginx
    ports:
      - '443:443'
      - '80:80'
    volumes:
      - ./nginx/logs:/var/log/nginx
      - ./nginx/html:/usr/share/nginx/html
      - ./nginx/conf/nginx.conf:/etc/nginx/nginx.conf
      - ./nginx/conf/conf.d:/etc/nginx/conf.d
      - ./nginx/ssl:/etc/nginx/ssl/
    privileged: true
    restart: always
```

- 脚本映射了 ssl、conf.d 等文件内容。

### 2. 上传文件

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-10.png" width="850px">
</div>

- 配置信息全部修改后，上传到你的云服务器上。

### 3. 执行脚本

```java
[root@lavm-aatjb98slj httpsok]# docker-compose up -d
[+] Running 1/1
 ✔ Container nginx  Started    
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-10.png" width="950px">
</div>

## 五、访问验证

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-12.png" width="850px">
</div>

- 访问地址；`https://ssl01.xiaofuge.tech/`、`https://ssl02.xiaofuge.tech/` 验证证书是否部署成功。
- 如图，验证结果符合预期。

## 六、自动部署

这个自动部署非常重要，有了它我们就不用操心是什么时候过期，手动处理了！

### 1. 获取脚本

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-13.png" width="850px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-14.png" width="850px">
</div>

### 2. 执行脚本

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-15.png" width="850px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-16.png" width="850px">
</div>

- 从 Docker Portainer 中的 Nginx 进入执行脚本。

### 3. 安装查看

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-17.png" width="850px">
</div>

- 如上表示自动部署正常，当证书剩余15天过期后，会自动部署。

### 4. 证书监控

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ssl-httpsok-18.png" width="850px">
</div>

- 这里还能看到每个证书剩余的有效期，还可以看到是哪个IP的服务器在使用。