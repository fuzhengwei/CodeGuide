---
title: Jenkins
lock: need
---

# 在 Docker 中部署 Jenkins，并完成项目的构建和发布

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=879248890&bvid=BV1AN4y1J7R3&cid=1416090219&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

本文的宗旨在于通过简单干净实践的方式教会读者，如何在 Docker 中部署 Jenkins，并通过 Jenkins 完成对项目的打包构建并在 Docker 容器中部署。

Jenkins 的主要作用是帮助你，把需要在本地机器完成的 Maven 构建、Docker 镜像发布、云服务器部署等系列动作全部集成在一个服务下。简化你的构建部署操作过程，因为 Jenkins 也被称为 CI&CD(持续集成&持续部署) 工具。提供超过 1000 个插件(Maven、Git、NodeJs)来支持构建、部署、自动化， 满足任何项目的需要。

官网：
- 英文：[https://www.jenkins.io/](https://www.jenkins.io/)
- 中文：[https://www.jenkins.io/zh/](https://www.jenkins.io/zh/)

本文涉及的工程：
- xfg-dev-tech-jenkins：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-jenkins](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-jenkins)
- 提示：
  - 推荐使用云服务器做本节的案例 [5.5元/1个月，50元/1年](https://gaga.plus/yun.html)
  - 本节会需要用到的环境 [Docker&Portainer](https://bugstack.cn/md/road-map/docker.html)

## 一、操作说明

本节小傅哥会带着大家完成 Jenkins 环境的安装，以及以最简单的方式配置使用 Jenkins 完成对 [xfg-dev-tech-jenkins](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-jenkins) 案例项目的部署。部署后可以访问 [xfg-dev-tech-jenkins](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-jenkins) 项目提供的接口进行功能验证。整个部署操作流程如下；

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jenkins-01.png?raw=true" width="650px">
</div>

- 左侧竖列为核心配置部署流程，右侧是需要在配置过程中处理的细节。
- 通过把本地对项目打包部署的过程拆解为一个个模块，配置到 Jenkins 环境中。这就是 Jenkins 的作用。

## 二、环境配置

1. 确保你已经在(云)服务器上配置了 [Docker](https://bugstack.cn/md/road-map/docker.html) 环境，以及安装了 docker-compose。同时最好已经安装了 [Portainer](https://bugstack.cn/md/road-map/portainer.html) 管理界面这样更加方便操作。
2. 在配置和后续的验证过程中，会需要访问(云)服务的地址加端口。如果你在云服务配置的，记得开放端口；`9000 - portainer`、`9090 - jenkins`、`8091 - xfg-dev-tech-app 服务`

### 1. Jenkins 部署

#### 1.1 上传文件

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jenkins-02.png?raw=true" width="400px">
</div>

- 如图；以上配置内容已经放到 [xfg-dev-tech-jenkins](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-jenkins) 工程中，如果你是云服务器部署则需要将 dev-ops 部分全部上传到服务器的根目录下。
- compose-down.sh 是 [docker-compose](https://bugstack.cn/md/road-map/docker.html#_7-%E5%AE%89%E8%A3%85docker-compose) 下载文件，只有你安装了 docker-compose 才能执行 `docker-compose -f docker-compose-v1.0.yml up -d` 
- jdk-down.sh 是 jdk1.8 下载路径，以及解压脚本。如果你在云服务器下载较慢，也可以本地搜索 jdk1.8 下载，并上传到云服务器上解压。注意：本步骤是可选的，如果你的项目不强依赖于 jdk1.8 也可以使用 Jenkins 默认自带的 JDK17。可以通过在安装后的 Jenkins 控制台执行 `which java` 找到 JDK 路径。
- maven 下的 settings.xml 配置，默认配置了阿里云镜像文件，方便在 Jenkins 构建项目时，可以快速地拉取下载下来包。

#### 1.2 脚本说明

```shell
version: '3.8'
# 执行脚本；docker-compose -f docker-compose-v1.0.yml up -d
services:
  jenkins:
    image: jenkins/jenkins:2.439
    container_name: jenkins
    privileged: true
    user: root
    ports:
      - "9090:8080"
      - "50001:50000"
    volumes:
      - ./jenkins_home:/var/jenkins_home # 如果不配置到云服务器路径下，则可以配置 jenkins_home 会创建一个数据卷使用
      - /var/run/docker.sock:/var/run/docker.sock
      - /usr/bin/docker:/usr/local/bin/docker
      - ./maven/conf/settings.xml:/usr/local/maven/conf/settings.xml # 这里只提供了 maven 的 settings.xml 主要用于修改 maven 的镜像地址
      - ./jdk/jdk1.8.0_202:/usr/local/jdk1.8.0_202 # 提供了 jdk1.8，如果你需要其他版本也可以配置使用。
    environment:
      - JAVA_OPTS=-Djenkins.install.runSetupWizard=false # 禁止安装向导「如果需要密码则不要配置」docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
    restart: unless-stopped

volumes:
  jenkins_home:
```

Jenkins Docker 执行安装脚本。
- `./jenkins_home:/var/jenkins_home` 是在云服务器端挂一个映射路径，方便可以重新安装后 Jenkins 依然存在。你也可以配置为 `jenkins_home:/var/jenkins_home` 这样是自动挂在 `volumes jenkins_home` 数据卷下。
- `docker` 两个 docker 的配置是为了可以在 Jenkins 中使用 Docker 命令，这样才能在 Docker 安装的 Jenkins 容器内，使用 Docker 服务。
- `./maven/conf/settings.xml:/usr/local/maven/conf/settings.xml` 为了在 Jenkins 中使用映射的 Maven 配置。
- `./jdk/jdk1.8.0_202:/usr/local/jdk1.8.0_202` 用于在 Jenkins 中使用 jdk1.8
- `JAVA_OPTS=-Djenkins.install.runSetupWizard=false` 这个是一个禁止安装向导，配置为 false 后，则 Jenkins 不会让你设置密码，也不会一开始就安装一堆插件。如果你需要安装向导可以注释掉这个配置。并且当提示你获取密码时，你可以执行；`docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword` 获取到登录密码。

#### 1.3 执行安装

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jenkins-03.png?raw=true" width="550px">
</div>

```shell
[root@lavm-aqhgp9nber dev-ops]# docker-compose -f docker-compose-v1.0.yml up -d
[+] Building 0.0s (0/0)                                                                                                       
[+] Running 1/0
 ✔ Container jenkins  Running 
```

执行脚本 `docker-compose -f docker-compose-v1.0.yml up -d` 后，这样执行完毕后，则表明已经安装成功了💐。

### 2. 插件安装

地址：[http://localhost:9090/](http://localhost:9090/) - `登录Jenkins`

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jenkins-04.png?raw=true" width="750px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jenkins-05.png?raw=true" width="450px">
</div>

- 1~2步，设置镜像源，设置后重启一下 Jenkins。
- 3~4步，下载插件，先下载安装 chinese 汉化插件，方便不太熟悉 Jenkins 的伙伴更好的知道页面都是啥内容。
- 5步，所有的插件安装完成后，都需要重启才会生效。`安装完 chinese 插件，重启在进入到 Jenkins 就是汉化的页面了`
- 除了以上步骤，你还需要同样的方式安装 maven、git、docker 插件。
- 注意，因为网络问题你可以再做过程中，提示失败。没关系，你可以再搜这个插件，再重新下载。它会把失败的继续下载。

### 3. 全局工具配置

地址：[http://localhost:9090/manage/configureTools/](http://localhost:9090/manage/configureTools/)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jenkins-06.png?raw=true" width="750px">
</div>

用于构建部署的 SpringBoot 应用的环境，都需要在全局工具中配置好。包括；Maven、JDK、Git、Docker。注意这里的环境路径配置，如果配置了是会提示你没有对应的路径文件夹。

### 4. 添加凭证

地址：[http://localhost:9090/manage/credentials/store/system/domain/_/](http://localhost:9090/manage/credentials/store/system/domain/_/)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jenkins-07.png?raw=true" width="650px">
</div>

- 配置了Git仓库的连接凭证，才能从Git仓库拉取代码。
- 如果你还需要操作如 ssh 也需要配置凭证。

## 三、新建任务

一个任务就是一条构建发布部署项目的操作。

### 1. 配置任务

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jenkins-08.png?raw=true" width="550px">
</div>

```
xfg-dev-tech-jenkins
```

### 2. 配置Git

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jenkins-09.png?raw=true" width="550px">
</div>

```java
# 你可以 fork 这个项目，到自己的仓库进行使用
https://gitcode.net/KnowledgePlanet/ddd-scene-solution/xfg-dev-tech-content-moderation.git
```

### 3. 配置Maven

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jenkins-10.png?raw=true" width="550px">
</div>

- 在高级中设置 Maven 配置的路径 `/usr/local/maven/conf/settings.xml`。这样才能走自己配置的阿里云镜像仓库。

```shell
clean install -Dmaven.test.skip=true
```

### 3. 配置Shell

```shell
# 先删除之前的容器和镜像文件
if [ "$(docker ps -a | grep xfg-dev-tech-app)" ]; then
docker stop xfg-dev-tech-app
docker rm xfg-dev-tech-app
fi
if [ "$(docker images -q xfg-dev-tech-app)" ]; then
docker rmi xfg-dev-tech-app
fi

# 重新生成
cd /var/jenkins_home/workspace/xfg-dev-tech-jenkins/xfg-dev-tech-app
docker build -t xiaofuge/xfg-dev-tech-app .
docker run -itd -p 8091:8091 --name xfg-dev-tech-app xiaofuge/xfg-dev-tech-app
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jenkins-11.png?raw=true" width="550px">
</div>

- 当你熟悉后还可以活学活用，比如这里只是做build 但不做run执行操作。具体的部署可以通过 docker compose 执行部署脚本。
- 另外如果你有发布镜像的诉求，也可以在这里操作。

## 四、测试验证

### 1. 工程准备

**工程**：`https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-jenkins` 你可以fork到自己的仓库进行使用，你的账号密码就是 CSDN 的账号密码。

```java
@SpringBootApplication
@RestController()
@RequestMapping("/api/")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    /**
     * http://localhost:8091/api/test
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseBodyEmitter test(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");

        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        String[] words = new String[]{"嗨，臭宝。\r\n", "恭喜💐 ", "你的", " Jenkins ", " 部", "署", "测", "试", "成", "功", "了啦🌶！"};
        new Thread(() -> {
            for (String word : words) {
                try {
                    emitter.send(word);
                    Thread.sleep(250);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        return emitter;
    }
    
}
```

- 工程中提供了接口；`http://localhost:8091/api/test`

### 2. CI&CD - 构建发布

**地址**：[http://localhost:9090/job/xfg-dev-tech-jenkins/](http://localhost:9090/job/xfg-dev-tech-jenkins/)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jenkins-12.png?raw=true" width="850px">
</div>

- 点击构建项目，最终会完成构建和部署成功。运行到这代表你全部操作完成了。

### 3. 验证结果

**地址**：[http://localhost:9000/#!/2/docker/containers](http://localhost:9000/#!/2/docker/containers)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jenkins-13.png?raw=true" width="850px">
</div>

**访问**：[http://localhost:8091/api/test](http://localhost:8091/api/test)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-jenkins-14.png?raw=true" width="850px">
</div>

- 运行到这代表着你已经完整的走完了 Jenkins CI&CD 流程。