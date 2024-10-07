---
title: 第3-6节：DDD 重构，支付回调处理
pay: https://t.zsxq.com/3X9GA
---

# 《小型支付商城系统》第3-6节：DDD 重构，支付回调处理

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

通过 DDD 领域设计，从 MVC 工程拆分支付回调和接口到 DDD 工程中实现。

从 MVC 到 DDD，是编码分层清晰，是逻辑实现整洁，也是后续的维护更加容易。其实也说明 DDD 的工程标准更加强。

## 二、回调模型

通过 DDD 领域驱动设计，本节实现支付回调过程。这一部分的设计可以参考第2部分中的四色建模设计。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/s-pay-mall/s-pay-mall-2-2-03.png" width="950px">
</div>

- 本节我们实现支付回调处理。