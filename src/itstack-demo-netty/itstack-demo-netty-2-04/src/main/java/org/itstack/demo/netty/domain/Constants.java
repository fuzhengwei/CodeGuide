package org.itstack.demo.netty.domain;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class Constants {

    //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
    public static class FileStatus{
        public static int BEGIN = 0;    //开始
        public static int CENTER = 1;   //中间
        public static int END = 2;      //结尾
        public static int COMPLETE = 3; //完成
    }

    //0传输文件'请求'、1文件传输'指令'、2文件传输'数据'
    public static class TransferType{
        public static int REQUEST = 0;    //0请求传输文件
        public static int INSTRUCT = 1;   //1文件传输指令
        public static int DATA = 2;       //2文件传输数据
    }

}
