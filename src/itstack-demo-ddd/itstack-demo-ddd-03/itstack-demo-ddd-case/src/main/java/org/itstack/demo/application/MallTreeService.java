package org.itstack.demo.application;

import org.itstack.demo.domain.tree.model.aggregates.TreeCollect;

/**
 * 商超规则树服务；提供规则树查询功能
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public interface MallTreeService {

    /**
     * 查询规则树概要信息
     * @param treeId 规则树ID
     * @return       规则树简要汇总信息
     */
    TreeCollect queryTreeSummaryInfo(Long treeId);

}
