---
title: apisix
lock: need
---

# Apisix 云原生架构的开源 API 网关

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

`Higress`、`SpringCloud Gateway`，再到今天这套 `Apisix` 小傅哥就把市面上非常常用的3套 API 网关服务就全部都展示给大家了。其实所有的 API 网关都有一个共同的目的，就是做统一的 API 管理，包括；协议转换、负载均衡、动态路由、灰度发布、服务熔断、统一认证等。

像是这些组件化的 API 网关，使用起来其实都挺容易的，就有点像是使用了一个可视化的 Nginx 一样。但这些 API 看着简单，但有时候没有一个不错的资料对照搭建、配置和使用，也是挺难下手的。所以小傅哥把这些内容，成体系的展示给你，让你可以对照学习使用。

- 官网：[https://apisix.apache.org/zh/](https://apisix.apache.org/zh/)
- 部署：[https://github.com/apache/apisix-docker](https://github.com/apache/apisix-docker) - 官网提供了 Docker 脚本，但也有一些注意事项才能使用
- 脚本：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-apisix](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-apisix) - 可执行使用的部署脚本

## 一、APISIX 介绍

这款老6API，是阿帕奇下的开源项目。最初由 [api7.ai](https://api7.ai/apisix) 创建，于 2019 年开源并捐献给 Apache 软件基金会，使服务全球一半 API 请求的愿景成为可能。自那时起，API7.ai一直投入最优秀的人才和资源来支持 Apache APISIX 及其社区，该社区由来自世界各地的数千名贡献者和用户加入。

Apache APISIX 是一个动态、实时、高性能的云原生 API 网关，提供了负载均衡、动态上游、灰度发布、服务熔断、身份认证、可观测性等丰富的流量管理功能。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-01.png" width="750px">
</div>

为什么选择 Apache APISIX?

- 简单易用的 Dashboard：Apache APISIX Dashboard 可以让用户尽可能直观、便捷地通过可视化界面操作 Apache APISIX。作为 Apache 软件基金会中持续迭代的开源项目，欢迎你随时提出新的想法。
- 友好的用户体验：Apache APISIX Dashboard 极大地满足了用户需求，不仅提供了清晰的组织架构以适配二次开发，而且可以借助插件编排能力释放想象力！
- 可视化配置：拒绝重复造轮子。借助 Apache APISIX 内置插件，可以在极短时间内创建灵活、可靠、高性能的网关。无需编写代码，只需在编辑器中拖拽插件、配置条件，便可通过可视化的方式打造专属的 API 管理系统。
- 极致的性能体验：Apache APISIX 基于 Radixtree Route 和 etcd 提供路由极速匹配与配置快速同步的能力。从路由到插件，所有的设计和实现都是为了极速性能和超低延迟。
- 阻拦恶意程序：Apache APISIX 提供了多款身份认证与接口验证的插件，我们将稳定、安全放在首位。

## 二、环境部署

- 云服务器：2c2g 最低，我是用的 2c8g 体验的。[https://yun.xfg.plus](https://yun.xfg.plus/) - 价格实惠。
- 基础环境：Docker、Portainer、Git 【在小傅哥的 bugstack.cn 路书中都有讲解安装和使用】

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-02.png" width="950px">
</div>

### 1. 脚本配置

官网中提供了 docker 的安装脚本，但不能直接使用，需要如图做下复制操作。小傅哥已经把这部分复制的内容，到`脚本`工程里了。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-03.png" width="450px">
</div>

- 你可以知道有这么一个地方，将来如果版本更新了，也可以做下配置操作。

### 2. 可用脚本 - 准备好的

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-04.png" width="650px">
</div>

- 这个是小傅哥帮你准备好的脚本，可以直接安装使用。
- 这里涉及了 docker、portainer 的使用，在《[bugstack.cn](https://bugstack.cn) 路书》中，已经完整配置提供，可以查看。

## 三、网关配置

- 网关的登录密码是在 dev-ops/apisix-dashboard/conf.yaml 中配置的。默认的是 `admin/admin`
- 另外注意 conf.yml 中 etcd:2379 的配置，这里从 127.0.0.1 调整为 etch 应用名称。这样能保证在同一个 docker 配置的网络下，可以直接连接使用。如果是分布式部署，则要改为具体的IP。
- 这套按照学习脚本中提供了 web1、web2 两个 nginx 的配置，可以测试使用。

### 1. 访问网关

地址：[http://127.0.0.1:9093/](http://127.0.0.1:9093/)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-05.png" width="450px">
</div>

- 访问后就可以看到左侧的目录，可以配置；服务、上游、路由，以及插件等功能。
- 路由是入口，从路由可以访问到配置的服务，服务是每个应用的访问地址，可以配置权重轮训和一致性哈希。插件主要是提供的是；身份验证、安全防护、流量控制、无服务架构、可观测性功能。

### 2. 服务配置

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-06.png" width="850px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-07.png" width="850px">
</div>

- 配置后，一直点击就可以了。

### 3. 配置路由

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-08.png" width="750px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-09.png" width="750px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-10.png" width="750px">
</div>

### 4. 插件配置

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-11.png" width="750px">
</div>

- 你可以按需测试各种插件，还可以通过编排让各个要执行的插件串联起使用。

## 四、服务验证

地址：[http://192.168.1.105:9080/api](http://192.168.1.105:9080/api)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-apisix-12.png" width="750px">
</div>

- 刷新网关接口，不停的刷新，会看到接口访问值的变化。
