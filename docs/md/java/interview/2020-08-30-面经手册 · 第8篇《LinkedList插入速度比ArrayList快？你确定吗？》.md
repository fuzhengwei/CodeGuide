---
layout: post
category: interview
title: 面经手册 · 第8篇《LinkedList插入速度比ArrayList快？你确定吗？》
tagline: by 小傅哥
tag: [java,interview]
excerpt: 🚀面试造火箭！汽车75马力就够奔跑了，那你怎么还想要2.0涡轮+9AT呢？不要觉得你在写CRUD，有时候是你把需求设计成了日复一日的增删改查。
lock: need
---

# 面经手册 · 第8篇《LinkedList插入速度比ArrayList快？你确定吗？》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`你以为考你个数据结构是要造火箭？`

🚕汽车75马力就够奔跑了，那你怎么还想要2.0涡轮+9AT呢？大桥两边的护栏你每次走的时候都会去摸吗？那怎么没有护栏的大桥你不敢上呢？

很多时候，你额外的能力才是自身价值的体现，不要以为你的能力就只是做个业务开发每天CRUD，并不是产品让你写CRUD，而是因为你的能力只能产品功能设计成CRUD。

就像数据结构、算法逻辑、源码技能，它都是可以为你的业务开发赋能的，也是写出更好、更易扩展程序的根基，所以学好这份知识非常有必要。

*本文涉及了较多的代码和实践验证图稿，欢迎关注公众号：`bugstack虫洞栈`，回复下载得到一个链接打开后，找到ID：19🤫获取！*

## 二、面试题

`谢飞机`，ArrayList资料看了吧？嗯，那行问问你哈🦀

**`问`**：ArrayList和LinkedList，都用在什么场景呢？

**`答`**：啊，这我知道了。ArrayList是基于数组实现、LinkedList是基于双向链表实现，所以基于数据结构的不同，遍历和查找多的情况下用ArrayList、插入和删除频繁的情况下用LinkedList。

**`问`**：嗯，那LinkedList的插入效率一定比ArrayList好吗？

**`答`**：对，好！

送你个飞机✈，回去等消息吧！

***

其实，飞机回答的也不是不对，只是不全面。出门后不甘心买瓶`肥宅水`又回来，跟面试官聊了2个点，要到了两张图，如下；

![小傅哥 bugstack.cn & ArrayList头插、尾插、中间](https://bugstack.cn/assets/images/2020/interview/interview-9-01.png)

![小傅哥 bugstack.cn & LinkedList头插、尾插、中间](https://bugstack.cn/assets/images/2020/interview/interview-9-02.png)

如图，分别是；`10万`、`100万`、`1000万`，数据在两种集合下不同位置的插入效果，**所以：**，不能说LinkedList插入就快，ArrayList插入就慢，还需要看具体的操作情况。

接下来我们带着数据结构和源码，具体分析下。

## 三、数据结构

`Linked + List = 链表 + 列表 = LinkedList = 链表列表`

![小傅哥 bugstack.cn & LinkedList数据结构](https://bugstack.cn/assets/images/2020/interview/interview-9-03.png)

LinkedList，是基于链表实现，由双向链条next、prev，把数据节点穿插起来。所以，在插入数据时，是不需要像我们上一章节介绍的ArrayList那样，扩容数组。

但，又不能说所有的插入都是高效，比如中间区域插入，他还需要遍历元素找到插入位置。具体的细节，我们在下文的源码分析中进行讲解，也帮`谢飞机`扫除疑惑。

## 四、源码分析

### 1. 初始化

与ArrayList不同，LinkedList初始化不需要创建数组，因为它是一个链表结构。而且也没有传给构造函数初始化多少个空间的入参，例如这样是不可以的，如下；

![](https://bugstack.cn/assets/images/2020/interview/interview-9-04.png)

**但是**，构造函数一样提供了和ArrayList一些相同的方式，来初始化入参，如下这四种方式；

```java
@Test
public void test_init() {
    // 初始化方式；普通方式
    LinkedList<String> list01 = new LinkedList<String>();
    list01.add("a");
    list01.add("b");
    list01.add("c");
    System.out.println(list01);
    
    // 初始化方式；Arrays.asList
    LinkedList<String> list02 = new LinkedList<String>(Arrays.asList("a", "b", "c"));
    System.out.println(list02);
    
    // 初始化方式；内部类
    LinkedList<String> list03 = new LinkedList<String>()\\{
        {add("a");add("b");add("c");}
    \\};
    System.out.println(list03);
    
    // 初始化方式；Collections.nCopies
    LinkedList<Integer> list04 = new LinkedList<Integer>(Collections.nCopies(10, 0));
    System.out.println(list04);
}

// 测试结果

[a, b, c]
[a, b, c]
[a, b, c]
[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

Process finished with exit code 0
```

- 这些方式都可以初始化操作，按需选择即可。

### 2. 插入

LinkedList的插入方法比较多，List中接口中默认提供的是add，也可以指定位置插入。但在LinkedList中还提供了头插`addFirst`和尾插`addLast`。

关于插入这部分就会讲到为什么；有的时候LinkedList插入更耗时、有的时候ArrayList插入更好。

#### 2.1 头插

先来看一张数据结构对比图，回顾下ArrayList的插入也和LinkedList插入做下对比，如下；

![小傅哥 bugstack.cn & 插入对比](https://bugstack.cn/assets/images/2020/interview/interview-9-05.png)

看上图我们可以分析出几点；
1. ArrayList 头插时，需要把数组元素通过`Arrays.copyOf`的方式把数组元素移位，如果容量不足还需要扩容。
2. LinkedList 头插时，则不需要考虑扩容以及移位问题，直接把元素定位到首位，接点链条链接上即可。

##### 2.1.1 源码

这里我们再对照下`LinkedList`头插的源码，如下；

```java
private void linkFirst(E e) {
    final Node<E> f = first;
    final Node<E> newNode = new Node<>(null, e, f);
    first = newNode;
    if (f == null)
        last = newNode;
    else
        f.prev = newNode;
    size++;
    modCount++;
}
```

- first，首节点会一直被记录，这样就非常方便头插。
- 插入时候会创建新的节点元素，`new Node<>(null, e, f)`，紧接着把新的头元素赋值给first。
- 之后判断f节点是否存在，不存在则把头插节点作为最后一个节点、存在则用f节点的上一个链条prev链接。
- 最后记录size大小、和元素数量modCount。*modCount用在遍历时做校验，modCount != expectedModCount*

##### 2.1.2 验证

**ArrayList、LinkeList，头插源码验证**

```java
@Test
public void test_ArrayList_addFirst() {
    ArrayList<Integer> list = new ArrayList<Integer>();
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < 10000000; i++) {
        list.add(0, i);
    }
    System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
}

@Test
public void test_LinkedList_addFirst() {
    LinkedList<Integer> list = new LinkedList<Integer>();
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < 10000000; i++) {
        list.addFirst(i);
    }
    System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
}
```

**比对结果：**

![](https://bugstack.cn/assets/images/2020/interview/interview-9-06.png)

- 这里我们分别验证，10万、100万、1000万的数据量，在头插时的一个耗时情况。
- 如我们数据结构对比图中一样，ArrayList需要做大量的位移和复制操作，而LinkedList的优势就体现出来了，耗时只是实例化一个对象。

#### 2.2 尾插

先来看一张数据结构对比图，回顾下ArrayList的插入也和LinkedList插入做下对比，如下；

![小傅哥 bugstack.cn & 插入对比](https://bugstack.cn/assets/images/2020/interview/interview-9-07.png)

看上图我们可以分析出几点；
1. ArrayList 尾插时，是不需要数据位移的，比较耗时的是数据的扩容时，需要拷贝迁移。
2. LinkedList 尾插时，与头插相比耗时点会在对象的实例化上。

##### 2.2.1 源码

这里我们再对照下`LinkedList`尾插的源码，如下；

```java
void linkLast(E e) {
    final Node<E> l = last;
    final Node<E> newNode = new Node<>(l, e, null);
    last = newNode;
    if (l == null)
        first = newNode;
    else
        l.next = newNode;
    size++;
    modCount++;
}
```

- 与头插代码相比几乎没有什么区别，只是first换成last
- 耗时点只是在创建节点上，`Node<E>`

##### 2.2.2 验证

**ArrayList、LinkeList，尾插源码验证**

```java
@Test
public void test_ArrayList_addLast() {
    ArrayList<Integer> list = new ArrayList<Integer>();
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < 10000000; i++) {
        list.add(i);
    }
    System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
}

@Test
public void test_LinkedList_addLast() {
    LinkedList<Integer> list = new LinkedList<Integer>();
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < 1000000; i++) {
        list.addLast(i);
    }
    System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
}
```

**比对结果：**

![](https://bugstack.cn/assets/images/2020/interview/interview-9-08.png)

- 这里我们分别验证，10万、100万、1000万的数据量，在尾插时的一个耗时情况。
- 如我们数据结构对比图中一样，ArrayList 不需要做位移拷贝也就不那么耗时了，而LinkedList则需要创建大量的对象。*所以这里ArrayList尾插的效果更好一些。*

#### 2.3 中间插

先来看一张数据结构对比图，回顾下ArrayList的插入也和LinkedList插入做下对比，如下；

![](https://bugstack.cn/assets/images/2020/interview/interview-9-09.png)

看上图我们可以分析出几点；

1. ArrayList 中间插入，首先我们知道他的定位时间复杂度是O(1)，比较耗时的点在于数据迁移和容量不足的时候扩容。
2. LinkedList 中间插入，链表的数据实际插入时候并不会怎么耗时，但是它定位的元素的时间复杂度是O(n)，所以这部分以及元素的实例化比较耗时。

##### 2.3.1 源码

这里看下LinkedList指定位置插入的源码；

**使用add(位置、元素)方法插入：**

```java
public void add(int index, E element) {
    checkPositionIndex(index);
    if (index == size)
        linkLast(element);
    else
        linkBefore(element, node(index));
}
```

**位置定位node(index)：**

```java
Node<E> node(int index) {
    // assert isElementIndex(index);
    if (index < (size >> 1)) {
        Node<E> x = first;
        for (int i = 0; i < index; i++)
            x = x.next;
        return x;
    } else {
        Node<E> x = last;
        for (int i = size - 1; i > index; i--)
            x = x.prev;
        return x;
    }
}
```

- `size >> 1`，这部分的代码判断元素位置在左半区间，还是右半区间，在进行循环查找。

**执行插入：**

```java
void linkBefore(E e, Node<E> succ) {
    // assert succ != null;
    final Node<E> pred = succ.prev;
    final Node<E> newNode = new Node<>(pred, e, succ);
    succ.prev = newNode;
    if (pred == null)
        first = newNode;
    else
        pred.next = newNode;
    size++;
    modCount++;
}
```

- 找到指定位置插入的过程就比较简单了，与头插、尾插，相差不大。
- 整个过程可以看到，插入中比较耗时的点会在遍历寻找插入位置上。

##### 2.3.2 验证

**ArrayList、LinkeList，中间插入源码验证**

```java
@Test
public void test_ArrayList_addCenter() {
    ArrayList<Integer> list = new ArrayList<Integer>();
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < 10000000; i++) {
        list.add(list.size() >> 1, i);
    }
    System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
}

@Test
public void test_LinkedList_addCenter() {
    LinkedList<Integer> list = new LinkedList<Integer>();
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < 1000000; i++) {
        list.add(list.size() >> 1, i);
    }
    System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
}
```

**比对结果：**

![](https://bugstack.cn/assets/images/2020/interview/interview-9-10.png)

- 这里我们分别验证，10万、100万、1000万的数据量，在中间插时的一个耗时情况。
- 可以看到Linkedlist在中间插入时，遍历寻找位置还是非常耗时了。所以不同的情况下，需要选择不同的List集合做业务。

### 3. 删除

讲了这么多插入的操作后，删除的知识点就很好理解了。与ArrayList不同，删除不需要拷贝元素，LinkedList是找到元素位置，把元素前后链连接上。基本如下图；

![](https://bugstack.cn/assets/images/2020/interview/interview-9-11.png)

- 确定出要删除的元素x，把前后的链接进行替换。
- 如果是删除首尾元素，操作起来会更加容易，这也就是为什么说插入和删除快。但中间位置删除，需要遍历找到对应位置。

#### 3.1 删除操作方法

| 序号 | 方法                                     | 描述                                  |
| :--: | ---------------------------------------- | ------------------------------------- |
|  1   | list.remove();                           | 与removeFirst()一致                   |
|  2   | list.remove(1);                          | 删除Idx=1的位置元素节点，需要遍历定位 |
|  3   | list.remove("a");                        | 删除元素="a"的节点，需要遍历定位      |
|  4   | list.removeFirst();                      | 删除首位节点                          |
|  5   | list.removeLast();                       | 删除结尾节点                          |
|  6   | list.removeAll(Arrays.asList("a", "b")); | 按照集合批量删除，底层是Iterator删除  |

**源码：**

```java
@Test
public void test_remove() {
    LinkedList<String> list = new LinkedList<String>();
    list.add("a");
    list.add("b");
    list.add("c");
    
    list.remove();
    list.remove(1);
    list.remove("a");
    list.removeFirst();
    list.removeLast();
    list.removeAll(Arrays.asList("a", "b"));
}
```

#### 3.2 源码

删除操作的源码都差不多，分为删除首尾节点与其他节点时候，对节点的解链操作。这里我们举例一个删除其他位置的源码进行学习，如下；

**list.remove("a");**

```java
public boolean remove(Object o) {
    if (o == null) {
        for (Node<E> x = first; x != null; x = x.next) {
            if (x.item == null) {
                unlink(x);
                return true;
            }
        }
    } else {
        for (Node<E> x = first; x != null; x = x.next) {
            if (o.equals(x.item)) {
                unlink(x);
                return true;
            }
        }
    }
    return false;
}
```

- 这一部分是元素定位，和` unlink(x)`解链。循环查找对应的元素，这部分没有什么难点。

**unlink(x)解链**

```java
E unlink(Node<E> x) {
    // assert x != null;
    final E element = x.item;
    final Node<E> next = x.next;
    final Node<E> prev = x.prev;
    
    if (prev == null) {
        first = next;
    } else {
        prev.next = next;
        x.prev = null;
    }
    
    if (next == null) {
        last = prev;
    } else {
        next.prev = prev;
        x.next = null;
    }
    
    x.item = null;
    size--;
    modCount++;
    return element;
}
```

这部分源码主要有以下几个知识点；
1. 获取待删除节点的信息；元素item、元素下一个节点next、元素上一个节点prev。
2. 如果上个节点为空则把待删除元素的下一个节点赋值给首节点，否则把待删除节点的下一个节点，赋值给待删除节点的上一个节点的子节点。
3. 同样待删除节点的下一个节点next，也执行2步骤同样操作。
4. 最后是把删除节点设置为null，并扣减size和modeCount数量。

### 4. 遍历

接下来说下遍历，ArrayList与LinkedList的遍历都是通用的，基本包括5种方式。

这里我们先初始化出待遍历的集合1千万数据；

```java
int xx = 0;
@Before
public void init() {
    for (int i = 0; i < 10000000; i++) {
        list.add(i);
    }
}
```

#### 4.1 普通for循环

```java
@Test
public void test_LinkedList_for0() {
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < list.size(); i++) {
        xx += list.get(i);
    }
    System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
}
```

#### 4.2 增强for循环

```java
@Test
public void test_LinkedList_for1() {
    long startTime = System.currentTimeMillis();
    for (Integer itr : list) {
        xx += itr;
    }
    System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
}
```

#### 4.3 Iterator遍历

```java
@Test
public void test_LinkedList_Iterator() {
    long startTime = System.currentTimeMillis();
    Iterator<Integer> iterator = list.iterator();
    while (iterator.hasNext()) {
        Integer next = iterator.next();
        xx += next;
    }
    System.out.println("耗时：" + (System.currentTimeMillis() - startTime))
}
```

#### 4.4 forEach循环

```java
@Test
public void test_LinkedList_forEach() {
    long startTime = System.currentTimeMillis();
    list.forEach(integer -> {
        xx += integer;
    });
    System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
}
```

#### 4.5 stream(流)

```java
@Test
public void test_LinkedList_stream() {
    long startTime = System.currentTimeMillis();
    list.stream().forEach(integer -> {
        xx += integer;
    });
    System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
}
```

**那么**，以上这5种遍历方式谁最慢呢？按照我们的源码学习分析下吧，欢迎留下你的答案在评论区！

## 五、总结

- ArrayList与LinkedList都有自己的使用场景，如果你不能很好的确定，那么就使用ArrayList。但如果你能确定你会在集合的首位有大量的插入、删除以及获取操作，那么可以使用LinkedList，因为它都有相应的方法；`addFirst`、`addLast`、`removeFirst`、`removeLast`、`getFirst`、`getLast`，这些操作的时间复杂度都是O(1)，非常高效。
- LinkedList的链表结构不一定会比ArrayList节省空间，首先它所占用的内存不是连续的，其次他还需要大量的实例化对象创造节点。虽然不一定节省空间，但链表结构也是非常优秀的数据结构，它能在你的程序设计中起着非常优秀的作用，例如可视化的链路追踪图，就是需要链表结构，并需要每个节点自旋一次，用于串联业务。
- 程序的精髓往往就是数据结构的设计，这能为你的程序开发提供出非常高的效率改变。可能目前你还不能用到，但万一有一天你需要去造🚀火箭了呢？