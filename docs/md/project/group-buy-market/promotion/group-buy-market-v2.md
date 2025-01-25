---
title: 总结通用模型设计提取，设计模式玩的贼6！
lock: no
---

# 《拼团交易平台系统》总结通用模型设计提取，设计模式玩的贼6！

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是小傅哥。

近几周在做公司的一个项目重构，有多复杂呢！`整个工程近40万行代码`，`重构部分单个类超过3000行`。历史最长周期的代码有`快10年以上`。而这东西到我手了，再继续这么写，早晚搞出事故！**所以，我决定改变一下。**

<div align="center">
    <img src="https://bugstack.cn/images/article/project/group-buy-market/group-buy-market-promotion-250118-01.gif" width="200px">
</div>

**你不可能停了业务需求再重构！**

在互联网公司中，产品需求交付永远是第1位的，他们不关心你代码是怎么实现的。但不要出问题，出了问题就是你的问题。

而长期需求迭代经历数年的代码，早已腐化的非常严重。但重构又绝对是非常考验工程师的业务经验和技术能力的。**往往重构不佳，就从维护一份屎山，到维护两份啦！**

但好在，我的工程落地经验积累较多，通过巧妙的设计模式运用，能从复杂的流程中划分出边界结构，从而降低业务功能的理解难度。与此同时，为了让其他的伙伴在不是特别能熟练使用设计模式的情况下，我把设计模式常用的模型结构，独立成一个单独的组件包，主要引入就可以按照这样的结构来开发代码。从一定程度上，让大家的代码都能在不错的水平上。

>如果，你也想提高自己的代码能力和工程驾驭能力，那么可以跟着小傅哥一起学习下。

## 一、实践出真知

编程这东西，最好的学习方式就是实践。拿一个真实项目，结合实际的需求，上手操作一遍，小白也能扎扎实实的学习到实际的技术运用。

所以，小傅哥为了让大家能学习到，关于工程中运用设计模式，解决实际场景的重构问题。专门提供了一个新的项目，在项目中解耦出标准的通用设计模型结构，再到需求中运用处理实际问题。

这个项目就是小傅哥的技术社群，星球「码农会锁」，最新的第14个项目《拼团交易平台系统》，结合实际场景设计和落地的设计模式方案。—— 这个项目，会给你设计模式更牛的认知，妥妥的让你可以在自己的项目使用上设计模式！

<div align="center">
    <img src="https://bugstack.cn/images/article/project/group-buy-market/group-buy-market-241109-01.png" width="800px">
</div>

`拼多多`、`京东`、`腾讯`，都有很多这样的拼团场景，之所以选择这样的业务需求，就是让大家可以结合实际的项目来学习真实的技术。**这套项目目前已经进行了13节，全程视频+小册，手把手带着设计和编码。**

>接下来，我们就看看，小傅哥是怎么带着大家玩转设计模式的！

## 二、提炼通用模型

`条条大路通罗马，但放到项目工程中，就有点受不了了！`

咋回事呢？🤔

只要不加规范，同样是做规则树、责任链、策略模式，都能有非常多的方式。那么，每来一个新人，就可能再多出来一份新的设计方式。这些一类的但非常差异化的东西，在下一个人要迭代需求的时候，就会有非常多的地方，有不同的改法。可能想死的新都有。

所以，为了把这些东西规范化，小傅哥设计了一套模型模板，单独提炼于项目需求逻辑之外。所有，有需要使用的场景，都可以引入使用。大家都在一个标准下使用设计模式，维护、迭代、扩展，也都非常容易。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/group-buy-market/group-buy-market-promotion-250118-02.png" width="400px">
</div>

- 首先，随着拼团项目的开发，定义2套了标准的通用设计模式结构。2种模式的责任链和1套规则树。这套责任链还用到了大家常学的数据结构中的链表设计和实现。
- 之后，这套模型非常巧妙的运用到业务场景中。举例像是规则树，还提供了多线程数据异步加载，自由化功能逻辑编排。这样的设计模式，可以解决非常多的场景问题。

> 为了更好的理解这样的模型运行，接下来，小傅哥甩个图给大家感受下！—— 小傅哥，画图可牛了，见图就知意。

## 三、小图很美！💐

如图，是拼团的部分流程。营销类场景的系统都是很复杂的，它的流程节点非常多，也经常做扩展和调整。所以要做非常细腻的设计，满足复杂流程的实现。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/group-buy-market/group-buy-market-promotion-250118-06.png" width="600px">
</div>

- 以上这两部分流程分别包括试算和锁定优惠，这里会使用到规则树和责任链进行实现。
- 下面，就介绍下关于规则树和责任链的设计和使用。

### 1. 规则树

`千万别觉得设计模式没有用`，否则也不会那么多源码框架中，会有那么多设计模式使用。MyBatis 一个框架，都用了10种设计模式！

像是这些设计模式，合理的设计好，是真的可以解决非常复杂的业务流程编码，让代码变得优雅。既不会有那么多的 if···else，也不会让代码逻辑成片的堆到一个类中。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/group-buy-market/group-buy-market-promotion-250118-03.png" width="600px">
</div>

- 如图，这是一个规则树模型，解决的拼团中实际的场景业务。从根节点、开关节点、营销节点、人群节点，再到最终的正常和异常结束节点。每个节点分别处理自己的业务流程。
- 你可以想象，如果没有这样的设计模式模型结构，那么在代码中，就是一个大方法中，一堆的逻辑编写，维护的成本是非常高的。

**展示一段代码，让兄弟👬🏻看看这是啥样的！**

```java
@Slf4j
@Service
public class MarketNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    /**
     * <a href="https://bugstack.cn/md/road-map/spring-dependency-injection.html">Spring 注入详细说明</a>
     */
    @Resource
    private Map<String, IDiscountCalculateService> discountCalculateServiceMap;
    @Resource
    private ErrorNode errorNode;
    @Resource
    private TagNode tagNode;

    @Override
    protected void multiThread(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        // 异步查询活动配置
        QueryGroupBuyActivityDiscountVOThreadTask queryGroupBuyActivityDiscountVOThreadTask = new QueryGroupBuyActivityDiscountVOThreadTask(requestParameter.getActivityId(), requestParameter.getSource(), requestParameter.getChannel(), requestParameter.getGoodsId(), repository);
        FutureTask<GroupBuyActivityDiscountVO> groupBuyActivityDiscountVOFutureTask = new FutureTask<>(queryGroupBuyActivityDiscountVOThreadTask);
        threadPoolExecutor.execute(groupBuyActivityDiscountVOFutureTask);

        // 异步查询商品信息 - 在实际生产中，商品有同步库或者调用接口查询。这里暂时使用DB方式查询。
        QuerySkuVOFromDBThreadTask querySkuVOFromDBThreadTask = new QuerySkuVOFromDBThreadTask(requestParameter.getGoodsId(), repository);
        FutureTask<SkuVO> skuVOFutureTask = new FutureTask<>(querySkuVOFromDBThreadTask);
        threadPoolExecutor.execute(skuVOFutureTask);

        // 写入上下文 - 对于一些复杂场景，获取数据的操作，有时候会在下N个节点获取，这样前置查询数据，可以提高接口响应效率
        dynamicContext.setGroupBuyActivityDiscountVO(groupBuyActivityDiscountVOFutureTask.get(timeout, TimeUnit.MINUTES));
        dynamicContext.setSkuVO(skuVOFutureTask.get(timeout, TimeUnit.MINUTES));

        log.info("拼团商品查询试算服务-MarketNode userId:{} 异步线程加载数据「GroupBuyActivityDiscountVO、SkuVO」完成", requestParameter.getUserId());
    }

    @Override
    public TrialBalanceEntity doApply(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("拼团商品查询试算服务-MarketNode userId:{} requestParameter:{}", requestParameter.getUserId(), JSON.toJSONString(requestParameter));

        // 获取上下文数据
        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = dynamicContext.getGroupBuyActivityDiscountVO();
        if (null == groupBuyActivityDiscountVO) {
            return router(requestParameter, dynamicContext);
        }

        GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount = groupBuyActivityDiscountVO.getGroupBuyDiscount();
        SkuVO skuVO = dynamicContext.getSkuVO();
        if (null == groupBuyDiscount || null == skuVO) {
            return router(requestParameter, dynamicContext);
        }

        // 优惠试算
        IDiscountCalculateService discountCalculateService = discountCalculateServiceMap.get(groupBuyDiscount.getMarketPlan());
        if (null == discountCalculateService) {
            log.info("不存在{}类型的折扣计算服务，支持类型为:{}", groupBuyDiscount.getMarketPlan(), JSON.toJSONString(discountCalculateServiceMap.keySet()));
            throw new AppException(ResponseCode.E0001.getCode(), ResponseCode.E0001.getInfo());
        }

        // 折扣价格
        BigDecimal deductionPrice = discountCalculateService.calculate(requestParameter.getUserId(), skuVO.getOriginalPrice(), groupBuyDiscount);
        dynamicContext.setDeductionPrice(deductionPrice);

        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        // 不存在配置的拼团活动，走异常节点
        if (null == dynamicContext.getGroupBuyActivityDiscountVO() || null == dynamicContext.getSkuVO() || null == dynamicContext.getDeductionPrice()) {
            return errorNode;
        }

        return tagNode;
    }

}
```

这是其中的一个营销节点，处理拼团营销试算的过程；

1. multiThread 负责异步多线程方式加载数据，节省数据获取时间。
2. doApply 处理业务流程，最后用 router 路由到下一个节点。
3. get 是获取到下一个节点，是走到哪里由这块判断。

<div align="center">
    <img src="https://bugstack.cn/images/article/project/group-buy-market/group-buy-market-promotion-250118-04.png" width="400px">
</div>

> 这套结构模型可以解决非常多的业务场景，而且每一个类都可以非常清晰的表达出具体的业务逻辑。无论以后是维护代码、迭代需求还是排查线上问题都变得很容易。

### 2. 责任链

规则树与责任链相比，规则树会有很多分叉流程，但责任链比较轻量主要负责单链路流程，在编码过程中不需要考虑流程的流转。相对来说更轻量了。

这里小傅哥设计了2套责任链模型，model1 - 单例链、model2 - 多例链（动态组装多套）。如图；

<div align="center">
    <img src="https://bugstack.cn/images/article/project/group-buy-market/group-buy-market-promotion-250118-05.png" width="650px">
</div>

- 如图，ILink 使用了数据结构责任链模型，ILogicHandler 为处理具体的业务功能逻辑。这里巧妙的设计在于把链的管理和业务逻辑的受理进行拆分，之后再由 LinkArmory 进行链路装配。**「当你看到代码的时候会觉得这真爽！原来写代码可以这样有意思」**
- 因为 LinkedList 实现如链表一样的方法，有添加，也有摘除链，所以还可以根据数据库配置动态处理链的实例化。

```java
@Service
public class Rule02TradeRuleFactory {

    @Bean("demo01")
    public BusinessLinkedList<String, DynamicContext, XxxResponse> demo01(RuleLogic201 ruleLogic201, RuleLogic202 ruleLogic202) {

        LinkArmory<String, DynamicContext, XxxResponse> linkArmory = new LinkArmory<>("demo01", ruleLogic201, ruleLogic202);

        return linkArmory.getLogicLink();
    }

    @Bean("demo02")
    public BusinessLinkedList<String, DynamicContext, XxxResponse> demo02(RuleLogic202 ruleLogic202) {

        LinkArmory<String, DynamicContext, XxxResponse> linkArmory = new LinkArmory<>("demo02", ruleLogic202);

        return linkArmory.getLogicLink();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {
        private String age;
    }

}
```

- 之后就可以动态的使用这套责任链完成功能需求的处理。demo01、demo02，分别组装了自己的责任链。

> 以上，在小傅哥的全部项目中也只是冰山一角，如果你加入小傅哥的社群学习，我还会让你感受到更多的关于工程在实际场景下设计的魅力！

## 四、一套学习进阶路线图！

**10年互联网大厂的经历，得出**；`最快的成长方式，是不要重复做一件事，而是能成体系的，有阶段性的成长。`

为此小傅哥做了一整套的实战学习进阶项目，从小白到大佬，全程视频手把手带着从0到1，一步步完成项目的设计、开发和上线。在整套内容学习过程中，小傅哥为你提供了非常好的技术交流社群，及时解决学习问题。还包括调试你的问题代码，带你快速🔜出坑！

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/zsxq-241007-02.png" width="550px">
</div>

**扫码加入即可获得全部项目学习！**

- 首先，这一整套全体系的学习课程比私教培训实惠，更比培训班上万的培训费便宜。可能也就是培训班1天的💰钱，就能学习到这一整套内容了。
- 之后，你学习的整套课程，就是小傅哥这个架构师自己全部原创编写的。这也就是说，你所提到的任何问题，小傅哥都能给你解答和讨论。
- 那么，这么实惠的课程，成体系的课程，还是架构师编写的。还有什么可犹豫的，完全可以撸起来了！

>🧧 注意，关注小傅哥公众号「bugstack虫洞栈」回复「星球」可以领取加入优惠哦！
