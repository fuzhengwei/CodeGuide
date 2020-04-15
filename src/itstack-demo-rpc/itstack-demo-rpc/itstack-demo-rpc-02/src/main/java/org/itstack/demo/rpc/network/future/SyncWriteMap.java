package org.itstack.demo.rpc.network.future;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SyncWriteMap {

    public static Map<String, WriteFuture> syncKey = new ConcurrentHashMap<String, WriteFuture>();

}
