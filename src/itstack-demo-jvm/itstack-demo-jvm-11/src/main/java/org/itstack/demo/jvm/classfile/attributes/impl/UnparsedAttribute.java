package org.itstack.demo.jvm.classfile.attributes.impl;

import org.itstack.demo.jvm.classfile.ClassReader;
import org.itstack.demo.jvm.classfile.attributes.AttributeInfo;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/26
 */
public class UnparsedAttribute implements AttributeInfo {

    private String name;
    private int length;
    private byte[] info;

    public UnparsedAttribute(String attrName, int attrLen) {
        this.name = attrName;
        this.length = attrLen;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.info = reader.readBytes(this.length);
    }

    public byte[] info(){
        return this.info;
    }

}
