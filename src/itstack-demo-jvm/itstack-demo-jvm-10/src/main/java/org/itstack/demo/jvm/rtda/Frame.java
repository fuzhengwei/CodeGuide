package org.itstack.demo.jvm.rtda;

import org.itstack.demo.jvm.rtda.heap.methodarea.Method;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/26
 * 栈帧
 */
public class Frame {

    //stack is implemented as linked list
    Frame lower;

    //局部变量表
    private LocalVars localVars;

    //操作数栈
    private OperandStack operandStack;

    private Thread thread;

    private Method method;

    private int nextPC;


    public Frame(Thread thread, Method method) {
        this.thread = thread;
        this.method = method;
        this.localVars = new LocalVars(method.maxLocals);
        this.operandStack = new OperandStack(method.maxStack);
    }

    public LocalVars localVars() {
        return localVars;
    }

    public OperandStack operandStack() {
        return operandStack;
    }

    public Thread thread() {
        return this.thread;
    }

    public Method method(){
        return this.method;
    }

    public int nextPC() {
        return this.nextPC;
    }

    public void setNextPC(int nextPC) {
        this.nextPC = nextPC;
    }

    public void revertNextPC(){
        this.nextPC = this.thread.pc();
    }

}
