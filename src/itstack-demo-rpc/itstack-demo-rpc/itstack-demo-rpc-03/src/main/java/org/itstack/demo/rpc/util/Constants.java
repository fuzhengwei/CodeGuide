package org.itstack.demo.rpc.util;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/7
 */
public class Constants {

    /**
     * 隐藏的key前缀，隐藏的key只能在filter里拿到，在RpcContext里拿不到，不过可以设置
     */
    public static final char HIDE_KEY_PREFIX = '.';

    /**
     * 内部使用的key前缀，防止和自定义key冲突
     */
    public static final char INTERNAL_KEY_PREFIX = '_';

}
