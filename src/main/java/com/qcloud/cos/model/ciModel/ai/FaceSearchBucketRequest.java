package com.qcloud.cos.model.ciModel.ai;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("Request")
public class FaceSearchBucketRequest extends CIServiceRequest {

    @XStreamAlias("GroupName")
    private String groupName;

    public String getGroupName() { return groupName; }

    public void setGroupName(String groupName) { this.groupName = groupName; }

}
