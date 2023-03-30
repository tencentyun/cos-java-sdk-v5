package com.qcloud.cos;

import com.qcloud.cos.exception.CosServiceException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qcloud.cos.model.BucketPolicy;

import static org.junit.Assert.*;

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
            try {
                BucketPolicy result = cosclient.getBucketPolicy(bucket);
            } catch (CosServiceException cse) {
                assertEquals(404, cse.getStatusCode());
            }

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

            Thread.sleep(5000);

            BucketPolicy bucketPolicy = new BucketPolicy();
            try {
                bucketPolicy = cosclient.getBucketPolicy(bucket);
            } catch (CosServiceException cse) {
                if (cse.getStatusCode() == 404) {
                    Thread.sleep(5000);
                    bucketPolicy = cosclient.getBucketPolicy(bucket);
                }
            }
            assertNotNull(bucketPolicy.getPolicyText());
            assertFalse(bucketPolicy.getPolicyText().isEmpty());

            cosclient.deleteBucketPolicy(bucket);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
