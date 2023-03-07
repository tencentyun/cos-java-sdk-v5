package com.qcloud.cos;

import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.BucketRefererConfiguration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.fail;

public class BucketRefererTest extends AbstractCOSClientTest{
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    @Test
    public void SetGetBucketRefererTest() throws Exception {
        BucketRefererConfiguration configuration = new BucketRefererConfiguration();

        // 启用防盗链
        configuration.setStatus(BucketRefererConfiguration.DISABLED);
        // 设置防盗链类型为白名单
        //configuration.setRefererType(BucketRefererConfiguration.WHITELIST);
        // 设置防盗链类型为黑名单 (与白名单二选一)
        configuration.setRefererType(BucketRefererConfiguration.BLACKLIST);

        // 填写要设置的域名
        configuration.addDomain("test.com");
        configuration.addDomain("test.1.com");

        // （可选）设置是否允许空防盗链访问，缺省就是 DENY
        configuration.setEmptyReferConfiguration(BucketRefererConfiguration.DENY);
        // configuration.setEmptyReferConfiguration(BucketRefererConfiguration.ALLOW);

        try {
            cosclient.setBucketRefererConfiguration(bucket, configuration);
            BucketRefererConfiguration configurationFromCos = cosclient.getBucketRefererConfiguration(bucket);
        } catch (CosServiceException cse) {
            fail(cse.toString());
        }
    }
}
