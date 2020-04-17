package org.itstack.demo.jvm.rtda.heap.methodarea;

import org.itstack.demo.jvm.classfile.ClassFile;
import org.itstack.demo.jvm.rtda.heap.constantpool.AccessFlags;
import org.itstack.demo.jvm.rtda.heap.ClassLoader;
import org.itstack.demo.jvm.rtda.heap.constantpool.RunTimeConstantPool;

public class Class {

    public int accessFlags;
    public String name;
    public String superClassName;
    public String[] interfaceNames;
    public RunTimeConstantPool runTimeConstantPool;
    public Field[] fields;
    public Method[] methods;
    public ClassLoader loader;
    public Class superClass;
    public Class[] interfaces;
    public int instanceSlotCount;
    public int staticSlotCount;
    public Slots staticVars;
    public boolean initStarted;

    public Class(ClassFile classFile) {
        this.accessFlags = classFile.accessFlags();
        this.name = classFile.className();
        this.superClassName = classFile.superClassName();
        this.interfaceNames = classFile.interfaceNames();
        this.runTimeConstantPool = new RunTimeConstantPool(this, classFile.constantPool());
        this.fields = new Field().newFields(this, classFile.fields());
        this.methods = new Method().newMethods(this, classFile.methods());
    }

    public boolean isPublic() {
        return 0 != (this.accessFlags & AccessFlags.ACC_PUBLIC);
    }

    public boolean isFinal() {
        return 0 != (this.accessFlags & AccessFlags.ACC_FINAL);
    }

    public boolean isSuper() {
        return 0 != (this.accessFlags & AccessFlags.ACC_SUPER);
    }

    public boolean isInterface() {
        return 0 != (this.accessFlags & AccessFlags.ACC_INTERFACE);
    }

    public boolean isAbstract() {
        return 0 != (this.accessFlags & AccessFlags.ACC_ABSTRACT);
    }

    public boolean isSynthetic() {
        return 0 != (this.accessFlags & AccessFlags.ACC_SYNTHETIC);
    }

    public boolean isAnnotation() {
        return 0 != (this.accessFlags & AccessFlags.ACC_ANNOTATION);
    }

    public boolean isEnum() {
        return 0 != (this.accessFlags & AccessFlags.ACC_ENUM);
    }

    public RunTimeConstantPool constantPool() {
        return this.runTimeConstantPool;
    }

    public String name() {
        return name;
    }

    public RunTimeConstantPool runTimeConstantPool() {
        return runTimeConstantPool;
    }

    public Field[] fields() {
        return fields;
    }

    public Method[] methods() {
        return methods;
    }

    public Class superClass() {
        return superClass;
    }

    public Slots staticVars() {
        return this.staticVars;
    }

    public boolean initStarted(){
        return this.initStarted;
    }

    public void startInit(){
        this.initStarted = true;
    }

    public boolean isAccessibleTo(Class other) {
        return this.isPublic() || this.getPackageName().equals(other.getPackageName());
    }

    public String getPackageName() {
        int i = this.name.lastIndexOf("/");
        if (i >= 0) return this.name;
        return "";
    }

    public Method getMainMethod() {
        return this.getStaticMethod("main", "([Ljava/lang/String;)V");
    }

    private Method getStaticMethod(String name, String descriptor) {
        for (Method method : this.methods) {
            if (method.name.equals(name) && method.descriptor.equals(descriptor)) {
                return method;
            }
        }
        return null;
    }

    public Method getClinitMethod(){
        return this.getStaticMethod("<clinit>","()V");
    }

    public Object newObject() {
        return new Object(this);
    }

    public boolean isAssignableFrom(Class other) {
        if (this == other) return true;
        if (!other.isInterface()) {
            return this.isSubClassOf(other);
        } else {
            return this.isImplements(other);
        }
    }

    public boolean isSubClassOf(Class other) {
        for (Class c = this.superClass; c != null; c = c.superClass) {
            if (c == other) {
                return true;
            }
        }
        return false;
    }

    public boolean isImplements(Class other) {

        for (Class c = this; c != null; c = c.superClass) {
            for (Class clazz : c.interfaces) {
                if (clazz == other || clazz.isSubInterfaceOf(other)) {
                    return true;
                }
            }
        }
        return false;

    }

    public boolean isSubInterfaceOf(Class iface) {
        for (Class superInterface : this.interfaces) {
            if (superInterface == iface || superInterface.isSubInterfaceOf(iface)) {
                return true;
            }
        }
        return false;
    }

}
