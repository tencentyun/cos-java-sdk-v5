package com.qcloud.cos.transfer;

import java.util.concurrent.Callable;

import com.qcloud.cos.COS;
import com.qcloud.cos.model.PartETag;
import com.qcloud.cos.model.UploadPartRequest;

public class UploadPartCallable implements Callable<PartETag> {
    private final COS cos;
    private final UploadPartRequest request;

    public UploadPartCallable(COS cos, UploadPartRequest request) {
        this.cos = cos;
        this.request = request;
    }

    public PartETag call() throws Exception {
        PartETag partETag = cos.uploadPart(request).getPartETag();
        return partETag;
    }
}
