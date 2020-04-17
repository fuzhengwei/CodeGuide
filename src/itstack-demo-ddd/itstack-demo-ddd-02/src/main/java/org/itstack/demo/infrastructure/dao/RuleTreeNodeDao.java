package org.itstack.demo.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import org.itstack.demo.infrastructure.po.RuleTreeNode;

import java.util.List;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Mapper
public interface RuleTreeNodeDao {

    List<RuleTreeNode> queryRuleTreeNodeList(Long treeId);

    int queryTreeNodeCount(Long treeId);

    List<RuleTreeNode> queryTreeRulePoint(Long treeId);

}
