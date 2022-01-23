package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.BucketPolicy;


public class BucketPolicyDemo {
    public static void SetGetBucketPolicy() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");

        ClientConfig clientConfig = new ClientConfig();

        // 2 设置 bucket 的域名, bucket 对应的 COS 地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        String region = "ap-guangzhou";
        // 如果是公网环境
        clientConfig.setEndpoint(String.format("cos.%s.tencentcos.cn", region));
        // 如果是腾讯云内网环境
        clientConfig.setEndpoint(String.format("cos-internal.%s.tencentcos.cn", region));

        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String bucketName = "examplebucket-1250000000";
        String bucketPolicyStr = "{" +
                "  \"Statement\": [" +
                "    {" +
                "      \"Principal\": {" +
                "        \"qcs\": [" +
                "          \"qcs::cam::uin/100000000001:uin/100000000011\"" +
                "        ]" +
                "      }," +
                "      \"Effect\": \"deny\"," +
                "      \"Action\": [" +
                "        \"name/cos:GetObject\"" +
                "      ]," +
                "      \"Resource\": [" +
                "        \"qcs::cos:ap-guangzhou:uid/1250000000:examplebucket-1250000000/test.txt\"" +
                "      ]" +
                "    }" +
                "  ]," +
                "  \"version\": \"2.0\"" +
                "}";
        cosclient.setBucketPolicy(bucketName, bucketPolicyStr);
        BucketPolicy bucketPolicy = cosclient.getBucketPolicy(bucketName);
        System.out.println(bucketPolicy.getPolicyText());
    }
    public static void main(String[] args) {
        SetGetBucketPolicy();
    }
}

