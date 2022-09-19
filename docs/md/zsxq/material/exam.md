---
title: 考试：能力测评
lock: no
---

# 考试：能力测评

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/exam-00.png?raw=true">
    <div style="font-size: 12px;"><a href="https://t.zsxq.com/Ja27ujq">星球介绍：码农会锁 - 实战项目、专属小册、问题解答、简历指导、架构图稿、视频课程</a></div>
</div>

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言：什么是面试？

`你的技术广度在哪？你的技术深度在哪？`

**面试**本身就一场对你能力质疑的过程，面试官必须通过考题来验证你的能力是否真实。正式因为我对你不了解，所以我要通过一些提问来得到我心里**对你的评分**。

如果你把面试当成是一场造火箭的演示，那也无可厚非。虽然最终可能你仍旧是80%的CRUD搬砖工，但就像没有桥墩和护栏的跨江大桥，也是没有几个人能敢上去走，即使平常我们都不是把着护栏过桥。这就像企业招聘员工，我要的就是考察出你的边界在哪，你应对风险的能力有多强。

好，这就是面试的目的！那么接下来，小傅哥也帮助你做一次**技术能力检验**考试。在100道题，考试中，看看你属于哪个范围；`在校实习生`、`初级工程师`、`中级工程师`、`高级工程师`还是有`大厂架构师`的潜力。加油 💪🏻

## 二、考试：准备好了吗？

- **试卷名称**：[《Java 工程师 100 道考题 v1.0》](https://docs.qq.com/form/page/DT3JKRWN5bkh4U2J2) —— *扫描二维码答题或阅读公众号原文答题*
- **出题范围**：这是一套根据小傅哥博客【[bugstack.cn](https://bugstack.cn/)】归纳出来的考试题目，用于小伙伴自己的学习成果检查。
- **考题范围**：数据结构、算法、源码、设计模式、系统架构、中间件、网络通信、实战项目、扩展问题
- **等级评估**：
  - 在校实习生：00分-30分
  - 初级工程师：30分-60分
  - 中级工程师：60分-70分
  - 高级工程师：70分-80分
  - 大厂架构师：80分+

---

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/exam-01.png?raw=true" width="500px">
    <div style="font-size: 12px;"><a href="https://docs.qq.com/form/page/DT3JKRWN5bkh4U2J2">考题：Java 工程师 100 道考题 v1.0 @小傅哥 bugstack.cn</a></div>
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/exam-02.png?raw=true" width="500px">
</div>

## 三、查看：你的总排名！

**考完了吗，快来查看下你的总分排行吧(动态更新Ing)** —— 80分以上的小伙伴，可以找小傅哥内推！

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/exam-03.png?raw=true" width="600px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/exam-04.png?raw=true" width="600px">
</div>

## 四、解析：考题知识点

`独生子女生娃，没救了！`

小傅哥，你的100道Java考题，我只考了16分，是不是没救了！给我个答案吧！那么鉴于不少小伙伴已经考完了，那么小傅哥就做一个考题解析，方便读者可以针对自己的问题进行补充学习。

其实这些考题只是相当于在你的技术栈地图中，拎出100个检查站点，进行片段化的提问，来检查你对知识网的覆盖程度。而学习的最终目标远不是来背这些题，那样是没有多大的意义的，否则你在面对面试官时换个方向再问，可能你还是没法回答。所以更有意义的事是慢下来，不贪多，一步一个脚印的用自己的思考和实践验证学会这些内容。

好啦，小傅哥就做个这些问题的分析，如果你还没有参与考试也可以先尝试考一下，再看解答会更有感觉。

### 1. 算法（1~10）

#### 01：常见的数据结构有哪些

- **选项**：
  - A.队列
  - B.栈
  - C.哈希表
  - D.堆
  - E.字典树
  - F.树（二叉查找树、AVL树、红黑树、线段树）
  - G.桥
  - H.图
  - I.并查集
  - J布隆过滤器

- **答案**：A、B、C、D、E、F、H、I、J
- **解析**：基本的数据结构都是基于数组和链表满足不同场景诉求所扩展出来的
- **详细**：[https://zh.wikipedia.org/wiki/%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E6%9C%AF%E8%AF%AD%E5%88%97%E8%A1%A8](https://zh.wikipedia.org/wiki/%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E6%9C%AF%E8%AF%AD%E5%88%97%E8%A1%A8) —— 数据结构属于列表

#### 02：优先队列是基于什么数据结构实现？

- **选项**：
  - A.链表
  - B.哈希表
  - C.栈
  - D.二叉堆
- **答案**：D
- **解析**：Java 中的优先队列 PriorityQueue 是基于数组结构的二叉堆实现的
- **详细**：[https://bugstack.cn/md/algorithm/data-structures/2022-08-06-queue.html](https://bugstack.cn/md/algorithm/data-structures/2022-08-06-queue.html)

#### 03：HashMap 解决哈希碰撞的数据结构？

- **选项**：
  - A.杜鹃散列
  - B.开放寻址
  - C.拉链寻址
  - D.合并散列
- **答案**：C
- **解析**：HashMap 为了解决元素的碰撞，采用哈希桶 + 链表/红黑树的数据结构，也称为拉链寻址。*开放寻址是 ThreadLocal 的数据结构*
- **详细**：[https://bugstack.cn/md/algorithm/data-structures/2022-08-27-hash-table.html#_2-%E6%8B%89%E9%93%BE%E5%AF%BB%E5%9D%80](https://bugstack.cn/md/algorithm/data-structures/2022-08-27-hash-table.html#_2-%E6%8B%89%E9%93%BE%E5%AF%BB%E5%9D%80)

#### 04：ArrayDeque 实现堆扩容时需要进行几次元素拷贝？

- **选项**：
  - A.1
  - B.2
  - C.3
  - D.4
- **答案**：B
- **解析**：ArrayDeque 是基于数组实现的堆栈结构，在元素的存放上因为有扩容数组操作，为了保持堆栈的先进后出的特性，所以需要分段拷贝元素。
- **详细**：[https://bugstack.cn/md/algorithm/data-structures/2022-08-17-stack.html](https://bugstack.cn/md/algorithm/data-structures/2022-08-17-stack.html)

#### 05：基于数组实现的二叉堆，元素k如何计算父节点

- **选项**：
  - A.k >> 1
  - B.(k + 1) << 1
  - C.(k - 1) <<< 1
  - D.(k - 1) >>> 1
- **答案**：D
- **解析**：基于数组实现的二叉堆所有子节点与父节点的位置关系，是子节点除以2取整，就是父节点的位置。之所以是这样的一个计算关系，因为它整个二叉堆上每一层元素在数组位置的存放上，就是一个1、2、4、8码的结构，也就是二进制结构。所以可以除以2取整计算父节点位置。那么使用二进制计算就是 (k - 1) >>> 1 
- **详细**：[https://bugstack.cn/md/algorithm/data-structures/2022-09-03-heap.html](https://bugstack.cn/md/algorithm/data-structures/2022-09-03-heap.html)

#### 06：链表删除任意元素的时间复杂度

- **选项**：
  - A.O(1)
  - B.O(n)
  - C.O(logn)
  - D.O(n^2)
- **答案**：B
- **解析**：链表的操作只有插入的时间复杂度是O(1)，其他操作时间复杂度都O(n)。也因为有这样的情况，所以开始设计出二叉搜索树、AVL平衡树、红黑树等数据结构。让时间复杂度尽可能平衡到O(logn)。
- **详细**：[https://bugstack.cn/md/algorithm/data-structures/2022-07-22-linked-list.html](https://bugstack.cn/md/algorithm/data-structures/2022-07-22-linked-list.html)

#### 07：HashMap 负载因子的使用

- **选项**：
  - A.负载因子越大，有限空间内存放元素越少
  - B.负载因子越小，有限空间内存放元素越多
  - C.负载因子越大，有限空间内存放元素越多
  - D.负载因子越小，有限空间内存放元素越少
- **答案**：C、D
- **解析**：对于 HashMap 来说，复杂因子的作用就是为了调衡元素的的碰撞情况。负载因子太大，是可以节省空间但碰撞的概率就会增大。反之就是用空间换时间，需要拉倒空间解决碰撞。所以 HashMap 选择 0.75 也就是 3/4 作为默认负载因子。
- **详细**：[https://bugstack.cn/md/java/interview/2020-08-07-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC3%E7%AF%87%E3%80%8AHashMap%E6%A0%B8%E5%BF%83%E7%9F%A5%E8%AF%86%EF%BC%8C%E6%89%B0%E5%8A%A8%E5%87%BD%E6%95%B0%E3%80%81%E8%B4%9F%E8%BD%BD%E5%9B%A0%E5%AD%90%E3%80%81%E6%89%A9%E5%AE%B9%E9%93%BE%E8%A1%A8%E6%8B%86%E5%88%86%EF%BC%8C%E6%B7%B1%E5%BA%A6%E5%AD%A6%E4%B9%A0%E3%80%8B.html#_3-%E5%88%9D%E5%A7%8B%E5%8C%96%E5%AE%B9%E9%87%8F%E5%92%8C%E8%B4%9F%E8%BD%BD%E5%9B%A0%E5%AD%90](https://bugstack.cn/md/java/interview/2020-08-07-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC3%E7%AF%87%E3%80%8AHashMap%E6%A0%B8%E5%BF%83%E7%9F%A5%E8%AF%86%EF%BC%8C%E6%89%B0%E5%8A%A8%E5%87%BD%E6%95%B0%E3%80%81%E8%B4%9F%E8%BD%BD%E5%9B%A0%E5%AD%90%E3%80%81%E6%89%A9%E5%AE%B9%E9%93%BE%E8%A1%A8%E6%8B%86%E5%88%86%EF%BC%8C%E6%B7%B1%E5%BA%A6%E5%AD%A6%E4%B9%A0%E3%80%8B.html#_3-%E5%88%9D%E5%A7%8B%E5%8C%96%E5%AE%B9%E9%87%8F%E5%92%8C%E8%B4%9F%E8%BD%BD%E5%9B%A0%E5%AD%90)

#### 08：ThreadLocal 解决哈希碰撞的散列算法？

- **选项**：
  - A.扰动函数
  - B.斐波那契
  - C.平方散列
  - D.合并散列
- **答案**：B
- **解析**：ThreadLocal 是基于数组的开放寻址数据结构，采用的斐波那契散列，因为它在有限空间内，对线程内的元素计算索引位置更加分散。*HashMap 为了降低元素的碰撞采用的是扰动函数*
- **详细**：[https://bugstack.cn/md/java/interview/2020-09-23-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC12%E7%AF%87%E3%80%8A%E9%9D%A2%E8%AF%95%E5%AE%98%EF%BC%8CThreadLocal%20%E4%BD%A0%E8%A6%81%E8%BF%99%E4%B9%88%E9%97%AE%EF%BC%8C%E6%88%91%E5%B0%B1%E6%8C%82%E4%BA%86%EF%BC%81%E3%80%8B.html](https://bugstack.cn/md/java/interview/2020-09-23-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC12%E7%AF%87%E3%80%8A%E9%9D%A2%E8%AF%95%E5%AE%98%EF%BC%8CThreadLocal%20%E4%BD%A0%E8%A6%81%E8%BF%99%E4%B9%88%E9%97%AE%EF%BC%8C%E6%88%91%E5%B0%B1%E6%8C%82%E4%BA%86%EF%BC%81%E3%80%8B.html)

#### 09：HashMap 链表转红黑树条件

- **选项**：
  - A.空间长度8，元素个数大于8
  - B.空间长度64，元素个数大于7
  - C.空间长度8，元素个数大于7
  - D.空间长度64，元素个数大于8
- **答案**：D
- **解析**：HashMap 不是一上来就在最低的初始化长度内，超过最长链表长度就直接转换红黑树，而是达到64位长度后，元素大于8，还有碰撞的情况下将链表转换为红黑树。*HashMap 里的数学知识点含量巨大*
- **详细**：[https://bugstack.cn/md/java/interview/2020-08-13-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC4%E7%AF%87%E3%80%8AHashMap%E6%95%B0%E6%8D%AE%E6%8F%92%E5%85%A5%E3%80%81%E6%9F%A5%E6%89%BE%E3%80%81%E5%88%A0%E9%99%A4%E3%80%81%E9%81%8D%E5%8E%86%EF%BC%8C%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E3%80%8B.html](https://bugstack.cn/md/java/interview/2020-08-13-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC4%E7%AF%87%E3%80%8AHashMap%E6%95%B0%E6%8D%AE%E6%8F%92%E5%85%A5%E3%80%81%E6%9F%A5%E6%89%BE%E3%80%81%E5%88%A0%E9%99%A4%E3%80%81%E9%81%8D%E5%8E%86%EF%BC%8C%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E3%80%8B.html)

#### 10：关于 ArrayList 和 LinkedList 插入1000万个元素性能对比

- **选项**：
  - A.头插时，ArrayList 性能高于 LinkedList
  - B.头插时，LinkedList 性能高于 ArrayList
  - C.中间插，ArrayList 性能高于 LinkedList
  - D.中间插，LinkedList 性能高于 ArrayList
  - E.尾插时，ArrayList 性能高于 LinkedList
  - F.尾插时，LinkedList 性能高于 ArrayList
- **答案**：B、C、E
- **解析**：ArrayList 耗时在元素拷贝、LinkedList 耗时在节点创建，大批量的新元素创建也是一种比较耗时的行为。*具体可以参考压测数据*
- **详细**：[https://bugstack.cn/md/java/interview/2020-08-30-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC8%E7%AF%87%E3%80%8ALinkedList%E6%8F%92%E5%85%A5%E9%80%9F%E5%BA%A6%E6%AF%94ArrayList%E5%BF%AB%EF%BC%9F%E4%BD%A0%E7%A1%AE%E5%AE%9A%E5%90%97%EF%BC%9F%E3%80%8B.html](https://bugstack.cn/md/java/interview/2020-08-30-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC8%E7%AF%87%E3%80%8ALinkedList%E6%8F%92%E5%85%A5%E9%80%9F%E5%BA%A6%E6%AF%94ArrayList%E5%BF%AB%EF%BC%9F%E4%BD%A0%E7%A1%AE%E5%AE%9A%E5%90%97%EF%BC%9F%E3%80%8B.html)

### 2. 源码-Java（11~20）

#### 01：JDK创建代理的方式

- **选项**：
  - A.new JavassistProxy().defineClass(clazz.getName(), bytes, 0, bytes.length).newInstance()
  - B.dynamicType.load(Thread.currentThread().getContextClassLoader()).getLoaded().newInstance();
  - C.Proxy.newProxyInstance
  - D.Enhancer.create(object.getClass(), this);
- **答案**：C
- **解析**：JDK 默认提供创建代理的方式是 Proxy.newProxyInstance，而不是如 Javassist、Cglib 等方式创建。
- **详细**：[https://bugstack.cn/md/java/interview/2020-10-14-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC13%E7%AF%87%E3%80%8A%E9%99%A4%E4%BA%86JDK%E3%80%81CGLIB%EF%BC%8C%E8%BF%98%E6%9C%893%E7%A7%8D%E7%B1%BB%E4%BB%A3%E7%90%86%E6%96%B9%E5%BC%8F%EF%BC%9F%E9%9D%A2%E8%AF%95%E5%8F%88%E5%8D%A1%E4%BD%8F%EF%BC%81%E3%80%8B.html](https://bugstack.cn/md/java/interview/2020-10-14-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC13%E7%AF%87%E3%80%8A%E9%99%A4%E4%BA%86JDK%E3%80%81CGLIB%EF%BC%8C%E8%BF%98%E6%9C%893%E7%A7%8D%E7%B1%BB%E4%BB%A3%E7%90%86%E6%96%B9%E5%BC%8F%EF%BC%9F%E9%9D%A2%E8%AF%95%E5%8F%88%E5%8D%A1%E4%BD%8F%EF%BC%81%E3%80%8B.html)

#### 02：volatile 关键字的作用

- **选项**：
  - A.可见性
  - B.有序性
  - C.原子性
  - D.防重排
- **答案**：A、D
- **解析**：在 volatile 的特性描述中主要为可见性和防重排，尽可能保证有序性。所以这里的选择 A、D 或者 A、B、D 都可以。但 volatile 是不具有原子性的，synchronized 既可以保证原子性 ，也可以保证可见性。
- **详细**：[https://bugstack.cn/md/java/interview/2020-10-21-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC14%E7%AF%87%E3%80%8Avolatile%20%E6%80%8E%E4%B9%88%E5%AE%9E%E7%8E%B0%E7%9A%84%E5%86%85%E5%AD%98%E5%8F%AF%E8%A7%81%EF%BC%9F%E6%B2%A1%E6%9C%89%20volatile%20%E4%B8%80%E5%AE%9A%E4%B8%8D%E5%8F%AF%E8%A7%81%E5%90%97%EF%BC%9F%E3%80%8B.html](https://bugstack.cn/md/java/interview/2020-10-21-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC14%E7%AF%87%E3%80%8Avolatile%20%E6%80%8E%E4%B9%88%E5%AE%9E%E7%8E%B0%E7%9A%84%E5%86%85%E5%AD%98%E5%8F%AF%E8%A7%81%EF%BC%9F%E6%B2%A1%E6%9C%89%20volatile%20%E4%B8%80%E5%AE%9A%E4%B8%8D%E5%8F%AF%E8%A7%81%E5%90%97%EF%BC%9F%E3%80%8B.html)

#### 03：Integer.toHexString("".hashCode()) 输出结果？

- **选项**：
  - A.抛异常
  - B.1
  - C.0
  - D.7fffffff
- **答案**：C
- **解析**：哈希的计算公式；`s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]` 当字符串为空时则不会通过公式计算哈希值，也就是0，那么0的 toHexString 16进制转换仍然是0。
- **详细**：[https://bugstack.cn/md/java/interview/2020-08-04-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC2%E7%AF%87%E3%80%8A%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%EF%BC%8CHashCode%E4%B8%BA%E4%BB%80%E4%B9%88%E4%BD%BF%E7%94%A831%E4%BD%9C%E4%B8%BA%E4%B9%98%E6%95%B0%EF%BC%9F%E3%80%8B.html](https://bugstack.cn/md/java/interview/2020-08-04-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC2%E7%AF%87%E3%80%8A%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%EF%BC%8CHashCode%E4%B8%BA%E4%BB%80%E4%B9%88%E4%BD%BF%E7%94%A831%E4%BD%9C%E4%B8%BA%E4%B9%98%E6%95%B0%EF%BC%9F%E3%80%8B.html)

#### 04：哪些是公平锁实现方式？

- **选项**：
  - A.CLH
  - B.MCSLock
  - C.TicketLock
  - D.SpinLock
- **答案**：A、B、C、D
- **解析**：CLH、MCS、Spin、Ticket 四种自旋锁的实现方式都是公平锁。
- **详细**：[https://bugstack.cn/md/java/interview/2020-11-04-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC16%E7%AF%87%E3%80%8A%E7%A0%81%E5%86%9C%E4%BC%9A%E9%94%81%EF%BC%8CReentrantLock%E4%B9%8B%E5%85%AC%E5%B9%B3%E9%94%81%E8%AE%B2%E8%A7%A3%E5%92%8C%E5%AE%9E%E7%8E%B0%E3%80%8B.html](https://bugstack.cn/md/java/interview/2020-11-04-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC16%E7%AF%87%E3%80%8A%E7%A0%81%E5%86%9C%E4%BC%9A%E9%94%81%EF%BC%8CReentrantLock%E4%B9%8B%E5%85%AC%E5%B9%B3%E9%94%81%E8%AE%B2%E8%A7%A3%E5%92%8C%E5%AE%9E%E7%8E%B0%E3%80%8B.html)

#### 05：Synchronized 和 ReentrantLock 的描述

- **选项**：
  - A.两者都是独占锁，只允许线程互斥的访问临界资源
  - B.两者都是基于AQS提供的共享资源同步框架实现的（独占、可重入、允许中断）
  - C.ReentrantLock是JVM层面的实现，synchronized是Java层面的实现
  - D.ReentrantLock是Java层面的实现，synchronized是JVM层面的实现
- **答案**：A、D
- **解析**：两者都是独占锁，但 Synchronized 是基于 JVM 层面的，ReentrantLock 是基于 Java 层面的，也就是基于 Java API 实现的。
- **详细**：[https://bugstack.cn/md/java/interview/2020-11-11-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC17%E7%AF%87%E3%80%8A%E7%A0%81%E5%86%9C%E4%BC%9A%E9%94%81%EF%BC%8CReentrantLock%E4%B9%8BAQS%E5%8E%9F%E7%90%86%E5%88%86%E6%9E%90%E5%92%8C%E5%AE%9E%E8%B7%B5%E4%BD%BF%E7%94%A8%E3%80%8B.html](https://bugstack.cn/md/java/interview/2020-11-11-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC17%E7%AF%87%E3%80%8A%E7%A0%81%E5%86%9C%E4%BC%9A%E9%94%81%EF%BC%8CReentrantLock%E4%B9%8BAQS%E5%8E%9F%E7%90%86%E5%88%86%E6%9E%90%E5%92%8C%E5%AE%9E%E8%B7%B5%E4%BD%BF%E7%94%A8%E3%80%8B.html)

#### 06：Thread.start()的启动过程包括？

- **选项**：
  - A.Java 创建线程和启动
  - B.调用本地方法 start0()
  - C.JVM 中 JVM_StartThread 的创建和启动
  - D.设置线程状态等待被唤醒
  - E.根据不同的OS启动线程并唤醒
  - F.回调 run() 方法启动 Java 线程
- **答案**：A、B、C、D、E、F
- **解析**：线程的创建包括了很多底层 C 语言的逻辑，体现在；Thread.c、jvm.cpp、thread.cpp、os.cpp、os_linux.cpp、os_windows.cpp、vmSymbols.cpp
- **详细**：[https://bugstack.cn/md/java/interview/2020-11-25-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC19%E7%AF%87%E3%80%8AThread.start()%20%EF%BC%8C%E5%AE%83%E6%98%AF%E6%80%8E%E4%B9%88%E8%AE%A9%E7%BA%BF%E7%A8%8B%E5%90%AF%E5%8A%A8%E7%9A%84%E5%91%A2%EF%BC%9F%E3%80%8B.html](https://bugstack.cn/md/java/interview/2020-11-25-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC19%E7%AF%87%E3%80%8AThread.start()%20%EF%BC%8C%E5%AE%83%E6%98%AF%E6%80%8E%E4%B9%88%E8%AE%A9%E7%BA%BF%E7%A8%8B%E5%90%AF%E5%8A%A8%E7%9A%84%E5%91%A2%EF%BC%9F%E3%80%8B.html)

#### 07：Thread 线程状态包括？

- **选项**：
  - A.NEW
  - B.RUNNABLE
  - C.BLOCKED
  - D.WAITING
  - E.TIMED_WAITING
  - F.TERMINATED
- **答案**：A、B、C、D、E、F
- **解析**：java.lang.Thread.State 的枚举状态包括； NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED;
- **详细**：[https://bugstack.cn/md/java/interview/2020-12-02-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC20%E7%AF%87%E3%80%8AThread%20%E7%BA%BF%E7%A8%8B%EF%BC%8C%E7%8A%B6%E6%80%81%E8%BD%AC%E6%8D%A2%E3%80%81%E6%96%B9%E6%B3%95%E4%BD%BF%E7%94%A8%E3%80%81%E5%8E%9F%E7%90%86%E5%88%86%E6%9E%90%E3%80%8B.html](https://bugstack.cn/md/java/interview/2020-12-02-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC20%E7%AF%87%E3%80%8AThread%20%E7%BA%BF%E7%A8%8B%EF%BC%8C%E7%8A%B6%E6%80%81%E8%BD%AC%E6%8D%A2%E3%80%81%E6%96%B9%E6%B3%95%E4%BD%BF%E7%94%A8%E3%80%81%E5%8E%9F%E7%90%86%E5%88%86%E6%9E%90%E3%80%8B.html)

#### 08：线程池拒绝策略包括？

- **选项**：
  - A.AbortPolicy(抛异常方式拒绝)
  - B.DiscardPolicy(直接丢弃)
  - C.DiscardOldestPolicy(丢弃存活时间最长的任务)
  - D.CallerRunsPolicy(谁提交谁执行)
- **答案**：A、B、C、D
- **解析**：线程池的拒绝策略包括；抛异常拒绝、直接丢弃、丢弃存活时间最长的和谁提交谁执行。
- **详细**：[https://bugstack.cn/md/java/interview/2020-12-09-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC21%E7%AF%87%E3%80%8A%E6%89%8B%E5%86%99%E7%BA%BF%E7%A8%8B%E6%B1%A0%EF%BC%8C%E5%AF%B9%E7%85%A7%E5%AD%A6%E4%B9%A0ThreadPoolExecutor%E7%BA%BF%E7%A8%8B%E6%B1%A0%E5%AE%9E%E7%8E%B0%E5%8E%9F%E7%90%86%EF%BC%81%E3%80%8B.html](https://bugstack.cn/md/java/interview/2020-12-09-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC21%E7%AF%87%E3%80%8A%E6%89%8B%E5%86%99%E7%BA%BF%E7%A8%8B%E6%B1%A0%EF%BC%8C%E5%AF%B9%E7%85%A7%E5%AD%A6%E4%B9%A0ThreadPoolExecutor%E7%BA%BF%E7%A8%8B%E6%B1%A0%E5%AE%9E%E7%8E%B0%E5%8E%9F%E7%90%86%EF%BC%81%E3%80%8B.html)

#### 09：JDK 常用命令叙述错误的是？

- **选项**：
  - A.javac– 编译器，将后缀名为.java的源代码编译成后缀名为.class的字节码
  - B.jstack – 获取java进程内存映射信息
  - C.jhat – java堆分析工具
  - D.jstat – JVM检测统计工具
- **答案**：B
- **解析**：jstack（Stack Trace for Java），用于生成虚拟机当前时刻的线程快照（threaddump、javacore）。所以它是 Java堆栈跟踪工具。
- **详细**：[https://bugstack.cn/md/java/interview/2021-01-13-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC26%E7%AF%87%E3%80%8AJVM%E6%95%85%E9%9A%9C%E5%A4%84%E7%90%86%E5%B7%A5%E5%85%B7%EF%BC%8C%E4%BD%BF%E7%94%A8%E6%80%BB%E7%BB%93%E3%80%8B.html](https://bugstack.cn/md/java/interview/2021-01-13-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC26%E7%AF%87%E3%80%8AJVM%E6%95%85%E9%9A%9C%E5%A4%84%E7%90%86%E5%B7%A5%E5%85%B7%EF%BC%8C%E4%BD%BF%E7%94%A8%E6%80%BB%E7%BB%93%E3%80%8B.html)

#### 10：JVM 虚拟机运行时数据区包括？

- **选项**：
  - A.Frame，栈帧
  - B.JvmStack，虚拟机栈
  - C.LocalVars，局部变量
  - D.OperandStack，操作数栈
  - E.Slot，数据槽
  - F.heap，堆，里面包括常量池和方法区
- **答案**：A、B、C、D、E、F
- **解析**：运行时数据区包括；栈帧、虚拟机栈、局部变量、操作数栈、数据槽、堆，在手写JVM中有这部分的体现。
- **详细**：[https://bugstack.cn/md/java/develop-jvm/2019-05-05-%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E5%9B%9B%E7%AB%A0%E3%80%8A%E8%BF%90%E8%A1%8C%E6%97%B6%E6%95%B0%E6%8D%AE%E5%8C%BA%E3%80%8B.html](https://bugstack.cn/md/java/develop-jvm/2019-05-05-%E7%94%A8Java%E5%AE%9E%E7%8E%B0JVM%E7%AC%AC%E5%9B%9B%E7%AB%A0%E3%80%8A%E8%BF%90%E8%A1%8C%E6%97%B6%E6%95%B0%E6%8D%AE%E5%8C%BA%E3%80%8B.html)

### 3. 源码-Spring（21~30）

#### 01：Bean 对象容器使用的是？

- **选项**：
  - A.ArrayList
  - B.LinkedList
  - C.HashSet
  - D.HashMap
- **答案**：D
- **解析**：Spring 中的 Bean 对象是存放到 HashMap 的数组结构中的，因为这个数组结构有 Key -> Value 结构，更加符合 Bean 对象的存放和获取。
- **详细**：[https://bugstack.cn/md/spring/develop-spring/2021-05-20-%E7%AC%AC2%E7%AB%A0%EF%BC%9A%E5%B0%8F%E8%AF%95%E7%89%9B%E5%88%80%EF%BC%8C%E5%AE%9E%E7%8E%B0%E4%B8%80%E4%B8%AA%E7%AE%80%E5%8D%95%E7%9A%84Bean%E5%AE%B9%E5%99%A8.html](https://bugstack.cn/md/spring/develop-spring/2021-05-20-%E7%AC%AC2%E7%AB%A0%EF%BC%9A%E5%B0%8F%E8%AF%95%E7%89%9B%E5%88%80%EF%BC%8C%E5%AE%9E%E7%8E%B0%E4%B8%80%E4%B8%AA%E7%AE%80%E5%8D%95%E7%9A%84Bean%E5%AE%B9%E5%99%A8.html)

#### 02：InstantiationStrategy Bean 对象的实例化策略有哪些？

- **选项**：
  - A.CglibSubclassingInstantiationStrategy
  - B.SimpleInstantiationStrategy
  - C.JDKInstantiationStrategy
  - D.ASMInstantiationStrategy
- **答案**：A、B
- **解析**：Spring 默认提供了 JDK（SimpleInstantiationStrategy） 和 Cglib（CglibSubclassingInstantiationStrategy） 两种实例化策略。
- **详细**：[https://bugstack.cn/md/spring/develop-spring/2021-05-30-%E7%AC%AC4%E7%AB%A0%EF%BC%9A%E5%B4%AD%E9%9C%B2%E5%A4%B4%E8%A7%92%EF%BC%8C%E5%9F%BA%E4%BA%8ECglib%E5%AE%9E%E7%8E%B0%E5%90%AB%E6%9E%84%E9%80%A0%E5%87%BD%E6%95%B0%E7%9A%84%E7%B1%BB%E5%AE%9E%E4%BE%8B%E5%8C%96%E7%AD%96%E7%95%A5.html](https://bugstack.cn/md/spring/develop-spring/2021-05-30-%E7%AC%AC4%E7%AB%A0%EF%BC%9A%E5%B4%AD%E9%9C%B2%E5%A4%B4%E8%A7%92%EF%BC%8C%E5%9F%BA%E4%BA%8ECglib%E5%AE%9E%E7%8E%B0%E5%90%AB%E6%9E%84%E9%80%A0%E5%87%BD%E6%95%B0%E7%9A%84%E7%B1%BB%E5%AE%9E%E4%BE%8B%E5%8C%96%E7%AD%96%E7%95%A5.html)

#### 03：资源解析的类型包括

- **选项**：
  - A.classpath
  - B.resources
  - C.https
  - D.rpc
- **答案**：A、B、C
- **解析**：Spring 的解析方式包括；类资源、本地resources配置、远程https拉取。
- **详细**：[https://bugstack.cn/md/spring/develop-spring/2021-06-09-%E7%AC%AC6%E7%AB%A0%EF%BC%9A%E6%B0%94%E5%90%9E%E5%B1%B1%E6%B2%B3%EF%BC%8C%E8%AE%BE%E8%AE%A1%E4%B8%8E%E5%AE%9E%E7%8E%B0%E8%B5%84%E6%BA%90%E5%8A%A0%E8%BD%BD%E5%99%A8%EF%BC%8C%E4%BB%8ESpring.xml%E8%A7%A3%E6%9E%90%E5%92%8C%E6%B3%A8%E5%86%8CBean%E5%AF%B9%E8%B1%A1.html](https://bugstack.cn/md/spring/develop-spring/2021-06-09-%E7%AC%AC6%E7%AB%A0%EF%BC%9A%E6%B0%94%E5%90%9E%E5%B1%B1%E6%B2%B3%EF%BC%8C%E8%AE%BE%E8%AE%A1%E4%B8%8E%E5%AE%9E%E7%8E%B0%E8%B5%84%E6%BA%90%E5%8A%A0%E8%BD%BD%E5%99%A8%EF%BC%8C%E4%BB%8ESpring.xml%E8%A7%A3%E6%9E%90%E5%92%8C%E6%B3%A8%E5%86%8CBean%E5%AF%B9%E8%B1%A1.html)

#### 04：BeanPostProcessor 与 BeanFactoryPostProcessor 的作用范围

- **选项**：
  - A.BeanPostProcessor 用于 Bean 对象执行初始化方法前后扩展
  - B.BeanFactoryPostProcessor 用于 Bean 对象执行初始化方法前后扩展
  - C.BeanPostProcessor 用于 BeanDefinition 加载完成后提供扩展机制
  - D.BeanFactoryPostProcessor 用于 BeanDefinition 加载完成后提供扩展机制
- **答案**：A、D
- **解析**：BeanFactoryPostProcessor 作用域 Bean 容器上下文中 refresh 对象时，提供了扩展修改 BeanDefinition 的机制。BeanPostProcessor 是在 Bean 实例化阶段的前后提供的扩展点。
- **详细**：[https://bugstack.cn/md/spring/develop-spring/2021-06-17-%E7%AC%AC7%E7%AB%A0%EF%BC%9A%E6%89%80%E5%90%91%E6%8A%AB%E9%9D%A1%EF%BC%8C%E5%AE%9E%E7%8E%B0%E5%BA%94%E7%94%A8%E4%B8%8A%E4%B8%8B%E6%96%87%EF%BC%8C%E8%87%AA%E5%8A%A8%E8%AF%86%E5%88%AB%E3%80%81%E8%B5%84%E6%BA%90%E5%8A%A0%E8%BD%BD%E3%80%81%E6%89%A9%E5%B1%95%E6%9C%BA%E5%88%B6.html](https://bugstack.cn/md/spring/develop-spring/2021-06-17-%E7%AC%AC7%E7%AB%A0%EF%BC%9A%E6%89%80%E5%90%91%E6%8A%AB%E9%9D%A1%EF%BC%8C%E5%AE%9E%E7%8E%B0%E5%BA%94%E7%94%A8%E4%B8%8A%E4%B8%8B%E6%96%87%EF%BC%8C%E8%87%AA%E5%8A%A8%E8%AF%86%E5%88%AB%E3%80%81%E8%B5%84%E6%BA%90%E5%8A%A0%E8%BD%BD%E3%80%81%E6%89%A9%E5%B1%95%E6%9C%BA%E5%88%B6.html)

#### 05：向虚拟机注册钩子的作用？

- **选项**：
  - A.init-method 初始化方法
  - B.destroy-method 销毁方法
  - C.init-bean 实例化对象方法
  - D.destroy-bean 销毁对象方法
- **答案**：B
- **解析**：虚拟机钩子 `Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("close！")));` 就是作用于 XML 中配置的 destroy-method 销毁方法上。用于对 Bean 对象的销毁处理。
- **详细**：[https://bugstack.cn/md/spring/develop-spring/2021-06-23-%E7%AC%AC8%E7%AB%A0%EF%BC%9A%E9%BE%99%E8%A1%8C%E6%9C%89%E9%A3%8E%EF%BC%8C%E5%90%91%E8%99%9A%E6%8B%9F%E6%9C%BA%E6%B3%A8%E5%86%8C%E9%92%A9%E5%AD%90%EF%BC%8C%E5%AE%9E%E7%8E%B0Bean%E5%AF%B9%E8%B1%A1%E7%9A%84%E5%88%9D%E5%A7%8B%E5%8C%96%E5%92%8C%E9%94%80%E6%AF%81%E6%96%B9%E6%B3%95.html](https://bugstack.cn/md/spring/develop-spring/2021-06-23-%E7%AC%AC8%E7%AB%A0%EF%BC%9A%E9%BE%99%E8%A1%8C%E6%9C%89%E9%A3%8E%EF%BC%8C%E5%90%91%E8%99%9A%E6%8B%9F%E6%9C%BA%E6%B3%A8%E5%86%8C%E9%92%A9%E5%AD%90%EF%BC%8C%E5%AE%9E%E7%8E%B0Bean%E5%AF%B9%E8%B1%A1%E7%9A%84%E5%88%9D%E5%A7%8B%E5%8C%96%E5%92%8C%E9%94%80%E6%AF%81%E6%96%B9%E6%B3%95.html)

#### 06：Aware 感知接口的实现类包括？

- **选项**：
  - A.BeanClassLoaderAware
  - B.BeanFactoryAware
  - C.BeanNameAware
  - D.ApplicationContextAware
- **答案**：A、B、C、D
- **解析**：Aware 是 Bean 容器的感知接口，提供了用于获取 ClassLoader、BeanFactory、BeanName、ApplicationContext 的对象。在 Bean 对象实例化的不同阶段提供调用机制，让外部获取到这些信息。
- **详细**：[https://bugstack.cn/md/spring/develop-spring/2021-06-28-%E7%AC%AC9%E7%AB%A0%EF%BC%9A%E8%99%8E%E8%A1%8C%E6%9C%89%E9%9B%A8%EF%BC%8C%E5%AE%9A%E4%B9%89%E6%A0%87%E8%AE%B0%E7%B1%BB%E5%9E%8BAware%E6%8E%A5%E5%8F%A3%EF%BC%8C%E5%AE%9E%E7%8E%B0%E6%84%9F%E7%9F%A5%E5%AE%B9%E5%99%A8%E5%AF%B9%E8%B1%A1.html](https://bugstack.cn/md/spring/develop-spring/2021-06-28-%E7%AC%AC9%E7%AB%A0%EF%BC%9A%E8%99%8E%E8%A1%8C%E6%9C%89%E9%9B%A8%EF%BC%8C%E5%AE%9A%E4%B9%89%E6%A0%87%E8%AE%B0%E7%B1%BB%E5%9E%8BAware%E6%8E%A5%E5%8F%A3%EF%BC%8C%E5%AE%9E%E7%8E%B0%E6%84%9F%E7%9F%A5%E5%AE%B9%E5%99%A8%E5%AF%B9%E8%B1%A1.html)

#### 07：FactoryBean 和 BeanFacory 的用途

- **选项**：
  - A.FactoryBean 获取 Bean 对象
  - B.BeanFacory 获取 Bean 对象
  - C.FactoryBean 创建对象
  - D.BeanFacory 创建对象
- **答案**：B、C
- **解析**：FactoryBean 工厂对象，是提供了一个使用工厂创建的对象，也就是创建那些复杂的对象。这个复杂指的是创建的对象不是直接new出来，还需一些包装和代理的操作，就像把每一个 DAO 接口封装成一个 Mapper 映射对象交给 Spring 容器管理一样。而 BeanFactory 是对象的工厂，专门提供各类对象的，也是 Spring 自身提供的获取 Bean 对象的接口。你所有获取的 Bean 对象都使用这个接口获取的。
- **详细**：[https://bugstack.cn/md/spring/develop-spring/2021-06-30-%E7%AC%AC10%E7%AB%A0%EF%BC%9A%E6%A8%AA%E5%88%80%E8%B7%83%E9%A9%AC%EF%BC%8C%E5%85%B3%E4%BA%8EBean%E5%AF%B9%E8%B1%A1%E4%BD%9C%E7%94%A8%E5%9F%9F%E4%BB%A5%E5%8F%8AFactoryBean%E7%9A%84%E5%AE%9E%E7%8E%B0%E5%92%8C%E4%BD%BF%E7%94%A8.html](https://bugstack.cn/md/spring/develop-spring/2021-06-30-%E7%AC%AC10%E7%AB%A0%EF%BC%9A%E6%A8%AA%E5%88%80%E8%B7%83%E9%A9%AC%EF%BC%8C%E5%85%B3%E4%BA%8EBean%E5%AF%B9%E8%B1%A1%E4%BD%9C%E7%94%A8%E5%9F%9F%E4%BB%A5%E5%8F%8AFactoryBean%E7%9A%84%E5%AE%9E%E7%8E%B0%E5%92%8C%E4%BD%BF%E7%94%A8.html)

#### 08：如何实现一个容器事件

- **选项**：
  - A.实现 ApplicationContextAware
  - B.实现 BeanPostProcessor
  - C.实现 BeanFactoryPostProcessor
  - D.继承 ApplicationContextEvent
- **答案**：D
- **解析**：其实像 Spring 中所有对外提供的 Aware、BeanPostProcessor、Event 都是一种 SPI 机制，满足外部对各类操作的扩展。而 Event 事件也是一种扩展机制，就像 ApplicationContextEvent 是用于监听应用容器的事件。
- **详细**：[https://bugstack.cn/md/spring/develop-spring/2021-07-07-%E7%AC%AC11%E7%AB%A0%EF%BC%9A%E6%9B%B4%E4%B8%8A%E5%B1%82%E6%A5%BC%EF%BC%8C%E5%9F%BA%E4%BA%8E%E8%A7%82%E5%AF%9F%E8%80%85%E5%AE%9E%E7%8E%B0%EF%BC%8C%E5%AE%B9%E5%99%A8%E4%BA%8B%E4%BB%B6%E5%92%8C%E4%BA%8B%E4%BB%B6%E7%9B%91%E5%90%AC%E5%99%A8.html](https://bugstack.cn/md/spring/develop-spring/2021-07-07-%E7%AC%AC11%E7%AB%A0%EF%BC%9A%E6%9B%B4%E4%B8%8A%E5%B1%82%E6%A5%BC%EF%BC%8C%E5%9F%BA%E4%BA%8E%E8%A7%82%E5%AF%9F%E8%80%85%E5%AE%9E%E7%8E%B0%EF%BC%8C%E5%AE%B9%E5%99%A8%E4%BA%8B%E4%BB%B6%E5%92%8C%E4%BA%8B%E4%BB%B6%E7%9B%91%E5%90%AC%E5%99%A8.html)

#### 09：AOP 切面如何拦截方法

- **选项**：
  - A.正则表达式
  - B.切点表达式
  - C.类方法匹配
  - D.Ognl表达式
- **答案**：B
- **解析**：Spring 中 AOP 切面拦截方法使用的是 `execution(* cn.bugstack.springframework.test.bean.IUserService.*(..))` 表达式匹配，它是一种 AspectJExpressionPointcut 提供的切点表达式。
- **详细**：[https://bugstack.cn/md/spring/develop-spring/2021-07-13-%E7%AC%AC12%E7%AB%A0%EF%BC%9A%E7%82%89%E7%81%AB%E7%BA%AF%E9%9D%92%EF%BC%8C%E5%9F%BA%E4%BA%8EJDK%E5%92%8CCglib%E5%8A%A8%E6%80%81%E4%BB%A3%E7%90%86%EF%BC%8C%E5%AE%9E%E7%8E%B0AOP%E6%A0%B8%E5%BF%83%E5%8A%9F%E8%83%BD.html](https://bugstack.cn/md/spring/develop-spring/2021-07-13-%E7%AC%AC12%E7%AB%A0%EF%BC%9A%E7%82%89%E7%81%AB%E7%BA%AF%E9%9D%92%EF%BC%8C%E5%9F%BA%E4%BA%8EJDK%E5%92%8CCglib%E5%8A%A8%E6%80%81%E4%BB%A3%E7%90%86%EF%BC%8C%E5%AE%9E%E7%8E%B0AOP%E6%A0%B8%E5%BF%83%E5%8A%9F%E8%83%BD.html)

#### 10：哪一级存缓存放代理对象

- **选项**：
  - A.singletonObjects
  - B.earlySingletonObjects
  - C.singletonFactories
  - D.earlySingletonFactories
- **答案**：C
- **解析**：Spring 框架在 DefaultSingletonBeanRegistry 类中一共提供了三级缓存来存放不同阶段的对象，包括；普通对象、提前暴漏的对象和代理对象。而代理对象就是存放在 singletonFactories 中。*你看到了吧，它是个Factories，和 FactoryBean 其实意思一样，它们都是工厂对象，不是实际的对象，所以也称为代理对象。*
- **详细**：[https://bugstack.cn/md/spring/develop-spring/2021-08-07-%E7%AC%AC17%E7%AB%A0%EF%BC%9A%E6%94%BB%E6%97%A0%E4%B8%8D%E5%85%8B%EF%BC%8C%E9%80%9A%E8%BF%87%E4%B8%89%E7%BA%A7%E7%BC%93%E5%AD%98%E8%A7%A3%E5%86%B3%E5%BE%AA%E7%8E%AF%E4%BE%9D%E8%B5%96.html](https://bugstack.cn/md/spring/develop-spring/2021-08-07-%E7%AC%AC17%E7%AB%A0%EF%BC%9A%E6%94%BB%E6%97%A0%E4%B8%8D%E5%85%8B%EF%BC%8C%E9%80%9A%E8%BF%87%E4%B8%89%E7%BA%A7%E7%BC%93%E5%AD%98%E8%A7%A3%E5%86%B3%E5%BE%AA%E7%8E%AF%E4%BE%9D%E8%B5%96.html)

### 4. 源码-MyBatis（31~40）

#### 01：MapperProxy 的作用是什么？

- **选项**：
  - A.DAO接口创建映射器代理
  - B.SQL 执行器代理
  - C.会话执行器
  - D.拦截器
- **答案**：A
- **解析**：MapperProxy 是每一个映射器代理类，也就是给每一个 DAO 接口创建出一个代理的映射器，这个映射就是把 DAO 接口和SQL语句、执行操作、结果封装给包装起来，让用户调用 DAO 接口时就能完成对数据库的增删改查操作。
- **详细**：[https://bugstack.cn/md/spring/develop-mybatis/2022-03-27-%E7%AC%AC2%E7%AB%A0%EF%BC%9A%E5%88%9B%E5%BB%BA%E7%AE%80%E5%8D%95%E7%9A%84%E6%98%A0%E5%B0%84%E5%99%A8%E4%BB%A3%E7%90%86%E5%B7%A5%E5%8E%82.html](https://bugstack.cn/md/spring/develop-mybatis/2022-03-27-%E7%AC%AC2%E7%AB%A0%EF%BC%9A%E5%88%9B%E5%BB%BA%E7%AE%80%E5%8D%95%E7%9A%84%E6%98%A0%E5%B0%84%E5%99%A8%E4%BB%A3%E7%90%86%E5%B7%A5%E5%8E%82.html)

#### 02：解析的SQL语句存放到哪里？

- **选项**：
  - A.存放到 SqlSession 会话中
  - B.存放到 Executor 执行器实现类中
  - C.存放到 Configuration 配置类中
  - D.存放到 MapperProxy 映射器代理类中
- **答案**：C
- **解析**：MyBatis 框架不像是 Spring 有维护 Bean 对象的容器，在 MyBatis 中用于贯穿整个会话周期的是 Configuration 配置项，那么类似这样的 SQL 语句、执行器的创建、映射的参数都是保存在 Configuration 配置项中的。
- **详细**：[https://bugstack.cn/md/spring/develop-mybatis/2022-04-09-%E7%AC%AC4%E7%AB%A0%EF%BC%9AXML%E7%9A%84%E8%A7%A3%E6%9E%90%E5%92%8C%E6%B3%A8%E5%86%8C%E4%BD%BF%E7%94%A8.html](https://bugstack.cn/md/spring/develop-mybatis/2022-04-09-%E7%AC%AC4%E7%AB%A0%EF%BC%9AXML%E7%9A%84%E8%A7%A3%E6%9E%90%E5%92%8C%E6%B3%A8%E5%86%8C%E4%BD%BF%E7%94%A8.html)

#### 03：池化数据源的目的是什么？

- **选项**：
  - A.提高连接的可复用性
  - B.有效管理空闲连接
  - C.统一控制资源使用
  - D.包装 JDBC 事务
- **答案**：A、B、C
- **解析**：池化的目的不只是数据源、线程、服务，其核心目的都是提高连接资源的复用性、空闲连接的管理和统一控制资源的使用。
- **详细**：[https://bugstack.cn/md/spring/develop-mybatis/2022-04-17-%E7%AC%AC5%E7%AB%A0%EF%BC%9A%E6%95%B0%E6%8D%AE%E6%BA%90%E7%9A%84%E8%A7%A3%E6%9E%90%E3%80%81%E5%88%9B%E5%BB%BA%E5%92%8C%E4%BD%BF%E7%94%A8.html](https://bugstack.cn/md/spring/develop-mybatis/2022-04-17-%E7%AC%AC5%E7%AB%A0%EF%BC%9A%E6%95%B0%E6%8D%AE%E6%BA%90%E7%9A%84%E8%A7%A3%E6%9E%90%E3%80%81%E5%88%9B%E5%BB%BA%E5%92%8C%E4%BD%BF%E7%94%A8.html)

#### 04：Executor 执行器提供哪些方法

- **选项**：
  - A.query
  - B.commit
  - C.rollback
  - D.close
- **答案**：A、B、C、D
- **解析**：Executor 是 MyBatis SqlSession 会话中的 SQL 执行器，这个类负责包装 SQL 所提供的必备方法。同时这个类只有 update 没有 insert、delete 方法。*所以其实更多的技术要从根本上学习，否则换个角度问其实还是没法回答*
- **详细**：[https://bugstack.cn/md/spring/develop-mybatis/2022-04-28-%E7%AC%AC7%E7%AB%A0%EF%BC%9ASQL%E6%89%A7%E8%A1%8C%E5%99%A8%E7%9A%84%E5%AE%9A%E4%B9%89%E5%92%8C%E5%AE%9E%E7%8E%B0.html](https://bugstack.cn/md/spring/develop-mybatis/2022-04-28-%E7%AC%AC7%E7%AB%A0%EF%BC%9ASQL%E6%89%A7%E8%A1%8C%E5%99%A8%E7%9A%84%E5%AE%9A%E4%B9%89%E5%92%8C%E5%AE%9E%E7%8E%B0.html)

#### 05：MyBatis 框架中值的设置

- **选项**：
  - A.通过 hutool
  - B.通过 lombok
  - C.实现 MetaObject 框架
  - D.硬编码
- **答案**：C
- **解析**：只要是一些中间件的框架开发就会有关于对象中值的设置，但这些值的属性有些时候都是动态提供的，所以不能直接硬编码。那么就需要开发一些反射工具，类似 hutool 也是这样的工具，但在 MyBatis 中是它自己实现的 MetaObject 反射工具包来处理值的设置和获取。
- **详细**：[https://bugstack.cn/md/spring/develop-mybatis/2022-05-03-%E7%AC%AC8%E7%AB%A0%EF%BC%9A%E6%8A%8A%E5%8F%8D%E5%B0%84%E7%94%A8%E5%88%B0%E5%87%BA%E7%A5%9E%E5%85%A5%E5%8C%96.html](https://bugstack.cn/md/spring/develop-mybatis/2022-05-03-%E7%AC%AC8%E7%AB%A0%EF%BC%9A%E6%8A%8A%E5%8F%8D%E5%B0%84%E7%94%A8%E5%88%B0%E5%87%BA%E7%A5%9E%E5%85%A5%E5%8C%96.html)

#### 06：参数的处理和结果集的封装，用到了什么设计模式

- **选项**：
  - A.代理模式
  - B.适配器模式
  - C.策略模式
  - D.责任链模式
- **答案**：C
- **解析**：在 MyBatis 框架中对于 JDBC 的封装处理是不能只是 if···else 判断，这样就过于臃肿了，那么对这些 Long、Integer、String 等，各类属性的获取和设置，就可以使用策略模式来处理。
- **详细**：[https://bugstack.cn/md/spring/develop-mybatis/2022-05-26-%E7%AC%AC10%E7%AB%A0%EF%BC%9A%E4%BD%BF%E7%94%A8%E7%AD%96%E7%95%A5%E6%A8%A1%E5%BC%8F%EF%BC%8C%E8%B0%83%E7%94%A8%E5%8F%82%E6%95%B0%E5%A4%84%E7%90%86%E5%99%A8.html](https://bugstack.cn/md/spring/develop-mybatis/2022-05-26-%E7%AC%AC10%E7%AB%A0%EF%BC%9A%E4%BD%BF%E7%94%A8%E7%AD%96%E7%95%A5%E6%A8%A1%E5%BC%8F%EF%BC%8C%E8%B0%83%E7%94%A8%E5%8F%82%E6%95%B0%E5%A4%84%E7%90%86%E5%99%A8.html)

#### 07：#{}与${}的区别是什么？

- **选项**：
  - A.#{} 是预编译处理，${}是字符串替换。
  - B.#{} 是字符串替换，${}是预编译处理。
  - C.#{} 能防止SQL注入，${}不能防止SQL注入
  - D.#{} 不能防止SQL注入，${}能防止SQL注入
- **答案**：A、C
- **解析**：#{} 是预编译处理，能防止 SQL 注入，${} 是字符串替换，不安全不能防止 SQL 注入
- **详细**：[https://bugstack.cn/md/spring/develop-mybatis/2022-06-02-%E7%AC%AC11%E7%AB%A0%EF%BC%9A%E6%B5%81%E7%A8%8B%E8%A7%A3%E8%80%A6%EF%BC%8C%E5%B0%81%E8%A3%85%E7%BB%93%E6%9E%9C%E9%9B%86%E5%A4%84%E7%90%86%E5%99%A8.html](https://bugstack.cn/md/spring/develop-mybatis/2022-06-02-%E7%AC%AC11%E7%AB%A0%EF%BC%9A%E6%B5%81%E7%A8%8B%E8%A7%A3%E8%80%A6%EF%BC%8C%E5%B0%81%E8%A3%85%E7%BB%93%E6%9E%9C%E9%9B%86%E5%A4%84%E7%90%86%E5%99%A8.html)

#### 08：Insert 返回自增索引

- **选项**：
  - A.配置 SELECT LAST_INSERT_ID()
  - B.一个DB连接的事务下，执行两条 SQL 语句
  - C.自增结果通过return返回
  - D.Jdbc3KeyGenerator 适用于MySql、PostgreSQL
- **答案**：A、B、D
- **解析**：让 Insert 操作能返回自增索引，其实相当于要在一个事务下执行2条 SQL 否则是没法返回的。另外 Jdbc3KeyGenerator：主要用于数据库的自增主键，比如 MySQL、PostgreSQL。SelectKeyGenerator：主要用于数据库不支持自增主键的情况，比如 Oracle、DB2。
- **详细**：[https://bugstack.cn/md/spring/develop-mybatis/2022-06-25-%E7%AC%AC15%E7%AB%A0%EF%BC%9A%E8%BF%94%E5%9B%9EInsert%E6%93%8D%E4%BD%9C%E8%87%AA%E5%A2%9E%E7%B4%A2%E5%BC%95%E5%80%BC.html](https://bugstack.cn/md/spring/develop-mybatis/2022-06-25-%E7%AC%AC15%E7%AB%A0%EF%BC%9A%E8%BF%94%E5%9B%9EInsert%E6%93%8D%E4%BD%9C%E8%87%AA%E5%A2%9E%E7%B4%A2%E5%BC%95%E5%80%BC.html)

#### 09：Plugin 插件功能作用范围

- **选项**：
  - A.ParameterHandler
  - B.ResultSetHandler
  - C.StatementHandler
  - D.Executor
- **答案**：A、B、C、D
- **解析**：MyBatis 框架在 Plugin 插件功能上提供了；ParameterHandler、ResultSetHandler、StatementHandler、Executor 四种范围扩展机制，你可以在插件机制上扩展自身的需求。
- **详细**：[https://bugstack.cn/md/spring/develop-mybatis/2022-07-01-%E7%AC%AC17%E7%AB%A0%EF%BC%9APlugin%E6%8F%92%E4%BB%B6%E5%8A%9F%E8%83%BD%E5%AE%9E%E7%8E%B0.html](https://bugstack.cn/md/spring/develop-mybatis/2022-07-01-%E7%AC%AC17%E7%AB%A0%EF%BC%9APlugin%E6%8F%92%E4%BB%B6%E5%8A%9F%E8%83%BD%E5%AE%9E%E7%8E%B0.html)

#### 10：缓存的作用范围

- **选项**：
  - A.一级缓存作用于 SqlSession
  - B.一级缓存作用于 namespace
  - C.二级缓存作用于 namespace
  - D.二级缓存作用于 All 全局查询
- **答案**：A、C
- **解析**：一级缓存是作用在 SqlSession 会话层面的，一次会话结束(commit、rollback、close)后缓存就结束了。而二级缓存则是作用到 namespace 范围，通过装饰一级缓存，把数据保存到队列中进行使用。那么你知道这是一种什么队列吗？为什么二级缓存并不常用？
- **详细**：[https://bugstack.cn/md/spring/develop-mybatis/2022-07-05-%E7%AC%AC19%E7%AB%A0%EF%BC%9A%E4%BA%8C%E7%BA%A7%E7%BC%93%E5%AD%98.html](https://bugstack.cn/md/spring/develop-mybatis/2022-07-05-%E7%AC%AC19%E7%AB%A0%EF%BC%9A%E4%BA%8C%E7%BA%A7%E7%BC%93%E5%AD%98.html)

### 5. 设计模式（41~50）

#### 01：设计模式分类

- **选项**：
  - A.创建型模式；工厂、建造、适配
  - B.结构型模式；迭代、享元、代理
  - C.行为型模式；观察、模板、命令
- **答案**：C
- **解析**：23种设计模式被分为；创建型模式、结构型模式、行为型模式。适配是结构型模式、迭代是行为型模式。更多参考详细链接。
- **详细**：[https://bugstack.cn/md/develop/design-pattern/2022-03-12-%E9%87%8D%E5%AD%A6Java%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8FB%E7%AB%99%E8%A7%86%E9%A2%91.html](https://bugstack.cn/md/develop/design-pattern/2022-03-12-%E9%87%8D%E5%AD%A6Java%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8FB%E7%AB%99%E8%A7%86%E9%A2%91.html)

#### 02：设计模式原则包括

- **选项**：
  - A.开放封闭原则
  - B.里氏代换原则
  - C.依赖倒转原则
  - D.接口隔离原则
  - E.迪米特法则
  - F.单一职责原则
- **答案**：A、B、C、D、E、F
- **解析**：设计原则包括；开放封闭、里氏替换、依赖倒置、接口隔离、迪米特、单一职责。
- **详细**：[https://bugstack.cn/md/develop/design-pattern/2022-03-12-%E9%87%8D%E5%AD%A6Java%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8FB%E7%AB%99%E8%A7%86%E9%A2%91.html](https://bugstack.cn/md/develop/design-pattern/2022-03-12-%E9%87%8D%E5%AD%A6Java%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8FB%E7%AB%99%E8%A7%86%E9%A2%91.html)

#### 03：多支付渠道与多支付方式，建议使用哪种设计模式

- **选项**：
  - A.组合模式
  - B.装饰器模式
  - C.代理模式
  - D.桥接模式
- **答案**：D
- **解析**：此题更多的是表达两种NxN的链接使用，一般是桥接更为合适。
- **详细**：[https://bugstack.cn/md/develop/design-pattern/2020-06-04-%E9%87%8D%E5%AD%A6%20Java%20%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E3%80%8A%E5%AE%9E%E6%88%98%E6%A1%A5%E6%8E%A5%E6%A8%A1%E5%BC%8F%E3%80%8B.html](https://bugstack.cn/md/develop/design-pattern/2020-06-04-%E9%87%8D%E5%AD%A6%20Java%20%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E3%80%8A%E5%AE%9E%E6%88%98%E6%A1%A5%E6%8E%A5%E6%A8%A1%E5%BC%8F%E3%80%8B.html)

#### 04：MyBatis 二级缓存对一级缓存的上层实现，使用的什么设计模式

- **选项**：
  - A.门面模式
  - B.代理模式
  - C.装饰器模式
  - D.策略模式
- **答案**：C
- **解析**：二级缓存是建立在一级缓存复用基础功能做的实现，把一级缓存中的数据在执行完会话操作后，迁移到二级缓存队列中。所以它使用了一种装饰器模式实现。**new CachingExecutor(new SimpleExecutor(this, transaction));**
- **详细**：[https://bugstack.cn/md/spring/develop-mybatis/2022-07-05-%E7%AC%AC19%E7%AB%A0%EF%BC%9A%E4%BA%8C%E7%BA%A7%E7%BC%93%E5%AD%98.html](https://bugstack.cn/md/spring/develop-mybatis/2022-07-05-%E7%AC%AC19%E7%AB%A0%EF%BC%9A%E4%BA%8C%E7%BA%A7%E7%BC%93%E5%AD%98.html)

#### 05：规则树人群过滤，建议使用什么设计模式实现

- **选项**：
  - A.命令模式
  - B.备忘录模式
  - C.策略模式
  - D.组合模式
- **答案**：D
- **解析**：规则树的特点是可组合，节点可复用，形成一套二叉搜索树的结构。所以它更适合使用组合模式来实现。
- **详细**：[https://bugstack.cn/md/develop/design-pattern/2020-06-08-%E9%87%8D%E5%AD%A6%20Java%20%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E3%80%8A%E5%AE%9E%E6%88%98%E7%BB%84%E5%90%88%E6%A8%A1%E5%BC%8F%E3%80%8B.html](https://bugstack.cn/md/develop/design-pattern/2020-06-08-%E9%87%8D%E5%AD%A6%20Java%20%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E3%80%8A%E5%AE%9E%E6%88%98%E7%BB%84%E5%90%88%E6%A8%A1%E5%BC%8F%E3%80%8B.html)

#### 06：不太常用的设计模式组合

- **选项**：
  - A.模板 + 策略 + 工厂
  - B.组合 + 策略 + 建造
  - C.中介 + 备忘 + 访问
  - D.工厂 + 装饰 + 模板
- **答案**：C
- **解析**：一般情况下常用的模式组合，主要为三类中的交叉组合，比如；创建型、结构型、行为型。但因为中介、备忘、访问，都是行为模式，所以通常比较少的被组合使用起来。
- **详细**：[https://bugstack.cn/md/develop/design-pattern/2020-06-27-%E9%87%8D%E5%AD%A6%20Java%20%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E3%80%8A%E5%AE%9E%E6%88%98%E4%B8%AD%E4%BB%8B%E8%80%85%E6%A8%A1%E5%BC%8F%E3%80%8B.html](https://bugstack.cn/md/develop/design-pattern/2020-06-27-%E9%87%8D%E5%AD%A6%20Java%20%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E3%80%8A%E5%AE%9E%E6%88%98%E4%B8%AD%E4%BB%8B%E8%80%85%E6%A8%A1%E5%BC%8F%E3%80%8B.html)

#### 07：模板模式的重点

- **选项**：
  - A.包装服务，对外提供接口
  - B.替代if···else判断流程
  - C.规定一套统一的标准流程
  - D.存放以及可恢复配置信息
- **答案**：C
- **解析**：模板模式的核心实在在于提供一个抽象类定义出标准的流程，和要调用方法的顺序，以及提供出抽象方法给子类实现。这在各类框架中也是非常常用的模式，例如 Spring、MyBatis 以及业务需求类似抽奖活动，定义抽奖流程；风控、规则、库存、抽奖、消息、发货等。
- **详细**：[https://bugstack.cn/md/project/lottery/Part-2/%E7%AC%AC06%E8%8A%82%EF%BC%9A%E6%A8%A1%E6%9D%BF%E6%A8%A1%E5%BC%8F%E5%A4%84%E7%90%86%E6%8A%BD%E5%A5%96%E6%B5%81%E7%A8%8B.html](https://bugstack.cn/md/project/lottery/Part-2/%E7%AC%AC06%E8%8A%82%EF%BC%9A%E6%A8%A1%E6%9D%BF%E6%A8%A1%E5%BC%8F%E5%A4%84%E7%90%86%E6%8A%BD%E5%A5%96%E6%B5%81%E7%A8%8B.html)

#### 08：建立起 MyBatis 和 Spring 的连接

- **选项**：
  - A.实现 BeanFactory
  - B.实现 FactoryBean
  - C.实现 BeanPostProcessor
  - D.实现 BeanFactoryPostProcessor
- **答案**：B
- **解析**：建立起连接的最大关键，在于让 Spring 管理 MyBatis 的映射器代理对象。也就是通过 FactoryBean 包装一下 DAO 接口代理类的获取。
- **详细**：[https://bugstack.cn/md/spring/develop-mybatis/2022-07-06-%E7%AC%AC20%E7%AB%A0%EF%BC%9A%E6%95%B4%E5%90%88Spring.html](https://bugstack.cn/md/spring/develop-mybatis/2022-07-06-%E7%AC%AC20%E7%AB%A0%EF%BC%9A%E6%95%B4%E5%90%88Spring.html)

#### 09：各类优惠券；直减、满减、免息等包装使用，需要什么设计模式

- **选项**：
  - A.观察者模式
  - B.策略模式
  - C.组合模式
  - D.备忘录模式
- **答案**：B
- **解析**：对于拥有同类共性的物料，但有差异化的使用时，通常都是使用策略模式进行封装。这在提到 MyBatis 中封装参数使用策略模式是一样的。
- **详细**：[https://bugstack.cn/md/develop/design-pattern/2020-07-05-%E9%87%8D%E5%AD%A6%20Java%20%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E3%80%8A%E5%AE%9E%E6%88%98%E7%AD%96%E7%95%A5%E6%A8%A1%E5%BC%8F%E3%80%8B.html](https://bugstack.cn/md/develop/design-pattern/2020-07-05-%E9%87%8D%E5%AD%A6%20Java%20%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E3%80%8A%E5%AE%9E%E6%88%98%E7%AD%96%E7%95%A5%E6%A8%A1%E5%BC%8F%E3%80%8B.html)

#### 10：单例模式实现方式包括

- **选项**：
  - A.懒汉模式
  - B.饿汉模式
  - C.类的内部类
  - D.双重校验锁
  - E.枚举
  - F.compareAndSet
- **答案**：A、B、C、D、E、F
- **解析**：这六种方式都是创建单例的方式，也是基础面试中常考的题。
- **详细**：[https://bugstack.cn/md/develop/design-pattern/2020-05-31-%E9%87%8D%E5%AD%A6%20Java%20%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E3%80%8A%E5%AE%9E%E6%88%98%E5%8D%95%E4%BE%8B%E6%A8%A1%E5%BC%8F%E3%80%8B.html](https://bugstack.cn/md/develop/design-pattern/2020-05-31-%E9%87%8D%E5%AD%A6%20Java%20%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E3%80%8A%E5%AE%9E%E6%88%98%E5%8D%95%E4%BE%8B%E6%A8%A1%E5%BC%8F%E3%80%8B.html)

### 6. 系统架构（51~60）

#### 01：创建工程框架包括

- **选项**：
  - A.MVC
  - B.DDD
  - C.六边形
  - D.整洁架构
  - E.SOA架构
- **答案**：A、B、C、D、E
- **解析**：MVC、DDD、六边形、整洁架构、SOA架构，都是创建工程搭建的方式
- **详细**：[https://bugstack.cn/md/develop/framework/frame/2019-12-22-%E6%9E%B6%E6%9E%84%E6%A1%86%E6%9E%B6%E6%90%AD%E5%BB%BA%E4%B8%80%E3%80%8A%E5%8D%95%E4%BD%93%E5%BA%94%E7%94%A8%E6%9C%8D%E5%8A%A1%E4%B9%8BSSM%E6%95%B4%E5%90%88%EF%BC%9ASpring4%20+%20SpringMvc%20+%20Mybatis%E3%80%8B.html](https://bugstack.cn/md/develop/framework/frame/2019-12-22-%E6%9E%B6%E6%9E%84%E6%A1%86%E6%9E%B6%E6%90%AD%E5%BB%BA%E4%B8%80%E3%80%8A%E5%8D%95%E4%BD%93%E5%BA%94%E7%94%A8%E6%9C%8D%E5%8A%A1%E4%B9%8BSSM%E6%95%B4%E5%90%88%EF%BC%9ASpring4%20+%20SpringMvc%20+%20Mybatis%E3%80%8B.html)

#### 02：引入Dubbo到框架中，为什么需要单独分出RPC的模块层

- **选项**：
  - A.拆分出来方便维护
  - B.并没有太大意义，也可以不拆分
  - C.对外提供的RPC调用，需要接口描述
  - D.单独拆分避免循环依赖
- **答案**：C
- **解析**：RPC 服务的调用方式有2种，一种是需要接口信息的编码方式，另外一种是泛化调用。而工程中使用 Dubbo 框架，对外提供服务的话，通常是需要对应提供一个对应的接口描述信息的，这样外部才能引用这个 Jar 包并做代理操作进行接口调用。所以需要单独拆分出 RPC 模块层用于打包。
- **详细**：[https://bugstack.cn/md/project/lottery/Part-2/%E7%AC%AC03%E8%8A%82%EF%BC%9A%E8%B7%91%E9%80%9A%E5%B9%BF%E6%92%AD%E6%A8%A1%E5%BC%8FRPC%E8%BF%87%E7%A8%8B%E8%B0%83%E7%94%A8.html](https://bugstack.cn/md/project/lottery/Part-2/%E7%AC%AC03%E8%8A%82%EF%BC%9A%E8%B7%91%E9%80%9A%E5%B9%BF%E6%92%AD%E6%A8%A1%E5%BC%8FRPC%E8%BF%87%E7%A8%8B%E8%B0%83%E7%94%A8.html)

#### 03：分布式框架技术栈包括

- **选项**：
  - A.Spring
  - B.RPC
  - C.MQ
  - D.分库分表
  - E.分布式任务
- **答案**：B、C、D、E
- **解析**：分布式框架需要解决应用的分布式部署下，数据的通信和使用。所以要有 RPC、MQ、分库分表、分布式任务来处理。
- **详细**：[https://bugstack.cn/md/project/lottery/Part-1/%E7%AC%AC03%E8%8A%82%EF%BC%9A%E7%B3%BB%E7%BB%9F%E6%9E%B6%E6%9E%84%E8%AE%BE%E8%AE%A1.html](https://bugstack.cn/md/project/lottery/Part-1/%E7%AC%AC03%E8%8A%82%EF%BC%9A%E7%B3%BB%E7%BB%9F%E6%9E%B6%E6%9E%84%E8%AE%BE%E8%AE%A1.html)

#### 04：DDD领域服务包括什么

- **选项**：
  - A.service 服务实现
  - B.repository 仓储接口
  - C.aggregates 聚合对象
  - D.dao 数据服务
- **答案**：A、B、C
- **解析**：在 DDD 领域服务的实现中，DAO 是被分配到仓储服务中，而 DDD 领域服务是定义一个仓储接口。其实这里还涉及到了工程的搭建和循环依赖。
- **详细**：[https://bugstack.cn/md/project/lottery/Part-2/%E7%AC%AC02%E8%8A%82%EF%BC%9A%E6%90%AD%E5%BB%BADDD%E5%9B%9B%E5%B1%82%E6%9E%B6%E6%9E%84.html](https://bugstack.cn/md/project/lottery/Part-2/%E7%AC%AC02%E8%8A%82%EF%BC%9A%E6%90%AD%E5%BB%BADDD%E5%9B%9B%E5%B1%82%E6%9E%B6%E6%9E%84.html)

#### 05：低代码能解决什么场景问题

- **选项**：
  - A.所有CRUD的代码都可以解决
  - B.各类领域服务的实现
  - C.部分通用共性的接口串联编排类场景
  - D.产品和运营可以直接替代研发直接上线需求
- **答案**：C
- **解析**：其实低代码并不能解决所有问题，甚至哪怕是 CRUD 更多的也需要研发来编写。而低代码的核心处理场景是解决部分通用性的接口串联编排。
- **详细**：[https://bugstack.cn/md/develop/framework/scheme/2021-02-21-%E5%85%B3%E4%BA%8E%E4%BD%8E%E4%BB%A3%E7%A0%81%E7%BC%96%E7%A8%8B%E7%9A%84%E5%8F%AF%E6%8C%81%E7%BB%AD%E6%80%A7%E4%BA%A4%E4%BB%98%E8%AE%BE%E8%AE%A1%E5%92%8C%E5%88%86%E6%9E%90.html](https://bugstack.cn/md/develop/framework/scheme/2021-02-21-%E5%85%B3%E4%BA%8E%E4%BD%8E%E4%BB%A3%E7%A0%81%E7%BC%96%E7%A8%8B%E7%9A%84%E5%8F%AF%E6%8C%81%E7%BB%AD%E6%80%A7%E4%BA%A4%E4%BB%98%E8%AE%BE%E8%AE%A1%E5%92%8C%E5%88%86%E6%9E%90.html)

#### 06：Zachman框架的六个观点

- **选项**：
  - A.数据
  - B.功能
  - C.网络
  - D.系统
  - E.时间
  - F.原因
  - G.人
- **答案**：A、B、C、E、F、G
- **解析**：Zachman框架，由约翰 扎科曼（John Zachman ）在1987年创立的全球第一个企业架构理论，其论文《信息系统架构框架》至今仍被业界认为是企业架构设计方面最权威的理论。读者可以阅读详细内容扩展学习。
- **详细**：[https://bugstack.cn/md/develop/framework/scheme/2021-02-28-%E5%B7%A5%E4%BD%9C%E4%B8%A4%E4%B8%89%E5%B9%B4%EF%BC%8C%E6%95%B4%E4%B8%8D%E6%98%8E%E7%99%BD%E6%9E%B6%E6%9E%84%E5%9B%BE%E9%83%BD%E7%94%BB%E5%95%A5%EF%BC%9F.html](https://bugstack.cn/md/develop/framework/scheme/2021-02-28-%E5%B7%A5%E4%BD%9C%E4%B8%A4%E4%B8%89%E5%B9%B4%EF%BC%8C%E6%95%B4%E4%B8%8D%E6%98%8E%E7%99%BD%E6%9E%B6%E6%9E%84%E5%9B%BE%E9%83%BD%E7%94%BB%E5%95%A5%EF%BC%9F.html)

#### 07：中台设计通常分为

- **选项**：
  - A.业务中台
  - B.技术中台
  - C.数据中台
  - D.运维中台
- **答案**：A、B、C
- **解析**：中台主要包括；业务中台、技术中台、数据中台，但运维不算一个中台，它是公司底层支撑的大基建，无论公司哪个部门的中台，都不能把运维拿出来造一份轮子。PS：虽然中台的概念已经不那么火，甚至很多都已经拆中台了，但不是中台的概率不好，只是不同场景的公司需要适合自己的架构方案。阅读详细你会知道中台最早是从一个游戏公司来的。
- **详细**：[https://bugstack.cn/md/develop/framework/scheme/2021-03-24-%E5%88%9A%E7%81%AB%E4%BA%86%E7%9A%84%E4%B8%AD%E5%8F%B0%E8%BD%AC%E5%A4%B4%E5%B0%B1%E6%8B%86%EF%BC%8C%E4%B8%80%E5%A4%A7%E6%B3%A2%E5%85%AC%E5%8F%B8%E6%94%BE%E4%B8%8D%E4%B8%8B%E5%8F%88%E6%8B%BF%E4%B8%8D%E8%B5%B7%E6%9D%A5%EF%BC%81.html](https://bugstack.cn/md/develop/framework/scheme/2021-03-24-%E5%88%9A%E7%81%AB%E4%BA%86%E7%9A%84%E4%B8%AD%E5%8F%B0%E8%BD%AC%E5%A4%B4%E5%B0%B1%E6%8B%86%EF%BC%8C%E4%B8%80%E5%A4%A7%E6%B3%A2%E5%85%AC%E5%8F%B8%E6%94%BE%E4%B8%8D%E4%B8%8B%E5%8F%88%E6%8B%BF%E4%B8%8D%E8%B5%B7%E6%9D%A5%EF%BC%81.html)

#### 08：中台的实现难度主要包括

- **选项**：
  - A.新需求响应难度增加
  - B.服务集成复杂度增加
  - C.可复用实现难度增加
  - D.工程师语言难度增加
- **答案**：A、B、C
- **解析**：中台在实践中主要的问题体现在；响应需求的难度、服务的集成复杂度和复用性变得困难。这是因为一个需求的实现要夸多个部门的多个系统实现，所有沟通成本也会增加。
- **详细**：[https://bugstack.cn/md/develop/framework/scheme/2021-03-24-%E5%88%9A%E7%81%AB%E4%BA%86%E7%9A%84%E4%B8%AD%E5%8F%B0%E8%BD%AC%E5%A4%B4%E5%B0%B1%E6%8B%86%EF%BC%8C%E4%B8%80%E5%A4%A7%E6%B3%A2%E5%85%AC%E5%8F%B8%E6%94%BE%E4%B8%8D%E4%B8%8B%E5%8F%88%E6%8B%BF%E4%B8%8D%E8%B5%B7%E6%9D%A5%EF%BC%81.html#%E5%9B%9B%E3%80%81%E5%88%9A%E5%BB%BA%E5%A5%BD%E5%8F%88%E8%A6%81%E6%8B%86](https://bugstack.cn/md/develop/framework/scheme/2021-03-24-%E5%88%9A%E7%81%AB%E4%BA%86%E7%9A%84%E4%B8%AD%E5%8F%B0%E8%BD%AC%E5%A4%B4%E5%B0%B1%E6%8B%86%EF%BC%8C%E4%B8%80%E5%A4%A7%E6%B3%A2%E5%85%AC%E5%8F%B8%E6%94%BE%E4%B8%8D%E4%B8%8B%E5%8F%88%E6%8B%BF%E4%B8%8D%E8%B5%B7%E6%9D%A5%EF%BC%81.html#%E5%9B%9B%E3%80%81%E5%88%9A%E5%BB%BA%E5%A5%BD%E5%8F%88%E8%A6%81%E6%8B%86)

#### 09：非入侵的系统监控设计需要哪些技术栈

- **选项**：
  - A.Javaagent
  - B.字节码框架
  - C.TTL
  - D.JDK
- **答案**：A、B、C
- **解析**：非入侵的全链路监控系统，最早是由谷歌的 Dapper 论文而来，在设计实现上主要包括的技术栈为；Javaagent、字节码框架(ASM\Javassist\TTL-链路打标)。
- **详细**：[https://bugstack.cn/md/develop/framework/scheme/2021-07-19-%E8%B0%83%E7%A0%94%E5%AD%97%E8%8A%82%E7%A0%81%E6%8F%92%E6%A1%A9%E6%8A%80%E6%9C%AF%EF%BC%8C%E7%94%A8%E4%BA%8E%E7%B3%BB%E7%BB%9F%E7%9B%91%E6%8E%A7%E8%AE%BE%E8%AE%A1%E5%92%8C%E5%AE%9E%E7%8E%B0.html](https://bugstack.cn/md/develop/framework/scheme/2021-07-19-%E8%B0%83%E7%A0%94%E5%AD%97%E8%8A%82%E7%A0%81%E6%8F%92%E6%A1%A9%E6%8A%80%E6%9C%AF%EF%BC%8C%E7%94%A8%E4%BA%8E%E7%B3%BB%E7%BB%9F%E7%9B%91%E6%8E%A7%E8%AE%BE%E8%AE%A1%E5%92%8C%E5%AE%9E%E7%8E%B0.html)

#### 10：软件设计原则康威定律包括

- **选项**：
  - A.组织沟通方式会通过系统设计表达出来
  - B.时间再多一件事情也不可能做的完美，但总有时间做完一件事情
  - C.线型系统和线型组织架构间有潜在的异质同态特性
  - D.大的系统组织总是比小系统更倾向于分解
- **答案**：A、B、C、D
- **解析**：康威定律 (康威法则 , Conway's Law) 是马尔文·康威1967年提出的："设计系统的架构受制于产生这些设计的组织的沟通结构。"
- **详细**：[https://zh.m.wikipedia.org/zh-hans/%E5%BA%B7%E5%A8%81%E5%AE%9A%E5%BE%8B](https://zh.m.wikipedia.org/zh-hans/%E5%BA%B7%E5%A8%81%E5%AE%9A%E5%BE%8B)

### 7. 中间件（61~70）

#### 01：中间件分为哪些类

- **选项**：
  - A.终端仿真/屏幕转换中间件
  - B.数据访问中间件
  - C.远程过程调用中间件
  - D.消息中间件
  - E.交易中间件
  - F.对象中间件
- **答案**：A、B、C、D、E、F
- **解析**：中间件的分类有很多中，这可能也是让大家在平常的开发中迷惑，到底哪些是中间件。一些基本的中间件定义很好定位，比如MQ、RPC、Dapper等等，但有些软件服务虽然不是作为中间件开发出来的，但它们如果符合中间件的定义，也可以归纳到中间的范畴里。
- **详细**：[https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%201%20%E7%AB%A0%20%E4%BB%80%E4%B9%88%E6%98%AF%E4%B8%AD%E9%97%B4%E4%BB%B6.html](https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%201%20%E7%AB%A0%20%E4%BB%80%E4%B9%88%E6%98%AF%E4%B8%AD%E9%97%B4%E4%BB%B6.html)

#### 02：非业务逻辑的共性服务功能

- **选项**：
  - A.白名单&黑名单
  - B.熔断
  - C.降级
  - D.限流
  - E.切量
  - F.A/BTest
- **答案**：A、B、C、D、E、F
- **解析**：通常在软件开发中，会把一些通用的共性功能提炼出来，作为共用的组件使用。这些组件可以嵌入到网关中。
- **详细**：[https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%203%20%E7%AB%A0%20%E6%9C%8D%E5%8A%A1%E6%B2%BB%E7%90%86%EF%BC%8C%E7%BB%9F%E4%B8%80%E7%99%BD%E5%90%8D%E5%8D%95%E6%8E%A7%E5%88%B6.html](https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%203%20%E7%AB%A0%20%E6%9C%8D%E5%8A%A1%E6%B2%BB%E7%90%86%EF%BC%8C%E7%BB%9F%E4%B8%80%E7%99%BD%E5%90%8D%E5%8D%95%E6%8E%A7%E5%88%B6.html)

#### 03：SpringBoot Starter 如何加载自定义配置（resources/META-INF/spring.factories）

- **选项**：
  - A.AutoConfigurations
  - B.EnableAutoConfiguration
  - C.ImportAutoConfiguration
  - D.AutoConfigurationPackage
- **答案**：B
- **解析**：SpringBoot Starter 的开发是基于 SPI 机制进行扩展，使用 EnableAutoConfiguration 加载配置。如：`org.springframework.boot.autoconfigure.EnableAutoConfiguration=cn.bugstack.middleware.whitelist.config.WhiteListAutoConfigure`
- **详细**：[https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%203%20%E7%AB%A0%20%E6%9C%8D%E5%8A%A1%E6%B2%BB%E7%90%86%EF%BC%8C%E7%BB%9F%E4%B8%80%E7%99%BD%E5%90%8D%E5%8D%95%E6%8E%A7%E5%88%B6.html](https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%203%20%E7%AB%A0%20%E6%9C%8D%E5%8A%A1%E6%B2%BB%E7%90%86%EF%BC%8C%E7%BB%9F%E4%B8%80%E7%99%BD%E5%90%8D%E5%8D%95%E6%8E%A7%E5%88%B6.html)

#### 04：ES 查询方式

- **选项**：
  - A.x-pack-sql-jdbc
  - B.spring-data-elasticsearch
  - C.elasticsearch-rest-high-level-client
  - D.以上全部
- **答案**：D
- **解析**：除了大家很常用的 spring-data-elasticsearch、elasticsearch-rest-high-level-client，以外 x-pack-sql-jdbc 也是一种查询 ES 的操作方式，它可以让你像使用数据库一样使用 ES。x-pack-sql-jdbc，在 7.x 版本中已经集成在 Elasticsearch 的代码中。
- **详细**：[https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%2010%20%E7%AB%A0%20ES-JDBC%20%E6%9F%A5%E8%AF%A2%E5%BC%95%E6%93%8E.html](https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%2010%20%E7%AB%A0%20ES-JDBC%20%E6%9F%A5%E8%AF%A2%E5%BC%95%E6%93%8E.html)

#### 05：Dubbo 通信方式

- **选项**：
  - A.HTTP
  - B.Webservice
  - C.WebFlux
  - D.Netty
- **答案**：D
- **解析**：Dubbo 实现的  RPC 框架，使用的是 Netty 作为 Socket 通信框架。
- **详细**：[https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%2012%20%E7%AB%A0%20RPC%20%E6%A1%86%E6%9E%B6%E5%AE%9E%E7%8E%B0.html](https://bugstack.cn/md/assembly/middleware/%E7%AC%AC%2012%20%E7%AB%A0%20RPC%20%E6%A1%86%E6%9E%B6%E5%AE%9E%E7%8E%B0.html)

#### 06：数据库路由分库分表散列算法

- **选项**：
  - A.哈希散列
  - B.斐波那契散列
  - C.平方散列
  - D.跳房子散列
- **答案**：A
- **解析**：数据库和表的数据结构近似于HashMap的拉链结构，所以在设计实现上也基本是选择了哈希散列的方式处理元素的索引。
- **详细**：[https://bugstack.cn/md/assembly/middleware/2021-08-19-%E5%9F%BA%E4%BA%8EHash%E6%95%A3%E5%88%97%EF%BC%8C%E6%95%B0%E6%8D%AE%E5%BA%93%E8%B7%AF%E7%94%B1%E7%BB%84%E4%BB%B6%E8%AE%BE%E8%AE%A1.html](https://bugstack.cn/md/assembly/middleware/2021-08-19-%E5%9F%BA%E4%BA%8EHash%E6%95%A3%E5%88%97%EF%BC%8C%E6%95%B0%E6%8D%AE%E5%BA%93%E8%B7%AF%E7%94%B1%E7%BB%84%E4%BB%B6%E8%AE%BE%E8%AE%A1.html)

#### 07：分布式任务调度实现技术

- **选项**：
  - A.扩展Quartz的任务执行能力，开发 SDK 组件
  - B.将任务服务引入 SDK 并 push 到注册中心
  - C.通过控制台统一管理注册中心任务进行下发执行
  - D.以上全部
- **答案**：D
- **解析**：这些选项的合并其实就是一个把 Quartz 扩展为分布式任务的核心流程，具体设计可以参考详细文档；
- **详细**：[https://bugstack.cn/md/assembly/middleware/2019-12-08-%E5%BC%80%E5%8F%91%E5%9F%BA%E4%BA%8ESpringBoot%E7%9A%84%E5%88%86%E5%B8%83%E5%BC%8F%E4%BB%BB%E5%8A%A1%E4%B8%AD%E9%97%B4%E4%BB%B6DcsSchedule.html](https://bugstack.cn/md/assembly/middleware/2019-12-08-%E5%BC%80%E5%8F%91%E5%9F%BA%E4%BA%8ESpringBoot%E7%9A%84%E5%88%86%E5%B8%83%E5%BC%8F%E4%BB%BB%E5%8A%A1%E4%B8%AD%E9%97%B4%E4%BB%B6DcsSchedule.html)

#### 08：字节码增强的框架

- **选项**：
  - A.ASM
  - B.Byte-Buddy
  - C.Javassist
  - D.Cglib
- **答案**：A、B、C
- **解析**：字节码增强框架主要流行的有三个；ASM、Byte-Buddy、Javassist，但大哥是 ASM 基本所有其他的字节码操作也都是基于 ASM 实现。例如 Cglib 但它是工具，到不能算是字节码增强框架。
- **详细**：[https://bugstack.cn/md/bytecode/asm/2020-03-25-%5BASM%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B%5D%E5%A6%82%E6%9E%9C%E4%BD%A0%E5%8F%AA%E5%86%99CRUD%EF%BC%8C%E9%82%A3%E8%BF%99%E7%A7%8D%E6%8A%80%E6%9C%AF%E4%BD%A0%E6%B0%B8%E8%BF%9C%E7%A2%B0%E4%B8%8D%E5%88%B0.html](https://bugstack.cn/md/bytecode/asm/2020-03-25-%5BASM%E5%AD%97%E8%8A%82%E7%A0%81%E7%BC%96%E7%A8%8B%5D%E5%A6%82%E6%9E%9C%E4%BD%A0%E5%8F%AA%E5%86%99CRUD%EF%BC%8C%E9%82%A3%E8%BF%99%E7%A7%8D%E6%8A%80%E6%9C%AF%E4%BD%A0%E6%B0%B8%E8%BF%9C%E7%A2%B0%E4%B8%8D%E5%88%B0.html)

#### 09：RPC-Dubbo 泛化调用的使用场景

- **选项**：
  - A.API网关服务
  - B.集成测试平台
  - C.低代码平台
  - D.以上都是
- **答案**：D
- **解析**：在一些框架和组件的设计实现中，如果有需要对 RPC 进行配置和调用，那么是没法硬编码的，因为这些接口都是通过配置动态读取的，那么这个时候你就需要使用泛化调用了。所以像；API网关、集成测试平台、低代码平台等，都是需要泛化调用的。
- **详细**：[https://bugstack.cn/md/assembly/api-gateway/2022-08-20-%E7%AC%AC2%E7%AB%A0%EF%BC%9A%E4%BB%A3%E7%90%86RPC%E6%B3%9B%E5%8C%96%E8%B0%83%E7%94%A8.html](https://bugstack.cn/md/assembly/api-gateway/2022-08-20-%E7%AC%AC2%E7%AB%A0%EF%BC%9A%E4%BB%A3%E7%90%86RPC%E6%B3%9B%E5%8C%96%E8%B0%83%E7%94%A8.html)

#### 10：IEDA Plugin 开发完成后如何提交到市场

- **选项**：
  - A.发送邮件给 IDEA 官网运营
  - B.在 Github 构建 Release
  - C.通过网站 plugins.jetbrains.com
  - D.本地 IDEA 开发工具构建
- **答案**：C
- **解析**：这个题有点偏门，基本是需要了解这方面内容才能回答。也算是对架构师的技术栈知晓范围的考核，看是否可以在某些问题场景给出不同的解决访问。那么像 IDEA 插件的发布则需要在 plugins.jetbrains.com 官网进行发布。
- **详细**：[https://bugstack.cn/md/assembly/idea-plugin/2021-08-29-%E6%8A%80%E6%9C%AF%E5%AE%9E%E8%B7%B5%EF%BC%8CIDEA%20%E6%8F%92%E4%BB%B6%E6%80%8E%E4%B9%88%E5%8F%91%E5%B8%83%EF%BC%9F.html](https://bugstack.cn/md/assembly/idea-plugin/2021-08-29-%E6%8A%80%E6%9C%AF%E5%AE%9E%E8%B7%B5%EF%BC%8CIDEA%20%E6%8F%92%E4%BB%B6%E6%80%8E%E4%B9%88%E5%8F%91%E5%B8%83%EF%BC%9F.html)

### 8. 网络通信（71~80）

#### 01：NIO 通信模式

- **选项**：
  - A.同步阻塞I/O模式
  - B.同步非阻塞模式
  - C.异步非阻塞I/O模型
  - D.以上都不是
- **答案**：B
- **解析**：`Java BIO[Blocking I/O]` 同步阻塞I/O模式、`Java NIO[New I/O]` 同步非阻塞模式、`Java AIO[Asynchronous I/O]` 异步非阻塞I/O模型
- **详细**：[https://bugstack.cn/md/netty/base/2019-07-30-netty%E6%A1%88%E4%BE%8B%EF%BC%8Cnetty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E9%9B%B6%E3%80%8A%E5%88%9D%E5%85%A5JavaIO%E4%B9%8B%E9%97%A8BIO%E3%80%81NIO%E3%80%81AIO%E5%AE%9E%E6%88%98%E7%BB%83%E4%B9%A0%E3%80%8B.html](https://bugstack.cn/md/netty/base/2019-07-30-netty%E6%A1%88%E4%BE%8B%EF%BC%8Cnetty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E9%9B%B6%E3%80%8A%E5%88%9D%E5%85%A5JavaIO%E4%B9%8B%E9%97%A8BIO%E3%80%81NIO%E3%80%81AIO%E5%AE%9E%E6%88%98%E7%BB%83%E4%B9%A0%E3%80%8B.html)

#### 02：Netty 的优势有哪些？

- **选项**：
  - A.使用简单：封装了 NIO 的很多细节，使用更简单。
  - B.功能强大：预置了多种编解码功能，支持多种主流协议。
  - C.性能高：通过与其他业界主流的 NIO 框架对比，Netty 的综合性能最优。
  - D.稳定性好：Netty 修复了已经发现的所有 NIO 的 bug，让开发人员可以专注于业务本身。
- **答案**：A、B、C、D
- **解析**：Netty 官网描述其特点；使用方便、效率高、安全的介绍。这也是大家都喜欢使用 Netty 的原因。
- **详细**：[https://netty.io/](https://netty.io/)

#### 03：Netty 的应用场景有哪些？

- **选项**：
  - A.Dubbo
  - B.MQ
  - C.API网关
  - D.以上都是
- **答案**：D
- **解析**：在各类框架中涉及到通信的时候，都会考虑使用 Netty 作为通信框架使用，包括；Dubbo、MQ、Api网关等。
- **详细**：[https://bugstack.cn/md/assembly/api-gateway/2022-08-12-%E5%BC%80%E7%AF%87%EF%BC%9A%E5%A6%82%E6%9E%9C%E8%AE%A9%E6%88%91%E8%AE%BE%E8%AE%A1%E4%B8%80%E5%A5%97%EF%BC%8CTPS%E7%99%BE%E4%B8%87%E7%BA%A7API%E7%BD%91%E5%85%B3.html](https://bugstack.cn/md/assembly/api-gateway/2022-08-12-%E5%BC%80%E7%AF%87%EF%BC%9A%E5%A6%82%E6%9E%9C%E8%AE%A9%E6%88%91%E8%AE%BE%E8%AE%A1%E4%B8%80%E5%A5%97%EF%BC%8CTPS%E7%99%BE%E4%B8%87%E7%BA%A7API%E7%BD%91%E5%85%B3.html)

#### 04：Netty 高性能表现在哪些方面？

- **选项**：
  - A.多线程Reactor反应器模式
  - B.内存零拷贝
  - C.内存池设计
  - D.对象池设计
- **答案**：A、B、C、D
- **解析**：Netty 的设计表现为；更高的吞吐量，更低的延迟、更少的资源消耗、最小化不必要的内存拷贝。
- **详细**：[https://netty.io/](https://netty.io/)

#### 05：Netty 和 Tomcat 的区别？

- **选项**：
  - A.Tomcat 是 Web 容器，Netty 是套接字服务
  - B.Tomcat 是 HTTP服务，Netty 可以实现多种服务(HTTP、FTP、UDP、RPC)
  - C.Tomcat 性能低于 Netty
  - D.Netty 更适合结合其他框架使用
- **答案**：A、B、D
- **解析**：Netty 和 Tomcat 的主要区别主要体现在协议和作用的不同，但不能抛开场景说某个性能就高。
- **详细**：[https://stackoverflow.com/questions/56794263/spring-webflux-differrences-when-netty-vs-tomcat-is-used-under-the-hood](https://stackoverflow.com/questions/56794263/spring-webflux-differrences-when-netty-vs-tomcat-is-used-under-the-hood)

#### 06：半包粘包协议的使用

- **选项**：
  - A.LineBasedFrameDecoder 基于换行符
  - B.ObjDecoder 对象传输处理
  - C.ByteToMessageDecoder 扩展自定义传输协议
  - D.以上都是
- **答案**：D
- **解析**：Netty 本身就提供了很多关于传输协议来处理半包粘包，同时也提供了自定义扩展类，你可以基于这些类扩展自己的业务场景中的协议信息。
- **详细**：[https://bugstack.cn/md/netty/base/2019-08-11-netty%E6%A1%88%E4%BE%8B%EF%BC%8Cnetty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E5%85%AB%E3%80%8ANettyClient%E5%8D%8A%E5%8C%85%E7%B2%98%E5%8C%85%E5%A4%84%E7%90%86%E3%80%81%E7%BC%96%E7%A0%81%E8%A7%A3%E7%A0%81%E5%A4%84%E7%90%86%E3%80%81%E6%94%B6%E5%8F%91%E6%95%B0%E6%8D%AE%E6%96%B9%E5%BC%8F%E3%80%8B.html](https://bugstack.cn/md/netty/base/2019-08-11-netty%E6%A1%88%E4%BE%8B%EF%BC%8Cnetty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E5%85%AB%E3%80%8ANettyClient%E5%8D%8A%E5%8C%85%E7%B2%98%E5%8C%85%E5%A4%84%E7%90%86%E3%80%81%E7%BC%96%E7%A0%81%E8%A7%A3%E7%A0%81%E5%A4%84%E7%90%86%E3%80%81%E6%94%B6%E5%8F%91%E6%95%B0%E6%8D%AE%E6%96%B9%E5%BC%8F%E3%80%8B.html)

#### 07：Netty 流量整形处理类

- **选项**：
  - A.ChannelTrafficShapingHandler
  - B.SimpleChannelInboundHandler
  - C.ChannelInboundHandlerAdapter
  - D.以上都不是
- **答案**：A
- **解析**：流量整形（Traffic Shaping）是一种主动调整流量输出速率的措施。一个典型应用是基于下游网络结点的TP指标来控制本地流量的输出。Netty 提供了 GlobalTrafficShapingHandler、ChannelTrafficShapingHandler、 GlobalChannelTrafficShapingHandler 类来处理流量传输速率。
- **详细**：[https://bugstack.cn/md/netty/expand/2019-08-27-netty%E6%A1%88%E4%BE%8B%EF%BC%8Cnetty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%8D%81%E4%BA%8C%E3%80%8ANetty%E6%B5%81%E9%87%8F%E6%95%B4%E5%BD%A2%E6%95%B0%E6%8D%AE%E6%B5%81%E9%80%9F%E7%8E%87%E6%8E%A7%E5%88%B6%E5%88%86%E6%9E%90%E4%B8%8E%E5%AE%9E%E6%88%98%E3%80%8B.html](https://bugstack.cn/md/netty/expand/2019-08-27-netty%E6%A1%88%E4%BE%8B%EF%BC%8Cnetty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%8D%81%E4%BA%8C%E3%80%8ANetty%E6%B5%81%E9%87%8F%E6%95%B4%E5%BD%A2%E6%95%B0%E6%8D%AE%E6%B5%81%E9%80%9F%E7%8E%87%E6%8E%A7%E5%88%B6%E5%88%86%E6%9E%90%E4%B8%8E%E5%AE%9E%E6%88%98%E3%80%8B.html)

#### 08：Netty 使用 SSL 通信的作用

- **选项**：
  - A.窃听风险eavesdropping：第三方可以获知通信内容。
  - B.篡改风险tampering：第三方可以修改通信内容。
  - C.冒充风险pretending：第三方可以冒充他人身份参与通信。
  - D.以上都是
- **答案**：D
- **解析**：SSL(Secure Sockets Layer 安全套接层),及其继任者传输层安全（Transport Layer Security，TLS）是为网络通信提供安全及数据完整性的一种安全协议。TLS与SSL在传输层对网络连接进行加密。通过它来防止；窃听、篡改和冒充。
- **详细**：[https://bugstack.cn/md/netty/expand/2019-08-28-netty%E6%A1%88%E4%BE%8B%EF%BC%8Cnetty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%8D%81%E4%B8%89%E3%80%8ANetty%E5%9F%BA%E4%BA%8ESSL%E5%AE%9E%E7%8E%B0%E4%BF%A1%E6%81%AF%E4%BC%A0%E8%BE%93%E8%BF%87%E7%A8%8B%E4%B8%AD%E5%8F%8C%E5%90%91%E5%8A%A0%E5%AF%86%E9%AA%8C%E8%AF%81%E3%80%8B.html](https://bugstack.cn/md/netty/expand/2019-08-28-netty%E6%A1%88%E4%BE%8B%EF%BC%8Cnetty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%8D%81%E4%B8%89%E3%80%8ANetty%E5%9F%BA%E4%BA%8ESSL%E5%AE%9E%E7%8E%B0%E4%BF%A1%E6%81%AF%E4%BC%A0%E8%BE%93%E8%BF%87%E7%A8%8B%E4%B8%AD%E5%8F%8C%E5%90%91%E5%8A%A0%E5%AF%86%E9%AA%8C%E8%AF%81%E3%80%8B.html)

#### 09：IM 通信；登录、验证、文件、表情、消息多协议处理

- **选项**：
  - A.定义通信一些包，含有针头和标记位
  - B.自定义继承实现 ByteToMessageDecoder 拦截标记指令
  - C.通过指令获取对应解析序列化操作
  - D.每个对应的消息处理器实现类 SimpleChannelInboundHandler 接收指定协议消息
- **答案**：A、B、C、D
- **解析**：一般对于 IM 通信的实现中，是需要自定义一组通信协议的，协议中要包括帧头、帧尾、标识符、长度等信息，来方便你完成半包粘包以及不同协议的处理。
- **详细**：[https://bugstack.cn/md/project/im/2.2%EF%BC%9A%E9%80%9A%E4%BF%A1%E5%8D%8F%E8%AE%AE%E5%8C%85%E5%AE%9A%E4%B9%89.html](https://bugstack.cn/md/project/im/2.2%EF%BC%9A%E9%80%9A%E4%BF%A1%E5%8D%8F%E8%AE%AE%E5%8C%85%E5%AE%9A%E4%B9%89.html)

#### 10：Netty ChunkedStream 数据流切块传输的目的

- **选项**：
  - A.解决网络带宽下，大数据块传输的性能问题
  - B.基于 ChunkedStream 对数据分块
  - C.通过管道消息传输控制 ChannelProgressivePromise
  - D.以上都包括
- **答案**：D
- **解析**：在Netty这种异步NIO框架的结构下，服务端与客户端通信过程中，高效、频繁、大量的写入大块数据时，因网络传输饱和的可能性就会造成数据处理拥堵、GC频繁、用户掉线的可能性。那么由于写操作是非阻塞的，所以即使没有写出所有的数据，写操作也会在完成时返回并通知ChannelFuture。当这种情况发生时，如果仍然不停地写入，就有内存耗尽的风险。所以在写大块数据时，需要对大块数据进行切割发送处理。
- **详细**：[https://bugstack.cn/md/netty/expand/2019-08-26-netty%E6%A1%88%E4%BE%8B%EF%BC%8Cnetty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%8D%81%E4%B8%80%E3%80%8ANetty%E5%9F%BA%E4%BA%8EChunkedStream%E6%95%B0%E6%8D%AE%E6%B5%81%E5%88%87%E5%9D%97%E4%BC%A0%E8%BE%93%E3%80%8B.html](https://bugstack.cn/md/netty/expand/2019-08-26-netty%E6%A1%88%E4%BE%8B%EF%BC%8Cnetty4.1%E4%B8%AD%E7%BA%A7%E6%8B%93%E5%B1%95%E7%AF%87%E5%8D%81%E4%B8%80%E3%80%8ANetty%E5%9F%BA%E4%BA%8EChunkedStream%E6%95%B0%E6%8D%AE%E6%B5%81%E5%88%87%E5%9D%97%E4%BC%A0%E8%BE%93%E3%80%8B.html)

### 9. 实战项目（81~90）

#### 01：分布式下保证幂等性实现

- **选项**：
  - A.Redis setNx 实现
  - B.JDK Lock 锁
  - C.数据库唯一索引
  - D.以上都是
- **答案**：C
- **解析**：在编程中一个幂等操作的特点是其任意多次执行所产生的影响均与一次执行的影响相同。而分布式环境下JDK Lock 锁只能保证当前实例加锁，但服务下的其他实例是没法保证的。而像一些交易、金融、账务等场景也都不敢使用 Redis setNx 实现，基本还是需要数据库唯一索引来限定。
- **详细**：[https://bugstack.cn/md/project/lottery/Part-2/%E7%AC%AC11%E8%8A%82%EF%BC%9A%E5%A3%B0%E6%98%8E%E4%BA%8B%E5%8A%A1%E9%A2%86%E5%8F%96%E6%B4%BB%E5%8A%A8%E9%A2%86%E5%9F%9F%E5%BC%80%E5%8F%91.html](https://bugstack.cn/md/project/lottery/Part-2/%E7%AC%AC11%E8%8A%82%EF%BC%9A%E5%A3%B0%E6%98%8E%E4%BA%8B%E5%8A%A1%E9%A2%86%E5%8F%96%E6%B4%BB%E5%8A%A8%E9%A2%86%E5%9F%9F%E5%BC%80%E5%8F%91.html)

#### 02：什么情况下分库分表

- **选项**：
  - A.TPS、QPS、GMV、PV、UV等数据指标，增速较快
  - B.数据增量很大，数据库连接数扩容不能满足
  - C.存量数据较大，热数据不多
  - D.单体应用承载了过多的业务诉求，业务又增量加快
- **答案**：A、B、D
- **解析**：对于存量数据较大，但热数据访问不多的情况下，大部分是通过迁移来解决，而不是引入分库分表提高系统的开发成本来处理。
- **详细**：[https://bugstack.cn/md/zsxq/material/interview.html](https://bugstack.cn/md/zsxq/material/interview.html)

#### 03：商品秒杀独占竞态锁

- **选项**：
  - A.可能发生死锁的风险
  - B.比较适合优化为分段静态的滑块锁
  - C.导致商品库存超卖
  - D.客户发现有库存但不能参与
- **答案**：A、B、D
- **解析**：独占竞态锁比较容易发生死锁的风险，所以会导致用户发现有库存但不能参与，可以使用分段滑块锁进行优化。但这不是导致超卖，只会少卖。
- **详细**：[https://bugstack.cn/md/develop/standard/2021-01-10-%E6%8F%A1%E8%8D%89%EF%BC%8C%E8%BF%99%E4%BA%9B%E7%A0%94%E5%8F%91%E4%BA%8B%E6%95%8530%E6%88%91%E9%83%BD%E5%B9%B2%E8%BF%87%EF%BC%81.html#_2-%E6%8A%80%E6%9C%AF%E6%96%B9%E6%A1%88%E5%AE%9E%E7%8E%B0%E7%B1%BB](https://bugstack.cn/md/develop/standard/2021-01-10-%E6%8F%A1%E8%8D%89%EF%BC%8C%E8%BF%99%E4%BA%9B%E7%A0%94%E5%8F%91%E4%BA%8B%E6%95%8530%E6%88%91%E9%83%BD%E5%B9%B2%E8%BF%87%EF%BC%81.html#_2-%E6%8A%80%E6%9C%AF%E6%96%B9%E6%A1%88%E5%AE%9E%E7%8E%B0%E7%B1%BB)

#### 04：抽奖概率 0.0000001 很小如何设计

- **选项**：
  - A.采用 Redis 存放一个对应概率码
  - B.建立一个超大的 HashMap 存放
  - C.通过双色球设计，每个为数是一组数字的组合
  - D.与运营沟通，调整方案
- **答案**：A、C、D
- **解析**：对于抽奖系统有时候运营会需要一些极小的概率的奖品，保持有但基本中不了。对于这样的情况实现方式还是蛮多的，比如开奖多少次后才投放，或者就是提供概率。而概率又太大，所以要进行一些设计比如 Redis 提供一个对应的概率码，或者双色球每一个位置又是N种组合。但不太合适建一个重大的 HashMap 来存放。
- **详细**：[https://bugstack.cn/md/zsxq/material/interview.html](https://bugstack.cn/md/zsxq/material/interview.html)

#### 05：多种类型抽奖策略如何注册

- **选项**：
  - A.构造注入
  - B.方法注入 @Bean
  - C.List注入
  - D.Map注入
- **答案**：A、B、C
- **解析**：对于使用策略模式实现的多种奖品组合，如果希望结合工厂模式提供服务，那么怎么维护对象的注入呢？这里可以使用构造注入、方法注入和 List注入，但不太适合 Map 注入，因为在不扩展 Spring 的情况下，Map 没法匹配到你对应每一个方法的 Key 值。
- **详细**：[https://bugstack.cn/md/project/lottery/Part-2/%E7%AC%AC07%E8%8A%82%EF%BC%9A%E7%AE%80%E5%8D%95%E5%B7%A5%E5%8E%82%E6%90%AD%E5%BB%BA%E5%8F%91%E5%A5%96%E9%A2%86%E5%9F%9F.html](https://bugstack.cn/md/project/lottery/Part-2/%E7%AC%AC07%E8%8A%82%EF%BC%9A%E7%AE%80%E5%8D%95%E5%B7%A5%E5%8E%82%E6%90%AD%E5%BB%BA%E5%8F%91%E5%A5%96%E9%A2%86%E5%9F%9F.html)

#### 06：A/BTest 用途

- **选项**：
  - A.形成数据对照
  - B.量化数据分析
  - C.运营策略对比
  - D.以上都是
- **答案**：D
- **解析**：A/B testing（以下简称 AB test）可以说是统计学和计算机科学相融合的产物，在互联网圈子中有着举足轻重的地位，各大科技巨头要做产品研发、数据分析都离不开使用 A/B Test 做对照组。
- **详细**：[https://bugstack.cn/md/project/lottery/introduce/Lottery%E6%8A%BD%E5%A5%96%E7%B3%BB%E7%BB%9F.html](https://bugstack.cn/md/project/lottery/introduce/Lottery%E6%8A%BD%E5%A5%96%E7%B3%BB%E7%BB%9F.html)

#### 07：MySQL 应用连接数配置

- **选项**：
  - A.总应用数 * 活跃连接数 < MySQL 可分配连接数
  - B.总应用数 * 最大连接数 < MySQL 可分配连接数
  - C.总应用数 * 空闲连接数 < MySQL 可分配连接数
  - D.以上都可以
- **答案**：B
- **解析**：分布式服务所有配置的连接池最大连接数综合要小于MySQL 分配的连接数，否则在流量上来以后会拖垮数据库。
- **详细**：[https://bugstack.cn/md/develop/standard/2021-01-10-%E6%8F%A1%E8%8D%89%EF%BC%8C%E8%BF%99%E4%BA%9B%E7%A0%94%E5%8F%91%E4%BA%8B%E6%95%8530%E6%88%91%E9%83%BD%E5%B9%B2%E8%BF%87%EF%BC%81.html#_3-%E6%8A%80%E6%9C%AF%E6%9C%8D%E5%8A%A1%E4%BD%BF%E7%94%A8%E7%B1%BB](https://bugstack.cn/md/develop/standard/2021-01-10-%E6%8F%A1%E8%8D%89%EF%BC%8C%E8%BF%99%E4%BA%9B%E7%A0%94%E5%8F%91%E4%BA%8B%E6%95%8530%E6%88%91%E9%83%BD%E5%B9%B2%E8%BF%87%EF%BC%81.html#_3-%E6%8A%80%E6%9C%AF%E6%9C%8D%E5%8A%A1%E4%BD%BF%E7%94%A8%E7%B1%BB)

#### 08：高并发下提供给前端H5分页

- **选项**：
  - A.limit x,y
  - B.where id > ? limit x
  - C.以上都可以
- **答案**：B
- **解析**：对于给前端 H5 提供的接口带有分页，要考虑性能，不能直接就 limit x,y 这样在大批量查询数据的时候会拖垮数据库，起不到索引的作用。所以要使用 `where id > ? limit x` 这样方式进行分页。
- **详细**：[https://bugstack.cn/md/project/lottery/Part-4/%E7%AC%AC01%E8%8A%82%EF%BC%9A%E6%90%AD%E5%BB%BA%E5%BE%AE%E4%BF%A1%E5%85%AC%E4%BC%97%E5%8F%B7%E7%BD%91%E5%85%B3%E6%9C%8D%E5%8A%A1.html](https://bugstack.cn/md/project/lottery/Part-4/%E7%AC%AC01%E8%8A%82%EF%BC%9A%E6%90%AD%E5%BB%BA%E5%BE%AE%E4%BF%A1%E5%85%AC%E4%BC%97%E5%8F%B7%E7%BD%91%E5%85%B3%E6%9C%8D%E5%8A%A1.html)

#### 09：项目运行较慢，重启后就好了

- **选项**：
  - A.应用服务不足
  - B.定时任务跑批
  - C.IO密集型运算
  - D.以上都是
- **答案**：D
- **解析**：服务运行慢重启就好，一般是服务数量不足，IO运算大，或者有定时任务，因为你重启这些业务短暂时间是与你断开连接了，所以速度好像快了。但实际治标不治本，需要优化服务。
- **详细**：[https://bugstack.cn/md/develop/standard/2021-09-15-%E8%BF%98%E9%87%8D%E6%9E%84%EF%BC%9F%E5%B0%B1%E4%BD%A0%E9%82%A3%E4%BB%A3%E7%A0%81%E5%8F%AA%E8%83%BD%E9%93%B2%E4%BA%86%E9%87%8D%E5%86%99%EF%BC%81.html](https://bugstack.cn/md/develop/standard/2021-09-15-%E8%BF%98%E9%87%8D%E6%9E%84%EF%BC%9F%E5%B0%B1%E4%BD%A0%E9%82%A3%E4%BB%A3%E7%A0%81%E5%8F%AA%E8%83%BD%E9%93%B2%E4%BA%86%E9%87%8D%E5%86%99%EF%BC%81.html)

#### 10：分库分表数据如何汇总查询(给C端用户使用)

- **选项**：
  - A.通过编写联合查询多库的SQL
  - B.从每个库表查询到内存中汇总
  - C.通过binlog同步到ES，从ES查询
  - D.以上都可以
- **答案**：C
- **解析**：通常在分布式服务中会使用分库分表承担较大的数据体量，但给C端的查询，基本不会使用复杂的 SQL 查询，更不会关联多个库表，这样会拖垮数据库。基本合理的方式是C端查询在有路由字段的时候都是查指定库表，直接提供返回即可。而如果是汇总类查询则需要基于 binlog 把分库分表数据同步到 ES 查询使用。
- **详细**：[https://bugstack.cn/md/project/lottery/Part-5/%E7%AC%AC07%E8%8A%82%EF%BC%9A%E9%83%A8%E7%BD%B2%E7%8E%AF%E5%A2%83%20Elasticsearch%E3%80%81Kibana.html](https://bugstack.cn/md/project/lottery/Part-5/%E7%AC%AC07%E8%8A%82%EF%BC%9A%E9%83%A8%E7%BD%B2%E7%8E%AF%E5%A2%83%20Elasticsearch%E3%80%81Kibana.html)

### 10. 扩展问题（91~100）

#### 01：常用的绘图工具

- **选项**：
  - A.xmind
  - B.visio
  - C.draw.io
  - D.Workbench
- **答案**：A、B、C、D
- **解析**：xmind、visio、draw.io、workbench
- **详细**：https://bugstack.cn/md/other/guide-to-reading.html[](https://bugstack.cn/md/other/guide-to-reading.html)

#### 02：常用的开发工具

- **选项**：
  - A.IntelliJ IDEA
  - B.Navicat
  - C.Docker
  - D.JD-GUI
  - E.RDM
  - F.Postman
- **答案**：A、B、C、D、E、F
- **解析**：常用工具系列；IntelliJ IDEAJ、Navicat、Docker、JD-GUI、RDM、Postman
- **详细**：https://bugstack.cn/md/other/guide-to-reading.html[](https://bugstack.cn/md/other/guide-to-reading.html)

#### 03：UML 类图，实现的画法

- **选项**：
  - A.虚线空心箭头
  - B.虚线实心箭头
  - C.实线空心箭头
  - D.实线实心箭头
- **答案**：A
- **解析**：实现接口的画法是虚线空心箭头，其他的可以参考详细文档
- **详细**：[https://bugstack.cn/md/about/study/2020-10-18-UML%E7%B1%BB%E5%9B%BE%E8%BF%98%E4%B8%8D%E6%87%82%EF%BC%9F%E6%9D%A5%E7%9C%8B%E7%9C%8B%E8%BF%99%E7%89%88%E4%B9%A1%E6%9D%91%E7%88%B1%E6%83%85%E7%B1%BB%E5%9B%BE%EF%BC%8C%E4%B8%80%E6%8A%8A%E5%AD%A6%E4%BC%9A%EF%BC%81.html](https://bugstack.cn/md/about/study/2020-10-18-UML%E7%B1%BB%E5%9B%BE%E8%BF%98%E4%B8%8D%E6%87%82%EF%BC%9F%E6%9D%A5%E7%9C%8B%E7%9C%8B%E8%BF%99%E7%89%88%E4%B9%A1%E6%9D%91%E7%88%B1%E6%83%85%E7%B1%BB%E5%9B%BE%EF%BC%8C%E4%B8%80%E6%8A%8A%E5%AD%A6%E4%BC%9A%EF%BC%81.html)

#### 04：树上10只鸟开一枪还剩下几只，你会想到什么？

- **选项**：
  - A.手抢是无声的吗？
  - B.有没有被关在笼子里或者绑在树上的鸟？
  - C.有残疾或者飞不动的鸟吗？
  - D.会不会一枪打死两只或者更多？
  - E.所有的鸟都可以自由活动飞离树以外吗？
  - F.打死以后挂在树上还是掉下来了？
  - G.确定那只鸟被打死了？
  - H.这个城市打鸟犯不犯法？
- **答案**：A、B、C、D、E、F、G、H
- **解析**：这是一到考察思路的题，基本就看你能对一个项目的开发前，能考虑到哪些点。
- **详细**：[https://bugstack.cn/md/about/job/2020-11-15-BATJTMD%EF%BC%8C%E5%A4%A7%E5%8E%82%E6%8B%9B%E8%81%98%EF%BC%8C%E9%83%BD%E6%8B%9B%E4%BB%80%E4%B9%88%E6%A0%B7Java%E7%A8%8B%E5%BA%8F%E5%91%98%EF%BC%9F.html](https://bugstack.cn/md/about/job/2020-11-15-BATJTMD%EF%BC%8C%E5%A4%A7%E5%8E%82%E6%8B%9B%E8%81%98%EF%BC%8C%E9%83%BD%E6%8B%9B%E4%BB%80%E4%B9%88%E6%A0%B7Java%E7%A8%8B%E5%BA%8F%E5%91%98%EF%BC%9F.html)

#### 05：想把代码写好，都要包括哪些东西

- **选项**：
  - A.数据结构
  - B.算法逻辑
  - C.设计模式
  - D.系统架构
- **答案**：A、B、C、D
- **解析**：对于能写好代码从设计上来说，会包括对库表的设计（数据结构），功能的实现（算法逻辑），设计模式和架构。所有的代码逻辑也都是对数学逻辑的具体实现。
- **详细**：[https://bugstack.cn/md/about/study/2021-01-17-%E6%95%B0%E5%AD%A6%EF%BC%8C%E7%A6%BB%E4%B8%80%E4%B8%AA%E7%A8%8B%E5%BA%8F%E5%91%98%E6%9C%89%E5%A4%9A%E8%BF%91%EF%BC%9F.html](https://bugstack.cn/md/about/study/2021-01-17-%E6%95%B0%E5%AD%A6%EF%BC%8C%E7%A6%BB%E4%B8%80%E4%B8%AA%E7%A8%8B%E5%BA%8F%E5%91%98%E6%9C%89%E5%A4%9A%E8%BF%91%EF%BC%9F.html)

#### 06：如何保证需求如期交付？

- **选项**：
  - A.从产品的BRD到PRD阶段开始确定预期上线时间
  - B.产品、UI设计、测试、研发、交付、预发、上线等时间线规划
  - C.每天一个固定时间开项目进度敏捷站会，对其进度，评估风险
  - D.如果是突然加需求，调整PRD等，那么需要重新进行资源协调。
- **答案**：A、B、C、D
- **解析**：为了保证项目的如期交付和交付质量，互联网中会从产品的BRD、PRD、研发设计、开发、测试等各个环节进行把控。
- **详细**：[https://bugstack.cn/md/zsxq/material/architecture_design.html](https://bugstack.cn/md/zsxq/material/architecture_design.html)

#### 07：什么是并发，什么是并行？

- **选项**：
  - A.并发：是指如何正确、高校地控制共享资源；
  - B.并行：是指如何利用更多的资源来产生高快速的响应；
  - C.并行：是指如何正确、高校地控制共享资源；
  - D.并发：是指如何利用更多的资源来产生高快速的响应；
- **答案**：A、B
- **解析**：其实有一大部分研发人员，搞不懂什么是并发编程，甚至常把并发编程和分布式联系起来。但很多的分布式架构设计，并不是在压榨一台机器的性能做IO密集型运算，所以如；并发、并行、多任务、多进程、多线程、分布式系统等，很多术语在大量的编程资料中被滥用了。
- **详细**：[https://bugstack.cn/md/about/study/2022-06-19-OnJava.html#%E5%9B%9B%E3%80%81%E6%B7%B1%E5%BA%A6-%E9%80%8F%E6%9E%90%E5%8E%9F%E7%90%86](https://bugstack.cn/md/about/study/2022-06-19-OnJava.html#%E5%9B%9B%E3%80%81%E6%B7%B1%E5%BA%A6-%E9%80%8F%E6%9E%90%E5%8E%9F%E7%90%86)

#### 08：你觉得怎样编码更合理

- **选项**：
  - A.先运行起来再优化性能
  - B.小心冗长的参数列表
  - C.不要通过子类来扩展基础功能
  - D.使用设计模式消除”裸功能“
- **答案**：A、B、C、D
- **解析**：在 On Java 一书中介绍了编码指南和实现意见，这些也都是程序员在编码多年的经验和习惯。
- **详细**：[https://bugstack.cn/md/about/study/2022-06-19-OnJava.html#%E4%BA%94%E3%80%81%E6%8C%87%E5%8D%97-%E5%B7%A8%E4%BD%AC%E7%BB%8F%E9%AA%8C](https://bugstack.cn/md/about/study/2022-06-19-OnJava.html#%E4%BA%94%E3%80%81%E6%8C%87%E5%8D%97-%E5%B7%A8%E4%BD%AC%E7%BB%8F%E9%AA%8C)

#### 09：你觉得我们的面试为什么像造火箭？

- **选项**：
  - A.因为面试官对我不了解，所以需要提出一些质疑的问题，通过我的回答了解我。
  - B.过桥的时候都不需要手扶着桥墩，但没有桥墩的桥，谁也不敢上桥。而多问的那些问题，是在考察我的边界在那里，能给企业带来多少种可能。
  - C.应对紧急复杂场景时可以快速反应，哪怕它可能只是万分之一的出现概率。但那也是我验证能力的机会。
  - D.为团队增加各类有高度的技术专才，形成团队技术力，获取更多的话语权和接项目的能力。
- **答案**：A、B、C、D
- **解析**：大部分研发都觉得面试像造火箭，但站在面试公司的角度，毕竟是对求职责不了解，所以要通过一些质疑和问题来了解求职者的技术广度和工程开发经验。
- **详细**：[https://bugstack.cn/md/about/job/2021-02-24-%E5%8D%8A%E5%B9%B4%E7%AD%9B%E9%80%89%E4%BA%86400+%E4%BB%BD%E7%AE%80%E5%8E%86%EF%BC%8C%E5%91%8A%E8%AF%89%E4%BD%A0%E6%80%8E%E4%B9%88%E5%86%99%E4%BC%9A%E8%A2%AB%E6%92%A9.html](https://bugstack.cn/md/about/job/2021-02-24-%E5%8D%8A%E5%B9%B4%E7%AD%9B%E9%80%89%E4%BA%86400+%E4%BB%BD%E7%AE%80%E5%8E%86%EF%BC%8C%E5%91%8A%E8%AF%89%E4%BD%A0%E6%80%8E%E4%B9%88%E5%86%99%E4%BC%9A%E8%A2%AB%E6%92%A9.html)

#### 10：关于小傅哥

- **选项**：
  - A.小傅哥的博客：[bugstack.cn](https://bugstack.cn/) —— 考题来自博客
  - B.小傅哥的Github：[https://github.com/fuzhengwei](https://github.com/fuzhengwei)
  - C.小傅哥的公众号：bugstack虫洞栈 —— 关注回复：1024 获取资料
  - D.小傅哥的出版物：[《重学Java设计模式》](https://item.jd.com/13218336.html)
  - E.小傅哥的IDEA 插件：[vo2dto](https://plugins.jetbrains.com/plugin/18262-vo2dto) —— 超3.4k安装使用
  - F.小傅哥的知识星球：[码农会锁](https://t.zsxq.com/05Qfeyby7)
- **答案**：A、B、C、D、E、F
- **解析**：这是一道送分题，感谢你的关注和支持。在我的博客中积累了大量的有深度的技术栈知识，可以补全你所需的成长内容。[bugstack.cn](https://bugstack.cn/)
- **详细**：[https://bugstack.cn](https://bugstack.cn/)