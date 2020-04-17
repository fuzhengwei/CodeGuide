package org.itstack.demo.jvm.instructions.stores.astore;

import org.itstack.demo.jvm.instructions.base.InstructionNoOperands;
import org.itstack.demo.jvm.rtda.Frame;

public class ASTORE_0 extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        _astore(frame, 0);
    }

}
