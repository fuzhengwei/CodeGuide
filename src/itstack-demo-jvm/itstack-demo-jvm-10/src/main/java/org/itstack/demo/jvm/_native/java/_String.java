package org.itstack.demo.jvm._native.java;

import org.itstack.demo.jvm._native.NativeMethod;
import org.itstack.demo.jvm._native.Registry;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.heap.methodarea.Object;
import org.itstack.demo.jvm.rtda.heap.methodarea.StringPool;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/30
 */
public class _String {

    private final String jlString = "java/lang/String";

    public _String() {
        Registry.register(jlString, "intern", "()Ljava/lang/String;", new NativeMethod(this, "intern"));
        Registry.register(jlString,"registerNatives", "()V",new NativeMethod(this,"registerNatives"));
    }

    public void registerNatives(Frame frame) {
        // do nothing
    }

    public void intern(Frame frame){
        Object thiz = frame.localVars().getThis();
        Object interned = StringPool.internString(thiz);
        frame.operandStack().pushRef(interned);
    }

}
