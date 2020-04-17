package org.itstack.demo.jvm.rtda.heap.methodarea;

import org.itstack.demo.jvm.classfile.attributes.impl.CodeAttribute;
import org.itstack.demo.jvm.rtda.heap.constantpool.ClassRef;
import org.itstack.demo.jvm.rtda.heap.constantpool.RunTimeConstantPool;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/2
 */
public class ExceptionTable {

    private ExceptionHandler[] exceptionTable;

    public ExceptionTable(CodeAttribute.ExceptionTableEntry[] entries, RunTimeConstantPool runTimeConstantPool) {
        this.exceptionTable = new ExceptionHandler[entries.length];
        int i = 0;
        for (CodeAttribute.ExceptionTableEntry entry : entries) {
            ExceptionHandler handler = new ExceptionHandler(
                    entry.startPC(),
                    entry.endPC(),
                    entry.handlerPC(),
                    getCatchType(entry.catchType(), runTimeConstantPool)
            );
            this.exceptionTable[i++] = handler;
        }
    }

    public ClassRef getCatchType(int idx, RunTimeConstantPool runTimeConstantPool) {
        if (idx == 0) return null;
        return (ClassRef) runTimeConstantPool.getConstants(idx);
    }

    public ExceptionHandler findExceptionHandler(Class exClass, int pc) {
        for (ExceptionHandler handler : exceptionTable) {
            if (pc >= handler.startPC && pc < handler.endPC) {
                if (null == handler.catchType) {
                    return handler;
                }
                Class catchClass = handler.catchType.resolvedClass();
                if (catchClass == exClass || catchClass.isSubClassOf(exClass)) {
                    return handler;
                }
            }
        }
        return null;
    }


    class ExceptionHandler {

        int startPC;
        int endPC;
        int handlerPC;
        ClassRef catchType;

        ExceptionHandler(int startPC, int endPC, int handlerPC, ClassRef catchType) {
            this.startPC = startPC;
            this.endPC = endPC;
            this.handlerPC = handlerPC;
            this.catchType = catchType;
        }
    }

}
