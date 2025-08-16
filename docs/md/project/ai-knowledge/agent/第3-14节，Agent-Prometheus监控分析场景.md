---
title: 【更】第3-14节，Agent-Prometheus监控分析场景
pay: https://t.zsxq.com/8BfkE
---

# 《Ai Agent》第3-14节，Agent-Prometheus监控分析场景

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/ymfSm](https://t.zsxq.com/ymfSm)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

上一节我们做了 Ai Agent ELK，这一节扩展增加 Prometheus（普罗米修斯监控） 监控系统，让 Ai Agent 具备智能监控问题分析场景。这种场景东西是在公司里非常重要的，且有实际使用用途的东西。

本节基于的是 Ai MCP Prometheus + Agent Prompt（分阶段提示词），来完成自动化分析、规划、执行、检测、输出的智能监控系统。

## 二、功能流程

如图，Agent-Prometheus 的设计使用流程图；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-14-01.png" width="650px">
</div>

- 首先，虚线框为模拟的系统监控日志脚本（含运行程序），部署的一套普罗米修斯监控系统。你可以通过这套教程来部署一套普罗米修斯监控 [https://bugstack.cn/md/road-map/grafana.html](https://bugstack.cn/md/road-map/grafana.html) 星球的拼团、大营销、openai应用都有这样的监控系统使用。
- 之后，要为这套场景增加一套新的 Ai Agent 执行话术，另外还要配置一套对应的 RAG 知识库，来增强分析能力。
