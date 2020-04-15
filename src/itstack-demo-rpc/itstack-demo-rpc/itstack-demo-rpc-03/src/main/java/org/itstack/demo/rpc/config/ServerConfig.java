package org.itstack.demo.rpc.config;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/6
 */
public class ServerConfig {

    protected String host;  //注册中心地址
    protected int port;     //注册中心端口

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
