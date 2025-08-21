package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.internal.CosServiceRequest;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

public abstract class XStreamXmlResponsesSaxParser<T> {
    private static final ConcurrentHashMap<String, XStream> xStreamMap = new ConcurrentHashMap<String, XStream>();

    abstract T getResponse(InputStream in);

    static XStream initXStream(Object obj) {
        return initXStream(obj.getClass());
    }

    static <T> XStream initXStream(Class<T> cls) {
        if (!xStreamMap.containsKey(cls.getName())) {
            XStream xstream = new XStream();
            //忽略不需要的节点
            xstream.ignoreUnknownElements();
            //对指定的类使用Annotations 进行序列化
            xstream.processAnnotations(cls);
            xstream.addPermission(AnyTypePermission.ANY);
            //添加 Map 类型converter
            xstream.registerConverter(new MediaWorkflowMapConverter());

            Field[] fields = CosServiceRequest.class.getDeclaredFields();
            for (Field field : fields) {
                xstream.omitField(CosServiceRequest.class, field.getName());
            }
            xStreamMap.put(cls.getName(), xstream);
            return xstream;
        }
        return xStreamMap.get(cls.getName());
    }

    public static String toXML(Object obj) {
        XStream xstream = initXStream(obj);
        return xstream.toXML(obj);
    }

    public static <T> T toBean(InputStream inputStream, Class<T> cls) {
        try (InputStream in = inputStream) {
            XStream xstream = initXStream(cls);
            return (T) xstream.fromXML(in);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CosServiceException("Response parse error");
        }
    }
}
