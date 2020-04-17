package org.itstack.demo.domain.rule.model.vo;

import java.util.List;

/**
 * 规则树节点信息
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class TreeNodeInfo {

    private Long treeId;            //规则树ID
    private Long treeNodeId;        //规则树节点ID
    private Integer nodeType;       //节点类型；1子叶、2果实
    private String nodeValue;       //节点值[nodeType=2]；果实值
    private String ruleKey;         //规则Key
    private String ruleDesc;        //规则描述
    private List<TreeNodeLineInfo> treeNodeLineInfoList; //节点链路

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    public Long getTreeNodeId() {
        return treeNodeId;
    }

    public void setTreeNodeId(Long treeNodeId) {
        this.treeNodeId = treeNodeId;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }

    public String getRuleKey() {
        return ruleKey;
    }

    public void setRuleKey(String ruleKey) {
        this.ruleKey = ruleKey;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public List<TreeNodeLineInfo> getTreeNodeLineInfoList() {
        return treeNodeLineInfoList;
    }

    public void setTreeNodeLineInfoList(List<TreeNodeLineInfo> treeNodeLineInfoList) {
        this.treeNodeLineInfoList = treeNodeLineInfoList;
    }
}
