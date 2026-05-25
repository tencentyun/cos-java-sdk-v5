package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.job.v2.VirusDetectConf;
import com.qcloud.cos.model.ciModel.job.v2.VirusDetectInput;
import com.qcloud.cos.model.ciModel.job.v2.VirusDetectJobRequest;
import com.qcloud.cos.model.ciModel.job.v2.VirusDetectJobResponse;
import com.qcloud.cos.model.ciModel.job.v2.VirusDetectRequest;
import com.qcloud.cos.model.ciModel.job.v2.VirusDetectResponse;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class VirusDetectTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Test
    public void testVirusDetectWithCOSObject() {
        if (!judgeUserInfoValid()) {
            return;
        }

        VirusDetectRequest request = new VirusDetectRequest();
        request.setBucketName(bucket);

        // 设置输入文件（COS中的文件）
        VirusDetectInput input = request.getInput();
        input.setObject("SDK/Images/test-2.jpg");

        // 设置检测配置
        VirusDetectConf conf = request.getConf();
        conf.setDetectType("Virus");
        // 可以设置回调地址（可选）
        // conf.setCallback("http://www.callback.com");

        // 执行病毒检测
        VirusDetectResponse response = cosclient.createVirusDetectJob(request);
        
        // 验证响应结果
        assertNotNull("病毒检测响应不能为空", response);
        assertNotNull("任务详情不能为空", response.getJobsDetail());
        assertNotNull("任务ID不能为空", response.getJobsDetail().getJobId());
        assertNotNull("任务状态不能为空", response.getJobsDetail().getState());
        
        // 打印响应结果
        System.out.println("病毒检测任务响应: " + Jackson.toJsonString(response));
        System.out.println("任务ID: " + response.getJobsDetail().getJobId());
        System.out.println("任务状态: " + response.getJobsDetail().getState());
    }

    @Test
    public void testDescribeVirusDetectJob() {
        if (!judgeUserInfoValid()) {
            return;
        }

        VirusDetectRequest createRequest = new VirusDetectRequest();
        createRequest.setBucketName(bucket);
        createRequest.getInput().setObject("SDK/Images/test-2.jpg");
        createRequest.getConf().setDetectType("Virus");

        VirusDetectResponse createResponse = cosclient.createVirusDetectJob(createRequest);
        assertNotNull("创建任务响应不能为空", createResponse);
        assertNotNull("任务ID不能为空", createResponse.getJobsDetail().getJobId());

        String jobId = createResponse.getJobsDetail().getJobId();
        System.out.println("创建的任务ID: " + jobId);

        // 查询任务结果
        VirusDetectJobRequest queryRequest = new VirusDetectJobRequest();
        queryRequest.setBucketName(bucket);
        queryRequest.setJobId(jobId);

        VirusDetectJobResponse queryResponse = cosclient.describeVirusDetectJob(queryRequest);
        
        // 验证查询响应
        assertNotNull("查询响应不能为空", queryResponse);
        assertNotNull("任务详情不能为空", queryResponse.getJobsDetail());
        assertEquals("任务ID应该一致", jobId, queryResponse.getJobsDetail().getJobId());
        assertNotNull("任务状态不能为空", queryResponse.getJobsDetail().getState());


        System.out.println("查询任务响应: " + Jackson.toJsonString(queryResponse));

    }

}