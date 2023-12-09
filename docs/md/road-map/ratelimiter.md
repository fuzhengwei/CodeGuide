---
title: RateLimiter
lock: need
---

# RateLimiter 限流 —— 通过切面对单个用户进行限流和黑名单处理

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

本文的宗旨在于通过对实际场景的案例进行抽复现，教会读者如何对应用的接口以`浏览器指纹ID`为维度的限流操作，同时对于频繁限流拦截的ID加入黑名单，不需要限流计算就🈲禁止对应用接口访问。通过这样的方式来保护应用的可用性。

本文涉及的工程：
- xfg-dev-tech-ratelimiter：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-ratelimiter](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-ratelimiter)

## 一、场景说明

关于登录的安全性管理有较多的手段，包括；设备信息、IP信息、绑定的信息、验证码登各类方式，不过在一些网页版的登录中，手机验证码方式都会有一个对应的提醒："请勿向他人泄露验证码信息"

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ratelimiter-01.png?raw=true" width="350px">
</div>

也就是说，如果你把你的验证码给我，我就可以登录你的账户，查看你的数据。对于一些不法分子通过让你进入某些应用的录屏会议后（XXX退货返现），就能拿到你的验证码，并做登录操作。还有一些是完全流氓式做法，就玩命的一些快递📦手机号+验证码频繁的撞接口，也是有概率成功登录的。

所以，本节的案例我们来考虑下该如何做这样的防护处理。

## 二、方案设计

我们可以考虑在登录的阶段必须加一些恶心的图片比对码，或者滑块验证码。这也是一种方式，能尽可能降低登录的撞接口操作。之后再考虑添加一个指纹ID，对于验证码的生成与用户从浏览器设备过来的指纹做绑定。这样即使对方通过录屏拿到你的验证码，也仍然没有做登录操作。

```js
<script>
  // Initialize the agent at application startup.
  const fpPromise = import('https://openfpcdn.io/fingerprintjs/v4')
    .then(FingerprintJS => FingerprintJS.load())

  // Get the visitor identifier when you need it.
  fpPromise
    .then(fp => fp.get())
    .then(result => {
      // This is the visitor identifier:
      const visitorId = result.visitorId
      console.log(visitorId)
    })
</script>
```

有了上面这个方案，我们至少可以做一些安全的管控了。但还有臭不要脸的，一直刷你接口。这既有安全风险，又有对服务器的压力。所以我们要考虑对于这样的恶意用户进行`限流和自动化黑名单`处理。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ratelimiter-02.png?raw=true" width="750px">
</div>

浏览器指纹的方案只需要做一个验证码绑定即可，之后`限流和自动化黑名单`，则需要做一些代码的开发。通过配置的方式为每一个需要做此类功能的接口添加上**服务治理**。*通常我们把对应用的熔断、降级、限流、切量、黑白名单、人群等，都称为服务治理*

## 三、功能实现

### 1. 工程结构

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ratelimiter-03.png?raw=true" width="400px">
</div>

- 工程中，提供了一个 AOP 切面专门用于处理使用了自定义注解 `AccessInterceptor` 接口方法。
- 这里的自定义注解，在 DDD 分层架构中，要放到 Types 层中，这样其他层才能引入使用。

### 2. 限流拦截

#### 2.1 切面定义

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AccessInterceptor {

    /** 用哪个字段作为拦截标识，未配置则默认走全部 */
    String key() default "all";

    /** 限制频次（每秒请求次数） */
    double permitsPerSecond();

    /** 黑名单拦截（多少次限制后加入黑名单）0 不限制 */
    double blacklistCount() default 0;

    /** 拦截后的执行方法 */
    String fallbackMethod();

}

@Pointcut("@annotation(cn.bugstack.xfg.dev.tech.annotation.AccessInterceptor)")
public void aopPoint() {
}
```

- 自定义切面注解，提供了拦截的key、限制频次、黑名单处理、拦截后的回调方法。再通过 @Pointcut 切入配置了自定义注解的接口方法

#### 2.2 切面拦截

```java
// 个人限频记录1分钟
private final Cache<String, RateLimiter> loginRecord = CacheBuilder.newBuilder()
        .expireAfterWrite(1, TimeUnit.MINUTES)
        .build();

// 个人限频黑名单24h - 自身的分布式业务场景，可以记录到 Redis 中
private final Cache<String, Long> blacklist = CacheBuilder.newBuilder()
        .expireAfterWrite(24, TimeUnit.HOURS)
        .build();

@Around("aopPoint() && @annotation(accessInterceptor)")
public Object doRouter(ProceedingJoinPoint jp, AccessInterceptor accessInterceptor) throws Throwable {
    String key = accessInterceptor.key();
    if (StringUtils.isBlank(key)) {
        throw new RuntimeException("annotation RateLimiter uId is null！");
    }
    
    // 获取拦截字段
    String keyAttr = getAttrValue(key, jp.getArgs());
    log.info("aop attr {}", keyAttr);
   
    // 黑名单拦截
    if (!"all".equals(keyAttr) && accessInterceptor.blacklistCount() != 0 && null != blacklist.getIfPresent(keyAttr) && blacklist.getIfPresent(keyAttr) > accessInterceptor.blacklistCount()) {
        log.info("限流-黑名单拦截(24h)：{}", keyAttr);
        return fallbackMethodResult(jp, accessInterceptor.fallbackMethod());
    }
   
    // 获取限流 -> Guava 缓存1分钟
    RateLimiter rateLimiter = loginRecord.getIfPresent(keyAttr);
    if (null == rateLimiter) {
        rateLimiter = RateLimiter.create(accessInterceptor.permitsPerSecond());
        loginRecord.put(keyAttr, rateLimiter);
    }
    
    // 限流拦截
    if (!rateLimiter.tryAcquire()) {
        if (accessInterceptor.blacklistCount() != 0) {
            if (null == blacklist.getIfPresent(keyAttr)) {
                blacklist.put(keyAttr, 1L);
            } else {
                blacklist.put(keyAttr, blacklist.getIfPresent(keyAttr) + 1L);
            }
        }
        log.info("限流-超频次拦截：{}", keyAttr);
        return fallbackMethodResult(jp, accessInterceptor.fallbackMethod());
    }
    // 返回结果
    return jp.proceed();
}
```

- 通过自定义注解中配置的拦截字段，获取对应的值。这里的值作为用户的标识使用，只对这个用户进行拦截。【也可以是一些列的信息确认，包括用户IP、设备等。】
- 这段代码流程中会根据自定义注解中的配置，对访问的用户进行限流拦截，当拦击次数达到加入黑名单的次数后，则直接存起来（Guava/Redis）在24h内直接走黑名单。—— 实际的场景中还会有风控的手段介入，以及人工来操作黑名单。

#### 2.3 回调处理

```java
/**
 * 调用用户配置的回调方法，当拦截后，返回回调结果。
 */
private Object fallbackMethodResult(JoinPoint jp, String fallbackMethod) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Signature sig = jp.getSignature();
    MethodSignature methodSignature = (MethodSignature) sig;
    Method method = jp.getTarget().getClass().getMethod(fallbackMethod, methodSignature.getParameterTypes());
    return method.invoke(jp.getThis(), jp.getArgs());
}
```

- 最终如果判定为拦截，则会走用户配置的回调方法。如 login 配置一个 loginErr，出入参都一样，只是名字不一样。这样才方便反射调用。

## 四、测试验证

### 1. 接口配置

```java
@AccessInterceptor(key = "fingerprint", fallbackMethod = "loginErr", permitsPerSecond = 1.0d, blacklistCount = 10)
@RequestMapping(value = "login", method = RequestMethod.GET)
public String login(String fingerprint, String uId, String token) {
    log.info("模拟登录 fingerprint:{}", fingerprint);
    return "模拟登录：登录成功 " + uId;
}

public String loginErr(String fingerprint, String uId, String token) {
    return "频次限制，请勿恶意访问！";
}
```

给你需要拦截的方法，添加上自定义注解。
- key: 以用户ID作为拦截，这个用户访问次数限制
- fallbackMethod：失败后的回调方法，方法出入参保持一样
- permitsPerSecond：每秒的访问频次限制。1秒1次
- blacklistCount：超过10次都被限制了，还访问的，扔到黑名单里24小时

### 2. 测试验证

访问：[http://localhost:8091/api/ratelimiter/login?fingerprint=uljpplllll01009&uId=1000&token=8790](http://localhost:8091/api/ratelimiter/login?fingerprint=uljpplllll01009&uId=1000&token=8790)

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ratelimiter-05.png?raw=true" width="700px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/roadmap-ratelimiter-04.png?raw=true" width="700px">
</div>

```java
22:34:47.518 [http-nio-8091-exec-6] INFO  RateLimiterAOP - 限流-超频次拦截：uljpplllll01009
22:34:47.669 [http-nio-8091-exec-7] INFO  RateLimiterAOP - aop attr uljpplllll01009
22:34:49.121 [http-nio-8091-exec-6] INFO  RateLimiterAOP - aop attr uljpplllll01009
22:34:49.122 [http-nio-8091-exec-6] INFO  RateLimiterAOP - 限流-黑名单拦截(24h)：uljpplllll01009
22:34:57.647 [http-nio-8091-exec-8] INFO  RateLimiterAOP - aop attr uljpplllll01009
22:34:57.650 [http-nio-8091-exec-8] INFO  RateLimiterAOP - 限流-黑名单拦截(24h)：uljpplllll01009
```

- 好啦，到这，我们就可以看到，用户的访问已经被拦截了。
- 赶紧到自己的应用加一下吧，一个指纹ID，一个用户维护限流访问。让自己的应用更加可靠！

---

>这些各项实际场景的内容，在小傅哥的【星球：码农会锁】有7个完结的项目和1个进行的项目，都有大量的实践运用。可以扫码加入，项目体验地址；[https://gaga.plus](https://gaga.plus)