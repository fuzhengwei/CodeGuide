---
title: 第3-2节：Word工具与纯Rust DOCX渲染
pay: 
---

# 《WaLiOffice - AI Agent 智能办公平台》第3-2节：Word工具与纯Rust DOCX渲染

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

上一节我们用 `md_generate` 打通了第一个完整工具：LLM 直接生成 Markdown，前端拿到文本后即可渲染。但是 Markdown 更适合浏览器、知识库和代码仓库。真实办公场景里，用户还会提出另一类需求：**“帮我写一份正式报告，最后给我一个可以交付的 Word 文件。”**

这时不能简单地把 Markdown 文件改成 `.docx` 后缀。因为 DOCX 不是普通文本文件，它本质上是一个遵循 Office Open XML 规范的 ZIP 压缩包，内部包含正文 XML、样式、关系和文件元数据。LLM 可以生成文档内容，却不适合直接生成一个稳定可打开的二进制 Office 文件。

所以本节采用和 PPT 工具相同的核心分工：

> **LLM 负责内容与结构，Rust 负责格式与文件。**

LLM 输出 `title + sections + paragraphs + bullets + table` 这样的结构化 JSON；后端将结构校验后交给 `docx-rs` 渲染为 `.docx`。如果 DOCX 渲染失败，再使用同一份结构化数据生成 Markdown 降级文件。这样既能获得 LLM 的内容生成能力，又不会把最终文件格式交给模型碰运气。

**但这里有一个容易忽略的工程问题**：为什么不让 `doc_generate` 直接返回一个 Markdown 字符串，然后由前端导出 Word？因为前端只能可靠地展示内容，真正的文件生成、文件落盘、下载权限和历史产物管理仍然应该由后端完成。`doc_generate` 的产物会沿着 AgentEvent → SSE → `save_generated_artifact_to_files` 进入统一的文件链路。

> **代码状态说明**：`ch03-02-doc-generate` 这个提交完整实现了 `doc_generate` 工具和结构化产物输出；该分支中的 `server/src/render/docx_render.rs` 仍保留第二章的 stub 版本，文件内明确标注“完整实现在 3-2 节”。本文会同时讲清楚工具已落地的部分、渲染器的目标实现方式以及当前分支的实际运行结果，避免把设计稿误当成已经提交的代码。

## 一、本章诉求

1. **理解 DOCX 的文件本质**：认识 `.docx` 是 ZIP + XML 的 Office Open XML 容器，而不是可以直接拼接的文本
2. **设计结构化文档 Schema**：掌握 `DocOutput`、`DocSection`、`DocTable` 的字段设计，以及为什么每个可选字段都要有默认值
3. **实现 Word 工具入口**：完成 `OfficeTool` 的 `name`、`description`、`parameters` 和 `call` 方法
4. **掌握格式与内容分工**：让 LLM 生成结构化内容，让 `docx-rs` 控制标题、段落、列表、表格和文件打包
5. **实现双重降级策略**：LLM 调用失败返回错误；JSON 解析成功但 DOCX 渲染失败时，降级保存 Markdown
6. **理解产物统一链路**：`ToolArtifact { kind: "document" }` 如何被 Chat 路由保存为 `.docx` 或 `.md`，并被前端预览
7. **掌握文档导出接口**：理解 `/api/doc/export` 接收的请求结构和 `/api/files/download/:filename` 下载接口

## 二、流程设计

### 2.1 Word 文档生成整体链路

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walioffice/walioffice-3-2-01.png" width="950px">
</div>

完整调用链路如下：

```
用户输入：帮我生成一份电商 APP 用户积分体系设计 PRD
        ↓
POST /api/chat/stream
        ↓
IntentAnalyzer 识别为文档生成意图
        ↓
ReAct Loop 决策调用 doc_generate
        ↓
DocGenerateTool.call()
  ① 读取 topic / audience / format
  ② infer_doc_scene(topic) 推断内容场景
  ③ format_guide 匹配文档类型
  ④ system_prompt + user_prompt
        ↓
LlmClient.chat()
        ↓
extract_json → DocOutput
  title + sections + paragraphs + bullets + table
        ↓
ToolResult::ok
  ToolArtifact { kind: "document", content: structured }
        ↓
AgentEvent::Artifact → SSE artifact_update
        ↓
Chat 路由 save_generated_artifact_to_files()
        ↓
优先 render_docx() → .docx
        │
        └── 渲染失败 → sections_to_markdown() → .md
        ↓
前端 WordPreview 结构化预览 / 下载 DOCX 或 Markdown
```