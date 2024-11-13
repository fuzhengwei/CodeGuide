---
title: Nginx
lock: need
---

# Nginx 环境配置

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

- 停止：`docker stop Nginx`
- 重启：`docker restart Nginx`
- 删除服务：`docker rm Nginx`
- 删除镜像：`docker rmi Nginx`
- 进入服务：`docker exec -it Nginx /bin/bash`
- 配置文件：[nginx - conf/html/logs/ssl](https://github.com/fuzhengwei/RoadMap/tree/main/10-%E5%8F%91%E5%B8%83%E9%83%A8%E7%BD%B2/103-%E6%9C%8D%E5%8A%A1%E5%99%A8/1-Nginx/data)

## 一、基础安装

```java
docker run \
--restart always \
--name Nginx \
-d \
-p 80:80 \
nginx
```

![](https://bugstack.cn/images/article/devops/dev-ops-nginx-230418-01.png)

- restart 重启策略，always 是一直保持重启。如果不设置，可以把这条删掉。`never\always`
- `8090` - 容器端口、`80` - 服务器端口，这样外部通过80端口即可访问。

## 二、管理配置

首次部署 nginx 后，其实我们还不好操作配置文件。也就是 Nginx 的配置文件是在 Docker 容器的程序下，只有把它拷贝到服务器上才好操作。

### 1. 进入 Nginx

进入程序：docker exec -it Nginx /bin/bash - 退出程序：exit

```java
[root@vultr ~]# docker exec -it Nginx /bin/bash
root@ed8dc07f2ae6:/# ls
bin  boot  dev  docker-entrypoint.d  docker-entrypoint.sh  etc  home  lib  lib64  media  mnt  opt  proc  root  run  sbin  srv  sys  tmp  usr  var
root@ed8dc07f2ae6:/# cd etc/nginx/
root@ed8dc07f2ae6:/etc/nginx# ls
conf.d  fastcgi_params  mime.types  modules  nginx.conf  scgi_params  uwsgi_params
root@ed8dc07f2ae6:/etc/nginx# pwd
/etc/nginx
root@ed8dc07f2ae6:/# cd /usr/share/nginx/html
root@ed8dc07f2ae6:/usr/share/nginx/html# ls
50x.html  index.html
root@ed8dc07f2ae6:/usr/share/nginx/html# cat index.html 
<!DOCTYPE html>
<html>
<head>
<title>Welcome to nginx!</title>
<style>
html { color-scheme: light dark; }
body { width: 35em; margin: 0 auto;
font-family: Tahoma, Verdana, Arial, sans-serif; }
</style>
</head>
<body>
<h1>Welcome to nginx!</h1>
<p>If you see this page, the nginx web server is successfully installed and
working. Further configuration is required.</p>

<p>For online documentation and support please refer to
<a href="http://nginx.org/">nginx.org</a>.<br/>
Commercial support is available at
<a href="http://nginx.com/">nginx.com</a>.</p>

<p><em>Thank you for using nginx.</em></p>
</body>
</html>
root@ed8dc07f2ae6:/usr/share/nginx/html# 
root@ed8dc07f2ae6:/usr/share/nginx/html# exit
exit
```

- 配置：`/etc/nginx`
- 网页：`/usr/share/nginx/html`

### 2. 拷贝 Nginx

创建目录

```shell
[root@vultr ~]# mkdir -p /data/nginx/conf
[root@vultr ~]# mkdir -p /data/nginx/html
```

拷贝文件

```shell
[root@vultr ~]# docker container cp Nginx:/etc/nginx/nginx.conf /data/nginx/conf
[root@vultr ~]# docker container cp Nginx:/usr/share/nginx/html/index.html /data/nginx/html
```

查看信息

```shell
[root@vultr ~]# ls /data/nginx/conf/
nginx.conf
[root@vultr ~]# ls /data/nginx/html/
index.html
```

### 3. 部署 Nginx

```shell
docker run \
--restart always \
--name Nginx \
-d \
-v /data/nginx/html:/usr/share/nginx/html \
-v /data/nginx/conf/nginx.conf:/etc/nginx/nginx.conf \
-p 80:80 \
nginx
```

- 重启：`sudo service nginx restart`


## 三、证书安装

### 4.1 创建证书

SSL 免费的证书，一种是 [ssl - 支持自动续期](https://bugstack.cn/md/road-map/ssl-httpsok.html) 另外各个云服务厂商都有提供，可以自己申请。这里以阿里云/京东云举例；

- 阿里云免费域名证书：[https://yundun.console.aliyun.com/?p=cas#/certExtend/free/cn-hangzhou](https://yundun.console.aliyun.com/?p=cas#/certExtend/free/cn-hangzhou)
- 京东云免费域名证书：[https://certificate-console.jdcloud.com/jsecssl/create?fastConfig=false&certBrand=TrustAsia&certType=domainType&protectionType=DV-1&gDomainCount=0](https://certificate-console.jdcloud.com/jsecssl/create?fastConfig=false&certBrand=TrustAsia&certType=domainType&protectionType=DV-1&gDomainCount=0) - 选择 TrustAsia 单域名 3个月 0元

![](https://bugstack.cn/images/article/devops/dev-ops-nginx-230418-02.png)

- 步骤1；通过免费的方式创建 SSL，之后通过引导的 DNS 方式进行验证。其实就是在你的域名里配置下验证信息。
- 步骤2；申请后，3-5分钟左右 DNS 会验证通过，这个时候你直接下载 Nginx 的 SSL 包即可。里面有2个文件【x.key、x.pem】

### 4.2 准备内容

#### 4.2.1 单个证书

- 把下载好的 SSL 文件解压到桌面，你会得到一个文件夹，里面含有 x.key、x.pem 两个文件。
- 创建一个 default.conf 这个文件配置的 SSL 信息

```conf
server {
    listen       80;
    listen  [::]:80;
    server_name  openai.xfg.im;

    rewrite ^(.*) https://$server_name$1 permanent;

}

server {
    listen       443 ssl;
    server_name  openai.xfg.im;

    ssl_certificate      /etc/nginx/ssl/9740289_openai.xfg.im.pem;
    ssl_certificate_key  /etc/nginx/ssl/9740289_openai.xfg.im.key;

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

- 你可以复制这份文件，在自己本地创建。注意修改域名和SSL文件路径。

#### 4.2.2 多个证书

如果你需要给1个以上的域名配置SSL，那么可以配置多组 server 如下；

```shell script
server {
    listen       80;
    listen  [::]:80;
    server_name  itedus.cn;

    rewrite ^(.*) https://$server_name$1 permanent;

}

server {
    listen       443 ssl;
    server_name  itedus.cn;

    ssl_certificate      /etc/nginx/ssl/9750021_itedus.cn.pem;
    ssl_certificate_key  /etc/nginx/ssl/9750021_itedus.cn.key;

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

server {
    listen       80;
    listen  [::]:80;
    server_name  chatgpt.itedus.cn;

    rewrite ^(.*) https://$server_name$1 permanent;

}

server {
    listen       443 ssl;
    server_name  chatgpt.itedus.cn;

    ssl_certificate      /etc/nginx/ssl/9749920_chatgpt.itedus.cn.pem;
    ssl_certificate_key  /etc/nginx/ssl/9749920_chatgpt.itedus.cn.key;

    ssl_session_cache    shared:SSL:1m;
    ssl_session_timeout  5m;

    ssl_ciphers  HIGH:!aNULL:!MD5;
    ssl_prefer_server_ciphers  on;

    location / {
        proxy_pass http://180.76.119.100:3002;
        proxy_http_version 1.1;
        chunked_transfer_encoding off;
        proxy_buffering off;
        proxy_cache off;
    }

    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }
}
```

### 4.3 上传文件

你可以通过 `SFTP` 工具或者 `mkdir -p`、`touch` 命令创建一些服务器本地用于映射的文件夹和文件，这里小傅哥使用了 [Termius](https://www.termius.com/) 工具进行创建操作。

![](https://bugstack.cn/images/article/devops/dev-ops-nginx-230418-03.png)

- 文件1；html 
- 文件2；ssl - 把本地的 ssh 文件上传进来
- 文件3；conf - 在 conf 下有个 `conf.d` 的文件夹，把 `default.conf` 上传进去。而 nginx.conf 传到 conf 中。
- 文件4；logs - 创建日志

### 4.4 启动服务

在 nginx.conf 的配置文件有这么一句；`include /etc/nginx/conf.d/*.conf;` 那么只要是 conf.d 文件夹下的文件都会被加载。所以直接在 conf.d/default.conf 配置 SSL 就会被加载。接下来重新安装 Nginx 即可。`安装前记得删除 Nginx 你可以用命令【docker stop Nginx、docker rm Nginx】或者在 Portainer 中操作即可`

```shell
docker run \
--name Nginx \
-p 443:443 -p 80:80 \
-v /data/nginx/logs:/var/log/nginx \
-v /data/nginx/html:/usr/share/nginx/html \
-v /data/nginx/conf/nginx.conf:/etc/nginx/nginx.conf \
-v /data/nginx/conf/conf.d:/etc/nginx/conf.d \
-v /data/nginx/ssl:/etc/nginx/ssl/  \
--privileged=true -d --restart=always nginx
```

![](https://bugstack.cn/images/article/devops/dev-ops-nginx-230418-04.png)

## 五、重定向

### 1. default.conf

在 default.conf 中添加如下配置后重启 Nginx 即可；

```shell
location /d5fe/ {
  rewrite ^/d5fe/(.*)$ /$1 break;
  proxy_pass  https://api.x.com;
  proxy_ssl_server_name on;
  proxy_set_header Host api.x.com;
  proxy_set_header Connection '';
  proxy_http_version 1.1;
  chunked_transfer_encoding off;
  proxy_buffering off;
  proxy_cache off;
  proxy_set_header X-Forwarded-For $remote_addr;
  proxy_set_header X-Forwarded-Proto $scheme;
}
```

### 2. auth_request

```shell
server {
    listen       80;
    listen  [::]:80;
    server_name  api.xfg.im;

    rewrite ^(.*) https://$server_name$1 permanent;

}

server {
    listen       443 ssl;
    server_name  api.xfg.im;

    ssl_certificate      /etc/nginx/ssl/9877497_api.xfg.im.pem;
    ssl_certificate_key  /etc/nginx/ssl/9877497_api.xfg.im.key;
    
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
    
    location /abc/ {
    	 auth_request /auth;
         rewrite ^/abc/(.*)$ /$1 break;
         proxy_pass  https://api.x.com;
         proxy_ssl_server_name on;
         proxy_set_header Host api.x.com;
         proxy_set_header Connection '';
         proxy_http_version 1.1;
         chunked_transfer_encoding off;
         proxy_buffering off;
         proxy_cache off;
         proxy_set_header X-Forwarded-For $remote_addr;
         proxy_set_header X-Forwarded-Proto $scheme;
     }
     
     location = /auth {
        # 发送子请求到HTTP服务，验证客户端的凭据，返回响应码
        internal;
        # 设置参数
        set $query '';
        if ($request_uri ~* "[^\?]+\?(.*)$") {
            set $query $1;
        }
        # 验证成功，返回200 OK
        proxy_pass http://207.246.123.*:8090/auth/token?$query;
        # 发送原始请求
        proxy_pass_request_body off;
        # 清空 Content-Type
        proxy_set_header Content-Type "";
     }
    
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }
}
```

---

**其他资料**：[Nginx 简明教程 @dunwu](https://dunwu.github.io/nginx-tutorial/#/nginx-quickstart) - 非常适合学习Nginx配置。

