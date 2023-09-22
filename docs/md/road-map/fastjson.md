---
title: fastjson
lock: need
---

# fastjson 使用

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

本文的宗旨在于通过简单干净实践的方式教会读者，使用 fastjson 的一些常用操作方法。这些方法也是日常使用 fastjson 时最为常用的方法，如果你在使用中还有一些案例和特性，或者踩坑经验也可以在本文提交PR

本文涉及的工程：

- xfg-dev-tech-fastjson：[https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-fastjson](https://gitcode.net/KnowledgePlanet/road-map/xfg-dev-tech-fastjson)
- Github：[https://github.com/alibaba/fastjson](https://github.com/alibaba/fastjson)

## 一、常用方法

### 1. 序列化和反序列化

```java
String strJson = JSON.toJSONString(UserEntity.builder().build());
UserEntity userEntity = JSON.parseObject(strJson, UserEntity.class);
```

### 2. 配置序列化字段

```java
// 不被序列化
@JSONField(name="amount", serialize=false)
private Double amount;
// 序列化格式
@JSONField(name="createTime", format="dd/MM/yyyy", ordinal = 3)
private Date createTime;

@JsonProperty("top_p")
private Double topP = 1d;
@JsonProperty("max_tokens")
private Integer maxTokens = 2048;
```

- 对象的属性上添加 `@JSONField`、`@JsonProperty` 都可以改变序列化字段的名字。同时还可以扩展是否被序列化和格式化。

### 3. 排除序列化字段

```java
UserEntity userEntity = UserEntity.builder()
        .amount(100D)
        .userName("xfg")
        .password("abc000")
        .createTime(new Date())
        .build();

SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
Collections.addAll(filter.getExcludes(), "password");
log.info(JSON.toJSONString(userEntity, filter));
```

- 因为有些时候不是你能修改被序列化的对象，如你引入了别人的 JAR 之后需要对某个类进行序列化，但因为有些对象不能被序列化或者不要序列化。那么这个时候就可以通过 filter 过滤的方式进行处理。

### 4. json2map 转换

```java
@Test
public void test_map2json() {
    Map<String, Object> map = new HashMap<>();
    map.put("key1", "xfg");
    map.put("key2", 123);
    map.put("key3", false);
    log.info(JSON.toJSONString(map));
}

@Test
public void test_json2map() {
    String jsonString = "{\"key1\":\"xfg\",\"key2\":123,\"key3\":false}";
    Map<String, Object> map = JSON.parseObject(jsonString, Map.class);
    for (Map.Entry<String, Object> entry : map.entrySet()) {
        log.info("{} : {}", entry.getKey(), entry.getValue());
    }
}
```

- 有些时候我们接收的对象就是个 Map 那么你可以使用 fastjson 来对对象进行 map 的转换或者序列化

### 5. toString 处理

```java
@Test
public void testToString2Bean() throws Exception {
    UserEntity userEntity = UserEntity.builder()
            .amount(100D)
            .userName("xfg")
            .password("abc000")
            .createTime(new Date())
            .build();
    log.info(userEntity.toString());
    log.info(JSON.toJSONString(ToString2Bean.toObject(userEntity.toString(), UserEntity.class)));
}

public static <T> T toObject(String str, Class<T> clazz) throws Exception {
    // 创建一个新的对象
    T obj = clazz.getDeclaredConstructor().newInstance();
    // 获取类对象
    Class<?> objClass = obj.getClass();
    // 解析字符串
    String[] fields = str.substring(str.indexOf("{") + 1, str.indexOf("}")).split(", ");
    // 遍历成员变量
    for (String field : fields) {
    // 获取成员变量名和值
    String[] parts = field.split("=");
    // 获取成员变量对象
    Field objField = objClass.getDeclaredField(parts[0].trim());
    // 设置成员变量可以访问
    objField.setAccessible(true);
    // 设置成员变量的值
    objField.set(obj, convertValue(objField.getType(), parts[1].trim()));
    // 设置成员变量不可访问
    objField.setAccessible(false);
    }
    return obj;
}
```

```java
06:03:46.302 [main] INFO cn.bugstack.xfg.dev.tech.test.ApiTest - UserEntity{userName='xfg', password='abc000', amount=100.0, createTime=2023-09-21 20:03:46}
06:03:46.670 [main] INFO cn.bugstack.xfg.dev.tech.test.ApiTest - {"password":"'abc000'","userName":"'xfg'","createTime":"21/09/2023"}

Process finished with exit code 0
```

- 有一些在方法入参的时候需要用日志打印入参信息。大部分时候都是直接用 json 打印对象，但对于一些较大对象就比较耗时。所以阿里的开发手册是建议这个场景使用 toString 操作。
- 但是 toString 操作后的日志不太便于，在本地进行测试验证。因为不好转对象。所以这里我们写个 toString2Bean 对象的方法。

