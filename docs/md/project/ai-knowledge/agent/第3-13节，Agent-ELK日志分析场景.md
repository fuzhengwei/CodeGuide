---
title: 【更】第3-13节，Agent-ELK日志分析场景
pay: https://t.zsxq.com/agZ9E
---

# 《Ai Agent》第3-13节，Agent-ELK日志分析场景

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/kMjAw](https://t.zsxq.com/kMjAw)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

增加 Agent-ELK 日志分析的实际应用场景，通过 Agent 根据用户诉求，自主分析、规划、执行和输出结果，来帮助我们对日志检索的提效。

ELK（或自研） 是各个互联网公司中都有的一套分布式日志设备，以便于研发在遇到线上系统报警和运营反馈事故问题时，快速检索日志。但往往这种检索的日志的方式都是非常耗时的，所以增加 Agent 方式来辅助提效是非常有必要的。

注意；面试往往就是需要这样的实际应用场景，而不是坦克大战、贪吃蛇、图书管理系统等一些不着边际的项目（适合练手但不适合写简历）。

## 二、功能流程

如图，Agent-ELK 的设计使用流程图；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-3-13-01.png" width="650px">
</div>

- 首先，虚线框内为模拟的系统的应用日志，部署一套 ELK 之后通过脚本把日志数据写入到 ELK。你也可以通过这套教程实际部署一套 ELK [https://bugstack.cn/md/road-map/elk.html](https://bugstack.cn/md/road-map/elk.html) 另外像星球项目，拼团、大营销等也都有 ELK 分布式日志的对接使用。
- 之后，要为这套场景增加一套新的 Ai Agent 描述话术，在执行  ELK 日志分析的时候，我们先手动选择出要使用的 Ai Agent 服务。这样它就可以以 ELK 对应的 Prompt 话术分析方式使用了。