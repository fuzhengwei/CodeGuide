package org.itstack.demo.jvm.instructions.stores.fstore;

import org.itstack.demo.jvm.instructions.base.InstructionNoOperands;
import org.itstack.demo.jvm.rtda.Frame;

public class FSTORE_3 extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        _fstore(frame, 3);
    }

}
