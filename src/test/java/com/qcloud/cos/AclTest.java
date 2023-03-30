package com.qcloud.cos;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.qcloud.cos.model.AccessControlList;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.model.Grant;
import com.qcloud.cos.model.GroupGrantee;
import com.qcloud.cos.model.Owner;
import com.qcloud.cos.model.Permission;
import com.qcloud.cos.model.UinGrantee;

public class AclTest extends AbstractCOSClientTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    @Test
    public void GetAclForNewPubReadBucket() throws InterruptedException {
        if (!judgeUserInfoValid()) {
            return;
        }
        String aclTestBucketName = null;
        try {
            aclTestBucketName = "javasdkacltest-" + appid;
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(aclTestBucketName);
            createBucketRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            cosclient.createBucket(createBucketRequest);
            AccessControlList aclGet = cosclient.getBucketAcl(aclTestBucketName);
//            assertEquals(aclGet.getCannedAccessControl(), CannedAccessControlList.PublicRead);
//            assertNotNull(aclGet.getOwner());
//            assertNotNull(aclGet.getOwner().getId());
//            assertNotNull(aclGet.getOwner().getDisplayName());
            
//            assertEquals(2, aclGet.getGrantsAsList().size());
            Grant firstGrant = aclGet.getGrantsAsList().get(0);
            //assertEquals(Permission.Read.toString(), firstGrant.getPermission().toString());
//            assertTrue(firstGrant.getGrantee() instanceof GroupGrantee);
            
            Grant secondGrant = aclGet.getGrantsAsList().get(1);
            //assertEquals(Permission.FullControl.toString(), secondGrant.getPermission().toString());
//            assertTrue(secondGrant.getGrantee() instanceof UinGrantee);

            // set to PublicReadWrite acl and get canned acl compare
            Thread.sleep(5000);
            cosclient.setBucketAcl(aclTestBucketName, CannedAccessControlList.PublicReadWrite);
            Thread.sleep(5000);
            aclGet = cosclient.getBucketAcl(aclTestBucketName);
            //assertEquals(aclGet.getCannedAccessControl(), CannedAccessControlList.PublicReadWrite);

            // set to private and get canned acl compare
            Thread.sleep(5000);
            cosclient.setBucketAcl(aclTestBucketName, CannedAccessControlList.Private);
            Thread.sleep(5000);
            aclGet = cosclient.getBucketAcl(aclTestBucketName);
            //assertEquals(aclGet.getCannedAccessControl(), CannedAccessControlList.Private);

        } finally {
            if (aclTestBucketName != null) {
                cosclient.deleteBucket(aclTestBucketName);
                aclTestBucketName = null;
            }
        }

    }

    @Test
    public void setGetBucketAclTest() {
        if (!judgeUserInfoValid()) {
            return;
        }

        String ownerId = String.format("qcs::cam::uin/%s:uin/%s", ownerUin, ownerUin);
        AccessControlList acl = new AccessControlList();
        Owner owner = new Owner();
        owner.setId(ownerId);
        acl.setOwner(owner);

//        String granteeUin = String.format("qcs::cam::uin/%s:uin/734505014", ownerUin);
        String granteeUin = "qcs::cam::uin/734505014:uin/734505014";
        UinGrantee uinGrantee = new UinGrantee(granteeUin);
        uinGrantee.setIdentifier(granteeUin);
        acl.grantPermission(uinGrantee, Permission.FullControl);
        cosclient.setBucketAcl(bucket, acl);

        AccessControlList aclGet = cosclient.getBucketAcl(bucket);
        List<Grant> grants = aclGet.getGrantsAsList();
        assertEquals(1L, grants.size());
        //assertEquals(granteeUin, grants.get(0).getGrantee().getIdentifier());
        //assertEquals(Permission.FullControl.toString(), grants.get(0).getPermission().toString());
    }

    @Ignore
    public void setGetBucketCannedAclTest() {
        if (!judgeUserInfoValid()) {
            return;
        }
        cosclient.setBucketAcl(bucket, CannedAccessControlList.Private);
        AccessControlList acl = cosclient.getBucketAcl(bucket);

    }

    @Test
    public void setGetObjectAclTest() throws IOException {
        if (!judgeUserInfoValid()) {
            return;
        }
        File localFile = buildTestFile(0L);
        String key = "ut/acl_test.txt";
        putObjectFromLocalFile(localFile, key);
        try {
            AccessControlList acl = new AccessControlList();
            Owner owner = new Owner();
            String ownerId = String.format("qcs::cam::uin/%s:uin/%s", ownerUin, ownerUin);
            owner.setId(ownerId);
            acl.setOwner(owner);
            String granteeUin = String.format("qcs::cam::uin/%s:uin/734505014", ownerUin);
            UinGrantee uinGrantee = new UinGrantee(granteeUin);
            acl.grantPermission(uinGrantee, Permission.FullControl);
            cosclient.setObjectAcl(bucket, key, acl);

            Thread.sleep(5000);

            AccessControlList aclGet = cosclient.getObjectAcl(bucket, key);
            List<Grant> grants = aclGet.getGrantsAsList();
            assertEquals(1L, grants.size());
//            assertEquals(granteeUin, grants.get(0).getGrantee().getIdentifier());
//            assertEquals(Permission.FullControl.toString(),
//                    grants.get(0).getPermission().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            assertTrue(localFile.delete());
            clearObject(key);
        }
    }

    @Test
    public void setObjectCannedAclTest() throws IOException, InterruptedException {
        if (!judgeUserInfoValid()) {
            return;
        }
        File localFile = buildTestFile(0L);
        String key = "ut/acl_test.txt";
        putObjectFromLocalFile(localFile, key);
        try {
            cosclient.setObjectAcl(bucket, key, CannedAccessControlList.PublicRead);
            AccessControlList accessControlList = cosclient.getObjectAcl(bucket, key);
            //assertEquals(accessControlList.getCannedAccessControl(), CannedAccessControlList.PublicRead);
            cosclient.setObjectAcl(bucket, key, CannedAccessControlList.Private);
            accessControlList = cosclient.getObjectAcl(bucket, key);
            //assertEquals(accessControlList.getCannedAccessControl(), CannedAccessControlList.Private);
            cosclient.setObjectAcl(bucket, key, CannedAccessControlList.Default);
            accessControlList = cosclient.getObjectAcl(bucket, key);
            //assertEquals(accessControlList.getCannedAccessControl(), CannedAccessControlList.Default);

        } finally {
            assertTrue(localFile.delete());
            clearObject(key);
        }
    }

}

