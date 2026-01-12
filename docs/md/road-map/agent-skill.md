---
title: agent-skill
lock: need
---

# Agent Skill，你记一下，我做如下部署调整！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

skill 是什么？它像是一本技能书📚，把一阳指（`mcp/py/shell/js`）和狮吼功（`prompt`）合成了一整招。缩短了从用户把提示词发给AI客户端，进行分析，决策，再到 mcp 执行的过程，**让诉求直达结果**，token 减少了，幻觉减少了！

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-agent-skill-00.png" width="250px">
</div>


随着 LLM 大模型能力的不断提升，并与 RAG、MCP、Skill 的结合，使得 Agent 智能体与完整的计算机环境（Computer/Phone）交互成为可能。这个过程中，一方面不断产生新的技术方案，一方面又不断的优化设计。就像 Skill 的出现，它不是替代 MCP，而是更准确的使用 MCP 能力。

接下来，小傅哥就带着大家使用一波 skill，让小伙伴们可以在 opencode、trae.ai，以及基于 Spring AI 也可以使用上 skill 能力。

## 一、skill 和 prompt + mcp

如图，演示了一段 skill 的编写案例；

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-agent-skill-02.png" width="850px">
</div>

- 场景：案例中体现的是，对电脑性能检测后，用一段下达命令的方式，告知用户如何优化电脑性能。
- 重点：如果不使用 skill，则需要描述一大段话术，让 ai 自己完成对用户场景诉求的分析，并按照步骤来调用对应的各个 mcp 服务（没有 skill 则需要把各类内容，都包装为 mcp 服务）。这个过程是比较消耗 token 的，也可能有不小的幻觉。现在有了 skill，我们可以适当的完整的写一段诉求文档，文档里嵌入可执行的脚本/mcp服务，让执行更可靠。
- 用途：那都有哪些场景可以写 skill 技能书呢？🤔 如；互联网公司里的系统巡检，在接收到报警日志后，拿到一个报警的系统和接口信息，之后用 skill 技能书，分别采集出对应的系统配置、上线日志、数据库/缓存情况、运营操作记录、全链路监控上的接口耗时情况等。之后在根据我们日常排查问题的时候经验，编写过程步骤，这样会更加准确。

> 所以，不是 skill、mcp 谁替代谁，而是 skill 对 mcp 进行增强，让 ai 执行时更加可靠。

## 二、配置使用

首先，像是市面上的 claude code、opencode 这些软件，都是支持了 skill 技能书配置使用的，如果遇到一些软件暂时还不支持 skill，或者自己使用 spring ai、langchain4j、google adk 构建的智能体时候需要使用 skil 技能，则可以通过 skillport-mcp 来使用 skill 配置。

这里小傅哥分别演示下 opencode、trae.ai + mcp、spring ai + google adk + mcp 的方式使用 skill；

### 1. 测试工程(skill)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-agent-skill-03.png" width="300px">
</div>

- 地址：[https://github.com/fuzhengwei/xfg-dev-tech-agent-skills](https://github.com/fuzhengwei/xfg-dev-tech-agent-skills)
- 说明：
  - 工程里 `docs/skills` 下面就是一个个技能书，battle-plan、pdf，每一个技能书下都必须有一个 SKILL.md 文件，作为入口。这个文件，可以描述 prompt 提示词，以及在提示词中明确给出可执行的脚本(py\shell\js)和可参考的文档。
  - 此外，在 xfg-dev-tech-app 下，test 里编码的是 SpringAiToolTest 测试技能书案例。

### 2. opencode 使用

- 文档：[https://opencode.ai/docs/skills/](https://opencode.ai/docs/skills/)
- 安装：[https://bugstack.cn/md/road-map/ai-ssh-opencode.html](https://bugstack.cn/md/road-map/ai-ssh-opencode.html) - `做好了安装脚本，方便小白伙伴使用`

#### 2.1 配置skill

```java
fuzhengwei@fuzhengweideMacBook-Pro-2 skill % ls
battle-plan
fuzhengwei@fuzhengweideMacBook-Pro-2 skill % pwd
/Users/fuzhengwei/.opencode/skill
fuzhengwei@fuzhengweideMacBook-Pro-2 skill % cd battle-plan 
fuzhengwei@fuzhengweideMacBook-Pro-2 battle-plan % ls
reference.md	scripts		SKILL.md
fuzhengwei@fuzhengweideMacBook-Pro-2 battle-plan % 
```

进入到 opencode 配置文件下，如果是 linux 一般会放到 `/root/.config/opencode/` 下。首先你要进入到这个 opencode 配置文件夹，之后在这个文件夹添加一个 skill，再之后就在 skill 下创建你的具体的技能书了。现在你可以把 xfg-dev-tech-agent-skills 案例工程的技能书，battle-plan 放到 skill 里。

#### 2.2 开启skill

```java
fuzhengwei@fuzhengweideMacBook-Pro-2 ~ % cd /Users/fuzhengwei/.opencode
fuzhengwei@fuzhengweideMacBook-Pro-2 .opencode % ls
bin		node_modules	package.json
bun.lock	opencode.json	skill
fuzhengwei@fuzhengweideMacBook-Pro-2 .opencode % cat opencode.json 
{
  "permission": {
    "skill": {
      "pr-review": "allow",
      "internal-*": "deny",
      "experimental-*": "ask",
      "*": "allow"
    }
  },
  "$schema": "https://opencode.ai/config.json"
}%                                                                                            fuzhengwei@fuzhengweideMacBook-Pro-2 .opencode % 
```

- 注意 `opencode.json` 需要配置下 `"*": "allow"`

#### 2.3 使用skill

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-agent-skill-04.png" width="600px">
</div>

- 提问：`基于 skill 解答，电脑性能优化`
- 说明：这里的`电脑性能优化`就是 skill 工具名称的描述。

### 3. trae.ai + mcp + skill

#### 3.1 工具说明

工具：[https://github.com/gotalab/skillport](https://github.com/gotalab/skillport)

目前还有不少 AI Agent 智能体，在底层设计上，还不支持直接使用 skill，也包括一些 ai 组件框架，也都没有 skill 的直接支持。那么这里要引入一个 skillport-mcp 服务来解决。借助 mcp 能力，使用 skill。

#### 3.2 工具安装

```java
# 需要安装 python3
pip3 config set global.index-url http://mirrors.aliyun.com/pypi/simple/
pip3 install uvx
```

- 安装 skillport 前，要确保本地安装了 pyhton/python3 环境。之后有 uvx 的安装。

```java
pip3 install skillport
# or: uv tool install skillport
```

- 如果安装过程中遇到一些失败的问题，可以用 trae.ai 里面执行安装，之后把报错拖进去提问。

#### 3.3 mcp 配置

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-agent-skill-05.png" width="600px">
</div>

```java
{
  "mcpServers": {
    "skillport": {
      "command": "uvx",
      "args": ["skillport-mcp"],
      "env": { "SKILLPORT_SKILLS_DIR": "~/.skillport/skills" }
    }
  }
}
```

- 这里你要配置下自己 skill mcp 服务，到你的 trae.ai 中。确保一定安装好了 python 环境，可以执行 `pip3 install skillport` 安装。

#### 3.4 工具使用

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-agent-skill-01.png" width="400px">
</div>

当你选择 Builder with MCP（涵盖了skillport-mcp），之后提问 `基于 skill 解答，电脑性能优化` 那么就可以得到上面的命令了。

### 4. spring ai + skill

```java
public class SpringAiToolTest {

    private static final Logger log = LoggerFactory.getLogger(SpringAiToolTest.class);

    public static void main(String[] args) {
        OpenAiApi openAiApi = OpenAiApi.builder()
                .baseUrl("https://apis.itedus.cn")
                .apiKey("sk-efen7WX8Q8vGvBps3f7c9a34578d41BbBc508dC5Df33A9Fb")
                .completionsPath("v1/chat/completions")
                .embeddingsPath("v1/embeddings")
                .build();

        ChatModel  chatModel = OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("gpt-4.1")
                        .toolCallbacks(new ArrayList<>() {{
                            addAll(List.of(sseMcpClient()));
                        }})
                        .build())
                .build();

//        String call = chatModel.call("你哪有哪些 skill 工具能力");
        String call = chatModel.call("基于 skill 解答，电脑性能优化");

        log.info("测试结果:{}", call);

    }

    /**
     * https://github.com/gotalab/skillport
     * pip3 config set global.index-url http://mirrors.aliyun.com/pypi/simple/
     * pip3 config set install.trusted-host mirrors.aliyun.com
     * pip3 config list
     * pip3 install uvx
     */
    public static ToolCallback[] sseMcpClient() {
        ServerParameters stdioParams = ServerParameters.builder("uvx")
                .args("skillport-mcp")
                .env(new HashMap<>() {{
                    put("SKILLPORT_SKILLS_DIR", "/Users/fuzhengwei/coding/gitcode/KnowledgePlanet/road-map/xfg-dev-tech-agent-skills/docs/skills");
                }})
                .build();

        McpSyncClient mcpSyncClient = McpClient.sync(new StdioClientTransport(stdioParams, new JacksonMcpJsonMapper(new ObjectMapper())))
                .requestTimeout(Duration.ofSeconds(35000)).build();

        McpSchema.InitializeResult initialize = mcpSyncClient.initialize();

        return SyncMcpToolCallbackProvider.builder().mcpClients(mcpSyncClient).build()
                .getToolCallbacks();
    }

}
```

- 在 Spring AI 程序中，添加 `skillport-mcp` 服务，之后在 ChatModel 模型里，使用 mcp 服务。
- 如果你正在开发一些 AI Agent，那么也可以把 `skillport-mcp`  配置进去使用。如小傅哥带着做 [AI Agent 智能体项目](https://bugstack.cn/md/project/ai-knowledge/ai-knowledge.html) 你现在可以加进去更多的扩展操作了。



