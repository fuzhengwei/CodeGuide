---
title: MVC2DDD - 架构重构
lock: need
---

# MVC2DDD - 架构重构

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)
<br/>项目：[大营销平台系统](https://bugstack.cn/md/project/big-market/ddd.html) - DDD 领域驱动设计，战略、战术、战役，落地指引规范。

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

<iframe id="B-Video" src="//player.bilibili.com/player.html?aid=576362586&bvid=BV1Dz4y1V7zf&cid=1276820371&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" width="100%" height="480"> </iframe>

大家好，我是技术UP主小傅哥。**MVC讲解了，DDD讲解了。接下来这个章节，我们讲讲从MVC到DDD的重构！**

为了避免概念的混淆和下文内容方便讲解，先进行DDD概念认知确认；

1. [Domain-driven design (DDD) is a major software design approach.](https://en.wikipedia.org/wiki/Domain-driven_design) 来自维基百科。DDD 是一种软件设计方法。
2. 软件的设计方法涵盖了；范式、模型、框架、方法论，主要活动包括建模、测试、工程、开发、部署、维护。来自维基百科的[软件设计](https://en.wikipedia.org/wiki/Software_design)涵盖信息介绍。
3. 所以在当前语境下，MVC与DDD的对比，只是对比软件落地的框架结构，并不是软件设计思想和思维建模的全方面对比。所以如果读者再给一些其他伙伴阐述MVC和DDD的对比时，也是有必要说明语境的，避免没必要的纠结于理论，而忽略了交流讨论的意义。

那么，接下来在此语境下，我们进行MVC和DDD的重构讲解；

**MVC 旧工程腐化严重，迭代成本太高。DDD 新工程全部重构，步子扯的太大。** 这是现阶段在工程体系化治理中，我们所面临的最大问题；`既想运用 DDD 的思想循序渐进重构现有工程，又想不破坏原有的工程体系结构以保持新需求的承接效率。`

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ddd-01.gif?raw=true" width="200px">
</div>

经过实践得知，DDD 架构能解决，现阶段 MVC 贫血结构中所遇到的众多问题。

众所周知，MVC 分层结构是一种贫血模型设计，它将”状态“和”行为“分离到不同的包结构中进行开发使用。domain 里写 po、vo、enum 对象，service 里写功能逻辑实现。也正因为 MVC 结构没有太多的约束，让前期的交付速度非常快。但随着系统工程的长期迭代，贫血对象开始被众多 serivice 交叉使用，而 service 服务也是相互调用。这样缺少一个上下文关系的开发方式，让长期迭代的 MVC 工程逐步腐化到严重腐化。

**MVC 工程的腐化根本**，就在于对象、服务、组件的交叉混乱使用。时间越长，腐化的越严重。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ddd-02.png?raw=true" width="850px">
</div>

在 MVC 的分层结构就像家里所有人的衣服放一个大衣柜、所有人的裤子放一个大库柜。衣服裤子(对象)，很少的时候很节省空间，**因为你的裤子别人可能也拿去穿，复用一下开发速度很快**。但时间一长，就越来越乱了。🤨 一条裤子被加肥加大，所有人都穿。

而 DDD 架构的模型分层，则是以人为视角，**一个人就是一个领域，一个领域内包括他所需的衣服、裤子、袜子、鞋子**。虽然刚开始有点浪费空间，但随着软件的长周期发展，后续的维护成本就会降低。

那么，接下来我们就着重看以下，从 MVC 到 DDD 的轻量化重构应该怎么做。🍻

>文章后面，含有 MVC 到 DDD 重构编码实践讲解。此文也是 MVC、DDD 的架构编码指导经验说明。

## 一、能学到啥

本文是偏实战可落地的 DDD 知识分享，也是从 MVC 到 DDD 的可落地方案讲解。在本文中会介绍 DDD 架构下的分层结构、调用全景图以及非常重要的 MVC 到 DDD 应该如何映射和编码。所以如下这一系列内容都是你能获得的知识；

1. DDD 领域驱动设计，对应的分层结构讲解。涵盖调用关系、依赖关系、对象转换以及各层的功能划分。—— 简单且清晰。
2. DDD 调用全景图，以一张全方位的结构关系调用视图，展开 DDD 的血脉流转关系。有了这一张视图，你会更加清楚的知道 DDD 的调用链路结构和各个代码都要写到那一层。
3. MVC 映射 DDD 后的调整方案，在尽可能低的成本下，让 MVC 结构具备 DDD 领域驱动设计的实现思想。这样的调整，可以在一定程度上，阻止旧工程的腐化程度，提高编码质量。同时也为后续从 MVC 到 DDD 的迁移，做好基础。
4. MVC、DDD 是工程设计骨架，设计原则、设计模式是工程实现血肉。所以设计模式也是本文要展示的重点内容。
5. 一整套实战开源课程；讲解在 DDD 架构中，各项技术栈；Dubbo、MQ、Redis、Zookeeper - 配置中心等的分层使用。—— 否则你可能都不知道一个 MQ 消息发送要放在哪里。有了 DDD 分层架构，这些东西会被归类的特别清晰。

此外，除了这些碎片化的知识学习，还有应用级实战项目锻炼；Lottery DDD 架构设计、ChatGPT 新DDD架构设计、API网关 会话设计 - 学习架构能力和编程思维，以及高端的编码技巧。

## 二、架构分层(DDD)

在 DDD 架构分层中，domain 模块最重要的，也是最大的那个。所有的其他模块都要围着它转。所有 domian 下的各个领域模块，都包含着一组完整的；model - 模型对象、service - 服务处理，以及在有需要操作数据库时，再引入对应的 IRepository - 仓储服务。这个 domain 的实现，就像是实现了一个炸药包，炸药包的火药、引线、包布等都是一个个物料被封装到一起使用。

如下是 DDD 架构所呈现出的一种四层架构分层，可能和一些其他的 DDD 分层略有差异，但核心的重点结构是不变的。尤其是 domain 领域、infrastructure 基础，是任何一个 DDD 架构分层都需要有的分层模块。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ddd-03.png?raw=true" width="650px">
</div>

- **应用封装 - app**：这是应用启动和配置的一层，如一些 aop 切面或者 config 配置，以及打包镜像都是在这一层处理。你可以把它理解为专门为了启动服务而存在的。
- **接口定义 - api**：因为微服务中引用的 RPC 需要对外提供接口的描述信息，也就是调用方在使用的时候，需要引入 Jar 包，让调用方好能依赖接口的定义做代理。
- **领域封装 - trigger**：触发器层，一般也被叫做 adapter 适配器层。用于提供接口实现、消息接收、任务执行等。所以对于这样的操作，这里把它叫做触发器层。
- **领域编排【可选】 - case**：领域编排层，一般对于较大且复杂的的项目，为了更好的防腐和提供通用的服务，一般会添加 case/application 层，用于对 domain 领域的逻辑进行封装组合处理。但对于一些小项目来说，完全可以去掉这一层。少量一层对象转换，代码的维护成本会降低很多。
- **领域封装 - domain**：领域模型服务，是一个非常重要的模块。无论怎么做DDD的分层架构，domain 都是肯定存在的。在一层中会有一个个细分的领域服务，在每个服务包中会有【模型、仓库、服务】这样3部分。
- **仓储服务 - infrastructure**：基础层依赖于 domain 领域层，因为在 domain 层定义了仓储接口需要在基础层实现。这是依赖倒置的一种设计方式。所有的仓储、接口、事件消息，都可以通过依赖倒置的方式进行调用。
- **类型定义 - gateway**：对于外部接口的调用，也可以从基础设施层分离一个专门的 gateway 网关层，来封装外部 RPC/HTTP 等类型接口的调用。
- **类型定义 - types**：通用类型定义层，在我们的系统开发中，会有很多类型的定义，包括；基本的 Response、Constants 和枚举。它会被其他的层进行引用使用。(这一层没有画到图中)

综上就是 DDD 架构思想下的工程分层模型结构，DDD 架构的领域驱动设计的重点包括；结构边界更加清晰、重视上下文调用、分离业务功能与基础支撑。总之一句话，就是各司其职。那么鉴于如此清晰工程结构，该如何将旧存工程，MVC 转向 DDD 呢？接下来就重点介绍下。

## 三、工程重构(MVC->DDD)

经过实践验证，不需要太高成本，MVC 就可以天然的向 DDD 工程分层的模型结构转变。重点是不改变原有的工程模块的依赖关系，将贫血的 domain 对象层，设计为充血的结构。**对于 domain 原本在 MVC 分层结构中，就是一个被依赖层，恰好可以与其他层做依赖倒置的设计方案处理**。具体如图所示；

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ddd-04.png?raw=true" width="650px">
</div>

左侧是我们常见的 MVC 分层结构，右侧是给大家上文讲解过的 DDD 分层结构。从 MVC 到 DDD 的映射，使用了相同颜色进行标注。之后我来介绍一些细节；

在 MVC 分层结构中，所有的逻辑都集中在 service 层，也是文中提到的腐化最严重的层，要治理的也是这一层。所以首先我们要将 service 里的功能进行拆解。

1. service 中具备领域特性的服务实现，抽离到原本贫血模型的 domain 中。在 domain 分层中添加 xxx、yyy、zzz 分层领域包，分别实现不同功能。**注意每个分层领域包内都具备完整的 DDD 领域服务内所需的模块**
2. service 中的基础功能组件，如；缓存Redis、配置中心等，迁移到 dao 层。这里我们把 dao 层看做为基础设施层。它与 domain 领域层的调用关系，为依赖倒置。也就是 domain 层定义接口，dao 层依赖于 domain 定义的接口，做依赖倒置实现接口。
3. service 本身最后被当做 application/case 层，来调用 domain 层做服务的编排处理。

因为恰好，MVC 分层结构中，也是 service 和 dao 依赖于 domain，这和 DDD 分层结构是一致的。所以经过这样的映射拆分代码实现调用结构后，并不会让工程结构发生变化。那么只要工程结构不发生变化，我们的改造成本就只剩下代码编写风格和旧代码迁移成本。

MVC 分层结构中的 export 层是 RPC 接口定义层，由 web 层实现。web 是对 service 的调用。也就是 DDD 分层结构中调用 application 编排好的服务。这部分无需改动。**但如果你原有工程把 domain 也暴漏出去了，则需要把对应的包迁移到 export** 因为 domain 包有太多的核心对象和属性，还包括数据库持久化对象。这些都不应该被暴漏。

MVC 分层中，因为有需要对外部 RPC 接口的调用，所以会单独有一层 RPC 来封装其他服务的接口。这一层被 domain 领域使用层，可以定义 adapter 适配器接口，通过依赖倒置，在 rpc 层实现 domain 层定义的调用接口。

此外 dao 层，在 MVC 结构中原本是比较单一的。但经过改造后会需要把基础的 Redis 使用、配置中使用，都迁移到 dao 层。因为原本在 service 层的话，domain 层是调用不到的这些基础服务的，而且也不符合服务功能边界的划分。

**综上**，就是从 MVC 到 DDD 重构架构的拆解实现方案。这是一种最低成本的最佳实施策略，完全可以保证 MVC 的结构，又可以应用上 DDD 的架构分层优势。也能运用 DDD 领域驱动设计思想，重构旧代码，增加可维护性。

到这里，分层结构问题我们说清楚了。从 MVC 调整结构到 DDD 后，工程模型中的调用链路关系是什么样呢？接下来我们在展开架构，看细节关系。

## 四、分层调用链路

接下来我们把 DDD 的分层架构平铺展开，看看从一个接口的实现到各个模块分层中的调用链路关系是什么样的。这样在做自己的代码开发中也可以参考到应该把什么的功能分配到哪个模块中处理。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ddd-05.png?raw=true" width="100%">
</div>

从APP层、触发器层、应用层，这三块主要对领域层的上下文逻辑封装、触发式(MQ、HTTP、JOB)使用，并最终在应用层中打包发布上线。这一部分的都是使用的处理，所以也不会有太复杂的操作。

当进入领域层开始，也是智力集中体现的开始了。所有你对工程的抽象能力，都在这一块区域体现。

接下来我们着种介绍下领域层和基础层的模块职责功能；**图中下方是对象的流转，可以注意下。**

### 1. 领域服务层

我们可以当 domain 领域层为一个充血模型结构，在一个 domain 领域层中，可以有多个领域包。当然理想状态下，如果你的 DDD 拆分的特别干净的新工程，那么可能一个 domain 就一个领域。但大部分时候微服务的拆分鉴于成本考虑不会那么细，还有一些老工程的重构，都是一个工程内有多个领域，对应的解决方案是在一个工程下建多个同级分层包。比如；账户领域包、授信领域包、结算领域包等，每个包内聚合实现不同的功能。

每一个 domain 下的领域包内，都包括；model 模型、仓储、接口、事件和服务的处理。

model 模型对象；

- aggreate：聚合对象，实体对象、值对象的协同组织，就是聚合对象。
- entity：实体对象，大多数情况下，实体对象(Entity)与数据库持久化对象(PO)是1v1的关系，但也有为了封装一些属性信息，会出现1vn的关系。
- valobj：值对象，通过对象属性值来识别的对象 By 《实现领域驱动设计》

repository 仓储服务；从数据库等数据源中获取数据，传递的对象可以是聚合对象、实体对象，返回的结果可以是；实体对象、值对象。因为仓储服务是由基础层(infrastructure) 引用领域层(domain)，是一种依赖倒置的结构，但它可以天然的隔离PO数据库持久化对象被引用。

adapter 接口服务；是依赖于外包的其他 HTTP/RPC 接口的封装调用，通过在 domain 领域层定义适配器接口，再有依赖于 domain 的基础层设施层或者一个单独的专门处理接口的额外分层，来实现 domain 定义的适配器接口，完成对依赖的 HTTP/RPC 进行封装处理。

event 事件消息；在服务实现中，进行会有业务完成后，对外发送消息的情况。这个时候，可以在领域模型中定义事件消息的接口，再有基础设施层完成消息的推送。

service 服务设计；这里要注意，不要以定义了聚合对象，就把超越1个对象以外的逻辑，都封装到聚合中，这会让你的代码后期越来越难维护。聚合更应该注重的是和本对象相关的单一简单封装场景，而把一些重核心业务方到 service 里实现。**此外；如果你的设计模式应用不佳，那么无论是领域驱动设计、测试驱动设计还是换了三层和四层架构，你的工程质量依然会非常差。**

### 2. 基础设施层

`提供数据库持久化`、`提供Redis和配置中心数据支撑`、`提供事件消息推送`、`提供外部服务接口封装`。总之这一层的核心目的就是更好的辅助 domain 领域层完成领域功能的开发。

而调用方式则为依赖倒置，也就是`领域服务层`定义接口，`基础设施层`做功能实现。这样可以有效的避免基础基础设施层中的对象被对外暴漏，如数据库持久化对象，在这样的分层结构中，天然的被保护在基础设置层中，外部是没法引入的，否则就循环依赖了。

有了这一层以后，domain 层不会关系数据的细节处理。传递给基础设施层的方法中，会把聚合对象或实体对象通过接口方法传递下来。之后在基础设施层中完成数据事务的操作。也会含有事务处理后，写入Redis缓存和发送MQ消息。如果说有夸领域的事务，一般可能就是跨库表，这个时候要使用 MQ 事件的方式进行驱动。

### 3. 类型对象层

这一层就比较简单了，只是一些通用的出入参对象 Response，还有枚举对象、异常对象等。供给于对外的接口层使用。但如果是 RPC 这样的接口，建议同 RPC 对外提供的接口描述包中提供，因为对外只提供1个轻量化的包且不依赖于任何其他包，是最好维护管理的。

## 五、只是换了别墅

从 MVC 到 DDD，我们有一点是必须清楚的认知的。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ddd-10.png?raw=true" width="650px">
</div>
从 MVC 到 DDD 我们只是换了一个更大、格局更清晰的房子🏡，但并不能决定你从 MVC 到 DDD 代码就变得非常干净、漂亮、整洁了。因为从 MVC 到 DDD 只是骨架变了，但骨架之下的血肉并没有改变。

如果你仍是把原有的烂代码平移到新的分层架构中，就相当于把老房子里的破旧家具衣物鞋帽搬过来而已。所以依照与软件设计的原则；分治、抽象和知识，中的知识是设计原则和设计模式的运用。所以要想把代码写好，就一定是要把`DDD + 设计模式`，才能真的把代码写好。接下来，小傅哥再给大家举个使用模式在 DDD 分层结构中重构的案例。

## 六、重构现有代码

软件设计第一原则，康威定律所提到的，分治、抽象和知识，是用于系统设计和实现的指导说明。分治和抽象，我们可以用 DDD 思想映射的分层架构来处理，但知识则是设计原则和设计模式的运用。

所以，如果没有合理的运用设计知识来对代码进行细化处理，那么即使拆分出流程边界在清晰的架构，也很难做出好维护的代码。而通常最常用的设计模式，无外乎；工厂、策略、模板的组合使用，少部分会用到责任链、建造者、组合模式。那么接下来，在分享一个带有流程的设计模式使用，让大家可以有一份可参考的工程代码设计。

### 1. 场景设定

这里我们做一个提额场景的设定。估计大家都用过信用卡💳，它有一个初始的额度，在后续的使用中会随着信用的积累和消费的增加，进行提高额度。而额度的提高则需要一系列的校验判断并最终做出提额处理。流程如下；

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ddd-06.png?raw=true" width="550px">
</div>

这样的流程图，是我们做业务开发的小伙伴，经常看到的。做一系列的流程判断处理，之后完成一个具体的功能。简单来说，就是 if···else 写代码，一条条的校验。但写着写着，时间一长就会发现代码变得特别混乱。最主要的原因就是，那些为了支撑完成业务的各类判断是不稳定因素，会随着业务的变化不断的调整。甚至有时候就直接下掉了。但你的代码就中多就了一条 `// 业务说暂时不使用，你也不敢删！`就像有首歌唱的🎤：**“需求依旧停在旷野上，你的代码被越拉越长。直到远去的马蹄声响，呼唤你的Bug传四方。”**

所以对于这样的功能流程设计，怎么办呢？总不能让旷野的马蹄，一直拉着你的bug在奔袭。

### 2. 代码现状

`一个接口一个实现，一个实现代码一片。`
`一片一片，又一片，代码行数，两三千。`

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ddd-07.png?raw=true" width="750px">
</div>


大部分我们在 MVC 工程分层结构下，参与开发的代码，基本都是定义一个接口，就写一片功能实现。功能实现中，如果看到有现成的接口，直接拿来复用。所有的实现并不会基于接口、抽象、模板等进行，所以最终这样的代码腐化的非常严重。

### 3. 重新分层

重构前，先说明下新的分层处理；如图

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ddd-09.png?raw=true" width="350px">
</div>

- 首先，在原有的 domain 贫血模型中，添加一个对应的领域包。credit 你可以是自己的其他的领域包。之后的 domain 则为充血模型设计。
- 之后，在领域包内实现自己的业务逻辑，注意这里需要用到设计模式来实现。代码实现中需要用到的数据查询、缓存使用、接口调用，全部采用依赖倒置的方式让基础层/接口层，来提供具体的实现逻辑。而 domain 层只是定义接口和使用 Spring 的注入进行使用。


### 4. 重构代码

抽象类，是一个非常好用的类。一种是可以定义出流程结构，让代码变得清晰干净。再有一种是定义共用方法，让其他实现类可复用。

那么这里，我们就使用抽象类定义模板 + 策略和工厂实现的规则引擎处理频繁变动的校验类流程，完成代码开发。如图我们先设计下代码的实现结构。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-ddd-08.png?raw=true" width="750px">
</div>


- 首先，定义一个受理调额的接口。因为额度的调整，包括；提额、降额。所以不要把名字写的太死。
- 之后，由抽象类实现接口。在抽象类中定义出整个调用链路关系，并把一些公用的数据类支撑逻辑，提到支撑类里。这和 Spring 的设计很像。
- 之后，因为规则校验这东西是为了支撑核心流程走下去的，而且还是随着业务频繁变动的。那就没必要在主线业务流程中，用 if···else 贴膏药的写代码，而是应该拆解出来。所以这里设计一个策略模式实现的规则校验，并通过工厂对外提供服务。
- 最后，这些东西零件类的东西都处理好后。就可以在抽象类的子类实现中进行调用处理了。

### 5. 代码呈现

经过设计模式的重构处理，现在的代码就以如下形式体现了。—— 拆解出来的伪代码，具体可以参考过往的一些设计模式运用。

```java
public AdjustAssetOrderEntity acceptAdjustAssetApply(AdjustAssetApplyEntity adjustAssetApplyEntity) {
    // 1. 参数校验
    this.parameterVerification(adjustAssetApplyEntity);
  
    // 2. 查询申请单数据，如已经存在则直接返回
    AdjustAssetOrderEntity orderEntity = queryAssetLog(adjustAssetApplyEntity.getPin(), adjustAssetApplyEntity.getAccountType(), adjustAssetApplyEntity.getTaskNo(), adjustAssetApplyEntity.getAdjustType());
    if (null != orderEntity) {
        log.info("pin={} taskNo={} 受理申请，检索到任务存在进行中的申请单。", adjustAssetApplyEntity.getUserId(), adjustAssetApplyEntity.getTaskNo());
        return orderEntity;
    }
  
    // 3. 以下流程放到分布式锁内处理【避免相同请求二次进入】
    String lockId = genLockId(adjustAssetApplyEntity.getAdjustType(), adjustAssetApplyEntity.getUserId());
    try {
        // 3.1 分布式锁：加锁
        long state = lock(lockId);
        if (0 == state) {
            throw new AccountRuntimeException(BizResultCodeEm.DISTRIBUTED_LOCK_EXCEPTION.getCode(), "分布式锁异常，当前用户行为处理中。");
        }
      
        // 3.2 账户查询
        UserAccountInfoDTO userAccountInfoDTO = queryJtAccount(adjustAssetApplyEntity.getUserId(), adjustAssetApplyEntity.getAccountType());
      
        // 3.3 基础校验；(1)账户类型、(2)状态状态、(3)额度类型、(4)账户逾期、(5)费率类型【暂无】
        LogicCheckResultEntity logicCheckResultEntity = doCheckLogic(adjustAssetApplyEntity, userAccountInfoDTO,
                DefaultLogicFactory.LogicModel.ACCOUNT_TYPE_FILTER.getCode(),
                DefaultLogicFactory.LogicModel.ACCOUNT_STATUS_FILTER.getCode(),
                DefaultLogicFactory.LogicModel.ACCOUNT_QUOTA_FILTER.getCode(),
                DefaultLogicFactory.LogicModel.ACCOUNT_OVERDUE_FILTER.getCode()
        );
      
        if (!AssetCycleQuotaAlterCodeEnum.E0000.getCode().equals(logicCheckResultEntity.getCode())) {
            log.info("userId={} taskNo={} 规则校验过滤拦截。code:{} info:{}", adjustAssetApplyEntity.getUserId(), adjustAssetApplyEntity.getTaskNo(), logicCheckResultEntity.getCode(), logicCheckResultEntity.getInfo());
            throw new AccountRuntimeException(logicCheckResultEntity.getCode(), logicCheckResultEntity.getInfo());
        }
      
        // 3.4 受理调额
        return this.acceptAsset(adjustAssetApplyEntity, userAccountInfoDTO);
    } finally {
        // 3.1 分布式锁：解锁
        this.unlock(lockId);
    }
}
```

这样的处理后，代码就变得非常清晰了。

1. 先是做基础的校验和数据的查询判断，之后加锁避免一个人超时申请。而后，进行规则引擎的调用和处理，根据不同的诉求，开发不同的规则，并配置的方式进行使用。
2. 最后所有的这些东西处理完成后，就是做最终的调额处理了。

## 七、编程经验

- 重构，是一直都在发生的事情，不能积累到最后才重构。那只有重做的可能。
- 工厂、模板、策略，这3个设计模式，就可以解决80%的场景问题。
- 小傅哥的编码标准也会成为伙伴参考的案例，所以小傅哥会更严格要求自己的标准。
