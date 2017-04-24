package com.qcloud.cos.model;

public class UinGrantee implements Grantee {

    private String id;

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

}
