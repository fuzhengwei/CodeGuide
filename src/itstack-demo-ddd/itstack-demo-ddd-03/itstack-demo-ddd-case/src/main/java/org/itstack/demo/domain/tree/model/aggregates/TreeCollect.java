package org.itstack.demo.domain.tree.model.aggregates;

import org.itstack.demo.domain.tree.model.vo.TreeInfo;
import org.itstack.demo.domain.tree.model.vo.TreeRulePoint;

import java.util.List;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class TreeCollect {

    private TreeInfo treeInfo;
    private List<TreeRulePoint> treeRulePointList;

    public TreeInfo getTreeInfo() {
        return treeInfo;
    }

    public void setTreeInfo(TreeInfo treeInfo) {
        this.treeInfo = treeInfo;
    }

    public List<TreeRulePoint> getTreeRulePointList() {
        return treeRulePointList;
    }

    public void setTreeRulePointList(List<TreeRulePoint> treeRulePointList) {
        this.treeRulePointList = treeRulePointList;
    }
    
}
