package org.itstack.demo.domain.rule.service.logic;

import org.itstack.demo.domain.rule.model.vo.DecisionMatter;
import org.itstack.demo.domain.rule.model.vo.TreeNodeLineInfo;
import org.itstack.demo.infrastructure.common.Constants;

import java.util.List;

/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public abstract class BaseLogic extends LogicConfig implements LogicFilter {

    @Override
    public Long filter(String matterValue, List<TreeNodeLineInfo> treeNodeLineInfoList) {
        for (TreeNodeLineInfo nodeLine : treeNodeLineInfoList) {
            if (decisionLogic(matterValue, nodeLine)) return  nodeLine.getNodeIdTo();
        }
        return Constants.Global.TreeNullNode;
    }

    @Override
    public abstract String matterValue(DecisionMatter decisionMatter);

    private boolean decisionLogic(String matterValue, TreeNodeLineInfo nodeLine) {
        switch (nodeLine.getRuleLimitType()) {
            case Constants.RuleLimitType.EQUAL:
                return matterValue.equals(nodeLine.getRuleLimitValue());
            case Constants.RuleLimitType.LT:
                return Double.parseDouble(matterValue) < Double.parseDouble(nodeLine.getRuleLimitValue());
            case Constants.RuleLimitType.GE:
                return Double.parseDouble(matterValue) >= Double.parseDouble(nodeLine.getRuleLimitValue());
            default:
                return false;
        }
    }

}
