package com.qcloud.cos.exception;

public class ExceptionLogDetail {
    public ExceptionLogDetail(Throwable t, String exception_level, long time_stamp, int execute_index) {
        this.t = t;
        this.exception_level = exception_level;
        this.time_stamp = time_stamp;
        this.execute_index = execute_index;
    }

    public long getTimeStamp() {
        return time_stamp;
    }

    public String getExceptionLevel() {
        return exception_level;
    }

    public Throwable getException() {
        return t;
    }

    public int getExecuteIndex() {
        return execute_index;
    }

    private long time_stamp;

    private Throwable t;

    private String exception_level;

    private int execute_index;
}
