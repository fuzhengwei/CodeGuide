---
title: 第8节：Ollama 模型部署与验证
pay: https://t.zsxq.com/UKozV
---

# 《AI 新范式》第8节：Ollama 模型部署与验证

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://t.zsxq.com/UKozV](https://t.zsxq.com/UKozV)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

Docker 部署 Ollama · 加载大模型 · API 调用验证，让你的云服务器拥有 AI 能力。

## 一、本章诉求

在云服务器上通过 Docker 部署 Ollama，加载 qwen2.5 等大模型，完成 API 接口调用验证，让云服务器拥有 AI 能力。

## 二、Ollama Docker 部署

### 1. 容器化部署

Docker 一键启动 Ollama，挂载模型目录，GPU 直通支持。

### 2. 模型下载

ollama pull 加载模型，支持 qwen2.5、llama3、deepseek 等。

### 3. 外网访问

配置 OLLAMA_HOST=0.0.0.0 开放外网 API 调用。

## 三、Docker 部署

```bash
docker run -d \
  --name ollama \
  -p 11434:11434 \
  -e OLLAMA_HOST=0.0.0.0 \
  -v ollama_models:/root/.ollama \
  ollama/ollama

# 进入容器拉取模型
docker exec -it ollama bash
ollama pull qwen2.5:7b
```

## 四、常用模型命令

```bash
# 拉取模型
ollama pull qwen2.5:7b
ollama pull deepseek-r1:8b

# 查看已安装模型
ollama list

# 本地对话测试
ollama run qwen2.5:7b
> 你好，介绍一下你自己
```

## 五、API 接口验证

整体调用链路如下：

```
💻 调用方（SpringBoot / curl）
        ↓ HTTP 请求
🤖 Ollama API（localhost:11434）
        ↓ 模型推理
✅ 返回结果（JSON / Stream）
```

### 1. curl 接口验证

```bash
# 查看已安装模型
curl http://IP:11434/api/tags

# 调用模型（同步）
curl http://IP:11434/api/generate \
  -d '{"model":"qwen2.5:7b",
    "prompt":"你好"}'

# 调用模型（兼容 OpenAI）
curl http://IP:11434/v1/chat/completions \
  -d '{"model":"qwen2.5:7b",
    "messages":[{"role":"user",
    "content":"你好"}]}'
```

### 2. SpringBoot 集成

```yaml
## application.yml
spring:
  ai:
    ollama:
      base-url: http://IP:11434
      chat:
        model: qwen2.5:7b
```

```java
// Java 调用
@Autowired
ChatClient chatClient;
String result = chatClient
  .prompt("你好")
  .call()
  .content();
```

## 六、读者作业

- 简单作业：Docker 部署 Ollama 并拉取 qwen2.5:7b 模型，使用 curl 调用 API 验证模型返回正常。
- 复杂作业：思考 Ollama 的 OpenAI 兼容接口和原生接口的区别？为什么兼容 OpenAI 协议对生态很重要？
