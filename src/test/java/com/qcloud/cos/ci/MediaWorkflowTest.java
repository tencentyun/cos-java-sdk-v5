package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.BatchJobOperation;
import com.qcloud.cos.model.ciModel.job.BatchJobRequest;
import com.qcloud.cos.model.ciModel.job.BatchJobResponse;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoRequest;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoResponse;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotRequest;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowExecutionResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowExecutionsResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowListRequest;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowListResponse;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MediaWorkflowTest extends AbstractCOSClientCITest {
    private String batchJobId;

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
        } catch (Exception e) {

        }

    }

    @Test
    public void testGenerateMediainfo() {
        MediaInfoRequest request = new MediaInfoRequest();
        request.setBucketName(bucket);
        request.getInput().setObject("1.mp3");
        MediaInfoResponse result = cosclient.generateMediainfo(request);
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
        } catch (Exception e) {
        }

    }

    @Before
    public void createInventoryTriggerJobTest() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("demo");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p9900025e4ec44b5e8225e70a52170834");
            operation.setTag("Transcode");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("out/${InventoryTriggerJobId}.mp4");
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            batchJobId = response.getJobDetail().getJobId();
        } catch (Exception e) {

        }

    }

    @Test
    public void describeInventoryTriggerJobTest() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setJobId(batchJobId);
            BatchJobResponse response = cosclient.describeInventoryTriggerJob(request);
        } catch (Exception e) {
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
