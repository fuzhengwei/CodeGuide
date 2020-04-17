package org.itstack.demo.jvm._native.java;


import org.itstack.demo.jvm._native.NativeMethod;
import org.itstack.demo.jvm._native.Registry;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.Thread;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Method;
import org.itstack.demo.jvm.rtda.heap.methodarea.Object;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/2
 */
public class _Throwable {

    private StackTraceElement stackTraceElement;

    private final String jlThrowable = "java/lang/Throwable";

    public _Throwable() {
        Registry.register(jlThrowable, "fillInStackTrace", "(I)Ljava/lang/Throwable;", new NativeMethod(this, "fillInStackTrace"));
        Registry.register(jlThrowable, "registerNatives", "()V", new NativeMethod(this, "registerNatives"));
    }

    public void registerNatives(Frame frame) {
        // do nothing
    }

    public String string() {
        return String.format("%s.%s(%s:%d)", this.stackTraceElement.className, this.stackTraceElement.methodName, this.stackTraceElement.fileName, this.stackTraceElement.lineNumber);
    }

    public void fillInStackTrace(Frame frame) {
        Object thiz = frame.localVars().getThis();
        frame.operandStack().pushRef(thiz);

        _Throwable[] stes = createStackTraceElements(thiz, frame.thread());
        thiz.setExtra(stes);
    }

    private _Throwable[] createStackTraceElements(Object tObj, Thread thread) {
        int skip = distanceToObject(tObj.clazz()) + 2;
        Frame[] frames = thread.getFrames();
        _Throwable[] stes = new _Throwable[frames.length - skip];
        int idx = 0;
        for (int i = skip; i < frames.length; i++) {
            stes[idx] = createStackTraceElement(frames[i]);
        }
        return stes;
    }

    private int distanceToObject(Class clazz) {
        int distance = 0;
        for (Class c = clazz.superClass(); c != null; c = c.superClass()) {
            distance++;
        }
        return distance;
    }

    private _Throwable createStackTraceElement(Frame frame) {
        Method method = frame.method();
        Class clazz = method.clazz();
        StackTraceElement stackTraceElement = new StackTraceElement();
        stackTraceElement.fileName = clazz.sourceFile();
        stackTraceElement.className = clazz.javaName();
        stackTraceElement.methodName = method.name();
        stackTraceElement.lineNumber = method.getLineNumber(frame.nextPC() - 1);
        _Throwable throwable = new _Throwable();
        throwable.stackTraceElement = stackTraceElement;
        return throwable;
    }

    private class StackTraceElement {
        private String fileName;
        private String className;
        private String methodName;
        private int lineNumber;
    }

}
