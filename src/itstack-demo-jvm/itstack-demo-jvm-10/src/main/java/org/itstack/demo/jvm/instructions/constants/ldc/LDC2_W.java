package org.itstack.demo.jvm.instructions.constants.ldc;

import org.itstack.demo.jvm.instructions.base.InstructionIndex16;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.OperandStack;
import org.itstack.demo.jvm.rtda.heap.constantpool.RunTimeConstantPool;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/28
 */
public class LDC2_W extends InstructionIndex16 {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        RunTimeConstantPool runTimeConstantPool = frame.method().clazz().constantPool();
        Object c = runTimeConstantPool.getConstants(this.idx);
        
        if (c instanceof Long) {
            stack.pushLong((Long) c);
            return;
        }
        if (c instanceof Double) {
            stack.pushDouble((Double) c);
            return;
        }
        
        throw new ClassFormatError(c.toString());

    }

}
