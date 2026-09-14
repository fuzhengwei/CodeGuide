---
title: DSH Java Plugin
lock: no
---

# DSH Java Plugin - DSH Java，1天2套 Agent Plugin 开发对接。企业级解决方案，就是快！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>项目：[https://t.zsxq.com/kYcVt](https://t.zsxq.com/kYcVt)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

有点上头了，一个晚上干一个，两个晚上干一对，有需要的话我还能三个干一串 🤨。在 DSH Java 🐴 的服务框架上，竟然可以如此快的对应用场景接入智能体。📚 书接上文，[《Java + DDD，1:1 复刻 Deepseek Harness 项目》](https://bugstack.cn/md/project/deepseek-harness-java/deepseek-harness-java.html) 这回小傅哥带着你把 **DSH Java** 实际用起来。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-0-00.png" width="150px">
</div>

是的，小傅哥的 `DeepSeek Harness Java` 马具服务已经跑起来了，现对外发布官网 [https://dsh-java.xiaofuge.cn/](https://dsh-java.xiaofuge.cn/) —— `提供了一键部署脚本，可以快速启动体验 👣`。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/deepseek-harness-java/dsh-java-0.1.6-07.png" width="650px">
</div>
> 官网文档，含有丰富的信息，以及学习和使用教程。可以预览一波。

**从头搭，已经不是做智能体的最佳方案，尤其企业级。🤔**

自从 `Claude Code`、`Codex` 打开局面，今年可以称为是智能体应用爆发元年。全互联网公司上下都在进场：你用 `Spring AI`，我用 `LangChain4J`，他用 `AgentScope`，还有部门直接从头自研。表面整的吧，热火朝天，实际却很难收口——模型接入重复建设，工具协议各不相同，权限审批散落在各个系统里。部门越多，智能体越多，维护成本和风险也越高。

企业级真正需要的，不是每条业务线再写一套 Agent，而是一个统一收口的智能体运行时：能装插件、能管工具、能控权限、能稳定执行任务。

而 **deepseek-harness-java** (`DSH Java`) 做的就是这件事。它把模型接入、ReAct 循环、工具注册、插件生命周期全部标准化。业务团队不需要每次从零开发一个智能体，只需要把业务能力封装成插件，`JAR` 上传或者配置（pom）安装即可接入运行时。就像给智能体装上“可插拔器官”：今天接数据库，明天接内部系统，后天切换模型，宿主（DSH Java）不用重写。

让团队把时间投入业务创新，而不是继续造轮子。🚀 —— 如果你有关注「`公众号：bugstack虫洞栈`」，就会看到小傅哥，过往发的各类技术解决方案，也都是企业里陆续要走的方式。因为小傅哥本身就是大厂的架构师，干的也就是这些事情。

于22年底 gpt-3.5 发布，小傅哥就追随 AI 的脚步，结合最新的技术方案，做了数十个 AI 场景应用。如，最早的问答助手、OpenAI 对话（俗称套壳）、代码评审、拖拉拽（类似 Dify）、脚手架（通用解决方案）、AI MCP Gateway（http -> mcp），之后是一些列 WaLiCode、WaLiAPI（LLM 网关 + RAG、LLM-Wiki）、WaLiOffice 的对外发布的 AI 产品。现在开始做 DSH Java 马具🐴，跟随小傅哥的脚步，会让你架构思维提高的更快。

>💐 好啦，前面一节带着大家理解了下 [《Java + DDD，1:1 复刻 Deepseek Harness 项目》](https://bugstack.cn/md/project/deepseek-harness-java/deepseek-harness-java.html) 带着大家理解了 DSH Java，这一节带着大家了解和体验基于 ClassLoader 设计插件机制。

## 一、快速体验

**DSH Java，一切皆可插件的 Agent 运行时。** 官网：[https://dsh-java.xiaofuge.cn](https://dsh-java.xiaofuge.cn)

以「一切皆可插件」为设计思想：ReAct 循环、工具系统、审批治理与事件溯源构成运行时内核；能力边界由插件无限扩展——Java Native 基于 ClassLoader 隔离加载、动态插拔，Node Bridge 与 MCP 生态即装即用。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/deepseek-harness-java/dsh-java-0.1.6-01.png" width="650px">
</div>

下面有2种启动方式，以及加一个插件案例的使用。启动部署后，访问链接，在设置里修改模型配置。这些启动方式都可以把 DSH-Java 运行起来快速的体验下。后面想深入的玩，还可以对照现在的插件工程，开发下插件。

### 1. 本地启动

<div align="center">
    <img src="https://bugstack.cn/images/article/project/deepseek-harness-java/dsh-java-0.1.6-02.png" width="750px">
</div>

- 环境说明：需要本地下载安装并设置环境变量，Java JDK 版本 17+
- 下载 Jar：[https://drive.weixin.qq.com/s?k=ACMA4AfQABUnIBrQ39](https://drive.weixin.qq.com/s?k=ACMA4AfQABUnIBrQ39)
- 启动 Jar：`java -jar deepseek-harness-java-0.1.6.4.jar --spring.profiles.active=standalone`

### 2. 容器启动（docker）

```java
docker run -d \
  --name dsh-java-web \
  -p 8090:8090 \
  registry.cn-hangzhou.aliyuncs.com/xfg-studio/deepseek-harness-java:0.1.6.4
```

- 应用镜像已经发布到 dockerhub 容器；[https://hub.docker.com/r/fuzhengwei/deepseek-harness-java/tags](https://hub.docker.com/r/fuzhengwei/deepseek-harness-java/tags) - `可以关注最新版本`
- 同时为了方便大家拉取，已经在 `阿里云` 仓库同步了镜像，`registry.cn-hangzhou.aliyuncs.com/xfg-studio/deepseek-harness-java:0.1.6.4`

> 📢 注意，部署后要开放对外的端口 8090，以及在设置中，添加可用的模型（支持的渠道蛮多的，基本都覆盖了）。

### 3. 容器启动 + 插件案例

也就2个晚上时间，基于 DSH-Java 插件方式做了2个智能体。一个是`智能客服`案例，另外一个是 `MySQL 运维平台`。我把`智能客服`这套给大家做了快捷部署，MySQL 运维平台，你可以把代码下载到本地启动，并把插件在 DSH-Java 部署的服务在插件里上传。这样就可以对接使用了。

- 插件（智能客服）：[https://github.com/fuzhengwei/2d-weekend-mall](https://github.com/fuzhengwei/2d-weekend-mall)
- 创建（MySQL 运维平台）：[https://github.com/fuzhengwei/dsh-java-mysql](https://github.com/fuzhengwei/dsh-java-mysql)

这两个项目里，都会把包括一个 plugin 的包，里面是通过实现 deepseek-harness-java 工程的对外的 types 层，通过引入 DSH-Java 发布的 types 包进行开发。

```java
<!-- Source: https://mvnrepository.com/artifact/cn.xiaofuge/deepseek-harness-java-types -->
<dependency>
    <groupId>cn.xiaofuge</groupId>
    <artifactId>deepseek-harness-java-types</artifactId>
    <version>0.1.5</version>
    <scope>provided</scope>
</dependency>
```

为了方便大家快速了解和体验，不非得直接上来就开发。我已经把构建好的要安装的插件 jar 已经放到2个插件的案例工程 docs 下，可以直接在 DSH-Java 服务的设置里，插件中安装使用。

#### 3.1 智能客服（便捷启动）

<div align="center">
    <img src="https://bugstack.cn/images/article/project/deepseek-harness-java/dsh-java-0.1.6-03.png" width="750px">
</div>

```java
#!/usr/bin/env bash
set -euo pipefail

HARNESS_CONTAINER="dsh-java-web"
MALL_CONTAINER="2d-weekend-mall"
HARNESS_IMAGE="registry.cn-hangzhou.aliyuncs.com/xfg-studio/deepseek-harness-java:0.1.6.4"
MALL_IMAGE="registry.cn-hangzhou.aliyuncs.com/fuzhengwei/2d-weekend-mall:0.1.1"
MALL_SERVICE_TOKEN="${MALL_SERVICE_TOKEN:-mall-internal-demo-token}"
HARNESS_URL="http://127.0.0.1:8090"
MALL_URL="http://127.0.0.1:18080"
PLUGIN_ID="mall-weekend-assistant"

command -v docker >/dev/null 2>&1 || { echo "未找到 docker，请先安装 Docker。" >&2; exit 1; }
command -v curl >/dev/null 2>&1 || { echo "未找到 curl，请先安装 curl。" >&2; exit 1; }

echo "删除旧容器..."
docker rm -f "$MALL_CONTAINER" "$HARNESS_CONTAINER" >/dev/null 2>&1 || true

echo "拉取镜像..."
docker pull "$HARNESS_IMAGE"
docker pull "$MALL_IMAGE"

echo "启动 Harness..."
docker run -d \
  --name "$HARNESS_CONTAINER" \
  --restart unless-stopped \
  --network host \
  -v dsh-web-data:/app/data \
  -v dsh-web-storage:/app/storage \
  -v dsh-web-plugins:/app/plugins \
  "$HARNESS_IMAGE" >/dev/null

echo "启动 Mall..."
docker run -d \
  --name "$MALL_CONTAINER" \
  --restart unless-stopped \
  --network host \
  -e MALL_ASSISTANT_HARNESS_BASE_URL="$HARNESS_URL" \
  -e MALL_SERVICE_TOKEN="$MALL_SERVICE_TOKEN" \
  "$MALL_IMAGE" >/dev/null

echo "等待 Harness 启动..."
READY=false
for _ in $(seq 1 60); do
  if curl -fsS "$HARNESS_URL/api/harness/plugins" >/dev/null 2>&1; then
    READY=true
    break
  fi
  sleep 2
done

if [[ "$READY" != true ]]; then
  echo "Harness 启动失败，请查看日志：" >&2
  echo "docker logs $HARNESS_CONTAINER" >&2
  exit 1
fi

echo "检查商城插件..."
PLUGIN_LIST="$(curl -fsS "$HARNESS_URL/api/harness/plugins")"
if grep -q "$PLUGIN_ID" <<<"$PLUGIN_LIST"; then
  echo "自动配置商城插件..."
  TOKEN_JSON="$(printf '%s' "$MALL_SERVICE_TOKEN" | sed 's/\\/\\\\/g; s/"/\\"/g')"
  curl -fsS -X POST \
    "$HARNESS_URL/api/harness/plugins/$PLUGIN_ID/config" \
    -H "Content-Type: application/json" \
    -d "{\"configs\":[{\"key\":\"mall.base-url\",\"value\":\"$MALL_URL\"},{\"key\":\"mall.service-token\",\"value\":\"$TOKEN_JSON\"}]}" \
    >/dev/null
  curl -fsS -X POST \
    "$HARNESS_URL/api/harness/plugins/$PLUGIN_ID/disable" \
    >/dev/null 2>&1 || true
  curl -fsS -X POST \
    "$HARNESS_URL/api/harness/plugins/activate" \
    -H "Content-Type: application/json" \
    -d "{\"pluginId\":\"$PLUGIN_ID\"}" \
    >/dev/null
else
  echo "商城插件尚未安装，请按页面提示下载 JAR 并在 Harness 插件管理中上传。"
fi

echo
echo "部署完成："
echo "Harness: $HARNESS_URL"
echo "Mall:    $MALL_URL"
echo
echo "商城插件 JAR："
echo "https://github.com/fuzhengwei/2d-weekend-mall/blob/main/docs/mall-agent-plugin-1.0.0-SNAPSHOT.jar"
echo
echo "打开 Harness -> 设置 -> 插件管理 -> 选择 JAR -> 安装并启用。"
echo "打开 Harness -> 设置 -> 模型设置 -> 添加模型，填写模型服务地址、模型名称和 API Key。"
echo "商城服务令牌：$MALL_SERVICE_TOKEN"
```

- 工程：[https://github.com/fuzhengwei/2d-weekend-mall](https://github.com/fuzhengwei/2d-weekend-mall)
- 把这个脚本复制到你的云服务器下（已安装docker的），复制进行执行即可。
- 执行完成后，把提示的商城 JAR 上传到 DSH-Java 里就可以体验了。

#### 3.2 运维平台（MySQL）

<div align="center">
    <img src="https://bugstack.cn/images/article/project/deepseek-harness-java/dsh-java-0.1.6-04.png" width="750px">
</div>

- 工程：[https://github.com/fuzhengwei/dsh-java-mysql](https://github.com/fuzhengwei/dsh-java-mysql)
- 你可以把插件工程下载到本地，之后启动服务。另外需要构建 dsh-java-mysql-plugin 执行出一个 Jar，这个是 Jar 用于在 DSH-Java 里安装使用。

## 二、应用插件

DSH-Java 插件基于 ClassLoader 加载，可以通过上传 Jar 或 POM 的方式进行解析加载使用。而且开发方式很简单，各类应用可以非常方便的扩展智能体能力。

```java
<!-- Source: https://mvnrepository.com/artifact/cn.xiaofuge/deepseek-harness-java-types -->
<dependency>
    <groupId>cn.xiaofuge</groupId>
    <artifactId>deepseek-harness-java-types</artifactId>
    <version>0.1.5</version>
</dependency>
```

插件开发需要使用到这个对外的插件包，里面包含了插件开发的必要方法。

### 1. 插件市场

<div align="center">
    <img src="https://bugstack.cn/images/article/project/deepseek-harness-java/dsh-java-0.1.6-05.png" width="750px">
</div>

- 地址：[https://github.com/topics/dsh-java](https://github.com/topics/dsh-java) 
- 说明：如果你希望贡献的插件，被很多用户使用。可以开发完成后，上传 Github，添加一个 topic 标签 `dsh-java`，这样会被收入到 deepseek-harness-java 的插件市场里。

> 💐 如果你想接一些小企业的智能体开发的活，那么这会是一个非常好的方式，快速的就能落地完成。

### 2. 插件开发

接下来以 [`dsh-java-mysql`](https://github.com/fuzhengwei/dsh-java-mysql) 为例，说明如何开发一个 DSH Java 插件，并上传到 DSH-Java 使用。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/deepseek-harness-java/dsh-java-0.1.6-06.png" width="350px">
</div>

插件开发的基本流程只有 5 步：

```text
引入依赖 → 编写插件入口 → 编写 Tool → 打包 JAR → 上传到 DSH-Java
```

#### 2.1 准备环境

需要：

```text
JDK 17+
Maven 3.9+
DSH-Java 已启动：http://127.0.0.1:8090
```

如果使用 MySQL 案例，还需要启动管理后台：

```bash
cd dsh-java-mysql
mvn -pl dsh-java-mysql-app spring-boot:run
```

管理后台地址：

```text
http://127.0.0.1:8091
```

#### 2.2 创建插件工程

插件就是一个普通 Maven 工程，最小结构如下：

```text
my-plugin/
├── pom.xml
└── src/main/
    ├── java/com/example/MyPlugin.java
    ├── java/com/example/HelloTool.java
    └── resources/META-INF/
        ├── plugin.yaml
        └── services/
            └── cn.xiaofuge.deepseek.harness.domain.spi.JavaHarnessPlugin
```

在 `pom.xml` 中加入 DSH-Java 插件开发依赖：

```xml
<dependency>
    <groupId>cn.xiaofuge</groupId>
    <artifactId>deepseek-harness-java-types</artifactId>
    <version>0.1.5</version>
    <scope>provided</scope>
</dependency>
```

实际案例可以参考：

```text
dsh-java-mysql/dsh-java-mysql-plugin/pom.xml
```

#### 2.3 编写插件入口

插件入口继承 `AbstractHarnessPlugin`，并返回需要提供的工具：

```java
public class MyPlugin extends AbstractHarnessPlugin {

    public MyPlugin() {
        super("my-plugin");
    }

    @Override
    public List<ToolDefinition> tools() {
        return List.of(new HelloTool());
    }
}
```

`my-plugin` 是插件 ID，后面要和 `plugin.yaml` 中的 `id` 保持一致。

#### 2.4 编写一个 Tool

Tool 是插件真正提供的能力。继承 `AbstractTool`，实现工具名称、说明、参数和执行逻辑：

```java
public class HelloTool extends AbstractTool {

    @Override
    public String name() {
        return "hello";
    }

    @Override
    public String description() {
        return "向用户返回问候语。";
    }

    @Override
    public Map<String, Object> parameters() {
        return objectSchema()
                .prop("name", stringSchema("用户姓名"))
                .required("name")
                .build();
    }

    @Override
    protected CompletableFuture<ToolExecutionResult> run(
            Map<String, Object> args, ToolRunContext ctx) {
        return ok("你好，" + str(args, "name") + "！");
    }
}
```

在 `dsh-java-mysql` 中，Tool 的实现方式相同，只是业务从简单问候变成了调用管理后台：

```java
return ok(post(
        "/api/mysql/" + connectionId + "/query",
        sqlJson(args, "sql"),
        args));
```

MySQL 案例已经实现了以下 Tool，可以直接参考源码：

```text
dsh-java-mysql/dsh-java-mysql-plugin/src/main/java/
└── cn/xiaofuge/dsh/mysql/plugin/DshMysqlPlugin.java
```

提供的工具包括：

```text
mysql_list_connections
mysql_read_query
mysql_explain_query
mysql_performance_snapshot
mysql_sql_review
```

#### 2.5 添加插件元数据

##### 2.5.1 plugin.yaml

文件：

```text
src/main/resources/META-INF/plugin.yaml
```

内容示例：

```yaml
id: my-plugin
name: My Plugin
version: 1.0.0
author: Your Name
description: My first DSH Java plugin.
entrypoint: com.example.MyPlugin
```

##### 2.5.2 ServiceLoader 文件

文件路径必须是：

```text
src/main/resources/META-INF/services/cn.xiaofuge.deepseek.harness.domain.spi.JavaHarnessPlugin
```

内容为插件入口类的全限定名：

```text
com.example.MyPlugin
```

这两个文件必须打进最终 JAR，否则 DSH-Java 无法发现插件。

#### 2.6 编译插件

在插件工程根目录执行（可以 IntelliJ IDEA 中的 Maven 操作）：

```bash
mvn clean package -DskipTests
```

例如 `dsh-java-mysql` 插件的打包命令：

```bash
cd dsh-java-mysql
mvn -pl dsh-java-mysql-plugin package -DskipTests
```

生成的 JAR：

```text
dsh-java-mysql-plugin/target/dsh-java-mysql-plugin-0.1.0-SNAPSHOT.jar
```

可以简单检查元数据是否存在：

```bash
jar tf dsh-java-mysql-plugin/target/dsh-java-mysql-plugin-0.1.0-SNAPSHOT.jar \
  | grep 'META-INF/'
```

#### 2.7 上传到 DSH-Java

本身 DSH-Java 是有一套页面可以直接上传 Jar 或者 POM 的，如果你需要一个插件管理后台，也可以通过接口来维护。

使用插件安装接口上传 JAR：

```bash
JAR="$(pwd)/dsh-java-mysql-plugin/target/dsh-java-mysql-plugin-0.1.0-SNAPSHOT.jar"

curl -X POST http://127.0.0.1:8090/api/harness/plugins/install \
  -H 'Content-Type: application/json' \
  -d "{
    \"pluginId\": \"dsh-java-mysql-plugin\",
    \"displayName\": \"DSH MySQL Plugin\",
    \"pluginVersion\": \"0.1.0\",
    \"runtimeType\": \"JAVA_NATIVE\",
    \"sourcePath\": \"$JAR\",
    \"entrypoint\": \"dsh-java-mysql-plugin-0.1.0-SNAPSHOT.jar\"
  }"
```

然后激活插件：

```bash
curl -X POST http://127.0.0.1:8090/api/harness/plugins/activate \
  -H 'Content-Type: application/json' \
  -d '{"pluginId":"dsh-java-mysql-plugin"}'
```

查看插件状态：

```bash
curl http://127.0.0.1:8090/api/harness/plugins
```

- 开发完成后，就可以如前面的案例一样进行使用即可。
- 开发时直接参考 `dsh-java-mysql/dsh-java-mysql-plugin`，先复制它的工程结构，再把 MySQL 查询逻辑替换成自己的业务逻辑即可。

## 三、设计分析

### 1. 整体架构

`deepseek-harness-java` 的插件系统，主要用于连接 Agent 与外部业务系统。

整体链路如下：

<div align="center">
    <img src="https://bugstack.cn/images/article/project/deepseek-harness-java/dsh-java-0.1.6-08.png" width="750px">
</div>

各部分职责如下：

- **外部应用**：负责接收用户消息、保存用户和会话上下文、调用 Harness 接口、展示最终结果。
- **Harness**：负责调用大模型、组织 ReAct 循环、管理工具目录、执行工具、处理超时、审批、事件和错误。
- **Tool Registry**：负责收集和管理所有工具，包括内置工具和插件工具。
- **ToolCallExecutor**：负责统一执行工具，插件工具不能绕过这条执行链。
- **插件**：负责定义 Tool，并将 Tool 调用转换成业务 API、SDK 或数据库操作。
- **业务系统**：负责真实业务数据、业务规则、最终权限和审计。

### 2. 一次对话的执行时序

用户在外部应用中发起一次业务对话后，执行过程大致如下：

<div align="center">
    <img src="https://bugstack.cn/images/article/project/deepseek-harness-java/dsh-java-0.1.6-09.png" width="750px">
</div>

可以将这个过程概括为：

```
用户提问
  → 模型判断
  → 选择 Tool
  → Harness 执行
  → 插件访问业务系统
  → 返回 ToolResult
  → 模型继续推理
  → 返回最终答案
```

这里有一个重要边界：

- 模型只负责理解问题和选择工具。
- Harness 负责工具发现、执行治理和流程编排。
- 插件负责调用业务系统。
- 业务系统负责最终权限和真实数据。

模型不会直接访问数据库，Harness 也不会自动读取外部应用的数据。只有插件通过明确的业务接口，才能访问对应的业务能力。

### 3. ClassLoader 的设计与使用

Java Native 插件运行在 Harness 的同一个 JVM 进程中。为了避免多个插件之间发生类和依赖冲突，Harness 会为每个插件创建独立的 `URLClassLoader`。

整体关系如下：

```
宿主 ClassLoader
    ├── Plugin A ClassLoader
    ├── Plugin B ClassLoader
    └── Plugin C ClassLoader
```

每个插件拥有自己的类加载器和类命名空间。

#### 3.1 ClassLoader 解决什么问题

`ClassLoader` 主要解决以下问题：

- 不同插件可以使用不同版本的第三方依赖。
- 一个插件的实现类不会直接污染另一个插件。
- 插件可以独立加载和重新加载。
- 插件停止后，可以尝试回收对应的类和资源。
- 宿主可以控制插件的加载入口和生命周期。

例如，插件 A 使用某个依赖的 1.x 版本，插件 B 使用 2.x 版本。通过独立的 `ClassLoader`，两个插件的实现依赖可以分别加载，减少版本冲突。

#### 3.2 父加载器共享 SPI 契约

插件 ClassLoader 通常以宿主 ClassLoader 作为父加载器：

```
URLClassLoader pluginClassLoader = new URLClassLoader(
        new URL[]{pluginJar.toUri().toURL()},
        Thread.currentThread().getContextClassLoader()
);
```

这样设计有两个目的：

- 插件自己的业务类和第三方依赖由插件 ClassLoader 加载。
- `JavaHarnessPlugin`、`ToolDefinition` 等公共契约类型由父加载器统一加载。

这是插件能够被宿主识别的关键。

Java 判断一个类型时，不只看类的全限定名，还会看它由哪个 `ClassLoader` 加载：

```
相同全限定名 + 不同 ClassLoader = 不同 Java 类型
```

因此，宿主和插件必须共享同一份 SPI 契约。如果插件把 `deepseek-harness-java-types` 重新打包进自己的 JAR，可能导致：

- `ClassCastException`
- `ServiceLoader` 无法发现插件
- 方法参数无法转换
- 宿主无法识别插件实现

所以，SPI 契约通常应该由宿主提供，插件只在编译时依赖，不应重复打包。

#### 3.3 ClassLoader 与插件生命周期

插件启动时，大致过程如下：

```
读取插件 JAR
    ↓
创建独立 ClassLoader
    ↓
读取插件元数据
    ↓
通过 entrypoint 或 ServiceLoader 找到插件入口
    ↓
实例化 JavaHarnessPlugin
    ↓
调用 onStart()
    ↓
注册 Tool、Prompt、Hook 和事件
    ↓
插件进入 ACTIVE 状态
```

插件停止时，需要按照相反的方向释放资源：

```
停止接收新的工具调用
    ↓
注销 Tool
    ↓
移除 Prompt 和 Hook
    ↓
取消事件订阅
    ↓
关闭线程池、连接池和定时器
    ↓
调用 onStop()
    ↓
关闭 URLClassLoader
```

仅仅调用：

```
pluginClassLoader.close();
```

并不能保证插件类马上被卸载。如果宿主或其他线程仍然持有插件对象，ClassLoader 仍然可能无法被垃圾回收。

常见的残留引用包括：

- 宿主静态集合持有插件对象。
- 插件创建的线程仍在运行。
- 定时任务仍然存在。
- `ThreadLocal` 保存了插件类对象。
- 异步回调仍然引用插件实例。
- 事件总线、缓存或日志系统保存了插件对象。

因此，插件卸载的本质不是关闭一个 JAR 文件，而是清理所有与插件相关的资源和引用。

## 四、为啥做 DSH Java

结尾了，回答个问题：”有人问，AI 时代，大部分代码都是 AI 写的。为什么要做一个 DSH Java 版本。“

>`你敢让一个不懂这个语言的人，直接对你的核心工程做黑盒交付吗？`、`你敢想AI的堆屎山的方式是 A、B、C... 方法里的逻辑都重复写一遍吗？`、`你知道这半年多 AI 写代码干多出来多少事故吗。—— 直接拿字符串靠正则截取的字符串就当幂等用，但线上数据复杂的一批。`

在互联网行业中，电商营销、交易支付、信贷、履约、出行、配送、外卖等核心业务场景，长期以来大多采用 Java 进行开发。并不是说 TypeScript、Go 等语言没有价值，而是它们通常在前端、运维、基础设施和数据处理等场景中更具优势，相较之下，在大型 To C 核心业务中的应用范围和工程积累有所不同。

这背后其实引出了一个重要问题：Java 程序员不仅需要能够编写代码，还需要能够看懂、维护和持续迭代整个工程。

即使目前大量代码由 AI 生成，也不能简单地将 AI 写完的代码直接以“黑盒”的方式交付到线上。尤其是在交易、支付、信贷、履约等核心业务场景中，系统一旦出现故障，影响范围往往非常大。代码由 AI 生成，并不意味着责任也可以交给 AI。最终，系统的设计、审批、上线、维护和事故责任，仍然需要由具备工程能力的开发团队承担。

因此，我们需要构建一套面向 Java 程序员的 DSH 工程体系。它的核心目标并不是让 AI 取代开发者，而是让 AI 生成的代码能够真正融入现有的 Java 工程体系，让开发者看得懂、审得过、改得动、维护得住，并且能够持续进行迭代和优化。

在大规模、复杂、分布式、高并发的系统建设方面，Java 经过多年的发展，已经形成了相对成熟的语言特性、开发框架、中间件生态和工程实践。随着 AI 类项目不断深入，系统也必然会逐渐涉及服务拆分、分布式架构、并发控制、数据一致性、链路治理和稳定性建设等问题。到了这个阶段，工程最终仍然需要回到成熟的企业级开发模式上来。

所以，Java 版 DSH 的价值就在于：让 AI 具备更强的代码生产能力，同时让 Java 程序员保留对系统的理解、控制和治理能力。AI 写完代码之后，我们仍然需要进行审批、测试、上线评估、问题排查、持续迭代和性能优化。

如果一个项目完全不需要人工审核，代码生成后就可以直接交付，那么使用什么语言、采用什么工程体系，确实都不再重要。但对于交易、支付、信贷等核心 To C 场景来说，真正可靠的方式从来不是“AI 写完就上线”，而是让 AI 进入一套能够被人理解、审核和维护的工程体系之中。

10年的互联网大厂核心架构师，可以从头手搓所有分布式框架，并给你的应用上线。这 TM 相当于十级钳工。让他们发挥最大的优势，就是在他们的领域提供最趁手的语言工具。这也是小傅哥要做 Java DSH 的核心原因之一。

> 👣 走起，如果你想更多的积累关于 AI 编程技术，可以跟着小傅哥好好的把这些东西锻炼一下。

