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
    public Object jClass;

    public Class(ClassFile classFile) {
        this.accessFlags = classFile.accessFlags();
        this.name = classFile.className();
        this.superClassName = classFile.superClassName();
        this.interfaceNames = classFile.interfaceNames();
        this.runTimeConstantPool = new RunTimeConstantPool(this, classFile.constantPool());
        this.fields = new Field().newFields(this, classFile.fields());
        this.methods = new Method().newMethods(this, classFile.methods());
    }

    //load Array Class
    public Class(int accessFlags, String name, ClassLoader loader, boolean initStarted, Class superClass, Class[] interfaces) {
        this.accessFlags = accessFlags;
        this.name = name;
        this.loader = loader;
        this.initStarted = initStarted;
        this.superClass = superClass;
        this.interfaces = interfaces;
    }

    public Class(int accessFlags, String name, ClassLoader loader, boolean initStarted) {
        this.accessFlags = accessFlags;
        this.name = name;
        this.loader = loader;
        this.initStarted = initStarted;
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

    public ClassLoader loader() {
        return this.loader;
    }

    public Class superClass() {
        return superClass;
    }

    public Slots staticVars() {
        return this.staticVars;
    }

    public boolean initStarted() {
        return this.initStarted;
    }

    public Object jClass() {
        return this.jClass;
    }

    public void startInit() {
        this.initStarted = true;
    }

    public boolean isAccessibleTo(Class other) {
        return this.isPublic() || this.getPackageName().equals(other.getPackageName());
    }

    public String getPackageName() {
        int i = this.name.lastIndexOf("/");
        if (i >= 0) return this.name.substring(0, i);
        return "";
    }

    public Method getMainMethod() {
        return this.getStaticMethod("main", "([Ljava/lang/String;)V", true);
    }

    public Method getClinitMethod() {
        return this.getStaticMethod("<clinit>", "()V", true);
    }

    private Method getStaticMethod(String name, String descriptor, boolean isStatic) {
        for (Class c = this; c != null; c = c.superClass) {
            if (null == c.methods) continue;
            for (Method method : c.methods) {
                if (method.isStatic() == isStatic && method.name.equals(name) && method.descriptor.equals(descriptor)) {
                    return method;
                }
            }
        }
        throw new RuntimeException("method not find: " + name + " " + descriptor);
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

    public Field getField(String name, String descriptor, boolean isStatic) {
        for (Class c = this; c != null; c = c.superClass) {
            for (Field field : c.fields) {
                if (field.isStatic() == isStatic &&
                        field.name.equals(name) &&
                        field.descriptor.equals(descriptor)) {
                    return field;
                }
            }
        }
        return null;
    }

    public boolean isJlObject() {
        return this.name.equals("java/lang/Object");
    }

    public boolean isJlCloneable() {
        return this.name.equals("java/lang/Cloneable");
    }

    public boolean isJioSerializable() {
        return this.name.endsWith("java/io/Serializable");
    }

    public Object newObject() {
        return new Object(this);
    }

    public Class arrayClass() {
        String arrayClassName = ClassNameHelper.getArrayClassName(this.name);
        return this.loader.loadClass(arrayClassName);
    }

    public String javaName() {
        return this.name.substring(0, 1) + this.name.substring(1).replace("/", ".");
    }

    public boolean IsPrimitive() {
        return null != ClassNameHelper.primitiveTypes.get(this.name);
    }

    public Method getInstanceMethod(String name, String descriptor) {
        return this.getStaticMethod(name, descriptor, false);
    }

    public Object getRefVar(String fieldName, String fieldDescriptor) {
        Field field = this.getField(fieldName, fieldDescriptor, true);
        return this.staticVars.getRef(field.slotId);
    }

    public void setRefVar(String fieldName, String fieldDescriptor, Object ref) {
        Field field = this.getField(fieldName, fieldDescriptor, true);
        this.staticVars.setRef(field.slotId, ref);
    }

    public boolean isArray() {
        return this.name.getBytes()[0] == '[';
    }

    public Class componentClass() {
        String componentClassName = ClassNameHelper.getComponentClassName(this.name);
        return this.loader.loadClass(componentClassName);
    }

    public Object newArray(int count) {
        if (!this.isArray()) {
            throw new RuntimeException("Not array class " + this.name);
        }
        switch (this.name()) {
            case "[Z":
                return new Object(this, new byte[count]);
            case "[B":
                return new Object(this, new byte[count]);
            case "[C":
                return new Object(this, new char[count]);
            case "[S":
                return new Object(this, new short[count]);
            case "[I":
                return new Object(this, new int[count]);
            case "[J":
                return new Object(this, new long[count]);
            case "[F":
                return new Object(this, new float[count]);
            case "[D":
                return new Object(this, new double[count]);
            default:
                return new Object(this, new Object[count]);
        }
    }

}
