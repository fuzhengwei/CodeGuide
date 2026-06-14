# 自建「中转站」 + 2 套 skills，让智能体自己找“粮”（token）吃！又能生成视频！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

一会这个厂家免费送Token，一会那个厂家免费到月底，再整上一堆的个人newapi。好家伙，本地的 `OpenClaw`、`WorkBuddy`、`Claude Code`、`Codex`、`WaliCode`...，哪个不是配置了一堆的 LLM 大模型地址（`✋🏻手都配麻了`）！主要还不稳，一会这个能用，一会哪个不能用。搞滴，兄弟们，群里天天喊：**“说啊！，哪里啊！”**

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-01.png" width="150px"/>
</div>
**Agnes LLM 你快点来，我等你！**

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-20.png" width="150px"/>
</div>

两周还没到，买的一堆 coding plan、token plan 也都吸干净了。 好在发现了 `Agnes AI`，它把核心全模态模型 API 免费开放！编码、画图、做视频都没问题；

- 文本模型：`Agnes-2.0-Flash`
- 图片模型：`Agnes-Image-2.1-Flash`
- 视频模型：`Agnes-Video-2.0`

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-21.png" width="550px"/>
</div>

经过一周的使用体验，确实还挺稳的。当然众所周知，LLM 这东西用的最多的还是程序员，所以能写代码，能把代码写好很重要。在这些天高强度的编码体验后，确实还挺让惊喜的。已经在 `OpenClaw`、`WaliCode`、`Codex`、`Claude Code（CCSwitch）`全都安排上了！

**但**，如果你想吃的更香，可以这么干！

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-22.png" width="200px"/>
</div>

`我提一个方案，大家来研究。`

我们免费的 LLM 很多呀，给的 Token 也很足。写代码、画图、做视频，都囊括了。那这，如果我做个 Skills，**不**！做2个Skills。一个负责统一的管理 LLM API 以及动态的分发 APIKey，另外一个负责画图和生视频，减去中间过程复杂操作，让 Skills 搞定画图和视频生成中的地址处理，提示词完善。那这样一来，我不成了免费大模型的东家了！

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-29.png" width="650px"/>
</div>

这2套skills，一套负责自动化配置、检查渠道，自动化创建 ApiKey。另外一套负责图片和视频自动化的生产。简直不要太舒服！如果你有一个小组/团队/公司，那大家可就都享福了！接下来，小傅哥就告诉你怎么安装使用。

> 接下来，小傅哥就带着你使用 xfg-skills-free-token-free +  xfg-skills-agnes + [agnes](https://agnes-ai.com/doc/quick-start)（免费1M全模态上下文模型），干起来！

## 一、免费模型（体验效果）

咱们干东西，上来先搞一下 API 看看最本质的东西咋样。

#### 1. 写代码

```java
curl https://apihub.agnes-ai.com/v1/chat/completions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer sk-3f9NQmveWbnL2Pxj4OAdSydKudUUeryh2q498jRuPnNWMJdQ" \
  -d '{
  "model": "agnes-2.0-flash",
  "messages": [{"role": "user", "content": "1+1"}]
}'
```

- 你可以自己申请 ApiKey 替换后进行测试。这部分重点在后面体现模型编码使用

#### 2. 文生图

```java
curl https://apihub.agnes-ai.com/v1/images/generations \
  -H "Authorization: Bearer sk-3f9NQmveWbnL2Pxj4OAdSydKudUUeryh2q498jRuPnNWMJdQ" \
  -H "Content-Type: application/json" \
  -d '{
    "model": "agnes-image-2.1-flash",
    "prompt": "绝美东方年轻女生，22 岁，鹅蛋脸，精致淡柳叶眉，下垂无辜小鹿眼，浅琥珀色瞳孔，长睫毛，自然淡卧蚕，小巧挺翘鼻梁，薄唇淡豆沙色唇釉，冷白皮，柔和原生骨相，微卷黑长直发披在肩头；宽松米白色针织吊带，细锁骨，肩颈线条流畅；窗边柔光侧逆光，薄纱窗帘透光，发丝发光，浅景深虚化背景，室内原木极简卧室，温柔氛围感，电影级光影，8K 超清，超写实人像，皮肤纹理细腻真实，单反 50mm f1.4 拍摄，高清五官特写，画质锐利，色彩柔和低饱和",
    "size": "1024x768",
    "extra_body": {
      "response_format": "url"
    }
  }'
```

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-23.png" width="600px"/>
</div>

- 提示词1 `日系氧气少女，元气笑脸，浅浅梨涡，短发内扣，浅栗色头发，日系淡妆，白衬衫百褶短裙，夏日街角街边，阳光树荫光斑，胶片质感，颗粒轻微，日系小清新色调，半身人像，自然动态，松弛姿态，高清写实，肤色通透`
- 提示词2 `90 年代香港复古御姐，大波浪复古卷发，红唇明艳，细长猫眼眼线，修身丝绒红裙，复古胶片闪光灯光影，昏暗酒吧背景，颗粒胶片质感，冷艳气场，眼神疏离，轮廓立体，高级人像摄影，4K 超清，复古色调浓郁`
- 提示词3 `绝美东方年轻女生，22 岁，鹅蛋脸，精致淡柳叶眉，下垂无辜小鹿眼，浅琥珀色瞳孔，长睫毛，自然淡卧蚕，小巧挺翘鼻梁，薄唇淡豆沙色唇釉，冷白皮，柔和原生骨相，微卷黑长直发披在肩头；宽松米白色针织吊带，细锁骨，肩颈线条流畅；窗边柔光侧逆光，薄纱窗帘透光，发丝发光，浅景深虚化背景，室内原木极简卧室，温柔氛围感，电影级光影，8K 超清，超写实人像，皮肤纹理细腻真实，单反 50mm f1.4 拍摄，高清五官特写，画质锐利，色彩柔和低饱和`
- 提示词4 `古典江南古装美人，温婉雅致，丹凤眼，桃花眼，樱桃小口，珍珠耳坠，水墨眉妆，乌黑盘发配玉簪流苏，浅青色齐胸襦裙，刺绣海棠花纹，站在江南园林荷花池边，湖面薄雾，垂柳摇曳，古风烟雨柔光，工笔质感 + 写实结合，仙气飘逸，发丝随风飘动，衣袂褶皱细腻，8K，电影光影，细节拉满，国风配色雅致`

#### 3. 生视频

```java
curl -X POST https://apihub.agnes-ai.com/v1/videos \
  -H "Authorization: Bearer sk-3f9NQmveWbnL2Pxj4OAdSydKudUUeryh2q498jRuPnNWMJdQ" \
  -H "Content-Type: application/json" \
  -d '{
    "model": "agnes-video-v2.0",
    "prompt": "整体剧情：外卖员接单遭遇地址混乱暴怒质问，另一方骑手暗中邀约碰面，幕后西装大佬准备抛出交易方案，三段镜头无缝转场，港片黑帮暗线外卖题材短剧。完整提示词：长剧情连贯港风犯罪短剧，三段镜头无缝转场，24 帧 4K 胶片电影质感，统一港片色调。第一段：楼道里甄子丹外卖骑手暴怒打电话嘶吼追问配送地址，镜头轻微晃动；硬切转场第二段：暗巷里邹兆龙红帽骑手接起电话，低声邀约对方前来碰面，暗光悬疑氛围；淡入转场第三段：红砖房间内西装大佬梁家辉端坐，从容准备抛出交易方案，大佬压迫气场拉满；全程人物口型匹配对应字幕台词，镜头运镜流畅连贯，光影色调全程统一，真人实拍电影效果，无 AI 扭曲面部，人物五官保持演员原生样貌，动态自然流畅，完整 15 秒连贯短视频。",
    "height": 768,
    "width": 1152,
    "num_frames": 121,
    "frame_rate": 24
  }'
```

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-24.png" width="600px"/>
</div>

更多细节，详细可见文档；[https://agnes-ai.com/doc/quick-start](https://agnes-ai.com/doc/quick-start)

## 二、技能安装

### 1. 技能介绍

#### 1.1 渠道技能

地址：[https://github.com/fuzhengwei/xfg-skills-free-token-plan](https://github.com/fuzhengwei/xfg-skills-free-token-plan)

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-03.png" width="650px"/>
</div>

- 首先，该技能先是分析了 one-api 源码，拿到所有服务接口，之后由智能体工具（WaliCode）按照技能创建模板（[xfg-skills-template](https://github.com/fuzhengwei/xfg-skills-template)），根据我的诉求（自动化的完成LLM渠道的维护和ApiKey的提供），制作的一套 xfg-skills-free-token-plan 技能。现技能源码全部开放，你可以学习，下载，使用。
- 之后，你可以在任何一个，支持 skills 技能配置的智能体软件上安装此技能，并与它对话，自主的完成渠道配置以及 ApiKey 的下发使用。

#### 1.2 模型技能（增强文生视频 + 文生图）

地址：[https://github.com/fuzhengwei/xfg-skills-agnes](https://github.com/fuzhengwei/xfg-skills-agnes)

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-25.png" width="650px"/>
</div>

- 这是 Agnes AI 图像与视频生成技能，兼容 Agent Skills 开放标准。直接配置到智能体软件就可以在各个场景做视频的生成了。使用过程中，技能会提示你如何操作。

### 2. 技能配置

以 OpenClaw、WaliCode 举例，其他的也都是类似的操作；

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-05.png" width="750px"/>
</div>

1. 下载本技能 [https://github.com/fuzhengwei/xfg-skills-free-token-plan](https://github.com/fuzhengwei/xfg-skills-free-token-plan)、[https://github.com/fuzhengwei/xfg-skills-agnes](https://github.com/fuzhengwei/xfg-skills-agnes) 或克隆到本地
2. 将 `xfg-skills-free-token-plan`、`xfg-skills-agnes` 目录复制到 WaLiCode Skills 目录：
   - macOS/Linux：`~/.walicode/skills/`
   - Windows：`%USERPROFILE%\.walicode\skills\`
3. 回到 WaLiCode 的 Skills 配置，点击导入即可。

> skills 脚本依赖于 python3 环境，一般 Mac 电脑都都是有的，Windows 电脑如果没有可以让智能体软件，帮你安装即可。

## 三、场景体验

### 1. 获取KEY

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-09.png" width="650px"/>
</div>

上去就是一句 `给爷来个 key` 之后你就可以拿到一个可以使用的 ApiKey 了。还有一个测试的 curl 把这个配置到你的智能体就可以使用了。

### 2. 生成图片

#### 2.1 人物图

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-27.png" width="400px"/>
</div>

随手生成个图片，直接在工具了就可以使用了。

#### 2.2 设计图

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-28.png" width="600px"/>
</div>

这次我只是给了一个简单的描述，就可以做出对应的 UI 设计。有了这些设计，还是很有利于我们做一些软件实现的。

### 3. 干个视频

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-26.png" width="600px"/>
</div>

任何一款智能体工具，安装好软件后，写好提示词，哇哇的就给出视频了。现在你不只是程序员，还是电影设计师，以后想自己搞点啥，都是美滋滋的。

## 四、深度编码

**我来啦，汤泥！**

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-18.png" width="550px"/>
</div>

如果你暂时不走 [xfg-skills-free-token-plan](https://github.com/fuzhengwei/xfg-skills-free-token-plan) 也可以直接配置模型开干！

- 软件：[https://walicode.xiaofuge.cn/](https://walicode.xiaofuge.cn/)

- BaseUrl：https://apihub.agnes-ai.com/v1
- APIKey：[https://platform.agnes-ai.com/settings/apiKeys](https://platform.agnes-ai.com/settings/apiKeys) - 直接申请就可以使用，模型能力很强！
- 模型：`agnes-2.0-flash`

### 1. 图片识别

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-14.png" width="350px"/>
</div>

- 具备图片识别能力的多模态模型，才好做开发。世界首富也可以识别出来，很可以。

### 2. 绘制流程

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-16.png" width="850px"/>
</div>

- 开发中还需要绘图，把代码流程绘制出来。这个模型很赞！

### 3. 辅助开发

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-19.png" width="850px"/>
</div>

- 长时间运行也很稳定，不错！

### 4. 智能运维

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-17.png" width="850px"/>
</div>

- 运维能力分析系统，效果很赞。也可以使用模型对服务器上的项目进行压测。

