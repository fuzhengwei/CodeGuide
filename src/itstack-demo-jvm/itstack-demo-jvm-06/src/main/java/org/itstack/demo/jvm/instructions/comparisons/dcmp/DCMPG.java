package org.itstack.demo.jvm.instructions.comparisons.dcmp;

import org.itstack.demo.jvm.instructions.base.InstructionNoOperands;
import org.itstack.demo.jvm.rtda.Frame;

public class DCMPG extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        _dcmp(frame, true);
    }
    
}
