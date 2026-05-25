package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.job.MediaJobOperation;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * MediaJobOperation 单元测试
 * 主要测试懒加载功能和JSON序列化后调用createMediaJobs的问题修复
 */
public class MediaJobOperationTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }


    @Test
    public void testCreateMediaJobsAfterJsonPrint() {
        
        try {
            // 1. 创建请求
            MediaJobsRequest request = new MediaJobsRequest();
            request.setBucketName(bucket);
            request.setTag("Transcode");
            request.getInput().setObject("test-input.mp4");
            
            // 2. 设置Operation参数
            MediaJobOperation operation = request.getOperation();
            operation.setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            operation.getOutput().setBucket(bucket);
            operation.getOutput().setRegion(region);
            operation.getOutput().setObject("test-output.mp4");

            // 3. 关键步骤：打印JSON（这是之前会导致问题的操作）
            String operationJson = Jackson.toJsonString(operation);
            System.out.println("Operation JSON: " + operationJson);

            String requestJson = Jackson.toJsonString(request);
            System.out.println("Request JSON: " + requestJson);

            // 4. 调用API（修复前这里会失败）
            MediaJobResponse response = cosclient.createMediaJobs(request);
            
            // 5. 验证响应
            assertNotNull("响应不应为null", response);
            assertNotNull("任务详情不应为null", response.getJobsDetail());
            assertNotNull("任务ID不应为null", response.getJobsDetail().getJobId());
            
            System.out.println("创建的任务ID: " + response.getJobsDetail().getJobId());
            System.out.println("任务状态: " + response.getJobsDetail().getState());
            
        } catch (Exception e) {
            System.err.println(" 测试失败: " + e.getMessage());
            e.printStackTrace();
            fail("JSON打印后调用createMediaJobs失败: " + e.getMessage());
        }
    }

}
