---
title: 【更】第3-19节：管理端-API与UI对接
pay: https://t.zsxq.com/199zh
---

# 《AI MCP Gateway 网关服务系统》第3-19节：管理端-API与UI对接

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/JoVrf](https://t.zsxq.com/JoVrf)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

## 一、本章诉求

结合网关管理后台（UI）功能，在服务端提供相应的接口能力，与管理后台对接。这些功能主要是 CRUD 做页面管理的增删改查操作。

此部分功能使用 AI IDE 工具就可以完成，也很建议学习的伙伴使用 AI IDE 工具（opencode、trae.ai、openclaw（QClaw）），做一套属于自己的管理后台，这样你可以在投递简历的时候发送，也可以在面试的时候演示。

>提示，可以在你的 AI IDE 安装技能 [https://github.com/fuzhengwei/xfg-ddd-skills](https://github.com/fuzhengwei/xfg-ddd-skills) 这样处理起来更加准确。

## 二、流程设计

如图，管理端到服务端接口的设计；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-19-01.png" width="700px"/>
</div>

- 整个这部分的设计实现都是 CRUD，管理端需要什么，就可以配置什么接口，查询、修改、删除即可。

