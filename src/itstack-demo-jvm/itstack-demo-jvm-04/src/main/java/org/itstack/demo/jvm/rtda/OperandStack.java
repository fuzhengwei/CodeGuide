package org.itstack.demo.jvm.rtda;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/26
 * 操作数栈
 */
public class OperandStack {

    private int size = 0;
    private Slot[] slots;

    public OperandStack(int maxStack) {
        if (maxStack > 0) {
            slots = new Slot[maxStack];
            for (int i = 0; i < maxStack; i++) {
                slots[i] = new Slot();
            }
        }
    }

    public void pushInt(int val) {
        slots[size].num = val;
        size++;
    }

    public int popInt(){
        size --;
        return slots[size].num;
    }

    public void pushRef(Object ref){
        slots[size].ref = ref;
        size++;
    }

    public Object popRef(){
        size --;
        Object ref = slots[size].ref;
        slots[size].ref = null;
        return ref;
    }

}
