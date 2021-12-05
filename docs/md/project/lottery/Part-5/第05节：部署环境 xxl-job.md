---
title: 第05节：部署环境 xxl-job
pay: https://t.zsxq.com/zbUVR7E
---

# 第05节：部署环境 xxl-job

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、运维日志

- 在 Docker 容器中部署 xxl-job，在部署之前我们需要一个 Mysql 的环境，这个环境我们已经在上一章节中安装完成。
- 本章节下载了 xxl-job 2.1.2 对应 SQL 文件已经存放到这里，你可以直接打开使用：[xxl-job.sql](https://codechina.csdn.net/KnowledgePlanet/Lottery/-/blob/master/doc/assets/sql/xxl-job.sql)

## 二、导入SQL

![](/images/article/project/lottery/Part-5/5-01.png)

- 我们使用本地 SQL 链接工具，并创建 xxl-job 库已经导入 xxl-job.sql 文件

## 三、安装 xxl-job

**拉取**

```java
docker pull xuxueli/xxl-job-admin:2.3.0
```

**部署**

```java
docker run -e PARAMS=" --server.port=7397 --spring.datasource.url=jdbc:mysql://172.17.0.6:3306/xxl_job?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8 --spring.datasource.username=root --spring.datasource.password=123456 --xxl.job.accessToken=xdsl3ewi3al1oehxmo68pqxer" -p 7397:7397 -v /logs/xxl-job:/data/applogs --name xxl-job-admin --restart=always  -d xuxueli/xxl-job-admin:2.3.0
```

- 修改成你的数据库服务器IP、账号、密码、accessToken





