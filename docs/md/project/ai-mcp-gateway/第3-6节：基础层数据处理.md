---
title: 【更】第3-6节：基础层数据处理(Dao)
pay: https://t.zsxq.com/LaFBT
---

# 《AI MCP Gateway 网关服务系统》第3-6节：基础层数据处理(Dao)

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/8xnuo](https://t.zsxq.com/8xnuo)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

将 AI MCP Gateway 库表设计，编写到工程中，映射成 PO、DAO、Mapper 文件，以便于后续章节的使用。

这一节，小傅哥会演示如何使用 AI IDE 工具，通过 Prompt 描述，来完成这些文件的生成。

## 二、结构介绍

如图，从领域层到基础设施层（mysql、redis...）的使用方式；

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-mcp-gateway/ai-mcp-gateway-3-6-01.png" width="750px"/>
</div>

- 首先，这里有个设计的思想的体现，这类思想也是 Spring、MyBatis 等框架源码中常用的思想，叫做【依赖倒置】。它的设计目标是，让数据使用方，不过渡依赖于提供方。提供方在`升级`、`替换`、`迭代`时候，都不影响使用方。
- 之后，图里是依赖倒置的具体编码体现，从 domain 领域层，每个会话、鉴权、协议的功能编写时，所需的数据，是通过在 domain 领域层定义接口，之后由infrastructure 基础设施层做具体的功能实现。也就是说，每个功能区需要啥数据，就定义好接口，确定好入参和返回结果的，基础设施层引入 domain 层定义的这个标准接口，做具体的数据封装使用。**也类似于公司的领导，要这，要那，他不关心具体是谁做，最后做好就可以了。**
