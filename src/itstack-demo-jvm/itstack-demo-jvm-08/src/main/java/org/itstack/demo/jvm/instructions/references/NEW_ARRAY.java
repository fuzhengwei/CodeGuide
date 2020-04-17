package org.itstack.demo.jvm.instructions.references;

import org.itstack.demo.jvm.instructions.base.BytecodeReader;
import org.itstack.demo.jvm.instructions.base.Instruction;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.OperandStack;
import org.itstack.demo.jvm.rtda.heap.ClassLoader;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Object;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/29
 */
public class NEW_ARRAY implements Instruction {

    private byte atype;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.atype = reader.readByte();
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        int count = stack.popInt();
        if (count < 0) {
            throw new NegativeArraySizeException();
        }

        ClassLoader classLoader = frame.method().clazz().loader();
        Class arrClass = getPrimitiveArrayClass(classLoader, this.atype);
        Object arr = arrClass.newArray(count);
        stack.pushRef(arr);

    }

    private Class getPrimitiveArrayClass(ClassLoader loader, byte atype){
        switch (atype) {
            case ArrayType.AT_BOOLEAN:
                return loader.loadClass("[Z");
            case ArrayType.AT_BYTE:
                return loader.loadClass("[B");
            case ArrayType.AT_CHAR:
                return loader.loadClass("[C");
            case ArrayType.AT_SHORT:
                return loader.loadClass("[S");
            case ArrayType.AT_INT:
                return loader.loadClass("[I");
            case ArrayType.AT_LONG:
                return loader.loadClass("[J");
            case ArrayType.AT_FLOAT:
                return loader.loadClass("[F");
            case ArrayType.AT_DOUBLE:
                return loader.loadClass("[D");
            default:
                throw new RuntimeException("Invalid atype!");
        }
    }

    static class ArrayType {
        static final byte AT_BOOLEAN = 4;
        static final byte AT_CHAR = 5;
        static final byte AT_FLOAT = 6;
        static final byte AT_DOUBLE = 7;
        static final byte AT_BYTE = 8;
        static final byte AT_SHORT = 9;
        static final byte AT_INT = 10;
        static final byte AT_LONG = 11;
    }

}
