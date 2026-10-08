---
title: 第4-3节：用户认证与JWT
pay: https://t.zsxq.com/SyaaJ
---

# 《WaLiOffice - AI Agent 智能办公平台》第4-3节：用户认证与JWT

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

大家好，我是技术UP主小傅哥。

上两节我们完成了配置管理和附件处理，系统已经能跑起来了。但一个真实的办公平台不可能谁都能用，我们需要给 WaLiOffice 加上一道"门"——**用户认证**。

这节我们就来搞定：用户怎么注册、怎么登录、登录后 Token 怎么生成、后续请求怎么验证身份。这套机制在几乎所有 Web 应用里都是标配，学完之后你换个项目也能直接用。

## 一、本章诉求

1. **理解 JWT 原理**：Token 是什么、怎么生成、怎么验证、为什么比 Session 更适合水平扩展
2. **掌握密码安全存储**：密码不能明文存，要 bcrypt 哈希；验证时也不能比较明文
3. **实现 Axum 认证提取器**：一个 `AuthUser` 提取器，让受保护的路由直接拿到当前用户
4. **完成两种登录路径**：用户名密码登录、微信验证码登录（外部服务校验 + 自动注册）

## 二、技术背景：JWT 认证原理

在说代码之前，先给大家讲清楚 JWT 是什么。

### 2.1 什么是 Token？

我们日常上网会遇到两种身份验证方式：

**第一种：Session 派** —— 你登录成功，服务器给你发一个 Session ID（存 Cookie），后续请求带上这个 Cookie，服务器去数据库/内存查"这个 Session 对应哪个用户"。

这方式挺好的，但有个问题——**水平扩展时很麻烦**。你部署了两台服务器，用户 A 的请求打到 Server 1，用户 B 的请求打到 Server 2，Server 2 的内存里根本没有 Session 的数据，你得搞 Redis 共享 Session，或者给请求按用户做 sticky session。这些方案都增加了系统复杂度。

**第二种：Token 派（JWT）** —— 你登录成功，服务器用**密钥**签发一个 Token（本质是一个签名字符串），直接返回给你。后续请求你带上这个 Token，服务器用**同一把密钥**验证 Token 真伪，**不需要查 Session 存储**——Token 本身就携带了用户信息，验签通过即可信。

WaLiOffice 用的就是这种方式。

> 👩🏻‍🏫敲黑板：注意这里的说法是"**密钥**"而不是"私钥/公钥"。JWT 有两类签名算法：**HS256 是对称的**——签发和验证用同一把密钥；**RS256 是非对称的**——签发用私钥、验证用公钥。WaLiOffice 是单体应用，选 HS256 足够，还省了密钥分发。如果是微服务架构（签发服务和验证服务分开），才需要考虑 RS256。**对称密钥必须保密，泄露了任何人都能伪造 Token**。

### 2.2 JWT 结构

一个 JWT 长这样：

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyXzEyMyIsInVzZXJuYW1lIjoiYWRtaW4iLCJyb2xlIjoiYWRtaW4iLCJleHAiOjE3NjQ0MjQ0MDR9.Xk5yJ8vW2pQ6rT9sN4mL3cF1gH7oA0dE
```

它由三段 Base64 组成，用 `.` 分隔：

```
Header.Payload.Signature
```

- **Header**：声明类型（JWT）和签名算法（HS256）
- **Payload**：存放用户信息（sub=用户ID, username, role, exp=过期时间）
- **Signature**：用密钥对 Header + Payload 签名，防止篡改
