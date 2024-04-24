package com.qcloud.cos.exception;

public class ExceptionLogDetail {
    public ExceptionLogDetail(Throwable t, String msg) {
        this.t = t;
        this.err_msg = msg;
    }

    public Throwable getException() {
        return t;
    }

    public String getErrMsg() {
        return err_msg;
    }

    private Throwable t;

    private String err_msg;
}
