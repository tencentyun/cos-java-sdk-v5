package com.qcloud.cos.demo;

import java.util.ArrayList;
import java.util.List;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.exception.MultiObjectDeleteException.DeleteError;
import com.qcloud.cos.model.DeleteObjectsRequest;
import com.qcloud.cos.model.DeleteObjectsRequest.KeyVersion;
import com.qcloud.cos.model.DeleteObjectsResult;
import com.qcloud.cos.model.DeleteObjectsResult.DeletedObject;
import com.qcloud.cos.region.Region;

/**
 * DelFileDemo展示了删除单个文件的DelObject, 删除多个文件的DelObjects的使用示例.
 */
public class DelFileDemo {
    private static String secretId = "AKIDXXXXXXXX";
    private static String secretKey = "1A2Z3YYYYYYYYYY";
    private static String bucketName = "examplebucket-12500000000";
    private static String region = "ap-guangzhou";
    private static COSClient cosClient = createCli();
    public static void main(String[] args) {
        delSingleFile();

        if (cosClient != null) {
            cosClient.shutdown();
        }
    }

    private static COSClient createCli() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        return cosclient;
    }

    // 删除单个文件(不带版本号, 即bucket未开启多版本)
    private static void delSingleFile() {
        try {
            String key = "aaa/bbb.txt";
            cosClient.deleteObject(bucketName, key);
        } catch (CosServiceException e) { // 如果是其他错误, 比如参数错误， 身份验证不过等会抛出CosServiceException
            e.printStackTrace();
        } catch (CosClientException e) { // 如果是客户端错误，比如连接不上COS
            e.printStackTrace();
        }

    }

    // 批量删除文件(不带版本号, 即bucket未开启多版本)
    private static void batchDelFile() {
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);
        // 设置要删除的key列表, 最多一次删除1000个
        ArrayList<KeyVersion> keyList = new ArrayList<>();
        // 传入要删除的文件名
        keyList.add(new KeyVersion("aaa.txt"));
        keyList.add(new KeyVersion("bbb.mp4"));
        keyList.add(new KeyVersion("ccc/ddd.jpg"));
        deleteObjectsRequest.setKeys(keyList);

        // 批量删除文件
        try {
            DeleteObjectsResult deleteObjectsResult = cosClient.deleteObjects(deleteObjectsRequest);
            List<DeletedObject> deleteObjectResultArray = deleteObjectsResult.getDeletedObjects();
        } catch (MultiObjectDeleteException mde) { // 如果部分产出成功部分失败, 返回MultiObjectDeleteException
            List<DeletedObject> deleteObjects = mde.getDeletedObjects();
            List<DeleteError> deleteErrors = mde.getErrors();
        } catch (CosServiceException e) { // 如果是其他错误, 比如参数错误， 身份验证不过等会抛出CosServiceException
            e.printStackTrace();
        } catch (CosClientException e) { // 如果是客户端错误，比如连接不上COS
            e.printStackTrace();
        }
    }

    // 批量删除带有版本号的文件(即bucket开启了多版本)
    private static void batchDelFileWithVersion() {
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);
        // 设置要删除的key列表, 最多一次删除1000个
        ArrayList<KeyVersion> keyList = new ArrayList<>();
        // 传入要删除的文件名
        keyList.add(new KeyVersion("aaa.txt", "axbefagagaxxfafa"));
        keyList.add(new KeyVersion("bbb.mp4", "awcafa1faxg0lx"));
        keyList.add(new KeyVersion("ccc/ddd.jpg", "kafa1kxxaa2ymh"));
        deleteObjectsRequest.setKeys(keyList);

        // 批量删除文件
        try {
            DeleteObjectsResult deleteObjectsResult = cosClient.deleteObjects(deleteObjectsRequest);
            List<DeletedObject> deleteObjectResultArray = deleteObjectsResult.getDeletedObjects();
        } catch (MultiObjectDeleteException mde) { // 如果部分产出成功部分失败, 返回MultiObjectDeleteException
            List<DeletedObject> deleteObjects = mde.getDeletedObjects();
            List<DeleteError> deleteErrors = mde.getErrors();
        } catch (CosServiceException e) { // 如果是其他错误, 比如参数错误， 身份验证不过等会抛出CosServiceException
            e.printStackTrace();
        } catch (CosClientException e) { // 如果是客户端错误，比如连接不上COS
            e.printStackTrace();
        }
    }
}
