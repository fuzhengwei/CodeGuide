package org.itstack.demo.jvm.instructions.constants.ldc;

import org.itstack.demo.jvm.instructions.base.InstructionIndex8;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.OperandStack;
import org.itstack.demo.jvm.rtda.heap.constantpool.RunTimeConstantPool;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Object;
import org.itstack.demo.jvm.rtda.heap.methodarea.StringPool;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/28
 */
public class LDC extends InstructionIndex8 {

    @Override
    public void execute(Frame frame) {
        _ldc(frame, this.idx);
    }

    private void _ldc(Frame frame, int idx) {
        OperandStack stack = frame.operandStack();
        Class clazz = frame.method().clazz();
        RunTimeConstantPool runTimeConstantPool = frame.method().clazz().constantPool();
        java.lang.Object c = runTimeConstantPool.getConstants(idx);

        if (c instanceof Integer) {
            stack.pushInt((Integer) c);
            return;
        }

        if (c instanceof Float) {
            stack.pushFloat((Float) c);
            return;
        }

        if (c instanceof String) {
            Object internedStr = StringPool.jString(clazz.loader(), (String) c);
            stack.pushRef(internedStr);
        }

        throw new RuntimeException("todo ldc");
    }

}
