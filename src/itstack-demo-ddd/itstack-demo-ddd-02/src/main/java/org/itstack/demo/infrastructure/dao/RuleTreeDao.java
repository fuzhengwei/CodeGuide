package org.itstack.demo.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import org.itstack.demo.infrastructure.po.RuleTree;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Mapper
public interface RuleTreeDao {

    RuleTree queryRuleTreeByTreeId(Long id);

    RuleTree queryTreeSummaryInfo(Long treeId);

}
