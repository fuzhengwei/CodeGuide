---
title: 第06节：部署环境 nacos
pay: https://t.zsxq.com/08kipfRje
---

# 第06节：部署环境 nacos

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

- Lottery 分支：[211218_xfg_nacos](https://gitcode.net/KnowledgePlanet/Lottery/-/tree/211218_xfg_nacos) 
- Lottery-ERP 分支：[211218_xfg_nacos](https://gitcode.net/KnowledgePlanet/Lottery-ERP/-/tree/211218_xfg_nacos) 
- 描述：使用 Nacos 注册中心的方式替换原有Dubbo的直连方式和广播模式

## 一、运维日志

- 在 Docker 环境安装 Nacos 2.0.3
- 配置本地应用服务(Lottery、Lottery-ERP)，把 Dubbo 注册到 Nacos，启动服务验证注册和使用

## 二、安装 Nacos

- 拉取镜像：`docker pull nacos/nacos-server`
- 安装部署：`docker run -d -p 8848:8848 --env MODE=standalone  --name nacos  nacos/nacos-server`
- 打开链接：[http://127.0.0.1:8848/nacos/](http://127.0.0.1:8848/nacos/) - `账号：nacos 密码：nacos`

![](/images/article/project/lottery/Part-5/6-01.png)

- 输入账号和密码，登录进来就可以了，安装到这就成功了