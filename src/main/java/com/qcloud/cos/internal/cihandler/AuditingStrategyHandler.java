package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.model.ciModel.auditing.AuditingStrategyListResponse;
import com.qcloud.cos.model.ciModel.auditing.AuditingStrategyResponse;

import java.io.InputStream;

public class AuditingStrategyHandler extends XStreamXmlResponsesSaxParser<AuditingStrategyResponse> {

    @Override
    public AuditingStrategyResponse getResponse(InputStream in) {
        return toBean(in, AuditingStrategyResponse.class);
    }

    public AuditingStrategyListResponse getResponseList(InputStream in) {
        return toBean(in, AuditingStrategyListResponse.class);
    }
}
