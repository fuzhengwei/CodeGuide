package org.itstack.demo.jvm.instructions.stores.lstore;

import org.itstack.demo.jvm.instructions.base.InstructionNoOperands;
import org.itstack.demo.jvm.rtda.Frame;

public class LSTORE_2 extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        _lstore(frame, 2);
    }

}
