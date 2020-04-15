package org.itstack.demo.netty.domain;

import java.util.Date;

/**
 * 用户管道信息；记录某个用户分配到某个服务端
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class UserChannelInfo {

    private String ip;        //服务端：IP
    private int port;         //服务端：port
    private String channelId; //channelId
    private Date linkDate;    //链接时间

    public UserChannelInfo(String ip, int port, String channelId, Date linkDate) {
        this.ip = ip;
        this.port = port;
        this.channelId = channelId;
        this.linkDate = linkDate;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Date getLinkDate() {
        return linkDate;
    }

    public void setLinkDate(Date linkDate) {
        this.linkDate = linkDate;
    }
}
