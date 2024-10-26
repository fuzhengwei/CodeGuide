---
title: 第1节：对接OpenAI项目额度奖品接口
pay: https://t.zsxq.com/ByrMx
---

# 《大营销平台系统设计实现》 - 外部对接 第1节：对接OpenAI项目额度奖品接口

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★★★☆☆
- **本章重点**：新增 OpenAI 账户额度调整接口，增加大营销 openai_use_count 奖品发放实现类，完成发奖对接。
- **课程视频**：[https://t.zsxq.com/w3Ebn](https://t.zsxq.com/w3Ebn)

**版权说明**：©本项目与星球签约合作，受[《中华人民共和国著作权法实施条例》](http://www.gov.cn/zhengce/2020-12/26/content_5573623.htm) 版权法保护，禁止任何理由和任何方式公开(public)源码、资料、视频等内容到Github、Gitee等，违反可追究进一步的法律行动。

## 一、本章诉求

**前置说明**：在学习本章节前，需要对星球内的 [《OpenAI 应用项目》](https://t.zsxq.com/19aSkDvYB) 有所了解。

星球的 OpenAI 业务项目是一个以售卖额度提供 ChatGLM、ChatGPT 各类服务为载体的 OpenAI 业务应用。那么大营销项目就可以结合到 OpenAI 应用项目中提供积分、兑换、返利、抽奖这样的服务模块，增强 OpenAI 业务项目的拉新、促活、留存用户的能力。

整个对接过程分为2个阶段，一个是 OpenAI 项目提供账户调额能力作为奖品接口给大营销使用，另外一个是 OpenAI 项目，对接大营销的 RPC/HTTP 接口，搭建营销活动页。

## 二、对接流程

整个对接过程示意；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-50-01.png" width="850px">
</div>

- 如图，为用户使用 OpenAI 应用项目时候，一个前端，两个后端服务的对接过程。这里你就可以把大营销看做为一个微服务了。
- 在这一节，我们先来完成，通过http接口，发放额度奖品的操作。
