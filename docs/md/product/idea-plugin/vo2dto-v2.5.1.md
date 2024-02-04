---
title: IDEA Plugin vo2dto v2.5.1
lock: no
---

# IDEA Plugin vo2dto —— 这款插件，为开发提效80%，已经有8.1k安装量！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

哈喽，大家好我是技术UP主小傅哥。

如果你担心维护成本和性能考量，不想使用 `BeanUtils.copyProperties` 复制对象，也不想大面积的增加配置文件使用 `MapStruct`。而是只想要要手写效果的 `x.set(y.get)` 模型。那么请立刻在 IDEA 中安装插件 **vo2dto v2.5.1** 最新版本。截止到目前小傅哥开发的这款插件已经有 **8.1k 安装量**！

<div align="center"><img src="https://bugstack.cn/images/article/product/idea-plugin/vo2dto-2.5.1-00.png" width="550px"></div>

IDEA Plugin vo2dto 是一款用于帮助使用 IntelliJ IDEA 编写代码的研发人员，快速生成两个对象转换过程中所需要大量的 `x.set(y.get)` 代码块的插件工具。在最新 v2.5.1 版本中已支持 Lombok.Builder 模式，让使用可丝滑得嘞！

>文末提供了此插件的源码地址，你可以针对使用优化提交PR，以后那么多人的使用，都会看见你的贡献💐。

## ✨ 特性

1. 2个对象的转换操作，通过复制 X x 对象，转换给 Y y 对象
2. 允许使用 lombok 对象转换、lombok 和普通对象转换，对于 serialVersionUID 属性过滤
3. 支持类继承类，全量的对象转换操作
4. 含记忆功能的弹窗选择映射关系，支持全量对象、支持匹配对象、也支持空转换，生成一组set但无get的对象
5. 支持对于引入不同包下的同名类处理
6. 支持 Lombok.Builder 模式创建转换对象

## 🛠️ 安装

### 1. 在线安装

| IDEA Plugin 搜索vo2dto直接在线安装即可|
|:---:|
| <div align="center"><img src="https://bugstack.cn/images/article/product/idea-plugin/vo2dto-2.5.1-02.png" width="700px"></div>|

### 2. 手动安装

- 下载：[https://github.com/fuzhengwei/vo2dto/releases/tag/v2.5.1](https://github.com/fuzhengwei/vo2dto/releases/tag/v2.5.1)
- 安装：

|  IDEA Plugin 手动安装，导入下载包  |
|:------------------------:|
| <div align="center"><img src="https://bugstack.cn/images/article/product/idea-plugin/vo2dto-2.5.1-03.png" width="700px"></div> |

## 🔨 使用

- 视频：[https://www.bilibili.com/video/BV13Y411h7fv](https://www.bilibili.com/video/BV13Y411h7fv) - `视频内有完整的使用介绍和插件设计`
- 描述：你需要复制被转换 X x = new X() 中的 X x 部分，无论它是方法入参还是实例化或者是接口回值，接下来鼠标定位到转换对象 Y y 上，可以定位到`类 大Y`、或者`属性 小y`，这样我就可以知道你要做到是X的对象的属性值，转换到Y对象的属性值上。接下来帮你快速生成全部的 `y.set(x.get)` 代码片段。

| IDEA Plugin vo2dto 使用演示图|
|:---:|
| <div align="center"><img src="https://bugstack.cn/images/article/product/idea-plugin/vo2dto-2.5.1-04.png" width="700px"></div> |

```java
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class ApiTest {

    /**
     * 普通模式
     */
    public void test_vo2dto01(UserVO user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserNickName(user.getUserNickName());
        userDTO.setUserHead(user.getUserHead());
    }
    /**
     * lombok Builder 模式
     */
    public void test_vo2dto02(UserVO user) {
        UserDTO userDTO = UserDTO.builder()
                .userId(user.getUserId())
                .userNickName(user.getUserNickName())
                .userHead(user.getUserHead())
                .build();
    }
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class UserDTO {

        private String userId;
        private String userIdx;
        private String userNickName;
        private String userHead;
        private int page;
        private int rows;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class UserVO {

        private Long id;
        private String userId;
        private String userNickName;
        private String userHead;
        private String userPassword;
        private Date createTime;
        private Date updateTime;

    }

}
```

- 注意；v2.5.1 版本已支持了 Lombok Builder 模式，方便使用 Lombok 的伙伴创建对象。如图使用方式即可完成创建过程。

## 🤝 共建

**源码**：https://github.com/fuzhengwei/vo2dto - 你可以Fork工程，了解这套组件源码，对使用过程中所需的优化和扩展，提交你的代码。那么下次发版就会带上你的贡献了💐

<div align="center"><img src="https://bugstack.cn/images/article/product/idea-plugin/vo2dto-2.5.1-05.png" width="700px"></div>

## 💐 成长

这样的一个8000多安装量的开源组件项目，仅仅是小傅哥这篇知识社群「星球：码农会锁」中的一个小小内容。此外还包括了；大营销平台、Api网关、Lottery 抽奖、IM通信、SpringBoot Starter、IDEA Plugin 等内容，也还带着伙伴一起做开源的SDK发布到Maven仓库。

如果你想🔜快速的提升技术，是非常有必要跟着小傅哥一起学习。以我在大厂的业务经历、技术经验、落地能力，可以为你提高更高的见识。👣 踩在我的肩膀，你能看的更远！

>赶紧加入星球，能做到这样的技术项目实战社群，真的不多！你只是投入一顿大麻辣烫💰，就🉐获得超级大的回报！

>[🧧加入学习](https://bugstack.cn/md/zsxq/other/join.html)