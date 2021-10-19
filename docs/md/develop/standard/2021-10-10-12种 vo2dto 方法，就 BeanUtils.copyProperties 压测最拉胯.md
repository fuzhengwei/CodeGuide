---
layout: post
category: itstack-code-life
title: 12种 vo2dto 方法，就 BeanUtils.copyProperties 压测最拉胯！
tagline: by 小傅哥
tag: [java,itstack-code-life]
excerpt: 没有技术深度、短缺知识储备、匮乏经验积累的前提下，怎么写代码？百度呀，遇到问题这搜一点，那查一块，不管它是什么原理还是适合哪种场景，先粘贴到自己的工程里。
lock: need
---

# 12种 vo2dto 方法，就 BeanUtils.copyProperties 压测最拉胯！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>原文：[https://mp.weixin.qq.com/s/Xq7oQg7dYESMYxHVnxX8Dw](https://mp.weixin.qq.com/s/Xq7oQg7dYESMYxHVnxX8Dw)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`为哈么，你的代码也就仅仅是能用而已？`

没有技术深度、短缺知识储备、匮乏经验积累的前提下，怎么写代码？百度呀，遇到问题这搜一点，那查一块，不管它是什么原理还是适合哪种场景，先粘贴到自己的工程里，**看，能跑了，能跑就行**。那这样的代码也就仅仅是能用程度的交付，根本没有一定的质量保证，也更别提数据结构、算法逻辑和设计模式了，那看的编程资料刷的LeetCode，全歇菜了。

当你感觉看了很多资料又不会用的时候，会说什么，`真卷，都学到这样了`。但其实我并不觉对技术的深度挖掘、梳理全套的知识体系，一点点耕耘一点点收获**是在卷**。反而把看技术视频当成看电影一样轻松，不写案例就以为书看会了的爽，没有意义的缺少脑力思考机械式体力重复，才是卷，甚至很卷。

就像让你用一个属性拷贝工具，把`vo转成dto`，你用了哪呢，是 Apache 的还是 Spring 的，还是其他的什么，哪个效率最高？*接下来我们来用数据验证下，并提供出各种案例的使用对比*

## 二、性能测试对比

在 Java 系统工程开发过程中，都会有各个层之间的对象转换，比如  VO、DTO、PO、VO 等，而如果都是手动`get、set`又太浪费时间，还可能操作错误，所以选择一个自动化工具会更加方便。

目前我整理出，用于对象属性转换有12种，包括：普通的getset、json2Json、Apache属性拷贝、Spring属性拷贝、bean-mapping、bean-mapping-asm、BeanCopier、Orika、Dozer、ModelMapper、JMapper、MapStruct 接下来我们分别测试这11种属性转换操作分别在`一百次`、`一千次`、`一万次`、`十万次`、`一百万次`时候的性能时间对比。

![](https://bugstack.cn/assets/images/guide/guide-1-01.png)

- `BeanUtils.copyProperties` 是大家代码里最常出现的工具类，但只要你不把它用错成 `Apache` 包下的，而是使用 Spring 提供的，就基本还不会对性能造成多大影响。
- 但如果说性能更好，可替代手动`get、set`的，还是 `MapStruct` 更好用，因为它本身就是在编译期生成`get、set`代码，和我们写`get、set`一样。
- 其他一些组件包主要基于 `AOP`、`ASM`、`CGlib`，的技术手段实现的，所以也会有相应的性能损耗。

## 三、12种转换案例

![](https://bugstack.cn/assets/images/guide/guide-1-02.png)

**源码**：[https://github.com/fuzhengwei/guide-vo2dto](https://github.com/fuzhengwei/guide-vo2dto)

**描述**：在案例工程下创建 interfaces.assembler 包，定义 `IAssembler<SOURCE, TARGET>#sourceToTarget(SOURCE var)` 接口，提供不同方式的对象转换操作类实现，学习的过程中可以直接下载运行调试。

### 1. get\set 

```java
@Component
public class GetSetAssembler implements IAssembler<UserVO, UserDTO> {

    @Override
    public UserDTO sourceToTarget(UserVO var) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(var.getUserId());
        userDTO.setUserNickName(var.getUserNickName());
        userDTO.setCreateTime(var.getCreateTime());
        return userDTO;
    }

}
```

- **推荐**：★★★☆☆
- **性能**：★★★★★
- **手段**：手写
- **点评**：其实这种方式也是日常使用的最多的，性能肯定是杠杠的，就是操作起来有点麻烦。尤其是一大堆属性的 VO 对象转换为 DTO 对象时候。但其实也有一些快捷的操作方式，比如你可以通过 Shift+Alt 选中所有属性，Shift+Tab 归并到一列，接下来在使用 Alt 选中这一列，批量操作粘贴 `userDTO.set` 以及快捷键大写属性首字母，最后切换到结尾补充括号和分号，最终格式化一下就搞定了。

### 2. json2Json

```java
@Component
public class Json2JsonAssembler implements IAssembler<UserVO, UserDTO> {

    @Override
    public UserDTO sourceToTarget(UserVO var) {
        String strJson = JSON.toJSONString(var);
        return JSON.parseObject(strJson, UserDTO.class);
    }

}
```

- **推荐**：☆☆☆☆☆
- **性能**：★☆☆☆☆
- **手段**：把对象转JSON串，再把JSON转另外一个对象
- **点评**：这么写多半有点烧！

### 3. Apache copyProperties

```java
@Component
public class ApacheCopyPropertiesAssembler implements IAssembler<UserVO, UserDTO> {

    @Override
    public UserDTO sourceToTarget(UserVO var) {
        UserDTO userDTO = new UserDTO();
        try {
            BeanUtils.copyProperties(userDTO, var);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return userDTO;
    }

}
```

- **推荐**：☆☆☆☆☆
- **性能**：★☆☆☆☆
- **手段**：Introspector 机制获取到类的属性来进行赋值操作
- **点评**：有坑，兼容性交差，不建议使用

### 4. Spring copyProperties

```java
@Component
public class SpringCopyPropertiesAssembler implements IAssembler<UserVO, UserDTO> {

    @Override
    public UserDTO sourceToTarget(UserVO var) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(var, userDTO);
        return userDTO;
    }

}
```

- **推荐**：★★★☆☆
- **性能**：★★★★☆
- **手段**：Introspector机制获取到类的属性来进行赋值操作
- **点评**：同样是反射的属性拷贝，Spring 提供的 copyProperties 要比 Apache 好用的多，只要你不用错，基本不会有啥问题。

### 5. Bean Mapping

```java
@Component
public class BeanMappingAssembler implements IAssembler<UserVO, UserDTO> {

    @Override
    public UserDTO sourceToTarget(UserVO var) {
        UserDTO userDTO = new UserDTO();
        BeanUtil.copyProperties(var, userDTO);
        return userDTO;
    }

}
```

- **推荐**：★★☆☆☆
- **性能**：★★★☆☆
- **手段**：属性拷贝
- **点评**：性能一般

### 6. Bean Mapping ASM

```java
@Component
public class BeanMappingAssembler implements IAssembler<UserVO, UserDTO> {

    @Override
    public UserDTO sourceToTarget(UserVO var) {
        UserDTO userDTO = new UserDTO();
        BeanUtil.copyProperties(var, userDTO);
        return userDTO;
    }

}
```

- **推荐**：★★★☆☆
- **性能**：★★★★☆
- **手段**：基于ASM字节码框架实现
- **点评**：与普通的 Bean Mapping 相比，性能有所提升，可以使用。

### 7. BeanCopier

```java
@Component
public class BeanCopierAssembler implements IAssembler<UserVO, UserDTO> {

    @Override
    public UserDTO sourceToTarget(UserVO var) {
        UserDTO userDTO = new UserDTO();
        BeanCopier beanCopier = BeanCopier.create(var.getClass(), userDTO.getClass(), false);
        beanCopier.copy(var, userDTO, null);
        return userDTO;
    }

}
```

- **推荐**：★★★☆☆
- **性能**：★★★★☆
- **手段**：基于CGlib字节码操作生成get、set方法
- **点评**：整体性能很不错，使用也不复杂，可以使用

### 8. Orika

```java
@Component
public class OrikaAssembler implements IAssembler<UserVO, UserDTO> {

    /**
     * 构造一个MapperFactory
     */
    private static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    static {
        mapperFactory.classMap(UserDTO.class, UserVO.class)
                .field("userId", "userId")  // 字段不一致时可以指定
                .byDefault()
                .register();
    }

    @Override
    public UserDTO sourceToTarget(UserVO var) {
        return mapperFactory.getMapperFacade().map(var, UserDTO.class);
    }

}
```

- **官网**：[https://orika-mapper.github.io/orika-docs/](https://orika-mapper.github.io/orika-docs/)
- **推荐**：★★☆☆☆
- **性能**：★★★☆☆
- **手段**：基于字节码生成映射对象
- **点评**：测试性能不是太突出，如果使用的话需要把 MapperFactory 的构建优化成 Bean 对象

### 9. Dozer

```java
@Component
public class DozerAssembler implements IAssembler<UserVO, UserDTO> {

    private static DozerBeanMapper mapper = new DozerBeanMapper();

    @Override
    public UserDTO sourceToTarget(UserVO var) {
        return mapper.map(var, UserDTO.class);
    }

}
```

- **官网**：[http://dozer.sourceforge.net/documentation/gettingstarted.html](http://dozer.sourceforge.net/documentation/gettingstarted.html)
- **推荐**：★☆☆☆☆
- **性能**：★★☆☆☆
- **手段**：属性映射框架，递归的方式复制对象
- **点评**：性能有点差，不建议使用

### 10. ModelMapper

```java
@Component
public class ModelMapperAssembler implements IAssembler<UserVO, UserDTO> {

    private static ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.addMappings(new PropertyMap<UserVO, UserDTO>() {
            @Override
            protected void configure() {
                // 属性值不一样可以自己操作
                map().setUserId(source.getUserId());
            }
        });
    }

    @Override
    public UserDTO sourceToTarget(UserVO var) {
        return modelMapper.map(var, UserDTO.class);
    }

}
```

- **官网**：[http://modelmapper.org](http://modelmapper.org)
- **推荐**：★★★☆☆
- **性能**：★★★☆☆
- **手段**：基于ASM字节码实现
- **点评**：转换对象数量较少时性能不错，如果同时大批量转换对象，性能有所下降

### 11. JMapper

```java
JMapper<UserDTO, UserVO> jMapper = new JMapper<>(UserDTO.class, UserVO.class, new JMapperAPI()
        .add(JMapperAPI.mappedClass(UserDTO.class)
                .add(JMapperAPI.attribute("userId")
                        .value("userId"))
                .add(JMapperAPI.attribute("userNickName")
                        .value("userNickName"))
                .add(JMapperAPI.attribute("createTime")
                        .value("createTime"))
        ));
```

- **官网**：[https://github.com/jmapper-framework/jmapper-core/wiki](https://github.com/jmapper-framework/jmapper-core/wiki)
- **推荐**：★★★★☆
- **性能**：★★★★★
- **手段**：Elegance, high performance and robustness all in one java bean mapper
- **点评**：速度真心可以，不过结合 SpringBoot 感觉有的一点点麻烦，可能姿势不对

### 12. MapStruct

```java
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserDTOMapping extends IMapping<UserVO, UserDTO> {

    /** 用于测试的单例 */
    IMapping<UserVO, UserDTO> INSTANCE = Mappers.getMapper(UserDTOMapping.class);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Override
    UserDTO sourceToTarget(UserVO var1);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Override
    UserVO targetToSource(UserDTO var1);

}
```

- **官网**：[https://github.com/mapstruct/mapstruct](https://github.com/mapstruct/mapstruct)
- **推荐**：★★★★★
- **性能**：★★★★★
- **手段**：直接在编译期生成对应的get、set，像手写的代码一样
- **点评**：速度很快，不需要到运行期处理，结合到框架中使用方便

## 四、总结

- 其实对象属性转换的操作无非是基于反射、AOP、CGlib、ASM、Javassist 在编译时和运行期进行处理，再有好的思路就是在编译前生成出对应的get、set，就像手写出来的一样。
- 所以我更推荐我喜欢的 MapStruct，这货用起来还是比较舒服的，一种是来自于功能上的拓展性，易用性和兼容性。
- 无论哪种使用，都要做一下完整的测试和验证，不要上来就复制粘贴，否则你可能早早的就把挖好坑了，当然不一定是哪个兄弟来填坑了。
