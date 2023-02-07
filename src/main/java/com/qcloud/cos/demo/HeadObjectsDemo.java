package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.GetObjectMetadataRequest;
import com.qcloud.cos.model.HeadObjects.HeadManager;
import com.qcloud.cos.model.HeadObjects.HeadObjectResult;
import com.qcloud.cos.region.Region;

import java.util.ArrayList;
import java.util.List;

public class HeadObjectsDemo {
    public static void headObjects() {
        // 1 初始化用户身份信息(appid, secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名称, 需包含appid
        String bucketName = "mybucket-1251668577";
        String objectKey1 = "example_key1";
        String objectKey2 = "example_key2";
        List<GetObjectMetadataRequest> requests = new ArrayList<>();

        requests.add(new GetObjectMetadataRequest(bucketName, objectKey1));
        requests.add(new GetObjectMetadataRequest(bucketName, objectKey2));

        long startTime = System.currentTimeMillis();
        HeadManager headManager = new HeadManager(cosclient);
        //headManager.setRequestTimeout(1000);

        List<HeadObjectResult> headedObjects = headManager.headObjects(bucketName, requests);

        long endTime = System.currentTimeMillis();
        System.out.println("used time: " + (endTime - startTime));

        for (HeadObjectResult object : headedObjects) {
            if (object.isSuccess()) {
                if (object.isExist()){
                    System.out.println(object.getKey() +" true");
                }else{
                    System.out.println(object.getKey() +" false");
                }
            } else {
                System.out.println(object.getKey() + " do head object request failed");
            }
        }

        cosclient.shutdown();
    }

    public static void main(String[] args) {
        headObjects();
    }
}
