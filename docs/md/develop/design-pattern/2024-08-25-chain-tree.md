---
title: 干掉if...else，最好用的3种设计模式！
lock: need
---

# 干掉if...else，最好用的3种设计模式！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

<iframe id="B-Video" src="//player.bilibili.com/player.html?isOutside=true&aid=113042213831736&bvid=BV1oYs3ejENB&cid=25629625969&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家伙，我是技术UP主小傅哥。

在我们做Java业务代码开发时，经常会碰到大量的流程判断，`验证场景渠道`、`验证用户身份`、`验证会员级别`等等10来个甚至几十个分支节点。对于这样的编码80%的T2码农都是 if...else 编码。那除了贴膏药一样的写代码还有别的什么办法吗？

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/student-learn-01.gif" width="150px">
</div>

**if...else 写代码有什么问题吗？**

往近了看，if...else 写的代码交付的快，但工程腐化的也快。有点像买的米面粮油不区分，全倒入一个桶里。让后面的兄弟在迭代需求时，都想铲了重写。但实际铲的成本太高，所以就挑挑拣拣、复制粘贴。

往远了看，是个人发展。如果昨天做的事、今天做的事和明天做的事，都是一个事。反复的重复，没有脑力思考，只是提高手速。那面试、述职、分享的时候真没的讲，你总不能告诉面试官我就写 if...else 了，遇到问题查百度。那离毕业🎓也不远了！

>接下来，小傅哥就给大家分享一个链路执行的设计模式，看看都有哪些方式处理。

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/student-learn-01.gif" width="150px">
</div>

## 一、场景问题

Java 开发，尤其是 Java 业务开发的，就离不开大量的流程分支处理。产品给的需求，也是在编码中调用一系列的接口做流程验证处理。而且需求是频繁变化的，这也就间接的导致了程序员的 if...else 代码也要跟着一次次调整，从原来的几十行编程几百几千行。这个过程中还有一些要去掉的、要根据流程类型选择切换的、要覆盖所有的之前的需求不能出问题的。所以腐化越来越严重，开发成本越来越高。

<div align="center">
    <img src="https://bugstack.cn/images/article/develop/xfg-dev-tech-design-240528-01.png" width="550px">
</div>

```java
一个接口、一个实现，
一个实现，代码一片。
一片一片、又一片，
代码行数、两三千。
```

这是我们在承接产品需求时候经验能看到的一种流程图，在没有太强的代码驾驭能力时，很多伙伴的都只能写 if...else 一个个分支节点判断，所有的代码都平铺到一个类中。如果注意到你会发现，不具有设计模式能力的程序员，代码是没有立体化的。都是扁平平铺下来的。

那么对于这样的场景，我们完全可以通过设计模式的知识进行分治和抽象，这也是软件设计的第一原则，康威定律所倡导的。通过设计模式解耦流程，让编码的呈现出立体化，通过类来划分职责和执行过程。

## 二、设计模式 - 链&树

对于大量的有衔接关系的 if...else 判断流程，有两种设计手段对应这3种编码方式。包括；责任链和规则树。

<div align="center">
    <img src="https://bugstack.cn/images/article/develop/xfg-dev-tech-design-240528-02.png" width="500px">
</div>

- 责任链是一条单向执行链，没有过程中的分支流转，适合简单的单一规则校验。
- 规则树是一条多向执行链，可以在过程中根据不同的分支判断走到对应的节点。而规则树的实现可以有两种方式，一种是通过数据库配置决定要加载的节点和执行的过程。另外一种是类似于责任链方式，让每个类自己根据业务条件决定下一个执行链路，这个设计巧妙的结合了责任链的思路，非常有意思。

>接下来，小傅哥就分别举例下这样的代码实现。程序员还是看代码，学习起来更有感觉。

## 三、编码实现

### 1. 责任链

#### 1.1 业务场景

抽奖规则过滤场景，分为黑名单用户、权重抽奖和默认抽奖，三个节点。如果一个用户是黑名单范围用户，则直接返回兜底奖品。而权重用户是一个用户已完成了N次抽奖后，在权重范围内可以获得一个固定的奖品。最后是兜底抽奖，这两个条件都不是，则进行默认兜底流程。

#### 1.2 设计类图

<div align="center">
    <img src="https://bugstack.cn/images/article/develop/xfg-dev-tech-design-240528-03.png" width="750px">
</div>

- 首先，定义出责任链装配接口和责任链逻辑接口，之后由抽象类实现接口，做链的封装实现。
- 之后，实现3个责任链实现类。黑名单、权重、兜底。处理各自的逻辑。
- 最后，由工厂装配责任链。后续可以按需扩展需要的责任链。这样业务流程就可以动态的拼装了。

#### 1.3 核心代码

<div align="center">
    <img src="https://bugstack.cn/images/article/develop/xfg-dev-tech-design-240528-04.png" width="450px">
</div>

```java
public abstract class AbstractLogicChain implements ILogicChain{

    private ILogicChain next;

    @Override
    public ILogicChain next() {
        return next;
    }

    @Override
    public ILogicChain appendNext(ILogicChain next) {
        this.next = next;
        return next;
    }

    protected abstract String ruleModel();

}

@Slf4j
@Component("rule_default")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DefaultLogicChain extends AbstractLogicChain {

    @Resource
    protected IStrategyDispatch strategyDispatch;

    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);
        log.info("抽奖责任链-默认处理 userId:{} strategyId:{} ruleModel:{} awardId:{}", userId, strategyId, ruleModel(), awardId);
        return DefaultChainFactory.StrategyAwardVO.builder()
                .awardId(awardId)
                .logicModel(ruleModel())
                .build();
    }

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode();
    }

}

public ILogicChain openLogicChain(Long strategyId) {
    ILogicChain cacheLogicChain = strategyChainGroup.get(strategyId);
    if (null != cacheLogicChain) return cacheLogicChain;
    StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);
    String[] ruleModels = strategy.ruleModels();
    // 如果未配置策略规则，则只装填一个默认责任链
    if (null == ruleModels || 0 == ruleModels.length) {
        ILogicChain ruleDefaultLogicChain = applicationContext.getBean(LogicModel.RULE_DEFAULT.getCode(), ILogicChain.class);
        // 写入缓存
        strategyChainGroup.put(strategyId, ruleDefaultLogicChain);
        return ruleDefaultLogicChain;
    }
    // 按照配置顺序装填用户配置的责任链；rule_blacklist、rule_weight 「注意此数据从Redis缓存中获取，如果更新库表，记得在测试阶段手动处理缓存」
    ILogicChain logicChain = applicationContext.getBean(ruleModels[0], ILogicChain.class);
    ILogicChain current = logicChain;
    for (int i = 1; i < ruleModels.length; i++) {
        ILogicChain nextChain = applicationContext.getBean(ruleModels[i], ILogicChain.class);
        current = current.appendNext(nextChain);
    }
    // 责任链的最后装填默认责任链
    current.appendNext(applicationContext.getBean(LogicModel.RULE_DEFAULT.getCode(), ILogicChain.class));
    // 写入缓存
    strategyChainGroup.put(strategyId, logicChain);
    return logicChain;
}
```

- 抽象类实现接口责任链的装配逻辑接口，并定义责任链链路衔接的方法。
- DefaultLogicChain 是其中一个链的实现，同类的也都是做自己的逻辑。
- openLogicChain 是工厂方法，用于组装责任链，提供服务。

### 2. 规则树 - 动态配置

#### 2.1 业务场景

当我们在实现业务流程编码时看到有些流程是带有判断和分支走向的，那么就不太适合用单一的责任链处理。比如一个流程中需要对抽奖的奖品进行交叉判断，抽中后判断是否满足中奖条件，满足后走库存处理，不满足走兜底处理。另外库存不足则也要走兜底处理。那么这样就是一个分叉的流程了。可以使用规则树进行实现。

#### 2.2 设计类图

<div align="center">
    <img src="https://bugstack.cn/images/article/develop/xfg-dev-tech-design-240528-05.png" width="750px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/article/develop/xfg-dev-tech-design-240528-06.png" width="750px">
</div>

- 首先，定义出规则树接口，并实现出对应的业务逻辑节点。包括；次数锁、库存扣减、兜底奖品。—— 次数锁判断为抽奖时，必须抽奖N次才可以获得某个奖品。
- 之后，设计执行器，负责完成规则节点的执行分支，如从A到B，如果B的条件满足XXX，则走到另外一个节点。而执行器中的节点来自于数据库的配置，这样就可以动态的调整各个节点的走向了。
- 最后，交给规则树工厂，完成执行器的服务提供。

#### 2.3 核心代码

<div align="center">
    <img src="https://bugstack.cn/images/article/develop/xfg-dev-tech-design-240528-07.png" width="450px">
</div>

```java
public interface ILogicTreeNode {

    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue, Date endDateTime);

}

@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {

    @Resource
    private IStrategyRepository repository;

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue, Date endDateTime) {
        log.info("规则过滤-次数锁 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);

        long raffleCount = 0L;
        try {
            raffleCount = Long.parseLong(ruleValue);
        } catch (Exception e) {
            throw new RuntimeException("规则过滤-次数锁异常 ruleValue: " + ruleValue + " 配置不正确");
        }

        // 查询用户抽奖次数 - 当天的；策略ID:活动ID 1:1 的配置，可以直接用 strategyId 查询。
        Integer userRaffleCount = repository.queryTodayUserRaffleCount(userId, strategyId);

        // 用户抽奖次数大于规则限定值，规则放行
        if (userRaffleCount >= raffleCount) {
            log.info("规则过滤-次数锁【放行】 userId:{} strategyId:{} awardId:{} raffleCount:{} userRaffleCount:{}", userId, strategyId, awardId, userRaffleCount, userRaffleCount);
            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                    .build();
        }

        log.info("规则过滤-次数锁【拦截】 userId:{} strategyId:{} awardId:{} raffleCount:{} userRaffleCount:{}", userId, strategyId, awardId, userRaffleCount, userRaffleCount);

        // 用户抽奖次数小于规则限定值，规则拦截
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .build();
    }

}

@Override
public DefaultTreeFactory.StrategyAwardVO process(String userId, Long strategyId, Integer awardId, Date endDateTime) {
    DefaultTreeFactory.StrategyAwardVO strategyAwardData = null;
    // 获取基础信息
    String nextNode = ruleTreeVO.getTreeRootRuleNode();
    Map<String, RuleTreeNodeVO> treeNodeMap = ruleTreeVO.getTreeNodeMap();
    // 获取起始节点「根节点记录了第一个要执行的规则」
    RuleTreeNodeVO ruleTreeNode = treeNodeMap.get(nextNode);
    while (null != nextNode) {
        // 获取决策节点
        ILogicTreeNode logicTreeNode = logicTreeNodeGroup.get(ruleTreeNode.getRuleKey());
        String ruleValue = ruleTreeNode.getRuleValue();
        // 决策节点计算
        DefaultTreeFactory.TreeActionEntity logicEntity = logicTreeNode.logic(userId, strategyId, awardId, ruleValue, endDateTime);
        RuleLogicCheckTypeVO ruleLogicCheckTypeVO = logicEntity.getRuleLogicCheckType();
        strategyAwardData = logicEntity.getStrategyAwardVO();
        log.info("决策树引擎【{}】treeId:{} node:{} code:{}", ruleTreeVO.getTreeName(), ruleTreeVO.getTreeId(), nextNode, ruleLogicCheckTypeVO.getCode());
        // 获取下个节点
        nextNode = nextNode(ruleLogicCheckTypeVO.getCode(), ruleTreeNode.getTreeNodeLineVOList());
        ruleTreeNode = treeNodeMap.get(nextNode);
    }
    // 返回最终结果
    return strategyAwardData;
}
```

- 定义规则树的接口，并根据业务实现相应的业务逻辑节点。举例中是规则过滤-次数锁节点实现，用于的抽奖次数大于限定才会发放奖品，否则就会流转到下一个节点。
- 节点的调用在 DecisionTreeEngine#process 方法中执行，它会从数据库获取数据执行节点链路。

### 3. 规则树 - 代码控制

#### 3.1 业务场景

在我们的业务场景中，有时候既不是走责任链，也不是走配置到库上的规则树，而是介于两者直接。由代码控制的节点走向，根据每个节点实现逻辑，动态处理下一个节点的实现。

如，一个流程中进入总人口，之后判断是否开量、账户数据、之后从账户数据开始又有3个级别判断。这3级别是根据账户数据的结果判断的。

最后，这里还要有一个上下文数据记录，所有的节点完成后填充数据。

#### 3.2 设计类图

<div align="center">
    <img src="https://bugstack.cn/images/article/develop/xfg-dev-tech-design-240528-08.png" width="450px">
</div>

- 首先，定义2个接口，一个是策略的执行接口 StrategyHandler，这个接口除了手里逻辑执行外，还要做一个兜底的上线文参数填充方法，也就是接口的默认方法。一个是策略映射接口 StrategyMapper。映射接口的作用是为了让每个节点实现类，可以动态的控制当前节点走到下一个节点的逻辑处理。
- 之后，按照业务诉求实现各个节点，每个节点都是继承抽象类（定义通用方法，和受理执行下一个节点的操作）。这些节点自己决定下一个节点走到哪里。

#### 3.3 核心代码

<div align="center">
    <img src="https://bugstack.cn/images/article/develop/xfg-dev-tech-design-240528-09.png" width="450px">
</div>

```java
public interface StrategyMapper {

    /**
     * 获取策略处理器
     */
    StrategyHandler get(DefaultStrategyFactory.MaterialVO materialVO, DefaultStrategyFactory.DynamicContext dynamicContext);

}

public interface StrategyHandler {

    /**
     * 处理最终返回结果
     */
    StrategyHandler DEFAULT = (materialVO, dynamicContext) -> {
        DefaultStrategyFactory.DecisionOutcomeVO decisionOutcomeVO = new DefaultStrategyFactory.DecisionOutcomeVO();
        decisionOutcomeVO.setLevel(dynamicContext.getLevel());
        return decisionOutcomeVO;
    };

    /**
     * 受理策略处理
     */
    DefaultStrategyFactory.DecisionOutcomeVO apply(DefaultStrategyFactory.MaterialVO materialVO, DefaultStrategyFactory.DynamicContext dynamicContext) throws Exception;

}

public abstract class AbstractStrategyRouter implements StrategyMapper, StrategyHandler {

    @Getter
    @Setter
    protected StrategyHandler defaultStrategyHandler = StrategyHandler.DEFAULT;

    /**
     * 行为路由
     */
    public DefaultStrategyFactory.DecisionOutcomeVO router(DefaultStrategyFactory.MaterialVO materialVO, DefaultStrategyFactory.DynamicContext dynamicContext) throws Exception {
        StrategyHandler strategyHandler = get(materialVO, dynamicContext);
        if (null != strategyHandler) return strategyHandler.apply(materialVO, dynamicContext);
        return defaultStrategyHandler.apply(materialVO, dynamicContext);
    }

}

@Component
public class AccountNode extends AbstractStrategyRouter {

    private final MemberLevel0Node memberLevel0Node;
    private final MemberLevel1Node memberLevel1Node;
    private final MemberLevel2Node memberLevel2Node;

    public AccountNode(MemberLevel0Node memberLevel0Node, MemberLevel1Node memberLevel1Node, MemberLevel2Node memberLevel2Node) {
        this.memberLevel0Node = memberLevel0Node;
        this.memberLevel1Node = memberLevel1Node;
        this.memberLevel2Node = memberLevel2Node;
    }

    @Override
    public DefaultStrategyFactory.DecisionOutcomeVO apply(DefaultStrategyFactory.MaterialVO materialVO, DefaultStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("【账户节点】规则决策树 userId:{}", materialVO.getUserId());

        // 1. 模拟查询用户级别
        int level = new Random().nextInt(3);
        log.info("模拟查询用户级别 level:{}",level);

        dynamicContext.setLevel(level);

        return router(materialVO, dynamicContext);
    }

    @Override
    public StrategyHandler get(DefaultStrategyFactory.MaterialVO materialVO, DefaultStrategyFactory.DynamicContext dynamicContext) {
        switch (dynamicContext.getLevel()){
            case 0:
                return memberLevel0Node;
            case 1:
                return memberLevel1Node;
            case 2:
                return memberLevel2Node;
            default:
                return defaultStrategyHandler;
        }
    }

}
```

- 通过最后的实现类可以看到，节点的执行是通过在本节点注入下一个要实现的节点，之后由get这个StrategyMapper接口的方法判断要走哪个节点去。
- apply 受理执行方法执行完毕后，则调用路由方法，路由方法是抽象类中的方法，用于操作执行下一个节点的处理。
- 所有执行链路上的数据，都有 DynamicContext 动态上下文进行收集，最后由 StrategyHandler 的 default 方法进行最终的结果数据拼装返回。

>这样的编码是不很爽，设计上即防腐又仿佛开启了新世界的大门！代码原来还能写的这么优雅！加入小傅哥即可获得整套项目代码学习。
