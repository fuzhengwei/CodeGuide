package org.itstack.demo.jvm.instructions.base;

import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.Slot;
import org.itstack.demo.jvm.rtda.Thread;
import org.itstack.demo.jvm.rtda.heap.methodarea.Method;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/28
 */
public class MethodInvokeLogic {

    public static void invokeMethod(Frame invokerFrame, Method method) {
        Thread thread = invokerFrame.thread();
        Frame newFrame = thread.newFrame(method);
        thread.pushFrame(newFrame);

        int argSlotCount = method.argSlotCount();
        if (argSlotCount > 0) {
            for (int i = argSlotCount - 1; i >= 0; i--) {
                Slot slot = invokerFrame.operandStack().popSlot();
                newFrame.localVars().setSlot(i, slot);
            }
        }
        
    }

}
