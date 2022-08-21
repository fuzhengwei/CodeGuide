---
title: IDEA Plugin 开发手册
lock: no
---

# IDEA Plugin 开发手册

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`💥为什么？写写快乐的热门文章不好吗！`

从开始准备成体系的编写 `IDEA Plugin` 知识内容前，我就知道这大概率不会是一个有太多阅读量的文章，因为基本日常的工作开发中几乎也用不到这样的知识。

那么为什么还要编写呢？就是因为用的人不多，所以这方面的知识成体系的少，也就导致真的有需要的人根本找不到一个可以上手的资料。*怎么开发*、*什么模式*、*哪种技术*、*如何发布* 等等，这些内容几乎就是空白的，在你有此类需求的时候完全不知道如何上手。

所以🌶，又一套**成系列体系**的`《IDEA Plugin 开发手册》`内容已经为有需要的你准备好啦：

![](https://bugstack.cn/images/article/knowledge/knowledge-220123-01.png)

- 此开发手册，分为4章12节循序渐进的通过实践案例开发的方式，串联 IDEA Plugin 开发的各项常用技术点，为读者讲解如何开发一个 IDEA 插件。
- 基本开发类知识点包括：`gradle 工程创建`、`插件发布`、`Swing UI`、`各类窗体`、`菜单配置`、`工程上下文对象`、`向导步骤`、`内容存放`、`配置加载`等，通过这些知识在案例中的逐个使用，而学习如何开发插件。

💋`鉴于作者水平有限`，如果PDF中含有不易理解的内容，一定是作者在编写的过程中缺少必要的描述和严格的校准，感谢把你的意见或者疑问提交给我来完善，也欢迎与我多一些交互，互相进步共同成长。

## 二、能干啥，举个栗子🌰

`vo2dto，一个已经被下载1000+的插件`

![](https://bugstack.cn/images/article/knowledge/knowledge-220123-02.png)

这是小傅哥开发的一款用于帮助使用 IDEA 编写代码的研发人员，快速生成两个对象转换过程中所需要大量的 `x.set(y.get)` 代码块的 vo2dto 插件工具。*可以直接在 IDEA 中搜索安装使用*

| `对vo2dto感兴趣的，程序员👨🏻‍💻‍，来自这些国家` |
|:---:|
| ![](https://bugstack.cn/images/article/knowledge/knowledge-220123-03.png) |

- 插件：[https://plugins.jetbrains.com/plugin/18262-vo2dto](https://plugins.jetbrains.com/plugin/18262-vo2dto)
- 源码：[https://github.com/fuzhengwei/vo2dto](https://github.com/fuzhengwei/vo2dto)
- 视频：[https://www.bilibili.com/video/BV13Y411h7fv](https://www.bilibili.com/video/BV13Y411h7fv) - `讲解插件的整体设计和使用说明`

## 三、别说了，上干货吧！

![](https://bugstack.cn/images/article/knowledge/knowledge-220123-04.png)

**Hello, world of idea plugin ！**  你好，IDEA 插件的世界！欢迎来到这里，很高兴你能拿到这本书！

IDEA 插件开发可以帮助研发人员提升能效，解决一些实际场景中的共性问题。但最近在折腾IDEA插件开发的时候，市面的资料确实不多，也没有成体系完整的开发指导手册，所以就遇到了很多不知道就不会的事情，需要一点点查询搜索源码、验证API接口，最终把各项功能实现，当然在这个过程中也确实踩了不少坑！接下来在这个专栏会把一些关于 IDEA 插件开发用到的各项知识做成案例输出出来，帮助有需要的研发伙伴，一起建设 IDEA Plugin。

### 1. 适合人群

1. 具备一定编程基础，工作1-3年的研发人员
2. 有 IDEA Plugin 开发需求的研发人员
3. 希望可以拓展一些除了业务以外的开发技能
4. 想做一些开源软件的贡献人员

### 2. 我能学到什么

1. 看得懂，有很多的案例来串联 IDEA Plugin 插件开发技能
2. 学得会，通过案例实践的方式学习 IDEA Plugin 开发技巧
3. 搞得清，不只是实践，还是实际场景的结合
4. 弄得明，学习完这套插件开发技巧，就可以自己完成一些场景设计和开发了

### 3. 阅读建议

此专栏是以案例串联 IDEA Plugin 插件开发中常用的技巧，在学习的过程中可以先着重案例实践，在去考虑如何设计和开发，以及已经上手后再去阅读一些核心的API以及如PMD插件的开发，学习各项技术补充自己的知识。

粉丝伙伴在阅读的过程中，**千万不要害怕在学习的过程中遇到问题，这些都是正常的！** 希望你可以一直坚持把这些内容事必躬亲、亲历亲为的学完，加油！

## 四、PDF📚下载

**版权说明**：`作者：小傅哥`的原创PDF书籍[《IDEA Plugin 开发手册》](https://t.zsxq.com/ufmQnA2)，已发布知识星球(`码农会锁`)和CSDN下载平台，享受版权保护，感谢支持和理解。

### 1. 可获得内容包括

1. 《IDEA Plugin 开发手册》PDF 完整版书籍一本
2. 完整版源码一份，共 12 个案例
3. 可以加入`IDEA Plugin`专栏讨论群，添加我的微信：`fustack`，备注：`IDEA Plugin`

### 2. 下载方式

- CSDN：[https://download.csdn.net/download/Yao__Shun__Yu/77484299](https://download.csdn.net/download/Yao__Shun__Yu/77484299) - `￥4.9`
- 知识星球(`码农会锁`)：[https://t.zsxq.com/ufmQnA2](https://t.zsxq.com/ufmQnA2) - `知识星球用户可直接免费下载，不需要单独付费。此外知识星球还提供了简历优化、实战DDD秒杀项目、架构设计、PPT画架构等内容`

## 五、🎉收尾

`🏃🏻总有些美景，在跑步的早上`

|                              春                              |                              夏                              |                              秋                              |                              冬                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](https://bugstack.cn/images/article/knowledge/knowledge-220123-05.png) | ![](https://bugstack.cn/images/article/knowledge/knowledge-220123-06.png) | ![](https://bugstack.cn/images/article/knowledge/knowledge-220123-07.png) | ![](https://bugstack.cn/images/article/knowledge/knowledge-220123-08.png) |

这是过年前的2021年最后一本 PDF 收尾之作的发布了，这一年在内容输出上包括了：[`《SpringBoot 中间件的设计和开发》`](https://bugstack.cn/md/project/springboot-middleware/2021-03-31-%E3%80%8ASpringBoot%20%E4%B8%AD%E9%97%B4%E4%BB%B6%E8%AE%BE%E8%AE%A1%E5%92%8C%E5%BC%80%E5%8F%91%E3%80%8B%E4%B8%93%E6%A0%8F%E5%B0%8F%E5%86%8C%E4%B8%8A%E7%BA%BF%E5%95%A6%EF%BC%81.html)、[`《重学Java设计模式》`出版图书](https://mp.weixin.qq.com/s/g9LYQEqzOeiYOpfG_5XFYg)、[`《手撸 Spring》`](https://mp.weixin.qq.com/s/kYio8zIG5UL-To3SV-uRmA)、[`《Lottery 抽奖系统 - 基于领域驱动设计的四层架构实践》`](https://bugstack.cn/md/project/lottery/introduce/Lottery%E6%8A%BD%E5%A5%96%E7%B3%BB%E7%BB%9F.html)、[`《IDEA Plugin 开发手册》`](https://download.csdn.net/download/Yao__Shun__Yu/77484299)，哈哈哈，是不是就挺肝的！当一个原创做作者有了粉丝的正向反馈、有了平台的扶持、有了一些收益，就可以非常好的在喜欢的领域上不断的耕作。

**当看着一个个从发芽🌱到长大的内容🌲**，真的是非常的开心。这个过程也是我这一年每天能 10:45-11:00 睡觉，6:20 起床(跑步、写作)，以此保持一个良好的作息习惯，有了健康的身体、有了内容的沉淀。也希望看到的这你，在22年有一个自己的计划，能落地的计划！
