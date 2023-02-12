---
title: 组合
lock: need
---

# 《程序员数学：组合》- 有/无重复

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>源码：[https://github.com/fuzhengwei/java-algorithms](https://github.com/fuzhengwei/java-algorithms)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

与排列相对应的同类的会有组合数数学知识，就像双色球组合能有多少种，中奖概率是多少。同时对于数字是否可以重复使用，还包括重复组合和不重复组合。

举例；

**不重复组合**公式：

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/combinations-03.png?raw=true" width="150px">
</div>

如彩票号码`(2,14,15,27,30,33)` ，哪里`n`是可供选择的东西的数量，我们从中选择`r`，没有重复，顺序无所谓。常称为“n选r”（如“16选3”）。也称为二项式系数。

**可重复组合**公式：

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/combinations-04.png?raw=true" width="150px">
</div>

比如口袋里的硬币`(5,5,5,10,10)`

或者假设冰淇淋有五种口味 ：`banana`、`chocolate`、`lemon`和。`strawberry``vanilla`

我们可以吃三勺。会有多少变化？

让我们用字母来表示口味：`{b, c, l, s, v}`。示例选择包括：

- `{c, c, c}`（3 勺巧克力）
- `{b, l, v}`（香蕉、柠檬和香草各一粒）
- `{b, v, v}`（一根香蕉，两根香草）

`n`有多少东西可供选择，我们从中选择`r`。允许重复，顺序无关紧要。

## 二、实现

### 1. 不重复组合

```java
public static List<List<String>> combineWithRepetitions(List<String> comboOptions, int comboLength) {
    // If the length of the combination is 1 then each element of the original array
    // is a combination itself.
    if (comboLength == 1) {
        List<List<String>> combos = new ArrayList<>();
        for (String comboOption : comboOptions) {
            List<String> combo = new ArrayList<>();
            combo.add(comboOption);
            combos.add(combo);
        }
        return combos;
    }
    // Init combinations array.
    List<List<String>> combos = new ArrayList<>();
    // Remember characters one by one and concatenate them to combinations of smaller lengths.
    // We don't extract elements here because the repetitions are allowed.
    for (int optionIndex = 0; optionIndex < comboOptions.size(); optionIndex++) {
        // Generate combinations of smaller size.
        String currentOption = comboOptions.get(optionIndex);
        List<String> remainingOptions = new ArrayList<>(comboOptions.subList(optionIndex, comboOptions.size()));
        List<List<String>> smallerCombos = combineWithRepetitions(remainingOptions, comboLength - 1);
        // Concatenate currentOption with all combinations of smaller size.
        for (List<String> smallerCombo : smallerCombos) {
            List<String> combo = new ArrayList<>();
            combo.add(currentOption);
            combo.addAll(smallerCombo);
            combos.add(combo);
        }
    }
    return combos;
}
```

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/combinations-01.jpeg?raw=true" width="450px">
</div>

此代码是一个 Java 函数，它从允许重复的元素列表中生成给定长度的所有可能组合。
该函数有两个输入：

1. `comboOptions`：生成组合的元素列表。 2. `comboLength`：要生成的每个组合的长度。
该函数返回一个字符串列表列表，其中每个内部列表代表一个组合。
该函数的逻辑如下：
2. 如果`comboLength`等于 1，则`comboOptions`列表中的每个元素本身就是一个组合并添加到`combos`列表中。 2. 如果`comboLength`大于 1，则该函数使用相同的函数生成更小尺寸的组合。对于列表中的每个元素，该函数通过使用当前选项之后的剩余选项调用自身来`comboOptions`生成元素组合。`comboLength - 1` 3. 最后，该函数将当前选项与每个较小的组合连接起来，并将结果添加到`combos`列表中。

### 2. 可重复组合

```java
public static List<List<String>> combineWithoutRepetitions(String[] comboOptions, int comboLength) {
    List<List<String>> combos = new ArrayList<>();
    if (comboLength == 1) {
        for (String comboOption : comboOptions) {
            List<String> singleOption = new ArrayList<>();
            singleOption.add(comboOption);
            combos.add(singleOption);
        }
        return combos;
    }
    for (int i = 0; i < comboOptions.length; i++) {
        String currentOption = comboOptions[i];
        String[] smallerOptions = new String[comboOptions.length - i - 1];
        System.arraycopy(comboOptions, i + 1, smallerOptions, 0, comboOptions.length - i - 1);
        List<List<String>> smallerCombos = combineWithoutRepetitions(smallerOptions, comboLength - 1);
        for (List<String> smallerCombo : smallerCombos) {
            List<String> newCombo = new ArrayList<>();
            newCombo.add(currentOption);
            newCombo.addAll(smallerCombo);
            combos.add(newCombo);
        }
    }
    return combos;
}
```

<div align="center">
    <img src="https://bugstack.cn/images/article/algorithm/logic/combinations-02.jpeg?raw=true" width="450px">
</div>

这段代码是一个生成不含重复元素的组合的函数。

1. 定义一个名为 "combos" 的列表，用于存储生成的组合。
2. 如果 "comboLength" 等于 1，则对于 "comboOptions" 数组中的每一个元素，将其单独作为一个列表存入 "combos" 列表中。最后返回 "combos" 列表。
3. 否则，对于 "comboOptions" 数组中的每一个元素，枚举它并作为组合的第一个元素，递归地调用该函数生成长度减 1 的组合。将枚举的元素加入生成的组合中，并将新生成的组合加入 "combos" 列表中。
4. 最后返回 "combos" 列表。

## 三、测试

```java
@Test
public void test_combineWithRepetitions() {
    List<String> comboOptions = new ArrayList<>();
    comboOptions.add("1");
    comboOptions.add("2");
    comboOptions.add("3");
    List<List<String>> lists = Combinations.combineWithRepetitions(comboOptions, 2);
    for (List<String> list : lists) {
        System.out.println(JSON.toJSONString(list));
    }
}

@Test
public void test_combineWithoutRepetitions() {
    String[] comboOptions = {"1", "2", "3"};
    List<List<String>> lists = Combinations.combineWithoutRepetitions(comboOptions, 2);
    for (List<String> list : lists) {
        System.out.println(JSON.toJSONString(list));
    }
}
```

**测试结果**

```java
["1","1"]
["1","2"]
["1","3"]
["2","2"]
["2","3"]
["3","3"]

Process finished with exit code 0
```

---

[https://www.mathsisfun.com/combinatorics/combinations-permutations.html](https://www.mathsisfun.com/combinatorics/combinations-permutations.html)