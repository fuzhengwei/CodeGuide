---
title: 问答：ChatGPT 回答星球问题
lock: no
---

# 知识星球 + ChatGPT，自动回答星球用户提问

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

2022年12月25日，我把 ChatGPT 通过 API Keys 的对接方式，拉倒了微信群。经过群友热情噼里啪啦的一顿提问后，ChatGPT $18 的配额就被干没了🤨！而且整理群友的提问，其实大部分都是没啥意义的，比如问：“一天吃几顿饭🍚能撑死？” 所以小傅哥希望用这个东西做点有意义的事！

## 一、前言：我要干啥？

经过对 ChatGPT 的了解和使用，尤其是对技术问题的广度和深度回答，某些时候甚至比在浏览器检索还要有用，ChatGPT 可以更精准、更简单、更直接。

**所以**，小傅哥研究着把 ChatGPT 接入到知识星球，当粉丝伙伴需要提问一些常见技术问题时可以直接提问给星球中的 ChatGPT 来回答。而那些星球中的项目学习问题和需要参考小傅哥的过往经验来处理的问题，再提问给小傅哥。

这样一方面可以提高粉丝伙伴的问题回答的时效性，另外一方面也可以帮助小傅哥减少一定的工作量。岂不美哉！说干就干，搞！

## 二、爬虫：要怎么干？

### 1. 设计

🤔 我要开发一个程序，把**知识星球**与**ChatGPT**连接起来！

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-openai-01.png?raw=true" width="550">
</div>

左侧是**知识星球**，右侧是**ChatGPT**的**OpenAI**。我希望通过我开发的这个应用程序，从知识星球拉取用户提给我的问题，之后把问题塞给**OpenAI**，得到答案以后再推给知识星球中提问者。

### 2. 开发

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-openai-02.png?raw=true" width="750">
</div>

反手就搭建了一个 ZSXQ-Api SpringBoot 领域驱动设计 DDD 架构工程，在工程中封装知识星球 API 以及 OpenAI 调用 API，当然这里还得有一个定时的随机任务来处理需要回答的问题。

哈哈哈，小傅哥这里留了个心眼。不能让程序一直有规律的跑，也不能半夜还在跑。首先这样的调用可能会触发风控机制，也会让你自己的 OpenAI 大量消耗。虽然知识星球或者任何一个网站你都可以只用自己的信息模拟浏览器行为，但也不能作死。

### 3. 部署

接下里就是打包镜像文件和部署 Docker 了，你可以在自己的 Docker中跑，有钱的也可以放到云服务器上跑。

```java
# 基础镜像
FROM openjdk:8-jre-slim
# 作者
MAINTAINER xiaofuge
# 配置
ENV PARAMS=""
# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# 添加应用
ADD target/zsxq-api.jar /zsxq-api.jar
# 执行镜像
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /zsxq-api.jar $PARAMS"]
```

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-openai-03.png?raw=true" width="750">
</div>

## 三、机器：回答问题！

在小傅哥的知识星球【码农会锁】中添加了一个叫 @ChatGPT 的机器人，只有对它的提问才会被 OpenAI 回答，其他的提问仍旧是小傅哥来回答。—— **希望用这样的技术手段，帮助到很多小白学习**。

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-openai-07.png?raw=true" width="450">
</div>

---

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-openai-05.png?raw=true" width="750">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-openai-06.png?raw=true" width="750">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-openai-11.png?raw=true" width="750">
</div>

🤔 考虑到 OpenAI 回答问题的频繁性，以及很多问题可能比较初级，所以设定为不提醒。**只回答给提问的用户可见**，所以你对他提问只有你自己会收到回复。


