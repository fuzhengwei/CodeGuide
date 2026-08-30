---
title: 第2-5节：ReAct循环核心实现
pay: https://t.zsxq.com/qdlwY
---

# 《WaLiOffice - AI Agent 智能办公平台》第2-5节：ReAct循环核心实现

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

上一节我们把工具系统搭好了——工具 Trait、注册表、执行上下文。但工具还是死的，得有一个**"大脑"来指挥**工具什么时候用、怎么用。

这就是 **ReAct 循环**——Reasoning（推理）+ Acting（行动）。

**ReAct 是什么？** 就是让 LLM 反复做两件事：

1. **思考（Thought）**：看一下历史对话和工具结果，决定下一步要做什么
2. **行动（Action）**：调用工具，把结果告诉 LLM，继续思考

如此循环，直到任务完成。**你跟 WaLiOffice 说"帮我做个PPT"，它背后的思考过程可能是这样的**：

```
Thought: 用户想要一个季度汇报PPT，需要先规划大纲
Action: 调用 ppt_plan 工具
Observation: 大纲规划完成：1. 业绩概览 2. 业务分析 3. 下季度计划
Thought: 大纲有了，可以开始生成PPT了
Action: 调用 ppt_generate 工具
Observation: PPT 生成完成，共 12 页
Thought: 任务完成，给用户返回结果
```

这个"思考→行动→观察→再思考"的循环，就是 ReAct。**但你想过没有**：ReAct 循环不是简单 while(true)，有这些问题需要解决：

1. **循环终止条件**：什么时候停下来？最大轮数到了？LLM 说不需要工具了？
2. **上下文长度**：对话历史越来越长，LLM 的 context window 会爆
3. **产物收集**：多轮工具调用产生的文件，怎么汇总给前端？
4. **错误处理**：某个工具调用失败了，循环要不要继续？

这节我们就来解决这些问题。

## 一、本章诉求

1. 理解 ReAct 循环的原理——为什么"思考+行动"能实现 AI Agent
2. 掌握 `AgentEvent` 事件枚举设计——ReAct 循环怎么和外部通信
3. 理解 `compact_context` 上下文压缩——历史太长怎么办
4. 掌握 `run_agent_loop` 主循环实现——多轮工具调用的控制流
5. 理解 `OFFICE_AGENT_PROMPT` 系统提示词——怎么让 LLM 按规则出牌

## 二、流程设计

### 2.1 ReAct 循环控制流

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walioffice/walioffice-2-4-01.png" width="950px">
</div>

ReAct 循环的核心是一个 `for turn in 0..max_turns` 循环，每次循环：

```
① 组装消息历史（含压缩后的上下文）
        ↓
② 调用 LLM（携带工具定义）
        ↓
③ 检查 LLM 返回
  ├─ 有 tool_calls → ④ 执行工具 → ⑤ 记录结果 → 回到 ①
  └─ 无 tool_calls → ⑥ 返回文本回复 → 结束
```

超过 `max_turns`（默认 8 轮）时，用无工具的 LLM 调用生成总结，避免无限循环。
