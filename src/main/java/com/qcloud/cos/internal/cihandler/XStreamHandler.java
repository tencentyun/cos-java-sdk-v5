package com.qcloud.cos.internal.cihandler;

import com.thoughtworks.xstream.XStream;

import java.io.InputStream;

public abstract class XStreamHandler<T> {

    private XStream xstream;

    abstract T fromXML();

    private void initXStream(boolean omitField) {
        if (xstream == null) {
            xstream = new XStream();
        }
    }

    private String toXML(Object obj) {
        return xstream.toXML(obj);
    }

    private T parseBean(Class<T> c, InputStream inputStream) {
        return (T) xstream.fromXML(inputStream);
    }

    public static <T> T toBean(InputStream inputStream, Class<T> cls) {
        if (inputStream == null) {
            return null;
        }
        try {
            XStream xstream = new XStream();
            //忽略不需要的节点
            xstream.ignoreUnknownElements();
            //对指定的类使用Annotations 进行序列化
            xstream.processAnnotations(cls);
            return (T) xstream.fromXML(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
