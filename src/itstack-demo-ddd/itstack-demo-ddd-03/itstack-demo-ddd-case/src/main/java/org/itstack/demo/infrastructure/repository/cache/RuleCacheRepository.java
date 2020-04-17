package org.itstack.demo.infrastructure.repository.cache;

import org.itstack.demo.domain.rule.model.aggregates.TreeRuleRich;
import org.itstack.demo.domain.rule.repository.IRuleRepository;
import org.itstack.demo.infrastructure.util.CacheUtil;
import org.springframework.stereotype.Repository;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Repository("ruleCacheRepository")
public class RuleCacheRepository implements IRuleRepository {

    @Override
    public TreeRuleRich queryTreeRuleRich(Long treeId) {
        return (TreeRuleRich) CacheUtil.cacheMap.get(treeId);
    }

}
