---
title: 归档：架构设计
lock: no
---

# 归档：架构设计

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

# 一、介绍

用于归档星球中关于架构方案设计类资料，包括；电商、金融、交易、账务、营销、活动、风控等各类场景。星球用户也可以通过在文章下编辑提交PR的方式共同维护。

- [架构师成长介绍](https://t.zsxq.com/05VJiQf66)
- [架构师方向，该怎么入门？](https://t.zsxq.com/056AUzFqr)
- [2年研发应该要会那些东西？](https://t.zsxq.com/05y7mAMju)
- [怎么做述职答辩](https://t.zsxq.com/05rjqZNB6)
- [跟公司大领导做项目汇报一般从什么角度切入](https://t.zsxq.com/05R3jQF23)

## 二、营销活动

- [营销中心架构设计](https://t.zsxq.com/056eiMrJ6)
- [营销活动倒排设计](https://t.zsxq.com/05aIYRb2F)
- [营销优惠券叠加使用](https://t.zsxq.com/05AUVZRBu)
- [营销结算拆分](https://t.zsxq.com/05FQzbAqn)
- [秒杀的时候如何防止超卖](https://t.zsxq.com/05eYFIYZv)
- [优惠活动和优惠券过滤条件展示设计](https://t.zsxq.com/05Jau7Ybe)
- [设计秒杀流程时，优化锁的颗粒度力度](https://t.zsxq.com/05EujY7IY)
- [为什么要采用异步扣减库存呢？](https://t.zsxq.com/05FUVJqjq)
- [邀请新人活动，活动规则和邀请记录怎么维护比较好？](https://t.zsxq.com/07qNBm2V3)
- [电商交易给其他用户打赏设计](https://t.zsxq.com/07euV7a2j)
- [优惠券快过期了，该怎么短信提醒用户，用户优惠券表数据量大的话怎么做合适](https://t.zsxq.com/08Mc52AFC)

## 三、电商场景

- [电商业务系统边界拆分](https://t.zsxq.com/05i2jiE6a)
- [收货地址自动识别](https://t.zsxq.com/05FAiy7AE)
- [单号预热，缓存队列消费](https://t.zsxq.com/05A276aEI)
- [开源电商项目分享](https://t.zsxq.com/05Ay7IeUJ)
- [高并发下的库存超卖问题的解决方式](https://t.zsxq.com/05IUBiAM3)
- [营销费用分摊](https://t.zsxq.com/05VnqB6Qv)

## 四、外卖场景

- [外卖点餐项目](https://t.zsxq.com/05Ey3fIUN)
- [就是假如有一个外卖项目，项目有单品、套餐两个实体，套餐就是多个单品的集合，然后要实现一个单品停售的功能，要求单品停售时，包含该单品的套餐也要一起停售](https://t.zsxq.com/08YnnhVxn)

## 五、支付交易

- [重构简单版支付中心](https://t.zsxq.com/05MVjmMFI)
- [我支付接口里面有点复杂，里面参杂了一些其它合作商相关需求，可以怎么去设计优化这一块](https://t.zsxq.com/07c20Wpwr)

## 六、功能服务

- [系统监控推送](https://t.zsxq.com/05FQzbAqn)
- [网关API调用次数统计](https://t.zsxq.com/05YzbEu76)
- [敏感词方案设计](https://t.zsxq.com/05uZf2nI6)
- [Token过期续租](https://t.zsxq.com/05NZJEaqv)
- [部分数据访问权限](https://t.zsxq.com/05Y7euVBE)
- [系统黑名单](https://t.zsxq.com/05MVZnYJi)
- [导出大量Excel](https://t.zsxq.com/05aIaqjyF)
- [现在有没有比较好用的分布式哈希表（DHT，Distributed Hash Table）](https://t.zsxq.com/05QZZjiiq)
- [物联网平台数据推送设计](https://t.zsxq.com/05fAUfi2N)
- [我现在要做一个接口计费和计数，接口调用一次 就要计算一次，接口调用之前也要校验是否达到接口调用次数上限了](https://t.zsxq.com/07BQFA6AI)

## 七、通用设计

- [数据库主从同步架构思考](https://t.zsxq.com/05QZ7ubY7)
- [CQRS模型设计](https://t.zsxq.com/05j2RfqzR)
- [如何保证MQ消息全部消费成功](https://t.zsxq.com/05qNRJ6qF)
- [多文件类型，数据同步入库](https://t.zsxq.com/05nmm62ZJ)
- [宽表查询设计](https://t.zsxq.com/05mu3vfuF)
- [分库分表跨库查询](https://t.zsxq.com/05zbyRJae)
- [自定义字段存放数据](https://t.zsxq.com/05AMFY7QV)
- [PDF 文件解析](https://t.zsxq.com/05AUzRjmm)
- [多渠道消息推送](https://t.zsxq.com/0562Fy3nm)
- [C/S架构数据采集场景](https://t.zsxq.com/05Ba2r72f)
- [微服务系统中的分布式事务](https://t.zsxq.com/05yVFyz3B)
- [二维码幂等生成设计](https://t.zsxq.com/05vzrrvZF)
- [基于证书的方式，实现单点登录](https://t.zsxq.com/05rj6aIiQ)
- [如何保证分页数据不重复问题](https://t.zsxq.com/057U3fiai)
- [分布式下最终一致性怎么保证](https://t.zsxq.com/05faQjEU7)
- [有关redis和mysql数据一致性的问题](https://t.zsxq.com/05FEubIIq)
- [Mysql的主从复制一致性怎么保证呢？](https://t.zsxq.com/05rfaYjqB)
- [ID生成策略问题](https://t.zsxq.com/05IiIynmi)
- [C/S架构数据采集](https://t.zsxq.com/05Eq3ZVVF)
- [动态导出字段设计——类似问卷表](https://t.zsxq.com/056QFaQvj)
- [文件传输OSS](https://t.zsxq.com/05ii2fyJu)
- [分库分表场景设计](https://t.zsxq.com/05rNniu72)
- [SSO单点登录](https://t.zsxq.com/07j5YTfDg)
- [运营平台需与各线下系统交互](https://t.zsxq.com/07xHH1EZE)
- [PDF 部分在线预览功能](https://t.zsxq.com/07HImi4T3)
- [想问一下各位大佬是如何学习权限的，感觉现在学的完全不够安全](https://t.zsxq.com/08y2ZygbU)
- [A方推送数据到我们平台，我们平台调用B方接口存数据，同时对数据做一些留痕等。](https://t.zsxq.com/08tmSlDOA)
- [咨询个小时维度库存设计问题；现在有这么个场景，下单占用库存，还车释放库存。](https://t.zsxq.com/08VmJAsho)

## 八、性能调优

- [QPS和TPS的区别，以及如何计算](https://t.zsxq.com/05fEeimQB)
- [首页QPS=2W查询优化](https://t.zsxq.com/053JqVFUJ)
- [系统压测](https://t.zsxq.com/05iYzNrzb)
- [压测步骤](https://t.zsxq.com/05QnaUBAm)
- [慢查询拖垮数据库](https://t.zsxq.com/05y3zvji2)
- [海量数据处理](https://t.zsxq.com/05BIei2Fq)
- [token太长3000+位往cookie写的时候会有问题](https://t.zsxq.com/052JImmuV)
- [有张表数据量为8000w字段有100多个，这样的表查询效率很慢，如何处理得以提升效率](https://t.zsxq.com/074yWu3X6)

## 九、架构方案

- [整洁结构](https://t.zsxq.com/05EUfMJUj)
- [六边形架构](https://t.zsxq.com/05I6YrrjM)
- [什么是微服务架构？](https://t.zsxq.com/05EEQzbUr)
- [为什么你们要用DDD架构设计项目？](https://t.zsxq.com/05AIqFIEa)
- [项目中为什么使用Dubbo](https://t.zsxq.com/05uFMniYJ)
- [设计一个系统，应该要考虑/着重要关注的点在那些？](https://t.zsxq.com/05ybeaa6Y)
- [项目划分多个微服务，有关微服务之间调用时设计的分布式事务问题](https://t.zsxq.com/05F6M7i2R)
- [服务拆分，领域和组件](https://t.zsxq.com/05F23rFq3)
- [DDD理论提出来很久了，为什么很多公司还在用MVC？](https://t.zsxq.com/05AIqFIEa)
- [可以直接在领域层调用仓储服务吗？](https://t.zsxq.com/05iMVvN7m)
- [将一个大事务拆分成几个小事务](https://t.zsxq.com/05RZRnayr)
- [单库单表拆分到分库分表，数据迁移处理](https://t.zsxq.com/05NzvBAai)
- [目前公司有个迁移老项目的任务，逐步替换掉不再使用的老系统](https://t.zsxq.com/05F2jamaI)
- [枚举字典表定义](https://t.zsxq.com/05feuVZf2)
- [使用DDD重构的优势是什么？](https://t.zsxq.com/05eyJqjeI)
- [分布式任务调度设计](https://t.zsxq.com/05uBa2VVF)
- [简化的DDD结构](https://t.zsxq.com/05vnuZfIu)
- [开发一个大系统，包括条码系统、MES系统、OA系统、客户管理系统、供应商管理系统等，架构分层思路](https://t.zsxq.com/06NBAeaA6)
- [微服务项目中MQ有必要单独搞个服务去处理消息吗？](https://t.zsxq.com/07H0diIGB)
- [类似钉钉文档协同编辑的功能](https://t.zsxq.com/088NBXEzT)
- [“当前流水表”的前一天的所有记录 导入到 “历史流水表”，成功后把这些记录从“当前流水表”中删除。](https://t.zsxq.com/08UGtaEda)

## 十、系统重构

- [系统重构经验](https://t.zsxq.com/05vz3VJa2)
- [部门最近想把几个产品线的相同业务模块抽出来复用](https://t.zsxq.com/05ZNfyNju)
- [用户将数据推送到公司系统，公司系统将处理后的数据回调到用户系统，这个时候是集群中的单服务回调，此时服务器的算力资源无法充分利用](https://t.zsxq.com/07vz3rzNr)

## 十一、其他场景

- [朋友圈好友动态](https://t.zsxq.com/05UFuzJE6)
- [禅道Bug数据设计](https://t.zsxq.com/053RNfY3z)
- [飞机黑匣子的安全性设计是不是已经跟不上时代步伐了啊？](https://t.zsxq.com/05UrRZFem)
- [代码混淆和反编译](https://t.zsxq.com/056eIqBMz)
- [项目运行较慢，重启下就好了，一般是什么原因导致的](https://t.zsxq.com/05eyz7QJ2)
- [规则引擎处理上报数据](https://t.zsxq.com/05FEurBmM)
- [短信平台](https://t.zsxq.com/05n6ybyFy)
- [定时任务衰减调用](定时任务执行时间的逐步衰减)
- [OCR服务使用整合](https://t.zsxq.com/05YJQVvFm)
- [异常码定义](https://t.zsxq.com/05rFqV3ZN)
- [如果数据库的一张表数据有2千万，对这张表做增删改查要注意什么？](https://t.zsxq.com/053RRRFIa)
- [技术词：“弹性ip”、“固定ip”、“csrf攻击”](https://t.zsxq.com/05yvvf27e)
- [支付后无支付结果轮训查询&宽表数据查询](https://t.zsxq.com/05iMZjYfq)
- [Minio 案例](https://t.zsxq.com/05BEqRnei)、[Minio 使用](https://t.zsxq.com/05IQVJ2rz)
- [如何优雅地调用受频率限制的第三方接口？](https://t.zsxq.com/05QnyFuVb)
- [针对来自外部系统恶意异常访问怎么监控到呢？](https://t.zsxq.com/05rznqJay)
- [java有没有什么办法能远程监控服务器的一些性能指标，比如cpu、内存使用率之类的？](https://t.zsxq.com/05YvVvNvN)
- [老项目原来是用weblogic部署的项目，Hibernate 速度慢优化](https://t.zsxq.com/06Fuv7amq)
- [业界在设计业务系统时，如何选择数据库的隔离级别，有没有什么标准，最常用的可重复读RR和读已提交RC使用场景是怎样的！目前我只感受到交易系统使用RC更合适，（又或者说什么场景使用RR）业界对这些场景有没有什么通用的选择方案吗](https://t.zsxq.com/06qBaaEUf)
- [云原生是什么？](https://t.zsxq.com/06VnIeeUv)
- [公司也有个需求合作方传过来的值和我们这边的值不一样，要做一一对应要求可扩展性强](https://t.zsxq.com/06qz7aunu)
- [服务多租户场景使用](https://t.zsxq.com/06ZfmqnmY)
- [加强产品配置信息的校验，不使用 if···else、validation 可以怎么做？](https://t.zsxq.com/06fMvf6M7)
- [目前有14个库，每个库其中有一个用户表，里面存储着用户信息，其中包括id,city等信息，14个库总共14亿数据（总之数据量很大），怎么统计出各个城市有多少用户，该怎么设计？](https://t.zsxq.com/07MZZBQfq)
- [系统的分支维护](https://t.zsxq.com/08VTJabHq)
- [设计完成一个全链路监控系统需要具备哪些技术栈和能力？](https://t.zsxq.com/097do563T)