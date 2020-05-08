# CodeGuide | 程序员编码指南

> **作者：** 小傅哥，Java Developer，[:pencil2: 虫洞 · 科技栈，作者](https://bugstack.cn)，[:trophy: CSDN 博客专家](https://bugstack.blog.csdn.net)

> 本文档是作者小傅哥从网上资料获取整理，方便学习使用。ASM 是一个 Java 字节码操控框架。它能被用来动态生成类或者增强既有类的功能。ASM 可以直接产生二进制 class 文件，也可以在类被加载入 Java 虚拟机之前动态改变类行为。如果本文能为您提供帮助，请给予支持(关注、点赞、分享)！

<br/>
<div align="center">
    <a href="https://bugstack.cn" style="text-decoration:none"><img src="https://bugstack.cn/assets/images/icon.svg" width="128px"></a>
</div>
<br/>  

<div align="center">
<a href="https://github.com/fuzhengwei/CodeGuide"><img src="https://badgen.net/github/stars/fuzhengwei/CodeGuide?icon=github&color=4ab8a1"></a>
<a href="https://github.com/fuzhengwei/CodeGuide"><img src="https://badgen.net/github/forks/fuzhengwei/CodeGuide?icon=github&color=4ab8a1"></a>
<a href="https://bugstack.cn" target="_blank"><img src="https://bugstack.cn/assets/images/onlinebook.svg"></a>
<a href="https://bugstack.cn/assets/images/qrcode.png?x-oss-process=style/may"><img src="https://itstack.org/_media/wxbugstack.svg"></a>
</div>
<br/>

**如何支持：**
- 关注公众号 [bugstack虫洞栈](https://itstack.org/_media/qrcode.png?x-oss-process=style/may)
- 点击右上角Star :star: 给予关注
- 分享给您身边更多的小伙伴

##  转载分享

建立本开源项目的初衷是基于个人学习与工作中对 Java 相关技术栈的总结记录，在这里也希望能帮助一些在学习 Java 过程中遇到问题的小伙伴，如果您需要转载本仓库的一些文章到自己的博客，请按照以下格式注明出处，谢谢合作。

```
作者：小傅哥
链接：https://bugstack.cn
来源：bugstack虫洞栈
```

## 与我联系

- **加群交流**
本群的宗旨是给大家提供一个良好的技术学习交流平台，所以杜绝一切广告！由于微信群人满 100 之后无法加入，请扫描下方二维码先添加作者 “小傅哥” 微信，备注：加群。
<img src="https://itstack.org/_media/fustack.png?x-oss-process=style/may" width="180" height="180"/>

- **公众号**
沉淀、分享、成长，专注于原创专题案例，以最易学习编程的方式分享知识，让自己和他人都能有所收获。目前已完成的专题有；Netty4.x实战专题案例、用Java实现JVM、基于JavaAgent的全链路监控、手写RPC框架、DDD专题案例、源码分析等。
<img src="https://itstack.org/_media/qrcode.png?x-oss-process=style/may" width="180" height="180"/>

## 参与贡献

1. 如果您对本项目有任何建议或发现文中内容有误的，欢迎提交 issues 进行指正。
2. 对于文中我没有涉及到知识点，欢迎提交 PR。

## 致谢

感谢以下人员对本仓库做出的贡献，当然不仅仅只有这些贡献者，这里就不一一列举了。如果你希望被添加到这个名单中，并且提交过 Issue 或者 PR，请与我联系。

<a href="https://github.com/linw7">
    <img src="https://avatars0.githubusercontent.com/u/3761578?s=460&v=4" style="border-radius:5px" width="50px">
</a> 
<a href="https://github.com/g10guang">
    <img src="https://avatars0.githubusercontent.com/u/30902679?s=400&v=4" style="border-radius:5px" width="50px">
</a> 
<a href="https://github.com/g10guang">
    <img src="https://avatars1.githubusercontent.com/u/15908832?s=180&v=4" style="border-radius:5px" width="50px">
</a>
