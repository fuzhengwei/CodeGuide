package org.itstack.demo.rpc.network.msg;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/6
 */
public class Response {

    private String requestId;
    private String param;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

}
