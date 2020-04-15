package org.itstack.demo.rpc.network.msg;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/6
 */
public class Request {

    private String requestId;
    private Object result;

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
