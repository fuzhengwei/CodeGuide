package org.itstack.demo.jvm._native.java;

import org.itstack.demo.jvm._native.NativeMethod;
import org.itstack.demo.jvm._native.Registry;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.LocalVars;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Object;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/30
 */
public class _System {

    private final String jlSystem = "java/lang/System";

    public _System() {
        Registry.register(jlSystem, "arraycopy", "()Ljava/lang/String;", new NativeMethod(this, "arraycopy"));
        Registry.register(jlSystem,"registerNatives", "()V",new NativeMethod(this,"registerNatives"));
    }

    public void registerNatives(Frame frame) {
        // do nothing
    }
    
    public void arraycopy(Frame frame) {
        LocalVars vars = frame.localVars();
        Object src = vars.getRef(0);
        int srcPos = vars.getInt(1);
        Object dest = vars.getRef(2);
        int destPos = vars.getInt(4);
        int length = vars.getInt(4);

        if (null == src || dest == null) {
            throw new NullPointerException();
        }

        if (!checkArrayCopy(src, dest)) {
            throw new ArrayStoreException();
        }

        if (srcPos < 0 || destPos < 0 || length < 0 ||
                srcPos + length > src.arrayLength() ||
                destPos + length > dest.arrayLength()) {
            throw new IndexOutOfBoundsException();
        }

        System.arraycopy(src, srcPos, dest, destPos, length);

        //todo

    }

    public boolean checkArrayCopy(Object src, Object dest) {
        Class srcClass = src.clazz();
        Class destClass = dest.clazz();

        if (!srcClass.isArray() || !destClass.isArray()) {
            return false;
        }

        if (srcClass.componentClass().IsPrimitive() || destClass.componentClass().IsPrimitive()) {
            return srcClass == destClass;
        }

        return true;

    }

}
