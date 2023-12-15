---
title: MyBatis
lock: need
---

# MyBatis 使用教程和插件开发

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=955906479&bvid=BV1DW4y1o7Vd&cid=1198523013&page=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

本文的宗旨在于通过简单干净实践的方式教会读者，使用 SpringBoot 配置 MyBatis 并完成对插入、批量插入、修改、查询以及注解事务和编程事务的使用，通过扩展插件开发对置顶字段进行加解处理。

此外本文也通过此案例，渗透讲解 DDD 模型中的聚合对象、实体对象和值对象在领域模型中的实践。

本文涉及的工程：

- xfg-dev-tech-mybatis：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-mybatis](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-mybatis)
- 导入测试库表：[road-map.sql](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-mybatis/-/blob/master/road-map.sql)

## 一、案例背景

`说一千道一万，给小卡拉米写的教程，得简单还好看！`

为了更好的把 MyBatis 常用的各项功能体现的清晰明了，小傅哥这里设定了公司雇员和对应薪酬关系的一个开发场景。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-mybatis-01.png?raw=true" width="750px">
</div>

- 首先，雇员员工和对应的薪资待遇，是一个1v1的关系。
- 之后，薪资表与调薪表，是一个1vn的关系。每次晋升、普调，都会有一条对应的调薪记录。
- 最后，有了这样3个表，我们就可以很好的完成，员工的插入、批量插入，和事务操作调薪。

## 二、领域模型

🌶 **模型定义**：[https://bugstack.cn/md/road-map/ddd.html](https://bugstack.cn/md/road-map/ddd.html) - 你可以先参考小傅哥的 [DDD](https://bugstack.cn/md/road-map/ddd.html) 篇，这样可以更好的理解模型概念和设计原则。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-mybatis-02.png?raw=true" width="650px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-mybatis-03.png?raw=true" width="450px">
</div>

此场景的业务用于对指定的用户进行**晋升加薪调幅**，但因为加薪会需要操作3个表，包括；雇员表 - 修改个人Title、薪资表 - 修改薪酬、调薪记录表 - 每一次加薪都写一条记录。

### 1. model

#### 1.1 值对象

```java
public enum EmployeePostVO {

    T1("T-1", "初级工程师"),
    T2("T-2", "初级工程师"),
    T3("T-3", "中级工程师"),
    T4("T-4", "中级工程师"),
    T5("T-5", "高级工程师"),
    T6("T-6", "高级工程师"),
    T7("T-7", "架构师"),
    T8("T-8", "架构师");

    private final String code;
    private final String desc;

		// 省略部分

}
```

- 当一个实体对象中的一个值，是有多个范围时候，则需要定义出值对象。由于此类的值对象更贴近于当前的场景业务，所以一般不会被定义为共用的枚举。如此此类值范围，都会被定义为值对象。

#### 1.2 实体对象

```java
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {

    /** 雇员级别 */
    private EmployeePostVO employeeLevel;
    /** 雇员岗位Title */
    private EmployeePostVO employeeTitle;

}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSalaryAdjustEntity {

    /** 总额调薪 */
    private BigDecimal adjustTotalAmount;
    /** 基础调薪 */
    private BigDecimal adjustBaseAmount;
    /** 绩效调薪 */
    private BigDecimal adjustMeritAmount;

}
```

- 实体对象是对数据库对象的抽象，大多数时候是 1:1 的关系结构，在一些复杂的模型场景中会是1:n的结构。

#### 1.3 聚合对象

```java
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdjustSalaryApplyOrderAggregate {

    /** 雇员编号 */
    private String employeeNumber;
    /** 调薪单号 */
    private String orderId;
    /** 雇员实体 */
    private EmployeeEntity employeeEntity;
    /** 雇员实体 */
    private EmployeeSalaryAdjustEntity employeeSalaryAdjustEntity;

}
```

- 聚合对象是对实体对象和值对象的封装，代表着一类业务的聚合。通常是作为 service 服务层中入参出现。

### 2. repository

```java
public interface ISalaryAdjustRepository {

    String adjustSalary(AdjustSalaryApplyOrderAggregate adjustSalaryApplyOrderAggregate);

}
```

- 仓储在 DDD 中的设计，是一种依赖倒置关系，由 domain 定义接口，之后由引入 domain 包的基层层 infrastructure 实现功能。
- 此外，因为是依赖倒置，所以天然的隔离了 PO 数据库持久化对象，不会被对外使用。这个设计是非常巧妙的。当我们从结构上定义了原则，就不会有人乱引用对象了。

### 3. service

```java
public interface ISalaryAdjustApplyService {

    String execSalaryAdjust(AdjustSalaryApplyOrderAggregate adjustSalaryApplyOrderAggregate);

}
```

- 当前场景简单，所以不需要额外的设计模式使用。但如果是复杂场景，必须考虑设计模式，否则代码都写到 SalaryAdjustApplyService 实现类里，那么将非常难维护。
- 不要只是把聚合对象当充血模型，你的充血结构是整个 domain 下的每一个领域包，也就是让这里的状态与行为看做为一整个结构。

📢 **综上**，有了这样的模型结构设计定义，相信你也可以很好的拆分自己的业务对象并完成领域功能实现了。

## 三、配置文件

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-mybatis-04.png?raw=true" width="950px">
</div>

- 工程中关于 MyBatis 的使用，在 xfg-dev-tech-app 下进行统一配置。因为所有配置信息都放到一起，比较方便管理，也利于线上上线后，提取配置文件。

## 四、功能实现

接下来我们介绍一些关于 MyBatis 的使用功能，但你可以带着 DDD 的思想来看这些内容实现时所在的位置，这会让你不只是学习 MyBatis 也能学会一些 DDD 的设计。

### 1. 插入&批量插入

**源码**：`cn.bugstack.xfg.dev.tech.infrastructure.dao.IEmployeeDAO`

```java
@Mapper
public interface IEmployeeDAO {

    void insert(EmployeePO employee);

    void insertList(List<EmployeePO> list);

    void update(EmployeePO employeePO);

    EmployeePO queryEmployeeByEmployNumber(String employNumber);

}
```

**xml**：`employee_mapper.xml`

```xml
<insert id="insert" parameterType="cn.bugstack.xfg.dev.tech.infrastructure.po.EmployeePO">
    INSERT INTO employee(employee_number, employee_name, employee_level, employee_title, create_time, update_time)
    VALUES(#{employeeNumber}, #{employeeName}, #{employeeLevel}, #{employeeTitle}, now(), now())
</insert>

<insert id="insertList" parameterType="java.util.List">
    INSERT INTO employee(employee_number, employee_name, employee_level, employee_title, create_time, update_time)
    VALUES
    <foreach collection="list" item="item" separator=",">
        (#{item.employeeNumber}, #{item.employeeName}, #{item.employeeLevel}, #{item.employeeTitle}, now(), now())
    </foreach>
</insert>
```

- 使用配置文件的方式比较好维护，当然如果也可以尝试使用 MyBatis 提供的注解方式，完成数据的操作。

### 2. 事务&注解编程

Spring 提供的事务分为注解事务和编程事务，编程事务可以更细粒度的控制。

Spring Boot 事务管理的级别可以通过 `@Transactional` 注解的 `isolation` 属性进行配置。常见的事务隔离级别有以下几种：

1. `DEFAULT`：使用底层数据库的默认隔离级别。MySQL 默认为 `REPEATABLE READ`，Oracle 默认为 `READ COMMITTED`。
2. `READ_UNCOMMITTED`：最低的隔离级别，允许读取未提交的数据变更，可能会导致脏读、不可重复读和幻读问题。
3. `READ_COMMITTED`：允许读取已经提交的数据变更，可以避免脏读问题，但可能会出现不可重复读和幻读问题。
4. `REPEATABLE_READ`：保证同一事务中多次读取同一数据时，结果始终一致，可以避免脏读和不可重复读问题，但可能会出现幻读问题。
5. `SERIALIZABLE`：最高的隔离级别，可以避免脏读、不可重复读和幻读问题，但会影响并发性能。

在 Spring Boot 中，默认的事务隔离级别为 `DEFAULT`。如果没有特殊需求，建议使用默认隔离级别。

SpringBoot 事务的传播行为可以通过 `@Transactional` 注解的 `propagation` 属性进行配置。常用的传播行为有以下几种：

1. `Propagation.REQUIRED`：默认的传播行为，如果当前存在事务，则加入该事务，否则新建一个事务；
2. `Propagation.SUPPORTS`：如果当前存在事务，则加入该事务，否则以非事务的方式执行；
3. `Propagation.MANDATORY`：如果当前存在事务，则加入该事务，否则抛出异常；
4. `Propagation.REQUIRES_NEW`：无论当前是否存在事务，都会新建一个事务，如果当前存在事务，则将当前事务挂起；
5. `Propagation.NOT_SUPPORTED`：以非事务的方式执行操作，如果当前存在事务，则将当前事务挂起；
6. `Propagation.NEVER`：以非事务的方式执行操作，如果当前存在事务，则抛出异常；
7. `Propagation.NESTED`：如果当前存在事务，则在该事务的嵌套事务中执行，否则新建一个事务。嵌套事务是独立于外部事务的，但是如果外部事务回滚，则嵌套事务也会回滚。

除了传播行为，`@Transactional` 注解还可以配置其他属性，例如隔离级别、超时时间、只读等。

#### 2.1 注解事务

**源码**：`cn.bugstack.xfg.dev.tech.infrastructure.repository.SalaryAdjustRepository`

```java
@Transactional(rollbackFor = Exception.class, timeout = 350, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public String adjustSalary(AdjustSalaryApplyOrderAggregate adjustSalaryApplyOrderAggregate) {
    String employeeNumber = adjustSalaryApplyOrderAggregate.getEmployeeNumber();
    String orderId = adjustSalaryApplyOrderAggregate.getOrderId();
    EmployeeEntity employeeEntity = adjustSalaryApplyOrderAggregate.getEmployeeEntity();
    EmployeeSalaryAdjustEntity employeeSalaryAdjustEntity = adjustSalaryApplyOrderAggregate.getEmployeeSalaryAdjustEntity();
    EmployeePO employeePO = EmployeePO.builder()
            .employeeNumber(employeeNumber)
            .employeeLevel(employeeEntity.getEmployeeLevel().getCode())
            .employeeTitle(employeeEntity.getEmployeeTitle().getDesc()).build();
    // 更新岗位
    employeeDAO.update(employeePO);
    EmployeeSalaryPO employeeSalaryPO = EmployeeSalaryPO.builder()
            .employeeNumber(employeeNumber)
            .salaryTotalAmount(employeeSalaryAdjustEntity.getAdjustTotalAmount())
            .salaryMeritAmount(employeeSalaryAdjustEntity.getAdjustMeritAmount())
            .salaryBaseAmount(employeeSalaryAdjustEntity.getAdjustBaseAmount())
            .build();
    // 更新薪酬
    employeeSalaryDAO.update(employeeSalaryPO);
    EmployeeSalaryAdjustPO employeeSalaryAdjustPO = EmployeeSalaryAdjustPO.builder()
            .employeeNumber(employeeNumber)
            .adjustOrderId(orderId)
            .adjustTotalAmount(employeeSalaryAdjustEntity.getAdjustTotalAmount())
            .adjustBaseAmount(employeeSalaryAdjustEntity.getAdjustMeritAmount())
            .adjustMeritAmount(employeeSalaryAdjustEntity.getAdjustBaseAmount())
            .build();
    // 写入流水
    employeeSalaryAdjustDAO.insert(employeeSalaryAdjustPO);
    return orderId;
}
```

- 这个事务所做的内容，就是前面小傅哥提到的调整薪资的处理。它的具体操作就是放到仓储层实现。
- 注意事务注解的配置。

#### 2.2 编程事务

##### 2.2.1 事务模板

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-mybatis-05.png?raw=true" width="950px">
</div>

- 使用编程事务，需要在这里创建出一个事务模板，当然你不创建也可以使用。只不过这样统一的配置会更加方便。

##### 2.2.2 事务使用

```java
private TransactionTemplate transactionTemplate;
@Override
public void insertEmployeeInfo(EmployeeInfoEntity employeeInfoEntity) {
    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
        @Override
        protected void doInTransactionWithoutResult(TransactionStatus status) {
            try {
                EmployeePO employeePO = EmployeePO.builder()
                        .employeeNumber(employeeInfoEntity.getEmployeeNumber())
                        .employeeName(employeeInfoEntity.getEmployeeName())
                        .employeeLevel(employeeInfoEntity.getEmployeeLevel())
                        .employeeTitle(employeeInfoEntity.getEmployeeTitle())
                        .build();
                employeeDAO.insert(employeePO);
                EmployeeSalaryPO employeeSalaryPO = EmployeeSalaryPO.builder()
                        .employeeNumber(employeeInfoEntity.getEmployeeNumber())
                        .salaryTotalAmount(employeeInfoEntity.getSalaryTotalAmount())
                        .salaryMeritAmount(employeeInfoEntity.getSalaryMeritAmount())
                        .salaryBaseAmount(employeeInfoEntity.getSalaryBaseAmount())
                        .build();
                employeeSalaryDAO.insert(employeeSalaryPO);
            } catch (Exception e) {
                status.setRollbackOnly();
                e.printStackTrace();
            }
        }
    });
}
```

- 之后就可以手动处理事务了，因为手动的处理可以更细节的控制，也可以根据返回的结果，手动回滚。而不非得异常回滚。

### 3. 插件&数据加密

使用 MyBatis 时，也会经常会用到插件开发。尤其是做一些数据的加解密、路由、日志等，都可以基于插件实现。

那么这里小傅哥就带着你实现一个对指定字段加解密的处理，比如雇员的姓名、薪资、级别是可以隐藏的，避免被有心之人盗取。

**源码**：`cn.bugstack.xfg.dev.tech.plugin.FieldEncryptionAndDecryptionMybatisPlugin`

```java
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class FieldEncryptionAndDecryptionMybatisPlugin implements Interceptor {

    /**
     * 密钥，必须是16位
     */
    private static final String KEY = "1898794876567654";
    /**
     * 偏移量，必须是16位
     */
    private static final String IV = "1233214566547891";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object parameter = args[1];
        String sqlId = mappedStatement.getId();

        if (parameter != null && (sqlId.contains("insert") || sqlId.contains("update")) ) {
            String columnName = "employeeName";
            if (parameter instanceof Map) {
                List<Object> parameterList = (List<Object>) ((Map<?, ?>) parameter).get("list");
                for (Object obj : parameterList) {
                    if (hasField(obj, columnName)) {
                        String fieldValue = BeanUtils.getProperty(obj, columnName);
                        String encryptedValue = encrypt(fieldValue);
                        BeanUtils.setProperty(obj, columnName, encryptedValue);
                    }
                }
            } else {
                if (hasField(parameter, columnName)) {
                    String fieldValue = BeanUtils.getProperty(parameter, columnName);
                    String encryptedValue = encrypt(fieldValue);
                    BeanUtils.setProperty(parameter, columnName, encryptedValue);
                }
            }
        }

        Object result = invocation.proceed();

        if (result != null && sqlId.contains("query")) {
            // 查询操作，解密
            String columnName = "employeeName";
            if (result instanceof List) {
                List<Object> resultList = (List<Object>) result;
                for (Object obj : resultList) {
                    if (!hasField(obj, columnName)) continue;
                    String fieldValue = BeanUtils.getProperty(obj, columnName);
                    if (StringUtils.isBlank(fieldValue)) continue;
                    String decryptedValue = decrypt(fieldValue);
                    BeanUtils.setProperty(obj, columnName, decryptedValue);
                }
            }
        }

        return result;
    }

    public String encrypt(String content) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = KEY.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(content.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * AES解密
     *
     * @param content 密文
     * @return 明文
     * @throws Exception 异常
     */
    public String decrypt(String content) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = KEY.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encrypted = Base64.getDecoder().decode(content);
        byte[] original = cipher.doFinal(encrypted);
        return new String(original);
    }

    public boolean hasField(Object obj, String fieldName) {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return false;
    }

}
```

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-mybatis-06.png?raw=true" width="850px">
</div>

- 首先通过注解配置，拦截指定范围内的信息 `Intercepts` 之后在 intercept 接口实现方法中，获取 MappedStatement 这个 MyBatis的映射核心类。[**《手写MyBatis：渐进式源码实践》**](https://item.jd.com/13811216.html)  - 跟小傅哥学MyBatis，从零手写源码级复杂项目，提升架构思维与设计逻辑，锻炼编码能力！
- 有了 AES  的加解密，就可以对指定的字段 employeeName 对插入数据库的字段进行加密，同时还可以在读取的时候解密。

## 五、测试验证

### 1. 调薪

```java
@Test
public void test_execSalaryAdjust() {
    AdjustSalaryApplyOrderAggregate adjustSalaryApplyOrderAggregate = AdjustSalaryApplyOrderAggregate.builder()
            .employeeNumber("10000001")
            .orderId("100908977676001")
            .employeeEntity(EmployeeEntity.builder().employeeLevel(EmployeePostVO.T3).employeeTitle(EmployeePostVO.T3).build())
            .employeeSalaryAdjustEntity(EmployeeSalaryAdjustEntity.builder()
                    .adjustTotalAmount(new BigDecimal(100))
                    .adjustBaseAmount(new BigDecimal(80))
                    .adjustMeritAmount(new BigDecimal(20)).build())
            .build();
    String orderId = salaryAdjustApplyService.execSalaryAdjust(adjustSalaryApplyOrderAggregate);
    log.info("调薪测试 req: {} res: {}", JSON.toJSONString(adjustSalaryApplyOrderAggregate), orderId);
}
```

```java
23-07-15.13:23:11.514 [main            ] INFO  HikariDataSource       - HikariPool-1 - Start completed.
23-07-15.13:23:11.910 [main            ] INFO  ISalaryAdjustApplyServiceTest - 调薪测试 req: {"employeeEntity":{"employeeLevel":"T3","employeeTitle":"T3"},"employeeNumber":"10000001","employeeSalaryAdjustEntity":{"adjustBaseAmount":80,"adjustMeritAmount":20,"adjustTotalAmount":100},"orderId":"100908977676002"} res: 100908977676002
```

### 2. 查询

```java
@Test
public void test_queryEmployInfo() {
    EmployeeInfoEntity employeeInfoEntity = employeeService.queryEmployInfo("10000001");
    log.info("测试结果：{}", JSON.toJSONString(employeeInfoEntity));
}
```

```java
23-07-15.13:24:54.000 [main            ] INFO  HikariDataSource       - HikariPool-1 - Start completed.
23-07-15.13:24:54.490 [main            ] INFO  IEmployeeServiceTest   - 测试结果：{"employeeLevel":"T-3","employeeName":"小傅哥","employeeNumber":"10000001","employeeTitle":"中级工程师","salaryBaseAmount":5200.00,"salaryMeritAmount":5200.00,"salaryTotalAmount":5200.00}
```

- 执行完调薪后，就可以来看下这个用户的薪资待遇是多少了。
