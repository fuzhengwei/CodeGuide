---
layout: post
category: interview
title: 面经手册 · 第36篇《MyBatis 动态 SQL 怎么实现的？9个标签用法与 OGNL 解析原理》
tagline: by 小傅哥
tag: [java,interview]
excerpt: MyBatis 动态 SQL 是最强大的特性之一，也是面试高频考点。9个标签各有妙用，if 条件判断、foreach 集合遍历、where/set 智能裁剪……但你知道底层是怎么解析的吗？OGNL 表达式如何求值？SqlNode 树如何遍历生成最终 SQL？本文从标签用法到源码原理，一网打尽。
lock: need
---

# 面经手册 · 第36篇《MyBatis 动态 SQL 怎么实现的？9个标签用法与 OGNL 解析原理》

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

## 一、前言

动态 SQL 是 MyBatis 最强大的特性之一，也是面试中出现频率极高的一道考题。

大部分候选人能说出 if、where、foreach 这几个标签的名字，但追问一句：where 标签是怎么自动去除前缀 AND/OR 的？foreach 的 collection 属性到底传什么？trim 和 where/set 是什么关系？动态 SQL 底层解析原理是什么？——大多数人就答不上来了。

本文从 9 个动态 SQL 标签的用法讲起，逐个给出完整代码示例，再深入源码追踪 OGNL 表达式解析和 SqlNode 树的遍历过程。不只是记住标签怎么用，更要知道底层是怎么跑的。

## 二、面试题

`谢飞机，小记！`，昨天刚背完 MyBatis 八股文，今天又来面试了。

**面试官**：谢飞机，说说 MyBatis 动态 SQL 是什么？有哪些标签？

**谢飞机**：动态 SQL 就是根据条件拼接不同的 SQL。有 if、where、foreach、choose、set 这些标签。

**面试官**：if 标签的 test 属性用的是什么表达式语言？

**谢飞机**：OGNL... 吧？

**面试官**：where 标签是怎么自动去掉前缀 AND 的？

**谢飞机**：嗯... 它内部有个处理逻辑？

**面试官**：对，那这个逻辑在源码里怎么实现的？和 trim 标签什么关系？

**谢飞机**：trim... 我只知道它能去前缀后缀...

**面试官**：foreach 标签的 collection 属性，传 List 和传数组分别写什么？

**谢飞机**：list？array？

**面试官**：那传 Map 呢？key 是什么？

**谢飞机**：... 再见！ヾ(￣▽￣)

## 三、9个动态SQL标签详解

### 1. if — 条件判断

最基础的动态 SQL 标签，根据条件决定是否拼接 SQL 片段。

```xml
<select id="findUsers" resultType="User">
    SELECT * FROM user
    WHERE status = 'ACTIVE'
    <if test="name != null">
        AND name LIKE CONCAT('%', #{name}, '%')
    </if>
    <if test="age != null">
        AND age = #{age}
    </if>
</select>
```

**test 属性**：使用 OGNL 表达式求值，结果为 true 时拼接 SQL 片段。

OGNL 常用判断写法：

```xml
<!-- 判断不为空 -->
<if test="name != null and name != ''">

<!-- 调用方法 -->
<if test="list != null and list.size() > 0">

<!-- 判断集合 -->
<if test="ids != null and ids.size() > 0">

<!-- 字符串方法 -->
<if test="name != null and name.indexOf('admin') >= 0">
```

### 2. choose/when/otherwise — 多条件分支

类似 Java 的 switch-case，只匹配第一个满足条件的分支。

```xml
<select id="findUsers" resultType="User">
    SELECT * FROM user WHERE status = 'ACTIVE'
    <choose>
        <when test="id != null">
            AND id = #{id}
        </when>
        <when test="name != null">
            AND name = #{name}
        </when>
        <otherwise>
            AND create_time > '2024-01-01'
        </otherwise>
    </choose>
</select>
```

**要点**：when 按顺序判断，匹配一个就跳出；otherwise 在所有 when 都不满足时生效。与多个 if 不同，choose 只会命中一个分支。

### 3. where — 智能WHERE子句

自动去除条件前缀的 AND/OR，避免语法错误。

```xml
<select id="findUsers" resultType="User">
    SELECT * FROM user
    <where>
        <if test="name != null">
            AND name = #{name}
        </if>
        <if test="age != null">
            AND age = #{age}
        </if>
    </where>
</select>
```

**场景一**：name 不为空、age 为空时，生成：

```sql
SELECT * FROM user WHERE name = ?
```

AND 被自动去除。

**场景二**：name 和 age 都不为空时，生成：

```sql
SELECT * FROM user WHERE name = ? AND age = ?
```

第一个 AND 被去除，第二个保留。

**场景三**：name 和 age 都为空时，生成：

```sql
SELECT * FROM user
```

WHERE 子句整体不生成。

### 4. set — 智能UPDATE SET子句

自动去除末尾多余的逗号。

```xml
<update id="updateUser" parameterType="User">
    UPDATE user
    <set>
        <if test="name != null">
            name = #{name},
        </if>
        <if test="age != null">
            age = #{age},
        </if>
        <if test="email != null">
            email = #{email},
        </if>
    </set>
    WHERE id = #{id}
</update>
```

只更新非空字段，set 标签自动去除最后一个条件末尾的逗号。生成示例：

```sql
UPDATE user SET name = ?, age = ? WHERE id = ?
```

### 5. trim — 自定义前缀后缀裁剪

where 和 set 的底层实现都依赖 trim，trim 是最灵活的裁剪标签。

```xml
<!-- trim 等价于 where 标签 -->
<trim prefix="WHERE" prefixOverrides="AND |OR ">
    <if test="name != null">
        AND name = #{name}
    </if>
    <if test="age != null">
        AND age = #{age}
    </if>
</trim>

<!-- trim 等价于 set 标签 -->
<trim prefix="SET" suffixOverrides=",">
    <if test="name != null">
        name = #{name},
    </if>
    <if test="age != null">
        age = #{age},
    </if>
</trim>
```

| 属性 | 说明 |
|------|------|
| prefix | 内容非空时，添加的前缀 |
| suffix | 内容非空时，添加的后缀 |
| prefixOverrides | 去除内容开头指定的字符串（多个用 | 分隔） |
| suffixOverrides | 去除内容末尾指定的字符串（多个用 | 分隔） |

### 6. foreach — 集合遍历

最常用的动态 SQL 标签之一，常用于 IN 查询和批量操作。

**IN 查询**：

```xml
<select id="findByIds" resultType="User">
    SELECT * FROM user WHERE id IN
    <foreach collection="ids" item="id" open="(" separator="," close=")">
        #{id}
    </foreach>
</select>
```

**批量插入**：

```xml
<insert id="batchInsert">
    INSERT INTO user (name, age) VALUES
    <foreach collection="list" item="user" separator=",">
        (#{user.name}, #{user.age})
    </foreach>
</insert>
```

**属性详解**：

| 属性 | 说明 | 示例 |
|------|------|------|
| collection | 遍历的集合对象 | List→list，数组→array，Map→Map的key |
| item | 每次迭代的变量名 | `#{id}` |
| index | 索引变量名（List为下标，Map为key） | `#{idx}` |
| open | 开始符号 | `(` |
| close | 结束符号 | `)` |
| separator | 每次迭代之间的分隔符 | `,` |

**collection 属性传值规则**：

```java
// 1. 传 List：collection="list"（默认）或 @Param 指定名称
List<User> findByIds(@Param("ids") List<Long> ids);  // collection="ids"

// 2. 传数组：collection="array"（默认）或 @Param 指定名称
List<User> findByIds(@Param("ids") Long[] ids);  // collection="ids"

// 3. 传 Map：collection 为 Map 的 key
List<User> findByMap(@Param("params") Map<String, List<Long>> params);
// XML 中 collection="params.ids" 或直接传 Map 时 collection 为某个 key
```

### 7. bind — 变量绑定

在 SQL 解析阶段创建一个变量，供后续 OGNL 表达式引用。

```xml
<select id="findUserByName" resultType="User">
    <bind name="pattern" value="'%' + name + '%'" />
    SELECT * FROM user WHERE name LIKE #{pattern}
</select>
```

**典型场景**：模糊查询，避免使用 `${}` 拼接通配符。bind 创建的变量可以用 `#{}` 引用，安全又灵活。

### 8. sql/include — SQL片段复用

将重复的 SQL 片段抽取出来，多处引用。

```xml
<!-- 定义 SQL 片段 -->
<sql id="userColumns">
    id, name, age, email, create_time
</sql>

<!-- 引用 SQL 片段 -->
<select id="findUsers" resultType="User">
    SELECT <include refid="userColumns"/> FROM user
</select>

<select id="findById" resultType="User">
    SELECT <include refid="userColumns"/> FROM user WHERE id = #{id}
</select>
```

**注意**：sql/include 不是动态 SQL 标签，它们在 MyBatis 解析阶段就完成了替换，不涉及运行时的条件判断。但日常开发中和动态 SQL 配合使用非常频繁。

## 四、OGNL表达式解析原理

### 1. 什么是OGNL

OGNL（Object-Graph Navigation Language）是一种表达式语言，用于读写 Java 对象的属性和调用方法。MyBatis 使用 OGNL 来解析动态 SQL 中 test 属性的表达式。

```xml
<!-- OGNL 表达式示例 -->
<if test="name != null">          <!-- 访问属性 -->
<if test="list.size() > 0">       <!-- 调用方法 -->
<if test="type == 'admin'">       <!-- 字符串比较 -->
<if test="age > 18 and age < 60"> <!-- 逻辑运算 -->
```

### 2. OGNL在MyBatis中的执行流程

```
XML 映射文件中的 test 表达式
  ↓
OgnlCache.getValue(expression, context)
  ↓  编译表达式（缓存机制）
Ognl.getValue(parsedExpression, root)
  ↓  根据上下文参数求值
返回 true/false → 决定是否拼接 SQL 片段
```

**OgnlCache 源码**：

```java
// org.apache.ibatis.scripting.xmltags.OgnlCache
public class OgnlCache {

    private static final Map<String, Object> expressionCache = new ConcurrentHashMap<>();

    public static Object getValue(String expression, Object root) {
        try {
            // 编译表达式（有缓存）
            Object parsedExpression = parseExpression(expression);
            // 在 root 对象上求值
            return Ognl.getValue(parsedExpression, root);
        } catch (OgnlException e) {
            throw new RuntimeException("Error evaluating expression '" +
                expression + "'. Cause: " + e, e);
        }
    }

    private static Object parseExpression(String expression) throws OgnlException {
        // 先查缓存，没有再编译
        Object node = expressionCache.get(expression);
        if (node == null) {
            node = Ognl.parseExpression(expression);
            expressionCache.put(expression, node);
        }
        return node;
    }
}
```

**关键点**：OGNL 表达式编译结果有缓存，同一条 SQL 多次执行时不会重复编译，性能有保障。

### 3. OGNL上下文中的参数访问

MyBatis 在构建 OGNL 上下文时，会将参数对象放入 `_parameter` 和方法参数名中：

```java
// DynamicContext 构造时
bindings.put("_parameter", parameter);
bindings.put("_databaseId", databaseId);
// 如果是 @Param 注解的参数，还会放入具体的参数名
```

所以在 test 表达式中可以直接写属性名：

```xml
<!-- 单参数 -->
<if test="name != null">  <!-- 等同于 _parameter.name != null -->

<!-- @Param("name") 多参数 -->
<if test="name != null">  <!-- 直接用参数名 -->

<!-- 无 @Param 多参数 -->
<if test="param1 != null"> <!-- 只能用 param1/param2 或 arg0/arg1 -->
```

## 五、动态SQL源码解析

### 1. 解析入口：XMLScriptBuilder

MyBatis 解析 XML 映射文件中的 SQL 语句时，通过 XMLScriptBuilder 处理动态 SQL 标签：

```java
// org.apache.ibatis.builder.xml.XMLScriptBuilder
public class XMLScriptBuilder extends BaseBuilder {

    public SqlSource parseScriptNode() {
        // 解析动态标签，构建 SqlNode 树
        MixedSqlNode rootSqlNode = parseDynamicTags(context);
        SqlSource sqlSource;
        if (isDynamic) {
            sqlSource = new DynamicSqlSource(configuration, rootSqlNode);
        } else {
            sqlSource = new RawSqlSource(configuration, rootSqlNode, parameterType);
        }
        return sqlSource;
    }
}
```

### 2. parseDynamicTags：构建SqlNode树

这是核心方法，递归解析 XML 节点，将每个标签转换为对应的 SqlNode 实现：

```java
// org.apache.ibatis.builder.xml.XMLScriptBuilder
protected MixedSqlNode parseDynamicTags(XNode node) {
    List<SqlNode> contents = new ArrayList<>();
    NodeList children = node.getNode().getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
        XNode child = node.newXNode(children.item(i));
        if (child.getNode().getNodeType() == Node.CDATA_SECTION_NODE
            || child.getNode().getNodeType() == Node.TEXT_NODE) {
            // 文本节点
            String data = child.getStringBody("");
            TextSqlNode textSqlNode = new TextSqlNode(data);
            if (textSqlNode.isDynamic()) {
                // 包含 ${} 的文本 → TextSqlNode
                contents.add(textSqlNode);
                isDynamic = true;
            } else {
                // 纯文本 → StaticTextSqlNode
                contents.add(new StaticTextSqlNode(data));
            }
        } else if (child.getNode().getNodeType() == Node.ELEMENT_NODE) {
            // 标签节点 → 根据标签名创建对应 SqlNode
            String nodeName = child.getNode().getNodeName();
            NodeHandler handler = nodeHandlers.get(nodeName);
            handler.handleNode(child, contents);
            isDynamic = true;
        }
    }
    return new MixedSqlNode(contents);
}
```

### 3. SqlNode体系

每个动态 SQL 标签都对应一个 SqlNode 实现类：

```
SqlNode（接口）
  ├── MixedSqlNode          — 组合节点，包含多个子 SqlNode
  ├── IfSqlNode             — 对应 <if> 标签
  ├── ChooseSqlNode         — 对应 <choose> 标签
  ├── WhereSqlNode          — 对应 <where> 标签
  ├── SetSqlNode            — 对应 <set> 标签
  ├── TrimSqlNode           — 对应 <trim> 标签
  ├── ForEachSqlNode        — 对应 <foreach> 标签
  ├── VarDeclSqlNode        — 对应 <bind> 标签
  ├── TextSqlNode           — 包含 ${} 的文本
  └── StaticTextSqlNode     — 纯静态文本
```

### 4. SqlNode.apply()：遍历生成SQL

SqlNode 接口只有一个方法：

```java
public interface SqlNode {
    boolean apply(DynamicContext context);
}
```

每个 SqlNode 的 apply 方法负责将自己的 SQL 片段追加到 DynamicContext 中。运行时，MixedSqlNode 递归调用所有子节点的 apply 方法：

```java
// MixedSqlNode
public class MixedSqlNode implements SqlNode {
    private final List<SqlNode> contents;

    @Override
    public boolean apply(DynamicContext context) {
        for (SqlNode sqlNode : contents) {
            sqlNode.apply(context);
        }
        return true;
    }
}
```

**IfSqlNode**：

```java
public class IfSqlNode implements SqlNode {
    private final ExpressionEvaluator evaluator;
    private final String test;
    private final SqlNode contents;

    @Override
    public boolean apply(DynamicContext context) {
        // 使用 OGNL 求值 test 表达式
        if (evaluator.evaluateBoolean(test, context.getBindings())) {
            contents.apply(context);  // 条件为 true，拼接子节点
            return true;
        }
        return false;
    }
}
```

**WhereSqlNode**：

```java
public class WhereSqlNode extends TrimSqlNode {

    private static List<String> prefixList = Arrays.asList("AND ", "OR ", "AND\n", "OR\n",
        "AND\r", "OR\r", "AND\t", "OR\t");

    public WhereSqlNode(Configuration configuration, SqlNode contents) {
        // where 标签就是 trim 的特例！
        // prefix = "WHERE"，prefixOverrides = "AND |OR "
        super(configuration, contents, "WHERE", prefixList, null, null);
    }
}
```

**SetSqlNode**：

```java
public class SetSqlNode extends TrimSqlNode {

    public SetSqlNode(Configuration configuration, SqlNode contents) {
        // set 标签也是 trim 的特例！
        // prefix = "SET"，suffixOverrides = ","
        super(configuration, contents, "SET", null, null, Arrays.asList(","));
    }
}
```

**关键发现**：where 和 set 都是 TrimSqlNode 的子类！where 就是 `prefix="WHERE" prefixOverrides="AND |OR "` 的 trim，set 就是 `prefix="SET" suffixOverrides=","` 的 trim。

**TrimSqlNode**：

```java
public class TrimSqlNode implements SqlNode {
    private final SqlNode contents;
    private final String prefix;
    private final String suffix;
    private final List<String> prefixesToOverride;
    private final List<String> suffixesToOverride;

    @Override
    public boolean apply(DynamicContext context) {
       FilteredDynamicContext filteredDynamicContext =
            new FilteredDynamicContext(context, prefix, suffix,
                prefixesToOverride, suffixesToOverride);
        // 先让子节点拼接 SQL
        contents.apply(filteredDynamicContext);
        // 再处理前缀后缀裁剪
        filteredDynamicContext.applyAll();
        return true;
    }

    private class FilteredDynamicContext extends DynamicContext {
        @Override
        public void applyAll() {
            // 1. 子节点拼接完成后，获取完整 SQL
            String sql = delegate.getSqlBuilder().toString().trim();
            // 2. 去除前缀（AND/OR）
            if (prefixesToOverride != null) {
                for (String toRemove : prefixesToOverride) {
                    if (sql.toUpperCase().startsWith(toRemove.toUpperCase())) {
                        sql = sql.substring(toRemove.length());
                        break;
                    }
                }
            }
            // 3. 去除后缀（逗号）
            if (suffixesToOverride != null) {
                for (String toRemove : suffixesToOverride) {
                    if (sql.toUpperCase().endsWith(toRemove.toUpperCase())) {
                        sql = sql.substring(0, sql.length() - toRemove.length());
                        break;
                    }
                }
            }
            // 4. 内容非空时添加 prefix/suffix
            if (sql.length() > 0) {
                if (prefix != null) sql = prefix + sql;
                if (suffix != null) sql = sql + suffix;
            }
            delegate.appendSql(sql);
        }
    }
}
```

**ForEachSqlNode**：

```java
public class ForEachSqlNode implements SqlNode {
    private final ExpressionEvaluator evaluator;
    private final String collectionExpression;
    private final String item;
    private final String index;
    private final String open;
    private final String close;
    private final String separator;
    private final SqlNode contents;

    @Override
    public boolean apply(DynamicContext context) {
        // 1. 通过 OGNL 获取集合对象
        Iterable<?> iterable = evaluator.evaluateIterable(
            collectionExpression, context.getBindings());
        int i = 0;
        for (Object o : iterable) {
            // 2. 保存旧变量（item/index 可能与外部变量同名）
            int uniqueNumber = context.getUniqueNumber();
            // 3. 将当前迭代项放入上下文
            if (o instanceof Map.Entry) {
                Map.Entry<Object, Object> mapEntry = (Map.Entry<Object, Object>) o;
                context.bind(item, mapEntry.getValue());
                context.bind(index, mapEntry.getKey());
            } else {
                context.bind(index, i);
                context.bind(item, o);
            }
            // 4. 添加 open/separator/close
            if (i == 0 && open != null) {
                context.appendSql(open);
            } else if (separator != null) {
                context.appendSql(separator);
            }
            // 5. 拼接每次迭代的 SQL
            contents.apply(context);
            i++;
        }
        if (i > 0 && close != null) {
            context.appendSql(close);
        }
        return true;
    }
}
```

### 5. DynamicSqlSource：动态SQL的执行入口

```java
// org.apache.ibatis.scripting.xmltags.DynamicSqlSource
public class DynamicSqlSource implements SqlSource {

    private final Configuration configuration;
    private final SqlNode rootSqlNode;

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        // 1. 创建动态上下文
        DynamicContext context = new DynamicContext(configuration, parameterObject);
        // 2. 遍历 SqlNode 树，拼接 SQL
        rootSqlNode.apply(context);
        // 3. 处理 #{ } → 替换为 ?
        SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
        SqlSource sqlSource = sqlSourceParser.parse(
            context.getSql(), parameterType, context.getBindings());
        // 4. 生成 BoundSql
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        // 5. 绑定附加参数
        for (Map.Entry<String, Object> entry : context.getBindings().entrySet()) {
            boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
        }
        return boundSql;
    }
}
```

**完整链路**：

```
DynamicSqlSource.getBoundSql()
  → DynamicContext 创建
  → rootSqlNode.apply(context)    ← 递归遍历 SqlNode 树
    → IfSqlNode: OGNL 求值 → 条件拼接
    → WhereSqlNode: 子节点拼接 → 去前缀 AND/OR → 加 WHERE
    → ForEachSqlNode: 集合遍历 → 生成 IN 列表
    → ... 其他 SqlNode
  → SqlSourceBuilder.parse()      ← `#{}` 替换为 ?
  → BoundSql 返回
```

## 六、常见陷阱

### 1. where标签不处理条件都为空的情况

```xml
<!-- 如果 name 和 age 都为空 -->
<select id="findUsers" resultType="User">
    SELECT * FROM user
    <where>
        <if test="name != null">
            AND name = #{name}
        </if>
        <if test="age != null">
            AND age = #{age}
        </if>
    </where>
</select>
<!-- 生成：SELECT * FROM user（无 WHERE，查全表！） -->
```

**建议**：业务层做校验，至少传一个条件，或在 SQL 中加上恒真条件兜底。

### 2. set标签内忘记加逗号

```xml
<!-- ❌ 错误：缺少逗号 -->
<set>
    <if test="name != null">
        name = #{name}
    </if>
    <if test="age != null">
        age = #{age}
    </if>
</set>
<!-- 如果两个都非空，生成：SET name = ? age = ? — 语法错误！ -->

<!-- ✅ 正确：每个条件后加逗号，set 会自动去最后一个 -->
<set>
    <if test="name != null">
        name = #{name},
    </if>
    <if test="age != null">
        age = #{age},
    </if>
</set>
```

### 3. foreach的collection属性写错

```java
// 接口方法
List<User> findByIds(List<Long> ids);       // collection="list"
List<User> findByIds(@Param("ids") List<Long> ids);  // collection="ids"
List<User> findByIds(Long[] ids);            // collection="array"
```

不使用 @Param 时，List 默认用 `list`，数组默认用 `array`。传错会报 `There is no getter for property named 'xxx'`。

### 4. OGNL中字符串比较用单引号

```xml
<!-- ❌ 错误：双引号会被当成 OGNL 的字符串界定符 -->
<if test="type == "admin"">

<!-- ✅ 正确：单引号 -->
<if test="type == 'admin'">

<!-- ✅ 也可以用 toString() -->
<if test='type == "admin".toString()'>
```

### 5. foreach批量插入量过大

```xml
<!-- 批量插入 10000 条 → 生成超长 SQL → 可能超出数据库限制 -->
<insert id="batchInsert">
    INSERT INTO user (name, age) VALUES
    <foreach collection="list" item="user" separator=",">
        (#{user.name}, #{user.age})
    </foreach>
</insert>
```

**建议**：分批执行，每批 500-1000 条；或使用 MyBatis 的 ExecutorType.BATCH 模式。

## 七、完整示例：动态查询

```xml
<mapper namespace="com.example.mapper.UserMapper">

    <!-- SQL 片段 -->
    <sql id="userColumns">
        id, name, age, email, status, create_time
    </sql>

    <!-- 动态查询 -->
    <select id="findUsers" resultType="User">
        SELECT <include refid="userColumns"/>
        FROM user
        <where>
            <if test="status != null">
                AND status = #{status}
            </if>
            <if test="name != null and name != ''">
                <bind name="namePattern" value="'%' + name + '%'" />
                AND name LIKE #{namePattern}
            </if>
            <if test="ids != null and ids.size() > 0">
                AND id IN
                <foreach collection="ids" item="id" open="(" separator="," close=")">
                    #{id}
                </foreach>
            </if>
        </where>
        <choose>
            <when test="sortField != null and sortField != ''">
                ORDER BY ${sortField}
                <if test="sortOrder != null">
                    ${sortOrder}
                </if>
            </when>
            <otherwise>
                ORDER BY create_time DESC
            </otherwise>
        </choose>
    </select>

    <!-- 动态更新 -->
    <update id="updateUser" parameterType="User">
        UPDATE user
        <set>
            <if test="name != null">name = #{name},</if>
            <if test="age != null">age = #{age},</if>
            <if test="email != null">email = #{email},</if>
            <if test="status != null">status = #{status},</if>
        </set>
        WHERE id = #{id}
    </update>

</mapper>
```

注意上面 ORDER BY 使用了 `${}`，因为排序字段名是标识符不能用 `#{}`，**必须在 Java 层做白名单校验**。

## 八、常见面试追问

### Q1：trim标签和where/set标签是什么关系？

where 和 set 都是 trim 的特例。where 等价于 `prefix="WHERE" prefixOverrides="AND |OR "`，set 等价于 `prefix="SET" suffixOverrides=","`。在源码中，WhereSqlNode 和 SetSqlNode 都继承自 TrimSqlNode，构造时传入对应的参数即可。

### Q2：动态SQL会影响性能吗？

会有一点影响，但通常可以忽略。动态 SQL 每次执行时都需要遍历 SqlNode 树、OGNL 求值、拼接 SQL 字符串，这个过程有开销。但 OGNL 表达式编译有缓存，SqlNode 树在启动时就已经构建好，运行时只是遍历和求值。相比数据库 I/O，这点开销微不足道。

需要注意的是，**动态 SQL 会导致 SQL 文本变化**，比如不同条件组合生成不同的 SQL，这可能影响数据库的 SQL 缓存命中率。但这也是无法避免的——不同的查询条件本来就应该生成不同的 SQL。

### Q3：SQL注入和动态SQL有关系吗？

有间接关系。动态 SQL 中的 `${}` 拼接和 if/foreach 标签本身不会导致注入，但如果在动态 SQL 中使用了 `${}`（如 ORDER BY 场景），且未做白名单校验，就存在注入风险。反过来，动态 SQL 的 `#{}` + foreach 等标签，恰恰是**防止注入**的正确写法——用 `#{}` + foreach 替代 `${}` 拼接 IN 列表。

### Q4：动态SQL和静态SQL的区别是什么？

静态 SQL（RawSqlSource）：SQL 在启动时就能确定完整内容，不含任何动态标签和 `${}`，只包含 `#{}` 占位符。解析一次，执行时直接拿来用。

动态 SQL（DynamicSqlSource）：SQL 在执行时才能确定内容，包含 if/where/foreach 等标签。每次执行都需要遍历 SqlNode 树、OGNL 求值，动态拼接 SQL。

判断依据：XMLScriptBuilder 解析时，如果发现任何动态标签或 `${}`，就标记 isDynamic=true，使用 DynamicSqlSource。

### Q5：为什么OGNL表达式判断字符串相等要用单引号？

OGNL 中双引号用于界定字符串字面量，而 MyBatis XML 的属性值本身用双引号包裹。所以 test 属性内的双引号会和 XML 属性的双引号冲突，必须用单引号表示字符串值。

## 九、总结

```
记住三个核心要点：

1. 9个标签各有分工：
   if 条件判断、choose 多分支、where 智能WHERE、set 智能SET、
   trim 自定义裁剪、foreach 集合遍历、bind 变量绑定、
   sql/include 片段复用
   where = trim(prefix="WHERE", prefixOverrides="AND |OR ")
   set = trim(prefix="SET", suffixOverrides=",")

2. 解析原理 = SqlNode 树 + OGNL 求值
   XMLScriptBuilder.parseDynamicTags() 将 XML 标签解析为 SqlNode 树
   运行时 DynamicSqlSource 遍历 SqlNode 树
   每个节点的 apply() 方法向 DynamicContext 追加 SQL
   OGNL 表达式编译结果有缓存，不会重复编译

3. 注意常见陷阱：
   where 条件全空 → 无 WHERE 子句 → 全表查询
   set 内每个条件后必须加逗号
   foreach 的 collection 属性要和参数对应
   OGNL 字符串比较用单引号
   批量操作要控制每批数量
```

**面试回答模板**：

> MyBatis 动态 SQL 有 9 个标签：if 做条件判断，choose/when/otherwise 做多分支选择，where 自动去除前缀 AND/OR，set 自动去除末尾逗号，trim 是自定义裁剪（where 和 set 底层都继承自 TrimSqlNode），foreach 遍历集合做 IN 查询或批量操作，bind 创建变量，sql/include 做 SQL 片段复用。
>
> 解析原理上，MyBatis 在启动时通过 XMLScriptBuilder.parseDynamicTags() 将 XML 中的动态标签递归解析为一棵 SqlNode 树，每个标签对应一个 SqlNode 实现类。运行时，DynamicSqlSource.getBoundSql() 遍历这棵树，每个 SqlNode 的 apply() 方法向 DynamicContext 追加 SQL 片段。if 标签通过 OGNL 表达式求值决定是否拼接，where/set 通过 TrimSqlNode 的裁剪逻辑处理前缀后缀，foreach 通过迭代集合拼接多次 SQL。OGNL 表达式编译有缓存，不会重复解析。
>
> 使用上需要注意几个常见陷阱：where 条件全空会导致全表查询，set 内每个条件后必须加逗号，foreach 的 collection 属性要和参数类型对应，批量操作要控制每批数量避免超限。