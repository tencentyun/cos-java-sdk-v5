package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.CopyObjectRequest;
import com.qcloud.cos.model.CopyObjectResult;
import com.qcloud.cos.model.COSVersionSummary;
import com.qcloud.cos.model.ListVersionsRequest;
import com.qcloud.cos.model.VersionListing;
import com.qcloud.cos.region.Region;

import java.util.ArrayList;
import java.util.List;

public class RecoverObjectsDemo2 {
    private static String secretId = "AKIDXXXXXXXX";
    private static String secretKey = "1A2Z3YYYYYYYYYY";
    private static String srcbucketName = "examplebucket-backup-12500000000";
    private static String dstbucketName = "examplebucket-dest-12500000000";
    private static String srcbucketRegion = "ap-guangzhou";
    private static String dstbucketRegion = "ap-shanghai";

    private static List<String> copyobjs = new ArrayList<>();

    private static COSClient srcCosClient = createCli(srcbucketRegion);
    private static COSClient dstCosClient = createCli(dstbucketRegion);

    public static void main(String[] args) {
        listAndRecoverObjs();
    }

    private static COSClient createCli(String region) {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));

        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 生成cos客户端
        return new COSClient(cred, clientConfig);
    }

    private static void recoverObj(String srcKey, String srcVersionId) {
        String dstKey = srcKey;
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(new Region(srcbucketRegion), srcbucketName, srcKey, dstbucketName, dstKey);
        copyObjectRequest.setSourceVersionId(srcVersionId);
        try {
            CopyObjectResult result = dstCosClient.copyObject(copyObjectRequest);
            String msg = String.format("finish recover by copying obj, srcBucket[%s], srcKey[%s], srcVersion[%s], dstBucket[%s], dstKey[%s], dstVersion[%s]",
                                        srcbucketName, srcKey, srcVersionId, dstbucketName, dstKey, result.getVersionId());
            System.out.println(msg);
            copyobjs.add(srcKey);
        } catch (CosServiceException cse) {
            cse.printStackTrace();
        } catch (CosClientException cce) {
            cce.printStackTrace();
        }
    }

    private static void listAndRecoverObjs() {
        ListVersionsRequest listVersionsRequest = new ListVersionsRequest();
        listVersionsRequest.setBucketName(srcbucketName);
        listVersionsRequest.setPrefix("");

        VersionListing versionListing = null;

        do {
            try {
                versionListing = srcCosClient.listVersions(listVersionsRequest);
            } catch (CosServiceException e) {
                e.printStackTrace();
                return;
            } catch (CosClientException e) {
                e.printStackTrace();
                return;
            }

            List<COSVersionSummary> cosVersionSummaries = versionListing.getVersionSummaries();
            for (COSVersionSummary cosVersionSummary : cosVersionSummaries) {
                String key = cosVersionSummary.getKey();
                String versionId = cosVersionSummary.getVersionId();
                boolean isDeleteMarker = cosVersionSummary.isDeleteMarker();
                boolean isLatest = cosVersionSummary.isLatest();
                if (!isDeleteMarker) {
                    if (isLatest) {
                        System.out.println("latest object, will copy " + "key:" + key + ", versionId:" + versionId);
                        recoverObj(key, versionId);
                    } else {
                        if (!copyobjs.contains(key)) {
                            System.out.println("not latest object, will copy " + "key:" + key + ", versionId:" + versionId);
                            recoverObj(key, versionId);
                        }
                    }
                }
            }

            String keyMarker = versionListing.getNextKeyMarker();
            String versionIdMarker = versionListing.getNextVersionIdMarker();

            listVersionsRequest.setKeyMarker(keyMarker);
            listVersionsRequest.setVersionIdMarker(versionIdMarker);

        } while (versionListing.isTruncated());
        System.out.println("--------------------------------------");
    }
}
