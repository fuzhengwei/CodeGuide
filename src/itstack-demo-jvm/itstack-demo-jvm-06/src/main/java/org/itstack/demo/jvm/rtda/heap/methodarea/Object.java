package org.itstack.demo.jvm.rtda.heap.methodarea;

public class Object {

    Class clazz;
    Slots fields;

    public Object(Class clazz){
        this.clazz = clazz;
        this.fields = new Slots(clazz.instanceSlotCount);
    }

    public Class clazz(){
        return this.clazz;
    }

    public Slots fields(){
        return this.fields;
    }

    public boolean isInstanceOf(Class clazz){
        return clazz.isAssignableFrom(this.clazz);
    }

}
