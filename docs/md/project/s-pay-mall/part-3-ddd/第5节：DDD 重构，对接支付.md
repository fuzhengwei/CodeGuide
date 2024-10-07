---
title: 第3-5节：DDD 重构，对接支付
pay: https://t.zsxq.com/3X9GA
---

# 《小型支付商城系统》第3-5节：DDD 重构，对接支付

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

通过 DDD 领域设计，从 MVC 工程拆分创建支付单到 DDD 工程中实现。

在我们不断把功能从 MVC 拆分到 DDD 的实现中，也是从面向过程到面向对象的开发思维转变。编程的能力提升就是不断地锻炼各种能力。

## 二、下单模型

通过 DDD 领域驱动设计，本节实现点击下单到完成支付宝订单创建过程。这一部分的设计可以参考第2部分中的四色建模设计。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/s-pay-mall/s-pay-mall-2-2-03.png" width="950px">
</div>

- 本节我们实现点击下单创建出支付宝沙箱订单完成。