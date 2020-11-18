# CodeGuide | 程序员编码指南

> **作者：** 小傅哥，Java Developer，[:pencil2: 虫洞 · 科技栈，作者](https://bugstack.cn)，[:trophy: CSDN 博客专家](https://bugstack.blog.csdn.net)，[:point_right: Wiki阅读跳转(有目录)](https://github.com/fuzhengwei/CodeGuide/wiki)

> 本代码库是作者小傅哥多年从事一线互联网```Java```开发的学习历程技术汇总，旨在为大家提供一个清晰详细的学习教程，侧重点更倾向编写Java核心内容。如果本仓库能为您提供帮助，请给予支持(关注、点赞、分享)！

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

|   ID  |   Logo  |   专栏文章  |   源码下载  |
|   :------:   |   :------:   |   :---    |   :---    |
|   0   |   :volcano: |   `网络调试助手`  |   [NetAssist下载](https://download.csdn.net/download/yao__shun__yu/11835105) |
|   1   |   :sound: |   [`Netty4.x专题`](#sound-Netty4专题)  |   [itstack-demo-netty](https://github.com/fuzhengwei/itstack-demo-netty) |
|   2   |   :electric_plug: |   [`手写RPC框架`](#electric_plug-手写RPC框架) |   [itstack-demo-rpc](https://github.com/fuzhengwei/itstack-demo-rpc)  |
|   3   |   :computer:  |   [`用Java实现JVM`](#computer-用Java实现JVM)  |   [itstack-demo-jvm](https://github.com/fuzhengwei/itstack-demo-jvm)  |
|   4   |   :ghost: |   [`基于JavaAgent的全链路监控`](#ghost-基于JavaAgent的全链路监控)   |   [itstack-demo-agent](https://github.com/fuzhengwei/itstack-demo-agent ) |
|   5   |   :shower:    |   [`iot-gateway网关案例`](#shower-iot-gateway网关案例) |   [itstack-demo-iot-gatewary](https://github.com/fuzhengwei/itstack-demo-iot-gatewary)    |
|   6   |   :triangular_ruler:  |   [`DDD领域驱动设计落地`](#triangular_ruler-DDD领域驱动设计落地) |   [itstack-demo-ddd](https://github.com/fuzhengwei/itstack-demo-ddd)  |
|   7   |   :outbox_tray:   |   [`SpringCloud入门案例`](#outbox_tray-SpringCloud入门案例) |   [itstack-demo-springcloud](https://github.com/fuzhengwei/itstack-demo-springcloud)  |
|   8   |   :performing_arts:   |   [`微信公众号开发`](#performing_arts-微信公众号开发) |   [itstack-ark-wx](https://github.com/fuzhengwei/itstack-ark-wx-test) |
|   9   |   :nut_and_bolt:  |   [`SpringBoot中间件开发`](#nut_and_bolt-SpringBoot中间件开发) |   [door-spring-boot-starter](https://github.com/fuzhengwei/door-spring-boot-starter)  |
|   10  |   :art:   |   [`服务框架搭建`](#art-服务框架搭建)  |   [itstack-demo-frame](https://github.com/fuzhengwei/itstack-demo-frame)  |
|   11  |   :flashlight:    |   [`源码分析(Spring、Mybatis、Schedule)`](#flashlight-源码分析)   |   [itstack-demo-code](https://github.com/fuzhengwei/itstack-demo-code)    |
|   12  |   :airplane:  |   [`Drools规则引擎`](#airplane-Drools规则引擎)  |   [itstack-demo-drools](https://github.com/fuzhengwei/itstack-demo-drools)    |
|   13  |   :tractor:   |   [`ASM字节码编程`](#tractor-ASM字节码编程)    |   [itstack-demo-asm](https://github.com/fuzhengwei/itstack-demo-asm)  |
|   14  |   :paw_prints:    |   [`我的大学四年到毕业工作5年的学习资源和面试汇总`](#paw_prints-我的大学四年到毕业工作5年的学习路线资源和面试汇总)   |   [https://pan.baidu.com/s/4mmX7sDy - （if链接失效，加我微信：fustack）](https://pan.baidu.com/s/4mmX7sDy)    |
|   15  |   :walking:    |   [`Netty+JavaFx实战：仿桌面版微信聊天`](#walking-Netty仿桌面版微信聊天)   |   [NaiveChat](https://github.com/fuzhengwei/NaiveChat)    |
|   16  |   :bookmark_tabs:    |   [`JDK1.8新特性41个案例讲解`](https://bugstack.cn/itstack-demo-any/2019/12/10/%E6%9C%89%E7%82%B9%E5%B9%B2%E8%B4%A7-Jdk1.8%E6%96%B0%E7%89%B9%E6%80%A7%E5%AE%9E%E6%88%98%E7%AF%87(41%E4%B8%AA%E6%A1%88%E4%BE%8B).html)   |   [itstack-demo-jdk8](https://github.com/fuzhengwei/itstack-demo-jdk8)    |
|   17  |   :bike:    |   [`小傅哥的《字节码编程》专栏`](#bike-字节码编程专栏)   |   [itstack-demo-bytecode](https://github.com/fuzhengwei/itstack-demo-bytecode)    |
| 18    |   :school_satchel: | [`重学Java设计模式「真实场景实战」`](#school_satchel-实战设计模式) | [itstack-demo-design](https://github.com/fuzhengwei/itstack-demo-design) |
| 19 | :bookmark_tabs: | [`面经手册`](#bookmark_tabs-面经手册) | [interview](https://github.com/fuzhengwei/interview) |
|   20  |   :racehorse:    |   [`码场故事`](#racehorse-码场故事)  |   -   |

**如果**，以上某些资源不能下载获取，可以添加作者：小傅哥，微信(fustack)

<div align="center">
    <a href="https://bugstack.cn" style="text-decoration:none"><img src="https://github.com/fuzhengwei/CodeGuide/blob/master/docs/assets/img/stack.png?raw=true"></a>
</div>

## :sound: Netty4专题

*Netty4.x案例从简单入门到应用实战，全篇37节优秀案例+实战源码[基础篇(12)、拓展篇(13)、应用篇(3章+)、源码篇(6)]，以上章节全部完成并不断持续更新中。*

### 基础入门篇

- [`netty4.1基础入门篇零《初入JavaIO之门BIO、NIO、AIO实战练习》`](https://bugstack.cn/itstack-demo-netty-1/2019/07/30/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E9%9B%B6-%E5%88%9D%E5%85%A5JavaIO%E4%B9%8B%E9%97%A8BIO-NIO-AIO%E5%AE%9E%E6%88%98%E7%BB%83%E4%B9%A0.html)
- [`netty4.1基础入门篇一《嗨！NettyServer》`](https://bugstack.cn/itstack-demo-netty-1/2019/08/01/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E4%B8%80-%E5%97%A8-NettyServer.html)
- [`netty4.1基础入门篇二《NettyServer接收数据》`](https://bugstack.cn/itstack-demo-netty-1/2019/08/05/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E4%BA%8C-NettyServer%E6%8E%A5%E6%94%B6%E6%95%B0%E6%8D%AE.html)
- [`netty4.1基础入门篇三《NettyServer字符串解码器》`](https://bugstack.cn/itstack-demo-netty-1/2019/08/06/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E4%B8%89-NettyServer%E5%AD%97%E7%AC%A6%E4%B8%B2%E8%A7%A3%E7%A0%81%E5%99%A8.html)
- [`netty4.1基础入门篇四《NettyServer收发数据》`](https://bugstack.cn/itstack-demo-netty-1/2019/08/07/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E5%9B%9B-NettyServer%E6%94%B6%E5%8F%91%E6%95%B0%E6%8D%AE.html)
- [`netty4.1基础入门篇五《NettyServer字符串编码器》`](https://bugstack.cn/itstack-demo-netty-1/2019/08/08/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E4%BA%94-NettyServer%E5%AD%97%E7%AC%A6%E4%B8%B2%E7%BC%96%E7%A0%81%E5%99%A8.html)
- [`netty4.1基础入门篇六《NettyServer群发消息》`](https://bugstack.cn/itstack-demo-netty-1/2019/08/09/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E5%85%AD-NettyServer%E7%BE%A4%E5%8F%91%E6%B6%88%E6%81%AF.html)
- [`netty4.1基础入门篇七《嗨！NettyClient》`](https://bugstack.cn/itstack-demo-netty-1/2019/08/10/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E4%B8%83-%E5%97%A8-NettyClient.html)
- [`netty4.1基础入门篇八《NettyClient半包粘包处理、编码解码处理、收发数据方式》`](https://bugstack.cn/itstack-demo-netty-1/2019/08/11/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E5%85%AB-NettyClient%E5%8D%8A%E5%8C%85%E7%B2%98%E5%8C%85%E5%A4%84%E7%90%86-%E7%BC%96%E7%A0%81%E8%A7%A3%E7%A0%81%E5%A4%84%E7%90%86-%E6%94%B6%E5%8F%91%E6%95%B0%E6%8D%AE%E6%96%B9%E5%BC%8F.html)
- [`netty4.1基础入门篇九《自定义编码解码器，处理半包、粘包数据》`](https://bugstack.cn/itstack-demo-netty-1/2019/08/12/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E4%B9%9D-%E8%87%AA%E5%AE%9A%E4%B9%89%E7%BC%96%E7%A0%81%E8%A7%A3%E7%A0%81%E5%99%A8-%E5%A4%84%E7%90%86%E5%8D%8A%E5%8C%85-%E7%B2%98%E5%8C%85%E6%95%B0%E6%8D%AE.html)
- [`netty4.1基础入门篇十《关于ChannelOutboundHandlerAdapter简单使用》`](netty案例，netty4.1基础入门篇十《关于ChannelOutboundHandlerAdapter简单使用》)
- [`netty4.1基础入门篇十一《netty udp通信方式案例Demo》`](https://bugstack.cn/itstack-demo-netty-1/2019/08/14/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E5%8D%81%E4%B8%80-netty-udp%E9%80%9A%E4%BF%A1%E6%96%B9%E5%BC%8F%E6%A1%88%E4%BE%8BDemo.html)
- [`netty4.1基础入门篇十二《简单实现一个基于Netty搭建的Http服务》`](https://bugstack.cn/itstack-demo-netty-1/2019/08/15/netty%E6%A1%88%E4%BE%8B-netty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E5%8D%81%E4%BA%8C-%E7%AE%80%E5%8D%95%E5%AE%9E%E7%8E%B0%E4%B8%80%E4%B8%AA%E5%9F%BA%E4%BA%8ENetty%E6%90%AD%E5%BB%BA%E7%9A%84Http%E6%9C%8D%E5%8A%A1.html)

### 中级拓展篇

- [`netty4.1中级拓展篇一《Netty与SpringBoot整合》`](https://bugstack.cn/itstack-demo-netty-2/2019/08/16/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E4%B8%80-Netty%E4%B8%8ESpringBoot%E6%95%B4%E5%90%88.html)
- [`netty4.1中级拓展篇二《Netty使用Protobuf传输数据》`](https://bugstack.cn/itstack-demo-netty-2/2019/08/17/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E4%BA%8C-Netty%E4%BD%BF%E7%94%A8Protobuf%E4%BC%A0%E8%BE%93%E6%95%B0%E6%8D%AE.html)
- [`netty4.1中级拓展篇三《Netty传输Java对象》`](https://bugstack.cn/itstack-demo-netty-2/2019/08/18/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E4%B8%89-Netty%E4%BC%A0%E8%BE%93Java%E5%AF%B9%E8%B1%A1.html)
- [`netty4.1中级拓展篇四《Netty传输文件、分片发送、断点续传》`](https://bugstack.cn/itstack-demo-netty-2/2019/08/19/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%9B%9B-Netty%E4%BC%A0%E8%BE%93%E6%96%87%E4%BB%B6-%E5%88%86%E7%89%87%E5%8F%91%E9%80%81-%E6%96%AD%E7%82%B9%E7%BB%AD%E4%BC%A0.html)
- [`netty4.1中级拓展篇五《基于Netty搭建WebSocket，模仿微信聊天页面》`](https://bugstack.cn/itstack-demo-netty-2/2019/08/20/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E4%BA%94-%E5%9F%BA%E4%BA%8ENetty%E6%90%AD%E5%BB%BAWebSocket-%E6%A8%A1%E4%BB%BF%E5%BE%AE%E4%BF%A1%E8%81%8A%E5%A4%A9%E9%A1%B5%E9%9D%A2.html)
- [`netty4.1中级拓展篇六《SpringBoot+Netty+Elasticsearch收集日志信息数据存储》`](https://bugstack.cn/itstack-demo-netty-2/2019/08/21/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%85%AD-SpringBoot+Netty+Elasticsearch%E6%94%B6%E9%9B%86%E6%97%A5%E5%BF%97%E4%BF%A1%E6%81%AF%E6%95%B0%E6%8D%AE%E5%AD%98%E5%82%A8.html)
- [`netty4.1中级拓展篇七《Netty请求响应同步通信》`](https://bugstack.cn/itstack-demo-netty-2/2019/08/22/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E4%B8%83-Netty%E8%AF%B7%E6%B1%82%E5%93%8D%E5%BA%94%E5%90%8C%E6%AD%A5%E9%80%9A%E4%BF%A1.html)
- [`netty4.1中级拓展篇八《Netty心跳服务与断线重连》`](https://bugstack.cn/itstack-demo-netty-2/2019/08/23/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%85%AB-Netty%E5%BF%83%E8%B7%B3%E6%9C%8D%E5%8A%A1%E4%B8%8E%E6%96%AD%E7%BA%BF%E9%87%8D%E8%BF%9E.html)
- [`netty4.1中级拓展篇九《Netty集群部署实现跨服务端通信的落地方案》`](https://bugstack.cn/itstack-demo-netty-2/2019/08/24/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E4%B9%9D-Netty%E9%9B%86%E7%BE%A4%E9%83%A8%E7%BD%B2%E5%AE%9E%E7%8E%B0%E8%B7%A8%E6%9C%8D%E5%8A%A1%E7%AB%AF%E9%80%9A%E4%BF%A1%E7%9A%84%E8%90%BD%E5%9C%B0%E6%96%B9%E6%A1%88.html)
- [`netty4.1中级拓展篇十《Netty接收发送多种协议消息类型的通信处理方案》`](https://bugstack.cn/itstack-demo-netty-2/2019/08/25/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%8D%81-Netty%E6%8E%A5%E6%94%B6%E5%8F%91%E9%80%81%E5%A4%9A%E7%A7%8D%E5%8D%8F%E8%AE%AE%E6%B6%88%E6%81%AF%E7%B1%BB%E5%9E%8B%E7%9A%84%E9%80%9A%E4%BF%A1%E5%A4%84%E7%90%86%E6%96%B9%E6%A1%88.html)
- [`netty4.1中级拓展篇十一《Netty基于ChunkedStream数据流切块传输》`](https://bugstack.cn/itstack-demo-netty-2/2019/08/26/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%8D%81%E4%B8%80-Netty%E5%9F%BA%E4%BA%8EChunkedStream%E6%95%B0%E6%8D%AE%E6%B5%81%E5%88%87%E5%9D%97%E4%BC%A0%E8%BE%93.html)
- [`netty4.1中级拓展篇十二《Netty流量整形数据流速率控制分析与实战》`](https://bugstack.cn/itstack-demo-netty-2/2019/08/27/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%8D%81%E4%BA%8C-Netty%E6%B5%81%E9%87%8F%E6%95%B4%E5%BD%A2%E6%95%B0%E6%8D%AE%E6%B5%81%E9%80%9F%E7%8E%87%E6%8E%A7%E5%88%B6%E5%88%86%E6%9E%90%E4%B8%8E%E5%AE%9E%E6%88%98.html)
- [`netty4.1中级拓展篇十三《Netty基于SSL实现信息传输过程中双向加密验证》`](https://bugstack.cn/itstack-demo-netty-2/2019/08/28/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%8D%81%E4%B8%89-Netty%E5%9F%BA%E4%BA%8ESSL%E5%AE%9E%E7%8E%B0%E4%BF%A1%E6%81%AF%E4%BC%A0%E8%BE%93%E8%BF%87%E7%A8%8B%E4%B8%AD%E5%8F%8C%E5%90%91%E5%8A%A0%E5%AF%86%E9%AA%8C%E8%AF%81.html)

### 高级应用篇

- [`手写RPC框架第一章《自定义配置xml》`](https://bugstack.cn/itstack-demo-netty-3/2019/09/01/%E6%89%8B%E5%86%99RPC%E6%A1%86%E6%9E%B6%E7%AC%AC%E4%B8%80%E7%AB%A0-%E8%87%AA%E5%AE%9A%E4%B9%89%E9%85%8D%E7%BD%AExml.html)
- [`手写RPC框架第二章《netty通信》`](https://bugstack.cn/itstack-demo-netty-3/2019/09/02/%E6%89%8B%E5%86%99RPC%E6%A1%86%E6%9E%B6%E7%AC%AC%E4%BA%8C%E7%AB%A0-netty%E9%80%9A%E4%BF%A1.html)
- [`手写RPC框架第三章《RPC中间件》`](https://bugstack.cn/itstack-demo-netty-3/2019/09/03/%E6%89%8B%E5%86%99RPC%E6%A1%86%E6%9E%B6%E7%AC%AC%E4%B8%89%E7%AB%A0-RPC%E4%B8%AD%E9%97%B4%E4%BB%B6.html)
- [`websocket与下位机通过netty方式通信传输行为信息`](https://bugstack.cn/itstack-demo-netty-3/2019/12/01/websocket%E4%B8%8E%E4%B8%8B%E4%BD%8D%E6%9C%BA%E9%80%9A%E8%BF%87netty%E6%96%B9%E5%BC%8F%E9%80%9A%E4%BF%A1%E4%BC%A0%E8%BE%93%E8%A1%8C%E4%B8%BA%E4%BF%A1%E6%81%AF.html)

### 源码分析篇

- [`netty4.1源码分析篇一《NioEventLoopGroup源码分析》`](https://bugstack.cn/itstack-demo-netty-4/2019/09/10/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E4%B8%80-NioEventLoopGroup%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.html)
- [`netty4.1源码分析篇二《ServerBootstrap配置与绑定启动》`](https://bugstack.cn/itstack-demo-netty-4/2019/09/11/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E4%BA%8C-ServerBootstrap%E9%85%8D%E7%BD%AE%E4%B8%8E%E7%BB%91%E5%AE%9A%E5%90%AF%E5%8A%A8.html)
- [`netty4.1源码分析篇三《Netty服务端初始化过程以及反射工厂的作用》`](https://bugstack.cn/itstack-demo-netty-4/2019/09/12/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E4%B8%89-Netty%E6%9C%8D%E5%8A%A1%E7%AB%AF%E5%88%9D%E5%A7%8B%E5%8C%96%E8%BF%87%E7%A8%8B%E4%BB%A5%E5%8F%8A%E5%8F%8D%E5%B0%84%E5%B7%A5%E5%8E%82%E7%9A%84%E4%BD%9C%E7%94%A8.html)
- [`netty4.1源码分析篇四《ByteBuf的数据结构在使用方式中的剖析》`](https://bugstack.cn/itstack-demo-netty-4/2019/09/13/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E5%9B%9B-ByteBuf%E7%9A%84%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E5%9C%A8%E4%BD%BF%E7%94%A8%E6%96%B9%E5%BC%8F%E4%B8%AD%E7%9A%84%E5%89%96%E6%9E%90.html)
- [`netty4.1源码分析篇五《一行简单的writeAndFlush都做了哪些事》`](https://bugstack.cn/itstack-demo-netty-4/2019/09/14/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E4%BA%94-%E4%B8%80%E8%A1%8C%E7%AE%80%E5%8D%95%E7%9A%84writeAndFlush%E9%83%BD%E5%81%9A%E4%BA%86%E5%93%AA%E4%BA%9B%E4%BA%8B.html)
- [`netty4.1源码分析篇六《Netty异步架构监听类Promise源码分析》`](https://bugstack.cn/itstack-demo-netty-4/2019/09/15/netty%E6%A1%88%E4%BE%8B-netty4.1%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E7%AF%87%E5%85%AD-Netty%E5%BC%82%E6%AD%A5%E6%9E%B6%E6%9E%84%E7%9B%91%E5%90%AC%E7%B1%BBPromise%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.html)

## :electric_plug: 手写RPC框架	

*RPC是一种远程调用的通信协议，例如dubbo、thrift等，我们在互联网高并发应用开发时候都会使用到类似的服务。本专题主要通过三个章节简单的实现rpc基础功能，来深入学习rpc是如何交互通信的。*

- [`手写RPC框架第一章《自定义配置xml》`](https://bugstack.cn/itstack-demo-netty-3/2019/09/01/%E6%89%8B%E5%86%99RPC%E6%A1%86%E6%9E%B6%E7%AC%AC%E4%B8%80%E7%AB%A0-%E8%87%AA%E5%AE%9A%E4%B9%89%E9%85%8D%E7%BD%AExml.html)
- [`手写RPC框架第二章《netty通信》`](https://bugstack.cn/itstack-demo-netty-3/2019/09/02/%E6%89%8B%E5%86%99RPC%E6%A1%86%E6%9E%B6%E7%AC%AC%E4%BA%8C%E7%AB%A0-netty%E9%80%9A%E4%BF%A1.html)
- [`手写RPC框架第三章《RPC中间件》`](https://bugstack.cn/itstack-demo-netty-3/2019/09/03/%E6%89%8B%E5%86%99RPC%E6%A1%86%E6%9E%B6%E7%AC%AC%E4%B8%89%E7%AB%A0-RPC%E4%B8%AD%E9%97%B4%E4%BB%B6.html)

## :computer: 用Java实现JVM

*本专题主要介绍如何通过java代码来实现JVM的基础功能（搜索解析class文件、字节码命令、运行时数据区等），从而让java程序员通过最熟知的java程序，学习JVM是如何将java程序一步步跑起来的。*

- [`用Java实现JVM第一章《命令行工具》`](https://bugstack.cn/itstack-demo-jvm/2019/05/01/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E4%B8%80%E7%AB%A0-%E5%91%BD%E4%BB%A4%E8%A1%8C%E5%B7%A5%E5%85%B7.html)
- [`用Java实现JVM第二章《搜索class文件》`](https://bugstack.cn/itstack-demo-jvm/2019/05/02/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E4%BA%8C%E7%AB%A0-%E6%90%9C%E7%B4%A2class%E6%96%87%E4%BB%B6.html)
- [`用Java实现JVM第三章《解析class文件》`](https://bugstack.cn/itstack-demo-jvm/2019/05/03/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E4%B8%89%E7%AB%A0-%E8%A7%A3%E6%9E%90class%E6%96%87%E4%BB%B6.html)
- [`用Java实现JVM第三章《解析class文件》附[classReader拆解]`](https://bugstack.cn/itstack-demo-jvm/2019/05/04/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E4%B8%89%E7%AB%A0-%E8%A7%A3%E6%9E%90class%E6%96%87%E4%BB%B6-%E9%99%84-classReader%E6%8B%86%E8%A7%A3.html)
- [`用Java实现JVM第四章《运行时数据区》`](https://bugstack.cn/itstack-demo-jvm/2019/05/05/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E5%9B%9B%E7%AB%A0-%E8%BF%90%E8%A1%8C%E6%97%B6%E6%95%B0%E6%8D%AE%E5%8C%BA.html)
- [`用Java实现JVM第五章《指令集和解释器》`](https://bugstack.cn/itstack-demo-jvm/2019/05/06/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E4%BA%94%E7%AB%A0-%E6%8C%87%E4%BB%A4%E9%9B%86%E5%92%8C%E8%A7%A3%E9%87%8A%E5%99%A8.html)
- [`用Java实现JVM第六章《类和对象》`](https://bugstack.cn/itstack-demo-jvm/2019/05/07/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E5%85%AD%E7%AB%A0-%E7%B1%BB%E5%92%8C%E5%AF%B9%E8%B1%A1.html)
- [`用Java实现JVM第七章《方法调用和返回》`](https://bugstack.cn/itstack-demo-jvm/2019/05/08/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E4%B8%83%E7%AB%A0-%E6%96%B9%E6%B3%95%E8%B0%83%E7%94%A8%E5%92%8C%E8%BF%94%E5%9B%9E.html)
- [`用Java实现JVM第八章《数组和字符串》`](https://bugstack.cn/itstack-demo-jvm/2019/05/09/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E5%85%AB%E7%AB%A0-%E6%95%B0%E7%BB%84%E5%92%8C%E5%AD%97%E7%AC%A6%E4%B8%B2.html)
- [`用Java实现JVM第九章《本地方法调用》`](https://bugstack.cn/itstack-demo-jvm/2019/05/10/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E4%B9%9D%E7%AB%A0-%E6%9C%AC%E5%9C%B0%E6%96%B9%E6%B3%95%E8%B0%83%E7%94%A8.html)
- [`用Java实现JVM第十章《异常处理》`](https://bugstack.cn/itstack-demo-jvm/2019/05/11/%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E5%8D%81%E7%AB%A0-%E5%BC%82%E5%B8%B8%E5%A4%84%E7%90%86.html)

## :ghost: 基于JavaAgent的全链路监控

*目前市面的全链路监控系统基本都是参考Google的Dapper来做的，本专题主要通过六个章节的代码实战，来介绍如何使用javaagent以及字节码应用，来实现一个简单的java代码链路流程监控。*

- [`基于JavaAgent的全链路监控一《嗨！JavaAgent》`](https://bugstack.cn/itstack-demo-agent/2019/07/10/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%B8%80-%E5%97%A8-JavaAgent.html)
- [`基于JavaAgent的全链路监控二《通过字节码增加监控执行耗时》`](https://bugstack.cn/itstack-demo-agent/2019/07/11/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%BA%8C-%E9%80%9A%E8%BF%87%E5%AD%97%E8%8A%82%E7%A0%81%E5%A2%9E%E5%8A%A0%E7%9B%91%E6%8E%A7%E6%89%A7%E8%A1%8C%E8%80%97%E6%97%B6.html)
- [`基于JavaAgent的全链路监控三《ByteBuddy操作监控方法字节码》`](https://bugstack.cn/itstack-demo-agent/2019/07/12/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%B8%89-ByteBuddy%E6%93%8D%E4%BD%9C%E7%9B%91%E6%8E%A7%E6%96%B9%E6%B3%95%E5%AD%97%E8%8A%82%E7%A0%81.html)
- [`基于JavaAgent的全链路监控四《JVM内存与GC信息》`](https://bugstack.cn/itstack-demo-agent/2019/07/13/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E5%9B%9B-JVM%E5%86%85%E5%AD%98%E4%B8%8EGC%E4%BF%A1%E6%81%AF.html)
- [`基于JavaAgent的全链路监控五《ThreadLocal链路追踪》`](https://bugstack.cn/itstack-demo-agent/2019/07/14/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%BA%94-ThreadLocal%E9%93%BE%E8%B7%AF%E8%BF%BD%E8%B8%AA.html)
- [`基于JavaAgent的全链路监控六《开发应用级监控》`](https://bugstack.cn/itstack-demo-agent/2019/07/15/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E5%85%AD-%E5%BC%80%E5%8F%91%E5%BA%94%E7%94%A8%E7%BA%A7%E7%9B%91%E6%8E%A7.html)

## :shower: iot-gateway网关案例

*基于Netty实现的物联网网关服务，支持百万客户端连接，压力测试ing...，并优化了与服务端集群通信对平均算法做了优化,本次上传代码添加了很多功能，摒弃了以往只做心跳维护、数据转发的功能。*

- [`基于Netty实践搭建的物联网网关iot-gatway`](https://mp.weixin.qq.com/s/LLyG2ji2gDR2Fz8uDmfJ7A)

## :triangular_ruler: DDD领域驱动设计落地

*本专题以DDD实战落地为根本，分章节设计不同的架构模型，学习并实战是奔入应用级开发最快的方法，Hi HelloWorld！我来了。*

- [`DDD专题案例一《初识领域驱动设计DDD落地》`](https://bugstack.cn/itstack-demo-ddd/2019/10/15/DDD%E4%B8%93%E9%A2%98%E6%A1%88%E4%BE%8B%E4%B8%80-%E5%88%9D%E8%AF%86%E9%A2%86%E5%9F%9F%E9%A9%B1%E5%8A%A8%E8%AE%BE%E8%AE%A1DDD%E8%90%BD%E5%9C%B0.html)
- [`DDD专题案例二《领域层决策规则树服务设计》`](https://bugstack.cn/itstack-demo-ddd/2019/10/16/DDD%E4%B8%93%E9%A2%98%E6%A1%88%E4%BE%8B%E4%BA%8C-%E9%A2%86%E5%9F%9F%E5%B1%82%E5%86%B3%E7%AD%96%E8%A7%84%E5%88%99%E6%A0%91%E6%9C%8D%E5%8A%A1%E8%AE%BE%E8%AE%A1.html)
- [`DDD专题案例三《领域驱动设计架构基于SpringCloud搭建微服务》`](https://bugstack.cn/itstack-demo-ddd/2019/10/17/DDD%E4%B8%93%E9%A2%98%E6%A1%88%E4%BE%8B%E4%B8%89-%E9%A2%86%E5%9F%9F%E9%A9%B1%E5%8A%A8%E8%AE%BE%E8%AE%A1%E6%9E%B6%E6%9E%84%E5%9F%BA%E4%BA%8ESpringCloud%E6%90%AD%E5%BB%BA%E5%BE%AE%E6%9C%8D%E5%8A%A1.html)

## :outbox_tray: SpringCloud入门案例

- [`Spring Cloud(零)《总有一偏概述告诉你SpringCloud是什么》`](https://bugstack.cn/itstack-demo-springcloud/2019/10/31/Spring-Cloud(%E9%9B%B6)-%E6%80%BB%E6%9C%89%E4%B8%80%E5%81%8F%E6%A6%82%E8%BF%B0%E5%91%8A%E8%AF%89%E4%BD%A0SpringCloud%E6%98%AF%E4%BB%80%E4%B9%88.html)
- [`Spring Cloud(一)《服务集群注册与发现 Eureka》`](https://bugstack.cn/itstack-demo-springcloud/2019/11/01/Spring-Cloud(%E4%B8%80)-%E6%9C%8D%E5%8A%A1%E9%9B%86%E7%BE%A4%E6%B3%A8%E5%86%8C%E4%B8%8E%E5%8F%91%E7%8E%B0-Eureka.html)
- [`Spring Cloud(二)《服务提供与负载均衡调用 Eureka》`](https://bugstack.cn/itstack-demo-springcloud/2019/11/02/Spring-Cloud(%E4%BA%8C)-%E6%9C%8D%E5%8A%A1%E6%8F%90%E4%BE%9B%E4%B8%8E%E8%B4%9F%E8%BD%BD%E5%9D%87%E8%A1%A1%E8%B0%83%E7%94%A8-Eureka.html)
- [`Spring Cloud(三)《应用服务快速失败熔断降级保护 Hystrix》`](https://bugstack.cn/itstack-demo-springcloud/2019/11/03/Spring-Cloud(%E4%B8%89)-%E5%BA%94%E7%94%A8%E6%9C%8D%E5%8A%A1%E5%BF%AB%E9%80%9F%E5%A4%B1%E8%B4%A5%E7%86%94%E6%96%AD%E9%99%8D%E7%BA%A7%E4%BF%9D%E6%8A%A4-Hystrix.html)
- [`Spring Cloud(四)《服务响应性能成功率监控 Hystrix》`](https://bugstack.cn/itstack-demo-springcloud/2019/11/04/Spring-Cloud(%E5%9B%9B)-%E6%9C%8D%E5%8A%A1%E5%93%8D%E5%BA%94%E6%80%A7%E8%83%BD%E6%88%90%E5%8A%9F%E7%8E%87%E7%9B%91%E6%8E%A7-Hystrix.html)
- [`Spring Cloud(五)《Turbine 监控信息聚合展示 Hystrix》`](https://bugstack.cn/itstack-demo-springcloud/2019/11/05/Spring-Cloud(%E4%BA%94)-Turbine-%E7%9B%91%E6%8E%A7%E4%BF%A1%E6%81%AF%E8%81%9A%E5%90%88%E5%B1%95%E7%A4%BA-Hystrix.html)
- [`Spring Cloud(六)《基于github webhook动态刷新服务配置》`](https://bugstack.cn/itstack-demo-springcloud/2019/11/06/Spring-Cloud(%E5%85%AD)-%E5%9F%BA%E4%BA%8EGithub-Webhook%E5%8A%A8%E6%80%81%E5%88%B7%E6%96%B0%E6%9C%8D%E5%8A%A1%E9%85%8D%E7%BD%AE.html)
- [`Spring Cloud(七)《基于RabbitMQ消息总线方式刷新配置服务》`](https://bugstack.cn/itstack-demo-springcloud/2019/11/07/Spring-Cloud(%E4%B8%83)-%E5%9F%BA%E4%BA%8ERabbitMQ%E6%B6%88%E6%81%AF%E6%80%BB%E7%BA%BF%E6%96%B9%E5%BC%8F%E5%88%B7%E6%96%B0%E9%85%8D%E7%BD%AE%E6%9C%8D%E5%8A%A1.html)
- [`Spring Cloud(八)《服务网关路由 Zuul1》`](https://bugstack.cn/itstack-demo-springcloud/2019/11/08/Spring-Cloud(%E5%85%AB)-%E6%9C%8D%E5%8A%A1%E7%BD%91%E5%85%B3%E8%B7%AF%E7%94%B1-Zuul1.html)
- [`Spring Cloud(九)《服务网关Zuul 动态路由与权限过滤器》`](https://bugstack.cn/itstack-demo-springcloud/2019/11/24/Spring-Cloud(%E4%B9%9D)-%E6%9C%8D%E5%8A%A1%E7%BD%91%E5%85%B3Zuul-%E5%8A%A8%E6%80%81%E8%B7%AF%E7%94%B1%E4%B8%8E%E6%9D%83%E9%99%90%E8%BF%87%E6%BB%A4%E5%99%A8.html)

## :performing_arts: 微信公众号开发

*这是一套基于领域驱动设计方式搭建的Java公众号开发工程，主要服务于博客与公众号之间打通，引导用户关注公众号，做粉丝回流。*

- [`并不想吹牛皮，但！为了把Github博客粉丝转移到公众号，我干了！`](https://bugstack.cn/itstack-demo-any/2019/11/23/%E5%B9%B6%E4%B8%8D%E6%83%B3%E5%90%B9%E7%89%9B%E7%9A%AE-%E4%BD%86-%E4%B8%BA%E4%BA%86%E6%8A%8AGithub%E5%8D%9A%E5%AE%A2%E7%B2%89%E4%B8%9D%E8%BD%AC%E7%A7%BB%E5%88%B0%E5%85%AC%E4%BC%97%E5%8F%B7-%E6%88%91%E5%B9%B2%E4%BA%86.html)

## :nut_and_bolt: SpringBoot中间件开发

*Spring Boot 中间件开发，基于服务治理为目的将非业务行为的核心逻辑剥离出来开发为独立的中间件，赋能于业务系统快速开发。*

- [`发布Jar包到Maven中央仓库(为开发开源中间件做准备)`](https://bugstack.cn/itstack-demo-any/2019/12/07/%E5%8F%91%E5%B8%83Jar%E5%8C%85%E5%88%B0Maven%E4%B8%AD%E5%A4%AE%E4%BB%93%E5%BA%93(%E4%B8%BA%E5%BC%80%E5%8F%91%E5%BC%80%E6%BA%90%E4%B8%AD%E9%97%B4%E4%BB%B6%E5%81%9A%E5%87%86%E5%A4%87).html)
- [`Spring Boot 中间件开发(一)《服务治理中间件之统一白名单验证》`](https://bugstack.cn/itstack-ark-middleware/2019/12/02/Spring-Boot-%E4%B8%AD%E9%97%B4%E4%BB%B6%E5%BC%80%E5%8F%91(%E4%B8%80)-%E6%9C%8D%E5%8A%A1%E6%B2%BB%E7%90%86%E4%B8%AD%E9%97%B4%E4%BB%B6%E4%B9%8B%E7%BB%9F%E4%B8%80%E7%99%BD%E5%90%8D%E5%8D%95%E9%AA%8C%E8%AF%81.html)
- [`开发基于SpringBoot的分布式任务中间件DcsSchedule(为开源贡献力量)`](https://bugstack.cn/itstack-ark-middleware/2019/12/08/%E5%BC%80%E5%8F%91%E5%9F%BA%E4%BA%8ESpringBoot%E7%9A%84%E5%88%86%E5%B8%83%E5%BC%8F%E4%BB%BB%E5%8A%A1%E4%B8%AD%E9%97%B4%E4%BB%B6DcsSchedule(%E4%B8%BA%E5%BC%80%E6%BA%90%E8%B4%A1%E7%8C%AE%E5%8A%9B%E9%87%8F).html)

## :art: 服务框架搭建

*服务框架搭建，依赖于不同的业务诉求搭建出各种服务功能的框架结构。将逐步完成；单体服务应用(适合于ERP和个人)、分库分表应用、Mq服务、任务服务、分布式服务、RPC服务等。*

- [`架构框架搭建(一)《单体应用服务之SSM整合：Spring4 + SpringMvc + Mybatis》`](https://bugstack.cn/itstack-demo-frame/2019/12/22/%E6%9E%B6%E6%9E%84%E6%A1%86%E6%9E%B6%E6%90%AD%E5%BB%BA(%E4%B8%80)-%E5%8D%95%E4%BD%93%E5%BA%94%E7%94%A8%E6%9C%8D%E5%8A%A1%E4%B9%8BSSM%E6%95%B4%E5%90%88-Spring4-+-SpringMvc-+-Mybatis.html)
- [`架构框架搭建(二)《Dubbo分布式领域驱动设计架构框体》`](https://bugstack.cn/itstack-demo-frame/2019/12/31/%E6%9E%B6%E6%9E%84%E6%A1%86%E6%9E%B6%E6%90%AD%E5%BB%BA(%E4%BA%8C)-Dubbo%E5%88%86%E5%B8%83%E5%BC%8F%E9%A2%86%E5%9F%9F%E9%A9%B1%E5%8A%A8%E8%AE%BE%E8%AE%A1%E6%9E%B6%E6%9E%84%E6%A1%86%E4%BD%93.html)

## :flashlight: 源码分析

*源码分析以最核心干货内容为入手，将平时开发使用到的Spring、Mybatis、多线程等逐个渗透分析研究。不在只是单纯使用，而是要从原理分析获取更多的技术成长。*

- [`源码分析 | Mybatis接口没有实现类为什么可以执行增删改查`](https://bugstack.cn/itstack-demo-any/2019/12/25/%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90-Mybatis%E6%8E%A5%E5%8F%A3%E6%B2%A1%E6%9C%89%E5%AE%9E%E7%8E%B0%E7%B1%BB%E4%B8%BA%E4%BB%80%E4%B9%88%E5%8F%AF%E4%BB%A5%E6%89%A7%E8%A1%8C%E5%A2%9E%E5%88%A0%E6%94%B9%E6%9F%A5.html)
- [`源码分析 | Spring定时任务Quartz执行全过程源码解读`](https://bugstack.cn/itstack-demo-any/2020/01/01/%E6%BA%90%E7%A0%81%E8%A7%A3%E6%9E%90-Spring%E5%AE%9A%E6%97%B6%E4%BB%BB%E5%8A%A1Quartz%E6%89%A7%E8%A1%8C%E5%85%A8%E8%BF%87%E7%A8%8B%E6%BA%90%E7%A0%81%E8%A7%A3%E8%AF%BB.html)
- [`源码分析 | 咋嘞？你的IDEA过期了吧！加个Jar包就破解了，为什么？`](https://bugstack.cn/itstack-demo-any/2020/01/06/%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90-%E5%92%8B%E5%98%9E-%E4%BD%A0%E7%9A%84IDEA%E8%BF%87%E6%9C%9F%E4%BA%86%E5%90%A7-%E5%8A%A0%E4%B8%AAJar%E5%8C%85%E5%B0%B1%E7%A0%B4%E8%A7%A3%E4%BA%86-%E4%B8%BA%E4%BB%80%E4%B9%88.html)
- [`源码分析 | 像盗墓一样分析Spring是怎么初始化xml并注册bean的`](https://bugstack.cn/itstack-demo-any/2020/01/08/%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90-%E5%83%8F%E7%9B%97%E5%A2%93%E4%B8%80%E6%A0%B7%E5%88%86%E6%9E%90Spring%E6%98%AF%E6%80%8E%E4%B9%88%E5%88%9D%E5%A7%8B%E5%8C%96xml%E5%B9%B6%E6%B3%A8%E5%86%8Cbean%E7%9A%84.html)
- [`源码分析 | 基于jdbc实现一个Demo版的Mybatis`](https://bugstack.cn/itstack-demo-any/2020/01/13/%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90-%E5%9F%BA%E4%BA%8Ejdbc%E5%AE%9E%E7%8E%B0%E4%B8%80%E4%B8%AADemo%E7%89%88%E7%9A%84Mybatis.html)
- [`源码分析 | 手写mybait-spring核心功能(干货好文一次学会工厂bean、类代理、bean注册的使用)`](https://bugstack.cn/itstack-demo-any/2020/01/20/%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90-%E6%89%8B%E5%86%99mybait-spring%E6%A0%B8%E5%BF%83%E5%8A%9F%E8%83%BD(%E5%B9%B2%E8%B4%A7%E5%A5%BD%E6%96%87%E4%B8%80%E6%AC%A1%E5%AD%A6%E4%BC%9A%E5%B7%A5%E5%8E%82bean-%E7%B1%BB%E4%BB%A3%E7%90%86-bean%E6%B3%A8%E5%86%8C%E7%9A%84%E4%BD%BF%E7%94%A8).html)

## :airplane: Drools规则引擎

*Drools 是 Java 语言基于Rete算法编写的规则引擎，可以方便的使用声明表达业务逻辑，非常简单易用。本专题会从入门开始逐步完成对Drools的讲解。*

- [`这种场景你还写ifelse你跟孩子坐一桌去吧`](https://bugstack.cn/itstack-demo-drools/2020/03/07/%E8%BF%99%E7%A7%8D%E5%9C%BA%E6%99%AF%E4%BD%A0%E8%BF%98%E5%86%99ifelse%E4%BD%A0%E8%B7%9F%E5%AD%A9%E5%AD%90%E5%9D%90%E4%B8%80%E6%A1%8C%E5%8E%BB%E5%90%A7.html)

## :tractor: ASM字节码编程

*ASM是一个java字节码操纵框架，它能被用来动态生成类或者增强既有类的功能。ASM 可以直接产生二进制 class 文件，也可以在类被加载入 Java 虚拟机之前动态改变类行为。*

- [`ASM字节码编程 | 如果你只写CRUD，那这种技术你永远碰不到`](https://bugstack.cn/itstack-demo-agent/2020/03/25/ASM%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-%E5%A6%82%E6%9E%9C%E4%BD%A0%E5%8F%AA%E5%86%99CRUD-%E9%82%A3%E8%BF%99%E7%A7%8D%E6%8A%80%E6%9C%AF%E4%BD%A0%E6%B0%B8%E8%BF%9C%E7%A2%B0%E4%B8%8D%E5%88%B0.html)
- [`ASM字节码编程 | JavaAgent+ASM字节码插桩采集方法名称以及入参和出参结果并记录方法耗时`](https://bugstack.cn/itstack-demo-agent/2020/04/05/ASM%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-JavaAgent+ASM%E5%AD%97%E8%8A%82%E7%A0%81%E6%8F%92%E6%A1%A9%E9%87%87%E9%9B%86%E6%96%B9%E6%B3%95%E5%90%8D%E7%A7%B0%E4%BB%A5%E5%8F%8A%E5%85%A5%E5%8F%82%E5%92%8C%E5%87%BA%E5%8F%82%E7%BB%93%E6%9E%9C%E5%B9%B6%E8%AE%B0%E5%BD%95%E6%96%B9%E6%B3%95%E8%80%97%E6%97%B6.html)
- [`源码分析 | 咋嘞？你的IDEA过期了吧！加个Jar包就破解了，为什么？`](https://bugstack.cn/itstack-demo-any/2020/01/06/%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90-%E5%92%8B%E5%98%9E-%E4%BD%A0%E7%9A%84IDEA%E8%BF%87%E6%9C%9F%E4%BA%86%E5%90%A7-%E5%8A%A0%E4%B8%AAJar%E5%8C%85%E5%B0%B1%E7%A0%B4%E8%A7%A3%E4%BA%86-%E4%B8%BA%E4%BB%80%E4%B9%88.html)
- [`ASM字节码编程 | 用字节码增强技术给所有方法加上TryCatch捕获异常并输出`](https://bugstack.cn/itstack-demo-agent/2020/04/16/ASM%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-%E7%94%A8%E5%AD%97%E8%8A%82%E7%A0%81%E5%A2%9E%E5%BC%BA%E6%8A%80%E6%9C%AF%E7%BB%99%E6%89%80%E6%9C%89%E6%96%B9%E6%B3%95%E5%8A%A0%E4%B8%8ATryCatch%E6%8D%95%E8%8E%B7%E5%BC%82%E5%B8%B8%E5%B9%B6%E8%BE%93%E5%87%BA.html)

## :paw_prints: 我的大学四年到毕业工作5年的学习路线资源和面试汇总

*一直有伙伴问小傅哥，有没有一个Java的学习路线和面试，最好再有一些相关的资料、书籍、视频。因为现在自己学习也不知道哪不会，看到这个学这个，看到那个学那个，也摸不到头，还比较混乱。特别希望有一个大学到毕业的学习路线整理。*

- [`大学四年到毕业工作5年的学习路线资源汇总`](https://bugstack.cn/itstack-code-life/2020/03/31/%E5%A4%A7%E5%AD%A6%E5%9B%9B%E5%B9%B4%E5%88%B0%E6%AF%95%E4%B8%9A%E5%B7%A5%E4%BD%9C5%E5%B9%B4%E7%9A%84%E5%AD%A6%E4%B9%A0%E8%B7%AF%E7%BA%BF%E8%B5%84%E6%BA%90%E6%B1%87%E6%80%BB.html)
- [`工作两年简历写成这样，谁要你呀！`](https://bugstack.cn/itstack-code-life/2020/04/11/%E5%B7%A5%E4%BD%9C%E4%B8%A4%E5%B9%B4%E7%AE%80%E5%8E%86%E5%86%99%E6%88%90%E8%BF%99%E6%A0%B7-%E8%B0%81%E8%A6%81%E4%BD%A0%E5%91%80.html)

## :walking: Netty仿桌面版微信聊天

*使用JavaFx、Netty4.x、SpringBoot、Mysql等技术栈和偏向于DDD领域驱动设计方式，搭建的仿桌面版微信聊天工程实现通信核心功能。如果本项目能为您提供帮助，请给予支持(关注、点赞、分享)！*

- [`《Netty+JavaFx实战：仿桌面版微信聊天》`](https://chat.istack.org)
- [`《Netty+JavaFx实战：仿桌面版微信聊天》代码开源、上云部署、视频讲解，只为让你给点个Star！`](https://mp.weixin.qq.com/s/OmXCY4fTfDpkvjlg5ME0ZA)

## :bike: 字节码编程专栏

- [`字节码编程，Javassist篇一《基于javassist的第一个案例helloworld》`](https://bugstack.cn/itstack-demo-agent/2020/04/19/%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-Javassist%E7%AF%87%E4%B8%80-%E5%9F%BA%E4%BA%8Ejavassist%E7%9A%84%E7%AC%AC%E4%B8%80%E4%B8%AA%E6%A1%88%E4%BE%8Bhelloworld.html)
- [`字节码编程，Javassist篇二《定义属性以及创建方法时多种入参和出参类型的使用》`](https://bugstack.cn/itstack-demo-agent/2020/04/20/%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-Javassist%E7%AF%87%E4%BA%8C-%E5%AE%9A%E4%B9%89%E5%B1%9E%E6%80%A7%E4%BB%A5%E5%8F%8A%E5%88%9B%E5%BB%BA%E6%96%B9%E6%B3%95%E6%97%B6%E5%A4%9A%E7%A7%8D%E5%85%A5%E5%8F%82%E5%92%8C%E5%87%BA%E5%8F%82%E7%B1%BB%E5%9E%8B%E7%9A%84%E4%BD%BF%E7%94%A8.html)
- [`字节码编程，Javassist篇三《使用Javassist在运行时重新加载类「替换原方法输出不一样的结果」》`](https://bugstack.cn/itstack-demo-agent/2020/04/21/%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-Javassist%E7%AF%87%E4%B8%89-%E4%BD%BF%E7%94%A8Javassist%E5%9C%A8%E8%BF%90%E8%A1%8C%E6%97%B6%E9%87%8D%E6%96%B0%E5%8A%A0%E8%BD%BD%E7%B1%BB-%E6%9B%BF%E6%8D%A2%E5%8E%9F%E6%96%B9%E6%B3%95%E8%BE%93%E5%87%BA%E4%B8%8D%E4%B8%80%E6%A0%B7%E7%9A%84%E7%BB%93%E6%9E%9C.html)
- [`字节码编程，Javassist篇四《通过字节码插桩监控方法采集运行时入参出参和异常信息》`](https://bugstack.cn/itstack-demo-agent/2020/04/27/%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-Javassist%E7%AF%87%E5%9B%9B-%E9%80%9A%E8%BF%87%E5%AD%97%E8%8A%82%E7%A0%81%E6%8F%92%E6%A1%A9%E7%9B%91%E6%8E%A7%E6%96%B9%E6%B3%95%E9%87%87%E9%9B%86%E8%BF%90%E8%A1%8C%E6%97%B6%E5%85%A5%E5%8F%82%E5%87%BA%E5%8F%82%E5%92%8C%E5%BC%82%E5%B8%B8%E4%BF%A1%E6%81%AF.html)
- [`字节码编程，Javassist篇五《使用Bytecode指令码生成含有自定义注解的类和方法》`](https://bugstack.cn/itstack-demo-agent/2020/04/29/%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-Javassist%E7%AF%87%E4%BA%94-%E4%BD%BF%E7%94%A8Bytecode%E6%8C%87%E4%BB%A4%E7%A0%81%E7%94%9F%E6%88%90%E5%90%AB%E6%9C%89%E8%87%AA%E5%AE%9A%E4%B9%89%E6%B3%A8%E8%A7%A3%E7%9A%84%E7%B1%BB%E5%92%8C%E6%96%B9%E6%B3%95.html)
- [`字节码编程，Byte-buddy篇一《基于Byte Buddy语法创建的第一个HelloWorld》`](https://bugstack.cn/itstack-demo-agent/2020/05/08/%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-Byte-buddy%E7%AF%87%E4%B8%80-%E5%9F%BA%E4%BA%8EByte-Buddy%E8%AF%AD%E6%B3%95%E5%88%9B%E5%BB%BA%E7%9A%84%E7%AC%AC%E4%B8%80%E4%B8%AAHelloWorld.html)
- [`字节码编程，Byte-buddy篇二《监控方法执行耗时动态获取出入参类型和值》`](https://bugstack.cn/itstack-demo-agent/2020/05/12/%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-Byte-buddy%E7%AF%87%E4%BA%8C-%E7%9B%91%E6%8E%A7%E6%96%B9%E6%B3%95%E6%89%A7%E8%A1%8C%E8%80%97%E6%97%B6%E5%8A%A8%E6%80%81%E8%8E%B7%E5%8F%96%E5%87%BA%E5%85%A5%E5%8F%82%E7%B1%BB%E5%9E%8B%E5%92%8C%E5%80%BC.html)
- [`字节码编程，Byte-buddy篇三《使用委托实现抽象类方法并注入自定义注解信息》`](https://bugstack.cn/itstack-demo-agent/2020/05/14/%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B-Byte-buddy%E7%AF%87%E4%B8%89-%E4%BD%BF%E7%94%A8%E5%A7%94%E6%89%98%E5%AE%9E%E7%8E%B0%E6%8A%BD%E8%B1%A1%E7%B1%BB%E6%96%B9%E6%B3%95%E5%B9%B6%E6%B3%A8%E5%85%A5%E8%87%AA%E5%AE%9A%E4%B9%89%E6%B3%A8%E8%A7%A3%E4%BF%A1%E6%81%AF.html)

## :school_satchel: 实战设计模式

### 创建型模式

- [`1. 重学 Java 设计模式：实战工厂方法模式「多种类型商品不同接口，统一发奖服务搭建场景」`](https://bugstack.cn/itstack-demo-design/2020/05/20/%E9%87%8D%E5%AD%A6Java%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E5%B7%A5%E5%8E%82%E6%96%B9%E6%B3%95%E6%A8%A1%E5%BC%8F.html)
- [`2. 重学 Java 设计模式：实战抽象工厂模式「替换Redis双集群升级，代理类抽象场景」`](https://bugstack.cn/itstack-demo-design/2020/05/24/%E9%87%8D%E5%AD%A6Java%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E6%8A%BD%E8%B1%A1%E5%B7%A5%E5%8E%82%E6%A8%A1%E5%BC%8F.html)
- [`3. 重学 Java 设计模式：实战建造者模式「各项装修物料组合套餐选配场景」`](https://bugstack.cn/itstack-demo-design/2020/05/26/%E9%87%8D%E5%AD%A6Java%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E5%BB%BA%E9%80%A0%E8%80%85%E6%A8%A1%E5%BC%8F.html)
- [`4. 重学 Java 设计模式：实战原型模式「上机考试多套试，每人题目和答案乱序排列场景」`](https://bugstack.cn/itstack-demo-design/2020/05/28/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E5%8E%9F%E5%9E%8B%E6%A8%A1%E5%BC%8F.html)
- [`5. 重学 Java 设计模式：实战单例模式「7种单例模式案例，Effective Java 作者推荐枚举单例模式」`](https://bugstack.cn/itstack-demo-design/2020/05/31/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E5%8D%95%E4%BE%8B%E6%A8%A1%E5%BC%8F.html)

### 结构型模式

- [`1. 重学 Java 设计模式：实战适配器模式「从多个MQ消息体中，抽取指定字段值场景」)`](https://bugstack.cn/itstack-demo-design/2020/06/02/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E9%80%82%E9%85%8D%E5%99%A8%E6%A8%A1%E5%BC%8F.html)
- [`2. 重学 Java 设计模式：实战桥接模式「多支付渠道(微信、支付宝)与多支付模式(刷脸、指纹)场景」`](https://bugstack.cn/itstack-demo-design/2020/06/04/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E6%A1%A5%E6%8E%A5%E6%A8%A1%E5%BC%8F.html)
- [`3. 重学 Java 设计模式：实战组合模式「营销差异化人群发券，决策树引擎搭建场景」`](https://bugstack.cn/itstack-demo-design/2020/06/08/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E7%BB%84%E5%90%88%E6%A8%A1%E5%BC%8F.html)
- [`4. 重学 Java 设计模式：实战装饰器模式「SSO单点登录功能扩展，增加拦截用户访问方法范围场景」`](https://bugstack.cn/itstack-demo-design/2020/06/09/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E8%A3%85%E9%A5%B0%E5%99%A8%E6%A8%A1%E5%BC%8F.html)
- [`5. 重学 Java 设计模式：实战外观模式「基于SpringBoot开发门面模式中间件，统一控制接口白名单场景」`](https://bugstack.cn/itstack-demo-design/2020/06/11/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E5%A4%96%E8%A7%82%E6%A8%A1%E5%BC%8F.html)
- [`6. 重学 Java 设计模式：实战享元模式「基于Redis秒杀，提供活动与库存信息查询场景」`](https://bugstack.cn/itstack-demo-design/2020/06/14/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E4%BA%AB%E5%85%83%E6%A8%A1%E5%BC%8F.html)
- [`7. 重学 Java 设计模式：实战代理模式「模拟mybatis-spring中定义DAO接口，使用代理类方式操作数据库原理实现场景」`](https://bugstack.cn/itstack-demo-design/2020/06/16/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E4%BB%A3%E7%90%86%E6%A8%A1%E5%BC%8F.html)

### 行为模式

- [`1. 重学 Java 设计模式：实战责任链模式「模拟618电商大促期间，项目上线流程多级负责人审批场景」`](https://bugstack.cn/itstack-demo-design/2020/06/18/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E8%B4%A3%E4%BB%BB%E9%93%BE%E6%A8%A1%E5%BC%8F.html)
- [`2. 重学 Java 设计模式：实战命令模式「模拟高档餐厅八大菜系，小二点单厨师烹饪场景」`](https://bugstack.cn/itstack-demo-design/2020/06/21/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E5%91%BD%E4%BB%A4%E6%A8%A1%E5%BC%8F.html)
- [`3. 重学 Java 设计模式：实战迭代器模式「模拟公司组织架构树结构关系，深度迭代遍历人员信息输出场景」`](https://bugstack.cn/itstack-demo-design/2020/06/23/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E8%BF%AD%E4%BB%A3%E5%99%A8%E6%A8%A1%E5%BC%8F.html)
- [`4. 重学 Java 设计模式：实战中介者模式「按照Mybatis原理手写ORM框架，给JDBC方式操作数据库增加中介者场景」`](https://bugstack.cn/itstack-demo-design/2020/06/27/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E4%B8%AD%E4%BB%8B%E8%80%85%E6%A8%A1%E5%BC%8F.html)
- [`5. 重学 Java 设计模式：实战备忘录模式「模拟互联网系统上线过程中，配置文件回滚场景」`](https://bugstack.cn/itstack-demo-design/2020/06/28/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E5%A4%87%E5%BF%98%E5%BD%95%E6%A8%A1%E5%BC%8F.html)
- [`6. 重学 Java 设计模式：实战观察者模式「模拟类似小客车指标摇号过程，监听消息通知用户中签场景」`](https://bugstack.cn/itstack-demo-design/2020/06/30/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E8%A7%82%E5%AF%9F%E8%80%85%E6%A8%A1%E5%BC%8F.html)
- [`7. 重学 Java 设计模式：实战状态模式「模拟系统营销活动，状态流程审核发布上线场景」`](https://bugstack.cn/itstack-demo-design/2020/07/02/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E7%8A%B6%E6%80%81%E6%A8%A1%E5%BC%8F.html)
- [`8. 重学 Java 设计模式：实战策略模式「模拟多种营销类型优惠券，折扣金额计算策略场景」`](https://bugstack.cn/itstack-demo-design/2020/07/05/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E7%AD%96%E7%95%A5%E6%A8%A1%E5%BC%8F.html)
- [`9. 重学 Java 设计模式：实战模版模式「模拟爬虫各类电商商品，生成营销推广海报场景」`](https://bugstack.cn/itstack-demo-design/2020/07/07/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E6%A8%A1%E6%9D%BF%E6%A8%A1%E5%BC%8F.html)
- [`10. 重学 Java 设计模式：实战访问者模式「模拟家长与校长，对学生和老师的不同视角信息的访问场景」`](https://bugstack.cn/itstack-demo-design/2020/07/09/%E9%87%8D%E5%AD%A6-Java-%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F-%E5%AE%9E%E6%88%98%E8%AE%BF%E9%97%AE%E8%80%85%E6%A8%A1%E5%BC%8F.html)

## :bookmark_tabs: 面经手册

- [`面经手册 · 开篇《面试官都问我啥》`](https://bugstack.cn/interview/2020/07/28/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E5%BC%80%E7%AF%87-%E9%9D%A2%E8%AF%95%E5%AE%98%E9%83%BD%E9%97%AE%E6%88%91%E5%95%A5.html)
- [`面经手册 · 第1篇《认知自己的技术栈盲区》`](https://bugstack.cn/interview/2020/07/30/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC1%E7%AF%87-%E8%AE%A4%E7%9F%A5%E8%87%AA%E5%B7%B1%E7%9A%84%E6%8A%80%E6%9C%AF%E6%A0%88%E7%9B%B2%E5%8C%BA.html)
- [`面经手册 · 第2篇《数据结构，HashCode为什么使用31作为乘数？》`](https://bugstack.cn/interview/2020/08/04/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC2%E7%AF%87-%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84-HashCode%E4%B8%BA%E4%BB%80%E4%B9%88%E4%BD%BF%E7%94%A831%E4%BD%9C%E4%B8%BA%E4%B9%98%E6%95%B0.html)
- [`面经手册 · 第3篇《HashMap核心知识，扰动函数、负载因子、扩容链表拆分，深度学习》`](https://bugstack.cn/interview/2020/08/07/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC3%E7%AF%87-HashMap%E6%A0%B8%E5%BF%83%E7%9F%A5%E8%AF%86-%E6%89%B0%E5%8A%A8%E5%87%BD%E6%95%B0-%E8%B4%9F%E8%BD%BD%E5%9B%A0%E5%AD%90-%E6%89%A9%E5%AE%B9%E9%93%BE%E8%A1%A8%E6%8B%86%E5%88%86-%E6%B7%B1%E5%BA%A6%E5%AD%A6%E4%B9%A0.html)
- [`面经手册 · 第4篇《HashMap数据插入、查找、删除、遍历，源码分析》`](https://bugstack.cn/interview/2020/08/13/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC4%E7%AF%87-HashMap%E6%95%B0%E6%8D%AE%E6%8F%92%E5%85%A5-%E6%9F%A5%E6%89%BE-%E5%88%A0%E9%99%A4-%E9%81%8D%E5%8E%86-%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.html)
- [`面经手册 · 第5篇《看图说话，讲解2-3平衡树「红黑树的前身」》`](https://bugstack.cn/interview/2020/08/16/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC5%E7%AF%87-%E7%9C%8B%E5%9B%BE%E8%AF%B4%E8%AF%9D-%E8%AE%B2%E8%A7%A32-3%E5%B9%B3%E8%A1%A1%E6%A0%91-%E7%BA%A2%E9%BB%91%E6%A0%91%E7%9A%84%E5%89%8D%E8%BA%AB.html)
- [`面经手册 · 第6篇《带着面试题学习红黑树操作原理，解析什么时候染色、怎么进行旋转、与2-3树有什么关联》`](https://bugstack.cn/interview/2020/08/20/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC6%E7%AF%87-%E5%B8%A6%E7%9D%80%E9%9D%A2%E8%AF%95%E9%A2%98%E5%AD%A6%E4%B9%A0%E7%BA%A2%E9%BB%91%E6%A0%91%E6%93%8D%E4%BD%9C%E5%8E%9F%E7%90%86-%E8%A7%A3%E6%9E%90%E4%BB%80%E4%B9%88%E6%97%B6%E5%80%99%E6%9F%93%E8%89%B2-%E6%80%8E%E4%B9%88%E8%BF%9B%E8%A1%8C%E6%97%8B%E8%BD%AC-%E4%B8%8E2-3%E6%A0%91%E6%9C%89%E4%BB%80%E4%B9%88%E5%85%B3%E8%81%94.html)
- [`面经手册 · 第7篇《ArrayList也这么多知识？一个指定位置插入就把谢飞机面晕了！》`](https://bugstack.cn/interview/2020/08/27/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC7%E7%AF%87-ArrayList%E4%B9%9F%E8%BF%99%E4%B9%88%E5%A4%9A%E7%9F%A5%E8%AF%86-%E4%B8%80%E4%B8%AA%E6%8C%87%E5%AE%9A%E4%BD%8D%E7%BD%AE%E6%8F%92%E5%85%A5%E5%B0%B1%E6%8A%8A%E8%B0%A2%E9%A3%9E%E6%9C%BA%E9%9D%A2%E6%99%95%E4%BA%86.html)
- [`面经手册 · 第8篇《LinkedList插入速度比ArrayList快？你确定吗？》`](https://bugstack.cn/interview/2020/08/30/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC8%E7%AF%87-LinkedList%E6%8F%92%E5%85%A5%E9%80%9F%E5%BA%A6%E6%AF%94ArrayList%E5%BF%AB-%E4%BD%A0%E7%A1%AE%E5%AE%9A%E5%90%97.html)
- [`面经手册 · 第9篇《队列是什么？什么是双端队列、延迟对列、阻塞队列，全是知识盲区！》`](https://bugstack.cn/interview/2020/09/03/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC9%E7%AF%87-%E9%98%9F%E5%88%97%E6%98%AF%E4%BB%80%E4%B9%88-%E4%BB%80%E4%B9%88%E6%98%AF%E5%8F%8C%E7%AB%AF%E9%98%9F%E5%88%97-%E5%BB%B6%E8%BF%9F%E5%AF%B9%E5%88%97-%E9%98%BB%E5%A1%9E%E9%98%9F%E5%88%97-%E5%85%A8%E6%98%AF%E7%9F%A5%E8%AF%86%E7%9B%B2%E5%8C%BA.html)
- [`面经手册 · 第10篇《扫盲java.util.Collections工具包，学习排序、二分、洗牌、旋转算法》`](https://bugstack.cn/interview/2020/09/10/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC10%E7%AF%87-%E6%89%AB%E7%9B%B2java.util.Collections%E5%B7%A5%E5%85%B7%E5%8C%85-%E5%AD%A6%E4%B9%A0%E6%8E%92%E5%BA%8F-%E4%BA%8C%E5%88%86-%E6%B4%97%E7%89%8C-%E6%97%8B%E8%BD%AC%E7%AE%97%E6%B3%95.html)
- [`面经手册 · 第11篇《StringBuilder 比 String 快？空嘴白牙的，证据呢！》`](https://bugstack.cn/interview/2020/09/17/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC11%E7%AF%87-StringBuilder-%E6%AF%94-String-%E5%BF%AB-%E7%A9%BA%E5%98%B4%E7%99%BD%E7%89%99%E7%9A%84-%E8%AF%81%E6%8D%AE%E5%91%A2.html)
- [`面经手册 · 第12篇《面试官，ThreadLocal 你要这么问，我就挂了！》`](https://bugstack.cn/interview/2020/09/23/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC12%E7%AF%87-%E9%9D%A2%E8%AF%95%E5%AE%98-ThreadLocal-%E4%BD%A0%E8%A6%81%E8%BF%99%E4%B9%88%E9%97%AE-%E6%88%91%E5%B0%B1%E6%8C%82%E4%BA%86.html)
- [`面经手册 · 第13篇《除了JDK、CGLIB，还有3种类代理方式？面试又卡住！》`](https://bugstack.cn/interview/2020/10/14/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC13%E7%AF%87-%E9%99%A4%E4%BA%86JDK-CGLIB-%E8%BF%98%E6%9C%893%E7%A7%8D%E7%B1%BB%E4%BB%A3%E7%90%86%E6%96%B9%E5%BC%8F-%E9%9D%A2%E8%AF%95%E5%8F%88%E5%8D%A1%E4%BD%8F.html)
- [`面经手册 · 第14篇《volatile 怎么实现的内存可见？没有 volatile 一定不可见吗？》`](https://bugstack.cn/interview/2020/10/21/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC14%E7%AF%87-volatile-%E6%80%8E%E4%B9%88%E5%AE%9E%E7%8E%B0%E7%9A%84%E5%86%85%E5%AD%98%E5%8F%AF%E8%A7%81-%E6%B2%A1%E6%9C%89-volatile-%E4%B8%80%E5%AE%9A%E4%B8%8D%E5%8F%AF%E8%A7%81%E5%90%97.html)
- [`面经手册 · 第15篇《码农会锁，synchronized 解毒，剖析源码深度分析！》`](https://bugstack.cn/interview/2020/10/28/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC15%E7%AF%87-%E7%A0%81%E5%86%9C%E4%BC%9A%E9%94%81-synchronized-%E8%A7%A3%E6%AF%92-%E5%89%96%E6%9E%90%E6%BA%90%E7%A0%81%E6%B7%B1%E5%BA%A6%E5%88%86%E6%9E%90.html)
- [`面经手册 · 第16篇《码农会锁，ReentrantLock之公平锁讲解和实现》`](https://bugstack.cn/interview/2020/11/04/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC16%E7%AF%87-%E7%A0%81%E5%86%9C%E4%BC%9A%E9%94%81-ReentrantLock%E4%B9%8B%E5%85%AC%E5%B9%B3%E9%94%81%E8%AE%B2%E8%A7%A3%E5%92%8C%E5%AE%9E%E7%8E%B0.html)
- [`面经手册 · 第17篇《码农会锁，ReentrantLock之AQS原理分析和实践使用》`](https://bugstack.cn/interview/2020/11/11/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC17%E7%AF%87-%E7%A0%81%E5%86%9C%E4%BC%9A%E9%94%81-ReentrantLock%E4%B9%8BAQS%E5%8E%9F%E7%90%86%E5%88%86%E6%9E%90%E5%92%8C%E5%AE%9E%E8%B7%B5%E4%BD%BF%E7%94%A8.html)

## :racehorse: 码场故事

- [BATJTMD，大厂招聘，都招什么样Java程序员？](https://bugstack.cn/itstack-code-life/2020/11/15/BATJTMD-大厂招聘-都招什么样Java程序员.html)
- [一个简单的能力，决定你是否会学习！](https://bugstack.cn/itstack-code-life/2020/11/08/一个简单的能力-决定你是否会学习.html)
- [刚毕业不久，接私活赚了2万块！](https://bugstack.cn/itstack-code-life/2020/11/01/刚毕业不久-接私活赚了2万块.html)
- [今天你写博客了吗？](https://bugstack.cn/itstack-code-life/2020/10/25/今天你写博客了吗.html)
- [UML类图还不懂？来看看这版乡村爱情类图，一把学会！](https://bugstack.cn/itstack-code-life/2020/10/18/UML类图还不懂-来看看这版乡村爱情类图-一把学会.html)
- [为了省钱，我用1天时间把PHP学了！](https://bugstack.cn/itstack-code-life/2020/10/11/为了省钱-我用1天时间把PHP学了.html)
- [让人怪不好意思的，粉丝破万，用了1年！](https://bugstack.cn/itstack-code-life/2020/10/09/让人怪不好意思的-粉丝破万-用了1年.html)
- [PPT画成这样，述职答辩还能过吗？](https://bugstack.cn/itstack-code-life/2020/09/27/PPT画成这样-述职答辩还能过吗.html)
- [程序员为什么热衷于造轮子，升职加薪吗？](https://bugstack.cn/itstack-code-life/2020/09/20/程序员为什么热衷于造轮子-升职加薪吗.html)
- [一次代码评审，差点过不了试用期！](https://bugstack.cn/itstack-demo-any/2020/09/14/一次代码评审-差点过不了试用期.html)
- [握草，你竟然在代码里下毒！](https://bugstack.cn/itstack-demo-any/2020/09/06/握草-你竟然在代码里下毒.html)
- [13年毕业，用两年时间从外包走进互联网大厂](https://bugstack.cn/itstack-code-life/2020/08/25/13年毕业-用两年时间从外包走进互联网大厂.html)
- [12天，这本《重学Java设计模式》PDF书籍下载量9k，新增粉丝1400人，Github上全球推荐榜！](https://bugstack.cn/itstack-code-life/2020/07/25/12天-这本-重学Java设计模式-PDF书籍下载量9k-新增粉丝1400人-Github上全球推荐榜.html)
- [讲道理，只要你是一个爱折腾的程序员，毕业找工作真的不需要再花钱培训！](https://bugstack.cn/itstack-code-life/2020/04/30/讲道理-只要你是一个爱折腾的程序员-毕业找工作真的不需要再花钱培训.html)
- [工作两年简历写成这样，谁要你呀！](https://bugstack.cn/itstack-code-life/2020/04/11/工作两年简历写成这样-谁要你呀.html)
- [大学四年到毕业工作5年的学习路线资源汇总](https://bugstack.cn/itstack-code-life/2020/03/31/大学四年到毕业工作5年的学习路线资源汇总.html)
- [20年3月27日，Github被攻击。我的GitPage博客也挂了，紧急修复之路，也教会你搭建 Jekyll 博客！](https://bugstack.cn/itstack-code-life/2020/03/28/GithubAndMyBlogAttacked.html)
- [汉字不能编程？别闹了，只是看着有点豪横！容易被开除！](https://bugstack.cn/itstack-demo-any/2020/05/05/%E6%B1%89%E5%AD%97%E4%B8%8D%E8%83%BD%E7%BC%96%E7%A8%8B-%E5%88%AB%E9%97%B9%E4%BA%86-%E5%8F%AA%E6%98%AF%E7%9C%8B%E7%9D%80%E6%9C%89%E7%82%B9%E8%B1%AA%E6%A8%AA-%E5%AE%B9%E6%98%93%E8%A2%AB%E5%BC%80%E9%99%A4.html) - [源码](https://github.com/fuzhengwei/CodeGuide/tree/master/src/itstack-demo-01)

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

---

<img src="https://bugstack.cn/assets/images/tip.jpg?x-oss-process=style/may" width="180" height="180"/>

**:gift_heart: 感谢大家对我资金的赞赏**

| 时间       | 小伙伴     | 赞赏金额 |
| ---------- | ---------- | -------- |
| 2020-11-16 | wuyou | 5.00元 |
| 2020-11-05 | 刘荣清 | 10.00元 |
| 2020-09-21 | 军 | 10.00元 |
| 2020-09-16 | 冰水之畔 | 20.00元 |
| 2020-08-17 | 郭嘉伟 | 5.00元 |
| 2020-08-07 | Jin Se | 5.00元 |
| 2020-07-25 | fun  | 10.00元 |
| 2020-06-16 | 贾学兵     | 5.00元 |
| 2020-06-11 | 刘洪泽     | 6.66元 |
| 2020-06-05 | [时光之刃](https://github.com/996546860) |   5元   |
| 2020-05-25 | [柠檬楠](https://juejin.im/user/5db135d86fb9a020512b3289/posts)    | 23.33元     |
| 2020-05-19 | 王刚       | 20元     |
| 2020-05-19 | 如鱼       | 3元      |
| 2020-05-18 | 帅地       | 6.66元   |
| 2020-05-18 | 放飞心情   | 6元      |
| 2020-05-18 | lemon      | 1元      |
| 2020-05-18 | 贺         | 1元      |
| 2020-05-18 | !sssss     | 1元      |
| 2020-05-18 | ZHANG      | 1元      |
| 2020-05-18 | vovovov    | 1元      |
| 2020-04-01 | 姬贵阳     | 1元      |
| 2020-02-29 | 日落黄昏下 | 1元      |
| 2019-12-26 | clearDay   | 1元      |
| 2019-11-27 | Jasonzhou  | 1元      |
| 2019-11-08 | 贺         | 1元      |
| 2019-08-06 | 贺         | 1元      |





