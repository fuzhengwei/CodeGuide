---
title: MySQL Time Zone
lock: need
---

# MySQL 8.0.22 引发的时区错误问题，应该如何正确的配置 TimeZone？

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

在实际的工作场景中有时候就是一个小小的问题，就可能引发出一个大大的bug。而且工作这么多年，看到的线上事故，往往也都是这些小的细节问题，所以学习这些具有实际经验的细节非常重要。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-mysql-time-zone-01.gif" width="150px">
</div>

**有些事故隐藏的很深！**

其实很多时候事故也不是一开始就有的，而是随着需求的迭代，达到某一个条件后触达到事故的发生条件了才出现的。就像 MySQL 的时区配置问题，它既有不同版本 JDBC 连接引擎的不同，又有数据库设置的时区，还有服务端设置的时区，还包括在使用数据库配置时指定的时区。这些条件综合发生时才会出现事故。

接下来，小傅哥就给大家分享下为啥是 8.0.22 版本才会引发时区错误问题。

## 一、问题场景

这是一条很普通的SQL语句；

```java
<insert id="insert" parameterType="cn.bugstack.xfg.dev.tech.infrastructure.po.EmployeePO">
    INSERT INTO employee(employee_number, employee_name, employee_level, employee_title, create_time, update_time)
    VALUES(#{employeeNumber}, #{employeeName}, #{employeeLevel}, #{employeeTitle}, now(), now())
</insert>
```

修改下这条普通的SQL语句；

```java
<insert id="insert" parameterType="cn.bugstack.xfg.dev.tech.infrastructure.po.EmployeePO">
    INSERT INTO employee(employee_number, employee_name, employee_level, employee_title, create_time, update_time)
    VALUES(#{employeeNumber}, #{employeeName}, #{employeeLevel}, #{employeeTitle}, #{createTime}, now())
</insert>
```

接下来在执行插入SQL语句；

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-mysql-time-zone-02.png" width="850px">
</div>

- 原本直接使用数据库语句 now() 的并没有问题，而改为由程序透传的时间 createTime 后，日期时间发生了错误。差了8个小时。
- 通常一般我们操作数据库的时候，写入的时间，往往都是 now()。但有时候比如要外部透传用户下单时间做本系统做一个返利活动，在什么时间内才返利，要记录时间。这个时候发现写入数据库的时间就不对了。
- 因为原本你的系统都是走的数据库时间，现在突然多了一个来自系统的透传时间，那么你可能是注意不到的。另外由于本机的开发环境与服务器配置不一样，所以最终直至上线开始跑数据了，才发现问题。这个就是一般出现事故的原因。

## 二、排查配置

### 1. mysql jdbc 版本

```java
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.22</version>
</dependency>
```

- 8.0.22 版本，官网提示有bug；`https://dev.mysql.com/doc/relnotes/connector-j/en/news-8-0-23.html`

### 2. 链接参数配置

```java
jdbc:mysql://127.0.0.1:3306/road-map?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&useSSL=true
```

- 注意首次没有配置时区。配置时区需要增加参数；`&serverTimezone=Asia/Shanghai`

### 3. mysql time-zone 配置

```java
show variables like '%time_zone%';

+------------------+--------+
| Variable_name    | Value  |
+------------------+--------+
| system_time_zone | CST    |
| time_zone        | SYSTEM |
+------------------+--------+
```

- 命令修改时区；`SET time_zone = 'SYSTEM';`
- 命令修改时区；`SET time_zone = '+8:00';`
- 注意CST配置，不是中国时区。默认是美国中部时间。
  - 美国中部时间 Central Standard Time (USA) UTC-05:00 或 UTC-06:00
  - 澳大利亚中部时间 Central Standard Time (Australia) UTC+09:30
  - 中国标准时 China Standard Time UTC+08:00
  - 古巴标准时 Cuba Standard Time UTC-04:00


### 4. linux 服务器时间

```java
[root@lavm-aqhgp9nber ~]# timedatectl
      Local time: Sat 2024-08-31 13:57:07 CST
  Universal time: Sat 2024-08-31 05:57:07 UTC
        RTC time: Sat 2024-08-31 05:57:06
       Time zone: Asia/Shanghai (CST, +0800)
     NTP enabled: yes
NTP synchronized: yes
 RTC in local TZ: no
      DST active: n/a
```

- 命令修改时区；`sudo timedatectl set-timezone Asia/Shanghai`
- 命令修改时区；`sudo timedatectl set-timezone America/New_York`

## 三、源码问题 - MySQL JDBC

### 1. 8.0.22 版本问题

在 8.0.0 ~ 8.0.22 版本中，如果未配置时区，`serverTimezone=Asia/Shanghai` 则会取服务端时区，所以如果服务端配置的是 CST 时区，则会有问题。调试源码；

**com.mysql.cj.protocol.a.NativeProtocol#configureTimezone**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-mysql-time-zone-03.png" width="850px">
</div>

- 在 8.0.22 版本中，获取时区的方法，如果本地为配置 jdbc 时区，则会获取服务端时区。也就是 CST 美国中部时间。
- 所以，如果你要使用的是 8.0.22 就必须指定时区。`jdbc:mysql://IP:13306/road-map?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=Asia/Shanghai` 

### 2. 8.0.23 + 版本

在 8.0.23 版本以后，如果未配置时区，调整为获取客户端时区。

```java
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.23</version>
</dependency>
```

- 切换到 8.0.23 版本。

**com.mysql.cj.protocol.a.NativeProtocol#configureTimezone**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-mysql-time-zone-04.png" width="850px">
</div>

- 在使用 8.0.23 TimeZone.getDefault() 注释 `Gets the default TimeZone of the Java virtual machine.` 获取 Java 虚拟机默认时区。这个方法也是 Java 本身代码的方法。
- 你可以通过 Java Main 函数执行 `System.*out*.println("Default Time Zone: " + TimeZone.getDefault().getID());` 获取默认时区。打印结果为 `Default Time Zone: Asia/Shanghai`

### 3. 官网说明

地址：[https://dev.mysql.com/doc/relnotes/connector-j/en/news-8-0-23.html](https://dev.mysql.com/doc/relnotes/connector-j/en/news-8-0-23.html)

**Bugs Fixed**

```java
After upgrading from Connector/J 5.1 to 8.0, the results of saving and then retrieving DATETIME and TIMESTAMP values became different sometimes. It was because while Connector/J 5.1 does not preserve a time instant by default, Connector/J 8.0.22 and earlier tried to do so by converting a timestamp to the server's session time zone before sending its value to the server. In this release, new mechanisms for controlling timezone conversion has been introduced—see Preserving Time Instants for details. Under this new mechanism, the default behavior of Connector/J 5.1 in this respect is preserved by setting the connection property preserveInstants=false. (Bug #30962953, Bug #98695, Bug #30573281, Bug #95644)
```

从 Connector/J 5.1 升级到 8.0 后，保存和检索 DATETIME 和 TIMESTAMP 值的结果有时会有所不同。这是因为，虽然 Connector/J 5.1 默认不保留时间点，但 Connector/J 8.0.22 及更早版本尝试通过在将时间戳的值发送到服务器之前将其转换为服务器的会话时区来保留时间点。在此版本中，引入了用于控制时区转换的新机制 - 有关详细信息，请参阅保留时间点。在这种新机制下，通过设置连接属性 retainInstants=false 来保留 Connector/J 5.1 在这方面的默认行为。（错误 #30962953、错误 #98695、错误 #30573281、错误 #95644）

## 四、综上总结

在使用MySQL的时候，确保服务器时区、MySQL时区、Java应用链接MySQL JDBC的参数配置，都指定到具体的时区上。MySQL JDBC 使用 8.0.23+ 版本，不要使用 8.0.0 ~ 8.0.22 版本，尤其是5.1升级要升级到 8.0.23 以及往后的版本。

正确配置；`url: jdbc:mysql://127.0.0.1:3306/road-map?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=Asia/Shanghai`
