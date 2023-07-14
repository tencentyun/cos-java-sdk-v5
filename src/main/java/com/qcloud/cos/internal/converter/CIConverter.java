package com.qcloud.cos.internal.converter;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

// 自定义转换器来处理null值字段
public class CIConverter implements Converter {

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        // 在此处检查字段是否为null，根据需要决定是否写入到XML中
        if (source instanceof CIServiceRequest) {

        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        return null;
    }

    @Override
    public boolean canConvert(Class type) {
        // 此处返回true，以便将该转换器应用于所有类型的对象
        return true;
    }
}