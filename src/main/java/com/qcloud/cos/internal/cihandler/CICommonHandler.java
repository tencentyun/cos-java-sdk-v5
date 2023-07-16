package com.qcloud.cos.internal.cihandler;

import java.io.InputStream;

public class CICommonHandler<T> {

    public T getResponse(InputStream in, Class<T> tClass) {
        return XStreamXmlResponsesSaxParser.toBean(in, tClass);
    }

}
