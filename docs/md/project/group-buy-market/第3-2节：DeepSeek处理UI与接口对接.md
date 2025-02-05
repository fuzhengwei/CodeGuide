---
title: 【更】第3-2节：DeepSeek处理UI与接口对接
pay: https://t.zsxq.com/LtzHY
---

# 《拼团交易平台系统》第3-2节：DeepSeek处理UI与接口对接

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/qKjOi](https://t.zsxq.com/qKjOi)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

OpenAI 适合什么场景的编程？我觉得前端算一个，为什么呢？

前端相对于后端，没有数据库、缓存、RPC、MQ、任务调度等等一系列的复杂组件运用，而更多的工作都是在既定路线上的重复处理。所以，它的场景就更适合 OpenAI 一次的加载和理解，并给出正确性更高的编码实现，从而减轻开发人员的工作量。

在有这类的 OpenAI 产品后，我在开发前端代码上，确实更加得心应手了。

## 一、本章诉求

结合 DeepSeek 等同类型 OpenAI 产品，辅助完成 UI 与服务端接口的对接。

前端的 UI 已经明确，服务端的接口已经开发完成，他们中间没有什么额外的复杂逻辑了。只要告诉 OpenAI 调用接口，并明确出 UI 中位置的内容展示是来自于接口里的位置，那么就可以完成自动化的对接。

## 二、效果展示

<div align="center">
    <img src="https://bugstack.cn/images/article/project/group-buy-market/group-buy-market-3-2-06.png" width="850px">
</div>

- UI 与 服务端接口完整对接后，可以看到数据接口数据的使用，并渲染到前端页面。
- 在没有用户参与拼团的时候，首次展示引导用户开启拼团。