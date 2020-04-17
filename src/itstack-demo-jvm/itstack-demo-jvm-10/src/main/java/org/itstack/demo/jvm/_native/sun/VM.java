package org.itstack.demo.jvm._native.sun;

import org.itstack.demo.jvm.instructions.base.MethodInvokeLogic;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Method;
import org.itstack.demo.jvm.rtda.heap.methodarea.Object;
import org.itstack.demo.jvm.rtda.heap.methodarea.StringPool;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/30
 */
public class VM {

    public void initialize(Frame frame) {
        Class vmClass = frame.method().clazz();
        Object savedProps = vmClass.getRefVar("savedProps", "Ljava/util/Properties;");
        Object key = StringPool.jString(vmClass.loader(), "foo");
        Object val = StringPool.jString(vmClass.loader(), "bar");

        frame.operandStack().pushRef(savedProps);
        frame.operandStack().pushRef(key);
        frame.operandStack().pushRef(val);

        Class propsClass = vmClass.loader().loadClass("java/util/Properties");
        Method setPropMethod = propsClass.getInstanceMethod("setProperty",
                "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;");
        MethodInvokeLogic.invokeMethod(frame, setPropMethod);
    }

}
