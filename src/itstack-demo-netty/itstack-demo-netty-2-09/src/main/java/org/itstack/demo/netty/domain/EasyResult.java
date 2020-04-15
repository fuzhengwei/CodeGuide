package org.itstack.demo.netty.domain;

/**
 * 接口反馈信息类
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class EasyResult {

    private boolean success;
    private String title;
    private String msg;

    static public EasyResult buildSuccessResult(){
        EasyResult easyResult = new EasyResult();
        easyResult.setSuccess(true);
        easyResult.setMsg("ok");
        return easyResult;
    }

    static public EasyResult buildErrResult(Exception e){
        EasyResult easyResult = new EasyResult();
        easyResult.setSuccess(false);
        easyResult.setMsg(e.getMessage());
        return easyResult;
    }

    static public EasyResult buildErrResult(String msg){
        EasyResult easyResult = new EasyResult();
        easyResult.setSuccess(false);
        easyResult.setMsg(msg);
        return easyResult;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
