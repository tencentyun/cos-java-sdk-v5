package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoRequest;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoResponse;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotRequest;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowExecutionResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowExecutionsResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowListRequest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MediaWorkflowTest extends AbstractCOSClientCITest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

//    @Test
//    public void testGenerateSnapshot() {
//        //1.创建截图请求对象
//        SnapshotRequest request = new SnapshotRequest();
//        //2.添加请求参数 参数详情请见api接口文档
//        request.setBucketName(bucket);
//        request.getInput().setObject("1.mp4");
//        request.getOutput().setBucket(bucket);
//        request.getOutput().setRegion(region);
//        request.getOutput().setObject("test/1.jpg");
//        request.setTime("15");
//        //3.调用接口,获取截图响应对象
//        SnapshotResponse response = cosclient.generateSnapshot(request);
//    }
//
//    @Test
//    public void testGenerateMediainfo() {
//        MediaInfoRequest request = new MediaInfoRequest();
//        request.setBucketName(bucket);
//        request.getInput().setObject("1.mp3");
//        MediaInfoResponse result = cosclient.generateMediainfo(request);
//    }
//
//    @Test
//    public void testDescribeWorkflowExecution() {
//        MediaWorkflowListRequest request = new MediaWorkflowListRequest();
//        request.setBucketName(bucket);
//        request.setRunId(workflowId);
//        MediaWorkflowExecutionResponse result = cosclient.describeWorkflowExecution(request);
//
//        // Verify the results
//    }
//
//    @Test
//    public void testDescribeWorkflowExecutions() {
//        MediaWorkflowListRequest request = new MediaWorkflowListRequest();
//        request.setBucketName(bucket);
//        request.setWorkflowId(workflowId);
//        MediaWorkflowExecutionsResponse result = cosclient.describeWorkflowExecutions(request);
//    }
}
