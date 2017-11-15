package com.qcloud.cos;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.qcloud.cos.model.AccessControlList;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.Grant;
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
    public void setGetBucketAclTest() {
        if (!judgeUserInfoValid()) {
            return;
        }
        AccessControlList acl = new AccessControlList();
        Owner owner = new Owner();
        owner.setId("qcs::cam::uin/2779643970:uin/2779643970");
        acl.setOwner(owner);
        String id = "qcs::cam::uin/2779643970:uin/734505014";
        UinGrantee uinGrantee = new UinGrantee(id);
        uinGrantee.setIdentifier(id);
        acl.grantPermission(uinGrantee, Permission.FullControl);
        cosclient.setBucketAcl(bucket, acl);

        AccessControlList aclGet = cosclient.getBucketAcl(bucket);
        List<Grant> grants = aclGet.getGrantsAsList();
        assertEquals(1L, grants.size());
        assertEquals(id, grants.get(0).getGrantee().getIdentifier());
        assertEquals(Permission.FullControl.toString(), grants.get(0).getPermission().toString());
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
            owner.setId("qcs::cam::uin/2779643970:uin/2779643970");
            acl.setOwner(owner);
            String id = "qcs::cam::uin/2779643970:uin/734505014";
            UinGrantee uinGrantee = new UinGrantee(id);
            acl.grantPermission(uinGrantee, Permission.FullControl);
            cosclient.setObjectAcl(bucket, key, acl);

            AccessControlList aclGet = cosclient.getObjectAcl(bucket, key);
            List<Grant> grants = aclGet.getGrantsAsList();
            assertEquals(1L, grants.size());
            assertEquals(id, grants.get(0).getGrantee().getIdentifier());
            assertEquals(Permission.FullControl.toString(),
                    grants.get(0).getPermission().toString());
        } finally {
            assertTrue(localFile.delete());
            clearObject(key);
        }
    }

    @Ignore
    public void setObjectCannedAclTest() throws IOException {
        if (!judgeUserInfoValid()) {
            return;
        }
        File localFile = buildTestFile(0L);
        String key = "ut/acl_test.txt";
        putObjectFromLocalFile(localFile, key);
        try {
            cosclient.setObjectAcl(bucket, key, CannedAccessControlList.PublicRead);
            cosclient.getObjectAcl(bucket, key);
        } finally {
            assertTrue(localFile.delete());
            clearObject(key);
        }
    }

}
