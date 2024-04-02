package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.*;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobResponseV2;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobsRequestV2;
import com.qcloud.cos.utils.Jackson;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 媒体处理 Concat接口相关demo 详情见https://cloud.tencent.com/document/product/460/49168
 */
public class ConcatDemo {

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createMediaJobs(client);
    }

    /**
     * createMediaJobs 接口用于创建任务。
     * @param client
     */
    public static void createMediaJobs(COSClient client) throws UnsupportedEncodingException {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("Concat");
        request.getInput().setObject("demo.mp4");
        MediaConcatTemplateObject mediaConcatTemplate = request.getOperation().getMediaConcatTemplate();
        SceneChangeInfo sceneChangeInfo = mediaConcatTemplate.getSceneChangeInfo();
        sceneChangeInfo.setMode("XFADE");
        sceneChangeInfo.setTime("5");
        sceneChangeInfo.setTransitionType("fade");
        List<MediaConcatFragmentObject> concatFragmentList = mediaConcatTemplate.getConcatFragmentList();
        MediaConcatFragmentObject mediaConcatFragmentObject = new MediaConcatFragmentObject();
        mediaConcatFragmentObject.setMode("Start");
        mediaConcatFragmentObject.setUrl("https://demo-1234567890.cos.ap-chongqing.myqcloud.com/media/1.mp4");
        concatFragmentList.add(mediaConcatFragmentObject);

        MediaAudioObject audio = mediaConcatTemplate.getAudio();
        audio.setCodec("mp3");
        MediaVideoObject video = mediaConcatTemplate.getVideo();
        video.setCodec("H.264");
        video.setBitrate("1000");
        video.setWidth("1280");
        video.setFps("30");

        MediaContainerObject container = mediaConcatTemplate.getContainer();
        container.setFormat("mp4");

        mediaConcatTemplate.setIndex("1");
        request.getOperation().getOutput().setBucket("demo-1234567890");
        request.getOperation().getOutput().setRegion("ap-chongqing");
        request.getOperation().getOutput().setObject("concat.mp4");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.createMediaJobsV2(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * describeMediaJob 根据jobId查询任务信息
     * @param client
     */
    public static void describeMediaJob(COSClient client)  {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("j0d9e19eef00211ee9b72713e624*****");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.describeMediaJobV2(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * cancelMediaJob 取消任务
     * @param client
     */
    public static void cancelMediaJob(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("jbfb0d02a092111ebb3167781d*****");
        //3.调用接口,获取任务响应对象
        Boolean response = client.cancelMediaJob(request);
        System.out.println(response);
    }
}
