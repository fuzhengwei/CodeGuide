---
title: 第3节：RPC接口对接支付返利
pay: https://t.zsxq.com/fFntX
---

# 《大营销平台系统设计实现》 - 外部对接 第3节：RPC接口对接支付返利

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★★★☆☆
- **本章重点**：通过在大营销 big-market 新增提供的通用返利 RPC 接口，由 OpenAI 服务 chatgpt-data 系统在支付完成接收到回调消息后进行对接完成返利动作。
- **课程视频**：[https://t.zsxq.com/15gLHtPaU](https://t.zsxq.com/15gLHtPaU)

**版权说明**：©本项目与星球签约合作，受[《中华人民共和国著作权法实施条例》](http://www.gov.cn/zhengce/2020-12/26/content_5573623.htm) 版权法保护，禁止任何理由和任何方式公开(public)源码、资料、视频等内容到Github、Gitee等，违反可追究进一步的法律行动。

## 一、本章诉求

通过在大营销 big-market 新增提供的通用返利 RPC 接口，由 OpenAI 服务 chatgpt-data 系统在支付完成接收到回调消息后进行对接完成返利动作。

本节会使用到注册到 Nacos 的 Dubbo 服务，完成内部微服务的对接。Dubbo 的优势在于底层的通信协议比 HTTP 更加迅速，适合微服务间通信使用。不过这样的对接方式会强依赖另外一方系统的存在，否则启动会失败。所以后续我们部署的时候，也会把 chatgpt-data 依赖于 big-market 一起部署。

前置资料：关于dubbo配置使用的基础教程：[https://bugstack.cn/md/road-map/dubbo.html](https://bugstack.cn/md/road-map/dubbo.html)

## 二、对接流程

通过 RPC 完成微服务的对接；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-52-01.png" width="650px">
</div>

- 用户下单完成支付后，会接收到支付消息，之后调用大营销提供的返利接口进行返利。返利为；积分和抽奖次数。