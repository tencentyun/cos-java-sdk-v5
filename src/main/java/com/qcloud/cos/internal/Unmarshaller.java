package com.qcloud.cos.internal;

public interface Unmarshaller<T, R> {

    public T unmarshall(R in) throws Exception;
    
}
