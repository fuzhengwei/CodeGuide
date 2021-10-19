---
layout: post
category: itstack-demo-netty-2
title: netty案例，netty4.1中级拓展篇九《Netty集群部署实现跨服务端通信的落地方案》
tagline: by 付政委
tag: [netty,itstack-demo-netty-2]
lock: need
---

## 前言介绍
![微信公众号：bugstack虫洞栈](https://bugstack.cn/assets/images/pic-content/2019/09/netty-2-09-3.png)

Netty的性能非常好，在一些小型用户体量的socket服务内，仅部署单台机器就可以满足业务需求。但当遇到一些中大型用户体量的服务时，就需要考虑讲Netty按照集群方式部署，以更好的满足业务诉求。但Netty部署集群后都会遇到跨服务端怎么通信，也就是有集群服务X和Y，用户A链接服务X，用户B链接服务Y，那么他们都不在一个服务内怎么通信？本章节将介绍一种实现方式案例，以满足跨服务之间的用户通信。但实际场景中需要进行一些扩展性改造，案例仅将核心主干思路做以实现，只是一种思路指导，并不能直接使用于业务开发。

**本章知识点**
- 跨服务之间案例采用redis的发布和订阅进行传递消息，如果你是大型服务可以使用zookeeper
- 用户A在发送消息给用户B时候，需要传递B的channeId，以用于服务端进行查找channeId所属是否自己的服务内
- 单台机器也可以启动多个Netty服务，程序内会自动寻找可用端口


## 环境准备
1. jdk1.8【jdk1.7以下只能部分支持netty】
2. Netty4.1.36.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
3. NetAssist 网络调试助手，可以从网上下载也可以联系我，微信公众号：bugstack虫洞栈 | 关注回复你的邮箱
4. redis服务端，案例采用windows版本，按需从官网[下载即可](https://github.com/MicrosoftArchive/redis/tags)

## 代码示例
```java
itstack-demo-rpc-2-09
└── src
    └── main
    │   ├── java
    │   │   └── org.itstack.demo.netty
    │   │        ├── domain
    │   │        │   ├── EasyResult.java
    │   │        │   ├── MsgAgreement.java
    │   │        │   ├── ServerInfo.java
    │   │        │   └── UserChannelInfo.java	
    │   │        ├── redis
    │   │        │   ├── config
    │   │        │   │	 ├── PublisherConfig.java
    │   │        │   │   └── ReceiverConfig.java	
    │   │        │   ├── AbstractReceiver.java
    │   │        │   ├── MsgAgreementReceiver.java
    │   │        │   ├── Publisher.java
    │   │        │   └── RedisUtil.java
    │   │        ├── server
    │   │        │   ├── MyChannelInitializer.java
    │   │        │   ├── MyServerHandler.java
    │   │        │   └── NettyServer.java	
    │   │        ├── service
    │   │        │   └── ExtServerService.java	
    │   │        ├── util
    │   │        │   ├── CacheUtil.java
    │   │        │   ├── MsgUtil.java
    │   │        │   └── NetUtil.java	
    │   │        ├── web
    │   │        │	  └── NettyController.java
    │   │        └── Application.java
    │   ├── resources	
    │   │   └── application.yml
    │   └── webapp	
    │       ├── res		
    │       └── WEB-INF
    │        	└── index.jsp		
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```

**演示讲解部分重点代码块，完整代码下载，关注公众号；bugstack虫洞栈 | 回复：netty案例源码**

>domain/MsgAgreement.java | 定义信息传输协议，这个看似简单但非常重要，每一个通信的根本就是定义传输协议信息

```java
/**
 * 消息协议
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class MsgAgreement {

    private String toChannelId; //发送给某人，某人channelId
    private String content;     //消息内容

    public MsgAgreement() {
    }

    public MsgAgreement(String toChannelId, String content) {
        this.toChannelId = toChannelId;
        this.content = content;
    }

    public String getToChannelId() {
        return toChannelId;
    }

    public void setToChannelId(String toChannelId) {
        this.toChannelId = toChannelId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
```

>redis/config/PublisherConfig.java | redis消息发布者，集成与SpringBoot的配置方式

```java
/**
 * 发布者
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on @2019
 */
@Configuration
public class PublisherConfig {

    @Bean
    public RedisTemplate<String, Object> redisMessageTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(new FastJsonRedisSerializer<>(Object.class));
        return template;
    }

}
```

>redis/config/ReceiverConfig.java | redis消息的订阅者，集成与SpringBoot的配置方式。可以订阅多个主题，本章节只订阅一个。

```java
/**
 * 订阅者
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on @2019
 */
@Configuration
public class ReceiverConfig {

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter msgAgreementListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(msgAgreementListenerAdapter, new PatternTopic("itstack-demo-netty-push-msgAgreement"));
        return container;
    }

    @Bean
    public MessageListenerAdapter msgAgreementListenerAdapter(MsgAgreementReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}
```

>redis/MsgAgreementReceiver.java | 实现抽象类用于接收订阅到的消息，接收消息后进行业务处理

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on @2019
 */
@Service
public class MsgAgreementReceiver extends AbstractReceiver {

    private Logger logger = LoggerFactory.getLogger(MsgAgreementReceiver.class);

    @Override
    public void receiveMessage(Object message) {
        logger.info("接收到PUSH消息：{}", message);
        MsgAgreement msgAgreement = JSON.parseObject(message.toString(), MsgAgreement.class);
        String toChannelId = msgAgreement.getToChannelId();
        Channel channel = CacheUtil.cacheChannel.get(toChannelId);
        if (null == channel) return;
        channel.writeAndFlush(MsgUtil.obj2Json(msgAgreement));
    }

}
```

>redis/RedisUtil.java | redis操作工具类，帮助存储数据。以下是将链接到服务的用户信息存放到redis方便可以在每个服务端都能看到这份用户链接数据。

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * Create by fuzhengwei on 2019
 */
@Service("redisUtil")
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void pushObj(UserChannelInfo userChannelInfo) {
        redisTemplate.opsForHash().put("itstack-demo-netty-2-09-user", userChannelInfo.getChannelId(), JSON.toJSONString(userChannelInfo));
    }

    public List<UserChannelInfo> popList() {
        List<Object> values = redisTemplate.opsForHash().values("itstack-demo-netty-2-09-user");
        if (null == values) return new ArrayList<>();
        List<UserChannelInfo> userChannelInfoList = new ArrayList<>();
        for (Object strJson : values) {
            userChannelInfoList.add(JSON.parseObject(strJson.toString(), UserChannelInfo.class));
        }
        return userChannelInfoList;
    }

    public void remove(String channelId) {
        redisTemplate.opsForHash().delete("itstack-demo-netty-2-09-user",channelId);
    }

    public void clear(){
        redisTemplate.delete("itstack-demo-netty-2-09-user");
    }

}
```

>server/MyServerHandler.java | 处理接收到的信息，尤其在channelRead中，将接受者不是本服务端的用户，进行全局push

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(MyServerHandler.class);

    private ExtServerService extServerService;

    public MyServerHandler(ExtServerService extServerService) {
        this.extServerService = extServerService;
    }

    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("链接报告开始");
        System.out.println("链接报告信息：有一客户端链接到本服务端。channelId：" + channel.id());
        System.out.println("链接报告IP:" + channel.localAddress().getHostString());
        System.out.println("链接报告Port:" + channel.localAddress().getPort());
        System.out.println("链接报告完毕");

        //保存用户信息
        UserChannelInfo userChannelInfo = new UserChannelInfo(channel.localAddress().getHostString(), channel.localAddress().getPort(), channel.id().toString(), new Date());
        extServerService.getRedisUtil().pushObj(userChannelInfo);
        CacheUtil.cacheChannel.put(channel.id().toString(), channel);
        //通知客户端链接建立成功
        String str = "通知客户端链接建立成功" + " " + new Date() + " " + channel.localAddress().getHostString() + "\r\n";
        ctx.writeAndFlush(MsgUtil.buildMsg(channel.id().toString(), str));

    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开链接" + ctx.channel().localAddress().toString());
        extServerService.getRedisUtil().remove(ctx.channel().id().toString());
        CacheUtil.cacheChannel.remove(ctx.channel().id().toString(), ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object objMsgJsonStr) throws Exception {
        //接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息内容：" + objMsgJsonStr);

        MsgAgreement msgAgreement = MsgUtil.json2Obj(objMsgJsonStr.toString());

        String toChannelId = msgAgreement.getToChannelId();
        //判断接收消息用户是否在本服务端
        Channel channel = CacheUtil.cacheChannel.get(toChannelId);
        if (null != channel) {
            channel.writeAndFlush(MsgUtil.obj2Json(msgAgreement));
            return;
        }
        //如果为NULL则接收消息的用户不在本服务端，需要push消息给全局
        logger.info("接收消息的用户不在本服务端，PUSH！");
        extServerService.push(msgAgreement);
    }

    /**
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        extServerService.getRedisUtil().remove(ctx.channel().id().toString());
        CacheUtil.cacheChannel.remove(ctx.channel().id().toString(), ctx.channel());
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }

}
```

>util/CacheUtil.java | 缓存必要信息，用于业务流程处理

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class CacheUtil {

    // 缓存channel
    public static Map<String, Channel> cacheChannel = Collections.synchronizedMap(new HashMap<String, Channel>());

    // 缓存服务信息
    public static Map<Integer, ServerInfo> serverInfoMap = Collections.synchronizedMap(new HashMap<Integer, ServerInfo>());

    // 缓存服务端
    public static Map<Integer, NettyServer> serverMap = Collections.synchronizedMap(new HashMap<Integer, NettyServer>());

}
```

>web/NettyController.java | 接口处理控制类，方便我们操作服务端方法，包括启动Netty服务、获取用户信息等

```java
/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on @2019
 */
@Controller
public class NettyController {

    private Logger logger = LoggerFactory.getLogger(NettyController.class);
    //默认线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Value("${server.port}")
    private int serverPort;
    @Autowired
    private ExtServerService extServerService;
    @Resource
    private RedisUtil redisUtil;
    //Netty服务端
    private NettyServer nettyServer;

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("serverPort", serverPort);
        return "index";
    }

    @RequestMapping("/openNettyServer")
    @ResponseBody
    public EasyResult openNettyServer() {
        try {
            int port = NetUtil.getPort();
            logger.info("启动Netty服务，获取可用端口：{}", port);
            nettyServer = new NettyServer(new InetSocketAddress(port), extServerService);
            Future<Channel> future = executorService.submit(nettyServer);
            Channel channel = future.get();
            if (null == channel) {
                throw new RuntimeException("netty server open error channel is null");
            }
            while (!channel.isActive()) {
                logger.info("启动Netty服务，循环等待启动...");
                Thread.sleep(500);
            }
            CacheUtil.serverInfoMap.put(port, new ServerInfo(NetUtil.getHost(), port, new Date()));
            CacheUtil.serverMap.put(port, nettyServer);
            logger.info("启动Netty服务，完成：{}", channel.localAddress());
            return EasyResult.buildSuccessResult();
        } catch (Exception e) {
            logger.error("启动Netty服务失败", e);
            return EasyResult.buildErrResult(e);
        }
    }

    @RequestMapping("/closeNettyServer")
    @ResponseBody
    public EasyResult closeNettyServer(int port) {
        try {
            logger.info("关闭Netty服务开始，端口：{}", port);
            NettyServer nettyServer = CacheUtil.serverMap.get(port);
            if (null == nettyServer) {
                CacheUtil.serverMap.remove(port);
                return EasyResult.buildSuccessResult();
            }
            nettyServer.destroy();
            CacheUtil.serverMap.remove(port);
            CacheUtil.serverInfoMap.remove(port);
            logger.info("关闭Netty服务完成，端口：{}", port);
            return EasyResult.buildSuccessResult();
        } catch (Exception e) {
            logger.error("关闭Netty服务失败，端口：{}", port, e);
            return EasyResult.buildErrResult(e);
        }
    }

    @RequestMapping("/queryNettyServerList")
    @ResponseBody
    public Collection<ServerInfo> queryNettyServerList() {
        try {
            Collection<ServerInfo> serverInfos = CacheUtil.serverInfoMap.values();
            logger.info("查询服务端列表。{}", JSON.toJSONString(serverInfos));
            return serverInfos;
        } catch (Exception e) {
            logger.info("查询服务端列表失败。", e);
            return null;
        }
    }

    @RequestMapping("/queryUserChannelInfoList")
    @ResponseBody
    public List<UserChannelInfo> queryUserChannelInfoList() {
        try {
            logger.info("查询用户列表信息开始");
            List<UserChannelInfo> userChannelInfoList = redisUtil.popList();
            logger.info("查询用户列表信息完成。list：{}", JSON.toJSONString(userChannelInfoList));
            return userChannelInfoList;
        } catch (Exception e) {
            logger.error("查询用户列表信息失败", e);
            return null;
        }
    }

}
```

>resources/application.yml | 基础配置，在我们启动服务端的时候，如果只有一台机器模拟，那么需要改变server.port端口｛8080、8081｝

```java
server:
  port: 8080

spring:
  mvc:
    view:
      prefix: /WEB-INF/
      suffix: .jsp
  redis:
    host: 127.0.0.1
    port: 6379
```

>index.jap | 页面操作，控制和展示的一些内容

```html
<%--
  虫洞栈：https://bugstack.cn
  公众号：bugstack虫洞栈  ｛获取学习源码｝
  Create by fuzhengwei on 2019
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>关注公众号：bugstack虫洞栈 | 专题案例开发，关注取源码 | bugstack.cn 付政委</title>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="res/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="res/js/themes/icon.css">
<script type="text/javascript" src="res/js/jquery.min.js"></script>
<script type="text/javascript" src="res/js/jquery.easyui.min.js"></script>

<style>

</style>

<script>
util = {
  formatDate: function (value, row, index) {
      if (null == value) return "";
      var date = new Date();
      date.setTime(value);
      return date.format('yyyy-MM-dd HH:mm:ss');
  }
};
</script>

</head>

<body>
	<div style="margin:20px 0;"></div>
	<table class="easyui-datagrid" title="localhost:${serverPort} | Netty服务端" style="width:700px;height:250px"
			data-options="rownumbers:true,singleSelect:true,url:'/queryNettyServerList',method:'get',toolbar:toolbar">
		<thead>
			<tr>
				<th data-options="field:'ip'">IP</th>
				<th data-options="field:'port'">端口</th>
				<th data-options="field:'openDate'">启动时间</th>
			</tr>
		</thead>
	</table>
	<script type="text/javascript">
		var toolbar = [{
			text:'启动',
			iconCls:'icon-open',
			handler:function(){
			    $.post('/openNettyServer',{}, function (res) {
                               if (res.success) {
                                  $.messager.show({
                                        title: '消息提示',
                                        msg: '启动成功，请稍后刷新页面！'
                                  });
                                   $('#easyui-datagrid').datagrid('reload');
                               } else {
                                   $.messager.show({
                                       title: 'Error',
                                       msg: res.msg
                                   });
                               }
                            }, 'json');
			}
		},'-',{
			text:'关闭',
			iconCls:'icon-close',
			handler:function(){
			     //可以自己添加实现
			}
		}];
	</script>
    <hr/>
	<!-- server-user -->
    <table class="easyui-datagrid" title="localhost:${serverPort} | 用户链接信息" style="width:700px;height:250px"
    			data-options="rownumbers:true,singleSelect:true,url:'/queryUserChannelInfoList',method:'get'">
    		<thead>
    			<tr>
    				<th data-options="field:'ip'">IP</th>
    				<th data-options="field:'port'">端口</th>
    				<th data-options="field:'channelId'">用户ID</th>
    				<th data-options="field:'linkDate'">链接时间</th>
    			</tr>
    		</thead>
    </table>
</body>
</html>
```

## 测试结果

>启动Redis服务 | 案例中使用windwos版本

![微信公众号：bugstack虫洞栈](https://bugstack.cn/assets/images/pic-content/2019/09/netty-2-09-1.png)

>启动2次SpringBoot，模拟Netty集群[不同端口8080、8081] | Plugins/spring-boot/run 双击启动

```java
2019-09-01 12:59:29.649  INFO 8952 --- [nio-8081-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring FrameworkServlet 'dispatcherServlet'
2019-09-01 12:59:29.649  INFO 8952 --- [nio-8081-exec-1] o.s.web.servlet.DispatcherServlet        : FrameworkServlet 'dispatcherServlet': initialization started
2019-09-01 12:59:29.681  INFO 8952 --- [nio-8081-exec-1] o.s.web.servlet.DispatcherServlet        : FrameworkServlet 'dispatcherServlet': initialization completed in 32 ms
2019-09-01 12:59:31.350  INFO 8952 --- [nio-8081-exec-2] o.i.demo.netty.web.NettyController       : 查询服务端列表。[]
2019-09-01 12:59:31.371  INFO 8952 --- [nio-8081-exec-3] o.i.demo.netty.web.NettyController       : 查询用户列表信息开始
2019-09-01 12:59:31.380  INFO 8952 --- [nio-8081-exec-3] o.i.demo.netty.web.NettyController       : 查询用户列表信息完成。list：[]
2019-09-01 13:04:22.864  INFO 8952 --- [nio-8081-exec-6] o.i.demo.netty.web.NettyController       : 启动Netty服务，获取可用端口：7398
2019-09-01 13:04:22.879  INFO 8952 --- [pool-1-thread-1] o.itstack.demo.netty.server.NettyServer  : itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}
2019-09-01 13:04:22.880  INFO 8952 --- [nio-8081-exec-6] o.i.demo.netty.web.NettyController       : 启动Netty服务，完成：/0:0:0:0:0:0:0:0:7398
2019-09-01 13:04:23.612  INFO 8952 --- [nio-8081-exec-9] o.i.demo.netty.web.NettyController       : 查询服务端列表。[{"ip":"10.13.28.13","openDate":1567314262880,"port":7398}]
2019-09-01 13:04:23.634  INFO 8952 --- [io-8081-exec-10] o.i.demo.netty.web.NettyController       : 查询用户列表信息开始
2019-09-01 13:04:23.636  INFO 8952 --- [io-8081-exec-10] o.i.demo.netty.web.NettyController       : 查询用户列表信息完成。list：[]
链接报告开始
链接报告信息：有一客户端链接到本服务端。channelId：3a2d5cee
链接报告IP:10.13.28.13
链接报告Port:7398
链接报告完毕
2019-09-01 13:04:42.704  INFO 8952 --- [nio-8081-exec-2] o.i.demo.netty.web.NettyController       : 查询服务端列表。[{"ip":"10.13.28.13","openDate":1567314262880,"port":7398}]
2019-09-01 13:04:42.738  INFO 8952 --- [nio-8081-exec-3] o.i.demo.netty.web.NettyController       : 查询用户列表信息开始
2019-09-01 13:04:42.755  INFO 8952 --- [nio-8081-exec-3] o.i.demo.netty.web.NettyController       : 查询用户列表信息完成。list：[{"channelId":"39d45ff7","ip":"10.13.28.13","linkDate":1567314278944,"port":7397},{"channelId":"3a2d5cee","ip":"10.13.28.13","linkDate":1567314280442,"port":7398}]
2019-09-01 13:05:25.545  INFO 8952 --- [    container-2] o.i.d.netty.redis.MsgAgreementReceiver   : 接收到PUSH消息：{"content":"hi! I'm 微信公众号：bugstack虫洞栈 | 欢迎关注&获取源码。* 来自A服务端里用户向B服务端里用户发送信息。[结尾换行，用于处理半包粘包]","toChannelId":"3a2d5cee"}
2019-09-01 13:05:26.107  INFO 8952 --- [    container-3] o.i.d.netty.redis.MsgAgreementReceiver   : 接收到PUSH消息：{"content":"hi! I'm 微信公众号：bugstack虫洞栈 | 欢迎关注&获取源码。* 来自A服务端里用户向B服务端里用户发送信息。[结尾换行，用于处理半包粘包]","toChannelId":"3a2d5cee"}
2019-09-01 13:05:27.025  INFO 8952 --- [    container-4] o.i.d.netty.redis.MsgAgreementReceiver   : 接收到PUSH消息：{"content":"hi! I'm 微信公众号：bugstack虫洞栈 | 欢迎关注&获取源码。* 来自A服务端里用户向B服务端里用户发送信息。[结尾换行，用于处理半包粘包]","toChannelId":"3a2d5cee"}
2019-09-01 13:05:27.545  INFO 8952 --- [    container-5] o.i.d.netty.redis.MsgAgreementReceiver   : 接收到PUSH消息：{"content":"hi! I'm 微信公众号：bugstack虫洞栈 | 欢迎关注&获取源码。* 来自A服务端里用户向B服务端里用户发送信息。[结尾换行，用于处理半包粘包]","toChannelId":"3a2d5cee"}
2019-09-01 13:05:28.559  INFO 8952 --- [    container-6] o.i.d.netty.redis.MsgAgreementReceiver   : 接收到PUSH消息：{"content":"hi! I'm 微信公众号：bugstack虫洞栈 | 欢迎关注&获取源码。* 来自A服务端里用户向B服务端里用户发送信息。[结尾换行，用于处理半包粘包]","toChannelId":"3a2d5cee"}

```

>启动2个以上的NetAssist分别链接到不同的服务端，以模拟测试跨服务通信，最后在客户端发送消息传递给另外一个不在本服务端的客户端。

```java
{"content":"hi! I'm 微信公众号：bugstack虫洞栈 | 欢迎关注&获取源码。* 来自A服务端里用户向B服务端里用户发送信息。[结尾换行，用于处理半包粘包]","toChannelId":"3a2d5cee"}
```

>最终运行效果

![微信公众号：bugstack虫洞栈](https://bugstack.cn/assets/images/pic-content/2019/09/netty-2-09-2.png)

------------

上一篇：[netty案例，netty4.1中级拓展篇八《Netty心跳服务与断线重连》](/itstack-demo-netty-2/2019/08/23/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%85%AB-Netty%E5%BF%83%E8%B7%B3%E6%9C%8D%E5%8A%A1%E4%B8%8E%E6%96%AD%E7%BA%BF%E9%87%8D%E8%BF%9E.html)

下一篇：[netty案例，netty4.1中级拓展篇十《Netty接收发送多种协议消息类型的通信处理方案》](/itstack-demo-netty-2/2019/08/25/netty%E6%A1%88%E4%BE%8B-netty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%8D%81-Netty%E6%8E%A5%E6%94%B6%E5%8F%91%E9%80%81%E5%A4%9A%E7%A7%8D%E5%8D%8F%E8%AE%AE%E6%B6%88%E6%81%AF%E7%B1%BB%E5%9E%8B%E7%9A%84%E9%80%9A%E4%BF%A1%E5%A4%84%E7%90%86%E6%96%B9%E6%A1%88.html)

微信搜索「**bugstack虫洞栈**」公众号，关注后回复「**Netty专题案例**」获取本文源码&更多原创专题案例！
