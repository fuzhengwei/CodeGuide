---
title: 第3-8节：图像生成——API调用与多Key轮询
pay: https://t.zsxq.com/MZvTI
---

# 《WaLiOffice - AI Agent 智能办公平台》第3-8节：图像生成——API调用与多Key轮询

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

前面的工具都是“文字内容生成”——Markdown、Word、Excel、图表，LLM 直接生成就行。**图片**完全不同：LLM 写字很强，画图不行，必须调用专业的图像生成模型 API。从这节开始连续三节做图像生成：本节讲**文生图的完整链路 + 多 Key 轮询基础设施**，3-9 讲图生图与附件理解。

`image_prompt` 工具的链路是：

> **用户需求 → LLM 规划 3 套风格提示词 → 逐张调用 Agnes Image API → 多图返回前端画廊**

难点有三个：**提示词工程**（LLM 产出的不是图片而是“提示词”，提示词质量决定图片质量）、**多 Key 轮询**（图片 API 限流严，单 Key 容易触发 QPS 上限）、**失败兜底**（一次生成几十秒，全挂了怎么给用户交代）。

另外一个工程决策要先讲清楚：**3 张图是串行生成的，不是并发**。每张图 10-60 秒，串行 3 张要等 1-3 分钟——所以每张图生成前后都推 `state_update`（“正在生成第 2 / 3 张图片（赛博科技）...”），用实时进度换等待焦虑。为什么不并发？3 个并发请求打同一个 Key 池，限流风险翻三倍，且失败重试逻辑会复杂一倍。**先跑通，再优化**——并发化留给读者作业。

## 一、本章诉求

1. **掌握图像提示词工程**：文生图六段式结构（主体+场景+风格+光照+构图+质量）、图生图四段式（修改+新风格+增删+保留）
2. **理解 AgnesCredentials 多 Key 基础设施**：Key 列表解析（逗号分隔）、round-robin 游标、`ordered_keys` 全量有序返回
3. **掌握两层重试机制**：外层 `generate_agnes_image` 两次尝试（间隔 2s）+ 内层 `post_json_url` 按序换 Key（401/403/429/5xx 触发）
4. **实现输出规格推断**：根据 topic 关键词推断 size 和 ratio（海报 2:3、头像素材 1:1、默认 3:2）
5. **掌握兜底链**：3 套提示词全失败 → 简化提示词重试 → 仍失败才报错
6. **理解产物结构**：`kind: "image"` 的 images/variants 双字段设计与前端画廊渲染

## 二、流程设计

### 2.1 文生图完整链路

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walioffice/walioffice-3-8-01.png" width="950px">
</div>

```
用户："帮我生成一张科技感产品海报"
    ↓
① call()：topic 校验、styles 提取
   scene_guide = infer_scene_guide(topic)          // 商业/科技/活动/默认
   output_spec = infer_image_output_spec(topic)    // 海报→2:3，头像→1:1，默认→3:2
   image_inputs = collect_image_inputs()           // 无参考图 → 文生图
        ↓
② ctx.send("state_update")：正在为《xx》生成高质量出图提示词...
        ↓
③ 规划 LLM：生成 ImagePromptPlan
   { title, description, prompts: [{style, prompt} × 3] }
        ↓
④ resolve_image_credentials() → AgnesCredentials { base_url, api_keys[] }
   endpoint = base_url + "/v1/images/generations"
        ↓
⑤ for (index, variant) in variants（串行，每张）：
   state_update("正在生成第 N / 3 张图片（风格）...")
   request_body = { model, prompt, size: "1K", ratio, extra_body: { response_format: "url" } }
   generate_agnes_image()
      └─ post_json_url()：ordered_keys 轮询 Key → 401/403/429/5xx 换下一个 Key
      └─ 失败 → sleep(2s) → 再来一轮（外层 2 次尝试）
   成功：url 或 b64_json → generated_images/variants
   失败：failed_variants 记录 → continue
        ↓
⑥ 全失败兜底：简化提示词（"Create a clear, high quality image..."）再试一次
        ↓
⑦ ToolArtifact { kind: "image" }
   content = { type, title, description, prompt, image_size, image_ratio,
               generation_mode, reference_image_count, images[], variants[], provider, model }
        ↓
chat.rs 落盘首图 → 前端 ImageArtifact 画廊渲染
```
