# 自建「中转站」 + skills，让智能体自己找“粮”（token）吃！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

一会这个厂家免费送Token，一会那个厂家免费到月底，再整上一堆的个人newapi。好家伙，本地的 `OpenClaw`、`WorkBuddy`、`Claude Code`、`Codex`、`WaliCode`...，哪个不是配置了一堆的 LLM 大模型地址（`✋🏻手都配麻了`）！主要还不稳，一会这个能用，一会哪个不能用。搞滴，兄弟们，群里天天喊：**“说啊！，哪里啊！”**

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-01.png" width="150px"/>
</div>

**咋整，这滴想想办法呀🤔！**

嘿嘿，有了；自己坐庄，把这些免费的 Token 给它囊括起来。—— `技术架构师，永远都是有问题就有方案。`

Github 上有个开源项目叫 [**one-api**](https://github.com/songquanpeng/one-api) 现已是 `34.9k Star⭐️`的明星项目。它是一套最早的 LLM API 管理 & 分发系统，基本囊括了市面90%的大模型对接。完成模型配置后，可以统一分发、管理和使用 ApiKey 进行大模型通信。

也就是说 one-api 承担的是，把你拿到手的各类 LLM API 渠道都配置进来，之后以映射的方式用自定义的 `auto-model`/`AutoModel` 模型名称进行使用，屏蔽掉各类模型的名称差异。那么本地这一堆的软件，也就只是需要配置一个统一的 baseUrl、model、apiKey 即可。

但是，让每个人都自己维护 one-api 的渠道配置（还得映射模型），ApiKey 的创建，也麻烦呀。所以，他来了 [xfg-skills-free-token-plan](https://github.com/fuzhengwei/xfg-skills-free-token-plan)  自由的，免费的，Token Plan 技能！

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-02.png" width="550px"/>
</div>
自动化配置、检查渠道，自动化创建 ApiKey，简直不要太舒服！如果你有一个小组/团队/公司，那大家可久都享福了！接下来，小傅哥就告诉你怎么安装使用。

> 接下来，小傅哥就带着你使用 one-api + xfg-skills-free-token-free + [agnes](https://agnes-ai.com/doc/quick-start)（免费1M上下文模型），干起来！

## 一、技能介绍

### 1. 技能工程

地址：[https://github.com/fuzhengwei/xfg-skills-free-token-plan](https://github.com/fuzhengwei/xfg-skills-free-token-plan)

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-03.png" width="650px"/>
</div>

- 首先，该技能先是分析了 one-api 源码，拿到所有服务接口，之后由智能体工具（WaliCode）按照技能创建模板（[xfg-skills-template](https://github.com/fuzhengwei/xfg-skills-template)），根据我的诉求（自动化的完成LLM渠道的维护和ApiKey的提供），制作的一套 xfg-skills-free-token-plan 技能。现技能源码全部开放，你可以学习，下载，使用。
- 之后，你可以在任何一个，支持 skills 技能配置的智能体软件上安装此技能，并与它对话，自主的完成渠道配置以及 ApiKey 的下发使用。

### 2. 免费渠道（agnes 👍🏻）

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-04.png" width="950px"/>
</div>

- 地址：[https://github.com/fuzhengwei/xfg-skills-free-token-plan/blob/main/data/channels.csv](https://github.com/fuzhengwei/xfg-skills-free-token-plan/blob/main/data/channels.csv)
- 说明：小傅哥这里内嵌了一些渠道，像是免费的 agnes-2.0-flash 用着也还很不错（支持1M上下文）。这些内嵌的渠道，你可以通过对话的方式获取 Key，拿到渠道配置信息后，继续使用技能对话，让配置到你的渠道里就可以了。

**以下是对  Agnes LLM 大模型的验证，写代码、文生图、文生视频，确实挺厉害；**

#### 2.1 写代码

```java
curl https://apihub.agnes-ai.com/v1/chat/completions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer sk-3f9NQmveWbnL2Pxj4OAdSydKudUUeryh2q498jRuPnNWMJdQ" \
  -d '{
  "model": "auto-model",
  "messages": [{"role": "user", "content": "1+1"}]
}'
```

- 你可以自己申请 ApiKey 替换后进行测试。

#### 2.2 文生图

```java
curl https://apihub.agnes-ai.com/v1/images/generations \
  -H "Authorization: Bearer sk-3f9NQmveWbnL2Pxj4OAdSydKudUUeryh2q498jRuPnNWMJdQ" \
  -H "Content-Type: application/json" \
  -d '{
    "model": "agnes-image-2.1-flash",
    "prompt": "90 年代香港复古御姐，大波浪复古卷发，红唇明艳，细长猫眼眼线，修身丝绒红裙，复古胶片闪光灯光影，昏暗酒吧背景，颗粒胶片质感，冷艳气场，眼神疏离，轮廓立体，高级人像摄影，4K 超清，复古色调浓郁",
    "size": "1024x768",
    "extra_body": {
      "response_format": "url"
    }
  }'
```

- 提示词1 `日系氧气少女，元气笑脸，浅浅梨涡，短发内扣，浅栗色头发，日系淡妆，白衬衫百褶短裙，夏日街角街边，阳光树荫光斑，胶片质感，颗粒轻微，日系小清新色调，半身人像，自然动态，松弛姿态，高清写实，肤色通透`
- 提示词2 `90 年代香港复古御姐，大波浪复古卷发，红唇明艳，细长猫眼眼线，修身丝绒红裙，复古胶片闪光灯光影，昏暗酒吧背景，颗粒胶片质感，冷艳气场，眼神疏离，轮廓立体，高级人像摄影，4K 超清，复古色调浓郁`
- 提示词3 `绝美东方年轻女生，22 岁，鹅蛋脸，精致淡柳叶眉，下垂无辜小鹿眼，浅琥珀色瞳孔，长睫毛，自然淡卧蚕，小巧挺翘鼻梁，薄唇淡豆沙色唇釉，冷白皮，柔和原生骨相，微卷黑长直发披在肩头；宽松米白色针织吊带，细锁骨，肩颈线条流畅；窗边柔光侧逆光，薄纱窗帘透光，发丝发光，浅景深虚化背景，室内原木极简卧室，温柔氛围感，电影级光影，8K 超清，超写实人像，皮肤纹理细腻真实，单反 50mm f1.4 拍摄，高清五官特写，画质锐利，色彩柔和低饱和`
- 提示词4 `古典江南古装美人，温婉雅致，丹凤眼，桃花眼，樱桃小口，珍珠耳坠，水墨眉妆，乌黑盘发配玉簪流苏，浅青色齐胸襦裙，刺绣海棠花纹，站在江南园林荷花池边，湖面薄雾，垂柳摇曳，古风烟雨柔光，工笔质感 + 写实结合，仙气飘逸，发丝随风飘动，衣袂褶皱细腻，8K，电影光影，细节拉满，国风配色雅致`

#### 2.3 生视频

```java
curl -X POST https://apihub.agnes-ai.com/v1/videos \
  -H "Authorization: Bearer sk-3f9NQmveWbnL2Pxj4OAdSydKudUUeryh2q498jRuPnNWMJdQ" \
  -H "Content-Type: application/json" \
  -d '{
    "model": "agnes-video-v2.0",
    "prompt": "A cinematic shot of a cat walking on the beach at sunset, soft ocean waves, warm golden lighting, realistic motion",
    "height": 768,
    "width": 1152,
    "num_frames": 121,
    "frame_rate": 24
  }'
```

更多细节，详细可见文档；[https://agnes-ai.com/doc/quick-start](https://agnes-ai.com/doc/quick-start)

> 接下来，我们要把这套免费的 API 配置到技能里，感受下它是否夯爆了💥！如果着急验证模型，也可以直接跳到第五部分，做模型的配置使用。

## 二、技能安装

以 OpenClaw、WaliCode 举例，其他的也都是类似的操作；

### 方式一：从 GitHub 克隆（推荐）

```bash
# 克隆仓库到 OpenClaw Skills 目录
git clone git@github.com:fuzhengwei/xfg-skills-free-token-plan.git ~/.qclaw/skills/xfg-skills-free-token-plan
```

克隆后重启 OpenClaw 即可生效，对话中输入触发词即可使用。

### 方式二：手动安装

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-05.png" width="750px"/>
</div>

1. 下载本技能 [https://github.com/fuzhengwei/xfg-skills-free-token-plan](https://github.com/fuzhengwei/xfg-skills-free-token-plan) 或克隆到本地
2. 将 `xfg-skills-free-token-plan` 目录复制到 WaLiCode Skills 目录：
   - macOS/Linux：`~/.walicode/skills/`
   - Windows：`%USERPROFILE%\.walicode\skills\`
3. 回到 WaLiCode 的 Skills 配置，点击导入即可。

> skills 脚本依赖于 python3 环境，一般 Mac 电脑都都是有的，Windows 电脑如果没有可以让智能体软件，帮你安装即可。

## 三、服务安装

本身这套 Skills 走到是 one-api 进行渠道配置和Key的管理，你可以在本地（含docker环境）或者在云服务器安装。暂时也可以先不安装，使用小傅哥提供的 one-api（在skills技能里）先玩玩试试。

```java
version: '3'
services:
  mysql:
    image: registry.cn-hangzhou.aliyuncs.com/xfg-studio/mysql:8.0
    container_name: oneapi-mysql
    restart: always
    ports:
      - "13306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: 12345678
      MYSQL_DATABASE: oneapi
    volumes:
      - /home/ubuntu/data/mysql:/var/lib/mysql
    networks:
      - oneapi-net

  one-api:
    image: registry.cn-hangzhou.aliyuncs.com/xfg-studio/one-api:v0.6.11
    container_name: one-api
    restart: always
    ports:
      - "4000:3000"
    environment:
      SQL_DSN: "root:12345678@tcp(mysql:3306)/oneapi"
      TZ: Asia/Shanghai
    volumes:
      - /home/ubuntu/data/one-api:/data
    depends_on:
      - mysql
    networks:
      - oneapi-net

networks:
  oneapi-net:
    driver: bridge
```

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-06.png" width="850px"/>
</div>


- 通过 docker-compose 执行脚本即可安装完成。
- 如果你使用的是 WaLiCode 那么还可以直接连接云服务器，告诉 AI 在云服务器上配置这套东西就可以了。

## 四、技能配置

### 1. 配置服务（one-api）

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-07.png" width="650px"/>
</div>

> 配置 one-api，地址 http://81.70.245.73:4000，账户 root，密码 12345678

- 选择安装技能，描述如上的话术，把 one-api 渠道配置到技能里。我的这个地址，暂时是可以测试使用的。如果你有部署一个自己的 one-api 记得更改下`地址`、`账户`、`密码`。

- 配置后会自动检测服务是否可用，之后会告诉你怎么使用，如；`给我来个 key`、`添加渠道`、`有哪些渠道`、`检查渠道`

### 2. 配置渠道

你自己的 one-api 安装后，需要找一些 LLM 渠道配置，可以是任何你现在使用的。如果还没有啥 LLM 也可以使用本文的这个免费（效果还不错）的。

#### 2.1 key申请

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-13.png" width="650px"/>
</div>

地址：[https://platform.agnes-ai.com/settings/apiKeys](https://platform.agnes-ai.com/settings/apiKeys) 在这里申请个 ApiKey 配置渠道的时候需要使用。

#### 2.2 key配置

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-08.png" width="650px"/>
</div>

```java
[前面选择上技能]添加渠道 
agnes 
地址；https://apihub.agnes-ai.com 
模型；agnes-2.0-flash 
ApiKey；sk-3f9NQmveWbnL2Pxj4OAdSydKudUUeryh2q498jRuPnNWMJdQ
```

### 3. 获取模型（ApiKey）

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-09.png" width="650px"/>
</div>
上去就是一句 `给爷来个 key` 之后你就可以拿到一个可以使用的 ApiKey 了。还有一个测试的 curl 把这个配置到你的智能体就可以使用了。

### 4. 模型配置

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-10.png" width="650px"/>
</div>

```java
curl http://81.70.245.73:4000/v1/chat/completions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer sk-bhdR5hX8Asq98z1g5a413fDcC44a4c0186711a7dD020Ec3a" \
  -d '{
  "model": "auto-model",
  "messages": [{"role": "user", "content": "1+1"}]
}'
```

接下来就容易了，各个智能体工具，你就配置这一个地址就行了。以后有啥免费的 LLM 直接扔进去，让 xfg-skills-free-token-plan 帮你维护。

### 5. 检查渠道

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-11.png" width="650px"/>
</div>
当我们配置了越来也多的免费渠道以后，这些渠道可能一段时间就不能用了。那么你可以`检查渠道`，skills 会自动的帮你测试，以及调整渠道的优先级。不可用的时候就会被下掉。

> 咋样，这样用起来是不蛮爽的！赶紧动起来，**你快点来，我等你！**

<div align="center">
	<img src="https://bugstack.cn/images/article/ai/xfg-skills-free-token-plan-12.png" width="150px"/>
</div>

- 技能包：[https://github.com/fuzhengwei/xfg-skills-free-token-plan](https://github.com/fuzhengwei/xfg-skills-free-token-plan)
- 服务器：[http://618.gaga.plus/](http://618.gaga.plus/)
- 免费（LLM）：[https://platform.agnes-ai.com/settings/apiKeys](https://platform.agnes-ai.com/settings/apiKeys)

## 五、编写代码

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

