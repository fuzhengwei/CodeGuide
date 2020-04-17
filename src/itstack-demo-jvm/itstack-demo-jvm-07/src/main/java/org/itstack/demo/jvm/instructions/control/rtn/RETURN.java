package org.itstack.demo.jvm.instructions.control.rtn;

import org.itstack.demo.jvm.instructions.base.InstructionNoOperands;
import org.itstack.demo.jvm.rtda.Frame;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/27
 */
public class RETURN extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        frame.thread().popFrame();
    }
    
}
