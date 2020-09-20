package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.workflow.*;

import java.util.List;

/**
 * 工作流相关接口demo 详情见 : https://cloud.tencent.com/document/product/460/45947
 */
public class WorkFlowDemo {
    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describeWorkflow(client);
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
        request.setBucketName("markjrzhang-1251704708");
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
        request.setBucketName("markjrzhang-1251704708");
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
        request.setBucketName("markjrzhang-1251704708");
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
        request.setBucketName("markjrzhang-1251704708");
        request.setWorkflowId("w4e6963a18e2446ed8bc8f09410e38580");
        MediaWorkflowExecutionsResponse response = client.describeWorkflowExecutions(request);
        List<MediaWorkflowExecutionObject> workflowExecutionList = response.getWorkflowExecutionList();
        for (MediaWorkflowExecutionObject mediaWorkflowExecutionObject : workflowExecutionList) {
            System.out.println(mediaWorkflowExecutionObject);
        }
    }

}
