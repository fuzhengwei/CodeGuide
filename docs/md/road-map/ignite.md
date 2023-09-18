---
title: Ignite
lock: need
---

# Apache Ignite —— 一种支持SQL语句的纯内存数据库！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=363559782&bvid=BV1594y1s7Ku&cid=1269949773&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

本文的宗旨在于通过简单干净实践的方式，向读者介绍一款基于内存的分布式SQL数据库Apache Ignite的部署、使用和性能测试。

那有了Redis这样优秀的NoSql数据库，为啥还会用到Apache Ignite呢？

不知道你是否有想过一个事情，就是Redis这样的内存数据库，如果能支持SQL语句，是不是就更牛了。这样一来本身存在MySQL数据库里的数据，就可以原封不动的封到内存中使用。既保留了原有的业务逻辑，又使用上了内存读取高性能。

所以，它来了。Apache Ignite是一个兼容ANSI-99、水平可扩展以及容错的分布式SQL数据库，作为一个SQL数据库，Ignite支持所有的DML指令，包括SELECT、UPDATE、INSERT和DELETE，它还实现了一个与分布式系统有关的DDL指令的子集。Ignite的一个突出特性是完全支持分布式的SQL关联，Ignite支持并置和非并置的数据关联。并置时，关联是在每个节点的可用数据集上执行的，而不需要在网络中移动大量的数据，这种方式在分布式数据库中提供了最好的扩展性和性能。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ignite-01.png?raw=true" width="650px"/>
</div>

本文涉及的工程：

- xfg-dev-tech-ignite：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-ignite](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-ignite)- `docs/dev-ops 提供了 mysql、ignite 安装脚本，和数据初始化操作。`
- 官网站点：[https://ignite.apache.org/](https://ignite.apache.org/) - `官网 docs 可以阅读安装和使用`
- 中文文档：[https://ignite-service.cn/doc/2.7.0/sql/](https://ignite-service.cn/doc/2.7.0/sql/) - `这是一个 Ignite 的中文站点`
- 管理工具：[DBeaver](https://dbeaver.io/download/) - `安装最新版，直接可以连接 Ignite 数据库`

## 一、案例说明

本案例中为了对比MySQL和Ignite的性能差异，以及如何同时使用两套数据库，这里小傅哥会在一个工程中分别配置出不同的数据库对应数据源的创建和MyBatis的配置用。如果说你做过小傅哥的 [DB-Router](https://bugstack.cn/md/road-map/db-router.html) 组件开发，那么也可以在组件中添加对Ignite内存数据库的路由配置。这样的使用会更加方便，也可以自动的通过注解来切换数据源的使用。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ignite-02.png?raw=true" width="550px"/>
</div>

- SpringBoot应用的yml配置，本身默认是配置一个数据源的。但我们这里需要把Ignite也配置出数据源并让它可以结合MyBatis进行使用。所以需要做一点编码的扩展使用。`具体可以参考源码`
- 与此同时还需要考虑对 Dao、Mapper 分不同的路径进行加载使用。因为本身来说，他们就是一套东西的不同数据源使用方式。

## 二、环境安装

在安装执行 docker-compose.yml 脚本之前，你需要先在本地安装 [docker](https://bugstack.cn/md/road-map/docker.html)之后 IntelliJ IDEA 打开 docker-compose.yml 文件，如图操作即可安装。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ignite-03.png?raw=true" width="550px"/>
</div>

- 在 docker-compose.yml 中会先安装 MySQL 并执行 sql 文件夹里的 SQL 语句初始化数据库表。之后会安装 Ignite 环境，安装后需要用到 [DBeaver](https://dbeaver.io/download/) 连接使用。同时 compose 中还安装了一个 ApacheBench 压测工具。

## 三、连接配置

首先确保你已经安装过  [DBeaver](https://dbeaver.io/download/) ，之后就可以连接和创建表了。

### 1. 选择 Ignite

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ignite-05.png?raw=true" width="650px"/>
</div>

### 2. 验证链接

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ignite-06.png?raw=true" width="650px"/>
</div>

### 3. 创建库表

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ignite-07.png?raw=true" width="650px"/>
</div>

### 4. 创建完成

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ignite-04.png?raw=true" width="650px"/>
</div>

- 之后你所有做的修改，包括你自己手动创建表、字段、索引，都需要点保存。否则它是红色的，不生效。

## 四、功能配置

### 1. 工程结构

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ignite-08.png?raw=true" width="850px"/>
</div>

- app层；application-dev.yml 配置多套数据源，并在 DataSourceConfig 中实现多套数据源的加载。
- infrastructure层；用于提供数据的 dao 层，这里分开2套分别提供。
- trigger；触发器层，提供了调用 Ignite、MySQL 的测试验证接口。

### 2. 数据源创建

```xml
<!-- https://mvnrepository.com/artifact/org.apache.ignite/ignite-core -->
<dependency>
    <groupId>org.apache.ignite</groupId>
    <artifactId>ignite-core</artifactId>
    <version>2.15.0</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.apache.ignite/ignite-spring -->
<dependency>
    <groupId>org.apache.ignite</groupId>
    <artifactId>ignite-spring</artifactId>
    <version>2.15.0</version>
</dependency>
```

- 注意引入 ignite 的 pom 配置

#### 2.1 Ignite 

**源码**：`cn.bugstack.xfg.dev.tech.config.DataSourceConfig#IgniteMyBatisConfig`

```java
@Configuration
@MapperScan(basePackages = "cn.bugstack.xfg.dev.tech.infrastructure.ignite.dao", sqlSessionFactoryRef = "igniteSqlSessionFactory")
static class IgniteMyBatisConfig {
    @Bean("igniteDataSource")
    @ConfigurationProperties(prefix = "spring.ignite.datasource")
    public DataSource igniteDataSource(Environment environment) {
        IgniteConfiguration igniteConfig = new IgniteConfiguration();
        DataStorageConfiguration dataStorageConfig = new DataStorageConfiguration();
        DataRegionConfiguration defaultDataRegionConfig = new DataRegionConfiguration();
        defaultDataRegionConfig.setPersistenceEnabled(false);
        dataStorageConfig.setDefaultDataRegionConfiguration(defaultDataRegionConfig);
        igniteConfig.setDataStorageConfiguration(dataStorageConfig);
        ConnectorConfiguration configuration = new ConnectorConfiguration();
        configuration.setIdleTimeout(6000);
        configuration.setThreadPoolSize(100);
        configuration.setIdleTimeout(60000);
        igniteConfig.setConnectorConfiguration(configuration);
        return DataSourceBuilder.create()
                .url(environment.getProperty("spring.ignite.datasource.url"))
                .driverClassName(environment.getProperty("spring.ignite.datasource.driver-class-name"))
                .build();
    }
    @Bean("igniteSqlSessionFactory")
    public SqlSessionFactory igniteSqlSessionFactory(DataSource igniteDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(igniteDataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mybatis/mapper/ignite/*.xml"));
        return factoryBean.getObject();
    }
}
```

- 创建 Ignite 的数据源，以及对应的 igniteSqlSessionFactory 工厂。这样就把 MyBatis 给关联起来了。

#### 2.2 MySQL

**源码**：`cn.bugstack.xfg.dev.tech.config.DataSourceConfig#MysqlMyBatisConfig`

```java
@Configuration
@MapperScan(basePackages = "cn.bugstack.xfg.dev.tech.infrastructure.mysql.dao", sqlSessionFactoryRef = "mysqlSqlSessionFactory")
static class MysqlMyBatisConfig {
    @Bean("mysqlDataSource")
    @ConfigurationProperties(prefix = "spring.mysql.datasource")
    public DataSource mysqlDataSource(Environment environment) {
        return DataSourceBuilder.create()
                .url(environment.getProperty("spring.mysql.datasource.url"))
                .driverClassName(environment.getProperty("spring.mysql.datasource.driver-class-name"))
                .build();
    }
    @Bean("mysqlSqlSessionFactory")
    public SqlSessionFactory mysqlSqlSessionFactory(DataSource mysqlDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(mysqlDataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mybatis/mapper/mysql/*.xml"));
        return factoryBean.getObject();
    }
}
```

- 同样，创建 MySQL 的数据源以及 MyBatis 的关联操作。

## 五、性能测试

小傅哥提供了 IgniteController、MySQLController 两个 HTTP 访问类，分别提供了两个数据库的压测操作。

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ignite-09.png?raw=true" width="950px"/>
</div>

- 这里提供了 Ignite、MySQL 的 HTTP 访问接口，分别进行压测。

### 1. Ignite 压测

- 初始化ID值：`ab -c 1 -n 1 http://127.0.0.1:8091/api/ignite/start`
- 写入数据：`ab -c 20 -n 50000 http://127.0.0.1:8091/api/ignite/insert`
- 随机加载内存1000条数据：`ab -c 20 -n 1000 http://127.0.0.1:8091/api/ignite/cacheData`
- 根据加载到内存的数据查询Ignite：`ab -c 20 -n 1000 http://127.0.0.1:8091/api/ignite/selectByOrderId` - 记得给 OrderId 加索引

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ignite-10.png?raw=true" width="650px"/>
</div>

### 2. MySQL 压测

- 初始化ID值：`ab -c 1 -n 1 http://127.0.0.1:8091/api/ignite/start`
- 写入数据：`ab -c 20 -n 50000 http://127.0.0.1:8091/api/ignite/insert`
- 随机加载内存1000条数据：`ab -c 20 -n 1000 http://127.0.0.1:8091/api/ignite/cacheData`
- 根据加载到内存的数据查询MySQL：`ab -c 20 -n 1000 http://127.0.0.1:8091/api/ignite/selectByOrderId` - 记得给 OrderId 加索引

<div align="center">
	<img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ignite-11.png?raw=true" width="650px"/>
</div>

---

**综上**，Ignite 略胜一筹，确实纯内存的数据库会更快一些。也适合在一些需要内存计算的场景中，并且不改变MySQL表结构的情况下，做一些优化的是使用。