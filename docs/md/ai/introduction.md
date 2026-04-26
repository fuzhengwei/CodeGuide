---
title: 初识智能体
lock: need
---

# 📚《从零理解智能体（+八股）》第1篇，认识智能体，从过去到现在

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

**我不是上车 AI 赛道，而是 AI 赛道的开车人！** 自 2022 年底，以 OpenAI 正式对外发布 ChatGPT 大语言模型 LLM（Large Language Model）开始，小傅哥便随时开启了 AI 应用开发的`”班车“`，先是最早发布了 AI 结合场景应用的问答助手，后是基于 AI 接口实现 SDK 做 OpenAI 应用，再往后逐步跟随最新 AI 技术（规范、模型、组件）实现相关的各类智能体服务。再到最近，以个人开发者发布了一款 AI IDE 工具 WaLiCode - [https://walicode.xiaofuge.cn](https://walicode.xiaofuge.cn) —— AI 让我走向了全栈开发！

<div align="center">
	<img src="https://bugstack.cn/images/article/project/ai-rag-knowledge/ai-rag-knowledge-0-00.png" width="150px"/>
</div>

**AI 时代，只要你学的足够慢，那你就不用学了！**

AI 的发展非常快，快到什么德行！如果一个`技术`/`服务`/`软件`，你还没来得及学，那么几周后，可能就淘汰换新品了。所以学的都够慢，很多东西就不用学了！

然而，事情的本质并不是这样。那些所谓的换新，眼花缭乱的 AI Agent 服务，都只是上层产品形态，对于底层的核心内容，协议、标准、组件，都没有变，而是逐步的开枝散叶。如果当你从一开始，就跟着小傅哥体验 API + 业务，手搓 AI SDK，在实现 AI Agent 服务，那么这个阶段，你就是一个标准的 AI 应用开发工程师，毛线的焦虑都不会有！

但没办法，有些伙伴就是错过了这一班车，都已经到了市面上大量出现 AI Agent 应用开发岗，到了公司倒逼着程序员了解 AI，使用 AI，才后知后觉的开始准备学习。然后被市面上的大量资料、产品、服务，带着节奏东跑一下，西跑一下。碎片化的知识（~~还包括用AI窜出来的~~），给人搞的身心疲惫。

所以，他来了！**《从零理解智能体（+八股）》** 从零，即是从本质出发，从第一性原理上手，从实践中学习。本套内容会系列的为大家补全关于`智能体`的相关知识，让小白伙伴跟着学习后对`智能体`也能有个清楚的认知。希望这次别错过这趟车，免费的班车 🚌！

>💐 文末还有相关智能体实战项目，一整套内容从22年 AI 发展到至今，全部都有代表性实战项目。通过实战项目学习，也可以很好的掌握智能体技术，成为 AI 应用开发工程师。

## 一、什么是智能体

简单来说，AI智能体（Agent）就是一个能够感知环境、做出决策并执行行动的智能系统。它就像一个有"大脑"和"手脚"的助手，可以帮你完成各种任务。

**但更直白一些来讲，智能体是这样（我们以发展迭代视角来看）；**

2022年，OpenAI GPT 横空出世，我们得到了一个牛逼的网页对话服务。它像是一个超级大脑，可以为我们解答各类问题，包括像是程序员遇到代码错误，也可以询问后给出一些正确的编码方式，之后我们在复制到工厂里运行验证到使用。这一时刻，大家普遍把 AI 定位成比百度更牛的搜索引擎。

2023年，各大 LLM 大语言模型爆发，也都开始提供 API（http）接口对接协议。这一年，我们把 AI 通过 Github 外挂程序接入到微信群（~~对接一次￥500~~），也接入公众号，还接入到各个网站以及网页分析，网页翻译等场景。因为直接访问 gpt 困难，因此做一套套壳的服务，成了变现入口。

```java
curl https://api.openai.com/v1/chat/completions -H "Content-Type: application/json" -H "Authorization: Bearer sk-DEOKLWYTQ0Lim9******" -d '{
  "model": "gpt-3.5",
  "messages": [
    {
      "role": "user",
      "content": "写个冒泡排序"
    }
  ]
}'
```

> API 协议就是这样，一个简单的 http 接口，来调用模型。为了更加方便的调用，后来各个厂家为自己的模型封装了 SDK 组件。这类组件小傅哥也带着实现了好几个。举例；[https://bugstack.cn/md/project/chatgpt/sdk/chatglm-sdk-java-v2.html](https://bugstack.cn/md/project/chatgpt/sdk/chatglm-sdk-java-v2.html)

2024年，以前的 AI 对话，只是问啥说啥，还可以告诉你怎么做，但它不去做。为了让它可以去做，我们开始设定 AI 对话中返回的格式结构，比如一个标准的 json 结构，还要告诉它不要解释任何信息，只给我返回这样一个结构，并直接告诉我结果。这一阶段，开始有苗头，AI 能做一些事了。因为返回的结构化 json，就可以被程序翻译识别，转而调用服务接口，但幻觉严重，实际使用效果还是有点差。不过这样的操作方式，也给 OpenAI 带来了新的思路，MCP 标准上下文协议于24年发布，它可以让我们以标准的协议方式，使用 AI 调用服务接口。之后就炸了！因为这相当于正式的给 AI 装上了四肢，它可以真的帮你干活了。百度搜索、谷歌地图、淘宝支付，也包括你公司的业务项目都能提供出来 MCP 协议接口服务，这个有脑子的 AI + 各种能力的服务，让各个互联网公司不得不重视 AI 的应用开发。

2025年，这是 AI Agent 智能体应用类项目爆发的一年，最有代表性的是 AI IDE 代码开发工具，Claude Code 可以说是玩命的干这个方向，把模型能力和工具能力都拉到爆。~~其他各个厂商，也都开始跟进开发 AI IDE 工具，之后大公司卖 coding/token plan，个人卖中转站。~~ 海外这些 AI 大厂商，为了能让 LLM 更准确的执行用户意图，发布了 skills 技能书（这些细节后续章节再分享），把一套套执行流程的标准做成技能（类似 sop 标准），这样 LLM 可以不需要那么准确的提示词（一次次重复写），就能很好的执行标准流程（画个时序图、按照DDD重构代码等）。当然，也有离职的好哥们被蒸馏成了 skills，虽然他人走了，他的灵魂永远留在了岗位。

2026年，开年之后最为炸裂的是 OpenClaw 养虾，把 Mac Mini 都干涨价了，上门安装 OpenClaw 也成了生意。OpenClaw 的玩法开发了一套电脑端网关，说白了也就是让 LLM 对接的不只是 mcp、skills，而是对接了硬件设备，让 LLM 可以直接像人一样操作电脑。其实在这东西之前，25年到26年，还有智谱发布的 autophone 通过 LLM 控制手机。不过手机的护城河目前太高，安全级别也很高。短时间还不太会各处开花。

---

综上，这是22年到26年的关于 LLM 大语言模型以应用视角下的发展路径。这里我画过一张图，可以参考；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/ai-agent-scaffold/part-1/1-1/images/ai-agent-scaffold-1-1-02.png" width="950px">
</div>

整个 AI 发展的诉求是这样；

- 因为，有 LLM 大语言模型对话诉求，所以基于机器学习，大规模的训练数据。
- 因为，有 LLM 对话可以调用外部服务的诉求，所以设计了 function 函数和 mcp 模型上下文协议。
- 因为，有 LLM 对话要像人一样可以规划、执行、检查诉求，所以设计了 agent work flow（ReAct）工作流。
- 因为，有 LLM 对话要有历史记忆诉求，所以设计了各类的 memory 记忆上下文，这个阶段还把 rag 向量知识库，作为存储源过。
- 因为，有 LLM 对同类智能体的分离诉求，所以设计了 a2a 协议，可以本地智能体调用其他智能体。
- 因为，有 LLM 对话不能每次都写一堆提示词说明情况，有了 skills 技能，把人的各类标准行为”蒸馏“成模块包，让 ai 应用使用，复用能力。
- 因为，有 LLM 对话编码过程中，还会有”惊喜“，每次执行的不一样。所以有了 [spec](https://github.com/github/spec-kit) 各类规约的限定，让 ai 从以前的随意搞，到现在的有规划和路径的搞。

llm、rag、function、mcp、skills、agent work flow（ReAct）、memory、a2a、spec...，他们总是在研究，怎么让 ai 更可靠。也一直想办法让 ai 像个人一样工作。你一次干不了的活，就让你记忆（短期/长期/工作），还让你有规划（拆解/执行/反思），再交给你干这个活的经验（skills 复刻能力），怕你干跑偏在你规约（spec-kit）。还干不好，没关系。ai 发展这么快，总会让你把活干明白。

## 二、了解发展历史

如果说过去几年是AI行业的“大航海时代”，那么如今我们已正式步入“大建构时代”。在这场重塑人类生产力的工业革命中，谷歌、OpenAI与Anthropic不再仅仅是技术路径的角逐者，更是三种截然不同的AI文明的构建者。他们共同推动了AI从“文本生成器”向“数字世界执行者”的历史性跨越。

<div align="center">
    <img src="https://bugstack.cn/images/article/ai/ai-introduction-01.png" width="950px">
</div>

**Google：全栈生态的守望者与多模态的践行者**

作为Transformer架构的缔造者，谷歌曾一度在生成式浪潮中显得庞大而迟缓。但在最新的现实中，谷歌凭借其无可比拟的“全栈底座”（TPU算力矩阵 + Android/Chrome终端生态 + 搜索数据）打出了反击战。其演进路线不再执着于单点突破，而是强调**“无处不在”与“原生多模态”**。通过Gemini系列，谷歌试图将AI化作水和电，无缝接入全球数十亿台设备中，实现跨越文本、视觉、音频的实时感知与交互。

**OpenAI：从“规模神话”到“系统2推理”的破壁人**

OpenAI曾用海量数据和算力验证了Scaling Law，推开了生成式AI的大门。然而，随着数据墙的逼近，OpenAI的最新锚点已从“生成（Generation）”转向了**“深度推理（Reasoning）”**（如o1系列模型）。通过引入强化学习与“测试时计算（Test-time Compute）”，OpenAI正在让AI从“凭直觉说话”进化到“思考后再回答”。他们依然是AGI（通用人工智能）最激进的冲锋者，试图在数学、编程和科学探索领域率先实现真正的机器逻辑。

**Anthropic：以“安全宪法”重塑企业级信任的实干家**

最初被视为OpenAI“保守派克隆体”的Anthropic，反而在商业落地中走出了最坚实的一步。其“宪法AI（Constitutional AI）”不仅仅是安全制衡的工具，更意外地造就了Claude系列在长文本处理、复杂代码编写和指令遵循上的惊人稳定性。Anthropic放弃了花哨的C端炒作，精准切入程序员与企业工作流（如Artifacts交互机制），证明了**“可控性与安全”本身就是最强大的生产力**。

无论是谷歌的设备级渗透、OpenAI的极致推理，还是Anthropic的可靠工作流，三家的路径正在收敛于同一个终局：**打造能在复杂现实世界中自主解决问题的智能体。** 这不再是一个纯粹的实验室科学问题，而是一场关乎算力调度、能源争夺、终端重构以及人类社会伦理适应的系统级战争。Transformer播下的种子，经过Scaling Law的催化，正在安全的护栏内，长出接管数字世界的神经元。

## 三、智能体的用途

智能体目前正处于从“只会说”到“能去做”的跨越期，从过去能做什么，到现在还有什么不能做的阶段。通过 AI MCP Gateway 解决了从应用服务到智能体的衔接，使用 Skills 提供了各类角色技能，叠加 Spec-Kit 做规约处置，我们让一个智能体的所应用场景变得越来也广泛和可靠了。

所以，当下阶段各类场景，都会被智能体逐步接入（举例）；

<div align="center">
    <img src="https://bugstack.cn/images/article/ai/ai-introduction-02.png" width="650px">
</div>

## 四、开发所需框架

不少伙伴被市面信息弄的很焦虑，本身学习 Java 的是不还要学习下 Python、Go 的 Agent 开发。但其实，企业里各个业务线是有所不同的，因此使用的语言也不一样。每一个语言，都有非常庞大的知识体系，来满足这个语言下各类场景的开发诉求。如果你是做 Java，其实优先把 Java 相关的智能体技术做好就可以。

1. 对于业务类项目，java 有庞大的市场，如美团、京东、阿里、饿了么、滴滴等。这些业务会大量的引入 ai 进行提效，如客服、巡检、日志分析、运营等。
2. 因为这些业务本身就是使用 java，背靠 spring，所以很多公司天然的选择了 spring ai 框架进行开发以及二次扩展（阿里的 spring ai）。所以学习 spring ai 是完全没问题的，包括谷歌，也做了 java 的 agent 框架。（星球ai agent脚手架课程，使用 spring ai/langchain4j + google adk - mcp、skills、a2a、agent work flow等，内容很全面）
3. 不同语言应对场景不同，如 py 更多面向基础、数据、量化等。java ai 更多面向业务，而业务岗位也是最多的。

其实，你一套东西搞透彻，很多东西也就自然而然的可以理解和上手了！

### 1. 🤖 Python 生态

| 框架             | 官网                                      | 特点                                                    |
| :--------------- | :---------------------------------------- | :------------------------------------------------------ |
| **LangChain**    | https://docs.langchain.com                | 最流行的 LLM 应用编排框架，模块化，支持 RAG/Agent/Chain |
| **LlamaIndex**   | https://www.llamaindex.ai                 | 专注数据连接与 RAG，文档索引优化能力强                  |
| **LangGraph**    | https://langchain-ai.github.io/langgraph/ | LangChain 团队出品，图形化工作流编排                    |
| **CrewAI**       | https://www.crewai.com                    | 多 Agent 协作框架，角色化编排，易上手                   |
| **AutoGen**      | https://microsoft.github.io/autogen/      | 微软开源，多 Agent 对话协作                             |
| **Dify**         | https://dify.ai                           | 开源 LLM 应用开发平台，低代码/零代码                    |
| **Coze（扣子）** | https://www.coze.cn                       | 字节跳动出品，Bot 编排平台                              |

### 2. ☕ Java / JVM 生态

| 框架                  | 官网                                         | 特点                                  |
| :-------------------- | :------------------------------------------- | :------------------------------------ |
| **Spring AI**         | https://spring.io/projects/spring-ai         | Spring 官方出品，统一 API 集成多模型  |
| **Spring AI Alibaba** | https://github.com/alibaba/spring-ai-alibaba | 阿里云开源，基于 Spring AI + 通义系列 |
| **LangChain4j**       | https://docs.langchain4j.dev                 | LangChain 的 Java 实现版              |

### 3. Agent 开发工具包

| 框架                  | 官网                                                         | 特点                                                         |
| :-------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| **Google ADK**        | https://adk.dev/                                             | Google 智能体开发工具包，图形化 Agent 构建。非常好用！支持多套语言 Java、JS、Python、Go |
| **OpenAI Agents SDK** | https://openai.github.io/openai-agents-python/               | OpenAI 官方 Agent SDK                                        |
| **LlamaIndex Agents** | https://docs.llamaindex.ai/en/latest/module_guides/deploying/agents/ | LlamaIndex 的 Agent 模块                                     |

```java
public class ChatGPTClient {
    
    private final String apiKey = "sk-xxxxxxxxxxxxxxxxxxxx";
    
    public String chat(String message) {
        // 1. 构建请求
        String requestBody = JSON.toJSONString(Map.of(
            "model", "gpt-3.5-turbo",
            "messages", Arrays.asList(
                Map.of("role", "user", "content", message)
            ),
            "temperature", 0.7
        ));
        
        // 2. 发送HTTP请求
        try {
            HttpResponse<String> response = HttpClient.newHttpClient()
                .send(HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build(),
                HttpResponse.BodyHandlers.ofString());
            
            // 3. 解析响应
            JSONObject json = JSON.parseObject(response.body());
            return json.getJSONArray("choices")
                       .getJSONObject(0)
                       .getJSONObject("message")
                       .getString("content");
        } catch (Exception e) {
            throw new RuntimeException("调用OpenAI接口失败", e);
        }
    }
}
```

>除以上之外，每个公司，还有自己的智能体开发框架。所以对你来说，你主要一套搞透彻，进入公司就可以做其他的内容。因为说到底，你还是在使用统一的 api，只是上层被不同语言封装了，之后为你构建智能体提供了方便。项目举例；[https://bugstack.cn/md/project/ai-agent-scaffold/ai-agent-scaffold.html](https://bugstack.cn/md/project/ai-agent-scaffold/ai-agent-scaffold.html)

## 五、我们正面临的

从2022年ChatGPT发布到现在，AI智能体已经走过了从简单对话到自主执行的发展历程。作为研发工程师，我们正处在一个非常有意思的时代，既见证着技术的快速变革，也有机会参与其中。

智能体不是要替代人类，而是要成为我们的得力助手。理解它的发展脉络，掌握它的核心技术，能让我们在这个AI时代更好地发挥自己的价值。

但要想更好的驾驭 AI，一定是深耕自己的技术，否则会让你觉得 AI 虽然能帮忙，但也会让你更忙。

>后续涉及各个细节的技术类章节，会附带的提供一些面试问题。