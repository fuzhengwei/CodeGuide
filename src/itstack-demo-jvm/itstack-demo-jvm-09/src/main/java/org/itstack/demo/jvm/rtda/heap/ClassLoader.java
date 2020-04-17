package org.itstack.demo.jvm.rtda.heap;

import org.itstack.demo.jvm.classfile.ClassFile;
import org.itstack.demo.jvm.classpath.Classpath;
import org.itstack.demo.jvm.rtda.heap.constantpool.AccessFlags;
import org.itstack.demo.jvm.rtda.heap.methodarea.*;
import org.itstack.demo.jvm.rtda.heap.constantpool.RunTimeConstantPool;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Object;

import java.util.HashMap;
import java.util.Map;

/*
class names:
    - primitive types: boolean, byte, int ...
    - primitive arrays: [Z, [B, [I ...
    - non-array classes: java/lang/Object ...
    - array classes: [Ljava/lang/Object; ...
*/
public class ClassLoader {

    private Classpath classpath;
    private Map<String, Class> classMap;

    public ClassLoader(Classpath classpath) {
        this.classpath = classpath;
        this.classMap = new HashMap<>();

        this.loadBasicClasses();
        this.loadPrimitiveClasses();
    }

    private void loadBasicClasses() {
        Class jlClassClass = this.loadClass("java/lang/Class");
        for (Map.Entry<String, Class> entry : this.classMap.entrySet()) {
            Class clazz = entry.getValue();
            if (clazz.jClass == null) {
                clazz.jClass = jlClassClass.newObject();
                clazz.jClass.extra = clazz;
            }
        }
    }

    private void loadPrimitiveClasses() {
        for (Map.Entry<String, String> entry : ClassNameHelper.primitiveTypes.entrySet()) {
            loadPrimitiveClass(entry.getKey());
        }
    }

    private void loadPrimitiveClass(String className) {
        Class clazz = new Class(AccessFlags.ACC_PUBLIC,
                className,
                this,
                true);
        clazz.jClass = this.classMap.get("java/lang/Class").newObject();
        clazz.jClass.extra = clazz;
        this.classMap.put(className, clazz);
    }

    public Class loadClass(String className) {
        Class clazz = classMap.get(className);
        if (null != clazz) return clazz;

        //'['数组标识
        if (className.getBytes()[0] == '[') {
            clazz = loadArrayClass(className);
        } else {
            clazz = loadNonArrayClass(className);
        }

        Class jlClazz = this.classMap.get("java/lang/Class");
        if (null != jlClazz && null != clazz) {
            clazz.jClass = jlClazz.newObject();
            clazz.jClass.extra = clazz;
        }

        return clazz;
    }

    private Class loadArrayClass(String className) {
        Class clazz = new Class(AccessFlags.ACC_PUBLIC,
                className,
                this,
                true,
                this.loadClass("java/lang/Object"),
                new Class[]{
                        this.loadClass("java/lang/Cloneable"),
                        this.loadClass("java/io/Serializable")});
        this.classMap.put(className, clazz);
        return clazz;
    }

    private Class loadNonArrayClass(String className) {
        try {
            byte[] data = this.classpath.readClass(className);
            if (null == data) {
                throw new ClassNotFoundException(className);
            }
            Class clazz = defineClass(data);
            link(clazz);
            return clazz;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void link(Class clazz) {
        verify(clazz);
        prepare(clazz);
    }

    private void prepare(Class clazz) {
        calcInstanceFieldSlotIds(clazz);
        calcStaticFieldSlotIds(clazz);
        allocAndInitStaticVars(clazz);
    }

    private void allocAndInitStaticVars(Class clazz) {
        clazz.staticVars = new Slots(clazz.staticSlotCount);
        for (Field field : clazz.fields) {
            if (field.isStatic() && field.isFinal()) {
                initStaticFinalVar(clazz, field);
            }
        }
    }

    private void initStaticFinalVar(Class clazz, Field field) {
        Slots staticVars = clazz.staticVars;
        RunTimeConstantPool constantPool = clazz.runTimeConstantPool;
        int cpIdx = field.constValueIndex();
        int slotId = field.slotId();

        if (cpIdx > 0) {
            switch (field.descriptor()) {
                case "Z":
                case "B":
                case "C":
                case "S":
                case "I":
                    java.lang.Object val = constantPool.getConstants(cpIdx);
                    staticVars.setInt(slotId, (Integer) val);
                    break;
                case "J":
                    staticVars.setLong(slotId, (Long) constantPool.getConstants(cpIdx));
                    break;
                case "F":
                    staticVars.setFloat(slotId, (Float) constantPool.getConstants(cpIdx));
                    break;
                case "D":
                    staticVars.setDouble(slotId, (Double) constantPool.getConstants(cpIdx));
                    break;
                case "Ljava/lang/String;":
                    String goStr = (String) constantPool.getConstants(cpIdx);
                    Object jStr = StringPool.jString(clazz.loader(), goStr);
                    staticVars.setRef(slotId, jStr);
                    break;
            }
        }

    }

    private void calcStaticFieldSlotIds(Class clazz) {
        int slotId = 0;
        for (Field field : clazz.fields) {
            if (field.isStatic()) {
                field.slotId = slotId;
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.staticSlotCount = slotId;
    }

    private void calcInstanceFieldSlotIds(Class clazz) {
        int slotId = 0;
        if (clazz.superClass != null) {
            slotId = clazz.superClass.instanceSlotCount;
        }
        for (Field field : clazz.fields) {
            if (!field.isStatic()) {
                field.slotId = slotId;
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.instanceSlotCount = slotId;
    }

    private void verify(Class clazz) {
        // 校验字节码，尚未实现
    }

    private Class defineClass(byte[] data) throws Exception {
        Class clazz = parseClass(data);
        clazz.loader = this;
        resolveSuperClass(clazz);
        resolveInterfaces(clazz);
        this.classMap.put(clazz.name, clazz);
        return clazz;
    }

    private void resolveInterfaces(Class clazz) throws Exception {
        int interfaceCount = clazz.interfaceNames.length;
        if (interfaceCount > 0) {
            clazz.interfaces = new Class[interfaceCount];
            for (int i = 0; i < interfaceCount; i++) {
                clazz.interfaces[i] = clazz.loader.loadClass(clazz.interfaceNames[i]);
            }
        }
    }

    private void resolveSuperClass(Class clazz) throws Exception {
        if (!clazz.name.equals("java/lang/Object")) {
            clazz.superClass = clazz.loader.loadClass(clazz.superClassName);
        }
    }

    private Class parseClass(byte[] data) {
        ClassFile classFile = new ClassFile(data);
        return new Class(classFile);
    }


}
