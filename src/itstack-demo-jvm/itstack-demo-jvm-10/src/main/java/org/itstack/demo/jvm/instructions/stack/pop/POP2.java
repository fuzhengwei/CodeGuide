package org.itstack.demo.jvm.instructions.stack.pop;

import org.itstack.demo.jvm.instructions.base.InstructionNoOperands;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.OperandStack;

// Pop the top one or two operand stack values
/*
bottom -> top
[...][c][b][a]
         |  |
         V  V
[...][c]
*/
public class POP2 extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        stack.popSlot();
        stack.popSlot();
    }

}
