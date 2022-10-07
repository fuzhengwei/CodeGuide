---
title: 数组 Array
lock: need
---

# 数组 Array

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>原文：[https://mp.weixin.qq.com/s/DIlTfiEHselk6yW19HplWQ](https://mp.weixin.qq.com/s/DIlTfiEHselk6yW19HplWQ)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`数组是数据结构还是数据类型？`

数组只是个名称，它可以描述一组操作，也可以命名这组操作。数组的数据操作，是通过 idx->val 的方式来处理。它不是具体要求内存上要存储着连续的数据才叫数据，而是说，通过连续的索引 idx，也可以线性访问相邻的数据。

那么当你定义了数据的存储方式，也就定义了数据结构。所以它也是被归类为数据结构。

## 二、数组数据结构

数组（Array）是一种线性表数据结构。它用一组连续的内存空间，来存储一组具有相同类型数据的集合。

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/algorithms-220730-01.png?raw=true" width="500px">
</div>

数组的特点：
1. 数组是相同数据类型的元素集合（int 不能存放 double）
2. 数组中各元素的存储是有先后顺序的，它们在内存中按照这个顺序连续存放到一起。内存地址连续。
3. 数组获取元素的时间复杂度为O(1)

### 1. 一维数组

一维数组是最常用的数组，其他很多数据结构的变种也都是从一维数组来的。例如 HashMap 的拉链寻址结构，ThreadLocal 的开放寻址结构，都是从一维数组上实现的。

### 2. 二维数组

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/algorithms-220730-02.png?raw=true" width="500px">
</div>

二维以及多维数组，在开发场景中使用到的到不是不多，不过在一些算法逻辑，数学计算中到是可以使用。

## 三、实现数组列表

在 Java 的源码中，数组是一个非常常用的数据结构，很多其他数据结构也都有数组的影子。在一些数据存放和使用的场景中，基本也都是使用 ArrayList 而不是 LinkedList，具体性能分析参考：[LinkedList插入速度比ArrayList快？你确定吗？](https://bugstack.cn/md/java/interview/2020-08-30-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC8%E7%AF%87%E3%80%8ALinkedList%E6%8F%92%E5%85%A5%E9%80%9F%E5%BA%A6%E6%AF%94ArrayList%E5%BF%AB%EF%BC%9F%E4%BD%A0%E7%A1%AE%E5%AE%9A%E5%90%97%EF%BC%9F%E3%80%8B.html)

那么本章节我们就借着数组结构的学习，实现一个简单的 ArrayList，让使用 Java 的读者既能了解学习数据结构，也能了解到 Java 源码实现。

- 源码地址：[https://github.com/fuzhengwei/java-algorithms](https://github.com/fuzhengwei/java-algorithms) - `Java 算法与数据结构`
- 本章源码：[https://github.com/fuzhengwei/java-algorithms/tree/main/data-structures/src/main/java/array_list](https://github.com/fuzhengwei/java-algorithms/tree/main/data-structures/src/main/java/array_list)

### 1. 基本设计

数组是一个固定的、连续的、线性的数据结构，那么想把它作为一个自动扩展容量的数组列表，则需要做一些扩展。

```java
/**
 * 默认初始化空间
 */
private static final int DEFAULT_CAPACITY = 10;
/**
 * 空元素
 */
private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
/**
 * ArrayList 元素数组缓存区
 */
transient Object[] elementData;
```

1. 初始化 ArrayList 阶段，如果不指定大小，默认会初始化一个空的元素。这个时候是没有默认长度的。
2. 那么什么时候给初始化的长度呢？是在首次添加元素的时候，因为所有的添加元素操作，也都是需要判断容量，以及是否扩容的。那么在 add 添加元素时统一完成这个事情，还是比较好处理的。
3. 之后就是随着元素的添加，容量是会不足的。当容量不足的是，需要进行扩容操作。同时还得需要把旧数据迁移到新的数组上。*所以数据的迁移算是一个比较耗时的操作*

### 2. 添加元素

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/algorithms-220730-03.png?raw=true" width="500px">
</div>

```java
public boolean add(E e) {
    // 确保内部容量
    int minCapacity = size + 1;
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
    }
    // 判断扩容操作
    if (minCapacity - elementData.length > 0) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
    // 添加元素
    elementData[size++] = e;
    return true;
}
```

**这是一份简化后的 ArrayList#add 操作**

1. 判断当前容量与初始化容量，使用 Math.max 函数取最大值最为最小初始化空间。
2. 接下来是判断 minCapacity 和元素的数量，是否达到了扩容。首次创建 ArrayList 是一定会扩容的，也就是初始化 DEFAULT_CAPACITY = 10 的容量。
3. Arrays.copyOf 实际上是创建一个新的空间数组，之后调用的 System.arraycopy 迁移到新创建的数组上。这样后续所有的扩容操作，也就都保持统一了。
4. ArrayList 扩容完成后，就是使用 elementData[size++] = e; 添加元素操作了。

### 3. 移除元素

ArrayList 的重点离不开对 System.arraycopy 的使用，它是一个本地方法，可以让你从原数组的特定位置，迁移到新数组的指定位置和迁移数量。如图 2-5 所示，数据迁移 *测试代码在 java-algorithms*

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/algorithms-220730-05.png?raw=true" width="500px">
</div>

**删除元素**

```java
public E remove(int index) {
    E oldValue = (E) elementData[index];
    int numMoved = size - index - 1;
    if (numMoved > 0) {
        // 从原始数组的某个位置，拷贝到目标对象的某个位置开始后n个元素
        System.arraycopy(elementData, index + 1, elementData, index, numMoved);
    }
    elementData[--size] = null; // clear to let GC do its work
    return oldValue;
}
```

- ArrayList 的元素删除，就是在确定出元素位置后，使用 System.arraycopy 拷贝数据方式移动数据，把需要删除的元素位置覆盖掉。
- 此外它还会把已经删除的元素设置为 null 一方面让我们不会在读取到这个元素，另外一方面也是为了 GC

### 4. 获取元素

```java
public E get(int index) {
    return (E) elementData[index];
}
@Override
public String toString() {
    return "ArrayList{" +
            "elementData=" + Arrays.toString(elementData) +
            ", size=" + size +
            '}';
}
```

- 获取元素就比较简单了，直接从 elementData 使用索引直接获取即可。这个是一个 O(1) 操作。也正因为搜索元素的便捷性，才让 ArrayList 使用的那么广泛。同时为了兼容可以通过元素来获取数据，而不是直接通过下标，引出了 HashMap 使用哈希值计算下标的计算方式，也引出了斐波那契散列。它们的设计都是在尽可能减少元素碰撞的情况下，尽可能使用贴近 O(1) 的时间复杂度获取数据。*这些内容的学习可以阅读小傅哥的《Java面经手册》也可以随着本系列章节内容的铺设逐步覆盖到算法后进行学习*

## 四、数组列表测试

```java
@Test
public void test_array_list() {
    cn.bugstack.algorithms.data.array.List<String> list = new ArrayList<>();
    list.add("01");
    list.add("02");
    list.add("03");
    list.add("04");
    list.add("05");
    list.add("06");
    list.add("07");
    list.add("08");
    list.add("09");
    list.add("10");
    list.add("11");
    list.add("12");
    
    System.out.println(list);
    
    list.remove(9);
    
    System.out.println(list);
}
```

**测试结果**

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/algorithms-220730-06.png?raw=true" width="500px">
</div>

```java
ArrayList{elementData=[01, 02, 03, 04, 05, 06, 07, 08, 09, 10, 11, 12, null, null, null], size=12}
ArrayList{elementData=[01, 02, 03, 04, 05, 06, 07, 08, 09, 11, 12, null, null, null, null], size=11}

Process finished with exit code 0
```

- 测试案例中包括了在我们自己实现的 ArrayList 中顺序添加元素，逐步测试扩容迁移元素，以及删除元素后数据的迁移。
- 最终的测试结果可以看到，一共有12个元素，其中idx=9的元素被删除前后，元素的迁移变化。

## 六、常见面试问题

1. 数据结构中有哪些是线性表数据结构？
2. 数组的元素删除和获取，时间复杂度是多少？
3. ArrayList 中默认的初始化长度是多少？
4. ArrayList 中扩容的范围是多大一次？
5. ArrayList 是如何完成扩容的，System.arraycopy 各个入参的作用是什么？

## 七、读者作业

- [https://t.zsxq.com/05vVjaa6i](https://t.zsxq.com/05vVjaa6i)
- [https://t.zsxq.com/05ba2fu3N](https://t.zsxq.com/05ba2fu3N)