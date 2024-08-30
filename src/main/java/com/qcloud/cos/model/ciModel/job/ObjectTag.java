package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class ObjectTag  {
    @XStreamImplicit(itemFieldName = "Objects")
    private List<ObjectTagInfo> objects;

    @XStreamAlias("TimeStamp")
    private String timeStamp;

    public List<ObjectTagInfo> getObjects() {
        return objects;
    }

    public void setObjects(List<ObjectTagInfo> objects) {
        this.objects = objects;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
