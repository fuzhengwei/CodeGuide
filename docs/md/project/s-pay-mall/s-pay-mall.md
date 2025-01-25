---
title: 小型支付电商系统
lock: no
---

# 小型支付电商系统 - 一套项目2套架构开发（MVC+DDD）

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>项目：[https://t.zsxq.com/3X9GA](https://t.zsxq.com/3X9GA)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

🌻 在小傅哥带大家做的众多实战项目中，有些伙伴喜欢看 `DDD` 因为面试有的讲，有些伙伴想做 `MVC` 因为虽然知道 `DDD` 有亮点但初次接触怕学起来难。所以，这次我要一套项目，写出两套架构开发。你可以对比着学习，看看不同架构（`MVC&DDD`）如何设计和编码！

<div align="center">
    <img src="https://bugstack.cn/images/article/project/business-behavior-monitor/business-behavior-monitor-01.png" width="150px">
</div>

**那咱们做什么项目呢？🤔**

这是一套`小型的支付电商系统`，提取实际生产中核心的真实模块作为咱们的开发需求，同时也是面试中最为常问的流程。包括；`如何微信扫码鉴权登录` + `模板消息通知`、`怎么做支付宝交易打通`、`商品支付掉单如何处理`、`相关的任务补偿怎么操作`等。把这些需求分别通过 MVC 架构、DDD 架构，进行设计实现。让学习的伙伴，对照出不同架构的设计思路和开发差异，即完成业务需求，也提高编程架构思维。—— 同龄人的差异，就是你比别人站的高的时候，略微出手，就是那个赛道的将相王侯 👍🏻！

两套架构工程的代码已经写完啦（录制视频中）！接下来小傅哥就来介绍下这套项目，你能获得的技术知识。

>文末有加入学习方式，还有优惠券可以使用。先到先得！

## 一、能学到啥

这是一套完整具备核心链路的小型项目，我们不在同类编码上反复重复，只关注核心链路。所以你可以花费很少的时间，积累丰富的架构和编程经验。在这套内容学习中，积累核心技术的运用，包括；

- 【前端】熟练使用，简单 HTML、DIV、CSS，对扫码登录、商品下单页面的构建。
- 【前端】掌握 fetch 方式对后端接口的调用，处理相关的逻辑数据。
- 【后端】熟练搭建 MVC 工程项目、理解各个分层模块作用，对 MVC 的设计方法有清楚的认识。
- 【后端】熟练搭建 DDD 工程项目、以及 DDD 脚手架搭建项目。并对 DDD 设计方法有清楚的认知。
- 【后端】理解 DDD 架构设计思维，这部分会有大量的内容进行讲解。再结合后续的实战，会对架构有更深入的认识。
- 【后端】熟练掌握 Spring、SpringBoot、MyBatis 等开发框架技术，并对框架源码所提供的扩展接口具备运用能力。
- 【后端】熟练使用模板设计模式，对商品下单的流程拆解和实现。
- 【后端】深度理解登录、支付、下单，全流程的核心设计和实现，而不是那种CRUD学习个DEMO，我们对接真实支付！
- 【运维】熟练使用 Docker 在本地和服务端的配置和部署应用，以及在本地构建前后端镜像。
- 【运维】熟练掌握 Git、GitCode，对工程代码的管理，推送、拉取、切换分支、合并代码等操作。
- 【运维】熟练使用 Nginx 配置转发服务，给前端应用在 Nginx 进行部署。

此外，小傅哥会把系统开发过程中的思考、设计、编码，录制⏺成完整的视频，让大家可以学习到的更多、更细、更深！

## 二、项目介绍

本次项目是一个包括 `前后端 + Dev-Ops` 且小型的综合实战项目，基于 SpringBoot、MyBatis、Nginx、Docker、微信公众号、支付宝沙箱等开发的项目。非常适合小白伙伴和有DDD学习诉求的伙伴上手！

本次项目铺设出来的内容并不大，但具备详细的核心流程，你可以通过一条完整链路学习到 MVC 和 DDD 的开发设计与编码差异。这是非常重要的。

### 1. 核心流程

<div align="center">
    <img src="https://bugstack.cn/images/article/project/s-pay-mall/s-pay-mall-0-01.png" width="800px">
</div>

### 2. 项目工程

<div align="center">
    <img src="https://bugstack.cn/images/article/project/s-pay-mall/s-pay-mall-0-02.png" width="600px">
</div>

- 一套项目需求，用两套架构开发。小傅哥，是真的想帮你提高架构思维！
- 我会在全程视频手把手的编码过程中，为你讲解 MVC 与 DDD 的设计，它们之间的对象设计，思维方式，编码结构。

### 3. 流程设计

#### 3.1 登录流程

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ddd-scene-solution/xfg-dev-tech-weixin-login-02.png" width="600px">
</div>

#### 3.2 下单流程

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ddd-scene-solution/alipay-sandbox-13.png?raw=true" width="600px">
</div>

### 4. 运行效果

<div align="center">
    <img src="https://bugstack.cn/images/article/project/s-pay-mall/s-pay-mall-0-03.png" width="800px">
</div>

### 5. 运行日志

```java
24-08-04.11:03:13.922 [http-nio-8092-exec-3] INFO  LoginController        - 生成微信扫码登录 ticket gQGq8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyT01QWXBJTDBjckcxUlFZbWhDMUgAAgR0765mAwQAjScA
24-08-04.11:11:00.291 [http-nio-8092-exec-7] INFO  WeixinPortalController - 接收微信公众号信息请求or0Ab6ivwmypESVp_bYuk92T6SvU开始 <xml><ToUserName><![CDATA[gh_e067c267e056]]></ToUserName>
<FromUserName><![CDATA[or0Ab6ivwmypESVp_bYuk92T6SvU]]></FromUserName>
<CreateTime>1722741062</CreateTime>
<MsgType><![CDATA[event]]></MsgType>
<Event><![CDATA[TEMPLATESENDJOBFINISH]]></Event>
<MsgID>3576043305420816385</MsgID>
<Status><![CDATA[success]]></Status>
</xml>
24-08-04.11:11:00.305 [http-nio-8092-exec-7] INFO  WeixinPortalController - 接收微信公众号信息请求or0Ab6ivwmypESVp_bYuk92T6SvU完成 <xml><ToUserName><![CDATA[gh_e067c267e056]]></ToUserName>
<FromUserName><![CDATA[or0Ab6ivwmypESVp_bYuk92T6SvU]]></FromUserName>
<CreateTime>1722741062</CreateTime>
<MsgType><![CDATA[event]]></MsgType>
<Event><![CDATA[TEMPLATESENDJOBFINISH]]></Event>
<MsgID>3576043305420816385</MsgID>
<Status><![CDATA[success]]></Status>
</xml>
24-08-04.11:11:12.374 [http-nio-8092-exec-9] INFO  LoginController        - 生成微信扫码登录 ticket gQGY8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyeGpIYm8yTDBjckcxUmktbXhDMU8AAgRS8a5mAwQAjScA
24-08-04.11:11:18.541 [http-nio-8092-exec-1] INFO  AliPayController       - 商品下单，根据商品ID创建支付单开始 userId:or0Ab6ivwmypESVp_bYuk92T6SvU productId:or0Ab6ivwmypESVp_bYuk92T6SvU
24-08-04.11:11:18.581 [http-nio-8092-exec-1] INFO  HikariDataSource       - HikariPool-1 - Starting...
24-08-04.11:11:18.778 [http-nio-8092-exec-1] INFO  HikariDataSource       - HikariPool-1 - Start completed.
24-08-04.11:11:19.177 [http-nio-8092-exec-1] INFO  AbstractOrderService   - 创建订单-完成，生成支付单。userId: or0Ab6ivwmypESVp_bYuk92T6SvU orderId: 3700032384239341 payUrl: <form name="punchout_form" method="post" action="https://openapi-sandbox.dl.alipaydev.com/gateway.do?charset=utf-8&method=alipay.trade.page.pay&sign=ThasDwUrX%2F%2BY6cnC3***&version=1.0&app_id=9021000132689924&sign_type=RSA2&timestamp=2024-08-04+11%3A11%3A19&alipay_sdk=alipay-sdk-java-4.38.157.ALL&format=json">
<input type="hidden" name="biz_content" value="{&quot;out_trade_no&quot;:&quot;3700032384239341&quot;,&quot;total_amount&quot;:&quot;1.68&quot;,&quot;subject&quot;:&quot;测试商品&quot;,&quot;product_code&quot;:&quot;FAST_INSTANT_TRADE_PAY&quot;}">
<input type="submit" value="立即支付" style="display:none" >
</form>
<script>document.forms[0].submit();</script>
24-08-04.11:11:19.178 [http-nio-8092-exec-1] INFO  AliPayController       - 商品下单，根据商品ID创建支付单完成 userId:or0Ab6ivwmypESVp_bYuk92T6SvU productId:100010090091 orderId:3700032384239341
```

## 三、项目大纲

**课程地址**：[https://t.zsxq.com/3X9GA](https://t.zsxq.com/3X9GA)

- 第1部分：架构理论
  - 第1节：DDD 架构概念
  - 第2节：DDD 建模方法
  - 第3节：DDD 工程模型（含 MVC 对比）
- 第2部分：需求设计
  - 第1节：小型支付商城需求设计
  - 第2节：工程四色建模设计
  - 第3节：库表设计
- 第3部分：功能实现 - MVC
  - 第1节：MVC 工程框架搭建 + 基础配置 + Git 使用
  - 第2节：微信公众号鉴权
  - 第3节：登录功能设计实现
  - 第4节：商品下单
  - 第5节：对接支付
  - 第6节：支付回调处理
- 第3部分：功能实现 - DDD
  - 第1节：DDD 工程框架搭建 + 基础配置 + Git 使用
  - 第2节：DDD 重构，微信公众号鉴权
  - 第3节：DDD 重构，登录功能设计实现
  - 第4节：DDD 重构，商品下单
  - 第5节：DDD 重构，对接支付
  - 第6节：DDD 重构，支付回调处理
- 第4部分：开发运维
  - 第1节：natapp 内网穿透
  - 第2节：微信公众号，测试平台申请
  - 第3节：支付宝沙箱申请
  - 第4节：发布上线

---

课程包括；视频、小册、1 对 1 答疑解惑、专属VIP项目交流群，并且提供简历编写模板结构的一条龙🐲服务。让你学习后，直接拉开与还在玩具项目其他人的差距，面试脱颖而出提高竞争力！！！
