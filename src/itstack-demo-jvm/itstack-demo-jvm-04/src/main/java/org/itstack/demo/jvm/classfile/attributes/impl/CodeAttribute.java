package org.itstack.demo.jvm.classfile.attributes.impl;

import org.itstack.demo.jvm.classfile.ClassReader;
import org.itstack.demo.jvm.classfile.attributes.AttributeInfo;
import org.itstack.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/26
 */
public class CodeAttribute implements AttributeInfo {

    private ConstantPool constantPool;
    private int maxStack;
    private int maxLocals;
    private byte[] data;
    private ExceptionTableEntry[] exceptionTable;
    private AttributeInfo[] attributes;

    public CodeAttribute(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.maxStack = reader.readU2ToInt();
        this.maxLocals = reader.readU2ToInt();
        int dataLength = reader.readU4ToInt();
        this.data = reader.readBytes(dataLength);
        this.exceptionTable = ExceptionTableEntry.readExceptionTable(reader);
        this.attributes = AttributeInfo.readAttributes(reader, this.constantPool);
    }

    public int maxStack() {
        return this.maxStack;
    }

    public int maxLocals() {
        return this.maxLocals;
    }

    public byte[] data() {
        return this.data;
    }

    public ExceptionTableEntry[] exceptionTable() {
        return this.exceptionTable;
    }

    public AttributeInfo[] attributes() {
        return this.attributes;
    }

    static class ExceptionTableEntry {

        private int startPC;
        private int endPC;
        private int handlerPC;
        private int catchType;

        ExceptionTableEntry(int startPC, int endPC, int handlerPC, int catchType) {
            this.startPC = startPC;
            this.endPC = endPC;
            this.handlerPC = handlerPC;
            this.catchType = catchType;
        }

        static ExceptionTableEntry[] readExceptionTable(ClassReader reader) {
            int exceptionTableLength = reader.readU2ToInt();
            ExceptionTableEntry[] exceptionTable = new ExceptionTableEntry[exceptionTableLength];
            for (int i = 0; i < exceptionTableLength; i++) {
                exceptionTable[i] = new ExceptionTableEntry(reader.readU2ToInt(), reader.readU2ToInt(), reader.readU2ToInt(), reader.readU2ToInt());
            }
            return exceptionTable;
        }

        public int startPC() {
            return this.startPC;
        }

        public int endPC() {
            return this.endPC;
        }

        public int handlerPC() {
            return this.handlerPC;
        }

        public int catchType() {
            return this.catchType;
        }

    }

}
