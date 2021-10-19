---
layout: post
category: itstack-demo-frame
title: 架构框架搭建(一)《单体应用服务之SSM整合：Spring4 + SpringMvc + Mybatis》
tagline: by 付政委
tag: [ddd,itstack-demo-frame]
excerpt: 在实际的业务开发中按照不同的场景需要，会有不同的业务架构也就同时会有不同的技术框架来支撑。那么这个专题想把一些常用的框架整理下，方便平时使用的同时也做一些技术沉淀。那么本章节会先搭建一个比较适合个人项目或者一些小公司开发项目的单体架构模型。服务功能展示页面如下；
lock: need
---

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 前言介绍
在实际的业务开发中按照不同的场景需要，会有不同的业务架构也就同时会有不同的技术框架来支撑。那么这个专题想把一些常用的框架整理下，方便平时使用的同时也做一些技术沉淀。那么本章节会先搭建一个比较适合个人项目或者一些小公司开发项目的单体架构模型。服务功能展示页面如下；

![微信公众号：bugstack虫洞栈 & 展示页面](https://bugstack.cn/assets/images/pic-content/2019/11/itstack-demo-frame-ssm-01.png)

## 工程环境
1. JDK1.8
2. Maven 3.2.3
3. Spring 4.3.24.RELEASE + SpringMvc + Mybatis 3.3.0
4. Mysql 5.6 + dbcp2
5. layui 2.5.4

## 工程模型

整体的工程模型采用DDD四层架构，相对于MVC模式来讲。嗯！相当于家里三居换四居了！

```java
itstack-demo-frame-ssm
└── src
    ├── main
    │   ├── java
    │   │   └── org.itstack.demo
    │   │       ├── application	
    │   │       │	└── UserService.java	
    │   │       ├── domain
    │   │       │	├── model
    │   │       │	│   ├── aggregates
    │   │       │	│   │   └── UserInfoCollect.java
    │   │       │	│   ├── req
    │   │       │	│   │   └── UserReq.java		
    │   │       │	│   └── vo
    │   │       │	│       └── UserInfo.java	
    │   │       │	├── repository
    │   │       │	│   └── IUserRepository.java	
    │   │       │	└── service	
    │   │       │	    └── UserServiceImpl.java	
    │   │       ├── infrastructure
    │   │       │	├── common
    │   │       │	│   ├── EasyResult.java
    │   │       │	│   └── PageRequest.java
    │   │       │	├── dao
    │   │       │	│   └── IUserDao.java	
    │   │       │	├── po
    │   │       │	│   └── User.java		
    │   │       │	└── repository
    │   │       │	    └── UserRepository.java	
    │   │       └── interfaces
    │   │        	└── UserController.java
    │   ├── resources	
    │   │   ├── mapper
    │   │   ├── props	
    │   │   ├── spring
    │   │   ├── logback.xml
    │   │   ├── mybatis-config.xml
    │   │   └── spring-config.xml
    │   └── webapp
    │       ├── page
    │       ├── res
    │       ├── WEB-INF
    │       ├── index.html
    │       └── res_layui.html
    └── test
         └── java
             └── org.itstack.demo.test
                 └── ApiTest.java
```

**以下对工程模块进行介绍，整体源码获取，可以关注公众号：bugstack虫洞栈，回复：框架搭建**

### application应用层

应用层是比较薄的一层，不做具体逻辑开发。本工程里只包括服务的定义，具体逻辑有领域层实现。如果需要扩展可以做一些应用服务编排。

>application/UserService.java & 定义接口

```java
/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public interface UserService {

    UserInfoCollect queryUserInfoList(UserReq req);

}
```

### domain领域层

领域层是整个工程的核心服务层，这里负责处理具体的核心功能，完成领域服务。domain下可以有多个领域，每个领域里包括；聚合、请求对象、业务对象、仓储、服务。

>domain/model/aggregates/UserInfoCollect.java & 定义聚合查询结果

```java
/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class UserInfoCollect {

    private Long count;
    private List<UserInfo> userInfoList;

    public UserInfoCollect() {
    }

    public UserInfoCollect(Long count, List<UserInfo> userInfoList) {
        this.count = count;
        this.userInfoList = userInfoList;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<UserInfo> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<UserInfo> userInfoList) {
        this.userInfoList = userInfoList;
    }
}
```

>domain/repository/IUserRepository.java & 定义仓储服务

```java
/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public interface IUserRepository {

    UserInfoCollect queryUserInfoList(UserReq req);

}

```

>domain/service/UserServiceImpl.java & 对业务层功能进行实现

```java
/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource(name = "userRepository")
    private IUserRepository userRepository;

    @Override
    public UserInfoCollect queryUserInfoList(UserReq req) {
        return userRepository.queryUserInfoList(req);
    }

}
```


### infrastructure基础层

1. 实现领域层仓储定义
2. 数据库操作为非业务属性的功能操作
3. 在仓储实现层进行组合装配DAO&Redis&Cache等

>infrastructure/dao/IUserDao.java & 数据库操作

```java
/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public interface IUserDao {

    List<User> queryUserInfoList(UserReq req);

    Long queryUserInfoCount(UserReq req);

}

```

>infrastructure/repository/UserRepository.java & 仓储功能实现如果有redis可以进行包装使用

```java
/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Repository("userRepository")
public class UserRepository implements IUserRepository {

    @Resource
    private IUserDao userDao;

    @Override
    public UserInfoCollect queryUserInfoList(UserReq req) {
        Long count = userDao.queryUserInfoCount(req);
        List<User> userList = userDao.queryUserInfoList(req);
        List<UserInfo> userInfoList = new ArrayList<>();
        userList.forEach(user -> {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(user.getId());
            userInfo.setName(user.getName());
            userInfo.setAge(user.getAge());
            userInfo.setAddress(user.getAddress());
            userInfo.setEntryTime(user.getEntryTime());
            userInfo.setStatus(user.getStatus());
            userInfoList.add(userInfo);
        });
        return new UserInfoCollect(count, userInfoList);
    }

}
```

### interfaces接口层

1. 包装应用接口对外提供api，目前这一层比较简单只需要进行接口使用即可
2. 如果是对外部提供服务接口，那么可以使用DTO方式进行转换，避免污染到业务类

>interfaces/UserController.java & 提供接口服务

```java
@Controller
@RequestMapping("/api/user/")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @RequestMapping(path = "queryUserInfoList", method = RequestMethod.GET)
    @ResponseBody
    public EasyResult queryUserInfoList(String json, String page, String limit) {
        try {
            logger.info("查询用户信息列表开始。json：{}", json);
            UserReq req = JSON.parseObject(json, UserReq.class);
            if (null == req) req = new UserReq();
            req.setPage(page, limit);
            UserInfoCollect userInfoCollect = userService.queryUserInfoList(req);
            logger.info("查询用户信息列表完成。userInfoCollect：{}", JSON.toJSONString(userInfoCollect));
            return EasyResult.buildEasyResultSuccess(userInfoCollect.getCount(), userInfoCollect.getUserInfoList());
        } catch (Exception e) {
            logger.error("查询用户信息列表失败。json：{}", json, e);
            return EasyResult.buildEasyResultError(e);
        }
    }

}
```

### resource配置

这里包括了Spring、SpringMvc、mybatis、以及日志信息的配置；

>mapper/User_Mapper.xml

```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="org.itstack.demo.infrastructure.dao.IUserDao">

    <select id="queryUserInfoCount" resultType="java.lang.Long">
        select count(id)
        from user
        <where>
            <if test="name != null">
                and name = #{name}
            </if>
            <if test="status != null">
                and status = #{status}
            </if>
        </where>
    </select>

    <select id="queryUserInfoList" resultType="org.itstack.demo.infrastructure.po.User">
      SELECT id, name, age, address, entryTime, status, remark, createTime, updateTime
      FROM user
      <where>
          <if test="name != null">
              and name = #{name}
          </if>
          <if test="status != null">
              and status = #{status}
          </if>
      </where>
      limit #{pageStart},#{pageEnd}
    </select>

</mapper>
```

>props/jdbc.properties & 数据库链接信息

```java
db.jdbc.driverClassName=com.mysql.jdbc.Driver
db.jdbc.url=jdbc:mysql://127.0.0.1:3306/itstack?createDatabaseIfNotExist=true&amp;characterEncoding=utf-8&amp;useUnicode=true
db.jdbc.username=root
db.jdbc.password=123456
```

>spring/spring-config-datasource.xml & dbcp2数据源配置以及扫描Mapper等

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 1.数据库连接池 -->
    <bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource">
        <property name="driverClassName" value="${db.jdbc.driverClassName}" />
        <property name="url" value="${db.jdbc.url}" />
        <property name="username" value="${db.jdbc.username}" />
        <property name="password" value="${db.jdbc.password}" />
        <property name="maxTotal" value="20" />
        <property name="maxIdle" value="3" />
        <property name="maxWaitMillis" value="15000" />
        <property name="timeBetweenEvictionRunsMillis" value="60000" />
        <property name="minEvictableIdleTimeMillis" value="180000" />
    </bean>

    <!-- 2.配置SqlSessionFactory对象 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!-- 注入数据库连接池 -->
        <property name="dataSource" ref="dataSource" />
        <!-- 配置MyBaties全局配置文件:mybatis-config.xml -->
        <property name="configLocation" value="classpath:mybatis-config.xml" />
        <!-- 扫描entity包 使用别名 -->
        <property name="typeAliasesPackage" value="com.soecode.lyf.entity" />
        <!-- 扫描sql配置文件:mapper需要的xml文件 -->
        <property name="mapperLocations" value="classpath:mapper/*.xml" />
    </bean>

    <!-- 3.配置扫描Dao接口包，动态实现Dao接口，注入到spring容器中 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!-- 注入sqlSessionFactory -->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
        <!-- 给出需要扫描Dao接口包 -->
        <property name="basePackage" value="org.itstack.demo.infrastructure.dao" />
    </bean>

</beans>
```

>resources/mybatis-config.xml

```java
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<!-- 配置全局属性 -->
	<settings>
		<!-- 使用jdbc的getGeneratedKeys获取数据库自增主键值 -->
		<setting name="useGeneratedKeys" value="true" />
		<!-- 使用列别名替换列名 默认:true -->
		<setting name="useColumnLabel" value="true" />
		<!-- 开启驼峰命名转换:Table{create_time} -> Entity{createTime} -->
		<setting name="mapUnderscoreToCamelCase" value="true" />
	</settings>
</configuration>
```

>resources/spring-config.xml

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd     http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.0.xsd"
       default-autowire="byName">

    <context:component-scan base-package="org.itstack"/>
    <aop:aspectj-autoproxy/>

    <!-- 属性文件读入 -->
    <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="locations">
            <list>
                <value>classpath:props/*.properties</value>
            </list>
        </property>
    </bean>

    <import resource="classpath:spring/spring-*.xml"/>

</beans>
```

### itstack.sql

```java
DROP TABLE user;
CREATE TABLE user ( id bigint(11) NOT NULL AUTO_INCREMENT, name varchar(32), age int(4), address varchar(128), entryTime datetime, remark varchar(64), createTime datetime, updateTime datetime, status int(4) DEFAULT '0', PRIMARY KEY (id), INDEX idx_name (name) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into user (id, name, age, address, entryTime, remark, createTime, updateTime, status) values (1, '水水', 18, '吉林省榆树市黑林镇尹家村5组', '2019-12-22 00:00:00', '无', '2019-12-22 00:00:00', '2019-12-22 00:00:00', 0);
insert into user (id, name, age, address, entryTime, remark, createTime, updateTime, status) values (2, '豆豆', 18, '辽宁省大连市清河湾司马道407路', '2019-12-22 00:00:00', '无', '2019-12-22 00:00:00', '2019-12-22 00:00:00', 1);
insert into user (id, name, age, address, entryTime, remark, createTime, updateTime, status) values (3, '花花', 19, '辽宁省大连市清河湾司马道407路', '2019-12-22 00:00:00', '无', '2019-12-22 00:00:00', '2019-12-22 00:00:00', 0);
```

## 综上总结

- 此工程模型基于SSM比较适合开发ERP服务，ERP使用layui页面清新，功能完善
- 工程框架采用了DDD架构模式，在此架构模式下可以更容易的开发系统，适应后比MVC更加方便
- 后续将继续拓展架构服务搭建，包括一些Dubbo、Redis、mq等使用，方便自己也方便他人



