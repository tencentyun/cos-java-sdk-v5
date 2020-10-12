package com.qcloud.cos;

import com.qcloud.cos.model.ciModel.queue.MediaListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.MediaQueueRequest;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowListRequest;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowListResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

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
    public void mediaWorkflowTest() {
        if (!judgeUserInfoValid()) {
            return;
        }
        MediaWorkflowListRequest request = new MediaWorkflowListRequest();
        request.setBucketName(bucket);
        MediaWorkflowListResponse response = cosclient.describeWorkflow(request);
        List<MediaWorkflowObject> mediaWorkflowList = response.getMediaWorkflowList();
        if (mediaWorkflowList.size() != 0) {
            for (MediaWorkflowObject mediaWorkflowObject : mediaWorkflowList) {
                assertEquals(bucket,mediaWorkflowObject.getBucketId());
                assertFalse(mediaWorkflowObject.getName().isEmpty());
                assertFalse(mediaWorkflowObject.getState().isEmpty());
            }
        }

    }

}
