package com.qcloud.cos.region;

import java.io.Serializable;

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
}
