---
title: 第1-3节：SSH连接云服务器
pay: https://t.zsxq.com/8glZo
---

# 《WaLiSSH - AI Shell 智能终端》第1-3节：SSH连接云服务器

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/m54kV](https://t.zsxq.com/m54kV)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、本章诉求

从这一节开始，小傅哥开始带着大家构建 AI Shell 运行时智能体。你可以先简单的理解为运行时智能体就是对用户的请求进行，意图识别（LLM + Prompt + 上下文），根据具体的场景诉求，与相关的 mcp、skills、tool、cli，进行交互操作，直至完成用户请求。

所以，我们要做 AI Shell 就需要先把与服务器的 Shell 能力建立起来，之后在把 Shell 能力包装为工具和相应的提示词，让 LLM 大模型可以识别到。这样就完成了最简单的 AI Shell 能力。

<div align="center">
	<img src="https://bugstack.cn/images/article/project/walissh/walissh-1-3-01.png" width="850px"/>
</div>

其实这部分内容都是通用的，当你完整学习完一套流程后，那么你就可以做 AI MySQL、AI Redis，也可以是做 AI Coding，也包括像是互联网公司中最常见的做 AI 智能客服。

那么，这一节，我们先来处理下 SpringBoot 工程如果完成 Shell 能力的对接。

>👩🏻‍🏫敲黑板：这一节的代码量不大，核心也不只是"写出代码"，更重要的是理解 DDD 架构下，**Domain 层 Port 接口与 Infrastructure 层实现的分离**。掌握了这个设计思想，以后无论底层换成什么 SSH 库，Domain 层的代码一行都不用改！

## 二、技术选型

SSH 对接，使用 **JSch**——纯 Java 实现的 SSH2 协议库，成熟稳定，后续还会基于它实现命令执行和 SFTP 文件传输。（这个不局限，你可以在检索其他的 SSH 协议库，多方面对比）

- 官网：[http://www.jcraft.com/jsch/](http://www.jcraft.com/jsch/)
- 代码：[https://github.com/mwiede/jsch](https://github.com/mwiede/jsch)

### 1. SSH 协议基础

SSH（Secure Shell）是一种加密的网络传输协议，用于在不安全的网络中安全地远程登录和执行命令。它就像一条加密的"隧道"🚇，你和服务器之间的所有数据都在这条隧道里安全传输，外人无法窃听或篡改。

SSH 连接支持两种认证方式：

| 认证方式 | 优点 | 缺点 | 适用场景 |
|---------|------|------|---------|
| **密码认证** | 简单直观，无需额外配置 | 安全性相对较低，需要每次输入密码 | 快速测试、临时连接 |
| **密钥认证** | 安全性高，支持免密登录 | 需要生成和管理密钥对 | 生产环境、自动化运维 |

在我们的实现中，两种认证方式都支持，互斥使用——优先使用密钥，没有密钥则走密码。

### 2. JSch 库介绍

JSch 是 Java Secure Channel 的缩写，提供了完整的 SSH2 客户端功能：

- 端口转发（本地/远程/动态）
- 文件传输（SFTP）
- 执行远程命令
- X11 转发

在我们的项目中，主要使用它的三个能力：

1. 建立 SSH 会话（`Session`）—— 本节实现
2. 执行远程命令（`ChannelExec`）—— 本节测试的时候验证，使用 IntelliJ IDEA
3. 创建 SFTP 通道（`ChannelSftp`）—— 后续章节使用