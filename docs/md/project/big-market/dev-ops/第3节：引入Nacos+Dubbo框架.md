---
title: 第3节：引入Nacos+Dubbo框架
pay: https://t.zsxq.com/17HkS0CI0
---

# 《大营销平台系统设计实现》 - 开发运维 第3节：引入Nacos+Dubbo框架

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★★★☆☆
- **本章重点**：引入分布式技术栈框架 Nacos + Dubbo，用于微服务间调用，提高信息数据传输效率。
- **课程视频**：[https://t.zsxq.com/bzjMv](https://t.zsxq.com/bzjMv)

**版权说明**：©本项目与星球签约合作，受[《中华人民共和国著作权法实施条例》](http://www.gov.cn/zhengce/2020-12/26/content_5573623.htm) 版权法保护，禁止任何理由和任何方式公开(public)源码、资料、视频等内容到Github、Gitee等，违反可追究进一步的法律行动。

## 一、本章诉求

因为分布式技术栈框架 Nacos + Dubbo，用于微服务间调用，提高信息数据传输效率。在本节为大家讲解 Dubbo 的核心模型结构，通信协议配置和对接使用。

**前置知识学习**，如果你此前没了解过 RPC 可以通过星球的基础教程进行学习，这里有 RPC 的讲解、简单 RPC 实现和实践运用。地址：[https://bugstack.cn/md/road-map/dubbo.html](https://bugstack.cn/md/road-map/dubbo.html)

## 二、架构说明

以整个互联网电商公司视角来看，它会包括；商品系统、购物系统、营销系统、交易系统、结算系统、清分系统、对账系统、以及各类运营系统。而咱们做的大营销系统就是其中的一个微服务系统，这个系统可以被分布式部署和使用。

而把一个系统扩展为分布式系统，会有很多相应的技术栈引入来解决数据传输问题，聚合查询问题，链路监控问题等。这些内容会在这个系列中逐步引入带大家一起学习。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-41-01.png?raw=true" width="650px">
</div>

- 首先，Dubbo 这样的 RPC 框架，是比 HTTP 在微服务间的通信效率更高的，所以各个中等规模以上的互联网公司也是更喜欢引入 RPC 框架来解决微服务间通信问题。
- 不过像 Dubbo、SpringCloud，都只是个框架而已，一个系统的核心实现不受框架影响，你可以按照实际诉求，更换或者支持多种通信协议。

