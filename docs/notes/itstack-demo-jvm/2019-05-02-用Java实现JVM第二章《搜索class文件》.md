## 案例简述
本章节主要了解Java虚拟机从哪里寻找class文件并且读取class内字节码

## 环境准备
1. jdk 1.8.0 
2. IntelliJ IDEA Community Edition 2018.3.1 x64
3. Notepad++ （插件安装HEX-Editor，用于查看class字节）

## 配置信息
1. 调试配置
    1. 配置位置：Run/Debug Configurations -> program arguments
    2. 配置内容：-Xjre "C:\Program Files\Java\jdk1.8.0_161\jre" E:\itstack\git\istack-demo\itstack-demo-jvm\itstack-demo-jvm-02\target\test-classes\org\itstack\demo\test\HelloWorld

## 代码示例
```java
itstack-demo-jvm-02
├── pom.xml
└── src
    └── main
    │    └── java
    │        └── org.itstack.demo.jvm
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

>pom.xml

```xml
<!-- 命令行参数解析器 -->
<dependency>
  <groupId>com.beust</groupId>
  <artifactId>jcommander</artifactId>
  <version>1.72</version>
</dependency>
```

>CompositeEntry.java

```java
package org.itstack.demo.jvm.classpath.impl;

import org.itstack.demo.jvm.classpath.Entry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/24
 */
public class CompositeEntry implements Entry {

    private final List<Entry> entryList = new ArrayList<>();

    public CompositeEntry(String pathList) {
        String[] paths = pathList.split(File.pathSeparator);
        for (String path : paths) {
            entryList.add(Entry.create(path));
        }
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        for (Entry entry : entryList) {
            try {
                return entry.readClass(className);
            } catch (Exception ignored) {
                //ignored
            }
        }
        throw new IOException("class not found " + className);
    }


    @Override
    public String toString() {
        String[] strs = new String[entryList.size()];
        for (int i = 0; i < entryList.size(); i++) {
            strs[i] = entryList.get(i).toString();
        }
        return String.join(File.pathSeparator, strs);
    }
    
}
```

>DirEntry.java

```java
package org.itstack.demo.jvm.classpath.impl;

import org.itstack.demo.jvm.classpath.Entry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/24
 * 目录形式的类路径
 */
public class DirEntry implements Entry {

    private Path absolutePath;

    public DirEntry(String path){
        //获取绝对路径
        this.absolutePath = Paths.get(path).toAbsolutePath();
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        return Files.readAllBytes(absolutePath.resolve(className));
    }

    @Override
    public String toString() {
        return this.absolutePath.toString();
    }
}
```

>WildcardEntry.java 

```java
package org.itstack.demo.jvm.classpath.impl;

import org.itstack.demo.jvm.classpath.Entry;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/24
 * 通配符类路径，继承CompositeEntry
 */
public class WildcardEntry extends CompositeEntry {

    public WildcardEntry(String path) {
        super(toPathList(path));
    }

    private static String toPathList(String wildcardPath) {
        String baseDir = wildcardPath.replace("*", ""); // remove *
        try {
            return Files.walk(Paths.get(baseDir))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(p -> p.endsWith(".jar") || p.endsWith(".JAR"))
                    .collect(Collectors.joining(File.pathSeparator));
        } catch (IOException e) {
            return "";
        }
    }

}
```

>ZipEntry.java

```java
package org.itstack.demo.jvm.classpath.impl;

import org.itstack.demo.jvm.classpath.Entry;

import java.io.IOException;
import java.nio.file.*;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/24
 * zip/zar文件形式类路径
 */
public class ZipEntry implements Entry {

    private Path absolutePath;

    public ZipEntry(String path) {
        //获取绝对路径
        this.absolutePath = Paths.get(path).toAbsolutePath();
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        try (FileSystem zipFs = FileSystems.newFileSystem(absolutePath, null)) {
            return Files.readAllBytes(zipFs.getPath(className));
        }
    }

    @Override
    public String toString() {
        return this.absolutePath.toString();
    }

}
```

>Classpath.java

```java
package org.itstack.demo.jvm.classpath;

import org.itstack.demo.jvm.classpath.impl.WildcardEntry;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/24
 * 类路径
 */
public class Classpath {

    private Entry bootstrapClasspath;  //启动类路径
    private Entry extensionClasspath;  //扩展类路径
    private Entry userClasspath;       //用户类路径

    public Classpath(String jreOption, String cpOption) {
        //启动类&扩展类 "C:\Program Files\Java\jdk1.8.0_161\jre"
        bootstrapAndExtensionClasspath(jreOption);
        //用户类 E:\..\org\itstack\demo\test\HelloWorld
        parseUserClasspath(cpOption);
    }

    private void bootstrapAndExtensionClasspath(String jreOption) {
        
        String jreDir = getJreDir(jreOption);

        //..jre/lib/*
        String jreLibPath = Paths.get(jreDir, "lib") + File.separator + "*";
        bootstrapClasspath = new WildcardEntry(jreLibPath);

        //..jre/lib/ext/*
        String jreExtPath = Paths.get(jreDir, "lib", "ext") + File.separator + "*";
        extensionClasspath = new WildcardEntry(jreExtPath);

    }

    private static String getJreDir(String jreOption) {
        if (jreOption != null && Files.exists(Paths.get(jreOption))) {
            return jreOption;
        }
        if (Files.exists(Paths.get("./jre"))) {
            return "./jre";
        }
        String jh = System.getenv("JAVA_HOME");
        if (jh != null) {
            return Paths.get(jh, "jre").toString();
        }
        throw new RuntimeException("Can not find JRE folder!");
    }

    private void parseUserClasspath(String cpOption) {
        if (cpOption == null) {
            cpOption = ".";
        }
        userClasspath = Entry.create(cpOption);
    }

    public byte[] readClass(String className) throws Exception {
        className = className + ".class";

        //[readClass]启动类路径
        try {
            return bootstrapClasspath.readClass(className);
        } catch (Exception ignored) {
            //ignored
        }

        //[readClass]扩展类路径
        try {
            return extensionClasspath.readClass(className);
        } catch (Exception ignored) {
            //ignored
        }

        //[readClass]用户类路径
        return userClasspath.readClass(className);
    }

}
```

>Entry.java

```java
package org.itstack.demo.jvm.classpath;

import org.itstack.demo.jvm.classpath.impl.CompositeEntry;
import org.itstack.demo.jvm.classpath.impl.DirEntry;
import org.itstack.demo.jvm.classpath.impl.WildcardEntry;
import org.itstack.demo.jvm.classpath.impl.ZipEntry;

import java.io.File;
import java.io.IOException;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/24
 * 类路径接口
 */
public interface Entry {

    byte[] readClass(String className) throws IOException;

    static Entry create(String path) {

        //File.pathSeparator；路径分隔符(win\linux)
        if (path.contains(File.pathSeparator)) {
            return new CompositeEntry(path);
        }

        if (path.endsWith("*")) {
            return new WildcardEntry(path);
        }

        if (path.endsWith(".jar") || path.endsWith(".JAR") ||
                path.endsWith(".zip") || path.endsWith(".ZIP")) {
            return new ZipEntry(path);
        }

        return new DirEntry(path);
    }

}
```

>Cmd.java

```java
package org.itstack.demo.jvm;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.List;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/24
 */
public class Cmd {

    @Parameter(names = {"-?", "-help"}, description = "print help message", order = 3, help = true)
    boolean helpFlag = false;

    @Parameter(names = "-version", description = "print version and exit", order = 2)
    boolean versionFlag = false;

    @Parameter(names = {"-cp", "-classpath"}, description = "classpath", order = 1)
    String classpath;

    @Parameter(names = "-Xjre", description = "path to jre", order = 4)
    String jre;

    @Parameter(description = "main class and args")
    List<String> mainClassAndArgs;

    boolean ok;

    String getMainClass() {
        return mainClassAndArgs != null && !mainClassAndArgs.isEmpty()
                ? mainClassAndArgs.get(0)
                : null;
    }

    List<String> getAppArgs() {
        return mainClassAndArgs != null && mainClassAndArgs.size() > 1
                ? mainClassAndArgs.subList(1, mainClassAndArgs.size())
                : null;
    }

    static Cmd parse(String[] argv) {
        Cmd args = new Cmd();
        JCommander cmd = JCommander.newBuilder().addObject(args).build();
        cmd.parse(argv);
        args.ok = true;
        return args;
    }
    
}
```

>Main.java

```java
package org.itstack.demo.jvm;

import org.itstack.demo.jvm.classpath.Classpath;

import java.util.Arrays;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/24
 * program arguments：-Xjre "C:\Program Files\Java\jdk1.8.0_161\jre" E:\itstack\git\istack-demo\itstack-demo-jvm\itstack-demo-jvm-02\target\test-classes\org\itstack\demo\test\HelloWorld
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
        Classpath cp = new Classpath(cmd.jre, cmd.classpath);
        System.out.printf("classpath：%s class：%s args：%s\n", cp, cmd.getMainClass(), cmd.getAppArgs());
        //获取className
        String className = cmd.getMainClass().replace(".", "/");
        try {
            byte[] classData = cp.readClass(className);
            System.out.println("classData：");
            for (byte b : classData) {
                //16进制输出
                System.out.print(String.format("%02x", b & 0xff) + " ");
            }
        } catch (Exception e) {
            System.out.println("Could not find or load main class " + cmd.getMainClass());
            e.printStackTrace();
        }
    }

}
```

## 测试结果
```java
classpath：org.itstack.demo.jvm.classpath.Classpath@4bf558aa class：E:\itstack\git\istack-demo\itstack-demo-jvm\itstack-demo-jvm-02\target\test-classes\org\itstack\demo\test\HelloWorld args：null
classData：
ca fe ba be 00 00 00 34 00 22 0a 00 06 00 14 09 
00 15 00 16 08 00 17 0a 00 18 00 19 07 00 1a 07 
00 1b 01 00 06 3c 69 6e 69 74 3e 01 00 03 28 29 
56 01 00 04 43 6f 64 65 01 00 0f 4c 69 6e 65 4e 
75 6d 62 65 72 54 61 62 6c 65 01 00 12 4c 6f 63 
61 6c 56 61 72 69 61 62 6c 65 54 61 62 6c 65 01 
00 04 74 68 69 73 01 00 22 4c 6f 72 67 2f 69 74 
73 74 61 63 6b 2f 64 65 6d 6f 2f 74 65 73 74 2f 
48 65 6c 6c 6f 57 6f 72 6c 64 3b 01 00 04 6d 61 
69 6e 01 00 16 28 5b 4c 6a 61 76 61 2f 6c 61 6e 
67 2f 53 74 72 69 6e 67 3b 29 56 01 00 04 61 72 
67 73 01 00 13 5b 4c 6a 61 76 61 2f 6c 61 6e 67 
2f 53 74 72 69 6e 67 3b 01 00 0a 53 6f 75 72 63 
65 46 69 6c 65 01 00 0f 48 65 6c 6c 6f 57 6f 72 
6c 64 2e 6a 61 76 61 0c 00 07 00 08 07 00 1c 0c 
00 1d 00 1e 01 00 0d 48 65 6c 6c 6f 2c 20 77 6f 
72 6c 64 21 07 00 1f 0c 00 20 00 21 01 00 20 6f 
72 67 2f 69 74 73 74 61 63 6b 2f 64 65 6d 6f 2f 
74 65 73 74 2f 48 65 6c 6c 6f 57 6f 72 6c 64 01 
00 10 6a 61 76 61 2f 6c 61 6e 67 2f 4f 62 6a 65 
63 74 01 00 10 6a 61 76 61 2f 6c 61 6e 67 2f 53 
79 73 74 65 6d 01 00 03 6f 75 74 01 00 15 4c 6a 
61 76 61 2f 69 6f 2f 50 72 69 6e 74 53 74 72 65 
61 6d 3b 01 00 13 6a 61 76 61 2f 69 6f 2f 50 72 
69 6e 74 53 74 72 65 61 6d 01 00 07 70 72 69 6e 
74 6c 6e 01 00 15 28 4c 6a 61 76 61 2f 6c 61 6e 
67 2f 53 74 72 69 6e 67 3b 29 56 00 21 00 05 00 
06 00 00 00 00 00 02 00 01 00 07 00 08 00 01 00 
09 00 00 00 2f 00 01 00 01 00 00 00 05 2a b7 00 
01 b1 00 00 00 02 00 0a 00 00 00 06 00 01 00 00 
00 03 00 0b 00 00 00 0c 00 01 00 00 00 05 00 0c 
00 0d 00 00 00 09 00 0e 00 0f 00 01 00 09 00 00 
00 37 00 02 00 01 00 00 00 09 b2 00 02 12 03 b6 
00 04 b1 00 00 00 02 00 0a 00 00 00 0a 00 02 00 
00 00 06 00 08 00 07 00 0b 00 00 00 0c 00 01 00 
00 00 09 00 10 00 11 00 00 00 01 00 12 00 00 00 
02 00 13 
```

**结果验证**
Notepad++ 打开HelloWorld.class，在'插件'工具中选HEX-Editor设置为View in Hex 默认为8-bit
![](https://fuzhengwei.github.io/assets/images/pic-content/2019/08/11.png)