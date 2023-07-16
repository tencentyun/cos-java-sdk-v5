package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.model.ciModel.auditing.AuditingTextLibResponse;

import java.io.InputStream;

public class AuditingTextLibHandler extends XStreamXmlResponsesSaxParser<AuditingTextLibResponse> {

    @Override
    public AuditingTextLibResponse getResponse(InputStream in) {
        return toBean(in, AuditingTextLibResponse.class);
    }

    
}
