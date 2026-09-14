---
title: 第3-5节：ECharts图表工具与前端渲染
pay: 
---

# 《WaLiOffice - AI Agent 智能办公平台》第3-5节：ECharts图表工具与前端渲染

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

大家好，我是技术UP主小傅哥。

前面几节的工具都是“文件下载”类——Markdown、Word、Excel，最终产物是一个可以下载的文件。但办公对话里还有一类高频诉求：用户说“帮我对比一下各渠道转化率”，他不需要下载文件，只想**在对话中立刻看到一张图**。

这节我们来写全项目最轻量的工具 **chart_generate**：后端 137 行，不渲染任何文件，只产出一份 ECharts 数据 JSON；前端拿到 `kind: "chart"` 产物后直接在右侧面板渲染。生成 Word/PPT 要等十几秒，图表 3 秒内就能出现在对话里——它是 Agent 回答“带点数据感”的最佳点缀。

**它也带来一种新的分工模式**：前面几节是“LLM 生成内容、Rust 渲染格式”，本节是“**LLM 生成数据、前端渲染图形**”——后端只做参数校验和产物封装，连格式渲染都省了。这个模式的适用边界（什么时候能省掉渲染层）正是本节要讲的。

## 一、本章诉求

1. **理解轻量产物模式**：`produces_artifact = true` 但不落盘文件，`kind: "chart"` 产物是纯配置数据
2. **掌握图表类型选择策略**：为什么类型选择交给 LLM（Prompt 约束 + 参数透传），而不是后端写规则函数
3. **掌握 ChartOutput 契约**：`labels/values 长度一致`、`values 纯数字不带单位` 这两条约束如何守住前后端边界
4. **实现前端 EChartsView**：类型白名单归一化、数值清洗（去 %/逗号）、pie/gauge 特化配置、兜底数据
5. **掌握渲染资源管理**：ResizeObserver 响应式、`dispose()` 防内存泄漏、option 变更时 `setOption(option, true)` 全量替换
6. **理解 ChartArtifact 包装层的容错**：`chart_type || type`、`chart_data || data || content` 多级兜底，LLM 输出什么形态都能渲染

## 二、流程设计

### 2.1 图表生成链路（轻量模式）

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walioffice/walioffice-3-5-01.png" width="950px">
</div>

```
用户需求（topic，可选 chart_type）
    ↓
① call() 校验：topic 非空、preferred_type 默认 "bar"
        ↓
② ctx.send("state_update")：正在生成《{topic}》图表...
        ↓
③ LLM 调用（system：类型枚举 + 数据约束 / user：需求 + 偏好类型）
        ↓
④ extract_json 解析 → 反序列化 ChartOutput
   { title, summary?, chart_type, labels[], values: Vec<f64>, series_name? }
        ↓
⑤ 封装 ToolArtifact { kind: "chart" }
   content = { type, chart_type, title, summary,
               chart_data: { labels, values, seriesName } }
        ↓
⑥ observation：已生成《xx》bar图表，可在右侧直接查看。
        ↓
前端 ChartArtifact 包装 → EChartsView 渲染
   ├─ normalizeChartType 白名单归一化（未知类型兜底 bar）
   ├─ buildOption 按 pie/gauge/通用 三分支构建配置
   └─ echarts.init → setOption → ResizeObserver 自适应 → dispose 清理
```

**👩🏻‍🏫敲黑板**：注意这个工具**没有任何文件落盘**。`save_generated_artifact_to_files` 里没有 `chart` 分支——产物只进入前端 artifact 状态树随会话持久化，不产生磁盘文件。“产物”这个词在项目里有两个含义：**文件产物**（docx/xlsx，落盘）和**数据产物**（search/chart，进前端渲染）。设计新工具时先想清楚属于哪类。

