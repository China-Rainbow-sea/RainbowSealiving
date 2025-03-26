package com.rainbowsea.common.exception;


/**
 * 编写方法，处理没有精确匹配到的异常/错误
 * 返回一个统一的信息，方便前端处理
 */
public enum  RainbowSealivingCodeEnume {

    UNKNOWN_EXCEPTION(40000,"系统未知异常"), // shift + ctrl + U 大小写转换
    INVALID_EXCEPTION(40001, " 参数校验异常~~");
    // 需要定义构造方法，不然会报错

    private int code;
    private String msg;

    RainbowSealivingCodeEnume(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
