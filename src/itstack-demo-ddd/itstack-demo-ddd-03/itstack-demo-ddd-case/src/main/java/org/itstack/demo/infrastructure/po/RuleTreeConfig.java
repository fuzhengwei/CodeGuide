package org.itstack.demo.infrastructure.po;

import java.util.Date;

/**
 * 规则树配置信息
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class RuleTreeConfig {

    private Long id;            //主键ID
    private String ruleKey;     //规则Key
    private String ruleDesc;    //规则描述
    private Integer limitType;  //限定类型；1:=;2:>;3:<;4:>=;5<=;6:enum[枚举范围]
    private String limitMode;   //限制模式 1单选框、2多选框、3日期单录框、4日期范围框、5输入框
    private String permitValue; //限定值
    private Date createTime;    //创建时间
    private Date updateTime;    //更新时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getLimitType() {
        return limitType;
    }

    public void setLimitType(Integer limitType) {
        this.limitType = limitType;
    }

    public String getLimitMode() {
        return limitMode;
    }

    public void setLimitMode(String limitMode) {
        this.limitMode = limitMode;
    }

    public String getPermitValue() {
        return permitValue;
    }

    public void setPermitValue(String permitValue) {
        this.permitValue = permitValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
