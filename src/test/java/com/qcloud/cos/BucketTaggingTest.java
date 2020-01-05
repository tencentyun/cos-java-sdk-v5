package com.qcloud.cos;

import com.qcloud.cos.model.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BucketTaggingTest extends AbstractCOSClientTest{
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    @Test
    public void setGetBucketLoggingTest() {
        List<TagSet> tagSetList = new LinkedList<TagSet>();
        TagSet tagSet = new TagSet();
        tagSet.setTag("age", "18");
        tagSet.setTag("name", "xiaoming");
        tagSetList.add(tagSet);
        BucketTaggingConfiguration bucketTaggingConfiguration = new BucketTaggingConfiguration();
        bucketTaggingConfiguration.setTagSets(tagSetList);
        SetBucketTaggingConfigurationRequest setBucketTaggingConfigurationRequest =
                new SetBucketTaggingConfigurationRequest(bucket, bucketTaggingConfiguration);
        cosclient.setBucketTaggingConfiguration(setBucketTaggingConfigurationRequest);
        BucketTaggingConfiguration bucketTaggingConfiguration1 = cosclient.getBucketTaggingConfiguration(bucket);
        assertEquals(bucketTaggingConfiguration.getAllTagSets(), bucketTaggingConfiguration1.getAllTagSets());
        cosclient.deleteBucketTaggingConfiguration(bucket);
    }
}
