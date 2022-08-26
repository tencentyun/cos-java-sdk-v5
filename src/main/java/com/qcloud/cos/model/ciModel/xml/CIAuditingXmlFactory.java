package com.qcloud.cos.model.ciModel.xml;

import com.qcloud.cos.internal.XmlWriter;
import com.qcloud.cos.model.ciModel.auditing.ReportBadCaseRequest;

/**
 * 数据万象内容审核xml格式化
 */
public class CIAuditingXmlFactory {

    /**
     * 文本审核结果反馈
     */
    public static byte[] convertToXmlByteArray(ReportBadCaseRequest request) {
        XmlWriter xml = new XmlWriter();
        xml.start("Request");
        CIMediaXmlFactory.addIfNotNull(xml, "ContentType", request.getContentType());
        CIMediaXmlFactory.addIfNotNull(xml, "Text", request.getText());
        CIMediaXmlFactory.addIfNotNull(xml, "Label", request.getLabel());
        CIMediaXmlFactory.addIfNotNull(xml, "SuggestedLabel", request.getSuggestedLabel());
        CIMediaXmlFactory.addIfNotNull(xml, "JobId", request.getJobId());
        CIMediaXmlFactory.addIfNotNull(xml, "ModerationTime", request.getModerationTime());
        xml.end();
        return xml.getBytes();
    }

}
