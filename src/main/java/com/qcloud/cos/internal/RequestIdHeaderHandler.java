package com.qcloud.cos.internal;

import java.util.Map;

import com.qcloud.cos.Headers;
import com.qcloud.cos.http.CosHttpResponse;

/**
 * 从响应头 x-cos-request-id 读取 requestId 写入解析结果，
 * 用于 HTTP 200 + Error 响应体场景下给 CosServiceException 回填 requestId。
 *
 * @since 5.6.283
 */
public class RequestIdHeaderHandler<T extends AgentBucketErrorAware> implements HeaderHandler<T> {

    @Override
    public void handle(T result, CosHttpResponse response) {
        if (result == null || response == null) {
            return;
        }
        Map<String, String> headers = response.getHeaders();
        if (headers != null) {
            result.setHeaderRequestId(headers.get(Headers.REQUEST_ID));
        }
    }
}
