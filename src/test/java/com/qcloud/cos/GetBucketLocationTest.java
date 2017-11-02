package com.qcloud.cos;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class GetBucketLocationTest extends AbstractCOSClientTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }
    
    @Test
    public void getbucketLocationTest() {
        String location = cosclient.getBucketLocation(bucket);
        assertEquals(clientConfig.getRegion().getRegionName(), location);
    }
}
