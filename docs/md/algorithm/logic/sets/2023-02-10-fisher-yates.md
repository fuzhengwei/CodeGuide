---
title: 洗牌算法
lock: need
---

# 《程序员数学：洗牌算法》- 随机置换有限序列

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>源码：[https://github.com/fuzhengwei/java-algorithms](https://github.com/fuzhengwei/java-algorithms)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`Fisher–Yates shuffle 的历史`

Fisher–Yates shuffle 的现代版本专为计算机使用而设计，由 Richard Durstenfeld 于 1964 年引入，并由Donald E. Knuth在计算机编程艺术中作为`算法 P（洗牌）`推广。Durstenfeld 的文章和 Knuth 的第一版计算机编程艺术都没有承认 Fisher 和 Yates 的工作；但在 Knuth 的《计算机编程艺术》的后续版本提到了 Fisher 和 Yates 的贡献。

## 二、使用场景

最早有诉求使用洗牌算法的场景是在业务需求开发中，需要对线上考试的题目和题目对应的答案进行乱序。也就是说在大家都需要作答10道题的时候，每个人看到的试卷题目是乱序的，并且题目的正确答案选项也被乱序了。

其实除此之外，洗牌算法也也可以用在一下场景；

1. 博彩：洗牌算法用于纸牌游戏和棋盘游戏，随机分配牌或棋子给玩家。
2. 随机抽样：随机抽样算法用于从较大的数据集中随机选择样本以用于测试或验证目的。
3. 数据混洗：混洗算法用于随机混洗数据集中的元素，例如在机器学习算法中。
4. 音乐播放列表：随机播放算法用于从播放列表中随机选择歌曲，以获得更多样化的聆听体验。
5. 随机密码生成：shuffle 算法用于通过从预定义的字符集中打乱字符来随机生成密码。
6. 加密应用程序：混洗算法用于密码学以出于安全目的生成元素的随机排列。
7. 彩票：随机抽取算法用于彩票中随机选择中奖号码或奖品。

## 三、算法描述

Fisher–Yates shuffle 是一种用于生成有限序列的随机排列的算法——简单来说，该算法对序列进行打乱。该算法有效地将所有元素放入帽子中；它通过从帽子中随机抽取一个元素来不断确定下一个元素，直到没有元素为止。该算法产生无偏排列：每个排列的可能性均等。该算法的现代版本是高效的：它花费的时间与被洗牌的项目数量成正比，并将它们洗牌到位。如图所示；

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/fisher-yates-01.png?raw=true" width="450px">
</div>

这个算法也是 Java 中 Collections.shuffle 对应源码实现，在 Java 中直接使用它就可以完成数组元素的洗牌。

## 四、算法实现

```java
public static int[] fisherYates(int[] originalArray) {
    int[] array = originalArray.clone();
    Random random = new Random();
    for (int i = array.length - 1; i > 0; i--) {
        int randomIndex = random.nextInt(i + 1);
        int temp = array[i];
        array[i] = array[randomIndex];
        array[randomIndex] = temp;
    }
    return array;
}
```

按照洗牌算法的描述做一下洗牌排序处理。其实实现起来还是比较简单的。

---

- [Fisher–Yates_shuffle](https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle)
- [Reservoir_sampling](https://en.wikipedia.org/wiki/Reservoir_sampling)

