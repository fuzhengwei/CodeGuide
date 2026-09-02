---
title: 第2-6节：System Prompt工程与意图识别
pay: https://t.zsxq.com/TLIQB
---

# 《WaLiOffice - AI Agent 智能办公平台》第2-6节：System Prompt工程与意图识别

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

上一节我们把 ReAct 循环跑通了——LLM 能思考、能调用工具、产物能预览。但现在有个问题：**LLM 不知道用户想要什么类型的产物**。

用户说"帮我做个汇报"——是 PPT 汇报？Word 报告？还是 Excel 数据汇报？
用户说"先帮我写提示词"——是文本意图，不能直接跳到图片/视频生成。
用户说"先做PPT，再生成流程图"——这是时序意图，要分步执行，不能一起做。

这些问题靠 ReAct 循环本身是解决不了的，因为 **LLM 在决策工具之前，需要先知道"用户的真实意图是什么"**。这就是**意图识别（Intent Analysis）**的价值所在。

**但你想过没有**：为什么不能直接让 LLM 自己判断意图？让 ReAct 循环里的 LLM 自己决定用哪个工具不就够了吗？

还真不够，原因有三：
1. **速度**：意图识别是纯规则，计算毫秒级；LLM 判断需要一次 API 调用，耗时数百毫秒到数秒
2. **成本**：意图识别不消耗 LLM token，节省 API 调用费用
3. **精确性**：有些意图靠关键词就能精准判断（比如"帮我做个PPT"→PPT），不需要 LLM 推理

所以 WaLiOffice 采用了**规则引擎优先、LLM 兜底**的策略。

## 一、本章诉求

1. 理解 14 种意图类型（IntentType）及其应用场景
2. 掌握基于关键词权重的规则分类器设计
3. 学会时序意图检测（"先做X，再做Y"）
4. 理解指代消解（上下文追踪）
5. 掌握 `build_intent_context_addition` 系统提示词注入策略

## 二、流程设计

### 2.1 意图识别流程

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walioffice/walioffice-2-6-01.png" width="950px">
</div>

意图识别在用户发消息后、ReAct 循环启动前执行，流程如下：

```
用户消息输入
        ↓
① 时序意图检测（先做X，再做Y？）
        ↓ 命中 → 返回 Compound 意图 + 子意图
② 文本优先检测（先写/先构思？）
        ↓ 命中 → TextGenerate 或具体子意图
③ 指代消解（这个/那个/继续？）
        ↓ 替换为实际内容
④ 关键词规则分类（权重匹配）
        ↓
⑤ 降级兜底（fallback）
        ↓
返回 IntentResult（含置信度 + 工具建议）
```

### 2.2 14 种意图类型

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walioffice/walioffice-2-6-02.png" width="950px">
</div>

| 意图类型 | 触发关键词示例 | 对应工具 | 备注 |
|---------|--------------|---------|------|
| `Ppt` | ppt、演示文稿、幻灯片 | ppt_plan → ppt_generate | 必须两步 |
| `Doc` | word文档、报告、PRD、方案书 | doc_generate | |
| `Markdown` | markdown、readme、知识库、会议纪要 | md_generate | |
| `Sheet` | excel、表格、数据分析、排期 | sheet_generate | |
| `Chart` | 图表、可视化、柱状图、折线图 | chart_generate | 非PPT/Excel场景 |
| `Drawio` | draw.io、流程图、架构图、UML | drawio_generate | |
| `Image` | 生成图片、海报、封面、logo | image_prompt | |
| `Video` | 生成视频、短视频、动画片 | video_generate | |
| `WebSearch` | 最新、官网、搜索一下 | web_search | |
| `TextGenerate` | 写提示词、写脚本、构思方案 | md_generate | 文本优先 |
| `ImageUnderstanding` | 这是什么、识别、OCR | 无工具 | 直接识图回答 |
| `Chat` | 日常对话、问答 | 无工具 | 纯文本回复 |
| `Compound` | 先做X再Y、先A然后B | 多个工具 | 时序分步 |
| `Unknown` | 未能识别 | 兜底处理 | |
