package org.itstack.demo.jvm._native;

import org.itstack.demo.jvm.rtda.Frame;

import java.lang.reflect.Method;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/30
 */
public class NativeMethod {

    private String methodName;
    private Object obj;

    public NativeMethod(Object obj, String methodName) {
        this.methodName = methodName;
        this.obj = obj;
    }

    public void invoke(Frame frame) {
        try {
            Method method = obj.getClass().getMethod(methodName, frame.getClass());
            method.invoke(obj, frame);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
