package org.itstack.demo.jvm._native.java;

import org.itstack.demo.jvm._native.NativeMethod;
import org.itstack.demo.jvm._native.Registry;
import org.itstack.demo.jvm.rtda.Frame;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/30
 */
public class _Double {

    private final String jlDouble = "java/lang/Double";

    public _Double() {
        Registry.register(jlDouble, "doubleToRawLongBits", "(D)J", new NativeMethod(this, "doubleToRawLongBits"));
        Registry.register(jlDouble,"longBitsToDouble", "(J)D",new NativeMethod(this,"longBitsToDouble"));
        Registry.register(jlDouble,"registerNatives", "()V",new NativeMethod(this,"registerNatives"));
    }

    public void registerNatives(Frame frame) {
        // do nothing
    }

    public void doubleToRawLongBits(Frame frame) {
        double val = frame.localVars().getDouble(0);
        frame.operandStack().pushLong((long) val);
    }

    public void longBitsToDouble(Frame frame) {
        long val = frame.localVars().getLong(0);
        frame.operandStack().pushDouble(val);
    }

}
