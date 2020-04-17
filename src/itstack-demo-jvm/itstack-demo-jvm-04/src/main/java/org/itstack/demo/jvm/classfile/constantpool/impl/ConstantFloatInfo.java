package org.itstack.demo.jvm.classfile.constantpool.impl;

import org.itstack.demo.jvm.classfile.ClassReader;
import org.itstack.demo.jvm.classfile.constantpool.ConstantInfo;
import sun.reflect.ConstantPool;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/26
 */
public class ConstantFloatInfo implements ConstantInfo {

    private float val;

    @Override
    public void readInfo(ClassReader reader) {
       this.val = reader.readU4ToFloat();
    }

    @Override
    public int tag() {
        return this.CONSTANT_TAG_FLOAT;
    }

    public float value(){
        return this.val;
    }

}
