package org.itstack.demo.jvm.classfile.constantpool.impl;

import org.itstack.demo.jvm.classfile.ClassReader;
import org.itstack.demo.jvm.classfile.constantpool.ConstantInfo;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/26
 */
public class ConstantNameAndTypeInfo implements ConstantInfo {

     public int nameIdx;
     public int descIdx;

    @Override
    public void readInfo(ClassReader reader) {
         this.nameIdx = reader.readUint16();
         this.descIdx = reader.readUint16();
    }

    @Override
    public int tag() {
        return this.CONSTANT_TAG_NAMEANDTYPE;
    }

}
