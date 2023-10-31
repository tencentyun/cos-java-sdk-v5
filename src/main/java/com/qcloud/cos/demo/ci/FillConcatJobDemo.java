package com.qcloud.cos.demo.ci;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.v2.FillConcat;
import com.qcloud.cos.model.ciModel.job.v2.FillInput;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobOperation;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobResponseV2;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobsRequestV2;
import com.qcloud.cos.model.ciModel.job.v2.SegmentVideoBody;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 填充拼接任务demo
 */
public class FillConcatJobDemo {
    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createMediaJobs(client);
    }

    /**
     * createMediaJobs demo 创建填充拼接任务
     *
     * @param client
     */
    public static void createMediaJobs(COSClient client) throws JsonProcessingException {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("FillConcat");
        //2.1添加填充拼接操作参数
        MediaJobOperation operation = request.getOperation();
        FillConcat fillConcat = operation.getFillConcat();
        fillConcat.setFormat("mp4");
        List<FillInput> fillInputList = fillConcat.getFillInput();
        FillInput fillInput = new FillInput();
        fillInput.setUrl("https://demo-1234567890.cos.ap-singapore.com/files/1.mp4");
        fillInputList.add(fillInput);

        fillInput = new FillInput();
        fillInput.setUrl("https://demo-1234567890.cos.ap-singapore.com/files/2.mp4");
        fillInputList.add(fillInput);

        MediaOutputObject output = request.getOperation().getOutput();
        output.setBucket("demo-1234567890");
        output.setRegion("ap-chongqing");
        output.setObject("media/out.${ext}");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.createMediaJobsV2(request);
        System.out.println(response.getJobsDetail().getJobId());
    }

    /**
     * describeMediaJob 根据jobId查询任务信息
     *
     * @param client
     */
    public static void describeMediaJob(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("j5d15810e77e111eeaf41e735259*****");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.describeMediaJobV2(request);
        System.out.println(Jackson.toJsonString(response));
    }

}
