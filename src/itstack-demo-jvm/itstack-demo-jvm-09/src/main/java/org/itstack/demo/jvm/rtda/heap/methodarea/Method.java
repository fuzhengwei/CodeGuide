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
            methods[i] = newMethod(clazz, cfMethods[i]);
        }
        return methods;
    }

    private Method newMethod(Class clazz, MemberInfo cfMethod) {
        Method method = new Method();
        method.clazz = clazz;
        method.copyMemberInfo(cfMethod);
        method.copyAttributes(cfMethod);
        MethodDescriptor md = MethodDescriptorParser.parseMethodDescriptorParser(method.descriptor);
        method.calcArgSlotCount(md.parameterTypes);
        if (method.isNative()) {
            method.injectCodeAttribute(md.returnType);
        }
        return method;
    }

    private void injectCodeAttribute(String returnType) {
        this.maxStack = 4; //todo
        this.maxLocals = this.argSlotCount;

        switch (returnType.getBytes()[0]) {
            case 'V':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xb1};
                break;
            case 'L':
            case '[':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xb0};
                break;
            case 'D':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xaf};
                break;
            case 'F':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xae};
                break;
            case 'J':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xad};
                break;
            default:
                this.code = new byte[]{(byte) 0xfe, (byte) 0xac};
                break;
        }
    }

    private void copyAttributes(MemberInfo cfMethod) {
        CodeAttribute codeAttr = cfMethod.codeAttribute();
        if (null != codeAttr) {
            this.maxStack = codeAttr.maxStack();
            this.maxLocals = codeAttr.maxLocals();
            this.code = codeAttr.data();
        }
    }

    private void calcArgSlotCount(List<String> parameterTypes) {
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
