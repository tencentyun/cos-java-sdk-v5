package com.qcloud.cos.model.ciModel.xml;

import com.qcloud.cos.internal.XmlWriter;
import com.qcloud.cos.model.ciModel.image.ImageSearchRequest;

/**
 * 数据万象图片处理xml格式化
 */
public class CImageXmlFactory {

    /**
     * 以图搜图请求转换
     */
    public static byte[] convertToXmlByteArray(ImageSearchRequest imageSearchRequest) {
        XmlWriter xml = new XmlWriter();
        xml.start("Request");
        CIMediaXmlFactory.addIfNotNull(xml, "MaxCapacity", imageSearchRequest.getMaxCapacity());
        CIMediaXmlFactory.addIfNotNull(xml, "MaxQps", imageSearchRequest.getMaxQps());
        xml.end();
        return xml.getBytes();
    }
}
