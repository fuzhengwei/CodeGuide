package org.itstack.demo.domain.rule.service.engine;

import org.itstack.demo.domain.rule.model.vo.DecisionMatter;
import org.itstack.demo.domain.rule.model.vo.EngineResult;

/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public interface EngineFilter {

    EngineResult process(final DecisionMatter matter) throws Exception;

}
