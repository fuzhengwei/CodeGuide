package org.itstack.demo.jvm.instructions.references;

import org.itstack.demo.jvm.instructions.base.InstructionNoOperands;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.OperandStack;
import org.itstack.demo.jvm.rtda.heap.methodarea.Object;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/29
 * get length of array
 */
public class ARRAY_LENGTH extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {

        OperandStack stack = frame.operandStack();
        Object arrRef = stack.popRef();
        if (null == arrRef){
            throw new NullPointerException();
        }

        int arrLen = arrRef.arrayLength();
        stack.pushInt(arrLen);
    }

}
