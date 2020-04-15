package org.itstack.demo.ark.domain.msgobj;

/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019/9/27
 */
public class Feedback {

    private String channelId;   //设备ID{管道ID}
    private Integer stateType; //状态类型;1\2
    private String stateMsg;   //状态信息

    public Feedback(String channelId, Integer stateType, String stateMsg) {
        this.channelId = channelId;
        this.stateType = stateType;
        this.stateMsg = stateMsg;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Integer getStateType() {
        return stateType;
    }

    public void setStateType(Integer stateType) {
        this.stateType = stateType;
    }

    public String getStateMsg() {
        return stateMsg;
    }

    public void setStateMsg(String stateMsg) {
        this.stateMsg = stateMsg;
    }
}
