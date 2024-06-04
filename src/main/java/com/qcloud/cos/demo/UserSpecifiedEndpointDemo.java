package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.endpoint.UserSpecifiedEndpointBuilder;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.Download;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.File;

public class UserSpecifiedEndpointDemo {
    private static String bucketName = "mybucket-12500000000";

    private static String key = "exampleobject";

    private static TransferManager transferManager = createTransferManager();

    public static void main(String[] args) {
        try {
            putObjectDemo();
            getObjectDemo();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            transferManager.shutdownNow();
        }
    }

    private static void putObjectDemo() {
        File localFile = new File("/path/to/localFile");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        try {
            // 返回一个异步结果 Upload , 可同步的调用 waitForUploadResult 等待 upload 结束, 成功返回 UploadResult , 失败抛出异常.
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            System.out.printf("RequestId : %s\n", uploadResult.getRequestId());
            System.out.println(uploadResult.getETag());
            System.out.println(uploadResult.getCrc64Ecma());
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void getObjectDemo() {
        File downloadFile = new File("/path/to/downloadFile");
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        try {
            // 返回一个异步结果 download, 可同步的调用 waitForCompletion 等待 download 结束, 成功返回 void , 失败抛出异常.
            Download download = transferManager.download(getObjectRequest, downloadFile);
            download.waitForCompletion();
            System.out.printf("RequestId : %s\n", download.getObjectMetadata().getRequestId());
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static TransferManager createTransferManager() {
        // 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX","1A2Z3YYYYYYYYYY");
        // 2 设置 bucket 的地域, COS_REGION 请参见 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("COS_REGION"));
        // 设置请求协议，推荐使用 https 协议
        clientConfig.setHttpProtocol(HttpProtocol.https);
        //若设置自定义源站域名时未上传 https 证书，则改为 clientConfig.setHttpProtocol(HttpProtocol.http);
        // 设置自定义的域名
        UserSpecifiedEndpointBuilder endpointBuilder = new UserSpecifiedEndpointBuilder("generalApiEndpoint", "getServiceApiEndpoint");
        clientConfig.setEndpointBuilder(endpointBuilder);
        // 3 生成 cos 客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        // 传入一个 threadpool, 若不传入线程池, 默认 TransferManager 中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosclient, threadPool);

        return transferManager;
    }
}
