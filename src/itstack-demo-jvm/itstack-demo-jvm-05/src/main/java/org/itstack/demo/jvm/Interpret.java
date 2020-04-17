package org.itstack.demo.jvm;

import com.alibaba.fastjson.JSON;
import org.itstack.demo.jvm.classfile.MemberInfo;
import org.itstack.demo.jvm.classfile.attributes.impl.CodeAttribute;
import org.itstack.demo.jvm.instructions.Factory;
import org.itstack.demo.jvm.instructions.base.BytecodeReader;
import org.itstack.demo.jvm.instructions.base.Instruction;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.Slot;
import org.itstack.demo.jvm.rtda.Thread;

import java.math.BigInteger;
import java.util.logging.Logger;


//指令集解释器
class Interpret {

    Interpret(MemberInfo m) {
        CodeAttribute codeAttr = m.codeAttribute();
        int maxLocals = codeAttr.maxLocals();
        int maxStack = codeAttr.maxStack();
        byte[] byteCode = codeAttr.data();
        Thread thread = new Thread();
        Frame frame = thread.newFrame(maxLocals, maxStack);
        thread.pushFrame(frame);
        loop(thread, byteCode);
    }

    private void loop(Thread thread, byte[] byteCode) {
        Frame frame = thread.popFrame();
        BytecodeReader reader = new BytecodeReader();

        while (true) {
            //循环
            int pc = frame.nextPC();
            thread.setPC(pc);
            //decode
            reader.reset(byteCode, pc);
            byte opcode = reader.readByte();
            Instruction inst = Factory.newInstruction(opcode);
            if (null == inst) {
                System.out.println("寄存器(指令)尚未实现 " + byteToHexString(new byte[]{opcode}));
                break;
            }
            inst.fetchOperands(reader);
            frame.setNextPC(reader.pc());
            System.out.println("寄存器(指令)：" + byteToHexString(new byte[]{opcode}) + " -> " + inst.getClass().getSimpleName() + " => 局部变量表：" + JSON.toJSONString(frame.localVars().getSlots()) + " 操作数栈：" + JSON.toJSONString(frame.operandStack().getSlots()));            //exec
            inst.execute(frame);
        }

    }

    private static String byteToHexString(byte[] codes) {
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        for (byte b : codes) {
            int value = b & 0xFF;
            String strHex = Integer.toHexString(value);
            if (strHex.length() < 2) {
                strHex = "0" + strHex;
            }
            sb.append(strHex);
        }
        return sb.toString();
    }

}
