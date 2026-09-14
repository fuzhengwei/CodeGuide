---
title: 第3-7节：PPT 双工具协作（plan + generate）
pay: https://t.zsxq.com/2p8bT
---

# 《WaLiOffice - AI Agent 智能办公平台》第3-7节：PPT 双工具协作（plan + generate）

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

大家好，我是技术UP主小傅哥。

前面我们写的工具都是“一步到位”——调用一次，拿到一个产物。但 PPT 有个特殊性：**内容规划**和**视觉渲染**是两种性质完全不同的工作。规划需要 LLM 发挥创意（叙事结构、页数分配、要点提炼），渲染需要精确的坐标和样式控制（每个元素在 13.33 × 7.5 英寸画布上的位置）。

让 LLM 一次干两件事，要么规划被渲染细节拖累（输出一堆坐标却讲不好故事），要么渲染失控（自然语言描述不出精确版式）。所以 PPT 是全项目唯一采用**双工具协作**的功能：

> **ppt_plan**（规划）：LLM 生成结构化大纲 → 写入 scratchpad，只出规划不出页面
> **↓**
> **ppt_generate**（生成）：读取 scratchpad 大纲 → Rust 代码精确渲染每一页

> **代码状态说明**：`ch03-06-ppt-generate` 分支上 `ppt_generate.rs` 已完整实现（685 行），但 `ppt_plan.rs` 在该分支仍是 stub（文件头注释“完整实现在 3-5 节”）——ppt_plan 的完整实现（213 行）随主分支代码呈现，本节按完整实现讲解。阅读分支代码时注意这个先后关系。

## 一、本章诉求

1. **理解双工具协作模式**：为什么 PPT 拆两个工具、scratchpad 跨工具数据共享的机制
2. **掌握 ppt_plan 的 Prompt 设计**：大纲 JSON 格式（layout 枚举、goal/visual/points 字段）、叙事链要求、受众注入
3. **掌握 ppt_generate 的渲染逻辑**：SlidePlan → Slide 的三布局分支、SlideElement 坐标体系、主题色 Palette 轮换
4. **理解三级 plan 来源**：scratchpad 优先 → LLM 实时生成 → fallback_plan 硬编码兜底
5. **掌握逐页推送机制**：project_update 起始帧、每页 slide_update + history 追加、最终 ppt artifact
6. **理解 PptProject 持久化**：结构定义、每页循环保存、chat.rs 的产物恢复

## 二、流程设计

### 2.1 双工具协作链路

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walioffice/walioffice-3-7-01.png" width="950px">
</div>

```
用户："帮我做一份电商系统技术方案 PPT"
    ↓
ReAct 循环：LLM 决策先调 ppt_plan(topic, audience?)
    ↓
ppt_plan：LLM 生成大纲 JSON { title, slides[{title,layout,goal,visual,points}] }
    ↓
写入 ctx.scratchpad["ppt_plan"] → ToolResult::ok(...).with_data(plan)
   （is_read_only=true、produces_artifact=false，对话中不出现产物卡片）
    ↓
Agent 汇报"已规划 N 页大纲" → LLM 继续决策调 ppt_generate(title, theme?)
    ↓
ppt_generate：三级 plan 来源
   ① scratchpad 有 → 反序列化 PresentationPlan（失败→fallback）
   ② scratchpad 无 → generate_plan_with_llm 实时生成（失败→fallback）
   ③ fallback_plan：3 页硬编码兜底
    ↓
创建 PptProject → project_update（空 slides 起始帧）
    ↓
for (i, sp) in plan.slides：
   plan_to_slide() 三布局渲染 → push 进 project
   save_ppt_project() 逐页落库
   ctx.send("slide_update") 前端实时看到页面逐张出现
    ↓
ToolArtifact { kind: "ppt", content: { project_id, slides, history, ... } }
```