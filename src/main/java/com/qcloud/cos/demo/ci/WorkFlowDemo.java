package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.template.MediaWaterMarkText;
import com.qcloud.cos.model.ciModel.template.MediaWatermark;
import com.qcloud.cos.model.ciModel.workflow.*;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 工作流相关接口demo 详情见 : https://cloud.tencent.com/document/product/460/45947
 */
public class WorkFlowDemo {
    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describeWorkflowExecution(client);
    }

    /**
     * DescribeWorkflow 接口用于搜索工作流。
     *
     * @param client
     */
    public static void describeWorkflow(COSClient client) {
        //1.创建工作流请求对象
        MediaWorkflowListRequest request = new MediaWorkflowListRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("DemoBucket-123456789");
        MediaWorkflowListResponse response = client.describeWorkflow(request);
        List<MediaWorkflowObject> mediaWorkflowList = response.getMediaWorkflowList();
        for (MediaWorkflowObject mediaWorkflowObject : mediaWorkflowList) {
            System.out.println(mediaWorkflowObject);
        }

    }

    /**
     * Delete Workflow 接口用于删除工作流。
     *
     * @param client
     */
    public static void deleteWorkflow(COSClient client) {
        //1.创建工作流请求对象
        MediaWorkflowListRequest request = new MediaWorkflowListRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("DemoBucket-123456789");
        request.setWorkflowId("aaaa");
        Boolean response = client.deleteWorkflow(request);
        System.out.println(response);
    }

    /**
     * Describe WorkflowExecution 接口用于获取工作流实例详情。
     *
     * @param client
     */
    public static void describeWorkflowExecution(COSClient client) {
        //1.创建工作流请求对象
        MediaWorkflowListRequest request = new MediaWorkflowListRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("DemoBucket-123456789");
        request.setRunId("i34bfd8d7eae711ea89fe525400c******");
        MediaWorkflowExecutionResponse response = client.describeWorkflowExecution(request);
        System.out.println(response);
    }

    /**
     * Describe WorkflowExecution 接口用于获取工作流实例列表。
     *
     * @param client
     */
    public static void describeWorkflowExecutions(COSClient client) {
        //1.创建工作流请求对象
        MediaWorkflowListRequest request = new MediaWorkflowListRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("DemoBucket-123456789");
        request.setWorkflowId("w4e6963a18e2446ed8bc8f09410e******");
        MediaWorkflowExecutionsResponse response = client.describeWorkflowExecutions(request);
        List<MediaWorkflowExecutionObject> workflowExecutionList = response.getWorkflowExecutionList();
        for (MediaWorkflowExecutionObject mediaWorkflowExecutionObject : workflowExecutionList) {
            System.out.println(mediaWorkflowExecutionObject);
        }
    }

    /**
     * triggerWorkflowList 接口手动调用工作流处理资源
     *
     * @param client
     */
    public static void triggerWorkflowList(COSClient client) {
        //1.创建工作流请求对象
        MediaWorkflowListRequest request = new MediaWorkflowListRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-123567890");
        request.setWorkflowId("w7d3f3c315b11497099632d9eb4f*****");
        request.setObject("1.mp4");
        AttachParam attachParam = new AttachParam();
        MediaWatermark watermark = new MediaWatermark();
        watermark.setType("Text");
        watermark.setLocMode("Absolute");
        watermark.setDx("128");
        watermark.setDy("128");
        watermark.setPos("TopRight");
        watermark.setStartTime("0");
        watermark.setEndTime("5");
        MediaWaterMarkText text = watermark.getText();
        text.setText("水印内容");
        text.setFontSize("30");
        text.setFontType("simfang.ttf");
        text.setFontColor("0x000000");
        text.setTransparency("30");
        attachParam.setWatermark(watermark);
        request.setAttachParam(attachParam);
        MediaWorkflowListResponse response = client.triggerWorkflowList(request);
        System.out.println(Jackson.toJsonString(response));
    }

}
