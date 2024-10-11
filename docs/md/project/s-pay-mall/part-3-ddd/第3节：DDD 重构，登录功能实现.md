---
title: 第3-3节：DDD重构，登录功能设计实现
pay: https://t.zsxq.com/Vc1Os
---

# 《小型支付商城系统》第3-3节：DDD重构，登录功能设计实现

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

通过 DDD 领域设计，从 MVC 工程中拆分登录功能到 DDD 工程中实现。

本节会涉及到 api、domain、infrastructure、trigger、app，几层模块的使用，有了这一节的功能实现，会更加清楚 MVC 和 DDD 实现差异。不过编码的核心本质是不变的，所有的分层结构都是为了让代码实现逻辑更加清晰，降低后续的迭代成本。

## 二、登录模型

通过 DDD 领域驱动设计，陆续实现扫描登录、下单等流程模块。这一部分的设计可以参考第2部分中的四色建模设计。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/s-pay-mall/s-pay-mall-2-2-03.png" width="950px">
</div>

- 本节我们先来实现登录功能领域，登录凭证、校验凭证以及保存登录状态。