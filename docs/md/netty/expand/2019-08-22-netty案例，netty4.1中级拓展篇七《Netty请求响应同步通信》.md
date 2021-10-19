---
layout: post
category: itstack-demo-netty-2
title: netty案例，netty4.1中级拓展篇七《Netty请求响应同步通信》
tagline: by 付政委
tag: [netty,itstack-demo-netty-2]
lock: need
---

## 前言介绍
在我们实现开发RPC框架的时候，需要选择socket的通信方式。而我们知道一般情况下socket通信类似与qq聊天，发过去消息，什么时候回复都可以。但是我们RPC框架通信，从感觉上类似http调用，需要在一定时间内返回，否则就会发生超时断开。这里我们选择netty作为我们的socket框架，采用future方式进行通信。
>Dubbo：国内最早开源的 RPC 框架，由阿里巴巴公司开发并于 2011 年末对外开源，仅支持 Java 语言。
Motan：微博内部使用的 RPC 框架，于 2016 年对外开源，仅支持 Java 语言。
Tars：腾讯内部使用的 RPC 框架，于 2017 年对外开源，仅支持 C++ 语言。
Spring Cloud：国外 Pivotal 公司 2014 年对外开源的 RPC 框架，仅支持 Java 语言
gRPC：Google 于 2015 年对外开源的跨语言 RPC 框架，支持多种语言。
Thrift：最初是由 Facebook 开发的内部系统跨语言的 RPC 框架，2007 年贡献给了 Apache 基金，成为 Apache 开源项目之一，支持多种语言。
hprose：一个MIT开源许可的新型轻量级跨语言跨平台的面向对象的高性能远程动态通讯中间件。它支持众多语言:nodeJs, C++, .NET, Java, Delphi, Objective-C, ActionScript, JavaScript, ASP, PHP, Python, Ruby, Perl, Golang 。

## 环境准备
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】

## 代码示例
```java
itstack-demo-rpc-2-07
└── src
    └── main
    │    └── java
    │        └── org.itstack.demo.netty
    │             ├── client
    │             │   ├── ClientSocket.java
    │             │   └── MyClientHandler.java  
    │             ├── codec
    │             │   ├── RpcDecoder.java
    │             │   └── RpcEncoder.java  
    │             ├── future
    │             │   ├── SyncWrite.java    
    │             │   ├── SyncWriteFuture.java  
    │             │   ├── SyncWriteMap.java 
    │             │   └── WriteFuture.java  
    │             ├── msg
    │             │   ├── Request.java
    │             │   └── Response.java 
    │             ├── server
    │             │   ├── MyServerHandler.java
    │             │   └── ServerSocket.java     
    │             └── util
    │                 └── SerializationUtil.java    
    └── test
         └── java
             └── org.itstack.demo.test
                 ├── StartClient.java
                 └── StartServer.java    
```

** 展示部分重要代码块，完整代码可以关注公众号获取；bugstack虫洞栈 **

>MyClientHandler.java 

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class MyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        Response msg = (Response) obj;
        String requestId = msg.getRequestId();
        SyncWriteFuture future = (SyncWriteFuture) SyncWriteMap.syncKey.get(requestId);
        if (future != null) {
            future.setResponse(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
```

>SyncWrite.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class SyncWrite {

    public Response writeAndSync(final Channel channel, final Request request, final long timeout) throws Exception {

        if (channel == null) {
            throw new NullPointerException("channel");
        }
        if (request == null) {
            throw new NullPointerException("request");
        }
        if (timeout <= 0) {
            throw new IllegalArgumentException("timeout <= 0");
        }

        String requestId = UUID.randomUUID().toString();
        request.setRequestId(requestId);

        WriteFuture<Response> future = new SyncWriteFuture(request.getRequestId());
        SyncWriteMap.syncKey.put(request.getRequestId(), future);

        Response response = doWriteAndSync(channel, request, timeout, future);

        SyncWriteMap.syncKey.remove(request.getRequestId());
        return response;
    }

    private Response doWriteAndSync(final Channel channel, final Request request, final long timeout, final WriteFuture<Response> writeFuture) throws Exception {

        channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                writeFuture.setWriteResult(future.isSuccess());
                writeFuture.setCause(future.cause());
                //失败移除
                if (!writeFuture.isWriteSuccess()) {
                    SyncWriteMap.syncKey.remove(writeFuture.requestId());
                }
            }
        });

        Response response = writeFuture.get(timeout, TimeUnit.MILLISECONDS);
        if (response == null) {
            if (writeFuture.isTimeout()) {
                throw new TimeoutException();
            } else {
                // write exception
                throw new Exception(writeFuture.cause());
            }
        }
        return response;
    }

}
```

>MyServerHandler.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj){
        Request msg = (Request) obj;
        //反馈
        Response request = new Response();
        request.setRequestId(msg.getRequestId());
        request.setParam(msg.getResult() + " 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。");
        ctx.writeAndFlush(request);
        //释放
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

}
```

>StartClient.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class StartClient {

    private static ChannelFuture future;

    public static void main(String[] args) {
        ClientSocket client = new ClientSocket();
        new Thread(client).start();

        while (true) {
            try {
                //获取future，线程有等待处理时间
                if (null == future) {
                    future = client.getFuture();
                    Thread.sleep(500);
                    continue;
                }
                //构建发送参数
                Request request = new Request();
                request.setResult("查询｛bugstack虫洞栈｝用户信息");
                SyncWrite s = new SyncWrite();
                Response response = s.writeAndSync(future.channel(), request, 1000);
                System.out.println("调用结果：" + JSON.toJSON(response));
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

```

>StartServer.java

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class StartServer {

    public static void main(String[] args) {
        new Thread(new ServerSocket()).start();
        System.out.println("itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}");
    }

}
```

## 测试结果

>启动StartServer

```java
itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}
```

>启动StartClient

```java
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"188ba00e-3a0d-4094-9475-c7ee93104011"}
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"bc9d63d4-9d37-406a-9c0f-a68211ac466f"}
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"cceb510b-8179-46ab-abc6-eb7d5b6c0ac2"}
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"f65aafd0-07b7-4bdb-be80-a57b4c58ad2d"}
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"e6700074-380b-441f-ae0d-f71dcd7f84c9"}
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"25a0a9d5-46d5-4da6-ad3f-1496ca20bb17"}
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"052dce21-dfb9-42d7-bbcf-46137b9933df"}
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"4fd968fa-3171-4e4f-838d-4a215a90da00"}
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"97f6eb6e-8c5d-49f4-beba-2ba7e1ff953f"}
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"ffc48115-8e62-43a8-b3f7-035390427d37"}
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"4bafefc9-4beb-49f6-8126-29e0e03a55d1"}
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"10367786-24fd-4f11-9465-bcd2c87b4027"}
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"82293ad3-a694-405c-ab03-01624f38b1ad"}
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"0c163cb8-be5f-4697-931f-61f5bf487bae"}
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"0f1e7611-4fd0-43b4-86dc-fce09965046e"}
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"f22ac0dc-974c-4de1-bcdf-1566ca0b2305"}
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"1907456b-2507-4ddd-8c1e-a86c990e3e72"}
调用结果：{"param":"查询｛bugstack虫洞栈｝用户信息 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。","requestId":"6a99b1f2-5859-4ed7-9d17-98229c13250f"}

Process finished with exit code -1

```
![](https://bugstack.cn/assets/images/pic-content/2019/08/netty-2-07-1.png)

------------

上一篇：[netty案例，netty4.1中级拓展篇六《SpringBoot+Netty+Elasticsearch收集日志信息数据存储》](/itstack-demo-netty-2/2019/08/21/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%85%AD-SpringBoot+Netty+Elasticsearch%E6%94%B6%E9%9B%86%E6%97%A5%E5%BF%97%E4%BF%A1%E6%81%AF%E6%95%B0%E6%8D%AE%E5%AD%98%E5%82%A8.html)

下一篇：[netty案例，netty4.1中级拓展篇八《Netty心跳服务与断线重连》](/itstack-demo-netty-2/2019/08/23/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%85%AB-Netty%E5%BF%83%E8%B7%B3%E6%9C%8D%E5%8A%A1%E4%B8%8E%E6%96%AD%E7%BA%BF%E9%87%8D%E8%BF%9E.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！
