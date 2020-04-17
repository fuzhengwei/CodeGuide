package org.itstack.demo.domain.rule.repository;

import org.itstack.demo.domain.rule.model.aggregates.TreeRuleRich;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public interface IRuleRepository {

    TreeRuleRich queryTreeRuleRich(Long treeId);

}
