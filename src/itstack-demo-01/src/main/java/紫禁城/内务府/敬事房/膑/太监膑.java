package 紫禁城.内务府.敬事房.膑;

public class 太监膑 {

    private String 姓名;
    private String 年龄;
    private String 性别;
    private String 敬事日期 = "";

    public String get姓名() {
        return 姓名;
    }

    public void set姓名(String 姓名) {
        this.姓名 = 姓名;
    }

    public String get年龄() {
        return 年龄;
    }

    public void set年龄(String 年龄) {
        this.年龄 = 年龄;
    }

    public String get性别() {
        return 性别;
    }

    public void set性别(String 性别) {
        this.性别 = 性别;
    }

    public String get敬事日期() {
        return 敬事日期;
    }

    public void set敬事日期(String 敬事日期) {
        this.敬事日期 = 敬事日期;
    }

    @Override
    public String toString() {
        return "太监膑 [姓名=" + 姓名 + ", 年龄=" + 年龄 + ", 性别=" + 性别 + ", 敬事日期=" + 敬事日期
                + "]";
    }

}
