package org.itstack.demo.jvm._native.java;

import org.itstack.demo.jvm._native.NativeMethod;
import org.itstack.demo.jvm._native.Registry;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.LocalVars;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Object;

import java.util.Arrays;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/30
 */
public class _System {

    private final String jlSystem = "java/lang/System";

    public _System() {
        Registry.register(jlSystem, "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", new NativeMethod(this, "arraycopy"));
        Registry.register(jlSystem, "registerNatives", "()V", new NativeMethod(this, "registerNatives"));
    }

    public void registerNatives(Frame frame) {
        // do nothing
    }

    public void arraycopy(Frame frame) {
        LocalVars vars = frame.localVars();
        Object src = vars.getRef(0);
        int srcPos = vars.getInt(1);
        Object dest = vars.getRef(2);
        int destPos = vars.getInt(3);
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

        java.lang.Object data = src.data;

        if (data instanceof byte[]) {
            dest.data = Arrays.copyOfRange((byte[]) src.data, srcPos, srcPos + length);
            return;
        }

        if (data instanceof char[]) {
            dest.data = Arrays.copyOfRange((char[]) src.data, srcPos, srcPos + length);
            return;
        }

        if (data instanceof short[]) {
            dest.data = Arrays.copyOfRange((short[]) src.data, srcPos, srcPos + length);
            return;
        }

        if (data instanceof int[]) {
            dest.data = Arrays.copyOfRange((int[]) src.data, srcPos, srcPos + length);
            return;
        }

        if (data instanceof double[]) {
            dest.data = Arrays.copyOfRange((double[]) src.data, srcPos, srcPos + length);
            return;
        }

        if (data instanceof float[]) {
            dest.data = Arrays.copyOfRange((float[]) src.data, srcPos, srcPos + length);
            return;
        }

        if (data instanceof Object[]) {
            dest.data = Arrays.copyOfRange((Object[]) src.data, srcPos, srcPos + length);
            return;
        }

        throw new RuntimeException("Not array!");

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
