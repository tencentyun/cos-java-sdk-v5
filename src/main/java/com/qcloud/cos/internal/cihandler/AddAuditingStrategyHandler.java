package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.model.ciModel.auditing.AuditingStrategyResponse;
import com.qcloud.cos.model.ciModel.persistence.AIGameRecResponse;
import org.xml.sax.Attributes;


public class AddAuditingStrategyHandler extends XStreamHandler {
    public AuditingStrategyResponse response = new AuditingStrategyResponse();

    public AuditingStrategyResponse getResponse() {
        return response;
    }

    @Override
    AddAuditingStrategyHandler fromXML() {
        return null;
    }
}
