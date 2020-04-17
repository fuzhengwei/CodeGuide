package org.itstack.demo.jvm.rtda;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/26
 * 线程
 */
public class Thread {

    //Program Counter 寄存器
    private int pc;

    //虚拟机栈
    private JvmStack stack;

    public Thread(){
        this.stack = new JvmStack(1024);
    }

    public int pc(){
        return this.pc;
    }

    public void setPC(int pc){
        this.pc = pc;
    }

    public void pushFrame(Frame frame){
        this.stack.push(frame);
    }

    public Frame popFrame(){
        return this.stack.pop();
    }

    public Frame currentFrame(){
        return this.stack.top();
    }

}
