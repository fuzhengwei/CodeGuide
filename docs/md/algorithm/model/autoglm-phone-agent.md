---
title: AutoGLM Phone Agent
lock: need
---

# 手机 + agent，这是要掀桌子！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

`这 + agent`，`那 + agent`，都是赋能，辅助提效。但**手机 + agent**，要掀桌子呀，这是要改变现有手机和APP厂商入口的格局。就像你开了个超市，别在你家开了个【超市入口】！

<div align="center">
	<img src="https://bugstack.cn/images/article/project/chatgpt/openai-01.jpg" width="150px"/>
</div>

**智能体时代，谁也阻挡不住！**

先是有豆包手机，之后GLM推出 AutoGLM-Phone-9B 模型，可以通知命令、API、语音等方式，以多模态形式，帮助用户完成手机任务。如，`打开xxxAPP，搜索xxx商品，完成下单，之后通知给xxx微信伙伴，在xxx时间，进行收货`。`也可以，告诉手机，定时定点完成xxxAPP的签到、领券，刷票抢票`。`还可以，为老年人对于手机的xxx业务复杂的操作，进行一些列的自动化完成处理`。

这将是下一代手机的使用体验，也是各大厂即将争夺的智能入口。接下来，小傅哥带着大家部署下 AutoGLM 模型，以及讲解如何配置使用和最终的效果。

目前 AutoGLM 还是面向研发使用的阶段，不是直接可以调用的 API，所以要自己部署。不过以后肯定会更加方便，也会附带的提供对应的产品。也有可能出新安卓/IOS+agent的手机系统。路已经开了，看谁跑的快吧！

## 一、模型介绍

官网：[https://github.com/zai-org/Open-AutoGLM](https://github.com/zai-org/Open-AutoGLM)

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-16.png" width="550px"/>
</div>

- Phone Agent 是一个基于 AutoGLM 构建的手机端智能助理框架，它能够以多模态方式理解手机屏幕内容，并通过自动化操作帮助用户完成任务。系统通过 ADB（Android Debug Bridge）来控制设备，以视觉语言模型进行屏幕感知，再结合智能规划能力生成并执行操作流程。用户只需用自然语言描述需求，如“打开小红书搜索美食”，Phone Agent 即可自动解析意图、理解当前界面、规划下一步动作并完成整个流程。系统还内置敏感操作确认机制，并支持在登录或验证码场景下进行人工接管。同时，它提供远程 ADB 调试能力，可通过 WiFi 或网络连接设备，实现灵活的远程控制与开发。
- 其他资料：[https://mp.weixin.qq.com/s/wRp22dmRVF23ySEiATiWIQ](https://mp.weixin.qq.com/s/wRp22dmRVF23ySEiATiWIQ)

## 二、安装教程

### 1. 环境要求

- Python 3.10+ - `如果你是手动配置环境，可以参考` [https://bugstack.cn/md/algorithm/model/2023-05-21-chatglm-6b.html](https://bugstack.cn/md/algorithm/model/2023-05-21-chatglm-6b.html)
- GPU 24G * 2 显卡（单卡24G部署，成功概率低）推荐具备 AI 能力的云服务器，可以减少很多基础环境的配置。如：[autodl.com](https://www.autodl.com)
- 安卓手机一台 + 数据线（不能只是充电功能的线），`设置-关于手机-版本号` 然后连续快速点击 10 次左右，直到弹出弹窗显示“开发者模式已启用”。启用开发者模式之后，会出现 `设置-开发者选项-USB 调试`，勾选启用。（如果手机不是这样的，可以百度搜下设置）
- AutoGLM-Phone-9B/AutoGLM-Phone-9B-Multilingual 模型镜像地址:[https://huggingface.co/zai-org/AutoGLM-Phone-9B/tree/main](https://huggingface.co/zai-org/AutoGLM-Phone-9B/tree/main)
- ADB (Android Debug Bridge) 桥接测试包 下载地址：[https://developer.android.com/tools/releases/platform-tools?hl=zh-cn](https://developer.android.com/tools/releases/platform-tools?hl=zh-cn) - `下载后配置路径 export PATH=${PATH}:~/Downloads/platform-tools` Windows 电脑参考第三方教程：[https://blog.csdn.net/x2584179909/article/details/108319973](https://blog.csdn.net/x2584179909/article/details/108319973)
- ADB Keyboard 安卓手机输入法，用于连接桥接测试包 下载地址：[https://github.com/senzhk/ADBKeyBoard/blob/master/ADBKeyboard.apk](https://github.com/senzhk/ADBKeyBoard/blob/master/ADBKeyboard.apk) - 安装后在手机`设置-输入法` 或者 `设置-键盘列表` 中启用 `ADB Keyboard` 才能生效

> 接下来介绍，各个环境配置以及验证使用。只想看效果的，可以翻看到最后（使用效果）。

### 2. 算力部署

目前具备 AI 算力的服务器(支持小时购买的，关机不收费)；

- [www.autodl.com](https://www.autodl.com/home) - `推荐 vGPUT-48GB，推荐 717机、708机`
- [www.ucloud.cn](https://passport.ucloud.cn/?cps_code=5704U7oL0BOGDYCoSmU0pj)
- [https://gcs-console.jdcloud.com/instance/create?region=cn-central-xy1](https://gcs-console.jdcloud.com/instance/create?region=cn-central-xy1)  - `2*24GB`

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-01.png" width="550px"/>
</div>

> 注意，选择2卡，单卡24G的，否则大概率会失败。

#### 2.1 创建实例（autodl）

##### 2.1.1 创建选择

地址：[https://www.autodl.com/create](https://www.autodl.com/create)

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-02.png" width="750px"/>
</div>

##### 2.1.2 创建完成

地址：[https://www.autodl.com/console/instance/list](https://www.autodl.com/console/instance/list) - `控制台`

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-03.png" width="750px"/>
</div>

- 创建并开机后，稍等即可看到GPU服务运行。
- 注意，蓝色字 JupyterLab 是你登录地址，可以进入控制台部署模型。

> 其他的 GPU 服务器也都类似，如果使用的纯白 GPU 服务器，需要自己安装各种环，可参考；境。[https://bugstack.cn/md/algorithm/model/2023-05-21-chatglm-6b.html](https://bugstack.cn/md/algorithm/model/2023-05-21-chatglm-6b.html)

#### 2.2 模型部署

##### 2.2.1 进入终端

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-04.png" width="850px"/>
</div>

- 注意，进入后，要把服务和软件安装到 `/root/autodl-tmp` 下，否则系统盘安装满了，就不能运行了。

##### 2.2.2 拉取代码

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-05.png" width="850px"/>
</div>

- 进入到 `/root/autodl-tmp`  拉取项目工程代码 `git clone https://github.com/zai-org/Open-AutoGLM.git`

##### 2.2.3 更新文件（requirements.txt）

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-06.png" width="550px"/>
</div>

```java
Pillow>=12.0.0
openai>=2.9.0

# For Model Deployment
# Linux 云服务器环境下可以正常安装这些包
transformers>=4.30.0
vllm>=0.12.0

# Optional: sglang (如果需要的话)
# sglang>=0.5.6.post1

# Optional: for development
pytest>=7.0.0
pre-commit>=4.5.0
black>=23.0.0
mypy>=1.0.0
```

- 这份文件需要更新下，默认是关闭的。

##### 2.2.4 安装依赖

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-07.png" width="750px"/>
</div>

```java
pip install -r requirements.txt 
pip install -e .
```

- 设置镜像源；`pip config set global.index-url https://pypi.tuna.tsinghua.edu.cn/simple`
- `cd Open-AutoGLM` 以此执行安装脚本。这个过程需要一段时间。如果有失败，可以重复执行脚本。

##### 2.2.5 下载模型

下载模型是为了启动 `Open-AutoGLM`，如果不手动下载，默认执行脚本速度比较慢，同时可能把文件安装到系统盘中。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-08.png" width="750px"/>
</div>

- 官网地址：[https://huggingface.co/zai-org/AutoGLM-Phone-9B/tree/main](https://huggingface.co/zai-org/AutoGLM-Phone-9B/tree/main)

- 镜像地址：[https://modelscope.cn/models/ZhipuAI/AutoGLM-Phone-9B](https://modelscope.cn/models/ZhipuAI/AutoGLM-Phone-9B) - `【下载模型】里面有对应的使用说明`

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-09.png" width="750px"/>
</div>

使用说明：

- 在下载前，请先通过如下命令安装ModelScope `pip install modelscope`
- 指定路径，下载完整模型库 `modelscope download --model ZhipuAI/AutoGLM-Phone-9B --local_dir ./AutoGLM-Phone-9B`

##### 2.2.6 模型部署

**脚本说明** - `固定大小的参数不能修改`

```java
python3 -m vllm.entrypoints.openai.api_server \
 --served-model-name autoglm-phone-9b \
 --allowed-local-media-path /   \
 --mm-encoder-tp-mode data \
 --mm_processor_cache_type shm \
 --mm_processor_kwargs "{\"max_pixels\":5000000}" \
 --max-model-len 25480  \
 --chat-template-content-format string \
 --limit-mm-per-prompt "{\"image\":10}" \
 --model /root/autodl-tmp/Open-AutoGLM/AutoGLM-Phone-9B \
 --port 6008
```

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-11.png" width="750px"/>
</div>

- model 由 Open-AutoGLM 默认的 `zai-org/AutoGLM-Phone-9B` 从 `https://huggingface.co/zai-org/AutoGLM-Phone-9B/tree/main` 下载，修改为已经下载好的本地的路径地址。
- port 修改为 6008 端口，因为 `autodl.com` 算力指定的自定义服务，对外暴漏的端口有 6008、6006 部署后，用 `https://uu835267-800d-24be97d2.westc.gpuhub.com:8443` 访问服务。

**执行脚本**

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-10.png" width="750px"/>
</div>

##### 2.2.7 验证模型

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-17.png" width="750px"/>
</div>

```java
curl https://cb869967ef619cf1-8000.cn-south-1.gpu-instance.ppinfra.com/v1/chat/completions -H "Content-Type: application/json" -d '{
  "model": "autoglm-phone-9b",
  "messages": [
    {
      "role": "user",
      "content": "打开抖音，连刷5个视频，给第4个视频点赞，第5个视频收藏"
    }
  ]
}'
```

- 这个API和 GPT 一样的格式，可以访问接口。

```java
# 交互模式
python main.py --base-url https://cb869967ef619cf1-8000.cn-south-1.gpu-instance.ppinfra.com/v1 --model "autoglm-phone-9b"

# 指定模型端点
python main.py --base-url https://cb869967ef619cf1-8000.cn-south-1.gpu-instance.ppinfra.com/v1 "打开美团搜索附近的火锅店"

# 使用英文 system prompt
python main.py --lang en --base-url https://cb869967ef619cf1-8000.cn-south-1.gpu-instance.ppinfra.com/v1 "Open Chrome browser"

# 列出支持的应用
python main.py --list-apps
```

- 根据你部署的模型, 设置 --base-url 和 --model 参数，之后本地就可以验证模型了。

### 3. ADB (Android Debug Bridge) - 电脑安装

#### 3.1 安装环境

1. 下载官方 ADB [安装包](https://developer.android.com/tools/releases/platform-tools?hl=zh-cn)，并解压到自定义路径，地址：[https://developer.android.com/tools/releases/platform-tools?hl=zh-cn](https://developer.android.com/tools/releases/platform-tools?hl=zh-cn)
2. 配置环境变量

- MacOS 配置方法：在 `Terminal` 或者任何命令行工具里

```
# 假设解压后的目录为 ~/Downlaods/platform-tools。如果不是请自行调整命令。
export PATH=${PATH}:~/Downloads/platform-tools
```

- Windows 配置方法：可参考 [第三方教程](https://blog.csdn.net/x2584179909/article/details/108319973) 进行配置。

#### 3.2 验证脚本

```java
(base) fuzhengwei@ZBMac-GV47H1GXD Open-AutoGLM % adb devices
List of devices attached
94343646   device

(base) fuzhengwei@ZBMac-GV47H1GXD Open-AutoGLM % 
```

- 此时你的电脑USB，链接了手机，会显示出设备ID（如果没显示，检查下数据线等）

### 4. Android 7.0+ 的设备或模拟器

#### 4.1 开启调试模式

1. 开发者模式启用：通常启用方法是，找到 `设置-关于手机-版本号` 然后连续快速点击 10 次左右，直到弹出弹窗显示“开发者模式已启用”。不同手机会有些许差别，如果找不到，可以上网搜索一下教程。

2. USB 调试启用：启用开发者模式之后，会出现 `设置-开发者选项-USB 调试`，勾选启用

3. 部分机型在设置开发者选项以后, 可能需要重启设备才能生效. 可以测试一下: 将手机用USB数据线连接到电脑后, `adb devices` 查看是否有设备信息, 如果没有说明连接失败.

#### 4.2 安装 ADB Keyboard（用于文本输入）

下载 [安装包](https://github.com/senzhk/ADBKeyBoard/blob/master/ADBKeyboard.apk) 并在对应的安卓设备中进行安装。 注意，安装完成后还需要到 `设置-输入法` 或者 `设置-键盘列表` 中启用 `ADB Keyboard` 才能生效

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-15.png" width="250px"/>
</div>

安装包地址：[https://github.com/senzhk/ADBKeyBoard/blob/master/ADBKeyboard.apk](https://github.com/senzhk/ADBKeyBoard/blob/master/ADBKeyboard.apk) - `可以下载好传到手机也可以`

## 三、测试验证

### 1. 使用说明

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-14.png" width="750px"/>
</div>

- 整个的过程为，你通过命令调用LLM Phone，之后模型返回的结果，通过 ADB 方式，调用`调试模式`的安卓机，完成各项 APP 应用的操作。

### 2. 支持应用

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-13.png" width="750px"/>
</div>

### 3. 使用效果

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-12.png" width="750px"/>
</div>

```java
python main.py --device-id 94343646 --base-url https://uu835267-800d-0124cb32.westc.gpuhub.com:8443/v1 --model "autoglm-phone-9b" "打开抖音，刷视频"
```

- `device-id 94343646` 就是 adb 列出来的设备ID，`base-url 是你的服务地址`，之后可以自行验证，测试各种 APP 的启动，使用等。
- 像是一些没有的预设的应用以及应用里的流程，他还会截图屏幕，自动分析和使用。也可以是打开其他APP，并执行一些列的流程操作。

## 四、其他资料

📢 **接下来**，在phone + agent 这个方向，将有越来越多的模型和产品。检索：[https://github.com/search?q=phone%20agent&type=repositories](https://github.com/search?q=phone%20agent&type=repositories)

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-phone-agent-17.png" width="750px"/>
</div>

- https://github.com/mobile-next/mobile-mcp
- https://device-farm.com/doc/
- https://droidrun.ai/
- https://github.com/CherryHQ/cherry-studio-app
- https://github.com/minitap-ai/mobile-use