---
title: 杨辉三角 PascalTriangle
lock: need
---

# 《程序员数学：杨辉三角》—— 开方作法本源

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>源码：[https://github.com/fuzhengwei/java-algorithms](https://github.com/fuzhengwei/java-algorithms)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`杨辉三角的历史`

杨辉三角按照杨辉于1261年所编写的《详解九章算法》一书，里面有一张图片，介绍此种算法来自于另外一个数学家贾宪所编写的《释锁算书》一书，但这本书早已失传无从考证。但可以肯定的是这一图形的发现我国不迟于1200年左右。在欧洲，这图形称为"巴斯加(Pascal)三角"。因为一般都认为这是巴斯加在1654年发明的。其实在巴斯加之前已经有许多人普及过，最早是德国人阿匹纳斯(Pertrus APianus)，他曾经把这个图形刻在1527年著的一本算术书封面上。但无论如何，杨辉三角的发现，在我国比在欧洲至少要早300年光景。

此外杨辉三角原来的名字也不是三角，而是叫做**开方作法本源**，后来也有人称为**乘法求廉图**。因为这些名称实在太古奥了些，所以后来简称为“三角”。

在小傅哥学习杨辉三角的过程中，找到了一本大数学家华罗庚的PDF[《从杨辉三角谈起 - 华罗庚》]()。—— 这些数学真的非常重要，每每映射到程序中都是一段把for循环优化成算法的体现，提高执行效率。

## 二、杨辉三角构造

在开始分享杨辉三角的特性和代码实现前，我们先来了解下杨辉三角的结构构造。

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/pascal-triangle-01.png?raw=true" width="450px">
</div>

杨辉三角的结构和规律非常简单，除去每次两边的1，中间的数字都是上面两个数字的和。如图示意的三角区域。但也就是如此简单的结构，却有着诸多的数学逻辑体现。包括我们计算的二项式、N选X的种数还有斐波那契数列等，都可以在杨辉三角中体现出来。接下来我们就来看看这些特性。

## 三、杨辉三角特性

为了方便学习杨辉三角的数学逻辑特性，我们把它按左对齐方式进行排列。

```java
[1]
[1,1]
[1,2,1]
[1,3,3,1]
[1,4,6,4,1]
[1,5,10,10,5,1]
[1,6,15,20,15,6,1]
[1,7,21,35,35,21,7,1]
[1,8,28,56,70,56,28,8,1]
```

接下来我们就以这组杨辉三角数列，来展示它的数学逻辑特性。关于杨辉三角的Java代码放已到下文中，读者可以查阅。

### 1. 二项式展开

大家在上学阶段一定学习过二项式展开，例如：`(x+y)^2 = x^2 + 2xy + y^2` 其实这个展开的数学逻辑在杨辉三角中可以非常好的展示出来。

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/pascal-triangle-02.png?raw=true" width="650px">
</div>

- 任意一个二项式展开后的数字乘积，都可以映射到杨辉三角对应的中的数字。
- 二项式展开公式是用来计算给定二项式的指数幂的展开式的公式。对于给定的二项式 (x + y)n，二项式展开公式为：`(x + y)^n = x^n + nx^{n-1}y + n(n-1)x^{n-2}y^2 + ... + y^n` 这个公式也正好符合杨辉三角的数字值。

### 2. 组合数

组合数是数学中定义的一种数学概念，用于计算有多少种选择可以从一组物品中选择出若干的物品。比如你早上有5种水果可以吃，但你吃不了那么多，让你5种水果中选2个，看看有多少种选择。通过使用公式 c(n,k) = n!/k!(n-k)! 可以计算出，5选2有10种选择。

那么这样一个计算也是可以体现在杨辉三角中的。

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/pascal-triangle-03.png?raw=true" width="400px">
</div>

- 5选2，在杨辉三角中可以找到第5行的第2列，结果是10。按照这个规律，5选3=10、5选4=5

### 3. 斐波那契数列

斐波那契数列出现在印度数学中，与梵文韵律有关。在梵语诗歌传统中，人们对列举所有持续时间为 2 单位的长 (L) 音节与 1 单位持续时间的短 (S) 音节并列的模式很感兴趣。关于更多斐波那契更多知识可以阅读小傅哥的：[《程序员数学：斐波那契》—— 为什么不能用斐波那契散列，做数据库路由算法？](https://bugstack.cn/md/algorithm/logic/math/2022-11-05-fibonacci.html)

斐波那契数列可以由递归关系定义：`F0 = 0，F1 = 1，Fn = Fn-1 + Fn-2`

|  F0  |  F1  |  F2  |  F3  |  F4  |  F5  |  F6  |  F7  |  F8  |  F9  |
| :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: |
|  0   |  1   |  1   |  2   |  3   |  5   |  8   |  13  |  21  |  34  |

而这样一个有规律的斐波那契数列在杨辉三角中也是有所体现的。

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/pascal-triangle-04.png?raw=true" width="550px">
</div>

- 把斜对角的数字做加和，会得到一组斐波那契数列；1、1、2、3、5、8、13、15、33

### 4. 次方数

在杨辉三角中还有一个非常有意思的特性，就是有2的次方和11次方数。

**2次方**

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/pascal-triangle-05.png?raw=true" width="550px">
</div>
- 杨辉三角每一行的数字加和，正好的2的0次方、1次方..n次方


**11次方**

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/pascal-triangle-06.png?raw=true" width="500px">
</div>


- 另外一个是11的次幂，例如11的2次幂的结果正好是121这一排数字的组合。如果是11的5次幂，中间有连续的10，则是把后一位向前一位进位一下。

### 5. 平方数

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/pascal-triangle-07.png?raw=true" width="350px">
</div>

- 在杨辉三角中还有一个平方数的规律体现。比如3的平方正好是右侧3+6的结果。4的平方是右侧6+10的结果。

## 四、杨辉三角实现

接下来我们实现下杨辉三角；

```java
public HashMap<Integer, Integer> pascalTriangle(int lineNumber) {
    HashMap<Integer, Integer> currentLine = new HashMap<>();
    currentLine.put(0, 1);
    int currentLineSize = lineNumber + 1;
    for (int numberIdx = 1; numberIdx < currentLineSize; numberIdx += 1) {
        /*
         * https://github.com/trekhleb/javascript-algorithms/blob/master/src/algorithms/math/pascal-triangle/pascalTriangle.js
         * 第i行号中的第 -th 个条目lineNumber是 Binomial CoefficientC(lineNumber, i)并且所有行都以 value 开头1。这个思路是C(lineNumber, i)使用C(lineNumber, i-1). 它可以O(1)使用以下方法及时计算：
         * C(lineNumber, i)   = lineNumber! / ((lineNumber - i)! * i!)
         * C(lineNumber, i - 1) = lineNumber! / ((lineNumber - i + 1)! * (i - 1)!)
         *
         * 从以上两个表达式我们可以推导出下面的表达式：C(lineNumber, i) = C(lineNumber, i - 1) * (lineNumber - i + 1) / i
         * 所以C(lineNumber, i)可以从C(lineNumber, i - 1)时间上算出来O(1)
         */
        currentLine.put(numberIdx, ((null == currentLine.get(numberIdx - 1) ? 0 : currentLine.get(numberIdx - 1)) * (lineNumber - numberIdx + 1)) / numberIdx);
    }
    return currentLine;
}
```

**单元测试**

```java
@Test
public void test_PascalTriangle() {
    PascalTriangle pascalTriangle = new PascalTriangle();
    for (int i = 0; i <= 10; i++) {
        HashMap<Integer, Integer> currentLineMap = pascalTriangle.pascalTriangle(i);
        System.out.println(JSON.toJSONString(currentLineMap.values()));
    }
}

[1]
[1,1]
[1,2,1]
[1,3,3,1]
[1,4,6,4,1]
[1,5,10,10,5,1]
[1,6,15,20,15,6,1]
[1,7,21,35,35,21,7,1]
[1,8,28,56,70,56,28,8,1]
[1,9,36,84,126,126,84,36,9,1]
[1,10,45,120,210,252,210,120,45,10,1]
```

- 这样我们可以得到一组杨辉三角数列了。

## 五、常见面试题

- 杨辉三角有哪些用途？
- 用代码实现下杨辉三角。—— 在LeetCode中也有这样的题目