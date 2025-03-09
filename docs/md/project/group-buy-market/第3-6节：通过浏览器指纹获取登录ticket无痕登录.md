---
title: 【更】第3-6节：通过浏览器指纹获取登录ticket + 无痕登录
pay: https://t.zsxq.com/cGRlq
---

# 《拼团交易平台系统》第3-6节：通过浏览器指纹获取登录ticket + 无痕登录

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/8Oa5K](https://t.zsxq.com/8Oa5K)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

增强登录安全性，使用浏览器指纹，获取唯一 Ticket 作为登录二维码使用。

本章节属于知识点的扩展，即使没实现，也不影响整体功能。

## 二、业务流程

如图，增加浏览器指纹标识；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/group-buy-market/group-buy-market-3-6-01.png" width="750px">
</div>

- 原有的微信扫码登录拿到的是同一个 Tikcet，这样是满足测试，但对于上线给其他人一起使用是不太安全的。所以我们可以通过浏览器指纹的方式进行处理。
- 浏览器指纹生成唯一标识，通过唯一标识获取ticket，并在轮训验证中使用浏览器指纹加验。确保浏览器指纹缓存的 ticket 和登录后的 ticket 保持一致。

