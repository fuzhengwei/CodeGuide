package org.itstack.demo.jvm.instructions.references;

import org.itstack.demo.jvm.instructions.base.InstructionIndex16;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.OperandStack;
import org.itstack.demo.jvm.rtda.heap.constantpool.ClassRef;
import org.itstack.demo.jvm.rtda.heap.constantpool.RunTimeConstantPool;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Object;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/29
 * create new array of reference
 */
public class ANEW_ARRAY extends InstructionIndex16 {

    @Override
    public void execute(Frame frame) {
        
        RunTimeConstantPool runTimeConstantPool = frame.method().clazz().constantPool();
        ClassRef classRef = (ClassRef) runTimeConstantPool.getConstants(this.idx);
        Class componentClass = classRef.resolvedClass();

        OperandStack stack = frame.operandStack();
        int count = stack.popInt();
        if (count < 0) {
            throw new NegativeArraySizeException();
        }

        Class arrClass = componentClass.arrayClass();
        Object arr = arrClass.newArray(count);
        stack.pushRef(arr);

    }

}
