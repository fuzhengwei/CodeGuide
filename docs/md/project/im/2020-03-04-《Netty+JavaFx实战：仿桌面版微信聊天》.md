---
title: 《Netty+JavaFx实战：仿桌面版微信聊天》
excerpt: 本专栏是作者小傅哥使用JavaFx、Netty4.x、SpringBoot、Mysql等技术栈和偏向于DDD领域驱动设计方式，搭建的仿桌面版微信聊天工程实现通信核心功能。
---

# 《Netty+JavaFx实战：仿桌面版微信聊天》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>项目：[https://articles.zsxq.com/id_uwf9xgmjczg3.html](https://articles.zsxq.com/id_uwf9xgmjczg3.html) - `加入知识星球可以获得多套学习项目`

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、前言

>本项目是作者小傅哥使用```JavaFx```、```Netty4.x```、```SpringBoot```、```Mysql```等技术栈和偏向于DDD领域驱动设计方式，搭建的仿桌面版微信聊天工程实现通信核心功能。如果本项目能为您提供帮助，请给予支持(关注、点赞、分享)！

雨后天晴写下、年华，<br/>
巫山云景彩霞、如画。<br/>
心似平远走**码**、飞驾，<br/>
整装年少风华、正恰。<br/>

走过了一个漫长的假期，从年假的第一天开始因为不能但又不能让自己太闲，就开始研究将所学的```Netty```技术实践一把，以此来巩固不同类型的技术栈在实际业务中的使用。那么使用Netty仿微信项目就此开始了！

---

任何一个新技术栈的学习过程都会包括这样一条路线；运行HelloWorld、熟练使用API、项目实践以及最后的深度源码挖掘。 那么在听到这样一个需求时候，Java程序员肯定会想到一些列的技术知识点来填充我们项目中的各个模块，例如；界面用JavaFx、Swing等，通信用Socket或者知道Netty框架、服务端控制用MVC模型加上SpringBoot等。但是怎么将这些各个技术栈合理的架设出我们的系统确是学习、实践、成长过程中最重要的部分。

可能很多的小伙伴在面试求职的过程中，都会看到招聘要求有些 Netty，这主要是因为 Netty 是一款非常优秀的NIO框架，并且应用非常广泛。无论在互联网、大数据以及通信和游戏行业中，都有Netty的身影。比如一线大厂阿里的RPC框架，Dubbo 协议默认使用 Netty 作为基础通信组件，用于各节点间的内部通信。淘宝的消息中间件 RocketMQ 的消息生产者与消费者，也是采用 Netty 作为高性能、异步通信组件。除了阿里系、淘宝系，其他很多一线大厂也都在使用Netty构建高性能、分布式的网络服务。

因此我们非常有必要学习 Netty，那么为了让大家更好的快速学习上手，我们拿一个熟悉又有趣的场景“PC端微信聊天”作为我们的目标项目。来让 Java 程序员使用自己熟悉的编程语言加上JavaFx、Netty4.x、SpringBoot、Mysql等技术栈和偏向于DDD领域驱动设计方式，搭建的仿桌面版微信聊天工程实现通信核心功能。从而学会Netty的同时，也做出合理的架构和干净的编码。

## 二、工程源码

![](https://bugstack.cn/assets/images/2020/project.jpg)

## 三、功能概述

本专栏会以三个大章节内容，逐步进行讲解；

**第一章节**：**UI开发**。使用```JavaFx```与```Maven```搭建UI桌面工程，逐步讲解登录框体、聊天框体、对话框、好友栏等各项UI展示及操作事件。从而在这一章节中让Java 程序员学会开发桌面版应用。

**第二章节**：**架构设计**。在这一章节中我们会使用DDD领域驱动设计的四层模型结构与Netty结合使用，架构出合理的分层框架。同时还有相应库表功能的设计。相信这些内容学习后，你一定也可以假设出更好的框架。

**第三章节**：**功能实现**。这部分我们主要将通信中的各项功能逐步实现，包括；登录、添加好友、对话通知、消息发送、断线重连等各项功能。最终完成整个项目的开发，同时也可以让你从实践中学会技能。

---

![](https://bugstack.cn/assets/images/2020/p-xmind.png)

## 四、项目演示

>登陆页面

![登陆页面](https://bugstack.cn/assets/images/2020/ui-00.png)

>聊天页面

![聊天页面](https://bugstack.cn/assets/images/2020/ui-01.png)

>添加好友

![添加好友](https://bugstack.cn/assets/images/2020/ui-02.png)

>消息提醒

![消息提醒](https://bugstack.cn/assets/images/2020/ui-05.png)

## 五、专栏学习

>专栏共有25篇文章，分别从UI、架构到功能实现逐步讲解，非常适合新人学习提升编码能力和架构思想。

>[🧧加入学习](https://bugstack.cn/md/zsxq/other/join.html)

## 六、优秀作业

- [Java仿微信对接小傅哥ChatGPT-SDK-Java实现智能机器人 @俗人](https://t.zsxq.com/10B7yE8xw)
- [借助 JDK1.8 中的 javapackager.exe 和 IDEA 生成 IM 可运行文件 @俗人](https://t.zsxq.com/10IkHjbpm)
- [JavaFX打包exe @俗人](https://t.zsxq.com/11ybJvpPf)