package org.itstack.demo.domain.rule.service;

import org.itstack.demo.application.MallRuleService;
import org.itstack.demo.domain.rule.model.vo.DecisionMatter;
import org.itstack.demo.domain.rule.model.vo.EngineResult;
import org.itstack.demo.domain.rule.service.engine.EngineFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 规则树服务；提供规则规律功能
 *
 * 1、rule包下只进行规则决策领域的处理
 * 2、封装决策行为到领域模型中，外部只需要调用和处理结果即可
 * 3、可以扩展不同的决策引擎进行统一管理
 *
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Service("mallRuleService")
public class MallRuleServiceImpl implements MallRuleService {

    private Logger logger = LoggerFactory.getLogger(MallRuleServiceImpl.class);

    @Resource(name = "ruleEngineHandle")
    private EngineFilter ruleEngineHandle;

    @Override
    public EngineResult process(DecisionMatter matter) {
        try {
            return ruleEngineHandle.process(matter);
        } catch (Exception e) {
            logger.error("决策引擎执行失败", e);
            return new EngineResult(false);
        }
    }

}
