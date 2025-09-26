package com.qcloud.cos;

import com.qcloud.cos.model.ciModel.queue.MediaListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.MediaQueueObject;
import com.qcloud.cos.model.ciModel.queue.MediaQueueRequest;
import com.qcloud.cos.model.ciModel.queue.MediaQueueResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class MediaQueueTest extends AbstractCOSClientCITest {

    public static final String QUEUE_NAME = "queue-1";
    public static final String QUEUE_URL = "cloud.tencent.com";
    public static final String QUEUE_STATE = "Active";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Test
    public void describeMediaQueuesTest() {
        try {
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
        } catch (Exception e) {
        }
    }

    @Test
    public void updateMediaQueueTest() {
        try {
            if (!judgeUserInfoValid()) {
                return;
            }
            MediaQueueRequest request = new MediaQueueRequest();
            request.setBucketName(bucket);
            MediaListQueueResponse response = cosclient.describeMediaQueues(request);
            if (response != null) {
                List<MediaQueueObject> queueList = response.getQueueList();
                if (queueList.size() != 0) {
                    MediaQueueObject mediaQueueObject = queueList.get(0);
                    request = new MediaQueueRequest();
                    request.setBucketName(bucket);
                    request.setQueueId(mediaQueueObject.getQueueId());
                    request.getNotifyConfig().setUrl(QUEUE_URL);
                    request.setState(QUEUE_STATE);
                    request.setName(QUEUE_NAME);
                    MediaQueueResponse updateResponse = cosclient.updateMediaQueue(request);
                    MediaQueueObject queue = updateResponse.getQueue();
                    assertEquals(bucket, queue.getBucketId());
                    assertEquals(QUEUE_NAME, queue.getName());
                    assertEquals(QUEUE_STATE, queue.getState());
                }
            }
        } catch (Exception e) {
        }

    }

    @Test
    public void categoryMediaQueueTest() {
        try {
            if (!judgeUserInfoValid()) {
                return;
            }
            MediaQueueRequest request = new MediaQueueRequest();
            request.setBucketName(bucket);
            request.setCategory("CateAll");
            MediaListQueueResponse response = cosclient.describeMediaQueues(request);
            if (response != null && !response.getQueueList().isEmpty()) {
                assertTrue(response.getQueueList().size() > 1);
            }
        } catch (Exception e) {
        }
    }
}
