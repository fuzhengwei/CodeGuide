package org.itstack.demo.jvm.instructions.base;

import org.itstack.demo.jvm.rtda.Frame;

public interface Instruction {

    void fetchOperands(BytecodeReader reader);

    void execute(Frame frame);

    static void branch(Frame frame, int offset) {
        int pc = frame.thread().pc();
        int nextPC = pc + offset;
        frame.setNextPC(nextPC);
    }

}
