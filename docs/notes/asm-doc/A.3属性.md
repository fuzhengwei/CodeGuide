2.1.1 节曾经解释过，有可能将任意属性关联到类、字段和方法。在引入新特性时，这种可扩展机制对于扩展类文件格式非常有用。例如，它已经被用于扩展这一格式，以支持注释、泛型、栈映射帧等。这一机制还可由用户使用，而不只是由 Sun 公司使用，但自从在 Java 5 中引入注释之后，注释的使用就比属性容易得多。也就是说，如果你真的需要使用自己的属性，或者必须管理由别人定义的非标准属性，可以在 ASM 用 Attribute 类完成。

默认情况下，ClassReader 类会为它找到的每个标准属性创建一个 Attribute 实例，并以这个实例为参数，调用 visitAttribute 方法（至于是 ClassVisitor、FieldVisitor， 还是 MethodVisitor 类的该方法，则取决于上下文）。这个实例中包含了属性的原始内容，其形式为私有字节数组。在访问这种未知属性时，ClassWriter 类就是将这个原始字节数组复制到它构建的类中。这一默认行为只有在使用 2.2.4 节介绍的优化时才是安全的（除了提高性能外，这是使用该优化的另一原因）。没有这一选项，原内容可能会与类编写器创建的新常量池不一致， 从而导致类文件被损坏。

默认情况下，非标准属性会以它在已转换类中的形式被复制，它的内容对 ASM 和用户来说是完全不透明的。如果需要访问这一内容，必须首先定义一个 Attribute 子类，能够对原内容进行解码并重新编码。还必须在 ClassReader.accept 方法中传送这个类的一个原型实例，使这个类可以解码这一类型的属性。让我们用一个例子来说明这一点。下面的类可用于运行一个设想的“注释”特性，它的原始内容是一个 short 值，引用存储在常量池中的一个 UTF8 字符串：

```java 
class CommentAttribute extends Attribute {
    private String comment;

    public CommentAttribute(final String comment) {
        super("Comment");
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public boolean isUnknown() {

        return false;
    }

    @Override
    protected Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels) {
        return new CommentAttribute(cr.readUTF8(off, buf));
    }

    @Override
    protected ByteVector write(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals) {
        return new ByteVector().putShort(cw.newUTF8(comment));
    }
}
```                                                                                                                                                                                             

最重要的方法是 read 和 write 方法。read 方法对这一类型的属性的原始内容进行解码， write 方法执行逆操作。注意，read 方法必须返回一个新的属性实例。为了在读取一个类时实现这种属性的解码，必须使用：

```java 
ClassReader cr = ...; ClassVisitor cv = ...;
cr.accept(cv, new Attribute[] { new CommentAttribute("") }, 0);
```

这个“注释”属性将被识别，并为它们中的每一个都创建一个 CommentAttribute 实例（而未知属性仍将用 Attribute 实例表示）。


