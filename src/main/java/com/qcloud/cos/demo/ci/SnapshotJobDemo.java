package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.model.ciModel.template.MediaSnapshotObject;
import com.qcloud.cos.model.ciModel.template.SpriteSnapshotConfig;

import java.io.UnsupportedEncodingException;

/**
 * 媒体处理 snapshot job接口相关demo 详情见https://cloud.tencent.com/document/product/460/48222
 */
public class SnapshotJobDemo {

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
        request.setTag("Snapshot");
        request.getInput().setObject("1.mp4");
        MediaSnapshotObject snapshot = request.getOperation().getSnapshot();
        snapshot.setCount("10");
        snapshot.setMode("Interval");
        snapshot.setWidth("1280");
        snapshot.setStart("0");
        snapshot.setTimeInterval("10");
        SpriteSnapshotConfig snapshotConfig = snapshot.getSnapshotConfig();
        snapshotConfig.setCellHeight("128");
        snapshotConfig.setCellWidth("128");
        snapshotConfig.setColor("White");
        snapshotConfig.setColumns("Columns");
        snapshotConfig.setLines("10");
        snapshotConfig.setMargin("0");
        snapshotConfig.setPadding("0");
        request.getOperation().getOutput().setBucket("demobucket-1234567890");
        request.getOperation().getOutput().setRegion("ap-chongqing");
        request.getOperation().getOutput().setObject("snapshot-${Number}.jpg");
        //3.调用接口,获取任务响应对象
        MediaJobResponse response = client.createMediaJobs(request);
        System.out.println(response);
    }

}
