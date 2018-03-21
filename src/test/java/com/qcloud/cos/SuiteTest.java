package com.qcloud.cos;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({AclTest.class, BatchDeleteTest.class, BucketReplicationTest.class,
        BucketVersioningTest.class, CORSTest.class, CreateDeleteHeadBucketTest.class,
        GeneratePresignedUrlTest.class, GetBucketLocationTest.class, GetServiceTest.class,
        ListObjectTest.class, ListVersionsTest.class, MultipartUploadTest.class,
        PutGetDelTest.class, PutGetLifeCycleConfigTest.class, PutObjectCopyTest.class})
public class SuiteTest {
}
