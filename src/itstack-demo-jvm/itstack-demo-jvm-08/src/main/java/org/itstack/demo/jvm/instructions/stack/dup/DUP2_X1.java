package org.itstack.demo.jvm.instructions.stack.dup;

import org.itstack.demo.jvm.instructions.base.InstructionNoOperands;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.OperandStack;
import org.itstack.demo.jvm.rtda.Slot;


// Duplicate the top one or two operand stack values and insert two or three values down
/*
bottom -> top
[...][c][b][a]
       _/ __/
      |  |
      V  V
[...][b][a][c][b][a]
*/
public class DUP2_X1 extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        Slot slot1 = stack.popSlot();
        Slot slot2 = stack.popSlot();
        Slot slot3 = stack.popSlot();
         stack.pushSlot(slot2);
         stack.pushSlot(slot1);
         stack.pushSlot(slot3);
         stack.pushSlot(slot2);
         stack.pushSlot(slot1);
    }

}
