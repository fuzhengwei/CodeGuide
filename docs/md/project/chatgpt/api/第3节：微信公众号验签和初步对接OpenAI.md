---
title: 第3节：微信公众号验签和初步对接OpenAI
pay: https://t.zsxq.com/0dJzAYXkG
---

# 《ChatGPT 微服务应用体系构建》 - chatgpt-api 第3节：微信公众号验签和初步对接OpenAI

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★★★☆☆
- **本章重点**：微信公众号SDK对接，并通过异步调用的方式处理消息应答。完成开发后使用内网穿透工具做本地的测试验证。
- **课程视频**：[https://t.zsxq.com/0dRFsWIoD](https://t.zsxq.com/0dRFsWIoD)

## 一、本章诉求

以实际应用为目标，初步尝试把OpenAI对接到公众号上，实现消息自动回复功能。随着 OpenAI 生成式服务的日益完善，以后各个大厂都会有自己品牌的 GPT 服务，并把这些服务对接到你现在通过问答方式回复的场景中。虽然我们本章来引入微信公众号开发的 SDK 和 chatgpt-sdk-java   到 chatgpt-api 做一个简单的对接使用。

**注意**：ChatGPT 对接微信公众号，可能会受到一些限制。但目前我们主要以教学为目的，如果以后腾讯自己的 GPT 产品问世后，那么会有更好的体验。所以现在是先学技能！

## 二、流程设计

整个流程为；对接微信公众号，提供验签服务的get请求和处理消息的post请求以及初始化 chatgpt-sdk-java 服务。当验签完成后接收post请求并做应答处理。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-api-03-01.png?raw=true" width="750px">
</div>

在本章节的学习，你需要准备一些基础内容；
- 微信订阅号申请：[https://mp.weixin.qq.com/cgi-bin/registermidpage?action=index&lang=zh_CN](https://mp.weixin.qq.com/cgi-bin/registermidpage?action=index&lang=zh_CN) - 个人即可申请，也支持基本的开发对接。
- 内网穿透使用：[https://natapp.cn/](https://natapp.cn/tunnel/buy) - 你可以使用免费的隧道，也可以购买最低配置，更稳定。