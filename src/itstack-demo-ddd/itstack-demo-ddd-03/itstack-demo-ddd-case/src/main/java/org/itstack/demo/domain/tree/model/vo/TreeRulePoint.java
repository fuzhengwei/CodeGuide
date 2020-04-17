package org.itstack.demo.domain.tree.model.vo;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class TreeRulePoint {

    private String ruleKey;      //规则Key
    private String ruleDesc;     //规则描述

    public TreeRulePoint() {
    }

    public TreeRulePoint(String ruleKey, String ruleDesc) {
        this.ruleKey = ruleKey;
        this.ruleDesc = ruleDesc;
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
}
