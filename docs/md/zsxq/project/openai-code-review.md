---
title: OpenAI 代码自动评审组件
lock: no
---

# OpenAI 代码自动评审组件 —— 小傅哥写的代码，会自动评审啦！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>星球：[https://t.zsxq.com/gYEVX](https://t.zsxq.com/gYEVX) - 课程入口

>沉淀、分享、成长，让自己和他人都能有所收获！

大家好，我是技术UP主小傅哥。

👬🏻哥们，你写的代码有没有Bug？有Bug？那`小卡拉米`测试没覆盖到，都整出线上事故了😂！其实强如大厂架构师，开发的代码也会存在一些遗漏的地方，所以要有代码评审、测试、预发验证等环境来保证交付质量。但小傅哥只有自己，还维护了  [bugstack.cn](https://bugstack.cn)  社群，上百个工程代码，也花点钱雇个人评审代码？—— **但这玩意，花钱，不行！我得整点技术活！**

<div align="center">
    <img src="https://bugstack.cn/images/article/project/openai-code-review/openai-code-review-01.png" width="550px">
</div>

**工欲善其事，必先利其器。**

其实我想要的，就是这么简单！当我提交合并分支的代码，则触发代码评审，并写入评审日志文件。完成后发送公众号模板消息通知，点击<详情>查看评审细节。这样我就知道本次开发的代码是否有问题啦，可以说是美滋滋！

其实这样一套东西，不只是小傅哥需要，就连企业中也是非常有需要的。通过自动化评审来辅助人工评审，可以把代码的交付质量拉倒一个更高的层次，也能尽可能的减少线上事故。~~没有事故 == 开猿节流~~

<div align="center">
    <img src="https://bugstack.cn/images/article/project/openai-code-review/openai-code-review-08.png" width="750px">
</div>

> 👣 接下来，小傅哥就来介绍下这套组件的配置使用，同时想学习这样组件开发的伙伴也可以加入小傅哥的社群。

## 一、使用方法

本套组件是小傅哥基于 `GitHub Actions` + `OpenAI(ChatGLM)` + `Git/GitHub` + `公众号模板消息` 串联出从代码提交获取通知，Git 检出分支变化，在使用 OpenAI 进行代码和写入日志，再发送消息通知完成整个链路。

好，那接下来，小傅哥就带着你做下 OpenAI Code Review 的配置，整体配置如下图；—— 下面👇🏻会告诉你在哪配置。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/openai-code-review/openai-code-review-02.png" width="850px">
</div>

### 1. 申请 ChatGLM

- CHATGLM_APIKEYSECRET： [https://open.bigmodel.cn/usercenter/apikeys](https://open.bigmodel.cn/usercenter/apikeys) - 申请方便&还挺好用。也可以对接其他模型。
- CHATGLM_APIHOST：https://open.bigmodel.cn/api/paas/v4/chat/completions

### 2. 申请 GitHub 仓库

组件是基于 Github Actions 实现的，所以要提供一个你的 Github 工程库和一个评审 Github 工程库写入日志的日志库。如果你有其他代码库，也可以按照对应代码库的 CI/CD 标准进行实现。

- 工程库：[https://github.com/xfg-studio-project/openai-code-review-test](https://github.com/xfg-studio-project/openai-code-review-test) - 你创建一个自己的，并提交代码。
- 日志库：[https://github.com/xfg-studio-project/openai-code-review-log](https://github.com/xfg-studio-project/openai-code-review-log) - 你创建一个自己的。

### 3. 申请 GitHub Token

地址：[https://github.com/settings/tokens](https://github.com/settings/tokens)

<div align="center">
    <img src="https://bugstack.cn/images/article/project/openai-code-review/openai-code-review-03.png" width="650px">
</div>

- 创建后，保存生成的 Token，用于配置到 GitHub Actions 参数中

### 4. 微信公众号配置

- 申请地址 [https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index](https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index)

<div align="center">
    <img src="https://bugstack.cn/images/article/project/openai-code-review/openai-code-review-04.png" width="650px">
</div>

- 这个测试公众号等同于企业公众号，有对应的模板消息。
- 申请后，你就会获得 appID、appsecret、tourse - 就是谁关注了公众号，就会展示一个分配的微信号，推送模板消息就是给这个用户推送。
- 模板消息，自己新建一个。之后就获得ID。消息格式如下；

```java
项目：{{repo_name.DATA}} 分支：{{branch_name.DATA}} 作者：{{commit_author.DATA}} 说明：{{commit_message.DATA}}
```

### 5. GitHub Actions 配置

#### 5.1 配置参数

地址：[https://github.com/xfg-studio-project/openai-code-review-test/settings/secrets/actions](https://github.com/xfg-studio-project/openai-code-review-test/settings/secrets/actions) - 换成你的项目工程，进入到 Setting -> Secrets and variables -> Actions -> Repository secrets -> New repository secret

<div align="center">
    <img src="https://bugstack.cn/images/article/project/openai-code-review/openai-code-review-05.png" width="650px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/project/openai-code-review/openai-code-review-06.png" width="650px">
</div>

| Name                 | Secret                                                       |
| -------------------- | ------------------------------------------------------------ |
| CHATGLM_APIHOST      | https://open.bigmodel.cn/api/paas/v4/chat/completions        |
| CHATGLM_APIKEYSECRET | `39580e34e175019c230fdd519817b381.F*****pzqiRDcAk` - 使用你的 |
| CODE_REVIEW_LOG_URI  | [https://github.com/xfg-studio-project/openai-code-review-log](https://github.com/xfg-studio-project/openai-code-review-log) - 使用你的 |
| CODE_TOKEN           | `ghp_KWBsnzwoQR4OXO4o3XjIJjVU****GsS1` - 使用你的            |
| WEIXIN_APPID         | `wx5a228ff69e2****1f` - 使用你的                             |
| WEIXIN_SECRET        | `0bea03aa1310bac050a******8703928` - 使用你的                |
| WEIXIN_TEMPLATE_ID   | `l2HTkntHB71R4NQTW77UkcqvSOIFqE_bss1DAVQSybc`  - 使用你的    |
| WEIXIN_TOUSER        | `or0Ab6ivwmypESVp_bYuk92T****` - 使用你的                    |


#### 5.2 配置脚本

<div align="center">
    <img src="https://bugstack.cn/images/article/project/openai-code-review/openai-code-review-07.png" width="650px">
</div>

```java
name: OpenAiCodeReview

on:
  push:
    branches:
      - '*'
  pull_request:
    branches:
      - '*'

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout repository
        uses: actions/checkout@v2
        with:
          fetch-depth: 2  # 检出最后两个提交，以便可以比较 HEAD~1 和 HEAD

      - name: Set up JDK 11
        uses: actions/setup-java@v2
        with:
          distribution: 'adopt'
          java-version: '11'

      - name: Create libs directory
        run: mkdir -p ./libs

      - name: Download openai-code-review-sdk JAR
        run: wget -O ./libs/openai-code-review-sdk-1.1.jar https://github.com/xfg-studio-project/openai-code-review-log/releases/download/v1.1/openai-code-review-sdk-1.1.jar

      - name: Get repository name
        id: repo-name
        run: echo "REPO_NAME=${GITHUB_REPOSITORY##*/}" >> $GITHUB_ENV

      - name: Get branch name
        id: branch-name
        run: echo "BRANCH_NAME=${GITHUB_REF#refs/heads/}" >> $GITHUB_ENV

      - name: Get commit author
        id: commit-author
        run: echo "COMMIT_AUTHOR=$(git log -1 --pretty=format:'%an <%ae>')" >> $GITHUB_ENV

      - name: Get commit message
        id: commit-message
        run: echo "COMMIT_MESSAGE=$(git log -1 --pretty=format:'%s')" >> $GITHUB_ENV

      - name: Print repository, branch name, commit author, and commit message
        run: |
          echo "Repository name is ${{ env.REPO_NAME }}"
          echo "Branch name is ${{ env.BRANCH_NAME }}"
          echo "Commit author is ${{ env.COMMIT_AUTHOR }}"
          echo "Commit message is ${{ env.COMMIT_MESSAGE }}"

      - name: Run Code Review
        run: java -jar ./libs/openai-code-review-sdk-1.1.jar
        env:
          # Github 配置；GITHUB_REVIEW_LOG_URI「https://github.com/xfg-studio-project/openai-code-review-log」、GITHUB_TOKEN「https://github.com/settings/tokens」
          GITHUB_REVIEW_LOG_URI: ${{ secrets.CODE_REVIEW_LOG_URI }}
          GITHUB_TOKEN: ${{ secrets.CODE_TOKEN }}
          COMMIT_PROJECT: ${{ env.REPO_NAME }}
          COMMIT_BRANCH: ${{ env.BRANCH_NAME }}
          COMMIT_AUTHOR: ${{ env.COMMIT_AUTHOR }}
          COMMIT_MESSAGE: ${{ env.COMMIT_MESSAGE }}
          # 微信配置 「https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index」
          WEIXIN_APPID: ${{ secrets.WEIXIN_APPID }}
          WEIXIN_SECRET: ${{ secrets.WEIXIN_SECRET }}
          WEIXIN_TOUSER: ${{ secrets.WEIXIN_TOUSER }}
          WEIXIN_TEMPLATE_ID: ${{ secrets.WEIXIN_TEMPLATE_ID }}
          # OpenAi - ChatGLM 配置「https://open.bigmodel.cn/api/paas/v4/chat/completions」、「https://open.bigmodel.cn/usercenter/apikeys」
          CHATGLM_APIHOST: ${{ secrets.CHATGLM_APIHOST }}
          CHATGLM_APIKEYSECRET: ${{ secrets.CHATGLM_APIKEYSECRET }}
```

- 把以上脚本粘贴到你的 GitHub Actions 中，之后保存。
- 接下来你提交代码就会自动触发代码评审啦。💐 赶紧玩一下吧！看看智能的AI评审能力！

>对于这里实现的内容，小傅哥会通过代码实践课程，手把手的给你讲解。从方案设计、代码串联、重构编码，一步步的带着你全部搞懂！

## 二、能学到啥

公司里其实也总有人做一些这样的创新组件，这些东西和业务没关系，也不是个人的KPI压力，但往往做这样的东西的伙伴就有更多的晋升资格和加薪待遇。因为我就是这个人 哈哈哈 死鬼！

这样的组件项目，本身就是一种技术创新应用来解决实际业务问题，提高交付质量。而不是那种野蛮的一遍遍重写RPC框架。所以学习一套这样的东西是非常有用的。那么在这套东西你可以学习到；

- 一整套的设计方案分析和相应的技术问题处理手段，这个思考方式很重要。
- GitHub Actions 的使用机制，它的一些超级强大的用途和使用方式。
- OpenAI ChatGLM 对接使用，用AI来做代码评审。
- 微信公众号的模板配置和API对接使用，运用 API 完成消息触达。
- 通过代码完成 Git 命令使用，检出代码分支，在通过 OPenAI 完成代码评审。整个过程先使用大家常用的流水账方式开发代码，再带着大家重构设计，让代码变得更加清晰。
- 打包 Jar 包，并把相关的组件一起打包，之后让 GitHub Actions 分别通过 Main 函数调用、mvn 构建使用、Jar 下载使用，多种方式学习整个过程（而不是只最终的结果，过程非常重要）。

> 初次之外，小傅哥还会在整个过程教会你 IntelliJ IDEA 操作技巧、快捷键使用、编码思维等。赶紧加入学习下！

## 三、加入学习

**星球「码农会锁」**  实战项目中有非常多的运用。还包括：大营销、OpenAI 应用、API网关、Lottery抽奖、IM通信、SpringBoot Starter 组件开发、IDEA Plugin 插件开发、支付SDK、动态线程组件、透视业务监控等，并还有开源项目学习。

如果大家希望通过做有价值的编程项目，提高自己的编程思维和编码能力，可以加入小傅哥的【星球：码农会锁】。加入后解锁🔓所有往期项目，还可以学习后续新开发的项目。

>[🧧加入学习](https://bugstack.cn/md/zsxq/other/join.html) 这样一套项目，放在一些平台售卖，一个至少都是几百块。但小傅哥的星球，只需要100多，就可以获得全部的学习项目！

**加入星球**：下载`星球APP`，从星球【课程入口】进入。里面有完整的学习指引，包括；使用说明、代码仓库、专属项目群、学习路线、往期项目。本项目地址：[https://t.zsxq.com/gYEVX](https://t.zsxq.com/gYEVX)
