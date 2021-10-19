---
layout: post
category: itstack-demo-any
title: elasticsearch-head插件安装
tagline: by 付政委
tag: [ddd,itstack-demo-any]
---

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
安装Elasticsearch的head插件，用于方便操作Elasticsearch

>elasticsearch-head 是用于监控 Elasticsearch 状态的客户端插件，包括数据可视化、执行增删改查操作等。elasticsearch-head 插件的安装在 Linux 和 Windows 没什么区别，安装之前确保当前系统已经安装 nodejs 即可。

## 安装环境
1. 安装[node.js](https://nodejs.org/en/download/)并配置环境变量PATH{path:D:\Program Files\nodejs\}

- [nodejs下载](https://nodejs.org/en/download/)
![](https://bugstack.cn/assets/images/pic-content/2019/08/nodejs.png)
- 执行安装，配置环境变量{path:D:\Program Files\nodejs\}
![](https://bugstack.cn/assets/images/pic-content/2019/08/nodejspath.png)
- 查看nodejs版本；node -v
![](https://bugstack.cn/assets/images/pic-content/2019/08/nodejsversion.png)

2. 安装elasticsearch-head
- [下载elasticsearch-head](https://github.com/mobz/elasticsearch-head)
- 将elasticsearch-head放到与elasticsearch同层级文件夹下
- 修改elasticsearch-head/Gruntfile.js
```xml
connect: {
	server: {
		options: {
			port: 9100,
			base: '.',
			keepalive: true
		}
	}
}
```
- 修改elasticsearch-6.2.2/config/elasticsearch.yml *添加配置信息
```xml
http.cors.enabled: true
http.cors.allow-origin: "*"
```

3、启动elasticsearch-head
```java
Microsoft Windows [版本 6.1.7601]
版权所有 (c) 2009 Microsoft Corporation。保留所有权利。

C:\Users\user>node -v
v10.16.0

C:\Users\user>D:

D:\>cd D:\Program Files\elasticsearch\head

D:\Program Files\elasticsearch\head>npm run start

> elasticsearch-head@0.0.0 start D:\Program Files\elasticsearch\head
> grunt server

Running "connect:server" (connect) task
Waiting forever...
Started connect web server on http://localhost:9100

```
**运行结果**
![](https://bugstack.cn/assets/images/pic-content/2019/08/eshead.png)

------------
