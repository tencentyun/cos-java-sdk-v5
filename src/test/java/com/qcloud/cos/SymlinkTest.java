package com.qcloud.cos;

import com.qcloud.cos.model.GetSymlinkRequest;
import com.qcloud.cos.model.GetSymlinkResult;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.PutSymlinkRequest;
import com.qcloud.cos.model.PutSymlinkResult;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class SymlinkTest extends AbstractCOSClientTest{
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    @Test
    public void testSetGetSymlink() throws IOException {
        String key = "testSym.txt";
        File localFile = buildTestFile(3 * 1024 * 1024L);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, localFile);
        PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);

        Boolean isExist = cosclient.doesObjectExist(bucket, key);
        assertTrue(isExist);

        PutSymlinkRequest putSymlinkRequest = new PutSymlinkRequest(bucket, "test-symlink", key);
        PutSymlinkResult putSymlinkResult = cosclient.putSymlink(putSymlinkRequest);
        GetSymlinkResult getSymlinkResult = cosclient.getSymlink(
                new GetSymlinkRequest(bucket, "test-symlink", null));
        System.out.println(getSymlinkResult.getETag());
    }
}
