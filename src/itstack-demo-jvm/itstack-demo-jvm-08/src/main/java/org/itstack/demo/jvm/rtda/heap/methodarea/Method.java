package org.itstack.demo.jvm.rtda.heap.methodarea;

import org.itstack.demo.jvm.classfile.MemberInfo;
import org.itstack.demo.jvm.classfile.attributes.impl.CodeAttribute;
import org.itstack.demo.jvm.rtda.heap.constantpool.AccessFlags;

import java.util.List;

public class Method extends ClassMember {

    public int maxStack;
    public int maxLocals;
    public byte[] code;
    private int argSlotCount;

    Method[] newMethods(Class clazz, MemberInfo[] cfMethods) {
        Method[] methods = new Method[cfMethods.length];
        for (int i = 0; i < cfMethods.length; i++) {
            methods[i] = new Method();
            methods[i].clazz = clazz;
            methods[i].copyMemberInfo(cfMethods[i]);
            methods[i].copyAttributes(cfMethods[i]);
            methods[i].calcArgSlotCount();
        }
        return methods;
    }

    private void copyAttributes(MemberInfo cfMethod) {
        CodeAttribute codeAttr = cfMethod.codeAttribute();
        if (null != codeAttr) {
            this.maxStack = codeAttr.maxStack();
            this.maxLocals = codeAttr.maxLocals();
            this.code = codeAttr.data();
        }
    }

    private void calcArgSlotCount() {
        MethodDescriptor parsedDescriptor = MethodDescriptorParser.parseMethodDescriptorParser(this.descriptor);
        List<String> parameterTypes = parsedDescriptor.parameterTypes;
        for (String paramType : parameterTypes) {
            this.argSlotCount++;
            if ("J".equals(paramType) || "D".equals(paramType)) {
                this.argSlotCount++;
            }
        }
        if (!this.isStatic()) {
            this.argSlotCount++;
        }
    }

    public boolean isSynchronized() {
        return 0 != (this.accessFlags & AccessFlags.ACC_SYNCHRONIZED);
    }

    public boolean isBridge() {
        return 0 != (this.accessFlags & AccessFlags.ACC_BRIDGE);
    }

    public boolean isVarargs() {
        return 0 != (this.accessFlags & AccessFlags.ACC_VARARGS);
    }

    public boolean isNative() {
        return 0 != (this.accessFlags & AccessFlags.ACC_NATIVE);
    }

    public boolean isAbstract() {
        return 0 != (this.accessFlags & AccessFlags.ACC_ABSTRACT);
    }

    public boolean isStrict() {
        return 0 != (this.accessFlags & AccessFlags.ACC_STRICT);
    }

    public int maxStack() {
        return this.maxStack;
    }

    public int maxLocals() {
        return this.maxLocals;
    }

    public byte[] code() {
        return this.code;
    }

    public int argSlotCount() {
        return this.argSlotCount;
    }

}
