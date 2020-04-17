package org.itstack.demo.jvm._native;

import org.itstack.demo.jvm._native.java.*;

import java.lang.StackTraceElement;
import java.util.HashMap;
import java.util.Map;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/30
 */
public class Registry {

    private static Map<String, NativeMethod> registry = new HashMap<>();

    //初始化本地方法
    public static void initNative() {
        new _Class();
        new _Double();
        new _Float();
        new _Object();
        new _String();
        new _System();
        new _Throwable();
    }

    public static void register(String className, String methodName, String methodDescriptor, NativeMethod method) {
        String key = className + "~" + methodName + "~" + methodDescriptor;
        registry.put(key, method);
    }

    public static NativeMethod findNativeMethod(String className, String methodName, String methodDescriptor) {
        String key = className + "~" + methodName + "~" + methodDescriptor;
        return registry.get(key);
    }

}
