---
title: 第4节：Nginx环境配置
pay: https://t.zsxq.com/0dcdSvckQ
---

# 《ChatGPT 微服务应用体系构建》 - dev-ops 第4节：Nginx环境配置

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★☆☆☆☆
- **本章重点**：通过本章节内容的学习，教会读者配置Nginx环境
- **课程视频**：[https://t.zsxq.com/0dEZlWkOC](https://t.zsxq.com/0dEZlWkOC)

## 一、常用命令

- 停止：`docker stop Nginx`
- 重启：`docker restart Nginx`
- 删除服务：`docker rm Nginx`
- 删除镜像：`docker rmi Nginx`
- 进入服务：`docker exec -it Nginx /bin/bash`

## 二、基础安装

<div align="center">
	<img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-dev-ops-04-02.png?raw=true" width="850px"/>
</div>

```java
docker run \
--restart always \
--name Nginx \
-d \
-p 80:80 \
nginx
```

<div align="center">
	<img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-dev-ops-04-01.png?raw=true" width="750px"/>
</div>

- restart 重启策略，always 是一直保持重启。如果不设置，可以把这条删掉。`never\always`
- 第1个 `80` - 容器端口、第2个 `80` - 服务器端口，这样外部通过80端口即可访问。