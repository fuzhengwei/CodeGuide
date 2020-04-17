package org.itstack.demo.jvm.instructions.references;

import org.itstack.demo.jvm.instructions.base.BytecodeReader;
import org.itstack.demo.jvm.instructions.base.Instruction;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.OperandStack;
import org.itstack.demo.jvm.rtda.heap.constantpool.ClassRef;
import org.itstack.demo.jvm.rtda.heap.constantpool.RunTimeConstantPool;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Object;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/29
 * create new multidimensional array
 */
public class MULTI_ANEW_ARRAY implements Instruction {

    private short idx;
    private byte dimensions;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.idx = (short) reader.readShort();
        this.dimensions = reader.readByte();
    }

    @Override
    public void execute(Frame frame) {
        RunTimeConstantPool runTimeConstantPool = frame.method().clazz().constantPool();
        ClassRef classRef = (ClassRef) runTimeConstantPool.getConstants((int) this.idx);
        Class arrClass = classRef.resolvedClass();

        OperandStack stack = frame.operandStack();
        int[] counts = popAndCheckCounts(stack, this.dimensions);
        Object arr = newMultiDimensionalArray(counts, arrClass);
        stack.pushRef(arr);

    }

    private int[] popAndCheckCounts(OperandStack stack, int dimensions) {
        int[] counts = new int[dimensions];
        for (int i = dimensions - 1; i >= 0; i--) {
            counts[i] = stack.popInt();
            if (counts[i] < 0) {
                throw new NegativeArraySizeException();
            }
        }

        return counts;
    }

    private Object newMultiDimensionalArray(int[] counts, Class arrClass) {
        int count = counts[0];
        Object arr = arrClass.newArray(count);
        if (counts.length > 1) {
            Object[] refs = arr.refs();
            for (int i = 0; i < refs.length; i++) {
                int[] copyCount = new int[counts.length - 1];
                System.arraycopy(counts, 1, copyCount, 0, counts.length - 1);
                refs[i] = newMultiDimensionalArray(copyCount, arrClass.componentClass());
            }
        }

        return arr;
    }

}
