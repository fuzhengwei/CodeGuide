---
title: 2次方数 IsPowerOfTwo
lock: need
---

# 《程序员数学：判断2次方数》—— 除法、二进制、对数，你会用哪种方式判断？

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>源码：[https://github.com/fuzhengwei/java-algorithms](https://github.com/fuzhengwei/java-algorithms)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`每一个知识的索引都可能会牵扯出一片的内容`

记得以前看到一个文章内容，说国外的小孩留一个很开放的作文题目，但如果想把这样一个作业写下来，则需要索引大量的资料才能完成。但在这个过程中其实会收获很多，也学会了如何学习。

其实像小傅哥去编写这样的《程序员数学》资料时，也会去横向和纵向的对比一些数学上的文章和内容，有的来自维基百科，有的来自于论文资料，现在还可以从 [chatGPT](https://chat.openai.com/chat) 中探索。

另外还有一份斯坦福大学的课程资料，小傅哥把它转成 PDF 有需要的伙伴可以下载学习使用。[《计算机课程资料 - 斯坦福大学 @肖恩·埃隆·安德森》](https://github.com/fuzhengwei/java-algorithms/blob/main/%E4%BD%9C%E8%80%85%EF%BC%9A%E8%82%96%E6%81%A9%C2%B7%E5%9F%83%E9%9A%86%C2%B7%E5%AE%89%E5%BE%B7%E6%A3%AE%20seander%40cs%E3%80%82%E6%96%AF%E5%9D%A6%E7%A6%8F%E5%A4%A7%E5%AD%A6.pdf)

## 二、判断2次方数

如果说判断一个数字是否满足2的次方，首先我会想到二进制，因为二进制的每一位都是2的次方数字。那么判断一个数字是否为2的次方可以从二进制中的数字特性下手，比如我们可以做二进制数字的判断，也就是一个数字的二进制必须只有1位为1，其他位都为0，那么这个数字就是2次方的数字。

🤔那除此之外还有其他手段吗？我们接下来就分别实现下

### 1. 整除法

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/is-power-of-two-01.png?raw=true" width="150px">
</div>

**代码实现**

```java
public boolean isPowerOfTwo01(int number) {
    if (number < 1) return false;
  
    int dividedNumber = number;
    while (dividedNumber != 1) {
        if (dividedNumber % 2 != 0) {
            return false;
        }
        dividedNumber = dividedNumber / 2;
    }
  
    return true;
}
```

- 循环数字除以2的结果与2求模计算看余数是否为0，只要有一次为0，那么就不是2的次方数。

### 2. 二进制

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/is-power-of-two-02.png?raw=true" width="350px">
</div>

**代码实现**

```java
public boolean isPowerOfTwo02(int number) {
    if (number < 1) return false;
    return (number & (number - 1)) == 0;
}
```

- 在斯坦福大学的计算资料中，有这么一条关于判断2的次方数的内容；`f = (v & (v - 1)) == 0;` 错位进行 & 与运算，结果为0则是2次方数【过程如图】。
- 此外这里我们过滤了小于的数字，如果不过滤则需要使用；`f = v && !(v & (v - 1));`

### 3. 求对数

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/is-power-of-two-03.png?raw=true" width="350px">
</div>

**代码实现**

```java
public boolean isPowerOfTwo03(int number) {
    if (number == 0)
        return false;
    // log8 = log2^3 / log2 = 3
    double x = Math.log(number) / Math.log(2);
    // 向上和向下取整的结果判断
    return (int)(Math.ceil(x)) == (int)(Math.floor(x));
}
```

- 此方式为计算2为底数的对数，如果得到的数字向上和向下取整结果一致，那么则是2的次方。
- 这里做一个简单的推到，加入 number = 8，那么 log8 = log2^3 也就是用 log2^3 与 log2 做对比。这样就能看出来一个结果3，这个3是一个整数数字，则这个 number 也就是2次方数。

### 4. 位计算

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/is-power-of-two-04.png?raw=true" width="350px">
</div>

**代码实现**

```java
public boolean isPowerOfTwo04(int number){
    int cnt = 0;
    while (number > 0) {
        if ((number & 1) == 1) {
            cnt++;
        }
        number = number >> 1;
    }
    return cnt == 1;
}
```

- 这个其实就是最开始说的，如果一个数字满足2次方，那么只要在二进制的转换中，它只有1位是1就可以了。

## 三、单元测试

```java
@Test
public void test_IsPowerOfTwo(){
    IsPowerOfTwo isPowerOfTwo = new IsPowerOfTwo();
    System.out.println(isPowerOfTwo.isPowerOfTwo01(8));
    System.out.println(isPowerOfTwo.isPowerOfTwo02(8));
    System.out.println(isPowerOfTwo.isPowerOfTwo03(163));
    System.out.println(isPowerOfTwo.isPowerOfTwo04(12));
}

@Test
public void test_math(){
    System.out.println(Math.ceil(Math.log(8) / Math.log(2)));
    System.out.println(Math.log(1));
    System.out.println(Math.E);
    System.out.println(Math.pow(Math.E, Math.log(2)));
}
```

- 在单元测试中除了验证4种判断2次方数的计算方式，也提供了关于log的计算，默认log是基于指数E的计算。读者也可以进行测试验证。a 的 x 次方 = N 那么 x = log(a)N

## 四、常见面试题

- 如何判断一个数字是2的次方数
- 在Java中怎么计算log公式