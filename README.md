# CodeGuide | 程序员编码指南

> **作者：** 小傅哥，Java Developer，[:pencil2: 虫洞 · 科技栈，作者](https://bugstack.cn)，[:trophy: CSDN 博客专家](https://bugstack.blog.csdn.net)，[:memo: 关于我](https://bugstack.cn/about.html)

> 本代码库是作者小傅哥多年从事一线互联网`Java`开发的学习历程技术汇总，旨在为大家提供一个清晰详细的学习教程，侧重点更倾向编写Java核心内容。如果本仓库能为您提供帮助，请给予支持(关注、点赞、分享)！

<br/>
<div align="center">
    <a href="https://bugstack.cn" style="text-decoration:none"><img src="https://bugstack.cn/assets/images/icon.svg" width="128px"></a>
</div>
<br/>

<div align="center">
<a href="https://github.com/fuzhengwei/CodeGuide"><img src="https://badgen.net/github/stars/fuzhengwei/CodeGuide?icon=github&color=4ab8a1"></a>
<a href="https://github.com/fuzhengwei/CodeGuide"><img src="https://badgen.net/github/forks/fuzhengwei/CodeGuide?icon=github&color=4ab8a1"></a>
<a href="https://bugstack.cn" target="_blank"><img src="https://bugstack.cn/assets/images/onlinebook.svg"></a>
<a href="https://bugstack.cn/assets/images/qrcode.png?x-oss-process=style/may"><img src="https://itstack.org/_media/wxbugstack.svg"></a>
</div>
<br/>

| 源码 | Java&Spring | 算法 | 面向对象 | 中间件 | 网络通信 | 字节码编程 | 故事 |  工具&软件  | 云服务学习|
| :--: | :---------: | :--: | :------: | :----: | :--: | :----: | :--: | :--: | :--: |
| [:octocat:](https://github.com/fuzhengwei/CodeGuide#octocat-%E6%BA%90%E7%A0%81) | [:coffee:](https://github.com/fuzhengwei/CodeGuide#coffee-javaspring) | [:pencil2:](https://github.com/fuzhengwei/CodeGuide#pencil2-%E7%AE%97%E6%B3%95) | [:art:](https://github.com/fuzhengwei/CodeGuide#art-%E9%9D%A2%E5%90%91%E5%AF%B9%E8%B1%A1) | [:hammer:](https://github.com/fuzhengwei/CodeGuide#hammer-%E4%B8%AD%E9%97%B4%E4%BB%B6) | [:cloud:](https://github.com/fuzhengwei/CodeGuide#cloud-%E7%BD%91%E7%BB%9C%E9%80%9A%E4%BF%A1) | [:tractor:](https://github.com/fuzhengwei/CodeGuide#-%E6%95%85%E4%BA%8B) |  [🐾](https://github.com/fuzhengwei/CodeGuide#-%E6%95%85%E4%BA%8B)   | [:hammer:](https://github.com/fuzhengwei/CodeGuide#hammer-%E5%B7%A5%E5%85%B7%E8%BD%AF%E4%BB%B6)   | [:cloud:](https://github.com/fuzhengwei/CodeGuide#cloud-云服务学习) |

<div align="center">
<a href="https://juejin.cn/book/6940996508632219689">中间件设计和开发</a> •    
<a href="https://download.csdn.net/download/Yao__Shun__Yu/14932325">Java面经手册</a> • 
<a href="https://bugstack.cn/itstack/itstack-demo-design.html">重学Java设计模式</a> • 
<a href="https://bugstack.cn/itstack/itstack-demo-bytecode.html">字节码编程</a> • 
<a href="https://github.com/fuzhengwei/CodeGuide#%E6%9E%B6%E6%9E%84%E8%AE%BE%E8%AE%A1">架构设计</a> • 
<a href="https://bugstack.cn/itstack-demo-netty-3/2020/03/04/Netty+JavaFx%E5%AE%9E%E6%88%98-%E4%BB%BF%E6%A1%8C%E9%9D%A2%E7%89%88%E5%BE%AE%E4%BF%A1%E8%81%8A%E5%A4%A9.html">Netty仿微信聊天</a>
</div>

<div align="center">
    <a href="https://bugstack.cn" style="text-decoration:none"><img src="https://github.com/fuzhengwei/CodeGuide/blob/master/docs/assets/img/bugstack-md.png?raw=true"></a>
</div>

## :octocat: 源码

| 序号 | 专栏名称                                      | GitHub                                                       | Gitee                                                        |
| :--: | --------------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
|  1   | [`Netty 4.x 专题`](https://github.com/fuzhengwei/CodeGuide#netty-4x-%E4%B8%93%E9%A2%98)                            | [itstack-demo-netty](https://github.com/fuzhengwei/itstack-demo-netty) | [itstack-demo-netty](https://gitee.com/fustack/itstack-demo-netty) |
|  2   | [`手写 RPC 框架`](https://github.com/fuzhengwei/CodeGuide#netty-4x-%E4%B8%93%E9%A2%98)                                 | [itstack-demo-rpc](https://github.com/fuzhengwei/itstack-demo-rpc) | [itstack-demo-rpc](https://gitee.com/fustack/itstack-demo-rpc) |
|  3   | [`用Java实现JVM`](https://github.com/fuzhengwei/CodeGuide#%E7%94%A8java%E5%AE%9E%E7%8E%B0jvm)                                 | [itstack-demo-jvm](https://github.com/fuzhengwei/itstack-demo-jvm) | [itstack-demo-jvm](https://gitee.com/fustack/itstack-demo-jvm) |
|  4   | [`基于JavaAgent的全链路监控`](https://github.com/fuzhengwei/CodeGuide#%E5%9F%BA%E4%BA%8Ejavaagent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7)                     | [itstack-demo-agent](https://github.com/fuzhengwei/itstack-demo-agent) | [itstack-demo-agent](https://gitee.com/fustack/itstack-demo-agent) |
|  5   | [`iot-gateway网关案例`](https://mp.weixin.qq.com/s/LLyG2ji2gDR2Fz8uDmfJ7A)                         | [itstack-demo-iot-gatewary](https://github.com/fuzhengwei/itstack-demo-iot-gatewary) | [itstack-demo-iot-gatewary](https://gitee.com/fustack/itstack-demo-iot-gatewary) |
|  6   | [`DDD领域驱动设计落地`](https://github.com/fuzhengwei/CodeGuide#%E9%A2%86%E5%9F%9F%E9%A9%B1%E5%8A%A8%E8%AE%BE%E8%AE%A1)                           | [itstack-demo-ddd](https://github.com/fuzhengwei/itstack-demo-ddd) | [itstack-demo-ddd](https://gitee.com/fustack/itstack-demo-ddd) |
|  7   | [`SpringCloud入门案例`](https://github.com/fuzhengwei/CodeGuide#springcloud%E5%85%A5%E9%97%A8%E6%A1%88%E4%BE%8B)                           | [itstack-demo-springcloud](https://github.com/fuzhengwei/itstack-demo-springcloud) | [itstack-demo-springcloud](https://gitee.com/fustack/itstack-demo-springcloud) |
|  8   | [`微信公众号开发`](https://bugstack.cn/itstack-demo-any/2019/11/23/%E5%B9%B6%E4%B8%8D%E6%83%B3%E5%90%B9%E7%89%9B%E7%9A%AE-%E4%BD%86-%E4%B8%BA%E4%BA%86%E6%8A%8AGithub%E5%8D%9A%E5%AE%A2%E7%B2%89%E4%B8%9D%E8%BD%AC%E7%A7%BB%E5%88%B0%E5%85%AC%E4%BC%97%E5%8F%B7-%E6%88%91%E5%B9%B2%E4%BA%86.html)                                | [itstack-ark-wx-test](https://github.com/fuzhengwei/itstack-ark-wx-test) | [itstack-ark-wx-test](https://gitee.com/fustack/itstack-ark-wx-test) |
|  9   | [`SpringBoot中间件开发`](https://github.com/fuzhengwei/CodeGuide#hammer-%E4%B8%AD%E9%97%B4%E4%BB%B6)                          | [door-spring-boot-starter](https://github.com/fuzhengwei/door-spring-boot-starter) | [door-spring-boot-starter](https://gitee.com/fustack/door-spring-boot-starter) |
|  10  | [`服务框架搭建`](https://github.com/fuzhengwei/CodeGuide#%E6%9C%8D%E5%8A%A1%E6%A1%86%E6%9E%B6%E6%90%AD%E5%BB%BA)                                  | [itstack-demo-frame](https://github.com/fuzhengwei/itstack-demo-frame) | [itstack-demo-frame](https://gitee.com/fustack/itstack-demo-frame) |
|  11  | [`源码分析(Spring、Mybatis、Schedule)`](https://github.com/fuzhengwei/CodeGuide#%E6%A0%B8%E5%BF%83%E6%BA%90%E7%A0%81%E8%A7%A3%E8%AF%BB)           | [itstack-demo-code](https://github.com/fuzhengwei/itstack-demo-code) | [itstack-demo-code](https://gitee.com/fustack/itstack-demo-code) |
|  12  | [`Drools规则引擎`](https://github.com/fuzhengwei/CodeGuide#%E5%85%B6%E4%BB%96%E5%86%85%E5%AE%B9)                                | [itstack-demo-drools](https://github.com/fuzhengwei/itstack-demo-drools) | [itstack-demo-drools](https://gitee.com/fustack/itstack-demo-drools) |
|  13  | [`ASM字节码编程`](https://github.com/fuzhengwei/CodeGuide#asm)                                 | [itstack-demo-asm](https://github.com/fuzhengwei/itstack-demo-asm) | [itstack-demo-asm](https://gitee.com/fustack/itstack-demo-asm) |
|  14  | [`我的大学四年到毕业工作5年的学习资源和面试汇总`](https://bugstack.cn/itstack-code-life/2020/03/31/%E5%A4%A7%E5%AD%A6%E5%9B%9B%E5%B9%B4%E5%88%B0%E6%AF%95%E4%B8%9A%E5%B7%A5%E4%BD%9C5%E5%B9%B4%E7%9A%84%E5%AD%A6%E4%B9%A0%E8%B7%AF%E7%BA%BF%E8%B5%84%E6%BA%90%E6%B1%87%E6%80%BB.html) | [网盘下载，if链接失效，加微信：fustack](https://pan.baidu.com/s/4mmX7sDy) | -                                                            |
|  15  | [`Netty+JavaFx实战：仿桌面版微信聊天`](https://github.com/fuzhengwei/CodeGuide#nettyjavafx%E5%AE%9E%E6%88%98%E4%BB%BF%E6%A1%8C%E9%9D%A2%E7%89%88%E5%BE%AE%E4%BF%A1%E8%81%8A%E5%A4%A9)            | [NaiveChat](https://github.com/fuzhengwei/NaiveChat) | [NaiveChat](https://gitee.com/fustack/NaiveChat) |
|  16  | [`JDK1.8新特性41个案例讲解`](https://bugstack.cn/itstack-demo-any/2019/12/10/%E6%9C%89%E7%82%B9%E5%B9%B2%E8%B4%A7-Jdk1.8%E6%96%B0%E7%89%B9%E6%80%A7%E5%AE%9E%E6%88%98%E7%AF%87(41%E4%B8%AA%E6%A1%88%E4%BE%8B).html)                      | [itstack-demo-jdk8](https://github.com/fuzhengwei/itstack-demo-jdk8) | [itstack-demo-jdk8](https://gitee.com/fustack/itstack-demo-jdk8) |
|  17  | [`小傅哥的《字节码编程》专栏`](https://github.com/fuzhengwei/CodeGuide#tractor-%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B)                    | [itstack-demo-bytecode](https://github.com/fuzhengwei/itstack-demo-bytecode) | [itstack-demo-bytecode](https://gitee.com/fustack/itstack-demo-bytecode) |
|  18  | [`重学Java设计模式`](https://github.com/fuzhengwei/CodeGuide#%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F)                             | [itstack-demo-design](https://github.com/fuzhengwei/itstack-demo-design) | [itstack-demo-design](https://gitee.com/fustack/itstack-demo-design) |
|  19  | [`面经手册`](https://github.com/fuzhengwei/CodeGuide#java-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C)                                      | [interview](https://github.com/fuzhengwei/interview) | [interview](https://gitee.com/fustack/interview) |
| 20 | [`Spring手撸专栏`](https://github.com/fuzhengwei/CodeGuide/blob/master/README.md#seedling-spring%E6%89%8B%E6%92%B8%E4%B8%93%E6%A0%8F) | [small-spring](https://github.com/fuzhengwei/small-spring) | [small-spring](https://gitee.com/fustack/small-spring) |

**如果**，以上某些资源不能下载获取，可以添加作者好友：小傅哥的微信(fustack)

## :coffee: Java&Spring

### Java 面经手册

全专栏共5章29节，417页11.5万字，耗时4个月完成。涵盖数据结构、算法逻辑、并发编程、JVM以及简历和互联网大厂面试等内容。

此书并不是单纯的面试题，也不是内卷八股文。而是从一个单纯的和程序员有关的数学知识点开始，深入讲解 Java 的核心技术。并且每一章节都配有实践验证的源码，可以对照着一起撸才更有感觉！

**源码**

- GitHub：[https://github.com/fuzhengwei/interview](https://github.com/fuzhengwei/interview)
- Gitee：[https://gitee.com/fustack/interview](https://gitee.com/fustack/interview) 

#### 第 1 章 谈谈面试

- [第 1 节：面试官都问我啥](https://bugstack.cn/interview/2020/07/28/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E5%BC%80%E7%AF%87-%E9%9D%A2%E8%AF%95%E5%AE%98%E9%83%BD%E9%97%AE%E6%88%91%E5%95%A5.html)
- [第 2 节：认知自己的技术栈盲区](https://bugstack.cn/interview/2020/07/30/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC1%E7%AF%87-%E8%AE%A4%E7%9F%A5%E8%87%AA%E5%B7%B1%E7%9A%84%E6%8A%80%E6%9C%AF%E6%A0%88%E7%9B%B2%E5%8C%BA.html)
- [第 3 节：简历该怎么写](https://bugstack.cn/itstack-code-life/2020/04/11/%E5%B7%A5%E4%BD%9C%E4%B8%A4%E5%B9%B4%E7%AE%80%E5%8E%86%E5%86%99%E6%88%90%E8%BF%99%E6%A0%B7-%E8%B0%81%E8%A6%81%E4%BD%A0%E5%91%80.html)
- [第 4 节：大厂都爱聊啥](https://bugstack.cn/itstack-code-life/2020/11/15/BATJTMD-%E5%A4%A7%E5%8E%82%E6%8B%9B%E8%81%98-%E9%83%BD%E6%8B%9B%E4%BB%80%E4%B9%88%E6%A0%B7Java%E7%A8%8B%E5%BA%8F%E5%91%98.html)

#### 第 2 章 数据结构和算法

- [第 1 节：HashCode为什么使用31作为乘数](https://bugstack.cn/interview/2020/08/04/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC2%E7%AF%87-%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84-HashCode%E4%B8%BA%E4%BB%80%E4%B9%88%E4%BD%BF%E7%94%A831%E4%BD%9C%E4%B8%BA%E4%B9%98%E6%95%B0.html)
- [第 2 节：HashMap 源码分析(上)](https://bugstack.cn/interview/2020/08/07/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC3%E7%AF%87-HashMap%E6%A0%B8%E5%BF%83%E7%9F%A5%E8%AF%86-%E6%89%B0%E5%8A%A8%E5%87%BD%E6%95%B0-%E8%B4%9F%E8%BD%BD%E5%9B%A0%E5%AD%90-%E6%89%A9%E5%AE%B9%E9%93%BE%E8%A1%A8%E6%8B%86%E5%88%86-%E6%B7%B1%E5%BA%A6%E5%AD%A6%E4%B9%A0.html)
- [第 3 节：HashMap 源码分析(下) ](https://bugstack.cn/interview/2020/08/13/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC4%E7%AF%87-HashMap%E6%95%B0%E6%8D%AE%E6%8F%92%E5%85%A5-%E6%9F%A5%E6%89%BE-%E5%88%A0%E9%99%A4-%E9%81%8D%E5%8E%86-%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.html)
- [第 4 节：2-3树与红黑树学习(上)](https://bugstack.cn/interview/2020/08/16/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC5%E7%AF%87-%E7%9C%8B%E5%9B%BE%E8%AF%B4%E8%AF%9D-%E8%AE%B2%E8%A7%A32-3%E5%B9%B3%E8%A1%A1%E6%A0%91-%E7%BA%A2%E9%BB%91%E6%A0%91%E7%9A%84%E5%89%8D%E8%BA%AB.html)
- [第 5 节：2-3树与红黑树学习(下)](https://bugstack.cn/interview/2020/08/20/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC6%E7%AF%87-%E5%B8%A6%E7%9D%80%E9%9D%A2%E8%AF%95%E9%A2%98%E5%AD%A6%E4%B9%A0%E7%BA%A2%E9%BB%91%E6%A0%91%E6%93%8D%E4%BD%9C%E5%8E%9F%E7%90%86-%E8%A7%A3%E6%9E%90%E4%BB%80%E4%B9%88%E6%97%B6%E5%80%99%E6%9F%93%E8%89%B2-%E6%80%8E%E4%B9%88%E8%BF%9B%E8%A1%8C%E6%97%8B%E8%BD%AC-%E4%B8%8E2-3%E6%A0%91%E6%9C%89%E4%BB%80%E4%B9%88%E5%85%B3%E8%81%94.html)
- [第 6 节：ArrayList 详细分析](https://bugstack.cn/interview/2020/08/27/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC7%E7%AF%87-ArrayList%E4%B9%9F%E8%BF%99%E4%B9%88%E5%A4%9A%E7%9F%A5%E8%AF%86-%E4%B8%80%E4%B8%AA%E6%8C%87%E5%AE%9A%E4%BD%8D%E7%BD%AE%E6%8F%92%E5%85%A5%E5%B0%B1%E6%8A%8A%E8%B0%A2%E9%A3%9E%E6%9C%BA%E9%9D%A2%E6%99%95%E4%BA%86.html)
- [第 7 节：LinkedList、ArrayList，插入分析](https://bugstack.cn/interview/2020/08/30/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC8%E7%AF%87-LinkedList%E6%8F%92%E5%85%A5%E9%80%9F%E5%BA%A6%E6%AF%94ArrayList%E5%BF%AB-%E4%BD%A0%E7%A1%AE%E5%AE%9A%E5%90%97.html)
- [第 8 节：双端队列、延迟队列、阻塞队列](https://bugstack.cn/interview/2020/09/03/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC9%E7%AF%87-%E9%98%9F%E5%88%97%E6%98%AF%E4%BB%80%E4%B9%88-%E4%BB%80%E4%B9%88%E6%98%AF%E5%8F%8C%E7%AB%AF%E9%98%9F%E5%88%97-%E5%BB%B6%E8%BF%9F%E5%AF%B9%E5%88%97-%E9%98%BB%E5%A1%9E%E9%98%9F%E5%88%97-%E5%85%A8%E6%98%AF%E7%9F%A5%E8%AF%86%E7%9B%B2%E5%8C%BA.html)
- [第 9 节：java.util.Collections、排序、二分、洗牌、旋转算法](https://bugstack.cn/interview/2020/09/10/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC10%E7%AF%87-%E6%89%AB%E7%9B%B2java.util.Collections%E5%B7%A5%E5%85%B7%E5%8C%85-%E5%AD%A6%E4%B9%A0%E6%8E%92%E5%BA%8F-%E4%BA%8C%E5%88%86-%E6%B4%97%E7%89%8C-%E6%97%8B%E8%BD%AC%E7%AE%97%E6%B3%95.html)
- [第 10 节：StringBuilder 与 String 对比](https://bugstack.cn/interview/2020/09/17/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC11%E7%AF%87-StringBuilder-%E6%AF%94-String-%E5%BF%AB-%E7%A9%BA%E5%98%B4%E7%99%BD%E7%89%99%E7%9A%84-%E8%AF%81%E6%8D%AE%E5%91%A2.html)
- [第 11 节：ThreadLocal 源码分析](https://bugstack.cn/interview/2020/09/23/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC12%E7%AF%87-%E9%9D%A2%E8%AF%95%E5%AE%98-ThreadLocal-%E4%BD%A0%E8%A6%81%E8%BF%99%E4%B9%88%E9%97%AE-%E6%88%91%E5%B0%B1%E6%8C%82%E4%BA%86.html)

#### 第 3 章 码农会锁

- [第 1 节：volatile](https://bugstack.cn/interview/2020/10/21/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC14%E7%AF%87-volatile-%E6%80%8E%E4%B9%88%E5%AE%9E%E7%8E%B0%E7%9A%84%E5%86%85%E5%AD%98%E5%8F%AF%E8%A7%81-%E6%B2%A1%E6%9C%89-volatile-%E4%B8%80%E5%AE%9A%E4%B8%8D%E5%8F%AF%E8%A7%81%E5%90%97.html)
- [第 2 节：synchronized](https://bugstack.cn/interview/2020/10/28/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC15%E7%AF%87-%E7%A0%81%E5%86%9C%E4%BC%9A%E9%94%81-synchronized-%E8%A7%A3%E6%AF%92-%E5%89%96%E6%9E%90%E6%BA%90%E7%A0%81%E6%B7%B1%E5%BA%A6%E5%88%86%E6%9E%90.html)
- [第 3 节：ReentrantLock 和 公平锁](https://bugstack.cn/interview/2020/11/04/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC16%E7%AF%87-%E7%A0%81%E5%86%9C%E4%BC%9A%E9%94%81-ReentrantLock%E4%B9%8B%E5%85%AC%E5%B9%B3%E9%94%81%E8%AE%B2%E8%A7%A3%E5%92%8C%E5%AE%9E%E7%8E%B0.html)
- [第 4 节：AQS原理分析和实践运用](https://bugstack.cn/interview/2020/11/11/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC17%E7%AF%87-%E7%A0%81%E5%86%9C%E4%BC%9A%E9%94%81-ReentrantLock%E4%B9%8BAQS%E5%8E%9F%E7%90%86%E5%88%86%E6%9E%90%E5%92%8C%E5%AE%9E%E8%B7%B5%E4%BD%BF%E7%94%A8.html)
- [第 5 节：AQS 共享锁，Semaphore、CountDownLatch](https://bugstack.cn/interview/2020/11/18/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC18%E7%AF%87-AQS-%E5%85%B1%E4%BA%AB%E9%94%81-Semaphore-CountDownLatch-%E5%90%AC%E8%AF%B4%E6%95%B0%E6%8D%AE%E5%BA%93%E8%BF%9E%E6%8E%A5%E6%B1%A0%E5%8F%AF%E4%BB%A5%E7%94%A8%E5%88%B0.html)

#### 第 4 章 多线程

- [第 1 节：Thread.start() 启动原理](https://bugstack.cn/interview/2020/11/25/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC19%E7%AF%87-Thread.start()-%E5%AE%83%E6%98%AF%E6%80%8E%E4%B9%88%E8%AE%A9%E7%BA%BF%E7%A8%8B%E5%90%AF%E5%8A%A8%E7%9A%84%E5%91%A2.html)
- [第 2 节：Thread，状态转换、方法使用、原理分析](https://bugstack.cn/interview/2020/12/02/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC20%E7%AF%87-Thread-%E7%BA%BF%E7%A8%8B-%E7%8A%B6%E6%80%81%E8%BD%AC%E6%8D%A2-%E6%96%B9%E6%B3%95%E4%BD%BF%E7%94%A8-%E5%8E%9F%E7%90%86%E5%88%86%E6%9E%90.html)
- [第 3 节：ThreadPoolExecutor](https://bugstack.cn/interview/2020/12/09/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC21%E7%AF%87-%E6%89%8B%E5%86%99%E7%BA%BF%E7%A8%8B%E6%B1%A0-%E5%AF%B9%E7%85%A7%E5%AD%A6%E4%B9%A0ThreadPoolExecutor%E7%BA%BF%E7%A8%8B%E6%B1%A0%E5%AE%9E%E7%8E%B0%E5%8E%9F%E7%90%86.html)
- [第 4 节：线程池讲解以及JVMTI监控](https://bugstack.cn/interview/2020/12/16/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC22%E7%AF%87-%E7%BA%BF%E7%A8%8B%E6%B1%A0%E7%9A%84%E4%BB%8B%E7%BB%8D%E5%92%8C%E4%BD%BF%E7%94%A8-%E4%BB%A5%E5%8F%8A%E5%9F%BA%E4%BA%8Ejvmti%E8%AE%BE%E8%AE%A1%E9%9D%9E%E5%85%A5%E4%BE%B5%E7%9B%91%E6%8E%A7.html)

#### 第 5 章 JVM 虚拟机

- [第 1 节：JDK、JRE、JVM](https://bugstack.cn/interview/2020/12/23/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC23%E7%AF%87-JDK-JRE-JVM-%E6%98%AF%E4%BB%80%E4%B9%88%E5%85%B3%E7%B3%BB.html)
- [第 2 节：JVM 类加载实践](https://bugstack.cn/interview/2020/12/30/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC24%E7%AF%87-%E4%B8%BA%E4%BA%86%E6%90%9E%E6%B8%85%E6%A5%9A%E7%B1%BB%E5%8A%A0%E8%BD%BD-%E7%AB%9F%E7%84%B6%E6%89%8B%E6%92%B8JVM.html)
- [第 3 节：JVM 内存模型](https://bugstack.cn/interview/2021/01/06/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC25%E7%AF%87-JVM%E5%86%85%E5%AD%98%E6%A8%A1%E5%9E%8B%E6%80%BB%E7%BB%93-%E6%9C%89%E5%90%84%E7%89%88%E6%9C%ACJDK%E5%AF%B9%E6%AF%94-%E6%9C%89%E5%85%83%E7%A9%BA%E9%97%B4OOM%E7%9B%91%E6%8E%A7%E6%A1%88%E4%BE%8B-%E6%9C%89Java%E7%89%88%E8%99%9A%E6%8B%9F%E6%9C%BA-%E7%BB%BC%E5%90%88%E5%AD%A6%E4%B9%A0%E6%9B%B4%E5%AE%B9%E6%98%93.html)
- [第 4 节：JVM 故障处理工具](https://bugstack.cn/interview/2021/01/13/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC26%E7%AF%87-JVM%E6%95%85%E9%9A%9C%E5%A4%84%E7%90%86%E5%B7%A5%E5%85%B7-%E4%BD%BF%E7%94%A8%E6%80%BB%E7%BB%93.html)
- [第 5 节：GC 垃圾回收](https://bugstack.cn/interview/2021/01/20/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC27%E7%AF%87-JVM-%E5%88%A4%E6%96%AD%E5%AF%B9%E8%B1%A1%E5%B7%B2%E6%AD%BB-%E5%AE%9E%E8%B7%B5%E9%AA%8C%E8%AF%81GC%E5%9B%9E%E6%94%B6.html)

### 用Java实现JVM

本专题主要介绍如何通过java代码来实现JVM的基础功能（搜索解析class文件、字节码命令、运行时数据区等），从而让java程序员通过最熟知的java程序，学习JVM是如何将java程序一步步跑起来的。

**源码**

- GitHub：[https://github.com/fuzhengwei/itstack-demo-jvm](https://github.com/fuzhengwei/itstack-demo-jvm) 
- Gitee： [https://gitee.com/fustack/itstack-demo-jvm](https://gitee.com/bugstack_cn/itstack-demo-jvm)

**目录**

- [第 1 节：命令行工具](https://bugstack.cn/itstack-demo-jvm/2019/05/01/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E4%B8%80%E7%AB%A0-%E5%91%BD%E4%BB%A4%E8%A1%8C%E5%B7%A5%E5%85%B7.html)
- [第 2 节：搜索class文件](https://bugstack.cn/itstack-demo-jvm/2019/05/02/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E4%BA%8C%E7%AB%A0-%E6%90%9C%E7%B4%A2class%E6%96%87%E4%BB%B6.html)
- [第 3 节：解析class文件](https://bugstack.cn/itstack-demo-jvm/2019/05/03/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E4%B8%89%E7%AB%A0-%E8%A7%A3%E6%9E%90class%E6%96%87%E4%BB%B6.html)
- [第 4 节：解析class文件 附 classReader拆解](https://bugstack.cn/itstack-demo-jvm/2019/05/04/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E4%B8%89%E7%AB%A0-%E8%A7%A3%E6%9E%90class%E6%96%87%E4%BB%B6-%E9%99%84-classReader%E6%8B%86%E8%A7%A3.html)
- [第 5 节：运行时数据区](https://bugstack.cn/itstack-demo-jvm/2019/05/05/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E5%9B%9B%E7%AB%A0-%E8%BF%90%E8%A1%8C%E6%97%B6%E6%95%B0%E6%8D%AE%E5%8C%BA.html)
- [第 6 节：指令集和解释器](https://bugstack.cn/itstack-demo-jvm/2019/05/06/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E4%BA%94%E7%AB%A0-%E6%8C%87%E4%BB%A4%E9%9B%86%E5%92%8C%E8%A7%A3%E9%87%8A%E5%99%A8.html)
- [第 7 节：类和对象](https://bugstack.cn/itstack-demo-jvm/2019/05/07/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E5%85%AD%E7%AB%A0-%E7%B1%BB%E5%92%8C%E5%AF%B9%E8%B1%A1.html)
- [第 8 节：方法调用和返回](https://bugstack.cn/itstack-demo-jvm/2019/05/08/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E4%B8%83%E7%AB%A0-%E6%96%B9%E6%B3%95%E8%B0%83%E7%94%A8%E5%92%8C%E8%BF%94%E5%9B%9E.html)
- [第 9 节：数组和字符串](https://bugstack.cn/itstack-demo-jvm/2019/05/09/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E5%85%AB%E7%AB%A0-%E6%95%B0%E7%BB%84%E5%92%8C%E5%AD%97%E7%AC%A6%E4%B8%B2.html)
- [第 10 节：本地方法调用](https://bugstack.cn/itstack-demo-jvm/2019/05/10/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E4%B9%9D%E7%AB%A0-%E6%9C%AC%E5%9C%B0%E6%96%B9%E6%B3%95%E8%B0%83%E7%94%A8.html)
- [第 11 节：异常处理](https://bugstack.cn/itstack-demo-jvm/2019/05/11/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E5%8D%81%E7%AB%A0-%E5%BC%82%E5%B8%B8%E5%A4%84%E7%90%86.html)

### SpringCloud入门案例

**源码**

- GitHub：[https://github.com/fuzhengwei/itstack-demo-springcloud](https://github.com/fuzhengwei/itstack-demo-springcloud)
- Gitee： [https://gitee.com/fustack/itstack-demo-springcloud](https://gitee.com/fustack/itstack-demo-springcloud)

**目录**

- [第 1 节：总有一偏概述告诉你SpringCloud是什么](https://bugstack.cn/itstack-demo-springcloud/2019/10/31/Spring-Cloud(%E9%9B%B6)-%E6%80%BB%E6%9C%89%E4%B8%80%E5%81%8F%E6%A6%82%E8%BF%B0%E5%91%8A%E8%AF%89%E4%BD%A0SpringCloud%E6%98%AF%E4%BB%80%E4%B9%88.html)
- [第 2 节：服务集群注册与发现 Eureka](https://bugstack.cn/itstack-demo-springcloud/2019/11/01/Spring-Cloud(%E4%B8%80)-%E6%9C%8D%E5%8A%A1%E9%9B%86%E7%BE%A4%E6%B3%A8%E5%86%8C%E4%B8%8E%E5%8F%91%E7%8E%B0-Eureka.html)
- [第 3 节：服务提供与负载均衡调用 Eureka](https://bugstack.cn/itstack-demo-springcloud/2019/11/02/Spring-Cloud(%E4%BA%8C)-%E6%9C%8D%E5%8A%A1%E6%8F%90%E4%BE%9B%E4%B8%8E%E8%B4%9F%E8%BD%BD%E5%9D%87%E8%A1%A1%E8%B0%83%E7%94%A8-Eureka.html)
- [第 4 节：应用服务快速失败熔断降级保护 Hystrix](https://bugstack.cn/itstack-demo-springcloud/2019/11/03/Spring-Cloud(%E4%B8%89)-%E5%BA%94%E7%94%A8%E6%9C%8D%E5%8A%A1%E5%BF%AB%E9%80%9F%E5%A4%B1%E8%B4%A5%E7%86%94%E6%96%AD%E9%99%8D%E7%BA%A7%E4%BF%9D%E6%8A%A4-Hystrix.html)
- [第 5 节：服务响应性能成功率监控 Hystrix](https://bugstack.cn/itstack-demo-springcloud/2019/11/04/Spring-Cloud(%E5%9B%9B)-%E6%9C%8D%E5%8A%A1%E5%93%8D%E5%BA%94%E6%80%A7%E8%83%BD%E6%88%90%E5%8A%9F%E7%8E%87%E7%9B%91%E6%8E%A7-Hystrix.html)
- [第 6 节：Turbine 监控信息聚合展示 Hystrix](https://bugstack.cn/itstack-demo-springcloud/2019/11/05/Spring-Cloud(%E4%BA%94)-Turbine-%E7%9B%91%E6%8E%A7%E4%BF%A1%E6%81%AF%E8%81%9A%E5%90%88%E5%B1%95%E7%A4%BA-Hystrix.html)
- [第 7 节：基于github webhook动态刷新服务配置](https://bugstack.cn/itstack-demo-springcloud/2019/11/06/Spring-Cloud(%E5%85%AD)-%E5%9F%BA%E4%BA%8EGithub-Webhook%E5%8A%A8%E6%80%81%E5%88%B7%E6%96%B0%E6%9C%8D%E5%8A%A1%E9%85%8D%E7%BD%AE.html)
- [第 8 节：基于RabbitMQ消息总线方式刷新配置服务](https://bugstack.cn/itstack-demo-springcloud/2019/11/07/Spring-Cloud(%E4%B8%83)-%E5%9F%BA%E4%BA%8ERabbitMQ%E6%B6%88%E6%81%AF%E6%80%BB%E7%BA%BF%E6%96%B9%E5%BC%8F%E5%88%B7%E6%96%B0%E9%85%8D%E7%BD%AE%E6%9C%8D%E5%8A%A1.html)
- [第 9 节：服务网关路由 Zuul1](https://bugstack.cn/itstack-demo-springcloud/2019/11/08/Spring-Cloud(%E5%85%AB)-%E6%9C%8D%E5%8A%A1%E7%BD%91%E5%85%B3%E8%B7%AF%E7%94%B1-Zuul1.html)
- [第 10 节：服务网关Zuul 动态路由与权限过滤器](https://bugstack.cn/itstack-demo-springcloud/2019/11/24/Spring-Cloud(%E4%B9%9D)-%E6%9C%8D%E5%8A%A1%E7%BD%91%E5%85%B3Zuul-%E5%8A%A8%E6%80%81%E8%B7%AF%E7%94%B1%E4%B8%8E%E6%9D%83%E9%99%90%E8%BF%87%E6%BB%A4%E5%99%A8.html)

### 核心源码解读

源码分析以最核心干货内容为入手，将平时开发使用到的Spring、Mybatis、多线程等逐个渗透分析研究。不在只是单纯使用，而是要从原理分析获取更多的技术成长。
         
**源码**

- GitHub：[https://github.com/fuzhengwei/itstack-demo-code](https://github.com/fuzhengwei/itstack-demo-code)
- Gitee： [https://gitee.com/fustack/itstack-demo-code](https://gitee.com/fustack/itstack-demo-code)

**目录**

- [第 1 节：Mybatis接口没有实现类为什么可以执行增删改查](https://bugstack.cn/itstack-demo-any/2019/12/25/%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90-Mybatis%E6%8E%A5%E5%8F%A3%E6%B2%A1%E6%9C%89%E5%AE%9E%E7%8E%B0%E7%B1%BB%E4%B8%BA%E4%BB%80%E4%B9%88%E5%8F%AF%E4%BB%A5%E6%89%A7%E8%A1%8C%E5%A2%9E%E5%88%A0%E6%94%B9%E6%9F%A5.html)
- [第 2 节：Spring定时任务Quartz执行全过程源码解读](https://bugstack.cn/itstack-demo-any/2020/01/01/%E6%BA%90%E7%A0%81%E8%A7%A3%E6%9E%90-Spring%E5%AE%9A%E6%97%B6%E4%BB%BB%E5%8A%A1Quartz%E6%89%A7%E8%A1%8C%E5%85%A8%E8%BF%87%E7%A8%8B%E6%BA%90%E7%A0%81%E8%A7%A3%E8%AF%BB.html)
- [第 3 节：咋嘞？你的IDEA过期了吧！加个Jar包就破解了，为什么？](https://bugstack.cn/itstack-demo-any/2020/01/06/%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90-%E5%92%8B%E5%98%9E-%E4%BD%A0%E7%9A%84IDEA%E8%BF%87%E6%9C%9F%E4%BA%86%E5%90%A7-%E5%8A%A0%E4%B8%AAJar%E5%8C%85%E5%B0%B1%E7%A0%B4%E8%A7%A3%E4%BA%86-%E4%B8%BA%E4%BB%80%E4%B9%88.html)
- [第 4 节：像盗墓一样分析Spring是怎么初始化xml并注册bean的](https://bugstack.cn/itstack-demo-any/2020/01/08/%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90-%E5%83%8F%E7%9B%97%E5%A2%93%E4%B8%80%E6%A0%B7%E5%88%86%E6%9E%90Spring%E6%98%AF%E6%80%8E%E4%B9%88%E5%88%9D%E5%A7%8B%E5%8C%96xml%E5%B9%B6%E6%B3%A8%E5%86%8Cbean%E7%9A%84.html)
- [第 5 节：基于jdbc实现一个Demo版的Mybatis](https://bugstack.cn/itstack-demo-any/2020/01/13/%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90-%E5%9F%BA%E4%BA%8Ejdbc%E5%AE%9E%E7%8E%B0%E4%B8%80%E4%B8%AADemo%E7%89%88%E7%9A%84Mybatis.html)
- [第 6 节：手写mybait-spring核心功能(干货好文一次学会工厂bean、类代理、bean注册的使用)](https://bugstack.cn/itstack-demo-any/2020/01/20/%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90-%E6%89%8B%E5%86%99mybait-spring%E6%A0%B8%E5%BF%83%E5%8A%9F%E8%83%BD(%E5%B9%B2%E8%B4%A7%E5%A5%BD%E6%96%87%E4%B8%80%E6%AC%A1%E5%AD%A6%E4%BC%9A%E5%B7%A5%E5%8E%82bean-%E7%B1%BB%E4%BB%A3%E7%90%86-bean%E6%B3%A8%E5%86%8C%E7%9A%84%E4%BD%BF%E7%94%A8).html)

## :seedling: Spring手撸专栏

- [`《Spring 手撸专栏》第 1 章：开篇介绍，我要带你撸 Spring 啦！`](https://bugstack.cn/spring/2021/05/16/%E7%AC%AC1%E7%AB%A0-%E5%BC%80%E7%AF%87%E4%BB%8B%E7%BB%8D-%E6%89%8B%E5%86%99Spring%E8%83%BD%E7%BB%99%E4%BD%A0%E5%B8%A6%E6%9D%A5%E4%BB%80%E4%B9%88.html)
- [`《Spring 手撸专栏》第 2 章：小试牛刀，实现一个简单的Bean容器`](https://bugstack.cn/spring/2021/05/20/%E7%AC%AC2%E7%AB%A0-%E5%B0%8F%E8%AF%95%E7%89%9B%E5%88%80-%E5%AE%9E%E7%8E%B0%E4%B8%80%E4%B8%AA%E7%AE%80%E5%8D%95%E7%9A%84Bean%E5%AE%B9%E5%99%A8.html)
- [`《Spring 手撸专栏》第 3 章：初显身手，运用设计模式，实现 Bean 的定义、注册、获取`](https://bugstack.cn/spring/2021/05/23/%E7%AC%AC3%E7%AB%A0-%E5%88%9D%E6%98%BE%E8%BA%AB%E6%89%8B-%E8%BF%90%E7%94%A8%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E7%8E%B0-Bean-%E7%9A%84%E5%AE%9A%E4%B9%89-%E6%B3%A8%E5%86%8C-%E8%8E%B7%E5%8F%96.html)
- [`《Spring 手撸专栏》第 4 章：崭露头角，基于Cglib实现含构造函数的类实例化策略`](https://bugstack.cn/spring/2021/05/30/%E7%AC%AC4%E7%AB%A0-%E5%B4%AD%E9%9C%B2%E5%A4%B4%E8%A7%92-%E5%9F%BA%E4%BA%8ECglib%E5%AE%9E%E7%8E%B0%E5%90%AB%E6%9E%84%E9%80%A0%E5%87%BD%E6%95%B0%E7%9A%84%E7%B1%BB%E5%AE%9E%E4%BE%8B%E5%8C%96%E7%AD%96%E7%95%A5.html)
- [`《Spring 手撸专栏》第 5 章：一鸣惊人，为Bean对象注入属性和依赖Bean的功能实现`](https://bugstack.cn/spring/2021/06/02/%E7%AC%AC5%E7%AB%A0-%E4%B8%80%E9%B8%A3%E6%83%8A%E4%BA%BA-%E4%B8%BABean%E5%AF%B9%E8%B1%A1%E6%B3%A8%E5%85%A5%E5%B1%9E%E6%80%A7%E5%92%8C%E4%BE%9D%E8%B5%96Bean%E7%9A%84%E5%8A%9F%E8%83%BD%E5%AE%9E%E7%8E%B0.html)
- [`《Spring 手撸专栏》第 6 章：气吞山河，设计与实现资源加载器，从Spring.xml解析和注册Bean对象`](https://bugstack.cn/spring/2021/06/09/%E7%AC%AC6%E7%AB%A0-%E6%B0%94%E5%90%9E%E5%B1%B1%E6%B2%B3-%E8%AE%BE%E8%AE%A1%E4%B8%8E%E5%AE%9E%E7%8E%B0%E8%B5%84%E6%BA%90%E5%8A%A0%E8%BD%BD%E5%99%A8-%E4%BB%8ESpring.xml%E8%A7%A3%E6%9E%90%E5%92%8C%E6%B3%A8%E5%86%8CBean%E5%AF%B9%E8%B1%A1.html)
- [`《Spring 手撸专栏》第 7 章：所向披靡，实现应用上下文，自动识别、资源加载、扩展机制`](https://bugstack.cn/spring/2021/06/17/%E7%AC%AC7%E7%AB%A0-%E6%89%80%E5%90%91%E6%8A%AB%E9%9D%A1-%E5%AE%9E%E7%8E%B0%E5%BA%94%E7%94%A8%E4%B8%8A%E4%B8%8B%E6%96%87-%E8%87%AA%E5%8A%A8%E8%AF%86%E5%88%AB-%E8%B5%84%E6%BA%90%E5%8A%A0%E8%BD%BD-%E6%89%A9%E5%B1%95%E6%9C%BA%E5%88%B6.html)
- [`《Spring 手撸专栏》第 8 章：龙行有风，向虚拟机注册钩子，实现Bean对象的初始化和销毁方法`](https://bugstack.cn/spring/2021/06/23/%E7%AC%AC8%E7%AB%A0-%E9%BE%99%E8%A1%8C%E6%9C%89%E9%A3%8E-%E5%90%91%E8%99%9A%E6%8B%9F%E6%9C%BA%E6%B3%A8%E5%86%8C%E9%92%A9%E5%AD%90-%E5%AE%9E%E7%8E%B0Bean%E5%AF%B9%E8%B1%A1%E7%9A%84%E5%88%9D%E5%A7%8B%E5%8C%96%E5%92%8C%E9%94%80%E6%AF%81%E6%96%B9%E6%B3%95.html)
- [`《Spring 手撸专栏》第 9 章：虎行有雨，定义标记类型Aware接口，实现感知容器对象`](https://bugstack.cn/spring/2021/06/28/%E7%AC%AC9%E7%AB%A0-%E8%99%8E%E8%A1%8C%E6%9C%89%E9%9B%A8-%E5%AE%9A%E4%B9%89%E6%A0%87%E8%AE%B0%E7%B1%BB%E5%9E%8BAware%E6%8E%A5%E5%8F%A3-%E5%AE%9E%E7%8E%B0%E6%84%9F%E7%9F%A5%E5%AE%B9%E5%99%A8%E5%AF%B9%E8%B1%A1.html)
- [`《Spring 手撸专栏》第 10 章：横刀跃马，关于Bean对象作用域以及FactoryBean的实现和使用`](https://bugstack.cn/spring/2021/06/30/%E7%AC%AC10%E7%AB%A0-%E6%A8%AA%E5%88%80%E8%B7%83%E9%A9%AC-%E5%85%B3%E4%BA%8EBean%E5%AF%B9%E8%B1%A1%E4%BD%9C%E7%94%A8%E5%9F%9F%E4%BB%A5%E5%8F%8AFactoryBean%E7%9A%84%E5%AE%9E%E7%8E%B0%E5%92%8C%E4%BD%BF%E7%94%A8.html)
- [`《Spring 手撸专栏》第 11 章：更上层楼，基于观察者实现，容器事件和事件监听器`](https://bugstack.cn/spring/2021/07/07/%E7%AC%AC11%E7%AB%A0-%E6%9B%B4%E4%B8%8A%E5%B1%82%E6%A5%BC-%E5%9F%BA%E4%BA%8E%E8%A7%82%E5%AF%9F%E8%80%85%E5%AE%9E%E7%8E%B0-%E5%AE%B9%E5%99%A8%E4%BA%8B%E4%BB%B6%E5%92%8C%E4%BA%8B%E4%BB%B6%E7%9B%91%E5%90%AC%E5%99%A8.html)

## :cloud: 云服务学习

**云服务器**：[79元1年，226元3年，新人服务器](https://dashi.aliyun.com/site/xiaofuge/618)

**视频课程**：关注小傅哥的微信视频号，[`小傅哥の码场`](https://www.yuque.com/fuzhengwei/hzgecg/hvoy2a)

---

- [x] 第1天：购买79元服务器，[https://dashi.aliyun.com/site/xiaofuge/618](https://dashi.aliyun.com/site/xiaofuge/618) 配置云服务器，xshell链接，初始化环境	
- [x] 第2天：重置系统安装宝塔，配置博客站点 [https://www.bilibili.com/video/BV1HK4y1X7Dr](https://www.bilibili.com/video/BV1HK4y1X7Dr)
- [x] 第3天：购买.cn域名，提交备案操作手册	
- [x] 第4天：搭建java运行环境、jdk、tomcat、mysql [https://www.bilibili.com/video/BV1BZ4y1W7fC](https://www.bilibili.com/video/BV1BZ4y1W7fC)
- [x] 第5天：部署Netty聊天室到云环境，与室友测试聊天程序 [https://www.bilibili.com/video/BV1BZ4y1W7fC](https://www.bilibili.com/video/BV1BZ4y1W7fC)
- [ ] 第6天：安装Elasticsearch、elasticsearch-head、Kibana，测试x-pack-sql-jdbc与云服务es数据交互	
- [ ] 第7天：安装docker容器，配置云环境，练习命令	
- [ ] 第8天：又购买2台，搭配使用，部署分布式系统，rpc、mq、分布式任务，测试应用程序	
- [ ] 第9天：mysql数据通过binlog同步到ES，以及双机备份	
- [ ] 第10天：域名备案完成，使用宝塔配置域名、ssl、反向代理等，搭建完整博客环境	
- [ ] 第11天：学习phpwind、Discuz，搭建论坛	
- [ ] 第12天：搭建个人完整博客系统，采用Jekyll静态博客，本地初始化，通过ftp上传到云服务	
- [ ] 第13天：搭建wordpress，尝试其他动态博客配置使用	
- [ ] 第14天：选型Go语言 cloudreve 网盘，进行搭建测试	
- [ ] 第15天：选型PHP语言 nextcloud网盘，进行搭建测试	
- [ ] 第16天：承接老舅的需求，把域名和服务器以及一个乡村超时论坛，5万元，卖给老舅。 

### 其他内容

*Drools 是 Java 语言基于Rete算法编写的规则引擎，可以方便的使用声明表达业务逻辑，非常简单易用。本专题会从入门开始逐步完成对Drools的讲解。*

- [Drools规则引擎使用入门：这种场景你还写ifelse你跟孩子坐一桌去吧](https://bugstack.cn/itstack-demo-drools/2020/03/07/%E8%BF%99%E7%A7%8D%E5%9C%BA%E6%99%AF%E4%BD%A0%E8%BF%98%E5%86%99ifelse%E4%BD%A0%E8%B7%9F%E5%AD%A9%E5%AD%90%E5%9D%90%E4%B8%80%E6%A1%8C%E5%8E%BB%E5%90%A7.html)

## :pencil2: 算法

- [两数之和](https://bugstack.cn/itstack-demo-algorithm/2020/03/14/%E9%87%8E%E8%B7%AF%E5%AD%90%E6%90%9E%E7%AE%97%E6%B3%95-%E4%B8%A4%E6%95%B0%E4%B9%8B%E5%92%8C-%E5%B8%A6%E7%9D%80%E5%B0%8F%E7%99%BD%E5%88%B7%E9%9D%A2%E8%AF%95.html)
- [最长子串](https://bugstack.cn/itstack-demo-algorithm/2020/03/18/%E6%97%A0%E9%87%8D%E5%A4%8D%E5%AD%97%E7%AC%A6%E7%9A%84%E6%9C%80%E9%95%BF%E5%AD%90%E4%B8%B2.html)

## :art: 面向对象

### 设计模式

《重学Java设计模式》是一本互联网真实案例实践书籍。以落地解决方案为核心，从实际业务中抽离出，交易、营销、秒杀、中间件、源码等22个真实场景，来学习设计模式的运用。

**源码**

- GitHub：[https://github.com/fuzhengwei/itstack-demo-design](https://github.com/fuzhengwei/itstack-demo-design)
- Gitee：[https://gitee.com/fustack/itstack-demo-design](https://gitee.com/fustack/itstack-demo-design)

#### 创建型模式

- [第 1 节：实战工厂方法模式「多种类型商品不同接口，统一发奖服务搭建场景」](https://bugstack.cn/itstack-demo-design/2020/05/20/%E9%87%8D%E5%AD%A6Java%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E5%B7%A5%E5%8E%82%E6%96%B9%E6%B3%95%E6%A8%A1%E5%BC%8F.html)
- [第 2 节：实战抽象工厂模式「替换Redis双集群升级，代理类抽象场景」](https://bugstack.cn/itstack-demo-design/2020/05/24/%E9%87%8D%E5%AD%A6Java%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E6%8A%BD%E8%B1%A1%E5%B7%A5%E5%8E%82%E6%A8%A1%E5%BC%8F.html)
- [第 3 节：实战建造者模式「各项装修物料组合套餐选配场景」](https://bugstack.cn/itstack-demo-design/2020/05/26/%E9%87%8D%E5%AD%A6Java%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E5%BB%BA%E9%80%A0%E8%80%85%E6%A8%A1%E5%BC%8F.html)
- [第 4 节：实战原型模式「上机考试多套试，每人题目和答案乱序排列场景」](https://bugstack.cn/itstack-demo-design/2020/05/28/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E5%8E%9F%E5%9E%8B%E6%A8%A1%E5%BC%8F.html)
- [第 5 节：实战单例模式「7种单例模式案例，Effective Java 作者推荐枚举单例模式」](https://bugstack.cn/itstack-demo-design/2020/05/31/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E5%8D%95%E4%BE%8B%E6%A8%A1%E5%BC%8F.html)

#### 结构型模式

- [第 1 节：实战适配器模式「从多个MQ消息体中，抽取指定字段值场景」)](https://bugstack.cn/itstack-demo-design/2020/06/02/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E9%80%82%E9%85%8D%E5%99%A8%E6%A8%A1%E5%BC%8F.html)
- [第 2 节：实战桥接模式「多支付渠道(微信、支付宝)与多支付模式(刷脸、指纹)场景」](https://bugstack.cn/itstack-demo-design/2020/06/04/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E6%A1%A5%E6%8E%A5%E6%A8%A1%E5%BC%8F.html)
- [第 3 节：实战组合模式「营销差异化人群发券，决策树引擎搭建场景」](https://bugstack.cn/itstack-demo-design/2020/06/08/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E7%BB%84%E5%90%88%E6%A8%A1%E5%BC%8F.html)
- [第 4 节：实战装饰器模式「SSO单点登录功能扩展，增加拦截用户访问方法范围场景」](https://bugstack.cn/itstack-demo-design/2020/06/09/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E8%A3%85%E9%A5%B0%E5%99%A8%E6%A8%A1%E5%BC%8F.html)
- [第 5 节：实战外观模式「基于SpringBoot开发门面模式中间件，统一控制接口白名单场景」](https://bugstack.cn/itstack-demo-design/2020/06/11/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E5%A4%96%E8%A7%82%E6%A8%A1%E5%BC%8F.html)
- [第 6 节：实战享元模式「基于Redis秒杀，提供活动与库存信息查询场景」](https://bugstack.cn/itstack-demo-design/2020/06/14/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E4%BA%AB%E5%85%83%E6%A8%A1%E5%BC%8F.html)
- [第 7 节：实战代理模式「模拟mybatis-spring中定义DAO接口，使用代理类方式操作数据库原理实现场景」](https://bugstack.cn/itstack-demo-design/2020/06/16/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E4%BB%A3%E7%90%86%E6%A8%A1%E5%BC%8F.html)

#### 行为模式

- [第 1 节：实战责任链模式「模拟618电商大促期间，项目上线流程多级负责人审批场景」](https://bugstack.cn/itstack-demo-design/2020/06/18/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E8%B4%A3%E4%BB%BB%E9%93%BE%E6%A8%A1%E5%BC%8F.html)
- [第 2 节：实战命令模式「模拟高档餐厅八大菜系，小二点单厨师烹饪场景」](https://bugstack.cn/itstack-demo-design/2020/06/21/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E5%91%BD%E4%BB%A4%E6%A8%A1%E5%BC%8F.html)
- [第 3 节：实战迭代器模式「模拟公司组织架构树结构关系，深度迭代遍历人员信息输出场景」](https://bugstack.cn/itstack-demo-design/2020/06/23/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E8%BF%AD%E4%BB%A3%E5%99%A8%E6%A8%A1%E5%BC%8F.html)
- [第 4 节：实战中介者模式「按照Mybatis原理手写ORM框架，给JDBC方式操作数据库增加中介者场景」](https://bugstack.cn/itstack-demo-design/2020/06/27/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E4%B8%AD%E4%BB%8B%E8%80%85%E6%A8%A1%E5%BC%8F.html)
- [第 5 节：实战备忘录模式「模拟互联网系统上线过程中，配置文件回滚场景」](https://bugstack.cn/itstack-demo-design/2020/06/28/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E5%A4%87%E5%BF%98%E5%BD%95%E6%A8%A1%E5%BC%8F.html)
- [第 6 节：实战观察者模式「模拟类似小客车指标摇号过程，监听消息通知用户中签场景」](https://bugstack.cn/itstack-demo-design/2020/06/30/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E8%A7%82%E5%AF%9F%E8%80%85%E6%A8%A1%E5%BC%8F.html)
- [第 7 节：实战状态模式「模拟系统营销活动，状态流程审核发布上线场景」](https://bugstack.cn/itstack-demo-design/2020/07/02/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E7%8A%B6%E6%80%81%E6%A8%A1%E5%BC%8F.html)
- [第 8 节：实战策略模式「模拟多种营销类型优惠券，折扣金额计算策略场景」](https://bugstack.cn/itstack-demo-design/2020/07/05/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E7%AD%96%E7%95%A5%E6%A8%A1%E5%BC%8F.html)
- [第 9 节：实战模版模式「模拟爬虫各类电商商品，生成营销推广海报场景」](https://bugstack.cn/itstack-demo-design/2020/07/07/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E6%A8%A1%E6%9D%BF%E6%A8%A1%E5%BC%8F.html)
- [第 10 节：实战访问者模式「模拟家长与校长，对学生和老师的不同视角信息的访问场景」](https://bugstack.cn/itstack-demo-design/2020/07/09/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E8%AE%BF%E9%97%AE%E8%80%85%E6%A8%A1%E5%BC%8F.html)

### 架构设计

- [方案设计：基于IDEA插件开发和字节码插桩技术，实现研发交付质量自动分析](https://bugstack.cn/framework/2021/02/04/%E5%9F%BA%E4%BA%8EIDEA%E6%8F%92%E4%BB%B6%E5%BC%80%E5%8F%91%E5%92%8C%E5%AD%97%E8%8A%82%E7%A0%81%E6%8F%92%E6%A1%A9%E6%8A%80%E6%9C%AF-%E5%AE%9E%E7%8E%B0%E7%A0%94%E5%8F%91%E4%BA%A4%E4%BB%98%E8%B4%A8%E9%87%8F%E8%87%AA%E5%8A%A8%E5%88%86%E6%9E%90.html)
- [技术扫盲：关于低代码编程的可持续性交付设计和分析](https://bugstack.cn/framework/2021/02/21/%E5%85%B3%E4%BA%8E%E4%BD%8E%E4%BB%A3%E7%A0%81%E7%BC%96%E7%A8%8B%E7%9A%84%E5%8F%AF%E6%8C%81%E7%BB%AD%E6%80%A7%E4%BA%A4%E4%BB%98%E8%AE%BE%E8%AE%A1%E5%92%8C%E5%88%86%E6%9E%90.html)
- [工作两三年了，整不明白架构图都画啥？](https://bugstack.cn/framework/2021/02/28/%E5%B7%A5%E4%BD%9C%E4%B8%A4%E4%B8%89%E5%B9%B4-%E6%95%B4%E4%B8%8D%E6%98%8E%E7%99%BD%E6%9E%B6%E6%9E%84%E5%9B%BE%E9%83%BD%E7%94%BB%E5%95%A5.html)
- [笔记整理：技术架构涵盖内容和演变过程总结](https://bugstack.cn/framework/2021/03/04/%E7%AC%94%E8%AE%B0%E6%95%B4%E7%90%86-%E6%8A%80%E6%9C%AF%E6%9E%B6%E6%9E%84%E6%B6%B5%E7%9B%96%E5%86%85%E5%AE%B9%E5%92%8C%E6%BC%94%E5%8F%98%E8%BF%87%E7%A8%8B%E6%80%BB%E7%BB%93.html)
- [不重复造轮子都是骗小孩的，教你手撸 SpringBoot 脚手架！](https://bugstack.cn/framework/2021/03/14/%E4%B8%8D%E9%87%8D%E5%A4%8D%E9%80%A0%E8%BD%AE%E5%AD%90%E5%8F%AA%E6%98%AF%E9%AA%97%E5%B0%8F%E5%AD%A9%E5%AD%90%E7%9A%84-%E6%95%99%E4%BD%A0%E6%89%8B%E6%92%B8-SpringBoot-%E8%84%9A%E6%89%8B%E6%9E%B6.html) - `脚手架源码`：[EasyRiggerInitializr](https://github.com/fuzhengwei/EasyRiggerInitializr)
- [刚火了的中台转头就拆，一大波公司放不下又拿不起来！](https://bugstack.cn/framework/2021/03/24/%E5%88%9A%E7%81%AB%E4%BA%86%E7%9A%84%E4%B8%AD%E5%8F%B0%E8%BD%AC%E5%A4%B4%E5%B0%B1%E6%8B%86-%E4%B8%80%E5%A4%A7%E6%B3%A2%E5%85%AC%E5%8F%B8%E6%94%BE%E4%B8%8D%E4%B8%8B%E5%8F%88%E6%8B%BF%E4%B8%8D%E8%B5%B7%E6%9D%A5.html)

### 服务框架搭建

服务框架搭建，依赖于不同的业务诉求搭建出各种服务功能的框架结构。将逐步完成；单体服务应用(适合于ERP和个人)、分库分表应用、Mq服务、任务服务、分布式服务、RPC服务等。

**源码**

- GitHub：[https://github.com/fuzhengwei/itstack-demo-frame](https://github.com/fuzhengwei/itstack-demo-frame)
- Gitee： [https://gitee.com/fustack/itstack-demo-frame](https://gitee.com/fustack/itstack-demo-frame)

**目录**

- [第 1 节：单体应用服务之SSM整合：Spring4 + SpringMvc + Mybatis](https://bugstack.cn/itstack-demo-frame/2019/12/22/%E6%9E%B6%E6%9E%84%E6%A1%86%E6%9E%B6%E6%90%AD%E5%BB%BA(%E4%B8%80)-%E5%8D%95%E4%BD%93%E5%BA%94%E7%94%A8%E6%9C%8D%E5%8A%A1%E4%B9%8BSSM%E6%95%B4%E5%90%88-Spring4-+-SpringMvc-+-Mybatis.html)
- [第 2 节：Dubbo分布式领域驱动设计架构框体](https://bugstack.cn/itstack-demo-frame/2019/12/31/%E6%9E%B6%E6%9E%84%E6%A1%86%E6%9E%B6%E6%90%AD%E5%BB%BA(%E4%BA%8C)-Dubbo%E5%88%86%E5%B8%83%E5%BC%8F%E9%A2%86%E5%9F%9F%E9%A9%B1%E5%8A%A8%E8%AE%BE%E8%AE%A1%E6%9E%B6%E6%9E%84%E6%A1%86%E4%BD%93.html)

### 领域驱动设计

本专题以DDD实战落地为根本，分章节设计不同的架构模型，学习并实战是奔入应用级开发最快的方法，Hi HelloWorld！我来了。

- [第 1 节：初识领域驱动设计DDD落地](https://bugstack.cn/itstack-demo-ddd/2019/10/15/DDD专题案例一-初识领域驱动设计DDD落地.html)
- [第 2 节：领域层决策规则树服务设计](https://bugstack.cn/itstack-demo-ddd/2019/10/16/DDD专题案例二-领域层决策规则树服务设计.html)
- [第 3 节：领域驱动设计架构基于SpringCloud搭建微服务](https://bugstack.cn/itstack-demo-ddd/2019/10/17/DDD专题案例三-领域驱动设计架构基于SpringCloud搭建微服务.html)

## :hammer: 中间件

- [第 1 节：发布Jar包到Maven中央仓库(为开发开源中间件做准备)](https://bugstack.cn/itstack-demo-any/2019/12/07/%E5%8F%91%E5%B8%83Jar%E5%8C%85%E5%88%B0Maven%E4%B8%AD%E5%A4%AE%E4%BB%93%E5%BA%93(%E4%B8%BA%E5%BC%80%E5%8F%91%E5%BC%80%E6%BA%90%E4%B8%AD%E9%97%B4%E4%BB%B6%E5%81%9A%E5%87%86%E5%A4%87).html)
- [第 2 节：服务治理中间件之统一白名单验证](https://bugstack.cn/itstack-ark-middleware/2019/12/02/Spring-Boot-%E4%B8%AD%E9%97%B4%E4%BB%B6%E5%BC%80%E5%8F%91(%E4%B8%80)-%E6%9C%8D%E5%8A%A1%E6%B2%BB%E7%90%86%E4%B8%AD%E9%97%B4%E4%BB%B6%E4%B9%8B%E7%BB%9F%E4%B8%80%E7%99%BD%E5%90%8D%E5%8D%95%E9%AA%8C%E8%AF%81.html)
- [第 3 节：开发基于SpringBoot的分布式任务中间件DcsSchedule](https://bugstack.cn/itstack-ark-middleware/2019/12/08/%E5%BC%80%E5%8F%91%E5%9F%BA%E4%BA%8ESpringBoot%E7%9A%84%E5%88%86%E5%B8%83%E5%BC%8F%E4%BB%BB%E5%8A%A1%E4%B8%AD%E9%97%B4%E4%BB%B6DcsSchedule(%E4%B8%BA%E5%BC%80%E6%BA%90%E8%B4%A1%E7%8C%AE%E5%8A%9B%E9%87%8F).html)
- [第 4 节：非入侵字节码增强策略，监测全链路监控实现源码](#)

## :cloud: 网络通信

### Netty 4.x 专题

Netty4.x案例从简单入门到应用实战，全篇37节优秀案例+实战源码；基础篇(12)、拓展篇(13)、应用篇(3章+)、源码篇(6)，以上章节全部完成并不断持续更新中。

**源码**

- GitHub：[https://github.com/fuzhengwei/itstack-demo-netty](https://github.com/fuzhengwei/itstack-demo-netty)
- Gitee： [https://gitee.com/fustack/itstack-demo-design](https://gitee.com/fustack/itstack-demo-design)

#### 基础入门篇

- [第 1 节：初入JavaIO之门BIO、NIO、AIO实战练习](https://bugstack.cn/itstack-demo-netty-1/2019/07/30/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E9%9B%B6-%E5%88%9D%E5%85%A5JavaIO%E4%B9%8B%E9%97%A8BIO-NIO-AIO%E5%AE%9E%E6%88%98%E7%BB%83%E4%B9%A0.html)
- [第 2 节：嗨！NettyServer](https://bugstack.cn/itstack-demo-netty-1/2019/08/01/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E4%B8%80-%E5%97%A8-NettyServer.html)
- [第 3 节：NettyServer接收数据](https://bugstack.cn/itstack-demo-netty-1/2019/08/05/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E4%BA%8C-NettyServer%E6%8E%A5%E6%94%B6%E6%95%B0%E6%8D%AE.html)
- [第 4 节：NettyServer字符串解码器](https://bugstack.cn/itstack-demo-netty-1/2019/08/06/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E4%B8%89-NettyServer%E5%AD%97%E7%AC%A6%E4%B8%B2%E8%A7%A3%E7%A0%81%E5%99%A8.html)
- [第 5 节：NettyServer收发数据](https://bugstack.cn/itstack-demo-netty-1/2019/08/07/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E5%9B%9B-NettyServer%E6%94%B6%E5%8F%91%E6%95%B0%E6%8D%AE.html)
- [第 6 节：NettyServer字符串编码器](https://bugstack.cn/itstack-demo-netty-1/2019/08/08/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E4%BA%94-NettyServer%E5%AD%97%E7%AC%A6%E4%B8%B2%E7%BC%96%E7%A0%81%E5%99%A8.html)
- [第 7 节：NettyServer群发消息](https://bugstack.cn/itstack-demo-netty-1/2019/08/09/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E5%85%AD-NettyServer%E7%BE%A4%E5%8F%91%E6%B6%88%E6%81%AF.html)
- [第 8 节：嗨！NettyClient](https://bugstack.cn/itstack-demo-netty-1/2019/08/10/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E4%B8%83-%E5%97%A8-NettyClient.html)
- [第 9 节：NettyClient半包粘包处理、编码解码处理、收发数据方式](https://bugstack.cn/itstack-demo-netty-1/2019/08/11/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E5%85%AB-NettyClient%E5%8D%8A%E5%8C%85%E7%B2%98%E5%8C%85%E5%A4%84%E7%90%86-%E7%BC%96%E7%A0%81%E8%A7%A3%E7%A0%81%E5%A4%84%E7%90%86-%E6%94%B6%E5%8F%91%E6%95%B0%E6%8D%AE%E6%96%B9%E5%BC%8F.html)
- [第 10 节：自定义编码解码器，处理半包、粘包数据](https://bugstack.cn/itstack-demo-netty-1/2019/08/12/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E4%B9%9D-%E8%87%AA%E5%AE%9A%E4%B9%89%E7%BC%96%E7%A0%81%E8%A7%A3%E7%A0%81%E5%99%A8-%E5%A4%84%E7%90%86%E5%8D%8A%E5%8C%85-%E7%B2%98%E5%8C%85%E6%95%B0%E6%8D%AE.html)
- [第 11 节：关于ChannelOutboundHandlerAdapter简单使用](netty案例，netty4.1基础入门篇十《关于ChannelOutboundHandlerAdapter简单使用》)
- [第 12 节：netty udp通信方式案例Demo](https://bugstack.cn/itstack-demo-netty-1/2019/08/14/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E5%8D%81%E4%B8%80-netty-udp%E9%80%9A%E4%BF%A1%E6%96%B9%E5%BC%8F%E6%A1%88%E4%BE%8BDemo.html)
- [第 13 节：简单实现一个基于Netty搭建的Http服务](https://bugstack.cn/itstack-demo-netty-1/2019/08/15/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E5%8D%81%E4%BA%8C-%E7%AE%80%E5%8D%95%E5%AE%9E%E7%8E%B0%E4%B8%80%E4%B8%AA%E5%9F%BA%E4%BA%8ENetty%E6%90%AD%E5%BB%BA%E7%9A%84Http%E6%9C%8D%E5%8A%A1.html)

#### 中级拓展篇

- [第 1 节：Netty与SpringBoot整合](https://bugstack.cn/itstack-demo-netty-2/2019/08/16/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E4%B8%80-Netty%E4%B8%8ESpringBoot%E6%95%B4%E5%90%88.html)
- [第 2 节：Netty使用Protobuf传输数据](https://bugstack.cn/itstack-demo-netty-2/2019/08/17/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E4%BA%8C-Netty%E4%BD%BF%E7%94%A8Protobuf%E4%BC%A0%E8%BE%93%E6%95%B0%E6%8D%AE.html)
- [第 3 节：Netty传输Java对象](https://bugstack.cn/itstack-demo-netty-2/2019/08/18/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E4%B8%89-Netty%E4%BC%A0%E8%BE%93Java%E5%AF%B9%E8%B1%A1.html)
- [第 4 节：Netty传输文件、分片发送、断点续传](https://bugstack.cn/itstack-demo-netty-2/2019/08/19/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%9B%9B-Netty%E4%BC%A0%E8%BE%93%E6%96%87%E4%BB%B6-%E5%88%86%E7%89%87%E5%8F%91%E9%80%81-%E6%96%AD%E7%82%B9%E7%BB%AD%E4%BC%A0.html)
- [第 5 节：基于Netty搭建WebSocket，模仿微信聊天页面](https://bugstack.cn/itstack-demo-netty-2/2019/08/20/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E4%BA%94-%E5%9F%BA%E4%BA%8ENetty%E6%90%AD%E5%BB%BAWebSocket-%E6%A8%A1%E4%BB%BF%E5%BE%AE%E4%BF%A1%E8%81%8A%E5%A4%A9%E9%A1%B5%E9%9D%A2.html)
- [第 6 节：SpringBoot+Netty+Elasticsearch收集日志信息数据存储](https://bugstack.cn/itstack-demo-netty-2/2019/08/21/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%85%AD-SpringBoot+Netty+Elasticsearch%E6%94%B6%E9%9B%86%E6%97%A5%E5%BF%97%E4%BF%A1%E6%81%AF%E6%95%B0%E6%8D%AE%E5%AD%98%E5%82%A8.html)
- [第 7 节：Netty请求响应同步通信](https://bugstack.cn/itstack-demo-netty-2/2019/08/22/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E4%B8%83-Netty%E8%AF%B7%E6%B1%82%E5%93%8D%E5%BA%94%E5%90%8C%E6%AD%A5%E9%80%9A%E4%BF%A1.html)
- [第 8 节：Netty心跳服务与断线重连](https://bugstack.cn/itstack-demo-netty-2/2019/08/23/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%85%AB-Netty%E5%BF%83%E8%B7%B3%E6%9C%8D%E5%8A%A1%E4%B8%8E%E6%96%AD%E7%BA%BF%E9%87%8D%E8%BF%9E.html)
- [第 9 节：Netty集群部署实现跨服务端通信的落地方案](https://bugstack.cn/itstack-demo-netty-2/2019/08/24/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E4%B9%9D-Netty%E9%9B%86%E7%BE%A4%E9%83%A8%E7%BD%B2%E5%AE%9E%E7%8E%B0%E8%B7%A8%E6%9C%8D%E5%8A%A1%E7%AB%AF%E9%80%9A%E4%BF%A1%E7%9A%84%E8%90%BD%E5%9C%B0%E6%96%B9%E6%A1%88.html)
- [第 10 节：Netty接收发送多种协议消息类型的通信处理方案](https://bugstack.cn/itstack-demo-netty-2/2019/08/25/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%8D%81-Netty%E6%8E%A5%E6%94%B6%E5%8F%91%E9%80%81%E5%A4%9A%E7%A7%8D%E5%8D%8F%E8%AE%AE%E6%B6%88%E6%81%AF%E7%B1%BB%E5%9E%8B%E7%9A%84%E9%80%9A%E4%BF%A1%E5%A4%84%E7%90%86%E6%96%B9%E6%A1%88.html)
- [第 11 节：Netty基于ChunkedStream数据流切块传输](https://bugstack.cn/itstack-demo-netty-2/2019/08/26/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%8D%81%E4%B8%80-Netty%E5%9F%BA%E4%BA%8EChunkedStream%E6%95%B0%E6%8D%AE%E6%B5%81%E5%88%87%E5%9D%97%E4%BC%A0%E8%BE%93.html)
- [第 12 节：Netty流量整形数据流速率控制分析与实战](https://bugstack.cn/itstack-demo-netty-2/2019/08/27/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%8D%81%E4%BA%8C-Netty%E6%B5%81%E9%87%8F%E6%95%B4%E5%BD%A2%E6%95%B0%E6%8D%AE%E6%B5%81%E9%80%9F%E7%8E%87%E6%8E%A7%E5%88%B6%E5%88%86%E6%9E%90%E4%B8%8E%E5%AE%9E%E6%88%98.html)
- [第 13 节：Netty基于SSL实现信息传输过程中双向加密验证](https://bugstack.cn/itstack-demo-netty-2/2019/08/28/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%8D%81%E4%B8%89-Netty%E5%9F%BA%E4%BA%8ESSL%E5%AE%9E%E7%8E%B0%E4%BF%A1%E6%81%AF%E4%BC%A0%E8%BE%93%E8%BF%87%E7%A8%8B%E4%B8%AD%E5%8F%8C%E5%90%91%E5%8A%A0%E5%AF%86%E9%AA%8C%E8%AF%81.html)

#### 高级应用篇

- [手写RPC框架第一章《自定义配置xml》](https://bugstack.cn/itstack-demo-netty-3/2019/09/01/%E6%89%8B%E5%86%99RPC%E6%A1%86%E6%9E%B6%E7%AC%AC%E4%B8%80%E7%AB%A0-%E8%87%AA%E5%AE%9A%E4%B9%89%E9%85%8D%E7%BD%AExml.html)
- [手写RPC框架第二章《netty通信》](https://bugstack.cn/itstack-demo-netty-3/2019/09/02/%E6%89%8B%E5%86%99RPC%E6%A1%86%E6%9E%B6%E7%AC%AC%E4%BA%8C%E7%AB%A0-netty%E9%80%9A%E4%BF%A1.html)
- [手写RPC框架第三章《RPC中间件》](https://bugstack.cn/itstack-demo-netty-3/2019/09/03/%E6%89%8B%E5%86%99RPC%E6%A1%86%E6%9E%B6%E7%AC%AC%E4%B8%89%E7%AB%A0-RPC%E4%B8%AD%E9%97%B4%E4%BB%B6.html)
- [websocket与下位机通过netty方式通信传输行为信息](https://bugstack.cn/itstack-demo-netty-3/2019/12/01/websocket%E4%B8%8E%E4%B8%8B%E4%BD%8D%E6%9C%BA%E9%80%9A%E8%BF%87netty%E6%96%B9%E5%BC%8F%E9%80%9A%E4%BF%A1%E4%BC%A0%E8%BE%93%E8%A1%8C%E4%B8%BA%E4%BF%A1%E6%81%AF.html)

### 源码分析篇

- [第 1 节：NioEventLoopGroup源码分析](https://bugstack.cn/itstack-demo-netty-4/2019/09/10/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E4%B8%80-NioEventLoopGroup%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.html)
- [第 2 节：ServerBootstrap配置与绑定启动](https://bugstack.cn/itstack-demo-netty-4/2019/09/11/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E4%BA%8C-ServerBootstrap%E9%85%8D%E7%BD%AE%E4%B8%8E%E7%BB%91%E5%AE%9A%E5%90%AF%E5%8A%A8.html)
- [第 3 节：Netty服务端初始化过程以及反射工厂的作用](https://bugstack.cn/itstack-demo-netty-4/2019/09/12/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E4%B8%89-Netty%E6%9C%8D%E5%8A%A1%E7%AB%AF%E5%88%9D%E5%A7%8B%E5%8C%96%E8%BF%87%E7%A8%8B%E4%BB%A5%E5%8F%8A%E5%8F%8D%E5%B0%84%E5%B7%A5%E5%8E%82%E7%9A%84%E4%BD%9C%E7%94%A8.html)
- [第 4 节：ByteBuf的数据结构在使用方式中的剖析](https://bugstack.cn/itstack-demo-netty-4/2019/09/13/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E5%9B%9B-ByteBuf%E7%9A%84%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E5%9C%A8%E4%BD%BF%E7%94%A8%E6%96%B9%E5%BC%8F%E4%B8%AD%E7%9A%84%E5%89%96%E6%9E%90.html)
- [第 5 节：一行简单的writeAndFlush都做了哪些事](https://bugstack.cn/itstack-demo-netty-4/2019/09/14/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E4%BA%94-%E4%B8%80%E8%A1%8C%E7%AE%80%E5%8D%95%E7%9A%84writeAndFlush%E9%83%BD%E5%81%9A%E4%BA%86%E5%93%AA%E4%BA%9B%E4%BA%8B.html)
- [第 6 节：Netty异步架构监听类Promise源码分析](https://bugstack.cn/itstack-demo-netty-4/2019/09/15/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E5%85%AD-Netty%E5%BC%82%E6%AD%A5%E6%9E%B6%E6%9E%84%E7%9B%91%E5%90%AC%E7%B1%BBPromise%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.html)

### Netty+JavaFx实战：仿桌面版微信聊天

本专栏是作者小傅哥使用 `JavaFx`、`Netty4.x`、`SpringBoot`、`Mysql`等技术栈和偏向于DDD领域驱动设计方式，搭建的仿桌面版微信聊天工程实现通信核心功能。

**源码**

- GitHub：[https://github.com/fuzhengwei/NaiveChat](https://github.com/fuzhengwei/NaiveChat)
- Gitee： [https://gitee.com/fustack/NaiveChat](https://gitee.com/fustack/NaiveChat)

**本专栏会以三个大章节内容，逐步进行讲解；**

- **第一章节**：**UI开发**。使用`JavaFx`与`Maven`搭建UI桌面工程，逐步讲解登录框体、聊天框体、对话框、好友栏等各项UI展示及操作事件。从而在这一章节中让Java 程序员学会开发桌面版应用。
- **第二章节**：**架构设计**。在这一章节中我们会使用DDD领域驱动设计的四层模型结构与Netty结合使用，架构出合理的分层框架。同时还有相应库表功能的设计。相信这些内容学习后，你一定也可以假设出更好的框架。
- **第三章节**：**功能实现**。这部分我们主要将通信中的各项功能逐步实现，包括；登录、添加好友、对话通知、消息发送、断线重连等各项功能。最终完成整个项目的开发，同时也可以让你从实践中学会技能。

* :memo: 目录
    * [开篇词](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * 第 1 章 - UI开发
        * [1.0：专栏学习简述以及全套源码获取](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [1.1：PC端微信页面拆分及JavaFx使用](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [1.2：登陆框体实现(结构定义、输入框和登陆)](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [1.3：登陆框体事件与接口](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [1.4：聊天框体实现一(整体结构定义、侧边栏)](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [1.5：聊天框体实现二(对话栏)](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [1.6：聊天框体实现三(对话聊天框)](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [1.7：聊天框体实现四(好友栏)](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [1.8：聊天框体实现五(好友填充框)](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [1.9：聊天框体事件定义](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [1.10：练习篇-聊天表情框体实现](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [1.11：解答篇-聊天表情框体实现](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * 第 2 章 - 架构设计
        * [2.1：服务端架构设计](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [2.2：通信协议包定义](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [2.3：客户端架构设计](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [2.4：数据库表结构设计](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * 第 3 章 - 功能实现 
        * [3.1：登陆功能实现](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [3.2：搜索和添加好友](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [3.3：对话通知与应答](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [3.4：用户与好友通信](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [3.5：用户与群组通信](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [3.6：断线重连恢复通信](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [3.7：服务端控制台搭建](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [3.8：练习篇-聊天表情发送功能实现](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
        * [3.9：解答篇-聊天表情发送功能实现](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)

## :tractor: 字节码编程

- GitHub：[https://github.com/fuzhengwei/itstack-demo-bytecode](https://github.com/fuzhengwei/itstack-demo-bytecode)
- Gitee： [https://gitee.com/fustack/itstack-demo-bytecode](https://gitee.com/fustack/itstack-demo-bytecode)

### ASM 

ASM是一个java字节码操纵框架，它能被用来动态生成类或者增强既有类的功能。ASM 可以直接产生二进制 class 文件，也可以在类被加载入 Java 虚拟机之前动态改变类行为。

- [第 1 节：ASM 使用入门，创建Helloworld以及基础方法使用](https://bugstack.cn/itstack-demo-agent/2020/03/25/ASM%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-%E5%A6%82%E6%9E%9C%E4%BD%A0%E5%8F%AA%E5%86%99CRUD-%E9%82%A3%E8%BF%99%E7%A7%8D%E6%8A%80%E6%9C%AF%E4%BD%A0%E6%B0%B8%E8%BF%9C%E7%A2%B0%E4%B8%8D%E5%88%B0.html)
- [第 2 节：JavaAgent+ASM字节码插桩采集方法名称以及入参和出参结果并记录方法耗时](https://bugstack.cn/itstack-demo-agent/2020/04/05/ASM%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-JavaAgent+ASM%E5%AD%97%E8%8A%82%E7%A0%81%E6%8F%92%E6%A1%A9%E9%87%87%E9%9B%86%E6%96%B9%E6%B3%95%E5%90%8D%E7%A7%B0%E4%BB%A5%E5%8F%8A%E5%85%A5%E5%8F%82%E5%92%8C%E5%87%BA%E5%8F%82%E7%BB%93%E6%9E%9C%E5%B9%B6%E8%AE%B0%E5%BD%95%E6%96%B9%E6%B3%95%E8%80%97%E6%97%B6.html)
- [第 3 节：用字节码增强技术给所有方法加上TryCatch捕获异常并输出](https://bugstack.cn/itstack-demo-agent/2020/04/16/ASM%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-%E7%94%A8%E5%AD%97%E8%8A%82%E7%A0%81%E5%A2%9E%E5%BC%BA%E6%8A%80%E6%9C%AF%E7%BB%99%E6%89%80%E6%9C%89%E6%96%B9%E6%B3%95%E5%8A%A0%E4%B8%8ATryCatch%E6%8D%95%E8%8E%B7%E5%BC%82%E5%B8%B8%E5%B9%B6%E8%BE%93%E5%87%BA.html)

### Javassist

- [第 1 节：基于javassist的第一个案例helloworld](https://bugstack.cn/itstack-demo-agent/2020/04/19/%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-Javassist%E7%AF%87%E4%B8%80-%E5%9F%BA%E4%BA%8Ejavassist%E7%9A%84%E7%AC%AC%E4%B8%80%E4%B8%AA%E6%A1%88%E4%BE%8Bhelloworld.html)
- [第 2 节：定义属性以及创建方法时多种入参和出参类型的使用](https://bugstack.cn/itstack-demo-agent/2020/04/20/%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-Javassist%E7%AF%87%E4%BA%8C-%E5%AE%9A%E4%B9%89%E5%B1%9E%E6%80%A7%E4%BB%A5%E5%8F%8A%E5%88%9B%E5%BB%BA%E6%96%B9%E6%B3%95%E6%97%B6%E5%A4%9A%E7%A7%8D%E5%85%A5%E5%8F%82%E5%92%8C%E5%87%BA%E5%8F%82%E7%B1%BB%E5%9E%8B%E7%9A%84%E4%BD%BF%E7%94%A8.html)
- [第 3 节：使用Javassist在运行时重新加载类「替换原方法输出不一样的结果」](https://bugstack.cn/itstack-demo-agent/2020/04/21/%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-Javassist%E7%AF%87%E4%B8%89-%E4%BD%BF%E7%94%A8Javassist%E5%9C%A8%E8%BF%90%E8%A1%8C%E6%97%B6%E9%87%8D%E6%96%B0%E5%8A%A0%E8%BD%BD%E7%B1%BB-%E6%9B%BF%E6%8D%A2%E5%8E%9F%E6%96%B9%E6%B3%95%E8%BE%93%E5%87%BA%E4%B8%8D%E4%B8%80%E6%A0%B7%E7%9A%84%E7%BB%93%E6%9E%9C.html)
- [第 4 节：通过字节码插桩监控方法采集运行时入参出参和异常信息](https://bugstack.cn/itstack-demo-agent/2020/04/27/%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-Javassist%E7%AF%87%E5%9B%9B-%E9%80%9A%E8%BF%87%E5%AD%97%E8%8A%82%E7%A0%81%E6%8F%92%E6%A1%A9%E7%9B%91%E6%8E%A7%E6%96%B9%E6%B3%95%E9%87%87%E9%9B%86%E8%BF%90%E8%A1%8C%E6%97%B6%E5%85%A5%E5%8F%82%E5%87%BA%E5%8F%82%E5%92%8C%E5%BC%82%E5%B8%B8%E4%BF%A1%E6%81%AF.html)
- [第 5 节：使用Bytecode指令码生成含有自定义注解的类和方法](https://bugstack.cn/itstack-demo-agent/2020/04/29/%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-Javassist%E7%AF%87%E4%BA%94-%E4%BD%BF%E7%94%A8Bytecode%E6%8C%87%E4%BB%A4%E7%A0%81%E7%94%9F%E6%88%90%E5%90%AB%E6%9C%89%E8%87%AA%E5%AE%9A%E4%B9%89%E6%B3%A8%E8%A7%A3%E7%9A%84%E7%B1%BB%E5%92%8C%E6%96%B9%E6%B3%95.html)

### Byte-Buddy

- [第 1 节：基于Byte Buddy语法创建的第一个HelloWorld](https://bugstack.cn/itstack-demo-agent/2020/05/08/%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-Byte-buddy%E7%AF%87%E4%B8%80-%E5%9F%BA%E4%BA%8EByte-Buddy%E8%AF%AD%E6%B3%95%E5%88%9B%E5%BB%BA%E7%9A%84%E7%AC%AC%E4%B8%80%E4%B8%AAHelloWorld.html)
- [第 2 节：监控方法执行耗时动态获取出入参类型和值](https://bugstack.cn/itstack-demo-agent/2020/05/12/%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-Byte-buddy%E7%AF%87%E4%BA%8C-%E7%9B%91%E6%8E%A7%E6%96%B9%E6%B3%95%E6%89%A7%E8%A1%8C%E8%80%97%E6%97%B6%E5%8A%A8%E6%80%81%E8%8E%B7%E5%8F%96%E5%87%BA%E5%85%A5%E5%8F%82%E7%B1%BB%E5%9E%8B%E5%92%8C%E5%80%BC.html)
- [第 3 节：使用委托实现抽象类方法并注入自定义注解信息](https://bugstack.cn/itstack-demo-agent/2020/05/14/%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-Byte-buddy%E7%AF%87%E4%B8%89-%E4%BD%BF%E7%94%A8%E5%A7%94%E6%89%98%E5%AE%9E%E7%8E%B0%E6%8A%BD%E8%B1%A1%E7%B1%BB%E6%96%B9%E6%B3%95%E5%B9%B6%E6%B3%A8%E5%85%A5%E8%87%AA%E5%AE%9A%E4%B9%89%E6%B3%A8%E8%A7%A3%E4%BF%A1%E6%81%AF.html)

### 基于JavaAgent的全链路监控

目前市面的全链路监控系统基本都是参考Google的Dapper来做的，本专题主要通过六个章节的代码实战，来介绍如何使用javaagent以及字节码应用，来实现一个简单的java代码链路流程监控。

- [第 1 节：嗨！JavaAgent](https://bugstack.cn/itstack-demo-agent/2019/07/10/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%B8%80-%E5%97%A8-JavaAgent.html)
- [第 2 节：通过字节码增加监控执行耗时](https://bugstack.cn/itstack-demo-agent/2019/07/11/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%BA%8C-%E9%80%9A%E8%BF%87%E5%AD%97%E8%8A%82%E7%A0%81%E5%A2%9E%E5%8A%A0%E7%9B%91%E6%8E%A7%E6%89%A7%E8%A1%8C%E8%80%97%E6%97%B6.html)
- [第 3 节：ByteBuddy操作监控方法字节码](https://bugstack.cn/itstack-demo-agent/2019/07/12/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%B8%89-ByteBuddy%E6%93%8D%E4%BD%9C%E7%9B%91%E6%8E%A7%E6%96%B9%E6%B3%95%E5%AD%97%E8%8A%82%E7%A0%81.html)
- [第 4 节：JVM内存与GC信息](https://bugstack.cn/itstack-demo-agent/2019/07/13/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E5%9B%9B-JVM%E5%86%85%E5%AD%98%E4%B8%8EGC%E4%BF%A1%E6%81%AF.html)
- [第 5 节：ThreadLocal链路追踪](https://bugstack.cn/itstack-demo-agent/2019/07/14/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%BA%94-ThreadLocal%E9%93%BE%E8%B7%AF%E8%BF%BD%E8%B8%AA.html)
- [第 6 节：开发应用级监控](https://bugstack.cn/itstack-demo-agent/2019/07/15/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E5%85%AD-%E5%BC%80%E5%8F%91%E5%BA%94%E7%94%A8%E7%BA%A7%E7%9B%91%E6%8E%A7.html)

## 🐾 故事

### 关于自己
- [今天你写博客了吗？](https://bugstack.cn/itstack-code-life/2020/10/25/今天你写博客了吗.html)
- [刚毕业不久，接私活赚了2万块！](https://bugstack.cn/itstack-code-life/2020/11/01/刚毕业不久-接私活赚了2万块.html)
- [让人怪不好意思的，粉丝破万，用了1年！](https://bugstack.cn/itstack-code-life/2020/10/09/让人怪不好意思的-粉丝破万-用了1年.html)
- [13年毕业，用两年时间从外包走进互联网大厂](https://bugstack.cn/itstack-code-life/2020/08/25/13年毕业-用两年时间从外包走进互联网大厂.html)
- [12天，这本《重学Java设计模式》PDF书籍下载量9k，新增粉丝1400人，Github上全球推荐榜！](https://bugstack.cn/itstack-code-life/2020/07/25/12天-这本-重学Java设计模式-PDF书籍下载量9k-新增粉丝1400人-Github上全球推荐榜.html)
- [北漂码农的我，把在大城市过成了屯子一样舒服，哈哈哈哈哈！](https://bugstack.cn/itstack-code-life/2020/11/29/%E5%8C%97%E6%BC%82%E7%A0%81%E5%86%9C%E7%9A%84%E6%88%91-%E6%8A%8A%E5%9C%A8%E5%A4%A7%E5%9F%8E%E5%B8%82%E8%BF%87%E6%88%90%E4%BA%86%E5%B1%AF%E5%AD%90%E4%B8%80%E6%A0%B7%E8%88%92%E6%9C%8D-%E5%93%88%E5%93%88%E5%93%88%E5%93%88%E5%93%88.html)
- [2020总结 | 作为技术号主的一年！](https://bugstack.cn/itstack-code-life/2020/12/27/2020%E6%80%BB%E7%BB%93-%E4%BD%9C%E4%B8%BA%E6%8A%80%E6%9C%AF%E5%8F%B7%E4%B8%BB%E7%9A%84%E4%B8%80%E5%B9%B4.html)
- [这一年，像踏码进货一样！](https://bugstack.cn/itstack-code-life/2021/01/31/%E8%BF%99%E4%B8%80%E5%B9%B4-%E6%83%B3%E8%B8%8F%E7%A0%81%E8%BF%9B%E8%B4%A7%E4%B8%80%E6%A0%B7.html)
- [毕业前写了20万行代码，让我从成为同学眼里的面霸！](https://bugstack.cn/itstack-code-life/2021/05/09/%E5%A4%A7%E5%AD%A6%E6%AF%95%E4%B8%9A%E8%A6%81%E5%86%99%E5%A4%9A%E5%B0%91%E8%A1%8C%E4%BB%A3%E7%A0%81-%E6%89%8D%E8%83%BD%E4%B8%8D%E7%94%A8%E8%8A%B1%E9%92%B1%E5%9F%B9%E8%AE%AD%E5%B0%B1%E6%89%BE%E5%88%B0%E4%B8%80%E4%BB%BD%E5%BC%80%E5%8F%91%E5%B7%A5%E4%BD%9C.html)
- [我，有12万+粉丝啦！](https://bugstack.cn/itstack-code-life/2021/06/20/%E6%88%91-%E6%9C%8910%E4%B8%87+%E7%B2%89%E4%B8%9D%E5%95%A6.html)

### 关于学习
- [讲道理，只要你是一个爱折腾的程序员，毕业找工作真的不需要再花钱培训！](https://bugstack.cn/itstack-code-life/2020/04/30/讲道理-只要你是一个爱折腾的程序员-毕业找工作真的不需要再花钱培训.html)
- [一个简单的能力，决定你是否会学习！](https://bugstack.cn/itstack-code-life/2020/11/08/一个简单的能力-决定你是否会学习.html)
- [UML类图还不懂？来看看这版乡村爱情类图，一把学会！](https://bugstack.cn/itstack-code-life/2020/10/18/UML类图还不懂-来看看这版乡村爱情类图-一把学会.html)
- [为了省钱，我用1天时间把PHP学了！](https://bugstack.cn/itstack-code-life/2020/10/11/为了省钱-我用1天时间把PHP学了.html)
- [大学四年到毕业工作5年的学习路线资源汇总](https://bugstack.cn/itstack-code-life/2020/03/31/大学四年到毕业工作5年的学习路线资源汇总.html)
- [汉字不能编程？别闹了，只是看着有点豪横！容易被开除！](https://bugstack.cn/itstack-demo-any/2020/05/05/%E6%B1%89%E5%AD%97%E4%B8%8D%E8%83%BD%E7%BC%96%E7%A8%8B-%E5%88%AB%E9%97%B9%E4%BA%86-%E5%8F%AA%E6%98%AF%E7%9C%8B%E7%9D%80%E6%9C%89%E7%82%B9%E8%B1%AA%E6%A8%AA-%E5%AE%B9%E6%98%93%E8%A2%AB%E5%BC%80%E9%99%A4.html) - [源码](https://github.com/fuzhengwei/CodeGuide/tree/master/src/itstack-demo-01)
- [鹿鼎记 · 韦小宝，丽春院恶搞版多线程学习！](https://bugstack.cn/itstack-code-life/2020/11/22/%E9%B9%BF%E9%BC%8E%E8%AE%B0-%E9%9F%A6%E5%B0%8F%E5%AE%9D-%E4%B8%BD%E6%98%A5%E9%99%A2-%E5%A4%A9%E5%9C%B0%E4%BC%9A-%E5%85%A5%E7%9A%87%E5%AE%AB%E7%AD%89%E4%BA%94%E4%B8%AA%E5%9C%BA%E6%99%AF-%E6%90%AD%E9%85%8D%E4%B8%8D%E5%90%8C%E5%89%A7%E6%83%85%E8%AE%B2%E8%A7%A3%E5%A4%9A%E7%BA%BF%E7%A8%8B%E5%92%8C%E9%94%81-%E7%9C%9F%E9%A6%99.html)
- [20年3月27日，Github被攻击。我的GitPage博客也挂了，紧急修复之路，也教会你搭建 Jekyll 博客！](https://bugstack.cn/itstack-code-life/2020/03/28/GithubAndMyBlogAttacked.html)
- [工作3年，看啥资料能月薪30K？](https://bugstack.cn/itstack-code-life/2020/12/20/%E5%B7%A5%E4%BD%9C3%E5%B9%B4-%E7%9C%8B%E5%95%A5%E8%B5%84%E6%96%99%E8%83%BD%E6%9C%88%E8%96%AA30K.html)
- [互联网大厂，常见研发线上事故总结！](https://bugstack.cn/itstack-code-life/2021/01/10/%E6%8F%A1%E8%8D%89-%E8%BF%99%E4%BA%9B%E7%A0%94%E5%8F%91%E4%BA%8B%E6%95%8530-%E6%88%91%E9%83%BD%E5%B9%B2%E8%BF%87.html)
- [数学，离一个程序员有多近？](https://bugstack.cn/itstack-code-life/2021/01/17/%E6%95%B0%E5%AD%A6-%E7%A6%BB%E4%B8%80%E4%B8%AA%E7%A8%8B%E5%BA%8F%E5%91%98%E6%9C%89%E5%A4%9A%E8%BF%91.html)
- [一天建4个，小傅哥教你搭博客！](https://bugstack.cn/itstack-code-life/2021/01/24/%E4%B8%80%E5%A4%A9%E5%BB%BA4%E4%B8%AA-%E5%B0%8F%E5%82%85%E5%93%A5%E6%95%99%E4%BD%A0%E6%90%AD%E5%8D%9A%E5%AE%A2.html)

### 关于职场
- [工作两年简历写成这样，谁要你呀！](https://bugstack.cn/itstack-code-life/2020/04/11/工作两年简历写成这样-谁要你呀.html)
- [PPT画成这样，述职答辩还能过吗？](https://bugstack.cn/itstack-code-life/2020/09/27/PPT画成这样-述职答辩还能过吗.html)
- [90%的程序员，都没用过多线程和锁，怎么成为架构师？](https://bugstack.cn/itstack-code-life/2020/12/06/90-%E7%9A%84%E7%A8%8B%E5%BA%8F%E5%91%98-%E9%83%BD%E6%B2%A1%E7%94%A8%E8%BF%87%E5%A4%9A%E7%BA%BF%E7%A8%8B%E5%92%8C%E9%94%81-%E6%80%8E%E4%B9%88%E6%88%90%E4%B8%BA%E6%9E%B6%E6%9E%84%E5%B8%88.html)
- [程序员为什么热衷于造轮子，升职加薪吗？](https://bugstack.cn/itstack-code-life/2020/09/20/程序员为什么热衷于造轮子-升职加薪吗.html)
- [码德需求，这不就是产品给我留的数学作业！](https://bugstack.cn/itstack-code-life/2020/12/13/%E7%A0%81%E5%BE%B7%E9%9C%80%E6%B1%82-%E8%BF%99%E4%B8%8D%E5%B0%B1%E6%98%AF%E4%BA%A7%E5%93%81%E7%BB%99%E6%88%91%E7%95%99%E7%9A%84%E6%95%B0%E5%AD%A6%E4%BD%9C%E4%B8%9A.html)
- [一次代码评审，差点过不了试用期！](https://bugstack.cn/itstack-demo-any/2020/09/14/一次代码评审-差点过不了试用期.html)
- [握草，你竟然在代码里下毒！](https://bugstack.cn/itstack-demo-any/2020/09/06/握草-你竟然在代码里下毒.html)
- [谁说明天上线，这货压根不知道开发流程！](https://bugstack.cn/itstack-code-life/2021/01/03/%E8%B0%81%E8%AF%B4%E6%98%8E%E5%A4%A9%E4%B8%8A%E7%BA%BF-%E8%BF%99%E8%B4%A7%E5%8E%8B%E6%A0%B9%E4%B8%8D%E7%9F%A5%E9%81%93%E5%BC%80%E5%8F%91%E6%B5%81%E7%A8%8B.html)

## :hammer: 工具&软件

- [NetAssist下载](https://download.csdn.net/download/yao__shun__yu/11835105) 
- [谢飞机简历模板合集，实习生、应届生、工作1~3年、工作3~5年.zip](https://download.csdn.net/download/Yao__Shun__Yu/15906757)
- [技术架构涵盖内容和演变过程总结的架构图.pptx](https://download.csdn.net/download/Yao__Shun__Yu/15567125)
- [elasticsearch-7.10.2、kibana-7.10.2 Mac 版.zip](https://download.csdn.net/download/Yao__Shun__Yu/15729298)
- [elasticsearch-7.10.2、kibana-7.10.2 Windows 版.zip](https://download.csdn.net/download/Yao__Shun__Yu/15729232)

## :cloud: 云服务学习

- **云服务器**：[79元1年，226元3年，新人服务器](https://dashi.aliyun.com/site/xiaofuge/618)
- **视频课程**：关注小傅哥的微信视频号，小傅哥の码场
![小傅哥の码场](https://github.com/fuzhengwei/CodeGuide/blob/master/docs/assets/img/fuvideo.jpeg?raw=true)

- [x] 第1天：购买79元服务器，[https://dashi.aliyun.com/site/xiaofuge/618](https://dashi.aliyun.com/site/xiaofuge/618) 配置云服务器，xshell链接，初始化环境	
- [x] 第2天：重置系统安装宝塔，配置博客站点 [https://www.bilibili.com/video/BV1HK4y1X7Dr](https://www.bilibili.com/video/BV1HK4y1X7Dr)
- [x] 第3天：购买.cn域名，提交备案操作手册	
- [x] 第4天：搭建java运行环境、jdk、tomcat、mysql [https://www.bilibili.com/video/BV1BZ4y1W7fC](https://www.bilibili.com/video/BV1BZ4y1W7fC)
- [x] 第5天：部署Netty聊天室到云环境，与室友测试聊天程序 [https://www.bilibili.com/video/BV1BZ4y1W7fC](https://www.bilibili.com/video/BV1BZ4y1W7fC)
- [ ] 第6天：安装Elasticsearch、elasticsearch-head、Kibana，测试x-pack-sql-jdbc与云服务es数据交互	
- [ ] 第7天：安装docker容器，配置云环境，练习命令	
- [ ] 第8天：又购买2台，搭配使用，部署分布式系统，rpc、mq、分布式任务，测试应用程序	
- [ ] 第9天：mysql数据通过binlog同步到ES，以及双机备份	
- [ ] 第10天：域名备案完成，使用宝塔配置域名、ssl、反向代理等，搭建完整博客环境	
- [ ] 第11天：学习phpwind、Discuz，搭建论坛	
- [ ] 第12天：搭建个人完整博客系统，采用Jekyll静态博客，本地初始化，通过ftp上传到云服务	
- [ ] 第13天：搭建wordpress，尝试其他动态博客配置使用	
- [ ] 第14天：选型Go语言 cloudreve 网盘，进行搭建测试	
- [ ] 第15天：选型PHP语言 nextcloud网盘，进行搭建测试	
- [ ] 第16天：承接老舅的需求，把域名和服务器以及一个乡村超时论坛，5万元，卖给老舅。 

---

##  转载分享

*建立本开源项目的初衷是基于个人学习与工作中对 Java 相关技术栈的总结记录，在这里也希望能帮助一些在学习 Java 过程中遇到问题的小伙伴，如果您需要转载本仓库的一些文章到自己的博客，请按照以下格式注明出处，谢谢合作。*

```
作者：小傅哥
链接：https://bugstack.cn
来源：bugstack虫洞栈
```

## 与我联系

- **加群交流**
    本群的宗旨是给大家提供一个良好的技术学习交流平台，所以杜绝一切广告！由于微信群人满 100 之后无法加入，请扫描下方二维码先添加作者 “小傅哥” 微信(fustack)，备注：加群。
    
    <img src="https://itstack.org/_media/fustack.png?x-oss-process=style/may" width="180" height="180"/>

- **公众号(bugstack虫洞栈)**
    沉淀、分享、成长，专注于原创专题案例，以最易学习编程的方式分享知识，让自己和他人都能有所收获。目前已完成的专题有；Netty4.x实战专题案例、用Java实现JVM、基于JavaAgent的全链路监控、手写RPC框架、DDD专题案例、源码分析等。
    
    <img src="https://itstack.org/_media/qrcode.png?x-oss-process=style/may" width="180" height="180"/>

## 参与贡献

1. 如果您对本项目有任何建议或发现文中内容有误的，欢迎提交 issues 进行指正。
2. 对于文中我没有涉及到知识点，欢迎提交 PR。

## 致谢

感谢以下人员对本仓库做出的贡献或者对小傅哥的赞赏，当然不仅仅只有这些贡献者，这里就不一一列举了。如果你希望被添加到这个名单中，并且提交过 Issue 或者 PR，请与我联系。

**:seedling: 感谢大家对仓库建设的贡献**

<a href="https://github.com/linw7">
    <img src="https://avatars0.githubusercontent.com/u/3761578?s=460&v=4" style="border-radius:5px" width="50px">
</a> 
<a href="https://github.com/g10guang">
    <img src="https://avatars0.githubusercontent.com/u/30902679?s=400&v=4" style="border-radius:5px" width="50px">
</a> 
<a href="https://github.com/g10guang">
    <img src="https://avatars1.githubusercontent.com/u/15908832?s=180&v=4" style="border-radius:5px" width="50px">
</a>
