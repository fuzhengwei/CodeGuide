---
layout: post
category: itstack-demo-netty-3
title: 《Netty+JavaFx实战：仿桌面版微信聊天》代码开源、上云部署、视频讲解，只为让你给点个Star！
tagline: by 付政委
tag: [netty,itstack-demo-netty-3] 
excerpt: https://github.com/fuzhengwei/NaiveChat 代码开源，通过视频讲解每一个服务模块的功能，快速入门后并深入学习实践。好！感谢那些默默支持小傅哥的伙伴们！
---

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>视频：[https://mp.weixin.qq.com/s/OmXCY4fTfDpkvjlg5ME0ZA](https://mp.weixin.qq.com/s/OmXCY4fTfDpkvjlg5ME0ZA)
<br/>源码：[`https://github.com/fuzhengwei/NaiveChat`](https://github.com/fuzhengwei/NaiveChat)

>本项目是作者小傅哥使用```JavaFx```、```Netty4.x```、```SpringBoot```、```Mysql```等技术栈和偏向于DDD领域驱动设计方式，搭建的仿桌面版微信聊天工程实现通信核心功能。如果本项目能为您提供帮助，请给予支持(关注、点赞、分享)！

## 一、前

与 `Netty` 的缘分是从 5.0 版本开始，当时网上资料也不多，就随着学习整理编写了一整套案例。但也就当我全套的案例编写没多久后，`netty5.0`，因最新版本的性能问题，被官网下掉了并主推 `netty4.0` 版本。好吧！就这样有很多小伙伴问我是不是可以写一套 `netty4.0` 的案例，否则现在看着 5.0 的案例写 4.0 的代码实在难受！

**安排**！从19年开始陆续编写 `netty4.0` 案例，从基础篇、中级篇、高级篇以及源码分析共编写了37个章节，基本可以满足所有小白对 `Netty` 的入门。目前也是我博客里非常火专题内容了。[https://bugstack.cn](https://bugstack.cn)

后来越来越多的小伙伴开始加我微信，一起讨论 **Netty** 学习。在讨论的工程中，遇到各种各样的问题，虽然在案例文章中都有所介绍，但是案例终究是引导入门的，并不是一次完整的实践。及时能看懂只言片语，但真的上手还是有一些难度。

为此！在19年结尾，20年的春节里。发起了`《Netty+JavaFX实战：仿桌面版微信聊天项目》`，并将文章和代码全套梳理发布到 [GitChat](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f) 专栏，截止到文章发布共计售出有 **716** 份，虽然不多。但好在没人买后**喷**`小傅哥`！还得到很多小伙伴的认可！`「在此感谢」`

---

整个对这次项目来说，只有专栏文章部分是付费的，源码其实一开始就是开源的。只不过我粉丝少宣传不到而已！

所以这次想通过将这份开源`IM`代码部署到云上验证功能，从而加强实践的能力。同时也为了多宣传一下，`真心感谢每一位分享和点赞的小伙伴`！

好！那么接下来我们开始进行服务上云。

*云市场太多，虽然占了伙伴便宜，要了大厂的一个月免费云服务，但没有让我打广告！*

## 二、代码开源

**开源代码** 关注公众号：[`bugstack虫洞栈`](https://bugstack.cn/assets/images/qrcode.png)，回复*源码下载*。`你会获得一个下载链接列表，打开后里面的第15个「因为我有好多开源代码」`，记得给个`Star`！

这套 `IM` 代码分为了三组模块；UI、客户端、服务端。之所以这样拆分，是为了将UI展示与业务逻辑隔离，使用事件和接口进行驱动，让代码层次更加干净整洁易于扩展和维护。

| 序号 | 工程 | 介绍 |
|:---|:---|:---|
| 1 | itstack-naive-chat-ui | 使用JavaFx开发的UI端，在我们的UI端中提供了；登录框体、聊天框体，同时在聊天框体中有大量的行为交互界面以及接口和事件。最终我的UI端使用Maven打包的方式向外提供Jar包，以此来达到UI界面与业务行为流程分离。 |
| 2 | itstack-naive-chat-client | 客户端是我们的通信核心工程，主要使用Netty4.x作为我们的socket框架来完成通信交互。并且在此工程中负责引入UI的Jar包，完成UI定义的事件(登录验证、搜索添加好友、对话通知、发送信息等等)，以及需要使用我们在服务端工程定义的通信协议来完成信息的交互操作。 |
| 3 | itstack-navie-chat-server | 服务端同样使用Netty4.x作为socket的通信框架，同时在服务端使用Layui作为管理后台的页面，并且我们的服务端采用偏向于DDD领域驱动设计的方式与Netty集合，以此来达到我们的框架结构整洁干净易于扩展。 |
| 4 | itstack.sql | 系统工程数据库表结构以及初始化数据信息，共计6张核心表；用户表、群组表、用户群组关联表、好友表、对话表以及聊天记录表。用户在实际业务开发中可以自行拓展完善，目前库表结构只以核心功能为基础。 |

## 三、功能概述

在这套`IM`中，服务端采用`DDD`领域驱动设计模式进行搭建。将 Netty 的功能交给 `SpringBoot` 进行启停控制，同时在服务端搭建控制台可以非常方便的操作通信系统，进行用户和通信管理。在客户端的建设上采用`UI`分离的方式进行搭建，以保证业务代码与`UI`展示分离，做到非常易于扩展的控制。

另外在功能实现上包括；完美仿照微信桌面版客户端、登录、搜索添加好友、用户通信、群组通信、表情发送等核心功能。如果有对于实际需要使用的功能，可以按照这套系统框架进行扩展。具体功能点如下；

![IM功能概述](https://bugstack.cn/assets/images/2020/p-xmind.png)

## 四、项目演示

>登陆页面

![登陆页面](https://bugstack.cn/assets/images/2020/ui-00.png)

>聊天页面

![聊天页面](https://bugstack.cn/assets/images/2020/ui-01.png)

>添加好友

![添加好友](https://bugstack.cn/assets/images/2020/ui-02.png)

>消息提醒

![消息提醒](https://bugstack.cn/assets/images/2020/ui-05.png)

## 五、服务上云

### 1. 选择云提供方

*其实云市场有很多，甚至你什么都不做广告都会打到你们口。*

包括；阿里云、华为云、腾讯云、京东云、等等，按照自己喜好下手。我这里是小伙伴赠送的华为云的免费试用，你那按需选择即可。

### 2. 环境配置

**安装包**

1. jdk-8u231-linux-i586.tar.gz
2. apache-tomcat-8.5.37.tar.gz 

**工具**

1. FTP 工具
    1. Mac：FileZilla
    2. Win：Flxe Ftp
    
2. Linux远程连接工具
    1. Mac：用自带的就可以
    2. Win：Xhell 非常好用

**命令**

1. 解压缩：tar -zxvf
2. 环境变量配置 `vi /etc/profile`
    
  ```java
  #JDK全局环境变量配置
  export JAVA_HOME=/usr/local/java/jdk1.8.0_231
  e.xport CLASSPATH=$:CLASSPATH:$JAVA_HOME/lib/
  export PATH=$PATH:$JAVA_HOME/bin
  ```
3. 查找占用端口杀死

 ```java
 fuser -v -n tcp 3389
 kill -s 9 2157
 ```

4. mysql授权

 ```java
 select user, host from mysql.user where user='root';
 GRANT all privileges ON 库名.* TO '数据库用户名'@'授权访问的IP' identified by '数据库用户密码';
 flush privileges;  
 ```

5. tomcat启停和查看日志

 ```java
 ./startup.sh 
 ./shutdown.sh 
 tail -f catalina.out 
 ```

### 3. 服务配置

关于服务配置在最上面的**视频**中进行演示操作，其实主要是将服务端代码部署到云服务中。这里最开始主要遇到了一些权限和端口访问的问题，整体来说还是比较顺畅的。

## 六、项目学习

**学习链接**：[https://chat.itstack.org/](https://chat.itstack.org/) - 代码**开源**，文章付费(自愿支持，交个朋友)

* [开篇词](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
* 第 1 章 - UI开发
    * [1.0：专栏学习简述以及全套源码获取](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [1.1：PC端微信页面拆分及JavaFx使用](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [1.2：登陆框体实现(结构定义、输入框和登陆)](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [1.3：登陆框体事件与接口](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [1.4：聊天框体实现一(整体结构定义、侧边栏)](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [1.5：聊天框体实现二(对话栏)](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [1.6：聊天框体实现三(对话聊天框)](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [1.7：聊天框体实现四(好友栏)](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [1.8：聊天框体实现五(好友填充框)](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [1.9：聊天框体事件定义](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [1.10：练习篇-聊天表情框体实现](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [1.11：解答篇-聊天表情框体实现](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
* 第 2 章 - 架构设计
    * [2.1：服务端架构设计](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [2.2：通信协议包定义](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [2.3：客户端架构设计](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [2.4：数据库表结构设计](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
* 第 3 章 - 功能实现 
    * [3.1：登陆功能实现](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [3.2：搜索和添加好友](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [3.3：对话通知与应答](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [3.4：用户与好友通信](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [3.5：用户与群组通信](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [3.6：断线重连恢复通信](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [3.7：服务端控制台搭建](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [3.8：练习篇-聊天表情发送功能实现](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)
    * [3.9：解答篇-聊天表情发送功能实现](https://gitbook.cn/gitchat/column/5e5d29ac3fbd2d3f5d05e05f)

## 七、总结

- 整篇内容主要讲解实战项目工程`《Netty+JavaFx实战：仿桌面版微信聊天》`的一个框架结构讲解，和部署到云服务器的过程。也是为了方便大家可以在学习过程中，迅速的入门然后深入学习。
- 源码是开放的按需获取进行改造成自己需要的就好，这部分源码也融合`小傅哥`一些开发经验，对于`架设出良好的系统结构`上，会有一定的提升。可以通过关注**公众号：`bugstack虫洞栈`**，进行获取。
- 编写技术文章的这段时间也遇到了很多号主好友，他们甚至`一直连续输出技术文章`，但是基本也没有什么流量。有时候好多好东西还是需要传播，分享，让多多的`原创和优质的内容`，多呈现在大家面前。比如那个并不简单的男人：`小傅哥`！
