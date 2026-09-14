---
title: 第3-6节：react-drawio嵌入式编辑器
pay: https://t.zsxq.com/a2D3G
---

# 《WaLiOffice - AI Agent 智能办公平台》第3-6节：react-drawio嵌入式编辑器

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

上一节的 ECharts 解决了“数据可视化”，但办公里还有一类图 ECharts 画不了：**流程图、架构图、泳道图、ER 图**。这类图的共同特点是——节点 + 连线的自由布局，而且用户拿到手往往还想**继续改**（加个节点、调个颜色、改句文案）。

这节的 `drawio_generate` 工具给出了三个有意思的设计：

1. **LLM 直接生成 XML 而不是 JSON**——这是全项目唯一一个让模型输出 XML 的工具，解析策略随之改变
2. **渲染交给 embed.diagrams.net**——不在前端自己写画布引擎，用 iframe 嵌入 draw.io 官方编辑器，通过 postMessage 双向通信
3. **预览/编辑双模式**——预览态轻量展示，编辑态加载完整编辑器 UI，用户改完自动回写到 artifact

另外它还创造了一个“之最”：**产物落盘全项目最简单**——drawio 产物落盘就是直接把 XML 文本存成文件，不需要任何渲染器（对比上一节 Excel 的 stub 渲染，这里是真实生效的）。

## 一、本章诉求

1. **理解 XML 生成的 Prompt 设计**：mxGraphModel 结构示例、六类图表的结构引导、布局质量要求
2. **掌握 XML 解析策略**：为什么不用 `extract_json`，fence 清理 + 包含性校验为什么够用
3. **掌握 react-drawio 集成原理**：iframe + postMessage 双向协议（init/load/configure/action）、URL 参数体系
4. **实现 preview/edit 双模式**：不同模式下 URL 参数、configuration、事件回调的切换
5. **理解编辑回写链路**：autosave/save/export 三个事件如何把用户编辑写回 artifact
6. **理解产物落盘与导出**：chat.rs 存 XML 文本（真实生效）、前端纯浏览器 Blob 下载

## 二、流程设计

### 2.1 drawio_generate 全链路

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walioffice/walioffice-3-6-01.png" width="950px">
</div>

```
用户需求（topic，可选 diagram_type）
    ↓
① call()：scene_guide = infer_diagram_scene(topic)，校验 topic 非空
        ↓
② ctx.send("state_update")：正在生成《{topic}》{diagram_type}...
        ↓
③ LLM 调用（system：mxGraphModel 格式示例 + 六类图结构引导
            / user：diagram_type + 场景偏好 + 需求）
        ↓
④ XML 清洗：去 ```xml / ``` 围栏 → trim
   校验：必须含 <mxGraphModel 或 <mxfile，否则报错
        ↓
⑤ ToolArtifact { kind: "drawio" }
   content = { type: "drawio", title, diagram_type, xml }
        ↓
⑥ 双链路分流：
   ├─ chat.rs 自动落盘：xml.as_bytes() → save_file_bytes（.drawio 文本文件，真实生效）
   └─ 前端 DrawIoArtifact → DrawIoEmbed（iframe 嵌入 embed.diagrams.net）
        ↓
用户点"编辑" → 加载完整编辑器 UI（ kennedy 主题 + 图形库 + 默认样式）
用户编辑 → autosave 事件 → onUpdate 回写 artifact.content.xml
用户点"保存" → exportDiagram(xmlsvg) → onExport 回写 preview + onSave 回写 xml
用户点"导出"（面板按钮）→ 前端 Blob 下载 .drawio 文件（纯浏览器侧，不经后端）
```
