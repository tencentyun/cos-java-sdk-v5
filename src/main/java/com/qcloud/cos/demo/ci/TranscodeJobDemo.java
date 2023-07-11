package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.MediaAudioMixObject;
import com.qcloud.cos.model.ciModel.job.MediaAudioObject;
import com.qcloud.cos.model.ciModel.job.MediaContainerObject;
import com.qcloud.cos.model.ciModel.job.MediaJobObject;
import com.qcloud.cos.model.ciModel.job.MediaJobOperation;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.model.ciModel.job.MediaListJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaTimeIntervalObject;
import com.qcloud.cos.model.ciModel.job.MediaTranscodeObject;
import com.qcloud.cos.model.ciModel.job.MediaTranscodeVideoObject;

import java.util.List;

/**
 * 媒体处理 job接口相关demo 详情见https://cloud.tencent.com/document/product/460/48216
 */
public class TranscodeJobDemo {

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createMediaJobs2(client);
    }

    /**
     * createMediaJobs 接口用于创建媒体任务
     * demo 使用转码参数创建任务 推荐使用模板创建媒体任务
     *
     * @param client
     */
    public static void createMediaJobs(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("Transcode");
        request.getInput().setObject("1.mp4");
        //2.1添加媒体任务操作参数
        MediaTranscodeObject transcode = request.getOperation().getTranscode();
        MediaContainerObject container = transcode.getContainer();
        container.setFormat("mp4");
        MediaTranscodeVideoObject video = transcode.getVideo();
        video.setCodec("H.264");
        video.setProfile("high");
        video.setBitrate("1000");
        video.setWidth("1280");
        video.setFps("30");
        video.setPreset("medium");
        video.setBufSize("0");
        video.setMaxrate("50000");

        MediaAudioObject audio = transcode.getAudio();
        audio.setCodec("aac");
        audio.setSamplerate("44100");
        audio.setBitrate("128");
        audio.setChannels("4");

        MediaTimeIntervalObject timeInterval = transcode.getTimeInterval();
        timeInterval.setStart("0");
        timeInterval.setDuration("60");

        request.getOperation().getOutput().setBucket("demo-1234567890");
        request.getOperation().getOutput().setRegion("ap-chongqing");
        request.getOperation().getOutput().setObject("demo1.mp4");
        request.setCallBack("https://cloud.tencent.com/xxx");
        //3.调用接口,获取任务响应对象
        MediaJobResponse response = client.createMediaJobs(request);
        System.out.println(response.getJobsDetail().getJobId());
    }

    /**
     * createMediaJobs2
     * demo 转码使用混音操作创建任务
     */
    public static void createMediaJobs2(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("Transcode");
        request.getInput().setObject("1.mp4");
        MediaJobOperation operation = request.getOperation();
        //2.1添加媒体任务操作参数
        MediaTranscodeObject transcode = operation.getTranscode();
        MediaContainerObject container = transcode.getContainer();
        container.setFormat("mkv");
        MediaTranscodeVideoObject video = transcode.getVideo();
        video.setCodec("H.264");
        video.setProfile("high");
        video.setBitrate("1000");
        video.setWidth("1280");
        video.setFps("30");
        video.setPreset("medium");
        video.setBufSize("0");
        video.setMaxrate("50000");

        MediaAudioObject audio = transcode.getAudio();
        audio.setCodec("aac");
        audio.setSamplerate("44100");
        audio.setBitrate("128");
        audio.setChannels("4");

        List<MediaAudioMixObject> audioMixArray = transcode.getAudioMixArray();
        MediaAudioMixObject mediaAudioMixObject = new MediaAudioMixObject();
        mediaAudioMixObject.setAudioSource("https://demo-1234567890.cos.ap-chongqing.myqcloud.com/1.mp3");
        mediaAudioMixObject.setReplace("true");
        audioMixArray.add(mediaAudioMixObject);

        mediaAudioMixObject = new MediaAudioMixObject();
        mediaAudioMixObject.setAudioSource("https://demo-1234567890.cos.ap-chongqing.myqcloud.com/1.mp3");
        mediaAudioMixObject.setReplace("true");
        audioMixArray.add(mediaAudioMixObject);

        operation.getOutput().setBucket("demo-1234567890");
        operation.getOutput().setRegion("ap-chongqing");
        operation.getOutput().setObject("demo2.mp4");
        request.setCallBack("https://cloud.tencent.com/xxx");
        //3.调用接口,获取任务响应对象
        MediaJobResponse response = client.createMediaJobs(request);
        System.out.println(response.getJobsDetail().getJobId());
    }

    /**
     * describeMediaJob 根据jobId查询任务信息
     *
     * @param client
     */
    public static void describeMediaJob(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("j64f64980a6cc11ed8d022552f0a*****");
        //3.调用接口,获取任务响应对象
        MediaJobResponse response = client.describeMediaJob(request);
        System.out.println(response);
    }

    /**
     * describeMediaJobs 查询任务列表
     *
     * @param client
     */
    public static void describeMediaJobs(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("Transcode");
        //3.调用接口,获取任务响应对象
        MediaListJobResponse response = client.describeMediaJobs(request);
        List<MediaJobObject> jobsDetail = response.getJobsDetailList();
        for (MediaJobObject mediaJobObject : jobsDetail) {
            System.out.println(mediaJobObject.getOperation().getTranscode());
        }
    }

    /**
     * cancelMediaJob 取消任务
     *
     * @param client
     */
    public static void cancelMediaJob(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("DemoBucket-123456789");
        request.setJobId("jbfb0d02a092111ebb3167781d*****");
        //3.调用接口,获取任务响应对象
        Boolean response = client.cancelMediaJob(request);
        System.out.println(response);
    }
}
