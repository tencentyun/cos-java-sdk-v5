package com.qcloud.cos.demo.ci;

import com.qcloud.cos.*;
import com.qcloud.cos.model.ciModel.workflow.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;

import java.util.List;

/**
 *  工作流相关接口demo 详情见 : https://cloud.tencent.com/document/product/460/45947
 */
public class WorkFlowDemo {
    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = "secretId";
        String secretKey = "secretKey";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, CI 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 https), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("chongqing");
        CIClientConfig clientConfig = new CIClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient client = new COSClient(cred, clientConfig);


        // 4 调用要使用的方法。
        describeWorkflowExecution(client);
    }

    /**
     * DescribeWorkflow 接口用于搜索工作流。
     * @param client
     */
    public static void describeWorkflow(COSClient client){
        MediaWorkflowRequest request = new MediaWorkflowRequest();
        request.setBucketName("markjrzhang-1251704708");
        MediaWorkflowResponse response = client.describeWorkflow(request);
        List<MediaWorkflowObject> mediaWorkflowList = response.getMediaWorkflowList();
        for (MediaWorkflowObject mediaWorkflowObject : mediaWorkflowList) {
            System.out.println(mediaWorkflowObject);
        }

    }

    /**
     * Delete Workflow 接口用于删除工作流。
     * @param client
     */
    public static void deleteWorkflow(COSClient client){
        MediaWorkflowRequest request = new MediaWorkflowRequest();
        request.setBucketName("markjrzhang-1251704708");
        request.setWorkflowId("aaaa");
        Boolean response = client.deleteWorkflow(request);
        System.out.println(response);
    }

    /**
     * Describe WorkflowExecution 接口用于获取工作流实例详情。
     * @param client
     */
    public static void describeWorkflowExecution(COSClient client){
        MediaWorkflowRequest request = new MediaWorkflowRequest();
        request.setBucketName("markjrzhang-1251704708");
        request.setRunId("i34bfd8d7eae711ea89fe525400ca1839");
        MediaWorkflowExecutionResponse response = client.describeWorkflowExecution(request);
        System.out.println(response);
    }

    /**
     * Describe WorkflowExecution 接口用于获取工作流实例列表。。
     * @param client
     */
    public static void describeWorkflowExecutions(COSClient client){
        MediaWorkflowRequest request = new MediaWorkflowRequest();
        request.setBucketName("markjrzhang-1251704708");
        request.setWorkflowId("w4e6963a18e2446ed8bc8f09410e38580");
        MediaWorkflowExecutionsResponse response = client.describeWorkflowExecutions(request);
        System.out.println(response);
    }


}
