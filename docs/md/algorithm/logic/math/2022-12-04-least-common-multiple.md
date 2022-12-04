---
title: 最小公倍数 LastCommonMultiple
lock: need
---

# 《程序员数学：最小公倍数》—— 你能用几种代码实现出计算公倍数？

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>源码：[https://github.com/fuzhengwei/java-algorithms](https://github.com/fuzhengwei/java-algorithms)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

在 [stackoverflow.com](https://stackoverflow.com/questions/3154454/what-is-the-most-efficient-way-to-calculate-the-least-common-multiple-of-two-int) 看到一道问题：**计算两个整数的最小公倍数的最有效方法是什么？**

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/least-common-multiple-01.png?raw=true" width="700px">
</div>

乍一看，🤨 这能有啥。不就是计算下最小公倍数吗？但一想我脑袋中计算最小公倍数的方法；一种是在本子上通过[短除法](https://zh.wikipedia.org/wiki/%E7%9F%AD%E9%99%A4%E6%B3%95)计算，另外一种是基于计算出的最大公约数，再使用公式：`lcm(a, b) = |a * b| / gcd(a, b)` 求得最小公倍数。—— 计算最大公约数是基于[欧几里德算法(辗转相除法)](https://bugstack.cn/md/algorithm/logic/math/2022-11-28-euclidean.html)

那么这样的计算方法是不是最有效的方法，另外如果是同时计算多个整数的最小公倍数，要怎么处理？

其实编程的学习往往就是这样，留心处处都是学问，你总是需要从各种细小的点中，积累自己的技术思维广度和纵向探索深度。好啦，接下来小傅哥就给大家介绍几种用于计算最小公倍数的算法。

## 二、用公约数实现

公式：`lcm(a, b) = |a * b| / gcd(a, b)`

```java
public long lcm01(long m, long n) {
    return ((m == 0) || (n == 0)) ? 0 : Math.abs(m * n) / gcd(m, n);
}

private long gcd(long m, long n) {
    m = Math.abs(m);
    n = Math.abs(n);
    // 从一个数字中减去另一个数字，直到两个数字变得相同。
    // 这将是 GCD。如果其中一个数字为零，也退出循环。
    // https://en.wikipedia.org/wiki/Euclidean_algorithm
    while (m != 0 && n != 0 && m != n) {
        if (m > n) {
            m = m - n;
        } else {
            n = n - m;
        }
    }
    return m == 0 ? n : m;
}
```

- 首先这里是一个比较简单的方式，基于两数乘积除以最大公约数，得到的结果就是最小公倍数。

## 三、简单累加计算

此计算方式为，在一组正整数数列中，通过找到最小的数字进行自身累加循环，直至所有数字相同时，则这个数字为最小公倍数。—— 你能代码实现一下吗？

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/least-common-multiple-02.png?raw=true" width="400px">
</div>

```java
public long lcm02(long... n) {
    long[] cache = n.clone();
    // 以所有数字都相等作为条件
    while (!isEquals(n)) {
        System.out.println(JSON.toJSONString(n));
        long min = n[0];
        int idx = 0;
        for (int i = 0; i < n.length; i++) {
            if (min > n[i]) {
                min = n[i];
                idx = i;
            }
        }
        n[idx] = cache[idx] + min;
    }
    return n[0];
}
```

- 在代码实现中，首先要把n个整数数列进行克隆保存。因为每次相加的都是最初的这个数列里的数字值。接下来就是以所有数字都相等作为条件循环判断，不断地的累加最小的数值即可。最终返回的就是最小公倍数。

## 四、表格推演计算

表格计算方式为将一组数字以最小的质数2开始整除，直到不能被2整除后，用下一个质数3继续整除（剩余的数字中比大的最小的质数）直至所有数字都为1的时候结束。最终所有有效的质数乘积就是最小公倍数。—— 想想如果这让你用代码实现，你能肝出来吗？

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/least-common-multiple-03.png?raw=true" width="400px">
</div>

```java
public long lcm03(long... n) {
    Map<Long, List<Long>> keys = new HashMap<>();
    for (long key : n) {
        keys.put(key, new ArrayList<Long>() {{
            add(key);
        }});
    }
    System.out.print("执行表格计算：\r\nx ");
    long primality = 2, cachePrimality = primality, filterCount = 0, lcm = 1;
    // 以所有元素最后一位为1作为条件
    while (filterCount != keys.size()) {
        int refresh = 0;
        filterCount = 0;
        for (Map.Entry<Long, List<Long>> entry : keys.entrySet()) {
            long value = entry.getValue().get(entry.getValue().size() - 1);
            if (value == 1) {
                filterCount++;
            }
            // 整除处理
            if (value % primality == 0) {
                entry.getValue().add(value / primality);
                refresh++;
            } else {
                entry.getValue().add(value);
            }
        }
        // 刷新除数
        if (refresh == 0) {
            for (Map.Entry<Long, List<Long>> entry : keys.entrySet()) {
                long value = entry.getValue().get(entry.getValue().size() - 1);
                // 找到下一个符合的素数
                if (value > primality || (value < cachePrimality && value > primality)) {
                    cachePrimality = value;
                }
                entry.getValue().remove(entry.getValue().size() - 1);
            }
            primality = cachePrimality;
        } else {
            // 累计乘积
            lcm *= cachePrimality;
            System.out.print(cachePrimality + " ");
        }
    }
    keys.forEach((key, values) -> {
        System.out.println();
        for (long v : values) {
            System.out.print(v + " ");
        }
    });
    System.out.println("\r\n");
    return lcm;
}
```

- 在代码实现中我们通过 Map 作为表的key，Map 中的 List 作为表每一行数据。通过这样一个结构构建出一张表。
- 接下来以所有元素最后一位为1作为条件循环处理数据，用最开始的2作为素数整除列表中的数据，并保存到下一组数列中。当2不能整除时，则刷新素数，选取另外一个列表中最小的素数作为除数继续。
- 这个过程中会累计有效素数的乘积，这个乘积的最终结果就是最小公倍数。

## 五、测试验证

**单元测试**

```java
@Test
public void test_euclidean() {
    LastCommonMultiple lastCommonMultiple = new LastCommonMultiple();
    // System.out.println("最小公倍数：" + lastCommonMultiple.lcm01(2, 7));
    System.out.println("最小公倍数：" + lastCommonMultiple.lcm02(3, 4, 6));
    // System.out.println("最小公倍数：" + lastCommonMultiple.lcm03(3, 4, 6));
     System.out.println("最小公倍数：" + lastCommonMultiple.lcm03(3, 4, 6, 8));
   //System.out.println("最小公倍数：" + lastCommonMultiple.lcm03(4, 7, 12, 21, 42));
}
```

**测试结果**

```java
执行累加计算：
[3,4,6]
[6,4,6]
[6,8,6]
[9,8,6]
[9,8,12]
[9,12,12]
最小公倍数：12

执行表格计算：
x 2 2 2 3 
3 3 3 3 1 
4 2 1 1 1 
6 3 3 3 1 
8 4 2 1 1 

最小公倍数：24
```

- 到这里测试就结束了，本章一共介绍了三种计算最小公倍数的方法。那如果只让你看到逻辑，你能写出最终的代码吗？

## 六、常见面试

- 如何计算两数的最小公倍数？
- 如果计算多个整数的最小公倍数？
- 你能说一下具体如何实现这种X的计算流程吗？
- 你知道最小公倍数计算的用途吗？

---

- What is the most efficient way to calculate the least common multiple of two integers?：[https://stackoverflow.com/questions/3154454/what-is-the-most-efficient-way-to-calculate-the-least-common-multiple-of-two-int/3154503#3154503](https://stackoverflow.com/questions/3154454/what-is-the-most-efficient-way-to-calculate-the-least-common-multiple-of-two-int/3154503#3154503)
- Least common multiple：[https://en.wikipedia.org/wiki/Least_common_multiple](https://en.wikipedia.org/wiki/Least_common_multiple)
- Chebyshev function：[https://en.wikipedia.org/wiki/Chebyshev_function](https://en.wikipedia.org/wiki/Chebyshev_function)
