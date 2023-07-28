package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.internal.CosServiceRequest;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.InputStream;
import java.lang.reflect.Field;

public abstract class XStreamXmlResponsesSaxParser<T> {

    private XStream xstream;

    abstract T getResponse(InputStream in);

    static XStream initXStream(Object obj) {
        return initXStream(obj.getClass());
    }

    static <T> XStream initXStream(Class<T> cls) {
        XStream xstream = new XStream();
        //忽略不需要的节点
        xstream.ignoreUnknownElements();
        //对指定的类使用Annotations 进行序列化
        xstream.processAnnotations(cls);
        xstream.addPermission(AnyTypePermission.ANY);
        Field[] fields = CosServiceRequest.class.getDeclaredFields();
        for (Field field : fields) {
            xstream.omitField(CosServiceRequest.class, field.getName());
        }
        return xstream;
    }

    public static String toXML(Object obj) {
        XStream xstream = initXStream(obj);
        return xstream.toXML(obj);
    }



    public static <T> T toBean(InputStream inputStream, Class<T> cls) {
        try {
            XStream xstream = initXStream(cls);
            return (T) xstream.fromXML(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CosServiceException("Response parse error");
        }
    }

}
