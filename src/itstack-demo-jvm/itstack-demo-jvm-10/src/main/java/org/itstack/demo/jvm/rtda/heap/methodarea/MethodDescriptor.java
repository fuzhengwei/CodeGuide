package org.itstack.demo.jvm.rtda.heap.methodarea;

import java.util.ArrayList;
import java.util.List;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/28
 */
public class MethodDescriptor {

    public List<String> parameterTypes = new ArrayList<>();
    public String returnType;

    public void addParameterType(String type){
        this.parameterTypes.add(type);
    }

}
