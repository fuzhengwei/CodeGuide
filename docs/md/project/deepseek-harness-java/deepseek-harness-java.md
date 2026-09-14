---
title: Java + DDD，1:1 复刻 Deepseek Harness 项目
lock: no
---

# Java + DDD，1:1 复刻 Deepseek Harness 项目

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>项目：[https://t.zsxq.com/kYcVt](https://t.zsxq.com/kYcVt)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

26 个内置工具（7 文件操作、6 Shell/终端、3 Agent、4 Job、2 Web……）、27 个领域模块、外加 MCP 与插件两套动态工具扩展点。这些玩意，**干了2周多**，`用 2.2 万行核心代码`，我把 DeepSeek Harness 原汁原味的复刻成了一套 Java DDD 架构版本（ClassLoader 加载插件）。`还好我的 DDD 功底扎实！🤨`

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-0-00.png" width="150px">
</div>

**DeepSeek Harness · Java Edition**

这是一个基于 **Java 17/21 + Spring Boot 3.3.x + DDD 六边形架构** 构建的 Agent Harness（智能体运行时基座）—— 该项目，不是说就模仿个 Deepseek Harness Chat UI 表皮，而是把；**模型调用、工具执行、会话事件、任务审批、插件生命周期、终端与工作区、工作流与后台作业**等，组织成可扩展的一体化运行时智能体，并自带可交互的 Web 控制台。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/deepseek-harness-java/deepseek-harness-java-01.png" width="750px">
</div>

为啥干一套 Java 版本的 `Deepseek Harness` ? 🤔

其实 Java 语言的 AI 框架也不少，但整个 AI 随着深度结合业务场景，也是在不断地设计新的架构形态，以此提高交付质量和效率。像是最早 SDK（Spring AI/LangChain） 解决 API 的对接，后来做 ADK（Google/LangGraph）解决 Agent 的开发。现在做 Harness 的插件化思维，又再更上一层，离业务更近的交付上，把通用的东西凝练成具备复用价值的框架结构。

商城、出行、外卖、履约、交易、信贷、支付、营销等等，这些 toc 业务的核心场景都是 Java 干的。这些业务也在大量的做；`智能客服`、`运营助手`、`营销投放`、`风控审批`、`履约调度`、`故障巡检`等，所以用 Java 直接做这些场景也是非常有必要的，而且做的规模更大后，也要解决分布式、微服务架构场景的问题。

>💐 好啦，接下来小傅哥就给大家介绍下这套项目，源码和文档教程也是附带一起给大家的。

## 一、项目介绍

`deepseek-harness-java` 是一个**单实例可运行的 Agent Harness**。提供了一个完整的运行时环境，让 LLM 驱动的智能体可以；

- 通过 **ReAct 循环**（Reason → Act → Observe）自主调用工具完成任务；
- 操作**文件系统、Shell、Web 搜索/抓取**等真实环境；
- 通过 **Java SPI 插件 / Node sidecar 插件 / MCP Server** 扩展能力；
- 接受**权限评估与人工审批**的治理约束；
- 将全过程以**事件溯源**方式落库，支持回放与审计。

>项目自带一个**原生 JS 实现的 Web 控制台**（无前端构建链），打开浏览器即可对话、管理工作区、审批任务、查看插件与模型。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/deepseek-harness-java/deepseek-harness-java-02.png" width="650px">
</div>

**项目地址**；[https://gitcode.net/KnowledgePlanet/deepseek-harness-java](https://gitcode.net/KnowledgePlanet/deepseek-harness-java) - 该项目目前开放在小傅哥的技术社群。

deepseek-harness-java 采用 **DDD 六边形（端口-适配器）架构**，以 Maven 多模块实现严格单向依赖：`App（SpringBoot 启动器）→ Trigger（19 个 C风格 REST Controller，只管协议/认证/路由）→ API（Facade + DTO 应用门面）→编排层，用策略树/责任链节点串联对话与任务两条主线）→ Domain（核心，27 个限界上下文，持有 React、ToolCallExecutor、任务审批链等实体/领域服务及全部 Port 端口接口）`，Infrastructure 反向实现 Domain 端口（LLM 网关、MySQL/H2 DAO、Shell/Node/MCP 适配器 提供不依赖 Spring 的轻量 SPI 契约供插件 JAR 复用——由此做到领域层零依赖具体技术：换数据库、换 LLM 供应商、换执行环境只需替换适配器，而 Agent 循环、会话事件、审批治理等核心业务逻辑完全不受影响。

### 1. 适用场景

| 本地智能体工作台 | 浏览器 UI、SSE 对话、Markdown 渲染、会话与工作区管理 |
| --- | --- |
| 场景 | 项目提供的能力 |
| Java 原生 Agent 运行时 | Spring Boot 装配、ReAct Loop、工具注册与执行、会话事件 |
| 企业内部扩展平台 | Java SPI 插件、Node sidecar 插件、MCP 工具适配、插件生命周期 API |
| 任务自动化系统 | 任务提交、队列、审批、目标、计划、待办、工作流、后台作业 |
| 可替换基础设施 | Domain 定义端口，Infrastructure 提供数据库、LLM、执行环境适配 |

> **定位说明**：它当前是**单实例 Agent 运行时**，不是开箱即用的分布式多租户 SaaS。生产化部署前仍需根据企业要求补充鉴权策略、审计、集群协调、可观测性与完整集成测试。

### 2. 特性一览

- 🤖 **Agent 对话**：阻塞式 `POST /api/agent/message` 与 SSE 流式 `POST /api/agent/stream`，支持取消、状态查询、`agentId` 会话复用
- 🔁 **ReAct 循环**：`ReactLoopAgent` 驱动模型输出 → 工具调用 → 结果回填 → 续步，单 turn 上限 50 步
- 🧰 **工具系统**：`fs_*` 文件工具、`shell_execute`、`web_search/web_fetch`、`ask_user_question`，统一 `ToolCallExecutor` + PRE/POST Hook
- 🧩 **双模式插件**：Java Native（进程内 `URLClassLoader` 隔离）与 Node Bridge（sidecar 进程 JSON-RPC），另支持 MCP stdio 工具
- 🛡️ **审批链路**：提交期权限矩阵评估 + 审批决策，高风险工具（Shell、写文件、插件、子进程）默认需人工审批
- 💾 **持久化**：会话/任务/目标/审批/模型设置落库；默认 MySQL，standalone profile 用 H2 文件库
- 🖥️ **Web 控制台**：原生 JS（约 3300 行）+ marked + DOMPurify + highlight.js，本地化加载无外网依赖
- 🐳 **Docker Compose**：一条命令拉起完整环境

## 二、启动体验

### 2.1 环境要求

| 软件 | 版本 | 必需性 |
| --- | --- | --- |
| JDK | 17+ | 必需 |
| Maven | 3.9+ | 构建必需 |
| Node.js | 18+ | 仅使用 Node Bridge Plugin 时必需 |
| MySQL | 5.7+ | 仅默认（MySQL）profile 需要，脚本在 `docs/dev-ops/mysql/sql` 下 |
| Docker | 24+ | 可选，用于 Compose 启动 |

### 2.2 克隆构建

```bash
git clone git@gitcode.net:KnowledgePlanet/deepseek-harness-java.git
cd deepseek-harness-java
```

- 下载后可以用 AI IDE 工具打开项目，辅助学习一波。推荐 [https://walicode.xiaofuge.cn/](https://walicode.xiaofuge.cn/)

### 2.3 启动项目

#### 2.3.1 本地启动

默认 `application.yml` 面向外部 MySQL（默认连 `127.0.0.1:3306/deepseek_harness_java`）。通过环境变量覆盖连接信息：

```bash
export SPRING_DATASOURCE_URL='jdbc:mysql://127.0.0.1:3306/deepseek_harness_java?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true'
export SPRING_DATASOURCE_USERNAME='root'
export SPRING_DATASOURCE_PASSWORD='your-db-password'
export DEEPSEEK_API_KEY='your-model-api-key'

java -jar deepseek-harness-java-app/target/deepseek-harness-java-app-1.0.0-SNAPSHOT.jar
```

- 下载后，用 IntelliJ IDEA 打开项目，导入库表后启动项目。脚本在 `docs/dev-ops/mysql/sql` 下

- 应用启动时自动执行  `docs/dev-ops/mysql/sql` ，确保 12 张核心表存在。

#### 2.3.2 云上部署（Docker）

##### 2.3.2.1 一键部署

```java
#!/usr/bin/env bash
set -euo pipefail

# 非 docker compose 一键启动脚本
# 等价来源：docs/dev-ops/docker-compose-app.yml
# 使用方式：
#   chmod +x start-app.sh
#   ./start-app.sh
#
# 可选环境变量：
#   DEEPSEEK_API_KEY=你的key ./start-app.sh

APP_NAME="deepseek-harness-java"
IMAGE="registry.cn-hangzhou.aliyuncs.com/xfg-studio/deepseek-harness-java:0.1.1"
NETWORK_NAME="my-network"
SERVER_PORT="8090"
DEEPSEEK_API_KEY="${DEEPSEEK_API_KEY:-sk-local}"

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "${SCRIPT_DIR}"

echo "[1/5] 准备数据目录..."
mkdir -p data plugins .dsh workspaces

echo "[2/5] 准备 Docker 网络：${NETWORK_NAME}"
if ! docker network inspect "${NETWORK_NAME}" >/dev/null 2>&1; then
  docker network create --driver bridge "${NETWORK_NAME}" >/dev/null
fi

echo "[3/5] 清理旧容器：${APP_NAME}"
if docker ps -a --format '{{.Names}}' | grep -qx "${APP_NAME}"; then
  docker rm -f "${APP_NAME}" >/dev/null
fi

echo "[4/5] 启动容器：${APP_NAME}"
docker run -d \
  --name "${APP_NAME}" \
  --restart on-failure \
  --network "${NETWORK_NAME}" \
  -p "${SERVER_PORT}:${SERVER_PORT}" \
  -e TZ=PRC \
  -e SERVER_PORT="${SERVER_PORT}" \
  -e DEEPSEEK_API_KEY="${DEEPSEEK_API_KEY}" \
  -e HARNESS_LLM_DEEPSEEK_BASE_URL="https://apis.itedus.cn/v1" \
  -e HARNESS_LLM_DEEPSEEK_DEFAULT_MODEL="gpt-5.5" \
  -v "${SCRIPT_DIR}/data:/app/data" \
  -v "${SCRIPT_DIR}/plugins:/app/plugins" \
  -v "${SCRIPT_DIR}/.dsh:/root/.dsh" \
  -v "${SCRIPT_DIR}/workspaces:/app/workspaces" \
  --log-driver json-file \
  --log-opt max-size=10m \
  --log-opt max-file=3 \
  "${IMAGE}"

echo "[5/5] 启动结果："
docker ps --filter "name=${APP_NAME}" --format 'table {{.Names}}\t{{.Image}}\t{{.Status}}\t{{.Ports}}'

echo ""
echo "启动完成。访问地址：http://127.0.0.1:${SERVER_PORT}"
echo "查看日志：docker logs -f ${APP_NAME}"
echo "停止服务：docker rm -f ${APP_NAME}"
```

- 首先，`HARNESS_LLM_DEEPSEEK_BASE_URL` 注意修改模型配置。
- 之后，复制脚本，粘贴到云服务器，一键启动部署。
- 最后，开始 8090 端口（在云服务器安装组里），完成后就可以访问了。

##### 2.3.2.1 docker compose 部署

**极简体验**

```
docker run -d --name dsh -p 8090:8090 registry.cn-hangzhou.aliyuncs.com/xfg-studio/deepseek-harness-java:0.1.5
```

**mysql部署**

```
docker run -d --name dsh -p 8090:8090 \
  -e DEEPSEEK_API_KEY=*** \
  -e SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/deepseek_harness_java?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true" \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=*** \
  registry.cn-hangzhou.aliyuncs.com/xfg-studio/deepseek-harness-java:0.1.5 \
  java -jar app.jar --spring.profiles.active=mysql
```

- 注意提前导入mysql

**完整脚本**

```bash
# docker-compose -f docker-compose-app.yml up -d
# 镜像名需修改为你自身系统的仓库名；使用项目根目录 Dockerfile 构建推送：
#   docker build -t registry.cn-hangzhou.aliyuncs.com/fuzhengwei/deepseek-harness-java:1.0 .
#   docker push registry.cn-hangzhou.aliyuncs.com/fuzhengwei/deepseek-harness-java:1.0
version: '3.8'
services:
  deepseek-harness-java:
    image: registry.cn-hangzhou.aliyuncs.com/xfg-studio/deepseek-harness-java:0.1.5
    container_name: deepseek-harness-java
    restart: on-failure
    ports:
      - "8090:8090"
    environment:
      - TZ=PRC
      - SERVER_PORT=8090
      # LLM 网关配置
      - DEEPSEEK_API_KEY=${DEEPSEEK_API_KEY:-sk-local}
      - HARNESS_LLM_DEEPSEEK_BASE_URL=https://apis.itedus.cn/v1
      - HARNESS_LLM_DEEPSEEK_DEFAULT_MODEL=gpt-5.5
      # 数据卷说明：standalone(H2) 库文件写入 ./data，插件/工作区/.dsh 需持久化
      # 如需切换 MySQL profile（默认 profile），放开 command 覆盖并追加数据源配置：
      # command: ["java", "-jar", "app.jar", "--spring.profiles.active=mysql"]
      # - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/deepseek_harness_java?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true
      # - SPRING_DATASOURCE_USERNAME=root
      # - SPRING_DATASOURCE_PASSWORD=123456
    volumes:
      - ./data:/app/data
      - ./plugins:/app/plugins
      - ./.dsh:/root/.dsh
      - ./workspaces:/app/workspaces
    logging:
      driver: "json-file"
      options:
        max-size: "10m"
        max-file: "3"
    networks:
      - my-network

networks:
  my-network:
    driver: bridge

```

- `docker run -d --name dsh -p 8090:8090 registry.cn-hangzhou.aliyuncs.com/xfg-studio/deepseek-harness-java:0.1.5`
- 如果你对项目进行二次迭代，那么可以在开发后，重新构建镜像后再部署。
- Compose 配置：暴露 `8090:8090`；默认 `standalone` Profile；挂载 `./data`、`./plugins`、`./.dsh`、`./workspaces`；通过 `DEEPSEEK_API_KEY` / `OPENAI_API_KEY` 注入模型凭据。

### 2.4 打开 Web 控制台

启动后访问：

```text
http://localhost:8090/
```

<div align="center">
    <img src="https://bugstack.cn/images/article/project/deepseek-harness-java/deepseek-harness-java-03.png" width="650px">
</div>

控制台支持：新建/切换/重命名/创建和恢复 Agent 会话、流式对话与工具卡片渲染、取消运行、模型设置管理、插件启停（在代码配置文件yml）、待审批任务处理、历史会话回放。

#### 2.4.1 curl 测试 - 阻塞

阻塞式对话：

```bash
curl -X POST http://localhost:8090/api/agent/message \
  -H 'Content-Type: application/json' \
  -d '{
    "agentId": "local-agent",
    "message": "Hello!",
    "provider": "deepseek",
    "model": "gpt-5.5",
    "maxTokens": 8192,
    "cwd": "."
  }'
```

> `agentId` 和 `message` 必填；`provider`、`model`、`maxTokens`、`cwd` 省略后使用默认模型设置。

#### 2.4.2 curl 测试 - 流式

SSE 流式对话：

```bash
curl -N -X POST http://localhost:8090/api/agent/stream \
  -H 'Content-Type: application/json' \
  -d '{"agentId": "local-agent", "message": "介绍这个项目的架构"}'
```

如果配置了 `harness.auth.api-keys`，需额外携带 `-H 'X-API-Key: your-api-key'` 或 `-H 'Authorization: Bearer your-api-key'`。

## 三、架构介绍

### 1. 分层设计

<div align="center">
    <img src="https://bugstack.cn/images/article/project/deepseek-harness-java/deepseek-harness-java-07.png" width="950px">
</div>

>项目工程中有源设计图，可以下载工程在 README 里看的更加清楚。

| 模块 | 职责 |
| --- | --- |
| `deepseek-harness-java-app` | Spring Boot 启动器、静态 UI、Profile 配置、预置插件加载 |
| `deepseek-harness-java-trigger` | 19 个 REST Controller、API Key 拦截器、Web MVC 配置 |
| `deepseek-harness-java-api` | 应用层 Facade、Command/Query DTO、响应对象 |
| `deepseek-harness-java-case` | Agent、Session、Task、Plugin 等用例编排（策略树） |
| `deepseek-harness-java-domain` | 27 个限界上下文、实体、值对象、端口与领域服务 |
| `deepseek-harness-java-infrastructure` | Repository、DAO、Gateway、协议和执行环境适配 |
| `deepseek-harness-java-types` | `JavaHarnessPlugin`、`AbstractTool`、`ToolDefinition` 等 SPI 契约 |
| `plugins/*` | 示例插件（Java JAR + Node demo）、插件 Archetype |
| `docs/` | 架构图、领域设计详解、插件开发指南 |

### 2. 领域设计

`domain` 模块共 27 个一级子包，遵循事件风暴（Event Storming）建模，六色图例贯穿下文。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/deepseek-harness-java/deepseek-harness-java-05.png" width="950px">
</div>

| 模块 | 用途 |
|---|---|
| agent | Agent 执行引擎，负责对话循环、消息编排、系统提示词组装、子 Agent 管理、审批与中断等能力 |
| agent.ask | 面向用户提问/确认的交互能力，如弹窗式问题、选项回答 |
| agent.compaction | 对 Agent 上下文进行压缩、裁剪、归纳，控制上下文长度 |
| agent.subagent | 子 Agent 的注册、创建与启动机制 |
| acp | ACP 协议相关能力，通常用于代理/协作通信接口封装 |
| coderuntime | 代码运行时相关能力，负责代码执行或运行环境支持 |
| credentials | 凭据管理，存放与访问认证、授权相关的数据结构与服务 |
| e2b | E2B 运行环境集成，面向沙箱/远程执行环境的封装 |
| goal | 目标管理，描述 Agent 或任务的目标定义与流转 |
| guard | 运行守卫与保护机制，用于限制危险操作、校验执行条件 |
| hooks | 生命周期 Hook 机制，提供事件拦截、扩展点 |
| jobs | 后台任务/作业调度与执行管理 |
| llm | LLM策略等 |
| lsp | 语言服务能力，支持代码定位、hover、引用查询等 |
| plan | 规划模式，支持计划状态切换、计划内容维护、退出规划模式等 |
| plugin | 插件体系总入口，包含插件注册、运行时、桥接等能力 |
| plugin.bridge | 插件运行时桥接层，负责插件与宿主运行环境之间的连接 |
| plugin.registry | 插件注册与安装管理，负责插件元数据、安装计划、安装结果等 |
| plugin.runtime | 插件运行时执行管理，负责插件进环境总模块，负责工具解析、执行配置、模型/工具/环境路由等 |
| runtime.model | 模型路由与模型提供方配置管理 |
| runtime.setting | 模型配置的查询与命令修改服务 |
| runtime.tool | 工具目录与工具选择/解析能力 |
| runtime.profile | 执行画像/运行模式管理，如 headless/web 等 profile 策略 |
| sandbox | 沙箱执行环境封装，支持受限代码或任务执行 |
| schedule | 定时/延时任务调度能力 |
| sdk | 对外 SDK 层，向上层暴露统一调用入口 |
| session | 会话管理，维护 Agent/Terminal/交互会话状态 |
| shared | 通用共享基础设施，如策略路由、抽象基类、通用接口 |
| skill | 技能系统，负责技能定义、选择、调用策略与提供方适配 |
| storage | 存储抽象层，负责键值、快照、持久化等数据访问 |
| task | 任务定义与执行管理，通常用于异步/协作式任务编排 |
| terminal | 终端交互能力，负责命令发送、读取、会话快照等 |
| todo | 待办事项管理，提供待办写入、结果返回等能力 |
| tool | 工具定义、工具调用、工具注册与执行相关能力 |
| typert | 类型注册/远程类型协议相关能力，偏向工具与上下文的注册和结果包装 |
| workflow | 工作流编排，负责多步骤流程的定义、推进与状态管理 |

>项目工程中有源设计图，可以下载工程在 README 里看的更加清楚。

### 3. 插件设计

deepseek harness 核心的插件设计，这里我们采用 Java JAR 使用 ClassLoader 的方式进行加载使用。这个机制的设计很重要，它可以代表着 deepseek harness 的设计思想。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/deepseek-harness-java/deepseek-harness-java-08.png" width="600px">
</div>

#### 3.1 Java Native Plugin

Java Native Plugin 以 JAR 方式安装，宿主使用**隔离 `URLClassLoader`** 加载（进程内、类隔离）：

1. 实现 `JavaHarnessPlugin` 或继承 `AbstractHarnessPlugin`。
2. 实现 `AbstractTool`，声明 `name()`、`description()`、`parameters()`、`run()`。
3. 在 `META-INF/plugin.yaml` 中声明插件元数据。
4. 在 `META-INF/services/cn.walioffice.deepseek.harness.domain.plugin.spi.JavaHarnessPlugin` 中注册入口类。
5. 打包为 JAR 并放入 `harness.plugin.install-root` 指向的目录（默认 `./plugins`）。

插件工具注册后的名称格式：`plugin__<pluginId>__<toolName>`。

#### 3.2 Node Bridge Plugin

`DSH_NODE_BRIDGE` 用于兼容 Node 侧插件：

- 插件包复制到 `harness.plugin.install-root`；
- 识别 `.codex-plugin/plugin.json`、`package.json`、`cordis.yml` 入口；
- 对可执行的 Node 插件生成受限 `node <entrypoint>` 运行计划，通过 **sidecar 进程 + JSON-RPC** 通信，不经 Shell 拼接；
- 入口限制在插件安装目录内的 `.js`、`.mjs`、`.cjs` 文件。

#### 3.3 MCP 工具适配

- `StdioMcpClient`：通过 stdin/stdout 与 MCP Server 做 JSON-RPC 2.0 通信；
- `McpToolAdapter` 把远端工具注册为 `mcp__<serverName>__<toolName>`，与内置工具同链执行；
- 在 `harness.mcp.servers` 中配置 `name/command/args/env/cwd`，启动时自动连接。

## 四、项目学习

为了让伙伴们更好的把 deepseek-harness-java 用起来的同时，还能掌握里面的核心设计。小傅哥先给出一套对应匹配的教程，直接就在工程下 `deepseek-harness-java/docs/html` 打开就可以看见直接预览了（后续还会做一些补充，直接提交到工程）。如果后续大家对这个内容很感兴趣，小傅哥也会在推进完其他课程后，对这部分内容，录制视频。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/deepseek-harness-java/deepseek-harness-java-06.png" width="950px">
</div>

---

小傅哥，一直在构建整个智能体生态的学习资料。包括各类场景和架构的设计方式，如；WaLiSSH 解决运维、WaLiCode 解决编码、WaLiOffice 处理办公、WaLiAPI 搞定 LLM 网关 + RAG + LLM-Wiki 等等。这些内容的建设也是按照一个公司里的场景构建的。可以参考路线图来学习，如下；

