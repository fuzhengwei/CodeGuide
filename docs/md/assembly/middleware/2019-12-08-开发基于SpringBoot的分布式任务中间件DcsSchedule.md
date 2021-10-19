---
layout: post
category: itstack-ark-middleware
title: 开发基于SpringBoot的分布式任务中间件DcsSchedule(为开源贡献力量)
tagline: by 付政委
tag: [java,itstack-ark-middleware]
excerpt: 如果我们的任务是比较大型的，比如；定时跑批T+1结算、商品秒杀前状态变更、刷新数据预热到缓存等等，这些定时任务都相同的特点；作业量大、实时性强、可用率高。而这时候如果只是单纯使用Schedule就显得不足以控制
lock: need
---

# 开发基于SpringBoot的分布式任务中间件DcsSchedule(为开源贡献力量)

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

- 分布式任务DcsSchedule中间件，Github地址：[https://github.com/fuzhengwei/schedule-spring-boot-starter](https://github.com/fuzhengwei/schedule-spring-boot-starter)
- 分布式任务DcsSchedule控制台，Github地址：[https://github.com/fuzhengwei/itstack-middleware-control](https://github.com/fuzhengwei/itstack-middleware-control)
- 欢迎⭐Star和使用，你用剑🗡、我用刀🔪，好的代码都很烧😏，望你不吝出招💨！

## 前言

---

```java
@SpringBootApplication
@EnableScheduling
public class Application{
    public static void mian(String[] args){
        SpringApplication.run(Application.class,args);
    }
	
	@Scheduled(cron = "0/3 * * * * *")
	public void demoTask() {
		//...
	}
}
```

咔咔，上面这段代码很熟悉吧，他就是SpringBoot的Schedule定时任务，简单易用。在我们开发中如果需要做一些定时或指定时刻循环执行逻辑时候，基本都会使用到Schedule。

但是，如果我们的任务是比较大型的，比如；定时跑批T+1结算、商品秒杀前状态变更、刷新数据预热到缓存等等，这些定时任务都相同的特点；作业量大、实时性强、可用率高。而这时候如果只是单纯使用Schedule就显得不足以控制。

那么，我们产品需求就出来了，分布式DcsSchedule任务；
1. 多机器部署任务
2. 统一控制中心启停
3. 宕机灾备，自动启动执行
4. 实时检测任务执行信息：部署数量、任务总量、成功次数、失败次数、执行耗时等

嗯？有人憋半天了想说可以用~~Quertz~~，嗯可以的，但这不是本篇文章的重点。难道你不想看看一个自言开源中间件是怎么诞生的吗，怎么推到中心Maven仓的吗？比如下图；**真香不！**

>**首页监控**
![微信公众号：bugstack虫洞栈 & 首页监控](https://bugstack.cn/assets/images/pic-content/2019/11/itstack-middleware-schedule-release-00.png)

>**任务列表**
![微信公众号：bugstack虫洞栈 & 任务列表](https://bugstack.cn/assets/images/pic-content/2019/11/itstack-middleware-schedule-release-01.png)

😀好了，接下来开始介绍这个中间件如何使用和怎么开发的了！

## 中间件使用

---

### 1. 版本记录

|  |  版本   |    发布日期      |   备注 |
|:--------:|:---------|:---------|:---------|
| 1 | 1.0.0-RELEASE | 2019-12-07 |  基本功能实现；任务接入、分布式启停    |
| 2 | ~~1.0.1-RELEASE~~ | 2019-12-07 |  上传测试版本    |

### 2. 环境准备

1. jdk1.8 
2. StringBoot 2.x
3. 配置中心zookeeper 3.4.14 {准备好zookeeper服务，如果windows调试可以从这里下载：https://www-eu.apache.org/dist/zookeeper}
  1. 下载后解压，在bin同级路径创建文件夹data、logs
  2. 修改conf/zoo.cfg，修改配置如下；
    
	 ```xml
	 dataDir=D:\\Program Files\\apache-zookeeper-3.4.14\\data
	 dataLogDir=D:\\Program Files\\apache-zookeeper-3.4.14\\logs
	 ```

4. 打包部署控制平台
  1. 下载地址：https://github.com/fuzhengwei/itstack-middleware-control.git
  2. 部署访问：http://localhost:7397

### 3. 配置POM

```xml
<dependency>
    <groupId>org.itstack.middleware</groupId>
    <artifactId>schedule-spring-boot-starter</artifactId>
    <version>1.0.0-RELEASE</version>
</dependency>
```

### 4. 引入分布式任务DcsSchedule @EnableDcsScheduling

1. 与SpringBoot的Sceduling非常像，他的注解是；@EnableScheduling，尽可能降低使用难度
2. 这个注解主要方便给我们自己的中间件一个入口，也是😏扒拉源码发现的可以这么干{我一直说好的代码都很骚气}

```java
@SpringBootApplication
@EnableDcsScheduling
public class HelloWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }

}
```

### 5. 在任务方法上添加注解

1. 这个注解也和SpringBoot的Schedule很像，但是多了desc描述和启停初始化控制
2. cron：执行计划
3. desc：任务描述
4. autoStartup：默认启动状态
5. 如果你的任务需要参数可以通过引入service去调用获取等方式都可以

```java
@Component("demoTaskThree")
public class DemoTaskThree {
	
    @DcsScheduled(cron = "0 0 9,13 * * *", desc = "03定时任务执行测试：taskMethod01", autoStartup = false)
    public void taskMethod01() {
        System.out.println("03定时任务执行测试：taskMethod01");
    }

    @DcsScheduled(cron = "0 0/30 8-10 * * *", desc = "03定时任务执行测试：taskMethod02", autoStartup = false)
    public void taskMethod02() {
        System.out.println("03定时任务执行测试：taskMethod02");
    }

}
```

### 6. 启动验证

1. 启动SpringBoot工程即可，autoStartup = true的会自动启动任务(任务是多线程并行执行的)
2. 启动控制平台：itstack-middleware-control，访问：http://localhost:7397/ 成功界面如下；*可以开启/关闭验证了！{功能还在完善}*
   ![微信公众号：bugstack虫洞栈 & 任务列表](https://bugstack.cn/assets/images/pic-content/2019/11/itstack-middleware-schedule-release-01.png)


## 中间件开发

---

以SpringBoot为基础开发一款中间件我也是第一次，因为接触SpringBoot也刚刚1个月左右。虽然SpringBoot已经出来挺久的了，但由于我们项目开发并不使用SpringBoot的一套东西，所以一直依赖没有接触。直到上个月开始考虑领域驱动设计才接触，嗯！真的不错，那么就开始了夯实技能、学习思想用到项目里。

按照我的产品需求，开发这么一款分布式任务的中间件，我脑袋中的模型已经存在了。另外就是需要开发过程中去探索我需要的知识工具，简单包括；
1. 读取Yml自定义配置
2. 使用zookeeper作为配置中心，这样如果有机器宕机了就可以通过临时节点监听知道
3. 通过Spring类；ApplicationContextAware, BeanPostProcessor, ApplicationListener，执行服务启动、注解扫描、节点挂在
4. 分布式任务统一控制台，来管理任务

### 1. 工程模型

```java
schedule-spring-boot-starter
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.middleware.schedule
    │   │       ├── annotation
    │   │       │	├── DcsScheduled.java	
    │   │       │	└── EnableDcsScheduling.java
    │   │       ├── annotation	
    │   │       │	└── InstructStatus.java	
    │   │       ├── config
    │   │       │	├── DcsSchedulingConfiguration.java	
    │   │       │	├── StarterAutoConfig.java	
    │   │       │	└── StarterServiceProperties.java	
    │   │       ├── domain
    │   │       │	├── DataCollect.java	
    │   │       │	├── DcsScheduleInfo.java	
    │   │       │	├── DcsServerNode.java	
    │   │       │	├── ExecOrder.java	
    │   │       │	└── Instruct.java
    │   │       ├── export	
    │   │       │	└── DcsScheduleResource.java
    │   │       ├── service
    │   │       │	├── HeartbeatService.java	
    │   │       │	└── ZkCuratorServer.java
    │   │       ├── task
    │   │       │	├── TaskScheduler.java	
    │   │       │	├── ScheduledTask.java	
    │   │       │	├── SchedulingConfig.java	
    │   │       │	└── SchedulingRunnable.java	
    │   │       ├── util
    │   │       │	└── StrUtil.java	
    │   │       └── DoJoinPoint.java
    │   └── resources	
    │       └── META_INF
    │           └── spring.factories	
    └── test
        └── java
            └── org.itstack.demo.test
                └── ApiTest.java
```

### 2. 代码讲解

1. 篇幅较长，只讲解部分重点代码块，如果你愿意参与到开源编写，可以和我申请
2. 我说过好的代码都很骚气，那么就从这部分入手吧

#### 2.1 自定义注解

>annotation/EnableDcsScheduling.java & 自定义注解

这个注解一堆的圈A，这些配置都是为了开始启动执行我们的中间件；
- Target 标识需要放到类上执行
- Retention 注释将由编译器记录在类文件中，并且在运行时由VM保留，因此可以反射地读取它们
- Import 引入入口资源，在程序启动时会执行到自己定义的类中，以方便我们；初始化配置/服务、启动任务、挂在节点
- ComponentScan 告诉程序扫描位置

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({DcsSchedulingConfiguration.class})
@ImportAutoConfiguration({SchedulingConfig.class, CronTaskRegister.class, DoJoinPoint.class})
@ComponentScan("org.itstack.middleware.*")
public @interface EnableDcsScheduling {
}
```

#### 2.2 扫描自定义注解、初始化配置/服务、启动任务、挂在节点 

>config/DcsSchedulingConfiguration.java & 初始化配置/服务、启动任务、挂在节点

- 写到这的时候，我们的自定义注解有了，已经写到方法上了，那么我们怎么拿到呢？
- 需要通过实现BeanPostProcessor.postProcessAfterInitialization，在每个bean实例化的时候进行扫描
- 这里遇到一个有趣的问题，一个方法会得到两次，因为有一个CGLIB给代理的，像真假美猴王一样，几乎一毛一样。😏扒了源码才看到，生命注解批注没有。好那就可以判断了！method.getDeclaredAnnotations()
- 我们将扫描下来的任务信息汇总到Map中，当Spring初始化完成后，在执行我们中间件内容。{太早执行有点喧宾夺主了！主要人家也不让呀，给你抛异常😭。}

```java
@Override
public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
	Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
	if (this.nonAnnotatedClasses.contains(targetClass)) return bean;
	Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
	if (methods == null) return bean;
	for (Method method : methods) {
		DcsScheduled dcsScheduled = AnnotationUtils.findAnnotation(method, DcsScheduled.class);
		if (null == dcsScheduled || 0 == method.getDeclaredAnnotations().length) continue;
		List<ExecOrder> execOrderList = Constants.execOrderMap.computeIfAbsent(beanName, k -> new ArrayList<>());
		ExecOrder execOrder = new ExecOrder();
		execOrder.setBean(bean);
		execOrder.setBeanName(beanName);
		execOrder.setMethodName(method.getName());
		execOrder.setDesc(dcsScheduled.desc());
		execOrder.setCron(dcsScheduled.cron());
		execOrder.setAutoStartup(dcsScheduled.autoStartup());
		execOrderList.add(execOrder);
		this.nonAnnotatedClasses.add(targetClass);
	}
	return bean;
}
``` 

- 初始化服务连接zookeeper配置中心
- 连接后将创建我们的节点以及添加监听，这个监听主要负责分布式消息通知，收到通知负责控制任务启停
- 这里包括了循环创建节点以及批量节点删除，似乎！面试题会问😏

```java
private void init_server(ApplicationContext applicationContext) {
    try {
        //获取zk连接
        CuratorFramework client = ZkCuratorServer.getClient(Constants.Global.zkAddress);
        //节点组装
        path_root_server = StrUtil.joinStr(path_root, LINE, "server", LINE, schedulerServerId);
        path_root_server_ip = StrUtil.joinStr(path_root_server, LINE, "ip", LINE, Constants.Global.ip);
        //创建节点&递归删除本服务IP下的旧内容
        ZkCuratorServer.deletingChildrenIfNeeded(client, path_root_server_ip);
        ZkCuratorServer.createNode(client, path_root_server_ip);
        ZkCuratorServer.setData(client, path_root_server, schedulerServerName);
        //添加节点&监听
        ZkCuratorServer.createNodeSimple(client, Constants.Global.path_root_exec);
        ZkCuratorServer.addTreeCacheListener(applicationContext, client, Constants.Global.path_root_exec);
    } catch (Exception e) {
        logger.error("itstack middleware schedule init server error！", e);
        throw new RuntimeException(e);
    }
}
```

- 启动标记了True的Schedule任务
- Scheduled默认是单线程执行的，这里扩展为多线程并行执行

```java
private void init_task(ApplicationContext applicationContext) {
    CronTaskRegister cronTaskRegistrar = applicationContext.getBean("itstack-middlware-schedule-cronTaskRegister", CronTaskRegister.class);
    Set<String> beanNames = Constants.execOrderMap.keySet();
    for (String beanName : beanNames) {
        List<ExecOrder> execOrderList = Constants.execOrderMap.get(beanName);
        for (ExecOrder execOrder : execOrderList) {
            if (!execOrder.getAutoStartup()) continue;
            SchedulingRunnable task = new SchedulingRunnable(execOrder.getBean(), execOrder.getBeanName(), execOrder.getMethodName());
            cronTaskRegistrar.addCronTask(task, execOrder.getCron());
        }
    }
}
```

- 挂在任务节点到zookeeper挂在
- 按照不同的场景，有些内容是挂在到虚拟机节点。{😏又来个面试题，虚拟节点数据怎么挂在，创建的是永久节点，那么虚拟值怎么加？}
- path_root_server_ip_clazz_method；这个结构是：根目录、服务、IP、类、方法

```java
private void init_node() throws Exception {
	Set<String> beanNames = Constants.execOrderMap.keySet();
	for (String beanName : beanNames) {
		List<ExecOrder> execOrderList = Constants.execOrderMap.get(beanName);
		for (ExecOrder execOrder : execOrderList) {
			String path_root_server_ip_clazz = StrUtil.joinStr(path_root_server_ip, LINE, "clazz", LINE, execOrder.getBeanName());
			String path_root_server_ip_clazz_method = StrUtil.joinStr(path_root_server_ip_clazz, LINE, "method", LINE, execOrder.getMethodName());
			String path_root_server_ip_clazz_method_status = StrUtil.joinStr(path_root_server_ip_clazz, LINE, "method", LINE, execOrder.getMethodName(), "/status");
			//添加节点
			ZkCuratorServer.createNodeSimple(client, path_root_server_ip_clazz);
			ZkCuratorServer.createNodeSimple(client, path_root_server_ip_clazz_method);
			ZkCuratorServer.createNodeSimple(client, path_root_server_ip_clazz_method_status);
			//添加节点数据[临时]
			ZkCuratorServer.appendPersistentData(client, path_root_server_ip_clazz_method + "/value", JSON.toJSONString(execOrder));
			//添加节点数据[永久]
			ZkCuratorServer.setData(client, path_root_server_ip_clazz_method_status, execOrder.getAutoStartup() ? "1" : "0");
		}
	}
}
```

#### 2.3 zookeeper控制服务

>service/ZkCuratorServer.java & zk服务

- 这里提供一个zk的方法集合，其中比较重要的方法添加监听
- zookeeper有一个特性是对这个监听后，当节点内容发生变化时会收到通知，当然宕机也是收得到的，这个也就是我们后面开发灾备的核心触发点

```java
public static void addTreeCacheListener(final ApplicationContext applicationContext, final CuratorFramework client, String path) throws Exception {
	TreeCache treeCache = new TreeCache(client, path);
	treeCache.start();
	treeCache.getListenable().addListener((curatorFramework, event) -> {
		//...
		switch (event.getType()) {
			case NODE_ADDED:
			case NODE_UPDATED:
				if (Constants.Global.ip.equals(instruct.getIp()) && Constants.Global.schedulerServerId.equals(instruct.getSchedulerServerId())) {
					//执行命令
					Integer status = instruct.getStatus();
					switch (status) {
						case 0: //停止任务
							cronTaskRegistrar.removeCronTask(instruct.getBeanName() + "_" + instruct.getMethodName());
							setData(client, path_root_server_ip_clazz_method_status, "0");
							logger.info("itstack middleware schedule task stop {} {}", instruct.getBeanName(), instruct.getMethodName());
							break;
						case 1: //启动任务
							cronTaskRegistrar.addCronTask(new SchedulingRunnable(scheduleBean, instruct.getBeanName(), instruct.getMethodName()), instruct.getCron());
							setData(client, path_root_server_ip_clazz_method_status, "1");
							logger.info("itstack middleware schedule task start {} {}", instruct.getBeanName(), instruct.getMethodName());
							break;
						case 2: //刷新任务
							cronTaskRegistrar.removeCronTask(instruct.getBeanName() + "_" + instruct.getMethodName());
							cronTaskRegistrar.addCronTask(new SchedulingRunnable(scheduleBean, instruct.getBeanName(), instruct.getMethodName()), instruct.getCron());
							setData(client, path_root_server_ip_clazz_method_status, "1");
							logger.info("itstack middleware schedule task refresh {} {}", instruct.getBeanName(), instruct.getMethodName());
							break;
					}
				}
				break;
			case NODE_REMOVED:
				break;
			default:
				break;
		}
	});
}
```

#### 2.4 并行任务注册

- 由于默认的SpringBoot是单线程的，所以这里改造了下，可以支持多线程并行执行
- 包括了添加任务和删除任务，也就是执行取消future.cancel(true)

```java
public void addCronTask(SchedulingRunnable task, String cronExpression) {
    if (null != Constants.scheduledTasks.get(task.taskId())) {
        removeCronTask(task.taskId());
    }
    CronTask cronTask = new CronTask(task, cronExpression);
    Constants.scheduledTasks.put(task.taskId(), scheduleCronTask(cronTask));
}
public void removeCronTask(String taskId) {
    ScheduledTask scheduledTask = Constants.scheduledTasks.remove(taskId);
    if (scheduledTask == null) return;
    scheduledTask.cancel();
}
```

#### 2.5 待扩展的自定义AOP

- 我们最开始配置的扫描@ComponentScan("org.itstack.middleware.*")，主要用到这里的自定义注解，否则是扫描不到的，也就是你自定义切面失效的效果
- 目前这里的功能并没有扩展，基本只是打印执行耗时，后续完善的任务执行耗时监听等，就需要这里来完善

```java
@Pointcut("@annotation(org.itstack.middleware.schedule.annotation.DcsScheduled)")
public void aopPoint() {
}

@Around("aopPoint()")
public Object doRouter(ProceedingJoinPoint jp) throws Throwable {
	long begin = System.currentTimeMillis();
	Method method = getMethod(jp);
	try {
		return jp.proceed();
	} finally {
		long end = System.currentTimeMillis();
		logger.info("\nitstack middleware schedule method：{}.{} take time(m)：{}", jp.getTarget().getClass().getSimpleName(), method.getName(), (end - begin));
	}
}
```

### 3. Jar包发布

开发完成后还是需要将Jar包发布到manven中心仓库的，这个过程较长单独写了博客；[发布Jar包到Maven中央仓库(为开发开源中间件做准备)](https://bugstack.cn/itstack-demo-any/2019/12/07/%E5%8F%91%E5%B8%83Jar%E5%8C%85%E5%88%B0Maven%E4%B8%AD%E5%A4%AE%E4%BB%93%E5%BA%93(%E4%B8%BA%E5%BC%80%E5%8F%91%E5%BC%80%E6%BA%90%E4%B8%AD%E9%97%B4%E4%BB%B6%E5%81%9A%E5%87%86%E5%A4%87).html)

## 综上总结

1. 要开发要实现的还很多，一个周末也干不完所有的！而且需要有想法的小猿/媛伴一起加入！🙂 😀 😏
2. 这里没有讲解分布式任务中间件控制平台itstack-middleware-control，因为比较简单只是使用了中间件的zk功能接口做展示和操作。
3. 中间件开发是一件非常有意思的事情，不同于业务它更像易筋经，寺庙老僧，剑走偏锋，驰骋纵横，骚招满屏。
