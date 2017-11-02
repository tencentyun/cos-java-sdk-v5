package com.qcloud.cos.model;

public class UinGrantee implements Grantee {

    private String id;
    private String displayName;
    
    public UinGrantee(String id) {
        super();
        this.id = id;
    }

    @Override
    public String getTypeIdentifier() {
        return "uin";
    }

    @Override
    public void setIdentifier(String id) {
        this.id = id;
    }

    @Override
    public String getIdentifier() {
        return this.id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
