package com.qcloud.cos.region;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.internal.UrlComponentUtils;

public class CIRegion  extends Region{
    public CIRegion(String region_name) {
        super(region_name);
    }

    public CIRegion(String region_name, String displayName) {
        super(region_name, displayName);
    }
}
