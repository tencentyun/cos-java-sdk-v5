package com.qcloud.cos.region;

public enum Region {
    CN_NORTH("cn-north", "china north"), // 华北
    CN_SOUTH("cn-south", "china south"), // 华南 
    CN_SOUTH2("cn-south-2", "china south"), // 华南2区 
    CN_EAST("cn-east", "china east"),    // 华东
    SG("sg", "singpore");


    private String regionName;
    private String displayName;

    private Region(String region_name, String displayName) {
        this.regionName = region_name;
        this.displayName = displayName;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
