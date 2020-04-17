package org.itstack.demo.jvm.instructions.comparisons.ifcond;

import org.itstack.demo.jvm.instructions.base.Instruction;
import org.itstack.demo.jvm.instructions.base.InstructionBranch;
import org.itstack.demo.jvm.rtda.Frame;

public class IFLT extends InstructionBranch {

    @Override
    public void execute(Frame frame) {
        int val = frame.operandStack().popInt();
        if (val < 0) {
            Instruction.branch(frame, this.offset);
        }
    }
}
