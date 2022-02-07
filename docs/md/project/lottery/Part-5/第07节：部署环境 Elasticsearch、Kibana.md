---
title: 第07节：部署环境 Elasticsearch、Kibana
pay: https://t.zsxq.com/6i27EeM
---

# 第07节：部署环境 Elasticsearch、Kibana

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！

## 一、运维日志

- 在 Docker 环境安装 Elasticsearch 7.6.2 版本
- 对 Elasticsearch 中的 `x-pack-core-7.6.2.jar` 进行破解，满足于在安装使用 Kibana 时可以使用 JDBC 能力

## 二、安装 Elasticsearch

### 1. 安装

- 拉取镜像：`docker pull elasticsearch:7.6.2`
- 执行安装：`docker run --name elasticsearch -d -e ES_JAVA_OPTS="-Xms128m -Xmx256m" -e "discovery.type=single-node" -p 9200:9200 -p 9300:9300 elasticsearch:7.6.2` - ES_JAVA_OPTS 可按需调整
    - --name表示镜像启动后的容器名称  
    - -d: 后台运行容器，并返回容器ID；
    - -e: 指定容器内的环境变量
    - -p: 指定端口映射，格式为：主机(宿主)端口:容器端口

### 2. 检查

**运维面板**：portainer [http://39.96.73.167:9000/#/containers](http://39.96.73.167:9000/#/containers)

![](/images/article/project/lottery/Part-5/7-01.png)

**启动信息**：[http://39.96.73.167:9200](http://39.96.73.167:9200)

![](/images/article/project/lottery/Part-5/7-02.png)

## 三、安装 Kibana

- 拉取镜像：`docker pull kibana:7.6.2`
- 添加配置：kibana.yml

    ```xml
    #
    # ** THIS IS AN AUTO-GENERATED FILE **
    #
    
    # Default Kibana configuration for docker target
    server.name: kibana
    server.host: "0"
    elasticsearch.hosts: [ "http://39.96.73.167:9200" ]
    xpack.monitoring.ui.container.elasticsearch.enabled: true
    # 设置中文显示
    i18n.locale: "zh-CN"
    ```
    
    - 配置 elasticsearch 地址
    - 配置 zh-CN 中文显示
    
- 执行安装：

    ```java
    docker run -d \
      --name=kibana \
      --restart=always \
      -p 5601:5601 \
      -v /data/kibana/config/kibana.yml:/usr/share/kibana/config/kibana.yml \
      kibana:7.6.2
    ```
  
- 访问验证：[http://39.96.73.167:5601/app/kibana#/home](http://39.96.73.167:5601/app/kibana#/home)  

![](/images/article/project/lottery/Part-5/7-03.png)
  

  