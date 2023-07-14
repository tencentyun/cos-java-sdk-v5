package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.internal.converter.CIConverter;
import com.qcloud.cos.model.ciModel.auditing.AuditingStrategyRequest;
import com.qcloud.cos.model.ciModel.auditing.AuditingStrategyResponse;
import com.qcloud.cos.model.ciModel.auditing.StrategyAudioLabel;
import com.qcloud.cos.model.ciModel.auditing.StrategyImageLabel;
import com.qcloud.cos.model.ciModel.auditing.StrategyLabels;
import com.qcloud.cos.model.ciModel.auditing.VideoAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.VideoAuditingResponse;
import com.qcloud.cos.utils.Jackson;
import com.thoughtworks.xstream.XStream;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 内容审核 审核策略相关demo 详情见https://cloud.tencent.com/document/product/460/76261
 */
public class AuditingStrategyDemo {

    public static void main(String[] args) throws InterruptedException {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        addAuditingStrategy(client);
    }


    public static void addAuditingStrategy(COSClient client) {
        AuditingStrategyRequest request = new AuditingStrategyRequest();
        request.setName("mark1");
        request.setService("aaa");

        StrategyLabels labels = request.getLabels();
        StrategyAudioLabel audio = labels.getAudio();
        List<String> pron = audio.getPron();
        pron.add("Sexy");
        pron.add("Sexuality");

        StrategyImageLabel image = labels.getImage();
        List<String> politics = image.getPolitics();
        politics.add("NegativeFigure");
        politics.add("ForeignLeaders");

        List<String> textLibs = request.getTextLibs();
        textLibs.add("aaaaa");
        textLibs.add("bbbbb");


        // 创建XStream对象
        XStream xstream = new XStream();
        xstream.processAnnotations(AuditingStrategyRequest.class);
        Field[] fields = CosServiceRequest.class.getDeclaredFields();
        for (Field field : fields) {
            xstream.omitField(CosServiceRequest.class, field.getName());
        }
        String s = xstream.toXML(request);


        AuditingStrategyResponse response = client.addAuditingStrategy(request);
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
