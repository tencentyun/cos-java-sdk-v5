package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.model.BucketPolicy;


public class BucketPolicyDemo {
    public static void main(String[] args) {
        setGetDelBucketPolicy();
    }

    private static void setGetDelBucketPolicy() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("SECRET_ID", "SECRET_KEY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
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
        cosClient.setBucketPolicy(bucketName, bucketPolicyStr);
        BucketPolicy bucketPolicy = cosClient.getBucketPolicy(bucketName);
        System.out.println(bucketPolicy.getPolicyText());

        cosClient.deleteBucketPolicy(bucketName);
        System.out.println("bucket policy has been deleted");
    }
}

