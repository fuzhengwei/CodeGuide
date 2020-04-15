package org.itstack.demo.rpc.domain;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/7
 */
public class RpcProviderConfig {

    private String nozzle; //接口
    private String ref;    //映射
    private String alias;  //别名
    private String host;   //ip
    private int port;      //端口

    public String getNozzle() {
        return nozzle;
    }

    public void setNozzle(String nozzle) {
        this.nozzle = nozzle;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
