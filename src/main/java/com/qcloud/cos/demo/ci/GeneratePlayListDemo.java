package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.common.MediaVod;
import com.qcloud.cos.model.ciModel.job.*;
import com.qcloud.cos.model.ciModel.job.v2.GetPlayListRequest;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobResponseV2;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobsRequestV2;
import com.qcloud.cos.utils.Jackson;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 媒体处理 边转边播接口相关demo
 */
public class GeneratePlayListDemo {

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        getPlayList(client);
    }

    /**
     * generatePlayList 提交生成播放列表任务
     */
    public static void generatePlayList(COSClient client)  {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        request.setBucketName("demo-1234567890");
        //2.添加请求参数 参数详情请见api接口文档
        request.setTag("GeneratePlayList");
        request.getInput().setObject("1.mp4");
        MediaContainerObject container = request.getOperation().getTranscode().getContainer();
        container.setFormat("hls");
        container.getClipConfig().setDuration("5");
        MediaTransConfigObject transConfig = request.getOperation().getTranscode().getTransConfig();
        transConfig.setCosTag("DemoTag=demo1&DemoTag2=demo2");
        transConfig.getHlsEncrypt().setIsHlsEncrypt("true");
        MediaTranscodeVideoObject video = request.getOperation().getTranscode().getVideo();
        video.setCodec("H.264");
        video.setWidth("1280");
        video.setHeight("960");

        request.getOperation().getOutput().setBucket("demo-1234567890");
        request.getOperation().getOutput().setRegion("ap-beijing");
        request.getOperation().getOutput().setObject("output/media/test.m3u8");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.createMediaJobsV2(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * describeMediaJob 根据jobId查询任务信息
     */
    public static void describeMediaJob(COSClient client)  {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("j8b360cd0142511efac6425779c0*****");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.describeMediaJobV2(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * getPlayList 接口用于获取私有 M3U8 ts 资源的下载授权
     */
    public static void getPlayList(COSClient client)  {
        GetPlayListRequest request = new GetPlayListRequest();
        request.setBucketName("demo-1234567890");
        request.setObject("output/media/test.m3u8");
        request.setExpires("3600");
        try {
            InputStream response = client.getPlayList(request);
            System.out.println(inputStreamToString(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String inputStreamToString(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();

        return stringBuilder.toString();
    }
}
