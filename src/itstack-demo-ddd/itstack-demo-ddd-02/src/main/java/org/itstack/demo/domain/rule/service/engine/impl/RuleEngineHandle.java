package org.itstack.demo.domain.rule.service.engine.impl;

import org.itstack.demo.domain.rule.model.aggregates.TreeRuleRich;
import org.itstack.demo.domain.rule.model.vo.DecisionMatter;
import org.itstack.demo.domain.rule.model.vo.EngineResult;
import org.itstack.demo.domain.rule.model.vo.TreeNodeInfo;
import org.itstack.demo.domain.rule.repository.IRuleRepository;
import org.itstack.demo.domain.rule.service.engine.EngineBase;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Service("ruleEngineHandle")
public class RuleEngineHandle extends EngineBase {

    @Resource
    private IRuleRepository ruleRepository;

    @Override
    public EngineResult process(DecisionMatter matter) throws Exception {
        //决策规则树
        TreeRuleRich treeRuleRich = ruleRepository.queryTreeRuleRich(matter.getTreeId());
        if (null == treeRuleRich) throw new RuntimeException("Tree Rule is null!");
        //决策节点
        TreeNodeInfo treeNodeInfo = engineDecisionMaker(treeRuleRich, matter);
        //决策结果
        return new EngineResult(matter.getUserId(), treeNodeInfo.getTreeId(), treeNodeInfo.getTreeNodeId(), treeNodeInfo.getNodeValue());
    }

}
