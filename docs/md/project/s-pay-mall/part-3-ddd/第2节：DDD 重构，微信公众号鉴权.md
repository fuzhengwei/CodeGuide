---
title: 第3-2节：DDD 重构，微信公众号鉴权
pay: https://t.zsxq.com/wBobE
---

# 《小型支付商城系统》第3-2节：DDD 重构，微信公众号鉴权

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

在【第2部分 - 需求设计】[工程四色建模](https://articles.zsxq.com/id_pj4jlidovrpd.html)里，我们已经做了 DDD 部分的建模设计。到这一节学习前，可以回顾下四色建模设计时所做的内容。关于 DDD 的知识，一部分是工程四色建模拆分领域功能的界限上下文战略知识，另外一部分就是本节这里的战术设计指导落地。

本节小傅哥会带着大家，对照 MVC 中的微信公众号鉴权代码实现，拆解到 DDD 中进行实现。

> 因为在前面的内容讲解中已经介绍了很多的业务，所以在本节的实现过程中，重点会关注 mvc 到 ddd 的功能重构细节差异。

## 二、功能分区

DDD 映射下的六边形架构，会有非常明确的分层分区，各个模块都在自己的职责范围内实现。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/s-pay-mall/s-pay-mall-ddd-3-2-02.png" width="750px">
</div>

- trigger 下实现让外部调用我们的接口，这里是微信公众号接口的鉴权和接收消息。
- types 下加入了 sdk 微信的对接实现。
- app 是启动层，里面增加 yml 配置信息。