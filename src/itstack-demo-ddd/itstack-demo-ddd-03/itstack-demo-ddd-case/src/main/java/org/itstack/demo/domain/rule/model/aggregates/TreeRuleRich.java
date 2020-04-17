package org.itstack.demo.domain.rule.model.aggregates;

import org.itstack.demo.domain.rule.model.vo.TreeNodeInfo;
import org.itstack.demo.domain.rule.model.vo.TreeRoot;

import java.util.Map;

/**
 * 规则树聚合
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class TreeRuleRich {

    private TreeRoot treeRoot;                          //树根信息
    private Map<Long, TreeNodeInfo> treeNodeMap;        //树节点ID -> 子节点

    public TreeRoot getTreeRoot() {
        return treeRoot;
    }

    public void setTreeRoot(TreeRoot treeRoot) {
        this.treeRoot = treeRoot;
    }

    public Map<Long, TreeNodeInfo> getTreeNodeMap() {
        return treeNodeMap;
    }

    public void setTreeNodeMap(Map<Long, TreeNodeInfo> treeNodeMap) {
        this.treeNodeMap = treeNodeMap;
    }
}
