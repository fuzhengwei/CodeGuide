package org.itstack.demo.rpc.network.msg;

import io.netty.channel.Channel;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/6
 */
public class Response {

    private transient Channel channel;
    private String requestId;
    private Object result;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
