---
title: 【更】第3-4节：根据AiAgent案例，设计库表
pay: https://t.zsxq.com/0tZhc
---

# 《Ai Agent》第3-4节：根据AiAgent案例，设计库表

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/Tx438](https://t.zsxq.com/Tx438)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

根据 Ai Agent 的代码案例，设计用于解耦，硬编码流程的库表。在后续的代码开发中，根据库表配置的 Ai Agent 流程所需的，模型、提示词、顾问、工具等，动态实例化出 Ai Agent 服务。

## 二、拆分设计

如图，为对应的Ai Agent 案例代码，映射出要拆分的库表设计；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-4-01.png" width="950px">
</div>

首先，整个代码构建的整个 Ai Agent 最小化单元服务，我们可以根据这样的服务信息设计出库表结构。

- 第一步，从上到下，OpenAiApi 是最基础单元结构，可以被多个 OpenAiChatModel 使用，它可以被拆分出第一张表。
- 第二步，构建 OpenAiChatModel，这个阶段，需要 openAiApi、model对话模型、tool mcp 工具。其中`model对话模型`时一种固定固定资源，可以直接放到 ai_client_model 模型中，而 openAiApi、mcp 工具，都属于复杂配置，则需要额外的外部关联来衔接。也就是后面的 ai_client_config 配置，用于配置衔接关系。
- 第三步，ChatClient 对话客户端，这部分的实例化过程都是和外部其他的资源关联，本身表设计只要有一个客户端的唯一id和客户端的描述介绍即可。
- 第四步，给 mcp 增加一个表，mcp 服务是非常重要的，有 mcp 才有 agent 服务。mcp 的启动有 stido、sse 两种方式，每种方式都有对应的配置文件 json 数据。
- 第五步，defaultSystem 系统提示词，需要单独拆分出来。提示词等于智能体的大脑，也有人说，其实 Ai Agent 就是 prompt 的堆叠，所以写提示词是很重要的。
- 第六步，advisor 顾问角色，在 Spring Ai 框架中，以顾问的方式，访问记忆上下文，知识库资源，所以这部分也要单独设计库表。
- 第七步，设计一个 ai_client_config，用于配置；api、model、client、prompt、mcp、advisor的衔接关系。
- 第八步，设计 ai_agent、ai_agent_flow_config，也就是一个 ai agent，是可以连续调用多个 ai client 客户端的。
- 第九步，设计 ai_agent_stask_schedule 任务，这是一种触达手段，可以把配置好的任务，让 task 定时执行，如自动发帖、系统异常巡检、舆情风险检测、系统配置变更、运营活动报表等。
- 第十步，ai_client_rag_order，是知识库表，用于上传知识库做一个记录，这样顾问角色就可以访问知识库内容了。

注意；chat_client 客户端的初始化过程中，也可以增加 mcp 服务，这部分在 chat_model 模型构建中，也可以增加 mcp，选择在 chat_model 增加即可。