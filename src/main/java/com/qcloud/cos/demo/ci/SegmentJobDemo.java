package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.AigcMetadata;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.model.ciModel.template.MediaHlsEncryptObject;
import com.qcloud.cos.model.ciModel.template.MediaSegmentObject;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;

/**
 * 媒体处理 Segment job接口相关demo 详情见https://cloud.tencent.com/document/product/460/67187
 */
public class SegmentJobDemo {

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createMediaJobs(client);
    }

    /**
     * createMediaJobs 接口用于创建媒体任务。
     * demo 使用模板创建任务，如需自定义模板请先使用创建模板接口
     *
     * @param client
     */
    public static void createMediaJobs(COSClient client) throws UnsupportedEncodingException {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demobucket-1234567890");
        request.setTag("Segment");
        request.getInput().setObject("1.mp4");
        MediaSegmentObject segment = request.getOperation().getSegment();
        segment.setDuration("5");
        segment.setFormat("mp4");
        MediaHlsEncryptObject hlsEncrypt = segment.getHlsEncrypt();
        hlsEncrypt.setIsHlsEncrypt("true");
        hlsEncrypt.setUriKey("https://example.com/aes.key");
        request.getOperation().getOutput().setBucket("demobucket-1234567890");
        request.getOperation().getOutput().setRegion("ap-chongqing");
        request.getOperation().getOutput().setObject("demo-trans-${Number}.mp4");
        //3.调用接口,获取任务响应对象
        MediaJobResponse response = client.createMediaJobs(request);
        System.out.println(response);
    }

    public static void createMediaJobsWithAigcMetadata(COSClient client) throws UnsupportedEncodingException {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demobucket-1234567890");
        request.setTag("Segment");
        request.getInput().setObject("1.mp4");
        MediaSegmentObject segment = request.getOperation().getSegment();
        segment.setFormat("mp4");
        AigcMetadata aigcMetadata = segment.getAigcMetadata();
        aigcMetadata.setLabel("label");
        aigcMetadata.setContentProducer("testProducer");
        aigcMetadata.setProduceId("testProduceId");
        aigcMetadata.setReservedCode1(new String(Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setReservedCode2(new String(Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setPropagateId("testPropagateId");
        aigcMetadata.setContentPropagator("testPropagator");
        request.getOperation().getOutput().setBucket("demobucket-1234567890");
        request.getOperation().getOutput().setRegion("ap-chongqing");
        request.getOperation().getOutput().setObject("demo-trans-result.mp4");
        //3.调用接口,获取任务响应对象
        MediaJobResponse response = client.createMediaJobs(request);
        System.out.println(response.getJobsDetail().getOperation().getSegment().getAigcMetadata());
        System.out.println(response.getJobsDetail().getJobId());
    }

}
