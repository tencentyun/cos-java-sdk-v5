package com.qcloud.cos;

import com.qcloud.cos.model.ciModel.queue.MediaListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.MediaQueueRequest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class MediaWorkflowTest extends AbstractCOSClientTest {

    public static final String QUEUE_NAME = "test-queue";
    public static final String QUEUE_URL = "cloud.tencent.com";
    public static final String QUEUE_STATE = "Active";
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    @Test
    public void describeMediaQueuesTest() {
        if (!judgeUserInfoValid()) {
            return;
        }
        MediaQueueRequest request = new MediaQueueRequest();
        request.setBucketName(bucket);
        MediaListQueueResponse response = cosclient.describeMediaQueues(request);
        if (response != null && response.getQueueList().size() != 0) {
            assertNotEquals("0", response.getTotalCount());
            assertTrue(Integer.parseInt(response.getTotalCount()) > 0);
            assertTrue(Integer.parseInt(response.getPageSize()) > 0);
            assertTrue(Integer.parseInt(response.getPageNumber()) > 0);
            assertEquals(bucket, response.getQueueList().get(0).getBucketId());
        }
    }

}
