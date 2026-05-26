---
layout: post
category: interview
title: 面经手册 · 第43篇《MyBatis 和 Spring 怎么整合？SqlSessionTemplate 和自动装配原理》
tagline: by 小傅哥
tag: [java,interview]
excerpt: 实际项目中 MyBatis 几乎都是和 Spring/Spring Boot 配合使用。SqlSessionTemplate 是怎么解决线程安全问题的？@MapperScan 是怎么扫描 Mapper 接口的？Spring Boot 自动装配做了什么？本文从整合方式到底层源码，把 Spring 整合彻底讲透。
lock: need
---

# 面经手册 · 第43篇《MyBatis 和 Spring 怎么整合？SqlSessionTemplate 和自动装配原理》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

实际项目中，没有人会手写 `SqlSessionFactoryBuilder` 来使用 MyBatis。MyBatis 几乎总是和 Spring/Spring Boot 搭配使用。

但大多数人只是照着模板写配置，面试官问一句"SqlSessionTemplate 原理是什么"、"@MapperScan 是怎么扫描 Mapper 的"，就答不上来了。

## 二、面试题

`谢飞机，小记！`，面试继续。

**面试官**：MyBatis 和 Spring 怎么整合的？

**谢飞机**：用 mybatis-spring 依赖。

**面试官**：整合的核心类有哪些？

**谢飞机**：SqlSessionFactoryBean？

**面试官**：SqlSessionTemplate 呢？它是线程安全的吗？

**谢飞机**：是线程安全的……

**面试官**：SqlSession 不是线程安全的吗？SqlSessionTemplate 怎么做到线程安全的？

**谢飞机**：代理？

**面试官**：Spring Boot 中 MyBatis 是怎么自动装配的？

**谢飞机**：starter？

**面试官**：具体说说我自动配置类做了什么？

**谢飞机**：不知道……再见！ヾ(￣▽￣)

## 三、整合核心组件

### 1. mybatis-spring 关键类

```
┌─────────────────────────────────────────────┐
│           mybatis-spring 整合架构            │
├─────────────────────────────────────────────┤
│  SqlSessionFactoryBean                      │
│    → 创建 SqlSessionFactory                  │
│    → 读取 DataSource 和 configLocation      │
│    → 扫描 Mapper XML                        │
├─────────────────────────────────────────────┤
│  SqlSessionTemplate                         │
│    → SqlSession 的线程安全代理               │
│    → 每次操作创建新 SqlSession               │
│    → 自动参与 Spring 事务                   │
├─────────────────────────────────────────────┤
│  MapperScannerConfigurer                     │
│    → 扫描 Mapper 接口                        │
│    → 注册为 Spring BeanDefinition            │
├─────────────────────────────────────────────┤
│  MapperFactoryBean                          │
│    → 为每个 Mapper 接口创建代理 Bean          │
│    → 从 SqlSessionTemplate 获取 Mapper      │
└─────────────────────────────────────────────┘
```

### 2. 整合依赖

```xml
<!-- MyBatis-Spring 整合 -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>3.0.3</version>
</dependency>

<!-- Spring Boot Starter -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>3.0.3</version>
</dependency>
```

## 四、SqlSessionFactoryBean

### 1. 配置方式

**XML 配置**：

```xml
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <property name="configLocation" value="classpath:mybatis-config.xml"/>
    <property name="mapperLocations" value="classpath:mapper/*.xml"/>
    <property name="typeAliasesPackage" value="com.example.entity"/>
</bean>
```

**Spring Boot 配置**：

```yaml
mybatis:
  config-location: classpath:mybatis-config.xml
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.example.entity
  configuration:
    map-underscore-to-camel-case: true
    cache-enabled: true
```

### 2. 源码追踪

```java
// org.mybatis.spring.SqlSessionFactoryBean
public class SqlSessionFactoryBean 
        implements FactoryBean<SqlSessionFactory>, InitializingBean, ApplicationListener<PooledConnectionAvailableEvent> {
    
    private DataSource dataSource;
    private Resource configLocation;
    private Resource[] mapperLocations;
    private String typeAliasesPackage;
    private Configuration configuration;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        // 1. 构建 Configuration
        this.sqlSessionFactory = buildSqlSessionFactory();
    }
    
    protected SqlSessionFactory buildSqlSessionFactory() throws Exception {
        Configuration targetConfiguration;
        
        // 如果有 configLocation，从 XML 文件解析
        if (this.configLocation != null) {
            XMLConfigBuilder xmlConfigBuilder = 
                new XMLConfigBuilder(this.configLocation.getInputStream(), ...);
            targetConfiguration = xmlConfigBuilder.parse();
        }
        // 否则使用空 Configuration
        else {
            targetConfiguration = new Configuration();
        }
        
        // 设置 DataSource
        targetConfiguration.setEnvironment(
            new Environment("development", 
                new SpringManagedTransactionFactory(), dataSource));
        
        // 解析 mapperLocations（Mapper XML 文件）
        if (this.mapperLocations != null) {
            for (Resource mapperLocation : this.mapperLocations) {
                XMLMapperBuilder xmlMapperBuilder = 
                    new XMLMapperBuilder(mapperLocation.getInputStream(), 
                        targetConfiguration, ...);
                xmlMapperBuilder.parse();
            }
        }
        
        return new DefaultSqlSessionFactory(targetConfiguration);
    }
    
    @Override
    public SqlSessionFactory getObject() {
        return this.sqlSessionFactory;
    }
}
```

## 五、SqlSessionTemplate 原理

### 1. 为什么需要 SqlSessionTemplate

SqlSession 不是线程安全的（持有 Connection + 一级缓存）。在 Spring 环境中，Service 层是单例的，多个线程共享同一个 Mapper 代理，直接用 SqlSession 会出问题。

SqlSessionTemplate 就是解决线程安全的方案。

### 2. 核心源码

```java
// org.mybatis.spring.SqlSessionTemplate
public class SqlSessionTemplate implements SqlSession, DisposableBean {
    
    private final SqlSessionFactory sqlSessionFactory;
    private final ExecutorType executorType;
    private final SqlSession sqlSessionProxy;  // ← 代理对象
    
    public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.sqlSessionProxy = (SqlSession) Proxy.newProxyInstance(
            SqlSessionFactory.class.getClassLoader(),
            new Class[] { SqlSession.class },
            new SqlSessionInterceptor());  // ← JDK 动态代理
    }
    
    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return this.sqlSessionProxy.selectOne(statement, parameter);
    }
    
    // 所有方法都委托给 sqlSessionProxy...
}
```

### 3. SqlSessionInterceptor（核心）

```java
// org.mybatis.spring.SqlSessionTemplate.SqlSessionInterceptor
private class SqlSessionInterceptor implements InvocationHandler {
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 1. 获取 SqlSession（从 Spring 事务同步器或新建）
        SqlSession sqlSession = getSqlSession(
            SqlSessionTemplate.this.sqlSessionFactory,
            SqlSessionTemplate.this.executorType,
            SqlSessionTemplate.this.exceptionTranslator);
        
        try {
            // 2. 执行方法
            Object result = method.invoke(sqlSession, args);
            
            // 3. 如果不是查询，标记为 dirty
            if (!isSqlSessionTransactional(sqlSession, SqlSessionTemplate.this.sqlSessionFactory)) {
                sqlSession.commit(true);  // 非事务环境下自动提交
            }
            
            return result;
        } catch (Throwable t) {
            // 4. 异常处理
            throw t;
        } finally {
            // 5. 关闭 SqlSession（非事务环境下）
            if (sqlSession != null) {
                closeSqlSession(sqlSession, SqlSessionTemplate.this.sqlSessionFactory);
            }
        }
    }
}
```

### 4. getSqlSession 详解

```java
// org.mybatis.spring.SqlSessionUtils
public static SqlSession getSqlSession(
        SqlSessionFactory sessionFactory, 
        ExecutorType executorType, ...) {
    
    // 1. 从事务同步管理器获取（事务环境下复用同一个 SqlSession）
    SqlSessionHolder holder = (SqlSessionHolder) 
        TransactionSynchronizationManager.getResource(sessionFactory);
    
    SqlSession session = sessionHolder(executorType, holder);
    if (session != null) {
        return session;  // 事务中复用
    }
    
    // 2. 新建 SqlSession
    session = sessionFactory.openSession(executorType);
    
    // 3. 注册到事务同步管理器（事务环境下）
    if (TransactionSynchronizationManager.isSynchronizationActive()) {
        holder = new SqlSessionHolder(session);
        TransactionSynchronizationManager.bindResource(sessionFactory, holder);
        TransactionSynchronizationManager.registerSynchronization(
            new SqlSessionSynchronization(holder, sessionFactory));
        holder.setSynchronizedWithTransaction(true);
    }
    
    return session;
}
```

### 5. 关键流程图解

```
非事务环境（每次新建）：
  每次调用 → getSqlSession() → 新建 SqlSession
  → 执行 SQL → 自动 commit → 自动 close
  
事务环境（复用）：
  第一次调用 → getSqlSession() → 新建 SqlSession → 绑定到 TransactionSynchronizationManager
  第二次调用 → getSqlSession() → 从同步管理器获取 → 复用同一个 SqlSession
  事务提交 → closeSqlSession() → 关闭
```

## 六、@MapperScan 原理

### 1. 配置方式

```java
@MapperScan("com.example.mapper")
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 2. 源码追踪

```java
// org.mybatis.spring.annotation.MapperScan
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MapperScannerRegistrar.class)  // ← 核心
public @interface MapperScan {
    String[] basePackages() default {};
    Class<?>[] basePackageClasses() default {};
    Class<? extends Annotation> annotationClass() default Annotation.class;
    Class<?> markerInterface() default Class.class;
    // ...
}
```

```java
// org.mybatis.spring.mapper.MapperScannerRegistrar
public class MapperScannerRegistrar implements ImportBeanDefinitionRegistrar {
    
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, 
            BeanDefinitionRegistry registry) {
        // 读取 @MapperScan 属性
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes
            .fromMap(importingClassMetadata.getAnnotationAttributes(MapperScan.class.getName()));
        
        ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
        // 配置扫描参数
        scanner.setAnnotationClass(mapperScanAttrs.getClass("annotationClass"));
        scanner.setMarkerInterface(mapperScanAttrs.getClass("markerInterface"));
        // ...
        
        // 扫描 basePackages
        scanner.doScan(StringUtils.toStringArray(basePackages));
    }
}
```

```java
// org.mybatis.spring.mapper.ClassPathMapperScanner
public class ClassPathMapperScanner extends ClassPathBeanDefinitionScanner {
    
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        // 1. 扫描接口
        Set<BeanDefinitionHolder> holders = super.doScan(basePackages);
        
        // 2. 将每个接口注册为 MapperFactoryBean
        for (BeanDefinitionHolder holder : holders) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            definition.setBeanClass(MapperFactoryBean.class);  // ← 关键
            // 设置 mapperInterface
            definition.getPropertyValues().add("mapperInterface", 
                definition.getBeanClassName());
        }
        
        return holders;
    }
}
```

### 3. MapperFactoryBean

```java
// org.mybatis.spring.mapper.MapperFactoryBean
public class MapperFactoryBean<T> extends SqlSessionDaoSupport 
        implements FactoryBean<T> {
    
    private Class<T> mapperInterface;
    
    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {
        return getSqlSession().getMapper(this.mapperInterface);
        // → SqlSessionTemplate.getMapper() → Configuration.getMapper()
        // → MapperRegistry.getMapper() → MapperProxyFactory.newInstance()
    }
    
    @Override
    public Class<T> getObjectType() {
        return this.mapperInterface;
    }
}
```

## 七、Spring Boot 自动装配

### 1. 自动配置类

```java
// org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@EnableConfigurationProperties(MybatisProperties.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MybatisAutoConfiguration implements InitializingBean {
    
    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setMapperLocations(resolveMapperLocations());
        // ... 
        return factory.getObject();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
```

### 2. 配置项映射

```yaml
mybatis:
  config-location: classpath:mybatis-config.xml    → SqlSessionFactoryBean.setConfigLocation()
  mapper-locations: classpath:mapper/*.xml           → SqlSessionFactoryBean.setMapperLocations()
  type-aliases-package: com.example.entity           → SqlSessionFactoryBean.setTypeAliasesPackage()
  configuration:
    map-underscore-to-camel-case: true                → Configuration.setMapUnderscoreToCamelCase()
    cache-enabled: true                              → Configuration.setCacheEnabled()
    lazy-loading-enabled: true                       → Configuration.setLazyLoadingEnabled()
```

### 3. @Mapper 和 @MapperScan 区别

| 对比项 | @Mapper | @MapperScan |
|--------|---------|-------------|
| 作用范围 | 接口级别 | 包级别 |
| 使用方式 | 每个接口加注解 | 配置类加一次 |
| 底层实现 | MapperScannerConfigurer | MapperScannerRegistrar |
| 推荐 | Mapper 少时 | Mapper 多时（推荐） |

## 八、常见面试追问

### Q1：SqlSessionTemplate 是线程安全的吗？

是。SqlSessionTemplate 内部通过 JDK 动态代理包装了一个 SqlSession 代理，每次方法调用都从 SqlSessionInterceptor 获取新的或事务绑定的 SqlSession，方法结束后自动关闭。SqlSessionTemplate 本身是无状态的，所有操作都委托给代理对象。

### Q2：MyBatis 事务和 Spring 事务的关系？

Spring 事务管理器管理 DataSource 的连接。在事务中，MyBatis 的 SqlSession 和 Spring 的数据库连接通过 TransactionSynchronizationManager 绑定到当前线程，实现事务一致性。事务提交时 Spring 提交连接并通知 MyBatis 关闭 SqlSession。

### Q3：@Mapper 和 @MapperScan 能一起用吗？

可以，但不建议。同时使用时，Mapper 可能被注册两次。建议只用 @MapperScan 统一管理。

## 九、总结

```
记住三个核心要点：

1. 整合核心三个类
   SqlSessionFactoryBean → 创建 SqlSessionFactory
   SqlSessionTemplate → 线程安全的 SqlSession 代理
   MapperScannerConfigurer / @MapperScan → 扫描 Mapper 接口

2. SqlSessionTemplate 线程安全原理
   JDK 动态代理 → 每次调用获取新/复用 SqlSession
   事务中：绑定到 TransactionSynchronizationManager 复用
   非事务：每次新建，执行完自动关闭

3. Spring Boot 自动装配
   MybatisAutoConfiguration 自动注册 SqlSessionFactory 和 SqlSessionTemplate
   yaml 配置 → MybatisProperties → SqlSessionFactoryBean 属性
```

**面试回答模板**：

> MyBatis 和 Spring 的整合通过 mybatis-spring 实现，核心类有三个：SqlSessionFactoryBean 创建 SqlSessionFactory，SqlSessionTemplate 提供线程安全的 SqlSession 操作，MapperScannerConfigurer 或 @MapperScan 扫描 Mapper 接口注册为 Bean。
>
> SqlSessionTemplate 的线程安全通过 JDK 动态代理实现。内部有一个 SqlSessionInterceptor，每次方法调用都从 SqlSessionUtils 获取 SqlSession——如果当前线程有事务绑定则复用，否则新建并在方法结束后关闭。SqlSessionTemplate 本身无状态，是线程安全的。
>
> Spring Boot 中通过 MybatisAutoConfiguration 自动装配，根据 yaml 配置创建 SqlSessionFactory 和 SqlSessionTemplate，@MapperScan 通过 MapperScannerRegistrar 扫描接口并注册为 MapperFactoryBean，getObject() 时从 SqlSessionTemplate 获取 Mapper 代理。
