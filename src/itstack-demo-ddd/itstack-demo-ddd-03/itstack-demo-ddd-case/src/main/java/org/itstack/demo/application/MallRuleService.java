package org.itstack.demo.application;

import org.itstack.demo.domain.rule.model.vo.DecisionMatter;
import org.itstack.demo.domain.rule.model.vo.EngineResult;

/**
 * 商超规则过滤服务；提供规则树决策功能
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public interface MallRuleService {

    /**
     * 决策服务
     * @param matter 决策物料
     * @return       决策结果
     */
    EngineResult process(final DecisionMatter matter);

}
