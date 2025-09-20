---
title: 钓鱼佬-网页游戏项目
lock: no
---

# 钓鱼佬-网页游戏项目

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>项目：[https://t.zsxq.com/g57ja](https://t.zsxq.com/g57ja)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

哈哈哈，不是干项目，就是在干项目的路上。除了正式的全程视频手把手带着做的，小傅哥也陆续的开始更新了大厂的 PRD 资料、市面上火热的外卖，进行DDD重构编写。这次又带来一个轻量基于 SpringBoot + Redis 实现的， 🎮 🐟 **钓鱼佬，网页游戏项目** 。

这个项目还在线部署过，让伙伴们体验效果。😄 下面有介绍，你可以自己部署体验。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/xfg-fish-pond/xfg-fish-pond-introduction-05.png" width="950px"/>
</div>

**为啥会有这样一个项目呢？🤔**

在互联网应用场景中，京东、淘宝、拼多多等，我们时常会看到一些游戏化玩法的场景，如；集卡、浇水、养动物，通过这些场景增强用户粘性，以及提高转化率。因为这些游戏化场景的背后，还是一套套的营销玩法，最终把一些券、积分、物品发放给用户。

而小傅哥的社群，是按照互联网公司级别的一个项目组进行规划、设计、落地的。在这个社群有非常丰富的项目资源，已经提供的有，业务的 - 交易、拼团、营销、openai应用、ai agent、IM等，组件的 - api网关、扳手工程、动态线程池、透视业务监控、支付sdk组件、IntelliJ IDEA 插件等。

那么为了让小傅哥的社群，给大家提供的项目，是一个有立体化分层关系结构的，可以通过对接形成各个环节的流程闭环。所以小傅哥在全程视频手把手带着做以外，又增加了 PRD 文档、吉祥外卖、这次又增加一个钓鱼佬娱乐的项目。

**基本你在小傅哥的这个社群，就等同于你在一个一线互联网大厂进行一次实习！** 接下来，小傅哥来介绍下这个小游戏项目。

## 一、运行效果

该项目是一个轻量的网页游戏应用项目，提供了 Java、Python 2个版本，数据使用 Redis 进行存储。以娱乐化的方式，让伙伴学习到 Redis 的相关技术。此外，你可以把这样的一个项目，与星球里的大营销（积分、兑换、返利、抽奖）进行对接使用。在描述上，是以调研了xxx互联网公司的xxx游戏化场景，进行设计实现的这样一整套系统。

### 1. 登录

<div align="center">
	<img src="https://bugstack.cn/images/article/project/xfg-fish-pond/xfg-fish-pond-introduction-01.png" width="350px"/>
</div>

### 2. 画鱼

<div align="center">
	<img src="https://bugstack.cn/images/article/project/xfg-fish-pond/xfg-fish-pond-introduction-02.png" width="350px"/>
</div>

### 3. 鱼塘

<div align="center">
	<img src="https://bugstack.cn/images/article/project/xfg-fish-pond/xfg-fish-pond-introduction-03.png" width="950px"/>
</div>

- 鱼塘页面，可以看到所有登录用户投放进来的鱼，还可以钓鱼🎣。也许会把别人的鱼钓鱼到自己的鱼篓里。当然，钓鱼是需要积分的，你需要点击签到获得积分。
- 页面里还有一些问题的话术和救赎时间（上班摸鱼等于救赎自己！）
- 本次设计的页面和后端的代码，都比较简单，后端也仅有一层架构。如果想添加功能，可以自己实现，也可以使用 trae.ai、Cursor 等工具，来辅助开发。
- 哈哈哈，还有伙伴提建议，让这些鱼，可以支持在线售卖。如果你还有很多想法，可以拿到这套代码，做一些扩展。

### 4. 居民

<div align="center">
	<img src="https://bugstack.cn/images/article/project/xfg-fish-pond/xfg-fish-pond-introduction-04.png" width="350px"/>
</div>

在鱼塘居民下，可以看到各个伙伴画的各种鱼。你可以对这些鱼点一个喜欢或者不喜欢。

## 二、项目介绍

### 1. 工程代码

<div align="center">
	<img src="https://bugstack.cn/images/article/project/xfg-fish-pond/xfg-fish-pond-introduction-06.png" width="350px"/>
</div>

- 地址：[https://gitcode.net/KnowledgePlanet/xfg-fish-pond](https://gitcode.net/KnowledgePlanet/xfg-fish-pond)  - `文末加入小傅哥社群，即可获得全部代码，以及其他17个实战项目`
- 说明：项目提供了前端代码，SpringBoot 服务端代码，以及一个 python 版本的代码。

### 2. 项目启动

<div align="center">
	<img src="https://bugstack.cn/images/article/project/xfg-fish-pond/xfg-fish-pond-introduction-07.png" width="850px"/>
</div>
- 首先，在 xfg-fish-pond-app 下，resources/application-dev.yml 配置 redis 链接信息。
- 之后，启动项目后，点击打开页面即可访问了。如果你要部署云服务器，可以通过 docker 构建镜像在云服务器部署即可。

> 如果你正在做星球的项目，那么很多都可以与这套项目做微服务对接。如；大营销的积分、兑换、返利、抽奖，也可以把 ai 场景对接进来，还可以把小型支付对接进来，买卖鱼的交易。

## 三、技术学习

本套项目的数据存储都是基于 Redis 实现的，你可以在这套代码中学习到非常多的 Redis 应用技术。

### 1. 用户会话管理（UserController）

**登录时存储用户信息：**
```java
RMap<String, String> userMap = redisService.getMap(userKey);
userMap.put("userId", userId);
userMap.put("username", username);
userMap.put("loginTime", LocalDateTime.now().toString());
userMap.expire(86400, TimeUnit.SECONDS); // 24小时过期
```

**用户列表管理：**
```java
redisService.addSetMember(USER_LIST_KEY, userId); // 添加到在线用户列表
redisService.removeSetMember(USER_LIST_KEY, userId); // 从在线用户列表移除
```

### 2. 积分系统（PointsController）

**积分存储和操作：**
```java
// 获取用户积分
int points = Integer.parseInt(pointsMap.getOrDefault("points", "0"));

// 更新积分
pointsMap.put("points", String.valueOf(newPoints));
pointsMap.put("lastUpdated", LocalDateTime.now().toString());
```

**签到记录：**
```java
String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
RMap<String, String> signMap = redisService.getMap(signKey);
signMap.put(today, LocalDateTime.now().toString());
```

**积分变动记录：**
```java
String recordValue = points + "|" + type + "|" + reason + "|" + LocalDateTime.now().toString();
recordsMap.put(recordId, recordValue);
```

### 3. 鱼类管理（FishController）

**添加鱼到池塘：**
```java
RMap<String, String> fishMap = redisService.getMap(fishKey);
fishMap.put("name", fishName);
fishMap.put("emoji", emoji);
fishMap.put("userId", userId);
fishMap.put("createTime", LocalDateTime.now().toString());

// 添加到鱼类列表
redisService.addSetMember(FISH_LIST_KEY, fishId);
```

### 4. 钓鱼系统（FishingController）

**钓鱼记录：**
```java
String recordValue = success + "|" + fishEmoji + "|" + fishName + "|" + fishRarity + "|" + 
                   LocalDateTime.now().toString() + "|" + transferred + "|" + 
                   originalOwner + "|" + fishId;
recordsMap.put(recordId, recordValue);
```

**鱼篓管理：**
```java
String fishValue = emoji + "|" + name + "|" + rarity + "|" + 
                  LocalDateTime.now().toString() + "|" + originalFishId + "|" + image;
basketMap.put(fishId, fishValue);
```

### 5. 投票系统（VoteController）

**用户投票记录：**
```java
RMap<String, String> userVotesMap = redisService.getMap(userVotesKey);
userVotesMap.put(fishId, voteType); // "like" 或 "dislike"
```

**投票统计：**
```java
fishVotesMap.put("likes", String.valueOf(currentLikes));
fishVotesMap.put("dislikes", String.valueOf(currentDislikes));
fishVotesMap.put("score", String.valueOf(currentLikes - currentDislikes));
```

### 6. 系统监控（SystemController）

**Redis 健康检查：**
```java
// 设置测试值
redisService.setValue("health_check", "ok");

// 读取测试值
String value = redisService.getValue("health_check");

// 删除测试值
redisService.remove("health_check");

// 获取用户总数
Map<String, String> userList = redisService.getMapAll("user_list");
```

好啦，如果你现在需要实战项目锻炼自己的能力，积累自己的储备，那么一定要加入小傅哥知识星球（码农会锁）。这里有非常多的类型丰富的项目，带着你一起起飞🛫！

