package com.qcloud.cos.exception;

public class ExceptionLogDetail {
    public ExceptionLogDetail(Throwable t, String msg) {
        this.t = t;
        this.errMsg = msg;
    }

    public Throwable getException() {
        return t;
    }

    public String getErrMsg() {
        return errMsg;
    }

    private Throwable t;

    private String errMsg;
}
