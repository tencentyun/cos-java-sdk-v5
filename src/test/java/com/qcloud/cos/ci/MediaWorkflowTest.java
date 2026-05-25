package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoRequest;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoResponse;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotRequest;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowExecutionResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowExecutionsResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowListRequest;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowListResponse;
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

    @Test
    public void testGenerateSnapshot() {
        try {
            SnapshotRequest request = new SnapshotRequest();
            request.setBucketName(bucket);
            request.getInput().setObject("2.mp4");
            request.getOutput().setBucket(bucket);
            request.getOutput().setRegion(region);
            request.getOutput().setObject("test/1.jpg");
            request.setTime("15");
            SnapshotResponse response = cosclient.generateSnapshot(request);
            System.out.println(response);
        } catch (Exception e) {

        }

    }

    @Test
    public void testGenerateMediainfo() {
        MediaInfoRequest request = new MediaInfoRequest();
        request.setBucketName(bucket);
        request.getInput().setObject("1.mp3");
        MediaInfoResponse result = cosclient.generateMediainfo(request);
        System.out.println(result);
    }

    @Test
    public void testDescribeWorkflowExecution() {
        try {
            MediaWorkflowListRequest request = new MediaWorkflowListRequest();
            request.setBucketName(bucket);
            request.setWorkflowId(workflowId);
            request.setObject("2.jpg");
            request.setName("mark");
            request.setRunId(runId);
            request.setPageSize("2");
            MediaWorkflowExecutionResponse result = cosclient.describeWorkflowExecution(request);
            System.out.println(result);
        } catch (Exception e) {

        }
    }

    @Test
    public void testDescribeWorkflowExecutions() {
        try {
            MediaWorkflowListRequest request = new MediaWorkflowListRequest();
            request.setBucketName(bucket);
            request.setWorkflowId(workflowId);
            request.setObject("1.jpg");
            request.setName("mark");
            request.setRunId(runId);
            request.setPageSize("2");
            MediaWorkflowExecutionsResponse result = cosclient.describeWorkflowExecutions(request);
            System.out.println(result);
        } catch (Exception e) {

        }

    }

    @Test
    public void triggerWorkflowListTest() {
        try {
            MediaWorkflowListRequest request = new MediaWorkflowListRequest();
            request.setBucketName(bucket);
            request.setWorkflowId(workflowId);
            request.setObject("1.mp4");
            MediaWorkflowListResponse response = cosclient.triggerWorkflowList(request);
            System.out.println("Trigger Workflow List Response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void describeWorkflowExecutionTest() {
        try {
            //1.创建工作流请求对象
            MediaWorkflowListRequest request = new MediaWorkflowListRequest();
            //2.添加请求参数 参数详情请见api接口文档
            request.setBucketName(bucket);
            request.setRunId("i34bfd8d7eae711ea89fe525400c");
            MediaWorkflowExecutionResponse response = cosclient.describeWorkflowExecution(request);
            System.out.println(response);
        } catch (Exception e) {
        }

    }
}
