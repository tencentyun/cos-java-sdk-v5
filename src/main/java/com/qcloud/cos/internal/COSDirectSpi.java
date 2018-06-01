package com.qcloud.cos.internal;

import java.io.File;

import com.qcloud.cos.model.AbortMultipartUploadRequest;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.CompleteMultipartUploadRequest;
import com.qcloud.cos.model.CompleteMultipartUploadResult;
import com.qcloud.cos.model.CopyPartRequest;
import com.qcloud.cos.model.CopyPartResult;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.InitiateMultipartUploadRequest;
import com.qcloud.cos.model.InitiateMultipartUploadResult;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.UploadPartRequest;
import com.qcloud.cos.model.UploadPartResult;

/**
 * A Service Provider Interface that allows direct access to the underlying non-encrypting COS
 * client of an COS encryption client instance.
 */
public interface COSDirectSpi {
    public PutObjectResult putObject(PutObjectRequest req);

    public COSObject getObject(GetObjectRequest req);

    public ObjectMetadata getObject(GetObjectRequest req, File dest);

    public CompleteMultipartUploadResult completeMultipartUpload(
            CompleteMultipartUploadRequest req);

    public InitiateMultipartUploadResult initiateMultipartUpload(
            InitiateMultipartUploadRequest req);

    public UploadPartResult uploadPart(UploadPartRequest req);

    public CopyPartResult copyPart(CopyPartRequest req);

    public void abortMultipartUpload(AbortMultipartUploadRequest req);
}
