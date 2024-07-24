package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;

import com.qcloud.cos.model.COSVersionSummary;
import com.qcloud.cos.model.CopyObjectRequest;
import com.qcloud.cos.model.CopyObjectResult;
import com.qcloud.cos.model.ListVersionsRequest;
import com.qcloud.cos.model.VersionListing;
import com.qcloud.cos.region.Region;

import java.util.List;
import java.util.Objects;

public class RecoverObjectsDemo {
    private static String secretId = "AKIDXXXXXXXX";
    private static String secretKey = "1A2Z3YYYYYYYYYY";
    private static String bucketName = "examplebucket-12500000000";

    private static String bucketRegion = "ap-guangzhou";

    private static COSClient cosClient = createCli();

    public static void main(String[] args) {
        listAndRecoverObjs();
    }

    private static COSClient createCli() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(bucketRegion));

        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 生成cos客户端
        return new COSClient(cred, clientConfig);
    }

    private static void listAndRecoverObjs() {
        ListVersionsRequest listVersionsRequest = new ListVersionsRequest();
        listVersionsRequest.setBucketName(bucketName);
        listVersionsRequest.setPrefix("");
        listVersionsRequest.setMaxResults(1000);

        VersionListing versionListing = null;

        String recover_key = "";
        String recover_versionid = "";
        boolean has_recovered = false;

        do {
            try {
                versionListing = cosClient.listVersions(listVersionsRequest);
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
                String msg = String.format("list obj, Key[%s], Version[%s], isDeleteMarker[%s], isLatest[%s]", key, versionId, isDeleteMarker, isLatest);
                System.out.println(msg);
                if (isDeleteMarker && isLatest) {
                    // 只恢复最新 versionid 为删除标记的对象
                    recover_key = key;
                    has_recovered = false;
                } else if (!isDeleteMarker && !isLatest && Objects.equals(key, recover_key) && !has_recovered) {
                    // 既不是最新版本，也不是删除标记，如果key等于recover_key，那么说明找到了要恢复的数据版本，执行恢复逻辑
                    recover_versionid = versionId;
                    recoverObj(recover_key, recover_versionid);
                    has_recovered = true;
                }
            }

            String keyMarker = versionListing.getNextKeyMarker();
            String versionIdMarker = versionListing.getNextVersionIdMarker();

            listVersionsRequest.setKeyMarker(keyMarker);
            listVersionsRequest.setVersionIdMarker(versionIdMarker);

        } while (versionListing.isTruncated());
    }

    private static void recoverObj(String srcKey, String srcVersionId) {
        String dstKey = srcKey;
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(new Region(bucketRegion), bucketName, srcKey, bucketName, dstKey);
        copyObjectRequest.setSourceVersionId(srcVersionId);
        try {
            CopyObjectResult result = cosClient.copyObject(copyObjectRequest);
            String msg = String.format("finish recover by copying obj, srcKey[%s], srcVersion[%s], dstKey[%s], dstVersion[%s]", srcKey, srcVersionId, dstKey, result.getVersionId());
            System.out.println(msg);
        } catch (CosServiceException cse) {
            cse.printStackTrace();
        } catch (CosClientException cce) {
            cce.printStackTrace();
        }
    }
}
