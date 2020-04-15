package org.itstack.demo.rpc.network.future;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fuzhengwei1 on 2016/10/20.
 */
public class SyncWriteMap {

    public static Map<String, WriteFuture> syncKey = new ConcurrentHashMap<String, WriteFuture>();

}
