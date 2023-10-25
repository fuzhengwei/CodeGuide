---
title: 斐波那契 fibonacci
lock: need
---

# 《程序员数学：斐波那契》—— 为什么不能用斐波那契散列，做数据库路由算法？

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>源码：[https://github.com/fuzhengwei/java-algorithms](https://github.com/fuzhengwei/java-algorithms)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、关于斐波那契

`斐波那契的历史`

斐波那契数列出现在[印度数学](https://en.wikipedia.org/wiki/Fibonacci_number#History)中，与梵文韵律有关。在梵语诗歌传统中，人们对列举所有持续时间为 2 单位的长 (L) 音节与 1 单位持续时间的短 (S) 音节并列的模式很感兴趣。用给定的总持续时间计算连续 L 和 S 的不同模式会产生斐波那契数：持续时间m单位的模式数量是F(m + 1)。

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/fibonacci-01.png?raw=true" width="550px">
</div>

斐波那契数列可以由递归关系定义

F0 = 0，F1 = 1，Fn = Fn-1 + Fn-2

|  F0  |  F1  |  F2  |  F3  |  F4  |  F5  |  F6  |  F7  |  F8  |  F9  | F10  | F11  | F12  | F13  | F14  | F15  | F16  | F17  | F18  | F19  |
| :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: |
|  0   |  1   |  1   |  2   |  3   |  5   |  8   |  13  |  21  |  34  |  55  |  89  | 144  | 233  | 377  | 610  | 987  | 1597 | 2584 | 4181 |

- 从 F2 开始任意一位都是前两位之和。
- 从 F2 开始任意一位与前一位相比的比值，都无限趋近于 (√*5* - 1)/2 = 0.618 因此基于黄金分割的计算应用，也被称为斐波那契应用。

那这个就是斐波那契的基本定义和特性，并且基于这样的特性在计算机科学中，斐波那契常用于；伪随机数生成、AVL二叉树、最大公约数、合并排序算法等。

而大部分程序员👨🏻‍💻包括小傅哥最开始意识到斐波那契的应用则来自于，Java 源码 ThreadLocal 中 HASH_INCREMENT = `0x61c88647` 这样一个常量的定义。因为这用作数据散列的特殊值 `0x61c88647` 就是基于黄金分割点计算得来的，公式：` (1L << 32) - (long) ((1L << 32) * (Math.sqrt(5) - 1))/2` 。

那么既然 [ThreadLocal 是基于斐波那契散列计算的下标索引](https://bugstack.cn/md/java/interview/2020-09-23-%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C%20%C2%B7%20%E7%AC%AC12%E7%AF%87%E3%80%8A%E9%9D%A2%E8%AF%95%E5%AE%98%EF%BC%8CThreadLocal%20%E4%BD%A0%E8%A6%81%E8%BF%99%E4%B9%88%E9%97%AE%EF%BC%8C%E6%88%91%E5%B0%B1%E6%8C%82%E4%BA%86%EF%BC%81%E3%80%8B.html)，那为啥数据库路由算法不能使用同样的方式计算散列索引呢？因为通过验证可以得知，斐波那契散列并不满足[**严格的雪崩标准（SAC）**](https://en.wikipedia.org/wiki/Avalanche_effect)。接下来小傅哥就带着大家一起来使用数据验证下。

## 二、斐波那契计算

斐波那契数列可以通过循环、递归以及[封闭式表达式（比奈公式）](https://en.wikipedia.org/wiki/Fibonacci_number#Closed-form_expression) 的方式进行计算。读者可在单元测试中验证：[https://github.com/fuzhengwei/java-algorithms](https://github.com/fuzhengwei/java-algorithms)

### 1. 循环计算

```java
public double fibonacci(int n) {
    double currentVal = 1;
    double previousVal = 0;
    if (n == 1) return 1;
    int iterationsCounter = n - 1;
    while (iterationsCounter > 0) {
        currentVal += previousVal;
        previousVal = currentVal - previousVal;
        iterationsCounter -= 1;
    }
    return currentVal;
}
```

### 2. 递归计算

```java
public int fibonacciRecursion(int n) {
    if (n == 1 || n == 2) {
        return 1;
    } else {
        return (fibonacciRecursion(n - 1) + fibonacciRecursion(n - 2));
    }
}
```

### 3. 比奈公式

```java
public double fibonacciClosedForm(long position) {
    int maxPosition = 75;
    if (position < 1 || position > maxPosition) {
        throw new RuntimeException("Can't handle position smaller than 1 or greater than 75");
    }
    double sqrt = Math.sqrt(5);
    double phi = (1 + sqrt) / 2;
    return Math.floor((Math.pow(phi, position)) / sqrt + 0.5);
}
```

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/fibonacci-02.png?raw=true" width="550px">
</div>

- **封闭式表达式**：与由具有常数系数的线性递归定义的每个序列一样，斐波那契数具有封闭形式的表达式。它被称为比奈公式，以法国数学家雅克·菲利普·玛丽·比内命名，尽管亚伯拉罕·德·莫弗和丹尼尔·伯努利已经知道它。

## 三、散列函数分类

散列函数（英语：Hash function）又称散列算法、哈希函数，是一种将任意大小的数据映射到固定大小值的计算方式。散列函数计算结果被称为散列值、散列码，也就是对应的 HashMap 中哈希桶的索引以及数据库中库表的路由信息。

例如在 Java 中对数据的散列算法：HashMap 用到的是一次扰动函数下的哈希散列、ThreadLocal 用到的斐波那契散列。而通常数据库路由组件用到的是整数模除法散列，这也是实践中最简单和最常用的方法之一。

接下来就给大家介绍这几种常用的散列算法，其他更多散列可以参考 [HashFunction](https://en.wikipedia.org/wiki/Hash_function)

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/fibonacci-03.png?raw=true" width="750px">
</div>

### 1. 除法散列

在用来设计散列函数的除法散列法中，通过取 K 除以 M 的余数，将关键字 K 映射到 M 个槽中的某一个位置上，即散列函数为：h(K) = K mod M 表格大小通常是 2 的幂。

另外除法散列的一个显着缺点是除法在大多数现代架构（包括 x86）上都是微编程的，并且可能比乘法慢 10 倍。

### 2. 乘法散列

乘法散列法整体包含两步：

- 用关键字k乘上常数`A(0<A<1)`，并去除kA的小数部分
- 用m乘以这个值，再取结果的底`floor`
  公式： `h(K)=Math.floor[m(aK mod 1)]`

**步骤**：

- 假设某计算机的字长为 ww 位，而 kk 正好可容于一个字中`(k<2wk<2w)`
- 现在选取范围`[0，2w]`内的任意数值 ss，k×sk×s 即可用`R1·2w+R0R1·2w+R0`来表示
- 因此`(k·A)mod1=k·s/2w(k·A)mod1=k·s/2w`就是将`k×sk×s`整体向右平移 ww 位，此时R0R0即为小数部分
- 再乘以 2m2m 相当于左移 mm 位，散列值` h(k)h(k)` 为 R0R0 的前 m 位。

乘法散列只需要单个整数乘法和右移，使其成为计算速度最快的哈希函数之一。但乘法散列可能会在变更计算因子后，较高值的输入位不会影响较低值的输出位，问题体现在元素分散不均，不满足严格的雪崩标准。所以通常在会进行异或操作

乘法散列容易受到导致扩散不良的“常见错误”的影响——较高值的输入位不会影响较低值的输出位。在乘法步骤对此进行校正之前，输入上的变换将保留的最高位的跨度向下移动，并将它们异或或加到键上。所以在输入上的变换将保留的最高位的跨度向下移动，并将它们异或操作或者加到键上。例如 HashMap 的扰动函数。

### 3. 斐波那契散列

其实斐波那契散列是一种特殊形式的乘法散列，只不过它的乘法因子选择的是一个黄金分割比例值，所以叫做斐波那契散列。

斐波那契散列的特性在于将“大数映射到小数”的计算结果在表空间上是均匀分布的，且计算满足乘法散列效率高。**那为什么并不能使用它作为数据库路由算法呢？**

## 四、雪崩标准测试

在数据库路由实现方面，通常我们都是使用整数模除法散列求模的方式进行元素的索引计算。那既然乘法散列效率高，斐波那契散列分散均匀，为什么不使用这样的方式处理数据库路由算法呢？

在检索的资料中并没有一个专门的文章来说明这一事项，这也导致很多在学习过 HashMap、ThreadLocal 源码的研发人员尝试把这两种源码中的乘法散列算法搬到数据库路由算法中使用。在保证每次扩容数据库表都是2的次幂的情况下，并没有出现什么样的问题。那么对于这样情况下，是否隐藏着什么潜在的风险呢？

那么为了证实斐波那契散列是否可以用在数据库路由散列算法中，我们可以尝试使用 **严格雪崩标准(SAC)** 进行验证测试。

那么什么是**严格雪崩标准( SAC )** ，在密码学中，雪崩效应是密码算法的理想属性，通常是分组密码和密码散列函数，其中如果输入发生轻微变化（例如，翻转单个位），输出会发生显着变化（例如，50%输出位翻转）

SAC 建立在完整性和雪崩的概念之上，由 Webster 和 Tavares 于 1985 年引入。SAC 的高阶概括涉及多个输入位。满足最高阶 SAC 的最大非线性函数，也称为“完全非线性”函数。

简单来说，当我们对数据库从8库32表扩容到16库32表的时候，每一个表中的数据总量都应该以50%的数量进行减少。这样才是合理的。

好，那么接下来我们就来做下雪崩测试；

1. 准备10万个单词用作样本数据。
2. 对比测试除法散列、乘法散列、斐波那契散列。
3. 基于条件1、2，对数据通过不同的散列算法分两次路由到8库32表和16库32表中，验证每个区间内数据的变化数量，是否在50%左右。
4. 准备一个 excel 表，来做数据的统计计算。

**测试代码**

```java
public Map<Integer, Map<Integer, Integer>> hashFunction(int dbCount, int tbCount, Long hashIncrementVal, int hashType) {
    int size = dbCount * tbCount;
    System.out.print("库数：" + dbCount + " 表数：" + tbCount + " 总值：" + size + " 幂值：" + Math.log(size) / Math.log(2));
  
    int HASH_INCREMENT = (int) ((null == hashIncrementVal ? size : hashIncrementVal) * (Math.sqrt(5) - 1) / 2);
    System.out.print(" 黄金分割：" + HASH_INCREMENT + "/" + size + " = " + (double) HASH_INCREMENT / size);
  
    Map<Integer, Map<Integer, Integer>> map = new ConcurrentHashMap<>();
    Set<String> words = FileUtil.readWordList("/Users/fuzhengwei/1024/github/java-algorithms/logic/src/main/java/math/fibonacci/103976个英语单词库.txt");
    System.out.println(" 单词总数：" + words.size() + "\r\n");
  
    for (String word : words) {
        int idx = 0;
        switch (hashType) {
            // 散列：斐波那契散列  int idx = (size - 1) & (word.hashCode() * HASH_INCREMENT + HASH_INCREMENT);
            case 0:
                idx = (word.hashCode() * HASH_INCREMENT) & (size - 1);
                break;
            // 散列：哈希散列 + 扰动函数
            case 1:
                idx = (size - 1) & (word.hashCode() ^ (word.hashCode() >>> 16));
                break;
            // 散列：哈希散列
            case 2:
                idx = (size - 1) & (word.hashCode()/* ^ (word.hashCode() >>> 16)*/);
                break;
            // 散列：整数求模
            case 3:
                idx = Math.abs(word.hashCode()) % size;
                break;
        }
      
        // 计算路由索引
        int dbIdx = idx / tbCount + 1;
        int tbIdx = idx - tbCount * (dbIdx - 1);
      
        // 保存路由结果
        if (map.containsKey(dbIdx)) {
            Map<Integer, Integer> dbCountMap = map.get(dbIdx);
            if (dbCountMap.containsKey(tbIdx)) {
                dbCountMap.put(tbIdx, dbCountMap.get(tbIdx) + 1);
            } else {
                dbCountMap.put(tbIdx, 1);
            }
        } else {
            Map<Integer, Integer> dbCountMap = new HashMap<>();
            dbCountMap.put(tbIdx, 1);
            map.put(dbIdx, dbCountMap);
        }
    }
    return map;
}
```

- 整个方法的目的在于得出不同的哈希算法，对10万个单词散列到指定的分库分表中，所体现的结果。

### 1. 斐波那契散列

#### 1.1 最小黄金分割

斐波那契散列也是乘法散列的一种体现形式，只不过它选择了一个黄金分割点作为乘积因子。例如 ThreadLocal 中的 `0x61c88647`。但如果说我们只是按照一个指定范围长度内做黄金分割计算，并拿这个结果当成乘法散列的因子，那么10万单词将不会均匀的散列到8个库，32张表内。如图：

```java
@Test
public void test_hashFunction_0_hash_null() {
    Map<Integer, Map<Integer, Integer>> map = fibonacci.hashFunction(8, 32, null, 0);
    Set<Integer> keys = map.keySet();
    for (Integer key : keys) {
        Collection<Integer> values = map.get(key).values();
        for (Integer v : values) {
            System.out.print(v + " ");
        }
        System.out.println();
    }
}
```

```java
库数：8 表数：32 总值：256 幂值：8.0 黄金分割：2147483647/256 = 8388607.99609375 单词总数：103976
```

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/fibonacci-04.png?raw=true" width="950px">
</div>

- 如果你的斐波那契散列值是根据库表的值进行黄金切割的，那么在最初的库表范围较小的阶段，将有部分区域无法使用。这是因为得到的黄金分割点的二进制值没法覆盖整个区域，也就做不到合适的乘法散列计算。参考：[https://bugstack.cn/md/algorithm/logic/math/2022-10-30-bits.html](https://bugstack.cn/md/algorithm/logic/math/2022-10-30-bits.html) - 《程序员数学：位运算》

#### 1.2 最大黄金分割

基于最小黄金分割的计算，是没法做到均匀散列的。所以你看到的 ThreadLocal 默认就给你一个 `0x61c88647` 而不是随着扩容长度实时计算的切割值。好那么我们接下来也使用这个值来做计算，看看8库到16库后，数据的雪崩结果。

```java
@Test
public void test_hashFunction_0() {
    Map<Integer, Map<Integer, Integer>> map = fibonacci.hashFunction(8, 32, 1L << 32, 0);
    Set<Integer> keys = map.keySet();
    for (Integer key : keys) {
        Collection<Integer> values = map.get(key).values();
        for (Integer v : values) {
            System.out.print(v + " ");
        }
        System.out.println();
    }
}
```

- 分别测试 dbCount = 8、dbCount = 16

```java
库数：8 表数：32 总值：512 幂值：9.0 黄金分割：2147483647/512 = 4194303.998046875 单词总数：103976

库数：16 表数：32 总值：512 幂值：9.0 黄金分割：2147483647/512 = 4194303.998046875 单词总数：103976
```

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/fibonacci-05.png?raw=true" width="950px">
</div>

- 从8库扩到16库以后，满足50%数据变化的，只有2库2表和3库20表。其他数据变化都不满足严格的雪崩测试。

#### 1.3 任意扩容库表

通常情况下做分库分表会考虑到以后的扩容操作，那如果说按照2的次幂扩容第一次是8库32表，之后是16库32表，在之后32库32表。那么这样扩容下去，其实是扛不住的。所以大多数时候希望是从8库扩到9库，而不是一下翻倍。那我们来测试下9库32表，斐波那契散列的分散效果。

```java
    Map<Integer, Map<Integer, Integer>> map = fibonacci.hashFunction(9, 32, 1L << 32, 0);
    Set<Integer> keys = map.keySet();
    for (Integer key : keys) {
        Collection<Integer> values = map.get(key).values();
        for (Integer v : values) {
            System.out.print(v + " ");
        }
        System.out.println();
    }
}
```

```java
库数：9 表数：32 总值：512 幂值：9.0 黄金分割：2147483647/512 = 4194303.998046875 单词总数：103976
```

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/fibonacci-06.png?raw=true" width="950px">
</div>

- 因为9库不满足2的次幂，也就没法直接乘法散列。所以相当于斐波那契散列失效了。这如果是线上的生产环境，将发生灾难性的事故。

### 2. 整数求模散列

#### 2.1 基础散列计算

整数求模以数据库表总数为除数，与哈希值的绝对值进行除法散列计算。一般在数据库路由中非常常用。另外如果根据用户ID做散列路由，但由于ID长度波动范围较大，则可以按照指定长度统一切割后使用。

```java
@Test
public void test_hashFunction_3() {
    Map<Integer, Map<Integer, Integer>> map = fibonacci.hashFunction(8, 32, null, 3);
    Set<Integer> keys = map.keySet();
    for (Integer key : keys) {
        Collection<Integer> values = map.get(key).values();
        for (Integer v : values) {
            System.out.print(v + " ");
        }
        System.out.println();
    }
}
```

- 分别测试 dbCount = 8、dbCount = 16

```java
库数：8 表数：32 总值：512 幂值：9.0 黄金分割：2147483647/512 = 4194303.998046875 单词总数：103976

库数：16 表数：32 总值：512 幂值：9.0 黄金分割：2147483647/512 = 4194303.998046875 单词总数：103976
```

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/fibonacci-07.png?raw=true" width="950px">
</div>

- 在使用除法散列方式后，满足50%数据变化的有5个表。看着并不多，但这相当于是斐波那契散列下的3倍。同时其他表数据接近50%的也要大于斐波那契散列。

#### 2.2 任意扩容计算

接下来我们任意从8库扩容到9库，看看数据的变化。

```java
@Test
public void test_hashFunction_3() {
    Map<Integer, Map<Integer, Integer>> map = fibonacci.hashFunction(9, 32, null, 3);
    Set<Integer> keys = map.keySet();
    for (Integer key : keys) {
        Collection<Integer> values = map.get(key).values();
        for (Integer v : values) {
            System.out.print(v + " ");
        }
        System.out.println();
    }
}
```

```java
库数：9 表数：32 总值：512 幂值：9.0 黄金分割：2147483647/512 = 4194303.998046875 单词总数：103976
```

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/fibonacci-08.png?raw=true" width="950px">
</div>

- 103976 / (9 * 32) ≈ 361，那么也就说扩容后的数据，基本在361范围波动，就满足了均匀散列的目的。所以在数据库散列算法中，除法散列是较靠谱且稳定的。

## 五、常见面试题

- 散列算法有哪些种？
- HashMap、ThreadLocal、数据库路由都是用了什么散列算法？
- 乘法散列为什么要用2的幂值作为每次的扩容条件？
- 你有了解过 `0x61c88647` 是怎么计算的吗？
- 斐波那契散列的使用场景是什么？

---

- The Fibonacci Association：[https://en.wikipedia.org/wiki/The_Fibonacci_Association](https://en.wikipedia.org/wiki/The_Fibonacci_Association)
- 哈希函数：[https://en.wikipedia.org/wiki/Hash_function](https://en.wikipedia.org/wiki/Hash_function)
- 斐波那契数：[https://en.wikipedia.org/wiki/Fibonacci_number#Mathematics](https://en.wikipedia.org/wiki/Fibonacci_number#Mathematics)
- 散列函数：[https://zh.wikipedia.org/wiki/%E6%95%A3%E5%88%97%E5%87%BD%E6%95%B8](https://zh.wikipedia.org/wiki/%E6%95%A3%E5%88%97%E5%87%BD%E6%95%B8)
- 雪崩效应：[https://en.wikipedia.org/wiki/Avalanche_effect](https://en.wikipedia.org/wiki/Avalanche_effect)
- Fibonacci Hashing: The Optimization that the World Forgot (or: a Better Alternative to Integer Modulo)：[https://probablydance.com/2018/06/16/fibonacci-hashing-the-optimization-that-the-world-forgot-or-a-better-alternative-to-integer-modulo/](https://probablydance.com/2018/06/16/fibonacci-hashing-the-optimization-that-the-world-forgot-or-a-better-alternative-to-integer-modulo/)
- 斐波那契数：[https://en.wikipedia.org/wiki/Fibonacci_number#Relation_to_the_golden_ratio](https://en.wikipedia.org/wiki/Fibonacci_number#Relation_to_the_golden_ratio)
- C++ 中具有面向对象设计模式的数据结构和算法：[https://book.huihoo.com/data-structures-and-algorithms-with-object-oriented-design-patterns-in-c++/html/page214.html](https://book.huihoo.com/data-structures-and-algorithms-with-object-oriented-design-patterns-in-c++/html/page214.html)
