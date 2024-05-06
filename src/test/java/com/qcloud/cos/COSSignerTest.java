package com.qcloud.cos;

import com.qcloud.cos.auth.AnonymousCOSCredentials;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.CosHttpRequest;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.ListObjectsRequest;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;
public class COSSignerTest {
    @Test
    public void testBuildPostObjectSignature() {
        String bucketName = "mybucket-1251668577";
        String key = "images/test.jpg";
        String secretId = "AKIDXXXXXXXX";
        String secretKey = "1A2Z3YYYYYYYYYY";
        long startTimestamp = System.currentTimeMillis() / 1000;
        long endTimestamp = startTimestamp +  30 * 60;
        String endTimestampStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").
                format(endTimestamp * 1000);
        String keyTime = startTimestamp + ";" + endTimestamp;

        // 设置表单的body字段值
        Map<String, String> formFields = new HashMap<>();
        formFields.put("q-sign-algorithm", "sha1");
        formFields.put("key", key);
        formFields.put("q-ak", secretId);
        formFields.put("q-key-time", keyTime);
        // 构造policy，参考文档: https://cloud.tencent.com/document/product/436/14690
        String policy = "{\n" +
                "    \"expiration\": \"" + endTimestampStr + "\",\n" +
                "    \"conditions\": [\n" +
                "        { \"bucket\": \"" + bucketName + "\" },\n" +
                "        { \"q-sign-algorithm\": \"sha1\" },\n" +
                "        { \"q-ak\": \"" + secretId + "\" },\n" +
                "        { \"q-sign-time\":\"" + keyTime + "\" }\n" +
                "    ]\n" +
                "}";
        // policy需要base64后算放入表单中
        String encodedPolicy = new String(Base64.encodeBase64(policy.getBytes()));
        // 设置policy
        formFields.put("policy", encodedPolicy);
        // 根据编码后的policy和secretKey计算签名
        COSSigner cosSigner = new COSSigner();
        String signature = cosSigner.buildPostObjectSignature(secretKey,
                keyTime, policy);
    }

    @Test
    public void testBuildAuthorizationStr() {
        String secretId = "AKIDXXXXXXXX";
        String secretKey = "1A2Z3YYYYYYYYYY";
        String bucketName = "mybucket-1251668577";

        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Date expirationDate = new Date(System.currentTimeMillis() + 30L * 60 * 1000);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("host", "mybucket-1251668577.cos.ap-guangzhou.myqcloud.com");
        COSSigner cosSigner = new COSSigner();

        String signature = cosSigner.buildAuthorizationStr(HttpMethodName.PUT, "testPath", headers, new HashMap<>(), cred, expirationDate);

        cred = new AnonymousCOSCredentials();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(bucketName);
        CosHttpRequest request = new CosHttpRequest<>(listObjectsRequest).withParameter("max-keys", "3");
        request.setHeaders(headers);

        Map<String, String> params = new HashMap<String, String>();
        params.put("max-keys", "4");
        request.setParameters(params);

        cosSigner.sign(request, cred, expirationDate);
    }

    @Test
    public void testBuildAuthorizationStrWithoutHost() {
        String secretId = "AKIDXXXXXXXX";
        String secretKey = "1A2Z3YYYYYYYYYY";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Date expirationDate = new Date(System.currentTimeMillis() + 30L * 60 * 1000);

        Set<String> needSignedHeaderSet = new HashSet<>();
        needSignedHeaderSet.add("cache-control");
        needSignedHeaderSet.add("content-disposition");
        needSignedHeaderSet.add("content-encoding");
        needSignedHeaderSet.add("content-length");
        needSignedHeaderSet.add("content-md5");
        needSignedHeaderSet.add("content-type");
        needSignedHeaderSet.add("expect");
        needSignedHeaderSet.add("expires");
        needSignedHeaderSet.add("host");
        needSignedHeaderSet.add("if-match");
        needSignedHeaderSet.add("if-modified-since");
        needSignedHeaderSet.add("if-none-match");
        needSignedHeaderSet.add("if-unmodified-since");
        needSignedHeaderSet.add("origin");
        needSignedHeaderSet.add("range");
        needSignedHeaderSet.add("transfer-encoding");

        COSSigner.setNeedSignedHeaderSet(needSignedHeaderSet);
        needSignedHeaderSet = COSSigner.getNeedSignedHeaderSet();
        // 根据编码后的policy和secretKey计算签名
        COSSigner cosSigner = new COSSigner();
        cosSigner.setLocalTimeDelta(0);
        int localTimeDelta = cosSigner.getLocalTimeDelta();
        assertEquals(localTimeDelta, 0);
        try {
            String signature = cosSigner.buildAuthorizationStr(HttpMethodName.PUT, "testPath", cred, expirationDate);
        } catch (CosClientException cce) {
            if (!cce.getMessage().startsWith("buildAuthorization missing header: host")) {
                fail(cce.getMessage());
            }
        }
    }
}
