package org.itstack.demo.jvm._native.java;

import org.itstack.demo.jvm._native.NativeMethod;
import org.itstack.demo.jvm._native.Registry;
import org.itstack.demo.jvm.rtda.Frame;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/30
 */
public class _Float {

    private final String jlFloat = "java/lang/Float";

    public _Float() {
        Registry.register(jlFloat, "floatToRawIntBits", "(F)I", new NativeMethod(this, "floatToRawIntBits"));
        Registry.register(jlFloat,"intBitsToFloat", "(I)F",new NativeMethod(this,"intBitsToFloat"));
        Registry.register(jlFloat,"registerNatives", "()V",new NativeMethod(this,"registerNatives"));
    }

    public void registerNatives(Frame frame) {
        // do nothing
    }

    public void floatToRawIntBits(Frame frame){
        float val = frame.localVars().getFloat(0);
        frame.operandStack().pushInt((int) val);
    }

    public void intBitsToFloat(Frame frame){
        int val = frame.localVars().getInt(0);
        frame.operandStack().pushFloat(val);
    }

}
