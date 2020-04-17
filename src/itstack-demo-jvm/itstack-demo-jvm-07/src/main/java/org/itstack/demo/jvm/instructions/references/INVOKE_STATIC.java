package org.itstack.demo.jvm.instructions.references;

import org.itstack.demo.jvm.instructions.base.ClassInitLogic;
import org.itstack.demo.jvm.instructions.base.InstructionIndex16;
import org.itstack.demo.jvm.instructions.base.MethodInvokeLogic;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.heap.constantpool.MethodRef;
import org.itstack.demo.jvm.rtda.heap.constantpool.RunTimeConstantPool;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Method;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/28
 * invokestatic调用static方法
 */
public class INVOKE_STATIC extends InstructionIndex16 {

    @Override
    public void execute(Frame frame) {
        RunTimeConstantPool runTimeConstantPool = frame.method().clazz().constantPool();
        MethodRef methodRef = (MethodRef) runTimeConstantPool.getConstants(this.idx);
        Method resolvedMethod = methodRef.ResolvedMethod();

        if (!resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError();
        }

        Class clazz = resolvedMethod.clazz();
        if (!clazz.initStarted()) {
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.thread(), clazz);
            return;
        }

        MethodInvokeLogic.invokeMethod(frame, resolvedMethod);
    }
}
