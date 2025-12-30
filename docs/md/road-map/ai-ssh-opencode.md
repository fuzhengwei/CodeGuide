---
title: ai ssh
lock: need
---

# ai ssh 命令行工具（opencode）

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

大家好，我是技术UP主小傅哥。

天才般的发明，**ai ssh 命令行工具**，让 ai 的使用不只局限在 `ai 对话提问`、`ai idea 辅助编码`，也可以直接让操作系统具备 ai 能力。其实这才是我一直想要的东西！😄

<div align="center">
	<img src="https://bugstack.cn/images/article/project/chatgpt/openai-01.jpg" width="150px"/>
</div>

**ai 正在改变产品设计！**

2025年，是 ai agent 智能体爆发的一年，它所提效的接入方式可以分三层来看；

先是中间这一层，是 ai agent 接入服务和软件，像是大家使用的 trae.ai 或者小傅哥分享过的 [draw.io + ai](https://bugstack.cn/md/road-map/draw.io.html)，以及各个互联网公司用 ai 提效做的一些场景客服，系统巡检，日志分析等。

之后上面这一层，是从用户视角，模拟用户行为使用软件，而不是直接接入到软件api上。它使用的方式是 AutoGLM-Phone-9B 手机模型，以 ADB 或者无障碍模式，让手机可以接收用户指令，完成操作。如；`打开京东，搜索东北老式麻辣烫，加入购物车，以默认地址，进行支付购买。下单支付完成后，微信发给xxx告诉，她预计送达时间。` 如果感兴趣，可以基于这篇文章进行实践。[《手机 + agent，这是要掀桌子！》](https://bugstack.cn/md/algorithm/model/autoglm-phone-agent.html)

然后底下这一层，是从系统层面，不再依赖于直接对接某个软件，某个api，而是以系统层面直接操作软件，或者完成整个行为动作。如，在云服务器/本地，配置好 jdk、maven、docker 环境，之后帮我拉取 git clone xxx 仓库代码，本地完成 maven 构建和启动。以及在遇到问题时候，让 ai 直接检索并处理都是非常可以的。

好，那接下来，小傅哥给大家分享下，如何安装一个 ai ssh 工具（没有这个教程，很多人是安装不上的！）。

>🧧 文末提供了小傅哥所有编程实战项目获取方式，一次加入即可获得19个已完结的实战项目，也有非常多的 AI 类项目，一定要补充学习！

## 一、工具介绍

ai ssh 是命令行工具，可以安装到 Mac、Windows、Linux 上，以通过 terminal（终端）直接操作系统进行使用。它的场景也包括你打开的 IntelliJ IDEA 下面的 terminal 终端中使用，这样就天然的嵌入到了 IntelliJ IDEA 中了，非常方便。

目前这类的 ai ssh 命令行工具软件也非常多，包括；[opencode.ai](https://opencode.ai/)、[claude](https://claude.com/product/claude-code)、[openai codex](https://github.com/openai/codex)、[阿里千问 - qianwen code](https://github.com/QwenLM/qwen-code)，都出了对应的软件。

在整体体验后，效果还是都不错的，这里是 opencode 的终端使用截图；

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-opencode-01.png" width="650px"/>
</div>

- opencode 默认提供了一些免费模型，可以输入 `/models` 进行选择。
- 另外还可以通过配置文件，添加其他模型，如 claude 模型、openai 模型、小米模型等。

## 二、软件安装

以下软件安装，会需要用到 gcc、nodejs 20+ 版本，建议安装 Ubuntu 24 版本，可以不需要折腾系统环境。

### 1. 脚本说明

这软件好用是挺好用，但它的源都在 Github 上，很多伙伴在执行官网脚本 `curl -fsSL https://opencode.ai/install | bash` 是安装不上的。所以，小傅哥做了一个对应的脚本，方便大家更加简化的安装使用。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-opencode-02.png" width="350px"/>
</div>

- 脚本（gitcode）：[https://gitcode.com/Yao__Shun__Yu/xfg-dev-tech-docker-install](https://gitcode.com/Yao__Shun__Yu/xfg-dev-tech-docker-install)
- 脚本（github）：[https://github.com/fuzhengwei/xfg-dev-tech-docker-install](https://github.com/fuzhengwei/xfg-dev-tech-docker-install)

### 2. 脚本下载

```java
git clone https://gitcode.com/Yao__Shun__Yu/xfg-dev-tech-docker-install.git
```

- 你可以通过命令的方式把脚本拉取到本地电脑或者云服务器上。

### 3. 脚本授权

```java
find . -name "*.sh" -type f -exec chmod +x {} \;
```

- 全部授权

```java
chmod +x terminal.sh
```

- 指定授权

### 4. 执行安装

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-opencode-03.png" width="650px"/>
</div>

```java
./terminal.sh
```

- 执行安装时，选择1，opencode code，这个是比较推荐的。
- 无论是 Windows、Mac、Linux 都可以使用这个脚本进行安装，我已经做好了对应的脚本。
- 安装完成后，需要输入 `opencode` 之后 Enter（回车）进入到系统中。如果提示 opencode 不是有效的命令，可以检查是否安装过程中有个提示 `source .../bashrc` 可以自行执行刷新。

## 三、软件配置

### 1. 命令使用

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-opencode-04.png" width="650px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-opencode-05.png" width="650px"/>
</div>

- `/init` - 创建/更新 Agents.md"
- `/review` - 检查变化"
- `/new` - 创建新的会话"
- `/models` - 选择模型"
- `/agents` - 智能体方式"
- `/session` - 会话列表"
- `/status` - 查看状态"
- `/mcp` - 选择mcp服务"
- `/theme` - 选择主题"
- `/editor` - 编辑"
- `/connect` - 链接模型提供者"
- `/help` - 帮助"
- `/commands` - 命令"
- `/exit` - 调试模式"

> 这些可能会随着版本更新而调整，你可以依次尝试下。

### 2. 配置模型（自定义 - 可选）

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-opencode-06.png" width="350px"/>
</div>

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-opencode-07.png" width="650px"/>
</div>

```java
{
    "$schema": "https://opencode.ai/config.json",
    "provider": {
        "my-model-openai": {
            "npm": "@ai-sdk/openai-compatible",
            "name": "OpenAPI",
            "options": {
                "apiKey": "你的apikey",
                "baseURL": "https://你的baseURL/v1"
            },
            "models": {
                "gpt-4.1": {
                    "name": "gpt-4.1"
                }
            }
        },
        "my-model-glm": {
            "npm": "@ai-sdk/openai-compatible",
            "name": "GLMAPI",
            "options": {
                "apiKey": "你的apikey",
                "baseURL": "https://open.bigmodel.cn/api/paas/v4"
            },
            "models": {
                "glm-4.7": {
                    "name": "glm-4.7"
                }
            }
        }
    },
    "model": "my-model-openai/gpt-4.1"
}
```

- 如果你想自己更换下模型，比如使用 openai 的或则 claude 的，那么需要你在脚本下的 terminal/opencode.json 进行更换，之后执行 `./opencode.json.sh` 进行创建。
- 如果先执行了 ``./opencode.json.sh`` 之后想更换配置的模型，则可以通过 vim 命令，编辑 `/root/.config/opencode/opencode.json`
- 其他的还有一些像是 mcp 的配置，可以参考官网（避免调整了配置错），[https://opencode.ai/docs/mcp-servers/](https://opencode.ai/docs/mcp-servers/)


## 三、软件使用

### 1. Linux

#### 1.1 构建项目

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-opencode-08.png" width="650px"/>
</div>

#### 1.2 安装软件

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-opencode-09.png" width="650px"/>
</div>

#### 1.3 巡检系统

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-opencode-10.png" width="650px"/>
</div>

#### 1.4 编写文件

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-opencode-11.png" width="650px"/>
</div>

>通过命令操作云服务器是非常便捷的，尤其是很多小白伙伴，有了这个可以说是如小白虎添翼！打开思路，你可以在更多地方使用上，尤其哪些环境安装都困难的伙伴。

### 2. Mac/Windows + IntelliJ IDEA

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-opencode-12.png" width="650px"/>
</div>

- 在本地电脑，除了可以像 Linux 举例那种安装和检查各类软件，也可以直接在 IntelliJ IDEA 开启，之后管理你的项目。
- 尤其是很多伙伴，拿到一个项目，不知道里面都是什么，也不清楚脚本能干啥，那你都可以使用 opencode 帮你解决。

### 3. IPad Pro

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/road-map-opencode-13.png" width="450px"/>
</div>

虽然 IPad Pro 没啥开发类软件，但如果你使用的是云服务器、Nas等，这些软件里安装 opencode，那么你也可以在 IPad Pro 安装 [termius.com](https://termius.com/) SSH 工具，通过 SSH 工具操作部署了 opencode 的 Linux 系统。

现在日常出门，带着 IPad Pro 也是可以处理一些小的开发的维护的，非常方便。IPad Pro + Nas 嘎嘎舒服！
