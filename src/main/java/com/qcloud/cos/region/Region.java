package com.qcloud.cos.region;

import java.io.Serializable;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.internal.UrlComponentUtils;

public class Region implements Serializable {
    private static final long serialVersionUID = 1L;
    private String regionName;
    private String displayName;

    public Region(String region_name) {
        this(region_name, region_name);
    }

    public Region(String region_name, String displayName) {
        this.regionName = region_name;
        this.displayName = displayName;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof Region) {
            String anotherRegionName = ((Region) anObject).getRegionName();
            return anotherRegionName.equals(regionName);
        }
        return false;
    }
    
    public static String formatRegion(Region region) throws CosClientException {
        return formatRegion(region.getRegionName());
    }
    
    public static String formatRegion(String regionName) throws CosClientException {
        UrlComponentUtils.validateRegionName(regionName);
        if (regionName.startsWith("cos.")) {
            return regionName;
        } else {
            if (regionName.equals("cn-east") || regionName.equals("cn-south")
                    || regionName.equals("cn-north") || regionName.equals("cn-south-2")
                    || regionName.equals("cn-southwest") || regionName.equals("sg")) {
                return regionName;
            } else {
                return "cos." + regionName;
            }
        }
    }
}
