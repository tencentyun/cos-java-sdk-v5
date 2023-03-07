package com.qcloud.cos;

import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.BucketDomainConfiguration;
import com.qcloud.cos.model.DomainRule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.fail;


public class BucketDomainTest extends AbstractCOSClientTest{
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    @Test
    public void SetGetDelUnregisteredDomainTest() throws Exception {
        BucketDomainConfiguration bucketDomainConfiguration = new BucketDomainConfiguration();
        DomainRule domainRule = new DomainRule();
        domainRule.setStatus(DomainRule.ENABLED);
        domainRule.setType(DomainRule.REST);
        domainRule.setName("test.com");
        bucketDomainConfiguration.getDomainRules().add(domainRule);
        try {
            cosclient.setBucketDomainConfiguration(bucket, bucketDomainConfiguration);
        } catch (CosServiceException cse) {
            if (cse.getStatusCode() != 451) {
                fail(cse.toString());
            }
        }

        try {
            cosclient.getBucketDomainConfiguration(bucket);
        } catch (CosServiceException cse) {
            if (cse.getStatusCode() != 404) {
                fail(cse.toString());
            }
        }

        cosclient.deleteBucketDomainConfiguration(bucket);
        BucketDomainConfiguration bucketDomainConfiguration2 = cosclient.getBucketDomainConfiguration(bucket);
        assert(bucketDomainConfiguration2 == null);
    }
}
