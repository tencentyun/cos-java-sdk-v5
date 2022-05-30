package com.qcloud.cos.model.ciModel.xml;

import com.qcloud.cos.internal.XmlWriter;
import com.qcloud.cos.model.ciModel.image.ImageSearchRequest;
import com.qcloud.cos.model.ciModel.image.OpenImageSearchRequest;

/**
 * 数据万象图片处理xml格式化
 */
public class CImageXmlFactory {

    /**
     * 以图搜图开通请求转换
     */
    public static byte[] convertToXmlByteArray(OpenImageSearchRequest imageSearchRequest) {
        XmlWriter xml = new XmlWriter();
        xml.start("Request");
        CIMediaXmlFactory.addIfNotNull(xml, "MaxCapacity", imageSearchRequest.getMaxCapacity());
        CIMediaXmlFactory.addIfNotNull(xml, "MaxQps", imageSearchRequest.getMaxQps());
        xml.end();
        return xml.getBytes();
    }

    /**
     * 以图搜图添加图库请求转换
     */
    public static byte[] convertToXmlByteArray(ImageSearchRequest imageSearchRequest) {
        XmlWriter xml = new XmlWriter();
        xml.start("Request");
        CIMediaXmlFactory.addIfNotNull(xml, "EntityId", imageSearchRequest.getEntityId());
        CIMediaXmlFactory.addIfNotNull(xml, "CustomContent", imageSearchRequest.getCustomContent());
        CIMediaXmlFactory.addIfNotNull(xml, "Tags", imageSearchRequest.getTags());
        xml.end();
        return xml.getBytes();
    }
}
