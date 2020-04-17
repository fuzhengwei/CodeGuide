package org.itstack.demo.jvm.rtda.heap.constantpool;

import org.itstack.demo.jvm.classfile.constantpool.impl.ConstantMemberRefInfo;

import java.util.Map;

public class MemberRef extends SymRef {

    public String name;
    public String descriptor;

    public void copyMemberRefInfo(ConstantMemberRefInfo refInfo){
        this.className = refInfo.className();
        Map<String, String> map = refInfo.nameAndDescriptor();
        this.name = map.get("name");
        this.descriptor = map.get("_type");
    }

    public String name(){
        return this.name;
    }

    public String descriptor(){
        return this.descriptor;
    }

}
