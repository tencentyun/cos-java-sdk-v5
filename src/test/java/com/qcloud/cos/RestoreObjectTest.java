package com.qcloud.cos;


import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.StorageClass;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RestoreObjectTest extends AbstractCOSClientTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }
    
    @Test
    public void restoreObject() {
        InputStream input = new ByteArrayInputStream(new byte[10]);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(10);
        String key = "ut/aaa.txt";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, input, objectMetadata);
        putObjectRequest.setStorageClass(StorageClass.Archive);
        cosclient.putObject(putObjectRequest);
        cosclient.restoreObject(bucket, key, 1);

        try {
            cosclient.restoreObject(bucket, key, -1);
        } catch (Exception e) {
            assertEquals("The expiration in days parameter must be specified when copying a cas object", e.getMessage());
        }
        ObjectMetadata metadata = cosclient.getObjectMetadata(bucket, key);
        cosclient.deleteObject(bucket, key);
    }
}
