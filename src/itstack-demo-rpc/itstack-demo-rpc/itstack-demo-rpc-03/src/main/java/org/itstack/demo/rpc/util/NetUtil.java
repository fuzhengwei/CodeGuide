package org.itstack.demo.rpc.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/7
 */
public class NetUtil {

    public static boolean isPortUsing(int port) throws UnknownHostException {
        boolean flag = false;
        try {
            Socket socket = new Socket("localhost", port);
            socket.close();
            flag = true;
        } catch (IOException e) {

        }
        return flag;
    }

    public static String getHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public static void main(String[] args) throws UnknownHostException {
        NetUtil.isPortUsing(8080);
    }

}
