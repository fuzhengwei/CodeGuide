---
title: 第12节：加载文件生成链表单词树，输入属性时英文校准提醒
lock: need
---

# 第12节：加载文件生成链表单词树，输入属性时英文校准提醒

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

`你不知道这个已经有人做了吗？`

哈哈哈，是不是你在做一些尝试、创新、落地的时候，都可能会被有些人问这样的话，好像这一句话还挺有力量的。`你不知道说明你调研不够、有人做了你还搞证明浪费时间`，那你问他你知道那个已经做了的怎么设计的、用了什么技术、我们可以借鉴哪些能力呢，他又有7788的一堆理由，总之一张嘴，都是进口饮料。

但其实我们很多时候在学习补充自己的能力，是要通过大量实践验证的，在实践的过程中完善X产品的a功能，补充Y产品的b功能，而这些一个个小的点就像我们积累下来的乐高玩具，我们有实践支撑理论，那么在以后真的要开发产品所需能力的时候，就是把这些一个个的乐高技术技能，组合起来，搭建出我们的目标结果。`但如果你没折腾过，那么手里的乐高肯定不多！`

## 二、需求目的

首先我想问问，你在编程开发中，有把类名称、属性名、方法名，写错的时候吗，比如；把`data`写成`date`、把`main`写成`mian`、把`queryBatch`写成`queryBitch`，闹了大笑话了，上线对外还没发修改了。

那能有什么办法在我写这样的单词属性名称的时候，给我来个提示，把那些关联到的正确的提醒出来，不要让我还得一个个敲，主要是还不受控制的敲错呢？

办法是有的，本章节我们就结合 IDEA Plugin 开发的能力，在启动插件后加载常用单词文件，生成一个单词树的结构。那么用户在 IDEA 开发时输入属性名称的时候，按照输入信息获取单词树中的匹配信息，并提醒成列表反馈到用户输入界面。

## 三、案例开发

### 1. 工程结构

```java
guide-idea-plugin-remind
├── .gradle
└── src
    ├── main
    │   └── java
    │   	└── cn.bugstack.guide.idea.plugin 
    │       	├── action
    │       	│	└── RemindCompletionContributor.java
    │       	├── application
    │       	│	└── IWordManageService.java
    │       	├── domain
    │       	│	├── model
    │       	│	│   └── Node.java
    │       	│	└── service
    │       	│	    ├── AbstractWordManage.java
    │       	│	    └── WordManageServiceImpl.java
    │       	└── infrastructure
    │       	 	└── Utils.java
    ├── resources
    │   ├── dictionary
    │   │   ├── word01.txt
    │   │   ├── word02.txt
    │   │   └── word03.txt
    │   └── META-INF
    │       └── plugin.xml
    ├── build.gradle
    └── gradle.properties
```

**源码获取**：#公众号：`bugstack虫洞栈` 回复：`idea` 即可下载全部 IDEA 插件开发源码

在此 IDEA 插件工程中，主要分为2块核心功能：
- 以接口 `IWordManageService` 为主的用于实现对 `dictionary` 处理，生成单词树链表结构。
- 以服务 `RemindCompletionContributor` 检测用户输入的熟悉单词信息，从单词树中索引到匹配的内容返回给用户。

### 2. 单词树处理

#### 2.1 数据结构

处理单词树，其实就是处理的一种数据结构，怎么让在用户一个个的输入字母的时候，找到对应匹配的单词。那么这里就需要把文件中的单词按照字母一个个存放到链表结构中，例如把 fustack 存放到单词树中，如下：

**word.txt**

```java
fustack#adj.小傅哥的微信
batch#adj.批量
bitch#adj.彪子 PS：你是要输入 batch 吧？
```

**word tree**

```java
f->u
f->u->s
f->u->s->t
f->u->s->t->a
f->u->s->t->a->c
f->u->s->t->a->k
```

当用户输入f、u，那么就开始索引这棵单词树，找到匹配的字母以及当前字母位置后续链路上的整个内容，并记录到可以反馈给用户的单词列表中。

#### 2.2 单词存放

**cn.bugstack.guide.idea.plugin.domain.service.AbstractWordManage**

```java
private void loadFile(String path) {
    try {
        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path), StandardCharsets.UTF_8));
        String line;
        while ((line = br.readLine()) != null) {
            String[] wordInfo = line.split("#");
            if (!compile.matcher(wordInfo[0]).matches()) {
                continue;
            }
            wordInfo[0] = wordInfo[0].toLowerCase().trim();
            StringBuilder sb = new StringBuilder();
            if (wordInfo.length == 2) {
                Matcher matcher = explainPattern.matcher(wordInfo[1]);
                boolean hasMatch = matcher.find();
                String singleExplain = wordInfo[1];
                do {
                    if (hasMatch) {
                        singleExplain = matcher.group(2);
                        sb.append(matcher.group(1));
                    }
                    sb.append(Arrays.stream(singleExplain.split("[；;,，]")).min(Comparator.comparingInt(String::length)).get());
                    wordInfo[1] = sb.toString();
                } while (hasMatch = matcher.find());
            }
            insert(wordsTree, wordInfo[0].toLowerCase().trim(), wordInfo.length == 2 ? wordInfo[1] : "");
        }
        br.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

```java
private static void insert(Node root, String words, String explain) {
    char[] chars = words.toCharArray();
    for (char aChar : chars) {
        int charIndex = aChar - 'a';
        if (root.slot[charIndex] == null) {
            root.slot[charIndex] = new Node();
        }
        root = root.slot[charIndex];
        root.c = aChar;
        root.prefix++;
    }
    root.explain = explain;
    root.count++;
}
```

- loadFile 加载和解析文件中的单词，之后使用正则表达式获取单词和描述信息。
- insert 是形成单词树的一个操作，把对应匹配的单词字母，生成node节点，因为一组node集合最多是26个字母，node节点拉出来的链路依旧是最多26个字母，以此类推形成链表树结构。

### 3. 单词使用

接下来我们使用到继承 `CompletionContributor` 的实现类，来监听用户输入的属性字母信息，每一个输入都会调用到这个实现类中，之后在实现类里索引到匹配的单词列表反馈给用户。

#### 3.1 启动监听

**cn.bugstack.guide.idea.plugin.action.RemindCompletionContributor**

```java
public RemindCompletionContributor() {
    IWordManageService wordManageService = ApplicationManager.getApplication().getService(WordManageServiceImpl.class);
    
    CompletionProvider<CompletionParameters> provider = new DefaultCompletionProvider(wordManageService);
    
    extend(CompletionType.BASIC, psiElement(PsiIdentifier.class).withParent(PsiLocalVariable.class), provider);
    extend(CompletionType.BASIC, psiElement(PsiIdentifier.class).withParent(PsiMethod.class), provider);
    extend(CompletionType.BASIC, psiElement(PsiIdentifier.class).withParent(PsiField.class), provider);
    extend(CompletionType.BASIC, psiElement(PsiIdentifier.class).withParent(PsiParameter.class), provider);
}
```

- 首先这里需要把处理单词提供辅助输入提醒的操作，注入到 extend 中，目前这个支持 Java 输入，也可以扩展成其他语言的输入辅助提醒。

#### 3.2 单词索引

这里的索引是一个继承 `CompletionProvider` 的实现类，通过对 `addCompletions` 的扩展，完成对单词的索引操作。

**索引单词**

**cn.bugstack.guide.idea.plugin.domain.service.WordManageServiceImpl**

```java
@Override
public List<Node> searchPrefix(String prefix) {
    Node root = wordsTree;
    char[] chars = prefix.toCharArray();
    StringBuilder sb = new StringBuilder();
    for (char aChar : chars) {
        int charIndex = aChar - 'a';
        if (charIndex > root.slot.length || charIndex < 0 || root.slot[charIndex] == null) {
            return Collections.emptyList();
        }
        sb.append(aChar);
        root = root.slot[charIndex];
    }
    ArrayList<Node> list = new ArrayList<>();
    if (root.prefix != 0) {
        for (int i = 0; i < root.slot.length; i++) {
            if (root.slot[i] != null) {
                char c = (char) (i + 'a');
                collect(root.slot[i], String.valueOf(sb) + c, list, RESULT_LIMIT);
                if (list.size() >= RESULT_LIMIT) {
                    return list;
                }
            }
        }
    }
    return list;
}
```

- 入参 `prefix` 是用于输入的单词，在 searchPrefix 中提取单词中中的字母，例如：fus，则陆续提取三个字母，一直到最后一个字母 `root = root.slot[charIndex];`
- 在索引到匹配的字母后，按照当前字母的对应 `s开头` 的列表，循环获取后续的字母并返回最终的结果列表。

### 4. plugin 配置

```xml
<extensions defaultExtensionNs="com.intellij">
    <!-- Add your extensions here -->
    <applicationService serviceImplementation="cn.bugstack.guide.idea.plugin.domain.service.WordManageServiceImpl"/>
    
    <completion.contributor language="JAVA"
                            order="first"
                            implementationClass="cn.bugstack.guide.idea.plugin.action.RemindCompletionContributor"/>
</extensions>
```

- 这里我们需要把单词文件树处理实现类和监听用户的单词输入，配置到 extensions 中，这样就可以使用我们的单词输入提醒操作了。

## 四、测试验证

**启动插件**

![](https://bugstack.cn/images/article/assembly/assembly-220122-9-01.png)

- 在 Grandle -> Tasks -> intellj -> runIde 进行启动
- 启动后打开一个你的工程进行输入测试属性信息进行测试

**测试插件**

![](https://bugstack.cn/images/article/assembly/assembly-220122-9-02.png)

- 现在当你输入一个单词的时候，就可以把我们存放好在文件里的单词提醒给用户了。*很好玩吧*

## 五、总结

- 在本章节我们学习到了如何去处理树结构的单词列表的存放和读取，对于这样的数据结构使用其实不只是在这里，也可以放到一些其他文字类的辅助输入提醒中。
- 接下来又是一个有关 IDEA 插件开发的知识点，就是把一些提醒信息自定义单词和描述，反馈给用户输入界面中。
- 这些内容的知识点使用，都可以被我们赋能于其他场景开发中，远远不只是这样一个场景。而这些内容虽然不是多有难度，但如果没见过、没想过、没用过，就一定不会想得到可以用。