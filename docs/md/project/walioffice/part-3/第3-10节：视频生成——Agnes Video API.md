---
title: 第3-10节：视频生成——Agnes Video API
pay: https://t.zsxq.com/Cj0j0
---

# 《WaLiOffice - AI Agent 智能办公平台》第3-10节：视频生成——Agnes Video API

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

图片生成搞定了，现在升级到**视频生成**。视频比图片复杂一个量级，核心差异有三：

1. **耗时长**：图片 5-10 秒，视频 1-8 分钟——同步等死等不了，必须**两阶段异步**
2. **任务态管理**：提交任务拿 task_id → 轮询状态 → 超时兜底，是一条完整的**状态机链路**
3. **失败率高**：远程服务限流、排队、超时、生成失败……任何一个环节挂掉都要有退路

这节写 **video_generate** 工具的远程主链路；本节还会预留一个钩子——所有远程失败路径最终汇入**本地兜底**（3-11 节展开本地合成的实现细节）。

> **代码状态说明**：`ch03-10-agnes-media` 分支落地的是 `agnes_media.rs`（视频凭证与多 Key 基础设施，3-8 已从图像视角讲过）和 `local_video.rs`（本地合成库，3-11 展开）；该分支上 `video_generate.rs` 仍是 19 行 stub。`video_generate.rs` 的 630 行完整实现随 `ch03-11-video-generate` 分支落地（提交信息：feat(ch3-10,ch3-11)）。本节按完整实现讲解远程链路，阅读 ch03-10 分支代码时注意这个先后关系。

## 一、本章诉求

1. **理解两阶段异步模式**：POST 提交任务 → task_id/video_id → GET 轮询 → URL 返回，为什么视频必须这样设计
2. **掌握 tool_config 三级配置优先级**：会话级配置 > 工具调用参数 > 默认值
3. **掌握参数确定性控制**：LLM 规划的 aspect_ratio/duration 会被用户指定值**强制覆盖**
4. **理解请求体三分支**：关键帧模式（keyframes）、多图模式（extra_body.image 数组）、单图模式（image 单值）
5. **掌握轮询状态机**：480 秒 deadline、5 秒间隔、progress 实时推送、completed/failed/超时三分支出口
6. **理解全路径兜底设计**：6 个失败出口全部汇入 local_video_artifact，“能力可降级，服务不拒绝”

## 二、流程设计

### 2.1 视频生成全链路

<div align="center">
    <img src="https://bugstack.cn/images/article/project/walioffice/walioffice-3-10-01.png" width="950px">
</div>

```
用户："帮我生成一段产品介绍视频"（可选：上传参考图 + "让它动起来"）
    ↓
① call()：三级配置解析
   aspect_ratio：ctx.get_config("aspect_ratio") > input.aspect_ratio > "16:9"
   duration   ：ctx.get_config("duration")    > input.duration    > "standard"
   mode       ：ctx.get_config("mode")        > input.mode        > 按图片数推断
   image_inputs = collect_video_images()（参数/附件双通道，同 image_prompt）
    ↓
② ctx.send("state_update")：正在为《xx》生成视频提示词与镜头描述...
    ↓
③ 规划 LLM（六段式：主体+动作+场景+镜头运动+光线+风格）
   失败 → default_video_plan 兜底（不报错）
   plan.aspect_ratio / plan.duration ← 用户指定值强制覆盖
    ↓
④ (width,height) = infer_dimensions()，num_frames = infer_num_frames()，24fps
    ↓
⑤ resolve_video_credentials() → 失败？→ 本地兜底
   POST /v1/videos → CreateVideoResponse { task_id, video_id, status, progress }
   → 失败？→ 本地兜底
    ↓
⑥ 轮询循环（deadline 480s）：
   GET /agnesapi?video_id=xx&model_name=xx
   state_update("视频状态：processing（45%）")
   ├─ pending/processing → sleep(5s) → 继续轮询
   ├─ completed → url（或 remixed_from_video_id 兜底）→ ToolResult::ok
   ├─ failed/error/cancelled → 本地兜底
   └─ 超时 → 本地兜底（提示 task_id 可稍后重试）
    ↓
ToolArtifact { kind: "video", content: { video_url, task_id, video_id,
               seconds, size, aspect_ratio, generation_mode, provider, ... } }
    ↓
chat.rs 落盘 .mp4 → 前端 VideoArtifact 播放器 + 下载按钮
```

**👩🏻‍🏫敲黑板**：两阶段模式的本质是**把“生成”和“等待”解耦**——POST 提交后远程服务排队异步生成，客户端只持有 task_id 定期问进度。图片生成 10 秒可以挂起一个 HTTP 请求硬等，视频 8 分钟必须轮询（或回调/WebSocket，但轮询实现最简单、对服务端要求最低）。轮询间隔 5 秒是“进度感知”和“请求压力”的平衡：视频生成以分钟计，5 秒的粒度用户已经觉得是“实时进度”了。

