package org.itstack.demo.domain.rule.service.logic.impl;

import org.itstack.demo.domain.rule.model.vo.DecisionMatter;
import org.itstack.demo.domain.rule.service.logic.BaseLogic;
import org.springframework.stereotype.Service;

/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Service("userAgeFilter")
public class UserAgeFilter extends BaseLogic {

    @Override
    public String matterValue(DecisionMatter decisionMatter) {
        return decisionMatter.getValMap().get("age").toString();
    }

}
