# CodeGuide | 程序员编码指南

> **作者：** 小傅哥，Java Developer，[:pencil2: 虫洞 · 科技栈，作者](https://bugstack.cn)，[:trophy: CSDN 博客专家](https://bugstack.blog.csdn.net)

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

|   序号  |   图标  |   专栏文章  |   资源下载  |   状态  |
|   :---:   |   :---:   |   :---    |   :---    |   :---:   |
|   0   |   :volcano: |   `网络调试助手`  |   [NetAssist下载](https://download.csdn.net/download/yao__shun__yu/11835105) |   [√]   |
|   1   |   :sound: |   [`Netty4.x专题`](#sound-Netty4专题)  |   [itstack-demo-netty](https://github.com/fuzhengwei/itstack-demo-netty) |   [√]   |
|   2   |   :electric_plug: |   [`手写RPC框架`](#electric_plug-手写RPC框架) |   [itstack-demo-rpc](https://github.com/fuzhengwei/itstack-demo-rpc)  |   [√]     |
|   3   |   :computer:  |   [`用Java实现JVM`](#computer-用Java实现JVM)  |   [itstack-demo-jvm](https://github.com/fuzhengwei/itstack-demo-jvm)  |   [√]     |
|   4   |   :ghost: |   [`基于JavaAgent的全链路监控`](#ghost-基于JavaAgent的全链路监控)   |   [itstack-demo-agent](https://github.com/fuzhengwei/itstack-demo-agent ) |   [√]     |
|   5   |   :shower:    |   [`iot-gateway网关案例`](#shower-iot-gateway网关案例) |   [itstack-demo-iot-gatewary](https://github.com/fuzhengwei/itstack-demo-iot-gatewary)    |   [√]     |
|   6   |   :triangular_ruler:  |   [`DDD领域驱动设计落地`](#triangular_ruler-DDD领域驱动设计落地) |   [itstack-demo-ddd](https://github.com/fuzhengwei/itstack-demo-ddd)  |   [√]     |
|   7   |   :outbox_tray:   |   [`SpringCloud入门案例`](#outbox_tray-SpringCloud入门案例) |   [itstack-demo-springcloud](https://github.com/fuzhengwei/itstack-demo-springcloud)  |   [√]     |
|   8   |   :performing_arts:   |   [`微信公众号开发`](#performing_arts-微信公众号开发) |   [itstack-ark-wx](https://github.com/fuzhengwei/itstack-ark-wx-test) |   [√]     |
|   9   |   :nut_and_bolt:  |   [`SpringBoot中间件开发`](#nut_and_bolt-SpringBoot中间件开发) |   [door-spring-boot-starter](https://github.com/fuzhengwei/door-spring-boot-starter)  |   [√]     |
|   10  |   :art:   |   [`服务框架搭建`](#art-服务框架搭建)  |   [itstack-demo-frame](https://github.com/fuzhengwei/itstack-demo-frame)  |   [√]     |
|   11  |   :flashlight:    |   [`源码分析(Spring、Mybatis、Schedule)`](#flashlight-源码分析)   |   [itstack-demo-code](https://github.com/fuzhengwei/itstack-demo-code)    |   [√]     |
|   12  |   :airplane:  |   [`Drools规则引擎`](#airplane-Drools规则引擎)  |   [itstack-demo-drools](https://github.com/fuzhengwei/itstack-demo-drools)    |   [√]     |
|   13  |   :tractor:   |   [`ASM字节码编程`](#tractor-ASM字节码编程)    |   [itstack-demo-asm](https://github.com/fuzhengwei/itstack-demo-asm)  |   [√]     |
|   14  |   :paw_prints:    |   [`我的大学四年到毕业工作5年的学习资源和面试汇总`](#paw_prints-我的大学四年到毕业工作5年的学习路线资源和面试汇总)   |   [https://pan.baidu.com/s/4mmX7sDy - （if链接失效，加我微信：fustack）](https://pan.baidu.com/s/4mmX7sDy)    |   [√]     |
|   15  |   :walking:    |   [`Netty+JavaFx实战：仿桌面版微信聊天`](#walking-Netty仿桌面版微信聊天)   |   [NaiveChat](https://github.com/fuzhengwei/NaiveChat)    |   [√]     |
|   16  |   :bookmark_tabs:    |   [`JDK1.8新特性41个案例讲解`](https://bugstack.cn/itstack-demo-any/2019/12/10/%E6%9C%89%E7%82%B9%E5%B9%B2%E8%B4%A7-Jdk1.8%E6%96%B0%E7%89%B9%E6%80%A7%E5%AE%9E%E6%88%98%E7%AF%87(41%E4%B8%AA%E6%A1%88%E4%BE%8B).html)   |   [itstack-demo-jdk8](https://github.com/fuzhengwei/itstack-demo-jdk8)    |   [√]     |
|   17  |   :bike:    |   [`字节码编程专栏`](#bike-字节码编程专栏)   |   [itstack-demo-bytecode](https://github.com/fuzhengwei/itstack-demo-bytecode)    |   [√]     |

**如果**，以上某些资源不能下载获取，可以添加作者：小傅哥，微信(fustack)

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

感谢以下人员对本仓库做出的贡献，当然不仅仅只有这些贡献者，这里就不一一列举了。如果你希望被添加到这个名单中，并且提交过 Issue 或者 PR，请与我联系。

<a href="https://github.com/linw7">
    <img src="https://avatars0.githubusercontent.com/u/3761578?s=460&v=4" style="border-radius:5px" width="50px">
</a> 
<a href="https://github.com/g10guang">
    <img src="https://avatars0.githubusercontent.com/u/30902679?s=400&v=4" style="border-radius:5px" width="50px">
</a> 
<a href="https://github.com/g10guang">
    <img src="https://avatars1.githubusercontent.com/u/15908832?s=180&v=4" style="border-radius:5px" width="50px">
</a>
