package org.itstack.demo.test.server;

import org.itstack.demo.rpc.network.server.ServerSocket;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/6
 */
public class StartServer {

    public static void main(String[] args) {
        System.out.println("启动服务端开始");
        new Thread(new ServerSocket()).start();
        System.out.println("启动服务端完成");
    }

}
