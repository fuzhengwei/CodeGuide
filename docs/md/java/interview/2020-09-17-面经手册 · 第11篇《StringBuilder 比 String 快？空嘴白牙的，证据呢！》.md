---
layout: post
category: interview
title: 面经手册 · 第11篇《StringBuilder 比 String 快？空嘴白牙的，证据呢！》
tagline: by 小傅哥
tag: [java,interview]
excerpt: 面我的题开发都用不到，你为什么要问？可能这是大部分程序员求职时的经历，甚至也是大家讨厌和烦躁的点。明明给的是拧螺丝的钱、明明做的是写CRUD的码、明明担的是成工具的人！明明... 有很多，可明明公司不会招5年开发做3年经验的事、明明公司也更喜欢具有附加价值的研发。
lock: need
---

# 面经手册 · 第11篇《StringBuilder 比 String 快？空嘴白牙的，证据呢！》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`聊的是八股的文，干的是搬砖的活！`

面我的题开发都用不到，你为什么要问？可能这是大部分程序员求职时的经历，甚至也是大家讨厌和烦躁的点。明明给的是拧螺丝的钱、明明做的是写CRUD的事、明明担的是成工具的人！

**明明... 有很多**，可明明公司不会招5年开发做3年经验的事、明明公司也更喜欢具有附加价值的研发。有些小公司不好说，但在一些互联网大厂中，我们都希望招聘到具有培养价值的，也更喜欢能快速打怪升级的，也更愿意让这样的人承担更大的职责。

**但，你酸了！** 别人看源码你打游戏、别人学算法你刷某音、别人写博客你浪98。`所以，没有把时间用到个人成长上，就一直会被别人榨取。`

## 二、面试题

`谢飞机`，总感觉自己有技术瓶颈、有知识盲区，但是又不知道在哪。所以约面试官聊天，虽然也面不过去！

**面试官**：飞机，你又抱着大脸，来白嫖我了啦？

**谢飞机**：嘿嘿，我需要知识，我渴。

**面试官**：好，那今天聊聊最常用的 `String` 吧，你怎么初始化一个字符串类型。

**谢飞机**：`String str = "abc"; `

**面试官**：还有吗？

**谢飞机**：还有？啊，这样 `String str = new String("abc");` 😄

**面试官**：还有吗？

**谢飞机**：啊！？还有！不知道了！

**面试官**：你不懂 `String`，你没看过源码。还可以这样；`new String(new char[]{'c', 'd'});` 回家再学学吧，下次记得给我买*百事*，我不喝*可口*。

## 三、StringBuilder 比 String 快吗？

### 1. StringBuilder 比 String 快，证据呢？

老子代码一把梭，总有人絮叨这么搞不好，那 `StringBuilder` 到底那快了！

#### 1.1 String

```java
long startTime = System.currentTimeMillis();
String str = "";
for (int i = 0; i < 1000000; i++) {
    str += i;
}
System.out.println("String 耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
```

#### 1.2 StringBuilder

```java
long startTime = System.currentTimeMillis();
StringBuilder str = new StringBuilder();
for (int i = 0; i < 1000000; i++) {
    str.append(i);
}
System.out.println("StringBuilder 耗时" + (System.currentTimeMillis() - startTime) + "毫秒");
```

#### 1.3 StringBuffer

```java
long startTime = System.currentTimeMillis();
StringBuffer str = new StringBuffer();
for (int i = 0; i < 1000000; i++) {
    str.append(i);
}
System.out.println("StringBuffer 耗时" + (System.currentTimeMillis() - startTime) + "毫秒");
```

***

**综上**，分别使用了 `String`、`StringBuilder`、`StringBuffer`，做字符串链接操作(*100个、1000个、1万个、10万个、100万个*)，记录每种方式的耗时。最终汇总图表如下；

![小傅哥 & 耗时对比](https://bugstack.cn/assets/images/2020/interview/interview-12-01.png)

从上图可以得出以下结论；
1. `String` 字符串链接是耗时的，尤其数据量大的时候，简直没法使用了。*这是做实验，基本也不会有人这么干！*
2. `StringBuilder`、`StringBuffer`，因为没有发生多线程竞争也就没有🔒锁升级，所以两个类耗时几乎相同，当然在单线程下更推荐使用 `StringBuilder` 。

### 2. StringBuilder 比 String 快， 为什么？

```java
String str = "";
for (int i = 0; i < 10000; i++) {
    str += i;
}
```

这段代码就是三种字符串拼接方式，最慢的一种。不是说这种`+`加的符号，会被优化成 `StringBuilder` 吗，那怎么还慢？

确实会被JVM编译期优化，但优化成什么样子了呢，先看下字节码指令；`javap -c ApiTest.class`

![小傅哥 & 反编译](https://bugstack.cn/assets/images/2020/interview/interview-12-02.png)

一看指令码，这不是在循环里(*if_icmpgt*)给我 `new` 了 `StringBuilder` 了吗，怎么还这么慢呢？再仔细看，其实你会发现，这new是在循环里吗呀，我们把这段代码写出来再看看；

```java
String str = "";
for (int i = 0; i < 10000; i++) {
    str = new StringBuilder().append(str).append(i).toString();
}
```

现在再看这段代码就很清晰了，所有的字符串链接操作，都需要实例化一次`StringBuilder`，所以非常耗时。**并且你可以验证，这样写代码耗时与字符串直接链接是一样的。** 所以把`StringBuilder` 提到上一层 `for` 循环外更快。

## 四、String 源码分析

```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
    /** The value is used for character storage. */
    private final char value[];

    /** Cache the hash code for the string */
    private int hash; // Default to 0

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -6849794470754667710L;
 	
    ...
}
````

### 1. 初始化

在与 `谢飞机` 的面试题中，我们聊到了 `String` 初始化的问题，按照一般我们应用的频次上，能想到的只有直接赋值，`String str = "abc"; `，但因为 String 的底层数据结构是数组`char value[]`，所以它的初始化方式也会有很多跟数组相关的，如下；

```java
String str_01 = "abc";
System.out.println("默认方式：" + str_01);

String str_02 = new String(new char[]{'a', 'b', 'c'});
System.out.println("char方式：" + str_02);

String str_03 = new String(new int[]{0x61, 0x62, 0x63}, 0, 3);
System.out.println("int方式：" + str_03);

String str_04 = new String(new byte[]{0x61, 0x62, 0x63});
System.out.println("byte方式：" + str_04);
```

以上这些方式都可以初始化，并且最终的结果是一致的，`abc`。如果说初始化的方式没用让你感受到它是数据结构，那么`str_01.charAt(0);`呢，只要你往源码里一点，就会发现它是 `O(1)` 的时间复杂度从数组中获取元素，所以效率也是非常高，源码如下；

```java
public char charAt(int index) {
    if ((index < 0) || (index >= value.length)) {
        throw new StringIndexOutOfBoundsException(index);
    }
    return value[index];
}
```

### 2. 不可变(final)

字符串创建后是不可变的，你看到的`+加号`连接操作，都是创建了新的对象把数据存放过去，通过源码就可以看到；

![小傅哥 & String 不可变](https://bugstack.cn/assets/images/2020/interview/interview-12-03.png)

从源码中可以看到，`String` 的类和用于存放字符串的方法都用了 `final` 修饰，也就是创建了以后，这些都是不可变的。

**举个例子**

```java
String str_01 = "abc";
String str_02 = "abc" + "def";
String str_03 = str_01 + "def";
```

不考虑其他情况，对于程序初始化。以上这些代码 `str_01`、`str_02`、`str_03`，都会初始化几个对象呢？*其实这个初始化几个对象从侧面就是反应对象是否可变性。*

接下来我们把上面代码反编译，通过指令码看到底创建了几个对象。

**反编译下**

```java
  public void test_00();
    Code:
       0: ldc           #2                  // String abc
       2: astore_1
       3: ldc           #3                  // String abcdef
       5: astore_2
       6: new           #4                  // class java/lang/StringBuilder
       9: dup
      10: invokespecial #5                  // Method java/lang/StringBuilder."<init>":()V
      13: aload_1
      14: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      17: ldc           #7                  // String def
      19: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      22: invokevirtual #8                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
      25: astore_3
      26: return
```

- `str_01 = "abc"`，指令码：`0: ldc`，创建了一个对象。
- `str_02 = "abc" + "def"`，指令码：`3: ldc // String abcdef`，得益于JVM编译期的优化，两个字符串会进行相连，创建一个对象存储。
- `str_03 = str_01 + "def"`，指令码：`invokevirtual`，这个就不一样了，它需要把两个字符串相连，会创建`StringBuilder`对象，直至最后`toString:()`操作，共创建了三个对象。

**所以**，我们看到，字符串的创建是不能被修改的，相连操作会创建出新对象。

### 3. intern()

#### 3.1 经典题目

```java
String str_1 = new String("ab");
String str_2 = new String("ab");
String str_3 = "ab";

System.out.println(str_1 == str_2);
System.out.println(str_1 == str_2.intern());
System.out.println(str_1.intern() == str_2.intern());
System.out.println(str_1 == str_3);
System.out.println(str_1.intern() == str_3);
```

这是一道经典的 `String` 字符串面试题，乍一看可能还会有点晕。答案如下；

```java
false
false
true
false
true
```

#### 3.2 源码分析

看了答案有点感觉了吗，其实可能你了解方法 `intern()`，这里先看下它的源码；

```java
/**
 * Returns a canonical representation for the string object.
 * <p>
 * A pool of strings, initially empty, is maintained privately by the
 * class {@code String}.
 * <p>
 * When the intern method is invoked, if the pool already contains a
 * string equal to this {@code String} object as determined by
 * the {@link #equals(Object)} method, then the string from the pool is
 * returned. Otherwise, this {@code String} object is added to the
 * pool and a reference to this {@code String} object is returned.
 * <p>
 * It follows that for any two strings {@code s} and {@code t},
 * {@code s.intern() == t.intern()} is {@code true}
 * if and only if {@code s.equals(t)} is {@code true}.
 * <p>
 * All literal strings and string-valued constant expressions are
 * interned. String literals are defined in section 3.10.5 of the
 * <cite>The Java&trade; Language Specification</cite>.
 *
 * @return  a string that has the same contents as this string, but is
 *          guaranteed to be from a pool of unique strings.
 */
public native String intern();
```

这段代码和注释什么意思呢？

**native**，说明 `intern()` 是一个本地方法，底层通过JNI调用C++语言编写的功能。

**\openjdk8\jdk\src\share\native\java\lang\String.c**

```c++
Java_java_lang_String_intern(JNIEnv *env, jobject this)  
{  
    return JVM_InternString(env, this);  
}  

oop result = StringTable::intern(string, CHECK_NULL);

oop StringTable::intern(Handle string_or_null, jchar* name,  
                        int len, TRAPS) {  
  unsigned int hashValue = java_lang_String::hash_string(name, len);  
  int index = the_table()->hash_to_index(hashValue);  
  oop string = the_table()->lookup(index, name, len, hashValue);  
  if (string != NULL) return string;   
  return the_table()->basic_add(index, string_or_null, name, len,  
                                hashValue, CHECK_NULL);  
}  
````

- 代码块有点长这里只截取了部分内容，源码可以学习开源jdk代码，连接： [https://codeload.github.com/abhijangda/OpenJDK8/zip/master](https://codeload.github.com/abhijangda/OpenJDK8/zip/master)
- C++这段代码有点像HashMap的哈希桶+链表的数据结构，用来存放字符串，所以如果哈希值冲突严重，就会导致链表过长。这在我们讲解hashMap中已经介绍，可以回看 [HashMap源码](https://bugstack.cn/interview/2020/08/13/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC4%E7%AF%87-HashMap%E6%95%B0%E6%8D%AE%E6%8F%92%E5%85%A5-%E6%9F%A5%E6%89%BE-%E5%88%A0%E9%99%A4-%E9%81%8D%E5%8E%86-%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.html)
- StringTable 是一个固定长度的数组 `1009` 个大小，jdk1.6不可调、jdk1.7可以设置`-XX:StringTableSize`，按需调整。

#### 3.3 问题图解

![小傅哥 & 图解true/false](https://bugstack.cn/assets/images/2020/interview/interview-12-04.png)

看图说话，如下；

1. 先说 `==`，基础类型比对的是值，引用类型比对的是地址。另外，equal 比对的是哈希值。
2. 两个new出来的对象，地址肯定不同，所以是false。
3. intern()，直接把值推进了常量池，所以两个对象都做了 `intern()` 操作后，比对是常量池里的值。
4. `str_3 = "ab"`，赋值，JVM编译器做了优化，不会重新创建对象，直接引用常量池里的值。所以`str_1.intern() == str_3`，比对结果是true。

理解了这个结构，根本不需要死记硬背应对面试，让懂了就是真的懂，大脑也会跟着愉悦。

## 五、StringBuilder 源码分析

### 1. 初始化

```java
new StringBuilder();
new StringBuilder(16);
new StringBuilder("abc");
```

这几种方式都可以初始化，你可以传一个初始化容量，也可以初始化一个默认的字符串。它的源码如下；

```java
public StringBuilder() {
    super(16);
}

AbstractStringBuilder(int capacity) {
    value = new char[capacity];
}
```

定睛一看，这就是在初始化数组呀！那是不操作起来跟使用 `ArrayList` 似的呀！

### 2. 添加元素

```java
stringBuilder.append("a");
stringBuilder.append("b");
stringBuilder.append("c");
```

添加元素的操作很简单，使用 `append` 即可，那么它是怎么往数组中存放的呢，需要扩容吗？

#### 2.1 入口方法

```java
public AbstractStringBuilder append(String str) {
    if (str == null)
        return appendNull();
    int len = str.length();
    ensureCapacityInternal(count + len);
    str.getChars(0, len, value, count);
    count += len;
    return this;
}
```

- 这个是 `public final class StringBuilder extends AbstractStringBuilder`，的父类与 `StringBuffer` 共用这个方法。
- 这里包括了容量检测、元素拷贝、记录 `count` 数量。

#### 2.2 扩容操作

**ensureCapacityInternal(count + len);**

```java
/**
 * This method has the same contract as ensureCapacity, but is
 * never synchronized.
 */
private void ensureCapacityInternal(int minimumCapacity) {
    // overflow-conscious code
    if (minimumCapacity - value.length > 0)
        expandCapacity(minimumCapacity);
}

/**
 * This implements the expansion semantics of ensureCapacity with no
 * size check or synchronization.
 */
void expandCapacity(int minimumCapacity) {
    int newCapacity = value.length * 2 + 2;
    if (newCapacity - minimumCapacity < 0)
        newCapacity = minimumCapacity;
    if (newCapacity < 0) {
        if (minimumCapacity < 0) // overflow
            throw new OutOfMemoryError();
        newCapacity = Integer.MAX_VALUE;
    }
    value = Arrays.copyOf(value, newCapacity);
}
```

如上，`StringBuilder`，就跟操作数组的原理一样，都需要检测容量大小，按需扩容。扩容的容量是 n * 2 + 2，另外把原有元素拷贝到新新数组中。

#### 2.3 填充元素

**str.getChars(0, len, value, count);**

```java
public void getChars(int srcBegin, int srcEnd, char dst[], int dstBegin) {
    // ...
    System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
}
```

添加元素的方式是基于 `System.arraycopy` 拷贝操作进行的，这是一个本地方法。

#### 2.4 toString()

既然 `stringBuilder` 是数组，那么它是怎么转换成字符串的呢？

`stringBuilder.toString();`

```java
@Override
public String toString() {
    // Create a copy, don't share the array
    return new String(value, 0, count);
}
```

其实需要用到它是 `String` 字符串的时候，就是使用 `String` 的构造函数传递数组进行转换的，这个方法在我们上面讲解 `String` 的时候已经介绍过。

## 六、StringBuffer 源码分析

`StringBuffer` 与 `StringBuilder`，API的使用和底层实现上基本一致，维度不同的是 `StringBuffer` 加了 `synchronized` 🔒锁，所以它是线程安全的。源码如下；

```java
@Override
public synchronized StringBuffer append(String str) {
    toStringCache = null;
    super.append(str);
    return this;
}
```

那么，`synchronized` 不是重量级锁吗，JVM对它有什么优化呢？

其实为了减少获得锁与释放锁带来的性能损耗，从而引入了偏向锁、轻量级锁、重量级锁来进行优化，它的进行一个锁升级，如下图(此图引自互联网用户：**韭韭韭韭菜**，画的非常优秀)；

![小傅哥 & 此图引自互联网，画的非常漂亮](https://bugstack.cn/assets/images/2020/interview/interview-12-05.png)

1. 从无锁状态开始，当线程进入 `synchronized` 同步代码块，会检查对象头和栈帧内是否有当前线下ID编号，无则使用 `CAS` 替换。
2. 解锁时，会使用 `CAS` 将 `Displaced Mark Word` 替换回到对象头，如果成功，则表示竞争没有发生，反之则表示当前锁存在竞争锁就会升级成重量级锁。
3. 另外，大多数情况下锁🔒是不发生竞争的，基本由一个线程持有。所以，为了避免获得锁与释放锁带来的性能损耗，所以引入锁升级，升级后不能降级。

## 七、常用API

| 序号 | 方法                             | 描述                                                         |
| ---- | -------------------------------- | ------------------------------------------------------------ |
| 1    | `str.concat("cde")`              | 字符串连接，替换+号                                          |
| 2    | `str.length()`                   | 获取长度                                                     |
| 3    | `isEmpty()`                      | 判空                                                         |
| 4    | `str.charAt(0)`                  | 获取指定位置元素                                             |
| 5    | `str.codePointAt(0)`             | 获取指定位置元素，并返回ascii码值                            |
| 6    | str.getBytes()                   | 获取byte[]                                                   |
| 7    | str.equals("abc")                | 比较                                                         |
| 8    | str.equalsIgnoreCase("AbC")      | 忽略大小写，比对                                             |
| 9    | str.startsWith("a")              | 开始位置值判断                                               |
| 10   | str.endsWith("c")                | 结尾位置值判断                                               |
| 11   | str.indexOf("b")                 | 判断元素位置，开始位置                                       |
| 12   | str.lastIndexOf("b")             | 判断元素位置，结尾位置                                       |
| 13   | str.substring(0, 1)              | 截取                                                         |
| 14   | str.split(",")                   | 拆分，可以支持正则                                           |
| 15   | str.replace("a","d")、replaceAll | 替换                                                         |
| 16   | str.toUpperCase()                | 转大写                                                       |
| 17   | str.toLowerCase()                | 转小写                                                       |
| 18   | str.toCharArray()                | 转数组                                                       |
| 19   | String.format(str, "")           | 格式化，%s、%c、%b、%d、%x、%o、%f、%a、%e、%g、%h、%%、%n、%tx |
| 20   | str.valueOf("123")               | 转字符串                                                     |
| 21   | trim()                           | 格式化，首尾去空格                                           |
| 22   | str.hashCode()                   | 获取哈希值                                                   |

## 八、总结

- `业精于勤,荒于嬉`，你学到的知识不一定只是为了面试准备，还更应该是拓展自己的技术深度和广度。这个过程可能很痛苦，但总得需要某一个烧脑的过程，才让其他更多的知识学起来更加容易。
- 本文介绍了 `String、StringBuilder、StringBuffer`，的数据结构和源码分析，更加透彻的理解后，也能更加准确的使用，不会被因为不懂而犯错误。
- 想把代码写好，至少要有这四面内容，包括；数据结构、算法、源码、设计模式，这四方面在加上业务经验与个人视野，才能真的把一个需求、一个大项目写的具备良好的扩展性和易维护性。

## 九、系列推荐

- [握草，你竟然在代码里下毒！](https://bugstack.cn/itstack-demo-any/2020/09/06/%E6%8F%A1%E8%8D%89-%E4%BD%A0%E7%AB%9F%E7%84%B6%E5%9C%A8%E4%BB%A3%E7%A0%81%E9%87%8C%E4%B8%8B%E6%AF%92.html)
- [一次代码评审，差点过不了试用期！](https://bugstack.cn/itstack-demo-any/2020/09/14/%E4%B8%80%E6%AC%A1%E4%BB%A3%E7%A0%81%E8%AF%84%E5%AE%A1-%E5%B7%AE%E7%82%B9%E8%BF%87%E4%B8%8D%E4%BA%86%E8%AF%95%E7%94%A8%E6%9C%9F.html)
- [LinkedList插入速度比ArrayList快？你确定吗？](https://bugstack.cn/interview/2020/08/30/%E9%9D%A2%E7%BB%8F%E6%89%8B%E5%86%8C-%E7%AC%AC8%E7%AF%87-LinkedList%E6%8F%92%E5%85%A5%E9%80%9F%E5%BA%A6%E6%AF%94ArrayList%E5%BF%AB-%E4%BD%A0%E7%A1%AE%E5%AE%9A%E5%90%97.html)
- [重学Java设计模式(22个真实开发场景)](https://bugstack.cn/itstack/itstack-demo-design.html)
- [面经手册(上最快的车，拿最贵的offer)](https://bugstack.cn/itstack/interview.html)

