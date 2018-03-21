package com.qcloud.cos;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;

import com.qcloud.cos.model.RestoreObjectRequest;

public class RestoreObjectTest extends AbstractCOSClientTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }
    
    @Ignore
    public void restoreObject() {
        RestoreObjectRequest restoreObjectRequest = new RestoreObjectRequest(bucket, "ut/aaa.txt", 1);
        cosclient.restoreObject(restoreObjectRequest);
    }
}
