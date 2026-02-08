---
title: OpenClaw
lock: need
---

# 🦞OpenClaw，你是不还没有安装上？这个中文版，可以试试！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

🦞OpenClaw，大龙虾，看着挺复杂，其实一点也不简单。都火成这德行了，但也还是有很多伙伴在安装这一块就挂壁了。想体验呀，咋整？

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-01.gif" width="150px"/>
</div>

**怎么理解 OpenClaw 🦞**

OpenClaw 是一款开源 AI 助手，不到2个月时间 [Github](https://github.com/openclaw/openclaw) 点赞量已经超过 170k Star ⭐️，它的出现为我们提供了更俏的思路使用 LLM 大模型。从24年底，定义了 MCP 协议，25年爆发了 Ai Agent 的落地，随后出现 [GLM AutoPhone](https://bugstack.cn/md/algorithm/model/autoglm-phone-agent.html) 控制手机，现在又来了大龙虾 OpenClaw 控制电脑。

你可以把 OpenClaw 理解为一个从你到电脑设备，需要敲键盘的过程，**设计了一个中间网关层**。你的指令可以通过各类 APP/Web 对话的方式，下发给 OpenClaw 完成电脑的一些列操作。如；`帮我安装个Docker`、`在Docker中部署下Redis`、`告诉我现在运行状况和剩余可用资源`等等操作。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-02.png" width="750px"/>
</div>

综上，这让我们离`数字员工`又近了一步，后续可以拷贝个人工作技能到 skills 编写，如一些高频重复的场景，对现存系统运行的巡检，对运营类数据的排查，对活动经营情况的分析等。都可以被制作为 skills 让 OpenClaw 使用。

接下来，小傅哥就带着大家安装下 OpenClaw + 飞书配置，这套组合还是蛮好用的。

## 一、安装介绍

### 1. 软件

官网：[https://openclaw.ai/](https://openclaw.ai/)
源码：[https://github.com/openclaw/openclaw](https://github.com/openclaw/openclaw)

中文社区：
- [https://clawd.org.cn/](https://clawd.org.cn/) - `新人伙伴比较推荐实用这个，全是中文提示，安装起来更友好。`
- [https://openclaw.qt.cool/](https://openclaw.qt.cool/)
- [https://1panel.cn](https://1panel.cn/)

> 除了官网的 OpenClaw 资源，还有不少中文社区，对 OpenClaw 做了镜像和汉化的处理。安装方式也都一样，小白伙伴也可以体验下，全是中文对小白更友好。另外，这部分汉化是基于 openclaw 源码来做的，如果你有一些预安装诉求，以及汉化或者有自身公司想做些扩展也可以基于源码 fork 来改造。

### 2. 环境

- nodejs 22+ （不少安装包里会提供检测和安装）[https://nodejs.org/zh-cn/download](https://nodejs.org/zh-cn/download)
- 2c4g 云服务器/本地电脑 [https://618.gaga.plus](https://618.gaga.plus) - `推荐买1年送3个月的` + Ubuntu 24 系统。
- 环境初始化脚本 [https://gitcode.com/Yao__Shun__Yu/xfg-dev-tech-docker-install](https://gitcode.com/Yao__Shun__Yu/xfg-dev-tech-docker-install) - `执行 sudo ./openclaw.sh 会帮你在云服务器安装 nodejs 22+`
- 镜像资源 `--registry https://registry.npmmirror.com` 如使用 `sudo npm install -g openclaw@latest --registry https://registry.npmmirror.com`

### 3. 资源

- GLM ApiKey：[https://open.bigmodel.cn/usercenter/proj-mgmt/apikeys](https://www.bigmodel.cn/glm-coding?ic=KFLKGANUMO) - 可以换账号新注册下，获取2000万token，可以提前申请好，复制到自己的文档里（你可能会失败好多次，反复使用）。
- 飞书：[https://open.feishu.cn/app](https://open.feishu.cn/app) - `企业自建应用（下载个桌面版更好用）`，先发布一次（拿到App ID、App Secret）让 OpenClaw 对接上，之后再飞书机器人配置上**事件配置**，之后在发布一次应用。这样就建立起链接了，下文教程有详细处理过程。

## 二、部署介绍

### 1. 部署流程

OpenClaw 的安装过程中，会涉及一些列命令的使用，我把这些放到一个图里，方便大家理解；

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-03.png" width="750px"/>
</div>

- 首先，关于安装可以使用 curl 向导式一次安装，也可以使用 npm 安装软件后，在启动配置向导。在 [xfg-dev-tech-docker-install](https://gitcode.com/Yao__Shun__Yu/xfg-dev-tech-docker-install) 一键安装脚本中，提供了 `sudo ./openclaw.sh` 安装脚本，附带的会帮助你完成 nodejs 22+ 的检测和安装，以及飞书组件的提示，会简化一些你的操作。
- 然后，在执行 `sudo openclaw onboard` 运行向导后，会告诉你这个软件的安全提示，你可以通过`←` `→`选择 yes 安装即可。再往后，会进行模型的选择，这里支持好多模型，本案例使用的  [glm apikey](https://www.bigmodel.cn/glm-coding?ic=KFLKGANUMO)
- 之后，一系列选择后（不分可以跳过），包括通信渠道（飞书）也可以先跳过，即可完成安装。安装后可以通过 `sudo openclaw gateway` 启动网关。这样 openclaw 也就正式启动起来了。
- 再后，安装飞书插件 `sudo openclaw plugins install @m1heng-clawd/feishu --registry https://registry.npmmirror.com` 这东西的目的是让 openclaw 可以对接上飞书。

### 2. 常用命令

| 命令                                                         | 作用                    | 备注 / 参数                                   |
| :----------------------------------------------------------- | :---------------------- | :-------------------------------------------- |
| `npm install -g openclaw@latest --registry https://registry.npmmirror.com` | 安装 openclaw           |                                               |
| `openclaw onboard`                                           | 安装引导                |                                               |
| `openclaw status`                                            | 查看 Gateway 状态       | 检查网关是否可达及运行状况                    |
| `openclaw health`                                            | 健康检查                | 主要检测 core 运行和依赖情况                  |
| `openclaw doctor`                                            | 综合诊断与修复建议      | 可配合 `--yes` / `--non-interactive` 自动执行 |
| `openclaw configure`                                         | 交互式配置向导          | 用于设置模型、通道、凭据等                    |
| `openclaw config get <path>`                                 | 获取配置值              | 指定路径提取配置                              |
| `openclaw config set <path> <value>`                         | 设置配置项              | 支持 JSON5/raw 文本                           |
| `openclaw config unset <path>`                               | 清除配置项              | 移除单个键值                                  |
| `openclaw channels list`                                     | 列出已登录通道          | 可观察 WhatsApp/Telegram 等登录状态           |
| `openclaw channels login`                                    | 登录新的通道账号        | 用于扫描/授权链接                             |
| `openclaw skills list`                                       | 列出技能                | 查看可用/已安装的技能                         |
| `openclaw skills info <skill>`                               | 技能详情                | 观察某项技能参数或版本                        |
| `openclaw plugins list`                                      | 列出插件                | 查看已安装插件                                |
| `openclaw plugins install <id>`                              | 安装插件                | 例如 @openclaw/voice-call                     |
| `openclaw plugins enable <id>`                               | 启用插件                | 之后通常需要重启网关                          |
| `openclaw logs --follow`                                     | 显示日志                | `--json / --plain / --limit` 等组合使用       |
| `openclaw gateway`                                           | 启动 Gateway 网关       |                                               |
| `openclaw gateway install`                                   | 安装系统服务            | 根据平台注册 Gateway 守护进程                 |
| `openclaw gateway start`                                     | 启动 Gateway 网关       | 系统服务模式下启动                            |
| `openclaw gateway stop`                                      | 停止 Gateway 网关       | 同上                                          |
| `openclaw gateway restart`                                   | 重启 Gateway 网关       | 适合配置变更后应用                            |
| `openclaw gateway status`                                    | 网关系统服务状态        | 不同于 `openclaw status`，会探测服务单元      |
| `openclaw uninstall`                                         | 卸载 Gateway 服务及数据 | 官方推荐使用                                  |
| `openclaw uninstall --all --yes --non-interactive`           | 全自动卸载              | 包含状态、workspace、插件等                   |
| `openclaw uninstall --state`                                 | 删除状态文件            | 不删除 workspace/CLI                          |
| `openclaw uninstall --workspace`                             | 删除工作区              | 移除 agent/workspace 文件                     |
| `openclaw uninstall --service`                               | 仅卸载服务              | 不删除数据                                    |
| `openclaw uninstall --dry-run`                               | 模拟卸载                | 显示结果但不实际执行                          |

## 三、部署操作

### 1. 创建飞书

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-04.png" width="850px"/>
</div>

#### 1.1 创建应用

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-05.png" width="850px"/>
</div>

**批量导入，权限列表**

```java
{
  "scopes": {
    "tenant": [
      "aily:file:read",
      "aily:file:write",
      "application:application.app_message_stats.overview:readonly",
      "application:application:self_manage",
      "application:bot.menu:write",
      "cardkit:card:write",
      "contact:contact.base:readonly",
      "contact:user.employee_id:readonly",
      "corehr:file:download",
      "docs:document.content:read",
      "event:ip_list",
      "im:chat",
      "im:chat.access_event.bot_p2p_chat:read",
      "im:chat.members:bot_access",
      "im:message",
      "im:message.group_at_msg:readonly",
      "im:message.group_msg",
      "im:message.p2p_msg:readonly",
      "im:message:readonly",
      "im:message:send_as_bot",
      "im:resource",
      "sheets:spreadsheet",
      "wiki:wiki:readonly"
    ],
    "user": [
      "aily:file:read",
      "aily:file:write",
      "im:chat.access_event.bot_p2p_chat:read"
    ]
  }
}
```

- 创建应用后，你可以获得到应用凭证（appid、appsecret）这个信息是用于 openclaw 对接使用的。

#### 1.2 发布应用

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-06.png" width="850px"/>
</div>

- 创建应用后，点击发布应用。这样你创建的应用才是有效的。

#### 1.3 事件配置（机器人）- openclaw 配置之前

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-07.png" width="850px"/>
</div>

- 飞书应用创建后，还需要配置机器人。但这会配置也是失败的，因为 openclaw 还没有关联上来。
- 这里需要在 openclaw 配置飞书通信渠道，之后重启 openclaw 网关，这里点击保存并配置权限（`接收消息`、`通讯录基本信息`），点击确认开通权限。在发布一个新应用，再和飞书机器人对话就可以使用了。

### 2. 安装应用（openclaw）

#### 2.1 执行脚本（含带引导）

```java
curl -fsSL https://openclaw.ai/install.sh | sudo bash -s -- --registry https://registry.npmmirror.com
```

- openclaw 官网版

```java
curl -fsSL https://clawd.org.cn/install.sh | sudo bash -s -- --registry https://registry.npmmirror.com
```

- openclaw-cn 中文社区版，适合新人伙伴使用。熟练后，使用官网版即可。他们操作的方式是一样的。
- 安装后的使用差异，一个是 `sudo openclaw config` 一个是 `sudo openclaw-cn config`

#### 2.2 引导配置

```java
sudo npm install -g openclaw@latest --registry https://registry.npmmirror.com
sudo npm install -g openclaw-cn@latest --registry https://registry.npmmirror.com

# 运行向导
sudo openclaw onboard
sudo openclaw-cn onboard
```

- 注意 `-cn` 为中文版，如果你不是执行的 curl 一键安装版本，是采用了 安装 `openclaw@latest` 程序，那么需要自己执行引导配置。

##### 2.2.1 安全提示

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-09.png" width="650px"/>
</div>

##### 2.2.2 配置方式

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-10.png" width="550px"/>
</div>

##### 2.2.3 模型配置

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-11.png" width="450px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-12.png" width="450px"/>
</div>

##### 2.2.4 通信通道（飞书）

**选择渠道**

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-13.png" width="450px"/>
</div>

**安装插件**

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-14.png" width="450px"/>
</div>

- 如果未安装过飞书插件，可以选择安装，也可以跳过后，后续在安装。
- 单独安装插件 `sudo openclaw-cn plugins install @m1heng-clawd/feishu`

**配置凭证**

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-15.png" width="650px"/>
</div>

- 把你的应用凭证，配置到 openclaw 应用程序里。之后继续回车。
- 注意，一会要会到飞书页面，配置事件关联到 openclaw 上。

##### 2.2.5 技能配置

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-16.png" width="650px"/>
</div>

##### 2.2.6 部署完成

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-17.png" width="650px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-18.png" width="650px"/>
</div>

```java
ubuntu@VM-0-2-ubuntu:~$ sudo openclaw-cn gateway
```

- 配置完成后，要启动下网关。能看到以上信息，表示运行没问题了。
- 现在要会到 1.4 接着配置飞书。

### 3. 配置飞书

#### 3.1 事件配置（机器人）

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-19.png" width="850px"/>
</div>

- 注意，完成 2.2.6 步骤后，再回来操作。
- openclaw 关联上飞书以后，点击**添加事件**，以及添加上相关权限。完成后，会提示你发布一个新应用。

#### 3.2 发布应用

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-20.png" width="850px"/>
</div>

- 点击发布应用，版本号 +1

#### 3.3 打开应用

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-21.png" width="850px"/>
</div>

- 这会对话飞书，会提示执行一个命令，类似于验证签名。
- 命令 `sudo openclaw-cn pairing approve feishu KF2BRAXW`

#### 3.4 验证前面（+重启网关）

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-22.png" width="850px"/>
</div>

```java
ubuntu@VM-0-2-ubuntu:~$ sudo openclaw-cn pairing approve feishu KF2BRAXW

🦞 Clawdbot-CN 0.1.4 (b161cdd) — 唯一不能在你的私信上训练的机器人Mark。

Approved feishu sender ou_762197513d3d1cb907f4c0d2ed3c3b2b.
ubuntu@VM-0-2-ubuntu:~$ sudo openclaw-cn gateway

🦞 Clawdbot-CN 0.1.4 (b161cdd) — 我不是魔法——我只是在重试和应对策略上极其执着。

06:39:38 [canvas] host mounted at http://127.0.0.1:18789/__clawdbot__/canvas/ (root /root/clawd/canvas)
06:39:38 [heartbeat] started
06:39:38 [gateway] agent model: zai/glm-4.7
06:39:38 [gateway] listening on ws://127.0.0.1:18789 (PID 20687)
06:39:38 [gateway] listening on ws://[::1]:18789
06:39:38 [gateway] log file: /tmp/clawdbot/clawdbot-2026-02-08.log
06:39:38 [browser/server] Browser control listening on http://127.0.0.1:18791/
06:39:38 [feishu] [default] starting Feishu provider (你的应用名称)
06:39:38 [info]: [ 'event-dispatch is ready' ]
```

- 如上方式，执行你的签名验证，并重启网关。
- 到这，就配置完成了，可以使用了。

## 四、使用体验

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-openclaw-23.png" width="950px"/>
</div>

现在你可以发挥想象的让 openclaw 帮你做事情了，很多运维的活，他也都可以完成。美滋滋！
