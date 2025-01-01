---
title: IDEA Plugin vo2dto v2.5.5
lock: no
---

# IDEA Plugin vo2dto —— 这款插件，已经有20k安装量，月增量1000+！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

公司明确要求禁止在项目中使用 `BeanUtils.copyProperties` 复制对象，不仅是因为性能问题，更多是这种方式根本不知道有哪些对象属性被转换了。而手动编码 x.set(y.get) 是最稳定可靠处理方式。

但如果有几十个属性怎么办😰！那么你可以立即安装这款免费的 vo2dto v2.5.5 版本插件。截止到目前小傅哥开发的这款插件已经有 20.1k 安装量！

<div align="center"><img src="https://bugstack.cn/images/article/product/idea-plugin/vo2dto-2.5.1-00.png" width="600px"></div>

IDEA Plugin vo2dto 是一款用于帮助使用 IntelliJ IDEA 编写代码的研发人员，快速生成两个对象转换过程中所需要大量的 `x.set(y.get)` 代码块的插件工具。在最新 v2.5.1 版本中已支持 Lombok.Builder 模式，让使用可丝滑得嘞！

>文末提供了此插件的源码地址，你可以针对使用优化提交PR，以后那么多人的使用，都会看见你的贡献💐。

## ✨ 特性

1. 2个对象的转换操作，通过复制 X x 对象，转换给 Y y 对象
2. 允许使用 lombok 对象转换、lombok 和普通对象转换，对于 serialVersionUID 属性过滤
3. 支持类继承类，全量的对象转换操作
4. 含记忆功能的弹窗选择映射关系，支持全量对象、支持匹配对象、也支持空转换，生成一组set但无get的对象
5. 支持对于引入不同包下的同名类处理
6. 支持 Lombok.Builder 模式创建转换对象
7. 支持类的内部类对象，进行转换

## 👨🏻‍💻 重构

**这次我要说说重构！**

其实最开始这个项目并不大，简单的建了个抽象模板类定义执行步骤，方法都写在子类里。三下五除二就完成了对象转换功能。

但随着小伙伴们不断的提出一些使用诉求后，这里的逻辑变得的复杂了，并且由于本身 IDEA Plugin 的开发，很多时候都要一点点的处理那些对象属性的数据，兼容各种类文件所在的包信息，还有Lombok以及类的内部类。所以，每次维护起来都像是重新写一遍一样。**时间一长，都不认识它了！**

所以，为了改变这种情况。在 v2.5.5 版本的开发中，做了首次的工程重构，把流水面条的代码，用规则树拆分，让不同的节点实现不同的功能。优雅的效果，如图；

<div align="center"><img src="https://bugstack.cn/images/article/product/idea-plugin/vo2dto-2.5.5-01.png" width="600px"></div>

这是一套规则树的模型结构，在 vo2dto 插件开发中的实践使用。关于这块的设计模式可以在这里学习；[https://bugstack.cn/md/develop/design-pattern/2024-08-25-chain-tree.html](https://bugstack.cn/md/develop/design-pattern/2024-08-25-chain-tree.html)

通过节点功能对逻辑边界的拆解，让每一块功能区都可以显而易见的找到和处理，这样即使是过去很长时间，在看这段代码也能很轻松的知道每一块在干什么。其实代码写的最好的目标就是看代码就像看文档，用类划分边界比只单纯的叠加方法要清晰的多。

<div align="center"><img src="https://bugstack.cn/images/article/product/idea-plugin/vo2dto-2.5.5-02.png" width="600px"></div>

- 重构前：[https://github.com/fuzhengwei/vo2dto/tree/2.5.4](https://github.com/fuzhengwei/vo2dto/tree/2.5.4)
- 重构后：[https://github.com/fuzhengwei/vo2dto/tree/2.5.5](https://github.com/fuzhengwei/vo2dto/tree/2.5.5)

如果感兴趣这样一个处理，可以进入到重构前后的代码，看看设计模式如何处理的这部分逻辑，怎么拆分的上下文逻辑。可以说非常优雅！

## 🛠️ 安装

### 1. 在线安装

| IDEA Plugin 搜索vo2dto直接在线安装即可|
|:---:|
| <div align="center"><img src="https://bugstack.cn/images/article/product/idea-plugin/vo2dto-2.5.5-03.png" width="700px"></div>|

### 2. 手动安装

- 下载：[https://github.com/fuzhengwei/vo2dto/releases/tag/v2.5.5](https://github.com/fuzhengwei/vo2dto/releases/tag/v2.5.5)
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
  
    /**
     * 类的内部类转换
     */
    public void test(UserDTO userDTO) {
         UserVO.UserVO2 userVO2

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

- 注意；v2.5.5 版本已支持了 `类的内部类` 模式，如图使用方式即可完成创建过程。

## 💐 成长

如果你的简历也能有一个这样的小组件，让那么多的程序员👨🏻‍💻进行使用，那么对你的面试简历来说也是非常亮眼的一笔。

包括；这样的组件，还有；OPenAI 代码自动评审、透视业务监控、动态线程池，以及大量的业务项目；大营销平台、小型支付商城、拼团交易等，你都可以跟着小傅哥一起学习。👣 踩在我的肩膀，你能看的更远，走的更快，上的更高！

