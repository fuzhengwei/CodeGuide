---
layout: post
category: itstack-demo-any
title: 源码分析 | 像盗墓一样分析Spring是怎么初始化xml并注册bean的
tagline: by 付政委
tag: [itstack-demo-code,itstack-demo-any]
excerpt: 往往简单的背后都有人为你承担着不简单，Spring 就是这样的家伙！而分析它的源码就像鬼吹灯，需要寻龙、点穴、分金、定位，最后往往受点伤(时间)、流点血(精力)、才能获得宝藏(成果)。
lock: need
---

# 源码分析 | 像盗墓一样分析Spring是怎么初始化xml并注册bean的

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言介绍
往往简单的背后都有人为你承担着不简单，Spring 就是这样的家伙！而分析它的源码就像鬼吹灯，需要寻龙、点穴、分金、定位，最后往往受点伤(时间)、流点血(精力)、才能获得宝藏(成果)。

另外鉴于之前分析spring-mybatis、quartz，一篇写了将近2万字，内容过于又长又干，挖藏师好辛苦，看戏的也憋着肾，所以这次分析spring源码分块解读，便于理解、便于消化。

那么，这一篇就从 bean 的加载开始，从 xml 配置文件解析 bean 的定义，到注册到 Spring 的核心类 DefaultListableBeanFactory ，盗墓过程如下；

![微信公众号：bugstack虫洞栈 & 盗墓](https://bugstack.cn/assets/images/pic-content/2019/11/itstack-demo-code-spring-02.png)

从上图可以看到从 xml 解析出 bean 到注册完成需要经历过8个核心类以及22个方法跳转流程，这也是本文后面需要重点分析的内容。好！那么就当为了你的**钱程**一起盗墓吧！

## 二、案例工程

对于源码分析一定要单独列一个简单的工程，一针见血的搞你最需要的地方，模拟、验证、调试。现在这个案例工程还很简单，随着后面分析内容的增加，会不断的扩充。整体工程可以下载，可以关注公众号：bugstack虫洞栈 | 回复：源码分析

```java
itstack-demo-code-spring
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.demo
    │   │       └── UserService.java   
    │   └── resources	
    │       └── spring-config.xml
    └── test
         └── java
             └── org.itstack.demo.test			
                 └── ApiTest.java
```

## 三、环境配置
1. JDK 1.8
2. IDEA 2019.3.1
3. Spring 4.3.24.RELEASE

## 四、源码分析

整个 bean 注册过程核心功能包括；配置文件加载、工厂创建、XML解析、Bean定义、Bean注册，执行流程如下；

![微信公众号：bugstack虫洞栈 & 盗墓](https://bugstack.cn/assets/images/pic-content/2019/11/itstack-demo-code-spring-01.png)

从上图的注册 bean 流程看到，核心类包括；
- ClassPathXmlApplicationContext
- AbstractXmlApplicationContext
- AbstractRefreshableApplicationContext
- AbstractXmlApplicationContext
- AbstractBeanDefinitionReader
- XmlBeanDefinitionReader
- DefaultBeanDefinitionDocumentReader
- DefaultListableBeanFactory

好！摸金分金定穴完事，搬山的搬山、卸岭的卸岭，开始搞！

### 1. 先扔个 helloworld 测试下

>UserService.java & 定义一个 bean，Spring 万物皆可 bean

```java
public class UserService {

    public String queryUserInfo(Long userId) {
        return "花花 id：" + userId;
    }

}
```

>spring-config.xml & 在 xml 配置 bean 内容

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd"
       default-autowire="byName">

    <bean id="userService" class="org.itstack.demo.UserService" scope="singleton"/>

</beans>
```

>ApiTest.java & 单元测试类

```java
@Test
public void test_XmlBeanFactory() {
    BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("spring-config.xml"));
    UserService userService = beanFactory.getBean("userService", UserService.class);
    logger.info("测试结果：{}", userService.queryUserInfo(1000L));
}

@Test
public void test_ClassPathXmlApplicationContext() {
    BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring-config.xml");
    UserService userService = beanFactory.getBean("userService", UserService.class);
    logger.info("测试结果：{}", userService.queryUserInfo(1000L));
}
```

两个单测方法都可以做结果输出，但是 XmlBeanFactory 已经标记为 @Deprecated 就是告诉我们这个墓穴啥也没有了，被盗过了，别看了。好！我们也不看他了，我们看现在推荐的2号墓  ClassPathXmlApplicationContext

如上不出意外正确结果如下；

```java
23:34:24.699 [main] INFO  org.itstack.demo.test.ApiTest - 测试结果：花花 id：1000

Process finished with exit code 0
```

### 2. 把 xml 解析过程搞定

在整个 bean 的注册过程中，xml 解析是非常大的一块，也是非常重要的一块。如果顺着 bean 工厂初始化分析，那么一层层扒开，就像陈玉楼墓穴挖到一半，遇到大蜈蚣一样难缠。所以我们先把蜈蚣搞定！

```java
@Test
public void test_DocumentLoader() throws Exception {
    
	// 设置资源
    EncodedResource encodedResource = new EncodedResource(new ClassPathResource("spring-config.xml"));
    
	// 加载解析
    InputSource inputSource = new InputSource(encodedResource.getResource().getInputStream());
    DocumentLoader documentLoader = new DefaultDocumentLoader();
    Document doc = documentLoader.loadDocument(inputSource, new ResourceEntityResolver(new PathMatchingResourcePatternResolver()), new DefaultHandler(), 3, false);
    
	// 输出结果
    Element root = doc.getDocumentElement();
    NodeList nodeList = root.getChildNodes();
    for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (!(node instanceof Element)) continue;
        Element ele = (Element) node;
        if (!"bean".equals(ele.getNodeName())) continue;
        String id = ele.getAttribute("id");
        String clazz = ele.getAttribute("class");
        String scope = ele.getAttribute("scope");
        logger.info("测试结果 beanName：{} beanClass：{} scope：{}", id, clazz, scope);
    }
	
}
```

可能初次看这段代码还是有点晕的，但这样提炼出来单独解决，至少给你一种有抓手的感觉。在 spring 解析 xml 时候首先是将配置资源交给 ClassPathResource ，再通过构造函数传递给 EncodedResource；

```java
private EncodedResource(Resource resource, String encoding, Charset charset) {
	super();
	Assert.notNull(resource, "Resource must not be null");
	this.resource = resource;
	this.encoding = encoding;
	this.charset = charset;
}
```

以上这个过程还是比较简单的，只是一个初始化过程。接下来是通过 Document 解析处理 xml 文件。这个过程是仿照 spring 创建时候需要的参数信息进行组装，如下；

```java
documentLoader.loadDocument(inputSource, new ResourceEntityResolver(new PathMatchingResourcePatternResolver()), new DefaultHandler(), 3, false);

public Document loadDocument(InputSource inputSource, EntityResolver entityResolver,
		ErrorHandler errorHandler, int validationMode, boolean namespaceAware) throws Exception {
	DocumentBuilderFactory factory = createDocumentBuilderFactory(validationMode, namespaceAware);
	if (logger.isDebugEnabled()) {
		logger.debug("Using JAXP provider [" + factory.getClass().getName() + "]");
	}
	DocumentBuilder builder = createDocumentBuilder(factory, entityResolver, errorHandler);
	return builder.parse(inputSource);
}
```

通过上面的代码获取 org.w3c.dom.Document, Document 里面此时包含了所有 xml 的各个 Node 节点信息，最后输出节点内容，如下；

```java
Element root = doc.getDocumentElement();
NodeList nodeList = root.getChildNodes();
```

好！测试一下，正常情况下，结果如下；

```java
23:47:00.249 [main] INFO  org.itstack.demo.test.ApiTest - 测试结果 beanName：userService beanClass：org.itstack.demo.UserService scope：singleton

Process finished with exit code 0
```

可以看到的我们的 xml 配置内容已经完完整整的取出来了，接下来就交给 spring 进行处理了。红姑娘、鹧鸪哨、咱们出发！

### 3. ClassPathXmlApplicationContext 构造函数初始化过程

>ClassPathXmlApplicationContext.java & 截取部分代码

```java
public ClassPathXmlApplicationContext(String[] configLocations, boolean refresh, ApplicationContext parent)
		throws BeansException {
	super(parent);
	setConfigLocations(configLocations);
	if (refresh) {
		refresh();
	}
}
```

- **源码139行：** setConfigLocations 设置我们的配置的资源位置信息
- 重点在 refresh() 这个方法里面内容非常多，随着文章的编写会陆续分析。

### 4. AbstractApplicationContext 初始化工厂 

>AbstractApplicationContext.java & 部分代码截取

```java
@Override
public void refresh() throws BeansException, IllegalStateException {
	synchronized (this.startupShutdownMonitor) {
		// 设置容器初始化
		prepareRefresh();
		
		// 让子类进行 BeanFactory 初始化，并且将 Bean 信息 转换为 BeanFinition，最后注册到容器中
		// 注意，此时 Bean 还没有初始化，只是配置信息都提取出来了
		ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

		...
	}
}
```

- **源码514行：** 这一行是我们重点往后分析的内容，它主要开始处理 xml 中 bean 的初始化过程，但此时不会注册，意思就是你通过 beanFactory.getBean 还获得不到内容

>AbstractApplicationContext.java & 部分代码截取

```java
protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
	refreshBeanFactory();
	ConfigurableListableBeanFactory beanFactory = getBeanFactory();
	if (logger.isDebugEnabled()) {
		logger.debug("Bean factory for " + getDisplayName() + ": " + beanFactory);
	}
	return beanFactory;
}
```

- 这一层方法在处理完解析后还会返回 bean 工厂
- **源码614行：** 回到我们主线继续分析解析过程，往下一层继续看

### 5. AbstractRefreshableApplicationContext 刷新上下文

>AbstractRefreshableApplicationContext.java & 部分代码截取

```java
protected final void refreshBeanFactory() throws BeansException {
	if (hasBeanFactory()) {
		destroyBeans();
		closeBeanFactory();
	}
	try {
		DefaultListableBeanFactory beanFactory = createBeanFactory();
		beanFactory.setSerializationId(getId());
		customizeBeanFactory(beanFactory);
		loadBeanDefinitions(beanFactory);
		synchronized (this.beanFactoryMonitor) {
			this.beanFactory = beanFactory;
		}
	}
	catch (IOException ex) {
		throw new ApplicationContextException("I/O error parsing bean definition source for " + getDisplayName(), ex);
	}
}
```

- 这里包括判断对 bean 工厂判断的以及销毁和初始化创建
- **源码129行：** loadBeanDefinitions(beanFactory);，获取 bean 工厂后继续我们 bean 注册过程

### 6. AbstractXmlApplicationContext xml配置处理

>AbstractXmlApplicationContext.java & 部分代码截取

```java
protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
	// Create a new XmlBeanDefinitionReader for the given BeanFactory.
	XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
	
	// Configure the bean definition reader with this context's
	// resource loading environment.
	beanDefinitionReader.setEnvironment(this.getEnvironment());
	beanDefinitionReader.setResourceLoader(this);
	beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));
	
	// Allow a subclass to provide custom initialization of the reader,
	// then proceed with actually loading the bean definitions.
	initBeanDefinitionReader(beanDefinitionReader);
	loadBeanDefinitions(beanDefinitionReader);
}
```

- **源码82行：** XmlBeanDefinitionReader 定义配置文件读取类，并设置基础的属性信息，getEnvironment、ResourceEntityResolver
- **源码93行：** loadBeanDefinitions 在拿到 beanDefinitionReader 继续执行

>AbstractXmlApplicationContext.java & 部分代码截取

```java
protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException, IOException {
	Resource[] configResources = getConfigResources();
	if (configResources != null) {
		reader.loadBeanDefinitions(configResources);
	}
	String[] configLocations = getConfigLocations();
	if (configLocations != null) {
		reader.loadBeanDefinitions(configLocations);
	}
}
```

- **源码121行：** 获取我们最开始设置的资源信息，在这里也就是 spring-config.xml
- **源码127行：** 通过 beanDefinitionReader 开始加载解析配置文件

### 7. AbstractBeanDefinitionReader 配置文件加载

>AbstractBeanDefinitionReader.java & 部分代码截取

```java
public int loadBeanDefinitions(String... locations) throws BeanDefinitionStoreException {
	Assert.notNull(locations, "Location array must not be null");
	int counter = 0;
	for (String location : locations) {
		counter += loadBeanDefinitions(location);
	}
	return counter;
}
```

- 抽象类是中提供了加载解析的方法，每解析一组就计数一次
- **源码252行：** loadBeanDefinitions(location) 循环加载 bean 的定义进行解析

>AbstractBeanDefinitionReader.java & 部分代码截取

```java
public int loadBeanDefinitions(String location) throws BeanDefinitionStoreException {
	return loadBeanDefinitions(location, null);
}
```

- 类内部提供的单个解析方法，没有什么特别。继续往下

>AbstractBeanDefinitionReader.java & 部分代码截取

```java
public int loadBeanDefinitions(String location, Set<Resource> actualResources) throws BeanDefinitionStoreException {
	ResourceLoader resourceLoader = getResourceLoader();
	if (resourceLoader == null) {
		throw new BeanDefinitionStoreException(
				"Cannot import bean definitions from location [" + location + "]: no ResourceLoader available");
	}
	
	if (resourceLoader instanceof ResourcePatternResolver) {
		// Resource pattern matching available.
		try {
			Resource[] resources = ((ResourcePatternResolver) resourceLoader).getResources(location);
			int loadCount = loadBeanDefinitions(resources);
			if (actualResources != null) {
				for (Resource resource : resources) {
					actualResources.add(resource);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Loaded " + loadCount + " bean definitions from location pattern [" + location + "]");
			}
			return loadCount;
		}
		catch (IOException ex) {
			throw new BeanDefinitionStoreException(
					"Could not resolve bean definition resource pattern [" + location + "]", ex);
		}
	}
	else {
		// Can only load single resources by absolute URL.
		Resource resource = resourceLoader.getResource(location);
		int loadCount = loadBeanDefinitions(resource);
		if (actualResources != null) {
			actualResources.add(resource);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Loaded " + loadCount + " bean definitions from location [" + location + "]");
		}
		return loadCount;
	}
}
```

- **源码217行：** 获取资源解析，最终执行到 loadBeanDefinitions(resources);，继续往下

### 8. XmlBeanDefinitionReader 配置解析

>XmlBeanDefinitionReader.java & 部分代码截取

```java
public int loadBeanDefinitions(EncodedResource encodedResource) throws BeanDefinitionStoreException {
	
	// 判断验证
	...
	
	try {
		InputStream inputStream = encodedResource.getResource().getInputStream();
		try {
			InputSource inputSource = new InputSource(inputStream);
			if (encodedResource.getEncoding() != null) {
				inputSource.setEncoding(encodedResource.getEncoding());
			}
			return doLoadBeanDefinitions(inputSource, encodedResource.getResource());
		}
		finally {
			inputStream.close();
		}
	}
	catch (IOException ex) {
		throw new BeanDefinitionStoreException(
				"IOException parsing XML document from " + encodedResource.getResource(), ex);
	}
	finally {
		currentResources.remove(encodedResource);
		if (currentResources.isEmpty()) {
			this.resourcesCurrentlyBeingLoaded.remove();
		}
	}
}
```

- **源码330行 -> 336行：** 这个就是 xml 的解析过程，在我们最开始优先分析的部分，这一部分真正的要为解析 xml 做准备

### 9. XmlBeanDefinitionReader 配置文件读取

>XmlBeanDefinitionReader.java & 部分代码截取

```java
protected int doLoadBeanDefinitions(InputSource inputSource, Resource resource)
		throws BeanDefinitionStoreException {
	try {
		Document doc = doLoadDocument(inputSource, resource);
		return registerBeanDefinitions(doc, resource);
	} catch(){}
	
}
```

- **源码391行：** 此时就获取到了 Document ，这里面就包括了所有的节点信息，也就是我们的 bean 的定义
- **源码392行：** 通过 doc 与 资源信息开始定义 bean 等待注册，这个注册 bean 的过程是需要先定义 bean 的内容，每一个 bean 都需要用 BeanDefinitionHolder 封装

### 10. DefaultBeanDefinitionDocumentReader 定义bean类

>DefaultBeanDefinitionDocumentReader.java  & 部分代码截取

```java
public void registerBeanDefinitions(Document doc, XmlReaderContext readerContext) {
	this.readerContext = readerContext;
	logger.debug("Loading bean definitions");
	Element root = doc.getDocumentElement();
	doRegisterBeanDefinitions(root);
}
```

- **源码93行：** 越来越熟悉了吧，开始获取节点元素了，也就可以获取 bean 信息

>DefaultBeanDefinitionDocumentReader.java & 部分代码截取

```java
protected void parseBeanDefinitions(Element root, BeanDefinitionParserDelegate deleg
	if (delegate.isDefaultNamespace(root)) {
		NodeList nl = root.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				if (delegate.isDefaultNamespace(ele)) {
					parseDefaultElement(ele, delegate);
				}
				else {
					delegate.parseCustomElement(ele);
				}
			}
		}
	}
	else {
		delegate.parseCustomElement(root);
	}
}
```

- NodeList 循环处理节点内容，开启注册
- **源码169行：** parseDefaultElement(ele, delegate); 解析元素操作

>DefaultBeanDefinitionDocumentReader.java & 部分代码截取

```java
private void parseDefaultElement(Element ele, BeanDefinitionParserDelegate delegate) {
	if (delegate.nodeNameEquals(ele, IMPORT_ELEMENT)) {
		importBeanDefinitionResource(ele);
	}
	else if (delegate.nodeNameEquals(ele, ALIAS_ELEMENT)) {
		processAliasRegistration(ele);
	}
	else if (delegate.nodeNameEquals(ele, BEAN_ELEMENT)) {
		processBeanDefinition(ele, delegate);
	}
	else if (delegate.nodeNameEquals(ele, NESTED_BEANS_ELEMENT)) {
		// recurse
		doRegisterBeanDefinitions(ele);
	}
}
```

- 这个方法会根据不同的节点类型；IMPORT_ELEMENT、ALIAS_ELEMENT、BEAN_ELEMENT、NESTED_BEANS_ELEMENT，进行不同的操作
- **源码190行：** 这里我们只需要关注 processBeanDefinition(ele, delegate) 即可，处理 bean 操作

>DefaultBeanDefinitionDocumentReader.java & 部分代码截取

```java
protected void processBeanDefinition(Element ele, BeanDefinitionParserDelegate delegate) {
	BeanDefinitionHolder bdHolder = delegate.parseBeanDefinitionElement(ele);
	if (bdHolder != null) {
		bdHolder = delegate.decorateBeanDefinitionIfRequired(ele, bdHolder);
		try {
			// Register the final decorated instance.
			BeanDefinitionReaderUtils.registerBeanDefinition(bdHolder, getReaderContext().getRegistry());
		}
		catch (BeanDefinitionStoreException ex) {
			getReaderContext().error("Failed to register bean definition with name '" +
					bdHolder.getBeanName() + "'", ele, ex);
		}
		// Send registration event.
		getReaderContext().fireComponentRegistered(new BeanComponentDefinition(bdHolder));
	}
}
```

- 如果你认真的读文章了，BeanDefinitionHolder 我们在上面已经说过一次，这是每一个 bean 都会定义的操作，最后交给注册中心
- **源码304行：** BeanDefinitionReaderUtils.registerBeanDefinition，类里的一个静态注册操作方法

### 11. BeanDefinitionReaderUtils bean注册工具类

>BeanDefinitionReaderUtils.java & 部分代码截取

```java
public static void registerBeanDefinition(
		BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry)
		throws BeanDefinitionStoreException {
	
	// Register bean definition under primary name.
	String beanName = definitionHolder.getBeanName();
	registry.registerBeanDefinition(beanName, definitionHolder.getBeanDefinition());
	
	// Register aliases for bean name, if any.
	String[] aliases = definitionHolder.getAliases();
	if (aliases != null) {
		for (String alias : aliases) {
			registry.registerAlias(beanName, alias);
		}
	}
}
```

- **源码149行：** 将 beanName、BeanDefinition，一同交给最后的注册中心，最后这个就是 DefaultListableBeanFactory

### 12. DefaultListableBeanFactory bean核心注册中心

>DefaultListableBeanFactory.java & 部分代码截取

```java
public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
		throws BeanDefinitionStoreException {
	
	Assert.hasText(beanName, "Bean name must not be empty");
	Assert.notNull(beanDefinition, "BeanDefinition must not be null");
	
	if (beanDefinition instanceof AbstractBeanDefinition) {
		try {
			((AbstractBeanDefinition) beanDefinition).validate();
		}
		catch (BeanDefinitionValidationException ex) {
			throw new BeanDefinitionStoreException(beanDefinition.getResourceDescription(), beanName,
					"Validation of bean definition failed", ex);
		}
	}
	
	BeanDefinition existingDefinition = this.beanDefinitionMap.get(beanName);
	if (existingDefinition != null) {
		...
	}
	else {
		
		...
		
		else {
			// Still in startup registration phase
			this.beanDefinitionMap.put(beanName, beanDefinition);
			this.beanDefinitionNames.add(beanName);
			this.manualSingletonNames.remove(beanName);
		}
		this.frozenBeanDefinitionNames = null;
	}
	if (existingDefinition != null || containsSingleton(beanName)) {
		resetBeanDefinition(beanName);
	}
}
```

- **源码853行：** 这就是最终我们将 xml 中的配置信息注册到了配置中心，beanDefinitionMap，同时还会写入到 beanDefinitionNames

- 看下最终的注入结果，嗯！我们的盗墓挖到了一点宝物；

	![微信公众号：bugstack虫洞栈 & bean注册结果](https://bugstack.cn/assets/images/pic-content/2019/11/itstack-demo-code-spring-04.png)


## 五、综上总结

- 陈玉楼的盗墓(源码分析)，初步确定了路线、墓室、干掉了蜈蚣，今天大家胜利而归，开始收拾整理装备
- 源码分析真的就像盗墓一样，分析前一切都是陌生的，一遍遍的分析后会从里面不断的获取宝藏，这个宝藏的多少取决你对他的挖掘深度
- 本次只是简单的分析了一个 xml 中配置的 bean 注册的过程，此时还没有真正的生成 bean，等下篇文章继续分析





