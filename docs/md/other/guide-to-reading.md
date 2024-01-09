---
title: 编码指南
---

# bugstack虫洞栈 | 程序员的编码指南 🔥

>你好，我是小傅哥，[《重学Java设计模式》](https://item.jd.com/13218336.html) 图书作者，一线互联网 Java 工程师、架构师。

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=298298421&bvid=BV1FF41137q5&cid=578551972&page=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

一个着迷于技术又喜欢不断折腾的技术活跃者，从13年毕业到进入互联网，开发过交易、营销类项目，实现过运营、活动类项目，设计过中间件，组织过系统重构，编写过技术专利。不仅从事业务系统的开发工作，也经常做一些字节码插桩类的设计和实现，对架构的设计和落地有丰富的经验。在热衷于Java语言的同时，也喜欢研究中继器、I/O板卡、C#和PHP！

除此之外小傅哥并不只满足于CRUD搬砖，也关心业务、运营、产品、数据、测试、运维等各项知识体系的完善学习，就研发架构设计来讲，更全面的学习会更有利于做出更长远的架构设计。同时完善个人知识体系也更有利于个人成长。

所以你会看到小傅哥在工作之外的深夜、周末、假期会折腾于写文章、编小册、出书籍，并十分热情于对粉丝的交流、提问、解惑。并不深沉且少许逗比的我，希望能给大家带来最接地气的帮助和成长。

---

⏰ **提醒**：在接下来你对本博客的阅读中，如果遇到一些`内容`、`图稿`、`代码`等中的勘误都可以提交Issue或者PR的方式进行反馈，小傅哥会陆续进行完善，感谢您的支持；
- Issue：[https://github.com/fuzhengwei/CodeGuide/issues](https://github.com/fuzhengwei/CodeGuide/issues)
- PR：[https://github.com/fuzhengwei/CodeGuide/pulls](https://github.com/fuzhengwei/CodeGuide/pulls) - 你只需要在阅读文章的最下面找到`在 GitHub 上编辑此页`，即可完成克隆和提交PR
- 考试：[100道八股题考试测验](https://bugstack.cn/md/zsxq/material/exam.html) —— 你可以尝试验证自己的能力，考题范围：数据结构、算法、源码、设计模式、系统架构、中间件、网络通信、实战项目、扩展问题
- 本站为公众号往期文章整理的小册，关注`公众号`：[bugstack虫洞栈](https://bugstack.cn/images/personal/qrcode.png) 可以知晓最新推文，避免错过最近正在更新的技术系列文章。
- 其他：如果你在学习本站内容遇到不能解决的问题，可以联系作者：`小傅哥`，微信：`fustack` - 交个朋友👬🏻，不要错过成为技术同好的机会。
- 加入星球：[码农会锁](https://bugstack.cn/md/zsxq/introduce.html) - 你可以获得本站所有学习内容的**指导**和**帮助**，还可以学习实战项目！`☕️一顿饭钱的支持，突破技术瓶颈` **小妙招**：关注公众号：[bugstack虫洞栈](https://bugstack.cn/images/personal/qrcode.png) 回复：`星球` 可以获取优惠券

<div align="center">
    <img src="https://bugstack.cn/images/system/zsxq/xingqiu-231018-01.png?raw=true" width="700px">
    <div style="font-size: 12px;"><a href="https://t.zsxq.com/Ja27ujq">星球介绍：码农会锁 - 实战项目「大营销平台、OpenAi、Lottery、Api网关等」、专属小册、问题解答、简历指导、架构图稿、视频课程</a></div>
</div>

## 一、本站知识阅览

我给自己在技术职业成长上，定位成一个能抗住`农夫三拳`的架构师，所以我在编写和输出的技术内容上，也是以数据结构、算法逻辑、设计模式、核心技术、系统架构、服务运维等方面的知识扩展技术广度和深度，并以实践验证的学习方式进行汇总内容编写文章。也希望这些成体系的技术系列内容能帮助你慢慢且踏实的成长起来。

![](https://bugstack.cn/images/system/overview.png)

而这几大块内容也是每一个较贵的 Java 程序员应该掌握的内容，可以包括：

- **Java&Spring**：以讲解Java、Spring核心知识为基础，用数学逻辑思维分析关于Java、Spring、Mybatis、Dubbo等核心源码技术内容。其中如[《Java 面经手册》](https://bugstack.cn/md/project/pdf/2021-01-26-Java%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8CPDF%E4%B8%8B%E8%BD%BD.html)是一本以面试题为入口讲解 Java 核心内容的技术书籍，书中内容极力的向你证实代码是对数学逻辑的具体实现。包括正在编写的[《手撸 Spring》](https://bugstack.cn/md/spring/develop-spring/2021-05-16-%E7%AC%AC1%E7%AB%A0%EF%BC%9A%E5%BC%80%E7%AF%87%E4%BB%8B%E7%BB%8D%EF%BC%8C%E6%89%8B%E5%86%99Spring%E8%83%BD%E7%BB%99%E4%BD%A0%E5%B8%A6%E6%9D%A5%E4%BB%80%E4%B9%88%EF%BC%9F.html)通过手写简化版 Spring 框架，了解 Spring 核心原理。在手写的过程中会简化 Spring 源码，摘取整体框架中的核心逻辑，简化代码实现过程，保留核心功能，例如：IOC、AOP、Bean生命周期、上下文、作用域、资源处理等内容实现。这些都程序员学习技术成长过程中非常重要的知识，如果能深入学习那么对以后的个人成长帮助非常大。
- **算法逻辑和数据结构**：这部分内容主要以Java源码为入手，讲解其中的数学知识，包括：扰动函数、负载因子、拉链寻址、开放寻址、斐波那契（Fibonacci）散列法还有黄金分割点的使用等等，这也正是[《Java 面经手册》](https://bugstack.cn/md/project/pdf/2021-01-26-Java%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8CPDF%E4%B8%8B%E8%BD%BD.html)的核心内容所在。
- **面向对象**：[《Java 设计模式》](https://item.jd.com/13218336.html)的知识是在Java基础铺平，数据结构、算法逻辑有了一定的了解后，在深入学习和使用的技术。同样是一个需求在学过设计模式后，也阅读了不少别人优秀的代码，那么在他实现需求的时候，会拆分出很多的接口和接口的继承、抽象类的职责隔离实现、具体业务模块的分层、功能服务组件的细化、具体实现过程中对设计模式的运用等等。这样的代码实现后会非常具有易扩展和可维护的特点，否则一篇的ifelse不是坑自己就是坑下一个人。
- **中间件**：可能很大一部分研发并不会接触到中间件，也不太可能有人告诉你可以使用中间件的方式解决一些实际遇到的问题。因为大部分时候你都会认为中间件只是公司专门部门的人写的，或者是技术大牛搞的，总之与你没关系。但其实代码知识对数学逻辑的具体实现，业务开发有业务开发的方式，[《Spring 中间件和开发》](https://bugstack.cn/md/project/springboot-middleware/2021-03-31-%E3%80%8ASpringBoot%20%E4%B8%AD%E9%97%B4%E4%BB%B6%E8%AE%BE%E8%AE%A1%E5%92%8C%E5%BC%80%E5%8F%91%E3%80%8B%E4%B8%93%E6%A0%8F%E5%B0%8F%E5%86%8C%E4%B8%8A%E7%BA%BF%E5%95%A6%EF%BC%81.html)也只是对Spring的关于容器中一些特定接口和类的使用，具体的还是普通的逻辑代码，比如暴露服务、采集日志、监控系统等。但如果你能早些学到这样技术的核心思想，那么对于升值、加薪、跳槽，都是非常有帮助的。
- **通信专题**：其实Netty是一项非常重要的技术，比如在RPC服务实现中的Dubbo、或者MQ、以及很多时候的通信里都是能用到的技术。就连小傅哥的第一次面试大厂也是靠着对Netty的学习，刷进来的！所以小傅哥编写了很多Netty从基础入门讲解到核心原理，告诉你如何处理半包、粘包，怎样定义消息协议，并开发了一个基于Netty的仿微信聊天项目，这些技术内容你都可以在我的博客学习到学习到。
- **字节码编程**：这项技术可能大多数研发，哪怕35岁的，可能也不一定接触到。但这样的技术你却基本都用过，比如你的IDEA是购买的吗，你怎么给让它能用的！你用过一些非入侵的全链路监控系统的，你通过字节码插桩搞过一些事情吗，那你用过Cglib吧，它的底层就是通过ASM字节码框架对字节码进行的一些列操作。
- **实战项目**：以实战项目的方式学习互联网大厂开发中应用到的技术，通过这样的实践方式把一些学习的技术应用起来，而不是单单的去背资料，但最终不知道该如何把这些技术内容综合起来使用。所以这里小傅哥以实战项目为主，推出：[Lottery 抽奖系统 - 基于领域驱动设计的四层架构实践](https://bugstack.cn/md/project/lottery/introduce/Lottery%E6%8A%BD%E5%A5%96%E7%B3%BB%E7%BB%9F.html)
- **关于**：除了技术学习以外，还有很多伙伴会经常问我一些关于学习、成长以及在职场中怎么活下去。所以我结合我自己在大厂互联网中的学习和成长经历，给读者伙伴写了不少此类的内容。如简历编写、招聘要求、技术资料、代码规范、评审晋升、薪资待遇、副业收入等等。这些内容可能很多会帮助你度过一个安定的职场生涯！

## 二、学习路线参考

可能有很大一部分处于开始成长阶段的码农伙伴，不太清楚自己要从哪里开始学习，哪怕手里有几个T的资料，也没有办法把这些内容吸收到大脑中。因此我把一些非常有必要学习的知识串连出一条学习路径，帮助处于不同阶段的伙伴找到学习方向，也可以把自己的技术水平和工资代码，都拉起来。🌶

![](https://bugstack.cn/assets/images/LearningPath.png)

- Java 面经手册：[https://download.csdn.net/download/Yao__Shun__Yu/14932325](https://download.csdn.net/download/Yao__Shun__Yu/14932325)
- 重学Java设计模式：[https://download.csdn.net/download/Yao__Shun__Yu/19265731](https://download.csdn.net/download/Yao__Shun__Yu/19265731)
- SpringBoot 中间件设计和开发：[https://juejin.cn/book/6940996508632219689](https://juejin.cn/book/6940996508632219689)
- 🔥Lottery 抽奖系统 - DDD 分布式实践：[https://t.zsxq.com/jAi2nUf](https://t.zsxq.com/jAi2nUf)
- [更多项目](https://gaga.plus)

## 三、算法

- 地址：[Java 数据结构和算法](https://bugstack.cn/md/algorithm/data-structures/data-structures.html)
- 介绍：以Java源码为基础，结合使用场景，学习数据结构和算法。涵盖4类14种数据结构，包括：链表、数组、队列、堆栈、哈希表、堆、字典树、二分搜索树、平衡二叉树、2-3树、红黑树、并查集、图、布隆过滤器。

## 四、Java

### 1. Java 面经手册

- 地址：[Java 面经手册](https://bugstack.cn/md/java/interview/2020-07-28-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E5%BC%80%E7%AF%87%E3%80%8A%E9%9D%A2%E8%AF%95%E5%AE%98%E9%83%BD%E9%97%AE%E6%88%91%E5%95%A5%E3%80%8B.html)
- 介绍：全书共计5章29节，417页11.5万字，耗时4个月完成。涵盖数据结构、算法逻辑、并发编程、JVM以及简历和互联网大厂面试等内容。

![](https://bugstack.cn/assets/images/2020/interview/interview-pdf-2.png)

[《Java 面经手册》](https://bugstack.cn/md/project/pdf/2021-01-26-Java%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8CPDF%E4%B8%8B%E8%BD%BD.html) 是一本以面试题为入口讲解 Java 核心技术的 PDF 书籍，书中内容也极力的向你证实`代码是对数学逻辑的具体实现`。*为什么这么说？* 当你仔细阅读书籍时，会发现这里有很多数学知识，包括：扰动函数、负载因子、拉链寻址、开放寻址、斐波那契（Fibonacci）散列法还有黄金分割点的使用等等。

编码只是在确定了研发设计后的具体实现，而设计的部分包括：数据结构、算法逻辑以及设计模式等，而这部分数据结构和算法逻辑在 Java 的核心 API 中体现的淋漓尽致。那么，也就解释了为什么这些内容成为了热点面试题，虽然可能我们都会觉得这样的面试像是造火箭。

### 2. 用Java实现JVM

- 地址：[用Java实现JVM](https://bugstack.cn/md/java/develop-jvm/2019-05-01-%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E4%B8%80%E7%AB%A0%E3%80%8A%E5%91%BD%E4%BB%A4%E8%A1%8C%E5%B7%A5%E5%85%B7%E3%80%8B.html)
- 介绍：本专题按照《java虚拟机规范》、go语言版《自己动手写Java虚拟机》来实现自己的java版虚拟机。从而深入学习jvm的基础功能；搜索和解析class、字节码指令集、运行时数据区、虚拟机栈、栈桢、局部变量表、操作数栈、寄存器等是如何一步步将java代码运行起来的。

### 3. 基础技术

- 地址：[基础技术](https://bugstack.cn/md/java/core/2020-01-06-%5B%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%5D%E5%92%8B%E5%98%9E%EF%BC%9F%E4%BD%A0%E7%9A%84IDEA%E8%BF%87%E6%9C%9F%E4%BA%86%E5%90%A7%EF%BC%81%E5%8A%A0%E4%B8%AAJar%E5%8C%85%E5%B0%B1%E7%A0%B4%E8%A7%A3%E4%BA%86%EF%BC%8C%E4%B8%BA%E4%BB%80%E4%B9%88%EF%BC%9F.html)
- 介绍：讲解 JDK 新特性、JDK 和 CGLIB 反射、Byte 字节、技术扩展、常忽略的技术问题，帮研发同学更好的拉伸自己的技术栈学习。

## 五、Spring

### 1. Spring 手撸专栏

- 地址：[Spring 手撸专栏](https://bugstack.cn/md/spring/develop-spring/2021-05-16-%E7%AC%AC1%E7%AB%A0%EF%BC%9A%E5%BC%80%E7%AF%87%E4%BB%8B%E7%BB%8D%EF%BC%8C%E6%89%8B%E5%86%99Spring%E8%83%BD%E7%BB%99%E4%BD%A0%E5%B8%A6%E6%9D%A5%E4%BB%80%E4%B9%88%EF%BC%9F.html)
- 介绍：在写了部分关于 `Spring核心源码` 的面经内容后，我决定要去手撸一个Spring了。为啥这么干呢？因为所有我想写的内容，都希望它是以理科思维理解为目的方式学会，而不是靠着硬背记住。而编写面经的过程中涉及到的每一篇Spring源码内容分析，在即使去掉部分非主流逻辑后，依然会显得非常庞大。

![](https://bugstack.cn/assets/images/spring/spring-0-03.png)

- 此专栏是一本以开发简化版Spring学习其原理和内核的知识内容，不仅是代码编写实现也更注重内容上的需求分析和方案设计，所以在学习的过程要结合这些内容一起来实践，并调试对应的代码。粉丝伙伴在阅读的过程中，**千万不要害怕在学习的过程中遇到问题，这些都是正常的！** 希望你可以一直坚持把这些内容事必躬亲、亲历亲为的学完，加油！

### 2. Mybatis 手撸专栏

- 地址：[Mybatis 手撸专栏](https://bugstack.cn/md/spring/develop-mybatis/2022-03-20-%E7%AC%AC1%E7%AB%A0%EF%BC%9A%E5%BC%80%E7%AF%87%E4%BB%8B%E7%BB%8D%EF%BC%8C%E6%89%8B%E5%86%99Mybatis%E8%83%BD%E7%BB%99%E4%BD%A0%E5%B8%A6%E6%9D%A5%E4%BB%80%E4%B9%88%EF%BC%9F.html)
- 介绍：像我们之前完成[手写 Spring](https://mp.weixin.qq.com/s/g7YdIe_FSrk-WE8nQRO3TA)一样，拆解功能、简化流程、渐进实现，让读者能够更容易的学习到最有价值的知识。

![](https://bugstack.cn/images/article/spring/mybatis-220320-02.png)

在手写的过程中学习 Mybatis 从解析、绑定、反射、缓存，到会话和事务操作，以及如何与 Spring 进行关联注册 Bean 对象，完成整合部分功能逻辑。通过这些内容的拆解实现，读者伙伴就可以非常清楚的知道这些核心功能都是如何实现的了，以后再阅读 Mybatis 源码也就知道从哪开始到哪结束了。

### 3. Spring Cloud

- 地址：[Spring Cloud](https://bugstack.cn/md/spring/spring-cloud/2019-10-31-Spring%20Cloud%E9%9B%B6%E3%80%8A%E6%80%BB%E6%9C%89%E4%B8%80%E5%81%8F%E6%A6%82%E8%BF%B0%E5%91%8A%E8%AF%89%E4%BD%A0SpringCloud%E6%98%AF%E4%BB%80%E4%B9%88%E3%80%8B.html)
- 介绍：以案例实践的方式讲解 Spring Cloud 中常用的技术内容，包括：Eureka注册中心、熔断、降级、限流、动态刷新配置、消息总线、zuul 网关路由等

### 4. 源码分析

- 地址：[源码分析](https://bugstack.cn/md/spring/source-code/2019-12-25-%5B%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%5DMybatis%E6%8E%A5%E5%8F%A3%E6%B2%A1%E6%9C%89%E5%AE%9E%E7%8E%B0%E7%B1%BB%E4%B8%BA%E4%BB%80%E4%B9%88%E5%8F%AF%E4%BB%A5%E6%89%A7%E8%A1%8C%E5%A2%9E%E5%88%A0%E6%94%B9%E6%9F%A5.html)
- 介绍：拆解、分析、实践的方式进行源码学习，包括对 Mybatis 的分析和手写，这部分内容涵盖的非常广泛，可以让你非常清楚一个 ORM 框架是如何开发的以及怎么结合到 Spring 中，同时这部分内容还包括了 Quartz 定时任务全流程的分析，可以帮助你更好的理解任务的分层处理。

## 六、面向对象

### 1. 重学Java设计模式

- 地址：[重学Java设计模式](https://bugstack.cn/md/develop/design-pattern/2020-05-20-%E9%87%8D%E5%AD%A6Java%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E3%80%8A%E5%AE%9E%E6%88%98%E5%B7%A5%E5%8E%82%E6%96%B9%E6%B3%95%E6%A8%A1%E5%BC%8F%E3%80%8B.html)
- 视频：[https://www.bilibili.com/video/BV1D341177SV](https://www.bilibili.com/video/BV1D341177SV) - `与重学Java设计模式相对应，成体系录制`
- 介绍：本书是作者小傅哥，基于互联网真实案例编写的Java设计模式实践图书。全书以解决方案为核心，从实际开发业务中抽离出交易、营销、规则引擎、中间件、框架源码等22个真实场景，对设计模式进行全面、彻底的分析。帮助读者灵活地使用各种设计模式，从容应对复杂变化的业务需求，编写出易维护、可扩展的代码结构。

| 序号 | 类型         | 图稿                                                         | 业务场景                                             | 实现要点                                                     |
| ---- | ------------ | ------------------------------------------------------------ | ---------------------------------------------------- | ------------------------------------------------------------ |
| 1    | **工厂方法** | ![img](https://bugstack.cn/assets/images/2020/design/11.png) | 多种类型商品不同接口，统一发奖服务搭建场景           | 定义一个创建对象的接口，让其子类自己决定实例化哪一个工厂类，工厂模式使其创建过程延迟到子类进行。 |
| 2    | **抽象工厂** | ![img](https://bugstack.cn/assets/images/2020/design/12.png) | 替换Redis双集群升级，代理类抽象场景                  | 提供一个创建一系列相关或相互依赖对象的接口，而无需指定它们具体的类。 |
| 3    | **生成器**   | ![img](https://bugstack.cn/assets/images/2020/design/13.png) | 各项装修物料组合套餐选配场景                         | 将一个复杂的构建与其表示相分离，使得同样的构建过程可以创建不同的表示。 |
| 4    | **原型**     | ![img](https://bugstack.cn/assets/images/2020/design/14.png) | 上机考试多套试，每人题目和答案乱序排列场景           | 用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象。 |
| 5    | **单例**     | ![img](https://bugstack.cn/assets/images/2020/design/15.png) | 7种单例模式案例，Effective Java 作者推荐枚举单例模式 | 保证一个类仅有一个实例，并提供一个访问它的全局访问点。       |

- 图片引用：[https://refactoringguru.cn](https://refactoringguru.cn)
- 出版图书：[https://mp.weixin.qq.com/s/g9LYQEqzOeiYOpfG_5XFYg](https://mp.weixin.qq.com/s/g9LYQEqzOeiYOpfG_5XFYg)

### 2. DDD 专题

- 地址：[DDD 专题](https://bugstack.cn/md/develop/framework/ddd/2019-10-15-DDD%E4%B8%93%E9%A2%98%E6%A1%88%E4%BE%8B%E4%B8%80%E3%80%8A%E5%88%9D%E8%AF%86%E9%A2%86%E5%9F%9F%E9%A9%B1%E5%8A%A8%E8%AE%BE%E8%AE%A1DDD%E8%90%BD%E5%9C%B0%E3%80%8B.html)
- 介绍：领取驱动设计DDD{Domain-Driven Design}历史较长但随着微服务的兴起DDD又活跃到人们的视线，它提供的是一套架构设计思想，我们可以使用这套方法论将架构设计的尽可能做到高内聚、低耦合、可扩展性强的应用服务。本专题以DDD实战落地为根本，分章节设计不同的架构模型。学习并实战是奔入应用级最快的方法，Hi HelloWorld！我来了。

### 3. 工程框架

- 地址：[工程框架](https://bugstack.cn/md/develop/framework/frame/2019-12-22-%E6%9E%B6%E6%9E%84%E6%A1%86%E6%9E%B6%E6%90%AD%E5%BB%BA%E4%B8%80%E3%80%8A%E5%8D%95%E4%BD%93%E5%BA%94%E7%94%A8%E6%9C%8D%E5%8A%A1%E4%B9%8BSSM%E6%95%B4%E5%90%88%EF%BC%9ASpring4%20+%20SpringMvc%20+%20Mybatis%E3%80%8B.html)
- 介绍：讲解工程🏭框架的搭建，落地案例代码，方便有需要的研发伙伴以此为模板快速搭建自己需要的框架工程，也是一种入门的学习的最快方式。

### 4. 架构设计

- 地址：[架构设计](https://bugstack.cn/md/develop/framework/scheme/2021-02-04-%E5%9F%BA%E4%BA%8EIDEA%E6%8F%92%E4%BB%B6%E5%BC%80%E5%8F%91%E5%92%8C%E5%AD%97%E8%8A%82%E7%A0%81%E6%8F%92%E6%A1%A9%E6%8A%80%E6%9C%AF%EF%BC%8C%E5%AE%9E%E7%8E%B0%E7%A0%94%E5%8F%91%E4%BA%A4%E4%BB%98%E8%B4%A8%E9%87%8F%E8%87%AA%E5%8A%A8%E5%88%86%E6%9E%90.html)
- 介绍：讲解互联网大厂中一些场景的复杂场景，该如何设计和落地，包括：IDEA 插件、低代码、脚手架、DDD、中台、非入侵的全链路监控、字节码插庒等场景问题。

## 七、中间件

### 1. Spring Boot 中间件开发

- 地址：[Spring Boot 中间件开发](https://bugstack.cn/md/assembly/middleware/2019-12-02-SpringBoot%E6%9C%8D%E5%8A%A1%E6%B2%BB%E7%90%86%E4%B8%AD%E9%97%B4%E4%BB%B6%E4%B9%8B%E7%BB%9F%E4%B8%80%E7%99%BD%E5%90%8D%E5%8D%95%E9%AA%8C%E8%AF%81.html)
- 介绍：讲解基础的 Spring Boot Starter 开发流程，以及如何设计实现的，并且该怎么把这样的插件发布到中心仓库中。如果对此感兴趣也可以阅读：[SpringBoot 中间件设计和开发](https://bugstack.cn/md/project/springboot-middleware/2021-03-31-%E3%80%8ASpringBoot%20%E4%B8%AD%E9%97%B4%E4%BB%B6%E8%AE%BE%E8%AE%A1%E5%92%8C%E5%BC%80%E5%8F%91%E3%80%8B%E4%B8%93%E6%A0%8F%E5%B0%8F%E5%86%8C%E4%B8%8A%E7%BA%BF%E5%95%A6%EF%BC%81.html) - `这里有更完整的中间件开发学习，包括：服务治理、ORM框架、分布式组件、字节码应用`

### 2. IDEA Plugin 开发

- 地址：[IDEA Plugin 开发](https://bugstack.cn/md/assembly/idea-plugin/2021-08-27-%E6%8A%80%E6%9C%AF%E8%B0%83%E7%A0%94%EF%BC%8CIDEA%20%E6%8F%92%E4%BB%B6%E6%80%8E%E4%B9%88%E5%BC%80%E5%8F%91%EF%BC%9F.html)
- 介绍：IDEA 插件开发可以帮助研发人员提升能效，解决一些实际场景中的共性问题。但最近在折腾IDEA插件开发的时候，市面的资料确实不多，也没有成体系完整的开发指导手册，所以就遇到了很多不知道就不会的事情，需要一点点查询搜索源码、验证API接口，最终把各项功能实现，当然在这个过程中也确实踩了不少坑！接下来在这个专栏会把一些关于 IDEA 插件开发用到的各项知识做成案例输出出来，帮助有需要的研发伙伴，一起建设 IDEA Plugin

![](https://bugstack.cn/assets/images/middleware/middleware-5-2.png)

- **开发方式**：在官网的描述中，创建IDEA插件工程的方式有两种分别是，IntelliJ Platform Plugin 模版创建和 Gradle 构建方式。
- **框架入口**：一个 IDEA 插件开发完，要考虑把它嵌入到哪，比如是从 IDEA 窗体的 Edit、Tools 等进入配置还是把窗体嵌入到左、右工具条还是IDEA窗体下的对话框。
- **UI**：思考的是窗体需要用到什么语言开发，没错，用的就是 Swing、Awt 的技术能力。
- **API**：在 IDEA 插件开发中，一般都是围绕工程进行的，那么基本要从通过 IDEA 插件 JDK 开发能力中获取到工程信息、类信息、文件信息等。
- **外部功能**：这一个是用于把插件能力与外部系统结合，比如你是需要把拿到的接口上传到服务器，还是从远程下载文件等等。

### 3. API网关：中间件设计和实践

- 地址：[API网关：中间件设计和实践](https://bugstack.cn/md/assembly/api-gateway/2022-08-12-%E5%BC%80%E7%AF%87%EF%BC%9A%E5%A6%82%E6%9E%9C%E8%AE%A9%E6%88%91%E8%AE%BE%E8%AE%A1%E4%B8%80%E5%A5%97%EF%BC%8CTPS%E7%99%BE%E4%B8%87%E7%BA%A7API%E7%BD%91%E5%85%B3.html)
- 介绍：API网关，是所有互联网大厂都有的一个核心服务，承接着来自用户的滴滴打车、美团外卖、京东购物、微信支付，更是大促期间千万级访问量的核心系统。

## 八、Netty 4.x

- 地址：[Netty 4.x](https://bugstack.cn/md/netty/base/2019-07-30-netty%E6%A1%88%E4%BE%8B%EF%BC%8Cnetty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E9%9B%B6%E3%80%8A%E5%88%9D%E5%85%A5JavaIO%E4%B9%8B%E9%97%A8BIO%E3%80%81NIO%E3%80%81AIO%E5%AE%9E%E6%88%98%E7%BB%83%E4%B9%A0%E3%80%8B.html)
- 介绍：跟着案例学Netty，Netty4.x案例从简单入门到应用实战，全篇37章节优秀案例+实战源码[基础篇(13)、中级篇(13)、高级篇(3章+)、源码分析篇]，以上章节全部完成并不断持续更新中。

## 九、字节码编程(ASM、Javassist、Byte-Buddy)

- 地址：[字节码编程(ASM、Javassist、Byte-Buddy)](https://bugstack.cn/md/bytecode/asm/2020-03-25-%5BASM%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B%5D%E5%A6%82%E6%9E%9C%E4%BD%A0%E5%8F%AA%E5%86%99CRUD%EF%BC%8C%E9%82%A3%E8%BF%99%E7%A7%8D%E6%8A%80%E6%9C%AF%E4%BD%A0%E6%B0%B8%E8%BF%9C%E7%A2%B0%E4%B8%8D%E5%88%B0.html)
- 介绍：但全书共计107页，11万7千字，20个章节涵盖三个字节码框架和JavaAgent使用并附带整套案例源码！讲道理，市面上以及网络搜索中都基本很少有成体系的关于字节码编程的知识，这主要由于大部分开发人员其实很少接触这部分内容，包括；ASM、Javassist、Byte-buddy以及JavaAgent，没有很大的市场也就没有很多的资料。但大家其实已经从其他的框架或者中间件中使用到，就像你用到的；Cglib、混沌工程、非入侵的全链路监控以及你是否使用过jetbrains-agent.jar做了某项实验？

## 十、实战项目

### 1. IM Netty 仿PC端微信

- 地址：[IM Netty 仿PC端微信](https://bugstack.cn/md/project/im/2020-03-04-%E3%80%8ANetty+JavaFx%E5%AE%9E%E6%88%98%EF%BC%9A%E4%BB%BF%E6%A1%8C%E9%9D%A2%E7%89%88%E5%BE%AE%E4%BF%A1%E8%81%8A%E5%A4%A9%E3%80%8B.html)
- 介绍：🎭 本项目是作者小傅哥使用JavaFx、Netty4.x、SpringBoot、Mysql等技术栈和偏向于DDD领域驱动设计方式，搭建的仿桌面版微信实现通信核心功能。课程文章已发布到GitChat专栏，欢迎购买。

<div align="center">
    <img src="https://bugstack.cn/assets/images/2020/project.jpg?raw=true" width="900px">
    <img src="https://bugstack.cn/assets/images/2020/ui-01.png?raw=true" width="900px">
</div>

**第一章节**：**UI开发**。使用`JavaFx`与`Maven`搭建UI桌面工程，逐步讲解登录框体、聊天框体、对话框、好友栏等各项UI展示及操作事件。从而在这一章节中让Java 程序员学会开发桌面版应用。

**第二章节**：**架构设计**。在这一章节中我们会使用DDD领域驱动设计的四层模型结构与Netty结合使用，架构出合理的分层框架。同时还有相应库表功能的设计。相信这些内容学习后，你一定也可以假设出更好的框架。

**第三章节**：**功能实现**。这部分我们主要将通信中的各项功能逐步实现，包括；登录、添加好友、对话通知、消息发送、断线重连等各项功能。最终完成整个项目的开发，同时也可以让你从实践中学会技能。

### 2. 《SpringBoot 中间件设计和开发》

- 地址：[《SpringBoot 中间件设计和开发》](https://bugstack.cn/md/project/springboot-middleware/2021-03-31-%E3%80%8ASpringBoot%20%E4%B8%AD%E9%97%B4%E4%BB%B6%E8%AE%BE%E8%AE%A1%E5%92%8C%E5%BC%80%E5%8F%91%E3%80%8B%E4%B8%93%E6%A0%8F%E5%B0%8F%E5%86%8C%E4%B8%8A%E7%BA%BF%E5%95%A6%EF%BC%81.html)
- 介绍：说到底，为什么要扒开CRUD的表面，深入到核心源码实践学一些中间件开发技能，还不是希望自己对技术栈学习有一定的深度，免得面试时被人忽悠压薪资。就像人家问你：类的代理、反射调用是在什么场景用到的？ 自定义注解是怎么和切面一起获取到信息使用的？你需要的yml配置信息是如何被SpringBoot加载并初始化的？Bean 是如何被注入到 Spring 容器，提供服务的？综上，等等这些技术点可能很多时候你所学到的只能称作为背答案、记结果，因为没有实操所以过后就忘而且也扛不住面试官的接连发问。

![](https://bugstack.cn/assets/images/middleware/2-1.png)

- **技术框架**：包括 Spring、SpringBoot 配置加载、自定义注解、扫描注册Bean等，以及 ORM 框架设计原理和实现。这部分技术主要是把开发的中间件与框架结合，开发相应的组件或者包装为各类 SpringBoot Starter 的能力学习。
- **数据服务**：Mysql、Redis、Elasticsearch，都是数据服务，通常需要开发各类组件对数据服务的使用进行封装，Mysql 我们知道有 JDBC，Redis 我们知道有 Jedis，但 Elasticsearch 有 x-pack 你是否了解。
- **数据组件**：这类组件的开发就是为了简化对数据服务的使用，Mysql+JDBC+ORM，可以非常方便的使用数据库服务，那么 Elasticsearch 是否也可以做相应的组件研发，让它的查询也能像使用 MyBatis 一样呢？二折页的技术能力就需要对 MyBatis 等 ORM 框架的实现原理熟悉，同时需要了解 JDBC 的概念。
- **分布式技术**：RPC 框架、注册中心、分布式任务，都是现有互联网分布式架构中非常重要的技术，而对于如何实现一个 RPC 框架，也技术是研发人员要掌握的重点，同时如何使用注册中心、怎么下发分布式调度任务，等等，这些技术的学习能让对现有的框架使用有更深入的认识。
- **服务治理**：熔断、降级、限流、切量、黑白名单以及对现有方法的非入侵式扩展增强等，都可以成为是服务治理类组件，原本这类技术在早期是与业务逻辑代码融合的，后来逐步被拆解出来，开发成对应的组件。所以我们可以学习到，关于这类组件的包装、集成是如何做的。
- **字节码&插件**：在互联网的系统应用运维过程中，你一定会接触到各类的监控系统，而很多监控系统是非入侵的全链路监控，那么这些是如何实现的呢？其实它们是基于字节码插桩，对系统方法的增强，采集相应的运行时信息，进行监控的。再到扩展 JVMTI、IDEA 插件开发，都是为了整个研发过程的可持续交付和上线提高交付质量和降低人效的。

**综上**，这些贯穿整个互联网系统架构中的各类典型中间件，都会在后续章节中陆续讲解出来，它们是如何设计和实现的，一点点带你解开中间件的神秘面纱，让你的技术栈知识也增加一些有深度的并且是可以亲自操作的内容。

### 3. Lottery 抽奖系统 - 基于领域驱动设计的四层架构实践

- 地址：[Lottery 抽奖系统 - 基于领域驱动设计的四层架构实践](https://bugstack.cn/md/project/lottery/introduce/Lottery%E6%8A%BD%E5%A5%96%E7%B3%BB%E7%BB%9F.html)
- 介绍：`Lottery 抽奖系统` 项目是一款互联网面向C端人群营销活动类的抽奖系统，可以提供抽奖活动玩法策略的创建、参与、记账、发奖等逻辑功能。在使用的过程中运营人员通过创建概率类奖品的抽奖玩法，对用户进行拉新、促活、留存，通常这样的系统会用在电商、外卖、出行、公众号运营等各类场景中。

![](https://bugstack.cn/images/article/project/lottery/introduce/system-list.png)

在此项目中你会学习到互联网公司关于C端项目开发时候用到的一些，技术、架构、规范等内容。由于项目为实战类编程项目，在学习的过程中需要上手操作，小傅哥会把系统的搭建拉不同的分支列为每一个章节进行设计和实现并记录到开发日记中，读者在学习的过程中可以结合这部分内容边看文章边写代码实践。

- 技术：SpringBoot、Mybatis、Dubbo、MQ、Redis、Mysql、ELK、分库分表、Otter
- 架构：DDD 领域驱动设计、充血模型、设计模式
- 规范：分支提交规范、代码编写规范

## 📚PDF 下载

- Java 面经手册：[https://download.csdn.net/download/Yao__Shun__Yu/14932325](https://download.csdn.net/download/Yao__Shun__Yu/14932325)
- 重学Java设计模式：[https://download.csdn.net/download/Yao__Shun__Yu/19265731](https://download.csdn.net/download/Yao__Shun__Yu/19265731)
- 字节码编程：[https://download.csdn.net/download/Yao__Shun__Yu/12505051](https://download.csdn.net/download/Yao__Shun__Yu/12505051)
- IDEA Plugin 开发手册：[https://download.csdn.net/download/Yao__Shun__Yu/77484299](https://download.csdn.net/download/Yao__Shun__Yu/77484299)

## 关于

关于自己、关于学习、关于职场，👉 如果你是刚入行、在外包、跨语言学习、想跳槽大厂、缺少学习动力等，可以阅读小傅哥的成长故事，这个系列包括了我的个人在外包到大厂的成长、跳槽的过程、互联网的学习经历，那么可以阅读一下。Go -> [https://bugstack.cn/md/about/me/about-me.html](https://bugstack.cn/md/about/me/about-me.html)
