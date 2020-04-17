package org.itstack.demo.jvm.instructions.conversions.d2x;

import org.itstack.demo.jvm.instructions.base.InstructionNoOperands;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.OperandStack;

//convert double to float
public class D2F extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        double d = stack.popDouble();
        float f = (float) d;
        stack.pushFloat(f);
    }

}
