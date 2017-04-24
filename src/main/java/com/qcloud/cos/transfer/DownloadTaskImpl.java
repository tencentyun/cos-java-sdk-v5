package com.qcloud.cos.transfer;

import com.qcloud.cos.COS;
import com.qcloud.cos.internal.SkipMd5CheckStrategy;
import com.qcloud.cos.model.COSEncryption;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.utils.ServiceUtils;

final class DownloadTaskImpl implements ServiceUtils.RetryableCOSDownloadTask {
    private final COS cos;
    private final DownloadImpl download;
    private final GetObjectRequest getObjectRequest;
    private final SkipMd5CheckStrategy skipMd5CheckStrategy = SkipMd5CheckStrategy.INSTANCE;

    DownloadTaskImpl(COS cos, DownloadImpl download, GetObjectRequest getObjectRequest) {
        this.cos = cos;
        this.download = download;
        this.getObjectRequest = getObjectRequest;
    }
    
    @Override
    public COSObject getCOSObjectStream() {
        COSObject cosObject = cos.getObject(getObjectRequest);
        download.setCosObject(cosObject);
        return cosObject;
    }

    @Override
    public boolean needIntegrityCheck() {
        // Don't perform the integrity check if the checksum won't matchup.
        return !(cos instanceof COSEncryption)
                && !skipMd5CheckStrategy.skipClientSideValidationPerRequest(getObjectRequest);
    }
}
