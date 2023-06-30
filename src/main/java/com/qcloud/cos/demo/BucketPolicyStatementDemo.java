package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.Policy.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.Jackson;

import java.util.ArrayList;
import java.util.List;

public class BucketPolicyStatementDemo {
    public static void SetBucketPolicySidDemo() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        String bucketName = "exampleBucket-exampleAppid";

        List<String> paths = new ArrayList<>();
        String path1 = PolicyUtils.buildQcsResourcePath("ap-guangzhou", "exampleAppid",
                "exampleBucket-exampleAppid", "*");
        paths.add(path1);


        String subUin = "xxxxxxxxx";
        SetBucketPolicyStatementRequest setBucketPolicyStatementRequest = new SetBucketPolicyStatementRequest(bucketName,
                subUin, paths, PolicyUtils.ACTION_TEMPLATE_HEAD_BUCKET);

        // ownerUin一定要设置
        setBucketPolicyStatementRequest.setOwnerUin("xxxxxxxxx");

        try {
            cosclient.setBucketPolicyStatement(setBucketPolicyStatementRequest);
        } catch (CosServiceException cse) {
            cse.printStackTrace();
        } catch (CosClientException cce) {
            cce.printStackTrace();
        } finally {
            cosclient.shutdown();
        }
    }


    public static void DelBucketPolicySidDemo() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        String bucketName = "exampleBucket-exampleAppid";

        String subUin = "xxxxxxxxxxx";

        DelBucketPolicyStatementRequest request = new DelBucketPolicyStatementRequest(bucketName, subUin, PolicyUtils.ACTION_TEMPLATE_READ);

        List<String> paths = new ArrayList<>();
        String path1 = PolicyUtils.buildQcsResourcePath("ap-guangzhou", "exampleAppid",
                "exampleBucket-exampleAppid", "*");
        paths.add(path1);

        request.setPath2Delete(paths);

        try {
            cosclient.deleteBucketPolicyStatement(request);
        } catch (CosServiceException cse) {
            cse.printStackTrace();
        } catch (CosClientException cce) {
            cce.printStackTrace();
        } finally {
            cosclient.shutdown();
        }
    }

    public static void GetBucketPolicySidDemo() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXXXXXX", "XXXXXXXXXXXX");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        String bucketName = "exampleBucket-exampleAppid";

        GetBucketPolicyStatementRequest request = new GetBucketPolicyStatementRequest(bucketName);

        // 设置查询条件 指定subUin（如有需要）
        List<String> subUins = new ArrayList<>();
        subUins.add("xxxxxxxxxx");
        subUins.add("xxxxxxxxxx");
        request.setSubUins(subUins);

        // 设置查询条件 指定文件路径（如有需要）
        List<String> resources = new ArrayList<>();
        resources.add(PolicyUtils.buildQcsResourcePath("ap-guangzhou", "exampleAppid",
                 "exampleBucket-exampleAppid", "usr/warehouse/1.db/*"));
        request.setResourcePaths(resources);

        // 设置查询条件 指定action模板（如有需要）
        List<String> actions = new ArrayList<>();
        actions.add(PolicyUtils.ACTION_TEMPLATE_READ);
        actions.add(PolicyUtils.ACTION_TEMPLATE_READ_WRITE);
        request.setActionTemplates(actions);

        try {
            BucketStatementResult result = cosclient.getBucketPolicyStatement(request);
            String s2 = Jackson.toJsonPrettyString(result);
            System.out.println(s2);
        } catch (CosServiceException cse) {
            cse.printStackTrace();
        } catch (CosClientException cce) {
            cce.printStackTrace();
        } finally {
            cosclient.shutdown();
        }
    }


    public static void main(String[] args) {
        SetBucketPolicySidDemo();
        GetBucketPolicySidDemo();
        DelBucketPolicySidDemo();
    }
}
