package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.auditing.AuditingAudio;
import com.qcloud.cos.model.ciModel.auditing.AuditingCosOutput;
import com.qcloud.cos.model.ciModel.auditing.AuditingImage;
import com.qcloud.cos.model.ciModel.auditing.AuditingLiveOutput;
import com.qcloud.cos.model.ciModel.auditing.Conf;
import com.qcloud.cos.model.ciModel.auditing.Mask;
import com.qcloud.cos.model.ciModel.auditing.VideoAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.VideoAuditingResponse;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 内容审核 直播审核接口相关demo 详情见https://cloud.tencent.com/document/product/460/76261
 */
public class LiveAuditingJobDemo {

    public static void main(String[] args) throws InterruptedException {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createLiveAuditingJob(client);
    }

    /**
     * createLiveAuditingJob 接口用于创建直播审核任务。
     */
    public static void createLiveAuditingJob(COSClient client) {
        //1.创建任务请求对象
        VideoAuditingRequest request = new VideoAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.getInput().setUrl("rtmp://play.demo.com/live/sdktest");
        request.setType("live_video");
        Conf conf = request.getConf();
        conf.setCallback("http://demohost:8001/livedemo");
        conf.setBizType("97ce1ce3b28609e92d441aa6166*****");
        Mask mask = conf.getMask();
        AuditingLiveOutput liveOutput = mask.getLiveOutput();
        liveOutput.setUrl("rtmp://demo.myqcloud.com/live/marktest");
        AuditingCosOutput cosOutput = mask.getCosOutput();
        cosOutput.setObject("demo/${JobId}.mp4</Object>");
        cosOutput.setRegion("ap-chongqing");
        cosOutput.setBucket("demo-1234567890");

        List<AuditingImage> images = mask.getImages();
        AuditingImage auditingImage = new AuditingImage();
        auditingImage.setLabel("Ads:QRCode");
        auditingImage.setType("Mask");
        auditingImage.setUrl("https://demo-1234567890.cos.ap-chongqign.myqcloud.com/guaziqrcode.png");
        images.add(auditingImage);
        auditingImage = new AuditingImage();
        auditingImage.setType("Mosaic");
        auditingImage.setLabel("default");
        images.add(auditingImage);

        List<AuditingAudio> audios = mask.getAudios();
        AuditingAudio audio = new AuditingAudio();
        audio.setLabel("default");
        audio.setType("Silence");
        audios.add(audio);
        //3.调用接口,获取任务响应对象
        VideoAuditingResponse response = client.createVideoAuditingJob(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * describeLiveAuditingJob 接口用于查询直播审核任务。
     *
     * @param client
     */
    public static void describeLiveAuditingJob(COSClient client) throws InterruptedException {
        //1.创建任务请求对象
        VideoAuditingRequest request = new VideoAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("avf8b813aa06a411ee80f45254004*****");
        //3.调用接口,获取任务响应对象
        VideoAuditingResponse response = client.describeAuditingJob(request);
        System.out.println(Jackson.toJsonString(response));
       
    }

    /**
     * cancelMediaJob 取消直播审核
     *
     * @param client
     */
    public static void cancelLiveAuditing(COSClient client) {
        //1.创建任务请求对象
        VideoAuditingRequest request = new VideoAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("avf8b813aa06a411ee80f45254004*****");
        //3.调用接口,获取任务响应对象
        Boolean response = client.cancelLiveAuditing(request);
        System.out.println(response);
    }


}
