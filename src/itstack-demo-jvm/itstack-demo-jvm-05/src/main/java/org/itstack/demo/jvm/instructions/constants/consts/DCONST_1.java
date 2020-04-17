package org.itstack.demo.jvm.instructions.constants.consts;

import org.itstack.demo.jvm.instructions.base.BytecodeReader;
import org.itstack.demo.jvm.instructions.base.InstructionNoOperands;
import org.itstack.demo.jvm.rtda.Frame;

public class DCONST_1 extends InstructionNoOperands {
    @Override
    public void execute(Frame frame) {
        frame.operandStack().pushDouble(1.0);
    }
}
