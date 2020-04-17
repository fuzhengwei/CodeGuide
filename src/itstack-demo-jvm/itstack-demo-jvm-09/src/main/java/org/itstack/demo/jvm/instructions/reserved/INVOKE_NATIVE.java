package org.itstack.demo.jvm.instructions.reserved;

import org.itstack.demo.jvm._native.NativeMethod;
import org.itstack.demo.jvm._native.Registry;
import org.itstack.demo.jvm.instructions.base.InstructionNoOperands;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.heap.methodarea.Method;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/2
 */
public class INVOKE_NATIVE extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        Method method = frame.method();
        String className = method.clazz().name();
        String methodName = method.name();
        String methodDescriptor = method.descriptor();

        NativeMethod nativeMethod = Registry.findNativeMethod(className, methodName, methodDescriptor);
        if (null == nativeMethod) {
            String methodInfo = className + "." + methodName + methodDescriptor;
            throw new UnsatisfiedLinkError(methodInfo);
        }

        nativeMethod.invoke(frame);

    }

}
