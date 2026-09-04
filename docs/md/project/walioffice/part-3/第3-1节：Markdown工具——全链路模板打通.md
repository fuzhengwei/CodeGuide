---
title: 第3-1节：Markdown工具——全链路模板打通
pay: https://t.zsxq.com/HLPPQ
---

# 《WaLiOffice - AI Agent 智能办公平台》第3-1节：Markdown工具——全链路模板打通

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

第二章我们把 WaLiOffice 的 Agent 骨架搭起来了——LLM 客户端、工具注册表、ReAct 循环、意图识别、SSE 推流、前端对话界面，全部打通。但那时候的 11 个办公工具全是 **stub（空壳）**：Agent 会调它们，调完只回一句"功能开发中"。

从这节开始，我们进入第三章——**把工具一个一个写实**。第一个要动手的就是 **Markdown 文档生成工具（md_generate）**。

为什么选它打头阵？因为它是所有工具里**结构最简单、链路最完整**的一个：不需要外部 API（不像 web_search）、不需要二进制渲染（不像 docx/pptx）、不需要多轮编排（不像 ppt_plan → ppt_generate）。就是纯粹的"**接收参数 → 构建 Prompt → 调 LLM → 解析 JSON → 封装产物**"。

但麻雀虽小五脏俱全——**Prompt 工程、场景推断、JSON 容错解析、降级草稿、产物结构化、前端渲染**，这些后面每个工具都要用到的套路，在这一节里全部会出现。把 md_generate 吃透，后面 9 个工具就是"换模板 + 加渲染"的事。

**但你想过没有**：用户说"帮我整理一份 AI Agent 技术调研文档"，LLM 直接生成的内容往往是一堆正确的废话。怎么让它生成"像人写的、能直接发布"的文档？答案藏在本节的两个设计里：**风格指引（style_guide）** 和 **场景推断（infer_markdown_scene）**。

## 一、本章诉求

1. **理解工具的输入/输出设计**：`parameters()` 返回的 JSON Schema 如何让 Agent 知道该传什么参数，`ToolResult` 如何把产物带回给前端
2. **掌握 Prompt 三段式工程**：system prompt（角色 + 输出格式约束）→ user prompt（风格指引 + 场景偏好 + 用户需求）→ LLM 输出（严格 JSON）
3. **实现场景自动推断**：`infer_markdown_scene` 根据 topic 关键词补足内容侧重点，让生成结果贴合真实办公场景
4. **处理 LLM 输出解析失败**：`extract_json` 两级容错（去 markdown fence → 截取花括号），失败时返回降级草稿而不是报错
5. **打通产物渲染链路**：`ToolArtifact { kind: "markdown" }` → SSE 推送 → 前端 `MarkdownArtifact` 组件渲染 → 下载 .md 文件

## 二、流程设计

### 2.1 md_generate 工具调用链路

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walioffice/walioffice-3-1-02.png" width="950px">
</div>

从用户发消息到 Markdown 文档出现在右侧面板，完整链路如下：

```
用户输入 "帮我整理一份 AI Agent 技术调研文档"
        ↓
POST /api/chat/stream（Chat 路由，第 2-7 节已实现）
        ↓
意图识别 → allowed_tools 包含 md_generate
        ↓
ReAct 循环：LLM 决策调用 md_generate(topic, style, audience)
        ↓
┌─────────────── md_generate.call() ───────────────┐
│ ① 参数提取与校验（topic 不能为空）                 │
│ ② 场景推断 infer_markdown_scene(topic)           │
│ ③ 状态推送 ctx.send("state_update", ...)         │
│ ④ 风格指引 style_guide 匹配                       │
│ ⑤ 构建 system_prompt + user_prompt               │
│ ⑥ LlmClient.chat() 调用 LLM                      │
│ ⑦ extract_json 容错解析 → MarkdownOutput         │
│ ⑧ 解析失败 → 降级草稿（fallback）                 │
│ ⑨ 封装 ToolArtifact { kind: "markdown" }         │
└───────────────────────────────────────────────────┘
        ↓
AgentEvent::Artifact → SSE artifact_update 事件
        ↓
产物落盘 .md 文件 + 持久化到会话
        ↓
前端 MarkdownArtifact 组件渲染（marked 风格预览 + 下载按钮）
```

