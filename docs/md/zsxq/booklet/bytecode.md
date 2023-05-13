---
title: 字节码编程
lock: no
---

# 字节码编程 | ASM、Javassist、Byte-Buddy

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>星球：[https://t.zsxq.com/0eHOK4Ad2](https://t.zsxq.com/0eHOK4Ad2) - 课程入口

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

**让人怪不好意思的**，说是出书有点膨胀💥，毕竟这不是走出版社的流程，选题、组稿、编著、审读、加工到出版发行。

**但全书共计107页，11万7千字，20个章节涵盖三个字节码框架和JavaAgent使用并附带整套案例源码！**

<div align="center">
    <img src="https://bugstack.cn/assets/images/illustration/让人怪不好意思的.png?raw=true" width="250px">
</div>

**讲道理**，市面上以及网络搜索中都基本很少有成体系的关于字节码编程的知识，这主要由于大部分开发人员其实很少接触这部分内容，包括；`ASM`、`Javassist`、`Byte-buddy`以及`JavaAgent`，没有很大的市场也就没有很多的资料。但大家其实已经从其他的框架或者中间件中使用到，就像你用到的；Cglib、混沌工程、非入侵的全链路监控以及你是否使用过`jetbrains-agent.jar`做了某项实验？

<div align="center">
    <img src="https://bugstack.cn/assets/images/illustration/上号Idea.png?raw=true" width="250px">
</div>

所以这样的技术栈一直都萦绕在你身边，只是你还没有发现！当有一天面试问到了，那时你已经170斤工作五年。

**蹭个车告诉你这个知识的重要性**，阿里云的挑战赛！

<div align="center">
    <img src="https://bugstack.cn/assets/images/2020/itstack-demo-bytecode-0-3.png?raw=true" width="550px">
</div>

`读不在三更五鼓，功只怕一曝十寒！`，不一定一本书中就能读出个黄金屋，但脚下路的用什么垫都是自己日积月累。

## 就这本书他出炉了

<div align="center">
    <img src="https://bugstack.cn/assets/images/2020/itstack-demo-bytecode-0-2.png?raw=true" width="750px">
</div>

## 介绍

初识字节码编程是从使用非入侵的全链路监控开始，在这之前我所了解的如果需要监控系统的运行状况，通常需要硬编码埋点或者AOP的方式采集方法执行信息；耗时、异常、出入参等来监控一个系统的运行健康度。而这样的监控方式在大量的系统中去改造非常耗时且不好维护，更不要说去监控一个业务流程的调用链路。

在2010年的时候，谷歌发布一篇名为《Dapper, a Large-Scale Distributed Systems Tracing Infrastructure》的论文，在文中介绍了谷歌生产环境中大规模分布式系统下的跟踪系统`Dapper`的设计和使用经验。

这样的监控系统采用 `Javaagent` 与字节码操作框架结合使用，在应用系统加载时对需要监控的方法进行字节码增强也叫插桩。对方法处理后的结果就和你之前硬编码类似，但这样就可以减轻认为操作，同时可以对多个系统之间定义调用链路ID进行串联业务流程关系。最终，极大减轻了监控成本也提高了线上问题的快速定位和处理。

这里面监控系统核心知识也主要是 `Javaagent`和字节码操作，在字节码操作中目前有三个比较常用的框架；`ASM`、`Javassist`、`Byte Buddy`，这几个框架都能进行字节码操作，其中`ASM` 更偏向于底层，需要了解字节码指令以及操作数栈等知识，最好学习过《Java虚拟机规范》等书籍，另外两个框架是对 `ASM` 的封装，提供更加高级的API去操作字节码。

在本书中`小傅哥`会分别讲解这三种字节码框架的使用，以及最终与`Javagent`结合完成全链路监控的案例。通过这样的学习让你可以从有抓手的从案例开始，把枯燥的字节码编程融入场景，深化理解和实操应用。也能让你忙于CRUD开发的同时提升自己的知识栈，拓展技术视野。也许不久以后这项技术也能为你带来一些有价值的收获！

## 作者

作者小傅哥多年从事一线互联网 Java 开发，热衷于对学习历程做技术汇总，侧重点更倾向编写 Java 核心内容。旨在为大家提供一个清晰详细的学习教程也帮助自己不断沉淀。所以投入时间学习、整理、编写相关的资料，如果我的文章或书籍能为您提供帮助，请给予**支持**(关注、点赞、分享)！

**如何支持：**

- 关注公众号： [`bugstack虫洞栈`](https://bugstack.cn/assets/images/qrcode.png)
- 收藏我博客：[`bugstack.cn`](https://bugstack.cn/)
- 分享给您身边的小伙伴
- 还可以给我开源的项目点个星星🦍  「`CodeGuide | 程序员编码指南`」- [`https://github.com/fuzhengwei/CodeGuide/wiki`](https://github.com/fuzhengwei/CodeGuide/wiki)

*如果这些都做了！记得加我`微信(fustack)`*，交个朋友！

## 下载

- [https://t.zsxq.com/05RRJUnUN](https://t.zsxq.com/05RRJUnUN)

## 收个尾

头一次把系列文章写成书，虽然免费发布，但也可能在获取书籍下载以及学习过程中发现我写错字以及写错某个名称`想喷我`🤮，如果你有此冲动！请添加小傅哥微信(`公众号：bugstack虫洞栈获取`)，我会用我的技术魅力和爆炸人品感化你，并修改我的书籍内容，📝记录你的功绩到：[https://github.com/fuzhengwei/CodeGuide/wiki](https://github.com/fuzhengwei/CodeGuide/wiki)

如果你在阅读本书的过程中有些地方不是很容易理解，不要担心一定作者没有描述的更加清楚。很多知识或者系统建设并不难，只是有时候被描述的麻烦了。所以我也非常愿意与你一起去学习这部分知识，在讨论中不断把问题梳理的更加清晰，用更易懂的方式剖析问题的本质。

**最后，希望同好编程开发的你不只是CRUD的工具人，多多拓展技术栈夯实基本功。共勉！加油！**