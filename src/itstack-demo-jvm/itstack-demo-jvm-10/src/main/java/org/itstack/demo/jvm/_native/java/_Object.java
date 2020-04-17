package org.itstack.demo.jvm._native.java;

import org.itstack.demo.jvm._native.NativeMethod;
import org.itstack.demo.jvm._native.Registry;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Object;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/30
 */
public class _Object {

    private final String jlObject = "java/lang/Object";

    public _Object() {
        Registry.register(jlObject, "getClass", "()Ljava/lang/Class;", new NativeMethod(this, "getClass"));
        Registry.register(jlObject,"hashCode", "()I",new NativeMethod(this,"_hashCode"));
        Registry.register(jlObject,"clone", "()Ljava/lang/Object;",new NativeMethod(this,"_clone"));
        Registry.register(jlObject,"registerNatives", "()V",new NativeMethod(this,"registerNatives"));
    }

    public void registerNatives(Frame frame) {
        // do nothing
    }
    
    public void getClazz(Frame frame) {
        Object thiz = frame.localVars().getThis();
        Object clazz = thiz.clazz().jClass();
        frame.operandStack().pushRef(clazz);
    }

    public void _hashCode(Frame frame) {
        Object thiz = frame.localVars().getThis();
        frame.operandStack().pushInt(thiz.hashCode());
    }

    public void _clone(Frame frame) throws CloneNotSupportedException {
        Object thiz = frame.localVars().getThis();

        Class cloneable = thiz.clazz().loader().loadClass("java/lang/Cloneable");

        if (!thiz.clazz().isImplements(cloneable)) {
            throw new CloneNotSupportedException();
        }

        frame.operandStack().pushRef(thiz);
    }


}
