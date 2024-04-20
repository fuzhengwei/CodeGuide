---
title: 第21节：活动信息API迭代和功能完善
pay: https://t.zsxq.com/19aanGm3U
---

# 《大营销平台系统设计实现》 - 营销服务 第21节：活动信息API迭代和功能完善

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★★★☆☆
- **本章重点**：完善接口功能，增加策略加锁key的过期时间，修复一些bug
- **课程视频**：https://t.zsxq.com/19fVwshWT](https://t.zsxq.com/19fVwshWT)

**版权说明**：©本项目与星球签约合作，受[《中华人民共和国著作权法实施条例》](http://www.gov.cn/zhengce/2020-12/26/content_5573623.htm) 版权法保护，禁止任何理由和任何方式公开(public)源码、资料、视频等内容到Github、Gitee等，违反可追究进一步的法律行动。

## 一、本章诉求

扩展查询奖品信息接口【queryRaffleAwardList】，以用户活动为视角进行查询增加返回信息。同时修复Redisson序列化、加锁过期时间问题。

## 二、业务流程

本节实现功能到不复杂，但涉及的代码量也比较多。因为整个过程要在策略的查询进行扩展，提供用`活动ID+用户ID`进行查询。这样才能查询到活动配置配置的信息，用户的抽奖参与次数，以及扩展查询策略的规则（rule_lock）配置。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/big-market/big-market-28-01.png" width="650px">
</div>

1. 提供带有活动和用户属性信息判断的抽奖列表数据，满足后续前端展示抽奖列表时可以渲染出奖品是否被加锁并提示用户还需要抽奖几次才能解锁奖品。
2. 在以活动为开始的抽奖，对抽奖策略加锁的key设置过期时间为活动结束时间。
3. 完善 Redis 序列化操作，Redisson 添加序列化设置。
4. 抽奖sku消耗完毕的队列清空，暂时先去掉。因为目前使用的一套队列，避免都清空喽。这部分不会影响到库存的更新。
