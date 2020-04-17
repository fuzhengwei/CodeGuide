package org.itstack.demo.jvm.instructions.extended.ifnull;

import org.itstack.demo.jvm.instructions.base.Instruction;
import org.itstack.demo.jvm.instructions.base.InstructionBranch;
import org.itstack.demo.jvm.rtda.Frame;

//branch if reference is null
public class IFNULL extends InstructionBranch {

    @Override
    public void execute(Frame frame) {
        Object ref = frame.operandStack().popRef();
        if (null == ref) {
            Instruction.branch(frame, this.offset);
        }
    }
}
