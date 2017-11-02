package com.qcloud.cos;

import java.util.List;

import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qcloud.cos.model.Bucket;

public class GetServiceTest extends AbstractCOSClientTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    @Test
    public void testGetService() {
        List<Bucket> buckets = cosclient.listBuckets();
        for (Bucket bucketElement : buckets) {
            if (bucketElement.getName().equals(bucket + "-" + appid)) {
                return;
            }
        }
        fail("GetService result not contain bucket: " + bucket);
    }
}
