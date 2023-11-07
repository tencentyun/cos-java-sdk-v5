package com.qcloud.cos.model.ciModel.xml;


import com.qcloud.cos.internal.cihandler.XStreamXmlResponsesSaxParser;

/**
 * 数据万象内容审核xml格式化
 */
public class CIAuditingXmlFactoryV2 extends CIAuditingXmlFactory {

    public static byte[] convertToXmlByteArray(Object obj) {
        String s = XStreamXmlResponsesSaxParser.toXML(obj);
        return s.getBytes();
    }

}
