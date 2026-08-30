---
title: 第2-4节：工具Trait定义与注册表机制
pay: https://t.zsxq.com/gh08J
---

# 《WaLiOffice - AI Agent 智能办公平台》第2-4节：工具Trait定义与注册表机制

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

上一节我们把数据库层搞定了——会话能存、历史能恢复。但现在整个系统还缺最核心的一块：**工具（Tools）**。

为什么需要工具？因为 LLM 自己只能"说话"，不能实际操作文件、生成 PPT、搜索网页。但 WaLiOffice 的目标是"你说什么，它生成什么"——光靠 LLM 的文字输出是做不到的，必须让 LLM **调用工具**，工具才是真正干活的人。

所以这一节，我们来设计 WaLiOffice 的**工具系统**——它包括：
1. **工具抽象（Trait）**：定义什么是"工具"
2. **工具上下文（Context）**：工具执行时的环境和能力
3. **工具注册表（Registry）**：全局工具管理和查询
4. **工具结果（Result）**：标准化的返回格式

**但你想过没有**：为什么要用 Trait 而不是直接写函数？——因为 Trait 提供了**统一接口**，不管工具是生成 PPT 还是搜索网页，调用方式都一样：`tool.call(input, ctx)`。这就像给所有工具装了一个"通用插座"，LLM 只需要知道"插座长什么样"，不需要知道每个工具的具体实现。

## 一、本章诉求

1. 理解 `OfficeTool` Trait 的设计哲学——为什么工具要抽象成 Trait
2. 掌握 `ToolContext` 执行上下文——工具能拿到什么信息，能做什么事
3. 学会 `ToolResult` 和 `ToolArtifact` 产物规范——工具如何返回标准化的结果
4. 理解 `ToolRegistry` 全局单例注册表——工具如何注册和查询
5. 掌握 `register_all_tools` 的启动流程

## 二、流程设计

### 2.1 工具系统架构

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walioffice/walioffice-2-4-01.png" width="950px">
</div>

WaLiOffice 工具系统的核心思路是：**把工具抽象成 Trait，具体工具实现 Trait，通过全局注册表统一管理**。

- LLM 决定调用哪个工具 → 从注册表查到工具实例 → 调用 `tool.call(input, ctx)` → 返回 `ToolResult`
- 工具的具体逻辑（生成 PPT、搜索网页）完全封装在实现类里，LLM 和 ReAct 循环不需要知道细节

### 2.2 工具类型一览

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walioffice/walioffice-2-4-02.png" width="950px">
</div>

WaLiOffice 注册了 10 个工具：

| 工具名 | 用途 | 产物类型 |
|--------|------|---------|
| `ppt_plan` | 规划 PPT 大纲 | document（JSON大纲） |
| `ppt_generate` | 生成完整 PPT | ppt |
| `doc_generate` | 生成 Word 文档 | document |
| `md_generate` | 生成 Markdown | markdown |
| `sheet_generate` | 生成 Excel 表格 | sheet |
| `chart_generate` | 生成 ECharts 图表 | chart |
| `drawio_generate` | 生成流程图 | drawio |
| `image_prompt` | 生成图片 | image |
| `video_generate` | 生成视频 | video |
| `web_search` | 联网搜索 | search |

> **补充说明**：`agnes_media.rs` 和 `local_video.rs` 虽然在 `tools/` 目录下声明了模块，但它们是**辅助模块**而非独立工具——`agnes_media` 提供 API Key 轮换和 HTTP 客户端能力（被 `image_prompt` 和 `video_generate` 引用），`local_video` 提供**本地视频降级**逻辑（被 `video_generate` 内部调用）。它们不实现 `OfficeTool` Trait，也不注册到 Registry。

