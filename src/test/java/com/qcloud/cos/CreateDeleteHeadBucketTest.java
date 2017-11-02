package com.qcloud.cos;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import com.qcloud.cos.model.AccessControlList;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.BucketVersioningConfiguration;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.model.Grantee;
import com.qcloud.cos.model.HeadBucketRequest;
import com.qcloud.cos.model.Permission;
import com.qcloud.cos.model.UinGrantee;

public class CreateDeleteHeadBucketTest extends AbstractCOSClientTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    @Test
    public void testCreateDeleteBucketPublicRead() throws Exception {
        String bucketName = "publicreadbucket";
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        createBucketRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        Bucket bucket = cosclient.createBucket(createBucketRequest);
        assertEquals(bucketName, bucket.getName());

        cosclient.headBucket(new HeadBucketRequest(bucketName));
        
        BucketVersioningConfiguration bucketVersioningConfiguration =
                cosclient.getBucketVersioningConfiguration(bucketName);
        assertEquals(BucketVersioningConfiguration.OFF, bucketVersioningConfiguration.getStatus());

        cosclient.deleteBucket(bucketName);
        // 删除bucket后, 由于server端有缓存 需要稍后查询, 这里sleep 5 秒
        assertFalse(cosclient.doesBucketExist(bucketName));
    }

    @Test
    public void testCreateDeleteBucketPublicReadWrite() throws Exception {
        String bucketName = "publicreadwritebucket";
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        createBucketRequest.setCannedAcl(CannedAccessControlList.PublicReadWrite);
        AccessControlList accessControlList = new AccessControlList();
        Grantee grantee = new UinGrantee("730123456");
        accessControlList.grantPermission(grantee, Permission.Write);
        createBucketRequest.setAccessControlList(accessControlList);
        Bucket bucket = cosclient.createBucket(createBucketRequest);
        assertEquals(bucketName, bucket.getName());
        
        assertTrue(cosclient.doesBucketExist(bucketName));

        BucketVersioningConfiguration bucketVersioningConfiguration =
                cosclient.getBucketVersioningConfiguration(bucketName);
        assertEquals(BucketVersioningConfiguration.OFF, bucketVersioningConfiguration.getStatus());

        cosclient.deleteBucket(bucketName);
        // 删除bucket后, 由于server端有缓存 需要稍后查询, 这里sleep 5 秒
        assertFalse(cosclient.doesBucketExist(bucketName));
    }

    @Test
    public void testCreateDeleteBucketPrivate() throws Exception {
        String bucketName = "privatebucket";
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
        Bucket bucket = cosclient.createBucket(createBucketRequest);
        assertEquals(bucketName, bucket.getName());
        
        assertTrue(cosclient.doesBucketExist(bucketName));

        BucketVersioningConfiguration bucketVersioningConfiguration =
                cosclient.getBucketVersioningConfiguration(bucketName);
        assertEquals(BucketVersioningConfiguration.OFF, bucketVersioningConfiguration.getStatus());

        cosclient.deleteBucket(bucketName);
        // 删除bucket后, 由于server端有缓存 需要稍后查询, 这里sleep 5 秒
        Thread.sleep(5000L);
        assertFalse(cosclient.doesBucketExist(bucketName));
    }

}
