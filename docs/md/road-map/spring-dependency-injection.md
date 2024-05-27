---
title: Spring Dependency Injection
lock: need
---

# Spring Dependency Injection - 依赖注入使用技巧

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

`@Autowired` 注入会用，`@Resource` 注入知道，但在项目看到一个没使用这2个注解的，直接在构造函数写了个两个入参 `public AwardController(List<IAwardService> awardServices, Map<String, IAwardService> awardServiceMap) { ...` 就不知道是怎么注入的了。我猜可能是以前一直写 CRUD 流水席代码，导致 Spring 的一些高级注入特性从来没接触过。所以小傅哥今天来给大家专门总结下 Spring 的各种注入的使用，方便小伙伴们可以运用这些特性，写出优雅的代码。

<div align="center">
    <img src="https://bugstack.cn/images/system/zsxq/xingqiu-231018-00.png" width="200px">
</div>

**你可以不用，但不能不会！**

在小傅哥带着大家做的实战项目中「 [https://gaga.plus](https://gaga.plus) 」，有着大量的高级编程技巧和设计模式结合运用的手段，帮你打开能”拿捏“写代码的强悍能力。这些能力都是越早积累越好，否则你以为，为啥一边人有喊着寒冬，一遍有人拿着高薪。

>文末提供了整合案例源码，以及5个业务应用级项目，5个技术组件项目。🛫 

## 一、工程说明

为了让大家更好的理解和学习 Spring 各类依赖注入的相关使用，这里小傅哥专门提供了案例工程，把相关的注入特性都整合进去。学习中可以下载工程对照验证。

**源码**：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-spring-dependency-injection](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-spring-dependency-injection)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-spring-dependency-injection-01.png" width="400px">
</div>

- config；工程所需的配置类对象的使用案例。
- domain；一个接口多个实现类、实现类优先级创建、原型对象、Spring 加载的对象。
- infrastructure；仓储注解实例化对象的使用。
- trigger；list注入、map注入、依赖注入，对象初始和销毁的监听。
- ApiTest；测试各类注入案例的验证。

## 二、实践案例

**实例化注解**：

- `@Component`：组件注解
- `@Serivice`：服务注解
- `@Repository`：仓储注解，提供对持久化类数据的操作的服务。
- `@Controller/@RestController()`：对外提供服务的注解。

### 1. 构造注入&List、Map

```java
private final List<IAwardService> awardServices;
private final Map<String, IAwardService> awardServiceMap;

public AwardController(List<IAwardService> awardServices, Map<String, IAwardService> awardServiceMap) {
    this.awardServices = awardServices;
    this.awardServiceMap = awardServiceMap;
}

public Response<String> distributeAward(@RequestParam String userId, @RequestParam String awardKey) {
    try {
        log.info("发放奖品服务 userId:{} awardKey:{}", userId, awardKey);
        awardServiceMap.get(awardKey);
        return Response.<String>builder()
                .code("0000")
                .info("调用成功")
                .data("发奖完成")
                .build();
    } catch (Exception e) {
        return Response.<String>builder()
                .code("0001")
                .info("调用失败")
                .build();
    }
}
```

- **场景**：IAwardService 接口有多个实现类，可以通过 @Resource、@Autowired 注解注入，也可以通过构造函数注入。在 Spring 官网文档中，是推荐使用构造函数注入的；`The Spring team generally advocates constructor injection, as it lets you implement application components as immutable objects and ensures that required dependencies are not null.` [https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html)
- **用途**：Map 注入是一个非常好的注入手段，我们可以把每个 IAwardService 实现类设定好 Bean 的名称为数据库中的奖品 awardKey。在发奖的时候，可以直接根据 awardKey 从 Map 中获取到对应的 Bean 对象，这样也就省去了 `if···else` 大量的判断操作。

### 2. 空注入判断

```java
public class NullAwardService implements IAwardService {

    @Override
    public void doDistributeAward(String userId) {

    }

}

@Autowired(required = false)
private NullAwardService nullAwardService;
```

- **场景**：NullAwardService 没有配置 @Service 注册，或者在程序中手动实例化的这个 Bean 对象，根据不同诉求，在没有创建的时候。可以使用 `@Autowired(required = false)` 进行注入。这样就不会报错 nullAwardService 空指针异常。
- **用途**：当我们在使用支付、openai外部接口对接测试阶段，可能有些时候是需要关闭服务的，也就是不实例化对象。那么这个时候就配置 `@Autowired(required = false)` 避免注入空指针

### 3. 优先实例化

```java
@Slf4j
@Service("openai_model")
// Primary 首选 Bean 对象标记
@Primary
@Order(1)
public class OpenAIModelAwardService implements IAwardService {

    @Override
    public void doDistributeAward(String userId) {
        log.info("发奖服务，OpenAI 模型奖励 {}", userId);
    }

}

@Resource
private IAwardService awardService;

@Test
public void test_awardService_primary() {
    log.info("测试结果 {}", awardService.getClass());
}

// 测试结果 class cn.bugstack.xfg.dev.tech.domain.impl.OpenAIModelAwardService
```

- **场景**：一个 IAwardService 有多个实现类的时候，如果还想用 `@Resource 注入 awardService` 的时候是会报错说 `NoUniqueBeanDefinitionException` 异常了。这个时候使用 @Primary 就会标记为首选对象，注入的时候会注入这个对象。另外这里的 `@Order(1)` 是对象的加载顺序。
- **用途**：当我们为一组接口提供实现类，并需要提供默认的注入的时候，就可以使用 `@Primary` 注解来限定首选注入项。

### 4. 检测创建，避免重复

```java
@Bean("redisson01")
// 当 Spring 应用上下文中不存在某个特定类型的 Bean 时，才会创建和配置标注了 @ConditionalOnMissingBean 的 Bean 对象
@ConditionalOnMissingBean
public String redisson01() {
    return "模拟的 Redis 客户端 01";
}
@Bean("redisson02")
// 当 Spring 应用上下文中不存在某个特定类型的 Bean 时，才会创建和配置标注了 @ConditionalOnMissingBean 的 Bean 对象
@ConditionalOnMissingBean
public String redisson02() {
    return "模拟的 Redis 客户端 02";
}
```

- **场景**：`@Bean` 可以用于在方法，创建出对象。这有点类似于使用 Spring 的 FactoryBean 接口创建对象一样，这里可以直接使用方法创建。之后 `@ConditionalOnMissingBean` 注解的目的是为了避免重复创建，判断应用上下文中存在这个对象，则不会重复创建。
- **用途**：通常我们在做一些组件的时候，会加入这样一个注解，避免在业务工程中引入同类的组件的时候，会导致创建出相同对象而报错。

### 5. 配置是否创建对象

```java
@Data
@ConfigurationProperties(prefix = "sdk.config", ignoreInvalidFields = true)
public class AutoConfigProperties {

    /** 状态；open = 开启、close 关闭 */
    private boolean enable;
    /** 转发地址 */
    private String apiHost;
    /** 可以申请 sk-*** */
    private String apiSecretKey;

}

@Bean
@ConditionalOnProperty(value = "sdk.config.enabled", havingValue = "true", matchIfMissing = false)
public String createTopic(@Qualifier("redisson01") String redisson, AutoConfigProperties properties) {
    log.info("redisson {} {} {}", redisson, properties.getApiHost(), properties.getApiSecretKey());
    return redisson;
}

sdk:
  config:
    enabled: false
    apiHost: https://open.bigmodel.cn/
    apiSecretKey: d570f7c5d289cdac2abdfdc562e39f3f.trqz1dH8ZK6ED7Pg
```

- **场景**：模拟创建 createTopic，入参的对象为注入的操作，@Qualifier 注解可以指定要注入哪个名字的对象。之后 `@ConditionalOnProperty` 注解可以通过配置的 enabled 值，来确定是否实例化对象。
- **用途**：这个场景场景是非常使用的，比如你做了一个组件，或者业务中要增加一些配置。启动或关闭某些服务，就可以使用了。而不需要把 pom 中引入的组件注释掉。

### 6. 自定义Condition，判断是否实例化对象

```java
public class BeanCreateCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String active = System.getProperty("isOpenWhitelistedUsers");
        return null != active && active.equals("true");
    }

}

@Bean
@Conditional(BeanCreateCondition.class)
public List<String> whitelistedUsers() {
    return new ArrayList<String>() {{
        add("user001");
        add("user002");
        add("user003");
    }};
}

static {
    // BeanCreateCondition 会检测这个值，确定是否创建对象
    System.setProperty("isOpenWhitelistedUsers", "false");
}

@Autowired(required = false)
@Qualifier("whitelistedUsers")
private List<String> whitelistedUsers;
```

- **场景**：是一个案例中使用到了 `@ConditionalOnProperty`  注解，我们也可以自定义一个 Conditional 的实现类，之后把这个实现类配置到需要实例化的对象上面，通过 matches 匹配条件方法的实现，决定是否实例化。
- **用途**：这个场景的用途和 `@ConditionalOnProperty` 是一样的，只不过我们可以更好的自定义控制。

### 7. 根据环境配置实例化对象

```java
@Slf4j
@Service
// 用于根据配置环境实例化 Bean 对象
@Profile({"prod", "test"})
@Lazy
public class AliPayAwardService implements IAwardService {

    public AliPayAwardService() {
        log.info("如一些支付场景，必须指定上线后才能实例化");
    }

    @Override
    public void doDistributeAward(String userId) {
        log.info("红包奖励 {}", userId);
    }

}

spring:
  config:
    name: xfg-dev-tech-spring-dependency-injection
  profiles:
    active: dev
```

- **场景**：`@Profile({"prod", "test"})` 注解可以配置你是在什么时候实例化这个对象，我们可以指定 application.yml 中配置的 `active: dev/prod/test` 来确定是在开发、测试还是上线才实例化这个对象。
- **用途**：一些只有到线上才能实例化对象的时候，就可以配置 `@Profile({"prod", "test"})` 注解，注意这个需要配合 `@Autowired(required = false)` 进行注入，否则会出现注入为空指针的异常。

### 8. 引入 Spring 配置

```java
@Slf4j
@SpringBootApplication
@Configurable
@PropertySource("classpath:properties/application.properties")
@ImportResource("classpath:spring/spring.xml")
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="exampleBean" class="cn.bugstack.xfg.dev.tech.domain.SpringBeanTest"/>

</beans>

@Slf4j
public class SpringBeanTest {

    public SpringBeanTest() {
        log.info("我是通过 Spring 配置文件实例化的 Bean 对象");
    }

}
```

- **场景**：在 SpringBoot 工程中，可以通过 `@ImportResource`、`@PropertySource` 引入对应的配置文件，完成对象的初始化。
- **用途**：在实际的开发中，虽然使用 SpringBoot 工程，但为了兼容一些老的项目或者一些还没有升级到 SpringBoot Starter 的组件，则需要单独引入 Spring 配置文件来创建对象。

### 9. 原型对象

```java
@Component
@Scope("prototype")
public class LogicChain {
}

@Resource
private ApplicationContext applicationContext;

@Test
public void test_prototype() {
    log.info("测试结果: {}", applicationContext.getBean(LogicChain.class).hashCode());
    log.info("测试结果: {}", applicationContext.getBean(LogicChain.class).hashCode());
}
```

- **场景**：`@Scope("prototype")` 可以设定对象类型为原型对象，每次获得的对象都是一个新的实例化对象。
- **用途**：对于动态，不同责任链创建，可以使用这个配置，确保每个对象都是自己的。

### 10. 其他注解

- `@EnableScheduling`：允许启动任务的注解，放到 Application 上，确保任务启动执行。
- `@DependsOn({"openai_model", "openai_use_count", "user_credit_random"})` Bean 对象实例化中，依赖于哪些对象。
- `@Autowired private Environment env;` 环境配置注入，可以获取到 application.yml 中的配置数据 `env.getProperty("app.name"), env.getProperty("app.version")`
- `@Async` 异步方法注解，可以用于调用某个方法后，让下面的具体逻辑方法为异步执行，主方法直接返回结果。可以用于一些申请导出数据到文件的场景。

## 三、源码分析

这里的 Map 注入比较有特点，小傅哥把它的流程和核心代码给大家描述下，方便感兴趣源码的伙伴，可以去看下源码调试跟进。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-spring-dependency-injection-02.png" width="750px">
</div>

在 Spring 框架中，依赖注入（DI）是通过一系列的步骤和组件来实现的。对于构造函数注入，特别是注入 `Map` 类型的依赖，Spring 需要处理以下几个关键步骤：

1. **Bean Definition 解析**：Spring 解析配置文件或注解，生成 BeanDefinition 对象。
2. **Bean 实例化**：Spring 根据 BeanDefinition 创建 Bean 实例。
3. **依赖注入**：Spring 将所需的依赖注入到 Bean 中。

### 1. 核心源码

具体到构造函数注入 `Map` 类型的依赖，Spring 主要通过以下源码来处理：

#### 1.1 `AutowiredAnnotationBeanPostProcessor`

```java
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {
    // 省略其他代码

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) {
        InjectionMetadata metadata = findAutowiringMetadata(beanName, bean.getClass(), pvs);
        try {
            metadata.inject(bean, beanName, pvs);
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", ex);
        }
        return pvs;
    }
}
```

`AutowiredAnnotationBeanPostProcessor` 是处理依赖注入的核心类之一。它会扫描 Bean 的构造函数、字段和方法上的 `@Autowired` 注解，并进行相应的依赖注入。

#### 1.2 `ConstructorResolver`

```java
public class ConstructorResolver {
    // 省略其他代码

    public BeanWrapper autowireConstructor(
            final String beanName, final RootBeanDefinition mbd, Constructor<?>[] chosenCtors, final Object[] explicitArgs) {

        // 省略其他代码

        Constructor<?> constructorToUse = null;
        ArgumentsHolder argsHolderToUse = null;
        Object[] argsToUse = null;

        // 省略其他代码

        for (Constructor<?> candidate : candidates) {
            Class<?>[] paramTypes = candidate.getParameterTypes();
            if (argsToUse == null) {
                // 省略其他代码
                argsHolder = createArgumentArray(
                        beanName, mbd, resolvedValues, bw, paramTypes, paramNames, getUserDeclaredConstructor(candidate), autowiring);
            }
            // 省略其他代码
        }

        // 省略其他代码

        BeanWrapperImpl bw = new BeanWrapperImpl(beanInstance);
        initBeanWrapper(bw);

        return bw;
    }
}
```

`ConstructorResolver` 是负责解析和调用构造函数的类。它会根据 BeanDefinition 和构造函数的参数类型，选择合适的构造函数并进行实例化。

#### 1.3 `DefaultListableBeanFactory`

```java
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {

    // 省略其他代码

    @Override
    protected Map<String, Object> findAutowireCandidates(String beanName, Class<?> requiredType, DependencyDescriptor descriptor) {
        String[] candidateNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(this, requiredType);
        Map<String, Object> result = new LinkedHashMap<>(candidateNames.length);
        for (String candidate : candidateNames) {
            if (!isSelfReference(beanName, candidate) && isAutowireCandidate(candidate, descriptor)) {
                result.put(candidate, getBean(candidate));
            }
        }
        if (result.isEmpty() && !indicatesMultipleBeans(requiredType)) {
            DependencyDescriptor fallbackDescriptor = descriptor.forFallbackMatch();
            for (String candidate : candidateNames) {
                if (!isSelfReference(beanName, candidate) && isAutowireCandidate(candidate, fallbackDescriptor)) {
                    result.put(candidate, getBean(candidate));
                }
            }
        }
        return result;
    }
}
```

`DefaultListableBeanFactory` 是 Spring 中最常用的 BeanFactory 实现类。它负责管理 Bean 的定义和生命周期，并提供依赖查找和注入的功能。

### 2. 具体流程

1. **解析 BeanDefinition**：
   Spring 解析配置文件或注解，生成 `AwardController` 的 `BeanDefinition` 对象。
2. **选择构造函数**：
   `AutowiredAnnotationBeanPostProcessor` 会扫描 `AwardController` 的构造函数，发现它有一个 `Map<String, IAwardService>` 类型的参数。
3. **查找依赖**：
   `ConstructorResolver` 会根据构造函数参数的类型，查找 Spring 容器中所有 `IAwardService` 类型的 Bean，并将它们放入一个 `Map` 中。这个 `Map` 的键是 Bean 的名称，值是对应的 `IAwardService` 实例。
4. **实例化 Bean**：
   `ConstructorResolver` 使用找到的依赖，调用 `AwardController` 的构造函数，创建 `AwardController` 实例。
5. **注入依赖**：
   `DefaultListableBeanFactory` 将创建好的 `Map<String, IAwardService>` 注入到 `AwardController` 的构造函数中。