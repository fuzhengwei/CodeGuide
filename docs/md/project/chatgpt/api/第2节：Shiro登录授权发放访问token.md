---
title: 第2节：Shiro登录授权发放访问token
pay: https://t.zsxq.com/0d1nGd9yJ
---

# 《ChatGPT 微服务应用体系构建》 - chatgpt-api 第2节：Shiro登录授权发放访问token

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

- **本章难度**：★★★☆☆
- **本章重点**：通过 SpringBoot 整合 Shiro + JWT 进行登录验证，发放使用API的准入Token信息。
- **课程视频**：[https://t.zsxq.com/0dpw1gaHJ](https://t.zsxq.com/0dpw1gaHJ)

## 一、本章诉求

以用户使用 OpenAI 接口，如；`http://localhost/api` 时，需要根据用户身份标识做一些访问的验证和限定。最直接就是在使用 api 的时候把用户的账号和密码一同和访问 api 传递过来，如；`http://localhost/api?userId=xfg&password=123` 但这样就把用户的密码信息给泄漏了，是非常不安全的。

所以我们本章节需要根据用户的账密，先通过登录验证的方式，发放一个 token，之后用户再使用这个 token 配置到链接后面使用。如；`http://localhost/api?token=xxxxx` 这样就安全多了。—— 一般 token 是配置到 http 请求头信息中，但这里为了更加方便用户传递参数，所以这样处理了。

## 二、流程设计

整个流程为；以用户访问一个登录接口，服务端使用用户的账号和密码进行验证，验证通过后发放 Token，之后再使用 Token 访问 OpenAI 地址。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/chatgpt/chatgpt-api-02-01.png?raw=true" width="750px">
</div>

在本章节的实现中，需要在 SpringBoot 工程中引入进来 Shiro、JWT，进行处理。读者可以在代码验证通过后，根据 Shiro、JWT 这样的关键字在 [ChatGPT](https://itedus.cn) 中进行检索，补充基础知识。