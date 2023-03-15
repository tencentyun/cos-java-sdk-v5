package com.qcloud.cos;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qcloud.cos.model.BucketPolicy;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

public class GetSetDelPolicyTest extends AbstractCOSClientTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    @Test
    public void setGetDelPolicyTest() {
        try {
//            String policyText = String.format(
//                    "{\"Statement\": [  {   \"Action\": [    \"name/cos:*\"   ],   \"Condition\": {    \"ip_equal\": {     \"qcs:ip\": [      \"10.1.1.0/24\"     ]    },    \"string_equal\": {     \"qcs:sourceVpc\": [      \"vpc-123456\"     ]    }   },   \"Effect\": \"deny\",   \"Principal\": {    \"qcs\": [     \"qcs::cam::anyone:anyone\"    ]   },   \"Resource\": [    \"qcs::cos:%s:uid/%s:%s/*\"   ]  } ], \"version\": \"2.0\"}",
//                    region, appid, bucket);
            String bucketPolicyStr = String.format(
                    "{" +
                            "    \"Statement\": [" +
                            "      {" +
                            "        \"Principal\": {" +
                            "          \"qcs\": [" +
                            "            \"qcs::cam::anyone:anyone\"" + //替换成您想授予权限的账户 uin
                            "          ]" +
                            "        }," +
                            "        \"Effect\": \"allow\"," +
                            "        \"Action\": [" +
                            "          \"cos:PutObject\"" +
                            "        ]," +
                            "        \"Resource\": [" + //这里改成允许的路径前缀，可以根据自己网站的用户登录态判断允许上传的具体路径，例子： a.jpg 或者 a/* 或者 * (使用通配符*存在重大安全风险, 请谨慎评估使用)
                            "          \"qcs::cos:%s:uid/%s:%s/*\"" +
                            "        ]," +
                            "        \"Condition\": {" +
                            "          \"string_equal\": {" +
                            "            \"cos:x-cos-mime-limit\": \"image/jpeg\"" +
                            "          }" +
                            "        }" +
                            "      }" +
                            "    ]," +
                            "    \"Version\": \"2.0\"" +
                            "  }", region, appid, bucket
            );
            cosclient.setBucketPolicy(bucket, bucketPolicyStr);

            BucketPolicy bucketPolicy = cosclient.getBucketPolicy(bucket);
            assertNotNull(bucketPolicy.getPolicyText());
            assertFalse(bucketPolicy.getPolicyText().isEmpty());

            cosclient.deleteBucketPolicy(bucket);
        } catch (Exception e) {
            fail(e.toString());
        }
    }
}
