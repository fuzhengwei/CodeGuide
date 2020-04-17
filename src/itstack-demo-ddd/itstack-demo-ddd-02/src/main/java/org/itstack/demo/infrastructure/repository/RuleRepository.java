package org.itstack.demo.infrastructure.repository;

import org.itstack.demo.domain.rule.model.aggregates.TreeRuleRich;
import org.itstack.demo.domain.rule.repository.IRuleRepository;
import org.itstack.demo.infrastructure.repository.cache.RuleCacheRepository;
import org.itstack.demo.infrastructure.repository.mysql.RuleMysqlRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Repository("ruleRepository")
public class RuleRepository implements IRuleRepository {

    @Resource(name = "ruleMysqlRepository")
    private RuleMysqlRepository ruleMysqlRepository;
    @Resource(name = "ruleCacheRepository")
    private RuleCacheRepository ruleCacheRepository;

    @Override
    public TreeRuleRich queryTreeRuleRich(Long treeId) {
        TreeRuleRich treeRuleRich = ruleCacheRepository.queryTreeRuleRich(treeId);
        if (null != treeRuleRich) return treeRuleRich;
        return ruleMysqlRepository.queryTreeRuleRich(treeId);
    }

}
