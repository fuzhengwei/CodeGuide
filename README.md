# CodeGuide | 代码指南，沉淀、分享、成长，让自己和他人都能有所收获！

> **作者：** 小傅哥，Java Developer，[:pencil2: 虫洞 · 科技栈，作者](https://bugstack.cn)，[:trophy: CSDN 博客专家](https://bugstack.blog.csdn.net)

> 本代码库是作者小傅哥多年从事一线互联网```Java```开发的学习历程技术汇总，旨在为大家提供一个清晰详细的学习教程，侧重点更倾向编写Java核心内容。如果本仓库能为您提供帮助，请给予支持(关注、点赞、分享)！

<div align="center">
<a href="https://github.com/fuzhengwei/CodeGuide"><img src="https://badgen.net/github/stars/fuzhengwei/CodeGuide?icon=github&color=4ab8a1"></a>
<a href="https://github.com/fuzhengwei/CodeGuide"><img src="https://badgen.net/github/forks/fuzhengwei/CodeGuide?icon=github&color=4ab8a1"></a>
<a href="https://itstack.org" target="_blank"><img src="https://itstack.org/_media/onlinebook.svg"></a>
<a href="https://itstack.org/_media/qrcode.png?x-oss-process=style/may"><img src="https://itstack.org/_media/wxbugstack.svg"></a>
</div>
<br/>

| 算法 | Java | Netty4.x专题 | JVM | 字节码编程 | 中间件开发 |  源码分析  | 走码观花 |
|:---:|:---:|:---|:---|:---:|:---|:---|:---|
|	算法	|	JVM	|	Java	|	Spring	|	Netty4.x专题	|	中间件开发	|	Drools规则引擎	|	架构设计	|	走码观花	|
|	野路子搞算法	|	用Java实现JVM	|	基础编程	|	源码分析	|	基础入门篇	|	SpringBoot中间件	|	Drools入门	|	DDD领域驱动设计	|	故事	|
|		|		|		|		|	中级拓展篇	|		|		|	字节码编程	|	面试	|
|		|		|		|		|	高级应用篇	|		|		|	JavaAgent全链路监控	|		|
|		|		|		|		|	源码分析篇	|		|		|		|		|

| Java基础 | JVM虚拟机| Spring源码 | Netty4.x专题 | 领域驱动设计 | 中间件开发 | JavaAgent | 架构框架搭建 | 
| :---: |  :---: |  :---: |  :---: |  :---: |  :---: |  :---: |  :---: | 
| [:coffee:](#coffee-Java基础编程) | [:computer:](#用Java实现jvm虚拟机) | [:pencil2:](#pencil2-Spring系列源码解读) | [:sound:](#sound-Netty4.x专题) | [:triangular_ruler:](#triangular_ruler-DDD领域驱动设计) | [:electric_plug:](#electric_plug-中间件开发) | [:ghost:](#ghost-JavaAgent全链路监控) | [:art:](#art-架构框架搭建) |

| 序号 | 标识 | 名称 | 描述 | 下载 |
|:---:|:---:|:---|:---|:---:|
| 1 | :sound: | Netty4.x专题 | Netty实战专题案例，Netty4.x案例从简单入门到应用实战，全篇37节优秀案例+实战源码[基础篇(12)、拓展篇(13)、应用篇(3章+)、源码篇(6)]，以上章节全部完成并不断持续更新中。| [download](#sound-Netty4.x专题) |
| 2 | :electric_plug: | 手写RPC框架 | RPC是一种远程调用的通信协议，例如dubbo、thrift等，我们在互联网高并发应用开发时候都会使用到类似的服务。本专题主要通过三个章节简单的实现rpc基础功能，来深入学习rpc是如何交互通信的。| [download](#) |
| 3 | :computer: | 用Java实现JVM | 本专题主要介绍如何通过java代码来实现JVM的基础功能（搜索解析class文件、字节码命令、运行时数据区等），从而让java程序员通过最熟知的java程序，学习JVM是如何将java程序一步步跑起来的。| [download](#) |
| 4 | :ghost: | 基于JavaAgent的全链路监控 | 目前市面的全链路监控系统基本都是参考Google的Dapper来做的，本专题主要通过六个章节的代码实战，来介绍如何使用javaagent以及字节码应用，来实现一个简单的java代码链路流程监控。| [download](#) |
| 5 | :shower: | iot-gateway网关案例 | 基于Netty实现的物联网网关服务，支持百万客户端连接，压力测试ing...，并优化了与服务端集群通信对平均算法做了优化,本次上传代码添加了很多功能，摒弃了以往只做心跳维护、数据转发的功能。| [download](#) |
| 6 | :triangular_ruler: | DDD领域驱动设计落地 | 本专题以DDD实战落地为根本，分章节设计不同的架构模型，学习并实战是奔入应用级开发最快的方法，Hi HelloWorld！我来了。| [download](#) |
| 7 | :outbox_tray: | SpringCloud入门案例 | Spring Cloud是一系列框架的有序集合。它利用Spring Boot的开发便利性巧妙地简化了分布式系统基础设施的开发，如服务发现注册、配置中心、消息总线、负载均衡、断路器、数据监控等。| [download](#) |
| 8 | :performing_arts: | 微信公众号开发 | 这是一套基于领域驱动设计方式搭建的Java公众号开发工程，主要服务于博客与公众号之间打通，引导用户关注公众号，做粉丝回流。 | [download](#) |
| 9 | :nut_and_bolt: | SpringBoot中间件开发 | Spring Boot 中间件开发，基于服务治理为目的将非业务行为的核心逻辑剥离出来开发为独立的中间件，赋能于业务系统快速开发。 | [download](#) |
| 10 | :art: | 服务框架搭建 | 服务框架搭建，依赖于不同的业务诉求搭建出各种服务功能的框架结构。将逐步完成；单体服务应用(适合于ERP和个人)、分库分表应用、Mq服务、任务服务、分布式服务、RPC服务等。| [download](#) |
| 11 | :flashlight: | 源码分析(Spring、Mybatis、Schedule) | 服源码分析以最核心干货内容为入手，将平时开发使用到的Spring、Mybatis、多线程等逐个渗透分析研究。不在只是单纯使用，而是要从原理分析获取更多的技术成长。| [download](#) |
| 12 | :airplane: | Drools规则引擎 | Drools 是 Java 语言基于Rete算法编写的规则引擎，可以方便的使用声明表达业务逻辑，非常简单易用。本专题会从入门开始逐步完成对Drools的讲解。| [download](#) |
| 13 | :tractor: | ASM字节码编程 | ASM是一个java字节码操纵框架，它能被用来动态生成类或者增强既有类的功能。ASM 可以直接产生二进制 class 文件，也可以在类被加载入 Java 虚拟机之前动态改变类行为。 | [download](#) |
| 14 | :paw_prints: | 我的大学四年到毕业工作5年的学习路线资源和面试汇总 | 一直有伙伴问小傅哥，有没有一个Java的学习路线和面试，最好再有一些相关的资料、书籍、视频。因为现在自己学习也不知道哪不会，看到这个学这个，看到那个学那个，也摸不到头，还比较混乱。特别希望有一个大学到毕业的学习路线整理。 | [download](#)  |

<br/>
<div align="center">
    <a href="https://itstack.org" style="text-decoration:none"><img src="https://itstack.org/_media/icon.svg" width="128px"></a>
</div>
<br/>  





## :sound: Netty4.x专题

### 基础入门篇

| 序号 | 文章阅读 | 源码下载 |
|:---:|:---|:---|
| 1 | [`netty案例，netty4.1基础入门篇零《初入JavaIO之门BIO、NIO、AIO实战练习》`](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724988&idx=1&sn=99a4f3677d2c5a9499cb06eba2cd37fe&chksm=8f613b9eb816b28876828985ab5b617fc2b3c33c3ae5227f29341e15bf50874fcfa736477d86&token=645297373&lang=zh_CN#rd) | [itstack-demo-netty-1-00](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-1-00) |
| 2 | [`netty案例，netty4.1基础入门篇一《嗨！NettyServer》`](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724732&idx=1&sn=39f47381b6f95b9f5ddd4385c10e50dd&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-1-01](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-1-01) |
| 3 | [`netty案例，netty4.1基础入门篇二《NettyServer接收数据》`](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724754&idx=1&sn=0d4ab65a57edc69f31357f42e4327279&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-1-02](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-1-02) |
| 4 | [`netty案例，netty4.1基础入门篇三《NettyServer字符串解码器》`](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724760&idx=1&sn=4e5faf45de87e2c7346e9053628b51b0&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-1-03](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-1-03) |
| 5 | [`netty案例，netty4.1基础入门篇四《NettyServer收发数据》`](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724766&idx=1&sn=a39a05c550f43467a3c3cedd070f8226&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-1-04](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-1-04) |
| 6 | [`netty案例，netty4.1基础入门篇五《NettyServer字符串编码器》`](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724771&idx=1&sn=8f70169b9f329a8cad0071a28f27a65a&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-1-05](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-1-05) |
| 7 | [`netty案例，netty4.1基础入门篇六《NettyServer群发消息》`](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724778&idx=1&sn=72e4b1ea5323475b16e99c6720c7069d&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-1-06](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-1-06) |
| 8 | [`netty案例，netty4.1基础入门篇七《嗨！NettyClient》`](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724783&idx=1&sn=bc827e680ebd533fe67720fd695257be&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-1-07](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-1-07) |
| 9 | [`netty案例，netty4.1基础入门篇八《NettyClient半包粘包处理、编码解码处理、收发数据方式》`](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724791&idx=1&sn=24fa3bf35922abbc54a95d7d724902cd&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-1-08](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-1-08) |
| 10 | [`netty案例，netty4.1基础入门篇九《自定义编码解码器，处理半包、粘包数据》`](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724871&idx=1&sn=83b4d8ac95962f2c0043e25f207cb7d7&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-1-09](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-1-09) |
| 11 | [`netty案例，netty4.1基础入门篇十《关于ChannelOutboundHandlerAdapter简单使用》`](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724887&idx=1&sn=de3a948d945151f78f7ee277e728043e&scene=19&token=540761663&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-1-10](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-1-10) |
| 12 | [`netty案例，netty4.1基础入门篇十一《netty udp通信方式案例Demo》`](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724927&idx=1&sn=a16bc8e98d6a27816da0896adcc83778&chksm=8f613bddb816b2cbb8eb7a4f332c547166568f55eff00448e19c5a0dfcf1b2934ff2d99f1407&token=1596842785&lang=zh_CN#rd) | [itstack-demo-netty-1-11](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-1-11) |
| 13 | [`netty案例，netty4.1基础入门篇十二《简单实现一个基于Netty搭建的Http服务》`](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724932&idx=1&sn=eb1631ddbd0e7a0dcb3a655374631f48&chksm=8f613ba6b816b2b03752cedd89ae451e671ad8727ad4be2d777c88b96c6487212d11d3dcd6fc&token=1872860325&lang=zh_CN#rd) | [itstack-demo-netty-1-12](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-1-12) |

### 中级拓展篇

| 序号 | 文章阅读 | 源码下载 |
|:---:|:---|:---|
| 1 |  [netty案例，netty4.1中级拓展篇一《Netty与SpringBoot整合》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724796&idx=1&sn=ce5dc3c913d464b0e2e4e429a17bb01e&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-2-0](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-2-) |
| 2 |  [netty案例，netty4.1中级拓展篇二《Netty使用Protobuf传输数据》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724807&idx=1&sn=e0d27e61423a8cf0ed7bc0b9663d1ff7&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-2-0](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-2-) |
| 3 |  [netty案例，netty4.1中级拓展篇三《Netty传输Java对象》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724806&idx=1&sn=bb986119b9cdd950e2e6d995295e7f06&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-2-0](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-2-) |
| 4 |  [netty案例，netty4.1中级拓展篇四《Netty传输文件、分片发送、断点续传》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724816&idx=1&sn=dd4a5b492c9d0b07cffcf35e51ffd2fd&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-2-0](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-2-) |
| 5 |  [netty案例，netty4.1中级拓展篇五《基于Netty搭建WebSocket，模仿微信聊天页面》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724747&idx=1&sn=603319014b7fd4e1f56600abf03948d2&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-2-0](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-2-) |
| 6 |  [netty案例，netty4.1中级拓展篇六《SpringBoot+Netty+Elasticsearch收集日志信息数据存储》](https://mp.weixin.qq.com/s/Pob79caA9AuOuHBEhVvu-g) | [itstack-demo-netty-2-0](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-2-) |
| 7 |  [netty案例，netty4.1中级拓展篇七《Netty请求响应同步通信》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724835&idx=1&sn=93fc37b8c35b19f91ef246632a37bab9&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-2-0](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-2-) |
| 8 |  [netty案例，netty4.1中级拓展篇八《Netty心跳服务与断线重连》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724845&idx=1&sn=8631c590ff4876ba0b7af64df16fc54b&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-2-0](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-2-) |
| 9 |  [netty案例，netty4.1中级拓展篇九《Netty集群部署实现跨服务端通信的落地方案》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724922&idx=1&sn=5af75ca113cf473c9e5a8deee7c256a2&chksm=8f613bd8b816b2ce3f2b227122ad09d9f18f20c131a16841fb6b44a59224fb0e6527e67bbeda&token=932005726&lang=zh_CN#rd) | [itstack-demo-netty-2-0](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-2-) |
| 10 | [netty案例，netty4.1中级拓展篇十《Netty接收发送多种协议消息类型的通信处理方案》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724938&idx=1&sn=646cf29dc8e5da92151a3207bbc129e4&chksm=8f613ba8b816b2be6ae702d330d6d89b0a225c76e4ee570dfead81e67b1a33b110d5098fe59f&token=178418407&lang=zh_CN#rd)  |[itstack-demo-netty-2-0](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-2-) |
| 11 | [netty案例，netty4.1中级拓展篇十一《Netty基于ChunkedStream数据流切块传输》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724953&idx=1&sn=42a0031e9b757f4d941a02b686e64e12&chksm=8f613bbbb816b2ad495fbe3a3f645ff74b722a851deb54b39aeaba975d934c4f1eabada40339&token=178418407&lang=zh_CN#rd)  |[itstack-demo-netty-2-0](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-2-) |
| 12 | [netty案例，netty4.1中级拓展篇十二《Netty流量整形数据流速率控制分析与实战》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724964&idx=1&sn=304e3cc882516c36a452a2712910858e&chksm=8f613b86b816b290ca3768c36a79484dfb28e844bf6e092c2f84bdf3eb291cf3ddc792db6108&token=178418407&lang=zh_CN#rd)  |[itstack-demo-netty-2-0](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-2-) |
| 13 | [netty案例，netty4.1中级拓展篇十三《Netty基于SSL实现信息传输过程中双向加密验证》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724977&idx=1&sn=cab1a02be74a62d8184c18abc5eba6b5&chksm=8f613b93b816b2850b17a05b74d7debec11eb3dfaa8d9763f1af63825b3e24685bcddd10a38e&token=1898926011&lang=zh_CN#rd)  |[itstack-demo-netty-2-0](https://github.com/fuzhengwei/CodeGuide/src/itstack-demo-netty/tree/master/itstack-demo-netty-2-) |

### 高级应用篇

| 序号 | 文章阅读 | 源码下载 |
|:---:|:---|:---|
| 1 | [手写RPC框架第一章《自定义配置xml》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724509&idx=1&sn=ee1ed819d09d7e34bde1ce7d3a2acca6&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-3]() |
| 2 | [手写RPC框架第二章《netty通信》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=503240874&idx=1&sn=053799da7ac8cd068ed086aa453ceeaf&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-3]() |
| 3 | [手写RPC框架第三章《RPC中间件》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=503240878&idx=1&sn=9ba8180767237b439960363ecd2be779&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-3]() |
| 4 | [基于Netty实践搭建的物联网网关iot-gatway](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724958&idx=1&sn=d26b475519c3a6baaf2e66be8b683d08&chksm=8f613bbcb816b2aab049219008e118a3b2ad37f02688be1028a5f882c20f446b1d76c05e241f&token=178418407&lang=zh_CN#rd) | [itstack-demo-netty-3]() |
| 5 | [websocket与下位机通过netty方式通信传输行为信息](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650725112&idx=1&sn=28bcd4fcdcac4eb6fe70ce65d0a1c172&chksm=8f613b1ab816b20c622f53e215a8478f09f76bb93c38a975149f01ff28b6e594209317d9fbfb&token=1234653459&lang=zh_CN#rd) | [itstack-demo-netty-3]() |

### 源码分析篇

| 序号 | 文章阅读 | 源码下载 |
|:---:|:---|:---:|
| 1 | [netty案例，netty4.1源码分析篇一《NioEventLoopGroup源码分析》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724880&idx=1&sn=2ae28b11fd51fbb5fffe48f80e5f05d1&chksm=8f613bf2b816b2e45df702b03a3473e47292784000a5e2bc12b6f99e8cc59006a1cb564d5a30&token=1869209761&lang=zh_CN#rd) | - |
| 2 | [netty案例，netty4.1源码分析篇二《ServerBootstrap配置与绑定启动》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724901&idx=1&sn=7203f80a077c80fc544214a9a9806571&chksm=8f613bc7b816b2d116030107ee41984e738ae9ec7d0740e97eed84259d52d00bc082d441ef38&token=1869209761&lang=zh_CN#rd) | - |
| 3 | [netty案例，netty4.1源码分析篇三《Netty服务端初始化过程以及反射工厂的作用》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724906&idx=1&sn=69cbfe07f7634b5d022a0d0c1a7e16ca&chksm=8f613bc8b816b2ded4172e54078f76f2113ada2643f87586b3576402c594afa13a3f70a449d6&token=1869209761&lang=zh_CN#rd) | - |
| 4 | [netty案例，netty4.1源码分析篇四《ByteBuf的数据结构在使用方式中的剖析》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724948&idx=1&sn=ab33e0c49d58903e463561082d124526&chksm=8f613bb6b816b2a05d508a5e7976afd751b0353d3c2edd805b012f6ab0d7ad32b51c99958d6f&token=178418407&lang=zh_CN#rd) | - |
| 5 | [netty案例，netty4.1源码分析篇五《一行简单的writeAndFlush都做了哪些事》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724970&idx=1&sn=c2f9592392da491eaa2b1d33024ac73b&chksm=8f613b88b816b29e370980c4b9bc3d1890322bfd72ce6eead3899bcb4e34f284ff9551412912&token=1628536267&lang=zh_CN#rd) | - |
| 6 | [netty案例，netty4.1源码分析篇六《Netty异步架构监听类Promise源码分析》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724983&idx=1&sn=36aaf2598cea0c6ce6ba1b1cb2ee450e&chksm=8f613b95b816b2837049080b95483b9a6fc56794fdf8716bf6732933b02ff07ad4e7be11a154&token=70507448&lang=zh_CN#rd) | - |

## :electric_plug: 手写RPC框架	

| 序号 | 文章阅读 | 源码下载 |
|:---:|:---|:---:|
| 1 | [手写RPC框架第一章《自定义配置xml》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724509&idx=1&sn=ee1ed819d09d7e34bde1ce7d3a2acca6&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-3]() |
| 2 | [手写RPC框架第二章《netty通信》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=503240874&idx=1&sn=053799da7ac8cd068ed086aa453ceeaf&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-3]() |
| 3 | [手写RPC框架第三章《RPC中间件》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=503240878&idx=1&sn=9ba8180767237b439960363ecd2be779&scene=19&token=583635275&lang=zh_CN#wechat_redirect) | [itstack-demo-netty-3]() |

## :computer: 用Java实现JVM

| 序号 | 文章阅读 | 源码下载 |
|:---:|:---|:---:|
| 1 | [用Java实现JVM第一章《命令行工具》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=503240911&idx=1&sn=5fb72ae66eba1428cf684260294f991d&scene=19#wechat_redirect) | [itstack-demo-jvm]() |
| 2 | [用Java实现JVM第二章《搜索class文件》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=503240930&idx=1&sn=e9f4e28e655eea6dc5e2abf71980adf7&scene=19#wechat_redirect) | [itstack-demo-jvm]() |
| 3 | [用Java实现JVM第三章《解析class文件》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=503240934&idx=1&sn=a83433523ca8f38e868f246773337966&scene=19#wechat_redirect) | [itstack-demo-jvm]() |
| 4 | [用Java实现JVM第三章《解析class文件》附[classReader拆解]](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=503240939&idx=1&sn=5d06411fdbbe7876660ebfdfdeff0b98&scene=19#wechat_redirect) | [itstack-demo-jvm]() |
| 5 | [用Java实现JVM第四章《运行时数据区》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=503240943&idx=1&sn=5ccd536d03150a89c97b43fc6f8907c8&scene=19#wechat_redirect) | [itstack-demo-jvm]() |
| 6 | [用Java实现JVM第五章《指令集和解释器》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=503240947&idx=1&sn=c0d288d4ece0090a7ba70445929de684&scene=19#wechat_redirect) | [itstack-demo-jvm]() |
| 7 | [用Java实现JVM第六章《类和对象》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=503240951&idx=1&sn=8327e5e0401a98bfe4b75ea2470cfe74&scene=19#wechat_redirect) | [itstack-demo-jvm]() |
| 8 | [用Java实现JVM第七章《方法调用和返回》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=503240957&idx=1&sn=abd60851d36d617718cd36f7c8ead95c&scene=19#wechat_redirect) | [itstack-demo-jvm]() |
| 9 | [用Java实现JVM第八章《数组和字符串》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=503240967&idx=1&sn=850fc94e120e8ab8f81f895e5c714d20&scene=19#wechat_redirect) | [itstack-demo-jvm]() |
| 10 | [用Java实现JVM第九章《本地方法调用》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=503240969&idx=1&sn=7f9da902cb2c17e91cf50e893141112d&scene=19#wechat_redirect) | [itstack-demo-jvm]() |
| 11 | [用Java实现JVM第十章《异常处理》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=503240975&idx=1&sn=129884bc3383f80089d30e65fb2e663a&scene=19#wechat_redirect) | [itstack-demo-jvm]() |

## :ghost: 基于JavaAgent的全链路监控

| 序号 | 文章阅读 | 源码下载 |
|:---:|:---|:---:|
| 1 | [基于JavaAgent的全链路监控一《嗨！JavaAgent》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724691&idx=1&sn=5d203764918ff62b74f70f7e0ebd6eb9&scene=19#wechat_redirect) | [itstack-demo-agent]() |
| 2 | [基于JavaAgent的全链路监控二《通过字节码增加监控执行耗时》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724696&idx=1&sn=872c776cdd5c401412d83975526511f1&scene=19#wechat_redirect) | [itstack-demo-agent]() |
| 3 | [基于JavaAgent的全链路监控三《ByteBuddy操作监控方法字节码》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724702&idx=1&sn=7894ea7c6c4c9fa1d73e6d9e2145d58b&scene=19#wechat_redirect) | [itstack-demo-agent]() |
| 4 | [基于JavaAgent的全链路监控四《JVM内存与GC信息》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724707&idx=1&sn=23ec3f7d57058ea7f343756d4075e62f&scene=19#wechat_redirect) | [itstack-demo-agent]() |
| 5 | [基于JavaAgent的全链路监控五《ThreadLocal链路追踪》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724712&idx=1&sn=f27afde798c982d45b60d2b2d5256567&scene=19#wechat_redirect) | [itstack-demo-agent]() |
| 6 | [基于JavaAgent的全链路监控六《开发应用级监控》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=503241065&idx=1&sn=aec38fe68da6427bb017d91e6ef72e14&scene=19#wechat_redirect) | [itstack-demo-agent]() |
| 7 | [基于jvmti定位java异常信息](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724993&idx=1&sn=5eb30ad62b51a7e383967b89eab64c9f&scene=19#wechat_redirect) | [itstack-demo-agent]() |

## :shower: iot-gateway网关案例

| 序号 | 文章阅读 | 源码下载 |
|:---:|:---|:---|
| 1 | [基于Netty实践搭建的物联网网关iot-gatway](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650724958&idx=1&sn=d26b475519c3a6baaf2e66be8b683d08&chksm=8f613bbcb816b2aab049219008e118a3b2ad37f02688be1028a5f882c20f446b1d76c05e241f&token=178418407&lang=zh_CN#rd) | [itstack-demo-netty-3]() |

## :triangular_ruler: DDD领域驱动设计落地

| 序号 | 文章阅读 | 源码下载 |
|:---:|:---|:---|
| 1 | [DDD专题案例一《初识领域驱动设计DDD落地》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650725016&idx=1&sn=cda59e3b2f54f1377f284d93e85333d1&scene=19#wechat_redirect) | [itstack-demo-ddd]() |
| 2 | [DDD专题案例二《领域层决策规则树服务设计》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650725021&idx=1&sn=0c07a1f833c0731b2b51da94ee833a27&scene=19#wechat_redirect) | [itstack-demo-ddd]() |
| 3 | [DDD专题案例三《领域驱动设计架构基于SpringCloud搭建微服务》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650725028&idx=1&sn=3b1f0af1b93d49d77e4332463956905d&scene=19#wechat_redirect) | [itstack-demo-ddd]() |

## :performing_arts: 微信公众号开发

| 序号 | 文章阅读 | 源码下载 |
|:---:|:---|:---|
| 1 | [并不想吹牛皮，但！为了把Github博客粉丝转移到公众号，我干了！](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650725099&idx=1&sn=ae9469bedc9edeff9535aace12cdf04c&scene=19#wechat_redirect) | [itstack-ark-wx]() |

## :nut_and_bolt: SpringBoot中间件开发

| 序号 | 文章阅读 | 源码下载 |
|:---:|:---|:---|
| 1 | [Spring Boot 中间件开发(一)《服务治理中间件之统一白名单验证》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650725117&idx=1&sn=d5cacbe5d6cfeabc1b29f5f37a6849e8&scene=19#wechat_redirect) | []() |
| 2 | [发布Jar包到Maven中央仓库(为开发开源中间件做准备)](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650725123&idx=1&sn=18b6b08dceba7c59904fe5f57505e2b5&scene=19#wechat_redirect) | []() |
| 3 | [开发基于SpringBoot的分布式任务中间件DcsSchedule(为开源贡献力量)](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650725132&idx=1&sn=158b797206873f276e427c283fe0da91&scene=19#wechat_redirect) | []() |

## :art: 服务框架搭建

| 序号 | 文章阅读 | 源码下载 |
|:---:|:---|:---|
| 1 | [架构框架搭建(一)《单体应用服务之SSM整合：Spring4 + SpringMvc + Mybatis》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650725149&idx=1&sn=a7f926bd1edb73422a46e698c89e2b4a&scene=19#wechat_redirect) | []()|
| 2 | [架构框架搭建(二)《Dubbo分布式领域驱动设计架构框体》](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650725161&idx=1&sn=81be28e87cf345591818a23d7a8885f8&scene=19#wechat_redirect) | []()|

## :flashlight: 源码分析(Spring、Mybatis、Schedule)

| 序号 | 文章阅读 | 源码下载 |
|:---:|:---|:---|
| 1 | [`源码分析 - Mybatis接口没有实现类为什么可以执行增删改查`](https://mp.weixin.qq.com/s?__biz=MzIxMDAwMDAxMw==&mid=2650725156&idx=1&sn=e05951ac0dfda3cfdb7cf7d15b73d6a3&scene=19#wechat_redirect) | []() |


## :airplane: Drools规则引擎



## :tractor: ASM字节码编程



## :paw_prints: 我的大学四年到毕业工作5年的学习路线资源和面试汇总




