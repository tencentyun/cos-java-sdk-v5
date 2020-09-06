/*
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 
 * According to cos feature, we modify some class，comment, field name, etc.
 */


package com.qcloud.cos.auth;

import static com.qcloud.cos.auth.COSSignerConstants.LINE_SEPARATOR;
import static com.qcloud.cos.auth.COSSignerConstants.Q_AK;
import static com.qcloud.cos.auth.COSSignerConstants.Q_HEADER_LIST;
import static com.qcloud.cos.auth.COSSignerConstants.Q_KEY_TIME;
import static com.qcloud.cos.auth.COSSignerConstants.Q_SIGNATURE;
import static com.qcloud.cos.auth.COSSignerConstants.Q_SIGN_ALGORITHM_KEY;
import static com.qcloud.cos.auth.COSSignerConstants.Q_SIGN_ALGORITHM_VALUE;
import static com.qcloud.cos.auth.COSSignerConstants.Q_SIGN_TIME;
import static com.qcloud.cos.auth.COSSignerConstants.Q_URL_PARAM_LIST;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;

import com.qcloud.cos.Headers;
import com.qcloud.cos.http.CosHttpRequest;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.utils.UrlEncoderUtils;

public class COSSigner {
    private static Set<String> needSignedHeaderSet = new HashSet<>();
    static {
        needSignedHeaderSet.add("host");
        needSignedHeaderSet.add("content-type");
        needSignedHeaderSet.add("content-md5");
        needSignedHeaderSet.add("content-disposition");
        needSignedHeaderSet.add("content-encoding");
        needSignedHeaderSet.add("content-length");
        needSignedHeaderSet.add("transfer-encoding");
        needSignedHeaderSet.add("range");
    }
    private boolean isAnonymous(COSCredentials cred) {
        return cred instanceof AnonymousCOSCredentials;
    }

    public <X extends CosServiceRequest> void sign(CosHttpRequest<X> request, COSCredentials cred, Date expiredTime) {
        if (isAnonymous(cred)) {
            return;
        }

        String authoriationStr =
                buildAuthorizationStr(request.getHttpMethod(), request.getResourcePath(),
                        request.getHeaders(), request.getParameters(), cred, expiredTime);

        request.addHeader(Headers.COS_AUTHORIZATION, authoriationStr);
        if (cred instanceof COSSessionCredentials) {
            request.addHeader(Headers.SECURITY_TOKEN,
                    ((COSSessionCredentials) cred).getSessionToken());
        }
    }

    public String buildAuthorizationStr(HttpMethodName methodName, String resouce_path,
            COSCredentials cred, Date expiredTime) {

        return buildAuthorizationStr(methodName, resouce_path, new HashMap<String, String>(),
                new HashMap<String, String>(), cred, expiredTime);
    }

    public String buildPostObjectSignature(String secretKey, String keyTime, String policy) {
        String signKey = HmacUtils.hmacSha1Hex(secretKey, keyTime);
        String stringToSign = DigestUtils.sha1Hex(policy);
        return HmacUtils.hmacSha1Hex(signKey, stringToSign);
    }

    public String buildAuthorizationStr(HttpMethodName methodName, String resouce_path,
            Map<String, String> headerMap, Map<String, String> paramMap, COSCredentials cred,
            Date expiredTime) {

        if (isAnonymous(cred)) {
            return null;
        }

        Map<String, String> signHeaders = buildSignHeaders(headerMap);
        // 签名中的参数和http 头部 都要进行字符串排序
        TreeMap<String, String> sortedSignHeaders = new TreeMap<>();
        TreeMap<String, String> sortedParams = new TreeMap<>();

        sortedSignHeaders.putAll(signHeaders);
        sortedParams.putAll(paramMap);

        String qHeaderListStr = buildSignMemberStr(sortedSignHeaders);
        String qUrlParamListStr = buildSignMemberStr(sortedParams);
        String qKeyTimeStr, qSignTimeStr;
        qKeyTimeStr = qSignTimeStr = buildTimeStr(expiredTime);
        String signKey = HmacUtils.hmacSha1Hex(cred.getCOSSecretKey(), qKeyTimeStr);
        String formatMethod = methodName.toString().toLowerCase();
        String formatUri = resouce_path;
        String formatParameters = formatMapToStr(sortedParams);
        String formatHeaders = formatMapToStr(sortedSignHeaders);

        String formatStr = new StringBuilder().append(formatMethod).append(LINE_SEPARATOR)
                .append(formatUri).append(LINE_SEPARATOR).append(formatParameters)
                .append(LINE_SEPARATOR).append(formatHeaders).append(LINE_SEPARATOR).toString();
        String hashFormatStr = DigestUtils.sha1Hex(formatStr);
        String stringToSign = new StringBuilder().append(Q_SIGN_ALGORITHM_VALUE)
                .append(LINE_SEPARATOR).append(qSignTimeStr).append(LINE_SEPARATOR)
                .append(hashFormatStr).append(LINE_SEPARATOR).toString();
        String signature = HmacUtils.hmacSha1Hex(signKey, stringToSign);

        String authoriationStr = new StringBuilder().append(Q_SIGN_ALGORITHM_KEY).append("=")
                .append(Q_SIGN_ALGORITHM_VALUE).append("&").append(Q_AK).append("=")
                .append(cred.getCOSAccessKeyId()).append("&").append(Q_SIGN_TIME).append("=")
                .append(qSignTimeStr).append("&").append(Q_KEY_TIME).append("=").append(qKeyTimeStr)
                .append("&").append(Q_HEADER_LIST).append("=").append(qHeaderListStr).append("&")
                .append(Q_URL_PARAM_LIST).append("=").append(qUrlParamListStr).append("&")
                .append(Q_SIGNATURE).append("=").append(signature).toString();
        return authoriationStr;
    }

    public boolean needSignedHeader(String header) {
        return needSignedHeaderSet.contains(header) || header.startsWith("x-cos-");
    }

    private Map<String, String> buildSignHeaders(Map<String, String> originHeaders) {
        Map<String, String> signHeaders = new HashMap<>();
        for (Entry<String, String> headerEntry : originHeaders.entrySet()) {
            String key = headerEntry.getKey().toLowerCase();
            if(needSignedHeader(key)) {
                String value = headerEntry.getValue();
                signHeaders.put(key, value);
            }
        }
        return signHeaders;
    }

    private String buildSignMemberStr(Map<String, String> signHeaders) {
        StringBuilder strBuilder = new StringBuilder();
        boolean seenOne = false;
        for (String key : signHeaders.keySet()) {
            if (!seenOne) {
                seenOne = true;
            } else {
                strBuilder.append(";");
            }
            strBuilder.append(key.toLowerCase());
        }
        return strBuilder.toString();
    }

    private String formatMapToStr(Map<String, String> kVMap) {
        StringBuilder strBuilder = new StringBuilder();
        boolean seeOne = false;
        for (Entry<String, String> entry : kVMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String lowerKey = key.toLowerCase();
            String encodeKey = UrlEncoderUtils.encode(lowerKey);
            String encodedValue = "";
            if (value != null) {
                encodedValue = UrlEncoderUtils.encode(value);
            }
            if (!seeOne) {
                seeOne = true;
            } else {
                strBuilder.append("&");
            }
            strBuilder.append(encodeKey).append("=").append(encodedValue);
        }
        return strBuilder.toString();
    }

    private String buildTimeStr(Date expiredTime) {
        StringBuilder strBuilder = new StringBuilder();
        long startTime = System.currentTimeMillis() / 1000;
        long endTime = expiredTime.getTime() / 1000;
        strBuilder.append(startTime).append(";").append(endTime);
        return strBuilder.toString();
    }

    public static Set<String> getNeedSignedHeaderSet() {
        return needSignedHeaderSet;
    }

    public static void setNeedSignedHeaderSet(Set<String> needSignedHeaderSet) {
        COSSigner.needSignedHeaderSet = needSignedHeaderSet;
    }

}
