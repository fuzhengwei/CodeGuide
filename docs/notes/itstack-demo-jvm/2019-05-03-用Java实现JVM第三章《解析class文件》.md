## 案例介绍
本案例主要介绍通过java代码从class文件中解析；class文件、常量池、属性表；
>作为类（或者接口）信息的载体，每个class文件都完整地定义了一个类。为了使java程序可以“编写一次，处处运行”，Java虚拟机规范对class文件格式进行了严格的规定。但是另外一方面，对于从哪里加载class文件，给了足够多的自由。Java虚拟机实现可以从文件系统读取和从JAR（或ZIP）压缩包中提取clss文件。除此之外，也可以通过网络下载、从数据库加载，甚至是在运行中直接生成class文件。Java虚拟机规范中所指的class文件，并非特指位于磁盘中的.class文件，而是泛指任何格式符号规范的class数据。

## 环境准备
1. jdk 1.8.0
2. IntelliJ IDEA Community Edition 2018.3.1 x64

## 配置信息
1. 调试配置
    1. 配置位置：Run/Debug Configurations -> program arguments
    2. 配置内容：-Xjre "C:\Program Files\Java\jdk1.8.0_161\jre" java.lang.String

## 代码示例
```java
itstack-demo-jvm-03
├── pom.xml
└── src
    └── main
    │    └── java
    │        └── org.itstack.demo.jvm
	│             ├── classfile
    │             │   ├── attributes   {BootstrapMethods/Code/ConstantValue...}
    │             │   ├── constantpool {CONSTANT_TAG_CLASS/CONSTANT_TAG_FIELDREF/CONSTANT_TAG_METHODREF...}
    │             │   ├── ClassFile.java
    │             │   ├── ClassReader.java
    │             │   └── MemberInfo.java	
    │             ├── classpath
    │             │   ├── impl
    │             │   │   ├── CompositeEntry.java
    │             │   │   ├── DirEntry.java 
    │             │   │   ├── WildcardEntry.java 
    │             │   │   └── ZipEntry.java    
    │             │   ├── Classpath.java
    │             │   └── Entry.java    
    │             ├── Cmd.java
    │             └── Main.java
    └── test
         └── java
             └── org.itstack.demo.test
                 └── HelloWorld.java
```

代码篇幅较长，不一一列举，需要源码可以关注公众号：**bugstack虫洞栈**

>AttributeInfo.java

```java
package org.itstack.demo.jvm.classfile.attributes;

import org.itstack.demo.jvm.classfile.ClassReader;
import org.itstack.demo.jvm.classfile.attributes.impl.*;
import org.itstack.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/26
 */
public interface AttributeInfo {

    void readInfo(ClassReader reader);

    static AttributeInfo[] readAttributes(ClassReader reader, ConstantPool constantPool) {
        int attributesCount = reader.readU2ToInt();
        AttributeInfo[] attributes = new AttributeInfo[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            attributes[i] = readAttribute(reader, constantPool);
        }
        return attributes;
    }

    static AttributeInfo readAttribute(ClassReader reader, ConstantPool constantPool) {
        int attrNameIdx = reader.readU2ToInt();
        String attrName = constantPool.getUTF8(attrNameIdx);
        int attrLen = reader.readU4ToInt();
        AttributeInfo attrInfo = newAttributeInfo(attrName, attrLen, constantPool);
        attrInfo.readInfo(reader);
        return attrInfo;
    }

    static AttributeInfo newAttributeInfo(String attrName, int attrLen, ConstantPool constantPool) {
        switch (attrName) {
            case "BootstrapMethods":
                return new BootstrapMethodsAttribute();
            case "Code":
                return new CodeAttribute(constantPool);
            case "ConstantValue":
                return new ConstantValueAttribute();
            case "Deprecated":
                return new DeprecatedAttribute();
            case "EnclosingMethod":
                return new EnclosingMethodAttribute(constantPool);
            case "Exceptions":
                return new ExceptionsAttribute();
            case "InnerClasses":
                return new InnerClassesAttribute();
            case "LineNumberTable":
                return new LineNumberTableAttribute();
            case "LocalVariableTable":
                return new LocalVariableTableAttribute();
            case "LocalVariableTypeTable":
                return new LocalVariableTypeTableAttribute();
            // case "MethodParameters":
            // case "RuntimeInvisibleAnnotations":
            // case "RuntimeInvisibleParameterAnnotations":
            // case "RuntimeInvisibleTypeAnnotations":
            // case "RuntimeVisibleAnnotations":
            // case "RuntimeVisibleParameterAnnotations":
            // case "RuntimeVisibleTypeAnnotations":
            case "Signature":
                return new SignatureAttribute(constantPool);
            case "SourceFile":
                return new SourceFileAttribute(constantPool);
            // case "SourceDebugExtension":
            // case "StackMapTable":
            case "Synthetic":
                return new SyntheticAttribute();
            default:
                return new UnparsedAttribute(attrName, attrLen);
        }

    }

}
```

>ConstantInfo.java

```java
package org.itstack.demo.jvm.classfile.constantpool;

import org.itstack.demo.jvm.classfile.ClassReader;
import org.itstack.demo.jvm.classfile.constantpool.impl.*;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/26
 */
public interface ConstantInfo {

    int CONSTANT_TAG_CLASS = 7;
    int CONSTANT_TAG_FIELDREF = 9;
    int CONSTANT_TAG_METHODREF = 10;
    int CONSTANT_TAG_INTERFACEMETHODREF = 11;
    int CONSTANT_TAG_STRING = 8;
    int CONSTANT_TAG_INTEGER = 3;
    int CONSTANT_TAG_FLOAT = 4;
    int CONSTANT_TAG_LONG = 5;
    int CONSTANT_TAG_DOUBLE = 6;
    int CONSTANT_TAG_NAMEANDTYPE = 12;
    int CONSTANT_TAG_UTF8 = 1;
    int CONSTANT_TAG_METHODHANDLE = 15;
    int CONSTANT_TAG_METHODTYPE = 16;
    int CONSTANT_TAG_INVOKEDYNAMIC = 18;

    void readInfo(ClassReader reader);

    int tag();
    
    static ConstantInfo readConstantInfo(ClassReader reader, ConstantPool constantPool) {
        int tag = reader.readU1ToInt();
        ConstantInfo constantInfo = newConstantInfo(tag, constantPool);
        constantInfo.readInfo(reader);
        return constantInfo;
    }

    static ConstantInfo newConstantInfo(int tag, ConstantPool constantPool) {
        switch (tag) {
            case CONSTANT_TAG_INTEGER:
                return new ConstantIntegerInfo();
            case CONSTANT_TAG_FLOAT:
                return new ConstantFloatInfo();
            case CONSTANT_TAG_LONG:
                return new ConstantLongInfo();
            case CONSTANT_TAG_DOUBLE:
                return new ConstantDoubleInfo();
            case CONSTANT_TAG_UTF8:
                return new ConstantUtf8Info();
            case CONSTANT_TAG_STRING:
                return new ConstantStringInfo(constantPool);
            case CONSTANT_TAG_CLASS:
                return new ConstantClassInfo(constantPool);
            case CONSTANT_TAG_FIELDREF:
                return new ConstantFieldRefInfo(constantPool);
            case CONSTANT_TAG_METHODREF:
                return new ConstantMethodRefInfo(constantPool);
            case CONSTANT_TAG_INTERFACEMETHODREF:
                return new ConstantInterfaceMethodRefInfo(constantPool);
            case CONSTANT_TAG_NAMEANDTYPE:
                return new ConstantNameAndTypeInfo();
            case CONSTANT_TAG_METHODTYPE:
                return new ConstantMethodTypeInfo();
            case CONSTANT_TAG_METHODHANDLE:
                return new ConstantMethodHandleInfo();
            case CONSTANT_TAG_INVOKEDYNAMIC:
                return new ConstantInvokeDynamicInfo();
            default:
                throw new ClassFormatError("constant pool tag");
        }
    }
}
```

>ClassFile.java

```java
package org.itstack.demo.jvm.classfile;

import org.itstack.demo.jvm.classfile.attributes.AttributeInfo;
import org.itstack.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/26
 */
public class ClassFile {

    private int minorVersion;
    private int majorVersion;
    private ConstantPool constantPool;
    private int accessFlags;
    private int thisClassIdx;
    private int supperClassIdx;
    private int[] interfaces;
    private MemberInfo[] fields;
    private MemberInfo[] methods;
    private AttributeInfo[] attributes;

    public ClassFile(byte[] classData) {
        ClassReader reader = new ClassReader(classData);
        this.readAndCheckMagic(reader);
        this.readAndCheckVersion(reader);
        this.constantPool = this.readConstantPool(reader);
        this.accessFlags = reader.readU2ToInt();
        this.thisClassIdx = reader.readU2ToInt();
        this.supperClassIdx = reader.readU2ToInt();
        this.interfaces = reader.readUInt16s();
        this.fields = MemberInfo.readMembers(reader, constantPool);
        this.methods = MemberInfo.readMembers(reader, constantPool);
        this.attributes = AttributeInfo.readAttributes(reader, constantPool);
    }

    private void readAndCheckMagic(ClassReader reader) {
        String magic = reader.readU4ToHexStr();
        if (!"cafebabe".equals(magic)) {
            throw new ClassFormatError("magic!");
        }
    }

    private void readAndCheckVersion(ClassReader reader) {
        this.minorVersion = reader.readU2ToInt();
        this.majorVersion = reader.readU2ToInt();
        switch (this.majorVersion) {
            case 45:
                return;
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
                if (this.minorVersion == 0)
                    return;
        }
        throw new UnsupportedClassVersionError();
    }

    private ConstantPool readConstantPool(ClassReader reader) {
        return new ConstantPool(reader);
    }

    public int minorVersion(){
        return this.minorVersion;
    }

    public int majorVersion(){
        return this.majorVersion;
    }

    public ConstantPool constantPool(){
        return this.constantPool;
    }

    public int accessFlags() {
        return this.accessFlags;
    }

    public MemberInfo[] fields() {
        return this.fields;
    }

    public MemberInfo[] methods() {
        return this.methods;
    }

    public String className() {
        return this.constantPool.getClassName(this.thisClassIdx);
    }

    public String superClassName() {
        if (this.supperClassIdx <= 0) return "";
        return this.constantPool.getClassName(this.supperClassIdx);
    }

    public String[] interfaceNames() {
        String[] interfaceNames = new String[this.interfaces.length];
        for (int i = 0; i < this.interfaces.length; i++) {
            interfaceNames[i] = this.constantPool.getClassName(interfaces[i]);
        }
        return interfaceNames;
    }

}
```

>ClassReader.java

```java
package org.itstack.demo.jvm.classfile;

import java.math.BigInteger;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/13
 * <p>
 * java虚拟机定义了u1、u2、u4三种数据类型来表示；1字节、2字节、4字节，无符号整数。
 * 在如下实现中，用增位方式表示无符号类型：
 * u1、u2可以用int类型存储，因为int类型是4字节
 * u4 需要用long类型存储，因为long类型是8字节
 */
public class ClassReader {

    private byte[] data;

    public ClassReader(byte[] data) {
        this.data = data;
    }

    //u1
    public int readUint8() {
        byte[] val = readByte(1);
        return byte2int(val);
    }

    //u2
    public int readUint16() {
        byte[] val = readByte(2);
        return byte2int(val);
    }

    //u4
    public long readUint32() {
        byte[] val = readByte(4);
        String str_hex = new BigInteger(1, val).toString(16);
        return Long.parseLong(str_hex, 16);
    }

    public float readUint64TFloat() {
        byte[] val = readByte(8);
        return new BigInteger(1, val).floatValue();
    }

    public long readUint64TLong() {
        byte[] val = readByte(8);
        return new BigInteger(1, val).longValue();
    }

    public double readUint64TDouble() {
        byte[] val = readByte(8);
        return new BigInteger(1, val).doubleValue();
    }

    public int[] readUint16s() {
        int n = this.readUint16();
        int[] s = new int[n];
        for (int i = 0; i < n; i++) {
            s[i] = this.readUint16();
        }
        return s;
    }

    public byte[] readBytes(int n) {
        return readByte(n);
    }

    private byte[] readByte(int length) {
        byte[] copy = new byte[length];
        System.arraycopy(data, 0, copy, 0, length);
        System.arraycopy(data, length, data, 0, data.length - length);
        return copy;
    }

    private int byte2int(byte[] val) {
        String str_hex = new BigInteger(1, val).toString(16);
        return Integer.parseInt(str_hex, 16);
    }

}
```

>MemberInfo.java

```java
package org.itstack.demo.jvm.classfile;

import org.itstack.demo.jvm.classfile.attributes.AttributeInfo;
import org.itstack.demo.jvm.classfile.attributes.impl.CodeAttribute;
import org.itstack.demo.jvm.classfile.attributes.impl.ConstantValueAttribute;
import org.itstack.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/26
 */
public class MemberInfo {

    private ConstantPool constantPool;
    private int accessFlags;
    private int nameIdx;
    private int descriptorIdx;
    private AttributeInfo[] attributes;

    public MemberInfo(ClassReader reader, ConstantPool constantPool) {
        this.constantPool = constantPool;
        this.accessFlags = reader.readU2ToInt();
        this.nameIdx = reader.readU2ToInt();
        this.descriptorIdx = reader.readU2ToInt();
        this.attributes = AttributeInfo.readAttributes(reader, constantPool);
    }

    public static MemberInfo[] readMembers(ClassReader reader, ConstantPool constantPool) {
        int fieldCount = reader.readU2ToInt();
        MemberInfo[] fields = new MemberInfo[fieldCount];
        for (int i = 0; i < fieldCount; i++) {
            fields[i] = new MemberInfo(reader, constantPool);
        }
        return fields;
    }

    public int accessFlags() {
        return this.accessFlags;
    }

    public String name() {
        return this.constantPool.getUTF8(this.nameIdx);
    }

    public String descriptor() {
        return this.constantPool.getUTF8(this.descriptorIdx);
    }

    public CodeAttribute codeAttribute() {
        for (AttributeInfo attrInfo : attributes) {
            if (attrInfo instanceof CodeAttribute) return (CodeAttribute) attrInfo;
        }
        return null;
    }

    public ConstantValueAttribute ConstantValueAttribute() {
        for (AttributeInfo attrInfo : attributes) {
            if (attrInfo instanceof ConstantValueAttribute) return (ConstantValueAttribute) attrInfo;
        }
        return null;
    }

}
```

>Main.java

```java
package org.itstack.demo.jvm;

import org.itstack.demo.jvm.classfile.ClassFile;
import org.itstack.demo.jvm.classfile.MemberInfo;
import org.itstack.demo.jvm.classpath.Classpath;

import java.util.Arrays;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/24
 */
public class Main {

    public static void main(String[] args) {
        Cmd cmd = Cmd.parse(args);
        if (!cmd.ok || cmd.helpFlag) {
            System.out.println("Usage: <main class> [-options] class [args...]");
            return;
        }
        if (cmd.versionFlag) {
            //注意案例测试都是基于1.8，另外jdk1.9以后使用模块化没有rt.jar
            System.out.println("java version \"1.8.0\"");
            return;
        }
        startJVM(cmd);
    }

    private static void startJVM(Cmd cmd) {
        Classpath classpath = new Classpath(cmd.jre, cmd.classpath);
        System.out.printf("classpath：%s class：%s args：%s\n",
                classpath, cmd.getMainClass(), cmd.getAppArgs());
        //获取className
        String className = cmd.getMainClass().replace(".", "/");
        ClassFile classFile = loadClass(className, classpath);
        assert classFile != null;
        printClassInfo(classFile);
    }

    private static ClassFile loadClass(String className, Classpath classpath) {
        try {
            byte[] classData = classpath.readClass(className);
            return new ClassFile(classData);
        } catch (Exception e) {
            System.out.println("Could not find or load main class " + className);
            return null;
        }
    }

    private static void printClassInfo(ClassFile cf) {
        System.out.println("version: " + cf.majorVersion() + "." + cf.minorVersion());
        System.out.println("constants count：" + cf.constantPool().getSiz());
        System.out.format("access flags：0x%x\n", cf.accessFlags());
        System.out.println("this class：" + cf.className());
        System.out.println("super class：" + cf.superClassName());
        System.out.println("interfaces：" + Arrays.toString(cf.interfaceNames()));
        System.out.println("fields count：" + cf.fields().length);
        for (MemberInfo memberInfo : cf.fields()) {
            System.out.format("  %s\n", memberInfo.name());
        }
        System.out.println("methods count: " + cf.methods().length);
        for (MemberInfo memberInfo : cf.methods()) {
            System.out.format("  %s\n", memberInfo.name());
        }
    }

}
```

## 测试结果
```java
"C:\Program Files\Java\jdk1.8.0_161\bin\java.exe" "-javaagent:D:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2018.3.1\lib\idea_rt.jar=61458:D:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2018.3.1\bin" -Dfile.encoding=UTF-8 -classpath "C:\Program Files\Java\jdk1.8.0_161\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\ext\jfxrt.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\ext\nashorn.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\ext\sunpkcs11.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\ext\zipfs.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\javaws.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\jce.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\jfxswt.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\management-agent.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\plugin.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\resources.jar;C:\Program Files\Java\jdk1.8.0_161\jre\lib\rt.jar;E:\itstack\git\istack-demo\itstack-demo-jvm\itstack-demo-jvm-03\target\classes;D:\Program Files (x86)\apache-maven-2.2.1\repository\com\beust\jcommander\1.72\jcommander-1.72.jar;D:\Program Files (x86)\apache-maven-2.2.1\repository\org\projectlombok\lombok\1.18.0\lombok-1.18.0.jar;D:\Program Files (x86)\apache-maven-2.2.1\repository\com\alibaba\fastjson\1.2.40\fastjson-1.2.40.jar" org.itstack.demo.jvm.Main -Xjre "C:\Program Files\Java\jdk1.8.0_161\jre" java.lang.String
classpath：org.itstack.demo.jvm.classpath.Classpath@4bf558aa class：java.lang.String args：null
version: 52.0
constants count：540
access flags：0x31
this class：java/lang/String
super class：java/lang/Object
interfaces：[java/io/Serializable, java/lang/Comparable, java/lang/CharSequence]
fields count：5
value 		 [C
hash 		 I
serialVersionUID 		 J
serialPersistentFields 		 [Ljava/io/ObjectStreamField;
CASE_INSENSITIVE_ORDER 		 Ljava/util/Comparator;
methods count: 94
<init> 		 ()V
<init> 		 (Ljava/lang/String;)V
<init> 		 ([C)V
<init> 		 ([CII)V
<init> 		 ([III)V
<init> 		 ([BIII)V
<init> 		 ([BI)V
checkBounds 		 ([BII)V
<init> 		 ([BIILjava/lang/String;)V
<init> 		 ([BIILjava/nio/charset/Charset;)V
<init> 		 ([BLjava/lang/String;)V
<init> 		 ([BLjava/nio/charset/Charset;)V
<init> 		 ([BII)V
<init> 		 ([B)V
<init> 		 (Ljava/lang/StringBuffer;)V
<init> 		 (Ljava/lang/StringBuilder;)V
<init> 		 ([CZ)V
length 		 ()I
isEmpty 		 ()Z
charAt 		 (I)C
codePointAt 		 (I)I
codePointBefore 		 (I)I
codePointCount 		 (II)I
offsetByCodePoints 		 (II)I
getChars 		 ([CI)V
getChars 		 (II[CI)V
getBytes 		 (II[BI)V
getBytes 		 (Ljava/lang/String;)[B
getBytes 		 (Ljava/nio/charset/Charset;)[B
getBytes 		 ()[B
equals 		 (Ljava/lang/Object;)Z
contentEquals 		 (Ljava/lang/StringBuffer;)Z
nonSyncContentEquals 		 (Ljava/lang/AbstractStringBuilder;)Z
contentEquals 		 (Ljava/lang/CharSequence;)Z
equalsIgnoreCase 		 (Ljava/lang/String;)Z
compareTo 		 (Ljava/lang/String;)I
compareToIgnoreCase 		 (Ljava/lang/String;)I
regionMatches 		 (ILjava/lang/String;II)Z
regionMatches 		 (ZILjava/lang/String;II)Z
startsWith 		 (Ljava/lang/String;I)Z
startsWith 		 (Ljava/lang/String;)Z
endsWith 		 (Ljava/lang/String;)Z
hashCode 		 ()I
indexOf 		 (I)I
indexOf 		 (II)I
indexOfSupplementary 		 (II)I
lastIndexOf 		 (I)I
lastIndexOf 		 (II)I
lastIndexOfSupplementary 		 (II)I
indexOf 		 (Ljava/lang/String;)I
indexOf 		 (Ljava/lang/String;I)I
indexOf 		 ([CIILjava/lang/String;I)I
indexOf 		 ([CII[CIII)I
lastIndexOf 		 (Ljava/lang/String;)I
lastIndexOf 		 (Ljava/lang/String;I)I
lastIndexOf 		 ([CIILjava/lang/String;I)I
lastIndexOf 		 ([CII[CIII)I
substring 		 (I)Ljava/lang/String;
substring 		 (II)Ljava/lang/String;
subSequence 		 (II)Ljava/lang/CharSequence;
concat 		 (Ljava/lang/String;)Ljava/lang/String;
replace 		 (CC)Ljava/lang/String;
matches 		 (Ljava/lang/String;)Z
contains 		 (Ljava/lang/CharSequence;)Z
replaceFirst 		 (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
replaceAll 		 (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
replace 		 (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
split 		 (Ljava/lang/String;I)[Ljava/lang/String;
split 		 (Ljava/lang/String;)[Ljava/lang/String;
join 		 (Ljava/lang/CharSequence;[Ljava/lang/CharSequence;)Ljava/lang/String;
join 		 (Ljava/lang/CharSequence;Ljava/lang/Iterable;)Ljava/lang/String;
toLowerCase 		 (Ljava/util/Locale;)Ljava/lang/String;
toLowerCase 		 ()Ljava/lang/String;
toUpperCase 		 (Ljava/util/Locale;)Ljava/lang/String;
toUpperCase 		 ()Ljava/lang/String;
trim 		 ()Ljava/lang/String;
toString 		 ()Ljava/lang/String;
toCharArray 		 ()[C
format 		 (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
format 		 (Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
valueOf 		 (Ljava/lang/Object;)Ljava/lang/String;
valueOf 		 ([C)Ljava/lang/String;
valueOf 		 ([CII)Ljava/lang/String;
copyValueOf 		 ([CII)Ljava/lang/String;
copyValueOf 		 ([C)Ljava/lang/String;
valueOf 		 (Z)Ljava/lang/String;
valueOf 		 (C)Ljava/lang/String;
valueOf 		 (I)Ljava/lang/String;
valueOf 		 (J)Ljava/lang/String;
valueOf 		 (F)Ljava/lang/String;
valueOf 		 (D)Ljava/lang/String;
intern 		 ()Ljava/lang/String;
compareTo 		 (Ljava/lang/Object;)I
<clinit> 		 ()V

Process finished with exit code 0

```