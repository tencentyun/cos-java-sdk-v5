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

public abstract class COSDirect implements COSDirectSpi {
    public abstract PutObjectResult putObject(PutObjectRequest req);

    public abstract COSObject getObject(GetObjectRequest req);

    public abstract ObjectMetadata getObject(GetObjectRequest req, File dest);

    public abstract CompleteMultipartUploadResult completeMultipartUpload(
            CompleteMultipartUploadRequest req);

    public abstract InitiateMultipartUploadResult initiateMultipartUpload(
            InitiateMultipartUploadRequest req);

    public abstract UploadPartResult uploadPart(UploadPartRequest req);

    public abstract CopyPartResult copyPart(CopyPartRequest req);

    public abstract void abortMultipartUpload(AbortMultipartUploadRequest req);
}
