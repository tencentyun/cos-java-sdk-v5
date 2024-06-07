package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.auditing.AuditingAudio;
import com.qcloud.cos.model.ciModel.auditing.AuditingCosOutput;
import com.qcloud.cos.model.ciModel.auditing.AuditingImage;
import com.qcloud.cos.model.ciModel.auditing.AuditingLiveOutput;
import com.qcloud.cos.model.ciModel.auditing.Conf;
import com.qcloud.cos.model.ciModel.auditing.Mask;
import com.qcloud.cos.model.ciModel.auditing.VideoAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.VideoAuditingResponse;
import com.qcloud.cos.model.ciModel.bucket.DocBucketRequest;
import com.qcloud.cos.model.ciModel.bucket.DocBucketResponse;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.DocHtmlRequest;
import com.qcloud.cos.model.ciModel.job.DocJobListRequest;
import com.qcloud.cos.model.ciModel.job.DocJobListResponse;
import com.qcloud.cos.model.ciModel.job.DocJobObject;
import com.qcloud.cos.model.ciModel.job.DocJobRequest;
import com.qcloud.cos.model.ciModel.job.DocJobResponse;
import com.qcloud.cos.model.ciModel.job.DocProcessObject;
import com.qcloud.cos.model.ciModel.queue.DocListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.DocQueueRequest;
import com.qcloud.cos.model.ciModel.queue.MediaQueueObject;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class LiveAuditingTest extends AbstractCOSClientCITest {
    private String queueId;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Test
    public void testCreateLiveAuditingJob() {
        try {
            //1.创建任务请求对象
            VideoAuditingRequest request = new VideoAuditingRequest();
            //2.添加请求参数 参数详情请见api接口文档
            request.setBucketName(bucket);
            request.getInput().setUrl("rtmp://play.demo.com/live/sdktest");
            request.setType("live_video");
            Conf conf = request.getConf();
            conf.setCallback("http://www.cidemo.com:8001/livedemo");
            conf.setBizType("97ce1ce3b28609e92d441aa6166");
            Mask mask = conf.getMask();
            AuditingLiveOutput liveOutput = mask.getLiveOutput();
            liveOutput.setUrl("rtmp://demo.myqcloud.com/live/marktest");
            AuditingCosOutput cosOutput = mask.getCosOutput();
            cosOutput.setObject("demo/${JobId}.mp4</Object>");
            cosOutput.setRegion(region);
            cosOutput.setBucket(bucket);

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
            VideoAuditingResponse response = cosclient.createVideoAuditingJob(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception ignored) {
        }
    }


}
